DROP PROCEDURE WEBOBJLIB.MWR001U2;


CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWR001U2
(
     IN  I_COR    VARCHAR(3)    -- COR
   , IN  I_UID    VARCHAR(12) -- 사용자 ID
   , IN  I_IP        VARCHAR(30)   -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)   -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)   -- 에러 코드
   , IN   I_DAT    DECIMAL(8,0)  --접수일자
   , IN   I_JNO    DECIMAL(5,0)   --접수번호
   , IN   I_GCD    CHAR(5)      --검사코드
   , IN   I_STS    VARCHAR(20)   --상태
   , IN   I_TCD    VARCHAR(20)   --검체종류
   , IN   I_REJ    VARCHAR(20)   --거부사유
   , IN   I_SSU  DECIMAL(7,0)       -- 기준수가
   , IN   I_BSU  DECIMAL(7,0)       -- 보험수가
   , IN   I_JSU  DECIMAL(7,0)       -- 질가산료
   , IN   I_ASU  DECIMAL(7,0)       -- 실제가
   , IN   I_BCC     CHAR(1)         -- 보험청구여부
   , IN   I_CSU  DECIMAL(7,0)      --  청구가
   , IN   I_CCR  DECIMAL(4,1)       --  청구DC율
)
BEGIN
   DECLARE V_CNT DECIMAL(5,0);   --  총 수량
   DECLARE V_MCCNT DECIMAL(5,0);   --  수량 확인 MCLISDLIB 테이블에 데이터가 있는지 확인
   DECLARE V_STSCNT DECIMAL(5,0);   --  승인/ 거부 수량
   DECLARE V_RCNT DECIMAL(5,0);   --  승인거부 수량
   DECLARE V_STS VARCHAR(20);   --  상태값
   DECLARE V_MCCNT_DEL_YN DECIMAL(5,0);   --  수량 확인 MCLISDLIB 테이블에 검사항목을 삭제했던 데이터가 있는지 확인
   
   IF I_STS = 'APPROVAL' THEN
      -- 추가의뢰정보 승인
      SET O_MSGCOD = '262';--승인을 완료하였습니다.
   ELSE
      -- 추가의뢰정보 승인
      SET O_MSGCOD = '263'; --승인거부를 완료하였습니다.
   END IF;
   SET O_ERRCOD = '';


   UPDATE WEBDBLIB.MWR001D@
   SET R001STS   =   I_STS   --상태
      , R001TCD   =   I_TCD   -- 검체종류 
      , R001REJ   =  CASE WHEN I_STS= 'REJECT' 
      				 THEN I_REJ  
      				 ELSE ''  END -- 거부사유
      , R001AUR   =   I_UID   -- 수정자 
      , R001ADT   =   TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   -- 수정일자
      , R001ATM   =   TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')    -- 수정시간
      , R001AIP   =   I_IP      --수정IP
  WHERE R001COR = I_COR   --COR
  AND R001DAT= I_DAT   --접수일자
  AND R001JNO = I_JNO   --접수번호
  AND R001GCD = I_GCD--검사코드
   ;

   SELECT (SELECT COUNT(*)
		      FROM WEBDBLIB.MWR001D@
		      WHERE R001COR = 'NML'   --COR
		      AND R001DAT =I_DAT   --접수일자
		      AND R001JNO = I_JNO   --접수번호
		   ) AS CNT,
		   (SELECT COUNT(*)
		      FROM WEBDBLIB.MWR001D@
		      WHERE R001COR = 'NML'   --COR
		      AND R001DAT =I_DAT   --접수일자
		      AND R001JNO = I_JNO   --접수번호
		      AND R001STS != 'STANDBY'   -- 승인 / 거부 수량
		   ) AS STSCNT,
		   (SELECT COUNT(*)
		      FROM WEBDBLIB.MWR001D@
		      WHERE R001COR = 'NML'   --COR
		      AND R001DAT =I_DAT   --접수일자
		      AND R001JNO = I_JNO   --접수번호
		      AND R001STS = 'REJECT'   -- 거부 상태
		   ) AS RCNT
   INTO V_CNT,V_STSCNT, V_RCNT
   FROM SYSIBM.SYSDUMMY1;
   
   
   -- 접수대기 : 한 항목이라도 미처리(대기) 항목이 있는 경우
   IF V_CNT - V_STSCNT > 0 THEN
      SET V_STS = 'S';   -- 접수대기
   ELSE 
    --접수완료 : 전 항목 '승인' 처리 시
    SET V_STS = 'C';   --접수완료
    
    -- 부분완료 : '승인','거부'가 섞여있는 경우
   	IF V_RCNT > 0 THEN
      SET V_STS = 'P';   --부분완료
    END IF;
    
    --접수불가 : 전체 항목이 모두 '거부'인 경우
    IF V_STSCNT = V_RCNT THEN
      SET V_STS = 'R';   --접수불가
    END IF;
   
   END IF;

   UPDATE WEBDBLIB.MWR001M@
   SET R001STS   =   V_STS   --상태
      , R001UUR   =   I_UID   -- 수정자 
      , R001UDT   =   TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   -- 수정일자
      , R001UTM   =   TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')    -- 수정시간
      , R001UIP   =   I_IP      --수정IP
      WHERE R001COR = I_COR   --COR
      AND R001DAT= I_DAT   --접수일자
      AND R001JNO = I_JNO   --접수번호
  	 ;
	IF I_STS = 'APPROVAL' THEN
	
	   SELECT COUNT(*)
	   INTO V_MCCNT
	   FROM MCLISDLIB.MC111D@
	   WHERE  F111COR  = I_COR
	   AND F111DAT  = I_DAT
	   AND F111JNO  = I_JNO
	   --AND F111TCD  = I_TCD ;
	   -- 2020.10.26 (변경후,아래) 
	   -- 검체테이블(MC111D@)의 경우, LF 파일에 유니크key 잡혀있는 것(MC111D@07,MC111D@14)이 있어서
	   -- F111DAT, F111JNO, F111GNO 컬럼이 중복되면 sql로 data insert가 되지 않는다. 
	   AND F111GNO  = (CASE WHEN I_TCD = 'A01'THEN 2
	            WHEN I_TCD = 'A02'THEN 9
	            WHEN I_TCD = 'A05'THEN 3
	            WHEN I_TCD = 'U01'
	                  OR I_TCD = 'U02'
	                  OR I_TCD = 'U03'
	               THEN 4
	            WHEN I_TCD = 'C12'THEN 5
	            ELSE 0 END);
	
	   IF V_MCCNT  = 0 THEN
		   INSERT INTO MCLISDLIB.MC111D@(
		           F111COR
		         , F111DAT
		         , F111JNO
		         , F111GCD
		         , F111TCD
		         , F111GNO
		         , F111GDT
		         , F111GTM
		         , F111WGL
		         , F111HGN
		         , F111STS
		         , F111RNO
		         , F111HNO
		         , F111C01
		         , F111C02
		         , F111C03
		         , F111C04
		         , F111C05
		         , F111RE1
		         , F111RE2
		         , F111TIF
		         , F111RS1
		         , F111RS2
		         , F111TCA
		         , F111CUR
		         , F111CDT
		         , F111CTM
		      )
	      SELECT
	         I_COR   AS  F111COR
	         , I_DAT   AS  F111DAT
	         , I_JNO   AS  F111JNO
	         , I_GCD   AS  F111GCD
	         , I_TCD   AS  F111TCD
	         , CASE WHEN I_TCD = 'A01'THEN 2
	            WHEN I_TCD = 'A02'THEN 9
	            WHEN I_TCD = 'A05'THEN 3
	            WHEN I_TCD = 'U01'
	                  OR I_TCD = 'U02'
	                  OR I_TCD = 'U03'
	               THEN 4
	            WHEN I_TCD = 'C12'THEN 5
	            ELSE 0
	            END AS F111GNO
	         , 0   AS  F111GDT
	         , 0   AS  F111GTM
	         , 0   AS  F111WGL
	         , ''   AS  F111HGN
	         , ''   AS  F111STS
	         , ''   AS  F111RNO
	         , 0   AS  F111HNO
	         , ''   AS  F111C01
	         , ''   AS  F111C02
	         , ''   AS  F111C03
	         , ''   AS  F111C04
	         , ''   AS  F111C05
	         , ''   AS  F111RE1
	         , ''   AS  F111RE2
	         , ''   AS  F111TIF
	         , ''   AS  F111RS1
	         , ''   AS  F111RS2
	         , ''   AS  F111TCA
	         , I_UID  AS  F111CUR
	         , TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   AS  F111CDT
	         , TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')  AS  F111CTM
	      FROM  SYSIBM.SYSDUMMY1 ;
	   END IF;
	
	   -- 기존 접수데이터에 추가요청한 검사종목이 있는지 CHECK
	   SELECT COUNT(*)
	   INTO V_MCCNT
	   FROM MCLISDLIB.MC110H@
	   WHERE  F110COR  = I_COR
	   AND F110DAT  = I_DAT
	   AND F110JNO  = I_JNO
	   AND F110GCD  = I_GCD ;
	   
	   -- 기존 접수데이터에 추가요청한 검사종목을 삭제했던 기록이 있는지 CHECK
	   SELECT COUNT(*)
	   INTO V_MCCNT_DEL_YN
	   FROM MCLISDLIB.MC110H@
	   WHERE  F110COR  = I_COR
	   AND F110DAT  = I_DAT
	   AND F110JNO  = I_JNO
	   AND F110GCD  = I_GCD 
	   AND F110STS  = 'D' ;
	
	   IF V_MCCNT  = 0 THEN
	      INSERT INTO MCLISDLIB.MC110H@(
	           F110COR
	         , F110DAT
	         , F110JNO
	         , F110GCD
	         , F110CNO
	         , F110STS
	         , F110SSU
	         , F110BSU
	         , F110JSU
	         , F110ASU
	         , F110DCR
	         , F110DCN
	         , F110BCC
	         , F110RRC
	         , F110UPD
	         , F110CDT
	         , F110CTM
	         , F110CUR
	         , F110C01
	         , F110C02
	         , F110C03
	         , F110C04
	         , F110C05
	         , F110CSU
	         , F110CCR
	         , F110CCN
	      )
	      SELECT
	         I_COR   AS  F110COR
	         , I_DAT   AS  F110DAT
	         , I_JNO   AS  F110JNO
	         , I_GCD   AS  F110GCD
	         , '' AS F110CNO
	         , 'B' AS F110STS
	         , I_SSU AS F110SSU
	         , I_BSU AS F110BSU
	         , I_JSU AS F110JSU
	         , I_ASU AS F110ASU
	         , 0 AS F110DCR
	         , '' AS F110DCN
	         , I_BCC  AS F110BCC
	         , 0 AS F110RRC
	         , 'N' AS F110UPD
	         , TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   AS F110CDT
	         , TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')  AS F110CTM
	         , I_UID AS F110CUR
	         , 'Y' AS F110C01
	         , '' AS F110C02
	         , ' 'AS F110C03
	         , ' ' AS F110C04
	         , '' AS F110C05
	         , I_CSU AS F110CSU
	         , I_CCR AS F110CCR
	         , ' 'AS F110CCN
	      FROM  SYSIBM.SYSDUMMY1 ;
	     ELSE 
	      IF V_MCCNT_DEL_YN  = 1 THEN
	      	UPDATE MCLISDLIB.MC110H@ SET F110STS = 'B'
	      	WHERE  F110COR  = I_COR
	   		AND F110DAT  = I_DAT
	   		AND F110JNO  = I_JNO
	   		AND F110GCD  = I_GCD 
	   		AND F110STS  = 'D' ;
	      END IF;
	     END IF;
     END IF;
END


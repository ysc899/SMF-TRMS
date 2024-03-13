--DROP PROCEDURE WEBOBJLIB.MCI008TR;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MCI008TR
(
     IN I_COR       VARCHAR(3)    -- COR
   , IN I_UID       VARCHAR(12)   -- 사용자 ID
   , IN I_IP        VARCHAR(30)   -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)    -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)   -- 에러 코드
   , IN  I_TERM   	VARCHAR(20)   -- 기간(TERM_DIV, D:접수일자,P:보고일자)
   , IN  I_FDT     	DECIMAL(8,0)  --보고일자 FROM
   , IN  I_TDT     	DECIMAL(8,0)  --보고 일자TO
   , IN  I_HOS   	VARCHAR(5)    -- 병원코드                        
   , IN  I_RECV   	VARCHAR(20)   -- 수신여부
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN    
	-- 이미지 결과지 출력 조회  
	DECLARE V_CNT DECIMAL(5,0);	--  수량 확인
	BEGIN    
	   DECLARE CUR1  CURSOR WITH RETURN FOR
		SELECT 
			TRIM(CHAR(AA.F008COR)) AS F008COR		--   회사코드                            
			,TRIM(CHAR(AA.F008HOS)) AS F008HOS		--   병원코드                            
			,F008DAT		--   접수일자                            
			,TRIM(CHAR(AA.F008DAT)) AS JDT		--   접수일자                                     
			,TRIM(CHAR(AA.F008BDT)) AS BDT		--   접수일자                            
			,AA.F008JNO		--   접수번호                            
			,AA.F008GCD		--   검사코드                            
			,TRIM(CHAR(AA.F008SEQ)) AS F008SEQ	       --    일련번호                            
			,TRIM(CHAR(AA.F008HID)) AS F008HID		--   검체번호                            
			-- 2022.01.06 복호화 제거
			--,ECHELON.PI_DECSN(AA.F008CHT) AS F008CHT		--   챠트번호         
			,AA.F008CHT AS F008CHT		--   챠트번호
			,TRIM(CHAR(AA.F008NAM)) AS F008NAM	--   환자명                               
			,AA.F008AGE AS F008AGE	       --    나이                                   
			,TRIM(CHAR(AA.F008SEX)) AS F008SEX		--   성별                                   
			,TRIM(CHAR(AA.F008FKN)) AS F008FKN	       --    검사명　                            
			,TRIM(CHAR(AA.F008RT1)) AS F008RT1		--   결과　　                            
			,TRIM(CHAR(AA.F008LEN)) AS F008LEN		--   결과길이                            
			,TRIM(CHAR(AA.F008PAN)) AS F008PAN		--   결과판정                            
			,TRIM(CHAR(AA.F008RNO)) AS F008RNO		--   REMARK코드                  
			,AA.F008BDT	       --    보고일자                            
			,AA.F008TIM		--   보고시간                            
			,TRIM(CHAR(AA.F008FHI)) AS F008FHI		--   참고치이력                        
			,TRIM(CHAR(AA.F008LNG)) AS F008LNG		--   참고치언어                        
			,TRIM(CHAR(AA.F008BCD)) AS F008BCD		--   보험코드                            
			,TRIM(CHAR(AA.F008FL1)) AS F008FL1		--   결과형태                            
			,TRIM(CHAR(AA.F008FL2)) AS F008FL2		--   다운여부                            
			,TRIM(CHAR(AA.F008FL3)) AS F008FL3		--   병원검체코드                    
			,TRIM(CHAR(AA.F008FL4)) AS F008FL4		--   타병원검사코드                 
			,TRIM(CHAR(AA.F008FL5)) AS F008FL5		--   PROFILE                           
			,TRIM(CHAR(AA.F008IMG)) AS F008IMG		--   이미지여부                        
			,TRIM(CHAR(AA.F008JN1)) AS F008JN1	       --    주민번호１                        
			,TRIM(CHAR(AA.F008JN2)) AS F008JN2	       --    주민번호２                        
			,TRIM(CHAR(AA.F008CDT)) AS F008CDT		--   처방일자　                        
			,TRIM(CHAR(AA.F008INC)) AS F008INC	       --    진료구분                            
			,TRIM(CHAR(AA.F008LOV)) AS F008LOV	       --    참고치하한                        
			,TRIM(CHAR(AA.F008HIV)) AS F008HIV	       --    참고치상한                        
			,TRIM(CHAR(AA.F008UNT)) AS F008UNT		--   단위                                   
			,TRIM(CHAR(AA.F008CNO)) AS F008CNO	       --    처방번호                            
			,TRIM(CHAR(AA.F008FL6)) AS F008FL6		--   암여부                               
			,TRIM(CHAR(AA.F008FL7)) AS F008FL7		--   FL8                                    
			,TRIM(CHAR(AA.F008FL8)) AS F008FL8		--   FL9                                    
			,TRIM(CHAR(AA.F008FL9)) AS F008FL9		--   기타                                   
		FROM QTEMP. MCI008T@ AA, MCLISDLIB.MC600M@ BB
		   WHERE AA.F008COR = BB.F600COR
		   AND AA.F008DAT = BB.F600DAT
		   AND AA.F008JNO = BB.F600JNO
		   AND AA.F008GCD = BB.F600GCD || BB.F600ACD
		   AND AA.F008COR = I_COR
		   AND( ( I_TERM = 'D' AND AA.F008DAT BETWEEN I_FDT AND I_TDT)--접수일자
			   OR ( I_TERM = 'P' AND AA.F008BDT BETWEEN I_FDT AND I_TDT)--보고일자
			   )
		   AND ( I_HOS = ''  OR AA.F008HOS = I_HOS)	--병원코드
		   AND ( I_RECV = 'A' OR AA.F008FL2 = I_RECV)	-- 접수 상태
		   AND ((BB.F600GCD != '71313' OR BB.F600GCD != '72185')  AND BB.F600SDB != 'STIP') -- B9028 프로파일인 경우는
		   ORDER BY AA.F008DAT, AA.F008JNO, AA.F008GCD;
	   OPEN CUR1;
	END;
    SET O_MSGCOD = '200';
   
   SELECT COUNT(*)
   INTO V_CNT 
   FROM  MCLISDLIB.SMIMGM@        
   WHERE FIMGMCOR = I_COR 
   AND FIMGMHOS = I_HOS
   AND FIMGMJDT =  TO_CHAR(CURRENT TIMESTAMP,'YYYYMMDD')  
   AND FIMGMJTM = TO_CHAR(CURRENT TIMESTAMP,'HH24MISS')    
   ;
   
	 IF V_CNT  = 0 THEN 
	    --  FIMGMFL1 조회 일자 기준 D:접수일자, P보고일자              
	    INSERT INTO MCLISDLIB.SMIMGM@                                
	               (FIMGMCOR, FIMGMHOS, FIMGMJDT, FIMGMJTM,          
	                FIMGMSTS, FIMGMFRD, FIMGMTOD, FIMGMFL1,
	                FIMGMCDT, FIMGMCTM, FIMGMCUR                               
	               )                                                 
	        VALUES(I_COR                                            
	              ,I_HOS                
	              ,TO_CHAR(CURRENT TIMESTAMP,'YYYYMMDD')             
	              ,TO_CHAR(CURRENT TIMESTAMP,'HH24MISS')                                            
	              ,'F'                                        
	              ,I_FDT                                            
	              ,I_TDT      
	              ,( CASE WHEN I_TERM='D' THEN 'F008DAT' ELSE 'F008BDT' END )
	              ,TO_CHAR(CURRENT TIMESTAMP,'YYYYMMDD')             
	              ,TO_CHAR(CURRENT TIMESTAMP,'HH24MISS')             
	              ,I_UID                                            
		         ) ;        
	END IF;
END



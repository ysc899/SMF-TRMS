DROP PROCEDURE WEBOBJLIB.MWT001RCU;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWT001RCU
(
	  IN I_COR      VARCHAR(3)   	-- COR
    , IN I_UID      VARCHAR(12) 	-- 사용자 ID
    , IN I_IP       VARCHAR(30)  	-- 로그인 IP
    , OUT O_MSGCOD  CHAR(3)       	-- 메세지 코드
    , OUT O_ERRCOD  CHAR(10)    	-- 에러 코드
    , IN I_GCD     	VARCHAR(5)		-- 검사코드
    , IN I_SAV     	VARCHAR(20)		-- 보존방법
    , IN I_REF     	VARCHAR(4000)	-- 참고치
    , IN I_CONT    	VARCHAR(10000) 	-- 임상정보
    , IN I_ETC     	VARCHAR(10000) 	-- 주의사항
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN	
	
	/********* 수정하고자 하는 검사코드의 '보존방법/참고치/임상정보/주의사항' 정보가 존재하는지 확인 *********/
   	DECLARE V_GCD_YN  VARCHAR(20);
	
    SELECT
        CASE WHEN count(T001TCD) <= 0 THEN 'N' ELSE 'Y' END INTO V_GCD_YN
    FROM WEBDBLIB.MWT001M@
    WHERE T001COR = I_COR
    AND T001TCD = I_GCD;
	
    IF V_GCD_YN = 'N' THEN /********* 검사코드가 존재하지 않는경우 *********/
    	SET O_MSGCOD = '200';
		INSERT INTO WEBDBLIB.MWT001M@                                
	               (T001COR, T001TCD, T001SAV, 
	                T001DAY, T001CONT, T001ETC, 
	                T001REF, T001FLG, T001LFLG, 
	                T001CUR, T001CDT, T001CTM, T001CIP                               
	               )                                                 
        VALUES(I_COR                                            
              ,I_GCD                
              ,I_SAV             
              ,'' -- 검사마스터에서 가져오도록 변경되어 빈값 처리함.                                            
              ,I_CONT                                       
              ,I_ETC                                            
              ,I_REF
              ,'Y'             
              ,'Y'             
              ,I_UID
              ,TO_CHAR(CURRENT TIMESTAMP,'YYYYMMDD')
              ,TO_CHAR(CURRENT TIMESTAMP,'HH24MISS')
              ,''
	         ) ;
	ELSE /********* 검사코드가 존재하는 경우 *********/
		SET O_MSGCOD = '200';
		UPDATE WEBDBLIB.MWT001M@ SET                                
	           T001SAV = I_SAV
	           ,T001CONT = I_CONT
	           ,T001ETC = I_ETC
	           ,T001REF = I_REF
	    WHERE T001COR = I_COR
	    AND T001TCD = I_GCD;
	END IF;
END




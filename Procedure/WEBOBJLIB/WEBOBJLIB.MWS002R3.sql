DROP PROCEDURE WEBOBJLIB.MWS002R3;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWS002R3
(
   	IN I_COR VARCHAR(3) ,	-- 회사코드 
	IN I_UID VARCHAR(12) , 	-- 사용자ID
	IN I_IP VARCHAR(30) , 	-- 사용자IP
	OUT O_MSGCOD CHAR(3) , 	-- return (메세지코드)
	OUT O_ERRCOD CHAR(10) , -- return (에러코드)
	
	OUT O_UYN CHAR(1) 		-- 구 검사결과관리 홈페이지 사용 여부 (Y:사용, N:미사용)  
	
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
    
    SET O_MSGCOD = '300' ; 
	SET O_ERRCOD = 'E' ;
	SET O_UYN = 'N' ;	
   
	-- 구 검사결과 홈페이지 사용하는지 조회
    SELECT  
    	CASE WHEN S002UYN = 'Y' THEN S002UYN ELSE ' ' END INTO O_UYN
    FROM WEBDBLIB.MWS002M@ 
    WHERE S002COR = I_COR 
    AND S002SCD = I_UID 
    AND S002PSCD = 'OLD_MENU_USE' 
    AND S002UYN = 'Y';

	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';
    
END
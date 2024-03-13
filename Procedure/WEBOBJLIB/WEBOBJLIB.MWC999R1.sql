DROP PROCEDURE WEBOBJLIB.MWC999R1;

CREATE PROCEDURE WEBOBJLIB.MWC999R1
(
	  IN I_COR 		VARCHAR(3) 	-- COR
	, IN I_UID 		VARCHAR(12) -- 사용자 ID
	, IN I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	CHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	CHAR(10)	-- 에러 코드
	, IN I_CD1		CHAR(4)
	, IN I_CD2		CHAR(4)
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN	
	-- Quick Setup  조회
	DECLARE CUR1 CURSOR WITH RETURN FOR	
	SELECT  TRIM(F999CD1) AS F999CD1   
		, TRIM(F999CD2 ) AS F999CD2   
		, TRIM(F999CD3 ) AS F999CD3   
		, TRIM(F999NM1) AS F999NM1 
		, TRIM(F999NM2) AS F999NM2 
		, TRIM(F999NM3) AS F999NM3 
		, TRIM(F999NM4) AS F999NM4 
	FROM MCLISDLIB.MC999D@
	WHERE  F999COR = I_COR
	AND(I_CD1 = '' OR F999CD1 = I_CD1)
	AND(I_CD2 = '' OR F999CD2 = I_CD2)
;
			
	OPEN CUR1;
	SET O_MSGCOD = '200';

END




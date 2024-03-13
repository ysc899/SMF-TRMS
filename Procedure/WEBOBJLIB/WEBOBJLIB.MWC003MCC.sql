DROP PROCEDURE WEBOBJLIB.MWC003MCC;

CREATE PROCEDURE WEBOBJLIB.MWC003MCC
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		VARCHAR(5)		-- 병원 코드				
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	-- SMS 연동관리, 중복 체크
	DECLARE CUR1  CURSOR WITH RETURN FOR
		SELECT C003GCD FROM
		WEBDBLIB."MWC003M@"
		WHERE C003COR = I_COR
		AND C003HOS = I_HOS;
	OPEN CUR1;
		
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

END
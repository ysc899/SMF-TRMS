DROP PROCEDURE WEBOBJLIB.MWC002MU;

CREATE PROCEDURE WEBOBJLIB.MWC002MU
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		CHAR(5)			-- 병원코드
	, IN I_SEQ		DECIMAL			-- 메시지 순번
	, IN I_NAM		VARCHAR(100)	-- 메시지 명
	, IN I_MSG		VARCHAR(4000)	-- 메시지
)
BEGIN
	--SMS 메시지 관리, SMS 메시지 수정
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';
 
	UPDATE WEBDBLIB.MWC002M@
	SET
		 C002NAM = I_NAM		-- 메시지 명
		,C002MSG = I_MSG		-- 메시지
		,C002UUR = I_UID		-- 수정자 ID
		,C002UDT = TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') -- 수정일자
		,C002UTM = TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')		-- 수정시간
		,C002UIP = I_IP		-- 수정자 IP
	WHERE 
		 C002COR = I_COR
		 AND C002HOS = I_HOS
		 AND C002SEQ = I_SEQ;
END
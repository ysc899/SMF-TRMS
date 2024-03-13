DROP PROCEDURE WEBOBJLIB.MWS008MC;

CREATE PROCEDURE WEBOBJLIB.MWS008MC
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		  -- 사용자 IP
	, OUT O_MSGCOD	VARCHAR(3)  -- 메시지 코드
	, OUT O_ERRCOD	VARCHAR(10) -- 에러 코드
	, IN I_MCD		VARCHAR(30)		-- 메뉴 코드
	, IN I_RCD		VARCHAR(30)		-- 검색어 코드
	, IN I_VCD		VARCHAR(30)		-- 검색어 값 코드
)
BEGIN
	-- 메인 퀵셋업, 메뉴별 퀵셋업 등록
	INSERT INTO WEBDBLIB.MWS008M@
	(
		  S008COR
		, S008UID
		, S008MCD
		, S008RCD
		, S008VCD
		, S008CUR
		, S008CDT
		, S008CTM
		, S008CIP
		, S008UUR
		, S008UDT
		, S008UTM
		, S008UIP
	)
	VALUES
	(
		  I_COR
		, I_UID
		, I_MCD
		, I_RCD
		, I_VCD
		, I_UID
		, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
		, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')
		, I_IP
		, ''
		, 0
		, 0
		, ''
	);

	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

END
DROP PROCEDURE WEBOBJLIB.MWC003DC;

CREATE PROCEDURE WEBOBJLIB.MWC003DC
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		CHAR(5)			-- 병원코드
	, IN I_SEQ		DECIMAL(10, 0)	-- 메시지 순번
	, IN I_CHN		VARCHAR(15)		-- 차트 번호
)
BEGIN
	-- SMS 연동 관리, 수진자 정보 추가
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

	INSERT INTO WEBDBLIB.MWC003D@
	(			
		 C003COR		-- 회사코드
		,C003HOS		-- 병원코드
		,C003SEQ		-- 메시지순번
		,C003CHN		-- 차트 번호
		,C003CUR		-- 등록자 ID
		,C003CDT		-- 등록 일자
		,C003CTM		-- 등록 시간
		,C003CIP		-- 등록자 IP
	)
	VALUES(
		I_COR
		, I_HOS
		, I_SEQ
		, I_CHN
		, I_UID
		, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
		, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')
		, I_IP
	);
END
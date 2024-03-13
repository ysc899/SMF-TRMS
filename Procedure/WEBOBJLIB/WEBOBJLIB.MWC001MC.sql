DROP PROCEDURE WEBOBJLIB.MWC001MC;

CREATE PROCEDURE WEBOBJLIB.MWC001MC
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		VARCHAR(5)		-- 병원 코드
	, IN I_CHN		VARCHAR(15)		-- 차트번호
	, IN I_NAM		VARCHAR(100)	-- 수신자 명
	, IN I_HPN		VARCHAR(20)		-- 핸드폰 번호
	, IN I_BDT		VARCHAR(8)		-- 생년월일
	, IN I_SEX		VARCHAR(20)		-- 성별
	, IN I_AGE		DECIMAL(3,0)	-- 나이
)
BEGIN
	-- 수진자 정보 관리, 수진자 정보 추가
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

	INSERT INTO WEBDBLIB.MWC001M@
	(			
		 C001COR		-- 회사 코드
		,C001HOS		-- 병원코드
		,C001CHN		-- 차트 번호
		,C001NAM		-- 수신자 명
		,C001HPN		-- 핸드폰 번호
		,C001BDT		-- 생년월일
		,C001SEX		-- 성별
		,C001AGE		-- 나이
		,C001CUR		-- 등록자 ID
		,C001CDT		-- 등록일자
		,C001CTM		-- 등록시간
		,C001CIP		-- 등록자 IP
		,C001UUR		-- 수정자 ID
		,C001UDT		-- 수정일자
		,C001UTM		-- 수정시간
		,C001UIP		-- 수정자 IP
		)
	VALUES(
		I_COR
		, I_HOS
		, I_CHN
		, I_NAM
		, I_HPN
		, I_BDT
		, I_SEX
		, I_AGE
		, I_UID
		, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
		, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')
		, I_IP
		, ''
		, 0
		, 0
		, ''
	);
END
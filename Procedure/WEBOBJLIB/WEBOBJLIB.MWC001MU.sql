DROP PROCEDURE WEBOBJLIB.MWC001MU;

CREATE PROCEDURE WEBOBJLIB.MWC001MU
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		CHAR(5)		-- 병원 코드
	, IN I_CHN		VARCHAR(15)		-- 차트번호
	, IN I_NAM		VARCHAR(100)	-- 수신자 명
	, IN I_HPN		VARCHAR(20)		-- 핸드폰 번호
	, IN I_BDT		VARCHAR(8)		-- 생년월일
	, IN I_SEX		VARCHAR(20)		-- 성별
	, IN I_AGE		DECIMAL(3,0)	-- 나이
)
BEGIN
	-- 수진자 정보 관리, 수진자 정보 수정
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';
 
	UPDATE WEBDBLIB.MWC001M@
	SET
		 C001NAM = I_NAM		-- 수신자 명
		,C001HPN = I_HPN		-- 핸드폰 번호
		,C001BDT = I_BDT		-- 생년월일
		,C001SEX = I_SEX		-- 성별
		,C001AGE = I_AGE		-- 나이
		,C001UUR = I_UID		-- 수정자 ID
		,C001UDT = TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')		-- 수정일자
		,C001UTM = TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')		-- 수정시간
		,C001UIP = I_IP		-- 수정자 IP
	WHERE 
		 C001COR = I_COR
		 AND C001HOS = I_HOS
		 AND C001CHN = I_CHN;
END
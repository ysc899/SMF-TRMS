DROP PROCEDURE WEBOBJLIB.MWC002MC;

CREATE PROCEDURE WEBOBJLIB.MWC002MC
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		CHAR(5)			-- 병원코드
	, IN I_NAM		VARCHAR(100)	-- 메시지 명
	, IN I_MSG		VARCHAR(4000)	-- 메시지
)
BEGIN
	-- SMS 메시지 관리, SMS 메시지 추가
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

	INSERT INTO WEBDBLIB.MWC002M@
	(			
		 C002COR		-- 회사코드
		,C002HOS		-- 병원코드
		,C002SEQ		-- 메시지순번
		,C002NAM		-- 메시지 명
		,C002MSG		-- 메시지
		,C002DYN		-- 삭제 여부
		,C002CUR		-- 등록자 ID
		,C002CDT		-- 등록 일자
		,C002CTM		-- 등록 시간
		,C002CIP		-- 등록자 IP
		,C002UUR		-- 수정자 ID
		,C002UDT		-- 수정 일자
		,C002UTM		-- 수정 시간
		,C002UIP		-- 수정자 IP
		,C002DUR		-- 삭제자 ID
		,C002DDT 		-- 삭제 일자
		,C002DTM		-- 삭제 시간
		,C002DIP		-- 삭제자 IP
		)
	VALUES(
		I_COR
		, I_HOS
		, ( NEXTVAL FOR WEBDBLIB.MWC002MSEQ )
		, I_NAM
		, I_MSG
		, 'N'
		, I_UID
		, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
		, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')
		, I_IP
		, ''
		, 0
		, 0
		, ''
		, ''
		, 0
		, 0
		, ''
	);
END
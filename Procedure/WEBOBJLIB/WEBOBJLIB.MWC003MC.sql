DROP PROCEDURE WEBOBJLIB.MWC003MC;

CREATE PROCEDURE WEBOBJLIB.MWC003MC
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		CHAR(5)			-- 병원코드
	, IN I_GCD		CHAR(5)			-- 검사항목 코드
	, IN I_MSG		VARCHAR(4000)	-- 메시지
	
)
BEGIN
	-- SMS 연동 관리, 메시지 추가
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

	INSERT INTO WEBDBLIB.MWC003M@
	(			
		 C003COR		-- 회사코드
		,C003HOS		-- 병원코드
		,C003SEQ		-- 메시지순번
		,C003GCD		-- 검사항목 코
		,C003MSG		-- 메시지
		,C003CUR		-- 등록자 ID
		,C003CDT		-- 등록 일자
		,C003CTM		-- 등록 시간
		,C003CIP		-- 등록자 IP
		,C003UUR		-- 수정자 ID
		,C003UDT		-- 수정 일자
		,C003UTM		-- 수정 시간
		,C003UIP		-- 수정자 IP
	)
	VALUES(
		I_COR
		, I_HOS
		, ( NEXTVAL FOR WEBDBLIB.MWC003MSEQ )
		, I_GCD
		, I_MSG
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
DROP PROCEDURE WEBOBJLIB.MWS012C;


CREATE PROCEDURE WEBOBJLIB.MWS012C
(
	  IN  I_COR 	VARCHAR(3) 	-- COR
	, IN  I_UID 	VARCHAR(12) -- 사용자 ID
	, IN  I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)	-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)	-- 에러 코드
	, IN I_HOS		VARCHAR(5)	  --병원코드
	, IN I_RCL		VARCHAR(20)	   --양식지
	, IN I_NSQ 		DECIMAL(3,0)	 --파일명 순번
	, IN I_NCL		VARCHAR(20)	 --파일명 구분
	, IN I_NDV		VARCHAR(20)	  --직접 입력 값
)
BEGIN

	SET O_MSGCOD	=	'200';
	SET O_ERRCOD	=	'';
	-- 병원별 결과지 관리 등록
	INSERT INTO WEBDBLIB.MWS012M@(
		S012COR	--   회사코드
		, S012HOS	--   병원코드
		, S012RCL	--   양식지
		, S012NSQ	--   파일명 순번
		, S012NCL	--  파일명 구분
		, S012NDV	--  직접 입력 값
		, S012CUR	--   등록자 ID
		, S012CDT	--   등록 일자
		, S012CTM	--   등록 시간
		, S012CIP		--   등록자 IP
		, S012UUR	--   수정자 ID
		, S012UDT	--   수정 일자
		, S012UTM	--   수정 시간
		, S012UIP		--   수정자 IP
	)
	VALUES(
		  I_COR	--   회사코드
		, I_HOS	--   병원코드
		, I_RCL	--   양식지
		, I_NSQ	--   파일명 순번
		, I_NCL	--  파일명 구분
		, I_NDV	--  직접 입력 값
		, I_UID
		, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')	-- 등록일자
		, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss') 	-- 등록시간
		, I_IP
		, ''
		, 0
		, 0
		, ''
	);

END



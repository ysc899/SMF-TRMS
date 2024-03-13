DROP PROCEDURE WEBOBJLIB.MWS012U;


CREATE PROCEDURE WEBOBJLIB.MWS012U
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
	-- 병원별 결과지 관리 수정
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';
	UPDATE WEBDBLIB.MWS012M@
	SET S012NCL	=	I_NCL	--양식지
		, S012NDV	=	I_NDV	--결과지 타입
		, S012UUR	=	I_UID	-- 수정자
		,  S012UDT	=	TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')	-- 수정일자
		, S012UTM	=	TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss') 	-- 수정시간
		, S012UIP	=	I_IP		--수정IP
	   WHERE S012COR = I_COR	--COR
	   AND S012HOS = I_HOS	--병원코드
	   AND S012RCL = I_RCL	--양식지
	   AND S012NSQ = I_NSQ	--파일명 순번
	;
END
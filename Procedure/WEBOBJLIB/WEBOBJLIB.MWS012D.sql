DROP PROCEDURE WEBOBJLIB.MWS012D;

CREATE PROCEDURE WEBOBJLIB.MWS012D
(
	  IN  I_COR 	VARCHAR(3) 	-- COR
	, IN  I_UID 	VARCHAR(12) -- 사용자 ID
	, IN  I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)	-- 에러 코드
	, IN I_HOS		VARCHAR(5)	  --병원코드
	, IN I_RCL		VARCHAR(20)	   --양식지
	, IN I_NSQ 		DECIMAL(3,0)	 --파일명 순번
)
BEGIN
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';
	-- 병원별 결과지 관리 삭제
	DELETE FROM WEBDBLIB.MWS012M@
	   WHERE S012COR = I_COR	--COR
	   AND S012HOS = I_HOS	--병원코드
	   AND S012RCL = I_RCL	--양식지
	   AND S012NSQ = I_NSQ	--파일명 순번
	;
END
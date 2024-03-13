DROP PROCEDURE WEBOBJLIB.MWS012R2;

CREATE PROCEDURE WEBOBJLIB.MWS012R2
(
	  IN I_COR 		VARCHAR(3) 	-- COR
	, IN I_UID 		VARCHAR(12) -- 사용자 ID
	, IN I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	CHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	CHAR(10)	-- 에러 코드
	, IN I_HOS		VARCHAR(5)	  --병원코드
	, IN I_RCL		VARCHAR(20)	   --양식지
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	--  병원별 결과지 관리 조회
   DECLARE CUR1  CURSOR WITH RETURN FOR
	SELECT TRIM(S012COR) AS S012COR   --회사코드
		, TRIM(S012HOS) AS S012HOS 	--병원코드
		, TRIM(S012RCL) AS S012RCL         -- 양식지
		, S012NSQ 	--파일명 순번
		, TRIM(S012NCL) AS S012NCL       -- 파일명 구분
		, TRIM(S012NDV) AS S012NDV     -- 직접 입력 값
		, TRIM(S012HOS) AS I_HOS 	--병원코드
		, TRIM(S012RCL) AS I_RCL         -- 양식지
		, S012NSQ AS I_NSQ 	--파일명 순번
		, TRIM(S012NCL) AS I_NCL       -- 파일명 구분
		, TRIM(S012NDV) AS I_NDV     -- 직접 입력 값
	FROM WEBDBLIB.MWS012M@
	WHERE S012HOS = I_HOS --- 병원코드
	AND S012RCL = I_RCL  -- 양식지
	ORDER BY S012HOS, S012NSQ;

	SET O_MSGCOD = '200';
   OPEN CUR1;
END
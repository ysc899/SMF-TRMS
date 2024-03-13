DROP PROCEDURE WEBOBJLIB.MWS012R1;

CREATE PROCEDURE WEBOBJLIB.MWS012R1
(
	  IN I_COR 		VARCHAR(3) 	-- COR
	, IN I_UID 		VARCHAR(12) -- 사용자 ID
	, IN I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	CHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	CHAR(10)	-- 에러 코드
	, IN I_PCD	VARCHAR(5)	-- 병원 코드
	, IN I_FNM 	VARCHAR(40) -- 병원명
	, IN I_MBC 	VARCHAR(6) -- 담당자 사원번호
	, IN I_NAM 	VARCHAR(12) -- 담당자 명
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	--  병원별 결과지 관리 조회
   DECLARE CUR1  CURSOR WITH RETURN FOR
	SELECT
		TRIM(F120FNM) AS F120FNM
		,TRIM(F120PCD) AS F120PCD
		,TRIM(F120MBC) AS F120MBC
		,TRIM(F910NAM) AS F910NAM
	FROM MCLISDLIB.MC120M@
	LEFT JOIN  JMTSLIB.MM910M@
			ON F910COR = F120COR
			AND F910SAB   = F120MBC
	WHERE (I_PCD = '' OR  F120PCD  LIKE I_PCD||'%')	--병원코드
	AND (I_FNM = '' OR  F120FNM  LIKE '%'|| I_FNM||'%')	--병원명
	AND (I_MBC = '' OR   F120MBC LIKE I_MBC||'%')	--담당자 사원번호
	AND (I_NAM = '' OR   F910NAM LIKE '%'|| I_NAM||'%')	--담당자 명
	ORDER BY F120PCD,F120MBC
;
	SET O_MSGCOD = '200';
   OPEN CUR1;
END
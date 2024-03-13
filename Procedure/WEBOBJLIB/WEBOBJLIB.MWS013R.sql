CREATE PROCEDURE WEBOBJLIB.MWS013R (IN I_COR VARCHAR(3)
									  , IN I_UID VARCHAR(12)
									  , IN I_IP  VARCHAR(30)
									  , OUT O_MSGCOD VARCHAR(3)
									  , OUT O_ERRCOD VARCHAR(10)
									  )
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	--도움말 목록 조회
	DECLARE C1 CURSOR WITH RETURN FOR
	SELECT A.S013MCD
		  , B.S001PMCD
		  , B.S001MNM
		  , (SELECT S001MNM FROM WEBDBLIB."MWS001M@" WHERE S001MCD = B.S001PMCD) AS S001PMNM
		  , A.S013CON
		  , A.S013CUR
		  , A.S013CDT
	FROM WEBDBLIB.MWS013M@ A LEFT OUTER JOIN (
	SELECT
			CONNECT_BY_ROOT S001PMCD AS ROOT
			, TRIM ( S001COR ) S001COR
			, TRIM ( S001MCD ) S001MCD
			, TRIM ( S001PMCD ) AS S001PMCD
			, TRIM ( S001MNM ) AS S001MNM
			, TRIM ( S001PTH ) AS S001PTH
			, LEVEL AS LEVEL_COUNT
			, S001SEQ
			, TRIM ( S001ICN ) AS S001ICN
			-- , CONNECT_BY_ROOT TREE
			, S001UYN
		FROM WEBDBLIB . MWS001M@ A
		START WITH S001PMCD = 'ROOT'
		CONNECT BY PRIOR S001MCD = S001PMCD --= CASE WHEN  S001MCD 
		ORDER SIBLINGS BY S001SEQ
	) B
	ON A.S013COR = B.S001COR
	AND A.S013MCD = B.S001MCD;
	OPEN C1;
	SET O_ERRCOD = '';
	SET O_MSGCOD = 200;
END
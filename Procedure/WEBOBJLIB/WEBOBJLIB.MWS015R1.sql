CREATE PROCEDURE WEBOBJLIB.MWS015R1(
			               	 IN  I_COR    VARCHAR(3)
			               , IN  I_UID    VARCHAR(12)
			               , IN  I_IP     VARCHAR(30)
			               , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN  I_DCL    VARCHAR(20)
                           , IN  I_TIT    VARCHAR(100)
                           , IN  I_CON    VARCHAR(100)
                           , IN  I_PDNOC  VARCHAR(1)
                           , IN  I_FWDT   DECIMAL(8,0)
                           , IN  I_TWDT   DECIMAL(8,0))
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	--
DECLARE C1 CURSOR WITH RETURN FOR
WITH TEMP ( PATH
, S015SEQ
, S015DCL
, S015PDNO
, S015TIT
, S015CON
, S015CNM
, S015CMA
, S015IYN
, S015RCT
, S015DYN
, S015CUR
, S015CDT
, S015CTM
, S015CIP
, S015UUR
, S015UDT
, S015UTM
, S015UIP
, S015DUR
, S015DDT
, S015DTM
, S015DIP
, S015PDNOCF
, DNCHK ) AS (
SELECT
DISTINCT CAST ( CONCAT ( REPEAT ( '0' , 11 - ( LENGTH ( TRIM ( CHAR ( S015SEQ ) ) ) ) ) , CHAR ( S015SEQ ) ) AS VARCHAR ( 3000 ) ) AS PATH
, S015SEQ
, S015DCL
, S015PDNO
, S015TIT
, S015CON
, S015CNM
, S015CMA
, S015IYN
, S015RCT
, S015DYN
, S015CUR
, S015CDT
, S015CTM
, S015CIP
, S015UUR
, S015UDT
, S015UTM
, S015UIP
, S015DUR
, S015DDT
, S015DTM
, S015DIP
, ( SELECT CASE WHEN COUNT ( * ) > 0 THEN 'C'
	ELSE (
			CASE WHEN A.S015PDNO = 0 THEN 'S'
			ELSE 'D'
			END
	)
END
FROM WEBDBLIB . MWS015M@
WHERE S015PDNO = A . S015SEQ
AND S015DYN = 'N' ) S015PDNOCF
, ( SELECT CASE WHEN COUNT ( * ) > 0 THEN 'Y'
ELSE 'N'
END
FROM WEBDBLIB . MWS015M@
WHERE S015PDNO = A . S015SEQ
AND S015DYN = 'N' ) DNCHK
FROM WEBDBLIB . MWS015M@ A
WHERE A . S015DCL LIKE '%' || I_DCL || '%'
AND A . S015TIT LIKE '%' || I_TIT || '%'
AND A . S015CON LIKE '%' || I_CON || '%'
AND A . S015CDT BETWEEN I_FWDT AND I_TWDT
AND A . S015CUR = I_UID
AND A . S015PDNO = '0'
AND S015DYN = 'N'
UNION ALL
SELECT
T . PATH || '.' || CONCAT ( REPEAT ( '0' , 11 - ( LENGTH ( TRIM ( CHAR ( C . S015SEQ ) ) ) ) ) , CHAR ( C . S015SEQ ) ) AS PATH
, C . S015SEQ
, C . S015DCL
, C . S015PDNO
, C . S015TIT
, C . S015CON
, C . S015CNM
, C . S015CMA
, C . S015IYN
, C . S015RCT
, C . S015DYN
, C . S015CUR
, CHAR (C . S015CDT) AS S015CDT 
, C . S015CTM
, C . S015CIP
, C . S015UUR
, C . S015UDT
, C . S015UTM
, C . S015UIP
, C . S015DUR
, C . S015DDT
, C . S015DTM
, C . S015DIP
, ( SELECT CASE WHEN COUNT ( * ) > 0 THEN 'C'
	ELSE (
			CASE WHEN C.S015PDNO = 0 THEN 'S'
			ELSE 'D'
			END
	)
END
FROM WEBDBLIB . MWS015M@
WHERE S015PDNO = C . S015SEQ
AND S015DYN = 'N' ) S015PDNOCF
, ( SELECT CASE WHEN COUNT ( * ) > 0 THEN 'Y'
ELSE 'N'
END
FROM WEBDBLIB . MWS015M@
WHERE S015PDNO = C . S015SEQ
AND S015DYN = 'N' ) DNCHK
FROM ( SELECT * FROM WEBDBLIB . MWS015M@
WHERE S015DCL LIKE '%' || I_DCL || '%'
AND S015TIT LIKE '%' || I_TIT || '%'
AND S015CON LIKE '%' || I_CON || '%'
AND S015DYN = 'N'
AND S015CDT BETWEEN I_FWDT AND I_TWDT ) C ,
TEMP T
WHERE
C . S015PDNO = T . S015SEQ )
SELECT
PATH TREE
, S015SEQ
, S015DCL
, S015PDNO
, S015TIT
, S015CON
, S015CNM
, S015CMA
, S015IYN
, S015RCT
, S015DYN
, S015CUR
, CHAR(S015CDT) AS S015CDT 
, S015CTM
, S015CIP
, S015UUR
, S015UDT
, S015UTM
, S015UIP
, S015DUR
, S015DDT
, S015DTM
, S015DIP
, S015PDNOCF
, DNCHK
, CASE WHEN S015PDNOCF = 'C' THEN '답변완료'
	   WHEN S015PDNOCF = 'S' THEN '대기'
	   WHEN S015PDNOCF = 'D' THEN '답변'
  END S015PDNOC
FROM
TEMP
WHERE S015PDNOCF LIKE '%' || I_PDNOC || '%'
AND S015DYN = 'N'
ORDER BY PATH ;
SET O_MSGCOD = 200 ;
OPEN C1 ;
END
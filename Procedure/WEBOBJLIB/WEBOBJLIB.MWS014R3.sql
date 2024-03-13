CREATE PROCEDURE WEBOBJLIB.MWS014R3(
                             IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN  I_DCL    VARCHAR(20)
                           , IN  I_TIT    VARCHAR(100)
                           , IN  I_CON    VARCHAR(100)
                           , IN  I_CNM    VARCHAR(100)
                           , IN  I_FWDT   DECIMAL(8,0)
                           , IN  I_TWDT   DECIMAL(8,0)
                           )
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
  DECLARE C1 CURSOR WITH RETURN FOR
    SELECT
    	  (SELECT CASE WHEN COUNT(S014FSNM) > 0 THEN 'Y'
                     ELSE 'N'
                     END S014FYN FROM WEBDBLIB . "MWS014D@" WHERE S014SEQ = A.S014SEQ AND S014COR = I_COR
                  )AS S014FYN
        , (SELECT CASE WHEN COUNT(S014RUR) > 0 THEN 'Y'
                     ELSE 'N'
                     END S014RYN FROM WEBDBLIB . "MWS014R@" WHERE S014SEQ = A.S014SEQ AND S014RUR = I_UID AND S014COR = I_COR
                  )AS S014RYN
        , A.S014COR
        , A.S014SEQ
        , CHAR (A.S014WDT) AS S014WDT 
        , A.S014DNO
        , A.S014DCL
        , (SELECT S002CNM FROM WEBDBLIB."MWS002M@" WHERE S002SCD = A.S014DCL AND S002COR = I_COR) AS S002CNM
        , A.S014SDV
        , A.S014PYN
        , A.S014PFR
        , A.S014PTO
        , A.S014LCO
        , A.S014TCO
        , A.S014WID
        , A.S014HIT
        , A.S014TIT
        , A.S014CNM
    FROM WEBDBLIB . "MWS014M@" A
    WHERE A.S014DCL LIKE '%'||I_DCL||'%'
    AND   A.S014TIT LIKE '%'||I_TIT||'%'
    AND   A.S014CON LIKE '%'||I_CON||'%'
    AND   A.S014CNM LIKE '%'||I_CNM||'%'
    AND   A.S014WDT BETWEEN I_FWDT AND I_TWDT
    AND   A.S014DYN = 'N'
    ORDER BY A.S014WDT DESC, A.S014DNO DESC;
SET O_MSGCOD = 200 ;
OPEN C1 ;
END
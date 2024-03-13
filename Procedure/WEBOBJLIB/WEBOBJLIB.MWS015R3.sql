CREATE PROCEDURE WEBOBJLIB.MWS015R3 (
                    IN  I_COR    VARCHAR(3)
                    , IN  I_UID    VARCHAR(12)
                    , IN  I_IP   VARCHAR(30)
                    , OUT O_MSGCOD VARCHAR(3)
                    , OUT O_ERRCOD VARCHAR(10)
                    , IN  I_SEQ    DECIMAL(11,0))
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
   DECLARE C1 CURSOR WITH RETURN FOR
      SELECT A.S015COR
     , A.S015SEQ
     , A.S015DCL
     , (SELECT S002CNM FROM WEBDBLIB."MWS002M@" WHERE S002SCD = A.S015DCL AND S002PSCD = 'SEARCH_ITEM') S002CNM
     , A.S015PDNO
     , A.S015TIT
     , A.S015CON
     , A.S015CNM
     , A.S015CMA
     , A.S015IYN
     , A.S015RCT
     , A.S015CUR
     , ( SELECT CASE WHEN COUNT ( * ) > 0 THEN 'Y'
                        ELSE 'N'
                        END
                FROM WEBDBLIB . MWS015M@
                WHERE S015PDNO = A . S015SEQ
                AND S015DYN = 'N') DNCHK
   FROM WEBDBLIB."MWS015M@" A
   WHERE A.S015SEQ = I_SEQ
   AND S015DYN = 'N';
   SET O_MSGCOD = 200;
   OPEN C1;
END
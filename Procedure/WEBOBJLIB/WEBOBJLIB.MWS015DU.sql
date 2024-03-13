
CREATE PROCEDURE WEBOBJLIB.MWS015DU (
                             IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN I_SEQ     DECIMAL(11,0)
                           , IN I_FSQ     DECIMAL(5,0)
                           , IN I_FPT  VARCHAR(1000)
                           , IN I_FNM  VARCHAR(200)
                           , IN I_FSNM VARCHAR(200)
                           )
LANGUAGE SQL
BEGIN
UPDATE WEBDBLIB . MWS015D@
SET S015FPT = I_FPT
, S015FNM = I_FNM
, S015FSNM = I_FSNM
, S015UUR = I_UID
, S015UDT = TO_CHAR ( CURRENT_TIMESTAMP , 'YYYYMMDD' )
, S015UTM = TO_CHAR ( CURRENT_TIMESTAMP , 'HH24MISS' )
, S015UIP = I_IP
WHERE S015SEQ = I_SEQ
AND	S015FSQ = I_FSQ ;
SET O_MSGCOD = 200 ;
END 
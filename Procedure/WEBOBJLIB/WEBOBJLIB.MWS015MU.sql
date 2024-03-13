DROP PROCEDURE  WEBOBJLIB.MWS015MU


CREATE PROCEDURE WEBOBJLIB.MWS015MU (IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN I_SEQ DECIMAL(11,0)
                           , IN I_DCL VARCHAR(100)
                           , IN I_PDNO DECIMAL(11,0)
                           , IN I_TIT VARCHAR(500)
                           , IN I_CON CLOB
                           , IN I_CNM VARCHAR(100)
                           , IN I_CMA VARCHAR(200)
                           , IN I_IYN VARCHAR(1)
                           )
LANGUAGE SQL
BEGIN
   UPDATE WEBDBLIB.MWS015M@
   SET S015DCL = I_DCL
      , S015PDNO = I_PDNO
      , S015TIT = I_TIT
      , S015CON = I_CON
      , S015CNM = I_CNM
      , S015CMA = I_CMA
      , S015IYN = I_IYN
      , S015UUR = I_UID
      , S015UDT = TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
      , S015UTM = TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')
      , S015UIP = I_IP
   WHERE S015SEQ = I_SEQ;
END



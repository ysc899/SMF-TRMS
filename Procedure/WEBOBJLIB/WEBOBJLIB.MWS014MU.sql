drop PROCEDURE WEBOBJLIB.MWS014MU;



CREATE PROCEDURE WEBOBJLIB.MWS014MU(
                             IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN I_SEQ     DECIMAL(11,0)
                           , IN I_CNM     VARCHAR(100)
                           , IN I_DNO     VARCHAR(50)
                           , IN I_DCL     VARCHAR(100)
                           , IN I_SDV     VARCHAR(20)
                           , IN I_PYN     VARCHAR(1)
                           , IN I_PFR     DECIMAL(8,0)
                           , IN I_PTO     DECIMAL(8,0)
                           , IN I_LCO     DECIMAL(5,0)
                           , IN I_TCO     DECIMAL(5,0)
                           , IN I_TIT     VARCHAR(500)
                           , IN I_CON     CLOB
                           )
LANGUAGE SQL
BEGIN
   UPDATE WEBDBLIB.MWS014M@
      SET S014TIT = I_TIT
        , S014CNM = I_CNM
        , S014DNO = I_DNO
        , S014DCL = I_DCL
        , S014CON = I_CON
        , S014SDV = I_SDV
        , S014PYN = I_PYN
        , S014PFR = I_PFR
        , S014PTO = I_PTO
        , S014LCO = I_LCO
        , S014TCO = I_TCO
        , S014UUR = I_UID
        , S014UDT = TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
        , S014UTM = TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')
        , S014UIP = I_IP
   WHERE S014SEQ = I_SEQ;
   SET O_MSGCOD = 200 ;
END
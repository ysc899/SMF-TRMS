CREATE PROCEDURE WEBOBJLIB.MWS014DU (
                             IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN I_SEQ     DECIMAL(11,0)
                           , IN I_FPT  VARCHAR(1000)
                           , IN I_FNM  VARCHAR(200)
                           , IN I_FSNM VARCHAR(200)
                           )
LANGUAGE SQL
BEGIN
   UPDATE WEBDBLIB.MWS014D@
      SET S014FPT  = I_FPT
        , S014FNM  = I_FNM
        , S014FSNM = I_FSNM
        , S014UUR  = I_UID
        , S014UDT  = TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
        , S014UTM  = TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')
        , S014UIP  = I_IP
   WHERE  S014SEQ  = I_SEQ;
  SET O_MSGCOD = 200 ;
END
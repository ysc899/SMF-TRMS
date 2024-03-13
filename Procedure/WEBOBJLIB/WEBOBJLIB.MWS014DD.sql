CREATE PROCEDURE WEBOBJLIB.MWS014DD (
                      IN  I_COR    VARCHAR(3)
                    , IN  I_UID    VARCHAR(12)
                    , IN  I_IP     VARCHAR(30)
                    , OUT O_MSGCOD VARCHAR(3)
                    , OUT O_ERRCOD VARCHAR(10)
                    , IN  I_SEQ    DECIMAL(11,0))
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
  DELETE FROM WEBDBLIB . MWS014D@
           WHERE S014SEQ = I_SEQ
           AND S014CUR = I_UID;
    SET O_MSGCOD = 200 ;
END
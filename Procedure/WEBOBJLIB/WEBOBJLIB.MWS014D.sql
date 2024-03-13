CREATE PROCEDURE WEBOBJLIB.MWS014D (
                      IN  I_COR  VARCHAR(3)
                    , IN  I_UID  VARCHAR(12)
                    , IN  I_IP  VARCHAR(30)
                    , OUT O_MSGCOD VARCHAR(3)
                    , OUT O_ERRCOD VARCHAR(10)
                    , IN  I_SEQ DECIMAL(11,0))
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
  UPDATE WEBDBLIB . MWS014M@
  	SET
  		S014DYN = 'Y'
      WHERE S014SEQ = I_SEQ;
    SET O_MSGCOD = 200 ;
END
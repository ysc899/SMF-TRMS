CREATE PROCEDURE WEBOBJLIB.MWS014RC (
                             IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN  I_SEQ    DECIMAL(11,0)
                           , IN  I_RUR   VARCHAR(12)
                           
                           )
BEGIN
INSERT INTO WEBDBLIB . MWS014R@ (
                                    S014COR
                                  , S014SEQ
                                  , S014RUR
                                  , S014RDT
                                  , S014RTM
                                  , S014RIP
                               	  )
VALUES (
						        I_COR
						      , I_SEQ
						      , I_RUR
						      , TO_CHAR ( CURRENT_TIMESTAMP , 'YYYYMMDD' )
						      , TO_CHAR ( CURRENT_TIMESTAMP , 'HH24MISS' )
						      , I_IP
						      ) ;
            SET O_MSGCOD = 200 ;
END
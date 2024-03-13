CREATE PROCEDURE WEBOBJLIB.MWS014DC (
                             IN  I_COR    VARCHAR(3)
                           , IN  I_UID    VARCHAR(12)
                           , IN  I_IP     VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN  I_SEQ    DECIMAL(11,0)
                           , IN  I_FSQ    DECIMAL(3,0)
                           , IN  I_FPT    VARCHAR(1000)
                           , IN  I_FNM    VARCHAR(200)
                           , IN  I_FSNM   VARCHAR(200)
                           
                           )
BEGIN
INSERT INTO WEBDBLIB . MWS014D@ (
                                    S014COR
                                  , S014SEQ
                                  , S014FSQ
                                  , S014FPT
                                  , S014FNM
                                  , S014FSNM
                                  , S014DYN
                                  , S014CUR
                                  , S014CDT
                                  , S014CTM
                                  , S014CIP
                                  , S014UUR
                                  , S014UDT
                                  , S014UTM
                                  , S014UIP
                                  , S014DUR
                                  , S014DDT
                                  , S014DTM
                                  , S014DIP
                                )
                                VALUES (
                                            I_COR
                                          , I_SEQ
                                          , I_FSQ
                                          , I_FPT
                                          , I_FNM
                                          , I_FSNM
                                          , 'N'
                                          , I_UID
                                          , TO_CHAR ( CURRENT_TIMESTAMP , 'YYYYMMDD' )
                                          , TO_CHAR ( CURRENT_TIMESTAMP , 'HH24MISS' )
                                          , I_IP
                                          , ''
                                          , 0
                                          , 0
                                          , ''
                                          , ''
                                          , 0
                                          , 0
                                          , ''
                                          ) ;
            SET O_MSGCOD = 200 ;
END
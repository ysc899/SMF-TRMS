CREATE PROCEDURE WEBOBJLIB.MWS015DC (
                             IN I_COR  VARCHAR(3)
                           , IN I_SEQ  DECIMAL(11,0)
                           , IN I_FSQ  DECIMAL(3,0)
                           , IN I_FPT  VARCHAR(1000)
                           , IN I_FNM  VARCHAR(200)
                           , IN I_FSNM VARCHAR(200)
                           , IN I_UID  VARCHAR(12)
                           , IN I_IP  VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           )
BEGIN
INSERT INTO WEBDBLIB . MWS015D@ (
                                    S015COR
                                  , S015SEQ
                                  , S015FSQ
                                  , S015FPT
                                  , S015FNM
                                  , S015FSNM
                                  , S015DYN
                                  , S015CUR
                                  , S015CDT
                                  , S015CTM
                                  , S015CIP
                                  , S015UUR
                                  , S015UDT
                                  , S015UTM
                                  , S015UIP
                                  , S015DUR
                                  , S015DDT
                                  , S015DTM
                                  , S015DIP
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
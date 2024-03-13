CREATE PROCEDURE WEBOBJLIB.MWS014MC (
                             IN I_COR     VARCHAR(3)
                           , IN I_UID     VARCHAR(12)
                           , IN I_IP      VARCHAR(30)
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
BEGIN
INSERT INTO WEBDBLIB . MWS014M@ (
                                    S014COR
                                  , S014SEQ
                                  , S014WDT
                                  , S014CNM
                                  , S014DNO
                                  , S014DCL
                                  , S014SDV
                                  , S014PYN
                                  , S014PFR
                                  , S014PTO
                                  , S014LCO
                                  , S014TCO
                                  , S014TIT
                                  , S014CON
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
                                          , TO_CHAR ( CURRENT_TIMESTAMP , 'YYYYMMDD' )
                                          , I_CNM
                                          , I_DNO
                                          , I_DCL
                                          , I_SDV
                                          , I_PYN
                                          , I_PFR
                                          , I_PTO
                                          , I_LCO
                                          , I_TCO
                                          , I_TIT
                                          , I_CON
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
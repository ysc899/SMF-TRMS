CREATE PROCEDURE WEBOBJLIB.MWS015MC (
                             IN I_COR     VARCHAR(3)
                           , IN I_UID     VARCHAR(12)
                           , IN I_IP      VARCHAR(30)
                           , OUT O_MSGCOD VARCHAR(3)
                           , OUT O_ERRCOD VARCHAR(10)
                           , IN I_SEQ     DECIMAL(11,0)
                           , IN I_DCL     VARCHAR(100)
                           , IN I_PDNO    VARCHAR(13)
                           , IN I_TIT     VARCHAR(500)
                           , IN I_CON     CLOB
                           , IN I_CNM     VARCHAR(100)
                           , IN I_CMA     VARCHAR(200)
                           , IN I_IYN     VARCHAR(1)
                           , IN I_RCT     DECIMAL(7,0)
                           )
BEGIN
INSERT INTO WEBDBLIB . MWS015M@ ( S015COR
, S015SEQ
, S015DCL
, S015PDNO
, S015TIT
, S015CON
, S015CNM
, S015CMA
, S015IYN
, S015RCT
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
, S015DIP )
VALUES (
I_COR
, I_SEQ
, I_DCL
, I_PDNO
, I_TIT
, I_CON
, I_CNM
, I_CMA
, I_IYN
, I_RCT
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
, '' ) ;
SET O_MSGCOD = 200 ;
END
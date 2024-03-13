CREATE PROCEDURE WEBOBJLIB.MWS014R2 (
                        IN  I_COR    VARCHAR(3)
                      , IN  I_UID    VARCHAR(12)
                      , IN  I_IP     VARCHAR(30)
                      , OUT O_MSGCOD VARCHAR(3)
                      , OUT O_ERRCOD VARCHAR(10)
                      , IN  I_SEQ    DECIMAL(11,0))
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
   DECLARE C1 CURSOR WITH RETURN FOR
   SELECT 
       A.S014COR        
     , A.S014SEQ        
     , A.S014WDT        
     , A.S014CNM        
     , A.S014DNO        
     , A.S014DCL        
     , (SELECT S002CNM 
        FROM WEBDBLIB."MWS002M@" 
        WHERE S002SCD = A.S014DCL 
        AND S002PSCD = 'NOTICE_KIND'
        AND S002COR = I_COR
        ) S002DCL       
     , A.S014SDV        
     , (SELECT S002CNM 
        FROM WEBDBLIB."MWS002M@" 
        WHERE S002SCD = A.S014SDV 
        AND S002PSCD = 'SYSTEM_DIV'
        AND S002COR = I_COR
        ) S002SDV       
     , A.S014PYN        
     , (SELECT S002CNM 
        FROM WEBDBLIB."MWS002M@" 
        WHERE S002SCD = A.S014PYN 
        AND S002PSCD = 'MAIN_POP_YN'
        AND S002COR = I_COR
        ) S002PYN       
     , (SELECT CASE WHEN COUNT(S014RUR) > 0 THEN '열람'
                     ELSE '미열람'
                     END S014RYN FROM WEBDBLIB . "MWS014R@" 
                     WHERE S014SEQ = A.S014SEQ 
                     AND S014RUR = I_UID
                     AND S014COR = I_COR
       )AS S014RYN
     , A.S014PFR        
     , A.S014PTO        
     , A.S014LCO        
     , A.S014TCO        
     , A.S014TIT        
     , A.S014CON        
     , A.S014CUR        
   FROM WEBDBLIB."MWS014M@" A
   WHERE A.S014SEQ = I_SEQ;
   SET O_MSGCOD = 200;
   OPEN C1;
END
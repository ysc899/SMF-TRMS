DROP PROCEDURE WEBOBJLIB.MWS007MR2;

CREATE PROCEDURE WEBOBJLIB.MWS007MR2
(
      IN I_COR         VARCHAR(3)      	-- COR   
    , IN I_UID         VARCHAR(12)      -- 사용자 ID
    , IN I_IP        VARCHAR(30)        -- 사용자 IP
    , OUT O_MSGCOD    VARCHAR(3)        -- 메세지 코드
    , OUT O_ERRCOD    VARCHAR(10)       -- 에러 코드
    , IN I_MCD        VARCHAR(30)       -- 메뉴 코드
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	-- 메인 퀵셋업, 메큐 코드별 퀵셋업 목록 호출
DECLARE CUR1 CURSOR WITH RETURN FOR 

    SELECT TRIM ( S007MCD ) AS S007MCD
    , TRIM ( S007RCD ) AS S007RCD
    , TRIM ( S007RNM ) AS S007RNM
    , TRIM ( S007RCV ) AS S007RCV
    , IFNULL(TRIM ( S008VCD ),'') AS S008VCD
    , IFNULL((SELECT TRIM(S002RF1) 
            FROM WEBDBLIB.MWS002M@
            WHERE S002COR = S007COR 
            AND S002PSCD= S007RCV
            AND S002SCD = S008VCD
        ),'') S002RF1
    FROM WEBDBLIB.MWS007M@
    LEFT JOIN WEBDBLIB.MWS008M@
                ON S007COR = S008COR
                AND S007MCD = S008MCD
                AND S007RCD = S008RCD
                AND S008UID = I_UID 
    WHERE S007COR = I_COR
    AND S007MCD = I_MCD
    ORDER BY S007RCD ;
    
    OPEN CUR1; 
    
    SET O_MSGCOD = '200';
    SET O_ERRCOD = '';

END
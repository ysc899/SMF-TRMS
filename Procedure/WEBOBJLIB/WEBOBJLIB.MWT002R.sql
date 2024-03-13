DROP PROCEDURE WEBOBJLIB.MWT002R;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWT002R
(
      IN I_COR      VARCHAR(3)   -- COR
    , IN I_UID      VARCHAR(12) -- 사용자 ID
    , IN I_IP       VARCHAR(30)  -- 로그인 IP
    , OUT O_MSGCOD  VARCHAR(3)       -- 메세지 코드
    , OUT O_ERRCOD  VARCHAR(10)    	-- 에러 코드
    , IN I_TCD   	VARCHAR(20) 	-- 병원검사코드
    , IN I_GAD    	VARCHAR(5000)    -- 검사항목
    , IN I_HOS      VARCHAR(5)    -- 병원코드
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
    -- 검사항목 매핑 조회
    DECLARE CUR1  CURSOR WITH RETURN FOR 
    WITH TESTITEM AS (    SELECT 
        	TRIM(MC33.F033COR) AS F033COR	--COR
        	, TRIM(MC33.F033HOS) AS F033HOS	--병원코드
        	, TRIM(MC33.F033TCD) AS F033TCD	--병원 검사코드
        	, TRIM(MC33.F033NCD) AS F033NCD	--씨젠검사코드
            , CASE WHEN mc010.f010cor IS NOT NULL THEN TRIM(mc010.F010COR) 
            	   WHEN mc011.f011cor IS NOT NULL THEN TRIM(mc011.F011COR) 
           	  END AS F010COR  -- COR
            , CASE WHEN mc010.f010cor IS NOT NULL THEN TRIM(mc010.F010GCD) 
             	   WHEN mc011.f011cor IS NOT NULL THEN TRIM(mc011.f011gcd) 
              END AS F010GCD  -- 검사코드
            , CASE WHEN mc010.f010cor IS NOT NULL THEN TRIM(mc010.F010FKN) 
            	   WHEN mc011.f011cor IS NOT NULL THEN trim(mc011.F011FKN) 
           	  END AS F010FKN  -- 검사명(한글)
--           	, CONCAT(mc011.F011GCD,mc011.F011ACD)
        FROM   MCLISDLIB.MC033M@ MC33
        LEFT JOIN MCLISDLIB.MC010M@ MC010
        	ON MC33.F033COR = MC010.F010COR
        	AND MC33.F033NCD = MC010.F010GCD
			AND MC010.F010STS = 'Y'
			AND MC010.F010ACH = 'Y'
		LEFT JOIN MCLISDLIB.MC010M@ for010
			ON MC33.F033COR = for010.F010COR
			AND for010.F010STS = 'Y'
         	AND for010.F010ACH = 'Y'
         	AND SUBSTRING(MC33.F033NCD,1,5) = for010.f010gcd
        LEFT JOIN MCLISDLIB.mc011m@ mc011
			ON MC33.F033COR = MC011.F011COR
         	AND MC33.F033NCD = CONCAT(mc011.F011GCD,mc011.F011ACD)
			AND mc011.f011gcd = for010.f010gcd
        WHERE MC33.F033COR  = I_COR
        AND  MC33.F033STS = 'Y'
        AND (I_TCD = '' OR F033TCD LIKE I_TCD||'%')
        AND (I_HOS = '' OR F033HOS = I_HOS)
        AND (I_GAD = '' OR SUBSTRING(MC33.F033NCD,1,5) IN (
			  SELECT SUBSTR(NAME, 1, INSTR(NAME, '|') - 1) DWARF
			 FROM (
			 	SELECT N,  SUBSTR(VAL, 1 + INSTR(VAL, '|', 1, N)) NAME
			      FROM (  
			     	 	SELECT ROW_NUMBER() OVER() AS N, LIST.VAL
			            FROM  (SELECT I_GAD VAL
			                   FROM SYSIBM.SYSDUMMY1) LIST      
			            CONNECT BY LEVEL < 
				           ( LENGTH(LIST.VAL) 
				           		- LENGTH(REPLACE(LIST.VAL, '|', ''))
				           	)
			           )AS bb 
			      ) cc
        	)
        )
    ) 
    , F018 AS (
       SELECT F018OCD, F018GCD,F018COR
       FROM (
	    SELECT 
            MAX(F018OCD1)
            ,   MAX(F018OCD2)F018OCD
            , F018GCD ,F018COR
            FROM (  
                SELECT 
                    CASE WHEN 
                        ROW_NUMBER() 
                        OVER(PARTITION BY F018GCD) = 1 
                    THEN TRIM(F018OCD) 
                    END F018OCD1
                 , CASE WHEN 
                    ROW_NUMBER() 
                    OVER(PARTITION BY F018GCD) = 2 
                THEN TRIM(F018OCD)
                END F018OCD2
                , TRIM(F018GCD) AS F018GCD,F018COR
                    FROM MCLISDLIB.MC018M@ A
                    WHERE F018COR = I_COR
                    AND F018OSD =  
                    	(SELECT MAX(F018OSD) 
                         FROM  MCLISDLIB.MC018M@ B
                         WHERE A.F018COR = B.F018COR
                         AND A.F018GCD = B.F018GCD)
                    ) A
                    GROUP BY F018GCD,F018COR
                ) A
            )
    SELECT F018OCD
         , TESTITEM.*
    FROM TESTITEM
	    LEFT JOIN F018 
		    ON F018.F018COR = TESTITEM.F010COR 
		    AND F018.F018GCD = TESTITEM.F010GCD
	ORDER BY F033COR, F033HOS,F033TCD,F010GCD
	;
	SET O_MSGCOD = '200';    
    OPEN CUR1;
END




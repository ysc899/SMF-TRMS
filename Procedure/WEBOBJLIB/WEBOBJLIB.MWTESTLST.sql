DROP PROCEDURE WEBOBJLIB.MWTESTLST;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWTESTLST
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		  -- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)	-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)	-- 에러 코드
	, IN I_GCD		VARCHAR(5)		-- 검사코드
	, IN I_FKN		VARCHAR(30)		-- 검사항목
	, IN I_SOCD     VARCHAR(15)     -- 보험코드
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	
	-- 검사항목 검색 팝업, 검사항목 LIST 호출
	DECLARE CUR1  CURSOR WITH RETURN FOR  
	
	SELECT 
	    TRIM(F010COR   ) F010COR  
	    ,TRIM(F010DOW ) F010DOW 
    	,TRIM(F010BDY ) F010BDY 
	    ,F010GCD  
--	    ,F011ACD  
	    ,TRIM(F010FKN  )F010FKN 
	    ,TRIM(F010SNM) F010SNM   
	    ,TRIM(F010TCD)  F010TCD   
	    ,(SELECT TRIM(MC9.F999NM2 )
	          FROM MCLISDLIB.MC999D@ MC9 
	          WHERE F999COR =I_COR
	          AND F999CD2 = 'SAMP' 
	          AND F999CD3 = F010TCD 
	    ) AS F010TNM					
	    ,TRIM(F010MSC)   F010MSC  
	    ,(
	       SELECT 
	        MAX(F028TXT1) 
	        ||  MAX(F028TXT2) 
	        ||  MAX(F028TXT3)
	     FROM (
	        SELECT CASE WHEN  F028SEQ  = 0 
	                THEN TRIM(F028TXT) 
	                ELSE ''
	                END AS  F028TXT1,
	           CASE WHEN F028SEQ  = 1 
	                THEN TRIM(F028TXT) 
	                ELSE ''
	                END AS  F028TXT2,
	           CASE WHEN F028SEQ  = 2 
	                THEN TRIM(F028TXT) 
	                ELSE ''
	                END  AS F028TXT3
	                ,F028GCD
	         FROM  MCLISDLIB.MC028D@  MC28 
	         WHERE F028COR =I_COR
	      ) AS MC28    
	        WHERE MC28.F028GCD =F010MSC
	      GROUP BY F028GCD
	    ) AS F010MSNM
	    ,TRIM(T001DAY)   T001DAY       
	    ,TRIM(F010EED)   F010EED        
	    ,TRIM(A.F018OCD) AS SOCD				--서울 보험코드
	    ,(SELECT TRIM(F018OCD) FROM MCLISDLIB."MC018M@" A
	            WHERE F018OSD =  (SELECT MAX(F018OSD) 
	                 FROM  MCLISDLIB."MC018M@" B
	                 WHERE A.F018COR = B.F018COR
	                 AND A.F018GCD = B.F018GCD)
	                 AND A.F018COR = MC010.F010COR 
	                 AND A.F018GCD = MC010.F010GCD 
	                 AND  TRIM(F018LMB)   ='6526' )BOCD   
	FROM MCLISDLIB.MC010M@ MC010
	 	LEFT JOIN MCLISDLIB."MC018M@" A ON F018GCD = MC010.F010GCD
 			AND A.F018COR = MC010.F010COR
 			AND A.F018GCD = MC010.F010GCD 
			--AND  TRIM(F018LMB) = ''
            AND F018OSD = (SELECT MAX(F018OSD) 
                 FROM  MCLISDLIB."MC018M@" B
                 WHERE A.F018COR = B.F018COR
                 AND A.F018GCD = B.F018GCD)			--서울 보험코드
	    LEFT JOIN  WEBDBLIB."MWT001M@" MWT001
		ON MC010.F010COR =  MWT001.T001COR 
			AND MC010.F010GCD =  MWT001.T001TCD
			AND T001FLG = 'Y'
			AND T001LFLG = 'Y'
	WHERE F010COR = I_COR 
	AND F010STS = 'Y'
	AND F010ACH = 'Y'
	AND F010GCD LIKE '%' || I_GCD || '%'
	AND (UPPER(F010FKN) LIKE '%' || UPPER(I_FKN) || '%' OR UPPER(F010SNM) LIKE '%' || UPPER(I_FKN) || '%')
	--AND UPPER(A.F018OCD) LIKE '%' || UPPER(:I_SOCD) || '%';
	AND (UPPER(COALESCE(A.F018OCD,'')) LIKE '%' || CASE WHEN UPPER(I_SOCD) = '' THEN UPPER(COALESCE(A.F018OCD,'')) ELSE UPPER(I_SOCD) END || '%');
		
	OPEN CUR1;
	
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

END
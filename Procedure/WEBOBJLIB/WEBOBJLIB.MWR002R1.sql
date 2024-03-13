DROP PROCEDURE WEBOBJLIB.MWR002R1;

CREATE PROCEDURE WEBOBJLIB.MWR002R1
(
      IN I_COR         VARCHAR(3)     -- COR
    , IN I_UID         VARCHAR(12) -- 사용자 ID
    , IN I_IP          VARCHAR(30)    -- 로그인 IP
    , OUT O_MSGCOD    CHAR(3)        -- 메세지 코드
    , OUT O_ERRCOD    CHAR(10)    -- 에러 코드
    , IN   I_HOS    VARCHAR(5)   --병원코드
    , IN   I_FDT     DECIMAL(8,0)   --업로드일자 FROM
    , IN   I_TDT     DECIMAL(8,0)   --업로드일자 TO
    , IN   I_RCP    VARCHAR(20)   --접수여부
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN    
     --  OCS 접수 파일 정보
    IF I_RCP = 'N' THEN 
        BEGIN
           DECLARE CUR1  CURSOR WITH RETURN FOR  
                SELECT R002COR
                    , R002HOS
               		, CASE WHEN ( SELECT COUNT(*) 
	               			FROM WEBDBLIB.MWR002D@ D  
			                WHERE   M.R002COR = D.R002COR 
			                AND  M.R002HOS = D.R002HOS 
			                AND  M.R002UPDT = D.R002UPDT 
			                AND  M.R002FSQ = D.R002FSQ
							AND R002DAT > 0
							AND R002JNO > 0
							AND R002GCD !='') > 0 
						THEN 'Y' 
						ELSE 'N' 
						END AS RCP_YN
                    , LPAD(R002UPDT,8,'0')|| LPAD(R002CTM,6,'0')  GRIDDAT
                    , R002UPDT
                    , R002FSQ
                    , TRIM(R002FPT) AS R002FPT
                    , TRIM(R002FNM) AS R002FNM
                    , TRIM(R002FSNM) AS R002FSNM
                    , 'Y' AS DOWN
                    , CASE WHEN R002DUR = '' THEN 'Y' ELSE 'N' END AS UPSTS
                FROM WEBDBLIB.MWR002M@ M
                WHERE R002COR = I_COR 
                AND R002HOS = I_HOS
                AND R002DYN = 'N'
                AND R002UPDT BETWEEN I_FDT AND I_TDT
                AND NOT EXISTS (
                    SELECT 1  
                    FROM WEBDBLIB.MWR002D@ D
                    WHERE M.R002COR = D.R002COR
                    AND D.R002HOS = M.R002HOS
                    AND D.R002UPDT = M.R002UPDT
                    AND D.R002FSQ = M.R002FSQ
                    AND D.R002COR = I_COR 
                    AND D.R002UPDT BETWEEN I_FDT AND I_TDT
					AND R002DAT > 0
					AND R002JNO > 0
					AND R002GCD !=''
                )
                ORDER BY R002UPDT DESC,R002FSQ DESC;
        
            OPEN CUR1;
        
        END;
        SET O_MSGCOD = '200';
    ELSE
        BEGIN
           DECLARE CUR1  CURSOR WITH RETURN FOR  
            
                SELECT R002COR
                    , R002HOS
               		, CASE WHEN ( SELECT COUNT(*) 
	               			FROM WEBDBLIB.MWR002D@ D  
			                WHERE   M.R002COR = D.R002COR 
			                AND  M.R002HOS = D.R002HOS 
			                AND  M.R002UPDT = D.R002UPDT 
			                AND  M.R002FSQ = D.R002FSQ
							AND R002DAT > 0
							AND R002JNO > 0
							AND R002GCD !='') > 0 
						THEN 'Y' 
						ELSE 'N' 
						END AS RCP_YN
                    , LPAD(R002UPDT,8,'0') ||   LPAD(R002CTM,6,'0')  GRIDDAT
                    , R002UPDT  R002UPDT
                    , R002FSQ
                    , TRIM(R002FPT) AS R002FPT
                    , TRIM(R002FNM) AS R002FNM
                    , TRIM(R002FSNM) AS R002FSNM
                    , 'Y' AS DOWN
                    , CASE WHEN R002DUR = '' THEN 'Y' ELSE 'N' END AS UPSTS
                FROM WEBDBLIB.MWR002M@ M
                WHERE R002COR = I_COR 
                AND R002HOS = I_HOS
                AND R002DYN = 'N'
                AND R002UPDT BETWEEN I_FDT AND I_TDT
                AND (I_RCP = '' OR  EXISTS (
                        SELECT 1  
                        FROM WEBDBLIB.MWR002D@ D
                        WHERE M.R002COR = D.R002COR
                        AND D.R002HOS = M.R002HOS
                        AND D.R002UPDT = M.R002UPDT
                        AND D.R002FSQ = M.R002FSQ
                		AND D.R002HOS = I_HOS
                        AND D.R002COR = I_COR 
                        AND D.R002UPDT BETWEEN I_FDT AND I_TDT
						AND R002DAT > 0
						AND R002JNO > 0
						AND R002GCD !=''
                    )
                )
                ORDER BY R002UPDT DESC,R002FSQ DESC;
        
            OPEN CUR1;
        END;
        SET O_MSGCOD = '200';
    END IF;
END



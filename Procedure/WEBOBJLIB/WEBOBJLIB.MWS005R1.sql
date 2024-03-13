DROP PROCEDURE WEBOBJLIB.MWS005R1;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWS005R1
(
	  IN I_COR 		VARCHAR(3) 	-- COR
	, IN I_UID 		VARCHAR(12) -- 사용자 ID
	, IN I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	CHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	CHAR(10)	-- 에러 코드
	, IN I_SERID 	VARCHAR(12) -- 조회 ID
	, IN I_SERNM	VARCHAR(40)	-- 병원사용자명
	, IN I_SERHOS 	VARCHAR(5) -- 병원코드
	, IN I_SERGIC	VARCHAR(8)	-- 요양기관코드
	, IN I_SERDIV 	VARCHAR(1) -- 사용자구분
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN	
	
	-- 담당병원만 검색할지 말지 Flag
   	DECLARE V_HRC  VARCHAR(20) ;
	
	-- 담당병원만 검색할지 말지 Flag
    SELECT
        -- 지점장/지점직원의 경우, 해당 지점이 담당하는 병원만 조회 가능하도록 한다.
        CASE WHEN A.S005AGR IN ('BRC_MNG','HOSP_NMG','TEST') THEN '' ELSE B.S003HRC END INTO V_HRC
    FROM WEBDBLIB.MWS005M@ A , WEBDBLIB.MWS003M@ B
    WHERE A.S005COR = B.S003COR
    AND A.S005AGR = B.S003ACD
    AND A.S005COR = I_COR
    AND A.S005UID = I_UID 
    LIMIT 1;
	
    IF V_HRC = '' THEN 
    	
    	BEGIN
			-- 병원(회원) 관리, 조회 LIST 조회
		   	DECLARE CUR1  CURSOR WITH RETURN FOR  
				SELECT 
					(SELECT TRIM(S002CNM) 
						FROM WEBDBLIB.MWS002M@ 
						WHERE S002COR = I_COR 
						AND S002PSCD='USER_DIV_HOS'  
						AND S002SCD = USERDIV 
						) AS USDNM
					, (SELECT TRIM(S002CNM) 
						FROM WEBDBLIB.MWS002M@ 
						WHERE S002COR = I_COR 
						AND S002PSCD='AUTH'  
						AND S002SCD = S005AGR 
						) AS S005ANM
					, (SELECT TRIM(S002CNM) 
						FROM WEBDBLIB.MWS002M@ 
						WHERE S002COR = I_COR 
						AND S002PSCD='YES_NO'  
						AND S002SCD = S005SYN 
						) AS S005SNM
					, USERTB.*
				FROM(
					/* 2020.05.07 - 병원 조회로 메뉴를 변경하면서
					 *              지점장/지점직원인 경우는 병원사용자만 조회 가능하도록 변경.
					 * SELECT TRIM(USRCOR)AS LOGCOR	 -- COR 
						, 'E' AS USERDIV   -- 사용자 : E, 병원 C
						, TRIM(USRID) AS LOGLID	 -- LOGIN ID
						, TRIM(S005AGR) AS S005AGR	-- 권한
						, '' AS LOGHOS	 -- 병원코드 
						, TRIM(F910NAM) AS F120FNM	 -- 병원명(사원명)
						, '' AS F120FL1	 -- 대표상호명
						, '' AS F120GIC	 -- 요양기관코드
						, TRIM(F910PNU) AS LOGTEL	 -- 전화번호 
						, '' AS LOGEML	 -- 멜주소
						, '' AS LOGPO1	 -- 우편번호1
						, '' AS LOGPO2	 -- 우편번호2
						, '' AS LOGADR	 -- 세부주소 
						, '' AS LOGWNO	 -- 대표자명 
						, '' AS LOGGNO	 -- 면허번호 
						, '' AS LOGSNO	 -- 사업자NO 
						, '' AS F120WNO
						, TRIM(S005DRS) AS S005DRS	--원장출신학교
						, TRIM(S005HPH) AS S005HPH	--병원홈페이지
						, TRIM(S005ITM) AS S005ITM		--전문진료과목
						, TRIM(S005POS) AS S005POS	--우편번호
						, TRIM(S005JAD1) AS S005JAD1	--지번 주소1
						, TRIM(S005JAD2) AS S005JAD2	--지번 주소2
						, TRIM(S005DAD1) AS S005DAD1	--도로명 주소1
						, TRIM(S005DAD2) AS S005DAD2	--도로명 주소2
						, TRIM(S005MMO) AS S005MMO	--메모
						, TRIM(S005MRV) AS S005MRV	--메일수신여부
						, TRIM(S005SYN) AS S005SYN	--SMS사용여부
					FROM JMTSLIB.MMUSER@ A	-- 사원 사용자 정보
						LEFT JOIN  WEBDBLIB.MWS005M@ MSW005 
							ON S005COR = A.USRCOR 
							AND S005UID = USRID 
						, JMTSLIB.MM910M@ B
					WHERE A.USRCOR = I_COR
					AND F910COR = I_COR
					AND B.F910COR = A.USRCOR
					AND USRSAB = F910SAB
					AND F910ODT = 0
					AND (I_SERID = '' OR UPPER( USRID ) LIKE UPPER(I_SERID) ||'%') --조회ID
					AND (I_SERNM = '' OR  F910NAM LIKE '%'||I_SERNM||'%')-- 사용자명
					AND (I_SERHOS = '' OR  S005POS = I_SERHOS )	--병원코드
					AND (I_SERGIC = '' OR  S005POS = I_SERGIC )	--요양기관코드
					UNION */
					SELECT 		
							LOGCOR	 -- COR 
						, 'C' AS USERDIV    -- 사용자 : E, 병원 C
						, TRIM(LOGLID) AS LOGLID      -- LOGIN ID
						, TRIM(S005AGR) AS S005AGR	-- 권한
						, TRIM(LOGHOS) AS LOGHOS    -- 병원코드 
						, TRIM(F120FNM) AS F120FNM   -- 병원명
						, TRIM(F120FL1) AS F120FL1     -- 대표상호명
						, TRIM(F120GIC) AS F120GIC     -- 요양기관코드
						, TRIM(LOGTEL) AS LOGTEL      -- 전화번호 
						, IFNULL(TRIM(REPLACE(LOGEML,'','')),'') AS LOGEML    -- 멜주소
						, TRIM(F120PO1) AS LOGPO1     -- 우편번호1
						, TRIM(F120PO2) AS LOGPO2     -- 우편번호2
						, IFNULL(TRIM(REPLACE(LOGADR,'','')),'') AS LOGADR    -- 세부주소 
						, IFNULL(TRIM(LOGWNO),'') AS LOGWNO  -- 대표자명 
						, TRIM(LOGGNO) AS LOGGNO   -- 면허번호 
						,CASE WHEN TRIM(LOGSNO)  ='' 
							THEN TRIM(F120SNO)  
							ELSE TRIM(LOGSNO)  END  LOGSNO    -- 사업자NO
						, IFNULL(TRIM(REPLACE(F120WNO,'','')),'') AS F120WNO  -- 대표자명 
						, TRIM(S005DRS) AS S005DRS	--원장출신학교
						, TRIM(S005HPH) AS S005HPH	--병원홈페이지
						, TRIM(S005ITM) AS S005ITM		--전문진료과목
						, TRIM(S005POS) AS S005POS	--우편번호
						, TRIM(S005JAD1) AS S005JAD1	--지번 주소1
						, TRIM(S005JAD2) AS S005JAD2	--지번 주소2
						, TRIM(S005DAD1) AS S005DAD1	--도로명 주소1
						, TRIM(S005DAD2) AS S005DAD2	--도로명 주소2
						, TRIM(S005MMO) AS S005MMO	--메모
						, TRIM(S005MRV) AS S005MRV	--메일수신여부
						, TRIM(S005SYN) AS S005SYN	--SMS사용여부
					FROM MCLISDLIB.MCLOGI@ MCLOG	--병원사용자정보
						LEFT JOIN  WEBDBLIB.MWS005M@ MSW005 
							ON MSW005.S005COR = MCLOG.LOGCOR 
							AND S005UID = LOGLID 
						, MCLISDLIB.MC120M@ M120
					WHERE LOGCOR = I_COR
					AND F120COR = I_COR
					AND MCLOG.LOGCOR = M120.F120COR
					AND MCLOG.LOGHOS = M120.F120PCD
					AND F120STS ='Y'
					AND F120DPT = (SELECT F910DPT					-- 사번으로 지점 번호 찾기
									FROM JMTSLIB.MM910M@
									WHERE F910COR = I_COR
									AND  F910SAB = (SELECT F910SAB				-- ID로 사번 가져오기
													FROM JMTSLIB.MMUSER@ B,JMTSLIB.MM910M@ G 
													WHERE    B.USRCOR = G.F910COR
													AND B.USRSAB = G.F910SAB
													AND B.USRID = I_UID) 
					)
					AND (I_SERID = '' OR   UPPER( LOGLID ) LIKE UPPER(I_SERID)||'%')	--조회ID
					AND (I_SERNM = '' OR   F120FNM LIKE '%'||I_SERNM||'%')	--사용자명
					AND (I_SERHOS = '' OR   LOGHOS LIKE I_SERHOS||'%')	--병원코드
					AND (I_SERGIC = '' OR   F120GIC LIKE I_SERGIC||'%')	--요양기관코드
				)AS USERTB
				WHERE   (I_SERDIV = '' OR  USERDIV = I_SERDIV)	-- 사용자구분
				ORDER BY USERDIV, F120FNM;
			
				SET O_MSGCOD = '200';
			OPEN CUR1;
		END;
	ELSE
		BEGIN
			-- 병원(회원) 관리, 조회 LIST 조회
		   	DECLARE CUR1  CURSOR WITH RETURN FOR  
				SELECT 
					(SELECT TRIM(S002CNM) 
						FROM WEBDBLIB.MWS002M@ 
						WHERE S002COR = I_COR 
						AND S002PSCD='USER_DIV'  
						AND S002SCD = USERDIV 
						) AS USDNM
					, (SELECT TRIM(S002CNM) 
						FROM WEBDBLIB.MWS002M@ 
						WHERE S002COR = I_COR 
						AND S002PSCD='AUTH'  
						AND S002SCD = S005AGR 
						) AS S005ANM
					, (SELECT TRIM(S002CNM) 
						FROM WEBDBLIB.MWS002M@ 
						WHERE S002COR = I_COR 
						AND S002PSCD='YES_NO'  
						AND S002SCD = S005SYN 
						) AS S005SNM
					, USERTB.*
				FROM(
					 SELECT TRIM(USRCOR)AS LOGCOR	 -- COR 
						, 'E' AS USERDIV   -- 사용자 : E, 병원 C
						, TRIM(USRID) AS LOGLID	 -- LOGIN ID
						, TRIM(S005AGR) AS S005AGR	-- 권한
						, '' AS LOGHOS	 -- 병원코드 
						, TRIM(F910NAM) AS F120FNM	 -- 병원명(사원명)
						, '' AS F120FL1	 -- 대표상호명
						, '' AS F120GIC	 -- 요양기관코드
						, TRIM(F910PNU) AS LOGTEL	 -- 전화번호 
						, '' AS LOGEML	 -- 멜주소
						, '' AS LOGPO1	 -- 우편번호1
						, '' AS LOGPO2	 -- 우편번호2
						, '' AS LOGADR	 -- 세부주소 
						, '' AS LOGWNO	 -- 대표자명 
						, '' AS LOGGNO	 -- 면허번호 
						, '' AS LOGSNO	 -- 사업자NO 
						, '' AS F120WNO
						, TRIM(S005DRS) AS S005DRS	--원장출신학교
						, TRIM(S005HPH) AS S005HPH	--병원홈페이지
						, TRIM(S005ITM) AS S005ITM		--전문진료과목
						, TRIM(S005POS) AS S005POS	--우편번호
						, TRIM(S005JAD1) AS S005JAD1	--지번 주소1
						, TRIM(S005JAD2) AS S005JAD2	--지번 주소2
						, TRIM(S005DAD1) AS S005DAD1	--도로명 주소1
						, TRIM(S005DAD2) AS S005DAD2	--도로명 주소2
						, TRIM(S005MMO) AS S005MMO	--메모
						, TRIM(S005MRV) AS S005MRV	--메일수신여부
						, TRIM(S005SYN) AS S005SYN	--SMS사용여부
					FROM JMTSLIB.MMUSER@ A	-- 사원 사용자 정보
						LEFT JOIN  WEBDBLIB.MWS005M@ MSW005 
							ON S005COR = A.USRCOR 
							AND S005UID = USRID 
						, JMTSLIB.MM910M@ B
					WHERE A.USRCOR = I_COR
					AND F910COR = I_COR
					AND B.F910COR = A.USRCOR
					AND USRSAB = F910SAB
					AND F910ODT = 0
					AND (I_SERID = '' OR UPPER( USRID ) LIKE UPPER(I_SERID) ||'%') --조회ID
					AND (I_SERNM = '' OR  F910NAM LIKE '%'||I_SERNM||'%')-- 사용자명
					AND (I_SERHOS = '' OR  S005POS = I_SERHOS )	--병원코드
					AND (I_SERGIC = '' OR  S005POS = I_SERGIC )	--요양기관코드
					UNION 
					SELECT 		
							LOGCOR	 -- COR 
						, 'C' AS USERDIV    -- 사용자 : E, 병원 C
						, TRIM(LOGLID) AS LOGLID      -- LOGIN ID
						, TRIM(S005AGR) AS S005AGR	-- 권한
						, TRIM(LOGHOS) AS LOGHOS    -- 병원코드 
						, TRIM(F120FNM) AS F120FNM   -- 병원명
						, TRIM(F120FL1) AS F120FL1     -- 대표상호명
						, TRIM(F120GIC) AS F120GIC     -- 요양기관코드
						, TRIM(LOGTEL) AS LOGTEL      -- 전화번호 
						, IFNULL(TRIM(REPLACE(LOGEML,'','')),'') AS LOGEML    -- 멜주소
						, TRIM(F120PO1) AS LOGPO1     -- 우편번호1
						, TRIM(F120PO2) AS LOGPO2     -- 우편번호2
						, IFNULL(TRIM(REPLACE(LOGADR,'','')),'') AS LOGADR    -- 세부주소 
						, IFNULL(TRIM(LOGWNO),'') AS LOGWNO  -- 대표자명 
						, TRIM(LOGGNO) AS LOGGNO   -- 면허번호 
						,CASE WHEN TRIM(LOGSNO)  ='' 
							THEN TRIM(F120SNO)  
							ELSE TRIM(LOGSNO)  END  LOGSNO    -- 사업자NO
						, IFNULL(TRIM(REPLACE(F120WNO,'','')),'') AS F120WNO  -- 대표자명 
						, TRIM(S005DRS) AS S005DRS	--원장출신학교
						, TRIM(S005HPH) AS S005HPH	--병원홈페이지
						, TRIM(S005ITM) AS S005ITM		--전문진료과목
						, TRIM(S005POS) AS S005POS	--우편번호
						, TRIM(S005JAD1) AS S005JAD1	--지번 주소1
						, TRIM(S005JAD2) AS S005JAD2	--지번 주소2
						, TRIM(S005DAD1) AS S005DAD1	--도로명 주소1
						, TRIM(S005DAD2) AS S005DAD2	--도로명 주소2
						, TRIM(S005MMO) AS S005MMO	--메모
						, TRIM(S005MRV) AS S005MRV	--메일수신여부
						, TRIM(S005SYN) AS S005SYN	--SMS사용여부
					FROM MCLISDLIB.MCLOGI@ MCLOG	--병원사용자정보
						LEFT JOIN  WEBDBLIB.MWS005M@ MSW005 
							ON MSW005.S005COR = MCLOG.LOGCOR 
							AND S005UID = LOGLID 
						, MCLISDLIB.MC120M@ M120
					WHERE LOGCOR = I_COR
					AND F120COR = I_COR
					AND MCLOG.LOGCOR = M120.F120COR
					AND MCLOG.LOGHOS = M120.F120PCD
					AND F120STS ='Y'
					AND (I_SERID = '' OR   UPPER( LOGLID ) LIKE UPPER(I_SERID)||'%')	--조회ID
					AND (I_SERNM = '' OR   F120FNM LIKE '%'||I_SERNM||'%')	--사용자명
					AND (I_SERHOS = '' OR   LOGHOS LIKE I_SERHOS||'%')	--병원코드
					AND (I_SERGIC = '' OR   F120GIC LIKE I_SERGIC||'%')	--요양기관코드
				)AS USERTB
				WHERE   (I_SERDIV = '' OR  USERDIV = I_SERDIV)	-- 사용자구분
				ORDER BY USERDIV, F120FNM;
			
				SET O_MSGCOD = '200';
			OPEN CUR1;
		END;
	END IF;
END




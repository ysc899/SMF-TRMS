DROP PROCEDURE WEBOBJLIB.MWHOSLST;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWHOSLST
(
     IN I_COR       VARCHAR(3)       -- COR
   , IN I_UID       VARCHAR(12)    -- 사용자 ID
   , IN I_IP      VARCHAR(30)      -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)      -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)      -- 에러 코드
   , IN I_HOS      VARCHAR(5)      -- 병원 코드
   , IN I_FNM      VARCHAR(40)      -- 병원이름
   , IN I_MCD      VARCHAR(40)      -- 팝업을 생서하는 메뉴코드(추가)
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	-- 병원 검색 팝업, 병원 목록 조회
   	DECLARE V_HRC  VARCHAR(20) ;
 
   	-- 파견사원 병원인지 확인 ( 한개 병원 계정으로 여러개 병원 검사결과를 조회할 수 있는 병원이면 'Y', 아니면 'N')
  	DECLARE PA_GUEN_HOS_YN  VARCHAR(1) DEFAULT 'Y';
  
  	-- 검색하는 병원인 파견사원 병원인지 확인 ( 한개 병원 계정으로 여러개 병원 검사결과를 조회할 수 있는 병원이면 'Y', 아니면 'N')
   	DECLARE SEARCH_PA_GUEN_HOS_YN  VARCHAR(1) DEFAULT 'Y';
   
  	-- 담당병원만 검색할지 말지 Flag
    SELECT
    	-- 2020.05.07 (변경전)
        -- B.S003HRC INTO V_HRC
        -- 2020.05.07 (변경후)
        -- 지점장/지점직원의 경우, 해당 지점이 담당하는 병원만 조회 가능하도록 한다.
        CASE WHEN A.S005AGR IN ('BRC_MNG','HOSP_NMG') THEN '' ELSE B.S003HRC END INTO V_HRC
    FROM WEBDBLIB.MWS005M@ A , WEBDBLIB.MWS003M@ B
    WHERE A.S005COR = B.S003COR
    AND A.S005AGR = B.S003ACD
    AND A.S005COR = I_COR
    AND A.S005UID = I_UID
    AND (I_MCD = 'MAIN' OR B.S003MCD = I_MCD) 
    LIMIT 1;

   	-- 로그인한 사용자가 파견사원 병원인지 확인 Flag (ex:NDCALL >> Y)
    SELECT 
    	CASE WHEN COUNT(*) = 0 THEN 'N' ELSE 'Y' END INTO  PA_GUEN_HOS_YN
    FROM mclisdlib."MC999D@"
   	WHERE f999cor = I_COR
   	AND F999cd1 = 'CLIC'
   	AND F999cd2 = 'SAA2'
   	AND upper(SUBSTRING(F999CD3,1,6)) = upper(I_UID);
   
	IF V_HRC = '' THEN 
	   --담당 병원만 검색
		SET V_HRC = 'M';
	END IF;
  
	IF PA_GUEN_HOS_YN = 'Y' THEN
		/* 한개 병원 계정으로 여러개 병원 검사결과를 조회할 수 있는 병원이 아니면 */
		BEGIN
		   DECLARE CUR1  CURSOR WITH RETURN FOR  
				SELECT 
					TRIM(AA.F120COR) F120COR
					,TRIM(AA.F120PCD) F120PCD
					,TRIM(AA.F120FNM) F120FNM
					,TRIM(AA.F120WNO) F120WNO
					,TRIM(AA.F120TEL) F120TEL
					,TRIM(AA.F120SML) F120SML
					,(SELECT TRIM(CC.F910NAM)
						FROM JMTSLIB.MM910M@ CC
						WHERE CC.F910SAB = AA.F120MBC) F910NAM
				FROM MCLISDLIB.MC120M@ AA LEFT OUTER JOIN MCLISDLIB."MC999D@" BB
				ON AA.F120COR = BB.F999COR AND AA.F120PCD = BB.F999NM1
				WHERE AA.F120COR = I_COR
				AND AA.F120PCD LIKE I_HOS || '%'
				AND AA.F120FNM LIKE '%' || I_FNM || '%'
				AND BB.F999CD1 = 'CLIC'
				AND BB.F999CD2 = 'SAA2'
				AND BB.F999CD3 LIKE '%' || (SELECT SUBSTRING(F999CD3,1,6) 
											FROM MCLISDLIB.MC999D@  
											WHERE f999cor = I_COR
											AND F999cd1 = 'CLIC'
											AND F999cd2 = 'SAA2' 
											AND upper(SUBSTRING(F999CD3,1,6)) = upper(I_UID)
											limit 1) || '%'
				ORDER BY AA.F120PCD;
	   		OPEN CUR1;
		   	SET O_MSGCOD = '200';
		   	SET O_ERRCOD = '';
	 	 END;
	ELSE
		IF V_HRC = 'A' THEN
			/* 전체병원 >> 한개 병원 계정으로 여러개 병원 검사결과를 조회할 수 있는 병원이면 */
			BEGIN
			   DECLARE CUR1  CURSOR WITH RETURN FOR  
			   SELECT
			       TRIM(F120COR) F120COR
			      ,TRIM(F120PCD) F120PCD
			      ,TRIM(F120FNM) F120FNM
			      ,TRIM(F120WNO) F120WNO
			      ,TRIM(F120TEL) F120TEL
			      ,TRIM(F120SML) F120SML
			      ,(SELECT TRIM(F910NAM)
			         FROM JMTSLIB.MM910M@
			         WHERE F910SAB = F120MBC) F910NAM
			      FROM MCLISDLIB.MC120M@
			      WHERE F120COR = I_COR
			        AND F120PCD LIKE I_HOS || '%'
			        AND F120FNM LIKE '%' || I_FNM || '%'
					ORDER BY F120PCD;
			       
				OPEN CUR1;
			   	SET O_MSGCOD = '200';
			   	SET O_ERRCOD = '';
			END;
		ELSE
			/* 담당 병원만 - 지점별 */
	   		BEGIN
			   DECLARE CUR1  CURSOR WITH RETURN FOR  
	
				SELECT 
				   TRIM(F120COR) F120COR
				  ,TRIM(F120PCD) F120PCD
				  ,TRIM(F120FNM) F120FNM
				  ,TRIM(F120WNO) F120WNO
				  ,TRIM(F120TEL) F120TEL
				  ,TRIM(F120SML) F120SML
				  ,(SELECT TRIM(F910NAM)
				     FROM JMTSLIB.MM910M@
				     WHERE F910SAB = F120MBC) F910NAM
				FROM MCLISDLIB.MC120M@
				WHERE F120COR = I_COR
				AND   F120DPT = (SELECT F910DPT					-- 사번으로 지점 번호 찾기
								FROM JMTSLIB.MM910M@
								WHERE F910COR = I_COR
								AND  F910SAB = (SELECT F910SAB				-- ID로 사번 가져오기
												FROM JMTSLIB.MMUSER@ B,JMTSLIB.MM910M@ G 
												WHERE    B.USRCOR = G.F910COR
												AND B.USRSAB = G.F910SAB
												AND B.USRID = I_UID) 
				)
		        AND F120PCD LIKE I_HOS || '%'
		        AND F120FNM LIKE '%' || I_FNM || '%'
				ORDER BY F120PCD;	   		
		   			
		   		OPEN CUR1;
			   	SET O_MSGCOD = '200';
			   	SET O_ERRCOD = '';
		 	 END;
		END IF;
	END IF;
END
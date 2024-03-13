DROP PROCEDURE WEBOBJLIB.MWR001R2GU;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWR001R2GU ( 
	IN I_COR VARCHAR(3) , 
	IN I_UID VARCHAR(12) , 
	IN I_IP VARCHAR(30) , 
	OUT O_MSGCOD CHAR(3) , 
	OUT O_ERRCOD CHAR(10) , 
	IN I_PWD CHAR(20) , 
	IN I_PWD_SHA VARCHAR(1000) , 
	OUT O_PHCD VARCHAR(5) , 
	OUT O_PUNM VARCHAR(40) , 
	OUT O_PUGB VARCHAR(1) , 
	OUT O_PIYN VARCHAR(1) ) 
	LANGUAGE SQL 
	SPECIFIC WEBOBJLIB.MWR001R2GU 
	NOT DETERMINISTIC 
	MODIFIES SQL DATA 
	CALLED ON NULL INPUT 
	SET OPTION  ALWBLK = *ALLREAD , 
	ALWCPYDTA = *OPTIMIZE , 
	COMMIT = *NONE , 
	DECRESULT = (31, 31, 00) , 
	DYNDFTCOL = *NO , 
	DYNUSRPRF = *USER , 
	SRTSEQ = *HEX   
	BEGIN	 
	 
	DECLARE V_CNT DECIMAL ( 5 , 0 ) ;			 -- 사용자 구분(사원/병원) 체크 
	DECLARE S_CNT DECIMAL ( 5 , 0 ) ;			 -- 사용자 정보가 사원마스터에 존재하는지 체크 
	DECLARE M_CNT DECIMAL ( 5 , 0 ) ;			 -- 사용자 정보가 사원마스터에 존재하는지 체크 
	DECLARE PWD_CNT DECIMAL ( 5 , 0 ) ;		 -- 패스워드 일치하는지 체크 
	DECLARE PWD_CNT_SHA DECIMAL ( 5 , 0 ) ;	 -- 패스워드 일치하는지 체크 (SHA256 암호화) 
	
	SET O_MSGCOD = '300' ; 
	SET O_ERRCOD = 'E' ; 
	SET O_PIYN = 'N' ; 
	SET O_PHCD = ' ' ;
	SET O_PUNM = ' ' ;
	 
	 -- 병원/사원 CHECK 
	SELECT COUNT ( * ) 
	INTO V_CNT 
	FROM MCLISDLIB . MCLOGI@ 
	WHERE 1 = 1 
	AND LOGCOR = I_COR 
	AND LOGLID = I_UID ; 
	 
	 -- 사원마스터에 데이터 존재하는지 CHECK 
	SELECT COUNT ( * ) 
	INTO S_CNT 
	FROM JMTSLIB . MM910M@ 
	WHERE 1 = 1 
	AND F910COR = I_COR 
	AND F910SAB = I_UID ; 
	 
	 -- MCLIS 계정에 존재하는지 CHECK 
	SELECT COUNT ( * ) 
	INTO M_CNT 
	FROM JMTSLIB . MMUSER@ 
	WHERE 1 = 1 
	AND USRCOR = I_COR 
	AND USRSAB = I_UID ; 
  
	 -- 1) 로그인한 사용자가 병원인 경우, 
	IF V_CNT > 0 THEN 
	
		SET O_PUGB = '1' ;  -- 1:병원
		
		-- 1.1) 병원코드 조회 
		SELECT CASE WHEN LOGHOS != '' THEN LOGHOS ELSE ' ' END INTO O_PHCD 
		FROM MCLISDLIB . MCLOGI@ 
		WHERE 1 = 1 
		AND LOGCOR = I_COR 
		AND LOGLID = I_UID ; 
		 
		 -- 1.2) 병원마스터에 사용중인 병원인 경우, 유저명 조회 
		SELECT CASE WHEN F120STS = 'Y' THEN F120FNM ELSE ' ' END INTO O_PUNM 
		FROM MCLISDLIB . MC120M@ 
		WHERE 1 = 1 
		AND F120COR = I_COR 
		AND F120PCD = O_PHCD ; 
		 
		 -- 1.3) 패스워드가 일치는지 체크 
		SELECT COUNT ( * ) INTO PWD_CNT_SHA	 
		FROM MCLISDLIB.MCPASW@T ---- 암호화 반영후(암호화이전까지는 주석처리 2020.04.20) 
		--FROM MCLISDLIB . MCPASW@ -- 암호화 반영전 
		WHERE 1 = 1 
		AND PASCOR = I_COR 
		AND PASLID = I_UID 
		AND PASASW = I_PWD_SHA; ---- 암호화 반영후 (암호화이전까지는 주석처리 2020.04.20) 
		--AND PASASW = I_PWD ; -- 암호화 반영전
		 
		 --1.4) 패스워드가 일치하는 경우, 
		IF PWD_CNT_SHA > 0 THEN 
			IF TRIM(O_PUNM) <> '' THEN
				SET O_ERRCOD = 'O' ;  -- 정상 
            ELSE
               	SET O_ERRCOD = '4' ;  -- 사용중지된 병원 
            END IF; 
		ELSEIF O_PUNM = '' THEN 
			SET O_ERRCOD = '3' ;  -- 병원기초자료 없음 
		ELSE 
			SET O_ERRCOD = '1' ;  -- 패스워드 틀림 
		END IF ; 
		 
	 -- 2.1) 로그인한 사용자가 사원인 경우,	 
	ELSE 
		SET O_PUGB = '2' ;  -- 2:사원 
		
		 -- 2.2) 사원마스터에 존재하고 , MCLIS 계정 존재하는 경우, 
		IF S_CNT > 0 AND M_CNT > 0 THEN 
			 
			 -- 2.3) 사용자명 조회 
			SELECT F910NAM INTO O_PUNM 
			FROM JMTSLIB . MM910M@ 
			WHERE 1 = 1 
			AND F910COR = I_COR 
			AND F910SAB = I_UID ; 
			 
			 -- 2.4) 패스워드가 일치는지 체크 
			SELECT COUNT ( * ) INTO PWD_CNT	 
			FROM JMTSLIB . MMUSER@ 
			WHERE 1 = 1 
			AND USRCOR = I_COR 
			AND USRSAB = I_UID 
			AND USRPWD = I_PWD ; 
			 
			 -- 2.5) 패스워드가 일치하는 경우, 
			IF PWD_CNT > 0 THEN 
				SET O_ERRCOD = 'O' ;  -- 정상 
			ELSE 
				SET O_ERRCOD = '1' ;  -- 패스워드 틀림 
			END IF ; 
			 
		ELSE 
			SET O_ERRCOD = '2' ;	 -- ID 미존재 
		END IF ; 
	END IF ; 
	 
	 -- 공통) 로그인한 사용자의 데이터가 권한데이터에 존재하는 경우, 
	SELECT S005IYN 
	INTO O_PIYN	 -- 초기화여부 
	FROM WEBDBLIB . MWS005M@ 
	WHERE 1 = 1 
	AND S005COR = I_COR 
	AND S005UID = I_UID ; 
  
	SET O_MSGCOD = '200' ; 
  
END 
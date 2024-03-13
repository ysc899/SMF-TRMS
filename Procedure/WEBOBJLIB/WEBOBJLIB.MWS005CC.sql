--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              21/07/26 10:29:09 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
DROP SPECIFIC PROCEDURE WEBOBJLIB.MWS005CC ; 
SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWS005CC ( 
  IN I_COR VARCHAR(3) , 
  IN I_UID VARCHAR(12) , 
  IN I_IP VARCHAR(30) , 
  OUT O_MSGCOD CHAR(3) , 
  OUT O_ERRCOD CHAR(10) , 
  IN I_CHKTYPE VARCHAR(1) , 
  IN I_CHKCD VARCHAR(12) , 
  IN I_PASASW VARCHAR(1000) , 
  IN I_NOWPASASW VARCHAR(1000) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.MWS005CC 
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
	DECLARE V_CNT DECIMAL ( 5 , 0 ) ;  -- 아이디 중복 확인, 병원 코드 확인 
DECLARE V_CNT_2 DECIMAL ( 5 , 0 ) ;  -- 병원코드에 홈페이지 계정이 존재할 경우 check 
	IF I_CHKTYPE = 'I' THEN 
		SELECT COUNT ( * )  -- 중복 아이디 체크 
		INTO V_CNT 
		FROM MCLISDLIB . MCLOGI@ 
		WHERE LOGCOR = I_COR 
		AND UPPER ( LOGLID ) = UPPER ( I_CHKCD ) ; 
	 
		IF V_CNT > 0 THEN 
			SET O_MSGCOD = '240' ;  -- 아이디 존재 
		ELSE 
			SET O_MSGCOD = '200' ; 
		END IF ; 
	ELSEIF I_CHKTYPE = 'P' THEN  -- 비밀번호 체크 
		SELECT COUNT ( * ) 
		INTO V_CNT 
		FROM MCLISDLIB . MCPASW@T 
		WHERE PASCOR = I_COR 
		AND PASLID = UPPER ( I_UID ) 
		AND PASASW = I_NOWPASASW ; 
		IF V_CNT > 0 THEN 
			SET O_MSGCOD = '200' ;  -- 정상 비밀번호 
		ELSE 
			SET O_MSGCOD = '247' ;  -- 비밀번호 false 
		END IF ; 
	 
	ELSE 
		SELECT COUNT ( * )  -- 병원 코드  확인 
		INTO V_CNT_2 
		FROM MCLISDLIB . MCLOGI@ 
		WHERE LOGCOR = I_COR 
		AND LOGHOS = I_CHKCD ; 
		
		SELECT COUNT ( * )  -- 병원 코드 사용상태 확인 
		INTO V_CNT 
		FROM MCLISDLIB . MC120M@ 
		WHERE F120COR = I_COR 
		AND F120STS = 'Y' 
		AND F120PCD = I_CHKCD ; 
	 
		IF V_CNT = 0 THEN 
			SET O_MSGCOD = '241' ;  -- 병원 코드 사용 금지 
		ELSE 
			SET O_MSGCOD = '200' ; 
			
			IF V_CNT_2 > 0 THEN 
				SET O_ERRCOD = '297' ; 
			END IF ; 
			
			BEGIN 
				DECLARE CUR1 CURSOR WITH RETURN FOR			 
				SELECT F120COR	 -- 	COR 
					, TRIM ( F120PCD	) AS F120PCD	 -- 	병원코드 
					, TRIM ( F120GIC	) AS F120GIC	 -- 	요양기관코드 
					, TRIM ( F120STS	) AS F120STS	 -- 	사용여부 
					, TRIM ( F120FNM	) AS F120FNM	 -- 	병원명 
					, TRIM ( F120SNO	) AS F120SNO	 -- 	사업자NO 
					, TRIM ( F120WNO	) AS F120WNO	 -- 	대표자명 
					, TRIM ( F120PO1	) AS F120PO1	 -- 	우편번호1 
					, TRIM ( F120PO2	) AS F120PO2	 -- 	우편번호2 
					, TRIM ( F120ADR	) AS F120ADR	 -- 	주소 
					, TRIM ( F120TEL	) AS F120TEL	 -- 	전화번호 
					, TRIM ( F120FAX	) AS F120FAX	 -- 	팩스번호 
					, TRIM ( F120FL4	) AS F120FL4	 -- 	우편번호SEQ 
				FROM MCLISDLIB . MC120M@ 
				WHERE F120COR = I_COR 
				AND F120STS = 'Y' 
				AND F120PCD = I_CHKCD ; 
				OPEN CUR1 ; 
			END ; 
		END IF ; 
	END IF ; 
END  ; 

--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/04/06 09:57:44 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE WEBOBJLIB.MWT001R1 ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWT001R1 ( 
  IN I_COR VARCHAR(3) , 
  IN I_UID VARCHAR(12) , 
  IN I_IP VARCHAR(30) , 
  OUT O_MSGCOD VARCHAR(3) , 
  OUT O_ERRCOD VARCHAR(10) , 
  IN I_SERFNM VARCHAR(20) , 
  IN I_SERNM VARCHAR(100) , 
  IN I_HAK VARCHAR(20) , 
  IN I_ISC VARCHAR(1000) , 
  IN I_ALHNM VARCHAR(1000) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.MWT001R1 
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
	IF I_SERFNM = '' THEN  -- 전체 
		BEGIN 
		 -- 검사항목조회 
		DECLARE CUR1 CURSOR WITH RETURN FOR 
		WITH TESTITEM AS ( 
		SELECT 
		TRIM ( F010COR ) F010COR  --COR 
		, TRIM ( F010GCD ) F010GCD  -- 검사코드 
		, TRIM ( F010SNM ) F010SNM  -- 검사약어 
		, TRIM ( F010FKN ) F010FKN  -- 검사명(한글) 
		, TRIM ( F010TCD ) F010TCD  -- 검체 코드 (F999CD2 ='SAMP' ) 
		, ( SELECT TRIM ( MC9 . F999NM2 ) 
		FROM MCLISDLIB . MC999D@ MC9 
		WHERE F999COR = F010COR 
				AND F999CD1 = 'CLIC' 
		AND F999CD2 = 'SAMP' 
		AND F999CD3 = F010TCD 
		) AS F010TNM 
		, TRIM ( F010GBX ) F010GBX  -- 검체용기코드 (F999CD2 ='VESS' ) 
		, TRIM ( F010MSC ) F010MSC  -- 검사법 
		, ( 
			SELECT 
							CONCAT ( CONCAT ( LISTAGG ( F028TXT1 , ' ' ) , LISTAGG ( F028TXT2 , ' ' ) ) , LISTAGG ( F028TXT3 , ' ' ) ) 
						FROM ( 
							SELECT CASE WHEN F028SEQ = 0 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT1 , 
								CASE WHEN F028SEQ = 1 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT2 , 
								CASE WHEN F028SEQ = 2 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT3 
						, F028GCD , F028COR 
							FROM MCLISDLIB . MC028D@ MC28 
							WHERE F028COR = I_COR 
						) AS MC28 
							WHERE MC28 . F028COR = F010COR 
							AND MC28 . F028GCD = F010MSC 
						GROUP BY F028GCD , F028COR 
		) AS F010MSNM 
		, CONCAT ( 
						CASE ( 
							CONCAT ( 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
														, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN '화' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN '수' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN '목' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN '금' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN '토' ELSE '' END 
								) 
								, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN '일' ELSE '' END 
							) 
						) WHEN '월화' THEN '월-화' 
							WHEN '월화수' THEN '월-수' 
							WHEN '월화수목' THEN '월-목' 
							WHEN '월화수목금' THEN '월-금' 
							WHEN '월화수목금토' THEN '월-토' 
							WHEN '월화수목금토일' THEN '월-일' 
							WHEN '화수' THEN '화-수' 
							WHEN '화수목' THEN '화-목' 
							WHEN '화수목금' THEN '화-금' 
							WHEN '화수목금토' THEN '화-토' 
							WHEN '화수목금토일' THEN '화-일' 
							WHEN '수목' THEN '수-목' 
							WHEN '수목금' THEN '수-금' 
							WHEN '수목금토' THEN '수-토' 
							WHEN '수목금토일' THEN '수-일' 
							WHEN '목금' THEN '목-금' 
							WHEN '목금토' THEN '목-토' 
							WHEN '목금토일' THEN '목-일' 
							WHEN '금토' THEN '금-토' 
							WHEN '금토일' THEN '금-일' 
							WHEN '토일' THEN '토-일' 
						ELSE 
							CASE SUBSTRING ( CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) , 1 , 1 ) 
							WHEN ',' 
							THEN 
								SUBSTRING ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
									) , 2 , LENGTH ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) 
										) - 1 
								) 
							ELSE 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
															, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
								) 
							END 
						END 
			 -- MC970M@ 테이블을 먼저 조회 후, MC010M@ 테이블을 조회	  		 
			, CONCAT ( '/' , CASE ( SELECT F970DNC FROM MCLISDLIB . MC970M@ WHERE F970COR = I_COR AND F970DPT = MC010 . F010HAK AND F970JOB = MC010 . F010JOB ) 
								WHEN 'D' THEN '주간' 
								WHEN 'N' THEN '야간' 
								ELSE CASE F010DNC WHEN 'A' THEN '' ELSE CONCAT ( '/' , CASE F010DNC 
																					WHEN 'D' THEN '주간' 
																					WHEN 'N' THEN '야간' 
																					ELSE '' END 
																		) 
									END 
						END 
			) 
		) T001DAY  -- 검사일 
		, CAST ( CONCAT ( ( F010EED ) , '일' ) AS VARCHAR ( 20 ) )	F010EED  -- 검사소요일수 
		, TRIM ( T001FLG )	T001FLG  -- 사용여부 
		, TRIM ( T001LFLG )	T001LFLG  --- 리스트사용여부 
		, TRIM ( F010STS )	F010STS  -- 상태 
		FROM MCLISDLIB . MC010M@ MC010 
		, WEBDBLIB . MWT001M@ MWT001 
		WHERE MC010 . F010COR = MWT001 . T001COR 
		AND MC010 . F010GCD = MWT001 . T001TCD 
		AND MC010 . F010COR = I_COR 
		AND F010STS = 'Y'	 --사용여부 
		AND F010ACH = 'Y'	 --웹여부 
		AND T001FLG = 'Y' 
		AND T001LFLG = 'Y' 
		AND ( I_ALHNM = '' OR UPPER ( F010FKN ) LIKE UPPER ( I_ALHNM ) || '%' ) 
		AND ( I_HAK = '' OR F010HAK = I_HAK ) 
		AND ( I_ISC = '' OR F010ISC IN ( 
					SELECT SUBSTR ( NAME , 1 , INSTR ( NAME , '|' ) - 1 ) DWARF 
					FROM ( 
						SELECT N , SUBSTR ( VAL , 1 + INSTR ( VAL , '|' , 1 , N ) ) NAME 
					FROM ( 
							SELECT ROW_NUMBER ( ) OVER ( ) AS N , LIST . VAL 
					FROM ( SELECT I_ISC VAL 
					FROM SYSIBM . SYSDUMMY1 ) LIST 
					CONNECT BY LEVEL < 
						( LENGTH ( LIST . VAL ) 
								- LENGTH ( REPLACE ( LIST . VAL , '|' , '' ) ) 
							) 
					) AS BB 
					) CC 
			) 
		) 
		) 
		, F018 AS ( 
		SELECT F018OCD , F018GCD , F018COR 
		FROM ( 
			SELECT 
		MAX ( F018OCD0 ) AS F018OCD 
		, MAX ( F018OCD1 )
		, MAX ( F018OCD2 ) 
		, F018GCD , F018COR 
		FROM ( 
		SELECT 
		CASE WHEN 
						TRIM ( F018LMB ) = '9999'	--기본 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD0
		,
		CASE WHEN 
						TRIM ( F018LMB ) = ''	 --서울 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD1 
		, CASE WHEN 
					TRIM ( F018LMB ) = '6526'	 -- 부산 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD2 
		, TRIM ( F018GCD ) AS F018GCD , F018COR 
		FROM MCLISDLIB . MC018M@ A 
		WHERE F018COR = I_COR 
		AND F018OSD = 
			( SELECT MAX ( F018OSD ) 
		FROM MCLISDLIB . MC018M@ B 
		WHERE A . F018COR = B . F018COR 
		AND A . F018GCD = B . F018GCD 
		AND B . F018LMB = '9999' )  -- 보험코드가 조회되지 않아 수정 (2020.03.03) 
		) A 
		GROUP BY F018GCD , F018COR 
		) A 
		) 
		SELECT F018OCD 
		, TESTITEM . * 
		FROM TESTITEM 
		LEFT JOIN F018 
			ON F018 . F018COR = TESTITEM . F010COR 
			AND F018 . F018GCD = TESTITEM . F010GCD 
		WHERE ( 
		( UPPER ( F010FKN ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 검사 명 
			OR ( UPPER ( F010SNM ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 검사 약어 
			OR ( UPPER ( F010GCD ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 검사 코드 
			OR ( UPPER ( F018OCD ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 보험코드 
		) 
			ORDER BY F010COR , F010FKN , F010GCD , F010TCD 
			; 
			SET O_MSGCOD = '200' ; 
		OPEN CUR1 ; 
		END ; 
		 --검사명 조회 
	ELSEIF I_SERFNM = '01' THEN 
		BEGIN 
		 -- 검사항목조회 
		DECLARE CUR1 CURSOR WITH RETURN FOR 
		WITH TESTITEM AS ( 
		SELECT 
		TRIM ( F010COR ) F010COR  --COR 
		, TRIM ( F010GCD ) F010GCD  -- 검사코드 
		, TRIM ( F010SNM ) F010SNM  -- 검사약어 
		, TRIM ( F010FKN ) F010FKN  -- 검사명(한글) 
		, TRIM ( F010TCD ) F010TCD  -- 검체 코드 (F999CD2 ='SAMP' ) 
		, ( SELECT TRIM ( MC9 . F999NM2 ) 
		FROM MCLISDLIB . MC999D@ MC9 
		WHERE F999COR = F010COR 
				AND F999CD1 = 'CLIC' 
		AND F999CD2 = 'SAMP' 
		AND F999CD3 = F010TCD 
		) AS F010TNM 
		, TRIM ( F010GBX ) F010GBX  -- 검체용기코드 (F999CD2 ='VESS' ) 
		, TRIM ( F010MSC ) F010MSC  -- 검사법 
		, ( 
			SELECT 
							CONCAT ( CONCAT ( LISTAGG ( F028TXT1 , ' ' ) , LISTAGG ( F028TXT2 , ' ' ) ) , LISTAGG ( F028TXT3 , ' ' ) ) 
						FROM ( 
							SELECT CASE WHEN F028SEQ = 0 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT1 , 
								CASE WHEN F028SEQ = 1 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT2 , 
								CASE WHEN F028SEQ = 2 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT3 
						, F028GCD , F028COR 
							FROM MCLISDLIB . MC028D@ MC28 
							WHERE F028COR = I_COR 
						) AS MC28 
							WHERE MC28 . F028COR = F010COR 
							AND MC28 . F028GCD = F010MSC 
						GROUP BY F028GCD , F028COR 
		) AS F010MSNM 
		, CONCAT ( 
						CASE ( 
							CONCAT ( 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
														, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN '화' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN '수' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN '목' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN '금' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN '토' ELSE '' END 
								) 
								, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN '일' ELSE '' END 
							) 
						) WHEN '월화' THEN '월-화' 
							WHEN '월화수' THEN '월-수' 
							WHEN '월화수목' THEN '월-목' 
							WHEN '월화수목금' THEN '월-금' 
							WHEN '월화수목금토' THEN '월-토' 
							WHEN '월화수목금토일' THEN '월-일' 
							WHEN '화수' THEN '화-수' 
							WHEN '화수목' THEN '화-목' 
							WHEN '화수목금' THEN '화-금' 
							WHEN '화수목금토' THEN '화-토' 
							WHEN '화수목금토일' THEN '화-일' 
							WHEN '수목' THEN '수-목' 
							WHEN '수목금' THEN '수-금' 
							WHEN '수목금토' THEN '수-토' 
							WHEN '수목금토일' THEN '수-일' 
							WHEN '목금' THEN '목-금' 
							WHEN '목금토' THEN '목-토' 
							WHEN '목금토일' THEN '목-일' 
							WHEN '금토' THEN '금-토' 
							WHEN '금토일' THEN '금-일' 
							WHEN '토일' THEN '토-일' 
						ELSE 
							CASE SUBSTRING ( CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) , 1 , 1 ) 
							WHEN ',' 
							THEN 
								SUBSTRING ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
									) , 2 , LENGTH ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) 
										) - 1 
								) 
							ELSE 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
															, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
								) 
							END 
						END 
		, CASE F010DNC WHEN 'A' THEN '' ELSE CONCAT ( '/' , CASE F010DNC WHEN 'D' THEN '주간' 
																	WHEN 'N' THEN '야간' 
														ELSE '' END 
													) END 
		) T001DAY  -- 검사일 
					, CAST ( CONCAT ( ( F010EED ) , '일' ) AS VARCHAR ( 20 ) )	F010EED  -- 검사소요일수 
		, TRIM ( T001FLG )	T001FLG  -- 사용여부 
		, TRIM ( T001LFLG )	T001LFLG  --- 리스트사용여부 
		, TRIM ( F010STS )	F010STS  -- 상태 
		FROM MCLISDLIB . MC010M@ MC010 
		, WEBDBLIB . MWT001M@ MWT001 
		WHERE MC010 . F010COR = MWT001 . T001COR 
		AND MC010 . F010GCD = MWT001 . T001TCD 
		AND MC010 . F010COR = I_COR 
		AND F010STS = 'Y'	 --사용여부 
		AND F010ACH = 'Y'	 --웹여부 
		AND T001FLG = 'Y' 
		AND T001LFLG = 'Y' 
		AND ( I_ALHNM = '' OR UPPER ( F010FKN ) LIKE UPPER ( I_ALHNM ) || '%' ) 
		AND ( UPPER ( F010FKN ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 검사 명 
		AND ( I_HAK = '' OR F010HAK = I_HAK ) 
		AND ( I_ISC = '' OR F010ISC IN ( 
					SELECT SUBSTR ( NAME , 1 , INSTR ( NAME , '|' ) - 1 ) DWARF 
					FROM ( 
						SELECT N , SUBSTR ( VAL , 1 + INSTR ( VAL , '|' , 1 , N ) ) NAME 
					FROM ( 
							SELECT ROW_NUMBER ( ) OVER ( ) AS N , LIST . VAL 
					FROM ( SELECT I_ISC VAL 
					FROM SYSIBM . SYSDUMMY1 ) LIST 
					CONNECT BY LEVEL < 
						( LENGTH ( LIST . VAL ) 
								- LENGTH ( REPLACE ( LIST . VAL , '|' , '' ) ) 
							) 
					) AS BB 
					) CC 
			) 
		) 
		) 
		, F018 AS ( 
		SELECT F018OCD , F018GCD , F018COR 
		FROM ( 
			SELECT 
		MAX ( F018OCD0 ) AS F018OCD 
		, MAX ( F018OCD1 )
		, MAX ( F018OCD2 ) 
		, F018GCD , F018COR 
		FROM ( 
		SELECT 
		CASE WHEN 
						TRIM ( F018LMB ) = '9999'	--기본 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD0
		,
		CASE WHEN 
						TRIM ( F018LMB ) = ''	 --서울 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD1 
		, CASE WHEN 
					TRIM ( F018LMB ) = '6526'	 -- 부산 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD2 
		, TRIM ( F018GCD ) AS F018GCD , F018COR 
		FROM MCLISDLIB . MC018M@ A 
		WHERE F018COR = I_COR 
		AND F018OSD = 
			( SELECT MAX ( F018OSD ) 
		FROM MCLISDLIB . MC018M@ B 
		WHERE A . F018COR = B . F018COR 
		AND A . F018GCD = B . F018GCD 
		AND B . F018LMB = '9999' ) 
		) A 
		GROUP BY F018GCD , F018COR 
		) A 
		) 
		SELECT F018OCD 
		, TESTITEM . * 
		FROM TESTITEM 
		LEFT JOIN F018 
			ON F018 . F018COR = TESTITEM . F010COR 
			AND F018 . F018GCD = TESTITEM . F010GCD 
			ORDER BY F010COR , F010FKN , F010GCD , F010TCD 
			; 
			SET O_MSGCOD = '200' ; 
		OPEN CUR1 ; 
		END ; 
		 --검사약어 
	ELSEIF I_SERFNM = '02' THEN 
		BEGIN 
		 -- 검사항목조회 
		DECLARE CUR1 CURSOR WITH RETURN FOR 
		WITH TESTITEM AS ( 
		SELECT 
		TRIM ( F010COR ) F010COR  --COR 
		, TRIM ( F010GCD ) F010GCD  -- 검사코드 
		, TRIM ( F010SNM ) F010SNM  -- 검사약어 
		, TRIM ( F010FKN ) F010FKN  -- 검사명(한글) 
		, TRIM ( F010TCD ) F010TCD  -- 검체 코드 (F999CD2 ='SAMP' ) 
		, ( SELECT TRIM ( MC9 . F999NM2 ) 
		FROM MCLISDLIB . MC999D@ MC9 
		WHERE F999COR = F010COR 
				AND F999CD1 = 'CLIC' 
		AND F999CD2 = 'SAMP' 
		AND F999CD3 = F010TCD 
		) AS F010TNM 
		, TRIM ( F010GBX ) F010GBX  -- 검체용기코드 (F999CD2 ='VESS' ) 
		, TRIM ( F010MSC ) F010MSC  -- 검사법 
		, ( 
			SELECT 
							CONCAT ( CONCAT ( LISTAGG ( F028TXT1 , ' ' ) , LISTAGG ( F028TXT2 , ' ' ) ) , LISTAGG ( F028TXT3 , ' ' ) ) 
						FROM ( 
							SELECT CASE WHEN F028SEQ = 0 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT1 , 
								CASE WHEN F028SEQ = 1 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT2 , 
								CASE WHEN F028SEQ = 2 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT3 
						, F028GCD , F028COR 
							FROM MCLISDLIB . MC028D@ MC28 
							WHERE F028COR = I_COR 
						) AS MC28 
							WHERE MC28 . F028COR = F010COR 
							AND MC28 . F028GCD = F010MSC 
						GROUP BY F028GCD , F028COR 
		) AS F010MSNM 
		, CONCAT ( 
						CASE ( 
							CONCAT ( 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
														, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN '화' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN '수' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN '목' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN '금' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN '토' ELSE '' END 
								) 
								, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN '일' ELSE '' END 
							) 
						) WHEN '월화' THEN '월-화' 
							WHEN '월화수' THEN '월-수' 
							WHEN '월화수목' THEN '월-목' 
							WHEN '월화수목금' THEN '월-금' 
							WHEN '월화수목금토' THEN '월-토' 
							WHEN '월화수목금토일' THEN '월-일' 
							WHEN '화수' THEN '화-수' 
							WHEN '화수목' THEN '화-목' 
							WHEN '화수목금' THEN '화-금' 
							WHEN '화수목금토' THEN '화-토' 
							WHEN '화수목금토일' THEN '화-일' 
							WHEN '수목' THEN '수-목' 
							WHEN '수목금' THEN '수-금' 
							WHEN '수목금토' THEN '수-토' 
							WHEN '수목금토일' THEN '수-일' 
							WHEN '목금' THEN '목-금' 
							WHEN '목금토' THEN '목-토' 
							WHEN '목금토일' THEN '목-일' 
							WHEN '금토' THEN '금-토' 
							WHEN '금토일' THEN '금-일' 
							WHEN '토일' THEN '토-일' 
						ELSE 
							CASE SUBSTRING ( CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) , 1 , 1 ) 
							WHEN ',' 
							THEN 
								SUBSTRING ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
									) , 2 , LENGTH ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) 
										) - 1 
								) 
							ELSE 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
															, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
								) 
							END 
						END 
		, CASE F010DNC WHEN 'A' THEN '' ELSE CONCAT ( '/' , CASE F010DNC WHEN 'D' THEN '주간' 
																	WHEN 'N' THEN '야간' 
														ELSE '' END 
													) END 
		) T001DAY  -- 검사일 
					, CAST ( CONCAT ( ( F010EED ) , '일' ) AS VARCHAR ( 20 ) )	F010EED  -- 검사소요일수 
		, TRIM ( T001FLG )	T001FLG  -- 사용여부 
		, TRIM ( T001LFLG )	T001LFLG  --- 리스트사용여부 
		, TRIM ( F010STS )	F010STS  -- 상태 
		FROM MCLISDLIB . MC010M@ MC010 
		, WEBDBLIB . MWT001M@ MWT001 
		WHERE MC010 . F010COR = MWT001 . T001COR 
		AND MC010 . F010GCD = MWT001 . T001TCD 
		AND MC010 . F010COR = I_COR 
		AND F010STS = 'Y'	 --사용여부 
		AND F010ACH = 'Y'	 --웹여부 
		AND T001FLG = 'Y' 
		AND T001LFLG = 'Y' 
		AND ( I_ALHNM = '' OR UPPER ( F010FKN ) LIKE UPPER ( I_ALHNM ) || '%' ) 
		AND ( UPPER ( F010SNM ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 검사 약어 
		AND ( I_HAK = '' OR F010HAK = I_HAK ) 
		AND ( I_ISC = '' OR F010ISC IN ( 
					SELECT SUBSTR ( NAME , 1 , INSTR ( NAME , '|' ) - 1 ) DWARF 
					FROM ( 
						SELECT N , SUBSTR ( VAL , 1 + INSTR ( VAL , '|' , 1 , N ) ) NAME 
					FROM ( 
							SELECT ROW_NUMBER ( ) OVER ( ) AS N , LIST . VAL 
					FROM ( SELECT I_ISC VAL 
					FROM SYSIBM . SYSDUMMY1 ) LIST 
					CONNECT BY LEVEL < 
						( LENGTH ( LIST . VAL ) 
								- LENGTH ( REPLACE ( LIST . VAL , '|' , '' ) ) 
							) 
					) AS BB 
					) CC 
			) 
		) 
		) 
		, F018 AS ( 
		SELECT F018OCD , F018GCD , F018COR 
		FROM ( 
			SELECT 
		MAX ( F018OCD0 ) AS F018OCD 
		, MAX ( F018OCD1 )
		, MAX ( F018OCD2 ) 
		, F018GCD , F018COR 
		FROM ( 
		SELECT 
		CASE WHEN 
						TRIM ( F018LMB ) = '9999'	--기본 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD0
		,
		CASE WHEN 
						TRIM ( F018LMB ) = ''	 --서울 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD1 
		, CASE WHEN 
					TRIM ( F018LMB ) = '6526'	 -- 부산 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD2 
		, TRIM ( F018GCD ) AS F018GCD , F018COR 
		FROM MCLISDLIB . MC018M@ A 
		WHERE F018COR = I_COR 
		AND F018OSD = 
			( SELECT MAX ( F018OSD ) 
		FROM MCLISDLIB . MC018M@ B 
		WHERE A . F018COR = B . F018COR 
		AND A . F018GCD = B . F018GCD 
		AND B . F018LMB = '9999' ) 
		) A 
		GROUP BY F018GCD , F018COR 
		) A 
		) 
		SELECT F018OCD 
		, TESTITEM . * 
		FROM TESTITEM 
		LEFT JOIN F018 
			ON F018 . F018COR = TESTITEM . F010COR 
			AND F018 . F018GCD = TESTITEM . F010GCD 
			ORDER BY F010COR , F010FKN , F010GCD , F010TCD 
			; 
			SET O_MSGCOD = '200' ; 
		OPEN CUR1 ; 
		END ; 
		 --검사코드조회 
	ELSEIF I_SERFNM = '03' THEN 
		BEGIN 
		 -- 검사항목조회 
		DECLARE CUR1 CURSOR WITH RETURN FOR 
		WITH TESTITEM AS ( 
		SELECT 
		TRIM ( F010COR ) F010COR  --COR 
		, TRIM ( F010GCD ) F010GCD  -- 검사코드 
		, TRIM ( F010SNM ) F010SNM  -- 검사약어 
		, TRIM ( F010FKN ) F010FKN  -- 검사명(한글) 
		, TRIM ( F010TCD ) F010TCD  -- 검체 코드 (F999CD2 ='SAMP' ) 
		, ( SELECT TRIM ( MC9 . F999NM2 ) 
		FROM MCLISDLIB . MC999D@ MC9 
		WHERE F999COR = F010COR 
				AND F999CD1 = 'CLIC' 
		AND F999CD2 = 'SAMP' 
		AND F999CD3 = F010TCD 
		) AS F010TNM 
		, TRIM ( F010GBX ) F010GBX  -- 검체용기코드 (F999CD2 ='VESS' ) 
		, TRIM ( F010MSC ) F010MSC  -- 검사법 
		, ( 
			SELECT 
							CONCAT ( CONCAT ( LISTAGG ( F028TXT1 , ' ' ) , LISTAGG ( F028TXT2 , ' ' ) ) , LISTAGG ( F028TXT3 , ' ' ) ) 
						FROM ( 
							SELECT CASE WHEN F028SEQ = 0 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT1 , 
								CASE WHEN F028SEQ = 1 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT2 , 
								CASE WHEN F028SEQ = 2 
						THEN TRIM ( F028TXT ) 
						ELSE '' 
						END AS F028TXT3 
						, F028GCD , F028COR 
							FROM MCLISDLIB . MC028D@ MC28 
							WHERE F028COR = I_COR 
						) AS MC28 
							WHERE MC28 . F028COR = F010COR 
							AND MC28 . F028GCD = F010MSC 
						GROUP BY F028GCD , F028COR 
		) AS F010MSNM 
		, CONCAT ( 
						CASE ( 
							CONCAT ( 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
														, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN '화' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN '수' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN '목' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN '금' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN '토' ELSE '' END 
								) 
								, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN '일' ELSE '' END 
							) 
						) WHEN '월화' THEN '월-화' 
							WHEN '월화수' THEN '월-수' 
							WHEN '월화수목' THEN '월-목' 
							WHEN '월화수목금' THEN '월-금' 
							WHEN '월화수목금토' THEN '월-토' 
							WHEN '월화수목금토일' THEN '월-일' 
							WHEN '화수' THEN '화-수' 
							WHEN '화수목' THEN '화-목' 
							WHEN '화수목금' THEN '화-금' 
							WHEN '화수목금토' THEN '화-토' 
							WHEN '화수목금토일' THEN '화-일' 
							WHEN '수목' THEN '수-목' 
							WHEN '수목금' THEN '수-금' 
							WHEN '수목금토' THEN '수-토' 
							WHEN '수목금토일' THEN '수-일' 
							WHEN '목금' THEN '목-금' 
							WHEN '목금토' THEN '목-토' 
							WHEN '목금토일' THEN '목-일' 
							WHEN '금토' THEN '금-토' 
							WHEN '금토일' THEN '금-일' 
							WHEN '토일' THEN '토-일' 
						ELSE 
							CASE SUBSTRING ( CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) , 1 , 1 ) 
							WHEN ',' 
							THEN 
								SUBSTRING ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
									) , 2 , LENGTH ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) 
										) - 1 
								) 
							ELSE 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
															, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
								) 
							END 
						END 
		, CASE F010DNC WHEN 'A' THEN '' ELSE CONCAT ( '/' , CASE F010DNC WHEN 'D' THEN '주간' 
																	WHEN 'N' THEN '야간' 
														ELSE '' END 
													) END 
		) T001DAY  -- 검사일 
		, CAST ( CONCAT ( ( F010EED ) , '일' ) AS VARCHAR ( 20 ) )	F010EED  -- 검사소요일수 
		, TRIM ( T001FLG )	T001FLG  -- 사용여부 
		, TRIM ( T001LFLG )	T001LFLG  --- 리스트사용여부 
		, TRIM ( F010STS )	F010STS  -- 상태 
		FROM MCLISDLIB . MC010M@ MC010 
		, WEBDBLIB . MWT001M@ MWT001 
		WHERE MC010 . F010COR = MWT001 . T001COR 
		AND MC010 . F010GCD = MWT001 . T001TCD 
		AND MC010 . F010COR = I_COR 
		AND F010STS = 'Y'	 --사용여부 
		AND F010ACH = 'Y'	 --웹여부 
		AND T001FLG = 'Y' 
		AND T001LFLG = 'Y' 
		AND ( I_ALHNM = '' OR UPPER ( F010FKN ) LIKE UPPER ( I_ALHNM ) || '%' ) 
		AND ( UPPER ( F010GCD ) LIKE '%' || UPPER ( I_SERNM ) || '%' )	 -- 검사 코드 
		AND ( I_HAK = '' OR F010HAK = I_HAK ) 
		AND ( I_ISC = '' OR F010ISC IN ( 
					SELECT SUBSTR ( NAME , 1 , INSTR ( NAME , '|' ) - 1 ) DWARF 
					FROM ( 
						SELECT N , SUBSTR ( VAL , 1 + INSTR ( VAL , '|' , 1 , N ) ) NAME 
					FROM ( 
							SELECT ROW_NUMBER ( ) OVER ( ) AS N , LIST . VAL 
					FROM ( SELECT I_ISC VAL 
					FROM SYSIBM . SYSDUMMY1 ) LIST 
					CONNECT BY LEVEL < 
						( LENGTH ( LIST . VAL ) 
								- LENGTH ( REPLACE ( LIST . VAL , '|' , '' ) ) 
							) 
					) AS BB 
					) CC 
			) 
		) 
		) 
		, F018 AS ( 
		SELECT F018OCD , F018GCD , F018COR 
		FROM ( 
			SELECT 
		MAX ( F018OCD0 ) AS F018OCD 
		, MAX ( F018OCD1 )
		, MAX ( F018OCD2 ) 
		, F018GCD , F018COR 
		FROM ( 
		SELECT 
		CASE WHEN 
						TRIM ( F018LMB ) = '9999'	--기본 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD0
		,
		CASE WHEN 
						TRIM ( F018LMB ) = ''	 --서울 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD1 
		, CASE WHEN 
					TRIM ( F018LMB ) = '6526'	 -- 부산 보험코드 
					THEN TRIM ( F018OCD ) 
		END F018OCD2 
		, TRIM ( F018GCD ) AS F018GCD , F018COR 
		FROM MCLISDLIB . MC018M@ A 
		WHERE F018COR = I_COR 
		AND F018OSD = 
			( SELECT MAX ( F018OSD ) 
		FROM MCLISDLIB . MC018M@ B 
		WHERE A . F018COR = B . F018COR 
		AND A . F018GCD = B . F018GCD 
		AND B . F018LMB = '9999' ) 
		) A 
		GROUP BY F018GCD , F018COR 
		) A 
		) 
		SELECT F018OCD 
		, TESTITEM . * 
		FROM TESTITEM 
		LEFT JOIN F018 
			ON F018 . F018COR = TESTITEM . F010COR 
			AND F018 . F018GCD = TESTITEM . F010GCD 
			ORDER BY F010COR , F010FKN , F010GCD , F010TCD 
			; 
			SET O_MSGCOD = '200' ; 
		OPEN CUR1 ; 
		END ; 
		 -- 보험코드 
ELSEIF I_SERFNM = '04' THEN 
	BEGIN 
	 -- 검사항목조회 
	DECLARE CUR2 CURSOR WITH RETURN FOR 
	WITH TESTITEM AS ( 
	SELECT 
	TRIM ( F010COR ) F010COR  --COR 
	, TRIM ( F010GCD ) F010GCD  -- 검사코드 
	, TRIM ( F010SNM ) F010SNM  -- 검사약어 
	, TRIM ( F010FKN ) F010FKN  -- 검사명(한글) 
	, TRIM ( F010TCD ) F010TCD  -- 검체 코드 (F999CD2 ='SAMP' ) 
	, ( SELECT TRIM ( MC9 . F999NM2 ) 
	FROM MCLISDLIB . MC999D@ MC9 
	WHERE F999COR = F010COR 
			AND F999CD1 = 'CLIC' 
	AND F999CD2 = 'SAMP' 
	AND F999CD3 = F010TCD 
	) AS F010TNM 
	, TRIM ( F010GBX ) F010GBX  -- 검체용기코드 (F999CD2 ='VESS' ) 
	, TRIM ( F010MSC ) F010MSC  -- 검사법 
	, ( 
		SELECT 
						CONCAT ( CONCAT ( LISTAGG ( F028TXT1 , ' ' ) , LISTAGG ( F028TXT2 , ' ' ) ) , LISTAGG ( F028TXT3 , ' ' ) ) 
					FROM ( 
						SELECT CASE WHEN F028SEQ = 0 
					THEN TRIM ( F028TXT ) 
					ELSE '' 
					END AS F028TXT1 , 
							CASE WHEN F028SEQ = 1 
					THEN TRIM ( F028TXT ) 
					ELSE '' 
					END AS F028TXT2 , 
							CASE WHEN F028SEQ = 2 
					THEN TRIM ( F028TXT ) 
					ELSE '' 
					END AS F028TXT3 
					, F028GCD , F028COR 
						FROM MCLISDLIB . MC028D@ MC28 
						WHERE F028COR = I_COR 
					) AS MC28 
						WHERE MC28 . F028COR = F010COR 
						AND MC28 . F028GCD = F010MSC 
					GROUP BY F028GCD , F028COR 
	) AS F010MSNM 
	, CONCAT ( 
						CASE ( 
							CONCAT ( 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
														, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN '화' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN '수' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN '목' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN '금' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN '토' ELSE '' END 
								) 
								, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN '일' ELSE '' END 
							) 
						) WHEN '월화' THEN '월-화' 
							WHEN '월화수' THEN '월-수' 
							WHEN '월화수목' THEN '월-목' 
							WHEN '월화수목금' THEN '월-금' 
							WHEN '월화수목금토' THEN '월-토' 
							WHEN '월화수목금토일' THEN '월-일' 
							WHEN '화수' THEN '화-수' 
							WHEN '화수목' THEN '화-목' 
							WHEN '화수목금' THEN '화-금' 
							WHEN '화수목금토' THEN '화-토' 
							WHEN '화수목금토일' THEN '화-일' 
							WHEN '수목' THEN '수-목' 
							WHEN '수목금' THEN '수-금' 
							WHEN '수목금토' THEN '수-토' 
							WHEN '수목금토일' THEN '수-일' 
							WHEN '목금' THEN '목-금' 
							WHEN '목금토' THEN '목-토' 
							WHEN '목금토일' THEN '목-일' 
							WHEN '금토' THEN '금-토' 
							WHEN '금토일' THEN '금-일' 
							WHEN '토일' THEN '토-일' 
						ELSE 
							CASE SUBSTRING ( CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) , 1 , 1 ) 
							WHEN ',' 
							THEN 
								SUBSTRING ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
									) , 2 , LENGTH ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( 
														CONCAT ( 
															CONCAT ( 
																CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
																		, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
																) 
																, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
															) 
															, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
														) 
														, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
											) 
										) - 1 
								) 
							ELSE 
								CONCAT ( 
									CONCAT ( 
										CONCAT ( 
											CONCAT ( 
												CONCAT ( 
													CONCAT ( CASE SUBSTRING ( F010DOW , 1 , 1 ) WHEN 'O' THEN '월' ELSE '' END 
															, CASE SUBSTRING ( F010DOW , 2 , 1 ) WHEN 'O' THEN ',화' ELSE '' END 
													) 
													, CASE SUBSTRING ( F010DOW , 3 , 1 ) WHEN 'O' THEN ',수' ELSE '' END 
												) 
												, CASE SUBSTRING ( F010DOW , 4 , 1 ) WHEN 'O' THEN ',목' ELSE '' END 
											) 
											, CASE SUBSTRING ( F010DOW , 5 , 1 ) WHEN 'O' THEN ',금' ELSE '' END 
										) 
										, CASE SUBSTRING ( F010DOW , 6 , 1 ) WHEN 'O' THEN ',토' ELSE '' END 
									) 
									, CASE SUBSTRING ( F010DOW , 7 , 1 ) WHEN 'O' THEN ',일' ELSE '' END 
								) 
							END 
						END 
		, CASE F010DNC WHEN 'A' THEN '' ELSE CONCAT ( '/' , CASE F010DNC WHEN 'D' THEN '주간' 
																	WHEN 'N' THEN '야간' 
														ELSE '' END 
													) END 
		) T001DAY  -- 검사일 
	, CAST ( CONCAT ( ( F010EED ) , '일' ) AS VARCHAR ( 20 ) )	F010EED  -- 검사소요일수 
	, TRIM ( T001FLG )	T001FLG  -- 사용여부 
	, TRIM ( T001LFLG )	T001LFLG  --- 리스트사용여부 
	, TRIM ( F010STS )	F010STS  -- 상태 
	FROM MCLISDLIB . MC010M@ MC010 
	, WEBDBLIB . MWT001M@ MWT001 
	WHERE MC010 . F010COR = MWT001 . T001COR 
	AND MC010 . F010GCD = MWT001 . T001TCD 
	AND MC010 . F010COR = I_COR 
	AND F010STS = 'Y'	 --사용여부 
	AND F010ACH = 'Y'	 --웹여부 
	AND T001FLG = 'Y' 
	AND T001LFLG = 'Y' 
	AND ( I_ALHNM = '' OR UPPER ( F010FKN ) LIKE UPPER ( I_ALHNM ) || '%' ) 
	AND ( I_HAK = '' OR F010HAK = I_HAK ) 
	AND ( I_ISC = '' OR F010ISC IN ( 
				SELECT SUBSTR ( NAME , 1 , INSTR ( NAME , '|' ) - 1 ) DWARF 
				FROM ( 
					SELECT N , SUBSTR ( VAL , 1 + INSTR ( VAL , '|' , 1 , N ) ) NAME 
				FROM ( 
						SELECT ROW_NUMBER ( ) OVER ( ) AS N , LIST . VAL 
				FROM ( SELECT I_ISC VAL 
				FROM SYSIBM . SYSDUMMY1 ) LIST 
				CONNECT BY LEVEL < 
					( LENGTH ( LIST . VAL ) 
							- LENGTH ( REPLACE ( LIST . VAL , '|' , '' ) ) 
						) 
				) AS BB 
				) CC 
		) 
	) 
	) 
	, F018 AS ( 
	SELECT F018OCD , F018GCD , F018COR 
	FROM ( 
		SELECT 
	MAX ( F018OCD0 ) AS F018OCD 
	, MAX ( F018OCD1 )
	, MAX ( F018OCD2 ) 
	, F018GCD , F018COR 
	FROM ( 
	SELECT 
	CASE WHEN 
					TRIM ( F018LMB ) = '9999'	--기본 보험코드 
				THEN TRIM ( F018OCD ) 
	END F018OCD0
	,
	CASE WHEN 
					TRIM ( F018LMB ) = ''	 --서울 보험코드 
				THEN TRIM ( F018OCD ) 
	END F018OCD1 
	, CASE WHEN 
				TRIM ( F018LMB ) = '6526'	 -- 부산 보험코드 
				THEN TRIM ( F018OCD ) 
	END F018OCD2 
	, TRIM ( F018GCD ) AS F018GCD , F018COR 
	FROM MCLISDLIB . MC018M@ A 
	WHERE F018COR = I_COR 
	AND F018OSD = 
		( SELECT MAX ( F018OSD ) 
	FROM MCLISDLIB . MC018M@ B 
	WHERE A . F018COR = B . F018COR 
	AND A . F018GCD = B . F018GCD ) 
	OR ( '04' = I_SERFNM 
	AND UPPER ( F018OCD ) LIKE UPPER ( I_SERNM ) || '%' )  -- 보험 코드 
	) A 
	GROUP BY F018GCD , F018COR 
	) A 
	) 
	SELECT F018OCD 
	, TESTITEM . * 
	FROM TESTITEM 
	LEFT JOIN F018 
		ON F018 . F018COR = TESTITEM . F010COR 
		AND F018 . F018GCD = TESTITEM . F010GCD 
	WHERE UPPER ( F018OCD ) LIKE '%' || UPPER ( I_SERNM ) || '%'  -- 보험 코드 
		ORDER BY F010COR , F010FKN , F010GCD , F010TCD 
		; 
		SET O_MSGCOD = '200' ; 
	OPEN CUR2 ; 
	END ; 
END IF ; 
END  ; 

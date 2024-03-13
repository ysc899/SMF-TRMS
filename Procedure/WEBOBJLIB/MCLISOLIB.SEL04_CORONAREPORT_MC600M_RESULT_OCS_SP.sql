--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/09/02 08:46:48 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE MCLISOLIB.SEL04_CORONAREPORT_MC600M_RESULT_OCS_SP ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE MCLISOLIB.SEL04_CORONAREPORT_MC600M_RESULT_OCS_SP ( 
  IN $P_COR CHAR(3) , 
  IN $P_DGN VARCHAR(1) , 
  IN $P_FDAT VARCHAR(8) , 
  IN $P_TDAT VARCHAR(8) , 
  IN $P_LOGID VARCHAR(50) , 
  IN $P_HOSCD VARCHAR(5) , 
  IN $P_DOWN_YN VARCHAR(5) , 
  IN $P_CORONA_ASSEMBLE VARCHAR(10) , 
  IN $P_CORONA_RESULT CHAR(20) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC MCLISOLIB.SEL04_CORONAREPORT_MC600M_RESULT_OCS_SP 
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
	DECLARE CUR1 CURSOR FOR 
		SELECT 
			ROWNUMBER ( ) OVER ( ) AS ROWNUM 
			, BB . F600DAT AS DAT 
			, BB . F600JNO AS JNO 
			 -- 2022.01.06 복호화 제거 
			 --, RTRIM ( ECHELON . PI_DECSN ( AA . F100CHN ) ) AS CHN 
			, RTRIM ( AA . F100CHN ) AS CHN 
			 --, trim(CONCAT(CONCAT(SUBSTRING(AA.F100BDT,3,6), '-'),AA.F100JN2)) AS JUNO 
			 -- 2022.01.06 복호화 제거 
			 -- , TRIM ( CASE WHEN AA . F100JN2 != '' THEN CONCAT ( CONCAT ( SUBSTRING ( ECHELON . PI_DECSN ( AA . F100JN1 ) , 1 , 6 ) , '-' ) , SUBSTRING ( ECHELON . PI_DECSN ( AA . F100JN2 ) , 1 , 2 ) ) 
			 --ELSE SUBSTRING ( ECHELON . PI_DECSN ( AA . F100JN1 ) , 1 , 6 ) END 
			 --, TRIM ( CASE WHEN AA . F100JN2 != '' THEN CONCAT ( CONCAT ( SUBSTRING ( AA . F100JN1 , 1 , 6 ) , '-' ) , SUBSTRING ( AA . F100JN2 , 1 , 2 ) ) 
			 --			ELSE SUBSTRING ( AA . F100JN1 , 1 , 6 ) END 
			 --) AS JUNO 
			 --, CONCAT ( CONCAT ( TRIM ( AA . F100JN1 ) , '-' ) , TRIM ( AA . F100JN2 ) ) AS JUNO 
, ( CASE WHEN TRIM ( AA . F100JN1 ) <> '' AND TRIM ( AA . F100JN2 ) <> '' THEN CONCAT ( CONCAT ( TRIM ( AA . F100JN1 ) , '-' ) , TRIM ( AA . F100JN2 ) ) 
ELSE SUBSTRING ( TRIM ( AA . F100BDT ) , 3 , 6 ) END ) AS JUNO 
			 --, RTRIM ( AA . F100NAM ) AS NAM 
			, RTRIM ( REPLACE ( REPLACE ( AA . F100NAM , UX'001A' , '' ) , UX'FFFD' , '' ) ) AS NAM 
			, AA . F100SEX AS SEX 
			, AA . F100AGE AS AGE 
			, RTRIM ( REPLACE(AA . F100ETC,'','') ) AS ETC 
			, COALESCE ( RTRIM ( S100TMP1 ) , '' ) AS S100TMP1 
			, RTRIM ( AA . F100CBC ) AS CBC 
			, ( SELECT RTRIM ( CC . F010FKN ) FROM MCLISDLIB . MC010M@ CC WHERE CC . F010COR = $P_COR AND CC . F010GCD = BB . F600GCD ) AS FKN 
			, RTRIM ( BB . F600CHR ) AS RST 
, IFNULL ( ( CASE WHEN UPPER ( BB . F600CHR ) = 'NEGATIVE' AND BB . F600GCD IN ( '89269' , '89274' ) THEN TRIM ( NCOVEGN ) 
							WHEN UPPER ( BB . F600CHR ) = 'POSITIVE' THEN TRIM ( NCOVEGN ) 
							WHEN UPPER ( BB . F600CHR ) LIKE 'INCONCLUSIVE%' THEN TRIM ( NCOVEGN ) 
							ELSE '' END ) 
				, '' ) AS EGN 
			, IFNULL ( ( CASE WHEN UPPER ( BB . F600CHR ) = 'NEGATIVE' AND BB . F600GCD IN ( '89269' , '89274' ) THEN TRIM ( NCOVRGN ) 
							WHEN UPPER ( BB . F600CHR ) = 'POSITIVE' THEN TRIM ( NCOVRGN ) 
							WHEN UPPER ( BB . F600CHR ) LIKE 'INCONCLUSIVE%' THEN TRIM ( NCOVRGN ) 
							ELSE '' END ) 
, '' ) AS RGN 
			, IFNULL ( ( CASE WHEN UPPER ( BB . F600CHR ) = 'NEGATIVE' AND BB . F600GCD IN ( '89269' , '89274' ) THEN TRIM ( NCOVNGN ) 
							WHEN UPPER ( BB . F600CHR ) = 'POSITIVE' THEN TRIM ( NCOVNGN ) 
							WHEN UPPER ( BB . F600CHR ) LIKE 'INCONCLUSIVE%' THEN TRIM ( NCOVNGN ) 
							ELSE '' END ) 
, '' ) AS NGN 
			, ( SELECT RTRIM ( DD . F024TXT ) FROM MCLISDLIB . MC024D@ DD WHERE DD . F024COR = $P_COR AND DD . F024GCD = BB . F600GCD AND F024LNG = 'K' ) AS REF 
			, BB . F600BDT AS BDT 
			, ( CASE WHEN ( BB . F600C03 = 'Y' AND ( 
													BB . F600CHR LIKE '%Positive%' 
													OR BB . F600CHR LIKE '%Inconclusive%' 
													OR BB . F600CHR LIKE '%Negative%' ) 
													) 
						OR ( BB . F600C03 = 'Y' AND BB . F600CHR LIKE '%개별%' AND ( 
																				SU . F600CHR LIKE '%Negative%' 
																				OR SU . F600CHR LIKE '%Positive%' 
																				OR SU . F600CHR LIKE '%Inconclusive%' 
																			) 
						) THEN '완료' 
					WHEN ( BB . F600C03 = 'Y' AND BB . F600CHR LIKE '%재검%' ) THEN '재검' 
				ELSE '검사중' END 
			) AS STSD 
			, CONCAT ( CONCAT ( BB . F600DAT , BB . F600JNO ) , BB . F600GCD ) AS TREE 
			, ( SELECT F120FNM FROM MCLISDLIB . "MC120M@" WHERE 1 = 1 AND F120COR = 'NML' AND F120PCD = AA . F100HOS ) AS HOS 
			, ( CASE WHEN BB . F600C03 = 'Y' 
					THEN ( CASE WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 6 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( SUBSTRING ( BB . F600CTM , 1 , 2 ) , ':' ) , SUBSTRING ( BB . F600CTM , 3 , 2 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 5 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 5 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( SUBSTRING ( BB . F600CTM , 1 , 1 ) , ':' ) , SUBSTRING ( BB . F600CTM , 2 , 2 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 4 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 4 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':' ) , SUBSTRING ( BB . F600CTM , 1 , 2 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 3 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 3 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':0' ) , SUBSTRING ( BB . F600CTM , 1 , 1 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 2 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 2 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':' ) , '00' ) , ':' ) , SUBSTRING ( BB . F600CTM , 1 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 1 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':' ) , '00' ) , ':0' ) , SUBSTRING ( BB . F600CTM , 1 , 1 ) ) 
						ELSE '' END ) 
				ELSE '' END 
			) AS CTM 
			, ( S100TMP10 ) AS ADDRESS 
		FROM MCLISDLIB . MC100H@ AA 
		LEFT OUTER JOIN MCLISDLIB . MC600M@ BB 
		ON AA . F100COR = BB . F600COR 
		AND AA . F100DAT = BB . F600DAT 
		AND AA . F100JNO = BB . F600JNO 
		LEFT OUTER JOIN MCLISDLIB . NCOVCT@ EE 
		ON AA . F100COR = NCOVCOR 
		AND AA . F100DAT = NCOVDAT 
		AND AA . F100JNO = NCOVJNO 
		LEFT OUTER JOIN MCLISDLIB . MC100HSUB@ FF 
		ON AA . F100COR = S100COR 
		AND AA . F100DAT = S100DAT 
		AND AA . F100JNO = S100JNO 
		AND AA . F100HOS = S100HOS 
		LEFT OUTER JOIN MCLISDLIB . MC600M@ SU 
		ON AA . F100COR = SU . F600COR 
		AND AA . F100DAT = SU . F600DAT 
		AND AA . F100JNO = SU . F600JNO 
		AND SU . F600GCD IN ( '71337' , '71340' , '71346' , '89272' ) 
		WHERE BB . F600COR = $P_COR 
		AND ( 
			/***** 조건1) 연도가 같은경우 *****/ 
			( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN $P_FDAT AND ( CASE WHEN $P_TDAT > '20221231' 
											THEN '20221231' 
									ELSE $P_TDAT END ) 
			/***** 조건2) 연도가 다른경우 + 2021년 포함인 경우 --> 2027년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20270101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20271231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건3) 연도가 다른경우 + 2021년 포함인 경우 --> 2028년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20280101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20281231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건4) 연도가 다른경우 + 2021년 포함인 경우 --> 2029년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20290101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20291231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건5) 연도가 다른경우 + 2021년 포함인 경우 --> 2030년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20300101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20301231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건6) 연도가 다른경우 + 2022년 포함인 경우 --> 2023년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20230101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20231231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건7) 연도가 다른경우 + 2022년 포함인 경우 --> 2024년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20240101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20241231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건8) 연도가 다른경우 + 2022년 포함인 경우 --> 2025년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20250101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20251231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건9) 연도가 다른경우 + 2022년 포함인 경우 --> 2026년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20260101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20261231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건10) 연도가 다른경우 + 2022년 포함인 경우 --> 2037년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20370101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20371231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건11) 연도가 다른경우 + 2022년 포함인 경우 --> 2038년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20380101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20381231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건12) 연도가 다른경우 + 2022년 포함인 경우 --> 2039년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20390101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20391231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
		) 
		AND BB . F600GCD IN ( SELECT F999CD3 FROM MCLISDLIB . MC999D@ 
								WHERE F999COR = $P_COR 
								AND F999CD1 = 'CLIC' 
								AND F999CD2 = 'COVI' 
								AND ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN F999CD3 ELSE '1' END ) <> ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN '71335' ELSE '2' END ) 
								AND ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN F999CD3 ELSE '1' END ) <> ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN '71336' ELSE '2' END ) 
								AND ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN F999CD3 ELSE '1' END ) <> ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN '71339' ELSE '2' END ) 
							) 
		AND BB . F600ACD IN ( '' , '01' ) 
		AND ( BB . F600HOS IN ( SELECT F999NM1 FROM MCLISDLIB . MC999D@ WHERE F999COR = $P_COR AND F999CD1 = 'CLIC' AND F999CD2 = 'SAA2' AND UPPER ( F999CD3 ) LIKE '' || UPPER ( $P_LOGID ) || '%' ) 
			OR BB . F600HOS = ( SELECT LOGHOS FROM MCLISDLIB . MCLOGI@ WHERE LOGCOR = $P_COR AND UPPER ( LOGLID ) = UPPER ( $P_LOGID ) ) ) 
		AND ( CASE WHEN $P_DOWN_YN = 'X' THEN BB . F600C15 ELSE '' END ) = '' 
AND TRIM ( UPPER ( BB . F600CHR ) ) LIKE ( CASE WHEN TRIM ( UPPER ( $P_CORONA_RESULT ) ) = '' THEN TRIM ( UPPER ( BB . F600CHR ) ) 
ELSE '%' || TRIM ( UPPER ( $P_CORONA_RESULT ) ) || '%' END ) 
		ORDER BY BB . F600DAT , BB . F600JNO ; 
	DECLARE CUR2 CURSOR FOR 
		SELECT 
			ROWNUMBER ( ) OVER ( ) AS ROWNUM 
			, BB . F600DAT AS DAT 
			, BB . F600JNO AS JNO 
			 -- 2022.01.06 복호화 제거 
			 --, RTRIM ( ECHELON . PI_DECSN ( AA . F100CHN ) ) AS CHN 
			, RTRIM ( AA . F100CHN ) AS CHN 
			 --, trim(CONCAT(CONCAT(SUBSTRING(AA.F100BDT,3,6), '-'),AA.F100JN2)) AS JUNO 
			 -- 2022.01.06 복호화 제거 
			 -- , TRIM ( CASE WHEN AA . F100JN2 != '' THEN CONCAT ( CONCAT ( SUBSTRING ( ECHELON . PI_DECSN ( AA . F100JN1 ) , 1 , 6 ) , '-' ) , SUBSTRING ( ECHELON . PI_DECSN ( AA . F100JN2 ) , 1 , 2 ) ) 
			 --ELSE SUBSTRING ( ECHELON . PI_DECSN ( AA . F100JN1 ) , 1 , 6 ) END 
			 --, TRIM ( CASE WHEN AA . F100JN2 != '' THEN CONCAT ( CONCAT ( SUBSTRING ( AA . F100JN1 , 1 , 6 ) , '-' ) , SUBSTRING ( AA . F100JN2 , 1 , 2 ) ) 
			 --				ELSE SUBSTRING ( AA . F100JN1 , 1 , 6 ) END 
			 --) AS JUNO 
			 --, CONCAT ( CONCAT ( TRIM ( AA . F100JN1 ) , '-' ) , TRIM ( AA . F100JN2 ) ) AS JUNO 
, ( CASE WHEN TRIM ( AA . F100JN1 ) <> '' AND TRIM ( AA . F100JN2 ) <> '' THEN CONCAT ( CONCAT ( TRIM ( AA . F100JN1 ) , '-' ) , TRIM ( AA . F100JN2 ) ) 
ELSE SUBSTRING ( TRIM ( AA . F100BDT ) , 3 , 6 ) END ) AS JUNO 
			 --, RTRIM ( AA . F100NAM ) AS NAM 
			, RTRIM ( REPLACE ( REPLACE ( AA . F100NAM , UX'001A' , '' ) , UX'FFFD' , '' ) ) AS NAM 
			, AA . F100SEX AS SEX 
			, AA . F100AGE AS AGE 
			, RTRIM ( REPLACE(AA . F100ETC,'','') ) AS ETC 
			, COALESCE ( RTRIM ( S100TMP1 ) , '' ) AS S100TMP1 
			, RTRIM ( AA . F100CBC ) AS CBC 
			, ( SELECT RTRIM ( CC . F010FKN ) FROM MCLISDLIB . MC010M@ CC WHERE CC . F010COR = $P_COR AND CC . F010GCD = BB . F600GCD ) AS FKN 
			, RTRIM ( BB . F600CHR ) AS RST 
, IFNULL ( ( CASE WHEN UPPER ( BB . F600CHR ) = 'NEGATIVE' AND BB . F600GCD IN ( '89269' , '89274' ) THEN TRIM ( NCOVEGN ) 
							WHEN UPPER ( BB . F600CHR ) = 'POSITIVE' THEN TRIM ( NCOVEGN ) 
							WHEN UPPER ( BB . F600CHR ) LIKE 'INCONCLUSIVE%' THEN TRIM ( NCOVEGN ) 
							ELSE '' END ) 
				, '' ) AS EGN 
			, IFNULL ( ( CASE WHEN UPPER ( BB . F600CHR ) = 'NEGATIVE' AND BB . F600GCD IN ( '89269' , '89274' ) THEN TRIM ( NCOVRGN ) 
							WHEN UPPER ( BB . F600CHR ) = 'POSITIVE' THEN TRIM ( NCOVRGN ) 
							WHEN UPPER ( BB . F600CHR ) LIKE 'INCONCLUSIVE%' THEN TRIM ( NCOVRGN ) 
							ELSE '' END ) 
, '' ) AS RGN 
			, IFNULL ( ( CASE WHEN UPPER ( BB . F600CHR ) = 'NEGATIVE' AND BB . F600GCD IN ( '89269' , '89274' ) THEN TRIM ( NCOVNGN ) 
							WHEN UPPER ( BB . F600CHR ) = 'POSITIVE' THEN TRIM ( NCOVNGN ) 
							WHEN UPPER ( BB . F600CHR ) LIKE 'INCONCLUSIVE%' THEN TRIM ( NCOVNGN ) 
							ELSE '' END ) 
, '' ) AS NGN 
			, ( SELECT RTRIM ( DD . F024TXT ) FROM MCLISDLIB . MC024D@ DD WHERE DD . F024COR = $P_COR AND DD . F024GCD = BB . F600GCD AND F024LNG = 'K' ) AS REF 
			, BB . F600BDT AS BDT 
			, ( CASE WHEN ( BB . F600C03 = 'Y' AND ( 
													BB . F600CHR LIKE '%Positive%' 
													OR BB . F600CHR LIKE '%Inconclusive%' 
													OR BB . F600CHR LIKE '%Negative%' ) 
													) 
						OR ( BB . F600C03 = 'Y' AND BB . F600CHR LIKE '%개별%' AND ( 
																				SU . F600CHR LIKE '%Negative%' 
																				OR SU . F600CHR LIKE '%Positive%' 
																				OR SU . F600CHR LIKE '%Inconclusive%' 
																			) 
						) THEN '완료' 
					WHEN ( BB . F600C03 = 'Y' AND BB . F600CHR LIKE '%재검%' ) THEN '재검' 
				ELSE '검사중' END 
			) AS STSD 
			, CONCAT ( CONCAT ( BB . F600DAT , BB . F600JNO ) , BB . F600GCD ) AS TREE 
			, ( SELECT F120FNM FROM MCLISDLIB . "MC120M@" WHERE 1 = 1 AND F120COR = 'NML' AND F120PCD = AA . F100HOS ) AS HOS 
			, ( CASE WHEN BB . F600C03 = 'Y' 
					THEN ( CASE WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 6 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( SUBSTRING ( BB . F600CTM , 1 , 2 ) , ':' ) , SUBSTRING ( BB . F600CTM , 3 , 2 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 5 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 5 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( SUBSTRING ( BB . F600CTM , 1 , 1 ) , ':' ) , SUBSTRING ( BB . F600CTM , 2 , 2 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 4 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 4 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':' ) , SUBSTRING ( BB . F600CTM , 1 , 2 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 3 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 3 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':0' ) , SUBSTRING ( BB . F600CTM , 1 , 1 ) ) , ':' ) , SUBSTRING ( BB . F600CTM , 2 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 2 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':' ) , '00' ) , ':' ) , SUBSTRING ( BB . F600CTM , 1 , 2 ) ) 
								WHEN LENGTH ( TRIM ( BB . F600CTM ) ) = 1 THEN CONCAT ( CONCAT ( CONCAT ( CONCAT ( '00' , ':' ) , '00' ) , ':0' ) , SUBSTRING ( BB . F600CTM , 1 , 1 ) ) 
						ELSE '' END ) 
				ELSE '' END 
			) AS CTM 
			, ( S100TMP10 ) AS ADDRESS 
		FROM MCLISDLIB . MC100H@ AA 
		LEFT OUTER JOIN MCLISDLIB . MC600M@ BB 
		ON AA . F100COR = BB . F600COR 
		AND AA . F100DAT = BB . F600DAT 
		AND AA . F100JNO = BB . F600JNO 
		LEFT OUTER JOIN MCLISDLIB . NCOVCT@ EE 
		ON AA . F100COR = NCOVCOR 
		AND AA . F100DAT = NCOVDAT 
		AND AA . F100JNO = NCOVJNO 
		LEFT OUTER JOIN MCLISDLIB . MC100HSUB@ FF 
		ON AA . F100COR = S100COR 
		AND AA . F100DAT = S100DAT 
		AND AA . F100JNO = S100JNO 
		AND AA . F100HOS = S100HOS 
		LEFT OUTER JOIN MCLISDLIB . MC600M@ SU 
		ON AA . F100COR = SU . F600COR 
		AND AA . F100DAT = SU . F600DAT 
		AND AA . F100JNO = SU . F600JNO 
		AND SU . F600GCD IN ( '71337' , '71340' , '71346' , '89272' ) 
		WHERE BB . F600COR = $P_COR 
		AND ( 
			/***** 조건1) 연도가 같은경우 *****/ 
			( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN $P_FDAT AND ( CASE WHEN $P_TDAT > '20221231' 
											THEN '20221231' 
										ELSE $P_TDAT END ) 
			/***** 조건2) 연도가 다른경우 + 2021년 포함인 경우 --> 2027년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20270101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20271231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건3) 연도가 다른경우 + 2021년 포함인 경우 --> 2028년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20280101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20281231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건4) 연도가 다른경우 + 2021년 포함인 경우 --> 2029년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20290101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20291231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건5) 연도가 다른경우 + 2021년 포함인 경우 --> 2030년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20300101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20301231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건6) 연도가 다른경우 + 2022년 포함인 경우 --> 2023년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20230101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20231231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건7) 연도가 다른경우 + 2022년 포함인 경우 --> 2024년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20240101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20241231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건8) 연도가 다른경우 + 2022년 포함인 경우 --> 2025년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20250101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20251231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건9) 연도가 다른경우 + 2022년 포함인 경우 --> 2026년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20260101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20261231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건10) 연도가 다른경우 + 2022년 포함인 경우 --> 2037년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20370101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20371231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건11) 연도가 다른경우 + 2022년 포함인 경우 --> 2038년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20380101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20381231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
			/***** 조건12) 연도가 다른경우 + 2022년 포함인 경우 --> 2039년 *****/ 
			OR ( CASE WHEN $P_DGN = 'D' THEN BB . F600DAT ELSE BB . F600BDT END ) 
				BETWEEN ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20390101' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
						ELSE $P_FDAT END 
				) AND ( 
					CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20391231' 
						WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
						ELSE $P_TDAT END 
				) 
		) 
		AND BB . F600GCD IN ( SELECT F999CD3 FROM MCLISDLIB . MC999D@ 
								WHERE F999COR = $P_COR 
								AND F999CD1 = 'CLIC' 
								AND F999CD2 = 'COVI' 
								AND ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN F999CD3 ELSE '1' END ) <> ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN '71335' ELSE '2' END ) 
								AND ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN F999CD3 ELSE '1' END ) <> ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN '71336' ELSE '2' END ) 
								AND ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN F999CD3 ELSE '1' END ) <> ( CASE WHEN $P_CORONA_ASSEMBLE = 'ASSEMBLE_N' THEN '71339' ELSE '2' END ) 
							) 
		AND BB . F600ACD IN ( '' , '01' ) 
		AND BB . F600HOS = $P_HOSCD 
		AND ( CASE WHEN $P_DOWN_YN = 'X' THEN BB . F600C15 ELSE '' END ) = '' 
AND TRIM ( UPPER ( BB . F600CHR ) ) LIKE ( CASE WHEN TRIM ( UPPER ( $P_CORONA_RESULT ) ) = '' THEN TRIM ( UPPER ( BB . F600CHR ) ) 
ELSE '%' || TRIM ( UPPER ( $P_CORONA_RESULT ) ) || '%' END ) 
		ORDER BY BB . F600DAT , BB . F600JNO ; 
		 
	IF LENGTH ( TRIM ( $P_HOSCD ) ) = 5 THEN 
		OPEN CUR2 ; 
		SET RESULT SETS CURSOR CUR2 ; 
	ELSE 
		OPEN CUR1 ; 
		SET RESULT SETS CURSOR CUR1 ; 
	END IF ; 
END  ; 

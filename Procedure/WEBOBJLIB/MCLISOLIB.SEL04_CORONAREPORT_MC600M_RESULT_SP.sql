--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/03/22 18:19:36 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE MCLISOLIB.SEL04_CORONAREPORT_MC600M_RESULT_SP ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE MCLISOLIB.SEL04_CORONAREPORT_MC600M_RESULT_SP ( 
  IN $P_COR CHAR(3) , 
  IN $P_FDAT VARCHAR(8) , 
  IN $P_TDAT VARCHAR(8) , 
  IN $P_LOGID VARCHAR(50) , 
  IN $P_HOSCD VARCHAR(5) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC MCLISOLIB.SEL04_CORONAREPORT_MC600M_RESULT_SP 
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
IFNULL ( MA . F600HOS , '' ) AS HOSCD  --병원코드 
, IFNULL ( F120FNM , '총 계' ) AS HOSNM  --병원명 
, SUM ( CASE WHEN MA . F600GCD IN ( '71330' , '71331' , '71334' , '71338' , '71341' , '71342' , '71349' , '71350' , '71351' , '89269' , '89274' ) THEN 1 ELSE 0 END ) AS JSCNT  --접수건수 
, SUM ( CASE WHEN MA . F600C03 = 'Y' AND MA . F600GCD IN ( '71330' , '71331' , '71334' , '71338' , '71341' , '71342' , '71349' , '71350' , '71351' , '89269' , '89274' ) THEN 1 ELSE 0 END ) AS GSCNT  --검사건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%Positive%' ) OR ( MA . F600CHR LIKE '%개별%' AND SU . F600CHR LIKE '%Positive%' ) THEN 1 ELSE 0 END ) AS NECNT  --양성건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%Inconclusive%' ) OR ( MA . F600CHR LIKE '%개별%' AND SU . F600CHR LIKE '%Inconclusive%' ) THEN 1 ELSE 0 END ) AS INCNT  --미결정건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%Negative%' ) OR ( MA . F600CHR LIKE '%개별%' AND SU . F600CHR LIKE '%Negative%' ) THEN 1 ELSE 0 END ) AS POCNT  --음성건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND ( MA . F600CHR LIKE '%Negative%' OR MA . F600CHR LIKE '%Positive%' OR MA . F600CHR LIKE '%Inconclusive%' ) ) 
		OR ( MA . F600CHR LIKE '%개별%' AND ( SU . F600CHR LIKE '%Negative%' OR SU . F600CHR LIKE '%Positive%' OR SU . F600CHR LIKE '%Inconclusive%' ) ) THEN 1 ELSE 0 END ) AS RSCNT  --최종보고결과건수계 
, SUM ( CASE WHEN MA . F600C03 <> 'Y' OR ( MA . F600CHR LIKE '%개별%' AND ( ( SU . F600CHR NOT LIKE '%Negative%' 
										AND SU . F600CHR NOT LIKE '%Positive%' 
										AND SU . F600CHR NOT LIKE '%Inconclusive%' 
AND SU . F600CHR NOT LIKE '%재검%' ) OR SU . F600C03 IS NULL ) ) 
					OR ( MA . F600C03 = 'Y' AND MA . F600CHR NOT LIKE '%재검%' 
AND MA . F600CHR NOT LIKE '%Negative%' 
AND MA . F600CHR NOT LIKE '%Positive%' 
AND MA . F600CHR NOT LIKE '%Inconclusive%' 
AND MA . F600CHR NOT LIKE '%개별%' ) THEN 1 ELSE 0 END ) AS INGCNT  --검사중건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND ( MA . F600CHR LIKE '%재검%' OR SU . F600CHR LIKE '%재검%' ) ) THEN 1 ELSE 0 END ) AS RECNT  --재검건수 
FROM MCLISDLIB . MC600M@ MA 
INNER JOIN MCLISDLIB . MC120M@ 
ON MA . F600COR = F120COR 
AND MA . F600HOS = F120PCD 
LEFT OUTER JOIN MCLISDLIB . MC600M@ SU 
ON MA . F600COR = SU . F600COR 
AND MA . F600DAT = SU . F600DAT 
AND MA . F600JNO = SU . F600JNO 
AND SU . F600GCD IN ( '71337' , '71340' , '71346' , '89272' ) 
WHERE MA . F600COR = $P_COR 
AND 
( 
	/***** 조건1) 연도가 같은경우 *****/ 
	MA . F600DAT BETWEEN $P_FDAT AND ( CASE WHEN $P_TDAT > '20221231' 
THEN '20221231' 
ELSE $P_TDAT END ) 
	OR 
	/***** 조건1-1) 연도가 다른경우 + 2020년12월 포함인 경우 --> 2020년12월 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 6 ) < '202012' AND '202012' <= SUBSTRING ( $P_TDAT , 1 , 6 ) THEN '20001201' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 6 ) = '202012' AND '202012' <= SUBSTRING ( $P_TDAT , 1 , 6 ) THEN CONCAT ( '2000' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 6 ) <= '202012' AND '202012' < SUBSTRING ( $P_TDAT , 1 , 6 ) THEN '20001231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 6 ) <= '202012' AND '202012' = SUBSTRING ( $P_TDAT , 1 , 6 ) THEN CONCAT ( '2000' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건2) 연도가 다른경우 + 2021년 포함인 경우 --> 2027년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20270101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20271231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건3) 연도가 다른경우 + 2021년 포함인 경우 --> 2028년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20280101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20281231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건4) 연도가 다른경우 + 2021년 포함인 경우 --> 2029년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20290101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20291231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건5) 연도가 다른경우 + 2021년 포함인 경우 --> 2030년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20300101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20301231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건6) 연도가 다른경우 + 2022년 포함인 경우 --> 2023년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20230101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20231231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건7) 연도가 다른경우 + 2022년 포함인 경우 --> 2024년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20240101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20241231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건8) 연도가 다른경우 + 2022년 포함인 경우 --> 2025년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20250101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20251231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건9) 연도가 다른경우 + 2022년 포함인 경우 --> 2026년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20260101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20261231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건10) 연도가 다른경우 + 2022년 포함인 경우 --> 2037년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20370101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20371231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건11) 연도가 다른경우 + 2022년 포함인 경우 --> 2038년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20380101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20381231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건12) 연도가 다른경우 + 2022년 포함인 경우 --> 2039년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20390101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20391231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
) 
-- AND 
-- ( 
-- MA . F600DAT BETWEEN $P_FDAT AND $P_TDAT 
-- OR MA . F600DAT BETWEEN REPLACE ( REPLACE ( $P_FDAT , '2021' , '2029' ) , '202012' , '202312' ) AND REPLACE ( REPLACE ( $P_TDAT , '2021' , '2029' ) , '202012' , '202312' ) 
-- OR MA . F600DAT BETWEEN REPLACE ( REPLACE ( $P_FDAT , '2021' , '2030' ) , '202012' , '202312' ) AND REPLACE ( REPLACE ( $P_TDAT , '2021' , '2030' ) , '202012' , '202312' ) 
-- ) 
AND MA . F600GCD IN ( '71330' , '71331' , '71334' , '71338' , '71341' , '71342' , '71349' , '71350' , '71351' , '89269' , '89274' ) AND MA . F600ACD IN ( '' , '01' ) 
AND ( MA . F600HOS IN ( SELECT F999NM1 FROM MCLISDLIB . MC999D@ WHERE F999COR = $P_COR AND F999CD1 = 'CLIC' AND F999CD2 = 'SAA2' AND UPPER ( F999CD3 ) LIKE '' || UPPER ( $P_LOGID ) || '%' ) 
OR MA . F600HOS = ( SELECT LOGHOS FROM MCLISDLIB . MCLOGI@ WHERE LOGCOR = $P_COR AND UPPER ( LOGLID ) = UPPER ( $P_LOGID ) ) ) 
GROUP BY ROLLUP ( ( MA . F600HOS , F120FNM ) ) 
ORDER BY MA . F600HOS ; 
DECLARE CUR2 CURSOR FOR 
SELECT 
IFNULL ( MA . F600HOS , '' ) AS HOSCD  --병원코드 
, IFNULL ( F120FNM , '총 계' ) AS HOSNM  --병원명 
, SUM ( CASE WHEN MA . F600GCD IN ( '71330' , '71331' , '71334' , '71338' , '71341' , '71342' , '71349' , '71350' , '71351' , '89269' , '89274' ) THEN 1 ELSE 0 END ) AS JSCNT  --접수건수 
, SUM ( CASE WHEN MA . F600C03 = 'Y' AND MA . F600GCD IN ( '71330' , '71331' , '71334' , '71338' , '71341' , '71342' , '71349' , '71350' , '71351' , '89269' , '89274' ) THEN 1 ELSE 0 END ) AS GSCNT  --검사건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%Positive%' ) OR ( MA . F600CHR LIKE '%개별%' AND SU . F600CHR LIKE '%Positive%' ) THEN 1 ELSE 0 END ) AS NECNT  --양성건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%Inconclusive%' ) OR ( MA . F600CHR LIKE '%개별%' AND SU . F600CHR LIKE '%Inconclusive%' ) THEN 1 ELSE 0 END ) AS INCNT  --미결정건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%Negative%' ) OR ( MA . F600CHR LIKE '%개별%' AND SU . F600CHR LIKE '%Negative%' ) THEN 1 ELSE 0 END ) AS POCNT  --음성건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND ( MA . F600CHR LIKE '%Negative%' OR MA . F600CHR LIKE '%Positive%' OR MA . F600CHR LIKE '%Inconclusive%' ) ) 
		OR ( MA . F600CHR LIKE '%개별%' AND ( SU . F600CHR LIKE '%Negative%' OR SU . F600CHR LIKE '%Positive%' OR SU . F600CHR LIKE '%Inconclusive%' ) ) THEN 1 ELSE 0 END ) AS RSCNT  --최종보고결과건수계 
, SUM ( CASE WHEN MA . F600C03 <> 'Y' OR ( MA . F600CHR LIKE '%개별%' AND ( ( SU . F600CHR NOT LIKE '%Negative%' 
										AND SU . F600CHR NOT LIKE '%Positive%' 
										AND SU . F600CHR NOT LIKE '%Inconclusive%' 
AND SU . F600CHR NOT LIKE '%재검%' ) OR SU . F600C03 IS NULL ) ) 
					OR ( MA . F600C03 = 'Y' AND MA . F600CHR NOT LIKE '%재검%' 
AND MA . F600CHR NOT LIKE '%Negative%' 
AND MA . F600CHR NOT LIKE '%Positive%' 
AND MA . F600CHR NOT LIKE '%Inconclusive%' 
AND MA . F600CHR NOT LIKE '%개별%' ) THEN 1 ELSE 0 END ) AS INGCNT  --검사중건수 
--, SUM ( CASE WHEN MA . F600C03 = 'Y' AND MA . F600CHR LIKE '%재검%' THEN 1 ELSE 0 END ) AS RECNT  --재검건수 
, SUM ( CASE WHEN ( MA . F600C03 = 'Y' AND ( MA . F600CHR LIKE '%재검%' OR SU . F600CHR LIKE '%재검%' ) ) THEN 1 ELSE 0 END ) AS RECNT  --재검건수 
FROM MCLISDLIB . MC600M@ MA 
INNER JOIN MCLISDLIB . MC120M@ 
ON MA . F600COR = F120COR 
AND MA . F600HOS = F120PCD 
LEFT OUTER JOIN MCLISDLIB . MC600M@ SU 
ON MA . F600COR = SU . F600COR 
AND MA . F600DAT = SU . F600DAT 
AND MA . F600JNO = SU . F600JNO 
AND SU . F600GCD IN ( '71337' , '71340' , '71346' , '89272' ) 
WHERE MA . F600COR = $P_COR 
AND 
( 
	/***** 조건1) 연도가 같은경우 *****/ 
	MA . F600DAT BETWEEN $P_FDAT AND ( CASE WHEN $P_TDAT > '20221231' 
THEN '20221231' 
ELSE $P_TDAT END ) 
	OR 
	/***** 조건2) 연도가 다른경우 + 2021년 포함인 경우 --> 2027년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20270101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20271231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2027' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건3) 연도가 다른경우 + 2021년 포함인 경우 --> 2028년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20280101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20281231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2028' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건4) 연도가 다른경우 + 2021년 포함인 경우 --> 2029년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20290101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20291231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2029' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건5) 연도가 다른경우 + 2021년 포함인 경우 --> 2030년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20300101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2021' AND '2021' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20301231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2021' AND '2021' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2030' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건6) 연도가 다른경우 + 2022년 포함인 경우 --> 2023년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20230101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20231231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2023' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건7) 연도가 다른경우 + 2022년 포함인 경우 --> 2024년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20240101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20241231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2024' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건8) 연도가 다른경우 + 2022년 포함인 경우 --> 2025년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20250101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20251231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2025' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건9) 연도가 다른경우 + 2022년 포함인 경우 --> 2026년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20260101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20261231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2026' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건10) 연도가 다른경우 + 2022년 포함인 경우 --> 2037년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20370101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20371231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2037' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건11) 연도가 다른경우 + 2022년 포함인 경우 --> 2038년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20380101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20381231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2038' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
	OR 
	/***** 조건11) 연도가 다른경우 + 2022년 포함인 경우 --> 2039년 *****/ 
	MA . F600DAT BETWEEN ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) < '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20390101' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) = '2022' AND '2022' <= SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_FDAT , 5 , 4 ) ) 
			ELSE $P_FDAT END 
	) AND ( 
		CASE WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' < SUBSTRING ( $P_TDAT , 1 , 4 ) THEN '20391231' 
			WHEN SUBSTRING ( $P_FDAT , 1 , 4 ) <= '2022' AND '2022' = SUBSTRING ( $P_TDAT , 1 , 4 ) THEN CONCAT ( '2039' , SUBSTRING ( $P_TDAT , 5 , 4 ) ) 
			ELSE $P_TDAT END 
	) 
) 
-- AND 
-- ( 
-- MA . F600DAT BETWEEN $P_FDAT AND $P_TDAT 
-- OR MA . F600DAT BETWEEN REPLACE ( REPLACE ( $P_FDAT , '2021' , '2029' ) , '202012' , '202312' ) AND REPLACE ( REPLACE ( $P_TDAT , '2021' , '2029' ) , '202012' , '202312' ) 
-- OR MA . F600DAT BETWEEN REPLACE ( REPLACE ( $P_FDAT , '2021' , '2030' ) , '202012' , '202312' ) AND REPLACE ( REPLACE ( $P_TDAT , '2021' , '2030' ) , '202012' , '202312' ) 
-- ) 
AND MA . F600GCD IN ( '71330' , '71331' , '71334' , '71338' , '71341' , '71342' , '71349' , '71350' , '71351' , '89269' , '89274' ) AND MA . F600ACD IN ( '' , '01' ) 
AND MA . F600HOS = $P_HOSCD 
GROUP BY ROLLUP ( ( MA . F600HOS , F120FNM ) ) 
ORDER BY MA . F600HOS ; 
IF LENGTH ( TRIM ( $P_HOSCD ) ) = 5 THEN 
OPEN CUR2 ; 
SET RESULT SETS CURSOR CUR2 ;	 
ELSE 
OPEN CUR1 ; 
SET RESULT SETS CURSOR CUR1 ; 
END IF ; 
END  ; 

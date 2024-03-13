--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/05/10 11:30:22 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE MCLISOLIB.SEL_SMS_SEND_DATA ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE MCLISOLIB.SEL_SMS_SEND_DATA ( 
  IN $I_HOS VARCHAR(5) , 
  IN $I_FDT DECIMAL(8, 0) , 
  IN $I_TDT DECIMAL(8, 0) , 
  IN $I_FDT_PRE DECIMAL(8, 0) , 
  IN $I_TDT_PRE DECIMAL(8, 0) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC MCLISOLIB.SEL_SMS_SEND_DATA 
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
			/**********************************************************************/ 
			/**********************************************************************/ 
			/**************** (01) 코로나검사 >> 최초 전송인 경우는 D-1 데이터만 집계하도록 한다 **************/ 
			/**********************************************************************/ 
			/**********************************************************************/ 
			SELECT AA . F600DAT AS DAT								 -- 접수일자 
					, AA . F600JNO AS JNO								 -- 접수번호 
					, AA . F600GCD AS GCD								 -- 검사코드 
					, AA . F600ACD AS ACD								 -- 부속코드 
					, AA . F600CHR AS RST								 -- 검사결과 
					, ( SELECT HH . F010FKN FROM MCLISDLIB . "MC010M@" HH 
						WHERE 1 = 1 
						AND HH . F010COR = 'NML' 
						AND HH . F010STS = 'Y' 
						AND HH . F010GCD = AA . F600GCD 
						) AS GCD_NM									 -- 검사명 
					, GG . F100NAM AS NAM								 -- 환자명 
					, AA . F600HOS AS HOS								 -- 병원코드 
					, ( SELECT GG . F120FNM FROM MCLISDLIB . "MC120M@" GG 
						WHERE 1 = 1 
						AND GG . F120COR = 'NML' 
						AND GG . F120STS = 'Y' 
						AND GG . F120PCD = BB . S100HOS 
						) AS HOS_NM									 -- 병원명 
					, '82' AS PCD										 -- 국가코드 
					, ( SELECT REPLACE ( EE . F120TEL , '-' , '' ) 
						FROM MCLISDLIB . MC120M@ EE 
						WHERE EE . F120COR = AA . F600COR	 
						AND EE . F120PCD = AA . F600HOS		 
						) AS SNN										 -- 발신자번호 
					, BB . S100PNO AS PNO								 -- 수신자번호 
					, FF . FSMSFRTM AS SMS_FR_TM						 -- SMS 발송 시작시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
					, FF . FSMSTOTM AS SMS_TO_TM						 -- SMS 발송 종료시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
					, FF . FSMSTXT AS SMS_TEMPLATE					 -- SMS 문자 템플릿 
					, ( CASE WHEN TRIM ( GG . F100SEX ) = 'F' THEN '여' 
								WHEN TRIM ( GG . F100SEX ) = 'M' THEN '남' 
								ELSE '' END ) AS F100SEX 
					, ( CASE WHEN TRIM ( GG . F100BDT ) <> '' THEN SUBSTRING ( GG . F100BDT , 3 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 5 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 7 , 2 ) ELSE '' END ) AS BIRTH 
			FROM MCLISDLIB . MC600M29 AA 
			LEFT JOIN MCLISDLIB . MC100HSUB@ BB	 -- 핸드폰번호가 등록된 병원만 SMS 발송을 할수 있게 하기 위한 JOIN 
				ON AA . F600COR = BB . S100COR 
				AND AA . F600DAT = BB . S100DAT 
				AND AA . F600JNO = BB . S100JNO 
				AND AA . F600HOS = BB . S100HOS 
			LEFT JOIN MCLISDLIB . MC100H@ GG 
				ON GG . F100COR = BB . S100COR 
				AND GG . F100DAT = BB . S100DAT 
				AND GG . F100JNO = BB . S100JNO 
				AND GG . F100HOS = BB . S100HOS 
			LEFT JOIN MCLISDLIB . SMSTEMPL@ FF 
				ON AA . F600COR = FF . FSMSCOR 
				AND AA . F600HOS = FF . FSMSHOS 
			WHERE AA . F600COR = 'NML' 
			AND ( IFNULL ( $I_HOS , '' ) = '' OR AA . F600HOS = $I_HOS )  -- 병원코드 없으면 모든병원 / 병원코드 있으면 특정병원만 집계 
			AND AA . F600BDT BETWEEN $I_FDT AND $I_TDT 
			AND AA . F600C03 = 'Y'  -- 걸사결과 완료 여부 확인 
			AND AA . F600C04 = '9'  -- 걸사결과 최종보고 여부 확인 
			AND FF . FSMSRST = ( CASE WHEN TRIM ( UPPER ( AA . F600CHR ) ) = 'NEGATIVE' THEN '00' 
								WHEN TRIM ( UPPER ( AA . F600CHR ) ) = 'POSITIVE' THEN '01' 
								WHEN RTRIM ( UPPER ( AA . F600CHR ) ) LIKE 'INCONCLUSIVE%' THEN '02' 
								ELSE '' 
								END ) 
			AND AA . F600GCD IN ( SELECT F999CD3 
								FROM MCLISDLIB . "MC999D@" 
								WHERE 1 = 1 
								AND F999COR = 'NML' 
								AND F999CD1 = 'CLIC' 
								AND F999CD2 = 'COVI' 
								)  -- 코로나 종목만 집계함 
			AND ( TRIM ( IFNULL ( BB . S100TMP6 , '' ) ) = '' )  -- SMS 발송된 데이터인지 확인 (미발송된 데이터만 집계됨) 
			AND ( TRIM ( IFNULL ( GG . F100NAM , '' ) ) <> '' )  -- SMS 발송된 데이터인지 확인 (미발송된 데이터만 집계됨) 
			AND FF . FSMSGCD = '' 
			AND ( TRIM ( IFNULL ( BB . S100PNO , '' ) ) <> '' )  -- 핸드폰번호 미입력시 집계가 되지 않도록 한다 (2022.01.25 추가) 
UNION 
			/****************************************************************/ 
			/****************************************************************/ 
			/**************** (02) 코로나검사 >> 재전송인 경우는 D-15 데이터까지 집계하도록 한다 ***************/ 
			/****************************************************************/ 
			/****************************************************************/ 
			SELECT AA . F600DAT AS DAT  -- 접수일자 
					, AA . F600JNO AS JNO  -- 접수번호 
					, AA . F600GCD AS GCD  -- 검사코드 
					, AA . F600ACD AS ACD  -- 부속코드 
					, AA . F600CHR AS RST  -- 검사결과 
					, ( SELECT HH . F010FKN FROM MCLISDLIB . "MC010M@" HH 
						WHERE 1 = 1 
						AND HH . F010COR = 'NML' 
						AND HH . F010STS = 'Y' 
						AND HH . F010GCD = AA . F600GCD 
						) AS GCD_NM  -- 검사명 
					, GG . F100NAM AS NAM  -- 환자명 
					, AA . F600HOS AS HOS  -- 병원코드 
					, ( SELECT GG . F120FNM FROM MCLISDLIB . "MC120M@" GG 
						WHERE 1 = 1 
						AND GG . F120COR = 'NML' 
						AND GG . F120STS = 'Y' 
						AND GG . F120PCD = BB . S100HOS 
						) AS HOS_NM  -- 병원명 
					, '82' AS PCD  -- 국가코드 
					, ( SELECT REPLACE ( EE . F120TEL , '-' , '' ) 
						FROM MCLISDLIB . MC120M@ EE 
						WHERE EE . F120COR = AA . F600COR	 
						AND EE . F120PCD = AA . F600HOS		 
						) AS SNN  -- 발신자번호 
					, BB . S100PNO AS PNO			 -- 수신자번호 
					, FF . FSMSFRTM AS SMS_FR_TM	 -- SMS 발송 시작시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
					, FF . FSMSTOTM AS SMS_TO_TM	 -- SMS 발송 종료시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
					, FF . FSMSTXT AS SMS_TEMPLATE  -- SMS 문자 템플릿 
					, ( CASE WHEN TRIM ( GG . F100SEX ) = 'F' THEN '여' 
						WHEN TRIM ( GG . F100SEX ) = 'M' THEN '남' 
						ELSE '' END ) AS F100SEX 
					, ( CASE WHEN TRIM ( GG . F100BDT ) <> '' THEN SUBSTRING ( GG . F100BDT , 3 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 5 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 7 , 2 ) ELSE '' END ) AS BIRTH 
			FROM MCLISDLIB . MC600M@ AA 
			LEFT JOIN MCLISDLIB . MC100HSUB@ BB	 -- 핸드폰번호가 등록된 병원만 SMS 발송을 할수 있게 하기 위한 JOIN 
				ON AA . F600COR = BB . S100COR 
				AND AA . F600DAT = BB . S100DAT 
				AND AA . F600JNO = BB . S100JNO 
				AND AA . F600HOS = BB . S100HOS 
			LEFT JOIN MCLISDLIB . MC100H@ GG 
				ON GG . F100COR = BB . S100COR 
				AND GG . F100DAT = BB . S100DAT 
				AND GG . F100JNO = BB . S100JNO 
				AND GG . F100HOS = BB . S100HOS 
			LEFT JOIN MCLISDLIB . SMSTEMPL@ FF 
				ON AA . F600COR = FF . FSMSCOR 
				AND AA . F600HOS = FF . FSMSHOS 
			WHERE AA . F600COR = 'NML' 
			AND (			 
								/** 조건1 - 연도가 같은경우 **/ 
								AA . F600DAT BETWEEN CHAR ( $I_FDT_PRE ) AND 
								( CASE WHEN CHAR ( $I_TDT_PRE ) > '20221231' 
								THEN '20221231' 
								ELSE CHAR ( $I_TDT_PRE ) END ) 
								OR 
								/** 조건2 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2027년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20270101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2027' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20271231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2027' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건3 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2028년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20280101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2028' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20281231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2028' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건4 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2029년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20290101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2029' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20291231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2029' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건5 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2030년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20300101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2030' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20301231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2030' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건6 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2023년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20230101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2023' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20231231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2023' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건7 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2024년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20240101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2024' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20241231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2024' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건8 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2025년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20250101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2025' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20251231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2025' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건9 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2026년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20260101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2026' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20261231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2026' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건10 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2037년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20370101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2037' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20371231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2037' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건11 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2038년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20380101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2038' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20381231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2038' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건12 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2039년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20390101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2039' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20391231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2039' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
		) 
			AND ( IFNULL ( $I_HOS , '' ) = '' OR AA . F600HOS = $I_HOS )  -- 병원코드 없으면 모든병원 / 병원코드 있으면 특정병원만 집계 
			AND AA . F600C03 = 'Y'  -- 걸사결과 완료 여부 확인 
			AND AA . F600C04 = '9'  -- 걸사결과 최종보고 여부 확인 
			AND FF . FSMSRST = ( CASE WHEN TRIM ( UPPER ( AA . F600CHR ) ) = 'NEGATIVE' THEN '00' 
								WHEN TRIM ( UPPER ( AA . F600CHR ) ) = 'POSITIVE' THEN '01' 
								WHEN RTRIM ( UPPER ( AA . F600CHR ) ) LIKE 'INCONCLUSIVE%' THEN '02' 
								ELSE '' 
								END ) 
			AND AA . F600GCD IN ( SELECT F999CD3 
								FROM MCLISDLIB . "MC999D@" 
								WHERE 1 = 1 
								AND F999COR = 'NML' 
								AND F999CD1 = 'CLIC' 
								AND F999CD2 = 'COVI' )  -- 코로나 종목만 집계함 
			AND ( TRIM ( IFNULL ( BB . S100TMP6 , '' ) ) = 'R' )  -- SMS 발송된 데이터인지 확인 (미발송된 데이터만 집계됨) 
			AND ( TRIM ( IFNULL ( GG . F100NAM , '' ) ) <> '' )  -- SMS 발송된 데이터인지 확인 (미발송된 데이터만 집계됨) 
			AND FF . FSMSGCD = '' 
			AND ( TRIM ( IFNULL ( BB . S100PNO , '' ) ) <> '' )  -- 핸드폰번호 미입력시 집계가 되지 않도록 한다 (2022.01.25 추가) 
UNION 
			/***************************************************************************/	 
			/***************************************************************************/ 
			/**************** (03) 코로나 외 검사 >> 최초 전송인 경우는 보고일자기준 D-1 데이터만 집계하도록 한다 **************/ 
			/***************************************************************************/ 
			/***************************************************************************/ 
			SELECT AA . F600DAT AS DAT										 -- 접수일자 
					, AA . F600JNO AS JNO										 -- 접수번호 
	, 'GCD' AS GCD										 -- 접수번호 
	, 'ACD' AS ACD										 -- 접수번호 
	, LISTAGG ( CONCAT ( CONCAT (
									(case when F600ACD = '' THEN ( SELECT RTRIM ( F010SNM ) FROM MCLISDLIB . MC010M@
																	WHERE 1 = 1 
																	AND F010COR = 'NML' 
																	AND F010GCD = F600GCD )
										ELSE (SELECT RTRIM(F011FKN)  FROM MCLISDLIB.MC011M@
												WHERE 1=1
												AND F011COR = 'NML'
												AND F011GCD = F600GCD
												AND F011ACD = F600ACD)
									END)
						, ' : ' )
				, (case when F600ACD = '' THEN ( SELECT (CASE WHEN F010RFG= 'N' THEN TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) )
															  WHEN F010RFG= 'C' THEN TRIM ( F600CHR )
															  ELSE CONCAT(TRIM( F600CHR ), TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) ))
														END
														) 
													FROM MCLISDLIB . MC010M@
													WHERE 1 = 1 
													AND F010COR = 'NML' 
													AND F010GCD = F600GCD )
						ELSE (SELECT (CASE WHEN F011RFG= 'N' THEN TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) )
									       WHEN F011RFG= 'C' THEN TRIM ( F600CHR )
										   ELSE CONCAT(TRIM( F600CHR ), TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) ))
									  END
									  )  
								FROM MCLISDLIB.MC011M@
								WHERE 1=1
								AND F011COR = 'NML'
								AND F011GCD = F600GCD
								AND F011ACD = F600ACD)
					END) 
				)  
	, '
' ) WITHIN GROUP ( ORDER BY F600DAT , F600JNO,F600GCD, F600ACD ) AS RST				 -- 발송할 SMS 템플릿에 REPLACE 해야하는 검사결과
, 'GCD_NM' AS GCD_NM									 
				, GG . F100NAM AS NAM										 -- 환자명 
, FF . FSMSHOS AS HOS										 -- 병원코드 
				, ( SELECT HH . F120FNM FROM MCLISDLIB . "MC120M@" HH 
					WHERE 1 = 1 
					AND HH . F120COR = 'NML' 
					AND HH . F120STS = 'Y' 
					AND HH . F120PCD = BB . S100HOS ) AS HOS_NM			 -- 병원명 
				, '82' AS PCD											 -- 국가코드 
				, ( SELECT REPLACE ( EE . F120TEL , '-' , '' ) 
					FROM MCLISDLIB . MC120M@ EE 
					WHERE EE . F120COR = AA . F600COR	 
					AND EE . F120PCD = AA . F600HOS ) AS SNN				 -- 발신자번호 
				, BB . S100PNO AS PNO										 -- 수신자번호 
				, JJ . FSMSFRTM AS SMS_FR_TM								 -- SMS 발송 시작시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
				, JJ . FSMSTOTM AS SMS_TO_TM								 -- SMS 발송 종료시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
, JJ . FSMSTEXT AS SMS_TEMPLATE							 -- 발송할 SMS 템플릿 
				, ( CASE WHEN TRIM ( GG . F100SEX ) = 'F' THEN '여' 
								WHEN TRIM ( GG . F100SEX ) = 'M' THEN '남' 
								ELSE '' END ) AS F100SEX 
				, ( CASE WHEN TRIM ( GG . F100BDT ) <> '' THEN SUBSTRING ( GG . F100BDT , 3 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 5 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 7 , 2 ) ELSE '' END ) AS BIRTH 
		FROM MCLISDLIB . "MC600M@" AA 
		LEFT JOIN MCLISDLIB . MC100HSUB@ BB 
			ON AA . F600COR = BB . S100COR 
			AND AA . F600DAT = BB . S100DAT 
			AND AA . F600JNO = BB . S100JNO 
			AND AA . F600HOS = BB . S100HOS 
		LEFT JOIN MCLISDLIB . MC100H@ GG 
			ON GG . F100COR = BB . S100COR 
			AND GG . F100DAT = BB . S100DAT 
			AND GG . F100JNO = BB . S100JNO 
			AND GG . F100HOS = BB . S100HOS 
		LEFT JOIN MCLISDLIB . "SMSTEMPL@H" FF 
			ON AA . F600COR = FF . FSMSCOR 
			AND AA . F600HOS = FF . FSMSHOS 
			AND AA . F600GCD = FF . FSMSGCD 
			AND AA . F600ACD = FF . FSMSACD 
LEFT JOIN MCLISDLIB . "SMSTEMPL@M" JJ 
			ON AA . F600COR = JJ . FSMSCOR 
			AND AA . F600HOS = JJ . FSMSHOS 
			AND FF . FSMSSEQ = JJ . FSMSSEQ 
			 --AND AA.F600ACD = FF.FSMSACD 
		WHERE 1 = 1 
		AND AA . F600COR = 'NML' 
		AND ( IFNULL ( $I_HOS , '' ) = '' OR AA . F600HOS = $I_HOS )	 -- 병원코드 없으면 모든병원 / 병원코드 있으면 특정병원만 집계 
		AND AA . F600BDT BETWEEN $I_FDT AND $I_TDT 
		AND FF . FSMSHOS IS NOT NULL 
		AND AA . F600C03 = 'Y'										 -- 걸사결과 완료 여부 확인 
		AND AA . F600C04 = '9'										 -- 걸사결과 최종보고 여부 확인 
		AND ( TRIM ( IFNULL ( BB . S100TMP6 , 'X' ) ) = '' )			 -- 최초발송 대상인지 확인. ('':최초발송대상, 'R':재발송대상, 'Y':발송완료대상) 
		AND ( TRIM ( IFNULL ( GG . F100NAM , '' ) ) <> '' )				 -- 미입력 된 경우 집계 대상에서 제외. (환자명) 
		AND ( TRIM ( IFNULL ( BB . S100PNO , '' ) ) <> '' )				 -- 미입력 된 경우 집계 대상에서 제외. (핸드폰번호) 
		AND ( TRIM ( IFNULL ( GG . F100SEX , '' ) ) <> '' )				 -- 미입력 된 경우 집계 대상에서 제외. (성별) 
		AND ( TRIM ( IFNULL ( JJ . FSMSTEXT , '' ) ) <> '' )			 -- 미입력 된 경우 집계 대상에서 제외. (성별) 
		GROUP BY AA . F600DAT 
				, AA . F600JNO 
				, GG . F100NAM 
				, ( SELECT HH . F120FNM FROM MCLISDLIB . "MC120M@" HH 
					WHERE 1 = 1 
					AND HH . F120COR = 'NML' 
					AND HH . F120STS = 'Y' 
					AND HH . F120PCD = BB . S100HOS 
					) 
				, ( SELECT REPLACE ( EE . F120TEL , '-' , '' ) 
					FROM MCLISDLIB . MC120M@ EE 
					WHERE EE . F120COR = AA . F600COR	 
					AND EE . F120PCD = AA . F600HOS ) 
				, BB . S100PNO 
				, JJ . FSMSFRTM 
				, JJ . FSMSTOTM 
				, JJ . FSMSTEXT 
				, GG . F100SEX 
				, GG . F100BDT 
				, FF . FSMSHOS 
				, FF . FSMSSEQ 
UNION 
		/*****************************************************************************/ 
		/*****************************************************************************/ 
		/**************** (04) 코로나 외 검사 >> 재전송인 경우는 접수일자 기준 D-15 데이터까지 집계하도록 한다 ***************/ 
		/*****************************************************************************/ 
		/*****************************************************************************/ 
		SELECT 
AA . F600DAT AS DAT										 -- 접수일자 
				, AA . F600JNO AS JNO										 -- 접수번호 
, 'GCD' AS GCD										 -- 접수번호 
, 'ACD' AS ACD										 -- 접수번호 
, LISTAGG ( CONCAT ( CONCAT (
									(case when F600ACD = '' THEN ( SELECT RTRIM ( F010SNM ) FROM MCLISDLIB . MC010M@
																	WHERE 1 = 1 
																	AND F010COR = 'NML' 
																	AND F010GCD = F600GCD )
										ELSE (SELECT RTRIM(F011FKN)  FROM MCLISDLIB.MC011M@
												WHERE 1=1
												AND F011COR = 'NML'
												AND F011GCD = F600GCD
												AND F011ACD = F600ACD)
									END)
						, ' : ' )
				, (case when F600ACD = '' THEN ( SELECT (CASE WHEN F010RFG= 'N' THEN TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) )
															  WHEN F010RFG= 'C' THEN TRIM ( F600CHR )
															  ELSE CONCAT(TRIM( F600CHR ), TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) ))
														END
														) 
													FROM MCLISDLIB . MC010M@
													WHERE 1 = 1 
													AND F010COR = 'NML' 
													AND F010GCD = F600GCD )
						ELSE (SELECT (CASE WHEN F011RFG= 'N' THEN TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) )
									       WHEN F011RFG= 'C' THEN TRIM ( F600CHR )
										   ELSE CONCAT(TRIM( F600CHR ), TRIM ( VARCHAR_FORMAT ( DECIMAL ( F600NU1 , 12 , 1 ) , '999999999990.0' ) ))
									  END
									  )  
								FROM MCLISDLIB.MC011M@
								WHERE 1=1
								AND F011COR = 'NML'
								AND F011GCD = F600GCD
								AND F011ACD = F600ACD)
					END) 
				)  
	, '
' ) WITHIN GROUP ( ORDER BY F600DAT , F600JNO,F600GCD, F600ACD ) AS RST				 -- 발송할 SMS 템플릿에 REPLACE 해야하는 검사결과
, 'GCD_NM' AS GCD_NM 
				, GG . F100NAM AS NAM										 -- 환자명 
, FF . FSMSHOS AS HOS										 -- 병원코드 
				, ( SELECT HH . F120FNM FROM MCLISDLIB . "MC120M@" HH 
					WHERE 1 = 1 
					AND HH . F120COR = 'NML' 
					AND HH . F120STS = 'Y' 
					AND HH . F120PCD = BB . S100HOS ) AS HOS_NM			 -- 병원명 
				, '82' AS PCD											 -- 국가코드 
				, ( SELECT REPLACE ( EE . F120TEL , '-' , '' ) 
					FROM MCLISDLIB . MC120M@ EE 
					WHERE EE . F120COR = AA . F600COR	 
					AND EE . F120PCD = AA . F600HOS ) AS SNN				 -- 발신자번호 
				, BB . S100PNO AS PNO										 -- 수신자번호 
				, JJ . FSMSFRTM AS SMS_FR_TM								 -- SMS 발송 시작시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
				, JJ . FSMSTOTM AS SMS_TO_TM								 -- SMS 발송 종료시간 (병원에서 원하는 시간대에만 SMS 발송이 가능하도록 시작시간 설정) 
				, JJ . FSMSTEXT AS SMS_TEMPLATE							 -- 발송할 SMS 템플릿 
				, ( CASE WHEN TRIM ( GG . F100SEX ) = 'F' THEN '여' 
								WHEN TRIM ( GG . F100SEX ) = 'M' THEN '남' 
								ELSE '' END ) AS F100SEX 
				, ( CASE WHEN TRIM ( GG . F100BDT ) <> '' THEN SUBSTRING ( GG . F100BDT , 3 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 5 , 2 ) || '.' || SUBSTRING ( GG . F100BDT , 7 , 2 ) ELSE '' END ) AS BIRTH 
		FROM MCLISDLIB . "MC600M@" AA 
		LEFT JOIN MCLISDLIB . MC100HSUB@ BB 
			ON AA . F600COR = BB . S100COR 
			AND AA . F600DAT = BB . S100DAT 
			AND AA . F600JNO = BB . S100JNO 
			AND AA . F600HOS = BB . S100HOS 
		LEFT JOIN MCLISDLIB . MC100H@ GG 
			ON GG . F100COR = BB . S100COR 
			AND GG . F100DAT = BB . S100DAT 
			AND GG . F100JNO = BB . S100JNO 
			AND GG . F100HOS = BB . S100HOS 
		LEFT JOIN MCLISDLIB . "SMSTEMPL@H" FF 
			ON AA . F600COR = FF . FSMSCOR 
			AND AA . F600HOS = FF . FSMSHOS 
			AND AA . F600GCD = FF . FSMSGCD 
			AND AA . F600ACD = FF . FSMSACD 
LEFT JOIN MCLISDLIB . "SMSTEMPL@M" JJ 
			ON AA . F600COR = JJ . FSMSCOR 
			AND AA . F600HOS = JJ . FSMSHOS 
			AND FF . FSMSSEQ = JJ . FSMSSEQ 
		WHERE 1 = 1 
		AND AA . F600COR = 'NML' 
		AND ( IFNULL ( $I_HOS , '' ) = '' OR AA . F600HOS = $I_HOS )	 -- 병원코드 없으면 모든병원 / 병원코드 있으면 특정병원만 집계 
		AND (			 
								/** 조건1 - 연도가 같은경우 **/ 
								AA . F600DAT BETWEEN CHAR ( $I_FDT_PRE ) AND 
								( CASE WHEN CHAR ( $I_TDT_PRE ) > '20221231' 
								THEN '20221231' 
								ELSE CHAR ( $I_TDT_PRE ) END ) 
								OR 
								/** 조건2 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2027년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20270101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2027' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20271231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2027' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건3 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2028년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20280101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2028' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20281231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2028' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건4 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2029년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20290101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2029' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20291231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2029' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건5 - 연도가 다른경우 + 2021년 포함인 경우**/ 
								/**  --> 2030년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20300101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2021' 
								AND '2021' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2030' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20301231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2021' 
								AND '2021' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2030' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건6 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2023년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20230101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2023' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20231231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2023' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건7 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2024년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20240101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2024' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20241231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2024' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건8 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2025년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20250101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2025' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20251231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2025' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건9 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2026년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20260101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2026' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20261231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2026' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건10 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2037년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20370101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2037' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20371231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2037' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건11 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2038년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20380101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2038' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20381231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2038' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
								OR 
								/** 조건12 - 연도가 다른경우 + 2022년 포함인 경우**/ 
								/**  --> 2039년 **/ 
								AA . F600DAT BETWEEN ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) < '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20390101' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) = '2022' 
								AND '2022' <= SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2039' , SUBSTRING ( CHAR ( $I_FDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_FDT_PRE ) END 
								) AND ( 
								CASE WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' < SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN '20391231' 
								WHEN SUBSTRING ( CHAR ( $I_FDT_PRE ) , 1 , 4 ) <= '2022' 
								AND '2022' = SUBSTRING ( CHAR ( $I_TDT_PRE ) , 1 , 4 ) 
								THEN CONCAT ( '2039' , SUBSTRING ( CHAR ( $I_TDT_PRE ) , 5 , 4 ) ) 
								ELSE CHAR ( $I_TDT_PRE ) END 
								) 
		) 
		AND FF . FSMSHOS IS NOT NULL 
		AND AA . F600C03 = 'Y'											 -- 걸사결과 완료 여부 확인 
		AND AA . F600C04 = '9'											 -- 걸사결과 최종보고 여부 확인 
		AND ( TRIM ( IFNULL ( BB . S100TMP6 , '' ) ) = 'R' )			 -- SMS 발송된 데이터인지 확인 (미발송된 데이터만 집계됨) 
		AND ( TRIM ( IFNULL ( GG . F100NAM , '' ) ) <> '' )					 -- 미입력 된 경우 집계 대상에서 제외. (환자명) 
		AND ( TRIM ( IFNULL ( BB . S100PNO , '' ) ) <> '' )					 -- 미입력 된 경우 집계 대상에서 제외. (핸드폰번호) 
		AND ( TRIM ( IFNULL ( GG . F100SEX , '' ) ) <> '' )					 -- 미입력 된 경우 집계 대상에서 제외. (성별) 
AND ( TRIM ( IFNULL ( JJ . FSMSTEXT , '' ) ) <> '' )				 -- 미입력 된 경우 집계 대상에서 제외. (성별) 
		GROUP BY AA . F600DAT 
				, AA . F600JNO 
				, GG . F100NAM 
				, ( SELECT HH . F120FNM FROM MCLISDLIB . "MC120M@" HH 
					WHERE 1 = 1 
					AND HH . F120COR = 'NML' 
					AND HH . F120STS = 'Y' 
					AND HH . F120PCD = BB . S100HOS 
					) 
				, ( SELECT REPLACE ( EE . F120TEL , '-' , '' ) 
					FROM MCLISDLIB . MC120M@ EE 
					WHERE EE . F120COR = AA . F600COR	 
					AND EE . F120PCD = AA . F600HOS ) 
				, BB . S100PNO 
				, JJ . FSMSFRTM 
				, JJ . FSMSTOTM 
				, JJ . FSMSTEXT 
				, GG . F100SEX 
				, GG . F100BDT 
				, FF . FSMSHOS 
				, FF . FSMSSEQ 
		; 
	OPEN CUR1 ; 
	SET RESULT SETS CURSOR CUR1 ; 
END  ; 

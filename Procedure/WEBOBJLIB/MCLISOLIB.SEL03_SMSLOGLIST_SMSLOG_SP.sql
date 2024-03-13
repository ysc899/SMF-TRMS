--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/03/02 15:58:57 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE SEL03_SMSLOGLIST_SMSLOG_SP ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO030" ; 
CREATE OR REPLACE PROCEDURE MCLISOLIB.SEL03_SMSLOGLIST_SMSLOG_SP ( 
  IN $P_COR CHAR(3) , 
  IN $P_RESULT CHAR(1) , 
  IN $P_HOS VARCHAR(10) , 
  IN $P_PNO VARCHAR(11) , 
  IN $P_PATNAM VARCHAR(20) , 
  IN $P_SRCHFR VARCHAR(8) , 
  IN $P_SRCHTO VARCHAR(8) , 
  IN $P_DPT CHAR(4) , 
  IN $P_JNO DECIMAL(5, 0) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC MCLISOLIB.SEL03_SMSLOGLIST_SMSLOG_SP 
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
	F100DAT 
	, F100JNO 
	, F100NAM 
	, JN 
	, F120FNM 
	, CASE WHEN LENGTH ( TRIM ( S100PNO ) ) = 11 THEN SUBSTRING ( S100PNO , 1 , 3 ) || '-' || SUBSTRING ( S100PNO , 4 , 4 ) || '-' || SUBSTRING ( S100PNO , 8 , 4 ) ELSE S100PNO END AS S100PNO 
	, CHR 
	, SENDYN 
	, SUCSSYNMSG 
	, SUCSSYN 
	, F600GCD 
	, F010FKN 
	, F100HOS 
	, F600BDT 
	 
FROM ( 
SELECT 
	P . F100DAT  --접수일자 
,	P . F100JNO  --접수번호 
,	TRIM ( P . F100NAM ) AS F100NAM  -- 환자명 
, ECHELON . PI_DECSN ( F100JN1 ) AS JN  -- 주민번호 
, B . F600GCD  -- 검사코드 
,	TRIM ( E . F010FKN ) AS F010FKN  -- 검사명 
,	P . F100HOS  -- 병원코드 
,	D . F120FNM  -- 병원명 
, D . F120DPT 
, D . F120MBC 
, TRIM ( F600CHR ) AS CHR 
, S100PNO 
, CASE WHEN TRIM ( IFNULL ( S100TMP6 , '' ) ) = 'Y' THEN '전송' 
WHEN TRIM ( IFNULL ( S100TMP6 , '' ) ) = 'R' THEN '재전송대기중' 
ELSE '미전송' END AS SENDYN  -- 전송햇는지여부 
, IFNULL ( 
( SELECT 
	CASE WHEN F . S100TMP6 = 'Y' AND FSMSREV5 = 'R000' AND FSMSREV6 IN ( '1000' , '' ) THEN '성공' 
WHEN FSMSREV5 = 'R000' AND FSMSREV6 NOT IN ( '1000' , '' ) THEN ( SELECT FERRTXT2 FROM MCLISDLIB . SMSERROR@ WHERE FERRCD = FSMSREV6 )  -- FSMSREV6로 에러표기 
WHEN FSMSREV5 <> 'R000' THEN ( SELECT FERRTXT2 FROM MCLISDLIB . SMSERROR@ WHERE FERRCD = FSMSREV5 )  -- FSMSREV5로 에러표기 
ELSE '' END 
FROM MCLISDLIB . SMSLOG@ 
WHERE FSMSCOR = 'NML' 
AND FSMSDAT = P . F100DAT 
AND FSMSJNO = P . F100JNO 
ORDER BY FSMSSND DESC , FSMSSNT DESC 
FETCH FIRST 1 ROWS ONLY 
) , '' ) AS SUCSSYNMSG  -- 가장 마지막 성공실패여부메시지 
, IFNULL ( 
( SELECT CASE WHEN F . S100TMP6 = 'Y' AND FSMSREV5 = 'R000' AND FSMSREV6 IN ( '1000' , '' ) THEN 'Y' ELSE '' END 
FROM MCLISDLIB . SMSLOG@ 
WHERE FSMSCOR = 'NML' 
AND FSMSDAT = P . F100DAT 
AND FSMSJNO = P . F100JNO 
ORDER BY FSMSSND DESC , FSMSSNT DESC 
FETCH FIRST 1 ROWS ONLY 
) , '' ) AS SUCSSYN  -- 가장 마지막 성공실패여부 
, CASE WHEN B . F600BDT <> 0 THEN			 
			VARCHAR_FORMAT ( TIMESTAMP_FORMAT ( CAST ( B . F600BDT AS VARCHAR ( 8 ) ) , 'YYYYMMDD' ) , 'YYYY-MM-DD  ' ) || 
			VARCHAR_FORMAT ( TIMESTAMP_FORMAT ( CAST ( LPAD ( B . F600CTM , 6 , 0 ) AS VARCHAR ( 8 ) ) , 'HH24MISS' ) , 'HH24:MI AM' )		 
	ELSE '' END AS F600BDT 
	FROM MCLISDLIB . SMSTEMPL@ AS A 
	INNER JOIN MCLISDLIB . MC100H@ AS P ON P . F100COR = 'NML' AND P . F100HOS = A . FSMSHOS 
	INNER JOIN MCLISDLIB . MC600M@ AS B ON B . F600COR = 'NML' 
										AND B . F600DAT = P . F100DAT 
										AND B . F600JNO = P . F100JNO 
										AND ( B . F600CHR LIKE 'Negative%' OR B . F600CHR LIKE 'Posi%' OR B . F600CHR LIKE 'Incon%' ) 
	INNER JOIN MCLISDLIB . MC120M@ AS D ON D . F120COR = 'NML' 
										AND D . F120PCD = P . F100HOS 
	INNER JOIN MCLISDLIB . MC010M@ AS E ON E . F010COR = 'NML' 
										AND E . F010GCD = B . F600GCD 
	INNER JOIN MCLISDLIB . MC100HSUB@ AS F ON F . S100COR = 'NML' 
										AND F . S100DAT = P . F100DAT 
										AND F . S100JNO = P . F100JNO 
										AND F . S100HOS = P . F100HOS 
											 
WHERE FSMSCOR = 'NML' 
AND B . F600C03 = 'Y' 
--AND B.F600C04 = '9' 
AND B . F600GCD IN 
( 
SELECT F999CD3 
FROM MCLISDLIB . MC999D@ 
WHERE F999COR = 'NML' 
AND F999CD1 = 'CLIC' 
AND F999CD2 = 'COVI' 
) 
AND B . F600ACD IN ( '' , '00' ) 
AND ( 
		( P . F100DAT BETWEEN $P_SRCHFR AND $P_SRCHTO ) 
		OR 
		( P . F100DAT BETWEEN '2023' || RIGHT ( $P_SRCHFR , 4 ) AND '2023' || RIGHT ( $P_SRCHTO , 4 ) ) 
		OR 
		( P . F100DAT BETWEEN '2024' || RIGHT ( $P_SRCHFR , 4 ) AND '2024' || RIGHT ( $P_SRCHTO , 4 ) ) 
		OR 
		( P . F100DAT BETWEEN '2025' || RIGHT ( $P_SRCHFR , 4 ) AND '2025' || RIGHT ( $P_SRCHTO , 4 ) ) 
		OR 
		( P . F100DAT BETWEEN '2026' || RIGHT ( $P_SRCHFR , 4 ) AND '2026' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2027' || RIGHT ( $P_SRCHFR , 4 ) AND '2027' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2028' || RIGHT ( $P_SRCHFR , 4 ) AND '2028' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2029' || RIGHT ( $P_SRCHFR , 4 ) AND '2029' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2030' || RIGHT ( $P_SRCHFR , 4 ) AND '2030' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2037' || RIGHT ( $P_SRCHFR , 4 ) AND '2037' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2038' || RIGHT ( $P_SRCHFR , 4 ) AND '2038' || RIGHT ( $P_SRCHTO , 4 ) )
		OR 
		( P . F100DAT BETWEEN '2039' || RIGHT ( $P_SRCHFR , 4 ) AND '2039' || RIGHT ( $P_SRCHTO , 4 ) )
) 
AND P . F100JNO = CASE WHEN $P_JNO = 0 THEN P . F100JNO ELSE $P_JNO END 
AND P . F100HOS = CASE WHEN $P_HOS = 'ALL' THEN P . F100HOS ELSE $P_HOS END 
AND P . F100NAM = CASE WHEN $P_PATNAM = '' THEN P . F100NAM ELSE $P_PATNAM END 
AND F120DPT = CASE WHEN $P_DPT = 'ALL' THEN F120DPT ELSE $P_DPT END 
AND FSMSRST = ( CASE WHEN B . F600CHR LIKE 'Negat%' THEN '00' WHEN B . F600CHR LIKE 'Posi%' THEN '01' WHEN B . F600CHR LIKE 'Incon%' THEN '02' END ) 
) 
--WHERE SUCSSYN <> 'Y' 
WHERE	(  -- 성공여부 
	( $P_RESULT = '0' AND 1 = 1 ) 
	OR 
	( $P_RESULT <> '0' AND SUCSSYN = ( CASE WHEN $P_RESULT = '1' THEN 'Y' ELSE '' END ) ) 
	) 
AND	(  -- 핸드폰번호 
		( $P_PNO = '' AND 1 = 1 ) 
		OR 
		( $P_PNO <> '' AND S100PNO LIKE '%' || $P_PNO || '%' ) 
	) 
	; 
	OPEN CUR1 ; 
SET RESULT SETS CURSOR CUR1 ;	 
END  ; 

--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/06/03 11:25:00 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
DROP SPECIFIC PROCEDURE WEBOBJLIB.SEL06_RESULTMGMTAI_SG680D_SP_BAK_20220603 ; 
SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.SEL06_RESULTMGMTAI_SG680D_SP_BAK_20220603 ( 
  IN $P_COR CHAR(3) , 
  IN $P_GBN CHAR(1) , 
  IN $P_DAT DECIMAL(8, 0) , 
  IN $P_JNO DECIMAL(5, 0) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.SEL06_RESULTMGMTAI_SG680D_SP_BAK_20220603 
  NOT DETERMINISTIC 
  MODIFIES SQL DATA 
  CALLED ON NULL INPUT 
  SET OPTION  ALWBLK = *ALLREAD , 
  ALWCPYDTA = *OPTIMIZE , 
  COMMIT = *NONE , 
  DBGVIEW = *SOURCE , 
  DECRESULT = (31, 31, 00) , 
  DFTRDBCOL = *NONE , 
  DLYPRP = *NO , 
  DYNDFTCOL = *NO , 
  DYNUSRPRF = *USER , 
  SRTSEQ = *HEX 
  BEGIN 
DECLARE V_CNT DECIMAL ( 5 , 0 ) ; 
SELECT COUNT ( * ) 
INTO V_CNT 
FROM MCLISDLIB . SG680D@ 
WHERE F680COR = $P_COR AND F680GBN = $P_GBN AND F680DAT = $P_DAT AND F680JNO = $P_JNO ; 
IF V_CNT > 0 THEN 
BEGIN 
DECLARE CUR1 CURSOR WITH RETURN FOR 
SELECT F680COR , F680GBN , F680DAT , F680JNO , REPLACE ( REPLACE ( F680TXT , CHR ( 10 ) , '<br>' ) , CHR ( 13 ) , '<br>' ) AS F680TXT , F680CUR , F680CTM 
	FROM MCLISDLIB . SG680D@ 
	WHERE F680COR = $P_COR AND F680GBN = $P_GBN AND F680DAT = $P_DAT AND F680JNO = $P_JNO ; 
OPEN CUR1 ; 
END ; 
ELSE 
BEGIN 
DECLARE CUR2 CURSOR WITH RETURN FOR 
SELECT 
	'' AS F680COR 
	, '' AS F680GBN 
	, 0 AS F680DAT 
	, 0 AS F680JNO 
	, '' AS F680TXT 
	, '' AS F680CUR 
	, 0 AS F680CTM 
FROM SYSIBM . SYSDUMMY1 ; 
OPEN CUR2 ; 
END ; 
END IF ; 
END  ; 
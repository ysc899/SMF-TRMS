--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/02/18 09:07:42 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
DROP SPECIFIC PROCEDURE WEBOBJLIB.SEL_CMAHOMEPAGE_GETRPTTXT_SGREPT_SP ; 
SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO030" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.SEL_CMAHOMEPAGE_GETRPTTXT_SGREPT_SP ( 
  IN $P_COR CHAR(3) , 
  IN $P_DAT DECIMAL(8, 0) , 
  IN $P_JNO DECIMAL(6, 0) , 
  IN $P_GCD CHAR(5) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.SEL_CMAHOMEPAGE_GETRPTTXT_SGREPT_SP 
  NOT DETERMINISTIC 
  MODIFIES SQL DATA 
  CALLED ON NULL INPUT 
  SET OPTION  ALWBLK = *ALLREAD , 
  ALWCPYDTA = *OPTIMIZE , 
  COMMIT = *CHG , 
  DBGVIEW = *SOURCE , 
  DECRESULT = (31, 31, 00) , 
  DFTRDBCOL = *NONE , 
  DLYPRP = *NO , 
  DYNDFTCOL = *NO , 
  DYNUSRPRF = *USER , 
  SRTSEQ = *HEX 
  BEGIN 
	DECLARE CUR CURSOR FOR 
	 
WITH TAB ( ID , STR1 , PROFILE , STR3 , STR4 , COL2 , COL3 , COL4 , COL5 , COL6 ) AS 
( 
SELECT 
'1' AS ID 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( FRPTTXT1 , 0 , LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 1 ) ) , '##' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS STR1 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( FRPTTXT1 , LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 1 ) , ( LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 2 ) - LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 1 ) ) ) , '##' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS PROFILE 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( FRPTTXT1 , LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 2 ) , ( LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 3 ) - LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 2 ) ) ) , '##' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS STR3 
--, REPLACE ( SUBSTRING ( FRPTTXT1 , LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 3 ) , ( LENGTH ( FRPTTXT1 ) - LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 3 ) ) ) , CONCAT('##',CHR(13)||CHR(10)) , '' ) AS STR4 
, CONCAT ( CONCAT ( '-------------------------------------------------------------------------------------------------------------------------------' , CHR ( 13 ) || CHR ( 10 ) ) 
, CONCAT ( REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( FRPTTXT1 , LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 3 ) , ( LENGTH ( FRPTTXT1 ) - LOCATE_IN_STRING ( FRPTTXT1 , '##' , 1 , 3 ) ) ) , '##' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) 
	, CONCAT ( CHR ( 13 ) || CHR ( 10 ) , '-------------------------------------------------------------------------------------------------------------------------------' ) 
	) 
) AS STR4 
, FRPTTXT2 AS COL2 
, FRPTTXT3 AS COL3 
, FRPTTXT4 AS COL4 
, FRPTTXT5 AS COL5 
, FRPTTXT6 AS COL6 
FROM MCLISDLIB . SGREPT@ 
WHERE FRPTCOR = $P_COR 
AND FRPTDAT = $P_DAT 
AND FRPTJNO = $P_JNO 
AND FRPTGCD = $P_GCD 
) 
SELECT 
ROW_NUMBER ( ) OVER ( ) AS ROWNUM 
, REPLACE ( REPLACE ( STR1 , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS COL_RST 
, REPLACE ( REPLACE ( SUBSTRING ( ARR . PROFILE , 0 , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 1 ) ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS COL1_ISCN 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( ARR . PROFILE , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 1 ) + 1 , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 2 ) - LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 1 ) ) , '#' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS COL1_TYPE 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( ARR . PROFILE , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 2 ) + 1 , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 3 ) - LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 2 ) ) , '#' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS COL1_CHRM 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( ARR . PROFILE , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 3 ) + 1 , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 4 ) - LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 3 ) ) , '#' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS COL1_SIZE 
, REPLACE ( REPLACE ( REPLACE ( SUBSTRING ( ARR . PROFILE , LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 4 ) + 1 , LENGTH ( ARR . PROFILE ) - LOCATE_IN_STRING ( ARR . PROFILE , '#' , 1 , 4 ) ) , '#' , '' ) , CHR ( 13 ) , '' ) , CHR ( 10 ) , '' ) AS COL1_CLS 
, T . STR3 
, T . STR4 
, COL2 , COL3 , COL4 , COL5 , COL6 
FROM ( 
	SELECT A . ID , B . ITEM AS PROFILE 
	FROM TAB A 
	, XMLTABLE ( '$doc/items/item' 
	PASSING XMLPARSE ( DOCUMENT CAST ( '<items><item>' || REPLACE ( A . PROFILE , '++' , '</item><item>' ) || '</item></items>' AS CLOB ) ) AS "doc" 
	COLUMNS 
	ITEM VARCHAR ( 255 ) PATH '.' 
	) B 
	WHERE B . ITEM <> '' 
) AS ARR 
INNER JOIN TAB AS T ON T . ID = ARR . ID 
; 
OPEN CUR ; 
SET RESULT SETS CURSOR CUR ; 
END  ; 

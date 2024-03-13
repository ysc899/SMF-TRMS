--  Generate SQL 
--  Version:                   V7R4M0 190621 
--  Generated on:              22/12/09 13:16:38 
--  Relational Database:       NEODIN 
--  Standards Option:          Db2 for i 
--DROP SPECIFIC PROCEDURE IF EXISTS MCLISOLIB.SEL02_HAKPOSITIVERATEOVERLIST_DMPOSM_SP ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO022" ; 
CREATE OR REPLACE PROCEDURE MCLISOLIB.SEL02_HAKPOSITIVERATEOVERLIST_DMPOSM_SP ( 
  IN $P_COR VARCHAR(3) , 
  IN $P_FDT VARCHAR(8) , 
  IN $P_TDT VARCHAR(8) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC MCLISOLIB.SEL02_HAKPOSITIVERATEOVERLIST_DMPOSM_SP 
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
SELECT '본원' AS CTNM , LISTAGG ( HAKNM ) WITHIN GROUP ( ORDER BY HAKNM DESC ) AS HAKNAM 
FROM ( 
SELECT 
CASE WHEN DPOSFL1 = 'Y' THEN TRIM ( F900KNM ) || ',' ELSE '' END AS HAKNM 
FROM MCLISDLIB . DMPOSM@ 
INNER JOIN JMTSLIB . MM900M@ 
ON F900COR = DPOSCOR 
AND F900DPT = DPOSHAK 
WHERE DPOSCOR = $P_COR AND DPOSDAT BETWEEN $P_FDT AND $P_TDT AND DPOSLMB = '' 
) 
UNION ALL 
SELECT '부산' , LISTAGG ( HAKNM ) WITHIN GROUP ( ORDER BY HAKNM DESC ) 
FROM ( 
SELECT 
CASE WHEN DPOSFL1 = 'Y' THEN TRIM ( F900KNM ) || ',' ELSE '' END AS HAKNM 
FROM MCLISDLIB . DMPOSM@ 
INNER JOIN JMTSLIB . MM900M@ 
ON F900COR = DPOSCOR 
AND F900DPT = DPOSHAK 
WHERE DPOSCOR = $P_COR AND DPOSDAT BETWEEN $P_FDT AND $P_TDT AND DPOSLMB = '6526' 
) 
UNION ALL 
SELECT '대구' , LISTAGG ( HAKNM ) WITHIN GROUP ( ORDER BY HAKNM DESC ) 
FROM ( 
SELECT 
CASE WHEN DPOSFL1 = 'Y' THEN TRIM ( F900KNM ) || ',' ELSE '' END AS HAKNM 
FROM MCLISDLIB . DMPOSM@ 
INNER JOIN JMTSLIB . MM900M@ 
ON F900COR = DPOSCOR 
AND F900DPT = DPOSHAK 
WHERE DPOSCOR = $P_COR AND DPOSDAT BETWEEN $P_FDT AND $P_TDT AND DPOSLMB = '6520' 
) 
UNION ALL 
SELECT '광주' , LISTAGG ( HAKNM ) WITHIN GROUP ( ORDER BY HAKNM DESC ) 
FROM ( 
SELECT 
CASE WHEN DPOSFL1 = 'Y' THEN TRIM ( F900KNM ) || ',' ELSE '' END AS HAKNM 
FROM MCLISDLIB . DMPOSM@ 
INNER JOIN JMTSLIB . MM900M@ 
ON F900COR = DPOSCOR 
AND F900DPT = DPOSHAK 
WHERE DPOSCOR = $P_COR AND DPOSDAT BETWEEN $P_FDT AND $P_TDT AND DPOSLMB = '6540' 
) 
UNION ALL 
SELECT '대전' , LISTAGG ( HAKNM ) WITHIN GROUP ( ORDER BY HAKNM DESC ) 
FROM ( 
SELECT 
CASE WHEN DPOSFL1 = 'Y' THEN TRIM ( F900KNM ) || ',' ELSE '' END AS HAKNM 
FROM MCLISDLIB . DMPOSM@ 
INNER JOIN JMTSLIB . MM900M@ 
ON F900COR = DPOSCOR 
AND F900DPT = DPOSHAK 
WHERE DPOSCOR = $P_COR AND DPOSDAT BETWEEN $P_FDT AND $P_TDT AND DPOSLMB = '6550' 
) ; 
OPEN CUR ; 
SET RESULT SETS CURSOR CUR ; 
END  ; 
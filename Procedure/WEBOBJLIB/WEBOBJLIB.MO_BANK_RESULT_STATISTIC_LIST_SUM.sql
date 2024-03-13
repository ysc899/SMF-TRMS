--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/04/06 18:22:54 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE WEBOBJLIB.MO_BANK_RESULT_STATISTIC_LIST_SUM ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.MO_BANK_RESULT_STATISTIC_LIST_SUM ( 
  IN $P_COMPANY CHAR(3) , 
  IN $P_GCDGRP VARCHAR(10) , 
  IN $P_GCD VARCHAR(20) , 
  IN $P_ACD VARCHAR(10) , 
  IN $P_SPECIMEN_ID VARCHAR(10) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.MO_BANK_RESULT_STATISTIC_LIST_SUM 
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
DECLARE CR_BANK_RESULT_STATISTIC_LIST_SUM CURSOR FOR 
WITH TEMP AS ( 
                    SELECT GCDGRP  --검사그룹 
                    , GNMGRP  --검사그룹명 
                    , GCD  --검사코드 
                    , GNM  --검사명 
                    , ACD  --부속코드 
                    , ACD || '. ' || ANM AS ANM  --부속명 
                    , SPC  --검체코드 
                    , SPCNM  --검체명 
                    , SUM ( CASE WHEN PNTP = 'P' AND RPTP = 'P' AND SHTP = 'SPECIMEN' THEN ( CASE WHEN RPID IS NULL THEN 0 ELSE 1 END ) ELSE 0 END ) AS POSI_SPEC  --분주검체 
                    , SUM ( CASE WHEN PNTP = 'P' AND RPTP = 'R' THEN ( CASE WHEN RPID IS NULL THEN 0 ELSE 1 END ) ELSE 0 END ) AS POSI  --양성검체 
                    , SUM ( CASE WHEN PNTP = 'N' AND RPTP = 'R' THEN ( CASE WHEN RPID IS NULL THEN 0 ELSE 1 END ) ELSE 0 END ) AS NEGA  --음성검체 
                    , SUM ( CASE WHEN PNTP = 'P' AND RPTP = 'P' AND SHTP = 'NUCLEICACI' THEN ( CASE WHEN RPID IS NULL THEN 0 ELSE 1 END ) ELSE 0 END ) AS POSI_NUCL  --양성핵산 
                    , SUM ( CASE WHEN PNTP = 'N' AND RPTP = 'P' AND SHTP = 'NUCLEICACI' THEN ( CASE WHEN RPID IS NULL THEN 0 ELSE 1 END ) ELSE 0 END ) AS NEGA_NUCL  --음성핵산 
                    FROM ( 
                    SELECT A . TABLE_ID_3 AS GCDGRP  --검사그룹 
                    , A . DESCRIPTION_1 AS GNMGRP  --검사그룹명 
                    , C . F010GCD AS GCD  --검사코드 
                    , TRIM ( C . F010SNM ) AS GNM  --검사명 
                    , D . F011ACD AS ACD  --부속코드 
                    , TRIM ( D . F011SNM ) AS ANM  --부속명 
                    , E . CLASS AS PNTP  --양/음성구분 
                    , E . SPECIMEN_ID AS SPC  --검체코드 
                    , G . DESCRIPTION_1 AS SPCNM  --검체명 
                    , F . RACK_PLATE AS RPTP  --RACK/PLATE구분 
                    , F . CLASSIFICATION AS SHTP  --검체/핵산구분 
                    , H . RACK_PLATE_ID AS RPID  --검체수 
                    FROM XCEEDIDLIB . CM_CODE3 A  --검체그룹 
                    INNER JOIN XCEEDIDLIB . CM_CODE3 B  --그룹별검체 
                    ON B . COMPANY = A . COMPANY 
                    AND B . TABLE_ID_1 = A . TABLE_ID_1 
                    AND B . TABLE_ID_2 = A . TABLE_ID_3 
                    INNER JOIN MCLISDLIB . MC010M@ C  --검체 
                    ON C . F010COR = A . COMPANY 
                    AND C . F010GCD = B . TABLE_ID_3 
                    AND C . F010GCD = CASE WHEN $P_GCD = '*' THEN C . F010GCD ELSE $P_GCD END 
                    LEFT OUTER JOIN MCLISDLIB . MC011M@ D  --부속 
                    ON D . F011COR = C . F010COR 
                    AND D . F011GCD = C . F010GCD 
                    --                              AND D.F011ACD <> '00' 
                    AND D . F011ACD = CASE WHEN $P_ACD = '*' THEN D . F011ACD ELSE $P_ACD END 
                    LEFT OUTER JOIN XCEEDIDLIB . CM_RACKPLATESPEC E  --RACK/PLATE 사양(검사코드/부속코드/검체코드/양성음성구분) 
                    ON E . INSPECTION_ID1 = C . F010GCD 
                    AND E . INSPECTION_ID2 = CASE WHEN D . F011ACD IS NULL THEN '' ELSE D . F011ACD END 
                    AND E . SPECIMEN_ID = CASE WHEN $P_SPECIMEN_ID = '*' THEN E . SPECIMEN_ID ELSE $P_SPECIMEN_ID END 
                    LEFT OUTER JOIN XCEEDIDLIB . CM_RACKPLATE F  --RACK/PLATE 마스터 
                    ON F . RACK_PLATE_ID = E . RACK_PLATE_ID 
                    LEFT OUTER JOIN XCEEDIDLIB . CM_CODE3 G  --검체명 
                    ON G . COMPANY = A . COMPANY 
                    AND G . TABLE_ID_1 = 'BANKING' 
                    AND G . TABLE_ID_2 = 'SPC' 
                    AND G . TABLE_ID_3 = E . SPECIMEN_ID 
                    LEFT OUTER JOIN XCEEDIDLIB . CM_ITEM_IN_RACK H  --RACK/PLATE 아이템 
                    ON H . RACK_PLATE_ID = E . RACK_PLATE_ID 
                    AND H . RACK_LOCATION = E . RACK_LOCATION 
                    WHERE A . COMPANY = $P_COMPANY 
                    AND A . TABLE_ID_1 = 'BANKING' 
                    AND A . TABLE_ID_2 = 'GCDGRP' 
                    AND A . TABLE_ID_3 = CASE WHEN $P_GCDGRP = '*' THEN A . TABLE_ID_3 ELSE $P_GCDGRP END 
                    ) 
                    GROUP BY GCDGRP , GNMGRP , GCD , GNM , ACD , ANM , SPC , SPCNM 
) 
                    SELECT '' AS GCDGRP 
                    , '전체' AS GNMGRP 
                    , '전체' AS GCD 
                    , '전체' AS GNM 
                    , '전체' AS ACD 
                    , '전체' AS ANM 
                    , '전체' AS SPC 
                    , '전체' AS SPCNM 
                    , SUM ( POSI_SPEC ) AS POSI_SPEC 
                    , SUM ( POSI ) AS POSI 
                    , SUM ( NEGA ) AS NEGA 
                    , SUM ( POSI_NUCL ) AS POSI_NUCL 
                    , SUM ( NEGA_NUCL ) AS NEGA_NUCL 
                    FROM TEMP 
                    ORDER BY GCDGRP , GCD , ACD , SPC ; 
OPEN CR_BANK_RESULT_STATISTIC_LIST_SUM ; 
SET 
RESULT SETS CURSOR CR_BANK_RESULT_STATISTIC_LIST_SUM ; 
END  ; 
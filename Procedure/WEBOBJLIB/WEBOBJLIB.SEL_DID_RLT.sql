--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              21/09/16 16:34:13 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
DROP SPECIFIC PROCEDURE WEBOBJLIB.SEL_DID_RLT ; 
SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.SEL_DID_RLT ( 
  IN $I_JDAT DECIMAL(8, 0) , 
  IN $I_JNO DECIMAL(5, 0) ,  
  IN $I_CD2 VARCHAR(4) 
  ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.SEL_DID_RLT 
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
		SELECT T.F600DAT  					--접수일자 
				, T.F600JNO 				--접수번호 
				, P.F100NAM					--환자성명
				, T.F600CHR 				--검사결과 
				, T.F600GCD 				--검사코드 
				, T.F600ACD 				--부속코드 
				, M.F010TCD 				--검체코드 
				, T.F600GDT 				--검사일자 
				, T.F600BDT  				--보고일자 
				, M.F010FKN  				--검사명 
				, T.F600HOS  				--병원코드 
				, H.F120FNM  				--검사의뢰기관명(한글) 
				, HE.F120FNM AS F120FNM_EN 	--검사의뢰기관명(영문) 
				, M.F010MSC  				--검사방법코드 
				, ( SELECT F028TXT FROM MCLISDLIB.MC028D@ 
					WHERE F028COR = F600COR 
					AND F028GCD = F010MSC 
					AND F028SEQ = 0 ) AS F028TXT	--검사방법 
		FROM MCLISDLIB.MC600M@ AS T  				--검사결과 
		LEFT JOIN MCLISDLIB.MC100H@ AS P  			--환자정보
            ON F600COR = F100COR 
            AND F600DAT = F100DAT
            AND F600JNO = F100JNO
		LEFT JOIN MCLISDLIB.MC010M@ AS M 			--검사마스터 
			ON T.F600COR = M.F010COR 
			AND T.F600GCD = M.F010GCD 
		LEFT JOIN MCLISDLIB.MC120M@ AS H  			--병원마스터 
			ON T.F600COR = H.F120COR 
			AND T.F600HOS = H.F120PCD 
		LEFT JOIN MCLISDLIB.MC120ME@ AS HE  		--병원마스터(영문) 
			ON T.F600COR = HE.F120COR 
			AND T.F600HOS = HE.F120PCD 
		WHERE T.F600COR = 'NML' 
		AND T.F600DAT = $I_JDAT 
		AND T.F600JNO = $I_JNO 
		AND t.F600GCD IN (SELECT DISTINCT F999CD3 
                        FROM MCLISDLIB."MC999D@" 
                       WHERE F999COR = 'NML'
                         AND F999CD1 = 'CLIC'
                         AND F999CD2 = $I_CD2)
		AND ( TRIM ( T.F600ACD ) = '' OR T.F600ACD = '01' )  --부속코드 없거나 01
		; 
	OPEN CUR1 ; 
	SET RESULT SETS CURSOR CUR1 ; 
END  ; 

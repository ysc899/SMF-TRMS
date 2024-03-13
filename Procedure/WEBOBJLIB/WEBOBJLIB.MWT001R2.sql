--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              22/05/30 11:32:42 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
--DROP SPECIFIC PROCEDURE WEBOBJLIB.MWT001R2 ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWT001R2 ( 
  IN I_COR VARCHAR(3) , 
  IN I_UID VARCHAR(12) , 
  IN I_IP VARCHAR(30) , 
  OUT O_MSGCOD CHAR(3) , 
  OUT O_ERRCOD CHAR(10) , 
  IN I_GCD VARCHAR(20) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.MWT001R2 
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
DECLARE CUR1 CURSOR WITH RETURN FOR 
-- 검사항목 상세조회 
SELECT 
TRIM ( F010COR ) F010COR  --COR 
, TRIM ( F010GCD ) F010GCD  -- 검사코드 
, TRIM ( F010FKN ) F010FKN  -- 검사명(한글) 
, TRIM ( F010SNM ) F010SNM  -- 검사명(약어) 
, TRIM ( F010GTQ ) F010GTQ  -- 검체량 
, TRIM ( F010TCD ) F010TCD  -- 검체 코드 (F999CD2 ='SAMP' ) 
, ( SELECT TRIM ( MC9 . F999NM2 ) 
FROM MCLISDLIB . MC999D@ MC9 
WHERE F999COR = F010COR 
AND F999CD1 = 'CLIC' 
AND F999CD2 = 'SAMP' 
AND F999CD3 = F010TCD 
) AS F010TNM 
, TRIM ( F010GBX ) F010GBX  -- 검체용기코드 (F999CD2 ='VESS' ) 
, ( SELECT TRIM ( MC9 . F999NM3 ) 
FROM MCLISDLIB . MC999D@ MC9 
WHERE F999COR = F010COR 
AND F999CD1 = 'CLIC' 
AND F999CD2 = 'VESS' 
AND F999CD3 = F010GBX 
) AS F010GBNM 
, ( SELECT TRIM ( T002FPT ) || TRIM ( T002IMG ) AS IMG 
FROM WEBDBLIB . MWT002M@ 
WHERE T002COR = F010COR 
AND T002VCD = F010GBX 
) AS VESSIMG 
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
WHERE MC28 . F028COR = I_COR 
) AS MC28 
WHERE MC28 . F028COR = F010COR 
AND MC28 . F028GCD = F010MSC 
GROUP BY F028GCD , F028COR 
) AS F010MSNM  -- 검사법 
, TRIM ( F010ACH )		F010ACH		 -- WEB여부 
--, TRIM(T001SAV)		T001SAV		-- 보존방법 (수정전) 
, ( CASE TRIM ( MC010 . F010SAV )		 
	WHEN '1' THEN '실온' 
	WHEN '2' THEN '냉장' 
	WHEN '3' THEN '동결' 
	WHEN '4' THEN '절대동결' 
ELSE '' END ) T001SAV		 -- 보존방법(수정후) , 검사코드마스터 data를 가져오도록 수정함(2020.10.30) 
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
, TRIM ( F010EED )		F010EED		 -- 검사소요일수 
, TRIM ( T001CONT )	T001CONT	 -- 임상정보 
, TRIM ( T001ETC )		T001ETC		 -- 주의사항 
, TRIM ( T001REF )	T001REF		 -- 참고치 
, TRIM ( T001FLG )	T001FLG		 -- 사용여부 
, TRIM ( T001LFLG )	T001LFLG	 -- 리스트사용여부 
, TRIM ( F010STS )		F010STS		 -- 상태 
, TRIM ( F010ISC )		F010ISC		 -- 질병검사코드 
, TRIM ( F010HAK )		F010HAK		 -- 검사학부코드 
, ( SELECT TRIM ( F018OMK ) FROM MCLISDLIB . MC018M@ A 
WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB . MC018M@ B WHERE A . F018COR = B . F018COR AND A . F018GCD = B . F018GCD AND B . F018LMB = '' ) 
				AND A . F018COR = MC010 . F010COR 
AND A . F018GCD = MC010 . F010GCD 
AND F018OMK != '' 
AND TRIM ( F018LMB ) = '' 
GROUP BY F018OMK ) SOMK				 --	보험기호(서울) 
, ( SELECT TRIM ( F018OMK ) FROM MCLISDLIB . MC018M@ A 
				WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB . MC018M@ B WHERE A . F018COR = B . F018COR AND A . F018GCD = B . F018GCD AND B . F018LMB = '6526' ) 
				AND A . F018COR = MC010 . F010COR 
				AND A . F018GCD = MC010 . F010GCD 
				AND F018OMK != '' 
				AND TRIM ( F018LMB ) = '6526' 
				GROUP BY F018OMK ) BOMK			 --	보험기호(부산) 
, ( SELECT TRIM ( F018OCD ) FROM MCLISDLIB . MC018M@ A 
				WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB . MC018M@ B WHERE A . F018COR = B . F018COR AND A . F018GCD = B . F018GCD AND B . F018LMB = '' ) 
				AND A . F018COR = MC010 . F010COR 
				AND A . F018GCD = MC010 . F010GCD 
				AND TRIM ( F018LMB ) = '' ) SOCD		 --보험코드(서울) 
, ( SELECT TRIM ( F018OCD ) FROM MCLISDLIB . MC018M@ A 
	WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB . MC018M@ B WHERE A . F018COR = B . F018COR AND A . F018GCD = B . F018GCD AND B . F018LMB = '6526' ) 
				AND A . F018COR = MC010 . F010COR 
				AND A . F018GCD = MC010 . F010GCD 
AND TRIM ( F018LMB ) = '6526' ) BOCD	 --보험코드(부산) 
, ( SELECT F026SSU FROM MCLISDLIB . MC026H@ A 
				WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '' ) 
				AND A . F026COR = MC010 . F010COR 
				AND A . F026GCD = MC010 . F010GCD 
AND TRIM ( F026LMB ) = '' ) SSSU		 --기준수가(서울) 
, ( SELECT F026SSU FROM MCLISDLIB . MC026H@ A 
				WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '6526' ) 
AND A . F026COR = MC010 . F010COR 
AND A . F026GCD = MC010 . F010GCD 
AND TRIM ( F026LMB ) = '6526' ) BSSU	 --기준수가(부산) 
, ( SELECT F026BSU FROM MCLISDLIB . MC026H@ A 
				WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '' ) 
				AND A . F026COR = MC010 . F010COR 
AND A . F026GCD = MC010 . F010GCD 
AND TRIM ( F026LMB ) = '' ) SBSU		 --보험수가(서울) 
, ( SELECT F026BSU FROM MCLISDLIB . MC026H@ A 
	WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '6526' ) 
				AND A . F026COR = MC010 . F010COR 
AND A . F026GCD = MC010 . F010GCD 
AND TRIM ( F026LMB ) = '6526' ) BBSU	 --보험수가(부산) 
/*            , CASE MC010.F010CHK 
            	-- 프로파일의 경우, 질가산료가 반영안된 수가가 없기때문에 아래와 같이 처리한다. -2020.10.19- 
				WHEN '2' THEN ( SELECT F026BSS FROM MCLISDLIB . MC026H@ A 
								WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '9999' ) 
								AND A . F026COR = MC010 . F010COR 
								AND A . F026GCD = MC010 . F010GCD 
								AND TRIM ( F026LMB ) = '9999' ) 
				--ELSE ( SELECT F026TSU FROM MCLISDLIB . MC026H@ A 
				ELSE ( SELECT F026SSU FROM MCLISDLIB . MC026H@ A 
					WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '9999' ) 
					AND A . F026COR = MC010 . F010COR 
					AND A . F026GCD = MC010 . F010GCD 
					AND TRIM ( F026LMB ) = '9999' ) 
				END	TSSU	 --질가산미포함(수가) 
*/ 
, ( SELECT TO_CHAR ( F026SSU , '999,999,999' ) FROM MCLISDLIB . MC026H@ A 
					WHERE F026ADT = ( SELECT MAX ( F026ADT ) FROM MCLISDLIB . MC026H@ B WHERE A . F026COR = B . F026COR AND A . F026GCD = B . F026GCD AND B . F026LMB = '9999' ) 
					AND A . F026COR = MC010 . F010COR 
					AND A . F026GCD = MC010 . F010GCD 
					AND TRIM ( F026LMB ) = '9999' ) TSSU  -- 검사수가 (질가산미포함 : LMB 9999) 
			, ( SELECT TRIM ( L029DOC ) FROM MCLISDLIB . SG029H@ A 
				WHERE A . L029COR = MC010 . F010COR 
				AND A . L029GCD = MC010 . F010GCD 
				AND TRIM ( L029LMB ) = '' ) DOCD	 --보험코드 
			, ( SELECT TRIM ( F018OCD ) FROM MCLISDLIB . MC018M@ A 
				WHERE A . F018COR = MC010 . F010COR 
				AND A . F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB . MC018M@ B WHERE A . F018COR = B . F018COR AND A . F018GCD = B . F018GCD AND B . F018LMB = '9999' ) 
				AND A . F018GCD = MC010 . F010GCD 
				AND TRIM ( F018LMB ) = '9999' ) DOCD_TMP	 -- 보험코드(질가산미포함 : LMB 9999) 
			, ( SELECT TRIM ( F018OMK ) FROM MCLISDLIB . MC018M@ A 
				WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB . MC018M@ B WHERE A . F018COR = B . F018COR AND A . F018GCD = B . F018GCD AND B . F018LMB = '9999' ) 
				AND A . F018COR = MC010 . F010COR 
				AND A . F018GCD = MC010 . F010GCD 
				AND F018OMK != '' 
				AND TRIM ( F018LMB ) = '9999' 
				GROUP BY F018OMK ) OMK	 -- 분류번호 (질가산미포함 : LMB 9999) 
FROM MCLISDLIB . MC010M@ MC010 
LEFT JOIN WEBDBLIB . MWT001M@ MWT001 
		ON MC010 . F010COR = MWT001 . T001COR 
		AND MC010 . F010GCD = MWT001 . T001TCD 
		AND T001COR = I_COR 
		AND T001FLG = 'Y' 
		AND T001LFLG = 'Y' 
WHERE F010COR = I_COR 
AND F010GCD = LPAD(I_GCD,5,'0' ) 
		AND F010STS = 'Y' 
AND F010ACH = 'Y' ; 
SET O_MSGCOD = '200' ; 
OPEN CUR1 ; 
END  ; 

--  Generate SQL 
--  Version:                   V7R4M0 190621 
--  Generated on:              23/01/05 09:29:11 
--  Relational Database:       NEODIN 
--  Standards Option:          Db2 for i 
--DROP SPECIFIC PROCEDURE IF EXISTS WEBOBJLIB.MWIMGGRPRPT ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWIMGGRPRPT ( 
  IN I_COR VARCHAR(3) , 
  IN I_HOS VARCHAR(10) , 
  IN I_TERM_DIV VARCHAR(20) , 
  IN I_FR_DT DECIMAL(8, 0) , 
  IN I_TO_DT DECIMAL(8, 0) , 
  IN I_RECV_YN VARCHAR(20) , 
  IN I_UID VARCHAR(12) , 
  IN I_IP VARCHAR(30) ) 
  DYNAMIC RESULT SETS 1 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.MWIMGGRPRPT 
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
	SELECT DISTINCT 
			F600COR 
			, F600DAT  --접수일자 
			, F600JNO  --접수번호 
			, F100CHN  --차트번호 
			, TRIM ( F100NAM ) AS F100NAM  --수진자명 
			, F100HOS  --병원코드 
			, F010TCD  --검체코드 
			, F600GCD  --검사코드 
			, F600HAK  --학부 
			, F600CHR  --별지참조 
			, HOS_GCD  --병원검사코드 
			, TRIM ( F010FKN ) AS F010FKN  -- 검사명 
			, PGCD  -- 접수된 프로파일 코드 
			, ( SELECT 
					F999NM3 
				FROM MCLISDLIB . MC999D@ 
				WHERE F999COR = 'NML' 
				AND F999CD1 = 'OCST' 
				AND F999CD2 = 'IMPF' 
				AND F999CD3 = PGCD 
				FETCH FIRST 1 ROW ONLY 
				) AS PGCD_NM  -- 프로파일 단일 명 
			, ( SELECT S002RF2 FROM WEBDBLIB . MWS002M@ 
				WHERE 1 = 1 
				AND S002COR = 'NML' 
				AND S002SCD = PGCD		 -- 프로파일코드 
				AND S002RF1 = F600GCD  -- 검사코드 
				AND S002PSCD = 'CHN_TEMPLATE' 
				) AS CHN_TEMPLATE 
			, ( SELECT S002RF1 FROM WEBDBLIB . MWS002M@ 
				WHERE 1 = 1 
				AND S002COR = 'NML' 
				AND S002SCD = F100HOS		 -- 병원코드 
				AND S002PSCD = 'DPI300_HOS' 
				) AS DPI300_HOS  -- 이미지결과 크기 
			, ( SELECT S002RF1 FROM WEBDBLIB . MWS002M@ 
				WHERE 1 = 1 
				AND S002COR = 'NML' 
				AND S002SCD = F100HOS		 -- 병원코드 
				AND S002PSCD = 'URIN_MICRO_13_FORM' 
				) AS URIN_MICRO_13_FORM  -- 요검경검사를 별도로 출력할것인가/한장에 함께출력할 것인가 구분하는 값 
			, ( SELECT 
					TRIM ( F010FKN ) 
				FROM MCLISDLIB . MC010M@ G 
				WHERE G . F010COR = X . F600COR 
				AND G . F010GCD = X . PGCD 
				FETCH FIRST 1 ROW ONLY ) AS PGNM	 
			, IFNULL ( CU_SE , '' ) AS CU_SE  -- CU/SE 구분 
			, CU_SE_GCD 
			, S011RCL  -- 양식지 
			, S011RTP  --결과지 타입 
			, RPAD ( F010RNO , 4 , ' ' ) AS F010RNO  --결과지 폼 
			, F100CBC  --병동 
			, F600C03  -- 보고일자 구분 
			, CASE WHEN F600C03 = 'Y' THEN BDT  --보고일자 
				ELSE '' 
			END BDT 
			, CASE WHEN F600C03 = 'Y' THEN BDTT  --보고일자 + 보고시간 
				ELSE '' 
			END BDTT 
			, BCDT	 --병원처방일자 
			, F100ETC  -- 검체코드 또는 파일명을 위한 ETC2 
			, FHCDFL3  -- 파일명을 위한 처방번호 
			, FHCDFL5  -- 파일명을 위한 ETC1 
			, FHCDTCD  -- 파일명을 위한 TCD (검체코드) 
			 --기본 파일 명 ::: 차트키(수진자 키)_수진자명_검체코드_병원코드_01 ==> NULL 이면 
			, FILE_NM 
			, Y . S018SYN 
			 --PDF 파일일 경우 일반결과지로 출력 
			, CASE WHEN PDF_CNT > 0 THEN '78-STI-TMJ-01' 
			ELSE COALESCE ( Y . S018RFN , 'GENERAL-01' ) 
			END AS S018RFN  -- 리포트 템플릿 파일명 
			, F100JKN  --진료과목 
			, F100DRC  --진료의사 
			, F111GDT  --검체최취일 
			, FST_MAKE 
			, PDF_CNT	 -- PDF 개수 
			, IFNULL ( ( SELECT SUBSTRING ( F008GCD , 1 , 5 ) AS F008GCD 
						FROM QTEMP . MCI008T@ 
						WHERE F008COR = F600COR  --회사구분 
						AND F008HOS = F100HOS  --병원코드 
						AND F008DAT = F600DAT 
						AND F008JNO = F600JNO 
						AND SUBSTRING ( F008GCD , 1 , 5 ) = F600GCD 
						AND ( IFNULL ( I_RECV_YN , '' ) = '' OR F008FL2 = I_RECV_YN )  --수신여부 
						) , '' ) AS F008GCD  -- 이미지 출력 관련 수정(recvImg.jsp) 
, MICROBE_RESULT_STS 
, MICROBE_RESULT_HOS 
		FROM ( 
			SELECT 
				F600COR 
				, F600DAT  --접수일자 
				, F600JNO  --접수번호 
				, F100CHN  --차트번호 
				, F100NAM  --수진자명 
				, F100HOS  --병원코드 
				, F010TCD  --검체코드 
				, F600GCD  --검사코드 
				, F010FKN  -- 검사명 
				, F600ACD  --부속코드 
				, F600CHR  --별지참조 
				, IFNULL ( PGCD_A , PGCD ) AS PGCD  -- 접수된 프로파일 코드 
				, TRIM ( CU_SE ) AS CU_SE  -- CU/SE 구분 
				, CASE WHEN TRIM ( CU_SE ) = 'CU' THEN CONCAT ( X . F600GCD , ( SELECT 
																		AA . F600GCD 
																	FROM MCLISDLIB . MC600M@ AA 
																	WHERE AA . F600COR = X . F600COR 
																	AND AA . F600DAT = X . F600DAT	 
																	AND AA . F600JNO = X . F600JNO 
																	AND AA . F600GCD = ( SELECT 
																							F999CD3 
																						FROM MCLISDLIB . MC999D@ 
																						WHERE F999COR = AA . F600COR 
																						AND F999CD1 = 'CLIC' 
																						AND F999CD2 = 'MBGC' 
																						AND F999CD3 = AA . F600GCD 
																						AND F999NM1 = 'SE' 
																						AND TRIM ( F999NM2 ) = '' )	 
															) 
													) 
			WHEN TRIM ( CU_SE ) = 'SE' THEN CONCAT ( ( SELECT 
																		AA . F600GCD 
																	FROM MCLISDLIB . MC600M@ AA 
																	WHERE AA . F600COR = X . F600COR 
																	AND AA . F600DAT = X . F600DAT	 
																	AND AA . F600JNO = X . F600JNO 
																	AND AA . F600GCD = ( SELECT 
																							F999CD3 
																						FROM MCLISDLIB . MC999D@ 
																						WHERE F999COR = AA . F600COR 
																						AND F999CD1 = 'CLIC' 
																						AND F999CD2 = 'MBGC' 
																						AND F999CD3 = AA . F600GCD 
																						AND F999NM1 = 'CU' 
																						AND TRIM ( F999NM2 ) = '' )	 
															) , X . F600GCD 
													) 
						ELSE '' END AS CU_SE_GCD 
				, S011RTP  --결과지 타입 
				, S011RCL  -- 양식지 
				, F010RNO  --결과지 폼 
				, F100CBC  --병동 
				, SUBSTRING ( BDTT , 1 , 8 ) AS BDT  --보고일자 
				, BDTT  --보고일자 + 보고시간 
				, BCDT	 --병원처방일자 
				, CASE WHEN IFNULL ( TRIM ( F100ETC ) , '' ) = '' THEN '검체번호' ELSE TRIM ( F100ETC ) END AS F100ETC  -- 검체코드 
				, FHCDFL3  -- 파일명을 위한 처방번호 
				, FHCDFL5  -- 파일명을 위한 ETC1 
				, FHCDTCD  -- 파일명을 위한 TCD (검체코드) 
				, F100JKN  --진료과목 
				, F100DRC  --진료의사 
				, F600HAK  --학부 
				, F111GDT  --검체최취일 
				, F600C03  -- 보고일자 구분 
				, FST_MAKE 
				, PDF_CNT	 -- PDF 개수 
				 --기본 파일 명 ::: 차트키(수진자 키)_수진자명_검체코드_병원코드_01 ==> NULL 이면 
				, IFNULL ( ( SELECT 
						LISTAGG ( CASE WHEN TRIM ( S012NCL ) = 'CHART' THEN TRIM ( F100CHN )  --차트번호 
									WHEN TRIM ( S012NCL ) = 'P_NM' THEN TRIM ( F100NAM )  --수진자명 
									WHEN TRIM ( S012NCL ) = 'HOS' THEN TRIM ( F100HOS )  -- 병원코드 
									WHEN TRIM ( S012NCL ) = 'TCD' THEN ( CASE 
																			WHEN ( IFNULL ( TRIM ( F100HOS ) , '' ) = '27466' AND IFNULL 
( TRIM ( F600GCD ) , '' ) = '51023' ) THEN ( SELECT TRIM ( FHCDTCD ) FROM MCLISDLIB . "MCHCDH@" M 
																																												WHERE 1 = 1 
																																												AND FHCDCOR = TRIM ( F600COR ) 
																																												AND FHCDDAT = TRIM ( F600DAT ) 
																																												AND FHCDJNO = TRIM ( F600JNO ) 
																																												AND SUBSTRING ( FHCDGCD , 1 , 5 ) IN ( '68028' , '52033' ) 
																																											) 
																			WHEN IFNULL ( TRIM ( FHCDTCD_1 ) , '' ) <> '' THEN FHCDTCD_1 
																			WHEN IFNULL ( TRIM ( FHCDTCD_2 ) , '' ) <> '' THEN FHCDTCD_2 
																		WHEN IFNULL ( TRIM ( FBOHIND ) , '' ) <> '' THEN FBOHIND 
																		ELSE ( CASE WHEN IFNULL ( TRIM ( F100ETC ) , '' ) = '' THEN '검체번호' ELSE TRIM ( F100ETC ) END ) 
																		END 
																		)  -- 검체코드 
									WHEN TRIM ( S012NCL ) = 'GCD' THEN TRIM ( F600GCD )  -- 검사코드 
									WHEN TRIM ( S012NCL ) = 'HOS_GCD' THEN ( CASE 
																				WHEN ( IFNULL ( TRIM ( F100HOS ) , '' ) = '27466' AND IFNULL ( TRIM ( F600GCD ) , '' ) = '51023' ) THEN ( SELECT TRIM ( FHCDCCD ) FROM MCLISDLIB . "MCHCDH@" M 
																																												WHERE 1 = 1 
																																												AND FHCDCOR = TRIM ( F600COR ) 
																																												AND FHCDDAT = TRIM ( F600DAT ) 
																																												AND FHCDJNO = TRIM ( F600JNO ) 
																																												AND SUBSTRING ( FHCDGCD , 1 , 5 ) IN ( '68028' , '52033' ) 
																																												FETCH FIRST 1 ROW ONLY 
																																											) 
																				WHEN IFNULL ( HOS_GCD01 , '' ) <> '' THEN HOS_GCD01  -- 병원검사코드 
																			WHEN IFNULL ( HOS_GCD02 , '' ) <> '' THEN HOS_GCD02 
																			WHEN IFNULL ( HOS_GCD03 , '' ) <> '' THEN HOS_GCD03 
																			WHEN IFNULL ( HOS_GCD04 , '' ) <> '' THEN HOS_GCD04 
																			WHEN IFNULL ( HOS_GCD05 , '' ) <> '' THEN HOS_GCD05 
																			ELSE TRIM ( F600GCD ) END ) 
									WHEN TRIM ( S012NCL ) = 'RPT_TYPE' THEN TRIM ( S011RTP )  -- 결과지 타입 
									WHEN TRIM ( S012NCL ) = 'RPT_FORM' THEN TRIM ( F010RNO )  -- 결과지 폼 
									WHEN TRIM ( S012NCL ) = 'H_O_DT' THEN TRIM ( BCDT )  -- 병운처방일자 
									WHEN TRIM ( S012NCL ) = 'J_NO' THEN TRIM ( CHAR ( F600JNO ) )  -- 접수번호 
									WHEN TRIM ( S012NCL ) = 'J_DT' THEN TRIM ( CHAR ( F600DAT ) )  -- 접수일자 
									WHEN TRIM ( S012NCL ) = 'B_DT' THEN TRIM ( SUBSTRING ( BDTT , 1 , 8 ) )  -- 보고일자 
									WHEN TRIM ( S012NCL ) = 'O_DT_TM' THEN TRIM ( BDTT )  --보고일자 + 시간 
									WHEN TRIM ( S012NCL ) = 'GY' THEN 'GY'  --병동 GY 
									WHEN TRIM ( S012NCL ) = 'MD' THEN 'MD'  --병동 MD 
									WHEN TRIM ( S012NCL ) = 'PD' THEN 'PD'  --병동 PD 
									WHEN TRIM ( S012NCL ) = 'OB' THEN 'OB'  --병동 OB 
									WHEN TRIM ( S012NCL ) = 'N_GY' THEN ( CASE WHEN F100JKN = '' THEN 'GY' ELSE F100JKN END )  --병동 NULL 일경우 GY 
									WHEN TRIM ( S012NCL ) = 'PDGYOB_GY' THEN ( CASE WHEN F100JKN = 'GY' THEN 'GY' 
																			WHEN F100JKN = 'PD' THEN 'PD' 
																			WHEN F100JKN = 'OB' THEN 'OB' 
																			WHEN F100JKN = 'HP' THEN 'HP' 
																			WHEN F100JKN = 'MD' THEN 'MD' 
									ELSE 'GY' END )  -- 병동 PD/GY/OB 아니면 GY 
									WHEN TRIM ( S012NCL ) = 'DIRECT' THEN TRIM ( S012NDV ) 
									WHEN TRIM ( S012NCL ) = 'H_CHU_FL5' THEN TRIM ( FHCDFL3 ) 
									ELSE TRIM ( S012NCL ) END 
								, '_' ) WITHIN GROUP ( ORDER BY S012NSQ ) AS COL 
					FROM WEBDBLIB . MWS012M@ 
					WHERE S012COR = F600COR 
					AND S012HOS = F100HOS 
					AND S012RCL = S011RCL					 
					) 
					 --, CONCAT( CONCAT( CONCAT( CONCAT(F100CHN ,'_'),  CONCAT(F100NAM ,'_') ),  CONCAT((CASE WHEN IFNULL(TRIM(F100ETC), '') = '' THEN '검체번호' ELSE F100ETC END) ,'_') ),  CONCAT(F100HOS ,'_01_') )  ) AS FILE_NM  --파일명 구분 
					, CONCAT ( CONCAT ( CONCAT ( CONCAT ( F100CHN , '_' ) , CONCAT ( F100NAM , '_' ) ) , CONCAT ( TRIM ( ( CASE WHEN IFNULL ( TRIM ( FHCDTCD_1 ) , '' ) <> '' THEN FHCDTCD_1 
																										WHEN IFNULL ( TRIM ( FHCDTCD_2 ) , '' ) <> '' THEN FHCDTCD_2 
																									WHEN IFNULL ( TRIM ( FBOHIND ) , '' ) <> '' THEN FBOHIND 
																									ELSE ( CASE WHEN IFNULL ( TRIM ( F100ETC ) , '' ) = '' THEN '검체번호' ELSE TRIM ( F100ETC ) END ) 
																									END ) ) , '_' ) ) 
																							, CONCAT ( ( CASE WHEN IFNULL ( HOS_GCD01 , '' ) <> '' THEN HOS_GCD01 
																											WHEN IFNULL ( HOS_GCD02 , '' ) <> '' THEN HOS_GCD02 
																										WHEN IFNULL ( HOS_GCD03 , '' ) <> '' THEN HOS_GCD03 
																											WHEN IFNULL ( HOS_GCD04 , '' ) <> '' THEN HOS_GCD04 
																											WHEN IFNULL ( HOS_GCD05 , '' ) <> '' THEN HOS_GCD05 
																											ELSE TRIM ( F600GCD ) END ) , '_01' ) ) ) AS FILE_NM  --파일명 구분																																			 									ELSE TRIM(F600GCD)  END ) ,'_01') )  ) AS FILE_NM  --파일명 구분 
				, CASE WHEN IFNULL ( HOS_GCD01 , '' ) <> '' THEN HOS_GCD01 
					WHEN IFNULL ( HOS_GCD02 , '' ) <> '' THEN HOS_GCD02 
				WHEN IFNULL ( HOS_GCD03 , '' ) <> '' THEN HOS_GCD03 
					WHEN IFNULL ( HOS_GCD04 , '' ) <> '' THEN HOS_GCD04 
					WHEN IFNULL ( HOS_GCD05 , '' ) <> '' THEN HOS_GCD05 
					ELSE TRIM ( F600GCD ) END AS HOS_GCD																																											 
				/*, IFNULL(( SELECT 
						LISTAGG(S012NCL,'|') within group(order by S012NSQ)  COL 
					FROM WEBDBLIB.MWS012M@ 
					WHERE S012COR = X.F600COR 
					AND   S012HOS = X.F100HOS 
					AND   S012RCL = X.F010RNO 
					) 
					, 'CHART|P_NM|TCD|HOS|DIRECT|')    AS NCL  --파일명 구분 NULL일 경우 기본 파일명 규칙 
				, IFNULL( (SELECT 
							LISTAGG(S012NDV,'|') within group(order by S012NSQ)  COL 
						FROM WEBDBLIB.MWS012M@ 
						WHERE S012COR = X.F600COR 
						AND   S012HOS = X.F100HOS 
						AND   S012RCL = X.F010RNO 
						) 
				  , '||||01|')   AS NDV --파일명 중 다이렉트 값  NULL일 경우 기본 파일명 규칙 */ 
, MICROBE_RESULT_STS 
, MICROBE_RESULT_HOS 
			FROM ( 
				SELECT 
					A . F600COR 
					, A . F600DAT  --접수일자 
					, TRIM ( A . F600JNO ) AS F600JNO  --접수번호 
					 -- 2022.01.06 복호화 제거 
					 --, ECHELON.PI_DECSN(D.F100CHN) AS F100CHN  --차트번호 
					, TRIM ( D . F100CHN ) AS F100CHN  --차트번호 
					, TRIM ( D . F100NAM ) AS F100NAM  --수진자명 
					, D . F100HOS  --병원코드 
					, TRIM ( B . F010TCD ) AS F010TCD  --검체코드 
					, CASE WHEN TRIM ( REPLACE ( A . F600CHR , ' ' , '' ) ) LIKE '%별지%' 
--						THEN TRIM(REPLACE(A.F600CHR,' ','' )) 
						THEN '' 
						ELSE '' END AS F600CHR  --별지참조 
					 --파일명에 사용할 검체코드(샘플번호) 파일명을 위한 ETC2(파일명 생성시 오루나는 문자 제거) 
					, REPLACE ( REPLACE ( REPLACE ( REPLACE ( REPLACE ( REPLACE ( REPLACE ( REPLACE ( REPLACE ( TRIM ( D . F100ETC ) , '/' , '_' ) , '\' , '' ) , ':' , '' ) , '*' , '' ) , '?' , '' ) , '"' , '' ) , '<' , '' ) , '>' , '' ) , '|' , '' ) AS F100ETC 
					, A . F600HAK  --학부 
					, ( SELECT 
							TRIM ( REPLACE ( REPLACE ( FHCDFL3 , '^D:' , '' ) , 'N:' , '' ) ) 
						FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = A . F600COR 
						AND FHCDDAT = A . F600DAT 
						AND FHCDJNO = A . F600JNO 
						 --AND   FHCDGCD = A.F600GCD 
						AND LOCATE ( A . F600GCD , FHCDGCD ) > 0 
						FETCH FIRST 1 ROW ONLY ) AS FHCDFL3  -- 파일명을 위한 처방번호 
					, ( SELECT 
							TRIM ( HCDTT . FHCDFL5 ) 
						FROM MCLISDLIB . MCHCDH@ HCDTT 
						WHERE FHCDCOR = A . F600COR 
						AND FHCDDAT = A . F600DAT 
						AND FHCDJNO = A . F600JNO 
						 --AND   FHCDGCD = A.F600GCD 
						AND LOCATE ( A . F600GCD , FHCDGCD ) > 0 
						FETCH FIRST 1 ROW ONLY ) AS FHCDFL5  -- 파일명을 위한 ETC1 
					, ( SELECT 
							TRIM ( FHCDTCD ) 
						FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = A . F600COR 
						AND FHCDDAT = A . F600DAT 
						AND FHCDJNO = A . F600JNO 
						 --AND   FHCDGCD = A.F600GCD 
						AND LOCATE ( A . F600GCD , FHCDGCD ) > 0 
						FETCH FIRST 1 ROW ONLY ) AS FHCDTCD  -- 파일명을 위한 TCD (검체코드) 
					, A . F600GCD  --검사코드 
					, B . F010FKN  -- 검사명 
					, A . F600ACD  --부속코드 
					, ( SELECT 
							H . F110GCD 
						FROM MCLISDLIB . MC110H@ H , MCLISDLIB . MC012M@ G 
						WHERE H . F110COR = G . F012COR 
						AND H . F110GCD = G . F012PCD 
						AND H . F110COR = A . F600COR 
						AND H . F110DAT = A . F600DAT 
						AND H . F110JNO = A . F600JNO 
						AND G . F012GCD = A . F600GCD 
						AND H . F110GCD = 'A1008' 
						FETCH FIRST 1 ROW ONLY ) AS PGCD_A  -- 접수된 프로파일 코드 
					, IFNULL ( ( SELECT 
							H . F110GCD 
						FROM MCLISDLIB . MC110H@ H , MCLISDLIB . MC012M@ G 
						WHERE H . F110COR = G . F012COR 
						AND H . F110GCD = G . F012PCD 
						AND H . F110COR = A . F600COR 
						AND H . F110DAT = A . F600DAT 
						AND H . F110JNO = A . F600JNO 
						AND G . F012GCD = A . F600GCD 
						AND H . F110STS <> 'D' 
						FETCH FIRST 1 ROW ONLY ) , A . F600GCD ) AS PGCD  -- 접수된 프로파일 코드 
					, ( SELECT CONCAT ( F999NM1 , F999NM2 ) FROM MCLISDLIB . MC999D@ 
					WHERE F999COR = A . F600COR AND F999CD1 = 'CLIC' 
					AND F999CD2 = 'MBGC' AND F999CD3 = A . F600GCD ) AS CU_SE 
					, TRIM ( C . S011RTP ) AS S011RTP  --결과지 타입 
					, TRIM ( C . S011RCL ) AS S011RCL  -- 양식지 
					, TRIM ( A . F600SDB ) AS F010RNO  --결과지 폼 
					, TRIM ( D . F100CBC ) AS F100CBC  --병동 
					, TRIM ( D . F100JKN ) AS F100JKN  --진료과목 
					, TRIM ( D . F100DRC ) AS F100DRC  --진료의사 
					, ( SELECT 
						MIN ( F111GDT ) 
					FROM MCLISDLIB . MC111D@ 
					WHERE F111COR = A . F600COR 
					AND F111DAT = A . F600DAT 
					AND F111JNO = A . F600JNO 
					AND F111GCD = A . F600GCD ) AS F111GDT  --검체최취일 
					, ( SELECT 
							MAX ( FHCDFL4 ) 
						FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = A . F600COR 
						AND FHCDDAT = A . F600DAT 
						AND FHCDJNO = A . F600JNO ) AS BCDT	 --병원처방일자 
					, TRIM ( A . F600C03 ) AS F600C03  -- 보고상태 구분 
					, ( SELECT 
							MAX ( LPAD ( F600BDT , 8 , 0 ) || LPAD ( F600CTM , 6 , 0 ) ) 
						FROM MCLISDLIB . MC600M@ 
						WHERE F600COR = A . F600COR 
						AND F600DAT = A . F600DAT 
						AND F600JNO = A . F600JNO 
						AND F600GCD = A . F600GCD 
						AND F600C03 = 'Y' 
						AND F600C05 != 'N' 
						AND F600SDB != 'K   ' ) AS BDTT  --보고일자 + 보고시간 
					, CASE WHEN A . F600C03 <> 'Y' THEN 'N' 
					/*WHEN A.F600C03 = 'Y' 
					       			AND (     A.F600C04 = '0' OR A.F600C04 = 'C' 
					       			      OR  A.F600C04 = 'Y' OR A.F600C04 = 'T' 
					       			      OR  A.F600C04 = 'B' OR A.F600C04 = 'Z' 
					       			      OR  A.F600C04 = 'E' OR A.F600C04 = 'F' 
					       			      OR  A.F600C04 = 'G' OR A.F600C04 = 'W' 
					       			      ) THEN 'N'*/ 
						ELSE 'Y' END AS FST_MAKE	 
					, ( SELECT TRIM ( FHCDCCD ) 
							FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = A . F600COR 
						AND FHCDDAT = A . F600DAT 
						AND FHCDJNO = A . F600JNO 
						AND SUBSTRING ( FHCDGCD , 1 , 5 ) = A . F600GCD 
						FETCH FIRST 1 ROW ONLY ) AS HOS_GCD01 
					, ( SELECT TRIM ( FHCDCCD ) 
							FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = A . F600COR 
						AND FHCDDAT = A . F600DAT 
						AND FHCDJNO = A . F600JNO 
						AND FHCDGCD = CONCAT ( A . F600GCD , CASE WHEN A . F600HOS = '22015' THEN '00' ELSE '01' END ) 
						FETCH FIRST 1 ROW ONLY ) AS HOS_GCD02 
				, ( SELECT TRIM ( FBOHNCD ) 
					FROM MCLISDLIB . MCWRKBOH4 
						WHERE FBOHCOR = A . F600COR 
						AND FBOHDAT = A . F600DAT 
						AND FBOHSEQ = A . F600JNO 
						AND FBOHGCD = A . F600GCD 
						FETCH FIRST 1 ROW ONLY ) AS HOS_GCD03 
				, ( SELECT TRIM ( F033TCD ) 
						FROM MCLISDLIB . MC033M@ 
						WHERE F033COR = A . F600COR 
						AND F033HOS = A . F600HOS 
						AND SUBSTRING ( F033NCD , 1 , 5 ) = A . F600GCD 
						FETCH FIRST 1 ROW ONLY ) AS HOS_GCD04 
					, ( SELECT TRIM ( FHCDCCD ) 
							FROM MCLISDLIB . MCHCDH@ 
							WHERE FHCDCOR = A . F600COR 
							AND FHCDDAT = A . F600DAT 
							AND FHCDJNO = A . F600JNO 
							AND FHCDGCD IN ( SELECT F012PCD FROM MCLISDLIB . "MC012M@" WHERE F012COR = A . F600COR AND F012GCD = A . F600GCD ) 
						FETCH FIRST 1 ROW ONLY ) AS HOS_GCD05 
				 --검체번호 뽑기 위한 것 
				, ( SELECT 
							TRIM ( FHCDTCD ) 
						FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = F600COR 
						AND FHCDDAT = F600DAT 
						AND FHCDJNO = F600JNO 
						AND FHCDGCD = CONCAT ( F600GCD , '01' )  -- 2022.09.01 : 담당자요청으로 조건 원복 
						 --AND FHCDGCD = ( CASE WHEN F600HOS = '27466' AND F600GCD = '51023' THEN '68028' ELSE CONCAT ( F600GCD , '01' ) END ) 
						FETCH FIRST 1 ROW ONLY ) AS FHCDTCD_1 
					, ( SELECT 
							TRIM ( FHCDTCD ) 
						FROM MCLISDLIB . MCHCDH@ 
						WHERE FHCDCOR = F600COR 
						AND FHCDDAT = F600DAT 
						AND FHCDJNO = F600JNO 
						AND SUBSTRING ( FHCDGCD , 1 , 5 ) = F600GCD 
						FETCH FIRST 1 ROW ONLY ) AS FHCDTCD_2 
					, ( SELECT 
						TRIM ( FBOHIND ) 
						FROM MCLISDLIB . MCWRKBOH@ 
						WHERE FBOHCOR = F600COR 
						AND FBOHDAT = F600DAT 
						AND FBOHSEQ = F600JNO 
						AND FBOHGCD = F600GCD 
						FETCH FIRST 1 ROW ONLY ) AS FBOHIND 
					, ( SELECT COUNT ( * ) 
						FROM MCLISDLIB . MC602D@ 
						WHERE F602COR = A . F600COR 
						AND F602DAT = A . F600DAT 
						AND F602JNO = A . F600JNO 
						AND F602GCD = A . F600GCD 
						AND UPPER ( TRIM ( F602PAT ) ) LIKE '%.PDF' )	AS PDF_CNT	 -- PDF 개수 
                                         , (SELECT 
                                        	CASE WHEN COUNT (*) = 0 
                                    			or (SELECT count(*)
                                    					FROM MCLISDLIB . "MC625D@" MD 
                                    					WHERE 1 = 1 
                                    					AND F625COR = A.F600COR 
                                    					AND F625DAT = A.F600DAT
                                    					AND F625JNO = A.F600JNO
                                    					AND F625LST = 'Y' 
                                    					AND F625COK = 'Y') > 0 THEN '최종보고'
                                    		ELSE '' END
                                            FROM MCLISDLIB . "MC625D@" MD 
                                            WHERE 1 = 1 
                                            AND F625COR = A.F600COR
                                            AND F625DAT = A.F600DAT
                                            AND F625JNO = A.F600JNO
                                        ) as microbe_result_sts  
                                        , ( SELECT ( CASE WHEN COUNT ( * ) > 0 THEN '미생성병원' ELSE '' END ) 
                                            FROM WEBDBLIB . MWS002M@ 
                				WHERE 1 = 1 
                				AND S002COR = 'NML' 
                				AND S002SCD = D . F100HOS		 
                				AND S002PSCD = 'MICRO_MID_RESULT_EXC' 
                			) AS MICROBE_RESULT_HOS  -- 미생물 결과가 중간보고인 경우, 이미지미생성 (최종보고만 이미지생성) 하는 병원 관리 
				FROM MCLISDLIB . MC600M@ A , MCLISDLIB . MC010M@ B , WEBDBLIB . MWS011M@ C , MCLISDLIB . MC100H@ D 
				WHERE A . F600COR = B . F010COR 
				AND A . F600GCD = B . F010GCD 
				AND B . F010COR = C . S011COR 
				AND A . F600SDB = C . S011RFM 
				AND A . F600COR = D . F100COR 
				AND A . F600DAT = D . F100DAT 
				AND A . F600JNO = D . F100JNO 
				AND A . F600C03 = 'Y' 
				AND A . F600C05 != 'N' 
				AND A . F600SDB != 'K   ' 
				 --AND  ( TRIM(A.F600acd) = '' OR TRIM(A.F600acd) = '00' ) 
				AND ( TRIM ( A . F600ACD ) = '' OR TRIM ( A . F600ACD ) = ( SELECT 
																	MAX ( F600ACD ) 
																	FROM MCLISDLIB . "MC600M@" 
																	WHERE F600COR = A . F600COR 
																	AND F600DAT = A . F600DAT 
																	AND F600JNO = A . F600JNO 
																	AND F600GCD = A . F600GCD ) 
				) 
				AND ( A . F600COR , A . F600DAT , A . F600JNO ) IN (	 
					SELECT 
							F008COR , F008DAT , F008JNO 
						FROM QTEMP . MCI008T@ 
--						FROM MCLISDLIB.MCI008T@ 
						WHERE F008COR = I_COR  --회사구분 
						AND F008HOS = I_HOS  --병원코드 
						AND ( ( I_TERM_DIV = 'D' AND F008DAT >= I_FR_DT AND F008DAT <= I_TO_DT )  --접수일자 
						OR ( I_TERM_DIV = 'P' AND F008BDT >= I_FR_DT AND F008BDT <= I_TO_DT ) )  --보고일자 
						AND ( IFNULL ( I_RECV_YN , '' ) = '' OR F008FL2 = I_RECV_YN )  --수신여부 
					) 
			) X 
		) X LEFT OUTER JOIN WEBDBLIB . MWS018M@ Y ON X . F600COR = Y . S018COR AND X . F600GCD = Y . S018GCD 
		ORDER BY X . F600COR , X . F600DAT , X . F600JNO , X . F600GCD 
		; 
		 
	OPEN CUR1 ; 
	 
END  ; 

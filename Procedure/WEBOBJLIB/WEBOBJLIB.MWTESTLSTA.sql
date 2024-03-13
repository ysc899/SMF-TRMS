DROP PROCEDURE WEBOBJLIB.MWTESTLSTA;

CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWTESTLSTA (IN I_COR VARCHAR(3)
									  			, IN I_UID VARCHAR(12)
									  			, IN I_IP  VARCHAR(30)
									  			, OUT O_MSGCOD VARCHAR(3)
									  			, OUT O_ERRCOD VARCHAR(10)
									  			, IN I_GCD  VARCHAR(5)
									  			, IN I_FKN  VARCHAR(30)
									  			, IN I_SOCD VARCHAR(15)
									  			, IN I_HOS VARCHAR(5))
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN	
	-- 추가검사의뢰리스트를 별도로 관리한다.
	IF I_HOS = '12527' THEN
		BEGIN
			DECLARE CUR1 CURSOR WITH RETURN FOR
				-- 검사항목조회
				SELECT
				TRIM ( F010COR ) F010COR --COR
				, TRIM ( F010DOW ) F010DOW -- 검사요일
				, TRIM ( F010BDY ) F010BDY -- 검사기준일
				, F010GCD -- 검사코드
				, TRIM ( F010FKN ) F010FKN -- 검사명(한글)
				, TRIM ( F010SNM ) F010SNM -- 검사명(약어)
				, TRIM ( F010TCD ) F010TCD -- 검체 코드 (F999CD2 ='SAMP' )
				, ( SELECT TRIM ( MC9.F999NM2 )
					 FROM MCLISDLIB.MC999D@ MC9
					 WHERE F999CD2 = 'SAMP'
					 AND F999COR = F010COR
					 AND F999CD3 = F010TCD
				   ) AS F010TNM					--검체 명
				, TRIM ( F010MSC ) F010MSC -- 검사법 -코드
				, (
					SELECT
						MAX ( F028TXT1 )
						|| MAX ( F028TXT2 )
						|| MAX ( F028TXT3 )
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
								, F028GCD
							FROM MCLISDLIB.MC028D@ MC28
							WHERE F028COR = I_COR
						) AS MC28
						WHERE MC28.F028GCD = F010MSC
						GROUP BY F028GCD
					) AS F010MSNM
				, TRIM ( T001DAY ) T001DAY -- 검사일
				, TRIM ( F010EED ) F010EED -- 검사소요일수
				,TRIM(A.F018OCD) AS SOCD				--보험코드(서울)
				, ( SELECT TRIM ( F018OCD ) FROM MCLISDLIB."MC018M@" A
					WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB."MC018M@" B WHERE A.F018COR = B.F018COR AND A.F018GCD = B.F018GCD AND B.F018LMB = '6526')
					AND A.F018COR = MC010.F010COR 
					AND A.F018GCD = MC010.F010GCD
					AND TRIM ( F018LMB ) = '6526' ) BOCD --보험코드(부산)
				FROM MCLISDLIB.MC010M@ MC010
					LEFT JOIN MCLISDLIB."MC018M@" A ON F018GCD = MC010.F010GCD
			 			AND A.F018COR = MC010.F010COR
			 			AND A.F018GCD = MC010.F010GCD 
--						AND  TRIM(F018LMB) = ''
			            AND F018OSD = (SELECT MAX(F018OSD) 
			                 FROM  MCLISDLIB."MC018M@" B
			                 WHERE A.F018COR = B.F018COR
			                 AND A.F018GCD = B.F018GCD)			--서울 보험코드
					LEFT JOIN WEBDBLIB."MWT001M@" MWT001
						ON MC010.F010COR = MWT001.T001COR
						AND MC010.F010GCD = MWT001.T001TCD
						AND T001FLG = 'Y'
						AND T001LFLG = 'Y'
				WHERE F010COR = I_COR
				AND F010STS = 'Y'
				AND F010ACH = 'Y'
				AND F010GCD LIKE '%' || I_GCD || '%'
				AND F010FKN LIKE '%' || I_FKN || '%'
				AND UPPER(A.F018OCD) LIKE '%' || UPPER(I_SOCD) || '%'
				AND F010GCD NOT IN (SELECT R001GCD AS GCD
											  FROM WEBDBLIB.MWR001C@ A
											  WHERE A.R001COR = I_COR
											  AND A.R001HOS = 'I_HOS'
											);
				OPEN CUR1 ;
			SET O_ERRCOD = '' ;
			SET O_MSGCOD = '200' ;
		
		END;
	ELSE
		BEGIN
			DECLARE CUR1 CURSOR WITH RETURN FOR
				-- 검사항목조회
				SELECT
				TRIM ( F010COR ) F010COR --COR
				, TRIM ( F010DOW ) F010DOW -- 검사요일
				, TRIM ( F010BDY ) F010BDY -- 검사기준일
				, F010GCD -- 검사코드
				, TRIM ( F010FKN ) F010FKN -- 검사명(한글)
				, TRIM ( F010SNM ) F010SNM -- 검사명(약어)
				, TRIM ( F010TCD ) F010TCD -- 검체 코드 (F999CD2 ='SAMP' )
				, ( SELECT TRIM ( MC9.F999NM2 )
					 FROM MCLISDLIB.MC999D@ MC9
					 WHERE F999CD2 = 'SAMP'
					 AND F999COR = F010COR
					 AND F999CD3 = F010TCD
				   ) AS F010TNM					--검체 명
				, TRIM ( F010MSC ) F010MSC -- 검사법 -코드
				, (
					SELECT
						MAX ( F028TXT1 )
						|| MAX ( F028TXT2 )
						|| MAX ( F028TXT3 )
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
								, F028GCD
							FROM MCLISDLIB.MC028D@ MC28
							WHERE F028COR = I_COR
						) AS MC28
						WHERE MC28.F028GCD = F010MSC
						GROUP BY F028GCD
					) AS F010MSNM
				, TRIM ( T001DAY ) T001DAY -- 검사일
				, TRIM ( F010EED ) F010EED -- 검사소요일수
				,TRIM(A.F018OCD) AS SOCD				--서울 보험코드
				, ( SELECT TRIM ( F018OCD ) FROM MCLISDLIB."MC018M@" A
					WHERE F018OSD = ( SELECT MAX ( F018OSD ) FROM MCLISDLIB."MC018M@" B WHERE A.F018COR = B.F018COR AND A.F018GCD = B.F018GCD AND B.F018LMB = '6526')
					AND A.F018COR = MC010.F010COR 
					AND A.F018GCD = MC010.F010GCD
					AND TRIM ( F018LMB ) = '6526' ) BOCD --보험코드(부산)
				FROM MCLISDLIB.MC010M@ MC010
					LEFT JOIN MCLISDLIB."MC018M@" A ON F018GCD = MC010.F010GCD
			 			AND A.F018COR = MC010.F010COR
			 			AND A.F018GCD = MC010.F010GCD 
--						AND  TRIM(F018LMB) = ''
			            AND F018OSD = (SELECT MAX(F018OSD) 
			                 FROM  MCLISDLIB."MC018M@" B
			                 WHERE A.F018COR = B.F018COR
			                 AND A.F018GCD = B.F018GCD)			--서울 보험코드
					LEFT JOIN WEBDBLIB."MWT001M@" MWT001
						ON MC010.F010COR = MWT001.T001COR
						AND MC010.F010GCD = MWT001.T001TCD
						AND T001FLG = 'Y'
						AND T001LFLG = 'Y'
				WHERE F010COR = I_COR
				AND F010STS = 'Y'
				AND F010ACH = 'Y'
				AND F010GCD LIKE '%' || I_GCD || '%'
				AND F010FKN LIKE '%' || I_FKN || '%'
				AND UPPER(A.F018OCD) LIKE '%' || UPPER(I_SOCD) || '%'
				AND F010GCD NOT IN (SELECT R001GCD AS GCD
											  FROM WEBDBLIB.MWR001C@ A
											  WHERE A.R001COR = I_COR
											  AND A.R001HOS = ''
											);
				OPEN CUR1 ;
			SET O_ERRCOD = '' ;
			SET O_MSGCOD = '200' ;
		
		END;
	END IF;
END
DROP PROCEDURE WEBOBJLIB.MWS005C1;


CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWS005C1
(
	  IN  I_COR 	VARCHAR(3) 	-- COR
	, IN  I_UID 	VARCHAR(12) -- 사용자 ID
	, IN  I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)	-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)	-- 에러 코드
	, IN  I_SAVEFLG	VARCHAR(1)	--	SAVEFLG
	, IN  I_LOGCOR	VARCHAR(3)	--	COR
	, IN  I_LOGLID	VARCHAR(12) --	LOGIN ID
	, IN  I_PASASW	VARCHAR(20)	--	비밀번호
	, IN  I_PASASW_SHA VARCHAR(1000)--	비밀번호 (SHA암호화)
	, IN  I_LOGHOS	VARCHAR(5)	--	병원코드
	, IN  I_FNM		VARCHAR(40)	--	병원명
	, IN  I_GIC		VARCHAR(8)	--	요양기관코드
	, IN  I_TEL		VARCHAR(20)	--	전화번호
	, IN  I_LOGEML	VARCHAR(80)	--	이메일주소
	, IN  I_AUTH	VARCHAR(20)	--	권한그룹
	, IN  I_WNO		VARCHAR(60)	--	대표자명
	, IN  I_LOGGNO	VARCHAR(20)	--	면허번호 	
	, IN  I_SNO		VARCHAR(60)	--	사업자NO
	, IN  I_DRS		VARCHAR(100)	--	원장출신학교
	, IN  I_HPH		VARCHAR(200)	--	병원홈페이지
	, IN  I_ITM		VARCHAR(100)--	전문진료과목
	, IN  I_POS		VARCHAR(6)	--	우편번호
	, IN  I_JAD1	VARCHAR(500)	--	지번 주소1
	, IN  I_DAD1	VARCHAR(500)	--	도로명 주소1
	, IN  I_DAD2	VARCHAR(500)	--	도로명 주소2
	, IN  I_MMO		VARCHAR(4000)	--	메모
	, IN  I_MRV		VARCHAR(4000)	--	메일수신여부
	, IN  I_SYN	VARCHAR(4000)	--	SMS 사용여부
)
BEGIN
	-- 병원(회원)  - 등록
	DECLARE V_CNT DECIMAL(5,0);	-- 사용자 아이디

	SET O_MSGCOD	=	'300';
	SET O_ERRCOD	=	'';
	SET V_CNT	=	0;

	--저장 Flg
	IF I_SAVEFLG	=	'I' THEN
	
		SELECT COUNT(*) -- 사용자 아이디 체크
		INTO V_CNT
		FROM MCLISDLIB.MCPASW@T
		WHERE PASCOR	=	I_LOGCOR
		AND PASLID	=	trim(UPPER( I_LOGLID));
		
		IF V_CNT	=	0 THEN
		
			INSERT INTO MCLISDLIB.MCPASW@T(
				PASCOR
				,PASLID
				,PASASW
			)
			VALUES(
				  I_LOGCOR	-- COR
				, trim(UPPER( I_LOGLID))	-- LOGIN ID
				, I_PASASW_SHA	-- 비밀번호
			);
	
			SELECT COUNT(*) -- 사용자 아이디 체크
			INTO V_CNT
			FROM MCLISDLIB.MCLOGI@
			WHERE LOGCOR	=	I_LOGCOR
			AND LOGLID	=  trim(UPPER(I_LOGLID));
			
			IF V_CNT	=	0 THEN
				INSERT INTO MCLISDLIB.MCLOGI@
				(	LOGCOR		--	COR
					,LOGLID		--	LOGIN ID
					,LOGHOS		--	병원코드
					,LOGWNO		--	대표자명
					,LOGGNO		--	면허번호
					,LOGJN1		--	주민번호1
					,LOGJN2		--	주민번호2
					,LOGSNO		--	사업자NO
					,LOGGIC		--	요양기관코드
					,LOGTEL		--	전화번호
					,LOGPO1		--	우편번호1
					,LOGPO2		--	우편번호2
					,LOGADR		--	세부주소
					,LOGPPN		--	핸드폰번호
					,LOGEML		--	멜주소
					,LOGFL1		--	우편번호SEQ
					,LOGCUR		--	등록자
					,LOGCDT		--	등록일자
					,LOGCTM		--	등록시간
				)
				VALUES(
					  I_LOGCOR	-- COR
					, trim(UPPER( I_LOGLID))	-- LOGIN ID
					, I_LOGHOS	-- 병원코드
					, I_WNO	-- 대표자명
					, I_LOGGNO	-- 면허번호
					, ''		-- 주민번호1
					, ''		-- 주민번호2
					, I_SNO	-- 사업자NO
					, I_GIC	--	요양기관코드
					, I_TEL	--	전화번호
					, ''	--	우편번호1
					,  ''	--	우편번호2
					, ''	--	세부주소
					, ''		--	핸드폰번호
					, I_LOGEML	--	이메일주소
					, ''	--	우편번호SEQ
					, I_UID	-- 등록자
					,  TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')	-- 등록일자
					,  TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss') 	-- 등록시간
				);
			
				SELECT COUNT(*) -- 사용자 아이디 체크
				INTO V_CNT
				FROM WEBDBLIB.MWS005M@
				WHERE S005COR	=	I_LOGCOR
				AND S005UID	=  trim(UPPER(I_LOGLID));
				
				
				IF V_CNT	=	0 THEN
				
					INSERT INTO WEBDBLIB.MWS005M@(
						S005COR		--	회사코드
						, S005UID		--	사용자 ID
						, S005AGR		--	권한그룹
						, S005CDT		--	최근 접속 일
						, S005CTM		--	최근 접속 시간
						, S005PCDT		--	비밀번호 변경 일자
						, S005IYN		--	비밀번호 초기화 여부
						, S005FCT		--	로그인 실패 횟수
						, S005DRS		-- 원장출신학교
						, S005HPH		-- 병원홈페이지
						, S005ITM		-- 전문진료과목
						, S005POS		-- 우편번호
						, S005JAD1		-- 지번 주소1
						, S005JAD2		-- 지번 주소2
						, S005DAD1		-- 도로명 주소1
						, S005DAD2		-- 도로명 주소2
						, S005MMO		-- 메모
						, S005MRV		-- 메일수신여부
						, S005SYN		-- SMS사용여부
					)
					VALUES(
						I_LOGCOR		--	회사코드
						, trim(UPPER( I_LOGLID))		--	사용자 ID
						, I_AUTH		--	권한그룹
						, 0			--	최근 접속 일
						, 0			--	최근 접속 시간
						, 0			--	비밀번호 변경 일자
						, 'N'			--	비밀번호 초기화 여부
						, 0			--	로그인 실패 횟수
						, I_DRS		-- 원장출신학교
						, I_HPH		-- 병원홈페이지
						, I_ITM		-- 전문진료과목
						, I_POS		-- 우편번호
						, I_JAD1	-- 지번 주소1
						, I_DAD2	-- 지번 주소2
						, I_DAD1	-- 도로명 주소1
						, I_DAD2	-- 도로명 주소2
						, I_MMO		-- 메모
						, I_MRV		-- 메일수신여부
						, I_SYN		--  SMS사용여부
					);				
				
					SET O_MSGCOD	=	'221';
				END IF; -- 권한 등록
			END IF;		-- MCLISDLIB.MCLOGI@
		END IF;	-- MCLISDLIB.MCPASW@
	ELSE -- 업데이트	
		SELECT COUNT(*) -- 사용자 아이디 체크
		INTO V_CNT
		FROM MCLISDLIB.MCPASW@T
		WHERE PASCOR	=	I_LOGCOR
		AND PASLID	=  trim(UPPER(I_LOGLID));
		
		IF I_PASASW_SHA  != ''  THEN
			UPDATE MCLISDLIB.MCPASW@T
			SET PASASW	=	I_PASASW_SHA
			WHERE PASCOR	=	I_LOGCOR
			AND PASLID	=  trim(UPPER(I_LOGLID));
		END IF;	--비밀번호 수정
	
		
		SELECT COUNT(*) -- 사용자 아이디 체크
		INTO V_CNT
		FROM MCLISDLIB.MCLOGI@
		WHERE LOGCOR	=	I_LOGCOR
		AND LOGLID	=  trim(UPPER(I_LOGLID));
			
	--MCLISDLIB.MCLOGI@ 수정
		IF V_CNT > 0 THEN
			SET O_MSGCOD	=	'100';
			UPDATE MCLISDLIB.MCLOGI@
			SET
				LOGWNO	=	I_WNO	-- 대표자명
				,LOGGNO	=	I_LOGGNO	-- 면허번호
				,LOGSNO	=	I_SNO	-- 사업자NO
				,LOGGIC	=	I_GIC	--	요양기관코드
				,LOGTEL	=	I_TEL	--	전화번호
				,LOGEML	=	I_LOGEML	--	이메일주소
			WHERE  LOGCOR	=	I_LOGCOR	-- COR
			AND LOGLID		=  trim(UPPER(I_LOGLID))	-- LOGIN ID
			;
		END IF;
	
	
		SELECT COUNT(*) -- 사용자 아이디 체크
		INTO V_CNT
		FROM WEBDBLIB.MWS005M@
		WHERE S005COR	=	I_LOGCOR
		AND S005UID	=  trim(UPPER(I_LOGLID));
		
		--권한 등록, 수정
		IF V_CNT	=	0 THEN
			INSERT INTO WEBDBLIB.MWS005M@(
				S005COR		--	회사코드
				, S005UID		--	사용자 ID
				, S005AGR		--	권한그룹
				, S005CDT		--	최근 접속 일
				, S005CTM		--	최근 접속 시간
				, S005PCDT		--	비밀번호 변경 일자
				, S005IYN		--	비밀번호 초기화 여부
				, S005FCT		--	로그인 실패 횟수
				, S005DRS		-- 원장출신학교
				, S005HPH		-- 병원홈페이지
				, S005ITM		-- 전문진료과목
				, S005POS		-- 우편번호
				, S005JAD1		-- 지번 주소1
				, S005JAD2		-- 지번 주소2
				, S005DAD1		-- 도로명 주소1
				, S005DAD2		-- 도로명 주소2
				, S005MMO		-- 메모
				, S005MRV		-- 메일수신여부
				, S005SYN		-- SMS사용여부
			)
			VALUES(
				I_LOGCOR		--	회사코드
				, trim(UPPER( I_LOGLID))		--	사용자 ID
				, I_AUTH		--	권한그룹
				, 0			--	최근 접속 일
				, 0			--	최근 접속 시간
				, 0			--	비밀번호 변경 일자
				, 'N'		--	비밀번호 초기화 여부
				, 0			--	로그인 실패 횟수
				, I_DRS		-- 원장출신학교
				, I_HPH		-- 병원홈페이지
				, I_ITM		-- 전문진료과목
				, I_POS		-- 우편번호
				, I_JAD1	-- 지번 주소1
				, I_DAD2	-- 지번 주소2
				, I_DAD1	-- 도로명 주소1
				, I_DAD2	-- 도로명 주소2
				, I_MMO		-- 메모
				, I_MRV     -- 메일수신여부
				, I_SYN		--  SMS사용여부
			);
			SET O_MSGCOD	=	'221';
		ELSE 			
			UPDATE WEBDBLIB.MWS005M@
			SET S005AGR	=	I_AUTH
				, S005DRS	 = I_DRS	--원장출신학교               
				, S005HPH	 = I_HPH	 --병원홈페이지      
				, S005ITM	 = I_ITM	--전문진료과목               
				, S005POS	 = I_POS	--우편번호                       
				, S005JAD1	 = I_JAD1	 --지번 주소1          
				, S005JAD2	 = I_DAD2	 --지번 주소2          
				, S005DAD1	 = I_DAD1	 --도로명 주소1       
				, S005DAD2	 = I_DAD2	 --도로명 주소2       
				, S005MMO	 = I_MMO	 --메모
				, S005MRV	 = I_MRV     -- 메일수신여부
				, S005SYN	 = I_SYN		--  SMS사용여부
			WHERE S005COR	 =	I_LOGCOR
			AND S005UID	=  trim(UPPER(I_LOGLID));
			SET O_MSGCOD	=	'221';
		END IF;		
	END IF; -- 저장, 업데이트 종료
END



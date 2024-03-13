DROP PROCEDURE WEBOBJLIB.MWS005C2;

CREATE PROCEDURE WEBOBJLIB.MWS005C2
(
	  IN  I_COR 	VARCHAR(3) 	-- COR
	, IN  I_UID 	VARCHAR(12) -- 사용자 ID
	, IN  I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)	-- 에러 코드
	, IN  I_LOGCOR	VARCHAR(3)	--	COR
	, IN  I_LOGLID	VARCHAR(12) --	LOGIN ID
	, IN  I_AUTH	VARCHAR(20)	--	권한그룹
	, IN  I_SYN		VARCHAR(20)	--	SMS사용여부
)
BEGIN
	-- 병원(회원) 권한 변경
	DECLARE V_CNT DECIMAL(5,0);	-- 사용자 아이디
	DECLARE V_S002SCD VARCHAR(20);
	SET O_MSGCOD = '300';
	SET O_ERRCOD = '';
	SET V_CNT = 0;

	SELECT COUNT(*) -- 코드 확인
	INTO V_CNT
	FROM WEBDBLIB.MWS002M@ 
	WHERE S002COR = I_COR
	AND S002PSCD='AUTH'  
	AND S002CNM  =  I_AUTH;

	IF V_CNT > 0 THEN
		SELECT S002SCD
		INTO V_S002SCD
		FROM WEBDBLIB.MWS002M@ 
		WHERE S002COR = I_COR
		AND S002PSCD='AUTH'  
		AND S002CNM  =  I_AUTH;
	ELSE
		SET V_S002SCD = I_AUTH;
	END IF;
			
	SELECT COUNT(*) -- 사용자 아이디 체크
	INTO V_CNT
	FROM WEBDBLIB.MWS005M@
	WHERE S005COR = I_LOGCOR
	AND S005UID = I_LOGLID;

	IF V_CNT = 0 THEN
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
			, S005SYN		-- 메일수신여부
		)
		VALUES(
			I_LOGCOR		--	회사코드
			, I_LOGLID		--	사용자 ID
			, V_S002SCD		--	권한그룹
			, 0			--	최근 접속 일
			, 0			--	최근 접속 시간
			, 0			--	비밀번호 변경 일자
			, 'N'			--	비밀번호 초기화 여부
			, 0			--	로그인 실패 횟수
			,''	-- 원장출신학교
			,''	-- 병원홈페이지
			,''	-- 전문진료과목
			,''	-- 우편번호
			,''	-- 지번 주소1
			,''	-- 지번 주소2
			,''	-- 도로명 주소1
			,''	-- 도로명 주소2
			,''	-- 메모
			,''	-- 메일수신여부
			,I_SYN	-- 메일수신여부
		);	
		SET O_MSGCOD = '221';
	ELSE
	
		UPDATE WEBDBLIB.MWS005M@
		SET S005AGR = V_S002SCD
		  , S005SYN = I_SYN
		WHERE S005COR = I_LOGCOR
		AND S005UID = I_LOGLID;
		SET O_MSGCOD = '221';
	
	END IF; -- 권한 등록
END

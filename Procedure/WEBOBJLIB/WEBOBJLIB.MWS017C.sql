DROP PROCEDURE WEBOBJLIB.MWS017C;

CREATE PROCEDURE WEBOBJLIB.MWS017C
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_EKD		VARCHAR(20)		--이메일 종류
	, IN I_TMA		VARCHAR(4000)	--수신자 이메일 주소
	, IN I_TNM		VARCHAR(4000)	--수신자 명
	, IN I_CMA		VARCHAR(4000)	--참조 이메일 주소
	, IN I_CNM		VARCHAR(4000)	--참조 명
	, IN I_TIT		VARCHAR(1000)	--제목
	, IN I_CONT		CLOB			--내용
	, IN I_FMA		VARCHAR(1000)	--송신 이메일 주소
	, IN I_FNM		VARCHAR(1000)	--송신자 명
	, IN I_SYN		CHAR(1)			--메일 전송 성공 여부 
	, IN I_FMS		VARCHAR(4000)	-- 실패메세지
)
BEGIN
		-- 메일 로그 저장
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

	INSERT INTO WEBDBLIB.MWS017M@
	(	S017COR	-- 회사코드
		,S017SDT	-- 송신 일자
		,S017SEQ	-- 이메일 순번
		,S017EKD	-- 이메일 종류
		,S017TMA	-- 수신자 이메일 주소
		,S017TNM	-- 수신자 명
		,S017CMA	-- 참조 이메일 주소
		,S017CNM	-- 참조 명
		,S017TIT	-- 제목
		,S017CONT	-- 내용
		,S017FMA	-- 송신 이메일 주소
		,S017FNM	-- 송신자 명
		,S017SYN	-- 메일 전송 성공 여부
		,S017SUR	-- 송신자 ID
		,S017STM	-- 송신 시간
		,S017SIP	-- 송신 IP
		,S017FMS	-- 실패메세지
		)
	VALUES(
		I_COR	
		, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')	-- 등록일자 
		, (NEXTVAL FOR WEBDBLIB.MWS017MSEQ)
		, I_EKD	
		, I_TMA	
		, I_TNM	
		, I_CMA	
		, I_CNM	
		, I_TIT	-- 제목
		, I_CONT	
		, I_FMA	
		, I_FNM	
		, I_SYN	
		, I_UID	
		, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss') 	-- 등록시간 	
		, I_IP
		, I_FMS	-- 실패메세지
	);
END



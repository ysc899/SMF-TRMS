DROP PROCEDURE WEBOBJLIB.MWS018C;


CREATE PROCEDURE WEBOBJLIB.MWS018C
(
	  IN  I_COR 	VARCHAR(3) 	-- COR
	, IN  I_UID 	VARCHAR(12) -- 사용자 ID
	, IN  I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)	-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)	-- 에러 코드
	,IN  I_GCD VARCHAR (5 ) -- 검사코드 
	,IN  I_TIT VARCHAR (200 ) -- 리포트 타이틀 
	,IN  I_RGN VARCHAR (200 ) -- 리포트 검사명 
	,IN  I_SYN CHAR (1 ) -- 단일 출력 여부 
	,IN  I_RFN VARCHAR (1000 ) -- 리포트 파일명 
)
BEGIN
	-- 항목별 결과 양식 관리 등록
	DECLARE V_CNT DECIMAL(5,0);	--  수량 확인
	SET O_MSGCOD	=	'200';
	SET O_ERRCOD	=	'';

   SELECT  COUNT(*)
    INTO  V_CNT
	FROM WEBDBLIB.MWS018M@
   WHERE S018COR = I_COR
   AND S018GCD = I_GCD	-- 검사코드 
   ;
  	IF V_CNT = 0 THEN
		INSERT INTO  WEBDBLIB.MWS018M@(
			S018COR	       --회사코드              
			,S018GCD	       --검사코드              
			,S018TIT	       --리포트 타이틀     
			,S018RGN	       --리포트 검사명     
			,S018SYN	       --단일 출력 여부    
			,S018RFN	      --리포트 파일명     
			,S018CUR	       --등록자 ID            
			,S018CDT	       --등록 일자             
			,S018CTM	       --등록 시간             
			,S018CIP	       --등록자 IP             
			,S018UUR	      --수정자 ID            
			,S018UDT	      --수정 일자             
			,S018UTM	      --수정 시간             
			,S018UIP	      --수정자 IP             
		)
		VALUES(
			 I_COR
			,I_GCD
			,I_TIT
			,I_RGN
			,I_SYN
			,I_RFN
			, I_UID
			, TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')	-- 등록일자
			, TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss') 	-- 등록시간
			, I_IP
			, ''
			, 0
			, 0
			, ''
		);
	ELSE
		UPDATE WEBDBLIB.MWS018M@
		SET  S018TIT	=    I_TIT   		  --리포트 타이틀       
			, S018RGN	=    I_RGN 		  --리포트 검사명       
			, S018SYN	=    I_SYN 		  --단일 출력 여부      
			, S018RFN	=    I_RFN  		  --리포트 파일명       
			, S018UUR	=	I_UID	-- 수정자	
			,  S018UDT	=	TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')	-- 수정일자
			, S018UTM	=	TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss') 	-- 수정시간
			, S018UIP	=	I_IP		--수정IP
		   WHERE S018COR = I_COR	--COR
		   AND S018GCD = I_GCD	--폼
		;
	END IF;
END
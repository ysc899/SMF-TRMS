DROP PROCEDURE WEBOBJLIB.MWR002DC;


CREATE PROCEDURE WEBOBJLIB.MWR002DC
(
     IN  I_COR    VARCHAR(3)    -- COR
   , IN  I_UID    VARCHAR(12) -- 사용자 ID
   , IN  I_IP        VARCHAR(30)   -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)      -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)   -- 에러 코드
   , IN    I_HOS   CHAR(5)   -- 병원코드                        
   , IN    I_UPDT   DECIMAL(8,0)   -- 업로드 일자          
   , IN    I_FSQ      DECIMAL(10,0)   --  파일순번       
)BEGIN
	-- OCS 접수 파일 내용(템플릿)	승인상태 조회
	DECLARE V_CNT DECIMAL(5,0);	--   승인 확인

	SELECT COUNT(*) 
	INTO V_CNT
	FROM WEBDBLIB.MWR002D@
	WHERE R002COR = I_COR
	   AND R002HOS = I_HOS
	   AND  R002UPDT = I_UPDT
	   AND R002FSQ = I_FSQ
	AND R002DAT > 0
	AND R002JNO > 0
	AND R002GCD !=''
   ;

   IF V_CNT >0 THEN
	SET O_MSGCOD = '264';
   ELSE
	SET O_MSGCOD = '200';
   END IF;

END
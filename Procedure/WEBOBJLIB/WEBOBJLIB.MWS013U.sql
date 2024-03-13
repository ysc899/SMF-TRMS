CREATE PROCEDURE WEBOBJLIB.MWS013U (IN I_COR VARCHAR(3)
									  , IN I_UID VARCHAR(12)
									  , IN I_IP  VARCHAR(30)
									  , OUT O_MSGCOD VARCHAR(3)
									  , OUT O_ERRCOD VARCHAR(10)
									  , IN I_MCD  VARCHAR(20)
									  , IN I_CON  CLOB
									  )
BEGIN
	-- 도움말 수정
	UPDATE WEBDBLIB."MWS013M@"
	SET S013CON=I_CON
	   , S013UUR=I_UID
	   , S013UDT=TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
	   , S013UTM=TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')
	   , S013UIP=I_IP
	WHERE S013COR=I_COR 
	AND S013MCD=I_MCD;
	SET O_ERRCOD = '';
	SET O_MSGCOD = 200;
END
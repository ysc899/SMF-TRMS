CREATE PROCEDURE WEBOBJLIB.MWS009D
(	
	   IN  I_COR VARCHAR(3)
	 , IN  I_UID VARCHAR(12)
	 , IN  I_IP  VARCHAR(30)
	 , OUT O_MSGCOD VARCHAR(3)
	 , OUT O_ERRCOD VARCHAR(10)
     , IN  I_S009HOS VARCHAR(5)
)
BEGIN
	--엑셀접수포멧 데이터 삭제
	DELETE WEBDBLIB . "MWS009M@"
	WHERE S009HOS = I_S009HOS ;
SET O_ERRCOD = '';
SET O_MSGCOD = 200 ;
END
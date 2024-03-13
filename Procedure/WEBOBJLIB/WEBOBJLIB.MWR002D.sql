DROP PROCEDURE WEBOBJLIB.MWR002D;

CREATE PROCEDURE WEBOBJLIB.MWR002D
(
     IN  I_COR    VARCHAR(3)    -- COR
   , IN  I_UID    VARCHAR(12) -- 사용자 ID
   , IN  I_IP        VARCHAR(30)   -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)      -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)   -- 에러 코드
   , IN    I_HOS   CHAR(5)   -- 병원코드                        
   , IN    I_UPDT   DECIMAL(8,0)   -- 업로드 일자          
   , IN    I_FSQ    DECIMAL(10,0)   --  파일순번       
   , IN    I_STS    VARCHAR(20)   -- 상태                        
)
BEGIN
    -- OCS 접수 파일 삭제
   UPDATE WEBDBLIB.MWR002M@
   SET R002DYN   =   I_STS  --상태
      , R002DUR   =   I_UID   -- 삭제 사용자   
      , R002DDT   =   TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   -- 삭제일자
      , R002DTM   =   TO_CHAR(CURRENT_TIMESTAMP, 'HH24MISS')    -- 삭제시간
      , R002DIP   =   I_IP      --삭제IP
   WHERE R002COR = I_COR
   AND R002HOS = I_HOS
   AND  R002UPDT = I_UPDT
   AND R002FSQ = I_FSQ
   ;

    -- OCS 접수 파일 내용(템플릿)	삭제
   DELETE  WEBDBLIB.MWR002D@
   WHERE R002COR = I_COR
   AND R002HOS = I_HOS
   AND  R002UPDT = I_UPDT
   AND R002FSQ = I_FSQ
   ;

   SET O_MSGCOD = '223';
   SET O_ERRCOD = '';
END

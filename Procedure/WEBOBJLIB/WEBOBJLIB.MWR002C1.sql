DROP PROCEDURE WEBOBJLIB.MWR002C1;


CREATE PROCEDURE WEBOBJLIB.MWR002C1
(
     IN  I_COR    VARCHAR(3)    -- COR
   , IN  I_UID    VARCHAR(12) -- 사용자 ID
   , IN  I_IP        VARCHAR(30)   -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)   -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)   -- 에러 코드
   , IN  I_HOS      CHAR(5)         --병원코드     
   , IN  I_FPT      VARCHAR(1000)      --파일 경로    
   , IN  I_FNM      VARCHAR(200)      --파일 명        
   , IN  I_FSNM      VARCHAR(200)      --파일 저장 명    
   , OUT  O_UPDT      DECIMAL(8,0)      --업로드 일자
   , OUT  O_FSQ      DECIMAL(10,0)      --파일순번     
)
BEGIN
   -- OCS 접수 파일 정보 
   SET O_MSGCOD   =   '300';
   
   SELECT NEXTVAL FOR WEBDBLIB.MWS017MSEQ  , TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')
   INTO O_FSQ, O_UPDT
   FROM SYSIBM.SYSDUMMY1;

   INSERT INTO  WEBDBLIB.MWR002M@(
      R002COR
      , R002HOS
      , R002UPDT
      , R002FSQ
      , R002FPT
      , R002FNM
      , R002FSNM
      , R002DYN
      , R002CUR
      , R002CDT
      , R002CTM
      , R002CIP
      , R002UUR
      , R002UDT
      , R002UTM
      , R002UIP
      , R002DUR
      , R002DDT
      , R002DTM
      , R002DIP
   )      
   VALUES(
      I_COR
      , I_HOS
      , O_UPDT
      , O_FSQ
      , I_FPT
      , I_FNM
      , I_FSNM
      , 'N'
      , I_UID
      , TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   -- 등록일자
      , TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')    -- 등록시간
      , I_IP
      , ''
      , 0
      , 0
      , ''
      , ''
      , 0
      , 0
      , ''
   );

   SET O_MSGCOD   =   '200';
   SET O_ERRCOD   =   '';

END
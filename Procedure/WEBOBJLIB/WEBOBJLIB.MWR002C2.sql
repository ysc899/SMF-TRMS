DROP PROCEDURE WEBOBJLIB.MWR002C2;


CREATE PROCEDURE WEBOBJLIB.MWR002C2
(
     IN  I_COR    VARCHAR(3)    -- COR
   , IN  I_UID    VARCHAR(12) -- 사용자 ID
   , IN  I_IP        VARCHAR(30)   -- 로그인 IP
   , OUT O_MSGCOD   VARCHAR(3)   -- 메세지 코드
   , OUT O_ERRCOD   VARCHAR(10)   -- 에러 코드
   , IN    I_HOS   CHAR(5)   -- 병원코드                        
   , IN    I_UPDT   DECIMAL(8,0)   -- 업로드 일자                   
   , IN    I_FSQ   DECIMAL(10,0)   --  파일순번                        
   , IN    I_RNO   DECIMAL(10,0)   --  Row 번호   
   , IN    I_001	VARCHAR(100)   	  -- 내원번호                                     
   , IN    I_002	VARCHAR(100)   	  -- 외래구분                                     
   , IN    I_003	VARCHAR(30)   	 -- 의뢰일자                                      
   , IN    I_004	VARCHAR(300)   	  -- 검체번호                                     
   , IN    I_005	VARCHAR(300)   	  -- 처방번호                                     
   , IN    I_006	VARCHAR(300)   	  -- 처방코드                                     
   , IN    I_007	VARCHAR(300)   	  -- 처방명                                       
   , IN    I_008	VARCHAR(300)   	  -- 검체명                                       
   , IN    I_009	VARCHAR(300)   	  -- 일련번호                                     
   , IN    I_010	VARCHAR(300)   	  -- 검체코드                                     
   , IN    I_011	VARCHAR(300)   	  -- 여유필드                                     
   , IN    I_012	VARCHAR(300)   	  -- 차트번호                                     
   , IN    I_013	VARCHAR(300)   	  -- 수신자명                                     
   , IN    I_014	VARCHAR(30)   	 -- 주민번호                                      
   , IN    I_015	VARCHAR(50)   	 -- 나이                                          
   , IN    I_016	VARCHAR(50)   	 -- 성별                                          
   , IN    I_017	VARCHAR(300)   	  -- 과명                                         
   , IN    I_018	VARCHAR(300)   	  -- 병동                                         
   , IN    I_019	VARCHAR(100)   	   -- 참고사랑                                    
   , IN    I_020	VARCHAR(300)   	  -- 검사파트                                     
   , IN    I_021	VARCHAR(300)   	  -- 의뢰기관                                     
   , IN    I_022	VARCHAR(300)   	 -- 처방일자                                      
   , IN    I_023	VARCHAR(30)   	 -- 혈액형                                        
   , IN    I_024	VARCHAR(300)   	  -- 질료의사                                     
   , IN    I_044	VARCHAR(30)   	 -- 생년월일                                      
   , IN    I_045	VARCHAR(30)   	 -- 프로파일                                                 
)
BEGIN
   -- 폼별 결과 양식 관리 등록

   SET O_MSGCOD   =   '200';
   SET O_ERRCOD   =   '';

   INSERT INTO  WEBDBLIB.MWR002D@(
      R002COR
      , R002HOS
      , R002UPDT
      , R002FSQ
      , R002RNO
      , R002001
      , R002002
      , R002003
      , R002004
      , R002005
      , R002006
      , R002007
      , R002008
      , R002009
      , R002010
      , R002011
      , R002012
      , R002013
      , R002014
      , R002015
      , R002016
      , R002017
      , R002018
      , R002019
      , R002020
      , R002021
      , R002022
      , R002023
      , R002024
      , R002044
      , R002045
      , R002DAT
      , R002JNO
      , R002GCD
      , R002CUR
      , R002CDT
      , R002CTM
      , R002CIP
   )
   VALUES(
      I_COR
      , I_HOS
      , I_UPDT
      , I_FSQ
      , I_RNO
      , I_001
      , I_002
      , I_003
      , I_004
      , I_005
      , I_006
      , I_007
      , I_008
      , I_009
      , I_010
      , I_011
      , I_012
      , I_013
      , I_014
      , I_015
      , I_016
      , I_017
      , I_018
      , I_019
      , I_020
      , I_021
      , I_022
      , I_023
      , I_024
      , I_044
      , I_045
      , 0
      , 0
      , ''
      , I_UID
      , TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD')   -- 등록일자
      , TO_CHAR(CURRENT_TIMESTAMP, 'hh24miss')    -- 등록시간
      , I_IP
   );
   SET O_MSGCOD   =   '200';
   SET O_ERRCOD   =   '';
END
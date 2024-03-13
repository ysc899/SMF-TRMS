DROP PROCEDURE WEBOBJLIB.MWR002R2;

CREATE PROCEDURE WEBOBJLIB.MWR002R2
(
     IN I_COR         VARCHAR(3)     -- COR
   , IN I_UID         VARCHAR(12) -- 사용자 ID
   , IN I_IP          VARCHAR(30)    -- 로그인 IP
   , OUT O_MSGCOD    CHAR(3)    -- 메세지 코드
   , OUT O_ERRCOD    CHAR(10)     -- 에러 코드
   , IN    I_HOS   CHAR(5)   -- 병원코드                        
   , IN    I_UPDT   DECIMAL(8,0)   -- 업로드 일자          
   , IN    I_FSQ      DECIMAL(10,0)   --  파일순번       
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN    
    -- OCS 접수 파일 내용(템플릿)	조회 
   DECLARE CUR1  CURSOR WITH RETURN FOR
   SELECT   
          TRIM(R002COR) AS R002COR    	 --  회사코드    
        , TRIM(R002HOS) AS  R002HOS    	 --  병원코드    
        , R002UPDT AS R002UPDT 		 --  업로드 일자
        , R002FSQ AS  R002FSQ     		--   파일순번    
        , R002RNO AS  R002RNO    		--   Row 번호  
        , TRIM(R002001) AS  R002001     	 --  내원번호    
        , TRIM(R002002) AS  R002002     	 --  외래구분    
        , TRIM(R002003) AS  R002003     	 --  의뢰일자    
        , TRIM(R002004) AS  R002004     	 --  검체번호    
        , TRIM(R002005) AS  R002005     	 --  처방번호    
        , TRIM(R002006) AS  R002006     	 --  처방코드    
        , TRIM(R002007) AS  R002007     	 --  처방명        
        , TRIM(R002008) AS  R002008     	 --  검체명        
        , TRIM(R002009) AS  R002009     	 --  일련번호    
        , TRIM(R002010) AS  R002010     	 --  검체코드    
        , TRIM(R002011) AS  R002011     	 --  여유필드    
        , TRIM(R002012) AS  R002012     	 --  차트번호    
        , TRIM(R002013) AS  R002013     	 --  수신자명    
        , TRIM(R002014) AS  R002014     	 --  주민번호    
        , TRIM(R002015) AS  R002015     	 --  나이           
        , TRIM(R002016) AS  R002016     	 --  성별           
        , TRIM(R002017) AS  R002017     	 --  과명           
        , TRIM(R002018) AS  R002018     	 --  병동           
        , TRIM(R002019) AS  R002019     	 --  참고사항    
        , TRIM(R002020) AS  R002020     	 --  검사파트    
        , TRIM(R002021) AS  R002021     	 --  의뢰기관    
        , TRIM(R002022) AS  R002022     	 --  처방일자    
        , TRIM(R002023) AS  R002023     	 --  혈액형        
        , TRIM(R002024) AS  R002024     	 --  진료의사    
        , TRIM(R002044) AS  R002044     	 --  생년월일    
        , TRIM(R002045) AS  R002045     	 --  프로파일    
        , CHAR(R002DAT) AS  R002DAT    	 --  씨젠 접수일자
        , CASE WHEN R002JNO = 0 THEN '' ELSE CHAR(R002JNO)  END AS  R002JNO     	 --  씨젠 접수번호
        , TRIM(R002GCD) AS  R002GCD   	 --  씨젠 검사코드
   FROM WEBDBLIB.MWR002D@
   WHERE R002COR = I_COR
   AND R002HOS = I_HOS
   AND  R002UPDT = I_UPDT
   AND R002FSQ = I_FSQ
   ORDER BY R002COR,R002HOS,R002UPDT,R002FSQ;

    SET O_MSGCOD = '200';
   OPEN CUR1;
END
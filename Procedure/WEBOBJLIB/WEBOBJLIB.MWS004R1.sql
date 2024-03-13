DROP PROCEDURE WEBOBJLIB.MWS004R1;

CREATE PROCEDURE WEBOBJLIB.MWS004R1
(
      IN	 I_COR	VARCHAR(3)-- COR
	, IN	 I_UID	VARCHAR(12) -- 사용자 ID
	, IN	 I_IP	VARCHAR(30) -- 로그인 IP
	, OUT	 O_MSGCOD	VARCHAR(3)-- 메세지 코드
	, OUT	 O_ERRCOD	VARCHAR(10) -- 에러 코드
	, IN	  I_FRDT	 DECIMAL(8,0)--접속일자FROM
	, IN	  I_TODT	 DECIMAL(8,0)--접속일자TO
	, IN	  I_NAM	 CHAR(60)--사용자
	, IN	  I_MNM	 CHAR(100) --메뉴명
	, IN	  I_SFL	 CHAR(1)--이벤트성공여부
	, IN	  I_EFG	 CHAR(20) --이벤트종류
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	-- 메인 퀵셋업, 권한별 퀵셋업 메뉴 목록 호출	
	DECLARE CUR1 CURSOR WITH RETURN FOR 
	SELECT 
		TRIM(S004UID) S004UID
		, TRIM(S004NAM) S004NAM
		, S004EDT || LPAD(S004ETM,6,'0')  DATETIME
		, S004EDT 
		, S004ETM
		, TRIM(S004MNM) S004MNM
		, TRIM(IFNULL(A.S002CNM, ' ')) S004EFG       
		, TRIM(S004URL) S004URL
		, TRIM(REPLACE(S004PAR,'','')) S004PAR
		, TRIM(IFNULL(B.S002CNM, ' ')) S004SFL       
		, TRIM(REPLACE(S004MSG,'','')) S004MSG
		FROM WEBDBLIB.MWS004M@  
		  LEFT JOIN WEBDBLIB.MWS002M@ A                    
		      ON S004COR=A.S002COR AND S004EFG=A.S002SCD                               
			   AND A.S002PSCD='EVT_FLAG'                                   
		  LEFT JOIN WEBDBLIB.MWS002M@ B                              
		      ON S004COR=B.S002COR AND S004SFL=B.S002SCD                               
			   AND B.S002PSCD='SUC_FAL'                                    
		WHERE S004COR =I_COR                                                   
		AND S004EDT BETWEEN I_FRDT AND I_TODT                                
		AND (I_EFG=' ' OR  S004EFG = I_EFG)                                  
		AND (I_NAM =' '    OR S004UID =TRIM(I_NAM) 
		               OR S004NAM LIKE '%'||TRIM(I_NAM)||'%' )                                   
		AND (I_MNM =' '    OR S004MNM LIKE '%'||TRIM(I_MNM)||'%'  
		                    OR S004MNU LIKE '%'||TRIM(I_MNM)||'%' )                                   
		AND (I_SFL=' '    OR S004SFL=I_SFL)                                
	 ORDER BY S004EDT DESC, S004TIM DESC ;
	OPEN CUR1; 
	
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';
	
END
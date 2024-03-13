DROP PROCEDURE WEBOBJLIB.MWS014R4;

CREATE PROCEDURE WEBOBJLIB.MWS014R4
(
	  IN I_COR 		VARCHAR(3) 	-- COR
	, IN I_UID 		VARCHAR(12) -- 사용자 ID
	, IN I_IP	  	VARCHAR(30)	-- 로그인 IP
	, OUT O_MSGCOD	CHAR(3)	-- 메세지 코드
	, OUT O_ERRCOD	CHAR(10)	 -- 에러 코드
	, IN  I_SDV		VARCHAR(20)	  -- 시스템 구분           
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN	
	-- 메인 알림 팝업
   DECLARE CUR1  CURSOR WITH RETURN FOR

	SELECT
		  TRIM(A.S014COR) AS S014COR                           --회사코드  
		, TRIM(A.S014SEQ) AS S014SEQ                           -- 문서순번 
		, TRIM(A.S014WDT) AS S014WDT               --작성일자
		, TRIM(A.S014CNM) AS S014CNM               -- 작성자
		, TRIM(A.S014DNO) AS S014DNO                 --문서번호 
		, TRIM(A.S014DCL) AS S014DCL                           -- 문서종류 
		, ( SELECT TRIM(S002CNM)
		      FROM WEBDBLIB.MWS002M@
		      WHERE  S002COR = A.S014COR
		      AND S002SCD = A.S014DCL
		      AND S002PSCD = 'NOTICE_KIND'
		) S002DCL                  -- 문서종류
		, A.S014SDV              --시스템 구분                                                                                     
		, ( SELECT TRIM(S002CNM)
		      FROM WEBDBLIB.MWS002M@
		      WHERE  S002COR = A.S014COR
		      AND S002SCD = A.S014SDV
		      AND S002PSCD = 'SYSTEM_DIV'
		) S002SDV                --시스템 구분 
		, A.S014PYN              --메인 팝업 여부                                                                                       
		, ( SELECT TRIM(S002CNM)
		      FROM WEBDBLIB.MWS002M@
		      WHERE  S002COR = A.S014COR
		      AND  S002SCD = A.S014PYN
		      AND S002PSCD = 'MAIN_POP_YN'
		) S002PYN                 --메인 팝업 여부                                                                                    
		, ( SELECT COUNT ( S014RUR )  S014RYN 
		            FROM WEBDBLIB.MWS014R@  B
		                  WHERE B.S014COR = A.S014COR
		                  AND B.S014SEQ = A.S014SEQ
		                  AND B.S014RUR = I_UID
		) AS S014RYN
		,TRIM( A.S014PFR)       AS   S014PFR            --메인 팝업 시작일
		,TRIM( A.S014PTO)       AS   S014PTO            --메인 팝업 종료일
		,TRIM( A.S014LCO)       AS   S014LCO             -- 메인 팝업 좌측 좌표                                                                              
		,TRIM( A.S014TCO) AS   S014TCO        --메인 팝업 상단 좌표                                                                                    
		,TRIM( A.S014WID) AS   S014WID         --메인 팝업 가로 크기
		,TRIM( A.S014HIT)       AS   S014HIT            --메인 팝업 세로 크기
		,TRIM( A.S014TIT)       AS   S014TIT             -- 제목 
		,TRIM( A.S014CON) AS   S014CON        -- 내용
		,TRIM( A.S014CUR) AS   S014CUR                                                                              
		,TRIM( A.S014DYN) AS   S014DYN        -- 삭제여부  
		,TRIM( B.S014FPT )      AS          S014FPT 
		,TRIM( B.S014FNM  ) AS          S014FNM 
		,TRIM( B.S014FSNM) AS         S014FSNM  
	FROM WEBDBLIB.MWS014M@ A
	LEFT JOIN  WEBDBLIB . MWS014D@ B ON A.S014COR =B.S014COR  AND A.S014SEQ = B.S014SEQ  
   WHERE A.S014COR = I_COR
   AND  A.S014DYN = 'N'	--삭제여부
   AND  A.S014PYN = 'Y'	--메인 팝업 생성여부
   AND TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') 
	BETWEEN	 A.S014PFR	  -- 메인 팝업 시작일
		AND  A.S014PTO	  -- 메인 팝업 종료일    
		AND  A.S014SDV IN('ALL','', I_SDV)
   ORDER BY  A.S014WDT, A.S014SEQ ;

	SET O_MSGCOD = '200';
   OPEN CUR1;
END


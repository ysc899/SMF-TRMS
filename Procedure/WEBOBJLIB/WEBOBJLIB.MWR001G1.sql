CREATE OR REPLACE PROCEDURE WEBOBJLIB.MWR001G1 (IN I_COR VARCHAR(3)
									  , IN I_UID VARCHAR(12)
									  , IN I_IP  VARCHAR(30)
									  , OUT O_MSGCOD VARCHAR(3)
									  , OUT O_ERRCOD VARCHAR(10)
									  , IN I_HOS  VARCHAR(5)
									  )
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	--이전 추가 의뢰 목록 리스트 조회
	DECLARE C1 CURSOR WITH RETURN FOR 
		SELECT 0 CHK, B.R001GCD, C.F010SNM, C.F010STS   
		FROM WEBDBLIB."MWR001M@" A INNER JOIN WEBDBLIB."MWR001D@" B
		ON A.R001JNO = B.R001JNO
		AND A.R001DAT = B.R001DAT
		LEFT OUTER JOIN mclisdlib.mc010m@ C
		ON B.R001GCD = C.F010GCD
		WHERE A.R001HOS = I_HOS
		GROUP BY B.R001GCD, C.F010SNM, C.F010STS
		ORDER BY 2;
	SET O_ERRCOD = '';
	SET O_MSGCOD = 200;
	OPEN C1;
END





                                           
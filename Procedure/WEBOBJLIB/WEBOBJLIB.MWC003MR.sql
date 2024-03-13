DROP PROCEDURE WEBOBJLIB.MWC003MR;

CREATE PROCEDURE WEBOBJLIB.MWC003MR
(
	  IN I_COR 		VARCHAR(3) 		-- COR
	, IN I_UID 		VARCHAR(12) 	-- 사용자 ID
	, IN I_IP		VARCHAR(30)		-- 로그인 IP
	, OUT O_MSGCOD	VARCHAR(3)		-- 메세지 코드
	, OUT O_ERRCOD	VARCHAR(10)		-- 에러 코드
	, IN I_HOS		VARCHAR(5)		-- 병원 코드				
	, IN I_GCD		VARCHAR(500)	-- 검사 항목 코드
	, IN I_MSG		VARCHAR(4000)	-- 메시지	
)
LANGUAGE SQL
DYNAMIC RESULT SETS 1
BEGIN
	-- SMS 연동관리, 메시지 LIST 호출
	DECLARE CUR1  CURSOR WITH RETURN FOR
	SELECT
		 TRIM(C003COR) AS C003COR		-- 회사코드
		,TRIM(C003HOS) AS C003HOS		-- 병원코드
		,TRIM(C003SEQ) AS C003SEQ		-- 메시지순번
		,TRIM(C003GCD) AS C003GCD		-- 검사항목 코드
		,(SELECT TRIM(F010FKN)
			FROM MCLISDLIB.MC010M@
			WHERE F010COR = C003COR 
			AND F010GCD = C003GCD) AS F010FKN	-- 검사 항목 이름
--		,TRIM(F010FKN) AS F010FKN		-- 검사 항목 이름
		,TRIM(C003MSG) AS C003MSG		-- 메시지
		,TRIM(C003CUR) AS C003CUR		-- 등록자 ID
		,TRIM(C003CDT) AS C003CDT		-- 등록 일자
		,TRIM(C003CTM) AS C003CTM		-- 등록 시간
		,TRIM(C003CIP) AS C003CIP		-- 등록자 IP
		,TRIM(C003UUR) AS C003UUR		-- 수정자 ID
		,TRIM(C003UDT) AS C003UDT		-- 수정 일자
		,TRIM(C003UTM) AS C003UTM		-- 수정 시간
		,TRIM(C003UIP) AS C003UIP		-- 수정자 IP
	FROM
		WEBDBLIB.MWC003M@ MWC003M
--	LEFT JOIN MCLISDLIB.MC010M@ MC010M ON C003GCD = F010GCD
	WHERE C003COR = I_COR
	  AND C003HOS = I_HOS
	  AND C003MSG LIKE I_MSG || '%'
 	  AND ( I_GCD = '' OR C003GCD IN ( SELECT SUBSTR(NAME, 1, INSTR(NAME, '|') - 1) DWARF
          FROM (
             SELECT N,  SUBSTR(VAL, 1 + INSTR(VAL, '|', 1, N)) NAME
               FROM (  
                     SELECT ROW_NUMBER() OVER() AS N, LIST.VAL
                     FROM  (SELECT I_GCD VAL
                            FROM "SYSIBM".SYSDUMMY1) LIST      
                     CONNECT BY LEVEL < 
                       ( LENGTH(LIST.VAL) 
                             - LENGTH(REPLACE(LIST.VAL, '|', ''))
                          )
                    )AS bb 
               ) cc ) 
     )
    ORDER BY C003CDT DESC, C003CTM DESC;
	
	OPEN CUR1;
	
	SET O_MSGCOD = '200';
	SET O_ERRCOD = '';

END
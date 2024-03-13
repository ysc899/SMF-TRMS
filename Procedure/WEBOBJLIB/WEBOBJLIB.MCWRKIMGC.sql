--  Generate SQL 
--  Version:                   V7R4M0 190621 
--  Generated on:              23/01/04 19:23:23 
--  Relational Database:       NEODIN 
--  Standards Option:          Db2 for i 
--DROP SPECIFIC PROCEDURE IF EXISTS WEBOBJLIB.MCWRKIMGC ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.MCWRKIMGC ( 
  IN I_COR VARCHAR(3) , 
  IN I_UID VARCHAR(12) , 
  IN I_IP VARCHAR(30) , 
  OUT O_MSGCOD VARCHAR(3) , 
  OUT O_ERRCOD VARCHAR(10) , 
  IN I_HOS VARCHAR(5) , 
  IN I_DAT DECIMAL(8, 0) , 
  IN I_JNO DECIMAL(7, 0) , 
  IN I_GCD CHAR(5) , 
  IN I_ACD CHAR(2) , 
  IN I_BDT DECIMAL(8, 0) , 
  IN I_STS VARCHAR(1) , 
  IN I_FILE_NM VARCHAR(100) ) 
  LANGUAGE SQL 
  SPECIFIC WEBOBJLIB.MCWRKIMGC 
  NOT DETERMINISTIC 
  MODIFIES SQL DATA 
  CALLED ON NULL INPUT 
  SET OPTION  ALWBLK = *ALLREAD , 
  ALWCPYDTA = *OPTIMIZE , 
  COMMIT = *NONE , 
  DECRESULT = (31, 31, 00) , 
  DYNDFTCOL = *NO , 
  DYNUSRPRF = *USER , 
  SRTSEQ = *HEX 
  BEGIN 
-- IDR 유승현 접수번호 길이변경(5->7) I_FILE_NM 적용 
	 -- 변수 선언 
	DECLARE V_CNT DECIMAL ( 5 , 0 ) ; 
	DECLARE V_SEQ DECIMAL ( 10 , 0 ) ; 
	SET O_MSGCOD = '200' ; 
	SET O_ERRCOD = '' ; 
-- 1) 이미지결과 생성 여부 확인 
	SELECT COUNT ( * ) 
	INTO V_CNT 
	FROM MCLISDLIB . MCWRKIMG@ 
	WHERE FIMGCOR = I_COR 
	AND FIMGDAT = I_DAT 
	AND FIMGJNO = I_JNO 
	AND FIMGGCD = I_GCD ; 
-- 2) 이미지결과를 생성했던 이력이 있다면, 이력 마스터에 데이터를 삭제한다. 
--     (최종 이미지결과 생성 이력만 이력 마스터에 남기기 위함.) 
	IF V_CNT > 0 THEN 
		DELETE 
		FROM MCLISDLIB . MCWRKIMG@ 
		WHERE FIMGCOR = I_COR 
		AND FIMGDAT = I_DAT 
		AND FIMGJNO = I_JNO 
		AND FIMGGCD = I_GCD ; 
	END IF ; 
--  3) 이미지결과 생성 결과가 성공인 경우에만 이미지결과 생성 이력 마스터에 insert 한다. 
	IF I_STS = 'Y' THEN 
		INSERT INTO MCLISDLIB . MCWRKIMG@ ( 
			FIMGCOR		 -- COR 
			, FIMGDAT		 --접수일자 
			, FIMGJNO		 --접수번호 
			, FIMGGCD		 --검사코드 
			, FIMGACD		 --부속코드 
			, FIMGSEQ		 --순번 
			, FIMGCDT		 --등록일자 
			, FIMGCTM		 --등록시간 
			, FIMGCUR		 --등록자 
			, FIMGBDT		 --보고일자 
			, FIMGSTS		 --상태 
			, FIMGFNM		 --이미지파일명 
		) 
		VALUES ( 
			I_COR		 -- COR 
			, I_DAT		 --접수일자 
			, I_JNO		 --접수번호 
			, I_GCD		 --검사코드 
			, I_ACD		 --부속코드 
			, 0	 --순번 
			, TO_CHAR ( CURRENT_TIMESTAMP , 'YYYYMMDD' )  -- 등록일자 
			, TO_CHAR ( CURRENT_TIMESTAMP , 'hh24miss' )	 -- 등록시간 
			 --, I_UID  --I_UID 
--, SUBSTRING ( I_UID , 1 , 6 ) 
                             ,I_UID
			, I_BDT		 --보고일자 
			, 'Y'		 --상태 
			, I_FILE_NM 
		) ;	 
-- 4) 이미지결과 생서이력 마스터와 누적 이력 데이터 key 값으로 사용. 
	SELECT NEXTVAL FOR WEBDBLIB . MWWRKLOGSEQ 
	INTO V_SEQ 
	FROM SYSIBM . SYSDUMMY1 ; 
--  5) 이미지결과 생성 결과(성공/실패) 유무와 상관 없이, 이미지결과 생성을 시도한 이력을 남긴다. 
	INSERT INTO WEBDBLIB . MWWRKLOG@ ( 
		FIMGCOR		 -- COR 
		, FIMGHOS		 --병원코드 
		, FIMGDAT		 --접수일자 
		, FIMGJNO		 --접수번호 
		, FIMGGCD		 --검사코드 
		, FIMGACD		 --부속코드 
		, FIMGSEQ		 --순번 
		, FIMGCDT		 --등록일자 
		, FIMGCTM		 --등록시간 
		, FIMGCUR		 --등록자 
		, FIMGBDT		 --보고일자 
		, FIMGSTS		 --상태 
	) 
	VALUES ( 
		I_COR		 -- COR 
		, I_HOS		 --접수일자 
		, I_DAT		 --접수일자 
		, I_JNO		 --접수번호 
		, I_GCD		 --검사코드 
		, I_ACD		 --부속코드 
		, V_SEQ  --순번 
		, TO_CHAR ( CURRENT_TIMESTAMP , 'YYYYMMDD' )	 -- 등록일자 
		, TO_CHAR ( CURRENT_TIMESTAMP , 'hh24miss' )	 -- 등록시간 
		, I_UID		 --등록일자 
		, I_BDT		 --보고일자 
		, I_STS		 -- Y:성공,N:실패 
	) ;		 
	END IF ; 
	SET O_MSGCOD = '200' ;	 
END  ; 

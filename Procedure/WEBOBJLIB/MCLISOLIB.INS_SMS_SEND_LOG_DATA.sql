--  Generate SQL 
--  Version:                   V7R2M0 140418 
--  Generated on:              21/10/07 12:39:44 
--  Relational Database:       NEODIN 
--  Standards Option:          DB2 for i 
DROP SPECIFIC PROCEDURE MCLISOLIB.INS_SMS_SEND_LOG_DATA ; 
SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO018" ; 
CREATE OR REPLACE PROCEDURE MCLISOLIB.INS_SMS_SEND_LOG_DATA ( 
  IN $I_DAT DECIMAL(8, 0) , 
  IN $I_JNO DECIMAL(5, 0) , 
  IN $I_GCD VARCHAR(5) , 
  IN $I_HOS VARCHAR(5) , 
  IN $I_PCD VARCHAR(2) , 
  IN $I_SNN VARCHAR(11) , 
  IN $I_RCN VARCHAR(11) , 
  IN $I_SMS_SEND_DATE DECIMAL(8, 0) , 
  IN $I_SMS_SEND_TIME DECIMAL(6, 0) , 
  IN $I_MESSAGE_STR VARCHAR(4000) , 
  IN $I_REPONSE_GROUPID VARCHAR(100) , 
  IN $I_REPONSE_TOCOUNT INTEGER , 
  IN $I_REPONSE_MESSAGEID VARCHAR(100) , 
  IN $I_REPONSE_TO VARCHAR(100) , 
  IN $I_REPONSE_STATUS VARCHAR(100) , 
  IN $I_REPONSE_ERRORTEXT VARCHAR(100) , 
  IN $I_REPONSE_REF VARCHAR(100) ) 
  LANGUAGE SQL 
  SPECIFIC MCLISOLIB.INS_SMS_SEND_LOG_DATA 
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
	INSERT INTO MCLISDLIB . SMSLOG@ ( FSMSCOR 
									, FSMSDAT 
									, FSMSJNO 
									, FSMSGCD 
									, FSMSHOS 
									, FSMSPCD 
									, FSMSSNN 
									, FSMSRCN 
									, FSMSSND 
									, FSMSSNT 
									, FSMSTXT 
									, FSMSREV1 
									, FSMSREV2 
									, FSMSREV3 
									, FSMSREV4 
									, FSMSREV5 
									, FSMSREV6 
									, FSMSREV7 ) 
	VALUES ( 'NML' 
	, $I_DAT 
	, $I_JNO 
	, $I_GCD 
	, $I_HOS 
	, $I_PCD 
	, $I_SNN 
	, $I_RCN 
	, $I_SMS_SEND_DATE 
	, $I_SMS_SEND_TIME 
	, $I_MESSAGE_STR 
	, $I_REPONSE_GROUPID 
	, $I_REPONSE_TOCOUNT 
	, $I_REPONSE_MESSAGEID 
	, $I_REPONSE_TO 
	, $I_REPONSE_STATUS 
	--, $I_REPONSE_ERRORTEXT 
	, ''	-- 2021.09.07 (통신사에서 SMS를 정상적으로 발송되었는지 유무 code 값을 저장하여 사용하기 위해 빈값으로 처리함. mclis에서 sms 실패사유는 SMSERROR 테이블을 참조한다)
	, $I_REPONSE_REF 
) ; 
END  ; 

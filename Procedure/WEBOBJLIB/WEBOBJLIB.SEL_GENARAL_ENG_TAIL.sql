--DROP SPECIFIC PROCEDURE WEBOBJLIB.SEL_GENARAL_ENG_TAIL ; 
--SET PATH "QSYS","QSYS2","SYSPROC","SYSIBMADM","NEO030" ; 
CREATE OR REPLACE PROCEDURE WEBOBJLIB.SEL_GENARAL_ENG_TAIL ( 
		IN I_COR CHAR(3) , 
		IN I_DAT DECIMAL(8, 0) , 
		IN I_JNO DECIMAL(8, 0) , 
		IN I_GCD CHAR(1000)
	)
	DYNAMIC RESULT SETS 1 
  	LANGUAGE SQL 
	BEGIN 
		DECLARE CUR1 CURSOR WITH RETURN FOR  
			SELECT  trim(F057ENM1) AS T_HNAM
                  ,trim(F057ENM2) AS T_HLIC
                  ,trim(F057ENM3) AS T_LNAM
                  ,trim(F057ENM4) AS T_LLIC
                  ,trim(F057ELM) ||' '|| trim(F057EADR)||' | '|| trim(F057ETEL)||' | '|| trim(F057EFAX) AS T_ADDR
            FROM  mclisdlib.MC057ME@
           	WHERE  f057cor = I_COR  
			and (f057lab, f057hak) IN (SELECT F600CNO, F600HAK
											FROM MCLISDLIB.MC600M@
											WHERE 1=1
											AND F600COR = I_COR 
											AND F600DAT = I_DAT
											AND F600JNO = I_JNO
											AND F600GCD IN (I_GCD))
			AND f057osd = (select max(f057osd) from mclisdlib.MC057ME@
                           	where  f057cor =  I_COR
							and (f057lab, f057hak) IN (SELECT F600CNO, F600HAK
														FROM MCLISDLIB.MC600M@
														WHERE 1=1
														AND F600COR = I_COR 
														AND F600DAT = I_DAT
														AND F600JNO = I_JNO
														AND F600GCD IN (I_GCD))
                            and f057osd <= I_DAT);
		OPEN CUR1 ; 
		--SET RESULT SETS CURSOR CUR ; 
	END  ; 

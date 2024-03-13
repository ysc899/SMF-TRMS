package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오. 작성 날짜: (2005-08-31 오후 3:58:11) 작 성 자: 조남식
 * 
 * 
 * 검사명 가져오기
 * 
 */

//
import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

//
public class DpcOfMCGETGNAM extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfMCGETGNAM() {
		lib = "MCLISOLIB";
		pgm = "MCGETGNAM";
		parm = new DPCParameter(4);
		setCheckField(3);
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters() {
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(java.lang.Object[] parameters) {
		try {
			int index = 0;
			// IN

			// ! 0
			parm.setParm(index++, "NML", DPCParameter.Text, 3); // office code
			parm.setParm(index++, parameters[0].toString(), DPCParameter.Text,
					5); // 검사코드

			// !
			parm.setParm(index++, "", DPCParameter.Text, 50); // 검사명
			parm.setParm(index, " ", DPCParameter.Text, 1); // Err

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(java.util.Vector parameters) {
	}
}

package com.neodin.files;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2006-08-31 오후 3:58:11)
 * 작  성  자: 조남식
 *
 *
 * 미생물 컬처센시 시르스
 *
 */
import java.math.BigDecimal;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

//
public class DpcOfMC118RMS1 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfMC118RMS1() {
		lib = "MCLISOLIB";
		pgm = "MC118RMS1";
		parm = new DPCParameter(11);
		setCheckField(9);
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
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 3); // 회사코드
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 8); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 5); // 접수번호

			// OUT
			parm.setParm(index++, "", DPCParameter.Text, 250); // Culture Code
			parm.setParm(index++, "", DPCParameter.Text, 2050); // Culture Name
			parm.setParm(index++, "", DPCParameter.Text, 400); // Culture Date
			//
			parm.setParm(index++, "", DPCParameter.Text, 250); // Sensi Code
			parm.setParm(index++, "", DPCParameter.Text, 2050); // Sensi Name
			parm.setParm(index++, "", DPCParameter.Text, 400); // Sensi Date
			//	
			parm.setParm(index++, 0, DPCParameter.Pack, 2.0); // Count
			parm.setParm(index, "", DPCParameter.Text, 1); // Error
		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters(java.util.Vector parameters) {
	}
}

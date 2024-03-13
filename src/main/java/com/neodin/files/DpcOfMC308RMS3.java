package com.neodin.files;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: Triple
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC308RMS3 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC308RMS3() {
		lib = "MCLISOLIB";
		pgm = "MC308RMS3";
		parm = new DPCParameter(6);
		setCheckField(5);
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters() {
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters(Object[] parameters) {
		try {
			int index = 0;

			// ! IN
			// 0
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 3); // 회사코드
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 8.0); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 5.0); // 접수번호

			// OUT

			parm.setParm(index++, "", DPCParameter.Text, 1220); // 염색체 이상 검진결과
			parm.setParm(index++, "", DPCParameter.Text, 1220); // 신경관 결손 검진결과

			// !5
			parm.setParm(index, "", DPCParameter.Text, 1); // Error

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters(Vector parameters) {
	}
}

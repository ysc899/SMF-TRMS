package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2006-08-31 오후 3:58:11)
 * 작  성  자: 조남식
 *
 *
 * 엑상세포
 *
 */

import java.math.BigDecimal;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

//
public class DpcOfMC106RMS2 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfMC106RMS2() {
		lib = "MCLISOLIB";
		pgm = "MC106RMS2";
		parm = new DPCParameter(9);
		setCheckField(8);
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

			// !
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 700); // 접수번호
			parm.setParm(index++, new BigDecimal(1), DPCParameter.Pack, 3); // 접수번호

			// OUT
			parm.setParm(index++, "", DPCParameter.Text, 40); // 결과
			parm.setParm(index++, "", DPCParameter.Text, 24400); // 결과
			parm.setParm(index++, "", DPCParameter.Text, 1200); // 결과
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // Data Count

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters(java.util.Vector parameters) {
	}
}

package com.neodin.files;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2006-08-31 오후 3:58:11)
 * 작  성  자: 조남식
 *
 *
 * 미생물 컬처 결과
 *
 */

import java.math.BigDecimal;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public class DpcOfMC118RM extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfMC118RM() {
		lib = "MCLISOLIB";
		pgm = "MC118RM";
		parm = new DPCParameter(13);
		setCheckField(10);
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

			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 3); // 검사갯수 1
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 3); // 검사갯수 2
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 700); // 검사코드 1
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 700); // 검사코드 2
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 800); // 보고일자 1
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 800); // 보고일자 2
			// OUT
			parm.setParm(index++, "", DPCParameter.Text, 40); // HEAD
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // Count
			parm.setParm(index++, "", DPCParameter.Text, 30400); // 결과

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

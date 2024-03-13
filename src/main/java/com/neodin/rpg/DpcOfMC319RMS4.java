package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-31 오후 3:58:11)
 * 작  성  자: 조남식
 *
 *
 * STD 검사방법 불러오기
 *
 */

import java.math.BigDecimal;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

//
public class DpcOfMC319RMS4 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfMC319RMS4() {
		lib = "MCLISOLIB";
		pgm = "MC319RMS4";
		parm = new DPCParameter(8);
		setCheckField(7);
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
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 3); // 회사코드
			parm.setParm(index, new BigDecimal(parameters[index++].toString()),
					DPCParameter.Pack, 8); // 보고일자 (from)
			parm.setParm(index, new BigDecimal(parameters[index++].toString()),
					DPCParameter.Pack, 5); // 보고일자 (to)
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 3); // 작업번호
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 5); // 작업번호
			// OUT
			parm.setParm(index++, "", DPCParameter.Text, 3030); // 출력 프로그램
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // 출력개수
			parm.setParm(index, "", DPCParameter.Text, 1); // Err

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(java.util.Vector parameters) {
	}
}

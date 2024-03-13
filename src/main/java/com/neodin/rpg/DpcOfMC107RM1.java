package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: 검사결과 리마크 가져오기
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC107RM1 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC107RM1() {
		lib = "MCLISOLIB";
		pgm = "MC107RM1";
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
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 4); // 검사부서
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 3); // 작업코드

			// ! OUT
			// 5
			parm.setParm(index++, "", DPCParameter.Text, 6100); // 리마크내용
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // 건수
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

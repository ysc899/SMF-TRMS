package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석:  참고치를 불러온다.
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC176RM extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC176RM() {
		lib = "MCLISOLIB";
		pgm = "MC176RMS6";
		parm = new DPCParameter(12);
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

	public void setParameters(Object[] parameters) {
		try {
			int index = 0;

			// ! IN
			// 0
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 8.0); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 5.0); // 접수번호
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 5); // 검사코드
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 2); // 부속코드

			// OUT
			parm.setParm(index++, 0, DPCParameter.Pack, 12.4); // 최저값

			// 5
			parm.setParm(index++, 0, DPCParameter.Pack, 12.4); // 최고값
			parm.setParm(index++, 0, DPCParameter.Pack, 12.4); // PANIC +
			parm.setParm(index++, 0, DPCParameter.Pack, 12.4); // PANIC -
			parm.setParm(index++, "", DPCParameter.Text, 30); // 단위
			parm.setParm(index++, "", DPCParameter.Text, 1); // ERROR 여부

			// 10
			parm.setParm(index++, "NML", DPCParameter.Text, 3); // 회사코드
			parm.setParm(index, "", DPCParameter.Text, 3000); // 회사코드  

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(Vector parameters) {
	}
}

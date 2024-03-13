package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: 검체를 가져온다
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC181RM2 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC181RM2() {
		lib = "MCLISOLIB";
		pgm = "MC181RM2";
		parm = new DPCParameter(10);
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
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 3); // 회사코드
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 8.0); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 5.0); // 접수번호
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 5); // 검사코드

			// ! OUT
			parm.setParm(index++, "", DPCParameter.Text, 30); // 검체코드

			// 5
			parm.setParm(index++, "", DPCParameter.Text, 100); // 검체량
			parm.setParm(index++, 0, DPCParameter.Pack, 8.0); // 검체 체취일
			parm.setParm(index++, "", DPCParameter.Text, 2300); // 검체명
			parm.setParm(index++, 0, DPCParameter.Pack, 2.0); // 읽은 개수
			parm.setParm(index, "", DPCParameter.Text, 1); // err

		} catch (Exception e) {

		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(Vector parameters) {
	}
}

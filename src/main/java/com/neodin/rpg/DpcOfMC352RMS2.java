package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-31 오후 3:58:11)
 * 작  성  자: 조남식
 *
 *
 * EP(결과가져오기)  
 *
 *숫자 1, 숫자 2, 숫자 3,  문자 
 */

import java.math.BigDecimal;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

//
public class DpcOfMC352RMS2 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfMC352RMS2() {
		lib = "MCLISOLIB";
		pgm = "MC352RMS2";
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

			// 0
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 3); // 회사코드
			parm.setParm(index, new BigDecimal(parameters[index++].toString()),
					DPCParameter.Pack, 8.0); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()),
					DPCParameter.Pack, 5.0); // 접수번호
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 5); // 검사코드
			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 2); // 부속코드

			// 5

			parm.setParm(index, parameters[index++].toString(),
					DPCParameter.Text, 1); // FX 타입
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // 판독일자
			parm.setParm(index, "", DPCParameter.Text, 1); // 에러

		} catch (Exception e) {

		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(java.util.Vector parameters) {
	}
}

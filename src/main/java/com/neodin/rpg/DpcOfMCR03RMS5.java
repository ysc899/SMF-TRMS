package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: 결과이미지 패스를 가지고 온다
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMCR03RMS5 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMCR03RMS5() {
		lib = "MCLISOLIB";
		pgm = "MCR03RMS5";
		parm = new DPCParameter(7);
		setCheckField(6);
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
					DPCParameter.Text, 5); // 검사코드
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 2); // 부속코드

			// ! OUT
			parm.setParm(index++, "", DPCParameter.Text, 6050); // 이미지 주소 - 20180327 : MCR03RMS5, RPG수정

			// 5
			parm.setParm(index, 0, DPCParameter.Pack, 2.0); // 건수

		} catch (Exception e) {
			/*
			 * parm.setParm(3, code.substring(0, 5), DPCParameter.Text, 5); //
			 * 검사코드 parm.setParm(4, code.substring(5, 7), DPCParameter.Text, 2); //
			 * 부속코드
			 */
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(Vector parameters) {
	}
}

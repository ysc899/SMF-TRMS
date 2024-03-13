package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: 기생충 검사종목 집계
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC306RMS1 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC306RMS1() {
		lib = "MCLISOLIB";
		pgm = "MC306RMS1";
		parm = new DPCParameter(16);
		setCheckField(15);
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
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 1); // 접수일자 ,보고일자 구분
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 8.0); // 보고일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 8.0); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()
					.trim()), DPCParameter.Pack, 5.0); // 접수번호

			// 5
			parm.setParm(index, parameters[index++].toString().trim(),
					DPCParameter.Text, 4); // 결과지 양식

			// OUT

			parm.setParm(index++, "", DPCParameter.Text, 25); // 검사코드
			parm.setParm(index++, "", DPCParameter.Text, 10); // 부속코드
			parm.setParm(index++, "", DPCParameter.Text, 255); // 검사명(한글)
			parm.setParm(index++, "", DPCParameter.Text, 255); // 검사명(영문)

			// 10
			parm.setParm(index++, "", DPCParameter.Text, 255); // 결과(문자)
			parm.setParm(index++, "", DPCParameter.Text, 155); // 참고치내용
			parm.setParm(index++, "", DPCParameter.Text, 355); // 첨부내용(FHx)
			parm.setParm(index++, "", DPCParameter.Text, 355); // 첨부내용(Habit)
			parm.setParm(index++, "", DPCParameter.Text, 255); // 결과(숫자)

			// 15
			parm.setParm(index, 0, DPCParameter.Pack, 3.0);
		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(Vector parameters) {
	}
}

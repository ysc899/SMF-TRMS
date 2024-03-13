package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-31 오후 3:58:11)
 * 작  성  자: 조남식
 *
 *
 * 결과지 선택출력
 *
 */

import java.math.BigDecimal;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

//
public class DpcOfTC310RM extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-04-20 오전 11:30:58)
	 */
	public DpcOfTC310RM() {
		lib = "MCLISOLIB";
		pgm = "TC310RM";
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
					DPCParameter.Pack, 8.0); // 접수일자
			parm.setParm(index, new BigDecimal(parameters[index++].toString()),
					DPCParameter.Pack, 5.0); // 접수번호

			// OUT
			// 5
			parm.setParm(index++, "", DPCParameter.Text, 6); // 헤더
			parm.setParm(index++, "", DPCParameter.Text, 6); // 일련번호
			parm.setParm(index++, "", DPCParameter.Text, 6); // 보험코드
			parm.setParm(index++, "", DPCParameter.Text, 6); // 검사명

			// 15
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

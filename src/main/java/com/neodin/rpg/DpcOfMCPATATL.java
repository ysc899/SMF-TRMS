package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: 환자정보 중 추가 세부항목을 불러온다
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMCPATATL extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMCPATATL() {
		lib = "MCLISOLIB";
		pgm = "MCPATATL";
		parm = new DPCParameter(15);
		setCheckField(14);
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

			// OUT
			parm.setParm(index++, 0, DPCParameter.Pack, 8.0); // 최종월경일자
			parm.setParm(index++, "", DPCParameter.Text, 2); // LMP 주수

			// 5
			parm.setParm(index++, "", DPCParameter.Text, 2); // LMP 일수
			parm.setParm(index++, "", DPCParameter.Text, 2); // SCAN 주수
			parm.setParm(index++, "", DPCParameter.Text, 2); // SCAN 일
			parm.setParm(index++, "", DPCParameter.Text, 1); // PDN
			parm.setParm(index++, "", DPCParameter.Text, 1); // PND

			// 10
			parm.setParm(index++, "", DPCParameter.Text, 1); // IDM
			parm.setParm(index++, "", DPCParameter.Text, 1); // HAD
			parm.setParm(index++, "", DPCParameter.Text, 1); // UTR
			parm.setParm(index++, "", DPCParameter.Text, 1); // CRL
			parm.setParm(index, "", DPCParameter.Text, 1); // ERR

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(Vector parameters) {
	}
}

package com.neodin.files;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: Quad
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC308RMS4 extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC308RMS4() {
		lib = "MCLISOLIB";
		pgm = "MC308RMS4";
		parm = new DPCParameter(35);
		setCheckField(34);
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

			// 환자정보-------------------
			parm.setParm(index++, "", DPCParameter.Text, 8); // 생년월일
			parm.setParm(index++, "", DPCParameter.Text, 3); // 나이

			// ! 5
			parm.setParm(index++, "", DPCParameter.Text, 8); // 최종월경일
			parm.setParm(index++, "", DPCParameter.Text, 4); // LMP주수
			parm.setParm(index++, "", DPCParameter.Text, 4); // SCAN 주수
			parm.setParm(index++, "", DPCParameter.Text, 6); // 체중
			parm.setParm(index++, "", DPCParameter.Text, 12); // Previos DOWN

			// !10
			parm.setParm(index++, "", DPCParameter.Text, 12); // Previous NTD
			parm.setParm(index++, "", DPCParameter.Text, 12); // Insulin 의존성
			parm.setParm(index++, "", DPCParameter.Text, 4); // Crown Rump
																// Length
			parm.setParm(index++, "", DPCParameter.Text, 1); // 태아수

			// 결과---------------------
			parm.setParm(index++, "", DPCParameter.Text, 15); // AFP

			// !15
			parm.setParm(index++, "", DPCParameter.Text, 15); // AFP _ MOM
			parm.setParm(index++, "", DPCParameter.Text, 15); // PAPP_A
			parm.setParm(index++, "", DPCParameter.Text, 15); // PAPP_A_MOM
			parm.setParm(index++, "", DPCParameter.Text, 15); // HCG
			parm.setParm(index++, "", DPCParameter.Text, 15); // HCG_MOM

			// !20
			parm.setParm(index++, "", DPCParameter.Text, 15); // FreeB-HCG
			parm.setParm(index++, "", DPCParameter.Text, 15); // FreeB-HCG_MOM
			parm.setParm(index++, "", DPCParameter.Text, 15); // uE3
			parm.setParm(index++, "", DPCParameter.Text, 15); // uE3_MOM
			parm.setParm(index++, "", DPCParameter.Text, 15); // NT

			// !25
			parm.setParm(index++, "", DPCParameter.Text, 15); // NT_MOM

			// 판정----------------------
			parm.setParm(index++, "", DPCParameter.Text, 12); // Maternal age
			parm.setParm(index++, "", DPCParameter.Text, 4); // Maternal age
			parm.setParm(index++, "", DPCParameter.Text, 12); // Down syndrome
			parm.setParm(index++, "", DPCParameter.Text, 6); // Down syndrome

			// !30
			parm.setParm(index++, "", DPCParameter.Text, 12); // Edward
																// syndrome
			parm.setParm(index++, "", DPCParameter.Text, 6); // Edward
																// syndrome
			parm.setParm(index++, "", DPCParameter.Text, 12); // Open NTD
			parm.setParm(index++, "", DPCParameter.Text, 6); // Open NTD
			parm.setParm(index, "", DPCParameter.Text, 31); // Error

		} catch (Exception e) {

		}
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters(Vector parameters) {
	}
}

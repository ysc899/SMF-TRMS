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
public final class DpcOfMCPATBAS extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMCPATBAS() {
		lib = "MCLISOLIB";
		pgm = "MCPATBAS";
		parm = new DPCParameter(20);
		setCheckField(19);
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
			parm.setParm(index++, "", DPCParameter.Text, 5); // 병원코드
			parm.setParm(index++, "", DPCParameter.Text, 40); // 병원명

			// 5
			parm.setParm(index++, "", DPCParameter.Text, 8); // 요양기관번호
			parm.setParm(index++, "", DPCParameter.Text, 15); // 차트번호
			parm.setParm(index++, "", DPCParameter.Text, 20); // 과명
			parm.setParm(index++, "", DPCParameter.Text, 30); // 병동명
			parm.setParm(index++, "", DPCParameter.Text, 30); // 의사이름

			// 10
			parm.setParm(index++, "", DPCParameter.Text, 30); // 환자명
			parm.setParm(index++, "", DPCParameter.Text, 14); // 주민번호
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // 나이
			parm.setParm(index++, "", DPCParameter.Text, 1); // 성별
			parm.setParm(index++, 0, DPCParameter.Pack, 5.2); // 체중

			// 15
			parm.setParm(index++, "", DPCParameter.Text, 50); // 기타사항
			parm.setParm(index++, "", DPCParameter.Text, 4); // 사무소
			parm.setParm(index++, 0, DPCParameter.Pack, 8.0); // 생년월일
			parm.setParm(index++, "", DPCParameter.Text, 12); // 담당자명
			parm.setParm(index, "", DPCParameter.Text, 1); // 오류

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */

	public void setParameters(Vector parameters) {
	}
}

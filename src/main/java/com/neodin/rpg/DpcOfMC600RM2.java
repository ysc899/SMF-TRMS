package com.neodin.rpg;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2005-08-03 오후 6:45:21)
 * 작  성  자: 조남식
 *
 * 주       석: 환자별 검사결과를 가지고  온다.
 *
 **/
import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

// !
public final class DpcOfMC600RM2 extends AbstractDpc {  
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:30:33)
	 */
	public DpcOfMC600RM2() {
		lib = "MCLISOLIB";
		pgm = "MC600RM2";
		parm = new DPCParameter(26);
		setCheckField(18);
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

			// OUT
			parm.setParm(index++, "", DPCParameter.Text, 200); // 부속코드

			// 5
			parm.setParm(index++, "", DPCParameter.Text, 5100); // 검사명
			parm.setParm(index++, "", DPCParameter.Text, 8100); // 검사결과
			parm.setParm(index++, "", DPCParameter.Text, 100); // 결과종류 'N' : 수치
			// 'C' : 문자 'A'
			// : 수치＋문자
			parm.setParm(index++, "", DPCParameter.Text, 1); // 이미지여부 'Y' :
			// 이미지있슴 'N' :
			// 이미지없슴
			parm.setParm(index++, "", DPCParameter.Text, 100); // H/L

			// 10
			parm.setParm(index++, "", DPCParameter.Text, 100); // PANIC H/L
			parm.setParm(index++, "", DPCParameter.Text, 100); // 제목여부 'Y' : 제목
			// 'N' : 제목아님
			parm.setParm(index++, "", DPCParameter.Text, 300); // 참고치이력번호
			parm.setParm(index++, 0, DPCParameter.Pack, 8.0); // 보고일자
			parm.setParm(index++, "", DPCParameter.Text, 30); // 홍창식

			// 15
			parm.setParm(index++, "", DPCParameter.Text, 12); // 7000
			parm.setParm(index++, "", DPCParameter.Text, 30); // S.H.Lee.M.D.
			parm.setParm(index++, "", DPCParameter.Text, 12); // ?
			parm.setParm(index++, 0, DPCParameter.Pack, 3.0); // 건수
			parm.setParm(index++, "", DPCParameter.Text, 3); // JOB

			// 20
			parm.setParm(index++, "", DPCParameter.Text, 4); // 부서코드
			parm.setParm(index++, "", DPCParameter.Text, 1500); // 숫자결과1
			parm.setParm(index++, "", DPCParameter.Text, 1500); // 숫자결과2
			parm.setParm(index++, "", DPCParameter.Text, 1500); // 숫자결과3
			parm.setParm(index++, "", DPCParameter.Text, 8100); // 문자결과

			// 25
			parm.setParm(index++, "", DPCParameter.Text, 100); // H/L

		} catch (Exception e) {

		}
	}



	@Override
	public void setParameters(Vector parameters) {
		// TODO 자동 생성된 메소드 스텁
		
	}
}

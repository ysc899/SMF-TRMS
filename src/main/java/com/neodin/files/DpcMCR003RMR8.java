package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMCR003RMR8 extends AbstractDpc {

	public DpcMCR003RMR8() {
		lib = "MCLISOLIB";
		pgm = "MCR003RMR8";
		parm = new DPCParameter(35);
		setCheckField(34);
	}

	public void setParameters() {
	}
	
	public void setParameters(Object parameters[]) {
		//[NML, 22023, 0, 0, , 0,
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, parameters[0].toString().trim(), (short) 1, 5D);
			parm.setParm(2, new BigDecimal(parameters[1].toString().trim()),(short) 2, 8D);
			parm.setParm(3, new BigDecimal(parameters[2].toString().trim()),(short) 2, 5D);
			parm.setParm(4, parameters[3].toString().trim(), (short) 1, 7D);
			parm.setParm(5, new BigDecimal(parameters[4].toString().trim()),(short) 2, 2D);
			parm.setParm(6, "", (short) 1, 2000D); // 병원코드
			parm.setParm(7, "", (short) 1, 3200D); // 접수일자
			parm.setParm(8, "", (short) 1, 2000D); // 접수번호
			parm.setParm(9, "", (short) 1, 12400D); // 검체번호
			parm.setParm(10, "", (short) 1, 6400D); // 차트번호
			parm.setParm(11, "", (short) 1, 12400D); // 수진자명
			parm.setParm(12, "", (short) 1, 2400D); // 나이
			parm.setParm(13, "", (short) 1, 400D); // 성별
			parm.setParm(14, "", (short) 1, 2800D); // 검사코드
			parm.setParm(15, "", (short) 1, 20400D); // 검사명
			parm.setParm(16, "", (short) 1, 800D); // 일련번호
			parm.setParm(17, "", (short) 1, 32400D); // 결과
			parm.setParm(18, "", (short) 1, 400D); // 판정
			parm.setParm(19, "", (short) 1, 2800D); // 리마크코드
			parm.setParm(20, "", (short) 1, 3200D); // 보고일자

			// !
			parm.setParm(21, "", (short) 1, 1600D); // 처방일자
			parm.setParm(22, "", (short) 1, 1200D); // 참고치이력
			parm.setParm(23, "", (short) 1, 400D); // 참고치언어
			parm.setParm(24, "", (short) 1, 4000D); // 보험코드
			parm.setParm(25, "", (short) 1, 400D); // 결과형태
			parm.setParm(26, "", (short) 1, 400D); // 외래구분
			parm.setParm(27, "", (short) 1, 2000D); // 결과전송개수
			parm.setParm(28, "", (short) 1, 8000D); // 타병원감서코드

			// !
			parm.setParm(29, "", (short) 1, 8000D); // 요양기관번호

			// !
			parm.setParm(30, "", (short) 1, 3200D); // 내원번호

			// !
			parm.setParm(31, "", (short) 1, 8400D); // 주민번호
			parm.setParm(32, "", (short) 1, 24400D); // 참고치
			parm.setParm(33, "0", (short) 2, 5D); // 개수
			parm.setParm(34, "", (short) 1, 24400D); // 처방번호
			
		} catch (Exception e) {
//			System.out.println("e:"+e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

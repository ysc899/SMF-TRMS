package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public class DpcMC999RM extends AbstractDpc {
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-09-09 오후 1:53:21)
	 */
	public DpcMC999RM() {
		lib = "MCLISOLIB";
		pgm = "MC999RM";
		parm = new DPCParameter(9);
		setCheckField(8);
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
			// in
			int index = 0;
			parm.setParm(index++, "NML", DPCParameter.Text, 3); // 회사코드
			parm.setParm(index++, parameters[0].toString(), DPCParameter.Text,
					4); // KEY 1
			parm.setParm(index++, parameters[1].toString(), DPCParameter.Text,
					4); // KEY 2
			parm.setParm(index++, parameters[2].toString(), DPCParameter.Text,
					5500); // KEY3
			parm.setParm(index++, "", DPCParameter.Text, 6500); // NM 1
			parm.setParm(index++, "", DPCParameter.Text, 11500); // NM 2
			parm.setParm(index++, "", DPCParameter.Text, 21500); // NM 3
			parm.setParm(index++, "".toString(), DPCParameter.Text, 31500); // NM 4
			// out

			parm.setParm(index, 0, DPCParameter.Pack, 3.0); // 에러유무

		} catch (Exception e) {
		}
	}

	/**
	 * setParameters 메소드 주석.
	 */
	public void setParameters(java.util.Vector parameters) {
	}
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMWR003RTR9 extends AbstractDpc {

	public DpcMWR003RTR9() {
		lib = "MCLISOLIB";
		pgm = "MWR003RTR9";
		parm = new DPCParameter(8);
		setCheckField(7);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, parameters[0].toString().trim(), (short) 1, 5D);
			parm.setParm(2, new BigDecimal(parameters[1].toString().trim()),(short) 2, 8D);
			parm.setParm(3, new BigDecimal(parameters[2].toString().trim()),(short) 2, 5D);
			parm.setParm(4, parameters[3].toString().trim(), (short) 1, 7D);
			parm.setParm(5, new BigDecimal(parameters[4].toString().trim()),(short) 2, 2D);
			parm.setParm(6, new BigDecimal("0"), (short) 2, 10D); // 개수
			parm.setParm(7, parameters[5].toString().trim(),(short) 1, 10D);
		} catch (Exception e) {
		}
	}

	public void setParameters(Vector vector) {
	}
}

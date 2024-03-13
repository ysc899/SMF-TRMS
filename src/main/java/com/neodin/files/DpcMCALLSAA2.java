package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMCALLSAA2 extends AbstractDpc {

	public DpcMCALLSAA2() {
		lib = "MCLISOLIB";
		pgm = "MCALLSAA2";
		parm = new DPCParameter(5);
		setCheckField(4);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, parameters[0].toString().trim(), (short) 1, 12D);
			parm.setParm(2, "", (short) 1, 5000D);
			parm.setParm(3, new BigDecimal(0.0D), (short) 2, 4D);
			parm.setParm(4, "", (short) 1, 1.0D);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

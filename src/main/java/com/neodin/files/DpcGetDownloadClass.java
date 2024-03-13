package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcGetDownloadClass extends AbstractDpc {

	public DpcGetDownloadClass() {
		lib = "MCLISOLIB";
		pgm = "MCDWNCLS";
		parm = new DPCParameter(4);
		setCheckField(3);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, parameters[0].toString().trim(), (short) 1, 10D);
			parm.setParm(2, "", (short) 1, 42D);
			parm.setParm(3, "", (short) 1, 1.0D);
		} catch (Exception e) {
//			System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

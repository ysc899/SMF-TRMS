package com.neodin.result;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcOfMC308RM3 extends AbstractDpc {

	public DpcOfMC308RM3() {
		lib = "MCLISOLIB";
		pgm = "MC308RM3";
		parm = new DPCParameter(6);
		setCheckField(5);
	}

	public void setParameters() {
	}

	public void setParameters(Object aobj[]) {
		try {
			int i = 0;
			parm.setParm(i++, aobj[0].toString(), (short) 1, 3D);
			parm.setParm(i++, new BigDecimal(aobj[1].toString().trim()),
					(short) 2, 8D);
			parm.setParm(i++, new BigDecimal(aobj[2].toString().trim()),
					(short) 2, 5D);
			parm.setParm(i++, 0, (short) 2, 8D);
			parm.setParm(i++, 0, (short) 2, 5D);
			parm.setParm(i++, "", (short) 1, 5D);
		} catch (Exception _ex) {
		}
	}

	public void setParameters(Vector vector) {
	}
}

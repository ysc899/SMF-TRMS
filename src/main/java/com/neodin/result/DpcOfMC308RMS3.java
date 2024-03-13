package com.neodin.result;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcOfMC308RMS3 extends AbstractDpc {

	public DpcOfMC308RMS3() {
		lib = "MCLISOLIB";
		pgm = "MC308RMS3";
		parm = new DPCParameter(6);
		setCheckField(5);
	}

	public void setParameters() {
	}

	public void setParameters(Object aobj[]) {
		try {
			int i = 0;
			parm.setParm(i, aobj[i++].toString(), (short) 1, 3D);
			parm.setParm(i, new BigDecimal(aobj[i++].toString().trim()),
					(short) 2, 8D);
			parm.setParm(i, new BigDecimal(aobj[i++].toString().trim()),
					(short) 2, 5D);
			parm.setParm(i++, "", (short) 1, 1220D);
			parm.setParm(i++, "", (short) 1, 1220D);
			parm.setParm(i, "", (short) 1, 1.0D);
		} catch (Exception _ex) {
		}
	}

	public void setParameters(Vector vector) {
	}
}

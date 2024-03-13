package com.neodin.rpg;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcOfMC107RMS2 extends AbstractDpc {

	public DpcOfMC107RMS2() {
		lib = "MCLISOLIB";
		pgm = "MC107RMS2";
		parm = new DPCParameter(9);
		setCheckField(7);
	}

	public void setParameters() {
	}   

	public void setParameters(Object aobj[]) {
		try {
			int i = 0;
			parm.setParm(i, aobj[i++].toString(), (short) 1, 3D);
			parm.setParm(i, new BigDecimal(aobj[i++].toString().trim()),(short) 2, 8D);
			parm.setParm(i, new BigDecimal(aobj[i++].toString().trim()),(short) 2, 5D);
			parm.setParm(i, aobj[i++].toString().trim(), (short) 1, 4D);
			parm.setParm(i, aobj[i++].toString().trim(), (short) 1, 3D);
			parm.setParm(i++, "", (short) 1, 12200D);
			parm.setParm(i++, 0, (short) 2, 3D);
			parm.setParm(i++, "", (short) 1, 1.0D);
			parm.setParm(i, "", (short) 1, 600D);
		} catch (Exception _ex) {
		}
	}

	public void setParameters(Vector vector) {
	}
}

package com.neodin.result;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcOfMC308RMS4 extends AbstractDpc {

	public DpcOfMC308RMS4() {
		lib = "MCLISOLIB";
		pgm = "MC308RMS4";
		parm = new DPCParameter(35);
		setCheckField(34);
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
			parm.setParm(i++, "", (short) 1, 8D);
			parm.setParm(i++, "", (short) 1, 3D);
			parm.setParm(i++, "", (short) 1, 8D);
			parm.setParm(i++, "", (short) 1, 4D);
			parm.setParm(i++, "", (short) 1, 4D);
			parm.setParm(i++, "", (short) 1, 6D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 4D);
			parm.setParm(i++, "", (short) 1, 1.0D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 15D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 4D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 6D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 6D);
			parm.setParm(i++, "", (short) 1, 12D);
			parm.setParm(i++, "", (short) 1, 6D);
			parm.setParm(i, "", (short) 1, 31D);
		} catch (Exception _ex) {
		}
	}

	public void setParameters(Vector vector) {
	}
}

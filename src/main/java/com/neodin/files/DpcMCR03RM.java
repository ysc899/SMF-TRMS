// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMCR03RM extends AbstractDpc {

	public DpcMCR03RM() {
		lib = "MCLISOLIB";
		pgm = "MCR03RM";
		parm = new DPCParameter(6);
		setCheckField(5);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		try {

			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, new BigDecimal(parameters[0].toString().trim()),
					(short) 2, 8D);
			parm.setParm(2, parameters[1].toString().trim(), (short) 1, 5D);
			parm.setParm(3, "", (short) 1, 8D);
			parm.setParm(4, "", (short) 1, 1.0D);
			parm.setParm(5, "", (short) 1, 1.0D);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setParameters(Vector vector) {
	}
}

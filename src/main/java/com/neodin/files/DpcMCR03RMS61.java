package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMCR03RMS61 extends AbstractDpc {

	public DpcMCR03RMS61() {
		lib = "MCLISOLIB";
		pgm = "MCR03RMS61";
		parm = new DPCParameter(9);
		setCheckField(8);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			if (parameters[0].toString().length() > 6) {
				parm.setParm(1,	parameters[0].toString().trim().substring(0, 5),(short) 1, 5D);
				parm.setParm(2,	parameters[0].toString().trim().substring(5, 7),(short) 1, 2D);
			} else {
				parm.setParm(1, parameters[0].toString().trim(), (short) 1, 5D);
				parm.setParm(2, "", (short) 1, 2D);
			}
			parm.setParm(3, parameters[1].toString().trim(), (short) 1, 1.0D);
			parm.setParm(4, new BigDecimal(parameters[2].toString().trim()),(short) 2, 3D);
			parm.setParm(5, parameters[3].toString().trim(), (short) 1, 1.0D);
			parm.setParm(6, parameters[4].toString().trim(), (short) 2, 5.2D);  
			//parm.setParm(7, "", (short) 1, 3100D);
			parm.setParm(7, "", (short) 1, 6200D);
			parm.setParm(8, new BigDecimal("0"), (short) 2, 3D);
		} catch (Exception _ex) {
			System.out.println(_ex.getMessage());
			return;
		}
	}

	public void setParameters(Vector vector) {
	}
}

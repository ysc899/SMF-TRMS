package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMCR003T11 extends AbstractDpc {
	public DpcMCR003T11() {
		lib = "MCLISOLIB";
		pgm = "MCR003T11";
		parm = new DPCParameter(6);
		setCheckField(5);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {  
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, new BigDecimal(parameters[0].toString().trim()),(short) 2, 8D);
			parm.setParm(2, parameters[1].toString().trim(), (short) 1, 5D);
			parm.setParm(3, "1", (short) 1, 1.0D);
			parm.setParm(4, "0", (short) 2, 5D);
			parm.setParm(5, "", (short) 1, 1.0D);//생성여부 A,N
		} catch (Exception e) {
			e.getMessage();
		}
	}
	public void setParameters(Vector vector) {
	}
}

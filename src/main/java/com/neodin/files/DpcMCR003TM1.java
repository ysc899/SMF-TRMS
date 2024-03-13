package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import com.neodin.comm.AbstractDpc;

import mbi.jmts.dpc.DPCParameter;

public class DpcMCR003TM1 extends AbstractDpc{
	public DpcMCR003TM1() {
		lib = "MCLISOLIB";
		pgm = "MCR003TM1";
		parm = new DPCParameter(7);
		setCheckField(6);
	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, parameters[0].toString().trim(), (short) 1, 5D);
			parm.setParm(2, new BigDecimal(parameters[1].toString().trim()),
					(short) 2, 8D);
			parm.setParm(3, new BigDecimal(parameters[2].toString().trim()),
					(short) 2, 5D);
			parm.setParm(4, parameters[3].toString().trim(), (short) 1, 7D);
			
			parm.setParm(5, new BigDecimal("0"), (short) 1, 5D);
			
			parm.setParm(6, new BigDecimal("0"), (short) 1, 1D);
//			parm.setParm(5, "", (short) 1, 32400D);
//			parm.setParm(6, "", (short) 1, 1200D);
//			parm.setParm(7, new BigDecimal("0"), (short) 2, 3D);
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}

}

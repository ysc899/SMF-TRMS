package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import com.neodin.comm.AbstractDpc;

import mbi.jmts.dpc.DPCParameter;

public class DpcMWR003RMT8 extends AbstractDpc {
	public DpcMWR003RMT8() {
		lib = "MCLISOLIB";
		pgm = "MWR003RMT8";
		parm = new DPCParameter(9);
		setCheckField(8);
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
			parm.setParm(5, "", (short) 1, 32400D);
			parm.setParm(6, "", (short) 1, 1200D);
			parm.setParm(7, new BigDecimal("0"), (short) 2, 3D);
			parm.setParm(8,  parameters[4].toString().trim(), (short) 1, 10D);//MID
			
//			System.out.println("======================");
//			System.out.println("NML");
//			System.out.println(parameters[0]);
//			System.out.println(parameters[1]);
//			System.out.println(parameters[2]);
//			System.out.println(parameters[3]);
			
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

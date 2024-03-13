package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMWR03RMT75 extends AbstractDpc {
	public DpcMWR03RMT75() {
		lib = "MCLISOLIB";
		//pgm = "MCR03RMS75"; //201500609
		pgm = "MWR03RMT75"; 
		
		parm = new DPCParameter(8);
		setCheckField(7);
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
			parm.setParm(5, "", (short) 1, 12200D);
			parm.setParm(6, new BigDecimal("0"), (short) 2, 12D);
			parm.setParm(7, parameters[4].toString(), (short) 1, 10D);//MID
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

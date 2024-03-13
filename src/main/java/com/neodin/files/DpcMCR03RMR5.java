// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcMCR03RMR5 extends AbstractDpc {

	public DpcMCR03RMR5() {
		lib = "MCLISOLIB";
		pgm = "MCR03RMR51";
		parm = new DPCParameter(35);
		setCheckField(34);
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
			parm.setParm(5, new BigDecimal(parameters[4].toString().trim()),
					(short) 2, 2D);
			parm.setParm(6, "", (short) 1, 2000D);
			parm.setParm(7, "", (short) 1, 3200D);
			parm.setParm(8, "", (short) 1, 2000D);
			parm.setParm(9, "", (short) 1, 12400D);
			parm.setParm(10, "", (short) 1, 6400D);
			parm.setParm(11, "", (short) 1, 12400D);
			parm.setParm(12, "", (short) 1, 2400D);
			parm.setParm(13, "", (short) 1, 400D);
			parm.setParm(14, "", (short) 1, 2800D);
			parm.setParm(15, "", (short) 1, 20400D);
			parm.setParm(16, "", (short) 1, 800D);
			parm.setParm(17, "", (short) 1, 30400D);
			parm.setParm(18, "", (short) 1, 400D);
			parm.setParm(19, "", (short) 1, 2800D);
			parm.setParm(20, "", (short) 1, 3200D);
			parm.setParm(21, "", (short) 1, 1600D);
			parm.setParm(22, "", (short) 1, 1200D);
			parm.setParm(23, "", (short) 1, 400D);
			parm.setParm(24, "", (short) 1, 4000D);
			parm.setParm(25, "", (short) 1, 400D);
			parm.setParm(26, "", (short) 1, 400D);
			parm.setParm(27, "", (short) 1, 2000D);
			parm.setParm(28, "", (short) 1, 8000D);
			parm.setParm(29, "", (short) 1, 8000D);
			parm.setParm(30, "", (short) 1, 400D);
			parm.setParm(31, "", (short) 1, 8400D);
			parm.setParm(32, "", (short) 1, 24400D);
			parm.setParm(33, "0", (short) 2, 5D);
			parm.setParm(34, "", (short) 1, 4400D);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setParameters(Vector vector) {
	}
}

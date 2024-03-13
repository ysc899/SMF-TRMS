// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 4   Fields: 0

package com.neodin.files;
import java.math.BigDecimal;
import mbi.jmts.dpc.DPCParameter;
import java.util.Vector;
import com.neodin.comm.AbstractDpc;

public final class DpcMCR03RMS71 extends AbstractDpc {

	public DpcMCR03RMS71() {
		lib = "MCLISOLIB";
		pgm = "MCR03RMS71";
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
			parm.setParm(5, "", (short) 1, 3050D);
			parm.setParm(6, new BigDecimal("0"), (short) 2, 2D);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setParameters(Vector vector) {
	}
}

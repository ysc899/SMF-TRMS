package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public final class DpcSG403RM1 extends AbstractDpc {
	public DpcSG403RM1() {
		lib = "MCLISOLIB";
		pgm = "SG403RM1";
		parm = new DPCParameter(33);
		setCheckField(5);
	}

	public void setParameters() {
	}
	

	public void setParameters(Object parameters[]) {  
		
		try {
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, new BigDecimal(parameters[1].toString().trim()),(short) 2, 8D);
			parm.setParm(2, new BigDecimal(parameters[2].toString().trim()),(short) 2, 8D);
			parm.setParm(3, parameters[3].toString().trim(), (short) 1, 5D);
			
			parm.setParm(4, "", DPCParameter.Text, 5000D);
			parm.setParm(5, "", DPCParameter.Text, 155000D);//생성여부 A,N
			parm.setParm(6, "", DPCParameter.Text, 5000D);
			parm.setParm(7, "", DPCParameter.Text, 5000D);
			parm.setParm(8, "", DPCParameter.Text, 155000D);
			parm.setParm(9, "", DPCParameter.Text, 70000D);
			parm.setParm(10, "", DPCParameter.Text, 15000D);
			parm.setParm(11, "", DPCParameter.Text, 155000D);
			parm.setParm(12, "", DPCParameter.Text, 5000D);
			parm.setParm(13, "", DPCParameter.Text, 5000D);
			parm.setParm(14, "", DPCParameter.Text, 5000D);
			parm.setParm(15, "",  DPCParameter.Text, 5000D);
			parm.setParm(16, "",  DPCParameter.Text, 5000D);
			parm.setParm(17, "", DPCParameter.Text, 5000D);
			parm.setParm(18, "",  DPCParameter.Text, 5000D);
			parm.setParm(19, "",  DPCParameter.Text, 40000D);
			parm.setParm(20, "",  DPCParameter.Text, 60000D);
			parm.setParm(21, "",  DPCParameter.Text, 60000D);
			parm.setParm(22, "",  DPCParameter.Text, 60000D);
			parm.setParm(23, "",  DPCParameter.Text, 60000D);
			parm.setParm(24, "",  DPCParameter.Text, 5000D);
			parm.setParm(25, "",  DPCParameter.Text, 5000D);
			parm.setParm(26, "",  DPCParameter.Text, 5000D);
			parm.setParm(27, "",  DPCParameter.Text, 5000D);
			parm.setParm(28, "",  DPCParameter.Text, 5000D);
			parm.setParm(29, "",  DPCParameter.Text, 5000D);
			parm.setParm(30, "",  DPCParameter.Text, 5000D);
			
			parm.setParm(31, 0, DPCParameter.Pack, 4.0);
			
			parm.setParm(32, "", DPCParameter.Text, 1D);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	public void setParameters(Vector vector) {
	}

}

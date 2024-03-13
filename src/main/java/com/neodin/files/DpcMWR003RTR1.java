package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public class DpcMWR003RTR1 extends AbstractDpc {
	
	public DpcMWR003RTR1() {
		lib = "MCLISOLIB";
		pgm = "MWR003RTR1";
		parm = new DPCParameter(9);
		setCheckField(8);

	}

	public void setParameters() {
	}

	public void setParameters(Object parameters[]) {
		try {
			//구분값 A-전체  O-다운받은자료  X-미다운자료
			String strDownLoadFlag = "A";
			if(!(Boolean) parameters[3]){//isAllDownload
				strDownLoadFlag ="X";
			}
			parm.setParm(0, "NML", (short) 1, 3D);
			parm.setParm(1, parameters[1].toString().trim(), (short) 1, 5D); // 거래코드
			parm.setParm(2, new BigDecimal(parameters[0].toString().trim()),(short) 2, 8.0D); //TODO : from 
			parm.setParm(3, new BigDecimal(parameters[2].toString().trim()),(short) 2, 8.0D); //TODO : to
			parm.setParm(4, strDownLoadFlag, (short) 1, 1D); //구분값 A-전체  O-다운받은자료  X-미다운자료
			parm.setParm(5, parameters[4].toString().trim(), (short) 1, 5D); //구분값 A-전체  O-다운받은자료  X-미다운자료
			parm.setParm(6, "0", (short) 2, 5.0D); //갯수
			parm.setParm(7, "", (short) 1, 1.0D); // error
			parm.setParm(8, "", (short) 1, 10D); // MID

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

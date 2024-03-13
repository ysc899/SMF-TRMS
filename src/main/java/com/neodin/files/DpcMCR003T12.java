package com.neodin.files;

import java.math.BigDecimal;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;

public class DpcMCR003T12 extends AbstractDpc {
	//[NML, 20150507, 22023, 1, 0, ]
	public DpcMCR003T12() {
		lib = "MCLISOLIB";
		pgm = "MCR003T12";
		//parm = new DPCParameter(6);
		//setCheckField(5);
		parm = new DPCParameter(7);
		setCheckField(6);

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
			parm.setParm(4, strDownLoadFlag, (short) 1, 1.0D); //구분값 A-전체  O-다운받은자료  X-미다운자료
			parm.setParm(5, "0", (short) 2, 5D); //갯수
			parm.setParm(6, "", (short) 1, 1.0D); // error

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setParameters(Vector vector) {
	}
}

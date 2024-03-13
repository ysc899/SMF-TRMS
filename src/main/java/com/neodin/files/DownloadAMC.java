package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;  

import com.neodin.comm.AbstractDpc;

// Referenced classes of package com.neodin.files:
//            ResultDownload, DpcMCR03RMR5

public class DownloadAMC extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadAMC() {
		isDebug = false;
		initialize();
	}

	public DownloadAMC(String id, String fdat, String tdat, Boolean isRewrite) {

		isDebug = false;
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public void closeDownloadFile() {
		if (isData)
			try {
				workbook.write();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	public void initialize() {
		getDpc().put("\uACB0\uACFC\uB2E4\uC6B4\uB85C\uB4DC",
				new DpcMCR003RMR8());
	}

	public void makeDownloadFile() {
		row = 1;
		try {
			workbook = Workbook
					.createWorkbook(new File(savedir + makeOutFile()));
			wbresult = workbook.createSheet("Result", 0);
			String ArraryResult[] = null;
			ArraryResult = (new String[] { "\uC758\uB8B0\uC77C\uC790",
					"\uB4F1\uB85D\uBC88\uD638", "\uAC80\uCCB4\uBC88\uD638",
					"\uC131\uBA85", "Item Code", "Seq. No", "Result" });
			for (int i = 0; i < ArraryResult.length; i++) {
				label = new jxl.write.Label(i, 0, ArraryResult[i]);
				wbresult.addCell(label);
			}
		} catch (Exception e) {
//			System.out
//					.println("OCS \uD30C\uC77C\uC4F0\uAE30 \uC2A4\uB808\uB4DC \uC624\uB958"
//							+ e.getMessage());
		}
	}

	public void processingData(int cnt) {
		try {
			String hosCode[] = (String[]) getDownloadData().get(
					"\uBCD1\uC6D0\uCF54\uB4DC");
			String rcvDate[] = (String[]) getDownloadData().get(
					"\uC811\uC218\uC77C\uC790");
			String rcvNo[] = (String[]) getDownloadData().get(
					"\uC811\uC218\uBC88\uD638");
			String specNo[] = (String[]) getDownloadData().get(
					"\uAC80\uCCB4\uBC88\uD638");
			String chartNo[] = (String[]) getDownloadData().get(
					"\uCC28\uD2B8\uBC88\uD638");
			String patName[] = (String[]) getDownloadData().get(
					"\uC218\uC2E0\uC790\uBA85");
			String inspectCode[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uCF54\uB4DC");
//			String[] _tmp = (String[]) getDownloadData().get(
//					"\uAC80\uC0AC\uBA85");
			String seq[] = (String[]) getDownloadData().get(
					"\uC77C\uB828\uBC88\uD638");
			String result[] = (String[]) getDownloadData().get("\uACB0\uACFC");
			String resultType[] = (String[]) getDownloadData().get(
					"\uACB0\uACFC\uD0C0\uC785");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			String data[] = new String[7];
			
			for (int i = 0; i < cnt; i++) {

				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				data[0] = rcvDate[i].trim();
				data[1] = chartNo[i];
				data[2] = specNo[i].trim();
				data[3] = patName[i];
				data[4] = clientInspectCode[i].trim();
				data[5] = "";
				if (resultType[i].trim().equals("C"))
					data[6] = result[i];
				else if (((AbstractDpc) getDpc()
						.get("\uBB38\uC7A5\uACB0\uACFC"))
						.processDpc(new Object[] { hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i] }))
					data[6] = ((AbstractDpc) getDpc().get(
							"\uBB38\uC7A5\uACB0\uACFC")).getParm()
							.getStringParm(5);
				else
					data[6] = "";
				for (int k = 0; k < data.length; k++) {
					label = new jxl.write.Label(k, row, data[k]);
					wbresult.addCell(label);
				}
				row++;
			}
			if (cnt == 400)
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			else
				setParameters(null);
		} catch (Exception e) {
			setParameters(null);
			e.printStackTrace();
		}
	}
}

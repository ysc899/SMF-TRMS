package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 7   Fields: 1

import java.io.File;

import jxl.Workbook;

public class DownloadBusanSungMo extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadBusanSungMo() {
		isDebug = false;
		initialize();
	}

	public DownloadBusanSungMo(String id, String fdat, String tdat,
			Boolean isRewrite) {

		isDebug = false;
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String src, int length) {
		String dest = src.trim().substring(0);
		if (src.trim().length() < length) {
			for (int i = 0; i < length - src.length(); i++)
				dest = dest + " ";

		} else {
			dest = src.substring(0, length);
		}
		return dest;
	}

	public String appendBlanks2(String src, int length) {
		String dest = src.trim().substring(0);
		int lens = src.length();
		if (src.trim().length() < length) {
			for (int i = 0; i < length - lens; i++)
				dest = dest + " ";

		} else {
			dest = src.substring(0, length);
		}
		return dest;
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

	public void makeDownloadFile() {
		row = 1;
		if (!isDebug)
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String ArraryResult[] = null;
				ArraryResult = (new String[] { "\uB4F1\uB85D\uBC88\uD638",
						"\uAC80\uCCB4\uBC88\uD638", "\uC131\uBA85",
						"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC ", "\uACB0\uACFC" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}

			} catch (Exception e) {
//				System.out
//						.println("OCS \uD30C\uC77C\uC4F0\uAE30 \uC2A4\uB808\uB4DC \uC624\uB958"
//								+ e.getMessage());
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
			String inspectName[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uBA85");
			String seq[] = (String[]) getDownloadData().get(
					"\uC77C\uB828\uBC88\uD638");
			String result[] = (String[]) getDownloadData().get("\uACB0\uACFC");
			String resultType[] = (String[]) getDownloadData().get(
					"\uACB0\uACFC\uD0C0\uC785");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			String history[] = (String[]) getDownloadData().get("\uC774\uB825");
			String sex[] = (String[]) getDownloadData().get("\uC131\uBCC4");
//			String[] _tmp = (String[]) getDownloadData().get(
//					"\uC774\uBBF8\uC9C0\uC5EC\uBD80");
//			String[] _tmp1 = (String[]) getDownloadData().get(
//					"\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			String remark[] = new String[4];
			for (int i = 0; i < cnt; i++) {

				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				String data[] = new String[9];
				String rlt[] = new String[6];
				data[0] = rcvDate[i];
				data[1] = chartNo[i];
				data[2] = specNo[i].trim();
				data[3] = patName[i];
				data[4] = clientInspectCode[i].trim();
				data[5] = inspectCode[i];
				data[6] = inspectName[i];
				data[7] = "";
				if (resultType[i].trim().equals("C")) {
					data[7] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
				} else {
					data[7] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				if (!isDebug) {
					rlt[0] = data[1];
					rlt[1] = data[2];
					rlt[2] = data[3];
					rlt[3] = data[4];
					rlt[4] = data[7];
					for (int k = 0; k < rlt.length; k++) {
						label = new jxl.write.Label(k, row, rlt[k]);
						wbresult.addCell(label);
					}

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

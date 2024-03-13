package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.StringTokenizer;

import jxl.Workbook;

public class DownloadHanil extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadHanil() {
		isDebug = false;
		initialize();
	}

	public DownloadHanil(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public void closeDownloadFile() {
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

	public String getFileName() {
		StringTokenizer st = new StringTokenizer(m_Filename, ".");
		String head = st.nextToken();
		String tail = "." + st.nextToken();
		return head.substring(0, head.length() - 2) + tail;
	}

	public void makeDownloadFile() {
		row = 1;
		row2 = 1;
		try {
			makeOutFile();
			StringTokenizer st = new StringTokenizer(getFileName(), ".");
			String sheetName = st.nextToken();
			workbook = Workbook
					.createWorkbook(new File(savedir + getFileName()));
			wbresult = workbook.createSheet(sheetName, 0);
			String ArraryResult[] = { "\uAC80\uCCB4\uBC88\uD638",
					"\uCC28\uD2B8\uBC88\uD638",
					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC",
					"\uC218\uC2E0\uC790\uBA85",
					"\uB124\uC624\uB518\uC811\uC218\uC77C\uC790",
					"\uB124\uC624\uB518\uC811\uC218\uBC88\uD638",
					"\uACB0\uACFC", "\uACB0\uACFC\uB9AC\uB9C8\uD06C",
					"\uCC38\uACE0\uCE58", "\uBCF4\uACE0\uC77C\uC790",
					"\uB124\uC624\uB518\uAC80\uC0AC\uCF54\uB4DC",
					"\uAE30\uD0C0\uD544\uB4DC" };
			for (int i = 0; i < ArraryResult.length; i++) {
				label = new jxl.write.Label(i, 0, ArraryResult[i]);
				wbresult.addCell(label);
			}

		} catch (Exception e) {
//			System.out
//					.println("OCS \uD30C\uC77C\uC4F0\uAE30 \uC2A4\uB808\uB4DC \uC624\uB958"
//							+ e.getMessage());
			e.printStackTrace();
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
			String sex[] = (String[]) getDownloadData().get("\uC131\uBCC4");
//			String[] _tmp1 = (String[]) getDownloadData().get("\uB098\uC774");
//			String[] _tmp2 = (String[]) getDownloadData().get(
//					"\uC8FC\uBBFC\uBC88\uD638");
//			String[] _tmp3 = (String[]) getDownloadData().get(
//					"\uACB0\uACFC\uC0C1\uD0DC");
			String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			String history[] = (String[]) getDownloadData().get("\uC774\uB825");
			String rmkCode[] = (String[]) getDownloadData().get(
					"\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			String reportDate[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uC644\uB8CC\uC77C");
			String data[] = new String[12];
			String remark[] = new String[4];
			String remarkCode = "";
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				data[0] = specNo[i].trim();
				data[1] = chartNo[i];
				data[2] = clientInspectCode[i].trim();
				data[3] = patName[i];
				data[4] = rcvDate[i].trim();
				data[5] = rcvNo[i].trim();
				data[9] = reportDate[i].trim();
				data[10] = inspectCode[i].trim();
				data[11] = "";
				if (resultType[i].trim().equals("C")) {
					data[6] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[8] = getReferenceValue(remark);
				}
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim())
								|| !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[7] = getReamrkValue(hosCode[i], rcvDate[i],
									rcvNo[i], rmkCode[i]);
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				else
					remarkCode = "";
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

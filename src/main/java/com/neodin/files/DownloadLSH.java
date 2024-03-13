package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1
import java.io.File;
import java.util.StringTokenizer;

import jxl.Workbook;

public class DownloadLSH extends ResultDownload {
	private boolean debug = false;

	boolean isData = true;

	public DownloadLSH() {
		debug = false;
		initialize();
	}

	public DownloadLSH(String id, String fdat, String tdat, Boolean isRewrite) {
		debug = false;
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

	public void closeDownloadFile() {
		if (!debug && isData)
			try {
				workbook.write();
			} catch (Exception _ex) {
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception _ex) {
				}
			}
	}

	public void makeDownloadFile() {
		row = 1;
		if (!debug)
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String ArraryResult[] = null;
				ArraryResult = (new String[] { "\uCC28\uD2B8\uBC88\uD638",
						"\uAC80\uC0AC\uC758\uB8B0\uC77C",
						"\uACB0\uACFC\uC77C\uC790", "\uC218\uC2E0\uC790\uBA85",
						"\uAC80\uC0AC\uBA85", "\uACB0\uACFC", "\uC2E4\uC2DC",
						"EDI\uCF54\uB4DC", "\uAC80\uCCB4\uBA85",
						"\uB2E8\uC704", "\uCC38\uACE0\uCE58",
						"\uAC80\uC0AC\uCF54\uB4DC" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
			} catch (Exception e) {
//				System.out
//						.println("OCS \uD30C\uC77C\uC4F0\uAE30 \uC2A4\uB808\uB4DC \uC624\uB958"
//								+ e.getMessage());
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
//			String[] _tmp = (String[]) getDownloadData().get(
//					"\uAC80\uCCB4\uBC88\uD638");
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
//			String[] _tmp1 = (String[]) getDownloadData().get(
//					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			String sex[] = (String[]) getDownloadData().get("\uC131\uBCC4");
//			String[] _tmp2 = (String[]) getDownloadData().get("\uB098\uC774");
//			String[] _tmp3 = (String[]) getDownloadData().get(
//					"\uC8FC\uBBFC\uBC88\uD638");
//			String[] _tmp4 = (String[]) getDownloadData().get(
//					"\uACB0\uACFC\uC0C1\uD0DC");
			String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			String history[] = (String[]) getDownloadData().get("\uC774\uB825");
//			String[] _tmp5 = (String[]) getDownloadData().get(
//					"\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			String unit[] = (String[]) getDownloadData().get(
					"\uCC38\uACE0\uCE58\uB2E8\uC704\uB4F1");
			String rdate[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uC644\uB8CC\uC77C");
			String bcode[] = (String[]) getDownloadData().get(
					"\uBCF4\uD5D8\uCF54\uB4DC");
			String data[] = new String[17];
//			String[] _tmp6 = new String[4];
			StringTokenizer st = null;
			int tokenCnt = 0;
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				data[0] = chartNo[i].trim();
				data[1] = rcvDate[i].trim();
				data[2] = rdate[i].trim();
				data[3] = patName[i];
				data[4] = inspectName[i];
				if (resultType[i].trim().equals("C"))
					data[5] = result[i];
				else
					data[5] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				if (inspectCode[i].trim().length() == 5
						|| inspectCode[i].substring(5, 7).equals("00"))
					data[6] = "1";
				else
					data[6] = "2";
				data[7] = bcode[i];
				data[8] = " ";
				st = new StringTokenizer(unit[i], ",");
				tokenCnt = st.countTokens();
				if (tokenCnt == 2) {
					data[9] = "";
				} else {
					for (int j = 0; j < tokenCnt; j++)
						data[9] = st.nextToken();
				}
				data[10] = getReferenceValue(
						new String[] { inspectCode[i], lang[i], history[i],
								sex[i] }).trim();
				data[11] = inspectCode[i].trim();
				if (!debug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
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

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class Download300158 extends ResultDownload {
	private boolean debug = false;

	boolean isData = false;

	public Download300158() {
		initialize();
	}

	public Download300158(String id, String fdat, String tdat, Boolean isRewrite) {
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
		row = 2;
		row2 = 1;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				label = new jxl.write.Label(
						0,
						0,
						"(\uC7AC)\uB124\uC624\uB518\uC758\uD559\uC5F0\uAD6C\uC18C   \uCCAB\uBC88\uC7AC ,\uB450\uBC88\uC9F8 Row - \uC5EC\uC720 \uB808\uCF54\uB4DC \uC785\uB2C8\uB2E4.\uD56D\uC2DC \uACB0\uACFC\uB294 3\uBC88\uC9F8 Row \uBD80\uD130 \uC785\uB2C8\uB2E4");
				wbresult.addCell(label);
				ArraryResult = (new String[] { "\uAE30\uAD00\uAD6C\uBD84",
						"\uAC80\uCCB4\uBC88\uD638", "\uC218\uC2E0\uC790\uBA85",
						"\uC131\uBCC4", "\uB098\uC774",
						"\uCC28\uD2B8\uBC88\uD638", "\uC811\uC218\uC77C\uC790",
						"\uC811\uC218\uBC88\uD638", "\uAC80\uC0AC\uCF54\uB4DC",
						"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC",
						"\uAC80\uC0AC\uBA85", "\uBB38\uC790\uACB0\uACFC",
						"\uBB38\uC7A5\uACB0\uACFC", "H/L", "Remark",
						"\uCC38\uACE0\uCE58",
						"\uC8FC\uBBFC\uB4F1\uB85D\uBC88\uD638",
						"\uCC98\uBC29\uBC88\uD638", "CVR" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 1, ArraryResult[i]);
					wbresult.addCell(label);
				}
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
			String inspectName[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uBA85");
			String seq[] = (String[]) getDownloadData().get(
					"\uC77C\uB828\uBC88\uD638");
			String result[] = (String[]) getDownloadData().get("\uACB0\uACFC");
			String resultType[] = (String[]) getDownloadData().get(
					"\uACB0\uACFC\uD0C0\uC785");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			String sex[] = (String[]) getDownloadData().get("\uC131\uBCC4");
			String age[] = (String[]) getDownloadData().get("\uB098\uC774");
			String securityNo[] = (String[]) getDownloadData().get(
					"\uC8FC\uBBFC\uBC88\uD638");
			String highLow[] = (String[]) getDownloadData().get(
					"\uACB0\uACFC\uC0C1\uD0DC");
			String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			String history[] = (String[]) getDownloadData().get("\uC774\uB825");
			String rmkCode[] = (String[]) getDownloadData().get(
					"\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			String cancer[] = (String[]) getDownloadData().get(
					"\uC774\uBBF8\uC9C0\uC5EC\uBD80");
			String cns[] = (String[]) getDownloadData().get(
					"\uCC98\uBC29\uBC88\uD638");
			String data[] = new String[19];
//			String[] _tmp = new String[3];
			String remark[] = new String[5];
			String remarkCode = "";
			for (int i = 0; i < cnt; i++) {
				isData = true;
				String curDate = "";
				String curNo = "";
				data[0] = "11370319";
				data[17] = cns[i];
				data[1] = specNo[i].trim();
				data[2] = patName[i];
				data[3] = sex[i];
				data[4] = age[i];
				data[5] = chartNo[i];
				data[6] = rcvDate[i].trim();
				data[7] = rcvNo[i].trim();
				data[8] = inspectCode[i].trim();
				data[9] = clientInspectCode[i].trim();
				data[10] = inspectName[i];
				data[14] = " ";
				data[16] = securityNo[i];
				data[18] = cancer[i];
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];//cdy 20151130 참고치 메시지 걸리는거 처리
					
					data[15] = getReferenceValue(remark);
					data[12] = "";
					if (inspectCode[i].trim().equals("31059"))
						data[12] = result[i];
					if ((hosCode[i].trim().equals("19159") || hosCode[i].trim()
							.equals("19758"))
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"00091")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00095")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00752")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("11101") || inspectCode[i]
									.trim().substring(0, 5).equals("21061"))) {
						data[12] = appendBlanks("\uAC80  \uC0AC  \uBA85 ", 30)
								+ "\t" + appendBlanks("\uACB0    \uACFC", 21)
								+ " \t" + "\uCC38    \uACE0    \uCE58";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)
								+ "\t" + appendBlanks(result[i], 21) + "\t"
								+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"00091")
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"00095")
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"00752")
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"11101")
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"21061")) {
						data[12] = appendBlanks("\uAC80  \uC0AC  \uBA85 ", 30)
								+ "\t" + appendBlanks("\uACB0    \uACFC", 21)
								+ " \t" + "\uCC38    \uACE0    \uCE58";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)
								+ "\t" + appendBlanks(result[i], 21) + "\t"
								+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					data[11] = "";
					data[15] = "";
					data[12] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				data[13] = highLow[i];
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim())
								|| !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[14] = getReamrkValue(hosCode[i], rcvDate[i],
									rcvNo[i], rmkCode[i]);
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				else
					remarkCode = "";
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
		} catch (Exception _ex) {
			setParameters(null);
		}
	}
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1
import java.io.File;

import jxl.Workbook;

public class DownloadMMC extends ResultDownload {
	private boolean debug = false;

	boolean isData = false;

	public DownloadMMC() {
		debug = false;
		initialize();
	}

	public DownloadMMC(String id, String fdat, String tdat, Boolean isRewrite) {
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
		if (isData)
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
		row = 0;
		row2 = 0;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String ArraryResult[] = null;
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
			}
		} catch (Exception _ex) {
		}
	}

	public void processingData(int cnt) {
		try {
			// String hosCode[] = (String[])
			// getDownloadData().get("\uBCD1\uC6D0\uCF54\uB4DC");
			// String rcvDate[] = (String[])
			// getDownloadData().get("\uC811\uC218\uC77C\uC790");
			// String rcvNo[] = (String[])
			// getDownloadData().get("\uC811\uC218\uBC88\uD638");
			// String specNo[] = (String[])
			// getDownloadData().get("\uAC80\uCCB4\uBC88\uD638");
			// String chartNo[] = (String[])
			// getDownloadData().get("\uCC28\uD2B8\uBC88\uD638");
			// String patName[] = (String[])
			// getDownloadData().get("\uC218\uC2E0\uC790\uBA85");
			// String inspectCode[] = (String[])
			// getDownloadData().get("\uAC80\uC0AC\uCF54\uB4DC");
			// String inspectName[] = (String[])
			// getDownloadData().get("\uAC80\uC0AC\uBA85");
			// String seq[] = (String[])
			// getDownloadData().get("\uC77C\uB828\uBC88\uD638");
			// String result[] = (String[])
			// getDownloadData().get("\uACB0\uACFC");
			// String resultType[] = (String[])
			// getDownloadData().get("\uACB0\uACFC\uD0C0\uC785");
			// String clientInspectCode[] = (String[])
			// getDownloadData().get("\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			// String sex[] = (String[]) getDownloadData().get("\uC131\uBCC4");
			// String age[] = (String[]) getDownloadData().get("\uB098\uC774");
			// String securityNo[] = (String[])
			// getDownloadData().get("\uC8FC\uBBFC\uBC88\uD638");
			// String[] _tmp = (String[])
			// getDownloadData().get("\uACB0\uACFC\uC0C1\uD0DC");
			// String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			// String history[] = (String[])
			// getDownloadData().get("\uC774\uB825");
			// String rmkCode[] = (String[])
			// getDownloadData().get("\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			// String bdt[] = (String[])
			// getDownloadData().get("\uAC80\uC0AC\uC644\uB8CC\uC77C");
			// String cns[] = (String[])
			// getDownloadData().get("\uCC98\uBC29\uBC88\uD638");

			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
			String specNo[] = (String[]) getDownloadData().get("검체번호");
			String chartNo[] = (String[]) getDownloadData().get("차트번호");

			// !
			String patName[] = (String[]) getDownloadData().get("수신자명");
			String inspectCode[] = (String[]) getDownloadData().get("검사코드");
			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");

			// !
			String resultType[] = (String[]) getDownloadData().get("결과타입");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
//			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");

			// !
//			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
//			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
//			String unit[] = (String[]) getDownloadData().get("참고치단위등");
//			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");

			// !
//			String inc[] = (String[]) getDownloadData().get("외래구분");
			// !

			String data[] = new String[18];
			String data_[] = new String[11];
//			String[] _tmp1 = new String[3];
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
				data_ = new String[8];
				String curDate = "";
				String curNo = "";
				data[0] = "11370319";
				data[1] = specNo[i].trim();
				data[2] = patName[i];
				data[3] = bdt[i];
				data[4] = age[i];
				data[5] = chartNo[i];
				data[6] = rcvDate[i].trim();
				data[7] = rcvNo[i].trim();
				data[8] = inspectCode[i].trim();
				data[9] = clientInspectCode[i].trim();
				data[10] = inspectName[i];
				data[14] = " ";
				data[17] = cns[i];
				data[16] = securityNo[i];
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[15] = getReferenceValue(remark);
					data[12] = "";

					// hosCode[]
					if (hosCode[i].trim().equals("21221")
							&& (inspectCode[i].trim().length() == 7 || !resultType[i]
									.trim().equals("C"))) {
						continue;
					}
					if (inspectCode[i].trim().equals("31059"))
						data[12] = result[i];
					else if (inspectCode[i].trim().length() == 7
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
					// hosCode[]
					if (hosCode[i].trim().equals("21221")
							&& (inspectCode[i].trim().length() == 7 || !resultType[i]
									.trim().equals("C"))) {
						continue;
					}
					data[11] = "";
					data[15] = "";
					data[12] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim())
								|| !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[12] += "\r\n"
									+ getReamrkValue(hosCode[i], rcvDate[i],
											rcvNo[i], rmkCode[i]);
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				else
					remarkCode = "";
				data_[0] = data[6];
				data_[1] = data[5];
				data_[2] = data[1];
				data_[3] = data[2];
				data_[4] = data[9];
				data_[5] = data[11] + data[12];
				data_[6] = data[15];
				data_[7] = "\uD64D\uCC3D\uC2DD 7000";
				if (!debug) {
					for (int k = 0; k < 8; k++) {
						label = new jxl.write.Label(k, row, data_[k]);
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

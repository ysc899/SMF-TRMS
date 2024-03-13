package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.StringTokenizer;

import jxl.Workbook;

public class DownloadInsung extends ResultDownload {
	boolean isDebug = false;

	// boolean isData = true;
	public DownloadInsung() {
		// isDebug = false;
		initialize();
	}

	public DownloadInsung(String id, String fdat, String tdat, Boolean isRewrite) {
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
		if (!isDebug) {
			try {
				workbook.write();
			} catch (Exception e) {
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void makeDownloadFile() {
		row = 1;
		if (!isDebug) {
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String as[] = null;
				as = (new String[] { "의뢰일자", "챠트번호", "성명", "검사번호", "내원번호",
						"검사코드", "검사명", "검사결과", "참고치", "보고일자" });
				for (int i = 0; i < as.length; i++) {
					label = new jxl.write.Label(i, 0, as[i]);
					wbresult.addCell(label);
				}
			} catch (Exception exception) {
			}
		}
	}

	public void processingData(int cnt) {
		try {
			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
			String specNo[] = (String[]) getDownloadData().get("검체번호");
			String chartNo[] = (String[]) getDownloadData().get("차트번호");
			String patName[] = (String[]) getDownloadData().get("수신자명");
			String inspectCode[] = (String[]) getDownloadData().get("검사코드");
			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");
			String resultType[] = (String[]) getDownloadData().get("결과타입");
//			String[] _tmp = (String[]) getDownloadData().get("병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
//			String[] _tmp1 = (String[]) getDownloadData().get("나이");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String reportDate[] = (String[]) getDownloadData().get("검사완료일");
			String cns[] = (String[]) getDownloadData().get("처방번호");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String data[] = new String[10];
			// String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";

			// !
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				String curDate = "";
				String curNo = "";
				data[0] = rcvDate[i].trim(); // 의뢰일자
				data[1] = chartNo[i]; // 차트번호
				data[2] = patName[i]; // 성명
				data[3] = specNo[i]; //
				data[4] = cns[i];
				// if (rcvNo[i].equals("40297")) {
				// System.out.println(i + "");
				// }
				for (StringTokenizer st = new StringTokenizer(specNo[i].trim(),
						"-"); st.hasMoreElements();)
					try {
						data[3] = st.nextToken();
						data[4] = st.nextToken();
					} catch (Exception e) {
					}
				data[5] = inspectCode[i].trim(); // 검사코드
				data[6] = inspectName[i].trim(); // 검사명
				data[9] = reportDate[i]; // 결과
				data[8] = getReferenceValue(new String[] { inspectCode[i],
						lang[i], history[i], sex[i] });
				if (resultType[i].trim().equals("C")) {
					data[7] = result[i];
				} else {
					data[7] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				if ((hosCode[i].toString().equals("24702")||hosCode[i].toString().equals("33344"))
						&& inspectCode[i].trim().length() == 7) {
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[7] = appendBlanks("검  사  명 ", 30) + "   "
							+ appendBlanks("결    과", 21) + "   "
							+ "참    고    치";
					data[7] += "\r\n"
							+ appendBlanks(inspectName[i], 30)
							+ "   "
							+ appendBlanks(result[i], 21)
							+ "   "
							+ getReferenceValue(new String[] { inspectCode[i],
									lang[i], history[i], sex[i] });
					// data[11] = "";
					// data[15] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						data[7] += "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ "   "
								+ appendBlanks(result[i], 21)
								+ "   "
								+ getReferenceValue(
										new String[] { inspectCode[i], lang[i],
												history[i], sex[i] }).trim();
						if (++i >= cnt)
							break;
					}
					i--;
				} else if (inspectCode[i].trim().length() == 7) {
					if (i != 399) {
						try {
							for (String thisTimeCode = inspectCode[i++].trim()
									.substring(0, 5); thisTimeCode == inspectCode[i++]
									.trim().substring(0, 5); data[8] += "\r\n"
									+ getReferenceValue(new String[] {
											inspectCode[i], lang[i],
											history[i], sex[i] }))
								if (resultType[i].trim().equals("C")) {
									data[7] += "\r\n" + inspectName[i] + "   "
											+ result[i];
								} else {
									data[7] += "\r\n"
											+ getTextResultValue(hosCode[i],
													rcvDate[i], rcvNo[i],
													inspectCode[i]);
								}
							i -= 2;
						} catch (Exception ee) {
							if (i > cnt) {
								i -= 1;
							}
							if (i > cnt) {
								i -= 1;
							}
							if (i == cnt) {
								i -= 1;
							}
							// if (i == cnt) {
							// System.out.println("");
							// }
						}
					} else {
						data[8] += "\r\n"
								+ getReferenceValue(new String[] {
										inspectCode[i], lang[i], history[i],
										sex[i] });
						if (resultType[i].trim().equals("C")) {
							data[7] += "\r\n" + inspectName[i] + "   "
									+ result[i];
						} else {
							data[7] += "\r\n"
									+ getTextResultValue(hosCode[i],
											rcvDate[i], rcvNo[i],
											inspectCode[i]);
						}
					}
				}
				
			
	//대성병원 20170817 양태용 부속 검사중 대표코드(*****00)에만 리마크 노출
				
				if (resultType[i].trim().equals("C") && hosCode[i].toString().equals("19758") 
					&&( !inspectCode[i].trim().substring(5, 7).equals("00"))){
					
					rmkCode[i]="";
					
					}
				
				
				//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if ((inspectCode[i].trim().equals("31100")||inspectCode[i].trim().equals("31101")
								||inspectCode[i].trim().equals("31102")||inspectCode[i].trim().equals("31103")
								||inspectCode[i].trim().equals("31104")||inspectCode[i].trim().equals("31105")
								||inspectCode[i].trim().equals("31106")||inspectCode[i].trim().equals("31107")
								||inspectCode[i].trim().equals("31108")||inspectCode[i].trim().equals("31109")
								||inspectCode[i].trim().equals("31110")||inspectCode[i].trim().equals("31111")
								||inspectCode[i].trim().equals("31112")||inspectCode[i].trim().equals("31113")
								||inspectCode[i].trim().equals("31114")||inspectCode[i].trim().equals("31115")
								||inspectCode[i].trim().equals("31116")||inspectCode[i].trim().equals("31117")
								||inspectCode[i].trim().equals("31118")||inspectCode[i].trim().equals("31119")
								||inspectCode[i].trim().equals("31120")||inspectCode[i].trim().equals("31121")
								||inspectCode[i].trim().equals("31122")||inspectCode[i].trim().equals("31123")
								||inspectCode[i].trim().equals("31124"))) {
					
					data[7] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					data[8] = data[7].trim();
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001")
								)
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[7] = data[7] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
							data[8] = data[7].trim();
							i++;
							// culture_flag = true;
						} else {
							data[7] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[8] = data[7].trim();
						}
					} catch (Exception e) {
						data[7] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[8] = data[7].trim();
					}
				}
				
				
				
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(rcvDate[i].trim())
								|| !kumdata[1].trim().equals(rcvNo[i])
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals("11026")|| inspectCode[i].trim().substring(0, 5).equals("11052")) {
								if (!hosCode[i].toString().equals("18221")) { // 문경
																				// 중앙병원
									data[7] = data[7]
											+ "\r\n"
											+ getReamrkValue(hosCode[i],
													rcvDate[i], rcvNo[i],
													rmkCode[i]);
								}
							} else {
								if (!hosCode[i].toString().equals("18221")) { // 문경
																				// 중앙병원
									data[7] += getReamrkValue(hosCode[i],
											rcvDate[i], rcvNo[i], rmkCode[i]);
								}
							}
							kumdata[0] = data[1].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
						//System.out.println(_ex.getMessage());
					}
				else {
					remarkCode = "";
				}
				if (!isDebug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
						wbresult.addCell(label);
					}
				}
				row++;
			}
			if (cnt == 400) {
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception e) {
			setParameters(null);
			e.printStackTrace();
		}
	}
}

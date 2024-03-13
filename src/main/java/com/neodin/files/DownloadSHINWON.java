package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

/*
 신원의학연구소
 */
import java.io.File;

import jxl.Workbook;

public class DownloadSHINWON extends ResultDownload {

	// !
	private boolean debug = false;

	//
//	private String gubun1 = "\n============================================================\n";

//	private String gubun2 = "\n------------------------------------------------------------\n";



	//
//	private com.neodin.result.PatientInformation mPatientData;

//	private jxl.write.Number number = null;

	public DownloadSHINWON() {
		initialize();
	}

	public DownloadSHINWON(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String src, int length) {
		return OracleCommon.appendBlanks(src, length);
	}

	public void closeDownloadFile() {
		if (!debug) {
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
		row = 0;
		row2 = 0;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
//				String ArraryResult[] = null;
//				ArraryResult = (new String[] { "접수일자+접수번호", // 0
//						"신원검사코드", // 1
//						"검사결과", // 2
//						"Comment", // 3
//						"참고치상", // 4
//						"참고치하", // 5
//						"출력용참고치", // 6
//						"단위", // 7
//						"검사일자", // 8
//						"검사접수번호", // 9
//						"검사코드", // 10
//						"검사명", // 11
//						"수진자명" // 12
//				});
			}
		} catch (Exception e) {
		}
	}

	public void processingData(int cnt) {
		try {

			// !
			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
//			String specNo[] = (String[]) getDownloadData().get("검체번호");
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
//			String age[] = (String[]) getDownloadData().get("나이");
//			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
//			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
//			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");
//			String img[] = (String[]) getDownloadData().get("이미지여부");
			String unit[] = (String[]) getDownloadData().get("참고치단위등");

			// !
			String data[] = new String[13];
//			String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";

			// !
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

				// !
				data[0] = chartNo[i]; // 병원키값(차트번호에 입력됨))
				data[1] = clientInspectCode[i].trim(); // 병원검사 코드
				data[2] = result[i]; // 검사결과
				if ((result[i].trim().toLowerCase().indexOf("mg/dl") > 0 || result[i]
						.trim().indexOf("mg/dL") > 0)) {
					data[2] = result[i].replace('m', ' ').replace('g', ' ')
							.replace('/', ' ').replace('d', ' ').replace('l',
									' ').replace('M', ' ').replace('G', ' ')
							.replace('/', ' ').replace('D', ' ').replace('L',
									' ').trim();
				}
				data[3] = ""; // 리마큳
				data[4] = ""; // 참고치 상

				// !
				data[5] = ""; // 참고치 하
				// data[6] = getReferenceValue(remark); // 표시참고치
				data[7] = unit[i]; // 단위
				data[8] = bdt[i]; // 검사일자(보고일자)
				data[9] = rcvDate[i] + rcvNo[i]; // 검사접수번호

				// !
				data[10] = inspectCode[i]; // 검사_검사코드
				data[11] = inspectName[i]; // 검사명
				data[12] = patName[i]; // 수진자명
				if (resultType[i].trim().equals("C")) {
					data[2] = result[i];
					if ((result[i].trim().toLowerCase().indexOf("mg/dl") > 0 || result[i]
							.trim().indexOf("mg/dL") > 0)) {
						data[2] = result[i].replace('m', ' ').replace('g', ' ')
								.replace('/', ' ').replace('d', ' ').replace(
										'l', ' ').replace('M', ' ').replace(
										'G', ' ').replace('/', ' ').replace(
										'D', ' ').replace('L', ' ').trim();
					}
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[6] = getReferenceValue(remark);
					// !

					// !
					if (inspectCode[i].trim().equals("21150")
							|| inspectCode[i].trim().equals("21137")
							|| inspectCode[i].trim().equals("21138")
							|| inspectCode[i].trim().equals("21141")
							|| inspectCode[i].trim().equals("21142")
							|| inspectCode[i].trim().equals("21064")
							|| inspectCode[i].trim().equals("21192")) {
						if (result[i].trim().toUpperCase().indexOf("NEGATIVE") > -1
								&& result[i].trim().length() > 8) {
							data[2] = result[i].trim().substring(0, 8) + " ("
									+ result[i].trim().substring(8).trim()
									+ ")"; // 문자결과
						} else if (result[i].trim().toUpperCase().indexOf(
								"POSITIVE") > -1
								&& result[i].trim().length() > 8) {
							data[2] = result[i].trim().substring(0, 8) + " ("
									+ result[i].trim().substring(8).trim()
									+ ")"; // 문자결과
						}
					} else if (inspectCode[i].trim().equals("21539")
							|| inspectCode[i].trim().equals("41147")
							|| inspectCode[i].trim().equals("41194")) {
						// data[2] = result[i].trim() + "\t\t" + data[6];
						data[2] = result[i].trim();
					} else if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("00405"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[2] = result[i];
							}
							if (++i == cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("21065"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"00")) {
								data[2] = result[i];
							}
							if (++i == cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7 //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"00091") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"00095") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"00752") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"11101") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"21061") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"31051") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"71251") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"71252") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"41026") //
							&& !inspectCode[i].trim().substring(0, 5).equals(
									"11026")) {
						data[2] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[2] += "\r\n" + appendBlanks(inspectName[i], 30)
								+ "\t" + appendBlanks(result[i], 21) + "\t"
								+ data[6];
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						// for (String thisTimeCode =
						// inspectCode[i++].trim().substring(0, 5);
						// thisTimeCode.equals(inspectCode[i].trim().substring(0,
						// 5)) && curDate.equals(rcvDate[i].trim()) &&
						// curNo.equals(rcvNo[i].trim());) {
						// data[2] += "\r\n" + appendBlanks(inspectName[i], 30)
						// + "\t" + appendBlanks(result[i], 21) + "\t" +
						// getReferenceValue(new String[] {inspectCode[i],
						// lang[i], history[i], sex[i]}).trim();
						// if (++i == cnt)
						// break;
						// }
						// i--;

						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[2] += "\r\n"
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
					data[2] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(rcvDate[i].trim())
								|| !kumdata[1].trim().equals(rcvNo[i].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals(
									" 11026 ")) {
								data[3] = getReamrkValue(hosCode[i],
										rcvDate[i], rcvNo[i], rmkCode[i]);
							} else {
								data[3] = getReamrkValue(hosCode[i],
										rcvDate[i], rcvNo[i], rmkCode[i]);
							}
							kumdata[0] = rcvDate[i].trim();
							kumdata[1] = rcvNo[i].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
						//System.out.println(" 리마크오류 : " + _ex.getMessage());
					}
				else {
					remarkCode = " ";
				}
				if (!debug) {
					for (int k = 0; k < data.length; k++) {
						if (k < 1) {
							try {
								// number = new jxl.write.Number(k, row,
								// Integer.parseInt(data[k].trim()));
								label = new jxl.write.Label(k, row, data[k]);
							} catch (Exception eee) {
								label = new jxl.write.Label(k, row, data[k]);
							}
						} else {
							label = new jxl.write.Label(k, row, data[k]);
						}
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
		} catch (Exception _ex) {
			//System.out.println(" 전체 오류 : " + _ex.getMessage());
			setParameters(null);
		}
	}
}

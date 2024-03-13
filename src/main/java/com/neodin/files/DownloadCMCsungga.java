package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

public class DownloadCMCsungga extends ResultDownload {
	boolean debug = false;

	boolean isData = false;

	//
	String gubun1 = "\n============================================================\n";

	String gubun2 = "\n------------------------------------------------------------\n";





	public DownloadCMCsungga() {
		initialize();
	}

	public DownloadCMCsungga(String id, String fdat, String tdat,
			Boolean isRewrite) {
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
		if (!debug && isData) {
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
		row = 2;
		row2 = 1;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				// wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				label = new jxl.write.Label(0, 0,
						"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				wbresult.addCell(label);
				ArraryResult = (new String[] { "의뢰일자", "기관코드", "등록번호", "검체번호",
						"성명", "검사코드", "결과", "결과상태", "서버상태", "이미지여부", "이미지명",
						"rkey", "이미지경로전체", "이미지가로사이즈", "이미지세로사이즈", "이미지내용",
						"검사명", "이미지저장여부", "서술결과" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 1, ArraryResult[i]);
					wbresult.addCell(label);
				}
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
//			String age[] = (String[]) getDownloadData().get("나이");
//			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
//			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
//			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
//			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");
//			String img[] = (String[]) getDownloadData().get("이미지여부");
//			String unit[] = (String[]) getDownloadData().get("참고치단위등");

			// !
			String data[] = new String[19];
//			String[] _tmp = new String[3];
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
				String curDate = "";
				String curNo = "";
				data[0] = rcvDate[i].trim(); // 의뢰일자
				data[1] = "014"; // 기관코드
				data[2] = chartNo[i]; // 차트번호
				data[3] = specNo[i].trim(); // 검체번호
				data[4] = patName[i]; // 성명
				data[5] = clientInspectCode[i].trim(); // 병원검사코드
				data[6] = ""; // 문자결과
				// data[7] = ""; //상태구분
				data[8] = ""; // 서버상태
				data[9] = ""; // 이미지여부
				data[10] = ""; // 이미지명
				data[11] = ""; // rkey
				data[12] = ""; // 이미지경로
				data[13] = ""; // 이미지가로
				data[14] = ""; // 이미지세로
				data[15] = ""; // 이미지내용
				data[16] = inspectName[i]; // 검사명
				data[17] = ""; // 이미지저장여부
				data[18] = ""; // 문장결과

				// data[17] = cns[i];
				// data[3] = sex[i];
				// data[4] = age[i];
				// data[7] = rcvNo[i].trim();
				// data[8] = inspectCode[i].trim();
				// data[9] = clientInspectCode[i].trim();
				// data[10] = inspectName[i];
				// data[14] = " ";
				// data[16] = securityNo[i];
				if (resultType[i].trim().equals("C")) {
					data[6] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[7] = getReferenceValue(remark);
					// data[12] = "";
					if (inspectCode[i].trim().equals("31059"))
						data[12] = result[i];
					if (inspectCode[i].trim().length() == 7) {
						data[18] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[18] += "\r\n" + appendBlanks(inspectName[i], 30)
								+ "\t" + appendBlanks(result[i], 21) + "\t"
								+ data[15];
						// data[11] = "";
						// data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[18] += "\r\n"
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
					} else if (isMAST(inspectCode[i].trim().substring(0, 5))) {
						Vector vmast = new Vector();
						// data[11] = "";
						// data[15] = "";
						data[18] = appendBlanks("검사항목", 26)
								+ appendBlanks("CLASS", 8)
								+ appendBlanks("검사항목", 25)
								+ appendBlanks("CLASS", 8);
						data[18] += "\r\n";
						data[18] += "\r\n" + appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i], 8);
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i++].substring(0, 1), 8));
						// data[12] += "\r\n";
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],26)
										+ appendBlanks(result[i++].substring(0,1), 8));
								if (inspectCode[i].trim().substring(0,5).equals("00673")|| //
									inspectCode[i].trim().substring(0,5).equals("00674"))
									vmast.addElement(appendBlanks(
											inspectName[i], 26)
											+ appendBlanks(result[i++]
													.substring(0, 1), 8));
								else
									break;
							} catch (Exception e) {
							}
							if (i >= cnt)
								break;
						}
						i--;
						data[18] = getResultMAST(data[18].toString(), vmast)
								+ "\r\n" + getMastRemark();
					} else if (isMAST_Two(inspectCode[i].trim().substring(0, 5))) {
						Vector vmast = new Vector();
						// data[11] = "";
						// data[15] = "";
						data[18] = appendBlanks("검사항목", 30) + appendBlanks("CLASS", 20) + appendBlanks("검사항목", 30) + appendBlanks("CLASS", 20);
						data[18] += "\r\n";
						data[18] += "\r\n" + appendBlanks(inspectName[i], 26)+ appendBlanks(result[i], 8);vmast.addElement(appendBlanks(inspectName[i], 26)							+ appendBlanks(result[i++].substring(0, 1), 8));
						// data[12] += "\r\n";
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],26)+ appendBlanks(result[i++].substring(0,	1), 8));
								if (inspectCode[i].trim().substring(0, 5).equals("00683")||//
									inspectCode[i].trim().substring(0, 5).equals("00684")
									|| inspectCode[i].trim().substring(0, 5).equals( "00687") || inspectCode[i].trim().substring(0, 5).equals( "00688") || inspectCode[i].trim().substring(0, 5).equals( "00689")
									)
									
									//20160422 MAST 변경작업
									//vmast.addElement(appendBlanks(	inspectName[i], 26)	+ appendBlanks(result[i++].substring(0, 1), 8));
									if(isMastDuplPrint(inspectCode[i])){
										i++;
									}else{
										vmast.addElement(appendBlanks(	inspectName[i], 26)	+ appendBlanks(result[i++].substring(0, 1), 8));
									}
									
								else
									break;
							} catch (Exception e) {
							}
							if (i >= cnt)
								break;
						}
						i--;
						data[18] = getResultMAST_Two(data[18].toString(), vmast)
								+ "\r\n" + getMastRemark();
					}
				} else {
					// data[11] = "";
					// data[15] = "";
					data[18] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				// data[13] = highLow[i];
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim())
								|| !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals(
									"11026"))
								data[18] = data[18]
										+ "\r\n"
										+ getReamrkValue(hosCode[i],
												rcvDate[i], rcvNo[i],
												rmkCode[i]);
							else
								data[18] = getReamrkValue(hosCode[i],
										rcvDate[i], rcvNo[i], rmkCode[i]);
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

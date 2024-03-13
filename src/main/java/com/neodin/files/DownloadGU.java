package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

public class DownloadGU extends ResultDownload {
	private boolean debug = false;

	boolean isData = true;

	public DownloadGU() {
		debug = false;
		initialize();
	}

	public DownloadGU(String id, String fdat, String tdat, Boolean isRewrite) {
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
				workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				label = new jxl.write.Label(0, 0, "(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				wbresult.addCell(label);
				ArraryResult = (new String[] { "기관구분", "검체번호", "수신자명", "성별", "나이", "차트번호", "접수일자", "접수번호", "검사코드", "병원검사코드", "검사명", "문자결과", "문장결과",
						"H/L", "Remark", "참고치", "주민등록번호", "처방번호" });
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
			/**/
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
			String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
			// String bdt[] = (String[]) getDownloadData().get("검사완료일");
			// String bgcd[] = (String[]) getDownloadData().get("보험코드");
			// String img[] = (String[]) getDownloadData().get("이미지여부");
			// String unit[] = (String[]) getDownloadData().get("참고치단위등");

			/**/
			String data[] = new String[18];
			// String[] _tmp = new String[3];
			String remark[] = new String[4];
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
				
				
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				
				
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[15] = getReferenceValue(remark);
					data[12] = "";
					if (inspectCode[i].trim().equals("31059"))
						data[12] = result[i];
					if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("00095") 
									|| inspectCode[i].trim().substring(0, 5).equals("00752"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("01")) {
								data[12] = "";
								data[11] = result[i];
							}
							if (++i == cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7 && !inspectCode[i].trim().substring(0, 5).equals("00091")
							&& !inspectCode[i].trim().substring(0, 5).equals("00095") && !inspectCode[i].trim().substring(0, 5).equals("00752")
							&& !inspectCode[i].trim().substring(0, 5).equals("11101") && !inspectCode[i].trim().substring(0, 5).equals("21061")
							&& !inspectCode[i].trim().substring(0, 5).equals("71297") && !inspectCode[i].trim().substring(0, 5).equals("72020")
							&&!inspectCode[i].trim().substring(0, 5).equals("00689")
							&&!inspectCode[i].trim().substring(0, 5).equals("00688")
							&&!inspectCode[i].trim().substring(0, 5).equals("00687")
							) {
						data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}else if (hosCode[i].trim().equals("19816")
							&& (
								inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
								)) {
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (++i == cnt)
								break;
						}
						i--;
						
						data[12] = "별지참조";
						data[11] = ""; // 문자결과
						data[15] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
					}
					
				} else {
					data[11] = "";
					data[15] = "";
					data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				}
				
				//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if ( (inspectCode[i].trim().equals("31100")||inspectCode[i].trim().equals("31101")
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
								||inspectCode[i].trim().equals("31124")||inspectCode[i].trim().equals("31125")
								||inspectCode[i].trim().equals("31126")
								||inspectCode[i].trim().equals("31127")||inspectCode[i].trim().equals("31128")
								||inspectCode[i].trim().equals("31010"))) {
					
					data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//	data[36] = data[35].trim();
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001")
								||inspectCode[i + 1].substring(0, 5).equals("31011"))
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[12] = data[12] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
				//			data[36] = data[35].trim();
							i++;
							// culture_flag = true;
						} else {
							data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//			data[36] = data[35].trim();
						}
					} catch (Exception e) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//		data[36] = data[35].trim();
					}
				}
				
				
				
				
				data[13] = highLow[i];
				

				
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim()) || !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("11052"))
								data[12] = data[12] + "\r\n" + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							else
								data[14] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
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
				setParameters(new String[] { hosCode[cnt - 1], rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1], seq[cnt - 1] });
			else
				setParameters(null);
		} catch (Exception _ex) {
			setParameters(null);
		}
	}
}

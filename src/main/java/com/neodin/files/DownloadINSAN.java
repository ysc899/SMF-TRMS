package com.neodin.files;

import java.io.File;
import jxl.Workbook;
  
//* 울산 인산병원
public class DownloadINSAN extends ResultDownload {
	private boolean debug = false;

	boolean isData = true;

	public DownloadINSAN() {
		// debug = false;
		initialize();
	}

	public DownloadINSAN(String id, String fdat, String tdat, java.lang.Boolean isRewrite) {
		// debug = false;
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
				e.printStackTrace();
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception _ex) {
				}
			}
		}
	}

	public void makeDownloadFile() {
		row = 2;
		row2 = 1;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
				wbresultC = workbook.createSheet("단문", 0);
				wbresultT = workbook.createSheet("장문", 1);
				String ArraryResultTitle[] = null;
				String ArraryResultTitle2[] = null;
				ArraryResultTitle = (new String[] { "검사명(Key)", "샘플번호(key)", "검사 결과" });
				ArraryResultTitle2 = (new String[] { "R" });
				for (int i = 0; i < ArraryResultTitle.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResultTitle[i]);
					wbresultC.addCell(label);
				}
				for (int i = 0; i < ArraryResultTitle2.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResultTitle2[i]);
					wbresultT.addCell(label);
				}
				for (int i = 0; i < ArraryResultTitle.length; i++) {
					label = new jxl.write.Label(i, 1, ArraryResultTitle[i]);
					wbresultT.addCell(label);
				}
			}
		} catch (Exception e) {
			//System.out.println("OCS 파일쓰기 스레드 오류" + e.getMessage());
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
			String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");
			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");
			String data[] = new String[18];
			// String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";
			String ExcelResult[] = null;
			boolean ttt = false;
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				ExcelResult = new String[] { "", "", "", "", "", "", "" };
				String curDate = "";
				String curNo = "";
				String result_ = "";
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
				String dat = rcvDate[i].toString();
				String jno = rcvNo[i].toString();
				inspectCode[i].trim();
				String gcd = inspectCode[i].trim().substring(0, 5);
				inspectCode[i].trim().substring(5);
				data[16] = securityNo[i];
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[15] = getReferenceValue(remark);
					data[12] = "";
					if (inspectCode[i].trim().equals("31059")) {
						data[12] = result[i];
					}
					ttt = false;
					if (inspectCode[i].trim().length() == 7 && isEP(gcd))
						try {
							ttt = true;
							result_ = getResultEEP2(new String[] { dat, jno, gcd });
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& dat.equals(rcvDate[i].trim()) && jno.equals(rcvNo[i].trim());)
								if (++i == cnt)
									break;
							i--;
							data[12] = result_;
						} catch (Exception e) {
							result_ = result_ + e.getMessage();
						}
					if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("11301") || inspectCode[i]
									.trim().substring(0, 5).equals("71244"))) {
						ttt = true;
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
					} else if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("00095"))) {
						if (inspectCode[i].trim().equals("0009101") || inspectCode[i].trim().equals("0009501"))
							data[11] = result[i];
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().equals("0009101") || inspectCode[i].trim().equals("0009501"))
								data[11] = result[i];
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					data[11] = "";
					data[15] = "";
					data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);

					/*
					 * 
					 * if (result[i].trim().toUpperCase().indexOf("NEGATIVE") >
					 * -1) { data[34] = result[i].trim().substring(8) + " (음성)";
					 * //문자결과 data[36] = data[34]; } else if
					 * (result[i].trim().toUpperCase().indexOf("POSITIVE") > -1)
					 * { data[34] = result[i].trim().substring(8) + " (양성)";
					 * //문자결과 data[36] = data[34]; }
					 */
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
						//System.out.println(_ex.getMessage());
					}
				else {
					remarkCode = "";
				}
				ExcelResult[0] = data[10];
				ExcelResult[1] = data[1];
				ExcelResult[3] = data[2];
				ExcelResult[4] = data[5];
				ExcelResult[5] = data[6];
				ExcelResult[6] = data[7];
				if (!debug) {
					if (resultType[i].trim().equals("C") && !ttt) {
						ExcelResult[2] = data[11];
						for (int k = 0; k < ExcelResult.length; k++) {
							label = new jxl.write.Label(k, row2, ExcelResult[k]);
							wbresultC.addCell(label);
						}
						row2++;
					} else {
						ExcelResult[2] = data[12];
						for (int k = 0; k < ExcelResult.length; k++) {
							label = new jxl.write.Label(k, row, ExcelResult[k]);
							wbresultT.addCell(label);
						}
						row++;
					}
				}
			}
			if (cnt == 400) {
				setParameters(new String[] { hosCode[cnt - 1], rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1], seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception _ex) {
			//System.out.println(_ex.getMessage());
			setParameters(null);
		}
	}
}

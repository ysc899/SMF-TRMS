package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

/*
 엑셀
 */
public class DownloadFoundationMSSQL extends ResultDownload {
	boolean debug = true;

	boolean isData = false;

	//
	String gubun1 = "\n============================================================\n";

	String gubun2 = "\n------------------------------------------------------------\n";





	public DownloadFoundationMSSQL() {
		initialize();
	}

	public DownloadFoundationMSSQL(String id, String fdat, String tdat,
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

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-08 오후 3:07:55)
	 * 
	 * @return java.lang.String
	 */
	public String[] cutHrcvDateNumber(String str) {

		// !
		String src_[] = new String[] { "", "" };

		// !
		if (str == null || src_.equals(""))
			return src_;

		// !
		src_ = Common.getDataCut(str, "^");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

		// !
		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[0] = "";
		}
		try {
			src_[1] = src_[1].replace('D', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		return src_;
	}

	protected String getRemarkTxt2(String str[]) {
		StringBuffer b = new StringBuffer("");
		boolean isSensi = false;
//		boolean isSensi2 = false;

		// !
		if (str.length == 0)
			return null;

		// !
		for (int i = 0; i < str.length; i++) {
			int kk = str[i].trim().lastIndexOf("<균　명>");
			if (kk > -1) {
				b.append("\r\n" + "<균 명 >" + "\r\n\r\n");
				b.append(str[i].trim().substring(5).trim() + " \r\n");
				isSensi = true;
			} else {
				if (isSensi && str[i].trim().trim().startsWith("1")) {
					b.append("                                         "
							+ str[i].trim().trim() + "\r\n\r\n");
//					isSensi2 = true;
				} else {
					// if (isSensi2) {
					b.append(str[i].trim() + "\r\n");
					// } else {
					// b.append(str[i].trim() + "\r\n");
					// }
				}
			}
		}
		return b.toString().trim();
	}

	protected synchronized String getTextResultValue2(String hos, String date,
			String jno, String rcd) {
		String result = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과")).processDpc(new Object[] {
					hos, date, jno, rcd })) {
				return "";
			}   
			String ArrayResult[] = Common
					.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get(
							"문장결과")).getParm().getStringParm(5));
			result = getRemarkTxt2(ArrayResult);
			if (result == null)
				result = "";
		} catch (Exception e) {    
			return "";
		}
		return result;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-10-25 오후 6:54:21)
	 * 
	 * @return java.lang.String
	 */
	public String[] getUintCut(String unt___) {
		String str[] = new String[] { "", "", "" };
		str = Common.getDataCut(unt___, ",");
		if (str.length == 2) {
			str = new String[] { str[0], str[1].replace(',', ' ').trim(), "" };
		}
		return str;
	}

	public void makeDownloadFile() {
		row = getStsrtResultRow() - 1;
		row2 = 1;
		try {
			String ArraryResult[] = null;
			ArraryResult = (getExcelFieldNameArray());
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				label = new jxl.write.Label(0, 0,
						"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				if (row == 2)
					wbresult.addCell(label);

				// !
				// ArraryResult = (getExcelFieldNameArray());
				if (row > 1) {
					for (int i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 1, ArraryResult[i]);
						wbresult.addCell(label);
					}
				} else if (row == 1) {
					for (int i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 0, ArraryResult[i]);
						wbresult.addCell(label);
					}
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
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");
			// !
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");
			// getDownloadData().put("요양기관번호",
			// Common.getArrayTypeData(dpcparameter.getStringParm(29), 20, i));

			// !
			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");

			// !
			String inc[] = (String[]) getDownloadData().get("외래구분");

			// !
			String data[] = new String[40];
//			String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";

			// !
			/*
			 * hsExcelFieldName.put("1", "내원번호"); hsExcelFieldName.put("2",
			 * "외래구분"); hsExcelFieldName.put("3", "의뢰일자");
			 * hsExcelFieldName.put("4", "검체번호");
			 * 
			 * hsExcelFieldName.put("5", "처방번호"); hsExcelFieldName.put("6",
			 * "처방코드"); hsExcelFieldName.put("7", "처방명");
			 * hsExcelFieldName.put("8", "검체");
			 * 
			 * //! hsExcelFieldName.put("9", "일련번호"); hsExcelFieldName.put("10",
			 * "검체코드"); hsExcelFieldName.put("11", "여유필드");
			 * 
			 * //! hsExcelFieldName.put("12", "차트번호");
			 * hsExcelFieldName.put("13", "수진자명"); hsExcelFieldName.put("14",
			 * "주민번호"); hsExcelFieldName.put("15", "나이");
			 * hsExcelFieldName.put("16", "성별"); hsExcelFieldName.put("17",
			 * "과명"); hsExcelFieldName.put("18", "병동");
			 * hsExcelFieldName.put("19", "참고사항"); hsExcelFieldName.put("21",
			 * "보고일자"); hsExcelFieldName.put("22", "처방일자");
			 * hsExcelFieldName.put("25", "단위"); hsExcelFieldName.put("26",
			 * "줄바꿈구분"); hsExcelFieldName.put("42", "최고치");
			 * hsExcelFieldName.put("43", "최저치"); hsExcelFieldName.put("30",
			 * "기관구분"); hsExcelFieldName.put("31", "접수일자");
			 * hsExcelFieldName.put("32", "접수번호"); hsExcelFieldName.put("33",
			 * "검사코드"); hsExcelFieldName.put("34", "단문결과");
			 * hsExcelFieldName.put("35", "장문결과"); hsExcelFieldName.put("36",
			 * "단문+장문"); hsExcelFieldName.put("37", "결과상태");
			 * hsExcelFieldName.put("38", "리마크"); hsExcelFieldName.put("39",
			 * "참고치");
			 */

			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				// if (rcvDate[i].equals("20101108") && rcvNo[i].equals("19517")
				// && inspectCode[i].equals("81399")) {
				// System.out.println("");
				// }
				// isData = true;
				String curDate = "";
				String curNo = "";

				// !
				data[0] = bdt[i]; // 걍~~
				data[1] = Common.cutZero(img[i]); // 내원번호
				data[2] = inc[i]; // 외래구분
				// data[3] = unit[i]; //의뢰일자
				data[3] = ""; // 의뢰일자

				// !
				data[4] = specNo[i].trim(); // 검체번호

				// !
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				}
				data[6] = clientInspectCode[i].trim(); // 병원검사코드(처방코드)
				data[7] = inspectName[i]; // 검사명(처방명)
				data[8] = hosSamp[i]; // 검체명(검체코드)

				// !
				// hsExcelFieldName.put("9", "일련번호");
				// hsExcelFieldName.put("10", "검체코드");
				// hsExcelFieldName.put("11", "여유필드");

				// !
				data[9] = bbseq[i]; // 일련번호
				data[10] = ""; // 검체코드
				data[11] = ""; // 여유필드

				// !
				data[12] = chartNo[i]; // 차트번호
				data[13] = patName[i]; // 수진자명
				data[14] = securityNo[i]; // 주민번호

				// !
				data[15] = age[i]; // 나이
				data[16] = sex[i]; // 성별
				data[17] = ""; // 과
				data[18] = ""; // 병동
				data[19] = ""; // 참고사항

				// !
				data[20] = ""; // 학부
				data[30] = "11370319"; // 요양기관번호
				if (hosCode[i].trim().equals("22001")) {
					data[21] = Common.getDateFormat(bdt[i]); // 검사완료일
				} else {
					data[21] = bdt[i]; // 검사완료일
				}
				try {
					data[22] = cutHrcvDateNumber(cns[i])[1]; // 처방일자
				} catch (Exception eee) {
					data[22] = ""; // 처방일자
				}

				// !
				data[23] = ""; // 혈액형
				data[24] = "";

				// !
				try {
					data[25] = getUintCut(unit[i])[2]; // 참고치단위
				} catch (Exception exx) {
					data[25] = ""; // 참고치단위
				}
				data[26] = ""; // 진료의사
				data[27] = ""; // 추가키1
				data[28] = ""; // 추가키2
				data[29] = "";

				// !
				data[30] = "11370319"; // 요양기관번호
				data[31] = rcvDate[i].trim(); // 접수일자
				data[32] = rcvNo[i].trim(); // 접수번호
				data[33] = inspectCode[i].trim(); // 검사코드
				data[34] = ""; // 단문결과

				// !
				data[35] = ""; // 문장결과
				data[36] = ""; // 수치+문장
				data[37] = ""; // 상태
				data[38] = rmkCode[i]; // 리마크
				data[39] = ""; // 참고치

				// !
				if (resultType[i].trim().equals("C")) {
					// if (inspectCode[i].trim().substring(0,
					// 5).equals("00095")) {
					// System.out.println("");
					// }
					data[34] = result[i]; // 문자결과
					data[36] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[35] = "";
					data[39] = getReferenceValue(remark);
					if (isMAST(inspectCode[i].trim().substring(0, 5))							&& (hosCode[i].trim().equals("12640") || hosCode[i]
									.trim().equals("22250"))) {
						Vector vmast = new Vector();
						data[34] = "";
						data[39] = "";
						data[35] = appendBlanks("검사항목", 26)
								+ appendBlanks("CLASS", 8)
								+ appendBlanks("검사항목", 25)
								+ appendBlanks("CLASS", 8);
						data[35] += getDivide() + "\r\n";
						data[36] = data[35];
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i].substring(0, 1), 8));
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],
										26)
										+ appendBlanks(result[i++].substring(0,
												1), 8));
								if (inspectCode[i].trim().substring(0, 5).equals("00673")||
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
						if (hosCode[i].trim().equals("22250")) {
							data[35] = getResultMAST(data[35].toString(), vmast)
									+ getDivide();
						} else {
							data[35] = getResultMAST(data[35].toString(), vmast)
									+ getDivide() + "\r\n" + getMastRemark();
						}
						data[36] = data[35];





					}				else	if (isMAST_Two(inspectCode[i].trim().substring(0, 5))							&& (hosCode[i].trim().equals("12640") || hosCode[i]
					 				    	                                                     							                                                 .trim().equals("22250"))) {
						Vector vmast = new Vector();
						data[34] = "";
						data[39] = "";
						data[35] = appendBlanks("검사항목", 26)
						+ appendBlanks("CLASS", 8)
						+ appendBlanks("검사항목", 25)
						+ appendBlanks("CLASS", 8);
						data[35] += getDivide() + "\r\n";
						data[36] = data[35];
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i].substring(0, 1), 8));
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],
										26)
										+ appendBlanks(result[i++].substring(0,
												1), 8));
								if (inspectCode[i].trim().substring(0,5).equals("00683")||//
									inspectCode[i].trim().substring(0,5).equals("00684")
									||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
									)
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
						if (hosCode[i].trim().equals("22250")) {
							data[35] = getResultMAST_Two(data[35].toString(), vmast)
							+ getDivide();
						} else {
							data[35] = getResultMAST_Two(data[35].toString(), vmast)
							+ getDivide() + "\r\n" + getMastRemark();
						}
						data[36] = data[35];

					} else if ((hosCode[i].trim().equals("22033") || hosCode[i]
							.trim().equals("22023"))
							&& (inspectCode[i].trim().equals("31010")
									|| inspectCode[i].trim().equals("31012") || inspectCode[i]
									.trim().equals("31059"))) {
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35];
						data[39] = ""; // 참고치
					} else if (inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("20720")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251") //
							|| inspectCode[i].trim().substring(0, 5).equals(
									"71252"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[34];
					} else if (hosCode[i].trim().equals("20720")) {
						if (result[i].trim().toUpperCase().indexOf("NEGATIVE") > -1) {
							data[34] = result[i].trim().substring(8) + " (음성)"; // 문자결과 NEGATIVE => 한글로
							data[36] = data[34];
						} else if (result[i].trim().toUpperCase().indexOf("POSITIVE") > -1) {
							data[34] = result[i].trim().substring(8) + " (양성)"; // 문자결과
							data[36] = data[34];
						}
					} else if ((hosCode[i].trim().equals("22250")
							&& inspectCode[i].trim().length() == 7 //
							&& inspectCode[i].trim().substring(0, 5).equals(
									"11026") //
							|| inspectCode[i].trim().substring(0, 5).equals(
									"00312") //
							|| inspectCode[i].trim().substring(0, 5).equals(
									"00313") //
							|| inspectCode[i].trim().substring(0, 5).equals(
									"00307") //
					|| inspectCode[i].trim().substring(0, 5).equals("00308"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					} else if (hosCode[i].trim().equals("22023")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"31001") || inspectCode[i].trim()
									.substring(0, 5).equals("31059"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					} else if (hosCode[i].trim().equals("12640")) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						// if (inspectCode[i].trim().substring(0,
						// 5).equals("31001")) {
						data[34] = result[i]; // 문자결과
						// } else {
						// data[34] = ""; //문자결과
						// }
						if (inspectCode[i].trim().substring(0, 5).equals(
								"31001")) {
							data[36] = "";
							data[35] = "";
						} else {
							data[36] = data[35];
						}
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
					} else if (inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("10780") || //
									hosCode[i].trim().equals("10781") || //
									hosCode[i].trim().equals("10782") || //
									hosCode[i].trim().equals("10783") || //
									hosCode[i].trim().equals("10784") || //
							hosCode[i].trim().equals("10785"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					} else if (hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[35];
					} else if ((hosCode[i].trim().equals("12770") && inspectCode[i]
							.trim().length() == 7)
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"11026")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00091")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00095") || inspectCode[i]
									.trim().substring(0, 5).equals("00804"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					} else if (inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("14279")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251") || inspectCode[i].trim()
									.substring(0, 5).equals("71252"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					} else if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[35];
					} else if (isMAST(inspectCode[i].trim().substring(0, 5))   
							&& !hosCode[i].trim().equals("21954")
							&& !hosCode[i].trim().equals("12640")) {
						Vector vmast = new Vector();
						data[34] = "";
						data[39] = "";
						data[35] = appendBlanks("검사항목", 26)
								+ appendBlanks("CLASS", 8)
								+ appendBlanks("검사항목", 25)
								+ appendBlanks("CLASS", 8);
						data[35] += getDivide() + "\r\n";
						data[36] = data[35];
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i].substring(0, 1), 8));
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],
										26)
										+ appendBlanks(result[i++].substring(0,
												1), 8));
								if (inspectCode[i].trim().substring(0, 5).equals("00673")||
										inspectCode[i].trim().substring(0, 5).equals("00683")||
										inspectCode[i].trim().substring(0, 5).equals("00684")||
										inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")||
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
						data[35] = getResultMAST(data[35].toString(), vmast)  
								+ getDivide() + "\r\n" + getMastRemark();
						data[36] = data[35];
						
					} else if (isMAST_Two(inspectCode[i].trim().substring(0, 5))
							&& !hosCode[i].trim().equals("21954")
							&& !hosCode[i].trim().equals("12640")) {
						Vector vmast = new Vector();
						data[34] = "";
						data[39] = "";
						data[35] = appendBlanks("검사항목", 26)
								+ appendBlanks("CLASS", 8)
								+ appendBlanks("검사항목", 25)
								+ appendBlanks("CLASS", 8);
						data[35] += getDivide() + "\r\n";
						data[36] = data[35];
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i].substring(0, 1), 8));
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],
										26)
										+ appendBlanks(result[i++].substring(0,
												1), 8));
								if (inspectCode[i].trim().substring(0,5).equals("00683")||//
									inspectCode[i].trim().substring(0,5).equals("00684")
									||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
									)
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
						data[35] = getResultMAST_Two(data[35].toString(), vmast)
								+ getDivide() + "\r\n" + getMastRemark();
						data[36] = data[35];		
						
					} else if (inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("22256")
							&& !hosCode[i].trim().equals("14279")
							&& ((inspectCode[i].trim().substring(0, 5).equals("71251") //
									|| inspectCode[i].trim().substring(0, 5)	.equals("71252") || inspectCode[i].trim().substring(0, 5).equals("00752")) || //
							(hosCode[i].trim().equals("22023") && (inspectCode[i]	.trim().substring(0, 5).equals("72059") || inspectCode[i]	.trim().substring(0, 5).equals("72018"))))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[35];
					} else if ((hosCode[i].trim().equals("22033") || hosCode[i]
							.trim().equals("12770"))
							&& inspectCode[i].trim().length() == 7) {
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 25) + "\t"
								+ appendBlanks(result[i], 15) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 25)
									+ "\t"
									+ appendBlanks(result[i], 15)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[35];
					} else if (hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31059") || inspectCode[i]
									.trim().equals("31019"))) {
						data[35] = result[i]; // 문자결과
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
					} else if (inspectCode[i].trim().length() == 7 && //
							((hosCode[i].trim().equals("19725") || hosCode[i]
									.trim().equals("21118"))
									|| //
									(hosCode[i].trim().equals("20974") && inspectCode[i]
											.trim().substring(0, 5).equals(
													"11026")) || //
									(hosCode[i].trim().equals("13931") && inspectCode[i]
											.trim().substring(0, 5).equals(
													"71251")) || //
									(hosCode[i].trim().equals("22262") && (inspectCode[i]
											.trim().substring(0, 5).equals(
													"11101") || inspectCode[i]
											.trim().substring(0, 5).equals(
													"31001"))) || //
									(hosCode[i].trim().equals("22256")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"88007")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"00091")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"00095") && (inspectCode[i]
											.trim().substring(0, 5).equals(
													"11101") || inspectCode[i]
											.trim().substring(0, 5).equals(
													"31001"))) || (hosCode[i]
									.trim().equals("13931") && inspectCode[i]
									.trim().substring(0, 5).equals("71251")))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					} else if (!hosCode[i].trim().equals("19725")
							&& (hosCode[i].trim().equals("13931")
									|| hosCode[i].trim().equals("19725") || hosCode[i].trim().equals("12770"))
							&& (inspectCode[i].trim().substring(0, 5).equals(	"00091")
									|| inspectCode[i].trim().substring(0, 5)	.equals("00095")
									|| inspectCode[i].trim().substring(0, 5)	.equals("00752") 
									|| inspectCode[i]	.trim().substring(0, 5).equals("72059")
									|| inspectCode[i]	.trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[35];
					} else if (hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("11101"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!inspectCode[i].trim().substring(5, 7).equals(
									"00")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt)
								break;
						}
						i--;
						data[36] = data[35];
					} else if (inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("22256")
							&& (hosCode[i].trim().equals("22262") && !inspectCode[i]
									.trim().equals("11101"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
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
						data[36] = data[35];
					}
				} else {
					if (hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31010")
									|| inspectCode[i].trim().equals("31012") || inspectCode[i]
									.trim().equals("31059"))) {
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35];
						data[39] = ""; // 참고치
					} else {
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35];
						data[39] = ""; // 참고치
					}
				}
				if (hosCode[i].trim().equals("22256")
						&& !data[35].trim().equals("")) {
					data[35] += "\r\n\r\n검체번호: " + data[4] + " [" + data[31]
							+ "-" + data[32] + "]";
				}
				data[37] = highLow[i]; // 결과상태
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[31].trim())
								|| !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();

							// !
							if (hosCode[i].trim().equals("22250")
									&& inspectCode[i].trim().substring(0, 5)
											.equals("11026"))
								data[35] = data[35]
										+ "\r\n"
										+ getReamrkValue(hosCode[i],
												rcvDate[i], rcvNo[i],
												rmkCode[i]);
							else
								data[38] = getReamrkValue(hosCode[i],
										rcvDate[i], rcvNo[i], rmkCode[i]);

							// data[38] = getReamrkValue(hosCode[i], rcvDate[i],
							// rcvNo[i], rmkCode[i]); //리마크
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				else
					remarkCode = "";
				if (!debug) {
					int lens = getExcelFieldNameArray().length;
					for (int k = 0; k < lens; k++) {
						label = new jxl.write.Label(k, row, data[Integer
								.parseInt(getExcelFieldArray()[k].toString())]);
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

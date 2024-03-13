package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.Vector;

import com.neodin.comm.Common;

import jxl.Workbook;

public class DownloadGTMC extends ResultDownload {
	boolean debug = false;

	boolean isData = true;

	//
	String gubun1 = "\n============================================================\n";

	String gubun2 = "\n------------------------------------------------------------\n";

	// private java.text.DecimalFormat df = new java.text.DecimalFormat(
	// "#######0.0");

	public DownloadGTMC() {
		initialize();
	}

	public DownloadGTMC(String id, String fdat, String tdat, Boolean isRewrite) {
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
	
	public String[] cutHrcvDateNumber2(String str) {
		String src_[] = new String[] { "", "" };
		if (str == null || src_.equals(""))
			return src_;

		src_ = Common.getDataCut(str, "D");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
			src_[0] = src_[0].substring(0, src_[0].length() - 1);
		} catch (Exception e) {
			src_[0] = "";
		}

		try {
			src_[1] = src_[1].replace(':', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		return src_;
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

			// !
			String data[] = new String[18];
			// String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";
			for (int i = 0; i < cnt; i++) {
				isData = true;
				String curDate = "";
				String curNo = "";
				data[0] = "11370319";
	//			data[17] = cns[i];
				
				//처방번호 구분자가 D인거 잘라서 생성
				try {
					data[17] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[17] = "";
				}    
				
				//처방번호 구분자 ^인거 잘라서 생성
				if (hosCode[i].trim().equals("30858")) {
					try {
						data[17] = cutHrcvDateNumber2(cns[i])[0]; // 처방번호
					} catch (Exception eee) {
						data[17] = ""; // 처방번호
					}
				}
				
				
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
				// 양산부산대병원
				if (inspectCode[i].trim().length() == 7
						&& (hosCode[i].trim().equals("20840") && (inspectCode[i].trim().equals("4137400") || inspectCode[i].trim().equals("4137402") || inspectCode[i]
								.trim().equals("4137403")))) {
					continue;
				}
				// 아이퍼스트병원
				if (inspectCode[i].trim().length() == 7
						&& (hosCode[i].trim().equals("20889") && (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i]
								.trim().equals("0009503")))) {
					continue;
				}
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				// !
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[15] = getReferenceValue(remark);
					data[12] = "";
					
					//20150818 최대열 : 리마크 코드 추가 21297
					if (hosCode[i].trim().equals("20788")
							&& (inspectCode[i].trim().substring(0, 5).equals("00804")||inspectCode[i].trim().substring(0, 5).equals("21297"))  ) {
						//data[11]="";
						data[12] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
					}
					
					//20160427 최대열 : 리마크 코드 삭제 요청
					if (hosCode[i].trim().equals("20840")
							&& (inspectCode[i].trim().substring(0, 5).equals("71297")) ) {
						//data[11]="";
//						data[12] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
//						System.out.println(data[12]);
					}
					
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("19239"))
							&& (inspectCode[i].trim().substring(0, 5).equals("41052") || inspectCode[i].trim().substring(0, 5).equals("41114") //
									|| inspectCode[i].trim().substring(0, 5).equals("41116") || inspectCode[i].trim().substring(0, 5).equals("41092") //
									|| inspectCode[i].trim().substring(0, 5).equals("41097") || inspectCode[i].trim().substring(0, 5).equals("41102") //
									|| inspectCode[i].trim().substring(0, 5).equals("41112") || inspectCode[i].trim().substring(0, 5).equals("41110") //
									|| inspectCode[i].trim().substring(0, 5).equals("41115") || inspectCode[i].trim().substring(0, 5).equals("00205") //
									|| inspectCode[i].trim().substring(0, 5).equals("00211") || inspectCode[i].trim().substring(0, 5).equals("00223") //
									|| inspectCode[i].trim().substring(0, 5).equals("00224") || inspectCode[i].trim().substring(0, 5).equals("00230") //
									|| inspectCode[i].trim().substring(0, 5).equals("00249") || inspectCode[i].trim().substring(0, 5).equals("00220") //
									|| inspectCode[i].trim().substring(0, 5).equals("81143") || inspectCode[i].trim().substring(0, 5).equals("41118") //
									|| inspectCode[i].trim().substring(0, 5).equals("41021") || inspectCode[i].trim().substring(0, 5).equals("41035") //
									|| inspectCode[i].trim().substring(0, 5).equals("41113") || inspectCode[i].trim().substring(0, 5).equals("81145") //
							|| inspectCode[i].trim().substring(0, 5).equals("81187"))) {
						data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						/*
						 * for (String thisTimeCode =
						 * inspectCode[i++].trim().substring(0, 5);
						 * thisTimeCode.
						 * equals(inspectCode[i].trim().substring(0, 5))&&
						 * curDate.equals(rcvDate[i].trim())&&
						 * curNo.equals(rcvNo[i].trim());) { if (++i == cnt || i
						 * > cnt) { break; } } i--;
						 */
					} else if (inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("12644"))
							&& (inspectCode[i].trim().equals("0000900") || inspectCode[i].trim().equals("0000902") || inspectCode[i].trim().equals(
									"0000903"))) {
						continue;
					}

					else if (inspectCode[i].trim().length() == 7 //
							&& (hosCode[i].trim().equals("12644") && (inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("72059") 
									|| inspectCode[i].trim().substring(0, 5).equals("72018") || inspectCode[i].trim().substring(0, 5).equals("71252")
									|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("21604")
									|| inspectCode[i].trim().substring(0, 5).equals("72227") || inspectCode[i].trim().substring(0, 5).equals("21683")
									|| inspectCode[i].trim().substring(0, 5).equals("21677") || inspectCode[i].trim().substring(0, 5).equals("72227")
									|| inspectCode[i].trim().substring(0, 5).equals("72228") || inspectCode[i].trim().substring(0, 5).equals("72229")
									|| inspectCode[i].trim().substring(0, 5).equals("72230") || inspectCode[i].trim().substring(0, 5).equals("72231")
									|| inspectCode[i].trim().substring(0, 5).equals("72232") || inspectCode[i].trim().substring(0, 5).equals("72233")
									|| inspectCode[i].trim().substring(0, 5).equals("72234") || inspectCode[i].trim().substring(0, 5).equals("72235")
									|| inspectCode[i].trim().substring(0, 5).equals("72236") || inspectCode[i].trim().substring(0, 5).equals("21068")
									|| inspectCode[i].trim().substring(0, 5).equals("72189") || inspectCode[i].trim().substring(0, 5).equals("72242")
									|| inspectCode[i].trim().substring(0, 5).equals("72182")))) {
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
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7 //
							&& (hosCode[i].trim().equals("20889") && inspectCode[i].trim().substring(0, 5).equals("11052"))) {
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
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7 //
							&& (hosCode[i].trim().equals("20639") && (inspectCode[i].trim().substring(0, 5).equals("71251")
									|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("71259")
									|| inspectCode[i].trim().substring(0, 5).equals("41374") || inspectCode[i].trim().substring(0, 5).equals("00307")))) {
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
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					}  else if (inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("18646") && (inspectCode[i].trim().substring(0, 5).equals("11052")
									|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("11301") || inspectCode[i].trim().substring(0, 5).equals("00322")
									|| inspectCode[i].trim().substring(0, 5).equals("21065") || inspectCode[i].trim().substring(0, 5).equals("71252")
									|| inspectCode[i].trim().substring(0, 5).equals("71245") || inspectCode[i].trim().substring(0, 5).equals("71325")
									|| inspectCode[i].trim().substring(0, 5).equals("21677") || inspectCode[i].trim().substring(0, 5).equals("81494")
									|| inspectCode[i].trim().substring(0, 5).equals("21068")
									))) {
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
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("19239")
							&& ((inspectCode[i].trim().substring(0, 5).equals("71245")) || (inspectCode[i].trim().substring(0, 5).equals("00083"))
									|| (inspectCode[i].trim().substring(0, 5).equals("71252"))
									|| (inspectCode[i].trim().substring(0, 5).equals("11052"))
									|| (inspectCode[i].trim().substring(0, 5).equals("71259")) || (inspectCode[i].trim().substring(0, 5)
										.equals("00304")))) {
						data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15] + "   "
								+ getResultHBV(inspectCode[i].trim());
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("24183")
							&& (inspectCode[i].trim().substring(0, 5).equals("11101"))) {
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
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7
							&& ((hosCode[i].trim().equals("14288") && (!inspectCode[i].trim().substring(0, 5).equals("31001"))))
							&& ((hosCode[i].trim().equals("19239") 
									&& !inspectCode[i].trim().substring(0, 5).equals("00083")
									&& !inspectCode[i].trim().substring(0, 5).equals("71209") //
							&& !inspectCode[i].trim().substring(0, 5).equals("71251"))) && !inspectCode[i].trim().substring(0, 5).equals("00091")
							&& !inspectCode[i].trim().substring(0, 5).equals("00095") && !inspectCode[i].trim().substring(0, 5).equals("00752")
							&& !inspectCode[i].trim().substring(0, 5).equals("11101") && !inspectCode[i].trim().substring(0, 5).equals("21061")
							&& !inspectCode[i].trim().substring(0, 5).equals("31051") && !inspectCode[i].trim().substring(0, 5).equals("11052")
							&& !inspectCode[i].trim().substring(0, 5).equals("11026")
							&& !inspectCode[i].trim().substring(0, 5)	.equals("71297")) {

						data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					} 
					//20190403 서울아동병원30858 때문에 72245 장문으로 처리 다른 병원 컴플레인 발생시 서울아동병원만 따로 예외 처리해아함
					else if ((hosCode[i].trim().equals("26616")||hosCode[i].trim().equals("28330")||hosCode[i].trim().equals("33370")
							||hosCode[i].trim().equals("30858")||hosCode[i].trim().equals("12754"))
							&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("72182")||inspectCode[i].trim().substring(0, 5).equals("72183")
									||inspectCode[i].trim().substring(0, 5).equals("72189")||inspectCode[i].trim().substring(0, 5).equals("72242")
									||inspectCode[i].trim().substring(0, 5).equals("72245")||inspectCode[i].trim().substring(0, 5).equals("00690")
									||inspectCode[i].trim().substring(0, 5).equals("00691"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
								0, 5))
								&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					}else if (hosCode[i].trim().equals("19920")
							&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i]
									.trim().substring(0, 5).equals("00752"))) {
						if (!inspectCode[i].trim().substring(5, 7).equals("00")) {
							data[12] = "";
							data[11] = result[i];
						} else {
							continue;
						}
					} else if (!hosCode[i].trim().equals("15298")) {
						if (hosCode[i].trim().equals("22015") && (inspectCode[i].trim().substring(0, 5).equals("00743"))) {
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							if (inspectCode[i].trim().substring(5, 7).equals("00")) {
								data[12] = "";
								data[11] = "별지참조";
							}
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								if (inspectCode[i].trim().substring(5, 7).equals("00")) {
									data[12] = "";
									data[11] = "별지참조";
								}
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						}
						if (inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("19920"))) {
							data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (!hosCode[i].trim().equals("20788")&&!hosCode[i].trim().equals("20889")
								&& (inspectCode[i].trim().substring(0, 5).equals("72059") 
										|| inspectCode[i].trim().substring(0, 5).equals("72018")
										|| inspectCode[i].trim().substring(0, 5).equals("71298")
										|| inspectCode[i].trim().substring(0, 5).equals("72182")
										)) {
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								if (inspectCode[i].trim().substring(5, 7).equals("01")) {
									data[12] = "";
									data[11] = result[i];
								}
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (inspectCode[i].trim().length() == 7 && //
								(hosCode[i].trim().equals("19159") && (inspectCode[i].trim().substring(0, 5).equals("31001")
										|| inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5)
										.equals("11052")))) {

							data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
							//20170913 어울림병원 단문->장문 양태용
						}  else if (inspectCode[i].trim().length() == 7 && //
								(hosCode[i].trim().equals("31079") && 
										(inspectCode[i].trim().substring(0, 5).equals("11301")
										|| inspectCode[i].trim().substring(0, 5).equals("31001") 
										|| inspectCode[i].trim().substring(0, 5).equals("11052")
										|| inspectCode[i].trim().substring(0, 5).equals("72227") 
										|| inspectCode[i].trim().substring(0, 5).equals("72228")
										|| inspectCode[i].trim().substring(0, 5).equals("72229") 
										|| inspectCode[i].trim().substring(0, 5).equals("72230")
										|| inspectCode[i].trim().substring(0, 5).equals("72231") 
										|| inspectCode[i].trim().substring(0, 5).equals("72232")
										|| inspectCode[i].trim().substring(0, 5).equals("72233") 
										|| inspectCode[i].trim().substring(0, 5).equals("72234")
										|| inspectCode[i].trim().substring(0, 5).equals("72235") 
										|| inspectCode[i].trim().substring(0, 5).equals("72236")
										|| inspectCode[i].trim().substring(0, 5).equals("72237") 
										|| inspectCode[i].trim().substring(0, 5).equals("72238")
										|| inspectCode[i].trim().substring(0, 5).equals("00301")
										|| inspectCode[i].trim().substring(0, 5).equals("11302")
										|| inspectCode[i].trim().substring(0, 5).equals("00690")
										|| inspectCode[i].trim().substring(0, 5).equals("00691")))) {

							data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} 
						
						 else if (inspectCode[i].trim().length() == 7 && //
									(hosCode[i].trim().equals("31079") && 
											( inspectCode[i].trim().substring(0, 5).equals("00309")
											|| inspectCode[i].trim().substring(0, 5).equals("00323")))) {

								data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
								data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
								data[11] = "";
								data[15] = "";
								curDate = rcvDate[i];
								curNo = rcvNo[i];
								for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
										0, 5))
										&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
									data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
											+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
									if (++i == cnt || i > cnt)
										break;
								}
								i--;
								//! 리마크 추가 부분
								data[12] = data[12].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"\r\n";
							} 
						
						else if (inspectCode[i].trim().length() == 7
								&& hosCode[i].trim().equals("20788")
								&& (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("11052") 
										|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("72182") 
										|| inspectCode[i].trim().substring(0, 5).equals("72261") || inspectCode[i].trim().substring(0, 5).equals("72242"))) {

							data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (inspectCode[i].trim().length() == 7
								&& hosCode[i].trim().equals("20788")&& (inspectCode[i].trim().substring(0, 5).equals("72185")||inspectCode[i].trim().substring(0, 5).equals("72241")
										||inspectCode[i].trim().substring(0, 5).equals("71259")||inspectCode[i].trim().substring(0, 5).equals("71245")
										||inspectCode[i].trim().substring(0, 5).equals("00901")||inspectCode[i].trim().substring(0, 5).equals("72194")
										||inspectCode[i].trim().substring(0, 5).equals("21677")
										)) {

							data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
							data[12] = data[12] +"\r\n\r\n"+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);;
						}else if (inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("19725"))
								&& (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("11052"))) {

							data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (hosCode[i].trim().equals("16702") && inspectCode[i].trim().substring(0, 5).equals("71258")) {

							data[12] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (!hosCode[i].trim().equals("22015") && isMAST(inspectCode[i].trim().substring(0, 5))) {

							Vector vmast = new Vector();
							data[11] = "";
							data[15] = "";
							data[12] = appendBlanks("검사항목", 26) + appendBlanks("CLASS", 8) + appendBlanks("검사항목", 25) + appendBlanks("CLASS", 8);
							data[12] += "\r\n";
							// data[12] += "\r\n" + appendBlanks(inspectName[i],
							// 26) + appendBlanks(result[i], 8);
							vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i].substring(0, 1), 8));
							// data[12] += "\r\n";
							// !
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								try {
									vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i++].substring(0, 1), 8));
									if (inspectCode[i].trim().substring(0,5).equals("00673")||
									    inspectCode[i].trim().substring(0,5).equals("00674"))
										vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i++].substring(0, 1), 8));
									else
										break;
								} catch (Exception e) {
								}
								if (i >= cnt)
									break;
							}
							i--;
							data[12] = getResultMAST(data[12].toString(), vmast) + "\r\n" + getMastRemark();
						} else if (!hosCode[i].trim().equals("22015") && isMAST_Two(inspectCode[i].trim().substring(0, 5))) {

							Vector vmast = new Vector();
							data[11] = "";
							data[15] = "";
							data[12] = appendBlanks("검사항목", 26) + appendBlanks("CLASS", 8) + appendBlanks("검사항목", 25) + appendBlanks("CLASS", 8);
							data[12] += "\r\n";
							// data[12] += "\r\n" + appendBlanks(inspectName[i],
							// 26) + appendBlanks(result[i], 8);
							vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i].substring(0, 1), 8));
							// data[12] += "\r\n";
							// !
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								try {
//									vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i++].substring(0, 1), 8));
									if ( inspectCode[i].trim().substring(0, 5).equals("00683")||
									    inspectCode[i].trim().substring(0, 5).equals("00684")
									    ||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
									    )
										
										if(isMastDuplPrint(inspectCode[i])){
											i++;
										}else{
											vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i++].substring(0, 1), 8));
										}
									else
										break;
								} catch (Exception e) {
								}
								if (i >= cnt)
									break;
							}
							i--;
							data[12] = getResultMAST_Two(data[12].toString(), vmast) + "\r\n" + getMastRemark();

		
						} else if (inspectCode[i].trim().length() == 7
								&& !hosCode[i].trim().equals("14288")
								&& (hosCode[i].trim().equals("19307") || hosCode[i].trim().equals("20639") || hosCode[i].trim().equals("19635")
										|| hosCode[i].trim().equals("26637") || hosCode[i].trim().equals("20788"))
								&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i]
										.trim().substring(0, 5).equals("00752"))) {
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								if (inspectCode[i].trim().substring(5, 7).equals("01")) {
									data[12] = "";
									data[11] = result[i];
								}
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (inspectCode[i].trim().length() == 7
								&& !hosCode[i].trim().equals("14288")
								&& ((hosCode[i].trim().equals("19635") || hosCode[i].trim().equals("26637")) 
										&& (!inspectCode[i].trim()	.substring(0, 5).equals("00083") && !inspectCode[i].trim().substring(0, 5).equals("00045")))
								&& (hosCode[i].trim().equals("19159") || hosCode[i].trim().equals("21123") || hosCode[i].trim().equals("19758")|| hosCode[i].trim().equals("15617"))
								&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("00095")
										|| inspectCode[i].trim().substring(0, 5).equals("00752")	|| inspectCode[i].trim().substring(0, 5).equals("11101") 
										|| inspectCode[i].trim().substring(0, 5)	.equals("21061") ||  !inspectCode[i].trim().substring(0, 5).equals("71297"))) {
							data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (inspectCode[i].trim().length() == 7
								&& !hosCode[i].trim().equals("14288")
								&& ((hosCode[i].trim().equals("19635") || hosCode[i].trim().equals("26637")) && !inspectCode[i].trim()
										.substring(0, 5).equals("00083")) && !hosCode[i].trim().equals("22015")
								&& !inspectCode[i].trim().substring(0, 5).equals("00091") && !inspectCode[i].trim().substring(0, 5).equals("00095")
								&& !inspectCode[i].trim().substring(0, 5).equals("00752") && !inspectCode[i].trim().substring(0, 5).equals("11101")
								&& !inspectCode[i].trim().substring(0, 5).equals("21061") && !inspectCode[i].trim().substring(0, 5).equals("00405")
								&&  !inspectCode[i].trim().substring(0, 5).equals("71297")) {

							data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						} else if (inspectCode[i].trim().length() == 7
								&& ((hosCode[i].trim().equals("14288") && !inspectCode[i].trim().substring(0, 5).equals("31001")))
								&& !inspectCode[i].trim().substring(0, 5).equals("00091")
								&& !inspectCode[i].trim().substring(0, 5).equals("00095") && !inspectCode[i].trim().substring(0, 5).equals("00752")
								&& !inspectCode[i].trim().substring(0, 5).equals("11101") && !inspectCode[i].trim().substring(0, 5).equals("21061")
								&& !inspectCode[i].trim().substring(0, 5).equals("31051") && !inspectCode[i].trim().substring(0, 5).equals("11052")
								&& !inspectCode[i].trim().substring(0, 5).equals("11026") &&  !inspectCode[i].trim().substring(0, 5).equals("71297")) {
							data[12] = appendBlanks("검  사  명 ", 25) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
							data[11] = "";
							data[15] = "";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
									0, 5))
									&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim();
								if (++i == cnt || i > cnt)
									break;
							}
							i--;
						}
					} else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("15298")
							&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("11101"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("01")) {
								data[12] = "";
								data[11] = result[i];
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					}
					
				} else
				{
					
					data[11] = "";
					data[15] = "";
					data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					
					// 조직 면허번호 짤리는 문제위해 임의로 전문의 이름만 출력 되도록 수정
					//int str_index = data[12].indexOf("전문의");
					//if (str_index > 10) {
					//	data[12] = data[12].substring(0, str_index + 7);
					//}
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
								||inspectCode[i].trim().equals("31126")||inspectCode[i].trim().equals("31127")
								||inspectCode[i].trim().equals("31128"))) {
					data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//	data[15] = data[12].trim();
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001"))
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[12] = data[12] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
						//	data[15] = data[12].trim();
							i++;
							// culture_flag = true;
						} else {
							data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						//	data[15] = data[12].trim();
						}
					} catch (Exception e) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					//	data[15] = data[35].trim();
					}
				}



				if (inspectCode[i].trim().substring(0, 5).equals("71244")) {
					data[12] += "\r\n" + YMDD();
				}
				
				if (hosCode[i].trim().equals("18646") && inspectCode[i].trim().equals("31059")) {
					data[12] = data[11];
					data[11] = "";
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

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public String YMDD() {
		String str = "";
		// str += "▣ Specimen : 비인후 또는 인후 도찰물\r\n";
		// str += "▣ Methods : Real-time RT-PCR(실시간역전사중합효소연쇄반응법) \r\n";
		// str += "\r\n";
		// str += "1. 환자의 검체로부터 RNA를 추출합니다.\r\n";
		// str += "2. 각 영역에서 특이한 primer를 사용하여 RT-PCR을 실시합니다.\r\n";
		// str += " - Influenza A (H1N1, 신종플루) - Hemagglutinin(HA)\r\n";
		// str += " - Common Influenza A - Matrix protein(MP)\r\n";
		// str += " - Influenza A(H1N1, 계절성) - Hemagglutinin(HA)\r\n";
		// str += " - Influenza A(H3N2, 계절성) - Hemagglutinin(HA)\r\n";
		// str += "\r\n";
		// str += "3. 증폭된 PCR 산물을 각각의 specific probe와 hybridization 시킵니다. \r\n";
		// str += " -Influenza A(H1N1, 신종플루)-specific probe(5'-FAM;
		// 3'-BHQ1)\r\n";
		// str += " -Common Influenza A- specific probe(5'-FAM; 3'-BHQ1)\r\n";
		// str += " -Influenza A(H1N1, 계절성) -specific probe(5'-FAM;
		// 3'-TAMRA)\r\n";
		// str += " -Influenza A(H3N2, 계절성) -specific probe(5'-HEX;
		// 3'-BHQ1)\r\n";
		// str += "\r\n";
		// str += "4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의
		// report dye로부터 방출되는 \r\n";
		// str += " 형광을 실시간으로 측정합니다.\r\n";
		// str += "\r\n";
		// str += "▣ Remark \r\n";
		// str += "\r\n";
		// str += "1. 본 검사는 realtime RT-PCR 원리를 이용하여 신종인플루엔자 및 계절성 인플루엔자에 특이한
		// \r\n";
		// str += " primer를 각각 사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다.\r\n";
		// str += "\r\n";
		// str += "2. 계절성 Influenza A 의 검사는 Influenza A (H1N1, 신종플루)에서 음성,
		// Common Influenza A 에서 양성인\r\n";
		// str += " 경우에 한하여 2차 검사로 진행됩니다. \r\n";
		// str += "\r\n";
		// str += "3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검
		// 바랍니다.\r\n";
		// return str;
		str += "현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘 (Lamivudine) 은 HBV 중합효소 (polymerase) 의 합성을 억제하여\r\n";
		str += "바이러스 유전자 증폭을 차단하는 역할을 합니다. 그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의\r\n";
		str += "출현확률이 높아지게 되고 결국에는 치료의 실패내지는 재발로 이어지는 문제점을 나타냅니다.\r\n";
		str += "이러한 변이형은 HBV 중합효소 (polymerase)내의 codon552 과 codon528 염기서열의 돌연변이로 주로 발생하게 되는데\r\n";
		str += "본 검사는 HBV 중합효소(polymerase)의 특정부위를 증폭한 후 염기서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\r\n";
		str += "따라서 이러한 변이형의 발견은 치료경과 관찰, Viral breakthrough 의 조기발견 및 치료방침을 결정하는데 유용하게 \r\n";
		str += "사용될 수 있습니다.";
		return str;
		// return null;
	}
}

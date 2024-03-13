package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.sql.Timestamp;
import java.util.Vector;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;

public class DownloadGUFOSUNG extends ResultDownload {
	boolean debug = false;

	boolean isData = true;

	//
	String gubun1 = "\n============================================================\n";

	String gubun2 = "\n------------------------------------------------------------\n";

	// private java.text.DecimalFormat df = new java.text.DecimalFormat(
	// "#######0.0");

	public DownloadGUFOSUNG() {
		initialize();
	}

	public DownloadGUFOSUNG(String id, String fdat, String tdat, Boolean isRewrite) {
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

	public void makeDownloadFile() {
		row = 2;
		row2 = 1;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				//label = new jxl.write.Label(0, 0, "(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				//wbresult.addCell(label);
				ArraryResult = (new String[] { "⊙","NO", "바코드번호", "Name", "성별/나이", "CharNo", "진료과/병실", "의뢰일자", "접수일자", "검사코드",  "검사명", "결과" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
				
				wbresult.setColumnView(0, 5);
				wbresult.setColumnView(1, 5);
				wbresult.setColumnView(2, 10);
				wbresult.setColumnView(6, 1);
				wbresult.setColumnView(7, 10);
				wbresult.setColumnView(8, 1);
				wbresult.setColumnView(9, 10);
				wbresult.setColumnView(10, 5);
				wbresult.setColumnView(11, 47);
				//wbresult.setColumnView(12, 1);
				//wbresult.setColumnView(13, 1);
				
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
			String bdt[] = (String[]) getDownloadData().get("검사완료일");

			String bgcd[] = (String[]) getDownloadData().get("보험코드");
			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");
			String inc[] = (String[]) getDownloadData().get("외래구분");
			
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
				data[0] = "⊙";
				data[1] = seq[i].trim();
				data[2] = specNo[i].trim();
				data[3] = patName[i];
				//String[] tempAge = age[i].toString().split(".");
				String[] tempAge = age[i].toString().split("\\.");
				if(tempAge!=null && tempAge.length>0){
					data[4] = sex[i]+"/"+tempAge[0];	
				}else{
					data[4] = sex[i]+"/";
				}
				
				data[5] = chartNo[i];
				data[6] = inc[i];//외례구분
				
				data[8] = rcvDate[i].trim();//접수일자
				data[9] = clientInspectCode[i].trim();//inspectCode[i].trim();
				data[10] = inspectName[i].trim();
				
//				data[12] = "SEGN";
//				data[17] = cns[i];
//				data[14] = " ";
//				data[16] = securityNo[i];
		
				// !
				
				

				
				
				if (resultType[i].trim().equals("C")) {
					data[11] = "";
					data[12] = "";
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[15] = getReferenceValue(remark);
					//data[12] = "";
					


					if (inspectCode[i].trim().length() == 7 && (hosCode[i].toString().trim().equals("31154")  
							&& (inspectCode[i].trim().substring(0, 5).equals( "21380")|| inspectCode[i].trim().substring(0, 5).equals( "72182")
									|| inspectCode[i].trim().substring(0, 5).equals( "72227")|| inspectCode[i].trim().substring(0, 5).equals( "00687") 
									|| inspectCode[i].trim().substring(0, 5).equals( "00688")|| inspectCode[i].trim().substring(0, 5).equals( "00689")
									|| inspectCode[i].trim().substring(0, 5).equals( "11301")|| inspectCode[i].trim().substring(0, 5).equals( "81148")
									|| inspectCode[i].trim().substring(0, 5).equals( "72241")
									|| inspectCode[i].trim().substring(0, 5).equals( "21264")|| inspectCode[i].trim().substring(0, 5).equals( "00628")
									|| inspectCode[i].trim().substring(0, 5).equals( "00950")|| inspectCode[i].trim().substring(0, 5).equals( "81469")
									|| inspectCode[i].trim().substring(0, 5).equals( "00948")|| inspectCode[i].trim().substring(0, 5).equals( "31001")
									|| inspectCode[i].trim().substring(0, 5).equals( "81494")|| inspectCode[i].trim().substring(0, 5).equals( "11052")
									|| inspectCode[i].trim().substring(0, 5).equals( "05495")|| inspectCode[i].trim().substring(0, 5).equals( "72185")
									|| inspectCode[i].trim().substring(0, 5).equals( "72189")|| inspectCode[i].trim().substring(0, 5).equals( "21068")
									|| inspectCode[i].trim().substring(0, 5).equals( "71246")
									))) {
						data[12] = appendBlanks("검  사  명 ", 25) + "    " + appendBlanks("결  과", 21) + "    " + "참  고  치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "    " + appendBlanks(result[i], 21) + "    " + data[15].replaceAll("\\p{Z}", "");
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "    " + appendBlanks(result[i], 21) + "    "+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim().replaceAll("\\p{Z}", "");
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						try {//20160311 리마크 추가 요청 - 최대열
							data[12] += "\r\n" +getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);	
						} catch (Exception e) {	}
						
					}
					
					if (inspectCode[i].trim().length() == 7 && !hosCode[i].toString().trim().equals("31154") 
							&& ((inspectCode[i].trim().substring(0, 5).equals("11002")|| inspectCode[i].trim().substring(0, 5).equals("72185")
									|| inspectCode[i].trim().substring(0, 5).equals("72182") || inspectCode[i].trim().substring(0, 5).equals("72189")
									|| inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("72227")
									|| inspectCode[i].trim().substring(0, 5).equals("71245") || inspectCode[i].trim().substring(0, 5).equals("72231")
									|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("72229")
									|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("71311")
									|| inspectCode[i].trim().substring(0, 5).equals("71297") || inspectCode[i].trim().substring(0, 5).equals("72241")
									))) {
						data[12] = appendBlanks("검  사  명 ", 25) + "    " + appendBlanks("결  과", 21) + "    " + "참  고  치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "    " + appendBlanks(result[i], 21) + "    " + data[15].replaceAll("\\p{Z}", "");
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "    " + appendBlanks(result[i], 21) + "    "+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim().replaceAll("\\p{Z}", "");
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						try {//20160311 리마크 추가 요청 - 최대열
							data[12] += "\r\n" +getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);	
						} catch (Exception e) {	}
						
					}
					

					if ( !hosCode[i].toString().trim().equals("31154") && inspectCode[i].trim().substring(0, 5).equals("21613")||inspectCode[i].trim().substring(0, 5).equals("72242")) {
						data[12] = appendBlanks("검  사  명 ", 25) + "    " + appendBlanks("결  과", 21) + "    " + "참  고  치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "    " + appendBlanks(result[i], 21) + "    " + data[15].replaceAll("\\p{Z}", "");
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n" + appendBlanks(inspectName[i], 25) + "    " + appendBlanks(result[i], 21) + "    "+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim().replaceAll("\\p{Z}", "");
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						try {//20160311 리마크 추가 요청 - 최대열
							data[12] += "\r\n" +getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);	
						} catch (Exception e) {	}
						
					}

					
					 if (isMAST(inspectCode[i].trim().substring(0, 5))) {

						Vector vmast = new Vector();
						data[11] = "";
						data[15] = "";
						data[12] = appendBlanks("검사항목", 26) + appendBlanks("CLASS", 8) + appendBlanks("검사항목", 25) + appendBlanks("CLASS", 8);
						data[12] += "\r\n";
						vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i].substring(0, 1), 8));
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i++].substring(0, 1), 8));
								if (inspectCode[i].trim().substring(0, 5).equals("00673")||//
									inspectCode[i].trim().substring(0, 5).equals("00674"))
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
					}  if (isMAST_Two(inspectCode[i].trim().substring(0, 5))) {

						Vector vmast = new Vector();
						data[11] = "";
						data[15] = "";
						data[12] = appendBlanks("검사항목", 26) + appendBlanks("CLASS", 8) + appendBlanks("검사항목", 25) + appendBlanks("CLASS", 8);
						data[12] += "\r\n";
						vmast.addElement(appendBlanks(inspectName[i], 26) + appendBlanks(result[i].substring(0, 1), 8));
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							try {
								if (	inspectCode[i].trim().substring(0, 5).equals("00683")||
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
					} 

				}else {
					data[11] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				}

				if (inspectCode[i].trim().substring(0, 5).equals("71244")) {
					data[12] += "\r\n" + YMDD();
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
								||inspectCode[i].trim().equals("31126"))) {
					
					data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001"))
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[12] = data[12] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
							i++;
							data[11] = data[12];
						} else {
							data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[11] = data[12];
						}
					} catch (Exception e) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[11] = data[12];
					}
				}
				
				//20190220 양태용 중간결과 제외 처리
				if (hosCode[i].trim().equals("27938") && (inspectCode[i].trim().substring(0, 5).equals( "31107") || inspectCode[i].trim().substring(0, 5).equals( "31108")
						||inspectCode[i].trim().substring(0, 5).equals( "31109") || inspectCode[i].trim().substring(0, 5).equals( "31110")
						||inspectCode[i].trim().substring(0, 5).equals( "31111") || inspectCode[i].trim().substring(0, 5).equals( "31115")
						||inspectCode[i].trim().substring(0, 5).equals( "31116") || inspectCode[i].trim().substring(0, 5).equals( "31118")
						||inspectCode[i].trim().substring(0, 5).equals( "31119") || inspectCode[i].trim().substring(0, 5).equals( "31120")
						||inspectCode[i].trim().substring(0, 5).equals( "31126") )) {
					
					if(data[12].indexOf("중　간　결　과　보　고")>=0)
						
					{
						continue;
					}
					
				}
				
				
				//20200601 양태용 예외 처리 진행
				if (hosCode[i].toString().trim().equals("31154") &&
						("중　간　결　과　보　고".equals(result[i]))) {
					continue;
				}

				
				
				if (hosCode[i].toString().trim().equals("31154")
						&& (inspectCode[i].trim().substring(0, 5).equals( "00690") || inspectCode[i].trim().substring(0, 5).equals( "00691")
						)) {
					data[12] = "별지보고";
					}
				
				

				

				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim()) || !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("11052"))
								data[12] = data[12] + "\r\n" + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							else if (hosCode[i].trim().equals("16523") && inspectCode[i].trim().substring(0, 5).equals("41360"))
								data[14] = "리마크참조";
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
					if(inspectCode[i].trim().length() == 7 && data[12]!=null&& !"".equals(data[12])){
						data[11] = data[12];
						data[12]="";
					}
					
					if(data[2].length()>6){
						data[7] = "20"+data[2].toString().substring(0, 6);//의뢰일자	;	
					}else{
						data[7]="";
					}
					
					data[14] = "";//remark 삭제
					
					if(hosCode[i].trim().equals("27938")){
						data[12] = data[15];
						data[15]="";
					}
					else{
					data[15] = "";//remark 삭제
					}
					
					data[16] = highLow[i];
					if(hosCode[i].trim().equals("27938")){
						data[13] = data[16];
						data[16]="";
					}
					else{
					data[16] = "";//remark 삭제
					}
					
					if(hosCode[i].trim().equals("31154")  || hosCode[i].trim().equals("33374") ){
					//	data[11] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[12] = getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] });
					}
					
					WritableCellFormat gehang= new WritableCellFormat();
					gehang.setWrap(true); 
					WritableCellFormat centerBoldFormat= new WritableCellFormat();
					centerBoldFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 

					for (int k = 0; k < 16; k++) {
						if(k==11){
							label = new jxl.write.Label(k, row-1, data[k],gehang);
						}else{
							label = new jxl.write.Label(k, row-1, data[k],centerBoldFormat);	
						}
						
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
			_ex.printStackTrace();
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
		str += "현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘 (Lamivudine) 은 HBV 중합효소 (polymerase) 의 합성을 억제하여\r\n";
		str += "바이러스 유전자 증폭을 차단하는 역할을 합니다. 그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의\r\n";
		str += "출현확률이 높아지게 되고 결국에는 치료의 실패내지는 재발로 이어지는 문제점을 나타냅니다.\r\n";
		str += "이러한 변이형은 HBV 중합효소 (polymerase)내의 codon552 과 codon528 염기서열의 돌연변이로 주로 발생하게 되는데\r\n";
		str += "본 검사는 HBV 중합효소(polymerase)의 특정부위를 증폭한 후 염기서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\r\n";
		str += "따라서 이러한 변이형의 발견은 치료경과 관찰, Viral breakthrough 의 조기발견 및 치료방침을 결정하는데 유용하게 \r\n";
		str += "사용될 수 있습니다.";
		return str;
	}
}

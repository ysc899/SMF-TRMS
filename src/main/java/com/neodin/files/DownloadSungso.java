package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

public class DownloadSungso extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadSungso() {
		// isDebug = false;
		initialize();
	}
	
	

	public DownloadSungso(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}


	public DownloadSungso(String id, String fdat, String tdat,
		Boolean isRewrite, String hakCd) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		setHakcd(hakCd);
		initialize();
	}
	public void closeDownloadFile() {
		if (!isDebug) {
			try {
				workbook.write();
			} catch (Exception e) {
				// e.printStackTrace();
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
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

	// boolean isData = false;
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

	public void makeDownloadFile() {
		{
			row = 1;
//			row2 = 1;
			if (!isDebug) {
				try {
					workbook = Workbook.createWorkbook(new File(savedir
							+ makeOutFile()));
					wbresult = workbook.createSheet("Result", 0);
					String ArraryResult[] = null;					
										
					
					ArraryResult = (new String[] { "접수일자", "기관코드", "차트번호",
							"검체번호", "이름", "처방코드", "결과값(단문)", "검사명", "Column8",
							"이미지여부", "이미지파일명", "Column11", "Column12", "Column13", "Column14",
							"Column15", "Column16", "Column17" , "서술결과", "리마크"});
					
					
					
					for (int i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 0, ArraryResult[i]);
						wbresult.addCell(label);
					}
				} catch (Exception exception) {
					// System.out.println("OCS 파일쓰기 스레드 오류" +
					// exception.getMessage());
					// exception.printStackTrace();
				}
			}
		}
	}

	public void processingData(int cnt) {
		try {
			String[] hosCode = (String[]) getDownloadData().get("병원코드"); // 병원코드
			String[] rcvDate = (String[]) getDownloadData().get("접수일자");
			String[] rcvNo = (String[]) getDownloadData().get("접수번호");
			String[] specNo = (String[]) getDownloadData().get("검체번호");
			String[] chartNo = (String[]) getDownloadData().get("차트번호");
			String[] patName = (String[]) getDownloadData().get("수신자명"); //
			String[] inspectCode = (String[]) getDownloadData().get("검사코드"); //
			String[] inspectName = (String[]) getDownloadData().get("검사명"); //
			String[] seq = (String[]) getDownloadData().get("일련번호"); //
			String[] result = (String[]) getDownloadData().get("결과"); //
			String[] resultType = (String[]) getDownloadData().get("결과타입"); // 문자인자 문장인지
			String[] clientInspectCode = (String[]) getDownloadData().get("병원검사코드"); //
			String[] sex = (String[]) getDownloadData().get("성별");
			String[] age = (String[]) getDownloadData().get("나이");
			String[] securityNo = (String[]) getDownloadData().get("주민번호");
			String[] highLow = (String[]) getDownloadData().get("결과상태");
			String[] lang = (String[]) getDownloadData().get("언어");
			String[] history = (String[]) getDownloadData().get("이력");
			String[] rmkCode = (String[]) getDownloadData().get("리마크코드");
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
			String bdt2[] = (String[]) getDownloadData().get("요양기관번호");
			String data[] = new String[20];
//			String[] _tmp = new String[3];
			String remark[] = new String[5];
			String remarkCode = "";
//			int index__ = 0;
			Vector vmast = new Vector();
			String lastData = "";
			int lastindex = 0;
			boolean isNext = false;
		
//			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
//				lastData = inspectCode[399].trim().substring(0, 5);
//				lastindex = 399;
//				isNext = true;
//
//				while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
//					cnt--;
//					if (inspectCode[lastindex].trim().substring(5).equals("00")) {
//						cnt--;
//					}
//				}
//			}
			
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
				isNext = true;

				while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
					cnt--;
					if (inspectCode[lastindex].trim().substring(5).equals("00")) {
						cnt--;
					}
				}
				
				if(inspectCode[399].trim().substring(5).equals("00")){
					cnt--;
				}
			}
			for (int i = 0; i < cnt; i++) {
				
				String curDate = "";
				String curNo = "";
				data[0] = rcvDate[i].trim();
				if(hosCode[i].trim().equals("25169")){
					data[1] = "016";
				}else if(hosCode[i].trim().equals("30418")){
					data[1] = "121";
				} else{
					data[1] = "203";
				}
				data[2] = chartNo[i];
				data[3] = specNo[i].trim();
				data[4] = patName[i];
				data[5] = clientInspectCode[i].trim();
				data[7] = inspectName[i];
				data[8] = "";
				if(hosCode[i].trim().equals("25169") || hosCode[i].trim().equals("28029")|| hosCode[i].trim().equals("30418")){
					data[9] = "";
				}else{
					data[9] = "Y";
				}
				data[10] = "";
				data[11] = "";
				data[12] = "";
				data[13] = "";
				data[14] = "";
				data[15] = "";
				data[16] = "";
				data[17] = "";
				data[18] = "";
				if(hosCode[i].trim().equals("25169")|| hosCode[i].trim().equals("30418")){
				data[19] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
				}else{
					data[19] = "";
				}
//				//특검검사 크레아틴 보정후값만 적용되도록 //20161007 05011 포항성모 제외
//				if((!hosCode[i].trim().equals("25291"))&&(inspectCode[i].trim().substring(0, 5).equals("05028")
//						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
//						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
//						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502")))){
//				continue;	
//				}
				
				
				if (hosCode[i].trim().equals("28029")
						&& (inspectCode[i].trim().substring(0, 5).equals("05481") || inspectCode[i].trim().substring(0, 5).equals("05483") 
								|| inspectCode[i].trim().substring(0, 5).equals("21254") || inspectCode[i].trim().substring(0, 5).equals("21257")
								|| inspectCode[i].trim().substring(0, 5).equals("41377") || inspectCode[i].trim().substring(0, 5).equals("41392")
								|| inspectCode[i].trim().substring(0, 5).equals("21259") || inspectCode[i].trim().substring(0, 5).equals("21273")
								|| inspectCode[i].trim().substring(0, 5).equals("21274") || inspectCode[i].trim().substring(0, 5).equals("41377")
								|| inspectCode[i].trim().substring(0, 5).equals("31077") || inspectCode[i].trim().equals("7125900")
								|| inspectCode[i].trim().equals("7125902") 
								|| inspectCode[i].trim().equals("41431")|| inspectCode[i].trim().equals("000900")|| inspectCode[i].trim().equals("000902")
								|| inspectCode[i].trim().equals("000903"))) {
					continue;
				}

				
				if (resultType[i].trim().equals("C")) {
					data[6] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
	//				data[15] = getReferenceValue(remark);
	//				data[18] = "";
					
					
					if ( hosCode[i].trim().equals("28029")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("71245")||inspectCode[i].trim().substring(0, 5).equals("11052")||inspectCode[i].trim().substring(0, 5).equals("21638")
									||inspectCode[i].trim().substring(0, 5).equals("72189")||inspectCode[i].trim().substring(0, 5).equals("72242")||inspectCode[i].trim().substring(0, 5).equals("72182")
									||(Integer.parseInt(inspectCode[i].trim().substring(0, 5))>=301 && Integer.parseInt(inspectCode[i].trim().substring(0, 5))<=325))) 
					{
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							if (++i == cnt)
								break;
						}
						i--;
						data[6] = "별지보고";
					}

					// !
//					if (inspectCode[i].trim().length() == 7 
//							&& ( !hosCode[i].trim().equals("28029") 
//									&& (!hosCode[i].trim().equals("30418") && ( inspectCode[i].trim().substring(0, 5).equals("81204") || inspectCode[i].trim().substring(0, 5).equals("21672")))))
//					{
//						data[18] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
//						data[18] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
//						data[11] = "";
//						data[15] = "";
//						curDate = rcvDate[i];
//						curNo = rcvNo[i];
//						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
//								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
//						{
//							data[18] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
//									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
//							if (++i == cnt)
//								break;
//						}
//						i--;
//						data[6] = "";
//					//	data[18] ="별지보고";  //이상하긴함 묶어놓고 별지보고 처리?? (20200213 이상해서 일단 별지보고 제외처리) 
//					}
					if (inspectCode[i].trim().length() == 7 
							&& ( !hosCode[i].trim().equals("28029")
									&& (!hosCode[i].trim().equals("30418") || (!inspectCode[i].trim().substring(0, 5).equals("21672") &&!inspectCode[i].trim().substring(0, 5).equals("81204")
											&& !inspectCode[i].trim().substring(0, 5).equals("00923")&& !inspectCode[i].trim().substring(0, 5).equals("00924")))))
					{
						data[18] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[18] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[18] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
						data[6] = "";
					//	data[18] ="별지보고";  //이상하긴함 묶어놓고 별지보고 처리?? (20200213 이상해서 일단 별지보고 제외처리) 
					}
					//test
					else if (hosCode[i].trim().equals("28029") && inspectCode[i].trim().substring(0, 5).equals("71252")) 
					{
						data[18] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[18] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[18] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
						data[6] = "";
					}
					else if (!hosCode[i].trim().equals("12640")&& isMAST(inspectCode[i].trim().substring(0, 5))) 
					{
						vmast = new Vector();
						if (inspectCode[i].trim().substring(0, 5).equals("00673")) {
							data[8] = "00673";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00674")) {
							data[8] = "00674";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00683")) {
							data[8] = "00683";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00684")) {
							data[8] = "00684";
						}
						
						if (inspectCode[i].trim().substring(0, 5).equals("00687")) {
							data[8] = "00687";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00688")) {
							data[8] = "00688";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00689")) {
							data[8] = "00689";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00690")) {
							data[8] = "00690";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00691")) {
							data[8] = "00691";
						}
						
						data[11] = "";
						data[15] = "";
						data[18] = appendBlanks("검사항목", 26)+ appendBlanks("CLASS", 8)+ appendBlanks("검사항목", 25)+ appendBlanks("CLASS", 8);
						data[18] += "\r\n";
						// data[18] += "\r\n" + appendBlanks(inspectName[i], 26)
						// + appendBlanks(result[i], 8);

						vmast.addElement(appendBlanks(inspectName[i], 26)+ appendBlanks(result[i].substring(0, 1), 8));
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							try {
								vmast.addElement(appendBlanks(inspectName[i],26)+ appendBlanks(result[i++].substring(0,1), 8));
								if (inspectCode[i].trim().substring(0,5).equals("00673")||
									inspectCode[i].trim().substring(0,5).equals("00674")) 
								{
									vmast.addElement(appendBlanks(inspectName[i], 26)+ appendBlanks(result[i++].substring(0, 1), 8));
								} else
									break;
							} catch (Exception e) {
							}
							if (i >= cnt)
								break;
						}
						i--;
						data[18] = getResultMAST(data[18].toString(), vmast)+ "\r\n" + getMastRemark();
					} else if (!hosCode[i].trim().equals("12640")&& isMAST_Two(inspectCode[i].trim().substring(0, 5))) 
					{
						vmast = new Vector();
						data[8] = inspectCode[i].trim().substring(0, 5);
						data[11] = "";
						data[15] = "";
						data[18] = getRPad("검사항목", 26)+ getRPad("CLASS", 6)+ getRPad("검사항목", 26)+ getRPad("CLASS", 6);
						data[18] += "\r\n";
						// data[18] += "\r\n" + appendBlanks(inspectName[i], 26)
						// + appendBlanks(result[i], 8);

						vmast.addElement(getRPad(inspectName[i].trim(), 20)+ getRPad(result[i].trim().substring(0, 1), 6));
						curDate = rcvDate[i];
						curNo = rcvNo[i];	
						int mastad =0;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							try {
								if(mastad==0)
								{
									vmast.addElement(getRPad(inspectName[i].trim(), 26) + getRPad(result[i++].trim().trim(), 6));
								}else
								{
//									vmast.addElement(getRPad(inspectName[i].trim(), 26) + getRPad(result[i++].trim().substring(0, 1), 6));
								}
								if (inspectCode[i].trim().substring(0, 5).equals("00683")||//
								inspectCode[i].trim().substring(0, 5).equals("00684")
								||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
								||inspectCode[i].trim().substring(0, 5).equals("00690")||inspectCode[i].trim().substring(0, 5).equals("00691")
								){
									if(isMastDuplPrint(inspectCode[i])){
										i++;
									}else{
										vmast.addElement(getRPad(inspectName[i].trim(), 26) + getRPad(result[i++].trim().substring(0, 1), 6));
									}
								
								}else
								break;
								
							} catch (Exception e) {
							}
							if (i >= cnt)
								break;
							mastad++;
						}
						i--;
						data[18] = getResultMAST_Two(data[18].toString(), vmast)+ "\r\n" + getMastRemark();		
					}
				} else {
					//컬쳐&센시 합치기
					if ((hosCode[i].trim().equals("17588")||(hosCode[i].trim().equals("30408")) && (inspectCode[i].trim().equals("31010")))) {
						data[18] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//		data[14] = data[18].trim();
						try {
							if ((inspectCode[i + 1].substring(0, 5).equals("31011")||inspectCode[i + 1].substring(0, 5).equals("31012")) && rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[18] = data[18] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
				//				data[14] = data[18].trim();
								i++;
								// culture_flag = true;
							} else {
								data[18] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//				data[14] = data[18].trim();
							}
						} catch (Exception e) {
							data[18] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//			data[14] = data[18].trim();
						}
					}
					
					//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
					
					if (inspectCode[i].trim().equals("31100")||inspectCode[i].trim().equals("31101")
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
							||inspectCode[i].trim().equals("31128")) {
						data[18] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						// data[14] = data[18].trim(); 20171019 리마크에 컬쳐센시 결과가 같이 집계된다는 내용에 해당 코드 제외 처리 아래의 4군데도 동ㅇ일 적용
						try {
							if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001")) 
									&& rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[18] = data[18] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
								//data[14] = data[18].trim();
								i++;
								// culture_flag = true;
							} else {
								data[18] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
								//data[14] = data[18].trim();
							}
						} catch (Exception e) {
							data[18] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							//data[14] = data[18].trim();
						}
					}
					
					else
					{
						data[11] = "";
						data[15] = "";
						data[18] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]);
					}
				}
				
				if(hosCode[i].trim().equals("25169")){
					System.out.println(inspectCode[i]);
					if (inspectCode[i].trim().length() == 7 
							|| "00654|00941|11050|11205|21258|21280|21284|21295|21297|21380".contains (inspectCode[i].trim())
							|| "21539|21574|21575|21576|21641|21653|31001|31010|31011|31012|31014|31022|31027|31029|31030|31031".contains (inspectCode[i].trim())
							|| "31035|31052|31055|31059|31067|31100|31101|31102|31103|31104|31105|31106|31107|31108|31109|31110".contains (inspectCode[i].trim())
							|| "31111|31112|31113|31114|31115|31116|31117|31118|31119|31120|31121|31122|31123|31124|31125|31126".contains (inspectCode[i].trim())
							|| "31127|31128|41112|41121|41359|41360|41381|41450|42000|51023|52001|52002|52003|52004|52005|52006".contains (inspectCode[i].trim())
							|| "52007|52008|52009|52010|52011|52030|52031|52032|52033|52034|52035|52036|52037|52038|52039|61001".contains (inspectCode[i].trim())
							|| "61002|61003|61004|61005|61006|61007|61008|61009|61010|61011|61012|61013|61014|61015|61016|61017".contains (inspectCode[i].trim())
							|| "61018|61019|61020|61021|61022|61023|61024|61025|61026|61028|61029|61030|61031|64483|64505|64673".contains (inspectCode[i].trim())
							|| "68014|68015|68016|68017|68018|68021|71001|71002|71003|71004|71005|71006|71011|71047|71050|71051".contains (inspectCode[i].trim())
							|| "71053|71059|71070|71071|71086|71094|71118|71124|71142|71153|71192|71196|71198|71245|71246|71249".contains (inspectCode[i].trim())
							|| "71280|72008|31052|21574|21573|21685|21686".contains (inspectCode[i].trim())
							|| "81354|81388|81399|81424|81695|81703|89978".contains (inspectCode[i].trim())) {
						
					
						
						
						data[10] = data[2]+ "_" + data[4] + "_" + data[3]+ "_" + data[5] + "_" + "01"+".jpg";
						data[9] = "Y";
					}
					else{
						data[10] ="";
					}
					
				}else if(hosCode[i].trim().equals("28029")){
					if( "00301|00302|00303|00304|00305|00306|00307|00308|00309|00310|00311|00312|00313".contains (inspectCode[i].trim().substring(0, 5))
							|| "00314|00315|00316|00317|00318|00319|00320|00321|00322|00323|00324|00325|00654".contains (inspectCode[i].trim().substring(0, 5))
							|| "00750|00820|05481|05483|05495|05562|21258|21280|21380|21582|21638|21653|21677".contains (inspectCode[i].trim().substring(0, 5))
							|| "21683|25002|31010|31015|31016|31019|31022|31077|31079|31100|31101|31102|31103".contains (inspectCode[i].trim().substring(0, 5))
							|| "31104|31105|31106|31107|31108|31109|31110|31111|31112|31113|31114|31115|31116".contains (inspectCode[i].trim().substring(0, 5))
							|| "31117|31118|31119|31120|31121|31122|31123|31124|31125|31126|31127|31128|41124".contains (inspectCode[i].trim().substring(0, 5))
							|| "41227|41236|41338|41360|42000|51073|61012|70009|71001|71002|71003|71004|71005".contains (inspectCode[i].trim().substring(0, 5))
							|| "71006|71011|71053|71139|71192|71245|71249|71259|71323|71324|72039|72178|21672".contains (inspectCode[i].trim().substring(0, 5))
							|| "7218200|72183|72189|72190|72210|7224200|72245|81418|81424|81494|41121|72241".contains (inspectCode[i].trim().substring(0, 5))
							|| "81544|81608|92894|99934|99935|99936|99952|99955|99956|72051|71252|25000|41201|31005|72255".contains (inspectCode[i].trim().substring(0, 5))
							|| "21382|21382|71118|21670|41381|31026|21589|89978|11137|11050|71246|71341".contains (inspectCode[i].trim().substring(0, 5))
							 ) {
						
						 
						data[10] = data[2]+ "_" + data[4] + "_" + data[3]+ "_" + data[5] + "_" +"01" + "_" + bdt[i] + ".jpg";
						data[9] = "Y";
						data[6] = "별지보고";
						data[18] = "별지보고";
					}
					else{
						data[10] ="";
					}
				}else if( hosCode[i].trim().equals("30418")){
					if( "00100|00301|00304|00305|00307|00308|00309|00310|00311|00312|00317|00318|00323|00332|00333|00654".contains (inspectCode[i].trim().substring(0, 5))
							|| "00655|00721|05483|05495|05566|05567|11042|11050|21189|21248|21276|21532|21557|21562|21589|21653".contains (inspectCode[i].trim().substring(0, 5))
							|| "21684|25002|31027|31029|31030|31031|31035|31050|31064|41024|41121|41124|41374|41377|41381|64460".contains (inspectCode[i].trim().substring(0, 5))
							|| "64461|64500|64654|64667|64699|70011|70012|70013|70022|70029|70044|71047|71055|71058|71059|71069".contains (inspectCode[i].trim().substring(0, 5))
							|| "71084|71086|71103|71116|71117|71118|71119|71139|71142|71143|71144|71153|71158|71159|71160|71161".contains (inspectCode[i].trim().substring(0, 5))
							|| "71197|71198|71226|71230|71231|71232|71233|71234|71246|71254|71264|71267|71303|71311|71600|72008".contains (inspectCode[i].trim().substring(0, 5))
							|| "72020|72058|72061|72062|72063|72157|72178|72182|72185|72189|72190|72208|72209|72242|73012".contains (inspectCode[i].trim().substring(0, 5))
							|| "81006|81016|81051|81074|81075|81076|81126|81127|81165|81186|81301|81319|81330|81354|81373|81374".contains (inspectCode[i].trim().substring(0, 5))
							|| "81377|81384|81386|81389|81391|81418|81420|81423|81424|81428|81429|81431|81436|81439|81449|81464".contains (inspectCode[i].trim().substring(0, 5))
							|| "81479|81488|81511|81534|81546|81553|81587|81600|81626|81694|81699|81715|81731|81760|81761|81784".contains (inspectCode[i].trim().substring(0, 5))
							|| "81808|81840|81841|81848|84002|84020|84025|84041|84073|89978|89992|90100|99935|99936|05496|21668".contains (inspectCode[i].trim().substring(0, 5))
							|| "81757|81758|21685|21686|81901|81902|31026|31079|84035|81901|64598|64599|21668|84090|84086|81735".contains (inspectCode[i].trim().substring(0, 5))
							|| "84035|81901|81902|64598|64599|21668|84090|84086|84087|84088|84089|84091|99933|99934|81469|81944".contains (inspectCode[i].trim().substring(0, 5))
							|| "21677|71601|71095|72255|81937|00260|71004|81772|71603|81614|81922|81806|71002|71006".contains (inspectCode[i].trim().substring(0, 5))
							|| "84029|84009|81809|72241|64462|71124|71225|81527|84030|72253|81630".contains (inspectCode[i].trim().substring(0, 5))) {
						
					
						
						
						data[10] = data[2]+ "_" + data[4] + "_" + data[3]+ "_" + data[5] + "_" +"01" + ".jpg";
						data[9] = "Y";
						data[6] = "별지보고";
						data[18] = "별지보고";
					}
					else{
						data[10] ="";
					}
				}
				else {
				if (specNo[i].trim().equals("")){
					data[10] = data[2]+ "_" + data[4] + "_" + "검체번호"+ "_" + data[5] + "_" + "01"+".jpg";
				}
				else {
				//이미지 생성 파일명		
						data[10] = data[2]+ "_" + data[4] + "_" + data[3]+ "_" + data[5] + "_" + "01"+".jpg";
					}
				}

				if (!isDebug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
						wbresult.addCell(label);
					}
				} 
				data = new String[20];
				row++;
			}
			if (cnt == 400 || isNext) {
				setParameters(new String[] { hosCode[cnt - 1],rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setParameters(null);
		}
	}
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

public class DownloadGeneral extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadGeneral() {
		// isDebug = false;
		initialize();
	}
	
	

	public DownloadGeneral(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}


	public DownloadGeneral(String id, String fdat, String tdat,
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
			row = 2;
			row2 = 1;
			if (!isDebug) {
				try {
					workbook = Workbook.createWorkbook(new File(savedir
							+ makeOutFile()));
					wbresult = workbook.createSheet("Result", 0);
					wbremark = workbook.createSheet("Remark", 1);
					String ArraryResult[] = null;					
										
					label = new jxl.write.Label(0, 0,
							"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
					wbresult.addCell(label);
					
					ArraryResult = (new String[] { "기관구분", "검체번호", "수신자명",
							"성별", "나이", "차트번호", "접수일자", "접수번호", "검사코드",
							"병원검사코드", "검사명", "문자결과", "문장결과", "H/L", "Remark",
							"참고치", "주민등록번호" });
					
//					ArraryResult = (new String[] { "병원코드", "수탁ID", "접수일",
//							"차트번호", "이름", "성별", "생년월일", "검사코드", "한글명",
//							"영문명", "검체ID", "검체명", "바코드", "수악일시", "검사결과",
//							"서술결과", "리마크", "판정", "참고치", "수탁기관코드" });
					
					for (int i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 1, ArraryResult[i]);
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
			String data[] = new String[17];
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
				data[0] = "11370319";
				if (hosCode[i].trim().equals("23070")) {
					data[1] = patName[i];
				}
				else{
				data[1] = specNo[i].trim();
				}
				if (hosCode[i].trim().equals("23070")) {
					data[2] = specNo[i].trim();
				}
				else{
				data[2] = patName[i];
				}
				data[3] = sex[i];
				data[4] = age[i];
				data[5] = chartNo[i];
				data[6] = rcvDate[i].trim();
				data[7] = rcvNo[i].trim();
				data[8] = inspectCode[i].trim();
				data[9] = clientInspectCode[i].trim();
				data[10] = inspectName[i];
				data[14] = "  ";
				data[16] = securityNo[i];
				//대전교도소  17685,12812,21583 주민등록 번호 앞6자리까지만 보이도록함
				if(hosCode[i].trim().equals("17685")||hosCode[i].trim().equals("12812")||hosCode[i].trim().equals("21583"))
				{
					if(data[16].length()>6)
					{
						data[16] = data[16].substring(0, 6);
					}
				}
				//서부병원 건너뛰기
				if (hosCode[i].trim().equals("27379")
						&& (inspectCode[i].trim().equals("61001") || inspectCode[i].trim().equals("61002") || inspectCode[i].trim().equals("61003")||
								inspectCode[i].trim().equals("61004") || inspectCode[i].trim().equals("61005") || inspectCode[i].trim().equals("61006")
								|| inspectCode[i].trim().equals("61007")|| inspectCode[i].trim().equals("31010")|| inspectCode[i].trim().equals("31012"))) {
					continue;
				}
				
				//서부병원 건너뛰기
				if (hosCode[i].trim().equals("29561")
						&& (inspectCode[i].trim().substring(0, 5).equals("05021")&&!inspectCode[i].trim().equals("0502102"))) {
					continue;
				}
				
				
				//중앙아동병원
				if (hosCode[i].trim().equals("26111")
						&& (inspectCode[i].trim().equals("1130900") || inspectCode[i].trim().equals("1130901") || inspectCode[i].trim().equals("1130902")||
								inspectCode[i].trim().equals("1130903") || inspectCode[i].trim().equals("1130905") || inspectCode[i].trim().equals("1130906"))) {
					continue;
				}

//				//특검검사 크레아틴 보정후값만 적용되도록 //20161007 05011 포항성모 제외
//				if((!hosCode[i].trim().equals("25291"))&&(inspectCode[i].trim().substring(0, 5).equals("05028")
//						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
//						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
//						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502")))){
//				continue;	
//				}
				
				//특검검사 크레아틴 보정후값만 적용되도록 //20161007 05011 포항성모 제외 20180213 경주동국대 때문에 아래와 같이 수정 
				if((!hosCode[i].trim().equals("25291") && !hosCode[i].trim().equals("23298")
						&& !hosCode[i].trim().equals("32472") && !hosCode[i].trim().equals("32730"))
						&& ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502")))){
				continue;	
				}


				
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
					data[15] = getReferenceValue(remark);
					data[12] = "";

					// !
					if ((hosCode[i].trim().equals("33598"))&& inspectCode[i].trim().length() == 7 
							&& ( inspectCode[i].trim().substring(0, 5).equals("72020")
									|| inspectCode[i].trim().substring(0, 5).equals("72242") || inspectCode[i].trim().substring(0, 5).equals("71252")
									|| inspectCode[i].trim().substring(0, 5).equals("81375") || inspectCode[i].trim().substring(0, 5).equals("00309")
									|| inspectCode[i].trim().substring(0, 5).equals("72245") || inspectCode[i].trim().substring(0, 5).equals("71259")
									|| inspectCode[i].trim().substring(0, 5).equals("72241") || inspectCode[i].trim().substring(0, 5).equals("00009")
									|| inspectCode[i].trim().substring(0, 5).equals("21068") || inspectCode[i].trim().substring(0, 5).equals("71246")
									|| inspectCode[i].trim().substring(0, 5).equals("72189") || inspectCode[i].trim().substring(0, 5).equals("11309")
									|| inspectCode[i].trim().substring(0, 5).equals("72237") || inspectCode[i].trim().substring(0, 5).equals("72227")
									|| inspectCode[i].trim().substring(0, 5).equals("72228") || inspectCode[i].trim().substring(0, 5).equals("72229")
									|| inspectCode[i].trim().substring(0, 5).equals("72230") || inspectCode[i].trim().substring(0, 5).equals("72231")
									|| inspectCode[i].trim().substring(0, 5).equals("72232") || inspectCode[i].trim().substring(0, 5).equals("72233")
									|| inspectCode[i].trim().substring(0, 5).equals("72234") || inspectCode[i].trim().substring(0, 5).equals("72235")
									|| inspectCode[i].trim().substring(0, 5).equals("72236") || inspectCode[i].trim().substring(0, 5).equals("72238")
									|| inspectCode[i].trim().substring(0, 5).equals("81469") || inspectCode[i].trim().substring(0, 5).equals("00948")
									) ) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 40) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 40) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 40)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					//해당병원 33598 해당검사 21677 만 결과와 검사명 따로 처리 참고치의 경우 참고치 필드에 따로 집계 처리 요청하여 진행 20200730
					else if ((hosCode[i].trim().equals("33598"))&& inspectCode[i].trim().length() == 7 
								&& (inspectCode[i].trim().substring(0, 5).equals("21677")) ) 
						{
							data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21);
							data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21);
							data[11] = "";
							data[15] = "참 고 치\r\n";
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
									&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
							{
								data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21);
								data[15] += getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
								if (++i == cnt)
									break;
							}
							i--;
						}
					
					//해당병원 33598 해당검사 31001 만 결과와 검사명 따로 처리 그람스테인 결과내용이 없을 경우 집계 제외 20200729
					//20200729 그람스테인 결과에 공백일 경우 제외 시키고 결과 값이 있을때만 묶는 것.
					
					else if ((hosCode[i].trim().equals("33598"))&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("31001"))) 
					{
						
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21);
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21);
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5);
								thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim()) 
								  ;) 
						{
								if ( result[i].equals("")){	
									data[12] +="";
								}else{
							
									data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21);
								}
							
							if (++i == cnt)
								break;							
						}
						i--;
					}
					
					else if ((hosCode[i].trim().equals("33598")) && inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("00334") || inspectCode[i].trim().substring(0, 5).equals("00690")
							|| inspectCode[i].trim().substring(0, 5).equals("00691") || inspectCode[i].trim().substring(0, 5).equals("11052")
							|| inspectCode[i].trim().substring(0, 5).equals("81148") || inspectCode[i].trim().substring(0, 5).equals("72182")
							|| inspectCode[i].trim().substring(0, 5).equals("00309") || inspectCode[i].trim().substring(0, 5).equals("00687") 
							)){

						data[12] = "";
						data[11] = ""; // 문자결과
						data[15] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5)
								; thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());
							) {
							data[12] ="이미지 별도 첨부";
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					//	data[12] = data[11].trim();
					}
					else if ((hosCode[i].trim().equals("24619")||hosCode[i].trim().equals("27468"))&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("00095")|| inspectCode[i].trim().substring(0, 5).equals("71252")
									|| inspectCode[i].trim().substring(0, 5).equals("11052"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}else if (hosCode[i].trim().equals("20709")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("00095")||inspectCode[i].trim().substring(0, 5).equals("00948")
									||inspectCode[i].trim().substring(0, 5).equals("00950")||inspectCode[i].trim().substring(0, 5).equals("11052")
									||inspectCode[i].trim().substring(0, 5).equals("72241")||inspectCode[i].trim().substring(0, 5).equals("71251")
									||inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("71259")
									||inspectCode[i].trim().substring(0, 5).equals("11101")||inspectCode[i].trim().substring(0, 5).equals("31001")
									||inspectCode[i].trim().substring(0, 5).equals("00301") ||inspectCode[i].trim().substring(0, 5).equals("00309")
									||inspectCode[i].trim().substring(0, 5).equals("00317")||inspectCode[i].trim().substring(0, 5).equals("21623")
									||inspectCode[i].trim().substring(0, 5).equals("71297"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					//20170710 2가지 검사 단문 -> 장문 27814 병원 양태용
					else if (hosCode[i].trim().equals("27814")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("71252")	|| inspectCode[i].trim().substring(0, 5).equals("21683")
									|| inspectCode[i].trim().substring(0, 5).equals("21677")|| inspectCode[i].trim().substring(0, 5).equals("72227")
									|| inspectCode[i].trim().substring(0, 5).equals("72242")|| inspectCode[i].trim().substring(0, 5).equals("11301")
									|| inspectCode[i].trim().substring(0, 5).equals("11303")|| inspectCode[i].trim().substring(0, 5).equals("72206")
									|| inspectCode[i].trim().substring(0, 5).equals("72182")|| inspectCode[i].trim().substring(0, 5).equals("21061"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
//					//20191007 새물아동병원 장문 요청 
//					else if (hosCode[i].trim().equals("33103")&& inspectCode[i].trim().length() == 7 
//							&& (inspectCode[i].trim().substring(0, 5).equals("72182") || inspectCode[i].trim().substring(0, 5).equals("72245")
//									|| inspectCode[i].trim().substring(0, 5).equals("72206") || inspectCode[i].trim().substring(0, 5).equals("72242")
//									)) 
//					{
//						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
//						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
//						data[11] = "";
//						data[15] = "";
//						curDate = rcvDate[i];
//						curNo = rcvNo[i];
//						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
//								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
//						{
//							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
//									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
//							if (++i == cnt)
//								break;
//						}
//						i--;
//					}
					
					else if ((hosCode[i].trim().equals("23379") || hosCode[i].trim().equals("30194") )&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("71251")||inspectCode[i].trim().substring(0, 5).equals("71252"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					else if (hosCode[i].trim().equals("18663")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("11101"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					else if (hosCode[i].trim().equals("23108")&& inspectCode[i].trim().length() == 7) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					else if (hosCode[i].trim().equals("15466")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("11052"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					else if ((hosCode[i].trim().equals("27283")||hosCode[i].trim().equals("28251"))&& inspectCode[i].trim().length() == 7) 
					{
						int f = i;
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;

					}
					else if ((hosCode[i].trim().equals("30448")&& inspectCode[i].trim().length() == 7)
							&& (inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("11052")|| inspectCode[i].trim().substring(0, 5).equals("21061")
									|| inspectCode[i].trim().substring(0, 5).equals("71252")|| inspectCode[i].trim().substring(0, 5).equals("21677")
									|| inspectCode[i].trim().substring(0, 5).equals("72227")|| inspectCode[i].trim().substring(0, 5).equals("00901")
									|| inspectCode[i].trim().substring(0, 5).equals("11301")|| inspectCode[i].trim().substring(0, 5).equals("00094"))) 
					{
						int f = i;
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;

					}
					else if (hosCode[i].trim().equals("27379")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("71251")||inspectCode[i].trim().substring(0, 5).equals("71252"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					else if (hosCode[i].trim().equals("24428")) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					else if (hosCode[i].trim().equals("20709")&& (inspectCode[i].trim().substring(0, 5).equals("00804"))) 
					{
						data[12] =  getReamrkValue(hosCode[i], rcvDate[i],rcvNo[i], rmkCode[i]);
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
					}
					else if (hosCode[i].trim().equals("17082")&& inspectCode[i].trim().length() == 7 
								&& (inspectCode[i].trim().substring(0, 5).equals("00609")||	inspectCode[i].trim().substring(0, 5).equals("00608") 
										|| inspectCode[i].trim().substring(0, 5).equals("21061"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} 
					else if (hosCode[i].trim().equals("25610")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("00802")||	inspectCode[i].trim().substring(0, 5).equals("00822") 
									|| inspectCode[i].trim().substring(0, 5).equals("11002"))) 
				{
					data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
					data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
					data[11] = "";
					data[15] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} 
					else if (hosCode[i].trim().equals("29114")&& inspectCode[i].trim().length() == 7 
							&& (inspectCode[i].trim().substring(0, 5).equals("00806")||	inspectCode[i].trim().substring(0, 5).equals("00822") 
									||	inspectCode[i].trim().substring(0, 5).equals("00802") 
									|| inspectCode[i].trim().substring(0, 5).equals("11002")|| inspectCode[i].trim().substring(0, 5).equals("00095")
									|| inspectCode[i].trim().substring(0, 5).equals("71252")|| inspectCode[i].trim().substring(0, 5).equals("71259"))) 
				{
					data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
					data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
					data[11] = "";
					data[15] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				}else if (hosCode[i].trim().equals("21118")|| hosCode[i].trim().equals("13928")&& inspectCode[i].trim().length() == 7) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if (!hosCode[i].trim().equals("14107")&& inspectCode[i].trim().length() == 7&& !hosCode[i].trim().equals("17082")&&inspectCode[i].trim().substring(0, 5).equals("28282")
							&& (inspectCode[i].trim().substring(0, 5).equals("21264")||inspectCode[i].trim().substring(0, 5).equals("00609") 
									|| inspectCode[i].trim().substring(0, 5).equals("00608") ||inspectCode[i].trim().substring(0, 5).equals("21061"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if ((hosCode[i].trim().equals("14894")|| hosCode[i].trim().equals("19115"))
							&& inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(0, 5).equals("00095")
									||inspectCode[i].trim().substring(0, 5).equals("00802") ||inspectCode[i].trim().substring(0, 5).equals("00822"))) 
					{ // 전주 중앙가정의학
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if ((hosCode[i].trim().equals("20974")||hosCode[i].trim().equals("26111"))&& inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(0, 5).equals("11026") 
							|| inspectCode[i].trim().substring(0, 5).equals("11052")	|| inspectCode[i].trim().substring(0, 5).equals("72182")	|| inspectCode[i].trim().substring(0, 5).equals("72183")
							|| inspectCode[i].trim().substring(0, 5).equals("72242")	|| inspectCode[i].trim().substring(0, 5).equals("72225")
							|| inspectCode[i].trim().substring(0, 5).equals("72245"))) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if (hosCode[i].trim().equals("28336")&& (inspectCode[i].trim().substring(0, 5).equals("72185")||inspectCode[i].trim().substring(0, 5).equals("31083")))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if  ((hosCode[i].trim().equals("27466") || hosCode[i].trim().equals("23070"))
							&&(inspectCode[i].trim().substring(0, 5).equals("72185")))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} else if (hosCode[i].trim().equals("20980")&&(inspectCode[i].trim().substring(0, 5).equals("41450")))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					else if (hosCode[i].trim().equals("27379")&&(inspectCode[i].trim().substring(0, 5).equals("31001")||inspectCode[i].trim().substring(0, 5).equals("11301"))) 
					{
						data[12] =  appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							String result_31001="-";
							if(appendBlanks(result[i], 21).trim().length()>0)
							{
								result_31001=appendBlanks(result[i], 21);
							}
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ result_31001 + "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}else if (hosCode[i].trim().equals("19033")&&(inspectCode[i].trim().substring(0, 5).equals("72185")||inspectCode[i].trim().substring(0, 5).equals("74023"))) 
					{
						data[12] =  appendBlanks(inspectName[i], 30)+ "\t" + appendBlanks(result[i], 21) + "\t"+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							String result_31001="-";
							if(appendBlanks(result[i], 21).trim().length()>0)
							{
								result_31001=appendBlanks(result[i], 21);
							}
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ result_31001 + "\t"
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						data[12] = "별지보고("+bdt[i]+")";
						i--;
						
					} else if (!hosCode[i].trim().equals("12640")&& isMAST(inspectCode[i].trim().substring(0, 5))) 
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
						data[12] = appendBlanks("검사항목", 26)+ appendBlanks("CLASS", 8)+ appendBlanks("검사항목", 25)+ appendBlanks("CLASS", 8);
						data[12] += "\r\n";
						// data[12] += "\r\n" + appendBlanks(inspectName[i], 26)
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
						data[12] = getResultMAST(data[12].toString(), vmast)+ "\r\n" + getMastRemark();
					} else if (!hosCode[i].trim().equals("12640")&& isMAST_Two(inspectCode[i].trim().substring(0, 5))) 
					{
						vmast = new Vector();
						data[8] = inspectCode[i].trim().substring(0, 5);
						data[11] = "";
						data[15] = "";
						data[12] = getRPad("검사항목", 26)+ getRPad("CLASS", 6)+ getRPad("검사항목", 26)+ getRPad("CLASS", 6);
						data[12] += "\r\n";
						// data[12] += "\r\n" + appendBlanks(inspectName[i], 26)
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
						data[12] = getResultMAST_Two(data[12].toString(), vmast)+ "\r\n" + getMastRemark();		
					}
				} else {
					//컬쳐&센시 합치기
					if ((hosCode[i].trim().equals("17588")||(hosCode[i].trim().equals("30408")) && (inspectCode[i].trim().equals("31010")))) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[14] = data[12].trim();
						try {
							if ((inspectCode[i + 1].substring(0, 5).equals("31011")||inspectCode[i + 1].substring(0, 5).equals("31012")) && rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[12] = data[12] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
								data[14] = data[12].trim();
								i++;
								// culture_flag = true;
							} else {
								data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
								data[14] = data[12].trim();
							}
						} catch (Exception e) {
							data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[14] = data[12].trim();
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
							||inspectCode[i].trim().equals("31124")) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						// data[14] = data[12].trim(); 20171019 리마크에 컬쳐센시 결과가 같이 집계된다는 내용에 해당 코드 제외 처리 아래의 4군데도 동ㅇ일 적용
						try {
							if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001")) 
									&& rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[12] = data[12] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
								//data[14] = data[12].trim();
								i++;
								// culture_flag = true;
							} else {
								data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
								//data[14] = data[12].trim();
							}
						} catch (Exception e) {
							data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							//data[14] = data[12].trim();
						}
					}
					
					else if (hosCode[i].trim().equals("19033")&&(inspectCode[i].trim().substring(0, 5).equals("61028"))){

						data[11] = "";
						data[15] = "";
						data[12] = data[12] = "별지보고("+bdt[i]+")";
					}
					else
					{
						data[11] = "";
						data[15] = "";
						data[12] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]);
					}
				}
				
				//todo 리마크 확인 필요//
				data[13] = highLow[i];
				if (rmkCode[i].trim().length() > 0){
					try {
						remarkCode = rmkCode[i].trim();//20160811 요기로 바꿈
						
							if (!kumdata[0].trim().equals(data[6].trim())|| !kumdata[1].trim().equals(data[7].trim())|| !kumdata[2].trim().equals(remarkCode)) 
								{
							//remarkCode = rmkCode[i].trim(); 20160811 리마크 수정 작업 양태용이 지켜봤음
								if(hosCode[i].trim().equals("10747") || hosCode[i].trim().equals("32472") 
										|| hosCode[i].trim().equals("32730") || hosCode[i].trim().equals("33598")){
									
									data[14] = getReamrkValue99(hosCode[i], rcvDate[i],rcvNo[i], rmkCode[i]);
								}
								
								else{
									
									data[14] = getReamrkValue(hosCode[i], rcvDate[i],rcvNo[i], rmkCode[i]);
								}
								
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						} 
					} catch (Exception ee) {
					}
	
					
				}
				else
				{
					remarkCode = "";
				}
				//혜화의원 문장결과에 리마크 내용 추가--05.06 천안 박성진다시 통화하여 리마크 내용이 아예빠지도록
				if (hosCode[i].trim().equals("28336"))
				{
					if(inspectCode[i].trim().equals("00904")||inspectCode[i].trim().equals("00906")||inspectCode[i].trim().equals("00909")){
						data[11]=data[15];	
						data[12]="";
					}
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
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001"))
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[12] = data[12] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
							i++;
							// culture_flag = true;
						} else {
							data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						}
					} catch (Exception e) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					}
				} 
				
				
				//삼광의료재단 리마크 제외
				if (hosCode[i].trim().equals("10747") || hosCode[i].trim().equals("32472")|| hosCode[i].trim().equals("32730"))
				{
					if(inspectCode[i].trim().equals("8160900")){
						data[14]="";
					}
				}
				
				//화홍병원 리마크 제외 
				if (hosCode[i].trim().equals("33598"))
				{
					if(inspectCode[i].trim().substring(0, 5).equals("11052")|| inspectCode[i].trim().substring(0, 5).equals("72182")
							|| inspectCode[i].trim().substring(0, 5).equals("11213") ){
						data[14]="";
					}
				}
				
				
				//20190214 양태용 중간결과 제외 처리
				if (hosCode[i].trim().equals("26111")) {
					if(data[12].indexOf("중　간　결　과　보　고")>=0)
						
					{
						continue;
					}
					
				}
				



				if (!isDebug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
						wbresult.addCell(label);
					}
				} 
				data = new String[17];
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

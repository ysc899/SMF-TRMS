package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

public class DownloadSJ extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadSJ() {
		// isDebug = false;
		initialize();
	}
	
	

	public DownloadSJ(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}


	public DownloadSJ(String id, String fdat, String tdat,
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
					wbresult = workbook.createSheet("Sheet1", 0);
					String ArraryResult[] = null;					
										
					
					ArraryResult = (new String[] { "차트번호","수진자명","성별","나이","식별번호","처방일자","처방코드","처방명","단문결과","판정","참고치"	,"장문결과"});
					
					
					
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
			String cns[] = (String[]) getDownloadData().get("처방번호"); // 처방번호
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
			String data[] = new String[12];
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
				data[0] = chartNo[i];	//차트번호
				data[1] = patName[i];	//수신자명
				data[2] = sex[i];		//성별
				data[3] = age[i];		//나이
				data[4] = specNo[i];	//검체번호(식별번호)	
				
				//처방번호 구분자가 D인거 잘라서 생성
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				}    
				
				data[6] = clientInspectCode[i];	//처방코드
				data[7] = inspectName[i];//처방명
				data[9] = highLow[i];	//판정
				
		
//				//특검검사 크레아틴 보정후값만 적용되도록 //20161007 05011 포항성모 제외
//				if((!hosCode[i].trim().equals("25291"))&&(inspectCode[i].trim().substring(0, 5).equals("05028")
//						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
//						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
//						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502")))){
//				continue;	
//				}

				


				
				if (resultType[i].trim().equals("C")) {
					data[8] = result[i]; 					//단문
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
					data[10] = getReferenceValue(remark);	//참고치
					data[11] = "";							//결과

					//서부병원 건너뛰기
					if (hosCode[i].trim().equals("31617")
							&& (inspectCode[i].trim().length()==7) && inspectCode[i].trim().substring(5, 7).equals("00")) {
						continue;
					}
					
					// !
					if ((hosCode[i].trim().equals("31617"))&& inspectCode[i].trim().length() == 7 ) 
					{
						
						data[11] = inspectName[i] + "(" + result[i] + ")";
						// data[11] = "";
						// data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[11] += "\r\n"+ inspectName[i] + "("+ result[i] + ")";
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					
					 if (!hosCode[i].trim().equals("12640")&& isMAST(inspectCode[i].trim().substring(0, 5))) 
					{
						vmast = new Vector();
						if (inspectCode[i].trim().substring(0, 5).equals("00673")) {
							data[7] = "00673";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00674")) {
							data[7] = "00674";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00683")) {
							data[7] = "00683";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00684")) {
							data[7] = "00684";
						}
						
						if (inspectCode[i].trim().substring(0, 5).equals("00687")) {
							data[7] = "00687";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00688")) {
							data[7] = "00688";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00689")) {
							data[7] = "00689";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00690")) {
							data[7] = "00690";
						}
						if (inspectCode[i].trim().substring(0, 5).equals("00691")) {
							data[7] = "00691";
						}
						
						data[11] = "";
						data[11] = "";
						data[11] = appendBlanks("검사항목", 26)+ appendBlanks("CLASS", 8)+ appendBlanks("검사항목", 25)+ appendBlanks("CLASS", 8);
						data[11] += "\r\n";
						// data[11] += "\r\n" + appendBlanks(inspectName[i], 26)
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
						data[11] = getResultMAST(data[11].toString(), vmast)+ "\r\n" + getMastRemark();
					} else if (!hosCode[i].trim().equals("12640")&& isMAST_Two(inspectCode[i].trim().substring(0, 5))) 
					{
						vmast = new Vector();
						data[7] = inspectCode[i].trim().substring(0, 5);
						data[11] = "";
						data[11] = "";
						data[11] = getRPad("검사항목", 26)+ getRPad("CLASS", 6)+ getRPad("검사항목", 26)+ getRPad("CLASS", 6);
						data[11] += "\r\n";
						// data[11] += "\r\n" + appendBlanks(inspectName[i], 26)
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
						data[11] = getResultMAST_Two(data[11].toString(), vmast)+ "\r\n" + getMastRemark();		
					}

					 data[9] = data[9].replace(".", " "); // 판정에 . 빼기
					 data[3] = data[3].replace(".00", " "); // 나이 소수점 버리기
					 
					
						
				}  
				else {

					 data[11] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]);
				}
				
				 



				if (!isDebug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
						wbresult.addCell(label);
					}
				} 
				data = new String[19];
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

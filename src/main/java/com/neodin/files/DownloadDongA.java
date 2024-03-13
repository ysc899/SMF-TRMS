package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 1

import java.io.File;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

public class DownloadDongA extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadDongA() {
		// isDebug = false;
		initialize();
	}

	public DownloadDongA(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
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
			if (!isDebug) {
				try {
					workbook = Workbook.createWorkbook(new File(savedir
							+ makeOutFile()));
					wbresult = workbook.createSheet("Result", 0);
					wbremark = workbook.createSheet("Remark", 1);
					String ArraryResult[] = null;
					ArraryResult = (new String[] { "의뢰일자", "병원등록번호", "검체번호",
							"환자명", "Item", "연속구분", "검사결과", "URL", "검사명" });
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
			String[] resultType = (String[]) getDownloadData().get("결과타입"); // 문자인지 문장인지
			String[] clientInspectCode = (String[]) getDownloadData().get("병원검사코드"); //
			String data[] = new String[9];
			String url_clientInspectCode="";

			String[] sex = (String[]) getDownloadData().get("성별");
			String[] age = (String[]) getDownloadData().get("나이");
			String[] securityNo = (String[]) getDownloadData().get("주민번호");
			String[] highLow = (String[]) getDownloadData().get("결과상태");
			String[] lang = (String[]) getDownloadData().get("언어");
			String[] history = (String[]) getDownloadData().get("이력");
			String[] rmkCode = (String[]) getDownloadData().get("리마크코드");
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
			String bdt2[] = (String[]) getDownloadData().get("요양기관번호");
			String data2[] = new String[17]; // TODO
//			String[] _tmp = new String[3];
			String remark[] = new String[5];
			String remarkCode = "";
			
			for (int i = 0; i < cnt; i++) {	//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				String curDate = "";
				String curNo = "";
				data[0] =  rcvDate[i].trim(); //의뢰일자
				data[1] = chartNo[i].trim(); //챠트번호
				data[2] = specNo[i].trim(); //검체번호
				data[3] = patName[i].trim();//환자명
				data[4] = clientInspectCode[i].trim(); 		//병원처방번호
				data[5] = "0";	//연속구분
				data[6] = result[i].trim();	//검사결과
				data[7] = "https://www.seegenemedical.com/resultImg/27594/"+specNo[i].trim()+"_"+clientInspectCode[i].trim()+".jpg";	//URL : 검체번호_검사코드.jpg
				data[8] = inspectName[i].trim();	//검사명
				
				if (resultType[i].trim().equals("C")) {//단문결과
					if (hosCode[i].trim().equals("27594") && (inspectCode[i].trim().equals("7115300"))) 
					{
						url_clientInspectCode = data[4];
					}
					if(hosCode[i].trim().equals("27594") && (inspectCode[i].trim().indexOf("71153")>=0))
					{
						data[7] = "https://www.seegenemedical.com/resultImg/27594/"+specNo[i].trim()+"_"+url_clientInspectCode.trim()+".jpg";
					}
					//부속 검사중 별지보고로 보고
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].toString().trim().equals("27594") 
							&& (inspectCode[i].trim().substring(0, 5).equals( "00307")
									||inspectCode[i].trim().substring(0, 5).equals( "00301")
									||inspectCode[i].trim().substring(0, 5).equals( "00941")
									||inspectCode[i].trim().substring(0, 5).equals( "00301")
									||inspectCode[i].trim().substring(0, 5).equals( "00304")
									||inspectCode[i].trim().substring(0, 5).equals( "00307")
									||inspectCode[i].trim().substring(0, 5).equals( "00309")
									||inspectCode[i].trim().substring(0, 5).equals( "00310")
									||inspectCode[i].trim().substring(0, 5).equals( "00312")
									||inspectCode[i].trim().substring(0, 5).equals( "00316")
									||inspectCode[i].trim().substring(0, 5).equals( "00332")
									||inspectCode[i].trim().substring(0, 5).equals( "00333")
									||inspectCode[i].trim().substring(0, 5).equals( "00654")
									||inspectCode[i].trim().substring(0, 5).equals( "00655")
									||inspectCode[i].trim().substring(0, 5).equals( "00947")
									||inspectCode[i].trim().substring(0, 5).equals( "05481")
									||inspectCode[i].trim().substring(0, 5).equals( "05483")
									||inspectCode[i].trim().substring(0, 5).equals( "21684")
									||inspectCode[i].trim().substring(0, 5).equals( "71245")
									||inspectCode[i].trim().substring(0, 5).equals( "72035")
									||inspectCode[i].trim().substring(0, 5).equals( "81051")
									||inspectCode[i].trim().substring(0, 5).equals( "81165")
									||inspectCode[i].trim().substring(0, 5).equals( "81516")
									||inspectCode[i].trim().substring(0, 5).equals( "81517")
									||inspectCode[i].trim().substring(0, 5).equals( "81518")
									||inspectCode[i].trim().substring(0, 5).equals( "00309")
									||inspectCode[i].trim().substring(0, 5).equals( "00323")
									||inspectCode[i].trim().substring(0, 5).equals( "00317")
									||inspectCode[i].trim().substring(0, 5).equals( "00318")
									||inspectCode[i].trim().substring(0, 5).equals( "00319")
									||inspectCode[i].trim().substring(0, 5).equals( "00320")
									||inspectCode[i].trim().substring(0, 5).equals( "00324")
									||inspectCode[i].trim().substring(0, 5).equals( "71136")
									||inspectCode[i].trim().substring(0, 5).equals( "71297")
									||inspectCode[i].trim().substring(0, 5).equals( "81127")
									||inspectCode[i].trim().substring(0, 5).equals( "41052")
									||inspectCode[i].trim().substring(0, 5).equals( "72255")
									))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] = "별지보고";
							if (++i == cnt)
								break;
						}
						i--;
					} 
					//단일 검사중 별지보고
					if (hosCode[i].toString().trim().equals("27594") 
							&& (inspectCode[i].trim().substring(0, 5).equals( "00814")
									||inspectCode[i].trim().substring(0, 5).equals( "00737")
									||inspectCode[i].trim().substring(0, 5).equals( "21035")
									||inspectCode[i].trim().substring(0, 5).equals( "21044")
									||inspectCode[i].trim().substring(0, 5).equals( "21253")
									||inspectCode[i].trim().substring(0, 5).equals( "21589")
									||inspectCode[i].trim().substring(0, 5).equals( "21590")
									||inspectCode[i].trim().substring(0, 5).equals( "21591")
									||inspectCode[i].trim().substring(0, 5).equals( "21592")
									||inspectCode[i].trim().substring(0, 5).equals( "41121")
									||inspectCode[i].trim().substring(0, 5).equals( "41360")
									||inspectCode[i].trim().substring(0, 5).equals( "70013")
									||inspectCode[i].trim().substring(0, 5).equals( "71084")
									||inspectCode[i].trim().substring(0, 5).equals( "71143")
									||inspectCode[i].trim().substring(0, 5).equals( "71144")
									||inspectCode[i].trim().substring(0, 5).equals( "71154")
									||inspectCode[i].trim().substring(0, 5).equals( "71156")
									||inspectCode[i].trim().substring(0, 5).equals( "72008")
									||inspectCode[i].trim().substring(0, 5).equals( "72058")
									||inspectCode[i].trim().substring(0, 5).equals( "72064")
									||inspectCode[i].trim().substring(0, 5).equals( "73012")
									||inspectCode[i].trim().substring(0, 5).equals( "81319")
									||inspectCode[i].trim().substring(0, 5).equals( "81354")
									||inspectCode[i].trim().substring(0, 5).equals( "81377")
									||inspectCode[i].trim().substring(0, 5).equals( "81390")
									||inspectCode[i].trim().substring(0, 5).equals( "81395")
									||inspectCode[i].trim().substring(0, 5).equals( "81398")
									||inspectCode[i].trim().substring(0, 5).equals( "81479")
									||inspectCode[i].trim().substring(0, 5).equals( "81600")
									||inspectCode[i].trim().substring(0, 5).equals( "89984")
									||inspectCode[i].trim().substring(0, 5).equals( "64699"))) {
						
						data[6] = "별지보고";
					} 
				} else {//장문결과
					//20151228 최대열 장문결과 세포 문장 형태로 변환작업
					data[6]= getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]);
//					if (hosCode[i].trim().equals("27594") && (inspectCode[i].trim().equals("31052"))) 
//					{
//						data[6]="별지보고";
//					}
//					
					
					
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
			if (cnt == 400) {
				setParameters(new String[] { hosCode[cnt - 1],rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception e) {
			setParameters(null);
		}
	}
}

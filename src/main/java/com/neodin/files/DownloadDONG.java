package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

public class DownloadDONG extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadDONG() {
		isDebug = false;
		initialize();
	}

	public DownloadDONG(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String s, int i) {
		String s1 = s.trim().substring(0);
		if (s.trim().length() < i) {
			for (int j = 0; j < i - s.length(); j++)
				s1 = s1 + " ";

		} else {
			s1 = s.substring(0, i);
		}
		return s1;
	}

	public void closeDownloadFile() {
		if (isData)
			try {
				workbook.write();
			} catch (Exception _ex) {
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception _ex) {
				}
			}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-05-25 오후 5:07:15)
	 * 
	 * @return boolean
	 * @param param
	 *            java.lang.String[]
	 */
	public boolean isText(String param) {
		if (param.equals("00246"))
			return true;
		if (param.equals("00211"))
			return true;
		if (param.equals("00045"))
			return true;
		if (param.equals("00089"))
			return true;
		if (param.equals("00525"))
			return true;
		if (param.equals("00091"))
			return true;
		if (param.equals("11026"))
			return true;
		if (param.equals("21191"))
			return true;
		if (param.equals("21192"))
			return true;
		if (param.equals("41092"))
			return true;
		if (param.equals("41097"))
			return true;
		if (param.equals("41114"))
			return true;
		if (param.equals("41112"))
			return true;
		if (param.equals("41072"))
			return true;
		if (param.equals("41062"))
			return true;
		if (param.equals("41075"))
			return true;
		if (param.equals("41081"))
			return true;
		if (param.equals("41110"))
			return true;
		if (param.equals("41052"))
			return true;
		if (param.equals("21565"))
			return true;
		if (param.equals("00028"))
			return true;
		if (param.equals("81378"))
			return true;
		if (param.equals("41201"))
			return true;
		if (param.equals("21123"))
			return true;
		if (param.equals("72059") || param.equals("72018"))
			return true;
		if (param.equals("21124"))
			return true;
		if (param.equals("00674"))
			return true;
		if (param.equals("00673"))
			return true;
		if (param.equals("00683"))
			return true;
		if (param.equals("00684"))
			return true;
		if (param.equals("00687"))
			return true;
		if (param.equals("00688"))
			return true;
		if (param.equals("00689"))
			return true;
		if (param.equals("00205"))
			return true;
		if (param.equals("00608"))
			return true;
		if (param.equals("21430"))
			return true;
		if (param.equals("51042"))
			return true;
		if (param.equals("21452"))
			return true;
		if (param.equals("41138"))
			return true;
		if (param.equals("21434"))
			return true;
		if (param.equals("21453"))
			return true;
		if (param.equals("41141"))
			return true;
		if (param.equals("21199"))
			return true;
		if (param.equals("71141"))
			return true;
		if (param.equals("21538"))
			return true;
		if (param.equals("41141"))
			return true;
		if (param.equals("21171"))
			return true;
		if (param.equals("21168"))
			return true;
		if (param.equals("21261"))
			return true;
		if (param.equals("21166"))
			return true;
		if (param.equals("21169"))
			return true;
		if (param.equals("21167"))
			return true;
		if (param.equals("21170"))
			return true;
		if (param.equals("21263"))
			return true;
		if (param.equals("21174"))
			return true;
		if (param.equals("21175"))
			return true;
		if (param.equals("21172"))
			return true;
		if (param.equals("21173"))
			return true;
		if (param.equals("21176"))
			return true;
		if (param.equals("21267"))
			return true;
		if (param.equals("00647"))
			return true;
		if (param.equals("21045"))
			return true;
		if (param.equals("31024"))
			return true;
		if (param.equals("21064"))
			return true;
		return false;
	}

	public void makeDownloadFile() {
		row = 0;
		row2 = 0;
		try {
			if (!isDebug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbresult.addCell(label);
			}
		} catch (Exception _ex) {
		}
	}

	public void processingData(int i) {
		try {
			String as[] = (String[]) getDownloadData().get("병원코드");
			String as1[] = (String[]) getDownloadData().get("접수일자");
			String as2[] = (String[]) getDownloadData().get("접수번호");
			String as3[] = (String[]) getDownloadData().get("검체번호");
			String as4[] = (String[]) getDownloadData().get("차트번호");
			String as5[] = (String[]) getDownloadData().get("수신자명");
			String as6[] = (String[]) getDownloadData().get("검사코드");
			String as7[] = (String[]) getDownloadData().get("검사명");
			String as8[] = (String[]) getDownloadData().get("일련번호");
			String as9[] = (String[]) getDownloadData().get("결과");
			String as10[] = (String[]) getDownloadData().get("결과타입");
			String as11[] = (String[]) getDownloadData().get("병원검사코드");
			String as12[] = (String[]) getDownloadData().get("성별");
			String as13[] = (String[]) getDownloadData().get("나이");
			String as14[] = (String[]) getDownloadData().get("주민번호");
//			String[] _tmp = (String[]) getDownloadData().get("결과상태");
			String as15[] = (String[]) getDownloadData().get("언어");
			String as16[] = (String[]) getDownloadData().get("이력");
			String as17[] = (String[]) getDownloadData().get("리마크코드");
			String as18[] = (String[]) getDownloadData().get("검사완료일");
			String as19[] = (String[]) getDownloadData().get("처방번호");
			String as20[] = new String[18];
//			String as21[] = new String[11];
//			String[] _tmp1 = new String[3];
			String as23[] = new String[4];
			String s = "";
			for (int j = 0; j < i; j++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((as6[j].trim().substring(0, 5).equals("05028")&&!as6[j].trim().equals("0502802"))
						|| (as6[j].trim().substring(0, 5).equals("05029")&&!as6[j].trim().equals("0502902"))
						|| (as6[j].trim().substring(0, 5).equals("05011")&&!as6[j].trim().equals("0501102"))
						|| (as6[j].trim().substring(0, 5).equals("05025")&&!as6[j].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				String as22[] = new String[11];
//				String s1 = "";
//				String s4 = "";
				as20[0] = "11370319";
				as20[17] = as19[j];
				as20[1] = as3[j].trim();
				as20[2] = as5[j];
				as20[3] = as18[j];
				as20[4] = as13[j];
				as20[5] = as4[j];
				as20[6] = as1[j].trim();
				as20[7] = as2[j].trim();
				as20[8] = as6[j].trim();
				as20[9] = as11[j].trim();
				as20[10] = as7[j];
				as20[14] = " ";
				as20[16] = as14[j];
				if (as10[j].trim().equals("C")) {
					as20[11] = as9[j];
					as23[0] = as6[j];
					as23[1] = as15[j];
					as23[2] = as16[j];
					as23[3] = as12[j];
					as20[15] = getReferenceValue(as23);
					as20[12] = "";
					if (as6[j].trim().equals("31059"))
						as20[12] = as9[j];
					if (as6[j].trim().length() == 7 && as6[j].trim().substring(0, 5).equals("71245")) {
						as20[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "관  련  약  제";
						as20[12] += "\r\n" + appendBlanks(as7[j], 30) + "\t"+ appendBlanks(as9[j], 21) + "\t" + getResultHBV(as6[j].trim());
						as20[11] = "";
						as20[15] = "";
						String s3 = as1[j];
						String s6 = as2[j];
						for (String s8 = as6[j++].trim().substring(0, 5); s8.equals(as6[j].trim().substring(0, 5))&& s3.equals(as1[j].trim())&& s6.equals(as2[j].trim());) 
						{
							as20[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "\t"+ appendBlanks(as9[j], 21)+ "\t" + getResultHBV(as6[j].trim());
							if (++j == i)
								break;
						}
						j--;
					}
					else 
					if ((
							as[j].toString().equals("20790") 
							&& isText(as6[j].trim().substring(0, 5)))
							|| as6[j].trim().substring(0, 5).equals("00091")	|| as6[j].trim().substring(0, 5).equals("00095")
							|| as6[j].trim().substring(0, 5).equals("00752")	|| as6[j].trim().substring(0, 5).equals("11101")
							|| as6[j].trim().substring(0, 5).equals("21061")	|| as6[j].trim().substring(0, 5).equals("11026")
							|| as6[j].trim().substring(0, 5).equals("11052")	|| as6[j].trim().substring(0, 5).equals("00662")
							|| as6[j].trim().substring(0, 5).equals("00663"))
					{
						as20[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						as20[12] += "\r\n" + appendBlanks(as7[j], 30) + "\t"	+ appendBlanks(as9[j], 21) + "\t" + as20[15];
						as20[11] = "";
						as20[15] = "";
						String s2 = as1[j];
						String s5 = as2[j];
						for (String s7 = as6[j++].trim().substring(0, 5); s7.equals(as6[j].trim().substring(0, 5))&& s2.equals(as1[j].trim())&& s5.equals(as2[j].trim());) 
						{
							as20[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "\t"+ appendBlanks(as9[j], 21)+ "\t"
									+ getReferenceValue(new String[] { as6[j], as15[j],as16[j], as12[j] }).trim();
							if (++j == i)
								break;
						}
						j--;						
					} 
					else if (as[j].toString().equals("24471")&& as6[j].trim().substring(0, 5).equals("31059"))
					{
						as20[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						as20[12] += "\r\n" + appendBlanks(as7[j], 30) + "\t"	+ appendBlanks(as9[j], 21) + "\t" + as20[15];
						as20[11] = "";
						as20[15] = "";
						String s3 = as1[j];
						String s6 = as2[j];
						for (String s8 = as6[j++].trim().substring(0, 5); s8.equals(as6[j].trim().substring(0, 5))	&& s3.equals(as1[j].trim())	&& s6.equals(as2[j].trim());) 
						{
							as20[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "\t"+ appendBlanks(as9[j], 21)	+ "\t"
									+ getReferenceValue(new String[] { as6[j], as15[j],as16[j], as12[j] }).trim();
							if (++j == i)
								break;
						}
						j--;
					}
					else if (as[j].toString().equals("24471")&& (as6[j].trim().substring(0, 5).equals("31059")||as6[j].trim().substring(0, 5).equals("72178")
							|| as6[j].trim().substring(0, 5).equals("72179")|| as6[j].trim().substring(0, 5).equals("71153")|| as6[j].trim().substring(0, 5).equals("72182")
							|| as6[j].trim().substring(0, 5).equals("72183")|| as6[j].trim().substring(0, 5).equals("72189")|| as6[j].trim().substring(0, 5).equals("72242")))
					{
						as20[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						as20[12] += "\r\n" + appendBlanks(as7[j], 30) + "\t"	+ appendBlanks(as9[j], 21) + "\t" + as20[15];
						as20[11] = "";
						as20[15] = "";
						String s3 = as1[j];
						String s6 = as2[j];
						for (String s8 = as6[j++].trim().substring(0, 5); s8.equals(as6[j].trim().substring(0, 5))	&& s3.equals(as1[j].trim())	&& s6.equals(as2[j].trim());) 
						{
							as20[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "\t"+ appendBlanks(as9[j], 21)	+ "\t"
									+ getReferenceValue(new String[] { as6[j], as15[j],as16[j], as12[j] }).trim();
							if (++j == i)
								break;
						}
						j--;
					}
					else if (as6[j].trim().length() == 7
							&& !as6[j].trim().substring(0, 5).equals("00095") && !as6[j].trim().substring(0, 5).equals("11101")
							&& !as6[j].trim().substring(0, 5).equals("21061") && !as6[j].trim().substring(0, 5).equals("11052")) {
						as20[12] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
						as20[12] += "\r\n" + appendBlanks(as7[j], 30) + "\t"+ appendBlanks(as9[j], 21) + "\t" + as20[15];
						as20[11] = "";
						as20[15] = "";
						String s3 = as1[j];
						String s6 = as2[j];
                            
						
						for (String s8 = as6[j++].trim().substring(0, 5); s8.equals(as6[j].trim().substring(0, 5))&& s3.equals(as1[j].trim())&& s6.equals(as2[j].trim());) 
						{
							 if(isMastDuplPrint(as6[j].trim())){ 
								 ++j;
							 }else{
									as20[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "\t"+ appendBlanks(as9[j], 21)+ "\t"
											+ getReferenceValue(new String[] { as6[j], as15[j],as16[j], as12[j] }).trim();
									if (++j == i)
										break;
	                         }

						}
						j--;
					}
				} 
				else {
					as20[11] = "";
					as20[15] = "";
					as20[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
				}
				

				
				
				if (as17[j].trim().length() > 0)
					try {
						if (
								!kumdata[0].trim().equals(as20[6].trim())|| !kumdata[1].trim().equals(as20[7].trim())|| !kumdata[2].trim().equals(s) 
								||as6[j].substring(0,5).equals("00683") ||as6[j].substring(0,5).equals("00684")
								||as6[j].substring(0,5).equals("00687") ||as6[j].substring(0,5).equals("00688") ||as6[j].substring(0,5).equals("00689")
								) 
						{
							
				            if(isMastDuplPrint(as6[j].trim())){
				            	continue;
				            }else{
								s = as17[j].trim();
								as20[12] += "\r\n"+ getReamrkValue(as[j], as1[j], as2[j],as17[j]);
								kumdata[0] = as20[6].trim();
								kumdata[1] = as20[7].trim();
								kumdata[2] = s;
				            }
						}
						
//						if (!kumdata[0].trim().equals(as20[6].trim())|| !kumdata[1].trim().equals(as20[7].trim())|| !kumdata[2].trim().equals(s) ||as6[j].substring(0,5).equals("00687") ||as6[j].substring(0,5).equals("00688")||as6[j].substring(0,5).equals("00689") ) 
//						{
//							s = as17[j].trim();
//							as20[12] += "\r\n"+ getReamrkValue(as[j], as1[j], as2[j],as17[j]);
//							kumdata[0] = as20[6].trim();
//							kumdata[1] = as20[7].trim();
//							kumdata[2] = s;
//						}
						
					} catch (Exception _ex) {
					}
				else
					s = "";
				try {
					as22[0] = as20[1].substring(0, 8);
				} catch (Exception _ex) {
					as22[0] = as20[6];
				}
				//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if (as6[j].trim().equals("31010")||as6[j].trim().equals("31100")||as6[j].trim().equals("31101")
						||as6[j].trim().equals("31102")||as6[j].trim().equals("31103")
						||as6[j].trim().equals("31104")||as6[j].trim().equals("31105")
						||as6[j].trim().equals("31106")||as6[j].trim().equals("31107")
						||as6[j].trim().equals("31108")||as6[j].trim().equals("31109")
						||as6[j].trim().equals("31110")||as6[j].trim().equals("31111")
						||as6[j].trim().equals("31112")||as6[j].trim().equals("31113")
						||as6[j].trim().equals("31114")||as6[j].trim().equals("31115")
						||as6[j].trim().equals("31116")||as6[j].trim().equals("31117")
						||as6[j].trim().equals("31118")||as6[j].trim().equals("31119")
						||as6[j].trim().equals("31120")||as6[j].trim().equals("31121")
						||as6[j].trim().equals("31122")||as6[j].trim().equals("31123")
						||as6[j].trim().equals("31124")) {
					
					as20[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//		data[8] = as19[12].trim();
					try {
						if ((as6[j + 1].substring(0, 5).equals("32000")||as6[j + 1].substring(0, 5).equals("32001")
								||as6[j + 1].substring(0, 5).equals("31012")	)
								&& as2[j + 1].equals(as2[j])
								&& as1[j + 1 ].equals(as1[j])) {
							as20[12] = as20[12] + "\r\n" + getTextResultValue(as[j], as1[j], as2[j], as6[j + 1]);
			//				data[8] = as19[12].trim();
							j++;
							// culture_flag = true;
						} else {
							as20[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//				data[8] = as19[12].trim();
						}
					} catch (Exception e) {
						as20[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//			data[8] = as19[12].trim();
					}
				}
				
				
				if (as[j].toString().equals("14474")) {
					//온누리병원 문장 결과일경우 결과갑 하단에 참고치 내용 추가 하도록 수정
					if(as20[12].trim().length() > 0)
					{
						String gubun="";
						if(as20[15].trim().length() > 0)
						{
							gubun = "\r\n"+ "참고치:\r\n";
						}
						as22[1] = as20[5];
						as22[2] = as20[2];
						as22[3] = as20[1];
						as22[4] = as20[9];
						as22[5] = as20[10];
						as22[6] = as20[11];
						as22[7] = as20[12]+ gubun +as20[15];
						as22[8] = "";
						as22[9] ="";
						as22[10] = as20[3];
					}
					else
					{
						as22[1] = as20[5];
						as22[2] = as20[2];
						as22[3] = as20[1];
						as22[4] = as20[9];
						as22[5] = as20[10];
						as22[6] = as20[11];
						as22[7] = as20[15];
						as22[8] = "";
						as22[9] = "";
						as22[10] = as20[3];
					}
					

					
					if (!isDebug) {
						for (int k = 0; k < 10; k++) {
							label = new jxl.write.Label(k, row, as22[k]);
							wbresult.addCell(label);
						}
					}
					row++;
				} else {
					as22[1] = as20[5];
					as22[2] = as20[2];
					as22[3] = as20[1];
					as22[4] = as20[9];
					as22[5] = as20[10];
					as22[6] = as20[11];
					as22[7] = as20[12];
					as22[8] = "";
					as22[9] = as20[15];
					as22[10] = as20[3];
					
					
					
					if (!isDebug) {
						for (int l = 0; l < 11; l++) {
							label = new jxl.write.Label(l, row, as22[l]);
							wbresult.addCell(label);
						}
					}
					row++;
				}
			}
			if (i == 400)
				setParameters(new String[] { as[i - 1], as1[i - 1], as2[i - 1],
						as6[i - 1], as8[i - 1] });
			else
				setParameters(null);
		} catch (Exception _ex) {
			setParameters(null);
		}
	}
}

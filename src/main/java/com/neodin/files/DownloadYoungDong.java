package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

public class DownloadYoungDong extends ResultDownload {
	/*
	 * 영동병원
	 */
	boolean isDebug = false;

	boolean isData = false;

	public DownloadYoungDong() {
		initialize();
	}

	public DownloadYoungDong(String id, String fdat, String tdat, Boolean isRewrite) {
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

	public String appendBlanks2(String s, int i) {
		String s1 = s.trim().substring(0);
		int j = s.length();
		if (s.trim().length() < i) {
			for (int k = 0; k < i - j; k++)
				s1 = s1 + " ";
		} else {
			s1 = s.substring(0, i);
		}
		return s1;
	}

	public void closeDownloadFile() {
		if (!isDebug && isData)
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

	public void makeDownloadFile() {
		row = 1;
		if (!isDebug)
			try {
				workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String as[] = null;
				as = (new String[] { "의뢰일자", "등록번호", "검체번호", "성명", "병원검사코드 ", "결과", "기타" });
				for (int i = 0; i < as.length; i++) {
					label = new jxl.write.Label(i, 0, as[i]);
					wbresult.addCell(label);
				}
			} catch (Exception exception) {
				// System.out.println("OCS 파일쓰기 스레드 오류" +
				// exception.getMessage());
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
			String as12[] = (String[]) getDownloadData().get("언어");
			String as13[] = (String[]) getDownloadData().get("이력");
			String as14[] = (String[]) getDownloadData().get("성별");
			String as15[] = (String[]) getDownloadData().get("이미지여부");
			String as16[] = (String[]) getDownloadData().get("리마크코드");
			String as17[] = new String[4];
			for (int j = 0; j < i; j++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((as6[j].trim().substring(0, 5).equals("05028")&&!as6[j].trim().equals("0502802"))
						|| (as6[j].trim().substring(0, 5).equals("05029")&&!as6[j].trim().equals("0502902"))
						|| (as6[j].trim().substring(0, 5).equals("05011")&&!as6[j].trim().equals("0501102"))
						|| (as6[j].trim().substring(0, 5).equals("05025")&&!as6[j].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				String as18[] = new String[7];
				// String s = "";
				// String s3 = "";
				as18[0] = as1[j]; // 의뢰일자
				as18[1] = as4[j]; // 등록번호
				as18[2] = as3[j].trim(); // 검체번호
				as18[3] = as5[j]; // 성명
				as18[4] = as11[j].trim(); // 병원검사코드
				as18[5] = ""; // 결과
				as18[6] = ""; // 기타
				if (as10[j].trim().equals("C")) {
					as18[5] = as9[j];
					as17[0] = as6[j];
					as17[1] = as12[j];
					as17[2] = as13[j];
					as17[3] = as14[j];
					if (as6[j].trim().length() == 7
							&& (as6[j].trim().substring(0, 5).equals("00091")
									|| as6[j].trim().substring(0, 5).equals("72059") || as6[j].trim().substring(0, 5).equals("72018"))) {
						String s1 = as1[j];
						String s4 = as2[j];
						as18[5] = "";
						for (String s9 = as6[j++].trim().substring(0, 5); s9.equals(as6[j].trim().substring(0, 5)) && s1.equals(as1[j].trim())
								&& s4.equals(as2[j].trim());) {
							if (as6[j].trim().substring(5, 7).equals("01")) {
								as18[5] = as9[j].trim();
								as18[6] = "";
							}
							if (++j == i)
								break;
						}
						j--;
					} else if (as6[j].trim().length() == 7
							&& (as6[j].trim().substring(0, 5).equals("71251") || as6[j].trim().substring(0, 5).equals("31001"))) {
						String s1 = as1[j];
						String s4 = as2[j];
						as18[5] = "Remark 참조";
						// as18[5] = "";
						for (String s9 = as6[j++].trim().substring(0, 5); s9.equals(as6[j].trim().substring(0, 5)) && s1.equals(as1[j].trim())
								&& s4.equals(as2[j].trim());) {
							// if (as6[j].trim().substring(5, 7).equals("01")) {
							as18[6] += as7[j] + "   값:  " + as9[j].trim() + "\r\n";
							// as18[6] = "";
							// }
							if (++j == i)
								break;
						}
						j--;
					} else if (as6[j].trim().length() == 7 && (as15[j].equals("Y"))) {
						as18[5] = "Remark 참조";
						as18[6] = "대치통보(챠트참조)";
						String s2 = as1[j];
						String s5 = as2[j];
						for (String s7 = as6[j++].trim().substring(0, 5); s7.equals(as6[j].trim().substring(0, 5)) && s2.equals(as1[j].trim())
								&& s5.equals(as2[j].trim());)
							if (++j == i)
								break;
						j--;
					} else if (as6[j].trim().length() == 7
							&& (as6[j].trim().substring(0, 5).equals("00662") || as6[j].trim().substring(0, 5).equals("72059")
									|| as6[j].trim().substring(0, 5).equals("71251") || as6[j].trim().substring(0, 5).equals("92894")
									|| as6[j].trim().substring(0, 5).equals("00663") || as6[j].trim().substring(0, 5).equals("00673")
								    || as6[j].trim().substring(0, 5).equals("00683") || as6[j].trim().substring(0, 5).equals("00684")
								    ||as6[j].trim().substring(0, 5).equals("00687")||as6[j].trim().substring(0, 5).equals("00688")||as6[j].trim().substring(0, 5).equals("00689")
							
									|| as6[j].trim().substring(0, 5).equals("00674") || as6[j].trim().substring(0, 5).equals("11026") || as6[j]
									.trim().substring(0, 5).equals("72018"))) {
						as18[5] = "Remark 참조";
						as18[6] = "대치통보(챠트참조)" + "\r\n";
						String s2 = as1[j];
						String s5 = as2[j];
						for (String s7 = as6[j++].trim().substring(0, 5); s7.equals(as6[j].trim().substring(0, 5)) && s2.equals(as1[j].trim())
								&& s5.equals(as2[j].trim());) {
							as18[6] += as7[j] + "   값:  " + as9[j].trim() + "\r\n";
							if (++j == i)
								break;
						}
						j--;
					}
					 else if (as6[j].trim().length() == 7
							&& ( as6[j].trim().substring(0, 5).equals("00269"))){
							 

						as18[5] = appendBlanks("검  사  명 ", 30)+""+"" + "\t"+ appendBlanks("결    과", 21) ;
						as18[5] += getDivide() + "\r\n" + appendBlanks(as7[j], 30) + "\t" + appendBlanks(as9[j], 21) ;

						String s2 = as1[j];
						String s5 = as2[j];
						
						for (String s9 = as6[j++].trim().substring(0, 5); s9.equals(as6[j].trim().substring(0, 5)) && s2.equals(as1[j].trim())
								&& s5.equals(as2[j].trim());) {
							if (!as9[j].toString().trim().equals("")) {
								as18[5] += getDivide() + "\r\n" + appendBlanks(as7[j], 30) + "\t" + appendBlanks(as9[j], 21);
							}
							if (++j == i)
								break;
						}
						i--;
					}
					// 20160907 양태용 71252 11052 별지보고 처리
					 else if (as6[j].trim().length() == 7
								&& (as6[j].trim().substring(0, 5).equals("71252") || as6[j].trim().substring(0, 5).equals("11052"))) {
							String s1 = as1[j];
							String s4 = as2[j];
							as18[5] = "별지보고";
							// as18[5] = "";
							for (String s9 = as6[j++].trim().substring(0, 5); s9.equals(as6[j].trim().substring(0, 5)) && s1.equals(as1[j].trim())
									&& s4.equals(as2[j].trim());) {
								// if (as6[j].trim().substring(5, 7).equals("01")) {
								//as18[6] += as7[j] + "   값:  " + as9[j].trim() + "\r\n";
								 as18[6] = "";
								// }
								if (++j == i)
									break;
							}
							j--;
						} 
					
					
				} else {
					as18[5] = "Remark 참조";
					as18[6] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
				}
				
				
	//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if (as6[j].trim().equals("31100")||as6[j].trim().equals("31101")
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
					
					as18[6] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//		data[8] = as18[12].trim();
					try {
						if ((as6[j + 1].substring(0, 5).equals("32000")||as6[j + 1].substring(0, 5).equals("32001")
								)
								&& as2[j + 1].equals(as2[j])
								&& as1[j + 1 ].equals(as1[j])) {
							as18[6] = as18[6] + "\r\n" + getTextResultValue(as[j], as1[j], as2[j], as6[j + 1]);
			//				data[8] = as18[12].trim();
							j++;
							// culture_flag = true;
						} else {
							as18[6] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//				data[8] = as18[12].trim();
						}
					} catch (Exception e) {
						as18[6] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//			data[8] = as18[12].trim();
					}
				}

				
				if (as6[j].trim().substring(0, 5).equals("00608"))
					as18[6] = getReamrkValue(as[j], as1[j], as2[j], as16[j]);
				if (!as16[j].toString().equals("")) {
					String s8 = "";
					s8 = getReamrkValue(as[j], as1[j], as2[j], as16[j]);
					if (!s8.equals(""))
						s8 = "아래 Remark\r\n" + s8;
					as18[6] = (as18[6].trim().equals("") ? "" : as18[6].trim() + "\r\n") + s8;
				}

				
				
				
				if (!isDebug) {
					for (int k = 0; k < as18.length; k++) {
						label = new jxl.write.Label(k, row, as18[k]);
						wbresult.addCell(label);
					}
				}
				row++;
				
				
				
			}
			if (i == 400)
				setParameters(new String[] { as[i - 1], as1[i - 1], as2[i - 1], as6[i - 1], as8[i - 1] });
			else
				setParameters(null);
		} catch (Exception exception) {
			setParameters(null);
			exception.printStackTrace();
		}
	}
}

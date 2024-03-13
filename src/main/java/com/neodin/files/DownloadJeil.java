package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadJeil extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadJeil() {

		initialize();
	}

	public DownloadJeil(String id, String fdat, String tdat, Boolean isRewrite) {
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
		if (!isDebug && isData)
			try {
				workbook.write();
			} catch (Exception exception) {
				exception.printStackTrace();
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception exception2) {
					exception2.printStackTrace();
				}
			}
	}

	public void makeDownloadFile() {
		row = 2;
		row2 = 1;
		if (!isDebug)
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String as[] = null;
				label = new Label(0, 0,
						"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				wbresult.addCell(label);
				as = (new String[] { "기관구분", "검체번호", "수신자명", "성별", "나이",
						"차트번호", "접수일자", "접수번호", "검사코드", "병원검사코드", "검사명",
						"문자결과", "문장결과", "H/L", "Remark번호", "참고치", "주민등록번호" });
				String as1[] = { "검체번호", "Remark 번호", "Remark내용" };
				for (int i = 0; i < as.length; i++) {
					label = new Label(i, 1, as[i]);
					wbresult.addCell(label);
				}

				for (int j = 0; j < as1.length; j++) {
					label = new Label(j, 0, as1[j]);
					wbremark.addCell(label);
				}

			} catch (Exception exception) {
				//System.out.println("OCS 파일쓰기 스레드 오류" + exception.getMessage());
				exception.printStackTrace();
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
			String as15[] = (String[]) getDownloadData().get("결과상태");
			String as16[] = (String[]) getDownloadData().get("언어");
			String as17[] = (String[]) getDownloadData().get("이력");
			String as18[] = (String[]) getDownloadData().get("리마크코드");
			String as19[] = new String[17];
			String as20[] = new String[3];
			String as21[] = new String[4];
			String as22[] = (String[]) getDownloadData().get("리마크");
			boolean flag = false;
			for (int j = 0; j < i; j++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((as6[j].trim().substring(0, 5).equals("05028")&&!as6[j].trim().equals("0502802"))
						|| (as6[j].trim().substring(0, 5).equals("05029")&&!as6[j].trim().equals("0502902"))
						|| (as6[j].trim().substring(0, 5).equals("05011")&&!as6[j].trim().equals("0501102"))
						|| (as6[j].trim().substring(0, 5).equals("05025")&&!as6[j].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				as19[0] = "11370319";
				as19[1] = as3[j].trim();
				as19[2] = as5[j];
				as19[3] = as12[j];
				as19[4] = as13[j];
				as19[5] = as4[j];
				as19[6] = as1[j].trim();
				as19[7] = as2[j].trim();
				as19[8] = as6[j].trim();
				as19[9] = as11[j].trim();
				as19[10] = as7[j];
//				String s = "";
//				String s2 = "";
				as19[16] = as14[j];
				

				
				
				if (as10[j].trim().equals("C")) {
					as19[11] = as9[j];
					as21[0] = as6[j];
					as21[1] = as16[j];
					as21[2] = as17[j];
					as21[3] = as12[j];
					as19[15] = getReferenceValue(as21);
					as19[12] = "";
				
	
					if (as18[j].trim().length() > 0){
						try {
							if (!kumdata[0].trim().equals(as19[6].trim())	|| !kumdata[1].trim().equals(as19[7].trim())	|| !kumdata[2].trim().equals(as19[14].trim()))
							{
								row2++;
								as19[14] = as18[j].trim();
								as20[0] = as19[1];
								as20[1] = as19[14];
								as20[2] = getReamrkValue(as[j], as1[j], as2[j],as18[j]);
								kumdata[0] = as19[6].trim();
								kumdata[1] = as19[7].trim();
								kumdata[2] = as19[14].trim();
								if (!isDebug) {
									for (int k = 0; k < as20.length; k++) 
									{
										label = new Label(k, row2, as20[k]);
										wbremark.addCell(label);
									}
								}
								row2++;
							}
						} catch (Exception exception1) {
							exception1.printStackTrace();
						}
					}
					else
						as19[14] = "";
					
					
					//20161014 문경제일병원  00323 , 00324 리마크 집계 추가			
					if ((as6[j].trim().length() == 7)
							&& (as6[j].trim().equals("0032300") || as6[j].trim().equals("0032400") || as6[j].trim().equals("1105200")))
					{
						as19[12] = appendBlanks("검  사  명 ", 30) + "   "	+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						as19[12] += "\r\n" + appendBlanks(as7[j], 30) + "   "	+ appendBlanks(as9[j], 21) + "   " + as19[15];
						as19[11] = "";
						as19[15] = "";
						String s1 = as1[j];
						String s3 = as2[j];
						for (String s4 = as6[j++].trim().substring(0, 5); s4.equals(as6[j].trim().substring(0, 5))&& s1.equals(as1[j].trim()) && s3.equals(as2[j].trim());) 
						{
							as19[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "   "+ appendBlanks(as9[j], 21)+ "   "+ getReferenceValue(new String[] { as6[j], as16[j],	as17[j], as12[j] }).trim();
							if (++j == i)
								break;
						}
						j--;
						as19[12] = as19[12].trim()+"\r\n[Remark]\r\n"+as20[2]+"\r\n";
					}
					
					
					else if (as6[j].trim().length() == 7)
					{
						as19[12] = appendBlanks("검  사  명 ", 30) + "   "	+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						as19[12] += "\r\n" + appendBlanks(as7[j], 30) + "   "	+ appendBlanks(as9[j], 21) + "   " + as19[15];
						as19[11] = "";
						as19[15] = "";
						String s1 = as1[j];
						String s3 = as2[j];
						for (String s4 = as6[j++].trim().substring(0, 5); s4.equals(as6[j].trim().substring(0, 5))&& s1.equals(as1[j].trim()) && s3.equals(as2[j].trim());) 
						{
							as19[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "   "+ appendBlanks(as9[j], 21)+ "   "+ getReferenceValue(new String[] { as6[j], as16[j],	as17[j], as12[j] }).trim();
							if (++j == i)
								break;
						}
						j--;
					}
					
					else if (as6[j].trim().equals("71032") || as6[j].trim().equals("71034"))
					
					{
						as19[12] = appendBlanks("검  사  명 ", 30) + "   "	+ appendBlanks("결    과", 21);
						as19[12] += "\r\n" + appendBlanks(as7[j], 30) + "   "	+ appendBlanks(as9[j], 21);
						as19[11] = "";
						as19[15] = "";
						String s1 = as1[j];
						String s3 = as2[j];
						for (String s4 = as6[j++].trim().substring(0, 5); s4.equals(as6[j].trim().substring(0, 5))&& s1.equals(as1[j].trim()) && s3.equals(as2[j].trim());) 
						{
							as19[12] += "\r\n"+ appendBlanks(as7[j], 30)+ "   "+ appendBlanks(as9[j], 21).trim();
							if (++j == i)
								break;
						}
						
						j--;
						as19[12] = as19[12].trim()+"\r\n[Remark]\r\n"+as20[2]+"\r\n";
						
						
					}
															
				} else {
					as19[11] = "";
					as19[15] = "";
					as19[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
					
				
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
					
					as19[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//		data[8] = as19[12].trim();
					try {
						if ((as6[j + 1].substring(0, 5).equals("32000")||as6[j + 1].substring(0, 5).equals("32001")
								||as6[j + 1].substring(0, 5).equals("31011")	)
								&& as2[j + 1].equals(as2[j])
								&& as1[j + 1 ].equals(as1[j])) {
							as19[12] = as19[12] + "\r\n" + getTextResultValue(as[j], as1[j], as2[j], as6[j + 1]);
			//				data[8] = as19[12].trim();
							j++;
							// culture_flag = true;
						} else {
							as19[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//				data[8] = as19[12].trim();
						}
					} catch (Exception e) {
						as19[12] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
			//			data[8] = as19[12].trim();
					}
				}
				
				as19[13] = as15[j];
				
				

				
		
				if (!isDebug) {
					for (int l = 0; l < as19.length; l++) {
						label = new Label(l, row, as19[l]);
						wbresult.addCell(label);
					}
				}
				row++;
				if (flag) {
					j++;
					flag = false;
				}
			}
			if (i == 400)
				setParameters(new String[] { as[i - 1], as1[i - 1], as2[i - 1],
						as6[i - 1], as8[i - 1] });
			else
				setParameters(null);
		} catch (Exception exception) {
			setParameters(null);
			exception.printStackTrace();
		}
	}
}

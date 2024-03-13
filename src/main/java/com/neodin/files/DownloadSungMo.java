package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

public class DownloadSungMo extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadSungMo() {
		isDebug = false;
		initialize();
	}

	public DownloadSungMo(String id, String fdat, String tdat, Boolean isRewrite) {
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
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String as[] = null;
				as = (new String[] { "의로일자", "등록번호", "검체번호", "성명", "병원검사코드 ",
						"검사코드", "검사명", "결과", "기타" });
				for (int i = 0; i < as.length; i++) {
					label = new jxl.write.Label(i, 0, as[i]);
					wbresult.addCell(label);
				}
			} catch (Exception exception) {
				//System.out.println("OCS 파일쓰기 스레드 오류" + exception.getMessage());
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
				String as18[] = new String[9];
//				String s = "";
//				String s3 = "";
				as18[0] = as1[j];
				as18[1] = as4[j];
				as18[2] = as3[j].trim();
				as18[3] = as5[j];
				as18[4] = as11[j].trim();
				as18[5] = as6[j];
				as18[6] = as7[j];
				as18[7] = "";
				as18[8] = "";
				if (as10[j].trim().equals("C")) {
					as18[7] = as9[j];
					as17[0] = as6[j];
					as17[1] = as12[j];
					as17[2] = as13[j];
					as17[3] = as14[j];
					try {
						as18[8] = getReferenceValue(as17);
					} catch (Exception _ex) {
					}
					if (as6[j].trim().substring(0, 5).equals("11213")) {
						as18[7] = "Remark 참조";
						as18[8] = getTextResultValue(as[j], as1[j], as2[j],
								as6[j]);
					} else if (as6[j].trim().length() == 7
							&& (as6[j].trim().substring(0, 5).equals("00091")|| as6[j].trim().substring(0, 5).equals(	"71252")
									|| as6[j].trim().substring(0, 5).equals("00095")|| as6[j].trim().substring(0, 5).equals("71251")
									|| as6[j].trim().substring(0, 5).equals(	"71259") || as6[j].trim().substring(0, 5).equals("72059") || as6[j].trim().substring(0, 5).equals("72018"))) {
						String s6 = as18[8];
						as18[7] = "Remark 참조";
						as18[8] = "";
						as18[8] = appendBlanks("검사명 ", 20) + "\t"
								+ appendBlanks("결과", 30) + " \t" + "참고치";
						as18[8] += "\r\n" + appendBlanks(as7[j].trim(), 20)
								+ "\t" + appendBlanks(as9[j].trim(), 30) + "\t"
								+ s6.trim();
						String s1 = as1[j];
						String s4 = as2[j];
						for (String s9 = as6[j++].trim().substring(0, 5); s9
								.equals(as6[j].trim().substring(0, 5))
								&& s1.equals(as1[j].trim())
								&& s4.equals(as2[j].trim());) {
							as18[8] += "\r\n"
									+ appendBlanks(as7[j].trim(), 20)
									+ "\t"
									+ appendBlanks(as9[j].trim(), 30)
									+ "\t"
									+ getReferenceValue(
											new String[] { as6[j], as12[j],
													as13[j], as14[j], "1" })
											.trim();
							if (++j == i)
								break;
						}
						j--;
					} else if (as6[j].trim().length() == 7
							&& (as6[j].trim().substring(0, 5).equals("00662")
									|| as6[j].trim().substring(0, 5).equals(
											"00663")
									|| as6[j].trim().substring(0, 5).equals(
											"11026")
									|| as6[j].trim().substring(0, 5).equals(
											"11052") || as15[j].equals("Y"))) {
						as18[7] = "Remark 참조";
						as18[8] = "대치통보(챠트참조)";
						String s2 = as1[j];
						String s5 = as2[j];
						for (String s7 = as6[j++].trim().substring(0, 5); s7
								.equals(as6[j].trim().substring(0, 5))
								&& s2.equals(as1[j].trim())
								&& s5.equals(as2[j].trim());)
							if (++j == i)
								break;
						j--;
					}
				} else {
					if (as6[j].trim().substring(0, 5).equals("11213")) {
						as18[7] = "Remark 참조";
						as18[8] = getTextResultValue(as[j], as1[j], as2[j],
								as6[j]);
					} else {
						as18[7] = "Remark 참조";
						as18[8] = getTextResultValue(as[j], as1[j], as2[j],
								as6[j]);
					}
				}
				if (as6[j].trim().substring(0, 5).equals("00608"))
					as18[8] = getReamrkValue(as[j], as1[j], as2[j], as16[j]);
				if (!as16[j].toString().equals("")) {
					String s8 = "";
					s8 = getReamrkValue(as[j], as1[j], as2[j], as16[j]);
					if (!s8.equals(""))
						s8 = "아래 Remark\r\n" + s8;
					as18[8] = (as18[8].trim().equals("") ? "" : as18[8].trim()
							+ "\r\n")
							+ s8;
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

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1
import java.io.File;
import java.util.StringTokenizer;

import jxl.Workbook;
import jxl.write.Boolean;

//!
public class DownloadHana extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadHana() {
		initialize();
	}

	public DownloadHana(String id, String fdat, String tdat, java.lang.Boolean isRewrite) {
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
		if (!isDebug && isData) {
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
	}

	public void makeDownloadFile() {
		row = 1;
		if (!isDebug) {
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String as[] = null;
				as = (new String[] { "의뢰일자", "챠트번호", "성명", "검사번호", "내원번호",
						"검사코드", "검사명", "검사결과", "참고치", "보고일자" });
				for (int i = 0; i < as.length; i++) {
					label = new jxl.write.Label(i, 0, as[i]);
					wbresult.addCell(label);
				}
			} catch (Exception exception) {
				//System.out.println("OCS 파일쓰기 스레드 오류" + exception.getMessage());
			}
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
//			String[] _tmp = (String[]) getDownloadData().get("병원검사코드");
			String as11[] = (String[]) getDownloadData().get("성별");
//			String[] _tmp1 = (String[]) getDownloadData().get("나이");
			String as12[] = (String[]) getDownloadData().get("언어");
			String as13[] = (String[]) getDownloadData().get("이력");
			String as14[] = (String[]) getDownloadData().get("검사완료일");
//			String[] _tmp2 = (String[]) getDownloadData().get("처방번호");
			String as15[] = (String[]) getDownloadData().get("리마크코드");
			String as16[] = new String[11];
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
				as16[0] = as1[j].trim();
				as16[10] = as2[j].trim();
				as16[1] = as4[j];
				as16[2] = as5[j];
				for (StringTokenizer stringtokenizer = new StringTokenizer(
						as3[j].trim(), "-"); stringtokenizer.hasMoreElements();)
					try {
						as16[3] = stringtokenizer.nextToken();
						as16[4] = stringtokenizer.nextToken();
					} catch (Exception _ex) {
					}
				as16[5] = as6[j].trim();
				as16[6] = as7[j].trim();
				as16[9] = as14[j];
				as16[8] = getReferenceValue(new String[] { as6[j], as12[j],
						as13[j], as11[j] });
				if (as10[j].trim().equals("C")) {
					as16[7] = as9[j];
				} else {
					as16[7] = getTextResultValue(as[j], as1[j], as2[j], as6[j]);
				}
				if (as6[j].trim().length() == 7) {
					as16[7] = appendBlanks("검  사  명 ", 30) + "\t"
							+ appendBlanks("결    과", 21) + " \t"
							+ "참    고    치";
					as16[7] += "\r\n" + appendBlanks(as7[j], 30) + "\t"
							+ appendBlanks(as9[j], 21) + "\t" + as16[8];

					// !
					try {
						for (String s1 = as6[j++].trim().substring(0, 5); s1 == as6[j++]
								.trim().substring(0, 5); as16[8] += "\r\n"
								+ getReferenceValue(new String[] { as6[j],
										as12[j], as13[j], as11[j] }))
							if (as10[j].trim().equals("C")) {
								as16[7] += "\r\n"
										+ appendBlanks(as7[j], 30)
										+ "\t"
										+ appendBlanks(as9[j], 21)
										+ "\t"
										+ getReferenceValue(new String[] {
												as6[j], as12[j], as13[j],
												as11[j], "1" });
							} else {
								as16[7] += "\r\n"
										+ getTextResultValue(as[j], as1[j],
												as2[j], as6[j]);
							}
					} catch (Exception eee) {
					}
					j -= 2;
				}
				if (as15[j].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(as16[0].trim())
								|| !kumdata[1].trim().equals(as16[10].trim())
								|| !kumdata[2].trim().equals(s)) {
							s = as15[j].trim();
							as16[7] += "\r\n<REMARK>\r\n"
									+ getReamrkValue(as[j], as1[j], as2[j],
											as15[j]);
							kumdata[0] = as16[0].trim();
							kumdata[1] = as16[10].trim();
							kumdata[2] = s;
						}
					} catch (Exception _ex) {
					}
				else
					s = "";
				if (!isDebug) {
					for (int k = 0; k < 10; k++) {
						label = new jxl.write.Label(k, row, as16[k]);
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

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 11   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// Referenced classes of package com.neodin.files:
//            ResultDownload

//! 경기도립의료원
/*
 1. 수원
 2. 안성
 3. 이천
 4. 파주
 5. 포천
 6. 의정부
 */

public class DownloadGPMC extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	private String gubun1 = "\r\n============================================================\r\n";

	private String gubun2 = "\r\n------------------------------------------------------------\r\n";

	public DownloadGPMC() {
		// System.out.println("cjmct");
	}

	public DownloadGPMC(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String src, int length) {
		String dest = src.trim().substring(0);
		if (src.trim().length() < length) {
			for (int i = 0; i < length - src.length(); i++)
				dest = dest + " ";
		} else {
			dest = src.substring(0, length);
		}
		return dest;
	}

	public void closeDownloadFile() {
		if (isData)
			try {
				if (b_writer != null)
					b_writer.close();
				if (o_writer != null)
					o_writer.close();
				if (f_outStream != null)
					f_outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public String getReference(String parm[]) {
		try {

			// !
			String refer[] = getReferenceValue2(parm);
			String ref_ = "";

			// !
			if (refer == null) {
				return ref_;
			}
			int refcnt = refer.length;
			for (int i = 0; i < refcnt; i++) {
				if (i == 0) {
					ref_ = refer[i].toString().trim();
				} else {
					ref_ += "\r\n                                          "
							+ refer[i].toString().trim();
				}
			}
			// !
			return ref_;
		} catch (Exception e) {
			return "";
		}
	}

	public String getRemark(String parm[]) {
		return getReamrkValue(parm[0], parm[1], parm[2], parm[3]);
	}

	public String getRemarkTxt(String str[]) {
		StringBuffer b = new StringBuffer("");
		if (str.length == 0)
			return null;
		for (int i = 0; i < str.length; i++) {
			b.append(str[i]);
			if (str.length - 1 != i)
				b.append("\r\n");
		}
		return b.toString().trim();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-16 오전 10:53:02)
	 * 
	 * @return java.lang.String
	 */
	public String getResults(String name, String sex, String sampleSeq,
			String department, String jdat, String bdat, String etc) {
		String rlt = "\r\n";
		rlt += "              재단법인 씨젠의료재단";
		rlt += gubun1;
		rlt += "환자성명 : " + addBlanks(name, 15) + "성별 : " + addBlanks(sex, 5)
				+ "검체번호 : " + addBlanks(sampleSeq, 30) + "\r\n";
		rlt += "진 료 과 : " + addBlanks(department, 10) + "\r\n";
		rlt += "접수일자 : " + addBlanks(jdat, 10) + "결과입력일자 : "
				+ addBlanks(bdat, 10) + "\r\n";
		rlt += "참    조 : " + addBlanks(etc, 10);
		rlt += gubun2;
		return rlt;
	}

	public String getUnit(String unit) {
		StringTokenizer st = new StringTokenizer(unit, ",");
		if (st.countTokens() != 3) {
			return "";
		} else {
			st.nextToken();
			st.nextToken();
			return st.nextToken();
		}
	}

	public void makeDownloadFile() {
		try {
			file = new File(savedir + mkOutTextFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processingData(int cnt) {
		try {
			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
			String specNo[] = (String[]) getDownloadData().get("검체번호");
			String chartNo[] = (String[]) getDownloadData().get("차트번호");
			String patName[] = (String[]) getDownloadData().get("수신자명");
			String inspectCode[] = (String[]) getDownloadData().get("검사코드");
			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");
			String resultType[] = (String[]) getDownloadData().get("결과타입");
//			String clientInspectCode[] = (String[]) getDownloadData().get(
//					"병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
//			String[] _tmp = (String[]) getDownloadData().get("나이");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
			String deli = "|";
			String temp_rlt = "";
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
				String data[] = new String[15];
//				String[] _tmp1 = new String[3];
//				String[] _tmp2 = new String[4];
				data[0] = chartNo[i]; // 환자등록번호
				data[1] = patName[i]; // 환자이름
				data[2] = specNo[i].trim(); // 검체번호
				data[6] = rcvDate[i];
				data[4] = sex[i];

				// String rlt_ = getResults(data[1].trim(), data[4].trim(),
				// data[2].trim(), "", data[6].trim(), data[11].trim(), "");
				if (data[5] == null) {
					data[5] = "";
				}
				data[8] = "";
				data[9] = "";
				data[10] = "";
				data[11] = bdt[i]; // 보고일자
				if (resultType[i].trim().equals("C")) {
					data[8] = getUnit(unit[i]).trim();
					data[9] = getReference(new String[] { inspectCode[i],
							lang[i], history[i], sex[i] });
					data[5] += "\r\n" + appendBlanks(inspectName[i], 25)
							+ appendBlanks(result[i], 17) + data[9];
					if (inspectCode[i].trim().length() == 7) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[5] += "\r\n"
									+ appendBlanks(inspectName[i], 25)
									+ appendBlanks(result[i], 17)
									+ getReference(new String[] {
											inspectCode[i], lang[i],
											history[i], sex[i] });
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					data[5] += "\r\n"
							+ getTextResultValue(hosCode[i], rcvDate[i],
									rcvNo[i], inspectCode[i]).trim();
				}
				if (rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(rcvDate[i].trim())
								|| !kumdata[1].trim().equals(rcvNo[i].trim())
								|| !kumdata[2].trim().equals(rmkCode[i].trim())) {
							data[10] = getRemark(
									new String[] { hosCode[i], rcvDate[i],
											rcvNo[i], rmkCode[i] }).trim();
							if (data[10] == null
									|| data[10].toString().trim() == "null"
									|| data[10].trim().equals("")) {
								data[5] += "\r\n <REMARK>\r\n" + data[10];
							}
							kumdata[0] = rcvDate[i].trim();
							kumdata[1] = rcvNo[i].trim();
							kumdata[2] = rmkCode[i].trim();
						}
					} catch (Exception e) {
					}
				}
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}
				// if (data[1].toString().trim().equals("김준기")) {
				// System.out.println("");
				// }
				if ((i + 1 < cnt)
						&& rcvDate[i].trim().equals(rcvDate[i + 1].trim())
						&& rcvNo[i].trim().equals(rcvNo[i + 1].trim())
						&& specNo[i].trim().equals(specNo[i + 1])) {
					temp_rlt += data[5];
					// !

				} else {
					// 환자이름 , 성별 , 검체번호 , 진료과 , 접수일자, 보고일자,기타사항
					String rlt_ = getResults(data[1].trim(), data[4].trim(),
							data[2].trim(), "", data[6].trim(),
							data[11].trim(), "");
					// ! 회사명, 검체번호, 4(고정) ,1( 고장),결과
					if (temp_rlt.equals("")) {
						text.append("sutak" + deli + data[2] + deli + "4"
								+ deli + "1" + deli + rlt_ + data[5] + gubun1);
					} else {
						text.append("sutak" + deli + data[2] + deli + "4"
								+ deli + "1" + deli + rlt_ + temp_rlt + data[5]
								+ gubun1);
						temp_rlt = "";
					}
					b_writer
							.write(text.toString(), 0, text.toString().length());
					text = new StringBuffer();
					data[5] = "";
				}
			}
			if (cnt == 400)
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			else
				setParameters(null);
		} catch (Exception e) {
			setParameters(null);
			e.printStackTrace();
		}
	}
}

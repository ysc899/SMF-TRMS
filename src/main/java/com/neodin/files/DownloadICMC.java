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

//! 인천의료원
public class DownloadICMC extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	public DownloadICMC() {
		// System.out.println("cjmct");
	}

	public DownloadICMC(String id, String fdat, String tdat, Boolean isRewrite) {
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
		String refer = getReferenceValue(parm);
		return refer.equals("") ? "" : replaceCrLf(refer);
	}

	public String getRemark(String parm[]) {
		return replaceCrLf(getReamrkValue(parm[0], parm[1], parm[2], parm[3]));
	}

	public String getRemarkTxt(String str[]) {
		StringBuffer b = new StringBuffer("");
		if (str.length == 0)
			return null;
		for (int i = 0; i < str.length; i++) {
			b.append(str[i]);
			if (str.length - 1 != i)
				b.append("@^");
		}
		return b.toString().trim();
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
			file = new File(savedir + mkOutDatFile());
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
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
//			String[] _tmp = (String[]) getDownloadData().get("나이");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String deli = "#";
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
				String data[] = new String[11];
//				String[] _tmp1 = new String[3];
//				String[] _tmp2 = new String[4];
				data[0] = chartNo[i]; // 환자등록번호
				data[1] = patName[i]; // 환자이름
				data[2] = specNo[i].trim(); // 검체번호
				if (clientInspectCode[i].trim().equals(""))
					data[3] = " ";
				else
					data[3] = clientInspectCode[i]; // 병원검사코드
				data[6] = rcvDate[i];
				data[7] = inspectName[i].trim();
				data[4] = "";
				data[5] = result[i];
				data[8] = "";
				data[9] = "";
				data[10] = "";
				if (resultType[i].trim().equals("C")) {
					data[8] = getUnit(unit[i]).trim();
					data[9] = getReference(
							new String[] { inspectCode[i], lang[i], history[i],
									sex[i] }).trim();
					data[5] = result[i].trim().length() > 20 ? "별지통보"
							: result[i].trim();
					if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("00673")
								|| inspectCode[i].trim().substring(0, 5).equals("11026")
									|| inspectCode[i].trim().substring(0, 5).equals("11052") 
									|| inspectCode[i].trim().substring(0, 5).equals("00683")
									|| inspectCode[i].trim().substring(0, 5).equals("00684") 
									||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
									|| inspectCode[i].trim().substring(0, 5).equals("00674"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[5] = "별지통보";
							if (++i == cnt)
								break;
						}
						i--;
					} else if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(	"00083") || (inspectCode[i].trim().substring(0, 5).equals("72059")|| inspectCode[i].trim().substring(0, 5).equals("72018")))) 
					{
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[5] = result[i];
							}
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					data[5] = "별지통보";
				}
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(rcvDate[i].trim())
								|| !kumdata[1].trim().equals(rcvNo[i].trim())
								|| !kumdata[2].trim().equals(rmkCode[i].trim())) {
							data[10] = getRemark(
									new String[] { hosCode[i], rcvDate[i],
											rcvNo[i], rmkCode[i] }).trim();
							kumdata[0] = rcvDate[i].trim();
							kumdata[1] = rcvNo[i].trim();
							kumdata[2] = rmkCode[i].trim();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				else
					data[10] = "";
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}
				// ! 의뢰일자,등록번호,검체번호,품목코드, 결과
				text.append(data[6] + deli + data[0] + deli + data[2] + deli
						+ data[3] + deli + data[5] + "@\r\n");
				// }
				b_writer.write(text.toString(), 0, text.toString().length());
				text = new StringBuffer();
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

	public String replaceCrLf(String src) {
		return src.replace('\r', '@');
	}
}

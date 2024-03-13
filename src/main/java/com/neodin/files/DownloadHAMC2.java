package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 13   Fields: 2

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadHAMC2 extends ResultDownload {
	private String curFileName;

	boolean isDebug = false;

	boolean isData = false;

	public DownloadHAMC2() {
		curFileName = null;
		isDebug = false;
	}

	public DownloadHAMC2(String id, String fdat, String tdat, Boolean isRewrite) {
		curFileName = null;
		isDebug = false;
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
				if (b_writer != null) {
					b_writer.close();
					b_writer = null;
				}
				if (o_writer != null) {
					o_writer.close();
					o_writer = null;
				}
				if (f_outStream != null) {
					f_outStream.close();
					f_outStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private String getCurFileName() {
		if (curFileName != null)
			return curFileName + ".txt";
		else
			return curFileName;
	}

	public String getReference(String parm[]) {
		String refer = getReferenceValue(parm);
		return refer;
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
		if (getCurFileName() != null)
			try {
				if (!cpyFilePath.exists())
					cpyFilePath.mkdir();
				file = new File("e:\\" + getCurFileName());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void processingData(int cnt) {
		try {
			String hosCode[] = (String[]) getDownloadData().get(
					"\uBCD1\uC6D0\uCF54\uB4DC");
			String rcvDate[] = (String[]) getDownloadData().get(
					"\uC811\uC218\uC77C\uC790");
			String rcvNo[] = (String[]) getDownloadData().get(
					"\uC811\uC218\uBC88\uD638");
			String specNo[] = (String[]) getDownloadData().get(
					"\uAC80\uCCB4\uBC88\uD638");
			String chartNo[] = (String[]) getDownloadData().get(
					"\uCC28\uD2B8\uBC88\uD638");
			String patName[] = (String[]) getDownloadData().get(
					"\uC218\uC2E0\uC790\uBA85");
			String inspectCode[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uCF54\uB4DC");
			String inspectName[] = (String[]) getDownloadData().get(
					"\uAC80\uC0AC\uBA85");
			String seq[] = (String[]) getDownloadData().get(
					"\uC77C\uB828\uBC88\uD638");
			String result[] = (String[]) getDownloadData().get("\uACB0\uACFC");
			String resultType[] = (String[]) getDownloadData().get(
					"\uACB0\uACFC\uD0C0\uC785");
//			String[] _tmp = (String[]) getDownloadData().get(
//					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			String sex[] = (String[]) getDownloadData().get("\uC131\uBCC4");
//			String[] _tmp1 = (String[]) getDownloadData().get("\uB098\uC774");
			String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			String history[] = (String[]) getDownloadData().get("\uC774\uB825");
			String rmkCode[] = (String[]) getDownloadData().get(
					"\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			String unit[] = (String[]) getDownloadData().get(
					"\uCC38\uACE0\uCE58\uB2E8\uC704\uB4F1");
			for (int i = 0; i < cnt; i++) {
				isData = true;
				String data[] = new String[11];
//				String[] _tmp2 = new String[3];
//				String[] _tmp3 = new String[4];
				data[0] = chartNo[i];
				data[1] = patName[i];
				data[2] = specNo[i].trim();
				setCurFileName(data[2]);
				closeDownloadFile();
				makeDownloadFile();
				data[3] = inspectCode[i];
				data[6] = rcvDate[i] + "-" + rcvNo[i] + "-"
						+ inspectCode[i].trim();
				data[7] = inspectName[i].trim();
				data[4] = "";
				data[5] = "";
				data[8] = "";
				data[9] = "";
				data[10] = "";
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				if (resultType[i].trim().equals("C")) {
					data[8] = getUnit(unit[i]).trim();
					data[9] = getReference(
							new String[] { inspectCode[i], lang[i], history[i],
									sex[i] }).trim();
					data[4] = result[i].trim();
				} else {
					data[5] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]).trim();
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
				text.append(data[1] + "\t" + data[2] + "\r\n" + data[5]
						+ "\r\n");
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
		return src.replace('\r', '@').replace('\n', '^');
	}

	private void setCurFileName(String newCurFileName) {
		curFileName = newCurFileName;
	}
}

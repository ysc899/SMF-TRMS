package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.neodin.comm.AbstractDpc;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadCJMCT extends ResultDownload {

	boolean isDebug;

	public DownloadCJMCT() {
		isDebug = false;
	}

	public DownloadCJMCT(String id, String fdat, String tdat, Boolean isRewrite) {

		isDebug = false;
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public void closeDownloadFile() {
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

	public void makeDownloadFile() {
		try {
			file = new File(savedir + mkOutTextFile());
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
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"\uBCD1\uC6D0\uAC80\uC0AC\uCF54\uB4DC");
			String data[] = new String[7];
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				data[0] = rcvDate[i].trim();
				data[1] = chartNo[i];
				data[2] = specNo[i].trim();
				data[3] = patName[i];
				data[4] = clientInspectCode[i].trim();
				data[5] = inspectName[i].trim();
				if (resultType[i].trim().equals("C"))
					data[6] = result[i];
				else if (((AbstractDpc) getDpc()
						.get("\uBB38\uC7A5\uACB0\uACFC"))
						.processDpc(new Object[] { hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i] }))
					data[6] = ((AbstractDpc) getDpc().get(
							"\uBB38\uC7A5\uACB0\uACFC")).getParm()
							.getStringParm(5);
				else
					data[6] = "";
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}
				text.append("sutak," + getDateCut(data[2], "\r\n").trim() + ","
						+ "100," + getDateCut(data[4], "\r\n").trim() + ","
						+ getDateCut(data[6], "\r\n").trim() + ","
						+ getDateCut(data[3], "\r\n").trim() + ","
						+ getDateCut(data[5], "\r\n").trim() + ","
						+ getDateCut(data[1], "\r\n").trim()
						+ System.getProperty("line.separator"));
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
}

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 0

package com.neodin.files;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadCham extends ResultDownload {

	public DownloadCham() {
		initialize();
	}

	public void closeDownloadFile() {
		try {
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void makeDownloadFile() {
		row = 2;
		row2 = 1;
		try {
			workbook = Workbook
					.createWorkbook(new File(savedir + makeOutFile()));
			wbresult = workbook.createSheet("Result", 0);
			wbremark = workbook.createSheet("Remark", 1);
			String ArraryResult[] = null;
			label = new Label(
					0,
					0,
					"(\uC7AC)\uB124\uC624\uB518\uC758\uD559\uC5F0\uAD6C\uC18C   \uCCAB\uBC88\uC7AC ,\uB450\uBC88\uC9F8 Row - \uC5EC\uC720 \uB808\uCF54\uB4DC \uC785\uB2C8\uB2E4.\uD56D\uC2DC \uACB0\uACFC\uB294 3\uBC88\uC9F8 Row \uBD80\uD130 \uC785\uB2C8\uB2E4");
			wbresult.addCell(label);
			ArraryResult = (new String[] { "\uAC80\uCCB4\uBC88\uD638",
					"\uC218\uC2E0\uC790\uBA85", "\uC131\uBCC4", "\uB098\uC774",
					"\uCC28\uD2B8\uBC88\uD638", "\uC811\uC218\uC77C\uC790",
					"\uC811\uC218\uBC88\uD638", "\uAC80\uC0AC\uCF54\uB4DC",
					"\uAC80\uC0AC\uBA85", "\uBB38\uC790\uACB0\uACFC",
					"\uBB38\uC7A5\uACB0\uACFC", "H/L", "Remark\uBC88\uD638",
					"\uCC38\uACE0\uCE58",
					"\uC8FC\uBBFC\uB4F1\uB85D\uBC88\uD638" });
			String ArraryRemark[] = { "\uAC80\uCCB4\uBC88\uD638",
					"Remark \uBC88\uD638", "Remark\uB0B4\uC6A9" };
			for (int i = 0; i < ArraryResult.length; i++) {
				label = new Label(i, 1, ArraryResult[i]);
				wbresult.addCell(label);
			}

			for (int i = 0; i < ArraryRemark.length; i++) {
				label = new Label(i, 0, ArraryRemark[i]);
				wbremark.addCell(label);
			}

		} catch (Exception e) {
//			System.out
//					.println("OCS \uD30C\uC77C\uC4F0\uAE30 \uC2A4\uB808\uB4DC \uC624\uB958"
//							+ e.getMessage());
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
			String age[] = (String[]) getDownloadData().get("\uB098\uC774");
			String securityNo[] = (String[]) getDownloadData().get(
					"\uC8FC\uBBFC\uBC88\uD638");
			String highLow[] = (String[]) getDownloadData().get(
					"\uACB0\uACFC\uC0C1\uD0DC");
			String lang[] = (String[]) getDownloadData().get("\uC5B8\uC5B4");
			String history[] = (String[]) getDownloadData().get("\uC774\uB825");
			String rmkCode[] = (String[]) getDownloadData().get(
					"\uB9AC\uB9C8\uD06C\uCF54\uB4DC");
			String data[] = new String[17];
			String data2[] = new String[3];
			String remark[] = new String[4];
			for (int i = 0; i < cnt; i++) {

				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				data[0] = specNo[i].trim();
				data[1] = patName[i];
				data[2] = sex[i];
				data[3] = age[i];
				data[4] = chartNo[i];
				data[5] = rcvDate[i].trim();
				data[6] = rcvNo[i].trim();
				data[7] = inspectCode[i].trim();
				data[8] = inspectName[i];
				data[14] = securityNo[i];
				if (resultType[i].trim().equals("C")) {
					data[9] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
					data[13] = getReferenceValue(remark);
					data[10] = "";
				} else {
					data[9] = "";
					data[13] = "";
					data[10] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				data[11] = highLow[i];
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[5].trim())
								|| !kumdata[1].trim().equals(data[6].trim())
								|| !kumdata[2].trim().equals(data[12].trim())) {
							row2++;
							data[12] = rmkCode[i].trim();
							data2[0] = data[0];
							data2[1] = data[12];
							data2[2] = getReamrkValue(hosCode[i], rcvDate[i],
									rcvNo[i], rmkCode[i]);
							kumdata[0] = data[5].trim();
							kumdata[1] = data[6].trim();
							kumdata[2] = data[12].trim();
							for (int j = 0; j < data2.length; j++) {
								label = new Label(j, row2, data2[j]);
								wbremark.addCell(label);
							}

							row2++;
						}
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				else
					data[14] = "";
				for (int k = 0; k < data.length; k++) {
					label = new Label(k, row, data[k]);
					wbresult.addCell(label);
				}

				row++;
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

	public DownloadCham(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}
}

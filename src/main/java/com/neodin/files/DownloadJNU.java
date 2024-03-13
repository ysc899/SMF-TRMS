package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 11   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

public class DownloadJNU extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	protected String gubun1 = "@============================================================@";

	protected String gubun2 = "@------------------------------------------------------------@";

	public DownloadJNU() {
		// isDebug = false;
		// System.out.println("cjmct");
	}

	public DownloadJNU(String id, String fdat, String tdat, Boolean isRewrite) {
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
		// if (isData)
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

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-10-08 오전 3:28:30)
	 * 
	 * @return java.lang.String
	 */
	private String getResultRemark(String param) {
		String str = "";
		Object[] remark1 = Common.getArrayTypeData_CheckLast(param);
		if (remark1 != null) {

			// !
			int len = remark1.length;
			for (int i = 0; i < len; i++) {
				str += remark1[i].toString() + "@";
			}
		}
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-10-08 오전 12:46:47)
	 */
	public String getResultTriple(java.lang.String[] id) {

		//

		// !
		boolean is90027 = false; // First double maker
//		boolean is99934 = false; // Integrated test 1차
		boolean is99935 = false; // Sequential test 1차
		boolean is99936 = false; // Sequential test 2차
//		boolean is90100 = false; // Quad test
//		boolean is90028 = false; // Triple marker
//		boolean is90029 = false; // Double marker

		//

		AbstractDpc dpc_;
		DPCParameter parm_;
		// ! 검사결과
		// 가져오기***************************************************************
		String[] tempArray = new String[24];
		String rtnResult = "";
		rtnResult = "<< Triple 검사결과보고 >>@@";
		// rtnResult += "* 검사방법 : 선별검사 \n";
		// rtnResult += "* 선별검사결과 : \n";

		try {
			dpc_ = new com.neodin.result.DpcOfMC308RMS4();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자,
																	// 접수번호
			parm_ = dpc_.getParm();

			// ! 임신중기검사
			tempArray[0] = parm_.getStringParm(14).trim(); // AFP
			tempArray[1] = parm_.getStringParm(15).trim(); // MOM
			// !
			tempArray[2] = parm_.getStringParm(20).trim(); // FREE
			tempArray[3] = parm_.getStringParm(21).trim(); // MOM

			tempArray[4] = parm_.getStringParm(18).trim(); // HCG
			tempArray[5] = parm_.getStringParm(19).trim(); // MOM

			tempArray[6] = parm_.getStringParm(16).trim(); // PAPP
			tempArray[7] = parm_.getStringParm(17).trim(); // MOM

			tempArray[8] = parm_.getStringParm(22).trim(); // UE3
			tempArray[9] = parm_.getStringParm(23).trim(); // MOM

			tempArray[10] = parm_.getStringParm(24).trim(); // NT
			tempArray[11] = parm_.getStringParm(25).trim(); // MOM

			tempArray[12] = parm_.getStringParm(34).substring(1, 16).trim(); // IuhibinA
			tempArray[13] = parm_.getStringParm(34).substring(16, 31).trim(); // MOM

			// ! 결과및 판정
			tempArray[14] = parm_.getStringParm(26).trim(); // 나이
			tempArray[15] = parm_.getStringParm(27).trim(); // 결과
			tempArray[16] = parm_.getStringParm(28).trim(); // 다운
			tempArray[17] = parm_.getStringParm(29).trim(); // 결과
			tempArray[18] = parm_.getStringParm(30).trim(); // 에드워드
			tempArray[19] = parm_.getStringParm(31).trim(); // 결과
			tempArray[20] = parm_.getStringParm(32).trim(); // 신경관결손
			tempArray[21] = parm_.getStringParm(33).trim(); // 결과

			// 문장결과(판정) 불러오기
			dpc_ = new com.neodin.result.DpcOfMC308RMS3();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자,
																	// 접수번호
			parm_ = dpc_.getParm();

			// !
			tempArray[22] = parm_.getStringParm(3).trim(); // 다운/에드워드 판정
			tempArray[23] = parm_.getStringParm(4).trim(); // 신경관 결손
		} catch (Exception e) {
		}
		rtnResult += gubun1;
		rtnResult += appendBlanks("검사항목", 10) + appendBlanks("결      과", 15)
				+ appendBlanks("MOM", 10);
		rtnResult += gubun2;
		// !
		if (!tempArray[0].equals(""))
			rtnResult += appendBlanks("AFP", 10)
					+ appendBlanks(tempArray[0] + " ng/mL", 15)
					+ appendBlanks(tempArray[1] + " mom", 10) + "@";
		if (!tempArray[2].equals(""))
			if(Integer.parseInt(id[0].toString().trim())>20140430){
				rtnResult += appendBlanks("hCG", 10)
						+ appendBlanks(tempArray[2] + " IU/mL", 15)
						+ appendBlanks(tempArray[3] + " mom", 10) + "@";
				}else{
					rtnResult += appendBlanks("Freeb-HCG", 10)
					+ appendBlanks(tempArray[2] + " ng/mL", 15)
					+ appendBlanks(tempArray[3] + " mom", 10) + "@";
					
				}

		if (!tempArray[4].equals(""))
			rtnResult += appendBlanks("HCG", 10)
					+ appendBlanks(tempArray[4] + " ug/mL", 15)
					+ appendBlanks(tempArray[5] + " mom", 10) + "@";
		if (!tempArray[6].equals(""))
			if(Integer.parseInt(id[0].toString().trim())>20140430){
				rtnResult += appendBlanks("PAPP-A", 10)
						+ appendBlanks(tempArray[6] + " ng/mL", 15)
						+ appendBlanks(tempArray[7] + " mom", 10) + "@";
				}else{
					rtnResult += appendBlanks("PAPP-A", 10)
					+ appendBlanks(tempArray[6] + " IU/mL", 15)
					+ appendBlanks(tempArray[7] + " mom", 10) + "@";		
				}
		if (!tempArray[8].equals(""))
			rtnResult += appendBlanks("uE3", 10)
					+ appendBlanks(tempArray[8] + " nmol/L", 15)
					+ appendBlanks(tempArray[9] + " mom", 10) + "@";
		if (!tempArray[10].equals(""))
			rtnResult += appendBlanks("NT", 10)
					+ appendBlanks(tempArray[10] + " mm", 15)
					+ appendBlanks(tempArray[11] + " mom", 10) + "@";
		if (!tempArray[12].equals(" "))
			rtnResult += appendBlanks(" Inhibin A ", 12)
					+ appendBlanks(tempArray[12] + " pg / mL ", 13)
					+ appendBlanks(tempArray[13] + " mom ", 10) + "@";
		rtnResult += gubun2;
		// !
		// 참고치 불러오기
		dpc_ = new com.neodin.result.DpcOfMC308RM3();
		dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자, 접수번호
		parm_ = dpc_.getParm();
		try {
			if (parm_.getStringParm(5) != null) {
				String pcode = parm_.getStringParm(5);
				if (pcode.equals("99934")) {
					is99934 = true;
				}
				if (pcode.equals("99935")) {
					is99935 = true;
				}
				if (pcode.equals("99936")) {
					is99936 = true;
				}
				if (pcode.equals("90027")) {
					is90027 = true;
				}
			}
		} catch (Exception ee) {
		} //

		if (is99935) {

			// } else if (is99935) { //! Sequential test 1차
			// drawString("Risk cut-off 1:270", 428, index); //나이에 의한
			// drawString("Risk cut-off 1: 41", 428, index + 16); //다운증후군
			// drawString("Risk cut-off 1:300", 428, index + 16 + 16); //에드워드
			// drawString("AFP cut-off 2.5 MoM", 423, index + 16 + 16 + 16);
			// //신경관결손

			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22)
					+ appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13)
					+ appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22)
					+ appendBlanks(" 2.5 MOM ", 12)
					+ appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "@" + appendBlanks(" 에드워드증후군 ", 22)
					+ appendBlanks(" 1 : 300 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "@" + appendBlanks(" 다운증후군 ", 22)
					+ appendBlanks(" 1 : 41 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		}
		if (is99936) {
			// } else if (is99936) { //! Sequential test 2차
			// drawString("Risk cut-off 1:270", 428, index); //나이에 의한
			// drawString("Risk cut-off 1:450", 428, index + 16); //다운증후군
			// drawString("Risk cut-off 1:200", 428, index + 16 + 16); //에드워드
			// drawString("AFP cut-off 2.5 MoM", 423, index + 16 + 16 + 16);
			// //신경관결손

			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22)
					+ appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13)
					+ appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22)
					+ appendBlanks(" 2.5 MOM ", 12)
					+ appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "@" + appendBlanks(" 에드워드증후군 ", 22)
					+ appendBlanks(" 1 : 200 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "@" + appendBlanks(" 다운증후군 ", 22)
					+ appendBlanks(" 1 : 450 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		}
		if (is90027) {

			// } else if (is90027) { //! First double marker
			// drawString("Risk cut-off 1:270", 428, index); //나이에 의한
			// drawString("Risk cut-off 1:250", 428, index + 16); //다운증후군
			// drawString("Risk cut-off 1:300", 428, index + 16 + 16); //에드워드
			// drawString("AFP cut-off 2.5 MoM", 423, index + 16 + 16 + 16);
			// //신경관결손

			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22)
					+ appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13)
					+ appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22)
					+ appendBlanks(" 2.5 MOM ", 12)
					+ appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "@" + appendBlanks(" 에드워드증후군 ", 22)
					+ appendBlanks(" 1 : 300 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "@" + appendBlanks(" 다운증후군 ", 22)
					+ appendBlanks(" 1 : 250 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		} else {
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22)
					+ appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13)
					+ appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22)
					+ appendBlanks(" 2.5 MOM ", 12)
					+ appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "@" + appendBlanks(" 에드워드증후군 ", 22)
					+ appendBlanks(" 1 : 200 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "@" + appendBlanks(" 다운증후군 ", 22)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		}
		rtnResult += "@@* Comment : @@";
		// if (id[2].toString().equals(" 41381 ")) {
		if (!tempArray[23].equals(" "))
			rtnResult += getResultRemark(tempArray[23] + "@");
		// } else {
		if (!tempArray[22].equals(" "))
			rtnResult += getResultRemark(tempArray[22] + "@");
		// }
		return rtnResult;
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

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-10-08 오전 1:10:21)
	 * 
	 * @return boolean
	 * @param param
	 *            java.lang.String
	 */
	public boolean isTriple(String gcd) {
		if (gcd.equals("41381"))
			return true;
		if (gcd.equals("41382"))
			return true;
		if (gcd.equals("41383"))
			return true;
		if (gcd.equals("41338"))
			return true;
		if (gcd.equals("41121"))
			return true;
		if (gcd.equals("41359"))
			return true;
		if (gcd.equals("41431"))
			return true;
		if (gcd.equals("42000"))
			return true;
		return false;
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
//			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");
			String resultType[] = (String[]) getDownloadData().get("결과타입");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"병원검사코드");
//			String sex[] = (String[]) getDownloadData().get("성별");
//			String[] _tmp = (String[]) getDownloadData().get("나이");
//			String lang[] = (String[]) getDownloadData().get("언어");
//			String history[] = (String[]) getDownloadData().get("이력");
//			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
//			String unit[] = (String[]) getDownloadData().get("참고치단위등");
//			String cns[] = (String[]) getDownloadData().get("처방번호");
			String deli = "\t";
			for (int i = 0; i < cnt; i++) {
				String thisTimeCode="";
				String curDate = "";
				String curNo = "";
				String data[] = new String[11];
//				String[] _tmp1 = new String[3];
//				String[] _tmp2 = new String[4];
				data[0] = chartNo[i]; // 차트번호
				data[1] = specNo[i]; // 검체번호
				data[2] = clientInspectCode[i].trim(); // 병원검사코드
				data[4] = patName[i].trim(); // 환자명
				data[5] = rcvDate[i]; // 처방번호
				if (resultType[i].trim().equals("C")) {
					data[3] = result[i].trim();
					try {
						if (isTriple(inspectCode[i])) {
							data[3] = getResultTriple(new String[] {
									rcvDate[i], rcvNo[i], inspectCode[i] });
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for ( thisTimeCode = inspectCode[i++].trim()
									.substring(0, 5); isTriple(inspectCode[i])
									&& curDate.equals(rcvDate[i].trim())
									&& curNo.equals(rcvNo[i].trim());) {
								if (++i == cnt)
									break;
							}
							i--;
						} else if (inspectCode[i].trim().length() == 7
								&& (inspectCode[i].trim().substring(0, 5)
										.equals("41026"))) {
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							for ( thisTimeCode = inspectCode[i++].trim()
									.substring(0, 5); thisTimeCode
									.equals(inspectCode[i].trim().substring(0,
											5))
									&& curDate.equals(rcvDate[i].trim())
									&& curNo.equals(rcvNo[i].trim());) {
								if (inspectCode[i].trim().substring(5, 7)
										.equals("01")) {
									data[3] = result[i];
								}
								if (++i == cnt)
									break;
							}
							i--;
						}
					} catch (Exception w) {
					}
				} else {
					data[3] = "별지참조";
				}
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream,"MS949");   // 인코딩 수정후
//					o_writer = new OutputStreamWriter(f_outStream);            // 인코딩 수정전
					b_writer = new BufferedWriter(o_writer);
				}
				// 5처방번호 0차트번호 . 1검체번호 ,4환자명, 2검사코드 ,3검사결과

				if (data[1].trim().equals("")) {
					data[1] = deli;
				}
				if (data[2].trim().equals("")) {
					data[2] = deli;
				}
				if (data[0].trim().equals("")) {
					data[0] = deli;
				}
				text.append(addBlanks(data[5], 8) + deli
						+ addBlanks(data[0], 8) + deli + addBlanks(data[1], 13)
						+ deli + addBlanks(data[4], 10) + deli
						+ addBlanks(data[2], 8) + deli + data[3] + "\r\n");
				// if (isDebug) {
				// System.out.println(text.toString());
				// }
				b_writer.write(text.toString(), 0, text.toString().length());
				text = new StringBuffer();
			}
			if (cnt == 400) {
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			setParameters(null);
		}
	}

	public String replaceCrLf(String src) {
		return src.replace('\r', '@').replace('\n', '^');
	}
}

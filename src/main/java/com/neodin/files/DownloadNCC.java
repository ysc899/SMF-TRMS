package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;
import jxl.write.WritableSheet;

import com.neodin.comm.Common;

//* 국립암센터
public class DownloadNCC extends ResultDownload {
	boolean isDebug = false;

	boolean isData = true;

	protected WritableSheet wbsnsi;

	protected WritableSheet wbmott;

	protected int rowsnsi;

	protected int rowmott;

	public DownloadNCC() {
		initialize();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-10-25 오후 6:54:21)
	 * 
	 * @return java.lang.String
	 */
	public String[] getUintCut(String unt___) {
		String str[] = new String[] { "", "", "" };
		str = Common.getDataCut(unt___, ",");
		if (str.length == 2) {
			str = new String[] { str[0], str[1].replace(',', ' ').trim(), "" };
		}
		return str;
	}

	public DownloadNCC(String id, String fdat, String tdat, Boolean isRewrite) {
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
		if (!isDebug && isData) {
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
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-03-12 오전 10:48:39)
	 * 
	 * @return java.lang.String
	 */
	public String getColonies(String str) {
		try {
			if (str.indexOf("1+") != -1 || str.indexOf("1 +") != -1)
				return "1+ (50-100 개 집락)";
			if (str.indexOf("2+") != -1 || str.indexOf("2 +") != -1)
				return "2+ (100-200 개 집락)";
			if (str.indexOf("3+") != -1 || str.indexOf("3 +") != -1)
				return "3+ (균집락이 구별되어 보이나 거의 융합)";
			if (str.indexOf("4+") != -1 || str.indexOf("4 +") != -1)
				return "4+ (서로 융합)";
			if (str.indexOf("5+") != -1 || str.indexOf("5 +") != -1)
				return "5+";
			if (str.indexOf("6+") != -1 || str.indexOf("6 +") != -1)
				return "6+";
			if (str.indexOf("7+") != -1 || str.indexOf("7 +") != -1)
				return "7+";
			if (str.indexOf("8+") != -1 || str.indexOf("8 +") != -1)
				return "8+";
			if (str.indexOf("9+") != -1 || str.indexOf("9 +") != -1)
				return "9+";
			if (str.indexOf("10+") != -1 || str.indexOf("10 +") != -1)
				return "10+";
			if (str.toLowerCase().indexOf("growth") != -1
					|| str.toLowerCase().indexOf("only") != -1
					|| str.toLowerCase().indexOf("colonies") != -1) {
				return getColony(str);
			}
			return "";
		} catch (Exception ee) {
			return "죄송합니다 전산오류 입니다. 씨젠전산실로 전화 부탁드립니다. 02-2244-6500 (391) ";
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-03-31 오후 5:41:05)
	 */
	public String getColony(String str) {
		int f = -1;
		int t = -1;
		// int temp = 0;

		// !
		for (int i = 0; i < 100; i++) {
			if (str.toLowerCase().indexOf(i + " colony") > -1) {
				return i + "";
			}
		}
		for (int i = 0; i < 100; i++) {
			if (str.toLowerCase().indexOf(i + "colony") > -1) {
				return i + "";
			}
		}
		for (int i = 0; i < 100; i++) {
			if (str.toLowerCase().indexOf(i + "  colony") > -1) {
				return i + "";
			}
		}
		for (int i = 0; i < 100; i++) {
			if (str.toLowerCase().indexOf(i + "   colony") > -1) {
				return i + "";
			}
		}
		// !
		if (str.toLowerCase().indexOf("colonies") > -1)
			t = str.toLowerCase().indexOf("colonies");
		else if (str.toLowerCase().indexOf("colony") > -1)
			t = str.toLowerCase().indexOf("colony");
		else if (str.toLowerCase().indexOf("colo") > -1)
			t = str.toLowerCase().indexOf("colo");
		if (t == -1) {
			return "";
		}

		// !
		for (int i = t - 10; i < t + 1; i++) {
			try {
				Integer.parseInt(str.substring(i, t).trim());
				f = i;
				break;
			} catch (Exception e) {
				continue;
			}
		}

		// !
		String src = str.substring(f, t);
		try {
			return Integer.parseInt(src.trim()) + " 개 집락증식";
		} catch (Exception e) {
			return "	죄송합니다 전산오류 입니다. 네오딘전산실로 전화 부탁드립니다. 02-2244-6500 (391) ";
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-03-12 오전 10:26:46)
	 */
	public String getGrowth(String str) {
		if (str.trim().indexOf("No growth of AFB after 8 weeks culture") != -1
				|| str.toLowerCase().trim().indexOf("final report") != -1
				|| str.toLowerCase().trim().indexOf("final  report") != -1
				|| str.toLowerCase().trim().indexOf("최　종　결　과　보　고") != -1
				&& (str.toLowerCase().trim().indexOf("no growth") != -1)) {
			return "No acid fast bacilli isolated";
		}
		if (str.toLowerCase().indexOf("growth of") != -1
				|| str.toLowerCase().indexOf("growth of") != -1
				|| str.toLowerCase().trim().indexOf("growthof") != -1
				|| str.toLowerCase().trim().indexOf("growth  of") != -1) {

			// !
			String rltLine[] = Common.getDataCut(str.trim(), "\n");
			String src = "";
			for (int i = 1; i < rltLine.length; i++) {
				if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("liquid media 최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("liquid  media 최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("liquid media  최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("liquid  media 최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("solid media 최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("solid  media 최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("solid media  최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (rltLine[i].toString().trim().toLowerCase()
						.indexOf("solid  media  최종보고 :") != -1) {
					src += rltLine[i]
							.substring(
									rltLine[i].toLowerCase().indexOf("growth"))
							.toString().trim()
							+ "\r\n";
				} else if (i > 1) {
					if (rltLine[i].toString().trim().toLowerCase()
							.indexOf("중간보고") != -1
							|| rltLine[i].toString().trim().toLowerCase()
									.indexOf("최종보고") != -1) {
					} else {
						src += rltLine[i].toString().trim() + "\r\n";
					}
				}
			}
			return src.trim();
		}
		return "";
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-27 오후 3:21:08)
	 * 
	 * @return java.lang.String
	 */
	public String getMOTTremark() {
		String str = "";
		str += "\r\n<검사방법>    \r\n ";
		str += "중합효소연쇄반응 - 교잡반응법( PCR-Hybridization) \r\n\r\n";
		str += "<검사개요>\r\n";
		str += "마이코박테리아의 23S rRNA유전자의 다형성 부위를 이용하여 결핵균과 비결핵 마이코박테리아\r\n";
		str += "(Nontuberculosis mycobacteria NTM) 균종을 동정합니다. Biotin이 표지된 Primer와 검체 DNA를\r\n";
		str += "혼합하여 중합효소반응으로 증폭시킨 후 그 산물을 스트립에 접합되어 있는 각 NTM 균종에 특이한 \r\n";
		str += "Probe에 반응시킵니다. 이 때 발색한 특정 패턴을 판독하여 NTM의 종류를 동정합니다.\r\n\r\n";
		str += "<결과 해석>\r\n";
		str += "NTM은 M. tuberculosis complex(M. tuberculosis, M. Bovis, M. Africanum, M. Microti, M. canettii 와 \r\n";
		str += "나균 M. leprae )를 제외한 마이코박테리아를 말합니다. NTM 폐질환은 NTM 감염증의 90%이상을 \r\n";
		str += "차지하는 가장 흔한 형태이며,  M. avium 과  M. intracellulare 감염이 50-60%이상을 차지하고 있습니다. \r\n";
		str += "NTM 폐질환은 주로 과거에 결핵을 앓은 적이 있거나 흡연, 알코올 중독, COPD 등에 이환된 남자에서 \r\n";
		str += "폐상엽을 침범하는 공동성 병변으로 발현하는 경우와, 별다른 기저 질환이 없고 비흡연 중년 여성에서 \r\n";
		str += "기관지 확장증과 동반된 결절성 병변으로 나누어지게 됩니다.  NTM은 균종에 따라 각종 항균제에 \r\n";
		str += "대한 높은 내성을 나타내기 때문에 치료가 쉽지 않아 정확한 동정이 가장 중요합니다. 치료약제로 \r\n";
		str += "미국 흉부학회에서는 Azithromycin이나 Clarithromycin, Rifampin 혹은 Rifabutin, Ethambutol 과의 \r\n";
		str += "병합요법을 배양 음성이 된 후 1년간 유지하는 것을 추천하고 있습니다. \r\n";
		str += "\r\n검사기관 : (재단법인) 씨젠의료재단";
	//	str += "\r\n보 고  자 :            MT/함정일 MT/황상현 MD ";
	//	str += "\r\n보 고  자 :            MT/함정일 MT/정희정 MD ";
		str += "\r\n보 고  자 :            MT/강수진 MT/이혜영 MD ";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-12-28 오전 11:23:31)
	 * 
	 * @return java.lang.String
	 */
	public String getResult2(String rlt) {
		try {
			if (rlt.substring(0, 8).trim().toUpperCase().equals("NEGATIVE")
					|| rlt.substring(0, 8).trim().toUpperCase()
							.equals("POSITIVE")) {
				if (rlt.trim().length() < 9)
					return rlt;
				return rlt.substring(0, 8) + "(" + rlt.substring(8).trim()
						+ ")";
			}
			return rlt;
		} catch (Exception ee) {
			return "죄송합니다 전산오류 입니다. 네오딘전산실로 전화 부탁드립니다. 02-2244-6500 (391) ";
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-12-28 오전 11:23:31)
	 * 
	 * @return java.lang.String
	 */
	public String getResult3(String rlt) {
		try {
			// !
			if (rlt.substring(0, 8).trim().toUpperCase().equals("NEGATIVE")
					|| rlt.substring(0, 8).trim().toUpperCase()
							.equals("POSITIVE")) {
				if (rlt.trim().length() < 9)
					return rlt;
				return "Clostridium difficile Toxin A,B : "
						+ rlt.substring(0, 8)
						+ "("
						+ rlt.substring(8).trim()
						+ ")"
						+ "\n\n 참고치 : Negative : <0.13 \n              Equivocal : 0.13~0.36 \n              Positive : >=0.37 ";
			}
			return rlt;
		} catch (Exception ee) {
			return "죄송합니다 전산오류 입니다. 네오딘전산실로 전화 부탁드립니다. 02-2244-6500 (391) ";
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-06-08 오전 11:15:09)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public String[] getSensi(String str) {
		String rltLine[] = Common.getDataCut(str.trim(), "\n");
		String[] sensi3 = new String[] { "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "" };

		// "INH"0, "RFP"1, "SM"2, "EMB"3, "KM"4, "PTH"5, "CS"6, "OFX"7, "PAS"8,
		// "CPM"9, "PZA"10, "RBT"11, "MXF"12, "LFX"13, "AMK"14,
		// "INH(농도)15", "RFP(농도16)", "SM(농도)17", "EMB(농도)18", "KM(농도)19",
		// "PTH(농도)20", "CS(농도)21", "OFX(농도)22", "PAS(농도)23", "CPM(농도)24",
		// "PZA(농도)25", "RBT(농도)26", "MXF(농도)27", "LFX(농도)28", "AMK(농도)29",
		// "대지배조상에서의 발육정도"});

		int cnt = rltLine.length;
		if (cnt == 0)
			return null;

		// !
		String[] rltItem = null;
		for (int i = 0; i < cnt; i++) {
			rltItem = Common.getDataCut(rltLine[i], ",");
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("INH")) {
				sensi3[0] = rltItem[3].replace(',', ' ').trim();
				sensi3[15] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("RFP")) {
				sensi3[1] = rltItem[3].replace(',', ' ').trim();
				sensi3[16] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("SM(")) {
				sensi3[2] = rltItem[3].replace(',', ' ').trim();
				sensi3[17] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("EMB")) {
				sensi3[3] = rltItem[3].replace(',', ' ').trim();
				sensi3[18] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("KM(")) {
				sensi3[4] = rltItem[3].replace(',', ' ').trim();
				sensi3[19] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("PTH")) {
				sensi3[5] = rltItem[3].replace(',', ' ').trim();
				sensi3[20] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("CS(")) {
				sensi3[6] = rltItem[3].replace(',', ' ').trim();
				sensi3[21] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("OFX")) {
				sensi3[7] = rltItem[3].replace(',', ' ').trim();
				sensi3[22] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("PAS")) {
				sensi3[8] = rltItem[3].replace(',', ' ').trim();
				sensi3[23] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			// "INH"0, "RFP"1, "SM"2, "EMB"3, "KM"4, "PTH"5, "CS"6, "OFX"7,
			// "PAS"8, "CPM"9, "PZA"10, "RBT"11, "MXF"12, "LFX"13, "AMK"14,
			// "INH(농도)15", "RFP(농도16)", "SM(농도)17", "EMB(농도)18", "KM(농도)19",
			// "PTH(농도)20", "CS(농도)21", "OFX(농도)22", "PAS(농도)23", "CPM(농도)24",
			// "PZA(농도)25", "RBT(농도)26", "MXF(농도)27", "LFX(농도)28", "AMK(농도)29",
			// "대지배조상에서의 발육정도"});

			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("CPM")) {
				sensi3[9] = rltItem[3].replace(',', ' ').trim();
				sensi3[24] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("PZA")) {
				try {
					sensi3[10] = rltItem[3].replace(',', ' ').trim();
					sensi3[25] = Common.getDataCut(rltItem[1].trim(), "m")[0];
				} catch (Exception e) {
					sensi3[10] = rltItem[1].replace(',', ' ').trim();
					sensi3[25] = "";
				}
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("RBT")) {
				sensi3[11] = rltItem[3].replace(',', ' ').trim();
				sensi3[26] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("MXF")) {
				sensi3[12] = rltItem[3].replace(',', ' ').trim();
				sensi3[27] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("LEV")) {
				sensi3[13] = rltItem[3].replace(',', ' ').trim();
				sensi3[28] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("AMK")) {
				sensi3[14] = rltItem[3].replace(',', ' ').trim();
				sensi3[29] = Common.getDataCut(rltItem[1].trim(), "m")[0];
			}
			if (rltItem[0].trim().toUpperCase().substring(0, 3).equals("*대조")) {
				sensi3[30] = Common.getDataCut(rltItem[0].trim(), ":")[1];
			}
		}
		return sensi3;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-06-08 오전 11:15:09)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public String getSensi2(String str) {
		try {
			String rltLine[] = Common.getDataCut(str.trim(), "\n");
			String src = "";
			for (int i = 1; i < rltLine.length; i++) {
				src += rltLine[i].toString().trim() + " ";
			}
			return src;
		} catch (Exception ee) {
			return "죄송합니다 전산오류 입니다. 네오딘전산실로 전화 부탁드립니다. 02-2244-6500 (391) ";
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-03-12 오전 10:11:31)
	 * 
	 * @return java.lang.String
	 */
	public int getWeeksCulture(String str) {
		try {
			// if (str.toLowerCase().trim().indexOf("최종보고는") == -1 &&
			// (str.toLowerCase().trim().indexOf("최종보고") != -1 ||
			// str.toLowerCase().trim().indexOf("최종 보고") != -1)) {
			// return 8;
			// }
			if (str.toLowerCase().trim().indexOf("4 weeks") != -1
					|| str.toLowerCase().trim().indexOf("4weeks") != -1
					|| str.toLowerCase().trim().indexOf("4  weeks") != -1
					|| str.toLowerCase().trim().indexOf("2 주후에") != -1) {
				return 4;
			}
			if (str.toLowerCase().trim().indexOf("3 weeks") != -1
					|| str.toLowerCase().trim().indexOf("3weeks") != -1
					|| str.toLowerCase().trim().indexOf("3  weeks") != -1
					|| str.toLowerCase().trim().indexOf("2 주후에") != -1) {
				return 4;
			}
			if (str.toLowerCase().trim().indexOf("6 weeks") != -1
					|| str.toLowerCase().trim().indexOf("6weeks") != -1
					|| str.toLowerCase().trim().indexOf("6  weeks") != -1
					|| str.toLowerCase().trim().indexOf("8 주후에") != -1) {
				return 6;
			}
			if (str.toLowerCase().trim().indexOf("8 weeks") != -1
					|| str.toLowerCase().trim().indexOf("8weeks") != -1
					|| str.toLowerCase().trim().indexOf("8  weeks") != -1) {
				return 8;
			}
			if (str.toLowerCase().trim().indexOf("1 weeks") != -1
					|| str.toLowerCase().trim().indexOf("1weeks") != -1
					|| str.toLowerCase().trim().indexOf("1  weeks") != -1) {
				return 4;
			}
			if (str.toLowerCase().trim().indexOf("2 weeks") != -1
					|| str.toLowerCase().trim().indexOf("2weeks") != -1
					|| str.toLowerCase().trim().indexOf("2  weeks") != -1) {
				return 4;
			}
			if (str.toLowerCase().trim().indexOf("3 weeks") != -1
					|| str.toLowerCase().trim().indexOf("3weeks") != -1
					|| str.toLowerCase().trim().indexOf("3  weeks") != -1) {
				return 4;
			}
			if (str.toLowerCase().trim().indexOf("5 weeks") != -1
					|| str.toLowerCase().trim().indexOf("5weeks") != -1
					|| str.toLowerCase().trim().indexOf("5  weeks") != -1) {
				return 6;
			}
			if (str.toLowerCase().trim().indexOf("7 weeks") != -1
					|| str.toLowerCase().trim().indexOf("7weeks") != -1
					|| str.toLowerCase().trim().indexOf("7  weeks") != -1) {
				return 6;
			}
			return 4;
		} catch (Exception ee) {
			return 0;
		}
	}

	public void makeDownloadFile() {
		if (!isDebug) {
			row = 1;
			rowmott = 1;
			rowsnsi = 1;
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("배양검사", 0);
				wbsnsi = workbook.createSheet("약제감수성 검사", 1);
				wbmott = workbook.createSheet("MOTT", 2);
				String ArraryResult[] = null;
				ArraryResult = (new String[] { "의뢰일자", "환자번호", "환자명", "종목코드",
						"검체번호", "검사결과4", "검사결과6", "검사결과8", "집약(colony count)",
						"결핵결과" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
				ArraryResult = (new String[] { "의뢰일자", "환자번호", "환자명", "종목코드",
						"검체번호", "INH", "RFP", "SM", "EMB", "KM", "PTH", "CS",
						"OFX", "PAS", "CPM", "PZA", "RBT", "MXF", "LFX", "AMK",
						"INH(농도)", "RFP(농도)", "SM(농도)", "EMB(농도)", "KM(농도)",
						"PTH(농도)", "CS(농도)", "OFX(농도)", "PAS(농도)", "CPM(농도)",
						"PZA(농도)", "RBT(농도)", "MXF(농도)", "LFX(농도)", "AMK(농도)",
						"대지배조상에서의 발육정도" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbsnsi.addCell(label);
				}
				ArraryResult = (new String[] { "의뢰일자", "환자번호", "환자명", "종목코드",
						"검체번호", "SPACE1", "SPACE2", "SPACE3", "SPACE4",
						"MOTT PCR 결과" });
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbmott.addCell(label);
				}
			} catch (Exception _ex) {
			}
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
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");
			// String[] _tmp = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
			String cns[] = (String[]) getDownloadData().get("처방번호");
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String data[] = new String[18];
			String data_[] = new String[11];
			// String[] _tmp1 = new String[3];
			String[] _sensi = new String[36];
			String[] _sensi2 = new String[31];
			String remark[] = new String[4];
			String remarkCode = "";
			boolean isNoData = false;
			boolean isRemarkResult = false;
			for (int i = 0; i < cnt; i++) {
				// 특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028") && !inspectCode[i]
						.trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5)
								.equals("05029") && !inspectCode[i].trim()
								.equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5)
								.equals("05011") && !inspectCode[i].trim()
								.equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5)
								.equals("05025") && !inspectCode[i].trim()
								.equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				isRemarkResult = false;
				// !
				isNoData = false;
				data_ = new String[10];
				// String curDate = "";
				// String curNo = "";
				data[0] = "11370319";
				data[17] = cns[i];
				data[1] = specNo[i].trim();
				data[2] = patName[i];
				data[3] = bdt[i];
				data[4] = age[i];
				data[5] = chartNo[i];
				data[6] = rcvDate[i].trim();
				data[7] = rcvNo[i].trim();
				data[8] = inspectCode[i].trim();
				data[9] = clientInspectCode[i].trim();
				data[10] = inspectName[i];
				data[14] = " ";
				data[16] = securityNo[i];

				// cdy test
				if ("백희숙".equals(patName[i])) {
					System.out.println("겨얼과가 안나와!");
				}

				if (inspectCode[i].toString().trim().equals("7125101")
						|| inspectCode[i].toString().trim().equals("7125102")
						|| inspectCode[i].toString().trim().equals("7125103")) {
					continue;
				} else if (inspectCode[i].toString().trim().equals("7125100")) {
					// data_[5] = result[i] + "  " +
					// getUintCut(unit[i].trim())[2] + "\r\n";
					remarkCode = "AAO";
					data[12] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i],
							remarkCode).replaceAll("\r\n", "\r\n\r\n");
					data_[9] = data[12];
					data_[5] = data[12]; // 4주
				} else if (inspectCode[i].toString().trim().equals("72046")) {
					data_[9] = "환자결과 : " + result[i] + "\r\n" + getMOTTremark();
					remarkCode = "AG6";
					String remark_str = getReamrkValue(hosCode[i], rcvDate[i],
							rcvNo[i], remarkCode);
					if (remark_str.length() > 50) {
						data[12] = "환자결과 : "
								+ result[i]
								+ "\r\n"
								+ getMOTTremark()
								+ "\r\n"
								+ remark_str.substring(
										remark_str.lastIndexOf("\r\n"),
										remark_str.length());
					} 
					 
					else {
						data[12] = "환자결과 : " + result[i];
					}
					
					data_[5] = data[12]; // 4주

				}
				else if (inspectCode[i].trim().substring(0, 5)
						.equals("72242")
						|| inspectCode[i].trim().substring(0, 5)
								.equals("72018")
						|| inspectCode[i].trim().substring(0, 5)
								.equals("21061")) {
					String curDate = rcvDate[i];
					String curNo = rcvNo[i];
					data[12] = appendBlanks("검  사  명 ", 30) + "\t"
							+ appendBlanks("결    과", 21);
					data[12] += getDivide() + "\r\n"
							+ appendBlanks(inspectName[i], 30) + "\t"
							+ appendBlanks(result[i], 21);
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						if (!result[i].toString().trim().equals("")) {
							data[12] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30) + "\t"
									+ appendBlanks(result[i], 21);
						}
						if (++i == cnt || i > cnt)
							break;
					}
					i--;
					data_[5] = data[12];

					data_[5] = data_[5].replaceAll("음성입니다", ": Negative");
					data_[5] = data_[5].replaceAll("양성입니다", ": Positive");

				} else if (inspectCode[i].trim().substring(0, 5).equals("72241")
						||inspectCode[i].trim().substring(0, 5).equals("81128")
						||inspectCode[i].trim().substring(0, 5).equals("72182")
						||inspectCode[i].trim().substring(0, 5).equals("72039")) {
					String curDate = rcvDate[i];
					String curNo = rcvNo[i];
					data[12] = appendBlanks("검  사  명 ", 30) + "\t"
							+ appendBlanks("결    과", 21);
					data[12] += getDivide() + "\r\n"
							+ appendBlanks(inspectName[i], 30) + "\t"
							+ appendBlanks(result[i], 21);
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						if (!result[i].toString().trim().equals("")) {
							data[12] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30) + "\t"
									+ appendBlanks(result[i], 21)
									;
						}
						if (++i == cnt || i > cnt)
							break;
					}
					i--;
					data_[5] = data[12]+ "\r\n"+ getReamrkValue99NCC(hosCode[i], rcvDate[i], rcvNo[i],
							rmkCode[i]).replaceAll("\r\n", "\r\n\r\n");

				} 
				
				//20170720 양태용 72237 부속 검사인데 리마크만 노출 요청하여 프로그램 수정 
				else if (inspectCode[i].trim().substring(0, 5).equals("72237")||inspectCode[i].trim().substring(0, 5).equals("72227")) {
					String curDate = rcvDate[i];
					String curNo = rcvNo[i];
					
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						
						if (++i == cnt || i > cnt)
							break;
					}
					i--;
					data_[5] = getReamrkValue99NCC(hosCode[i], rcvDate[i], rcvNo[i],
							rmkCode[i]).replaceAll("\r\n", "\r\n\r\n");
					
					
					

					}else if (inspectCode[i].trim().substring(0, 5).equals("21380") || inspectCode[i].trim().substring(0, 5).equals("21382")) {
					String curDate = rcvDate[i];
					String curNo = rcvNo[i];
					data[12] = ("");
					data[12] += (result[i]) + ("( ");
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						if (!result[i].toString().trim().equals("")) {
							data[12] += (inspectName[i]) + " : " + (result[i])
									+ " , ";
						}
						if (++i == cnt || i > cnt)
							break;
					}
					i--;
					data[12] += (")");
					data[12] = data[12].replace(", )", ")");

					data_[5] = data[12];
				}

				else if (inspectCode[i].trim().substring(0, 5).equals("41450")) {
					String curDate = rcvDate[i];
					String curNo = rcvNo[i];
					data[12] = ("");
					data[12] += appendBlanks(inspectName[i], 30) + " : "
							+ appendBlanks(result[i], 21) + "\r\n";
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						if (!result[i].toString().trim().equals("")) {
							data[12] += appendBlanks(inspectName[i], 30)
									+ " : " + appendBlanks(result[i], 21)
									+ "\r\n";
						}
						if (++i == cnt || i > cnt)
							break;
					}
					i--;
					data_[5] = data[12];
				}

				else if (inspectCode[i].toString().trim().equals("72039")) {
					remarkCode = "AF9";
					data[12] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i],
							remarkCode).replaceAll("\r\n", "\r\n\r\n");
					String resultNTM = data[12];
					String[] tmp = resultNTM.split("\r\n\r\n");

					if (tmp.length >= 3) {
						resultNTM = tmp[0] + "\r\n\r\n" + tmp[1] + "\r\n\r\n"
								+ tmp[2];
					}
					data_[9] = resultNTM;
					data_[5] = resultNTM; // 4주

					// } else if
					// (inspectCode[i].toString().trim().equals("31079")
					// && (result[i].toString().trim().equals("Remark 참조")
					// || result[i].toString().trim().equals(
					// "Remark참조")
					// || result[i].toString().trim().equals(
					// "Remark  참조") || result[i].toString()
					// .trim().equals("Remark    참조"))) {
					// data_[9] = "환자결과 : " + "\r\n" + "";
					// // !
					// remarkCode = "MB8";
					// data[12] = "\r\n"
					// + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i],
					// remarkCode);
					// // data[12] = "환자결과 : " + result[i] + "\r\n" + "검사방법 :
					// // Nested PCR";
					// // data_[9] = "환자결과 : " + result[i] + "\r\n" +
					// // getMOTTremark();
					// data_[5] = data[12]; // 4주

				} else if (inspectCode[i].toString().trim().equals("31022")
						|| inspectCode[i].toString().trim().equals("31079")) {
					data[12] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
					if (data[12] != null && !data[12].trim().equals("")) {
						// !
						_sensi2 = getSensi(data[12]);
						_sensi[0] = data[6]; // 의뢰일자
						_sensi[1] = data[5]; // 환자번호
						_sensi[2] = data[2]; // 환자명
						_sensi[3] = data[9]; // 종목코드
						_sensi[4] = data[1]; // 검체번호

						for (int x = 0; x < 31; x++) {
							_sensi[5 + x] = _sensi2[x];
						}
						isRemarkResult = true;
						data_[5] = data[12]; // 4주
					} else {
						remarkCode = "MB8";
						data[12] += ""
								+ getReamrkValue(hosCode[i], rcvDate[i],
										rcvNo[i], remarkCode).replaceAll(
										"\r\n", "\r\n\r\n");
						if (data[12].trim().equals("")) {
							remarkCode = "AG6";
							data[12] += "\r\n"
									+ getReamrkValue(hosCode[i], rcvDate[i],
											rcvNo[i], remarkCode).replaceAll(
											"\r\n", "\r\n\r\n");
						} else if (data[12].trim().equals("")) {
							remarkCode = "RB6";
							data[12] += "\r\n"
									+ getReamrkValue(hosCode[i], rcvDate[i],
											rcvNo[i], remarkCode).replaceAll(
											"\r\n", "\r\n\r\n");
						}
						if (!data[12].trim().equals("")) {
							isRemarkResult = true;
							data_[5] = data[12]; // 4주
						}
					}
				} else if (inspectCode[i].toString().trim().equals("31052")) {
					data[12] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
					// !
					data[12] = getSensi2(data[12]);
					data_[5] = data[12]; // 6주
					data[11] = "";
					data[15] = "";
					// !
					// }
				} else if (inspectCode[i].toString().trim().substring(0, 5)
						.equals("00928")) {
					if (inspectCode[i].trim().substring(5, 7).equals("00")) {
						isNoData = true;
					} else if (inspectCode[i].trim().substring(5, 7)
							.equals("01")
							|| inspectCode[i].trim().substring(5, 7)
									.equals("02")) {
						data[12] = result[i];
						data_[5] = data[12]; // 6주
						data[11] = "";
						data[15] = "";
						// } else {
						// continue;
					}
				} else if (inspectCode[i].toString().trim().substring(0, 5)
						.equals("31074")) {
					if (inspectCode[i].trim().substring(5, 7).equals("00")) {
						isNoData = true;
					} else if (inspectCode[i].trim().substring(5, 7)
							.equals("01")
							|| inspectCode[i].trim().substring(5, 7)
									.equals("02")) {
						data[12] = result[i];
						data_[5] = data[12]; // 6주
						data[11] = "";
						data[15] = "";
						// } else {
						// continue;
					}

				} else if (inspectCode[i].toString().trim().equals("21064")) {
					data[12] = getResult2(result[i]);
					data_[5] = data[12]; // 6주
					data[11] = "";
					data[15] = "";
					// !
				} else if (inspectCode[i].toString().trim().equals("31076")) {
					data[12] = getResult3(result[i]);
					data_[5] = data[12]; // 6주
					data[11] = "";
					data[15] = "";
					// !
				} else if (inspectCode[i].toString().trim().equals("00901")
						|| inspectCode[i].toString().trim().equals("21040")
						|| inspectCode[i].toString().trim().equals("41487")) {
					data[12] = result[i];
					data_[5] = data[12]; // 6주
					data[11] = "";
					data[15] = "";
					// !
				} else if (inspectCode[i].toString().trim().equals("00906")) {
					data_[5] = ""; // SPACE1
					data_[6] = ""; // SPACE2
					data_[7] = ""; // SPACE3
					data_[8] = ""; // SPACE4

					data[12] = result[i];
					data_[5] = data[12]; // 6주
					data[11] = "";
					data[15] = "";
					if (result[i].toString().trim().equals("")) {
						data[12] = getTextResultValue(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]);
						data_[5] = data[12]; // 6주
					}
					// !
				} else if (inspectCode[i].toString().trim().equals("71022")) {
					data[12] = result[i].replaceAll("\r\n", "");
					data_[5] = data[12]; // 6주
				} else {
					if (resultType[i].trim().equals("C")) {
						data[11] = result[i];
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						data[15] = getReferenceValue(remark);
						// TODO : 최대열 20160404 면역학 PRL 41102 21541
						data[12] = result[i];
						
					} 
					else{
					
								data[11] = "";
								data[15] = "";
								data[12] = getTextResultValue(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]);
								
					}
					if (rmkCode[i].trim().length() > 0
							&& !inspectCode[i].toString().trim().substring(0, 5).equals("71251")) {
						try {
							if (!kumdata[0].trim().equals(data[6].trim())
									|| !kumdata[1].trim()
											.equals(data[7].trim())
									|| !kumdata[2].trim().equals(remarkCode)) {
								remarkCode = rmkCode[i].trim();
								data[12] += "\r\n"
										+ getReamrkValue(hosCode[i],
												rcvDate[i], rcvNo[i],
												rmkCode[i]).replaceAll("\r\n",
												"\r\n\r\n");
								kumdata[0] = data[6].trim();
								kumdata[1] = data[7].trim();
								kumdata[2] = remarkCode;
							}
						} catch (Exception ee) {
						}
					} else {
						remarkCode = "";
					}
					int weeks = getWeeksCulture(data[12].toString().trim());
					if (weeks == 3 || weeks == 4) {
						data_[5] = data[12]; // 4주
						data_[6] = ""; // 6주
						data_[7] = ""; // 8주
					}
					if (weeks == 6) {
						data_[5] = ""; // 4주
						data_[6] = data[12]; // 6주
						data_[7] = ""; // 8주
					}
					if (weeks == 8) {
						data_[5] = ""; // 4주
						data_[6] = ""; // 6주
						data_[7] = data[12]; // 8주
					}
					data_[8] = getColonies(data[12].toString()); // 집략 개수
					data_[9] = getGrowth(data[12].toString()); // 결핵결과
				}
				if (inspectCode[i].toString().trim().equals("31079")) {
					remarkCode = rmkCode[i].trim();
					data_[5] = "\r\n"
							+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i],
									rmkCode[i]); //.replaceAll("", "\r\n"); 병원측 요청으로 공백을 엔터값 제외 처리 20170706 양태용
				}
				
				//리마크 수정 요청하여 99 길게 나오게 요청 20161130 양태용
				
				if (inspectCode[i].toString().trim().equals("21582")|| inspectCode[i].toString().trim().equals("21653")) {
					remarkCode = rmkCode[i].trim();
					data_[5] = ""
							+ getReamrkValue99NCC(hosCode[i], rcvDate[i], rcvNo[i],
									rmkCode[i]).replaceAll("\r\n", "\r\n\r\n");
				}
				

				
				//결과 나오고 리마크 수정 요청하여 99 길게 나오게 요청 20161130 양태용
				
				if (inspectCode[i].toString().trim().equals("00647") || inspectCode[i].toString().trim().equals("21192")) {
					if(rmkCode[i].toString().trim().equals("")){
						data_[5] = result[i];
					}
					else{
					remarkCode = rmkCode[i].trim();
					data_[5] = result[i] +"\n"
							+ getReamrkValue99NCC(hosCode[i], rcvDate[i], rcvNo[i],
									rmkCode[i]).replaceAll("\r\n", "\r\n\r\n");
					}
				}
				
//				if (inspectCode[i].toString().trim().equals("00647") || inspectCode[i].toString().trim().equals("21192")) {
//					remarkCode = rmkCode[i].trim();
//					data_[5] = result[i] +"\n"
//							+ getReamrkValue99NCC(hosCode[i], rcvDate[i], rcvNo[i],
//									rmkCode[i]).replaceAll("\r\n", "\r\n\r\n");
//				}
				// 20160425 72018 검사 결과 수정작업 
				// ||inspectCode[i].toString().trim().substring(0,
				// 5).equals("72018")
				if (inspectCode[i].toString().trim().substring(0, 5)
						.equals("72059")) {
					data_[5] = data_[5].replaceAll("음성입니다", ": Negative");
					data_[5] = data_[5].replaceAll("양성입니다", ": Positive");
				}

				data_[0] = data[6]; // 의뢰일자
				data_[1] = data[5]; // 환자번호
				data_[2] = data[2]; // 환자명
				data_[3] = data[9]; // 종목코드
				data_[4] = data[1]; // 검체번호
				// !
				if (!isNoData) {
					if (inspectCode[i].toString().trim().equals("72046")) {
						// data_[5] = ""; //SPACE1
						data_[6] = ""; // SPACE2
						data_[7] = ""; // SPACE3
						data_[8] = ""; // SPACE4
						if (!isDebug) {
							for (int k = 0; k < 10; k++) {
								label = new jxl.write.Label(k, rowmott,
										data_[k]);
								wbmott.addCell(label);
							}
						}
						rowmott++;
						// ! 추가
						if (!isDebug) {
							data_[9] = "";
							for (int k = 0; k < 10; k++) {
								label = new jxl.write.Label(k, row, data_[k]);
								wbresult.addCell(label);
							}
							row++;
						}
					} else if ((inspectCode[i].toString().trim().equals("31022") 
							|| inspectCode[i].toString().trim().equals("31079"))
							&& !isRemarkResult) {
						if (!isDebug) {
							for (int k = 0; k < 36; k++) {
								label = new jxl.write.Label(k, rowsnsi,
										_sensi[k]);
								wbsnsi.addCell(label);
							}
						}
						rowsnsi++;
					} else {
						if (!isDebug) {
							for (int k = 0; k < 10; k++) {
								label = new jxl.write.Label(k, row, data_[k]);
								wbresult.addCell(label);
							}
							row++;
						}
					}
				}
			}
			if (cnt == 400) {
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception e) {
			setParameters(null);
			e.printStackTrace();
		}
	}
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

/*
 엑셀
 */
public class DownloadFoundation_OLD extends ResultDownload {
	boolean debug = false;





	



	public DownloadFoundation_OLD() {
		initialize();
	}

	public DownloadFoundation_OLD(String id, String fdat, String tdat,
			Boolean isRewrite) {
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
		if (!debug) {
			try {
				workbook.write();
			} catch (Exception e) {
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-08 오후 3:07:55)
	 * 
	 * @return java.lang.String
	 */
	public String[] cutHrcvDateNumber(String str) {

		// !
		String src_[] = new String[] { "", "" };

		// !
		if (str == null || src_.equals(""))
			return src_;

		// !
		src_ = Common.getDataCut(str, "^");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

		// !
		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[0] = "";
		}
		try {
			src_[1] = src_[1].replace('D', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		return src_;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-08 오후 3:07:55)
	 * 
	 * @return java.lang.String
	 */
	public String[] cutHrcvDateNumber2(String str) {

		// !
		String src_[] = new String[] { "", "" };

		// !
		if (str == null || src_.equals(""))
			return src_;

		// !
		src_ = Common.getDataCut(str, "D");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

		// !
		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
			src_[0] = src_[0].substring(0, src_[0].length() - 1);
		} catch (Exception e) {
			src_[0] = "";
		}
		try {
			src_[1] = src_[1].replace(':', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		return src_;
	}



	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-01-30 오후 6:51:33)
	 * 
	 * @return java.lang.String
	 */
	public String getHCVMethods() {
		String str = "";
		str += "[검사방법]\r\n";
		str += " PCR- DNA sequencing (염기서열분석법)\r\n";
		str += " -> 분자생물학기술 중 가장 정확하고 최신방법이며 고가의 장비와 고도의 기술이 필요한 염기서열\r\n";
		str += " 분석법을 이용하여 HCV 유전자형을 직접 검출함. \r\n";
		str += "\r\n";
		str += " 혈청에서 RNA 추출  -> RNA를 reverse transcriptase를 이용하여 cDNA로 전환\r\n";
		str += " ->HCV 5' UTR 부위에서  특이  Primer 를 사용하여 PCR 증폭 ->PCR 산물을 전기영동으로 확인\r\n";
		str += " ->염기서열분석 (sequencing) 실시 ->HCV 유전자형 분석하여 결과 보고 \r\n";
		str += "\r\n";
		str += "[검사 의의]\r\n";
		str += "\r\n";
		str += " C형간염바이러스 (HCV)는 RNA 바이러스로서 전세계인구의 약 1%가 감염되어 있으며 만성감염이 \r\n";
		str += " 될 경우 간경화나 간암으로 진행 가능성이 높습니다.\r\n";
		str += "\r\n";
		str += " HCV는 6종의 주된 genotype이 있고 80종 이상의 subtype이 보고되고 있으며 HCV genotype중에서\r\n";
		str += " HCV 1b 형은 치료가 어렵고 간경화로 발전할 가능성이 높으며 간 이식 후에도 간 질환의 발생빈도가\r\n";
		str += " 높습니다.\r\n";
		str += "\r\n";
		str += " HCV는 유전자형에 따라 치료의 효과, 예후 등이 다르므로, HCV 감염이 되었을 경우 주기적인 \r\n";
		str += " HCV 정량검사 와 함께 HCV genotype 검사를 실시하여야 합니다.\r\n";
		str += "\r\n";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-06-16 오전 9:11:40)
	 * 
	 * @return java.lang.String
	 */
	public String getMTHFR() {
		String str = "";
		// //!

		str += "●   유전자: \r\n";
		str += "      MTHFR [5,10-methylenetetrahydrofolate reductase ; 1p36.3]\r\n";
		str += "\r\n";
		str += "●   검사방법:\r\n";
		str += "      MTHFR 유전자의 codon 1298 위치의 Glutamate(A)가 Alanine(C)으로 바뀌는 것  \r\n";
		str += "      (1298 A>C)을 PCR-RFLP를 이용하여 검사함.\r\n";

		// !

		str += "\r\n";
		str += "●   PCR-RFLP 방법:\r\n";
		str += "      목표하는 유전자의 특정부위를 PCR을 이용하여 증폭시키고, 관련된 염기서열을 인식하는 \r\n";
		str += "      제한효소를 처리한 후, 잘라진 PCR 단편을 전기영동으로 분석하여 돌연변이 여부를\r\n";
		str += "      판단하는 방법.\r\n";
		// !

		str += "\r\n";
		str += "●   임상적의의\r\n";
		str += "      - MTHFR은 엽산 대사에 중요한 작용을 하는 효소 유전자로 메틸기를 homocysteine에 \r\n";
		str += "        전달하여 methionine으로 합성하는 대사과정에 관여합니다.\r\n";
		str += "\r\n";
		str += "      - 가장 대표적인 MTHFR 유전자 변이는 C677T와 A1298C입니다.  \r\n";
		str += "\r\n";
		str += "      - 677T와 1298C의 복합돌연변이인 경우 677 homozygote와 유사한 임상적 의의를  \r\n";
		str += "        가진다고 보고되고 있습니다.\r\n";
		str += "\r\n";
		str += "      - 관련질환 : Hyperhomocysteinemia, Cardiovascular disease, Thromobisis 등\r\n";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-20 오전 9:39:07)
	 * 
	 * @return java.lang.String
	 */
	public String getQuantiFERON() {
		String str = "";
		str += "◈ 검사원리\r\n";
		str += " 혈액내에 존재하는 T세포에 결핵균 특이항원(ESAT-6,CFP-10,TB7.7(p4))을 노출시켜\r\n";
		str += "IFN-γ 농도를 측정하는 방법입니다. 결핵균에 노출되어 감작된 T세포들은 감작되지 않은\r\n";
		str += "T세포에 비해  IFN-γ 분비능이 높습니다. 증가된 IFN-γ은 효소면역분석법에 의해 측정됩니다.\r\n";
		str += "\r\n";
		str += "◈ 검사방법\r\n";
		str += " 음성대조(Nil control), 결핵균 특이항원(TB antigen:ESAT-6,CFP-10,TB7.7(p4)),양성대조\r\n";
		str += "(Mitogen control)를 37℃에서 16-24시간 배양 후 분리된 혈장을 효소면역분석법으로 측정\r\n";
		str += "하여 각 항원의 IFN-γ 반응값을 구합니다. 결핵균 특이항원의 IFN-γ 반응의 결과값에서 \r\n";
		str += "음성대조의 IFN-γ 반응의 결과값을 뺀 값(TB antigen minus Nil)을 분별한계치(cut off)를 \r\n";
		str += "기준으로 음성,양성으로 판정됩니다.\r\n";
		str += "\r\n";
		str += "◈ 검사의의\r\n";
		str += " 결핵균 특이항원인 ESAT-6,CFP-10,TB7.7(p4)는 BCG 접종에 영향을 받지 않으며, 대부\r\n";
		str += "분의 NTM(nontuberculous mycobacteria) 감염에도 영향을 받지 않기 때문에, 잠복결핵\r\n";
		str += "(latent tuberculosis)을 진단하는데 우수한 검사법입니다. 그러나, cellular immune\r\n";
		str += "response에 이상이 있는 환자의 경우에는 False negative or Indeterminate 결과를 보일 수 있으므로, \r\n";
		str += "HIV 감염자,장기이식환자, 면역억제제를 장기간 사용하는 환자 등에서는 결과 해석에 주의를 요합니다. \r\n";
		str += "\r\n";
		return str;
	}

	protected String getRemarkTxt2(String str[]) {
		StringBuffer b = new StringBuffer("");
		boolean isSensi = false;
//		boolean isSensi2 = false;

		// !
		if (str.length == 0)
			return null;

		// !
		for (int i = 0; i < str.length; i++) {
			int kk = str[i].trim().lastIndexOf("<균　명>");
			if (kk > -1) {
				b.append("\r\n" + "<균 명 >" + "\r\n\r\n");
				b.append(str[i].trim().substring(5).trim() + " \r\n");
				isSensi = true;
			} else {
				if (isSensi && str[i].trim().trim().startsWith("1")) {
					b.append("                                         "
							+ str[i].trim().trim() + "\r\n\r\n");
//					isSensi2 = true;
				} else {
					// if (isSensi2) {
					b.append(str[i].trim() + "\r\n");
					// } else {
					// b.append(str[i].trim() + "\r\n");
					// }
				}
			}
		}
		return b.toString().trim();
	}

	protected synchronized String getTextResultValue2(String hos, String date,
			String jno, String rcd) {
		String result = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과")).processDpc(new Object[] {
					hos, date, jno, rcd })) {
				return "";
			}
			String ArrayResult[] = Common
					.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get(
							"문장결과")).getParm().getStringParm(5));
			result = getRemarkTxt2(ArrayResult);
			if (result == null)
				result = "";
		} catch (Exception e) {
			return "";
		}
		return result;
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

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public String H1N1() {
		String str = "";
		str += "▣ 검체\r\n";
		str += "비인후 또는 인후 도찰물\r\n";
		str += "\r\n";
		str += " ▣ 검사방법\r\n";
		str += " Real time RT-PCR(실시간역전사중합효소연쇄반응법)" + "\r\n";
		str += " 1. 환자의 검체로부터 RNA를 추출합니다. " + "\r\n";
		str += " 2. Influenza A(H1N1)의 Hemagglutinin(HA) 영역에서 고안된 Primer로 RT-PCR을 실시합니다."
				+ "\r\n";
		str += " 3. 증폭된 PCR 산물을 Influenza A(H1N1)-specific probe(5'-FAM; 5'BHQ1)와 hybridization 시킵니다. "
				+ "\r\n";
		str += " 4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는"
				+ "\r\n";
		str += "  형광을 실시간으로 측정합니다." + "\r\n";
		str += "▣ Remark" + "\r\n";
		str += " 1. 본 검사는 Real time RT-PCR 원리를 이용하여 신종인플루엔자 A [Influenza A(H1N1)]에 특이한 primer를"
				+ "\r\n";
		str += "   사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다." + "\r\n";
		str += " 2. Influenza A 검사를 동시에 실시하여 확인된 결과입니다." + "\r\n";
		str += " 3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다."
				+ "\r\n";
		str += "" + "\r\n";

		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public String H1N1_NEW() {
		String str = "";
		str += "▣ Specimen   : 비인후 또는 인후 도찰물\r\n";
		str += "▣ Methods : Real-time RT-PCR(실시간역전사중합효소연쇄반응법) \r\n";
		str += "\r\n";
		str += "1. 환자의 검체로부터 RNA를 추출합니다.\r\n";
		str += "2. 각 영역에서 특이한 primer를 사용하여 RT-PCR을 실시합니다.\r\n";
		str += "    - Influenza A (H1N1, 신종플루) - Hemagglutinin(HA)\r\n";
		str += "    - Common Influenza A - Matrix protein(MP)\r\n";
		str += "    - Influenza A(H1N1, 계절성) - Hemagglutinin(HA)\r\n";
		str += "    - Influenza A(H3N2, 계절성) - Hemagglutinin(HA)\r\n";
		str += "\r\n";
		str += "3. 증폭된 PCR 산물을 각각의 specific probe와 hybridization 시킵니다. \r\n";
		str += "    -Influenza A(H1N1, 신종플루)-specific probe(5'-FAM; 3'-BHQ1)\r\n";
		str += "    -Common Influenza A- specific probe(5'-FAM; 3'-BHQ1)\r\n";
		str += "    -Influenza A(H1N1, 계절성) -specific probe(5'-FAM; 3'-TAMRA)\r\n";
		str += "    -Influenza A(H3N2, 계절성) -specific probe(5'-HEX; 3'-BHQ1)\r\n";
		str += "\r\n";
		str += "4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는 \r\n";
		str += "   형광을 실시간으로 측정합니다.\r\n";
		str += "\r\n";
		str += "▣ Remark \r\n";
		str += "\r\n";
		str += "1. 본 검사는 realtime RT-PCR 원리를 이용하여 신종인플루엔자 및 계절성 인플루엔자에 특이한 \r\n";
		str += "   primer를 각각 사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다.\r\n";
		str += "\r\n";
		str += "2. 계절성 Influenza A 의 검사는 Influenza A (H1N1, 신종플루)에서 음성, Common Influenza A 에서 양성인\r\n";
		str += "   경우에 한하여 2차 검사로 진행됩니다. \r\n";
		str += "\r\n";
		str += "3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다.\r\n";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String
	 */
	public String HLAB27() {
		String str = "";
		str += "▣ 검사방법\r\n";
		str += "PCR (Polymerase Chain Reaction)\r\n";
		str += "\r\n";
		str += "  -> 전혈에서 DNA를 추출 \r\n";
		str += "  -> HLA-B27 유전자에 특이한 시발체로 PCR시행 " + "\r\n";
		str += "  -> 전기영동으로 증폭된 PCR 산물 확인 " + "\r\n";
		str += "  -> 결과보고 " + "\r\n";
		str += "" + "\r\n";
		str += "▣ 검사 의의" + "\r\n";
		str += "" + "\r\n";
		str += " HLA-B27 유전자는 강직성척수염환자의 90% 정도에서 나타납니다." + "\r\n";
		str += "" + "\r\n";
		str += " 강직성척수염 (Ankylosing spondylitis) 이란 류마티스 질환의 일종으로 16~35세의 남자들에서"
				+ "\r\n";
		str += " 주로 발생하며, 척추관절이나 무릎, 발목, 발가락, 골반, 갈비뼈 등과 같은 관절에 염증을 일으킬 수"
				+ "\r\n";
		str += " 있습니다. " + "\r\n";
		str += "" + "\r\n";
		str += " HLA-B27 유전자와 강직성척수염과의 유전적 관련성이 매우 높다고 보고되고 있지만, 이 유전자는 "
				+ "\r\n";
		str += " 유전자가 발견되었다고 하여 반드시 강직성척수염이 발생한다고 볼 수는 없습니다." + "\r\n";
		str += "" + "\r\n";
		str += " 이외에 HLA-B27 유전자와 연관성이 있는 질환은 Reiter's disease, Juvenile rheumatoid arthritis,"
				+ "\r\n";
		str += " Anterior uveitis 등이 있습니다." + "\r\n";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2011-06-15 오전 11:05:01)
	 * 
	 * @return boolean 인천 사랑병원
	 */
	public boolean isReferenceValue13928(String s) {
		return false;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2011-06-15 오전 11:05:01)
	 * 
	 * @return boolean 인천 사랑병원
	 */
	public boolean isReferenceValue14279(String s) {
		if (s.equals("21064"))
			return true;
		else if (s.equals("21109"))
			return true;
		else if (s.equals("21110"))
			return true;
		else if (s.equals("21115"))
			return true;
		else if (s.equals("21116"))
			return true;
		else if (s.equals("21119"))
			return true;
		else if (s.equals("21120"))
			return true;
		else if (s.equals("21123"))
			return true;
		else if (s.equals("21124"))
			return true;
		else if (s.equals("21127"))
			return true;
		else if (s.equals("21128"))
			return true;
		else if (s.equals("21135"))
			return true;
		else if (s.equals("21136"))
			return true;
		else if (s.equals("21137"))
			return true;
		else if (s.equals("21138"))
			return true;
		else if (s.equals("21141"))
			return true;
		else if (s.equals("21142"))
			return true;
		else if (s.equals("21145"))
			return true;
		else if (s.equals("21146"))
			return true;
		else if (s.equals("21153"))
			return true;
		else if (s.equals("21154"))
			return true;
		else if (s.equals("21157"))
			return true;
		else if (s.equals("21158"))
			return true;
		else if (s.equals("21160"))
			return true;
		else if (s.equals("21167"))
			return true;
		else if (s.equals("21172"))
			return true;
		else if (s.equals("21172"))
			return true;
		else
			return false;
	}

	public void makeDownloadFile() {
		row = getStsrtResultRow() - 1;
		row2 = 1;
		int i = 0;
		try {
			String ArraryResult[] = null;
			ArraryResult = (getExcelFieldNameArray());
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				label = new jxl.write.Label(0, 0,
						"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				if (row == 2)
					wbresult.addCell(label);

				// !
				// ArraryResult = (getExcelFieldNameArray());
				if (row > 1) {
					for (i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 1, ArraryResult[i]);
						wbresult.addCell(label);
					}
					label = new jxl.write.Label(i, 1, "소요일");
					wbresult.addCell(label);
				} else if (row == 1) {
					for (i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 0, ArraryResult[i]);
						wbresult.addCell(label);
					}
					label = new jxl.write.Label(i, 0, "소요일");
					wbresult.addCell(label);
				}
			}
		} catch (Exception e) {
		}
	}

	public void processingData(int cnt) {
		try {

			// !
			boolean isNext = false;
			// !

			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
			String specNo[] = (String[]) getDownloadData().get("검체번호");
			String chartNo[] = (String[]) getDownloadData().get("차트번호");

			// !
			String patName[] = (String[]) getDownloadData().get("수신자명");
			String inspectCode[] = (String[]) getDownloadData().get("검사코드");
			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");

			// !
			String resultType[] = (String[]) getDownloadData().get("결과타입");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
			String bgcd[] = (String[]) getDownloadData().get("보험코드");

			// !
			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");

			// !
			String inc[] = (String[]) getDownloadData().get("외래구분");

			// !
			String data[] = new String[45];
//			String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";
//			boolean isCulture = false;
//			boolean isCulture_Sensi = false;
			boolean isTQ = false;
			int k = 0;

			// !
			String lastData = "";
			int lastindex = 0;
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
				isNext = true;

				// !
				while (lastData.equals(inspectCode[lastindex].trim().substring(
						0, 5))
						&& !(inspectCode[lastindex--].trim().substring(5)
								.equals("00"))) {
					cnt--;
					if (inspectCode[lastindex].trim().substring(5).equals("00")) {      
						cnt--;
					}
				}
			}
			for (int i = 0; i < cnt; i++) {
				String curDate = "";
				String curNo = "";

				// !
				data[0] = bdt[i]; // 걍~~
				data[1] = Common.cutZero(img[i]); // 내원번호
				data[2] = inc[i]; // 외래구분
				// data[3] = unit[i]; //의뢰일자
				data[3] = ""; // 의뢰일자

				// !
				data[4] = specNo[i].trim(); // 검체번호

				// !
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				}
				if (hosCode[i].trim().equals("23401")
						|| hosCode[i].trim().equals("24580")
						|| hosCode[i].trim().equals("24080")) {
					try {
						data[5] = cutHrcvDateNumber2(cns[i])[0]; // 처방번호
					} catch (Exception eee) {
						data[5] = ""; // 처방번호
					}
				}
				data[6] = clientInspectCode[i].trim(); // 병원검사코드(처방코드)
				data[7] = inspectName[i]; // 검사명(처방명)
				data[8] = hosSamp[i]; // 검체명(검체코드)

				// !
				data[9] = bbseq[i]; // 일련번호
				data[10] = ""; // 검체코드
				data[11] = ""; // 여유필드

				// !
				data[12] = chartNo[i]; // 차트번호
				data[13] = patName[i]; // 수진자명
				data[14] = securityNo[i]; // 주민번호

				// !
				data[15] = age[i]; // 나이
				data[16] = sex[i]; // 성별
				data[17] = ""; // 과
				data[18] = ""; // 병동
				data[19] = ""; // 참고사항

				// !
				data[20] = ""; // 학부
				data[30] = "11370319"; // 요양기관번호
				if (hosCode[i].trim().equals("22001")) {
					data[21] = Common.getDateFormat(bdt[i]); // 검사완료일
				} else {
					data[21] = bdt[i]; // 검사완료일
				}
				try {
					data[22] = cutHrcvDateNumber(cns[i])[1]; // 처방일자
				} catch (Exception eee) {
					data[22] = ""; // 처방일자
				}
				if (hosCode[i].trim().equals("23697")
						|| hosCode[i].trim().equals("13725")) {
					data[22] = bgcd[i]; // 처방일자
				}
				if (hosCode[i].trim().equals("23401")
						|| hosCode[i].trim().equals("24580")
						|| hosCode[i].trim().equals("24080")) {
					try {
						data[22] = cutHrcvDateNumber2(cns[i])[1]; // 처방일자
					} catch (Exception eee) {
						data[22] = ""; // 처방일자
					}
				}
				// !
				data[23] = ""; // 혈액형
				data[24] = "";

				// !
				try {
					data[25] = getUintCut(unit[i])[2]; // 참고치단위
				} catch (Exception exx) {
					data[25] = ""; // 참고치단위
				}
				try {
					data[42] = getUintCut(unit[i])[0]; // 참고치단위
				} catch (Exception exx) {
					data[42] = ""; // 참고치단위
				}
				try {
					data[43] = getUintCut(unit[i])[1]; // 참고치단위
				} catch (Exception exx) {
					data[43] = ""; // 참고치단위
				}

				// !
				data[26] = ""; // 진료의사
				data[27] = ""; // 추가키1
				data[28] = ""; // 추가키2
				data[29] = "";

				// !
				data[30] = "11370319"; // 요양기관번호
				data[31] = rcvDate[i].trim(); // 접수일자
				data[32] = rcvNo[i].trim(); // 접수번호
				data[33] = inspectCode[i].trim(); // 검사코드
				data[34] = ""; // 단문결과

				// !
				data[35] = ""; // 문장결과
				data[36] = ""; // 수치+문장
				data[37] = ""; // 상태
				data[38] = ""; // 리마크
				// data[38] = rmkCode[i]; //리마크
				data[39] = ""; // 참고치
				if (inspectCode[i].trim().length() == 7
						&& hosCode[i].trim().equals("18115")
						&& (inspectCode[i].trim().substring(0, 5).equals(
								"1100300") //
								|| inspectCode[i].trim().substring(0, 5)
										.equals("1100302") //
								|| inspectCode[i].trim().substring(0, 5)
										.equals("1100307") //
								|| inspectCode[i].trim().substring(0, 5)
										.equals("1100308") //
								|| inspectCode[i].trim().substring(0, 5)
										.equals("1100309") //
								|| inspectCode[i].trim().substring(0, 5)
										.equals("1100310") //
						|| inspectCode[i].trim().substring(0, 5).equals(
								"1100311"))) { // 단독
					continue;
				}
				// !
				// System.out.println(inspectCode[i].trim().substring(0, 5));
				if (resultType[i].trim().equals("C")) {
					data[34] = result[i]; // 문자결과
					data[36] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[35] = "";
					data[39] = getReferenceValue(remark);

					// !
					boolean isTxtRltB = false;
					// !
					if (!isTxtRltB
							&& isTriple(inspectCode[i].trim().substring(0, 5))) {
						data[34] = ""; // 리마크
						data[38] = ""; // 리마크
						data[39] = ""; // 참고치
						data[35] = ""; // 문장결과
						data[36] = ""; // 수치+문장
						data[37] = ""; // 상태
						isTQ = false;
						// ! Triple 검사인지 시작 ---------------------
						String result_ = getResultTriple(new String[] {
								rcvDate[i].trim(), rcvNo[i].trim(),
								inspectCode[i].trim().substring(0, 5) });
						data[35] = result_;
						data[39] = ""; // 참고치
						isTQ = true;
						isTxtRltB = true;
						// !
						i++;
						while (isTQ
								&& i > 0
								&& rcvDate[i].trim().equals(
										rcvDate[i - 1].trim())
								&& rcvNo[i].trim().equals(rcvNo[i - 1].trim())) {
							i++;
						}
						i--;
						// ! Triple 검사인지 종료 ---------------------

					}
					if (!isTxtRltB && hosCode[i].trim().equals("22023") && (inspectCode[i].trim().equals("31059"))) {
						//data[35] = result[i]; //문장결과
						//data[34] = ""; //문자결과
						//data[34] = result[i]; //문자결과   
						//data[35] = ""; //문장결과
						data[34] = ""; //문자결과
						data[35] = result[i]; //문장결과
						data[39] = ""; //참고치
						isTxtRltB = true;
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("23741")
									|| hosCode[i].trim().equals("22874") || hosCode[i]
									.trim().equals("18115"))
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("31001"))
							|| inspectCode[i].trim().substring(0, 5).equals(
									"11301")
							|| inspectCode[i].trim().substring(0, 5).equals(
									"05481")) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("24080")
							&& inspectCode[i].trim().length() == 7
							&& inspectCode[i].trim().substring(0, 5).equals(
									"71139")) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("24080")
							&& inspectCode[i].trim().length() == 7
							&& inspectCode[i].trim().substring(0, 5).equals(
									"05481")) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"50")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[39] = getReferenceValue(remark);
								// data[21] = bdt[i]; //검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("13928")
							&& inspectCode[i].trim().length() == 7
							&& inspectCode[i].trim().substring(0, 5).equals(
									"00309")
							|| inspectCode[i].trim().substring(0, 5).equals(
									"00323")) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("24654")
							&& inspectCode[i].trim().substring(0, 5).equals(
									"11101")) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								// !
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark).replace(
										'-', '~');
								data[21] = bdt[i]; // 검사완료일
							}
							if (inspectCode[i].trim().substring(5, 7).equals(
									"03")) {
								data[34] = data[34] + " (" + result[i] + ")"; // 문자결과
								// data[35] = "";
								// data[39] = ""; //참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								// data[25] = data[25] + "\n" +
								// getUintCut(unit[i])[2]; //참고치단위
								// !
								try {
									data[25] = data[25] + "\n"
											+ getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = data[39]
										+ "\n"
										+ getReferenceValue(remark).replace(
												'-', '~');
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("24654")
							&& inspectCode[i].trim().length() == 7
							&& inspectCode[i].trim().substring(0, 5).equals(
									"11301")) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("24516")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"31001") || inspectCode[i].trim()
									.substring(0, 5).equals("71252"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30)
								+ appendBlanks("결    과", 21) + "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ appendBlanks(result[i], 21)
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ appendBlanks(result[i], 21)
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24183")
							&& inspectCode[i].trim().length() == 7) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("24434")
									|| hosCode[i].trim().equals("24446")
									|| hosCode[i].trim().equals("19015") || hosCode[i]
									.trim().equals("12770"))
							&& inspectCode[i].trim().length() == 7 && //  
							(inspectCode[i].trim().substring(0, 5).equals(	"72059") || inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("72018") 
									|| 	inspectCode[i].trim().substring(0, 5)	.equals("71251") || 	inspectCode[i].trim().substring(0, 5).equals("71252"))) 
					{ // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("24434")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("00405"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치"; // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일

							}
							data[35] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30) + "\t"
									+ appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(remark);

							// !
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34] + "\r\n" + data[36];

						// !
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("24183") || hosCode[i]
									.trim().equals("24080"))
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("00095"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24161")
							&& inspectCode[i].trim().length() == 7) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("23401")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251")
									|| //
									inspectCode[i].trim().substring(0, 5)
											.equals("21061") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("00950") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11003") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11002") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11303") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("71259") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("00901") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11306") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11308") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11309") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11302") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11304") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("11306") || //

							inspectCode[i].trim().substring(0, 5).equals(
									"31001"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35]; // 단독

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("24051")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("31001"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide()
										+ "\r\n"
										+ appendBlanks(inspectName[i], 30)
										+ "\t"
										+ appendBlanks(result[i], 21)
										+ "\t"
										+ getReferenceValue(
												new String[] { inspectCode[i],
														lang[i], history[i],
														sex[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("23401")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"11301") || inspectCode[i].trim()
									.substring(0, 5).equals("71252"))) { // 단독

						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						try {
							data[25] = getUintCut(unit[i])[2]; // 참고치단위
						} catch (Exception exx) {
							data[25] = ""; // 참고치단위
						}
						try {
							data[42] = getUintCut(unit[i])[0]; // 참고치단위
						} catch (Exception exx) {
							data[42] = ""; // 참고치단위
						}
						try {
							data[43] = getUintCut(unit[i])[1]; // 참고치단위
						} catch (Exception exx) {
							data[43] = ""; // 참고치단위
						}
						data[39] = getReferenceValue(remark);
						data[21] = bdt[i]; // 검사완료일
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24051")
							&& inspectCode[i].trim().length() == 7) { // 단독
						// ! 박진영 내과
						data[35] = appendBlanks("검  사  명 ", 30) + ""
								+ appendBlanks("결    과", 21) + " "
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + ""
								+ appendBlanks(result[i], 21) + ""
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ ""
									+ appendBlanks(result[i], 21)
									+ ""
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].replace('\r', ' ').replace('\n',
								' ');

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22401")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"97655") || inspectCode[i].trim()
									.substring(0, 5).equals("72080"))) { // 단독
						// ! 예산 삼성 병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						try {
							data[25] = getUintCut(unit[i])[2]; // 참고치단위
						} catch (Exception exx) {
							data[25] = ""; // 참고치단위
						}
						try {
							data[42] = getUintCut(unit[i])[0]; // 참고치단위
						} catch (Exception exx) {
							data[42] = ""; // 참고치단위
						}
						try {
							data[43] = getUintCut(unit[i])[1]; // 참고치단위
						} catch (Exception exx) {
							data[43] = ""; // 참고치단위
						}
						data[39] = getReferenceValue(remark);
						data[21] = bdt[i]; // 검사완료일
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[36] = data[34];

						// !
					}
					if (!isTxtRltB && inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("12640")
							&& isHBV(inspectCode[i].trim().substring(0, 5))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "관  련  약  제";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getResultHBV(inspectCode[i].trim());
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30) + "\t"
									+ appendBlanks(result[i], 21) + "\t"
									+ getResultHBV(inspectCode[i].trim());
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("13928")
							&& isReferenceValue13928(inspectCode[i].trim()
									.substring(0, 5))) { // 단독
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						try {
							data[25] = getUintCut(unit[i])[2]; // 참고치단위
						} catch (Exception exx) {
							data[25] = ""; // 참고치단위
						}
						try {
							data[42] = getUintCut(unit[i])[0]; // 참고치단위
						} catch (Exception exx) {
							data[42] = ""; // 참고치단위
						}
						try {
							data[43] = getUintCut(unit[i])[1]; // 참고치단위
						} catch (Exception exx) {
							data[43] = ""; // 참고치단위
						}
						data[39] = getReferenceValue(remark);
						data[21] = bdt[i]; // 검사완료일
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22401")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("97655"))) { // 단독
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						try {
							data[25] = getUintCut(unit[i])[2]; // 참고치단위
						} catch (Exception exx) {
							data[25] = ""; // 참고치단위
						}
						try {
							data[42] = getUintCut(unit[i])[0]; // 참고치단위
						} catch (Exception exx) {
							data[42] = ""; // 참고치단위
						}
						try {
							data[43] = getUintCut(unit[i])[1]; // 참고치단위
						} catch (Exception exx) {
							data[43] = ""; // 참고치단위
						}
						data[39] = getReferenceValue(remark);
						data[21] = bdt[i]; // 검사완료일
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251")
									|| //
									inspectCode[i].trim().substring(0, 5)
											.equals("71252") || //
							inspectCode[i].trim().substring(0, 5).equals(
									"71259"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[34] = "";
							remark[0] = inspectCode[i];
							remark[1] = lang[i];
							remark[2] = history[i];
							remark[3] = sex[i];
							try {
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
							} catch (Exception exx) {
								data[25] = ""; // 참고치단위
							}
							try {
								data[42] = getUintCut(unit[i])[0]; // 참고치단위
							} catch (Exception exx) {
								data[42] = ""; // 참고치단위
							}
							try {
								data[43] = getUintCut(unit[i])[1]; // 참고치단위
							} catch (Exception exx) {
								data[43] = ""; // 참고치단위
							}
							data[39] = getReferenceValue(remark);
							data[21] = bdt[i]; // 검사완료일

							data[35] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30) + "\t"
									+ appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(remark);

							// !
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("14279")
							&& isReferenceValue14279(inspectCode[i].trim()
									.substring(0, 5))) { // 단독
						// ! 인천사랑병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						try {
							data[25] = getUintCut(unit[i])[2]; // 참고치단위
						} catch (Exception exx) {
							data[25] = ""; // 참고치단위
						}
						try {
							data[42] = getUintCut(unit[i])[0]; // 참고치단위
						} catch (Exception exx) {
							data[42] = ""; // 참고치단위
						}
						try {
							data[43] = getUintCut(unit[i])[1]; // 참고치단위
						} catch (Exception exx) {
							data[43] = ""; // 참고치단위
						}
						data[39] = getReferenceValue(remark);
						data[21] = bdt[i]; // 검사완료일
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("12640")
							&& !hosCode[i].trim().equals("24516")
							&& !hosCode[i].trim().equals("22015")
							& (inspectCode[i].trim().substring(0, 5)
									.equals("11052"))) { // 단독아님
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치"; // 삼육의료원제외
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("24516")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("11052"))) { // 단독아님
						data[35] = appendBlanks("검  사  명 ", 30)
								+ appendBlanks("결    과", 21) + "참    고    치"; // 삼육의료원제외
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ appendBlanks(result[i], 21)
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ appendBlanks(result[i], 21)
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("23697") || hosCode[i]
									.trim().equals("13725"))
							&& inspectCode[i].trim().substring(0, 5).equals(
									"00405")) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("24654")
							&& inspectCode[i].trim().substring(0, 5).equals(
									"11103")) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark).replace(
										'-', '~');
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("13928"))
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251") || inspectCode[i].trim()
									.substring(0, 5).equals("00095"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치"; // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일

							}
							data[35] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30) + "\t"
									+ appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(remark);

							// !
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("24516"))
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251") || inspectCode[i].trim()
									.substring(0, 5).equals("00095"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30)
								+ appendBlanks("결    과", 21) + "참    고    치"; // 단독
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일

							}
							data[35] += getDivide() + "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ appendBlanks(result[i], 21)
									+ getReferenceValue(remark);

							// !
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22089")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"31001")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("11301") || (!hosCode[i]
									.trim().equals("22023") && inspectCode[i]
									.trim().substring(0, 5).equals("31059")))) {
						// ! 박진영 내과
						data[35] = appendBlanks("검  사  명 ", 30) + ""
								+ appendBlanks("결    과", 21) + " "
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + ""
								+ appendBlanks(result[i], 21) + ""
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i]; // 단독
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ ""
									+ appendBlanks(result[i], 21)
									+ ""
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].replace('\r', ' ').replace('\n',
								' ');
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("22221")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("41026"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("11101"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"03")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[25] = getUintCut(unit[i])[2]; // 참고치단위
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("23697")
									|| hosCode[i].trim().equals("13725") || hosCode[i]
									.trim().equals("18115"))
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("00095"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("23697") || hosCode[i]
									.trim().equals("13725"))
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("11101"))) { // 단독
						isTxtRltB = true;
						if (inspectCode[i].trim().substring(5, 7).equals("02")) {
							continue;
						}
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("23316")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("00901"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"02")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("23316") || hosCode[i]
									.trim().equals("23741"))
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("00095"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB
							&& isMAST(inspectCode[i].trim().substring(0, 5))
							&& (hosCode[i].trim().equals("22250"))
							&& !hosCode[i].trim().equals("12640")
							&& !hosCode[i].trim().equals("21954")
							&& !hosCode[i].trim().equals("22015")) { // 삼육제외
						Vector vmast = new Vector();
						isTxtRltB = true;
						data[34] = "";
						data[39] = "";
						data[35] = appendBlanks("검사항목", 26)
								+ appendBlanks("CLASS", 8)
								+ appendBlanks("검사항목", 25)
								+ appendBlanks("CLASS", 8);
						data[35] += getDivide() + "\r\n";
						data[36] = data[35];
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i].substring(0, 1), 8));
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],
										26)
										+ appendBlanks(result[i++].substring(0,
												1), 8));
								if ((inspectCode[i].trim().substring(0, 5)
										.equals("00673"))
										|| (inspectCode[i].trim().substring(0,
												5).equals("00674")))
									vmast.addElement(appendBlanks(
											inspectName[i], 26)
											+ appendBlanks(result[i++]
													.substring(0, 1), 8));
								else
									break;
							} catch (Exception e) {
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						if (hosCode[i].trim().equals("22250")) {
							data[35] = getResultMAST(data[35].toString(), vmast)
									+ getDivide();
						} else {
							data[35] = getResultMAST(data[35].toString(), vmast)
									+ getDivide() + "\r\n" + getMastRemark();
						}
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("23316") || hosCode[i]
									.trim().equals("23586"))) { // 단독
						isTxtRltB = true;
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31010")
									|| inspectCode[i].trim().equals("31012")
									|| (inspectCode[i].trim().equals("31059") && !hosCode[i]
											.trim().equals("22023")) || inspectCode[i]
									.trim().equals("31079"))) { // 단독
						data[34] = ""; // 문자결과
						isTxtRltB = true;
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35];
						data[39] = ""; // 참고치
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("20720")
							&& !hosCode[i].trim().equals("22221")
							&& !hosCode[i].trim().equals("22015")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251") // 삼육제외
							|| inspectCode[i].trim().substring(0, 5).equals(
									"71252"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB && hosCode[i].trim().equals("20720")) {
						isTxtRltB = true;
						if (result[i].trim().toUpperCase().indexOf("NEGATIVE") > -1) {
							data[34] = result[i].trim().substring(8) + " (음성)"; // 문자결과 NEGATIVE => 한글로
							data[36] = data[34];
						} else if (result[i].trim().toUpperCase().indexOf("POSITIVE") > -1) {
							data[34] = result[i].trim().substring(8) + " (양성)"; // 문자결과
							data[36] = data[34];
						}
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("22250") && inspectCode[i]
									.trim().substring(0, 5).equals("81388"))) {
						isTxtRltB = true;
						data[35] += getDivide() + getMTHFR();
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("22250")
									&& inspectCode[i].trim().length() == 7 //
									&& inspectCode[i].trim().substring(0, 5)
											.equals("11026") //
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00312") //
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00313") //
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00307") //
							|| inspectCode[i].trim().substring(0, 5).equals(
									"00308"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22023")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"31001")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("71252")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("71251") || inspectCode[i]
									.trim().substring(0, 5).equals("71259"))) {
						// if (hosCode[i].trim().equals("22023") &&
						// inspectCode[i].trim().length() == 7 &&
						// (inspectCode[i].trim().substring(0,
						// 5).equals("31001") ||
						// inspectCode[i].trim().substring(0, 5).equals("31059")
						// || inspectCode[i].trim().substring(0,
						// 5).equals("71252") ||
						// inspectCode[i].trim().substring(0,
						// 5).equals("71259"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB && hosCode[i].trim().equals("12640")) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = result[i]; // 문자결과
						isTxtRltB = true;
						if (inspectCode[i].trim().substring(0, 5).equals(
								"31001")) {
							data[36] = "";
							data[35] = "";
						} else {
							data[36] = data[35];
						}
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						if (inspectCode[i].trim().substring(0, 5).equals(
								"11052")) {
							remarkCode = "";
						}
						if (rmkCode[i].trim().length() > 0) {
							try {
								if (!kumdata[0].trim().equals(data[31].trim())
										|| !kumdata[1].trim().equals(
												data[32].trim())
										|| !kumdata[2].trim()
												.equals(remarkCode)) {
									remarkCode = rmkCode[i].trim();
									// if (inspectCode[i].trim().substring(0,
									// 5).equals("11026") ||
									// inspectCode[i].trim().substring(0,
									// 5).equals("11052")) {
									// data[35] = data[35] + "\r\n" + "\r\n" +
									// getReamrkValue(hosCode[i], rcvDate[i],
									// rcvNo[i], rmkCode[i]);
									// data[36] = data[35];
									// } else {
									data[38] = getReamrkValue(hosCode[i],
											rcvDate[i], rcvNo[i], rmkCode[i]);
									// }
									kumdata[0] = data[31].trim();
									kumdata[1] = data[32].trim();
									kumdata[2] = remarkCode;
								}
							} catch (Exception _ex) {
							}
						} else {
							remarkCode = "";
						}
					}
					if (!isTxtRltB && inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("10780") || //
									hosCode[i].trim().equals("10781") || //
									hosCode[i].trim().equals("10782") || //
									hosCode[i].trim().equals("10783") || //
									hosCode[i].trim().equals("10784") || //
							hosCode[i].trim().equals("10785"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5).equals("72059")||inspectCode[i].trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("12770") && inspectCode[i]
									.trim().length() == 7)
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"11026")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00091")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("00095") || inspectCode[i]
									.trim().substring(0, 5).equals("00804"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("14279")
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251") || inspectCode[i].trim()
									.substring(0, 5).equals("71252"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("22256")
							&& !hosCode[i].trim().equals("22015")
							&& (inspectCode[i].trim().substring(0, 5).equals("72059")||inspectCode[i].trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& isMAST(inspectCode[i].trim().substring(0, 5))
							&& !hosCode[i].trim().equals("21954")
							&& !hosCode[i].trim().equals("22015")
							&& !hosCode[i].trim().equals("12640")) {
						Vector vmast = new Vector();
						data[34] = "";
						data[39] = "";
						data[35] = appendBlanks("검사항목", 26)
								+ appendBlanks("CLASS", 8)
								+ appendBlanks("검사항목", 25)
								+ appendBlanks("CLASS", 8);
						data[35] += getDivide() + "\r\n";
						data[36] = data[35];
						vmast.addElement(appendBlanks(inspectName[i], 26)
								+ appendBlanks(result[i].substring(0, 1), 8));
						// !
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							try {
								vmast.addElement(appendBlanks(inspectName[i],
										26)
										+ appendBlanks(result[i++].substring(0,
												1), 8));
								if ((inspectCode[i].trim().substring(0, 5)
										.equals("00673"))
										|| (inspectCode[i].trim().substring(0,
												5).equals("00674")))
									vmast.addElement(appendBlanks(
											inspectName[i], 26)
											+ appendBlanks(result[i++]
													.substring(0, 1), 8));
								else
									break;
							} catch (Exception e) {
							}
							if (i >= cnt)
								break;
						}
						i--;
						data[35] = getResultMAST(data[35].toString(), vmast)
								+ getDivide() + "\r\n" + getMastRemark();
						data[36] = data[35];
					}
					if (!isTxtRltB&& inspectCode[i].trim().length() == 7	&& !hosCode[i].trim().equals("24434")	&& !hosCode[i].trim().equals("22256")
							&& !hosCode[i].trim().equals("22015")	&& !hosCode[i].trim().equals("22250")	&& !hosCode[i].trim().equals("14279")
							&& !hosCode[i].trim().equals("22221")	&& 
							((inspectCode[i].trim().substring(0, 5).equals("71251") || inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("00752")) 
									|| 	(hosCode[i].trim().equals("22023") && (inspectCode[i]	.trim().substring(0, 5).equals("72059") || inspectCode[i]	.trim().substring(0, 5).equals("72018"))))) 
					{
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("22033") || hosCode[i]
									.trim().equals("12770"))
							&& inspectCode[i].trim().length() == 7) {
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 25) + "\t"
								+ appendBlanks(result[i], 15) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 25)
									+ "\t"
									+ appendBlanks(result[i], 15)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB && hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31019"))) {
						data[35] = result[i]; // 문장결과
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("24516")
							&& ((hosCode[i].trim().equals("19725") || hosCode[i]
									.trim().equals("21118"))
									|| //
									(hosCode[i].trim().equals("20974") && inspectCode[i]
											.trim().substring(0, 5).equals(
													"11026")) || //
									(hosCode[i].trim().equals("13931") && inspectCode[i]
											.trim().substring(0, 5).equals(
													"71251")) || //
									(hosCode[i].trim().equals("22262") && (inspectCode[i]
											.trim().substring(0, 5).equals(
													"11101") || inspectCode[i]
											.trim().substring(0, 5).equals(
													"31001"))) || //
									(hosCode[i].trim().equals("22256")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"88007")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"00091")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"00095") && (inspectCode[i]
											.trim().substring(0, 5).equals(
													"11101") || inspectCode[i]
											.trim().substring(0, 5).equals(
													"31001"))) || (hosCode[i]
									.trim().equals("13931") && inspectCode[i]
									.trim().substring(0, 5).equals("71251")))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& hosCode[i].trim().equals("24516")
							&& ((hosCode[i].trim().equals("19725") || hosCode[i]
									.trim().equals("21118"))
									|| //
									(hosCode[i].trim().equals("20974") && inspectCode[i]
											.trim().substring(0, 5).equals(
													"11026")) || //
									(hosCode[i].trim().equals("13931") && inspectCode[i]
											.trim().substring(0, 5).equals(
													"71251")) || //
									(hosCode[i].trim().equals("22262") && (inspectCode[i]
											.trim().substring(0, 5).equals(
													"11101") || inspectCode[i]
											.trim().substring(0, 5).equals(
													"31001"))) || //
									(hosCode[i].trim().equals("22256")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"88007")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"00091")
											&& !inspectCode[i].trim()
													.substring(0, 5).equals(
															"00095") && (inspectCode[i]
											.trim().substring(0, 5).equals(
													"11101") || inspectCode[i]
											.trim().substring(0, 5).equals(
													"31001"))) || (hosCode[i]
									.trim().equals("13931") && inspectCode[i]
									.trim().substring(0, 5).equals("71251")))) {
						data[35] = appendBlanks("검  사  명 ", 30)
								+ appendBlanks("결    과", 21) + "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ appendBlanks(result[i], 21)
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ appendBlanks(result[i], 21)
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB&& !hosCode[i].trim().equals("19725")	&& (hosCode[i].trim().equals("13931")	|| hosCode[i].trim().equals("19725")
									|| hosCode[i].trim().equals("13406") || hosCode[i].trim().equals("12770"))&& (inspectCode[i].trim().substring(0, 5).equals("00091")
									|| inspectCode[i].trim().substring(0, 5)	.equals("00095")	|| inspectCode[i].trim().substring(0, 5)	.equals("00752") 
									|| inspectCode[i]	.trim().substring(0, 5).equals("72059") || inspectCode[i]	.trim().substring(0, 5).equals("72018"))) 
					{
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals(
									"01")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5)
									.equals("11101"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							if (!inspectCode[i].trim().substring(5, 7).equals(
									"00")) {
								data[34] = result[i]; // 문자결과
								data[35] = "";
								data[39] = ""; // 참고치
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								try {
									data[25] = getUintCut(unit[i])[2]; // 참고치단위
								} catch (Exception exx) {
									data[25] = ""; // 참고치단위
								}
								try {
									data[42] = getUintCut(unit[i])[0]; // 참고치단위
								} catch (Exception exx) {
									data[42] = ""; // 참고치단위
								}
								try {
									data[43] = getUintCut(unit[i])[1]; // 참고치단위
								} catch (Exception exx) {
									data[43] = ""; // 참고치단위
								}
								data[39] = getReferenceValue(remark);
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("22256")
							&& !hosCode[i].trim().equals("22015")
							&& (hosCode[i].trim().equals("22262") && !inspectCode[i]
									.trim().equals("11101"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t"
								+ appendBlanks("결    과", 21) + " \t"
								+ "참    고    치";
						data[35] += getDivide() + "\r\n"
								+ appendBlanks(inspectName[i], 30) + "\t"
								+ appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						// data[36] = data[35];
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim()
								.substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide()
									+ "\r\n"
									+ appendBlanks(inspectName[i], 30)
									+ "\t"
									+ appendBlanks(result[i], 21)
									+ "\t"
									+ getReferenceValue(
											new String[] { inspectCode[i],
													lang[i], history[i], sex[i] })
											.trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35];
					}

					// ! 여기서 부터
					// 문장형--------------------------------------------------------------------------------------------------------------------------------------------------------
				} else {
					boolean isTxtRltC = false;
					if (!isTxtRltC && hosCode[i].trim().equals("24434")) {
						isTxtRltC = true;
						if (inspectCode[i].trim().equals("61007")) {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue_NGY(hosCode[i],
									rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35];
							data[39] = ""; // 참고치
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(hosCode[i],
									rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35];
							data[39] = ""; // 참고치
						}
					}
					if (!isTxtRltC && hosCode[i].trim().equals("24446")) {
						isTxtRltC = true;
						if (inspectCode[i].trim().equals("31012")) {
							continue;
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(hosCode[i],
									rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35];
							data[39] = ""; // 참고치
						}
					}
					if (!isTxtRltC
							&& hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31010") || inspectCode[i]
									.trim().equals("31012"))
							|| inspectCode[i].trim().equals("31014")) {
						isTxtRltC = true;
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i],
								rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35];
						data[39] = ""; // 참고치
					}
					if (!isTxtRltC
							&& hosCode[i].trim().equals("22023")
							&& (inspectCode[i].trim().equals("31010") || inspectCode[i]
									.trim().equals("31012"))
							|| inspectCode[i].trim().equals("31014")) {
						data[34] = ""; // 문자결과
						data[35] = getCultureSensi(rcvDate[i], rcvNo[i]); // 문장결과
						data[36] = data[35];
						isTxtRltC = true;
						data[39] = ""; // 참고치
					}
//					if (!isTxtRltC && hosCode[i].trim().equals("22256")) {
//						isTxtRltC = true;
//						if (inspectCode[i].trim().equals("31022")
//								&& !getCultureSensi(rcvDate[i], rcvNo[i])
//										.trim().equals("")) {
//							data[34] = ""; // 문자결과
//							data[35] = getReamrkValue99(hosCode[i], rcvDate[i],
//									rcvNo[i], "MB8"); // 문장결과
//							data[36] = data[35];
//							data[39] = ""; // 참고치
//
//							if (data[35].trim().equals("")) {
//								data[35] = getTextResultValue2(hosCode[i],
//										rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
//																				// 길게
//								data[36] = data[35];
//							}
//						} else {
//							data[34] = ""; // 문자결과
//							data[35] = getTextResultValue2(hosCode[i],
//									rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
//																			// 길게
//							data[36] = data[35];
//							data[39] = ""; // 참고치
//						}
//					} else {
//						if (!isTxtRltC) {
//							data[34] = ""; // 문자결과
//							data[35] = getTextResultValue(hosCode[i],
//									rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
//							data[36] = data[35];
//							data[39] = ""; // 참고치
//						}
//					}
//				}
					if (!isTxtRltC && hosCode[i].trim().equals("22256")) {   
						isTxtRltC = true;
						if ((inspectCode[i].trim().equals("31022") || inspectCode[i].trim().equals("31079")) && !getCultureSensi(rcvDate[i], rcvNo[i]).trim().equals("")) {
							data[34] = ""; //문자결과
							data[35] = getReamrkValue99(hosCode[i], rcvDate[i], rcvNo[i], "MB8"); //문장결과   
							data[36] = data[35];
							data[39] = ""; //참고치


							if (data[35].trim().equals("")) {
								data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); //문장결과 길게
								data[36] = data[35];
							}
						} else
							if (inspectCode[i].trim().equals("31079")) {
								data[34] = ""; //문자결과
								data[35] = getReamrkValue99(hosCode[i], rcvDate[i], rcvNo[i], "MB8"); //문장결과   
								data[36] = data[35];
								data[39] = ""; //참고치


								if (data[35].trim().equals("")) {
									data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); //문장결과 길게
									data[36] = data[35];
								}
							} else {
								data[34] = ""; //문자결과					
								data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); //문장결과 길게
								data[36] = data[35];
								data[39] = ""; //참고치
							}
					} else {
						if (!isTxtRltC) {
							data[34] = ""; //문자결과					
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); //문장결과
							data[36] = data[35];
							data[39] = ""; //참고치
						}
					}
				}
				if (hosCode[i].trim().equals("22256")
						&& !data[35].trim().equals("")) {
					data[35] += "\r\n\r\n검체번호: " + data[4] + " [" + data[31]
							+ "-" + data[32] + "]";
					data[36] = data[35];
				}
				data[37] = highLow[i]; // 결과상태

				if (inspectCode[i].trim().substring(0, 5).equals("11052")) {
					remarkCode = "";
				}
				if (rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim())
								|| !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals(
									"11026")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("11052")) {
								data[35] = data[35]
										+ getDivide()
										+ "\r\n"
										+ "\r\n"
										+ getDivide()
										+ getReamrkValue(hosCode[i],
												rcvDate[i], rcvNo[i],
												rmkCode[i]);
								data[36] = data[35];
							} else {
								data[38] = getReamrkValue(hosCode[i],
										rcvDate[i], rcvNo[i], rmkCode[i]);
							}
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				} else {
					remarkCode = "";
				}
				if (hosCode[i].trim().equals("12640")
						&& rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim())
								|| !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[38] = getReamrkValue(hosCode[i], rcvDate[i],
									rcvNo[i], rmkCode[i]);
							// }
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				}
				// !-------------------------------------------
				boolean isTxtRltA = false;
				if (!isTxtRltA && hosCode[i].trim().equals("13725")) {
					data[35] = getCrLF(data[35]);
					data[36] = getCrLF(data[36]);
					data[38] = getCrLF(data[38]);
					isTxtRltA = true;
				}
				if (!isTxtRltA && hosCode[i].trim().equals("14279")) { // 인천사랑병원
					data[35] = getCrLF(data[35]);
					data[36] = getCrLF(data[36]);
					data[38] = data[38].replace('\r', ' ');
					isTxtRltA = true;
				}
				if (!isTxtRltA && hosCode[i].trim().equals("22089")
						|| hosCode[i].trim().equals("24516")) {
					data[35] = data[35].replace('\r', ' ').replace('\n', ' ');
					data[36] = data[36].replace('\r', ' ').replace('\n', ' ');
					data[35] = data[35].replace('\t', ' ');
					data[36] = data[36].replace('\t', ' ');
					isTxtRltA = true;
				}
				if (!isTxtRltA && hosCode[i].trim().equals("19391")) {
					data[35] = data[35].replace('\r', '\n');
					data[36] = data[36].replace('\r', '\n');
					data[35] = data[35].replace('\t', ' ');
					data[36] = data[36].replace('\t', ' ');
					isTxtRltA = true;
				}
				if (!isTxtRltA
						&& hosCode[i].trim().equals("22015")
						&& (inspectCode[i].trim().equals("0067342") || inspectCode[i]
								.trim().equals("0067442"))) {
					data[35] = data[35] + "\r\n" + getMastRemark();
					data[36] = data[35];
					isTxtRltA = true;
				}
				if (!isTxtRltA && hosCode[i].trim().equals("24516")) {
					data[35] = data[35].replace('\t', ' ');
					data[36] = data[36].replace('\t', ' ');
					isTxtRltA = true;
				}
				
				data[11] = "";
				if (hosCode[i].trim().equals("19015") && inspectCode[i].trim().length() == 7) {
					if (inspectCode[i].trim().substring(5, 7).equals("00")) {
						data[11] = "H";
					} else {  
						data[11] = "S";
					}
				}

				// !
				if (!debug) {
					int lens = getExcelFieldNameArray().length;
					for (k = 0; k < lens; k++) {
						label = new jxl.write.Label(k, row, data[Integer
								.parseInt(getExcelFieldArray()[k].toString())]);
						wbresult.addCell(label);
					}
					// 쇼요일
					String tat = "";
					try {
						tat = Integer.parseInt(bdt[i])
								- Integer.parseInt(rcvDate[i]) + "";
						if (tat.equals("0")) {
							tat = "1";
						}
						label = new jxl.write.Label(k, row, tat);
						wbresult.addCell(label);
					} catch (Exception xx) {
					}
				} 
				row++;
			}
			if (cnt == 400 || isNext) {
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception _ex) {
			setParameters(null);
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2010-07-30 오후 4:13:49)
	 */
	public void xxx() {
		/*
		 * 성지병원
		 * 
		 * //(inspectCode[i].trim().substring(0, 5).equals("00028") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("00608") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("05039") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("05044") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21043") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21044") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21061") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21064") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21123") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21124") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21127") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21128") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21136") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21137") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21138") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21142") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21141") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21145") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21146") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21159") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21160") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21192") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21207") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21208") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21209") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21210") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21211") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("21264") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41075") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41081") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41092") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41097") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41109") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41112") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41114") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41116") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41118") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41035") || //
		 * //inspectCode[i].trim().substring(0, 5).equals("41020") // //)
		 * 
		 */
	}
}

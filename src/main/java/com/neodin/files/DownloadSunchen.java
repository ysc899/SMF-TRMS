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
public class DownloadSunchen extends ResultDownload {
	boolean debug = false;
	public DownloadSunchen() {
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
	public String appendBlanksBack(String src, int length) {
		String dest = src.trim().substring(0);
		if (src.trim().length() < length) {
			for (int i = 0; i < length - src.length(); i++)
				dest =  " "+dest;
		} else {
			dest = src.substring(0, length);
		}
		return dest;
	}
	public DownloadSunchen(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
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
	public String[] cutHrcvDateNumber3(String str) {

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
		String src_[] = new String[] { "", "" };
		if (str == null || src_.equals(""))
			return src_;

		src_ = Common.getDataCut(str, "D");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

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
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 jre1.22 작성 날짜:
	 * (2005-10-24 오후 10:06:16)
	 */
	// private String getHBV() {
	// String str = "";
	// ////!
	// str += "[Remark]\r\n";
	// str +=
	// "현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘 (Lamivudine) 은 HBV 중합효소 (polymerase) 의 합성을 억제하여\r\n";
	// str +=
	// "바이러스 유전자 증폭을 차단하는 역할을 합니다. 그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의\r\n";
	// str += "출현확률이 높아지게 되고 결국에는 치료의 실패내지는 재발로 이어지는 문제점을 나타냅니다.\r\n";
	// str +=
	// "이러한 변이형은 HBV 중합효소 (polymerase)내의 codon552 과 codon528 염기서열의 돌연변이로 주로 발생하게 되는데\r\n";
	// str +=
	// "본 검사는 HBV 중합효소(polymerase)의 특정부위를 증폭한 후 염기서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\r\n";
	// str +=
	// "따라서 이러한 변이형의 발견은 치료경과 관찰, Viral breakthrough 의 조기발견 및 치료방침을 결정하는데 유용하게 \r\n";
	// str += "사용될 수 있습니다.\r\n";
	// return str;
	// }
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
	
	public String getHpyloriPcr() {
		String str = "[Comment]\r\n" 
                + "PCR 검사는 검체 내 균 수가 적거나 부적절한 검체 희석 또는 증폭 억제물질이 존재하는 경우 \r\n"
				+ "위음성이 나올 수 있습니다.\r\n"
                + "또한, PCR 검사는 유전자 유무를 검사하므로 생존균과 사균의 구분이 안되어 위양성의 가능성이 있습니다. \r\n"
                + "결과 해석 시, 환자의 임상 양상과 연관지어 판단하시기 바랍니다.";
		str += "\r\n";
		return str;
	}

	protected String getRemarkTxt2(String str[]) {
		StringBuffer b = new StringBuffer("");
		boolean isSensi = false;
		// boolean isSensi2 = false;

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
					b.append("                                         " + str[i].trim().trim() + "\r\n\r\n");
					// isSensi2 = true;
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

	protected synchronized String getTextResultValue2(String hos, String date, String jno, String rcd) {   
		String result = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과")).processDpc(new Object[] { hos, date, jno, rcd })) {
				return "";
			}
			String ArrayResult[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("문장결과")).getParm().getStringParm(5));
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
		str += " 2. Influenza A(H1N1)의 Hemagglutinin(HA) 영역에서 고안된 Primer로 RT-PCR을 실시합니다." + "\r\n";
		str += " 3. 증폭된 PCR 산물을 Influenza A(H1N1)-specific probe(5'-FAM; 5'BHQ1)와 hybridization 시킵니다. " + "\r\n";
		str += " 4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는" + "\r\n";
		str += "  형광을 실시간으로 측정합니다." + "\r\n";
		str += "▣ Remark" + "\r\n";
		str += " 1. 본 검사는 Real time RT-PCR 원리를 이용하여 신종인플루엔자 A [Influenza A(H1N1)]에 특이한 primer를" + "\r\n";
		str += "   사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다." + "\r\n";
		str += " 2. Influenza A 검사를 동시에 실시하여 확인된 결과입니다." + "\r\n";
		str += " 3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다." + "\r\n";
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
		str += " 강직성척수염 (Ankylosing spondylitis) 이란 류마티스 질환의 일종으로 16~35세의 남자들에서" + "\r\n";
		str += " 주로 발생하며, 척추관절이나 무릎, 발목, 발가락, 골반, 갈비뼈 등과 같은 관절에 염증을 일으킬 수" + "\r\n";
		str += " 있습니다. " + "\r\n";
		str += "" + "\r\n";
		str += " HLA-B27 유전자와 강직성척수염과의 유전적 관련성이 매우 높다고 보고되고 있지만, 이 유전자는 " + "\r\n";
		str += " 유전자가 발견되었다고 하여 반드시 강직성척수염이 발생한다고 볼 수는 없습니다." + "\r\n";
		str += "" + "\r\n";
		str += " 이외에 HLA-B27 유전자와 연관성이 있는 질환은 Reiter's disease, Juvenile rheumatoid arthritis," + "\r\n";
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
		if (s.equals("21064") || s.equals("21109") || s.equals("21110") || s.equals("21115") || s.equals("21116") || s.equals("21119")
				|| s.equals("21120") || s.equals("21123") || s.equals("21124") || s.equals("21127") || s.equals("21128") || s.equals("21135")
				|| s.equals("21136") || s.equals("21137") || s.equals("21138") || s.equals("21141") || s.equals("21142") || s.equals("21145")
				|| s.equals("21146") || s.equals("21153") || s.equals("21154") || s.equals("21157") || s.equals("21158") || s.equals("21160")
				|| s.equals("21167") || s.equals("21172") || s.equals("21172"))
			return true;
		else
			return false;
	}

	public void makeDownloadFile() {

		row = getStsrtResultRow() - 1;
		row2 = 1;
		int i = 0;

		// !
		try {
			String ArraryResult[] = null;
			ArraryResult = (getExcelFieldNameArray());
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir + mkOutCSVFile()));
				wbresult = workbook.createSheet("Result", 0);
				label = new jxl.write.Label(0, 0, "(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				if (row == 2)
					wbresult.addCell(label);

				if (row > 1) {
					for (i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 1, ArraryResult[i]);
						wbresult.addCell(label);
					}
				} else if (row == 1) {
					for (i = 0; i < ArraryResult.length; i++) {
						label = new jxl.write.Label(i, 0, ArraryResult[i]);
						wbresult.addCell(label);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void processingData(int cnt) {
		try {


			
			boolean isNext = false;

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
			String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");

			String bdt[] = (String[]) getDownloadData().get("검사완료일");
			String bgcd[] = (String[]) getDownloadData().get("보험코드");

			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");

			String inc[] = (String[]) getDownloadData().get("외래구분");

			String data[] = new String[45];
			String remark[] = new String[5];
			String remarkCode = "";
			boolean isTQ = false;
			int k = 0;
			String lastData = "";
			int lastindex = 0;
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
				isNext = true;

				while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
					cnt--;
					if (inspectCode[lastindex].trim().substring(5).equals("00")) {
						cnt--;
					}
				}
				
				if(inspectCode[399].trim().substring(5).equals("00")){
					cnt--;
				}
			}
			
			for (int i = 0; i < cnt; i++) {

				//TODO test cdy				
				if("김예진".equals(patName[i]))
						//||"B1100".equals(clientInspectCode[i])  )
						{
					//System.out.println("PBS 결과 테스트");
				}
				
				String curDate = "";
				String curNo = "";
				// !
				data[0] = bdt[i]; // 걍~~
				data[1] = Common.cutZero(img[i]); // 내원번호
				data[2] = inc[i]; // 외래구분
				data[3] = ""; // 의뢰일자
				data[4] = specNo[i].trim(); // 검체번호
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				}    
				if (hosCode[i].trim().equals("23401")||hosCode[i].trim().equals("28279")) {
					try {
						data[5] = cutHrcvDateNumber2(cns[i])[0]; // 처방번호
					} catch (Exception eee) {
						data[5] = ""; // 처방번호
					}
				}
				data[6] = clientInspectCode[i].trim(); // 병원검사코드(처방코드)
				data[7] = inspectName[i]; // 검사명(처방명)
				data[8] = hosSamp[i]; // 검체명(검체코드)

				data[9] = bbseq[i]; // 일련번호
				data[10] = ""; // 검체코드
				data[11] = ""; // 여유필드

				data[12] = chartNo[i]; // 차트번호
				data[13] = patName[i]; // 수진자명
				data[14] = securityNo[i]; // 주민번호

				data[15] = age[i]; // 나이
				data[16] = sex[i]; // 성별
				data[17] = ""; // 과
				data[18] ="";
				//함소담외과 , 참편한가정의학과 에서는 병동명 가져오기
				if (hosCode[i].trim().equals("28107")||hosCode[i].trim().equals("28551")||hosCode[i].trim().equals("28790")||hosCode[i].trim().equals("29507")) {
					try {
						data[18] = getPatientInformationNew(rcvDate[i],rcvNo[i]).toString(); // 병동
					} catch (Exception eee) {
						data[18] = ""; // 처방일자
					}
				}
				data[19] = ""; // 참고사항

				data[20] = ""; // 학부
				data[30] = "11370319"; // 요양기관번호
				data[21] = bdt[i]; // 검사완료일
				try {
					data[22] = cutHrcvDateNumber(cns[i])[1]; // 처방일자
				} catch (Exception eee) {
					data[22] = ""; // 처방일자
				}
				
				if (hosCode[i].trim().equals("23401")) {
					try {
						data[22] = cutHrcvDateNumber2(cns[i])[1]; // 처방일자
					} catch (Exception eee) {
						data[22] = ""; // 처방일자
					}
				}
				
				if (hosCode[i].trim().equals("28279")) {//20160302
					try {
						data[22] = cutHrcvDateNumber3(cns[i])[1]; // 처방일자
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
				try {
					data[39] = ""; // 참고치
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					if (hosCode[i].trim().equals("23401")) {//세계로 병원은 참고치가 나이에 맞는 참고치만 출력되도록함
						data[39] = getReferenceValueAge(remark);
					}
					else{
						data[39] = getReferenceValueNotBlank(remark);
					}
				} catch (Exception e) {
					data[39] = "";
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
				
				// 도안누리 건너뛰기
				if (inspectCode[i].trim().length() == 7 && ((hosCode[i].trim().equals("26853")||hosCode[i].trim().equals("27897")||hosCode[i].trim().equals("28106")) && !inspectCode[i].trim().equals("0548150") && !inspectCode[i].trim().equals("0548356") 
						&& (inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102") || inspectCode[i].trim().equals("1110103")
						||(inspectCode[i].trim().substring(0, 5).equals("05481")||(inspectCode[i].trim().substring(0, 5).equals("05483")||inspectCode[i].trim().equals("7218500") || inspectCode[i].trim().equals("7218502")))))) { // 단독
					continue;
				}

				// 박애병원
				if (hosCode[i].trim().equals("22023")&& inspectCode[i].trim().substring(0, 5).equals("05008") && !inspectCode[i].trim().equals("0500802")  ) { // 단독
					continue;
				}
				
				// 광명성애 병원
				if (hosCode[i].trim().equals("28135")&& inspectCode[i].trim().substring(0, 5).equals("72194") && !inspectCode[i].trim().equals("7219401")  ) { // 단독
					continue;
				}
				
				
				// 박애병원 컬쳐 & ID 에서 내부 검사 관련 항목 노출 안해달라는 요청
				/* ex) System.out.println("파일명 :" + mcr03rm.createExcel("08217", "20150912", "20150912", true));
				 *  20150908	LPC5911	2012	Biopsy생검1-3개				520110-*******
					Autoclave1번		LZAMG05		Culture & I.D				-*******
					Autoclave2번		LZAMG05		Culture & I.D				-*******
					E.O Gas1번		LZAMG05		Culture & I.D				-*******
				 */
				if (hosCode[i].trim().equals("22023")&& (inspectCode[i].equals("31010")) ) { 
					if("".equals(data[12])&& "-*******".equals(data[14]) ){ 
						continue;	
					}
					
				}
				
				// 아이앤젤 건너뛰기
				if (inspectCode[i].trim().length() == 7 && ((hosCode[i].trim().equals("26609"))&& !inspectCode[i].trim().equals("0548150") && !inspectCode[i].trim().equals("0548356") 
						&& (inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102") || inspectCode[i].trim().equals("1110103") 
								||inspectCode[i].trim().substring(0, 5).equals("05481") ||inspectCode[i].trim().substring(0, 5).equals("05483") ))) { // 단독
					continue;
				}
				// 아이앤젤 건너뛰기 - 검체번호가 없는 환자도 건너뛰도록함
				if (hosCode[i].trim().equals("26609")&& specNo[i].trim().length()<=0) { // 단독
					continue;
				}

				// 광주세계로 2126400건너뛰도록함
				if (hosCode[i].trim().equals("23401") && (inspectCode[i].trim().equals("2126400") || inspectCode[i].trim().equals("51023") || inspectCode[i].trim().equals("2106501")
						|| inspectCode[i].trim().equals("2106502")|| inspectCode[i].trim().equals("2106503"))) {
					continue;
				}
				// 초앤유
				if (hosCode[i].trim().equals("27048") && ((inspectCode[i].trim().substring(0, 5).equals("05481")&&!inspectCode[i].trim().equals("0548150") )
						|| (inspectCode[i].trim().substring(0, 5).equals("05483")&&!inspectCode[i].trim().equals("0548356") ))) { // 단독
					continue;
				}

				// 영주기독병원 텐덤 결과만 출력
				if (hosCode[i].trim().equals("20231") && (
						//(inspectCode[i].trim().substring(0, 5).equals("05481")&&!inspectCode[i].trim().equals("0548150") )	|| 
						(inspectCode[i].trim().substring(0, 5).equals("05483")&&!inspectCode[i].trim().equals("0548356") ))) { // 단독
					continue;
				}
				
				// 정동병원
				if (hosCode[i].trim().equals("28668") && (
						inspectCode[i].trim().equals("1130100") ||inspectCode[i].trim().equals("1130101")
						 ||inspectCode[i].trim().equals("1130102") ||inspectCode[i].trim().equals("1130103")
						 ||inspectCode[i].trim().equals("1130104")  ||inspectCode[i].trim().equals("1130105")
						 ||inspectCode[i].trim().equals("1130106")
						 ||inspectCode[i].trim().equals("7125200")
						 ||inspectCode[i].trim().equals("7125202")
						 ||inspectCode[i].trim().equals("7125203")
						 
						 )) { // 단독
					continue;
				}
				

				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05010")&&!inspectCode[i].trim().equals("0501002"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				
				
				// 미래아동병원
				if (hosCode[i].trim().equals("28290") && (Integer.parseInt(inspectCode[i].trim().substring(0, 5))>=21629 &&Integer.parseInt(inspectCode[i].trim().substring(0, 5))<=21636)) { // 단독
					continue;
				}
			
				// 녹향병원 0009501 외에 삭제 처리  
				if (hosCode[i].trim().equals("29038") && (inspectCode[i].trim().substring(0, 5).equals("00095")  && ! inspectCode[i].trim().equals("0009501"))) { // 단독
					continue;
				}
				
				// 녹향병원 0009501 외에 삭제 처리  
				if (hosCode[i].trim().equals("29038") && (inspectCode[i].trim().substring(0, 5).equals("00083")  && 
						!(inspectCode[i].trim().equals("0008302")|| inspectCode[i].trim().equals("0008303")))) { // 단독
					continue;
				}
				
				// 녹향병원 0009501 외에 삭제 처리  
				if (hosCode[i].trim().equals("29038") && (inspectCode[i].trim().substring(0, 5).equals("71252")  && 
						!(inspectCode[i].trim().equals("7125201")))) { // 단독
					continue;
				}
				
				
				// 새부산병원 0009501 외에 삭제 처리 , 세흥병원 28451 추가 
				if (hosCode[i].trim().equals("28451")  && (inspectCode[i].trim().substring(0, 5).equals("00095")  && ! inspectCode[i].trim().equals("0009501")) ) { // 단독
					continue;
				}

				// 새부산병원 0009501 외에 삭제 처리 , 세흥병원 28451 추가 
				if (hosCode[i].trim().equals("28451")  && (inspectCode[i].trim().substring(0, 5).equals("71251")  && ! inspectCode[i].trim().equals("7125102")) ) { // 단독
					continue;
				}

				// 새부산병원 0009501 외에 삭제 처리 , 세흥병원 28451 추가 
				if (hosCode[i].trim().equals("28451")  && (inspectCode[i].trim().substring(0, 5).equals("71252")  && ! inspectCode[i].trim().equals("7125202")) ) { // 단독
					continue;
				}
				
				// 새부산병원 0009501 외에 삭제 처리 , 세흥병원 28451 추가 
				if (hosCode[i].trim().equals("28451")  && (inspectCode[i].trim().substring(0, 5).equals("71259")  && ! inspectCode[i].trim().equals("7125901")) ) { // 단독
					continue;
				}
				
				
				// 세명병원 28253 00095 01 번  71252 02 번 노출 요청 
				if (hosCode[i].trim().equals("28253") && 
						( ( inspectCode[i].trim().substring(0, 5).equals("00095")  && ! inspectCode[i].trim().equals("0009501"))
						||  ( inspectCode[i].trim().substring(0, 5).equals("71252")  && ! inspectCode[i].trim().equals("7125202"))   )) { // 단독
					continue;
				}
				
				// 미래아동병원
				if (hosCode[i].trim().equals("28290") && (data[4]==null ||data[12]==null || "".equals(data[4]) || "".equals(data[12])) ) { 
					continue;
				}
				
				//대청병원
				if (inspectCode[i].trim().length() == 7
						&& (hosCode[i].trim().equals("28279") 
								&& (inspectCode[i].trim().equals("2106801") || inspectCode[i].trim().equals("2106802")
								|| inspectCode[i].trim().equals("2106803")|| inspectCode[i].trim().equals("2106804")
								|| inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals("7125203") // iu 추가 작업 20150918
								))) { // 단독
					continue;
				}
				// 광주세계로 00904 결과가 단문셀에 나오도록 함
				if (hosCode[i].trim().equals("23401") && (inspectCode[i].trim().equals("00904"))) {
					resultType[i] = "C";
				}
				
				// 강북삼성요양병원 부속의 00번 빼기
				if (hosCode[i].trim().equals("27995") && inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(5, 7).equals("00")
						||inspectCode[i].trim().equals("0009502")||inspectCode[i].trim().equals("0009503"))) {
					continue;
				}
				
				// 새부산병원
				if (hosCode[i].trim().equals("28830") && inspectCode[i].trim().length() == 7 && (!inspectCode[i].trim().equals("0009501"))) {
					continue;
				}
				
				// 정동병원 4102601 값만 잘라서
				if (hosCode[i].trim().equals("28668") && inspectCode[i].trim().length() == 7 && inspectCode[i].trim().substring(5, 7).equals("41026") && !inspectCode[i].trim().equals("4102601")) {
					continue;
				}
				
				

				if (resultType[i].trim().equals("C")) {
					data[34] = result[i].trim(); // 문자결과
					data[36] = result[i].trim();
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
					data[35] = "";
					data[39] = getReferenceValueAge(remark);
					boolean isTxtRltB = false;
					
					//cdy
					if((hosCode[i].trim().equals("26853")||hosCode[i].trim().equals("26609")||hosCode[i].trim().equals("27897")||hosCode[i].trim().equals("28106")) 
							&& inspectCode[i].trim().substring(0, 5).equals("21258")  
							&& i+5<cnt	&&  inspectCode[i+5].trim().substring(0, 5).equals("41392") 
							)
					{
						
						data[34]  = "";
						data[39]  = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];

						data[35] = appendBlanks("검  사  명 ", 30) + "   "	+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+  getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
						i++;
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
						i++;
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
						i++;
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
						i++;
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
						i++;
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();

						data[36] = data[35].trim();
						
						
					}else if(//cdy
							(hosCode[i].trim().equals("26853")||hosCode[i].trim().equals("26609")
									||hosCode[i].trim().equals("27897")||hosCode[i].trim().equals("28106")) 
									&& inspectCode[i].trim().substring(0, 5).equals("21258") && 
									i+5>=cnt   
							){
						isNext =true;
						cnt =cnt-5;
						//setParameters(new String[] { hosCode[cnt - 1], rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1], seq[cnt - 1] });
						break;
					}
					
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("27991")||hosCode[i].trim().equals("28028")||hosCode[i].trim().equals("28042")) 
							&& (inspectCode[i].trim().substring(5, 7).equals("00"))) {
						data[34]  = "";
					}
					if (!isTxtRltB && hosCode[i].trim().equals("22023") && (inspectCode[i].trim().equals("31059"))) {
						data[34] = ""; // 문자결과
						data[35] = result[i].trim(); // 문장결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22023")
							&& (inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("71252")
									|| inspectCode[i].trim().substring(0, 5).equals("71251") || inspectCode[i].trim().substring(0, 5).equals("71259")
									 || inspectCode[i].trim().substring(0, 5).equals("71298") || inspectCode[i].trim().substring(0, 5).equals("72242")
									 || inspectCode[i].trim().substring(0, 5).equals("72241") )) {

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("28805")
							&& (inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("00094") )) {

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("28253")
							&& (inspectCode[i].trim().substring(0, 5).equals("31001"))) {

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("29125")
							&& (inspectCode[i].trim().substring(0, 5).equals("72182") || inspectCode[i].trim().substring(0, 5).equals("72183")
									|| inspectCode[i].trim().substring(0, 5).equals("72208")
									|| inspectCode[i].trim().substring(0, 5).equals("72242")
									|| inspectCode[i].trim().substring(0, 5).equals("72189")
									|| inspectCode[i].trim().substring(0, 5).equals("11101")
									
									)) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					
					

					if (!isTxtRltB && hosCode[i].trim().equals("29404") && (inspectCode[i].trim().equals("41381"))) {
						data[34] = getResultMSAFP(new String[] { rcvDate[i], rcvNo[i] });
					}
					

					if (!isTxtRltB
							&& hosCode[i].trim().equals("29404")
							&& (inspectCode[i].trim().substring(0, 5).equals("05483")||inspectCode[i].trim().substring(0, 5).equals("00095"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					

					if (!isTxtRltB
							&& hosCode[i].trim().equals("28829")
							&& (inspectCode[i].trim().substring(0, 5).equals("72182") || inspectCode[i].trim().substring(0, 5).equals("72183"))) {

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("28933")
							&& (inspectCode[i].trim().substring(0, 5).equals("11052")||inspectCode[i].trim().substring(0, 5).equals("72206")||inspectCode[i].trim().substring(0, 5).equals("72242") || inspectCode[i].trim().substring(0, 5).equals("72182") || inspectCode[i].trim().substring(0, 5).equals("72183"))) {

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB && hosCode[i].trim().equals("28290")	&& (inspectCode[i].trim().substring(0, 5).equals("21628"))) {

						data[35] = "별지보고";
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] ="별지보고";
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					// 이해안됨! 71251,71252,00752,22023,72059,72018 첫번째 부속값만 출력되도록
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&&(hosCode[i].trim().equals("22023") && (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))))
					{
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("01")) {
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
						data[36] = data[35].trim();
					}

					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("28135") && (inspectCode[i].trim().substring(0, 5).equals("71252"))) {
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if(inspectCode[i].trim().equals("7125201"))
							{
								data[35] = result[i];
								data[39] = getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							else if(inspectCode[i].trim().equals("7125202"))
							{
								data[34] = result[i];
								data[39] += "\r\n" +getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					}

					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("25382") && (inspectCode[i].trim().substring(0, 5).equals("72182")||inspectCode[i].trim().substring(0, 5).equals("72183")||inspectCode[i].trim().substring(0, 5).equals("11052"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("28830") && (inspectCode[i].trim().substring(0, 5).equals("11052") )) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if(result[i].trim().length()>0) //정동병원 그람스테인 결과에서 결과값이 있는 항목만 집계되도록 수정
							{
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && ( hosCode[i].trim().equals("28668") ) && (inspectCode[i].trim().substring(0, 5).equals("11002")
							||inspectCode[i].trim().substring(0, 5).equals("11052")||inspectCode[i].trim().substring(0, 5).equals("31001") 
							|| inspectCode[i].trim().substring(0, 5).equals("00683") || inspectCode[i].trim().substring(0, 5).equals("00684")
							||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
							|| inspectCode[i].trim().substring(0, 5).equals("00901")
							||inspectCode[i].trim().substring(0, 5).equals("11301"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if(result[i].trim().length()>0) //정동병원 그람스테인 결과에서 결과값이 있는 항목만 집계되도록 수정
							{
								
								if(isMastDuplPrint(inspectCode[i].trim())){
									
								}else{
									data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
											+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();	
								}
									
								
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					
					
					if (!isTxtRltB && (( hosCode[i].trim().equals("29038")) && (inspectCode[i].trim().substring(0, 5).equals("11052")||inspectCode[i].trim().substring(0, 5).equals("72182")||inspectCode[i].trim().substring(0, 5).equals("72183")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					
					
					if (!isTxtRltB && (( hosCode[i].trim().equals("26853")||hosCode[i].trim().equals("27897")||hosCode[i].trim().equals("28106")) && (inspectCode[i].trim().substring(0, 5).equals("00015")||inspectCode[i].trim().substring(0, 5).equals("72035")
							||inspectCode[i].trim().substring(0, 5).equals("00095")||inspectCode[i].trim().substring(0, 5).equals("71252")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					
					
					if (!isTxtRltB && (hosCode[i].trim().equals("21114") && (inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("21114")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && (hosCode[i].trim().equals("28106") && (inspectCode[i].trim().substring(0, 5).equals("11052")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}

					if (!isTxtRltB && hosCode[i].trim().equals("28414") && (inspectCode[i].trim().substring(0, 5).equals("00095")
							||inspectCode[i].trim().substring(0, 5).equals("00317")||inspectCode[i].trim().substring(0, 5).equals("00319")
							||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")
							||inspectCode[i].trim().substring(0, 5).equals("00689")||inspectCode[i].trim().substring(0, 5).equals("11052")
							||inspectCode[i].trim().substring(0, 5).equals("11301")||inspectCode[i].trim().substring(0, 5).equals("21061")
							||inspectCode[i].trim().substring(0, 5).equals("31001")||inspectCode[i].trim().substring(0, 5).equals("71252")
							||inspectCode[i].trim().substring(0, 5).equals("71259")||inspectCode[i].trim().substring(0, 5).equals("71282")
							||inspectCode[i].trim().substring(0, 5).equals("11002")||inspectCode[i].trim().substring(0, 5).equals("11230")
							||inspectCode[i].trim().substring(0, 5).equals("00802")||inspectCode[i].trim().substring(0, 5).equals("00822")
							)) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							
							if(!isMastDuplPrint(inspectCode[i].trim())){
								if (!result[i].toString().trim().equals("")) {
									data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
											+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
								}	
							}
							
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}

					
					if (!isTxtRltB && hosCode[i].trim().equals("27640") && (inspectCode[i].trim().substring(0, 5).equals("11052")
							||inspectCode[i].trim().substring(0, 5).equals("31001")||inspectCode[i].trim().substring(0, 5).equals("00095"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && ((hosCode[i].trim().equals("23906")||hosCode[i].trim().equals("25663")) && (inspectCode[i].trim().substring(0, 5).equals("11052")//
							||inspectCode[i].trim().substring(0, 5).equals("00673")
							||inspectCode[i].trim().substring(0, 5).equals("00683")
							||inspectCode[i].trim().substring(0, 5).equals("00684")
							||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
							||inspectCode[i].trim().substring(0, 5).equals("00674")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							
							
							if(!isMastDuplPrint(inspectCode[i].trim())){
								if (!result[i].toString().trim().equals("")) {
									data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
											+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
								}	
							}
								
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("28540")
							&& (inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							String arrRefer[] = getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim().split("\r\n");
							String strRefer = arrRefer[0];
							
							if(isMastPrint(inspectCode[i].trim())){
								for (int r = 1; r < arrRefer.length; r++) {
									strRefer = strRefer + "\r\n" + insertBlanks(arrRefer[r].toString(), 55);
								}
								data[35] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + strRefer;
								if (++i == cnt)
									break;

							}else{
								  i++;
							}
	                            
							
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB && (hosCode[i].trim().equals("26609")||hosCode[i].trim().equals("15710")||hosCode[i].trim().equals("25663")) && (inspectCode[i].trim().substring(0, 5).equals("31001")//
							||inspectCode[i].trim().substring(0, 5).equals("00673")
							||inspectCode[i].trim().substring(0, 5).equals("00674")//
							||inspectCode[i].trim().substring(0, 5).equals("00683")
							||inspectCode[i].trim().substring(0, 5).equals("00684")//
							||inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")
							||inspectCode[i].trim().substring(0, 5).equals("72035")
							||inspectCode[i].trim().substring(0, 5).equals("31003")||inspectCode[i].trim().substring(0, 5).equals("72185"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							
							if(!isMastDuplPrint(inspectCode[i].trim())){
								if (!result[i].toString().trim().equals("")) {
									data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
											+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
								}	
							}
								
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("27254") && (inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("21068"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					
					//20161013 양태용 세란병원 71311 검사 장문으로 .. 리마크 포함
					if (!isTxtRltB && hosCode[i].trim().equals("29740") && (inspectCode[i].trim().substring(0, 5).equals("71311"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) ;
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();

						// !
					}
					
					if (!isTxtRltB && hosCode[i].trim().equals("28395") && (inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("11052"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					
					if (!isTxtRltB && (hosCode[i].trim().equals("29454") && (inspectCode[i].trim().substring(0, 5).equals("00095") ))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}

					
					
					if (!isTxtRltB && hosCode[i].trim().equals("20740") && (inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("11301")
							 || inspectCode[i].trim().substring(0, 5).equals("00095"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					
					if (!isTxtRltB && hosCode[i].trim().equals("28933") && (inspectCode[i].trim().substring(0, 5).equals("31001") )) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					
					if (!isTxtRltB && hosCode[i].trim().equals("28408") && (inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("71252")
							|| inspectCode[i].trim().substring(0, 5).equals("31001"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}	
					if (!isTxtRltB && (hosCode[i].trim().equals("28241") && (inspectCode[i].trim().substring(0, 5).equals("72182")//
							||inspectCode[i].trim().substring(0, 5).equals("72183")||inspectCode[i].trim().substring(0, 5).equals("72242")
							||inspectCode[i].trim().substring(0, 5).equals("11052")
							||inspectCode[i].trim().substring(0, 5).equals("72206")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB && hosCode[i].trim().equals("23401") && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("71251") || inspectCode[i].trim().substring(0, 5).equals("21061") 
									|| inspectCode[i].trim().substring(0, 5).equals("11003") || inspectCode[i].trim().substring(0, 5).equals("11002") 
									|| inspectCode[i].trim().substring(0, 5).equals("11303") || inspectCode[i].trim().substring(0, 5).equals("71259") 
									|| inspectCode[i].trim().substring(0, 5).equals("00901") || inspectCode[i].trim().substring(0, 5).equals("11306") 
									|| inspectCode[i].trim().substring(0, 5).equals("11308") || inspectCode[i].trim().substring(0, 5).equals("11309") 
									|| inspectCode[i].trim().substring(0, 5).equals("11302") || inspectCode[i].trim().substring(0, 5).equals("11304") 
									|| inspectCode[i].trim().substring(0, 5).equals("11306") || inspectCode[i].trim().substring(0, 5).equals("81469") 
									|| inspectCode[i].trim().substring(0, 5).equals("00405") || inspectCode[i].trim().substring(0, 5).equals("21061") 
									|| inspectCode[i].trim().substring(0, 5).equals("00316") 	|| inspectCode[i].trim().substring(0, 5).equals("72018") 
									|| inspectCode[i].trim().substring(0, 5).equals("31001") 	|| inspectCode[i].trim().substring(0, 5).equals("72185") 
									|| inspectCode[i].trim().substring(0, 5).equals("72182")	|| inspectCode[i].trim().substring(0, 5).equals("11052") 
									|| inspectCode[i].trim().substring(0, 5).equals("72205") || inspectCode[i].trim().substring(0, 5).equals("72206") )) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValueAge(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim(); // 단독

						// !
					}	if (!isTxtRltB && hosCode[i].trim().equals("28412") && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("00822") || inspectCode[i].trim().substring(0, 5).equals("31001") 
									|| inspectCode[i].trim().substring(0, 5).equals("31003") || inspectCode[i].trim().substring(0, 5).equals("72185")  )) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValueAge(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim(); // 단독

						// !
					}

					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("27995"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("11101"))) { // 단독아님
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21) + getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21)
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}

					
					
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("28279"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("31001")//inspectCode[i].trim().substring(0, 5).equals("00405") ||
									|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("11101")
									|| inspectCode[i].trim().substring(0, 5).equals("21065") || inspectCode[i].trim().substring(0, 5).equals("31003")
									|| inspectCode[i].trim().substring(0, 5).equals("31005") || inspectCode[i].trim().substring(0, 5).equals("00309")
									|| inspectCode[i].trim().substring(0, 5).equals("72185") || inspectCode[i].trim().substring(0, 5).equals("21061"))) { // 단독아님
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치";
						if(hosCode[i].trim().equals("28279")) data[35]="";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21) + getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21)
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB && hosCode[i].trim().equals("23401") &&  isEP(inspectCode[i].trim().substring(0,5))) {
						try {
							data[34] = ""; // 문자결과
							data[35] = getResultEEP2(new String[] { data[31], data[32], data[33].substring(0,5) });
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							isTxtRltB = true;
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode	.equals(inspectCode[i].trim().substring(0,	5))&& data[31].equals(rcvDate[i].trim())&& data[32].equals(rcvNo[i].trim());){
								data[39] = getReferenceValueAge(new String[] {inspectCode[i].trim(), lang[i], history[i], sex[i], age[i] }).trim();
								if (++i == cnt)
									break;
							}
							i--;
							data[35] = data[35].trim()+"\r\n\r\n[Comment]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						} catch (Exception e) {
						}
					}
					if (!isTxtRltB && hosCode[i].trim().equals("23401") && inspectCode[i].trim().substring(0, 5).equals("00907") && data[34].trim().equals("Remark 참조")) {
						data[35] = data[34].trim()+"\r\n\r\n리마크\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]); // 단독
						data[34] = "";
					}

					if (!isTxtRltB && (isMAST_Two(inspectCode[i].trim().substring(0, 5)) || isMAST(inspectCode[i].trim().substring(0, 5)))&&//
							 (hosCode[i].trim().equals("23401")||hosCode[i].trim().equals("25382")||hosCode[i].trim().equals("28241")||hosCode[i].trim().equals("22023")
									 ||hosCode[i].trim().equals("28933"))) { 
								try {// 삼육제외
									Vector vmast = new Vector();
									isTxtRltB = true;
									data[34] = "";   
									data[39] = "";
									data[35] = appendBlanks("검사항목", 30) + appendBlanks("CLASS", 8) + appendBlanks("검사항목", 30) + appendBlanks("CLASS", 8);
									data[35] += getDivide() + "\r\n";
									data[36] = data[35].trim();
									vmast.addElement(appendBlanks(inspectName[i].trim(), 30) + appendBlanks(result[i].trim(), 8));   
									curDate = rcvDate[i];  
									curNo = rcvNo[i];		
									int mastad =0;
									if (i+1 == cnt || i > cnt){
										break;
									}
									for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode	.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
										try {		
												if(mastad==0)
												{
													vmast.addElement(appendBlanks(inspectName[i], 30) + appendBlanks(result[i++].trim(), 8));
												}else
												{
													vmast.addElement(appendBlanks(inspectName[i], 30) + appendBlanks(result[i++].trim().substring(0, 1), 8));
												}
										} catch (Exception e) {
										}
										if (++i == cnt || i > cnt){
											i--;
											break;
										}
										i--;
										mastad++;

									}
									data[35] = getResultMAST_Two(data[35].toString(), vmast) + getDivide() + "\r\n" + getMastRemark();
									data[36] = data[35].trim();
									i--;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
					
					if (!isTxtRltB && hosCode[i].trim().equals("23401")
							&& (inspectCode[i].trim().substring(0, 5).equals("11301") || inspectCode[i].trim().substring(0, 5).equals("71252"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
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
						data[39] = getReferenceValueAge(remark);
						data[21] = bdt[i]; // 검사완료일
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValueAge(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
						// !
					}
					// 광주세계로 참고치 일단 하드코딩함-추후 수정 필수!!!!
					if (hosCode[i].trim().equals("23401") && (inspectCode[i].trim().equals("00061") || inspectCode[i].trim().equals("00062"))) {
						String Reference = "";
						if (inspectCode[i].trim().equals("00061")) {
							Reference = "8.2   -   10.2   mg/dL";
						} else {
							Reference = "2.5   -   4.5   mg/dL";
						}
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + Reference;
						curDate = rcvDate[i];
						isTxtRltB = true;
						data[34] = ""; // 문자결과
						curNo = rcvNo[i];
						data[39] = Reference;
						data[21] = bdt[i]; // 검사완료일

						data[36] = data[35].trim();
						// !
					}
					
									
					if (!isTxtRltB
							&& hosCode[i].trim().equals("23401")
							&& ((inspectCode[i].trim().substring(0, 5).equals("41035")) || (inspectCode[i].trim().substring(0, 5).equals("41097"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41114"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21613"))
									|| (inspectCode[i].trim().substring(0, 5).equals("00114"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41024"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41081"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21264"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41075"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21430"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21110"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21565"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41201"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41035"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41110"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21149"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21207"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21208"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21123"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21124"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41109"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41118"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21115"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21116"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41132"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21178"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21213")) 
									|| (inspectCode[i].trim().substring(0, 5).equals("21146"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21145"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41025"))
									|| (inspectCode[i].trim().substring(0, 5).equals("41092"))
									|| (inspectCode[i].trim().substring(0, 5).equals("81370"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21141"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21127"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21551"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21218"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21106"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21107"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21378"))
									|| (inspectCode[i].trim().substring(0, 5).equals("21379"))
									|| (inspectCode[i].trim().substring(0, 5).equals("00719")))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValueAge2(remark);
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
						data[39] = getReferenceValueAge(remark);
						data[21] = bdt[i]; // 검사완료일

						data[36] = data[35].trim();
						// !
					}
					if (!isTxtRltB && (hosCode[i].trim().equals("27936")||hosCode[i].trim().equals("27902")) && inspectCode[i].trim().length() == 7) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[34] = data[35].trim(); // 단독
						data[36] = data[35].trim(); // 단독

						// !
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("25663") && inspectCode[i].trim().length() == 7 && 
									(inspectCode[i].trim().substring(0, 5).equals("31001")
									|| inspectCode[i].trim().substring(0, 5).equals("72185")
									|| inspectCode[i].trim().substring(0, 5).equals("05418")
									|| inspectCode[i].trim().substring(0, 5).equals("11301")
									|| inspectCode[i].trim().substring(0, 5).equals("00901")
									|| inspectCode[i].trim().substring(0, 5).equals("21061")))) { // 단독

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValueAge(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("29229")
							&& (inspectCode[i].trim().substring(0, 5).equals("72185"))) {

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t";// + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + result[i].trim() + "\t";	//+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + result[i].trim() + "\t";
									//+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}

					
					//20150930 23401 병원 추가 - 광주 세계로 병원
					if (!isTxtRltB
							&& ( ( hosCode[i].trim().equals("28507") || hosCode[i].trim().equals("23401") ) && (inspectCode[i].trim().substring(0, 5).equals("72194")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValueAge(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--; 
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"\r\n"+getHpyloriPcr();
						data[36] = data[35].trim();
					}
					
					//20151123 새통영병원 문잔형태로 변경작업 
					if (!isTxtRltB
							&& ( ( hosCode[i].trim().equals("28982") ) && (inspectCode[i].trim().substring(0, 5).equals("72194")||inspectCode[i].trim().substring(0, 5).equals("72199")||inspectCode[i].trim().substring(0, 5).equals("72203")||inspectCode[i].trim().substring(0, 5).equals("72178")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValueAge(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--; 
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"\r\n"+getHpyloriPcr();
						data[36] = data[35].trim();
					}
					
					
					data[37] = highLow[i].replace(".", " "); // 판정에 . 빼기
					//아이본 병원의경우 일부검사결과가 Positive이면 판정값에 'E'문구 입력-20150903
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("25663") && inspectCode[i].trim().length() == 7 && 
									(inspectCode[i].trim().substring(0, 5).equals("72182")
									|| inspectCode[i].trim().substring(0, 5).equals("72183")
									|| inspectCode[i].trim().substring(0, 5).equals("72242")
									|| inspectCode[i].trim().substring(0, 5).equals("72206")))) {
						if(data[34].toString().indexOf("Positive")>=0)
						{
							data[37] = "E";
						}
					}
					// ! 여기서 부터
					// 문장형--------------------------------------------------------------------------------------------------------------------------------------------------------
				} else {
					boolean isTxtRltC = false;
			
					data[34] = ""; // 문자결과
					data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
					data[36] = data[35].trim();
					
					//char ch=data[36].charAt(358);
					if("28805".equals(hosCode[i].trim())){
						data[36] = data[36].replaceAll(Character.toString((char)12539), "-");
						data[35] = data[35].replaceAll(Character.toString((char)12539), "-");
					}
					
					data[39] = ""; // 참고치
					data[37] = highLow[i]; // 결과상태
					
					//int str_index = data[35].indexOf("전문의");
					int str_index = data[35].indexOf("면허");
					
					if (str_index > 10) {
						data[35] = data[35].substring(0, str_index);
						data[36] = data[35].trim();
					}
					//정동병원 Blood culture, AFB culture 중간보고는 연동되지 않도록 함
					if (!isTxtRltC && (hosCode[i].trim().equals("28668")) && (inspectCode[i].trim().equals("31014")||inspectCode[i].trim().equals("31019")||inspectCode[i].trim().equals("31077"))) {
						if(data[35].indexOf("중　간　결　과　보　고")>=0)
						{
							continue;
						}
						
					}
					//컬쳐&센시 합치기
					if (!isTxtRltC && (hosCode[i].trim().equals("25382")||hosCode[i].trim().equals("23906")||hosCode[i].trim().equals("22023")||hosCode[i].trim().equals("28668")||hosCode[i].trim().equals("27995")) && (inspectCode[i].trim().equals("31010"))) {
						isTxtRltC = true;
						data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[36] = data[35].trim();
						try {
							if ((inspectCode[i + 1].substring(0, 5).equals("31011")||inspectCode[i + 1].substring(0, 5).equals("31012")) && rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
								data[36] = data[35].trim();
								i++;
								// culture_flag = true;
							} else {
								data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
								data[36] = data[35].trim();
							}
						} catch (Exception e) {
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[36] = data[35].trim();
						}
					}
				}

				// ------------------------------------------------------------------------

				//부산특영 거래처 이미지 파일명 추가
				if((hosCode[i].trim().equals("30286"))&&data[4].trim().length()>0)
				{
					data[19] = data[4]+"_"+data[6]+"_01.jpg";
				}
				if((hosCode[i].trim().equals("28395")))
				{
					data[18] ="외래";
				}
				if (inspectCode[i].trim().substring(0, 5).equals("11052")) {
					remarkCode = "";
				}
				
				remarkCode = rmkCode[i].trim();
				
				if (rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							
							if (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("11052")) {
								data[35] = data[35] + getDivide() + "\r\n" + "\r\n" + getDivide()
										+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
								data[36] = data[35].trim();
							} else {
								data[38] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							}
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
						
//						System.out.println(remarkCode);
//						System.out.println(inspectCode[i]);
//						System.out.println(patName[i]);
						
						
					} catch (Exception _ex) {
					}
				} else {
					remarkCode = "";
				}
				if(hosCode[i].trim().equals("23401") &&inspectCode[i].trim().substring(0, 5).equals("11301"))
				{
					data[35] = data[35]+"\r\n\r\n[Remark]\r\n"+data[38];
					data[36]=data[35] ; 
				}
				// !-------------------------------------------
				boolean isTxtRltA = false;
				
				data[11] = "";
				
				// ! 참고치
				try {
					data[39] = data[39].trim();
				} catch (Exception yxx) {
				}
				//정동병원은 일부검사만 참고치가 보이도록 요청
				if (hosCode[i].trim().equals("28668") && (!inspectCode[i].trim().equals("41052")&&!inspectCode[i].trim().equals("41054")&&!inspectCode[i].trim().equals("41114")&&!inspectCode[i].trim().equals("00045")&&!inspectCode[i].trim().equals("21565")&&!inspectCode[i].trim().equals("41092")
						&&!inspectCode[i].trim().equals("41097")&&!inspectCode[i].trim().equals("41025")&&!inspectCode[i].trim().equals("41024")&&!inspectCode[i].trim().equals("21430")&&!inspectCode[i].trim().equals("41110"))) { // 단독
					data[39] = "";
				}
				//대청병원 단문+장문에 참고치 추가 되도록 수정
				if (hosCode[i].trim().equals("28279") && data[39].trim().length()>0)
				{
					if(data[34].trim().length()>0)data[35]=data[34];
					data[36] = data[35]+"\r\n[*참고치*]\r\n"+data[39];
				}
				
				//광명 성애병원의 71252 검사이면 리마크란에 7125201 결과가 입력되도록
				if (hosCode[i].trim().equals("28135") && inspectCode[i].trim().substring(0, 5).equals("71252"))
				{
					data[38] = data[35]+" Copies/mL";
					data[35] = "";
				}

				//에댈산부인과 20160428 요청 단문 장문 결과 같이넣기
				if (hosCode[i].trim().equals("29404") && (inspectCode[i].trim().equals("31060"))) {
					data[34] = data[35];
					data[36] =data[35].trim();
				}
//				if (hosCode[i].trim().equals("28540")) {
//					System.out.println(data[35]);
//					data[34] = data[36];
//				}
				// !
				if (!debug) {
					int lens = getExcelFieldNameArray().length;
					try {
						for (k = 0; k < lens; k++) {
							label = new jxl.write.Label(k, row, data[Integer.parseInt(getExcelFieldArray()[k].toString())]);
							wbresult.addCell(label);
						}
					} catch (Exception xx) {
					}
				}
				row++;
			}
			
			if (cnt == 400 || isNext) {
				setParameters(new String[] { hosCode[cnt - 1], rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1], seq[cnt - 1] });
			} else {
				setParameters(null);
			}
			
		} catch (Exception _ex) {
			setParameters(null);
		}
	}
}

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
public class DownloadKH extends ResultDownload {
	boolean debug = false;

	//
	// private String gubun1 = 
	// "\n============================================================\n";
	// private String gubun2 =
	// "\n------------------------------------------------------------\n";
	// private java.text.DecimalFormat df = new
	// java.text.DecimalFormat("#######0.0");
	//
	// private com.neodin.result.PatientInformation mPatientData;
	public DownloadKH() {
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

	public DownloadKH(String id, String fdat, String tdat, Boolean isRewrite) {
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
	 * N = > 구문을 강제로 ' ' 에서 replaceAll "N:" 을 " " 작성 날짜: (2016-08-18 오후 3:07:55)
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
			src_[0] = src_[0].replaceAll("N:"," ").trim();
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
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-20 오전 9:39:07)
	 * 
	 * @return java.lang.String
	 */
	public String get71245() {
		String str = "";
		str += "현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘(Lamivudine)은 \r\n" ;
		str += "HBV중합 효소(Polymerase)의 합성을 억제하여 \r\n";
		str +=	"바이러스 유준자 증폭을 차단하는 역할을 합니다. \r\n" ;
		str +=	"그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의 \r\n" ;
		str +=	"출현확륙이 높아지게 되고 결국에는 치료의 실패내지는 \r\n" ;
		str +=	"재발로 이어지는 문제점을 나타 냅니다.\r\n" ;
		str +=	"이러한 변이형은 HBV중합 효소(Polymerase)내의 codon552와 codon528 염기 서열의\r\n" ;
		str +=	"돌연변이를 주로 발생하게 되는데 본검사는 HBV중합 효소(Polymerase)의 특정부위를\r\n" ;
		str +=	"증폭한 후 염기 서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\r\n" ;
		str +=	"따라서 이러한 변이형의 발견은 치료경과 관찰, Vrial breakthrough 의 \r\n" ;
		str +=	"조기 발견및 치료방침을 결정하는데 유용하게 사용될 수 있습니다.\r\n";
		return str;
	}

	protected String getRemarkTxt2(String str[]) {
		StringBuffer b = new StringBuffer("");
		boolean isSensi = false;
		if (str.length == 0)
			return null;

		for (int i = 0; i < str.length; i++) {
			int kk = str[i].trim().lastIndexOf("<균　명>");
			if (kk > -1) {
				b.append("\r\n" + "<균 명 >" + "\r\n\r\n");
				b.append(str[i].trim().substring(5).trim() + " \r\n");
				isSensi = true;
			} else {
				if (isSensi && str[i].trim().trim().startsWith("1")) {
					b.append("                                         " + str[i].trim().trim() + "\r\n\r\n");
				} else {
					b.append(str[i].trim() + "\r\n");
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
	public boolean isReferenceValue14279(String s) {
		if (s.equals("21064") || s.equals("21109") || s.equals("21110") || s.equals("21115") || s.equals("21116") || s.equals("21119")
				|| s.equals("21120") || s.equals("21123") || s.equals("21124") || s.equals("21127") || s.equals("21128") || s.equals("21135")
				|| s.equals("21136") || s.equals("21137") || s.equals("21138") || s.equals("21141") || s.equals("21142") || s.equals("21145")
				|| s.equals("21146") || s.equals("21153") || s.equals("21154") || s.equals("21157") || s.equals("21158") || s.equals("21160")
				|| s.equals("21167") || s.equals("21172") || s.equals("21172") )
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
				workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
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
			String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
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

			
			
			

			for (int i = 0; i < cnt; i++) {
				System.out.println(patName[i]+","+inspectCode[i] );
				
			}
			// !
			String data[] = new String[45];
			String remark[] = new String[5];
			String remarkCode = "";
			boolean isTQ = false;
			int k = 0;
			String lastData = "";
			int lastindex = 0;

			// yty : 400개 마지막에 부속검사 오면 집계 넘어가고  
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
				isNext = true;

				// !
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

				String curDate = "";
				String curNo = "";
				String curGcd = "";
				data[0] = bdt[i]; // 걍~~

				data[1] = Common.cutZero(img[i]); // 내원번호
				data[2] = inc[i]; // 외래구분
				data[3] = ""; // 의뢰일자
				data[4] = specNo[i].trim(); // 검체번호
				// TODO : 삭제할 부분
				
				
				
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				} 
				
				if (is24580_24080(hosCode, i)) {
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

				data[15] = age[i]; // 나이
				data[16] = sex[i]; // 성별
				data[17] = ""; // 과
				data[18] = ""; // 병동
				data[19] = ""; // 참고사항
				
				
				//System.out.println(patName[i]+","+inspectCode[i]);
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
				//처방일자에 처방코드 넣는것~
				if (hosCode[i].trim().equals("23697") || hosCode[i].trim().equals("25791") || hosCode[i].trim().equals("13725")
						|| hosCode[i].trim().equals("26454")|| hosCode[i].trim().equals("29504")|| hosCode[i].trim().equals("26800")
						|| hosCode[i].trim().equals("18535")|| hosCode[i].trim().equals("30267")|| hosCode[i].trim().equals("30300")) {
					data[22] = bgcd[i]; // 처방일자
				}
				
				
				if (makeHrcvDate(hosCode, i)) {
					try {
						data[22] = cutHrcvDateNumber2(cns[i])[1]; // 처방일자
					} catch (Exception eee) {
						data[22] = ""; // 처방일자
					}
				}
			
				
				
				if (hosCode[i].trim().equals("16468")) { //현대요양병원은 처장일자 없으면 접수일자가 입력 되도록
					try {
						data[22] = cutHrcvDateNumber(cns[i])[1]; // 처방일자
					} catch (Exception eee) {
						data[22] = rcvDate[i]; // 처방일자
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
					remark[4] = age[i];
					data[39] = getReferenceValueNotBlank(remark);
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

				
				
				
				
				
				if (hosCode[i].trim().equals("25493") || hosCode[i].trim().equals("25494")|| hosCode[i].trim().equals("27609")) {
					// 처방일자
					data[22] = bgcd[i];
					// 검사일자
					data[22] = Common.change_day1(data[22]);
					// 보고일자
					data[21] = Common.change_day1(data[21]);
				}
				
				/**(S)결과 건너뛰기**/
				// 분당재생병원 건너뛰기
				if (continueHos12657(hosCode, inspectCode, i)) { // 단독
						continue;
				}
				
						

				// 화인메트로병원 20151027 00405 p/c 값 절편 작업
				if (continueHos20796(hosCode, inspectCode, i)) { // 단독
						continue;
				}
				
				if(continueHos29659(hosCode, inspectCode, i)
						){
						continue;
					
				}
				if(continueHos25560(hosCode, inspectCode, i)){
						continue;
					
				}
				if(continueHos29516(hosCode, inspectCode, i)){
						continue;
				}
				
				if(continueHos29463(hosCode, inspectCode, i)){
						continue;
				}
				if(continueHos28280(hosCode, inspectCode, i)){
						continue;
				}

				if (continueHos28696(hosCode, inspectCode, i)) {
					continue;
				}
				// 김한방병원 추후송부 결과 집계 안돼도록
				if (continueHos29171(hosCode, inspectCode, result, i)) {
					continue;
				}
				// 김한방병원 추후송부 결과 집계 안돼도록
				if (continueHos22721(hosCode, result, i)) {
					continue;
				}
				// 김한방병원 추후송부 결과 집계 안돼도록
				if (continueHos29171(hosCode, inspectCode, i) ) {
					continue;
				}

				// KN경남요양병원 추후송부 결과 집계 안돼도록
				if (continueHos29969(hosCode, inspectCode, i) ) {
					continue;
				}
				// 울산현대요양병원 ABO CELL 타입 만 추출
				if (continueHos16468(hosCode, inspectCode, i)) {
					continue;
				}
				
				if (continueHos28930(hosCode, cns, data, i)) {
					continue;
				}
				// 문화여성병원  2163803 2163804 만 보이고 나머지 건너뜀
				if (continueHos25493(hosCode, inspectCode, i)) {
					continue;
				}
				// 문화산부인과 Tandem mess + 4종(99990), 복지부6종(21276) 일경우 21258 검사코드만
				// 보이고 나머지 건너뛰도록함. 0009501값만전송
				if (continueHos25493_2(hosCode, inspectCode, i)) {
					continue;
				}
				// 한국원자력 acratio 건너뛰도록함
				if (hosCode[i].trim().equals("18819") && (inspectCode[i].trim().equals("0008300"))) {
					continue;
				}
				// 서초 참 요양병원 HBA1C 값에  0009501 만연동
				if (continueHos29504(hosCode, inspectCode, i)) {
					continue;
				}
				// 일산자애병원 diffcount00, hba1c 00,02,03, P.T 00,02건너뛰도록함
				if (continueHos17654(hosCode, inspectCode, i)) {
					continue;
				}
				
				
				if (continueHos28833(hosCode, inspectCode, i)) {
					continue;
				}
				
				if (continueHos24100(hosCode, inspectCode, i)) {
					continue;
				}
				
				
				// 에스웰요양병원,동서요양병원
				if (continueHos25812(hosCode, inspectCode, i)) {
					continue;
				}
				// 에스웰요양병원,맑은머리김동욱신경과
				if (continueHos26577(hosCode, inspectCode, i)) {
					continue;
				}
				// 메트로병원,21세기라파병원
				if (continueHos20607(hosCode, inspectCode, i)) {
					continue;
				}

				// 서광병원
				if (continueHos18115(hosCode, inspectCode, i)) {
					continue;
				}
				// 홍익병원
				if (continueHos12471(hosCode, inspectCode, i)) {
					continue;
				}
				// 운남한방병원 부속의 00번 빼기
				if (continueHos25723(hosCode, inspectCode, i)) {
					continue;
				}

				// 거제굿뉴스 0009501만 보이기
				if (continueHos17210(hosCode, inspectCode, i)) {
					continue;
				}
			
				// 거제굿뉴스 0009501만 보이기
				if (continueHos29154(hosCode, inspectCode, i)) {
					continue;
				}
				
				if (continueHos29154_1(hosCode, inspectCode, i)) {
					if(data[35].indexOf("중　간　결　과　보　고")>=0)
					{
						continue;
					}
					
				}
				if (continueHos25815(hosCode, inspectCode, i)) {
					continue;
				}
				if (continueHos26719(hosCode, inspectCode, i)) {
					continue;
				}

				// 모태산부인과 건너뛰기
				if (continueHos26852(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				
				
				// 영주기독병원
				if (continueHos20231(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				
				if (continueHos27693(hosCode, inspectCode, i)) {
					continue;
				}

				// 동탄제일산부인과
				if (continueHos26713(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				// 다온미래 건너뛰기
				if (continueHos20713(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				// 흥해아동병원
				if (continueHos26410(hosCode, inspectCode, i)) { // 단독
					continue;
				}

				// 서울하내과
				if (continueHos15298(hosCode, inspectCode, i)) {
					continue;
				}
				// 삼성조은내과 건너뛰기
				if (continueHos23535(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				// 서울참요양병원
				if (continueHos26454(hosCode, inspectCode, i)) {
					continue;
				}
				
				//울산시티병원
				if (continueHos15428(hosCode, inspectCode, i)) {
					continue;
				}

				//참조은요양병원
				if (continueHos27993(hosCode, inspectCode, i)) {
					continue;
				}
				

				//안종훈 내과 Hba1c 결과는 처방코드 0009501 일때 0009500 처받코드가 다운되도록함
				if (hosCode[i].trim().equals("10650")&& (inspectCode[i].trim().equals("0009501"))) {
					data[6] = clientInspectCode[i-1].trim();
				}
				
				//안종훈내과,예스병원(안산)
				if (continueHos10650(hosCode, inspectCode, i)) {
					continue;
				}

				// 안종훈내과 건너뛰기 - 검체번호,처방일자, 보고일자 가 없는 환자도 건너뛰도록함
				if (hosCode[i].trim().equals("10650")&& specNo[i].trim().length()<=0) { // 단독
					continue;
				}
			
				//경주동산병원
				if (continueHos21505(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				//고성강병원
				if (continueHos29112(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				//일산21세기병원
				if (hosCode[i].trim().equals("27021")&&(inspectCode[i].trim().equals("1130100"))) { // 단독
					continue;
				}
				//울산병원
				if (continueHos13952(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				//특검검사 크레아틴 보정후값만 적용되도록
				if (!hosCode[i].trim().equals("29112")&&is특검검사(inspectCode, i)) { // 단독
					continue;
				}
				
				if (hosCode[i].trim().equals("29112")&&is특검검사2(inspectCode, i)) { // 단독
					continue;
				}
				
				//인천사랑병원 20150807
				if (hosCode[i].trim().equals("14279")&&(inspectCode[i].trim().equals("8137100") || inspectCode[i].trim().equals("8137102"))) { // 단독
					continue;
				}
				
				//명지병원
				if (continueHos22721_1(hosCode, inspectCode, i)) { // 단독
					continue;
				}
				// 성모메디칼 검사코드 연동 시 5자리로 무조건 연동 해야 하여 잘랐음.
				if (hosCode[i].trim().equals("25140") &&  data[33] !=null && data[33].length() > 5 ) {
					data[33] = data[33].substring(0, 5);
				}
				/**(E)결과 건너뛰기**/

				
				
				//현대중앙병원 이미지 등록 양태용 20170105
				System.out.println(rcvDate[i] + "^^^" + rcvNo[i]);//양태용 찾았음 여기서 볼것!!!
				
				
				if ( (hosCode[i].trim().equals("30286") || hosCode[i].trim().equals("30310"))
						&& (inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("31010")
								|| inspectCode[i].trim().equals("7218301")|| inspectCode[i].trim().equals("7218201")
								|| inspectCode[i].trim().equals("7218901")|| inspectCode[i].trim().equals("7224201")
								|| inspectCode[i].trim().equals("0040501")
								|| inspectCode[i].trim().equals("0068800")|| inspectCode[i].trim().equals("0068900")
								|| inspectCode[i].trim().equals("7129700")
								|| inspectCode[i].trim().substring(0, 5).equals("61029")|| inspectCode[i].trim().substring(0, 5).equals("61026")
								|| inspectCode[i].trim().substring(0, 5).equals("61028")
								|| inspectCode[i].trim().substring(0, 5).equals("31014")|| inspectCode[i].trim().substring(0, 5).equals("11052")
								|| inspectCode[i].trim().substring(0, 5).equals("72185")|| inspectCode[i].trim().substring(0, 5).equals("21638")
								|| inspectCode[i].trim().substring(0, 5).equals("21653")|| inspectCode[i].trim().substring(0, 5).equals("81399")
								|| inspectCode[i].trim().substring(0, 5).equals("21274")|| inspectCode[i].trim().substring(0, 5).equals("41121")
								|| inspectCode[i].trim().substring(0, 5).equals("41338")|| inspectCode[i].trim().substring(0, 5).equals("31060")
								|| inspectCode[i].trim().substring(0, 5).equals("21123")|| inspectCode[i].trim().substring(0, 5).equals("05483")
								|| inspectCode[i].trim().substring(0, 5).equals("71311")|| inspectCode[i].trim().substring(0, 5).equals("31016")
								|| inspectCode[i].trim().substring(0, 5).equals("80014")|| inspectCode[i].trim().substring(0, 5).equals("41381")
								|| inspectCode[i].trim().equals("71139")
								
								
								)) { // 단독
					
					data[19] = data[5] +".jpg";
					// !
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
					boolean isTxtRltB = false;
					
//					System.out.println("==========================");
//					System.out.println(getPatientInformationNew(rcvDate[i].trim(),rcvNo[i].trim()));
//					System.out.println("==========================");
					
					
					
					//경희의료원 추후보고 검체부족 재검 보고예정 제외 -양태용 20170316 
					if ((hosCode[i].trim().equals("30418"))) {
						if(data[34].indexOf("재검")>=0 || data[34].indexOf("추후보고")>=0 || data[34].indexOf("검체부족")>=0|| data[34].indexOf("보고예정")>=0)
						{
							continue;
						}
						
					}
					
					
					if (!isTxtRltB && hosCode[i].trim().equals("28930")	&& (inspectCode[i].trim().substring(0, 5).equals("05483"))) {

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
					//cdy
					//System.out.println(inspectCode[i]);
					
					if (!isTxtRltB && isTriple(inspectCode[i].trim().substring(0, 5)) && (!hosCode[i].trim().equals("25493")&&!hosCode[i].trim().equals("26852")&&!hosCode[i].trim().equals("26713")&&!hosCode[i].trim().equals("26853")&&!hosCode[i].trim().equals("20231")
							&&!hosCode[i].trim().equals("22401"))) {
						data[34] = ""; // 리마크
						data[38] = ""; // 리마크
						data[39] = ""; // 참고치
						data[35] = ""; // 문장결과
						data[36] = ""; // 수치+문장
						data[37] = ""; // 상태
						isTQ = false; 
						// ! Triple 검사인지 시작 --------------------- data[6] =
						String result_ = getResultTriple(new String[] { rcvDate[i].trim(), rcvNo[i].trim(), inspectCode[i].trim().substring(0, 5) });
						data[35] = result_;
						data[39] = ""; // 참고치
						isTQ = true;
						isTxtRltB = true;
						// !
						i++;
						try {
							while (isTQ && i > 0 && rcvDate[i].trim().equals(rcvDate[i - 1].trim()) && rcvNo[i].trim().equals(rcvNo[i - 1].trim())) {
								i++;
							}
						} catch (Exception e) {
							// handle exception
						}

						i--;
						if(inspectCode[i].equals("42021")) i--; //임신중독증 검사이면 출력될수 있게 함
						// ! Triple 검사인지 종료 ---------------------
					}
					if (hosCode[i].trim().equals("20713")&&(inspectCode[i].trim().substring(0, 5).equals("41431") ||inspectCode[i].trim().substring(0, 5).equals("42000"))) {
						data[6] = "c160"; 
					}
					if (hosCode[i].trim().equals("20713")&&(inspectCode[i].trim().substring(0, 5).equals("41383") )) {
						data[6] = "c116"; 
					}

					if (!isTxtRltB && isPSTS(inspectCode[i].trim().substring(0, 5)) && (!hosCode[i].trim().equals("25493"))) {
						data[34] = ""; // 리마크
						data[38] = ""; // 리마크
						data[39] = ""; // 참고치
						data[35] = ""; // 문장결과
						data[36] = ""; // 수치+문장
						data[37] = ""; // 상태
						isTQ = false;
						// ! Triple 검사인지 시작 ---------------------
						String result_ = getResultRSTS(new String[] { rcvDate[i].trim(), rcvNo[i].trim(), inspectCode[i].trim().substring(0, 5) });
						data[35] = result_;
						data[39] = ""; // 참고치
						isTQ = true;
						isTxtRltB = true;
						// !
						i++;
					}
					
					//겸희대 양태용
					
					if (!isTxtRltB
							&& !(inspectCode[i].trim().substring(0, 5).equals("05496")||inspectCode[i].trim().substring(0, 5).equals("81204")
									||inspectCode[i].trim().substring(0, 5).equals("00924")||inspectCode[i].trim().substring(0, 5).equals("41026")
									||inspectCode[i].trim().substring(0, 5).equals("21672"))
									//부속검사인데 제외 코드 추가부분
							&& hosCode[i].trim().equals("30418") 
							&& inspectCode[i].trim().length() == 7 ) { // 단독

						data[34] = ""; // 문자결과
						
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						
						
						i = setResult30267(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						
						i--;
						data[36] = data[35].trim();
						
					}
					


									
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("24654")
							&& inspectCode[i].trim().substring(0, 5).equals("11101")) { // 단독
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
								data[39] = getReferenceValue(remark).replace('-', '~');
								data[21] = bdt[i]; // 검사완료일
							}
							if (inspectCode[i].trim().substring(5, 7).equals("03")) {
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
									data[25] = data[25] + "\n" + getUintCut(unit[i])[2]; // 참고치단위
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
								data[39] = data[39] + "\n" + getReferenceValue(remark).replace('-', '~');
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24654") && inspectCode[i].trim().length() == 7
							&& inspectCode[i].trim().substring(0, 5).equals("11301")) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("25265")
							&& inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("72018") 
									|| inspectCode[i].trim().substring(0, 5).equals("71259")|| inspectCode[i].trim().substring(0, 5).equals("71325")
									|| inspectCode[i].trim().substring(0, 5).equals("81000")
									|| inspectCode[i].trim().substring(0, 5).equals("72241")|| inspectCode[i].trim().substring(0, 5).equals("31001"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21) + getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21)
										+ getReferenceValueAge(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("25265")&& (inspectCode[i].trim().substring(0, 5).equals("00804"))) 
					{ 
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						data[35] =getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24516") && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("71252"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21) + getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21)
										+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24183") && inspectCode[i].trim().length() == 7) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("24446") || hosCode[i].trim().equals("19015") || hosCode[i]
									.trim().equals("12770")) && inspectCode[i].trim().length() == 7 && //
							(inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("00095") || //
									inspectCode[i].trim().substring(0, 5).equals("72018") || 	inspectCode[i].trim().substring(0, 5).equals("71251") || //
									inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("00009"))) { // 단독

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("24434")) && inspectCode[i].trim().length() == 7 && //
							(inspectCode[i].trim().substring(0, 5).equals("72059") || //
									inspectCode[i].trim().substring(0, 5).equals("72018") || 	inspectCode[i].trim().substring(0, 5).equals("71251") || //
									inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("00009"))) { // 단독

						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24434") && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("00405"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("01")) {
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
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(remark);

							// !
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34] + "\r\n" + data[36];

						// !
					}
					if (!isTxtRltB && (hosCode[i].trim().equals("24183") || hosCode[i].trim().equals("24080")) && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("00095"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24161") && inspectCode[i].trim().length() == 7) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}

					if (!isTxtRltB && hosCode[i].trim().equals("24051") && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("31001"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("24051") && inspectCode[i].trim().length() == 7) { // 단독
						// ! 박진영 내과
						data[35] = appendBlanks("검  사  명 ", 30) + "" + appendBlanks("결    과", 21) + " " + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "" + appendBlanks(result[i], 21) + ""
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "" + appendBlanks(result[i], 21) + ""
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim().replace('\r', ' ').replace('\n', ' ');

						// !
					}
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22401")
							&& (inspectCode[i].trim().substring(0, 5).equals("97655") || inspectCode[i].trim().substring(0, 5).equals("72080")
									|| inspectCode[i].trim().substring(0, 5).equals("00938") || inspectCode[i].trim().substring(0, 5).equals("00009")
									|| inspectCode[i].trim().substring(0, 5).equals("71259") 	|| inspectCode[i].trim().substring(0, 5).equals("41026") 
									|| inspectCode[i].trim().substring(0, 5).equals("00304") || inspectCode[i].trim().substring(0, 5).equals("71251")
									|| inspectCode[i].trim().substring(0, 5).equals("72185"))) { // 단독
						// ! 예산 삼성 병원
						data[35] = appendBlanks("검  사  명 ", 30) + "                " + appendBlanks("결    과", 21) + "                 " + "참    고    치";
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
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "@^" + appendBlanks(inspectName[i], 30) + "                " + appendBlanks(result[i], 21) + "                "
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
				
						data[36] = data[34];

						// !
					}
					if (!isTxtRltB && hosCode[i].trim().equals("22401") && (inspectCode[i].trim().substring(0, 5).equals("00301")||inspectCode[i].trim().substring(0, 5).equals("72185")
							||inspectCode[i].trim().substring(0, 5).equals("71251")||inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("21061"))) { // 단독
						// ! 예산 삼성 병원
						data[35] = appendBlanks("검  사  명 ", 30) + "                " + appendBlanks("결    과", 21) + "                  " + "참    고    치"; // 삼육의료원제외
						data[35] += getDivide() + "@^" + appendBlanks(inspectName[i], 30) + "                " + appendBlanks(result[i], 21) + "                 "
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "@^" + appendBlanks(inspectName[i], 30) +  "                "  + appendBlanks(result[i], 21) + "                 "
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					
					}
					
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && !hosCode[i].trim().equals("12640")
							&& isHBV(inspectCode[i].trim().substring(0, 5))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "관  련  약  제";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getResultHBV(inspectCode[i].trim());
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getResultHBV(inspectCode[i].trim());
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						if(hosCode[i].trim().equals("25814"))
						{
							String rmk_71245 =getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							if(rmk_71245.trim().length()>0)
							{
								data[36] = data[35].trim()+"\r\n\r\n"+get71245()+"\r\nRemark : \r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							}
							else
							{
								data[36] = data[35].trim()+"\r\n\r\n"+get71245();
							}
						} else
						{
							data[36] = data[35].trim();
						}

						// !
					}
					
					if (!isTxtRltB && hosCode[i].trim().equals("22401") && (inspectCode[i].trim().substring(0, 5).equals("97655"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "                " + appendBlanks("결    과", 21) + "                 " + "참    고    치";
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
						data[35] += getDivide() + "@^" + appendBlanks(inspectName[i], 30) + "                " + appendBlanks(result[i], 21) + "                "
								+ getReferenceValue(remark);
						data[36] = data[34];
					}
					if (!isTxtRltB && hosCode[i].trim().equals("14279") && isReferenceValue14279(inspectCode[i].trim().substring(0, 5))) { // 단독
						// ! 인천사랑병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
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
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[36] = data[34];
					}
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && !hosCode[i].trim().equals("12640") && !hosCode[i].trim().equals("24516") && !hosCode[i].trim().equals("29171")
							&& !hosCode[i].trim().equals("22015") && !hosCode[i].trim().equals("26082")
							&& (inspectCode[i].trim().substring(0, 5).equals("11052"))) { // 단독아님
						
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 희경의료재단 제	
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
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
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("24516")
							&& (inspectCode[i].trim().substring(0, 5).equals("11052"))) { // 단독아님
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치"; // 삼육의료원제외
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
							&& (hosCode[i].trim().equals("23697") || hosCode[i].trim().equals("25791") || hosCode[i].trim().equals("26454") || hosCode[i]
									.trim().equals("13725")|| hosCode[i].trim().equals("26800")|| hosCode[i].trim().equals("18535")) && inspectCode[i].trim().substring(0, 5).equals("00405")) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[34];
					}
					
				
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("24654")
							&& inspectCode[i].trim().substring(0, 5).equals("11103")) { // 단독
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
								data[39] = getReferenceValue(remark).replace('-', '~');
								data[21] = bdt[i]; // 검사완료일
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("13928"))
							&& (inspectCode[i].trim().substring(0, 5).equals("71251") || inspectCode[i].trim().substring(0, 5).equals("00095"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("01")) {
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
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(remark);

							// !
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[34];
					}

					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("24516"))
							&& (inspectCode[i].trim().substring(0, 5).equals("71251") || inspectCode[i].trim().substring(0, 5).equals("00095"))) {
						// ! 청아병원
						data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치"; // 단독
						curDate = rcvDate[i];
						isTxtRltB = true;
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("01")) {
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
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + appendBlanks(result[i], 21)
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
							&& (inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("11301") )) {
						// ! 박진영 내과
						data[35] = appendBlanks("검  사  명 ", 30) + "" + appendBlanks("결    과", 21) + " " + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "" + appendBlanks(result[i], 21) + ""
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i]; // 단독
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "" + appendBlanks(result[i], 21) + ""
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim().replace('\r', ' ').replace('\n', ' ');
					}
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("22221") || hosCode[i].trim().equals("25400"))
							&& (inspectCode[i].trim().substring(0, 5).equals("41026"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[34];
					}

					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("25956")
							&& (//inspectCode[i].trim().substring(0, 5).equals("11101")||
									inspectCode[i].trim().substring(0, 5).equals("00095")||
									inspectCode[i].trim().substring(0, 5).equals("00822")||
									inspectCode[i].trim().substring(0, 5).equals("11002")
									)) { // 단독
						
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 희경의료재단 제	
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
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

					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("29516") || hosCode[i].trim().equals("30300"))
							&& (inspectCode[i].trim().substring(0, 5).equals("11101"))) { // 단독
						
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 희경의료재단 제	
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
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

					
					
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5).equals("11101"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("03")) {
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
							&& (hosCode[i].trim().equals("29167")||hosCode[i].trim().equals("23697") || hosCode[i].trim().equals("25791") || hosCode[i].trim().equals("26454")
									|| hosCode[i].trim().equals("13725") || hosCode[i].trim().equals("18115")|| hosCode[i].trim().equals("26800")|| hosCode[i].trim().equals("18535"))
							&& (inspectCode[i].trim().substring(0, 5).equals("00095"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[34];
					}
					
					//두손병원 결과에 참고치 추가 요청
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("13725")) && (inspectCode[i].trim().substring(0, 5).equals("81370")||(inspectCode[i].trim().substring(0, 5).equals("64095")))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						data[39] = getReferenceValue(remark);
						System.out.println(data[39]);
						data[36] = data[34] + "\r\n" + data[39];
					}

					
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("23697") || hosCode[i].trim().equals("25791") || hosCode[i].trim().equals("26454") || hosCode[i]
									.trim().equals("13725")|| hosCode[i].trim().equals("26800")|| hosCode[i].trim().equals("18535")) && (inspectCode[i].trim().substring(0, 5).equals("11101"))) { // 단독
						isTxtRltB = true;
						if (inspectCode[i].trim().substring(5, 7).equals("02")) {
							continue;
						}
					}
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("23316")
							&& (inspectCode[i].trim().substring(0, 5).equals("00901"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("02")) {
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
					
					// 확인 필요 00095 검사 01 코드 일 시 참고치  및 리마크 표시
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("23316") || hosCode[i].trim().equals("23741"))
							&& (inspectCode[i].trim().substring(0, 5).equals("00095"))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[34];
					}
					
					// 마스트 검사
					if (!isTxtRltB && (isMAST_Two(inspectCode[i].trim().substring(0, 5)) || isMAST(inspectCode[i].trim().substring(0, 5)))&&//
					   (!hosCode[i].trim().equals("21954")	&& !hosCode[i].trim().equals("22015") && !hosCode[i].trim().equals("12640"))) { 
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
							curGcd = inspectCode[i].substring(0, 5);	
							int mastad =0;
							if (i+1 == cnt || i > cnt){
								break;
							}
							for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode	.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
								try {		
									   if(mastad==0)
				                        {
				                           vmast.addElement(getRPad(inspectName[i].trim(), 26) + getRPad(result[i++].trim().trim(), 6));
				                        }else
				                        {
				                        	 if(isMastDuplPrint(inspectCode[i].trim()))
												 i++;
					                           else
					                        	   vmast.addElement(getRPad(inspectName[i].trim(), 26) + getRPad(result[i++].trim().substring(0, 1), 6));
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
							if (hosCode[i].trim().equals("22250")) {
								data[35] = getResultMAST_Two(data[35].toString(), vmast) + getDivide();
							}else if(hosCode[i].trim().equals("29171")){
								data[35] = getResultMAST_Two(data[35].toString(), vmast) + getDivide() + "\r\n" +getMastRemark();//getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							}else {
								data[35] = getResultMAST_Two(data[35].toString(), vmast) + getDivide() + "\r\n" + getMastRemark();
							}
							data[36] = data[35].trim();
							i--;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("23316") || hosCode[i].trim().equals("23586"))) { // 단독
						isTxtRltB = true;
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
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
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31010")||//
								inspectCode[i].trim().equals("31012")||//
								(inspectCode[i].trim().equals("31059") && !hosCode[i].trim().equals("22023")) || inspectCode[i].trim().equals(
									"31079"))) { // 단독
						data[34] = ""; // 문자결과
						isTxtRltB = true;
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35].trim();
						data[39] = ""; // 참고치
					}
					
					// 확인 필요 71251 01 검사 표현 변경
					
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("20720") && !hosCode[i].trim().equals("25400")
							&& !hosCode[i].trim().equals("22221") && !hosCode[i].trim().equals("22015")&& !hosCode[i].trim().equals("22015")
							&& (inspectCode[i].trim().substring(0, 5).equals("71251") 
							|| inspectCode[i].trim().substring(0, 5).equals("71252"))) {
						curDate = rcvDate[i];
					
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
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
					if (!isTxtRltB && (hosCode[i].trim().equals("22250") && inspectCode[i].trim().substring(0, 5).equals("81388"))) {
						isTxtRltB = true;
						data[35] += getDivide() + getMTHFR();
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						data[36] = data[35].trim();
					}
					if (!isTxtRltB && (hosCode[i].trim().equals("22250") && inspectCode[i].trim().length() == 7 //
							&& inspectCode[i].trim().substring(0, 5).equals("11026") //
							|| inspectCode[i].trim().substring(0, 5).equals("00312") //
							|| inspectCode[i].trim().substring(0, 5).equals("00313") //
							|| inspectCode[i].trim().substring(0, 5).equals("00307") //
					|| inspectCode[i].trim().substring(0, 5).equals("00308"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
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
					if (!isTxtRltB && hosCode[i].trim().equals("27456") && inspectCode[i].trim().length() == 7 //
							&& (inspectCode[i].trim().substring(0, 5).equals("72185"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
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
					if (!isTxtRltB && hosCode[i].trim().equals("27251") && inspectCode[i].trim().length() == 7 //
							&& (inspectCode[i].trim().substring(0, 5).equals("72185"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
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
				
					if (!isTxtRltB && hosCode[i].trim().equals("12640")) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = result[i]; // 문자결과
						isTxtRltB = true;
						if (inspectCode[i].trim().substring(0, 5).equals("31001")) {
							data[36] = "";
							data[35] = "";
						} else {
							data[36] = data[35].trim();
						}
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						if (inspectCode[i].trim().substring(0, 5).equals("11052")) {
							remarkCode = "";
						}
						if (rmkCode[i].trim().length() > 0) {
							try {
								if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
										|| !kumdata[2].trim().equals(remarkCode)) {
									remarkCode = rmkCode[i].trim();
							
									data[38] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
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
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("10780") || //
							hosCode[i].trim().equals("10781") || //
							hosCode[i].trim().equals("10782") || //
							hosCode[i].trim().equals("10783") || //
							hosCode[i].trim().equals("10784") || //
							hosCode[i].trim().equals("10785"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
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
					if (!isTxtRltB && hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}

					if (!isTxtRltB && hosCode[i].trim().equals("12770")
							&& (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}

					if (!isTxtRltB
							&& (hosCode[i].trim().equals("12770") && inspectCode[i].trim().length() == 7)
							&& (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("00091")
									|| inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("00804"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
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
					
			
			
					if (!isTxtRltB && ((hosCode[i].trim().equals("27282")||hosCode[i].trim().equals("27693")) && inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals("71251")|| inspectCode[i].trim().substring(0, 5).equals("11301")
									||inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("31001")))) {
						data[35] = appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) ;
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) ;
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
		
					//  첫번째 부속값만
					// 이해안됨! 71251,71252,00752,22023,72059,72018 첫번째 부속값만 출력되도록
					//20161028 양태용 예스병원(안산) 71252 02번 노출 되도록 제외처리함
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("29659")&& !hosCode[i].trim().equals("24434")&& !hosCode[i].trim().equals("29167")	&& !hosCode[i].trim().equals("22256")&& !hosCode[i].trim().equals("22015")
							&& !hosCode[i].trim().equals("22250")&& !hosCode[i].trim().equals("14279")&& !hosCode[i].trim().equals("22221")
							&& !hosCode[i].trim().equals("25400")&& !hosCode[i].trim().equals("13707")&& !hosCode[i].trim().equals("22874")
							&& !hosCode[i].trim().equals("25814")&& !hosCode[i].trim().equals("23211")&& !hosCode[i].trim().equals("12657")
							&& !hosCode[i].trim().equals("25815")&& !hosCode[i].trim().equals("25184")&& !hosCode[i].trim().equals("12471")
							&& !hosCode[i].trim().equals("27282")&& !hosCode[i].trim().equals("27693")&& !hosCode[i].trim().equals("22401")
							&& !hosCode[i].trim().equals("26454")&& !hosCode[i].trim().equals("22721")&& !hosCode[i].trim().equals("29171")
							&& !hosCode[i].trim().equals("25560")&& !hosCode[i].trim().equals("28351")&& !hosCode[i].trim().equals("29005")
							&& ((inspectCode[i].trim().substring(0, 5).equals("71251") || inspectCode[i].trim().substring(0, 5).equals("71252")|| inspectCode[i].trim().substring(0, 5).equals("00752")) 
									|| (hosCode[i].trim().equals("22023") && (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))))) 
					{
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						System.out.println("1");
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						System.out.println("2");
						data[36] = data[34].trim();
					}
					
			
						System.out.println(rcvNo[i]+inspectCode[i]+"yty");
						
						
						if (!isTxtRltB
								&& hosCode[i].trim().equals("29005")
								&& inspectCode[i].trim().length() == 7
								&& (inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("71259")
									||inspectCode[i].trim().substring(0, 5).equals("31001")||inspectCode[i].trim().substring(0, 5).equals("21380"))) { // 단독
							
							data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(remark);
							data[34] = ""; // 문자결과
							data[39] = ""; // 참고치
							isTxtRltB = true;
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							i = setResult(cnt, rcvDate, rcvNo, inspectCode,
									inspectName, result, sex, age, lang, history,
									data, i, curDate, curNo);
							i--;
							data[36] = data[35].trim();

							// !
						}
					

					
					if (!isTxtRltB && (hosCode[i].trim().equals("22033") || hosCode[i].trim().equals("12770")) && inspectCode[i].trim().length() == 7) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
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
					if (!isTxtRltB && hosCode[i].trim().equals("22033") && (inspectCode[i].trim().equals("31019"))) {
						data[35] = result[i]; // 문장결과
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						isTxtRltB = true;
					}
					
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& !hosCode[i].trim().equals("24516")
							&& ((hosCode[i].trim().equals("19725") || hosCode[i].trim().equals("21118")) || //
									(hosCode[i].trim().equals("20974") && inspectCode[i].trim().substring(0, 5).equals("11026")) || //
									(hosCode[i].trim().equals("13931") && inspectCode[i].trim().substring(0, 5).equals("71251")) || //
									(hosCode[i].trim().equals("22262") && (inspectCode[i].trim().substring(0, 5).equals("11101") || inspectCode[i]
											.trim().substring(0, 5).equals("31001"))) || //
									(hosCode[i].trim().equals("22256") && !inspectCode[i].trim().substring(0, 5).equals("88007")
											&& !inspectCode[i].trim().substring(0, 5).equals("00091")
											&& !inspectCode[i].trim().substring(0, 5).equals("00095") && (inspectCode[i].trim().substring(0, 5)
											.equals("11101") || inspectCode[i].trim().substring(0, 5).equals("31001"))) || (hosCode[i].trim().equals(
									"13931") && inspectCode[i].trim().substring(0, 5).equals("71251")))) {
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
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("25906") && inspectCode[i].trim().substring(0, 5).equals("81469"))) {
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
							&& !hosCode[i].trim().equals("19725")
							&& (hosCode[i].trim().equals("13931") || hosCode[i].trim().equals("19725") || hosCode[i].trim().equals("13406") || hosCode[i].trim().equals("12770"))
							&& (inspectCode[i].trim().substring(0, 5).equals("00091") || inspectCode[i].trim().substring(0, 5).equals("00095")
									|| inspectCode[i].trim().substring(0, 5).equals("00752") || inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i]
									.trim().substring(0, 5).equals("72018"))) {

						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setBusock01(cnt, rcvDate, rcvNo, inspectCode,
								result, sex, lang, history, bdt, unit, data,
								remark, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}
					// 확인필요
					if (!isTxtRltB && hosCode[i].trim().equals("12770") && (inspectCode[i].trim().substring(0, 5).equals("11101"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!inspectCode[i].trim().substring(5, 7).equals("00")) {
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
					if (!isTxtRltB && inspectCode[i].trim().length() == 7 && !hosCode[i].trim().equals("22256") && !hosCode[i].trim().equals("22015")
							&& (hosCode[i].trim().equals("22262") && !inspectCode[i].trim().equals("11101"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						// data[36] = data[35].trim();
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
					//세강병원 병원코드 변경 28961 -> 30353 처리완료 20170116
					
					if (!isTxtRltB
							&& inspectCode[i].trim().length() == 7
							&& (hosCode[i].trim().equals("21505")|| hosCode[i].trim().equals("29034") || hosCode[i].trim().equals("26719") || hosCode[i].trim().equals("23661")||hosCode[i].trim().equals("27993")
									|| hosCode[i].trim().equals("28272") || hosCode[i].trim().equals("28312") || hosCode[i].trim().equals("30353")|| hosCode[i].trim().equals("28351"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("31001")//inspectCode[i].trim().substring(0, 5).equals("00405") ||
									|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("11101")
									|| inspectCode[i].trim().substring(0, 5).equals("71246")|| inspectCode[i].trim().substring(0, 5).equals("21065")
									|| inspectCode[i].trim().substring(0, 5).equals("31003")|| inspectCode[i].trim().substring(0, 5).equals("31005")
									|| inspectCode[i].trim().substring(0, 5).equals("00309")|| inspectCode[i].trim().substring(0, 5).equals("72182")
									|| inspectCode[i].trim().substring(0, 5).equals("72194")|| inspectCode[i].trim().substring(0, 5).equals("21380"))) { // 단독아님 26719 병원 때문에 20170210 추가함 21380검사~ 양태용
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
							&& (hosCode[i].trim().equals(""))
							&& ( inspectCode[i].trim().substring(0, 5).equals("31001")//inspectCode[i].trim().substring(0, 5).equals("00405") ||
									|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("11101")
									|| inspectCode[i].trim().substring(0, 5).equals("71246")|| inspectCode[i].trim().substring(0, 5).equals("21065")
									|| inspectCode[i].trim().substring(0, 5).equals("31003")|| inspectCode[i].trim().substring(0, 5).equals("31005")
									|| inspectCode[i].trim().substring(0, 5).equals("00309")|| inspectCode[i].trim().substring(0, 5).equals("72194")
									)) { // 단독아님
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
							&& (hosCode[i].trim().equals("23913") && (inspectCode[i].trim().substring(0, 5).equals("00095")
									|| inspectCode[i].trim().substring(0, 5).equals("11101") || inspectCode[i].trim().substring(0, 5).equals("31001")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB
							&& ((hosCode[i].trim().equals("25659") || hosCode[i].trim().equals("25694")) && (inspectCode[i].trim().substring(0, 5)
									.equals("00802")
									|| inspectCode[i].trim().substring(0, 5).equals("11002")
									|| inspectCode[i].trim().substring(0, 5).equals("00095")
									|| inspectCode[i].trim().substring(0, 5).equals("11301") || inspectCode[i].trim().substring(0, 5).equals("00806")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}

					if (!isTxtRltB
							&& (hosCode[i].trim().equals("17178") && inspectCode[i].trim().length() == 7 
							&& !(inspectCode[i].trim().substring(0, 5).equals("00095")
									|| inspectCode[i].trim().substring(0, 5).equals("00312")
									|| inspectCode[i].trim().substring(0, 5).equals("00799")
									|| inspectCode[i].trim().substring(0, 5).equals("00802")
									|| inspectCode[i].trim().substring(0, 5).equals("00819")
									|| inspectCode[i].trim().substring(0, 5).equals("00822")
									|| inspectCode[i].trim().substring(0, 5).equals("11002")
									|| inspectCode[i].trim().substring(0, 5).equals("11101") 
									|| inspectCode[i].trim().substring(0, 5).equals("11301")))) {
									// 권강진정형외과 부속은 모두 문장이고 단문으로 나올것만 조건에 추가함
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
				
					if (!isTxtRltB
							&& hosCode[i].trim().equals("25815")
							&& (inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018")
									|| inspectCode[i].trim().substring(0, 5).equals("00938")|| inspectCode[i].trim().substring(0, 5).equals("72185"))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = set문장(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, lang, history, data,
								i, curDate, curNo);
					}

					if (!isTxtRltB
							&& hosCode[i].trim().equals("15710")
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
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("29167")
							&& is29167(inspectCode, i)) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = set문장(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, lang, history, data,
								i, curDate, curNo);
					}

					
					if (!isTxtRltB && hosCode[i].trim().equals("25815") && (inspectCode[i].trim().equals("7125203")||inspectCode[i].trim().equals("7129706"))) { // 단독
						data[35] = "결과지참조";
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						data[36] = data[35].trim();
					}
					
					//별지참조
					if (hosCode[i].trim().equals("13707")
							&& (inspectCode[i].trim().substring(0, 5).equals("00673")||//
								inspectCode[i].trim().substring(0, 5).equals("00683")||//
								inspectCode[i].trim().substring(0, 5).equals("00684")||
								inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")||
								inspectCode[i].trim().substring(0, 5).equals("00674")||//
								inspectCode[i].trim().substring(0, 5).equals("11052"))) {
						data[35] = "별지참조";
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB && hosCode[i].trim().equals("25184") && inspectCode[i].trim().substring(0, 5).equals("00804")) {
						data[35] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();
						isTxtRltB = true;
					}

				
					if (!isTxtRltB && (hosCode[i].trim().equals("20713") && (inspectCode[i].trim().substring(0, 5).equals("72185")||inspectCode[i].trim().substring(0, 5).equals("71137")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && (( hosCode[i].trim().equals("26852")) && (inspectCode[i].trim().substring(0, 5).equals("72035")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();

						// !
					}
					if (!isTxtRltB && (hosCode[i].trim().equals("26852") && (inspectCode[i].trim().substring(0, 5).equals("72185")))) { // 단독
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						data[34] ="Negative";
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("") && result[i].indexOf("+")>=0) {
								data[34] ="Positive";
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						// !
					}
					if (!isTxtRltB && (hosCode[i].trim().equals("27233") && (inspectCode[i].trim().substring(0, 5).equals("31001")))) { // 단독
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						i--;
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB && (hosCode[i].trim().equals("29825") && (inspectCode[i].trim().substring(0, 5).equals("00911")))) { // 단독
//						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t";
//								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (!result[i].toString().trim().equals("")) {
								data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t";
								//		+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							}
							if (++i == cnt || i > cnt)
								break;
						}
						
						i--;
						data[36] = data[35].trim();
					}
					//희경의료재단 단문->장문
					if (!isTxtRltB && (hosCode[i].trim().equals("29171") && (inspectCode[i].trim().substring(0, 5).equals("31001") 
							|| inspectCode[i].trim().substring(0, 5).equals("71297") 
							|| inspectCode[i].trim().substring(0, 5).equals("72185")
							|| inspectCode[i].trim().substring(0, 5).equals("11052")
							|| inspectCode[i].trim().substring(0, 5).equals("00804")
							|| inspectCode[i].trim().substring(0, 5).equals("72241")
							|| inspectCode[i].trim().substring(0, 5).equals("00901")
							|| inspectCode[i].trim().substring(0, 5).equals("00911")
							|| inspectCode[i].trim().substring(0, 5).equals("31083")
							|| inspectCode[i].trim().substring(0, 5).equals("00323")
							|| inspectCode[i].trim().substring(0, 5).equals("00309")
							))) { // 단독
						//data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						//data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"	+ getReferenceValue(remark);
						
						//양태용 20161011 검체 추가 요청
						try {
							data[35] = "검체 :    "+cutHrcvDateNumber3(cns[i])[0];	
						} catch (Exception e) {	}
											
						
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "    " + appendBlanks(result[i], 21) + "    "	+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
								
//						i = setResult(cnt, rcvDate, rcvNo, inspectCode,
//								inspectName, result, sex, age, lang, history,
//								data, i, curDate, curNo);
						
						
						i = setResultTabToBlank(cnt, rcvDate, rcvNo, inspectCode,
								inspectName, result, sex, age, lang, history,
								data, i, curDate, curNo);
						
						
						i--;
						data[36] = data[35].trim();
						
						//그람 스테인 검사에 한하여 리마크를 결과 아래 표기 한다.20160307 김영상 과장 요청  
						try {
							if( inspectCode[i].trim().substring(0, 5).equals("31001") ||inspectCode[i].trim().substring(0, 5).equals("00804")||inspectCode[i].trim().substring(0, 5).equals("72241")){
								data[35] =data[35] +"\r\n"+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							}
							
						} catch (Exception e) {	}
						
						
					}

					//희경의료재단 31059 검사 결과내용을 Positive, Negative 만 입력되되록
					if (!isTxtRltB && hosCode[i].trim().equals("29171") && inspectCode[i].trim().equals("31059")) {
						if(data[34].indexOf("Positive")>=0) data[34] = "Positive";
						else if(data[34].indexOf("Negative")>=0) data[34] = "Negative";
					}
					
					if (!isTxtRltB && (hosCode[i].trim().equals("17989") && (inspectCode[i].trim().substring(0, 5).equals("21598") ||inspectCode[i].trim().substring(0, 5).equals("21416") 
							||inspectCode[i].trim().substring(0, 5).equals("21445")||inspectCode[i].trim().substring(0, 5).equals("21417")||inspectCode[i].trim().substring(0, 5).equals("00509")))) { // 단독
						//환자당 마지막 검사에만 출력되도록 
						if ((i+1) == cnt || i > cnt)
						{
							data[35] =getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+ "\r\n검사실명 : (재)씨젠의료재단\r\n"
									    +"주     소 : 서울 특별시 성동구 천호대로320(용답동)\r\n"
									    +"전 문 의 : 이선화M.D. (제 416호) \r\n"
									    +"검사기관 : 11370319";
						}
						else if(!(data[31].equals(rcvDate[i+1].trim()) &&data[32].equals(rcvNo[i+1].trim())))
						{
							data[35] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+ "\r\n검사실명 : (재)씨젠의료재단\r\n"
							    +"주     소 : 서울 특별시 성동구 천호대로320(용답동)\r\n"
							    +"전 문 의 : 이선화M.D. (제 416호) \r\n"
							    +"검사기관 : 11370319";
						}
					}
					// ! 여기서 부터
					// 문장형--------------------------------------------------------------------------------------------------------------------------------------------------------
				} else {
					boolean isTxtRltC = false;
					
					
					if (!isTxtRltC && hosCode[i].trim().equals("29171")) {
						isTxtRltC = true;
						if (inspectCode[i].trim().equals("31010")||inspectCode[i].trim().equals("00233")) {
							
							data[34] = ""; // 문자결과
							
							try {
								data[35] = "검체 :    "+cutHrcvDateNumber3(cns[i])[0];	
							} catch (Exception e) {	}
							
							
							
								
								
							data[35] = data[35]+getTextResultValue_NGY(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]).replaceAll(
									"<Non-Gyn Cytology                   >\r\nSputum cytology\r\n", ""); // 문장결과
							
							data[35] = data[35].replaceAll("최　종　결　과　보　고", "");

							data[36] = data[35].trim();
							
							data[39] = ""; // 참고치
							
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						}
					}
					if (!isTxtRltC && hosCode[i].trim().equals("24434")) {
						isTxtRltC = true;
						if (inspectCode[i].trim().equals("61007")) {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue_NGY(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]).replaceAll(	"<Non-Gyn Cytology                   >\r\nSputum cytology\r\n", ""); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						}
					}
					if (!isTxtRltC && hosCode[i].trim().equals("24446")) {
						isTxtRltC = true;
						if (inspectCode[i].trim().equals("31012")) {
							continue;
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						}
					}
					if (!isTxtRltC && (hosCode[i].trim().equals("20607") && (inspectCode[i].trim().equals("00904")))) { // 단독
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[34] = "";
						data[39] = ""; // 참고치
						isTxtRltC = true;
						data[36] = data[35].trim();
					}

					
					//컬쳐&센시 합치기 
					/**
					 * 20160715
					 * 센시 31012 31064 31011
					 * 컬쳐 31010 31014 31060
					 * 합치기 - 2개 결과 나오는 순서가 틀릴수 있음
					 */
					if (!isTxtRltC && ((hosCode[i].trim().equals("29659")) && 
							(
							inspectCode[i].trim().equals("31012")||inspectCode[i].trim().equals("31064")||inspectCode[i].trim().equals("31010")
							||inspectCode[i].trim().equals("31014")||inspectCode[i].trim().equals("31060")||inspectCode[i].trim().equals("31011")
							)
						)) {
						//마지막 아이가 되어 +1 익셉션이 발생할 수 있으니 캣취해서 결과를 넣어준다.
						try {
							if ((
								inspectCode[i + 1].substring(0, 5).equals("31012")||inspectCode[i + 1].substring(0, 5).equals("31064")||inspectCode[i + 1].substring(0, 5).equals("31011")
								||inspectCode[i + 1].substring(0, 5).equals("31010")||inspectCode[i + 1].substring(0, 5).equals("31014")
								) && rcvNo[i].equals(rcvNo[i + 1])&& rcvDate[i].equals(rcvDate[i + 1])) {
								
								if(inspectCode[i].trim().equals("31010")||inspectCode[i].trim().equals("31014")||inspectCode[i].trim().equals("31011")){
									data[35] = data[35]+getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
									data[36] = data[35].trim();
									data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
									data[36] = data[35].trim();	
								}else{
									data[35] = data[35]+getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i+1]);
									data[36] = data[35].trim();
									data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
									data[36] = data[35].trim();	
								}
								
								i++;
								// culture_flag = true;
							} 
							   // 20160831 양태용 31060 예외 처리 결과 순서 뒤바꿈 
							else if ((
									inspectCode[i + 1].substring(0, 5).equals("31060")
									) && rcvNo[i].equals(rcvNo[i + 1])&& rcvDate[i].equals(rcvDate[i + 1])) {
									
									if(inspectCode[i].trim().equals("31060")){
										data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
										data[36] = data[35].trim();	
										data[35] = data[35]+getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
										data[36] = data[35].trim();

									}else{
										data[35] = data[35]+getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i+1]);
										data[36] = data[35].trim();
										data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
										data[36] = data[35].trim();	
									}
									i++;
									// culture_flag = true;
								} 
						} catch (Exception e) {
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[36] = data[35].trim();
						}
						
						if("".equals(data[36])||"".equals(data[35])){
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[36] = data[35].trim();
						}
						
						data[35] = data[35] + "\r\n 보고일자  : " + Common.change_day1(data[21]);
						data[36] = data[35].trim();
						
						
						isTxtRltC = true;
					}

					
					
					
					
					if (!isTxtRltC && hosCode[i].trim().equals("27126")) 
					{
						isTxtRltC = true;
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35].trim();
						data[39] = ""; // 참고치
						data[9] = "1"; //현대요양병원 일련번호 무조건1
						data[8] = "Tissue"; //현대요양병원 검체명
					}
					if (!isTxtRltC
							&& hosCode[i].trim().equals("22033")
							&& (inspectCode[i].trim().equals("31010") || inspectCode[i].trim().equals("31012") || inspectCode[i].trim().equals(
									"31014"))) {
						isTxtRltC = true;
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
						data[36] = data[35].trim();
						data[39] = ""; // 참고치
					}
					
					if (!isTxtRltC && hosCode[i].trim().equals("23913") && (inspectCode[i].trim().equals("31010"))) {
						isTxtRltC = true;
						data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[36] = data[35].trim();
						try {
							if (inspectCode[i + 1].substring(0, 5).equals("31011") && rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
								data[36] = data[35].trim();
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
					if (!isTxtRltC && hosCode[i].trim().equals("26410") && (inspectCode[i].trim().substring(0, 5).equals("31010"))) { // 단독
						isTxtRltC = true;
						data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[36] = data[35].trim();
						try {
							if (inspectCode[i + 1].substring(0, 5).equals("31012") && rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {
								data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
								data[36] = data[35].trim();
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

					if (!isTxtRltC && hosCode[i].trim().equals("25184") && (inspectCode[i].trim().substring(0, 5).equals("31010"))) { // 단독
						isTxtRltC = true;
						data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[36] = data[35].trim();
						try {
							if (inspectCode[i + 1].substring(0, 5).equals("31014") && rcvNo[i].equals(rcvNo[i + 1])
									&& rcvDate[i].equals(rcvDate[i + 1])) {

								data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]) + "\r\n" +data[35] ;
								data[36] = data[35].trim();
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
					if (!isTxtRltC && hosCode[i].trim().equals("22256")) {
						isTxtRltC = true;
						if ((inspectCode[i].trim().equals("31022") || inspectCode[i].trim().equals("31079"))
								&& !getCultureSensi(rcvDate[i], rcvNo[i]).trim().equals("")) {
							data[34] = ""; // 문자결과
							data[35] = getReamrkValue99(hosCode[i], rcvDate[i], rcvNo[i], "MB8"); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치

							if (data[35].trim().equals("")) {
								data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과 길게
								data[36] = data[35].trim();
							}
						} else if (inspectCode[i].trim().equals("31079")) {
							data[34] = ""; // 문자결과
							data[35] = getReamrkValue99(hosCode[i], rcvDate[i], rcvNo[i], "MB8"); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치

							if (data[35].trim().equals("")) {
								data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과 길게
								data[36] = data[35].trim();
							}
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue2(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과 길게
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						}
					} else {
						if (!isTxtRltC) {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						}
					}
					

				}

				
				// ------------------------------------------------------------------------
				// 이건 꼭 확인
				
				
				
				if (hosCode[i].trim().equals("12657")
						&& (inspectCode[i].trim().substring(0, 5).indexOf("0030") == 0 || inspectCode[i].trim().substring(0, 5).indexOf("0031") == 0
								|| inspectCode[i].trim().substring(0, 5).equals("00320") || inspectCode[i].trim().substring(0, 5).equals("00321")
								|| inspectCode[i].trim().substring(0, 5).equals("00322") || inspectCode[i].trim().substring(0, 5).equals("00323")
								|| inspectCode[i].trim().substring(0, 5).equals("00324") || inspectCode[i].trim().substring(0, 5).equals("31001")
								|| inspectCode[i].trim().substring(0, 5).equals("31010") || inspectCode[i].trim().substring(0, 5).equals("31012")
								|| inspectCode[i].trim().substring(0, 5).equals("71001") || inspectCode[i].trim().substring(0, 5).equals("11050")
								|| inspectCode[i].trim().substring(0, 5).equals("71002")
								|| inspectCode[i].trim().substring(0, 5).equals("71003")
								|| inspectCode[i].trim().substring(0, 5).equals("71004")
								|| inspectCode[i].trim().substring(0, 5).equals("71005")
								|| inspectCode[i].trim().substring(0, 5).equals("71006")
								|| inspectCode[i].trim().substring(0, 5).equals("72008")
								|| inspectCode[i].trim().substring(0, 5).equals("00334")
								|| inspectCode[i].trim().substring(0, 5).equals("81441")
								|| inspectCode[i].trim().substring(0, 5).equals("71245")
								|| inspectCode[i].trim().substring(0, 5).equals("71325"))) { // 단독
					
					data[34] = ""; // 문자결과
					
					data[39] = ""; // 참고치
					data[38] = ""; // 리마크
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					
					
					
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
						if (!result[i].toString().trim().equals("")) {
							data[35] = "별지보고";
						}
						if (++i == cnt || i > cnt)
							break;
					}
					i--;
					data[36] = data[35].trim();
					data[38] = ""; // 리마크
					
					if(inspectCode[i].trim().substring(0, 5).equals("71245")
							||inspectCode[i].trim().substring(0, 5).equals("71001")
							|| inspectCode[i].trim().substring(0, 5).equals("71002")
							|| inspectCode[i].trim().substring(0, 5).equals("71003")
							|| inspectCode[i].trim().substring(0, 5).equals("71004")
							|| inspectCode[i].trim().substring(0, 5).equals("71005")
							|| inspectCode[i].trim().substring(0, 5).equals("71006")
							||inspectCode[i].trim().substring(0, 5).equals("72008")
							||inspectCode[i].trim().substring(0, 5).equals("81441")
							||inspectCode[i].trim().substring(0, 5).equals("00323")
							||inspectCode[i].trim().substring(0, 5).equals("00334")
							|| inspectCode[i].trim().substring(0, 5).equals("73027")
							|| inspectCode[i].trim().substring(0, 5).equals("11050")
							){
						data[36] = "별지보고";
						data[35] = "별지보고";
					}
					
					if(Integer.parseInt(inspectCode[i].trim().substring(0, 5))>300 &&Integer.parseInt(inspectCode[i].trim().substring(0, 5))<325){
						data[36] = "별지보고";
						data[35] = "별지보고";
					}
					// !
				}
				
				// 분당재생병원 건너뛰기
//				if (inspectCode[i].trim().length() == 7
//						&& (hosCode[i].trim().equals("12657") && 
//						(
//						inspectCode[i].trim().equals("7129700")||
//						inspectCode[i].trim().equals("7129800")||
//						inspectCode[i].trim().equals("7128900")||
//						
//						inspectCode[i].trim().equals("7129000")||
//						inspectCode[i].trim().equals("7128200")||
//						inspectCode[i].trim().equals("7128300")
//								))) { // 단독
//					data[36] = "아래 결과 참조";
//					data[35] = "아래 결과 참조";
//				}
				
				// 12657 중간결과 보고 출력 안되게 
				
				if ((hosCode[i].trim().equals("12657")) && (inspectCode[i].trim().equals("31016"))) {
					if(data[36].indexOf("중　간　결　과　보　고")>=0)
					{
						continue;
					}
					
				}
				
	
				
				if(inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("12657") && inspectCode[i].trim().substring(5, 7).equals("00")){
					data[36] = "아래 결과 참조";
					data[35] = "아래 결과 참조";
				}
				
				if(hosCode[i].trim().equals("12657") && (inspectCode[i].trim().equals("81399") || (inspectCode[i].trim().equals("71325")))){
					data[36] = "별지 보고";
				}
				
				if (hosCode[i].trim().equals("22256") && !data[35].trim().equals("")) {
					data[35] += "\r\n\r\n검체번호: " + data[4] + " [" + data[31] + "-" + data[32] + "]";
					data[36] = data[35].trim();
				}

				// 대전 산재병원 재검여부 처리 
				
				if (hosCode[i].trim().equals("25815")) {
					data[1] = "1";
					if (data[38].indexOf("재검") >= 0) {
						data[1] = "3";
					}
				}
				// 대전 산재병원 조직인경우 "결과지 참조" 로 나오도록
				if (hosCode[i].trim().equals("25815")
						&& (inspectCode[i].trim().substring(0, 5).equals("51023") || inspectCode[i].trim().substring(0, 2).equals("52"))) {
					data[35] = "결과지참조";
					data[36] = data[35].trim();
					data[38] = "";
					data[39] = "";
				}
				data[37] = highLow[i]; // 결과상태

				if (inspectCode[i].trim().substring(0, 5).equals("11052")) {
					remarkCode = "";
				}
				
				
				
				
				
				
				
				
				
				
				
				
//리마크 한줄로 처리 완료				
				if (rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
								|| 
								//!kumdata[2].trim().equals(remarkCode)
								!kumdata[2].trim().equals(rmkCode[i].trim())
								) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("11052")) {
								data[35] = data[35] + getDivide() + "\r\n" + "\r\n" + getDivide()
										+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]).replace("\r\n", "");
								data[36] = data[35].trim();
							} else {
								data[38] =  getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]).replace("\r\n", "");//리마크 한줄로 엔터값 공백으로 처리
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
				if (hosCode[i].trim().equals("12640") && rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[38] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							// }
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				}

				//  그밖에 특이사항 개발 작업!-------------------------------------------
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
				if(hosCode[i].trim().equals("14279") &&inspectCode[i].trim().substring(0, 5).equals("71325"))
				{
					if(data[34].toString().indexOf("Positive")>=0){
						
						data[34] = "별지참조";
						
					}
					else{
						data[34] = "Not detected";
					}
					 
				}
				if (!isTxtRltA && hosCode[i].trim().equals("22089") || hosCode[i].trim().equals("24516") || hosCode[i].trim().equals("17654")) {
					data[35] = data[35].replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
					data[36] = data[36].replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
					isTxtRltA = true;
				}
				if (!isTxtRltA &&(hosCode[i].trim().equals("19391")||hosCode[i].trim().equals("24516") || hosCode[i].trim().equals("17654"))) {
					data[35] = data[35].replace('\r', '\n').replace('\t', ' ');
					data[36] = data[36].replace('\r', '\n').replace('\t', ' ');
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
				// 일산호수병원 장문결과상단에 접수일자, 보고일자 추가
				if (!isTxtRltA && (hosCode[i].trim().equals("23913"))) {
					if (data[35].trim().length() > 0) {
						data[35] = "접수일자 " + Common.change_day1(data[31]) + "\r\n" + "보고일자 " + Common.change_day1(data[21]) + "\r\n" + data[35];
						isTxtRltA = true;
					}
				}
				
				// 국모닝내과 21191(ANA)단문 리마크 추가  장문에 찍기
				if ( hosCode[i].trim().equals("30120") && inspectCode[i].trim().equals("21191") ) {
						
						data[35] = data[34] +"\r\n[Remark]\r\n"+data[38];
						data[34] = "";
			
		
				}

				// 성심힐요양병원 부속의 00번 빼기,
				if (hosCode[i].trim().equals("25839")) {
					data[34] = data[34].replace("**", ""); // 부속의 00번 빼기
					data[39] = data[39].replaceAll(data[25].toString(), ""); // 참고치 단위빼기
					data[35] = ""; // 장문결과빼기
					data[37] = data[37].replace(".", " "); // 판정에 . 빼기
					String temp_arr[] = data[39].split("\r\n");
					data[39] = temp_arr[0].toString(); // 참고치 첫번째줄만
				}
				
				
				// 성심힐요양병원 부속의 00번 빼기,
				if (hosCode[i].trim().equals("29598")) {
					data[37] = data[37].replace(".", " "); // 판정에 . 빼기
				}
				
				
				
				if (hosCode[i].trim().equals("28930")) {
					data[37] = data[37].replace(".", " "); // 판정에 . 빼기
				}
				
				// 정병우내과 외래구분
				if (hosCode[i].trim().equals("12883")) {
					data[2] = "외래";
				}
				// ! 참고치
				try {
					data[39] = data[39].trim();
				} catch (Exception yxx) {
				}
				if (hosCode[i].trim().equals("25815") && (inspectCode[i].trim().equals("7125203"))) { // 단독
					data[38] = "";
				}
				if (hosCode[i].trim().equals("25815")
						&& (inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018"))) { // 단독
					data[36] = data[36] + "\r\n\r\n" + data[38];
					data[35] = data[36];
				}
				if (hosCode[i].trim().equals("22401")) { // 단독
					data[36] = data[36].replaceAll("\t", "         ");
					data[35] = data[36];
				}
				//21세기 라파병원 문장결과에 리마크 내용 추가 되도록 수정
				if ( hosCode[i].trim().equals("27233") && data[38].trim().length()>0)
				{
					data[35] = data[35]+"\r\n[Remark]\r\n"+data[38];
				}

				
				//울산미즈병원 24080 리마크 장문결과에 나오도록 추가
				if ( ( hosCode[i].trim().equals("24080") &&inspectCode[i].trim().equals("72211") ) || (hosCode[i].trim().equals("27233") && data[38].trim().length()>0))
				{
					data[35] = data[35]+"\r\n"+"\r\n"+data[38];
				}
				
				//20161028 예쓰병원 처방번호 구분자 없어지는 문제 발생 처리 양태용
				
				if(hosCode[i].trim().equals("29034") || hosCode[i].trim().equals("28351")){
					try{
					String[] temp =cutHrcvDateNumber(cns[i]);
					
					data[5] =temp[0]+"^"+temp[1]+"^"+temp[2]+"^"; 
					}
					catch(Exception e){
						data[5]="";
					} 
					
				}
				
				if ((hosCode[i].trim().equals("13150")) && (inspectCode[i].trim().equals("31019")||inspectCode[i].trim().equals("31077")||inspectCode[i].trim().equals("31014"))) {
					if(data[35].indexOf("중　간　결　과　보　고")>=0)
					{
						continue;
					}
					
				}
				
				//의료법인명지병원 처방코드 삭제 요청
				if(hosCode[i].trim().equals("22721") &&( inspectCode[i].trim().substring(0, 5).equals("81469")||inspectCode[i].trim().substring(0, 5).equals("81702"))){
					data[6] = "";
				}
				
				
				
				
				if ( hosCode[i].trim().equals("28952"))//hosCode[i].trim().equals("30373")) 
				{
					String[] strTemp =  specNo[i].split("_");
					if(strTemp.length>1){
						data[4] = strTemp[0];//검체번호
						data[9] = strTemp[1];//일련번호
					}else{
						data[9] = "0"; //현대요양병원 일련번호 무조건1	
					}
				}
				
				if ( hosCode[i].trim().equals("29567")||hosCode[i].trim().equals("29568")) 
				{
					data[4] = data[22]+data[4];//검체번호
				}
				
				if ( hosCode[i].trim().equals("29567")&&inspectCode[i].trim().equals("31059")) 
				{
					data[35]=data[34];
					data[34]="";
					//data[4] = data[22]+data[4];//검체번호
				}
				
				if ( hosCode[i].trim().equals("29171")&& (inspectCode[i].trim().equals("21295") || inspectCode[i].trim().equals("72241"))) 
				{
					data[35] ="참고치 : "+data[39] +"\r\n\r\nRemark \r\n"+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
					//data[4] = data[22]+data[4];//검체번호
				}
				
				if ( hosCode[i].trim().equals("30")&& (inspectCode[i].trim().equals("21295") || inspectCode[i].trim().equals("72241"))) 
				{
					data[35] ="참고치 : "+data[39] +"\r\n\r\nRemark \r\n"+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
					//data[4] = data[22]+data[4];//검체번호
				}
				
				if(hosCode[i].trim().equals("22721") && !"".equals(data[38].trim()) ){
						data[38] = data[38].replaceAll("\r\n", " ");
					
				}
				
				
				if(hosCode[i].trim().equals("29659") && !"".equals(data[34].trim()) ){
					data[35] = "결과 : "+data[34];
					if(!"".equals(data[39].trim())){
						data[35] = data[35]+"\r\n\r\n참고치 : "+data[39];
					}
					
				}
						

				
//				if(hosCode[i].trim().equals("29659") && 
//						 (inspectCode[i].trim().substring(0, 5).equals("31010")||inspectCode[i].trim().substring(0, 5).equals("31012")||inspectCode[i].trim().substring(0, 5).equals("31011"))
//						&&(data[35].indexOf("균　명")>-1)	){
//					continue;
//				}
				
				// YTY (마석기독재활요양병원) 판정에 => . 일 경우  null로 변경 20160824
				if(hosCode[i].trim().equals("29502")){
					data[37] = highLow[i].replace(".", "");;
				}
				// YTY (서율요양병원) 판정에 => . 일 경우  null로 변경 20160824
				if(hosCode[i].trim().equals("30028")){
					data[37] = highLow[i].replace(".", "");;
				}	
				
				// 처방일자 변경 20170104 든든한병원 한사랑병원 처방일자 변경  20170116 30353세강병원 처리 20170118 
				if (hosCode[i].trim().equals("28833")||hosCode[i].trim().equals("29005")||hosCode[i].trim().equals("30099")
						||hosCode[i].trim().equals("30353")) {
					try {
						data[22] = cutHrcvDateNumber2(cns[i])[0]; // 처방일자
						data[5] = data[22]; // 처방일자
					} catch (Exception eee) {
						data[22] = ""; // 처방일자
					}
				}
				
				//제주대병원(29825)
				if ( hosCode[i].trim().equals("29825")) 
				{
						data[30] = "SEGEN";//씨젠
				}

				
				// !
				if (!debug) {
					int lens = getExcelFieldNameArray().length;
					
					try {
						for (k = 0; k < lens; k++) {
							label = new jxl.write.Label(k, row, data[Integer.parseInt(getExcelFieldArray()[k].toString())]);
							wbresult.addCell(label);
						}
					} catch (Exception xx) {
						//xx.printStackTrace();
					}
					
				}

				row++;
			}

//			try {//여성아이병원 28930 헤더부분이 데이터 베이스랑 연동 키가 되어 강제로 변경 작업 함
//				//정보 수정작업
//				if(hosCode[0].trim().equals("28930")){
//					//wbresult.removeRow(0);
//					String ArraryResult[] = (new String[] { "기관구분", "검체번호", "수신자명", "성별", "나이", "차트번호", "접수일자", "접수번호", "검사코드", "병원검사코드", "검사명", "문자결과", "문장결과",
//							"H/L", "Remark", "참고치", "주민등록번호", "처방번호" });
//					
//					for (int j = 0; j < ArraryResult.length; j++) {
//						label = new jxl.write.Label(j, 1, ArraryResult[j]);
//						wbresult.addCell(label);
//					}
//				}
//				
//			} catch (Exception e) {
//			}
			
			
			if (cnt == 400 || isNext) {
				setParameters(new String[] { hosCode[cnt - 1], rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1], seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception _ex) {
			_ex.printStackTrace();
			setParameters(null);
		}
	}
 
	private int setBusock01(int cnt, String[] rcvDate, String[] rcvNo,
			String[] inspectCode, String[] result, String[] sex, String[] lang,
			String[] history, String[] bdt, String[] unit, String[] data,
			String[] remark, int i, String curDate, String curNo) {
		System.out.println("test");
		for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5);
				thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) 
		{
			
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
		return i;
	}

	private boolean continueHos22721_1(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("22721")&&(inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125203")
				||inspectCode[i].trim().equals("7125900") || inspectCode[i].trim().equals("7224100")	||inspectCode[i].trim().equals("7224101"));
	}

	private boolean continueHos13952(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("13952")&&(inspectCode[i].trim().equals("4102600") || inspectCode[i].trim().equals("4102602")||inspectCode[i].trim().equals("4102603"));
	}

	private boolean continueHos29112(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("29112")||hosCode[i].trim().equals("26260"))&&(inspectCode[i].trim().equals("0009500")||inspectCode[i].trim().equals("8137102"));
	}

	private boolean continueHos10650(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("10650")||hosCode[i].trim().equals(""))
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim()
						.equals("0009503"));
	}

	private boolean continueHos21505(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7
				&& ((hosCode[i].trim().equals("21505")|| hosCode[i].trim().equals("29034")|| hosCode[i].trim().equals("28272") || hosCode[i].trim().equals("")|| hosCode[i].trim().equals("28312") || hosCode[i].trim().equals("28961"))
						&& (inspectCode[i].trim().equals("2106801") || inspectCode[i].trim().equals("2106802")
						|| inspectCode[i].trim().equals("2106803")|| inspectCode[i].trim().equals("2106804")));
	}

	private boolean continueHos27993(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("27993")
				&& (inspectCode[i].trim().equals("1100202"));
	}

	private boolean continueHos15428(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("15428")
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim()
						.equals("0009503"));
	}

	private boolean continueHos26454(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("26454")
				&& (inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125202")|| inspectCode[i].trim().equals("7125203"));
	}

	private boolean continueHos23535(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("23535")
				&& (inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals("7125203") 
						|| inspectCode[i].trim().equals("7125900") || inspectCode[i].trim().equals("7125902")));
	}

	private boolean continueHos15298(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7
				&& (hosCode[i].trim().equals("15298") && (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i]
						.trim().equals("0009503")));
	}

	private boolean continueHos26410(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("26410") && (inspectCode[i].trim().equals("31012"));
	}

	private boolean continueHos20713(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7 && ((hosCode[i].trim().equals("20713"))
				&& (inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102") || inspectCode[i].trim().equals("1110103")));
	}

	private boolean continueHos26713(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("26713") && (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")
				||inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102") || inspectCode[i].trim().equals("1110103")));
	}

	private boolean continueHos27693(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("27282")||hosCode[i].trim().equals("27693"))
				&& (inspectCode[i].trim().equals("7125100")||inspectCode[i].trim().equals("0009500")||inspectCode[i].trim().equals("0009502")||inspectCode[i].trim().equals("0009503"));
	}

	private boolean continueHos20231(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7 && ((hosCode[i].trim().equals("20231")) && !inspectCode[i].trim().equals("0548150") && !inspectCode[i].trim().equals("0548356") 
				&& (inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102") || inspectCode[i].trim().equals("1110103")
				||(inspectCode[i].trim().substring(0, 5).equals("05481")||(inspectCode[i].trim().substring(0, 5).equals("05483")))));
	}

	private boolean continueHos26852(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7 && ((hosCode[i].trim().equals("26852")) && !inspectCode[i].trim().equals("0548150") && !inspectCode[i].trim().equals("0548356") 
				&& (inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102") || inspectCode[i].trim().equals("1110103")
				||(inspectCode[i].trim().substring(0, 5).equals("05481")||(inspectCode[i].trim().substring(0, 5).equals("05483")))));
	}

	private boolean continueHos26719(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("26719")
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")||
						inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals("7125203"));
	}

	private boolean continueHos25815(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("25815")
				&& (inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals(
						"7125202") || inspectCode[i].trim().equals("71197")|| inspectCode[i].trim().equals("7129700")|| inspectCode[i].trim().equals("7129701")
						|| inspectCode[i].trim().equals("7129702")|| inspectCode[i].trim().equals("7129703")|| inspectCode[i].trim().equals("7129704")|| inspectCode[i].trim().equals("7129705"));
	}

	private boolean continueHos29154_1(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("29154")) && (inspectCode[i].trim().equals("31019")||inspectCode[i].trim().equals("31077"));
	}

	private boolean continueHos29154(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("29154")
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals(
						"0009503"));
	}

	private boolean continueHos17210(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("17210")
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals(
						"0009503"));
	}

	private boolean continueHos25723(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("25729") && inspectCode[i].trim().length() == 7 && inspectCode[i].trim().substring(5, 7).equals("00");
	}

	private boolean continueHos12471(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("12471")
				&& (inspectCode[i].trim().equals("7125100") || inspectCode[i].trim().equals("7125102")|| inspectCode[i].trim().equals("7125203")
						|| inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125202")|| inspectCode[i].trim().equals("7125203")
						|| inspectCode[i].trim().equals("7125900") || inspectCode[i].trim()	.equals("7125901"));
	}

	private boolean continueHos18115(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("18115")
				&& (inspectCode[i].trim().equals("7125100") || inspectCode[i].trim().equals("7125102")|| inspectCode[i].trim().equals("7125203")
						|| inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125202")|| inspectCode[i].trim().equals("7125203")
						|| inspectCode[i].trim().equals("7125900") || inspectCode[i].trim()	.equals("7125902"));
	}

	private boolean continueHos20607(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("20607")||hosCode[i].trim().equals("27233")||hosCode[i].trim().equals("24434"))
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals(
						"0009503"));
	}

	private boolean continueHos26577(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("26577"))
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")
						|| inspectCode[i].trim().equals("1100200"));
	}

	private boolean continueHos25812(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("25812")||hosCode[i].trim().equals("23619"))
				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"));
	}

	private boolean continueHos24100(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("24100")
				&& ( inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102")|| inspectCode[i].trim().equals("1110103"));
	}

	private boolean continueHos28833(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("28833")
				&& (inspectCode[i].trim().equals("0009500")
						|| inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"));
	}

	private boolean continueHos17654(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("17654")
				&& (inspectCode[i].trim().equals("1100200") || inspectCode[i].trim().equals("0009500")
						|| inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")
						|| inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102"));
	}

	private boolean continueHos29504(String[] hosCode, String[] inspectCode,
			int i) {
		return (hosCode[i].trim().equals("29504"))
				&& (inspectCode[i].trim().equals("0009500")
						|| inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"));
	}

	private boolean continueHos25493_2(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("25493")
				&& (inspectCode[i].trim().substring(0, 5).equals("05481") || inspectCode[i].trim().substring(0, 5).equals("05483") || inspectCode[i].trim().substring(0, 5).equals("21254")
						|| inspectCode[i].trim().substring(0, 5).equals("21257")
						|| inspectCode[i].trim().substring(0, 5).equals("41377") || inspectCode[i].trim().substring(0, 5).equals("41392")
						|| inspectCode[i].trim().substring(0, 5).equals("21259") || inspectCode[i].trim().substring(0, 5).equals("21273")
						|| inspectCode[i].trim().substring(0, 5).equals("21274") || inspectCode[i].trim().substring(0, 5).equals("41377")
						|| inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") 
						|| inspectCode[i].trim().equals("0009503")|| inspectCode[i].trim().equals("7218500")|| inspectCode[i].trim().equals("7218502"));
	}

	private boolean continueHos25493(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7
				&& hosCode[i].trim().equals("25493") &&( inspectCode[i].trim().equals("2163801") || inspectCode[i].trim().equals("2163802"));
	}

	//여성아이 병원 접수일자 접수번호가 없을 시 생략
	private boolean continueHos28930(String[] hosCode, String[] cns,
			String[] data, int i) {
		return hosCode[i].trim().equals("28930") &&"".equals(data[22])  && "".equals(cns[i]);
	}

	private boolean continueHos16468(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("16468") && (inspectCode[i].trim().substring(0, 5).equals("11230") && !inspectCode[i].trim().equals("1123001") );
	}

	private boolean continueHos29171(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("29171")
				&& ( inspectCode[i].trim().equals("7125200")||inspectCode[i].equals("7125203"));
	}

	private boolean continueHos22721(String[] hosCode, String[] result, int i) {
		return hosCode[i].trim().equals("22721")
				&& (result[i].trim().indexOf("추후송부") > 0 || result[i].trim().indexOf("추후보고") > 0);
	}

	private boolean continueHos29969(String[] hosCode, String[] result, int i) {
		return (hosCode[i].trim().equals("29969")||hosCode[i].trim().equals("17788")) //추후송부, 추후보고, 보고예정, 검체부족, 검체부적합등의 문구는 
				&& (result[i].trim().indexOf("추후") > 0  || result[i].trim().indexOf("보고예정") > 0 || result[i].trim().indexOf("검체") > 0);
	}
	private boolean continueHos29171(String[] hosCode, String[] inspectCode,
			String[] result, int i) {
		return hosCode[i].trim().equals("29171")
				&& (result[i].trim().indexOf("추후송부") > 0 || (inspectCode[i].trim().length() == 7 && inspectCode[i].trim().substring(5, 7).equals("00")));
	}

	private boolean continueHos28280(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("28280") && 
				 (inspectCode[i].trim().substring(0, 5).equals("00095")&& !inspectCode[i].equals("0009501"));
	}

	private boolean continueHos29463(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("29463") && 
				 (inspectCode[i].trim().substring(0, 5).equals("00095")&& !inspectCode[i].equals("0009501"));
	}

	private boolean continueHos29516(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("29516") && 
				 (inspectCode[i].trim().substring(0, 5).equals("00095")&& !inspectCode[i].equals("0009501"));
	}

	private boolean continueHos25560(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("25560") && 
			 //(inspectCode[i].trim().substring(0, 5).equals("71252")&& !inspectCode[i].equals("7125202"))||
			 (inspectCode[i].trim().substring(0, 5).equals("71252")&& !inspectCode[i].equals("7125202"));
	}

	private boolean continueHos29659(String[] hosCode, String[] inspectCode,
			int i) {
		return hosCode[i].trim().equals("29659") && 
			 //(inspectCode[i].trim().substring(0, 5).equals("71252")&& !inspectCode[i].equals("7125202"))||
			 (inspectCode[i].trim().substring(0, 5).equals("71259")&& !inspectCode[i].equals("7125901")
					 ||inspectCode[i].trim().substring(0, 5).equals("71297")
					 ||inspectCode[i].trim().substring(0, 5).equals("72180")
					 ||inspectCode[i].trim().substring(0, 5).equals("72183"));
	}

	private boolean continueHos12657(String[] hosCode, String[] inspectCode,
			int i) {
		return inspectCode[i].trim().length() == 7	&& (hosCode[i].trim().equals("12657") && 
				(inspectCode[i].trim().equals("7224101") || inspectCode[i].trim().equals("7125902")));
		        
	}

	private boolean makeHrcvDate(String[] hosCode, int i) {
		return hosCode[i].trim().equals("24580") || hosCode[i].trim().equals("24080")
				|| hosCode[i].trim().equals("21505")|| hosCode[i].trim().equals("29034") || hosCode[i].trim().equals(26719) || hosCode[i].trim().equals("25649")
				|| hosCode[i].trim().equals("26172") || hosCode[i].trim().equals("23661")||hosCode[i].trim().equals("27282")
				||hosCode[i].trim().equals("27693")||hosCode[i].trim().equals("27993")|| hosCode[i].trim().equals("28272") 
				 || hosCode[i].trim().equals("") || hosCode[i].trim().equals("28961")
				 || hosCode[i].trim().equals("28615")|| hosCode[i].trim().equals("28312")|| hosCode[i].trim().equals("28272");
	}

	private int set문장(int cnt, String[] rcvDate, String[] rcvNo,
			String[] inspectCode, String[] inspectName, String[] result,
			String[] sex, String[] lang, String[] history, String[] data,
			int i, String curDate, String curNo) {
		for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
				.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
			String arrRefer[] = getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i] }).trim().split("\r\n");
			String strRefer = arrRefer[0];
			for (int r = 1; r < arrRefer.length; r++) {
				strRefer = strRefer + "\r\n" + insertBlanks(arrRefer[r].toString(), 55);
			}

			data[35] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + strRefer;
			if (++i == cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		return i;
	}

	private boolean is29167(String[] inspectCode, int i) {
		return inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("00095") || inspectCode[i].trim().substring(0, 5).equals("72059") || inspectCode[i].trim().substring(0, 5).equals("72018")
				|| inspectCode[i].trim().substring(0, 5).equals("00938")|| inspectCode[i].trim().substring(0, 5).equals("72185");
	}

	private boolean e29154(String[] hosCode, String[] inspectCode, int i) {
		return hosCode[i].trim().equals("29154") && inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(0, 5)
				.equals("00095") || inspectCode[i].trim().substring(0, 5).equals("31001")
				|| inspectCode[i].trim().substring(0, 5).equals("00901")
				|| inspectCode[i].trim().substring(0, 5).equals("72183"));
	}
	
	private boolean is특검검사2(String[] inspectCode, int i) {
		return (inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502903")
						&&!inspectCode[i].trim().equals("0502902"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05008")&&!inspectCode[i].trim().equals("0500802"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05010")&&!inspectCode[i].trim().equals("0501002"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05021")&&!inspectCode[i].trim().equals("0502102"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05022")&&!inspectCode[i].trim().equals("0502202"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05030")&&!inspectCode[i].trim().equals("0503002"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05031")&&!inspectCode[i].trim().equals("0503102"));
	}

	private int setResult(int cnt, String[] rcvDate, String[] rcvNo,
			String[] inspectCode, String[] inspectName, String[] result,
			String[] sex, String[] age, String[] lang, String[] history,
			String[] data, int i, String curDate, String curNo) {
		for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
				.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
			if (!result[i].toString().trim().equals("")) {
				data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
						+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
			}
			if (++i == cnt || i > cnt)
				break;
		}
		return i;
	}

	private int setResult29567(int cnt, String[] rcvDate, String[] rcvNo,
			String[] inspectCode, String[] inspectName, String[] result,
			String[] sex, String[] age, String[] lang, String[] history,
			String[] data, int i, String curDate, String curNo) {
		for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
			if (!result[i].toString().trim().equals("")) {
				data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21).trim() ;
						//+ "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
			}
			if (++i == cnt || i > cnt)
				break;
		}
		return i;
	}
	
	//희경의료재단 29171 은 결과에 tab 이 들어가면 결과가 0 가 들어간다..
	private int setResultTabToBlank(int cnt, String[] rcvDate, String[] rcvNo,
			String[] inspectCode, String[] inspectName, String[] result,
			String[] sex, String[] age, String[] lang, String[] history,
			String[] data, int i, String curDate, String curNo) {
		for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
			if (!result[i].toString().trim().equals("")) {
				data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "    " + appendBlanks(result[i], 21) + "    "	+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
			}
			if (++i == cnt || i > cnt)	
			break;
				
		}
		return i;
	}
	//원병원 부속검사 결과 
	private int setResult30267(int cnt, String[] rcvDate, String[] rcvNo,
			String[] inspectCode, String[] inspectName, String[] result,
			String[] sex, String[] age, String[] lang, String[] history,
			String[] data, int i, String curDate, String curNo) {
		for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
			if (!result[i].toString().trim().equals("")) {
				data[35] += inspectName[i] + " : " + result[i] + " " + getReferenceValueNotBlank(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim() + "\r\n"   ;
				data[39] += getReferenceValueNotBlank(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
			}
			if (++i == cnt || i > cnt)
				break;
		}
		return i;
	}
	
	
	

	
	private boolean 문장_20231(String[] hosCode, String[] inspectCode, int i,
			boolean isTxtRltB) {
		return !isTxtRltB
				&& hosCode[i].trim().equals("20231")
				&& inspectCode[i].trim().length() == 7
				&& (inspectCode[i].trim().substring(0, 5).equals("05483") || inspectCode[i].trim().substring(0, 5).equals("72185")
						|| inspectCode[i].trim().substring(0, 5).equals("21638")
						|| inspectCode[i].trim().substring(0, 5).equals("31001")|| inspectCode[i].trim().substring(0, 5).equals("72182"));
	}

	private boolean is24580_24080(String[] hosCode, int i) {
		return hosCode[i].trim().equals("24580") || hosCode[i].trim().equals("24080")
				|| hosCode[i].trim().equals("21505")|| hosCode[i].trim().equals("29034") || hosCode[i].trim().equals("26719") || hosCode[i].trim().equals("21517")
				|| hosCode[i].trim().equals("13952") || hosCode[i].trim().equals("25649") || hosCode[i].trim().equals("26172")
				|| hosCode[i].trim().equals("23661") ||hosCode[i].trim().equals("27282") || hosCode[i].trim().equals("27693")
				|| hosCode[i].trim().equals("27993") || hosCode[i].trim().equals("28272")  || hosCode[i].trim().equals("28961")
				|| hosCode[i].trim().equals("") || hosCode[i].trim().equals("28615") || hosCode[i].trim().equals("28312")
				;
	}

	private boolean continueHos28696(String[] hosCode, String[] inspectCode, int i) {
		return inspectCode[i].trim().length() == 7 && (
				hosCode[i].trim().equals("28696") || hosCode[i].trim().equals("24100"))&&(inspectCode[i].trim().substring(0, 5).equals("00095") && ! inspectCode[i].equals("0009501"));
	}

	private boolean is특검검사(String[] inspectCode, int i) {
		return (inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05008")&&!inspectCode[i].trim().equals("0500802"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05010")&&!inspectCode[i].trim().equals("0501002"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05021")&&!inspectCode[i].trim().equals("0502102"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05022")&&!inspectCode[i].trim().equals("0502202"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05030")&&!inspectCode[i].trim().equals("0503002"))
				|| (inspectCode[i].trim().substring(0, 5).equals("05031")&&!inspectCode[i].trim().equals("0503102"));
	}

	private boolean is22721_문장(String[] hosCode, String[] inspectCode, int i) {
		return hosCode[i].trim().equals("22721") && inspectCode[i].trim().length() == 7 && 
		!(inspectCode[i].trim().substring(0, 5).equals("72189") || inspectCode[i].trim().substring(0, 5).equals("72242")
				|| inspectCode[i].trim().substring(0, 5).equals("72182")	|| inspectCode[i].trim().substring(0, 5).equals("72178") 
				|| inspectCode[i].trim().substring(0, 5).equals("72183")	|| inspectCode[i].trim().substring(0, 5).equals("71252")
				|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("72241") );
	}

	private boolean is27091_문장(String[] hosCode, String[] inspectCode, int i) {
		return hosCode[i].trim().equals("27091") && inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(0, 5).equals("31010")
				|| inspectCode[i].trim().substring(0, 5).equals("71259")
				|| inspectCode[i].trim().substring(0, 5).equals("21061")
				|| inspectCode[i].trim().substring(0, 5).equals("11002"));
	}

	private boolean is27021_문장(String[] hosCode, String[] inspectCode, int i) {
		return hosCode[i].trim().equals("27021") && inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(0, 5).equals("31001")
				|| inspectCode[i].trim().substring(0, 5).equals("71259") || inspectCode[i].trim().substring(0, 5).equals("71252")
				|| inspectCode[i].trim().substring(0, 5).equals("72194") || inspectCode[i].trim().substring(0, 5).equals("72241")
				 || inspectCode[i].trim().substring(0, 5).equals("72183") || inspectCode[i].trim().substring(0, 5).equals("72182")
				  || inspectCode[i].trim().substring(0, 5).equals("72018"));
	}


	private boolean is24080_장문(String[] hosCode, String[] inspectCode, int i,
			boolean isTxtRltB) {
		return !isTxtRltB && hosCode[i].trim().equals("24080") && 
		( inspectCode[i].trim().length() == 7 && (inspectCode[i].trim().substring(0, 5).equals("71139") || inspectCode[i].trim().substring(0, 5).equals("72185"))) 
		|| inspectCode[i].trim().equals("72211");
	}

	private boolean e26260(String[] hosCode, String[] inspectCode, int i,
			boolean isTxtRltB) {
		return !isTxtRltB
				&& ((hosCode[i].trim().equals("29112") || hosCode[i].trim().equals("26260")) && 
						inspectCode[i].trim().length() == 7 && (
							inspectCode[i].trim().substring(0, 5).equals("31001")
							|| inspectCode[i].trim().substring(0, 5).equals("11301")
							|| inspectCode[i].trim().substring(0, 5).equals("71252")
							|| inspectCode[i].trim().substring(0, 5).equals("71259")
							|| inspectCode[i].trim().substring(0, 5).equals("72241")
							|| inspectCode[i].trim().substring(0, 5).equals("72018")//20160414 (고성)강병원 요청 26260
							|| inspectCode[i].trim().substring(0, 5).equals("72242")
							|| inspectCode[i].trim().substring(0, 5).equals("72206")
							|| inspectCode[i].trim().substring(0, 5).equals("72183")
							|| inspectCode[i].trim().substring(0, 5).equals("72182")
							|| inspectCode[i].trim().substring(0, 5).equals("72194")
							|| inspectCode[i].trim().substring(0, 5).equals("00083")
						)
						);
	}

	private boolean continueHos20796(String[] hosCode, String[] inspectCode, int i) {
		return hosCode[i].trim().equals("20796") && inspectCode[i].trim().substring(0, 5).equals("00405")&& !  inspectCode[i].equals("0040501");
	}
}
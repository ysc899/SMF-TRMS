package com.neodin.files;

public interface IMethologyGetter {
	public static String HCVMETHODS =
			"[검사방법]\r\n" 
			+" PCR- DNA sequencing (염기서열분석법)\r\n" 
			+" -> 분자생물학기술 중 가장 정확하고 최신방법이며 고가의 장비와 고도의 기술이 필요한 염기서열\r\n" 
			+" 분석법을 이용하여 HCV 유전자형을 직접 검출함. \r\n" 
			+"\r\n" 
			+" 혈청에서 RNA 추출  -> RNA를 reverse transcriptase를 이용하여 cDNA로 전환\r\n" 
			+" ->HCV 5' UTR 부위에서  특이  Primer 를 사용하여 PCR 증폭 ->PCR 산물을 전기영동으로 확인\r\n" 
			+" ->염기서열분석 (sequencing) 실시 ->HCV 유전자형 분석하여 결과 보고 \r\n" 
			+"\r\n" 
			+"[검사 의의]\r\n" 
			+"\r\n" 
			+" C형간염바이러스 (HCV)는 RNA 바이러스로서 전세계인구의 약 1%가 감염되어 있으며 만성감염이 \r\n" 
			+" 될 경우 간경화나 간암으로 진행 가능성이 높습니다.\r\n" 
			+"\r\n" 
			+" HCV는 6종의 주된 genotype이 있고 80종 이상의 subtype이 보고되고 있으며 HCV genotype중에서\r\n" 
			+" HCV 1b 형은 치료가 어렵고 간경화로 발전할 가능성이 높으며 간 이식 후에도 간 질환의 발생빈도가\r\n" 
			+" 높습니다.\r\n" 
			+"\r\n" 
			+" HCV는 유전자형에 따라 치료의 효과, 예후 등이 다르므로, HCV 감염이 되었을 경우 주기적인 \r\n" 
			+" HCV 정량검사 와 함께 HCV genotype 검사를 실시하여야 합니다.\r\n" 
			+"\r\n" ;
	
	public static String MTHFR=
			"●   유전자: \r\n" 
			+"      MTHFR [5,10-methylenetetrahydrofolate reductase ; 1p36.3]\r\n"
			+"\r\n" 
			+"●   검사방법:\r\n" 
			+"      MTHFR 유전자의 codon 1298 위치의 Glutamate(A)가 Alanine(C)으로 바뀌는 것  \r\n" 
			+"      (1298 A>C)을 PCR-RFLP를 이용하여 검사함.\r\n" 
		
			// !
		
			+"\r\n" 
			+"●   PCR-RFLP 방법:\r\n" 
			+"      목표하는 유전자의 특정부위를 PCR을 이용하여 증폭시키고, 관련된 염기서열을 인식하는 \r\n" 
			+"      제한효소를 처리한 후, 잘라진 PCR 단편을 전기영동으로 분석하여 돌연변이 여부를\r\n" 
			+"      판단하는 방법.\r\n" 
			// !
		
			+"\r\n" 
			+"●   임상적의의\r\n" 
			+"      - MTHFR은 엽산 대사에 중요한 작용을 하는 효소 유전자로 메틸기를 homocysteine에 \r\n" 
			+"        전달하여 methionine으로 합성하는 대사과정에 관여합니다.\r\n" 
			+"\r\n" 
			+"      - 가장 대표적인 MTHFR 유전자 변이는 C677T와 A1298C입니다.  \r\n" 
			+"\r\n" 
			+"      - 677T와 1298C의 복합돌연변이인 경우 677 homozygote와 유사한 임상적 의의를  \r\n" 
			+"        가진다고 보고되고 있습니다.\r\n" 
			+"\r\n" 
			+"      - 관련질환 : Hyperhomocysteinemia, Cardiovascular disease, Thromobisis 등\r\n" ;
	
	public static String QUANTIFERON =
			"◈ 검사원리\r\n" 
			+" 혈액내에 존재하는 T세포에 결핵균 특이항원(ESAT-6,CFP-10,TB7.7(p4))을 노출시켜\r\n" 
			+"IFN-γ 농도를 측정하는 방법입니다. 결핵균에 노출되어 감작된 T세포들은 감작되지 않은\r\n" 
			+"T세포에 비해  IFN-γ 분비능이 높습니다. 증가된 IFN-γ은 효소면역분석법에 의해 측정됩니다.\r\n" 
			+"\r\n" 
			+"◈ 검사방법\r\n" 
			+" 음성대조(Nil control), 결핵균 특이항원(TB antigen:ESAT-6,CFP-10,TB7.7(p4)),양성대조\r\n" 
			+"(Mitogen control)를 37℃에서 16-24시간 배양 후 분리된 혈장을 효소면역분석법으로 측정\r\n" 
			+"하여 각 항원의 IFN-γ 반응값을 구합니다. 결핵균 특이항원의 IFN-γ 반응의 결과값에서 \r\n" 
			+"음성대조의 IFN-γ 반응의 결과값을 뺀 값(TB antigen minus Nil)을 분별한계치(cut off)를 \r\n" 
			+"기준으로 음성,양성으로 판정됩니다.\r\n" 
			+"\r\n" 
			+"◈ 검사의의\r\n" 
			+" 결핵균 특이항원인 ESAT-6,CFP-10,TB7.7(p4)는 BCG 접종에 영향을 받지 않으며, 대부\r\n" 
			+"분의 NTM(nontuberculous mycobacteria) 감염에도 영향을 받지 않기 때문에, 잠복결핵\r\n" 
			+"(latent tuberculosis)을 진단하는데 우수한 검사법입니다. 그러나, cellular immune\r\n" 
			+"response에 이상이 있는 환자의 경우에는 False negative or Indeterminate 결과를 보일 수 있으므로, \r\n" 
			+"HIV 감염자,장기이식환자, 면역억제제를 장기간 사용하는 환자 등에서는 결과 해석에 주의를 요합니다. \r\n" 
			+"\r\n"; 
	
	public static String METHOD_71245 =
			"현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘(Lamivudine)은 \r\n"
			+"HBV중합 효소(Polymerase)의 합성을 억제하여 \r\n"
			+	"바이러스 유준자 증폭을 차단하는 역할을 합니다. \r\n" 
			+	"그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의 \r\n" 
			+	"출현확륙이 높아지게 되고 결국에는 치료의 실패내지는 \r\n" 
			+	"재발로 이어지는 문제점을 나타 냅니다.\r\n" 
			+	"이러한 변이형은 HBV중합 효소(Polymerase)내의 codon552와 codon528 염기 서열의\r\n" 
			+	"돌연변이를 주로 발생하게 되는데 본검사는 HBV중합 효소(Polymerase)의 특정부위를\r\n" 
			+	"증폭한 후 염기 서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\r\n" 
			+	"따라서 이러한 변이형의 발견은 치료경과 관찰, Vrial breakthrough 의 \r\n" 
			+	"조기 발견및 치료방침을 결정하는데 유용하게 사용될 수 있습니다.\r\n";
	
	public static String H1N1 = 
		 "▣ 검체\r\n"
		+ "비인후 또는 인후 도찰물\r\n"
		+ "\r\n"
		+ " ▣ 검사방법\r\n"
		+ " Real time RT-PCR(실시간역전사중합효소연쇄반응법)" + "\r\n"
		+ " 1. 환자의 검체로부터 RNA를 추출합니다. " + "\r\n"
		+ " 2. Influenza A(H1N1)의 Hemagglutinin(HA) 영역에서 고안된 Primer로 RT-PCR을 실시합니다." + "\r\n"
		+ " 3. 증폭된 PCR 산물을 Influenza A(H1N1)-specific probe(5'-FAM 5'BHQ1)와 hybridization 시킵니다. " + "\r\n"
		+ " 4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는" + "\r\n"
		+ "  형광을 실시간으로 측정합니다." + "\r\n"
		+ "▣ Remark" + "\r\n"
		+ " 1. 본 검사는 Real time RT-PCR 원리를 이용하여 신종인플루엔자 A [Influenza A(H1N1)]에 특이한 primer를" + "\r\n"
		+ "   사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다." + "\r\n"
		+ " 2. Influenza A 검사를 동시에 실시하여 확인된 결과입니다." + "\r\n"
		+ " 3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다." + "\r\n"
		+ "" + "\r\n";

	public static String H1N1_NEW= "▣ Specimen   : 비인후 또는 인후 도찰물\r\n"
			+ "▣ Methods : Real-time RT-PCR(실시간역전사중합효소연쇄반응법) \r\n"
			+ "\r\n"
			+ "1. 환자의 검체로부터 RNA를 추출합니다.\r\n"
			+ "2. 각 영역에서 특이한 primer를 사용하여 RT-PCR을 실시합니다.\r\n"
			+ "    - Influenza A (H1N1, 신종플루) - Hemagglutinin(HA)\r\n"
			+ "    - Common Influenza A - Matrix protein(MP)\r\n"
			+ "    - Influenza A(H1N1, 계절성) - Hemagglutinin(HA)\r\n"
			+ "    - Influenza A(H3N2, 계절성) - Hemagglutinin(HA)\r\n"
			+ "\r\n"
			+ "3. 증폭된 PCR 산물을 각각의 specific probe와 hybridization 시킵니다. \r\n"
			+ "    -Influenza A(H1N1, 신종플루)-specific probe(5'-FAM 3'-BHQ1)\r\n"
			+ "    -Common Influenza A- specific probe(5'-FAM 3'-BHQ1)\r\n"
			+ "    -Influenza A(H1N1, 계절성) -specific probe(5'-FAM 3'-TAMRA)\r\n"
			+ "    -Influenza A(H3N2, 계절성) -specific probe(5'-HEX 3'-BHQ1)\r\n"
			+ "\r\n"
			+ "4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는 \r\n"
			+ "   형광을 실시간으로 측정합니다.\r\n"
			+ "\r\n"
			+ "▣ Remark \r\n"
			+ "\r\n"
			+ "1. 본 검사는 realtime RT-PCR 원리를 이용하여 신종인플루엔자 및 계절성 인플루엔자에 특이한 \r\n"
			+ "   primer를 각각 사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다.\r\n"
			+ "\r\n"
			+ "2. 계절성 Influenza A 의 검사는 Influenza A (H1N1, 신종플루)에서 음성, Common Influenza A 에서 양성인\r\n"
			+ "   경우에 한하여 2차 검사로 진행됩니다. \r\n"
			+ "\r\n"
			+ "3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다.\r\n";
	public static String HLAB27 = "▣ 검사방법\r\n"
			+ "PCR (Polymerase Chain Reaction)\r\n"
			+ "\r\n"
			+ "  -> 전혈에서 DNA를 추출 \r\n"
			+ "  -> HLA-B27 유전자에 특이한 시발체로 PCR시행 " + "\r\n"
			+ "  -> 전기영동으로 증폭된 PCR 산물 확인 " + "\r\n"
			+ "  -> 결과보고 " + "\r\n"
			+ "" + "\r\n"
			+ "▣ 검사 의의" + "\r\n"
			+ "" + "\r\n"
			+ " HLA-B27 유전자는 강직성척수염환자의 90% 정도에서 나타납니다." + "\r\n"
			+ "" + "\r\n"
			+ " 강직성척수염 (Ankylosing spondylitis) 이란 류마티스 질환의 일종으로 16~35세의 남자들에서" + "\r\n"
			+ " 주로 발생하며, 척추관절이나 무릎, 발목, 발가락, 골반, 갈비뼈 등과 같은 관절에 염증을 일으킬 수" + "\r\n"
			+ " 있습니다. " + "\r\n"
			+ "" + "\r\n"
			+ " HLA-B27 유전자와 강직성척수염과의 유전적 관련성이 매우 높다고 보고되고 있지만, 이 유전자는 " + "\r\n"
			+ " 유전자가 발견되었다고 하여 반드시 강직성척수염이 발생한다고 볼 수는 없습니다." + "\r\n"
			+ "" + "\r\n"
			+ " 이외에 HLA-B27 유전자와 연관성이 있는 질환은 Reiter's disease, Juvenile rheumatoid arthritis," + "\r\n"
			+ " Anterior uveitis 등이 있습니다." + "\r\n";
	
	public static String SEEGENE_INFO="\r\n검사실명 : (재)씨젠의료재단\r\n"
		    +"주     소 : 서울 특별시 성동구 천호대로320(용답동)\r\n"
		    +"전 문 의 : 이선화M.D. (제 416호) \r\n"
		    +"검사기관 : 11370319";
	public static String DELI_CRLF = "\r\n";
	public static String DELI_NIL = "";
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public static String H1N1() {
		return H1N1;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public static String H1N1_NEW() {
		
		return H1N1_NEW;
		
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String
	 */
	public static String HLAB27() {
	
		return HLAB27;
		
	}

	public static String getHCVMethods() {
		return HCVMETHODS;
	}
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-06-16 오전 9:11:40)
	 * 
	 * @return java.lang.String
	 */
	public static String getMTHFR() {

		return MTHFR;
		
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-20 오전 9:39:07)
	 * 
	 * @return java.lang.String
	 */
	public static String getQuantiFERON() {
			
		return QUANTIFERON;
	}
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-20 오전 9:39:07)
	 * 
	 * @return java.lang.String
	 */
	public static String get71245() {
		
		
		return METHOD_71245;
	}

}

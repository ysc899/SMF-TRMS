package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadYonsei extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

//	private Hashtable hsRemark = null;

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-01-06 오후 6:03:43)
	 */
	public DownloadYonsei() {
	}

	public DownloadYonsei(String id, String fdat, String tdat, Boolean isRewrite) {
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
		if (!isDebug && isData)
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

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 jre1.22 작성 날짜:
	 * (2005-10-24 오후 10:06:16)
	 */
	private String getHBV() {
		String str = "";
		// //!
		str += "[Remark]\r\n";
		str += "현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘 (Lamivudine) 은 HBV 중합효소 (polymerase) 의 합성을 억제하여\r\n";
		str += "바이러스 유전자 증폭을 차단하는 역할을 합니다. 그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의\r\n";
		str += "출현확률이 높아지게 되고 결국에는 치료의 실패내지는 재발로 이어지는 문제점을 나타냅니다.\r\n";
		str += "이러한 변이형은 HBV 중합효소 (polymerase)내의 codon552 과 codon528 염기서열의 돌연변이로 주로 발생하게 되는데\r\n";
		str += "본 검사는 HBV 중합효소(polymerase)의 특정부위를 증폭한 후 염기서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\r\n";
		str += "따라서 이러한 변이형의 발견은 치료경과 관찰, Viral breakthrough 의 조기발견 및 치료방침을 결정하는데 유용하게 \r\n";
		str += "사용될 수 있습니다.\r\n";
		return str;
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
	 * makeDownloadFile 메소드 주석.
	 */
	public void makeDownloadFile() {
		row = 2;
		row2 = 1;
		try {
			if (!isDebug) {

				// Start =============== >엑셀파일을 만든다 : 파일명 ,시트명
				// #####################################
				workbook = Workbook.createWorkbook(new File(savedir	+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				label = new jxl.write.Label(0, 0,
						"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				wbresult.addCell(label);
				ArraryResult = new String[] { "기관구분", "검체번호", "수신자명", "성별",
						"나이", "차트번호", "접수일자", "접수번호", "검사코드", "병원검사코드", "검사명",
						"문자결과", "문장결과", "H/L", "Remark번호", "참고치", "주민등록번호" };
				String ArraryRemark[] = { "검체번호", "Remark 번호", "Remark내용" };
				//

				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 1, ArraryResult[i]);
					wbresult.addCell(label);
				}
				for (int i = 0; i < ArraryRemark.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryRemark[i]);
					wbremark.addCell(label);
				}
				// End == == == == == == = > 엑셀파일을 만든다 : 파일명, 시트명 # # # # # # #
				// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
				// processCallDpcMCR03rmr5();

				//	
				// 파일은 일단 서버에 생성이 된다
			}
			//
		} catch (Exception e) {
			//System.out.println("OCS 파일쓰기 스레드 오류" + e.getMessage());
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
			String age[] = (String[]) getDownloadData().get("나이");
			String securityNo[] = (String[]) getDownloadData().get("주민번호");
			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String data[] = new String[17];
			String data2[] = new String[3];
			String remark[] = new String[4];
			boolean culture_flag = false;
//			hsRemark = new Hashtable();
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				data[0] = "11370319";
				data[1] = specNo[i].trim();
				data[2] = patName[i];
				data[3] = sex[i];
				data[4] = age[i];
				data[5] = chartNo[i];
				data[6] = rcvDate[i].trim();
				data[7] = rcvNo[i].trim();
				data[8] = inspectCode[i].trim();
				data[9] = clientInspectCode[i].trim();
				data[10] = inspectName[i];
				data[12] = "";
				String curDate = "";
				String curNo = "";
				data[16] = securityNo[i];
				if (resultType[i].trim().equals("C")) {
					data[11] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					data[15] = getReferenceValue(remark);
					data[12] = "";
					if (hosCode[i].trim().equals("17251")&& inspectCode[i].trim().substring(0, 5).equals("21065")) {
						if (inspectCode[i].trim().substring(5, 7).equals("00")) {
							data[11] = result[i];
							remark[0] = inspectCode[i];
							remark[1] = lang[i];
							remark[2] = history[i];
							remark[3] = sex[i];
							data[15] = getReferenceValue(remark);
						}
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							if (inspectCode[i].trim().substring(5, 7).equals("00")) 
							{
								data[11] = result[i];
								remark[0] = inspectCode[i];
								remark[1] = lang[i];
								remark[2] = history[i];
								remark[3] = sex[i];
								data[15] = getReferenceValue(remark);
							} 
							if (++i >= cnt)
								break;
						}
						i--;
						// String temp = HLAB27();
						// if (!temp.trim().equals(""))
						// data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					}
					else if (isMAST(inspectCode[i].trim().substring(0, 5))) {  
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "   "	+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = getMastRemark();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (isMAST_Two(inspectCode[i].trim().substring(0, 5))) {
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "   "	+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = getMastRemark();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("72241"))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"	+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)	+ "   "+ getReferenceValue(	new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = getHCVMethods();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("81388"))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "   "	+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = getMTHFR();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("71244"))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "	+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "   "	+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = getHBV();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("72047")) 
					{
						String temp = H1N1();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					}
					else if (inspectCode[i].trim().substring(0, 5).equals("71011"))
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"	+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = HLAB27();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("21297")) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"	+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i >= cnt)
								break;
						}
						i--;
						String temp = getQuantiFERON();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n" + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("71048")|| 71031 < Integer.parseInt(inspectCode[i].trim().substring(0, 5))
							&& Integer.parseInt(inspectCode[i].trim().substring(0, 5)) < 71043 || 71043 < Integer.parseInt(inspectCode[i].trim()	.substring(0, 5))
							&& Integer.parseInt(inspectCode[i].trim().substring(0, 5)) < 71047) 
					{
						data[11] = "";
						data[12] = "";
						data[15] = "";
						data[12] += getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i])+ "\r\n";
						String temp = getReamrkValue(hosCode[i], rcvDate[i],	rcvNo[i], rmkCode[i]);
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n리 마 크  : " + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("11026")) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())	&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"	+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)	+ "   "+ getReferenceValue(	new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
						String temp = getReamrkValue(hosCode[i], rcvDate[i],	rcvNo[i], rmkCode[i]);
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n리 마 크  : " + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if ((inspectCode[i].trim().substring(0, 5).equals("00091")|| inspectCode[i].trim().substring(0, 5).equals("00095")
							|| inspectCode[i].trim().substring(0, 5).equals("10015")|| inspectCode[i].trim().substring(0, 5).equals("10011")
							|| inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("10001"))
							&& !inspectCode[i].trim().substring(0, 5).equals("00009")) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"	+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("71244") || inspectCode[i].trim().substring(0, 5).equals(	"71251")
							|| inspectCode[i].trim().substring(0, 5).equals("71252")|| inspectCode[i].trim().substring(0, 5).equals("71258")
							|| inspectCode[i].trim().substring(0, 5).equals("71259")) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "   "	+ appendBlanks(result[i], 21)	+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
						String temp = getReamrkValue(hosCode[i], rcvDate[i],	rcvNo[i], rmkCode[i]);
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n리 마 크  : " + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					} 
					else if (inspectCode[i].trim().substring(0, 5).equals("72059") ||inspectCode[i].trim().substring(0, 5).equals("72018")) 
					{
						data[12] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "	+ "참    고    치";
						data[12] += "\r\n" + appendBlanks(inspectName[i], 30)+ "   " + appendBlanks(result[i], 21) + "   "+ data[15];
						data[11] = "";
						data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[12] += "\r\n"	+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)+ "   "	+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
						String temp = getReamrkValue(hosCode[i], rcvDate[i],	rcvNo[i], rmkCode[i]);
						temp += "\r\n" + "\r\n" + H1N1_NEW();
						if (!temp.trim().equals(""))
							data[12] += "\r\n\r\n리 마 크  : " + temp + "\r\n";
						data[12] += "\r\n완료된 결과 입니다";
					}
				} 
				else 
				{
					 if (inspectCode[i].trim().substring(0, 5).equals("61022")) 
					 {
							data[11] = "";
							data[12] = "";
							data[15] = "";
							data[12] += getTextResultValue(hosCode[i], rcvDate[i],	rcvNo[i], inspectCode[i]);
							data[12] += "\r\n완료된 결과 입니다";
							data[12]=data[12].replaceAll("\r\nethesda", "ethesda");
							data[12]=data[12].replaceAll("\r\n\\(Bethesda", "\\(Bethesda");
						 
					 }
					 else
					 {
						data[11] = "";
						data[12] = "";
						data[15] = "";
						data[12] += getTextResultValue(hosCode[i], rcvDate[i],	rcvNo[i], inspectCode[i]);
						data[12] += "\r\n완료된 결과 입니다";
					 }
				}
				if (data[12].trim().equals("완료된 결과 입니다"))
				{
					data[12] = "";
				}
				data[13] = highLow[i];
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim())|| !kumdata[1].trim().equals(data[7].trim())	|| !kumdata[2].trim().equals(rmkCode[i].trim())) 
						{
							// row2++;
							data[14] = rmkCode[i].trim();
							data2[0] = data[1];
							data2[1] = data[14];
							data2[2] = getReamrkValue(hosCode[i], rcvDate[i],rcvNo[i], rmkCode[i]);
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = data[14].trim();

							// !
							try {
								if (!isDebug) {
									for (int j = 0; j < data2.length; j++) {
										label = new jxl.write.Label(j, row2,
												data2[j]);
										wbremark.addCell(label);
									}
								} 
							} catch (Exception e) {
							}
							row2++;
						}
					} catch (Exception _ex) {
					}
				else
					data[14] = "";
				if (!isDebug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
						wbresult.addCell(label);
					}
				} 
				row++;
				if (culture_flag) {
					i++;
					culture_flag = false;
				}
			}
			if (cnt == 400)
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			else
				setParameters(null);
		} catch (Exception _ex) {
			setParameters(null);
		}
	}
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

public class DownloadSR extends ResultDownload {

	// 서린병원
	boolean debug = false;

	boolean isData = true;

	//
	String gubun1 = "============================================================";

	String gubun2 = "------------------------------------------------------------";

//	private java.text.DecimalFormat df = new java.text.DecimalFormat(
//			"#######0.0");

	//
//	private com.neodin.result.PatientInformation mPatientData;

	public DownloadSR() {
		initialize();
	}

	public DownloadSR(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String src, int length) {
		return OracleCommon.appendBlanks(src, length);
	}

	public void closeDownloadFile() {
		if (!debug && isData) {
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

	public void makeDownloadFile() {
		row = 1;
		row2 = 1;
		try {
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				// label = new jxl.write.Label(0, 0, "(재)씨젠의료재단 첫번재 ,두번째 Row -
				// 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				// wbresult.addCell(label);
				// ArraryResult = (new String[] {"기관구분", "검체번호", "수신자명", "성별",
				// "나이", "차트번호", "접수일자", "접수번호", "검사코드", "병원검사코드", "검사명",
				// "문자결과", "문장결과", "H/L", "Remark", "참고치", "주민등록번호", "처방번호"});
				ArraryResult = (new String[] { "차트번호", // A 0
						"접수일자", // B1
						"검사코드", // C2
						"이름", // D3
						"주민번호", // E4
						"나이", // F5
						"성별", // G6
						"신장", // H7
						"체중", // I8
						"임신주", // J9
						"임신주", // K10
						"진료과", // L11
						"병동", // M12
						"진료의사", // N13
						"총뇨량", // O14
						"채취일자", // P15
						"접수일자", // Q16
						"접수번호", // R17
						"진행사항", // S18
						"결과", // T19
						"참고치", // U20
						"보고일자", // V21
						"검사자", // W22
						"확인자", // X23
						"검사명칭", // Y24
						"분류번호", // Z25
						"조직번호", // AA26
						"의뢰종목", // AB27
						"검체종류", // AC28
						"검사분류", // AD 29
						"검체번호", // AE30
						"처리부서", // AF31
						"장비", // AG32
						"결과상태", // AH33
						"콜로니", // AI34
						"패닉", // AJ35
						"델타", // AK36
						"리마크", // AL37
						"검사코드", // AM38
						"기타", // AN39
						"기타", // AO40

				});
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
			}
		} catch (Exception e) {
		}
	}

	public void processingData(int cnt) {
		try {

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
//			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
//			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");
//			String img[] = (String[]) getDownloadData().get("이미지여부");
			String unit[] = (String[]) getDownloadData().get("참고치단위등");

			// !
			String data[] = new String[41];
//			String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				String curDate = "";
				String curNo = "";
				data[0] = chartNo[i];
				data[1] = rcvDate[i].trim();
				data[2] = clientInspectCode[i].trim();
				data[3] = patName[i];
				data[4] = securityNo[i];
				data[5] = sex[i];
				data[6] = age[i];
				data[7] = ""; // 신장
				data[8] = ""; // 체중
				data[9] = ""; // 임신주
				data[10] = ""; // 초음파
				data[11] = ""; // 진료과
				data[12] = ""; // 병동
				data[13] = ""; // 진료의
				data[14] = ""; // 총뇨량
				data[15] = ""; // 채취일자
				data[16] = rcvDate[i].trim();
				data[17] = rcvNo[i].trim();
				data[18] = ""; // 진행사항
				data[19] = ""; // 결과
				data[20] = unit[i]; // 차고치
				data[21] = bdt[i]; // 보고일자

				// data[22] = clientInspectCode[i].trim();
				// data[23] = patName[i];
				data[22] = "";
				data[23] = "";
				data[24] = inspectName[i]; // 검사명
				data[25] = ""; // 검사명
				data[26] = ""; // 조직번호
				data[27] = ""; // 의뢰종목
				data[28] = ""; // 검체종류
				data[29] = ""; // 검사분류
				data[30] = specNo[i]; // 검체번호

				data[31] = ""; // 학부
				data[32] = "";
				data[33] = ""; // 결과상태
				data[34] = ""; // 콜로니
				data[35] = ""; // 패닉
				data[36] = ""; // 델타
				data[37] = ""; // 리마크
				data[38] = inspectCode[i]; // 검사코드
				data[39] = ""; // 기타
				data[40] = ""; // 기타

				// !

				// !
				if (resultType[i].trim().equals("C")) {
					data[19] = result[i];
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
					data[37] = getReferenceValueTrim(remark);
					if (inspectCode[i].trim().length() == 7	&& (inspectCode[i].trim().substring(0, 5).equals("11052") ||  inspectCode[i].trim().substring(0, 5).equals("72242")||  inspectCode[i].trim().substring(0, 5).equals("72178"))) 
					{
						data[19] = appendBlanks("검  사  명 ", 30) + "        "	+ appendBlanks("결    과", 21) + "         "+ "참    고    치";
						data[19] += "\n" + appendBlanks(inspectName[i], 30)+ "        " + appendBlanks(result[i], 21)+ "        " + data[15];
						// data[11] = "";
						// data[15] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) {
							data[19] += "\n"+ appendBlanks(inspectName[i], 30)+ "        "+ appendBlanks(result[i], 21)+ "        "
									+ getReferenceValue(new String[] { inspectCode[i],lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					data[19] = getTextResultValueTrim(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				}
				if (inspectCode[i].trim().substring(0, 5).equals("72039") || //
						inspectCode[i].trim().substring(0, 5).equals("72059") || //
						inspectCode[i].trim().substring(0, 5).equals("72080") || //
						inspectCode[i].trim().substring(0, 5).equals("90100") || //
						inspectCode[i].trim().substring(0, 5).equals("90028") || //
						inspectCode[i].trim().substring(0, 5).equals("00673") || //
						inspectCode[i].trim().substring(0, 5).equals("00674") || //
						inspectCode[i].trim().substring(0, 5).equals("00683") || //
						inspectCode[i].trim().substring(0, 5).equals("00684") ||
						inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689")||
						inspectCode[i].trim().substring(0, 5).equals("72001") || //
						inspectCode[i].trim().substring(0, 5).equals("71001") || //
						inspectCode[i].trim().substring(0, 5).equals("71002") || //
						inspectCode[i].trim().substring(0, 5).equals("71003") || //
						inspectCode[i].trim().substring(0, 5).equals("71004") || //
						inspectCode[i].trim().substring(0, 5).equals("71005") || //
						inspectCode[i].trim().substring(0, 5).equals("71006") || //
						inspectCode[i].trim().substring(0, 5).equals("71007") || //
						inspectCode[i].trim().substring(0, 5).equals("71008") || //
						inspectCode[i].trim().substring(0, 5).equals("31022") || //
						inspectCode[i].trim().substring(0, 5).equals("00301") || //
						inspectCode[i].trim().substring(0, 5).equals("00302") || //
						inspectCode[i].trim().substring(0, 5).equals("00303") || //
						inspectCode[i].trim().substring(0, 5).equals("00304") || //
						inspectCode[i].trim().substring(0, 5).equals("00305") || //
						inspectCode[i].trim().substring(0, 5).equals("00306") || //
						inspectCode[i].trim().substring(0, 5).equals("00307") || //
						inspectCode[i].trim().substring(0, 5).equals("00308") || //
						inspectCode[i].trim().substring(0, 5).equals("00309") || //
						inspectCode[i].trim().substring(0, 5).equals("00310") || //
						inspectCode[i].trim().substring(0, 5).equals("00311") || //
						inspectCode[i].trim().substring(0, 5).equals("00312") || //
						inspectCode[i].trim().substring(0, 5).equals("00313") || //
						inspectCode[i].trim().substring(0, 5).equals("00314") || //
						inspectCode[i].trim().substring(0, 5).equals("00315") || //
						inspectCode[i].trim().substring(0, 5).equals("00316") || //
						inspectCode[i].trim().substring(0, 5).equals("00317") || //
						inspectCode[i].trim().substring(0, 5).equals("00318") || //
						inspectCode[i].trim().substring(0, 5).equals("00319") || //
						inspectCode[i].trim().substring(0, 5).equals("00320") || //
						inspectCode[i].trim().substring(0, 5).equals("00321") || //
						inspectCode[i].trim().substring(0, 5).equals("00322") || //

						inspectCode[i].trim().substring(0, 5).equals("72241") || //
						inspectCode[i].trim().substring(0, 5).equals("71259") || //
						inspectCode[i].trim().substring(0, 5).equals("71246") || //
						inspectCode[i].trim().substring(0, 5).equals("71249") || //
						inspectCode[i].trim().substring(0, 5).equals("41338") || //
						inspectCode[i].trim().substring(0, 5).equals("41381") || //
						inspectCode[i].trim().substring(0, 5).equals("41382") || //
						inspectCode[i].trim().substring(0, 5).equals("41383") || //
						inspectCode[i].trim().substring(0, 5).equals("00673") || //
						inspectCode[i].trim().substring(0, 5).equals("00674") || //
						inspectCode[i].trim().substring(0, 5).equals("31022") || //
						inspectCode[i].trim().substring(0, 5).equals("72001") || //

						inspectCode[i].trim().substring(0, 5).equals("71004") || //
						inspectCode[i].trim().substring(0, 5).equals("41035") || //
						inspectCode[i].trim().substring(0, 5).equals("41052") || //
						inspectCode[i].trim().substring(0, 5).equals("41092") || //
						inspectCode[i].trim().substring(0, 5).equals("41097") || //
						inspectCode[i].trim().substring(0, 5).equals("41114") || //
						inspectCode[i].trim().substring(0, 5).equals("31022") || //

						inspectCode[i].trim().substring(0, 5).equals("00323") || //
						inspectCode[i].trim().substring(0, 5).equals("00323") || //
						inspectCode[i].trim().substring(0, 5).equals("72018")) {
					data[19] = "이미지첨부";
				}

				// /!
				if (rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[6].trim())|| !kumdata[1].trim().equals(data[7].trim())|| !kumdata[2].trim().equals(remarkCode)) 
						{
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals("11026")|| inspectCode[i].trim().substring(0, 5).equals("11052")) 
							{
								data[19] = data[19]+ ""+ getReamrkValueTrim(hosCode[i],rcvDate[i], rcvNo[i],rmkCode[i]);
							} else {
								data[37] = getReamrkValueTrim(hosCode[i],rcvDate[i], rcvNo[i], rmkCode[i]);
							}
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
						//System.out.println(_ex);
					}
				} else {
					remarkCode = "";
				}
				if (!debug) {
					if (inspectCode[i].trim().length() == 5	|| (inspectCode[i].trim().length() == 7 && !inspectCode[i].trim().substring(5, 7).equals("00"))) 
					{
						for (int k = 0; k < data.length; k++) {
							label = new jxl.write.Label(k, row, data[k]);
							wbresult.addCell(label);
						}
						row++;
					}
				} 
				// row++;
			}
			if (cnt == 400) {
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception _ex) {
			//System.out.println(_ex.getMessage());
			setParameters(null);
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public String YMDD() {
		String str = "";
		// str += "▣ Specimen : 비인후 또는 인후 도찰물\r\n";
		// str += "▣ Methods : Real-time RT-PCR(실시간역전사중합효소연쇄반응법) \r\n";
		// str += "\r\n";
		// str += "1. 환자의 검체로부터 RNA를 추출합니다.\r\n";
		// str += "2. 각 영역에서 특이한 primer를 사용하여 RT-PCR을 실시합니다.\r\n";
		// str += " - Influenza A (H1N1, 신종플루) - Hemagglutinin(HA)\r\n";
		// str += " - Common Influenza A - Matrix protein(MP)\r\n";
		// str += " - Influenza A(H1N1, 계절성) - Hemagglutinin(HA)\r\n";
		// str += " - Influenza A(H3N2, 계절성) - Hemagglutinin(HA)\r\n";
		// str += "\r\n";
		// str += "3. 증폭된 PCR 산물을 각각의 specific probe와 hybridization 시킵니다. \r\n";
		// str += " -Influenza A(H1N1, 신종플루)-specific probe(5'-FAM;
		// 3'-BHQ1)\r\n";
		// str += " -Common Influenza A- specific probe(5'-FAM; 3'-BHQ1)\r\n";
		// str += " -Influenza A(H1N1, 계절성) -specific probe(5'-FAM;
		// 3'-TAMRA)\r\n";
		// str += " -Influenza A(H3N2, 계절성) -specific probe(5'-HEX;
		// 3'-BHQ1)\r\n";
		// str += "\r\n";
		// str += "4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의
		// report dye로부터 방출되는 \r\n";
		// str += " 형광을 실시간으로 측정합니다.\r\n";
		// str += "\r\n";
		// str += "▣ Remark \r\n";
		// str += "\r\n";
		// str += "1. 본 검사는 realtime RT-PCR 원리를 이용하여 신종인플루엔자 및 계절성 인플루엔자에 특이한
		// \r\n";
		// str += " primer를 각각 사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다.\r\n";
		// str += "\r\n";
		// str += "2. 계절성 Influenza A 의 검사는 Influenza A (H1N1, 신종플루)에서 음성,
		// Common Influenza A 에서 양성인\r\n";
		// str += " 경우에 한하여 2차 검사로 진행됩니다. \r\n";
		// str += "\r\n";
		// str += "3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검
		// 바랍니다.\r\n";
		// return str;
		str += "현재 만성 B형 간염 치료제로 사용되고 있는 라미부딘 (Lamivudine) 은 HBV 중합효소 (polymerase) 의 합성을 억제하여\n";
		str += "바이러스 유전자 증폭을 차단하는 역할을 합니다. 그러나 라미부딘의 투여기간이 길어질수록 약제에 내성을 갖는 변이형의\n";
		str += "출현확률이 높아지게 되고 결국에는 치료의 실패내지는 재발로 이어지는 문제점을 나타냅니다.\n";
		str += "이러한 변이형은 HBV 중합효소 (polymerase)내의 codon552 과 codon528 염기서열의 돌연변이로 주로 발생하게 되는데\n";
		str += "본 검사는 HBV 중합효소(polymerase)의 특정부위를 증폭한 후 염기서열분석법을 이용하여 돌연변이 여부를 확인하게 됩니다.\n";
		str += "따라서 이러한 변이형의 발견은 치료경과 관찰, Viral breakthrough 의 조기발견 및 치료방침을 결정하는데 유용하게\n";
		str += "사용될 수 있습니다.";
		return str;
		// return null;
	}
}

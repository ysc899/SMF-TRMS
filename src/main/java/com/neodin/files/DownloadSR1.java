package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 11   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadSR1 extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	private String deli = "\t";

	public DownloadSR1() {
		isDebug = false;
	}

	public DownloadSR1(String id, String fdat, String tdat, Boolean isRewrite) {
		isDebug = false;
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
		return refer.equals("") ? "" : refer;
	}

	public String getRemark(String parm[]) {
		return getReamrkValue(parm[0], parm[1], parm[2], parm[3]);
	}

	public String getRemarkTxt(String str[]) {
		StringBuffer b = new StringBuffer("");
		if (str.length == 0)
			return null;
		for (int i = 0; i < str.length; i++) {
			b.append(str[i]);
			if (str.length - 1 != i)
				b.append("");
		}
		return b.toString().trim();
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

	public void makeDownloadFile() {
		String[] ArraryResult = new String[41];
		try {
			file = new File(savedir + mkOutTextFileNew());
			ArraryResult = (new String[] { "차트번호", // A 0
					"접수일자", // B1
					"검사코드" // C2
					, "이름", // D3
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
			text.append(ArraryResult[0] + deli + ArraryResult[1] + deli
					+ ArraryResult[2] + deli + ArraryResult[3]
					+ deli
					+ ArraryResult[4]
					+ deli
					+ ArraryResult[5].toString()
					+ deli
					+ ArraryResult[6]
					+ deli
					+ ArraryResult[7]
					+ deli
					+ ArraryResult[8]
					+ deli
					+ ArraryResult[9]
					+ deli
					+ ArraryResult[10]
					+ deli
					+ //
					ArraryResult[11] + deli + ArraryResult[12] + deli
					+ ArraryResult[13] + deli + ArraryResult[14] + deli
					+ ArraryResult[15].toString() + deli + ArraryResult[16]
					+ deli
					+ ArraryResult[17]
					+ deli
					+ ArraryResult[18]
					+ deli
					+ ArraryResult[19]
					+ deli
					+ ArraryResult[20]
					+ deli
					+ //
					ArraryResult[21] + deli + ArraryResult[22] + deli
					+ ArraryResult[23] + deli + ArraryResult[24] + deli
					+ ArraryResult[25].toString() + deli + ArraryResult[26]
					+ deli + ArraryResult[27] + deli + ArraryResult[28] + deli
					+ ArraryResult[29]
					+ deli
					+ ArraryResult[30]
					+ deli
					+ //
					ArraryResult[31] + deli + ArraryResult[32] + deli
					+ ArraryResult[33] + deli + ArraryResult[34] + deli
					+ ArraryResult[35].toString() + deli + ArraryResult[36]
					+ deli + ArraryResult[37] + deli + ArraryResult[38] + deli
					+ ArraryResult[39] + deli + ArraryResult[40] + "\n");
			b_writer.write(text.toString(), 0, text.toString().length());
			text = new StringBuffer();
		} catch (Exception e) {
			e.printStackTrace();
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
//				String curDate = "";
//				String curNo = "";
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
					data[37] = getReferenceValue(remark);

					// !

				} else {
					data[19] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				if (inspectCode[i].trim().substring(0, 5).equals("71244")) {
					data[19] += "\n" + YMDD();
				}
				// data[13] = highLow[i];
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[6].trim())
								|| !kumdata[1].trim().equals(data[7].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals(
									"11026")
									|| inspectCode[i].trim().substring(0, 5)
											.equals("11052"))
								data[19] = data[19]
										+ "\n"
										+ getReamrkValue(hosCode[i],
												rcvDate[i], rcvNo[i],
												rmkCode[i]);
							else
								data[37] = getReamrkValue(hosCode[i],
										rcvDate[i], rcvNo[i], rmkCode[i]);
							kumdata[0] = data[6].trim();
							kumdata[1] = data[7].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				else
					remarkCode = "";
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}
				text.append(data[0] + deli + data[1] + deli + data[2] + deli
						+ data[3] + deli + data[4] + deli + data[5].toString()
						+ deli + data[6]
						+ deli
						+ data[7]
						+ deli
						+ data[8]
						+ deli
						+ data[9]
						+ deli
						+ data[10]
						+ deli
						+ //
						data[11] + deli + data[12] + deli + data[13] + deli
						+ data[14] + deli + data[15].toString() + deli
						+ data[16] + deli + data[17] + deli
						+ data[18]
						+ deli
						+ data[19]
						+ deli
						+ data[20]
						+ deli
						+ //
						data[21] + deli + data[22] + deli + data[23] + deli
						+ data[24] + deli + data[25].toString() + deli
						+ data[26] + deli + data[27] + deli + data[28] + deli
						+ data[29] + deli
						+ data[30]
						+ deli
						+ //
						data[31] + deli + data[32] + deli + data[33] + deli
						+ data[34] + deli + data[35].toString() + deli
						+ data[36] + deli + data[37] + deli + data[38] + deli
						+ data[39] + deli + data[40] + "\n");
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
		str += "따라서 이러한 변이형의 발견은 치료경과 관찰, Viral breakthrough 의 조기발견 및 치료방침을 결정하는데 유용하게 \n";
		str += "사용될 수 있습니다.";
		return str;
		// return null;
	}
}

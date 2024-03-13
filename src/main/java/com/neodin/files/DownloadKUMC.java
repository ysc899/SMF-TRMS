package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1
//! 제일병원 - 기본폼 + 알파

import java.io.File;
import java.util.StringTokenizer;

import jxl.Workbook;

import com.neodin.comm.Common;

public class DownloadKUMC extends ResultDownload {
	boolean isDebug = false;

	// boolean isData = true;
	public String ArraryResult[] = null;

	public DownloadKUMC() {
		initialize();
	}

	public DownloadKUMC(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public void closeDownloadFile() {
		if (!isDebug) {
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
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2012-04-09 오전 3:23:28)
	 * 
	 * @return java.lang.String
	 */
	public String getIGEA_Interpretation() {
		String Interpretation = "";
		Interpretation += "[ Interpretation ]\n";
		Interpretation += "========================================================================\n";
		Interpretation += " Nil	  TB Ag - Nil		   Mitogen - Nil	Result	\n";
		Interpretation += "[IU/ml]	 [IU/mL]		     [IU/ml]		\n";
		Interpretation += "========================================================================\n";
		Interpretation += "	  <0.35				≥ 0.5			\n";
		Interpretation += "≤ 8.0							Negative	\n";
		Interpretation += "	 ≥0.35 and < 25% of Nil	≥ 0.5			\n";
		Interpretation += "------------------------------------------------------------------------\n";
		Interpretation += "≤ 8.0	 ≥0.35 and < 25% of Nil	Any		Postitve	\n";
		Interpretation += "------------------------------------------------------------------------\n";
		Interpretation += "	  <0.35				<0.5			\n";
		Interpretation += "≤ 8.0							Indeterminate	\n";
		Interpretation += "	 ≥0.35 and < 25% of Nil	<0.5		\n";
		Interpretation += "------------------------------------------------------------------------\n";
		Interpretation += "  >8.0	    Any				Any		Indeterminate	\n";
		Interpretation += "========================================================================\n";
		return Interpretation;
	}

	public String[] getUnit(String unit) {
		String rlt[] = new String[] { "", "", "" };
		StringTokenizer st = new StringTokenizer(unit, ",");
		if (st.countTokens() != 3) {
			return new String[] { "", "", "" };
		} else {
			rlt[0] = st.nextToken();
			rlt[1] = st.nextToken();
			rlt[2] = st.nextToken();
		}
		return rlt;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2012-04-09 오전 3:20:45)
	 * 
	 * @return boolean
	 * @param gcd
	 *            java.lang.String
	 */
	public boolean isIGRA(String gcd) {
		if (gcd.equals("64257"))
			return true;
		return false;
	}

	public void makeDownloadFile() {
		row = 1;
		// row2 = 0;
		if (!isDebug) {
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				// wbremark = workbook.createSheet("Remark", 1);
				// String ArraryResult[] = null;
				// label = new jxl.write.Label(0, 0, "(재)씨젠의료재단 첫번재 ,두번째 Row -
				// 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
				// wbresult.addCell(label);
				ArraryResult = (new String[] { "병원구분", // 0
						"검체번호", // 1
						"처방순서", // 2
						"검사항목코드", // 3
						"검사결과", // 4
						"정상치상", // 5
						"정상치하", // 6
						"검사단위", // 7
						"검사특기사항", // 8
						"접수번호", // 9
						"검사코드", // 10
						"검체코드", // 11
						"특기사항", // 12
						"Comment", // 13
						"Method", // 14
						"Interpretation", // 15
						"소견", // 16
						"참고사항", // 17
						"기타1", // 18
						"기타2", // 19
						"기타3" // 20
				});
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
			} catch (Exception e) {
			}
		}
	}

	public void processingData(int cnt) {
		try {

			// !
//			boolean isNext = false;

			// !
			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
			String specNo[] = (String[]) getDownloadData().get("검체번호");
//			String chartNo[] = (String[]) getDownloadData().get("차트번호");

			// !
//			String patName[] = (String[]) getDownloadData().get("수신자명");
			String inspectCode[] = (String[]) getDownloadData().get("검사코드");
			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");

			// !
			String resultType[] = (String[]) getDownloadData().get("결과타입");
			String clientInspectCode[] = (String[]) getDownloadData().get(
					"병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
//			String age[] = (String[]) getDownloadData().get("나이");
//			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
//			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");

			// !
//			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");

			// !
//			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
//			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");

			// !
//			String inc[] = (String[]) getDownloadData().get("외래구분");

			// !
			String data[] = new String[21];
//			String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";
//			Vector vmast = new Vector();
			String lastData = "";
			int lastindex = 0;

			// !
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
//				isNext = true;

				// !
				while (lastData.equals(inspectCode[lastindex--].trim()
						.substring(0, 5))) {
					cnt--;
				}
			}

			// !
			String acd = "";
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				data[4] = "";
				try {
					acd = inspectCode[i].trim().substring(5);
					if (acd.equals("00"))
						continue;
				} catch (Exception rr) {
				}

				// !
				// if (rcvDate[i].equals("20120628") &&
				// rcvNo[i].equals("45566")) {
				// System.out.println("");
				// }
				// if (rcvNo[i].equals("45599")) {
				// System.out.println("");
				// }

				// !
				if (inspectCode[i].trim().length() == 7 && //
						(inspectCode[i].trim().equals("7125100")) || //
						(inspectCode[i].trim().equals("7125101")) || //
						(inspectCode[i].trim().equals("7125103")) || //
						(inspectCode[i].trim().equals("7125200")) || //
						(inspectCode[i].trim().equals("7125201")) || //
						(inspectCode[i].trim().equals("7125203")) || //
						(inspectCode[i].trim().equals("4102600")) || //
						(inspectCode[i].trim().equals("4102602")) || //
						(inspectCode[i].trim().equals("4102603")) || //
						(inspectCode[i].trim().equals("8137100")) || //
						(inspectCode[i].trim().equals("8137102")) || //
						(inspectCode[i].trim().equals("4137400")) || //		 	
						(inspectCode[i].trim().equals("4137402")) || //
						(inspectCode[i].trim().equals("4137403")) || //
						(inspectCode[i].trim().equals("0092303")) || //
						(inspectCode[i].trim().equals("0092403")) //

				) {
					continue;
				}
				// !
				data = new String[] { "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "" };
				if (hosCode[1].toString().trim().equals("24668")
						|| hosCode[1].toString().trim().equals("24669")
						|| hosCode[1].toString().trim().equals("24670")) {
					data[0] = "AA"; // 병원구분 안암
				} else if (hosCode[1].toString().trim().equals("24671")
						|| hosCode[1].toString().trim().equals("24672")
						|| hosCode[1].toString().trim().equals("24673")) {
					data[0] = "GR"; // 병원구분 구로
				} else if (hosCode[1].toString().trim().equals("24674")
						|| hosCode[1].toString().trim().equals("24675")
						|| hosCode[1].toString().trim().equals("24676")) {
					data[0] = "AS"; // 병원구분 안산
				}
				// !

//				String curDate = "";
//				String curNo = "";
				try {
					Long.parseLong(specNo[i].trim()); // 검체번호
					data[1] = specNo[i].trim(); // 검체번호
				} catch (Exception ee) {
					data[1] = specNo[i].trim(); // 검체번호
				}

				// !
				try {
					try {
						Long.parseLong(cutHrcvDateNumber(cns[i])[0]); // 처방순서
						data[2] = cutHrcvDateNumber(cns[i])[0]; // 처방순서
					} catch (Exception ee) {
						data[2] = cutHrcvDateNumber(cns[i])[0]; // 처방순서
					}
				} catch (Exception eee) {
					data[2] = ""; // 처방순서
				}
				data[3] = clientInspectCode[i]; // 검사항목코드
				// !
				// !
				String LHUnit[] = getUnit(unit[i].trim());
				try {
					Long.parseLong(LHUnit[0]); // 참고치 사한값
					data[6] = LHUnit[0]; // 참고치 사한값
				} catch (Exception ee) {
					data[6] = LHUnit[0]; // 참고치 사한값
				}
				// !
				try {
					Long.parseLong(LHUnit[1]); // 참고치 상한값
					data[5] = LHUnit[1]; // 참고치 상한값
				} catch (Exception ee) {
					data[5] = LHUnit[1]; // 참고치 상한값
				}
				// !
				try {
					Long.parseLong(LHUnit[2]); // 검사단위
					data[7] = LHUnit[2]; // 검사단위
				} catch (Exception ee) {
					data[7] = LHUnit[2]; // 검사단위
				}

				// !
				try {
					Long.parseLong(rcvNo[i].trim()); // 접수번호
					data[9] = rcvNo[i].trim(); // 접수번호
				} catch (Exception ee) {
					data[9] = rcvNo[i].trim(); // 접수번호
				}
				try {
					Long.parseLong(inspectCode[i].trim()); // 접수번호
					data[10] = inspectCode[i].trim(); // 접수번호
				} catch (Exception ee) {
					data[10] = inspectCode[i].trim(); // 접수번호
				}
				try {
					Long.parseLong(hosSamp[i].trim()); // 검체코드
					data[11] = hosSamp[i].trim(); // 검체코드
				} catch (Exception ee) {
					data[11] = hosSamp[i].trim(); // 검체코드
				}
				data[12] = ""; // 특기사항
				data[13] = ""; // Comment
				data[14] = ""; // Method
				data[15] = ""; // Interpretation
				data[16] = ""; // 소견
				data[17] = ""; // 참고사항

				try {
					Long.parseLong(rcvNo[i].trim()); // 접수일자
					data[18] = rcvNo[i].trim(); // 접수일자
				} catch (Exception ee) {
					data[18] = rcvNo[i].trim(); // 접수일자
				}

				// !
				data[19] = inspectName[i]; // 검사명
				try {
					Long.parseLong(hosSamp[i]); // 병원검체코드
					data[20] = hosSamp[i]; // 병원검체코드
				} catch (Exception ee) {
					data[20] = hosSamp[i]; // 병원검체코드
				}
				// ! 수치형
				if (resultType[i].trim().equals("C")) {
					if (inspectCode[i].trim().length() == 7
							&& (inspectCode[i].trim().substring(0, 5).equals(
									"71251")
									|| //
									inspectCode[i].trim().substring(0, 5)
											.equals("71252") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("81371") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("41026") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("41374") || //
									inspectCode[i].trim().substring(0, 5)
											.equals("00924") || //
							inspectCode[i].trim().substring(0, 5).equals(
									"00923"))) {
						// curDate = rcvDate[i];
						// curNo = rcvNo[i];
						// for (String thisTimeCode =
						// inspectCode[i++].trim().substring(0, 5);
						// thisTimeCode.equals(inspectCode[i].trim().substring(0,
						// 5)) && curDate.equals(rcvDate[i].trim()) &&
						// curNo.equals(rcvNo[i].trim());) {
						// if ((inspectCode[i].trim().substring(0,
						// 5).equals("71251") &&
						// inspectCode[i].trim().substring(5, 7).equals("02"))
						// || (inspectCode[i].trim().substring(0,
						// 5).equals("41374") &&
						// inspectCode[i].trim().substring(5, 7).equals("01")))
						// {
						data[4] = result[i]; // 검사결과2
						// !
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						// !

						try {
							Long.parseLong(getReferenceValueTrim2(remark)); // 리마크
																			// (코멘트)
							data[17] = getReferenceValueTrim2(remark); // 리마크
																		// (코멘트)
						} catch (Exception ee) {
							data[17] = getReferenceValueTrim2(remark); // 리마크
																		// (코멘트)
						}

						// !
						LHUnit = getUnit(unit[i].trim());
						try {
							Long.parseLong(LHUnit[0]); // 참고치 사한값
							data[6] = LHUnit[0]; // 참고치 사한값
						} catch (Exception ee) {
							data[6] = LHUnit[0]; // 참고치 사한값
						}
						// !
						try {
							Long.parseLong(LHUnit[1]); // 참고치 상한값
							data[5] = LHUnit[1]; // 참고치 상한값
						} catch (Exception ee) {
							data[5] = LHUnit[1]; // 참고치 상한값
						}
						// !
						try {
							Long.parseLong(LHUnit[2]); // 검사단위
							data[7] = LHUnit[2]; // 검사단위
						} catch (Exception ee) {
							data[7] = LHUnit[2]; // 검사단위
						}
						// }
						// if (++i == cnt)
						// break;
						// }
						// i--;
					} else {
						try {
							Long.parseLong(result[i]); // 검사결과
							data[4] = result[i]; // 검사결과
						} catch (Exception ee) {
							data[4] = result[i]; // 검사결과
						}
						if (data[4] == null || data[4].equals("null")) {
							data[4] = "";
						}

						// !
						remark[0] = inspectCode[i];
						remark[1] = lang[i];
						remark[2] = history[i];
						remark[3] = sex[i];
						// !

						try {
							Long.parseLong(getReferenceValueTrim2(remark)); // 리마크
																			// (코멘트)
							data[17] = getReferenceValueTrim2(remark); // 리마크
																		// (코멘트)
						} catch (Exception ee) {
							data[17] = getReferenceValueTrim2(remark); // 리마크
																		// (코멘트)
						}
						if (isIGRA(inspectCode[i].trim())) {
							data[17] = data[17] + "\n\n"
									+ getIGEA_Interpretation();
						}
					}
					// !문자형
				} else {
					try {
						Long.parseLong(getTextResultValueTrim2(hosCode[i],
								rcvDate[i], rcvNo[i], inspectCode[i])); // 문장형결과
																		// (리마크도
																		// 사용가능)
						data[8] = getTextResultValueTrim2(hosCode[i],
								rcvDate[i], rcvNo[i], inspectCode[i]); // 문장형결과
																		// (리마크도
																		// 사용가능)
						data[8] = data[8] != null ? data[8].toString().trim()
								: "";
					} catch (Exception ee) {
						data[8] = getTextResultValueTrim2(hosCode[i],
								rcvDate[i], rcvNo[i], inspectCode[i]); // 문장형결과
																		// (리마크도
																		// 사용가능)
						data[8] = data[8] != null ? data[8].toString().trim()
								: "";
					}
				}

				// !
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(rcvDate[i].trim())
								|| !kumdata[1].trim().equals(rcvNo[i].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[8] = "\n\n"
									+ getReamrkValueTrim2(hosCode[i],
											rcvDate[i], rcvNo[i], rmkCode[i]);
							data[8] = data[8] != null ? data[8].toString()
									.trim() : "";
							data[13] = "";
							kumdata[0] = rcvDate[i].trim();
							kumdata[1] = rcvNo[i].trim();
							kumdata[2] = rmkCode[i].trim();
						}
					} catch (Exception ee) {
					}
				else {
					remarkCode = "";
				}
				if (!isDebug) {
					if (!data[4].toString().trim().equals("별지참조")) {
						for (int k = 0; k < data.length; k++) {
							if (data[k] != null) {
								label = new jxl.write.Label(k, row,
										((data[k] == null || data[k].toString()
												.equals("")) ? "" : data[k]));
								wbresult.addCell(label);
							}
						}
					}
				}
				if (!data[4].toString().trim().equals("별지참조")) {
					row++;
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

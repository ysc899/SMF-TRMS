package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler

//대우의료재단
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

/**
 * processingData()에서 실제 엑셀을 만들며 
 * TODO 라고 친부분은 루틴하게 발생하는 부분에 대한 주석으로 바로 아래에 있는 예제코드를 복사하여 수정하면 된다.
 * [루틴업무]
 * TODO1. 대표검사코드만 노출해달라는 작업
 * TODO2. 부속검사 문장형태로 묶는작업
 * TODO3. 검사 결과 별지보고 처리 작업
 * TODO4. 부속검사 문장결과에 리마크 추가 작업
 */
public class DownloadDaewoo extends ResultDownload {
	boolean debug = false;
	public DownloadDaewoo() {
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
	public DownloadDaewoo(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public DownloadDaewoo(String id, String fdat, String tdat, Boolean isRewrite,String hakCd) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		setHakcd(hakCd);
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
	
	public void makeDownloadFile() {
		{
			row = getStsrtResultRow() - 1;
			row2 = 1;
			int i = 0;
			
			try {
				String ArraryResult[] = null;
				ArraryResult = (new String[] { "이름", "검체번호", "차트번호",
						"성별", "나이", "과", "처방코드", "검사명", "결과",
						"결과일", "코멘트"
						});
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
	}
	

	
	//TODO : 엑셀 만드는 메인 클레스 cdy
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
			
			//수정 발생 X
			//결과를 400개씩 갖어와서 엑셀에 쓰는 작업을 하는데 부속코드가 마지막에 있을 시 다음 엑셀에 표기되도록 작업 cdy 
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
				/**
				 * (s) 엑셀에 들어갈 데이터 생성
				 */
				String curDate = "";
				String curNo = "";
				data[0] = bdt[i]; // 걍~~
				data[1] = Common.cutZero(img[i]); // 내원번호
				data[2] = inc[i]; // 외래구분
				data[3] = ""; // 의뢰일자
				data[4] = specNo[i].trim(); // 검체번호
				
				//처방번호 구분자가 D인거 잘라서 생성 
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				}    
				
				//처방번호 구분자 ^인거 잘라서 생성
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
				data[11] = data[5]; // 여유필드

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

				data[30] = "11370319"; // 요양기관번호
				data[31] = rcvDate[i].trim(); // 접수일자
				data[32] = rcvNo[i].trim(); // 접수번호
				data[33] = inspectCode[i].trim(); // 검사코드
				data[34] = ""; // 단문결과

				data[35] = ""; // 문장결과
				data[36] = ""; // 수치+문장
				data[37] = ""; // 상태
				data[38] = ""; // 리마크
				
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


					
					
					data[37] = highLow[i].replace(".", " "); // 판정에 . 빼기
				
					// ! 여기서 부터
					// 문장형--------------------------------------------------------------------------------------------------------------------------------------------------------
				} else {
					boolean isTxtRltC = false;
			
					data[34] = ""; // 문자결과
					data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
					data[36] = data[35].trim();
					
									
					data[39] = ""; // 참고치
					data[37] = highLow[i]; // 결과상태
					
					int str_index = data[35].indexOf("면허");
					
					if (str_index > 10) {
						data[35] = data[35].substring(0, str_index);
						data[36] = data[35].trim();
					}
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
						
					} catch (Exception _ex) {
					}
				} else {
					remarkCode = "";
				}
			
				// !-------------------------------------------
				boolean isTxtRltA = false;
				
				// ! 참고치
				try {
					data[39] = data[39].trim();
				} catch (Exception yxx) {
				}
									
						
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
			_ex.printStackTrace();//엑셀생성 디버그 용도로 익셉션도 로그 작업 함 20160714
			setParameters(null);
		}
	}

	private boolean isMast(String[] inspectCode, int i) {
		return inspectCode[i].trim().substring(0, 5).equals("00687")||inspectCode[i].trim().substring(0, 5).equals("00688")||inspectCode[i].trim().substring(0, 5).equals("00689");
	}
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 5   Fields: 1

import java.io.File;

import jxl.Workbook;
import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.Common;

/**
 * 수정 : 사용 병원 (인구보건 - GPPFK01 , 25424 )
 * @author 최대열
 *
 */
public class DownloadPPFK extends ResultDownload {
	boolean debug = false;

	public DownloadPPFK() {
		// isDebug = false;
		initialize();
	}

	public DownloadPPFK(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	protected void initialize() {
		getDpc().put("병원리스트", new DpcMCALLSAA2());
		getDpc().put("결과다운해제", new DpcMCR03RM2W());
		getDpc().put("참고치", new DpcMCR03RMS6());
		getDpc().put("참고치2", new DpcMCR03RMS61());
		getDpc().put("리마크", new DpcMWR03RMT75());
//		getDpc().put("리마크", new DpcMCR03RMS75());
		getDpc().put("리마크2", new DpcMWR03RMT76());
//		getDpc().put("리마크2", new DpcMCR03RMS76());
		getDpc().put("결과포멧", new DpcMC999RM());
		getDpc().put("결과집계", new DpcMCR003RMR());
		getDpc().put("결과", new DpcMCR003RMR8());
		getDpc().put("문장결과", new DpcMCR003RMS8());
		getDpc().put("결과다운해제2", new DpcMCR03RM2W());
		getDpc().put("MC118RM", new DpcOfMC118RM());
		getDpc().put("MC118RMS1", new DpcOfMC118RMS1());
		getDpc().put("MCR003T11", new DpcMWR003T11());
//		getDpc().put("MCR003T11", new DpcMCR003T11());
		getDpc().put("결과집계CL", new DpcMWR003RTR());
//		getDpc().put("결과집계CL", new DpcMCR003T12());
		getDpc().put("결과CL", new DpcMWR003RTR9());
//		getDpc().put("결과CL", new DpcMCR003RTR9());
		getDpc().put("문장결과CL", new DpcMWR003RMT8());
//		getDpc().put("문장결과CL", new DpcMCR003RMT8());
		downloading();
	}
	
	
	protected void parsingDownloadData(DPCParameter dpcparameter) {
		if (!getDownloadData().isEmpty())
			getDownloadData().clear();
		try {
			int i = dpcparameter.getIntParm(33);
			getDownloadData().put("병원코드", Common.getArrayTypeData(dpcparameter.getStringParm(6), 5, i));
			getDownloadData().put("접수일자", Common.getArrayTypeData(dpcparameter.getStringParm(7), 8, i));
			getDownloadData().put("접수번호", Common.getArrayTypeData(dpcparameter.getStringParm(8), 5, i));
			getDownloadData().put("검체번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(9), i));
			getDownloadData().put("차트번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(10), i));
			getDownloadData().put("수신자명", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(11), i));
			getDownloadData().put("나이", Common.getArrayTypeData(dpcparameter.getStringParm(12), 6, i));
			getDownloadData().put("성별", Common.getArrayTypeData(dpcparameter.getStringParm(13), 1, i));
			getDownloadData().put("검사코드", Common.getArrayTypeData(dpcparameter.getStringParm(14), 7, i));
			getDownloadData().put("검사명", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(15), i));
			getDownloadData().put("일련번호", Common.getArrayTypeData(dpcparameter.getStringParm(16), 2, i));
			getDownloadData().put("결과", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(17), i));
			getDownloadData().put("결과상태", Common.getArrayTypeData(dpcparameter.getStringParm(18), 1, i));
			getDownloadData().put("리마크코드", Common.getArrayTypeData(dpcparameter.getStringParm(19), 7, i));
			getDownloadData().put("검사완료일", Common.getArrayTypeData(dpcparameter.getStringParm(20), 8, i));
			getDownloadData().put("처방일자", Common.getArrayTypeData(dpcparameter.getStringParm(21), 8, i));
			getDownloadData().put("이력", Common.getArrayTypeData(dpcparameter.getStringParm(22), 3, i));
			getDownloadData().put("언어", Common.getArrayTypeData(dpcparameter.getStringParm(23), 1, i));
			getDownloadData().put("보험코드", Common.getArrayTypeData(dpcparameter.getStringParm(24), 10, i));
			getDownloadData().put("결과타입", Common.getArrayTypeData(dpcparameter.getStringParm(25), 1, i));
			getDownloadData().put("외래구분", Common.getArrayTypeData(dpcparameter.getStringParm(26), 1, i));
			getDownloadData().put("병원검체코드", Common.getArrayTypeData(dpcparameter.getStringParm(27), 5, i));
			getDownloadData().put("병원검사코드", Common.getArrayTypeData(dpcparameter.getStringParm(28), 20, i));
			getDownloadData().put("요양기관번호", Common.getArrayTypeData(dpcparameter.getStringParm(29), 20, i));
			getDownloadData().put("이미지여부", Common.getArrayTypeData(dpcparameter.getStringParm(30), 8, i)); // 내원번호
			getDownloadData().put("주민번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(31),i));
			getDownloadData().put("참고치단위등", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(32), i));
			getDownloadData().put("처방번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(34), i));
		} catch (Exception _ex) {
		}
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


	public void makeDownloadFile() {

		row = 1;
		int i = 0;

		// !
		try {
			String ArraryResult[] = null;
			ArraryResult = (getExcelFieldNameArray());
			if (!debug) {
				workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				for (i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
			}
		} catch (Exception e) {
		}
	}

	public void processingData(int cnt) {
		try {

			boolean isNext = false;

			String curDate = "";
			String curNo = "";
			
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
			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");
			String inc[] = (String[]) getDownloadData().get("외래구분");

			String data[] = new String[45];
			String remark[] = new String[5];
			String remarkCode = "";
			int k = 0;
		
			for (int i = 0; i < cnt; i++) {

				data[12] = chartNo[i].trim(); //챠트번호
				data[13] = patName[i]; // 수진자명
				data[6] = clientInspectCode[i].trim(); // 병원검사코드(처방코드)
				data[7] = inspectName[i]; // 검사명(처방명)
				data[34] = ""; // 단문결과
				data[35] = ""; // 문장결과
				data[36] = ""; // 수치+문장
				data[37] = highLow[i].trim(); //판정
				data[38] = ""; // 리마크
				data[4] = specNo[i].trim(); // 검체번호
				data[17] ="";
				data[18] ="";
				String tmp[] = securityNo[i].split(",");
				if(tmp.length==1)
				{
					data[17] = tmp[0].trim(); // 과명
				}
				else if(tmp.length>=2)
				{
					data[17] = tmp[0].trim(); // 과명
					data[18] = tmp[1].trim(); // 병동
				}

				try {
				remark[0] = inspectCode[i];
				remark[1] = lang[i];
				remark[2] = history[i];
				remark[3] = sex[i];
				data[39] = getReferenceValue(remark);
			} catch (Exception e) {
				data[39] = "";
			}
				
				if (hosCode[i].trim().equals("22862") && !(data[6].equals("CZ212")||data[6].equals("CZ211")||data[6].equals("C4212")||data[6].equals("C3520")
						||data[6].equals("C3270")||data[6].equals("CY691006"))) { // 단독
					continue;
				}
//				if(!(hosCode[i].trim().equals("22862") &&(inspectCode[i].trim().equals("41338")||inspectCode[i].trim().equals("41381")
//						||inspectCode[i].trim().equals("41382")||inspectCode[i].trim().equals("41383")
//						||inspectCode[i].trim().equals("41121")||inspectCode[i].trim().equals("41431"))))
//				{
//					continue;
//				}
				
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
					
					if (!isTxtRltB
							&& hosCode[i].trim().equals("22862") && inspectCode[i].trim().substring(0, 5).equals("71139")) { // 단독
						data[35] = getDivide() + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
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
					if(hosCode[i].trim().equals("22862") &&(inspectCode[i].trim().equals("41338")||inspectCode[i].trim().equals("41381")
								||inspectCode[i].trim().equals("41382")||inspectCode[i].trim().equals("41383")
								||inspectCode[i].trim().equals("41121")||inspectCode[i].trim().equals("41431")))
					{
						data[34] = getResultMSAFP(new String[] { rcvDate[i], rcvNo[i],inspectCode[i]  });
					}
				} else {
					boolean isTxtRltC = false;

					data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					data[36] = data[35].trim();
				}

				
				if (rmkCode[i].trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							data[38] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				} else {
					remarkCode = "";
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
			setParameters(null);
		}
	}
}

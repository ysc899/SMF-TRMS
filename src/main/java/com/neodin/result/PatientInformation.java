package com.neodin.result;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 33   Fields: 13

import java.util.StringTokenizer;
import java.util.Vector;

import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;
import com.neodin.rpg.DpcOfMC106RMS2;
import com.neodin.rpg.DpcOfMC107RM1;
import com.neodin.rpg.DpcOfMC107RMS2;
import com.neodin.rpg.DpcOfMC176RM;
import com.neodin.rpg.DpcOfMC177RM2;
import com.neodin.rpg.DpcOfMC181RM2;
import com.neodin.rpg.DpcOfMC306RMS1;
import com.neodin.rpg.DpcOfMC319RMS4;
import com.neodin.rpg.DpcOfMC352RM;
import com.neodin.rpg.DpcOfMC352RMS1;
import com.neodin.rpg.DpcOfMC352RMS2;
import com.neodin.rpg.DpcOfMC356RM;
import com.neodin.rpg.DpcOfMC600RM1;
import com.neodin.rpg.DpcOfMC600RM2;
import com.neodin.rpg.DpcOfMC600RM3;
import com.neodin.rpg.DpcOfMC600RM6;
import com.neodin.rpg.DpcOfMC600RM7;
import com.neodin.rpg.DpcOfMCGETGNAM;
import com.neodin.rpg.DpcOfMCPATATL;
import com.neodin.rpg.DpcOfMCPATBAS;
import com.neodin.rpg.DpcOfMCPATBAS1;
import com.neodin.rpg.DpcOfMCPATBAS2;
import com.neodin.rpg.DpcOfMCR03RMS5;
import com.neodin.rpg.DpcOfTC310RM;

public class PatientInformation {

	private DPCParameter parm;

	private AbstractDpc dpc;

	final char pattern = '\t';

	private String mArrayAcd[];

	private String mArrayName[];

	private String mArrayResult[];

	private String mArrayResult1[];

	private String mArrayResult2[];

	// private String mArrayResult3[];

	// private String mArrayResultText[];

	private String mArrayResultSts[];

	private String mArrayFhl[];

	private String mArrayRtn[];

	private String m_aPatientinfo[];

	private String m_aResult[];

	private Vector m_vResult;

	private int gum[] = { 5, 9, 13, 6, 10, 14 };

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2006-08-31 오후 3:58:11) 작 성 자: 조남식
	 * 
	 * 
	 * 본사 액상세포
	 * 
	 */
	public String[] getMonoprep(String date, String jno, String gcd) {

		// String[] ArrayHead = null;
		// String[] ArrayResult = null;
		// String[] ArrayLen = null;
		Vector rmk = new Vector();
		// !
		try {
			String remark = "";
			dpc = new DpcOfMC106RMS2();
			if (dpc.processDpc(new String[] { "NML", date, jno, gcd })) {
				parm = dpc.getParm();

				// !
				int cnt = parm.getIntParm(8);

				String RemarkResult[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(6), cnt);
				String RemarkLen[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(7), cnt);
				for (int i = 1; i < cnt; i++)
					if (RemarkLen[i].toString().equals("99")) {
						remark = remark + Common.trimRight(RemarkResult[i]);
					} else {
						remark = remark + RemarkResult[i];
						// if (remark.trim().startsWith("<")) {
						// rmk.addElement(" ");
						// }
						rmk.addElement(remark);
						remark = "";
					}

				if (!remark.equals(""))
					rmk.addElement(remark);

			}
		} catch (Exception e) {
			return (new String[] { "검사결과를  불러오는 중에 오류가 발행하였습니다. 정보지원실로 문의 바랍니다" });
		}
		if (rmk.size() > 0) {
			String rtnData[] = new String[rmk.size()];
			for (int i = 0; i < rmk.size(); i++)
				rtnData[i] = rmk.elementAt(i).toString();

			return rtnData;
		} else {
			return (new String[] { "검사결과를  불러오는 중에 오류가 발행하였습니다. 정보지원실로 문의 바랍니다" });
		}
	}

	public PatientInformation() {
		parm = null;
		dpc = null;
	}

	public String[] getPatientResult2(String Date, String No, String Gcode) {
		int cnt = 0;
		mArrayRtn = new String[11];
		try {
			dpc = new DpcOfMC600RM7();
			if (dpc.processDpc(new String[] { "NML", Date, No, Gcode })) {
				parm = dpc.getParm();
				cnt = parm.getIntParm(18);
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, cnt);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), cnt);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), cnt);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, cnt);
				mArrayRtn[0] = parm.getStringParm(8);
				mArrayRtn[1] = parm.getStringParm(13);
				mArrayRtn[2] = parm.getStringParm(14).trim() + " " + parm.getStringParm(15);
				mArrayRtn[3] = parm.getStringParm(16).trim() + " " + parm.getStringParm(17);
				mArrayRtn[4] = parm.getStringParm(19);
				mArrayRtn[5] = parm.getStringParm(20);
				mArrayRtn[6] = mArrayName[0];
				for (int i = 0; i < cnt; i++) {
					mArrayRtn[7 + i] = mArrayResult[i];
					if (i == 3)
						break;
				}

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public String[] getPatientInformationNew(String Date, String No, String Gcode) {
		String str[] = new String[33];
		// 0 : 의뢰기관명
		// 1 : 요양기관번호
		// 2 : 병원코드
		// 3 : 수진자명
		// 4 : 진료과명
		// 5 : 검체1
		// 6 : 검체4
		// 7 : 주민번호
		// 8 : 병동
		// 9 : 검체2
		// 10 : 검체5
		// 11 : 차트번호
		// 12 : 담당의사
		// 13 : 검체3
		// 14 : 검체4
		// 15 : 나이
		// 16 : 성별
		// 17 : 인종
		// 18 :채취일시
		// 19 : 검사보고일시
		// 20 : 신장
		// 21 : 체중
		// 22 : 임신주일
		// 23 : 접수일자
		// 24 : 접수번호
		// 25 : 기타사항
		// 26 : 검사시행일
		// 27 : 총뇨량
		// 28 : 의뢰기관주소
		// 29 : 임상소견

		try {
			dpc = new DpcOfMCPATBAS2();
			if (dpc.processDpc(new String[] { "NML", Date, No })) {
				parm = dpc.getParm();

				// !
				str[0] = parm.getStringParm(4).trim(); // 병원명
				str[1] = parm.getStringParm(5).trim(); // 요양기관번호
				str[2] = parm.getStringParm(3).trim(); // 병원코드

				// !
				str[3] = parm.getStringParm(10).trim(); // 환자명
				str[4] = parm.getStringParm(7).trim(); // 과명
				str[5] = ""; // 검체 1
				str[6] = ""; // 검체 4

				// !
				str[7] = parm.getStringParm(11).trim(); // 주민번호
				str[8] = parm.getStringParm(8).trim(); // 병동명
				str[9] = ""; // 검체 2
				str[10] = ""; // 검체 5

				// !
				str[11] = parm.getStringParm(6).trim(); // 차트번호
				str[12] = parm.getStringParm(9).trim(); // 의사이름
				str[13] = ""; // 검체 3
				str[14] = ""; // 검체 6

				// !
				str[15] = parm.getStringParm(12).trim().equals("0") ? "" : "    " + parm.getStringParm(12).trim(); // 나이
				str[16] = parm.getStringParm(13).trim().equals("") ? "" : "    " + parm.getStringParm(13).trim(); // 성별
				str[17] = ""; // 인종
				str[18] = ""; // 채취일자
				str[19] = ""; // 검사보고일자

				// !
				str[20] = parm.getStringParm(20).trim().indexOf(".000") > -1 ? parm.getStringParm(20).trim()
						.substring(0, parm.getStringParm(20).trim().indexOf(".000")) : parm.getStringParm(20).trim();
				// 신장
				str[21] = parm.getStringParm(14).trim().equals("0.00") ? "" : (parm.getStringParm(14).trim().indexOf(".00") > -1 ? parm
						.getStringParm(14).trim().substring(0, parm.getStringParm(14).trim().indexOf(".00")) : parm.getStringParm(14).trim());
				// 체중

				str[22] = ""; // 임신주수
				str[23] = Common.getDaySeperate(Date); // 접수일자
				str[24] = Integer.parseInt(No) + ""; // 접수번호

				// !
				str[25] = parm.getStringParm(15).trim(); // 기타
				str[26] = ""; // 시행일자
				// 총뇨량
				// str[27]= parm.getStringParm(19).trim() + "";

				str[27] = parm.getStringParm(19).trim().equals("0.00") ? "" : (parm.getStringParm(19).trim().indexOf(".00") > -1 ? parm
						.getStringParm(19).trim().substring(0, parm.getStringParm(19).trim().indexOf(".00")) : parm.getStringParm(19).trim());

				// !
				str[28] = "";
				// 주소
				str[29] = "";
				// 소견
				// !
				str[30] = parm.getStringParm(16).trim();
				// 사무소
				str[31] = parm.getStringParm(17).trim();
				// 생년월일
				str[32] = parm.getStringParm(18).trim();
				// 담당자
			} else {
				return null;
			}
			dpc = new DpcOfMCPATATL();
			if (dpc.processDpc(new String[] { "NML", Date, No })) {
				parm = dpc.getParm();
				str[22] += parm.getStringParm(4).trim() != null ? parm.getStringParm(4).trim() + "    " : "      ";
				// 임신주수
				str[22] += parm.getStringParm(6).trim() != null ? parm.getStringParm(6).trim() : "      ";
				// 임신주수
				if (str[22].trim().equals("")) {
					str[22] += parm.getStringParm(5).trim() != null ? parm.getStringParm(5).trim() + "    " : "      ";
					// 임신주수
					str[22] += parm.getStringParm(7).trim() != null ? parm.getStringParm(7).trim() : "      ";
					// 임신주수
				}
			}
			dpc = new DpcOfMC181RM2();
			if (dpc.processDpc(new String[] { "NML", Date, No, Gcode })) {
				parm = dpc.getParm();
				String ArraryML[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(5).trim());
				// 검체량
				String ArraryData[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(7).trim());
				// 검체명
				try {
					String gdt = Common.getDaySeperate(parm.getStringParm(6).trim()); // 채취일자
					if (!gdt.equals("") && gdt.length() == 10)
						str[18] = gdt;
				} catch (Exception _ex) {
				}
				if (str[27] == null || str[27].trim().equals("") || str[27].trim().equals("0.00")) {
					for (int i = 0; i < ArraryML.length; i++) {
						// str[27 + i]=
						// Integer.parseInt(ArraryML[i].toString().trim()) + "";

						str[27] = ArraryML[i].toString().trim().equals("0.00") ? ""
								: (ArraryML[i].toString().trim().indexOf(".00") > -1 ? ArraryML[i].toString().trim()
										.substring(0, ArraryML[i].toString().trim().indexOf(".00")) : ArraryML[i].toString().trim());

						if (i == 0)
							break;
					}
				}
				for (int i = 0; i < ArraryData.length; i++) {
					str[5 + i] = ArraryData[i];
					if (i == 0)
						break;
				}

			}
		} catch (Exception _ex) {
			return str;
		}
		return str;
	}

	public String[] getMethods(String s, String s1, String s2, String s3) {
		try {
			dpc = new DpcOfMC319RMS4();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				return Common.getArrayTypeData_CheckLast(parm.getStringParm(5).trim());
			}
		} catch (Exception _ex) {
			return null;
		}
		return null;
	}

	public String[] getPatientResultMast(String s, String s1, String s2) {
		// boolean flag = false;
		try {
			dpc = new DpcOfMC600RM6();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				mArrayRtn = new String[i];
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				for (int j = 0; j < mArrayName.length; j++)
					mArrayRtn[j] = mArrayName[j] + "|" + mArrayResult[j] + "|"
							+ callDpcMC177RM2(new String[] { s, s1, s2, mArrayAcd[j], mArrayFhl[j] });

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public String getReferenceRangeEP(String s, String s1, String s2, String s3) {
		String s4 = "";
		double d = 0.0D;
		double d1 = 0.0D;
		try {
			dpc = new DpcOfMC176RM();
			if (dpc.processDpc(new String[] { s, s1, s2, s3 })) {
				parm = dpc.getParm();
				d = parm.getDoubleParm(4);
				d1 = parm.getDoubleParm(5);
			}
		} catch (Exception _ex) {
			return "";
		}
		if (d == 0.0D && d1 == 0.0D)
			return "";
		s4 = d + " - " + d1;
		if (s2.equals("00309")) {
			if (s4.trim().equals("3.4 - 5.6"))
				s4 = s4.trim() + " (52.3-66.0 %)";
			if (s4.trim().equals("0.2 - 0.6"))
				s4 = s4.trim() + " (3.3-7.0 %)";
			if (s4.trim().equals("0.4 - 1.0"))
				s4 = s4.trim() + " (6.3-11.7 %)";
			if (s4.trim().equals("0.5 - 1.2"))
				s4 = s4.trim() + " (7.8-14.3 %)";
			if (s4.trim().equals("0.7 - 1.7"))
				s4 = s4.trim() + " (11.1-20.4 %)";
		}
		return s4;
	}

	public String getReferenceRangeEP(String s, String s1, String s2, String s3, String s4) {
		double d = getDouble(s4);
		double d1 = 0.0D;
		double d2 = 0.0D;
		try {
			dpc = new DpcOfMC176RM();
			if (dpc.processDpc(new String[] { s, s1, s2, s3 })) {
				parm = dpc.getParm();
				d1 = parm.getDoubleParm(4);
				d2 = parm.getDoubleParm(5);
			}
		} catch (Exception _ex) {
			return "";
		}
		if (d1 <= d && d <= d2)
			return "";
		if (d < d1)
			return "L";
		if (d > d2)
			return "H";
		else
			return "";
	}

	public String[] getResultRemarkNew(String s, String s1, String s2, String s3) {
		Vector vector = new Vector();
		try {
			String s4 = "";
			dpc = new DpcOfMC107RMS2();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(6);
				String as1[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				String as2[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(8), i);
				for (int k = 1; k < i; k++)
					if (as2[k].toString().equals("99")) {
						s4 = s4 + as1[k];
					} else {
						s4 = s4 + as1[k];
						vector.addElement(s4);
						s4 = "";
					}

				if (!s4.equals(""))
					vector.addElement(s4);
			}
		} catch (Exception _ex) {
			return (new String[] { "리마크를 불러오는 중에 오류가 발행하였습니다. 정보지원실로 문의 바랍니다" });
		}
		if (vector.size() > 0) {
			String as[] = new String[vector.size()];
			for (int j = 0; j < vector.size(); j++)
				as[j] = vector.elementAt(j).toString();

			return as;
		} else {
			return null;
		}
	}

	public String[] getResultRemarkNoTrim(String s, String s1, String s2, String s3) {
		Vector vector = new Vector();
		try {
			int i = 0;
			boolean flag = false;
			String s4 = "";
			dpc = new DpcOfMC107RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				String as1[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5).trim(), parm.getIntParm(6));
				for (int k = 1; k < as1.length; k++)
					if (as1[k].trim().length() == 0) {
						if (++i == 1) {
							vector.addElement(s4);
							s4 = "";
							flag = true;
						}
					} else {
						i = 0;
						if (!flag && as1[k].length() < 40 && as1[k].endsWith(".") && as1[k].startsWith(".")) {
							s4 = s4 + " " + as1[k];
							flag = true;
						} else {
							vector.addElement(s4 + " " + as1[k]);
							s4 = "";
							flag = false;
						}
					}

				if (!s4.trim().equals(""))
					vector.addElement(s4);
			}
		} catch (Exception _ex) {
			return (new String[] { "리마크를 불러오는 중에 오류가 발행하였습니다. 정보지원실로 문의 바랍니다" });
		}
		if (vector.size() > 0) {
			String as[] = new String[vector.size()];
			for (int j = 0; j < vector.size(); j++)
				as[j] = vector.elementAt(j).toString();

			return as;
		} else {
			return null;
		}
	}

	private synchronized String callDpcMC177RM2(String as[]) {
		try {
			dpc = new DpcOfMC177RM2();
			if (dpc.processDpc(as)) {
				parm = dpc.getParm();
				return parm.getStringParm(5).substring(0, 30).replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').replace('\f', ' ');
			}
		} catch (Exception _ex) {
			return "";
		}
		return "";
	}

	// private synchronized void callDpcMC306(String as[]) {
	// try {
	// dpc = new DpcOfMC306RMS1();
	// if (dpc.processDpc(as)) {
	// parm = dpc.getParm();
	// int i = parm.getIntParm(15);
	// Common.getArrayTypeData(parm.getStringParm(6), 5, i);
	// Common.getArrayTypeData(parm.getStringParm(7), 2, i);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(8), i);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(9), i);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(10), i);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(14), i);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(11), i);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(12), 1);
	// Common.getArrayTypeData_CheckLast(parm.getStringParm(13), 3);
	// }
	// } catch (Exception _ex) {
	// }
	// }

	private synchronized String cutItemName(String s) {
		if (s.equals(""))
			return "";
		StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
		String as[] = new String[stringtokenizer.countTokens()];
		int i = 0;
		while (stringtokenizer.hasMoreTokens())
			as[i++] = stringtokenizer.nextToken().toString().trim();
		return as[as.length - 1];
	}

	public String[] getClinicRemark(String s, String s1, String s2) {
		try {
			dpc = new DpcOfMC356RM();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				return Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(4).trim(), parm.getIntParm(5));
			}
		} catch (Exception _ex) {
			return null;
		}
		return null;
	}

	private double getDouble(String s) {
		if (s.equals(""))
			return 0.0D;
		else
			return Double.valueOf(s).doubleValue();
	}

	public String getGeneticKaryotype(String s, String s1, String s2, String s3, String s4) {
		try {
			dpc = new DpcOfMC352RMS2();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3, s4 })) {
				parm = dpc.getParm();
				return parm.getStringParm(6).trim();
			} else {
				return "";
			}
		} catch (Exception _ex) {
			return "";
		}
	}

	public Vector getGeneticMethods(String s, String s1, String s2, String s3) {
		Vector vector = new Vector();
		try {
			dpc = new DpcOfMC352RM();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(9);
				Common.getArrayTypeData(parm.getStringParm(5).trim(), 1, i);
				Common.getArrayTypeData(parm.getStringParm(6).trim(), 2, i);
				String as[] = Common.getArrayTypeData(parm.getStringParm(7).trim(), 2, i);
				String as1[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(8).trim());
				for (int j = 0; j < i; j++)
					if (as[j].trim().equals("20"))
						vector.addElement(as1[j].trim());

			}
		} catch (Exception _ex) {
			return vector;
		}
		return vector;
	}

	public String[] getGeneticResult(String s, String s1, String s2) {
		// boolean flag = false;
		mArrayRtn = new String[12];
		try {
			dpc = new DpcOfMC600RM3();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				mArrayRtn[0] = parm.getStringParm(8);
				mArrayRtn[1] = parm.getStringParm(13);
				mArrayRtn[2] = parm.getStringParm(14).trim() + " " + parm.getStringParm(15);
				mArrayRtn[3] = parm.getStringParm(16).trim() + " " + parm.getStringParm(17);
				mArrayRtn[4] = parm.getStringParm(19);
				mArrayRtn[5] = parm.getStringParm(20);
				mArrayRtn[6] = parm.getStringParm(21);
				mArrayRtn[7] = mArrayName[0];
				for (int j = 0; j < i; j++) {
					mArrayRtn[8 + j] = mArrayResult[j];
					if (j == 3)
						break;
				}

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public String getGeneticTitle(String s, String s1, String s2) {
		// Object obj = null;
		try {
			// DetailCodeData detailcodedata =
			// DetailCodeMgmt.getDetailCode("CLIC", "CGAA", s1);
			// return detailcodedata.getName(2) + " [" + s.substring(0, 4) + "-"
			// + s2.substring(0, s2.length() - 3) + "-" +
			// s2.substring(s2.length() - 3) + "]";
			return "PatientInfo클래스에서 getGeneticTitle메소드 구현해야";

		} catch (Exception _ex) {
			return "";
		}
	}

	public String getGumsaName(String s) {
		try {
			dpc = new DpcOfMCGETGNAM();
			if (dpc.processDpc(new String[] { s })) {
				parm = dpc.getParm();
				return parm.getStringParm(2);
			}
		} catch (Exception _ex) {
		}
		return "";
	}

	public String[] getImagePath(String s, String s1, String s2) {
		dpc = new DpcOfMCR03RMS5();
		if (dpc.processDpc(new String[] { "NML", s, s1, s2, "" })) {
			parm = dpc.getParm();
			try {
				return Common.getArrayTypeData_CheckLast(parm.getStringParm(5), parm.getIntParm(6));
			} catch (Exception _ex) {
				return null;
			}
		} else {
			return null;
		}
	}

	public Vector getImages(String s, String s1, String s2) {
		Vector vector = null;
		dpc = new DpcOfMCR03RMS5();
		if (dpc.processDpc(new String[] { "NML", s, s1, s2, "" })) {
			parm = dpc.getParm();
			try {
				String as[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(5), parm.getIntParm(6));
				vector = new Vector();

				for (int i = 0; i < as.length; i++) {
					vector.addElement(as[i].trim());
				}
				if (s2.equals("61028")) {
					vector.addElement("URL:정상이미지");
				}

			} catch (Exception _ex) {
				return vector;
			}
		}
		return vector;
	}

	public String[] getMethods(String s, String s1, String s2) {
		try {
			dpc = new DpcOfMC319RMS4();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				return Common.getArrayTypeData_CheckLast(parm.getStringParm(4).trim());
			}
		} catch (Exception _ex) {
			return null;
		}
		return null;
	}

	public String[] getPatientGeneticResult(String s, String s1, String s2, String s3, String s4) {
		String as[] = new String[7];
		try {
			dpc = new DpcOfMC352RMS1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3, s4 })) {
				parm = dpc.getParm();
				as[0] = parm.getStringParm(6).trim();
				as[1] = parm.getStringParm(7).trim();
				as[2] = parm.getStringParm(8).trim();
				as[3] = parm.getStringParm(9).trim();
				as[4] = parm.getStringParm(10).trim();
				as[5] = parm.getStringParm(11).trim();
				as[6] = parm.getStringParm(12).trim();
			}
		} catch (Exception _ex) {
			return as;
		}
		return as;
	}

	public String getPatientGname(String s) {
		try {
			dpc = new DpcOfMCGETGNAM();
			if (dpc.processDpc(new String[] { s })) {
				parm = dpc.getParm();
				return parm.getStringParm(2);
			}
		} catch (Exception _ex) {
			return "";
		}
		return "";
	}

	public String[] getPatientInfo(String s, String s1, String s2) {
		m_aPatientinfo = new String[30];
		try {
			dpc = new DpcOfMCPATBAS();
			if (dpc.processDpc(new String[] { "NML", s, s1 })) {
				parm = dpc.getParm();
				m_aPatientinfo[0] = parm.getStringParm(4).trim();
				m_aPatientinfo[1] = parm.getStringParm(5).trim();
				m_aPatientinfo[2] = "";
				m_aPatientinfo[3] = parm.getStringParm(10).trim();
				m_aPatientinfo[4] = parm.getStringParm(7).trim();
				m_aPatientinfo[5] = "";
				m_aPatientinfo[6] = "";
				m_aPatientinfo[7] = parm.getStringParm(11).trim();
				m_aPatientinfo[8] = parm.getStringParm(8).trim();
				m_aPatientinfo[9] = "";
				m_aPatientinfo[10] = "";
				m_aPatientinfo[11] = parm.getStringParm(6).trim();
				m_aPatientinfo[12] = parm.getStringParm(9).trim();
				m_aPatientinfo[13] = "";
				m_aPatientinfo[14] = "";
				m_aPatientinfo[15] = parm.getStringParm(12).trim().equals("0") ? "" : "    " + parm.getStringParm(12).trim();
				m_aPatientinfo[16] = parm.getStringParm(13).trim().equals("") ? "" : "    " + parm.getStringParm(13).trim();
				m_aPatientinfo[17] = "";
				m_aPatientinfo[18] = Common.getDaySeperate(s);
				m_aPatientinfo[19] = "";
				m_aPatientinfo[20] = "";
				m_aPatientinfo[21] = parm.getStringParm(14).trim().equals("0.00") ? "" : "  " + parm.getStringParm(14).trim();
				m_aPatientinfo[22] = "";
				m_aPatientinfo[23] = Common.getDaySeperate(s);
				m_aPatientinfo[24] = Common.cutZero(s1);
				m_aPatientinfo[25] = "";
				m_aPatientinfo[26] = "";
				m_aPatientinfo[27] = parm.getStringParm(15).trim();
				m_aPatientinfo[28] = parm.getStringParm(18).trim() + " " + parm.getStringParm(16).trim();
				m_aPatientinfo[29] = "";
				dpc = new DpcOfMCPATATL();
				if (dpc.processDpc(new String[] { "NML", s, s1 })) {
					parm = dpc.getParm();
					m_aPatientinfo[22] += parm.getStringParm(6).trim() == null ? "      " : parm.getStringParm(6).trim() + "    ";
					m_aPatientinfo[22] += parm.getStringParm(7).trim() == null ? "      " : parm.getStringParm(7).trim();
				}
				dpc = new DpcOfMC181RM2();
				if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
					parm = dpc.getParm();
					String as[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(7).trim());
					try {
						String s3 = Common.getDaySeperate(parm.getStringParm(6).trim());
						if (!s3.equals("") && s3.length() == 10)
							m_aPatientinfo[18] = s3;
					} catch (Exception _ex) {
					}
					for (int i = 0; i < as.length; i++) {
						m_aPatientinfo[gum[i]] = as[i];
						if (i == 1)
							break;
					}

				}
			}
		} catch (Exception _ex) {
			return null;
		}
		return m_aPatientinfo;
	}

	public String[] getPatientInformation(String s, String s1, String s2) {
		String as[] = new String[30];
		try {
			dpc = new DpcOfMCPATBAS1();
			if (dpc.processDpc(new String[] { "NML", s, s1 })) {
				parm = dpc.getParm();
				as[0] = parm.getStringParm(4).trim();
				as[1] = parm.getStringParm(5).trim();
				as[2] = "";
				as[3] = parm.getStringParm(10).trim();
				as[4] = parm.getStringParm(7).trim();
				as[5] = "";
				as[6] = "";
				as[7] = parm.getStringParm(11).trim();
				as[8] = parm.getStringParm(8).trim();
				as[9] = parm.getStringParm(19).trim().equals("0.00") ? "" : "       " + parm.getStringParm(19).trim();
				as[10] = parm.getStringParm(6).trim();
				as[11] = parm.getStringParm(9).trim();
				as[12] = "";
				as[13] = parm.getStringParm(12).trim().equals("0") ? "" : "    " + parm.getStringParm(12).trim();
				as[14] = parm.getStringParm(13).trim().equals("") ? "" : "    " + parm.getStringParm(13).trim();
				as[15] = "";
				as[16] = Common.getDaySeperate(s);
				as[17] = "";
				as[18] = "";
				as[19] = parm.getStringParm(14).trim().equals("0.00") ? "" : "  " + parm.getStringParm(14).trim();
				as[20] = "";
				as[21] = Common.getDaySeperate(s);
				try {
					as[22] = Integer.parseInt(s1) + "";
				} catch (Exception _ex) {
					as[22] = s1;
				}
				as[23] = "";
				as[24] = parm.getStringParm(15).trim();
				as[25] = "";
				as[26] = "";
				as[27] = parm.getStringParm(16).trim();
				as[28] = parm.getStringParm(18).trim();
				as[29] = "";
			} else {
				return null;
			}
			dpc = new DpcOfMCPATATL();
			if (dpc.processDpc(new String[] { "NML", s, s1 })) {
				parm = dpc.getParm();
				as[20] += parm.getStringParm(6).trim() == null ? "      " : parm.getStringParm(6).trim() + "    ";
				as[20] += parm.getStringParm(7).trim() == null ? "      " : parm.getStringParm(7).trim();
			}
			dpc = new DpcOfMC181RM2();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				String as1[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(7).trim());
				try {
					String s3 = Common.getDaySeperate(parm.getStringParm(6).trim());
					if (!s3.equals("") && s3.length() == 10)
						as[16] = s3;
				} catch (Exception _ex) {
				}
				for (int i = 0; i < as1.length; i++) {
					as[5 + i] = as1[i];
					if (i == 1)
						break;
				}

			}
		} catch (Exception _ex) {
			return as;
		}
		return as;
	}

	public String[][] getPatientParasite(String s, String s1, String s2) {
		try {
			dpc = new DpcOfMC306RMS1();
			if (dpc.processDpc(new String[] { "NML", "2", s, s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(15);
				Common.getArrayTypeData(parm.getStringParm(6), 5, i);
				Common.getArrayTypeData(parm.getStringParm(7), 2, i);
				String as[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(8), i);
				String as1[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(9), i);
				String as2[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(10), i);
				String as3[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(14), i);
				String as4[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(11), i);
				String s3 = parm.getStringParm(12);
				String s4 = parm.getStringParm(13);
				String as5[][] = new String[2][4];
				as5[0][0] = s3 + "  " + s4;
				as5[1][0] = "";
				as5[1][1] = "";
				as5[1][2] = "";
				as5[1][3] = "";
				for (int j = 0; j < i; j++)
					as5[1][j] += as1[j].toString().trim()
							+ (as1[j].toString().trim().equals("") ? "*" : as1[j].toString().trim().equals(as[j].toString().trim()) ? "" : " [ "
									+ as[j].toString().trim() + " ] ") + "|" + (as3[j].toString().trim().equals("") ? "*" : as3[j].toString().trim())
							+ "|" + (as2[j].toString().trim().equals("") ? "*" : as2[j].toString().trim()) + "|"
							+ (as4[j].toString().trim().equals("") ? "*" : as4[j].toString().trim());

				return as5;
			} else {
				return null;
			}
		} catch (Exception _ex) {
			return null;
		}
	}

	public String[] getPatientResult(String s, String s1, String s2) {
		// boolean flag = false;
		mArrayRtn = new String[11];
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				mArrayRtn[0] = parm.getStringParm(8);
				mArrayRtn[1] = parm.getStringParm(13);
				mArrayRtn[2] = parm.getStringParm(14).trim() + " " + parm.getStringParm(15);
				mArrayRtn[3] = parm.getStringParm(16).trim() + " " + parm.getStringParm(17);
				mArrayRtn[4] = parm.getStringParm(19);
				mArrayRtn[5] = parm.getStringParm(20);
				mArrayRtn[6] = mArrayName[0];
				for (int j = 0; j < i; j++) {
					mArrayRtn[7 + j] = mArrayResult[j];
					if (j == 3)
						break;
				}

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public String[] getPatientResultEP(String s, String s1, String s2) {
		// boolean flag = false;
		try {
			dpc = new DpcOfMC600RM2();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				mArrayRtn = new String[7 + i * 6];
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult1 = Common.getArrayTypeData_CheckLast(parm.getStringParm(21), i);
				mArrayResult2 = Common.getArrayTypeData_CheckLast(parm.getStringParm(22), i);
				// mArrayResult3 = Common.getArrayTypeData_CheckLast(parm
				// .getStringParm(23), i);
				// mArrayResultText = Common.getArrayTypeData_CheckLast(parm
				// .getStringParm(24), i);
				mArrayResultSts = Common.getArrayTypeData(parm.getStringParm(9), 1, i);
				mArrayRtn[0] = parm.getStringParm(8);
				mArrayRtn[1] = parm.getStringParm(13);
				mArrayRtn[2] = parm.getStringParm(14).trim() + " " + parm.getStringParm(15);
				mArrayRtn[3] = parm.getStringParm(16).trim() + " " + parm.getStringParm(17);
				mArrayRtn[4] = parm.getStringParm(19);
				mArrayRtn[5] = parm.getStringParm(20);
				mArrayRtn[6] = mArrayName[0];
				int j = 7;
				for (int k = 0; k < i; k++) {
					mArrayRtn[j++] = mArrayAcd[k];
					mArrayRtn[j++] = mArrayName[k];
					mArrayRtn[j++] = mArrayResult1[k];
					mArrayRtn[j++] = mArrayResult2[k];
					if (s2.equals("00309") || s2.equals("00322"))
						mArrayRtn[j++] = getReferenceRangeEP(s, s1, s2, mArrayAcd[k], mArrayResult1[k]);
					else
						mArrayRtn[j++] = mArrayResultSts[k];
					mArrayRtn[j++] = getReferenceRangeEP(s, s1, s2, mArrayAcd[k]);
				}
			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public String[] getPatientResults(String s, String s1, String s2) {
		// boolean flag = false;
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				mArrayRtn = new String[i];
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				for (int j = 0; j < mArrayName.length; j++)
					mArrayRtn[j] = mArrayName[j] + "|" + mArrayResult[j] + "|"
							+ callDpcMC177RM2(new String[] { s, s1, s2, mArrayAcd[j], mArrayFhl[j] });

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public Object[] getPatientResultsAll(String s, String s1, String s2) {
		int i = 0;
		Vector vector = new Vector();
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				i = parm.getIntParm(18);
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				vector.addElement(parm.getStringParm(8));
				vector.addElement(parm.getStringParm(13));
				vector.addElement(parm.getStringParm(14).trim() + " " + parm.getStringParm(15));
				vector.addElement(parm.getStringParm(16).trim() + " " + parm.getStringParm(17));
				vector.addElement(parm.getStringParm(19));
				vector.addElement(parm.getStringParm(20));
				for (int j = 0; j < i; j++) {
					vector.addElement(mArrayName[j].trim());
					vector.addElement(mArrayResult[j].trim());
				}

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		i = vector.size();
		String as[] = new String[i];
		for (int k = 0; k < i; k++)
			as[k] = vector.elementAt(k).toString();

		return as;
	}

	public String[] getPatientResultSTD(String s, String s1, String s2) {
		// boolean flag = false;
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				mArrayRtn = new String[i];
				mArrayAcd = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				mArrayName = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				mArrayResult = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				mArrayFhl = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				for (int j = 0; j < mArrayName.length; j++)
					mArrayRtn[j] = mArrayName[j] + "|" + mArrayResult[j] + (mArrayResult[j].trim().startsWith("*") ? "|**" : "|Negative");

			}
		} catch (Exception _ex) {
			return mArrayRtn;
		}
		return mArrayRtn;
	}

	public String[] getPatientResultYMDD(String s, String s1, String s2) {
		String as[] = null;
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				String as1[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(5), i);
				String as2[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				as = new String[6 + as1.length];
				as[0] = parm.getStringParm(8);
				as[1] = parm.getStringParm(13);
				as[2] = parm.getStringParm(14).trim() + " " + parm.getStringParm(15);
				as[3] = parm.getStringParm(16).trim() + " " + parm.getStringParm(17);
				as[4] = parm.getStringParm(19);
				as[5] = parm.getStringParm(20);
				as[6] = as1[0];
				for (int j = 0; j < as2.length - 1; j++)
					as[7 + j] = as2[1 + j].trim();

			}
		} catch (Exception _ex) {
			return null;
		}
		return as;
	}

	public String[] getPatientResultYMDD2(String s, String s1, String s2) {
		String as[] = null;
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				int i = parm.getIntParm(18);
				String as1[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(5), i);
				String as2[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				as = new String[6 + as1.length * 2];
				as[0] = parm.getStringParm(8);
				as[1] = parm.getStringParm(13);
				as[2] = parm.getStringParm(14).trim() + " " + parm.getStringParm(15);
				as[3] = parm.getStringParm(16).trim() + " " + parm.getStringParm(17);
				as[4] = parm.getStringParm(19);
				as[5] = parm.getStringParm(20);
				as[6] = as1[0];
				int j = 7;
				for (int k = 0; k < as2.length - 1; k++) {
					as[j++] = cutItemName(as1[1 + k].trim());
					as[j++] = as2[1 + k].trim();
				}

			}
		} catch (Exception _ex) {
			return null;
		}
		return as;
	}

	public String[] getRemark(String s, String s1, String s2, String s3) {
		Vector vector = new Vector();
		try {
			int i = 0;
			boolean flag = false;
			String s4 = "";
			dpc = new DpcOfMC107RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				String as1[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5).trim(), parm.getIntParm(6));
				for (int k = 1; k < as1.length; k++)
					if (as1[k].trim().length() == 0) {
						if (++i == 1) {
							vector.addElement(s4);
							s4 = "";
							flag = true;
						}
					} else {
						i = 0;
						if (!flag && as1[k].trim().length() < 40 && as1[k].trim().endsWith(".") && as1[k].trim().startsWith(".")) {
							s4 = s4 + " " + as1[k].trim();
							flag = true;
						} else {
							vector.addElement(s4 + " " + as1[k].trim());
							s4 = "";
							flag = false;
						}
					}

				if (!s4.trim().equals(""))
					vector.addElement(s4);
			}
		} catch (Exception _ex) {
			return (new String[] { "리마크를 불러오는 중에 오류가 발행하였습니다. 정보지원실로 문의 바랍니다" });
		}
		if (vector.size() > 0) {
			String as[] = new String[vector.size()];
			for (int j = 0; j < vector.size(); j++)
				as[j] = vector.elementAt(j).toString().trim();

			return as;
		} else {
			return null;
		}
	}

	public Object[] getResult(String s, String s1, String s2) {
		int i = 0;
		m_vResult = new Vector();
		String s3 = getGumsaName(s2);
		try {
			dpc = new DpcOfMC600RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2 })) {
				parm = dpc.getParm();
				i = parm.getIntParm(18);
				String as[] = Common.getArrayTypeData(parm.getStringParm(4), 2, i);
				String as2[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5), i);
				String as3[] = Common.getArrayTypeData_CheckLast(parm.getStringParm(6), i);
				String as4[] = Common.getArrayTypeData(parm.getStringParm(10), 1, i);
				String as5[] = Common.getArrayTypeData(parm.getStringParm(12), 1, i);
				m_vResult.addElement(s3.trim());
				m_vResult.addElement(parm.getStringParm(13));
				m_vResult.addElement(parm.getStringParm(14).trim() + " " + parm.getStringParm(15).trim());
				m_vResult.addElement(parm.getStringParm(16).trim() + " " + parm.getStringParm(17).trim());
				m_vResult.addElement(parm.getStringParm(20));
				m_vResult.addElement(parm.getStringParm(19));
				m_vResult.addElement(parm.getStringParm(8));
				for (int k = 0; k < i; k++) {
					m_aResult = new String[5];
					m_aResult[0] = as[k];
					m_aResult[1] = as2[k];
					m_aResult[2] = as3[k];
					m_aResult[3] = as4[k];
					m_aResult[4] = as5[k];
					m_vResult.addElement(m_aResult);
				}

			} else {
				return null;
			}
		} catch (Exception _ex) {
		}
		i = m_vResult.size();
		String as1[] = new String[i];
		for (int j = 0; j < i; j++)
			as1[j] = m_vResult.elementAt(j).toString();

		return as1;
	}

	public String[] getResultRemark(String s, String s1, String s2, String s3) {
		Vector vector = new Vector();
		try {
			int i = 0;
			boolean flag = false;
			String s4 = "";
			dpc = new DpcOfMC107RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				String as1[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5).trim(), parm.getIntParm(6));
				for (int k = 1; k < as1.length; k++)
					if (as1[k].trim().length() == 0) {
						if (++i == 1) {
							vector.addElement(s4);
							s4 = "";
							flag = true;
						}
					} else {
						i = 0;
						if (!flag && as1[k].trim().length() < 40 && as1[k].trim().endsWith(".") && as1[k].trim().startsWith(".")) {
							s4 = s4 + " " + as1[k].trim();
							flag = true;
						} else {
							vector.addElement(s4 + " " + as1[k].trim());
							s4 = "";
							flag = false;
						}
					}

				if (!s4.trim().equals(""))
					vector.addElement(s4);
			}
		} catch (Exception _ex) {
			return (new String[] { "리마크를 불러오는 중에 오류가 발행하였습니다. 정보지원실로 문의 바랍니다" });
		}
		if (vector.size() > 0) {
			String as[] = new String[vector.size()];
			for (int j = 0; j < vector.size(); j++)
				as[j] = vector.elementAt(j).toString().trim();

			return as;
		} else {
			return null;
		}
	}

	public String[] getResultRemarkDefault(String s, String s1, String s2, String s3) {
		String as[] = null;
		int i = 0;
		try {
			dpc = new DpcOfMC107RM1();
			if (dpc.processDpc(new String[] { "NML", s, s1, s2, s3 })) {
				parm = dpc.getParm();
				String as1[] = Common.getArrayTypeData_CheckLast_NoTrim(parm.getStringParm(5).trim(), parm.getIntParm(6));
				as = new String[as1.length - 1];
				as[0] = "";
				for (int j = 1; j < as1.length; j++)
					as[i++] = as1[j];

			}
		} catch (Exception _ex) {
			return null;
		}
		return as;
	}

	public String[] getResultTime(String s, String s1) {
		String as[] = { "", "", "", "" };
		try {
			dpc = new DpcOfTC310RM();
			if (dpc.processDpc(new String[] { "NML", s, s1 })) {
				parm = dpc.getParm();
				as[0] = parm.getStringParm(3);
				as[1] = parm.getStringParm(4);
				as[2] = parm.getStringParm(5);
				as[3] = parm.getStringParm(6);
			}
		} catch (Exception _ex) {
		}
		return as;
	}
}

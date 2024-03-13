package com.neodin.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import com.neodin.comm.Common;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadGSUNI extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadGSUNI() {
		isDebug = false;
	}

	public DownloadGSUNI(String id, String fdat, String tdat, Boolean isRewrite) {
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
		return refer.equals("") ? "" : replaceCrLf(refer);
	}

	public String getRemark(String parm[]) {
		return replaceCrLf(getReamrkValue(parm[0], parm[1], parm[2], parm[3]));
	}

	public String getRemarkTxt(String str[]) {
		StringBuffer b = new StringBuffer("");
		if (str.length == 0)
			return null;
		for (int i = 0; i < str.length; i++) {
			b.append(str[i]);
			if (str.length - 1 != i)
				b.append("@^");
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
		try {
			file = new File(savedir + mkOutTextFile());
		} catch (Exception e) {
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
			String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
			String age[] = (String[]) getDownloadData().get("나이");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드"); 
			String unit[] = (String[]) getDownloadData().get("참고치단위등"); 
			String bdt[] = (String[]) getDownloadData().get("검사완료일"); 
			String cns[] = (String[]) getDownloadData().get("처방번호");
			String highLow[] = (String[]) getDownloadData().get("결과상태");

			String deli = "\t";
			
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
				curDate = rcvDate[i];
				curNo = rcvNo[i];
				String data[] = new String[13];
				data[0] = chartNo[i];
				data[1] = patName[i];
				data[2] = specNo[i].trim();
				if (clientInspectCode[i].trim().equals(""))
					data[3] = " ";
				else
					data[3] = clientInspectCode[i];
				data[6] = rcvDate[i];
				data[7] = inspectName[i].trim();
				data[4] = result[i].trim();
				data[5] = result[i].trim();
				data[8] = getUnit(unit[i]).trim();
				data[9] = getReference(new String[] { inspectCode[i], lang[i], history[i],sex[i] }).trim();
				data[10] = "";
				data[11] = highLow[i]; //HL판정치

				// data[0]-챠트번호 data[1]-환자이름 data[2]-검체번호 data[3]-병원검사코드 data[4]-결과 data[5]- 결과 
				// data[6]-접수일자 data[7]- 검사명 data[8]-단위  data[9]-참고치 data[10]-리마크

				String cnsDate=""; //처방일자 
				try {
					cnsDate = cutHrcvDateNumber2(cns[i])[1].substring(0, 8); //처방일자		
				} catch (Exception eee) {
					cnsDate = ""; //처방일자	
				}
				
				try {
					data[12] = cutHrcvDateNumber2(cns[i])[0]; // 처방번호
				} catch (Exception eee) {
					data[12] = ""; // 처방번호
				}
				

				if (resultType[i].trim().equals("C" )) {
					if (inspectCode[i].trim().length() == 7 && hosCode[i].toString().trim().equals("23979") 
							&& ( inspectCode[i].trim().substring(0, 5).equals( "72179") || inspectCode[i].trim().substring(0, 5).equals( "71311"))) {
						data[5] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21);
						data[5] += "\r\n" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   ";
						data[4] = "";
						data[8] = "";
						data[9] = "";
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[5] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21);
							if (++i == cnt)
								break;
						}
						i--;
					} 
					if (hosCode[i].trim().equals("23979") 
							&& (inspectCode[i].trim().substring(0, 5).equals("72020") || inspectCode[i].trim().substring(0, 5).equals("72178")
									|| inspectCode[i].trim().substring(0, 5).equals("72246"))) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						if (inspectCode[i].trim().substring(5, 7).equals("00")) {
							data[6] = "";
							data[5] = "이미지결과확인";
						}
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(
								0, 5))
								&& curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							if (inspectCode[i].trim().substring(5, 7).equals("00")) {
								data[6] = "";
								data[5] = "이미지결과확인";
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
					}
					
					if (hosCode[i].trim().equals("23979") && inspectCode[i].trim().length() == 7 && inspectCode[i].trim().substring(5, 7).equals("00")) {
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						if (inspectCode[i].trim().substring(5, 7).equals("00")) {
							data[6] = "";
							data[5] = "이미지결과확인";
						}
					}
					
					;
					
				} else {
					
						data[4] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();
				}
				if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(rcvDate[i].trim())|| !kumdata[1].trim().equals(rcvNo[i].trim())|| !kumdata[2].trim().equals(rmkCode[i].trim()))
						{
							data[10] = getRemark(new String[] { hosCode[i], rcvDate[i],	rcvNo[i], rmkCode[i] }).trim();
							kumdata[0] = rcvDate[i].trim();
							kumdata[1] = rcvNo[i].trim();
							kumdata[2] = rmkCode[i].trim();
						}
					} catch (Exception e) {
						e.printStackTrace();   
					}
				else
					data[10] = "";
				if (f_outStream == null) {
                    f_outStream = new FileOutputStream(this.file);
                    o_writer = new OutputStreamWriter(this.f_outStream,"MS949");   // 인코딩 수정후
//                    o_writer = new OutputStreamWriter(this.f_outStream); // 인코딩 수정전
                    b_writer = new BufferedWriter(this.o_writer);
				}
				// data[0]-챠트번호 data[1]-환자이름 data[2]-검체번호 data[3]-병원검사코드 data[4]-결과 data[5]- 결과 
				// data[6]-접수일자 data[7]- 검사명 data[8]-단위  data[9]-참고치 data[10]-리마크
				// 검사일자  SID(검체번호) 검사코드 IDNO(챠트번호) 성명 검체코드(?) RESULT CMT
				text.append(data[12] + deli + data[2] + deli +  data[3] + deli +   data[0] + deli + data[1] + deli + " " + deli + data[5]+ deli 
					//	+data[9]
								+ "\r\n");
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

	public String replaceCrLf(String src) {
		return src.replace('\r', '@').replace('\n', '^').replace('\t', ' ');
	}
	
	/**
	 * 메소드 설명을 삽입하십시오.
	 * 작성 날짜: (2009-04-08 오후 3:07:55)
	 * @return java.lang.String
	 */
	public String[] cutHrcvDateNumber2(String str) {
		String src_[] = new String[] {"", ""};
		if (str == null || src_.equals(""))
			return src_;
		
		src_ = Common.getDataCut(str, "D");
		if (src_ == null || src_.length == 0)
			return new String[] {"", ""};
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
}

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 11   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import com.neodin.comm.Common;

// Referenced classes of package com.neodin.files:
//            ResultDownload
/**
 * TODO6 : 엑셀외에 메모장 등으로 결과를 생성 할때 복사 붙여넣고 작업
 * @author cdy
 *
 */
public class DownloadBit extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadBit() {
		isDebug = false;
	}

	public DownloadBit(String id, String fdat, String tdat, Boolean isRewrite) {
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

			String deli = "$";
			
			boolean isNext = false;
			String lastData = "";
			int lastindex = 0;
			
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
				isNext = true;

				if (inspectCode[lastindex].trim().substring(5).equals("00")) {
					cnt--;
				}
				
				while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
					cnt--;
				}
			}
			
			
			for (int i = 0; i < cnt; i++) {
				
				isData = true;
				String curDate = "";
				String curNo = "";
				String data[] = new String[12];
				data[0] = chartNo[i];
				data[1] = patName[i];
				data[2] = specNo[i].trim();
				if (clientInspectCode[i].trim().equals(""))
					data[3] = " ";
				else
					data[3] = clientInspectCode[i];
				data[6] = rcvDate[i] + "-" + rcvNo[i] + "-"+ inspectCode[i].trim();
				data[7] = inspectName[i].trim();
				data[4] = result[i].trim();
				data[5] = result[i].trim();
				data[8] = getUnit(unit[i]).trim();
				data[9] = getReference(new String[] { inspectCode[i], lang[i], history[i],sex[i],age[i] }).trim();
				data[10] = "";
				data[11] = highLow[i]; //HL판정치
				// data[0]: 챠트번호 data[1] 환자이름 data[2]: 검체번호 data[3]:병원검사코드 data[4]:결과 data[5]: 결과 data[6] 접수일자-접수번호-씨젠검사코드 data[7]: 검사명 data[8]:단위  data[9]:참고치 data[10]:리마크
				
				String cnsDate=""; //처방일자 
				try {
					cnsDate = cutHrcvDateNumber2(cns[i])[1].substring(0, 8); //처방일자		
				} catch (Exception eee) {
					cnsDate = ""; //처방일자	
				}
				
						
				if (hosCode[i].toString().trim().equals("26070") && (inspectCode[i].trim().substring(0, 5).equals( "74012")|| inspectCode[i].trim().substring(0, 5).equals( "74013")
						|| inspectCode[i].trim().substring(0, 5).equals( "74014")	|| inspectCode[i].trim().substring(0, 5).equals( "74015") 
						|| inspectCode[i].trim().substring(0, 5).equals( "74016"))
						) {
					// 갑을장유 병원 STI6종(74023) 별지참조로 결과가 나가기 위해 74011 만빼고 모두 건너뜀
					continue;
					}

				//20150818 최대열 : 중간 결과 보고 예외처리
				if (hosCode[i].toString().trim().equals("26070") &&
						(inspectCode[i].trim().substring(0, 5).equals( "31014")|| inspectCode[i].trim().substring(0, 5).equals( "31019")
								||inspectCode[i].trim().substring(0, 5).equals( "31077") ||inspectCode[i].trim().substring(0, 5).equals( "31114"))
						&&"중　간　결　과　보　고".equals(data[4])
						) {
					continue;
				}

				
				
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				if (resultType[i].trim().equals("C")) {
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].toString().trim().equals("26070")  ||hosCode[i].toString().trim().equals("15710")&& (inspectCode[i].trim().substring(0, 5).equals( "71251")
							|| inspectCode[i].trim().substring(0, 5).equals( "71252")|| inspectCode[i].trim().substring(0, 5).equals( "71259")|| inspectCode[i].trim().substring(0, 5).equals( "00083")
							|| inspectCode[i].trim().substring(0, 5).equals( "00673") || inspectCode[i].trim().substring(0, 5).equals( "00674") || inspectCode[i].trim().substring(0, 5).equals( "11052")
							|| inspectCode[i].trim().substring(0, 5).equals( "00683") || inspectCode[i].trim().substring(0, 5).equals( "00684") || 
							inspectCode[i].trim().substring(0, 5).equals( "11052")
							
							|| inspectCode[i].trim().substring(0, 5).equals( "00687") || inspectCode[i].trim().substring(0, 5).equals( "00688") || inspectCode[i].trim().substring(0, 5).equals( "00689")
							
							|| inspectCode[i].trim().substring(0, 5).equals( "72241") || inspectCode[i].trim().substring(0, 5).equals( "74023") || inspectCode[i].trim().substring(0, 5).equals( "72182")
							|| inspectCode[i].trim().substring(0, 5).equals( "72183") || inspectCode[i].trim().substring(0, 5).equals( "72194") || inspectCode[i].trim().substring(0, 5).equals( "71297")
							|| inspectCode[i].trim().substring(0, 5).equals( "72189") || inspectCode[i].trim().substring(0, 5).equals( "72242")))) {
						data[4] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[4] += "@^" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   "+ data[9];
						data[8] = "";
						data[9] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode =	inspectCode[i++].trim().substring(0, 5); 
								thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) 
								&& curDate.equals(rcvDate[i].trim())
								&& curNo.equals(rcvNo[i].trim());) 
						{
							//20160422 MAST 검사 수정작업
							if(isMastDuplPrint(inspectCode[i])){
								++i;
							}else{
								//TODO MAST 검사 40자리가 넘어 수정함
								data[4] += "@^"+ appendBlanks(inspectName[i], 40)+ "   "+ appendBlanks(result[i], 21)+ "   "+ getReference(new String[] { inspectCode[i],	lang[i], history[i], sex[i],age[i] }).trim();
								if (++i >= cnt)
									break;								
							}
						}
						i--;	
						
					}
					
					
					
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].toString().trim().equals("26070") && (inspectCode[i].trim().substring(0, 5).equals( "72185")))) {
						data[4] = appendBlanks("검  사  명 ", 30) + "   "+ "결    과";
						data[4] += "@^" +  appendBlanks(inspectName[i], 30)	+ "   " +data[4].trim();
						data[8] = "";
						data[9] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[4] += "@^"+  appendBlanks(inspectName[i], 30)+ "   "+ result[i].trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					
						data[4] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();
				}
				if (hosCode[i].toString().trim().equals("26070")&& (inspectCode[i].trim().substring(0, 5).equals( "71251")|| inspectCode[i].trim().substring(0, 5).equals( "71252")
						|| inspectCode[i].trim().substring(0, 5).equals( "71259") || inspectCode[i].trim().substring(0, 5).equals( "00673") 
						|| inspectCode[i].trim().substring(0, 5).equals( "00674") || inspectCode[i].trim().substring(0, 5).equals( "11052")
						|| inspectCode[i].trim().substring(0, 5).equals( "00684") || inspectCode[i].trim().substring(0, 5).equals( "00683")
						|| inspectCode[i].trim().substring(0, 5).equals( "00687") || inspectCode[i].trim().substring(0, 5).equals( "00688")
						|| inspectCode[i].trim().substring(0, 5).equals( "00689") || inspectCode[i].trim().substring(0, 5).equals( "00690")
						|| inspectCode[i].trim().substring(0, 5).equals( "00691")
						|| inspectCode[i].trim().substring(0, 5).equals( "72241") || inspectCode[i].trim().substring(0, 5).equals( "74023") 
						|| inspectCode[i].trim().substring(0, 5).equals( "72182") || inspectCode[i].trim().substring(0, 5).equals( "72183")
						|| inspectCode[i].trim().substring(0, 5).equals( "74011") || inspectCode[i].trim().substring(0, 5).equals( "72194")
						|| inspectCode[i].trim().substring(0, 5).equals( "72185") || inspectCode[i].trim().substring(0, 5).equals( "71297")
						|| inspectCode[i].trim().substring(0, 5).equals( "72189") || inspectCode[i].trim().substring(0, 5).equals( "72242")
						|| inspectCode[i].trim().substring(0, 5).equals( "21065") || inspectCode[i].trim().substring(0, 5).equals( "00405")
						)) {
					data[4] = "별지보고";
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
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}
				// 차트번호$ 환자이름$ 접수일시$ 접수번호$ 검체코드(바코드번호)$ 검사코드$ 결과  $H/L판정치$참고치
				// data[0]: 챠트번호 data[1] 환자이름 data[2]: 검체번호 data[3]:병원검사코드 data[4]:결과 data[5]: 결과 data[6] 접수일자( rcvDate[i])-접수번호( rcvNo[i] )-씨젠검사코드( inspectName[i].trim()) data[7]: 검사명 data[8]:단위  data[9]:참고치 data[10]:리마크
				String result_str = data[4].replaceAll("\r\n", "@^");
				String reference_str = data[9].replaceAll("\r\n", "@^");
				text.append(data[0] + deli + data[1] + deli + cnsDate + deli +  rcvNo[i] + deli + data[2] + deli + data[3] + deli + result_str+ deli +data[11] + deli +reference_str+ "\r\n");
				b_writer.write(text.toString(), 0, text.toString().length());
				text = new StringBuffer();
			}
			if (cnt == 400 || isNext)
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

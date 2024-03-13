package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 11   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import com.neodin.comm.Common;

// Referenced classes of package com.neodin.files:
//            ResultDownload
/**
 * TODO6 : 엑셀외에 메모장 등으로 결과를 생성 할때 복사 붙여넣고 작업
 * @author cdy
 *
 */
public class DownloadDR extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadDR() {
		isDebug = false;
	}

	public DownloadDR(String id, String fdat, String tdat, Boolean isRewrite) {
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
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ');
			src_[0] = StringUtils.leftPad(src_[0],5,"");                             // 자릿수 앞에 공백 채워 넣기
			
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
//				data[5] = result[i].trim(); // data[5] 의경우 처방 번호로 예측됨
				data[8] = getUnit(unit[i]).trim();
				data[9] = getReference(new String[] { inspectCode[i], lang[i], history[i],sex[i],age[i] }).trim();
				data[10] = "";
				data[11] = highLow[i]; //HL판정치
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				} 
				
				
				// data[0]: 챠트번호 data[1] 환자이름 data[2]: 검체번호 data[3]:병원검사코드 data[4]:결과 처방번호[5]: 결과 data[6] 접수일자-접수번호-씨젠검사코드 data[7]: 검사명 data[8]:단위  data[9]:참고치 data[10]:리마크
				
				String cnsDate=""; //처방일자 
				try {
					cnsDate = cutHrcvDateNumber2(cns[i])[1].substring(0, 8); //처방일자		
				} catch (Exception eee) {
					cnsDate = ""; //처방일자	
				}


			
				if (resultType[i].trim().equals("C")) {
					
					
					
					
				} else {
						
						data[4] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();
						
			
						
							      String[] result_arr= data[4].split("\r\n");
							      String result_cell="";

		//					      System.out.println("result_arr.length:"+result_arr.length);
							      for(int j=0; j<result_arr.length;j++)
							      { 
							         if(j>0 && j<result_arr.length-1)
							         {
							            
							            result_cell = result_cell+result_arr[j]+"\r\n";
							         }
							   
							      }
				
							
						
								
										
			

				
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}

				int str_index = data[4].indexOf("면허:");
				String str_doc="";	
				String str_num="";		
				
				
				
				try {
				if (hosCode[i].trim().equals("30044")) {
										str_doc = data[4].substring(str_index -4 ,str_index);
										str_num = data[4].substring(str_index +3 ,str_index +6);
			
									
									}
				} catch (Exception eee){
					
				}
				
				
				
			
				
				 
				
				
			
				
				// 차트번호$ 환자이름$ 접수일시$ 접수번호$ 검체코드(바코드번호)$ 검사코드$ 결과  $H/L판정치$참고치
				// data[0]: 챠트번호 data[1] 환자이름 data[2]: 검체번호 data[3]:병원검사코드 data[4]:결과 data[5]: 결과 data[6] 접수일자( rcvDate[i])-접수번호( rcvNo[i] )-씨젠검사코드( inspectName[i].trim()) data[7]: 검사명 data[8]:단위  data[9]:참고치 data[10]:리마크
				
				
				
				result_cell = result_cell.replaceAll("\r\n", "@^");
				result_cell = result_cell.replace("<Result                             >", "<Result>");
				result_cell = result_cell.replace("<Non-Gyn Cytology                   >", "<Non-Gyn Cytology>");
				//System.out.println(result_cell);
//				String reference_str = data[9].replaceAll("\r\n", "@^");
		
				
				String subknum = specNo[i].trim().substring(7);
				int subknum2 = Integer.parseInt(subknum);
				
				if ( subknum2 < 10  ){		
					data[2] = specNo[i].replace("G-2018", "G2018   ").replace("-", ""); // 검체번호에 자릿수 및  - 빼기
					}else if ( subknum2 < 100 ){		
						data[2] = specNo[i].replace("G-2018", "G2018  ").replace("-", ""); // 
						} else if ( subknum2 < 1000 ){	
							data[2] = specNo[i].replace("G-2018", "G2018 ").replace("-", ""); // 
							}else{
								data[2] = specNo[i].replace("G-2018", "G2018").replace("-", ""); // 
						}
			 

				text.append(data[2] + data[3] + data[5] + data[0] + cnsDate + bdt[i] + str_doc + "MD  " + str_num + "       " + "@^"+ result_cell + "|" + "\r\n");
				b_writer.write(text.toString(), 0, text.toString().length());
				text = new StringBuffer();
			}
			if (cnt == 400 || isNext)
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			else
				setParameters(null);
		}
		}catch (Exception e) {
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

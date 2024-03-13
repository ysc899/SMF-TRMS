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

public class DownloadFamily extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadFamily() {
		isDebug = false;
	}

	public DownloadFamily(String id, String fdat, String tdat, Boolean isRewrite) {
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
				
				if("27076".equals(hosCode[i])&&
						(
							inspectCode[i].trim().equals( "0080200")
						  ||inspectCode[i].trim().equals( "0082200")||inspectCode[i].trim().equals( "0082204")||inspectCode[i].trim().equals( "0082205")||inspectCode[i].trim().equals( "0082206")||inspectCode[i].trim().equals( "0082207")
						  ||inspectCode[i].trim().equals( "1100200")||inspectCode[i].trim().equals( "1100202")||inspectCode[i].trim().equals( "1100205")||inspectCode[i].trim().equals( "1100206")
						  ||inspectCode[i].trim().equals( "1123000")||inspectCode[i].trim().equals( "0009502")||inspectCode[i].trim().equals( "0009503")
						)
					){
					continue;
				}
				
				
				
				
				isData = true;
				String curDate = "";
				String curNo = "";
				String data[] = new String[13];
				data[0] = chartNo[i];
				data[1] = patName[i];
				data[2] = specNo[i].trim();
				if (clientInspectCode[i].trim().equals(""))
					data[3] = " ";
				else
					data[3] = clientInspectCode[i];

				data[4] = result[i].trim();
				data[5] = result[i].trim();
				data[6] = rcvDate[i];
				data[7] = inspectName[i].trim();
				data[8] = getUnit(unit[i]).trim();
				data[9] = getReference(new String[] { inspectCode[i], lang[i], history[i],sex[i] }).trim();
				data[10] = "";
				data[11] = highLow[i]; //HL판정치
				data[12] = inspectCode[i].trim(); //검사코드
				// data[0](챠트번호) data[1](환자이름) data[5](접수일자) data[2](검체번호) data[12](검사코드) data[3](처방코드) data[7](검사명) data[4](결과)
				
				String cnsDate=""; //처방일자 
				try {
					cnsDate = cutHrcvDateNumber2(cns[i])[1].substring(0, 8); //처방일자		
				} catch (Exception eee) {
					cnsDate = ""; //처방일자	
				}
				

				
				
				if (resultType[i].trim().equals("C")) {
					if (inspectCode[i].trim().length() == 7 && hosCode[i].toString().trim().equals("27076") &&(
							! inspectCode[i].trim().substring(0, 5).equals( "00802") 
							&& ! inspectCode[i].trim().substring(0, 5).equals( "00822")
							&& ! inspectCode[i].trim().substring(0, 5).equals( "11002")
							&& ! inspectCode[i].trim().substring(0, 5).equals( "11230")
							&& ! inspectCode[i].trim().substring(0, 5).equals( "00095")
							) ) {
						//data[4] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[4] += "@^" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   "+ data[9];
						data[8] = "";
						data[9] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						
						
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[4] += "@^"+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)+ "   "+ getReference(new String[] { inspectCode[i],	lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}

					
					// inspectCode[i].trim().substring(0, 5).equals( "21628")
					
//					if(hosCode[i].trim().equals("25722")&& inspectCode[i].trim().substring(0, 5).equals("21628")){
//						System.out.println("========================");
//						System.out.println(inspectCode[i+1]);
//						System.out.println(inspectCode[i+2]);
//						System.out.println(inspectCode[i+3]);
//						System.out.println(inspectCode[i+4]);
//						System.out.println(inspectCode[i+5]);
//						System.out.println(inspectCode[i+6]);
//						System.out.println(inspectCode[i+7]);
//						System.out.println(inspectCode[i+8]);
//						System.out.println(inspectCode[i+216]);
//						System.out.println(inspectCode[i+217]);
//						System.out.println(inspectCode[i+218]);
//						System.out.println(inspectCode[i+220]);
//						System.out.println("========================");
//					}
					
					if((hosCode[i].trim().equals("25722")) 
							&& inspectCode[i].trim().substring(0, 5).equals("21628")  
							&& i+216<cnt	&&  inspectCode[i+216].trim().substring(0, 5).equals("21636") 
							)
					{
						
					//	System.out.println("드러왔다!");
						data[4] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[4] += "@^" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   "+ data[9];
						data[8] = "";
						data[9] = "";
						data[3]= "C50000";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						
						//20151014 최대열 : 결과 연동 프로그램상에 검사코드가 5자리만 연동 되도록 되어있어서 부속 검사 코드 5자리로 변경
						//if (inspectCode[i].trim().equals( "0068400")){
						data[12] =  inspectCode[i].substring(0, 5);//검사코드 5자리만 연동 가능하여
						//}
						
						inspectCode[i].substring(0, 5);
						for (int j = 0; j < 231; j++) {
							data[4] += "@^"+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)+ "   "+ getReference(new String[] { inspectCode[i],	lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						
						i--;
					}
					
					
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].toString().trim().equals("29674") ||hosCode[i].toString().trim().equals("15710")&& 
							( inspectCode[i].trim().substring(0, 5).equals( "11052")||
									inspectCode[i].trim().substring(0, 5).equals( "31001")))) {
						data[4] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[4] += "@^" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   "+ data[9];
						data[8] = "";
						data[9] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						
						//20151014 최대열 : 결과 연동 프로그램상에 검사코드가 5자리만 연동 되도록 되어있어서 부속 검사 코드 5자리로 변경
						if (inspectCode[i].trim().equals( "3100100")){
								data[12] ="31001";
						}
						
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							//20160422 최대열 MAST 코드 변경
							if(isMastDuplPrint(inspectCode[i].trim())){
								++i;
							}else{
								data[4] += "@^"+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)+ "   "+ getReference(new String[] { inspectCode[i],	lang[i], history[i], sex[i] }).trim();	
							}
								
							if (++i == cnt)
								break;
						}
						

						
						i--;
					}
					
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].toString().trim().equals("25722") || hosCode[i].toString().trim().equals("15710")&& 
							(inspectCode[i].trim().substring(0, 5).equals( "31001")
							|| inspectCode[i].trim().substring(0, 5).equals( "00673")//
							|| inspectCode[i].trim().substring(0, 5).equals( "00674")//	  
							|| inspectCode[i].trim().substring(0, 5).equals( "00683")//
							|| inspectCode[i].trim().substring(0, 5).equals( "00684")//
							|| inspectCode[i].trim().substring(0, 5).equals("00687")
							|| inspectCode[i].trim().substring(0, 5).equals("00688")
							|| inspectCode[i].trim().substring(0, 5).equals("00689")
							|| inspectCode[i].trim().substring(0, 5).equals( "11052")//
							|| inspectCode[i].trim().substring(0, 5).equals( "81000")//
							|| inspectCode[i].trim().substring(0, 5).equals( "11002")//
							|| inspectCode[i].trim().substring(0, 5).equals( "71298")
							|| inspectCode[i].trim().substring(0, 5).equals( "72182")//
							|| inspectCode[i].trim().substring(0, 5).equals( "72018")//
							|| inspectCode[i].trim().substring(0, 5).equals( "31083")//
							|| inspectCode[i].trim().substring(0, 5).equals( "72189")//
							|| inspectCode[i].trim().substring(0, 5).equals( "72242")//
							|| inspectCode[i].trim().substring(0, 5).equals( "72198")
							|| inspectCode[i].trim().substring(0, 5).equals( "11301")//
							|| inspectCode[i].trim().substring(0, 5).equals( "71252")//
							|| inspectCode[i].trim().substring(0, 5).equals( "72183")
							|| inspectCode[i].trim().substring(0, 5).equals( "81148")
							
							))) {
						data[4] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[4] += "@^" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   "+ data[9];
						data[8] = "";
						data[9] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						
						//20151014 최대열 : 결과 연동 프로그램상에 검사코드가 5자리만 연동 되도록 되어있어서 부속 검사 코드 5자리로 변경
						if (inspectCode[i].trim().equals( "3100100")){
								data[12] ="31001";
						}
						
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							//20160422 최대열 MAST 코드 변경
							if(isMastDuplPrint(inspectCode[i].trim())){
								++i;
							}else{
								data[4] += "@^"+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)+ "   "+ getReference(new String[] { inspectCode[i],	lang[i], history[i], sex[i] }).trim();	
							}
								
							if (++i == cnt)
								break;
						}
						

						
						i--;
					}
				} 
				else {
					
						data[4] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();
						
						
				}
				
			
				
				//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if ((inspectCode[i].trim().equals("31100")||inspectCode[i].trim().equals("31101")
								||inspectCode[i].trim().equals("31102")||inspectCode[i].trim().equals("31103")
								||inspectCode[i].trim().equals("31104")||inspectCode[i].trim().equals("31105")
								||inspectCode[i].trim().equals("31106")||inspectCode[i].trim().equals("31107")
								||inspectCode[i].trim().equals("31108")||inspectCode[i].trim().equals("31109")
								||inspectCode[i].trim().equals("31110")||inspectCode[i].trim().equals("31111")
								||inspectCode[i].trim().equals("31112")||inspectCode[i].trim().equals("31113")
								||inspectCode[i].trim().equals("31114")||inspectCode[i].trim().equals("31115")
								||inspectCode[i].trim().equals("31116")||inspectCode[i].trim().equals("31117")
								||inspectCode[i].trim().equals("31118")||inspectCode[i].trim().equals("31119")
								||inspectCode[i].trim().equals("31120")||inspectCode[i].trim().equals("31121")
								||inspectCode[i].trim().equals("31122")||inspectCode[i].trim().equals("31123")
								||inspectCode[i].trim().equals("31124"))) {

					data[4] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//	data[36] = data[35].trim();
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001"))
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[4] = data[4] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
				//			data[36] = data[35].trim();
							i++;
							// culture_flag = true;
						} else {
							data[4] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//			data[36] = data[35].trim();
						}
					} catch (Exception e) {
						data[4] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
				//		data[36] = data[35].trim();
					}
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
				
				
				//20170720 패밀리병원 00804 "리마크 참조" 문구 대신 실제 리마크 노출 요청
				if ( hosCode[i].trim().equals("27076") &&(inspectCode[i].trim().substring(0, 5).equals( "00804"))) 
				{
						
						data[4] = data[10] ;
				}
				
				
				
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
                    o_writer = new OutputStreamWriter(f_outStream,"MS949");		// 인코딩 수정후
//					o_writer = new OutputStreamWriter(f_outStream);				// 인코딩 수정전
					b_writer = new BufferedWriter(o_writer);
				}
				
				// data[0](챠트번호) data[1](환자이름) cnsDate(처방일자),data[6] data[2](검체번호) data[12](검사코드) data[3](처방코드) data[7](검사명) data[4](결과)
				text.append(data[0] + deli + data[1] + deli + data[6] + deli +  data[2] + deli + data[12] + deli + data[3] + deli + data[7]+ deli +data[4].replaceAll("\r\n", "@^")+ "\r\n");
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

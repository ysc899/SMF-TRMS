package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 11   Fields: 1

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import jxl.write.Boolean;

import com.neodin.comm.Common;

// Referenced classes of package com.neodin.files:
//            ResultDownload

public class DownloadCentral extends ResultDownload {
	boolean isDebug = false;
	boolean isData = false;
    
	//!
    private String HR1[]= new String[] { "16", "18", "26", "31", "33", "35", "39", "45", "51", "52" }; //10
    private String HighRisk_A[]= new String[HR1.length];
    private String HR2[]= new String[] { "53", "56", "58", "59", "66", "68", "69", "70", "73", "82" }; //10
    private String HighRisk_B[]= new String[HR2.length];
    private String LR1[]= new String[] { " 6", "11", "40", "42", "43", "44", "54", "61" }; //9
    private String LowRisk[]= new String[LR1.length];
    
    
    public boolean isRetest(String param) {
        if (param.toLowerCase().indexOf("보고예정") > -1
            || param.toLowerCase().indexOf("재검") > -1
            || param.toLowerCase().indexOf("검체부족") > -1
            || param.toLowerCase().indexOf("추후송부") > -1
            || param.toLowerCase().indexOf("이전결과") > -1
            || param.toLowerCase().indexOf("검체") > -1
            || param.toLowerCase().indexOf("추후보고") > -1
            || param.toLowerCase().indexOf("초과") > -1) {
            return true;
        }
        return false;
    }
    //!
	public DownloadCentral() {
		isDebug = false;
	}

	public DownloadCentral(String id, String fdat, String tdat, java.lang.Boolean isRewrite) {
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
	/**
	* 메소드 설명을 삽입하십시오.
	* 작성 날짜: (2013-06-30 오전 6:38:20)
	*/
	private String positiveLow(String rlt) {
		LowRisk= new String[]{"","","","","","","",""};
	    String _result= rlt;
	    if (rlt.toString().trim().toUpperCase().equals("NEGATIVE")
	        || rlt.toString().trim().toLowerCase().equals("negative")
	        || rlt.toString().trim().toLowerCase().equals("nega")) {
	        return rlt;
	    }
	    if (isRetest(rlt))
	        return rlt;

	    String[] str= Common.getDataCut(rlt, ",");

	    if (str != null && str.length == 1) {
	        str= Common.getDataCut(rlt, " ");
	    }

	    if (str != null && str.length == 1) {
	        str= Common.getDataCut(rlt, "type");
	    }

	    if (str != null && str.length == 1) {
	        str= Common.getDataCut(rlt, "Type");
	    }

	    //if (str != null ) {
	    //return rlt;
	    //}

	    //}
	    for (int i= 0; i < LowRisk.length; i++) {
	        LowRisk[i]= "";
	    }

	    for (int i= 0; i < LR1.length; i++) {

	        if (LR1[i].trim().equals("6")) {

	            for (int v= 0; v < str.length; v++) {
	                if (str[v].toString().trim().replace('(', ' ').replace(')', ' ').replace('+', ' ').toString().trim().equals("6")) {

	                    if (rlt.indexOf(LR1[i].trim() + "+++") > -1 || rlt.indexOf(LR1[i].trim() + "(+++)") > -1) {
	                        LowRisk[i]= "3+";
	                    } else if (rlt.indexOf(LR1[i].trim() + "++") > -1 || rlt.indexOf(LR1[i].trim() + "(++)") > -1) {
	                        LowRisk[i]= "2+";
	                    } else if (rlt.indexOf(LR1[i].trim() + "") > -1 || rlt.indexOf(LR1[i].trim() + "(+)") > -1) {
	                        LowRisk[i]= "1+";
	                    }

	                }
	            }
	        } else if (!LR1[i].equals("")) {
	            if (rlt.indexOf(LR1[i]) > -1) {
	                if (rlt.indexOf(LR1[i].trim() + "+++") > -1 || rlt.indexOf(LR1[i].trim() + "(+++)") > -1) {
	                    LowRisk[i]= "3+";
	                } else if (rlt.indexOf(LR1[i].trim() + "++") > -1 || rlt.indexOf(LR1[i].trim() + "(++)") > -1) {
	                    LowRisk[i]= "2+";
	                } else if (rlt.indexOf(LR1[i].trim() + "") > -1 || rlt.indexOf(LR1[i].trim() + "(+)") > -1) {
	                    LowRisk[i]= "1+";
	                }
	            }
	        }
	    }
	    int lens= rlt.length();
	    StringBuffer sb= new StringBuffer();
	    for (int i= 0; i < lens; i++) {
	        try {
	            if (!rlt.substring(i, i + 1).equals("+")) {
	                sb.append(rlt.substring(i, i + 1));
	            }
	        } catch (Exception ee) {
	            sb.append(rlt.substring(i)); //System.out.println(rlt.substring(i));
	        }

	    }
	    _result= sb.toString(); //!
	    return _result;
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
				b.append("^@");
		}

		return b.toString().trim();
	}

	/**
	* 메소드 설명을 삽입하십시오.
	* 작성 날짜: (2013-06-30 오전 6:38:20)
	*/
	private String positiveHigh(String rlt) {
		
	   HighRisk_A= new String[]{"","","","","","","","","",""};
	   HighRisk_B= new String[]{"","","","","","","","","",""};

	    
	    String _result= rlt;
	    if (rlt.toString().trim().toUpperCase().equals("NEGATIVE")
	        || rlt.toString().trim().toLowerCase().equals("negative")
	        || rlt.toString().trim().toLowerCase().equals("nega")) {
	        return rlt;
	    }
	    if (isRetest(rlt))
	        return rlt;

	    String[] str= Common.getDataCut(rlt, ",");

	    if (str != null && str.length == 1) {
	        str= Common.getDataCut(rlt, " ");
	    }

	    if (str != null && str.length == 1) {
	        str= Common.getDataCut(rlt, "type");
	    }

	    if (str != null && str.length == 1) {
	        str= Common.getDataCut(rlt, "Type");
	    }

	    //if (str != null ) {
	    //return rlt;
	    //}

	    for (int i= 0; i < HighRisk_A.length; i++) {
	        HighRisk_A[i]= "";
	    }
	    for (int i= 0; i < HighRisk_B.length; i++) {
	        HighRisk_B[i]= "";
	    }

	    //!
	    for (int i= 0; i < HR1.length; i++) {
	        if (!HR1[i].equals("")) {
	            if (rlt.indexOf(HR1[i].trim() + "+++") > -1 || rlt.indexOf(HR1[i].trim() + "(+++)") > -1) {
	                HighRisk_A[i]= "3+";
	            } else if (rlt.indexOf(HR1[i].trim() + "++") > -1 || rlt.indexOf(HR1[i].trim() + "(++)") > -1) {
	                HighRisk_A[i]= "2+";
	            } else if (rlt.indexOf(HR1[i].trim() + "") > -1 || rlt.indexOf(HR1[i].trim() + "(+)") > -1) {
	                HighRisk_A[i]= "1+";
	            }
	        }
	    }

	    for (int i= 0; i < HR2.length; i++) {
	        if (!HR2[i].equals("")) {
	            if (rlt.indexOf(HR2[i]) > -1) {
	                if (rlt.indexOf(HR2[i].trim() + "+++") > -1 || rlt.indexOf(HR2[i].trim() + "(+++)") > -1) {
	                    HighRisk_B[i]= "3+";
	                } else if (rlt.indexOf(HR2[i].trim() + "++") > -1 || rlt.indexOf(HR2[i].trim() + "(++)") > -1) {
	                    HighRisk_B[i]= "2+";
	                } else if (rlt.indexOf(HR2[i].trim() + "") > -1 || rlt.indexOf(HR2[i].trim() + "(+)") > -1) {
	                    HighRisk_B[i]= "1+";
	                }
	            }
	        }
	    }

	    int lens= rlt.length();
	    StringBuffer sb= new StringBuffer();
	    for (int i= 0; i < lens; i++) {
	        try {
	            if (!rlt.substring(i, i + 1).equals("+")) {
	                sb.append(rlt.substring(i, i + 1));
	            }
	        } catch (Exception ee) {
	            sb.append(rlt.substring(i)); //System.out.println(rlt.substring(i));
	        }

	    }
	    _result= sb.toString(); //!
	    return _result;
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
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드"); 
			String bgcd[] = (String[]) getDownloadData().get("보험코드");
//			20190801 이후로 바뀜 해당 병원의 경우 2018년 이후로 거래내역 없음
//			String sti_remark = "[Comment]^@viral load에서 +++ : >10^5 copies/rxn, ++ : 10^5~10^2 copies/rxn," 
//					                +"^@ + : <10^2 copeis/rxn 의 농도로 해석될 수"
//									+"^@있습니다. 이 중 ‘+’는 매우 낮은 농도로 감염시기, 검체 채취" 
//									+"^@상태에 따라 반복 검사시 재현되지 않을 수 있습니다."
//					                +"^@PCR 검사는 검체 내 균 수가 적거나 부적절한 검체 희석 또는" 
//									+"^@증폭 억제물질이 존재하는 경우 위음성이 나올 수"
//					                +"^@있습니다. 또한, PCR 검사는 유전자 유무를 검사하므로 생존균과"
//					                +"^@사균의 구분이 안되어 위양성의 가능성이"
//					                +"^@있습니다. 결과 해석 시, 환자의 임상 양상과 연관지어 판단하시기" 
//					                +"^@바랍니다.";

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
				String curDate =rcvDate[i];
				String curNo = rcvNo[i];
				String data[] = new String[13];
				data[0] = chartNo[i];
				data[1] = patName[i];
				data[2] = specNo[i].trim();
				data[9]="";
				if (clientInspectCode[i].trim().equals(""))
					data[3] = " ";
				else
					data[3] = clientInspectCode[i];
				data[4] = result[i].trim();
		
				if (resultType[i].trim().equals("C")) {   
					if (inspectCode[i].trim().length() == 7 && (inspectCode[i].toString().substring(0, 5).trim().equals("72185"))) {//72185

						data[4] = appendBlanks("검  사  명 ", 30) + "   "+ "결    과";
						if(inspectCode[i].toString().equals("7218500"))
						{
							data[4] += "^@" + appendBlanks(inspectName[i], 30);
						}else
						{
							data[4] += "^@" + appendBlanks(inspectName[i], 30)	+ "   " +data[4].trim();
						}
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							if(inspectCode[i].toString().equals("7218501")){
							    positiveHigh(result[i].trim());
								}
								if(inspectCode[i].toString().equals("7218502")){
								positiveLow(result[i].trim());
								}
							data[4] += "^@"+ appendBlanks(inspectName[i], 30)+ "   "+ result[i].trim();
							if (++i == cnt)
								break;
						}
						i--;
						//!

						//HPV RealTime 검사시 리마크 및 검사 내용 추가 INTERPRETATION
						data[4] = data[4] +"^@";
						data[4] = data[4] +"^@HPV Genotype";
						data[4] = data[4] +"^@-----------------------------------------------------------";
						data[4] = data[4] +"^@고위험군";
						data[4] = data[4] +"^@"+appendBlanks("16", 3)+""//
											  +appendBlanks("18", 3)+""//
											  +appendBlanks("26", 3)+""//
											  +appendBlanks("31", 3)+""//
											  +appendBlanks("33", 3)+""//
											  +appendBlanks("35", 3)+""//
											  +appendBlanks("39", 3)+""//
											  +appendBlanks("45", 3)+""//
											  +appendBlanks("51", 3)+""//
											  +appendBlanks("52", 3)+""//
											  +appendBlanks("53", 3)+""//
											  +appendBlanks("56", 3)+""//
											  +appendBlanks("58", 3)+""//
											  +appendBlanks("59", 3)+""//
											  +appendBlanks("66", 3)+""//
											  +appendBlanks("68", 3)+""//
											  +appendBlanks("69", 3)+""//
											  +appendBlanks("70", 3)+""//
											  +appendBlanks("73", 3)+""//
											  +appendBlanks("82", 3);//-------------------- 아래는 결과						
					  	data[4] = data[4] +"^@"+appendBlanks(HighRisk_A[0], 3)+""//16
					  						  +appendBlanks(HighRisk_A[1], 3)+""//18
					  						  +appendBlanks(HighRisk_A[2], 3)+""//26
					  						  +appendBlanks(HighRisk_A[3], 3)+""//31
					  						  +appendBlanks(HighRisk_A[4], 3)+""//33
					  					      +appendBlanks(HighRisk_A[5], 3)+""//35
						  					  +appendBlanks(HighRisk_A[6], 3)+""//39
						  					  +appendBlanks(HighRisk_A[7], 3)+""//45
						  					  +appendBlanks(HighRisk_A[8], 3)+""//51
						  					  +appendBlanks(HighRisk_A[9], 3)+""//52
						  					  +appendBlanks(HighRisk_B[0], 3)+""//53
						  					  +appendBlanks(HighRisk_B[1], 3)+""//56
						  					  +appendBlanks(HighRisk_B[2], 3)+""//58
						  					  +appendBlanks(HighRisk_B[3], 3)+""//59
						  					  +appendBlanks(HighRisk_B[4], 3)+""//66
						  					  +appendBlanks(HighRisk_B[5], 3)+""//68
						  					  +appendBlanks(HighRisk_B[6], 3)+""//69
						  					  +appendBlanks(HighRisk_B[7], 3)+""//70
						  					  +appendBlanks(HighRisk_B[8], 3)+""//93
						  					  +appendBlanks(HighRisk_B[9], 3);//82
					
						
						data[4] = data[4] +"^@-----------------------------------------------------------";		
						data[4] = data[4] +"^@저위험군";  
						data[4] = data[4] +"^@"+appendBlanks("6", 3)+""//
											  +appendBlanks("11", 3)+""//
											  +appendBlanks("40", 3)+""//
											  +appendBlanks("42", 3)+""//
											  +appendBlanks("43", 3)+""//
											  +appendBlanks("44", 3)+""//
											  +appendBlanks("54", 3)+""//
											  +appendBlanks("61", 3);//----------------------- 아래 결가  
						data[4] = data[4] +"^@"+appendBlanks(LowRisk[0], 3)+""//6
											  +appendBlanks(LowRisk[1], 3)+""//11
											  +appendBlanks(LowRisk[2], 3)+""//40
											  +appendBlanks(LowRisk[3], 3)+""//42
											  +appendBlanks(LowRisk[4], 3)+""//43
											  +appendBlanks(LowRisk[5], 3)+""//44
											  +appendBlanks(LowRisk[6], 3)+""//54
											  +appendBlanks(LowRisk[7], 3);//61
						data[4] = data[4] +"^@-----------------------------------------------------------";	
						data[4] = data[4] +"^@";		
						data[4] = data[4] +"^@ [INTERPRETATION]^@"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"^@"; // +sti_remark; 20190801 이후로 바뀜 
						data[4] = data[4] +"^@^@씨젠의료재단 1566-6500 ^@ 전문의:이선화 면허:416 ^@";
					}
				} else {
						//문장형태는 무조건 별지보고
					     if(inspectName[i].indexOf("액상")>=0 || inspectName[i].indexOf("FNA")>=0 || inspectName[i].indexOf("GY")>=0 || inspectName[i].indexOf("세포")>=0 || inspectName[i].indexOf("cell")>=0)
					     {
					    	 String cyResult  = getTextCytologyType( rcvDate[i],rcvNo[i]).replaceAll("< 의뢰　종목 >","").trim();

					    	 //세포 검사일 경우 첫번째 줄에 기존에 나온 형태가 아닌 검사명 + 보험 코드로 변경한다
					    	 String[] result_arr = cyResult.split("\r\n");
					    	 data[4] = inspectName[i] +"   " + bgcd[i].trim();
					    	 for(int j=1; j<result_arr.length;j++)
					    	 {
					    		 data[4] =data[4] +"^@"+result_arr[j];
					    	 }
					    	 //마지막 줄에 전문의 정보를 추가한다.
					    	 data[4] = data[4]+"^@^@씨젠의료재단 070-7496-2701^@전문의:강신광 면허:47";
					     }
					     else
					     {
					    	 data[4] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]).trim().replace("\r\n","^@");
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
				if (f_outStream == null) {
					f_outStream = new FileOutputStream(file);
					o_writer = new OutputStreamWriter(f_outStream);
					b_writer = new BufferedWriter(o_writer);
				}
				//챠트번호	환자명		검체번호	검사코드	결과	
				text.append(data[0].trim() + deli + data[1] + deli +  data[2] + deli + data[3] + deli+ data[4] +"\r\n");
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
		return src.replace("\r\n", "^@").replace("\t", " ");
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

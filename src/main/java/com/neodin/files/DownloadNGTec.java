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

public class DownloadNGTec extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	public DownloadNGTec() {
		isDebug = false;
	}

	public DownloadNGTec(String id, String fdat, String tdat, Boolean isRewrite) {
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

			String deli = "|";
			for (int i = 0; i < cnt; i++) {
				//cdy
				if("윤인순".equals(patName[i])){
					System.out.println("asdfasdfsadf");
				}
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
				String data[] = new String[11];
				data[0] = chartNo[i];
				data[1] = patName[i];
				data[2] = specNo[i].trim();
				if (clientInspectCode[i].trim().equals(""))
					data[3] = " ";
				else
					data[3] = clientInspectCode[i];
				data[6] = rcvDate[i] + "-" + rcvNo[i] + "-"+ inspectCode[i].trim();
				data[7] = inspectName[i].trim();
				data[4] = "";
				data[5] = "";
				data[8] = "";
				data[9] = "";
				data[10] = "";
				
				String cnsDate=""; //처방일자
				try {
					cnsDate = cutHrcvDateNumber2(cns[i])[1]; //처방일자		
				} catch (Exception eee) {
					cnsDate = ""; //처방일자	
				}
				
				if(hosCode[i].trim().equals("20971")&& (inspectCode[i].trim().equals("7125100")||inspectCode[i].trim().equals("7125102")||inspectCode[i].trim().equals("7125103")
																	|| inspectCode[i].trim().equals("7125200")||inspectCode[i].trim().equals("7125202")||inspectCode[i].trim().equals("7125203")
																	|| inspectCode[i].trim().equals("7125900")||inspectCode[i].trim().equals("7125901")||inspectCode[i].trim().equals("0008300")))
				{
					//상무병원 71251,71252,71259 copies갑만보고, 0008300 값건너뛰기
					continue;
				}
				if (resultType[i].trim().equals("C")) {
					data[8] = getUnit(unit[i]).trim();
					data[9] = getReference(new String[] { inspectCode[i], lang[i], history[i],sex[i],age[i] }).trim();
					if(hosCode[i].toString().trim().equals("20971"))
					{
						//상무병원은 참고치란에 줄바꿈기호를 '@^' -> ' @'로변경
						data[9] = data[9].replaceAll("@", " ");
						data[9] = data[9].replaceAll("\\^", "@");
						//상무병원 참고치 공백이 너무 많아 공백 2개를 1개로 변경 처리 
						data[9] = data[9].replaceAll("  ", "");
					}
					data[4] = result[i].trim();
					if (inspectCode[i].trim().length() == 7 && !(hosCode[i].toString().trim().equals("20971") && (inspectCode[i].trim().substring(0, 5).equals( "71251")
							|| inspectCode[i].trim().substring(0, 5).equals( "71252")|| inspectCode[i].trim().substring(0, 5).equals( "71259")|| inspectCode[i].trim().substring(0, 5).equals( "00083")))) {
						//상무병원은 부속중  71251,71252,71259, 00083은 숫자형태로 보고하도록함
						data[5] = appendBlanks("검  사  명 ", 30) + "   "+ appendBlanks("결    과", 21) + "   "+ "참    고    치";
						data[5] += "@^" + appendBlanks(inspectName[i], 30)	+ "   " + appendBlanks(data[4], 21) + "   "+ data[9];
						data[4] = "";
						data[8] = "";
						data[9] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[5] += "@^"+ appendBlanks(inspectName[i], 30)+ "   "+ appendBlanks(result[i], 21)+ "   "+ getReference(new String[] { inspectCode[i],	lang[i], history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
				} else {
					if (hosCode[i].toString().trim().equals("20119")	|| hosCode[i].toString().trim().equals("15152")|| hosCode[i].toString().trim().equals("15153")) 
					{
						// 충주건대는 문장결과 트림 문장결과에 @^ 들어감
						
						data[5] = getTextResultValue2(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();
					} else if(hosCode[i].toString().trim().equals("20971")){
						data[5] = getTextResultValue2(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();					
						
						if(data[5].indexOf("< Pathological Diagnosis") > -1){
							int temp = data[5].indexOf("병리번호:");
							String byoNum = "";
							if(temp>0)
								byoNum = data[5].substring(temp+5, temp+17);
							data[5] = "병리작업번호:"+byoNum+
									  "@^" + "검체 채취일:"+  Common.change_day1(cutHrcvDateNumber2(cns[i])[1]) + 
									  "@^" + "검사기관 접수일: "+ Common.change_day1(rcvDate[i]) +
									  "@^" + "검사기관 보고일: "+ Common.change_day1(bdt[i]) +
									  //"@^" + "챠트번호: "+ chartNo[i] +
									  "@^" + "의뢰정보: 상무병원" +
									  //"@^" + "수진자명: "+ patName[i] +
									  "@^" + "나이/성별: "+ age[i] + " / " + sex[i] +
									  //"@^" + "검사항목: "+clientInspectCode[i] +
									  "@^" + getTextResultValue2(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim().toString()
									  .replace("@^<", "@^@^<")
									  .replaceAll("<Specimen Adequacy                  >", "<Specimen Adequacy>")
									  .replaceAll("<Result                             >", "<Result>")
									  .replaceAll("<General Categorization             >", "<General Categorization>")
									  .replaceAll("<Interpretation Result              >", "<Interpretation Result>")
									  .replaceAll("<Recommendation                     >", "<Recommendation>")
									  .replaceAll("<Pathological Diagnosis             >", "<Pathological Diagnosis>")
									  .replaceAll("<Gross Description                  >", "<Gross Description>")
									  .replaceAll("<Non-Gyn Cytology                   >", "<Non-Gyn Cytology>")
									  .replaceAll("<Microscopic Findings               >", "<Microscopic Findings>")
									  .replaceAll("<Note                               >", "<Note>");

						}
						else if((data[5].indexOf("<Pathological Diagnosis") > -1 || data[5].indexOf("Adequacy of the Specimen") > -1 || data[5].indexOf("<Specimen Adequacy") > -1 ) 
								&& data[5].indexOf("병리번호:") > -1){
						//상무병원 조직,세포병리 결과에 추가정보  입력
						//병리번호 결과에서 추출하기
						int temp = data[5].indexOf("병리번호:");
						String byoNum = "";
						if(temp>0)
							byoNum = data[5].substring(temp+5, temp+17);
						
						data[5] = "병리번호:"+byoNum+
								  "@^" + "검체 채취일:"+  Common.change_day1(cutHrcvDateNumber2(cns[i])[1]) + 
								  "@^" + "검사기관 접수일: "+ Common.change_day1(rcvDate[i]) +
								  "@^" + "검사기관 보고일: "+ Common.change_day1(bdt[i]) +
								  //"@^" + "챠트번호: "+ chartNo[i] +
								  "@^" + "의뢰정보: 상무병원" +
								  //"@^" + "수진자명: "+ patName[i] +
								  "@^" + "나이/성별: "+ age[i] + " / " + sex[i] +
								  //"@^" + "검사항목: "+clientInspectCode[i] +
								  "@^" + getTextResultValue2(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim().toString()
								  .replace("@^<", "@^@^<")
								  .replaceAll("<Specimen Adequacy                  >", "<Specimen Adequacy>")
								  .replaceAll("<Result                             >", "<Result>")
								  .replaceAll("<General Categorization             >", "<General Categorization>")
								  .replaceAll("<Interpretation Result              >", "<Interpretation Result>")
								  .replaceAll("<Recommendation                     >", "<Recommendation>")
								  .replaceAll("<Pathological Diagnosis             >", "<Pathological Diagnosis>")
								  .replaceAll("<Gross Description                  >", "<Gross Description>")
								  .replaceAll("<Non-Gyn Cytology                   >", "<Non-Gyn Cytology>")
								  .replaceAll("<Microscopic Findings               >", "<Microscopic Findings>")
								  .replaceAll("<Note                               >", "<Note>");

	
							
						}
						else if( data[5].indexOf("세포번호:") > -1){  // data[5].indexOf("<Pathological Diagnosis") > -1 || data[5].indexOf("Adequacy of the Specimen") > -1 || data[5].indexOf("<Specimen Adequacy") > -1 || 20201027
							
							int temp1 = data[5].indexOf("세포번호:");
							String byoNum = "";
							if(temp1>0)
								byoNum = data[5].substring(temp1+5, temp1+17);
							data[5] = "세포번호:"+byoNum+
									  "@^" + "검체 채취일:"+  Common.change_day1(cutHrcvDateNumber2(cns[i])[1]) + 
									  "@^" + "검사기관 접수일: "+ Common.change_day1(rcvDate[i]) +
									  "@^" + "검사기관 보고일: "+ Common.change_day1(bdt[i]) +
									  //"@^" + "챠트번호: "+ chartNo[i] +
									  "@^" + "의뢰정보: 상무병원" +
									  //"@^" + "수진자명: "+ patName[i] +
									  "@^" + "나이/성별: "+ age[i] + " / " + sex[i] +
									  //"@^" + "검사항목: "+clientInspectCode[i] +
									  "@^" + getTextResultValue2(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim().toString()
									  .replace("@^<", "@^@^<")
									  .replaceAll("<Specimen Adequacy                  >", "<Specimen Adequacy>")
									  .replaceAll("<Result                             >", "<Result>")
									  .replaceAll("<General Categorization             >", "<General Categorization>")
									  .replaceAll("<Interpretation Result              >", "<Interpretation Result>")
									  .replaceAll("<Recommendation                     >", "<Recommendation>")
									  .replaceAll("<Pathological Diagnosis             >", "<Pathological Diagnosis>")
									  .replaceAll("<Gross Description                  >", "<Gross Description>")
									  .replaceAll("<Non-Gyn Cytology                   >", "<Non-Gyn Cytology>")
									  .replaceAll("<Microscopic Findings               >", "<Microscopic Findings>")
									  .replaceAll("<Note                               >", "<Note>");

						}
						
						
					} else {
						data[5] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]).trim();
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
					o_writer = new OutputStreamWriter(f_outStream,"MS949");   // 인코딩 수정후
//					o_writer = new OutputStreamWriter(f_outStream);            // 인코딩 수정전
					b_writer = new BufferedWriter(o_writer);
				}
				//상무병원(20971)의 경우 검체번호에 처방일자+검체번호(cnsDate+data[2]) 형태로 출력한다.
				if(hosCode[i].toString().trim().equals("20971"))
				{
					text.append(data[0] + deli + data[1] + deli + cnsDate+data[2] + deli + data[3] + deli + data[4] + deli + data[5].toString() + deli + data[6] + deli + data[7] + deli + data[8] + deli + data[9] + deli + data[10] + deli + "\r\n");
				}
				else
				{
					text.append(data[0] + deli + data[1] + deli + data[2] + deli + data[3] + deli + data[4] + deli + data[5].toString() + deli + data[6] + deli + data[7] + deli + data[8] + deli + data[9] + deli + data[10] + deli + "\r\n");
				}
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

package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;

/*
 엑셀
 */
public class DownloadFoundation extends ResultDownload {
	boolean debug = false;
	boolean isTxtRltB = false;
	boolean isTxtRltC = false;
	boolean isNext = false;
	private DLDataModel dataModel;
	public boolean isNext() {
		return isNext;
	}

	public void setNext(boolean isNext) {
		this.isNext = isNext; 
	}

	int lastindex = 0;


	String lastData = "";
	public String getLastData() {
		return lastData;
	}

	public void setLastData(String lastData) {
		this.lastData = lastData;
	}

	public int getLastindex() {
		return lastindex;
	}

	public void setLastindex(int lastindex) {
		this.lastindex = lastindex;
	}


	public boolean isTxtRltC() {
		return isTxtRltC;
	}

	public void setTxtRltC(boolean isTxtRltC) {
		this.isTxtRltC = isTxtRltC;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isTxtRltB() {
		return isTxtRltB;
	}

	public void setTxtRltB(boolean isTxtRltB) {
		this.isTxtRltB = isTxtRltB;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCurGcd() {
		return curGcd;
	}

	public void setCurGcd(String curGcd) {
		this.curGcd = curGcd;
	}




	String curDate = "";
	String curNo = "";
	String curGcd = "";
	//
	// private String gubun1 = 
	// "\n============================================================\n";
	// private String gubun2 =
	// "\n------------------------------------------------------------\n"; 
	// private java.text.DecimalFormat df = new
	// java.text.DecimalFormat("#######0.0");
	//
	// private com.neodin.result.PatientInformation mPatientData;
	public DownloadFoundation() {
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
	
	

	public DownloadFoundation(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
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

	


	protected String getRemarkTxt2(String str[]) {
		StringBuffer b = new StringBuffer("");
		boolean isSensi = false;
		if (str.length == 0)
			return null;

		for (int i = 0; i < str.length; i++) {
			int kk = str[i].trim().lastIndexOf("<균　명>");
			if (kk > -1) {
				b.append("\r\n" + "<균 명 >" + "\r\n\r\n");
				b.append(str[i].trim().substring(5).trim() + " \r\n");
				isSensi = true;
			} else {
				if (isSensi && str[i].trim().trim().startsWith("1")) {
					b.append("                                         " + str[i].trim().trim() + "\r\n\r\n");
				} else {
					b.append(str[i].trim() + "\r\n");
				}
			}
		}
		return b.toString().trim();
	}



	protected synchronized String getTextResultValue2(String hos, String date, String jno, String rcd) {
		String result = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과")).processDpc(new Object[] { hos, date, jno, rcd })) {
				return "";
			}
			String ArrayResult[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("문장결과")).getParm().getStringParm(5));
			result = getRemarkTxt2(ArrayResult);
			if (result == null)
				result = "";
		} catch (Exception e) {
			return "";
		}
		return result;
	}



//	

	

	public void makeDownloadFile() {

		row = getStsrtResultRow() - 1;
		row2 = 1;
		int i = 0;
		// !
		try {
			String ArraryResult[] = null;
			ArraryResult = (getExcelFieldNameArray());
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
			e.printStackTrace();
		}
	}


	public void processingData(int cnt) {  
		
		try {
			setNext(false);

			dataModel = new DLDataModel(getDownloadData());

			// !
			String data[] = new String[45];
			String remark[] = new String[5];
			String remarkCode = "";
			boolean isTQ = false;
			int k = 0;
			String lastData = "";
			int lastindex = 0;


			 cnt = setInitialCount(cnt);

			for (int i = 0; i < cnt; i++) {

				setTxtRltB(false);
				data[0] = dataModel.getBdt(i); // 걍~~

				data[1] = Common.cutZero(dataModel.getImg(i)); // 내원번호
				data[2] = dataModel.getInc(i); // 외래구분
				data[3] = ""; // 의뢰일자
				data[4] = dataModel.getSpecNo(i).trim(); // 검체번호
				// TODO : 삭제할 부분
				
			
				try {
					if ("29034|26719|13952|26172|23661|27282|27693|27993|30099|28833|29005|30353|28615|28312|31129|21496|32622|28945".contains(dataModel.getHosCode(i).trim())) {
						data[5] = DFUtil.cutHrcvDateNumber2(dataModel.getCns(i))[0]; // 처방번호
					} else{	
						data[5] = DFUtil.cutHrcvDateNumber(dataModel.getCns(i))[0]; // 처방번호
					}
				} catch (Exception ee) {
					data[5] = "";
				} 
				
				
				data[6] = dataModel.getClientInspectCode(i).trim(); // 병원검사코드(처방코드)
				data[7] = dataModel.getInspectName(i); // 검사명(처방명)
				data[8] = dataModel.getHosSamp(i); // 검체명(검체코드)

				// !
				data[9] = dataModel.getBbseq(i); // 일련번호
				data[10] = ""; // 검체코드
				data[11] = ""; // 여유필드

				// !
				data[12] = dataModel.getChartNo(i); // 차트번호
				data[13] = dataModel.getPatName(i); // 수진자명
				data[14] = dataModel.getSecurityNo(i); // 주민번호

				data[15] = dataModel.getAge(i); // 나이
				
				data[16] = dataModel.getSex(i); // 성별
				data[17] = ""; // 과
				data[18] = ""; // 병동
				data[19] = ""; // 참고사항
				if ( DFUtil.isEnableData_19(dataModel.getHosCode(i), dataModel.getInspectCode(i)))
				 { // 단독
					
					data[19] = data[5] +".jpg";
					// !
				}

				data[20] = ""; // 학부
			
				
				switch(dataModel.getHosCode(i).trim()) {
				case "22001" : 
					data[21] = Common.getDateFormat(dataModel.getBdt(i)); // 검사완료일
					break;
				case "25493" :
					// data[21] = Common.change_day1(data[21]); //양태용 수정 20190129
					data[21] = Common.change_day1(dataModel.getBdt(i));
					break;
				default : 
					data[21] = dataModel.getBdt(i); // 검사완료일
					break;
					
				}
				
				
				try {
					//처방일자에 처방코드 넣는것~
					switch(dataModel.getHosCode(i).trim()) {
					case "25791":
					case "13725":
					case "26454":
					case "29504":
					case "26800":
					case "18535":
					case "31844":
					case "32511":	//20190228 양태용 비트엑셀 차트
					case "33470": //비트엑셀 연동
					case "14565"://비트 차트 처방일
					case "14166"://비트 차트 처방일 
					case "34115"://비트 차트 처방일 
					case "13804"://비트 차트 처방일
					case "14211"://비트 차트 처방일
					case "30300":
					case "30886":
					case "31014":
					case "31064":
					case "31818":
					case "10786":	//양태용 20190131 추가
						data[22] = dataModel.getBgcd(i); // 처방일자
						break;
					case "29034":
					case "26719":
					case "26172":
					case "23661":
					case "27282":
					case "27693":
					case "27993":
					case "28615":
					case "28312":
					
						data[22] = DFUtil.cutHrcvDateNumber2(dataModel.getCns(i))[1]; // 처방일자
						break;	// break 누락으로 예상 양태용
					case "25493" :
						data[22] = dataModel.getBgcd(i); // 처방일자
						data[22] = Common.change_day1(data[22]); // 검사일자
						break;
					default :
						data[22] = DFUtil.cutHrcvDateNumber(dataModel.getCns(i))[1]; // 처방일자
						break;
						
						
					}
					
				} catch (Exception eee) {
					data[22] = ""; // 처방일자
				}
				
			
				// !
				data[23] = ""; // 혈액형
				data[24] = "";
				data[26] = ""; // 진료의사
				data[27] = ""; // 추가키1
				data[28] = ""; // 추가키2
				data[29] = "";

				// !
				data[30] = "11370319"; // 요양기관번호
				data[31] = dataModel.getRcvDate(i).trim(); // 접수일자
				data[32] = dataModel.getRcvNo(i).trim(); // 접수번호
				data[33] = dataModel.getInspectCode(i).trim(); // 검사코드
				data[34] = ""; // 단문결과
				// !
				data[35] = ""; // 문장결과
				data[36] = ""; // 수치+문장
				data[37] = ""; // 상태
				data[38] = ""; // 리마크
				data[39] = ""; // 참고치
				
				//20190523
				if (!isTxtRltB()  && dataModel.getInspectCode(i).trim().length() == 5 )
					switch( dataModel.getHosCode(i).trim()) {
					case "32679" : if ("31108|31109|31110|31111|31113|31126|31115|31104|31112|31106|31114".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
						data[19]="https://www.seegenemedical.com/resultImg/32679/"+ data[12] + "_" + data[13] + "_" + data[4] + "_" + data[6] + "_01.jpg";
						break;
			
				}
				
				
				setReferUnit( data, i);
				// !
				try {
					
					setRemarkArray( remark, i);
					data[39] = getReferenceValueNotBlank(remark);
				} catch (Exception e) {
					data[39] = "";
				}

				
				
				
				/**(S)결과 건너뛰기**/
				// 분당재생병원 건너뛰기
				
				if (DFUtil.isContinue(dataModel.getHosCode(i), dataModel.getInspectCode(i))) { // 단독
						continue;
				}
				
			//TODO 	
//				// 거제굿뉴스 0009501만 보이기
//				if (continueHos29154(hosCode, dataModel.getInspectCode(), i)) {
//					continue;
//				}
//				
//				if (continueHos29154_1(hosCode, dataModel.getInspectCode(), i)) {
//					if(data[35].indexOf("중　간　결　과　보　고")>=0)
//					{
//						continue;
//					}
//					
//				}
//

				
				
				if (
						(!dataModel.getHosCode(i).trim().equals("31907") && !dataModel.getHosCode(i).trim().equals("31161")) && DFUtil.isSpecialTest(dataModel.getInspectCode(i), 0)) { // 단독
					continue;
				}
				
				
				if (dataModel.getHosCode(i).trim().equals("29567")&&DFUtil.isSpecialTest(dataModel.getInspectCode(i), 1)) { // 단독
					continue;
				}
				
							
				
				if (dataModel.getResultType(i).trim().equals("C")) {
					data[34] = dataModel.getResult(i).trim(); // 문자결과
					data[36] = dataModel.getResult(i).trim();
					setRemarkArray( remark, i);
					data[35] = "";
			
					if (!isTxtRltB() && isTriple(dataModel.getInspectCode(i).trim().substring(0, 5)) ) { 
							
						if (!dataModel.getHosCode(i).trim().equals("25493") && !dataModel.getHosCode(i).trim().equals("20231") ) {
							i = transformRltToTxt25( data, i);
						} 
						if ( dataModel.getHosCode(i).trim().equals("25493")) {
							data[39] = "별지참조"; // 참고치
						}
								
					}


					if (!isTxtRltB() && isPSTS(dataModel.getInspectCode(i).trim().substring(0, 5)) && (!dataModel.getHosCode(i).trim().equals("25493"))) {
						i = transformRltToTxt26( data, i);
					}
					
				
					if (!isTxtRltB() && "31844|30886".contains(dataModel.getHosCode(i).trim())
							
									&& dataModel.getInspectCode(i).trim().length() == 7 ) { // 단독

						i = transformRltToTxt28(cnt,data, remark, i);
					}
					
					//20160830 양태용 00217 단문 -> 장문
					if (!isTxtRltB() && dataModel.getInspectCode(i).trim().length() == 7 ) { // 단독

						switch(dataModel.getHosCode(i).trim()) {
						case "12657" : if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("00217"))
							i = transformRltToTxt(cnt, data, remark, i);
							break;
						case "29598":
						case "31109":
						case "19115":
							if ("00095|31001|21677|71252".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt(cnt, data, remark, i);
							break;
						case "29154" : if ("00095|31001|00901|72245".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
							
								i = transformRltToTxt(cnt, data, remark, i);
							break;
						//	20190228 양태용 병원측 요청으로 72185/72020 장문 처리
						case "31918": if ("72185|31001|72020|00802|11002".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
							i = transformRltToTxt(cnt, data, remark, i);
						break;
//						20190930 수원 중앙병원 요청으로 해당검사만 장문으로 
						case "14565": if ("71311|31001|72020|00690|00691".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
							i = transformRltToTxt(cnt, data, remark, i);
						break;
						case "31798": 
							if ("31001|21638".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
								 i = transformRltToTxt(cnt, data, remark, i);
								break;
								
						case "31865"://서울대효병원 (금천 ) , 그람스테인 문장형태로 변경 (31001) 20151012
							 if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("31001")) 
								 i = transformRltToTxt(cnt, data, remark, i);
								break;
								
						case "29453":
							if ("31010|31001|11301".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
									i = transformRltToTxt(cnt, data, remark, i);
								break;
						case "31259":
							if ("31010|31001|11301|72206|72242|00901".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) )
								i = transformRltToTxt(cnt, data, remark, i);
							break;
						case "27097": //30042 병원 그람스테인 단문 -> 장문 20161013 양태용
						case "30042": if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("31001"))
							i = transformRltToTxt(cnt, data, remark, i);
							break;
						case "23535" :
							if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("21061")) 
								i = transformRltToTxt(cnt, data, remark, i);
								break;
						case "25531" : if ("00095|11052|00304".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt(cnt, data, remark, i);
							break;
						case "23741" : if ("31001|11301|05481|20231|05483|71251|00309|05567|21677|72194|71252".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))		
									i = transformRltToTxt(cnt, data, remark, i);

								break;
						case "28099" : if ("72185|72245|72182|72189|72242".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))		
							i = transformRltToTxt4(cnt, data, remark, i);

						break;
						case "25184" : if ("31001|11301|05481|00309|00901|21068|71251|71252|72182|72189|72242|72245|81469".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							
							i = transformRltToTxt(cnt, data, remark, i);
							break;
						case "30039" :
							if ((dataModel.getInspectCode(i).trim().substring(0, 5).equals("31001")||dataModel.getInspectCode(i).trim().substring(0, 5).equals("31010")
											||dataModel.getInspectCode(i).trim().substring(0, 5).equals("00927")||dataModel.getInspectCode(i).trim().substring(0, 5).equals("72192"))) { // 단독
								i = transformRltToTxt(cnt, data, remark, i);

								// !
							} 
							break;
						case "26172":
						case "30120":
						case "32622":
							if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("72182") || dataModel.getInspectCode(i).trim().substring(0, 5).equals("72245")
											|| dataModel.getInspectCode(i).trim().substring(0, 5).equals("72189") || dataModel.getInspectCode(i).trim().substring(0, 5).equals("72242")) { // 단독

								i = transformRltToTxt(cnt, data, remark, i);

								// !
							}
							break;
						case "22874":					

							if ("31001|11301|05481|05483|71252|21638|71251|00309|72059|00095|72018|72185|72227|72228|72229|72230|72231|72232|72233|72234|72235|72236|72237|72238".contains(dataModel.getInspectCode(i).trim().substring(0, 5))
									 || "|05567|21677|72020|00804|72226|72182|72245".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt(cnt, data, remark, i);
							break;
							

						case "29561":
							
							if ("72185|72220".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							 { // 단독
								i = transformRltToTxt(cnt, data, remark, i);
							}
							break;
							
						case "33114":
							if ("72185|72245|11002|11003|72182|72189|72242".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							 { // 단독
								i = transformRltToTxt(cnt, data, remark, i);
							}
							break;
						case "26260": if ("31001|31003|11301|71252|71259|71252|72018|72241|72242|72206|72245|72182|72194|00083|00405|72227|72237".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt(cnt, data, remark, i);
						   break;
						case "16805": if ("71259|71252|72182|21677|31001|21068|21650|21604|72255".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt(cnt, data, remark, i);
						   break;
						case "20231": if ("05483|72185|21638|05567|31001|72182|72020|21677|71252|72227".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt(cnt, data, remark, i);
						   break;
						case "31393":
						case "31395":
							if ("72185|21638|05483|71311|05567".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
									i = transformRltToTxt(cnt, data, remark, i);
							   break;
						 
						   
						default : 
							
							
						}
						
					}
				
					
					//29567 윤호21병원(진단)
					if (!isTxtRltB() && "29567|32015".contains(dataModel.getHosCode(i).trim()) 
							 && dataModel.getInspectCode(i).trim().length() == 7 
							&&  "11301|31001".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) { // 단독

					
						i = transformRltToTxt28(cnt, data, remark, i);
									
								
					}			
					
					//33645
					if (!isTxtRltB() && "33645".contains(dataModel.getHosCode(i).trim()) 
							 && dataModel.getInspectCode(i).trim().length() == 7 
							&&  "31001".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) { // 단독

					
						i = transformRltToTxt28(cnt, data, remark, i);
									
								
					}	
					
					//32316
					if (!isTxtRltB() && "32316".contains(dataModel.getHosCode(i).trim()) 
							 && dataModel.getInspectCode(i).trim().length() == 7 
							&&  "21677".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) { // 단독

					
						i = transformRltToTxt(cnt, data, remark, i);
									
								
					}
					if (!isTxtRltB() && "31844|30886".contains(dataModel.getHosCode(i).trim())
							
							&& dataModel.getInspectCode(i).trim().length() == 7 ) { // 단독

						i = transformRltToTxt28(cnt, data, remark, i);
					}
									
					if (!isTxtRltB() ) {
						switch (dataModel.getHosCode(i).trim()) {
						case "27021": 
							if ("31001|71259|72194|72241|72245|72182|72018|21650|71311|71256|71325|21677".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) {
								i = transformRltToTxtWithRemark(cnt, data, remark, i);
							}
							break;
						case "29005": 
							if (dataModel.getInspectCode(i).trim().length() == 7 && "31001|21380|21677|00901|71136|21382".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) {
								i = transformRltToTxtWithRemark(cnt, data, remark, i);
							}
							break;
						case "28615": 
							if (dataModel.getInspectCode(i).trim().length() == 7 && "21677".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) {
								i = transformRltToTxtWithRemark(cnt, data, remark, i);
							}
							break;
						}
					}
							
					
					
					
					if (!isTxtRltB()	&& dataModel.getHosCode(i).trim().equals("27021")&& 
							dataModel.getInspectCode(i).trim().substring(0, 5).equals("21297")) {
						transformRltToTxt29( data, remark, i);
					}
					
							
					if (!isTxtRltB() && dataModel.getHosCode(i).trim().equals("25531") && (dataModel.getInspectCode(i).trim().equals("31059"))) {
						transformRltToTxt30( data, i);
					}
					if (!isTxtRltB()
							&& (dataModel.getHosCode(i).trim().equals("27282")||dataModel.getHosCode(i).trim().equals("27693"))&&
							(dataModel.getInspectCode(i).trim().substring(0, 5).equals("41097")||
									dataModel.getInspectCode(i).trim().substring(0, 5).equals("41114"))) {
						transformRltToTxt31(data, remark, i);
					}
					

					
					if (!isTxtRltB()  && dataModel.getInspectCode(i).trim().length() == 7 )
						switch( dataModel.getHosCode(i).trim()) {
						case "24516" : if ("31001|71252|11052".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt8(cnt,  data, remark, i);
							break;
						case "29034":
						case "26719":
						case "23661":
						case "27993":
						case "28312":
						case "30353":
						case "28351":
							if ("31001|71259|71246|31003|00309|72194|11101|21065|31005|72182|21380|72261".contains( dataModel.getInspectCode(i).trim().substring(0, 5)))
							{ // 단독아님 26719 병원 때문에 20170210 추가함 21380검사~ 양태용
							i = transformRltToTxt8(cnt, data, remark, i);
							}
							break;
				
					}
					
					if (!isTxtRltB()  && dataModel.getInspectCode(i).trim().length() == 7 )
						switch( dataModel.getHosCode(i).trim()) {
						case "32679" : if ("11101".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt12(cnt,  data, i);
							break;
				
					}
										
					//모든부속검사 장문 처리 20190402 32610 김내과의원
					if (!isTxtRltB()  && dataModel.getInspectCode(i).trim().length() == 7 )
						switch( dataModel.getHosCode(i).trim()) {
						case "32610" : 
							if (!"00095".contains( dataModel.getInspectCode(i).trim().substring(0, 5)))
							{
							i = transformRltToTxt8(cnt,  data, remark, i);
							}
							break;
				
					}
					
					
				
					//특정검사 장문 처리 20190406 29567 윤호21병원
					if (!isTxtRltB()  && dataModel.getInspectCode(i).trim().length() == 7 )
						switch( dataModel.getHosCode(i).trim()) {
						case "29567" : 
							if ("71325|72182|72189|72237|21677|00690|00691|72227|72228|72229|72230|72231|72232|72232|72233|72234|72235|72236".contains( dataModel.getInspectCode(i).trim().substring(0, 5))
									||"72237|72238|72239|72240|72241|72242|72243|72244|72245".contains( dataModel.getInspectCode(i).trim().substring(0, 5)))
							{
							i = transformRltToTxt8(cnt,  data, remark, i);
							}
							break;
				
					}
					
					
							
				
					if (!isTxtRltB() && dataModel.getHosCode(i).trim().equals("24434") && dataModel.getInspectCode(i).trim().length() == 7
							&& (dataModel.getInspectCode(i).trim().substring(0, 5).equals("00405"))) {
						// ! 청아병원
						i = transformRltToTxt21(cnt,  data, remark, i);

						// !
					}
				
					
					
					if (!isTxtRltB() && dataModel.getInspectCode(i).trim().length() == 7 && !dataModel.getHosCode(i).trim().equals("12640")
							&& isHBV(dataModel.getInspectCode(i).trim().substring(0, 5))) { // 단독
						i = transformRltToTxt20(cnt, data, i);
						// !
					}
					
					if (!isTxtRltB() && dataModel.getInspectCode(i).trim().length() == 7 ) {
						switch( dataModel.getHosCode(i).trim()) {
						case "12346":
							if ("00309|00317|21650|21677|71259|72245|72227|72229|72234|72236|72241|11234|72182|31001".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt16(cnt, data, remark, i);
							break;
						case "31073":
							if ("11101|31001|00095|11231".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt16(cnt,  data, remark, i);
							break;
						default : if (!"12640|24516|29171|22015|26082|".contains(dataModel.getHosCode(i).trim()) && "11052".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							
							i = transformRltToTxt16(cnt,  data, remark, i);
							break;
						}
				
						
					}
					
					
				
					if (!isTxtRltB()
							&& dataModel.getInspectCode(i).trim().length() == 7) {
						switch(dataModel.getHosCode(i).trim()) {
						case "25791":
						case "13725":
						case "26800":
						case "18535":
						case "26454":	
						case "12640":								//20190215 추가 양태용
							if ("00405|00095".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt14(cnt, data, remark, i);
							break;

						
						case "29167":
						case "23741":
							if ("00095".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt14(cnt, data, remark, i);
							break;
						
						case "20720":
								if ("71251|71252".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
									i = transformRltToTxt14(cnt, data, remark, i);
							break;
							
						
						case "13406":
						case "12770":
							if ("00091|00095|00752|72059|72018".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt14(cnt, data, remark, i);
							break;
						
						default :
						
							//  첫번째 부속값만
							// 이해안됨! 71251,71252,00752,22023,72059,72018 첫번째 부속값만 출력되도록
							//20161028 양태용 예스병원(안산) 71252 02번 노출 되도록 제외처리함
							if (!"24434|29167|22015|22874|23211|12657|25815|25184|27282|27693|26454|29171|25560|28351|29005|31259|31818|14166|13804|14211".contains(dataModel.getHosCode(i).trim()) &&
								"71251|71252|00752".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt14(cnt, data, remark, i);
							break;
							
						
						}
					}
						
					
					if (!isTxtRltB() && dataModel.getInspectCode(i).trim().length() == 7) {
						switch (dataModel.getHosCode(i).trim() ) {
						case "24516" :
						case "26406" : // ! 청아병원
							if  ("00095|71251".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt18(cnt,data, remark, i);
							break;
						case "30495": //20170404 30495 진도 한국병원 때문에 11052 추가하여 처리 
							if ("31001|11301|11052|71297|72236".contains( dataModel.getInspectCode(i).trim().substring(0, 5)))
									i = transformRltToTxt17(cnt,  data, remark, i);	
							break;
						case "12770" :
						case "25963" :
							if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("11101"))
									i = transformRltToTxt15(cnt, data, remark, i);
							break;
						case "25791":
						case "26454":
						case "13725":
						case "26800":
						case "18535":  // 이 부분을 잘 테스트 해야 함.
							if (dataModel.getInspectCode(i).trim().equals("1110102")) 
								continue;
							break;
						default :	
						}
				
					}
					
					
					//두손병원 결과에 참고치 추가 요청
					if (!isTxtRltB()
							&& (dataModel.getHosCode(i).trim().equals("13725")) && 
							(dataModel.getInspectCode(i).trim().substring(0, 5).equals("81370")||(dataModel.getInspectCode(i).trim().substring(0, 5).equals("64095")))) { // 단독
						transformRltToTxt32( data, remark, i);
					}

					// 마스트 검사
					if (!isTxtRltB() && (isMAST_Two(dataModel.getInspectCode(i).trim().substring(0, 5)) || isMAST(dataModel.getInspectCode(i).trim().substring(0, 5)))&&//
					   (// !dataModel.getHosCode(i).trim().equals("21954") && 20190109  사용 안하는거래처 양태용  
							   !dataModel.getHosCode(i).trim().equals("22015") && !dataModel.getHosCode(i).trim().equals("12640")
							   && !dataModel.getHosCode(i).trim().equals("26082")//&& !dataModel.getHosCode(i).trim().equals("30355")20190109  사용 안하는거래처 양태용
							   )) { 
						try {// 삼육제외
							Vector<String> vmast = new Vector<>();
							setTxtRltB(true);
							data[34] = "";   
							data[39] = "";
							data[35] = appendBlanks("검사항목", 30) + appendBlanks("CLASS", 8) + appendBlanks("검사항목", 30) + appendBlanks("CLASS", 8);
							data[35] += getDivide() + "\r\n";
							data[36] = data[35].trim();
							vmast.addElement(appendBlanks(dataModel.getInspectName(i).trim(), 30) + appendBlanks(dataModel.getResult(i).trim(), 8));   
							setCurDate(dataModel.getRcvDate(i));  
							setCurNo(dataModel.getRcvNo(i));		
							curGcd = dataModel.getInspectCode(i).substring(0, 5);	
							int mastad =0;
							if (i+1 == cnt || i > cnt){
								break;
							}
							for (String thisTimeCode = dataModel.getInspectCode()[i++].trim().substring(0, 5); thisTimeCode	.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
								try {		
									   if(mastad==0)
				                        {
				                           vmast.addElement(getRPad(dataModel.getInspectName(i).trim(), 26) + getRPad(dataModel.getResult()[i++].trim().trim(), 6));
				                        }else
				                        {
				                        	 if(isMastDuplPrint(dataModel.getInspectCode(i).trim()))
												 i++;
					                           else
					                        	   vmast.addElement(getRPad(dataModel.getInspectName(i).trim(), 26) + getRPad(dataModel.getResult()[i++].trim().substring(0, 1), 6));
				                        }
								} catch (Exception e) {
								}
								if (++i == cnt || i > cnt){
									i--;
									break;
								}
								i--;
								mastad++;

							}
						
							if(dataModel.getHosCode(i).trim().equals("29171")){
								data[35] = getResultMAST_Two(data[35].toString(), vmast) + getDivide() + "\r\n" +getMastRemark();//getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
							}else {
								data[35] = getResultMAST_Two(data[35].toString(), vmast) + getDivide() + "\r\n" + getMastRemark();
							}
							data[36] = data[35].trim();
							i--;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
 				
				
					if (!isTxtRltB() && dataModel.getInspectCode(i).trim().length() == 7 ) { // 단독
						switch(dataModel.getHosCode(i).trim()) {
						case "23586" : i = transformRltToTxt4(cnt,  data, remark, i);
							break;	
						case "27456" :	if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("72185")) 
							i = transformRltToTxt4(cnt,  data, remark, i);
								break;
						case "30336":
							if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("72225")) 
								i = transformRltToTxt4(cnt,  data, remark, i);
								break;
						case "12770": if ("11026|00091|00095|00804".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
							i = transformRltToTxt4(cnt,  data, remark, i);
								break;
						case "31818": if ("71251|71252|31001".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
							i = transformRltToTxt4(cnt,  data, remark, i);
							break;
						case "29005" : //병원측 요구로 특정검사 리마크 빼고 문장으로 처리 요청 20170918 양태용
							if ("71252|71259|71018|72227|72228|72229|72230|72231|72232|72233|72234|72235|72236|72242|72189|72018".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt4(cnt,  data, remark, i);
							break;
						case "22262" :	if (!dataModel.getInspectCode(i).trim().equals("11101"))
							i = transformRltToTxt4(cnt,  data, remark, i);
							break;
						
						
						case "25791" : if ("21683".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								i = transformRltToTxt4(cnt,  data, remark, i);
							break;
							
						case "13804" : if ("00309|21677".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
							i = transformRltToTxt4(cnt,  data, remark, i);
						break;		
						
						case "34115" : if ("00095".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
							i = transformRltToTxt4(cnt,  data, remark, i);
						break;	

						
						case "10786":
						case "31064" : 
							if ("00309|00323|21068|31001|72194|72261".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								
								i = transformRltToTxt4(cnt,  data, remark, i);
							break;
						case "30099":
							if ("00309|00323|21068|31001|21650|21649|11234|71259|00301|72227|72228|72229|72230|72231|72232|72233|72234|72235|72236".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								
								i = transformRltToTxt4(cnt,  data, remark, i);
							break;									
						default : 
									
						}
						
					}
					// ========================================== 20190130 양태용 수정
					if (isTxtRltB()) {
						switch(dataModel.getHosCode(i).trim()) {
						case "30336":
							if ( dataModel.getInspectCode(i).trim().substring(0, 5).equals("00690")) { //
								setTxtRltB(true);
								data[35] = "결과완료(이미지결과)";
							}
							break;
						}
					}
					//==========================================
					if (!isTxtRltB()) {
						switch(dataModel.getHosCode(i).trim()) {
						case "20720" :
							setTxtRltB(true);
							if (dataModel.getResult(i).trim().toUpperCase().indexOf("NEGATIVE") > -1) {
								data[34] = dataModel.getResult(i).trim().substring(8) + " (음성)"; // 문자결과 NEGATIVE => 한글로
								data[36] = data[34];
							} else if (dataModel.getResult(i).trim().toUpperCase().indexOf("POSITIVE") > -1) {
								data[34] = dataModel.getResult(i).trim().substring(8) + " (양성)"; // 문자결과
								data[36] = data[34];
							}
							break;
						
						case "12640" :
							remarkCode = transformRltToTxt5(data, remark, remarkCode, i);
							break;
							
						case "27282":
						case "27693":
							if (dataModel.getInspectCode(i).trim().length() == 7 &&	"71251|11301|71252|31001".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
								i = transformRltToTxt12(cnt, data, i);
							break;
						case "12770":
							if ("11101".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								i = transformRltToTxt9(cnt, data, remark, i);
							break;
						}
					}

						
					if (!isTxtRltB() ) {
							switch(dataModel.getHosCode(i).trim()) {
							case "28280":
							case "22015":
							case "18468":  if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("72242"))
								
								i = transformRltToTxt7(cnt,  data, remark, i);
								break;
							case "23913":
								if ("00095|11101|31001".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
									i =  transformRltToTxt7(cnt,  data, remark, i);
									break;
							case "25659":
							case "25694":
								if ("00095|11101|31001|00806".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
									i =  transformRltToTxt7(cnt,  data, remark, i);
									break;
									//20190131 양태용 17178 병원 수정
							case "17178" :
								if (!"00095|11101|11002|11301|00822|00819|00802|00799|00312".contains(dataModel.getInspectCode(i).trim().substring(0, 5))
										 && dataModel.getInspectCode(i).trim().length() == 7)
									i =  transformRltToTxt7(cnt,  data, remark, i);
									break;
									//20190218 양태용 병원측 요구로 해당 검사만 장문으로
							case "31302" : 
								if ("00822|00095".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								i =  transformRltToTxt7(cnt,  data, remark, i);
								break;
									
								//20191218 오스정형외과 장문 처리
							case "33116" : 
								if ("00822|00806".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								i =  transformRltToTxt7(cnt,  data, remark, i);
								break;
								
							case "12770" : if ("00009|00095|72018|72059|71251|71252|".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								i =  transformRltToTxt7(cnt,  data, remark, i);
								break;
							case "24434" :
								if ("00009|72018|72059|71251|71252".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
									i = transformRltToTxt7(cnt,  data, remark, i);
									break;
									// 20190228 양태용 병원 요청으로 00095 검사 장문포함 처리
							case "24183" : 
								if ("00095|21683|11301".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
								i =  transformRltToTxt7(cnt,  data, remark, i);
									break;
							case "32028" :
								if  (!"00095".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) &&
										(dataModel.getInspectCode(i).trim().length() == 7))
									i =  transformRltToTxt7(cnt,  data, remark, i);
									break;
							case "29167":
							case "25815":	//20190130 양태용 추가
									if ("71252|00095|72059|72018|00938|72185".contains( dataModel.getInspectCode(i).trim().substring(0, 5))) 
										i =  transformRltToTxt7(cnt,  data, remark, i);
									break;
							default : 
										
						
								
							}
							
					}
					
							
					if (!isTxtRltB()) {
						switch(dataModel.getHosCode(i).trim()) {
						case "25815" : if  ("7125203|7129706".contains(dataModel.getInspectCode(i).trim()) ) {
								data[35] = "결과지참조";
								data[34] = ""; // 문자결과
								data[39] = ""; // 참고치
								setCurDate(dataModel.getRcvDate(i));
								setCurNo(dataModel.getRcvNo(i));
								setTxtRltB(true);
								data[36] = data[35].trim();
							}
						case "29825": if  (dataModel.getInspectCode(i).trim().substring(0, 5).equals("00911"))
							i = transformRltToTxt6(cnt, data, i);
							break;
						case "29171" : 	//희경의료재단 단문->장문
							if ("31001|71297|72185|11052|00804|72241|00901|00911|31083|00323|00309".contains(dataModel.getInspectCode(i).trim().substring(0, 5)) ||
									"71136|72020|72182|72227|21068|00951|72182|72232|72234|72236|21604".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
									i = transformRltToTxt33(cnt,  data, i);
							if (dataModel.getInspectCode(i).trim().equals("31059")) { //희경의료재단 31059 검사 결과내용을 Positive, Negative 만 입력되되록
								if(data[34].indexOf("Positive")>=0) data[34] = "Positive";
								else if(data[34].indexOf("Negative")>=0) data[34] = "Negative";
							}
							break;
						case "17989" : 
							if ("21598|21416|21445|21417|00509".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) {
								if ((i+1) == cnt || i > cnt)  //환자당 마지막 검사에만 출력되도록 
								{
									data[35] =getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i))+ IMethologyGetter.SEEGENE_INFO ;
								}
								else if(!(data[31].equals(dataModel.getRcvDate(i+1).trim()) &&data[32].equals(dataModel.getRcvNo(i+1).trim())))
								{
									data[35] = getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i))+ IMethologyGetter.SEEGENE_INFO;
								}
							}
							break;
						}
					}
							

					// ! 여기서 부터
					// 문장형--------------------------------------------------------------------------------------------------------------------------------------------------------
				} else {
					setTxtRltC(false);
					
					// 희경의료재단 컬쳐센시 로직이 동일해서 하단의 컬쳐 센시 로직에 희경만 예외 처리 예정 추후 확인 필요 20170921 양태용
					
					if (!isTxtRltC && dataModel.getHosCode(i).trim().equals("29171")) {
				
						if ("31010|00233|31100|31101|31102|31103|31104|31105|31106|31107|31108|31109|31110|31111|31112|31113|31114|31115|31116|31117|31118|31119|31120|31121|31122|31123|31124|31125|31126|31127|31128".contains(dataModel.getInspectCode(i).trim())) {
							setCtypeRltToTxt( data, i);
							
						} else {
							data[34] = ""; // 문자결과
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i)); // 문장결과
							data[36] = data[35].trim();
							data[39] = ""; // 참고치
						}
					}
				
					
					if (!isTxtRltC && dataModel.getHosCode(i).trim().equals("24434")) {
						setTxtRltC(true);
						switch(dataModel.getInspectCode(i).trim()) {
						case "61007" :
							data[35] = getTextResultValue_NGY(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i)).replaceAll(	"<Non-Gyn Cytology                   >\r\nSputum cytology\r\n", ""); // 문장결과
							break;
						default : 
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i)); // 문장결과
							
						}
						
						data[34] = ""; // 문자결과
						data[36] = data[35].trim();
					//	data[39] = ""; // 참고치			20191114 희경의료재단 요청으로 장문도 리마크 수정 요청
					}
					
					
					//컬쳐&센시 합치기 
					/**
					 * 20160715
					 * 센시 31012 31064 31011
					 * 컬쳐 31010 31014 31060
					 * 합치기 - 2개 결과 나오는 순서가 틀릴수 있음
					 */
					if (!isTxtRltC && dataModel.getHosCode(i).trim().equals("29659") && "31010|31011|31012|31014|31060|31064".contains(dataModel.getInspectCode(i).trim())) {
							
						//마지막 아이가 되어 +1 익셉션이 발생할 수 있으니 캣취해서 결과를 넣어준다.
						try {
							if ("31010|31011|31012|31014|31064".contains(dataModel.getInspectCode(i+1).trim().substring(0, 5))	
								 && dataModel.getRcvNo(i).equals(dataModel.getRcvNo(i+1))&& dataModel.getRcvDate(i).equals(dataModel.getRcvDate(i+1))) {
								
								if( "31010|31011|31014".contains(dataModel.getInspectCode(i).trim()) ){
									data[35] = data[35]+ getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
									data[36] = data[35].trim();
									data[35] = data[35] + "\r\n" + getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1));
									data[36] = data[35].trim();	

								}else{
									data[35] = data[35]+getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1));
									data[36] = data[35].trim();
									data[35] = data[35] + "\r\n" + getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
									data[36] = data[35].trim();	
								}
								
								i++;
								// culture_flag = true;
							} 
							   // 20160831 양태용 31060 예외 처리 결과 순서 뒤바꿈 
							else if ( "31060".contains(dataModel.getInspectCode(i+1).substring(0, 5))
									 && dataModel.getRcvNo(i).equals(dataModel.getRcvNo(i+1))&& dataModel.getRcvDate(i).equals(dataModel.getRcvDate(i+1))) {
									
									if("31060".contains(dataModel.getInspectCode(i).trim())){
										data[35] = data[35] + "\r\n" + getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1));
										data[36] = data[35].trim();	
										data[35] = data[35]+getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
										data[36] = data[35].trim();
										

									}else{
										data[35] = data[35]+getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1));
										data[36] = data[35].trim();
										data[35] = data[35] + "\r\n" + getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
										data[36] = data[35].trim();	
									}
									i++;
									// culture_flag = true;
								} 
						} catch (Exception e) {
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
							data[36] = data[35].trim();
						}
						
						
						
						if("".equals(data[36])||"".equals(data[35])){
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
							data[36] = data[35].trim();
						}
						
						data[35] = data[35] + "\r\n 보고일자  : " + Common.change_day1(data[21]);
						data[36] = data[35].trim();
						
						
						setTxtRltC(true);
					}

					
					
					//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
					
					if (!isTxtRltC 
							&& "31100|31101|31102|31103|31104|31105|31106|31107|31108|31109|31110|31111|31112|31113|31114|31115|31116|31117|31118|31119|31120|31121|31122|31123|31124|31125|31126|31127|31128".contains(dataModel.getInspectCode(i).trim()))
							 {
						setTxtRltC(true);
						data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
						data[36] = data[35].trim();
						try {
							if ("32000|32001".contains(dataModel.getInspectCode(i+1).substring(0, 5))
									&& dataModel.getRcvNo(i).equals(dataModel.getRcvNo(i+1))
									&& dataModel.getRcvDate(i).equals(dataModel.getRcvDate(i+1))) {
								data[35] = data[35] + "\r\n" + getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1));
								data[36] = data[35].trim();
								
								//양태용 cns처방번호가 센시의 경우 없기 때문에(엠클리스만 추가) 기존의 컬처의 처방번호를 가져와야 한다.
								if("".equals(dataModel.getCns(i+1))){
									dataModel.setCns(dataModel.getCns(i),i+1);
								}
								
								i++;
								// culture_flag = true;
							} else {
								data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
								data[36] = data[35].trim();
							}
						} catch (Exception e) {
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
							data[36] = data[35].trim();
						}
					}
					

					
					
					if (!isTxtRltC && dataModel.getHosCode(i).trim().equals("27126")) 
					{
						setTxtRltC(true);
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue2(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i)); // 문장결과
						data[36] = data[35].trim();
						data[39] = ""; // 참고치
						data[9] = "1"; //현대요양병원 일련번호 무조건1
						data[8] = "Tissue"; //현대요양병원 검체명
					}
					
					
					
					if (!isTxtRltC && dataModel.getHosCode(i).trim().equals("23913") && (dataModel.getInspectCode(i).trim().equals("31010"))) {
						setTxtRltC(true);
						data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
						data[36] = data[35].trim();
						try {
							if (dataModel.getInspectCode(i+1).substring(0, 5).equals("31011") && dataModel.getRcvNo(i).equals(dataModel.getRcvNo(i+1))
									&& dataModel.getRcvDate(i).equals(dataModel.getRcvDate(i+1))) {
								data[35] = data[35] + "\r\n" + getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1));
								data[36] = data[35].trim();
								// culture_flag = true;
							} else {
								data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
								data[36] = data[35].trim();
							}
						} catch (Exception e) {
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
							data[36] = data[35].trim();
						}
					}
					
					if (!isTxtRltC && dataModel.getHosCode(i).trim().equals("25184") && (dataModel.getInspectCode(i).trim().substring(0, 5).equals("31010"))) { // 단독
						setTxtRltC(true);
						data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
						data[36] = data[35].trim();
						try {
							if (dataModel.getInspectCode(i+1).substring(0, 5).equals("31014") && dataModel.getRcvNo(i).equals(dataModel.getRcvNo(i+1))
									&& dataModel.getRcvDate(i).equals(dataModel.getRcvDate(i+1))) {

								data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i+1)) + "\r\n" +data[35] ;
								data[36] = data[35].trim();
								// culture_flag = true;
							} else {
								data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
								data[36] = data[35].trim();
							}
						} catch (Exception e) {
							data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
							data[36] = data[35].trim();
						}
					}
					
					
					if (!isTxtRltC) {
						data[34] = ""; // 문자결과
						data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i)); // 문장결과
						data[36] = data[35].trim();
						data[39] = ""; // 참고치
					}

				}
				//20190131 양태용 추가
				if ("16702".contains(dataModel.getHosCode(i).trim()))
					
				{ // 단독
					if  ( "71325|72242".contains( dataModel.getInspectCode(i).trim().substring(0, 5)))  {
						 i = transformRltToTxt(cnt, data, remark, i);
					}
					if("21653|64631|00655".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))

					{
						data[34] = "별지보고";
					}				
					//20190212 양태용 4137401 만 처방코드 노출
					if("4137400|4137402|4137403".contains(dataModel.getInspectCode(i).trim()))

					{
						data[6] = "";
					}	
					
				}
				if ("12657".contains(dataModel.getHosCode(i).trim()))
						
				{ // 단독
					if  ( "0030|0031".contains(dataModel.getInspectCode(i).trim().substring(0, 4)) ||
							"21670|00320|00321|00322|00323|00324|00334|11050|21677|21685|21686|31001|31010|31012|71001|71002|71003|71004|71005|71006|71245|71325|72008|81441|72194|72255|71139".contains( dataModel.getInspectCode(i).trim().substring(0, 5)))  {
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						data[38] = ""; // 리마크
						setCurDate(dataModel.getRcvDate(i));
						setCurNo(dataModel.getRcvNo(i));
						
						for (String thisTimeCode = dataModel.getInspectCode()[i++].trim().substring(0, 5); thisTimeCode.equals(dataModel.getInspectCode(i).trim().substring(0, 5))
								&& curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
							if (!dataModel.getResult(i).toString().trim().equals("")) {
								data[35] = "별지보고";
							}
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
						data[38] = ""; // 리마크
					}
					if("71245|71001|71002|71003|71004|71005|71006|72008|81441|00323|00334|73027|11050".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))

					{
						data[36] = "별지보고";
						data[35] = "별지보고";
					}
					
					if(Integer.parseInt(dataModel.getInspectCode(i).trim().substring(0, 5))>300 &&Integer.parseInt(dataModel.getInspectCode(i).trim().substring(0, 5))<325){
						data[36] = "별지보고";
						data[35] = "별지보고";
					}
					
					if ("31016|31116|31117|31118|31119|31120".contains(dataModel.getInspectCode(i).trim()) && data[36].indexOf("중　간　결　과　보　고")>=0) {
						continue;
					}
					
				}
				//20190528 양태용 추가 첨단요양병원 
				if ("32679".contains(dataModel.getHosCode(i).trim()))
					
				{ 
					if("31108|31109|31110|31111|31113|31126|31115|31104|31112|31106|31114|32001".contains(dataModel.getInspectCode(i).trim().substring(0, 5)))
					{
						data[35] = "별지보고";
					}				
					
				}
				
				//원광대 병원 중간결과 보고 제외  양태용 20190214
				if ("13150".contains(dataModel.getHosCode(i).trim()))
				{ 
					if ("31017|31019|31077".contains(dataModel.getInspectCode(i).trim()) && data[36].indexOf("중　간　결　과　보　고")>=0) {
						continue;
					}
				}
				
				//한강아이제일병원 중간보고 제외 양태용 20190222
				if ("30336".contains(dataModel.getHosCode(i).trim()))
				{ 
					if ("31110|31108|31109".contains(dataModel.getInspectCode(i).trim()) && data[36].indexOf("중　간　결　과　보　고")>=0) {
						continue;
					}
				}

				//순천현대 여성 아이병원 21274 검사 단문 공백 처리
	
				if ( "31393|31395".contains(dataModel.getHosCode(i).trim()) && "21274".contains(dataModel.getInspectCode(i).trim()))  {
						data[34]="";
				}
				
				//26172 우리아동병원 7222500 공백 처리 
				
				if ( "26172".contains(dataModel.getHosCode(i).trim()) && "7222500".contains(dataModel.getInspectCode(i).trim()))  {
						data[34]="";
				}
				
				if( "12657".contains(dataModel.getHosCode(i).trim())) {
					if  (dataModel.getInspectCode(i).trim().length() == 7 &&  dataModel.getInspectCode(i).trim().substring(5, 7).equals("00")){
						data[35] = "아래 결과 참조";
						data[36] = "아래 결과 참조";
					}
					if ( "21258|21259|21273|21274|21677|31119|31120|41377|41392|70017|71325|81399|31116|32001".contains(dataModel.getInspectCode(i).trim())
							|| "72247".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
					{
						data[36] = "별지 보고";
					}
					if ( "31116|84002".contains(dataModel.getInspectCode(i).trim())) 
					{
						data[36] = "별지 보고";
					}
				}
				

				
				//29171 희경의료재단 단문인데 검체 정보 포함해서 장문으로 결과 입력 요청 20181113
				
				if(dataModel.getHosCode(i).trim().equals("29171") 
						&& (dataModel.getInspectCode(i).trim().equals("21072"))){
					
					data[35] =  "검체 :    "+ DFUtil.cutHrcvDateNumber3(dataModel.getCns(i))[0] + "\r\n" + "\r\n" + data[34];
					data[34] = "";
				}
				
				// 20200212 한국병원 담당자 요청으로 ANA검사에 라미크 포함 처리 진행
				if (dataModel.getHosCode(i).trim().equals("28099") && (dataModel.getInspectCode(i).trim().equals("21192")) )
				{
					data[36] = data[36] +"\r\n"+ getReamrkValue99(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i)); // 문장결과;
				}	

				// 대전 산재병원 재검여부 처리 
				
				if (dataModel.getHosCode(i).trim().equals("25815")) {
					data[1] = "1";
					if (data[38].indexOf("재검") >= 0) {
						data[1] = "3";
					}
				}
				
				
				//현대자동차(주)울산공장 검사명 변경 처리 요청
				
				if (dataModel.getHosCode(i).trim().equals("31161")) {
					if (dataModel.getInspectCode(i).trim().equals("0502802")) {
						data[7] = "요중메틸마뇨산";
					}
					else if (dataModel.getInspectCode(i).trim().equals("0502902")) {
						data[7] = "요중마뇨산";
					}
				}
				
				
				// 대전 산재병원 조직인경우 "결과지 참조" 로 나오도록
				if (dataModel.getHosCode(i).trim().equals("25815")
						&& (dataModel.getInspectCode(i).trim().substring(0, 5).equals("51023") || dataModel.getInspectCode(i).trim().substring(0, 2).equals("52"))) {
					data[35] = "결과지참조";
					data[36] = data[35].trim();
					data[38] = "";
					data[39] = "";
				}
				data[37] = dataModel.getHighLow(i); // 결과상태

				if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("11052")) {
					remarkCode = "";
				}
				if (dataModel.getRmkCode(i).trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
								|| 
								//!kumdata[2].trim().equals(remarkCode)
								!kumdata[2].trim().equals(dataModel.getRmkCode(i).trim())
								) {
							remarkCode = dataModel.getRmkCode(i).trim();
							if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("11026") || dataModel.getInspectCode(i).trim().substring(0, 5).equals("11052")) {
								data[35] = data[35] + getDivide() + "\r\n" + "\r\n" + getDivide()
										+ getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
								data[36] = data[35].trim();
							} else {
								data[38] = getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
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
				if (dataModel.getHosCode(i).trim().equals("12640") && dataModel.getRmkCode(i).trim().length() > 0) {
					try {
						if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
								|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = dataModel.getRmkCode(i).trim();
							data[38] = getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
							// }
							kumdata[0] = data[31].trim();
							kumdata[1] = data[32].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				}

				//  그밖에 특이사항 개발 작업!-------------------------------------------
				setExtraData( data, i);
			
			
				// !
				if (!debug) {
					int lens = getExcelFieldNameArray().length;
					
					try {
						for (k = 0; k < lens; k++) {
							label = new jxl.write.Label(k, row, data[Integer.parseInt(getExcelFieldArray()[k].toString())]);
							wbresult.addCell(label);
						}
					} catch (Exception xx) {
						//xx.printStackTrace();
					}
					
				}

				row++;
			}
			

			 setParameters(cnt, isNext());
		} catch (Exception _ex) {
			_ex.printStackTrace();
			setParameters(null);
		}
	}

	
	private int setInitialCount(int cnt) {
		setLastData("");
		setLastindex(0);

		// yty : 400개 마지막에 부속검사 오면 집계 넘어가고  
		if (cnt == 400 && dataModel.getInspectCode(399).trim().length() == 7) {
			setLastData(dataModel.getInspectCode(399).trim().substring(0, 5));
			setLastindex(399);
			setNext(true) ;

			// !
			while (getLastData().equals(dataModel.getInspectCode(getLastindex()).trim().substring(0, 5)) && 
					!(dataModel.getInspectCode(lastindex--).trim().substring(5).equals("00"))) {
				cnt--;
				if (dataModel.getInspectCode(lastindex).trim().substring(5).equals("00")) {
					cnt--;
				}
			}
			
			if(dataModel.getInspectCode(399).trim().substring(5).equals("00")){
				cnt--;
			}
		}
		return cnt;
	}

	
	private void setParameters(int cnt, boolean isNext) {
		if (cnt == 400 || isNext) {
			setParameters(new String[] { dataModel.getHosCode(cnt - 1), dataModel.getRcvDate(cnt - 1), dataModel.getRcvNo(cnt - 1), dataModel.getInspectCode(cnt - 1), dataModel.getSeq(cnt - 1) });
		} else {
			setParameters(null);
		}
	}

	
	private void setExtraData( String[] data, int i) {
		data[11] = "";
		
		switch(dataModel.getHosCode(i).trim()) {
		case "13725" :
			data[35] = getCrLF(data[35]);
			data[36] = getCrLF(data[36]);
			data[38] = getCrLF(data[38]);
			break;
		case "24516" :
			data[35] = data[35].replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
			data[36] = data[36].replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
			break;
		case "19391" :
			data[35] = data[35].replace('\r', '\n').replace('\t', ' ');
			data[36] = data[36].replace('\r', '\n').replace('\t', ' ');
			break;
		case "23913" :
			if (data[35].trim().length() > 0) {
				data[35] = "접수일자 " + Common.change_day1(data[31]) + "\r\n" + "보고일자 " + Common.change_day1(data[21]) + "\r\n" + data[35];
			} 
			break;
		case "30120": if (dataModel.getInspectCode(i).trim().equals("21191") ) {
				
				data[35] = data[34] +"\r\n[Remark]\r\n"+data[38];
				data[34] = "";
		
			}
			break;
		case "31259": if (dataModel.getInspectCode(i).trim().equals("00804") ) {
			
			data[35] = "\r\n[Remark]\r\n"+data[38];
			data[34] = "";
	
		}
		case "31798" : if ("11101|21653|41112|71311|71315|71316|71317|71318|71319|71322".contains(dataModel.getInspectCode(i).trim()))
			{
				data[35] = data[38];
				data[38] = "";
		
			}
		case "13150": 	// 원광대 산본병원 21191(ANA)단문 리마크 추가  장문에 찍기
			if (dataModel.getInspectCode(i).trim().equals("21191") ||dataModel.getInspectCode(i).trim().equals("21192") ) {
				
				data[35] = data[38];
				data[38] = "";
			}
			
		case "33993": 	// 단문 리마크 추가  장문에 찍기
			if (dataModel.getInspectCode(i).trim().equals("00064") ||dataModel.getInspectCode(i).trim().equals("00021") 
					 ||dataModel.getInspectCode(i).trim().equals("00022")||dataModel.getInspectCode(i).trim().equals("00005")
					 ||dataModel.getInspectCode(i).trim().equals("21032")) {
				
				data[35] = data[38];
				data[38] = "";
			}
			
			if (dataModel.getInspectCode(i).trim().length() == 7 && "00301|00307|00309|00310|00312|00317|00318|00319|00320|00323|00334".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) 
			{
				
				data[34] = "별지참조";
				data[35] = data[38];
				data[38] = "";
			}
			
		case "22256": 	// 온종합병원 81441 리마크 포함 장문 처리 
			if ( dataModel.getInspectCode(i).trim().equals("81441") ) {
				
				data[35] = data[34] + "\r\n\r\n" + data[38];
				data[38] = "";
				data[34] = "";
			}
				
			break;
		case "25839":// 성심힐요양병원 부속의 00번 빼기, 
			data[34] = data[34].replace("**", ""); // 부속의 00번 빼기
			data[35] = ""; // 장문결과빼기
			data[37] = data[37].replace(".", ""); // 판정에 . 빼기
			data[39] = data[39].replaceAll(data[25].toString(), ""); // 참고치 단위빼기
			data[39] = data[39].split("\r\n")[0]; // 참고치 첫번째줄만
			break;
		case "29598" : // 아시아드요양병원  부속의 00번 빼기,
			data[37] = data[37].replace(".", ""); // 판정에 . 빼기
			break;
		case "23242": 	// 칠곡경북대 경북대병원 판정 공백 
		case "17382": data[37] = ""; // 판정 공백
			break;
			
		case "25815" : 	
			if (dataModel.getInspectCode(i).trim().equals("7125203")) data[38] = "";
			if ("72018|72059".contains(dataModel.getInspectCode(i).trim().substring(0, 5))) { // 단독
				data[36] = data[36] + "\r\n\r\n" + data[38];
				data[35] = data[36];
			}	
			break;
		case "29034" : //20161028 예쓰병원 처방번호 구분자 없어지는 문제 발생 처리 양태용,20170911 cnsi 배열이 2개 뿐이라서 3개로 변경 처리 
		case "28351" :	
			try {
				String[] temp = DFUtil.cutHrcvDateNumberAce(dataModel.getCns(i));
				data[5] =temp[0]+"^"+temp[1]+"^"+temp[2]+"^";
			} catch(Exception e){
				e.printStackTrace();
				data[5] = "test";
			} 
			break;
		case "28952": 
			{
				String[] strTemp =  dataModel.getSpecNo(i).split("_");
				if(strTemp.length>1){
					data[4] = strTemp[0];//검체번호
					data[9] = strTemp[1];//일련번호
				}else{
					data[9] = "0"; //현대요양병원 일련번호 무조건1	
				}
			}
		case "30373" : if (dataModel.getInc(i).trim().equals("") ) 
			data[2] = "I";
			break;
		case "29567":
			data[4] = data[22]+data[4];//검체번호
			if (dataModel.getInspectCode(i).trim().equals("31059")) 
			{
				data[35]=data[34];
				data[34]="";
				//data[4] = data[22]+data[4];//검체번호
			}
			break;
		case "29568":
			data[4] = data[22]+data[4];//검체번호
			break;
		case "29171" : if( "21295|72241|21581|64665".contains(dataModel.getInspectCode(i).trim())) 
			data[35] = // "참고치 : "+data[39] + 		김영상과장님 요구사항 
			           "Remark \r\n"+ getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
			break;
		case "29005" : 	//20170316 병원 측 요구로 인해 21295 검사에 리마크 추가 처리 완료
			if ("21295|21072".contains(dataModel.getInspectCode(i).trim())) 
			{
				data[35] =data[34] +"\r\n\r\nRemark \r\n"+ getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
				data[34]="";
				//data[4] = data[22]+data[4];//검체번호
			}
			break;
		case "29498":
			data[15] = data[15].replaceAll(".00", "");
			break;
			//20190305 양태용 병원측 요구로 프로그램 수정
		case "16702":	
			//20190625 주석 처리 추가 등록요청 있었음
//			if("00056|00260|00301|00309|00312|00316|00317|00318|00319|00320|00323|00332|00333|00640|00654|05566|11050|11131|21380|21653|21677|21683".contains(dataModel.getInspectCode(i).trim())
//					|| "31026|31029|31031|31035|31079|31081|31120|41381|41498|64460|64699|70017|70074|71003|71004|71005|71011|71018|71053|71062|71069".contains(dataModel.getInspectCode(i).trim())
//					|| "71086|71094|71095|71124|71159|71161|71198|71246|71249|71259|71325|71327|71329|71604|72012|72013|72020|72182|72189|72194|72241".contains(dataModel.getInspectCode(i).trim())
//					|| "81127|81271|81272|81373|81374|81388|81399|81418|81424|81428|81458|81476|81477|81515|81587|81614|81727|81753|81757|81758|81761|81820".contains(dataModel.getInspectCode(i).trim())
//					|| "81828|81846|89978|89994|90027|90100|99950|99954|0030900|0031700|0031800|0032300|21653|31026|31079|31120|71011|71018|71053|7218900|7224100".contains(dataModel.getInspectCode(i).trim())
//					|| "7125900|05564|05565|05562|05563|0556700|7202000|2168300|2138000|7224200|7218200|8112700|31120|31080|2126900|6463100|7224200".contains(dataModel.getInspectCode(i).trim()))
			if("00260||00301|00309|00312|00316|00317|00318|0031900|00320|00323|00332|00333|00640|0065400|05566|11050|11131|21380|21653|21677|21683".contains(dataModel.getInspectCode(i).trim())
					|| "31026|31029|31031|31035|31079|31081|41381|41498|64460|64699|70017|70074|71003|71004|71005|71011|71018|71053|71069".contains(dataModel.getInspectCode(i).trim())
					|| "71086|71094|71095|71124|71159|71161|71198|71246|71249|71259|7132500|7132700|71329|71604|72012|72013|72020|72182|72189|72194|72241".contains(dataModel.getInspectCode(i).trim())
					|| "81127|81271|81272|81373|81374|81388|81399|81418|81424|81428|81476|81477|81515|81587|81614|05496|81753|81757|81758|81761|81820".contains(dataModel.getInspectCode(i).trim())
					|| "81828|81846|89978|89994|90027|90100|99950|99954|0030900|0031700|0031800|0032300|21653|31026|31079|71011|71018|71053|7218900|7224100".contains(dataModel.getInspectCode(i).trim())
					|| "7125900|05564|05565|05562|05563|0556700|7202000|2168300|2138000|7224200|7218200|8112700|31120|31080|2126900|6463100|7224200".contains(dataModel.getInspectCode(i).trim())
					|| "4102601|71257|7224500|32001".contains(dataModel.getInspectCode(i).trim())
					|| "81377|7219400|2167700|71027|4102600|72011|72010|70076|70077|31030|8142900|71055|81600|31034|31027|7113600|2138200|0549500".contains(dataModel.getInspectCode(i).trim()))
			{
				data[11] = "Image";
			}
			else {
				data[11] = "Text";
			}
			break;
		
		case "29502" :// YTY (마석기독재활요양병원) 판정에 => . 일 경우  null로 변경 20160824
			data[37] = dataModel.getHighLow(i).replace(".", "");
			break;
		case "30028" : // YTY (서율요양병원) 판정에 => . 일 경우  null로 변경 20160824
			data[37] = dataModel.getHighLow(i).replace(".", "");
			break;
		case "29825" : //제주대병원(29825)
			data[30] = "SEGEN";//씨젠
			break;
		case "27230" : //20190220 양태용 세계로 병원 27230 단문 결과에 리마크 참조 찍기
			data[34] = "Remark 참조"; // 참고치
			break;
		}
	
	}


	
	
	private void setCtypeRltToTxt(String[] data, int i) {
		setTxtRltC(true);
		data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
		data[36] = data[35].trim();
		try {
			if ((dataModel.getInspectCode(i + 1).substring(0, 5).equals("32000")||dataModel.getInspectCode(i+1).substring(0, 5).equals("32001"))
					&& dataModel.getRcvNo(i).equals(dataModel.getRcvNo(i + 1))
					&& dataModel.getRcvDate(i).equals(dataModel.getRcvDate(i + 1))) {
				data[35] = data[35] + "\r\n" +  getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i + 1));
		//		data[35] =   getTextResultValue(dataModel.getHosCode(i), rcvDate[i], dataModel.getRcvNo(i), inspectCode[i + 1]);
				data[36] = data[35].trim();
				
				//양태용 cns처방번호가 센시의 경우 없기 때문에(엠클리스만 추가) 기존의 컬처의 처방번호를 가져와야 한다.
				if("".equals(dataModel.getCns(i+1))){
					dataModel.setCns(dataModel.getCns(i), i+1); //cn[i+1] = cns[i];
				}
				
				i++;
				// culture_flag = true;
			} else {
				data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
				data[36] = data[35].trim();
			}
		} catch (Exception e) {
			data[35] = getTextResultValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getInspectCode(i));
			data[36] = data[35].trim();
		}
		
		try {
			data[35] =  "검체 :    "+ DFUtil.cutHrcvDateNumber3(dataModel.getCns(i))[0] + "\r\n" + data[35];
		} catch (Exception e) {	}
		
		data[35] = data[35].replaceAll("최　종　결　과　보　고", "");
		data[39] = ""; // 참고치
	}

	

	
	private int transformRltToTxt33(int cnt, String[] data, int i) {
		//양태용 20161011 검체 추가 요청
		try {
			data[35] = "검체 :    " + DFUtil.cutHrcvDateNumber3(dataModel.getCns(i))[0];	
		} catch (Exception e) {	}
							
		
//				data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "    " + appendBlanks(dataModel.getResult(i), 21) + "    "	+ getReferenceValue(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		
		i = setResultTabToBlank(cnt,data, i, curDate, curNo);
		
		
		i--;
		data[36] = data[35].trim();
		
		//그람 스테인 검사에 한하여 리마크를 결과 아래 표기 한다.20160307 김영상 과장 요청  
		try {
			if( dataModel.getInspectCode(i).trim().substring(0, 5).equals("31001") ||dataModel.getInspectCode(i).trim().substring(0, 5).equals("00804") 
					|| dataModel.getInspectCode(i).trim().substring(0, 5).equals("72241")){
				data[35] =data[35] +"\r\n"+ getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
			}
			
		} catch (Exception e) {	}
		return i;
	}

	private void transformRltToTxt32(String[] data, String[] remark, int i) {
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		data[39] = getReferenceValue(remark);
		data[36] = data[34] + "\r\n" + data[39];
	}

	
	private void transformRltToTxt31(String[] data,	String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValue(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setTxtRltB(true);
		setCurNo(dataModel.getRcvNo(i));
		setRemarkArray( remark, i);
		setReferUnit(data, i);
		data[39] = getReferenceValue(remark);
		data[21] = dataModel.getBdt(i); // 검사완료일

		data[36] = data[35].trim();
		// !
	}

	
	private void setRemarkArray(String[] remark,int i) {
		remark[0] = dataModel.getInspectCode(i);
		remark[1] = dataModel.getLang(i);
		remark[2] = dataModel.getHistory(i);
		remark[3] = dataModel.getSex(i);
		remark[4] = dataModel.getAge(i);
	}

	private void transformRltToTxt30(String[] data, int i) {
		data[35] = appendBlanks(dataModel.getResult(i), 21);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		data[36] = data[35].trim();
	}

	private void transformRltToTxt29(String[] data,	String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValue(remark)+"\r\n"+ IMethologyGetter.getQuantiFERON();
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setTxtRltB(true);
		setCurNo(dataModel.getRcvNo(i));
		setRemarkArray(remark, i);
		setReferUnit(data, i);
		data[39] = getReferenceValue(remark);
		data[21] = dataModel.getBdt(i); // 검사완료일

		data[36] = data[35].trim();
		// !
	}

	private int transformRltToTxt28(int cnt, String[] data, String[] remark, int i) {
		
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		switch (dataModel.getHosCode(i)) {
	
		case "29567":
		case "32015":
			data[35] +=  appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"		
					+ getReferenceValueAge(remark).trim();
			break;
		}
		i = setResult(cnt,data, i, curDate, curNo);
		i--;
		data[36] = data[35].trim();
		return i;
	}
	
	private int transformRltToTxt26( String[] data, int i) {

		data[34] = ""; // 리마크
		data[35] = ""; // 문장결과
		data[36] = ""; // 수치+문장
		data[37] = ""; // 상태
		data[38] = ""; // 리마크
		data[39] = ""; // 참고치
		
	
		// ! Triple 검사인지 시작 ---------------------
		String result_ = getResultRSTS(new String[] { dataModel.getRcvDate(i).trim(), dataModel.getRcvNo(i).trim(), dataModel.getInspectCode(i).trim().substring(0, 5) });
		data[35] = result_;
		data[39] = ""; // 참고치

		setTxtRltB(true);
		// !
		i++;
		return i;
	}

	private int transformRltToTxt25(String[] data, int i) {
		boolean isTQ;
		data[34] = ""; // 리마크
		data[35] = ""; // 문장결과
		data[36] = ""; // 수치+문장
		data[37] = ""; // 상태
		data[38] = ""; // 리마크
		data[39] = ""; // 참고치
	
		isTQ = false; 
		// ! Triple 검사인지 시작 --------------------- data[6] =
		String result_ = getResultTriple(new String[] { dataModel.getRcvDate(i).trim(), dataModel.getRcvNo(i).trim(), dataModel.getInspectCode(i).trim().substring(0, 5) });
		data[35] = result_;
		data[39] = ""; // 참고치
		isTQ = true;
		setTxtRltB(true);
		// !
		i++;
		try {
			while (isTQ && i > 0 && dataModel.getRcvDate(i).trim().equals(dataModel.getRcvDate(i-1).trim()) && dataModel.getRcvNo(i).trim().equals( dataModel.getRcvNo(i - 1).trim())) {
				i++;
			}
		} catch (Exception e) {
			// handle exception
		}

		i--;
		if(dataModel.getInspectCode(i).equals("42021")) i--; //임신중독증 검사이면 출력될수 있게 함
		// ! Triple 검사인지 종료 ---------------------
		return i;
	}

	
	private int transformRltToTxt23(int cnt, String[] data, int i) {
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (!dataModel.getResult(i).toString().trim().equals("Negative")) {
				data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + appendBlanks(dataModel.getResult(i), 21)
						+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
			}
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		
		//! 리마크 추가 부분
		data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
		data[36] = data[35].trim();
		// !
		return i;
	}

	
	private int transformRltToTxt21(int cnt, String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 단독
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (dataModel.getInspectCode(i).trim().substring(5, 7).equals("01")) {
				data[34] = dataModel.getResult(i); // 문자결과
				setRemarkArray( remark, i);
				setReferUnit(data, i);
				data[39] = getReferenceValue(remark);
				data[21] = dataModel.getBdt(i); // 검사완료일

			}
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
					+ getReferenceValue(remark);

			// !
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[34] + "\r\n" + data[36];
		return i;
	}

	
	private int transformRltToTxt20(int cnt, String[] data, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "관  련  약  제";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getResultHBV(dataModel.getInspectCode(i).trim());
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
					+ getResultHBV(dataModel.getInspectCode(i).trim());
			if (++i == cnt || i > cnt)
				break;
		}
		i--;

		
		data[36] = data[35].trim();
		return i;
	}

	
	private int transformRltToTxt18(int cnt,String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치"; // 단독
		setCurDate(dataModel.getRcvDate(i));
		setTxtRltB(true);
		setCurNo(dataModel.getRcvNo(i));
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (dataModel.getInspectCode(i).trim().substring(5, 7).equals("01")) {
				data[34] = dataModel.getResult(i); // 문자결과
				setRemarkArray(remark, i);
				setReferUnit(data, i);
				data[39] = getReferenceValue(remark);
				data[21] = dataModel.getBdt(i); // 검사완료일

			}
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + appendBlanks(dataModel.getResult(i), 21)
					+ getReferenceValue(remark);

			// !
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[34];
		return i;
	}

	
	private int transformRltToTxt17(int cnt, String[] data, String[] remark, int i) {
		// ! 박진영 내과
		data[35] = appendBlanks("검  사  명 ", 30) + "" + appendBlanks("결    과", 21) + " " + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "" + appendBlanks(dataModel.getResult(i), 21) + ""
				+ getReferenceValue(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i)); // 단독
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "" + appendBlanks(dataModel.getResult(i), 21) + ""
					+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[35].trim().replace('\r', ' ').replace('\n', ' ');
		return i;
	}

	
	private int transformRltToTxt16(int cnt,String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치"; // 희경의료재단 제	
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValue(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
					+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		return i;
	}


	private int transformRltToTxt15(int cnt, String[] data, String[] remark,int i) {
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (dataModel.getInspectCode(i).trim().substring(5, 7).equals("03")) {
				data[34] = dataModel.getResult(i); // 문자결과
				data[35] = "";
				data[39] = ""; // 참고치
				setRemarkArray(remark, i);
				data[25] = DFUtil.getUintCut(dataModel.getUnit(i))[2]; // 참고치단위
				data[39] = getReferenceValue(remark);
				data[21] = dataModel.getBdt(i); // 검사완료일
			}
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[34];
		return i;
	}

	
	private int transformRltToTxt14(int cnt, String[] data, String[] remark,int i) {
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		i = setBusock01(cnt, data,remark, i, curDate, curNo);
		i--;
		switch (dataModel.getHosCode(i)) {
		case "13406":
		case "12770": data[36] = data[35].trim();
			break;
		default : data[36] = data[34].trim();
		}
		
		return i;
	}
	

	private int transformRltToTxt12(int cnt,String[] data, int i) {
		data[35] = appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) ;
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) ;
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		return i;
	}
	
	private int transformRltToTxt8(int cnt, String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + appendBlanks("결    과", 21) + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + appendBlanks(dataModel.getResult(i), 21) + getReferenceValue(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + appendBlanks(dataModel.getResult(i), 21)
					+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		return i;
	}
	
	private int transformRltToTxt9(int cnt,String[] data, String[] remark,
			int i) {
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (!dataModel.getInspectCode(i).trim().substring(5, 7).equals("00")) {
				data[34] = dataModel.getResult(i); // 문자결과
				data[35] = "";
				data[39] = ""; // 참고치
				setRemarkArray( remark, i);
				setReferUnit(data, i);
				data[39] = getReferenceValue(remark);
				data[21] = dataModel.getBdt(i); // 검사완료일
			}
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		return i;
	}

	
	private void setReferUnit( String[] data, int i) {
		try {
			data[25] = DFUtil.getUintCut(dataModel.getUnit(i))[2]; // 참고치단위
		} catch (Exception exx) {
			data[25] = ""; // 참고치단위
		}
		try {
			data[42] = DFUtil.getUintCut(dataModel.getUnit(i))[0]; // 참고치단위
		} catch (Exception exx) {
			data[42] = ""; // 참고치단위
		}
		try {
			data[43] = DFUtil.getUintCut(dataModel.getUnit(i))[1]; // 참고치단위
		} catch (Exception exx) {
			data[43] = ""; // 참고치단위
		}
	}



	private int transformRltToTxt7(int cnt,String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValue(remark);
	
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		switch(dataModel.getHosCode(i)) {
		case "29167":
		case "25815":
			
				i = set문장(cnt,  data,i, curDate, curNo);
				
			break;
			
		default : 
			i = setResult(cnt, 	data, i, curDate, curNo);
			i--;
			data[36] = data[35].trim();
		}
			
		return i;
			
		
	

	}

	
	private int transformRltToTxt6(int cnt, String[] data, int i) {
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t";
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (!dataModel.getResult(i).toString().trim().equals("")) {
				data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t";
				//		+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
			}
			if (++i == cnt || i > cnt)
				break;
		}
		
		i--;
		data[36] = data[35].trim();
		return i;
	}

	
	private String transformRltToTxt5( String[] data, String[] remark, String remarkCode,int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValue(remark);
		data[34] = dataModel.getResult(i); // 문자결과
		setTxtRltB(true);
		if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("31001")) {
			data[36] = "";
			data[35] = "";
		} else {
			data[36] = data[35].trim();
		}
		data[39] = ""; // 참고치
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		if (dataModel.getInspectCode(i).trim().substring(0, 5).equals("11052")) {
			remarkCode = "";
		}
		if (dataModel.getRmkCode(i).trim().length() > 0) {
			try {
				if (!kumdata[0].trim().equals(data[31].trim()) || !kumdata[1].trim().equals(data[32].trim())
						|| !kumdata[2].trim().equals(remarkCode)) {
					remarkCode = dataModel.getRmkCode(i).trim();
					data[38] = getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
					kumdata[0] = data[31].trim();
					kumdata[1] = data[32].trim();
					kumdata[2] = remarkCode;
				}
			} catch (Exception _ex) {
			}
		} else {
			remarkCode = "";
		}
		return remarkCode;
	}

	
	private int transformRltToTxt4(int cnt, String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValue(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
					+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
			if (++i == cnt || i > cnt)
				break;
		}
		i--;
		switch(dataModel.getHosCode(i).trim()) {
		
		case "13804":
		case "30099":
		case "10786":
		case "31064" : 
		case "28099" : 
			data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue99(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
			data[36] = data[35].trim();
			break;
		default : data[36] = data[35].trim();
		break;
		}
		
		return i;
	}



	private int transformRltToTxtWithRemark(int cnt,  String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		switch(dataModel.getHosCode(i).trim()) {
		case "27021" : data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValueAge(remark);
			break;
		default : 
			data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
					+ getReferenceValue(remark)
					;
			 break;
		}
		
		
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		
		setCurDate(dataModel.getRcvDate(i));
		setCurNo(dataModel.getRcvNo(i));
		setTxtRltB(true);
		i = setResult(cnt,data, i, curDate, curNo);
		i--;
		
		switch(dataModel.getHosCode(i).trim()) {
		case "27021" :
			data[36] = data[35].trim()+"\r\n"+getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
			break;
		default :
			data[35] += "\r\n" + getReamrkValue(dataModel.getHosCode(i), dataModel.getRcvDate(i), dataModel.getRcvNo(i), dataModel.getRmkCode(i));
			data[36] = data[35].trim();
		}
		
		
		return i;
	}


	private int transformRltToTxt(int cnt, String[] data, String[] remark, int i) {
		data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
		data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
				+ getReferenceValueAge(remark);
		data[34] = ""; // 문자결과
		data[39] = ""; // 참고치
		setTxtRltB(true);
		setCurDate( dataModel.getRcvDate(i)) ;
		setCurNo(dataModel.getRcvNo(i));
		i = setResult(cnt, data, i, curDate, curNo);
		i--;
		data[36] = data[35].trim();
		return i;
	}
 
	private int setBusock01(int cnt, String[] data,	String[] remark, int i, String curDate, String curNo) {
//		System.out.println("test");
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5);
				thisTimeCode.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) 
		{
			
			if (dataModel.getInspectCode(i).trim().substring(5, 7).equals("01")) {
				data[34] = dataModel.getResult(i); // 문자결과
				data[35] = "";
				data[39] = ""; // 참고치
				setRemarkArray(remark, i);
				setReferUnit(data, i);
				data[39] = getReferenceValue(remark);
				data[21] = dataModel.getBdt(i); // 검사완료일
			}
			if (++i == cnt || i > cnt)
				break;
		}
		return i;
	}



	private int set문장(int cnt, String[] data,int i, String curDate, String curNo) {
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); thisTimeCode
				.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			String arrRefer[] = getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i) }).trim().split("\r\n");
			String strRefer = arrRefer[0];
			for (int r = 1; r < arrRefer.length; r++) {
				strRefer = strRefer + "\r\n" + insertBlanks(arrRefer[r].toString(), 55);
			}

			data[35] += "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t" + strRefer;
			if (++i == cnt)
				break;
		}
		i--;
		data[36] = data[35].trim();
		return i;
	}

	
	//희경의료재단 29171 은 결과에 tab 이 들어가면 결과가 0 가 들어간다..

	
	private int setResultTabToBlank(int cnt,String[] data, int i, String curDate, String curNo) {
		for (String thisTimeCode = dataModel.getInspectCode(i).trim().substring(0, 5);    // 20180726 기존 소스의 경우     String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); 로 되어 있었음 오류 발생해서 이와 같이 수정
				thisTimeCode.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) 
				&& curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (!dataModel.getResult(i).toString().trim().equals("")) {
				data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "    " + appendBlanks(dataModel.getResult(i), 21) + "    "	
			+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i)}).trim();
			}
			if (++i == cnt || i > cnt)	
			break;
				
		}
		return i;
	}
	//원병원 부속검사 결과 
	private int setResult(int cnt, String[] data, int i, String curDate, String curNo) {
		for (String thisTimeCode = dataModel.getInspectCode(i++).trim().substring(0, 5); 
				thisTimeCode.equals(dataModel.getInspectCode(i).trim().substring(0, 5)) && curDate.equals(dataModel.getRcvDate(i).trim()) && curNo.equals(dataModel.getRcvNo(i).trim());) {
			if (!dataModel.getResult(i).toString().trim().equals("")) {
				switch(dataModel.getHosCode(i)) {
				case "31844": // 오타 수정 양태용 20190131
				case "30886":
					data[35] += dataModel.getInspectName(i) + " : " + dataModel.getResult(i) + " " 
							+ getReferenceValueNotBlank(new String[] {dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i)}).trim() + "\r\n"   ;
					
					break;
				case "29567":
				case "32015":
					data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21).trim() ;
					break;
				default : 
					data[35] += getDivide() + "\r\n" + appendBlanks(dataModel.getInspectName(i), 30) + "\t" + appendBlanks(dataModel.getResult(i), 21) + "\t"
							+ getReferenceValue(new String[] { dataModel.getInspectCode(i), dataModel.getLang(i), dataModel.getHistory(i), dataModel.getSex(i), dataModel.getAge(i) }).trim();
					break;
				}
				
			}
			if (++i == cnt || i > cnt)
				break;
		}
		return i;
	}
	

}

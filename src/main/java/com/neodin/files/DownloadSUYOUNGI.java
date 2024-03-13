package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import jxl.Workbook;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;
import mbi.jmts.dpc.DPCParameter;

/*
 연세시티병원만을 위한 나만의 엑셀 excel!
 */
public class DownloadSUYOUNGI extends ResultDownload {
	boolean debug = false;
	public DownloadSUYOUNGI() {
		initialize();
	}

	public String getResulttandem(String s, Vector vector) {
		String s1 = "";
		String as[] = new String[vector.size()];
		int i = as.length;		
		try {
		     for (int j = 0; j < as.length; j++) {
		    	 as[j] = vector.elementAt(j).toString();	
			}    			     
			for (int j = 0; j < i; j++) {     
				
				try {
					s1 = s1 + getDivide() + "\r\n" + as[j];
					
				} catch (Exception xx) {
					s1 = s1 + getDivide() + "\r\n" + as[--j];
				}
			}
			return  s1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return"";
	}
	

	private String getResultRemark(String param) {
		String str = "";
		Object[] remark1 = Common.getArrayTypeData_CheckLast(param);
		if (remark1 != null) {

			// !
			int len = remark1.length;
			for (int i = 0; i < len; i++) {
				str += remark1[i].toString() ;//+ "\r";
			}
		}
		return str;
	}
	
	public String getResultTriple(java.lang.String[] id) {

		// !
		boolean is90027 = false; // First double maker
		boolean is99933 = false; // Integrated test 2차
		boolean is99934 = false; // Integrated test 2차
		boolean is99935 = false; // Sequential test 1차
		boolean is99936 = false; // Sequential test 2차
		boolean is90100 = false; // Quad test
		

		String temp_title = "<< 기형아 검사결과보고 >>";

		// !
		AbstractDpc dpc_;
		DPCParameter parm_;
		// ! 검사결과
		// 가져오기***************************************************************
		String[] tempArray = new String[24];
		String rtnResult = "";
		rtnResult = temp_title + "\r\n";
		
		// 프로파일 코드불러오기
		dpc_ = new com.neodin.result.DpcOfMC308RM3();
		dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자, 접수번호
		parm_ = dpc_.getParm();
		try {
			if (parm_.getStringParm(5) != null) {
				String pcode = parm_.getStringParm(5);
				if (pcode.equals("99933")) {
					is99934 = true;
					temp_title = "<<Integrated test 1차 결과보고>>";
					rtnResult = temp_title + "\r\n\r\n";
				}
				if (pcode.equals("99934")) {
					is99934 = true;
					temp_title = "<<Integrated test 2차 결과보고>>";
					rtnResult = temp_title + "\r\n\r\n";
				}
				if (pcode.equals("99935")) {
					is99935 = true;
					temp_title = "<<Sequential test 1차 결과보고>>";
					rtnResult = temp_title + "\r\n\r\n";
				}
				if (pcode.equals("99936")) {
					is99936 = true;
					temp_title = "<<Sequential test 2차 결과보고>>";
					rtnResult = temp_title + "\r\n\r\n";
				}
				if (pcode.equals("90027")) {
					is90027 = true;
					temp_title = "<<First double maker test>>";
					rtnResult = temp_title + "\r\n\r\n";
				}
				if (pcode.equals("90100")) {
					is90100 = true;
					temp_title = "<<Quadruple test 결과보고>>";
					rtnResult = temp_title + "\r\n\r\n";
				}
			}
		} catch (Exception ee) {
		}
		try {
			dpc_ = new com.neodin.result.DpcOfMC308RMS4();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자,
																	// 접수번호
			
			
			parm_ = dpc_.getParm();

			// ! 임신중기검사
			tempArray[0] = parm_.getStringParm(14).trim(); // AFP
			tempArray[1] = parm_.getStringParm(15).trim(); // MOM
			// !
			tempArray[2] = parm_.getStringParm(20).trim(); // FREE
			tempArray[3] = parm_.getStringParm(21).trim(); // MOM

			tempArray[4] = parm_.getStringParm(18).trim(); // HCG
			tempArray[5] = parm_.getStringParm(19).trim(); // MOM

			tempArray[6] = parm_.getStringParm(16).trim(); // PAPP
			tempArray[7] = parm_.getStringParm(17).trim(); // MOM

			tempArray[8] = parm_.getStringParm(22).trim(); // UE3
			tempArray[9] = parm_.getStringParm(23).trim(); // MOM

			tempArray[10] = parm_.getStringParm(24).trim(); // NT
			tempArray[11] = parm_.getStringParm(25).trim(); // MOM

			tempArray[12] = parm_.getStringParm(34).substring(1, 16).trim(); // IuhibinA
			tempArray[13] = parm_.getStringParm(34).substring(16, 31).trim(); // MOM

			// ! 결과및 판정
			tempArray[14] = parm_.getStringParm(26).trim(); // 나이
			tempArray[15] = parm_.getStringParm(27).trim(); // 결과
			tempArray[16] = parm_.getStringParm(28).trim(); // 다운  
			tempArray[17] = parm_.getStringParm(29).trim(); // 결과
			tempArray[18] = parm_.getStringParm(30).trim(); // 에드워드
			tempArray[19] = parm_.getStringParm(31).trim(); // 결과
			tempArray[20] = parm_.getStringParm(32).trim(); // 신경관결손
			tempArray[21] = parm_.getStringParm(33).trim(); // 결과

			// 문장결과(판정) 불러오기
			dpc_ = new com.neodin.result.DpcOfMC308RMS3();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자,
																	// 접수번호
			parm_ = dpc_.getParm();

			// !
			tempArray[22] = parm_.getStringParm(3).trim(); // 다운/에드워드 판정
			tempArray[23] = parm_.getStringParm(4).trim(); // 신경관 결손
		} catch (Exception e) {
		}
		rtnResult += gubun1;
		rtnResult += appendBlanks("검사항목", 10) + appendBlanks("결      과", 15)
				+ appendBlanks("MoM", 10);
		rtnResult += gubun2;
		// !
		//cdy 
		if (!tempArray[0].equals(""))
			rtnResult += appendBlanks("AFP", 14)
					+ appendBlanks(tempArray[0] + " ng/mL", 17)	
					+ appendBlanks(tempArray[1] + "", 10) + "\r\n";
		if (!tempArray[2].equals(""))
			if(Integer.parseInt(id[0].toString().trim())>20140430){
			rtnResult += appendBlanks("hCG", 14)
					+ appendBlanks(tempArray[2] + " IU/mL", 17)
					+ appendBlanks(tempArray[3] + "", 10) + "\r\n";
			}else{
				rtnResult += appendBlanks("Freeb-HCG", 15)
				+ appendBlanks(tempArray[2] + " ng/mL", 15)
				+ appendBlanks(tempArray[3] + "", 10) + "\r\n";
				
			}
		if (!tempArray[4].equals(""))
			rtnResult += appendBlanks("HCG", 14)
					+ appendBlanks(tempArray[4] + " ug/mL", 17)
					+ appendBlanks(tempArray[5] + "", 10) + "\r\n";
		if (!tempArray[6].equals(""))
			if(Integer.parseInt(id[0].toString().trim())>20140430){
			rtnResult += appendBlanks("PAPP-A", 14)
					+ appendBlanks(tempArray[6] + " ng/mL", 17)
					+ appendBlanks(tempArray[7] + "", 10) + "\r\n";
			}else{
				rtnResult += appendBlanks("PAPP-A", 14)
				+ appendBlanks(tempArray[6] + " IU/mL", 17)
				+ appendBlanks(tempArray[7] + "", 10) + "\r\n";		
			}
		if (!tempArray[8].equals(""))
			rtnResult += appendBlanks("uE3", 14)
					+ appendBlanks(tempArray[8] + " nmol/L", 17)
					+ appendBlanks(tempArray[9] + "", 10) + "\r\n";
		
		if (!tempArray[10].equals("")){
			rtnResult += appendBlanks("NT", 14)
					+ appendBlanks(tempArray[10] + " mm", 17)
					+ appendBlanks(tempArray[11] + "", 10) + "\r\n";
		}
		if (!tempArray[12].equals("")){
			rtnResult += appendBlanks(" Inhibin A ", 16)
					+ appendBlanks(tempArray[12] + " pg / mL ", 18)
					+ appendBlanks(tempArray[13] + " ", 14) + "\r\n";
		}
		
		rtnResult += gubun2;
		
		if (is99933) {
			//! Integrated test 1 차
			rtnResult += gubun1;
			rtnResult += appendBlanks("선별검사항목", 22)
                    + appendBlanks( " 기준치 " , 12) + appendBlanks(" 결과      " , 16)
                    + appendBlanks( "위 험 도 " , 10);
			rtnResult += gubun2;
			
			
			rtnResult += appendBlanks(" 신경관 결손증 ", 24)
					+ appendBlanks(" 2.5 MoM ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[21]).trim()) ?"":"1 : " + Common.cutZero(tempArray[21]) , 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 23)
					+ appendBlanks(" 1 : 200 ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[19]).trim()) ?"":"1 : " +Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 25)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[17]).trim()) ?"":"1 : " +Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);

			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
											+ appendBlanks("" , 21) //20190320양태용 기존 21X 17O
											+ appendBlanks(tempArray[14], 10);
									rtnResult += gubun1;
									rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
									rtnResult += getResultRemark(tempArray[22]);
									rtnResult += "\r\n\r\n";
										
				} 
		}

		if (is99934) {
			//! Integrated test 2 차
            rtnResult += gubun1 ;
            rtnResult += appendBlanks("선별검사항목" , 22)
            			+ appendBlanks( " 기준치 " , 12) + appendBlanks(" 결과      " , 16)
            			+ appendBlanks( "위 험 도 " , 10);
            
            rtnResult += gubun2 ;

			rtnResult += appendBlanks(" 신경관 결손증 ", 24)
					+ appendBlanks(" 2.5 MoM ", 12)
					+ appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);

			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 23)
					+ appendBlanks(" 1 : 200 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);

			
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 22)
					+ appendBlanks(" 1 : 495 ", 12)
					+ appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
		

			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
						//					+ appendBlanks(" 만 35세 ", 12)
						//					+ appendBlanks("" .equals(Common.cutZero (tempArray [15]).trim()) ?"": "1 : " +Common.cutZero(tempArray[15]), 13)
											+ appendBlanks("" , 21)
											+ appendBlanks(tempArray[14], 10);
									rtnResult += gubun1;
									rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
									rtnResult += getResultRemark(tempArray[22]);
									rtnResult += "\r\n\r\n";
									
									
				} 
		}
		if (is99935) {
			//! Sequential test 1차

			rtnResult += gubun1 ;
            rtnResult += appendBlanks("선별검사항목" , 22)
            			+ appendBlanks( " 기준치 " , 12) + appendBlanks(" 결과      " , 16)
            			+ appendBlanks( "위 험 도 " , 10);
            rtnResult += gubun2 ;

			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 24)
					+ appendBlanks(" 2.5 MoM ", 12)
                    + appendBlanks("" .equals(Common.cutZero (tempArray [21]).trim()) ?"": "1 : " + Common.cutZero(tempArray[21]) , 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 23)
					+ appendBlanks("         ", 12)
                    + appendBlanks("" .equals(Common.cutZero (tempArray [19]).trim()) ?"": "1 : " +Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 25)
					+ appendBlanks(" 1 : 41 ", 12)
                   + appendBlanks("" .equals(Common.cutZero (tempArray [17]).trim()) ?"": "1 : " +Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
						+ appendBlanks("" , 21)
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
				rtnResult += getResultRemark(tempArray[22]);
				rtnResult += "\r\n\r\n";
				} 
			
		}
			
		
		else if (is99936) {
			
			rtnResult += gubun1;
			rtnResult += appendBlanks("선별검사항목 ", 22)
					+ appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13)
					+ appendBlanks(" 위험도 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22)
					+ appendBlanks(" 2.5 MoM ", 12)
					+ appendBlanks("" .equals(Common.cutZero (tempArray [1]).trim()) ?"": Common.cutZero(tempArray[1]) , 5)+ "MoM     "
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 20)
					+ appendBlanks(" 1 : 200 ", 15)
					+ appendBlanks("" .equals(Common.cutZero (tempArray [19]).trim()) ?"": "1 : " +Common.cutZero(tempArray[19]), 15)					
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 24)
					+ appendBlanks(" 1 : 450 ", 15)
					+ appendBlanks("" .equals(Common.cutZero (tempArray [17]).trim()) ?"": "1 : " +Common.cutZero(tempArray[17]), 16)
					+ appendBlanks(tempArray[16], 10);

			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 22)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
											+ appendBlanks("" , 21)
											+ appendBlanks(tempArray[14], 10);
									rtnResult += gubun1;
									rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
									rtnResult += getResultRemark(tempArray[22]);
									rtnResult += "\r\n\r\n";
				} 
			
		}
		else if (is90027) {
            rtnResult += gubun1 ;
            rtnResult += appendBlanks("선별검사항목" , 22)
                       + appendBlanks( " 기준치 " , 12) + appendBlanks(" 결과      " , 16)
                       + appendBlanks( "위 험 도 " , 10);
            rtnResult += gubun2 ;

			rtnResult += appendBlanks(" 신경관 결손증 ", 24)
					+ appendBlanks(" 2.5 MoM ", 12)
                    + appendBlanks("" .equals(Common.cutZero (tempArray [21]).trim()) ?"": "1 : " + Common.cutZero(tempArray[21]) , 13)
					+ appendBlanks(tempArray[20], 10);

			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 23)
					+ appendBlanks(" 1 : 300 ", 12)
                    + appendBlanks("" .equals(Common.cutZero (tempArray [19]).trim()) ?"": "1 : " +Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 25)
					+ appendBlanks(" 1 : 250 ", 12)
                    + appendBlanks("" .equals(Common.cutZero (tempArray [17]).trim()) ?"": "1 : " +Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			
			
			int len = id.length;
			
			
			
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
											+ appendBlanks("" , 21)
											+ appendBlanks(tempArray[14], 10);
									rtnResult += gubun1;
									rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
									rtnResult += getResultRemark(tempArray[22]);
									rtnResult += "\r\n\r\n";
				} 
			
		} else if (is90100) {
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks("선별검사항목", 22)
                    + appendBlanks( " 기준치 " , 12) + appendBlanks(" 결과      " , 16)
                    + appendBlanks( "위 험 도 " , 10);
			rtnResult += gubun2;
			
			
			rtnResult += appendBlanks(" 신경관 결손증 ", 24)
					+ appendBlanks(" 2.5 MoM ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[21]).trim()) ?"":"1 : " + Common.cutZero(tempArray[21]) , 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 23)
					+ appendBlanks(" 1 : 200 ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[19]).trim()) ?"":"1 : " +Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 25)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[17]).trim()) ?"":"1 : " +Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);

			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
											+ appendBlanks("" , 21)
											+ appendBlanks(tempArray[14], 10);
									rtnResult += gubun1;
									rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
									rtnResult += getResultRemark(tempArray[22]);
									rtnResult += "\r\n\r\n";
				} 
		}
		else {
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks("선별검사항목", 22)
                    + appendBlanks( " 기준치 " , 12) + appendBlanks(" 결과      " , 16)
                    + appendBlanks( "위 험 도 " , 10);
			rtnResult += gubun2;
			
			
			rtnResult += appendBlanks(" 신경관 결손증 ", 24)
					+ appendBlanks(" 2.5 MoM ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[21]).trim()) ?"":"1 : " + Common.cutZero(tempArray[21]) , 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\r\n" + appendBlanks(" 에드워드증후군 ", 23)
					+ appendBlanks(" 1 : 200 ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[19]).trim()) ?"":"1 : " +Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\r\n" + appendBlanks(" 다운증후군 ", 25)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks("".equals(Common.cutZero(tempArray[17]).trim()) ?"":"1 : " +Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);

			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
			rtnResult += getResultRemark(tempArray[22]);
			rtnResult += "\r\n\r\n";
			} else {
				
				rtnResult += "\r\n" + appendBlanks(" Down SD Risk by Age 만 35세 ", 30)
											+ appendBlanks("" , 21) //20190320양태용 기존 21X 17O
											+ appendBlanks(tempArray[14], 10);
									rtnResult += gubun1;
									rtnResult += "\r\n\r\n* 다운증후군/에드워드 증후군 : \r\n\r\n";
									rtnResult += getResultRemark(tempArray[22]);
									rtnResult += "\r\n\r\n";
				} 
		}

	
		if (!tempArray[23].equals("")){
			
			
			rtnResult += getResultRemark(" * 신경관 결손 : ") + "\r\n\r\n";
			rtnResult += getResultRemark(tempArray[23]);
			
			}
		
		return rtnResult;
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
	public DownloadSUYOUNGI(String id, String fdat, String tdat, Boolean isRewrite) {
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
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-01-30 오후 6:51:33)
	 * 
	 * @return java.lang.String
	 */
	public String getHCVMethods() {
		String str = "";
		str += "[검사방법]\r\n";
		str += " PCR- DNA sequencing (염기서열분석법)\r\n";
		str += " -> 분자생물학기술 중 가장 정확하고 최신방법이며 고가의 장비와 고도의 기술이 필요한 염기서열\r\n";
		str += " 분석법을 이용하여 HCV 유전자형을 직접 검출함. \r\n";
		str += "\r\n";
		str += " 혈청에서 RNA 추출  -> RNA를 reverse transcriptase를 이용하여 cDNA로 전환\r\n";
		str += " ->HCV 5' UTR 부위에서  특이  Primer 를 사용하여 PCR 증폭 ->PCR 산물을 전기영동으로 확인\r\n";
		str += " ->염기서열분석 (sequencing) 실시 ->HCV 유전자형 분석하여 결과 보고 \r\n";
		str += "\r\n";
		str += "[검사 의의]\r\n";
		str += "\r\n";
		str += " C형간염바이러스 (HCV)는 RNA 바이러스로서 전세계인구의 약 1%가 감염되어 있으며 만성감염이 \r\n";
		str += " 될 경우 간경화나 간암으로 진행 가능성이 높습니다.\r\n";
		str += "\r\n";
		str += " HCV는 6종의 주된 genotype이 있고 80종 이상의 subtype이 보고되고 있으며 HCV genotype중에서\r\n";
		str += " HCV 1b 형은 치료가 어렵고 간경화로 발전할 가능성이 높으며 간 이식 후에도 간 질환의 발생빈도가\r\n";
		str += " 높습니다.\r\n";
		str += "\r\n";
		str += " HCV는 유전자형에 따라 치료의 효과, 예후 등이 다르므로, HCV 감염이 되었을 경우 주기적인 \r\n";
		str += " HCV 정량검사 와 함께 HCV genotype 검사를 실시하여야 합니다.\r\n";
		str += "\r\n";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-06-16 오전 9:11:40)
	 * 
	 * @return java.lang.String
	 */
	public String getMTHFR() {
		String str = "";
		// //!

		str += "●   유전자: \r\n";
		str += "      MTHFR [5,10-methylenetetrahydrofolate reductase ; 1p36.3]\r\n";
		str += "\r\n";
		str += "●   검사방법:\r\n";
		str += "      MTHFR 유전자의 codon 1298 위치의 Glutamate(A)가 Alanine(C)으로 바뀌는 것  \r\n";
		str += "      (1298 A>C)을 PCR-RFLP를 이용하여 검사함.\r\n";

		// !

		str += "\r\n";
		str += "●   PCR-RFLP 방법:\r\n";
		str += "      목표하는 유전자의 특정부위를 PCR을 이용하여 증폭시키고, 관련된 염기서열을 인식하는 \r\n";
		str += "      제한효소를 처리한 후, 잘라진 PCR 단편을 전기영동으로 분석하여 돌연변이 여부를\r\n";
		str += "      판단하는 방법.\r\n";
		// !

		str += "\r\n";
		str += "●   임상적의의\r\n";
		str += "      - MTHFR은 엽산 대사에 중요한 작용을 하는 효소 유전자로 메틸기를 homocysteine에 \r\n";
		str += "        전달하여 methionine으로 합성하는 대사과정에 관여합니다.\r\n";
		str += "\r\n";
		str += "      - 가장 대표적인 MTHFR 유전자 변이는 C677T와 A1298C입니다.  \r\n";
		str += "\r\n";
		str += "      - 677T와 1298C의 복합돌연변이인 경우 677 homozygote와 유사한 임상적 의의를  \r\n";
		str += "        가진다고 보고되고 있습니다.\r\n";
		str += "\r\n";
		str += "      - 관련질환 : Hyperhomocysteinemia, Cardiovascular disease, Thromobisis 등\r\n";
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-20 오전 9:39:07)
	 * 
	 * @return java.lang.String
	 */
	public String getQuantiFERON() {
		String str = "";
		str += "◈ 검사원리\r\n";
		str += " 혈액내에 존재하는 T세포에 결핵균 특이항원(ESAT-6,CFP-10,TB7.7(p4))을 노출시켜\r\n";
		str += "IFN-γ 농도를 측정하는 방법입니다. 결핵균에 노출되어 감작된 T세포들은 감작되지 않은\r\n";
		str += "T세포에 비해  IFN-γ 분비능이 높습니다. 증가된 IFN-γ은 효소면역분석법에 의해 측정됩니다.\r\n";
		str += "\r\n";
		str += "◈ 검사방법\r\n";
		str += " 음성대조(Nil control), 결핵균 특이항원(TB antigen:ESAT-6,CFP-10,TB7.7(p4)),양성대조\r\n";
		str += "(Mitogen control)를 37℃에서 16-24시간 배양 후 분리된 혈장을 효소면역분석법으로 측정\r\n";
		str += "하여 각 항원의 IFN-γ 반응값을 구합니다. 결핵균 특이항원의 IFN-γ 반응의 결과값에서 \r\n";
		str += "음성대조의 IFN-γ 반응의 결과값을 뺀 값(TB antigen minus Nil)을 분별한계치(cut off)를 \r\n";
		str += "기준으로 음성,양성으로 판정됩니다.\r\n";
		str += "\r\n";
		str += "◈ 검사의의\r\n";
		str += " 결핵균 특이항원인 ESAT-6,CFP-10,TB7.7(p4)는 BCG 접종에 영향을 받지 않으며, 대부\r\n";
		str += "분의 NTM(nontuberculous mycobacteria) 감염에도 영향을 받지 않기 때문에, 잠복결핵\r\n";
		str += "(latent tuberculosis)을 진단하는데 우수한 검사법입니다. 그러나, cellular immune\r\n";
		str += "response에 이상이 있는 환자의 경우에는 False negative or Indeterminate 결과를 보일 수 있으므로, \r\n";
		str += "HIV 감염자,장기이식환자, 면역억제제를 장기간 사용하는 환자 등에서는 결과 해석에 주의를 요합니다. \r\n";
		str += "\r\n";
		return str;
	}
	
	public String getHpyloriPcr() {
		String str = "[Comment]\r\n" 
                + "PCR 검사는 검체 내 균 수가 적거나 부적절한 검체 희석 또는 증폭 억제물질이 존재하는 경우 \r\n"
				+ "위음성이 나올 수 있습니다.\r\n"
                + "또한, PCR 검사는 유전자 유무를 검사하므로 생존균과 사균의 구분이 안되어 위양성의 가능성이 있습니다. \r\n"
                + "결과 해석 시, 환자의 임상 양상과 연관지어 판단하시기 바랍니다.";
		str += "\r\n";
		return str;
	}

	protected String getRemarkTxt2(String str[]) {
		StringBuffer b = new StringBuffer("");
		boolean isSensi = false;
		// boolean isSensi2 = false;

		// !
		if (str.length == 0)
			return null;

		// !
		for (int i = 0; i < str.length; i++) {
			int kk = str[i].trim().lastIndexOf("<균　명>");
			if (kk > -1) {
				b.append("\r\n" + "<균 명 >" + "\r\n\r\n");
				b.append(str[i].trim().substring(5).trim() + " \r\n");
				isSensi = true;
			} else {
				if (isSensi && str[i].trim().trim().startsWith("1")) {
					b.append("                                         " + str[i].trim().trim() + "\r\n\r\n");
					// isSensi2 = true;
				} else {
					// if (isSensi2) {
					b.append(str[i].trim() + "\r\n");
					// } else {
					// b.append(str[i].trim() + "\r\n");
					// }
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

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public String H1N1() {
		String str = "";
		str += "▣ 검체\r\n";
		str += "비인후 또는 인후 도찰물\r\n";
		str += "\r\n";
		str += " ▣ 검사방법\r\n";
		str += " Real time RT-PCR(실시간역전사중합효소연쇄반응법)" + "\r\n";
		str += " 1. 환자의 검체로부터 RNA를 추출합니다. " + "\r\n";
		str += " 2. Influenza A(H1N1)의 Hemagglutinin(HA) 영역에서 고안된 Primer로 RT-PCR을 실시합니다." + "\r\n";
		str += " 3. 증폭된 PCR 산물을 Influenza A(H1N1)-specific probe(5'-FAM; 5'BHQ1)와 hybridization 시킵니다. " + "\r\n";
		str += " 4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는" + "\r\n";
		str += "  형광을 실시간으로 측정합니다." + "\r\n";
		str += "▣ Remark" + "\r\n";
		str += " 1. 본 검사는 Real time RT-PCR 원리를 이용하여 신종인플루엔자 A [Influenza A(H1N1)]에 특이한 primer를" + "\r\n";
		str += "   사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다." + "\r\n";
		str += " 2. Influenza A 검사를 동시에 실시하여 확인된 결과입니다." + "\r\n";
		str += " 3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다." + "\r\n";
		str += "" + "\r\n";

		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String 72047
	 */
	public String H1N1_NEW() {
		String str = "";
		str += "▣ Specimen   : 비인후 또는 인후 도찰물\r\n";
		str += "▣ Methods : Real-time RT-PCR(실시간역전사중합효소연쇄반응법) \r\n";
		str += "\r\n";
		str += "1. 환자의 검체로부터 RNA를 추출합니다.\r\n";
		str += "2. 각 영역에서 특이한 primer를 사용하여 RT-PCR을 실시합니다.\r\n";
		str += "    - Influenza A (H1N1, 신종플루) - Hemagglutinin(HA)\r\n";
		str += "    - Common Influenza A - Matrix protein(MP)\r\n";
		str += "    - Influenza A(H1N1, 계절성) - Hemagglutinin(HA)\r\n";
		str += "    - Influenza A(H3N2, 계절성) - Hemagglutinin(HA)\r\n";
		str += "\r\n";
		str += "3. 증폭된 PCR 산물을 각각의 specific probe와 hybridization 시킵니다. \r\n";
		str += "    -Influenza A(H1N1, 신종플루)-specific probe(5'-FAM; 3'-BHQ1)\r\n";
		str += "    -Common Influenza A- specific probe(5'-FAM; 3'-BHQ1)\r\n";
		str += "    -Influenza A(H1N1, 계절성) -specific probe(5'-FAM; 3'-TAMRA)\r\n";
		str += "    -Influenza A(H3N2, 계절성) -specific probe(5'-HEX; 3'-BHQ1)\r\n";
		str += "\r\n";
		str += "4. Dual-labeled fluogenic probe에서 quencher가 제거되는 순간 probe의 report dye로부터 방출되는 \r\n";
		str += "   형광을 실시간으로 측정합니다.\r\n";
		str += "\r\n";
		str += "▣ Remark \r\n";
		str += "\r\n";
		str += "1. 본 검사는 realtime RT-PCR 원리를 이용하여 신종인플루엔자 및 계절성 인플루엔자에 특이한 \r\n";
		str += "   primer를 각각 사용하여 증폭시킨 후 바이러스를 실시간으로 측정하는 확진 검사입니다.\r\n";
		str += "\r\n";
		str += "2. 계절성 Influenza A 의 검사는 Influenza A (H1N1, 신종플루)에서 음성, Common Influenza A 에서 양성인\r\n";
		str += "   경우에 한하여 2차 검사로 진행됩니다. \r\n";
		str += "\r\n";
		str += "3. 약양성(weak positive)으로 검출된 경우는 잠복기이거나 회복기일 수 있으므로 2-3일 후 재검 바랍니다.\r\n";
		return str;
	}

	
	
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-08-25 오전 10:23:21)
	 * 
	 * @return java.lang.String
	 */
	public String HLAB27() {
		String str = "";
		str += "▣ 검사방법\r\n";
		str += "PCR (Polymerase Chain Reaction)\r\n";
		str += "\r\n";
		str += "  -> 전혈에서 DNA를 추출 \r\n";
		str += "  -> HLA-B27 유전자에 특이한 시발체로 PCR시행 " + "\r\n";
		str += "  -> 전기영동으로 증폭된 PCR 산물 확인 " + "\r\n";
		str += "  -> 결과보고 " + "\r\n";
		str += "" + "\r\n";
		str += "▣ 검사 의의" + "\r\n";
		str += "" + "\r\n";
		str += " HLA-B27 유전자는 강직성척수염환자의 90% 정도에서 나타납니다." + "\r\n";
		str += "" + "\r\n";
		str += " 강직성척수염 (Ankylosing spondylitis) 이란 류마티스 질환의 일종으로 16~35세의 남자들에서" + "\r\n";
		str += " 주로 발생하며, 척추관절이나 무릎, 발목, 발가락, 골반, 갈비뼈 등과 같은 관절에 염증을 일으킬 수" + "\r\n";
		str += " 있습니다. " + "\r\n";
		str += "" + "\r\n";
		str += " HLA-B27 유전자와 강직성척수염과의 유전적 관련성이 매우 높다고 보고되고 있지만, 이 유전자는 " + "\r\n";
		str += " 유전자가 발견되었다고 하여 반드시 강직성척수염이 발생한다고 볼 수는 없습니다." + "\r\n";
		str += "" + "\r\n";
		str += " 이외에 HLA-B27 유전자와 연관성이 있는 질환은 Reiter's disease, Juvenile rheumatoid arthritis," + "\r\n";
		str += " Anterior uveitis 등이 있습니다." + "\r\n";
		return str;
	}

	public void makeDownloadFile() {
			row = 2;
			row2 = 1;
			try {
				workbook = Workbook.createWorkbook(new File(savedir	+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String ArraryResult[] = null;
				ArraryResult = new String[] { "검사ID", "접수ID", "접수일자", "시간",
						"환자이름", "챠트번호", "성별", "나이", "유형", "코드", "명칭", "의뢰일자",
						"검사결과", "서술결과", "하이로우", "단위", "참고치", "번호"};

				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

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
			
			
//			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
//				lastData = inspectCode[399].trim().substring(0, 5);
//				lastindex = 399;
//				isNext = true;
//				// 임시로 수정 기존 소스의 경우 (추후 문제 되면 기존 소스로 수정 계획)
//				// while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
//				while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals(""))) {
//					cnt--;
//					if (inspectCode[lastindex].trim().substring(5).equals("00")) {
//						cnt--;
//					}
//				}
//			}
			
			// yty : 400개 마지막에 부속검사 오면 집계 넘어가고  
						if (cnt == 400 && inspectCode[399].trim().length() == 7) {
							lastData = inspectCode[399].trim().substring(0, 5);
							lastindex = 399;
							isNext = true;

							// !
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

				String curDate = "";
				String curNo = "";
				// !
				data[0] = bdt[i]; // 걍~~
				data[1] = Common.cutZero(img[i]); // 내원번호
				data[2] = inc[i]; // 외래구분
				//0:입원, 1:외래
				if(data[2].equals("0")) data[2] ="입원";
				else if(data[2].equals("1")) data[2] ="외래";
				data[3] = ""; // 의뢰일자
				
				if(hosCode[i].trim().equals("34329") && specNo[i].equals("")){
					continue;
				}
					else {
							data[4] = specNo[i].trim(); // 검체번호
					}
				
				try {
					data[5] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
				} catch (Exception ee) {
					data[5] = "";
				}
				/*
				if (hosCode[i].trim().equals("23401")||hosCode[i].trim().equals("28279")) {
					try {
						data[5] = cutHrcvDateNumber2(cns[i])[0]; // 처방번호
					} catch (Exception eee) {
						data[5] = ""; // 처방번호
					}
				}
				20190116 양태용 소스정리
				*/
				data[6] = clientInspectCode[i].trim(); // 병원검사코드(처방코드)
				data[7] = inspectName[i]; // 검사명(처방명)
				data[8] = hosSamp[i]; // 검체명(검체코드)

				data[9] = bbseq[i]; // 일련번호
				data[10] = ""; // 검체코드
				data[11] = "00:00"; // 여유필드

				data[12] = chartNo[i]; // 차트번호
				data[13] = patName[i]; // 수진자명
				data[14] = securityNo[i]; // 주민번호

				data[15] = age[i]; // 나이
				data[16] = sex[i]; // 성별
				data[17] = ""; // 과
				data[18] ="";
				
				data[14]= data[14].replace("-*******", "");
				data[18] = getPatientInformationNew(rcvDate[i],rcvNo[i]).toString(); // 병동
				
			
				data[19] = ""; // 참고사항

				data[20] = ""; // 학부
				data[30] = "11370319"; // 요양기관번호
				data[21] = bdt[i]; // 검사완료일
				try {
					data[22] = Common.getDateFormat(cutHrcvDateNumber(cns[i])[1]); // 처방일자
				} catch (Exception eee) {
					data[22] = ""; // 처방일자
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
					data[39] = getReferenceValueNotBlank(remark);
				} catch (Exception e) {
					data[39] = "";
				}
				// !
				data[26] = ""; // 진료의사
				data[27] = ""; // 추가키1
				data[28] = ""; // 추가키2
				data[29] = "";

				// !
				data[30] = "11370319"; // 요양기관번호
				data[31] = rcvDate[i].trim(); // 접수일자
				data[32] = rcvNo[i].trim(); // 접수번호
				data[33] = inspectCode[i].trim(); // 검사코드
				data[34] = ""; // 단문결과

				// !
				
				data[35] = ""; // 문장결과

				data[36] = ""; // 수치+문장
				data[37] = ""; // 상태
				data[38] = ""; // 리마크
				
				// SimpleDateFormat의 형식을 선언한다.
				SimpleDateFormat original_format = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd");
				
				// ! 날짜 타입 변경 작
				try {
					// 문자열 타입을 날짜 타입으로 변환한다. 
					Date original_date = original_format.parse(data[31]);
					// 날짜 형식을 원하는 타입으로 변경한다. 
					data[31] = new_format.format(original_date);
				} catch (Exception e) {
					data[31] = rcvDate[i].trim();
				}
			

				if (resultType[i].trim().equals("C")) {
					data[34] = result[i].trim(); // 문자결과
					data[36] = result[i].trim();
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					remark[4] = age[i];
					data[35] = "";
					try {
						if (isTriple(inspectCode[i])) {
							data[35] = getResultTriple(new String[] {rcvDate[i], rcvNo[i], inspectCode[i] });
							
							curDate = rcvDate[i];
							curNo = rcvNo[i];
							String thisTimeCode="";
					
							for ( thisTimeCode = inspectCode[i++].trim().substring(0, 5); isTriple(inspectCode[i])&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
							{
								if (++i == cnt)
									break;
							}
							i--;
							data[34]="";
						}
						} catch (Exception e) {
							e.printStackTrace();
						}
					data[39] = getReferenceValueAge(remark);
					boolean isTxtRltB = false;
					
					
					if (inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("31189")) && (
							(rcvDate[i].trim().substring(0, 8).equals("20180828")))) { // 단독
						continue;
					}	
					
					

					if (!isTxtRltB
							&& (hosCode[i].trim().equals("29226"))&&
							( inspectCode[i].trim().substring(0, 5).equals("11052"))) {
						data[35] = "결과확인(스캔결과)";
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += "";
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB
						&& (hosCode[i].trim().equals("27991"))
						&& (inspectCode[i].trim().substring(0, 5).equals("00095")|| inspectCode[i].trim().substring(0, 5).equals("11002"))) {
					data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
					data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
							+ getReferenceValue(remark);
					data[34] = ""; // 문자결과
					data[39] = ""; // 참고치
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					isTxtRltB = true;
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
						if (++i == cnt || i >= cnt)
							break;
					}
					i--;
					data[36] = data[35].trim();
				}
				if (!isTxtRltB
							&& (hosCode[i].trim().equals("32286"))
							&& (inspectCode[i].trim().substring(0, 5).equals("31001")|| inspectCode[i].trim().substring(0, 5).equals("72227")
									|| inspectCode[i].trim().substring(0, 5).equals("71252")|| inspectCode[i].trim().substring(0, 5).equals("81371")
									|| inspectCode[i].trim().substring(0, 5).equals("72182")|| inspectCode[i].trim().substring(0, 5).equals("72206")
									|| inspectCode[i].trim().substring(0, 5).equals("71259")|| inspectCode[i].trim().substring(0, 5).equals("11052")
									|| inspectCode[i].trim().substring(0, 5).equals("21068")|| inspectCode[i].trim().substring(0, 5).equals("21638")
									|| inspectCode[i].trim().substring(0, 5).equals("21677")|| inspectCode[i].trim().substring(0, 5).equals("72020")
									|| inspectCode[i].trim().substring(0, 5).equals("31002")|| inspectCode[i].trim().substring(0, 5).equals("21683")
									|| inspectCode[i].trim().substring(0, 5).equals("81469")|| inspectCode[i].trim().substring(0, 5).equals("00405")
									)) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i >= cnt)
								break;
						}
						i--;
						data[36] = data[35].trim();
					}
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("32286"))
							&& (inspectCode[i].trim().substring(0, 5).equals("72194") || inspectCode[i].trim().substring(0, 5).equals("72261"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i >= cnt)
								break;
						}
						i--;
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"\r\n"+getHpyloriPcr();
						data[36] = data[35].trim();
					}
					
					
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("31189")|| hosCode[i].trim().equals("23333")||hosCode[i].trim().equals("28933"))
							&& (inspectCode[i].trim().substring(0, 5).equals("00690")|| inspectCode[i].trim().substring(0, 5).equals("00691"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i >= cnt)
								break;
						}
						i--;
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("31189")||hosCode[i].trim().equals("28933"))
							&& (inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("00095")
									|| inspectCode[i].trim().substring(0, 5).equals("21677"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();
					}
					
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("31189")||hosCode[i].trim().equals("28933"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("72182")|| inspectCode[i].trim().substring(0, 5).equals("72245")
									|| inspectCode[i].trim().substring(0, 5).equals("72232")|| inspectCode[i].trim().substring(0, 5).equals("72206")
									|| inspectCode[i].trim().substring(0, 5).equals("72242") )) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"\r\n"+getHpyloriPcr();
						data[36] = data[35].trim();
					}
				
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("31189"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("72242")|| inspectCode[i].trim().substring(0, 5).equals("72189"))) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i > cnt)
								break;
						}
						i--;
						if(Integer.parseInt(rcvDate[i]) >= 20190801){
						data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();
						}else{
							data[35] = data[35].trim()+"\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i])+"\r\n"+getHpyloriPcr();
							data[36] = data[35].trim();
						}
					}
				
					
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("34329") || hosCode[i].trim().equals("34564"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("00405") || inspectCode[i].trim().substring(0, 5).equals("72245") 
									|| inspectCode[i].trim().substring(0, 5).equals("72185") || inspectCode[i].trim().substring(0, 5).equals("72189") 
									|| inspectCode[i].trim().substring(0, 5).equals("72242") || inspectCode[i].trim().substring(0, 5).equals("72182") 
									|| inspectCode[i].trim().substring(0, 5).equals("21653") || inspectCode[i].trim().substring(0, 5).equals("72020") 
									|| inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("21677") 
									|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("11310")
									|| inspectCode[i].trim().substring(0, 5).equals("00304") || inspectCode[i].trim().substring(0, 5).equals("00301") 
									|| inspectCode[i].trim().substring(0, 5).equals("71139") 
									)) {
						data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
						data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
								+ getReferenceValue(remark);
						data[34] = ""; // 문자결과
						data[39] = ""; // 참고치
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						isTxtRltB = true;
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
								.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i], history[i], sex[i], age[i] }).trim();
							if (++i == cnt || i >= cnt)
								break;
						}
						i--;
						data[35] = data[35].trim()+"\r\n\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
						data[36] = data[35].trim();
					}
					
					// 선천성대사이상검사 (tendum) 코드 합치기
					if (!isTxtRltB
							&& (hosCode[i].trim().equals("34329") || hosCode[i].trim().equals("34564"))
							&& ( inspectCode[i].trim().substring(0, 5).equals("05562"))) {
						Vector vmast = new Vector();
						data[35] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) ;
						curDate = rcvDate[i];  
						curNo = rcvNo[i];						
						String thisTimeCode = inspectCode[i++].trim().substring(0, 5);
							for (;curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
							try {
							
								if ( inspectCode[i].trim().substring(0, 5).equals("05563") 
										||inspectCode[i].trim().substring(0, 5).equals("05564") || inspectCode[i].trim().substring(0, 5).equals("05565")
										|| inspectCode[i].trim().substring(0, 7).equals("0556700")
										 || inspectCode[i].trim().substring(0, 7).equals("0556701")|| inspectCode[i].trim().substring(0, 7).equals("0556702")
										 || inspectCode[i].trim().substring(0, 7).equals("0556703")|| inspectCode[i].trim().substring(0, 7).equals("0556704")
										 || inspectCode[i].trim().substring(0, 7).equals("0556705")|| inspectCode[i].trim().substring(0, 7).equals("0556706")
										 || inspectCode[i].trim().substring(0, 7).equals("0556707")|| inspectCode[i].trim().substring(0, 7).equals("0556708")
										 || inspectCode[i].trim().substring(0, 7).equals("0556709")|| inspectCode[i].trim().substring(0, 7).equals("0556710")
										 || inspectCode[i].trim().substring(0, 7).equals("0556711")|| inspectCode[i].trim().substring(0, 7).equals("0556712")
										 || inspectCode[i].trim().substring(0, 7).equals("0556713")|| inspectCode[i].trim().substring(0, 7).equals("0556714")
										 || inspectCode[i].trim().substring(0, 7).equals("0556715")|| inspectCode[i].trim().substring(0, 7).equals("0556716")
										 || inspectCode[i].trim().substring(0, 7).equals("0556717")|| inspectCode[i].trim().substring(0, 7).equals("0556718")
										 || inspectCode[i].trim().substring(0, 7).equals("0556719")|| inspectCode[i].trim().substring(0, 7).equals("0556720")
										 || inspectCode[i].trim().substring(0, 7).equals("0556721")|| inspectCode[i].trim().substring(0, 7).equals("0556722")
										 || inspectCode[i].trim().substring(0, 7).equals("0556723")|| inspectCode[i].trim().substring(0, 7).equals("0556724")
										 || inspectCode[i].trim().substring(0, 7).equals("0556725")|| inspectCode[i].trim().substring(0, 7).equals("0556726")
										 || inspectCode[i].trim().substring(0, 7).equals("0556727")|| inspectCode[i].trim().substring(0, 7).equals("0556728")
										 || inspectCode[i].trim().substring(0, 7).equals("0556729")|| inspectCode[i].trim().substring(0, 7).equals("0556730")
										 || inspectCode[i].trim().substring(0, 7).equals("0556731")|| inspectCode[i].trim().substring(0, 7).equals("0556732")
										 || inspectCode[i].trim().substring(0, 7).equals("0556733")|| inspectCode[i].trim().substring(0, 7).equals("0556734")
										 || inspectCode[i].trim().substring(0, 7).equals("0556735")|| inspectCode[i].trim().substring(0, 7).equals("0556736")
										 || inspectCode[i].trim().substring(0, 7).equals("0556737")|| inspectCode[i].trim().substring(0, 7).equals("0556738")
										 || inspectCode[i].trim().substring(0, 7).equals("0556739")|| inspectCode[i].trim().substring(0, 7).equals("0556740")
										 || inspectCode[i].trim().substring(0, 7).equals("0556741")|| inspectCode[i].trim().substring(0, 7).equals("0556742")
										 || inspectCode[i].trim().substring(0, 7).equals("0556743")|| inspectCode[i].trim().substring(0, 7).equals("0556744")
										 || inspectCode[i].trim().substring(0, 7).equals("0556745")|| inspectCode[i].trim().substring(0, 7).equals("0556746")
										 || inspectCode[i].trim().substring(0, 7).equals("0556747")|| inspectCode[i].trim().substring(0, 7).equals("0556748")
										 || inspectCode[i].trim().substring(0, 7).equals("0556749")|| inspectCode[i].trim().substring(0, 7).equals("0556750")
										 || inspectCode[i].trim().substring(0, 7).equals("0556751")|| inspectCode[i].trim().substring(0, 7).equals("0556752")
										 || inspectCode[i].trim().substring(0, 7).equals("0556753")|| inspectCode[i].trim().substring(0, 7).equals("0556754")
										 || inspectCode[i].trim().substring(0, 7).equals("0556755")|| inspectCode[i].trim().substring(0, 7).equals("0556756")){
																				
											vmast.addElement(appendBlanks(inspectName[i], 30)+ appendBlanks(result[i].trim(), 21));
				                            i++;
									
									}else{
									break;
								}   
							} catch (Exception e) {
							}
							
							if (i >= cnt)
								break;
						}
						i--;
						data[35] += getResulttandem(data[7].toString(), vmast)+ getDivide() +"\r\n\r\n[Remark]\r\n"+getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]) ;
						data[34] = "";
					} 
					
								
					// ! 여기서 부터
					// 문장형--------------------------------------------------------------------------------------------------------------------------------------------------------
				} else {
					boolean isTxtRltC = false;
					
					data[34] = ""; // 문자결과
					data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
					data[36] = data[35].trim();
					data[39] = ""; // 참고치
					data[37] = highLow[i]; // 결과상태
					// 조직 면허번호 짤리는 문제위해 임의로 전문의 이름만 출력 되도록 수정
					int str_index = data[35].indexOf("전문의");
					if (str_index > 10) {
						data[35] = data[35].substring(0, str_index + 7);
						data[36] = data[35].trim();
					}
		
				}

				// ------------------------------------------------------------------------
				
//				// 27991 20170914 
				if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("27991")  && inspectCode[i].trim().substring(5, 7).equals("00")) {
					
					data[35] = data[35] +"\r\n" + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
					
				}	
				
				if (inspectCode[i].trim().substring(0, 5).equals("11052")) {
					remarkCode = "";
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
			
				// !-------------------------------------------
				boolean isTxtRltA = false;
			
				// ! 참고치
				try {
					data[39] = data[39].trim();
				} catch (Exception yxx) {
				}
			
				//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if (
					 (inspectCode[i].trim().equals("31100")||inspectCode[i].trim().equals("31101")
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
								||inspectCode[i].trim().equals("31124")||inspectCode[i].trim().equals("31010"))) {
					
					data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
					data[36] = data[35].trim();
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001")
								||inspectCode[i + 1].substring(0, 5).equals("31011"))
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[35] = data[35] + "\r\n" + getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i + 1]);
							data[36] = data[35].trim();
							i++;
							// culture_flag = true;
						} else {
							data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
							data[36] = data[35].trim();
						}
					} catch (Exception e) {
						data[35] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]);
						data[36] = data[35].trim();
					}
				}
				
				
				if (hosCode[i].trim().equals("32286")
						&& ( inspectCode[i].trim().substring(0, 5).equals("00804") || inspectCode[i].trim().substring(0, 5).equals("64665")
								)) {
					data[35] = data[34] + "\r\n" + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
					data[36] = data[35].trim();
					data[34] = "";
					
				}

				
				data[37] = highLow[i]; // 결과상태 20170201 양태용 판정 추가
				
				if (hosCode[i].trim().equals("27991")||hosCode[i].trim().equals("32453")||hosCode[i].trim().equals("28540")||hosCode[i].trim().equals("33223")
						||hosCode[i].trim().equals("29130")||hosCode[i].trim().equals("29579")||hosCode[i].trim().equals("34329")|| hosCode[i].trim().equals("34564")) {
					
					data[37] = data[37].replace(".", " "); // 판정에 . 빼기
				}
				
				// 20200929 울산 지점장님 요청사항(김영상 차장에게 전달 받음)
				if (hosCode[i].trim().equals("29130") && (!result[i].trim().equals("Negative") && !result[i].trim().equals("**"))
					&& (inspectCode[i].trim().substring(0, 5).equals("72020") || inspectCode[i].trim().substring(0, 5).equals("71311")
							|| inspectCode[i].trim().substring(0, 5).equals("72185"))) {
					
					data[37] = "E"; // 판정에 . 빼기
				}
				
				// !
				if (!debug) {
					int lens = getExcelFieldNameArray().length;
					try {
						for (k = 0; k < lens; k++) {
							label = new jxl.write.Label(k, row-1, data[Integer.parseInt(getExcelFieldArray()[k].toString())]);
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
}

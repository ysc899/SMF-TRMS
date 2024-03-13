package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1

import java.io.File;

import jxl.Workbook;

import com.neodin.comm.Common;


/*
  
  인천 성모병원 

*/
public class DownloadCMCISM extends ResultDownload {   
	/**
	 * 메소드 설명을 삽입하십시오.
	 * 작성 날짜: (2009-04-08 오후 3:07:55)
	 * @return java.lang.String
	 */
	public String[] cutHrcvDateNumber2(String str) {

		//!
		String src_[] = new String[] {"", ""};

		//!
		if (str == null || src_.equals(""))
			return src_;

		//!
		src_ = Common.getDataCut(str, "D");
		if (src_ == null || src_.length == 0)
			return new String[] {"", ""};

		//!
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
	private boolean debug = false;   
	private boolean isData = true;
	//

	/**
	 * 메소드 설명을 삽입하십시오.
	 * 작성 날짜: (2013-05-01 오후 4:40:54)
	 * @return boolean
	 * @param gcd java.lang.String
	 *
	 * 아래 검사코드는 이미지로 생성 되어야 하는 이미지
	 *
	 */
	public boolean isText(String gcd) {
		gcd = gcd.substring(0, 5);
		if (gcd.equals("00048"))
			return true;
		if (gcd.equals("21574"))
			return true;
		if (gcd.equals("21575"))
			return true;
		if (gcd.equals("81416"))
			return true;
		if (gcd.equals("64493"))
			return true;
		if (gcd.equals("64494"))
			return true;
		if (gcd.equals("41450"))
			return true;
		if (gcd.equals("31052"))
			return true;
		if (gcd.equals("21641"))
			return true;
		if (gcd.equals("81375"))
			return true;
		return false;
	}
public DownloadCMCISM(String id, String fdat, String tdat,Boolean isRewrite) {
	setId(id);
	setfDat(fdat);
	settDat(tdat);
	setIsRewrite(isRewrite.booleanValue());
	initialize();
}
public String appendBlanks(String src, int length) {
	return OracleCommon.appendBlanks(src, length);
}
public void closeDownloadFile() {
	if (!debug && isData) {
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
	/**
 * 메소드 설명을 삽입하십시오.
 * 작성 날짜: (2009-10-25 오후 6:54:21)
 * @return java.lang.String
 */
public String[] getUintCut(String unt___) {
	String str[] = new String[] {"", "", ""};
	str = Common.getDataCut(unt___, ",");
	if (str.length == 2) {
		str = new String[] {str[0], str[1].replace(',', ' ').trim(), ""};
	}
	return str;
}
/**
	 * 메소드 설명을 삽입하십시오.
	 * 작성 날짜: (2013-05-01 오후 4:40:54)
	 * @return boolean
	 * @param gcd java.lang.String
	 *
	 * 아래 검사코드는 이미지로 생성 되어야 하는 이미지
	 *
	 */
	public boolean isImage(String gcd) {
		gcd = gcd.substring(0, 5);
		if (gcd.equals("21575"))
			return true;
		if (gcd.equals("81375"))
			return true;
		if (gcd.equals("41450"))
			return true;
		if (gcd.equals("31052"))
			return true;
		if (gcd.equals("21574"))
			return true;
		if (gcd.equals("21641"))
			return true;
		
		if (gcd.equals("81000"))
			return true;
		if (gcd.equals("72241"))
			return true;
		if (gcd.equals("71245"))
			return true;
		if (gcd.equals("71280"))
			return true;
		if (gcd.equals("71069"))
			return true;
		if (gcd.equals("71250"))
			return true;
		if (gcd.equals("71086"))
			return true;
		if (gcd.equals("71118"))
			return true;
		if (gcd.equals("71119"))
			return true;
		if (gcd.equals("71253"))
			return true;
		if (gcd.equals("71070"))
			return true;
		if (gcd.equals("71071"))
			return true;
		if (gcd.equals("71084"))
			return true;
		if (gcd.equals("70041"))
			return true;
		if (gcd.equals("81373"))
			return true;
		if (gcd.equals("81374"))
			return true;
		if (gcd.equals("71095"))
			return true;
		if (gcd.equals("72036"))
			return true;
		if (gcd.equals("81386"))
			return true;
		if (gcd.equals("81420"))
			return true;
		if (gcd.equals("71226"))
			return true;
		if (gcd.equals("72057"))
			return true;
		if (gcd.equals("64458"))
			return true;
		if (gcd.equals("64459"))
			return true;
		if (gcd.equals("81406"))
			return true;
		if (gcd.equals("81424"))
			return true;
		if (gcd.equals("81428"))
			return true;
		if (gcd.equals("72008"))
			return true;
		if (gcd.equals("71230"))
			return true;
		if (gcd.equals("71231"))
			return true;
		if (gcd.equals("71232"))
			return true;
		if (gcd.equals("71233"))
			return true;
		if (gcd.equals("71234"))
			return true;
		if (gcd.equals("81384"))
			return true;
		if (gcd.equals("71249"))
			return true;
		if (gcd.equals("71267"))
			return true;
		if (gcd.equals("81433"))
			return true;
		if (gcd.equals("81479"))
			return true;
		if (gcd.equals("64460"))
			return true;
		if (gcd.equals("72079"))
			return true;
		if (gcd.equals("81434"))
			return true;
		if (gcd.equals("71073"))
			return true;
		if (gcd.equals("71139"))
			return true;
		if (gcd.equals("71144"))
			return true;
		if (gcd.equals("81474"))
			return true;
		if (gcd.equals("81439"))
			return true;
		if (gcd.equals("64461"))
			return true;
		if (gcd.equals("81488"))
			return true;
		if (gcd.equals("71266"))
			return true;
		if (gcd.equals("71167"))
			return true;
		if (gcd.equals("64462"))
			return true;
		if (gcd.equals("71140"))
			return true;
		if (gcd.equals("81420"))
			return true;
		if (gcd.equals("71135"))
			return true;
		if (gcd.equals("71154"))
			return true;
		if (gcd.equals("64498"))
			return true;
		if (gcd.equals("72052"))
			return true;
		if (gcd.equals("89978"))
			return true;
		if (gcd.equals("71225"))
			return true;
		if (gcd.equals("81377"))
			return true;
		if (gcd.equals("81074"))
			return true;
		if (gcd.equals("81075"))
			return true;
		if (gcd.equals("64463"))
			return true;
		if (gcd.equals("64500"))
			return true;
		if (gcd.equals("64464"))
			return true;
		if (gcd.equals("64465"))
			return true;
		if (gcd.equals("81359"))
			return true;
		if (gcd.equals("64466"))
			return true;
		if (gcd.equals("64467"))
			return true;
		if (gcd.equals("64468"))
			return true;
		if (gcd.equals("81459"))
			return true;
		if (gcd.equals("64469"))
			return true;
		if (gcd.equals("64470"))
			return true;
		if (gcd.equals("64471"))
			return true;
		if (gcd.equals("41341"))
			return true;
		if (gcd.equals("21582"))
			return true;
		if (gcd.equals("90027"))
			return true;
		if (gcd.equals("90028"))
			return true;
		if (gcd.equals("90100"))
			return true;
		if (gcd.equals("99935"))
			return true;
		if (gcd.equals("99934"))
			return true;
	//	if (gcd.equals("41450"))
	//		return true;
		if (gcd.equals("81494"))
			return true;
		if (gcd.equals("99933"))
			return true;
		if (gcd.equals("99936"))
			return true;
		if (gcd.equals("00309"))
			return true;
		if (gcd.equals("00323"))
			return true;
		if (gcd.equals("00334"))
			return true;
		if (gcd.equals("00322"))
			return true;
		if (gcd.equals("00324"))
			return true;
		if (gcd.equals("00319"))
			return true;
		if (gcd.equals("00320"))
			return true;
		if (gcd.equals("00320"))
			return true;
		if (gcd.equals("00317"))
			return true;
		if (gcd.equals("00318"))
			return true;
		if (gcd.equals("00316"))
			return true;
		if (gcd.equals("00301"))
			return true;
		if (gcd.equals("00305"))
			return true;
		if (gcd.equals("00304"))
			return true;
		if (gcd.equals("00307"))
			return true;
		if (gcd.equals("00312"))
			return true;
		if (gcd.equals("00313"))
			return true;
		if (gcd.equals("00311"))
			return true;
		if (gcd.equals("00310"))
			return true;
		if (gcd.equals("00325"))
			return true;
		if (gcd.equals("41360"))
			return true;
		if (gcd.equals("00654"))
			return true;
		if (gcd.equals("00655"))
			return true;
		if (gcd.equals("05481"))
			return true;
		if (gcd.equals("05483"))
			return true;
		if (gcd.equals("21276"))
			return true;
		if (gcd.equals("81165"))
			return true;
		if (gcd.equals("81051"))
			return true;
		if (gcd.equals("64475"))
			return true;
		if (gcd.equals("71004"))
			return true;
		if (gcd.equals("70000"))
			return true;
		if (gcd.equals("71005"))
			return true;
		if (gcd.equals("71001"))
			return true;
		if (gcd.equals("71006"))
			return true;
		if (gcd.equals("71003"))
			return true;
		if (gcd.equals("70023"))
			return true;
		if (gcd.equals("71124"))
			return true;
		if (gcd.equals("70031"))
			return true;
		if (gcd.equals("64476"))
			return true;
		if (gcd.equals("64477"))
			return true;
		if (gcd.equals("70033"))
			return true;
		if (gcd.equals("70019"))
			return true;
		if (gcd.equals("81422"))
			return true;
		if (gcd.equals("70024"))
			return true;
		if (gcd.equals("70017"))
			return true;
		if (gcd.equals("70030"))
			return true;
		if (gcd.equals("70008"))
			return true;
		if (gcd.equals("70009"))
			return true;
		if (gcd.equals("64478"))
			return true;
		if (gcd.equals("64479"))
			return true;
		if (gcd.equals("64480"))
			return true;
		if (gcd.equals("64481"))
			return true;
		if (gcd.equals("70026"))
			return true;
		if (gcd.equals("70012"))
			return true;
		if (gcd.equals("64482"))
			return true;
		if (gcd.equals("64483"))
			return true;
		if (gcd.equals("64484"))
			return true;
		if (gcd.equals("70029"))
			return true;
		if (gcd.equals("64485"))
			return true;
		if (gcd.equals("64486"))
			return true;
		if (gcd.equals("70003"))
			return true;
		if (gcd.equals("70034"))
			return true;
		if (gcd.equals("70022"))
			return true;
		if (gcd.equals("70035"))
			return true;
		if (gcd.equals("70013"))
			return true;
		if (gcd.equals("70005"))
			return true;
		if (gcd.equals("70011"))
			return true;
		if (gcd.equals("70027"))
			return true;
		if (gcd.equals("70021"))
			return true;
		if (gcd.equals("72041"))
			return true;
		if (gcd.equals("11050"))
			return true;
		if (gcd.equals("64487"))
			return true;
		if (gcd.equals("71012"))
			return true;
		if (gcd.equals("72013"))
			return true;
		if (gcd.equals("81330"))
			return true;
		if (gcd.equals("81336"))
			return true;
		if (gcd.equals("81330"))
			return true;
		if (gcd.equals("31022"))
			return true;
		if (gcd.equals("31079"))
			return true;
		if (gcd.equals("64473"))
			return true;
		if (gcd.equals("81527"))
			return true;
		if (gcd.equals("11131"))
			return true;
		if (gcd.equals("64502"))
			return true;
		if (gcd.equals("81319"))
			return true;
		if (gcd.equals("64499"))
			return true;
		if (gcd.equals("92894"))
			return true;
		if (gcd.equals("11214"))
			return true;
		if (gcd.equals("11210"))
			return true;
		if (gcd.equals("81354"))
			return true;
		if (gcd.equals("89984"))
			return true;
		if (gcd.equals("05501"))
			return true;
		if (gcd.equals("00265"))
			return true;
		return false;
	
	}
/**
 * 메소드 설명을 삽입하십시오.
 * 작성 날짜: (2013-05-01 오후 4:40:54)
 * @return boolean
 * @param gcd java.lang.String
 *
 * 아래 검사코드는 이미지로 생성 되어야 하는 이미지
 *
 */
public boolean isHosImage(String gcd) {   
	    if(gcd.equals("LOC146"))
		  return true;
	    if(gcd.equals("LOC142"))
			  return true;
		if(gcd.equals("LOD118"))
		  return true;
		if(gcd.equals("LOD123"))
		  return true;
		if(gcd.equals("LOD126"))
		  return true;
		if(gcd.equals("LOD301"))
		  return true;
		if(gcd.equals("LOD301"))
		  return true;
		if(gcd.equals("LOD302"))
		  return true;
		if(gcd.equals("LOD302"))
		  return true;
		if(gcd.equals("LOD303"))
		  return true;
		if(gcd.equals("LOD304"))
		  return true;
		if(gcd.equals("LOD304"))
		  return true;
		if(gcd.equals("LOD305"))
		  return true;
		if(gcd.equals("LOD305"))
		  return true;
		if(gcd.equals("LOD305"))
		  return true;
		if(gcd.equals("LOD306"))
		  return true;
		if(gcd.equals("LOD306"))
		  return true;
		if(gcd.equals("LOD307"))
		  return true;
		if(gcd.equals("LOD307"))
		  return true;
		if(gcd.equals("LOD308"))
		  return true;
		if(gcd.equals("LOD503"))
		  return true;
		if(gcd.equals("LOD504"))
		  return true;
		if(gcd.equals("LOD505"))
		  return true;
		if(gcd.equals("LOD506"))
		  return true;
		if(gcd.equals("LOD508"))
		  return true;
		if(gcd.equals("LOD509"))
		  return true;
		if(gcd.equals("LOD511"))
		  return true;
		if(gcd.equals("LOD512"))
		  return true;
		if(gcd.equals("LOD513"))
		  return true;
		if(gcd.equals("LOD514"))
		  return true;
		if(gcd.equals("LOD514"))
		  return true;
		if(gcd.equals("LOD516"))
		  return true;
		if(gcd.equals("LOD516"))
		  return true;
		if(gcd.equals("LOD517"))
		  return true;
		if(gcd.equals("LOD517"))
		  return true;
		if(gcd.equals("LOD518"))
		  return true;
		if(gcd.equals("LOD518"))
		  return true;
		if(gcd.equals("LOD519"))
		  return true;
		if(gcd.equals("LOD520"))
		  return true;
		if(gcd.equals("LOD521"))
		  return true;
		if(gcd.equals("LOD522"))
		  return true;
		if(gcd.equals("LOD523"))
		  return true;
		if(gcd.equals("LOD524"))
		  return true;
		if(gcd.equals("LOD525"))
		  return true;
		if(gcd.equals("LOD526"))
		  return true;
		if(gcd.equals("LOD528"))
		  return true;
		if(gcd.equals("LOD528"))
		  return true;
		if(gcd.equals("LOD529"))
		  return true;
		if(gcd.equals("LOD530"))
		  return true;
		if(gcd.equals("LOD531"))
		  return true;
		if(gcd.equals("LOD532"))
		  return true;
		if(gcd.equals("LOD532"))
		  return true;
		if(gcd.equals("LOD533"))
		  return true;
		if(gcd.equals("LOD534"))
		  return true;
		if(gcd.equals("LOD535"))
		  return true;
		if(gcd.equals("LOD536"))
		  return true;
		if(gcd.equals("LOD537"))
		  return true;
		if(gcd.equals("LOD537"))
		  return true;
		if(gcd.equals("LOD538"))
		  return true;
		if(gcd.equals("LOD539"))
		  return true;
		if(gcd.equals("LOD540"))
		  return true;
		if(gcd.equals("LOD541"))
		  return true;
		if(gcd.equals("LOD542"))
		  return true;
		if(gcd.equals("LOD543"))
		  return true;
		if(gcd.equals("LOD544"))
		  return true;
		if(gcd.equals("LOD545"))
		  return true;
		if(gcd.equals("LOD546"))
		  return true;
		if(gcd.equals("LOD547"))
		  return true;
		if(gcd.equals("LOD548"))
		  return true;
		if(gcd.equals("LOD549"))
		  return true;
		if(gcd.equals("LOD550"))
		  return true;
		if(gcd.equals("LOD551"))
		  return true;
		if(gcd.equals("LOD552"))
		  return true;
		if(gcd.equals("LOD553"))
		  return true;
		if(gcd.equals("LOD554"))
		  return true;
		if(gcd.equals("LOD555"))
		  return true;
		if(gcd.equals("LOD556"))
		  return true;
		if(gcd.equals("LOD557"))
		  return true;
		if(gcd.equals("LOD558"))
		  return true;
		if(gcd.equals("LOD559"))
		  return true;
		if(gcd.equals("LOD560"))
		  return true;
		if(gcd.equals("LOD561"))
		  return true;
		if(gcd.equals("LOD562"))
		  return true;
		if(gcd.equals("LOD563"))
		  return true;
		if(gcd.equals("LOD564"))
		  return true;
		if(gcd.equals("LOD565"))
		  return true;
		if(gcd.equals("LOD567"))
		  return true;
		if(gcd.equals("LOE101"))
		  return true;
		if(gcd.equals("LOE116"))
		  return true;
		if(gcd.equals("LOE134"))
		  return true;
		if(gcd.equals("LOE152"))
		  return true;
		if(gcd.equals("LOE153"))
		  return true;
		if(gcd.equals("LOE154"))
		  return true;
		if(gcd.equals("LOE155"))
		  return true;
		if(gcd.equals("LOE181"))
		  return true;
		if(gcd.equals("LOE182"))
		  return true;
		if(gcd.equals("LOE183"))
		  return true;
		if(gcd.equals("LOF101"))
		  return true;
		if(gcd.equals("LOF102"))
		  return true;
		if(gcd.equals("LOF102"))
		  return true;
		if(gcd.equals("LOF102"))
		  return true;
		if(gcd.equals("LOF102"))
		  return true;
		if(gcd.equals("LOF102"))
		  return true;
		if(gcd.equals("LOF103"))
		  return true;
		if(gcd.equals("LOF104"))
		  return true;
		if(gcd.equals("LOF104"))
		  return true;
		if(gcd.equals("LOF105"))
		  return true;
		if(gcd.equals("LOF106"))
		  return true;
		if(gcd.equals("LOF107"))
		  return true;
		if(gcd.equals("LOF108"))
		  return true;
		if(gcd.equals("LOF109"))
		  return true;
		if(gcd.equals("LOF109"))
		  return true;
		if(gcd.equals("LOF110"))
		  return true;
		if(gcd.equals("LOF111"))
		  return true;
		if(gcd.equals("LOF111"))
		  return true;
		if(gcd.equals("LOF112"))
		  return true;
		if(gcd.equals("LOF112"))
		  return true;
		if(gcd.equals("LOF113"))
		  return true;
		if(gcd.equals("LOF114"))
		  return true;
		if(gcd.equals("LOG102"))
		  return true;
		if(gcd.equals("LOG102"))
		  return true;
		if(gcd.equals("LOG112"))
		  return true;
		if(gcd.equals("LOG113"))
		  return true;
		if(gcd.equals("LOG114"))
		  return true;
		if(gcd.equals("LOG114"))
		  return true;
		if(gcd.equals("LOG115"))
		  return true;
		if(gcd.equals("LOG119"))
		  return true;
		if(gcd.equals("LOH103"))
		  return true;
		if(gcd.equals("LOI101"))
		  return true;
		if(gcd.equals("LOI102"))
		  return true;
		if(gcd.equals("LOI103"))
		  return true;
		if(gcd.equals("LOI104"))
		  return true;
		if(gcd.equals("LOI105"))
		  return true;
		if(gcd.equals("LOI106"))
		  return true;
		if(gcd.equals("LOI107"))
		  return true;
		if(gcd.equals("LOI109"))
		  return true;
		if(gcd.equals("LOI109"))
		  return true;
		if(gcd.equals("LOI110"))
		  return true;
		if(gcd.equals("LOI110"))
		  return true;
		if(gcd.equals("LOI111"))
		  return true;
		if(gcd.equals("LOI111"))
		  return true;
		if(gcd.equals("LOI113"))
		  return true;
		if(gcd.equals("LOI113"))
		  return true;
		if(gcd.equals("LOI114"))
		  return true;
		if(gcd.equals("LOI114"))
		  return true;
		if(gcd.equals("LOI117"))
		  return true;
		if(gcd.equals("LOI117"))
		  return true;
		if(gcd.equals("LOI118"))
		  return true;
		if(gcd.equals("LOI118"))
		  return true;
		if(gcd.equals("LOI119"))
		  return true;
		if(gcd.equals("LOI119"))
		  return true;
		if(gcd.equals("LOI120"))
		  return true;
		if(gcd.equals("LOI120"))
		  return true;
		if(gcd.equals("LOI121"))
		  return true;
		if(gcd.equals("LOI121"))
		  return true;
		if(gcd.equals("LOI122"))
		  return true;
		if(gcd.equals("LOI122"))
		  return true;
		if(gcd.equals("LOI122"))
		  return true;
		if(gcd.equals("LOI123"))
		  return true;
		if(gcd.equals("LOI123"))
		  return true;
		if(gcd.equals("LOI123"))
		  return true;
		if(gcd.equals("LOI124"))
		  return true;
		if(gcd.equals("LOI124"))
		  return true;
		if(gcd.equals("LOI125"))
		  return true;
		if(gcd.equals("LOI125"))
		  return true;
		if(gcd.equals("LOI126"))
		  return true;
		if(gcd.equals("LOI126"))
		  return true;
		if(gcd.equals("LOI127"))
		  return true;
		if(gcd.equals("LOI127"))
		  return true;
		if(gcd.equals("LOI128"))
		  return true;
		if(gcd.equals("LOI129"))
		  return true;
		if(gcd.equals("LOI129"))
		  return true;
		if(gcd.equals("LOI130"))
		  return true;
		if(gcd.equals("LOI130"))
		  return true;
		if(gcd.equals("LOI131"))
		  return true;
		if(gcd.equals("LOI131"))
		  return true;
		if(gcd.equals("LOI132"))
		  return true;
		if(gcd.equals("LOI132"))
		  return true;
		if(gcd.equals("LOI133"))
		  return true;
		if(gcd.equals("LOI133"))
		  return true;
		if(gcd.equals("LOI134"))
		  return true;
		if(gcd.equals("LOI134"))
		  return true;
		if(gcd.equals("LOI135"))
		  return true;
		if(gcd.equals("LOI135"))
		  return true;
		if(gcd.equals("LOI136"))
		  return true;
		if(gcd.equals("LOI136"))
		  return true;
		if(gcd.equals("LOI137"))
		  return true;
		if(gcd.equals("LOI137"))
		  return true;
		if(gcd.equals("LOI138"))
		  return true;
		if(gcd.equals("LOI138"))
		  return true;
		if(gcd.equals("LOI139"))
		  return true;
		if(gcd.equals("LOI139"))
		  return true;
		if(gcd.equals("LOI149"))
		  return true;
		if(gcd.equals("LOI150"))
		  return true;
		if(gcd.equals("LOI203"))
		  return true;
		if(gcd.equals("LOI304"))
		  return true;
		if(gcd.equals("LOI305"))
		  return true;
		if(gcd.equals("LOI333"))
		  return true;
		if(gcd.equals("LOI334"))
		  return true;
		if(gcd.equals("LOI418"))
		  return true;
		if(gcd.equals("LOI418"))
		  return true;
		if(gcd.equals("LOI428"))
		  return true;
		if(gcd.equals("LOI428"))
		  return true;
		if(gcd.equals("LOI429"))
		  return true;
		if(gcd.equals("LOI429"))
		  return true;
		if(gcd.equals("LOJ101"))
		  return true;
		if(gcd.equals("LOM102"))
		  return true;
		if(gcd.equals("LOM103"))
		  return true;
		if(gcd.equals("LOM103"))
		  return true;
		if(gcd.equals("LOM106"))
		  return true;
		if(gcd.equals("LOM106"))
		  return true;
		if(gcd.equals("LOM106"))
		  return true;
		if(gcd.equals("LOM106"))
		  return true;
		if(gcd.equals("LOM106"))
		  return true;
		if(gcd.equals("LOP123"))
		  return true;
		if(gcd.equals("LOP126"))
		  return true;
		if(gcd.equals("LOS129"))
		  return true;
		if(gcd.equals("LOS138"))
		  return true;
		if(gcd.equals("LOS152"))
		  return true;
		if(gcd.equals("LOS152"))
		  return true;
		if(gcd.equals("LOT101"))
		  return true;
		if(gcd.equals("LOV101"))
		  return true;
		if(gcd.equals("LOV102"))
		  return true;
		if(gcd.equals("LOY138"))
		  return true;
		if(gcd.equals("LOY138"))
		  return true;
		if(gcd.equals("LOY139"))
		  return true;
		if(gcd.equals("LOY140"))
		  return true;
		if(gcd.equals("LOY141"))
		  return true;
		if(gcd.equals("LOE149"))
			  return true;
		if(gcd.equals("LOS154"))
			  return true;

	return false;

}
public void makeDownloadFile() {
	row = 1;
	row2 = 1;
	try {   
		if (!debug) {
			workbook = Workbook.createWorkbook(new File(savedir + makeOutFile()));
			wbresult = workbook.createSheet("Result", 0);   
			String ArraryResult[] = null;
//			label = new jxl.write.Label(0, 0,
//			"(재)씨젠의료재단   첫번재 ,두번째 Row - 여유 레코드 입니다.항시 결과는 3번째 Row 부터 입니다");
//	wbresult.addCell(label);
			ArraryResult = (new String[] {"수진자명", "챠트번호", "주민번호", "성별", "검체코드", "과명", "병동", "담당의사", "의뢰일자", "검체번호", //
			"처방코드", "처방명", "결과구분", "검사결과", "보고일자", "결과입력시간", "리마크", "결과입력자", "작업일자", "이미지유무", //
			"이미지경로", "최저치", "최고치", "참고치", "H/L", "단위", "소요일"});
			for (int i = 0; i < ArraryResult.length; i++) {   
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
		boolean isImage = false; //이미지 결과지 여부
		boolean isHosImage =false;// 프로파일 결과지 여부
		//!
		String hosCode[] = (String[]) getDownloadData().get("병원코드");
		String rcvDate[] = (String[]) getDownloadData().get("접수일자");
		String rcvNo[] = (String[]) getDownloadData().get("접수번호");
		String specNo[] = (String[]) getDownloadData().get("검체번호");
		String chartNo[] = (String[]) getDownloadData().get("차트번호");

		//!
		String patName[] = (String[]) getDownloadData().get("수신자명");
		String inspectCode[] = (String[]) getDownloadData().get("검사코드");
		String inspectName[] = (String[]) getDownloadData().get("검사명");
		String seq[] = (String[]) getDownloadData().get("일련번호");
		String result[] = (String[]) getDownloadData().get("결과");

		//!
		String resultType[] = (String[]) getDownloadData().get("결과타입");
		String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
		String sex[] = (String[]) getDownloadData().get("성별");
		String age[] = (String[]) getDownloadData().get("나이");
		String securityNo[] = (String[]) getDownloadData().get("주민번호");

		//!
		String highLow[] = (String[]) getDownloadData().get("결과상태");
		String lang[] = (String[]) getDownloadData().get("언어");
		String history[] = (String[]) getDownloadData().get("이력");
		String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
		String cns[] = (String[]) getDownloadData().get("처방번호");

		//!
		String bdt[] = (String[]) getDownloadData().get("검사완료일");
//		String bgcd[] = (String[]) getDownloadData().get("보험코드");

		//!
//		String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
//		String img[] = (String[]) getDownloadData().get("이미지여부"); //내원번호
		String unit[] = (String[]) getDownloadData().get("참고치단위등");
		String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");
//		String inc[] = (String[]) getDownloadData().get("외래구분");

		//!
		//ArraryResult = (new String[] {"수진자명", "챠트번호", "주민번호", "성별", "검체코드", "과명", "병동", "담당의사", "의뢰일자", "검체번호", //
		//"처방코드", "처방명", "결과구분", "검사결과", "보고일자", "결과입력시간", "리마크", "결과입력자", "작업일자", "이미지유무", //
		//"이미지경로", "최저치", "최고치", "참고치", "H/L", "단위", "소요일"});

		//	data[0]= "수진자명", A
		//	data[1]= "챠트번호",B
		//	data[2]=  "주민번호",C 
		//	data[3]= "성별", D
		//	data[4]= "검체코드",E 
		//	data[5]= "과명",F
		//	data[6]=  "병동",G
		//	data[7]=  "담당의사",H 
		//	data[8]= "의뢰일자",I
		//	data[9]=  "검체번호",J 
		//	data[10]= "처방코드",K
		//	data[11]= "처방명",L
		//	data[12]= "결과구분",M
		//	data[13]= "검사결과",N
		//	data[14]= "보고일자",O
		//	data[15]= "결과입력시간",P 
		//	data[16]= "리마크", Q
		//	data[17]= "결과입력자",R
		//	data[18]= "작업일자",S
		//	data[19]= "이미지유무",T 
		//	data[20]= "이미지경로", U
		//	data[21]= "최저치", V
		//	data[22]= "최고치", W
		//	data[23]= "참고치", X
		//	data[24]= "H/L",Y
		//	data[25]= "단위",Z
		//	data[26]= "소요일" 0 });

		//!


		String data[] = new String[27];  
//		String[] _tmp = new String[3];
		String remark[] = new String[5];
		String remarkCode = "";

   
		//!
		String lastData = "";
		int lastindex = 0;
		if (cnt == 400 && inspectCode[399].trim().length() == 7) {
			lastData = inspectCode[399].trim().substring(0, 5);
			lastindex = 399;
			isNext = true;

			//!
			while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
				cnt--;
				if (inspectCode[lastindex].trim().substring(5).equals("00")) {
					cnt--;
				}
			}
		}

		//!
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


			//!
			data[0] = patName[i]; // "수진자명", A
			data[1] = chartNo[i]; // "챠트번호",B
			data[2] = securityNo[i]; //  "주민번호",C 
			data[3] = sex[i]; // "성별", D
			data[4] = hosSamp[i]; // "검체코드",E 
			data[5] = ""; // "과명",F
			data[6] = ""; //  "병동",G
			data[7] = ""; //  "담당의사",H
			try {
				data[8] = cutHrcvDateNumber2(cns[i])[1]; //처방일자 "의뢰일자",I			
			} catch (Exception e) {
				data[8] = cns[i];
			}
			data[9] = specNo[i].trim(); // "검체번호",J 
			data[10] = clientInspectCode[i].trim(); //"처방코드",K
			data[11] = inspectName[i]; //"처방명",L
			data[12] = ""; //"결과구분",M
			data[13] = result[i]; //"검사결과",N
			data[14] = bdt[i]; //"보고일자",O
			data[15] = ""; // "결과입력시간",P 
			data[16] = ""; //"리마크", Q
			data[17] = ""; //"결과입력자",R
			data[18] = ""; //"작업일자",S
			data[19] = ""; //"이미지유무",T 
			data[20] = ""; //"이미지경로", U
			data[21] = ""; //"최저치", V
			data[22] = ""; //"최고치", W
			data[23] = ""; //"참고치", X
			data[24] = highLow[i].replace('.', ' ').trim(); //"H/L",Y
			data[25] = ""; //"단위",Z
			data[26] = ""; //"소요일" 

			//!본원검사코드로 체크
			isImage = isImage(inspectCode[i]);
			
			//!병원검사코드로 체크 (프로파일 코드로 체크)
			if(!isImage){
			isHosImage = isHosImage(clientInspectCode[i]);	
//			isImage=isHosImage;		
			}
			
			//! 이미지 일때
			if (isImage) {
				if (inspectCode[i].trim().length() == 7) {
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
						if (++i == cnt) {
							break;
						}
					}
					i--;
				}    
				data[12] = "IMAGE_FORM"; //"결과구분",M
				data[13] = "별지참조"; //"검사결과",N
				data[19] = "Y"; //"이미지유무",T 
				data[20] = ""; //"이미지경로", U

			}
			
			//! 병원 프로파일 일
			if (isHosImage) {     
//				if (inspectCode[i].trim().length() == 7) {   
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = clientInspectCode[i++].trim(); thisTimeCode.equals(clientInspectCode[i].trim()) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
						if (++i == cnt) {
							break;
						}
					}
					i--;  
//				}
				data[12] = "IMAGE_FORM"; //"결과구분",M
				data[13] = "별지참조"; //"검사결과",N
				data[19] = "Y"; //"이미지유무",T 
				data[20] = ""; //"이미지경로", U

			}
			
			

			//! 이미지가 아니고 -문자형태
			if (!isImage && resultType[i].trim().equals("C")) {      
				data[13] = result[i]; //결과
				remark[0] = inspectCode[i];
				remark[1] = lang[i];
				remark[2] = history[i];  
				remark[3] = sex[i];
				remark[4] = age[i];
				
				data[23] = getReferenceValue(remark);//참고치 수정 작업 20151130 cdy
				
				if (inspectCode[i].trim().length() == 7) {
					data[13] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t" + "참    고    치";
					data[13] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" + data[15];
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					data[12] = "TEXT_FORM"; //"결과구분",M
					data[19] = ""; //"이미지유무",T 
					data[20] = ""; //"이미지경로", U
					
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
						data[13] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t" +getReferenceValue(new String[] {inspectCode[i], lang[i], history[i], sex[i]}).trim() ;
						if (++i == cnt) {
							break;
						}
					}
					i--;
				}
				try {
					data[21] = getUintCut(unit[i])[0]; //최저치
				} catch (Exception exx) {
					data[21] = "";
				}
				try {
					data[22] = getUintCut(unit[i])[1]; //최고치
				} catch (Exception exx) {
					data[22] = "";
				}
				try {
					data[25] = getUintCut(unit[i])[2]; //단위
				} catch (Exception exx) {
					data[25] = "";
				}
			}

			//! Negative or Positive 이상결과여부 확인
			if (data[24] == null || data[24].toString().trim().equals("") || data[24].toString().trim().equals(".")) {   
				if (data[23].toString().trim().toLowerCase().indexOf("negative") > -1) {
					if (data[13].toString().trim().toLowerCase().indexOf("positive") > -1) {    
						data[24] = "*";
					}
				}
			}

			//! 이미지가 아니고 -문장형태
			if (!isImage && !resultType[i].trim().equals("C")) {
				data[12] = "TEXT_FORM"; //"결과구분",M
				data[19] = ""; //"이미지유무",T 
				data[20] = ""; //"이미지경로", U
				data[13] = getTextResultValue(hosCode[i], rcvDate[i], rcvNo[i], inspectCode[i]); //검사결과
			}

			//! 리마크
			if (rmkCode[i].trim().length() > 0) {
				try {
					if (!kumdata[0].trim().equals(rcvDate[i]) || !kumdata[1].trim().equals(rcvNo[i]) || !kumdata[2].trim().equals(remarkCode)) {
						remarkCode = rmkCode[i].trim();
						if (inspectCode[i].trim().substring(0, 5).equals("11026") || inspectCode[i].trim().substring(0, 5).equals("11052")) {
							data[13] = data[13] + "\r\n" + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]); //검사결과
						} else {
							data[16] = getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]).replaceAll("\\r\\n", " "); //리마크 줄바꿈 제거요청-0605
						}
						kumdata[0] = rcvDate[i].trim();
						kumdata[1] = rcvNo[i].trim();
						kumdata[2] = remarkCode;
					}
				} catch (Exception _ex) {
				}
			} else {
				remarkCode = "";
			}
			if (!debug) {
				for (int k = 0; k < data.length; k++) {
					label = new jxl.write.Label(k, row, data[k]);
					wbresult.addCell(label);
				}
			} else {
				for (int k = 0; k < data.length; k++) {
//					System.out.print(rcvDate[i] + "  " + rcvNo[i] + "  " + inspectCode[i] + "  " + inspectName[i] + " " + data[k]);
				}
			}
			row++;
		}
		if (cnt == 400 || isNext) {
			setParameters(new String[] {hosCode[cnt - 1], rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1], seq[cnt - 1]});
		} else {
			setParameters(null);
		}
	} catch (Exception _ex) {
		setParameters(null);
	}
}
}

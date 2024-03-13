package com.neodin.files;

import java.util.Hashtable;

public class DLDataModel {
	private Hashtable downloadData;
	String hosCode[]; //"병원코드"
	String rcvDate[]; //"접수일자"
	String rcvNo[]; //"접수번호"
	String specNo[]; //"검체번호"
	String chartNo[];//"차트번호"

	// !
	String patName[]; // "수신자명"
	String inspectCode[]; // "검사코드"
	String inspectName[];// "검사명"
	String seq[]; // "일련번호"
	String result[] ;//"결과"

	// !
	String resultType[];// "결과타입"
	String clientInspectCode[];// "병원검사코드"
	String sex[];//"성별"
	String age[];//"나이"
	String securityNo[];// "주민번호"

	// !
	String highLow[];// "결과상태"
	String lang[];// "언어"
	String history[];// "이력"
	String rmkCode[];// "리마크코드"
	String cns[];// "처방번호"

	// !
	String bdt[];// "검사완료일"
	String bgcd[];// "보험코드"

	// !
	String bbseq[];// "요양기관번호"
	String img[];// "이미지여부" // 내원번호
	String unit[]; //"참고치단위등"
	String hosSamp[];// "병원검체코드"
	String inc[]; // "외래구분"
	public String[] getInc() {
		return inc;
	}
	public void setInc(String[] inc) {
		this.inc = inc;
	}
	public String getInc(int i) {
		return inc[i];
	}
	public void setInc(String inc, int i) {
		this.inc[i] = inc;
	}
	
	public String[] getHosCode() {
		return hosCode;
	}
	public void setHosCode(String[] hosCode) {
		this.hosCode = hosCode;
	}
	
	public String getHosCode(int i) {
		return hosCode[i];
	}
	public void setHosCode(String hosCode,int i) {
		this.hosCode[i] = hosCode;
	}
	
	public String[] getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(String[] rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getRcvDate(int i) {
		return rcvDate[i];
	}
	public void setRcvDate(String rcvDate, int i) {
		this.rcvDate[i] = rcvDate;
	}
	public String[] getRcvNo() {
		return rcvNo;
	}
	public void setRcvNo(String[] rcvNo) {
		this.rcvNo = rcvNo;
	}
	public String getRcvNo(int i) {
		return rcvNo[i];
	}
	public void setRcvNo(String rcvNo, int i) {
		this.rcvNo[i] = rcvNo;
	}
	public String[] getSpecNo() {
		return specNo;
	}
	public void setSpecNo(String[] specNo) {
		this.specNo = specNo;
	}
	public String getSpecNo(int i) {
		return specNo[i];
	}
	public void setSpecNo(String specNo, int i) {
		this.specNo[i] = specNo;
	}
	public String[] getChartNo() {
		return chartNo;
	}
	public void setChartNo(String[] chartNo) {
		this.chartNo = chartNo;
	}
	public String getChartNo(int i) {
		return chartNo[i];
	}
	public void setChartNo(String chartNo, int i) {
		this.chartNo[i] = chartNo;
	}
	public String[] getPatName() {
		return patName;
	}
	public void setPatName(String[] patName) {
		this.patName = patName;
	}
	public String getPatName(int i) {
		return patName[i];
	}
	public void setPatName(String patName, int i) {
		this.patName[i] = patName;
	}
	public String[] getInspectCode() {
		return inspectCode;
	}
	public void setInspectCode(String[] inspectCode) {
		this.inspectCode = inspectCode;
	}
	public String getInspectCode(int i) {
		return inspectCode[i];
	}
	public void setInspectCode(String inspectCode, int i) {
		this.inspectCode[i] = inspectCode;
	}
	public String[] getInspectName() {
		return inspectName;
	}
	public void setInspectName(String[] inspectName) {
		this.inspectName = inspectName;
	}
	public String getInspectName(int i) {
		return inspectName[i];
	}
	public void setInspectName(String inspectName, int i) {
		this.inspectName[i] = inspectName;
	}
	public String[] getSeq() {
		return seq;
	}
	public void setSeq(String[] seq) {
		this.seq = seq;
	}
	public String getSeq(int i) {
		return seq[i];
	}
	public void setSeq(String seq, int i) {
		this.seq[i] = seq;
	}
	public String[] getResult() {
		return result;
	}
	public void setResult(String[] result) {
		this.result = result;
	}
	public String getResult(int i) {
		return result[i];
	}
	public void setResult(String result, int i) {
		this.result[i] = result;
	}
	public String[] getResultType() {
		return resultType;
	}
	public void setResultType(String[] resultType) {
		this.resultType = resultType;
	}
	public String getResultType(int i) {
		return resultType[i];
	}
	public void setResultType(String resultType, int i) {
		this.resultType[i] = resultType;
	}
	public String[] getClientInspectCode() {
		return clientInspectCode;
	}
	public void setClientInspectCode(String[] clientInspectCode) {
		this.clientInspectCode = clientInspectCode;
	}
	public String getClientInspectCode(int i) {
		return clientInspectCode[i];
	}
	public void setClientInspectCode(String clientInspectCode, int i) {
		this.clientInspectCode[i] = clientInspectCode;
	}
	public String[] getSex() {
		return sex;
	}
	public void setSex(String[] sex) {
		this.sex = sex;
	}
	public String getSex(int i) {
		return sex[i];
	}
	public void setSex(String sex, int i) {
		this.sex[i] = sex;
	}
	public String[] getAge() {
		return age;
	}
	public void setAge(String[] age) {
		this.age = age;
	}
	
	public String getAge(int i) {
		return age[i];
	}
	public void setAge(String age, int i) {
		this.age[i] = age;
	}
	public String[] getSecurityNo() {
		return securityNo;
	}
	public void setSecurityNo(String[] securityNo) {
		this.securityNo = securityNo;
	}
	public String getSecurityNo(int i) {
		return securityNo[i];
	}
	public void setSecurityNo(String securityNo, int i) {
		this.securityNo[i] = securityNo;
	}
	public String[] getHighLow() {
		return highLow;
	}
	public void setHighLow(String[] highLow) {
		this.highLow = highLow;
	}
	public String getHighLow(int i) {
		return highLow[i];
	}
	public void setHighLow(String highLow, int i) {
		this.highLow[i] = highLow;
	}
	public String[] getLang() {
		return lang;
	}
	public void setLang(String[] lang) {
		this.lang = lang;
	}
	public String getLang(int i) {
		return lang[i];
	}
	public void setLang(String lang, int i) {
		this.lang[i] = lang;
	}
	public String[] getHistory() {
		return history;
	}
	public void setHistory(String[] history) {
		this.history = history;
	}
	public String getHistory(int i) {
		return history[i];
	}
	public void setHistory(String history, int i) {
		this.history[i] = history;
	}
	public String[] getRmkCode() {
		return rmkCode;
	}
	public void setRmkCode(String[] rmkCode) {
		this.rmkCode = rmkCode;
	}
	public String getRmkCode(int i) {
		return rmkCode[i];
	}
	public void setRmkCode(String rmkCode, int i) {
		this.rmkCode[i] = rmkCode;
	}
	public String[] getCns() {
		return cns;
	}
	public void setCns(String[] cns) {
		this.cns = cns;
	}
	public String getCns(int i) {
		return cns[i];
	}
	public void setCns(String cns, int i) {
		this.cns[i] = cns;
	}
	public String[] getBdt() {
		return bdt;
	}
	public void setBdt(String[] bdt) {
		this.bdt = bdt;
	}
	public String getBdt(int i) {
		return bdt[i];
	}
	public void setBdt(String bdt, int i) {
		this.bdt[i] = bdt;
	}
	public String[] getBgcd() {
		return bgcd;
	}
	public void setBgcd(String[] bgcd) {
		this.bgcd = bgcd;
	}
	public String getBgcd(int i) {
		return bgcd[i];
	}
	public void setBgcd(String bgcd, int i) {
		this.bgcd[i] = bgcd;
	}
	public String[] getBbseq() {
		return bbseq;
	}
	public void setBbseq(String[] bbseq) {
		this.bbseq = bbseq;
	}
	public String getBbseq(int i) {
		return bbseq[i];
	}
	public void setBbseq(String bbseq, int i) {
		this.bbseq[i] = bbseq;
	}
	public String[] getImg() {
		return img;
	}
	public void setImg(String[] img) {
		this.img = img;
	}
	public String getImg(int i) {
		return img[i];
	}
	public void setImg(String img, int i) {
		this.img[i] = img;
	}
	public String[] getUnit() {
		return unit;
	}
	public void setUnit(String[] unit) {
		this.unit = unit;
	}
	public String getUnit(int i) {
		return unit[i];
	}
	public void setUnit(String unit, int i) {
		this.unit[i] = unit;
	}
	public String[] getHosSamp() {
		return hosSamp;
	}
	public void setHosSamp(String[] hosSamp) {
		this.hosSamp = hosSamp;
	}
	public String getHosSamp(int i) {
		return hosSamp[i];
	}
	public void setHosSamp(String hosSamp, int i) {
		this.hosSamp[i] = hosSamp;
	}
	public void setDownloadData(Hashtable downloadData) {
		this.downloadData = downloadData;
	}
	
	
	public DLDataModel(Hashtable downloadData) {
		this.downloadData = downloadData;
		initialize();
	}
	private void initialize() {
		// TODO Auto-generated method stub
		// !
		setHosCode((String[]) getDownloadData().get("병원코드"));
		setRcvDate((String[]) getDownloadData().get("접수일자"));
		setRcvNo((String[]) getDownloadData().get("접수번호"));
		setSpecNo((String[]) getDownloadData().get("검체번호"));
		setChartNo((String[]) getDownloadData().get("차트번호"));

		// !
		setPatName((String[]) getDownloadData().get("수신자명"));
		setInspectCode((String[]) getDownloadData().get("검사코드"));
		setInspectName((String[]) getDownloadData().get("검사명"));
		setSeq((String[]) getDownloadData().get("일련번호"));
		setResult((String[]) getDownloadData().get("결과"));

		// !
		setResultType((String[]) getDownloadData().get("결과타입"));
		setClientInspectCode((String[]) getDownloadData().get("병원검사코드"));
		setSex((String[]) getDownloadData().get("성별"));
		setAge((String[]) getDownloadData().get("나이"));
		setSecurityNo((String[]) getDownloadData().get("주민번호"));

		// !
		setHighLow((String[]) getDownloadData().get("결과상태"));
		setLang((String[]) getDownloadData().get("언어"));
		setHistory((String[]) getDownloadData().get("이력"));
		setRmkCode((String[]) getDownloadData().get("리마크코드"));
		setCns((String[]) getDownloadData().get("처방번호"));

		// !
		setBdt((String[]) getDownloadData().get("검사완료일"));
		setBgcd((String[]) getDownloadData().get("보험코드"));

		// !
		setBbseq((String[]) getDownloadData().get("요양기관번호"));
		setImg((String[]) getDownloadData().get("이미지여부")); // 내원번호
		setUnit((String[]) getDownloadData().get("참고치단위등"));
		setHosSamp((String[]) getDownloadData().get("병원검체코드"));

		// !
		setInc((String[]) getDownloadData().get("외래구분"));
	}
	
	private Hashtable getDownloadData() {
		return this.downloadData;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

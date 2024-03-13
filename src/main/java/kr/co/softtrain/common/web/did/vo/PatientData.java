package kr.co.softtrain.common.web.did.vo;

/**
 * <pre>
 * kr.co.softtrain.common.web.did.vo
 * PatientData.java
 * </pre>
 * @title :  수진자 개인정보 조회결과
 * @author : kwc
 * @since : 2021. 08. 17.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2021. 08. 17.		kwc   			최초생성
 * 
 * </pre>
 */
public class PatientData {

    private String receiptDate;    //접수일자
	private String receiptNo;      //접수번호
	private String hospitalCode;   //병원코드
	private String reportDate;     //보고일자
	private String userName;       //환자명
    private String userSex;        //성별
 	private String userBirthday;   //생년월일
 	private String userNationCode; //국가구분코드
	private String userPhoneNo;    //전화번호
	private String personalNo1;    //주민번호1
	private double matchingValue;  //matching value
	private String comment;        //comment
	
	//getter -------------------------------------
	public String getReceiptDate() {
		return receiptDate;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public String getReportDate() {
		return reportDate;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public String getUserNationCode() {
		return userNationCode;
	}
	public String getUserPhoneNo() {
		return userPhoneNo;
	}
	public String getPersonalNo1() {
		return personalNo1;
	}
	public double getMatchingValue() {
		return matchingValue;
	}
	public String getComment() {
		return comment;
	}
	
	//setter -------------------------------------
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public void setUserNationCode(String userNationCode) {
		this.userNationCode = userNationCode;
	}
	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}
	public void setPersonalNo1(String personalNo1) {
		this.personalNo1 = personalNo1;
	}
	public void setMatchingValue(double matchingValue) {
		this.matchingValue = matchingValue;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//c'tor -------------------------------------------
	public PatientData(String receiptDate, String receiptNo, String hospitalCode, String reportDate, String userName,
			String userSex, String userBirthday, String userNationCode, String userPhoneNo, String personalNo1, double matchingValue, String comment) {
		super();
		this.receiptDate = receiptDate;
		this.receiptNo = receiptNo;
		this.hospitalCode = hospitalCode;
		this.reportDate = reportDate;
		this.userName = userName;
		this.userSex = userSex;
		this.userBirthday = userBirthday;
		this.userNationCode = userNationCode;
		this.userPhoneNo = userPhoneNo;
		this.personalNo1 = personalNo1;
		this.matchingValue = matchingValue;
		this.comment = comment;
	}
	public PatientData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}

package kr.co.softtrain.common.web.did.vo;

/**
 * <pre>
 * kr.co.softtrain.common.web.did.vo
 * RequestUserData.java
 * </pre>
 * @title :  개인정보기반 검사결과조회 Request
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
public class RequestUserData 
{

	private String medicalCode;    // 수탁검사기관 코드
	private String hospitalCode;   // 검사 의뢰 기관 코드
	private String receiptId;      // 접수번호
	private String issueDate;      // 발급일
	private String issueDateFrom;  // 발급일From
	private String issueDateTo;    // 발급일To
	private String did;            // 피검사자 did
	private String vcid;           // 피검사자 신원확인용 VC ID
	private String testType;       // 검사 항목
	private String userName;       // 피검사자 이름
	private String userPhoneno;    // 전화번호 
	private String userBirthday;   // 생년월일
	private String userSex;        // 성별
	private String language;       //요청언어
	
	//getter ----------------------------------
	public String getMedicalCode() {
		return medicalCode;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public String getIssueDateFrom() {
		return issueDateFrom;
	}
	public String getIssueDateTo() {
		return issueDateTo;
	}
	public String getDid() {
		return did;
	}
	public String getVcid() {
		return vcid;
	}
	public String getTestType() {
		return testType;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPhoneno() {
		return userPhoneno;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public String getUserSex() {
		return userSex;
	}
	public String getLanguage() {
		return language;
	}
	
	//setter ----------------------------------
	public void setMedicalCode(String medicalCode) {
		this.medicalCode = medicalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public void setIssueDateFrom(String issueDateFrom) {
		this.issueDateFrom = issueDateFrom;
	}
	public void setIssueDateTo(String issueDateTo) {
		this.issueDateTo = issueDateTo;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public void setVcid(String vcid) {
		this.vcid = vcid;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserPhoneno(String userPhoneno) {
		this.userPhoneno = userPhoneno;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	//c'tor --------------------------------
	public RequestUserData(String medicalCode, String hospitalCode, String receiptId, String issueDate, String issueDateFrom, String issueDateTo, String did,
			String vcid, String testType, String userName, String userPhoneno, String userBirthday, String userSex,
			String language) {
		super();
		this.medicalCode = medicalCode;
		this.hospitalCode = hospitalCode;
		this.receiptId = receiptId;
		this.issueDate = issueDate;
		this.issueDateFrom = issueDateFrom;
		this.issueDateTo = issueDateTo;
		this.did = did;
		this.vcid = vcid;
		this.testType = testType;
		this.userName = userName;
		this.userPhoneno = userPhoneno;
		this.userBirthday = userBirthday;
		this.userSex = userSex;
		this.language = language;
	}
	
	public RequestUserData() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}

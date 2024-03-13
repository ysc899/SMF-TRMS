package kr.co.softtrain.common.web.did.vo;

/**
 * <pre>
 * kr.co.softtrain.common.web.did.vo
 * TestResultData.java
 * </pre>
 * @title :  개인정보기반 검사결과조회 Response
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
public class TestResultData {

	private String testResultID;    //결과id
	private String hospitalCode;    //검사의뢰기관 코드
	private String hospitalDID;    	//검사의뢰기관 DID 
	private String hospitalVCID;    //검사의뢰기관 VC ID
	private String hospitalName;    //검사의뢰기관명
	private int    receiptId;       //접수번호 
	private String testType;        //검사항목
	private String testCode;        //검사코드
	private String specimenCode;    //검체코드
	private String did;             //피검사자 did
	private String vcid;            //피검사자 신원확인용 VC ID
	private String userName;        //피검사자 이름
	private String testDate;        //검사일시
	private int receiptDate;        //접수일
	private String testName;        //검사명
	private String testMethod;      //검사방법
	private String testResult;      //검사결과
	private String issueDate;       //검사 결과 발행 일시
	private String medicalCode;      //검사기관 코드 
	private String medicalDID;       //검사기관 DID
	private String medicalVCID;      //검사기관 VCID
	private String medicalName;     //검사기관명
	
	//getter ------------------------------------
	public String getTestResultID() {
		return testResultID;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public String getHospitalDID() {
		return hospitalDID;
	}
	public String getHospitalVCID() {
		return hospitalVCID;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public int getReceiptId() {
		return receiptId;
	}
	public String getTestType() {
		return testType;
	}
	public String getTestCode() {
		return testCode;
	}
	public String getSpecimenCode() {
		return specimenCode;
	}
	public String getDid() {
		return did;
	}
	public String getVcid() {
		return vcid;
	}
	public String getUserName() {
		return userName;
	}
	public String getTestDate() {
		return testDate;
	}
	public int getReceiptDate() {
		return receiptDate;
	}
	public String getTestName() {
		return testName;
	}
	public String getTestMethod() {
		return testMethod;
	}
	public String getTestResult() {
		return testResult;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public String getMedicalCode() {
		return medicalCode;
	}
	public String getMedicalDID() {
		return medicalDID;
	}
	public String getMedicalVCID() {
		return medicalVCID;
	}
	public String getMedicalName() {
		return medicalName;
	}
	
	//setter -----------------------------------------
	public void setTestResultID(String testResultID) {
		this.testResultID = testResultID;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public void setHospitalDID(String hospitalDID) {
		this.hospitalDID = hospitalDID;
	}
	public void setHospitalVCID(String hospitalVCID) {
		this.hospitalVCID = hospitalVCID;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public void setSpecimenCode(String specimenCode) {
		this.specimenCode = specimenCode;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public void setVcid(String vcid) {
		this.vcid = vcid;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public void setReceiptDate(int receiptDate) {
		this.receiptDate = receiptDate;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public void setMedicalCode(String medicalCode) {
		this.medicalCode = medicalCode;
	}
	public void setMedicalDID(String medicalDID) {
		this.medicalDID = medicalDID;
	}
	public void setMedicalVCID(String medicalVCID) {
		this.medicalVCID = medicalVCID;
	}
	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}
	
	//c'tor ---------------------------------------
	public TestResultData(String testResultID, String hospitalCode, String hospitalDID, String hospitalVCID,
			String hospitalName, int receiptId, String testType, String testCode, String specimenCode, String did,
			String vcid, String userName, String testDate, int receiptDate, String testName, String testMethod,
			String testResult, String issueDate, String medicalCode, String medicalDID, String medicalVCID,
			String medicalName) {
		super();
		this.testResultID = testResultID;
		this.hospitalCode = hospitalCode;
		this.hospitalDID = hospitalDID;
		this.hospitalVCID = hospitalVCID;
		this.hospitalName = hospitalName;
		this.receiptId = receiptId;
		this.testType = testType;
		this.testCode = testCode;
		this.specimenCode = specimenCode;
		this.did = did;
		this.vcid = vcid;
		this.userName = userName;
		this.testDate = testDate;
		this.receiptDate = receiptDate;
		this.testName = testName;
		this.testMethod = testMethod;
		this.testResult = testResult;
		this.issueDate = issueDate;
		this.medicalCode = medicalCode;
		this.medicalDID = medicalDID;
		this.medicalVCID = medicalVCID;
		this.medicalName = medicalName;
	}
	public TestResultData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

package kr.co.softtrain.common.web.did.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.did.util.DidConstants;
import kr.co.softtrain.common.web.did.util.DidUtility;
import kr.co.softtrain.common.web.did.vo.PatientData;
import kr.co.softtrain.common.web.did.vo.TestResultData;
import kr.co.softtrain.common.web.did.vo.RequestUserData;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.did.controller
 * DidController.java
 * </pre>
 * @title :  DID
 * @author : kh
 * @since : 2021. 08. 17.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2021. 08. 17.		kh   			최초생성
 * 
 * </pre>
 */
class DescendingPatient implements Comparator<PatientData>
{
	@Override
	public int compare(PatientData lh, PatientData rh)
	{
		//내림차순
		if (rh.getMatchingValue() > lh.getMatchingValue())
			return -1;
		else if (rh.getMatchingValue() > lh.getMatchingValue())
			return 1;
		else
			return 0;
	}
}

@Controller
public class DidController  
{
	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
	
	@RequestMapping(value = "/didIntro.do")
	public String didIntro(HttpSession session){
		return "did/didIntro";
	}
	
	@RequestMapping(value = "/didAgree.do")
	public String didAgree(HttpSession session){
		return "did/didAgree";
	}

	
	@RequestMapping(value = "/requestTestResultForUserInfo.do", method = {RequestMethod.POST})
	public ResponseEntity<Object> requestTestResultForUserInfo(HttpServletRequest request, 
			                                                   HttpSession session,
			                                      @RequestBody RequestUserData requestUserData) throws Exception
	{
		Map<String, Object> responseResult = new HashMap<String, Object>();
		
		try 
		{
			//검사의뢰기관코드 체크
			if (requestUserData.getHospitalCode() == null || requestUserData.getHospitalCode().isEmpty())
			{
				responseResult.put("result", false);
				responseResult.put("testResults", "hospitalCode ERROR[" + requestUserData.getHospitalCode() + "]");
				return ResponseEntity.badRequest().body(responseResult);
			}
			
			//발급일 체크
			boolean isIssueDateRange = false;
			java.util.Date issueDate = null;
			java.util.Date issueDateFrom = null;
			java.util.Date issueDateTo = null;
			issueDate = DidUtility.getDateFromDashFormat(requestUserData.getIssueDate().trim());
			
			if (issueDate == null)
			{
				issueDateFrom = DidUtility.getDateFromDashFormat(requestUserData.getIssueDateFrom().trim());
				issueDateTo = DidUtility.getDateFromDashFormat(requestUserData.getIssueDateTo().trim());
				
				if (issueDateFrom == null || issueDateTo == null)
				{
					responseResult.put("result", false);
					responseResult.put("testResults", "issueDate ERROR, issueDate[" + requestUserData.getIssueDate() +
							         "] issueDateFrom[" + requestUserData.getIssueDateFrom() +
							         "] issueDateTo[" + requestUserData.getIssueDateTo() + "]");
					return ResponseEntity.badRequest().body(responseResult);
				}
				
				isIssueDateRange = true;
				long diffDay = (issueDateTo.getTime() - issueDateFrom.getTime()) / (24*60*60*1000);
								
				if (diffDay > 14)
				{
					responseResult.put("result", false);
					responseResult.put("testResults", "issueDate range ERROR, range is over " + diffDay + " days");
					return ResponseEntity.badRequest().body(responseResult);
				}
			}

			//검사항목 체크
			if (requestUserData.getTestType() == null || requestUserData.getTestType().isEmpty())
			{
				responseResult.put("result", false);
				responseResult.put("testResults", "testType ERROR[" + requestUserData.getTestType() + "]");
				return ResponseEntity.badRequest().body(responseResult);
			}
			
			int smfBranchNo = DidConstants.getSmfBranchNo(requestUserData.getMedicalCode());
			if (smfBranchNo < 0)
			{
				responseResult.put("result", false);
				responseResult.put("testResults", "medicalCode ERROR[" + requestUserData.getMedicalCode() + "]");
				return ResponseEntity.badRequest().body(responseResult);
			}
			
			boolean isKor = true;
			if (requestUserData.getLanguage() != null &&
				requestUserData.getLanguage().equals("EN"))
			{
				isKor = false;
			}
			
			/** 
			 *  (1) 환자정보 검색
			 *   - input : Request의 hospitalCode, isueDate, testType
			 *   - output : list of PatientData 
			 */
			Map<String, Object> param = new HashMap<String, Object>();
			
			String I_GIC = requestUserData.getHospitalCode();
			String I_BDT_FROM = "";
			String I_BDT_TO = "";
			
			if (isIssueDateRange)
			{
				I_BDT_FROM = requestUserData.getIssueDateFrom().toString().substring(0, 10).replace("-", "");
				I_BDT_TO = requestUserData.getIssueDateTo().toString().substring(0, 10).replace("-", "");
			}
			else
			{
				I_BDT_FROM  = requestUserData.getIssueDate().toString().substring(0, 10).replace("-", "");
				I_BDT_TO = requestUserData.getIssueDate().toString().substring(0, 10).replace("-", "");
			}
					
			String I_CD2 = requestUserData.getTestType();
			if (I_CD2.equals(DidConstants.TYPE_PCR))
			{
				I_CD2 = DidConstants.TYPECODE_PCR;
			}
			else if (I_CD2.equals(DidConstants.TYPE_ANTIBODY))
			{
				I_CD2 = DidConstants.TYPECODE_ANTIBODY;
			}
			else
			{
				responseResult.put("result", false);
				responseResult.put("testResults", "testType ERROR");
				return ResponseEntity.badRequest().body(responseResult);
			}
			
			// testMode start --------------------------------------------
			boolean testMode = false;
			if (requestUserData.getUserPhoneno() != null &&
				requestUserData.getUserPhoneno().isEmpty() == false)
			{
				if (requestUserData.getUserPhoneno().replace("-", "").trim().equals("01041383827") ||
					requestUserData.getUserPhoneno().replace("-", "").trim().equals("01027405391") ||
					requestUserData.getUserPhoneno().replace("-", "").trim().equals("01092836776") ||
					requestUserData.getUserPhoneno().replace("-", "").trim().equals("01064348704"))
				{
					testMode = true;
					I_BDT_FROM = "20210903";
					I_BDT_FROM = "20210903";
					
					if (isIssueDateRange)
					{
						responseResult.put("result", false);
						responseResult.put("testResults", "this is test result, cannot use issueDate range");
						return ResponseEntity.badRequest().body(responseResult);
					}
				}
			}
			// testMode end -----------------------------------------------
			
			param.put("I_GIC", I_GIC);
			param.put("I_BDT_FROM", Integer.valueOf(I_BDT_FROM));
			param.put("I_BDT_TO", Integer.valueOf(I_BDT_TO));
			param.put("I_CD2", I_CD2);
			
			List<Map<String, Object>> plist = commonService.commonList("patInfoList", param); //ucciri
			List<PatientData> patientDataList = new ArrayList<>();
			for (Map<String, Object> map : plist) 
			{
				PatientData patientData = new PatientData();	
				
				//접수일자
				patientData.setReceiptDate(DidUtility.getValue(map, "F600DAT"));
				//접수번호
				patientData.setReceiptNo(DidUtility.getValue(map, "F600JNO"));
				//병원코드(씨젠내부)
				patientData.setHospitalCode(DidUtility.getValue(map, "F600HOS"));
				//보고일자
				patientData.setReportDate(DidUtility.getValue(map, "F600BDT"));
				//환자명
				patientData.setUserName(DidUtility.getValue(map, "F100NAM"));
				//성별
				patientData.setUserSex(DidUtility.getValue(map, "F100SEX"));
				//생년월일
				patientData.setUserBirthday(DidUtility.getValue(map, "F100BDT"));
				//국가구분코드
				patientData.setUserNationCode(DidUtility.getValue(map, "S100PCD"));
				//전화번호
				patientData.setUserPhoneNo(DidUtility.getValue(map, "S100PNO"));
				//주민번호1
				patientData.setPersonalNo1(DidUtility.getValue(map, "F100JN1"));
				//matching value 
				patientData.setMatchingValue(0.0);
				
				patientDataList.add(patientData);
			}
			
			/** 
			 *  (2) 환자정보 matching
			 *   - input : list of PatientData
			 *   - output : list of PatientData 
			 */
			int matchingTargetCnt = patientDataList.size();
			logger.debug("[Matching Target] : " + patientDataList.size());
			List<PatientData> tmpList = new ArrayList<>();
			for (int i = 0; i < patientDataList.size(); i++)
			{
				PatientData data = patientDataList.get(i);
				String comment = "";
				
				String patBirthDay = data.getUserBirthday().trim();
				if (patBirthDay.length() != 8)
				{
					patBirthDay = DidUtility.getBirthDayFromPersonalNo(data.getPersonalNo1());
				}
								
				int matchingCnt = 0;
				double matchingValue = 0.;

				if (data.getUserPhoneNo() != null &&
					requestUserData.getUserPhoneno() != null &&
					DidUtility.isMatched(data.getUserPhoneNo().replace("-", ""),
						                 requestUserData.getUserPhoneno().replace("-", "")))
				{
					matchingCnt++;
					matchingValue += 1000.;
					comment += "전화번호, ";
				}
				
				if (DidUtility.isMatched(data.getUserName(), requestUserData.getUserName()))
				{
					matchingCnt++;
					matchingValue += 100.;
					comment += "성명, ";
				}
				
				if (patBirthDay != null &&
					requestUserData.getUserBirthday() != null &&
					DidUtility.isMatched(patBirthDay.replace("-", ""),
						                 requestUserData.getUserBirthday().replace("-", "")))
				{
					matchingCnt++;
					matchingValue += 10.;
					comment += "생년월일, ";
				}
				
				if (DidUtility.isMatched(data.getUserSex(), requestUserData.getUserSex()))
				{
					matchingCnt++;
					matchingValue += 1.;
					comment += "성별, ";
				}
				
				if (matchingCnt > 1)
				{
					//(생년월일,성별) 일치는 제외
					if (matchingValue <= 11.)
					{
						continue;
					}
					
					//발급일Range지정시 (성별,성별) 일치는 제외
					if (isIssueDateRange && matchingValue <= 101.)
					{
						continue;
					}
					
					data.setMatchingValue(matchingValue);
					if (comment.isEmpty() == false)
					{
						comment = comment.substring(0, comment.length()-2);
					}
					data.setComment(comment);
					tmpList.add(data);
				}
			}
			
			DescendingPatient descPatient = new DescendingPatient();
			Collections.sort(tmpList, descPatient);
			
			patientDataList.clear();
			
			/**
			 * matching value 가 가장 큰것을 선정하되 가장 큰값이 복수개일 경우 모두 포함한다.
			 * (전화번호+성별)일치, (생년월일+성명)일치 두개 모두 존재하는 경우 matching value가 
			 * 큰값인 (전화번호+성별)만 선정한다.
			 */
			logger.debug("[Matching Check Result] : " + tmpList.size());
			double maxValue = 0.;
			for (int i = 0; i < tmpList.size(); i++)
			{
				PatientData data = tmpList.get(i);
				String tmp = Integer.toString(i);
				tmp += ", " + data.getUserPhoneNo();
				tmp += ", " + data.getUserName().trim();
				tmp += ", " + data.getUserBirthday();
				tmp += ", " + data.getPersonalNo1();
				tmp += ", " + data.getUserSex();
				tmp += ", " + data.getUserPhoneNo();
				tmp += ", value[" + data.getMatchingValue() + "]";
				logger.debug("  " + tmp);
				
				if (data.getMatchingValue() >= maxValue)
					patientDataList.add(data);
				
				if (data.getMatchingValue() > maxValue)
					maxValue = data.getMatchingValue();
			}

			/**
			 *  (3) 검사결과 편집
			 *   - input : list of PatientData
			 *   - output : list of TestResultData 
			 */
			
			List<TestResultData> resultList = new ArrayList<TestResultData>();
			
			for (PatientData pd : patientDataList)
			{
				param.clear();
				
				String I_JDAT = pd.getReceiptDate();
				String I_JNO = pd.getReceiptNo();
				param.put("I_JDAT", Integer.valueOf(I_JDAT));
				param.put("I_JNO", Integer.valueOf(I_JNO));
				param.put("I_CD2", I_CD2);
				
				List<Map<String, Object>> results = commonService.commonList("patList", param); //ucciri
				for (Map<String, Object> map : results) 
				{
					TestResultData trd = new TestResultData();

					//결과id (접수일자+접수번호)
					trd.setTestResultID(DidUtility.getValue(map, "F600DAT") + DidUtility.getValue(map, "F600JNO"));
					
					//검사의뢰기관코드
					trd.setHospitalCode(requestUserData.getHospitalCode());
					
					//검사의뢰기관 DID
					trd.setHospitalDID(""); /** @todo */
					
					//검사의뢰기관 VCID
					trd.setHospitalVCID(""); /** @todo */
					
					//검사의뢰기관명
					trd.setHospitalName(isKor ? (DidUtility.getValue(map, "F120FNM").trim())
							                  : (DidUtility.getValue(map, "F120FNM_EN").trim()));
					
					//접수번호
					trd.setReceiptId(Integer.valueOf(DidUtility.getValue(map, "F600JNO")));
					
					//검사항목
					trd.setTestType(requestUserData.getTestType());
					
					//검사코드
					trd.setTestCode(DidUtility.getValue(map, "F600GCD"));
					
					//검체코드
					trd.setSpecimenCode(DidUtility.getValue(map, "F010TCD"));
					
					//피검사자 DID
					trd.setDid(""); /** @todo */
					
					//피검사자 신원확인용 VC ID
					trd.setVcid(""); /** @todo */
					
					//피검사자 이름
					trd.setUserName(DidUtility.getValue(map, "F100NAM").trim());
					
					//검사일시
					trd.setTestDate(DidUtility.convertDateFormat(DidUtility.getValue(map, "F600GDT")));
					
					//접수일
					trd.setReceiptDate(Integer.valueOf(DidUtility.getValue(map, "F600DAT")));
					
					//검사명
					trd.setTestName(DidUtility.getValue(map, "F010FKN").trim());
					
					//검사방법
					trd.setTestMethod(DidUtility.getValue(map, "F028TXT").trim());
					
					//검사결과
					String rstChar = DidUtility.getValue(map, "F600CHR");
					String rstVal = "";
					if (rstChar.toUpperCase().contains("NEGATIVE"))
					{
						if (isKor) rstVal = "음성";
						else rstVal = "Negative";
					}
					else if (rstChar.toUpperCase().contains("POSITIVE"))
					{
						if (isKor) rstVal = "양성";
						else rstVal = "Positive";
					}
					else
					{
						if (isKor) rstVal = "미결정";
						else rstVal = "Inconclusive";
					}
					
					trd.setTestResult(rstVal);
					
					//검사결과 발행일시 (request 정보)
					if (testMode)
					{
						trd.setIssueDate(requestUserData.getIssueDate());
					}
					else
					{
						String bdt = DidUtility.getValue(map, "F600BDT").trim();
						String idt = bdt.substring(0, 4) + "-" + bdt.substring(4, 6) + "-" + bdt.substring(6);
						trd.setIssueDate(idt);
					}
					
					//검사기관코드
					trd.setMedicalCode(DidConstants.smfInstitutionCode[smfBranchNo]);
					
					//검사기관 DID
					trd.setMedicalDID("");
					
					//검사기관 VCID
					trd.setMedicalVCID("");
					
					//검사기관명
					trd.setMedicalName(isKor ? DidConstants.smfNameKor[smfBranchNo]
							                 : DidConstants.smfNameEng[smfBranchNo]);
					
					resultList.add(trd);
				}
			}
			
			if (patientDataList.isEmpty() == false)
			{
				responseResult.put("commment", "체크대상 " + matchingTargetCnt + "건 중, [" + 
							       patientDataList.get(0).getComment() + "] 일치 한 총 " + patientDataList.size() + "건 Matching");
			}
			responseResult.put("result", true);
			responseResult.put("testResults", resultList);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			responseResult.put("result", false);
			responseResult.put("testResults", null);
			logger.error("requestTestResultForUserInfo failed", e);
		}
		
		return ResponseEntity.ok(responseResult);
	}

}
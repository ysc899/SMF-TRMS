package kr.co.softtrain.common.web.rst;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;
import kr.co.softtrain.resultImg.web.AES256;
import kr.co.softtrain.resultImg.web.QrCodeUtils;
import kr.co.softtrain.resultImg.web.ResultImgProp;


/**
 * <pre>
 * kr.co.softtrain.common.web.rst
 * RstBeforeController.java
 * </pre>
 * @title :  
 * @author : OJS
 * @since : 2098. 01. 7.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 3.		수정자명  				최초생성
 * 
 * </pre>
 */
@Controller
public class RstGroupReport {
	Logger logger = LogManager.getLogger();
	
	@Autowired
	private ResultImgProp resultImgProp;

	@Resource(name = "commonService")
	private commonService commonService;
	
	private final AES256 aes256 = new AES256();

	/**
	 * @Method Name : getGroup
	 * @see
	 * <pre>
	 * Method 설명 :  결과지 출력 버튼 클릭 시 선택된 검사항목을의 그룹 리스트 및 검사코드 파라미터, 파일명, MRD 파일명 등 리턴
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getGroup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object getGroup(HttpServletRequest request, HttpSession session) throws Exception {

		/*
		 * Test Url / parameter
		 * http://localhost:8080/getGroup.do?I_PARAM=|NML201705160555641377|NML201705160555641392|NML201705160555621258|NML201705160555621562|NML201705160555621257|NML201705160555605483|NML201705160555611232|NML201705160555611233|
		 */
		/**
		 * 파라미터
		 * I_PARAM: 파라미터
		 * 구분자: | /  내용: 회사구분(NML)3자+접수일자8자+접수번호5자(모자란 자리수는 왼쪽으로 00채움 즉, 521 => 00521) + 검사코드 5자
		 * 예: I_PARAM=|NML201705160555641377|NML201705160555641392| 
		 */
		Map<String, Object> param = new HashMap<String, Object>();

		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		param.put("I_PARAM", Utils.isNull(param.get("I_PARAM")));
		//param.put("I_DAT", Utils.isNull(param.get("I_DAT")));
		//param.put("I_JNO", Utils.isNull(param.get("I_JNO")));		
		//검사코드 파라미터 예) I_GCD=|41377|41392|21258|21562|21257|05483|11232|11233|
		//param.put("I_GCD", Utils.isNull(param.get("I_GCD")));
		param.put("I_REPORT_LANGUAGE", Utils.isNull(param.get("report_language")));

		//생성할 검사코드의 정보 가져오기
		List<Map<String, Object>> resultList = commonService.commonList("rstGroupReportList", param);

		/*
	  	logger.debug("===============================================");
	  	logger.debug(resultList.toString());
	  	logger.debug("===============================================");
		 */
		
		param = (Map<String, Object>) setGroupReoport(resultList,  param, false, "1");
		return param;
	}
	
	
	/**
	 * @Method Name : getGroup2
	 * @see
	 * <pre>
	 * Method 설명 :  결과지 출력 버튼 클릭 시 선택된 검사항목을의 그룹 리스트 및 검사코드 파라미터, 파일명, MRD 파일명 등 리턴
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getGroup2.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object getGroup2(HttpServletRequest request, HttpSession session) throws Exception {
		
		/*
		 * Test Url / parameter
		 * http://localhost:8080/getGroup.do?I_PARAM=|NML201705160555641377|NML201705160555641392|NML201705160555621258|NML201705160555621562|NML201705160555621257|NML201705160555605483|NML201705160555611232|NML201705160555611233|
		 */
		/**
		 * 파라미터
		 * I_PARAM: 파라미터
		 * 구분자: | /  내용: 회사구분(NML)3자+접수일자8자+접수번호5자(모자란 자리수는 왼쪽으로 00채움 즉, 521 => 00521) + 검사코드 5자
		 * 예: I_PARAM=|NML201705160555641377|NML201705160555641392| 
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		param.put("I_PARAM", Utils.isNull(param.get("I_PARAM")));
		//param.put("I_DAT", Utils.isNull(param.get("I_DAT")));
		//param.put("I_JNO", Utils.isNull(param.get("I_JNO")));		
		//검사코드 파라미터 예) I_GCD=|41377|41392|21258|21562|21257|05483|11232|11233|
		//param.put("I_GCD", Utils.isNull(param.get("I_GCD")));		


		param.put("I_COR", Utils.isNull(param.get("I_COR")));
		//병원코드
		param.put("I_HOS", Utils.isNull(param.get("I_HOS")));  
		//접수일자(D), 보고일자(P) 구분
		param.put("I_TERM_DIV", Utils.isNull(param.get("I_TERM_DIV"))); 
		//시작일자
		param.put("I_FDT", Utils.isNull(param.get("I_FDT")).replaceAll("-", "")); 
		//종료일자
		param.put("I_TDT", Utils.isNull(param.get("I_TDT")).replaceAll("-", "")); 
		//수신(다운)여부 O/X
		param.put("I_RECV_YN", Utils.isNull(param.get("I_RECV_YN")));
		param.put("I_RECV", Utils.isNull(param.get("I_RECV_YN"),"A"));

		commonService.commonOne("MWW03T12", param);
		//생성할 검사코드의 정보 가져오기
		List<Map<String, Object>> resultList = commonService.commonList("rstGroupReportList2", param);
		
		/*
	  	logger.debug("===============================================");
	  	logger.debug(resultList.toString());
	  	logger.debug("===============================================");
		 */
		
		param = (Map<String, Object>) setGroupReoport(resultList,  param, true, "2");
		
		// TODO 홍재훈 - QRCode 생성 로직 시작 -------------------------------------
		final String hos = Utils.isNull(param.get("I_HOS"));
		final String appRoot = request.getSession().getServletContext().getRealPath("/");
		((List<Map<String, Object>>)param.get("resultList")).stream()
				.filter(resultRow -> "COV".equals(Objects.toString(resultRow.get("F010RNO"), "").toUpperCase().trim()))
				.forEach(resultRow -> {
					String dat = Utils.isNull(resultRow.get("F600DAT"));
					String jno = Utils.isNull(resultRow.get("F600JNO"));
					String sign = Utils.setSHA256(dat + ":" + jno + ":" + resultImgProp.get(ResultImgProp.ENCKEY));
					
					try {
						QrCodeUtils.makeQrCode(dat, jno, hos, "kor", sign, resultImgProp, appRoot);
					} catch (Exception ex) {
						StringWriter error = new StringWriter();
						ex.printStackTrace(new PrintWriter(error));
						if (logger.isErrorEnabled()) {
							logger.error("QRCode 생성 실패 : {}", error.toString());
						}
						// QRCode 생성이 실패하더라도 결과지는 정상적으로 출력이 되어야 하므로 log만 남기고 나머지 로직 정상적으로 실행
					}
				});
		// TODO 홍재훈 - QRCode 생성 로직 종료 -------------------------------------
		
		return param;
	}
	
	
	/**
	 * @Method Name : getImgGroup
	 * @see
	 * <pre>
	 * Method 설명 : 이미지 수신에서 전체 수신 미수시만 수신 등의 버튼 클릭 시 선택된 검사항목을의 그룹 리스트 및 검사코드 파라미터, 파일명, MRD 파일명 등 리턴
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getImgGroup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object getImgGroup(HttpServletRequest request, HttpSession session) throws Exception {

		/*
		 * Test Url / parameter
		 * http://localhost:8080/getImgGroup.do?I_COR=NML&I_HOS=10747&I_TERM_DIV=D&I_FDT=20190101&I_TDT=20190115&I_RECV_YN=
		 */
		/**
		 * 파라미터
		 * I_COR: 회사구분
		 * I_HOS: 병원코드
		 * I_TERM_DIV: 접수일자(D), 보고일자(P) 구분 
		 * I_FDT: 시작일자
		 * I_TDT: 종료일자
		 * I_RECV_YN: 수신(다운)여부 O/X
		 * 
		 * I_COR=NML&I_HOS=10747&I_TERM_DIV=D&I_FR_DT=20190101&I_TO_DT=20190115&I_RECV_YN=
		 */
		Map<String, Object> param = new HashMap<String, Object>();

		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경


		param.put("I_COR", Utils.isNull(param.get("I_COR")));
		//병원코드
		param.put("I_HOS", Utils.isNull(param.get("I_HOS")));  
		//접수일자(D), 보고일자(P) 구분
		param.put("I_TERM_DIV", Utils.isNull(param.get("I_TERM_DIV"))); 
		//시작일자
		param.put("I_FDT", Utils.isNull(param.get("I_FDT")).replaceAll("-", "")); 
		//종료일자
		param.put("I_TDT", Utils.isNull(param.get("I_TDT")).replaceAll("-", "")); 
		//수신(다운)여부 O/X
		param.put("I_RECV_YN", Utils.isNull(param.get("I_RECV_YN")));
		param.put("I_RECV", Utils.isNull(param.get("I_RECV_YN"),"A"));

		commonService.commonOne("MWW03T12", param);
		//생성할 검사코드의 정보 가져오기
		List<Map<String, Object>> resultList = commonService.commonList("rstIMGGroupReportList", param);

		/*
	  	logger.debug("===============================================");
	  	logger.debug(resultList.toString());
	  	logger.debug("===============================================");
		 */
		
		param = (Map<String, Object>) setGroupReoport(resultList,  param, true, "2");
		
		// TODO 홍재훈 - QRCode 생성 로직 시작 -------------------------------------
		final String hos = Utils.isNull(param.get("I_HOS"));
		final String appRoot = request.getSession().getServletContext().getRealPath("/");
		((List<Map<String, Object>>)param.get("resultList")).stream()
				.filter(resultRow -> "COV".equals(Objects.toString(resultRow.get("F010RNO"), "").toUpperCase().trim()))
				.forEach(resultRow -> {
					String dat = Utils.isNull(resultRow.get("F600DAT"));
					String jno = Utils.isNull(resultRow.get("F600JNO"));
					String sign = Utils.setSHA256(dat + ":" + jno + ":" + resultImgProp.get(ResultImgProp.ENCKEY));
					
					try {
						QrCodeUtils.makeQrCode(dat, jno, hos, "kor", sign, resultImgProp, appRoot);
					} catch (Exception ex) {
						StringWriter error = new StringWriter();
						ex.printStackTrace(new PrintWriter(error));
						if (logger.isErrorEnabled()) {
							logger.error("QRCode 생성 실패 : {}", error.toString());
						}
						// QRCode 생성이 실패하더라도 결과지는 정상적으로 출력이 되어야 하므로 log만 남기고 나머지 로직 정상적으로 실행
					}
				});
		
		// TODO 홍재훈 - QRCode 생성 로직 종료 -------------------------------------
		
		return param;

	}

	/**
	 * @Method Name : setGroupReoport
	 * @see
	 * <pre>
	 * Method 설명 :  결과지를 그룹별로 묶음
	 * return_type : Object
	 * </pre>
	 * @param resultList
	 * @param param
	 * @return
	 */
	public Object setGroupReoport(List<Map<String, Object>> resultList, Map<String, Object> param,Boolean imgFg, String imgMenuGubun) {

		int r_len = resultList.size();

		String FILE_NM = "";//jsp 생성 파일명
		String S011RCL = "";//리포트 양식

		String F008GCD = ""; //이미지 출력 검사코드

		String GCD = ""; //검사코드
		String HOS_GCD = ""; //병원검사코드
		String F010FKN = ""; //검사명


		HashMap<String, String> rowMap = null;
		
		//미리보기 리스트
		ArrayList<HashMap<String, String>> rtnList = new ArrayList<HashMap<String, String>>();
		boolean isGroup = false; // 이미지 생성하는 경우, N번째 종목의 이미지를 생성할지/말지 여부
		
		//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
		boolean is72185 = isGCD(resultList,"72185");

		logger.debug(is72185+"");
		
		//boolean isPDF = false;
		boolean isPDFFirst = false;
		
		boolean isChgTemp = false;
		String isChgTempNm = "";
		
		boolean isChgMicTemp = false;
		boolean isChg72190Temp = false;
		//boolean isChg72190First = false;
		boolean is72190_72213 = false;
		
		boolean isChg71256Temp = false;
		//boolean isChg71256First = false;
		boolean is71256_71313 = false;
		
		
		boolean is42000 = false;
		boolean isA1008Group = false;
		
		//int pdf_cnt = 0;
		
		//==================================================================================================================================================================================
		// 00799/00802/00806/00822 4개 종목은 별도로 생성되게 처리하기 위해 데이터를 담아놓을 변수를 선언한다. 
		//==================================================================================================================================================================================
		boolean urine_gcd_check = false;
		String urine_F600GCD = "";
		String urine_F100NAM = "";
		String urine_F600DAT = "";
		String urine_F600JNO = "";
		String urine_HOS_GCD = "";
		String urine_S011RCL = "";
		String urine_PGCD = "";
		String urine_PGNM = "";
		String urine_PG_TIT = "";
		String urine_F010RNO = "";
		String urine_S018SYN = "";
		String urine_F600HAK = "";
		String urine_S018RFN = "";
		String urine_P_HOS_GCD = "";
		String urine_P_GCD = "";
		String urine_BDT = "";
		String urine_ADD_CD_NM = "";
		String urine_F008GCD = "";
		String urine_F010FKN = "";
		String urine_P_F010FKN = "";
		String urine_CU_SE_GCD = "";
		String urine_F100HOS = "";
		String urine_F600CHR = "";
		//HashMap<String, String> rowMap2_tmp = null;
		HashMap<String, String> rowMap2 = null;
		String preDate = "";
		String preJno = "";
		String nowDate = "";
		String nowJno = "";
		String nextDate = "";
		String nextJno = "";
		boolean nowNextDateJnoCheck = false;
		
		//==================================================================================================================================================================================
		// 이미지 개별/일괄 생성 : Start
		//==================================================================================================================================================================================
		for(int i=0; i<r_len; i++ ){
			isGroup = false;
			
			/*if("00802".equals(String.valueOf(resultList.get(i).get("F600GCD")))
				|| "00822".equals(String.valueOf(resultList.get(i).get("F600GCD")))
			){
				System.out.println("### 접수일자 : "+String.valueOf(resultList.get(i).get("F600DAT")));
				System.out.println("### 접수번호 : "+String.valueOf(resultList.get(i).get("F600JNO")));
				System.out.println("### 검사코드 : "+String.valueOf(resultList.get(i).get("F600GCD")));
			}*/
			
			/*if(i == 25)					
			{
				System.out.println("### i : "+i);
			}*/

//			logger.debug(i+"th:: "+resultList.get(i).toString());
			
			/* pdf 체트 F600chr 값 별지참조로 체크 하므로 주석 처리
			pdf_cnt = Integer.parseInt(String.valueOf(resultList.get(i).get("PDF_CNT")));
 
			if(!imgFg)
			{
				if(pdf_cnt > 0){
					isPDF = true;
					break;
				}
			}
			*/
			/**
			 * MIC 세균정보가 나올경우 SPECIAL-04 템플릿을 사용한다.
			 */
			if("31108".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31109".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31110".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31111".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31112".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31113".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31114".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31115".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31116".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31117".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31118".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31119".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31120".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31123".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31124".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31126".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31127".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"31128".equals(String.valueOf(resultList.get(i).get("F600GCD")))
			){
				isChgMicTemp = true;
			}else if(!"".equals(String.valueOf(resultList.get(i).get("CHN_TEMPLATE")).trim()) && resultList.get(i).get("CHN_TEMPLATE") != null ){ // MWS002M@ 테이블에 프로파일 등록되어져 있는지 체크
				isChgTemp = true;
				isChgTempNm = String.valueOf(resultList.get(i).get("CHN_TEMPLATE"));	// MWS002M@ 테이블에 프로파일 등록된 경우, 셋팅된 템플릿 가져오기
			}

			//jpg 또는 pdf 파일명
			FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM"));
			S011RCL = String.valueOf(resultList.get(i).get("S011RCL"));
			//검사코드
			GCD = String.valueOf(resultList.get(i).get("F600GCD"));
			//이미지 검사코드
			F008GCD = String.valueOf(resultList.get(i).get("F008GCD"));
			//병원검사코드
			HOS_GCD = String.valueOf(resultList.get(i).get("HOS_GCD"));
			//검사명
			F010FKN = String.valueOf(resultList.get(i).get("F010FKN"));

			//	  		String.valueOf(resultList.get(i).get("CU_SE_GCD")) != null && !"".equals(String.valueOf(resultList.get(i).get("CU_SE_GCD")))
			//컬쳐 센시 구분에 의한 검사코드 변경 켤처 코드와 센시코드를 합한 검사코드로 변경

			//if(!"".equals(Utils.isNull(resultList.get(i).get("CU_SE_GCD")))){
			//	GCD = String.valueOf(resultList.get(i).get("CU_SE_GCD"));
			//}

			
			// ######################################## 파일명 수정 : Start ########################################
			
			//파일명 하드코딩 해야 되는 부분
			if("HARD".equals(FILE_NM)){
				// F100ETC-- 검체코드 또는 파일명을 위한 ETC2
				// FHCDFL5 -- 파일명을 위한 ETC1
				//General

				if("GNR".equals(S011RCL)){
					
					//19775-전남중앙병원
					//  isFirstMake or isWanYo ==> N 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_C
					//  isFirstMake or isWanYo ==> Y 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_결과지 타입
					if("19775".equals(String.valueOf(resultList.get(i).get("F100HOS"))) ){
						//isFirstMake or isWanYo
						if("N".equals(String.valueOf(resultList.get(i).get("FST_MAKE")))){
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_C";
						}else{
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_" +String.valueOf(resultList.get(i).get("S011RTP"));
						}

						FILE_NM = FILE_NM.replaceAll("[(]", "-").replaceAll("[)]", "-").replaceAll("null_", "");;

					}
					
					/*
				    	30286	순천현대여성아동병원(진단)
						30310	순천현대여성아동병원(검진)
					*/
					if("30286".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						|| "30310".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					  ){
						if( resultList.get(i).get("FHCDFL5") != null && !"".equals(String.valueOf(resultList.get(i).get("FHCDFL5"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("FHCDFL5"));
						}else if( resultList.get(i).get("F100ETC") != null && !"".equals(String.valueOf(resultList.get(i).get("F100ETC"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"));
						}
					}
				}

				//Pathologic
				else if("PTL".equals(S011RCL)){ 
						
					//19775-전남중앙병원
					//  isFirstMake or isWanYo ==> N 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_C
					//  isFirstMake or isWanYo ==> Y 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_결과지 타입
					if("19775".equals(String.valueOf(resultList.get(i).get("F100HOS"))) ){
						//isFirstMake or isWanYo
						if("N".equals(String.valueOf(resultList.get(i).get("FST_MAKE")))){
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_C";
						}else{
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_" +String.valueOf(resultList.get(i).get("S011RTP"));
						}

						FILE_NM = FILE_NM.replaceAll("[(]", "-").replaceAll("[)]", "-").replaceAll("null_", "");;
					}

					/*
				    30286	순천현대여성아동병원(진단)
					30310	순천현대여성아동병원(검진)
					*/
					if("30286".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						|| "30310".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					  ){
						if( resultList.get(i).get("FHCDFL5") != null && !"".equals(String.valueOf(resultList.get(i).get("FHCDFL5"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("FHCDFL5"));
						}else if( resultList.get(i).get("F100ETC") != null && !"".equals(String.valueOf(resultList.get(i).get("F100ETC"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"));
						}
					}
				}
			
				//Special
				else if("SPC".equals(S011RCL)){ 

					//19775-전남중앙병원
					//  isFirstMake or isWanYo ==> N 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_C
					//  isFirstMake or isWanYo ==> Y 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_결과지 타입
					if("19775".equals(String.valueOf(resultList.get(i).get("F100HOS"))) ){
						//isFirstMake or isWanYo
						if("N".equals(String.valueOf(resultList.get(i).get("FST_MAKE")))){
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_C";
						}else{
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_" +String.valueOf(resultList.get(i).get("S011RTP"));
						}

						FILE_NM = FILE_NM.replaceAll("[(]", "-").replaceAll("[)]", "-").replaceAll("null_", "");;
					}

					/*
				    30286	순천현대여성아동병원(진단)
					30310	순천현대여성아동병원(검진)
					*/
					if("30286".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						|| "30310".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					  ){
						if( resultList.get(i).get("FHCDFL5") != null && !"".equals(String.valueOf(resultList.get(i).get("FHCDFL5"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("FHCDFL5"));
						}else if( resultList.get(i).get("F100ETC") != null && !"".equals(String.valueOf(resultList.get(i).get("F100ETC"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"));
						}
					}
				}
				
				//Specific
				else if("SPF".equals(S011RCL)){ 

					//19775-전남중앙병원
					//  isFirstMake or isWanYo ==> N 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_C
					//  isFirstMake or isWanYo ==> Y 이면 검체체취일_챠트번호_수진자명_진료과명_진료의사_기타_결과지 타입
					if("19775".equals(String.valueOf(resultList.get(i).get("F100HOS"))) ){
						//isFirstMake or isWanYo
						if("N".equals(String.valueOf(resultList.get(i).get("FST_MAKE")))){
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_C";
						}else{
							FILE_NM = String.valueOf(resultList.get(i).get("F111GDT"))
									+ "_" +String.valueOf(resultList.get(i).get("F100CHN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100NAM"))
									+ "_" +String.valueOf(resultList.get(i).get("F100JKN"))
									+ "_" +String.valueOf(resultList.get(i).get("F100DRC"))
									+ "_" +String.valueOf(resultList.get(i).get("F100ETC"))
									+ "_" +String.valueOf(resultList.get(i).get("S011RTP"));
						}

						FILE_NM = FILE_NM.replaceAll("[(]", "-").replaceAll("[)]", "-").replaceAll("null_", "");
					}

					/*
					    30286	순천현대여성아동병원(진단)
						30310	순천현대여성아동병원(검진)
					*/
					if("30286".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						||"30310".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					  ){
						if( resultList.get(i).get("FHCDFL5") != null && !"".equals(String.valueOf(resultList.get(i).get("FHCDFL5"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("FHCDFL5"));
						}else if( resultList.get(i).get("F100ETC") != null && !"".equals(String.valueOf(resultList.get(i).get("F100ETC"))) ){
							FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"));
						}
					}
				}

			}else{
				// HARD 구분 하드 코딩외 예외 처리
				// 병원별 검사 코드별 예외 처리

				//General
				if("GNR".equals(S011RCL)){
					// 병원별 + 폼별 파일명을 다르게 지정해야 하는 경우
					
					//23302 : 충청남도 홍성의료원
					if("23302".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						
						//보고일자와 보고시간 사이에 문자가 '_' 아닌 '-'로 설정
						String bdt_time_val = FILE_NM.split("_")[5];
						bdt_time_val = bdt_time_val.substring(0, 8)+"-"+bdt_time_val.substring(8, bdt_time_val.length());
						
						//결과지 타입이 1자리인 경우, 앞에 '0'문자를 붙이도록 설정
						String type_val = FILE_NM.split("_")[4];
						
						if(type_val.trim().length() < 2){
							type_val = "0" + type_val;
						}
						
						FILE_NM = FILE_NM.split("_")[0]+"_"
									+ FILE_NM.split("_")[1].replaceAll(" ", "")+"_"
									+ FILE_NM.split("_")[2]+"_"
									+ FILE_NM.split("_")[3]+"_"
									+ type_val+"-"
									+ bdt_time_val;
					}
				}
				
				//Pathologic
				else if("PTL".equals(S011RCL)){
					// 병원별 + 폼별 파일명을 다르게 지정해야 하는 경우
					
					//23302 : 충청남도 홍성의료원
					if("23302".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						
						//보고일자와 보고시간 사이에 문자가 '_' 아닌 '-'로 설정
						String bdt_time_val = FILE_NM.split("_")[5];
						bdt_time_val = bdt_time_val.substring(0, 8)+"-"+bdt_time_val.substring(8, bdt_time_val.length());
						
						//결과지 타입이 1자리인 경우, 앞에 '0'문자를 붙이도록 설정
						String type_val = FILE_NM.split("_")[4];
						
						if(type_val.trim().length() < 2){
							type_val = "0" + type_val;
						}
						
						FILE_NM = FILE_NM.split("_")[0]+"_"
									+ FILE_NM.split("_")[1].replaceAll(" ", "")+"_"
									+ FILE_NM.split("_")[2]+"_"
									+ FILE_NM.split("_")[3]+"_"
									+ type_val+"-"
									+ bdt_time_val;
					}
				}
				
				//Special
				else if("SPC".equals(S011RCL)){
					
					// 병원별 + 폼별 파일명을 다르게 지정해야 하는 경우
					
					//20229 : 청화병원
					if("20229".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						//검사코드  21276
						if("21276".equals(String.valueOf(resultList.get(i).get("F600GCD")))  ){
							//차트번호_PD(병동)_접수일자_00506_검사코드
							FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
									+"_PD_" + String.valueOf(resultList.get(i).get("F600DAT"))
									+"_00506_" + String.valueOf(resultList.get(i).get("F600GCD"));
						}
					}
					
					//23302 : 충청남도 홍성의료원
					if("23302".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						
						//보고일자와 보고시간 사이에 문자가 '_' 아닌 '-'로 설정
						String bdt_time_val = FILE_NM.split("_")[5];
						bdt_time_val = bdt_time_val.substring(0, 8)+"-"+bdt_time_val.substring(8, bdt_time_val.length());
						
						//결과지 타입이 1자리인 경우, 앞에 '0'문자를 붙이도록 설정
						String type_val = FILE_NM.split("_")[4];
						
						if(type_val.trim().length() < 2){
							type_val = "0" + type_val;
						}
						
						FILE_NM = FILE_NM.split("_")[0]+"_"
									+ FILE_NM.split("_")[1].replaceAll(" ", "")+"_"
									+ FILE_NM.split("_")[2]+"_"
									+ FILE_NM.split("_")[3]+"_"
									+ type_val+"-"
									+ bdt_time_val;
					}
				}
				
				//Specific
				else if("SPF".equals(S011RCL)){
					
					// 병원별 + 폼별 파일명을 다르게 지정해야 하는 경우
					
					//23302 : 충청남도 홍성의료원
					if("23302".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						
						//보고일자와 보고시간 사이에 문자가 '_' 아닌 '-'로 설정
						String bdt_time_val = FILE_NM.split("_")[5];
						bdt_time_val = bdt_time_val.substring(0, 8)+"-"+bdt_time_val.substring(8, bdt_time_val.length());
						
						//결과지 타입이 1자리인 경우, 앞에 '0'문자를 붙이도록 설정
						String type_val = FILE_NM.split("_")[4];
						
						if(type_val.trim().length() < 2){
							type_val = "0" + type_val;
						}
						
						FILE_NM = FILE_NM.split("_")[0]+"_"
									+ FILE_NM.split("_")[1].replaceAll(" ", "")+"_"
									+ FILE_NM.split("_")[2]+"_"
									+ FILE_NM.split("_")[3]+"_"
									+ FILE_NM.split("_")[4]+"-"
									+ bdt_time_val;
						
						//검사코드  00691
						if("00691".equals(String.valueOf(resultList.get(i).get("F600GCD")))  ){
							//일발 파일 명 + "_0"
							FILE_NM = FILE_NM+"0";
						}
					}

					//30336 : 한강아이제일병원
					if("30336".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						//검사코드  00690 / 00691
						if("00690".equals(String.valueOf(resultList.get(i).get("F600GCD"))) ){
							//일반 파일 명 + "_01"
							FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM")) +"_01";
						}else if("00691".equals(String.valueOf(resultList.get(i).get("F600GCD"))) ){
							//일반 파일 명 + "_02"
							FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM")) +"_02";
						}
					}


					//26241 : 마리본산부인과
					//33036 : 삼성프라임산부인과
					//26153 : 윤호병원 (2019.11.29 추가)
					if("26241".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						|| "33036".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						|| "26153".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					){
						//검사코드  72185
						if("72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))  ){
							////일반 파일 명 + "_검사코드"
							FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM"))
									+"_" + String.valueOf(resultList.get(i).get("F600GCD"));
						}
					}


					//29005 : 한사랑병원
					if("29005".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						//검사코드  72182 || 72225
						if("72182".equals(String.valueOf(resultList.get(i).get("F600GCD")))  
								|| "72225".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
							//차트번호_수진자명_검체코드_병원코드	_검사코드_	01
							FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
									+"_" + String.valueOf(resultList.get(i).get("F100NAM"))
									+"_" + String.valueOf(resultList.get(i).get("F100ETC"))
									+"_" + String.valueOf(resultList.get(i).get("F100HOS"))
									+"_" + String.valueOf(resultList.get(i).get("F600GCD"))
									+"_01" ;
						}
					}

					//14054 : 청담마리산부인과
					if("14054".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
						//검사코드  72185 
						if("72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))   ){
							////일반 파일 명 + "_병원검사코드"
							FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM"))
									+"_" + String.valueOf(resultList.get(i).get("HOS_GCD"));
						}
					}

					/*
					 * 16702 : 건양대병원(진단)
					 * 부속코드가 아닌 검사코드의 경우 '/'가 제거되어 파일명이 붙어서 나오므로 '_'로 수정
					 */
					if ("16702".equals(String.valueOf(resultList.get(i).get("F100HOS"))) ){
						FILE_NM = FILE_NM.replaceAll("/", "_");
					}
				}
				
				//==================================================================================================================================================================================
				// 파일명이 하드코딩 or 폼종류와 무관하게 고정으로 변경이 되어야 하는 경우 - Start
				//==================================================================================================================================================================================
				
				// 22015 : 삼육의료원 서울병원
				// 30418 : 경희의료원 (삭제 - 2020.03.03 / 요청자 : 김영상 과장)
				// 파일명은 구분자가 _가 아닌 -로되어야함
                if("22015".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
					FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM")).replaceAll("_","-");
				}
				
                // 31393 : 현대여성아동병원(일반) , 31394	: 현대여성아동병원(조직) , 31395 : 현대여성아동병원(검진)
                else if("31393".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					||"31394".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					||"31395".equals(String.valueOf(resultList.get(i).get("F100HOS")))
				){
                	if( resultList.get(i).get("FHCDFL5") != null && !"".equals(String.valueOf(resultList.get(i).get("FHCDFL5"))) ){
						FILE_NM = String.valueOf(resultList.get(i).get("FHCDFL5"));
					}else if( resultList.get(i).get("F100ETC") != null && !"".equals(String.valueOf(resultList.get(i).get("F100ETC"))) ){
						FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"));
					}
                	
                	// 파일명 강제 변경 : Start =======================
                	String[] gcd_change_str =FILE_NM.split("_");
                	if(!"검체번호".equals(FILE_NM) // 검체번호가 없는 경우에 검체번호라고 출력됨 
                			&& "50215".equals(gcd_change_str[3].trim())){
                		gcd_change_str[3] = "41940";
                		FILE_NM = gcd_change_str[0]+"_"+gcd_change_str[1]+"_"+gcd_change_str[2]+"_"+gcd_change_str[3]+"_"+gcd_change_str[4];
                	}
                	// 파일명 강제 변경 : End =======================
				}
                
                // 23979 : 고신대학교복음병원
                else if("23979".equals(String.valueOf(resultList.get(i).get("F100HOS")))
    			){
                	
                	// 병원에서 처방한 검사코드가 부속종목일 경우, 부속을 구분하는 끝에 01,02...등 문자가 파일명에 포함되지 않게 해달라는 요청.  ======================== Start
                	String hosGcd_nm = String.valueOf(resultList.get(i).get("HOS_GCD"));
                	
                	/* 20191127 - 매핑테이블에 부속검사 대표코드에 '00'을 붙이는 작업을 일괄로 한 이후 예외처리가 되어 병원처방코드 끝에 2자리가 짤려서 출력되어서 병원담당자요청으로 처방코드끝자리 2개까지 출력되도록 추석처리함.*/
                	/*if("72189".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                		|| "72242".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                	){
                		hosGcd_nm = hosGcd_nm.substring(0, hosGcd_nm.length()-2);
                	}*/
                	// 병원에서 처방한 검사코드가 부속종목일 경우, 부속을 구분하는 끝에 01,02...등 문자가 파일명에 포함되지 않게 해달라는 요청.  ======================== End
                	
                	FILE_NM = String.valueOf(resultList.get(i).get("FHCDFL5"))
                				+"_" + hosGcd_nm;
    			}
                
                
                //23199 : 양정분종합검진센타
                /* 20190715 제거
                else if("23199".equals(String.valueOf(resultList.get(i).get("F100HOS")))){
					//검사코드  61026 || 68021
					if("61026".equals(String.valueOf(resultList.get(i).get("F600GCD")))  
							|| "68021".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
						//차트번호_GY(병동)_접수일자_00506_결과지타입(01.13..)_접수번호
						FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
								+"_GY_" + String.valueOf(resultList.get(i).get("F600DAT"))
								+"_00506_" + String.valueOf(resultList.get(i).get("S011RTP"))
								+"_" + String.valueOf(resultList.get(i).get("F600JNO"));
					}else if("A6937".equals(String.valueOf(resultList.get(i).get("PGCD")))){
						//차트번호_GY(병동)_접수일자_00506_결과지타입(01.13..)_접수번호
						FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
								+"_GY_" + String.valueOf(resultList.get(i).get("F600DAT"))
								+"_00506_01_" + String.valueOf(resultList.get(i).get("F600JNO"));
					}
				}    
                */
                
             // 29404 : 에델산부인과
                else if("29404".equals(String.valueOf(resultList.get(i).get("F100HOS")))
    			){
                	if("72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                	){
                		FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                				+"_" + String.valueOf(resultList.get(i).get("F100NAM"))
                				+"_" + String.valueOf(resultList.get(i).get("FHCDTCD"))
                				+"_" + String.valueOf(resultList.get(i).get("F600GCD"))
                				+"_01";
                	}
    			}
                
             // 32944 : 우리원헬스케어
                else if("32944".equals(String.valueOf(resultList.get(i).get("F100HOS")))
    			){
                	FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                				+"@" + String.valueOf(resultList.get(i).get("F111GDT"));
    			}
                
             // 33081 : 구세산부인과
             /* 20191002 제거
                else if("33081".equals(String.valueOf(resultList.get(i).get("F100HOS")))
    			){
                	FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                				+"^" + String.valueOf(resultList.get(i).get("F100ETC"))
			                	+"^" + String.valueOf(resultList.get(i).get("F100NAM"))
			                	+"^" + String.valueOf(resultList.get(i).get("HOS_GCD"));
    			}
    			*/
                // 울진군의료원 (처리일자:2020.08.27 - 검사코드=05566)
                // 울진군의료원 예외처리 주석처리 (처리일자:2021.07.13 - 검사코드=05566)
                // 울진군의료원 예외조건 추가 (처리일자:2021.07.13 - 검사코드=05573)
                else if("17251".equals(String.valueOf(resultList.get(i).get("F100HOS"))) 
                		//&& "05566".equals(String.valueOf(resultList.get(i).get("PGCD")))
                		&& "05573".equals(String.valueOf(resultList.get(i).get("PGCD")))
    			){
                	/*FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
            				+"_" + String.valueOf(resultList.get(i).get("F100NAM"))
		                	+"_" + String.valueOf(resultList.get(i).get("F100ETC"))
		                	+"_" + "l0846"
		                	+"_"+"01";*/
                	FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"))
		                	+"_" + "l0846"
		                	+"_"+"01";
    			}
                
                // 충주의료원 (처리일자:2020.09.08)
                else if("13094".equals(String.valueOf(resultList.get(i).get("F100HOS"))) 
    			){
                	if ("72020".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                	){
                		FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                				+"_" + String.valueOf(resultList.get(i).get("F100ETC"))
    		                	+"_" + String.valueOf(resultList.get(i).get("HOS_GCD")).replaceAll("s5956g", "s5956")
    		                	+"_" + String.valueOf(resultList.get(i).get("BDT"));
                	}
                	
                	// 2020.11.24 - 71341 검사 이미지 파일명의 병원처방코드를 강제로 s7133 문자로 치환한다. 이미지OCS 연동을 위해 처리함.
                	if ("71341".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                	){
                		FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                				+"_" + String.valueOf(resultList.get(i).get("F100ETC"))
    		                	+"_" + String.valueOf(resultList.get(i).get("HOS_GCD")).replaceAll("ainflus", "s7133")
    		                	+"_" +"01";
                	}
                	
                	// 2021.11.16 - 81790 검사 이미지 파일명의 병원처방코드를 강제로 s5030 문자로 치환한다. 이미지OCS 연동을 위해 처리함.
                	// 2022.05.20 - 지점담당자 요청으로 치환하지 않도록 처리함. 
                	/*if ("41024".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                	){
                		FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                				+"_" + String.valueOf(resultList.get(i).get("F100ETC"))
    		                	+"_" + String.valueOf(resultList.get(i).get("HOS_GCD")).replaceAll("s5123s", "s5030")
    		                	+"_" + String.valueOf(resultList.get(i).get("BDT"));
                	}*/
    			}
                
                /*
				 * 21225 : 경기도의료원이천병원
				 * 2개 조직 종목(51023,68028)이 의뢰될 경우, 파일명에 병원처방코드가 51023 종목이 아닌 68028 종목의 병원처방코드가 입력되도록 수정.
				 */
                /*else if("21225".equals(String.valueOf(resultList.get(i).get("F100HOS"))) 
                		&& "51023".equals(String.valueOf(resultList.get(i).get("F600GCD")))
				){
					FILE_NM = FILE_NM.replaceAll("c5630", "hqc5602");
				}*/
                
                // 한양대학교병원(진단) (처리일자:2020.12.10)
                else if("29804".equals(String.valueOf(resultList.get(i).get("F100HOS"))) 
                		//&& "05566".equals(String.valueOf(resultList.get(i).get("PGCD")))
    			){
                	String[] f100etc_spit_val = String.valueOf(resultList.get(i).get("F100ETC")).split("_"); // 파일명 조건1 변수
                	String hos_gcd_compare_f100etc = ""; // 파일명 조건1 변수
                	String profileGcdChecVal = "";		 // 파일명 조건2 변수
                	
                	// (파일명 조건1) 검체번호에 입력된 값중 병원처방코드 값과 일치하는 문자가 있는 경우, 중복되어 표기되지 않게 하기 위한 조건
                	if(!f100etc_spit_val[f100etc_spit_val.length-1].equals(String.valueOf(resultList.get(i).get("HOS_GCD")))) 
                		hos_gcd_compare_f100etc = String.valueOf(resultList.get(i).get("HOS_GCD"))+"_";
                		
                	if(f100etc_spit_val.length > 1){ // ex : 2220633354/2735/L74124 (접수일자 : 2022.08.31 / 접수번호 : 4334)
                		FILE_NM = f100etc_spit_val[0] 
                					+"_" 
                					+ f100etc_spit_val[f100etc_spit_val.length-1]
    		                		+"_" 
    		                		+ hos_gcd_compare_f100etc 
    		                		+ "01";
                	}else{	// ex : 2210475506 (접수일자 : 2022.09.01 / 접수번호 : 3828)
                		FILE_NM = String.valueOf(resultList.get(i).get("F100ETC")) 
                					+"_" 
                					+ hos_gcd_compare_f100etc 
                					+ "01";
                	}
                	
                	
                	// (파일명 조건2) B2254, B2253 2개 검사인 경우만 파일명 예외처리.
                	// 2022.10.11 : 병원에서 검체번호를 주는 형태와 별개로 예외처리 요청함.
                	// B2254	mchcdh@ 의 검체번호 / mc033m@ 의 00318 00323 의 처방코드
                	// B2253	mchcdh@ 의 검체번호 / mc033m@ 의 00309 00317 의 처방코드
                	if("B2254".equals(String.valueOf(resultList.get(i).get("PGCD")))
                		|| "B2253".equals(String.valueOf(resultList.get(i).get("PGCD")))
                	){
                		if(!"".equals(f100etc_spit_val[0])
                			&& f100etc_spit_val.length > 1
                		){
                			FILE_NM = f100etc_spit_val[0]
                					+"_" 
                					+ String.valueOf(resultList.get(i).get("HOS_GCD"))
                					+"_01"; 
                		}else{
                			FILE_NM = String.valueOf(resultList.get(i).get("F100ETC"))
                    				+"_" 
                    				+ String.valueOf(resultList.get(i).get("HOS_GCD"))
                    				+"_01";
                		}
                	}
    			}
                
                // 한사랑병원 (처리일자:2021.11.05)
                else if("29005".equals(String.valueOf(resultList.get(i).get("F100HOS"))) 
    			){
                	
                	// 현재 날짜 구하기
                	LocalDate now_date = LocalDate.now();
                	
                	// 포맷 정의하기
                	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
                	
                	// 포맷 적용하기
                	String formated_now_date = now_date.format(formatter1);
                	
                	// 현재 시간 구하기
                	LocalTime now_time = LocalTime.now();
                	
                	// 포맷 정의하기
                	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HHmmss");
                	
                	// 포맷 적용하기
                	String formated_now_time = now_time.format(formatter2);
                		
                	FILE_NM = String.valueOf(resultList.get(i).get("F100CHN"))
                			+"_" + String.valueOf(resultList.get(i).get("F100NAM"))
            				+"_" + String.valueOf(resultList.get(i).get("F100ETC"))
		                	+"_" + String.valueOf(resultList.get(i).get("HOS_GCD"))
		                	+"_01" 
		                	+"_" + String.valueOf(formated_now_date) 
                			+"_" + String.valueOf(formated_now_time); 
    			}
                
                // 27466 : 한마음병원(병리)
                else if("27466".equals(String.valueOf(resultList.get(i).get("F100HOS")))
    			){
                	if("51023".equals(String.valueOf(resultList.get(i).get("F600GCD")))
                	){
                		FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM")).replaceAll("PSSM00048", "PSUM00012");
                	}
    			}
                
                // 34139 : 명지성모병원
                // 13837 : 강릉아산병원
                else if("34139".equals(String.valueOf(resultList.get(i).get("F100HOS")))
                		|| "13837".equals(String.valueOf(resultList.get(i).get("F100HOS")))
    			){
                	FILE_NM = FILE_NM.replaceAll("_", "-");
    			}
			}
			
			//==================================================================================================================================================================================
			// 파일명이 하드코딩 or 폼종류와 무관하게 고정으로 변경이 되어야 하는 경우 - End
			//==================================================================================================================================================================================
			
			// ######################################## 파일명 수정 : End ########################################
			
			
			//==================================================================================================================================================================================
			// 별도로 이미지 결과 생성하는 경우, 
			// 일괄 이미지생성할 때, A환자와 B환자를 구분하기 위해 이전/현재/이후 접수일자/번호를 별도로 저장한다. 
			//==================================================================================================================================================================================
																																															  
			if(i != 0){
				preDate = String.valueOf(resultList.get(i-1).get("F600DAT")).trim(); // 이전접수일자
				preJno = String.valueOf(resultList.get(i-1).get("F600JNO")).trim();	 // 이전접수번호
			}
			
			nowDate = String.valueOf(resultList.get(i).get("F600DAT")).trim(); //현재접수일자
			nowJno = String.valueOf(resultList.get(i).get("F600JNO")).trim();  //현재접수번호
			
			if(i != r_len-1){
				nextDate = String.valueOf(resultList.get(i+1).get("F600DAT")).trim(); // 다음접수일자
				nextJno = String.valueOf(resultList.get(i+1).get("F600JNO")).trim();  // 다음접수번호
			}
			
			// 현재접수일자/접수번호와 다음접수일자/접수번호를 비교하여, 일치하면 true
			nowNextDateJnoCheck = false; 
			if(!nowDate.equals(nextDate) || !nowJno.equals(nextJno)){
				nowNextDateJnoCheck = true;					
			}
			
			// 이전접수일자/접수번호와 현재접수일자/접수번호를 비교하여, 일치하면 true
			//preNowDateJnoCheck = false; //미사용
			if(!preDate.equals(nowDate) || !preJno.equals(nowJno)){
				isGroup = false;					
			}
			
			
			// 20200115 - 요검경검사를 별도로 출력할것인가/한장에 함께출력할 것인가 구분하는 값
			String urin_micro_13_form = String.valueOf(resultList.get(i).get("URIN_MICRO_13_FORM"));
			//System.out.println("##0 urin_micro_13_form :: "+urin_micro_13_form);
			
			//==================================================================================================================================================================================
			// 00799/00802/00806/00822 4개 종목은 별도로 생성되게 처리하기 위해 변수에 데이터를 담아놓는다. 
			//==================================================================================================================================================================================
			if(
				("00799".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"00802".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"00806".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					||"00822".equals(String.valueOf(resultList.get(i).get("F600GCD")))
				) && !"URIN_MICRO".equals(String.valueOf(urin_micro_13_form))
				&& !"CHI".equals(String.valueOf(resultList.get(i).get("F010RNO")).trim())
			){
				//System.out.println("##1 urin_micro_13_form :: "+urin_micro_13_form);
				//==================================================================================================================================================================================
				// 별도로 이미지를 생성하기 위한 변수값들
				//==================================================================================================================================================================================
				urine_F600GCD = GCD;	// 검사코드
				urine_F100NAM = String.valueOf(resultList.get(i).get("F100NAM"));	//환자명
				urine_F600DAT = String.valueOf(resultList.get(i).get("F600DAT"));	//접수일자
				urine_F600JNO = String.valueOf(resultList.get(i).get("F600JNO"));	//접수번호
				urine_HOS_GCD = HOS_GCD;	//병원검사코드
				urine_S011RCL = S011RCL;	//결과지 유형 (GNR/General , PTL/Pathologic , SPC/Special , SPF/Specific)
				urine_PGCD = String.valueOf(resultList.get(i).get("PGCD")); //프로파일코드?
				urine_PGNM = String.valueOf(resultList.get(i).get("PGNM")); //프로파일명?
				urine_PG_TIT = chgTitle( String.valueOf(resultList.get(i).get("PGCD")).replaceAll(" ","") ); //결과지에 출력되는 타이틀변경
				urine_F010RNO = String.valueOf(resultList.get(i).get("F010RNO")); //폼 (ex : STI, ECA5, D, ARC, EAAX 등)
				urine_S018SYN = String.valueOf(resultList.get(i).get("S018SYN")); //단일출력 여부
				urine_F600HAK = String.valueOf(resultList.get(i).get("F600HAK")); //학부코드
				urine_S018RFN = "GENERAL-01"; //리포트 mrd 파일 명
				urine_P_HOS_GCD = urine_P_HOS_GCD + "||" + GCD; //병원검사코드(이미지 생성 실패시 사용)
				urine_P_GCD = urine_P_GCD + GCD; //리포트 툴에 넣을 파라미터 검사코드
				urine_BDT = String.valueOf(resultList.get(i).get("BDT")); //보고일자
				urine_CU_SE_GCD = String.valueOf(resultList.get(i).get("CU_SE_GCD")); //컬쳐 센시 묶음 검사코드
				urine_F100HOS = String.valueOf(resultList.get(i).get("F100HOS")); //병원코드
				urine_F600CHR = String.valueOf(resultList.get(i).get("F600CHR")); //차트번호
				
				// 이미지에 종목이 2개 이상일 경우, 검사명 뒤에 '외' 문구열을 추가한다.
				if(urine_P_GCD.trim().length() < 6){
					urine_ADD_CD_NM = "";
				}else{
					urine_ADD_CD_NM = "외";
				}
				
				// 아래 종목들일 경우, 하드코딩으로 값을 셋팅한다.
				// urine_F008GCD : 이미지 출력 검사코드
				// urine_F010FKN : 이미지 출력 검사명?
				// urine_P_F010FKN : 이미지 출력 검사명?
				if(GCD.indexOf("00799") > -1){urine_F008GCD = "00799";urine_F010FKN = "Urine(4)";urine_P_F010FKN = "Urine(4)";}
				else if(GCD.indexOf("00802") > -1){urine_F008GCD = "00802";urine_F010FKN = "R-Urine(10)";urine_P_F010FKN = "Urine(10)";}
				else if(GCD.indexOf("00806") > -1){urine_F008GCD = "00806";urine_F010FKN = "R-Urine(7)";urine_P_F010FKN = "Urine(7)";}
				else if(GCD.indexOf("00822") > -1){urine_F008GCD = "00822";urine_F010FKN = "Microscopic (Flow Cytometry)";urine_P_F010FKN = "Microscopic (Flow Cytometry)";}
				
				urine_gcd_check = true;
				
				// 이미지 개별 생성하는 경우, 그리고 접수번호에 접수된 종목이 마지막인지 체크한다.
				if("1".equals(imgMenuGubun) && i != r_len-1){
					continue;
				}else if("2".equals(imgMenuGubun) && i != r_len-1 ){ // 이미지 일괄 생성하는 경우, 그리고 접수번호에 접수된 종목이 마지막인지 체크한다. 
					GCD = String.valueOf(resultList.get(i+1).get("CU_SE_GCD")); // 마지막이 아닌 경우, 예외처리된 종목(00799/00802/00806/00822) 4개는 생성되지 않고 건너뛰도록 한다.
					//i++;
				}
				
				//urine_gcd_check = true;
				   
	
	
			}
			
			
			//==================================================================================================================================================================================
			// 1. 1번째 종목은 아래 로직에 의해 이미지가 생성된다. (이미지 개별/일괄 생성 동일)
			// (폼이 동일한 경우 묶음처리를 위해 1번째 종목과 N번째 종목의 이미지가 생성되는 로직을 나누어 놓았다.)
			//==================================================================================================================================================================================
			
			//결과지 그룹 시작
			if(i == 0
				&& 
				(
					(!"00799".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						&& !"00802".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						&& !"00806".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						&& !"00822".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					)
					//|| ("14054".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					|| ("URIN_MICRO".equals(String.valueOf(urin_micro_13_form))
						&& ("00799".equals(String.valueOf(resultList.get(i).get("F600GCD")))
							|| "00802".equals(String.valueOf(resultList.get(i).get("F600GCD")))
							|| "00806".equals(String.valueOf(resultList.get(i).get("F600GCD")))
							|| "00822".equals(String.valueOf(resultList.get(i).get("F600GCD"))))
						)
				)
			){
				
				//row 생성
				rowMap = new HashMap<String, String>();
				//수진자명
				rowMap.put("F100NAM", String.valueOf(resultList.get(i).get("F100NAM")));
				//접수일자
				rowMap.put("F600DAT", String.valueOf(resultList.get(i).get("F600DAT")));
				//접수번호
				rowMap.put("F600JNO", String.valueOf(resultList.get(i).get("F600JNO")));
				//검사번호
				rowMap.put("F600GCD", GCD);
				//병원별 검사코드
				rowMap.put("HOS_GCD", HOS_GCD);
				//이미지 출력 검사코드
				rowMap.put("F008GCD", F008GCD);
				//검사명
				rowMap.put("F010FKN", F010FKN);
				//양식
				rowMap.put("S011RCL", S011RCL);
				//프로파일코드
				rowMap.put("PGCD", String.valueOf(resultList.get(i).get("PGCD")));
				//프로파일명
				rowMap.put("PGNM", String.valueOf(resultList.get(i).get("PGNM")));
				//결과지 프로파일에 따른 타이틀명 
				rowMap.put("PG_TIT", chgTitle( String.valueOf(resultList.get(i).get("PGCD")).replaceAll(" ","") ) );	
				//폼
				rowMap.put("F010RNO", String.valueOf(resultList.get(i).get("F010RNO")));
				
				// ^D: 문자열 때문에 파일명 배열에 담는다. 
				String[] FILE_NM_ARR;
				FILE_NM_ARR = FILE_NM.split("\\^D:");
				
				if("23979".equals(String.valueOf(resultList.get(i).get("F100HOS")))
					&& FILE_NM_ARR.length > 1
				){
					//파일명
					rowMap.put("FILE_NM", FILE_NM_ARR[0].replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null","") + FILE_NM_ARR[1]);
				}else{
					rowMap.put("FILE_NM", FILE_NM_ARR[0].replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null",""));
				}				
		
				//검사명
				rowMap.put("P_F010FKN", String.valueOf(resultList.get(i).get("F010FKN")));
				//단일출력 여부
				rowMap.put("S018SYN", String.valueOf(resultList.get(i).get("S018SYN")));
				//학부
				rowMap.put("F600HAK", String.valueOf(resultList.get(i).get("F600HAK")));
				//리포트 mrd 파일 명
				rowMap.put("S018RFN", String.valueOf(resultList.get(i).get("S018RFN")));
				
				//==================================================================================================================================================================================
				// 2021-12-30
				// 결과지 양식 변경으로 인해 기준일(2021-12-29) 이전의 결과지는 예전 양식지로 출력되어야 함.
				//==================================================================================================================================================================================
				if(
					"ECA5".equals(String.valueOf(resultList.get(i).get("F010RNO")))
					&& Integer.parseInt(String.valueOf(resultList.get(i).get("F100DAT")) == "null"?"0":String.valueOf(resultList.get(i).get("F100DAT"))) < 20211229
					&& Integer.parseInt(String.valueOf(resultList.get(i).get("F100DAT")) == "null"?"0":String.valueOf(resultList.get(i).get("F100DAT"))) > 0
				){
					if("71601".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
						rowMap.put("S018RFN",  "77-ECA5-TMP-20");
					}
					if("81359".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
						rowMap.put("S018RFN",  "77-ECA5-TMP-01");
					}
				}
				
				/**
				 * PDF 일 경우 별지참조
				 */
				if("별지참조".equals(String.valueOf(resultList.get(i).get("F600CHR")))
						// 결과값이 별지참조로 입력은 되지만 pdf 양식의 mrd를 이용하지 않는 예외 경우이다.
						&& !"EEP4".equals(String.valueOf(resultList.get(i).get("F010RNO")))
						&& !"EEP2".equals(String.valueOf(resultList.get(i).get("F010RNO")))
				){
					isPDFFirst = true;
					//리포트 mrd 파일 명, PDF일 경우 무조건 78-STI-TMJ-01 템플릿
					rowMap.put("S018RFN",  "78-STI-TMJ-01");
				}
				
				/**
				 * 변경자 : cjw
				 * 변경일자 : 2019-04-08
				 * 서로 다른 2개 MRD가 하나의 접수번호에 등록되었을 경우,
				 * 2개 모두 MWS002M@ 테이블에 등록된 프로파일과 종목이 일치하는 템플릿이 존재하는 경우, 조회된 템플릿으로 강제 변경.
				 */
				if(!"".equals(String.valueOf(resultList.get(i).get("CHN_TEMPLATE")).trim()) 
						&& resultList.get(i).get("CHN_TEMPLATE") != null 
						&& isChgTemp){ // MWS002M@ 테이블에 프로파일 등록되어져 있는지 체크	
					//리포트 mrd 파일 명
					rowMap.put("S018RFN",  isChgTempNm); // MWS002M@ 테이블에 프로파일 등록된 경우, 셋팅된 템플릿 가져오기
					isChgTemp = false;
				} 
				
				/**
				 * 72195가 77-STI-TMP-01 인 다른 검사코드와 같이 나올경우 77-STI-TMP-01 템플릿으로 같이 사용한다.
				 */
				if("72195".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
					for(int j=0; j<r_len; j++ ){
						// "77-STI-TMP-01" 존재 할 경우
						if("77-STI-TMP-01".equals(String.valueOf(resultList.get(j).get("S018RFN")))){
							rowMap.put("S018RFN", String.valueOf(resultList.get(j).get("S018RFN")));
							break;
						}
					}
				}
				/**
				 * 72190가 72213와 같이 나오면 72213의 템플릿인 77-STI-TMP-05 템플릿으로 같이 사용한다.
				 */
				else if("72190".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
					for(int j=0; j<r_len; j++ ){
						// 72213 존재 할 경우
						if("72213".equals(String.valueOf(resultList.get(j).get("F600GCD")))){
							//72190가 처음으로 나오고 72213가 뒤에 존재 할 경우 파일명만 
							rowMap.put("S018RFN", String.valueOf(resultList.get(j).get("S018RFN")));
							is72190_72213 = true;
							break;
						}
					}
				}
				
				/**
				 * 71256이 71313 STI 7종과 같이 나오면 71313의 템플릿인 77-STI-TMP-01 템플릿으로 같이 사용한다.
				 */
				else if("71256".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
					for(int j=0; j<r_len; j++ ){
						// 72213 존재 할 경우
						if("71313".equals(String.valueOf(resultList.get(j).get("F600GCD")))){
							//71256가 처음으로 나오고 71313가 뒤에 존재 할 경우 파일명만 
							rowMap.put("S018RFN", String.valueOf(resultList.get(j).get("S018RFN")));
							is71256_71313 = true;
							break;
						}
					}
				}

				//리포트 툴에 넣을 파라미터 검사코드
				rowMap.put("P_GCD", String.valueOf(resultList.get(i).get("F600GCD")));
				rowMap.put("F008GCD", String.valueOf(resultList.get(i).get("F008GCD")));
				//병원검사코드(이미지 생성 실패시 사용)
				rowMap.put("P_HOS_GCD", String.valueOf(resultList.get(i).get("HOS_GCD")));

				// STIP 폼인 액상세포를 포함한 종목이 접수되면 특정 폼으로 강제로 변경시킨다.
				// ex) 61028, 71313, 72185 
				if("STIP".equals(String.valueOf(resultList.get(i).get("F010RNO")))
				){
					if("71313".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						|| "72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					){
						continue;
					}else if("A9230".equals(String.valueOf(resultList.get(i).get("PGCD")))){
						rowMap.put("S018RFN", "77-STIP-TMP-02");
					}else if("A9240".equals(String.valueOf(resultList.get(i).get("PGCD")))){
						rowMap.put("S018RFN", "77-STIP-TMP-03");
					}else{
						//rowMap.put("S018RFN", "77-STIP-TMP-01");
						if(!"".equals(String.valueOf(resultList.get(i).get("CHN_TEMPLATE")).trim()) 
								&& resultList.get(i).get("CHN_TEMPLATE") != null 
						){ // MWS002M@ 테이블에 프로파일 등록되어져 있는지 체크	
							//리포트 mrd 파일 명
							rowMap.put("S018RFN",  isChgTempNm); // MWS002M@ 테이블에 프로파일 등록된 경우, 셋팅된 템플릿 가져오기
						}else{
							rowMap.put("S018RFN", "77-STIP-TMP-01");
						}
					}
					
					// ▽이미지 꼬릿말이 미생물 학부 정보로 출력되어야 하기 때문에 추가했으나,
					// ▽이미지 일괄 내려받기 할 경우, 61028 종목이 수신여부값이 O 값으로 변경되지 않기 때문에 주석처리함. -20190724-
					//rowMap.put("P_GCD", rowMap.get("P_GCD").replaceAll("61028", ""));
				}/*else{
					rowMap.put("S018RFN", String.valueOf(resultList.get(i).get("S018RFN")));
				}*/
				
				//컬쳐 센시 묶음 검사코드
				rowMap.put("CU_SE_GCD", String.valueOf(resultList.get(i).get("CU_SE_GCD")));
				//보고일자
				rowMap.put("BDT", String.valueOf(resultList.get(i).get("BDT")));
				rowMap.put("ADD_CD_NM","");
				//강제로 결과지 변경할 경우
				rowMap.put("CHN_TEMPLATE", String.valueOf(resultList.get(i).get("CHN_TEMPLATE")));				
				//이미지퀄리티300DPI 출력해야하는 병원
				rowMap.put("DPI300_HOS", String.valueOf(resultList.get(i).get("DPI300_HOS")));
				//이미지 결과 페이지별로 개별 생성되어야 하는 병원
				rowMap.put("IMG_EACH_HOS", String.valueOf(resultList.get(i).get("IMG_EACH_HOS")));
				
				// 2019.10.29
				// '보고예정:YYYYMMDD 추후보고'라는 문구의 결과가 입력되는 경우,
				// 즉, 추후보고 상태인 경우를 포함해 결과가 완료가 아닌 상태인 경우에는 이미지결과를 생성하는 목록에서 제외되도록 한다.
				// ex) F600C03(Y), F600C04(C)인 경우로 인해 처리함.
				if(!"Y".equals(String.valueOf(resultList.get(i).get("FST_MAKE")))
					// 2022.12.12 - 29844(제천서울병원)인 경우, 미생물 결과가 최종보고가 아니면 이미지 생성 안되도록 처리 요청함. (요청부서:운영팀)
					// 미생물 검사결과가 최종보고가 아니면서, ex)중간보고
					// 코드관리(MICRO_MID_RESULT_EXC)에 등록된 병원인 경우,
					// 이미지결과 생성되지 않도록 처리
					|| ("미생성병원".equals(String.valueOf(resultList.get(i).get("MICROBE_RESULT_HOS"))) 
							&& "BMB".equals(String.valueOf(resultList.get(i).get("F010RNO")).trim())
							&& !"최종보고".equals(String.valueOf(resultList.get(i).get("MICROBE_RESULT_STS")))
						)
				){
					continue;
				}else{
					rtnList.add(rowMap);
				}
				
				logger.debug("====================row map["+i+"]==============================");
				logger.debug(rowMap.toString());

			}else{
				//==================================================================================================================================================================================
				// 2. 종목이 2개 이상인 경우, 2번째 종목부터는 아래 로직에 의해 이미지가 생성된다. (이미지 개별/일괄 생성 동일)
				//==================================================================================================================================================================================
				
				isGroup = false;
				/**
				 * 프로파일별 묶음 처리 시작
				 */
				
				if(
					(
						"00799".equals(GCD)||"00802".equals(GCD)||"00806".equals(GCD)||"00822".equals(GCD)
					) && 
					!"URIN_MICRO".equals(String.valueOf(urin_micro_13_form))
				){
					if("1".equals(imgMenuGubun)){
						isGroup = true;
					}
					// 마리본산부인과 - 이미지결과받기 메뉴에서
					// 유린,마이크로 종목만 선택 후, 선택받기 할 경우
					// 선택 받기가 되지 않음.
					// 병원 : 26241, 마리본산부인과, 접수일자 : 2021.09.03, 접수번호 : 5414
					/*else if("2".equals(imgMenuGubun)){
						//isGroup = true;
						break;
					}*/
					
					//break;
					//continue;
				}
				
				
				//검사코드 41121과 42000이 포함된 프로파일 A1008,90027,99935,99950,99952 일경우 결과지 41121과 42000 만 나누기
				if("A1008".equals(String.valueOf(resultList.get(i).get("PGCD")))   
						//|| "90027".equals(String.valueOf(resultList.get(i).get("PGCD"))) 
						//|| "99935".equals(String.valueOf(resultList.get(i).get("PGCD"))) 
						//|| "99950".equals(String.valueOf(resultList.get(i).get("PGCD"))) 
						//|| "99952".equals(String.valueOf(resultList.get(i).get("PGCD"))) 
					){
					//특수 용지로 따로 출력
					if("41121".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
						isGroup = false;
					}
					else if("42000".equals(String.valueOf(resultList.get(i).get("F600GCD"))) ){
						GCD = "A1008";
						F010FKN = String.valueOf(resultList.get(i).get("PGCD_NM")) ;
						is42000 = true;
						if(!isA1008Group){
							isGroup = false;
						}else{
							isGroup = true;
							for( HashMap<String, String> t_hm : rtnList){
								if( "A1008".equals(t_hm.get("F600GCD")) ){
									t_hm.put("F010FKN", F010FKN);
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + String.valueOf(resultList.get(i).get("F600GCD")));
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + String.valueOf(resultList.get(i).get("F008GCD")));
									t_hm.put("S018RFN",  String.valueOf(resultList.get(i).get("S018RFN")));
								}
							}
						}
						
					} else {
						if(!is42000){	
							isGroup = false;
							isA1008Group = true;
							GCD = "A1008";
						}else{
							isGroup = true;
							for( HashMap<String, String> t_hm : rtnList){
								if( "A1008".equals(t_hm.get("F600GCD")) ){
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + String.valueOf(resultList.get(i).get("F008GCD")));
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + String.valueOf(resultList.get(i).get("F600GCD")));
								}
							}
						}
					}
				
					/**
					 * 프로파일별 묶음 처리 끝
					 */
					
				}else{
					
					/**
					 * 일반 묶음 시작
					 */
					
					//이미 리스트 업 된 결과지 출력 대상과 비교
					for( HashMap<String, String> t_hm : rtnList){
	
//						logger.debug("S018SYN = " + String.valueOf(resultList.get(i).get("S018SYN")));
//						logger.debug("F600GCD = " + String.valueOf(resultList.get(i).get("F600GCD"))+"code");
//						logger.debug("S011RCL = " + S011RCL);
						//if("N".equals(t_hm.get("S018SYN"))){
						
						if("72190".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
							
							boolean isBreak = false;
							
							int cnt_row = 0;
							
							for(int j=0; j<r_len; j++ ){
								// 72213 존재 할 경우
//								logger.debug("F600GCD=="+String.valueOf(resultList.get(j).get("F600GCD")));
								if("72213".equals(String.valueOf(resultList.get(j).get("F600GCD")))){
									////System.out.println("++++++++++++++isChg72190Temp+++++++++++++++++++");
									isChg72190Temp = true;
									
									is72190_72213 = true;
									
									for( HashMap<String, String> b_t_hm : rtnList){
//										logger.debug(b_t_hm.toString());
										
										if( b_t_hm.get("P_GCD").indexOf("72213") > -1  ){
											b_t_hm.put("P_F010FKN",  b_t_hm.get("P_F010FKN") +"||"  + F010FKN );	
											b_t_hm.put("P_HOS_GCD",  b_t_hm.get("P_HOS_GCD") +"||"  + HOS_GCD );
											b_t_hm.put("P_GCD",  b_t_hm.get("P_GCD") + GCD);
											
											b_t_hm.put("ADD_CD_NM","외");
											b_t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

											cnt_row = 1;
											isBreak = true;
											isGroup = true;
											break;
										}
										////System.out.println(t_hm.toString());
									}
									
								}
								
							}

							////System.out.println("++++++++++++++cnt_row+++++++++++++++++++="+cnt_row);
							
							if(isChg72190Temp && cnt_row < 1){
								//72195가 처음으로 나오고 77-STI-TMP-01가 뒤에 존재 할 경우 파일명만 수정 isGroup = false 이므로 새로 add 됨
								////System.out.println("+++++++++++++++++++++++++++++++++");
								resultList.get(i).put("S018RFN", "77-STI-TMP-05") ;
								isGroup = false;
							}
							
							if(isBreak){
								//다음 검사 코드로
								break;
							}
						}
						/**
						 * 71256이 71313 STI 7종과 같이 나오면 71313의 템플릿인 77-STI-TMP-01 템플릿으로 같이 사용한다.
						 */
						else if("71256".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
							
							boolean isBreak = false;
							
							int cnt_row = 0;
							
							for(int j=0; j<r_len; j++ ){
								// 71313 존재 할 경우
//								logger.debug("F600GCD=="+String.valueOf(resultList.get(j).get("F600GCD")));
								if("71313".equals(String.valueOf(resultList.get(j).get("F600GCD")))){
									////System.out.println("++++++++++++++isChg71256Temp+++++++++++++++++++");
									isChg71256Temp = true;
									
									is71256_71313 = true;
									
									for( HashMap<String, String> b_t_hm : rtnList){
//										logger.debug(b_t_hm.toString());
										
										if( b_t_hm.get("P_GCD").indexOf("71313") > -1  ){
											b_t_hm.put("P_F010FKN",  b_t_hm.get("P_F010FKN") +"||"  + F010FKN );	
											b_t_hm.put("P_HOS_GCD",  b_t_hm.get("P_HOS_GCD") +"||"  + HOS_GCD );
											b_t_hm.put("P_GCD",  b_t_hm.get("P_GCD") + GCD);
											
											b_t_hm.put("ADD_CD_NM","외");
											b_t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

											cnt_row = 1;
											isBreak = true;
											isGroup = true;
											break;
										}
										////System.out.println(t_hm.toString());
									}
									
								}
								
							}
							
							////System.out.println("++++++++++++++cnt_row+++++++++++++++++++="+cnt_row);
							
							if(isChg71256Temp && cnt_row < 1){
								//71256가 처음으로 나오고 77-STI-TMP-01가 뒤에 존재 할 경우 파일명만 수정 isGroup = false 이므로 새로 add 됨
								////System.out.println("+++++++++++++++++++++++++++++++++");
								resultList.get(i).put("S018RFN", "77-STI-TMP-01") ;
								isGroup = false;
							}
							
							if(isBreak){
								//다음 검사 코드로
								break;
							}
						}
						
						/**
						 * PDF 일 경우 별지참조
						 */
						else if("별지참조".equals(String.valueOf(resultList.get(i).get("F600CHR")))){
							if(isPDFFirst){
								
								//리포트 mrd 파일 명, PDF일 경우 무조건 78-STI-TMJ-01 템플릿
								
								for( HashMap<String, String> b_t_hm : rtnList){
//									logger.debug(b_t_hm.toString());
									
									// 2020.01.03 - 결과지출력을 눌렀을때, 이미지결과 리스트가 출력안되는 문제 때문에 수정함.
									// ex) 접수일자:20191226, 접수번호:1636, 검사코드:64598,64599
									//if( b_t_hm.get("F600CHR").indexOf("별지참조") > -1  ){
									if( b_t_hm.get("S018RFN").indexOf("78-STI-TMJ-01") > -1  ){
										b_t_hm.put("P_F010FKN",  b_t_hm.get("P_F010FKN") +"||"  + F010FKN );
										b_t_hm.put("P_HOS_GCD",  b_t_hm.get("P_HOS_GCD") +"||"  + HOS_GCD );
										b_t_hm.put("P_GCD",  b_t_hm.get("P_GCD") + GCD);

										//중복 제거
										b_t_hm.put("F010RNO",t_hm.get("F010RNO").replaceAll(String.valueOf(resultList.get(i).get("F010RNO")), "")  + String.valueOf(resultList.get(i).get("F010RNO")));
										b_t_hm.put("ADD_CD_NM","외");
										b_t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

										isGroup = true;
										break;
									}
//									System.out.println(t_hm.toString());
								}
								//다음 검사 코드로
								break;
								
							}else{
								isPDFFirst = true;
								isGroup = false;
							}
						}
						
						/**
						 * STIP 폼인 액상세포를 포함한 종목이 접수되면 특정 폼으로 강제로 변경시킨다.
						 * ex) 61028, 71313, 72185
						 * 72185 종목의 경우, 단독 출력 설정이 되어 있기때문에
						 * STIP 폼이면서 종목이 72185인 경우는 이미지가 생성되지 않도록 제외처리 한다.
						 */
						else if(String.valueOf(resultList.get(i).get("PGCD")) != null 
								&& !"".equals(String.valueOf(resultList.get(i).get("PGCD")))
								&& !"null".equals(String.valueOf(resultList.get(i).get("PGCD")))
								&& t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
								&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
								&& "STIP".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
								&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
								&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){

							////System.out.println("sti 000 tm PRO GCD :: " + String.valueOf(resultList.get(i).get("PGCD")) + "******");
							////System.out.println("sti 000 tm GCD :: " + t_hm.get("P_GCD"));
							////System.out.println("sti 000 GCD :: " + GCD);
							
							t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
							t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
							//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
							t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
							
							//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
							// 71256이 71313 STI 7종, 61028 액상세포검사와 같이 나오면 71313의 템플릿인 77-STI-TMP-01 템플릿으로 같이 사용한다.
							// mrd 주쿼리에 템플릿이 77-STI-TMP-01 템플릿으로 하드코딩되어져 있음.(프로파일 B9028로 접수되는 경우만 해당되기 때문...)
							if("61028".equals(String.valueOf(resultList.get(i).get("F600GCD")))
								||"71313".equals(String.valueOf(resultList.get(i).get("F600GCD")))
								||"72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))
							  ){
								if(is72185){
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + "72185");
								}
							}
							
							t_hm.put("ADD_CD_NM","외");
							t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

							isGroup = true;
							//다음 검사 코드로
							break;
						}
						
						
						/**
						 * 그룹을 묶는 경우
						 */
						else if("N".equals(String.valueOf(resultList.get(i).get("S018SYN")))){
							
							if( i == 1 &&  "Y".equals(String.valueOf(resultList.get(0).get("S018SYN")))
								// 2020.11.20 추가 (일반결과가 한접수번호에 의뢰되는 경우, 파일명때문에 병원차트에 MTB-PCR 종목 이미지가 연동안됨.) 
								|| ("21225".equals(String.valueOf(resultList.get(i).get("F100HOS"))) && "72227".equals(String.valueOf(resultList.get(i).get("F600GCD"))))
							){
//								logger.debug("0 th:: S018SYN=" +String.valueOf(resultList.get(0).get("S018SYN")));
								//단일 출력 일경우
								isGroup = false;
							}else{
								
//								logger.debug("Gneral----------------------------------");
//		  						logger.debug("F600DAT tm ="+t_hm.get("F600DAT") + ", rst ="+ resultList.get(i).get("F600DAT")+"//");
//		  						logger.debug("F600JNO tm ="+t_hm.get("F600JNO") + ", rst ="+ resultList.get(i).get("F600JNO")+"//");
//		  						logger.debug("S018SYN tm =N, rst ="+ resultList.get(i).get("S018SYN")+"//");
//		  						logger.debug("F010RNO tm ="+t_hm.get("F010RNO") + ", rst ="+ resultList.get(i).get("F010RNO")+"//");
//		  						logger.debug("S018RFN tm ="+t_hm.get("S018RFN") + ", rst ="+ resultList.get(i).get("S018RFN")+"//");
								
								//0. General일 경우 무조건 하나로 묶기
								if( "GNR".equals(t_hm.get("S011RCL")) && "GNR".equals(S011RCL)
									&& ((!"00799".equals(GCD)&&!"00802".equals(GCD)&&!"00806".equals(GCD)&&!"00822".equals(GCD)) // 다른 일반결과지 양식과 함께 묶여서 출력되지 않도록 하기 위함. ex) 접수일자 : 2020.03.23, 접수번호 : 6654
											|| "URIN_MICRO".equals(String.valueOf(urin_micro_13_form))
										)
								){
									
									if( t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
											&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO"))) 
											&& "N".equals(t_hm.get("S018SYN")) 
											&& (!"28664".equals(String.valueOf(resultList.get(i).get("F100HOS"))) && !"81371".equals(String.valueOf(resultList.get(i).get("F600GCD"))))	// 2020.05.19
																																														// 해운대부민병원의 경우, 41027+81371 함께 접수되는 경우
																																														// 41027 병원코드가 파일명으로 생성되지 않고, 81371 병원코드가 파일명으로 생성되어야 한다는 요청으로 인해 조건 추가함. 
																																														// - 변경전 : 00072291_송영수_2005140499_LZC3800_01.jpg
																																														// - 변경후 : 00072291_송영수_2005140499_XCX131_01.jpg
									) {
												t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
												t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
												t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
												
												//중복 제거
												t_hm.put("F010RNO",t_hm.get("F010RNO").replaceAll(String.valueOf(resultList.get(i).get("F010RNO")), "")  + String.valueOf(resultList.get(i).get("F010RNO")));
												t_hm.put("ADD_CD_NM","외");
												t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

												isGroup = true;
												//다음 검사 코드로
												break;
									}else{
										//단일 출력 일경우
										isGroup = false;
									}
								}
								//0-1. General 이지만 폼이 BPA로 변경된 경우 템플릿 PATHOLOGIC-01로 변경 후 같은 PATHOLOGIC-01끼리 합함
								else if( t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "BPA".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
										&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
										&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){
		
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									//템플릿 파일 명
									t_hm.put("S018RFN",  "PATHOLOGIC-01");
									
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

		
									isGroup = true;
									//다음 검사 코드로
									break;
								}
								
								//0-2. General 이지만 폼이 C로 변경된 경우 템플릿 95-STI_PSTS_C로 변경 후 같은 PATHOLOGIC-01끼리 합함
								else if( t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "C".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
										&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
										&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){
		
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									//템플릿 파일 명
									t_hm.put("S018RFN",  "95-STI_PSTS_C");
									
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

		
									isGroup = true;
									//다음 검사 코드로
									break;
								}
								
								//1. NSR일 경우 프로파일 명이 같으면 하나로 묶음 ==> General이 같은 프로파일로 묶여서 리포트 템플릿이 다르지만 하나로 묶여서 리포트 출력이 되기 때문..
								else if( t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "NSR".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
										&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
										&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){
		
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

									isGroup = true;
									//다음 검사 코드로
									break;
								}
		
								//1-1. STI일 경우 프로파일 명이 같으면 하나로 묶음 ==> General이 같은 프로파일로 묶여서 리포트 템플릿이 다르지만 하나로 묶여서 리포트 출력이 되기 때문..
								else if(String.valueOf(resultList.get(i).get("PGCD")) != null 
										&& !"".equals(String.valueOf(resultList.get(i).get("PGCD")))
										&& !"null".equals(String.valueOf(resultList.get(i).get("PGCD")))
										&& t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "STI".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
										&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
										&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){

									////System.out.println("sti 000 tm PRO GCD :: " + String.valueOf(resultList.get(i).get("PGCD")) + "******");
									////System.out.println("sti 000 tm GCD :: " + t_hm.get("P_GCD"));
									////System.out.println("sti 000 GCD :: " + GCD);
									
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									
									//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
									if("74011".equals(String.valueOf(resultList.get(i).get("F600GCD")))
										||"74012".equals(String.valueOf(resultList.get(i).get("F600GCD")))
										||"74017".equals(String.valueOf(resultList.get(i).get("F600GCD")))
									  ){
										if(is72185){
											t_hm.put("P_GCD",  t_hm.get("P_GCD") + "72185");
										}
									}
									
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

									isGroup = true;
									//다음 검사 코드로
									break;
								}
								
								//1-2. STI일 경우 프로파일 명이 같으면 하나로 묶음 ==> General이 같은 프로파일로 묶여서 리포트 템플릿이 다르지만 하나로 묶여서 리포트 출력이 되기 때문..
								else if(String.valueOf(resultList.get(i).get("PGCD")) != null 
										&& !"".equals(String.valueOf(resultList.get(i).get("PGCD")))
										&& !"null".equals(String.valueOf(resultList.get(i).get("PGCD")))
										&& t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "STIP".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
										&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
										&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){

									////System.out.println("sti 000 tm PRO GCD :: " + String.valueOf(resultList.get(i).get("PGCD")) + "******");
									////System.out.println("sti 000 tm GCD :: " + t_hm.get("P_GCD"));
									////System.out.println("sti 000 GCD :: " + GCD);
									
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									
									//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
									// 71256이 71313 STI 7종, 61028 액상세포검사와 같이 나오면 71313의 템플릿인 77-STI-TMP-01 템플릿으로 같이 사용한다.
									// mrd 주쿼리에 템플릿이 77-STI-TMP-01 템플릿으로 하드코딩되어져 있음.(프로파일 B9028로 접수되는 경우만 해당되기 때문...)
									if("61028".equals(String.valueOf(resultList.get(i).get("F600GCD")))
										||"71313".equals(String.valueOf(resultList.get(i).get("F600GCD")))
										||"72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))
									  ){
										if(is72185){
											t_hm.put("P_GCD",  t_hm.get("P_GCD") + "72185");
										}
									}
									
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

									isGroup = true;
									//다음 검사 코드로
									break;
								}
								
								//1-3. ECA6일 경우 프로파일 명이 같으면 하나로 묶음 ==> 같은 폼이면서 프로파일명이 동일한 경우 묶는다.
								else if( t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "ECA6".equals(String.valueOf(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ",""))) 
										&& t_hm.get("F010RNO").equals( String.valueOf(String.valueOf(resultList.get(i).get("F010RNO"))) )
										&& t_hm.get("PGCD").equals(String.valueOf(resultList.get(i).get("PGCD")))){
		
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN);
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD);
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

									isGroup = true;
									//다음 검사 코드로
									break;
								}
		
								//2. 폼이 다르지만 묶기
								else if( t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& (( "AHE".equals(t_hm.get("F010RNO").replaceAll(" ","")) && "ANS".equals(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ","")) )
												|| ( "ARC".equals(t_hm.get("F010RNO").replaceAll(" ","")) && "AHE".equals(String.valueOf(resultList.get(i).get("F010RNO")).replaceAll(" ","")))) ){
		
									t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN );
									t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD );
									t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD );
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									//중복 제거
									t_hm.put("F010RNO",t_hm.get("F010RNO").replaceAll(String.valueOf(resultList.get(i).get("F010RNO")), "")  + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);

									isGroup = true;
									//다음 검사 코드로
									break;
								}
								//3. 그룹/폼/템플릿이 같은 것 묶기
								else if(t_hm.get("F600DAT").equals(String.valueOf(resultList.get(i).get("F600DAT")))
										&& t_hm.get("F600JNO").equals(String.valueOf(resultList.get(i).get("F600JNO")))
										&& "N".equals(String.valueOf(String.valueOf(resultList.get(i).get("S018SYN")))) 
										&& "N".equals(String.valueOf(t_hm.get("S018SYN")))
										&& t_hm.get("F010RNO").equals(String.valueOf(resultList.get(i).get("F010RNO"))) 
										&& t_hm.get("S018RFN").equals(String.valueOf(resultList.get(i).get("S018RFN"))) ){

									////System.out.println("000 GCD :: " + GCD);
									////System.out.println("CU_SE_GCD :: " + String.valueOf(resultList.get(i).get("CU_SE_GCD")));
		
									//컬쳐 센시 구분에 의한 검사코드 변경 켤처 코드와 센시코드를 합한 검사코드로 변경
									if(String.valueOf(resultList.get(i).get("CU_SE_GCD")) != null && !"".equals(String.valueOf(resultList.get(i).get("CU_SE_GCD"))) && !"null".equals(String.valueOf(resultList.get(i).get("CU_SE_GCD")))){
										t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||" + F010FKN );						
										t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||" + HOS_GCD );		
										//repplace로 검사 코드 중복 방지 
										//logger.debug("CU_SE_GCD="+String.valueOf(resultList.get(i).get("CU_SE_GCD")));
										if(resultList.get(i).get("CU_SE_GCD") != null 
												&&  !"".equals(String.valueOf(resultList.get(i).get("CU_SE_GCD")))
												&&  !"null".equals(String.valueOf(resultList.get(i).get("CU_SE_GCD"))) ){
											t_hm.put("P_GCD",  t_hm.get("P_GCD").replace(String.valueOf(resultList.get(i).get("CU_SE_GCD")).substring(0,5),"").replace(String.valueOf(resultList.get(i).get("CU_SE_GCD")).substring(5,10), "")  + String.valueOf(resultList.get(i).get("CU_SE_GCD")));
											
											if(String.valueOf(resultList.get(i).get("CU_SE")) != null 
													&& !"".equals(String.valueOf(resultList.get(i).get("CU_SE")))
													 && !"null".equals(String.valueOf(resultList.get(i).get("CU_SE")))
													 && "CU".equals(String.valueOf(resultList.get(i).get("CU_SE")))){
												//리포트 mrd 파일 명은 culture인 검사코드의 mrd 파일로 수정 및 Culture의 검사코드로 변경
												t_hm.put("S018RFN", String.valueOf(resultList.get(i).get("S018RFN")));
												t_hm.put("F600GCD", String.valueOf(resultList.get(i).get("F600GCD")));
											}
											
										}else{
											t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
										}
									}else{
										////System.out.println("111 GCD :: " + GCD);
										t_hm.put("P_F010FKN",  t_hm.get("P_F010FKN") +"||"  + F010FKN );	
										t_hm.put("P_HOS_GCD",  t_hm.get("P_HOS_GCD") +"||"  + HOS_GCD );
										t_hm.put("P_GCD",  t_hm.get("P_GCD") + GCD);
									}
									
									//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
									if("74011".equals(String.valueOf(resultList.get(i).get("F600GCD")))
										||"74012".equals(String.valueOf(resultList.get(i).get("F600GCD")))
										||"74017".equals(String.valueOf(resultList.get(i).get("F600GCD")))
									  ){
										if(is72185){
											t_hm.put("P_GCD",  t_hm.get("P_GCD") + "72185");
										}
									}
									//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
									t_hm.put("ADD_CD_NM","외");
									t_hm.put("F008GCD",  t_hm.get("F008GCD") + F008GCD);									
									
		
									isGroup = true;
									//다음 검사 코드로
									break;
								}
							}
						}else{
							//단일 출력 일경우
							isGroup = false;
						}
					}
				}
				
				//묶인 것 없을 경우 새로운 출력 리스트 생성
				if(!isGroup
					&& (
							(!"00799".equals(String.valueOf(resultList.get(i).get("F600GCD")))
								&& !"00802".equals(String.valueOf(resultList.get(i).get("F600GCD")))
								&& !"00806".equals(String.valueOf(resultList.get(i).get("F600GCD")))
								&& !"00822".equals(String.valueOf(resultList.get(i).get("F600GCD")))
							)
							|| ("URIN_MICRO".equals(String.valueOf(urin_micro_13_form))
								&& ("00799".equals(String.valueOf(resultList.get(i).get("F600GCD")))
									|| "00802".equals(String.valueOf(resultList.get(i).get("F600GCD")))
									|| "00806".equals(String.valueOf(resultList.get(i).get("F600GCD")))
									|| "00822".equals(String.valueOf(resultList.get(i).get("F600GCD"))))
								)
						)
				){
					
					logger.debug(i+"th:::     ");
					//row 생성
					rowMap = new HashMap<String, String>();
					//수진자명
					rowMap.put("F100NAM", String.valueOf(resultList.get(i).get("F100NAM")));
					//접수일자
					rowMap.put("F600DAT", String.valueOf(resultList.get(i).get("F600DAT")));
					//접수번호
					rowMap.put("F600JNO", String.valueOf(resultList.get(i).get("F600JNO")));
					//검사번호
					rowMap.put("F600GCD", GCD);
					//병원별검사코드
					rowMap.put("HOS_GCD", HOS_GCD);
					//이미지 출력 검사코드
					rowMap.put("F008GCD", F008GCD);
					//검사명
					rowMap.put("F010FKN", F010FKN);
					//양식
					rowMap.put("S011RCL", S011RCL);
					//프로파일코드
					rowMap.put("PGCD", String.valueOf(resultList.get(i).get("PGCD")));
					//프로파일명
					rowMap.put("PGNM", String.valueOf(resultList.get(i).get("PGNM")));
					//결과지 프로파일에 따른 타이틀명 
					rowMap.put("PG_TIT", chgTitle( String.valueOf(resultList.get(i).get("PGCD")).replaceAll(" ","") ) );					
					//폼
					rowMap.put("F010RNO", String.valueOf(resultList.get(i).get("F010RNO")));					
					
					// ^D: 문자열 때문에 파일명 배열에 담는다. 
					String[] FILE_NM_ARR;
					String FILE_NM_STR_01 = "";
					FILE_NM_ARR = FILE_NM.split("\\^D:");
					 
					
					if("23979".equals(String.valueOf(resultList.get(i).get("F100HOS")))
						&& FILE_NM_ARR.length > 1
					){
						//파일명
						FILE_NM_STR_01 = FILE_NM_ARR[0].replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null","") + FILE_NM_ARR[1];						 
					}else{
						FILE_NM_STR_01 = FILE_NM_ARR[0].replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null","");
					}
					//rowMap.put("FILE_NM",FILE_NM_STR);
					
					//==================================================================================================================================================================================
					//파일명이 동일한게 존재하는 경우, 끝에 구분값(seq) 문자를 추가한다.
					//==================================================================================================================================================================================
					String file_nm_all_list_01 = "";	// 1. 이미지 생성할 list에 데이터 중 파일명(FILE_NM)만 추출하여 전체 파일명 목록을 만든다.
					int file_nm_gubun_seq_01 = 0;		// 2. 이미지 파일명이 앞에 생성되는 파일명과 동일한 경우, 파일명 끝에 추가할 구분값(seq)
					
					for(HashMap<String, String> t_hm : rtnList){ // 3.이미지 생성할 list에 데이터를 hashmap 에 담는다. 각각의 값을 추출하여 사용하기 위해서.
						file_nm_all_list_01 += t_hm.get("FILE_NM") + "|";
					}
					
					/*System.out.println("### 변경전 ###");
					System.out.println("file_nm_all_list_01 : "+file_nm_all_list_01);
					System.out.println("FILE_NM_STR_01 : "+FILE_NM_STR_01);*/
					
					file_nm_gubun_seq_01 = 0;
					if(file_nm_all_list_01.indexOf(FILE_NM_STR_01) != -1){	// 4.이미지 생성할 list에 데이터 중 FILE_NM 값만 추출하여 이어 붙인 String에, 현재 생성하고자하는 이미지 파일명과 동일한 파일명이 존재하는지 체크!!
						file_nm_gubun_seq_01 ++; 						// 5. 구분값을 증가시킨다.
						FILE_NM_STR_01 += file_nm_gubun_seq_01;			// 6. 동일할 경우, 파일명끝에  구분값(seq)을 추가한다.
					}
					
					/*System.out.println("### 변경후 ###");
					System.out.println("file_nm_all_list_01 : "+file_nm_all_list_01);
					System.out.println("FILE_NM_STR_01 : "+FILE_NM_STR_01);*/
					
					rowMap.put("FILE_NM",FILE_NM_STR_01);
					
					//검사명
					rowMap.put("P_F010FKN", String.valueOf(resultList.get(i).get("F010FKN")));					
					//단일출력 여부
					rowMap.put("S018SYN", String.valueOf(resultList.get(i).get("S018SYN")));					
					//학부
					rowMap.put("F600HAK", String.valueOf(resultList.get(i).get("F600HAK")));
					//리포트 mrd 파일 명
					rowMap.put("S018RFN", String.valueOf(resultList.get(i).get("S018RFN")));
					
					//==================================================================================================================================================================================
					// 2021-12-30
					// 결과지 양식 변경으로 인해 기준일(2021-12-29) 이전의 결과지는 예전 양식지로 출력되어야 함.
					//==================================================================================================================================================================================
					if(
						"ECA5".equals(String.valueOf(resultList.get(i).get("F010RNO")))
						&& Integer.parseInt(String.valueOf(resultList.get(i).get("F100DAT")) == "null"?"0":String.valueOf(resultList.get(i).get("F100DAT"))) < 20211229
						&& Integer.parseInt(String.valueOf(resultList.get(i).get("F100DAT")) == "null"?"0":String.valueOf(resultList.get(i).get("F100DAT"))) > 0
					){
						if("71601".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
							rowMap.put("S018RFN",  "77-ECA5-TMP-20");
						}
						if("81359".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
							rowMap.put("S018RFN",  "77-ECA5-TMP-01");
						}
					}
					
					/**
					 * PDF 일 경우 별지참조
					 */
					if("별지참조".equals(String.valueOf(resultList.get(i).get("F600CHR")))
							// 결과값이 별지참조로 입력은 되지만 pdf 양식의 mrd를 이용하지 않는 예외 경우이다.
							&& !"EEP4".equals(String.valueOf(resultList.get(i).get("F010RNO")))
							&& !"EEP2".equals(String.valueOf(resultList.get(i).get("F010RNO")))
					){
						isPDFFirst = true;
						//리포트 mrd 파일 명, PDF일 경우 무조건 78-STI-TMJ-01 템플릿
						rowMap.put("S018RFN",  "78-STI-TMJ-01");
					}
					
					/**
					 * 변경자 : cjw
					 * 변경일자 : 2019-04-08
					 * 서로 다른 2개 MRD가 하나의 접수번호에 등록되었을 경우,
					 * 2개 모두 MWS002M@ 테이블에 등록된 프로파일과 종목이 일치하는 템플릿이 존재하는 경우, 조회된 템플릿으로 강제 변경.
					 */
					if(!"".equals(String.valueOf(resultList.get(i).get("CHN_TEMPLATE")).trim()) 
							&& resultList.get(i).get("CHN_TEMPLATE") != null 
							&& isChgTemp){ // MWS002M@ 테이블에 프로파일 등록되어져 있는지 체크	
						//리포트 mrd 파일 명
						rowMap.put("S018RFN",  isChgTempNm); // MWS002M@ 테이블에 프로파일 등록된 경우, 셋팅된 템플릿 가져오기
						isChgTemp = false;
					} 

					//리포트 툴에 넣을 파라미터 검사코드
					rowMap.put("P_HOS_GCD", String.valueOf(resultList.get(i).get("HOS_GCD")));
					//리포트 툴에 넣을 파라미터 검사코드
					rowMap.put("P_GCD", String.valueOf(resultList.get(i).get("F600GCD")));
					rowMap.put("F008GCD", String.valueOf(resultList.get(i).get("F008GCD")));

					//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
					if("74011".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						||"74012".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						||"74017".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					  ){
						if(is72185){
							rowMap.put("P_GCD",  rowMap.get("P_GCD") + "72185");
						}
					}
					
					// STIP 폼인 액상세포를 포함한 종목이 접수되면 특정 폼으로 강제로 변경시킨다.
					// ex) 61028, 71313, 72185 
					if("STIP".equals(String.valueOf(resultList.get(i).get("F010RNO")))
					){
						if("71313".equals(String.valueOf(resultList.get(i).get("F600GCD")))
							|| "72185".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						){
							continue;
						}else if("A9230".equals(String.valueOf(resultList.get(i).get("PGCD")))){
							rowMap.put("S018RFN", "77-STIP-TMP-02");
						}else if("A9240".equals(String.valueOf(resultList.get(i).get("PGCD")))){
							rowMap.put("S018RFN", "77-STIP-TMP-03");
						}else{
							//rowMap.put("S018RFN", "77-STIP-TMP-01");
							if(!"".equals(String.valueOf(resultList.get(i).get("CHN_TEMPLATE")).trim()) 
									&& resultList.get(i).get("CHN_TEMPLATE") != null 
							){ // MWS002M@ 테이블에 프로파일 등록되어져 있는지 체크	
								//리포트 mrd 파일 명
								rowMap.put("S018RFN",  isChgTempNm); // MWS002M@ 테이블에 프로파일 등록된 경우, 셋팅된 템플릿 가져오기
							}else{
								rowMap.put("S018RFN", "77-STIP-TMP-01");
							}
						}
						// ▽이미지 꼬릿말이 미생물 학부 정보로 출력되어야 하기 때문에 추가했으나,
						// ▽이미지 일괄 내려받기 할 경우, 61028 종목이 수신여부값이 O 값으로 변경되지 않기 때문에 주석처리함. -20190724-
						//rowMap.put("P_GCD", rowMap.get("P_GCD").replaceAll("61028", ""));
					}/*else{
						rowMap.put("S018RFN", String.valueOf(resultList.get(i).get("S018RFN")));
					}*/

					//컬쳐 센시 묶음 검사코드
					rowMap.put("CU_SE_GCD", String.valueOf(resultList.get(i).get("CU_SE_GCD")));
					//보고일자
					rowMap.put("BDT", String.valueOf(resultList.get(i).get("BDT")));
					rowMap.put("ADD_CD_NM","");
					//이미지퀄리티300DPI 출력해야하는 병원
					rowMap.put("DPI300_HOS", String.valueOf(resultList.get(i).get("DPI300_HOS")));
					//이미지 결과 페이지별로 개별 생성되어야 하는 병원
					rowMap.put("IMG_EACH_HOS", String.valueOf(resultList.get(i).get("IMG_EACH_HOS")));
					
					// 2022.12.12 - 29844(제천서울병원)인 경우, 미생물 결과가 최종보고가 아니면 이미지 생성 안되도록 처리 요청함. (요청부서:운영팀)
					// 미생물 검사결과가 최종보고가 아니면서, ex)중간보고
					// 코드관리(MICRO_MID_RESULT_EXC)에 등록된 병원인 경우,
					// 이미지결과 생성되지 않도록 처리
					if("미생성병원".equals(String.valueOf(resultList.get(i).get("MICROBE_RESULT_HOS"))) 
						&& "BMB".equals(String.valueOf(resultList.get(i).get("F010RNO")).trim())
						&& !"최종보고".equals(String.valueOf(resultList.get(i).get("MICROBE_RESULT_STS")))
					){
						continue;
					}else{
						rtnList.add(rowMap);
					}
					
					logger.debug("====================row map["+i+"]==============================");
					logger.debug(rowMap.toString());
				}

			}
			
			//==================================================================================================================================================================================
			// 3. 이미지 개별/일괄 생성 완료 후, 별도로 생성되어야 하는 이미지를 생성한다. (이미지 개별/일괄 생성 동일)
			// (조건1. 이미지 개별 생성하는 경우, 종목 개수의 마지막인지 체크한다. 그리고 유린/마이크로 종목이 포함된 접수인지 체크한다. 마지막에 한번만 아래 로직을 실행한다.)
			// (조건2. 이미지 일괄 생성하는 경우, 종목 개수의 마지막인지 대신 환자가 바뀌는지 체크한다.그리고 유린/마이크로 종목이 포함된 접수인지 체크한다. 마지막에 한번만 아래 로직을 실행한다.)
			// *** 추가필요조건. 일괄 생성화면에서 환자 1명만 선택해서 다운 받는 경우는 실행이 안되기 때문에 해당조건을 추가해야함.
			//==================================================================================================================================================================================
			
			/*if(
					"20180602".equals(resultList.get(i).get("F600DAT").toString().trim())
					&&"365".equals(resultList.get(i).get("F600JNO").toString().trim())
					&&"00802".equals(resultList.get(i).get("F600GCD").toString().trim())
			){
				System.out.println(resultList.get(i).get("F600DAT").toString().trim());
				System.out.println(resultList.get(i).get("F600JNO").toString().trim());
			}*/
			
			//System.out.println("resultList.size :: "+resultList.size());
			//System.out.println(i+"번째 검사코드 :: "+resultList.get(i).get("F600GCD").toString().trim());
			
			if(
				(nowNextDateJnoCheck && urine_gcd_check) 
				|| ("1".equals(imgMenuGubun) 
					&& i == r_len-1 
					&& urine_gcd_check
				   )
				|| ("D".equals(resultList.get(i).get("F010RNO").toString().trim()) 
					&& urine_gcd_check
					&& "00822".equals(resultList.get(i).get("F600GCD").toString().trim()) 
				   ) // 혈종인 경우
				|| (
						"2".equals(imgMenuGubun)
						&& urine_gcd_check
						// 예를들어 검사결과받기 메뉴에서,
						// 00802,00822 선택받기하는 경우, 선택받기한 값중 마지막 값이 00799/00802/00806/00822 4가지 값중 포함되는지 체크하여,
						// 마지막 값과 일치할때만 이미지결과를 생성한다.
						// 이유는 조건이 없을경우, 00802/00822 2개 이미지가 모두 생성되기 때문이다.
						&& "00822".equals(resultList.get(i).get("F600GCD").toString().trim())
					)	// 유린+마이크로스코피 접수되는경우
				|| (
						"2".equals(imgMenuGubun)
						&& urine_gcd_check
						// 예를들어 검사결과받기 메뉴에서,
						// 00802,00822 선택받기하는 경우, 선택받기한 값중 마지막 값이 00799/00802/00806/00822 4가지 값중 포함되는지 체크하여,
						// 마지막 값과 일치할때만 이미지결과를 생성한다.
						// 이유는 조건이 없을경우, 00802/00822 2개 이미지가 모두 생성되기 때문이다.
						&& !"00822".equals(resultList.get(i).get("F600GCD").toString().trim())
						&& resultList.size() == i+1
					) // 유린만 접수되는경우
			){
				
				//row 생성
				rowMap2 = new HashMap<String, String>();
				
				rowMap2.put("F600GCD", urine_F600GCD); //검사코드
				rowMap2.put("F100NAM", urine_F100NAM); //수진자명
				rowMap2.put("P_HOS_GCD", urine_P_HOS_GCD); //병원검사코드
				
				// 문제점 : 이미지결과받기 메뉴에서 STI 종목만 선택 후 선택받기 하면, 유린도 함께 생성되는 문제
				// 조치내용 : 27609,봄빛병원인 경우만, 유린이 아닌 종목을 선택한 경우는 선택한 종목만 생성되며, 유린을 선택한 경우만 유린 이미지가 함께 생성되도록 조치
				if(!"27609".equals(urine_F100HOS)
				){
					rowMap2.put("F008GCD", urine_F008GCD); //이미지 출력 검사코드
				}else{
					rowMap2.put("F008GCD", F008GCD); //이미지 출력 검사코드
				}
			
				rowMap2.put("F600DAT", urine_F600DAT); //접수일자
				rowMap2.put("F600JNO", urine_F600JNO); //접수번호
				rowMap2.put("HOS_GCD", urine_HOS_GCD); //병원별검사코드
				rowMap2.put("F010FKN", urine_F010FKN); //검사명
				rowMap2.put("S011RCL", urine_S011RCL); //양식
				rowMap2.put("PGCD", urine_PGCD); //프로파일코드
				rowMap2.put("PGNM", urine_PGNM); //프로파일명
				rowMap2.put("PG_TIT", urine_PG_TIT); //결과지 프로파일에 따른 타이틀명 
				rowMap2.put("F010RNO", urine_F010RNO); //폼
				rowMap2.put("P_F010FKN", urine_P_F010FKN); //검사명
				rowMap2.put("S018SYN", urine_S018SYN); //단일출력 여부
				rowMap2.put("F600HAK", urine_F600HAK); //학부
				rowMap2.put("S018RFN", urine_S018RFN); //리포트 mrd 파일 명
				rowMap2.put("P_GCD", urine_P_GCD); //리포트 툴에 넣을 파라미터 검사코드
				rowMap2.put("CU_SE_GCD", urine_CU_SE_GCD); //컬쳐 센시 묶음 검사코드
				rowMap2.put("ADD_CD_NM",urine_ADD_CD_NM); // 이미지에 종목이 2개 이상일 경우, 검사명 뒤에 '외' 문구열을 추가한다.
				rowMap2.put("BDT",urine_BDT); //보고일자
					
				// ^D: 문자열 때문에 파일명 배열에 담는다. 
				String[] FILE_NM_ARR;
				String FILE_NM_STR_02 = "";
				FILE_NM_ARR = FILE_NM.split("\\^D:");
				
				// 파일명에 일부 문자를 realpce 한다.
				if("23979".equals(urine_F100HOS)
					&& FILE_NM_ARR.length > 1
				){
					FILE_NM_STR_02 = FILE_NM_ARR[0].replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null","") + FILE_NM_ARR[1];
				}else{
					FILE_NM_STR_02 = FILE_NM_ARR[0].replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null","");
				}
				
				//==================================================================================================================================================================================
				//파일명이 동일한게 존재하는 경우, 끝에 구분값(seq) 문자를 추가한다.
				//==================================================================================================================================================================================
				String file_nm_all_list_02 = "";	// 1. 이미지 생성할 list에 데이터 중 파일명(FILE_NM)만 추출하여 전체 파일명 목록을 만든다.
				int file_nm_gubun_seq_02 = 0;		// 2. 이미지 파일명이 앞에 생성되는 파일명과 동일한 경우, 파일명 끝에 추가할 구분값(seq)
				
				for(HashMap<String, String> t_hm : rtnList){ // 3.이미지 생성할 list에 데이터를 hashmap 에 담는다. 각각의 값을 추출하여 사용하기 위해서.
					file_nm_all_list_02 += t_hm.get("FILE_NM") + "|";
				}
				
				/*System.out.println("### 변경전 ###");
				System.out.println("file_nm_all_list_02 : "+file_nm_all_list_02);
				System.out.println("FILE_NM_STR_02 : "+FILE_NM_STR_02);*/
				
				file_nm_gubun_seq_02 = 0;
				if(file_nm_all_list_02.indexOf(FILE_NM_STR_02) != -1){	// 4.이미지 생성할 list에 데이터 중 FILE_NM 값만 추출하여 이어 붙인 String에, 현재 생성하고자하는 이미지 파일명과 동일한 파일명이 존재하는지 체크!!
					file_nm_gubun_seq_02 ++; 						// 5. 구분값을 증가시킨다.
					FILE_NM_STR_02 += file_nm_gubun_seq_02;			// 6. 동일할 경우, 파일명끝에  구분값(seq)을 추가한다.
				}
				
				/*System.out.println("### 변경후 ###");
				System.out.println("file_nm_all_list_02 : "+file_nm_all_list_02);
				System.out.println("FILE_NM_STR_02 : "+FILE_NM_STR_02);*/
				
				rowMap2.put("FILE_NM",FILE_NM_STR_02);
				
				//==================================================================================================================================================================================
				// 2021-12-30
				// 결과지 양식 변경으로 인해 기준일(2021-12-29) 이전의 결과지는 예전 양식지로 출력되어야 함.
				//==================================================================================================================================================================================
				if(
					"ECA5".equals(String.valueOf(resultList.get(i).get("F010RNO")))
					&& Integer.parseInt(String.valueOf(resultList.get(i).get("F100DAT")) == "null"?"0":String.valueOf(resultList.get(i).get("F100DAT"))) < 20211229
					&& Integer.parseInt(String.valueOf(resultList.get(i).get("F100DAT")) == "null"?"0":String.valueOf(resultList.get(i).get("F100DAT"))) > 0
				){
					if("71601".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
						rowMap2.put("S018RFN",  "77-ECA5-TMP-20");
					}
					if("81359".equals(String.valueOf(resultList.get(i).get("F600GCD")))){
						rowMap2.put("S018RFN",  "77-ECA5-TMP-01");
					}
				}
				
				//==================================================================================================================================================================================
				// PDF 일 경우 별지참조
				//==================================================================================================================================================================================
				if("별지참조".equals(urine_F600CHR)
						// 결과값이 별지참조로 입력은 되지만 pdf 양식의 mrd를 이용하지 않는 예외 경우이다.
						&& !"EEP4".equals(String.valueOf(resultList.get(i).get("F010RNO")))
						&& !"EEP2".equals(String.valueOf(resultList.get(i).get("F010RNO")))
				){
					isPDFFirst = true;
					//리포트 mrd 파일 명, PDF일 경우 무조건 78-STI-TMJ-01 템플릿
					rowMap2.put("S018RFN",  "78-STI-TMJ-01");
				}
				
				//==================================================================================================================================================================================
				// 변경자 : cjw
				// 변경일자 : 2019-04-08
				// 서로 다른 2개 MRD가 하나의 접수번호에 등록되었을 경우,
				// 2개 모두 MWS002M@ 테이블에 등록된 프로파일과 종목이 일치하는 템플릿이 존재하는 경우, 조회된 템플릿으로 강제 변경.
				//==================================================================================================================================================================================
				if(!"".equals(String.valueOf(resultList.get(i).get("CHN_TEMPLATE")).trim()) 
						&& resultList.get(i).get("CHN_TEMPLATE") != null 
						&& isChgTemp){ // MWS002M@ 테이블에 프로파일 등록되어져 있는지 체크	
					//리포트 mrd 파일 명
					rowMap2.put("S018RFN",  isChgTempNm); // MWS002M@ 테이블에 프로파일 등록된 경우, 셋팅된 템플릿 가져오기
					isChgTemp = false;
				} 
				
				//==================================================================================================================================================================================
				// 이미지 파일명 변경
				//==================================================================================================================================================================================
	         	if( ("00799".equals(rowMap2.get("P_GCD").substring(0, 5)) || "00802".equals(rowMap2.get("P_GCD").substring(0, 5)) || "00806".equals(rowMap2.get("P_GCD").substring(0, 5)) || "00822".equals(rowMap2.get("P_GCD").substring(0, 5)))
	         			&& (!"33872".equals(urine_F100HOS)) // urine 관련 이미지파일명 마지막에 검사코드를 추가하지 않는 병원
	         			&& (!"38310".equals(urine_F100HOS)) // urine 관련 이미지파일명 마지막에 검사코드를 추가하지 않는 병원
            	){
            		FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM")) + "_" + rowMap2.get("P_GCD").trim().substring(0, 5);
            		rowMap2.put("FILE_NM", FILE_NM.replaceAll("N:","").replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|","").replaceAll("null",""));
            	}	
	         	
	         	//이미지퀄리티300DPI 출력해야하는 병원
				rowMap2.put("DPI300_HOS", String.valueOf(resultList.get(i).get("DPI300_HOS")));
				
				rtnList.add(rowMap2);
				
				urine_P_GCD = ""; // 결과지에 생성할 종목 리스트 : 초기화
				urine_gcd_check = false; // 유린검사 포함여부 : 초기화
				
				logger.debug("====================row map["+i+"]==============================");
				logger.debug(rowMap2.toString());
			}
				
				
			
		}
		//==================================================================================================================================================================================
		// 이미지 개별/일괄 생성 : End
		//==================================================================================================================================================================================
		
		/**
		 * pdf 결과일 경우 20190401 주석처리로 제거 별지참조 가 pdf이므로
		
		if(isPDF){
			//초기화 PDF 파일이 있으면 결과지 출력 건수를 0건으로
			//PDF 일경우 모두 General로 출력
			rtnList =  new ArrayList<HashMap<String, String>>();
			rowMap = new HashMap<String, String>();
			for(int i=0; i<r_len; i++ ){
				//jpg 또는 pdf 파일명
				FILE_NM = String.valueOf(resultList.get(i).get("FILE_NM"));
				S011RCL = String.valueOf(resultList.get(i).get("S011RCL"));
				//검사코드
				GCD = String.valueOf(resultList.get(i).get("F600GCD"));
				//이미지 검사코드
				F008GCD = String.valueOf(resultList.get(i).get("F008GCD"));
				//병원검사코드
				HOS_GCD = String.valueOf(resultList.get(i).get("HOS_GCD"));
				//검사명
				F010FKN = String.valueOf(resultList.get(i).get("F010FKN"));
				
				if(i == 0){
					//row 생성
					//수진자명
					rowMap.put("F100NAM", String.valueOf(resultList.get(i).get("F100NAM")));
					//접수일자
					rowMap.put("F600DAT", String.valueOf(resultList.get(i).get("F600DAT")));
					//접수번호
					rowMap.put("F600JNO", String.valueOf(resultList.get(i).get("F600JNO")));
					//검사번호
					rowMap.put("F600GCD", GCD);
					//병원별검사코드
					rowMap.put("HOS_GCD", HOS_GCD);
					//이미지 출력 검사코드
					rowMap.put("F008GCD", F008GCD);

					//검사명
					rowMap.put("F010FKN", F010FKN);

					//양식
					rowMap.put("S011RCL", S011RCL);
					//프로파일코드
					rowMap.put("PGCD", String.valueOf(resultList.get(i).get("PGCD")));
					//프로파일명
					rowMap.put("PGNM", String.valueOf(resultList.get(i).get("PGNM")));
					
					//결과지 프로파일에 따른 타이틀명 
					rowMap.put("PG_TIT", chgTitle( String.valueOf(resultList.get(i).get("PGCD")).replaceAll(" ","") ) );
					
					//폼
					rowMap.put("F010RNO", String.valueOf(resultList.get(i).get("F010RNO")));
					//파일명
					rowMap.put("FILE_NM", FILE_NM.replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|",""));
					//검사명
					rowMap.put("P_F010FKN", String.valueOf(resultList.get(i).get("F010FKN")));
					//단일출력 여부
					rowMap.put("S018SYN", String.valueOf(resultList.get(i).get("S018SYN")));
					//학부
					rowMap.put("F600HAK", String.valueOf(resultList.get(i).get("F600HAK")));

					//리포트 mrd 파일 명, PDF일 경우 무조건 GENERAL-01 템플릿
					rowMap.put("S018RFN",  "GENERAL-01");

					//리포트 툴에 넣을 파라미터 검사코드
					rowMap.put("P_HOS_GCD", String.valueOf(resultList.get(i).get("HOS_GCD")));
					//리포트 툴에 넣을 파라미터 검사코드
					rowMap.put("P_GCD", String.valueOf(resultList.get(i).get("F600GCD")));

					//검사코드 72185가 나오면 74011,74012,74017 일경우 72185대한 결과지가 출력되고 나머지 74011,74012,74017를 출력할 때 72185 결과 값, 리마크를 같이 출력하기 위한 flag
					if("74011".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						||"74012".equals(String.valueOf(resultList.get(i).get("F600GCD")))
						||"74017".equals(String.valueOf(resultList.get(i).get("F600GCD")))
					  ){
						if(is72185){
							rowMap.put("P_GCD",  rowMap.get("P_GCD") + "72185");
						}
					}

					//컬쳐 센시 묶음 검사코드
					rowMap.put("CU_SE_GCD", String.valueOf(resultList.get(i).get("CU_SE_GCD")));

					//보고일자
					rowMap.put("BDT", String.valueOf(resultList.get(i).get("BDT")));

					rowMap.put("ADD_CD_NM","");

					rtnList.add(rowMap);
				}else{
					
					rowMap.put("P_F010FKN",  rowMap.get("P_F010FKN") +"||" + F010FKN);
					rowMap.put("P_HOS_GCD",  rowMap.get("P_HOS_GCD") +"||" + HOS_GCD);
					rowMap.put("P_GCD",  rowMap.get("P_GCD") + GCD);
					
					//t_hm.put("F010RNO",t_hm.get("F010RNO") + String.valueOf(resultList.get(i).get("F010RNO")));
					rowMap.put("ADD_CD_NM","외");
					rowMap.put("F008GCD",  rowMap.get("F008GCD") + F008GCD);
					
				}
				
				
			}
			
		}
		
		 */
		
		
		//==================================================================================================================================================================================
		// 72190 72213 같이 나오는 경우 검사항목 순서 72213, 72190으로 변경
		//==================================================================================================================================================================================
  
  
		if(is72190_72213){
			for( HashMap<String, String> t_hm : rtnList){
				//	logger.debug("is72190_72213 1. P_GCD= "+t_hm.get("P_GCD"));
				if( t_hm.get("P_GCD").indexOf("72190") > -1 ){
					t_hm.put("P_GCD",  t_hm.get("P_GCD").replaceAll("72190","") + "72190");
				}
				//logger.debug("is72190_72213 2. P_GCD= "+t_hm.get("P_GCD"));
			}
		}
		
		//==================================================================================================================================================================================
		// MIC 세균정보가 나올경우 SPECIAL-01의 템플릿을 SPECIAL-04 템플릿으로 변경.
		//==================================================================================================================================================================================
		if(isChgMicTemp){
			String Pgcd = "";
			for( HashMap<String, String> t_hm : rtnList){
				Pgcd = String.valueOf(t_hm.get("P_GCD"));
				
				//==================================================================================================================================================================================
				// MIC 세균정보가 나올경우 SPECIAL-04 템플릿을 사용한다.
				//==================================================================================================================================================================================

//				if( Pgcd.indexOf("31108")>-1
//					||Pgcd.indexOf("31109")>-1
//					||Pgcd.indexOf("31110")>-1
//					||Pgcd.indexOf("31111")>-1
//					||Pgcd.indexOf("31112")>-1
//					||Pgcd.indexOf("31113")>-1
//					||Pgcd.indexOf("31114")>-1 
//					||Pgcd.indexOf("31115")>-1
//					||Pgcd.indexOf("31116")>-1 
//					||Pgcd.indexOf("31117")>-1
//					||Pgcd.indexOf("31118")>-1 
//					||Pgcd.indexOf("31119")>-1
//					||Pgcd.indexOf("31120")>-1 
//					||Pgcd.indexOf("31123")>-1
//					||Pgcd.indexOf("31124")>-1 
//					||Pgcd.indexOf("31126")>-1
//					||Pgcd.indexOf("31127")>-1 
//					||Pgcd.indexOf("31128")>-1
//				){
				// 미생물 MIC,DISK 결과지 SPECIAL-01 통일하여 해당 검사코드 제외함 20210409 HJY
				if( Pgcd.indexOf("31116")>-1 
					||Pgcd.indexOf("31117")>-1
					||Pgcd.indexOf("31118")>-1 
					||Pgcd.indexOf("31119")>-1
					||Pgcd.indexOf("31120")>-1 
				){
					if("SPECIAL-01".equals(t_hm.get("S018RFN"))){
						//리포트 mrd 파일 명
						t_hm.put("S018RFN",  "SPECIAL-04");
					}
				}
			}
		} 
		
		
		//==================================================================================================================================================================================
		// 이미지 출력 관련없는 검사코드 삭제 (recvImg.jsp)																																											  
		//==================================================================================================================================================================================													   
		if(imgFg)
		{
			logger.debug(" rtnList.size()        =====   "+rtnList.size()  );
			for(int i = rtnList.size()-1 ; 0 <= i; i-- ){
				HashMap<String, String> t_hm = rtnList.get(i);
				logger.debug(t_hm.get("F600DAT")+"  /// "+t_hm.get("F600JNO")+"===============================================t_hm   === "+t_hm.get("F008GCD"));
				if("".equals(t_hm.get("F008GCD"))
						&&!"41121".equals(t_hm.get("P_GCD"))){
					rtnList.remove(i);
				}
			}
		}
		//logger.debug("IMG_EACH_HOS : "+ resultList.get(0).get("IMG_EACH_HOS"));
		//rowMap2.put("DPI300_HOS", String.valueOf(resultList.get(0).get("DPI300_HOS")));
		//rtnList.add(rowMap2);
		
		// 이미지결과받기 recvImg_mclis 호출시 값을 세팅한다.
		// ex) https://trms.seegenemedical.com/recvImg_mclis.do?parm_fdt=20230130&parm_tdt=20230202&parm_hos=31139
		if (!rtnList.isEmpty() && !resultList.isEmpty())
			rtnList.get(0).put("IMG_EACH_HOS", String.valueOf(resultList.get(0).get("IMG_EACH_HOS")));

		//==================================================================================================================================================================================
		// 생성된 출력물 리스트 log
		//==================================================================================================================================================================================
		logger.debug("===============================================imgFg   === "+imgFg);
		for( HashMap<String, String> t_hm : rtnList){
			logger.debug(t_hm.toString());
			////System.out.println(t_hm.toString());
		}
		logger.debug("===============================================");
     
		//==================================================================================================================================================================================
		//파일명이 동일한게 존재하는 경우, 끝에 구분값(seq) 문자를 추가한다.
		//==================================================================================================================================================================================
		/*String file_nm_list_all = "";
		int file_nm_gubun_seq = 0;
		int for_each_seq = 0;
		
		for(HashMap<String, String> t_hm : rtnList){
			System.out.println("### FILE_NM : "+t_hm.get("FILE_NM"));
			file_nm_list_all += file_nm_list_all + "|";
		}
		
		for(HashMap<String, String> t_hm : rtnList){
			for_each_seq ++;
			if(file_nm_list_all.indexOf(t_hm.get("FILE_NM")) != -1){
				file_nm_gubun_seq ++;
				rtnList.get(for_each_seq). = t_hm.get("FILE_NM") + file_nm_gubun_seq;
			}
		}
		*/
		
		//==================================================================================================================================================================================
		// 생성할 이미지결과 리스트를 return할 param에 put 한다.
		//==================================================================================================================================================================================
//		logger.error("=====  === "+rtnList.size());
		param.put("resultList", rtnList);

		return param;
	}
	
	
	/**
	 * @Method Name : isGCD
	 * @see
	 * <pre>
	 * Method 설명 :  해당 검사코드 존재 여부를 리턴
	 * return_type : boolean
	 * </pre>
	 * @param resultList
	 * @param GCD
	 * @return
	 */
	public boolean isGCD(List<Map<String, Object>>  resultList, String GCD){
		boolean isGCD = false;
		
		int r_len = resultList.size();
		for(int i=0; i<r_len; i++ ){
			if(GCD.equals(String.valueOf(resultList.get(i).get("F600GCD")))){
				isGCD = true;
				break;
			}
		}
		return isGCD;
	}
	
	

	/**
	 * @Method Name : chgTitle
	 * @see
	 * <pre>
	 * Method 설명 :  해당 검사코드의 타이틀 변경(ResultPage_C 결과지 title 하드 코딩 추가)
	 * return_type : boolean
	 * </pre>
	 * @param resultList
	 * @param GCD
	 * @return
	 */
	public String chgTitle(String PGCD){
		String title = "";

		if ("90027".equals(PGCD)   || "99950".equals(PGCD)   ) {
			title = "임신초기검사 : First double test";
		} else if ("99934".equals(PGCD)   || "99955".equals(PGCD) ) {
			title = "Integrated test";
		} else if ("99935".equals(PGCD)   || "99952".equals(PGCD)   || "A1008".equals(PGCD) ) {
			title = "Sequential test 1차";
		} else if ("99936".equals(PGCD)  || "99956".equals(PGCD)    ) {
			title = "Sequential test 2차";
		} else if ("90100".equals(PGCD) || "99954".equals(PGCD) ) {
			title = "임신중기검사 : Quadruple test";
		} else if ("90028".equals(PGCD) || "99953".equals(PGCD) ) {
			title = "임신중기검사 :Triple marker";
		} else if ("90029".equals(PGCD) ) {
			title = "임신중기검사 :Double maker";
		}
	      
		return title;
	}
	
}

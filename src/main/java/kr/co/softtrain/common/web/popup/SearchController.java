package kr.co.softtrain.common.web.popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.popup
 * SearchController.java
 * </pre>
 * @title :  검색 팝업창
 * @author : OJS
 * @since : 2018. 12. 4.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 4.		수정자명  				최초생성
 * 
 * </pre>
 */
@Controller
public class SearchController {
	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : gohospSearchS
	 * @see
	 * <pre>
	 * Method 설명 : 싱글 병원 검색 팝업
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/hospSearchS.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gohospSearchS(HttpServletRequest request, HttpSession session){
		String str = "popup/hospSearchS";
		
		return str;
	}
	
	/**
	 * @Method Name : gohospSearchS
	 * @see
	 * <pre>
	 * Method 설명 : 싱글 병원 검색 팝업
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/hospSearchSEng.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gohospSearchSEng(HttpServletRequest request, HttpSession session){
		String str = "popup/hospSearchSEng";
		
		return str;
	}
	
	/**
	 * @Method Name : gohospSearchMS
	 * @see
	 * <pre>
	 * Method 설명 :  멀티 병원 검색
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/hospSearchMS.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gohospSearchMS(HttpServletRequest request, HttpSession session){
		String str = "popup/hospSearchMS";
		
		return str;
	}
	
	/**
	 * @Method Name : gotestSearchM
	 * @see
	 * <pre>
	 * Method 설명 : 멀티 검사 항목 검색 팝업
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/testSearchM.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gotestSearchM(HttpServletRequest request, HttpSession session){
		String str = "popup/testSearchM";
		
		return str;
	}
	
	/**
	 * @Method Name : gopatSearchS
	 * @see
	 * <pre>
	 * Method 설명 : 싱글 수진자 검색 팝업
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/testSearchMA.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gotestSearchMA(HttpServletRequest request, HttpSession session){
		String str = "popup/testSearchMA";
		
		return str;
	}
	
	/**
	 * @Method Name : gohpatSearchS
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/patSearchS.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gopatSearchS(HttpServletRequest request, HttpSession session){
		String str = "popup/patSearchS";
		
		return str;
	}	
	
	/**
	 * @Method Name : hospSearchSList
	 * @see
	 * <pre>
	 * Method 설명 : 병원 검색 팝업 - 병원 목록
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/hospSearchSList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object hospSearchSList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
	  	param.put("I_FNM", Utils.isNull(param.get("I_FNM")));
	  	param.put("I_MCD", Utils.isNull(param.get("I_LOGMNU")));
	  	
	  	//logger.debug("------ hospSearchSList.do ------" + param);
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("hospSearchSList", param);

	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	
	/**
	 * @Method Name : patSearchSList
	 * @see
	 * <pre>
	 * Method 설명 : 수진자 검색 팝업 - 수진자 정보 목록
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/patSearchSList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object patSearchSList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	//param.put("I_HOS", session.getAttribute("HOS"));
	  	
	  	//param.put("I_HOS",  Utils.Null2Blank("23266"));
	  	param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
	  	param.put("I_CHN", Utils.isNull(param.get("I_CHN")));
	  	param.put("I_NAM", Utils.isNull(param.get("I_NAM")));
	  	
	  	//logger.debug("------test1-------" + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("patSearchSList", param);
	  	
	  	//logger.debug("연습 : ------ : " + param.get("O_MSGCOD"));
	  	
	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	
	/**
	 * @Method Name : testSearchM
	 * @see
	 * <pre>
	 * Method 설명 : 검사 항목 검색 팝업 - 검사 항목 목록
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/testSearchMList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object testSearchM(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	param.put("I_GCD", Utils.isNull(param.get("I_GCD")));
	  	param.put("I_FKN", Utils.isNull(param.get("I_FKN")));
	  	param.put("I_SOCD", Utils.isNull(param.get("I_SOCD")));
	  	
	  	//logger.debug("------ testSearchMList.do -------" + param);
	  	
	  	List<Map<String, Object>> resultList = new ArrayList<>(); 
	  	
	  	resultList = commonService.commonList("testSearchMList", param);
	  	
	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	
	/**
	 * @Method Name : testSearchMA
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/testSearchMListA.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object testSearchMA(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	param.put("I_GCD", Utils.isNull(param.get("I_GCD")));
	  	param.put("I_FKN", Utils.isNull(param.get("I_FKN")));
	  	param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
	  	
	  	logger.debug("------test1-------" + param);
	  	
	  	List<Map<String, Object>> resultList = new ArrayList<>(); 
	  	
	  	resultList = commonService.commonList("testSearchMListA", param);
	  	
	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	
	/**
	 * @Method Name : hospSearchA
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/hospSearchA.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object hospSearchA(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
	  		  	
	  	//logger.debug("------hospSearchA.do-------" + param);
	  	
	  	List<Map<String, Object>> resultList = new ArrayList<>(); 
	  	
	  	resultList = commonService.commonList("hospSearchA", param);
	  	
	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
}

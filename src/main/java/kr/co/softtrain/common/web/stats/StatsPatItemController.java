package kr.co.softtrain.common.web.stats;

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
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.stats
 * StatsPatItemController.java
 * </pre>
 * @title :  통계관리> 항목 통계(수진자)
 * @author : seegene3
 * @since : 2018. 12. 14.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 14.		ejlee  			  최초생성
 *   2019. 02. 07       OJS				 statsItem -> statsPatItem 변경
 * 
 * </pre>
 */
@Controller
public class StatsPatItemController {

	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : goStatsPatItem
	 * @see
	 * <pre>
	 * Method 설명 : statsPatItem.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/statsPatItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goStatsPatItem(HttpServletRequest request, HttpSession session) throws Exception{
	   String str ="stats/statsPatItem";
	   if(CommonController.AuthPage(request,session)){
		      str = "index";
	   }	
		return str;
	}
	
	@RequestMapping(value="/statsPatItem01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goStatsPatItem01(HttpServletRequest request, HttpSession session){
		
		return "stats/statsPatItem01";
	}
	
	
	/**
	 * @Method Name : statsPatItemList
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
	@RequestMapping(value="/statsPatItemList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object statsPatItemList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
		param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
		
		logger.debug("--test--" + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("statsPatItemList", param);
	  	
	  	logger.debug("--test2--" + param.get("O_DAT"));
	  	param.put("resultList", resultList);
	  	
		return param;
	}
	
	
	/**
	 * @Method Name : statsPatItem01List
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
	@RequestMapping(value="/statsPatItem01List.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object statsPatItem01List(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
		param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
		
		logger.debug("--test--" + param);
		List<Map<String, Object>> resultList = commonService.commonList("statsPatItem01List", param);
		
		logger.debug("--test2--" + param.get("O_DAT"));
		param.put("resultList", resultList);
		
		return param;
	}
}


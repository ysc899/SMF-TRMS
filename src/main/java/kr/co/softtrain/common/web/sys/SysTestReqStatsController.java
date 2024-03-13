package kr.co.softtrain.common.web.sys;

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
 * SysTestReqStatsController.java
 * </pre>
 * @title :  시스템관리 > 검사의뢰서 통계
 * @author : seegene3
 * @since : 2018. 12. 27.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 27.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysTestReqStatsController {

	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : goSysTestReqStats
	 * @see
	 * <pre>
	 * Method 설명 : sysTestReqStats.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/sysTestReqStats.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goSysTestReqStats(HttpServletRequest request, HttpSession session) throws Exception {
		String str = "sys/sysTestReqStats";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		
		return str;
	}
	
	
	/**
	 * @Method Name : sysTestReqStatsList
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
	@RequestMapping(value="/sysTestReqStatsList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysTestReqStatsList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
		param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
		
	  	List<Map<String, Object>> resultList = commonService.commonList("sysTestReqStatsList", param);
	  	
	  	param.put("resultList", resultList);
	  	
		return param;
	}
}


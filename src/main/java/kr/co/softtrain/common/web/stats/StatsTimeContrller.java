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
 * StatsTimeContrller.java
 * </pre>
 * @title :  통계관리 > 시계열
 * @author : OJS
 * @since : 2018. 12. 10.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 10.		OJS  				최초생성
 * 
 * </pre>
 */
@Controller
public class StatsTimeContrller {

	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : gostatsTime
	 * @see
	 * <pre>
	 * Method 설명 : statsTime.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/statsTime.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gostatsTime(HttpServletRequest request, HttpSession session) throws Exception{
	   String str = "stats/statsTime";
	   if(CommonController.AuthPage(request,session)){
		      str = "index";
	   }		
		return str;
	}
	
	/**
	 * @Method Name : statsTimeList
	 * @see
	 * <pre>
	 * Method 설명 : 시계열 목록 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/statsTimeList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object statsTimeList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	Object[] args = new Object[0];
	  	if(param.containsKey("I_RGN")){
	  		args =  request.getParameterValues("I_RGN");
	  	}

	  	param.put("I_RGN1", "");
	  	param.put("I_RGN2", "");
	  	param.put("I_RGN3", "");
	  	if(args.length>0){
	  		for (Object object : args) {
	  			param.put("I_RGN"+object, "Y");
	  		}
	  	}
	  	//System.out.println(param);
	  	param.put("I_DGN", Utils.isNull(param.get("I_DGN")));
	  	param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
	  	param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
	  	param.put("I_HOS", Utils.isNull(param.get("I_HOS")));		
	  	param.put("I_BDT", Utils.isNull(param.get("I_BDT")));
	  	param.put("I_CHN", Utils.isNull(request.getParameter("I_CHN")));
	  	param.put("I_NAM", Utils.isNull(param.get("I_NAM")));
	  	//param.put("I_aaa", Utils.isNull(param.get("I_RGN1")));
	  	//param.put("I_aaa", Utils.isNull(param.get("I_RGN1")));
	  	//param.put("I_RGN2", Utils.isNull(param.get("I_RGN2")));
	  	//param.put("I_RGN3", Utils.isNull(param.get("I_RGN3")));
		param.put("I_HAK", Utils.isNull(param.get("I_HAK")));
		param.put("I_STS", Utils.isNull(param.get("I_STS")));
		param.put("I_ETC", Utils.isNull(param.get("I_ETC")));
		param.put("I_GAD", Utils.isNull(param.get("I_GAD")));
		param.put("I_PNN", Utils.isNull(param.get("I_PNN")));
		param.put("I_ICNT", Utils.isNull(request.getParameter("I_ICNT")));
		param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_JNO")));
		param.put("I_GCD", Utils.isNull(param.get("I_GCD")));
		param.put("I_ACD", Utils.isNull(param.get("I_ACD")));
		
		//param.put("I_ICNT", Utils.Null2Zero("20"));
		//param.put("I_HOS", "12257");
		//param.put("I_FDT", "20100101");
		//param.put("I_TDT", "20181231");
		//param.put("I_BDT", "20170329");
		//param.put("I_CHN", "2306-1213");
		//param.put("I_NAM", "김응삼");
		
		//logger.debug("--test--" + param + ":  icnt" + param.get("I_ICNT"));
	 	List<Map<String, Object>> resultList = commonService.commonList("statsTimeList", param);
	  	
		param.put("resultList", resultList);
	  	
		return param;
	}
	
	/**
	 * @Method Name : statsTimeGrp
	 * @see
	 * <pre>
	 * Method 설명 : 시계열  상세정보 그래프/그리드 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/statsTimeGrp.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object statsTimeGrp(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_HOS", Utils.isNull(param.get("I_HOS")));		
	  	param.put("I_BDT", Utils.isNull(param.get("I_BDT")));
	  	param.put("I_CHN", Utils.isNull(param.get("I_CHN")));
		param.put("I_ICNT", Utils.Null2Zero(request.getParameter("I_ICNT_CHART")));
		param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_JNO")));
		param.put("I_GCD", Utils.isNull(param.get("I_GCD")));
		param.put("I_ACD", Utils.isNull(param.get("I_ACD")));
		
		//param.put("I_HOS", "12257");
		//param.put("I_BDT", "19600924");
		//param.put("I_CHN", "2306-1213");
		
		//logger.debug("--test--" + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("statsTimeGrp", param);
	  	
	  	param.put("resultList", resultList);
	  	
		return param;
	}
}


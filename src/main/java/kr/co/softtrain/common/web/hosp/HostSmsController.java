package kr.co.softtrain.common.web.hosp;

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
 * kr.co.softtrain.common.web.hosp
 * HostSmsController.java
 * </pre>
 * @title :  SMS 메시지 관리
 * @author : OJS 
 * @since : 2018. 11. 28.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 28.		OJS 			최초생성
 * 
 * </pre>
 */
@Controller
public class HostSmsController {
	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : gohospSms
	 * @see
	 * <pre>
	 * Method 설명 : hospSms.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/hospSms.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gohospSms(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "hosp/hospSms";
	   if(CommonController.AuthPage(request,session)){
		      str = "index";
	   }
		return str;
	}
	
	
	/**
	 * @Method Name : hospSmsList
	 * @see
	 * <pre>
	 * Method 설명 : SMS 정보 목록 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/hospSmsList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object hospSmsList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	//param.put("I_HOS", session.getAttribute("HOS"));
	  	//param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
	  	param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_HOS")));
	  	List<Map<String, Object>> resultList = commonService.commonList("hospSmsList", param);

	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	
	
    /**
     * @Method Name : hospSmsSave
     * @see
     * <pre>
     * Method 설명 : 추가 되는 SMS 정보 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_HOS", session.getAttribute("HOS"));
	  	//param.put("I_HOS", Utils.isNull("HP001"));
	  	//param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
        param.put("I_NAM",  Utils.Null2Blank(request.getParameter("C002NAM")));
        param.put("I_MSG",  Utils.Null2Blank(request.getParameter("C002MSG")));
        
	  	
        //logger.debug("---test1---" + param);
        
        commonService.commonInsert("hospSmsSave", param);
        
	  	return param;
    }
     
    /**
     * @Method Name : hospSmsUdt
     * @see
     * <pre>
     * Method 설명 : 기존 SMS 정보 수정 
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsUdt.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsUdt(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("I_HOS", session.getAttribute("HOS"));
        param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C002SEQ")));
        param.put("I_NAM",  Utils.Null2Blank(request.getParameter("C002NAM")));
        param.put("I_MSG",  Utils.Null2Blank(request.getParameter("C002MSG")));
    	
    	commonService.commonUpdate("hospSmsUdt", param);
    	
    	return param;
    	
    }
    
    /**
     * @Method Name : hospSmsDel
     * @see
     * <pre>
     * Method 설명 : 기존 SMS 정보 삭제 
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsDel.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        
	  	param.put("I_HOS", session.getAttribute("HOS"));
        param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C002SEQ")));
	  	
        //logger.debug("--- test2 ----" + param);
        
        commonService.commonDelete("hospSmsDel", param);
        
 	  	return param;
    }
		
}

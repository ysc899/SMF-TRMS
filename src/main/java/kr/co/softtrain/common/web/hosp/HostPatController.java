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
 * HostPatController.java
 * </pre>
 * @title :  수진자 정보 관리
 * @author : OJS
 * @since : 2018. 11. 27.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 27.		OJS			최초생성
 * 
 * </pre>
 */
@Controller
public class HostPatController {

	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : gohospPat
	 * @see
	 * <pre>
	 * Method 설명 : hospPat.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/hospPat.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gohospPat(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "hosp/hospPat";
	   if(CommonController.AuthPage(request,session)){
		      str = "index";
	   }
		   
		return str;
	}
	
	/**
	 * @Method Name : hospPatList
	 * @see
	 * <pre>
	 * Method 설명 : 수진자 정보 목록 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/hospPatList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object hospPatList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		param.put("I_COR", session.getAttribute("COR"));
		param.put("I_UID", session.getAttribute("UID"));
		param.put("I_IP", session.getAttribute("IP"));
		
	  	//param.put("I_HOS", session.getAttribute("HOS"));
	  	param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_HOS")));
		param.put("I_CHN", Utils.isNull(param.get("I_CHN")));
		param.put("I_SEX", Utils.isNull(param.get("I_SEX")));
		param.put("I_NAM", Utils.isNull(param.get("I_NAM")));
	  	
		//logger.debug("--test--" + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("hospPatList", param);
	  	
	  	//logger.debug("--test1--" + param.get("O_CHN"));

	  	param.put("resultList", resultList);
		
		return param;
	}
	
	
    /**
     * @Method Name : hospPatSave
     * @see
     * <pre>
     * Method 설명 : 추가되는 수진자 정보 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospPatSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospPatSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_HOS", Utils.isNull(session.getAttribute("HOS")));
        param.put("I_CHN",  Utils.Null2Blank(request.getParameter("C001CHN")));
        param.put("I_NAM",  Utils.Null2Blank(request.getParameter("C001NAM")));
        param.put("I_HPN",  Utils.Null2Blank(request.getParameter("C001HPN")));
        param.put("I_BDT",  Utils.Null2Blank(request.getParameter("C001BDT")));
        param.put("I_SEX",  Utils.Null2Blank(request.getParameter("C001SEX")));
        param.put("I_AGE",  Utils.Null2Zero(request.getParameter("C001AGE")));
        
        //logger.debug("---test2---" + param);
        
        commonService.commonInsert("hospPatSave", param);
        
	  	return param;
    }
    
    /**
     * @Method Name : hospPatDel
     * @see
     * <pre>
     * Method 설명 : 기존 수진자 정보 삭제
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospPatDel.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospPatDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
       
	  	//param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C001HOS")));
	  	param.put("I_HOS", Utils.isNull(session.getAttribute("HOS")));
        param.put("I_CHN",  Utils.Null2Blank(request.getParameter("C001CHN")));

        //logger.debug("--- test3 ----" + param);
        
        commonService.commonDelete("hospPatDel", param);
        
 	  	return param;
    }
    
    /**
     * @Method Name : hospPatUdt
     * @see
     * <pre>
     * Method 설명 : 기존 수진자 정보 수정
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospPatUdt.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospPatUdt(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	//param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
        //param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C001HOS")));
    	param.put("I_HOS", Utils.isNull(session.getAttribute("HOS")));
        param.put("I_CHN",  Utils.Null2Blank(request.getParameter("C001CHN")));
        param.put("I_NAM",  Utils.Null2Blank(request.getParameter("C001NAM")));
        param.put("I_HPN",  Utils.Null2Blank(request.getParameter("C001HPN")));
        param.put("I_BDT",  Utils.Null2Blank(request.getParameter("C001BDT")));
        param.put("I_SEX",  Utils.Null2Blank(request.getParameter("C001SEX")));
        param.put("I_AGE",  Utils.Null2Blank(request.getParameter("C001AGE")));
        
    	commonService.commonUpdate("hospPatUdt", param);
    	
    	return param;
    	
    }
    
    /**
     * @Method Name : hospPatChkSave
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
    @RequestMapping(value = "/hospPatChkSave.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospPatChkSave(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	//param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
        //param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C001HOS")));
    	param.put("I_HOS", Utils.isNull(session.getAttribute("HOS")));
        param.put("I_CHN",  Utils.Null2Blank(request.getParameter("C001CHN")));

        
        List<Map<String, Object>> resultList = commonService.commonList("hospPatChkSave", param);
        param.put("resultList", resultList);
        
    	return param;
    	
    }
    
}

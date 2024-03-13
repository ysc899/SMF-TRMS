/*
 * Copyright 2014 MOSPA(Ministry of Security and Public Administration).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.co.softtrain.common.web.hosp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import antlr.Utils;
import kr.co.softtrain.custom.util.*;
import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;



/**
 * <pre>
 * kr.co.softtrain.common.web.hosp
 * HostSmsPatController.java
 * </pre>
 * @title :  SMS 연동 관리
 * @author : OJS
 * @since : 2018. 11. 28.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 28.		OJS  				최초생성
 * 
 * </pre>
 */
@Controller
public class HostSmsPatController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : sysAuth
	 * @see
	 * <pre>
	 * Method 설명 : hsopSmsPat.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/hospSmsPat.do")
	public String gohospSmsPat(HttpServletRequest request, HttpSession session) throws Exception{
	   String str = "hosp/hospSmsPat";
	   if(CommonController.AuthPage(request,session)){
		      str = "index";
	   }
		return str;
	}
	
	/**
	 * @Method Name : gohospSmsPat01
	 * @see
	 * <pre>
	 * Method 설명 : hospSmsPat01.jsp 팝업 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/hospSmsPat01.do")
	public String gohospSmsPat01(HttpSession session){
		return "hosp/hospSmsPat01";
	}
	
	/**
	 * @Method Name : gohospSmsPat02
	 * @see
	 * <pre>
	 * Method 설명 : hospSmsPat02.jsp 팝업 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/hospSmsPat02.do")
	public String gohospSmsPat02(HttpSession session){
		return "hosp/hospSmsPat02";
	}
	
	/**
	 * @Method Name : hospSmsPatList
	 * @see
	 * <pre>
	 * Method 설명 : SMS 연동 관리 - SMS 메시지 정보 목록 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/hospSmsPatList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object hospSmsPatList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  		  	
	  	//param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
	  	param.put("I_GCD", Utils.isNull(param.get("I_GCD")));
	  	param.put("I_MSG", Utils.isNull(param.get("I_MSG")));
	  	
	  	//logger.debug("--------------test : " + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("hospSmsPatList", param);

	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	
	
    /**
     * @Method Name : hospPatSave
     * @see
     * <pre>
     * Method 설명 : SMS 연동 관리 - SMS 메시지 정보 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsMsgSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospPatSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	//param.put("I_HOS", session.getAttribute("HOS"));
	  	param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C003HOS")));
        param.put("I_GCD",  Utils.Null2Blank(request.getParameter("C003GCD")));
        param.put("I_MSG",  Utils.Null2Blank(request.getParameter("C003MSG")));
        //logger.debug("---test1---" + param);
        
        commonService.commonInsert("hospSmsMsgSave", param);
        
	  	return param;
    }
    
    
    /**
     * @Method Name : hospSmsPatSave
     * @see
     * <pre>
     * Method 설명 : SMS 연동 관리 - 수진자 정보 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsPatSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsPatSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	//param.put("I_HOS", session.getAttribute("HOS"));
	  	//param.put("I_HOS", Utils.isNull("HP001"));
	  	param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C001HOS")));
	  	param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C003SEQ")));
	  	param.put("I_CHN",  Utils.Null2Blank(request.getParameter("C001CHN")));
	  	
	  	//logger.debug("---test2---" + param);
        
        commonService.commonInsert("hospSmsPatSave", param);
        
	  	return param;
    }
    
    
    /**
     * @Method Name : hospSmsPatDtlList
     * @see
     * <pre>
     * Method 설명 : SMS 연동 관리 - 수진자 정보 목록 호출
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsPatDtlList.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsPatDtlList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	//param.put("I_HOS",  Utils.isNull(session.getAttribute("HOS")));
	  	param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_HOS")));
	  	param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C003SEQ")));
	  	
	  	//logger.debug("---test3---" + param);
        
	  	List<Map<String, Object>> resultList = commonService.commonList("hospSmsPatDtlList", param);
	  	
	  	param.put("resultList", resultList);
	  	return param;
    }
    
    /**
     * @Method Name : hospSmsPatDel
     * @see
     * <pre>
     * Method 설명 : SMS 연동 관리 - 수진자 정보 삭제
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsPatDel.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsPatDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        
	  	param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C001HOS")));
        param.put("I_CHN",  Utils.Null2Blank(request.getParameter("C001CHN")));
        param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C003SEQ")));
	  	
        //logger.debug("--- test4----" + param);
        
        commonService.commonDelete("hospSmsPatDel", param);
        
 	  	return param;
    }
    
    
    /**
     * @Method Name : hospSmsMsgUdt
     * @see
     * <pre>
     * Method 설명 : SMS 연동 관리 - SMS 메시지 수정
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsMsgUdt.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsMsgUdt(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        
        param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C003HOS")));
        param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C003SEQ")));
        param.put("I_GCD",  Utils.Null2Blank(request.getParameter("C003GCD")));
        param.put("I_MSG",  Utils.Null2Blank(request.getParameter("C003MSG")));
	  	
        //logger.debug("--- test5----" + param);
        
        commonService.commonDelete("hospSmsMsgUdt", param);
        
 	  	return param;
    }
    
    
    /**
     * @Method Name : hospSmsMsgDel
     * @see
     * <pre>
     * Method 설명 : SMS 연동 관리 - 메시지 삭제
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/hospSmsMsgDel.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsMsgDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        param.put("I_HOS",  Utils.Null2Blank(request.getParameter("C003HOS")));
        param.put("I_GCD",  Utils.Null2Blank(request.getParameter("C003GCD")));
        param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("C003SEQ")));
	  	
        //logger.debug("--- test6----" + param);
        
        commonService.commonDelete("hospSmsMsgDel", param);
        
 	  	return param;
    }

    /**
     * @Method Name : hospSmsPatSave
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
    @RequestMapping(value = "/hospSmsPatChkSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object hospSmsPatChkSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	param.put("I_HOS",  Utils.isNull(session.getAttribute("HOS")));
	  	
	  	//logger.debug("---test2---" + param);
        
	  	List<Map<String, Object>> resultList = commonService.commonList("hospSmsPatChkSave", param);
	  	
	  	param.put("resultList", resultList);
        
	  	return param;
    }
}




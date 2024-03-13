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
package kr.co.softtrain.common.web.sys;

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
 * kr.co.softtrain.common.web.sys
 * SysMenuController.java
 * </pre>
 * @title :  
 * @author : twbae
 * @since : 2018. 11. 19.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 19.		twbae	 			최초생성
 * 
 * </pre>
 */
@Controller
public class SysMenuController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	@RequestMapping(value = "/sysMenu.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String sysMenu(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "sys/sysMenu";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name : sysMenuList
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
	@RequestMapping(value = "/sysMenuList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object sysMenuList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysMenuList", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	/**
	 * @Method Name : sysMenuAgrList
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
	@RequestMapping(value = "/sysMenuAgrList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object sysMenuAgrList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysMenuAgrList", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	/**
	 * @Method Name : sysMenuDtl
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
	@RequestMapping(value = "/sysMenuDtl.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object sysMenuDtl(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysMenuDtl", param);
    	
    	param.put("resultList", resultList);

    	return param;
    }
	
	
	/**
	 * @Method Name : sysMenuChkSave
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
	@RequestMapping(value = "/sysMenuChkSave.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object sysMenuChkSave(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	//param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	param.put("I_MCD", Utils.Null2Blank(request.getParameter("$R_MCD")));
    	param.put("I_PMCD", Utils.Null2Blank(request.getParameter("$R_PMCD")));
    	param.put("I_SEQ", Utils.Null2Zero(request.getParameter("$R_SEQ")));
    	param.put("I_MNM", Utils.Null2Blank(request.getParameter("$R_MNM")));
    	param.put("I_PTH", Utils.Null2Blank(request.getParameter("$R_PTH")));
    	param.put("I_ICN", Utils.Null2Blank(request.getParameter("$R_ICN")));
    	param.put("I_UYN", "Y"); //사용여부
    	
    	param.put("I_LOGMNU", request.getParameter("I_LOGMNU"));// 메뉴코드isEmpty
        param.put("I_LOGMNM", request.getParameter("I_LOGMNM"));// 메뉴코드isEmpt
        
    	List<Map<String, Object>> resultList =  commonService.commonList("sysMenuChkSave", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    	
    }
	
	
	/**
	 * @Method Name : sysMenuSave
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : void
	 * </pre>
	 * @param request
	 * @param session
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysMenuSave.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object sysMenuSave(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	//param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	  
    	param.put("I_MCD", Utils.Null2Blank(request.getParameter("$R_MCD")));
    	param.put("I_PMCD", Utils.Null2Blank(request.getParameter("$R_PMCD")));
    	param.put("I_SEQ", Utils.Null2Zero(request.getParameter("$R_SEQ")));
    	param.put("I_MNM", Utils.Null2Blank(request.getParameter("$R_MNM")));
    	param.put("I_PTH", Utils.Null2Blank(request.getParameter("$R_PTH")));
    	param.put("I_ICN", Utils.Null2Blank(request.getParameter("$R_ICN")));
    	param.put("I_UYN", "Y"); //사용여부
    	
    	param.put("I_LOGMNU", request.getParameter("I_LOGMNU"));// 메뉴코드isEmpty
        param.put("I_LOGMNM", request.getParameter("I_LOGMNM"));// 메뉴코드isEmpt
        
    	commonService.commonInsert("sysMenuSave", param);
        
        return param;
    }
	
	
	/**
	 * @Method Name : sysMenuUdt
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : void
	 * </pre>
	 * @param request
	 * @param session
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysMenuUdt.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object sysMenuUdt(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	//param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("I_MCD", Utils.Null2Blank(request.getParameter("$R_MCD")));
    	param.put("I_PMCD", Utils.Null2Blank(request.getParameter("$R_PMCD")));
    	param.put("I_SEQ", Utils.Null2Zero(request.getParameter("$R_SEQ")));
    	param.put("I_MNM", Utils.Null2Blank(request.getParameter("$R_MNM")));
    	param.put("I_PTH", Utils.Null2Blank(request.getParameter("$R_PTH")));
    	param.put("I_ICN", Utils.Null2Blank(request.getParameter("$R_ICN")));
    	param.put("I_UYN", Utils.Null2Blank(request.getParameter("$R_UYN")));
    	
    	param.put("I_LOGMNU", request.getParameter("I_LOGMNU"));// 메뉴코드isEmpty
        param.put("I_LOGMNM", request.getParameter("I_LOGMNM"));// 메뉴코드isEmpt
        
    	commonService.commonUpdate("sysMenuUdt", param);
        
        return param;
    }
	
	
	/**
	 * @Method Name : sysMenuDel
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : void
	 * </pre>
	 * @param request
	 * @param session
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysMenuDel.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object sysMenuDel(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	//param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	param.put("I_MCD", Utils.Null2Blank(request.getParameter("$R_MCD")));
    	
    	param.put("I_LOGMNU", request.getParameter("I_LOGMNU"));// 메뉴코드isEmpty
        param.put("I_LOGMNM", request.getParameter("I_LOGMNM"));// 메뉴코드isEmpt
        
    	commonService.commonDelete("sysMenuDel", param);
        
        return param;
    }
}




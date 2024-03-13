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
 * SysAuthController.java
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
public class SysAuthController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	@RequestMapping(value = "/sysAuth.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String sysAuth(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "sys/sysAuth";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	@Autowired  
    private MessageSource messageSource;

	/**
	 * @Method Name : sysAuthDtl
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
	@RequestMapping(value = "/sysAuthDtl.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysAuthDtl(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysAuthDtl", param);
    	
    	param.put("resultList", resultList);
        
        return param;
    }
	
	/**
	 * @Method Name : sysAuthSave
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
	@RequestMapping(value = "/sysAuthSave.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysAuthSave(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param.put("I_ACD", Utils.Null2Blank(request.getParameter("I_ACD")));
    	param.put("I_MCD", Utils.Null2Blank(request.getParameter("$R_MCD")));
    	param.put("I_HRC", Utils.Null2Blank(request.getParameter("I_HRC")));
    	
    	param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
    	
    	param.put("O_MSGCOD", "");
    	param.put("O_ERRCOD","");
    	
    	commonService.commonInsert("sysAuthSave", param);
        
        return param;
    }
	
	/**
	 * @Method Name : sysAuthDel
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
	@RequestMapping(value = "/sysAuthDel.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysAuthDel(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param.put("I_ACD", Utils.Null2Blank(request.getParameter("I_ACD")));
    	
    	param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
    	
    	param.put("O_MSGCOD", "");
    	param.put("O_ERRCOD","");
    	
    	commonService.commonDelete("sysAuthDel", param);
        
        return param;
    }
	
}




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
public class SysRegExFormatController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	@RequestMapping(value = "/sysRegExFormat.do")
	public String sysRegExFormat(HttpSession session){
		return "sys/sysRegExFormat";
	}
	
	@Autowired  
    private MessageSource messageSource;

	/**
	 * @Method Name	: sysRegExFormatList
	 * @author	: twbae
	 * @version : 1.0
	 * @see : 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 * @return type :List<Map<String,Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "/sysRegExFormatList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysRegExFormatList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	
    /*	param.put("I_F120PCD", request.getParameter("I_F120PCD"));
    	param.put("I_F120FNM", request.getParameter("I_F120FNM"));
    	
    	param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
    	
    	param.put("O_MSGCOD", "");
    	param.put("O_ERRCOD","");*/
    	
    	List<Map<String, Object>> sysRegExFormatList = commonService.commonList("sysRegExFormatList", param);
    	
    	param.put("msg", messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, null));
    	logger.debug("msg " + param.get("msg"));
    	
    	param.put("resultList", sysRegExFormatList);
    	
    	return param;
    }
	
	/**
	 * @Method Name	: sysRegExFormatDtl
	 * @author	: twbae
	 * @version : 1.0
	 * @see : 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 * @return type :List<Map<String,Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "/sysRegExFormatDtl.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysRegExFormatDtl(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	
    /*	param.put("I_S009HOS", request.getParameter("I_S009HOS"));
    	
    	param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
    	
    	param.put("O_MSGCOD", "");
    	param.put("O_ERRCOD","");*/
    	
    	List<Map<String, Object>> sysRegExFormatList = commonService.commonList("sysRegExFormatDtl", param);
    	
    	param.put("msg", messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, null));
    	logger.debug("msg " + param.get("msg"));
    	
    	param.put("resultList", sysRegExFormatList);
    	
    	return param;
    }
	
	/**
	 * @Method Name	: sysMenuDel
	 * @author	: twbae
	 * @version : 1.0
	 * @see : 
	 * @param request
	 * @param session
	 * @throws Exception 
	 * @return type :void
	 */
	@ResponseBody
	@RequestMapping(value = "/sysRegExFormatDel.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysRegExFormatDel(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
		
    	//logger.debug(request.getParameter("I_S009HOS"));
    	logger.debug(param);
    	
    	/*param.put("I_S009HOS", request.getParameter("I_S009HOS"));
    	
    	param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
    	
    	param.put("O_MSGCOD", "");
    	param.put("O_ERRCOD","");*/
    	
    	//List<Map<String, Object>> sysMenuDel = commonService.commonDelete("sysMenuDel", param);
    	commonService.commonDelete("sysRegExFormatDel", param);
    	
    	//System.out.println("O_MSGCOD" + param.get("O_MSGCOD"));
    	logger.debug("O_MSGCOD " + param.get("O_MSGCOD"));
    	
    	return param;
    }
	
	/**
	 * @Method Name	: sysRegExFormatSave
	 * @author	: twbae
	 * @version : 1.0
	 * @see : 
	 * @param request
	 * @param session
	 * @throws Exception 
	 * @return type :void
	 */
	@ResponseBody
	@RequestMapping(value = "/sysRegExFormatSave.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object sysRegExFormatSave(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	//logger.debug(request.getParameter("S009001"));
    	logger.debug(request.getParameterNames());
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	/*
    	param.put("I_S009HOS", Utils.Null2Blank(request.getParameter("I_S009HOS")));
    	param.put("I_S009EXT", Utils.Null2Blank(request.getParameter("I_S009EXT")));
    	param.put("I_S009COL", Utils.Null2Blank(request.getParameter("I_S009COL")));
    	param.put("I_S009STR", Utils.Null2Blank(request.getParameter("I_S009STR")));
    	
    	param.put("I_S009001", Utils.Null2Blank(request.getParameter("I_S009001")));
    	param.put("I_S009002", Utils.Null2Blank(request.getParameter("I_S009002")));
    	param.put("I_S009003", Utils.Null2Blank(request.getParameter("I_S009003")));
    	param.put("I_S009004", Utils.Null2Blank(request.getParameter("I_S009004")));
    	param.put("I_S009005", Utils.Null2Blank(request.getParameter("I_S009005")));
    	param.put("I_S009006", Utils.Null2Blank(request.getParameter("I_S009006")));
    	param.put("I_S009007", Utils.Null2Blank(request.getParameter("I_S009007")));
    	param.put("I_S009008", Utils.Null2Blank(request.getParameter("I_S009008")));
    	param.put("I_S009009", Utils.Null2Blank(request.getParameter("I_S009009")));
    	param.put("I_S009010", Utils.Null2Blank(request.getParameter("I_S009010")));
    	param.put("I_S009011", Utils.Null2Blank(request.getParameter("I_S009011")));
    	param.put("I_S009012", Utils.Null2Blank(request.getParameter("I_S009012")));
    	param.put("I_S009013", Utils.Null2Blank(request.getParameter("I_S009013")));
    	param.put("I_S009014", Utils.Null2Blank(request.getParameter("I_S009014")));
    	param.put("I_S009015", Utils.Null2Blank(request.getParameter("I_S009015")));
    	param.put("I_S009016", Utils.Null2Blank(request.getParameter("I_S009016")));
    	param.put("I_S009017", Utils.Null2Blank(request.getParameter("I_S009017")));
    	param.put("I_S009018", Utils.Null2Blank(request.getParameter("I_S009018")));
    	param.put("I_S009019", Utils.Null2Blank(request.getParameter("I_S009019")));
    	param.put("I_S009020", Utils.Null2Blank(request.getParameter("I_S009020")));
    	param.put("I_S009021", Utils.Null2Blank(request.getParameter("I_S009021")));
    	param.put("I_S009022", Utils.Null2Blank(request.getParameter("I_S009022")));
    	param.put("I_S009023", Utils.Null2Blank(request.getParameter("I_S009023")));
    	param.put("I_S009024", Utils.Null2Blank(request.getParameter("I_S009024")));
    	param.put("I_S009044", Utils.Null2Blank(request.getParameter("I_S009044")));
    	param.put("I_S009045", Utils.Null2Blank(request.getParameter("I_S009045")));
    	
    	param.put("I_LOGMNU","sysRegExFormat");   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM","접수 포맷 관리");   //시스템 메뉴의 S001MNM
    	
    	param.put("O_MSGCOD", "");
    	param.put("O_ERRCOD","");
    	*/
    	//List<Map<String, Object>> sysMenuDel = commonService.commonDelete("sysMenuDel", param);
    	commonService.commonInsert("sysRegExFormatSave", param);
    	
    	//System.out.println("O_MSGCOD" + param.get("O_MSGCOD"));
    	logger.debug("O_MSGCOD " + param.get("O_MSGCOD"));
    	
    	return param;
    }
	
}




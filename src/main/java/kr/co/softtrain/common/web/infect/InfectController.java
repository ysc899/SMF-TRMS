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
package kr.co.softtrain.common.web.infect;

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
 * kr.co.softtrain.common.web.infect
 * infectController.java
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
public class InfectController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	
	@RequestMapping(value = "/infect.do")
    public String infect(HttpServletRequest request,HttpSession session) throws Exception{
    	String str = "infect/infect";
    	
    	if(CommonController.AuthPage(request,session)){
			str = "index";
		}
    	
        return str;
    }
	
	@RequestMapping(value = "/infect01.do")
	public String infect01(HttpSession session){
		return "infect/infect01";
	}
	
	@Autowired  
    private MessageSource messageSource;

	/**
	 * @Method Name	: sysMenuList
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
	@RequestMapping(value = "/infectList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object infectList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	
    	param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
    	List<Map<String, Object>> infectList = commonService.commonList("infectList", param);
    	
    	param.put("resultList", infectList);
    	
    	return param;
    }
	
	/**
	 * @Method Name	: infectStdList
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
	@RequestMapping(value = "/infectStdList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST}
	  , produces="application/json;charset=UTF-8")
	public Object infectStdList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> infectStdList = commonService.commonList("infectStdList", param);
    	
    	param.put("msg", messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, null));
    	logger.debug("msg " + param.get("msg"));
    	param.put("resultList", infectStdList);
    	
    	return param;
    }
	
}




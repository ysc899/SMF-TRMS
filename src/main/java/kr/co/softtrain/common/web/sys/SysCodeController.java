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
 * SysCodeController.java
 * </pre>
 * @title :  
 * @author : jsyoo
 * @since : 2018. 12. 7.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 7.		jsyoo	 			최초생성
 * 
 * </pre>
 */
@Controller
public class SysCodeController  {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    @RequestMapping(value = "/sysCode.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String sysCode(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "sys/sysCode";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
    
    @Autowired  
    private MessageSource messageSource;
    
    /**
     * @Method Name : sysCodeList
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
    @RequestMapping(value = "/sysCodeList.do"
      , method = {RequestMethod.GET, RequestMethod.POST})
//    , produces="application/json;charset=UTF-8")
    public Object sysCodeList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> resultList = commonService.commonList("sysCodeList", param);
        
        param.put("resultList", resultList);
        
        return param;
    }
    
    /**
     * @Method Name : sysCodeDtl
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
    @RequestMapping(value = "/sysCodeDtl.do"
      , method = {RequestMethod.GET, RequestMethod.POST})
      //, produces="application/json;charset=UTF-8")
    public Object sysCodeDtl(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> resultList = commonService.commonList("sysCodeDtl", param);
        
        param.put("resultList", resultList);
        
        return param;
    }
    
    
    /**
     * @Method Name : sysCodeSave
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
    @RequestMapping(value = "/sysCodeSave.do"
      , method = {RequestMethod.GET, RequestMethod.POST})
      //, produces="application/json;charset=UTF-8")
    public Object sysCodeSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("I_SCD", Utils.Null2Blank(request.getParameter("$R_SCD")));
        param.put("I_PSCD", Utils.Null2Blank(request.getParameter("$R_PSCD")));
        param.put("I_CNM", Utils.Null2Blank(request.getParameter("$R_CNM")));
        param.put("I_DSC", Utils.Null2Blank(request.getParameter("$R_DSC")));
        param.put("I_SEQ", Utils.Null2Zero(request.getParameter("$R_SEQ")));
        param.put("I_RF1", Utils.Null2Blank(request.getParameter("$R_RF1")));
        param.put("I_RF2", Utils.Null2Blank(request.getParameter("$R_RF2")));
        param.put("I_RF3", Utils.Null2Blank(request.getParameter("$R_RF3")));
        
        param.put("I_UYN", "Y"); //사용여부
        
        param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));
        
        param.put("O_MSGCOD", "");
        param.put("O_ERRCOD", "");
        
        commonService.commonInsert("sysCodeSave", param);
        
        return param;
        
        
    }
    
    /**
     * @Method Name : sysCodeChkSave
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
    @RequestMapping(value = "/sysCodeChkSave.do"
      , method = {RequestMethod.GET, RequestMethod.POST})
      //, produces="application/json;charset=UTF-8")
    public Object sysCodeChkSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("I_SCD", Utils.Null2Blank(request.getParameter("$R_SCD")));
        param.put("I_PSCD", Utils.Null2Blank(request.getParameter("$R_PSCD")));
        param.put("I_CNM", Utils.Null2Blank(request.getParameter("$R_CNM")));
        param.put("I_DSC", Utils.Null2Blank(request.getParameter("$R_DSC")));
        param.put("I_SEQ", Utils.Null2Zero(request.getParameter("$R_SEQ")));
        param.put("I_RF1", Utils.Null2Blank(request.getParameter("$R_RF1")));
        param.put("I_RF2", Utils.Null2Blank(request.getParameter("$R_RF2")));
        param.put("I_RF3", Utils.Null2Blank(request.getParameter("$R_RF3")));
        
        param.put("I_UYN", "Y"); //사용여부
        
        param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));
        
        param.put("O_MSGCOD", "");
        param.put("O_ERRCOD", "");
        
        List<Map<String, Object>> resultList = commonService.commonList("sysCodeChkSave", param);
        
        param.put("resultList", resultList);
        
        return param;
    }
    
    /**
     * @Method Name : sysCodeUdt
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
    @RequestMapping(value = "/sysCodeUdt.do"
      , method = {RequestMethod.GET, RequestMethod.POST})
      //, produces="application/json;charset=UTF-8")
    public Object sysCodeUdt(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("I_SCD", Utils.Null2Blank(request.getParameter("$R_SCD")));
        param.put("I_PSCD",Utils.Null2Blank(request.getParameter("$R_PSCD")));
        param.put("I_CNM", Utils.Null2Blank(request.getParameter("$R_CNM")));
        param.put("I_DSC", Utils.Null2Blank(request.getParameter("$R_DSC")));
        param.put("I_SEQ", Utils.Null2Zero(request.getParameter("$R_SEQ")));
        param.put("I_CNM", Utils.Null2Blank(request.getParameter("$R_CNM")));
        param.put("I_RF1", Utils.Null2Blank(request.getParameter("$R_RF1")));
        param.put("I_RF2", Utils.Null2Blank(request.getParameter("$R_RF2")));
        param.put("I_RF3", Utils.Null2Blank(request.getParameter("$R_RF3")));
        param.put("I_UYN", Utils.Null2Blank(request.getParameter("$R_UYN")));
        
        param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));
        
        param.put("O_MSGCOD", "");
        param.put("O_ERRCOD", "");
     
        commonService.commonUpdate("sysCodeUdt", param);
        
        return param;
    }
    
    /**
     * 하위코드삭제
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sysCodeDel.do"
      , method = {RequestMethod.GET, RequestMethod.POST})
      //, produces="application/json;charset=UTF-8")
    public Object sysCodeDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("I_SCD", request.getParameter("$R_SCD"));
        param.put("I_PSCD", request.getParameter("$R_PSCD"));
        
        param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));
    	
    	param.put("O_MSGCOD", "");
        param.put("O_ERRCOD", "");
        
        commonService.commonDelete("sysCodeDel", param);
        
        return param;
    }

    /**
     * @Method Name : getCommCode
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
	@RequestMapping(value = "/getCommCode.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object getCommCode(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("getCommCode", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    	
    }
    /**
     * @Method Name : getCommCode
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
	@RequestMapping(value = "/getMC999DCommCode.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object geMC999DtCommCode(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("getMC999DCommCode", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    	
    }
}




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

import java.util.ArrayList;
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

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.sys
 * SysTestReqController.java
 * </pre>
 * @title :  의뢰목록 관리
 * @author : seegene3
 * @since : 2019. 1. 2.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2019. 1. 2.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysTestReqController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;

	/**
	 * @Method Name	: sysTestReq
	 * @see
	 * <pre>
	 * Method 설명 : 의뢰목록관리 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysTestReq.do")
	public String testReq(HttpServletRequest request,HttpSession session) throws Exception {
		String str = "sys/sysTestReq";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	/**
	 * @Method Name	: sysTestReq01
	 * @see
	 * <pre>
	 * Method 설명 : 의뢰목록관리 팝업 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysTestReq01.do")
	public String sysTestReq01(HttpServletRequest request,HttpSession session) throws Exception {
		String str = "sys/sysTestReq01";
		return str;
	}
	
	/**
	 * @Method Name	: testReqDtl
	 * @see
	 * <pre>
	 * Method 설명 : 추가 검사 항목 리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysTestReq01List.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public Object testReqDtl(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
//    	List<Map<String, Object>> resultList = commonService.commonList("testReqDtl", param);
    	
		//환자정보

    	List<Map<String, Object>> resultList = commonService.commonList("rstUserDtl2", param);
    	
    	

//    	List<Map<String, Object>> rstUserDtl2 = commonService.commonList("rstUserDtl2", param_rstUserDtl2);
    	
    	
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }

    
	/**
	 * @Method Name	: sysTestReqSave
	 * @see
	 * <pre>
	 * Method 설명 : 수진자 접수목록 (추가의뢰정보) 승인
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	
	@ResponseBody
	@RequestMapping(value = "/sysTestReqSave1.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
	public Object sysTestReqSave(HttpServletRequest request,HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>>  paramList = new ArrayList();
		String JSONROW = request.getParameter("JSONROW")==null ? "" : request.getParameter("JSONROW");
		
		if(!"".equals(JSONROW)){
			paramList =  Utils.jsonList(request.getParameter("JSONROW").toString());	// 데이터 Parameter 상태로 변경
		}
	  	Object obj =  commonService.commonListOne("sysTestReqCheck", param, paramList);
	  	if("200".equals(param.get("O_MSGCOD"))){
	  		commonService.commonListUpdate2("sysTestReqSave2", param, paramList,"testReqDtl","sysTestReqMC185RM");
	  	}
		return param;
	}
    /**
     * @Method Name	: sysTestReqSave2
     * @see
     * <pre>
     * Method 설명 :  (추가의뢰정보) 승인
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    
    @ResponseBody
    @RequestMapping(value = "/sysTestReqSave2.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysTestReqSave2(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>>  paramList = new ArrayList();
		String JSONROW = request.getParameter("JSONROW")==null ? "" : request.getParameter("JSONROW");
	  	if(!"".equals(JSONROW)){
			paramList =  Utils.jsonList(request.getParameter("JSONROW").toString());	// 데이터 Parameter 상태로 변경
			
			logger.debug(paramList.size());
			logger.debug(paramList.get(0));
	  	}
	  	Object obj =  commonService.commonListOne("sysTestReqCheck", param, paramList);
	  	if("200".equals(param.get("O_MSGCOD"))){
	        commonService.commonListUpdate("sysTestReqSave2", param, paramList,"sysTestReqMC185RM");
	  	}
		logger.debug("=======sysTestReqSave2===================================="+param);
	  	return param;
    }
    
    
    
    /**
     * @Method Name	: sysTestReqSave2
     * @see
     * <pre>
     * Method 설명 :  (추가의뢰정보) 승인
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    
    @ResponseBody
    @RequestMapping(value = "/sysTestReqCheck.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysTestReqCheck(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
		logger.debug("---------------------------------------------------------------");
		List<Map<String, Object>>  paramList = new ArrayList();
		String JSONROW = request.getParameter("JSONROW")==null ? "" : request.getParameter("JSONROW");
		
	  	if(!"".equals(JSONROW)){
			paramList =  Utils.jsonList(request.getParameter("JSONROW").toString());	// 데이터 Parameter 상태로 변경
			
			logger.debug(paramList.size());
			logger.debug(paramList.get(0));
	  	}
	  	//sysTestReqCheck
	  	Object obj =  commonService.commonListOne("sysTestReqCheck", param, paramList);
		logger.debug("========sysTestReqCheck========================================="+obj);
		logger.debug("=======sysTestReqCheck===================================="+param);
	  	return param;
    }

}




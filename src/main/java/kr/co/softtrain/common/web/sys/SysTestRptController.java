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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
//import antlr.Utils;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.sys
 * SysTestRptController.java
 * </pre>
 * @title : 항목별 결과 양식 관리
 * @author : seegene3
 * @since : 2018. 12. 20.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 20.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysTestRptController  {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    /**
     * @Method Name	: getSysTestRpt
     * <pre>
     * Method 설명 : sysTestRpt.jsp 호출
     * return_type :String
     * </pre>
     * @param session
     * @return 
     */
    @RequestMapping(value = "/sysTestRpt.do")
    public String getSysTestRpt(HttpServletRequest request,HttpSession session) throws Exception {
    	String str ="sys/sysTestRpt";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
        return str;
    }


    /**
     * @Method Name	: sysTestRptDtl
     * @see
     * <pre>
     * Method 설명 : 폼별결과 양식 내역 조회
     * return_type :Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysTestRptList.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysTestRptList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	List<Map<String, Object>> resultList = commonService.commonList("sysTestRptList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    /**
     * @Method Name	: sysTestRptSave
     * @see
     * <pre>
     * Method 설명 : 폼별결과 양식 내역 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    
    @ResponseBody
    @RequestMapping(value = "/sysTestRptSave.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysTestRptSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
		logger.debug("---------------------------------------------------------------");
		List<Map<String, Object>>  paramList = new ArrayList();
		String JSONROW = request.getParameter("JSONROW")==null ? "" : request.getParameter("JSONROW");
		
	  	if(!"".equals(JSONROW)){
			paramList =  Utils.jsonList(request.getParameter("JSONROW").toString());	// 데이터 Parameter 상태로 변경
			
			logger.debug(paramList.size());
			logger.debug(paramList.get(0));
	  	}
//		param.put("JSONROW", paramList);
			
			
        
//	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
//        param.put("I_GCD", Utils.isNull(param.get("F010GCD")));
//        param.put("I_TIT", Utils.isNull(param.get("S018TIT")));
//        param.put("I_RGN", Utils.isNull(param.get("S018RGN")));
//        param.put("I_SYN", Utils.isNull(param.get("S018SYN")));
//        param.put("I_RFN", Utils.isNull(param.get("S018RFN")));
//        
        commonService.commonListInsert("sysTestRptSave", param, paramList);
        
        

		logger.debug(param);
        
        
        
	  	return param;
    }
}




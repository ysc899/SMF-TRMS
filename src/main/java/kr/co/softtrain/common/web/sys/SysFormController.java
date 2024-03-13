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
 * SysFormController.java
 * </pre>
 * @title : 폼별 결과 양식 관리
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
public class SysFormController  {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    /**
     * @Method Name	: getSysForm
     * @see
     * <pre>
     * Method 설명 : 폼별결과양식 페이지 
     * return_type : String
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sysForm.do")
    public String getSysForm(HttpServletRequest request,HttpSession session) throws Exception {
    	String str = "sys/sysForm";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
        return str;
    }

    /**
     * @Method Name	: sysFormList
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
    @RequestMapping(value = "/sysFormList.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysFormList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	List<Map<String, Object>> resultList = commonService.commonList("sysFormList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    /**
     * @Method Name	: sysFormCheck
     * @see
     * <pre>
     * Method 설명 : 폼별결과 양식 내역 체크
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysFormCheck.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysFormCheck(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        param.put("I_RFM", Utils.isNull(param.get("S011RFM")));
        param.put("I_RCL", Utils.isNull(param.get("S011RCL")));
        param.put("I_RTP", Utils.isNull(param.get("S011RTP")));

	  	List<Map<String, Object>> resultList = commonService.commonList("sysFormCheck", param);
	  	param.put("resultList", resultList);
    	
    	return param;
    }
    /**
     * @Method Name	: sysFormSave
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
    @RequestMapping(value = "/sysFormSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysFormSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        param.put("I_RFM", Utils.isNull(param.get("S011RFM")));
        param.put("I_RCL", Utils.isNull(param.get("S011RCL")));
        param.put("I_RTP", Utils.isNull(param.get("S011RTP")));
        
        commonService.commonInsert("sysFormSave", param);
        
	  	return param;
    }
    
    /**
     * @Method  sysFormUdt
     * @see
     * <pre>
     * Method 설명 : 폼별결과 양식 내역 수정
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysFormUdt.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysFormUdt(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        param.put("I_RFM", Utils.isNull(param.get("S011RFM")));
        param.put("I_RCL", Utils.isNull(param.get("S011RCL")));
        param.put("I_RTP", Utils.isNull(param.get("S011RTP")));

       commonService.commonUpdate("sysFormUdt", param);
        
	  	return param;
        
    }

    /**
     * @Method Name	: sysFormDel
     * @see 
     * <pre>
     * Method 설명 : 폼별결과 양식 내역 삭제
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysFormDel.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysFormDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        param.put("I_RFM", Utils.isNull(param.get("S011RFM")));
        param.put("I_RCL", Utils.isNull(param.get("S011RCL")));
        param.put("I_RTP", Utils.isNull(param.get("S011RTP")));
        
        commonService.commonDelete("sysFormDel", param);
        
 	  	return param;
    }
    
}




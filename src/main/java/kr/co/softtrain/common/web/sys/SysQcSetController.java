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
 * SysQcSetController.java
 * </pre>
 * @title :  Quick Setup
 * @author : seegene3
 * @since : 2018. 11. 26.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 26.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysQcSetController  {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    /**
     * @Method Name	: getSysQcSet
     * <pre>
     * Method 설명 : sysQcSet.jsp 호출
     * return_type :String
     * </pre>
     * @param session
     * @return 
     */
    @RequestMapping(value = "/sysQcSet.do")
    public String getSysQcSet(HttpServletRequest request,HttpSession session) throws Exception {
    	String str = "sys/sysQcSet";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
        return str;
    }

    /**
     * @Method Name : goMainQcSet
     * @see
     * <pre>
     * Method 설명 :  mainQcSet.jsp 호출
     * return_type : String
     * </pre>
     * @param session
     * @return 
     */
    @RequestMapping(value = "/mainQcSet.do")
    public String goMainQcSet(HttpSession session){
        return "main/mainQcSet";
    }

    /**
     * @Method Name	: sysQcSetDtl1
     * @see
     * <pre>
     * Method 설명 : 메뉴별 검색어 조회
     * return_type :Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysQcSetDtl1.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysQcSetDtl1(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	List<Map<String, Object>> resultList = commonService.commonList("sysQcSetDtl1", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    /**
     * @Method Name	: sysQcSetSave1
     * @see
     * <pre>
     * Method 설명 : 검색어 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysQcSetCheck.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysQcSetCheck(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

        param.put("I_MCD", Utils.isNull(param.get("S007MCD")));
        param.put("I_RCD", Utils.isNull(param.get("S007RCD")));
        param.put("I_RNM", Utils.isNull(param.get("S007RNM")));
        param.put("I_RCV", Utils.isNull(param.get("S007RCV")));
        
        commonService.commonInsert("sysQcSetCheck", param);
        
	  	return param;
    }
    /**
     * @Method Name	: sysQcSetSave1
     * @see
     * <pre>
     * Method 설명 : 검색어 저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysQcSetSave1.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysQcSetSave1(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("I_MCD", Utils.isNull(param.get("S007MCD")));
    	param.put("I_RCD", Utils.isNull(param.get("S007RCD")));
    	param.put("I_RNM", Utils.isNull(param.get("S007RNM")));
    	param.put("I_RCV", Utils.isNull(param.get("S007RCV")));
    	
    	commonService.commonInsert("sysQcSetSave1", param);
    	
    	return param;
    }
    
    /**
     * @Method  sysQcSetUdt1
     * @see
     * <pre>
     * Method 설명 : 검색어 수정
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysQcSetUdt1.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysQcSetUdt1(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

        param.put("I_MCD", Utils.isNull(param.get("S007MCD")));
        param.put("I_RCD", Utils.isNull(param.get("S007RCD")));
        param.put("I_RNM", Utils.isNull(param.get("S007RNM")));
        param.put("I_RCV", Utils.isNull(param.get("S007RCV")));
        
       commonService.commonUpdate("sysQcSetUdt1", param);
        
	  	return param;
        
    }

    /**
     * @Method Name	: sysQcSetDel1
     * @see 
     * <pre>
     * Method 설명 : 검색어 삭제
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysQcSetDel1.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysQcSetDel1(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

        param.put("I_MCD", Utils.isNull(param.get("S007MCD")));
        param.put("I_RCD", Utils.isNull(param.get("S007RCD")));
        commonService.commonDelete("sysQcSetDel1", param);
        
 	  	return param;
    }
    
    /**
     * @Method Name : mainQcSetMenuList
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
    @RequestMapping(value = "/mainQcSetMenuList.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object mainQcSetMenuList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_AGR", Utils.isNull(param.get("I_AGR")));
	  	logger.debug("--------test : " + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("mainQcSetMenuList", param);
	  	
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    /**
     * @Method Name : mainQcSetList
     * @see
     * <pre>
     * Method 설명 : 메인 Quick Setup 조회
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/mainQcSetList.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object mainQcSetList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_MCD", Utils.isNull(param.get("I_MCD")));
	  	/* 메뉴 Quick Setup 조회에서도 사용 
	  	 * MainController 참조
	  	 * MenuQcSetList.do
	  	 * */
	  	List<Map<String, Object>> resultList = commonService.commonList("mainQcSetList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    
    /**
     * @Method Name : mainQcSetSave
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
    @RequestMapping(value = "/mainQcSetSave.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object mainQcSetSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_MCD", Utils.isNull(param.get("I_MCD")));
	  	param.put("I_RCD", Utils.isNull(param.get("I_RCD")));
	  	param.put("I_VCD", Utils.isNull(param.get("I_VCD")));
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("mainQcSetSave", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    /**
     * @Method Name : mainQcSetUdt
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
    @RequestMapping(value = "/mainQcSetUdt.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object mainQcSetUdt(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_MCD", Utils.isNull(param.get("I_MCD")));
	  	param.put("I_RCD", Utils.isNull(param.get("I_RCD")));
	  	param.put("I_VCD", Utils.isNull(param.get("I_VCD")));
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("mainQcSetUdt", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
}




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
 * SysImgMngController.java
 * </pre>
 * @title :  병원별 결과지 관리
 * @author : seegene3
 * @since : 2018. 12. 26.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 26.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysImgMngController  {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    /**
     * @Method Name	: getSysImgMng
     * <pre>
     * Method 설명 : sysImgMng.jsp 호출
     * return_type :String
     * </pre>
     * @param session
     * @return 
     */
    @RequestMapping(value = "/sysImgMng.do")
    public String getSysImgMng(HttpServletRequest request,HttpSession session) throws Exception {
    	String str = "sys/sysImgMng";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
        return str;
    }

    /**
     * @Method Name	: sysImgMngList
     * @see
     * <pre>
     * Method 설명 : 병원정보
     * return_type :Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysImgMngList.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysImgMngList(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	List<Map<String, Object>> resultList = commonService.commonList("sysImgMngList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
    
    /**
     * @Method Name	: sysImgMngDtl
     * @see
     * <pre>
     * Method 설명 : 파일명 규칙 상세 조회
     * return_type :Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysImgMngDtl.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object sysImgMngDtl1(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysImgMngDtl", param);
    	param.put("resultList", resultList);
    	
    	return param;
    }
    
    /**
     * @Method Name	: sysImgMngSave1
     * @see
     * <pre>
     * Method 설명 : 파일명 규칙  저장
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysImgMngSave.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysImgMngSave(HttpServletRequest request,HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	commonService.commonInsert("sysImgMngSave", param);
    	
    	return param;
    }
    
    /**
     * @Method  sysImgMngUdt
     * @see
     * <pre>
     * Method 설명 : 파일명 규칙  수정
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysImgMngUdt.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysImgMngUdt(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        
       commonService.commonUpdate("sysImgMngUdt", param);
        
	  	return param;
        
    }

    /**
     * @Method Name	: sysImgMngDel1
     * @see 
     * <pre>
     * Method 설명 : 파일명 규칙  삭제
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/sysImgMngDel.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object sysImgMngDel1(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
        commonService.commonDelete("sysImgMngDel", param);
        
 	  	return param;
    }
}




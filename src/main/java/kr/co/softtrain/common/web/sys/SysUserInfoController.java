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
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.sys
 * SysUserInfoController.java
 * </pre>
 * @title :  병원(회원) 조회
 * @author : seegene3
 * @since :  2018. 11. 20
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 20.		 ejlee			최초생성
 * 
 * </pre>
 */
@Controller
public class SysUserInfoController  {
	Logger logger = LogManager.getLogger( SysUserInfoController.class);
    
	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name	: goSysUserInfo
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 조회 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysUserInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goSysUserInfo(HttpServletRequest request, HttpSession session) throws Exception {
		String str = "sys/sysUserInfo";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name	: gosysUserInfo01
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 상세팝업호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysUserInfo01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goSysUserInfo01(HttpServletRequest request, HttpSession session){
		String str = "sys/sysUserInfo01";
		return str;
	}

	/**
	 * @Method Name	: getSysUserInfoList
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysUserInfoList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getSysUserInfoList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
    	param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
	  	
	  	logger.debug(param);
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("sysUserMngList", param);

	  	param.put("resultList", resultList);
	  	return param;
	}

	
}

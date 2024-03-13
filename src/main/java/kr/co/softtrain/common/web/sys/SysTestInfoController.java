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
 * SysTestInfoController.java
 * </pre>
 * @title :   의뢰목록 조회
 * @author : seegene3
 * @since : 2019. 2. 18.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2019. 2. 18.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysTestInfoController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;

	/**
	 * @Method Name	: sysTestInfo
	 * @see
	 * <pre>
	 * Method 설명 : 의뢰목록조회 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysTestInfo.do")
	public String testReq(HttpServletRequest request,HttpSession session) throws Exception {
		String str = "sys/sysTestInfo";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}

	/**
	 * @Method Name	: sysTestInfoList
	 * @see
	 * <pre>
	 * Method 설명 : 추가검사 수진자 접수 목록 조회 
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysTestInfoList.do" , method = {RequestMethod.GET, RequestMethod.POST})
	public Object testReqList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
		param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysTestInfoList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
	

}



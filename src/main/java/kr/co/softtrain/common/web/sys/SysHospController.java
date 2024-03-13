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

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.sys
 * SysHospController.java
 * </pre>
 * @title :  담당자별 병원 조회
 * @author : ejlee
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
public class SysHospController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;

	/**
	 * @Method Name	: sysHosp
	 * @see
	 * <pre>
	 * Method 설명 : 담당자별 병원 조회 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysHosp.do")
	public String sysHosp(HttpServletRequest request, HttpSession session) throws Exception {
		String str = "sys/sysHosp";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	

	/**
	 * @Method Name	: sysHospList
	 * @see
	 * <pre>
	 * Method 설명 : 당당자 리스트 조회 
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysHospList.do" , method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysHospList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("sysHospList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
	
	/**
	 * @Method Name	: sysHospDtl
	 * @see
	 * <pre>
	 * Method 설명 : 담당자별 병원리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysHospDtl.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public Object sysHospDtl(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	List<Map<String, Object>> resultList = commonService.commonList("sysHospDtl", param);
    	
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
}




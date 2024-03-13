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
package kr.co.softtrain.common.web.ocs;

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
 * kr.co.softtrain.common.web.ocs
 * TestItemMapController.java
 * </pre>
 * @title :  검사항목 매핑 조회
 * @author : seegene3
 * @since : 2018. 12. 13.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 13.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class TestItemMapController  {
	private static final int Map = 0;

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
	
    @Autowired  
    private MessageSource messageSource;

	/**
	 * @Method Name	: goTestItemMap
	 * @see
	 * <pre>
	 * Method 설명 : testItemMap 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/testItemMap.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goTestItemMap(HttpServletRequest request, HttpSession session)throws Exception {
		String str = "ocs/testItemMap";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name	: goTestItemMap01
	 * @see
	 * <pre>
	 * Method 설명 : testItemMap01 팝업 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/testItemMap01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goTestItemMap01(HttpServletRequest request, HttpSession session){
		String str = "ocs/testItemMap01";
		return str;
	}

	/**
	 * @Method Name	: getTestItemMapList
	 * @see
	 * <pre>
	 * Method 설명 : 상세 리스트 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/testItemMapList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getTestItemMapList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	String param_GAD = param.get("I_GAD").toString();
	  	String strArry = "";
	  	
	  	if(Utils.isNullBool(param_GAD)){
	  		for(int i=0;i<param_GAD.length(); i = i+5){
	  			strArry += "|"+param_GAD.substring(i, i+5)+"";
	  		}
		  	if(!"||".equals(strArry)){
		  		param.put("I_GAD", strArry+"|");
		  	}
	  	}
	  	List<Map<String, Object>> resultList = commonService.commonList("testItemMapList", param);
	  	param.put("resultList", resultList);
	  
	  	return param;
	}
}



















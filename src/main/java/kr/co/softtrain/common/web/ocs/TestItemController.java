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

import kr.co.softtrain.resultImg.web.QrCodeUtils;

/**
 * <pre>
 * kr.co.softtrain.common.web.ocs
 * TestItemController.java
 * </pre>
 * @title :  검사항목 조회
 * @author : seegene3
 * @since : 2018. 12. 03.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 03.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class TestItemController  {
	private static final int Map = 0;

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
	
    @Autowired  
    private MessageSource messageSource;

	/**
	 * @Method Name	: goTestItem
	 * @see
	 * <pre>
	 * Method 설명 : testItem 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/testItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goTestItem(HttpServletRequest request, HttpSession session)throws Exception {
		String str = "ocs/testItem";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name	: goTestItem01
	 * @see
	 * <pre>
	 * Method 설명 : testItem01 팝업 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/testItem01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goTestItem01(HttpServletRequest request, HttpSession session){
		String str = "ocs/testItem01";
		return str;
	}

	/**
	 * @Method Name	: getTestItemList
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
	@RequestMapping(value = "/testItemList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getTestItemList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	Object[] args = new Object[0];
	  	
	  	if(param.containsKey("I_ISC")){
	  		args =  request.getParameterValues("I_ISC");
	  	}
	  	
	  	String tt = "";
	  	if(args.length>0){
	  		for (Object object : args) {
  				tt += "|"+object+"";
	  		}
	  		tt += "|";
	  	}
	  	param.put("I_ISC", tt);
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("testItemList", param);
	  	param.put("resultList", resultList);
	  
	  	return param;
	}

	/**
	 * @Method Name	: testItemDtl
	 * @see
	 * <pre>
	 * Method 설명 : 검사항목 조회 상세
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/testItemDtl.do", method = {RequestMethod.GET, RequestMethod.POST})
	//	  , produces="application/json;charset=UTF-8")
	public Object testItemDtl(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	List<Map<String, Object>> resultList = commonService.commonList("testItemDtl", param);

		//url 을 qr코드로 변환
		if(!"".equals(String.valueOf(resultList.get(0).get("T001URL")))){
			resultList.get(0).put("T001URLQR", QrCodeUtils.generateQRCodeImage(String.valueOf(resultList.get(0).get("T001URL")), 350, 350));
		}

	  	param.put("resultList", resultList);
//	
	  	return param;
	}


}

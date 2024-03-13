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
package kr.co.softtrain.common.web.rstInfo;

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
 * kr.co.softtrain.common.web.rstInfo
 * RecvImgController.java
 * </pre>
 * @title :  이미지결과수신
 * @author : seegene3
 * @since : 2019. 1. 23.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2019. 1. 23.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class RecvImgController  {

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;

	
	/**
	 * @Method Name	: gorecvImg
	 * @see
	 * <pre>
	 * Method 설명 : 이미지결과수신 호출 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvImg.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gorecvImg(HttpServletRequest request, HttpSession session) throws Exception {
		String str = "rstInfo/recvImg";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	/**
	 * @Method Name	: gorecvImg
	 * @see
	 * <pre>
	 * Method 설명 : 이미지결과수신 ActiveX 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvImgDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String recvImgDown(HttpServletRequest request, HttpSession session){
		String str = "rstInfo/recvImgDown";
		return str;
	}
	
	/**
	 * @Method Name	: getRecvImgList
	 * @see
	 * <pre>
	 * Method 설명 : 이미지결과수신 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/recvImgList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getRecvImgList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		//시작일자
		param.put("I_FDT", Utils.isNull(param.get("I_FDT")).replaceAll("-", "")); 
		//종료일자
		param.put("I_TDT", Utils.isNull(param.get("I_TDT")).replaceAll("-", "")); 
		param.put("I_RECV", Utils.isNull(param.get("I_RECV"),"A")); 
        //MWW03T12
	  	commonService.commonOne("MWW03T12", param);
        
	  	List<Map<String, Object>> resultList = commonService.commonList("recvImgList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
	}

}

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
package kr.co.softtrain.common.web.rst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.debug.NewLineListener;
//import antlr.Utils;
import kr.co.softtrain.custom.util.*;
import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;


/**
 * <pre>
 * kr.co.softtrain.common.web.rst
 * SysMenuController.java
 * </pre>
 * @title :  
 * @author : twbae
 * @since : 2018. 12. 12.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 12.		twbae	 			최초생성
 * 
 * </pre>
 */
@Controller
public class RstItemController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	
	@RequestMapping(value = "/rstItem.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String rstItem(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rst/rstItem";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	
   
	@Autowired  
    private MessageSource messageSource;

	/**
	 * @Method Name : rstUserList
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
	@RequestMapping(value = "/rstItemList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstItemList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
    	Object[] args = new Object[0];
	  	if(param.containsKey("I_RGN")){
	  		args =  request.getParameterValues("I_RGN");
	  	}

	  	param.put("I_RGN1", "");
	  	param.put("I_RGN2", "");
	  	param.put("I_RGN3", "");
	  	if(args.length>0){
	  		for (Object object : args) {
	  			param.put("I_RGN"+object, "Y");
	  		}
	  	}
	  	
        
		//param.put("I_ICNT", "40");            //읽을갯수
    	//param.put("I_PNN", "C");			  //이전다음
    	//param.put("I_DAT", "0");            
    	//param.put("I_JNO", "0");			
    	
	  	System.out.println("## 로그인한 유저 권한 :: "+Utils.isNull(request.getParameter("loginUser_authority")));
    	
    	List<Map<String, Object>> resultList;
    //IDR 김종범 미래연도데이터 로직주석처리	
/*    	if("CUSTOMER_HEALTH".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "CUSTOMER_HEALTH_IMG".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "ADMIN".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "IT".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "SUPPORT".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "SUPPORT_REALTIME".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "BRC_MNG".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "HOSP_NMG".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "LAB".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "LAB_REALTIME".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    		|| "TEST".equals(Utils.isNull(request.getParameter("loginUser_authority")))
    	){
    		// 23~30년 바코드 조회
    		resultList = commonService.commonList("rstItemList_corona", param);
    	}else{
    		// 입력받은 년도 데이터만 조회
    		resultList = commonService.commonList("rstItemList", param);
    	}*/
	      
    	//List<Map<String, Object>> resultList = commonService.commonList("rstItemList", param);
    	resultList = commonService.commonList("rstItemList", param);
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	
}





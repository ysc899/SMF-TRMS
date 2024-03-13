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

import java.security.PrivateKey;
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
 * SysUserMngController.java
 * </pre>
 * @title :  병원(회원) 관리
 * @author : seegene3
 * @since : 2018. 11. 13.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 13.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class SysUserMngController  {

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
		
	/**
	 * @Method Name	: goSysUserMng
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 관리 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysUserMng.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goSysUserMng(HttpServletRequest request, HttpSession session)throws Exception{
		String str = "sys/sysUserMng";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	/**
	 * @Method Name	: gosysUserMng01
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 상세 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysUserMng01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gosysUserMng01(HttpServletRequest request, HttpSession session)throws Exception{
		String str = "sys/sysUserMng01";
		return str;
	}
	/**
	 * @Method Name	: goSysUserMng02
	 * @see
	 * <pre>
	 * Method 설명 : 로그인 사용자 정보 수정 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/sysUserMng02.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goSysUserMng02(HttpServletRequest request, HttpSession session)throws Exception{
		String str = "sys/sysUserMng02";
		return str;
	}
	/**
	 * @Method Name	: getSysUserMngList
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
	@RequestMapping(value = "/sysUserMngList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getSysUserMngList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	List<Map<String, Object>> resultList = commonService.commonList("sysUserMngList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
	/**
	 * @Method Name	: getSysUserMngDtl
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원)  상세정보
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysUserMngDtl.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getSysUserMngDtl(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		List<Map<String, Object>> resultList = commonService.commonList("sysUserMngDtl", param);
		param.put("resultList", resultList);
		
		return param;
	}

	/**
	 * @Method Name	: sysUserMngAuthSave
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 권한 저장
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysUserMngAuthSave.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysUserMngAuthSave(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	param.put("I_LOGCOR", request.getParameter("LOGCOR"));
	  	param.put("I_LOGLID", request.getParameter("LOGLID"));
	  	param.put("I_AUTH", request.getParameter("S005AGR"));
	  	param.put("I_SYN", request.getParameter("S005SYN"));
		
	  	commonService.commonInsert("sysUserMngAuthSave", param);
	  	return param;
	}
	


	/**
	 * @Method Name	: sysUserMngDel
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 삭제
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysUserMngDel.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysUserMngDel(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	param.put("I_LOGCOR", param.get("LOGCOR"));
	  	param.put("I_LOGLID", param.get("LOGLID"));
	  	param.put("I_USERDIV", param.get("USERDIV"));
	  	
	  	commonService.commonDelete("sysUserMngDel", param);
//	
	  	return param;
	}
	
	/**
	 * @Method Name	: setSysUserMngSave
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 저장
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysUserMngSave.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object setSysUserMngSave(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	
	  	String I_PASASW_SHA = "";
	  	
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	// 세션키를 사용하여 암호화키를 생성한다.
	  	PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
	  	
	  	if (privateKey == null) {
	  		throw new RuntimeException("PrivateKey Error");
	  	}
	  	
		//암호화 파라미터 디코드
		try {
			if(param.containsKey("I_LOGLID")){
				param.put("I_LOGLID",  Utils.decryptRsa(privateKey, param.get("I_LOGLID")));
			}
			if(param.containsKey("I_PASASW")){
				param.put("I_PASASW",  Utils.decryptRsa(privateKey, param.get("I_PASASW")));
				
				I_PASASW_SHA = param.get("I_PASASW").toString();
				
				if(!"".equals(I_PASASW_SHA)){
					param.put("I_PASASW_SHA",  CommonController.sha256(I_PASASW_SHA)); // SHA256 암호화된 패스워드
				}else{
					param.put("I_PASASW_SHA",  I_PASASW_SHA); // 패스워드 변경 없이 정보수정만 하는 경우,
				}
				
			}
			if(param.containsKey("I_CHKCD")){
				param.put("I_CHKCD",  Utils.decryptRsa(privateKey, param.get("I_CHKCD")));
			}
		} catch (Exception e) {
			throw new RuntimeException("RSA Error");
		}
	  	param.put("I_LOGEML", param.get("I_MAILID")+"@"+param.get("I_MAILCD"));
	  	commonService.commonInsert("sysUserMngSave", param);
        param.remove("I_PASASW");
        param.remove("I_LOGLID");
        param.remove("I_CHKCD");
	
	  	return param;
	}
	
	private Object trim(String i_PASASW_SHA) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @Method Name	: setSysUserMngCheck
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원)  ID 중복 , 병원(회원) 비밀번호, 병원 체크
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/sysUserMngCheck.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object setSysUserMngCheck(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	// 세션키를 사용하여 암호화키를 생성한다.
	  	PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
	  	
	  	if (privateKey == null) {
	  		throw new RuntimeException("PrivateKey Error");
	  	}
	  	
		//암호화 파라미터 디코드
		try {
			if(param.containsKey("I_LOGLID")){
				param.put("I_LOGLID",  Utils.decryptRsa(privateKey, param.get("I_LOGLID")));
			}
			if(param.containsKey("I_PASASW")){
				//param.put("I_PASASW",  Utils.decryptRsa(privateKey, param.get("I_PASASW")));
				param.put("I_PASASW",  CommonController.sha256(Utils.decryptRsa(privateKey, param.get("I_PASASW")))); // 변경후 패스워드 (SHA256 암호화된 패스워드)
			}
			if(param.containsKey("I_CHKCD")){
				param.put("I_CHKCD",  Utils.decryptRsa(privateKey, param.get("I_CHKCD")));
			}
			if(param.containsKey("I_NOWPASASW")){
				//param.put("I_NOWPASASW",  Utils.decryptRsa(privateKey, param.get("I_NOWPASASW")));
				param.put("I_NOWPASASW",  CommonController.sha256(Utils.decryptRsa(privateKey, param.get("I_NOWPASASW")))); // 변경전 패스워드
			}
		} catch (Exception e) {
			throw new RuntimeException("RSA Error");
		}

	  	List<Map<String, Object>> resultList = commonService.commonList("sysUserMngCheck", param);
	  	param.put("resultList", resultList);
        param.remove("I_PASASW");
        param.remove("I_LOGLID");
        param.remove("I_CHKCD");
        param.remove("I_NOWPASASW");
	  	return param;
	}

}

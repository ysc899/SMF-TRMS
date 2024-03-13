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
package kr.co.softtrain.common.web.main;


import java.net.Inet4Address;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
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
 * kr.co.softtrain.common.web.main
 * LoginController.java
 * </pre>
 * @title :  로그인
 * @author : ejlee
 * @since : 2018. 10. 24.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 10. 24.		ejlee   			최초생성
 * 
 * </pre>
 */


@Controller
public class LoginController  {
	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
	
	public static final int KEY_SIZE = 1024;

    @Autowired  
    private MessageSource messageSource;

    
	/**
	 * @Method Name	: goLogin
	 * @see
	 * <pre>
	 * Method 설명 : 로그인 화면 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goLogin(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "index";
//		String str = "main/login";
		if("".equals(Utils.isNull(session.getAttribute("__rsaPrivateKey__"))))
		{
			rsaKey(request,session);
		}
	
		return str;
	}
	/**
	 * @Method Name	: goLogin
	 * @see
	 * <pre>
	 * Method 설명 : 로그인 화면 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/trmslogin.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goTrmsLogin(HttpServletRequest request, HttpSession session) throws Exception{
//		String str = "index";
		String str = "main/login";

		if(session.getAttribute("UID") != null){
			str = "main/main";
		}else{
			if("".equals(Utils.isNull(session.getAttribute("__rsaPrivateKey__"))))
			{
				rsaKey(request,session);
			}
		}
		
		return str;
	}
	
	/**
	 * @Method Name	: rsaKey
	 * @see
	 * <pre>
	 * Method 설명 : 로그인 RSA 암호화 키 설정		
	 * return_type : void
	 * </pre>
	 * @param request
	 * @param session
	 * @throws Exception 
	 */
	public void rsaKey(HttpServletRequest request,HttpSession session) throws Exception{

		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(KEY_SIZE);

		KeyPair keyPair = generator.genKeyPair();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
	 	session.setAttribute("__rsaPrivateKey__", privateKey);  
	 	
	 	RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

		request.setAttribute("publicKeyModulus", publicKeyModulus);
		request.setAttribute("publicKeyExponent", publicKeyExponent);

	 	session.setAttribute("publicKeyModulus", publicKeyModulus);  
	 	session.setAttribute("publicKeyExponent", publicKeyExponent);  
	}
	
	/**
	 * @Method Name	: goUserPwd
	 * @see
	 * <pre>
	 * Method 설명 : 비밀번호 변경
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/userPwd.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goUserPwd(HttpServletRequest request, HttpSession session){
		String str = "main/userPwd";		
		return str;
	}
	

	/**
	 * @Method Name	: getLoginUser
	 * @see
	 * <pre>
	 * Method 설명 : 사용자 로그인
			 - 사용자 아이디, 패스워드 확인
			 - 접속일자 수정 
			 - 세션 설정
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/loginUser.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
	public Object getLoginUser(HttpServletRequest request, HttpSession session) throws Exception {	
    	Map<String, Object> param = new HashMap<String, Object>();
    	Map<String, Object> rtnParam = new HashMap<String, Object>();
//    	KeyFactory
    	String ip = request.getHeader("X-FORWARDED-FOR");
    	/** 사용자 IP 설정**/
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
        if("0:0:0:0:0:0:0:1".equals(ip)){
        	  ip = Inet4Address.getLocalHost().getHostAddress();
        }
        /** 사용자 IP 설정**/

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		// 세션키를 사용하여 암호화키를 로드
		PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");

		if (privateKey == null) {
	    	rtnParam.put("O_MSGCOD","288");
	    	return rtnParam;
		}else{
			
			boolean decryCheck = true;
	
			String user_id = param.get("I_UID").toString();
			String user_pw = param.get("I_UPWD").toString();
			//String isDID = param.get("I_DID").toString();
			//암호화 파라미터 디코드
			String id = null;
			String pw = null;		// RSA 암호화 된 패스워드를 복호화한 패스워드
			String pw_sha = null;	// SHA256 암호화 된 패스워드
			try {
				id = Utils.decryptRsa(privateKey, user_id);
				pw = Utils.decryptRsa(privateKey, user_pw);	// RSA 암호화 된 패스워드를 복호화한 패스워드
				pw_sha = CommonController.sha256(pw);		// SHA256 암호화 된 패스워드
				
			} catch (Exception e) {
				decryCheck = false; // 암호화키 관련 오류 
	//			throw new RuntimeException("RSA Error");
			}
			if(decryCheck)
			{
				param.put("I_UID", id);	
				param.put("I_UPWD", pw);			// RSA 암호화 된 패스워드를 복호화한 패스워드
				param.put("I_UPWD_SHA", pw_sha);	// SHA256 암호화 된 패스워드
				param.put("I_IP", ip); 			//아이피
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		    	param.put("I_LOGEFG", "LI");   	//이벤트종류
		    	
		    	
		    	// 로그인 체크 
		    	Object loginChk = commonService.commonOne("LoginCheck", param);
		
		    	logger.debug("=="+param);
		    	
		    	int O_MSGCOD =  Utils.isNull(param.get("O_MSGCOD"),0);
		    	
		    	// 로그인 성공시 세션 적용
		    	if(O_MSGCOD != 0 && O_MSGCOD <= 201){
		    		session.setAttribute("UID",		Utils.isNullTrim(param.get("O_UID")));		//로그인아이디
		    		session.setAttribute("IP",		ip);		//로그인 IP
		    		session.setAttribute("COR",		Utils.isNullTrim(param.get("O_COR")));		//COR코드
		    		session.setAttribute("ECF",		Utils.isNullTrim(param.get("O_ECF")));		//병원/직원flag
		    		session.setAttribute("NAM",		Utils.isNullTrim(param.get("O_NAM")));		//사용자명,병원명
		    		session.setAttribute("AGR",		Utils.isNullTrim(param.get("O_AGR")));		//권한그룹
		    		session.setAttribute("CDT",		Utils.isNullTrim(param.get("O_CDT")));		//최근접속일
		    		session.setAttribute("CTM",		Utils.isNullTrim(param.get("O_CTM")));		//최근접속시간
		       		session.setAttribute("HOS",		Utils.isNullTrim(param.get("O_HOS")));		//병원코드
		       		session.setAttribute("IYN",		Utils.isNullTrim(param.get("O_IYN")));		//초기화 구분
					if(param.containsKey("O_SYN")){
						session.setAttribute("SYN",		Utils.isNull(param.get("O_SYN"),"N"));		//SMS구분
					}else{
						session.setAttribute("SYN",		"N");		//SMS구분
					}
					session.setAttribute("TICKER_YN",		Utils.isNullTrim(param.get("O_TICKER")));	//티커(미니)사용여부
					session.setAttribute("CHATBOT_YN",		Utils.isNullTrim(param.get("O_CHATBOT")));	//챗봇 사용여부
					session.setAttribute("MONITER_VIEW_PRINT",Utils.isNullTrim(param.get("O_MONITERVIEWPRINT")));	//화면출력 사용유무
					session.setAttribute("PAGUEN_HOS_YN",Utils.isNullTrim(param.get("O_PAGUENHOSYN")));	//파견사원 병원유무
					
					//session.setAttribute("I_DID", isDID);
		    	}
		    	rtnParam.put("msgStr", messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, null));
		    	rtnParam.put("strMessage", param.get("strMessage"));
		    	rtnParam.put("O_MSGCOD", param.get("O_MSGCOD"));
		    	rtnParam.put("O_IYN", param.get("O_IYN"));
			}else{
		    	rtnParam.put("O_MSGCOD","283");
			}
		}
		return rtnParam;
    }
	
	/**
	 * @Method Name	: goLogout
	 * @see
	 * <pre>
	 * Method 설명 :   로그아웃 
	 * 			   - 사용자 세션 삭제
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/logout.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goLogout(HttpServletRequest request, HttpSession session){
		String	str = "index";

		if(session != null){
			if(session.getAttribute("UID") != null){
				session.invalidate();
				session = request.getSession(true);
			}
    	}

		return str;
	}
	/**
	 * @Method Name	: ChangePWD
	 * @see
	 * <pre>
	 * Method 설명 : 비밀번호 변경
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/ChangePWD.do", method = {RequestMethod.GET, RequestMethod.POST})
	//	  , produces="application/json;charset=UTF-8")
	public Object ChangePWD(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		// 세션키를 사용하여 암호화키를 생성한다.
		PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");

		if (privateKey == null) {
			throw new RuntimeException("PrivateKey Error");
		}

		String securedI_SPWD = param.get("I_SPWD").toString();
		String securedI_PWD = param.get("I_PWD").toString();
		//암호화 파라미터 디코드
		String I_SPWD = null;
		String I_PWD = null;
		try {
			I_SPWD = Utils.decryptRsa(privateKey, securedI_SPWD);
			I_PWD = Utils.decryptRsa(privateKey, securedI_PWD);
		} catch (Exception e) {
			throw new RuntimeException("RSA Error");
		}
		
		param.put("I_SPWD", I_SPWD);	// 변경전 비밀번호
		param.put("I_PWD", I_PWD);		// 변경후 비밀번호
		param.put("I_SPWD_SHA", CommonController.sha256(I_SPWD)); 	// 변경전 비밀번호 SHA 암호화
		param.put("I_PWD_SHA", CommonController.sha256(I_PWD));		// 변경후 비밀번호 SHA 암호화
	  	commonService.commonUpdate("ChangePWD", param);
   		session.setAttribute("IYN",		"N");		//초기화 구분

    	Map<String, Object> rtnParam = new HashMap<String, Object>();
    	rtnParam.put("msgStr", messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, null));
    	rtnParam.put("O_MSGCOD", param.get("O_MSGCOD"));
    	rtnParam.put("strMessage", param.get("strMessage"));
	  	return rtnParam;
	}

	/**
	 * @Method Name	: goSmft_rms
	 * @author	: ejlee
	 * @version : 1.0
	 * @since	: 2018. 10. 31.
	 * @param request
	 * @param session
	 * @return 
	 * @ReturnType : String
	 */
	@RequestMapping(value = "/SMFTRMS.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goSmf_trms(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "main/main";
		
    	Map<String, Object> param = new HashMap<String, Object>();
    	Map<String, Object> rtnParam = new HashMap<String, Object>();

		if("".equals(Utils.isNull(session.getAttribute("__rsaPrivateKey__"))))
		{
			rsaKey(request,session);
		}
	
//    	KeyFactory
    	String ip = request.getHeader("X-FORWARDED-FOR");
    	 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
        if("0:0:0:0:0:0:0:1".equals(ip)){
        	  ip = Inet4Address.getLocalHost().getHostAddress();
        }

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경


		String I_HLK = param.get("I_HLK").toString();

		String isDID = "N";
		if(param.containsKey("I_DID")) {
			isDID = param.get("I_DID").toString();
		}
		session.setAttribute("I_DID", isDID);
		
		param.put("I_COR", "NML");	
		param.put("I_UID", "");	
		param.put("I_HLK", I_HLK); 	
		param.put("I_IP", ip); 			//아이피
    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
    	param.put("I_LOGEFG", "LI");   	//이벤트종류
		param.put("I_LOGMNU", "GOMAIN");	//메뉴코드
		param.put("I_LOGMNM", "홍보페이지이동");	//메뉴명
    	
    	Object loginChk = commonService.commonOne("FromHomepage", param);
    	
    	int O_MSGCOD =  Utils.isNull(param.get("O_MSGCOD"),0);
    	
    	if(O_MSGCOD != 0 && O_MSGCOD < 300){
    		session.setAttribute("UID",		Utils.isNullTrim(param.get("O_UID")));		//로그인아이디
    		session.setAttribute("IP",		ip);		//로그인 IP
    		session.setAttribute("COR",		Utils.isNullTrim(param.get("O_COR")));		//COR코드
    		session.setAttribute("ECF",		Utils.isNullTrim(param.get("O_ECF")));		//병원/직원flag
    		session.setAttribute("NAM",		Utils.isNullTrim(param.get("O_NAM")));		//사용자명,병원명
    		session.setAttribute("AGR",		Utils.isNullTrim(param.get("O_AGR")));		//권한그룹
    		session.setAttribute("CDT",		Utils.isNullTrim(param.get("O_CDT")));		//최근접속일
    		session.setAttribute("CTM",		Utils.isNullTrim(param.get("O_CTM")));		//최근접속시간
       		session.setAttribute("HOS",		Utils.isNullTrim(param.get("O_HOS")));		//병원코드
       		session.setAttribute("IYN",		Utils.isNullTrim(param.get("O_IYN")));		//초기화 구분
       		session.setAttribute("SYN",		Utils.isNull(param.get("O_SYN"),"N"));		//SMS구분
       		session.setAttribute("TICKER_YN",Utils.isNullTrim(param.get("O_TICKER")));	//티커(미니)사용여부
       		session.setAttribute("CHATBOT_YN",Utils.isNullTrim(param.get("O_CHATBOT")));	//티커(미니)사용여부
       		session.setAttribute("MONITER_VIEW_PRINT",Utils.isNullTrim(param.get("O_MONITERVIEWPRINT")));	//화면출력 사용유무
       		session.setAttribute("PAGUEN_HOS_YN",Utils.isNullTrim(param.get("O_PAGUENHOSYN")));	//파견사원 병원유무
    	}else{
    		str = "index";
    	}
		
		//fromHomepage
		return str;
	}


}
package kr.co.softtrain.mobile.service.impl;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.softtrain.mobile.service.MobileService;

@Service("MobileService")
public class MobileServiceImpl extends EgovAbstractServiceImpl implements MobileService{

	
	Logger logger = LogManager.getLogger();
	
	public static final int KEY_SIZE = 1024;
	
	
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


	@Override
	public String getIP(HttpServletRequest request) throws UnknownHostException {
		
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
		
		return ip;
	}
	
	
	
}

package kr.co.softtrain.mobile.service;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface MobileService {

	/** 세션 키 생성 */
	public void rsaKey(HttpServletRequest request, HttpSession session) throws Exception;
	
	public String getIP(HttpServletRequest request)  throws UnknownHostException;
	
}

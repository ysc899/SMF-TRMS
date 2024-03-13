<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page  pageEncoding="UTF-8"%>
<%
	String ss_uid 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("UID"), "");	//로그인아이디             
	String ss_ip 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("IP"), "");	//로그인 IP                   
	String ss_cor 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("COR"), "");	//COR코드                  
	String ss_ecf 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("ECF"), "");	//병원/직원flag          
	String ss_nam 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("NAM"), "");	//사용자명병원명     
	String ss_agr 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("AGR"), "");	//권한그룹                  
	String ss_cdt 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("CDT"), "");	//최근접속일              
	String ss_ctm 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("CTM"), "");	//최근접속시간           
	String ss_hos 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("HOS"), "");	//병원코드  
	String ss_iyn 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("IYN"), "");	//비밀번호초기화
	String ss_syn 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("SYN"), "");	//SMS사용권한
	String ss_ticker_yn = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("TICKER_YN"), "");	//티커(미니)사용여부
	String ss_chatbot_yn = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("CHATBOT_YN"), "");	//챗봇 사용여부
	String ss_moniter_view_print = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("MONITER_VIEW_PRINT"), "");	//화면출력 사용유무
	String ss_paguen_hos_yn = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("PAGUEN_HOS_YN"), "");	//파견사원 병원유무
	
	if("0".equals(ss_cdt)){
		ss_cdt = "";
	}else{
		if(ss_cdt.length()>7){
			ss_cdt = ss_cdt.substring(0, 4)+"-"+ss_cdt.substring(4, 6)+"-"+ss_cdt.substring(6, 8);
		}
	}
%>
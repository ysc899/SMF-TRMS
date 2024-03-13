<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>씨젠의료재단</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<script type="text/javascript" src="../plugins/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="../plugins/jquery/jquery-ui.min.js"></script>
<%
	String ss_uid 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("UID"), "");	//로그인아이디             
	String ss_iyn 		= org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)session.getAttribute("IYN"), "");	//병원코드  
%>
	<script>	
	//var strUrl = "https://www.seegenemedical.com/sub/login.act.jsp?act=logout";
	var strUrl = "https://pr.seegenemedical.com/logout.do";
	
	$(document).ready( function(){
		if("<%=ss_uid%>" == "" ){
			location.assign(strUrl);
		}else{
			if("<%=ss_iyn%>" == "Y" ){
				location.assign(strUrl);
			}else{
				location.assign(strUrl);
// 				location.assign("/main.do");
			}
		}
	});
	
	</script>
</head>
<body>
</body>
</html>


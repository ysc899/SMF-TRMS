<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%
	String errMsg = Objects.toString(request.getAttribute("ERR_MSG"), "").replaceAll("(\r\n|\r|\n|\n\r)", "<br />");
	String jsErrMsg = Objects.toString(request.getAttribute("ERR_MSG"), "").replaceAll("(\r\n|\r|\n|\n\r)", "\\\\n");
	boolean isHiddenFrameFlag = Objects.toString(request.getParameter("ishiddenframe"), "").toUpperCase().equals("Y");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta name="p:domain_verify">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Seegene</title>
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
    <meta name="format-detection" content="telephone=no">
	 
	<link rel="stylesheet" type="text/css" href="../css/resultImg/sg_ui.css">
</head>
<body>
    <div class="ui-wrap">
	    <div class="ui-container">
	        <div class="title error">
                <h1>COVID-19 검사<br><span>조회 결과</span></h1>
            	<span class="txt"><%=errMsg %></span>
            </div>
        </div>
    </div>
    <script type="text/javascript">
<%	if (isHiddenFrameFlag) {	%>
    	alert('<%=jsErrMsg %>');
<%	}	%>
    </script>
</body>
</html>
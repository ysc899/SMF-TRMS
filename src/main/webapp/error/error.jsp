<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage = "true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>씨젠의료재단</title>

<style type="text/css">
/* 개별 CSS적용해야 하는 경우는 여기에 */ 
.err_title{
	font-size: 30px;
	color: #afafaf;
	width: 100%;
	padding-left: 20px;
	padding-top: 10px;
}

.err_contents{
	font-size: 50px;
	color: #4f4f4f;
	width: 98%;
	vertical-align: middle;
	text-align: center;
	padding-top: 200px;
}
</style>   


</head>

<body>
	<form id="frm" method="post" >
		<div id="header">
			<div class="err_title">Error</div>
		</div>
		<div id="centerAreaWrap">
			<div id="centerArea">
				<div class="err_contents">
					Sorry, something went wrong.
<!-- 					죄송합니다. 관리자에게 문의 바랍니다. -->
				</div>
			</div>
		</div>
	</form>
</body>
</html>
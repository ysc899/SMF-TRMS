<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>session Out</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<!-- 
	<script type="text/javascript" src="../../js/jquery-1.12.4.min.js"></script>
	 -->
	<script type="text/javascript" src="/plugins/jquery/jquery-1.12.4.js"></script>
	<script>	
	
	var strUrl = "https://www.seegenemedical.com/sub/login.act.jsp?act=logout";
	
// 	var strUrl = "https://www.seegenemedical.com/";
// 	$(document).ready( function(){
// 		try{
// 			parent.parent.location.assign("/logout.do");	
// 		}catch (e1) {
// 			try{
// 				parent.parent.location.assign("/logout.do");	
// 			}catch (e2) {
<%-- 				<% --%>
// 					if(session != null){
// 						if(session.getAttribute("UID") != null){
// 							session.invalidate();
// 							session = request.getSession(true);
// 						}
// 			    	}
<%-- 				%> --%>
// 				location.assign(strUrl);
// 			}
// 		}
		
// 	});

	$(document).ready( function(){
		try{
			//parent.parent.location.assign("/logout.do");	
			location.replace('https://trms.seegenemedical.com/trmslogin.do');
		}catch (e1) {
			try{
				parent.location.assign("/logout.do");	
			}catch (e2) {
				location.assign("/logout.do");
			}
		}
		
	});

	</script>
</head>
<body>

</body>
</html>


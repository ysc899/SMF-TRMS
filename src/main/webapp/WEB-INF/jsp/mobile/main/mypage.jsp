<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1">
<title>::씨젠의료재단 모바일::</title>
	<%@ include file="/inc/mobile-common.inc"%>
</head>
<body>

	<div id="app_wrap">
		<%@ include file="../include/header.jsp"%>
		
		<!--container-->
		<div id="container" style="margin-top:25px;">
			<div class="myinfo_area">
				<h2>마이페이지</h2>
		
				<div class="myinfo_box">
					<table>
					<thead></thead>
					<tbody>
						<tr>
						<th>로그인 정보</th>
						<td class="myinfo_name"></td>
						</tr>
		
						<tr>
						<th>버전 정보</th>
						<td class="myinfo_version">0.0.1 사용중</td>
						</tr>
					</tbody>
					</table>
				</div>
		
				<span class="logout_bt" id="logout">로그아웃</span>
	
			</div>
		</div>
		<!--//container End-->
		
		<%@ include file="../include/navigation.jsp"%>
	</div>



<!-- 	<h1>메인 페이지</h1> -->
<!-- 	<a class="btn btn-default" href="/mobileLogin.do">로그인</a> -->
<!-- 	<button class="btn btn-info" id="logout">로그 아웃</button> -->
</body>

<script type="text/javascript">


	var s_nam = "<%= ss_nam %>";

	$(document).ready(function(){
		
		$("._topHeader").css('position', 'relative');
		
		
		$(".myinfo_name").html(s_nam+'님');
		
		//로그아웃
		$("#logout").click(function(){
			
			try{
				if (isIOS()) {
					var sendMsg = {
						actionType: "clearLoginInfo",
					};
					window.webkit.messageHandlers.fromWeb.postMessage(JSON.stringify(sendMsg));
				} else {
					window.Android.clearLoginInfo();
				}
// 				window.Android.clearLoginInfo();
			}catch(e){
				console.log(e);
			}
			
		 	location.assign("/mobileLogout.do");
		});
	});
</script>

</html>
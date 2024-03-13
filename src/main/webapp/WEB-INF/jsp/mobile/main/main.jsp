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
		<div id="container">
			<div class="bg_area">
				<div class="bg_area_text">
					<p>Seegene Medical Foundation</p>
					<h1>세계로 진출하는<br/>글로벌 질병검사전문 기관</h1>
				</div>
			</div>

			<div class="icon_area">
				<table>
					<tbody>
						<tr>
							<td class="_goNavi" data-url="/mobileTestItem.do">
								<img src="/images/mobile/ico_01.png" alt="아이콘01">
								<span>검사항목조회</span>
							</td>

							<td class="_goNavi" data-url="/mobileRstUserTableMini01.do">
								<img src="/images/mobile/ico_02.png" alt="아이콘02">
								<span>검사결과조회</span>
							</td>
						</tr>

						<tr>
							<td class="_goNavi" data-url="/mobileRecvRstCorona.do">
								<img src="/images/mobile/ico_03.png" alt="아이콘03">
								<span>결과보고</span>
							</td>

							<td class="_goNavi" data-url="/mobileNotice.do">
								<img src="/images/mobile/ico_04.png" alt="아이콘04">
								<span>공문/새소식</span>
							</td>
						</tr>
					</tbody>
				</table>

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


	$(document).ready(function(){
		
		var printMsg = '${pmsg}';
		
		if(printMsg != ''){
			alert('${pmsg}');
		}
		
		
		//로그아웃
		$("#logout").click(function(){
			
			try{
				window.Android.clearLoginInfo();
			}catch(e){
				console.log(e);
			}
			
		 	location.assign("/mobileLogout.do");
		});
		
		
		$(window).resize(function(){
			if($(window).height() > $(window).width()){
				$("#container").css('margin-bottom', 0);
			}else{
				$("#container").css('margin-bottom', ($(window).height() * 0.4));
			}
			
		});
		
	});
</script>

</html>
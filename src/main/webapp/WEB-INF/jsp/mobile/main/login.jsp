<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1">
<title>::씨젠의료재단 모바일::</title>
	<%@ include file="/inc/mobile-common.inc"%>
	
	<%
		// ===================== pr 도메인을 통해 접속할 경우만 해당 페이지에 접속 가능하도록 한다.
		/* try{
			String pre_url = (String)request.getHeader("REFERER");
			
			if(pre_url.indexOf("pr.seegenemedical.com")<=-1){
				response.sendRedirect("http://pr.seegenemedical.com/");
			}
		}catch(Exception ex){
			response.sendRedirect("http://pr.seegenemedical.com/");
		} */
		// ===================== pr 도메인을 통해 접속할 경우만 해당 페이지에 접속 가능하도록 한다.
		
		
		String publicKeyModulus = (String) request.getAttribute("publicKeyModulus");
		String publicKeyExponent = (String) request.getAttribute("publicKeyExponent");
		if("".equals(Utils.isNull(publicKeyModulus))){
			publicKeyModulus = (String) session.getAttribute("publicKeyModulus");
			publicKeyExponent = (String) session.getAttribute("publicKeyExponent");
		}
		String isLogout = "0";
		//암호화 키가 없으면 다시 서블릿을 호출한다.
		if(session == null || publicKeyExponent == null||"".equals(publicKeyExponent)){
			isLogout = "1";
		} 
	%>
	
</head>
<body>
	<form class="login" id="menuForm" name="menuForm" method="post"	>
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
	</form>
	
	
	
	<div id="app_wrap">
	
		<%@ include file="../include/header.jsp"%>
		
		
			<!--container-->
			<div id="container">
				<div class="login_area">
					<form>
						<input type="text" class="user_id engNum" id="I_UID" name="I_UID" maxlength="12" placeholder="아이디">
						<input type="password" class="user_pw" id="I_UPWD" name="I_UPWD"	maxlength="20" placeholder="비밀번호">
		
						<input type="checkbox" id="idsave" name="login_save" >
						<label for="idsave">자동로그인</label>
						
						<button type="button" name="로그인" value="로그인" class="login_bt" id="login_btn">로그인</button>
					</form>
				</div>
			</div>
			<!--//container End-->

		<%@ include file="../include/navigation.jsp"%>
	
	</div>
	
	<script type="text/javascript">
	
	var I_LOGMNU = "M_LOGIN";
	var I_LOGMNM = "모바일 로그인";
	
	
	function setLoginInfo(id, pw){
		
// 		console.log("웹 setLoginInfo 호출 됨 info - " + id + "	:	" + pw);
		
		$("#I_UID").val(id);
		$("#I_UPWD").val(pw);
		login();
// 		console.log('로그인 펑션 호출');
		
	}
	
	function login(){
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		var I_UID = jQuery("#I_UID").val();
		var I_UPWD = jQuery("#I_UPWD").val();
 		if(I_UID == "" || I_UPWD == "" ) { // ID, PW 빈값인지 체크
			CallMessage("254");	// 회원님의 로그인 정보를 정확히 입력하세요.
			return;
 		}else{
 				try {
 						var rsaPublicKeyModulus = "<%=publicKeyModulus %>";
 						var rsaPublicKeyExponent = "<%=publicKeyExponent %>";
 						
						submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent);

 				} catch(err) {
// 				 messagePopup("","",err);
				 parentMag("","",err);
 				}
 		}
	}
	
	function submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent){

		var I_UID = jQuery("#I_UID").val();
		var I_UPWD = jQuery("#I_UPWD").val();
		
			var rsa = new RSAKey();
			
			rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
			// 사용자ID와 비밀번호를 RSA로 암호화한다.
			var securedUserId = rsa.encrypt(I_UID);
			var securedUserPw = rsa.encrypt(I_UPWD);
			
			//해당 URL	만들기
//			var url = "/goLogin.do";
		//암호화
		var formData = $("#menuForm").serialize();
		
		formData += "&I_UID="+ securedUserId +"&I_UPWD="+ securedUserPw;
		//form 의 객체 값을 파라미터화 한다.
		ajaxCall("/loginUser.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		var regex = /<br\s*[\/]?>/gi;
		
// 		console.log('---------------------------------- result str ----------------------------------');
// 		console.log(strUrl);
// 		console.log(response);
// 		console.log('---------------------------------- result end ----------------------------------');
		
		
		if(strUrl == "/loginUser.do"){
		 	var msgCd = response.O_MSGCOD;
			if(msgCd != "200"){
				if(!isNull(response.strMessage)){
					console.log(response.strMessage.replace(regex, "\n"));
					
// 					 alert(response.strMessage.replace(regex, "\n"));
					 return;
				}else{
					alert(assignPage);
					return;
				}
			}else{
// 				if(response.O_IYN == "Y"){
// 					openPopup();						
// 				}else{
				try{
					if($("#idsave").is(":checked")){
						var I_UID = jQuery("#I_UID").val();
						var I_UPWD = jQuery("#I_UPWD").val();
// 						window.Android.setLoginInfo(I_UID, I_UPWD);
						if (isIOS()) {
							var sendMsg = {
								actionType: "setLoginInfo",
								uid: I_UID,
								upwd: I_UPWD,
							};
							window.webkit.messageHandlers.fromWeb.postMessage(JSON.stringify(sendMsg));
							
						} else {
							window.Android.setLoginInfo(I_UID, I_UPWD);
						}
					}
				}catch(e){
					console.log(e);
				}
	
				location.assign("/mobileMain.do");
// 				}
				
			}
		}
	}
	
	
		$("document").ready(function(){
			
			var printMsg = '${pmsg}';
			
			if(printMsg != ''){
				alert('${pmsg}');
			}
			
			$('#I_UID').bind("keyup", function(){
				$(this).val($(this).val().toUpperCase());
			});
			
			$("#login_btn").click(function(){
				login();
			});
			
			$("#I_UPWD").keydown(function(e){
					if(e.keyCode==13){	
						login();
					}
			});
			
			
			setTimeout(() => {
				var offset = $(".login_area").offset();
				$('html, body').animate({scrollTop : (offset.top -100) }, 400);	
			}, 100);
			
			
			
			//alert($("._topHeader").offset().top);
			
			
			$(window).resize(function(){
				var offset = $(".login_area").offset();
				$('html, body').animate({scrollTop : (offset.top -100) }, 400);	
			});
			
			
			
		});
	
	</script>
	
</body>
</html>
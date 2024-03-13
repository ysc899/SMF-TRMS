<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.security.PrivateKey"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>씨젠의료재단</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	
	<%
		// ===================== pr 도메인을 통해 접속할 경우만 해당 페이지에 접속 가능하도록 한다.
		/* try{
			String pre_url = (String)request.getHeader("REFERER");
			
			if(pre_url.indexOf("pr.seegenemedical.com")<=-1){
				//response.sendRedirect("http://pr.seegenemedical.com/");
			}
		}catch(Exception ex){
			//response.sendRedirect("http://pr.seegenemedical.com/");
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
	<script>	
	var I_LOGMNU = "LOGIN";
	var I_LOGMNM = "로그인";
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
					 messagePopup("","",err);
	 		    }
	 		}
		}
		function submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent){

			var I_UID = jQuery("#I_UID").val();
			var I_UPWD = jQuery("#I_UPWD").val();
			var I_DID = jQuery("input:checkbox[id='I_DID']").is(":checked") == true ? "Y" : "N";

			
		    var rsa = new RSAKey();
		    
		    rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
		    // 사용자ID와 비밀번호를 RSA로 암호화한다.
		    var securedUserId = rsa.encrypt(I_UID);
		    var securedUserPw = rsa.encrypt(I_UPWD);
		    
		    //해당 URL  만들기
// 			var url = "/goLogin.do";
			//암호화
			var formData = $("#menuForm").serialize();
			formData += "&I_UID="+ securedUserId +"&I_UPWD="+ securedUserPw;
			formData += "&I_DID="+ I_DID;
			//form 의 객체 값을 파라미터화 한다.
			ajaxCall("/loginUser.do", formData);
		}
		// ajax 호출 result function
		function onResult(strUrl,response){
			if(strUrl == "/loginUser.do"){
			 	var msgCd = response.O_MSGCOD;
				if(msgCd != "200"){
					if(!isNull(response.strMessage)){
						 messagePopup("","",response.strMessage);
						 return;
					}else{
						CallMessage(msgCd,"alert","",assignPage);
						return;
					}
				}else{
					if(response.O_IYN == "Y"){
						openPopup();						
					}else{
						location.assign("/main.do");
					}
					
				}
			}
		}
		
		function assignPage(){
			location.assign("/");
			
		}
		
		/* 팝업 호출 및 종료 */
		function openPopup(){
			callPagePop = "main";
			var gridValus,labelNm,strUrl;
			strUrl = "/userPwd.do";
			labelNm = "비밀번호 변경";

			/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
		}
		
		/*callback 호출 */
		function callFunPopup(url,iframe,gridValus){
		}
		/* 팝업 호출 및 종료 */
		
		function doEnterId(e) {
		    if(e.keyCode==13 && e.srcElement.type != 'textarea')
		    {	
		    	$("#I_UPWD").focus();
		    }
		}

		function doEnterPwd(e) {
		    if(e.keyCode==13 && e.srcElement.type != 'textarea')
		    {	
		    	login();
		    }
		}

		function parentMag(type,yesFn,strMessage){

			if(type=="confirm"){
		        $.message.confirm({
					title: 'Confirm',
		            contents : strMessage,
		            onConfirm: function () {
		        		setTimeout(function() {
		        			yesFn();
		        		}, 400);
		            },
		            onCancel: function () {
						rtnBool = false;
		            }
		        });
			}else{
				$.message.alert({
		            title : 'Alert',
		            contents : strMessage,
		            onConfirm: function () {
		        		if(typeof yesFn == "function"){
			        		setTimeout(function() {
			        			yesFn();
			        		}, 400);
		            	}
		            }
		        });
			} 
		}

		/**팝업 크기 설정**/
		function resizeTopIframe(dynheight,dynWidth) {  
			//width
			$('#pageiframe').height(200);
			$('#pageiframe').width(330);
			$('#popCont').height(250);
			$('#popCont').width(360);
		}
		function fnLoginClose(type){
			$('#pageModalView').modal('hide');
			if(type=="T"){
				location.assign("/main.do");
			}
		}		
	</script>
<style type="text/css">
/* 1000px */
html{height:100%;width:100%;background-color: #999999;}
.login_wrap .footer{margin-left: 0px;}
@media all and (max-width: 780px) {
/* container_wrap */
#container_wrap{margin:0px}

}
</style>

	
</head>
<body class="loginBg">
	<form class="login" id="menuForm" name="menuForm" method="post"  >
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
	</form>
	<!-- wrap -->
	<div id="wrap">
		<!-- wrap_inner -->
	    <div class="wrap_inner">
	        
	        <!-- container_wrap -->
	        <div id="container_wrap" class="login_container">
	                    
	            <!-- login -->
	            <div class="login_wrap">
	            	<div class="login_wrap_inner">
	                	<div class="login_container_wrap">
	                        <div class="login_container">
	                            <div class="login_box">  
		                            <div class="loginBox2">  
		                                <div class="login_box_inner">
											<form class="login" id="productForm" name="productForm" method="post"  >
		                                        <h2 class="form-title"><strong>안녕하세요</strong><br>씨젠의료재단 입니다.</h2>
		                                        <input  id="I_UID" name="I_UID" maxlength="12" type="text" placeholder="ID" class="engNum" />
		                                        <input id="I_UPWD" name="I_UPWD"  type="password" placeholder="Password"  onkeydown="return doEnterPwd(event)" maxlength="20"/>
		                                        <input type="button" value="로그인" class="btn btn-login btn-sm" onclick="login()" /> 
		                                        <input type="checkbox" id="I_DID" style="margin-top: -4px;" />
		                                        	<label style="display: inline-block; 
		                                        		position: relative; cursor: pointer; 
		                                        		-webkit-user-select: none; 
		                                        		margin-left : 10px;
    													margin-top: 12px;
    													font-size : 18px;
		                                        		-moz-user-select: none; 
		                                        		-ms-user-select: none; color: #fff;"> DID 사용</label>
											</form>
		                                </div>
		                                <div class="loginBox">
		                                    <img src="../images/common/login_logo.png"  class="login_logo" alt="재단법인 씨젠의료재단. Seegene Medical Foundation">              	
		                                </div>
		                            </div>
	                            </div>
	                        </div>
	                    </div>
	                </div> 
	                <!-- footer -->
	                <footer class="footer ">
	                    Copyright &copy; Seegene Medical Foundation. All right reserved.
	                </footer>
	                <!-- footer end -->
	            </div>
	        	<!-- login end -->
	                      
	        </div>
	        <!-- container_wrap end -->        
	           
	    </div>
	    <!-- wrap_inner end -->
	</div>
	<!-- wrap end -->
	<%-- 팝업 --%> 
	<div class="modal fade" id="pageModalView" tabindex="-1" role="dialog" aria-labelledby="pageModalLabel" data-backdrop="static" aria-hidden="true">
	    <div class="modal-dialog"  id="popCont">
	        <div class="modal-content">
	            <div class="modal-header">
<!-- 	                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnLoginClose()"> -->
<!-- 	                <span aria-hidden="true">&times;</span></button> -->
	                <h4 class="modal-title" id="pageModalLabel"></h4>
	            </div>
	            <div class="modal-body" id="popBody">
		        	<iframe id="pageiframe" src="" style="width:100%; overflow:hidden;"  frameborder="0" scrolling="no"></iframe>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<%-- 팝업 --%> 
</body>
</html>


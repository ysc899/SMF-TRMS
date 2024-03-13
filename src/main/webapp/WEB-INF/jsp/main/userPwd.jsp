<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.security.PrivateKey"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>Main Quick Setup</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script type="text/javascript" src="/plugins/rsa/jsbn.js"></script>
	<script type="text/javascript" src="/plugins/rsa/rsa.js"></script>
	<script type="text/javascript" src="/plugins/rsa/prng4.js"></script>
	<script type="text/javascript" src="/plugins/rsa/rng.js"></script>
	
	<%
		String publicKeyModulus = (String) request.getAttribute("publicKeyModulus");
		String publicKeyExponent = (String) request.getAttribute("publicKeyExponent");
		PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
		if("".equals(Utils.isNull(publicKeyModulus))){
			publicKeyModulus = (String) session.getAttribute("publicKeyModulus");
			publicKeyExponent = (String) session.getAttribute("publicKeyExponent");
		}
	
		String isLogout = "0";
		//암호화 키가 없으면 다시 서블릿을 호출한다.
		if(session == null || privateKey == null){
			isLogout = "1";
		} 
	%>
	<style type="text/css">
		input{width: 200px !important ;}
		.form-group label{    
			width: 100px !important ;
		}
		.form-group div{    
			display: inline-block;
		}
		.form-group{
			padding-bottom: 5px;
		}
	</style>
	
	<script type="text/javascript">
	var closeType="F";

	function save(){
		var chk = true;
        var pattern1 = /[0-9]/;
        var pattern2 = /[a-zA-Z]/;
        var pw = $("#I_CHPWD").val();
		closeType = "F";

		if(!isNull($("#I_CHPWD").val())){
	        if(!pattern1.test(pw) || !pattern2.test(pw) || pw.length < 6 || pw.length > 20){
       		 CallMessage("207","alert"); //비밀번호는 영문+숫자 8자리로 구성하여야 합니다.
//     		 CallMessage("208","alert",["8"]); //비밀번호는 영문+숫자 {0}자리 이상 구성하여야 합니다.
	        	chk = false;
	            return false;
	         }
		}
		
		if($("#I_CHPWD").val() != $("#I_CHPWD2").val()){
    		CallMessage("271","alert");
    		$("#I_CHPWD").focus();
    		return false;
		}
		
		if(chk){
		    try {
		        var rsaPublicKeyModulus = document.getElementById("rsaPublicKeyModulus").value;
		        var rsaPublicKeyExponent = document.getElementById("rsaPublicKeyExponent").value;
		        submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent);
		    } catch(err) {
				messagePopup("","",err);
		    }
		}
	}
	function submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent){

		var I_RPWD = jQuery("#I_RPWD").val();
		var I_CHPWD = jQuery("#I_CHPWD").val();
		
	    var rsa = new RSAKey();
	    
	    rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
	    // 사용자ID와 비밀번호를 RSA로 암호화한다.
	    var securedI_RPWD = rsa.encrypt(I_RPWD);
	    var securedI_CHPWD = rsa.encrypt(I_CHPWD);
	    $("#I_SPWD").val(securedI_RPWD);
	    $("#I_PWD").val(securedI_CHPWD);
	    
		var strUrl = "/ChangePWD.do";
		var formData = $("#searchForm").serialize();
		ajaxCall(strUrl, formData);
	}
	function onResult(strUrl,response){
	 	var msgCd = response.O_MSGCOD;
		if(msgCd == "272"){
			closeType = "T";
			CallMessage(msgCd,"alert","",PwdClose);	//200 
		}else{
			CallMessage(msgCd,"alert");	//247 = 현재 비밀번호를 다시 확인하세요.
		}
	}
	
	function PwdClose(){
		
		try{
			window.parent.fnLoginClose(closeType);
			<%
// 				System.out.println("session.invalidate");
	//				session.invalidate();
			%>
		}catch (e) {
		}
	}
	
	</script>
	
</head>
<body style="width: 330px;">
	<form id="searchForm" name="searchForm" method="post">
		<input type="hidden" id="rsaPublicKeyModulus" value="<%=publicKeyModulus%>" />
		<input type="hidden" id="rsaPublicKeyExponent" value="<%=publicKeyExponent%>" />
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="CHANGPWD"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="비밀번호변경"/>
		<input id="I_LOGLID"  name="I_LOGLID"  type="hidden" value="<%=ss_uid%>"/>
		<input id="I_SPWD"  name="I_SPWD"  type="hidden" />
		<input id="I_PWD"  	name="I_PWD"  type="hidden" />
		<input id="I_PWD2"  name="I_PWD2"  type="hidden" />
	</form>
	<div class="form-group">
		<label for="I_RPWD" >현재 비밀번호</label>
		<div>
			 <input type="password" class="form-control" id="I_RPWD" name="I_RPWD"  maxlength="20"  >
		</div>
	</div>
	<div class="form-group">
		<label for="I_CHPWD" >초기화 비밀번호</label> 
		<div>
			 <input type="password" class="form-control" id="I_CHPWD" name="I_CHPWD"   maxlength="20" >
		</div>
	</div>
	<div class="form-group">
		<label for="I_CHPWD2" >비밀번호 확인</label>
		<div>
			 <input type="password" class="form-control" id="I_CHPWD2" name="I_CHPWD2"  maxlength="20"  >
		</div>
	</div>
	<div class="modal-footer">
		<div class="min_btn_area">
			<button type="button" class="btn btn-primary" onclick="save()">저장</button>
<!-- 			<button type="button" class="btn btn-info" data-dismiss="modal" onclick="PwdClose()">닫기</button> -->
		 </div>
	</div>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.security.PrivateKey"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원(회원 정보)</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
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
	 
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<style type="text/css">
		body{
			min-width:300px;
		}
	</style>
<!-- <script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script> -->
	<script  type="text/javascript"   src="/plugins/daum/js/daum_addr_search.js " ></script>
	<script>

    var pscd = "";
	var authCd = "CUSTOMER" ;
	
	var logId ="";
	var pswd1 ="";
	var pswd2 ="";
	var chkCd ="";
	
	$(document).ready( function(){
		
		var resultCode = '<option value=""></option>';
		/* 권한그룹 */
	    resultCode += getCode(I_LOGMNU, I_LOGMNM, "AUTH");
		$('#I_AUTH').html(resultCode);
		if(authCd != null){
			$("#I_AUTH").val(authCd);       //사용자권한 
		}
  		jcf.replace($("#I_AUTH"));
  		
		var synCode = "";
	   	synCode += getCode(I_LOGMNU, I_LOGMNM, "YES_NO");
		$('#I_SYN').html(synCode);
		$("#I_SYN").val("N");       //SMS사용권한 
  		jcf.replace($("#I_SYN"));
		/* 권한그룹 */
		
		/* 메일 코드 */
	    resultCode = getRefCode(I_LOGMNU, I_LOGMNM, "EMAIL_ADDR");
		$('#I_CDMAIL').html(resultCode);
		$( "#I_CDMAIL" ).change(function() {
			$("#I_MAILCD").val( $( this ).val());
		});
  		jcf.replace($("#I_CDMAIL"));
		/* 메일 코드 */

		$("#I_LOGLID").keyup(function(event){
			$("#I_LOGINCHK").val("F");
		});
		$("#I_LOGHOS").keyup(function(event){
			$("#I_SAVEFLG").val("F");
		});
		//I_EML
		var formData = $("#productForm").serializeArray();
		
		
		$("#testPopup").click(function(){
			$(".modal-content").css("display" ,"block")
		});
	
	})
	
	/*팝업 호출 Input 내역 설정*/
	function gridViewRead(gridValus){
		
		$("#IDC").attr("hidden",true);
		$("#HOSC").attr("hidden",true);
		
		$(".noneDiv").removeClass("col-md-9");
		$(".noneDiv").removeClass("col-md-10");
		$(".noneDiv").addClass("col-md-12");
		
		$("#I_LOGLID").attr("readOnly",true);
		$("#I_LOGHOS").attr("readOnly",true);

		$("#I_SAVEFLG").val("U");
		$("#I_LOGINCHK").val("U");
		
		$("#I_LOGLID").val(gridValus["LOGLID"]);			//아이디		
		$("#I_LOGHOS").val(gridValus["LOGHOS"]);       //병원코드 
		$("#I_GIC").val(gridValus["F120GIC"]);       //요양기관코드 
		$("#I_FNM").val(gridValus["F120FNM"]);       //병원명 
		$("#I_SNO").val(gridValus["LOGSNO"]);       //사업자번호
		$("#I_WNO").val(gridValus["LOGWNO"]);       //대표자명 
		$("#I_TEL").val(gridValus["LOGTEL"]);       //전화번호 
		$("#I_LOGWNO").val(gridValus["LOGWNO"]);		//대표자명
		$("#I_LOGGNO").val(gridValus["LOGGNO"]);		//면허번호
		$("#I_I_EKD").val(gridValus["I_EKD"]);		//메일코드
		$("#I_DRS").val(gridValus["S005DRS"]);		//원장출신학교
		$("#I_HPH").val(gridValus["S005HPH"]);		//병원홈페이지
		$("#I_ITM").val(gridValus["S005ITM"]);		//전문진료과목
		$("#I_POS").val(gridValus["S005POS"]);		//우편번호
		$("#I_JAD1").val(gridValus["S005JAD1"]);		//지번주소1
		$("#I_DAD1").val(gridValus["S005DAD1"]);		//도로명주소1
		$("#I_DAD2").val(gridValus["S005DAD2"]);		//도로명주소2
		$("#I_MMO").val(gridValus["S005MMO"]);		//메모
		$("#I_MRV").val(gridValus["S005MRV"]);		//메일수신여부
 	    $("#mail_chk").prop('checked', gridValus["S005MRV"]=="Y"?true:false) ;
		
		setMail(gridValus["LOGEML"]);//메일 설정
		
		if(!isNull( gridValus["S005SYN"])){
			$("#I_SYN").val(gridValus["S005SYN"]);       //SMS사용권한 
	        jcf.replace($("#I_SYN"));
		}
		if(!isNull( gridValus["S005AGR"])){
			authCd = gridValus["S005AGR"];
			$("#I_AUTH").val(authCd);       //사용자권한 
	        jcf.replace($("#I_AUTH"));
		}
	}

	//정보 상세보기시 메일 설정
	function setMail(strMail){
		if(strMail.indexOf("@")>-1){
			var splitMail = strMail.split('@');
			$("#I_MAILID").val(splitMail[0]);
			$("#I_MAILCD").val(splitMail[1]);
		}
		
	}
	
	//아이디, 병원코드 체크
	function ID_HospCheck(chktype){
		
		 $("#I_CHKTYPE").val(chktype);
		 var chkNm = "";
		 if(chktype == "I"){
		 	chkNm = "아이디를";
			 $("#I_CHKCD").val($("#I_LOGLID").val());
		}else{
		 	chkNm = "병원코드를";
			 $("#I_CHKCD").val($("#I_LOGHOS").val());
		}
		if($("#I_CHKCD").val() == ""){
			CallMessage("245","alert",[chkNm]);
			return;
		}
		 var rsaPublicKeyModulus = "";
		 var rsaPublicKeyExponent = "";
	    try {
	        rsaPublicKeyModulus = "<%=publicKeyModulus %>";
	        rsaPublicKeyExponent = "<%=publicKeyExponent %>";
	    } catch(err) {
		 	messagePopup("","",err);
	    }
	    
	    var rsa = new RSAKey();
	    rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
	    
// 	    사용자ID와 비밀번호를 RSA로 암호화한다.
		var formData = $("#Mng01Form").serializeObject();
		if(!isNull(formData.I_LOGLID)){
			formData.I_LOGLID = rsa.encrypt(formData.I_LOGLID);
		}
		if(!isNull(formData.I_PASASW)){
			formData.I_PASASW = rsa.encrypt(formData.I_PASASW);
			formData.I_PASASW2 = rsa.encrypt(formData.I_PASASW2);
		}
		if(!isNull(formData.I_CHKCD)){
			formData.I_CHKCD = rsa.encrypt(formData.I_CHKCD);
		}
		ajaxCall("/sysUserMngCheck.do", formData);
	}
	//저장
	function save(){
		$("#I_MRV").val($("#mail_chk").prop("checked")?"Y":"N");
		//mail_chk
		if(!saveValidation()) { return; }
	    try {
	        var rsaPublicKeyModulus = "<%=publicKeyModulus %>";
	        var rsaPublicKeyExponent = "<%=publicKeyExponent %>";
	        submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent);
	    } catch(err) {
		 	messagePopup("","",err);
	    }
	}
		    
	function submitEncryptedForm(rsaPublicKeyModulus, rsaPublicKeyExponent){

	    var rsa = new RSAKey();
	    rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
	    
		// 사용자ID와 비밀번호를 RSA로 암호화한다.
		var formData = $("#Mng01Form").serializeObject();
		if(!isNull(formData.I_LOGLID)){
			formData.I_LOGLID = rsa.encrypt(formData.I_LOGLID);
			logId = formData.I_LOGLID;
		}
		if(!isNull(formData.I_PASASW)){
			formData.I_PASASW = rsa.encrypt(formData.I_PASASW);
			formData.I_PASASW2 = rsa.encrypt(formData.I_PASASW2);
			pswd1 = formData.I_PASASW;
			pswd2 = formData.I_PASASW2
		}
		if(!isNull(formData.I_CHKCD)){
			formData.I_CHKCD = rsa.encrypt(formData.I_CHKCD);
			chkCd = formData.I_CHKCD ;
		}
		
		var overlap = $("#OVERLAP").val(); // 신규/중복 여부 
		if(297 == overlap){
			CallMessage("297","confirm","",saveForm);	//저장하시겠습니까?	
		}else{
			ajaxCall("/sysUserMngSave.do", formData);
		}
	}
	
	function saveForm(){
		var formData = $("#Mng01Form").serializeObject();
			formData.I_LOGLID  = logId;
			formData.I_PASASW  = pswd1;
			formData.I_PASASW2 = pswd2;
			formData.I_CHKCD   = chkCd;
		ajaxCall("/sysUserMngSave.do", formData);
	}
		
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/sysUserMngCheck.do"){
		 	var msgCd = response.O_MSGCOD;
		 	var overLap = response.O_ERRCOD;
		 	$("#OVERLAP").val(overLap);
		 	
			//I_LOGURL
// 			$("#I_SAVEFLG").val("F");
			if(msgCd == "200"){
				if($("#I_CHKTYPE").val() == "H"){
					var resultList =  response.resultList;
					$("#I_GIC").val(resultList[0]["F120GIC"]);       //요양기관코드 
					$("#I_FNM").val(resultList[0]["F120FNM"]);       //병원명 
					$("#I_SNO").val(resultList[0]["F120SNO"]);       //사업자NO
					$("#I_WNO").val(resultList[0]["F120WNO"]);       //대표자명 
					$("#I_TEL").val(resultList[0]["F120TEL"]);       //전화번호 
					$("#I_SAVEFLG").val("I");
				}else{
					
					// 아이디 유효성 체크
	        		if(!isNull($("#I_LOGLID").val())){
	        	        if($("#I_LOGLID").val().length < 4){
	               		CallMessage("209","alert"); //아이디는 4~12자리로 구성하여야 합니다.
	 		           		result = false;
	        	            return false;
	        	        }else{
		        			CallMessage("251");
							$("#I_LOGINCHK").val("I");
		        		}
	        		}
				}
			}
		}
		else if(strUrl == "/sysUserMngSave.do"){
		 	var msgCd = response.O_MSGCOD;
			if(msgCd == "221"){
				CallMessage("221","alert","",popupClose);	//저장을 완료하였습니다.
			}else{
				CallMessage(msgCd,"alert");	//저장을 완료하였습니다.
			}
		}
	}
	function popupClose(){
		closeModal("fnCloseModal");
	}
	
	
	function saveValidation(){
		var result = true;
		var t,formObj;
        var pattern1 = /[0-9]/;
        var pattern2 = /[a-zA-Z]/;
        var pw = $("#I_PASASW").val();
		$("#Mng01Form").find("input, select, textarea").each(function(i) {
			formObj = jQuery(this);
			
            if(formObj.prop("required")) {
            	
           		if(formObj.attr("id")=="I_LOGLID"){
            		if($("#I_LOGINCHK").val() == "F"){
		           		t = jQuery("label[for='"+formObj.attr("id")+"']").text();
		           		result = false;
		           		CallMessage("249");//ID 중복 확인  해주세요.
		           		formObj.focus();
		           		return false;
            		}
           		}
           		
           		// 아이디 유효성 체크
        		if(!isNull($("#I_LOGLID").val())){
        	        if($("#I_LOGLID").val().length < 4){
               		 CallMessage("209","alert"); //아이디는 4~12자리로 구성하여야 합니다.
 		           		 result = false;
        	             return false;
        	         }
        		}
           	
           		if(formObj.attr("id")=="I_LOGHOS"){
            		if($("#I_SAVEFLG").val() == "F"){
		           		result = false;
		           		CallMessage("252");//병원코드 확인해주세요.
		           		formObj.focus();
		           		return false;
            		}
           		}
           		
           		if(formObj.attr("id") != "I_PASASW"||$("#I_SAVEFLG").val()=="I"){
	            	if(!jQuery.trim(formObj.val())) {
	            		t = jQuery("label[for='"+formObj.attr("id")+"']").text();
	            		result = false;
	            		CallMessage("201","alert",[t]);//{0} 필수 입력입니다.
	            		formObj.focus();
	            		return false;
	           		}
           		}
				
           		
           		// 2020.05.22
           		// 별도 권한(ADMIN,IT,SUPPORT)의 경우,
           		// 정보 수정시 패스워드 입력 여부를 체크하지 않도록 한다.
           		// (병원 사용가 개인정보 수정할때만 패스워드 체크하도록 한다.)
           		if("<%=ss_agr%>" != "ADMIN"
           			&& "<%=ss_agr%>" != "IT"
           			&& "<%=ss_agr%>" != "SUPPORT"
           		){
           			// 비밀번호 미입력시,
               		if(isNull($("#I_PASASW").val()) || isNull($("#I_PASASW2").val())){
               			CallMessage("245","alert",["비밀번호를"]);				//{0} 입력하세요.
        				result = false;
        		 		return  false;
            		}
           		}
           		
           		// 비밀번호 유효성 체크
        		if(!isNull($("#I_PASASW").val())){
        	        if(!pattern1.test(pw) || !pattern2.test(pw) || pw.length < 6 || pw.length > 20){
               		 CallMessage("207","alert"); //비밀번호는 영문+숫자 6~20자리로 구성하여야 합니다.
//             		 CallMessage("208","alert",["8"]); //비밀번호는 영문+숫자 {0}자리 이상 구성하여야 합니다.
 		           		 result = false;
        	             return false;
        	         }
        		}
           		
           		if($("#I_LOGINCHK").val() == "I" && $("#I_SAVEFLG").val()=="I")
           		{
            		if($("#I_PASASW").val() != $("#I_PASASW2").val()){
	            		CallMessage("271","alert"); //비밀번호가 일치하지 않습니다.
	            		formObj.focus();
		           		result = false;
	            		return false;
            		}
           		}
           		if(formObj.attr("id")=="I_TEL"){
           			isPhone();
            		if(!isPhone(formObj.val())) {
	            		CallMessage("250");//전화번호를 정확히 입력하여 주십시오.
	            		formObj.focus();
		           		result = false;
	            		return false;
            			
            		}
           		}
            }
        });
		if(result){
			
	   		if($("#I_PASASW").val() != "")
	   		{
	    		if($("#I_PASASW").val() != $("#I_PASASW2").val()){
	        		CallMessage("271","alert"); //비밀번호가 일치하지 않습니다.
	        		formObj.focus();
	           		result = false;
	        		return false;
	    		}
		        if(!pattern1.test(pw) || !pattern2.test(pw) || pw.length < 6 || pw.length > 20){
	        		 CallMessage("207","alert"); //비밀번호는 영문+숫자 8자리로 구성하여야 합니다.
//	         		 CallMessage("208","alert",["8"]); //비밀번호는 영문+숫자 {0}자리 이상 구성하여야 합니다.
           			result = false;
   	            	return false;
				}
	   		}

	   	    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			var I_MAIL = $("#I_MAILID").val()+"@"+$("#I_MAILCD").val();
			if(isNull(I_MAIL.match(regExp))){
				CallMessage("275");	// 275 이메일 형식이 올바르지 않습니다.
	       		result = false;
	    		return false;
	        }
			
		}
		
		return result;
		//return true;
	}

	function fn_get_post_cd(){
		//addr_search_wrap:검색창 div id, I_POS: 우편번호 입력 할 id
		//I_DAD1: 도로명 입력할 id, I_JAD1: 지번 주소 입력할 id
	   execDaumPostcode("addr_search_wrap", "I_POS", "I_DAD1", "I_JAD1");
	}
	</script>
	
	<style type="text/css">
		body{
/* 			width:400px !important ; */
		}
		.form-group{
			padding-top: 5px;
		}
		.#HOSC{
			padding-left: 5px;
		}
		.col-md-9,.col-md-3,.col-md-1,.col-md-2{
			padding-right: 5px;
		}
	</style>
</head>
<body>
	<form id="Mng01Form" name="Mng01Form">
		<input id="I_CHKTYPE" name="I_CHKTYPE" type="hidden"/>
		<input id="I_CHKCD" name="I_CHKCD" type="hidden"/>
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_MRV" 	name="I_MRV" type="hidden"/>
		<input id="I_LOGCOR" name="I_LOGCOR" type="hidden" value="NML"/>
		<input id="I_SAVEFLG" name="I_SAVEFLG" type="hidden" value="F"/>
		<input id="I_LOGINCHK" name="I_LOGINCHK" type="hidden" value="F"/>
		<input id="I_PO1" name="I_PO1" type="hidden" value=""/>
		<input id="I_PO2" name="I_PO2" type="hidden" value=""/>
		<input id="I_FL4" name="I_FL4" type="hidden" value=""/>
		<input id="OVERLAP" name="OVERLAP" type="hidden" value=""/>
	           		
		<div class = "row">
			<div class="col-md-10 noneDiv"  >
				<div class="form-group">
					<label for="I_LOGLID">아이디</label>	<span class="color_red">*</span>
					<input type="text" class="form-control engNum" maxlength="12" id="I_LOGLID" name="I_LOGLID" required >  
				</div>
			</div>
			<div class="col-md-1"  id="IDC">
				<div class="form-group">
					<label>&nbsp;</label>
					<button type="button" class="btn btn-default btn-sm" onclick="ID_HospCheck('I')">중복확인</button>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class = "row">
					<div class="col-md-9 noneDiv"  >
						<div class="form-group">
							<label for="I_LOGHOS">병원코드</label>	<span class="color_red">*</span>
							<input type="text" class="form-control numberOnly" id="I_LOGHOS" name="I_LOGHOS" maxlength="5" required  > 
						</div>
					</div>
					<div class="col-md-3"  id="HOSC">
						<div class="form-group">
							<label>&nbsp;</label>
							<button type="button" class="btn btn-default btn-sm" onclick="ID_HospCheck('H')">병원확인</button>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_FNM" >병원(사용자)명</label>
					<div>
						<input type="text" class="form-control" id="I_FNM" name="I_FNM" readonly>
					</div>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_PASASW">비밀번호</label>	<span class="color_red">*</span>
					<div>
						<input type="password" style="ime-mode:disabled"  class="form-control engNum" id="I_PASASW" name="I_PASASW" maxlength="20" required>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label>비밀번호 확인</label>	<span class="color_red">*</span>
					<div>
						<input type="password" style="ime-mode:disabled" class="form-control engNum" id="I_PASASW2" name="I_PASASW2" maxlength="20">
					</div>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_GIC">요양기관코드</label>
					<div>
						<input type="text" class="form-control" id="I_GIC" name="I_GIC" readonly>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_TEL">병원연락처</label>	<span class="color_red">*</span>
					<div>
						<input type="text" class="form-control telNm" id="I_TEL" name="I_TEL" maxlength="14" placeholder="031-1234-5678" required>
					</div>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label for="I_MAILID">대표이메일</label>	<span class="color_red">*</span>
			<div class="row">
				<div class="col-md-3">
					<input type="text" class="form-control" id="I_MAILID" name="I_MAILID" required>
				</div>
				<div class="col-md-1" style="width: 40px;">@</div>
				<div class="col-md-3" >
					<input type="text" class="form-control" id="I_MAILCD" name="I_MAILCD" required>
				</div>
				<div class="col-md-3 select2">
					<select id="I_CDMAIL" name="I_CDMAIL"> </select>
				</div>
				<div class="col-md-2 modal_mail_chk">
					<input id="mail_chk" name="mail_chk" type="checkbox" value="Y">
					<label for="mail_chk">메일수신</label>
				</div>
			</div>
		</div>

		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_AUTH">권한그룹</label>	<span class="color_red">*</span>
					<select  id="I_AUTH" name="I_AUTH" required>	</select>										
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_SYN">SMS사용권한</label>	<span class="color_red">*</span>
					<select  id="I_SYN" name="I_SYN" required>	</select>										
				</div>
				
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
					<div class="form-group">
					<label>대표자성명</label>
					<div>
						<input type="text" class="form-control" id="I_WNO" name="I_WNO" >
					</div>
					</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label>대표자면허번호</label>
					<div>
						<input type="text" class="form-control Num" id="I_LOGGNO" name="I_LOGGNO" >
					</div>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>사업자번호</label>
					<div>
						<input type="text" class="form-control Num" id="I_SNO" name="I_SNO" >
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label>전문진료과목</label>
					<div>
						<input type="text" class="form-control" id="I_ITM" name="I_ITM" >
					</div>
				</div>
			</div>
		</div>
		
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>병원홈페이지</label>
					<div>
						<input type="text" class="form-control" id="I_HPH" name="I_HPH" >
					</div>
				</div>
			</div>
			<div class="col-md-6" >
		
				<div class="form-group">
					<label>원장출신학교</label>
					<div>
						<input type="text" class="form-control" id="I_DRS" name="I_DRS" >
					</div>
				</div>
			</div>
		</div>
		
		<!-- 우편번호 -->
		<div id="div_post_cd" class="form-group">
			<label>우편번호</label>
			<div class="col-md-12 con_row">
				<div class="col-md-10 col-xs-8 con_row">
					<input type="text" class="form-control" id="I_POS" name="I_POS"  placeholder="우편번호" maxlength="6" readonly="readonly" >  
				</div>
				<div class="col-md-2 col-xs-4 ">                     
					<button type="button" class="btn btn-default btn-sm" onclick="fn_get_post_cd()" class="btn btn-success">검색</button>
				</div>   
			</div>   
		</div>
		<div class="input-group" style="font-size: 1px;">&nbsp;</div>
		<!-- 우편번호 찾기 버튼 -->
		<div id="addr_search_wrap" style="display:none;border:1px solid; position:relative;">
	     	<img src="//t1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="Close">
	 	</div>
		<!-- 도로명 주소 -->
		<div id="div_road_addr" class="form-group ">
			<label for="I_DAD1">도로명주소</label> 
			<input type="text" class="form-control  m-t-10" id="I_DAD1"  name="I_DAD1" placeholder="도로명주소" readonly="readonly">
		</div>
		<!-- 지번 주소 -->
		<div id="div_gibun_addr" class="form-group">
			<label for="I_JAD1">지번주소</label> 
			<input type="text" class="form-control  m-t-10" id="I_JAD1"  name="I_JAD1" placeholder="지번주소" readonly="readonly">
		</div>
		<!-- 나머지 주소 -->
		<div id="div_rest_addr" class="form-group">
			<label for="I_DAD2">나머지주소</label> 
			<input type="text" class="form-control  m-t-10" id="I_DAD2" name="I_DAD2" placeholder="나머지주소" maxlength="150">
		</div>
		<!-- 메모 -->
		<div class="form-group">
			<label>메모</label>
			<div>
					<input type="text" class="form-control" id="I_MMO" name="I_MMO" maxlength="4000">
			</div>
		</div>
		</form>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-primary" onclick="save()">저장</button>
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		 </div>
	</div>
</body>
</html>


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
	<%@ include file="/inc/did.inc"%>
	<%
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
			response.sendRedirect("/");
		} 
		
		String I_DID = (String) session.getAttribute("I_DID");
	%>
	 
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<style type="text/css">
		body{
			min-width:300px;
		}
	</style>
	<script  type="text/javascript"   src="/plugins/daum/js/daum_addr_search.js " ></script>
	<script>

    var pscd = "";
	var authCd = "CUSTOMER" ;

	$(document).ready( function(){
		$("#I_AUTH").val(authCd);       //기본권한 
		
		var resultCode = '<option value=""></option>';
		/* 메일 코드 */
	    resultCode = getRefCode(I_LOGMNU, I_LOGMNM, "EMAIL_ADDR");
		$('#I_CDMAIL').html(resultCode);
		$( "#I_CDMAIL" ).change(function() {
			$("#I_MAILCD").val( $( this ).val());
		});
  		jcf.replace($("#I_CDMAIL"));
		/* 메일 코드 */

		//I_EML
		var formData = $("#productForm").serializeArray();
		gridViewRead();

		/* DID 프로젝트 */
		checkDID();
	})
	
	async function checkDID(){
		I_DID = "<%=I_DID %>";
		if(I_DID == "Y"){
   	   		var userid = "<%=ss_uid %>";
   	   		console.log(userid);
   		 	try{
  	  		 	// 성공시
	   		 	let response = await NdsApi.getDidHolder(userid);
	   		 	//console.log(response);
	   		 	$("#didExitBtn").show();
  		 	}catch(x){
  	  		 	// 서버오류 실패시 이쪽으로...
				//console.log(x);
				if(x.responseJSON.success == false){
					console.log(x.responseJSON.message);
		   		 	$("#didExitBtn").hide();
				}
 			}
   	   	}
	}

	async function didExit(){
		CallMessage("228","confirm",["DID 계정"],didExitDo);	
	}

	async function didExitDo(){
		var userid = "<%=ss_uid %>";
   		console.log(userid);
	 	try{
  		 	// 성공시
  		 	let response = await NdsApi.deleteDidHolder(userid);
  		 	//console.log(response);
  		 	await NdsApi.getIndexedDB().deleteHolder(userid);
	 		CallMessage("223","alert","",closeModal('','','main'));	//삭제를 완료하였습니다.
	 	}catch(x){
  		 	// 서버오류 실패시 이쪽으로...
			//console.log(x);
			if(x.responseJSON.success == false){
				console.log(x.responseJSON.message);
			}
		}
	}
	
	/*팝업 호출 Input 내역 설정*/
	function gridViewRead(){
		$("#I_SERID").val("<%=ss_uid%>");
		var formData = $("#Mng01Form").serializeArray();
		ajaxCall("/sysUserMngDtl.do", formData);
	}

	//정보 상세보기시 메일 설정
	function setMail(strMail){
		if(strMail.indexOf("@")>-1){
			var splitMail = strMail.split('@');
			$("#I_MAILID").val(splitMail[0]);
			$("#I_MAILCD").val(splitMail[1]);
		}
	}
	
	function saveCheck(){
		if(!saveValidation()) { return; }

		if(!isNull($("#I_NOWPASASW").val())||!isNull($("#I_PASASW").val()) )
		{
			passwordCheck();
		}else{
			save();
		}
	}

	//아이디, 병원코드 체크
	function passwordCheck(){
		$("#I_CHKTYPE").val("P");
		//$("#I_CHKCD").val($("#I_NOWPASASW").val());
		$("#I_CHKCD").val($("#I_LOGLID").val());
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
	    
// 	    사용자ID와 비밀번호를 RSA로 암호화한다.
		var formData = $("#Mng01Form").serializeObject();
		if(!isNull(formData.I_LOGLID)){
			formData.I_LOGLID = rsa.encrypt(formData.I_LOGLID);
		}
		if(!isNull(formData.I_NOWPASASW)){
			formData.I_NOWPASASW = rsa.encrypt(formData.I_NOWPASASW);
			formData.I_PASASW = rsa.encrypt(formData.I_PASASW);
			formData.I_PASASW2 = rsa.encrypt(formData.I_PASASW2);
		}
		if(!isNull(formData.I_CHKCD)){
			formData.I_CHKCD = rsa.encrypt(formData.I_CHKCD);
		}

// 		var formData = $("#Mng01Form").serializeArray();
		ajaxCall("/sysUserMngCheck.do", formData);
		
	}
	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
		if(strUrl == "/sysUserMngDtl.do"){
			resultList =  response.resultList[0];
	 		$("#I_LOGCOR").val(resultList["LOGCOR"]);		//아이디		
	 		$("#I_LOGLID").val(resultList["LOGLID"]);		//아이디		
	 		$("#I_LOGHOS").val(resultList["LOGHOS"]);       //병원코드 
	 		$("#I_GIC").val(resultList["F120GIC"]);       	//요양기관코드 
	 		$("#I_FNM").val(resultList["F120FNM"]);       	//병원명 
	 		$("#I_SNO").val(resultList["LOGSNO"]);       	//사업자번호
	 		$("#I_WNO").val(resultList["LOGWNO"]);       	//대표자명 
	 		$("#I_TEL").val(resultList["LOGTEL"]);       	//전화번호 
	 		$("#I_LOGWNO").val(resultList["LOGWNO"]);		//대표자명
	 		$("#I_LOGGNO").val(resultList["LOGGNO"]);		//면허번호
	 		$("#I_I_EKD").val(resultList["I_EKD"]);			//메일코드
	 		$("#I_DRS").val(resultList["S005DRS"]);			//원장출신학교
	 		$("#I_HPH").val(resultList["S005HPH"]);			//병원홈페이지
	 		$("#I_ITM").val(resultList["S005ITM"]);			//전문진료과목
	 		$("#I_POS").val(resultList["S005POS"]);			//우편번호
	 		$("#I_JAD1").val(resultList["S005JAD1"]);		//지번주소1
	 		$("#I_DAD1").val(resultList["S005DAD1"]);		//도로명주소1
	 		$("#I_DAD2").val(resultList["S005DAD2"]);		//도로명주소2
	 		$("#I_MMO").val(resultList["S005MMO"]);			//메모
	 		$("#I_MRV").val(resultList["S005MRV"]);			//메모
	 	    $("#mail_chk").prop('checked', resultList["S005MRV"]=="Y"?true:false) ;

	 		
	 		setMail(resultList["LOGEML"]);//메일 설정
 			authCd = resultList["S005AGR"];
 			$("#I_AUTH").val(authCd);       //권한코드 
 			$("#I_SYN").val( resultList["S005SYN"]);       //권한코드 
		}
		if(strUrl == "/sysUserMngCheck.do"){
		 	var msgCd = response.O_MSGCOD;
			//I_LOGURL
			$("#I_SAVEFLG").val("F");
			if(msgCd == "200"){
				save();
			}
		}
		else if(strUrl == "/sysUserMngSave.do"){
		 	var msgCd = response.O_MSGCOD;
			if(msgCd == "221"){
				CallMessage("221","alert","",popupClose);	//저장을 완료하였습니다.
			}else{
				CallMessage(msgCd,"alert");	//저장을 완료하였습니다.
			}
// 			CallMessage("221","alert","",popupClose);	//저장을 완료하였습니다.
		}
	}

	function popupClose(){
		closeModal('','','main');
	}
	
	
	
	function save(){
		$("#I_SAVEFLG").val("U");
		$("#I_MRV").val($("#mail_chk").prop("checked")?"Y":"N");
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
		if(!isNull(formData.I_NOWPASASW)){
			formData.I_NOWPASASW = rsa.encrypt(formData.I_NOWPASASW);
			formData.I_PASASW = rsa.encrypt(formData.I_PASASW);
			formData.I_PASASW2 = rsa.encrypt(formData.I_PASASW2);
		}
		if(!isNull(formData.I_CHKCD)){
			formData.I_CHKCD = rsa.encrypt(formData.I_CHKCD);
		}
		
		ajaxCall("/sysUserMngSave.do", formData);
		
	}
	function saveValidation(){
		var result = true;
		var t,formObj;
        var pattern1 = /[0-9]/;
        var pattern2 = /[a-zA-Z]/;
        var pw = $("#I_PASASW").val();
		

		if(!isNull($("#I_NOWPASASW").val())||!isNull($("#I_PASASW").val()) )
		{
			if($("#I_NOWPASASW").val() == ""){
				CallMessage("245","alert",["현재 비밀번호를"]);				//{0} 입력하세요.
				result = false;
		 		return  false;
			}
			// 비밀번호 체크
			/* if($("#I_PASASW").val() == ""){
				CallMessage("245","alert",["변경 비밀번호를"]);				//{0} 입력하세요.
				result = false;
		 		return  false;
			} */
    		if(!isNull($("#I_PASASW").val())){
    	        if(!pattern1.test(pw) || !pattern2.test(pw) || pw.length < 6 || pw.length > 20){
        		 CallMessage("207","alert"); //비밀번호는 영문+숫자 8자리로 구성하여야 합니다.
//         		 CallMessage("208","alert",["8"]); //비밀번호는 영문+숫자 {0}자리 이상 구성하여야 합니다.
		           		 result = false;
    	             return false;
    	         }
    		}
	
			if($("#I_PASASW").val() != $("#I_PASASW2").val()){
				CallMessage("245","alert",["변경 비밀번호확인를"]);			//선택내역이 없습니다.
				result = false;
		 		return  false;
			}
		}
		
		$("#Mng01Form").find("input, select, textarea").each(function(i) {
			formObj = jQuery(this);
            if(formObj.prop("required")) {
            	if(!jQuery.trim(formObj.val())) {
            		t = jQuery("label[for='"+formObj.attr("id")+"']").text();
            		result = false;
            		CallMessage("201","alert",[t]);//{0} 필수 입력입니다.
            		formObj.focus();
            		return false;
           		}
           	}
           	if(formObj.attr("id")=="I_TEL"){
            	if(!isPhone(formObj.val())) {
	            	CallMessage("250");//전화번호를 정확히 입력하여 주십시오.
					result = false;
	            	formObj.focus();
	            	return false;
            			
            	}
           	}
        });

   	    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		var I_MAIL = $("#I_MAILID").val()+"@"+$("#I_MAILCD").val();
		if(isNull(I_MAIL.match(regExp))){
			CallMessage("275");	// 275 이메일 형식이 올바르지 않습니다.
       		result = false;
    		return false;
        }
		
		return result;
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
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_LOGCOR" name="I_LOGCOR" type="hidden" />
		<input id="I_CHKTYPE" name="I_CHKTYPE" type="hidden"/>
		<input id="I_CHKCD" name="I_CHKCD" type="hidden"/>
		<input id="I_AUTH" name="I_AUTH" type="hidden"/>
		<input id="I_SYN" name="I_SYN" type="hidden"/>
		<input id="I_MRV" 	name="I_MRV" type="hidden"/>
		<input id="I_SERID" name="I_SERID" type="hidden" value="<%=ss_uid%>"/>
		<input id="I_SAVEFLG" name="I_SAVEFLG" type="hidden" value="F"/>
		<div class="form-group">
			<label>아이디</label>
			<input type="text" class="form-control"  id="I_LOGLID" name="I_LOGLID"  value="<%=ss_uid%>" maxlength="12" readonly>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>병원코드</label>
					<div>
						<input type="text" class="form-control" id="I_LOGHOS" name="I_LOGHOS" maxlength="5"  readonly>
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
			<div class="col-md-12" >
				<div class="form-group">
					<label>현재 비밀번호</label>	<span class="color_red">*</span>
					<div>
						<input type="password" class="form-control" id="I_NOWPASASW" name="I_NOWPASASW" maxlength="20" required>
					</div>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>변경 비밀번호</label>
					<div>
						<input type="password" style="ime-mode:disabled"  class="form-control engNum" id="I_PASASW" name="I_PASASW" maxlength="20" >
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_PASASW2">변경 비밀번호 확인</label>
					<div>
						<input type="password" style="ime-mode:disabled" class="form-control engNum" id="I_PASASW2" name="I_PASASW2" maxlength="20">
					</div>
				</div>
			</div>
		</div>
		
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label  for="I_GIC" >요양기관코드</label>
					<div>
						<input type="text" class="form-control" id="I_GIC" name="I_GIC"	readonly>
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
					<input type="text" class="form-control"  id="I_MAILID" name="I_MAILID" required>
				</div>
				<div class="col-md-1" style="width: 40px;">
				@
				</div>
				<div class="col-md-3" >
					<input type="text" class="form-control"  id="I_MAILCD" name="I_MAILCD" required> 
				</div>
				<div class="col-md-3 select2">									
					<select  id="I_CDMAIL" name="I_CDMAIL">
					</select>										
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
					<div class="form-group">
						<label>병원홈페이지</label>
						<div>
							<input type="text" class="form-control" id="I_HPH" name="I_HPH" >
						</div>
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
		<div id="addr_search_wrap" style="display:none;border:1px solid; position:relative; ">
			<img src="//t1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="Close">
		</div>
		<!-- 도로명 주소 -->
		<div id="div_road_addr" class="form-group">
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
				<input type="text" class="form-control" id="I_MMO" name="I_MMO" maxlength="4000" >
			</div>
		</div>
	 </form>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-primary" onclick="saveCheck()">저장</button>
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="javascript:closeModal('','','main')">닫기</button>
		 </div>
	</div>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<%@ include file="/inc/did.inc"%>
	<title>DID 가입 동의</title>
	
	<script>
		
	$(document).ready( function(){
		getTerms();
	});

	async function getTerms(){
		console.log("[약관조회]")
		try{
  		 	// 성공시
  		 	let response = await NdsApi.getTerms();
  		 	//console.log(response);
  		 	var agreeHtml = "";
		 	for(var i=0; i<response.length; i++){
		 		agreeHtml += "<h3>" + response[i].terms_nm + "</h3>";
		 		agreeHtml += "<p>" + response[i].terms_conts.replace(/\r\n/ig, '<br>') + "</p>";
		 		agreeHtml += "<hr>";
		 	}
			$("#agreeView").html(agreeHtml); 
	 	}catch(x){
  		 	// 서버오류 실패시 이쪽으로...
			//console.log(x);
			if(x.responseJSON.success == false){
				console.log(x.responseJSON.message);
			}
		}
	}

	async function agreeDID(){
		var userid = "<%=ss_uid %>";
		console.log(userid);
		let response411, response423, response431 = null; 
		try{
			/* 4.3.1 [POST] 약관 동의 이력 등록 : 씨젠 약관관련 회의 후 수정필요 */
			/* var historyParam = {
			    "histSno": "기본기필요",
			    "termsTypeCd": "타입필요",
			    "termsNm": "개인정보약관",
			    "termsVer": "1.0.0",
			    "termsAgreeYn": "Y",
			    "stepGbcd": "",
			    "refNo": "",
			    "regDate": "",
			    "bizId": "",
			    "accAgentCd": "",
			    "apUserNo": userid,
			    "apTermsTypeCd": "",
			    "apTermsNm": "",
			    "apTermsVer": "1.0.0"
			};
			response431 = await NdsApi.setAgreeHistory(historyParam); */
			
  		 	/* 4.1.1 [POST] Holder DID 생성  */
  		 	response411 = await NdsApi.setDidHolder(userid);
  		 	await NdsApi.getIndexedDB().createHolderDid(userid, response411.data);
  		 	console.log(response411);
			/*	if(response411.success){
	  		 	// 4.2.3 [POST] VC 생성 – Hospital(병원 신원확인)  : 씨젠 API 추가 후 수정필요
	  		 	var paramObj = {};
	  	    	paramObj["holderName"] = userid;
	  	    	paramObj["issuerName"] = "seegene";
	  			paramObj["vcType"] = "Hospital";
	  			paramObj["credentialSubject"] = {};
	  	        paramObj["credentialSubject"]["hospitalID"] = userId;//"요양기관ID",
	  	        paramObj["credentialSubject"]["hospitalCode"] = hospitalCode;//"요양기관코드",
	  	        paramObj["credentialSubject"]["hospitalName"] = hospitalName;//"요양기관명",
	  	        paramObj["credentialSubject"]["hospitalChief"] = hospitalChief;//"요양기관장",
	  	        paramObj["credentialSubject"]["hospitalPhone"] = hospitalPhone;//"000-0000-0000",
	  		 	response2 = await NdsApi.getHospitalVC(userId, paramObj);
	  		 	await NdsApi.getIndexedDB().createHospitalVc(userid, paramObj);
  		 	}

  		 	if(response411.success && response423.success){ */
 		 	if(response411.success){
  		 		CallMessage("221","alert","",closeModal('','','main'));	//저장을 완료하였습니다.
	  		}else{
	  			CallMessage("244","alert","",closeModal('','','main'));	//저장을 완료하였습니다.
	  		}
	 	}catch(x){
  		 	// 서버오류 실패시 이쪽으로...
			console.log(x);
			if(x.responseJSON.success == false){
				console.log(x.responseJSON.message);
			}
		}
	}
	
	</script>
</head>
<body>
	<div style="width : 1500px; height:700px;" >
		<form>
			<!--<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
			<input id="I_MCD" name="I_MCD" type="hidden"/>
			<input id="I_MNM" name="I_MNM" type="hidden"/>
			<input id="I_PMCD" name="I_PMCD" type="hidden"/>
			<input id="I_AGR" name="I_AGR" type="hidden"  value="<%=ss_agr%>"/>	 권한 그룹 -->
	          <!-- <div class="con_wrap col-md-3 col-sm-12">
                  <div class="title_area">
                      <h3 class="tit_h3">메뉴</h3>
                  </div>
                  <div class="con_section overflow-scr">
                      <div class="tbl_type">
                      	<div id="realgrid1"  class="realgridH" style="height:600px"></div>	
                      </div>
                  </div>
              </div> -->
              <div class="con_wrap col-md-12 col-sm-12" >
              	  <div class="title_area">
                      <h3 class="tit_h3" id="viewTitle">약관</h3>
                  </div>
                  <div class="con_section overflow-scr">
                  	<div  id="agreeView" style="height:560px;overflow:auto"></div>
					<div class="min_btn_area" style="text-align: center;margin-top:20px">
						  <button type="button" class="btn btn-primary" onclick="agreeDID()">동의후가입</button>
						  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="javascript:closeModal('','','main')">닫기</button>
					 </div>
				  </div>
              </div>
                  	
	     </form>
	</div>
</body>
</html>
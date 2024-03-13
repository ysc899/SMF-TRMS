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
	<title>DID 소개</title>
	
	<script>
		
	$(document).ready( function(){
		//getTerms();
	});

	async function addDID(){
		fnOpenPopup("/didAgree.do","DID 가입 동의",null,null);
	}
	
	</script>
</head>
<body>
	<div style="width : 800px; height:600px;" >
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
              	  <!-- <div class="title_area">
                      <h3 class="tit_h3" id="viewTitle">DID 소개</h3>
                  </div> -->
                  <div class="con_section overflow-scr">
                  	<div  id="agreeView" style="height:auto;overflow:auto">
                  		<!-- [DID 소개부분] -->
                  		<img alt="" src="../images/intro.png">
                  	</div>
					<div class="min_btn_area" style="text-align: center;margin-top:20px">
						  <button type="button" class="btn btn-primary" onclick="addDID()">가입하기</button>
						  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="javascript:closeModal('','','main')">닫기</button>
					 </div>
				  </div>
              </div>
                  	
	     </form>
	</div>
</body>
</html>
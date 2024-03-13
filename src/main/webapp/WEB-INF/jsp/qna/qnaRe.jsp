<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	 
	<script type="text/javascript" src="/plugins/ckeditor/ckeditor.js"></script>
	
	
	<style type="text/css">
	 <!--
		td{border:1px solid; padding-left: 5px;	}
		-->
	</style>
	
	<script>
	var searchItem = "";
	
	$(document).ready( function(){
		var resultCode = '<option value=""></option>';
		
		CKEDITOR.replace('I_CON1',{
			height :'350px',
			filebrowserUploadUrl : '/ckeditorImageUploadQna.do'
		});
		
	})
	
	function gridViewRead(gridValue){
		$("#I_SEQ").val(gridValue);
		$("#I_PDNO").val(gridValue);
		
		$("#S002CNM").attr("disabled",true);
		$("#O_CNM").attr("readonly",true);
		$("#I_CMA1").attr("readonly",true);
		$("#I_CMA2").attr("readonly",true);
		$("#I_IYNC").attr(
		{
			disabled : "disabled"
		});
		
		dataResult();
	}
	
	function popupClose(){
		closeModal("fnCloseModal");
	}
	
	function onResult(strUrl,response){
		if(strUrl == "/qnaNextVal.do"){
			var resultList = response.resultList;
			$('#I_SEQ').val(resultList[0]["S015SEQ"]);
			
			$('#I_DCL').removeAttr('disabled');
			
			var formData = $("#qna01Form").serialize();
			
			callLoading(null,"on");
			ajaxCall("/qnaReReg.do", formData,false);
		}
		
		if(strUrl == "/qnaReReg.do"){
			callLoading(null,"off");
			CallMessage("221","alert","",popupClose);	//저장을 완료하였습니다.
		}
		
		if(strUrl == "/qnaDtl.do"){
			var resultList = response.resultList;
			$('#I_DCL').val(resultList[0]["S015DCL"]);
			$('#S002CNM').val(resultList[0]["S002CNM"]);
			$('#O_CNM').val(resultList[0]["S015CNM"]);
			$('#I_CMA').val(resultList[0]["S015CMA"]);
			$('#I_TIT').val("Re : " + resultList[0]["S015TIT"]);
			
			var iyn = resultList[0]["S015IYN"];
			if(iyn == "Y"){
				$('#I_IYNC').attr({
					checked : "checked"
					,disabled : "disabled"
				});
			}else{
				$('#I_IYN').removeAttr('checked');
			}
			
			CKEDITOR.instances.I_CON1.setData();
			
			var text = resultList[0]["S015CON"];
			
			text += "<p>답변 : "
			
			setTimeout(function(){
				CKEDITOR.instances.I_CON1.document.getBody().setHtml(text);
			},250);
			
			CKEDITOR.instances.I_CON1.on('instanceReady', function(event) {
				  CKEDITOR.instances.I_CON1.focus();
				});
			
			setMail(resultList[0]["S015CMA"]);
		}
	}
	
	function dataResult()
	{
		var formData = $("#qna01Form").serialize();
		ajaxCall("/qnaDtl.do",formData);
	}
	
	function qnaReReg(){
				
		if($('#I_TIT').val() == "" || $('#I_TIT').val() == null){
			CallMessage("201","alert",["제목은"]);	//필수 입력입니다.
			return;
		}
		
		if(CKEDITOR.instances.I_CON1.getData() == ""){
			CallMessage("201","alert",["내용은"]);	//필수 입력입니다.
			return;
		}
		
		CallMessage("203","confirm","",fnSave);	//저장하시겠습니까?
	}
	
	function fnSave(){
		
		if ($('#I_IYNC').is(":checked"))
		{
			document.getElementById('I_IYN').value = "Y";
		}else{
			document.getElementById('I_IYN').value = "N";
		}
		
		document.getElementById('I_CON').value = CKEDITOR.instances.I_CON1.getData();
		
		ajaxCall("/qnaNextVal.do", "",false);
		
	}
	
	//정보 상세보기시 메일 설정
	function setMail(strMail){
		if(strMail.indexOf("@")>-1){
			var splitMail = strMail.split('@');
			$("#I_CMA1").val(splitMail[0]);
			$("#I_CMA2").val(splitMail[1]);
		}
	}

	</script>
	
</head>
<body  style="width:1000px; ">
	<div class="tbl_type">
		<form id="qna01Form" name="qna01Form" enctype="multipart/form-data" method="post">
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_SEQ" name="I_SEQ"   type="hidden">
			<input id="I_PDNO" name="I_PDNO" type="hidden">
			<input id="I_RCT" name="I_RCT" type="hidden" value="0">
			<!-- <input id="S002CNM" name="S002CNM" type="hidden"> -->
			<input id="I_DCL" name="I_DCL" type="hidden"/>
			
			<table class="table table-bordered table-condensed tbl_l-type" >
				<colgroup>
	                <col width="10%">
	           		<col width="20%" >
	           		<col width="10%" >
	           		<col width="20%" >
	           		<col width="10%" >
	            	<col width="20%" >
		        </colgroup>
		        <tbody>
					<tr>
						<th scope="row">문의종류</th>
						<td>
							<div class="select_area">
								<input id="S002CNM" name="S002CNM" class="form-control"/>
							</div>
						</td>
						<th scope="row">작성자</th>
						<td>
							<input id="O_CNM" name="O_CNM" class="form-control" style="width:200px" type="text" maxlength="8" value="<%=session.getAttribute("NAM") %>">
						</td>
						<th scope="row">답변자</th>
						<td>
							<input id="I_CNM" name="I_CNM" class="form-control" style="width:200px" type="text" maxlength="8" value="<%=session.getAttribute("NAM") %>">
						</td>	
					</tr>
					<tr>
						<th scope="row">이메일</th>
						<td colspan="5">
							<div class="row">
								<div class="col-md-2">
									<input id="I_CMA1" name="I_CMA1" style="width:120px;" class="form-control engNum"/>
								 </div>
								 <div class="col-md-1" style="width: 50px;">@</div>
								 <div class="col-md-2">
									<input id="I_CMA2" name="I_CMA2" style="width:130px" class="form-control engNum"/>
								 </div>
								 <!-- <div class="col-md-3 select2">
									<select id="I_CMA3" name="I_CMA3" class="" onChange="document.getElementById('I_CMA2').value = this.value;"></select>
								 </div> -->
								<div class="col-md-4 ">          
									<input type="checkbox" id="I_IYNC" name="checkbox">개인정보(이메일) 활용 동의
									<input type="hidden" id="I_IYN" name="I_IYN">
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row">제목<span class="color_red">*</span></th>
						<td colspan="5">
							<input style="text" class="form-control" id="I_TIT" name="I_TIT" maxlength="40"/>
						</td>
					</tr>
					<tr>
						<th scope="col" colspan="6" style="text-align : center;">내용<span class="color_red">*</span></th>
					</tr>
					<tr>
						<th scope="col" colspan="6">
		             		<div style="width: 100%; height: 490px;">
								<textarea name="I_CON1" id="I_CON1" ></textarea>
								<input type="hidden" id="I_CON" name="I_CON" >
							</div>
			            </th>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="modal-footer">
		<div class="min_btn_area" align="center">
			<button type="button" class="btn btn-primary" onclick="javascript:qnaReReg();">저장</button>
			<button type="button" class="btn btn-info" 	  data-dismiss="modal" onclick="closeModal();">취소</button>
		</div>
	</div>
</body>
</html>
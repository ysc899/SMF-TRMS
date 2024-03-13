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
	var I_SEQ = 0;
	var searchItem = ""; 
	var maxSize = 2097152;
   	var maxSizeDtl = 2;
   	var saveTypeE = "";
	
	$(document).ready( function(){
		
		var resultCode = '<option value=""></option>';
		
		searchItem = getCode(I_LOGMNU, I_LOGMNM, "SEARCH_ITEM","N");
        $('#I_DCL').html(searchItem);
        jcf.replace($("#I_DCL"));
        
        /* 메일 코드 */
	    resultCode = getRefCode(I_LOGMNU, I_LOGMNM, "EMAIL_ADDR");
		$('#I_CMA3').html(resultCode);
		$( "#I_CMA3" ).change(function() {
			$("#I_CMA2").val( $( this ).val());
		});
  		jcf.replace($("#I_CMA3"));
		/* 메일 코드 */
		
  		CKEDITOR.replace('I_CON1',{
			height :'263px',
			filebrowserUploadUrl : '/ckeditorImageUploadQna.do'
		});
	})
	
	function gridViewRead(index){
		gridView = index;
		$("#I_SEQ").val(gridView); //순번 값 셋팅.
		I_SEQ = $("#I_SEQ").val(); 
		dataResult(I_SEQ);
	}
	
	function dataResult(strYn)
	{
		//부모창(qna.jsp)에서 받아온 strYn값을 구분자로 사용.
		if(strYn != ""){ // strYn이 "" 이면 새로 등록.
			$('#saveBtn').html("수정");
			var formData = $("#qna01Form").serialize();
			ajaxCall("/qnaDtl.do",formData);
		}
	}
	
	function qnaAttachList()
	{
		var formData = new FormData($("#qna01Form")[0]);
		ajaxCall2("/qnaAttachList.do",formData,false);
	}
	
	
	
	//정보 상세보기시 메일 설정
	function setMail(strMail){
		if(strMail.indexOf("@")>-1){
			var splitMail = strMail.split('@');
			$("#I_CMA1").val(splitMail[0]);
			$("#I_CMA2").val(splitMail[1]);
		}
	}
		
	var gridView;
	var dataProvider;
	
	function qnaSave()
	{
		
		// 첨부파일01
       	if($('#attachments1').val() != ""){
       		
       		// 1) 업로드파일 확장자 확인 / 2022.11-23 취약점 조치
        	var fileName_attachments1 = $("#attachments1").val();
           	fileName_attachments1 = fileName_attachments1.split('.').pop().toLowerCase();
           	
           	if(fileName_attachments1 != "jpg" && fileName_attachments1 != "png" &&  fileName_attachments1 != "hwp" &&  fileName_attachments1 != "pdf" 
           		&&	fileName_attachments1 != "xls" && fileName_attachments1 != "xlsx" 
           		&&  fileName_attachments1 != "doc" &&  fileName_attachments1 != "docs"
           		&&  fileName_attachments1 != "ppt" &&  fileName_attachments1 != "pptx"
           	){
           		CallMessage("302","alert","");	//필수 입력입니다.
           		return;
           	}
           	
         	// 2) 업로드파일 용량 확인
       		var fileSize1 = document.getElementById("attachments1").files[0].size;
       		if(fileSize1 > maxSize){
       			CallMessage("231","alert",[maxSizeDtl]); //첨부파일 사이즈는 {0} 이내로 가능합니다.
       			return;
       		}
       	}   
       	
       	// 첨부파일02
       	if($('#attachments2').val() != ""){
       		
       		// 1) 업로드파일 확장자 확인 / 2022.11-23 취약점 조치
        	var fileName_attachments2 = $("#attachments2").val();
           	fileName_attachments2 = fileName_attachments2.split('.').pop().toLowerCase();
           	
           	if(fileName_attachments2 != "jpg" && fileName_attachments2 != "png" &&  fileName_attachments2 != "hwp" &&  fileName_attachments2 != "pdf" 
           		&&	fileName_attachments2 != "xls" && fileName_attachments2 != "xlsx" 
           		&&  fileName_attachments2 != "doc" &&  fileName_attachments2 != "docs"
           		&&  fileName_attachments2 != "ppt" &&  fileName_attachments2 != "pptx"
           	){
           		CallMessage("302","alert","");	//필수 입력입니다.
           		return;
           	}
       		
           	// 2) 업로드파일 용량 확인
       		var fileSize2 = document.getElementById("attachments2").files[0].size;
       		if(fileSize2 > maxSize){
       			CallMessage("231","alert",[maxSizeDtl]); //첨부파일 사이즈는 {0} 이내로 가능합니다.
       			return;
       		}
       	}
       	
		if($('#I_DCL').val() == "" || $('#I_DCL').val() == null){
			CallMessage("201","alert",[T_DCL]);	//필수 입력입니다.
			return;
		}
		if($('#I_CNM').val() == "" || $('#I_CNM').val() == null){
			CallMessage("201","alert",["작성자는"]);	//필수 입력입니다.
			return;
		}
		
		if($('#I_TIT').val() == "" || $('#I_TIT').val() == null){
			CallMessage("201","alert",["제목은"]);	//필수 입력입니다.
			return;
		}
		
		if(CKEDITOR.instances.I_CON1.getData() == ""){
			CallMessage("201","alert",["내용은"]);	//필수 입력입니다.
			return;
		}
		
		if($('#I_SEQ').val() == "" || $('#I_SEQ').val() == null )
		{
			CallMessage("203","confirm","",qnaReg);	//저장하시겠습니까?
		}else{
			CallMessage("225","confirm","",qnaUdt);	//수정하시겠습니까?
		}
	}
	
	function qnaReg(){
		
		saveTypeE = "C"
		
		if ($('#I_IYNC').is(":checked"))
		{
			document.getElementById('I_IYN').value = "Y";
		}else{
			document.getElementById('I_IYN').value = "N";
		}
		document.getElementById('I_CON').value = CKEDITOR.instances.I_CON1.getData();
		
		var formData = $("#qna01Form").serialize();
		
		callLoading(null,"on");
		ajaxCall("/qnaNextVal.do","",false);
	}
	
	function popupClose(){
		closeModal("fnCloseModal");
	}
	
	function onResult(strUrl,response){
		if(strUrl == "/qnaNextVal.do"){
			var resultList = response.resultList;
			$('#I_SEQ').val(resultList[0]["S015SEQ"]);
			
			if($('#attachments1').val() != "" || $('#attachments2').val() != ""){
				qnaAttach();
			}else{
				var formData = $("#qna01Form").serialize();
				ajaxCall("/qnaSave.do",formData,false);
			}
		}
		
		if(strUrl == "/qnaSave.do"){
			callLoading(null,"off");
			CallMessage("221","alert","",popupClose);	//저장을 완료하였습니다.
		}
		
		if(strUrl == "/qnaUdt.do"){
			callLoading(null,"off");
			CallMessage("222","alert","",popupClose);	//수정을 완료하였습니다.
		}
		
		if(strUrl == "/qnaAttachSave.do"){
			if(saveTypeE == "U"){
				var formData = $("#qna01Form").serialize();
				ajaxCall("/qnaUdt.do",formData,false);
			}else{
				var formData = $("#qna01Form").serialize();
				ajaxCall("/qnaSave.do",formData,false);
			}
		}
		
		if(strUrl == "/qnaAttachList.do"){
			var resultList = response.resultList;
			if(resultList != ""){
				if(resultList[0]["S015FSQ"] > 1){
						$('#I_FPT2').html(resultList[0]["S015FNM"]);
						$('#I_FPT2').val(resultList[0]["S015FPT"]);
						$('#I_FNM2').val(resultList[0]["S015FNM"]);
						$('#I_FSNM2').val(resultList[0]["S015FNM"]);
						$('#old_attach2').html(resultList[0]["S015FNM"]);
				}else{
					if(resultList[0]){
						$('#I_FPT1').html(resultList[0]["S015FNM"]);
						$('#I_FPT1').val(resultList[0]["S015FPT"]);
						$('#I_FNM1').val(resultList[0]["S015FNM"]);
						$('#I_FSNM1').val(resultList[0]["S015FNM"]);
						$('#old_attach1').html(resultList[0]["S015FNM"]);
					}
					
					if(resultList[1]){
						$('#I_FPT2').html(resultList[1]["S015FNM"]);
						$('#I_FPT2').val(resultList[1]["S015FPT"]);
						$('#I_FNM2').val(resultList[1]["S015FNM"]);
						$('#I_FSNM2').val(resultList[1]["S015FNM"]);
						$('#old_attach2').html(resultList[1]["S015FNM"]);
					}
				}
			}
		}
		
		if(strUrl == "/qnaDtl.do"){
			
			var resultList = response.resultList;
			$('#I_PDNO').val(resultList[0]["S015PDNO"]);
			$('#I_DCL').val(resultList[0]["S015DCL"]);
			jcf.replace($("#I_DCL"));
			$('#S002CNM').val(resultList[0]["S002CNM"]);
			$('#I_CNM').val(resultList[0]["S015CNM"]);
			$('#I_CMA').val(resultList[0]["S015CMA"]);
			$('#I_TIT').val(resultList[0]["S015TIT"]);
			
			var iyn = resultList[0]["S015IYN"];
			if(iyn == "Y"){
				$('#I_IYNC').attr({
					checked : "checked"
				});
			}else{
				$('#I_IYNC').removeAttr('checked');
			}
			
			
			CKEDITOR.instances.I_CON1.setData();
			
			var text = resultList[0]["S015CON"];
			
			setTimeout(function(){
				CKEDITOR.instances.I_CON1.document.getBody().setHtml(text);
			},250);
			
			CKEDITOR.instances.I_CON1.on('instanceReady', function(event) {
				  CKEDITOR.instances.I_CON1.focus();
				});
			
			setMail(resultList[0]["S015CMA"]);
			
			qnaAttachList();
		}
		
		if(strUrl == "/qnaAttachments.do"){
			
			$('#I_SEQ').val(response.I_SEQ);
			
			$('#I_FNM1').val(response.S015FNM1);
			$('#I_FPT1').val(response.S015FPT1);
			$('#I_FSNM1').val(response.S015FSNM1);
			
			$('#I_FNM2').val(response.S015FNM2);
			$('#I_FPT2').val(response.S015FPT2);
			$('#I_FSNM2').val(response.S015FSNM2);
			
			qnaAttachSave();
		}
	}
	
	
	
	function qnaUdt(){
		saveTypeE = "U";
		
		if($('#I_IYNC').is(":checked"))
		{
			document.getElementById('I_IYN').value = "Y";
		}else{
			document.getElementById('I_IYN').value = "N";
		}
		
		document.getElementById('I_CON').value = CKEDITOR.instances.I_CON1.getData();
		
		callLoading(null,"on");
		
		if($('#attachments1').val() != "" || $('#attachments2').val() != ""){
			qnaAttach();
		}else{
			var formData = $("#qna01Form").serialize();
			ajaxCall("/qnaUdt.do",formData,false);
		}
	}
	
	function qnaAttach()
	{
		var formData = new FormData($("#qna01Form")[0]);
		ajaxCall2("/qnaAttachments.do",formData,false);
		
	}
	
	function qnaAttachSave(){
		
		$('#I_OLD1').val($('#old_attach1').html());
		$('#I_OLD2').val($('#old_attach2').html());
		
		var formData = new FormData($("#qna01Form")[0]);
		
		ajaxCall2("/qnaAttachSave.do",formData,false); //업데이트를 탄다	
	}

	</script>
	
</head>
<body>
	<div style="width: 1000px;">
		<div class="tbl_type">
			<form id="qna01Form"  name="qna01Form" enctype="multipart/form-data" method="post">
				<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
				<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
				<input id="I_SEQ"    name="I_SEQ"    type="hidden"/>
				<input id="I_FNM1"   name="I_FNM1"   type="hidden"/>
				<input id="I_FNM2"   name="I_FNM2"   type="hidden"/>
				<input id="I_FPT1"   name="I_FPT1"   type="hidden"/>
				<input id="I_FPT2"   name="I_FPT2"   type="hidden"/>
				<input id="I_FSNM1"  name="I_FSNM1"  type="hidden"/>
				<input id="I_FSNM2"  name="I_FSNM2"  type="hidden"/>
				<input id="I_PDNO"   name="I_PDNO"   type="hidden" value="0"/>
				<input id="I_OLD1"   name="I_OLD1"   type="hidden"/>
				<input id="I_OLD2"   name="I_OLD2"   type="hidden"/>
				
				<table class="table table-bordered table-condensed tbl_l-type">
					<colgroup>
						<col width="15%">
		           		<col width="30%" >
		           		<col width="15%" >
		           		<col>
		            </colgroup>
		           <tbody>
						<tr>
							<th scope="row" id="T_DCL">문의종류 <span class="color_red">*</span></th>
							<td>
								<div class="select_area">
									<select id="I_DCL" name="I_DCL" class="form-control"></select>
								</div>
							</td>
							<th scope="row">작성자 <span class="color_red">*</span></th>
							<td > 
								<input id="I_CNM" name="I_CNM" class="form-control" style="width:200px" type="text" maxlength="40" value="<%=session.getAttribute("NAM") %>"> 
							</td>
						</tr>
						<tr>
							<th scope="row">이메일</th>
							<td colspan="3">
								<div class="row">
									<div class="col-md-2">
										<input id="I_CMA1" name="I_CMA1" type="text" style="width:120px" class="form-control engNum"/>
									 </div>
									 <div class="col-md-1" style="width: 10px;">@</div>
									 <div class="col-md-2">
										<input id="I_CMA2" name="I_CMA2" type="text" style="width:130px" class="form-control engNum"/>
									 </div>
									 <div class="col-md-3 select2">
										<select id="I_CMA3" name="I_CMA3" class="form-control" onChange="document.getElementById('I_CMA2').value = this.value;"></select>
									 </div>
									<div class="col-md-4 ">          
										<input type="checkbox" id="I_IYNC" name="checkbox">개인정보(이메일) 활용 동의
										<input type="hidden" id="I_IYN" name="I_IYN">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">제목 <span class="color_red">*</span></th>
							<td colspan="3">
								<input style="text" class="form-control" id="I_TIT" name="I_TIT" maxlength="40" />
							</td>
						</tr>
						<tr>
							<th scope="row">첨부파일 1<span></span></th>
							<td colspan="3">
								<input type="file" id="attachments1" name="attachments1" style="display: inline;width: 400px;"/>
								<span id="old_attach1"></span>
							</td>
							
						</tr>
						<tr>
							<th scope="row">첨부파일 2<span></span></th>
							<td colspan="3">
								<input type="file" id="attachments2" name="attachments2" style="display: inline;width: 400px;"/>
								<span id="old_attach2"></span>
							</td>
						</tr>
						<tr>
							<th scope="col" colspan="4" style="text-align : center;">내용 <span class="color_red">*</span></th>
						</tr>
						<tr>
							<td scope="col" colspan="4">
		                		<div style="height: 400px;" >
									<textarea name="I_CON1" id="I_CON1" ></textarea>
									<input type="hidden" id="I_CON" name="I_CON" >
								</div>
			                </td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="modal-footer">
			<div class="min_btn_area" align="center">
				<button type="button" class="btn btn-primary" onclick="javascript:qnaSave();" id="saveBtn">저장</button>
				<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal();">취소</button>
			</div>
		</div>
	</div>
</body>
</html>
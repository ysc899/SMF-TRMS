<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>공문/새소식 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script type="text/javascript" src="/plugins/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
		      
		var I_SEQ = 0;
		var gridView;
		var dataProvider;
		var resultCode = "";
		var maxSize = 104857600;
    	var maxSizeDtl = 100;
    	var saveTypeE = "";
    
		$(document).ready( function(){
			
			resultCode = getCode(I_LOGMNU, I_LOGMNM, "NOTICE_KIND","N");
			$('#I_DCL').html(resultCode);
			jcf.replace($("#I_DCL"));
			
			resultCode = getCode(I_LOGMNU, I_LOGMNM, "MAIN_POP_YN","N");
			$('#I_PYN').html(resultCode);
			jcf.replace($("#I_PYN"));
			
			resultCode = getCode(I_LOGMNU, I_LOGMNM, "SYSTEM_DIV", "N");
			$('#I_SDV').html(resultCode);
			jcf.replace($("#I_SDV"));
			
			
			//달력 from~to
			$("#I_PFR").datepicker({
				dateFormat: 'yy-mm-dd',
				maxDate: $('#I_PTO').val(),
				onSelect: function(selectDate){
					$('#I_PTO').datepicker('option', {
						minDate: selectDate,
					});
				}
			});	
			$("#I_PTO").datepicker({
				dateFormat: 'yy-mm-dd',
				minDate: $('#I_PFR').val(),
				onSelect : function(selectDate){
					$('#I_PFR').datepicker('option', {
						maxDate: selectDate,
					});
				}		
			});
			 
			/* $(".fr_date").datepicker("setDate", new Date()); */
			
			CKEDITOR.replace('I_CON1',{
				 height : '300px',
				 filebrowserUploadUrl : '/ckeditorImageUploadNotice.do'
			});
			
		});
		
		function gridViewRead(gridValus){
			gridView = gridValus;
			$("#I_SEQ").val(gridView);
			I_SEQ = $("#I_SEQ").val();
			dataResult(I_SEQ);
		}
		
		function dataResult(seq){
			
			if(seq != ""){
				$('#saveBtn').html("수정");
				
				var formData = $("#searchForm").serialize();
				ajaxCall("/noticeDtl.do",formData);
			}
		}
		
		function sysNoticeSave(file){
			
			if($('#attachments').val() != ""){
	    		
	        	var fileSize = 0;
	        	
	        	fileSize = file.files[0].size;
	        	
	    		if(fileSize > maxSize){
	    			CallMessage("231","alert",[maxSizeDtl]); //첨부파일 사이즈는 {0} 이내로 가능합니다.
	    			return;
	    		}
	    	}

			if($('#I_DNO').val() == "" || $('#I_DNO').val() == null){
				CallMessage("201","alert",["문서번호는"]);	//필수 입력입니다.
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
			
			if(!popUpValidation()){return;}
			
			if($('#I_SEQ').val() == "" || $('#I_SEQ').val() == null )
			{	
				CallMessage("203","confirm","",sysNoticeReg);	//저장하시겠습니까?
			}else{
				CallMessage("225","confirm","",sysNoticeUpdate);	//수정하시겠습니까?
			}
		}
		
		function sysNoticeReg(){
			
			saveType = "C";
			
			document.getElementById('I_CON').value = CKEDITOR.instances.I_CON1.getData();
			
			var formData = $("#searchForm").serialize();
			
			callLoading(null,"on");
			ajaxCall("/sysNoticeNextVal.do","",false);
		}
		
		function popUpValidation(){
			var startDate,endDate;
			var bool;
			startDate = $("#I_PFR").val();
			endDate = $("#I_PTO").val();
			startDate = startDate.replace(/-/gi,"");
			endDate = endDate.replace(/-/gi,"");
			
			if(startDate > endDate){
				CallMessage("232","alert");	//시작일자와 종료일자를 확인하여 주십시오.
				return bool;
			}
			
			return true;
		}
		
		function sysNoticeUpdate(){
			saveType = "U";
			
			document.getElementById('I_CON').value = CKEDITOR.instances.I_CON1.getData();
			
			callLoading(null,"on");
			if($('#attachments').val() != ""){
				sysNoticeAttach();	
			}else {
				var formData = $("#searchForm").serialize();
				ajaxCall("/sysNoticeUpdate.do",formData,false);	
			}
		}
		
		function sysNoticeAttach(){
			var formData = new FormData($("#searchForm")[0]);
			ajaxCall2("/noticeAttachments.do",formData,false);
		}
		
		function popupClose(){
			closeModal("fnCloseModal");
		}
		
		function onResult(strUrl,response){
			if(strUrl == "/noticeAttachments.do"){
				$('#I_SEQ').val(response.I_SEQ);
				$('#I_FNM').val(response.S014FNM);
				$('#I_FPT').val(response.S014FPT);
				$('#I_FSNM').val(response.S014FSNM);
				
				noticeAttchSave();
			}
			
			if(strUrl == "/sysNoticeNextVal.do"){
				var resultList = response.resultList;
				$('#I_SEQ').val(resultList[0]["S014SEQ"]);
				
				if($('#attachments').val() != ""){
					sysNoticeAttach();	
				}else{
					var formData = $("#searchForm").serialize();
					ajaxCall("/sysNoticeSave.do",formData,false);
				}
			}
			if(strUrl == "/noticeAttchUpt.do"){
				var formData = $("#searchForm").serialize();
				ajaxCall("/sysNoticeUpdate.do",formData,false);
			}
			
			if(strUrl == "/noticeAttchSave.do"){
				if(saveType == "U"){
					var formData = $("#searchForm").serialize();
					ajaxCall("/sysNoticeUpdate.do",formData,false);
				}else{
					var formData = $("#searchForm").serialize();
					ajaxCall("/sysNoticeSave.do",formData,false);
				}
			}
			
			if(strUrl == "/noticeAttachList.do"){
				var resultList = response.resultList;
				if(resultList!=""){
					$('#I_FNM').val(resultList[0]["S014FNM"]);
					$('#old_attach').html(resultList[0]["S014FNM"]);
					$('#I_FPT').val(resultList[0]["S014FPT"]);
					$('#I_FSNM').val(resultList[0]["S014FSNM"]);	
				}
			}
			
			if(strUrl == "/sysNoticeSave.do"){
					if(response.O_MSGCOD == 200){
						callLoading(null,"off");
						CallMessage("221","alert","",popupClose);	//저장을 완료하였습니다.	
					} else {
						$('#I_SEQ').val("");
						callLoading(null,"off");
						CallMessage("244", "alert", ["저장"]);
					}
					
			}
			
			if(strUrl == "/sysNoticeUpdate.do"){
					if(response.O_MSGCOD == 200){
						callLoading(null,"off");
						CallMessage("222","alert","",popupClose);	//수정을 완료하였습니다.
					} else {
						callLoading(null,"off");
						CallMessage("244", "alert", ["수정"]);
					}
			}
			
			if(strUrl == "/noticeDtl.do"){
				var result1 = response.result1;
				$('#I_DCL').val(result1[0]["S014DCL"]);
				jcf.replace($("#I_DCL"));
				$('#I_CNM').val(result1[0]["S014CNM"]);
				$('#I_WDT').val(result1[0]["S014WDT"]);
				$('#I_SDV').val(result1[0]["S014SDV"]);
				jcf.replace($("#I_SDV"));
				$('#I_PYN').val(result1[0]["S014PYN"]);
				jcf.replace($("#I_PYN"));
				
				$('#I_PFR').val(parseDate(result1[0]["S014PFR"]));
				$('#I_PTO').val(parseDate(result1[0]["S014PTO"]));
				
				$('#I_LCO').val(result1[0]["S014LCO"]);
				$('#I_TCO').val(result1[0]["S014TCO"]);
				$('#I_TIT').val(result1[0]["S014TIT"]);
				$('#I_DNO').val(result1[0]["S014DNO"]);
				
										
				CKEDITOR.instances.I_CON1.setData();
				
				var text = result1[0]["S014CON"];
				$('#I_CON').html(text);
				
				setTimeout(function(){
					CKEDITOR.instances.I_CON1.document.getBody().setHtml(text);
				},250);
				
				noticeAttachList();
			}
		}
		
		function noticeAttchSave(){
			
			var formData = new FormData($("#searchForm")[0]);
			
			if(saveType == "U"){
				if($('#old_attach').html() != ""){
					ajaxCall2("/noticeAttchUpt.do",formData,false);
				}else{
					ajaxCall2("/noticeAttchSave.do",formData,false);
				}
			}else{
				ajaxCall2("/noticeAttchSave.do",formData,false);
			}
		}
		
		function noticeAttachList()
		{
			var formData = $("#searchForm").serialize();
			ajaxCall("/noticeAttachList.do",formData,false);
		}
		
    </script>
    
</head>
<body>
	<div style="min-width: 1000px;">
		<form id="searchForm" name="searchForm" enctype="multipart/form-data" method="post">
			<div class="tbl_type">
				<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
				<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
				<input id="I_SEQ"  name="I_SEQ"  type="hidden"/>
				<input id="I_FNM"  name="I_FNM"  type="hidden"/>
				<input id="I_FSNM"  name="I_FSNM"  type="hidden"/>
				<input id="I_FPT"  name="I_FPT"  type="hidden"/>
				<input id="I_WDT"    name="I_WDT"    type="hidden"/>
				<input type="hidden" id="I_CON" name="I_CON" >
				<table class="table table-bordered table-condensed tbl_l-type">
					<colgroup>
		            	<col width="15%">
		            	<col width="17%">
		            	<col width="15%">
		            	<col width="20%">
		            	<col width="13%">
		            	<col width="20%">
		            </colgroup>
		            <tbody>
		            	<tr>
							<th scope="row" id="T_DCL" >문서종류 <span class="color_red">*</span></th>
							<td>
								<div class="select_area">
									<select id="I_DCL" name="I_DCL" class="form-control"></select>
								</div>
							</td>
							<th scope="row">문서번호 <span class="color_red">*</span></th>
							<td>
								<input id="I_DNO" name="I_DNO" class="form-control" type="text"  maxlength="20"/>
							</td>
							<th scope="row">시스템 구분<span class="color_red">*</span></th>
							<td>
								<div class="select_area">
									<select id="I_SDV" name="I_SDV" class="form-control"></select>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">메인 팝업 생성 유무 <span class="color_red">*</span></th>
							<td>
			                    <div class="select_area">
			                        <select id="I_PYN" name="I_PYN" class="form-control"></select>
			                    </div>
		                    </td>
		                    <th scope="row">메인팝업 생성 기간</th>
			                <td colspan="3">
		                        <div class="form-group">
		                            <span class="period_area">
		                                <label for="startDt3" class="blind">날짜 입력</label>
		
			                               <input type="text" class="fr_date calendar_put" id="I_PFR" name="I_PFR">
			                               ~
			                               <label for="endDt3" class="blind">날짜 입력</label>
			                                <input type="text" class="fr_date calendar_put" id="I_PTO" name="I_PTO">
			                        </span>
		                        </div>
			               	</td>
						</tr>
			            <tr>
		                	<th scope="row" colspan="1" id="T_LCO">좌측 시작 좌표</th>
		                    <td>
		                    	<input id="I_LCO" name="I_LCO" type="number"  class="form-control" maxlength="5"/>
		                    </td>
		                    <th scope="row" id="T_TCO">상단 시작 좌표</th>
		                  	<td>
		                  		<input id="I_TCO" name="I_TCO" type="number" class="form-control" maxlength="5"/>
		                  	</td>
		                  	<th scope="row">작성자<span class="color_red">*</span></th>
							<td>
								<input id="I_CNM" name="I_CNM" class="form-control" type="text" maxlength="40" value="<%=session.getAttribute("NAM") %>"/>
							</td>
		                </tr>
		                <tr>
		                    <th scope="row">제목 <span class="color_red">*</span></th>
		                    <td colspan="5">
		                    	<input id="I_TIT" name="I_TIT" type="text" class="form-control" maxlength="200">
		                    </td>
		                </tr>
						<tr>
							<th scope="row">첨부파일 <span></span></th>
		                    <td colspan="5">
		                    	<input type="file" id="attachments" name="attachments" style="display: inline;width: 400px;"/>
		                    	<span id="old_attach"></span>
		                    </td>
						</tr>
						<tr>
		                     <th scope="col" colspan="6" style="text-align : center;">내용 <span class="color_red">*</span></th>
		                </tr>
		                <tr>
		                	<th scope="col" colspan="6">
		                		<div style="width: 100%; height: 440px;">
									<textarea name="I_CON1" id="I_CON1" ></textarea>
								</div>
		                	</th>
		                </tr>
		            </tbody>
				</table>
		</div>
		<div class="modal-footer" >
	      	<div class="min_btn_area" align="center">
            	<button type="button" class="btn btn-primary"  onclick="javascript:sysNoticeSave(this.form.attachments);" id="saveBtn">저장</button>
				<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal();">닫기</button>
	        </div>
	    </div>
	    </form>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	<script type="text/javascript" src="/plugins/ckeditor/ckeditor.js"></script>
	
	<script type="text/javascript">
	
	var resultCode = "";
	var searchSetup
	var count = 0;
	var I_VCD;
	
	$(document).ready( function(){
		CKEDITOR.replace('I_CON1',{
			height :'263px',
			filebrowserUploadUrl : '/ckeditorImageUploadHelp.do'
		});
		
		//dataResult();	
	});
	
	function dataResult(){
		//$('#I_MCD').val(userData.I_MCD);
		//jcf.replace($('#I_MCD'));
		var formData = $("#searchForm").serialize();
		callLoading(null, "off");
		ajaxCall("/mainQcSetMenuList.do", formData, false);
	}
	
	function userData(data){
		$('#I_MCDP').val(data.I_MCD);
		$('#I_CONP').val(data.I_CON);
		//console.log("I_MCD =" + data.I_MCD);
		//console.log($('#I_MCD').val());
		//console.log($('#I_CON').val());
		//jcf.replace($('#I_MCD'));
		//$('#I_CON').val(data.I_CON);
		//$('#I_MCD').val(data.I_MCD);
		//$('#I_CON').val(data.I_CON);
	}
	
	function onResult(strUrl,response){
		callLoading(null, "off");
		var resultList =  response.resultList;
		var optionList;
		var S001PMNM;
		
		if(strUrl == "/mainQcSetMenuList.do"){							// 메뉴 SELECT
			optionList = "";
			optionList='<option value="">선택</option>';
			optionList+='<option value="MAIN">메인</option>';
			$.each(resultList, function(key, val){
				S001PMNM = "";
				$.each(resultList, function(key2, val2){
					
					if(val.S001PMCD == val2.S001MCD){
						S001PMNM = val2.S001MNM + " > " + val.S001MNM;
						return false;
					}
					if(val.S001PMCD == 'ROOT'){
						if(val.S001MCD != val2.S001PMCD){
							S001PMNM = val.S001MNM;
		 				}else{
		 					S001PMNM = '';
		 					return false;
		 				}
					} 
				});
				if(!isNull(S001PMNM)){
					optionList += '<option value="'+val.S001MCD+'">'+ S001PMNM + '</option>';
				}
			});
			$('#I_MCD').html(optionList);
			$("#I_MCD option[value!="+$('#I_MCDP').val()+"]").remove();
			
			//search();
			CKEDITOR.instances.I_CON1.setData();
			
			var text = $('#I_CONP').val();
			
			setTimeout(function(){
				CKEDITOR.instances.I_CON1.document.getBody().setHtml(text);
			},250);
			
			CKEDITOR.instances.I_CON1.on('instanceReady', function(event) {
				  CKEDITOR.instances.I_CON1.focus();
			});
			
			
		}
		jcf.replace($('#I_MCD'));
		if(strUrl == "/sysHelpSave.do"){							// 메뉴 SELECT
			CallMessage("221","alert","");	//필수 입력입니다.
			closeModal("search","");
		}
	}
		
	function save(){
		if($('#I_MCD').val() == ""){
			CallMessage("201","alert",["메뉴는"]);	//필수 입력입니다.
			return;
		}
				
		if(CKEDITOR.instances.I_CON1.getData() == ""){
			CallMessage("201","alert",["내용은"]);	//필수 입력입니다.
			return;
		}
		
		$('#I_CON').val(CKEDITOR.instances.I_CON1.getData());
		
		//callLoading(null,"on");
		var formData = $("#searchForm").serialize();
		ajaxCall("/sysHelpSave.do",formData,false);
	}
	
	</script>
	
</head>
<body >
	<div style="width : 1000px; overflow:auto">
		<div class="tbl_type">
			<form id="searchForm" name="searchForm">
				<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
				<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
				<input id="I_HOS" name="I_HOS" type="hidden"  value="<%=ss_hos%>"/>
				<input id="I_AGR" name="I_AGR" type="hidden"  value="<%=ss_agr%>"/>	<!-- 권한 그룹 -->
				<input id="I_MCDP" name="I_MCDP" type="hidden"/>
				<input id="I_CONP" name="I_CONP" type="hidden"/>
				<div class="srch_box" style="border: 1px solid #ccc; padding: 10px 10px">
		            <div class="srch_box_inner">
		                <div class="srch_item">
		                    <label class="label_basic" style="display: inline-block;"><font color="#ff0000">*</font> 메뉴</label>						  
							<div class="select_area" style="min-width: 50%">
								<select  id="I_MCD" name="I_MCD" class="form-control"></select>										
							</div>
						</div>          
		            </div> 
		        </div>
		        <div align="center">		
					<h5><font color="#ff0000">*</font> 도움말</h5>
					<div style="height: 400px;" >
						<textarea name="I_CON1" id="I_CON1" ></textarea>
						<input type="hidden" id="I_CON" name="I_CON">
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<div class="min_btn_area">
				<button type="button" class="btn btn-primary" onclick="save()">저장</button>
				<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
			</div>
		</div>
	</div>
	
</body>
</html>



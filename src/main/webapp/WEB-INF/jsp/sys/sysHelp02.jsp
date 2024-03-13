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
		//dataResult();	
	});
	
	function dataResult(data){
		//console.log("확인");
		//$('#I_MCD').val(userData.I_MCD);
		//jcf.replace($('#I_MCD'));
		//console.log(JSON.stringify(data));
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
	
	function dataResult2(){
		var formData = $("#searchForm").serialize();
	  	/* 메뉴 Quick Setup 조회에서도 사용 
	  	 * MainController 참조
	  	 * MenuQcSetList.do
	  	 * */
	  	callLoading(null, "off");
		ajaxCall("/sysHelpList.do", formData, false);
		$('#tbody').empty();
	}
	
	function onResult(strUrl,response){
		callLoading(null, "off");
		var resultList =  response.resultList;
		var optionList;
		var S001PMNM;
		if(strUrl == "/mainQcSetSave.do" || strUrl == "/mainQcSetUdt.do" ){
			saveCount--;
			//console.log(saveCount);
			if(saveCount == 0){
				CallMessage("221","alert","",dataResult2);
			}
		}
		if(strUrl == "/mainQcSetMenuList.do"){							// 메뉴 SELECT
			optionList="";
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
			//$('#I_MCD').val($('#I_MCDP').val());
			$("#I_MCD option[value!="+$('#I_MCDP').val()+"]").remove();
			//console.log($('#I_MCDP').val());
			
			
			//search();
			
			$('#I_CON').html($('#I_CONP').val());
		}
		jcf.replace($('#I_MCD'));
		
		if(strUrl == "/sysHelpList.do"){
			var resultList =  response.resultList;
			if (resultList.length > 0){
				$('#I_CON').html(resultList[1].S013CON);	
			}
		}
		if(strUrl == "/mainQcSetList.do"){
			var optionList, optionSelect;
			I_VCD = [];	
			count = resultList.length;
			if(resultList.length > 0){
				for(var i=0; i<resultList.length; i++){
					if(!isNull(resultList[i].S008VCD)){
						I_VCD.push(resultList[i].S008VCD);
					}else{
						I_VCD.push('');
					}
					optionList = "";
					optionSelect = "";
					$.ajax({
						 url : "/getCommCode.do"
						, type : "post"
						//, data : {"I_COR" : "NML", "I_UID" : "twbae","I_IP" : "100.100.40.183","I_MCD" : $("#searchWordCode").val(), "I_MNM" : $("#searchWordName").val()}
						, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : resultList[i].S007RCV}
						, dataType: 'json'
						, async:false
						, success : function(response){
							var resultList =  response.resultList;
							optionList += '<option value="">선택</option>';
							$.each(resultList, function(k, v) {
								optionList += '<option value="'+v.$R_SCD+'">'+v.$R_CNM+ '</option>';
							});																													 
	
						}
					});
					optionSelect = '<div class="select_area a-wh100"><select class="form-control" style="padding:0 9px" id="I_RCV'+i+'" name="I_RCV'+i+'"></select></div>';
					searchSetup = '<tr><td style="text-align: left"><input type="hidden" id="I_RCD'+i+'" value="'+resultList[i].S007RCD+'">'+resultList[i].S007RNM+'</td><td>'+optionSelect+'</td></tr>';
					$('#tbody').append(searchSetup);
					
					$('#I_RCV'+i).append(optionList);
					if(!isNull(I_VCD)){
						$('#I_RCV'+i).val(I_VCD[i]).prop('selected', true);
					}
					jcf.replace($('#I_RCV'+i));
	
					window.setTimeout(function () {
						callResize();
					}, 100);
					
				}
			}else{					
				var searchSetup = '<tr><td style="text-align: left">&nbsp;</td><td>&nbsp;</td></tr>';
				$('#tbody').append(searchSetup);
				
			}
		}
	}
	
	var saveCount = 0;
	function modify(){
		closeModal("openPopup", 3);
	}
	</script>
	
</head>
<body >
	<div style="width : 1000px; ">
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
								<select  id="I_MCD" name="I_MCD" style="disable:true"></select>										
							</div>
						</div>          
		            </div> 
		        </div>
		        <div align="center">		
					<h5><font color="#ff0000">*</font> 도움말</h5>
				</div>
				<div style="height: 400px; overflow:auto" id="I_CON" >
				</div>
				
			</form>
		</div>
		<div class="modal-footer">
			<div class="min_btn_area">
				<button type="button" class="btn btn-primary" onclick="modify()">수정</button>
				<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal('chkReset', 'N')">닫기</button>
			</div>
		</div>
	</div>
	
</body>
</html>



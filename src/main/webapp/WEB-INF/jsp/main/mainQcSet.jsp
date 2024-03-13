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
	
	<script type="text/javascript">
	
	var resultCode = "";
	var searchSetup
	var count = 0;
	var I_VCD;
	
	$(document).ready( function(){
		
		dataResult();	
	});
	
	function dataResult(){
		var formData = $("#searchForm").serialize();
		callLoading(null, "off");
		ajaxCall("/mainQcSetMenuList.do", formData, false);
	}
	
	function search(){
		searchSetup = "";
		callLoading(null, "on");
		dataResult2();
	}
	
	function dataResult2(){
		var formData = $("#searchForm").serialize();
	  	/* 메뉴 Quick Setup 조회에서도 사용 
	  	 * MainController 참조
	  	 * MenuQcSetList.do
	  	 * */
	  	callLoading(null, "off");
		ajaxCall("/mainQcSetList.do", formData, false);
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
			$('#I_MCD').html("");
			optionList='<option value="">선택해주세요</option>'
			$.each(resultList, function(key, val){
				S001PMNM = "";
				$.each(resultList, function(key2, val2){
					
					if(val.S001PMCD == val2.S001MCD){
						if(val.S003CNT > 0){
							S001PMNM = val2.S001MNM + " > " + val.S001MNM;
						}
						return false;
					}
					if(val.S001PMCD == 'ROOT'){
						if(val.S001MCD != val2.S001PMCD){
							if(val.S003CNT > 0){
								S001PMNM = val.S001MNM;
							}
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
			$('#I_MCD').append(optionList);
			jcf.replace($('#I_MCD'));
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
					//console.log(resultList[i]);
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
							var popupList =  response.resultList;
							optionList += '<option value="">선택</option>';
							$.each(popupList, function(k, v) {
								if(resultList[i].S007MCD == 'RSTPRE' && resultList[i].S007RCV == 'TEST_STAT'){ // 이전결과 - 검사상태 일떄
									if(v.$R_SCD != '3'){			// 재검 제외
										optionList += '<option value="'+v.$R_SCD+'">'+v.$R_CNM+ '</option>';
									}	
								}else{
									optionList += '<option value="'+v.$R_SCD+'">'+v.$R_CNM+ '</option>';
								}
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
	function save(){
		var formData;
		saveCount = count;
		
		if(count != 0){
			for(var i=0; i<count;i++){
				if(isNull($('#I_RCV'+i).val())){
					strUrl  = "/mainQcSetUdt.do";
				}else{
					if(isNull(I_VCD[i])){
						strUrl = "/mainQcSetSave.do";
					}else{
						strUrl  = "/mainQcSetUdt.do";
					}
				}
				
				formData = $("#searchForm").serialize();
				formData += "&I_RCD="+$('#I_RCD'+i).val();
				formData += "&I_VCD="+$('#I_RCV'+i).val();
				callLoading(null, "on");
				ajaxCall(strUrl, formData, false);
			}		
		}
	}
	
	</script>
	
</head>
<body >
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_HOS" name="I_HOS" type="hidden"  value="<%=ss_hos%>"/>
		<input id="I_AGR" name="I_AGR" type="hidden"  value="<%=ss_agr%>"/>	<!-- 권한 그룹 -->
		<div class="srch_box" style="border: 1px solid #ccc; padding: 10px 10px">
            <div class="srch_box_inner">
                <div class="srch_item">
                    <label class="label_basic" style="display: inline-block;">메뉴</label>						  
					<div class="select_area" style="min-width: 50%">
						<select  id="I_MCD" name="I_MCD" class="form-control" onchange="search()"></select>										
					</div>
				</div>          
            </div> 
        </div>
        		
		<h5 class="tit_h5">검색어 설정</h5>
		<div class="con_section overflow-scr">
		    <div class="tbl_type" style="height: 300px; overflow: auto;">
		    	<table class="table table-bordered table-condensed tbl_c-type">
		        	<colgroup id="table" >
		                <col width="40%">
		                <col >
		            </colgroup>
		    		<thead>
		            	<tr>
		                    <th scope="col">검색어</th>
		                    <th scope="col">기본 값</th>
		                </tr>
		            </thead>
		            <tbody id="tbody">
		            	<tr>
		            		<td style="text-align: left">&nbsp;</td>
		            		<td>&nbsp;</td>
		            	</tr>
		            </tbody>
		        </table>
		    </div>
		</div>	
	</form>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-primary" onclick="save()">저장</button>
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal('','','main')">닫기</button>
		 </div>
	</div>
</body>
</html>



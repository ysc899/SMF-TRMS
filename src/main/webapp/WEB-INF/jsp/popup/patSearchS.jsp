<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>수진자 검색 팝업</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script type="text/javascript">
	
	var resultCode = "";
	var gridValues = [];
	var gridLabels = [];
	var dataProvider, gridView;
	
	$(document).ready( function(){

	});
	
	/* 팝업 호출 input 설정 */
 	function gridViewRead(gridValus){
 		$('#I_HOS').val(gridValus["I_HOS"]);		// 병원 코드
		//$('#I_NAM').val(gridValus["I_NAM"));		// 수진자 명
		//$('#I_CHN').val(gridValus["I_CHN"));		// 차트 번호 
		
		if(gridValus["I_ECF"] == "C"){
			$('#I_FNM').val(gridValus["I_NAME"]);		// 병원명
//			$('#hospSearch').css('display', 'none');
// 			$('body').height(615);
			callResize();
		}
		
		if(gridValus["I_ECF"] == "E"){
			$('#hospSearch').show();
			$('#I_FNM').val(gridValus["I_FNM"]);		// 병원명  
		}
		
	    var param ={I_PSCD:"GENDER", "I_LOGMNU":I_LOGMNU, "I_LOGMNM":I_LOGMNM};
	    $.ajax({
			 url : "/getCommCode.do"
			, dataType: 'json'
			, type : "post"
			, data : param
			, success : function(response){
				var resultList =  response.resultList;
	            $.each(resultList,function(k,v){
	                gridValues.push(v.VALUE);
	                gridLabels.push(v.LABEL);
	            });
	   		 	setGrid();
			}
		});
	} 
	
	function setGrid(){
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    
	    gridView.setEditOptions({
	    	insertable: false,
	    	deletable: true,
	    	readOnly: true,
	    	deletable: false
	    })

	    var fields = [
	    	  {"fieldName": "F120COR"}		// COR
	    	, {"fieldName": "F100HOS"}		// 병원 코드
	    	, {"fieldName": "F120FNM"}		// 병원 명
	    	, {"fieldName": "F100BDT"}		// 생년월일
	    	, {"fieldName": "F100NAM"}		// 수진자 명
			, {"fieldName": "F100SEX"}		// 성별
			, {"fieldName": "F100AGE", dataType: "number"}		// 나이
			, {"fieldName": "F100CHN"}		// 차트번호-암호화
			, {"fieldName": "F100C"}		// 차트번호-복호화
	    	, {"fieldName": "I_LOGMNU"}
	    	, {"fieldName": "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	        {"name": "F120FNM", "fieldName": "F120FNM", "editable": false, "width": "100", "styles": {"textAlignment": "near"}, "header": {"text": "병원 명"}}
	      ,	{"name": "F100C", "fieldName": "F100C", "editable": false, "width": "100", "styles": {"textAlignment": "near"}, "header": {"text": "차트번호"}}
	      ,	{"name": "F100BDT", "fieldName": "F100BDT", "editable": false, "width": "100", "styles": {"textAlignment": "near"}, "header": {"text": "생년월일"}, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3"}
	      ,	{"name": "F100NAM", "fieldName": "F100NAM", "editable": false, "width": "100", "styles": {"textAlignment": "near"}, "header": {"text": "수진자 명"}}
	      ,	{"name": "F100SEX", "fieldName": "F100SEX", "editable": false, "width": "100", "styles": {"textAlignment": "center"}, "header": {"text": "성별"}, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "textReadOnly":true}}//,dropDownWhenClick :true}
	      ,	{"name": "F100AGE", "fieldName": "F100AGE", "editable": false, "width": "100", "styles": {"textAlignment": "far"}, "header": {"text": "나이"}}
	    ];
	    gridView.setColumns(columns);
	    
	    //체크바 제거
	    var checkBar = gridView.getCheckBar();
	    checkBar.visible = false;
	    gridView.setCheckBar(checkBar);
	    
	    //상태바 제거
	    var StateBar = gridView.getStateBar();
	    StateBar.visible = false;
	    gridView.setStateBar(StateBar);
	    
	    gridView.setDisplayOptions({
			fitStyle: "even"
	    });
	    
	    gridView.onDataCellClicked = function (grid, index) {
	    	
	    	var data = [];
	    	data.push(strUrl);
	    	data.push(grid.getValues(index.itemIndex));
	    	
	    	closeModal("fnCloseModal", data);
	    	
	 	};
	 	
	 	if(!isNull($('#I_HOS').val()) && !isNull($('#I_NAM').val()) ){
	 		dataResult();
	 	}
	}
	
	function dataResult(){
		strUrl = "/patSearchSList.do";
		var formData = $("#searchForm").serialize();
		ajaxCall(strUrl, formData);
	}
	
	function onResult(strUrl, response){
		var resultList;
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		dataProvider.setRows(resultList);		
		gridView.setDataSource(dataProvider);
	}
	//조회
	function search(searchId){
 		if(isNull($('#I_FNM').val())){ 		//사원을 로그인시 병원코드 입력후 조회버튼 누를경우
			if(!isNull($('#I_HOS').val())){
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		} 
		if(!saveValidation()) { return;}
		dataResult();
	}
	
	/* 팝업 호출 및 종료 */
	function openPopup(){
		var gridValus,labelNm;
		labelNm = "병원 정보 조회";

		//fnOpenPopup(url,labelNm,gridValus,callback)
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup2("/hospSearchS.do",labelNm,gridValus,callFunPopup2);
	}
	/*callback 호출 */
	function callFunPopup2(url,iframe,gridValus){
		if(url=="/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
			iframe.gridViewRead(gridValus);
		}
	}

	/*close 호출*/
	function fnCloseModal(obj){
		//var data = Object.values(obj);
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(data[0] == "/hospSearchSList.do"){
			$('#I_HOS').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
		}
	}
	
	function saveValidation(){
		
		if(strByteLength($("#I_CHN").val()) > 15){
			CallMessage("292", "alert", ["차트번호는 15 Byte"]);
			return false;
		}
		
		if($('#I_HOS').val() == ""){
			CallMessage("245", "alert", ["병원을"]);
			return false;			
		}

 		if($('#I_NAM').val() == ""  && $('#I_CHN').val() == ""){
			CallMessage("245", "alert", ["수진자명 또는 차트번호를"]);
			return false;			
		} 
 		if($('#I_NAM').val() != "")
 		{
	 		if($('#I_NAM').val().length == 1)
 			{
				CallMessage("245", "alert", ["수진자명은 두 글자 이상 "]);
				return false;			
 			
 			}
		} 
		
		return true;
	}
	
	function enterSearch(enter){
		
		if(event.keyCode == 13){
			if(enter == "1"){
				search();
			}else{
				openPopup();
			}
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
			if(target.id == "I_NAM"){
				$('#I_CHN').val('');
			}
		}
	}
	</script>
	
</head>
<body  style="width: 850px;height: 550px;">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<%-- <input id="I_HOS" name="I_HOS" type="hidden"  value="<%=ss_hos%>"/> --%>
		<div class="srch_box">
            <div class="srch_box_inner">
                <div class="srch_item">
                    <label class="label_basic">차트 번호</label>
                    <input type="text" class="srch_put" id="I_CHN" name="I_CHN" maxlength="15" onkeydown="javascript:enterSearch(1)">
                </div>
                <div class="srch_item">
                	<label class="label_basic">수진자 명</label>
					<input type="text" class="srch_put" id="I_NAM" name="I_NAM" maxlength="25" onkeydown="javascript:enterSearch(1)">
				</div>          
            </div> 
            <div class="srch_box_inner m-t-10" id="hospSearch" style="display:none">
	            <div class="srch_item hidden_srch" style="width: 100%;">
					<label for="" class="label_basic">병원</label>
					<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" maxlength="5"  onkeydown="javasscript:enterSearch(1)" class="numberOnly"/>
					<input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" maxlength="40" style="width: 30%" readonly="readonly"onClick="javascript:openPopup()">
				</div>            
			</div>	
            <div class="btn_srch_box" >
                <button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
            </div> 
        </div>
	</form>
	<br>
    <div class="con_section overflow-scr" >
        <div class="tbl_type">
			<div id="realgrid" style="height: 400px;"  class="realgridH"></div>
		</div>
	</div>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		 </div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원 검색 팝업</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	
	var resultCode = "";
	var I_FNM;
	var gridView;
	var dataProvider;
	var curData; 
	var strUrl;
	var arrData;

	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		dataResult();
	        }
	    }
	}
	$(document).ready( function(){
		//$("#I_FNM").val(I_FNM);
		//setGrid();
		//callResize2();
	});
	
	/* 팝업 호출 input 설정 */
  	function gridViewRead(gridValus){
		curData = [];
		arrData = [];
		for(var i=0; i<(gridValus.length/5); i++){
			curData.push(gridValus.substr(i*5, 5));
		}
		setGrid();
	}
	
	function setGrid(){
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    
	    setStyles(gridView);
	    setGridBar(gridView, false,"state");
	    gridView.setCheckBar({
  			showAll: false  
		});
	    gridView.setCheckableExpression("row < 10", true);

	    var fields = [
	    	  {"fieldName": "F120COR"}		// COR
	    	, {"fieldName": "F120PCD"}		// 병원 코드
	    	, {"fieldName": "F120FNM"}		// 병원 명
	    	, {"fieldName": "F120WNO"}		// 대표자명
			, {"fieldName": "F120TEL"}		// 전화번호
			, {"fieldName": "F120SML"}		// 이메일
			, {"fieldName": "F120MBC"}		// 담당자 사번
			, {"fieldName": "F910NAM"}		// 사원 이름
	    	, {"fieldName": "I_LOGMNU"}
	    	, {"fieldName": "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	        {"name": "F120PCD", "fieldName": "F120PCD", "editable": false, "width": "80", "styles": {"textAlignment": "center"}, "header": {"text": "병원코드"}}
	      ,	{"name": "F120FNM", "fieldName": "F120FNM", "editable": false, "width": "150", "styles": {"textAlignment": "near"}, "header": {"text": "병원 명"}}
	      ,	{"name": "F910NAM", "fieldName": "F910NAM", "editable": false, "width": "80", "styles": {"textAlignment": "near"}, "header": {"text": "담당자"}}
	      ,	{"name": "F120TEL", "fieldName": "F120TEL", "editable": false, "width": "100", "styles": {"textAlignment": "near"}, "header": {"text": "연락처"}}
	      ,	{"name": "F120SML", "fieldName": "F120SML", "editable": false, "width": "150", "styles": {"textAlignment": "near"}, "header": {"text": "이메일"}}
	      ,	{"name": "F120WNO", "fieldName": "F120WNO", "editable": false, "width": "80", "styles": {"textAlignment": "near"}, "header": {"text": "대표자"}}
	    ];
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    
	    gridView.setDisplayOptions({
			fitStyle: "even"
	    });
	    
	    gridView.onItemChecked = function (grid, itemIndex, checked) {
	        var item = [];
	        item.push(itemIndex);
	        itemChecked(item);
			if(!checked){
				for(var i=0; i<arrData.length;i++){
					if(arrData[i].F120PCD == grid.getValues(itemIndex).F120PCD){
						arrData.splice(i, 1);	
					}
				}
			}else{
				arrData.push(grid.getValues(itemIndex));
			}
	    };
	    //flash 지원
 	    gridView.onItemsChecked = function (grid, items, checked) {
	    	itemChecked(items);
	    }; 
	    dataResult();
	}
	function itemChecked(item){
        if(gridView.getCheckedRows().length > 20){
        	CallMessage("258","alert",["20개만"]);
        	gridView.checkRows(item, false);
        }
	}
	
	
	function dataResult(){
		strUrl = "/hospSearchSList.do";
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

		if(!isNull(curData)){			// 체크 된 값 유지
			var PCD;
			var check = [];
			var count;		// 체크 갯수
			if(isNull(arrData)){
				for(var i=0; i<resultList.length; i++){
					PCD = resultList[i].F120PCD;
					
					for(var j=0; j<curData.length;j++){
						if(curData[j] == (PCD)){
							check.push(i);
							arrData.push(resultList[i]);
							count++;
							break;
						}
					}
					if(count == curData.length){
						break;
					}
				}
			}else{
				for(var i=0; i<resultList.length; i++){
					PCD = resultList[i].F120PCD;
					
					for(var j=0; j<curData.length;j++){
						if(curData[j] == (PCD)){
							check.push(i);
							count++;
							break;
						}
					}
					if(count == curData.length){
						break;
					}
				}
			}
			gridView.checkRows(check, true);
		}
	}
	
	function insertRow(){
		dataRows = gridView.getCheckedRows();
		console.log(dataRows);
		if(dataRows == ""){
			CallMessage("243");
			return;
		}
		/*
		var data = [];
		data.push(strUrl);
		for(var i=0; i<arrData.length;i++){
			//data.push(dataProvider.getJsonRow(dataRows[i]));
			data.push(arrData[i]);
		}
		*/
		
		var strData = [];
		//strUrl = "/hospSearchSList.do"
		strData.push(strUrl);
		for(var i in dataRows){
			strData.push(gridView.getValues(dataRows[i]));	
		}
		//console.log("strData = "+strData);
		//console.log("data = "+ data);
		closeModal("fnCloseModal", strData);
	}
	//조회
	function search(){
		dataResult();
	}
	
	</script>
	
</head>
<body>
<div style="width: 800px;height: 540px;">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<div class="srch_box">
			<div class="srch_box_inner">
				<div class="srch_item">
					<label for="" class="label_basic">병원 코드</label>
					<input type="text" class="engNumOnly" id="I_HOS" name="I_HOS" maxlength="5"   onkeydown="return enterSearch(event)" >
				</div>
				<div class="srch_item">
					<label for="" class="label_basic">병원 명</label>
					<input type="text" id="I_FNM" name="I_FNM" maxlength="30"   onkeydown="return enterSearch(event)" >
				</div>               
			</div>
			<div class="btn_srch_box">
				<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
			</div> 
		</div>
	</form>      
<!--     <div class="tit_btn_area modal_btn_area">
		<button type="button" class="btn btn-darkBlue btn-sm" onClick="javascript:insertRow()">추가</button>        
    </div> -->
    <div class="con_section overflow-scr m-t-10" style="height: 400px;">
        <div class="tbl_type">
			<div id="realgrid" style="height: 400px;" class="realgridH"></div>
		</div>
	</div>
	<div class="modal-footer">
		<div class="min_btn_area">
			<button type="button" class="btn btn-darkBlue" onClick="javascript:insertRow()">추가</button>
			<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		 </div>
	</div>

</div>
</body>
</html>
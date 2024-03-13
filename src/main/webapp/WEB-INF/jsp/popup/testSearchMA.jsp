<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>검사항목 검색 팝업</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>	
	<script type="text/javascript">
	
	var pscd = "";
	var I_EKD = "INIT_PW";
	var gridView;
	var dataProvider;
	var curData; 
	
	$(document).ready( function(){

		//setGrid();
	});

	function enterSearch(e){	
		if (e.keyCode == 13) {
			// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
			if(e.target.nodeName == "INPUT" ){
				search();
			}
		}
	} 

	function gridViewRead(gridValus){
		curData = [];
		for(var i=0; i<(gridValus.length/7); i++){
			curData.push(gridValus.substr(i*7, 7));
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
	    
	    var fields=[
	    		  {fieldName:"F010COR"}		//COR
				, {fieldName:"F010GCD"}		//검사 코드
				, {fieldName:"F011ACD"}		//부속 코드
				, {fieldName:"F010FKN"}		//검사 항목(한글)
				, {fieldName:"F010TCD"}		//검체 코드
				, {fieldName:"F010TNM"}		//검체 명
				, {fieldName:"F010MSC"}		//검사 방법코드
				, {fieldName:"F010MSNM"}	//검사방법 명
				, {fieldName:"T001DAY"}		//검사 일
				, {fieldName:"F010EED"}		//검사 소요일
				, {fieldName:"F010TCD"}		//검체 코드
				, {fieldName:"SOCD"}		//보험 코드
				, {fieldName:"F010DOW"}		//검사 요일
				, {fieldName:"F010BDY"}		//검사 기준일
				, {fieldName:"I_LOGMNU"}	//메뉴코드
				, {fieldName:"I_LOGMNM"}	//메뉴명
	    	];
	    dataProvider.setFields(fields);

	    var columns=[
	    	  {name:"F010GCD",		fieldName:"F010GCD",	header:{text:"검사 코드"},		width:80}
	    	, {name:"F010FKN",		fieldName:"F010FKN",	header:{text:"검사 항목"},		width:120 }//,dropDownWhenClick :true
	    	, {name:"F010TNM",		fieldName:"F010TNM",	header:{text:"검체"},			width:150 }
	    	, {name:"F010MSNM",		fieldName:"F010MSNM",	header:{text:"검사법"},		width:100 }
	    	, {name:"T001DAY",		fieldName:"T001DAY",	header:{text:"검사일/소요일"},	width:120 }//,dropDownWhenClick :true
	    	, {name:"SOCD",			fieldName:"SOCD",		header:{text:"보험코드"},		width:120 }
	    ];
		
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    gridView.setDisplayOptions({
			fitStyle: "even"
	    });
	    
	    dataResult();	        
	    gridView.onItemChecked = function (grid, itemIndex, checked) {
	        var item = [];
	        item.push(itemIndex);
	        itemChecked(item);
	    };
	    //flash 지원
 	    gridView.onItemsChecked = function (grid, items, checked) {
	    	itemChecked(items);
	    }; 

	}
	
	function itemChecked(item){
        if(gridView.getCheckedRows().length > 30){
        	CallMessage("258","alert",["30개만"]);
        	gridView.checkRows(item, false);
        }
	}
	
	function dataResult(){
		strUrl = "/testSearchMListA.do";
		var formData = $("#searchForm").serialize();
		ajaxCall(strUrl, formData);
	}
	
	function onResult(strUrl,response){
		var resultList;
		if(!isNull(response.resultList)){
			resultList = response.resultList;
		}
		
		dataProvider.setRows(resultList);		
		gridView.setDataSource(dataProvider);
		
		if(!isNull(curData)){			// 체크 된 값 유지
			var GCD, ACD;
			var check = [];
			var count;		// 체크 갯수
			for(var i=0; i<resultList.length; i++){
				GCD = resultList[i].F010GCD;
				ACD = resultList[i].F011ACD;
				if(isNull(ACD)){
					ACD = "  ";
				}	
				
				for(var j=0; j<curData.length;j++){
					if(curData[j] == (GCD+ACD)){
						check.push(i);
						count++;
						break;
					}
				}
				if(count == curData.length){
					break;
				}
			}
			gridView.checkRows(check, true);
		}
	}
	
	function insertRow(){
		dataRows = gridView.getCheckedRows();

		var data = [];
		data.push(strUrl);
		for(var i=0; i<dataRows.length;i++){
			data.push(dataProvider.getJsonRow(dataRows[i]));
		}
		
		closeModal("fnCloseModal", data);
	}

	// 조회
	function search(){
		dataResult();
	}
		
	</script>
</head>
<body  style="width:900px;height: 540px;">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_HOS" name="I_HOS" type="hidden"  value="<%=ss_hos%>"/>
      	<div class="srch_box">
            <div class="srch_box_inner">
                <div class="srch_item srch_item_v2" style="width: 220px;">
                    <label class="label_basic">보험코드</label>
                    <input type="text" class="engNum" id="I_SOCD" name="I_SOCD" maxlength="15" onkeydown="return enterSearch(event)" style="width: 120px;" >
                </div>
                <div class="srch_item srch_item_v2" style="width: 200px;">
                    <label class="label_basic">검사코드</label>
                    <input type="text" class="engNumOnly" id="I_GCD" name="I_GCD" maxlength="5" onkeydown="return enterSearch(event)" style="width: 100px;" >
                </div>
                <div class="srch_item srch_item_v2" style="width: 330px;">
                    <label class="label_basic">검사항목</label>
					<input type="text" class="srch_put" id="I_FKN" name="I_FKN" maxlength="25" onkeydown="return enterSearch(event)" style="width: 220px;" >
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
</body>
</html>
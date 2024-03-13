<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>SMS 연동 관리 - 검사 항목</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>

	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script  type="text/javascript"   src="/plugins/daum/js/daum_addr_search.js " ></script>
	<script type="text/javascript">
	
	var I_LOGMNU = "hospSmsPat01";
	var I_LOGMNM = "SMS 연동 관리 - 검사 항목";
	var resultCode = "";
	var gridView,dataProvider;
	
	$(document).ready( function(){
		
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid1");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    
	    gridView.setEditOptions({
	    	insertable: false,
	    	deletable: true,
	    	readOnly: true,
	    	deletable: false
	    })

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
	    	  {name:"F010GCD",		fieldName:"F010GCD",		header:{text:"검사 코드"},			editable: false,	width:80}
	    	, {name:"F011ACD",		fieldName:"F011ACD",		header:{text:"부속 코드"},			editable: false,	width:80}
	    	, {name:"F010FKN",		fieldName:"F010FKN",	header:{text:"검사 항목"},		editable: true,		width:120 }//,dropDownWhenClick :true
	    	, {name:"F010TNM",		fieldName:"F010TNM",	header:{text:"검체"},		editable: true,	width:150, "displayRegExp":/^([0-9]{3})([0-9]{3,4})([0-9]{4})$/, "displayReplace": "$1-$2-$3"}
	    	, {name:"F010MSNM",		fieldName:"F010MSNM",		header:{text:"검사법"},			editable: true,	width:100 }
	    	, {name:"T001DAY",		fieldName:"T001DAY",		header:{text:"검사일/소요일"},		editable: true,	width:120 }//,dropDownWhenClick :true
	    	, {name:"SOCD",		fieldName:"SOCD",		header:{text:"보험코드"},			editable: true,	width:120 }
	    	, {name:"F010EED",		fieldName:"F010EED",		header:{text:"검사소요일"},			editable: true,	width:120 }
	    	, {name:"F010BDY",		fieldName:"F010BDY",		header:{text:"검사기준일"},			editable: true,	width:120 }
	    	, {name:"F010DOW",		fieldName:"F010DOW",		header:{text:"검사 요일"},			editable: true,	width:120 }
	    ];
	    
	    gridView.setColumns(columns);
	    
	    dataResult1();

		
	})
	
	function dataResult1(){
		strUrl = "/testSearchMList.do";
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
	function search(){
		dataResult1();
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
	
	</script>
	
</head>
<body>
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_HOS" name="I_HOS" type="hidden"  value="<%=ss_hos%>"/>
		<div align="right">
		<label>검사 코드</label>
		<input type="text" id="I_GCD" name="I_GCD" maxlength="5">
		<label>검사 항목</label>
		<input type="text" id="I_FKN" name="I_FKN" maxlength="40">
			<input type="button" value="조회" onClick="javascript:search()">
		</div>
	</form>
	<div style="width : 600px; height: 610px; margin:auto">
		<div style="width : 100%; height: 500px;position:absolute; left:10px;">
			<label></label>
			<input type="button" value="추가" onClick="javascript:insertRow()">
			<div id="realgrid1" style="width : 100%; height: 100%;"></div>
		</div>
	</div>	
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		 </div>
	</div>
</body>
</html>
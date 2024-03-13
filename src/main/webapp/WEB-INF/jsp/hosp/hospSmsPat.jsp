<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>SMS 연동 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	
	<script type="text/javascript">
	
	var pscd = "";
	var I_EKD = "INIT_PW";
	var gridView1,dataProvider1,gridView2,dataProvider2,tooltip;
	var gridValues = [];
	var gridLabels = [];
	var I_FKN;
	var indexCheck;
		
	$(document).ready( function(){
/* 	    $('#I_FNM').change(function(){
	    	$('#I_HOS').val('');
	    }); */
		if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }
	    //if($('#I_ECF').val() == "C"){
	    	$('#hospRes').show();
	    	$('#hospAdd').show();
	    	$('#hospDel').show();
	    	$('#hospRes2').show();
	    	$('#hospAdd2').show();
	    	$('#hospDel2').show();
	    //}

		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "GENDER",'Y');
		$('#I_SEX').html(resultCode);
		
	    setGrid1();
		getGridCode();
	});
	
    function getGridCode(){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD=GENDER";
		ajaxCall("/getCommCode.do", formData);
	}
    
	function setGrid1(){
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    setStyles(gridView1);
		
	    gridView1.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	        editable: true
	    })
		    
	    var fields1=[
	    		  {fieldName:"C003HOS"}		//병원 코드
				, {fieldName:"C003COR"}		//회사 코드
				, {fieldName:"C003GCD"}		//검사 코드
				, {fieldName:"F010FKN"}		//검사 항목 명
				, {fieldName:"C003MSG"}		//메시지
				, {fieldName:"C003DYN"}		//삭제 여부
				, {fieldName:"C003CUR"}		//등록자 ID
				, {fieldName:"C003CDT"}		//등록 일자
				, {fieldName:"C003SEQ"}		//메시지 순번
				, {fieldName:"I_LOGMNU"}	//메뉴코드
				, {fieldName:"I_LOGMNM"}	//메뉴명
	    	];
	    dataProvider1.setFields(fields1);
	    
	    
	    var columns1=[
	    	  {name:"F010FKN",		fieldName:"F010FKN",	header:{text:"검사 항목"}, 	editable: false,		width:120, /* styles:{fontBold:true, fontUnderline: true } */renderer:{type: "link", url:"#",   requiredFields: "F010FKN", showUrl: false} }//,dropDownWhenClick :true
	    	, {name:"C003MSG",		fieldName:"C003MSG",	header:{text:"메시지"},		editable: true,		width:250, renderer:{ showTooltip: true },editor:{ type:"multiline", maxLength:3000 }  , styles: { "textWrap":"explicit" } }
	   	];
	    
		 // 툴팁 기능
	    gridView1.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        return tooltip;
	    }
	 
	    //컬럼을 GridView에 입력 합니다.
	    gridView1.setColumns(columns1);
	 	
	    gridView1.onDataCellClicked = function (grid, index) {
	 		
			var gridValue = gridView1.getValues(index.itemIndex);
			if( index.column == "F010FKN"){
				if(!isNull(gridValue["C003MSG"])){
					$("#C003SEQ").val(grid.getValue(index.itemIndex, 8));
					if(!isNull(grid.getValue(index.itemIndex, 8))){
						dataResult2();
					}
				}
			}
	 		
	 	};
		//그리드 너비 보다 컬럼 너비가 작으면 width비율로 자동 증가
	    gridView1.setDisplayOptions({
			fitStyle: "even"
	    });

	}
	
	function setGrid2(){
	    dataProvider2 = new RealGridJS.LocalDataProvider();
	    gridView2 = new RealGridJS.GridView("realgrid2");
	    gridView2.setDataSource(dataProvider2);
	    setStyles(gridView2);
	    
	    gridView2.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })
		    
	    var fields2=[
	    	, {fieldName:"C003SEQ"}
  			, {fieldName:"C001HOS"}		//병원 코드
			, {fieldName:"C001CHN"}		//차트번호
			, {fieldName:"C001NAM"}		//수진자 명
			, {fieldName:"C001HPN"}		//핸드폰 번호
			, {fieldName:"C001BDT"}		//생년월일
			, {fieldName:"C001SEX"}		//성별
			, {fieldName:"C001AGE", dataType: "number"}		//나이
			, {fieldName:"C001CUR"}		//등록자 ID
			, {fieldName:"C001CDT"}		//등록 일자
			, {fieldName:"C001CTM"}		//등록 시간
			, {fieldName:"C001CIP"}		//등록자 IP
			, {fieldName:"I_LOGMNU"}	//메뉴코드
			, {fieldName:"I_LOGMNM"}	//메뉴명
	    	];
	    dataProvider2.setFields(fields2);
	    	    
	    var columns2=[
	    	  	  {name:"C001CHN",		fieldName:"C001CHN",		header:{text:"차트번호"},			editable: false,	width:80}
		    	, {name:"C001NAM",		fieldName:"C001NAM",	header:{text:"수진자 명"},		editable: false,		width:120 }//,dropDownWhenClick :true
		    	, {name:"C001HPN",		fieldName:"C001HPN",	header:{text:"핸드폰 번호"}, "displayRegExp":/^([0-9]{3})([0-9]{3,4})([0-9]{4})$/, "displayReplace": "$1-$2-$3",		editable: false,	width:150 }
		    	, {name:"C001BDT",		fieldName:"C001BDT",		header:{text:"생년월일"}, styles: {textAlignment: "center" }, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3",			editable: false,	width:100 }
		    	, {name:"C001SEX",		fieldName:"C001SEX",		header:{text:"성별"}, styles: {textAlignment: "center" },		editable: false,	width:120, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "textReadOnly":true}}//,dropDownWhenClick :true
		    	, {name:"C001AGE",		fieldName:"C001AGE",		header:{text:"나이"}, styles: {textAlignment: "far" },			editable: false,	width:120 }
	   	];

	    //컬럼을 GridView에 입력 합니다.
	    gridView2.setColumns(columns2);
		//그리드 너비 보다 컬럼 너비가 작으면 width비율로 자동 증가
	    gridView2.setDisplayOptions({
			fitStyle: "even"
	    });
		
	}
	
	function dataResult1(){		// sms 메시지 정보 - MWC003M@
		var formData = $("#searchForm").serialize();
		//console.log(formData);
		callLoading(null, "on");
		ajaxCall("/hospSmsPatList.do", formData, false);
	}
	
	function dataResult2(){		// 수진자 정보 - MWC003D@
		var formData = $("#searchForm").serialize();
		callLoading(null, "on");
		ajaxCall("/hospSmsPatDtlList.do", formData, false);
	}
	
	function onResult(strUrl,response){
		callLoading(null, "off");
		var resultList;
		if(!isNull(response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/getCommCode.do"){
            $.each(resultList,function(k,v){
                gridValues.push(v.VALUE);
                gridLabels.push(v.LABEL);
            });
            setGrid2();
		}
		
 		if(strUrl == "/hospSmsPatList.do"){
 			gridView1.cancel();
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
			gridView1.setAllCheck(false);
			var data1 = dataProvider2.getJsonRows(0, -1);
			dataProvider2.removeRows(data1);
		}
		
 		if(strUrl == "/hospSmsPatDtlList.do"){
 			gridView2.cancel();
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
			gridView2.setAllCheck(false);
		}
 		
 		
	}
	
	/* 팝업 호출 및 종료 */
	function openPopup(index){
		var gridValus = "";
		var labelNm,strUrl;
		if(index == "1"){
			strUrl = "/testSearchM.do";
			labelNm = "검사항목 조회";
			indexCheck = index;
		}else if(index == "2"){
			//console.log($("#C003SEQ").val());
 			if( $("#C003SEQ").val()==""){
				CallMessage("246","alert",["검사항목"]);// 선택 후 추가해주세요.
				return;
			}
 			
			strUrl = "/hospSmsPat02.do";
			labelNm = "수진자 정보 조회";
			
		}else if(index == "3"){
			indexCheck = index;
			strUrl = "/testSearchM.do";
			labelNm = "검사항목 조회";
			gridValus = $('#I_GAD').val();
			
		}else if(index == "4"){
			strUrl = "/hospSearchS.do";
			labelNm = "병원 조회";
		}
		//fnOpenPopup(url,labelNm,gridValus,callback)
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	/*callback 호출 */
	function callFunPopup(url,iframe,gridValus){
		if(url == "/testSearchM.do"){
			//console.log("A" + gridValus);
			if(isNull(gridValus)){
				gridValus = '';
			}
		}
		
		if(url == "/hospSmsPat02.do"){
			if(isNull(gridValus)){
				gridValus = '';
			}
		}
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
		}
		iframe.gridViewRead(gridValus);
	}

	/*close 호출*/
	function fnCloseModal(obj){
		//var data = Object.values(obj);
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		
		if(data[0] == "/testSearchMList.do"){
/* 			if(indexCheck == "0"){
				var values = [];
//				var ACD = "";	

		    	// hospPatList.do 에서 넘어노는 데이터
		    	for(var i=1; i<data.length; i++){

		    		data1 = [];
		    		data1.push($('#I_HOS').val(), '', data[i].F010GCD, data[i].F010FKN);
		    		values.push(data1);
		    	}
					 		
		 		gridView1.commit();
				dataProvider1.insertRows(0, values, 0, data.length, false);
				gridView1.setDataSource(dataProvider1);
				gridView1.showEditor();
				gridView1.setFocus();
				
			} */
			if(indexCheck == "1"){
				var values=[];										//grid에 들어갈 데이터
		    	var data1;										//data에서 받은 데이터 []로
		    	var bool;
		    	var data2 = dataProvider1.getJsonRows(0, -1);	// 현재 그리드에 있는 데이터
				
				var data3 = [];									// 현재 그리드의 있는 데이터중 차트 번호
				for(var i=0; i<data2.length; i++){
					data3.push(data2[i].C003GCD);
				}
				var resultList;
		    	$.ajax({
					 url : "/hospSmsPatChkSave.do"
					, type : "post"
					, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM}
					, dataType: 'json'
					, async: false
					, success : function(response){
						resultList =  response.resultList;
						//console.log(resultList);
					}
				});
		    	
		    	//console.log(resultList[0].C003GCD);
		    	var chartNum = [];
		    	for(var i=1; i<data.length; i++){
		    		bool = true;
		    		data1 = [];
		    		//console.log(data2);
		    		//console.log(data3);
		    		if(!isNull(data2)){
		    			//console.log(1);
		    			for(var j=0; j<data2.length; j++){
			    			if(data[i].F010GCD == data3[j]){
			    				chartNum.push(data[i].F010FKN);
			    				bool = false;
			    				break;
			    			}
			    		}
		    		}else{
		    			for(var j=0; j<resultList.length; j++){
		    				if(data[i].F010GCD == resultList[j].C003GCD){
		    					chartNum.push(data[i].F010FKN);
		    					//console.log(data[i].F010GCD);
		    					//console.log(resultList[j].C003GCD);
		    					bool = false;
		    				}
		    			}
		    		}
			    		
		    		//console.log(bool);
		    		if(bool){
		    			data1.push($('#I_HOS').val(), '', data[i].F010GCD, data[i].F010FKN);
		    			values.push(data1);
		    		}
		    		
		    	}
		    	var a = new String(chartNum);
		    	
		    	if(!isNull(chartNum)){
			    	CallMessage("280","alert",["중복된 검사항목 ( " + a + " ) 데이터가 "]);
		    	}
		    	
		 		gridView1.commit();
				dataProvider1.insertRows(0, values, 0, data.length, false);
				gridView1.setDataSource(dataProvider1);
				gridView1.showEditor();
				gridView1.setFocus();
			}
			if(indexCheck == "3"){
				var I_GAD="";
				I_FKN=[];
				//console.log(data[1]);
				var I_GCD = "|";
					for(var i=1; i<data.length;i++){
						/* if(isNull(data[i].F011ACD)){
							data[i].F011ACD = "  ";
						} */
						//I_GAD += (data[i].F010GCD + data[i].F011ACD);
						
						I_GCD += data[i].F010GCD + "|";
						I_GAD += data[i].F010GCD;
						I_FKN.push(data[i].F010FKN);
					} 
					$('#I_GCD').val(I_GCD);
					$('#I_GAD').val(I_GAD);
					$('#I_FKN').val(I_FKN);
					tooltip = "";
					for(var x in I_FKN){
						tooltip += (Number(x)+1)+". "+I_FKN[x] + "\n";	
					}
					$('#I_FKN').attr('title', tooltip);
			}
		}
	    if(data[0] == "/hospPatList.do"){
	    	var values=[];										//grid에 들어갈 데이터
	    	var data1;										//data에서 받은 데이터 []로
	    	var bool;
	    	var data2 = dataProvider2.getJsonRows(0, -1);	// 현재 그리드에 있는 데이터
			
			var data3 = [];									// 현재 그리드의 있는 데이터중 차트 번호
			for(var i=0; i<data2.length; i++){
				data3.push(data2[i].C001CHN);
			}
			
	    	for(var i=1; i<data.length; i++){
	    		bool = true;
	    		data1 = [];
	    		for(var j=0; j<data2.length; j++){
	    			if(data[i].C001CHN == data3[j]){
	    				bool = false;
	    				break;
	    			}
	    		}
	    		if(bool){
	    			data1.push($('#C003SEQ').val(), data[i].C001HOS, data[i].C001CHN, data[i].C001NAM, data[i].C001HPN, data[i].C001BDT, data[i].C001SEX, data[i].C001AGE);
	    			values.push(data1);
	    		}
	    		
	    	}
	    			
   			gridView2.commit();
   			dataProvider2.insertRows(0, values, 0, values.length, false);
   			gridView2.setDataSource(dataProvider2);
   			gridView2.showEditor();
   			gridView2.setFocus();
	    }
	    if(data[0] == "/hospSearchSList.do"){
			$('#C003HOS').val(data[1].F120PCD);
			$('#I_HOS').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
	    }
		
	}

	
	// 저장
 	function saveRow(gridId){
 		
		var createRows,updateRows;
        var rtnCall = true;
		
        if(gridId == "1"){
	        gridView1.commit();
	        
			createRows = dataProvider1.getStateRows('created');
			updateRows = dataProvider1.getStateRows('updated');
			
			if(createRows != ""){
				CallMessage("203","confirm", "", creatStart1);
			}else{
				if(updateRows != ""){
					CallMessage("225","confirm", "", updateStart1);
				}
			}
        }
        if(gridId == "2"){
	        gridView2.commit();

	        createRows = dataProvider2.getStateRows('created');
			if(createRows != ""){
				CallMessage("203","confirm", "", creatStart2);
			}

        }
 	}
	
 	function creatStart1(){
        var rtnCall = true;
		var createRows = dataProvider1.getStateRows('created');
		
		if(createRows != ""){
			if(!saveValidation(createRows, dataProvider1)) { return;}
			callLoading(null, "on");
			rtnCall = ajaxListCall("/hospSmsMsgSave.do",dataProvider1,createRows, createEnd1, false); // 등록
		}
 	}
 	
 	function createEnd1(rtnCall){
		var	updateRows = dataProvider1.getStateRows('updated');
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["SMS 메시지 저장"]);		//{0}에 실패하였습니다.
		}else{
			if(updateRows != ""){ 
				if(rtnCall){
					dataProvider1.setRowState(0, "create", false);
					if(!saveValidation(updateRows, dataProvider1)) { return;}
					rtnCall = ajaxListCall("/hospSmsMsgUdt.do",dataProvider1,updateRows,creAupdEnd, false); // 수정
				}
			}else{
				CallMessage("221","alert","",dataResult1);
			}
		}
 	}
 	
 	function creAupdEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["수진자 정보수정"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("282","alert","",dataResult1);
		}
 	}
 	
 	function updateStart1(){
        var rtnCall = true;
		var updateRows = dataProvider1.getStateRows('updated');

		if(updateRows != ""){
 			if(rtnCall){
				if(!saveValidation(updateRows, dataProvider1)) { return;}
 				callLoading(null, "on");
				rtnCall = ajaxListCall("/hospSmsMsgUdt.do",dataProvider1,updateRows,updateEnd1, false); // 수정
			} 
		}
 	}
 	
	function updateEnd1(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["SMS 메시지 수정"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("222","alert","",dataResult1);
		}
	}
	
 	function creatStart2(){
        var rtnCall = true;
		var createRows = dataProvider2.getStateRows('created');
		
		if(createRows != ""){ 
			callLoading(null, "on");
			rtnCall = ajaxListCall("/hospSmsPatSave.do",dataProvider2,createRows,createEnd2, false); // 등록
		}
 	}
 	
 	function createEnd2(rtnCall){
		var	updateRows = dataProvider2.getStateRows('updated');
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["SMS 메시지 - 수진자 정보 저장"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("221","alert","",dataResult2);
		}
 	}
 	
	function deleteRow(gridId) {
		var dataRows1, dataRows2;
		dataRows1 = gridView1.getCheckedRows();
		dataRows2 = gridView2.getCheckedRows();
		if(gridId == "1"){
			if(dataRows1 == ""){ 
				CallMessage("243");
				return;
			}
			CallMessage("242","confirm", "", deleteStart1);
		}
		if(gridId == "2"){
			if(dataRows2 == ""){ 
				CallMessage("243");
				return;
			}
			CallMessage("242","confirm", "", deleteStart2);
		}
		
 	}
	
	function deleteStart1(){
		var rtnCall =  true;
		var dataRows,strUrl;
		dataRows = gridView1.getCheckedRows();
		strUrl = "/hospSmsMsgDel.do";
		callLoading(null, "on");
		rtnCall = ajaxListCall(strUrl,dataProvider1,dataRows, deleteEnd1, false); // 삭제	
	}
	function deleteStart2(){
		var rtnCall =  true;
		var dataRows,strUrl;
		dataRows = gridView2.getCheckedRows();
		strUrl = "/hospSmsPatDel.do";
		callLoading(null, "on");
		rtnCall = ajaxListCall(strUrl,dataProvider2,dataRows, deleteEnd2, false); // 삭제	
	}
	
	function deleteEnd1(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244", "alert", ["삭제"]);
		}else{
			CallMessage("223","alert","",dataResult1);
		}
	}
 	
	function deleteEnd2(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244", "alert", ["삭제"]);
		}else{
			CallMessage("223","alert","",dataResult2);
		}
	}
	
 	function search(){
 		if(isNull($('#I_FNM').val())){ 		
			if(!isNull($('#I_HOS').val())){
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		} 
 		if(!searchValidation()) { return;}
		dataResult1();
		var data1 = dataProvider2.getJsonRows(0, -1);
		dataProvider2.removeRows(data1);
		$('#C003SEQ').val("");
 	}

	function saveValidation(dataRows, dataProvider){
		
		var data = [];
		var bool;
  	    
		var i = 0;
		do{
			data.push(dataProvider.getJsonRow(dataRows[i]));
			bool = validation(1, data[i], (dataRows[i]+1));
			if(!bool){
				return bool;
			}else{
				i++;
			}
			
		}while(i < dataRows.length);
		
		return true;
		
	}
	
	function searchValidation(){
		
		/* if($('#I_HOS').val() == ""){
			CallMessage("245", "alert", ["병원 코드를"],dataClean);
			return false;
		} */

		return true;
	}
	
	function validation(validId, data, num){

		if(validId == "1"){						//추가, 수정
			if(isNull(data.C003MSG)){
				CallMessage("245", "alert", [num+"번째 메시지를"]);
				return false;
			}
		}
		return true;
	}
	
	function init(){
		$('#I_FKN').removeAttr("title");
		I_FKN = [];
		$('#I_GAD').val('');
		$('#I_GCD').val('');
		$('#I_FKN').val('');
	}
	
	function enterSearch(enter){
		
		if(event.keyCode == 13){
			if(enter == "1"){
				search();
			}else{
				openPopup(enter);
			}
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
		}
	}
	
	function resetFNM(){
		$('#I_FNM').val("");
	}
	
	function dataClean(){
		dataProvider1.clearRows();
		dataProvider2.clearRows();
	}
	</script>
</head>
<body>

<div class="content_inner">
   
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/>
		<input id="I_AGR" name="I_AGR" type="hidden" value="<%=ss_agr%>"/>
		<%-- <input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>" /> --%>
		<input id="I_GCD" name="I_GCD" type="hidden"/>
		<input id="F010FKN" name="F010FKN" type="hidden"/>
		<input id="C003SEQ" name="C003SEQ" type="hidden"/>
		<input type="hidden" id="I_GAD" name="I_GAD">
		<div class="container-fluid">
			<div class="con_wrap col-md-12 srch_wrap">
				<div class="title_area open">
					<h3 class="tit_h3">검색영역</h3>
					<a href="#" class="btn_open">검색영역 접기</a>
				</div>
				<div class="con_section row">
					<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
					           
					<div class="srch_box">
						<div class="srch_box_inner">
							<div class="srch_item">
								<label for="" class="label_basic">검사항목</label>
								<input type="text" class="test_pop_srch_ico srch_put" id="I_FKN" name="I_FKN" readonly="readonly" onClick="javascript:openPopup(3)">
								<!-- <button type="button" class="btn btn-red btn-sm" title="검색" onClick="javascript:openPopup(3)"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button> -->
								<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init()">초기화</button>
								<!-- <button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init()"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button> -->
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">메시지</label>
								<input type="text" class="srch_put" id="I_MSG" name="I_MSG" maxlength="3000" onkeydown="javascript:enterSearch(1)">
							</div>               
						</div>
						
						<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item hidden_srch">
								<label for="" class="label_basic">병원</label>
								<input id="I_HOS" name="I_HOS" type="text" class="srch_put srch_hos numberOnly"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup(4)" maxlength="5" onkeydown="javasscript:enterSearch(1)"/>
								<input type="text" class="hos_pop_srch_ico srch_put" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly" onclick="javascript:openPopup(4)">
							</div>
						</div> 						
						
						<div class="btn_srch_box">
							<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
						</div> 
					</div>
				</div>
			</div>
		</div>
	</form>
	
    <div class="container-fluid">
        <div class="con_wrap col-md-5 col-sm-12">
            <div class="title_area">
                <h3 class="tit_h3">SMS 메시지 정보</h3>
                <div class="tit_btn_area">
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(1)" style="display:none" id="hospRes">등록</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow(1)" style="display:none" id="hospAdd">저장</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow(1)" style="display:none" id="hospDel">삭제</button>
                </div>
            </div>
            <div class="con_section overflow-scr">
                <div class="tbl_type">
                <div id="realgrid1" class="realgridH"></div>
                </div>
            </div>
        </div>
        
        <div class="con_wrap col-md-7 col-sm-12">
            <div class="title_area">
                <h3 class="tit_h3">수진자 정보</h3>
                <div class="tit_btn_area">
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(2)" style="display:none" id="hospRes2">등록</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow(2)" style="display:none" id="hospAdd2">저장</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow(2)" style="display:none" id="hospDel2">삭제</button>                           
                </div>
            </div>
            <div class="con_section overflow-scr">
                <div class="tbl_type">
                 <div id="realgrid2" class="realgridH"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
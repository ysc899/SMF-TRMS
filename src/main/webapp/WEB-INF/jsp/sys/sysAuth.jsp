<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>권한 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<!--
	<link rel="stylesheet" href="/css/commonCss.css"> 
    <script type="text/javascript" src="/plugins/jquery/js/jquery.js"></script>
    <script type="text/javascript" src="/js/commonJs.js"></script>
	-->
    <%@ include file="/inc/common.inc"%>
    
	
	<script>

	var dataProvider1,gridView1;
	var treeDataProvider2,treeView2;
	var acd = "";
	var resultCode = "";
	$(document).ready( function(){
		
		//RealGridJS.setTrace(false);
		//RealGridJS.setRootContext("/js");
	    
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    setStyles(gridView1);
	    gridView1.setDataSource(dataProvider1);
	    gridView1.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	editable: true
	    })

	    var fields1 = [
	    	{fieldName: "$R_SCD"},
	        {fieldName: "$R_CNM"},
	        {fieldName: "$R_DSC"}
	    ];
	    dataProvider1.setFields(fields1);
	    
	    var columns1 = [
	    	
	        {name: "$R_SCD", fieldName: "$R_SCD", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "권한 그룹 코드"}, editable: false}
	      ,	{name: "$R_CNM", fieldName: "$R_CNM", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "권한 그룹 명"} , editable: false}
	      , {name: "$R_DSC", fieldName: "$R_DSC", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "비고"}		 , editable: false}
	    ];
	    gridView1.setColumns(columns1);
	    
	    gridView1.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
	    
	    var checkBar = gridView1.getCheckBar();
	    checkBar.visible = false;
	    gridView1.setCheckBar(checkBar);
	    
	    var stateBar = gridView1.getStateBar();
	    stateBar.visible = false;
	    gridView1.setStateBar(stateBar);
	    
		gridView1.onDataCellClicked = function (grid, index) {
	 		acd = grid.getValue(index.itemIndex, 0);
	 		dataResult2();
		};
		
		treeDataProvider2 = new RealGridJS.LocalTreeDataProvider();
	    treeView2 = new RealGridJS.TreeView("realgrid2");
	    setStyles(treeView2);
	    treeView2.setDataSource(treeDataProvider2);
	    treeView2.setEditOptions({
	    	insertable: false,
	    	appendable: false,
	    	deletable: false,
	    	readOnly: true
	    })
	    
	    var fields2 = [
	    	{fieldName: "$R_RLT"},
	        {fieldName: "$R_ICON"},
	        {fieldName: "$R_MCD"},
	        {fieldName: "$R_MNM"},
	        {fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"},
	        {fieldName: "I_ACD"},
	        {fieldName: "I_HRC"}
	    ];
	    
	    treeDataProvider2.setFields(fields2);
	    
	    var columns2 = [
	        {name: "$R_MCD", fieldName: "$R_MCD", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "메뉴코드"},editable: false},
	        {name: "$R_MNM", fieldName: "$R_MNM", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "메뉴명"},editable: false}
	    ];
	    treeView2.setColumns(columns2);
	    
	    treeView2.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
	    
	 	//var checkBar = treeView2.getCheckBar();
	    //checkBar.visible = false;
	    //treeView2.setCheckBar(checkBar);
	    
	    var stateBar = treeView2.getStateBar();
	    stateBar.visible = false;
	    treeView2.setStateBar(stateBar);
	    
	    getGridCode();
	    
	    var resultCode = "";
		resultCode = getCode(I_LOGMNU, I_LOGMNM, "RETR_AUTH", 'N');
		$('#retrAuth').html(resultCode);
		//콤보박스 초기에 보이기
		jcf.replace($("#retrAuth"));
	});
	
	function getGridCode(){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD=AUTH";
		ajaxCall("/getCommCode.do", formData);
	}
	
	function dataResult2(){
		$("#I_ACD").val(acd);
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysAuthDtl.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/getCommCode.do"){
			var resultList =  response.resultList;
			gridView1.closeProgress();
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
		}
		
		if(strUrl == "/sysAuthDtl.do"){
			var resultList =  response.resultList;
			treeView2.setAllCheck(false);
			var hrc = "A";
			for(var i in resultList){
				if(resultList[i].$R_HRC != ""){
					//alert("확인");
					hrc = resultList[i].$R_HRC;
				}
				
			}
			//alert(hrc);
			$('#retrAuth').val(hrc);
			jcf.replace($("#retrAuth"));
			
			treeDataProvider2.setRows(resultList, "$R_RLT", false);		
			treeView2.setDataSource(treeDataProvider2);
			
			treeView2.expandAll();
			
			for (var i=1;i <= treeDataProvider2.getRowCount(); i++){
				var chk = false;
				
				if(treeDataProvider2.getValue(i, "$R_ICON") == "1") {
					chk = true; 
				} else {
					chk = false;
				}
			  	//console.log(treeDataProvider2.getValue(i, "$R_ICON") + " = " + i + " = " + chk);
				treeView2.checkItem(i-1
								  , chk
								  , false
								  , false);
			}
			
		}
	}
	
	function saveRow(){
		CallMessage("203","confirm", "", saveStart);
	}

	function saveStart(){
		$("#I_ACD").val(acd);
		
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysAuthDel.do", formData);
		
		var dataRows = treeView2.getCheckedRows();
		if(dataRows != ""){
			for (var i in dataRows){
				treeDataProvider2.setValue(dataRows[i], "I_ACD", acd);
				treeDataProvider2.setValue(dataRows[i], "I_HRC", $('#retrAuth').val());
			}
			ajaxListCall("/sysAuthSave.do", treeDataProvider2, dataRows, saveEnd)
		}
	}
	
	function saveEnd(rtnCall){
		if(!rtnCall){
			CallMessage("244","alert",["저장"]);	
		} else {
			CallMessage("278","alert",["저장을"],dataResult2);	//278={0} 완료하였습니다.
// 			setTimeout("dataResult2(acd)", 1000);
		}
	}
	</script>
</head>
<body>
	<form id="searchForm" name="searchForm">
	<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
	<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
	<input id="I_ACD" name="I_ACD" type="hidden"/>
	
	<div class="content_inner">
         <div class="container-fluid">
             <div class="con_wrap col-md-6 col-sm-12">
                 <div class="title_area">
                     <h3 class="tit_h3">권한 그룹</h3>
                 </div>
                 <div class="con_section">
                     <div class="tbl_type">
                     	<div id="realgrid1" class="realgridH"></div>
                     </div>
                 </div>
             </div>
         
         
             <div class="con_wrap col-md-6 col-sm-12">
                 <div class="title_area">
                     <h3 class="tit_h3">메뉴</h3>
                     <div class="tit_btn_area">
                     	<div class="select_area">
                     	 	 <select id="retrAuth" class="form-control">
                             </select>
                         </div>
                         <button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()">저장</button>
                     </div>
                 </div>
                 <div class="con_section">
                     <div class="tbl_type">
                     	<div id="realgrid2" class="realgridH"></div>
                     </div>
                 </div>
             </div>
         </div>
                            
     </div>    
     <!-- content_inner -->
     </form>  
</body>
</html>



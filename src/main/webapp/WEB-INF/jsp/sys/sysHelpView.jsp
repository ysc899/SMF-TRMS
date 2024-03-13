<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>		
	<title>시스템관리::메뉴관리</title>
	
	<script>
		
	var pmcd = "";
	var treeView1,treeDataProvider1;
    
	$(document).ready( function(){
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
	    treeDataProvider1 = new RealGridJS.LocalTreeDataProvider();
	    treeView1 = new RealGridJS.TreeView("realgrid1");
	    setStyles(treeView1);
	    treeView1.setDataSource(treeDataProvider1);
	    treeView1.setEditOptions({
	    	insertable: false,
	    	appendable: false,
	    	deletable: false,
	    	readOnly: true
	    })
	    
	    var fields1 = [
	    	{fieldName: "$R_RLT"},
	        {fieldName: "$R_MCD"},
	        {fieldName: "$R_PMCD"},
	        {fieldName: "$R_MNM"}
	    ];
	    
	    treeDataProvider1.setFields(fields1);
	    
	    var columns1 = [
	        {name: "$R_MNM", fieldName: "$R_MNM", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {"text": "메뉴명"}, editable: false}
	    ];
	    treeView1.setColumns(columns1);
	    
	 	treeView1.onDataCellClicked = function (tree, index) {
	 		dataResult2(tree.getValue(index.itemIndex, 1));
	 		$('#viewTitle').html(tree.getValue(index.itemIndex, 3));
		};
	    var checkBar = treeView1.getCheckBar();
	    checkBar.visible = false;
	    treeView1.setCheckBar(checkBar);
	    
	    var stateBar = treeView1.getStateBar();
	    stateBar.visible = false;
	    treeView1.setStateBar(stateBar);
	    
	    treeView1.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	        var tooltip = value;
	        return tooltip;
	    }
	    
	    $('#viewTitle').html("메인");
	    dataResult2("MAIN");
	});
	
	function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysMenuAgrList.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/sysMenuAgrList.do"){
			var resultList =  response.resultList;
			treeView1.closeProgress();
	        treeDataProvider1.setRows(resultList, "$R_RLT", false);		
			treeView1.setDataSource(treeDataProvider1);
		}
		if(strUrl == "/sysHelpViewData.do"){
			var resultList =  response.resultList;
			if(resultList.length > 0){
				$('#helpView').html(resultList[0].S013CON);	
			} else {
				$('#helpView').html("");
			}
			window.setTimeout(function () {
				callResize();
			}, 100);
			
		}
	}
	
	function dataResult2(mcd){
		$("#I_MCD").val(mcd);
		var formData = $("#searchForm").serialize();
		ajaxCall("/sysHelpViewData.do", formData);
	}
	
	</script>
</head>
<body>
	<div style="width : 1500px; height:700px;" >
		<form id="searchForm" name="searchForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
			<input id="I_MCD" name="I_MCD" type="hidden"/>
			<input id="I_MNM" name="I_MNM" type="hidden"/>
			<input id="I_PMCD" name="I_PMCD" type="hidden"/>
			<input id="I_AGR" name="I_AGR" type="hidden"  value="<%=ss_agr%>"/>	<!-- 권한 그룹 -->
	          <div class="con_wrap col-md-3 col-sm-12">
                  <div class="title_area">
                      <h3 class="tit_h3">메뉴</h3>
                  </div>
                  <div class="con_section overflow-scr">
                      <div class="tbl_type">
                      	<div id="realgrid1"  class="realgridH" style="height:600px"></div>	
                      </div>
                  </div>
              </div>
              <div class="con_wrap col-md-9 col-sm-12" >
              	  <div class="title_area">
                      <h3 class="tit_h3" id="viewTitle">도움말</h3>
                  </div>
                  <div class="con_section overflow-scr">
                  	<div  id="helpView" style="height:600px;overflow:auto"></div>
                  </div>
              </div>
                  	
	     </form>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>SMS메시지 템플릿</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	var I_LOGMNU = "SMS메시지 템플릿";
	var I_LOGMNM = "SMSMessage";
	var dataList = [];
	var strUrl = "smsMsgPopup.do";
	var dataProvider,gridView;
	$(document).ready( function(){
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		dataProvider = new RealGridJS.LocalDataProvider();
		gridView = new RealGridJS.GridView("realgrid");
	    setStyles(gridView);
	    gridView.setHeader({
	    	height:40
	    })
	    /*
	    gridView.setDisplayOptions({
		  heightMeasurer: "fixed",
		  rowResizable: true,
		  rowHeight: 150
		});
	    */

	    
	    gridView.setDataSource(dataProvider);
	    gridView.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields=[
		  		  	  {fieldName:"C002HOS"}		//병원 코드
					, {fieldName:"C002COR"}		//회사 코드
					, {fieldName:"C002SEQ"}		//메시지 순번
					, {fieldName:"C002NAM"}		//메시지 명
					, {fieldName:"C002MSG"}		//메시지
					, {fieldName:"C002DYN"}		//삭제 여부
					, {fieldName:"C002CUR"}		//등록자 ID
					, {fieldName:"C002CDT"}		//등록 일자
					, {fieldName:"I_LOGMNU"}	//메뉴코드
					, {fieldName:"I_LOGMNM"}	//메뉴명
		  	];
		  dataProvider.setFields(fields);
		  
		  
		  var columns=[
		  	  {name:"C002NAM",		fieldName:"C002NAM",	header:{text:"메시지 명"},		editable: false,		width:250, editor : { maxLength : 40 }  }//,dropDownWhenClick :true
		  	, {name:"C002MSG",		fieldName:"C002MSG",	header:{text:"메시지"},		editable: false,		width:500
		  		  , renderer:{ showTooltip: true },editor:{ type:"multiline", maxLength:4000}  , styles: { textWrap:"explicit"} } //ellipse : 
		 	];
	    
		gridView.setColumns(columns);
		
		gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    };
		
	    /*
		gridView.onDataCellClicked = function (grid, index) {
			dataProvider.removeRow(index.itemIndex, true);
			//dataResult4(grid.getValues(index.itemIndex));
		};
		*/
		
		var checkBar = gridView.getCheckBar();
		checkBar.visible = true;
	    gridView.setCheckBar(checkBar);
	    
	    var stateBar = gridView.getStateBar();
	    stateBar.visible = false;
	    gridView.setStateBar(stateBar);
	});
	
	function search(){
		dataResult();
	}
	
	function dataResult(){
		var formData = $("#popupForm").serialize();
		ajaxCall("/hospSmsList.do", formData);
	}
	
	function gridViewRead(formData){
		ajaxCall("/hospSmsList.do", formData);	
	}
	
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/hospSmsList.do"){
			var resultList;
			if(!isNull(response.resultList)){
				resultList = response.resultList;
			}
			
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	
	function reSize(){
		setTimeout("callResize()", 100);
		//http://219.252.39.22:8080/spring_basic/fileDown?UUID=	
	}
	
	function userData2(userData){
		$('#I_HOS').val(userData.I_HOS);
		$('#I_NAM').val(userData.I_NAM);
	}
	
	function selectMsg(){
		dataRows = gridView.getCheckedRows();
		if(dataRows.length > 1){
			CallMessage("206","alert",["1"]);// 선택 후 추가해주세요.
			return;
		} else if(dataRows.length == 0){ 
			CallMessage("246","alert",["메시지를"]);// 선택 후 추가해주세요.
			return;
		}
		var data = [];
		data.push(strUrl);
		for(var i=0; i<dataRows.length;i++){
			data.push(dataProvider.getJsonRow(dataRows[i]));
		}
		
		closeModal("fnCloseModal", data);
	}
    </script>
    
</head>
<body style="width : 500px;">
	<form id="popupForm" name="popupForm">
	<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
	<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		
	<input id="I_DTLDAT" name="I_DTLDAT" type="hidden">
	<input id="I_DTLJNO" name="I_DTLJNO" type="hidden">
	<input id="I_HOS" name="I_HOS" type="hidden">
		<div class="container-fluid">                
	        <div class="con_wrap col-md-12 col-sm-12">
            	<div class="srch_box">
                    <div class="srch_box_inner">
                    	<label for="I_NAM" class="label_basic">제목</label>
                    	<input type="text" class="srch_put" id="I_NAM" name="I_NAM" placeholder="" style="width:90%;">
                    </div> 
                                
                    <div class="btn_srch_box m-t-10">
                        <button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
                    </div> 
                </div>
                <div class="tit_btn_area modal_btn_area">
                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:selectMsg();">선택</button>
                </div>
            	<div class="tbl_type" style="overflow:auto">
					<div id="realgrid"  class="realgridH"></div>
                </div>                
	        </div>                    
	    </div>
	 </form>
</body>
</html>

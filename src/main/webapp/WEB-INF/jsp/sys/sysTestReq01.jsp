<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>항목별 통계 상세</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">

    var dataProvider,gridView;
    var pscd = "";
	var resultCode = "";
	var I_FNM;
	var count = 0;		// 페이지 확인 0이면 첫 페이지
	
	$(document).ready( function(){
		//$("#I_FNM").val(I_FNM);
		//setGrid();
		//callResize2();
	});
	
	/* 팝업 호출 input 설정 */
  	function gridViewRead( gridValus){
		var formData = $("#popupForm").serializeArray();
		$("#I_HOS").val(gridValus["I_HOS"]);
		$("#I_DAT").val(gridValus["I_DAT"]);
		$("#I_JNO").val(gridValus["I_JNO"]);
	
		
		setGrid();
	}
	
	function setGrid(){
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    setGridBar(gridView,false);
	    var fields = [

	    	{fieldName: "O_CHK"},
	    	{fieldName: "O_REP"},
	    	{fieldName: "O_OCD"},
	    	{fieldName: "O_GCD"},
	    	{fieldName: "F010GCD"},
	    	{fieldName: "O_ACD"},
	    	{fieldName: "O_GCDN"},
	    	{fieldName: "O_GCDNIMG"},
	    	{fieldName: "O_BDT" ,dataType: "datetime", datetimeFormat: "yyyyMMdd"},
	    	{fieldName: "O_NU1"},
	    	{fieldName: "O_CHR"},
	    	{fieldName: "O_C03"},
	    	{fieldName: "O_C04"},
	    	{fieldName: "O_C05"},
	    	{fieldName: "O_C07"},
	    	{fieldName: "O_CKI"},
	    	{fieldName: "O_CKP"},
	    	{fieldName: "O_REF"},
	    	{fieldName: "O_REF2"},
	    	{fieldName: "O_DPT"},
	    	{fieldName: "O_PRF"},
	    	{fieldName: "O_SDB"},
	    	{fieldName: "O_BM"},
	    	{fieldName: "O_TNM"},
	    	{fieldName: "O_RSTB"},
	    	{fieldName: "O_REMK"},
	    	{fieldName: "O_LMBN"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
			{name: "O_OCD", fieldName: "O_OCD", width: "80", styles: {textAlignment: "center"},		renderer:{showTooltip: true}, header: {text: "보험코드"}}
			, {name: "O_GCDN", fieldName: "O_GCDN",	width: "90",	renderer:{showTooltip: true}, header: {text: "검사항목"}}
			, {name: "O_TNM", fieldName: "O_TNM",	width: "90",	renderer:{showTooltip: true}, header: {text: "검체종류"}}
			, {name: "O_CHR", fieldName: "O_CHR",	width: "70", styles: {textAlignment: "center", font : "맑은 고딕, 12, Bold"},		renderer:{showTooltip: true}, header: {text: "결과"}}
			, {name: "O_REF", fieldName: "O_REF2",	width: "130", styles: {textAlignment: "near", textWrap: "explicit"}, renderer:{showTooltip: true}, header: {text: "참고치"}, editor : {type: "multiline"}}
			, {name: "O_C07", fieldName: "O_C07", 	width: "40", styles: {textAlignment: "center", font : "맑은 고딕, 12, Bold"},		renderer:{showTooltip: true}, header: {text: "H/L"}} //H/L/BLANK //PANIC H/L/BLANK //DETAIL Y/BLANK
			, {name: "O_BDT", fieldName: "O_BDT",	width:"100",	header:{text: "보고일자"},	styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment: "center"}}
		];
	    gridView.setColumns(columns);
	    
	    // 툴팁 기능
	    gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        return tooltip;
	    }

	    
	    gridView.setColumnProperty("O_C07","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "O_C07");
	    	   if (index.column === "O_C07") {
	    	    switch (col1Value) {
	    	      case 'H' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
	    	        ret.foreground = "#ffff0000";
	    	        break;
	    	      case 'L' :
	    	        //ret.editable = false;
	    	        //ret.readOnly = true;
	    	        ret.foreground = "#ff0000ff";
	    	   }
	    	   return ret;
	    	  }
	    	});


	    gridView.setColumnProperty("O_CHR","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "O_C07");
	    	   //var col2Value = grid.getValue(index.itemIndex, "O_CHR");
	    	   var col2Value = grid.getValue(index.itemIndex, "O_FLG");
	    	   //var col2Value = dataProvider2.getValue(index.itemIndex, "O_FLG");
	    	   if (index.column === "O_CHR") {
	    	    switch (col1Value) {
	    	      case 'H' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
	    	        ret.foreground = "#ffff0000";
	    	        break;
	    	      case 'L' :
	    	        //ret.editable = false;
	    	        //ret.readOnly = true;
	    	        ret.foreground = "#ff0000ff";
	    	    }
	    	    
	    	    switch (col2Value) {
	    	      case 'R' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
	    	        ret.foreground = "#ffff0000";
	    	        //break;
	    	      
	    	   	}
	    	    
	    	    return ret;
	    	  }
	    	});
	    
	    
	    gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "O_REF") {
	            tooltip = grid.getValue(itemIndex, "O_REF").replace(/"\n"/gi,"<br>");
	        }
	        
	        return tooltip;
	        
	        
	    }
	    
	 	dataResult();
	    
	}
	
	function dataResult(){
		var formData = $("#popupForm").serialize();
		var url = "/sysTestReq01List.do";
		ajaxCall(url, formData);
		
	}
	
	function onResult(strUrl, response){
		var resultList;
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		for(var i in resultList){
			var O_REF = resultList[i].O_REF;
			resultList[i].F010GCD = resultList[i].O_GCD;
			resultList[i].O_BDT = resultList[i].O_BDT+"";
			resultList[i].O_C07 = resultList[i].O_C07.substring(0,1);
			//resultList[i].O_C07 = resultList[i].O_C07.substring(0,1);
			resultList[i].O_GCDNIMG = "../images/common/ico_viewer.png";
			if(O_REF.length > 25){
				resultList[i].O_REF2 = O_REF.substring(0,25)+"...";	
			}else {
				resultList[i].O_REF2 = O_REF;
			}
		}

		gridView.closeProgress();
		dataProvider.setRows(resultList);
		gridView.setDataSource(dataProvider);
	}
	//조회
	function search(){
		dataResult();
	}
	
	</script>
	
</head>
<body style="width: 950px; ">
	<form id="popupForm" name="popupForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>"/><%-- 병원코드 --%>
		<input id="I_NAM" name="I_NAM" type="hidden" value="<%=ss_nam%>"/> <%-- 사용자명 병원명 --%>
		<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/><%-- 병원사용자 구분 (E:병원사용자,C:사원) --%>
		<input id="I_DAT" name="I_DAT" type="hidden" ><%-- 접수일자		 --%> 
		<input id="I_JNO" name="I_JNO" type="hidden"><%-- 검사번호	 --%> 
		
		<input id="I_ICNT" name="I_ICNT" value="1000" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
		<input id="I_PECF" name="I_PECF" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
		<input id="I_PNN" name="I_PNN" value="C" type="hidden">
		<!-- <input id="I_PHOS" name="I_PHOS" type="hidden"> -->
		
		<input id="I_RGN1" name=I_RGN1 type="hidden">
		<input id="I_RGN2" name="I_RGN2" type="hidden">
		<input id="I_RGN3" name="I_RGN3" type="hidden">
				
		<input id="I_HAK" name="I_HAK" type="hidden">
		<input id="I_SAB" name="I_SAB" type="hidden">
		<input id="I_GCD" name="I_GCD" type="hidden">
		<input id="I_ACD" name="I_ACD" type="hidden">
		
		
		
	</form>         
	<div class="tbl_type">
		<div id="realgrid" style="width: 100%; height: 400px;"></div>
	</div>	
	
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		</div> 
	</div>
</body>
</html>
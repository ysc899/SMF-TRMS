<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>항목별 통계 상세</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">

    var pscd = "";
	var resultCode = "";
	var I_FNM;
	var gridView,dataProvider ;
	
	$(document).ready( function(){
		//$("#I_FNM").val(I_FNM);
		//setGrid();
		//callResize2();
	});
	
	/* 팝업 호출 input 설정 */
  	function gridViewRead( gridValus){
		var formData = $("#popupForm").serializeArray();
	    $.map(formData, function(n, i){
	    	if(!isNull(gridValus[n['name']])){
				$("#"+n['name']).val(gridValus[n['name']]);
	    	}
	    });		
	    
	    var formData = $("#popupForm").serializeArray();
		$("#I_GCD").val(gridValus["F600GCD"]);
		$("#I_ACD").val(gridValus["F600ACD"]);
		$("#I_FDT").val(gridValus["I_YEAR"]+gridValus["I_MONTH"]+"01");
		$("#I_TDT").val(gridValus["I_YEAR"]+gridValus["I_MONTH"]+"31");
		$("#I_NAM").val(gridValus["F100NAM"]);
		$("#I_CHN").val(gridValus["F100CHN"]);

	    
		setGrid();
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
	    	{fieldName:"OCD"}	//보험코드	
	    	,{fieldName:"DAT",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	//접수일자	
	    	,{fieldName:"JNO"}	//접수번호
	    	,{fieldName:"NAM"}	//수진자	
	    	,{fieldName:"CHN"}	//챠트번호	
	    	,{fieldName:"GCD"}	//검사코드	
	    	,{fieldName:"ACD"}	//부속코드	
	    	,{fieldName:"GNM"}	//검사명	
	    	,{fieldName:"RLT"}	//결과	
	    	,{fieldName:"REF"}	//참고치	
	    	,{fieldName:"BDT",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	//보고일자	
	    	,{fieldName:"STS"}	//상태
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	    	{name:"OCD",	fieldName:"OCD",	header:{text:"보험코드"},		width:100}
	    	,{name:"GNM",	fieldName:"GNM",	header:{text:"Test Item"},	width:200}
	    	,{name:"RLT",	fieldName:"RLT",	header:{text:"Result"},		width:120}
	    	,{name:"BDT",	fieldName:"BDT",	header:{text:"Date"},		width:80,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
	    	,{name:"STS",	fieldName:"STS",	header:{text:"Sts"},		width:80}
	    	,{name:"REF",	fieldName:"REF",	header:{text:"참고치"},		width:100}
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
	    
	 	dataResult();
	    
	}
	
	function dataResult(){
		strUrl = "/statsPatItem01List.do";
		var formData = $("#popupForm").serialize();
		//console.log(formData);
		ajaxCall(strUrl, formData);
	}
	
	function onResult(strUrl, response){
		var resultList;
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
        $.each(resultList,function(k,v){
    		resultList[k].BDT = (v.BDT+"").trim();
    		resultList[k].ACD = (v.ACD+"").trim();
    		resultList[k].GNM = (v.GNM+"").trim();
    		resultList[k].OCD = (v.OCD+"").trim();
        });
		
		dataProvider.setRows(resultList);		
		gridView.setDataSource(dataProvider);
	}
	//조회
	function search(){
		dataResult();
	}
	
	</script>
	
</head>
<body style="width: 820px; ">
	<form id="popupForm" name="popupForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_MONTH" name="I_MONTH" type="hidden"/><%-- 접수일자from --%> 
			<input id="I_FDT" name="I_FDT" type="hidden"/><%-- 접수일자from --%> 
			<input id="I_TDT" name="I_TDT" type="hidden"/><%-- 접수일자to --%> 
			<input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>"/><%-- 병원코드 --%>
			<input id="I_NAM" name="I_NAM" type="hidden" value="<%=ss_nam%>"/> <%-- 사용자명 병원명 --%>
			<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/><%-- 병원사용자 구분 (E:병원사용자,C:사원) --%>
			<input id="I_CHN" name="I_CHN" type="hidden" ><%-- 챠트번호		 --%> 
			<input id="I_GCD" name="I_GCD" type="hidden"><%-- I_GCD	--검사코드		 --%> 
			<input id="I_ACD" name="I_ACD" type="hidden"><%-- I_ACD	--부속코드		 --%> 
			<%--페이징 관련 --%>
			<input id="I_PNN" name="I_PNN" value="C" type="hidden"> <%-- 이전／다음(C/N/P) --%>
			<input id="I_CNT" name="I_CNT" value="200" type="hidden"><%-- 읽을건수		 --%> 
			<input id="I_ICNT" name="I_ICNT" value="200" type="hidden"><%-- 읽을건수		 --%> 
			<input id="I_DAT" name="I_DAT" type="hidden" value="0"><%-- 접수일자(페이징 이전 차트 : 첫번째 접수일자, 페이징 다음 차트 : 마지막 접수일자)--%> 				
			<input id="I_JNO" name="I_JNO" type="hidden" value="0"><%-- 접수번호(페이징 이전 차트 : 첫번째 접수번호, 페이징 다음 차트 : 마지막 접수번호)--%> 
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
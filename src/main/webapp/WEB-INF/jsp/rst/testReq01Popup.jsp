<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>추가의뢰</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	var I_LOGMNU = "TESTREQ";
	var I_LOGMNM = "추가의뢰";
	var dataProvider3, dataProvider4, gridView3,gridView4;
	$(document).ready( function(){
		
		dataProvider3 = new RealGridJS.LocalDataProvider();
		gridView3 = new RealGridJS.GridView("realgrid3");
	    setStyles(gridView3);
	    gridView3.setHeader({
	    	height:40
	    })
	    gridView3.setDataSource(dataProvider3);
	    gridView3.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields3 = [
	    	{fieldName: "R001COR"},
	    	{fieldName: "R001DAT"},
	    	{fieldName: "R001DAT2"},
	    	{fieldName: "R001JNO"},
	    	{fieldName: "R001HOS"},
	    	{fieldName: "R001HNM"},
	    	{fieldName: "R001CHN"},
	    	{fieldName: "R001NAM"},
	    	{fieldName: "R001JMN"},
	    	{fieldName: "R001AGE"},
	    	{fieldName: "R001SEX"},
	    	{fieldName: "R001DTC"},
	    	{fieldName: "R001TRM"},
	    	{fieldName: "R001WRD"},
	    	{fieldName: "R001CLT"},
	    	{fieldName: "R001CLT2"},
	    	{fieldName: "R001GDT"},
	    	{fieldName: "R001GDT2"},
	    	{fieldName: "R001GKD"},
	    	{fieldName: "R001OPN"},
	    	{fieldName: "R001RMK"},
	    	{fieldName: "R001STS"},
	    	{fieldName: "R001STSNM"},
	    	{fieldName: "R001CUR"},
	    	{fieldName: "R001CDT"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider3.setFields(fields3);
	  //이미지 버튼 생성
		var columns3 = [
	        {name: "R001DAT", fieldName: "R001DAT2", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "접수\n일자"}}
	      ,	{name: "R001JNO", fieldName: "R001JNO", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "접수\n번호"}}
	      ,	{name: "R001HNM", fieldName: "R001HNM", width: "120", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "의료기관명"}}
	      ,	{name: "R001NAM", fieldName: "R001NAM", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "수진자\n명"}}
	      ,	{name: "R001CHN", fieldName: "R001CHN", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "차트\n번호"}}
	      ,	{name: "R001SEX", fieldName: "R001SEX", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "성별\n/나이"}}
	      ,	{name: "R001CLT", fieldName: "R001CLT2", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검체\n채취일"}}
	      ,	{name: "R001GDT", fieldName: "R001GDT2", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검체\n의뢰일"}}
	      ,	{name: "R001STSNM", fieldName: "R001STSNM", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "상태"}}
	      ];
	      
		gridView3.setColumns(columns3);
	  /*
	    gridView3.onDataCellClicked = function (grid, index) {
	    	//alert(JSON.stringify(index));
	    	
	 		if(grid.getValue(index.itemIndex, "PRF") == "Y" && index.column == "PRF"){
	 			parent.goPage('RSTPRE', '이전결과', 'rstPre.do');
	 			//parent.goPage('STATSTIME', '시계열', 'statsTime.do', '&menutest=확인&adf=');
	 		} else if(grid.getValue(index.itemIndex, "PRF") == "Y" && index.column == "PRF2"){
	 			parent.goPage('STATSTIME', '시계열', 'statsTime.do');
	 		} else {
	 			dataResult2(grid.getValues(index.itemIndex));	
	 		}
	 		//pmcd = tree.getValue(index.itemIndex, 1);
		};
		*/
		gridView3.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    };
		
		gridView3.onDataCellClicked = function (grid, index) {
			dataResult4(grid.getValues(index.itemIndex));
		};
		
		var checkBar3 = gridView3.getCheckBar();
		checkBar3.visible = false;
	    gridView3.setCheckBar(checkBar3);
	    
	    var stateBar3 = gridView3.getStateBar();
	    stateBar3.visible = false;
	    gridView3.setStateBar(stateBar3);
	    
		
		dataProvider4 = new RealGridJS.LocalDataProvider();
		gridView4 = new RealGridJS.GridView("realgrid4");
	    setStyles(gridView4);
	    gridView4.setHeader({
	    	height:40
	    })
	    gridView4.setDataSource(dataProvider4);
	    gridView4.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields4 = [
	    	{fieldName: "R001COR"},
	    	{fieldName: "R001DAT"},
	    	{fieldName: "R001JNO"},
	    	{fieldName: "R001GCD"},
	    	{fieldName: "R001TCD"},
	    	{fieldName: "R001REJ"},
	    	{fieldName: "R001REJNM"},
	    	{fieldName: "R001STS"},
	    	{fieldName: "R001STSNM"},
	    	{fieldName: "F010FKN"},
	    	{fieldName: "F010TCD"},
	    	{fieldName: "F010TNM"},
	    	{fieldName: "F018OCD"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider4.setFields(fields4);
	  //이미지 버튼 생성
		var columns4 = [
	        {name: "F018OCD", fieldName: "F018OCD", width: "240", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "보험\n코드"}}
	      ,	{name: "R001GCD", fieldName: "R001GCD", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검사\n코드"}}
	      ,	{name: "F010FKN", fieldName: "F010FKN", width: "180", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검사\n항목"}}
	      ,	{name: "F010TNM", fieldName: "F010TNM", width: "100", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검체\n종류"}}
	      ,	{name: "R001REJNM", fieldName: "R001REJNM", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "거부\n사유"}}
	      ,	{name: "R001STSNM", fieldName: "R001STSNM", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "상태"}}
	      ]
	      
		gridView4.setColumns(columns4);
	  
		gridView4.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
		
		var checkBar4 = gridView4.getCheckBar();
		checkBar4.visible = false;
	    gridView4.setCheckBar(checkBar4);
	    
	    var stateBar4 = gridView4.getStateBar();
	    stateBar4.visible = false;
	    gridView4.setStateBar(stateBar4);
	    
	});
	
	function testReq01Call(formData){
		//console.log("formData.I_DTLJNO = " + formData.I_DTLJNO);
		//console.log("formData.I_DTLDAT = " + formData.I_DTLDAT);
		//console.log("formData = " + formData);
		ajaxCall("/testReq01List.do", formData);	
	}
	
	function testReq02Call(formData){
		//console.log("formData.I_DTLJNO = " + formData.I_DTLJNO);
		//console.log("formData.I_DTLDAT = " + formData.I_DTLDAT);
		//console.log("formData = " + formData);
		ajaxCall("/testReq01List2.do", formData);	
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/testReq01List.do" || strUrl == "/testReq01List2.do"){
		
		var testReq01List =  response.testReq01List;
		var addReqHeader = "";
		
		for (var i in testReq01List){
			testReq01List[i].R001SEX = testReq01List[i].R001SEX + "/" + testReq01List[i].R001AGE;  
			
			var dat = testReq01List[i].R001DAT+"";
			var clt = testReq01List[i].R001CLT+"";
			var gdt = testReq01List[i].R001GDT+"";
			
			if(dat != 0){
				testReq01List[i].R001DAT2 = dat.substring(0,4)+"-"+dat.substring(4,6)+"-"+dat.substring(6,8);	
			}else {
				testReq01List[i].R001DAT2 = dat;
			}
			
			if(clt != 0){
				testReq01List[i].R001CLT2 = clt.substring(0,4)+"-"+clt.substring(4,6)+"-"+clt.substring(6,8);
			}else{
				testReq01List[i].R001CLT2 = clt;
			}
			
			if(clt != 0){
				testReq01List[i].R001GDT2 = gdt.substring(0,4)+"-"+gdt.substring(4,6)+"-"+gdt.substring(6,8);	
			}else{
				testReq01List[i].R001GDT2 = gdt;
			}
		}
		
		gridView3.closeProgress();
		dataProvider3.clearRows();
		dataProvider3.setRows(testReq01List);
		gridView3.setDataSource(dataProvider3);
		
		if (dataProvider3.getRowCount() > 0){
			dataResult4(gridView3.getValues(0));	
		} else {
			dataProvider4.clearRows();
			gridView4.setDataSource(dataProvider4);

			}
		}
		
		
		if(strUrl == "/testReq02List.do"){
			var resultList =  response.resultList;
			
			//for (var i in resultList){
			//	resultList[i].R001SEX = resultList[i].R001SEX + "/" + resultList[i].R001AGE;  
			//}
			gridView4.closeProgress();
			dataProvider4.clearRows();
			dataProvider4.setRows(resultList);
			gridView4.setDataSource(dataProvider4);
			
			callResize();
		}
	}
	
	function dataResult4(data){
		$('#I_DTLDAT').val(data.R001DAT);
		$('#I_DTLJNO').val(data.R001JNO);
		$('#I_DTLHOS').val(data.R001HOS);
		
		var formData = $("#popupForm").serialize();
		
		ajaxCall("/testReq02List.do", formData);
	}
	
	function addReq01List(){
		closeModal("openPopup", 5);
	}
	
	function userData1(userData){
		$('#I_DTLDAT').val(userData.I_DAT);
		$('#I_DTLJNO').val(userData.I_JNO);
		$('#I_DTLHOS').val(userData.I_HOS);
		$('#I_RGN1').val(userData.I_RGN1);
		$('#I_RGN2').val(userData.I_RGN2);
		$('#I_RGN3').val(userData.I_RGN3);
		$('#I_HAK').val(userData.I_HAK);
		$('#I_SAB').val(userData.I_SAB);
	}
	
    </script>
    
</head>
<body style="width : 1200px;">
	<form id="popupForm" name="popupForm">
	<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
	<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		
	<input id="I_DTLDAT" name="I_DTLDAT" type="hidden">
	<input id="I_DTLJNO" name="I_DTLJNO" type="hidden">
	<input id="I_DTLHOS" name="I_DTLHOS" type="hidden">
	<input id="I_RGN1" name="I_RGN1" type="hidden">
	<input id="I_RGN2" name="I_RGN2" type="hidden">
	<input id="I_RGN3" name="I_RGN3" type="hidden">
	<input id="I_HAK" name="I_HAK" type="hidden">
	<input id="I_SAB" name="I_SAB" type="hidden">
		<div class="container-fluid">                
	        <div class="con_wrap col-md-7 col-sm-12">
	            <div class="modal_title_area">
	                <h5 class="tit_h5">의뢰 접수 목록</h5>
	                <div class="tit_btn_area modal_btn_area">
	                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:addReq01List()">추가</button>
	                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:closeModal()">닫기</button>
	                </div>
	            </div>
	            <div class="tbl_type" style="overflow:auto">
				<div id="realgrid3"  class="realgridH"></div>
	                 </div> 
	            </div>
	             
	            <div class="con_wrap col-md-5 col-sm-12">
	             	<div class="modal_title_area">
	                     <h5 class="tit_h5">추가 검사 항목</h5>
	                </div>
	                <div class="tbl_type" style="overflow:auto">
	                <div id="realgrid4"  class="realgridH"></div>
	            </div>
	        </div>                    
	    </div>
	 </form>
</body>
</html>
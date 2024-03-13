<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>도움말 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>		
	
	<script>
		
	var dataProvider,gridView;
	var pmcd = "";
	var chk = "N";
	$(document).ready( function(){
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    setStyles(gridView);
	    gridView.setDataSource(dataProvider);
	    gridView.setStyles(basicGrayBlueSkin);
	    
	    gridView.setOptions({
	    	panel : {
				visible : false		
			},footer: {
                visible: false
            }
	    })
	    
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })
	    
	    gridView.setStateBar({
				  visible: false  
		});

	    var fields = [
	    	{fieldName: "S013MCD"},
	    	{fieldName: "S001PMCD"},
	    	{fieldName: "S001MNM"},
	        {fieldName: "S001PMNM"},
	        {fieldName: "S001MMNM"},
	        {fieldName: "S013CON"},
	        {fieldName: "S013CUR"},
	        {fieldName: "S013CDT"},
	        {fieldName: "S013CDT2"},
	        {fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	        
	        
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	    	
	        {name: "S013MCD", fieldName: "S001MMNM", width: "300",  styles: {textAlignment: "near"},    header: {text: "메뉴"}}
	      ,	{name: "S013CUR", fieldName: "S013CUR", width: "100", styles: {textAlignment: "center"},   header: {text: "등록자"}}
	      , {name: "S013CDT", fieldName: "S013CDT2", width: "100", styles: {textAlignment: "center"},   header: {text: "등록일"}}
	    ];
	    
	    gridView.setColumns(columns);
		
	    gridView.onDataCellClicked = function (grid, index) {
	    	chk = "Y";
	    	$('#I_MCDP').val(dataProvider.getValue(index.itemIndex, "S013MCD"));
	    	$('#I_CONP').val(dataProvider.getValue(index.itemIndex, "S013CON"));
	    	//console.log(grid.getValue(index.itemIndex, "S013MCD"));
	    	//console.log($('#I_MCDP').val());
	    	
	    	//console.log(dataProvider.getValue(index.itemIndex, "S013CON"));
			//userData.I_CON = $('#I_CON').val();
	    	openPopup(2);
	    	//alert(JSON.stringify(index));
	    	/*
	 		if(index.column == "S013FNM"){
	 			var path = grid.getValue(index.itemIndex, "S013FPT");
	 			var fileNm  = grid.getValue(index.itemIndex, "S013FNM");
	 			FileDown(path,fileNm);
	 			
	 		}
	    	*/
	 		//pmcd = tree.getValue(index.itemIndex, 1);
		};
		
		//dataResult1();
	    
	    //var column = gridView.columnByName("S013CUR");
	    //gridView.setColumnProperty(column, "visible", false);
	    

		
	});
	
	function FileDown(path,fileNm){
		$.when(function () {
	        var def = jQuery.Deferred();
	        window.setTimeout(function () {
	        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+path+"&file_name="+encodeURIComponent(fileNm);
	            def.resolve();
	        }, 100);
	        return def.promise();
	    }())
	    .done(function () {
	    });
	}
	
	function dataResult1(){
		var formData = $("#sysHelpForm").serialize();
		ajaxCall("/sysHelpList.do",formData);
	}
	
	//조회
	function search(){
		dataResult1();
	}
	
	//팝업 호출
	function save()	{
		//fnOpenPopup("/sysHelp01.do","도움말", formData,callFunPopup);   
		//fnOpenPopup("/testReq01Popup.do","추가의뢰",formData,callFunPopup);
	}
	
	//삭제(DYN값 'N'->'Y')
	function deleteRow(){
		var dataRows = gridView.getCheckedRows();
		
		if(dataRows.length == 0){
			CallMessage("243","alert");	//선택 내역이 없습니다.
			return;
		}
		
		CallMessage("242","confirm", "", deleteStart);//정말 삭제하시겠습니까?
	}
	
	function deleteStart(){
		var dataRows = gridView.getCheckedRows();
		var strUrl = "/sysHelpDelete.do";
		
		rtnCall = ajaxListCall(strUrl,dataProvider, dataRows,ListCallEnd, false);//삭제call
	}
	
	function ListCallEnd(rtnCall){
		if(!rtnCall){
   			CallMessage("244","alert",["삭제"]);		//{0}에 실패하였습니다.
   		}else{
   			CallMessage("223","alert");	//삭제를 완료하였습니다.
   			dataResult1();
   		}		
	}
	
	function onResult(strUrl,response){
		if(strUrl == "/sysHelpList.do"){
			var resultList = response.resultList;
			
			gridView.setAllCheck(false);
			for (var i in resultList){
				if(resultList[i].S013MCD != ""){
					resultList[i].S001MMNM = resultList[i].S001PMNM + " > " + resultList[i].S001MNM;
				}
				
				if(resultList[i].S013CDT != 0){
					var cdt = resultList[i].S013CDT+"";
					resultList[i].S013CDT2 = cdt.substring(0,4)+"-"+cdt.substring(4,6)+"-"+cdt.substring(6,8);
				}
			}
			
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
		if(strUrl == "/sysHelpDelete.do"){
			search();
		}
		
		
	}
	
	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// Enter Key 조회
	    	if(e.target.nodeName == "INPUT" ){
	    		dataResult1();
	        }
	    }
	}
	
	/*close 호출*/
	function fnCloseModal(obj){
	    dataResult1();
	}
	
	/* 팝업 호출 및 종료 */
	function openPopup(str){
		var formData = $("#searchForm").serialize();
		
		if(str == "1"){
			fnOpenPopup("/sysHelp01.do","도움말 등록",formData,callFunPopup);	
		} else if(str == "2"){
			fnOpenPopup("/sysHelp02.do","도움말 상세보기",formData,callFunPopup);
		} else if(str == "3"){
			fnOpenPopup("/sysHelp01_1.do","도움말 수정",formData,callFunPopup);
		}	
	}
	
	/*callback 호출 */
	function callFunPopup(url,iframe,formData){
		if(url == "/sysHelp01.do"){
			iframe.dataResult();
		}
		
		if(url == "/sysHelp02.do"){
			var userData = [];
			userData.I_MCD = $('#I_MCDP').val();
			userData.I_CON = $('#I_CONP').val();
			iframe.userData(userData);
			iframe.dataResult(formData);
		}
		if(url == "/sysHelp01_1.do"){
			var userData = [];
			userData.I_MCD = $('#I_MCDP').val();
			userData.I_CON = $('#I_CONP').val();
			iframe.userData(userData);
			iframe.dataResult();	
		}
	}
	
	function chkReset(str){
		chk = str;
	}
	</script>
</head>
<body>
	<!-- content_inner -->
	<div class="content_inner">
		<form id="sysHelpForm" name="sysHelpForm">
		<input id="I_SEQU" name="I_SEQU" type="hidden"/>
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_AGR" name="I_AGR" type="hidden"  value="<%=ss_agr%>"/>	<!-- 권한 그룹 -->
		<input id="I_MCDP" name="I_MCDP" type="hidden"/>
		<input id="I_CONP" name="I_CONP" type="hidden"/>
			<!-- 검색영역 -->
			<div class="container-fluid">
				<div class="con_wrap col-md-12 srch_wrap">
					<div class="title_area open">
		                <h3 class="tit_h3">검색영역</h3>
				    </div>
		            <div class="con_section row">
		                <p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
		                 <div class="srch_box">
		                    <div class="srch_box_inner">
		                        <div class="srch_item" >
		                            <label class="label_basic">메뉴코드</label>
		                            <input type="text" class="srch_put engNumOnly" id="I_MCD" name="I_MCD"  maxlength="20" onkeydown="return enterSearch(event)">
		                        </div>
		                        <div class="srch_item">
		                        	<label class="label_basic">메뉴 명</label>
		                            <input type="text" class="srch_put" id="I_MNM" name="I_MNM"  maxlength="49" onkeydown="return enterSearch(event)">
		                        </div>     
		                    </div>
		                    <div class="btn_srch_box">
		                        <button type="button" class="btn_srch" onClick="javascript:search();">조회</button>
		                    </div> 
		                </div> 
		            </div>
				</div>
			</div>
			<!-- 검색영역 end -->
			
			<div class="container-fluid">
				<div class="con_wrap col-md-12  col-sm-12">
					<div class="title_area">
		                <h3 class="tit_h3">도움말 파일</h3>
		                <div class="tit_btn_area">
		                	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#help-Modal"  onClick="javascript:openPopup(1);">등록</button>
		                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow();">삭제</button>
		                </div>
		            </div>	
		            <div class="con_section overflow-scr">
	                    <div class="tbl_type">
	                    	<div id="realgrid"  class="realgridH" ></div>
	                    </div>
	                </div>
				</div>	
			</div>
			<%-- 첨부파일 다운로드 --%> 
			<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
			<%-- 첨부파일 다운로드 --%>
		</form>
	</div>
</body>
</html>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원 검색 팝업</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	
	var resultCode = "";
	var I_FNM;
	var dataProvider, gridView;

	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		dataResult();
	        }
	    }
	} 
	
	$(document).ready( function(){
		//$("#I_FNM").val(I_FNM);
		//setGrid();
	});
	
	/* 팝업 호출 input 설정 */
  	function gridViewRead(gridValus){
		//console.log(gridValus);
		//$('#I_HOS').val(gridValus["I_HOS"]);		// 병원 코드
		//$('#I_FNM').val(gridValus["I_FNM"]);		// 수진자 명
		//$('#I_FNM').val(gridValus);
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
	    	  {"fieldName": "F120COR"}		// COR
	    	, {"fieldName": "F120PCD"}		// 병원 코드
	    	, {"fieldName": "F120FNM"}		// 병원 명
	    	, {"fieldName": "F120WNO"}		// 대표자명
			, {"fieldName": "F120TEL"}		// 전화번호
			, {"fieldName": "F120SML"}		// 이메일
			, {"fieldName": "F120MBC"}		// 담당자 사번
			, {"fieldName": "F910NAM"}		// 사원 이름
	    	, {"fieldName": "I_LOGMNU"}
	    	, {"fieldName": "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	        {"name": "F120PCD", "fieldName": "F120PCD", "editable": false, "width": "80", "styles": {"textAlignment": "center"}, "header": {"text": "HospitalCode"}}
	      ,	{"name": "F120FNM", "fieldName": "F120FNM", "editable": false, "width": "150", "styles": {"textAlignment": "near"}, "header": {"text": "HospitalName"}}
	      ,	{"name": "F910NAM", "fieldName": "F910NAM", "editable": false, "width": "80", "styles": {"textAlignment": "near"}, "header": {"text": "Manager"}}
	      ,	{"name": "F120TEL", "fieldName": "F120TEL", "editable": false, "width": "100", "styles": {"textAlignment": "near"}, "header": {"text": "Contact"}}
	      ,	{"name": "F120SML", "fieldName": "F120SML", "editable": false, "width": "150", "styles": {"textAlignment": "near"}, "header": {"text": "E-mail"}}
	      //,	{"name": "F120WNO", "fieldName": "F120WNO", "editable": false, "width": "80", "styles": {"textAlignment": "near"}, "header": {"text": "대표자"}}
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
	    
	    gridView.setDisplayOptions({
			fitStyle: "even"
	    });
	    
	    gridView.onDataCellClicked = function (grid, index) {
	    	
	    	var data = [];
	    	data.push(strUrl);
	    	data.push(grid.getValues(index.itemIndex));
	    	
/* 	    	if($('#pageiframe', parent.document).attr("src" ).split("?")[0] == "/hospSearchS.do"){
		    	closeModal("fnCloseModal", data);
	    	}
	    	if($('#pageiframe', parent.document).attr("src" ).split("?")[0] == "/patSearchS.do"){
	    		closeModal2("fnCloseModal2", data);
	    	} */
	    	closeModal("fnCloseModal", data);
	 	};
	 	if(!isNull($("#I_FNM").val()) || !isNull($("#I_HOS").val()) ){
	 		dataResult();
	 	}
	    
	}
	
	function dataResult(){
		strUrl = "/hospSearchSList.do";
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
		callResize();
	}
	//조회
	function search(){
		dataResult();
	}
	
	
	</script>
	
</head>
<body>
<div style="width : 790px;height:520px;">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<div class="srch_box">
			<div class="srch_box_inner">
				<div class="srch_item">
					<label for="" class="label_basic">Hospital Code</label>
					<input type="text" class="engNumOnly" id="I_HOS" name="I_HOS" maxlength="5"  onkeydown="return enterSearch(event)" >
				</div>
				<div class="srch_item">
					<label for="" class="label_basic">Hospital Name</label>
					<input type="text" id="I_FNM" name="I_FNM" maxlength="30"  onkeydown="return enterSearch(event)" >
				</div>               
			</div>
			
			<div class="btn_srch_box">
				<button type="button" class="btn_srch" onClick="javascript:search()">Search</button>
			</div> 
		</div>
	</form>      
	<br>
    <div class="con_section overflow-scr">
        <div class="tbl_type">
			<div id="realgrid" style="height: 400px;" class="realgridH"></div>
		</div>
	</div>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">Close</button>
		 </div>
	</div>

</div>
</body>
</html>
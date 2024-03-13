<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>SMS 연동 관리 - 수진자 정보</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script  type="text/javascript"   src="/plugins/daum/js/daum_addr_search.js " ></script>
	
	<script type="text/javascript">
	
	//var I_LOGMNU = "hospSmsPat02";
	//var I_LOGMNM = "SMS 연동 관리 - 수진자 정보";
	var resultCode = "";
	var gridView;
	var dataProvider;
	var gridValues = [];
	var gridLabels = [];
	
	$(document).ready( function(){
		
		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "GENDER",'Y');
		$('#I_SEX').html(resultCode);
		jcf.replace($("#I_SEX"));
		
	});
	
  	function gridViewRead(gridValus){
	    var param ={I_PSCD:"GENDER", "I_LOGMNU":I_LOGMNU, "I_LOGMNM":I_LOGMNM};
	    $.ajax({
			 url : "/getCommCode.do"
			, dataType: 'json'
			, type : "post"
			, data : param
			, success : function(response){
				var resultList =  response.resultList;
	            $.each(resultList,function(k,v){
	                gridValues.push(v.VALUE);
	                gridLabels.push(v.LABEL);
	            });
	   		 	setGrid();
			}
		});
	}
	function setGrid(){
		
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid1");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })

	    var fields = [
  		 	  {fieldName:"C001HOS"}		//병원 코드
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
			, {fieldName:"C001UUR"}		//수정자 ID
			, {fieldName:"C001UDT"}		//수정 일자
			, {fieldName:"C001UTM"}		//수정 시간
			, {fieldName:"C001UIP"}		//수정자 IP
			, {fieldName:"I_LOGMNU"}	//메뉴코드
			, {fieldName:"I_LOGMNM"}	//메뉴명
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	    	  	  {name:"C001CHN", fieldName:"C001CHN",	header:{text:"차트번호"},	editable: false, width:80}
		    	, {name:"C001NAM", fieldName:"C001NAM",	header:{text:"수진자 명"},	editable: false, width:120 }
		    	, {name:"C001HPN", fieldName:"C001HPN",	header:{text:"핸드폰 번호"}, editable: false, width:150, "displayRegExp":/^([0-9]{3})([0-9]{3,4})([0-9]{4})$/, "displayReplace": "$1-$2-$3" }
		    	, {name:"C001BDT", fieldName:"C001BDT",	header:{text:"생년월일"},	editable: false, width:100, styles: {textAlignment: "center" }, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3" }
		    	, {name:"C001SEX", fieldName:"C001SEX",	header:{text:"성별"},		editable: false, width:120, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "textReadOnly":true}}//,dropDownWhenClick :true
		    	, {name:"C001AGE", fieldName:"C001AGE", header:{text:"나이"}, styles: {textAlignment: "far"},	 editable: false, width:120 }
	      
	    ];
	    gridView.setColumns(columns);

	    //체크바 제거
	    //var checkBar = gridView.getCheckBar();
	    //checkBar.visible = false;
	    //gridView.setCheckBar(checkBar);
	    
	    //상태바 제거
	    var StateBar = gridView.getStateBar();
	    StateBar.visible = false;
	    gridView.setStateBar(StateBar);
	    
	    dataResult1();
		
	}
	
	function dataResult1(){
		strUrl = "/hospPatList.do";
		var formData = $("#searchForm").serialize();
		ajaxCall(strUrl, formData);
	}
	
	function onResult(strUrl,response){
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
	
	function enterSearch(enter){
		if(event.keyCode == 13){
			if(enter == "0"){
				search();
			}
		}
	}
	
	</script>
	
</head>
<body>
	<div style="width: 800px; height: 600px;">
		<form id="searchForm" name="searchForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_HOS" name="I_HOS" type="hidden"  value="<%=ss_hos%>"/>
			<div class="srch_box">
				<div class="srch_box_inner">
					<div class="srch_item">
						<label for="" class="label_basic">차트번호</label>
						<input  type="text" class="srch_put engNum" id="I_CHN" name="I_CHN" maxlength="15" onkeydown="javasscript:enterSearch(0)">
					</div>
					<div class="srch_item">
						<label for="" class="label_basic">수진자 명</label>
						<input type="text" id="I_NAM" name="I_NAM" maxlength="40" onkeydown="javasscript:enterSearch(0)">
					</div>               
				</div>
				<div class="srch_box_inner m-t-10">
					<div class="srch_item">
						<label for="" class="label_basic">성별</label>
	                    <div class="select_area">
							<select id="I_SEX"  name="I_SEX"></select> 
						</div>
					</div>               
				</div>
				
				<div class="btn_srch_box">
					<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
				</div> 
			</div>
		</form>      
	    <div class="tit_btn_area modal_btn_area">
	        <button type="button" class="btn btn-darkBlue btn-sm" onClick="javascript:insertRow()">추가</button>
	    </div>
	    <div class="con_section overflow-scr" >
	        <div class="tbl_type">
				<div id="realgrid1" style="height: 400px;" class="realgridH"></div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="min_btn_area">
				  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
			 </div>
		</div>
	</div>

</body>
</html>
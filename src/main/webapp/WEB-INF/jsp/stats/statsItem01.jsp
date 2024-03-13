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

    var dataProvider, gridView;
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
	    $.map(formData, function(n, i){
	    	if(!isNull(gridValus[n['name']])){
				$("#"+n['name']).val(gridValus[n['name']]);
	    	}
	    });		
		$('#I_PNN_P').addClass("disabled");
		$('#I_PNN_N').addClass("disabled");
		$('#I_PICNT').val(Number($('#I_PCNT').val())+1);
		$('#I_PNN').val('C');
		count = 0;
		//console.log(gridValus);
	    var formData = $("#popupForm").serializeArray();
		$("#I_GCD").val(gridValus["F110GCD"]);
		$("#I_HOS").val(gridValus["I_HOS"]);
		$("#I_FDT").val(gridValus["I_YEAR"]+gridValus["I_MONTH"]+"01");
		$("#I_TDT").val(gridValus["I_YEAR"]+gridValus["I_MONTH"]+"31");
		//$("#I_NAM").val(gridValus["F100NAM"]);
		//$("#I_CHN").val(gridValus["F100CHN"]);
	
	    
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
	    	 {fieldName:"DATS",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	//접수일자
	    	,{fieldName:"DAT"}	//접수일자
	    	,{fieldName:"JNO"}	//접수번호
	    	,{fieldName:"CHN"}	//챠트번호	
	    	,{fieldName:"NAM"}	//수진자	
	    	,{fieldName:"OCD"}	//보험코드	
	    	,{fieldName:"GCD"}	//검사코드	
	    	,{fieldName:"ACD"}	//부속코드	
	    	,{fieldName:"GNM"}	//검사명	
	    	,{fieldName:"RLT"}	//결과	
	    	,{fieldName:"BDT",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	//보고일자	
	    	,{fieldName:"STS"}	//상태
	    	,{fieldName:"REF"}	//참고치 grid
	    	,{fieldName:"REF1"}	//참고치 tooltip
	    	,{fieldName:"REF2"}	//참고치 tooltip
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	    	 {name:"DATS",	fieldName:"DATS",	header:{text:"접수일자"},		width:100,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
	    	,{name:"JNO",	fieldName:"JNO",	header:{text:"접수번호"},		width:60,  styles: {textAlignment:"center"}}
	    	,{name:"CHN",	fieldName:"CHN",	header:{text:"차트번호"},		width:90}
	    	,{name:"NAM",	fieldName:"NAM",	header:{text:"수진자명"},		width:70, renderer:{ showTooltip: true }}
	    	,{name:"OCD",	fieldName:"OCD",	header:{text:"보험코드"},		width:100, renderer:{ showTooltip: true }}
	    	,{name:"GNM",	fieldName:"GNM",	header:{text:"검사항목"},	width:150, renderer:{ showTooltip: true }}
	    	,{name:"RLT",	fieldName:"RLT",	header:{text:"결과"},		width:100, renderer:{ showTooltip: true }}
	    	,{name:"BDT",	fieldName:"BDT",	header:{text:"보고일자"},		width:80,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
	    	,{name:"STS",	fieldName:"STS",	header:{text:"검사진행"},		width:80,  styles: {textAlignment:"center"}}
	    	,{name:"REF",	fieldName:"REF",	header:{text:"참고치"},		width:160, renderer:{ showTooltip: true }, styles: { textWrap:"explicit"}, editor : {type: "multiline"} }
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
	    
	    // 툴팁 기능
	    gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "REF") {
	        	tooltip = grid.getValue(itemIndex, "REF2");
	        }
	        
	        return tooltip;
	    }
	    
	 	dataResult();
	    
	}
	
	function dataResult(){
		strUrl = "/statsItem01List.do";
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
    		resultList[k].DATS = (v.DAT+"").trim();
    		//var refTool = "";
    		resultList[k].REF2 = v.REF1.replace(/@@/gi,"\n").trim();
    		
    		//resultList[k].ACD = (v.ACD+"").trim();
    		//resultList[k].GNM = (v.GNM+"").trim();
    		//resultList[k].OCD = (v.OCD+"").trim();
        });
		
		dataProvider.setRows(resultList);		
		gridView.setDataSource(dataProvider);
		
		if(dataProvider.getRowCount() < $('#I_PICNT').val()){					// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
			$('#I_PNN_N').addClass("disabled");
		}else{
			if($('#I_PNN').val() != "P"){
				dataProvider.hideRows([(dataProvider.getRowCount()-1)]);						// 보여줄 데이터 마지막 값 숨김
			}
			$('#I_PNN_N').removeClass("disabled");
		}
		
		if($('#I_PNN').val() == "C"){											// 첫페이지일 경우 이전 페이지 버튼 숨김
			$('#I_PNN_P').addClass("disabled");
		}
	}
	//조회
	function search(){
		dataResult();
	}
	
	function pageCNP(type){
		
		if($('#I_PNN_'+type).attr("class") == "disabled"){
			return;
		}
		var page = Number($('#I_PCNT').val());
		
		$('#I_PNN').val(type);
		if(type == "P"){ 			// 이전결과 페이지
			count--;
			$('#I_PDAT').val(dataProvider.getValue(0, "DAT"));
			$('#I_PJNO').val(dataProvider.getValue(0, "JNO"));
			$('#I_PGCD').val(dataProvider.getValue(0, "GCD"));
			$('#I_PACD').val(dataProvider.getValue(0, "ACD"));
			$('#I_PICNT').val(page);
		}
		if(type == "N"){ 			// 다음 결과 페이지
			count++;
			$('#I_PDAT').val(dataProvider.getValue((page-1), "DAT"));
			$('#I_PJNO').val(dataProvider.getValue((page-1), "JNO"));
			$('#I_PGCD').val(dataProvider.getValue((page-1), "GCD"));
			$('#I_PACD').val(dataProvider.getValue((page-1), "ACD"));
			$('#I_PICNT').val(page+1);
		}
		if(count == 0){
			$('#I_PNN_N').removeClass("disabled");
			$('#I_PNN_P').addClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		}
		
		dataResult();
	}
	
	</script>
	
</head>
<body style="width: 950px; ">
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
			<%--페이징 관련 --%>
			<input id="I_PNN" name="I_PNN" value="C" type="hidden"> <%-- 이전／다음(C/N/P) --%>
			<input id="I_PCNT" name="I_PCNT" value="200" type="hidden"><%-- 읽을건수		 --%> 
			<input id="I_PICNT" name="I_PICNT" type="hidden"><%-- 읽을건수		 --%> 
			<input id="I_PDAT" name="I_PDAT" type="hidden" value="0"><%-- 접수일자(페이징 이전 차트 : 첫번째 접수일자, 페이징 다음 차트 : 마지막 접수일자)--%> 				
			<input id="I_PJNO" name="I_PJNO" type="hidden" value="0"><%-- 접수번호(페이징 이전 차트 : 첫번째 접수번호, 페이징 다음 차트 : 마지막 접수번호)--%>
			<input id="I_PGCD" name="I_PGCD" type="hidden" value=""><%-- I_PGCD	--부속코드		 --%>
			<input id="I_PACD" name="I_PACD" type="hidden" value=""><%-- I_PACD	--부속코드		 --%> 
	</form>         
	<div class="tbl_type">
		<div id="realgrid" style="width: 100%; height: 400px;"></div>
		<nav class="pagination_area">
			<ul class="pagination" >
				<li id="I_PNN_P" >
					<a href="#" aria-label="Previous">
					<span aria-hidden="true" onClick="javascript:pageCNP('P')" >이전</span>
				</a>
				</li>
				<li id="I_PNN_N">
					<a href="#" aria-label="Next">
					<span aria-hidden="true" onclick="pageCNP('N')">다음</span>
					</a>
				</li>
			</ul>
		</nav>
	</div>	
	
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		</div> 
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>SMS 메세지 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	
	<script type="text/javascript">
	
	var pscd = "";
	var I_EKD = "INIT_PW";
	var gridView, dataProvider, checkBar;
	var gridValues = [];
	var gridLabels = [];
		
	$(document).ready( function(){
/* 	    $('#I_FNM').change(function(){
	    	$('#I_HOS').val('');
	    }); */
	    setGrid();
	    if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }
	    if($('#I_ECF').val() == "C"){
	    	$('#hospRes').show();
	    	$('#hospAdd').show();
	    	$('#hospDel').show();
	    }
/* 		$("#I_HOS").keyup(function(event){
			console.log(11);
			if(event.keyCode != 13){
				$('#I_FNM').val('');
			}
		}); */
	});
	
	function setGrid(){
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
		
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	        editable: true
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
	    	  {name:"C002NAM",		fieldName:"C002NAM",	header:{text:"메시지 명"}, styles: { textWrap:"explicit"}, renderer:{ showTooltip: true },		editable: true,		width:250, editor : { maxLength : 40,type:"multiline" }  }//,dropDownWhenClick :true
	    	, {name:"C002MSG",		fieldName:"C002MSG",	header:{text:"메시지"},		editable: true,		width:500
	    		  , renderer:{ showTooltip: true },editor:{ type:"multiline", maxLength:4000}  , styles: { textWrap:"explicit"} } //ellipse : 
	   	];
	    
	    // 툴팁 기능
	    gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "C002MSG") {
	            tooltip = value;
	        }
	        return tooltip;
	    }	    
	    
	    var checkBar = gridView.getCheckBar();
	    checkBar.visible = true;
	    gridView.setCheckBar(checkBar);
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
		//그리드 너비 보다 컬럼 너비가 작으면 width비율로 자동 증가
	    gridView.setDisplayOptions({
			fitStyle: "even"
	    });

	}
	
	function dataResult(){
		var formData = $("#searchForm").serialize();
		//console.log(formData);
		callLoading(null, "on");
		ajaxCall("/hospSmsList.do", formData, false);
	}
	
	function onResult(strUrl,response){
		callLoading(null, "off");
		var resultList;
		if(!isNull(response.resultList)){
			resultList = response.resultList;
		}
		
		gridView.cancel();
		dataProvider.setRows(resultList);		
		gridView.setDataSource(dataProvider);
		gridView.setAllCheck(false);	
	}
	
 	function insertRow(){
	    var values = [];
		gridView.commit();
		dataProvider.insertRow(0, values);
		gridView.setDataSource(dataProvider)
		gridView.showEditor();
		gridView.setFocus();
		
	}
 	
 	function saveRow(){
 		var createRows,updateRows;
        gridView.commit();
        
		createRows = dataProvider.getStateRows('created');
		updateRows = dataProvider.getStateRows('updated');
		
		if(createRows != ""){
			CallMessage("203","confirm", "", creatStart);
		}else{
			if(updateRows != ""){
				CallMessage("225","confirm", "", updateStart);
			}
		}


 	}
 	
 	function creatStart(){
        var rtnCall = true;
		var createRows = dataProvider.getStateRows('created');
		
		if(createRows != ""){
			if(!saveValidation(createRows, 'created')) { return;}
			callLoading(null, "on");
			rtnCall = ajaxListCall("/hospSmsSave.do",dataProvider,createRows, createEnd, false); // 등록
		}
 	}
 	
 	function createEnd(rtnCall){
		var	updateRows = dataProvider.getStateRows('updated');
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["SMS 메시지 저장"]);		//{0}에 실패하였습니다.
		}else{
			if(updateRows != ""){
				CallMessage("221","alert");
				if(rtnCall){
					dataProvider.setRowState(0, "create", false);
					if(!saveValidation(updateRows, 'updated')) { return;}
					rtnCall = ajaxListCall("/hospSmsUdt.do",dataProvider,updateRows, creAupdEnd, false); // 수정
				}
			}else{
				CallMessage("221","alert","",dataResult);
			}
		}
 	}
 	
 	function creAupdEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["수진자 정보수정"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("282","alert","",dataResult);
		}
 	}
 	
 	function updateStart(){
        var rtnCall = true;
		var updateRows = dataProvider.getStateRows('updated');

		if(updateRows != ""){
 			if(rtnCall){
				if(!saveValidation(updateRows, 'updated')) { return;}
 				callLoading(null, "on");
				rtnCall = ajaxListCall("/hospSmsUdt.do",dataProvider,updateRows, updateEnd, false); // 수정
			} 
		}
 	}
 	
	function updateEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["SMS 메시지 수정"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("222","alert","",dataResult)
		}
	}
	
	function deleteRow() {
		var dataRows;
		dataRows = gridView.getCheckedRows();
		
		if(dataRows == ""){ 
			CallMessage("243");
			return;
		}
		CallMessage("242","confirm", "", deleteStart);
 	}
	
	function deleteStart(){
		var rtnCall =  true;
		var dataRows = gridView.getCheckedRows();
		strUrl = "/hospSmsDel.do";
		callLoading(null, "on");
		rtnCall = ajaxListCall(strUrl,dataProvider,dataRows,deleteEnd, false); // 삭제	
	}
	
	function deleteEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244", "alert", ["삭제"]);
		}else{
			CallMessage("223","alert","",dataResult);
		}
	}
 	
	function exportGrid(){
		var totalCnt = dataProvider.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		CallMessage("257","confirm", "", gridExport);
	}
	
	function gridExport(){
		gridView.exportGrid({
		    type: "excel",
		    target: "local",
		    fileName: I_LOGMNM+".xlsx",
		    showProgress: true,
		    applyDynamicStyles: false,
		    progressMessage: "엑셀 Export중입니다.",
		    indicator: "default",
		    header: "visible",
		    footer: "default",
		    compatibility: "2010",
		    done: function () {  //내보내기 완료 후 실행되는 함수
		    }
		});
	}

	// 조회
	function search(){
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		if(!searchValidation()){return;}
		dataResult();
	}
	
	function searchValidation(){
		if($('#I_HOS').val() == ""){
			CallMessage("245", "alert", ["병원 코드를"],dataClean);
			return false;
		}
		
		return true;
	}
	
	function saveValidation(dataRows){
		
		var data = [];
		var bool;
  	    
		var i = 0;
		do{
			data.push(dataProvider.getJsonRow(dataRows[i]));
			bool = validation(data[i], (dataRows[i]+1));
			if(!bool){
				return bool;
			}else{
				i++;
			}
			
		}while(i < dataRows.length);
		return true;
		
	}
	
	function validation(data, num){
		
		if(isNull(data.C002NAM)){
			CallMessage("245", "alert", [num+"번째 메시지명을"]);
			return false;
		}
		
		if(isNull(data.C002MSG)){
			CallMessage("245", "alert", [num+"번째 메시지를"]);
			return false;
		}
		return true;
	}
	
	function openPopup(){
		var gridValus,labelNm,strUrl;
		strUrl = "/hospSearchS.do";
		labelNm = "병원 조회";
		
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,gridValus){
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
/* 			if(isNull($('#I_FNM').val())){
				gridValus = '';
			}  */
		}
		iframe.gridViewRead(gridValus);
	}
	
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		$('#I_HOS').val(data[1].F120PCD);
		$('#I_FNM').val(data[1].F120FNM);
	}
	
	function enterSearch(enter){
		//console.log(22);
		if(event.keyCode == 13){
			search();
		}
		else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
		} 
	}
	
	function resetFNM(){
		//console.log(33);
		$('#I_FNM').val("");
	}
	
	function dataClean(){
		var data1 = dataProvider.getJsonRows(0, -1);
		dataProvider.removeRows(data1);
	}
	
	</script>
</head>
<body>
	<div class="content_inner">
		<!-- 검색영역 -->
		<form id="searchForm" name="searchForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/>
			<input id="I_AGR" name="I_AGR" type="hidden" value="<%=ss_agr%>"/>
			<div class="container-fluid">
				<div class="con_wrap col-md-12 srch_wrap">
			        <div class="title_area open">
			            <h3 class="tit_h3">검색영역</h3>
			            <a href="#" class="btn_open">검색영역 접기</a>
			        </div>
			        <div class="con_section row">
			             <p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
			             <div class="srch_box">
			                <div class="srch_box_inner">
			                    <div class="srch_item srch_item_v3">
			                        <label for="" class="label_basic">메시지 명</label>
			                        <input type="text" class="srch_put" id="I_NAM" name="I_NAM" maxlength="40" placeholder="" onkeydown="javasscript:enterSearch()">
			                    </div>               
			                </div>
							<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
								<div class="srch_item hidden_srch">
									<label for="" class="label_basic">병원</label>
									<input id="I_HOS" name="I_HOS" type="text" class="srch_put srch_hos numberOnly"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup()" maxlength="5" onkeydown="javasscript:enterSearch()"/>
									<input type="text" class="hos_pop_srch_ico srch_put" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly" onclick="javascript:openPopup()">
								</div>
							</div> 
			                <div class="btn_srch_box">
			                    <button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
			                </div> 
			            </div>
			        </div>
			    </div>
			</div>
		</form>
        <!-- 검색영역 end -->
               
		<div class="container-fluid">
			<div class="con_wrap col-md-12  col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">SMS 메시지 관리</h3>
					<div class="tit_btn_area" >
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:insertRow()"style="display:none" id="hospRes">추가</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()"style="display:none" id="hospAdd">저장</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow()"style="display:none" id="hospDel">삭제</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button>
					</div>
				</div>
				<div class="con_section overflow-scr">
					<div class="tbl_type">
						<div id="realgrid" class="realgridH"></div>
					</div>
				</div>
			</div>
		</div>        
	</div>    
</body>
</html>
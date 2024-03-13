<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>Quick Setup</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<script>
		var pmcd = "";
		var resultCode = "";
	    var gridValues = [];
	    var gridLabels = [];
		var treeView1,treeDataProvider1,gridView2,dataProvider2;
		$(document).ready( function(){
			getGridCode();
			setGrid1();
			$(".btn_srch").click(function(){
				dataResult1();
			});
		});
		function enterSearch(e){	
		    if (e.keyCode == 13) {
		    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
		    	if(e.target.nodeName == "INPUT" ){
		    		dataResult1();
		        }
		    }
		}
		function getGridCode(){
			var formData = $("#searchForm").serializeArray();
			formData +="&I_PSCD=ROOT";
			ajaxCall("/getCommCode.do", formData);
		}
		/*메뉴 그리드 설정*/
		function setGrid1(){
		    treeDataProvider1 = new RealGridJS.LocalTreeDataProvider();
		    treeView1 = new RealGridJS.TreeView("realgrid1");
		    treeView1.setDataSource(treeDataProvider1);
		    setStyles(treeView1);
		    setGridBar(treeView1,false);
		    
		    var fields1 = [
		    	{"fieldName": "$R_RLT"},
		        {"fieldName": "$R_MCD"},
		        {"fieldName": "$R_PMCD"},
		        {"fieldName": "$R_MNM"}
		    ];
		    
		    var columns1 = [
		        {"name": "$R_MCD", "fieldName": "$R_MCD", "width": "200", "styles": {"textAlignment": "near"}, "header": {"text": "메뉴코드"}},
		        {"name": "$R_MNM", "fieldName": "$R_MNM", "width": "200", "styles": {"textAlignment": "near"}, "header": {"text": "메뉴명"}}
		    ];
		    
		    treeDataProvider1.setFields(fields1);
		    treeView1.setColumns(columns1);
		 	treeView1.onDataCellClicked = function (tree, index) {
		 		$("#I_GRDMCD").val(tree.getValue(index.itemIndex, 1));
		 		dataResult2();
			};
// 		    dataResult1();
		}
		/*검색어 그리드 설정*/
		function setGrid2(){
		    dataProvider2 = new RealGridJS.LocalDataProvider();
		    gridView2 = new RealGridJS.GridView("realgrid2");
		    gridView2.setDataSource(dataProvider2);
		    setStyles(gridView2);
		    
		    gridView2.setEditOptions({
		    	insertable: true,
		    	deletable: true,
		    	editable: true,
		    	readOnly: false
		    });
	
			var fields2 = [
				{"fieldName": "S007COR"}
				,{"fieldName": "S007MCD"}
				,{"fieldName": "S007RCD"}
				,{"fieldName": "S007RNM"}
				,{"fieldName": "S007RCV"}
				,{"fieldName": "I_LOGMNU"}
				,{"fieldName": "I_LOGMNM"}
			];
		    dataProvider2.setFields(fields2);
	
		    var columnStyles_Bold = [{
		        criteria: "values['S007COR'] != ''",
		        styles: "fontBold=true"
		    }];
		    var columns2 = [ 
		    	 {name:"S007RCD",   fieldName:"S007RCD",   	header:{text: "검색어코드", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}}, width:100,  editor : {textCase: "upper", maxLength : 20},   renderer:{showTooltip: true, type: "link", url:"#",   requiredFields: "S007RCD", showUrl: false},dynamicStyles: columnStyles_Bold}
				,{name:"S007RNM",   fieldName:"S007RNM",    header:{text:"검색어", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}},		width:200,  editor: {maxLength: 1000} }
		    	,{name:"S007RCV",	fieldName:"S007RCV",	header:{text:"검색값", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}},		width:200,  lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "domainOnly": true}}//,dropDownWhenClick :true

		    ];
		    gridView2.setColumns(columns2);
		    gridView2.setPasteOptions({ fillColumnDefaults: true });

		    gridView2.setColumnProperty("S007RCV", "editButtonVisibility", "always");
		    gridView2.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		        var tooltip = value;
		        return tooltip;
		    }
		    
			/*신규행인 경우에는 모든 컬럼을 편집할수 있도록 변경, 기존행(DB조회등)인 경우 특정컬럼만 편집할수 있도록 변경*/
			gridView2.onCurrentRowChanged = function (grid, oldRow, newRow) {
				var provider = grid.getDataProvider();
				var columns = grid.getColumnNames();
				var editable = newRow < 0 || provider.getRowState(newRow) === "created";
				
				if (!editable) { // 신규행이 아니면. 전체컬럼 editable:false
					columns.forEach(function(obj) {
						grid.setColumnProperty(obj,"editable",false)
					});
	// 				columns = ["S007RCD","S007RNM"];
					columns = ["S007RNM","S007RCV"];
				};
				columns.forEach(function(obj) {
					grid.setColumnProperty(obj,"editable",true)
				});
			};
		    /** 영문만 들어가게 설정 **/
		    gridView2.onEditChange = function(grid, index, value) {
				if (index.column === "S007RCD" ) {
					EditChange(grid, index, value);
			    }
			}
		}
		function EditChange(grid, index, value){
			if (value) {
				var onEditChange = grid.onEditChange;
				grid.onEditChange = null; // 무한 루프로 인한 이벤트 제거
				grid.setEditValue(value.replace(/[/^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,""))
				grid.onEditChange = onEditChange; // 이벤트 재설정
			}
		}
		
		
		function dataResult1(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysMenuList.do", formData);
		}
		
		function dataResult2(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysQcSetDtl1.do", formData);
		}
	
		// ajax 호출 result function
		function onResult(strUrl,response){
			var resultList;
			if(!isNull( response.resultList)){
				resultList = response.resultList;
			}
			if(strUrl == "/getCommCode.do"){
				var resultList =  response.resultList;
	            $.each(resultList,function(k,v){
	                gridValues.push(v.VALUE);
	                gridLabels.push(v.LABEL);
	            });
				setGrid2();
			}
			
			if(strUrl == "/sysQcSetDtl1.do"){
		    	$("#I_GRDRCD").val("");
		    	gridView2.setAllCheck(false);
		    	gridView2.cancel();
				dataProvider2.setRows(resultList);		
				gridView2.setDataSource(dataProvider2);
			}
			if(strUrl == "/sysMenuList.do"){
				treeView1.cancel();
				treeDataProvider1.setRows(resultList, "$R_RLT", false);		
				treeView1.setDataSource(treeDataProvider1);
			    treeView1.expandAll();
	    		$("#I_GRDMCD").val("");
	    		$("#I_GRDRCD").val("");
	    		 dataResult2();
			}
		}
		/** 그리드 호출 **/
		
		//추가
		function insertRow(){
			if( $("#I_GRDMCD").val()==""){
				CallMessage("246","alert",["메뉴"]);// 선택 후 추가해주세요.
				return;
			}
		    var values = [
		    	"" 
		    	, $("#I_GRDMCD").val()
		    	];
		    gridInsertRow(gridView2,dataProvider2, values,"S007RCD" );
			$("#I_GRDRCD").val("");
		}
		function gridInsertRow(insertView,dataProv, values,focusfield){
			insertView.commit();
			dataProv.insertRow(0, values);
			insertView.setDataSource(dataProv);
			
			var focusCell = insertView.getCurrent();
			focusCell.itemIndex = "0";
			focusCell.column =focusfield;
			focusCell.fieldName = focusfield;
			insertView.setCurrent(focusCell);
			insertView.showEditor();
		}
		
		
		
		function saveValidation(dataRows)
		{
			var rtnValid = false;
			var focusCell = gridView2.getCurrent();

			if(dataRows != ""){
				$.each(dataRows, function(i, e) {
		 			if(isNull(dataProvider2.getValue(e, "S007RCD"))){
		 				CallMessage("201","alert",["검색어코드는"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "S007RCD";
		 				focusCell.fieldName = "S007RCD";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(isNull(dataProvider2.getValue(e, "S007RNM"))){
		 				CallMessage("201","alert",["검색어는 "]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "S007RNM";
		 				focusCell.fieldName = "S007RNM";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(isNull(dataProvider2.getValue(e, "S007RCV"))){
		 				CallMessage("201","alert",["검색값은"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "S007RCV";
		 				focusCell.fieldName = "S007RCV";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
				});
			}
			return rtnValid;
		}


		/* 검색어 저장 */
		function saveRow(){
			gridView2.commit();	
			var createRows = dataProvider2.getStateRows('created');
			var	updateRows = dataProvider2.getStateRows('updated');
			
			var valid = saveValidation(createRows);
			if(!valid){
				valid = saveValidation(updateRows);
			}
			if(!valid){

				callLoading("/sysQcSetCheck.do","on");
				if(createRows != ""){ 
		 			ajaxListCall("/sysQcSetCheck.do",dataProvider2,createRows,createCheckEnd,false); // 등록
	// 				var tt = dataPrvd(createRows);
	// 				ajaxListCall("/sysFormCheck.do",dataProvider,createRows,createEnd); // 등록
				}else{
					if(updateRows != ""){ 
						ajaxListCall("/sysQcSetUdt1.do",dataProvider2,updateRows,updateEnd,false); // 수정
					}
				}
			}
		}

		
		/*저장 가능 여부 체크후 저장 call */
		function createCheckEnd(rtnCall,Msg){
			if(!isNull(Msg)){
				callLoading("/sysQcSetCheck.do","off");
				messagePopup("","",Msg);
			}else{
				var createRows = dataProvider2.getStateRows('created');
				ajaxListCall("/sysQcSetSave1.do",dataProvider2,createRows,createEnd,false); // 등록
			}
		}
		
		function createEnd(rtnCall){
			var	updateRows = dataProvider2.getStateRows('updated');
			if(!rtnCall){
				callLoading("/sysQcSetSave1.do","off");
				CallMessage("244","alert",["검색어 저장"]);		//{0}에 실패하였습니다.
			}else{
				if(updateRows != ""){ 
					if(rtnCall){
						rtnCall = ajaxListCall("/sysQcSetUdt1.do",dataProvider2,updateRows,updateEnd,false); // 수정
					}
				}else{
					callLoading("/sysQcSetSave1.do","off");
					CallMessage("278","alert",["검색어 저장을"],dataResult2);	//278={0} 완료하였습니다.
// 					dataResult2();
				}
			}
		}
		function updateEnd(rtnCall){
			callLoading("/sysQcSetUdt1.do","off");
			if(!rtnCall){
				CallMessage("244","alert",["검색어 저장"]);		//{0}에 실패하였습니다.
			}else{
				CallMessage("278","alert",["검색어 저장을"],dataResult2);	//278={0} 완료하였습니다.
// 				dataResult2();
			}
		}
		/* 검색어 저장 */
		
		//삭제
		function deleteRow(){
			var dataRows,strUrl,Provider;
	        //검색어 삭제
			dataRows = gridView2.getCheckedRows();
	        var insertRowChk = InsertGridRowDel(dataRows,gridView2,dataProvider2);
			dataRows = gridView2.getCheckedRows();
			if(dataRows == ""){ 
				if(!insertRowChk){
					CallMessage("243");			//선택내역이 없습니다.
					return;
				}
			}else{
				CallMessage("242","confirm","",fnDel);	//정말 삭제하시겠습니까?
			}
		}
	
		function fnDel(){
			var rtnCall =  true;
			var dataRows,strUrl,Provider;
	        //검색어 삭제
			dataRows = gridView2.getCheckedRows();
			strUrl = "/sysQcSetDel1.do";
			Provider = dataProvider2;
			
			rtnCall = ajaxListCall(strUrl,Provider,dataRows,delEnd); // 삭제
		}
		function delEnd(rtnCall){
			if(!rtnCall){
				CallMessage("244","alert",["검색어 삭제"]);		//{0}에 실패하였습니다.
			}else{
				CallMessage("278", "alert", ["검색어 삭제"], dataResult2);	//278={0} 완료하였습니다.
// 				dataResult2();
			}
		}
		
	</script>
</head>
<body>
	<div class="content_inner">
		<!-- 검색영역 -->
		<div class="container-fluid">
			<div class="con_wrap col-md-12 srch_wrap">
				<div class="title_area open">
					<h3 class="tit_h3">검색영역</h3>
					<a href="#" class="btn_open">검색영역 접기</a>
				</div>
				<div class="con_section row">
					<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
					<form id="searchForm" name="searchForm">
						<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
						<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
						<input id="I_PMCD" name="I_PMCD" type="hidden"/>
						<input id="I_GRDMCD" 	name="I_GRDMCD" 	type="hidden"/>
						<input id="I_GRDRCD" 	name="I_GRDRCD" 	type="hidden"/>
						<div class="srch_box">
							<div class="srch_box_inner">
								<div class="srch_item">
									<label for="" class="label_basic">메뉴코드</label>
									<input type="text" class="srch_put engNumOnly"  id="I_MCD" name="I_MCD" maxlength="20"   onkeydown="return enterSearch(event)" >
								</div>
								<div class="srch_item">
									<label for="" class="label_basic">메뉴명</label>
									<input type="text" class="srch_put"  id="I_MNM" name="I_MNM" maxlength="49" placeholder=""   onkeydown="return enterSearch(event)" >
								</div>               
							</div>
							
							<div class="btn_srch_box">
								<button type="button" class="btn_srch">조회</button>
							</div> 
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 검색영역 end -->
		
		<div class="container-fluid">
             <div class="con_wrap col-md-4 col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">메뉴</h3>
				</div>
				<div class="con_section overflow-scr">
					<div class="tbl_type">
						<div id="realgrid1"   class="realgridH"></div>
					</div>
				</div>
			</div>

            <div class="con_wrap col-md-8 col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">검색어</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:insertRow(2)">추가</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow(2)">저장</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow(2)">삭제</button>
					</div>
				</div>
				<div class="con_section overflow-scr">
					<div class="tbl_type">
						<div id="realgrid2"   class="realgridH"></div>
					</div>
				</div>
			</div>
		</div>
	</div>    
	<!-- content_inner -->       	
	
</body>
</html>



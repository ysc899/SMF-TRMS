<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원별 결과지 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<style type="text/css">
		.label_basic{width: 100px;}
	</style>
	<script>
		var pmcd = "";
		var resultCode = "";
	    var gridValues = [];
	    var gridLabels = [];
		var gridView1,dataProvider1,gridView2,dataProvider2;
		function enterSearch(e){	
		    if (e.keyCode == 13) {
		    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
		    	if(e.target.nodeName == "INPUT" ){
		    		dataResult1();
		        }
		    }
		} 
		$(document).ready( function(){
			var resultCode = getCode(I_LOGMNU, I_LOGMNM, "RPT_CLS");	//양식지
			$('#I_RCL').html(resultCode);
	  		jcf.replace($("#I_RCL"));
			getGridCode();
			setGrid1();
			$(".btn_srch").click(function(){
				dataResult1();
			});
		});
		function getGridCode(){
			var formData = $("#searchForm").serializeArray();
			formData +="&I_PSCD=IMG_FILE_RULE";
			ajaxCall("/getCommCode.do", formData);
		}
		/*메뉴 그리드 설정*/
		function setGrid1(){
		    dataProvider1 = new RealGridJS.LocalDataProvider();
		    gridView1 = new RealGridJS.GridView("realgrid1");
		    gridView1.setDataSource(dataProvider1);
		    setStyles(gridView1);
		    setGridBar(gridView1,false);
		    
		    var fields1 = [
		    	{fieldName: "F120FNM"},
		        {fieldName: "F120PCD"},
		        {fieldName: "F120MBC"},
		        {fieldName: "F910NAM"}
		    ];

		    var columnRenderer = {
		         type: "link"
	    		, url:"#"
	    		, cursor:"pointer"
	    		, showUrl: false
		    };
		    
		    var columns1 = [
		    	{name:"F120PCD",   fieldName:"F120PCD",   	header:{text: "병원코드"}, width:80,	styles: {textAlignment:"center"}, renderer:columnRenderer   }
				,{name:"F120FNM",   fieldName:"F120FNM",   	header:{text: "병원명(사용자)"}, width:200 , renderer:columnRenderer }
				,{name:"F910NAM",   fieldName:"F910NAM",   	header:{text: "담당자"}, width:100 , renderer:columnRenderer }
			];
		    
		    dataProvider1.setFields(fields1);
		    gridView1.setColumns(columns1);
		 	gridView1.onDataCellClicked = function (grid, index) {
		 		$("#I_HOS").val(grid.getValue(index.itemIndex, 1));
		 		dataResult2();
			};
// 		    dataResult1();
		}
		/*파일명 규칙 그리드 설정*/
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
				{fieldName: "S012HOS"}
				,{fieldName: "S012RCL"}
				,{fieldName: "S012NSQ"}
				,{fieldName: "S012NCL"}
				,{fieldName: "S012NDV"}
				,{fieldName: "I_HOS"}
				,{fieldName: "I_RCL"}
				,{fieldName: "I_NSQ"}
				,{fieldName: "I_NCL"}
				,{fieldName: "I_NDV"}
				,{fieldName: "I_LOGMNU"}
				,{fieldName: "I_LOGMNM"}
			];
		    dataProvider2.setFields(fields2);
	
		    var columns2 = [ 
		    	 {name:"I_NSQ",   fieldName:"I_NSQ",   	header:{text:"순번"}, width:100, editor : {maxLength : 3} }
		    	, {name:"I_NCL",   fieldName:"I_NCL",   	header:{text:"파일명 구분"},		width:200,  lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "domainOnly": true}}//,dropDownWhenClick :true
		    	, {name:"I_NDV",   fieldName:"I_NDV",   	header:{text:"직접 입력 값"}, width:200, editor : {maxLength : 9}}

		    ];
		    gridView2.setColumns(columns2);
		    gridView2.setPasteOptions({ fillColumnDefaults: true });

		    gridView2.setColumnProperty("I_NCL", "editButtonVisibility", "always");
		    gridView2.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		        var tooltip = value;
		        return tooltip;
		    }
			gridView2.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
				 grid.setValue(itemIndex, "I_LOGMNU",I_LOGMNU);
				 grid.setValue(itemIndex, "I_LOGMNM",I_LOGMNM);
				 
			    var I_NCL = grid.getValue(itemIndex, "I_NCL");
				 if(!isNull(I_NCL)){
					 if(I_NCL == "DIRECT"){
						grid.setColumnProperty("I_NDV","editable",true);
					 }else{
						grid.setValue(itemIndex, "I_NDV","");
						grid.setColumnProperty("I_NDV","editable",false);
					 }
				 }
			};
			
			/*신규행인 경우에는 모든 컬럼을 편집할수 있도록 변경, 기존행(DB조회등)인 경우 특정컬럼만 편집할수 있도록 변경*/
			gridView2.onCurrentRowChanged = function (grid, oldRow, newRow) {
				var provider = grid.getDataProvider();
				var columns = grid.getColumnNames();
				var editable = newRow < 0 || provider.getRowState(newRow) === "created";
				var I_NCL = grid.getValue(newRow,"I_NCL");
				grid.setColumnProperty("I_NDV","editable",true);

				if (!editable) { // 신규행이 아니면. 전체컬럼 editable:false
					grid.setColumnProperty("I_NSQ","editable",false)
				}else{
					grid.setColumnProperty("I_NSQ","editable",true);
				}
				if(!isNull(I_NCL))
				{
					if( I_NCL=="DIRECT" ){
						grid.setColumnProperty("I_NDV","editable",true);
					}else{
						grid.setColumnProperty("I_NDV","editable",false);
					}
				}
			};
			
		    
		    /** 영문만 들어가게 설정 **/
		    gridView2.onEditChange = function(grid, index, value) {
				if (index.column === "I_NSQ" ) {
					EditChange(grid, index, value);
			    }
			}
		}
		function EditChange(grid, index, value){
			if (value) {
				var onEditChange = grid.onEditChange;
				grid.onEditChange = null; // 무한 루프로 인한 이벤트 제거
		        grid.setEditValue(value.replace(/[^0-9]/g,""))
				grid.onEditChange = onEditChange; // 이벤트 재설정
			}
		}
		
		
		function dataResult1(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysImgMngList.do", formData);
		}
		
		function dataResult2(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysImgMngDtl.do", formData);
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
			
			if(strUrl == "/sysImgMngList.do"){
		    	gridView1.cancel();
		    	$("#I_HOS").val("");
				dataProvider1.setRows(resultList);		
				gridView1.setDataSource(dataProvider1);
		 		$("#I_HOS").val("");
	    		dataResult2();
			}
			if(strUrl == "/sysImgMngDtl.do"){
		    	gridView2.cancel();
				gridView2.setAllCheck(false);
		    	gridView2.cancel();
				dataProvider2.setRows(resultList);		
				gridView2.setDataSource(dataProvider2);
			}
		}
		/** 그리드 호출 **/
		
		//추가
		function insertRow(){
			if( $("#I_HOS").val()==""){
				CallMessage("246","alert",["병원코드"]);// 선택 후 추가해주세요.
				return;
			}
		    var values = [
		    	  $("#I_HOS").val()
		    	];
		    gridInsertRow(gridView2,dataProvider2, values,"I_NSQ" );
		}
		function gridInsertRow(insertView,dataProv, values,focusfield){
			insertView.commit();
			dataProv.insertRow(0, values);
			insertView.setDataSource(dataProv);

			var updateCol = {
					  I_HOS	:$("#I_HOS").val()
					, I_RCL	:$("#I_RCL").val()
					, I_NSQ	:""
					, I_NCL	:""
					, I_NDV	:""
					, I_LOGMNU:I_LOGMNU
					, I_LOGMNM:I_LOGMNM
				};
			dataProvider2.updateStrictRow(0, updateCol);
			
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
// 			 dataProvider.getRowCount()
			var Nseq = "";
			
			if(dataRows != ""){				
				$.each(dataRows, function(i, e) {
		 			if(isNull(dataProvider2.getValue(e, "I_NSQ"))){
		 				CallMessage("201","alert",["순번은"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "I_NSQ";
		 				focusCell.fieldName = "I_NSQ";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(isNull(dataProvider2.getValue(e, "I_NCL"))){
		 				CallMessage("201","alert",["파일명 구분은 "]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "I_NCL";
		 				focusCell.fieldName = "I_NCL";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(dataProvider2.getValue(e, "I_NCL") == "DIRECT"){
			 			if(isNull(dataProvider2.getValue(e, "I_NDV"))){
			 				CallMessage("201","alert",["직접입력 값은"]);				//{0} 필수 입력입니다.
			 				focusCell.itemIndex = e;
			 				focusCell.column = "I_NDV";
			 				focusCell.fieldName = "I_NDV";
			 				gridView2.setCurrent(focusCell);
			 				gridView2.showEditor();
			 				rtnValid = true;
			 				return false;
			 			}
		 			}
				});
				var overlapFlg = true;
			    for (var i=0;i < dataProvider2.getRowCount(); i++){
					Nseq = dataProvider2.getValue(i, "I_NSQ");
					$.each(dataRows, function(idx2, e2) {
						if(i != e2){
							if(dataProvider2.getValue(e2, "I_NSQ") == Nseq){
				 				focusCell.itemIndex = e2;
				 				rtnValid = true;
				 				overlapFlg =  false;
				 				return false;
							}
						}
					});

					if(!overlapFlg){
		 				CallMessage("202","alert");				//{0} 필수 입력입니다.
		 				focusCell.column = "I_NSQ";
		 				focusCell.fieldName = "I_NSQ";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				overlapFlg =  false;
		 				break;
					}
					
				}

			}
			return rtnValid;
		}


		/* 파일명 규칙 저장 */
		function saveRow(){
			gridView2.commit();	
			var createRows = dataProvider2.getStateRows('created');
			var	updateRows = dataProvider2.getStateRows('updated');
			
			var valid = saveValidation(createRows);
			if(!valid){
				valid = saveValidation(updateRows);
			}
			if(!valid){
				callLoading("/sysImgMngSave.do","on");
				if(createRows != ""){ 
					ajaxListCall("/sysImgMngSave.do",dataProvider2,createRows,createEnd,false); // 등록
				}else{
					if(updateRows != ""){ 
						ajaxListCall("/sysImgMngUdt.do",dataProvider2,updateRows,updateEnd,false); // 수정
					}
				}
			}
		}
		

		function createEnd(rtnCall){
			if(!rtnCall){
				callLoading("/sysImgMngSave.do","off");
				CallMessage("244","alert",["파일명 규칙 저장"]);		//{0}에 실패하였습니다.
			}else{
				var	updateRows = dataProvider2.getStateRows('updated');
				if(updateRows != ""){ 
					if(rtnCall){
						rtnCall = ajaxListCall("/sysImgMngUdt.do",dataProvider2,updateRows,updateEnd,false); // 수정
					}
				}else{
					endEvet(rtnCall,"/sysImgMngSave.do");
				}
			}
		}
		
		function updateEnd(rtnCall){
			endEvet(rtnCall,"/sysImgMngUdt.do");
		}
		function endEvet(rtnCall,strUrl){
			callLoading(strUrl,"off");
			if(!rtnCall){
				CallMessage("244","alert",["파일명 규칙 저장"]);		//{0}에 실패하였습니다.
			}else{
				CallMessage("278","alert",["파일명 규칙 저장을"],dataResult2);	//278={0} 완료하였습니다.
// 				dataResult2();
			}
			
		}
		/* 파일명 규칙 저장 */
		
		//삭제
		function deleteRow(){
			var dataRows,strUrl,Provider;
	        //파일명 규칙 삭제
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
	        //파일명 규칙 삭제
			dataRows = gridView2.getCheckedRows();
			strUrl = "/sysImgMngDel.do";
			Provider = dataProvider2;
			
			rtnCall = ajaxListCall(strUrl,Provider,dataRows,delEnd); // 수정
		}
		function delEnd(rtnCall){
			if(!rtnCall){
				CallMessage("244","alert",["파일명 규칙 삭제"]);		//{0}에 실패하였습니다.
			}else{
				CallMessage("278","alert",["파일명 규칙 삭제를"],dataResult2);	//278={0} 완료하였습니다.
// 				dataResult2();
			}
		}
		
	</script>
</head>
<body>                       
<form id="searchForm" name="searchForm">
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
						<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
						<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
						<input id="I_HOS" 	name="I_HOS" 	type="hidden"/>
						<div class="srch_box">
							<div class="srch_box_inner">
								<div class="srch_item">
									<label for="" class="label_basic">병원 코드</label>
									<input  type="text" class="srch_put numberOnly"  id="I_PCD" name="I_PCD" maxlength="5"   onkeydown="return enterSearch(event)" >
								</div>
								<div class="srch_item">
									<label for="" class="label_basic">병원명</label>
									<input  type="text" class="srch_put"  id="I_FNM" name="I_FNM"  maxlength="19"  onkeydown="return enterSearch(event)" >
								</div>
							</div>  
					
							<div class="srch_box_inner m-t-10">
								<div class="srch_item">
									<label for="" class="label_basic">담당자아이디</label>
									<input  type="text" class="srch_put engNumOnly"  id="I_MBC" name="I_MBC"  maxlength="6"  onkeydown="return enterSearch(event)" >
								</div>
								<div class="srch_item">
									<label for="" class="label_basic">담당자명</label>
									<input  type="text" class="srch_put"  id="I_NAM" name="I_NAM"  maxlength="5"  onkeydown="return enterSearch(event)" >
								</div>
							
							</div>
							
							<div class="btn_srch_box">
								<button type="button" class="btn_srch">조회</button>
							</div> 
						</div>
				</div>
			</div>
		</div>
		<!-- 검색영역 end -->
		
		<div class="container-fluid">
             <div class="con_wrap col-md-5 col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">병원</h3>
				</div>
				<div class="con_section overflow-scr">
					<div class="tbl_type">
						<div id="realgrid1"   class="realgridH"></div>
					</div>
				</div>
			</div>

            <div class="con_wrap col-md-7 col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">파일명 규칙</h3>
					<div class="tit_btn_area">
						<label for="" class="label_basic">적용 양식지</label>
                     	<div class="select_area">
                     	 	 <select id="I_RCL" name="I_RCL" class="form-control" onchange="dataResult2()">
                             </select>
                         </div>
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
</form>
	<!-- content_inner -->       	
	
</body>
</html>



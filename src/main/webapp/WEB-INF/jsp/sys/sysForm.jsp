<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>폼별 결과 양식 관리  </title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<script>
		var pmcd = "";
		var resultCode = "";
	    var formValues = [];
	    var typeValues = [];
	    var clsValues = [];
	    var formLabels = [];
	    var typeLabels = [];
	    var clsLabels = [];
		var gridView,dataProvider;
		$(document).ready( function(){
	  		
			getGridCode("RPT_FORM");
   			getGridCode("RPT_TYPE");
   			getGridCode("RPT_CLS");
// 			setGrid();
			//RPT_FORM
			$(".btn_srch").click(function(){
				dataResult();
			});
		});
		function getGridCode(PSCD){
			var formData = $("#searchForm").serialize();
			formData +="&I_PSCD="+PSCD;
			ajaxAsyncCall("/getCommCode.do", formData);
		}
		
		function dataResult(){
			gridView.commit();
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysFormList.do", formData);
		}
	
		// ajax 호출 result function
		function onResult(strUrl,response){
			var resultList,I_PSCD;
			if(!isNull( response.resultList)){
				resultList = response.resultList;
			}
			if(strUrl == "/getCommCode.do"){
				//RPT_FORM :  결과지 폼
				//RPT_TYPE :  결과지 종류
				//RPT_CLS : 결과지 양식
				I_PSCD = response.I_PSCD;
				var resultCode = '<option value="">전체</option>';
	            $.each(resultList,function(k,v){
	            	if(I_PSCD =="RPT_FORM"){
	            		formValues.push(v.VALUE);
		                formLabels.push(v.LABEL);
	            	}
	            	if(I_PSCD =="RPT_TYPE"){
		                typeValues.push(v.VALUE);
		                typeLabels.push(v.LABEL);
	            	}
	            	if(I_PSCD =="RPT_CLS"){
		                clsValues.push(v.VALUE);
		                clsLabels.push(v.LABEL);
	            	}
	            	if(I_PSCD =="RPT_FORM"||I_PSCD =="RPT_TYPE"||I_PSCD =="RPT_CLS"){
						resultCode += '<option value="'+v.$R_SCD+'">'+v.$R_CNM+ '</option>';
	            	}
	            });
				//RPT_FORM :  결과지 폼
            	if(I_PSCD =="RPT_FORM"){
        			$('#I_RFM').html(resultCode);
        	  		jcf.replace($("#I_RFM"));
            	}
				//RPT_TYPE :  결과지 종류
            	if(I_PSCD =="RPT_TYPE"){
        			$('#I_RTP').html(resultCode);
        	  		jcf.replace($("#I_RTP"));
            	}
				//RPT_CLS : 결과지 양식
            	if(I_PSCD =="RPT_CLS"){
        			$('#I_RCL').html(resultCode);
        	  		jcf.replace($("#I_RCL"));
            	}
	            
	            if(formValues.length>0&&typeValues.length>0&&clsValues.length>0){
	          		<%
	    	  		Map<String, Object> QuickParam = new HashMap<String, Object>();
	    	  		if(paramList.size()>0){
	    	  			for(int idx=0;idx<paramList.size();idx++)
	    	  			{
	    	  				QuickParam = paramList.get(idx);
	    	  				if(!"".equals(QuickParam.get("S008VCD"))){
			    		%>
			    			$("#<%=QuickParam.get("S007RCD")%>").val("<%=QuickParam.get("S008VCD")%>");
			    	  		jcf.replace($("#<%=QuickParam.get("S007RCD")%>"));
			    		<%
			    	  			}
		    	  			}
		    	  		}
		    	  	%>
	 				setGrid();
	            }
			}
			if(strUrl == "/sysFormList.do"){
		    	gridView.setAllCheck(false);
				dataProvider.setRows(resultList);		
				gridView.setDataSource(dataProvider);
				callLoading(strUrl,"off");
			}
		}
		/*폼별 결과 양식 그리드 설정*/
		function setGrid(){
		    dataProvider = new RealGridJS.LocalDataProvider();
		    gridView = new RealGridJS.GridView("realgrid");
		    gridView.setDataSource(dataProvider);
		    setStyles(gridView);
		    
		    gridView.setEditOptions({
		    	insertable:true,
		    	deletable:true,
		    	editable:true,
		    	readOnly:false
		    });
	
			var fields = [
				{fieldName:"S011COR"}
				,{fieldName:"S011RFM"}
				,{fieldName:"S011RTP"}
				,{fieldName:"S011RCL"}
				,{fieldName:"I_LOGMNU"}
				,{fieldName:"I_LOGMNM"}
			];
		    dataProvider.setFields(fields);
		    var columnEditor =  {type :"dropDown" , dropDownPosition:"button", "domainOnly": true}
		    
	
		    var columns = [ 
		    	 {name:"S011RFM",   fieldName:"S011RFM",    header:{text:"폼", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}},		width:150,  editable:true,	lookupDisplay:true, values:formValues,labels:formLabels, editor:columnEditor}
				,{name:"S011RTP",   fieldName:"S011RTP",    header:{text:"결과지 타입", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}},	width:250,  editable:true,	lookupDisplay:true, values:typeValues,labels:typeLabels, editor:columnEditor}
		    	,{name:"S011RCL",	fieldName:"S011RCL",	header:{text:"양식지", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}},		width:150,  editable:true,	lookupDisplay:true, values:clsValues, labels:clsLabels,  editor:columnEditor}//,dropDownWhenClick :true

		    ];
		    gridView.setColumns(columns);
		    gridView.setPasteOptions({ fillColumnDefaults:true });

		    gridView.setColumnProperty("S011RFM", "editButtonVisibility", "always");
		    gridView.setColumnProperty("S011RCL", "editButtonVisibility", "always");
		    gridView.setColumnProperty("S011RTP", "editButtonVisibility", "always");
		    gridView.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		        var tooltip = value;
		        return tooltip;
		    }
		    
			/*신규행인 경우에는 모든 컬럼을 편집할수 있도록 변경, 기존행(DB조회등)인 경우 특정컬럼만 편집할수 있도록 변경*/
			gridView.onCurrentRowChanged = function (grid, oldRow, newRow) {
				var provider = grid.getDataProvider();
				var columns = grid.getColumnNames();
				var editable = newRow < 0 || provider.getRowState(newRow) === "created";
				
				if (!editable) { // 신규행이 아니면. 전체컬럼 editable:false
					columns.forEach(function(obj) {
						grid.setColumnProperty(obj,"editable",false)
					});
					columns = ["S011RCL","S011RTP"];
				};
				columns.forEach(function(obj) {
					grid.setColumnProperty(obj,"editable",true)
				});
			};
		    /** 영문만 들어가게 설정 **/
		    gridView.onEditChange = function(grid, index, value) {
				if (index.column === "S011RFM" ) {
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
		/** 그리드 호출 **/
		
		//추가
		function insertRow(){
		    var values = [
		    	"" 
		    	];
		    gridInsertRow(gridView,dataProvider, values,"S011RFM" );
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
			var focusCell = gridView.getCurrent();

			if(dataRows != ""){
				$.each(dataRows, function(i, e) {
		 			if(isNull(dataProvider.getValue(e, "S011RFM"))){
		 				CallMessage("201","alert",["폼은"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "S011RFM";
		 				focusCell.fieldName = "S011RFM";
		 				gridView.setCurrent(focusCell);
		 				gridView.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(isNull(dataProvider.getValue(e, "S011RTP"))){
		 				CallMessage("201","alert",["결과지타입은"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "S011RTP";
		 				focusCell.fieldName = "S011RTP";
		 				gridView.setCurrent(focusCell);
		 				gridView.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(isNull(dataProvider.getValue(e, "S011RCL"))){
		 				CallMessage("201","alert",["양식지는"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "S011RCL";
		 				focusCell.fieldName = "S011RCL";
		 				gridView.setCurrent(focusCell);
		 				gridView.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
				});
			}
			return rtnValid;
		}
		
		/* 폼별 결과 양식 저장 */
		function saveRow(){
		    //폼별 결과 양식 추가, 수정
			gridView.commit();	
			var createRows = dataProvider.getStateRows('created');
			var	updateRows = dataProvider.getStateRows('updated');
			
			var valid = saveValidation(createRows);
			if(!valid){
				valid = saveValidation(updateRows);
			}
			if(!valid){
				callLoading("/sysFormCheck.do","on");
				if(createRows != ""){ 
		 			ajaxListCall("/sysFormCheck.do",dataProvider,createRows,createCheckEnd,false); // 등록
	// 				var tt = dataPrvd(createRows);
	// 				ajaxListCall("/sysFormCheck.do",dataProvider,createRows,createEnd); // 등록
				}else{
					if(updateRows != ""){ 
						ajaxListCall("/sysFormUdt.do",dataProvider,updateRows,updateEnd,false); // 수정
					}
				}
			}
		}
		
		/*저장 가능 여부 체크후 저장 call */
		function createCheckEnd(rtnCall,Msg){
			if(!isNull(Msg)){
				callLoading("/sysFormSave.do","off");
				messagePopup("","",Msg);
			}else{
				var createRows = dataProvider.getStateRows('created');
				ajaxListCall("/sysFormSave.do",dataProvider,createRows,createEnd,false); // 등록
			}
		}
		

		function createEnd(rtnCall){
			var	updateRows = dataProvider.getStateRows('updated');
			if(!rtnCall){
				callLoading("/sysFormSave.do","off");
				CallMessage("244","alert",["폼별 결과 양식 저장"]);		//{0}에 실패하였습니다.
			}else{
				if(updateRows != ""){ 
					if(rtnCall){
						rtnCall = ajaxListCall("/sysFormUdt.do",dataProvider,updateRows,updateEnd,false); // 수정
					}
				}else{
					callLoading("/sysFormSave.do","off");
					CallMessage("278","alert",["폼별 결과 양식 저장을"],dataResult);	//278={0} 완료하였습니다.
				}
			}
		}
		function updateEnd(rtnCall){
			callLoading("/sysFormUdt.do","off");
			if(!rtnCall){
				CallMessage("244","alert",["폼별 결과 양식 저장"]);		//{0}에 실패하였습니다.
			}else{
				CallMessage("278","alert",["폼별 결과 양식 저장을"],dataResult);	//278={0} 완료하였습니다.
			}
		}
		/* 폼별 결과 양식 저장 */
		
		
		//삭제
		function deleteRow(){
			gridView.commit();	
			var dataRows,strUrl,Provider;
	        //폼별 결과 양식 삭제
			dataRows = gridView.getCheckedRows();
	        var insertRowChk = InsertGridRowDel(dataRows,gridView,dataProvider);
			dataRows = gridView.getCheckedRows();
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
	        //폼별 결과 양식 삭제
			dataRows = gridView.getCheckedRows();
			strUrl = "/sysFormDel.do";
			Provider = dataProvider;
			
			rtnCall = ajaxListCall(strUrl,Provider,dataRows,deleteEnd); // 삭제
	
// 			if(!rtnCall){
// 				CallMessage("244","alert",["폼별 결과 양식 삭제"]);		//{0}에 실패하였습니다.
// 			}else{
// 				dataResult(false);
// 			}
		}
		function deleteEnd(rtnCall){
			if(!rtnCall){
				CallMessage("244","alert",["폼별 결과 양식 삭제"]);		//{0}에 실패하였습니다.
			}else{
				CallMessage("278","alert",["폼별 결과 양식 삭제를"],dataResult);	//278={0} 완료하였습니다.
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
						<input id="I_LOGMNU" 	name="I_LOGMNU" 	type="hidden"/>
						<input id="I_LOGMNM" 	name="I_LOGMNM" 	type="hidden"/>
						<div class="srch_box">
							<div class="srch_box_inner">
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">양식지</label>
									<div class="select_area">
									<select class="form-control"  id="I_RCL"  name="I_RCL"></select> 
									</div>
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">결과지</label>
									<div class="select_area">
									<select class="form-control"  id="I_RTP"  name="I_RTP"></select> 
									</div>
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">폼</label>
									<div class="select_area">
									<select class="form-control"  id="I_RFM"  name="I_RFM"></select> 
									</div>
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
            <div class="con_wrap">
				<div class="title_area">
					<h3 class="tit_h3">폼별 결과 양식</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:insertRow()">추가</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()">저장</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow()">삭제</button>
					</div>
				</div>
	            <div class="con_section overflow-scr">
                    <div class="tbl_type">
                    	<div id="realgrid"  class="realgridH" ></div>
                    </div>
                </div>
			</div>
		</div>
	</div>    
	<!-- content_inner -->       	
	
</body>
</html>
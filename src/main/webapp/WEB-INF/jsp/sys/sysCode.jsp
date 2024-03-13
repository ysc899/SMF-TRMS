<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>시스템 코드</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <meta name="title" content="BASIC"/>
    
    <%@ include file="/inc/common.inc"%>
    <script type="text/javascript">

    var treeDataProvider1, treeView1,gridView2,dataProvider2;
    var pscd = "";
    var gridValues = [];
    var gridLabels = [];
    
    $(document).ready( function(){
    
        treeDataProvider1 = new RealGridJS.LocalTreeDataProvider();
        
        treeView1 = new RealGridJS.TreeView("realgrid1");
        setStyles(treeView1);
        treeView1.setDataSource(treeDataProvider1);
        
        treeView1.setEditOptions({
            insertable: false,
            appendable: true,
            deletable: false,
            readOnly: true,
            editable:true
        })
        
        var fields1 = [
            {fieldName: "$R_RLT"},
            {fieldName: "$R_SCD"},
            {fieldName: "$R_PSCD"},
            {fieldName: "$R_CNM"}
        ];
        
        treeDataProvider1.setFields(fields1);
        
        var columns1 = [
            {name: "$R_SCD", fieldName: "$R_SCD", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "코드"}, editable: false},
            {name: "$R_CNM", fieldName: "$R_CNM", width: "200", styles: {textAlignment: "near"}, renderer:{showTooltip: true}, header: {text: "코드명"}, editable: false}
        ];
        
        treeView1.setColumns(columns1);
        
        treeView1.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
        
        treeView1.onDataCellClicked = function (tree, index) {
            
	        pscd = tree.getValue(index.itemIndex, 1);
	        dataResult2();
        };
        
        var checkBar = treeView1.getCheckBar();
        checkBar.visible = false;
        treeView1.setCheckBar(checkBar);
        
        var stateBar = treeView1.getStateBar();
	    stateBar.visible = false;
	    treeView1.setStateBar(stateBar);
	    
	    getGridCode();
    });
    
    function getGridCode(){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD=YES_NO";
		ajaxCall("/getCommCode.do", formData);
	}
    
    function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysCodeList.do", formData);
	}
	
    function dataResult2(){
		$("#I_PSCD").val(pscd);
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysCodeDtl.do", formData);
	}
    
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/getCommCode.do"){
			var resultList =  response.resultList;
            $.each(resultList,function(k,v){
                gridValues.push(v.VALUE);
                gridLabels.push(v.LABEL);
            });
   		 	setGrid();
		}
		
		if(strUrl == "/sysCodeList.do"){
			var resultList =  response.resultList;
			treeView1.closeProgress();
	        treeDataProvider1.setRows(resultList, "$R_RLT", false);		
			treeView1.setDataSource(treeDataProvider1);
			treeView1.expand(0,false);
			gridView2.setAllCheck(false);
		}
		if(strUrl == "/sysCodeDtl.do"){
			gridView2.setAllCheck(false);
			var resultList =  response.resultList;
			gridView2.closeProgress();
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
		}
	}
	
    function setGrid(){
        dataProvider2 = new RealGridJS.LocalDataProvider();
     
        gridView2 = new RealGridJS.GridView("realgrid2");
        setStyles(gridView2);
        gridView2.setDataSource(dataProvider2);
          
        gridView2.setEditOptions({
            insertable: true,
            deletable: true,
            readOnly: false,
            editable: true
            
        })
        
        var fields2 = [
            {fieldName: "$R_SCD"},
            {fieldName: "$R_PSCD"},
            {fieldName: "$R_SEQ", dataType: "number"},
            {fieldName: "$R_CNM"},
            {fieldName: "$R_DSC"},
            {fieldName: "$R_RF1"},
            {fieldName: "$R_RF2"},
            {fieldName: "$R_RF3"},
            {fieldName: "$R_UYN"}, 
            {fieldName: "$R_CHL"},
            {fieldName: "I_LOGMNU"}, 
            {fieldName: "I_LOGMNM"}   //메뉴명
        ];
        
        dataProvider2.setFields(fields2);
        
        var columns2 = [
            {name: "$R_SCD", fieldName: "$R_SCD", width: "100", styles: {textAlignment: "near"},   renderer:{showTooltip: true}, header: {text: "코드", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}},  editable: true, editor : {textCase: "upper", maxLength : 20},  validations : [{ criteria : "value not match '\[^A-Z_0-9\]'", message : "대문자만 입력하세요.", level : "error", mode : "always" }]}
          , {name: "$R_CNM", fieldName: "$R_CNM", width: "100", styles: {textAlignment: "near"},   renderer:{showTooltip: true}, header: {text: "코드명", subText : "*", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}}, editable: true, editor : {maxLength : 30}}
          , {name: "$R_UYN", fieldName: "$R_UYN", width: "100", styles: {textAlignment: "center"}, renderer:{showTooltip: true}, header: {text: "사용여부"}, editable: true, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "domainOnly": true}}//,dropDownWhenClick :true}
          , {name: "$R_SEQ", fieldName: "$R_SEQ", width: "50",  styles: {textAlignment: "far"},    renderer:{showTooltip: true}, header: {text: "순번"}, editable: true, editor : {type : "number", maxLength : 10}}
          , {name: "$R_DSC", fieldName: "$R_DSC", width: "100", styles: {textAlignment: "near"},   renderer:{showTooltip: true}, header: {text: "비고"}, editor : {maxLength : 300}}
          , {name: "$R_RF1", fieldName: "$R_RF1", width: "50",  styles: {textAlignment: "far" },   renderer:{showTooltip: true}, header: {text: "참조1"}, editor : {maxLength : 30}}
          , {name: "$R_RF2", fieldName: "$R_RF2", width: "50",  styles: {textAlignment: "far" },   renderer:{showTooltip: true}, header: {text: "참조2"}, editor : {maxLength : 30}}
          , {name: "$R_RF3", fieldName: "$R_RF3", width: "50",  styles: {textAlignment: "far" },   renderer:{showTooltip: true}, header: {text: "참조3"}, editor : {maxLength : 30}}
        ];
        
        //컬럼을 GridView에 입력 합니다.
        //dataProvider2.setFields(columns2);
        gridView2.setColumns(columns2);
        
        gridView2.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
        
        gridView2.onEditChange = function(grid, index, value) {
			if (index.column === "$R_SCD" ) {
		      if (value) {
		        var onEditChange = grid.onEditChange;
		        grid.onEditChange = null; // 무한 루프로 인한 이벤트 제거
		        grid.setEditValue(value.replace(/[/^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,""))
		        grid.onEditChange = onEditChange; // 이벤트 재설정
		      }
		    }
		}
		/*신규행인 경우에는 모든 컬럼을 편집할수 있도록 변경, 기존행(DB조회등)인 경우 특정컬럼만 편집할수 있도록 변경*/
		gridView2.onCurrentRowChanged = function (grid, oldRow, newRow) {
			var provider = grid.getDataProvider();
			var columns = grid.getColumnNames();
			var editable = newRow < 0 || provider.getRowState(newRow) === "created";
			columns.forEach(function(obj) {
				grid.setColumnProperty(obj,"editable",true);
			});
			if (!editable) { // 신규행이 아니면. 전체컬럼 editable:false
				grid.setColumnProperty("$R_SCD","editable",false);
			};
		};
        
        dataResult();
    }
    
  	//조회
    function search(){
   		gridView2.cancel();
   		dataProvider2.clearRows();
   		gridView2.setDataSource(dataProvider2);
   		dataResult();
   	}
        
    //추가
    function insertRow(){
    	if(pscd == ""){
			pscd = "ROOT";
		}
		var values = [];
		//dataProvider2.insertRow(0, values);
		
		gridView2.commit();
		dataProvider2.insertRow(0, values);
		gridView2.setDataSource(dataProvider2);
		//gridView2.beginInsertRow(Math.max(0,-1), false);
		gridView2.showEditor();
		gridView2.setFocus();
    }
    
    //삭제
    function deleteRow(){
	var dataRows = gridView2.getCheckedRows();

// 		dataRows = gridView2.getCheckedRows();
	    var insertRowChk = InsertGridRowDel(dataRows,gridView2,dataProvider2);
		dataRows = gridView2.getCheckedRows();
		if(dataRows == ""){ 
			if(!insertRowChk){
				CallMessage("243");			//선택내역이 없습니다.
				return;
			}
		}else{
			if(dataRows != ""){
		 		for(var i in dataRows){
					var chk = "";
					chk = dataProvider2.getValue(dataRows[i], "$R_CHL");
					if(chk == "Y"){
						CallMessage("281","alert",["하위 코드가"]);	//{0} 존재하여 삭제할 수 없습니다.
						return;	
					}
				}	
				CallMessage("242","confirm", "", deleteStart);
			} else {
				CallMessage("243","alert");
				return;
			}
	    }
    }
    
    function deleteStart(){
		gridView2.cancel();
		var dataRows = gridView2.getCheckedRows();

		ajaxListCall("/sysCodeDel.do", dataProvider2, dataRows, deleteEnd)
	}
    
	
	function deleteEnd(rtnCall){
		if(!rtnCall){
			CallMessage("244","alert",["삭제"]);	
		} else {
			CallMessage("278","alert",["삭제를"],dataResult2);	//278={0} 완료하였습니다.
		}
	}

	function saveValidation(dataRows)
	{
		var rtnValid = false;
		var focusCell = gridView2.getCurrent();
		var R_SCD = "";
		
		if(dataRows != ""){				
			$.each(dataRows, function(i, e) {
	 			if(isNull(dataProvider2.getValue(e, "$R_SCD"))){
	 				CallMessage("201","alert",["코드는"]);				//{0} 필수 입력입니다.
	 				focusCell.itemIndex = e;
	 				focusCell.column = "$R_SCD";
	 				focusCell.fieldName = "$R_SCD";
	 				gridView2.setCurrent(focusCell);
	 				gridView2.showEditor();
	 				rtnValid = true;
	 				return false;
	 			}
	 			if(isNull(dataProvider2.getValue(e, "$R_CNM"))){
	 				CallMessage("201","alert",["코드명은"]);				//{0} 필수 입력입니다.
	 				focusCell.itemIndex = e;
	 				focusCell.column = "$R_CNM";
	 				focusCell.fieldName = "$R_CNM";
	 				gridView2.setCurrent(focusCell);
	 				gridView2.showEditor();
	 				rtnValid = true;
	 				return false;
	 			}
			});
			
			if(!rtnValid)
			{
				var overlapFlg = true;
			    for (var rowidx=0;rowidx < dataProvider2.getRowCount(); rowidx++){
			    	R_SCD = dataProvider2.getValue(rowidx, "$R_SCD");
					$.each(dataRows, function(i, e) {
						if(rowidx != e){
							if(dataProvider2.getValue(e, "$R_SCD") == R_SCD){
				 				focusCell.itemIndex = e;
				 				rtnValid = true;
				 				overlapFlg =  false;
				 				return false;
							}
						}
					});
	
					if(!overlapFlg){
		 				CallMessage("202","alert");				//중복데이터가 존재합니다.
		 				focusCell.column = "$R_SCD";
		 				focusCell.fieldName = "$R_SCD";
		 				gridView2.setCurrent(focusCell);
		 				gridView2.showEditor();
		 				rtnValid = true;
		 				overlapFlg =  false;
		 				break;
					}
				}
			}

		}
		return rtnValid;
	}

	function saveRow(){
		
		gridView2.commit();		
        var createRows = dataProvider2.getStateRows('created');
		var updateRows = dataProvider2.getStateRows('updated');
		
		if(createRows == "" && updateRows == ""){
			CallMessage("205","alert",["저장"]); //저장 대상이 없습니다.
			return;	
		}
		var valid = saveValidation(createRows);
		if(!valid){
			valid = saveValidation(updateRows);
		}
		if(!valid){
			callLoading(null,"on");
			if(createRows != ""){
				var sysChkSaveCnt = 0;
				$.each(createRows, function(i, e) {
					var	setValue = {
							I_LOGMNU: I_LOGMNU
							, I_LOGMNM:I_LOGMNM
							, "$R_PSCD":pscd
						};
					dataProvider2.updateStrictRow(e, setValue);
				});
				createRows = dataProvider2.getStateRows('created');
				//console.log(createRows);
				ajaxListCall("/sysCodeChkSave.do", dataProvider2, createRows, saveStart,false)
			} else {
				saveStart(true);
			}
		}
	}
	
	function saveStart(rtnCall){
		if(!rtnCall){
			callLoading("/sysCodeChkSave.do","off");
			CallMessage("202","alert");	
		} else {
			var createRows = dataProvider2.getStateRows('created');
			var updateRows = dataProvider2.getStateRows('updated');
			
			if(createRows == "" && updateRows == ""){
				callLoading(null,"off");
				CallMessage("205","alert",["저장"]);
				return;	
			}
			if(createRows != ""){
				ajaxListCall("/sysCodeSave.do", dataProvider2, createRows, saveEnd,false)
			}else{
				if(updateRows != ""){
					ajaxListCall("/sysCodeUdt.do", dataProvider2, updateRows, updateEnd,false)
				}
			}
		}
	}
		
	
	function saveEnd(rtnCall){
		if(!rtnCall){
			callLoading("/sysCodeSave.do","off");
			CallMessage("244","alert",["저장"]);	
		} else {
			var	updateRows = dataProvider2.getStateRows('updated');
			if(updateRows != ""){ 
				if(rtnCall){
					rtnCall = ajaxListCall("/sysCodeUdt.do",dataProvider2,updateRows,updateEnd,false); // 수정
				}
			}else{
				endEvent(rtnCall,"/sysCodeSave.do");
			}
		}
	}
	
	function updateEnd(rtnCall){
		endEvent(rtnCall,"/sysCodeUdt.do");
	}
	
	function endEvent(rtnCall,strUrl){
		callLoading(strUrl,"off");
		if(!rtnCall){
			CallMessage("244","alert",["저장"]);		//저장에 실패하였습니다.
		}else{
			CallMessage("278","alert",["저장을"],dataResult2);	//278={0} 완료하였습니다.
		}
	}
	
    </script>
</head>

<body>

<!-- <spring:message code="200" /> -->

	<!-- content_wrap -->
    <form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_PSCD" name="I_PSCD" type="hidden"/>
        		<div class="content_inner">
                
                	<!-- 검색영역 -->
                    <div class="container-fluid">
                        <div class="con_wrap col-md-12 srch_wrap">
                            <div class="title_area open">
                                <h3 class="tit_h3">검색영역</h3>
                                <a href="#" class="btn_open">검색영역접기</a>
                            </div>
                            <div class="con_section row">
                                <p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>
                                <div class="srch_box">
                                    <div class="srch_box_inner">
                                        <div class="srch_item">
                                            <label for="menucode" class="label_basic">코드</label>
                                            <input type="text" class="srch_put" id="I_SCD" name="I_SCD"  onKeydown="if(event.keyCode==13) search()" placeholder="">
                                        </div>
                                        <div class="srch_item">
                                            <label for="menuname" class="label_basic">코드명</label>
                                            <input type="text" class="srch_put" id="I_CNM" name="I_CNM" onKeydown="if(event.keyCode==13) search()" placeholder="">
                                        </div>
                                                         
                                    </div>
                                    <div class="btn_srch_box">
                                            <button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
                                    </div>  
                                </div>
                            </div>
                        </div>
                    </div>
                	<!-- 검색영역 end -->
                    
                    <div class="container-fluid">
                        <div class="con_wrap col-md-4 col-sm-12">
                            <div class="title_area">
                                <h3 class="tit_h3">코드</h3>
                            </div>
                            <div class="con_section overflow-scr">
                                <div class="tbl_type">
                                	<div id="realgrid1" class="realgridH"></div>
                                </div>
                            </div>
                        </div>
                    
                    
                        <div class="con_wrap col-md-8 col-sm-12">
                            <div class="title_area">
                                <h3 class="tit_h3">하위 코드</h3>
                                <div class="tit_btn_area">
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:insertRow()">추가</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()">저장</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow()">삭제</button>
                                </div>
                            </div>
                            <div class="con_section overflow-scr">
                                <div class="tbl_type">
                                	<div id="realgrid2" class="realgridH"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                                       
                </div>    
                <!-- content_inner -->       	
            
       </form>     
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script>
	
	var I_LOGMNU = "infect01";
	var I_LOGMNM = "감염병 신고 팝업";
	var resultCode = "";
	var gridView,dataProvider;
	
	$(document).ready( function(){
		
		dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid1");
	    
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
	    
	    gridView.setDisplayOptions({
  			fitStyle: "even"
		});
	    
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })

	    var fields = [
	    	{
	            "fieldName": "F999NM1"
	        },
	        {
	            "fieldName": "F999NM4"
	        },
	        {
	            "fieldName": "I_LOGMNU"
	        },
	        {
	            "fieldName": "I_LOGMNM"
	        }
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	    	
	        {"name": "F999NM1", "fieldName": "F999NM1", "width": "100", "styles": {"textAlignment": "center"}, "header": {"text": "질병군"}}
	      ,	{"name": "F999NM4", "fieldName": "F999NM4", "width": "500", "styles": {"textAlignment": "near"}, "header": {"text": "감염병명"}}
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
	    
		resultCode = getCode(I_LOGMNU, I_LOGMNM, "DISE_CLS", "Y");
		$('#I_F999NM1').html(resultCode);
		jcf.replace($("#I_F999NM1"));
		
		dataResult1();
	})
	
	function gridViewRead(gridValus){
		
	}
	
	function dataResult1(){
		
		var formData = $("#infect01Form").serialize();
		ajaxCall("/infectStdList.do",formData);
	}
	
	function onResult(strUrl,response){
		if(strUrl == "/infectStdList.do"){
			var resultList = response.resultList;
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
			
			callResize();
		}
	}
	
	//조회
	function search(){
		dataResult1();
	}
	
	function exportGrid(){
		gridView.exportGrid({
		    type: "excel",
		    target: "local",
		    fileName: "기준항목내역.xlsx",
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
	
	</script>
	
</head>
<body>
	<div style="width: 800px;">
		<form id="infect01Form" name="infect01Form" enctype="multipart/form-data" method="post">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<div class="srch_box">
				<div class="srch_box_inner">
					<div class="srch_item srch_item_v3">
						<!-- <label class="label_basic">질병군</label> -->
						<label class="label_basic">감염병분류</label>
						<div class="select_area">
	                       <select id="I_F999NM1" name="I_F999NM1" class="form-control"></select>
	                    </div>
	                    <label class="label_basic">감염병 명</label>
	                    <input type="text" id="I_F999NM4" name="I_F999NM4" style="width:200px;" onKeydown="if(event.keyCode==13) dataResult1()" maxlength="40">
	                </div> 
				</div>
				
				<div class="btn_srch_box">
	             	<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
	            </div>
			</div>
			<div class="tit_btn_area modal_btn_area">
	        	<button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button>
	        </div>
	        
	        <div class="con_section overflow-scr">
		    	<div class="tbl_type" >
					<div id="realgrid1" style="height:300px;"></div>                
				</div>
			</div>
		</form>
	
		<div class="modal-footer">
			<div class="min_btn_area">
				  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
			 </div>
		</div>
	</div>

</body>
</html>
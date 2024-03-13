<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>SMS</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	var I_LOGMNU = "reqUser03";
	var I_LOGMNM = "SMS";
	var dataProvider,gridView;
	var dataList = [];
	
	$(document).ready( function(){
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		var s_nam = "<%= ss_nam %>";
		
		$('#SENDNAM').val(s_nam);
		$('#SENDHPN').val("15666500");
		dataProvider = new RealGridJS.LocalDataProvider();
		gridView = new RealGridJS.GridView("realgrid");
	    setStyles(gridView);
	    gridView.setHeader({
	    	height:40
	    })
	    /*
	    gridView.setDisplayOptions({
		  heightMeasurer: "fixed",
		  rowResizable: true,
		  rowHeight: 150
		});
	    */

	    
	    gridView.setDataSource(dataProvider);
	    gridView.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:true
	    })
	    var fields = [
	    	{fieldName: "DNO"},
	    	{fieldName: "DAT"},
	    	{fieldName: "JNO"},
	    	{fieldName: "NAM"},
	    	{fieldName: "CHN"},
	    	{fieldName: "SENDNAM"},
	    	{fieldName: "SENDHPN"},
	    	{fieldName: "C002MSG"},
	    	{fieldName: "C001HPN"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	  //이미지 버튼 생성
		var columns = [
	        {name: "IMAGE", fieldName: "", width: "80", styles: {iconLocation: "center"},    renderer:{type:"image", sommthing:true, type:"multiIcon", renderCallback:function(grid, index){
		    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
		          var prf = [];
		          //console.log(JSON.stringify(index));
		          prf.push('../images/common/btn_delete.png');
		          
		          return prf
		      }}, header: {text: "출력" ,visible : false}, editable : false}
	       ,{name: "NAM", fieldName: "NAM", width: "120", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "수진자 명"}, editable : false}
	       ,{name: "C001HPN", fieldName: "C001HPN", width: "150", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "수진자 전화번호"}, editor : { maxLength : 20}, editable: false}
	      ];
	    
		gridView.setColumnProperty("IMAGE", "buttonVisibility", "always");

		gridView.setColumns(columns);
		
		gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    };
		
		gridView.onDataCellClicked = function (grid, index) {
			if(index.column == "IMAGE"){
				dataProvider.removeRow(index.itemIndex, true);
			}
			//dataResult4(grid.getValues(index.itemIndex));
		};
		
		var checkBar = gridView.getCheckBar();
		checkBar.visible = false;
	    gridView.setCheckBar(checkBar);
	    
	    var stateBar = gridView.getStateBar();
	    stateBar.visible = false;
	    gridView.setStateBar(stateBar);
	    
	    /** 숫자만 들어가게 설정 **/
	    gridView.onEditChange = function(grid, index, value) {
			if (index.column == "C001HPN" ) {
				EditChange(grid, index, value);
		    }
		}
	});
	
	
	function EditChange(grid, index, value){
		if (value) {
			var onEditChange = grid.onEditChange;
			grid.onEditChange = null; // 무한 루프로 인한 이벤트 제거
	        grid.setEditValue(value.replace(/[^0-9]/g,""))
			grid.onEditChange = onEditChange; // 이벤트 재설정
		}
	}

	function userData(userData){
		$('#I_HOS').val(userData.I_HOS);
		//$('#I_NAM').val(userData.I_NAM);
	}
	
	function smsReq03Call(formData){
		//alert($('#searchForm', parent.document).val());
		//alert($(this, parent.document).html());
		
		/*
		if($('#pageiframe', parent.parent.document).attr("src" ).split("?")[0] == "/rstItem.do"){
			alert("확인");
		}
		*/
			
		var formData2 = $("#popupForm").serialize();
		dataList = formData;
		
		for(var i = 0; i < dataList.length - 1; i++) {
	        for(var j = i + 1; j < dataList.length; j++) {
	            if(JSON.stringify(dataList[i].CHN) === JSON.stringify(dataList[j].CHN)) {
	            	dataList.splice(j, 1);
	            }
	        }
	    }
		
		//console.log(dataList);
		var paramData = "";
		if(dataList.length > 0){
			for(var i in dataList){
				if(i == 0){
					//paramData = dataList[i].CHN;
					paramData = "|"+dataList[i].CHN+"|";
				} else {
					//paramData += "|"+dataList[i].CHN;
					paramData += dataList[i].CHN+"|";
				}
				
				//console.log(dataList[i].CHN);
				
			}	
		}
		//console.log(paramData);
		
		ajaxCall("/rstUser03List.do", formData2+"&I_CHN="+paramData);
		
	}
	
	
	function userData1(userData){
		$('#I_HOS').val(userData.I_HOS);
		$('#I_NAM').val(userData.I_NAM);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/rstUser03List.do"){
		//console.log(dataList);
			var resultList =  response.resultList;
			
			if (resultList.length > 0){
				for (var i in resultList){
					dataList[i].C001HPN =  resultList[i].C001HPN;
					//dataList[i].C001HPN = "01043058927";
				}	
			}
			
			/*
			for (var i in dataList){
				dataList[i].C001HPN =  resultList[i].C001HPN;
				//dataList[i].C001HPN = "01043058927";
			}
			*/
			
			gridView.closeProgress();
			dataProvider.clearRows();
			dataProvider.setRows(dataList);
			gridView.setDataSource(dataProvider);
			
			reSize();
		}
		
		if(strUrl == "/rstUserSMSSend.do"){
			closeModal();
		}
		
		
	}
	
	function reSize(){
		setTimeout("callResize()", 100);
		//http://219.252.39.22:8080/spring_basic/fileDown?UUID=	
	}
	
	function messageOpen(){
		var gridValus = $("#popupForm").serialize();
		
		fnOpenPopup2("/smsMsgPopup.do","메시지",gridValus,callFunPopup2);	
	}
	
	function callFunPopup2(url,iframe,gridValus){
		var userData = [];
		userData.I_HOS = $('#I_HOS').val();
		userData.I_NAM = $('#I_NAM').val();
		iframe.userData2(userData);
		iframe.gridViewRead(gridValus);	
	}
	
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		$('#C002MSG').val(data[1].C002MSG);
	}
	
	function sendMsg(){
		CallMessage("233","confirm", "", sendMsgStart);
	}
	
	function sendMsgStart(){
		gridView.commit();
		//if(){
			
		//}
		
		//console.log($('#C002MSG').size()); 
		/*
		var rows = dataProvider.getRows(0, dataProvider.getRowCount());
		console.log("rows = "+ JSON.stringify(rows));
		for(var i in dataProvider.getRows(0, dataProvider.getRowCount())){
			console.log("i = "+ i);
			console.log("dataProvider.getValue(i, 'C001HPN') = "+ dataProvider.getValue(0, "C001HPN"));
			console.log("dataProvider.getValue(1, 'C001HPN') = "+ dataProvider.getValue(1, "C001HPN"));
			if(dataProvider.getValue(i, "C001HPN") != ""){
				dataProvider.setValue(i, "SENDNAM", $('#SENDNAM').val());
				dataProvider.setValue(i, "SENDHPN", $('#SENDHPN').val());
				dataProvider.setValue(i, "C002MSG", $('#C002MSG').val());
				
				ajaxCall("/rstUserSMSSend.do", dataProvider.getJsonRow(i));
			}
			
		}
		*/
		for(var i in dataProvider.getRows(0, dataProvider.getRowCount())){
			if(dataProvider.getValue(i, "C001HPN") == ""){
				//console.log("dataProvider.getValue(i, 'C001HPN') = " + dataProvider.getValue(i, "C001HPN"));
			} else {
				dataProvider.setValue(i, "SENDNAM", $('#SENDNAM').val());
				dataProvider.setValue(i, "SENDHPN", $('#SENDHPN').val());
				dataProvider.setValue(i, "C002MSG", $('#C002MSG').val());
				
				ajaxCall("/rstUserSMSSend.do", dataProvider.getJsonRow(i));	
			}
		}
	}
	
	
    </script>
    
</head>
<body style="width : 500px;">
	<form id="popupForm" name="popupForm">
	<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
	<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		
	<input id="I_DTLDAT" name="I_DTLDAT" type="hidden">
	<input id="I_DTLJNO" name="I_DTLJNO" type="hidden">
	<input id="I_HOS" name="I_HOS" type="hidden">
	<input id="I_NAM" name="I_NAM" type="hidden">
		<div class="container-fluid">                
	        <div class="con_wrap col-md-12 col-sm-12">
            	<div class="tbl_type" style="overflow:auto">
					<div id="realgrid"  class="realgridH"></div>
			    </div>    
			    <div class="tbl_type">
					<table class="table table-bordered table-condensed tbl_l-type">
                    	<colgroup>
                        	<col width="25%">
                            <col >
                        </colgroup>
                        <tbody>
                        	<tr>
                            	<th scope="row">송신자 명</th>
                                <td>
                                	<input type="text" class="form-control" placeholder="" id="SENDNAM" name="SENDNAM" readonly>
                                </td>
                            </tr>
                            <tr>
                            	<th scope="row">송신자 연락처</th>
                                <td>
                                	<input type="number" class="form-control" placeholder="" id="SENDHPN" name="SENDHPN" readonly>
                                </td>
                            </tr>
                            <tr>
                            	<th scope="row">메세지 <button type="button"  data-toggle="modal" data-target="#msg-Modal" onClick="javascript:messageOpen();"><img src="../images/common/btn_srch.png" alt="검색"></button> </th>
                                <td>
                                    <textarea class="form-control" id="C002MSG" name="C002MSG"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="min_btn_area" align="center">
                	<button type="button" class="btn btn-primary" onClick="javascript:sendMsg()">보내기</button>
                    <button type="button" class="btn btn-info" data-dismiss="modal" onClick="javascript:closeModal()">취소</button>
                </div>          
	        </div>                    
	    </div>
	 </form>
</body>
</html>

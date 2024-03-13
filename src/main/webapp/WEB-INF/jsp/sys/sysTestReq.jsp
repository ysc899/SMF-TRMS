<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>의뢰목록관리 </title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	
	<script>
	var gridView1,dataProvider1,gridView2,dataProvider2;
    var SampValues = [];
    var SampLabels = [];
    var RejReasonValues = [];
    var RejReasonLabels = [];
    function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		search();
// 	    		dataResult1();
	        }
	    }else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
	    }
    } 
	
	$(document).ready( function(){
	    $('#I_FNM').change(function(){
	    	$('#I_HOS').val('');
	    });
		if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }
		
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "RCP_STAT", "Y");
		$('#I_STS').html(resultCode);
		jcf.replace($("#I_STS"));

	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "REQ_DIV");
		$('#I_REQDIV').html(resultCode);
		jcf.replace($("#I_REQDIV"));
		
		//달력 from~to
		$("#I_FDT").datepicker({
			dateFormat: 'yy-mm-dd',
			maxDate: $('#I_TDT').val(),
			onSelect: function(selectDate){
				$('#I_TDT').datepicker('option', {
					minDate: selectDate,
				});
			}
		});	
		$("#I_TDT").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: $('#I_FDT').val(),
			onSelect : function(selectDate){
				$('#I_FDT').datepicker('option', {
					maxDate: selectDate,
				});
			}		
		});
		$("#I_FDT").datepicker('setDate',new Date());
        $("#I_TDT").datepicker('setDate',new Date());
        getGridCode('REJ_REASON');

  		<%
	  		Map<String, Object> QuickParam = new HashMap<String, Object>();
	  		if(paramList.size()>0){
	  			for(int idx=0;idx<paramList.size();idx++)
	  			{
	  				QuickParam = paramList.get(idx);
	  				if(!"".equals(QuickParam.get("S008VCD"))){
		%>
					if("<%=QuickParam.get("S007RCV")%>" == "S_TERM"){
						changeDT("<%=QuickParam.get("S008VCD")%>");
					}else{
						$("#<%=QuickParam.get("S007RCD")%>").val("<%=QuickParam.get("S008VCD")%>");
				  		jcf.replace($("#<%=QuickParam.get("S007RCD")%>"));
					}
		<%
		  			}
	  			}
	  		}
	  	%>
        
        getMC999DGridCode();
		setGrid1();
		$(".btn_srch").click(function(){
			search();
		});
	});
	function getGridCode(PSCD){
		var formData = $("#searchForm").serialize();
		formData +="&I_PSCD="+PSCD;
		ajaxAsyncCall("/getCommCode.do", formData);
	}
	function getMC999DGridCode(){
		var formData = $("#searchForm").serialize();
		formData +="&I_CD1=CLIC&I_CD2=SAMP&I_CD3=";
		ajaxAsyncCall( "/getMC999DCommCode.do", formData);
	}
	//조회
	function search(){
		//병원코드 입력 후 조회 할 경우
		$("#I_DATAROW").val("0");
		$("#I_DAT").val("0");
		$("#I_JNO").val("0");
		dataResult1();
	}
	
	function setGrid1(){
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    setStyles(gridView1);
	    setGridBar(gridView1, false,"state");

	    var fields1=[
		    	{fieldName:"R001COR"}	//회사코드
		    	, {fieldName:"GRIDDAT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	   	//접수일자
		    	, {fieldName:"R001DAT"}	   	//접수일자
		    	, {fieldName:"R001JNO", dataType: "number"}	//접수번호
		    	, {fieldName:"R001HOS"}	//병원코드
		    	, {fieldName:"R001HNM"}	//병원명
		    	, {fieldName:"R001CHN"}	//차트번호
		    	, {fieldName:"R001NAM"}	//환자명
		    	, {fieldName:"R001AGE", dataType: "number"}	//나이
		    	, {fieldName:"R001SEXNM"}	//성별
		    	, {fieldName:"R001CLT","dataType": "datetime", "datetimeFormat": "yyyyMMdd"}	   	//접수일자
		    	, {fieldName:"R001GDT","dataType": "datetime", "datetimeFormat": "yyyyMMdd"}	   	//접수일자
		    	, {fieldName:"R001STS"}	//상태
		    	, {fieldName:"I_STS"}	//상태
		    	, {fieldName:"R001STSNM"}	//상태
		    	, {fieldName:"R001GCD"}	//상태
				, {fieldName:"I_LOGMNU"}
				, {fieldName:"I_LOGMNM"}
				, {fieldName:"I_DAT"}
				, {fieldName:"I_JNO"}
				, {fieldName:"I_REJ"}
		    	];
				
	    
	    dataProvider1.setFields(fields1);

	    var columns1=[
				    {name:"GRIDDAT",	fieldName:"GRIDDAT", 	renderer:{showTooltip: true}, header:{text:"접수일자"},	width:80,   styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				   , {name:"I_JNO",		fieldName:"R001JNO",	header:{text:"접수번호"},		width:80,  styles: {textAlignment: "far"} }
				   , {name:"R001HNM",	fieldName:"R001HNM",	header:{text:"병원명"},		width:170}
				   , {name:"R001NAM",	fieldName:"R001NAM",	header:{text:"수진자명"},		width:90}
				   , {name:"R001CHN",	fieldName:"R001CHN",	header:{text:"차트번호"},		width:90}
				   , {name:"R001AGE",	fieldName:"R001AGE",	header:{text:"나이"},			width:70,  styles: {textAlignment: "far"}}
				   , {name:"R001SEXNM",	fieldName:"R001SEXNM",	header:{text:"성별"},			width:80,  styles: {textAlignment: "center"}}
// 				   , {name:"R001CLT",	fieldName:"R001CLT",	header:{text:"검체채취일"},	width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
// 				   , {name:"R001GDT",	fieldName:"R001GDT",	header:{text:"검체의뢰일"},	width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
				   , {name:"R001STSNM",	fieldName:"R001STSNM",	header:{text:"접수상태"},			width:90,  styles: {textAlignment: "center"}}
	    ];
	    gridView1.setCheckableExpression("value['R001STS'] = 'S'", true);
	    gridView1.setColumns(columns1);
	 	gridView1.onDataCellClicked = function (grid, index) {
	 		var itemIndex = index.itemIndex;
	 		
			$("#I_DAT").val(grid.getValue(itemIndex, "R001DAT"));
			$("#I_JNO").val(grid.getValue(itemIndex, "R001JNO"));
			var title = grid.getValue(itemIndex, "R001HNM")+" "+grid.getValue(itemIndex, "R001NAM")+"["
			title += parseDate(grid.getValue(itemIndex, "R001DAT"))+"/"+grid.getValue(itemIndex, "R001JNO")+"] ";
			
			$("#I_HOSNM").val(title)
			$("#I_DATAROW").val(itemIndex);
			openPopup(2);
	 		dataResult2();
		};
	}
	
	function setGrid2(){
	    dataProvider2 = new RealGridJS.LocalDataProvider();
	    gridView2 = new RealGridJS.GridView("realgrid2");
	    gridView2.setDataSource(dataProvider2);
	    
	    setStyles(gridView2);
	    setGridBar(gridView2, false,"state");
	    
	    gridView2.setEditOptions({
	    	insertable:false,
	    	deletable:false,
	    	editable:true,
	    	readOnly:false
	    });

	    var fields2=[
			    	{fieldName:"R001COR"}	//회사코드
			    	,{fieldName:"GRIDDAT",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	   	//접수일자
			    	,{fieldName:"R001DTTM", dataType: "datetime", datetimeFormat: "yyyyMMddHHmmss"}	   	//의뢰일시
			    	,{fieldName:"R001DAT"}	   	//접수일자
			    	,{fieldName:"R001JNO"}	//접수번호
			    	,{fieldName:"R001GCD"}	//검사코드
			    	,{fieldName:"R001TCD"}	//검체코드
			    	,{fieldName:"I_TCD"}	//검체코드
			    	,{fieldName:"R001REJ"}	//거부사유
			    	,{fieldName:"F018OCD"}	//보험코드
			    	,{fieldName:"F010FKN"}	//검사항목
			    	,{fieldName:"F010TNM"}	//검체종류
			    	,{fieldName:"R001STS"}	//상태
			    	,{fieldName:"I_STS"}	//상태
			    	,{fieldName:"R001STSNM"}	//상태
			    	,{fieldName:"F010C01"}	//지정검체유무
					,{fieldName:"I_LOGMNU"}
					,{fieldName:"I_LOGMNM"}
					,{fieldName:"I_DAT"}
					,{fieldName:"I_JNO"}
					,{fieldName:"I_GCD"}
					,{fieldName:"I_REJ"}
		    	];
				
	    
	    dataProvider2.setFields(fields2);
	    var columnEditor =  {type :"dropDown" , dropDownPosition:"button", "domainOnly": true};
	    var columns2=[
		    	,{name:"GRIDDAT",	fieldName:"GRIDDAT",	header:{text:"접수일자"},		width:80,  editable:false,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				,{name:"R001DTTM",	fieldName:"R001DTTM", 	renderer:{showTooltip: true}, header:{text:"의뢰일시"},	width:130,   styles: {"datetimeFormat": "yyyy-MM-dd HH:mm:ss",textAlignment:"center"}}
		    	,{name:"F018OCD",	fieldName:"F018OCD",	header:{text:"보험코드"},		width:200,  editable:false, renderer:{ showTooltip: true }}
		    	,{name:"R001GCD",	fieldName:"R001GCD",	header:{text:"검사코드"},		width:80,  editable:false,  styles: {textAlignment: "center"}}
		    	,{name:"F010FKN",	fieldName:"F010FKN",	header:{text:"검사항목"},		width:160,  editable:false}
		    	,{name:"F010C01",	fieldName:"F010C01",	header:{text:"지정검체유무"},	width:90,  editable:false, styles: {textAlignment: "center"}}
		    	,{name:"I_TCD",		fieldName:"I_TCD",		header:{text:"검체종류"},		width:170,  editable:false,	lookupDisplay:true, values:SampValues, labels:SampLabels, editor:columnEditor}
		    	,{name:"R001REJ",	fieldName:"R001REJ",	header:{text:"거부사유"},		width:140,  editable:false,	lookupDisplay:true, values:RejReasonValues, labels:RejReasonLabels, editor:columnEditor}
		    	,{name:"R001STSNM",	fieldName:"R001STSNM",	header:{text:"상태"},			width:80,  editable:false, styles: {textAlignment: "center"}}
	    ];
	    
	    gridView2.setColumns(columns2);
	    gridView2.setColumnProperty("I_TCD", "editButtonVisibility", "always");
	    gridView2.setColumnProperty("R001REJ", "editButtonVisibility", "always");

	    gridView2.setCheckableExpression("value['R001STS'] = 'STANDBY'", true);
		gridView2.onCurrentRowChanged = function (grid, oldRow, newRow) {
			var provider = grid.getDataProvider();
			var columns = grid.getColumnNames();
			var I_STS = grid.getValue(newRow,"R001STS");
			var F010C01 = grid.getValue(newRow,"F010C01");
// 			var I_TCD = grid.getValue(newRow,"R001TCD");
			
			if(!isNull(I_STS))
			{
				if( I_STS=="STANDBY" ){
					
					if( F010C01 == "Y" ){
						grid.setColumnProperty("I_TCD","editable",true);
					}else{
						grid.setColumnProperty("I_TCD","editable",false);
					}
					grid.setColumnProperty("R001REJ","editable",true);
				}else{
					grid.setColumnProperty("I_TCD","editable",false);
					grid.setColumnProperty("R001REJ","editable",false);
				}
			}
			
		};
		gridView2.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
			 grid.checkItem(itemIndex, true);
		};
// 	    // 툴팁 기능
	    gridView2.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "F018OCD") {
	            tooltip = value;
	        }
	        return tooltip;
	    }	    
	}
	
	function dataResult1(){
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		dataProvider1.setRows([]);		
		gridView1.setDataSource(dataProvider1);
		
//  		if($('#I_HOS').val() == ""){
//  			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
//  			return false;
//  		}

		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		var interval = eDate - sDate;
		var day = 1000*60*60*24;
		
		if(strByteLength($("#I_CHN").val()) > 15){
			CallMessage("292", "alert", ["차트번호는 15 Byte"]);
			return false;
		}
		
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			CallMessage("273");	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
			return;
		}
		if (interval/day > 365){
   			CallMessage("274","alert");		//최대 1년 내에서 조회해주세요.
			return false;
		}
		if(sDate > eDate){
			CallMessage("229","alert",""); // 조회기간을 확인하여 주십시오.
			return;
		}
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/testReqList.do", formData);
	}

	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
	 	var msgCd = response.O_MSGCOD;
		
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/testReqList.do"){
	    	gridView1.cancel();
			gridView1.setAllCheck(false);
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
		
			var index = { dataRow : $("#I_DATAROW").val()}
			
			gridView1.setCurrent(index);
			dataResult2();
		}
		else if(strUrl == "/testReqDtl.do"){
	    	gridView2.cancel();
			gridView2.setAllCheck(false);
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
		}
		else if(strUrl == "/getCommCode.do"){
			var I_PSCD = "";
            $.each(resultList,function(k,v){
            	I_PSCD = response.I_PSCD;
            	if(response.I_PSCD =="REJ_REASON"){
            		RejReasonValues.push(v.VALUE);
            		RejReasonLabels.push(v.LABEL);
            	}
            });
		}
		else if(strUrl == "/getMC999DCommCode.do"){
			var I_PSCD = "";
            $.each(resultList,function(k,v){
            	SampValues.push(v.F999CD3);
            	SampLabels.push(v.F999NM2);
            });
		}
		else if(strUrl =="/sysTestReqCheck.do"){
			if(msgCd == "200"){
				fnSave();
			}
		}
		else if(strUrl == "/sysTestReqSave1.do"){
			CallMessage(msgCd,"alert","",search);	//승인을 완료하였습니다.
		}
		else if(strUrl == "/sysTestReqSave2.do"){
			CallMessage(msgCd,"alert","",dataResult1);	//승인을 완료하였습니다.
		}

		if(strUrl == "/getMC999DCommCode.do" || strUrl == "/getCommCode.do"){
	        if(SampLabels.length>0 && RejReasonLabels.length>0){
	        	setGrid2();
	        }
		}
	}
	function dataResult2(pmcd){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/testReqDtl.do", formData);
	}
	
	function openPopup(index)
	{
		var labelNm,strUrl,gridValus;
		if(index == 1){
			strUrl = "/hospSearchS.do";
			labelNm = "병원 조회";
		}else{
			strUrl = "/sysTestReq01.do";
			labelNm = $("#I_HOSNM").val()+ "검사 결과";
		}
		
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,gridValus){
		gridValus = {};
		if(url == "/hospSearchS.do"){
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
			
		}else if(url == "/sysTestReq01.do"){
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
			gridValus["I_DAT"] =  $('#I_DAT').val();
			gridValus["I_JNO"] =  $('#I_JNO').val();
			
		}
		iframe.gridViewRead(gridValus);
	}
	
	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(data[0] == "/hospSearchSList.do"){
			$('#I_HOS').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
	    }
	}
	
	function ApprovalValidation(dataRows)
	{
		var rtnValid = false;
		var RejReasonFlag = false;
		var RejReanMessage = false;
		var RejReasonValue = "";
		
		var focusCell = gridView2.getCurrent();
		if(dataRows != ""){
			$.each(dataRows, function(i, e) {
				
	 			if(isNull(dataProvider2.getValue(e, "R001REJ"))){
	 				CallMessage("201","alert",["거부사유는"]);				//{0} 필수 입력입니다.
	 				focusCell.itemIndex = e;
	 				focusCell.column = "R001REJ";
	 				focusCell.fieldName = "R001REJ";
	 				gridView2.setCurrent(focusCell);
	 				gridView2.showEditor();
	 				rtnValid = true;
	 				return false;
	 			}else{
	 				if(!RejReanMessage)
 					{
		 				RejReasonFlag = false;
		 				$.each(RejReasonValues, function(ReasonIdx, ReasonE) {
		 					if(ReasonE == dataProvider2.getValue(e, "R001REJ"))
		 					{
		 						RejReanMessage = false;
		 						RejReasonFlag = true;
		 						RejReasonValue = "";
		 					}
		 					if(!RejReasonFlag){
		 						RejReanMessage = true;
		 						RejReasonValue = dataProvider2.getValue(e, "R001REJ");
		 					}
		 				})
 					}
	 			}
			});
		}
		if(RejReanMessage){
			rtnValid = true;
			CallMessage("289","alert",[RejReasonValue]);				//{0} 필수 입력입니다.
		}
		
		
		return rtnValid;
	}
	var ApprovalGrid = 0;

	/* 의뢰목록관리 승인 */
	function ApprovalRow(flag){
		ApprovalGrid = flag;
		var	dataRows = null;
		if(flag=="3"){
			gridView2.commit();	
			dataRows = gridView2.getCheckedRows();
			var valid = ApprovalValidation(dataRows);
			if(!valid){
				if(dataRows.length == 0){
			    	CallMessage("243");	//선택내역이 없습니다.
					return;
				}
				CallMessage("260","confirm","",saveCheck);	//거부하시겠습니까?
			}
		}else{
			if(flag == "1"){
				dataRows = gridView1.getCheckedRows();
			}else{
				dataRows = gridView2.getCheckedRows();
			}

			if(dataRows.length == 0){
		    	CallMessage("243");	//선택내역이 없습니다.
				return;
			}
			CallMessage("259","confirm","",saveCheck);	//승인하시겠습니까?
			
		}
	}

	/*저장 가능 여부 체크후 저장 call */
	function saveCheck(rtnCall,Msg){
		var jsonRow = [];
		var	dataRows = null;
		var url,strSts = "APPROVAL";
		url = "/sysTestReqCheck.do";
		if(ApprovalGrid == 3){
			strSts = "REJECT";
		}
		var setValue = {
				I_LOGMNU: I_LOGMNU
				, I_LOGMNM:I_LOGMNM
				, I_STS:strSts
				, I_LOGEFG:strSts	//이벤트 종류 EVT_FLAG : APPROVAL(승인),REJECT(거절)
			};
// 		if(ApprovalGrid == 1){
// 			gridView1.commit();	
// 			dataRows = gridView1.getCheckedItems();
// 		}else{
// 			gridView2.commit();	
// 			dataRows = gridView2.getCheckedItems();
// 		}
		if(ApprovalGrid == 1){// 수진자 접수목록 승인
			gridView1.commit();	
			dataRows = gridView1.getCheckedItems();
			url = "/sysTestReqSave1.do";
		}else if(ApprovalGrid == 2){//추가 검사 항목 승인
			gridView2.commit();	
			dataRows = gridView2.getCheckedItems();
			url = "/sysTestReqSave2.do";
		}else if(ApprovalGrid == 3){//추가검사항목 거부
			gridView2.commit();	
			dataRows = gridView2.getCheckedItems();
			url = "/sysTestReqSave2.do";
		}
	    
		$.each(dataRows, function(i, e) {
			if(ApprovalGrid == 1){
				setValue = {
						I_LOGMNU: I_LOGMNU
						, I_LOGMNM:I_LOGMNM
						, I_STS:strSts
						, R001GCD:""
						, I_LOGEFG:strSts //이벤트 종류 EVT_FLAG : APPROVAL(승인)
						, I_DAT:gridView1.getValue(e, "R001DAT")
						, I_JNO:gridView1.getValue(e, "R001JNO")
						, I_REJ:""
					};
				dataProvider1.updateStrictRow(e, setValue);
			}else{ 
				setValue = {
					I_LOGMNU: I_LOGMNU
					, I_LOGMNM:I_LOGMNM
					, I_STS:strSts
					, I_LOGEFG:strSts  //이벤트 종류 EVT_FLAG : REJECT:거절
					, I_DAT:gridView2.getValue(e, "R001DAT")
					, I_JNO:gridView2.getValue(e, "R001JNO")
					, I_GCD:gridView2.getValue(e, "R001GCD")
					, I_REJ:gridView2.getValue(e, "R001REJ")
				};
				dataProvider2.updateStrictRow(e, setValue);
			}
		});
	    
		if(dataRows != ""){ 
 			$.each(dataRows, function(i, e) {
 				if(ApprovalGrid == 1){
					jsonRow.push(dataProvider1.getJsonRow(e));
 				}else{
					jsonRow.push(dataProvider2.getJsonRow(e));
 				}
 			});
 			var param = "I_LOGMNU="+I_LOGMNU+"&I_LOGMNM="+I_LOGMNM+"&JSONROW="+encodeURIComponent(JSON.stringify(jsonRow));
			ajaxCall3(url,param); // 수정
		}
	}


	function fnSave(){
		var jsonRow = [];
		var	dataRows = null;
		var url;
		if(ApprovalGrid == 1){// 수진자 접수목록 승인
			gridView1.commit();	
			dataRows = gridView1.getCheckedItems();
			url = "/sysTestReqSave1.do";
		}else if(ApprovalGrid == 2){//추가 검사 항목 승인
			gridView2.commit();	
			dataRows = gridView2.getCheckedItems();
			url = "/sysTestReqSave2.do";
		}else if(ApprovalGrid == 3){//추가검사항목 거부
			gridView2.commit();	
			dataRows = gridView2.getCheckedItems();
			url = "/sysTestReqSave2.do";
		}
	    
		if(dataRows != ""){ 
 			$.each(dataRows, function(i, e) {
 				if(ApprovalGrid == 1){
					jsonRow.push(dataProvider1.getJsonRow(e));
 				}else{
					jsonRow.push(dataProvider2.getJsonRow(e));
 				}
 			});
 			var param = "I_LOGMNU="+I_LOGMNU+"&I_LOGMNM="+I_LOGMNM+"&JSONROW="+encodeURIComponent(JSON.stringify(jsonRow));
			ajaxCall3(url,param); // 수정
		}
	}

	function ListSaveEnd(rtnCall){
		if(!rtnCall){
			CallMessage("244","alert",["추가검사 승인"]);		//{0}에 실패하였습니다.
		}else{
			if(ApprovalGrid == 1){
				search();
// 				dataResult1();
			}else{
				dataResult2();
			}
		}
	}
	/* 의뢰목록관리 승인 */
	</script>
</head>
<body>
	
<div class="content_inner">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"value="<%=menu_cd%>"  />
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>" />
		<input id="I_SERDAT" name="I_SERDAT" type="hidden" value="0"/>
		<input id="I_SERJNO" name="I_SERJNO" type="hidden" value="0"/>
		<input id="I_DAT" name="I_DAT" type="hidden"/>
		<input id="I_JNO" name="I_JNO" type="hidden"/>
		<input id="I_HOSNM" name="I_HOSNM" type="hidden"/>
		<input id="I_DATAROW" name="I_DATAROW" type="hidden"/>
		<!-- 검색영역 -->
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
								<label for="fr_date" class="label_basic">기간</label>
								<div class="select_area">
									<select id="I_REQDIV" name="I_REQDIV" class="form-control"></select>
								</div>
								<div class="btn_group">
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('1D')">1일</button>
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('3D')">3일</button>
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('1M')">1개월</button>
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('3M')">3개월</button>
								</div>
								<span class="period_area">
									 <label for="startDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT"   >
								</span>
								~
								<span class="period_area">
									 <label for="endDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT" >
								</span>
								
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10">
							<div class="srch_item">
								<label for="" class="label_basic">수진자 명</label>
								<input type="text" class="srch_put" id="I_NAM" name="I_NAM" maxlength="40" onkeydown="return enterSearch(event)" >
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">차트번호</label>
								<input type="text" class="srch_put" id="I_CHN" name="I_CHN" maxlength="15" onkeydown="return enterSearch(event)" >
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item hidden_srch"  id="hospHide"  style="display:none">
								<label for="" class="label_basic">병원</label>
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly" onClick="javascript:openPopup(1)" maxlength="5" onkeydown="return enterSearch(event)" class="srch_hos numberOnly"/>
	                             <input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup(1)"/>
								<%-- <input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>" /> --%>
								<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">접수상태</label>
								<div class="select_area">
									<select id="I_STS" name="I_STS" class="form-control"></select>
								</div>
							</div>
						</div>
					    
					    <div class="btn_srch_box">
							<button type="button" class="btn_srch">조회</button>
					    </div> 
					</div>
			    </div>
			</div>
	    </div>
		</form>
		<!-- 검색영역 end -->
	    
		<div class="container-fluid">
		    <div class="con_wrap col-md-6 col-sm-12">
			<div class="title_area">
			    <h3 class="tit_h3">수진자 접수 목록</h3>
                <div class="tit_btn_area">
                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:ApprovalRow('1')">승인</button>
                </div>
			</div>
			<div class="con_section overflow-scr">
			    <div class="tbl_type">
					<div id="realgrid1"  class="realgridH"></div>
			    </div>
			</div>
		    </div>
		    
		    <div class="con_wrap col-md-6 col-sm-12">
			<div class="title_area">
			    <h3 class="tit_h3">추가 검사 항목</h3>
                <div class="tit_btn_area">
                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:ApprovalRow('2')">승인</button>
                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:ApprovalRow('3')">거부</button>
                </div>
			</div>
			<div class="con_section overflow-scr">
			    <div class="tbl_type">
					<div id="realgrid2"  class="realgridH"></div>
			    </div>
			</div>
		    </div>
		</div>
			       
	</div>    
	<!-- content_inner -->       	
</body>
</html>



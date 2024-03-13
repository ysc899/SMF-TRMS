<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<!-- 검사 실시간 현황 css (하이차트 css 포함) -->
	<link rel="stylesheet" href="/css/realtime.css?v=3"> 
	
	<%@ include file="/inc/common.inc"%>		
	
	<title>코로나 결과보고</title>
	
	<%
		String Param_HOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_HOS"), "");
		String Param_FDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FDT"), "");
		String Param_TDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_TDT"), "");
	%>
	
	<script>
		
		var I_LOGMNU = "RECVREPORTCORONA";
		var I_LOGMNM = "코로나 결과보고";
		var Param_HOS = "<%=Param_HOS%>";
		var Param_FDT = "<%=Param_FDT%>";
	    var Param_TDT = "<%=Param_TDT%>";
		var s_uid = "<%= ss_uid %>";
	   	var s_ecf = "<%= ss_ecf %>";
	   	var s_hos = "<%= ss_hos %>";
		var s_nam = "<%= ss_nam %>";
		var s_paguen_hos_yn = "<%= ss_paguen_hos_yn %>";	/* 파견사원 병원유무 Flag */
		
		/*************** 접수 및 결과 보고 집계표 (건수) - grid ***************/
		var gridView5;
		var dataProvider5;
		var recvReportCoronaList_1;
		
		/*************** 접수 및 결과 보고 집계표 (상세) - grid ***************/
		var gridView1;
		var dataProvider1;
		var recvReportCoronaList_2;
		
	    $(document).ready( function(){
	    	
	    	$("#I_ID").val(s_uid);
	    	$("#I_ECF").val(s_ecf);
	    	$('#I_HOS').val(s_hos);
	    	$("#I_LOGMNU").val(I_LOGMNU);
			$("#I_LOGMNM").val(I_LOGMNM);
			
			// 병원 사용자 && 파견사원이 아닌 경우
	    	if(s_ecf != "E" && s_paguen_hos_yn == ""){
				$('#I_FNM').val(s_nam);
	    	}
			
			// 병원 검색 창은 Default 값이 hide 
			$('#DivHosSearch').hide();
			
			//사원일경우 or 파견사원 병원인 경우 >> 병원 검색 div 화면에 출력.
			if(s_ecf == "E" || s_paguen_hos_yn != ""){
				$('#DivHosSearch').show();
			}
			
			// ===============================================================================================================================
				
			dataProvider5 = new RealGridJS.LocalDataProvider();
			gridView5 = new RealGridJS.GridView("realgrid5");
			setStyles(gridView5);
			gridView5.setHeader({	// 헤더라인 높이 조절
		    	//height:50
		    	heightFill : "default"
		    })
		    gridView5.setDataSource(dataProvider5);
			gridView5.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: -1,
				emptyMessage: "조회된 데이터가 없습니다."
				//emptyMessage: ""
			});
			
			var fields5=[
			    	{fieldName: "HOSCD"},
			    	{fieldName: "HOSNM"},
			    	{fieldName: "JSCNT"},
			    	{fieldName: "GSCNT"},
			    	{fieldName: "NECNT"},
			    	{fieldName: "INCNT"},
			    	{fieldName: "POCNT"},
			    	{fieldName: "RSCNT"},
			    	{fieldName: "INGCNT"},
			    	{fieldName: "RECNT"}
		    	];
			
			dataProvider5.setFields(fields5);
			
			var columns5=[
					{name: "HOSCD", fieldName: "HOSCD", width: "45"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "병원코드"}}
			      ,	{name: "HOSNM", fieldName: "HOSNM", width: "255"	, styles: {textAlignment: "left"}	,    renderer:{showTooltip: true}, header: {text: "보건소명"}}
			      , {name: "GUNSU", type: "group", width: "200",header: { text: "건수"},
		    	       columns: [
		    	    	    {name: "JSCNT", fieldName: "JSCNT", width: "150"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "접수건수"}}
		 			      , {name: "GSCNT", fieldName: "GSCNT", width: "150" , styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "검사건수"}}
		    	        ]
		    	    }
			      , {name: "RESULTBOGOGUNSU", type: "group", width: "220",header: { text: "최종보고 결과 건수"},
		    	       columns: [
	     	    	        {name: "NECNT", fieldName: "NECNT", width: "130"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "양성"}}
		 			      , {name: "INCNT", fieldName: "INCNT", width: "130"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "미결정"}}
		 			      , {name: "POCNT", fieldName: "POCNT", width: "130"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "음성"}}
		 			      ,	{name: "RSCNT", fieldName: "RSCNT", width: "130" , styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "계"}}
		    	        ]
		    	    }
			      ,	{name: "INGCNT", fieldName: "INGCNT", width: "140"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "검사중"}}
			      ,	{name: "RECNT", fieldName: "RECNT", width: "140"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "재검중"}}
	    	];
			
		    gridView5.setColumns(columns5);	//컬럼을 GridView에 입력 합니다.
			
			gridView5.setStyles(basicGrayBlueSkin);
			
			gridView5.setStateBar({
			  visible: false  
			});
			
			gridView5.setOptions({
	            insertable: false,
	            appendable: false,
	            deletable : false,
	            readOnly  : true,
	            panel     : {visible : false},
				footer	  : {visible: false},
				keepNullFocus: true
	           });
			
			var options5 = { visible: false}
		    gridView5.setCheckBar(options5);
				
			
			
		    gridView5.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
	        
	        gridView5.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
	        
	        // ===============================================================================================================================
	     	   
			dataProvider1 = new RealGridJS.LocalDataProvider();
			gridView1 = new RealGridJS.GridView("realgrid1");
			setStyles(gridView1);
			gridView1.setHeader({
		    	//height:50
		    	heightFill : "default"
		    })
		    gridView1.setDataSource(dataProvider1);
			gridView1.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				//rowHeight: -1,
				eachRowResizable: true,
				emptyMessage: "조회된 데이터가 없습니다."
				//emptyMessage: ""
			});
			
			var fields1=[
				 {fieldName:"HOSCD"}
				,{fieldName:"HOSNM"}
		    	,{fieldName:"GUBUN"}
		    	//,{fieldName:"PATIENTNM"}
		    	,{fieldName:"NAM"}
		    	,{fieldName:"DAT"}
		    	,{fieldName:"JN12"}
		    	,{fieldName:"ETC"}
		    	,{fieldName:"ADDRESS"}
		    	,{fieldName:"PNO"}
		    	,{fieldName:"EGN"}
		    	,{fieldName:"RGN"}
		    	,{fieldName:"NGN"}
		    	];
			
			dataProvider1.setFields(fields1);
			
			var columns1=[
			     {name:"HOSCD"		, fieldName:"HOSCD"		, width: "20",	renderer:{showTooltip: true}, header:{text:"병원코드"}, styles: {textAlignment: "center"	, font : "나눔고딕, 13"},mergeRule: {criteria: "value"}}
			    ,{name:"HOSNM"		, fieldName:"HOSNM"		, width: "100",	renderer:{showTooltip: true}, header:{text:"보건소명"}, styles: {textAlignment: "left"		, font : "나눔고딕, 13"},mergeRule: {criteria: "value"}}
			    ,{name:"GUBUN"		, fieldName:"GUBUN"		, width: "20",	renderer:{showTooltip: true}, header:{text:"양성분류"}, styles: {textAlignment: "center"	, font : "나눔고딕, 13"},mergeRule: {criteria: "value"}}
			    //,{name:"PATIENTNM"	, fieldName:"PATIENTNM"	, width: "200",	renderer:{showTooltip: true}, header:{text:"환자목록"}, styles: {textAlignment: "near"		, font : "나눔고딕, 13", textWrap: "explicit"}}
			    ,{name:"NAM"		, fieldName:"NAM"		, width: "40",	renderer:{showTooltip: true}, header:{text:"환자명"}	, styles: {textAlignment: "near"		, font : "나눔고딕, 13", textWrap: "explicit"}}
			    ,{name:"DAT"		, fieldName:"DAT"		, width: "20",	renderer:{showTooltip: true}, header:{text:"의뢰일"}	, styles: {textAlignment: "center"		, font : "나눔고딕, 13", textWrap: "explicit"}}
			    ,{name:"JN12"		, fieldName:"JN12"		, width: "20",	renderer:{showTooltip: true}, header:{text:"생년월일"}	, styles: {textAlignment: "center"		, font : "나눔고딕, 13", textWrap: "explicit"}}
			    ,{name:"ADDRESS"	, fieldName:"ADDRESS"	, width: "40",	renderer:{showTooltip: true}, header:{text:"실거주지"}	, styles: {textAlignment: "near"		, font : "나눔고딕, 13", textWrap: "explicit"}}			    
			    ,{name:"ETC"		, fieldName:"ETC"		, width: "40",	renderer:{showTooltip: true}, header:{text:"기타기록"}	, styles: {textAlignment: "near"		, font : "나눔고딕, 13", textWrap: "explicit"}}			    
			    ,{name:"PNO"		, fieldName:"PNO"		, width: "40",	renderer:{showTooltip: true}, header:{text:"핸드폰번호"}	, styles: {textAlignment: "center"		, font : "나눔고딕, 13", textWrap: "explicit"}}			    
			    , {name: "RESULTCTVALUE", type: "group", width: "100",header: { text: "CT Value"},
		    		columns: [
	     	    		{name: "EGN"	, fieldName: "EGN"	, width: "33"	, styles: {textAlignment: "center", font : "나눔고딕, 13"}	,    renderer:{showTooltip: true}, header: {text: "E gene"}}
	     	    		,{name: "RGN"	, fieldName: "RGN"	, width: "33"	, styles: {textAlignment: "center", font : "나눔고딕, 13"}	,    renderer:{showTooltip: true}, header: {text: "RdRP/S gene"}}
	     	    		,{name: "NGN"	, fieldName: "NGN"	, width: "33"	, styles: {textAlignment: "center", font : "나눔고딕, 13"}	,    renderer:{showTooltip: true}, header: {text: "N gene"}}
	     	    	    /* ,{name:"EGN"	, fieldName:"EGN" 		, width: "7%"	, renderer:{showTooltip: true}, header:{text:"E"}, styles: {textAlignment: "left", font : "나눔고딕, 13"}}
	     				,{name:"RGN"	, fieldName:"RGN"		, width: "7%"	, renderer:{showTooltip: true}, header:{text:"환자목록"}, styles: {textAlignment: "left", font : "나눔고딕, 13"}}
	     				,{name:"NGN"	, fieldName:"NGN" 		, width: "7%"	, renderer:{showTooltip: true}, header:{text:"환자목록"}, styles: {textAlignment: "left", font : "나눔고딕, 13"}} */
		    	   	]
		    	}
	    	];
						
		    gridView1.setColumns(columns1);	//컬럼을 GridView에 입력 합니다.
			
			gridView1.setStyles(basicGrayBlueSkin);
			
			gridView1.setStateBar({
			  visible: false  
			});
			
			gridView1.setOptions({
	            insertable: false,
	            appendable: false,
	            deletable : false,
	            readOnly  : true,
	            panel     : {visible : false},
				footer	  : {visible: false},
				keepNullFocus: true
	        });
			
			var options1 = { visible: false}
		    gridView1.setCheckBar(options1);
			
		    gridView1.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
	        
	        gridView1.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	}); 
	        
	     	// ===============================================================================================================================
	    	 
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
			
			var yearAgo = new Date();
	        yearAgo.setDate(yearAgo.getDate() - 1);
	        $("#I_FDT").datepicker('setDate',yearAgo);
	        $("#I_TDT").datepicker('setDate',new Date);
	        
	     	// 처음 수진자별 결과조회 화면이 열릴 때, 파견사원 병원인 경우는 병원코드 입력값을 빈값으로 셋팅한다.
	        // (이유는 파견사원 병원은 1개 계정으로 N개 병원 검사결과 데이터를 조회해야하므로,
	        // 병원코드 값이 존재하지 않아야 N개 병원 검사결과 데이터가 모두 조회 가능하다.)
	        if(s_paguen_hos_yn != ""){
		        $("#I_HOS").val('');        	
	        }
			
	        /** 메인에서 들어올경우 조회 조건 셋팅 **/
		   	if(!isNull(Param_HOS)){		// 사원/파견사용자인 경우
		   		
				$("#I_HOS").val(Param_HOS);     // 병원코드
				
				Param_FDT = Param_FDT.substring(0,4)+"-"+Param_FDT.substring(4,6)+"-"+Param_FDT.substring(6,8); 
		    	Param_TDT = Param_TDT.substring(0,4)+"-"+Param_TDT.substring(4,6)+"-"+Param_TDT.substring(6,8);
		    	
		    	$("#I_FDT").datepicker('setDate', Param_FDT);		// 접수일자 - 시작
				$("#I_TDT").datepicker('setDate', Param_TDT);	 	// 접수일자 - 종료
					
		    }
		});
	    
	    function init(){
	    	$('#I_FKN').val("");
	    	$('#I_GAD').val("");
	    	
	    }
	    
	 	// 데이터 가져오기
		function dataResult1(){
			callLoading(null,"on");
			
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/recvReportCoronaList.do", formData,false);
		}
		
		// ajax 호출 result function
		function onResult(strUrl,response){
			
			if(strUrl == "/recvReportCoronaList.do"){
					
				callLoading(null,"off");
				
				if(!isNull( response.recvReportCoronaList_1) || !isNull( response.recvReportCoronaList_2)){
					
					// 접수 및 결과 보고 집계표 (건수)
					recvReportCoronaList_1 =  response.recvReportCoronaList_1;
					dataProvider5.setRows(recvReportCoronaList_1);
					gridView5.setDataSource(dataProvider5);
					
					// 접수 및 결과 보고 집계표 (상세)
					recvReportCoronaList_2 =  response.recvReportCoronaList_2;
					dataProvider1.setRows(recvReportCoronaList_2);
					gridView1.setDataSource(dataProvider1);
					
					// 데이터 길이에 맞추어 열 높이 자동조정
					gridView1.fitRowHeightAll(0, true);
					
				}else{
					var messge = "접수일자 ";// 보고일자 , 접수일자
					messge += $("#I_FDT").val() + " ~ " + $("#I_TDT").val() +" ";
					
			    	CallMessage("291","alert",[messge]);//결과가 존재하지 않습니다.
				}
			}
		}
		
		//조회
		function search(){
			
			//병원코드 입력 후 조회 할 경우
	 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
	 			
				if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
					var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
					$('#I_FNM').val(I_FNM);
				}
	 		
				// 2020.04.21 (수)
	 			// 검색초기화 후, 조회를 할 경우
	 			// 파견사원인지 체크 후, 병원/사원 값을 로그인 유저 정보로 셋팅되도록 한다.
	 			if(s_paguen_hos_yn != ""){
	 				$('#I_ECF').val(s_ecf);
	 			}
			}else{
				
				// 병원코드 미입력되었을 경우, 병원이름도 미입력 상태로 바꿈
				if($('#I_HOS').val() == ""){
					$('#I_FNM').val("");
				}else{
					// 2020.04.21 (수)
					// 병원코드 입력시 병원/사원 정보를 사원으로 변경하여
					// 사원처럼 입력한 병원코드 1곳 데이터를 조회하도록 한다.
		 			if(s_paguen_hos_yn != ""){
		 				$('#I_ECF').val("E");
					}
				}
			}
			
	 		gridView5.cancel();
	 		gridView1.cancel();
			dataProvider5.clearRows();
			dataProvider1.clearRows();
			
			if(!searchValidation()) {return;}
						
			var url = "";
			
			dataResult1();
		}
		
		function searchValidation(){
			
			var sDate = new Date($("#I_FDT").val());
			var eDate = new Date($("#I_TDT").val());
			var interval = eDate - sDate;
			var day = 1000*60*60*24;
			
			// 유효성검사 - 최대 검색 기간 체크 !!
			var now = new Date();
		    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
			if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
				CallMessage("301","alert",["10"]);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
				return ;
		   	}
			
			//if (interval/day > 365){
			if (interval/day > 30){
	   			CallMessage("298","alert");		//최대 30일 내에서 조회해주세요.
				return false;
			}
			
			// 변경전 : 검색조건에 병원코드가 입력되지 않으면 검색이 불가능함.
			// 변경후 : 검색조건에 병원코드가 입력되지 않으면서 사원인 경우만 검색이 불가능 하도록 변경. 파견사원의 경우, 병원코드가 비어있어도 검색이 되도록 하기 위해서.
			if($('#I_HOS').val() == "" && $('#I_ECF').val() =="E" && s_paguen_hos_yn == ""){
				CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
				return false;
			}
			
			if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
				CallMessage("273", "alert");
			   return false;
			}
			
			if(sDate > eDate){
				CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
				return false;
			}
		      
			return true;
			
		}
			
		// 팝업 open
		function openPopup(str){
			var formData = $("#searchForm").serialize();
			
			if(str == "3"){
				fnOpenPopup("/hospSearchS.do","병원조회",formData,callFunPopup);
			}
		
			/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			//fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
		}
		
		function  callFunPopup(url,iframe,formData){
			if(url == "/hospSearchS.do"){
				
				formData = {};
				formData["I_HOS"] =  $('#I_HOS').val();
				formData["I_FNM"] =  $('#I_FNM').val();
	
				iframe.gridViewRead(formData);
			}
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
		
		function exportGrid(){
			var totalCnt_1 = dataProvider5.getRowCount();
			var totalCnt_2 = dataProvider1.getRowCount();
			
			if(totalCnt_1 == 0 && totalCnt_2 == 0){
		    	CallMessage("268","alert");	// 저장하시겠습니까?
				return;
			}
			CallMessage("257","confirm", "", gridExport);
		}
		
		function gridExport(){
			RealGridJS.exportGrid({
			    type: "excel",
			    target: "local",
			    lookupDisplay : true,
			    fileName: s_nam+"_코로나결과보고_"+getToday()+".xlsx",
			    showProgress: true,
			    applyDynamicStyles: false,
			    progressMessage: "엑셀 Export중입니다.",
			    indicator: "default",
			    header: "visible",
			    footer: "default",
			    compatibility: "2010",
			    done: function () {},  //내보내기 완료 후 실행되는 함수			    
			    exportGrids:[
			        { 
			            grid:gridView5, //그리드 변수명
			            sheetName:"병원목록" // 다른 그리드와 중복되어서는 안된다.
			        }, {
			            grid:gridView1,
			            sheetName:"환자목록"
			        }
		      	]
			});
		}
	
		function getToday(){
		    var date = new Date();
		    var year = date.getFullYear();
		    var month = pad(("0" + (1 + date.getMonth())).slice(-2),2);
		    var day = pad(("0" + date.getDate()).slice(-2),2);
		    var hh = pad(date.getHours(),2);
		    var mm = pad(date.getMinutes(),2);
		    var ss = pad(date.getSeconds(),2);	    

		    return year + month + day + hh + mm + ss;
		}
		
		function pad(number, length){
			var str = number.toString();
			while(str.length < length){
				str = '0' + str;
			}
			return str;
		}
		
		
	</script>
</head>
<body>
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
		<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->
		<input id="I_ID" 		name="I_ID" 	type="hidden" />	<!-- 로그인한 사용자 ID -->
		<input id="I_ECF" 		name="I_ECF" 	type="hidden" />	<!-- 사원/파견사원 구분 -->
		
		<div class="content_inner" >
           	<!-- 검색영역 -->
               <div class="container-fluid">
					<div class="con_wrap col-md-12 srch_wrap">
						<div class="title_area open">
							<h3 class="tit_h3">검색영역</h3>
						</div>
						<div class="con_section row">
							<div class="main_srch_box">
								<div class="srch_box_inner m-t-10">
									<div class="col-md-12">
										<div  class="group4" >
											<label for="" class="label_basic" style="float: left;">접수일자</label>
											<div  class="group4" >
												&nbsp;
												<span class="period_area">
													<label for="I_FDT" class="blind">날짜 입력</label>
													<input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT" >
												</span>
												 ~ 
												<span class="period_area">
													<label for="I_TDT" class="blind">날짜 입력</label>
													<input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT" >
												</span>
											</div>
									</div>
								</div>
								<div class="srch_box_inner m-t-10" >
									<div class="srch_item srch_item_v5 hidden_srch"  id="DivHosSearch" style="display: none">
										<label for="" class="label_basic">병원</label>
										&nbsp;<input id="I_HOS" name="I_HOS" type="text"  class="srch_put numberOnly srch_hos"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup(3)" maxlength="5" class="numberOnly"/>
										<input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly" onclick="javascript:openPopup(3)">
									</div>
								</div>
								<div class="btn_srch_box m-t-10" style="width: 55%;">
									<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
									<button type="button" class="btn_srch" onClick="javascript:exportGrid()">엑셀다운</button>
								</div>
							</div>
							</div>
						</div>
					</div>
				</div>
            	<!-- 검색영역 end -->     
                
                <div class="container-fluid">
                    <div class="con_wrap col-md-12 ">
                        <div id="realgrid_realtime_view_1" class="container-fluid" border:2px;">
							<div class="con_wrap_realtime col-md-12 col-sm-12" style="margin-left:-30px;">
					        	
					           	<div style="float:left;width:100%;">
					           		<div class="title_area_realtime_4">
						            	<h3 class="tit_h3">■ 접수 및 결과 보고 집계표</h3>
						          	</div>
				 		          	
						          	<div class="con_section overflow-scr">
						          		<div id="realgrid5" style="height:200px;"></div>
						          		<div>&nbsp;</div>
						          		<div id="realgrid1" style="height:350px;" ></div>
						          	</div>	
								</div>		
							</div>
						</div>
                    </div>
                </div>                 
            </div>    
            <!-- content_inner -->       	
     </form>
     
</body>
</html>




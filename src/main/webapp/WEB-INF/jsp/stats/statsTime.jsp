<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>시계열</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	
	<%
		String I_CHN = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_CHN"), "");
		String I_NAM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_NAM"), "");	
		String I_FNM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FNM"), "");
		String I_HOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_HOS"), "");
		String I_DAT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_DAT"), "");
		String I_GCD = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_GCD"), "");
		String I_ACD = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_ACD"), "");
		String I_FKN = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FKN"), "");
    %>
	<script type="text/javascript" src="/plugins/chart/Chart.bundle.min.js"></script>
	<script type="text/javascript">
	
	var pscd = "";
	var I_EKD = "INIT_PW";
	var gridView,gridView2;
	var dataProvider,dataProvider2;
	var fields = [];
	var columns = [];
	var gridValues = [];
	var gridLabels = [];
	var myChart;
	var count = 0;		// 페이지 확인 0이면 첫 페이지
	var resultList;
	
	$(document).ready( function(){
	    if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }
/* 	    $('#I_FNM').change(function(){
	    	$('#I_HOS').val('');
	    }); */
		
		$("#I_FDT").datepicker({
			dateFormat: 'yy-mm-dd',
			maxDate: $('#I_TDT').val(),
			onSelect: function(selectDate){
				$('#I_TDT').datepicker('option', {
					minDate: selectDate
				});
			}
		});	
		$("#I_TDT").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: $('#I_FDT').val(),
			onSelect : function(selectDate){
				$('#I_FDT').datepicker('option', {
					maxDate: selectDate
				});
			}		
		});
		
		$(".fr_date").datepicker("setDate", new Date());
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 1);
        $("#I_FDT").datepicker('setDate', yearAgo);
		
	    //등록일
		var term_div = getCode(I_LOGMNU, I_LOGMNM, "TERM_DIV", 'N');
		$('#I_DGN').html(term_div);
		jcf.replace($("#I_DGN"));
		
		// 검사분야
		var test_cls = getCode(I_LOGMNU, I_LOGMNM, "TEST_CLS", 'Y');
		$('#I_HAK').html(test_cls);
		jcf.replace($("#I_HAK"));
		
		// 검사 상태
		var test_sts = getCode(I_LOGMNU, I_LOGMNM, "TEST_STAT", 'Y');
		$('#I_STS').html(test_sts);
		jcf.replace($("#I_STS"));
		
		// 결과 구분
		var chkDiv = getCheckBox(I_LOGMNU, I_LOGMNM, "RESULT_DIV");
		$("#RGN").append(chkDiv);
		

		// 기간 검색 구분 - 1일,3일,1개월,3개월
		var buttonList = getTerm(I_LOGMNU, I_LOGMNM, "S_TERM");
		$('#I_S_TERM').append(buttonList);

		 if(isNull("<%=I_CHN%>".trim())){
				<%
		  		Map<String, Object> QuickParam = new HashMap<String, Object>();
		  		if(paramList.size()>0){
		  			for(int idx=0;idx<paramList.size();idx++)
		  			{
		  				QuickParam = paramList.get(idx);
		  				String RCV = QuickParam.get("S007RCV").toString();
		  				String RCD = QuickParam.get("S007RCD").toString();
		  				String VCD = QuickParam.get("S008VCD").toString();
		  				if(!"".equals(QuickParam.get("S008VCD"))){
				%>
						if("<%=RCV%>" == "S_TERM"){
							changeDT("<%=VCD%>");
						}else{
							if("<%=RCV%>" == "TERM_DIV"){
								if(isNull("<%=VCD%>")){
									$("#<%=RCD%>").val("D");
								}else{
									$("#<%=RCD%>").val("<%=VCD%>");
								}
							}else if("<%=RCV%>" == "RESULT_DIV"){
								$("input[id=I_RGN<%=VCD%>]:input[value=<%=VCD%>]").prop('checked',true);
							}else{
								$("#<%=RCD%>").val("<%=VCD%>");
							}
					  		jcf.replace($("#<%=RCD%>"));
						}
			<%
		  				}
		  			}
		  		}
		  	%>
		} 

//		var graph_kind = getCode(I_LOGMNU, I_LOGMNM, "GRAPH_KIND", 'N');
//		$('#G_KIND').html(graph_kind);
		
		graph("myChart", 'bar');
		setGrid2();
		$('#I_PNN_P').addClass("disabled");
		$('#I_PNN_N').addClass("disabled");
		
		getGridCode();
	});
	
    function getGridCode(){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD=GENDER";
		ajaxCall("/getCommCode.do", formData);
	}
    
	function setGrid1(){
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })
    
	    fields=[
  		 	  {fieldName:"DAT"}		//접수일자
			, {fieldName:"JNO"}		//접수번호
			, {fieldName:"ETC"}		//검체번호
			, {fieldName:"NAM"}		//수진자명
			, {fieldName:"SEX"}		//성별
			, {fieldName:"AGE", dataType: "number"}		//나이
			, {fieldName:"CHN"}		//차트번호
			, {fieldName:"GCD"}		//검사코드
			, {fieldName:"ACD"}		//부속코드
			, {fieldName:"GNM"}		//검사명
			, {fieldName:"VAL"}		//소수점 갯수
			, {fieldName:"RLT"}		//결과
			, {fieldName:"RFG"}		//결과-수치,문자 구분
			, {fieldName:"LHK"}		//하이/로우
			, {fieldName:"REF"}		//참고치
			, {fieldName:"REF1"}		//참고치1
			, {fieldName:"REF2"}		//참고치2
			, {fieldName:"BDT"}		//보고일자
			, {fieldName:"STSD"}		//상태
			, {fieldName:"I_LOGMNU"}	//메뉴코드
			, {fieldName:"I_LOGMNM"}	//메뉴명
	    ];
	    
	    columns=[
	    	  {name:"DAT",		fieldName:"DAT",		header:{text:"접수일자"}, styles: {textAlignment: "center"}, editable: false,	width:80, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3"}
	    	, {name:"JNO",		fieldName:"JNO",		header:{text:"접수번호"},	editable: false, width:80 }
	    	, {name:"CHN",		fieldName:"CHN",		header:{text:"차트번호"},	editable: false,	width:110 }
	    	, {name:"NAM",		fieldName:"NAM",		header:{text:"수진자"}, editable: false,	width:80 }
	    	, {name:"SEX",		fieldName:"SEX",		header:{text:"성별"}, styles: {textAlignment: "center"},	editable: false, width:40, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "textReadOnly":true}}//,dropDownWhenClick :true
	    	, {name:"AGE",		fieldName:"AGE",		header:{text:"나이"}, styles: {textAlignment: "far"}, editable: false,	width:40 }
	    	, {name:"ETC",		fieldName:"ETC",		header:{text:"검체번호"}, renderer:{ showTooltip: true },	editable: false, width:120 }
	    	, {name:"GNM",		fieldName:"GNM",		header:{text:"검사명"}, renderer:{ showTooltip: true }, editable: false,	width:120 }
	    	, {name:"RLT",		fieldName:"RLT",		header:{text:"결과"},	 editable: false,	width:120 }
	    	, {name:"LHK",		fieldName:"LHK",		header:{text:"H/L"},styles: {textAlignment: "center"},	 editable: false,	width:40 }
	    	, {name:"REF",		fieldName:"REF",		header:{text:"참고치"}, styles: { textWrap:"explicit"}, renderer:{ showTooltip: true }, editable: false, width:120 }
	    	, {name:"BDT",		fieldName:"BDT",		header:{text:"보고일자"},styles: {textAlignment: "center"}, editable: false, width:100, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3" }
	    	, {name:"STSD",		fieldName:"STSD",		header:{text:"검사진행"},	 editable: false,	width:100 }
	    ];

	    //체크바 제거
	    var checkBar = gridView.getCheckBar();
	    checkBar.visible = false;
	    gridView.setCheckBar(checkBar);
	    
	    //상태바 제거
	    var StateBar = gridView.getStateBar();
	    StateBar.visible = false;
	    gridView.setStateBar(StateBar);
	    
	    gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "REF") {
	        	tooltip = grid.getValue(itemIndex, "REF2");
	        }
	        return tooltip;
	    }
	    
	    dataProvider.setFields(fields);
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    
	    gridView.onDataCellClicked = function (grid, index) {
		 	dataResultChart(grid.getValues(index.itemIndex));
		 	
	 	};
        var I_HOS = "<%=I_HOS%>";
        var I_FNM = "<%=I_FNM%>";
        if(!isNull(I_HOS)){				// 사원
			$("#I_HOS").val(I_HOS);		// 병원코드
			$("#I_FNM").val(I_FNM);		// 병원이름
        }
        
        var I_CHN = "<%=I_CHN%>".trim();
        var I_DAT = "<%=I_DAT%>";
        var I_NAM = "<%=I_NAM%>".trim();
        var I_GCD = "<%=I_GCD%>";
        var I_ACD = "<%=I_ACD%>";
        var I_FKN = "<%=I_FKN%>";
        
        //console.log(I_CHN);
        if(!isNull(I_CHN)){				// 사원, 병원사용자
			$("#I_CHN").val(I_CHN);     // 차트번호
			$("#I_NAM").val(I_NAM);     // 수진자명
			$("#I_FDT").datepicker('setDate', I_DAT);		// 접수일자 - 시작
			$("#I_TDT").datepicker('setDate', I_DAT);		// 접수일자 - 종료
			if(isNull(I_ACD)){
				I_ACD = "  ";
			}
			$('#I_GAD').val(I_GCD + I_ACD);
			//console.log($('#I_GAD').val());
			$('#I_FKN').val(I_FKN);
			//ajaxCall("/patSearchS.do", "I_LOGMNU="+I_LOGMNU+"&I_LOGNMN="+I_LOGMNM+"&I_CHN="+I_CHN+"&I_NAM="+I_NAM+"&I_HOS="+I_HOS);
			$.ajax({
				 url : "/patSearchSList.do"
				, type : "post"
				, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_CHN" : I_CHN, "I_NAM" : I_NAM, "I_HOS" : I_HOS}
				, dataType: 'json'
				, async:false
				, success : function(response){
					var resultList =  response.resultList;
					if(!isNull(resultList)){
						$("#I_BDT").val(resultList[0].F100BDT);     // 수진자 생일
					}
				}
			});
			
			if(isNull($("#I_BDT").val())){
				CallMessage("269", "alert");	
			}else{
				search();
			}
        }
        
	}
	
	function setGrid2(data){
	    dataProvider2 = new RealGridJS.LocalDataProvider();
	    gridView2 = new RealGridJS.GridView("realgrid2");
	    gridView2.setDataSource(dataProvider2);
	    setStyles(gridView2);
	    
	    gridView2.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })
    
	    var fields2=[
	    		  {fieldName:"DAT"}			//병원 코드
				, {fieldName:"RLT"}			//차트번호
				, {fieldName:"I_LOGMNU"}	//메뉴코드
				, {fieldName:"I_LOGMNM"}	//메뉴명
	    	];
	    
	    dataProvider2.setFields(fields2);

	    var columns2=[
	    	  {name:"DAT",		fieldName:"DAT",		header:{text:"접수일자"}, styles: {textAlignment: "center"}, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3",			editable: false,	width:120}
	    	, {name:"RLT",		fieldName:"RLT",		header:{text:"결과"},		editable: true,		width:260 }//,dropDownWhenClick :true
	    ];
	    
	    //체크바 제거
	    var checkBar = gridView2.getCheckBar();
	    checkBar.visible = false;
	    gridView2.setCheckBar(checkBar);
	    
	    //상태바 제거
	    var StateBar = gridView2.getStateBar();
	    StateBar.visible = false;
	    gridView2.setStateBar(StateBar);
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView2.setColumns(columns2);
	    
	    //그리드 너비 보다 컬럼 너비가 작으면 width비율로 자동 증가
	    gridView2.setDisplayOptions({
			fitStyle: "even"
	    });
	    
	}
	
	function dataResult(data){
		$(".graph_tit").html("");
		resultList = "";
		var formData = $("#searchForm").serialize();
		if(isNull(data)){
			data = "&I_DAT="+"";
			data += "&I_JNO="+"";
			data += "&I_GCD="+"";
			data += "&I_ACD="+"";
		}
		formData += data;
		//console.log(formData);
		ajaxCall("/statsTimeList.do", formData);
	}
	
	function onResult(strUrl,response){
		if(!isNull(response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/getCommCode.do"){
            $.each(resultList,function(k,v){
                gridValues.push(v.VALUE);
                gridLabels.push(v.LABEL);
            });
            setGrid1();
		}
		
		if(strUrl == "/statsTimeList.do"){
	        if(!isNull(resultList)){
	        	$.each(resultList,function(k,v){
		        	resultList[k].REF = v.REF.trim();
		        	resultList[k].REF2 = v.REF1.replace(/@@/gi,"\n").trim();
		        });	
	        }
	        
			dataProvider.setRows(resultList);	
			gridView.setDataSource(dataProvider);
			
			if(dataProvider.getRowCount() < $('#I_ICNT').val()){					// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
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
			
			if(!isNull(resultList)){
				dataResultChart(resultList[0]);
			}else{
				var msg = $("#I_FDT").val() +"~"+$("#I_TDT").val() +"기간 안에 <br>"+$("#I_NAM").val()+" ("+$("#I_CHN").val()+")의 "
					CallMessage("291", "alert", [ msg ]);
					//				CallMessage("286", "alert");
				}
			}

			//console.log(resultList);
			//console.log(dataSort(resultList));
			//console.log(resultList);
			if (strUrl == "/statsTimeGrp.do") {
				chartGrid(resultList);
			}

		}

		function chartGrid(resultList) {
			//console.log(resultList);
			//var dataList = resultList.slice();
			var dataList = new Array();
			for(var i=0; i<resultList.length; i++ ){
				//console.log(resultList[i].RLT);
				if(!isNull(resultList[i].RLT.trim())){
					dataList.push(resultList[i]);
					//dataList.remove(i);
				}
			}
			
			if (!isNull(dataList)) {
				var check = true;
				for (var i = 0; i < dataList.length; i++) {
					if (isNaN(dataList[i].RLT)) {
						check = false;
						break;
					}
				}
			}

			if ($('#I_SORT').val() == "ASC") {
				dataList = dataSort(dataList);
			}
			//console.log(dataList);
			//console.log(resultList);

			if (check) { // ture 일경우 수치 = 그래프 출력, fasle 일경우 문자 = 그리드 출력
				$('#chart').show();
				$('#grid').hide();
				dataChart(dataList);
			} else {
				$('#grid').show();
				gridView2.resetSize();
				$('#chart').hide();
				dataResult2(dataList);
			}

		}

		function dataSort(data) {
			var dataR = data.reverse();

			return dataR;
		}

		function sortChange() {
			if (!isNull(resultList)) {
				chartGrid(resultList);
			}
		}

		function dataResultChart(data) {
			// 병원 코드, 생일, 차트번호, 읽을건수=10?, 접수일자, 접수번호, 검사코드, 부속코드
			// 그리드 - 접수일자-DAT, 접수번호-JNO, 검사코드-GCD, 부속코드-ACD
			// form - 차트번호, 병원코드, 생일, 읽을건수=10건 고정
			var formData = $("#searchForm").serialize();
			formData += "&I_DAT=" + data.DAT;
			formData += "&I_JNO=" + data.JNO;
			formData += "&I_GCD=" + data.GCD;
			formData += "&I_ACD=" + data.ACD;
			formData += "&I_ICNT_CHART=" + $('#I_ICNT_CHART').val();
			ajaxCall("/statsTimeGrp.do", formData);

		}

		function dataResult2(data) {
			var values = [];
			var value;

			dataProvider2.setRows(data);
			gridView2.setDataSource(dataProvider2);
		}

		function dataChart(data) {
			var dts = [];
			var labelHeader = [];
			var RLT = [];
			var HIV = [];
			var LOV = [];
			var DAT = [];
			var date;
			$(".graph_tit").html(data[0].GNM);
			//console.log(data);
			for (var i = 0; i < data.length; i++) {
				RLT.push(data[i].RLT);
				HIV.push(data[i].HIV);
				LOV.push(data[i].LOV);
				//console.log(data[i].DAT.toString().substr(0,4));
				DAT.push(data[i].DAT.toString().substr(0, 4) + "-"
						+ data[i].DAT.toString().substr(4, 2) + "-"
						+ data[i].DAT.toString().substr(6, 2));
			}

			if (data.length == "1") {
				HIV.unshift(data[0].HIV);
				RLT.unshift(0);
				LOV.unshift(data[0].LOV);
				DAT.unshift("");
				HIV.push(data[0].HIV);
				LOV.push(data[0].LOV);
				RLT.push(0);
				DAT.push("");
			}
			
			var data1 = {
				label : "결과 값",
				data : RLT, // db 데이터
				type : 'bar',
				borderWidth : 1,
				borderColor : window.colors[8],
				backgroundColor : window.colors[8]
			};
			var data2 = {
				label : "참고치 최고치",
				data : HIV,
				type : 'line',
				borderColor : 'rgb(255, 0, 0)',
				backgroundColor : 'transparent'
			};
			var data3 = {
				label : "참고치 최저치",
				data : LOV,
				type : 'line',
				borderColor : 'rgb(0, 0, 255)',
				backgroundColor : 'transparent'
			};

			var options = {
				    scales: {
				        xAxes: [{
				            barThickness : 40
				        	//barPercentage: 0.4
				        }]
				    },
			        elements: {
			            line: {
			                tension: 0, // disables bezier curves
			            }
			        }
				}
			chart.options = options;
			
			dts.push(data2);
			dts.push(data3);
			dts.push(data1);
			labelHeader = DAT;
			chart.data.labels = labelHeader;
			chart.data.datasets = dts;
			
	 		Chart.plugins.register({			// 차트에 수치 데이터 보여주기
				afterDatasetsDraw: function(chart) {
					var ctx = chart.ctx;

					chart.data.datasets.forEach(function(dataset, i) {
						var meta = chart.getDatasetMeta(i);
						if (!meta.hidden) {
							if(dts[i].type == "bar"){
								meta.data.forEach(function(element, index) {
									// Draw the text in black, with the specified font
									ctx.fillStyle = 'rgb(0, 0, 0)';
		
									var fontSize = 13;
									var fontStyle = 'normal';
									var fontFamily = 'Helvetica Neue';
									ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
		
									// Just naively convert to string for now
									var dataString = dataset.data[index] + "";
		
									// Make sure alignment settings are correct
									ctx.textAlign = 'center';
									ctx.textBaseline = 'middle';
		
									var padding = 2;
									var position = element.tooltipPosition();
									ctx.fillText(dataString, position.x+15 , position.y - (fontSize / 2) - padding);
									
								});
								
							}
						}
					});
				}
			}); 
			
			chart.update();
			setTimeout(function() {
				TabResize();
			}, 200);
		}

		function paging(pnnId) {
			if ($('#I_PNN_' + pnnId).attr("class") == "disabled") {
				return;
			}
			chart.data.labels = [];
			chart.data.datasets = [];
			chart.update();
			$('#grid').hide();
			$('#chart').show();
			var data = "";
			var page = Number($('#I_CNT').val());

			if (pnnId == "P") { // 이전결과 페이지
				$('#I_PNN').val('P');
				count--;
				data = "&I_DAT=" + dataProvider.getValue(0, "DAT");
				data += "&I_JNO=" + dataProvider.getValue(0, "JNO");
				data += "&I_GCD=" + dataProvider.getValue(0, "GCD");
				data += "&I_ACD=" + dataProvider.getValue(0, "ACD");
				$('#I_ICNT').val(page);
			}
			if (pnnId == "N") { // 다음 결과 페이지
				$('#I_PNN').val('N');
				count++;
				data = "&I_DAT=" + dataProvider.getValue((page - 1), "DAT");
				data += "&I_JNO=" + dataProvider.getValue((page - 1), "JNO");
				data += "&I_GCD=" + dataProvider.getValue((page - 1), "GCD");
				data += "&I_ACD=" + dataProvider.getValue((page - 1), "ACD");
				$('#I_ICNT').val(page + 1);
			}
			if (count == 0) {
				$('#I_PNN_N').removeClass("disabled");
				$('#I_PNN_P').addClass("disabled");
			} else {
				$('#I_PNN_P').removeClass("disabled");
			}
			dataResult(data);
		}

		function search() {
			chart.data.labels = [];
			chart.data.datasets = [];
			chart.update();
			$('#grid').hide();
			$('#chart').show();
			var url = "";
			var cnt = Number($('#I_CNT').val());
			$('#I_ICNT').val(Number($('#I_CNT').val()) + 1);
			$('#I_PNN').val('C');
			if (isNull($('#I_FNM').val())) { //사원을 로그인시 병원코드 입력후 조회버튼 누를경우
				if (!isNull($('#I_HOS').val())) {
					var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
					$('#I_FNM').val(I_FNM);
				}
			}
			if (!searchValidation()) {
				return;
			}
			dataResult();

		}

		/* 팝업 호출 및 종료 */
		function openPopup(index) {
			var gridValus, labelNm, strUrl;
			if (index == "2") {
				strUrl = "/hospSearchS.do";
				labelNm = "병원 조회";
			} else if (index == "3") {
				strUrl = "/patSearchS.do";
				labelNm = "수진자 정보 조회";
			} else if (index == "4") {
				strUrl = "/testSearchM.do";
				labelNm = "검사 항목 조회";
			}
			//fnOpenPopup(url,labelNm,gridValus,callback)
			/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			fnOpenPopup(strUrl, labelNm, gridValus, callFunPopup);
		}
		/*callback 호출 */
		function callFunPopup(url, iframe, gridValus) {
			if (url == "/hospSearchS.do") {
				gridValus = {};
				gridValus["I_HOS"] =  $('#I_HOS').val();
				gridValus["I_FNM"] =  $('#I_FNM').val();
				iframe.gridViewRead(gridValus);
			}
			if (url == "/patSearchS.do") {
				gridValus = {};
				gridValus["I_HOS"]     =     $('#I_HOS').val();
				gridValus["I_NAM"]     =     $('#I_NAM').val();
				gridValus["I_CHN"]     =     $('#I_CHN').val();
				gridValus["I_FNM"]     =     $('#I_FNM').val();
				gridValus["I_NAME"]    =     $('#I_NAME').val();
				gridValus["I_ECF"]     =     $('#I_ECF').val();
				if (isNull(gridValus)) {
					gridValus = '';
				}
				iframe.gridViewRead(gridValus);
			}
			if (url == "/testSearchM.do") {
				gridValus = $('#I_GAD').val();
				if (isNull(gridValus)) {
					gridValus = '';
				}
				iframe.gridViewRead(gridValus);
			}
		}
		/*close 호출*/
		function fnCloseModal(obj) {
			//var data = Object.values(obj);
			var data = [];
			for ( var x in obj) {
				data.push(obj[x]);
			}

			if (data[0] == "/hospSearchSList.do") {
				$('#I_HOS').val(data[1].F120PCD);
				$('#I_FNM').val(data[1].F120FNM);
			}
			if (data[0] == "/testSearchMList.do") {
				var I_GAD = "";
				var I_FKN = [];
				for (var i = 1; i < data.length; i++) {
					/* 					if(isNull(data[i].F011ACD)){
					 data[i].F011ACD = "  ";
					 }
					 I_GAD += (data[i].F010GCD + data[i].F011ACD); */

					I_GAD += data[i].F010GCD;

					I_FKN.push(data[i].F010FKN);
				}

				$('#I_GAD').val(I_GAD);
				$('#I_FKN').val(I_FKN);
				var tooltip = "";
				for ( var x in I_FKN) {
					tooltip += (Number(x) + 1) + ". " + I_FKN[x] + "\n";
				}
				$('#I_FKN').attr('title', tooltip);
			}
			if (data[0] == "/patSearchSList.do") {
				$('#I_NAM').val(data[1].F100NAM);
				$('#I_CHN').val(data[1].F100C);
				$('#I_BDT').val(data[1].F100BDT);
				$('#I_HOS').val(data[1].F100HOS);
				$('#I_FNM').val(data[1].F120FNM);
			}
		}

		function exportGrid() {
			var totalCnt = dataProvider.getRowCount();
			if (totalCnt == 0) {
				CallMessage("268", "alert");
				return;
			}
			CallMessage("257", "confirm", "", gridExport);
		}

		function gridExport() {
			gridView.exportGrid({
				type : "excel",
				target : "local",
				fileName : I_LOGMNM + ".xlsx",
				showProgress : true,
				applyDynamicStyles : true,
				progressMessage : "엑셀 Export중입니다.",
				indicator : "default",
				header : "visible",
				lookupDisplay : true,
				footer : "default",
				compatibility : "2010",
				done : function() { //내보내기 완료 후 실행되는 함수
				}
			});
		}

		function dataClean() {
			var data1 = dataProvider.getJsonRows(0, -1);
			dataProvider.removeRows(data1);
		}

		function searchValidation() {
			
			var sDate = new Date($("#I_FDT").val());
			var eDate = new Date($("#I_TDT").val());
			
			// 유효성검사 - 최대 검색 기간 체크 !!
			var now = new Date();
		    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
			if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
				CallMessage("301","alert",["10"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
				return ;
		   	}
			
			if(strByteLength($("#I_ETC").val()) > 50){
				CallMessage("292", "alert", ["검체번호는 50 Byte"], dataClean);
				return false;
			}
			
			if ($('#I_HOS').val() == "") {
				CallMessage("245", "alert", [ "병원 코드를" ], dataClean);
				return false;
			}

			if ($('#I_NAM').val() == "") {
				CallMessage("245", "alert", [ "수진자 명을" ], dataClean);
				return false;
			}

			if ($('#I_CHN').val() == "") {
				CallMessage("245", "alert", [ "수진자 명을 검색하여 차트 번호를" ], dataClean);
				return false;
			}

			if (isNull($("#I_BDT").val())) {
				CallMessage("269", "alert", "", dataClean);
				return false;
			}

			var sDate = new Date($("#I_FDT").val());
			var eDate = new Date($("#I_TDT").val());
			var interval = eDate - sDate;
			var day = 1000 * 60 * 60 * 24;

			if (interval / day > 365) {
				CallMessage("274", "alert", "", dataClean); //최대 1년 내에서 조회해주세요.
				return false;
			}

			if (!checkdate($("#I_FDT")) || !checkdate($("#I_TDT"))) {
				CallMessage("273", "alert", "", dataClean);
				return false;
			}

			if (sDate > eDate) {
				CallMessage("229", "alert", "", dataClean); // 조회기간을 확인하여 주십시오.
				return false;
			}

			return true;

		}

		function byteCheck(str) { //문자열 한글 2byte, 영어,숫자 1byte 변환
			var codeByte = 0;
			for (var i = 0; i < str.length; i++) {
				var oneChar = escape(str.charAt(i));
				if (oneChar.length == 1) {
					codeByte++;
				} else if (oneChar.indexOf("%u") != -1) {
					codeByte += 2;
				} else if (oneChar.indexOf("%") != -1) {
					codeByte++;
				}
			}
			return codeByte;
		}

		function init() {
			$('#I_FKN').removeAttr("title");
			$('#I_GAD').val('');
			$('#I_FKN').val('');
		}

		function enterSearch(enter) {
			if (event.keyCode == 13) {
				if (enter == "1") {
					search();
				} else {
					openPopup(enter);
				}
			} else {
				var target = event.target || event.srcElement;
				if (target.id == "I_HOS") {
					$('#I_FNM').val('');
				}
				if (target.id == "I_NAM") {
					$('#I_CHN').val('');
				}
			}
		}
	</script>
</head>
<body>
	<div class="content_inner">
		<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_NAME" name="I_NAME" type="hidden" value="<%=ss_nam%>"/> <!-- 사용자명 병원명 -->
		<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/>
		<input id="I_AGR" name="I_AGR" type="hidden" value="<%=ss_agr%>"/>
		<input id="I_BDT" name="I_BDT" type="hidden">
		<input id="I_GAD" name="I_GAD" type="hidden">
		<input id="I_PNN" name="I_PNN" type="hidden">
		<input type="hidden" id="I_ICNT_CHART" name="I_ICNT_CHART" value="10"> <!-- 차트에 보여줄 건수 -->
		<input type="hidden" id="I_ICNT" name="I_ICNT"> 				  	   <!-- 그리드에 보여줄 건수 -->
		<input type="hidden" id="I_CNT" name="I_CNT" value="50"> 			   <!-- 그리드에 보여줄 건수 -->
			<div class="container-fluid">
				<div class="con_wrap col-md-12 srch_wrap">
					<div class="title_area open">
						<h3 class="tit_h3">검색영역</h3>
						<a href="#" class="btn_open">검색영역 닫기</a>
					</div>
					<div class="con_section row">
						<div class="main_srch_box">
<!-- 						<p class="srch_txt text-center" style="display: none">조회하려면 검색영역을 열어주세요.</p> -->
							<div class="srch_box_inner m-t-10">
<!-- 							<div class="srch_box_inner m-t-10" style="float: left; width: 90%;"> -->
								<div class="col-md-12">
									<div  class="group4" >
										<label for="" class="label_basic" style="float: left;">기간</label>
										<div  class="group1" >
											<div class="select_area">
												<select id="I_DGN" name="I_DGN" class="form-control"></select>
											</div>
											<div class="btn_group" style="font-size: 14px" id="I_S_TERM"></div>
										</div>
										<div class="group2" >
											<span class="period_area">
												<label for="I_FDT" class="blind">날짜 입력</label>
												<input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT">
											</span>
											 ~ 
											<span class="period_area">
												<label for="I_TDT" class="blind">날짜 입력</label>
												<input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT">
											</span>
										</div>
									</div>
									<div class="group3" >
										<label for="" class="label_basic">수진자</label>
										<input type="text" id="I_CHN" name="I_CHN"readonly="readonly" title="차트번호"   class="srch_put srch_chn" onclick="javascript:openPopup(3)">
										<input type="text" id="I_NAM" name="I_NAM" maxlength="25" readonly="readonly" onclick="javascript:openPopup(3)"  class="srch_put pat_pop_srch_ico" >
									</div>
								</div>
							</div>
							<div class="srch_box_inner m-t-10" id="hospHide" style="display: none">
								<div class="srch_item srch_item_v5 hidden_srch" >
									<label for="" class="label_basic">병원</label>
									<input id="I_HOS" name="I_HOS" type="text"  class="srch_put srch_hos numberOnly"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup(2)" maxlength="5" onkeydown="javasscript:enterSearch(1)" />
									<input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly" onclick="javascript:openPopup(2)">
								</div>
							</div>
							<div class="btn_srch_box m-t-10">
<!-- 							<div style="float: left;width: 79px;"> -->
								<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
							</div>
						</div>
						<div class="srch_line"></div>
						<div class="srch_box floatLeft">
							<div class="srch_box_inner m-t-10">
								<div class="srch_item srch_item_v5">
	 								<label for="" class="label_basic">검사항목</label>
									<input type="text" class="srch_put btn_ico test_pop_srch_ico" id="I_FKN" readonly="readonly" data-toggle="tooltip" data-placement="bottom"  onClick="javascript:openPopup(4)" >
									<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init()">초기화</button>
								</div>
								<div class="srch_item srch_item_v4">
									<label for="" class="label_basic">검체번호</label>
									<input type="text" id="I_ETC"  class="srch_put"  name="I_ETC" maxlength="50" onkeydown="javascript:enterSearch(1)">
									
								</div>
							</div>
							<div class="srch_box_inner m-t-10">
								<div class="srch_item srch_item_v5">
									<div class="srch_item">
										<label for="" class="label_basic">검사분야</label>
										<div class="select_area">
											<select id="I_HAK" name="I_HAK" class="form-control"></select>
										</div>
									</div>
									<div class="srch_item">
										<label for="" class="label_basic">검사진행</label>
										<div class="select_area">
											<select id="I_STS" name="I_STS" class="form-control"></select>
										</div>
									</div>
								</div>
								<div class="srch_item srch_item_v4">
									<ul class="chk_lst chk_lst_v2" id="RGN"></ul>
								</div>
							</div>
					
						</div>
					</div>
				</div>
			</div>
		</form>
       <div class="container-fluid">
			<div class="con_wrap col-md-7 col-sm-12">
               <div class="title_area">
                   <h3 class="tit_h3">검사결과</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀 다운</button>
					</div>
               </div>
               <div class="con_section overflow-scr">
                   <div class="tbl_type">
                   		<div id="realgrid"  class="realgridH"></div>
					</div>
					<nav class="pagination_area">
						<ul class="pagination" >
							<li id="I_PNN_P" >
								<a href="#" aria-label="Previous">
								<span aria-hidden="true" onClick="javascript:paging('P')" >이전</span>
							</a>
							</li>
							<li id="I_PNN_N">
								<a href="#" aria-label="Next">
								<span aria-hidden="true" onclick="javascript:paging('N')">다음</span>
								</a>
							</li>
						</ul>
					</nav>
                </div>
           </div>
			<div class="con_wrap col-md-5 col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">그래프 / 그리드</h3>
					<div class="tit_btn_area">
						<div class="select_area">
							<!-- <select id="G_KIND" name="G_KIND" class="form-control"></select> -->
						<select id="I_SORT" name="I_SORT" class="form-control" onchange="sortChange()">
							<option value="DESC">내림차순</option>
							<option value="ASC">오름차순</option>
						</select>
						</div>
					</div>
				</div>
				<div class="con_section overflow-scr">
					<div class="graph_area" id="chart">
						<!-- <p class="graph_tit">시계열</p> -->
						<p class="graph_tit">&nbsp;</p>
						<canvas id="myChart" style="width:100%; height:400px;"></canvas>
					</div>
					<div class="" style="display: none;" id="grid">
						<!-- <p class="graph_tit">시계열</p> -->
						<div id="realgrid2" class="realgridH"></div>
					</div>					
				</div>
			</div>
       </div>
	</div>
</body>
</html>

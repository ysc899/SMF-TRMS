<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>

	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>실시간 현황</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	
	<!-- 검사 실시간 현황 css (하이차트 css 포함) -->
	<link rel="stylesheet" href="/css/realtime.css?v=3">
	
	<!-- 하이차트 js -->
	<script src="/js/hightchart/highcharts.js"></script>
	<script src="/js/hightchart/modules/exporting.js"></script>
	<script src="/js/hightchart/modules/export-data.js"></script>
	<script src="/js/hightchart/modules/accessibility.js"></script>
	
    <script type="text/javascript">
		
    
	    /*************** 05.분야별 검사 실시 현황 (전일접수) - grid ***************/
		var gridView5;
		var dataProvider5;
		var resultList5;
	
	    /*************** 01.접수현황에 따른 검체별 개수 (조회일 기준) - grid ***************/
		var gridView1;
		var dataProvider1;
		var resultList1;
		var resultList1_1;
		
		/*************** 06.근무 현황 (조회일 기준) - grid ***************/
		var gridView6;
		var dataProvider6;
		var resultList6;
		
		/*************** 03.본원 TS SYSTEM 가동률 (실시간현황) - grid ***************/
		var resultList3;
		
		function init() {
	    	setGrid();
		} 
		
		function setGrid(){
			
			 // 05.분야별 검사 실시 현황 (전일접수)
			dataProvider5 = new RealGridJS.LocalDataProvider();
			gridView5 = new RealGridJS.GridView("realgrid5");
			
			setStyles(gridView5);

			gridView5.setHeader({
		    	height:30
		    })
		    
			gridView5.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 27,
				emptyMessage: "조회중입니다."
			});
			
			gridView5.setDataSource(dataProvider5);
			
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
			
			var fields5=[
				 {fieldName:"00001"}	// 구분
				,{fieldName:"00002"}	// 진행상황
		    	,{fieldName:"SCNT"}		// 본원
		    	,{fieldName:"BCNT"}		// 부산
		    	,{fieldName:"DCNT"}		// 대구
		    	,{fieldName:"GCNT"}		// 광주
		    	,{fieldName:"JCNT"}		// 대전
		    	];
			
			dataProvider5.setFields(fields5);
			
			var columns5=[
			     {name:"00001",	fieldName:"00001", renderer:{showTooltip: true},header:{text:"구분"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}, mergeRule: {criteria: "value"}}
			    ,{name:"00002",	fieldName:"00002", renderer:{showTooltip: true},header:{text:"진행상황"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"SCNT",	fieldName:"SCNT", renderer:{showTooltip: true} ,header:{text:"본원"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"BCNT",	fieldName:"BCNT" ,renderer:{showTooltip: true} ,header:{text:"부산"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"DCNT",	fieldName:"DCNT" ,renderer:{showTooltip: true} ,header:{text:"대구"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"GCNT",	fieldName:"GCNT" ,renderer:{showTooltip: true} ,header:{text:"광주"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"JCNT",	fieldName:"JCNT" ,renderer:{showTooltip: true} ,header:{text:"대전"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options5 = { visible: false}
		    gridView5.setCheckBar(options5); 
 			
			//컬럼을 GridView에 입력 합니다.
		    gridView5.setColumns(columns5);
			
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
 			
			// 01.접수현황에 따른 검체별 개수 (조회일 기준)
			dataProvider1 = new RealGridJS.LocalDataProvider();
			gridView1 = new RealGridJS.GridView("realgrid1");
			
			setStyles(gridView1);
			
			gridView1.setHeader({
		    	height:30
		    })
		    
			gridView1.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 30,
				emptyMessage: "조회중입니다."
			});
			
			gridView1.setDataSource(dataProvider1);
			
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
			
			var fields1=[
				 {fieldName:"DATE_VAL"}
				,{fieldName:"TCD"}
		    	,{fieldName:"TCNT"}
		    	,{fieldName:"SCNT"}
		    	,{fieldName:"BCNT"}
		    	,{fieldName:"DCNT"}
		    	,{fieldName:"GCNT"}
		    	,{fieldName:"JCNT"}
		    	];
			
			dataProvider1.setFields(fields1);
			
			var columns1=[
			     {name:"DATE_VAL",	fieldName:"DATE_VAL", renderer:{showTooltip: true},header:{text:"구분"},	width:30, styles: {textAlignment: "center", font : "나눔고딕, 13"},mergeRule: {criteria: "value"}}
			    ,{name:"TCD",	fieldName:"TCD", renderer:{showTooltip: true},header:{text:"검체종류"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"TCNT",	fieldName:"TCNT", renderer:{showTooltip: true} ,header:{text:"전체"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"SCNT",	fieldName:"SCNT" ,renderer:{showTooltip: true} ,header:{text:"본원"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"BCNT",	fieldName:"BCNT" ,renderer:{showTooltip: true} ,header:{text:"부산"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"DCNT",	fieldName:"DCNT" ,renderer:{showTooltip: true} ,header:{text:"대구"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"GCNT",	fieldName:"GCNT" ,renderer:{showTooltip: true} ,header:{text:"광주"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"JCNT",	fieldName:"JCNT" ,renderer:{showTooltip: true} ,header:{text:"대전"},	width:45, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options1 = { visible: false}
		    gridView1.setCheckBar(options1); 
 			
			//컬럼을 GridView에 입력 합니다.
		    gridView1.setColumns(columns1);
			
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
            
            
            
         	// 06.근무 현황 (조회일 기준)
			dataProvider6 = new RealGridJS.LocalDataProvider();
			gridView6 = new RealGridJS.GridView("realgrid6");
			
			setStyles(gridView6);
			
			gridView6.setHeader({
		    	height:30
		    })
		    
			gridView6.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 30,
				emptyMessage: "조회중입니다."
			});
			
			gridView6.setDataSource(dataProvider6);
			
			gridView6.setStyles(basicGrayBlueSkin);
			
			gridView6.setStateBar({
				  visible: false  
				});
			
			gridView6.setOptions({
                insertable: false,
                appendable: false,
                deletable : false,
                readOnly  : true,
                panel     : {visible : false},
    			footer	  : {visible: false},
    			keepNullFocus: true
               });
			
			var fields6=[
				 {fieldName:"00001"}
				,{fieldName:"00002"}
		    	,{fieldName:"SCNT"}
		    	,{fieldName:"BCNT"}
		    	,{fieldName:"DCNT"}
		    	,{fieldName:"GCNT"}
		    	,{fieldName:"JCNT"}
		    	];
			
			dataProvider6.setFields(fields6);
			
			var columns6=[
			     {name:"00001",	fieldName:"00001", renderer:{showTooltip: true},header:{text:"주/야"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"},mergeRule: {criteria: "value"}}
			    ,{name:"00002",	fieldName:"00002", renderer:{showTooltip: true},header:{text:"구분"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"SCNT",	fieldName:"SCNT", renderer:{showTooltip: true} ,header:{text:"본원"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"BCNT",	fieldName:"BCNT" ,renderer:{showTooltip: true} ,header:{text:"부산"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"DCNT",	fieldName:"DCNT" ,renderer:{showTooltip: true} ,header:{text:"대구"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"GCNT",	fieldName:"GCNT" ,renderer:{showTooltip: true} ,header:{text:"광주"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"JCNT",	fieldName:"JCNT" ,renderer:{showTooltip: true} ,header:{text:"대전"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options6 = { visible: false}
		    gridView6.setCheckBar(options6); 
 			
			//컬럼을 GgridView6 입력 합니다.
		    gridView6.setColumns(columns6);
			
		    gridView6.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
            
            gridView6.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
            
         	// 데이터 가져오기
            dataResult1();
		}
		
		// 데이터 가져오기
		function dataResult1(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/realtimeAList.do", formData,false);
		}
		
		// 가져온 데이터 grealGrid로 그리기
		function onResult(strUrl,response){
			
			if(strUrl == "/realtimeAList.do"){
 				
				// 05.분야별 검사 실시 현황 (전일접수)
				resultList5 =  response.resultList5;
				dataProvider5.setRows(resultList5);
				gridView5.setDataSource(dataProvider5);
				//넘버링 제거 
			    gridView5.setIndicator({ visible: false});
				
				// 01.접수현황에 따른 검체별 개수 (조회일 기준)
				// 당일
				resultList1 =  response.resultList1;
				dataProvider1.setRows(resultList1);
				gridView1.setDataSource(dataProvider1);
				//넘버링 제거 
			    gridView1.setIndicator({ visible: false});
			    // 전일
			    resultList1_1 =  response.resultList1_1;
			    dataProvider1.addRows(resultList1_1);
				gridView1.setDataSource(dataProvider1);
				//넘버링 제거 
			    gridView1.setIndicator({ visible: false});
				
			 	// 06.근무 현황 (조회일 기준)
			    resultList6 =  response.resultList6;
				dataProvider6.setRows(resultList6);
				gridView6.setDataSource(dataProvider6);
				//넘버링 제거 
			    gridView6.setIndicator({ visible: false});
				
			    // 03.본원 TS SYSTEM 가동률 (실시간현황)
			    resultList3 =  response.resultList3;
			 	// 그래프그리기
			    hightcharts_graph_bar_combo();
			}
		}
		
		/*******************************************************
		**** 03.본원 TS SYSTEM 가동률 (실시간현황) (하이차트)
		*******************************************************/
		function hightcharts_graph_bar_combo(){
			 
			var ts_system_01 = '';
			var ts_system_02 = '';
			var ts_system_03 = '';
			var ts_system_04 = '';
			 
			for (key in resultList3) {
				ts_system_01 = resultList3[key]['00003'];	// 1호기
				ts_system_02 = resultList3[key]['00005'];	// 2호기
				ts_system_03 = resultList3[key]['00007'];	// 3호기
				ts_system_04 = resultList3[key]['00009'];	// 4호기
			} 
			 
			// ########################## 하이차트그리기 ##########################
			// Build the chart
			/* 
			Highcharts.chart('container1', {
			    chart: {
			        plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
			        type: 'pie'
			    },
			    title: {
			        text: ''
			    },
			    tooltip: {
			        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			    },
			    accessibility: {
			        point: {
			            valueSuffix: '%'
			        }
			    },
			    plotOptions: {
			        pie: {
			            allowPointSelect: true,
			            cursor: 'pointer',
			            dataLabels: {
			                enabled: false
			            },
			            showInLegend: true
			        }
			    },
			    series: [{
			        name: '가동률',
			        colorByPoint: true,
			        data: [{
			            name: '1호기',
			            y: ts_system_01,
			            sliced: true,
			            selected: true
			        }, {
			            name: '2호기',
			            y: ts_system_02
			        }, {
			            name: '3호기',
			            y: ts_system_03
			        }, {
			            name: '4호기',
			            y: ts_system_04
			        }]
			    }]
			}); // end - Highcharts.chart 
			 */
			
			Highcharts.chart('container1', {
			    chart: {
			        plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
			        type: 'pie'
			    },
			    credits: {
		            enabled: false
		        },
			    title: {
			        text: ''
			    },
			    tooltip: {
			        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			    },
			    accessibility: {
			        point: {
			            valueSuffix: '%'
			        }
			    },
			    plotOptions: {
			        pie: {
			            allowPointSelect: true,
			            cursor: 'pointer',
			            //colors: pieColors,
			            dataLabels: {
			                enabled: true,
			                format: '<b>{point.name}</b><br>{point.percentage:.1f} %',
			                distance: -50,
			                filter: {
			                    property: 'percentage',
			                    operator: '>',
			                    value: 3
			                },
			                style: {
					          fontSize: '13px', // X축 폰트 사이즈
					          //fontFamily: 'Verdana, sans-serif' // X축 폰트
					          fontFamily: '나눔고딕, sans-serif' // X축 폰트
					        }
			            }/* ,
			            showInLegend: true // 하단에 색깔별 호기 표시 */
			        }
			    },
			    series: [{
			        name: '가동률',
			        colorByPoint: true, // 그림자
			        data: [
			            { name: '1호기', y: ts_system_01, color: "#4f81bd"},
			            { name: '2호기', y: ts_system_02, color: "#c0504d"},
			            { name: '3호기', y: ts_system_03, color: "#9bbb59"},
			            { name: '4호기', y: ts_system_04, color: "#8064a2"}
			        ]
			    }]
			});
			
		}	// end - hightcharts_graph_bar_combo()
		 
    </script>
    
</head>
<!-- <body> -->
<body onload="init();">

	<!-- content_inner :: start -->
	<div class="content_inner">
	
		<!-- 검색영역 start -->
		<%-- <form id="searchForm" name="searchForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		</form> --%>
		<!-- 검색영역 end -->
		
		<!-- realgrid_realtime_view_1 : start -->
		<div id="realgrid_realtime_view_1" class="container-fluid" style="height:350px;border:2px;">
			
			<div class="con_wrap_realtime col-md-12 col-sm-12">
	        	
	        	<!-- -------------------- 분야별 검사 실시 현황 (전일접수) - start -------------------- --> 
	           	<div style="float:left;width:50%;padding-right:5px;">
	           		<div class="title_area_realtime_2">
		            	<h3 class="tit_h3">분야별 검사 실시 현황 (전일접수)</h3>
		          	</div>
		          	<div class="con_section overflow-scr">
		          		<div style="height:370px;">
		          			<div id="realgrid5" style="height:380px;"></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;">
						<div style="float:left;">※ 코로나 검사 건수 제외</div>
	          			<div style="float:right;">(단위:건)</div>
	          		</div>
				</div>
				
				<!-- -------------------- 접수현황에 따른 검체별 개수 (조회일 기준) - start -------------------- --> 
	           	<div style="float:left;width:50%;padding-left:5px;">
		        	<div class="title_area_realtime_6">
						<div style="float:left;width:50%;">
							<h3 class="tit_h3">접수현황에 따른 검체별 개수 (조회일 기준)</h3>
						</div>
						<div style="text-align: center;float:right;width:30%;height:30px;padding:0 25px;background-color:#c7171d; border-radius:5px;line-height:29px;color:#fff">
							<button type="button" class="btn_srch" name = "btn_srch" onClick="window.location.reload()">전체 조회</button>
						</div>
		          	</div>
		          	<div class="con_section overflow-scr">
		          		<div style="height:370px;">
		          			<div id="realgrid1" style="height:380px;"></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;">
						<div style="float:left;">※ 코로나 검사 건수 제외</div>
	          			<div style="float:right;">(단위:개)</div>
	          		</div>
				</div>
				
				
			</div>
		</div>
		<!-- realgrid_realtime_view_1 : end -->
	 	
	 	<!-- realgrid_realtime_view_2 : start -->
	 	<div id="realgrid_realtime_view_2" class="container-fluid" style="height:350px;border:2px;">
	        <div class="con_wrap_realtime col-md-12 col-sm-12">		
				 
				<!-- -------------------- 근무 현황 (조회일 기준) - start -------------------- -->
				<div style="float:left;width:50%;padding-right:5px;">	
					<div class="title_area_realtime_1">
						<h3 class="tit_h3">근무 현황 (조회일 기준)</h3>
		          	</div>
		          	<div class="con_section overflow-scr">
		          		<div style="height:330px;">
		          			<div id="realgrid6" style="height:340px;" ></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;text-align:right;">(단위:명)</div>
				</div>
				
				<!-- -------------------- 본원 TS SYSTEM 가동률 (실시간현황) - start -------------------- -->
				<div style="float:left;width:50%;padding-left:5px;">
					<div class="title_area_realtime_2">
						<h3 class="tit_h3">본원 TS SYSTEM 가동률 (실시간현황)</h3>
					</div>
					<div class="con_section overflow-scr">
						<div style="height:317px;">
							<figure class="highcharts-figure">
								<div id="container1" style="height:317px;" ></div>
							</figure>
					    </div>
					</div>
				</div>
				
			</div>
        </div>
        <!-- realgrid_realtime_view_2 : end -->
        
	</div>
	<!-- content_inner :: end -->
	
</body>
</html>
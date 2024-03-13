<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>

	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>코로나 현황</title>
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
		
    
	    /*************** 09.코로나 검사 실시 현황 (조회일 기준) - grid ***************/
		var gridView9;
		var dataProvider9;
		var resultList9;	// 당일
		var resultList9_1;	// 전일
	
		/*************** 10.코로나 주단위 건수 추이 - grid ***************/
		var resultList10;
		
		/*************** 11.코로나 양성률 (전일 보고) - grid ***************/
		var gridView11;
		var dataProvider11;
		var resultList11;
		
		function init() {
	    	setGrid();
		} 
		
		function setGrid(){
			
			// 09.코로나 검사 실시 현황 (조회일 기준)
			dataProvider9 = new RealGridJS.LocalDataProvider();
			gridView9 = new RealGridJS.GridView("realgrid9");
			
			setStyles(gridView9);

			gridView9.setHeader({
		    	height:30
		    })
		    
			gridView9.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 27,
				emptyMessage: "조회중입니다."
			});
			
			gridView9.setDataSource(dataProvider9);
			
			gridView9.setStyles(basicGrayBlueSkin);
			
			gridView9.setStateBar({
				  visible: false  
				});
			
			gridView9.setOptions({
                insertable: false,
                appendable: false,
                deletable : false,
                readOnly  : true,
                panel     : {visible : false},
    			footer	  : {visible: false},
    			keepNullFocus: true
               });
			
			var fields9=[
				 {fieldName:"DATE_VAL"}		// 구분
				,{fieldName:"PROGRESS_NM"}	// 진행상황
		    	,{fieldName:"SCNT"}			// 본원
		    	,{fieldName:"BCNT"}			// 부산
		    	,{fieldName:"DCNT"}			// 대구
		    	,{fieldName:"GCNT"}			// 광주
		    	,{fieldName:"JCNT"}			// 대전
		    	];
			
			dataProvider9.setFields(fields9);
			
			var columns9=[
			     {name:"DATE_VAL"	, fieldName:"DATE_VAL"	 , renderer:{showTooltip: true},header:{text:"구분"} 		, width:30, styles: {textAlignment: "center", font : "나눔고딕, 13"}, mergeRule: {criteria: "value"}}
			    ,{name:"PROGRESS_NM", fieldName:"PROGRESS_NM", renderer:{showTooltip: true},header:{text:"진행상황"}	, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"SCNT",	fieldName:"SCNT", renderer:{showTooltip: true} ,header:{text:"본원"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"BCNT",	fieldName:"BCNT" ,renderer:{showTooltip: true} ,header:{text:"부산"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"DCNT",	fieldName:"DCNT" ,renderer:{showTooltip: true} ,header:{text:"대구"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"GCNT",	fieldName:"GCNT" ,renderer:{showTooltip: true} ,header:{text:"광주"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"JCNT",	fieldName:"JCNT" ,renderer:{showTooltip: true} ,header:{text:"대전"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options9 = { visible: false}
		    gridView9.setCheckBar(options9); 
 			
			//컬럼을 GridView에 입력 합니다.
		    gridView9.setColumns(columns9);
			
		    gridView9.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
            
            gridView9.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
            
            
         	// 11.코로나 양성률 (전일 보고)
			dataProvider11 = new RealGridJS.LocalDataProvider();
			gridView11 = new RealGridJS.GridView("realgrid11");
			
			setStyles(gridView11);

			gridView11.setHeader({
		    	height:30
		    })
		    
			gridView11.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 27,
				emptyMessage: ""
			});
			
			gridView11.setDataSource(dataProvider11);
			
			gridView11.setStyles(basicGrayBlueSkin);
			
			gridView11.setStateBar({
				  visible: false  
				});
			
			gridView11.setOptions({
                insertable: false,
                appendable: false,
                deletable : false,
                readOnly  : true,
                panel     : {visible : false},
    			footer	  : {visible: false},
    			keepNullFocus: true
               });
			
			var fields11=[
				 {fieldName:"00001"}	// 구분
				,{fieldName:"00002"}	// 결과
		    	,{fieldName:"00004"}	// 전체
		    	,{fieldName:"00006"}	// 본원
		    	,{fieldName:"00008"}	// 부산
		    	,{fieldName:"00010"}	// 대구
		    	,{fieldName:"00012"}	// 광주
		    	,{fieldName:"00014"}	// 대전
		    	];
			
			dataProvider11.setFields(fields11);
			
			var columns11=[
			     {name:"00001",	fieldName:"00001", renderer:{showTooltip: true},header:{text:"구분"}		, width:30, styles: {textAlignment: "center", font : "나눔고딕, 13"}, mergeRule: {criteria: "value"}}
			    ,{name:"00002",	fieldName:"00002", renderer:{showTooltip: true},header:{text:"결과"}	, width:70, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"00004",	fieldName:"00004", renderer:{showTooltip: true} ,header:{text:"전체"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"00006",	fieldName:"00006" ,renderer:{showTooltip: true} ,header:{text:"본원"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"00008",	fieldName:"00008" ,renderer:{showTooltip: true} ,header:{text:"부산"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"00010",	fieldName:"00010" ,renderer:{showTooltip: true} ,header:{text:"대구"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"00012",	fieldName:"00012" ,renderer:{showTooltip: true} ,header:{text:"광주"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"00014",	fieldName:"00014" ,renderer:{showTooltip: true} ,header:{text:"대전"}		, width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options11 = { visible: false}
		    gridView11.setCheckBar(options11); 
 			
			//컬럼을 GridView에 입력 합니다.
		    gridView11.setColumns(columns11);
			
		    gridView11.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
            
            gridView11.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
 			
         	// 데이터 가져오기
            dataResult1();
		}
		
		// 데이터 가져오기
		function dataResult1(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/realtimeCList.do", formData,false);
		}
		
		// 가져온 데이터 grealGrid로 그리기
		function onResult(strUrl,response){
			
			if(strUrl == "/realtimeCList.do"){
 				
				// 09.코로나 검사 실시 현황 (조회일 기준)
				// 당일
				resultList9 =  response.resultList9;
				dataProvider9.setRows(resultList9);
				gridView9.setDataSource(dataProvider9);
				//넘버링 제거 
			    gridView9.setIndicator({ visible: false});
				// 전일
				resultList9_1 =  response.resultList9_1;
				dataProvider9.addRows(resultList9_1);
				gridView9.setDataSource(dataProvider9);
				//넘버링 제거 
			    gridView9.setIndicator({ visible: false});
				
			 	// 04.주단위 건수 추이
			    resultList10 =  response.resultList10;
				// 그래프그리기
			    hightcharts_graph_line_labels_corona();
				
				// 11.코로나 양성률 (전일 보고)
				resultList11 =  response.resultList11;
				dataProvider11.setRows(resultList11);
				gridView11.setDataSource(dataProvider11);
				//넘버링 제거 
			    gridView11.setIndicator({ visible: false});
			}
		}
		
		/*******************************************************
		**** 04.주단위 건수 추이 (하이차트)
		*******************************************************/
	   	function hightcharts_graph_line_labels_corona(){
			
			// 날짜/일자
			var ts_system_01_1 = '';
			var ts_system_01_2 = '';
			var ts_system_01_3 = '';
			var ts_system_01_4 = '';
			var ts_system_01_5 = '';
			var ts_system_01_6 = '';
			var ts_system_01_7 = '';
			
			// 전체
			var ts_system_02_1 = '';
			var ts_system_02_2 = '';
			var ts_system_02_3 = '';
			var ts_system_02_4 = '';
			var ts_system_02_5 = '';
			var ts_system_02_6 = '';
			var ts_system_02_7 = '';
			
			// 본원
			var ts_system_03_1 = '';
			var ts_system_03_2 = '';
			var ts_system_03_3 = '';
			var ts_system_03_4 = '';
			var ts_system_03_5 = '';
			var ts_system_03_6 = '';
			var ts_system_03_7 = '';

			// 부산
			var ts_system_04_1 = '';
			var ts_system_04_2 = '';
			var ts_system_04_3 = '';
			var ts_system_04_4 = '';
			var ts_system_04_5 = '';
			var ts_system_04_6 = '';
			var ts_system_04_7 = '';
			
			//대구
			var ts_system_05_1 = '';
			var ts_system_05_2 = '';
			var ts_system_05_3 = '';
			var ts_system_05_4 = '';
			var ts_system_05_5 = '';
			var ts_system_05_6 = '';
			var ts_system_05_7 = '';
			
			//광주
			var ts_system_06_1 = '';
			var ts_system_06_2 = '';
			var ts_system_06_3 = '';
			var ts_system_06_4 = '';
			var ts_system_06_5 = '';
			var ts_system_06_6 = '';
			var ts_system_06_7 = '';
			
			//대전
			var ts_system_07_1 = '';
			var ts_system_07_2 = '';
			var ts_system_07_3 = '';
			var ts_system_07_4 = '';
			var ts_system_07_5 = '';
			var ts_system_07_6 = '';
			var ts_system_07_7 = '';
			 
			for (key in resultList10) {
				//console.log("key :: "+key);
				if(key == 0){
					ts_system_01_1 = isEmpty(resultList10[key]['00001']);
					ts_system_02_1 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_1 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_1 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_1 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_1 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_1 = isEmpty(resultList10[key]['CNT6']);
				}else if(key == 1){
					ts_system_01_2 = isEmpty(resultList10[key]['00001']);
					ts_system_02_2 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_2 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_2 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_2 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_2 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_2 = isEmpty(resultList10[key]['CNT6']);
				}else if(key == 2){
					ts_system_01_3 = isEmpty(resultList10[key]['00001']);
					ts_system_02_3 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_3 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_3 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_3 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_3 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_3 = isEmpty(resultList10[key]['CNT6']);
				}else if(key == 3){
					ts_system_01_4 = isEmpty(resultList10[key]['00001']);
					ts_system_02_4 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_4 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_4 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_4 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_4 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_4 = isEmpty(resultList10[key]['CNT6']);
				}else if(key == 4){
					ts_system_01_5 = isEmpty(resultList10[key]['00001']);
					ts_system_02_5 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_5 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_5 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_5 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_5 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_5 = isEmpty(resultList10[key]['CNT6']);
				}else if(key == 5){
					ts_system_01_6 = isEmpty(resultList10[key]['00001']);
					ts_system_02_6 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_6 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_6 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_6 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_6 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_6 = isEmpty(resultList10[key]['CNT6']);
				}else if(key == 6){
					ts_system_01_7 = isEmpty(resultList10[key]['00001']);
					ts_system_02_7 = isEmpty(resultList10[key]['CNT1']);
					ts_system_03_7 = isEmpty(resultList10[key]['CNT2']);
					ts_system_04_7 = isEmpty(resultList10[key]['CNT3']);
					ts_system_05_7 = isEmpty(resultList10[key]['CNT4']);
					ts_system_06_7 = isEmpty(resultList10[key]['CNT5']);
					ts_system_07_7 = isEmpty(resultList10[key]['CNT6']);
				}
			}
			 
			// ########################## 하이차트그리기 ##########################
			Highcharts.chart('container10', {
			    chart: {
			        type: 'line'
			    },
			    credits: {
		            enabled: false
		        },
			    title: {
			        //text: 'Monthly Average Temperature'
			        text: ''
			    },
			    subtitle: {
			        //text: 'Source: WorldClimate.com'
			        text: ''
			    },
			    xAxis: {
			        //categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
			        categories: [ts_system_01_1
			        			,ts_system_01_2
			        			,ts_system_01_3
			        			,ts_system_01_4
			        			,ts_system_01_5
			        			,ts_system_01_6
			        			,ts_system_01_7
			       	],
				    labels: {
				        //rotation: -45, // 라벨 기울기
				        style: {
				          fontSize: '13px', // X축 폰트 사이즈
				          //fontFamily: 'Verdana, sans-serif' // X축 폰트
				          fontFamily: '나눔고딕, sans-serif' // X축 폰트
				        }
				    }
			    },
			    yAxis: {
			        title: {
			            //text: 'Temperature (°C)'
			            text: ''
			        },
				    labels: {
				        //rotation: -45, // 라벨 기울기
				        style: {
				          fontSize: '13px', // X축 폰트 사이즈
				          //fontFamily: 'Verdana, sans-serif' // X축 폰트
				          fontFamily: '나눔고딕, sans-serif' // X축 폰트
				        },formatter: function() {
		                    return parseInt(this.value );
	                    }
				    }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                enabled: true
			            },
			            enableMouseTracking: false
			        }
			    },
			    legend: {
					layout: 'vertical',
			        align: 'right',
			        verticalAlign: 'top',
			        x: 0,
			        y: 0,
			        floating: false,
			        borderWidth: 1,
			        backgroundColor:
			        	Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
			        shadow: true
			    },
			    series: [{
			        name: '전체',
			        data: [ts_system_02_1
			        	 , ts_system_02_2
			        	 , ts_system_02_3
			        	 , ts_system_02_4
			        	 , ts_system_02_5
			        	 , ts_system_02_6
			        	 , ts_system_02_7
			       	]
			    }, {
			        name: '본원',
			        data: [ts_system_03_1
			        	 , ts_system_03_2
			        	 , ts_system_03_3
			        	 , ts_system_03_4
			        	 , ts_system_03_5
			        	 , ts_system_03_6
			        	 , ts_system_03_7
			       	]
			    }, {
			        name: '부산',
			        data: [ts_system_04_1
			        	 , ts_system_04_2
			        	 , ts_system_04_3
			        	 , ts_system_04_4
			        	 , ts_system_04_5
			        	 , ts_system_04_6
			        	 , ts_system_04_7
			       	]
			    }, {
			        name: '대구',
			        data: [ts_system_05_1
			        	 , ts_system_05_2
			        	 , ts_system_05_3
			        	 , ts_system_05_4
			        	 , ts_system_05_5
			        	 , ts_system_05_6
			        	 , ts_system_05_7
			       	]
			    }, {
			        name: '광주',
			        data: [ts_system_06_1
			        	 , ts_system_06_2
			        	 , ts_system_06_3
			        	 , ts_system_06_4
			        	 , ts_system_06_5
			        	 , ts_system_06_6
			        	 , ts_system_06_7
			       	]
			    }, {
			        name: '대전',
			        data: [ts_system_07_1
			        	 , ts_system_07_2
			        	 , ts_system_07_3
			        	 , ts_system_07_4
			        	 , ts_system_07_5
			        	 , ts_system_07_6
			        	 , ts_system_07_7
			       	]
			    }]
			}); // end - Highcharts.chart
		} // end - hightcharts_graph_line_labels_corona()
		 
		//null값 체크 
		function isEmpty(value){
		    if(value == null || value.length === 0) {
		           return 0;
		     } else{
		            return value;
		     }
		
		}
		
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
		
		<!-- realgrid_realtime_view_5 : start -->
		<div id="realgrid_realtime_view_5" class="container-fluid" style="height:350px;border:2px;">
	        <div class="con_wrap_realtime col-md-12 col-sm-12">
	        	
	        	<!-- -------------------- 코로나 검사 실시 현황 (조회일 기준) - start -------------------- --> 
	           	<div style="float:left;width:50%;padding-right:5px;">
	           		<div class="title_area_realtime_2">
		            	<h3 class="tit_h3">코로나 검사 실시 현황 (조회일 기준)</h3>
		          	</div>
		          	<div class="con_section overflow-scr">
		          		<div style="height:370px;">
		          			<div id="realgrid9" style="height:380px;"></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;text-align:right;">(단위:건)</div>
				</div>
				
				<!-- -------------------- 코로나 주단위 건수 추이 - start -------------------- --> 
	           	<div style="float:left;width:50%;padding-left:5px;">
		        	<div class="title_area_realtime_5">
						<div style="float:left;width:50%;">
							<h3 class="tit_h3">코로나 주단위 건수 추이 (최근 일주일)</h3>
						</div>
						<div style="text-align: center;float:right;width:30%;height:30px;padding:0 25px;background-color:#c7171d; border-radius:5px;line-height:29px;color:#fff">
							<button type="button" class="btn_srch" name = "btn_srch" onClick="window.location.reload()">전체 조회</button>
						</div>
		          	</div>
		          	<div class="con_section overflow-scr">
		          		<div style="height:370px;">
		          			<div id="container10" style="height:380px;"></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;text-align:right;">(단위:건)</div>
				</div>
				
				
			</div>
		</div>
		<!-- realgrid_realtime_view_5 : end -->
	 	
	 	<!-- realgrid_realtime_view_6 : start -->
	 	<div id="realgrid_realtime_view_6" class="container-fluid" style="height:350px;border:2px;">
	        <div class="con_wrap_realtime col-md-12 col-sm-12">		
				 
				<!-- -------------------- 양성률 (전일 집계 기준) - start -------------------- -->
				<div style="float:left;width:50%;padding-right:5px;">	
					<div class="title_area_realtime_2">
						<h3 class="tit_h3">코로나 양성률 (전일 보고)</h3>
		          	</div>
		          	<div class="con_section overflow-scr">
		          		<div style="height:310px;">
		          			<div id="realgrid11" style="height:320px;" ></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;text-align:right;">(단위:건)</div>
				</div>
				
				<!-- -------------------- 본원 TS SYSTEM 가동률 (실시간현황) - start -------------------- -->
				<div style="float:left;width:50%;padding-left:5px;">
					<!-- <div class="title_area_realtime_2">
						<h3 class="tit_h3">본원 TS SYSTEM 가동률 (실시간현황)</h3>
					</div>
					<div class="con_section overflow-scr">
						<div style="height:297px;">
							<figure class="highcharts-figure">
								<div id="container1" style="height:297px;" ></div>
							</figure>
					    </div>
					</div> -->
				</div>
				
			</div>
        </div>
        <!-- realgrid_realtime_view_6 : end -->
        
	</div>
	<!-- content_inner :: end -->
	
</body>
</html>
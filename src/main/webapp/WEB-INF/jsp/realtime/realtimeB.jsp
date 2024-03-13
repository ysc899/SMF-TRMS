<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>

	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>누적 현황</title>
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
		
		/*************** 08.야간검사 결과보고율 (전일 접수) - grid ***************/
		var resultList8;
		
		/*************** 02.검체오류 현황 (전일 접수) - grid ***************/
		var gridView2;
		var dataProvider2;
		var resultList2;
		
		/*************** 07.양성률 (전일 보고) - grid ***************/
		var gridView7;
		var dataProvider7;
		var resultList7;
		
		/*************** 04.주단위 건수 추이 - grid ***************/
		var resultList4;
		
		function init() {
	    	setGrid();
		}
		
		function setGrid(){
            
			// =====================  02.검체오류 현황 (전일 접수)
			dataProvider2 = new RealGridJS.LocalDataProvider();
			gridView2 = new RealGridJS.GridView("realgrid2");
			
			setStyles(gridView2);
			
			gridView2.setHeader({
		    	height:30
		    })
		    
			gridView2.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 30,
				emptyMessage: ""
			});
			
			gridView2.setDataSource(dataProvider2);
			
			gridView2.setStyles(basicGrayBlueSkin);
			
			gridView2.setStateBar({
				  visible: false  
				});
			
			gridView2.setOptions({
                insertable: false,
                appendable: false,
                deletable : false,
                readOnly  : true,
                panel     : {visible : false},
    			footer	  : {visible: false},
    			keepNullFocus: true
               });
			
			var fields2=[
				 {fieldName:"00001"}
		    	,{fieldName:"CNT1"}
		    	,{fieldName:"CNT2"}
		    	,{fieldName:"CNT3"}
		    	,{fieldName:"CNT4"}
		    	,{fieldName:"CNT5"}
		    	,{fieldName:"CNT6"}
		    	];
			
			dataProvider2.setFields(fields2);
			
			var columns2=[
			     {name:"00001",	fieldName:"00001", renderer:{showTooltip: true},header:{text:"오류종류"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"CNT1",	fieldName:"CNT1", renderer:{showTooltip: true} ,header:{text:"전체"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"CNT2",	fieldName:"CNT2" ,renderer:{showTooltip: true} ,header:{text:"본원"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"CNT3",	fieldName:"CNT3" ,renderer:{showTooltip: true} ,header:{text:"부산"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"CNT4",	fieldName:"CNT4" ,renderer:{showTooltip: true} ,header:{text:"대구"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"CNT5",	fieldName:"CNT5" ,renderer:{showTooltip: true} ,header:{text:"광주"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"CNT6",	fieldName:"CNT6" ,renderer:{showTooltip: true} ,header:{text:"대전"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options2 = { visible: false}
		    gridView2.setCheckBar(options2); 
 			
			//컬럼을 GgridView2 입력 합니다.
		    gridView2.setColumns(columns2);
			
		    gridView2.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
            
            gridView2.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
            
         	// ===================== 07.양성률 (전일 보고)
			dataProvider7 = new RealGridJS.LocalDataProvider();
			gridView7 = new RealGridJS.GridView("realgrid7");
			
			setStyles(gridView7);
			
			gridView7.setHeader({
		    	height:30
		    })
		    
			gridView7.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: 30,
				emptyMessage: "조회중입니다."
			});
			
			gridView7.setDataSource(dataProvider7);
			
			gridView7.setStyles(basicGrayBlueSkin);
			
			gridView7.setStateBar({
				  visible: false  
				});
			
			gridView7.setOptions({
                insertable: false,
                appendable: false,
                deletable : false,
                readOnly  : true,
                panel     : {visible : false},
    			footer	  : {visible: false},
    			keepNullFocus: true
               });
			
			var fields7=[
				 {fieldName:"CTNM"}
		    	,{fieldName:"HAKNAM"}
		    	];
			
			dataProvider7.setFields(fields7);
			
			var columns7=[
			     {name:"CTNM",	fieldName:"CTNM", renderer:{showTooltip: true},header:{text:"학부"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
			    ,{name:"HAKNAM",fieldName:"HAKNAM", renderer:{showTooltip: true} ,header:{text:"기준치초과 학부"},	width:50, styles: {textAlignment: "center", font : "나눔고딕, 13"}}
	    	];
			
 			var options7 = { visible: false}
		    gridView7.setCheckBar(options7); 
 			
			//컬럼을 GgridView2 입력 합니다.
		    gridView7.setColumns(columns7);
			
		    gridView7.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
            
            gridView7.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
            
            // 데이터 가져오기
            dataResult1();
		}
		
		// 데이터 가져오기
		function dataResult1(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/realtimeBList.do", formData,false);
		}
		
		// 가져온 데이터 grealGrid로 그리기
		function onResult(strUrl,response){
			
			if(strUrl == "/realtimeBList.do"){
				
			 	// 08.야간검사 결과보고율 (전일 접수)
			    resultList8 =  response.resultList8;
				// 그래프그리기
			    hightcharts_graph_column_basic();
				
			 	// 02.검체오류 현황 (전일 접수)
			    resultList2 =  response.resultList2;
				dataProvider2.setRows(resultList2);
				gridView2.setDataSource(dataProvider2);
				//넘버링 제거 
			    gridView2.setIndicator({ visible: false});

				// 07.양성률 (전일 보고)
			    resultList7 =  response.resultList7;
				dataProvider7.setRows(resultList7);
				gridView7.setDataSource(dataProvider7);
				//넘버링 제거 
			    gridView7.setIndicator({ visible: false});
				
			 	// 04.주단위 건수 추이
			    resultList4 =  response.resultList4;
				// 그래프그리기
			    hightcharts_graph_line_labels();
			}
		}
		
		/*******************************************************
		**** 08.야간검사 결과보고율 (전일 접수) (하이차트)
		*******************************************************/
	   	function hightcharts_graph_column_basic(){
		
	   		// 구분
			var dn_result_report_01_1 = '';
			var dn_result_report_01_2 = '';
			var dn_result_report_01_3 = '';
			var dn_result_report_01_4 = '';
			var dn_result_report_01_5 = '';
			var dn_result_report_01_6 = '';
			
			// 본원
			var dn_result_report_02_1 = '';
			var dn_result_report_02_2 = '';
			var dn_result_report_02_3 = '';
			var dn_result_report_02_4 = '';
			var dn_result_report_02_5 = '';
			var dn_result_report_02_6 = '';
			
			// 부산
			var dn_result_report_03_1 = '';
			var dn_result_report_03_2 = '';
			var dn_result_report_03_3 = '';
			var dn_result_report_03_4 = '';
			var dn_result_report_03_5 = '';
			var dn_result_report_03_6 = '';

			// 대구
			var dn_result_report_04_1 = '';
			var dn_result_report_04_2 = '';
			var dn_result_report_04_3 = '';
			var dn_result_report_04_4 = '';
			var dn_result_report_04_5 = '';
			var dn_result_report_04_6 = '';
			
			// 광주
			var dn_result_report_05_1 = '';
			var dn_result_report_05_2 = '';
			var dn_result_report_05_3 = '';
			var dn_result_report_05_4 = '';
			var dn_result_report_05_5 = '';
			var dn_result_report_05_6 = '';
			
			// 대전
			var dn_result_report_06_1 = '';
			var dn_result_report_06_2 = '';
			var dn_result_report_06_3 = '';
			var dn_result_report_06_4 = '';
			var dn_result_report_06_5 = '';
			var dn_result_report_06_6 = '';
			
			
			for (key in resultList8) {
				if(key == 0){
					dn_result_report_01_1 = isEmpty(resultList8[key]['00001']);
					dn_result_report_02_1 = isEmpty(resultList8[key]['00003']);
					dn_result_report_03_1 = isEmpty(resultList8[key]['00005']);
					dn_result_report_04_1 = isEmpty(resultList8[key]['00007']);
					dn_result_report_05_1 = isEmpty(resultList8[key]['00009']);
					dn_result_report_06_1 = isEmpty(resultList8[key]['00011']);
				}else if(key == 1){
					dn_result_report_01_2 = isEmpty(resultList8[key]['00001']);
					dn_result_report_02_2 = isEmpty(resultList8[key]['00003']);
					dn_result_report_03_2 = isEmpty(resultList8[key]['00005']);
					dn_result_report_04_2 = isEmpty(resultList8[key]['00007']);
					dn_result_report_05_2 = isEmpty(resultList8[key]['00009']);
					dn_result_report_06_2 = isEmpty(resultList8[key]['00011']);
				}else if(key == 2){
					dn_result_report_01_3 = isEmpty(resultList8[key]['00001']);
					dn_result_report_02_3 = isEmpty(resultList8[key]['00003']);
					dn_result_report_03_3 = isEmpty(resultList8[key]['00005']);
					dn_result_report_04_3 = isEmpty(resultList8[key]['00007']);
					dn_result_report_05_3 = isEmpty(resultList8[key]['00009']);
					dn_result_report_06_3 = isEmpty(resultList8[key]['00011']);
				}else if(key == 3){
					dn_result_report_01_4 = isEmpty(resultList8[key]['00001']);
					dn_result_report_02_4 = isEmpty(resultList8[key]['00003']);
					dn_result_report_03_4 = isEmpty(resultList8[key]['00005']);
					dn_result_report_04_4 = isEmpty(resultList8[key]['00007']);
					dn_result_report_05_4 = isEmpty(resultList8[key]['00009']);
					dn_result_report_06_4 = isEmpty(resultList8[key]['00011']);
				}else if(key == 4){
					dn_result_report_01_5 = isEmpty(resultList8[key]['00001']);
					dn_result_report_02_5 = isEmpty(resultList8[key]['00003']);
					dn_result_report_03_5 = isEmpty(resultList8[key]['00005']);
					dn_result_report_04_5 = isEmpty(resultList8[key]['00007']);
					dn_result_report_05_5 = isEmpty(resultList8[key]['00009']);
					dn_result_report_06_5 = isEmpty(resultList8[key]['00011']);
				}else if(key == 5){
					dn_result_report_01_6 = isEmpty(resultList8[key]['00001']);
					dn_result_report_02_6 = isEmpty(resultList8[key]['00003']);
					dn_result_report_03_6 = isEmpty(resultList8[key]['00005']);
					dn_result_report_04_6 = isEmpty(resultList8[key]['00007']);
					dn_result_report_05_6 = isEmpty(resultList8[key]['00009']);
					dn_result_report_06_6 = isEmpty(resultList8[key]['00011']);
				}
			}
			
	   	// ########################## 하이차트그리기 ##########################
			Highcharts.chart('container8', {
			    chart: {
			        type: 'column'
			    },
			    credits: {
		            enabled: false
		        },
			    title: {
			        //text: 'Monthly Average Rainfall'
			        text: ''
			    },
			    subtitle: {
			        //text: 'Source: WorldClimate.com'
			        text: ''
			    },
			    xAxis: {
			        categories: [
			        	'본원'
			        	, '부산'
			        	, '대구'
			        	, '광주'
			        	, '대전'
			        ],
			        crosshair: true,
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
			        min: 0,
			        max: 100,
			        title: {
			            //text: 'Rainfall (mm)'
			            text: ''
			        },
				    labels: {
				        //rotation: -45, // 라벨 기울기
				        style: {
				          fontSize: '13px', // X축 폰트 사이즈
				          //fontFamily: 'Verdana, sans-serif' // X축 폰트
				          fontFamily: '나눔고딕, sans-serif' // X축 폰트
				        }
				    }
			    },
			    tooltip: {
			        headerFormat: '<span style="font-size:20px">{point.key}</span><table>',
			        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			            '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
			        footerFormat: '</table>',
			        shared: true,
			        useHTML: true
			    },
			    plotOptions: {
			        column: {
			            pointPadding: 0.2,
			            borderWidth: 0
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
			        name: dn_result_report_01_1,
			        //data: [24.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
			        data: [dn_result_report_02_1	// 화학 (본원)
			        		, dn_result_report_03_1	// 화학 (부산)
			        		, dn_result_report_04_1 // 화학 (대구)
			        		, dn_result_report_05_1 // 화학 (광주)
			        		, dn_result_report_06_1 // 화학 (대전)
			        ],
			        color: "#4f81bd"
			    }, {
			        name: dn_result_report_01_2,
			        //data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
			        data: [dn_result_report_02_2	// 면역 (본원)
			        		, dn_result_report_03_2 // 면역 (부산)
			        		, dn_result_report_04_2 // 면역 (대구)
			        		, dn_result_report_05_2 // 면역 (광주)
			        		, dn_result_report_06_2 // 면역 (대전)
					],
			        color: "#c0504d"
			    }, {
			        name: dn_result_report_01_3,
			        //data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
			        data: [dn_result_report_02_3    // 혈액 (본원)
			        		, dn_result_report_03_3 // 혈액 (부산)
			        		, dn_result_report_04_3 // 혈액 (대구)
			        		, dn_result_report_05_3 // 혈액 (광주)
			        		, dn_result_report_06_3 // 혈액 (대전)
			        ],
			        color: "#9bbb59"
			    }, {
			        name: dn_result_report_01_4,
			        //data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
			        data: [dn_result_report_02_4    // 요검경 (본원)
		        		, dn_result_report_03_4 	// 요검경 (부산)
		        		, dn_result_report_04_4 	// 요검경 (대구)
		        		, dn_result_report_05_4 	// 요검경 (광주)
		        		, dn_result_report_06_4 	// 요검경 (대전)
			        ],
			        color: "#8064a2"
			    }, {
			        name: dn_result_report_01_5,
			        //data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
			        data: [dn_result_report_02_5    // 분자미생물 (본원)
		        		, dn_result_report_03_5 	// 분자미생물 (부산)
		        		, dn_result_report_04_5 	// 분자미생물 (대구)
		        		, dn_result_report_05_5 	// 분자미생물 (광주)
		        		, dn_result_report_06_5 	// 분자미생물 (대전)
			        ],
			        color: "#4bacc6"
			    }, {
			        name: dn_result_report_01_6,
			        //data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
			        data: [dn_result_report_02_6    // 코로나 (본원)
		        		, dn_result_report_03_6 	// 코로나 (부산)
		        		, dn_result_report_04_6 	// 코로나 (대구)
		        		, dn_result_report_05_6 	// 코로나 (광주)
		        		, dn_result_report_06_6 	// 코로나 (대전)
			        ],
			        color: "#f79646"
			    }]
			});
		}
		
		
	   	/*******************************************************
		**** 04.주단위 건수 추이 (하이차트)
		*******************************************************/
	   	function hightcharts_graph_line_labels(){
			
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
			 
			for (key in resultList4) {
				//console.log("key :: "+key);
				if(key == 0){
					ts_system_01_1 = isEmpty(resultList4[key]['00001']);
					ts_system_02_1 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_1 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_1 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_1 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_1 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_1 = isEmpty(resultList4[key]['CNT6']);
				}else if(key == 1){
					ts_system_01_2 = isEmpty(resultList4[key]['00001']);
					ts_system_02_2 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_2 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_2 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_2 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_2 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_2 = isEmpty(resultList4[key]['CNT6']);
				}else if(key == 2){
					ts_system_01_3 = isEmpty(resultList4[key]['00001']);
					ts_system_02_3 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_3 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_3 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_3 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_3 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_3 = isEmpty(resultList4[key]['CNT6']);
				}else if(key == 3){
					ts_system_01_4 = isEmpty(resultList4[key]['00001']);
					ts_system_02_4 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_4 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_4 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_4 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_4 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_4 = isEmpty(resultList4[key]['CNT6']);
				}else if(key == 4){
					ts_system_01_5 = isEmpty(resultList4[key]['00001']);
					ts_system_02_5 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_5 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_5 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_5 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_5 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_5 = isEmpty(resultList4[key]['CNT6']);
				}else if(key == 5){
					ts_system_01_6 = isEmpty(resultList4[key]['00001']);
					ts_system_02_6 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_6 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_6 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_6 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_6 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_6 = isEmpty(resultList4[key]['CNT6']);
				}else if(key == 6){
					ts_system_01_7 = isEmpty(resultList4[key]['00001']);
					ts_system_02_7 = isEmpty(resultList4[key]['CNT1']);
					ts_system_03_7 = isEmpty(resultList4[key]['CNT2']);
					ts_system_04_7 = isEmpty(resultList4[key]['CNT3']);
					ts_system_05_7 = isEmpty(resultList4[key]['CNT4']);
					ts_system_06_7 = isEmpty(resultList4[key]['CNT5']);
					ts_system_07_7 = isEmpty(resultList4[key]['CNT6']);
				}
			}
			 
			// ########################## 하이차트그리기 ##########################
			Highcharts.chart('container2', {
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
		} // end - hightcharts_graph_line_labels()
		
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
		
        <!-- realgrid_realtime_view_3 : start -->
		<div id="realgrid_realtime_view_3" class="container-fluid" style="height:350px;border:2px;">
	        <div class="con_wrap_realtime col-md-12 col-sm-12">
	        	
	        	<!-- -------------------- 야간검사 결과보고율 (전일 접수) - start -------------------- -->
				<div style="float:left;width:50%;padding-right:5px;">
					<div class="title_area_realtime_5">
						<h3 class="tit_h3">야간검사 결과보고율 (전일 접수)</h3>
					</div>
					<div class="con_section overflow-scr">
						<div style="height:297px;">
							<figure class="highcharts-figure">
								<div id="container8" style="height:297px;" ></div>
							</figure>
					    </div>
					    <div style="height:20px;text-align:right;"></div>
					</div>
				</div>
				
				<!-- -------------------- 검체오류 현황 (전일 접수) - start -------------------- -->
				<div style="float:left;width:50%;padding-left:5px;">
				    <div class="title_area_realtime_4">
		        	    <div style="float:left;width:50%;">
							<h3 class="tit_h3">검체오류 현황 (전일 접수)</h3>
						</div>
						<div style="text-align: center;float:right;width:30%;height:30px;padding:0 25px;background-color:#c7171d; border-radius:5px;line-height:29px;color:#fff">
							<button type="button" class="btn_srch" name = "btn_srch" onClick="window.location.reload()">전체 조회</button>
						</div>
		          	</div>
		          	
		          	<div class="con_section overflow-scr">
			          	<div style="height:330px;">
		          			<div id="realgrid2" style="height:340px;" ></div>
		          		</div>
		          	</div>
		          	<div style="height:20px;">
						<div style="float:left;">※ 당일 검체관리팀 작성 집계현황이며, 확인 후 집계 건수 변경될 수 있음</div>
	          			<div style="float:right;">(단위:건)</div>
	          		</div>
				</div>
				
			</div>
		</div>
		<!-- realgrid_realtime_view_3 : end -->
				
		<!-- realgrid_realtime_view_4 : start -->
		<div id="realgrid_realtime_view_4" class="container-fluid" style="height:350px;border:2px;">
	        <div class="con_wrap_realtime col-md-12 col-sm-12">		 
				
				<!-- -------------------- 양성률 (전일 보고) - start -------------------- -->
				<div style="float:left;width:50%;height:400px;padding-right:5px;">
					<div class="title_area_realtime_1">
						<h3 class="tit_h3">양성률 (전일 보고)</h3>
					</div>
					<div class="con_section overflow-scr">
						<div style="height:335px;">
							<div id="realgrid7" style="height:345px;" ></div>
					    </div>
					</div>
					<div style="height:20px;text-align:right;"></div>
				</div>
				
				<!-- -------------------- 주단위 건수 추이 - start -------------------- -->
				<div style="float:left;width:50%;padding-left:5px;">
					<div class="title_area_realtime_2">
						<h3 class="tit_h3">주단위 건수 추이 (최근 일주일)</h3>
					</div>
					<div class="con_section overflow-scr">
						<div style="height:320px;">
							<figure class="highcharts-figure">
								<div id="container2" style="height:330px;" ></div>
							</figure>
					    </div>
					</div>
					<div style="height:20px;">
						<div style="float:left;">※ 코로나 검사 건수 제외</div>
	          			<div style="float:right;">(단위:건)</div>
	          		</div>
				</div>
				
			</div>
        </div>
        <!-- realgrid_realtime_view_4 : end -->
        
        
	</div>
	<!-- content_inner :: end -->
	
</body>
</html>
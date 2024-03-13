<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>검사의뢰서 통계</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<script type="text/javascript">
	
	var pscd = "";
	var gridView, dataProvider, gridView2, dataProvider2;
	var fields = [];
	var columns = [];
	var gridValues = [];
	var gridLabels = [];
	var gridHeader;
	var dts, labelHeader;
	var graphData = [];
	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		search();
	        }
	    }
	} 
	
	$(document).ready( function(){

		$('#I_PNN_P').addClass("disabled");
		$('#I_PNN_N').addClass("disabled");
		var graph_kind = getCode(I_LOGMNU, I_LOGMNM, "GRAPH_KIND", 'N');
		$('#G_KIND').html(graph_kind);
		$('#G_KIND').val("LINE");	
		jcf.replace($("#G_KIND"));
		setGrid1();
		
	    var year = new Date().getFullYear();
	    //console.log(year);
	    var optionList = "";
	    for(var i=0; i<5; i++){
	    	optionList += '<option value="'+(year-i)+'">'+(year-i)+ ' 년도</option>'
	    }
		$('#I_YEAR').html(optionList);
		jcf.replace($("#I_YEAR"));
	// 그래프 영역
		var gKind = $('#G_KIND').val().toLowerCase();
 		graph("GraphChart", gKind);
			
		$('#G_KIND').change(function(){
			var gKind = $('#G_KIND').val().toLowerCase();
			graph("GraphChart", gKind, graphData);
		});
	});

	function setGrid1(){
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);

	    setStyles(gridView);
	    setGridBar(gridView, false,"state");
	    gridView.setCheckBar({
	    	showAll : false
	     });  
    
	    fields=[
	    	 {fieldName:"F600HOS"}	// 차트번호
	    	,{fieldName:"F100COR"}
	    	,{fieldName:"F120FNM"}	//수진자명
	    	,{fieldName:"F600ACD"}	//
	    	,{fieldName:"F600GCD"}	//검사항목
	    	,{fieldName:"SNM"}//검사항목
	    	,{fieldName:"M_01",dataType: "number"}		
	    	,{fieldName:"M_02",dataType: "number"}
	    	,{fieldName:"M_03",dataType: "number"}
	    	,{fieldName:"M_04",dataType: "number"}
	    	,{fieldName:"M_05",dataType: "number"}
	    	,{fieldName:"M_06",dataType: "number"}
	    	,{fieldName:"M_07",dataType: "number"}
	    	,{fieldName:"M_08",dataType: "number"}
	    	,{fieldName:"M_09",dataType: "number"}
	    	,{fieldName:"M_10",dataType: "number"}
	    	,{fieldName:"M_11",dataType: "number"}
	    	,{fieldName:"M_12",dataType: "number"}
	    	,{fieldName:"ROW_SORT"}
	    	,{fieldName:"VAL"}
			, {fieldName:"I_LOGMNU"}	//메뉴코드
			, {fieldName:"I_LOGMNM"}	//메뉴명
	    ];

	    columns=[
	    	   {name:"F600HOS",		fieldName:"F600HOS",	header:{text:"병원코드"},				width:80 }
	    	  , {name:"F120FNM",	fieldName:"F120FNM",	header:{text:"병원명"},				width:120 }
	    	  , {name:"SNM",		fieldName:"SNM",		header:{text:"검사항목"},				width:120 }
	    	  , {name:"G_HYear", type: "group", width: "900",header: { text: "YEAR"},
	    	       columns: [
			    	   {name:"M_01",		fieldName:"M_01",		header:{text:"1"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_02",		fieldName:"M_02",		header:{text:"2"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_03",		fieldName:"M_03",		header:{text:"3"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_04",		fieldName:"M_04",		header:{text:"4"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_05",		fieldName:"M_05",		header:{text:"5"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_06",		fieldName:"M_06",		header:{text:"6"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_07",		fieldName:"M_07",		header:{text:"7"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_08",		fieldName:"M_08",		header:{text:"8"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_09",		fieldName:"M_09",		header:{text:"9"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_10",		fieldName:"M_10",		header:{text:"10"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_11",		fieldName:"M_11",		header:{text:"11"},			width:80, styles:{textAlignment: "far"}}
			    	  , {name:"M_12",		fieldName:"M_12",		header:{text:"12"},			width:80, styles:{textAlignment: "far"}}
	    	        ]
	    	    }
	    ];
	    
	    dataProvider.setFields(fields);
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);

	    gridView.onItemChecked = function (grid, itemIndex, checked) {
	    	var chartData = [];
	    	var gridH = [];
	    	var data = [];
	    	var data2= [];
			graphData = [];

			var dataRows = grid.getCheckedRows();
			if(dataRows.length>10){
	        	CallMessage("258","alert",["10개만"]);
				grid.checkItem(itemIndex, false);  
				return;
			}
			for(var i in dataRows){
				gridH = [];
		    	data = [];
		    	chartData = [];
				var gridIdx = dataRows[i];
				var gridItem = grid.getValues(gridIdx);
				
				for(var  colNm in gridItem){
					if(colNm.indexOf("M_")>-1){
	 		    		gridH.push(colNm.replace(/M_/,""));
	 		    		chartData.push(gridItem[colNm]);
					}
				}
	 	    	data["data"]= chartData;
	 	    	data["label"] = grid.getValue(gridIdx,"SNM");
	 	    	graphData.push(data);
			}
			var gKind = $('#G_KIND').val().toLowerCase();
			graph("GraphChart", gKind, graphData);
	 	};
	}
	

	function dataChart(data){
		var label = [];
		dts = [];
		labelHeader = [];
		var color = Chart.helpers.color;

		var colorNames = Object.keys(window.colors);
		var i = 0;
		for(var key in data){
			label.push(key);
	    	if(key.indexOf("gridH")==-1){
				var colorName = colorNames[i];
				var dsColor = window.colors[colorName];
				var data1 = {
			            label: key,
			            data: data[key],				// db 데이터
			            borderWidth: 1,
						backgroundColor: color(dsColor).alpha(0.5).rgbString(),
						borderColor: dsColor,
						fill:false
			        };
				
				dts.push(data1);
	    		
	    	}
			i++;
    	}
		labelHeader = data["gridH"];
		GraphChart.data.labels = labelHeader;		
		GraphChart.data.datasets = dts;
		GraphChart.update();
	}
	
	function exportGrid(){
		var totalCnt = dataProvider.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		CallMessage("257","confirm", "", gridExport);
	}
	
	function gridExport(){
		  gridView.exportGrid({
			    type: "excel",
			    target: "local",
			    fileName: I_LOGMNM+".xlsx",
			    showProgress: true,
			    applyDynamicStyles: true,
			    progressMessage: "엑셀 Export중입니다.",
			    indicator: "default",
			    header: "visible",
			    lookupDisplay : true,
			    footer: "default",
			    compatibility: "2010",
			    done: function () {  //내보내기 완료 후 실행되는 함수
			    }
			});   
	}
	

	function dataResult(){
		$("#I_FDT").val($("#I_YEAR").val()+"0101");
		$("#I_TDT").val($("#I_YEAR").val()+"1231");

		dataProvider.setRows([]);		
		gridView.setDataSource(dataProvider);
		if(!Validation()) { return;}
	 	var   aHeader = gridView.getColumnProperty("G_HYear","header");
	    aHeader.text =$("#I_YEAR").val();
	    $(".graph_tit").html($("#I_YEAR").val()+"년 항목 의뢰 건수")
	    //하늘 병원 2018년 의뢰 건수
	    gridView.setColumnProperty("G_HYear","header",aHeader);
		
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysTestReqStatsList.do", formData);
	}
	
	function onResult(strUrl,response){
		var resultList;
		if(!isNull(response.resultList)){
			resultList = response.resultList;
		}
		if(resultList.lenght>0){
			gridHeader.push($("#I_YEAR").val());
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
	}
	
	
	function search(searchId){
		var url = "";
		$('#I_ICNT').val(Number($('#I_CNT').val())+1);
		$('#I_PNN').val('C');
		graphData = [];
		var gKind = $('#G_KIND').val().toLowerCase();
		graph("GraphChart", gKind, graphData);
		count = 0;
		dataResult();
	}

	/* 팝업 호출 및 종료 */
	function openPopup(index,gridValus){
		var labelNm,strUrl;
		if(index == "2"){
			strUrl = "/hospSearchMS.do";
			labelNm = "병원 조회";
		}else if(index == "4"){
			strUrl = "/testSearchM.do";
			labelNm = "검사 항목 조회";
		}
		//fnOpenPopup(url,labelNm,gridValus,callback)
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	/*callback 호출 */
	function callFunPopup(url,iframe,gridValus){
		//병원 검사 팝업
		if(url == "/hospSearchMS.do"){
			gridValus = $('#I_HOS_A').val();
			if(isNull(gridValus)){
				gridValus = '';
			}
		}
		//검사항목 팝업
		if(url == "/testSearchM.do"){
			gridValus = $('#I_GAD').val();
			if(isNull(gridValus)){
				gridValus = '';
			}
		}
		iframe.gridViewRead(gridValus);
	}
	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		//병원 검사 팝업
		if(data[0] == "/hospSearchSList.do"){
			$('#I_HOS_A').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
			var I_PCD="";
			var I_FNM=[];
				for(var i=1; i<data.length;i++){
					I_PCD += (data[i].F120PCD );
					I_FNM.push(data[i].F120FNM);
				} 
				$('#I_HOS_A').val(I_PCD);
				$('#I_FNM').val(I_FNM);
				
		}
		
		//검사항목 팝업
		if(data[0] == "/testSearchMList.do"){
			var I_GAD="";
			var I_FKN=[];
				for(var i=1; i<data.length;i++){   
					I_GAD += data[i].F010GCD;
					I_FKN.push(data[i].F010FKN);
				} 
				$('#I_GAD').val(I_GAD);
				$('#I_FKN').val(I_FKN);
				//$('#I_FKN').attr('title', I_FKN);
		}

	}
	
	function Validation(){
		
		if($('#I_HOS_A').val() == ""){
			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
			return false;
		}
		if($('#I_YEAR').val() == ""){
			CallMessage("245", "alert", ["접수 년도를"]);
			return false;
		}
// 		if($('#I_HOS').val() == ""){
// 			CallMessage("245", "alert", ["수진자 명을 검색하여 차트 번호를"]);
// 			return false;
// 		}
// 		if($('#I_FDT').val() == ""){
// 			CallMessage("245", "alert", ["접수 시작일을"]);
// 			return false;
// 		}
// 		if($('#I_TDT').val() == ""){
// 			CallMessage("245", "alert", ["접수 종료일을"]);
// 			return false;
// 		}
		return true;
		
	}
	
	function init(flag){
		if(flag == '4'){
			$('#I_GAD').val('');
			$('#I_FKN').val('');
		}
		if(flag=='2'){
			$('#I_HOS_A').val('');
			$('#I_FNM').val('');
		}
	}
	var count = 0;		// 페이지 확인 0이면 첫 페이지
	function pageCNP(type){
		var page = Number($('#I_CNT').val());
		
		if($('#I_PNN_'+type).attr("class") == "disabled"){
			return;
		}
		
		
		$('#I_PNN').val(type);
		if(type == "P"){ 			// 이전결과 페이지
			count--;
			$('#I_ICNT').val(page);
			$('#I_GCD').val(dataProvider.getValue(0, "F600GCD"));
			$('#I_ACD').val(dataProvider.getValue(0, "F600ACD"));
			$('#I_HOS').val(dataProvider.getValue(0, "F600HOS"));
		}
		if(type == "N"){ 			// 다음 결과 페이지
			count++;
			$('#I_GCD').val(dataProvider.getValue((page-1), "F600GCD"));
			$('#I_ACD').val(dataProvider.getValue((page-1), "F600ACD"));
			$('#I_HOS').val(dataProvider.getValue((page-1), "F600HOS"));
			$('#I_ICNT').val(page+1);
		}
		
		if(count == 0){
			$('#I_PNN_P').addClass("disabled");
			$('#I_PNN_N').removeClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		};
		
		graphData = [];
		var gKind = $('#G_KIND').val().toLowerCase();
		graph("GraphChart", gKind, graphData);
		dataResult();
		
	}
	
	</script>
</head>
<body>
<div class="content_inner">
	<div class="container-fluid">
		<div class="con_wrap col-md-12 srch_wrap">
			<div class="title_area open">
				<h3 class="tit_h3">검색영역</h3>
				<a href="#" class="btn_open">검색영역 접기</a>
			</div>
			<!-- con_section 검색영역 -->
			<div class="con_section row">
			<form id="searchForm" name="searchForm">
				<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
				<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
				<input id="I_BDT" name="I_BDT" type="hidden">
				
				<input id="I_NAME" name="I_NAME" type="hidden" value="<%=ss_nam%>"/> <%-- 사용자명 병원명 --%>
				<input id="I_HOS_A" name="I_HOS_A" type="hidden" /><%-- 병원코드 --%>
				<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/><%-- 병원사용자 구분 (E:병원사용자,C:사원) --%>
				<input id="I_FDT" name="I_FDT" type="hidden"/><%-- 접수일자from --%> 
				<input id="I_TDT" name="I_TDT" type="hidden"/><%-- 접수일자to --%> 
				<%--페이징 관련 --%>
				<input id="I_PNN" name="I_PNN" value="C" type="hidden"> <%-- 이전／다음(C/N/P) --%>
				<input id="I_CNT" name="I_CNT" value="50" type="hidden"><%-- 읽을건수		 --%> 
				<input id="I_ICNT" name="I_ICNT" type="hidden"><%-- 읽을건수		 --%> 
				<input id="I_HOS" name="I_HOS" type="hidden"><%-- 페이징 병원코드--%> 				
				<input id="I_GCD" name="I_GCD" type="hidden"><%-- I_GCD	--검사코드		 --%> 
				<input id="I_ACD" name="I_ACD" type="hidden"><%-- I_ACD	--부속코드		 --%> 
				<%--페이징 관련 --%>
				<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
				 <div class="srch_box">
					<div class="srch_box_inner">
						<div class="srch_item ">
							<label for="" class="label_basic">병원</label>
							<input type="text" class="srch_put" id="I_FNM" name="I_FNM" readonly="readonly"  onclick="openPopup('2')" >
							<button type="button" class="btn btn-red btn-sm" onclick="openPopup('2')" ><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
							<button type="button" class="btn btn-gray btn-sm" onclick="javascript:init(2)" title="초기화"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button>
						</div>
						<div class="srch_item">
							<label for="" class="label_basic">접수년도</label>
							<div class="select_area">
								<select id="I_YEAR" name="I_YEAR" class="form-control"></select>
							</div>
							<!-- <input type="text" class="srch_put numberOnly" maxlength="4"  id="I_YEAR" name="I_YEAR"    onkeydown="return enterSearch(event)"  > -->
						</div>
					</div>
							
					<div class="srch_box_inner m-t-10"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
						<div class="srch_item">
							<label for="" class="label_basic">검사 항목</label>
							<input type="hidden" id="I_GAD" name="I_GAD">	<!-- 검사코드 + 부속코드 -->
							<input type="text" class="srch_put"  id="I_FKN" name="I_FKN" onclick="javascript:openPopup(4)"  readonly="readonly">
							<button type="button" class="btn btn-red btn-sm" onclick="javascript:openPopup(4)" title="검색"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
							<button type="button" class="btn btn-gray btn-sm" onclick="javascript:init(4)" title="초기화"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button>
						</div>
					</div>
				
					<div class="btn_srch_box">
						<button type="button" id="SearchBtn" class="btn_srch" onclick="search()">조회</button>
					</div> 
				</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 검색영역 end -->         
	
       <div class="container-fluid">
       
			<div class="con_wrap col-md-7 col-sm-12">
               <div class="title_area">
                   <h3 class="tit_h3">검사의뢰서 통계</h3>
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
								<span aria-hidden="true" onClick="javascript:pageCNP('P')" >이전</span>
							</a>
							</li>
							<li id="I_PNN_N">
								<a href="#" aria-label="Next">
								<span aria-hidden="true" onclick="pageCNP('N')">다음</span>
								</a>
							</li>
						</ul>
					</nav>
                </div>
           </div>
			<div class="con_wrap col-md-5 col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">그래프</h3>
					<div class="tit_btn_area">
						<div class="select_area">
							<select id="G_KIND" name="G_KIND" class="form-control"></select>
						</div>
					</div>
				</div>
				<div class="con_section overflow-scr">
					<div class="graph_area">
						<p class="graph_tit"></p>
						<canvas id="GraphChart" style="width:100%; height:400px;"></canvas>
					</div>
				</div>
			</div>
       </div>
     </div>
					
                          
</body>
</html>

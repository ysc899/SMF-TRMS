<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>담당자별 병원 관리 </title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	
	<script>
	var gridView1,dataProvider1,gridView2,dataProvider2;
	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		search();
	        }
	    }
	}
	
	$(document).ready( function(){
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		setGrid1();
		setGrid2();
		$(".btn_srch").click(function(){
			dataResult1();
		});
	});
	
	function setGrid1(){
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    setStyles(gridView1);
	    setGridBar(gridView1, false);

	    var fields1=[
		    	  {fieldName:"F910SAB"}		//사원번호
		    	, {fieldName:"F910NAM"}		//사원명
		    	, {fieldName:"F910DPT"}		//부서번호
		    	, {fieldName:"F900KNM"}		//부서명
		    	, {fieldName:"HOSCNT", dataType: "number"}		//담당병원수
		    	];
				
	    
	    dataProvider1.setFields(fields1);

	    var columns1=[
	    	  {name:"F910SAB",		fieldName:"F910SAB",	header:{text:"사원번호"},	width:110 ,"styles":{"textAlignment": "center"}}
	    	, {name:"F910NAM",		fieldName:"F910NAM",	header:{text:"사원명"},	width:110 }
	    	, {name:"F900KNM",		fieldName:"F900KNM",	header:{text:"부서"},		width:150}
	    	, {name:"HOSCNT",		fieldName:"HOSCNT",		header:{text:"담당병원수"},width:80,"styles":{"textAlignment": "far"}}

	    ];
	    gridView1.setColumns(columns1);
	 	gridView1.onDataCellClicked = function (grid, index) {
			$("#I_MBC").val(grid.getValue(index.itemIndex, 0));
			callLoading(null,"on");
	 		dataResult2();
		};
// 	    dataResult1();
	}
	
	function setGrid2(){
	    dataProvider2 = new RealGridJS.LocalDataProvider();
	    gridView2 = new RealGridJS.GridView("realgrid2");
	    gridView2.setDataSource(dataProvider2);
	    
	    setStyles(gridView2);
	    setGridBar(gridView2, false);

	    var fields2=[
		    	  {fieldName:"F120PCD"}		//병원코드
		    	, {fieldName:"F120FNM"}		//병원명
		    	, {fieldName:"F120TEL"}		//연락처
		    	];
				
	    
	    dataProvider2.setFields(fields2);

	    var columns2=[
	    	  {name:"F120PCD",		fieldName:"F120PCD",		header:{text:"병원코드"},	width:110 ,"styles":{"textAlignment": "center"}}
	    	, {name:"F120FNM",		fieldName:"F120FNM",		header:{text:"병원명"},	width:170 }
	    	, {name:"F120TEL",		fieldName:"F120TEL",		header:{text:"연락처"},	width:150}

	    ];
	    
	    gridView2.setColumns(columns2);
	}
	
	function dataResult1(){
		callLoading(null,"on");
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysHospList.do", formData,false);
	}

	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/sysHospList.do"){
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
			$("#I_MBC").val("");
	 		dataResult2(false);
		}
		else if(strUrl == "/sysHospDtl.do"){
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
			callLoading(strUrl,"off");
		}
	}
	function dataResult2(type){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysHospDtl.do", formData,type);
	}
	
	//조회
	function search(){
		dataResult1();
	}
	
	</script>
</head>
<body>
	
<div class="content_inner">
	<form id="searchForm" name="searchForm">
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
					<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
					<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
					<input id="I_MBC" name="I_MBC" type="hidden"/>
					<input id="I_SERHOS" name="I_SERHOS" type="hidden"  ><%-- 병원명 --%>
					<div class="srch_item srch_item_v2">
					    <label for="" class="label_basic">사원번호</label>
					    <input type="text" class="srch_put"  id="I_SERASB" name="I_SERASB" maxlength="6" onkeydown="return enterSearch(event)" >
					</div>
					<div class="srch_item srch_item_v2">
					    <label for="" class="label_basic">사원명</label>
					    <input type="text" class="srch_put" id="I_SERNM" name="I_SERNM"  maxlength="12"  onkeydown="return enterSearch(event)" >
					</div>
					<div class="srch_item srch_item_v2">
					    <label for="" class="label_basic">부서명</label>
					    <input type="text" class="srch_put" id="I_SERDPT" name="I_SERDPT"  maxlength="30" onkeydown="return enterSearch(event)" >
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
			    <h3 class="tit_h3">담당자</h3>
			</div>
			<div class="con_section overflow-scr">
			    <div class="tbl_type">
					<div id="realgrid1"  class="realgridH"></div>
			    </div>
			</div>
		    </div>
		    
		    <div class="con_wrap col-md-6 col-sm-12">
			<div class="title_area">
			    <h3 class="tit_h3">병원(사용자)</h3>
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



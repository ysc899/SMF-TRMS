<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>검사항목 매핑 조회</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<style type="text/css">
		.label_basic{width: 100px;}
	</style>
	<script>

	var ss_ecf = "<%=ss_ecf%>";
    var pscd = "";
	var gridView;
	var dataProvider;
    var gridValues = [];
    var gridLabels = [];


	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		dataResult();
	        }
	    }else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
	    }
	    
	}
	$(document).ready( function(){
		//TEST_INFO
		if(ss_ecf=="C"){
			//hos_div
			$("#hos_div").addClass("display_off");
		}else{
			$("#hos_div").removeClass("display_off");
		}
		
		$(".btn_srch").click(function(){
			
			dataResult();
		});
		
		
		setGrid();
	});
	function dataResult(){
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
 		if($('#I_HOS').val() == ""){
 			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
 			return false;
 		}
 		
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/testItemMapList.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/getCommCode.do"){
		}
		if(strUrl == "/testItemMapList.do"){
			var resultList =  response.resultList;
	        gridView.closeProgress();
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	
	function setGrid(){
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    
	    setStyles(gridView);
	    setGridBar(gridView,false);
	    gridView.setDataSource(dataProvider);
		var fields=[
			{fieldName:"F033COR"}		//COR 
			, {fieldName:"F033HOS"}		// 병원 코드 
			, {fieldName:"F033TCD"}		// 병원 검사코드
			, {fieldName:"F033NCD"}		// 씨젠 검사코드 
			, {fieldName:"F010GCD"}		// 검사코드 
			, {fieldName:"F010FKN"} 	// 검사명(한글) 
			, {fieldName:"F018OCD"}		// 보험코드
		];
	    dataProvider.setFields(fields);
	    var columns=[
			 ,{name:"F033TCD",		fieldName:"F033TCD",	header:{text:"병원 검사코드"},	width:150 }
			 ,{name:"F033NCD",		fieldName:"F033NCD",	header:{text:"씨젠 검사코드"},	width:150 }
			 ,{name:"F010FKN",		fieldName:"F010FKN",	header:{text:"검사항목"},		width:300 }
			 ,{name:"F018OCD",		fieldName:"F018OCD",	header:{text:"보험코드"},		width:110 }
	    ];
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    
// 	    gridView.onDataCellDblClicked = function (grid, index) {
// 	    	openPopup(index);
// 	    };
 	    
 	    if(!isNull($('#I_HOS').val())){
			dataResult();
 	    }

	}

	var gridIdx;

	
	/* 팝업 호출 및 종료 */
	function openPopup(index){
		var gridValus,labelNm,strUrl;
		if(index == "1"){
			if(!saveValidation()) { return;}
			dataResult();
		}else if(index == "2"){
			strUrl = "/hospSearchS.do";
			labelNm = "병원 조회";
		}else if(index == "3"){
			strUrl = "/testSearchM.do";
			labelNm = "검사 항목 조회";
		}
		//fnOpenPopup(url,labelNm,gridValus,callback)
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	/*callback 호출 */
	function callFunPopup(url,iframe,gridValus){
		//병원조회
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
		}
		//검사항목조회
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
		
			if(data[0] == "/hospSearchSList.do"){
				$('#I_HOS').val(data[1].F120PCD);
				$('#I_FNM').val(data[1].F120FNM);
			}
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
	
	function init(flag){
		if(flag == '3'){
			$('#I_GAD').val('');
			$('#I_FKN').val('');
		}
		if(flag=='2'){
			$('#I_HOS').val('');
			$('#I_FNM').val('');
		}
	}

	function exportGrid(){
		var totalCnt = dataProvider.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		gridView.exportGrid({
		    type: "excel",
		    target: "local",
		    lookupDisplay : true,
		    fileName: I_LOGMNM+".xlsx",
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
<body >			
	<div class="content_inner">
	
		<!-- 검색영역 -->
		<div class="container-fluid">
			<div class="con_wrap col-md-12 srch_wrap">
				<div class="title_area open">
					<h3 class="tit_h3">검색영역</h3>
					<a href="#" class="btn_open">검색영역 접기</a>
				</div>
				<div class="con_section row">
					<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
								
					<form id="searchForm" name="searchForm">
						<input id="I_ALHNM" name="I_ALHNM" type="hidden"/>
						<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
						<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
						<input id="I_NAME" name="I_NAME" type="hidden" value="<%=ss_nam%>"/> <!-- 사용자명 병원명 -->
						<%-- <input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>"/> --%>
						<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/>
						 <div class="srch_box">
							<div class="srch_box_inner">
								<div class="srch_item">
									<label for="" class="label_basic">병원 검사코드</label>
									<input type="text" class="srch_put" id="I_TCD" name="I_TCD"    onkeydown="return enterSearch(event)" >
								</div>
								<div class="srch_item">
									<label for="" class="label_basic">검사항목</label>
									<input type="hidden" id="I_GAD" name="I_GAD">	<!-- 검사코드 + 부속코드 -->
									<input type="text" class="srch_put test_pop_srch_ico"  id="I_FKN" name="I_FKN" onclick="openPopup('3')" readonly="readonly">
									<button type="button" class="btn btn-gray btn-sm" onclick="javascript:init(3)" title="초기화">초기화</button>
								</div>          
							</div>
							
							<div class="srch_box_inner m-t-10" id="hos_div"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
								<div class="srch_item hidden_srch">
									<label for="" class="label_basic">병원</label>
									<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly"  onclick="openPopup('2')" maxlength="5" onkeydown="return enterSearch(event)" class="numberOnly"/>
									<input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" placeholder="" readonly="readonly"  onclick="openPopup('2')">
								</div>
							</div>
							<div class="btn_srch_box">
								<button type="button" class="btn_srch">조회</button>
							</div> 
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 검색영역 end -->

                    
       <div class="container-fluid">
           <div class="con_wrap col-md-12 col-sm-12">
               <div class="title_area">
                   <h3 class="tit_h3">검사 항목 조회</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onclick="exportGrid()">엑셀다운</button>
					</div>
               </div>
               <div class="con_section overflow-scr">
                   <div class="tbl_type">
						<div id="realgrid"  class="realgridH"></div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>


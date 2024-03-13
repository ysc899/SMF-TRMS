<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>추가검사 접수 조회</title>
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
		var date = new Date();
		$(".fr_date").datepicker('setDate',new Date());
		date.setDate(date.getDate() - 30);
        $("#I_FDT").datepicker('setDate',date);

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
		setGrid1();
		setGrid2();
		$(".btn_srch").click(function(){
			search();
		});
	});
	
	function setGrid1(){
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    setStyles(gridView1);
	    setGridBar(gridView1, false);

	    var fields1=[
		    	{fieldName:"R001COR"}	//회사코드
		    	, {fieldName:"GRIDDAT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	   	//접수일자
		    	, {fieldName:"R001DAT"}	   	//접수일자
		    	, {fieldName:"R001JNO", dataType: "number"}	//접수번호
		    	, {fieldName:"R001HOS"}	//병원코드
		    	, {fieldName:"R001HNM"}	//병원명
		    	, {fieldName:"R001CHN"}	//차트번호
		    	, {fieldName:"R001NAM"}	//수진자명
		    	, {fieldName:"R001AGE", dataType: "number"}	//나이
		    	, {fieldName:"R001SEXNM"}	//성별
// 		    	, {fieldName:"R001CLT"}	//검체최취일자
// 		    	, {fieldName:"R001GDT"}	//검체의뢰일
		    	, {fieldName:"R001CLT","dataType": "datetime", "datetimeFormat": "yyyyMMdd"}	   	//접수일자
		    	, {fieldName:"R001GDT","dataType": "datetime", "datetimeFormat": "yyyyMMdd"}	   	//접수일자
		    	, {fieldName:"R001STSNM"}	//상태
		    	];
				
	    
	    dataProvider1.setFields(fields1);

	    var columns1=[
				    {name:"GRIDDAT",	fieldName:"GRIDDAT", 	renderer:{showTooltip: true}, header:{text:"접수일자"},	width:80,   styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				   , {name:"R001JNO",	fieldName:"R001JNO",	header:{text:"접수번호"},		width:80,  styles: {textAlignment: "far"} }
				   , {name:"R001NAM",	fieldName:"R001NAM",	header:{text:"수진자명"},		width:90}
				   , {name:"R001CHN",	fieldName:"R001CHN",	header:{text:"차트번호"},		width:90}
				   , {name:"R001AGE",	fieldName:"R001AGE",	header:{text:"나이"},			width:70,  styles: {textAlignment: "far"}}
				   , {name:"R001SEXNM",	fieldName:"R001SEXNM",	header:{text:"성별"},			width:80,  styles: {textAlignment: "center"}}
// 				   , {name:"R001CLT",	fieldName:"R001CLT",	header:{text:"검체채취일"},		width:90,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
// 				   , {name:"R001GDT",	fieldName:"R001GDT",	header:{text:"검체의뢰일"},		width:90,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				   , {name:"R001STSNM",	fieldName:"R001STSNM",	header:{text:"접수상태"},			width:90,  styles: {textAlignment: "center"}}
	    ];
	    gridView1.setColumns(columns1);
	 	gridView1.onDataCellClicked = function (grid, index) {
			$("#I_DAT").val(grid.getValue(index.itemIndex, "R001DAT"));
			$("#I_JNO").val(grid.getValue(index.itemIndex, "R001JNO"));
	 		dataResult2();
	 		
		};
 	    if(!isNull($('#I_HOS').val())){
			dataResult1();
 	    }
	}
	
	function setGrid2(){
	    dataProvider2 = new RealGridJS.LocalDataProvider();
	    gridView2 = new RealGridJS.GridView("realgrid2");
	    gridView2.setDataSource(dataProvider2);
	    
	    setStyles(gridView2);
	    setGridBar(gridView2, false);

	    var fields2=[
			    	{fieldName:"R001COR"}	//회사코드
			    	,{fieldName:"R001DAT",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	   	//접수일자
			    	,{fieldName:"R001DTTM", dataType: "datetime", datetimeFormat: "yyyyMMddHHmmss"}	   	//의뢰일시
			    	,{fieldName:"R001JNO"}	//접수번호
			    	,{fieldName:"R001GCD"}	//검사코드
			    	,{fieldName:"R001TCD"}	//검체코드
			    	,{fieldName:"R001REJ"}	//거부사유
			    	,{fieldName:"F018OCD"}	//보험코드
			    	,{fieldName:"F010FKN"}	//검사항목
			    	,{fieldName:"F010TNM"}	//검체종류
			    	,{fieldName:"R001STSNM"}	//상태
			    	,{fieldName:"R001REJNM"}	//거부사유
		    	];
				
	    
	    dataProvider2.setFields(fields2);

	    var columns2=[
		    	,{name:"R001DAT",	fieldName:"R001DAT",	header:{text:"접수일자"},		width:80,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				,{name:"R001DTTM",	fieldName:"R001DTTM", 	renderer:{showTooltip: true}, header:{text:"의뢰일시"},	width:130,   styles: {"datetimeFormat": "yyyy-MM-dd HH:mm:ss",textAlignment:"center"}}
		    	,{name:"F018OCD",	fieldName:"F018OCD",	header:{text:"보험코드"},		width:200, renderer:{ showTooltip: true }}
		    	,{name:"R001GCD",	fieldName:"R001GCD",	header:{text:"검사코드"},		width:80}
		    	,{name:"F010FKN",	fieldName:"F010FKN",	header:{text:"검사항목"},		width:160}
		    	,{name:"F010TNM",	fieldName:"F010TNM",	header:{text:"검체종류"},		width:100}
		    	,{name:"R001REJNM",	fieldName:"R001REJNM",	header:{text:"거부사유"},		width:140}
		    	,{name:"R001STSNM",	fieldName:"R001STSNM",	header:{text:"상태"},			width:80,  styles: {textAlignment: "center"}}

	    ];
	    
	    gridView2.setColumns(columns2);
	    // 툴팁 기능
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
 		
		//조회 기간(ex > 20170101~20180101) 최대 1년으로 그 이상일경우 조회 못하게 설정
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
		
		if($('#I_HOS').val() == ""){
 			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
 			return false;
 		}
 		
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			CallMessage("273");	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
			return;
		}
 		if (interval/day > 365){
 			CallMessage("274","alert");	//최대 1년 내에서 조회해주세요.
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
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/testReqList.do"){
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
			$("#I_DAT").val("0");
			$("#I_JNO").val("0");
			dataResult2();
		}
		else if(strUrl == "/testReqDtl.do"){
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
		}
	}
	function dataResult2(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/testReqDtl.do", formData);
	}
	
	//조회
	function search(){
		if(strByteLength($("#I_CHN").val()) > 15){
			CallMessage("292", "alert", ["차트번호는 15 Byte"]);
			return false;
		}
		
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		dataResult1();
	}

	function openPopup(index)
	{
		var labelNm,strUrl,gridValus;
		strUrl = "/hospSearchS.do";
		labelNm = "병원 조회";
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,gridValus){
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
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
	
	</script>
</head>
<body>
	
<div class="content_inner">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"value="<%=menu_cd%>"  />
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>" />
		<input id="I_DAT" name="I_DAT" type="hidden"/>
		<input id="I_JNO" name="I_JNO" type="hidden"/>
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
								<label for="" class="label_basic">기간</label>
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
									 <input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT"  >
								</span>
								~
								<span class="period_area">
									 <label for="endDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT"  >
								</span>
								
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10">
							<div class="srch_item">
								<label for="" class="label_basic">수진자 명</label>
								<input type="text" class="srch_put" id="I_NAM" name="I_NAM" maxlength="40"   onkeydown="return enterSearch(event)" >
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">차트번호</label>
								<input type="text" class="srch_put" id="I_CHN" name="I_CHN" maxlength="15"   onkeydown="return enterSearch(event)" >
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item hidden_srch">
								<label for="" class="label_basic">병원</label>
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly" onClick="javascript:openPopup(1)" maxlength="5" onkeydown="return enterSearch(event)" class="numberOnly"/>
	                             <input type="text" style="width:203px" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup(1)"/>
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



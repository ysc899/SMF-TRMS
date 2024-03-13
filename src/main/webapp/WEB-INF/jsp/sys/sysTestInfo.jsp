<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>의뢰목록조회 </title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	
	<script>
	var gridView1,dataProvider1;
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
		
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "REQ_STAT", "Y");
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
		$(".btn_srch").click(function(){
			search();
		});
	});
	//조회
	function search(){
		//병원코드 입력 후 조회 할 경우
		dataResult1();
	}
	
	function setGrid1(){
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    setStyles(gridView1);
	    setGridBar(gridView1, false);

	    var fields1=[
		    {fieldName:"R001COR"}	//회사코드
	    	,{fieldName:"F010C01"}	//지정검체유무
		    ,{fieldName:"F010FKN"}	//검사항목
		    ,{fieldName:"F010TNM"}	//검체종류
		    ,{fieldName:"F018OCD"}	//보험코드
		    ,{fieldName:"F900KNM"}	//지점명
		    ,{fieldName:"GRIDDAT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	//접수일자
	    	,{fieldName:"R001DTTM", dataType: "datetime", datetimeFormat: "yyyyMMddHHmmss"}	   	//의뢰일시
		    ,{fieldName:"R001AGE", dataType: "number"}	//나이
		    ,{fieldName:"R001CHN"}		//차트번호
		    ,{fieldName:"R001CLT","dataType": "datetime", "datetimeFormat": "yyyyMMdd"}	//검체채취일
		    ,{fieldName:"R001COR"}		//회사코드
		    ,{fieldName:"R001DAT"}		//접수일자
		    ,{fieldName:"R001GCD"}		//검사코드
		    ,{fieldName:"R001GDT","dataType": "datetime", "datetimeFormat": "yyyyMMdd"}	//접수일자
		    ,{fieldName:"R001HNM"}		//병원명
		    ,{fieldName:"R001HOS"}		//병원코드
		    ,{fieldName:"R001JNO", dataType: "number"}	//접수번호
		    ,{fieldName:"R001NAM"}		//환자명
		    ,{fieldName:"R001REJ"}		//거부사유
		    ,{fieldName:"R001SEXNM"}	//성별
		    ,{fieldName:"R001STS"}		//상태
		    ,{fieldName:"R001STSNM"}	//상태
		    ,{fieldName:"R001TCD"}		//검체코드
		    ,{fieldName:"I_REJ"}	
		    ,{fieldName:"I_STS"}		//상태
		    ,{fieldName:"I_TCD"}		//검체코드
		    ,{fieldName:"I_DAT"}	
		    ,{fieldName:"I_GCD"}	
		    ,{fieldName:"I_JNO"}	
		    ,{fieldName:"I_LOGMNM"}	
		    ,{fieldName:"I_LOGMNU"}	
		];
				
	    
	    dataProvider1.setFields(fields1);

	    var columns1=[
				    {name:"GRIDDAT",	fieldName:"GRIDDAT", 	renderer:{showTooltip: true}, header:{text:"접수일자"},	width:80,   styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				   , {name:"I_JNO",		fieldName:"R001JNO",	header:{text:"접수번호"},		width:70,  styles: {textAlignment: "far"} }
				   , {name:"F900KNM",	fieldName:"F900KNM",	header:{text:"지점명"},		width:100}
				   , {name:"R001HOS",	fieldName:"R001HOS",	header:{text:"병원코드"},		width:70}
				   , {name:"R001HNM",	fieldName:"R001HNM",	header:{text:"병원명"},		width:170}
				   , {name:"R001NAM",	fieldName:"R001NAM",	header:{text:"수진자명"},		width:80}
				   , {name:"R001CHN",	fieldName:"R001CHN",	header:{text:"차트번호"},		width:90}
				   , {name:"R001AGE",	fieldName:"R001AGE",	header:{text:"나이"},			width:60,  styles: {textAlignment: "far"}}
				   , {name:"R001SEXNM",	fieldName:"R001SEXNM",	header:{text:"성별"},			width:70,  styles: {textAlignment: "center"}}
// 				   , {name:"R001CLT",	fieldName:"R001CLT",	header:{text:"검체채취일"},	width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
// 				   , {name:"R001GDT",	fieldName:"R001GDT",	header:{text:"검체의뢰일"},	width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
// 				   , {name:"R001STSNM",	fieldName:"R001STSNM",	header:{text:"상태"},			width:90,  styles: {textAlignment: "center"}}
			    	,{name:"F018OCD",	fieldName:"F018OCD",	header:{text:"보험코드"},		width:200,  renderer:{ showTooltip: true }}
					,{name:"R001DTTM",	fieldName:"R001DTTM", 	renderer:{showTooltip: true}, header:{text:"의뢰일시"},	width:130,   styles: {"datetimeFormat": "yyyy-MM-dd HH:mm:ss",textAlignment:"center"}}
			    	,{name:"R001GCD",	fieldName:"R001GCD",	header:{text:"검사코드"},		width:70,   styles: {textAlignment: "center"}}
			    	,{name:"F010FKN",	fieldName:"F010FKN",	header:{text:"검사항목"},		width:160  }
			    	,{name:"F010C01",	fieldName:"F010C01",	header:{text:"지정검체유무"},	width:90,  editable:false, styles: {textAlignment: "center"}}
			    	,{name:"F010TNM",	fieldName:"F010TNM",	header:{text:"검체종류"},		width:170  }
			    	,{name:"R001STSNM",	fieldName:"R001STSNM",	header:{text:"상태"},			width:80,  editable:false, styles: {textAlignment: "center"}}
	    ];

	    gridView1.setCheckableExpression("value['R001STS'] = 'S'", true);
	    gridView1.setColumns(columns1);
	}
	
	function replaseAll (str){
	     str.replase(/~/gi,"");
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
		ajaxCall("/sysTestInfoList.do", formData);
	}

	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
	 	var msgCd = response.O_MSGCOD;
		
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/sysTestInfoList.do"){
	    	gridView1.cancel();
			gridView1.setAllCheck(false);
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
		
		}
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
	
	function exportGrid(){

		var totalCnt = dataProvider1.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		gridView1.exportGrid({
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
<body>
	
<div class="content_inner">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"value="<%=menu_cd%>"  />
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>" />
		<input id="I_SERDAT" name="I_SERDAT" type="hidden" value="0"/>
		<input id="I_SERJNO" name="I_SERJNO" type="hidden" value="0"/>
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
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly" onClick="javascript:openPopup(1)" maxlength="5" onkeydown="return enterSearch(event)" class="srch_put numberOnly"/>
	                             <input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup(1)"/>
								<%-- <input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>" /> --%>
								<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">상태</label>
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
			<div class="con_wrap col-md-12 col-sm-12">
				<div class="title_area">
				    <h3 class="tit_h3">추가 의뢰 목록</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button>
					</div>
				</div>
				<div class="con_section overflow-scr">
				    <div class="tbl_type">
						<div id="realgrid1"  class="realgridH"></div>
				    </div>
				</div>
		    </div>
		    
		</div>
			       
	</div>    
	<!-- content_inner -->       	
</body>
</html>



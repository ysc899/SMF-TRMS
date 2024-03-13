<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>사용자로그</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<style type="text/css">
		.label_basic{width: 100px;}
	</style>
	<script>
	var gridView;
	var dataProvider;

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

		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "SUC_FAL",'Y');
		
		$('#I_SFL').html(resultCode);
		$('#I_SFL').val("F");
		
  		jcf.replace($("#I_SFL"));
  		
		var resultCode2 = getCode(I_LOGMNU, I_LOGMNM, "EVT_FLAG",'Y');		
		$('#I_EFG').html(resultCode2);		
  		jcf.replace($("#I_EFG"));
  		<%
	  		Map<String, Object> QuickParam = new HashMap<String, Object>();
	  		if(paramList.size()>0){
	  			for(int idx=0;idx<paramList.size();idx++)
	  			{
	  				QuickParam = paramList.get(idx);
	  				if(!"".equals(QuickParam.get("S008VCD"))){
		%>
						$("#<%=QuickParam.get("S007RCD")%>").val("<%=QuickParam.get("S008VCD")%>");
				  		jcf.replace($("#<%=QuickParam.get("S007RCD")%>"));
		<%
		  			}
	  			}
	  		}
	  	%>

		//달력 from~to
		$("#I_FRDT").datepicker({
			dateFormat: 'yy-mm-dd',
			maxDate: $('#I_TODT').val(),
			onSelect: function(selectDate){
				$('#I_TODT').datepicker('option', {
					minDate: selectDate,
				});
			}
		});	
		$("#I_TODT").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: $('#I_FRDT').val(),
			onSelect : function(selectDate){
				$('#I_FRDT').datepicker('option', {
					maxDate: selectDate,
				});
			}		
		});
		$(".fr_date").datepicker("setDate", new Date());
		
		setGrid();
	});
	
	function setGrid(){
		
		dataProvider = new RealGridJS.LocalDataProvider();
		gridView = new RealGridJS.GridView("realgrid");
		
		setStyles(gridView);
	    setGridBar(gridView,false);
	    
		
		gridView.setDataSource(dataProvider);
		gridView.setStyles(basicGrayBlueSkin);
	    var fields=[
	    	{fieldName:"S004UID"}
	    	,{fieldName:"S004NAM"}
	    	,{fieldName:"S004EDT"}
	    	,{fieldName:"S004ETM"}
	    	,{fieldName:"DATETIME","dataType": "datetime", "datetimeFormat": "yyyyMMddHHmmss"}	        
	    	,{fieldName:"S004MNM"}
	    	,{fieldName:"S004EFG"}
	    	,{fieldName:"S004URL"}
	    	,{fieldName:"S004PAR"}
	    	,{fieldName:"S004SFL"}
	    	,{fieldName:"S004MSG"}
			,{fieldName:"I_LOGMNU"}	//메뉴코드
			,{fieldName:"I_LOGMNM"}	//메뉴명
	    	];
	    dataProvider.setFields(fields);

	    var columns=[
			    {name:"S004UID",	fieldName:"S004UID",	header:{text:"아이디"},	width:100   }
			    ,{name:"S004NAM",	fieldName:"S004NAM",	header:{text:"사용자 명"},	width:120, renderer:{ showTooltip: true }   }
			    ,{name:"S004EDT",	fieldName:"DATETIME",	header:{text:"접속 일시"},	width:150,  styles: { "datetimeFormat": "yyyy-MM-dd HH:mm:ss",textAlignment:"center" } }
			    ,{name:"S004MNM",	fieldName:"S004MNM",	header:{text:"메뉴 명"},	width:150   }
			    ,{name:"S004EFG",	fieldName:"S004EFG",	header:{text:"이벤트"},	width:100   }
			    ,{name:"S004URL",	fieldName:"S004URL",	header:{text:"호출 명"},	width:150   }
			    ,{name:"S004PAR",	fieldName:"S004PAR",	header:{text:"파라미터"},	width:170, renderer:{ showTooltip: true }	}
			    ,{name:"S004SFL",	fieldName:"S004SFL",	header:{text:"성공여부"},	width:80, styles: {textAlignment:"center"}}
			    ,{name:"S004MSG",	fieldName:"S004MSG",	header:{text:"로그"},		width:270, renderer:{ showTooltip: true }	}
	    ];

	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);

	    gridView.onDataCellDblClicked = function (grid, index) {
	        var itemIndex = index.itemIndex;
			var gridValus = gridView.getValues(itemIndex);
	        fnOpenPopup("/sysUserLogDtl.do","사용자 로그 상세",gridValus,callFunPopup);
	    };
	    // 툴팁 기능
	    gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "S004MSG") {
	        	
	        	if(value.length>500){
		            tooltip = value.substring(0,500)+"\n......";
	        	}
	        }
	        return tooltip;
	    }	 
	    gridView.setEditOptions({
	    	  editable: false,
	    	  updatable: false
	    	});
// 	    dataResult();
	}

	function  callFunPopup(url,iframe,gridValus){
		
		iframe.gridView(gridValus);
	}
	function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysUserLogList.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/sysUserLogList.do"){
			var resultList =  response.resultList;
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	
	function search(){
		var sDate = new Date($("#I_FRDT").val());
		var eDate = new Date($("#I_TODT").val());
		var interval = eDate - sDate;
		var day = 1000*60*60*24;
		dataProvider.setRows([]);		
		gridView.setDataSource(dataProvider);
		
		if(!checkdate($("#I_FRDT"))||!checkdate($("#I_TODT"))){
			CallMessage("273");
			return;
		}
		if (interval/day > 7){
   			CallMessage("290","alert");		//최대 7일 내에서 조회해주세요.
			return false;
		}
		if(sDate > eDate){
			CallMessage("229","alert",""); // 조회기간을 확인하여 주십시오.
			return;
		}
		
		dataResult();
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
	//srch_wrap
	
	</script>
	
</head>
<body >
	
<div class="content_inner">
	<!-- 검색영역 -->
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
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
							<div class="srch_item">
								<label for="fr_date" class="label_basic">접속일자 </label>
								<span class="period_area">
								<label for="startDt" class="blind">접속일자 </label>
								<input type="text" class="fr_date startDt calendar_put" id="I_FRDT" name="I_FRDT"   >
								</span>
								~
								<span class="period_area">
									<label for="endDt" class="blind">날짜 입력</label>
									<input type="text" class="fr_date calendar_put"  id="I_TODT" name="I_TODT">
								</span>
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">병원(사용자)명</label>
								<input type="text" class="srch_put"  id="I_NAM" name="I_NAM"  onkeydown="return enterSearch(event)" >
							</div>               
						</div>
						
						<div class="srch_box_inner m-t-10">
							<div class="srch_item">
								<label for="" class="label_basic">메뉴명</label>
								<input type="text" class="srch_put"  id="I_MNM" name="I_MNM" onkeydown="return enterSearch(event)" >
							</div>
							<div class="srch_item">
								<label for="" class="label_basic">성공여부</label>
								<div class="select_area">
									<select class="form-control"  id="I_SFL"  name="I_SFL"></select> 
								</div>
							</div>                
						</div>                        
						<div class="srch_box_inner m-t-10">
							<div class="srch_item">
								<label for="" class="label_basic">이벤트</label>
								<div class="select_area">
									<select class="form-control"  id="I_EFG"  name="I_EFG"></select> 
								</div>
							</div>                
						</div>                        
						<div class="btn_srch_box">
							<button type="button" id="SearchBtn" class="btn_srch" onclick="search()">조회</button>
						</div> 
					</div>           
				</div>
			</div>
		</div>
	<!-- 검색영역 end -->            
	</form>
	<div class="container-fluid" >
		<div class="con_wrap col-md-12 col-sm-12" >
			<div class="title_area">
				<h3 class="tit_h3">사용자 로그</h3>
				<div class="tit_btn_area">
					<button type="button" class="btn btn-default btn-sm" onclick="exportGrid()">엑셀다운</button>
				</div>
			</div>
			<div class="con_section overflow-scr" >
				<div class="tbl_type">
					<div id="realgrid" class="realgridH" ></div>
				</div>
			</div>
		</div>
	</div>
					   
</div>	
<!-- content_inner -->	   	    	

</body>
</html>


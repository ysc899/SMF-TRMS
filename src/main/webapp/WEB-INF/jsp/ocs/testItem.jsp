<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>검사항목 조회</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>

	<script>

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
	    }
	}
	$(document).ready( function(){
		//TEST_INFO
		
		var ahbtn = "";
		
		//검사정보
		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "TEST_INFO",'Y');
		$('#I_SERFNM').html(resultCode);
		jcf.replace($("#I_SERFNM"));

		//학부분류 MEDI_CLS
		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "MEDI_CLS",'Y');
		$('#I_HAK').html(resultCode);
		jcf.replace($("#I_HAK"));
		
		/*관련질환 분류*/
		 getData("DISS_CLS");
		 getData("ALPHABET");
// 		 getDISS_CLS();
		
		
		$(".btn_srch").click(function(){
			dataResult();
		});
		setGrid();
	});

	function setAlhNm(inNo,Type){
		dataProvider.setRows([]);		
		gridView.setDataSource(dataProvider);
		if($("#I_ALHNM").val()==inNo){
			$("#btnDiv").find(".btn-red").addClass("btn-white").removeClass("btn-red");
			$("#I_ALHNM").val("");
		}else{
			$("#btnDiv").find(".btn-red").addClass("btn-white").removeClass("btn-red");
			$("#btn_"+inNo).addClass("btn-red").removeClass("btn-white");
			$("#I_ALHNM").val(inNo);
			if(Type=="T"){
				dataResult();
			}
		}
	}
	/*관련질환 분류*/
	function getData(pscd){
		var formData =  {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : pscd};
		ajaxCall("/getCommCode.do", formData);
	}

	function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/testItemList.do", formData);
	}
	
	var inDiv = 0;
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/getCommCode.do"){
			var data =response.resultList;
			var chkDiv ="";
			var I_PSCD =response.I_PSCD;
			$.each(data, function(i, e) {
				if(I_PSCD=="DISS_CLS"){
					chkDiv += '\n<li>';
					chkDiv += '\n     <input type="checkbox" id="I_ISC'+i+'" name="I_ISC" value="'+e.$R_SCD+'" onClick="dataResult();" />';
					chkDiv += '\n     <label for="I_ISC'+i+'" onClick="dataResult();" >'+e.$R_CNM+'</label>';
					chkDiv += '\n</li>';
				}
				if(I_PSCD=="ALPHABET"){
	 	 			chkDiv +='<button type="button" class="btn btn-white" id="btn_'+e.$R_CNM+'" onClick="setAlhNm(\''+e.$R_CNM+'\',\'T\')">'+e.$R_CNM+'</button>';
				}
			});
			if(I_PSCD=="DISS_CLS"){
	 			$("#DissCLS").append(chkDiv);
	 			inDiv++;
			}
			if(I_PSCD=="ALPHABET"){
	 			$("#btnDiv").append(chkDiv);
	 			inDiv++;
			}
// 			$("input").find("checkbox").each(function(i) {
// 				formObj = jQuery(this);
				
// 			});
				
			if(inDiv==2){
	  		<%
		  		Map<String, Object> QuickParam = new HashMap<String, Object>();
		  		if(paramList.size()>0){
		  			for(int idx=0;idx<paramList.size();idx++)
		  			{
		  				QuickParam = paramList.get(idx);
		  				String RCV = QuickParam.get("S007RCV").toString();
		  				String RCD = QuickParam.get("S007RCD").toString();
		  				String VCD = QuickParam.get("S008VCD").toString();
		  				if(!"".equals(VCD)){
			%>
							if("<%=RCV%>" == "S_TERM"){
								changeDT("<%=VCD%>");
							}else if("<%=RCV%>" == "ALPHABET"){
								setAlhNm("<%=VCD%>");
							}else{
								if($("input[name=<%=RCD%>]").attr("type")=="checkbox"){
									$("input[name=<%=RCD%>]:input[value=<%=VCD%>]").prop('checked',true);
								}else{
									$("#<%=RCD%>").val("<%=VCD%>");
							  		jcf.replace($("#<%=RCD%>"));
								}
							}
			<%
			  			}
		  			}
		  		}
		  	%>

			}
 			TabResize();
		}
		if(strUrl == "/testItemList.do"){
			var resultList =  response.resultList;
// 	        gridView.closeProgress();
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
			{fieldName:"F010COR"}		//COR 
			, {fieldName:"F010GCD"}		// 검사코드 
			, {fieldName:"F010FKN"} 	// 검사명(한글) 
			, {fieldName:"F010TCD"} 	// 검체 코드 (F999CD2 ='SAMP' ) 
			, {fieldName:"F010TNM"}  
			, {fieldName:"F010GBX"}		// 검체용기코드 (F999CD2 ='VESS' )
			, {fieldName:"F010GBNM"} 
			, {fieldName:"F010MSNM"}	// 검사법 
			, {fieldName:"T001SAV"}		// 보존방법 
			, {fieldName:"T001DAY"}		// 검사일 
			, {fieldName:"F018OCD"}		// 보험코드
			, {fieldName:"F010EED"} 	// 검사소요일수
			, {fieldName:"I_LOGMNU"	}	// 메뉴코드
			, {fieldName:"I_LOGMNM"	}	// 메뉴명
		];
	    dataProvider.setFields(fields);
	    var columns=[
			 /*  {name:"F018OCD",		fieldName:"F018OCD",		header:{text:"보험코드"},		width:110 }
			 ,{name:"F010FKN",		fieldName:"F010FKN",		header:{text:"검사항목"},		width:250 }
			 ,{name:"F010TNM",		fieldName:"F010TNM",		header:{text:"검체"},			width:150 }
			 ,{name:"F010MSNM",		fieldName:"F010MSNM",		header:{text:"검사법"},		width:150 }
			 ,{name:"T001DAY",		fieldName:"T001DAY",		header:{text:"검사일/소요일"},	width:100 }
			 ,{name:"F010GCD",		fieldName:"F010GCD",		header:{text:"검사코드"},		width:100, styles:{textAlignment:"center"} } */
	    	 
			 {name:"F010GCD",		fieldName:"F010GCD",		header:{text:"검사코드"},		width:100, styles:{textAlignment:"center"} }
			 //,{name:"F018OCD",		fieldName:"F018OCD",		header:{text:"보험코드"},		width:110 }
			 ,{name:"F010FKN",		fieldName:"F010FKN",		header:{text:"검사항목"},		width:250 }
			 ,{name:"F010TNM",		fieldName:"F010TNM",		header:{text:"검체"},			width:150 }
			 ,{name:"F010MSNM",		fieldName:"F010MSNM",		header:{text:"검사법"},		width:150 }
			 ,{name:"T001DAY",		fieldName:"T001DAY",		header:{text:"검사일"},	width:100 }
			 ,{name:"F010EED",		fieldName:"F010EED",		header:{text:"소요일"},	width:100 }
	    ];
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    
	    gridView.onDataCellDblClicked = function (grid, index) {
	    	openPopup(index);
	    };
// 	    dataResult();

	}

	var gridIdx;
	function openPopup(index){

        var itemIndex = index.itemIndex;
		var gridValus = gridView.getValues(itemIndex);
		
		//RA Factor IgG
        fnOpenPopup("/testItem01.do",gridValus["F010FKN"]+" 검사 항목 상세",gridValus,callFunPopup);
	}
	function  callFunPopup(url,iframe,gridValus){
		
		iframe.gridViewRead(gridValus);
	}
	
	function init(){
		$("#I_SERFNM").val("");
		jcf.replace($("#I_SERFNM"));
		$("#I_ALHNM").val("");
		$("#I_SERNM").val("");
		$("#I_HAK").val("");
		jcf.replace($("#I_HAK"));
		$("#btnDiv").find(".btn-red").addClass("btn-white").removeClass("btn-red");
	
		$("input[name=I_ISC]").prop('checked', false); 

  		<%
	  		if(paramList.size()>0){
	  			for(int idx=0;idx<paramList.size();idx++)
	  			{
	  				QuickParam = paramList.get(idx);
	  				String RCV = QuickParam.get("S007RCV").toString();
	  				String RCD = QuickParam.get("S007RCD").toString();
	  				String VCD = QuickParam.get("S008VCD").toString();
	  				if(!"".equals(VCD)){
		%>
						if("<%=RCV%>" == "S_TERM"){
							changeDT("<%=VCD%>");
						}else if("<%=RCV%>" == "ALPHABET"){
							setAlhNm("<%=VCD%>");
						}else{
							if($("input[name=<%=RCD%>]").attr("type")=="checkbox"){
								$("input[name=<%=RCD%>]:input[value=<%=VCD%>]").prop('checked',true);
							}else{
								$("#<%=RCD%>").val("<%=VCD%>");
						  		jcf.replace($("#<%=RCD%>"));
							}
						}
		<%
		  			}
	  			}
	  		}
	  	%>
		gridView.cancel();
		dataProvider.clearRows();
		gridView.setDataSource(dataProvider);

	}
	
	
	</script>
</head>
<body >			
	<div class="content_inner">
	
		<!-- 검색영역 -->
		<form id="searchForm" name="searchForm"  onsubmit="return false;" method="post"  >
			<div class="container-fluid">
				<div class="con_wrap col-md-12 srch_wrap">
					<div class="title_area open">
						<h3 class="tit_h3">검색영역</h3>
						<a href="#" class="btn_open">검색영역 접기</a>
					</div>
					<div class="con_section row">
						<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
									
							<input id="I_ALHNM" name="I_ALHNM" type="hidden"/>
							<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
							<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
							 <div class="srch_box">
								<div class="srch_box_inner">
								
									<div class="srch_item">
										<label for="" class="label_basic">검사정보</label>
										<div class="select_area">
											<select class="form-control" id="I_SERFNM"  name="I_SERFNM"></select> 
										</div>
										<input  type="text"  id="I_SERNM" name="I_SERNM" maxlength="49"    onkeydown="return enterSearch(event)" >
									</div>
	
	
									 <div class="srch_item">
										<label for="" class="label_basic">학부분류</label>
										<div class="select_area">
										<select id="I_HAK"  name="I_HAK"  class="form-control"></select> 
										</div>
									</div>            
								</div>
								
								<div class="srch_box_inner m-t-10">
									<div class="srch_item srch_item_v3">
										<label for="" class="label_basic">알파벳 분류</label>
										<div class="btn_group" role="group" aria-label="..." id="btnDiv">
										</div>
									</div>       
								</div> 
								
								<div class="srch_box_inner m-t-10">
									<div class="srch_item srch_item_v3">
										<label for="" class="label_basic">관련질환 분류</label>
										<ul class="chk_lst" id="DissCLS">
										</ul>
									</div>
								</div>
								<div class="btn_srch_box">
									<button type="button" class="btn_srch">조회</button>	
									<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init()">검색 초기화</button>
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
                   <h3 class="tit_h3">검사 항목 조회</h3>
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


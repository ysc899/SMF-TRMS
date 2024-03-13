<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	
	<!--
	<link rel="stylesheet" href="/css/commonCss.css"> 
    <script type="text/javascript" src="/plugins/jquery/js/jquery.js"></script>
    <script type="text/javascript" src="/js/commonJs.js"></script>
     -->
	<%@ include file="/inc/common.inc"%>		
	
	<title>결과관리::항목별 결과관리</title>
	
	<style>
		.line9 {border-bottom:1px solid #d7d7d7;}
		.line9 td {padding:10px 10px 10px 10px;}
		.line9_l {border-bottom:1px solid #d7d7d7; text-align:left;}
		.line10 {border-bottom:1px solid #cecece; font-weight:bold; background:#e5e5e5;}
	</style>	
	
	<%
		String Param_HOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_HOS"), "");
		String Param_FDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FDT"), "");
		String Param_TDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_TDT"), "");
	%>
	
	<script>
		
	var pmcd = "";
	var I_LOGMNU = "RSTITEM";
	var I_LOGMNM = "항목별 결과조회";
	var treeDataProvider1, treeView1;
	var rows = "";	
	var count = 0;		// 페이지 확인 0이면 첫 페이지
	var i_icnt = 0;     // 데이터 카운팅	
	
	// 파견사원 병원유무 Flag
   	var s_paguen_hos_yn = "<%= ss_paguen_hos_yn %>";
// 	console.log("### s_paguen_hos_yn :: "+s_paguen_hos_yn);
   	
	var Param_HOS = "<%=Param_HOS%>";
	var Param_FDT = "<%=Param_FDT%>";
    var Param_TDT = "<%=Param_TDT%>";
//	console.log("### Param_HOS :: "+Param_HOS);
	
	var s_uid = "<%= ss_uid %>";
   	var s_ecf = "<%= ss_ecf %>";
   	var s_hos = "<%= ss_hos %>";
	var s_nam = "<%= ss_nam %>";
	var s_syn = "<%= ss_syn %>";
	
    //session
    $(document).ready( function(){
    	
		//sms 버튼 활성화 여부
		if(s_syn == "Y"){
			$('#smsDisplay').show();	
		}
		
    	$("#I_ID").val(s_uid);
    	$("#I_ECF").val(s_ecf);
    	$('#I_HOS').val(s_hos);
    	if(s_ecf != "E"){
			$('#I_FNM').val(s_nam);
    	}
    	
    	$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
	    
		$('#DivHosSearch').hide();
		
		//사원일경우 or 파견사원 병원인 경우 >> 병원 검색 div 화면에 출력.
		if(s_ecf == "E" || s_paguen_hos_yn != ""){
			$('#DivHosSearch').show();
		}
		
		// 20190325 현주연 주임 요청사항
		$('#rstPreButton').hide();
		
		
		if(s_ecf == "E"){
			$('#DivHosSearch').show();
			
		}
		
	    treeDataProvider1 = new RealGridJS.LocalTreeDataProvider();
	    treeView1 = new RealGridJS.TreeView("realgrid1");
	    setStyles(treeView1);
	    treeView1.setHeader({
	    	height:40
	    })
	    treeView1.setDataSource(treeDataProvider1);
	    treeView1.setEditOptions({
	    	insertable: false,
	    	appendable: true,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields1 = [
	    	{fieldName: "SORT_KEY"},
	    	{fieldName: "TREE"},
	    	{fieldName: "DAT"},
	    	{fieldName: "DAT2"},
	    	{fieldName: "JNO"},
	    	{fieldName: "GCD"},
	    	{fieldName: "ACD"},
	    	{fieldName: "FKN"},
	    	{fieldName: "BDT"},
	    	{fieldName: "BDT2"},
	    	{fieldName: "AGE"},
	    	{fieldName: "SEX"},
	    	{fieldName: "NAM"}, //이전결과/ 시계열
	    	{fieldName: "NU1"}, // 하이 Y
	    	{fieldName: "JOB"}, // 로우 Y
	    	{fieldName: "HAK"},  // 양성 Y
	    	{fieldName: "RST"},
	    	{fieldName: "C03"},
	    	{fieldName: "C07"},
	    	{fieldName: "IMG"},
	    	{fieldName: "REF"},
	    	{fieldName: "REF1"},
	    	{fieldName: "PRF"},
	    	{fieldName: "ETC"},
	    	{fieldName: "ADDRESS"},
	    	{fieldName: "ETCN"},
	    	{fieldName: "CHN"},
	    	{fieldName: "JUNO"},
	    	{fieldName: "STS"},
	    	{fieldName: "REDTEXT"},
	    	{fieldName: "STSD"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    treeDataProvider1.setFields(fields1);
	  var columns1 = [
	    	
		  	{name: "DAT", 	fieldName: "DAT2", 	width: "100", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "접수일자"}}
	      ,	{name: "JNO", 	fieldName: "JNO", 	width: "50", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "접수번호"}}
	      , {name: "CHN", 	fieldName: "CHN", 	width: "80", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "차트번호"}}
	      , {name: "NAM", 	fieldName: "NAM", 	width: "60", 	styles: {textAlignment: "near"},    	renderer:{showTooltip: true}, header: {text: "수진자"}}
	      , {name: "SEX", 	fieldName: "SEX", 	width: "30", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "성별"}}
	      , {name: "AGE", 	fieldName: "AGE", 	width: "30", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "나이"}}
	      , {name: "JUNO", 	fieldName: "JUNO", 	width: "70", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "생년월일"}}
	      ,	{name: "ETC", 	fieldName: "ETC", 	width: "100", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "검체번호"}}
	      ,	{name: "ADDRESS", 	fieldName: "ADDRESS", 	width: "100", 	styles: {textAlignment: "near"},    	renderer:{showTooltip: true}, header: {text: "실거주지"}}
	      , {name: "FKN", 	fieldName: "FKN", 	width: "120", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "검사명"}}
	      , {name: "RST", 	fieldName: "RST", 	width: "120", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "결과"}}
	      , {name: "C07", 	fieldName: "C07", 	width: "40", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "H/L"}}
	      , {name: "REF", 	fieldName: "REF", 	width: "160", 	styles: {textAlignment: "near", textWrap:"explicit"},    renderer:{showTooltip: true}, header: {text: "참고치"}}
	      , {name: "BDT", 	fieldName: "BDT2", 	width: "80", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "보고일자"}}
	      , {name: "STSD", 	fieldName: "STSD", 	width: "80", 	styles: {textAlignment: "center"},    	renderer:{showTooltip: true}, header: {text: "검사진행"}}
	    ];
	    treeView1.setColumns(columns1);
	    
	    //treeView1.setColumnProperty("PRF", "buttonVisibility", "always");
	    //treeView1.setColumnProperty("PRF2", "buttonVisibility", "always");
	    //treeView1.setColumnProperty("PNF", "buttonVisibility", "always");
	    
	    treeView1.setColumnProperty("C07","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "C07");
	    	   if (index.column === "C07") {
	    	    switch (col1Value) {
	    	      case 'H' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
	    	        ret.foreground = "#ffff0000";
	    	        break;
	    	      case 'L' :
	    	        //ret.editable = false;
	    	        //ret.readOnly = true;
	    	        ret.foreground = "#ff0000ff";
	    	   }
	    	   return ret;
	    	  }
	    	});

	    treeView1.setColumnProperty("RST","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "C07");
	    	   var col2Value = grid.getValue(index.itemIndex, "REDTEXT");
	    	   var col3Value = grid.getValue(index.itemIndex, "RST");
	    	   //var col2Value = grid.getValue(index.itemIndex, "O_FLG");
	    	   //var col2Value = dataProvider2.getValue(index.itemIndex, "O_FLG");
	    	   if (index.column === "RST") {
	    	    switch (col1Value) {
	    	      case 'H' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
	    	        ret.foreground = "#ffff0000";
	    	        break;
	    	      case 'L' :
	    	        //ret.editable = false;
	    	        //ret.readOnly = true;
	    	        ret.foreground = "#ff0000ff";
	    	    }
	    	    
	    	    // 2020.03.18 - 결과값 MWR003M@ 테이블에 등록된 경우 붉은색/빨강색으로 결과값을 표시한다.
	    	    switch (col2Value) {
	    	      case 'R' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
													  
	    	        ret.foreground = "#ffff0000";
					
					  
												   
										   
					
					  
												
								  
	    	        //break; 
	    	   	}
	    	    
	    	 	// 71252 종목인 경우, '<' 부등호가 없는 경우에는 빨강색 표시되어야 함. (단, 부속코드가 01,02,03 3개만 해당됨)
	    	    // 71259 종목인 경우, '이하' 문구가 없는 경우에는 빨강색 표시되어야 함. (단, 부속코드가 01,02 2개만 해당됨)
	    	    // 71259 종목인 경우, '이하' 문구가 없는 경우에는 빨강색 표시되어야 함. (단, 부속코드가 01,02 2개만 해당됨)
	    	    if(grid.getValue(index.itemIndex, "RST").indexOf("보고예정") <= -1 && // 보고예정이 포함되어 있지 않으면서
	    	    	(col3Value.indexOf("<") <= -1 && grid.getValue(index.itemIndex, "GCD") == "71252" && grid.getValue(index.itemIndex, "ACD") != "00")
																	 
	    	    	||(col3Value.indexOf("이하") <= -1 && col3Value.indexOf("미만") <= -1 && grid.getValue(index.itemIndex, "GCD") == "71259" && grid.getValue(index.itemIndex, "ACD") != "00")
	    	    	||(col3Value.indexOf("<") <= -1 && grid.getValue(index.itemIndex, "GCD") == "71257")
	    	    	
	    	    	// 2019.12.24 - 21295 종목인 경우, 'Reactive' 문구가 포함된 결과일 경우 빨강색 표시되어야 함. (단, 'Non-Reactive' 문구가 포함된 경우는 제외함)
	    	    	||(col3Value.indexOf("Reactive") > -1 && grid.getValue(index.itemIndex, "GCD") == "21295" && col3Value.indexOf("Non-Reactive") <= -1)
	    	    	
	    	    	// 2021.05.10 - 72185 / 부속01 인 경우, 결과에 '+' 문자가 포함되면 붉은색 표시
	    	    	||(grid.getValue(index.itemIndex, "GCD") == "72185" && grid.getValue(index.itemIndex, "ACD") == "01" && col3Value.indexOf("+") > -1)
	    	    ){
	    	    	//console.log("## 결과값 빨강색 표시 ##");
    	    		ret.foreground = "#ffff0000";
    	    	}
	    	    
	    	 		// 2021.05.10 - 72185 / 부속02 인 경우, 결과에 '+' 문자가 포함되면 파랑색 표시
	    	    if(grid.getValue(index.itemIndex, "GCD") == "72185" && grid.getValue(index.itemIndex, "ACD") == "02" && col3Value.indexOf("+") > -1){
	    	    	//console.log("## 결과값 파랑색 표시 ##");
	    	    	ret.foreground = "#ff0000ff";
	    	    }
	    	    
	    	    return ret;
	    	  }
	    	});
	    
	    treeView1.setColumnProperty("STSD","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "STSD");
	    	   if (index.column === "STSD") {
	    	    switch (col1Value) {
	    	      case '완료' :
	    	        //ret.editable = true;
	    	        //ret.readOnly = false;
	    	        ret.foreground = "#ff0000ff";
	    	        break;
	    	   }
	    	   return ret;
	    	  }
	    	});
	    
	    treeView1.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	       
	        var tooltip = value;
	        if (column == "REF") {
	            //tooltip = grid.getValue(itemIndex, "O_REF").replace(/"\n"/gi,"<br>");
	        	tooltip = grid.getValue(itemIndex, "REF1");
	        }
	        return tooltip;
	        
	        
	    }
	    var stateBar1 = treeView1.getStateBar();
	    stateBar1.visible = false;
	    treeView1.setStateBar(stateBar1);
	    
	    //기간구분
	    var resultCode = "";
		resultCode = getCode(I_LOGMNU, I_LOGMNM, "TERM_DIV", 'N');
		$('#I_DGN').html(resultCode);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_DGN"));
		
		//검사분야
		var resultCode1 = "";
		resultCode1 = getCode(I_LOGMNU, I_LOGMNM, "TEST_CLS", 'Y');
		$('#I_HAK').html(resultCode1);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_HAK"));
		
		//검사진행상황
		var resultCode2 = "";
		resultCode2 = getCode(I_LOGMNU, I_LOGMNM, "TEST_STAT", 'Y');
		$('#I_STS').html(resultCode2);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_STS"));
		
		//진료과/진료의사
		var resultCode3 = "";
		resultCode3 = getCode(I_LOGMNU, I_LOGMNM, "TREAT_DIV");
		$('#I_PJKNF').html(resultCode3);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_PJKNF"));
		
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
		
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 2);
        $("#I_FDT").datepicker('setDate',yearAgo);
        $("#I_TDT").datepicker('setDate',new Date);
        //$("#I_FDT").attr( 'readOnly' , 'true' );
        //$("#I_TDT").attr( 'readOnly' , 'true' );
        
	    //getTermCode();
	    //getTermCode();
	    //dataResult();
	    /*
        var modal = $('#sts-Modal');
        $('#modal_open').addEventListener("click", function(){
        	modal.style.display = "block";
        });
        
        $('#modal_close').addEventListener("click", function(){
        	modal.style.display = "none";
        });
        
        window.addEventListener("click", function(event){
        	if(event.target == modal){
        		modal.style.display = "none";
        	}
        });
        */
        //추가 검사
        
     	// 처음 수진자별 결과조회 화면이 열릴 때, 파견사원 병원인 경우는 병원코드 입력값을 빈값으로 셋팅한다.
        // (이유는 파견사원 병원은 1개 계정으로 N개 병원 검사결과 데이터를 조회해야하므로,
        // 병원코드 값이 존재하지 않아야 N개 병원 검사결과 데이터가 모두 조회 가능하다.)
        if(s_paguen_hos_yn != ""){
	        $("#I_HOS").val('');        	
        }
        
        // 기간 검색 구분 - 1일,3일,1개월,3개월
		var buttonList = getTerm(I_LOGMNU, I_LOGMNM, "S_TERM");
		$('#I_S_TERM').append(buttonList);
        
		// 결과 구분
		var chkDiv = getCheckBox(I_LOGMNU, I_LOGMNM, "RESULT_DIV");
		$("#RGN").append(chkDiv);
		
		//검색영역 접기
        $('.btn_open').click();
		
        /** 메인에서 들어올경우 조회 조건 셋팅 **/
	   	if(!isNull(Param_HOS)){		// 사원/파견사용자인 경우
	   		
			$("#I_HOS").val(Param_HOS);     // 병원코드
			
			Param_FDT = Param_FDT.substring(0,4)+"-"+Param_FDT.substring(4,6)+"-"+Param_FDT.substring(6,8); 
	    	Param_TDT = Param_TDT.substring(0,4)+"-"+Param_TDT.substring(4,6)+"-"+Param_TDT.substring(6,8);
	    	
	    	$("#I_FDT").datepicker('setDate', Param_FDT);		// 접수일자 - 시작
			$("#I_TDT").datepicker('setDate', Param_TDT);	 	// 접수일자 - 종료
				
			search();	
	    }else{
			<%
	  		Map<String, Object> QuickParam = new HashMap<String, Object>();
	  		if(paramList.size()>0){
	  			for(int idx=0;idx<paramList.size();idx++)
	  			{
	  				QuickParam = paramList.get(idx);
	  				String RCV = QuickParam.get("S007RCV").toString();
	  				String RCD = QuickParam.get("S007RCD").toString();
	  				String VCD = QuickParam.get("S008VCD").toString();
	  				if(!"".equals(QuickParam.get("S008VCD"))){
			%>
					if("<%=RCV%>" == "S_TERM"){
						changeDT("<%=VCD%>");
					}else{
						if("<%=RCV%>" == "TERM_DIV"){
							if(isNull("<%=VCD%>")){
								$("#<%=RCD%>").val("D");
							}else{
								$("#<%=RCD%>").val("<%=VCD%>");
							}
						}else if("<%=RCV%>" == "RESULT_DIV"){
							$("input[id=I_RGN<%=VCD%>]:input[value=<%=VCD%>]").prop('checked',true);
						}else{
							$("#<%=RCD%>").val("<%=VCD%>");
						}
				  		jcf.replace($("#<%=RCD%>"));
					}
		<%
	  				}
	  			}
	  		}
	  	%>
		  	$('#I_PNN_P').addClass("disabled");
		    $('#I_PNN_N').addClass("disabled");
	    
	  		if($("#I_ECF").val() == "C"){
	  			search();	
	  		};
		}
  		
	});
    
    window.onload = function(){
    	var modal = document.getElementById("sts-Modal");
    	
    	var modalchk = "N";
    	
    	window.addEventListener("click", function(event){
			modal.style.display = "none";
        });		
    }
    
    function init(){
    	$('#I_FKN').val("");
    	$('#I_GAD').val("");
    	
    }
    
    function init2(){
    	var quickCount = 0;
    	
    	//검색조건 초기화
    	changeDT("1M");
    	
    	$('#I_DGN').val("D");
  		$('#I_NAM').val("");  		
  		$('#I_CHN').val("");
  		$('#I_FKN').val("");
  		$('#I_GAD').val("");
  		$('#I_ETC').val("");
  		$('#I_HAK').val("");
  		$('#I_STS').val("");
  		
  		//사원일경우 병원정보 초기화
  		if($("#I_ECF").val() == "E"){
  			$('#I_HOS').val("");
  	  		$('#I_FNM').val("");
		}
  		
  		//사원일경우 병원정보 초기화 or 파견사원 병원인 경우 초기화
  		if($('#I_ECF').val() == "E" || s_paguen_hos_yn != ""){
  			$('#I_HOS').val("");
  	  		$('#I_FNM').val("");
		}
  		
  		jcf.replace($("#I_DGN"));
  		jcf.replace($("#I_HAK"));
  		jcf.replace($("#I_STS"));
  		$('#I_RGN1').attr("checked", false);
  		$('#I_RGN2').attr("checked", false);
  		$('#I_RGN3').attr("checked", false);
  		
  		//퀵셋업 조회조건 반영
    	<%
  		if(paramList.size()>0){
  			
  			for(int idx=0;idx<paramList.size();idx++)
  			{
  				QuickParam = paramList.get(idx);
  				String RCV = QuickParam.get("S007RCV").toString();
  				String RCD = QuickParam.get("S007RCD").toString();
  				String VCD = QuickParam.get("S008VCD").toString();
  				if(!"".equals(QuickParam.get("S008VCD"))){
		%>
				quickCount++;
				if("<%=RCV%>" == "S_TERM"){
					changeDT("<%=VCD%>");
				}else{
					if("<%=RCV%>" == "TERM_DIV"){
						if(isNull("<%=VCD%>")){
							$("#<%=RCD%>").val("D");
						}else{
							$("#<%=RCD%>").val("<%=VCD%>");
						}
					}else if("<%=RCV%>" == "RESULT_DIV"){
						$("input[id=I_RGN<%=VCD%>]:input[value=<%=VCD%>]").prop('checked',true);
					}else{
						$("#<%=RCD%>").val("<%=VCD%>");
					}
			  		jcf.replace($("#<%=RCD%>"));
				}
	<%
  				}
  			}
  		}
  	%>
  		//퀵셋업 조회조건 없을경우 다시 초기화
	  	if(quickCount == 0){
	  		changeDT("1M");
	  		$('#I_DGN').val("D");
	  		$('#I_NAM').val("");  		
	  		$('#I_CHN').val("");
	  		$('#I_FKN').val("");
	  		$('#I_GAD').val("");
	  		$('#I_ETC').val("");
	  		$('#I_HAK').val("");
	  		$('#I_STS').val("");
	  		
	  		//사원일경우 병원정보 초기화
	  		if($("#I_ECF").val() == "E"){
	  			$('#I_HOS').val("");
	  	  		$('#I_FNM').val("");
			}
	  	
	  		jcf.replace($("#I_DGN"));
	  		jcf.replace($("#I_HAK"));
	  		jcf.replace($("#I_STS"));
	  		$('#I_RGN1').attr("checked", false);
	  		$('#I_RGN2').attr("checked", false);
	  		$('#I_RGN3').attr("checked", false);  		
	  	}
  	}
    
    
    function dataResult(data){
		var formData = $("#searchForm").serialize();
		if(isNull(data)){
			data = "&I_DAT="+"";
			data += "&I_JNO="+"";
		}
		formData += data;
		ajaxCall("/rstItemList.do", formData);
	}
	
	function paging(pnnId){

		
		if($('#I_PNN_'+pnnId).attr("class") == "disabled"){
	         return;
	     }
		
		var data = "";
		var page = Number($('#I_CNT').val());
		var row = Number($('#I_ROW').val());
		
		if(pnnId == "P"){ 			// 이전결과 페이지
			$('#I_PNN').val('P');
			count--;
			if(row != 0){
				row = row - 5000;
				$('#I_ROW').val(row);	
			}
		}
		if(pnnId == "N"){ 			// 다음 결과 페이지
			$('#I_PNN').val('N');
			count++;
			row = row + 5000;
			$('#I_ROW').val(row);
			//$('#I_ICNT').val(page+1);
			
		}
		if(count == 0){
			$('#I_PNN_N').removeClass("disabled");
			$('#I_PNN_P').addClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		}
		dataResult(data);
	}
	
	
	/*
	function paging(pnnId){

	
		if($('#I_PNN_'+pnnId).attr("class") == "disabled"){
	         return;
	     }
		
		var data = "";
		var page = Number($('#I_CNT').val());
		
		if(pnnId == "P"){ 			// 이전결과 페이지
			$('#I_PNN').val('P');
			count--;
			rows[0].DAT
			$('#I_DAT').val(rows[0].DAT);
			$('#I_JNO').val(rows[0].JNO);
			data = "&I_GCD="+rows[0].GCD;
			data += "&I_ACD="+rows[0].ACD;
			
			$('#I_ICNT').val(page);
		}
		if(pnnId == "N"){ 			// 다음 결과 페이지
			$('#I_PNN').val('N');
			count++;
			//data = "&I_DAT="+dataProvider2.getValue((page-1), "DAT");
			//data += "&I_JNO="+dataProvider2.getValue((page-1), "JNO");
			$('#I_DAT').val(rows[page-1].DAT);
			$('#I_JNO').val(rows[page-1].JNO);
			data = "&I_GCD="+rows[page-1].GCD;
			data += "&I_ACD="+rows[page-1].ACD;
			$('#I_ICNT').val(page+1);
			
		}
		if(count == 0){
			$('#I_PNN_N').removeClass("disabled");
			$('#I_PNN_P').addClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		}
		dataResult(data);
	}
	*/
	// ajax 호출 result function
	function onResult(strUrl,response){
		
		
		if(strUrl == "/rstItemList.do"){
			var resultList =  response.resultList;
			
			
			for (var i in resultList){
				resultList[i].REF1 = resultList[i].REF1.replace(/@@/gi,"\n").trim();
				//resultList[i].REF = resultList[i].REF;
				
				if(resultList[i].DAT != 0){
					resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
				}
				
				if(resultList[i].BDT != 0){
					resultList[i].BDT2 = resultList[i].BDT.substring(0,4)+"-"+resultList[i].BDT.substring(4,6)+"-"+resultList[i].BDT.substring(6,8);						   
				}
			}
			
			treeView1.closeProgress();
			
			/*
			for (var i in resultList){
				if(resultList[i].PRF == "N"){
					resultList[i].PRF = "";
				}
			}
			*/
			
			//treeDataProvider1.clearRows();
			
			
			treeDataProvider1.setRows(resultList, "TREE", false);
			treeView1.setDataSource(treeDataProvider1);
			treeView1.setAllCheck(false);
			//console.log("treeDataProvider1.getRowCount() = " + treeDataProvider1.getRowCount());
			/*
			i_icnt = 0;
			for (var d in treeDataProvider1.getRows(0, treeDataProvider1.getRowCount())){
				if(treeDataProvider1.getValue(d, "ACD") == "" || treeDataProvider1.getValue(d, "ACD") == "00"){
					i_icnt++;
				}
				//treeDataProvider1.setRows(resultList, "TREE", false);
			}
			*/
			//treeProvider.removeRows([10, 30, 40]);
			
			
			
			//treeDataProvider1.setFilters(filters);
			
			//treeDataProvider1.setFilters(filters).getRowCount();
			
			//alert(treeDataProvider1.setFilters(filters).getRowCount());
			
			
			
			//console.log(i_icnt + " = " +  $('#I_ICNT').val());
			var dataCnt = 0;
			rows = treeDataProvider1.getJsonRows(-1, false, "rows", "icon");
			
			//console.log("rows = " + JSON.stringify(rows));
			
			
			
			for (var i in rows){
				//console.log("rows[i].ACD = " + rows[i].ACD);
				/*
				if(rows[i].ACD == "" || rows[i].ACD == "00"){
					dataCnt++;	
				}
				*/
				dataCnt++;
			}
			
			/*
			for (var i in treeDataProvider1.getRows(0, treeDataProvider1.getRowCount())){
				if(treeDataProvider1.getValue(i, "ACD") == "" || treeDataProvider1.getValue(i, "ACD") == "00"){
					dataCnt++;	
				} 
				
			}
			*/
			//console.log("dataCnt = " + dataCnt);
			
			if(dataCnt < $('#I_ICNT').val()){					// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
				$('#I_PNN_N').addClass("disabled");
			}else{
				if($('#I_PNN').val() != "P"){
					treeDataProvider1.hideRows([(treeDataProvider1.getRowCount())]);						// 보여줄 데이터 마지막 값 숨김
				}
				$('#I_PNN_N').removeClass("disabled");
				
			}
			
			if($('#I_PNN').val() == "C"){											// 첫페이지일 경우 이전 페이지 버튼 숨김
				$('#I_PNN_P').addClass("disabled");
			}
			
		}
	}
	
	//조회
	function search(){
		//병원코드 입력 후 조회 할 경우
 		/* if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		} */
		
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
 			
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
 		
			// 2020.04.21 (수)
 			// 검색초기화 후, 조회를 할 경우, 
 			// 파견사원인지 체크 후, 병원/사원 값을 로그인 유저 정보로 셋팅되도록 한다.
 			if(s_paguen_hos_yn != ""){
 				$('#I_ECF').val(s_ecf);
 			}
		}else{
			
			// 병원코드 미입력되었을 경우, 병원이름도 미입력 상태로 바꿈
			if($('#I_HOS').val() == ""){
				$('#I_FNM').val("");
			}else{
				// 2020.04.21 (수)
				// 병원코드 입력시 병원/사원 정보를 사원으로 변경하여
				// 사원처럼 입력한 병원코드 1곳 데이터를 조회하도록 한다.
	 			if(s_paguen_hos_yn != ""){
	 				$('#I_ECF').val("E");
				}
			}
		}
		
		//init();
		treeView1.cancel();
		treeDataProvider1.clearRows();
		
		if(!searchValidation()) {return;}
		/*
		if($("#I_TDT").val()==""||$("#I_FDT").val()==""){
	         CallMessage("273","alert");      //조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
	         return ;
	      }
		*/
		
		//treeView1.setDataSource(treeDataProvider1);
		
		
		var url = "";
		var cnt = Number($('#I_CNT').val());
		$('#I_ICNT').val(Number($('#I_CNT').val())+1);
		$('#I_ROW').val(Number($('#I_IROW').val()));
		$('#I_PNN').val("C");
		//if(!saveValidation()) { return;}
		//이미지 초기화
		
		dataResult();
	}
	
	function searchValidation(){
		
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		
		/* 
		if($('#I_NAM').val() == ""){
			CallMessage("245", "alert", ["수진자 명을"], dataClean);
			return false;
		}

		if($('#I_CHN').val() == ""){
			CallMessage("245", "alert", ["수진자 명을 검색하여 차트 번호를"], dataClean);
			return false;
		}		
		
		
		var interval = eDate - sDate;
		var day = 1000*60*60*24;

		if (interval/day > 365){
   			CallMessage("274","alert","", dataClean);		//최대 1년 내에서 조회해주세요.
			return false;
		}
		
		
		*/
		
		// 유효성검사 - 최대 검색 기간 체크 !!
		var now = new Date();
	    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
			CallMessage("301","alert",["10"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
			return ;
	   	}
		
		// 변경전 : 검색조건에 병원코드가 입력되지 않으면 검색이 불가능함.
		// 변경후 : 검색조건에 병원코드가 입력되지 않으면서 사원인 경우만 검색이 불가능 하도록 변경. 파견사원의 경우, 병원코드가 비어있어도 검색이 되도록 하기 위해서.
		if($('#I_HOS').val() == "" && $('#I_ECF').val() =="E" && s_paguen_hos_yn == ""){
			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"],dataClean);
			return false;
		}
		
		if(strByteLength($("#I_CHN").val()) > 15){
			CallMessage("292", "alert", ["차트번호는 15 Byte"], dataClean);
			return false;
		}
		
		if(strByteLength($("#I_ETC").val()) > 50){
			CallMessage("292", "alert", ["검체번호는 50 Byte"], dataClean);
			return false;
		}
		
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			CallMessage("273", "alert","", dataClean);
		   return false;
		}
		
		if(sDate > eDate){
			CallMessage("229","alert","", dataClean); // 조회기간을 확인하여 주십시오.
			return false;
		}
	      
		return true;
		
	}
	
	function dataClean(){
		init();
		treeView1.cancel();
		treeDataProvider1.clearRows();
	}

	//오더의뢰지
	/*
	function mobile_order_view(encPrm) {
		    var win = window.open('./order_pic_detail.jsp?UUID=  &encPrm='+encPrm,"imgViewDown","width=755, height=800, scrollbars=yes, location=no");
		    win.focus();
	}
	*/
	//추가검사
	function openPopup(str){
		var dataRows = treeView1.getCheckedRows();
		if(str == "1" || str == "2"|| str == "7"|| str == "8"){
			if(dataRows.length > 1){
				messagePopup("","","추가검사, 오더 의뢰지, 이전결과, 시계열은 1건만 선택 가능합니다." );
				return;
			}
			if(dataRows.length != 1){
				messagePopup("","","최소 1건이상 선택해야 합니다." );
				return;
			} 
		}
		if(str == "99" ){
			if(dataRows.length == 0){
				messagePopup("","","최소 1건이상 선택해야 합니다." );
				return;
			} 
		} 
		
		var formData = $("#searchForm").serialize();
		
		if(str == "1"){
			
			for(var i in dataRows){
				//data.push(treeDataProvider1.getValues(dataRows[i], ""));
				$('#I_DTLDAT').val(treeDataProvider1.getValue(dataRows[i], "DAT"));
				$('#I_DTLJNO').val(treeDataProvider1.getValue(dataRows[i], "JNO"));
				
				//jsonRow.push(dataProvider.getJsonRow(e));
			}
			var formData = $("#searchForm").serialize();
			fnOpenPopup("/testReq01Popup.do","추가의뢰",formData,callFunPopup);	
		} else if(str == "2"){
			var data = "";
			for(var i in dataRows){
				//data.push(treeDataProvider1.getJsonRow(dataRows[i]));
				$('#I_DTLDAT').val(treeDataProvider1.getValue(dataRows[i], "DAT"));
				$('#I_DTLJNO').val(treeDataProvider1.getValue(dataRows[i], "JNO"));
			}
			 
			var formData = $("#searchForm").serialize();
			
			fnOpenPopup("/orderReqPopup.do","오더의뢰지",formData,callFunPopup);
		} else if(str == "3"){
			fnOpenPopup("/hospSearchS.do","병원조회",formData,callFunPopup);
		} else if(str == "4"){
			//formData
			//var dataRows = treeView1.getCheckedRows();
			var data = [];
			for(var i in dataRows){
				//data.push(treeDataProvider1.getValues(dataRows[i], ""));
				data.push(treeDataProvider1.getJsonRow(dataRows[i]));
				
				//jsonRow.push(dataProvider.getJsonRow(e));
			}
			
			if (dataRows.length > 0){
				fnOpenPopup("/smsReqPopup.do","SMS",data,callFunPopup);
				
			}
			
		}else if(str == "5"){
			/*
			if($('#I_RGN1').is(":checked")){
		    	rd_rgn1 = "Y";	
		    }
		    if($('#I_RGN2').is(":checked")){
		    	rd_rgn2 = "Y";	
		    }
		    if($('#I_RGN3').is(":checked")){
		    	rd_rgn3 = "Y";	
		    }
		    */
			//rd_rgn1 = $('#I_RGN1').val();
		    //rd_rgn2 = $('#I_RGN2').val();
		    //rd_rgn3 = $('#I_RGN3').val();
		    
		    for(var i in dataRows){
				//data.push(treeDataProvider1.getValues(dataRows[i], ""));
				$('#I_DTLDAT').val(treeDataProvider1.getValue(dataRows[i], "DAT"));
				$('#I_DTLJNO').val(treeDataProvider1.getValue(dataRows[i], "JNO"));
				
				//jsonRow.push(dataProvider.getJsonRow(e));
			}
			var formData = $("#searchForm").serialize();
			
			fnOpenPopup("/addReq01Popup.do","추가의뢰 등록",formData,callFunPopup);
		}else if(str == "6"){
			fnOpenPopup("/testSearchM.do","검사 항목 조회",data,callFunPopup);
		}else if(str == "7"){
			parent.goPage('RSTPRE', '이전결과', 'rstPre.do', "&I_HOS="+$('#I_HOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+treeView1.getValue(dataRows[0]-1, "CHN")+"&I_NAM="+treeView1.getValue(dataRows[0]-1, "NAM")+"&I_DAT="+treeView1.getValue(dataRows[0]-1, "DAT").substring(0,4)+"-"+treeView1.getValue(dataRows[0]-1, "DAT").substring(4,6)+"-"+treeView1.getValue(dataRows[0]-1, "DAT").substring(6,8) );
		}else if(str == "8"){
			parent.goPage('STATSTIME', '시계열', 'statsTime.do', "&I_HOS="+$('#I_HOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+treeView1.getValue(dataRows[0]-1, "CHN")+"&I_NAM="+treeView1.getValue(dataRows[0]-1, "NAM")+"&I_DAT="+treeView1.getValue(dataRows[0]-1, "DAT").substring(0,4)+"-"+treeView1.getValue(dataRows[0]-1, "DAT").substring(4,6)+"-"+treeView1.getValue(dataRows[0]-1, "DAT").substring(6,8) );
		}else if(str == "9"){
			fnOpenPopup("/patSearchS.do","수진자 정보 조회",data,callFunPopup);
		} else if(str == "99"){
			//getCurrent
			dataRows = treeView1.getCheckedItems();
			if (dataRows.length > 0){
				var data = [];
				var gridData;
// 				data.push(gridData);
				for(var i in dataRows){
					gridData = treeView1.getValues(dataRows[i]);
					gridData["I_HOS"] = $('#I_HOS').val();
					data.push(gridData);
				}
				fnOpenPopup("/rstReport.do","결과지 출력",data,callFunPopup);
			}
		}
	
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		//fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function  callFunPopup(url,iframe,formData){
		if(url == "/testReq01Popup.do"){
			var userData = [];
			userData.I_DAT = $('#I_DTLDAT').val();
			userData.I_JNO = $('#I_DTLJNO').val();
			userData.I_HOS = $('#I_HOS').val();
			userData.I_RGN1 = $('#I_RGN1').val();
			userData.I_RGN2 = $('#I_RGN2').val();
			userData.I_RGN3 = $('#I_RGN3').val();
			userData.I_HAK = $('#I_HAK').val();
			userData.I_SAB = $('#I_SAB').val();

			iframe.userData1(userData);
			iframe.testReq02Call(formData);	
		} else if(url == "/orderReqPopup.do"){
			iframe.orderReq01Call_2(formData);
		} else if(url == "/hospSearchS.do"){

			formData = {};
			formData["I_HOS"] =  $('#I_HOS').val();
			formData["I_FNM"] =  $('#I_FNM').val();

			iframe.gridViewRead(formData);
		} else if(url == "/smsReqPopup.do"){
			var userData = [];
			userData.I_HOS = $('#I_HOS').val();
			iframe.userData(userData);
			iframe.smsReq03Call(formData);
			
		} else if(url == "/patSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"]   =   $('#I_HOS').val();
			gridValus["I_NAM"]   =   $('#I_NAM').val();
			gridValus["I_FNM"]   =   $('#I_FNM').val();
			gridValus["I_ECF"]   =   $('#I_ECF').val();
			if(isNull(gridValus)){
				gridValus = '';
			}
			iframe.gridViewRead(gridValus);
		} else if(url == "/testSearchM.do"){
			gridValus = $('#I_GAD').val();
			if(isNull(gridValus)){
				gridValus = '';
			}
			iframe.gridViewRead(gridValus);
		} else if(url == "/rstReport.do"){
			iframe.getReport(formData);
		} else if(url == "/addReq01Popup.do"){
			var userData = [];
			userData.I_DAT = $('#I_DTLDAT').val();
			userData.I_JNO = $('#I_DTLJNO').val();
			userData.I_HOS = $('#I_HOS').val();
			userData.I_RGN1 = $('#I_RGN1').val();
			userData.I_RGN2 = $('#I_RGN2').val();
			userData.I_RGN3 = $('#I_RGN3').val();
			userData.I_HAK = $('#I_HAK').val();
			userData.I_SAB = $('#I_SAB').val();
			
			iframe.userData2(userData);
			iframe.addReq01Call_item(formData);
		}
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
/* 					if(isNull(data[i].F011ACD)){
						data[i].F011ACD = "  ";
					}
					I_GAD += (data[i].F010GCD + data[i].F011ACD); */
					I_GAD += data[i].F010GCD;
					I_FKN.push(data[i].F010FKN);
				} 
				//console.log(I_GAD);
				$('#I_GAD').val(I_GAD);
				$('#I_FKN').val(I_FKN);
				var tooltip = "";
				for(var x in I_FKN){
					tooltip += (Number(x)+1)+". "+I_FKN[x] + "\n";	
				}
				$('#I_FKN').attr('title', tooltip);
		}
		if(data[0] == "/patSearchSList.do"){
			$('#I_NAM').val(data[1].F100NAM);
			$('#I_CHN').val(data[1].F100C);
			$('#I_BDT').val(data[1].F100BDT);
			$('#I_HOS').val(data[1].F100HOS);
			$('#I_FNM').val(data[1].F120FNM);
		}
	}
	
	//오더의뢰지
	//orderReqList
	
	function exportGrid(){
		var totalCnt = treeDataProvider1.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		CallMessage("257","confirm", "", gridExport);
	}
	
	function gridExport(){
		treeView1.exportGrid({
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
	
	function enterSearch(enter){
		if(event.keyCode == 13){
			if(enter == "0"){
				search();
			}else{
				openPopup(enter);
			}
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
	            $('#I_FNM').val('');
	         }
		}
	}
	
	</script>
</head>
<body>
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_ID" name="I_ID" type="hidden"/>
		<input id="I_ICNT" name="I_ICNT" type="hidden"> 				  	   <!-- 그리드에 보여줄 건수 -->
		<input id="I_CNT" name="I_CNT" value="5000" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
		<input id="I_ROW" name="I_ROW" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		<input id="I_IROW" name="I_IROW" value="0" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		<input id="I_ECF" name="I_ECF" type="hidden"> 			   
		<input id="I_PNN" name="I_PNN" type="hidden">
		<!-- <input id="I_HOS" name="I_HOS" type="hidden"> -->
		
		<input id="I_GAD" name="I_GAD" type="hidden">
		
		<input id="I_DTLDAT" name="I_DTLDAT" type="hidden">
		<input id="I_DTLJNO" name="I_DTLJNO" type="hidden">
		
		<input id="I_RBN" name="I_RBN" type="hidden">
		
		<!-- 2022.01.03 - 로그인한 유저 권한 -->
		<input id="loginUser_authority" name="loginUser_authority" type="hidden" value="<%=ss_agr%>">
		
		<div class="content_inner" >
                
                	<!-- 검색영역 -->
                    <div class="container-fluid">
						<div class="con_wrap col-md-12 srch_wrap">
							<div class="title_area open">
								<h3 class="tit_h3">검색영역</h3>
								<a href="#" class="btn_open">검색영역 닫기</a>
							</div>
							<div class="con_section row">
								<div class="main_srch_box">
		<!-- 						<p class="srch_txt text-center" style="display: none">조회하려면 검색영역을 열어주세요.</p> -->
									<div class="srch_box_inner m-t-10">
		<!-- 							<div class="srch_box_inner m-t-10" style="float: left; width: 90%;"> -->
										<div class="col-md-12">
											<div  class="group4" >
												<label for="" class="label_basic" style="float: left;">기간</label>
												<div  class="group1" >
													<div class="select_area">
														<select id="I_DGN" name="I_DGN" class="form-control"></select>
													</div>
													<div class="btn_group" style="font-size: 14px" id="I_S_TERM"></div>
												</div>
												<div class="group2" >
													<span class="period_area">
														<label for="I_FDT" class="blind">날짜 입력</label>
														<input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT">
													</span>
													 ~ 
													<span class="period_area">
														<label for="I_TDT" class="blind">날짜 입력</label>
														<input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT">
													</span>
												</div>
											</div>
											<div class="group3" >
												<label for="" class="label_basic">수진자명</label>
												<input type="text" id="I_NAM" name="I_NAM" maxlength="14" class="srch_put"  onkeydown="javasscript:enterSearch(0)" >
											</div>
										</div>
									</div>
									<div class="srch_box_inner m-t-10" >
										<div class="srch_item srch_item_v5 hidden_srch"  id="DivHosSearch" style="display: none">
											<label for="" class="label_basic">병원</label>
											<input id="I_HOS" name="I_HOS" type="text"  class="srch_put numberOnly srch_hos"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup(3)" maxlength="5" onkeydown="javasscript:enterSearch(0)" class="numberOnly"/>
											<input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly" onclick="javascript:openPopup(3)">
										</div>
										<div class="srch_item srch_item_v4 ">
											<label for="" class="label_basic">차트번호</label>
											<input type="text" id="I_CHN" name="I_CHN" title="차트번호" class="srch_put" maxlength="15" onkeydown="javasscript:enterSearch(0)" >
										</div>
									</div>
									<div class="btn_srch_box m-t-10">
		<!-- 							<div style="float: left;width: 79px;"> -->
										<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
										<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init2()">검색 초기화</button>
									</div>
								</div>
								<div class="srch_line"></div>
								<div class="srch_box floatLeft" style="">
									<div class="srch_box_inner m-t-10">
										<div class="srch_item srch_item_v5">
			 								<label for="" class="label_basic">검사항목</label>
											<input type="text" class="srch_put btn_ico test_pop_srch_ico" id="I_FKN" readonly="readonly" data-toggle="tooltip" data-placement="bottom"  onClick="javascript:openPopup(6)" >
											<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init()">초기화</button>
										</div>
										<div class="srch_item srch_item_v4">
											<label for="" class="label_basic">검체번호</label>
											<input type="text" id="I_ETC"  class="srch_put"  name="I_ETC" maxlength="50" onkeydown="javascript:enterSearch(0)">
											
										</div>
									</div>
									<div class="srch_box_inner m-t-10">
										<div class="srch_item srch_item_v5">
											<div class="srch_item">
												<label for="" class="label_basic">검사분야</label>
												<div class="select_area">
													<select id="I_HAK" name="I_HAK" class="form-control"></select>
												</div>
											</div>
											<div class="srch_item">
												<label for="" class="label_basic">검사진행</label>
												<div class="select_area">
													<select id="I_STS" name="I_STS" class="form-control"></select>
												</div>
											</div>
										</div>
										<div class="srch_item srch_item_v4">
											<ul class="chk_lst chk_lst_v2" id="RGN"></ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
                	<!-- 검색영역 end -->                  
                    
                    <div class="container-fluid">
                        <div class="con_wrap col-md-12 col-sm-12">
                            <div class="title_area">
                                <h3 class="tit_h3">검사결과</h3>
                                <div class="tit_btn_area">
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(99)">결과지 출력</button>
                                    <!-- 
                                    <button type="button" class="btn btn-default btn-sm">추가검사</button>
                                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#order-Modal">오더 의뢰지</button>
                                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#sms-Modal">SMS</button>
                                     -->
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(1)">추가 검사</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(2)">오더 의뢰지</button>
                                    <button type="button" class="btn btn-default btn-sm" id="rstPreButton" onClick="javascript:openPopup(7)">이전 결과</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(8)">시계열</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(4)" style="display:none" id="smsDisplay">SMS</button>
                                    <button type="button" class="btn btn-default btn-sm" onclick="javascript:exportGrid()">엑셀 다운</button>
                                </div>
                            </div>
                            <div class="con_section overflow-scr">
                                <div class="tbl_type" style="overflow:auto"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                                	<div id="realgrid1"  class="realgridH"></div>
                                	<nav class="pagination_area">
										<ul class="pagination" >
											<li id="I_PNN_P" >
												<a href="#" aria-label="Previous">
												<span aria-hidden="true" onClick="javascript:paging('P')" >이전</span>
											</a>
											</li>
											<li id="I_PNN_N">
												<a href="#" aria-label="Next">
												<span aria-hidden="true" onclick="javascript:paging('N')">다음</span>
												</a>
											</li>
										</ul>
									</nav>
									<div id="realgrid2"  style="display:none"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                                       
                </div>    
                <!-- content_inner -->       	
     </form>
     
<!-- sts-Modal 팝업 -->
<div class="modal fade" id="sts-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="" aria-hidden="true" style="height:160px" >
    <div class="modal-dialog" id="sts-Modal1" style="position:absolute; left:0px;">
        <div class="modal-content" style="position:absolute; left:0px;" id="sts-Modal2">
            <div class="modal-header" id="sts-Modal3">
                <button type="button" id="modal-close" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel" id="sts-Modal4">검사 의뢰 목록</h4>
            </div>
            <div class="modal-body" id="sts-Modal5">
            	<div class="tbl_type" id="sts-Modal6">
					<table class="table table-bordered table-condensed tbl_c-type" id="sts-Modal7">
                    	<colgroup>
                        	<col width="60">
                            <col width="60">
                            <col width="100">
                            <col width="80">
                        </colgroup>
                        <thead>
                        	<tr>
                            	<th scope="col" id="sts-Modal8">보험코드</th>
                                <th scope="col" id="sts-Modal9">검사코드</th>
                                <th scope="col" id="sts-Modal10">검사항목</th>
                                <th scope="col" id="sts-Modal11">보고예정일자</th>
                            </tr>
                        </thead>
                        <tbody id="stsData">
                        </tbody>
                    </table>
                </div> 
            </div>
        </div>
    </div>
</div>
<!-- sts-Modal 팝업 end -->


<!-- item-Modal 팝업 -->
<div class="modal fade" id="item-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">검사항목</h4>
            </div>
            <div class="modal-body">
            	<div class="tbl_type">
					<table class="table table-bordered table-condensed tbl_l-type">
                    	<colgroup>
                        	<col width="20%">
                            <col width="30%">
                            <col width="20%">
                            <col >
                        </colgroup>
                        <tbody>
                        	<tr>
                            	<th scope="col">검사명</th>
                                <td>AFB xxxxxxxxxxxxxxxxxxxxxxxxxxx</td>
                                <th scope="col">검체(mL)</th>
                                <td>xxxxxxxxx</td>
                            </tr>
                            <tr>
                            	<th scope="col">전산코드</th>
                                <td>xxxxxxxxx</td>
                                <th scope="col">보존방법</th>
                                <td>xxxxxxxxx</td>
                            </tr>
                            <tr>
                            	<th scope="col">검사일정(소요일)</th>
                                <td>xxxxxxxxx</td>
                                <th scope="col">용기명</th>
                                <td>xxxxxxxxx</td>
                            </tr>
                            <tr>
                            	<th scope="col">검사법</th>
                                <td></td>
                                <th scope="col">보험수가</th>
                                <td></td>
                            </tr>
                            <tr>
                            	<th scope="col">기준수가</th>
                                <td></td>
                                <th scope="col" rowspan="6">검체용기</th>
                                <td rowspan="6"></td>
                            </tr>
                            <tr>
                            	<th scope="col">보험코드</th>
                                <td></td>
                            </tr>
                            <tr>
                            	<th scope="col">보험기호</th>
                                <td></td>
                            </tr>
                            <tr>
                            	<th scope="col">주의사항</th>
                                <td></td>
                            </tr>
                            <tr>
                            	<th scope="col">참고치</th>
                                <td></td>
                            </tr>
                            <tr>
                            	<th scope="col">임상정보</th>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>         
            </div>
            <div class="modal-footer">
            
            </div>
        </div>
    </div>
</div>
<!-- item-Modal 팝업 end -->

<!-- ect_plus-Modal 기타 추가 팝업 -->
<div class="modal fade" id="ect_plus-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">검사항목</h4>
            </div>
            <div class="modal-body">
            	<div class="srch_box">
                    <div class="srch_box_inner">
                        <div class="srch_item">
                            <label for="" class="label_basic">검사코드</label>
                            <input type="text" class="srch_put" id="" placeholder="">
                        </div>  
                        <div class="srch_item">
                            <label for="" class="label_basic">검사항목</label>
                            <input type="text" class="srch_put" id="" placeholder="">
                        </div>          
                    </div> 
                                
                    <div class="btn_srch_box">
                        <button type="button" class="btn_srch">조회</button>
                    </div> 
                </div>
                <div class="tit_btn_area modal_btn_area">
                    <button type="button" class="btn btn-default btn-sm">추가</button>
                </div>
            	<div class="tbl_type">
					그리드영역
                </div>         
            </div>
            <div class="modal-footer">
            
            </div>
        </div>
    </div>
</div>
<!-- ect_plus-Modal 기타 추가 팝업 end -->
     
</body>
</html>




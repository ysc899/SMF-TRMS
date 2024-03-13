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
	<%@ include file="/inc/reportPop.inc"%>		
	
	<title>결과관리::수진자별 결과관리(A)</title>
	
	<style>
		.floatLeft .srch_box_inner{width:100% !important;}
		.floatLeft .srch_item_v5{display: inline-block;}
		.floatLeft .srch_item_v4{display: inline-block;}
		.line0 {border-right:1px solid #959595;border-left:1px solid #959595;}
		.line0 td {padding:5px 0px 5px 0px;}
		
		.line1 {border-top:3px solid #656565;border-right:1px solid #959595;border-left:1px solid #959595;}
		.line1 td {padding:5px 5px 5px 5px;}
		.line2 {border-bottom:1px solid #959595; border-right:1px solid #959595;}
		.line2_l {border-bottom:1px solid #959595; border-right:1px solid #959595; text-align:left;}
		.line3 {border-bottom:1px solid #959595;}
		.line3_l {border-bottom:1px solid #959595; text-align:left;}
		.line4 {border-bottom:1px solid #959595; border-right:1px solid #959595; font-weight:bold; background:#efefef;}
		.line5 {border-bottom:1px solid #959595; font-weight:bold; background:#efefef;}
		.line6 {border-bottom:1px solid #959595; border-left:1px solid #959595;}
		.line7 {border-bottom:1px solid #959595; border-left:1px solid #959595; font-weight:bold; background:#efefef;}
		.line8 {border-bottom:1px solid #959595; border-right:1px solid #959595; font-weight:bold; background:#ffe9ce;}
		
		.line9 {border-bottom:1px solid #d7d7d7;}
		.line9 td {padding:10px 10px 10px 10px;}
		.line9_l {border-bottom:1px solid #d7d7d7; text-align:left;}
		.line10 {border-bottom:1px solid #cecece; font-weight:bold; background:#e5e5e5;}
	</style>	
	
	
	<%
		String Param_CHN = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_CHN"), "");
		String Param_NAM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_NAM"), "");	
		String Param_FNM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_PFNM"), "");
		String Param_HOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_PHOS"), "");
		String Param_DAT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_DAT"), "");
		String Param_FDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FDT"), "");
		String Param_TDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_TDT"), "");
		String Param_POT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_POT"), "");
		String Param_MAINFLAG = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_MAINFLAG"), "");
		
		
    %>
	<script>
	
	var dataProvider1,dataProvider2;
    var gridView1,gridView2;
		
	var pmcd = "";
	var I_LOGMNU = "RSTUSER";
	var I_LOGMNM = "수진자별 결과관리";
	var gridValues = [];
    var gridLabels = [];
	var mousePointer ;
    
    var mast_class1="<b>class1</b> : ";
    var mast_class2="<b>class2</b> : ";
    var mast_class3="<b>class3</b> : ";
    var mast_class4="<b>class4</b> : ";
    var mast_class5="<b>class5</b> : ";
    var mast_class6="<b>class6</b> : ";
    var mast_str="N"; // MSAS검사 class 검사결과 존재여부 flag
    var rlt_class = "";
    
    var ria_str="N"; // Ria
    var riaData="N";    
    var riaResult="";
    var remarkPositiveGcdn=""; // positive Gcdn내용
    var remarkDataRd = "";
    var isTripleNew = "N";
    
    var hematoma_str="N";
    var hematomaStr = "";
        
    var obgy_str="N";
    var obgyStr = "";
        
    var img_str="N";
    
    var photo_str="N";
    
    //img_data
    var img_dpt = "";
    var url_str = "";
	var cnt_str = 0;
	var img_html = "";
	var pdf_html = "";
	var pdf_htmlx = "";
	
	
	var img_cnt = 0;
	var img_sdb = "";
	var img_rotate = "";
    
	//Microbio
	var microbio_str = "N";
	var microbio_html = "";
	
	var microbio119yn = "N";
	var microbio118yn = "N";
	var gac = "";
	var gac1 = "";
	var gac2 = "";
	var dt = "";
	var bdt1 = "";
	var bdt2 = "";
	
	var LmbYN_str = "N";	// LmbYN
	var LmbYN_html = "";	// LmbYN
	var Mw106rm3 = "N"; 	// LmbYN 이전
	var Mw106rms6 = "N"; 	// LmbYN 이후

	var pathologyType_str = "N";	// pathologyType
	var pathologyType_html = "";	// pathologyType
	
	var pathologyType2_str = "N";	// pathologyType2
	var pathologyType2_html = "";	// pathologyType2
	
	var pathTitleYn = "N";	// 면역병리 진단보고
	
	var CytologyType_str = "N";
	var CytologyType_html = "";
	var CytologyType2_str = "N";
	var CytologyType2_html = "";
	var CytologyType3_str = "N";
	var CytologyType3_html = "";
	
	var Cytogene_str = "N";
	var Cytogene_prc = "N";
	var Cytogene_html = "";
	
	
    var remark_str="N"; // remark 검사결과 존재여부 flag
    var remark_html = "";
    var remarkPositiveYn ="N";
    var remarkData=""; // remark 내용 검사결과 존재여부 flag
    var page_count = 0;		// 페이지 확인 0이면 첫 페이지
    
    var coronaCtData = "";		// 코로나 CT 결과 존재여부 flag
    var corona_ct_str = "N";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
    
    var cellData = "N";			// 세포유전 결과 존재시, 결과 상세 화면에 '세포유전' 문구를 출력할지 여부 flag(2020.08.29)
    var cell_str = "N";			// 세포유전 결과 존재시, 결과 상세 화면에 '세포유전' 문구를 출력할지 여부 flag(2020.08.29)

    //RD DATA
    var rd_dat = 0;
    var rd_jno = 0;
    var rd_rgn1 = "";
    var rd_rgn2 = "";
    var rd_rgn3 = "";
    var rd_hak = "";
    var rd_pnn = "";
    var rd_gcd = "";
    var rd_acd = "";
    //var rd_gcd = "";
    //var rd_acd = "";
    var s_uid = "<%= ss_uid %>";
   	var s_ip =  "<%= ss_ip %>";
   	var s_cor = "<%= ss_cor %>";
   	
   	var s_ecf = "<%= ss_ecf %>";
   	var s_hos = "<%= ss_hos %>";
   	var s_nam = "<%= ss_nam %>";
   	
   	var s_syn = "<%= ss_syn %>";
   	
   	// 코드관리(화면출력 사용유무)
   	var s_moniter_view_print = "<%= ss_moniter_view_print %>";

   	// 파견사원 병원유무 Flag
   	var s_paguen_hos_yn = "<%= ss_paguen_hos_yn %>";
   	
   	var Param_NAM = "<%=Param_NAM%>";
    var Param_CHN = "<%=Param_CHN%>";
    var Param_DAT = "<%=Param_DAT%>";
    var Param_HOS = "<%=Param_HOS%>";
    var Param_FNM = "<%=Param_FNM%>";
    var Param_FDT = "<%=Param_FDT%>";
    var Param_TDT = "<%=Param_TDT%>";
    var Param_POT = "<%=Param_POT%>";

   	var Param_MAINFLAG = "<%=Param_MAINFLAG%>";
   	
   	var mainIn = "Y";
   	
   	//session
    $(document).ready( function(){
    	
    	// 처음 수진자별 결과조회 화면이 열릴때는 default 값으로 hide 처리한다.
    	$("#resultImg_eng").hide();
    	$("#resultImg_kor").hide();
    	
    	$(".onlyHangul").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[a-z0-9]/gi,''));
	        }
	    });
    	
    	// 화면출력
    	// 20190320 화면출력 사용안함(김윤영 과장님 요청사항(당일 회의 결과))
    	// 20200302 요청한 병원에 한해서만 기능 오픈하기로 변경
    	if(s_moniter_view_print == "Y" 
    	){
        	$('#rstDisplayPrintButton').show();
    	}else{
    		$('#rstDisplayPrintButton').hide();
    	}
    	
    	$('#DivMastResult').hide();
		$('#DivMastResult_Reference').hide();
		$('#DivRia').hide();
		$('#DivHematoma').hide();
		$('#DivObgy').hide();
		$('#DivHasimg').hide();
		$('#DivPopphoto').hide();
		$('#DivMicrobio').hide();
		$('#DivLmbYN').hide();
		$('#DivPathologyType').hide();
		$('#DivPathologyType2').hide();
		$('#DivCytologyType').hide();
		$('#DivCytologyType2').hide();
		$('#DivCytologyType3').hide();
		$('#DivCytogene').hide();
		$('#DivRemarktext').hide();
		$('#DivCoronaCT').hide();		// 결과 상세 화면에 'Corona CT' (show/hide)
		$('#DivCell').hide();			// 결과 상세 화면에 '세포유전' (show/hide)
		
		//sms 버튼 활성화 여부
		if(s_syn == "Y"){
			$('#smsDisplay').show();	
		}
		
		$('#I_PECF').val(s_ecf);
		$('#I_PHOS').val(s_hos);
		if(s_ecf != "E"){
			$('#I_FNM').val(s_nam);
    	} 
		
		$('#DivHosSearch').hide();
		//$('#DivHosSearch').hide();
		
 		//사원일경우 or 파견사원 병원인 경우 >> 병원 검색 div 화면에 출력.
		if(s_ecf == "E" || s_paguen_hos_yn != ""){
			$('#DivHosSearch').show();
		}
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		dataProvider1 = new RealGridJS.LocalDataProvider();
		gridView1 = new RealGridJS.GridView("realgrid1");
	    setStyles(gridView1);
	    
	    gridView1.setOptions({keepNullFocus:true}); // 2019.05.29 - 주석하면, 첫번째 row 자동 선택
	    //alert(JSON.stringify(gridView1.getOptions()));
	    	    
	    //20190403 최재원 대리님 요청으로 체크박스 제거
	    setGridBar(gridView1, false);
	    
	    gridView1.setHeader({
	    	height:40
	    })
	    gridView1.setDataSource(dataProvider1);
	    gridView1.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields1 = [
	    	{fieldName: "DNO"},
	    	{fieldName: "DAT"},
	    	{fieldName: "DAT2"},
	    	{fieldName: "JNO"},
	    	{fieldName: "NAM"},
	    	{fieldName: "CHN"},
	    	{fieldName: "SEX"},
	    	{fieldName: "AGE"},
	    	{fieldName: "STSD"},
	    	{fieldName: "ETC"},
	    	{fieldName: "PRF"}, //이전결과/ 시계열
	    	{fieldName: "PNF"},  // 양성 Y
	    	{fieldName: "PNFIMG"},  // 양성 Y
	    	{fieldName: "C07H"}, // 하이 Y
	    	{fieldName: "C07HIMG"}, // 하이 Y
	    	{fieldName: "C07L"}, // 로우 Y
	    	{fieldName: "C07LIMG"}, // 로우 Y
	    	{fieldName: "ETCINF"},
	    	{fieldName: "GADINF"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider1.setFields(fields1);
	  var columns1 = [
	    	
	        {name: "DAT", fieldName: "DAT2", width: "75", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "접수일자"}}
	      ,	{name: "CHN", fieldName: "CHN", width: "75", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "차트번호"}}
	      , {name: "NAM", fieldName: "NAM", width: "50", styles: {textAlignment: "near"},    renderer:{showTooltip: true}, header: {text: "수진자"}}
	      , {name: "SEX", fieldName: "SEX", width: "30", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "성별"}}
	      , {name: "AGE", fieldName: "AGE", width: "30", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "나이"}}
	      ,	{name: "ETC", fieldName: "ETC", width: "90", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검체번호"}}
	      //, {name: "PNF", fieldName: "PNF", width: "30", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "양성/음성"}}
	      //, {name: "C07H", fieldName: "C07H", width: "30", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "하이"}}
	      //, {name: "C07L", fieldName: "C07L", width: "30", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "로우"}}
	      //, {name: "PRF", fieldName: "PRF", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "이전\n결과"} , "dynamicStyles":[{"criteria":["value = 'Y'"],"styles":["renderer=buttons1"]}]}
	      , {name: "PRF", fieldName: "", width: "40", styles: {iconLocation : "center", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: true, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var prf = [];
	          //console.log(JSON.stringify(index));
	          if (grid.getValue(index.itemIndex, "PRF") == "Y") {
	              //prf.push('../images/common/btn_view.png');
	              prf.push('../images/common/btn_srch.png');
	        	  
	          }
	          
	          return prf
	      }}, header: {text: "이전\n결과"}}
	      //, {name: "PRF2", fieldName: "PRF", width: "100", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "시계열"} , "dynamicStyles":[{"criteria":["value = 'Y'"],"styles":["renderer=buttons1"]}]}
	      /* 20190321 제거 요청
	      , {name: "PRF2", fieldName: "", width: "40", styles: {iconLocation : "center", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: true, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var prf2 = [];
	          //console.log(JSON.stringify(index));
	          if (grid.getValue(index.itemIndex, "PRF") == "Y") {
	              //prf2.push('../images/common/btn_view.png');
	        	  prf2.push('../images/common/btn_srch.png');
	          }
	          
	          return prf2
	      }}, header: {text: "시계열"}}
	      */
	      , { type : "group", name : "PNFGROUP", width : "30", hideChildHeaders : "false", columns : [
	    	  {name: "PNF11",  fieldName: "PNFIMG",  width: "15", styles: {iconLocation : "center", iconAlignment: "center", contentFit : "auto", borderRight:"#000000, 0", paddingRight  : "0"},    renderer:{type:"image", smoothing: false}, header: {text: "1"}}
	    	  , { type : "group", name : "PNFSUBGROUP", width : "15", orientation : "vertical", columns : [
	    		  {name: "PNF22",  fieldName: "C07HIMG",  width: "15", styles: {iconLocation : "bottom", iconAlignment: "center", contentFit : "auto", borderBottom:"#000000, 0", paddingLeft  : "0", paddingBottom  : "0"},    renderer:{type:"image", smoothing: false}, header: {text: "2"}}
	    		, {name: "PNF33",  fieldName: "C07LIMG",  width: "15", styles: {iconLocation : "top", iconAlignment: "center", contentFit : "auto", paddingLeft  : "0" , paddingTop  : "0"},    renderer:{type:"image", smoothing: false}, header: {text: "3"}}
	    	  ]}
	      ], header: {text: "상태"}}
	      , {name: "STSD",  fieldName: "STSD",  width: "80", cursor: "pointer", styles: {iconLocation : "right", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: true, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var ret = [];
	          var value = grid.getValue(index.itemIndex, "GADINF");
	          //console.log(index.itemIndex + "= " + value);
	          if(value != null && value.replace(/ /gi, "") != ""){
		          ret.push('../images/common/ico_viewer.png');
	          }
	          return ret
	      }}, header: {text: "검사진행"}}
	      //, {name: "STSD", fieldName: "STSD", width: "80", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검사진행"}}
	    ];
	    gridView1.setColumns(columns1);
	    
	    gridView1.setColumnProperty("PRF", "buttonVisibility", "always");
	    gridView1.setColumnProperty("PRF2", "buttonVisibility", "always");
	    gridView1.setColumnProperty("PNF", "buttonVisibility", "always");
	    
	    gridView1.onDataCellClicked = function (grid, index) {
        	$('#sts-Modal').modal('hide');
	    	//alert(JSON.stringify(index));
	 		if(grid.getValue(index.itemIndex, "PRF") == "Y" && index.column == "PRF"){
	 				
	 			parent.goPage('RSTPRE', '이전결과', 'rstPre.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+grid.getValue(index.itemIndex, "CHN")+"&I_NAM="+grid.getValue(index.itemIndex, "NAM")+"&I_DAT="+grid.getValue(index.itemIndex, "DAT").substring(0,4)+"-"+grid.getValue(index.itemIndex, "DAT").substring(4,6)+"-"+grid.getValue(index.itemIndex, "DAT").substring(6,8) );
	 			//parent.goPage('STATSTIME', '시계열', 'statsTime.do', '&menutest=확인&adf=');
	 		} else if(grid.getValue(index.itemIndex, "PRF") == "Y" && index.column == "PRF2"){
	 			parent.goPage('STATSTIME', '시계열', 'statsTime.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM=&I_CHN="+grid.getValue(index.itemIndex, "CHN")+"&I_NAM="+grid.getValue(index.itemIndex, "NAM")+"&I_DAT="+grid.getValue(index.itemIndex, "DAT").substring(0,4)+"-"+grid.getValue(index.itemIndex, "DAT").substring(4,6)+"-"+grid.getValue(index.itemIndex, "DAT").substring(6,8));
	 			//parent.goPage('STATSTIME', '시계열', 'statsTime.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM=&I_CHN="+grid.getValue(index.itemIndex, "CHN")+"&I_NAM="+grid.getValue(index.itemIndex, "NAM")+"&I_DAT=2018-09-01");
	 			
	 		} else {
	 			TabResize();
	 			if(loding){
	 				callLoading(null, "on");
	 			}
	 			setTimeout(function() {
		 			dataResult2(grid.getValues(index.itemIndex));	
	 			}, 200);
	 		}
	 		//pmcd = tree.getValue(index.itemIndex, 1);
	 		//$('#dtlDiv').focus();
		};
		gridView1.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "ETC") {
	            tooltip = "검체번호 : "+value +
	                    "\n검체명    : "+ grid.getValue(itemIndex, "ETCINF");
	        }
	        
	        if (column == "STSD") {
	        	var gadInfHtml = "";
	 			var gadData = "";
	 			gadData = "["+grid.getValue(index.itemIndex, "GADINF")+"]";
	 			var gadInf = JSON.parse(gadData); 
	 			var dataCnt = gadInf.length;
	 			
	 			if(dataCnt < 1){
	 				$('#sts-Modal').modal('hide');
	 				return " ";
	 			}
	 			
	 			//gadData = gadData.replace(/\\/gi,"");
	 			
	 			//alert(gadData);
	 			//console.log(gadData)
	 			//gadInf = {stsData : gadData};
	 			//index.itemIndex
	 			//alert(JSON.stringify(gadInf));
	 			
	 			
	 			for (var i in gadInf){
	 				//console.log(i);
	 				gadInfHtml += 		"<tr>";
	 				gadInfHtml += 			"<td>"+gadInf[i].SNM+"</td>";
	        		gadInfHtml += 			"<td>"+gadInf[i].BDT.substring(0,4)+"-"+gadInf[i].BDT.substring(4,6)+"-"+gadInf[i].BDT.substring(6,8)+"</td>";
	        		
	 				//gadInfHtml += 			"<td>"+gadInf[i].OCD+"</td>";
	        		//gadInfHtml += 			"<td>"+gadInf[i].GCD+"</td>";
	        		
	        		gadInfHtml += 		"</tr>";
	 			}
	 			
	 			//var p_list_width = $('#realgrid1').width() + 50;
	 			
	 			var body_width = $('#bodyWidth').width();
	 			var Modal1_width = $('#sts-Modal1').width();
	 			var p_list_width = 800;
	 			
	 			if(grid.getValue(index.itemIndex, "STSD") == "완료"){
	 				$('#sts-Modal11').html("보고일자");
	 			} else {
	 				$('#sts-Modal11').html("보고예정일자");
	 			}
	 			
	 			$('#myModalLabel').html(grid.getValue(index.itemIndex, "NAM")+ " 검사 의뢰 정보");
	 			$('#stsData').html(gadInfHtml);
	 			
	 			var positionY = mousePointer.positionY - 40;
	 			p_list_width = Math.ceil(mousePointer.positionX /10)*10 + 10;
	 			
	 			if(window.innerHeight>600){
		 			if(window.innerHeight-positionY < 410){
		 				var pos = 500 - (window.innerHeight-positionY);
		 				positionY = positionY - pos;
		 			}
	 			}else{
	 				positionY = window.innerHeight/2-130;
	 			}
	 			if(window.innerWidth<992){
	 				p_list_width = p_list_width - (Modal1_width+40);
 				}else{
	 				p_list_width = p_list_width + 20;
 				}
 				mouseF = true;
 				
	 			$('#sts-Modal').attr('style', 'display:block;position:absolute;left:'+p_list_width+'px;top:'+positionY+"px;height:410px;width:"+(Modal1_width+13)+"px;");
	 			$('#sts-Modal').modal('show');
	        } else {
	        	$('#sts-Modal').modal('hide');
	        }
	        
	        return tooltip;
	    }
		
		gridView1.setColumnProperty("STSD","dynamicStyles",function(grid, index, value) {
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
		
		var mouseF = true;
		$( "#realgrid1" ).mousemove(function( event ) {
		  	mousePointer = get_position_of_mousePointer(event);
		});
		$( "#realgrid1" ).mouseleave(function( event ) {
			var relatedTarget = event.relatedTarget;
			try{
				if(relatedTarget["id"].indexOf("sts-Modal")>-1){
				}else{
					if(mouseF){
			        	$('#sts-Modal').modal('hide');
						mouseF = false;
					}
				}
			}catch(e){
				
			}
		});
		$( "#sts-Modal2" ).mouseleave(function( event ) {
			var relatedTarget = event.relatedTarget;
			if(mouseF){
	        	$('#sts-Modal').modal('hide');
				mouseF = false;
			}
		});
		
		var stateBar1 = gridView1.getStateBar();
	    stateBar1.visible = false;
	    gridView1.setStateBar(stateBar1);
	    
	    //20190403 최재원 대리님 요구사항(넘버링 제거) 
	    gridView1.setIndicator({ visible: false});	    
		
		dataProvider2 = new RealGridJS.LocalDataProvider();
		gridView2 = new RealGridJS.GridView("realgrid2");
	    setStyles(gridView2);
	    
	    gridView2.setHeader({
	    	height:40
	    })
	    
	    gridView2.setDataSource(dataProvider2);
	    gridView2.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true,
	    	editable:false
	    })
	    var fields2 = [
	    	{fieldName: "O_CHK"},
	    	{fieldName: "O_REP"},
	    	{fieldName: "O_OCD"},
	    	{fieldName: "O_GCD"},
	    	{fieldName: "F010GCD"},
	    	{fieldName: "O_ACD"},
	    	{fieldName: "O_GCDN"},
	    	{fieldName: "O_GCDNIMG"},
	    	{fieldName: "O_BDT"},
	    	{fieldName: "O_BDT2"},
	    	{fieldName: "O_NU1"},
	    	{fieldName: "O_CHR"},
	    	{fieldName: "O_C03"},
	    	{fieldName: "O_C04"},
	    	{fieldName: "O_C05"},
	    	{fieldName: "O_C07"},
	    	{fieldName: "O_CKI"},
	    	{fieldName: "O_CKP"},
	    	{fieldName: "O_REF"},
	    	{fieldName: "O_REF1"},
	    	{fieldName: "O_REF2"},
	    	{fieldName: "O_DPT"},
	    	{fieldName: "O_PRF"},
	    	{fieldName: "O_SDB"},
	    	{fieldName: "O_BM"},
	    	{fieldName: "O_RSTB"},
	    	{fieldName: "O_REMK"},
	    	{fieldName: "O_LMBN"},
	    	{fieldName: "O_FLG"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider2.setFields(fields2);
	    
	    var columns2 = [
	    	{name: "O_OCD", fieldName: "O_OCD", width: "50", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "보험코드"}}
	      //,	{name: "O_GCDN", fieldName: "O_GCDN", width: "120", styles: {textAlignment: "near"},    renderer:{showTooltip: true}, header: {text: "Test\nItem"}}
	      , { type : "group", name : "GCDNGROUP", width : "80", hideChildHeaders : "false", columns : [
	    	  {name: "O_GCDN",  fieldName: "O_GCDN",  width: "60", /*styles: {borderRight:"#000000, 0", paddingRight  : "0"},*/    renderer:{showTooltip: true}, header: {text: "1"}}
	    	  //, {name: "O_GCDNIMG",  fieldName: "O_GCDNIMG",  width: "20", styles: {iconLocation : "center", iconAlignment: "center", contentFit : "auto"},    renderer:{type:"image", smoothing: false}, header: {text: "2"}}
	    	  /* 검사항목 이미지 삭제 20190412 임상사업부 요청
	    	  , {name: "O_GCDNIMG", fieldName: "", width: "20", styles: {iconLocation : "center", iconAlignment: "center", contentFit: "auto"},    renderer:{showTooltip: false, type:"multiIcon", renderCallback:function(grid, index){
		    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
		          var o_prf = [];
		          //console.log(JSON.stringify(index));
		          if (grid.getValue(index.itemIndex, "O_ACD") == "" || grid.getValue(index.itemIndex, "O_ACD") == "00") {
		        	  //o_prf.push('../images/common/btn_view.png');
		        	  o_prf.push('../images/common/ico_viewer.png');
		          }
		          
		          return o_prf
		      }}, header: {text: "2"}}
	    	  */
	      ], header: {text: "검사항목"}}
	      /*, {name: "O_GCDN",  fieldName: "O_GCDN",  width: "80", cursor: "pointer", styles: {iconLocation : "right", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: true, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var ret = [];
	          ret.push('../images/common/ico_viewer.png');
	          return ret
	      }}, header: {text: "검사진행상황"}}
	      */
	      ,	{name: "O_CHR", fieldName: "O_CHR", width: "100", styles: {textAlignment: "center", font : "맑은 고딕, 12, Bold"},    renderer:{showTooltip: true}, header: {text: "결과"}}
	      , {name: "O_REF", fieldName: "O_REF2", width: "100", styles: {textAlignment: "near", textWrap:"explicit"}, renderer:{showTooltip: true}, header: {text: "참고치"}, editor : {type: "multiline"}}
	      , {name: "O_C07", fieldName: "O_C07", width: "15", styles: {textAlignment: "center", font : "맑은 고딕, 12, Bold"},    renderer:{showTooltip: true}, header: {text: "H/L"}} //H/L/BLANK //PANIC H/L/BLANK //DETAIL Y/BLANK
	      , {name: "O_BDT", fieldName: "O_BDT2", width: "40", styles: {textAlignment: "center", textWrap:"explicit"}, renderer:{showTooltip: true}, header: {text: "보고일자"}}
	      //, {name: "O_PRF", fieldName: "O_PRF", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "이전\n결과"} , "dynamicStyles":[{"criteria":["value = 'Y'"],"styles":["renderer=buttons1"]}]}
	      /* 20190321 제거 요청
	      , {name: "O_PRF", fieldName: "", width: "20", styles: {iconLocation : "center", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: false, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var o_prf = [];
	          //console.log(JSON.stringify(index));
	          if (grid.getValue(index.itemIndex, "O_PRF") == "Y") {
	        	  //o_prf.push('../images/common/btn_view.png');
	        	  o_prf.push('../images/common/btn_srch.png');
	          }
	          
	          return o_prf
	      }}, header: {text: "이전\n결과"}}
	      */
	      //, {name: "O_PRF2", fieldName: "O_PRF", width: "60", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "시계열"} , "dynamicStyles":[{"criteria":["value = 'Y'"],"styles":["renderer=buttons1"]}]}
	      , {name: "O_PRF2", fieldName: "", width: "20", styles: {iconLocation : "center", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: false, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var o_prf2 = [];
	          //console.log(JSON.stringify(index));
	          if (grid.getValue(index.itemIndex, "O_PRF") == "Y") {
	        	  //o_prf2.push('../images/common/btn_view.png');
	        	  o_prf2.push('../images/common/btn_srch.png');
	          }
	          
	          return o_prf2
	      }}, header: {text: "시계열"}}
	    ];
	    gridView2.setColumns(columns2);
	    
	    gridView2.setColumnProperty("O_PRF", "buttonVisibility", "always");
	    gridView2.setColumnProperty("O_PRF2", "buttonVisibility", "always");
	    
	    gridView2.setColumnProperty("O_C07","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "O_C07");
	    	   if (index.column === "O_C07") {
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

	    gridView2.setColumnProperty("O_CHR","dynamicStyles",function(grid, index, value) {
	    	   var ret = {};
	    	   var col1Value = grid.getValue(index.itemIndex, "O_C07");
	    	   var col2Value = grid.getValue(index.itemIndex, "O_FLG");
	    	   var col3Value = grid.getValue(index.itemIndex, "O_CHR");
	    	   //var col2Value = dataProvider2.getValue(index.itemIndex, "O_FLG");
	    	   if (index.column === "O_CHR") {
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
	    	    if(grid.getValue(index.itemIndex, "O_CHR").indexOf("보고예정") <= -1 && // 보고예정이 포함되어 있지 않으면서
	    	    	(col3Value.indexOf("<") <= -1 && grid.getValue(index.itemIndex, "O_GCD") == "71252" && grid.getValue(index.itemIndex, "O_ACD") != "00")
	    	    	||(col3Value.indexOf("이하") <= -1 && col3Value.indexOf("미만") <= -1 && grid.getValue(index.itemIndex, "O_GCD") == "71259" && grid.getValue(index.itemIndex, "O_ACD") != "00")
	    	    	||(col3Value.indexOf("<") <= -1 && grid.getValue(index.itemIndex, "O_GCD") == "71257")
	    	    	
	    	    	// 2019.12.24 - 21295 종목인 경우, 'Reactive' 문구가 포함된 결과일 경우 빨강색 표시되어야 함. (단, 'Non-Reactive' 문구가 포함된 경우는 제외함)
	    	    	||(col3Value.indexOf("Reactive") > -1 && grid.getValue(index.itemIndex, "O_GCD") == "21295" && col3Value.indexOf("Non-Reactive") <= -1)
	    	    	
	    	    	// 2021.05.10 - 72185 / 부속01 인 경우, 결과에 '+' 문자가 포함되면 붉은색 표시
	    	    	||(grid.getValue(index.itemIndex, "O_GCD") == "72185" && grid.getValue(index.itemIndex, "O_ACD") == "01" && col3Value.indexOf("+") > -1)
	    	    ){
	    	    	//console.log("## 결과값 빨강색 표시 ##");
    	    		ret.foreground = "#ffff0000";
    	    	}
	    	    
	    	 		// 2021.05.10 - 72185 / 부속02 인 경우, 결과에 '+' 문자가 포함되면 파랑색 표시
	    	    if(grid.getValue(index.itemIndex, "O_GCD") == "72185" && grid.getValue(index.itemIndex, "O_ACD") == "02" && col3Value.indexOf("+") > -1){
	    	    	//console.log("## 결과값 파랑색 표시 ##");
	    	    	ret.foreground = "#ff0000ff";
	    	    }
	    	    	
	    	    
	    	    // 검사결과에 결과값에 Positive 문구가 포함되어 있으면 빨강색으로 표시함.
	    	    // 20190722 - MWR003M@ 테이블에 등록된 종목별 문구인 경우만 결과값을 빨강색으로 표시하는게 기준이라서
	    	    //            주석처리함
	    	    /* if(col3Value.indexOf("Positive") >= 0){
	    	    	ret.foreground = "#ffff0000";
	    	    }*/
	    	    
	    	    return ret;
	    	  }
	    	});
	    
	    gridView2.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "O_REF") {
	            //tooltip = grid.getValue(itemIndex, "O_REF").replace(/"\n"/gi,"<br>");
	        	tooltip = grid.getValue(itemIndex, "O_REF1");
	        }
	        
	        return tooltip;
	        
	        
	    }
	    
	    gridView2.onDataCellClicked = function (grid, index) {
	    	if(grid.getValue(index.itemIndex, "O_PRF") == "Y" && index.column == "O_PRF"){
	 			parent.goPage('RSTPRE', '이전결과', 'rstPre.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+$('#CHN').html()+"&I_NAM="+$('#NAM').html()+"&I_DAT="+$('#DAT').html()+"&I_FKN="+grid.getValue(index.itemIndex, "O_GCDN")+"&I_GCD="+grid.getValue(index.itemIndex, "O_GCD")+"&I_ACD="+grid.getValue(index.itemIndex, "O_ACD") );
	 		} else if(grid.getValue(index.itemIndex, "O_PRF") == "Y" && index.column == "O_PRF2"){
	 			parent.goPage('STATSTIME', '시계열', 'statsTime.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+$('#CHN').html()+"&I_NAM="+$('#NAM').html()+"&I_DAT="+$('#DAT').html()+"&I_FKN="+grid.getValue(index.itemIndex, "O_GCDN")+"&I_GCD="+grid.getValue(index.itemIndex, "O_GCD")+"&I_ACD="+grid.getValue(index.itemIndex, "O_ACD") );
	 		//} else if(index.column == "O_GCDNIMG" && grid.getValue(index.itemIndex, "O_ACD") == "" || grid.getValue(index.itemIndex, "O_ACD") == "00"){ // 검사항목 이미지 삭제 20190412
	 		} else if(index.column == "O_GCDN" && grid.getValue(index.itemIndex, "O_ACD") == "" || grid.getValue(index.itemIndex, "O_ACD") == "00"){
	 			openPopup2(index);
	 		}
		};
		
		//20190403 최재원 대리님 요구사항(넘버링 제거) 
	    gridView2.setIndicator({ visible: false});
		
		var checkBar2 = gridView2.getCheckBar();
	    checkBar2.visible = true;
	    gridView2.setCheckBar(checkBar2);
	    
	    var stateBar2 = gridView2.getStateBar();
	    stateBar2.visible = false;
	    gridView2.setStateBar(stateBar2);
	    
	    gridView2.setAllCheck(true);

	    $(".fr_date").datepicker("setDate", new Date());
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 7);
        $("#I_FDT").datepicker('setDate', yearAgo);
		
	    //기간구분
	    var resultCode = "";
		resultCode = getCode(I_LOGMNU, I_LOGMNM, "TERM_DIV", 'N');
		$('#I_DGN').html(resultCode);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_DGN"));
		
		//$('#I_DGN_DIV').css('min-width','80px');
		//$('#I_DGN_DIV').css('width','80px');
		//$('.jcf-select jcf-unselectable jcf-select-form-control').css('padding','0 10 0 0');
		//jcf.replace($("#I_DGN_DIV"));
		
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
		
		//코로나 결과(양성여부)
		var resultCode4 = "";
		resultCode4 = getCode(I_LOGMNU, I_LOGMNM, "CORONA_RESULT", 'Y');
		$('#I_CORONA').html(resultCode4);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_CORONA"));
		
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
         //$("#I_FDT").val("2016-05-23");
         //$("#I_TDT").val("2016-05-25");
// //         $("#I_NAM").val("성매");
//         $("#I_PHOS").val("25169");
        //$("#I_FDT").attr( 'readOnly' , 'true' );
        //$("#I_TDT").attr( 'readOnly' , 'true' );
        
        // 처음 수진자별 결과조회 화면이 열릴 때, 파견사원 병원인 경우는 병원코드 입력값을 빈값으로 셋팅한다.
        // (이유는 파견사원 병원은 1개 계정으로 N개 병원 검사결과 데이터를 조회해야하므로,
        // 병원코드 값이 존재하지 않아야 N개 병원 검사결과 데이터가 모두 조회 가능하다.)
        if(s_paguen_hos_yn != ""){
	        $("#I_PHOS").val('');        	
        }
        
        // 기간 검색 구분 - 1일,3일,1개월,3개월
		var buttonList = getTerm(I_LOGMNU, I_LOGMNM, "S_TERM_A");
		$('#I_S_TERM').append(buttonList);
		
		// 결과 구분
		var chkDiv = getCheckBox(I_LOGMNU, I_LOGMNM, "RESULT_DIV");
		$("#RGN").append(chkDiv);
		
		//다음/이전버튼 비활성화
		$('#I_PNN_P').addClass("disabled");
        $('#I_PNN_N').addClass("disabled");
        
        //검색영역 접기
        $('.btn_open').click();
        
        //if(!isNull(Param_HOS) && $('#I_PECF').val() == "E"){
        	//console.log("Param_HOS = " + Param_HOS);
       	if(!isNull(Param_HOS)){
       		/** 메인에서 들어올경우 조회 조건 셋팅 **/
        	// 사원, 병원사용자
			$("#I_PHOS").val(Param_HOS);     // 병원코드
			//$("#I_FNM").val(Param_FNM);     // 병원명
			
	        //if(!isNull(Param_HOS)){				// 사원, 병원사용자
				//$("#I_PCHN").val(Param_CHN);     // 차트번호
				//$("#I_NAM").val(Param_NAM);     // 수진자명
				
				//$("#I_FDT").datepicker('setDate', Param_DAT);		// 접수일자 - 시작
				//$("#I_TDT").datepicker('setDate', Param_DAT);	 	// 접수일자 - 종료
			
			Param_FDT = Param_FDT.substring(0,4)+"-"+Param_FDT.substring(4,6)+"-"+Param_FDT.substring(6,8); 
        	Param_TDT = Param_TDT.substring(0,4)+"-"+Param_TDT.substring(4,6)+"-"+Param_TDT.substring(6,8);
        	
        	$("#I_FDT").datepicker('setDate', Param_FDT);		// 접수일자 - 시작
			$("#I_TDT").datepicker('setDate', Param_TDT);	 	// 접수일자 - 종료
				
	        //}
			search();	
        }
        else
    	{	
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
					if("<%=RCV%>" == "S_TERM_A"){
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
		  	if($('#I_PECF').val() == "C"){
	    		search();
	    		//$('#I_FNM').val("");
	    		//console.log("자동조회 중지");
	    	}
        }
	
        
		
	    //추가 검사
        //setReq01Grid()
        //if($('#I_PECF').val() == "C"){
        	//search();		
        //}
	    //dataResult();
	});
    	
    window.onload = function(){
    	var modal = document.getElementById("sts-Modal");
    	
    	var modalchk = "N";
    	
    	window.addEventListener("click", function(event){
			modal.style.display = "none";
        });		
    }
    
    
    /*
    function before1day(){
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 1);
        $("#I_FDT").datepicker('setDate',yearAgo);
        $("#I_TDT").datepicker('setDate',new Date);	
	}
	
	function before3day(){
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 3);
        $("#I_FDT").datepicker('setDate',yearAgo);
        $("#I_TDT").datepicker('setDate',new Date);	
	}
	
	function before1mon(){
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 30);
        $("#I_FDT").datepicker('setDate',yearAgo);
        $("#I_TDT").datepicker('setDate',new Date);	
	}
	
	function before3mon(){
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 90);
        $("#I_FDT").datepicker('setDate',yearAgo);
        $("#I_TDT").datepicker('setDate',new Date);	
	}
	*/
	var loding = false;
	function dataResult(data){
		//상세 초기화
		init();
		
		var formData = $("#searchForm").serialize();
		if(isNull(data)){
			data = "&I_DAT="+"";
			data += "&I_JNO="+"";
		}
		formData += data;
		callLoading(null, "on");
		ajaxCall("/rstUserList.do", formData,false);
	}
	
	// I_IGBN 이전 다음 구분/I_IDAT 접수일자/ I_IJNO 접수번호
	function paging(pnnId){
	
		//상세 초기화
		init();
		
		if($('#I_PNN_'+pnnId).attr("class") == "disabled"){
	         return;
	     }
		var data = "";
		var page = Number($('#I_CNT').val());
		var row = Number($('#I_ROW').val());
		
		//이전 다음 페이지 구분 설정 20190507
		$('#I_IGBN').val(pnnId);
		$('#I_PNN').val(pnnId);
		
		if(pnnId == "P"){ 			// 이전결과 페이지
			page_count--;
			if(row != 0){
				row = row - 40;
				$('#I_ROW').val(row);	
			}
			
			// 20190507 추가
			//이전 페이지 시작 접수일자
			$('#I_IDAT').val(dataProvider1.getValue(0, "DAT"));
			//이전 페이지 시작 접수번호
			$('#I_IJNO').val(dataProvider1.getValue(0, "JNO"));
		}
		if(pnnId == "N"){ 			// 다음 결과 페이지
			page_count++;
			row = row + 40;
			$('#I_ROW').val(row);
			
			// 20190507 추가
			//다음 페이지 시작 접수일자
			$('#I_IDAT').val(dataProvider1.getValue((page-1), "DAT"));
			//다음 페이지 시작 접수번호
			$('#I_IJNO').val(dataProvider1.getValue((page-1), "JNO"));
		}
		
		if(page_count == 0){
			$('#I_PNN_N').removeClass("disabled");
			$('#I_PNN_P').addClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		}
		
		//상세 리스트 초기화
		dataProvider2.clearRows();
		//결과값 초기화
		$('#NAM').html("");
		$('#CHN').html("");
		$('#DAT').html("");
		$('#JN').html("");
		$('#DPTN').html("");
		$('#SEX').html("");
		$('#AGE').html("");
		$('#ETCINF').html("");
		$('#ETC').html("");
		$('#HOSN').html("");
		$('#HOS').html("");
		$('#JNO').html("");
		$('#ADDRESS').html("");
		
		dataResult();
		
		//$('#I_PNN').val('P');
		//count--;
		//data = "&I_DAT="+dataProvider1.getValue(0, "DAT");
		//data += "&I_JNO="+dataProvider1.getValue(0, "JNO");
		//$('#I_ICNT').val(page);
		
		//console.log("확인");
		//$('#I_PNN').val('N');
		//count++;
		//data = "&I_DAT="+dataProvider1.getValue((page-1), "DAT");
		//data += "&I_JNO="+dataProvider1.getValue((page-1), "JNO");
		//$('#I_ICNT').val(page+1);
		
		/*
		if($('#I_PNN_'+pnnId).attr("class") == "disabled"){
	         return;
	     }
		
		var data = "";
		var page = Number($('#I_CNT').val());
		
		if(pnnId == "P"){ 			// 이전결과 페이지
			$('#I_PNN').val('P');
			count--;
			data = "&I_DAT="+dataProvider1.getValue(0, "DAT");
			data += "&I_JNO="+dataProvider1.getValue(0, "JNO");
			$('#I_ICNT').val(page);
		}
		if(pnnId == "N"){ 			// 다음 결과 페이지
			//console.log("확인");
			$('#I_PNN').val('N');
			count++;
			data = "&I_DAT="+dataProvider1.getValue((page-1), "DAT");
			data += "&I_JNO="+dataProvider1.getValue((page-1), "JNO");
			$('#I_ICNT').val(page+1);
			
		}
		if(count == 0){
			$('#I_PNN_N').removeClass("disabled");
			$('#I_PNN_P').addClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		}
		dataResult(data);
		*/
		
	}
	
	//rtrim
	function replaceAll(str){
		str.replace(/^\\s+/gi,"");
	}
	
	function init(){
	
		//RD DATA 초끼화
		rd_dat = 0;
	    rd_jno = 0;
	    rd_rgn1 = "";
	    rd_rgn2 = "";
	    rd_rgn3 = "";
	    rd_hak = "";
	    rd_pnn = "";
	    rd_gcd = "";
	    rd_acd = "";
	    
		//상세 초기화
		$('#DivMastResult').hide();
		$('#DivMastResult_Reference').hide();
		$('#DivRia').hide();
		$('#DivHematoma').hide();
		$('#DivObgy').hide();
		$('#DivHasimg').hide();
		$('#DivPopphoto').hide();
		$('#DivMicrobio').hide();
		$('#DivLmbYN').hide();
		$('#DivPathologyType').hide();
		$('#DivPathologyType2').hide();
		$('#DivCytologyType').hide();
		$('#DivCytologyType2').hide();
		$('#DivCytologyType3').hide();
		$('#DivCytogene').hide();
		$('#DivRemarktext').hide();
		$('#DivCoronaCT').hide();		// 결과 상세 화면에 'Corona CT' (show/hide)
		$('#DivCell').hide();			// 결과 상세 화면에 '세포유전' (show/hide)
		
		mast_str = "N";
		ria_str = "N";
		isTripleNew = "N";
		hematoma_str = "N";
		hematomaStr = "";
		obgyStr = "";
		obgy_str = "N";
		img_str = "N";
		photo_str = "N";
		microbio_str = "N";
		LmbYN_str = "N";
		Mw106rm3 = "N";
		Mw106rms6 = "N";
		pathologyType_str = "N";
		pathologyType2_str = "N";
		pathTitleYn = "N";
		CytologyType_str = "N";
		CytologyType2_str = "N";
		CytologyType3_str = "N";
		Cytogene_str = "N";
		remark_str = "N";
		remarkPositiveGcdn = "";
		remarkPositiveYn = "N";
		corona_ct_str = "N";		// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
		cell_str = "N";				// 세포유전 결과 존재시, 결과 상세 화면에 '세포유전' 문구를 출력할지 여부 flag(2022.08.29)
		
		//상세 내용 삭제
		$('#MastResult').html("");
		$('#Ria').html("");
		$('#Hematoma').html("");
		$('#Obgy').html("");
		$('#hasimg').html("");
		$('#Popphoto').html("");
		$('#Microbio').html("");
		$('#LmbYN').html("");
		$('#PathologyType').html("");
		$('#PathologyType2').html("");
		$('#CytologyType').html("");
		$('#CytologyType2').html("");
		$('#CytologyType3').html("");
		$('#CytologyType3').html("");
		$('#Cytogene').html("");
		$('#Remarktext').html("");
		$('#DivCoronaCT_val').html("");		// Corona CT 값 화면에 출력할 변수
		$('#DivCell_val').html("");			// 세포유전 값 화면에 출력할 변수 (2022.08.29)
		
		//이미지 초기화
		img_html = "";
		pdf_html = "";
		pdf_htmlx = "";
	}
	
	function init2(){
		var quickCount = 0;
		
			//검색조건 초기화
	    	changeDT("1M");
	    	
	    	$('#I_DGN').val("D");
	  		$('#I_NAM').val("");  		
	  		$('#I_PCHN').val("");
	  		$('#I_PETC').val("");
	  		$('#I_STS').val("");
	  		$('#I_PJKNF').val("1");
	  		$('#I_PJKN').val("");
	  		$('#I_HAK').val("");
	  		$('#I_BIRTH').val("");
	  		
	  		//사원일경우 병원정보 초기화 or 파견사원 병원인 경우 초기화
	  		if($('#I_PECF').val() == "E" || s_paguen_hos_yn != ""){
	  			$('#I_PHOS').val("");
	  	  		$('#I_FNM').val("");
			}
	  	
	  		jcf.replace($("#I_DGN"));
	  		jcf.replace($("#I_PJKNF"));
	  		jcf.replace($("#I_STS"));
	  		jcf.replace($("#I_HAK"));
	  		
	  		$('#I_RGN1').attr("checked", false);
	  		$('#I_RGN2').attr("checked", false);
	  		$('#I_RGN3').attr("checked", false);
	  		
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
					if("<%=RCV%>" == "S_TERM_A"){
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
	  		$('#I_PCHN').val("");
	  		$('#I_PETC').val("");
	  		$('#I_STS').val("");
	  		$('#I_PJKNF').val("1");
	  		$('#I_PJKN').val("");
	  		$('#I_HAK').val("");
	  		$('#I_BIRTH').val("");	  		
	  		
	  		//사원일경우 병원정보 초기화
	  		if($('#I_PECF').val() == "E"){
	  			$('#I_PHOS').val("");
	  	  		$('#I_FNM').val("");
			}
	  	
	  		jcf.replace($("#I_DGN"));
	  		jcf.replace($("#I_PJKNF"));
	  		jcf.replace($("#I_STS"));
	  		jcf.replace($("#I_HAK"));
	  		
	  		$('#I_RGN1').attr("checked", false);
	  		$('#I_RGN2').attr("checked", false);
	  		$('#I_RGN3').attr("checked", false);
	  	}
        
		
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/rstUserList.do"){
			//상세 초기화
			init();
			loding = false;
						
			var resultList =  response.resultList;
			
			gridView1.closeProgress();
			
			for (var i in resultList){
			
				if(resultList[i].PRF == "N"){
					resultList[i].PRF = "";
				}
				
				if(resultList[i].DAT != 0){
					// IDR 유승현 날짜변환 제거 2022.08.11
					resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
				}
				
				if(resultList[i].PNF != ""){
					if(resultList[i].PNF == "Y"){
						resultList[i].PNFIMG = "../images/common/ico_warning.png";
					} else if(resultList[i].PNF == "Y"){
						resultList[i].PNFIMG = "../images/common/ico_dis_warning.png";
					} else {
						resultList[i].PNFIMG = "../images/common/ico_dis_warning.png";
					}
				}
				
				if(resultList[i].C07H != ""){
					if(resultList[i].C07H == "Y"){
						resultList[i].C07HIMG = "../images/common/ico_high.png";
					} else if(resultList[i].C07H == "Y"){
						resultList[i].C07HIMG = "../images/common/ico_dis_high.png";
					} else {
						resultList[i].C07HIMG = "../images/common/ico_dis_high.png";
					}
				}
				
				if(resultList[i].C07L != ""){
					if(resultList[i].C07L == "Y"){
						resultList[i].C07LIMG = "../images/common/ico_low.png";
					} else if(resultList[i].C07L == "Y"){
						resultList[i].C07LIMG = "../images/common/ico_dis_low.png";
					} else {
						resultList[i].C07LIMG = "../images/common/ico_dis_low.png";
					}
				}
			}
			dataProvider1.setRows(resultList);
			gridView1.setDataSource(dataProvider1);
			gridView1.setAllCheck(false);
			
			if(dataProvider1.getRowCount() < $('#I_ICNT').val()){				// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
				$('#I_PNN_N').addClass("disabled");
			}else{
				//if($('#I_PNN').val() != "P"){
					dataProvider1.hideRows([(dataProvider1.getRowCount()-1)]);	// 보여줄 데이터 마지막 값 숨김
				//}
				$('#I_PNN_N').removeClass("disabled");
			}
			
			if($('#I_PNN').val() == "C"){										// 첫페이지일 경우 이전 페이지 버튼 숨김
				
				$('#I_PNN_P').addClass("disabled");
			}
			
			if(Param_MAINFLAG == "Y"){
			
				Param_MAINFLAG = "N";
				if(resultList.length > 0){
					var index = {
					    itemIndex: Param_POT
					};
					
					gridView1.setCurrent(index);
					//1. 오늘쪽 상세 자동 검색 호출, 상세 자동검색시 dataResult2(gridView1.getValues(Param_POT)); 주석 해제
					//dataResult2(gridView1.getValues(Param_POT));
					
					//1.오른쪽 상세 자동 검색 호출을 하지 않으면  callLoading(null, "off"); 호출하여 종료
					//2.오른쪽 상세 자동 검색 호출시에는 callLoading(null, "off"); 주석 처리
					callLoading(null, "off");
					
					//상세 초기화
					init();
				}else{
					callLoading(null, "off");
				}
				
			} else {
				if(resultList.length > 0){
					var index2 = {
						    itemIndex: 0
						};
					
					//gridView1.setCurrent(index2); // 2019.05.29 - 주석 제거시, 첫번째row 자동 선택
					//1. 오늘쪽 상세 자동 검색 호출 상세 자동검색시 dataResult2(gridView1.getValues(0)); 주석 해제
					//dataResult2(gridView1.getValues(0));
					
					//1.오른쪽 상세 자동 검색 호출을 하지 않으면  callLoading(null, "off"); 호출하여 종료
				    //2.오른쪽 상세 자동 검색 호출시에는 callLoading(null, "off"); 주석 처리
					callLoading(null, "off");
					//상세 초기화
					init();
				}else{
					callLoading(null, "off");
				}
			}
			
			// 조회 후, 지정 컬럼 기준으로 자동정렬.
			var fields = ["DAT2"];
			var dirs = ["descending"];
			gridView1.orderBy(fields, dirs);
		}
		
		if(strUrl == "/rstUserDtl.do"){
			
			//환자정보
			var rstUserDtl =  response.rstUserDtl;
			$('#NAM').html(rstUserDtl[0]["NAM"]);
			$('#CHN').html(rstUserDtl[0]["CHN"]);
			
			var datData = rstUserDtl[0].DAT.toString(); 
			var datCustom = datData.substring(0,4)+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			
			if(rstUserDtl[0].DAT != 0){
				$('#DAT').html(datCustom);	
			} else {
				$('#DAT').html(datData);
			}
			var dptn = rstUserDtl[0]["DPTN"];
			if (dptn == "//"){
				$('#DPTN').html();
			} else {
				$('#DPTN').html(rstUserDtl[0]["DPTN"]);
			}
			
			$('#JN').html(rstUserDtl[0]["JN"]);
			//$('#DPTN').html(rstUserDtl[0]["DPTN"]);
			$('#SEX').html(rstUserDtl[0]["SEX"]);
			$('#AGE').html(rstUserDtl[0]["AGE"]);
			$('#ETCINF').html(rstUserDtl[0]["ETCINF"]);
			$('#ETC').html(rstUserDtl[0]["ETC"]);
			$('#HOSN').html(rstUserDtl[0]["HOSN"]);
			$('#HOS').html(rstUserDtl[0]["HOS"]);
			$('#JNO').html(rstUserDtl[0]["JNO"]);
			$('#ADDRESS').html(rstUserDtl[0]["ADDRESS"]);
			
			// 2021.12.16 추가 
			// 오더의뢰지 버튼 누르면 현재 선택된 접수데이터의 병원코드가 넘어갈 수 있도록 변수가 필여함. 
			$('#order_img_hos').val(rstUserDtl[0]["HOS"])	
			
			// MAST결과
			mast_class1="<b>class1</b> : ";
		    mast_class2="<b>class2</b> : ";
		    mast_class3="<b>class3</b> : ";
		    mast_class4="<b>class4</b> : ";
		    mast_class5="<b>class5</b> : ";
		    mast_class6="<b>class6</b> : ";
		    
		    rlt_class = "";
		    
			//수진자 목록 상세
			var rstUserDtl2 =  response.rstUserDtl2;
			
			// 코로나 CT 결과
			var coronaCtVal = response.coronaCtVal;

			// 세포유전 결과 (2022.08.29)
			var cellVal = response.cellVal;
			
			// 영문결과지 출력 버튼 노출 여부 Flag
			var tmp_eng_result_flg = 0;
			
			if(rstUserDtl2.length > 0){
				
				// 20190709 - 검사결과에 양성반응인 경우, 수진자목록 더블클릭하면 2번이상 반복되서 출력됨.
				remarkPositiveGcdn = "";
				
				for (var i in rstUserDtl2){
					
					// 수정일자 : 2020.12.23
					// 접수일자/번호에 코로나 검사가 포함되어 있는지 체크. (영문결과지의 경우, 코로나 검사만을 위해 제작되어 있기 때문이다)
					if(rstUserDtl2[i].O_GCD.indexOf("71330") > -1	//71330 종목이 포함되어 있지 않으면
						|| rstUserDtl2[i].O_GCD.indexOf("71331") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71332") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71334") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71335") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71336") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71337") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71338") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71339") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71340") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71346") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71349") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71350") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71351") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("71341") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("00690") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("00691") > -1 
						|| rstUserDtl2[i].O_GCD.indexOf("21382") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("21709") > -1
						|| (rstUserDtl[0]["HOS"] == "34028" && rstUserDtl2[i].O_CKP == "5260@")
						|| rstUserDtl2[i].O_GCD.indexOf("21677") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("21683") > -1
						// 2021.11.18 검사운영팀 요청으로 영문결과지 추가
						|| rstUserDtl2[i].O_GCD.indexOf("21711") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("21712") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("89273") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("00701") > -1
						|| rstUserDtl2[i].O_GCD.indexOf("00702") > -1
					){ 
						tmp_eng_result_flg = tmp_eng_result_flg +1;
					}
					
					if(tmp_eng_result_flg > 0){
						$("#resultImg_eng").show();
						$("#resultImg_kor").show();
						$("#resultImg").hide();
					}else{
						$("#resultImg_eng").hide();
						$("#resultImg_kor").hide();
						$("#resultImg").show();
					}
					
					// 수정일자 : 2020.08.03
					// 문자결과,숫자결과 함께나오는 결과의 경우, '- 0' 으로 표기되는 결과가 존재하기 때문에 문자열을 '-'문자로 치환하여 처리함 (ex검사코드 : 21677)
					// ex) 병원(17082,천안충무병원), 접수일자(20200727), 접수번호(16172), 검사코드(21677)
		    	    if(rstUserDtl2[i].O_CHR == "- 0"){
		    	    	rstUserDtl2[i].O_CHR = "-";
		    	    }
				
					if(rstUserDtl2[i].O_CKI == "Y" && img_cnt == 0){
						calLCnt ++;
					}
					if (rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("Eugene") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("에이스") >= 0){
						LmbYN_str = "Y";
					}
					
					if(rstUserDtl2[i].O_PRF == "N"){
						rstUserDtl2[i].O_PRF = "";
					}
					
					var datData1 = "";
					
					datData1 = rstUserDtl2[i].O_REF;
					rstUserDtl2[i].O_REF = datData1;
					
					if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "* 아래참조  [phi = (P2PSA/freePSA)*√PSA]"; 	
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF1 = datData1+"(pg/mL)";
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF1 = datData1+"(ng/mL)";
					} else if(rstUserDtl2[i].O_GCD == 71300){
						rstUserDtl2[i].O_REF1 = "Negative";
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21639 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21640 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = datData1.replace("F", "\n여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF1 = datData1.replace("F", "\n여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 25000 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF1 = "●저위험군 : <38.00\n" +
												"●고위험군 : 38.00~84.99 (20-33주6일)/38.00~109.99 (34주 이상)\n" +
												"●전자간증 : ≥85.00(20-33주6일 )/≥110.00 (34주 이상)\n" +
												"●쌍태아 : 저위험군 <53.00, 전자간증의심 ≥53.00";		
					} else {
						rstUserDtl2[i].O_REF1 = rstUserDtl2[i].O_REF1.replace(/@@/gi,"\n").trim();	
					}
					
					//20190313 참고치 하드코딩 추가(테스트 오류 수정사항)
					if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "* 아래참조  [phi = (P2PSA/freePSA)*√PSA]"; 	
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF2 = datData1+"(pg/mL)";
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF2 = datData1+"(ng/mL)";
					} else if(rstUserDtl2[i].O_GCD == 71300){
						rstUserDtl2[i].O_REF2 = "Negative";
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21639 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21640 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = datData1.replace("F", "여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF2 = datData1.replace("F", "여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 25000 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF2 = "●저위험군 : <38.00\n" +
												"●고위험군 : 38.00~84.99 (20-33주6일)/38.00~109.99 (34주 이상)\n" +
												"●전자간증 : ≥85.00(20-33주6일 )/≥110.00 (34주 이상)\n" +
												"●쌍태아 : 저위험군 <53.00, 전자간증의심 ≥53.00";		
					} else {
						rstUserDtl2[i].O_REF2 = datData1.replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").trim();	
					}
					
					var datData2 = "";
					
					datData2 = rstUserDtl2[i].O_BDT.toString();
					
					if(rstUserDtl2[i].O_BDT != 0){
						rstUserDtl2[i].O_BDT2 = datData2.substring(0,4)+"-"+datData2.substring(4,6)+"-"+datData2.substring(6,8);	
					}else {
						rstUserDtl2[i].O_BDT2 = "";
					}
					rstUserDtl2[i].F010GCD = rstUserDtl2[i].O_GCD;
					rstUserDtl2[i].O_C07 = rstUserDtl2[i].O_C07.substring(0,1);
					rstUserDtl2[i].O_GCDNIMG = "../images/common/ico_viewer.png";
					
					if(rstUserDtl2[i].O_ACD != "" && rstUserDtl2[i].O_ACD != "00"){
						rstUserDtl2[i].O_OCD = "";
					}

					//mast_str
					if(rstUserDtl2[i].O_GCD == "00690" || rstUserDtl2[i].O_GCD == "00691"
					|| rstUserDtl2[i].O_GCD == "00701" || rstUserDtl2[i].O_GCD == "00702"
					|| rstUserDtl2[i].O_GCD == "00687"
					){
						
						var startChar = -1;
						var item_str = rstUserDtl2[i].O_GCDN;
						
						try{
							startChar = item_str.indexOf("]");
							
							while(item_str.indexOf("]") > 0){
								
								item_str = item_str.substring(startChar+1);
								
								// item_str 값을 substring 한 후에 
								// "]" 값이 맨앞에 있을경우, item_str 값이 잘못된 모양으로 출력되어 아래와 같이 변경함.
								// ex) 접수일자 : 20190509 ,접수번호 : 22028 
								if(item_str.indexOf("]") == 0){
									item_str = item_str.replace("]","");
								}
								
							}
							
							// 배열값인 i 값이 page2 판넬 제목 데이터에도 포함이 되기때문에
							// 2번째 page 에 no 값들이 1씩 많게 출력되는 문제가 있어서 아래와 같이 변경함.
							// ex) 접수일자 : 20190509 ,접수번호 : 22028 
							if(parseInt(i)<38){
								item_str = "("+i+")" + item_str;
							}else{
								i --;
								item_str = "("+i+")" + item_str;
								i ++;
							}
							
						} catch(e){}

						if(rstUserDtl2[i].O_CHR.indexOf("1 Class ")>=0){
							mast_class1 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("2 Class ")>=0){
							mast_class2 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("3 Class ")>=0){
							mast_class3 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("4 Class ")>=0){
							mast_class4 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("5 Class ")>=0){
							mast_class5 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("6 Class ")>=0){
							mast_class6 += item_str + " , ";
							mast_str="Y";
						}
						
						// 문자결과를 강제로 치환해줘야 하는 조건 추가
						// ex) 접수일자 : 20190509 ,접수번호 : 22028 
						mast_class1 = mast_class1.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class2 = mast_class2.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class3 = mast_class3.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class4 = mast_class4.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class5 = mast_class5.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						
					}
					
					var redTxtList = response.redTxtList;
					// 20170302 Remark 자동추가 입력(STI검사항목)
					var redTxtChk = "N";
					for(var c in redTxtList){
						if(redTxtList[c].R003GCD == rstUserDtl2[i].O_GCD){
							redTxtChk = "Y"; 
						}
					}
					
					if (redTxtChk == "Y" && (rstUserDtl2[i].O_CHR.indexOf("Positive") >=0 ) 
					) {
						if (remarkPositiveGcdn == "") {
							remarkPositiveGcdn += rstUserDtl2[i].O_GCDN;
						} else {
							remarkPositiveGcdn += ", "+rstUserDtl2[i].O_GCDN;
						}
						if(!isNull(remarkPositiveGcdn) ){
							remarkPositiveYn = "Y";
						} else {
							remarkPositiveYn = "N";
						}
						
						//$('#remarkPositive').html("의뢰하신 검체에서 " + remarkPositiveGcdn + " 양성 반응이 검출되었습니다.<br/>");
						
						
					}
					
					//remark_str
					if(rstUserDtl2[i].O_REMK == "Y"){
						remark_str = "Y";
					} 
					
					//코로나
					if(coronaCtVal.length > 0){
						if(coronaCtVal[0].CT1.trim() != "" || coronaCtVal[0].CT2.trim() != "" || coronaCtVal[0].CT3.trim() != ""){
							corona_ct_str = "Y";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
						}
					}
					
					//세포유전 (2022.08.29)
					if(cellVal.length > 0){
						if(cellVal[0].FFIRTXT.trim() != ""){
							cell_str = "Y";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2022.08.29)
						}
					}
					
					if(rstUserDtl2[i].O_CKI == "Y" && img_cnt == 0){
						img_cnt++;
						//img_sdb = rstUserDtl2[i].O_SDB;
						//$("#I_PMCD").val(pmcd);
						//$("#I_DAT").val(data.DAT);
						//$("#I_JNO").val(data.JNO);
						var rstUserParam = "";
						rstUserParam = "&I_DAT="+$('#I_DTLDAT').val();
						rstUserParam += "&I_JNO="+$('#I_DTLJNO').val();
						//rstUserParam += "&I_GCD="+rstUserDtl2[i].O_GCD;
						//rstUserParam += "&I_ACD="+rstUserDtl2[i].O_ACD;
						var gcdMerge1 = "";
						var gcdMerge2 = "";
						
						for (var c in rstUserDtl2){
							if(rstUserDtl2[c].O_CKI == "Y" && gcdMerge1 == "" ){
								//console.log("rstUserDtl2[c].O_GCD1 = "+ rstUserDtl2[c].O_GCD);
								gcdMerge1 = rstUserDtl2[c].O_GCD;
								gcdMerge2 = gcdMerge1;
							} else if(rstUserDtl2[c].O_CKI == "Y" && gcdMerge1 != "" ){
								//console.log("rstUserDtl2[c].O_GCD2 = "+ rstUserDtl2[c].O_GCD);
								if(gcdMerge1 != rstUserDtl2[c].O_GCD){
									gcdMerge2 += rstUserDtl2[c].O_GCD; 
									gcdMerge1 = rstUserDtl2[c].O_GCD;
								}
							}
						}
						
						$('#I_GCD').val(gcdMerge2);
						
						rd_gcd = $('#I_GCD').val();
					    rd_acd = $('#I_ACD').val();
					    
						img_dpt = "";
						if(rstUserDtl2[i].O_DPT == "5230"){
							img_dpt = "5295"	
						} else {
							img_dpt = rstUserDtl2[i].O_DPT;
						}
						
						var formData = $("#searchForm").serialize();
						formData += rstUserParam;
						ajaxCall("/rstUserMwr03rms5.do", formData, false);
					}
					
					//화면 flag 설정
					if(rstUserDtl2[i].O_CKP == "5250@"){
						//기형아 검사
						ria_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "HEMAT"){
						//혈종소견
						hematoma_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "OBGY"){
						//산전
						obgy_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5240S" || rstUserDtl2[i].O_CKP == "5240C"){
						microbio_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5260@" && LmbYN_str == "N"){
						pathologyType_str = "Y";
					if (rstUserDtl2[i].O_LMBN.indexOf("한미") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("포유") >= 0 
						 || rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("화신") >= 0 
						 || rstUserDtl2[i].O_LMBN.indexOf("운경") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("명진") >= 0
						 || (rstUserDtl2[i].O_LMBN.indexOf("삼성") >= 0 && parseInt(rstUserDtl2[i].O_BDT) > 20200316)
						 || (rstUserDtl2[i].O_LMBN.indexOf("하나병리") >= 0 && parseInt(rstUserDtl2[i].O_BDT) > 20200107)
						 || (rstUserDtl2[i].O_LMBN.indexOf("CFC") >= 0 && parseInt($('#I_DTLDAT').val()) > 20200719)
						){
							pathologyType_str = "N";
							pathologyType2_str = "Y";
						}
					} else if(rstUserDtl2[i].O_CKP == "5295@"){
						CytologyType2_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5270@"){
						CytologyType_str = "Y";
						
						if (rstUserDtl2[i].O_LMBN.indexOf("한미") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("포유") >= 0 
							 || rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("화신") >= 0 
							 || rstUserDtl2[i].O_LMBN.indexOf("운경") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("명진") >= 0
							 || (rstUserDtl2[i].O_LMBN.indexOf("삼성") >= 0 && parseInt(rstUserDtl2[i].O_BDT) > 20200316)
							 || (rstUserDtl2[i].O_LMBN.indexOf("하나병리") >= 0 && parseInt(rstUserDtl2[i].O_BDT) > 20200107)
							 || (rstUserDtl2[i].O_LMBN.indexOf("CFC") >= 0 && parseInt($('#I_DTLDAT').val()) > 20200719)
						){
								CytologyType3_str = "Y";
								CytologyType_str = "N";
						}
					} else if(rstUserDtl2[i].O_CKP == "5281@"){
						Cytogene_str = "Y";
					}
					//화면 flag 설정 끝
						
					//면역병리 진단보고 타이틀 확인
					if(rstUserDtl2[i].O_GCD == "51234" || rstUserDtl2[i].O_GCD == "51244"
					|| rstUserDtl2[i].O_GCD == "51247" || rstUserDtl2[i].O_GCD == "51253"){
						pathTitleYn = "Y";
					}
				}
				
				gridView2.closeProgress();
				dataProvider2.setRows(rstUserDtl2);
				
				for (var i=1;i <= dataProvider2.getRowCount(); i++){
					var chk = true;
					gridView2.checkItem(i-1
									  , chk
									  , false
									  , false);
				}
				
				gridView2.setDataSource(dataProvider2);
				
				
				//********************************************
				// MAST
				//********************************************
				
					if(mast_class1.length >3 )
					mast_class1 = mast_class1.substring(0, mast_class1.length-3) + "<br>";
							
					if(mast_class2.length >3 )
					mast_class2 = mast_class2.substring(0, mast_class2.length-3) + "<br>";
							
					if(mast_class3.length >3 )
					mast_class3 = mast_class3.substring(0, mast_class3.length-3) + "<br>";
					
					if(mast_class4.length >3 )
					mast_class4 = mast_class4.substring(0, mast_class4.length-3) + "<br>";
					
					if(mast_class5.length >3 )
					mast_class5 = mast_class5.substring(0, mast_class5.length-3) + "<br>";
					
					if(mast_class6.length >3 )
					mast_class6 = mast_class6.substring(0, mast_class6.length-3) + "<br>";				
					
					if("<b>class6</b><br>" != mast_class6.trim() && (mast_class6 != null || "" != mast_class6.trim())){	// class6 결과가 없는 경우,
						rlt_class += mast_class6 + "<br>";
					}
					if("<b>class5</b><br>" != mast_class5.trim() && (mast_class5 != null || "" != mast_class5.trim())){	// class5 결과가 없는 경우,
						rlt_class += mast_class5 + "<br>";
					}
					if("<b>class4</b><br>" != mast_class4.trim() && (mast_class4 != null || "" != mast_class4.trim())){	// class4 결과가 없는 경우,
						rlt_class += mast_class4 + "<br>";
					}
					if("<b>class3</b><br>" != mast_class3.trim() && (mast_class3 != null || "" != mast_class3.trim())){	// class3 결과가 없는 경우,
						rlt_class += mast_class3 + "<br>";
					}
					if("<b>class2</b><br>" != mast_class2.trim() && (mast_class2 != null || "" != mast_class2.trim())){	// class2 결과가 없는 경우,
						rlt_class += mast_class2 + "<br>";
					}
					if("<b>class1</b><br>" != mast_class1.trim() && (mast_class1 != null || "" != mast_class1.trim())){	// class1 결과가 없는 경우,
						rlt_class += mast_class1 + "<br>";
					}
					
					// 20190319 김윤영 과장님 요청사항 : 내용이 없는 상세내역 hide 처리 
					if(rlt_class == "" || rlt_class == "<br>" || rlt_class == "<br><br>&nbsp;&nbsp;"){
						mast_str="N";
					}
					
					$('#MastResult').html(rlt_class);
				
				//********************************************
				// 기형아 검사
				//********************************************
					var rstUserMw109rms = response.rstUserMw109rms
					
					isTripleNew = "N";
					if($('#I_DTLDAT').val() > 20140430){
						isTripleNew = "Y";
					}
					
					riaResult ="";
					
					if (rstUserMw109rms.length > 0){
						if(rstUserMw109rms[0].RRC != "E"){
							riaData = "Y"; 	
							riaResult += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9'>";
							riaResult += "<tr>";
							riaResult += "			  <td class='line10' height='25' width='120'>Birth date</td>";
							riaResult += "					<td class='line9' width='150'>"+parseDate(rstUserMw109rms[0].RBDT)+"</td>";
							riaResult += "					<td class='line10' width='120'>LMP</td>";
							riaResult += "					<td class='line9' width='150'>"+parseDate(rstUserMw109rms[0].RLPD)+"</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>LMP age</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RGW1+"</td>";
							riaResult += "					<td class='line10' >Ultrasound age</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RGW2+"</td>";
							riaResult += "				</tr>";
							riaResult += "			</table>";
							riaResult += "			<br />";
							riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25' width='180'>AFP</td>";
							riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RAFP+"</td>";
							riaResult += "					<td class='line10' width='80'>ng/mL</td>";
							riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RAFPM+"</td>";
							riaResult += "					<td class='line10' width='70'>MOM</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>hCG</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RHCG+"</td>";
							riaResult += "					<td class='line10' >IU/mL</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RHCGM+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>uE3</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RUE3+"</td>";
							riaResult += "					<td class='line10' >nmol/L</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RUE3M+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height=25>Inhibin A</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RINH+"</td>";
							riaResult += "					<td class='line10' >pg/mL</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RINHM+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							//접수일자 기준으로 보여지는 항목이 다름
							if (isTripleNew == "Y") { //접수날짜가 20140501 이상일떄
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>hCG</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBG+"</td>";
							riaResult += "					<td class='line10' >IU/mL</td>"; 
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBGM+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>PAPP-A</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPA+"</td>";
							riaResult += "					<td class='line10' >ng/mL</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPAM+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							} else { //접수날짜가 2014.05.01 이전일때
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25' >FreeB-hCG</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBG+"</td>";
							riaResult += "					<td class='line10' >mlu/mL</td> ";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBGM+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>PAPP-A</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPA+"</td>";
							riaResult += "					<td class='line10' >mg/L</td>";
							riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPAM+"</td>";
							riaResult += "					<td class='line10' >MOM</td>";
							riaResult += "				</tr>";
							}
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25'>NT</td>";
							riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RNT+"</td>";
							riaResult += "					<td class='line10' width='80'>mm</td>";
							riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RNTM+"</td>";
							riaResult += "					<td class='line10' width='70'>MOM</td>";
							riaResult += "				</tr>";
							riaResult += "			</table>";
							riaResult += "			<br />";
							
							riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25' width='280'>Risk by Maternal age</td>";
							riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].RRSK+"</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25' width='280'>Risk of Down syndrome</td>";
							riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].RDWN+"</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25' width='280'>Risk of Edward syndrome</td>";
							riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].REDW+"</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line9_l' height='25' colspan='2'>"+rstUserMw109rms[0].RRMK1+"</td>";
							riaResult += "				</tr>";
							riaResult += "			</table>";
							riaResult += "			<br/>";
							riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
							riaResult += "				<tr>";
							riaResult += "					<td class='line10' height='25' width='280'>Risk of Open NTD</td>";
							riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].RNTD+"</td>";
							riaResult += "				</tr>";
							riaResult += "				<tr>";
							riaResult += "					<td class='line9_l' height='25' colspan='2'>"+rstUserMw109rms[0].RRMK2+"</td>";
							riaResult += "				</tr>";
							riaResult += "			</table>";
								
							// 20190319 김윤영 과장님 요청사항 : 내용이 없는 상세내역 hide 처리 
							if(riaResult == "" || riaResult == "<br>" || riaResult == "<br><br>&nbsp;&nbsp;"){
								ria_str = "N";
							}
							
							$('#Ria').html(riaResult);
						
						} else {
							riaData = "N";
							ria_str = "N";
						}
					}
				
				//********************************************
				// 혈종검사소견 D폼 / N폼에 따라 해당하는 데이터 가져오기
				//********************************************
					var rstUserMw108rmA = response.rstUserMw108rmA;
					
					if(rstUserMw108rmA.length > 0){
						if(typeof rstUserMw108rmA[0].RREMK == "undefined"){
							hematomaStr = rstUserMw108rmA[0].F680TXT;
							
							if(typeof rstUserMw108rmA[0].F680TXT == "undefined"){
								hematoma_str = "N";
							}else{
								hematoma_str = "Y";
							}
							
						}else{
							hematomaStr = rstUserMw108rmA[0].RREMK;
						}	
					}
					
					$('#Hematoma').html(hematomaStr);
					
				//********************************************
				// 산전검사소견 BOB폼 / P폼에 따라 해당하는 데이터 가져오기
				//********************************************
					var rstUserMw108rmB = response.rstUserMw108rmB
					
					if(rstUserMw108rmB.length > 0){
						if(typeof rstUserMw108rmB[0].F680TXT != "undefined"){
							obgyStr = rstUserMw108rmB[0].F680TXT;
							obgy_str = "Y";
						}
					}
					
					$('#Obgy').html(obgyStr);
				
				//********************************************
				// microbio
				//********************************************
					var rstUserMw118rms1 = response.rstUserMw118rms1;
					
					if(rstUserMw118rms1.length>0)
					{
						microbio_html = "";
						
						microbio_html += "<tr>";
						microbio_html += 	"<td class='line10' width='20%' height='25'>Code</td>";
						microbio_html += 	"<td class='line10' width='30%'>Test Name</td>";
						microbio_html += 	"<td class='line10' width='20%'>Code</td>";
						microbio_html += 	"<td class='line10' width='30%'>Test Name</td>";
						microbio_html += "</tr>";
						
						for (var i in rstUserMw118rms1){
							microbio_html += "<tr>";
							microbio_html += "	<td class='line9' height='25'>"+rstUserMw118rms1[i].CGCD+"</td>";
							microbio_html += "	<td class='line9_l'>"+rstUserMw118rms1[i].CFEN+"</td>";
							microbio_html += "	<td class='line9'>"+rstUserMw118rms1[i].SGCD+"</td>";
							microbio_html += "	<td class='line9_l'>"+rstUserMw118rms1[i].SFEN+"</td>";
							microbio_html += "</tr>";
							
							if(rstUserMw118rms1[i].SGCD == "31022" || rstUserMw118rms1[i].SGCD == "31026"){
								microbio_html += "<tr>";
								microbio_html += 	"<td class='line9_l' colspan='4'><pre>"+rstUserMw118rms1[i].SRST+"</pre></td>";
								microbio_html += "</tr>";
							} else {
								microbio_html += "<tr>";
								microbio_html += 	"<td class='line9_l' colspan='4'><pre>"+rstUserMw118rms1[i].CRST+"</pre></td>";
								microbio_html += "</tr>";
							}
							
						}
						
						if(microbio_html == "" || microbio_html == "<br>" || microbio_html == "<br><br>&nbsp;" || microbio_html == "<br><br>&nbsp;&nbsp;"){
							microbio_str = "N";
						}
						
						
					} else {
						microbio_str = "N";
					}
					
					$('#Microbio').html(microbio_html);
				
				//********************************************
				// LmbYN 20140502 이전 / 20140502 이후
				//********************************************				
					var rstUserMw106rm3 = response.rstUserMw106rm3;
					
					if(LmbYN_str == "Y"){
						if($('#I_DTLDAT').val()<= 20140502){
							if(rstUserMw106rm3.length > 0){
								var strs1 = rstUserMw106rm3[0].RTXT;
								LmbYN_html = strs1.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
								Mw106rm3 = "Y";
								if(LmbYN_html == "" || LmbYN_html == "<br>" || LmbYN_html == "<br><br>&nbsp;" || LmbYN_html == "<br><br>&nbsp;&nbsp;"){
									Mw106rm3 = "N";
									LmbYN_str = "N";
								}
							}
							$('#LmbYN').html(LmbYN_html);
						}
					} else {
						if(rstUserMw106rm3.length > 0){
							var strs2 = rstUserMw106rm3[0].RTXT;
							pathologyType_html = strs2.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
							if(pathologyType_html == "" || pathologyType_html == "<br>" || pathologyType_html == "<br><br>&nbsp;" || pathologyType_html == "<br><br>&nbsp;&nbsp;"){
								pathologyType_str = "N";
							}
							$('#PathologyType').html(pathologyType_html);
						}else{
							$('#PathologyType').html("");
						}
					}
					//lmbyn 20140502 이전 끝
					
					var rstUserMw106rms6 = response.rstUserMw106rms6;
					
					if(LmbYN_str == "Y"){
						if($('#I_DTLDAT').val()> 20140502){
							if(rstUserMw106rms6.length > 0){
								var strs3 = rstUserMw106rms6[0].RTXT;
								LmbYN_html = strs3.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
								Mw106rms6 = "Y";
								if(LmbYN_html == "" || LmbYN_html == "<br>" || LmbYN_html == "<br><br>&nbsp;" || LmbYN_html == "<br><br>&nbsp;&nbsp;"){
									Mw106rms6 = "N";
									LmbYN_str = "N";
								}
							}	
							$('#LmbYN').html(LmbYN_html);
						}
					} else {
						if(rstUserMw106rms6.length > 0){
							var strs4 = rstUserMw106rms6[0].RTXT;
							pathologyType2_html = strs4.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");							
							if(pathologyType2_html == "" || pathologyType2_html == "<br>" || pathologyType2_html == "<br><br>&nbsp;" || pathologyType2_html == "<br><br>&nbsp;&nbsp;"){
								pathologyType2_str = "N";
							}
						}
						$('#PathologyType2').html(pathologyType2_html);
					}
					//lmbyn 20140502 이후 끝
				
				//********************************************
				// CytologyType
				//********************************************
					var rstUserMw105rms1 = response.rstUserMw105rms1;
					if(rstUserMw105rms1.length > 0){
						CytologyType_html = rstUserMw105rms1[0].RTXT;
					}
				
				//********************************************
				// CytologyType2
				//********************************************
					var rstUserMw106rms1 = response.rstUserMw106rms1;
					if(rstUserMw106rms1.length > 0){
						CytologyType2_html = rstUserMw106rms1[0].RTXT;
					}
				
				//********************************************
				// CytologyType3
				//********************************************
					var rstUserMw106rms5 = response.rstUserMw106rms5;
					if(rstUserMw106rms5.length > 0){
						CytologyType3_html = rstUserMw106rms5[0].RTXT;
					}
				
				//********************************************
				// Cytogene
				//********************************************
					var rstUserMw117rm = response.rstUserMw117rm;
					var param_rstUserMw117rm = response.param_rstUserMw117rm;
					
					if(param_rstUserMw117rm.O_PRC == "E"){
						Cytogene_prc = "N";
					} else {
						Cytogene_prc = "Y";
						if(rstUserMw117rm.length > 0){
							Cytogene_html = "";
							Cytogene_html += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9'>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line10' height='25' width='150' style='text-align:center'>Band resolution</td>";
							Cytogene_html += 		"<td class='line9' width='120' style='text-align:center'>"+rstUserMw117rm[0].RBRS+"</td>";
							Cytogene_html += 		"<td class='line10' width='150' style='text-align:center'>Counted cells</td>";
							Cytogene_html += 		"<td class='line9' width='120' style='text-align:center'>"+rstUserMw117rm[0].RCCN+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line10' height='25' style='text-align:center'>Analyzed cells</td>";
							Cytogene_html += 		"<td class='line9' style='text-align:center'>"+rstUserMw117rm[0].RCAN+"</td>";
							Cytogene_html += 		"<td class='line10' style='text-align:center'>Karyotyped cells</td>";
							Cytogene_html += 		"<td class='line9' style='text-align:center'>"+rstUserMw117rm[0].RKAP+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line10' style='text-align:center'>KARYOTYPE</td>";
							Cytogene_html += 		"<td class='line9_l' colspan='3'>"+rstUserMw117rm[0].RJPG+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line9_l' colspan='4'>"+rstUserMw117rm[0].RRMK+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += "</table>";
						}
					}
					
					if(Cytogene_html == "" || Cytogene_html == "<br>" || Cytogene_html == "<br><br>&nbsp;" || Cytogene_html == "<br><br>&nbsp;&nbsp;"){
						Cytogene_str = "N";
					}
					
					$('#Cytogene').html(Cytogene_html);
				
				//********************************************
				// Remarktext
				//********************************************
					remarkData = "";
					remarkDataRd = "";
					if(remarkPositiveYn == "Y"){
						remarkData = "<br>의뢰하신 검체에서 " + remarkPositiveGcdn + " 양성 반응이 검출되었습니다.";
						remarkDataRd = "<br>의뢰하신 검체에서 " + remarkPositiveGcdn + " 양성 반응이 검출되었습니다.";						
					}
					
					var rstUserMw107rms3 = response.rstUserMw107rms3;
					
					// 2023-01-19
					// 세포유전 요청으로 FISH 폼인 경우, remark 값이 화면에 표시되지 않도록 처리 (요청자 : 세포유전 , 김단비)
					if(rstUserMw107rms3.length > 0 && rstUserDtl2[0].O_SDB != "FISH"){
					//if(rstUserMw107rms3.length > 0){
						remarkData += rstUserMw107rms3[0].RREMK;
					}
					
					if(remarkData.length>0){
						remarkData = remarkData.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/>의/gi,"&nbsp;>의").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
					}
					
					// remark 내용에 <요경검> 이라고 출력되는 타이틀을 <핵의학>으로 변경 요청
					// 요청자 : 전준호 부장
					// 접수일자 : 20190528
					// 접수번호 : 9717
					if(remarkData.indexOf("요경검") > -1){
						remarkData = remarkData.replace('요경검','경검');
					}
					
					if(CytologyType_html == "" || CytologyType_html == "<br>" || CytologyType_html == "<br><br>&nbsp;" || CytologyType_html == "<br><br>&nbsp;&nbsp;"){
						CytologyType_str = "N";
					}
					
					if(CytologyType2_html == "" || CytologyType2_html == "<br>" || Cytogene_html == "<br><br>&nbsp;" || Cytogene_html == "<br><br>&nbsp;&nbsp;"){
						CytologyType2_str = "N";
					}
					
					if(CytologyType3_html == "" || CytologyType3_html == "<br>" || CytologyType3_html == "<br><br>&nbsp;" || CytologyType3_html == "<br><br>&nbsp;&nbsp;"){
						CytologyType3_str = "N";
					}
					
					$('#CytologyType').html(CytologyType_html);
					$('#CytologyType2').html(CytologyType2_html);
					$('#CytologyType3').html(CytologyType3_html);
					
					if(remarkData == "" || remarkData == "<br>" || remarkData == "<br><br>&nbsp;" || remarkData == "<br><br>&nbsp;&nbsp;"){
						remark_str = "N";
					}
					
					$('#Remarktext').html(remarkData);
				
				//********************************************
				// Corona CT
				//********************************************
				
					coronaCtData = "";	// 코로나 CT 결과 존재여부 flag
					cellData = "";		// 세포유전 결과 존재여부 flag
					
					// 코로나 CT 값 (2020.03.05)
					if(coronaCtVal.length > 0){
						coronaCtData += coronaCtVal[0].CT1;
						coronaCtData += "<br>";
						coronaCtData += coronaCtVal[0].CT2;
						coronaCtData += "<br>";
						coronaCtData += coronaCtVal[0].CT3;
					}
					
					// 세포유전 값 (2022.08.29)
					if(cellVal.length > 0){
						cellData += cellVal[0].FFIRTXT
					}
					
					if(coronaCtData == "" || coronaCtData == "<br>" || coronaCtData == "<br><br>&nbsp;" || coronaCtData == "<br><br>&nbsp;&nbsp;"){
						corona_ct_str = "N";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
					}
					
					$('#DivCoronaCT_val').html(coronaCtData);	// Corona CT 값 화면에 출력할 변수
					
					
					// 세포유전 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2022.08.29)
					if(cellData == ""){
						cell_str = "N";
					}
					$('#DivCell_val').html(cellData);	// 세포유전 값 화면에 출력할 변수
				
			}
			calLCnt--;
			mastResult();
					
		}
		
		if(strUrl == "/rstUserMwr03rms5.do"){
			var resultList = response.resultList;
			url_str = "";
			
			pdf_htmlx += "<tr>";
			pdf_htmlx += "	<td class='line3_l' align='left'>";
			pdf_htmlx += "		<a href='http://img.neodin.com:8080/servlet/com.neodin.files.Download?sourceFilePathName=AdbeRdr70_kor_full.exe&destFileName=AdbeRdr70_kor_full.exe' style='text-decoration:underline;'>Adobe Reader은 여기서 내려 받을수 있습니다.</a>";
			pdf_htmlx += "	</td>";
			pdf_htmlx += "</tr>";
			
			for(var r in resultList){
				
				if(r == resultList[r].URL.length-1){
					return;
				}
				url_str = resultList[r].URL.substring(resultList[r].URL.lastIndexOf("\\")+1);
				var img_dpt2 = resultList[r].URL.substring(17, 21);
				if(url_str.indexOf("pdf") > 0 || url_str.indexOf("PDF") > 0){
					photo_str = "Y";
						
						pdf_html += "<tr>";
						pdf_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						pdf_html += 		"<a href='http://img.neodin.com:8080/servlet/com.neodin.files.PdfResultPath?sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".pdf' width='100%' style='text-decoration:underline;'>";
						pdf_html +=         "<font color='#0000ff' >"+$('#I_DTLDAT').val()+"("+$('#NAM').html()+") This service is in preparation</font></a>";
						pdf_html +=		"</td>";
						pdf_html += "</tr>";

				} else {
					img_str = "Y";
					if(resultList[r].SDB.trim() == "LSD" || resultList[r].SDB.trim() == "NSR"){
						img_rotate = "Y";
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>"; 
						img_html += 		"<img src='/rotateImage.do?url=http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath&sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg&radians=270' width=100%>";
						img_html +=		"</td>";
						img_html += "</tr>";						
					} else if(resultList[r].URL.indexOf("25000.jpg") > -1) {
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						img_html += 		"<img src='/images/remarkImg/25000.jpg' width=100%>"; 
						img_html +=		"</td>";
						img_html += "</tr>";	
					} else {
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						img_html += 		"<img src='http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg' width=100%>"; 
						img_html +=		"</td>";
						img_html += "</tr>";	
					}
				}
				
			}
			
			pdf_htmlx += pdf_html;
			
			$('#hasimg').html(img_html);
			$('#Popphoto').html(pdf_htmlx);
			calLCnt--;
			setTimeout("mastResult()",1000);
		}
	}
	
	function dataResult2(data){
		
		//상세 초기화
		init();
		
		//RD DATA
		rd_dat = data.DAT;
	    rd_jno = data.JNO;
	    
	    if($('#I_RGN1').is(":checked")){
	    	rd_rgn1 = "Y";	
	    }
	    if($('#I_RGN2').is(":checked")){
	    	rd_rgn2 = "Y";	
	    }
	    if($('#I_RGN3').is(":checked")){
	    	rd_rgn3 = "Y";	
	    }
	    
	    rd_hak = $('#I_HAK').val();
	    rd_pnn = $('#I_PNN').val();
	    
		var formData = $("#searchForm").serialize();
		img_cnt = 0;
		img_sdb = "";
		img_rotate = "";
		
		
		var rstUserParam = "";
		rstUserParam = "&I_DAT="+data.DAT;
		rstUserParam += "&I_JNO="+data.JNO;
		
		$('#I_DTLDAT').val(data.DAT);
		$('#I_DTLJNO').val(data.JNO);
		
		formData += rstUserParam;
		var url = "/rstUserDtl.do";
		calLCnt = 1;
		ajaxCall(url, formData, false);
	}
	
	var calLCnt = 0;
	
	//조회
	function search(){
		
		// 2022-02-14 (추가)
		// 뱅킹통계 메뉴 사용을 위해 외부에 오픈된 계정(SG0001)은 수진자별결과조회 조회 불가함!!
		if(s_uid == "SG0001") {return;}
	
		//상세 초기화
		init();
		
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
 		
			if(!isNull($('#I_PHOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_PHOS').val());
				$('#I_FNM').val(I_FNM);
			}
 		
			// 2020.04.21 (수)
 			// 검색초기화 후, 조회를 할 경우, 
 			// 파견사원인지 체크 후, 병원/사원 값을 로그인 유저 정보로 셋팅되도록 한다.
 			if(s_paguen_hos_yn != ""){
 				$('#I_PECF').val(s_ecf);
 			}
		}else{
		
			// 병원코드 미입력되었을 경우, 병원이름도 미입력 상태로 바꿈
			if($('#I_PHOS').val() == ""){
				$('#I_FNM').val("");
			}else{
				// 2020.04.21 (수)
				// 병원코드 입력시 병원/사원 정보를 사원으로 변경하여
				// 사원처럼 입력한 병원코드 1곳 데이터를 조회하도록 한다.
	 			if(s_paguen_hos_yn != ""){
	 				$('#I_PECF').val("E");
				}
			}
		}

		init();
		gridView1.cancel();
		dataProvider1.clearRows();
		dataProvider2.clearRows();
		
		if(!searchValidation()) {return;}
		
		
		if($("#I_TDT").val()==""||$("#I_FDT").val()==""){
	    	CallMessage("273","alert");      //조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
			return ;
	    }
		 

		var cnt = Number($('#I_CNT').val());
		$('#I_ICNT').val(Number($('#I_CNT').val())+1);
		$('#I_ROW').val(Number($('#I_IROW').val()));
		$('#I_PNN').val("C");
		
		$('#NAM').html("");
		$('#CHN').html("");
		$('#DAT').html("");
		$('#JN').html("");
		$('#DPTN').html("");
		$('#SEX').html("");
		$('#AGE').html("");
		$('#ETCINF').html("");
		$('#ETC').html("");
		$('#HOSN').html("");
		$('#HOS').html("");
		$('#JNO').html("");
		$('#ADDRESS').html("");
		
		// 조회버튼클릭!!
		dataResult();
	}
	
	function searchValidation(){
		
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		
		//I_PINQ = Y ==> 수진자명，챠트번호　조건만 들어가는 기본 조건
		if(
				(
						$('#I_DGN').val() == "P" 	// 기간구분(접수일자:D/보고일자:P)
					//|| $('#I_PETC').val() != ""		// 검체번호 	
					|| $('#I_STS').val() != ""  	// 검사진행
					|| $('#I_PJKN').val() != "" 	// 진료구분 (진료과/진료의사) 
					|| $('#I_HAK').val() != "" 		// 검사분야
					|| $('#I_RGN1').is(":checked") == true 	// 일반(체크박스)
					|| $('#I_RGN2').is(":checked") == true  // 조직(체크박스)
					|| $('#I_RGN3').is(":checked") == true  // 세포(체크박스)
					//|| $('#I_BIRTH').val() != "" 		// 검사분야
				)
				||(
						$("#I_FDT").val() == "" 	// 기간(시작일)
						&& $("#I_TDT").val() == "" 	// 기간(종료일)
						//&& $('#CHN').html("") != ""
						&& $('#I_PCHN').val() != ""
						&& $('#I_BIRTH').val() == ""// 생년월일
				)
		){
			console.log("RPG 호출");
			$("#I_PINQ").val("N");
		} else {
			console.log("SQL 호출");
			$("#I_PINQ").val("Y");
		}
		
		// 유효성검사 - 최대 검색 기간 체크 !!
		var now = new Date();
	    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
			CallMessage("301","alert",["10"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
			return ;
	   	}
		
		// 유효성검사 - 병원코드 입력됬는지 체크!!
		if($('#I_PHOS').val() == ""	//검색조건에 병원코드가 입력되지 않으면서 사원인 경우만 검색이 불가능 하도록 변경 
			&& $('#I_PECF').val() =="E" && s_paguen_hos_yn == ""	// 파견사원의 경우, 병원코드가 비어있어도 검색
		){
			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"],dataClean);
			return false;
		}
		
		// 유효성검사 - 종료일이 시작일보다 이후 날짜인지 체크!!
		if(sDate > eDate){
			CallMessage("229","alert","", dataClean); // 조회기간을 확인하여 주십시오.
			return false;
		}		
		
		// 유효성검사 - 차트번호 자리수 체크!!
		if(strByteLength($("#I_PCHN").val()) > 15){
			CallMessage("292", "alert", ["차트번호는 15 Byte"], dataClean);
			return false;
		}
		
		// 유효성검사 - 검체번호 자리수 체크!!
		if(strByteLength($("#I_PETC").val()) > 50){
			CallMessage("292", "alert", ["검체번호는 50 Byte"], dataClean);
			return false;
		}
		
		// 유효성검사 - 생년월일 자리수 체크!!
		if(strByteLength($("#I_BIRTH").val()) > 6){
			CallMessage("292", "alert", ["생년월일은 6 Byte"], dataClean);
			return false;
		}
		
		// page_count 초기화
		page_count = 0;
		
		// 최초 조회 시 I_IGBN = C / I_IDAT = 0 / I_IJNO = 0
		$("#I_IGBN").val("C");
		$("#I_IDAT").val("0");
		$("#I_IJNO").val("0");
		
		// 검색조건에 시작일, 종료일 입력됬는지 체크!!
		if($("#I_FDT").val() != "" && $("#I_TDT").val() != ""){
			if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
				CallMessage("273", "alert","", dataClean);
			   return false;
			}	
		}
		return true;
	}
	
	function dataClean(){
		init();
		gridView1.cancel();
		dataProvider1.clearRows();
		dataProvider2.clearRows();
	}
	
	function mastResult(){
	
		$('#DivMastResult').hide();
		$('#DivMastResult_Reference').hide();
		$('#DivRia').hide();
		$('#DivHematoma').hide();
		$('#DivObgy').hide();
		$('#DivHasimg').hide();
		$('#DivPopphoto').hide();
		$('#DivMicrobio').hide();
		$('#DivLmbYN').hide();
		$('#DivPathologyType').hide();
		$('#DivPathologyType2').hide();
		$('#DivCytologyType').hide();
		$('#DivCytologyType2').hide();
		$('#DivCytologyType3').hide();
		$('#DivCytogene').hide();
		$('#DivRemarktext').hide();
		$('#DivCoronaCT').hide();
		$('#DivCell').hide();			// 결과 상세 화면에 '세포유전' (show/hide)
		
		if(pathTitleYn == "Y"){
			$('#DivLmbYNTitle').html("면역병리 진단보고");
			$('#DivPathologyTypeTitle').html("면역병리 진단보고");
			$('#DivPathologyType2Title').html("면역병리 진단보고");
		} else {
			$('#DivLmbYNTitle').html("조직병리 진단보고");
			$('#DivPathologyTypeTitle').html("조직병리 진단보고");
			$('#DivPathologyType2Title').html("조직병리 진단보고");
		}
		
		if(photo_str == "Y"){
			mast_str = "N";				// MAST 검사
			ria_str = "N";				// 기형아 검사
			hematoma_str = "N";			// remark(혈종)
			obgy_str = "N";				// remark(산전)
			img_str = "N";				// 이미지
			microbio_str = "N";			// microbio
			LmbYN_str = "N";			// LmbYN	
			pathologyType_str = "N";	// pathologyType
			pathologyType2_str = "N";	// pathologyType2
			CytologyType_str = "N";		// CytologyType
			CytologyType2_str = "N";	// CytologyType2
			CytologyType3_str = "N";	// CytologyType3
			Cytogene_str = "N";			// Cytogene
			Cytogene_prc = "N";			// Cytogene
			remark_str = "N";			// remark 세포병리로 삽입로직 복구
			corona_ct_str = "N";		// Corona CT -> 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
			cell_str = "N";				// Corona CT -> 세포유전 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2022.08.29)
		}
		
		// MAST 검사
		if(mast_str == "Y"){
			$('#DivMastResult').show();
			$('#DivMastResult_Reference').show();
			
		} else {
			$('#DivMastResult').hide();
			$('#DivMastResult_Reference').hide();
		}
		
		// 기형아 검사
		if(ria_str == "Y"){
			if($("#I_DTLDAT").val() >= 20170609){
				$("#REALAGE").text("분만 시 나이");
			} else {
				$("#REALAGE").text("나이");
			}
			$('#DivRia').show();		
		} else {
			$("#REALAGE").text("나이");
			$('#DivRia').hide();
		}
		
		if(hematoma_str == "Y"){$('#DivHematoma').show();} else {$('#DivHematoma').hide();}							// remark(혈종)
		if(obgy_str == "Y"){$('#DivObgy').show();} else {$('#DivObgy').hide();}										// remark(산전)		
		if(img_str == "Y"){$('#DivHasimg').show();} else {$('#DivHasimg').hide();}									// 이미지
		if(photo_str == "Y"){$('#DivPopphoto').show();} else {$('#DivPopphoto').hide();}							// pdf
		if(microbio_str == "Y"){$('#DivMicrobio').show();} else {$('#DivMicrobio').hide();}							// microbio
		if(LmbYN_str == "Y"){$('#DivLmbYN').show();} else {$('#DivLmbYN').hide();}									// LmbYN
		if(pathologyType_str == "Y"){$('#DivPathologyType').show();} else {$('#DivPathologyType').hide();}			// pathologyType
		if(pathologyType2_str == "Y"){$('#DivPathologyType2').show();} else {$('#DivPathologyType2').hide();}		// pathologyType2		
		if(CytologyType_str == "Y"){$('#DivCytologyType').show();} else {$('#DivCytologyType').hide();}				// CytologyType
		if(CytologyType2_str == "Y"){$('#DivCytologyType2').show();} else {$('#DivCytologyType2').hide();}			// CytologyType2
		if(CytologyType3_str == "Y"){$('#DivCytologyType3').show();} else {$('#DivCytologyType3').hide();}			// CytologyType
		if(Cytogene_str == "Y" && Cytogene_prc == "Y"){$('#DivCytogene').show();} else {$('#DivCytogene').hide();}	// Cytogene 
		if(remark_str == "Y"){$('#DivRemarktext').show();} else {$('#DivRemarktext').hide();}						// remark 세포병리로 삽입로직 복구
		
		// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
		if(corona_ct_str == "Y"){$('#DivCoronaCT').show();} else {$('#DivCoronaCT').hide();}

		// 세포유전 결과 존재시, 결과 상세 화면에 '세포유전' 문구를 출력할지 여부 flag(2020.08.29)
		if(cell_str == "Y"){$('#DivCell').show();} else {$('#DivCell').hide();}
		
		TabResize();
		
		if(calLCnt ==0){
			setTimeout(function() {
				loding = true;
				
				//20190326 요청사항 수정(화면 줄였을때 포커싱 이동)
				$('#addTestButton')[0].scrollIntoView(true); 
				
				//$('#dtlDiv').focus();
				callLoading(null, "off");
			}, 300);
		}
	}
		
	function openPopup2(index){

        var itemIndex = index.itemIndex;
		var gridValus = gridView2.getValues(itemIndex);
		var gdcn = gridView2.getValue(itemIndex, "O_GCDN");
		fnOpenPopup("/testItem01.do",gdcn +" 검사 항목 상세",gridValus,callFunPopup2);
	}
	
	function  callFunPopup2(url,iframe,gridValus){
		iframe.gridViewRead(gridValus);
	}
	
	//추가검사
	function openPopup(str){
		var formData = $("#searchForm").serialize();
		if(str == "1"){
			if($('#NAM').html() == ""){
				messagePopup("","","추가 검사는 검사결과 상세 데이터가 필수입니다." );
				return;
			}
			fnOpenPopup("/testReq01Popup.do","추가의뢰",formData,callFunPopup);	
		} else if(str == "2"){
			if($('#NAM').html() == ""){
				messagePopup("","","오더 의뢰지는 검사결과 상세 데이터가 필수입니다." );
				return;
			}
			fnOpenPopup("/orderReqPopup.do","오더의뢰지",formData,callFunPopup);
		} else if(str == "3"){
			fnOpenPopup("/hospSearchS.do","병원조회",formData,callFunPopup);
		} else if(str == "4"){
			//formData
			var dataRows = gridView1.getCheckedRows();
			if(dataRows.length == 0){
				messagePopup("","","수진자 목록 최소 1건 이상 선택 필수입니다." );
				return;
			}
			var data = [];
			for(var i in dataRows){
				data.push(dataProvider1.getJsonRow(dataRows[i]));
			}
			if (dataRows.length > 0){
				fnOpenPopup("/smsReqPopup.do","SMS",data,callFunPopup);	
			}
			
		} else if(str == "5"){
			if($('#I_RGN1').is(":checked")){
		    	rd_rgn1 = "Y";	
		    }
		    if($('#I_RGN2').is(":checked")){
		    	rd_rgn2 = "Y";	
		    }
		    if($('#I_RGN3').is(":checked")){
		    	rd_rgn3 = "Y";	
		    }
		    
			fnOpenPopup("/addReq01Popup.do","추가의뢰 등록",formData,callFunPopup);
		} else if(str == "9"){
			var dataRows = gridView2.getCheckedRows();
			
			if (dataRows.length == 0){
				messagePopup("","","검사 결과 최소 1건 이상 선택 필수입니다." );
				return;
			}
			if (dataRows.length > 0){
				var data = [];
				
				var grid1 =  gridView1.getCurrent();
				var gridData  =dataProvider1.getJsonRow(grid1.itemIndex);
				gridData["RSTUSER"] = "RSTUSER";
				gridData["JNO"] = $("#I_DTLJNO").val();
				gridData["DAT"] = $("#I_DTLDAT").val();
				gridData["I_HOS"] = $("#I_PHOS").val();
				console.log("######1 :: "+gridData["RSTUSER"]);
				console.log("######2 :: "+gridData["JNO"]);
				console.log("######3 :: "+gridData["DAT"]);
				console.log("######4 :: "+gridData["I_HOS"]);
				
				data.push(gridData);
				for(var i in dataRows){
					data.push(dataProvider2.getJsonRow(dataRows[i]));
				}
				fnOpenPopup("/rstReport.do","결과지 출력",data,callFunPopup);
			}
		} 
	}
	
	function  callFunPopup(url,iframe,formData){
		if(url == "/testReq01Popup.do"){
			var userData = [];
			userData.I_DAT = $('#I_DTLDAT').val();
			userData.I_JNO = $('#I_DTLJNO').val();
			userData.I_HOS = $('#I_PHOS').val();
			userData.I_RGN1 = $('#I_RGN1').val();
			userData.I_RGN2 = $('#I_RGN2').val();
			userData.I_RGN3 = $('#I_RGN3').val();
			userData.I_HAK = $('#I_HAK').val();
			userData.I_SAB = $('#I_SAB').val();
			
			
			iframe.userData1(userData);
			iframe.testReq01Call(formData);	
		} else if(url == "/orderReqPopup.do"){
			iframe.orderReq01Call(formData);
		} else if(url == "/hospSearchS.do"){
			formData = {};
			formData["I_HOS"] =  $('#I_PHOS').val();
			formData["I_FNM"] =  $('#I_FNM').val();
			iframe.gridViewRead(formData);
		} else if(url == "/smsReqPopup.do"){
			var userData = [];
			userData.I_HOS = $('#I_PHOS').val();
			userData.I_NAM = "";
			iframe.userData1(userData);
			iframe.smsReq03Call(formData);
		} else if(url == "/addReq01Popup.do"){
			var userData = [];
			userData.I_DAT = $('#I_DTLDAT').val();
			userData.I_JNO = $('#I_DTLJNO').val();
			userData.I_HOS = $('#I_PHOS').val();
			userData.I_RGN1 = $('#I_RGN1').val();
			userData.I_RGN2 = $('#I_RGN2').val();
			userData.I_RGN3 = $('#I_RGN3').val();
			userData.I_HAK = $('#I_HAK').val();
			userData.I_SAB = $('#I_SAB').val();
			
			iframe.userData2(userData);
			iframe.addReq01Call(formData);
		} else if(url == "/rstReport.do"){
			iframe.getReport(formData);
		}
	}
	
	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(data[0] == "/hospSearchSList.do"){
			$('#I_PHOS').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
	    }
	}
	
	function rstDisplayPrint(){
		if($('#NAM').html() == ""){
			messagePopup("","","화면 출력은 검사결과 상세 데이터가 필수입니다." );
			return;
		}
		
		if($('#I_RGN1').is(":checked")){
	    	rd_rgn1 = "Y";	
	    }
	    if($('#I_RGN2').is(":checked")){
	    	rd_rgn2 = "Y";	
	    }
	    if($('#I_RGN3').is(":checked")){
	    	rd_rgn3 = "Y";	
	    }
	    
		//승인 안되면 출력 못함
        var w = window.screen.width ;
        var h = window.screen.height ;
        //var dataset = ;
        //reportPop.inc의 셋팅값 받기
        //var datadd = "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=b330d8ba-f922-4214-bdcb-590bd60a701f";
        var viewerUrl = "<%=viewerUrl%>";
        //var dataSet = dataList[index].I_IMG;
        
        <%-- var mrd_path = "<%=systemUrl%>/mrd/GENERAL-01.mrd"; --%>
        <%-- var mrd_path = "<%=systemUrl%>/mrd/77-ECA4-TMP-02.mrd"; --%>
        // mrd_path 기존_backup
        <%-- var mrd_path = "<%=systemUrl%>/mrd/rstDisplayPrint.mrd"; --%>
        var mrd_path = "rstDisplayPrint.mrd";
        var rdServerSaveDir = "<%=rdSaveDir%>";
        var rd_param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv SYSURL["+trmsSystemUrl+"]COR["+s_cor+"]UID["+s_uid+"]IP["+s_ip+"]DAT["+rd_dat+"]JNO["+rd_jno+"]RGN1["+rd_rgn1+"]RGN2["+rd_rgn2+"]RGN3["+rd_rgn3+"]HAK["+rd_hak+"]SAB[ ]PNN[C]ICNT[1000]GCD["+rd_gcd+"]ACD["+rd_acd+"]GAC["+gac+"]GAC1["+gac1+"]GAC2["+gac2+"]DT["+dt+"]BDT1["+bdt1+"]BDT2["+bdt2+"]MASTFLAG["+mast_str+"]MASTDATA["+rlt_class+"]RIAFLAG["+ria_str+"]RIATRIPLE["+isTripleNew+"]HEMAFLAG["+hematoma_str+"]OBGYFLAG["+obgy_str+"]IMGFLAG["+img_str+"]IMGROTATE["+img_rotate+"]IMGDPT["+img_dpt+"]PHOTOFLAG["+photo_str+"]MICROBIOFLAG["+microbio_str+"]MICROBIO119YN["+microbio119yn+"]MICROBIO118YN["+microbio118yn+"]LMBYNFLAG["+LmbYN_str+"]MW106RM3["+Mw106rm3+"]MW106RMS6["+Mw106rms6+"]PATHOLOGY1FLAG["+pathologyType_str+"]PATHOLOGY2FLAG["+pathologyType2_str+"]PATHTITLEYN["+pathTitleYn+"]CYTOLOGYFLAG["+CytologyType_str+"]CYTOLOGY2FLAG["+CytologyType2_str+"]CYTOLOGY3FLAG["+CytologyType3_str+"]CYTOGENEFLAG["+Cytogene_str+"]CYTOGENEPRC["+Cytogene_prc+"]REMARKDATARD["+remarkDataRd+"]REMARKFLAG["+remark_str+"] /rstyle [p {font-size:9pt; font-family:바탕,바탕체,나눔고딕;}] /rimageopt render [1] /rsaveopt [4288] /rusecxlib /rpdfexportapitype [0] /rimagexdpi [110]  /rimageydpi [110] /p /rusesystemfont";
        
        
         //console.log("rd_param = " ,rd_param);
        //rv COR['NML']UID['KNNSSS']JDAT['20180301']JNO['27604']GCD['|05520|11001|11002|11004|11005|11006|11007|11008|11009|11011|11012|11013|11014|11015|11052|11310|']RFN['BMA8ARA ']
        //rnl [|] /rdata ["+dataSet+"]
        var systemDownDir = "<%=rdSysSaveDir%>/"+$('#I_PHOS').val()+"/<%=yyyy%>/<%=mm%>/<%=dd%>";
        //alert(mrd_path);
        //rdata [{imgUrl : "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=b330d8ba-f922-4214-bdcb-590bd60a701f"} , {imgUrl : "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=a3668881-bed8-48e9-b8cf-9f708f3dbe62"}]
    	
        //파일 명만 셋팅 , 확장자는 따로 셋팅
        var downFileName = rd_dat+"_"+rd_jno;
        
        var param="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path+"&rdServerSaveDir="+rdServerSaveDir+"&param="+rd_param+"&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);

		var f = document.reportForm;
		f.viewerUrl.value			=  viewerUrl;
		f.mrd_path.value			=  mrd_path;
		f.rdServerSaveDir.value		=  rdServerSaveDir;
		f.param.value				=  rd_param;
		f.systemDownDir.value		=  systemDownDir;
		f.downFileName.value		=  (downFileName);
		
        showReportViewDialog("/crownixViewer.do", w, h, printcallback);
//         showReportViewDialog("/crownixViewer.do?"+param, w, h, printcallback);
     }
	
	function printcallback(){
		//alert("print callback!");
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
			if(target.id == "I_PHOS"){
	            $('#I_FNM').val('');
	         }
		}
	}
	
	function report_language_val_setting(language){	
		$('#report_language').val(language);
		
		switch(language){
			case "kor":
				I_LOGMNM = "수진자별 결과관리(kor)";break;
			case "eng":
				I_LOGMNM = "수진자별 결과관리(eng)";break;
			default:
				I_LOGMNM = "수진자별 결과관리";
		}
		
		openPopup(9);
	}
	
	</script>
</head>
<body>
	<form method="post" action="" name="reportForm">
		<input type="hidden" name="viewerUrl" />
		<input type="hidden" name="mrd_path" />
		<input type="hidden" name="rdServerSaveDir" />
		<input type="hidden" name="param" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="downFileName" />
		<input type="hidden" name="imgObj" />
	</form>
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_ICNT" name="I_ICNT" type="hidden"> 				  	   <!-- 그리드에 보여줄 건수 -->
		<input id="I_CNT" name="I_CNT" value="40" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
		<input id="I_ROW" name="I_ROW" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		<input id="I_IROW" name="I_IROW" value="0" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		
		<input id="I_PECF" name="I_PECF" type="hidden"> 			   
		<input id="I_PNN" name="I_PNN" type="hidden">
		<!-- <input id="I_PHOS" name="I_PHOS" type="hidden"> -->
		
		<!-- Y = 수진자명，챠트번호　조건만 들어가는 기본 조건 -->
		<input id="I_PINQ" name="I_PINQ" type="hidden">
		
		<input id="I_DTLDAT" name="I_DTLDAT" type="hidden">
		<input id="I_DTLJNO" name="I_DTLJNO" type="hidden">
		
		<input id="I_GCD" name="I_GCD" type="hidden">
		<input id="I_ACD" name="I_ACD" type="hidden">
		<input id="I_CHN" name="I_CHN" type="hidden">
				
		<input id="I_GAC" name="I_GAC" type="hidden">
		<input id="I_GAC1" name="I_GAC1" type="hidden">
		<input id="I_GAC2" name="I_GAC2" type="hidden">
		<input id="I_DT" name="I_DT" type="hidden">
		<input id="I_BDT1" name="I_BDT1" type="hidden">
		<input id="I_BDT2" name="I_BDT2" type="hidden">
		
		<input id="I_RBN" name="I_RBN" type="hidden">
		
		<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  시작-->
		<!-- C:최초 조회, N:다음, P:이전 -->
		<input id="I_IGBN" name="I_IGBN" type="hidden">
		<!-- 이전/다음 시작 접수일자 : 최초 조회일 경우 0 -->
		<input id="I_IDAT" name="I_IDAT" type="hidden">
		<!-- 이전/다음 시작 접수번호 : 최초 조회일 경우 0 -->
		<input id="I_IJNO" name="I_IJNO" type="hidden">
		<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  끝-->

		<!-- 결과지출력시 국문/영문 구분자 -->
		<input id="report_language" name="report_language" type="hidden">
		
		<!-- 2021.12.16 추가 -->
		<!-- 오더의뢰지 버튼 누르면 현재 선택된 접수데이터의 병원코드가 넘어갈 수 있도록 변수가 필여함. -->
		<input id="order_img_hos" name="order_img_hos" type="hidden">
		
		<!-- 2022.01.03 - 로그인한 유저 권한 -->
		<input id="loginUser_authority" name="loginUser_authority" type="hidden" value="<%=ss_agr%>">

		<!-- 
		<input id="topFunc" type="button" onclick="javascript:moveFocus();" style="display:none;">
		 -->
		 
		<div class="content_inner" >
                    <div class="container-fluid">
                        <div class="con_wrap col-md-5 col-sm-12" id="rstListDiv">
                        	<!-- 검색영역 -->
                            <div class="container-fluid container-fluid-inner">
                                <div class="con_wrap col-md-12 srch_wrap">
                                    <div class="title_area open">
                                        <h3 class="tit_h3">검색영역</h3>
                                        <a href="#" class="btn_open">검색영역 접기</a>
                                    </div>
                                    <div class="con_section row">
				<!-- 						<p class="srch_txt text-center" style="display: none">조회하려면 검색영역을 열어주세요.</p> -->
										<div class="main_srch_box">
											<div class="col-md-12  m-t-10">
												<label for="" class="label_basic" style="float: left;">기간</label>
												<div  class="group1" style="width:400px;">
													<div class="select_area" id="I_DGN_DIV">
														<select id="I_DGN" name="I_DGN" class="form-control"></select>
													</div>
													<div class="btn_group" style="font-size: 14px" id="I_S_TERM"></div>
												</div>
											<!-- 	
												<div class="group2" style="width:240px;">
													<span class="period_area">
														<label for="I_FDT" class="blind">날짜 입력</label>
														<input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT" style="width:110px;">
													</span>
													 ~ 
													<span class="period_area">
														<label for="I_TDT" class="blind">날짜 입력</label>
														<input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT" style="width:110px;">
													</span>
												</div> 
											-->
											</div>
											<div class="col-md-12  m-t-10">
												<label for="" class="label_basic" style="float: left;">날짜 입력</label>											
												<div class="group1" style="width:300px;">
													<span class="period_area">
														<label for="I_FDT" class="blind">날짜 입력</label>
														<input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT" style="width:110px;">
													</span>
													 ~ 
													<span class="period_area">
														<label for="I_TDT" class="blind">날짜 입력</label>
														<input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT" style="width:110px;">
													</span>
												</div>
											</div>
											
											<div class="col-md-12  m-t-10">
												<div class="group5  m-t-10" >
													<label for="I_NAM" class="label_basic">수진자명</label>
													<input type="text" id="I_NAM" name="I_NAM" maxlength="14" class="srch_put"  onkeydown="javasscript:enterSearch(0)" style="-webkit-ime-mode:active;-moz-ime-mode:active;-ms-ime-mode:active;ime-mode:active;" >
												</div>
												<div class="group5  m-t-10" >
													<label for="I_PCHN" class="label_basic">차트번호</label>
													<input type="text" id="I_PCHN" name="I_PCHN" title="차트번호" maxlength="15" class="srch_put"  onkeydown="javasscript:enterSearch(0)" >
												</div>
											</div>
										
											<div class="col-md-12 hidden_srch   m-t-10"  id="DivHosSearch" style="display: none">
												<div class="group0  m-t-10" >
                                                    <label for="I_FNM" class="label_basic">병원</label>
                                                    <%-- 병원코드를 input box 로 받을 경우, 파견사원 병원계정으로 로그인 한 사용자가 타병원 결과까지 검색이 가능하기 때문에 popup 창이 뜨도록 변경함 --%>
                                                    <%-- <input id="I_PHOS" name="I_PHOS" type="text"  value="<%=ss_hos%>" maxlength="5" onkeydown="javasscript:enterSearch(0)" class="srch_put numberOnly srch_hos" style="width:100px;"/> --%>
                                                    <input id="I_PHOS" name="I_PHOS" type="text"  value="<%=ss_hos%>" readonly="readonly"  placeholder="" onClick="javascript:openPopup(3)" class="srch_put numberOnly srch_hos" style="width:100px;"/>
                                                    <input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly"  placeholder="" onClick="javascript:openPopup(3)" style="width:38%;">
												</div>
											</div>
											<div class="col-md-12 m-t-10 floatLeft" style="text-align: center; width: 100%;">
				<!-- 							<div style="float: left;width: 79px;"> -->
												<button type="button" class="btn_srch" name = "btn_srch" onClick="javascript:search()">조회</button>
												<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init2()">검색 초기화</button>
											</div>
										</div>
										<div class="srch_line"></div>
										<div class="srch_box floatLeft">
                                             <div class="srch_box_inner m-t-10" >
                                                <div class="srch_item_v5">  
                                                    <label for="I_BIRTH" class="label_basic">생년월일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                                    <input type="text" class="srch_put" id="I_BIRTH" name="I_BIRTH" placeholder="" maxlength="6" style="width: 55%;" onkeydown="javascript:enterSearch(0)" pattern="[0-9]+">
                                                </div>
                                            </div>
                                            
                                            <div class="srch_box_inner m-t-10" >
                                            	<div class="srch_item_v5">  
                                                    <label for="I_PETC" class="label_basic">검체번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                                    <input type="text" class="srch_put" id="I_PETC" name="I_PETC" placeholder="" maxlength="50" style="width: 55%;" onkeydown="javascript:enterSearch(0)">
                                                </div>
                                                <div class="srch_item_v4">    
                                                    <label for="I_STS" class="label_basic">검사진행&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                                    <div class="select_area">
                                                        <select id="I_STS" name="I_STS">
                                                        </select>
                                                    </div>
                                                </div>             
                                            </div>
                                            
                                            <div class="srch_box_inner m-t-10">
                                                <div class="srch_item_v5">
                                                	<div class="select_area">
                                                       <select id="I_PJKNF" name="I_PJKNF">
				                                       </select>
                                                    </div>
                                                    <input type="text" class="srch_put" id="I_PJKN" name="I_PJKN" placeholder="" maxlength="9" onkeydown="javascript:enterSearch(0)" style="width:55%;">
                                                </div>
                                                 <div class="srch_item_v4">
                                                 	<label for="I_HAK" class="label_basic">검사분야&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                                    <div class="select_area">
                                                        <select id="I_HAK" name="I_HAK">
                                                        </select>
                                                    </div>
                                                 </div>
                                            </div> 
                                            <div class="srch_box_inner">
                                                <div class="srch_item_v5 m-t-10">
                                                	<ul class="chk_lst2 chk_lst_v5" id="RGN"></ul>
                                                	<!-- 
                                                	<ul class="chk_lst chk_lst_v2">                                          
                                                        <li><input type="checkbox" value="Y" id="I_RGN1" name="I_RGN1"> <label for="I_RGN1">일반</label></li>  
                                                        <li><input type="checkbox" value="Y" id="I_RGN2" name="I_RGN2"> <label for="I_RGN2">조직</label></li>
                                                        <li><input type="checkbox" value="Y" id="I_RGN3" name="I_RGN3"> <label for="I_RGN3">세포</label></li>
                                                    </ul>
                                                     -->
                                                </div>
                                                <div class="srch_item_v4">
	                                                <label for="I_CORONA" class="label_basic">COVID19 결과&nbsp;&nbsp;&nbsp;</label>
	                                                <div class="select_area">
	                                                    <select id="I_CORONA" name="I_CORONA">
	                                                    </select>
	                                                </div>
	                                            </div>
                                            </div>
                                       </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 검색영역 end -->
                        	
                            <div class="title_area">
                                <h3 class="tit_h3">수진자 목록</h3>
                                <div class="tit_btn_area">
                                <!-- 
									<button type="button" id="stsPop" class="btn btn-default btn-sm" data-toggle="modal" data-target="#sts-Modal" style="display:none;">STS</button>
									 -->
									 <!--                                 
									 <button type="button" id="stsPop" class="btn btn-default btn-sm" data-toggle="modal" data-target="#sts-Modal">STS</button>
									  -->
                                	<button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(4)" id="smsDisplay" style="display:none">SMS</button>                            
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
                         		</div>
                            </div>
                        </div>
                        
                        <div class="con_wrap col-md-7 col-sm-12" style="overflow:auto" id="dtlDiv">
                            <div class="title_area">
                                <h3 class="tit_h3" id="titTest">검사 결과</h3>
                                <div class="tit_btn_area">
                                	<button type="button" id="resultImg_eng" name="resultImg_eng" class="btn btn-default btn-sm" data-toggle="modal" data-target="#result-Modal" onClick="javascript:report_language_val_setting('eng')">결과지출력(영문)</button>
                                	<button type="button" id="resultImg_kor" name="resultImg_kor" class="btn btn-default btn-sm" data-toggle="modal" data-target="#result-Modal" onClick="javascript:report_language_val_setting('kor')">결과지출력(국문)</button>
                                	<button type="button" id="resultImg" name="resultImg" class="btn btn-default btn-sm" data-toggle="modal" data-target="#result-Modal" onClick="javascript:report_language_val_setting('kor')">결과지출력</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:rstDisplayPrint()" id="rstDisplayPrintButton">화면 출력</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(1)" id="addTestButton">추가 검사</button>
                                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(2)">오더 의뢰지</button>
                                </div>
                            </div>
	                        <div class="con_section overflow-scr" style="overflow:auto">
		                        <div class="con_wrap col-md-12 col-sm-12" style="overflow:auto" id="rstDiv">
	                            	<div class="tbl_type" style="overflow:auto" id="infoDiv"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_l-type">
	                                    	<colgroup>
	                                        	<col width="100px">
	                                            <col width="100px">
	                                            <col width="100px">
	                                            <col width="100px">
	                                            <col width="100px">
	                                            <col width="100px">
	                                        </colgroup>
	                                        <tbody>
	                                        	<tr>
	                                            	<th scope="row">수진자</th>
	                                                <td><label class="label_basic" id="NAM"></label></td>
	                                                <th scope="row">차트번호</th>
	                                                <td><label class="label_basic" id="CHN"></label></td>
	                                                <th scope="row">접수일자</th>
	                                                <td><label class="label_basic" id="DAT"></label></td>
	                                            </tr>
	                                            <tr>
	                                            	<th scope="row">주민등록번호</th>
	                                                <td><label class="label_basic" id="JN"></label></td>
	                                                <th scope="row">담당의/과/병동</th>
	                                                <td><label class="label_basic" id="DPTN"></label></td>
	                                                <th scope="row">성별</th>
	                                                <td><label class="label_basic" id="SEX"></label></td>
	                                            </tr>
	                                            <tr>
	                                            	<th scope="row" id="REALAGE">나이</th>
	                                                <td><label class="label_basic" id="AGE"></label></td>
	                                                <th scope="row">검체종류</th>
	                                                <td><label class="label_basic" id="ETCINF"></label></td>
	                                                <th scope="row">검체번호</th>
	                                                <td><label class="label_basic" id="ETC"></label></td>
	                                            </tr>
	                                            <tr>
	                                            	<th scope="row">의료기관</th>
	                                                <td><label class="label_basic" id="HOSN"></label></td>
	                                                <th scope="row">병원코드</th>
	                                                <td><label class="label_basic" id="HOS"></label></td>
	                                                <th scope="row">접수번호</th>
	                                                <td><label class="label_basic" id="JNO"></label></td>
	                                            </tr>
	                                            <tr>
	                                            	<th scope="row">실거주지</th>
	                                                <td colspan="5">
	                                                	<label class="label_basic" id="ADDRESS"></label>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<!-- 
	                                	<div id="realgrid2"  class="realgridH"></div>
	                                	 -->	
	                                	 <div id="realgrid2"  style="height:300px"></div>
			                        </div>
			                        
	                                <div style="height:20px"></div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivMastResult"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <th scope="col">Result</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="MastResult"></div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivMastResult_Reference"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                		<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                		<thead>
	                                        	<tr>
	                                                <th scope="col">Specific IgE Reference Range</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody id="mastrefer">
	                                       		<tr>
	                                       			<td>
	                                       				<table>
				                                       		<tr>
																<td style="border-right: solid 1px; border-bottom: solid 1px;" align="center">IU/mL</td>
																<td style="border-right: solid 1px; border-bottom: solid 1px;" align="center">Class</td>
																<td style="border-bottom: solid 1px;"align="center">Description</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left"><=0.34</td>
																<td style="border-right: solid 1px;" align="center">0</td>
																<td align="left">Not found</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left">0.35~0.69</td>
																<td style="border-right: solid 1px;" align="center">1</td>
																<td align="left">Weak</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left">0.70~3.49</td>
																<td style="border-right: solid 1px;" align="center">2</td>
																<td align="left">Description</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left">3.35~17.49</td>
																<td style="border-right: solid 1px;" align="center">3</td>
																<td align="left">Moderate</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left">17.50~49.99</td>
																<td style="border-right: solid 1px;" align="center">4</td>
																<td align="left">Strong</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left">50.00~99.99</td>
																<td style="border-right: solid 1px;" align="center">5</td>
																<td align="left">Very strong</td>
															</tr>
															<tr>
																<td style="border-right: solid 1px;" align="left">>=100</td>
																<td style="border-right: solid 1px;" align="center">6</td>
																<td align="left">Extremely strong</td>
															</tr>
														</table>
	                                       			</td>
	                                       		</tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivRia"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <th scope="col">Prenatal Screening Test</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td>
	                                                	<div id="Ria"></div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div> 
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivHematoma"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        		<!--
	                                        			<th scope="col">Remark(혈종)</th> 
	                                        		 -->
	                                                <th scope="col">혈종검사소견</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="Hematoma"></div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivObgy"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        		<!-- 
	                                        			<th scope="col">Remark(산전)</th>
	                                        		 -->
	                                                <th scope="col">산전검사소견</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="Obgy"></div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivHasimg"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        		<!-- 
	                                        			<th scope="col">Image results (img)</th>
	                                        		 -->
	                                                <th scope="col">Image results</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody id="hasimg">
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivPopphoto"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        		<!--
	                                        			<th scope="col">Image results (photo)</th> 
	                                        		 -->
	                                                <th scope="col">Image results</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody id="Popphoto">
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivMicrobio"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed">
	                                    	<colgroup>
	                                            <col width="20%">
	                                            <col width="30%">
	                                            <col width="20%">
	                                            <col width="30%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <th scope="col" colspan="4">Microbio Result</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody id="Microbio">
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto;" id="DivLmbYN"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <!--
	                                                	<th scope="col">면역병리 진단보고 (LmbYN)</th>
	                                                	<th scope="col">면역병리 진단보고</th> 
	                                                 -->
	                                                 <th scope="col" id="DivLmbYNTitle">면역병리 진단보고</th>
	                                                
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="LmbYN"  style="font-family:굴림체;">
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto;" id="DivPathologyType"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        		<!--
	                                        			<th scope="col">면역병리 진단보고 (PathologyType)</th>
	                                        			<th scope="col">면역병리 진단보고</th> 
	                                        		 -->
	                                        		 <th scope="col" id="DivPathologyTypeTitle">면역병리 진단보고</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="PathologyType" style="font-family:굴림체;">
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto;" id="DivPathologyType2"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        		<th scope="col" id="DivPathologyType2Title">면역병리 진단보고</th> 
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="PathologyType2" style="font-family:굴림체;">
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivCytologyType"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        	<!--
	                                        		<th scope="col">세포병리 진단보고 (CytologyType)</th>
	                                        		<th scope="col">세포병리 진단보고</th> 
	                                        	 -->
	                                        	 <th scope="col">세포병리 진단보고</th>
	                                        	 
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="CytologyType">
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivCytologyType2"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        	<!--
	                                        		<th scope="col">세포병리 진단보고 (CytologyType2)</th>
	                                        		<th scope="col">세포병리 진단보고</th> 
	                                        	 -->
	                                        	 	<th scope="col">세포병리 진단보고</th>
	                                                
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="CytologyType2">
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <div class="tbl_type" style="overflow:auto" id="DivCytologyType3"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                        	<!--
	                                        		<th scope="col">세포병리 진단보고 (CytologyType3)</th>
	                                        		<th scope="col">세포병리 진단보고</th> 
	                                        	 -->
	                                        	 	<th scope="col">세포병리 진단보고</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="CytologyType3">
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                <!-- 사용하지 않음 데이터도 없음 (최재원 대리님 요청확인) 운영에서 데이터 확은으로인해 노출되도록 변경 20190225 (최재원 대리님 요청)--> 
	                                <div class="tbl_type" style="overflow:auto" id="DivCytogene"> 
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <th scope="col">Cytogenetics Result</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="Cytogene"></div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                 
	                                <div class="tbl_type" style="overflow:auto" id="DivRemarktext"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <!-- 
	                                                <th scope="col">Remark isRemarktext</th>
	                                                 -->
	                                                <th scope="col">Remark</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="Remarktext" style="font-family:굴림체;">
	                                                	</div>
	                                                	<!-- 
	                                                	<label class="label_basic" id="RemarkPositive"></label>
	                                                	<label id="Remarktext"></label>
	                                                	 -->
	                                                	
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                
	                                <!-- Corona CT : Start -->
	                                <div class="tbl_type" style="overflow:auto" id="DivCoronaCT"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <th scope="col">Corona CT</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="DivCoronaCT_val" style="font-family:굴림체;"> <!-- Corona CT 값 화면에 출력할 변수 -->
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                <!-- Corona CT : End -->
	                                
	                                <!-- 세포유전 (2022.08.29): Start -->
	                                <div class="tbl_type" style="overflow:auto" id="DivCell"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<table class="table table-bordered table-condensed tbl_c-type">
	                                    	<colgroup>
	                                            <col width="100%">
	                                        </colgroup>
	                                        <thead>
	                                        	<tr>
	                                                <th scope="col">세포유전</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        	<tr>
	                                                <td style="text-align:left;">
	                                                	<div id="DivCell_val" style="font-family:굴림체;"> <!-- Corona CT 값 화면에 출력할 변수 -->
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                <!-- 세포유전 (2022.08.29): End -->
	                                  
	                            </div>
                            </div>
                        </div>
                        
                    </div> 
                                                       
                </div>    
                <!-- content_inner -->       	
     </form>
     
<!-- sts-Modal 팝업 -->
<div class="modal fade" id="sts-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="" aria-hidden="true" style="height:10px;" >
<!-- 
    <div class="modal-dialog" id="sts-Modal1" style="position:absolute; left:0px;margin: 0px;height: 10px;width: 450px;">
     -->
     <div class="modal-dialog" id="sts-Modal1" style="position:absolute; left:0px;margin: 0px;height: 10px;width: 350px;">
        <div class="modal-content" style="position:absolute; left:0px;padding-bottom:20px;" id="sts-Modal2">
            <div class="modal-header" id="sts-Modal3">
                <button type="button" id="modal-close" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel" id="sts-Modal4">검사 의뢰 목록</h4>
            </div>
            <div class="modal-body" id="sts-Modal5">
            <!-- 
            	<div class="tbl_type" id="sts-Modal6" style="height:300px;overflow:auto;width: 470px;">
            	 -->
            	 <div class="tbl_type" id="sts-Modal6" style="max-height:300px;overflow:auto;width: 320px;">
					<table class="table table-bordered table-condensed tbl_c-type" id="sts-Modal7">
                    	<colgroup>
                        	<col width="200">
                            <col width="100">
                            <!-- 
                            <col width="100">
                            <col width="70">
                             -->
                        </colgroup>
                        <thead>
                        	<tr>
                        		<th scope="col" id="sts-Modal10">검사항목</th>
                                <th scope="col" id="sts-Modal11">보고예정일자</th>
                                <!-- 
                            	<th scope="col" id="sts-Modal8">보험코드</th>
                                <th scope="col" id="sts-Modal9">검사코드</th>
                                 -->
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




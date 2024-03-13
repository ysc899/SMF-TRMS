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
	<link rel="stylesheet" href="/css/rstUserTable.css">
	
	<title>결과관리::수진자별 결과관리</title>
	
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

		.select_area_t .jcf-select{
			padding : 0 20px 0 5px;
			text-indent : 0px;
		}
		
		tbody[id="rstUserBody"] tr:hover, tbody[id="rstResultBody"] tr:hover{
			background-color: rgba(100, 170, 255, 0.2);
		}
		
		.table-bordered > tbody > tr > th, .table-bordered > tbody > tr > td {
			border-top: 0px;
		}

		body {
			background-color: #cdcdcd;
		}
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
	var rsTableList = "";
	var dataProvider1,dataProvider2;
    var gridView1,gridView2;
		
	var pmcd = "";
	var I_LOGMNU = "RSTUSERTABLE";
	var I_LOGMNM = "수진자별 결과관리(Table)";
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
	
	
	//LmbYN
	var LmbYN_str = "N";
	var LmbYN_html = "";
	var Mw106rm3 = "N"; //이전
	var Mw106rms6 = "N"; //이후
	
	//pathologyType
	var pathologyType_str = "N";
	var pathologyType_html = "";
	//pathologyType2
	var pathologyType2_str = "N";
	var pathologyType2_html = "";
	
	var pathTitleYn = "N";
	
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
   	var open_click = true;
	/* var mql = window.matchMedia("screen and (max-width: 1000px)"); */
   	//session
   	
	$(document).ready(function(){
		//다음/이전버튼 비활성화
		$('#I_PNN_P').addClass("disabled");
        $('#I_PNN_N').addClass("disabled");
        search();
	});	
	
	var loding = false;
	function dataResult(data){
		//상세 초기화
		init();
		$('#rstUserBody').empty();
		
		var formData = $("#searchForm").serialize();
		
		if(isNull(data)){
			data = "&I_DAT="+"";
			data += "&I_JNO="+"";
		}
		//console.log(1);
		var I_TDT = "";
		var I_FDT = "";
		if(!isNull($('#I_DGN').val())){
			I_TDT = $('#I_DGN').val();
			I_TDT = I_TDT.substring(0,4)+"-"+I_TDT.substring(4,6)+"-"+I_TDT.substring(6,8);
			//console.log(I_TDT);
			var I_DT = new Date(I_TDT);
			I_DT.setDate(I_DT.getDate() - 90);
			I_FDT = parseDate(I_DT.yyyymmdd());
		}
		data += "&I_FDT=" + I_FDT;
		data += "&I_TDT=" + I_TDT;
		//console.log(I_FDT);
		//console.log(I_TDT);
		//console.log(2);
		formData += data;
		//callLoading(null, "on");
		//console.log(formData);
		ajaxCall("/rstUserListMini.do", formData, false);
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
			$('#I_IDAT').val(firstData.date);
			//이전 페이지 시작 접수번호
			$('#I_IJNO').val(firstData.jno);
		}
		if(pnnId == "N"){ 			// 다음 결과 페이지
			page_count++;
			row = row + 40;
			$('#I_ROW').val(row);
			
			// 20190507 추가
			//다음 페이지 시작 접수일자
			$('#I_IDAT').val(endData.date);
			//다음 페이지 시작 접수번호
			$('#I_IJNO').val(endData.jno);
		}
		
		if(page_count == 0){
			$('#I_PNN_N').removeClass("disabled");
			$('#I_PNN_P').addClass("disabled");
		}else{
			$('#I_PNN_P').removeClass("disabled");
		}
		
		// rsUserTable 검색
		dataResult();
		
	}
	
	function init(){
		
	}
	//rtrim
	function replaceAll(str){
		str.replace(/^\\s+/gi,"");
	}
	
	var firstData = {};
	var endData = {};
	var dataList = "";
	function rutCreate(resultList){			//환자 정보 table body 생성
		//console.log(resultList);
		firstData = {};
		endData = {};
		var rstUserT;
		//$('#rstUserBody').empty();			//환자 정보 table body 초기화
		//dataResult2(grid.getValues(index.itemIndex));
		
		dataList = resultList;
		var rstListLen = $('#I_CNT').val();
		if($('#I_CNT').val() > resultList.length){
			rstListLen = resultList.length;
		}
		
		for(i=0; i<rstListLen; i++ ){
			rstUserT = "";
			rstUserT += '	<tr onclick="javascript:resultClick(\''+resultList[i].DAT+'\', \''+ resultList[i].JNO +'\', \''+i+'\', this)">';
			rstUserT += '       <td class="tool">'+ resultList[i].DAT2  + '</td>';
			rstUserT += '       <td class="tool">' + resultList[i].CHN + '</td>';
			rstUserT += '       <td class="tool" style="text-align:left;">' + resultList[i].NAM + '</td>';
			rstUserT += '       <td class="tool" style="text-align:center;">' + resultList[i].STSD + '</td>';
			rstUserT += '	</tr>';
			
			$('#rstUserBody').append(rstUserT);
			if(i==0){
				firstData.date = resultList[i].DAT;
				firstData.jno = resultList[i].JNO;
			}else if(i==39){
				endData.date = resultList[i].DAT;
				endData.jno = resultList[i].JNO;				
			}
		}
		//console.log(dataList);
		$('.tool').hover(function(){
			$(this).attr('title', $(this).html());	
		})
		
	}
	
	// 검사결과 보기 팝업으로 수정 필요
	function resultClick(DAT, JNO, i, obj){		//수진자 목록 >>> 검사결과 보기
		
		var table = document.getElementById("rstUserTable");
		var tr = table.getElementsByTagName("tr");
		$('#rstUserBody tr').css('background-color', '');
		// 수진자 목록 선택된 행 배경색 변경
		obj.style.backgroundColor = "rgba(255, 161, 32, 0.196)"; 
		
		TabResize();						// table size조절
		//$('#rstResultBody').empty();		// 검사결과 Table Body 초기화
		if(loding){
			callLoading(null, "off");
		}
		setTimeout(function() {
 			dataResult2(DAT, JNO);	
		}, 200);
		
	}
	
	var rstformData = "";
	function dataResult2(SDAT, SJNO){
		
		$('#I_DTLDAT').val(SDAT);
		$('#I_DTLJNO').val(SJNO);
		
		rstformData = document.searchForm;

		window.open("", "rstPopup", "width=660px, height=650px, scrollbars=no, resizable=no");

		rstformData.target = "rstPopup";
		rstformData.action = "/rstUserTableMini02.do";
		rstformData.method = "post";
		rstformData.submit();

	}
	
	
	function onResult(strUrl,response){
		//console.log('onResult1');
		if(strUrl == "/rstUserListMini.do"){
			//상세 초기화
			init();
			loding = false;
			//console.log('onResult2');
			var resultList =  response.resultList;
			
 			for (var i in resultList){
				if(resultList[i].PRF == "N"){
					resultList[i].PRF = "";
				}
				if(resultList[i].DAT != 0){
					resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
				}
				
			}
 			
			rutCreate(resultList);
			
			if(resultList.length < $('#I_ICNT').val()){					// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
				$('#I_PNN_N').addClass("disabled");
			}else{
				$('#I_PNN_N').removeClass("disabled");
			}
			
			if($('#I_PNN').val() == "C"){											// 첫페이지일 경우 이전 페이지 버튼 숨김
				
				$('#I_PNN_P').addClass("disabled");
			}
			
		}
	}
	
	//조회
	function search(){
		//상세 초기화
		//init();
		callLoading(null, "off");
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_PHOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_PHOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		
		//init();
		// cancel
		// clear table 2개
		if(!searchValidation()) {return;}

		var cnt = Number($('#I_CNT').val());
		$('#I_ICNT').val(Number($('#I_CNT').val())+1);
		$('#I_ROW').val(Number($('#I_IROW').val()));
		$('#I_PNN').val("C");
		
		$("#I_IGBN").val("C");
		$("#I_IDAT").val("0");
		$("#I_IJNO").val("0");
		//console.log("search");
		dataResult();
		
	}
	function searchValidation(){
		
		var I_DPN = $('#I_DPN option:selected').val()
		//console.log('vali');
		if(I_DPN == "I_DGN"){
			//console.log(1);
			// 데이터가 날짜 형식 아닐 경우
			var I_DT = $('#I_DGN').val();
			if((I_DT.length < 8 && I_DT.length > 0) && !isNull(I_DT)){
				CallMessage("229", "alert","", dataClean);
				return false;
			}
			
		}else if(I_DPN == "I_PCHN"){
			//console.log(2);
			if(strByteLength($("#I_PCHN").val()) > 15){
				CallMessage("292", "alert", ["차트번호는 15 Byte"], dataClean);
				return false;
			}
		}
		
		return true;
	}
	
	// 메시지 알람창
	function parentMag(type,yesFn,strMessage){
		
		if(type=="confirm"){
	        $.message.confirm({
				title: 'Confirm',
	            contents : strMessage,
	            onConfirm: function () {
	        		setTimeout(function() {
	        			yesFn();
	        		}, 400);
	            },
	            onCancel: function () {
					rtnBool = false;
	            }
	        });
		}else{
			$.message.alert({
	            title : 'Alert',
	            contents : strMessage,
	            onConfirm: function () {
	        		if(typeof yesFn == "function"){
		        		setTimeout(function() {
		        			yesFn();
		        		}, 400);
	            	}
	            }
	        });
		} 
		
	}
	
	// 검색 실패시 데이터 수진자 목록 초기화
	function dataClean(){
		// 수진자 목록 table 초기화
		$('#rstUserBody').empty();
	}
	
	/* 병원검색 */
	function openPopup(str){
		if(str == "3"){
			fnOpenPopup("/hospSearchS.do","병원조회",formData,callFunPopup);
		}
	}
	
	function  callFunPopup(url,iframe,formData){
		
		if(url == "/hospSearchS.do"){
			formData = {};
			formData["I_HOS"] =  $('#I_PHOS').val();
			formData["I_FNM"] =  $('#I_FNM').val();
			iframe.gridViewRead(formData);
		}
		
	}
	
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
	/* 병원검색 끝 */
	function searchEnter(enter){
		if(event.keyCode == 13){
			if(enter == "0"){
				search();
			}
		}
	}
	
	function selectChange(){
		
		var data = $('#I_DPN option:selected').val()
		//console.log(data);
		
		var maxlength = 8;
		
		if(data == "I_PCHN"){
			maxlength = 15;
		}else if(data == "I_NAM"){
			maxlength = 14;
		}else if(data == "I_DGN"){
			$('.I_DPN').addClass('numberOnly');
		}
			
		$('.I_DPN').attr({
			'id' : data,
			'name' : data,
			'maxlength' : maxlength
		});
		
	}	
	
/* 	$(window).load(function(){
		
		var strW = $('.content_inner').outerWidth() + (window.outerWidth - window.innerWidth);
		var strH = $('.content_inner').outerHeight() + (window.outerHeight - window.outerHeight);
		
		window.resizeTo(strW, strH);
	});
 */	
	</script>
</head>
<body style=" background: url('../images/ticker/ticker_bg.jpg') no-repeat; width: 375px" onresize="parent.resizeTo(322,658)">
	<form method="post" action="" name="reportForm">
		<input type="hidden" name="viewerUrl" />
		<input type="hidden" name="mrd_path" />
		<input type="hidden" name="rdServerSaveDir" />
		<input type="hidden" name="param" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="downFileName" />
		<input type="hidden" name="imgObj" />
	</form>
	<form id="searchForm" name="searchForm" onsubmit="return false">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_ICNT" name="I_ICNT" type="hidden"> 				  	   <!-- 그리드에 보여줄 건수 -->
		<input id="I_CNT" name="I_CNT" value="40" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
		<input id="I_ROW" name="I_ROW" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		<input id="I_IROW" name="I_IROW" value="0" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		
		<input id="I_PECF" name="I_PECF" type="hidden"> 			   
		<input id="I_PNN" name="I_PNN" type="hidden">
		<input id="I_PHOS" name="I_PHOS" type="hidden" value="<%=ss_hos %>">
		
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
		
		<input id="I_HAK" name="I_HAK" type="hidden" value="">
		<input id="I_STS" name="I_STS" type="hidden" value="">
		<input id="I_PETC" name="I_PETC" type="hidden" value="">
		<input id="I_PJKNF" name="I_PJKNF" type="hidden" value="">
		<input id="I_PJKN" name="I_PJKN" type="hidden" value="">
		
		<!-- 코로나 검색조건 추가로 인해 추가된 파라미터 -->
		<input id="I_CORONA" name="I_CORONA" type="hidden" value="">
		
		<!-- 생년월일 검색조건 추가로 인해 추가된 파라미터 -->
		<input id="I_BIRTH" name="I_BIRTH" type="hidden" value="">
		
		<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  시작-->
		<!-- C:최초 조회, N:다음, P:이전 -->
		<input id="I_IGBN" name="I_IGBN" type="hidden">
		<!-- 이전/다음 시작 접수일자 : 최초 조회일 경우 0 -->
		<input id="I_IDAT" name="I_IDAT" type="hidden">
		<!-- 이전/다음 시작 접수번호 : 최초 조회일 경우 0 -->
		<input id="I_IJNO" name="I_IJNO" type="hidden">
		<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  끝-->
		
		<div class="content_inner">
			<div class="container-fluid" ><!-- style="min-width: 1024px;" -->
				<!-- 검색영역 -->
				<div class="container-fluid container-fluid-inner" style="margin-top: 20px; margin-left: 8px;">
                  <div style="margin-left: 150px;color: #FFE400;font-weight: bold;">
                     <span><%=ss_nam %></span>
                  </div>
						<div class="fleft">
						<div class="select_area_t" id="I_DGN_DIV">
							<!-- <select id="I_DPN" name="I_DPN" class="form-control"></select> -->
							<select id="I_DPN" name="I_DPN" class="form-control" onchange="javascript:selectChange()">
								<option value="I_DGN" selected="selected">접수일자</option>
								<option value="I_PCHN">차트번호</option>
								<option value="I_NAM">수진자명</option>
							</select>
						</div>
						<input type="text" id="I_DGN" name="I_DGN" maxlength="8" class="srch_put I_DPN"  onkeydown="javascript:searchEnter(0)" style="width: 210px;" >
						<button type="button" class="btn_srch_t" name = "btn_srch" onClick="javascript:search()" style="height:30px;padding:0 15px;background-color:#ffa200; border-radius:5px;line-height:29px;color:#000;">조회</button>
						</div>
						<!-- <div class="fleft"  style="width : 135px;">
							<button type="button" class="btn_srch_t" name = "btn_srch" onClick="javascript:search()" style="height:30px;padding:0 15px;background-color:#c7171d; border-radius:5px;line-height:29px;color:#fff;">조회</button>
						</div> -->
					</div>
	                            <!-- 검색영역 end -->
					<div class="con_wrap_t col-md-5-t" style="width: 356px; padding-left: 6px; padding-right: 0px"><!--  style="min-width: 400px;" -->
	                    <div class="overflow-scr" style="margin-top:37px; position:relative;min-height:50px;padding: 0px 0px 10px 0px;">
	                    	<div class="tbl_type" style="width:100%; overflow:auto; margin-top:5px;" > <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                            <div class="" style="overflow-y: auto; overflow-x:hidden; cursor:default; height: 422px">
	                                <table class="table table-bordered table-condensed tbl_c-type" width="100%" id="rstUserTable">
	                                   	<colgroup>
	                                	    <col width="25%">
	                                    	<col width="25%">
	                                        <col width="25%">
	                                        <col width="25%">
	                                    </colgroup>
	                                    <thead >
<!-- 	                                       	<tr style="font-weight: bold;">
	                                       		<td>접수일자</td>
	                                       		<td>차트번호</td>
	                                       		<td>수진자명</td>
											</tr> -->
										</thead>
	                                    <tbody id="rstUserBody" name="rstUserBody" >
	                                    </tbody>
	                                </table>
								</div>
								<div style="text-align: center; width: 100%; line-height: 30px;">
									<button type="button" class="btn btn-default btn-sm_t" id="I_PNN_P" onClick="javascript:paging('P')" >이전</button>
	                                <button type="button" class="btn btn-default btn-sm_t" id="I_PNN_N" onclick="javascript:paging('N')" >다음</button>                            
	                            </div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</form>
</body>
</html>




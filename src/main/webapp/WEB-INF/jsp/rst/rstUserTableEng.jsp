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
	<link rel="stylesheet" href="/css/rstUserTableEng.css">
	
	<title>결과관리::Checking Result</title>
	
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
	var I_LOGMNU = "RSTUSERTABLEENG";
	var I_LOGMNM = "Checking Result";
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
    
    var coronaCtData = "";		// 코로나 CT 결과 존재여부 flag
    var corona_ct_str = "N";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
    
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
   	//console.log("### s_paguen_hos_yn :: "+s_paguen_hos_yn);
   	
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
   	
  	
    $(document).ready( function(){
    	
    	var mql = window.matchMedia("screen and (max-width: 1154px)");
		// 화면 가로 사이즈가 1224이하일경우 메뉴 최소화
		if(mql.matches){
			if(parent.$('.side_wrap').hasClass('on')){
				$('.label_margin').css('margin-right', '5px');
				$('#searchDT').css('display', 'flex');
				$('#searchDT').css('float', 'none');
			}
		}
		// 화면 가로 사이즈 조절시
		$(window).resize(function(e){
		//mql.addListener(function(e){
 			var winSize = 1224;
 			// 메뉴탭 최소화, 최대화에 따른 가로사이즈 축소
			if(parent.$('.side_wrap').hasClass('on')){
				winSize -= 70;
			}else{
				winSize -= 200;
			} 
			// 메뉴탭 제외하고 window 가로 사이즈
			e = window.matchMedia("screen and (max-width: "+ winSize +"px)");
			if(e.matches){		// 화면 가로 사이즈가 1224이하일경우 최소화
				$('.label_margin').css('margin-right', '5px');
				$('#searchDT').css('display', 'flex');
				$('#searchDT').css('float', 'none');
			}else{				// 화면 가로 사이즈가 1224이상일 경우 복구
				$('.label_margin').css('margin-right', '');
				$('#searchDT').css('display', '');
				$('#searchDT').css('float', '');
			}
		});
    	
    	// 검색영역
    	$('.btn_open_t').on('click', function(){
    		if(open_click)
    		{
    			
    			var srch_wrap = $(this).closest('.srch_wrap');
    			var title_area = $(this).closest('.title_area_t');
    			
    			//btn_open
    			open_click = false;
    			if ($(this).hasClass('on')){
    				$(this).removeClass('on');
    				$(this).html('Search area close');
    			}
    			else {
    				$(this).addClass('on');
    				if(srch_wrap.find('.srch_txt').length > 0){
    					$(this).html('Search area open');
    				}else{
    					$(this).html('Search area more');
    				}
    			}
    		
    			$(this).removeAttr('href');

    			if (title_area.hasClass('open')) {
    				title_area.removeClass('open');
    				$('.srch_line').hide();
    				if(srch_wrap.find('.srch_txt').length > 0){
    					srch_wrap.find('.srch_box').fadeOut(function(){
    						srch_wrap.find('.srch_txt').fadeIn(function(){
    							 open_click = true;
    						});
    						TabResize();
    					});
    				}else{
    					srch_wrap.find('.srch_box').fadeOut(function(){
    						open_click = true;
    						TabResize();
    					});
    				}
    			}
    			else {
    				title_area.addClass('open');
    				 $('.srch_line').show();
    				if(srch_wrap.find('.srch_txt').length > 0){
    					srch_wrap.find('.srch_txt').fadeOut(function(){
    						srch_wrap.find('.srch_box').fadeIn(function(){
    							 open_click = true;
    						});
    						TabResize();
    					});
    				}else{
    					srch_wrap.find('.srch_box').fadeIn(function(){
    						 open_click = true;
    					});
    					TabResize();
    				}
    			}
    		}
    	});
    	$('.btn_open_t').click();
    	// 검색영역 끝
    	
    	// 검사 결과 전체 선택, 해제
    	$('#resCheck').click(function(){
    		if($('#resCheck').prop("checked")){
    			$('input[id=rstCheck]').prop('checked', true);
    		}else{
    			$('input[id=rstCheck]').prop('checked', false);
    		}
    	});
    	
    	$(".onlyHangul").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[a-z0-9]/gi,''));
	        }
	    });
    	
    	// 화면출력
    	// 20190320 화면출력 사용안함(김윤영 과장님 요청사항(당일 회의 결과))
    	// 20200302 요청한 병원에 한해서만 기능 오픈하기로 변경
    	if(s_moniter_view_print == "Y" || s_uid == "L17045"
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
		
		// 병원 사용자, 특정 병원만 네오티커 대행 팝업 사용
		if(s_ecf == "C"){
			// 병원코드 조건문 추가시 특정 병원만 확인 가능				
			if(s_hos == "15710"){
				$('#miniWin').show();
			}
		} 
		
		
		//sms 버튼 활성화 여부
		if(s_syn == "Y"){
			//$('#smsDisplay').show();	
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
		
	    $(".fr_date").datepicker("setDate", new Date());
		var yearAgo = new Date();
        yearAgo.setDate(yearAgo.getDate() - 1);
        $("#I_FDT").datepicker('setDate', yearAgo);
		
	    //기간구분
	    var resultCode = "";
		//resultCode = getCodeEng(I_LOGMNU, I_LOGMNM, "TERM_DIV", 'N');
		resultCode = getCodeEng(I_LOGMNU, I_LOGMNM, "TERM_DIV_ENG", 'N');
		$('#I_DGN').html(resultCode);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_DGN"));
		
		//$('#I_DGN_DIV').css('min-width','80px');
		//$('#I_DGN_DIV').css('width','80px');
		//$('.jcf-select jcf-unselectable jcf-select-form-control').css('padding','0 10 0 0');
		//jcf.replace($("#I_DGN_DIV"));
		
		//검사분야
		var resultCode1 = "";
		resultCode1 = getCodeEng(I_LOGMNU, I_LOGMNM, "TEST_CLS_ENG", 'Y');
		$('#I_HAK').html(resultCode1);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_HAK"));
		
		//검사진행상황
		var resultCode2 = "";
		resultCode2 = getCodeEng(I_LOGMNU, I_LOGMNM, "TEST_STAT_ENG", 'Y');
		$('#I_STS').html(resultCode2);
		//콤보박스 초기에 보이기
		jcf.replace($("#I_STS"));
		
		//진료과/진료의사
		var resultCode3 = "";
		resultCode3 = getCodeEng(I_LOGMNU, I_LOGMNM, "TREAT_DIV_ENG");
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
        yearAgo.setDate(yearAgo.getDate() - 7);
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
		var buttonList = getTerm(I_LOGMNU, I_LOGMNM, "S_TERM_ENG");
        //alert(buttonList);
 		$('#I_S_TERM').append(buttonList);
		
		// 결과 구분
		var chkDiv = getCheckBox(I_LOGMNU, I_LOGMNM, "RESULT_DIV_ENG");
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
		$('#rstResultBody').empty();
		
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
		
		//상세 리스트 초기화
		//clear
		//dataProvider2.clearRows();
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
		
		// rsUserTable 검색
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
		
		//alert("init");
		
		//RD DATA 초기화
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
		
		mast_str = "N";
		ria_str = "N";
		isTripleNew = "N";
		
		hematoma_str = "N";
		
		hematomaStr = "";
		
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
	
	var firstData = {};
	var endData = {};
	var dataList = "";
	function rutCreate(resultList){			//환자 정보 table body 생성	
		//console.log(resultList);
		firstData = {};
		endData = {};
		var rstUserT;
		$('#rstUserBody').empty();			//환자 정보 table body 초기화
		//dataResult2(grid.getValues(index.itemIndex));
		
		dataList = resultList;
		var rstListLen = $('#I_CNT').val();
		if($('#I_CNT').val() > resultList.length){
			rstListLen = resultList.length;
		}
		
		rstUserT = "";
		// 테이블 배열 생성 
		var rstUserTA = [];
		// 테이블 배열 변수 초기화
		var a = 0;
		for(i=0; i<rstListLen; i++ ){
/* 			rstUserT += '	<tr onclick="javascript:resultClick(\''+resultList[i].DAT+'\', \''+ resultList[i].JNO +'\', \''+i+'\', this)">';
			rstUserT += '       <td class="tool">'+ resultList[i].DAT2  + '</td>';
			rstUserT += '       <td class="tool">' + resultList[i].CHN + '</td>';
			rstUserT += '       <td class="tool" style="text-align:left;">' + resultList[i].NAM + '</td>';
			rstUserT += '  	    <td class="tool">' + resultList[i].SEX + '</td>';
			rstUserT += '	    <td class="tool">' + resultList[i].AGE + '</td>';
			if(isNull(resultList[i].STS) || resultList[i].STS == " "){
			 	 rstUserT += '  <td style="max-width: 100px;" ></td>';
			}else{
				if(resultList[i].STSD == "완료"){
				 rstUserT += '  <td style="max-width: 100px; color: #0000ff" >' + resultList[i].STSD + '</td>';
				}else{
				 rstUserT += '  <td style="max-width: 100px;" >' + resultList[i].STSD + '</td>';
				}
			}
			rstUserT += '	</tr>'; */
			// 테이블 배열에 저장
			rstUserTA[a++] = '	<tr onclick="javascript:resultClick(\''+resultList[i].DAT+'\', \''+ resultList[i].JNO +'\', \''+i+'\', this)">';
			rstUserTA[a++] = '       <td class="tool">'+ resultList[i].DAT2  + '</td>';
			rstUserTA[a++] = '       <td class="tool">' + resultList[i].CHN + '</td>';
			rstUserTA[a++] = '       <td class="tool" style="text-align:left;">' + resultList[i].NAM + '</td>';
			rstUserTA[a++] = '  	    <td class="tool">' + resultList[i].SEX + '</td>';
			rstUserTA[a++] = '	    <td class="tool">' + resultList[i].AGE + '</td>';
			if(isNull(resultList[i].STS) || resultList[i].STS == " "){
				rstUserTA[a++] = '  <td style="max-width: 100px;" ></td>';
			}else{
				if(resultList[i].STSD == "완료"){
					rstUserTA[a++] = '  <td style="max-width: 100px; color: #0000ff" >' + 'Completion' + '</td>';
				}else if(resultList[i].STSD == "일부완료"){
					rstUserTA[a++] = '  <td style="max-width: 100px;" >' + 'Partially Completion' + '</td>';
				}else{
					if(resultList[i].STSD == "검사중"){
						rstUserTA[a++] = '  <td style="max-width: 100px;" >' + 'In progress' + '</td>';
					}
				}
			}
			rstUserTA[a++] = '	</tr>'; 
			
			//$('#rstUserBody').append(rstUserT);
			if(i==0){
				firstData.date = resultList[i].DAT;
				firstData.jno = resultList[i].JNO;
			}else if(i==39){
				endData.date = resultList[i].DAT;
				endData.jno = resultList[i].JNO;				
			}
		}
		//$('#rstUserBody').html(rstUserT);
		// 테이블 배열로 생성
		$('#rstUserBody').html(rstUserTA.join(''));
		//console.log(dataList);
		//console.log(rstUserTA.join(''));
		$('.tool').hover(function(){
			$(this).attr('title', $(this).html());	
		})
		
	}
	
	function resultClick(DAT, JNO, i, obj){		//수진자 목록 >>> 검사결과 보기
		
		var table = document.getElementById("rstUserTable");
		var tr = table.getElementsByTagName("tr");
		$('#rstUserBody tr').css('background-color', '');
		// 수진자 목록 선택된 행 배경색 변경
		obj.style.backgroundColor = "rgba(255, 161, 32, 0.196)"; 
		
		TabResize();						// table size조절
		$('#rstResultBody').empty();		// 검사결과 Table Body 초기화
		if(loding){
			callLoading(null, "on");
		}
		setTimeout(function() {
 			dataResult2(DAT, JNO, i);	
		}, 200);
	}
	
	function tableFocus(obj){
		
		var table = document.getElementById("rstResultTable");
		var tr = table.getElementsByTagName("tr");
		$('#rstResultTable tr').css('background-color', '');
		// 검사결과 선택된 행 배경색 변경
		obj.style.backgroundColor = "rgba(255, 161, 32, 0.196)";
		
	}
	
	function resultTable(rstUserDtl2){		// 검사 결과 Table Body 생성
		//console.log(rstUserDtl2);
		var rstReusltT;
		rsTableList = "";
		rsTableList = rstUserDtl2;
		
		$('#rstResultBody').empty();		// 검사결과 Table Body 초기화
		// white-space: nowrap; text-overflow: clip; overflow: hidden; 데이터가 테이블 가로 사이즈 안넘기고 숨기기
		
		rstReusltT = "";
		// 검사결과 테이블 배열 생성, 초기화, 변수 초기화 
		var rstResultTA = [];
		var a = 0;
		
		for(i=0; i<rstUserDtl2.length; i++ ){
			/* 			
 			rstReusltT += '<tr onclick="javascript:tableFocus(this)">';
			rstReusltT += '      <td><input type="checkbox" checked="checked" id="rstCheck" name="rstCheck"></td>';
			rstReusltT += ' 	 <td class="tool" style="max-width: 100px; text-align:left;">' + rstUserDtl2[i].O_OCD + '</td>';
			rstReusltT += '  	 <td class="tool" style="max-width: 150px;" onclick="javascript:goOCD(\''+rstUserDtl2[i].O_GCD+'\', \''+rstUserDtl2[i].O_GCDN+'\',\''+rstUserDtl2[i].O_ACD+'\')">' + rstUserDtl2[i].O_GCDN + '</td>';
			// 검사 결과 색상 변경
			if(rstUserDtl2[i].O_C07 == "H" || rstUserDtl2[i].O_FLG == "R" || rstUserDtl2[i].O_CHR.indexOf("Positive") >= 0){
				rstReusltT += '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #ff0000;">' + rstUserDtl2[i].O_CHR + '</td>';
			}else if(rstUserDtl2[i].O_C07 == "L"){
				rstReusltT += '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #0000ff;">' + rstUserDtl2[i].O_CHR + '</td>';
			}else{
				rstReusltT += '  <td class="tool" style="max-width: 150px; font-weight: bold;">' + rstUserDtl2[i].O_CHR + '</td>';
			}
			rstReusltT += '      <td class="tool" style="max-width: 130px; text-align:left;">' + rstUserDtl2[i].O_REF + '</td>';
			// H/L 색상 변경
			if(rstUserDtl2[i].O_C07 == "H"){
				rstReusltT += '  <td class="tool" style="color: #ff0000;">' + rstUserDtl2[i].O_C07 + '</td>';
			}else if(rstUserDtl2[i].O_C07 == "L"){
				rstReusltT += '  <td class="tool" style="color: #0000ff;">' + rstUserDtl2[i].O_C07 + '</td>';
			}else{
				rstReusltT += '  <td class="tool">' + rstUserDtl2[i].O_C07 + '</td>';
			}
			rstReusltT += '      <td class="tool" style="white-space: nowrap; text-overflow: clip;  max-width: 150px;">' + rstUserDtl2[i].O_BDT2 + '</td>';
			// 시계열 아이콘 표시 여부
			if( rstUserDtl2[i].O_PRF == "Y" ){
				rstReusltT += '  <td onclick="javascript:goStatsTime(\''+rstUserDtl2[i].O_GCDN+'\', \''+rstUserDtl2[i].O_GCD+'\', \''+rstUserDtl2[i].O_ACD+'\')"><img src="../images/common/btn_srch.png" width="20px" height="20px;" ></td>';
			}else{
				rstReusltT += '  <td></td>';
			}
			rstReusltT += '</tr>'; 
			*/
			//datData1 = rstUserDtl2[i].O_REF.replace(/n/gi,"\n").trim();
			// 검사결과 테이블 생성
			rstResultTA[a++] = '<tr onclick="javascript:tableFocus(this)">';
			rstResultTA[a++] = '      <td><input type="checkbox" checked="checked" id="rstCheck" name="rstCheck"></td>';
			rstResultTA[a++] = ' 	 <td class="tool" style="max-width: 100px; text-align:left;">' + rstUserDtl2[i].O_OCD + '</td>';
			rstResultTA[a++] = '  	 <td class="tool" style="max-width: 150px;" onclick="javascript:goOCD(\''+rstUserDtl2[i].O_GCD+'\', \''+rstUserDtl2[i].O_GCDN+'\',\''+rstUserDtl2[i].O_ACD+'\')">' + rstUserDtl2[i].O_GCDN + '</td>';
			// 검사 결과 색상 변경
			//if(rstUserDtl2[i].O_C07 == "H" || rstUserDtl2[i].O_FLG == "R" || rstUserDtl2[i].O_CHR.indexOf("Positive") >= 0){
			if(rstUserDtl2[i].O_C07 == "H" || rstUserDtl2[i].O_FLG == "R"){
				rstResultTA[a++] = '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #ff0000;">' + rstUserDtl2[i].O_CHR + '</td>';
			}else if(rstUserDtl2[i].O_C07 == "L"){
				rstResultTA[a++] = '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #0000ff;">' + rstUserDtl2[i].O_CHR + '</td>';
			}
			// 71252 종목인 경우, '<' 부등호가 없는 경우에는 빨강색 표시되어야 함. (단, 부속코드가 01,02,03 3개만 해당됨)
    	    // 71259 종목인 경우, '이하' 문구가 없는 경우에는 빨강색 표시되어야 함. (단, 부속코드가 01,02 2개만 해당됨)
    	    // 71259 종목인 경우, '이하' 문구가 없는 경우에는 빨강색 표시되어야 함. (단, 부속코드가 01,02 2개만 해당됨)
			else if(
						(rstUserDtl2[i].O_CHR.indexOf("<") <= -1 && rstUserDtl2[i].O_GCD == "71252" && rstUserDtl2[i].O_ACD != "00")
						||(rstUserDtl2[i].O_CHR.indexOf("이하") <= -1 && rstUserDtl2[i].O_GCD == "71259" && rstUserDtl2[i].O_ACD != "00")
						||(rstUserDtl2[i].O_CHR.indexOf("<") <= -1 && rstUserDtl2[i].O_GCD == "71257")
						
						// 2019.12.24 - 21295 종목인 경우, 'Reactive' 문구가 포함된 결과일 경우 빨강색 표시되어야 함. (단, 'Non-Reactive' 문구가 포함된 경우는 제외함)
	    	    		||(rstUserDtl2[i].O_CHR.indexOf("Reactive") > -1 && rstUserDtl2[i].O_GCD == "21295" && rstUserDtl2[i].O_CHR.indexOf("Non-Reactive") <= -1)
					){
				
				rstResultTA[a++] = '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #ff0000;">' + rstUserDtl2[i].O_CHR + '</td>';
				
			}else{
				rstResultTA[a++] = '  <td class="tool" style="max-width: 150px; font-weight: bold;">' + rstUserDtl2[i].O_CHR + '</td>';
			}
			rstResultTA[a++] = '      <td class="tool" style="max-width: 130px; text-align:left;">' + rstUserDtl2[i].O_REF1 + '</td>';
			//참고치 개행 적용
			//rstResultTA[a++] = '      <td class="tool" style="max-width: 130px; text-align:left;">' + rstUserDtl2[i].O_REF1.replace(/\n/gi, "<br>").trim() + '</td>';
			
			// H/L 색상 변경
			if(rstUserDtl2[i].O_C07 == "H"){
				rstResultTA[a++] = '  <td class="tool" style="color: #ff0000;">' + rstUserDtl2[i].O_C07 + '</td>';
			}else if(rstUserDtl2[i].O_C07 == "L"){
				rstResultTA[a++] = '  <td class="tool" style="color: #0000ff;">' + rstUserDtl2[i].O_C07 + '</td>';
			}else{
				rstResultTA[a++] = '  <td class="tool">' + rstUserDtl2[i].O_C07 + '</td>';
			}
			rstResultTA[a++] = '      <td class="tool" style="white-space: nowrap; text-overflow: clip;  max-width: 150px;">' + rstUserDtl2[i].O_BDT2 + '</td>';
			// 시계열 아이콘 표시 여부
			if( rstUserDtl2[i].O_PRF == "Y" ){
				rstResultTA[a++] = '  <td onclick="javascript:goStatsTime(\''+rstUserDtl2[i].O_GCDN+'\', \''+rstUserDtl2[i].O_GCD+'\', \''+rstUserDtl2[i].O_ACD+'\')"><img src="../images/common/btn_srch.png" width="20px" height="20px;" ></td>';
			}else{
				rstResultTA[a++] = '  <td></td>';
			}
			rstResultTA[a++] = '</tr>';
			
			//$('#rstResultBody').append(rstReusltT);
		}
		//$('#rstResultBody').html(rstReusltT);
		// 검사결과 테이블 등록
		$('#rstResultBody').html(rstResultTA.join(''));
		
		// tooltip 사용
		$('.tool').hover(function(){
			
			// 참고치 개행 적용
			/* if($(this).html().indexOf("<br>") > -1){
				console.log($(this).html());				
				$(this).attr('title', $(this).html().replace(/<br>/gi, "\n").trim());	
			}else{ */
				$(this).attr('title', $(this).html());
			//}
			
			
		});
		
	}
	
	function goOCD(GCD, FKN, ACD){		// 검사 결과 검사항목 상세 팝업창
		//console.log(ACD);
		if(ACD == "" || ACD == "00"){
			openPopup2(GCD, FKN);
		}
	}
	
	function goStatsTime(FKN, GCD, ACD){	// 시계열 페이지 이동
		parent.goPage('STATSTIME', '시계열', 'statsTime.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+$('#CHN').html()+"&I_NAM="+$('#NAM').html()+"&I_DAT="+$('#DAT').html()+"&I_FKN="+FKN+"&I_GCD="+GCD+"&I_ACD="+ACD );
	}
	
	//'../images/common/btn_srch.png'
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/rstUserList.do"){
			//상세 초기화
			init();
			loding = false;
						
			var resultList =  response.resultList;
			
 			for (var i in resultList){
				if(resultList[i].PRF == "N"){
					resultList[i].PRF = "";
				}
				if(resultList[i].DAT != 0){
					resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
				}
				
/* 				if(resultList[i].PNF != ""){
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
				} */
			} 
			rutCreate(resultList);
			
			if(resultList.length < $('#I_ICNT').val()){					// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
				$('#I_PNN_N').addClass("disabled");
			}else{
				//if($('#I_PNN').val() != "P"){
					//dataProvider1.hideRows([(dataProvider1.getRowCount()-1)]);						// 보여줄 데이터 마지막 값 숨김
				//}
				$('#I_PNN_N').removeClass("disabled");
			}
			
			if($('#I_PNN').val() == "C"){											// 첫페이지일 경우 이전 페이지 버튼 숨김
				
				$('#I_PNN_P').addClass("disabled");
			}
			//console.log(Param_MAINFLAG);
			if(Param_MAINFLAG == "Y"){						// 메인화면의 mainCont.jsp에 있는 수진자별 결과관리에서 수진자 선택해서 들어올 경우
				//$('.title_area').removeClass("open");
				/*
				if(mainIn == "Y"){
					mainIn = "N";
					$('.btn_open').click();	
				}
				*/
				Param_MAINFLAG = "N";
				if(resultList.length > 0){
					//gridView1.setCurrent(Param_POT);
					var index = {
					    itemIndex: Param_POT
					};
					
					//gridView1.setCurrent(index);
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
				
			} else {										// 수진자별 결과관리 페이지에서 search 할 경우
				//if($('#I_PECF').val() == "C"){ //병원 사용자
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
				//}
			}
		}
		
		if(strUrl == "/rstUserDtl.do"){
			
			//gridView1.setCurrent(Param_POT);
			//환자정보
			var rstUserDtl =  response.rstUserDtl;
			$('#NAM').html(rstUserDtl[0]["NAM"]);
			$('#CHN').html(rstUserDtl[0]["CHN"]);
			//console.log(rstUserDtl[0].DAT);
			var datData = rstUserDtl[0].DAT.toString(); 
			var datCustom = datData.substring(0,4)+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			//var datCustom = datData.substring(0,4);
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

			// MAST결과
			mast_class1="<b>class1</b> : ";
		    mast_class2="<b>class2</b> : ";
		    mast_class3="<b>class3</b> : ";
		    mast_class4="<b>class4</b> : ";
		    mast_class5="<b>class5</b> : ";
		    mast_class6="<b>class6</b> : ";
		    
			//console.log("### 111 mast_class6 :: "+mast_class6);
			//alert("### 111 mast_class6 :: "+mast_class6);
		    
		    rlt_class = "";
		    
			//수진자 목록 상세
			var rstUserDtl2 =  response.rstUserDtl2;
			
			// 코로나 CT 결과
			var coronaCtVal = response.coronaCtVal;
			
			// 세포유전 결과 (2022.08.29)
			var cellVal = response.cellVal;
			
			// 검사결과 height 조절을 위한 rsTableList 데이터 초기화/추가
			rsTableList = "";
			rsTableList = rstUserDtl2;
			if(rstUserDtl2.length > 0){
				
				// 양성 데이터 중복 문제 해결
				remarkPositiveGcdn = "";
				
				for (var i in rstUserDtl2){
					if(rstUserDtl2[i].O_CKI == "Y" && img_cnt == 0){
						calLCnt ++;
// 						console.log("(rstUserDtl2[i].O_CKI    ====  "+calLCnt);
					}
					if (rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("Eugene") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("에이스") >= 0){
						LmbYN_str = "Y";
						//console.log("LmbYN_str = " + LmbYN_str);
					}
					
					if(rstUserDtl2[i].O_PRF == "N"){
						rstUserDtl2[i].O_PRF = "";
					}
					
					var datData1 = "";
					
					//datData1 = rstUserDtl2[i].O_REF.replace(/n/gi,"\n").trim();
					datData1 = rstUserDtl2[i].O_REF;
					//datData1 = "<br>";
					rstUserDtl2[i].O_REF = datData1;
					//rstUserDtl2[i].O_REF = "<br>"";
					
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
					
					
					//datData1 = rstUserDtl2[i].O_REF;
					//console.log(datData1);
					//rstUserDtl2[i].O_REF = rstUserDtl2[i].O_REF.replace(/$/gi,"\n").trim();
					//rstUserDtl2[i].O_REF = rstUserDtl2[i].O_REF;
					
					
					/*
					if(datData1.length > 25){
						rstUserDtl2[i].O_REF2 = datData1.substring(0,25)+"...";	
					}else {
						//rstUserDtl2[i].O_BDT2 = rstUserDtl2[i].O_BDT; 
						rstUserDtl2[i].O_REF2 = datData1;
					}
					*/
					
					//rstUserDtl2[i].O_REF2 = datData1;
					
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
						//rstUserDtl2[i].O_BDT2 = rstUserDtl2[i].O_BDT; 
						rstUserDtl2[i].O_BDT2 = "";
					}
					rstUserDtl2[i].F010GCD = rstUserDtl2[i].O_GCD;
					rstUserDtl2[i].O_C07 = rstUserDtl2[i].O_C07.substring(0,1);
					//rstUserDtl2[i].O_C07 = rstUserDtl2[i].O_C07.substring(0,1);
					rstUserDtl2[i].O_GCDNIMG = "../images/common/ico_viewer.png";
					
					if(rstUserDtl2[i].O_ACD != "" && rstUserDtl2[i].O_ACD != "00"){
						rstUserDtl2[i].O_OCD = "";
					}
					
					//20160502 MAST 검사 결과 겹치는 부분 수정 작업
					/*
					if ((tempItemCode.equals("0068916")) || (tempItemCode.equals("0068917"))
							|| (tempItemCode.equals("0068939")) || (tempItemCode.equals("0068940"))
							|| (tempItemCode.equals("0068941")) || (tempItemCode.equals("0068945"))
							|| (tempItemCode.equals("0068946")) || (tempItemCode.equals("0068950"))
							|| (tempItemCode.equals("0068952")) || (tempItemCode.equals("0068959"))
							|| (tempItemCode.equals("0068962")) || (tempItemCode.equals("0068963"))
							|| (tempItemCode.equals("0068817")) || (tempItemCode.equals("0068834"))
							|| (tempItemCode.equals("0068835")) || (tempItemCode.equals("0068836"))
					) {
						continue;
					}
					
					*/

					//mast_str
					if(rstUserDtl2[i].O_GCD == "00690" || rstUserDtl2[i].O_GCD == "00691"
						|| rstUserDtl2[i].O_GCD == "00701" || rstUserDtl2[i].O_GCD == "00702"
						|| rstUserDtl2[i].O_GCD == "00687"
					){
						
						var startChar = -1;
						var item_str = rstUserDtl2[i].O_GCDN;
						//console.log("### item_str["+i+"] : "+item_str);
						try{
							startChar = item_str.indexOf("]");
							
							while(item_str.indexOf("]") > 0){
								
								//console.log("## item_str(전) : "+item_str);
								//console.log("## item_str.indexOf : "+item_str.indexOf("]"));
								//console.log("## startChar : "+startChar);
								item_str = item_str.substring(startChar+1);
								//console.log("## item_str(후) : "+item_str);
								
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
							
							//console.log("replaceAll(item_str) : "+replaceAll(item_str));
							
						} catch(e){}
						
						//console.log("### rstUserDtl2["+i+"].O_CHR : "+rstUserDtl2[i].O_CHR);
						//console.log(" ");
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
						//console.log(gcdMerge2);
						
						$('#I_GCD').val(gcdMerge2);
						
						//$('#I_GCD').val(rstUserDtl2[i].O_GCD);
						//$('#I_ACD').val(rstUserDtl2[i].O_ACD);
						
						rd_gcd = $('#I_GCD').val();
					    rd_acd = $('#I_ACD').val();
					    
						img_dpt = "";
						if(rstUserDtl2[i].O_DPT == "5230"){
							img_dpt = "5295"	
						} else {
							img_dpt = rstUserDtl2[i].O_DPT;
						}
						//console.log("img_dpt = " + img_dpt);
						var formData = $("#searchForm").serialize();
						//alert(JSON.stringify(data));
						//alert(data.DAT);
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
						 ){
							pathologyType_str = "N";
							pathologyType2_str = "Y";
						}
						//console.log("pathologyType_str = pathologyType2_str : " +pathologyType_str + " = " + pathologyType2_str)
					} else if(rstUserDtl2[i].O_CKP == "5295@"){
						CytologyType2_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5270@"){
						CytologyType_str = "Y";
						
						if (rstUserDtl2[i].O_LMBN.indexOf("한미") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("포유") >= 0 
							 || rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("화신") >= 0 
							 || rstUserDtl2[i].O_LMBN.indexOf("운경") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("명진") >= 0
							 || (rstUserDtl2[i].O_LMBN.indexOf("삼성") >= 0 && parseInt(rstUserDtl2[i].O_BDT) > 20200316)
							 || (rstUserDtl2[i].O_LMBN.indexOf("하나병리") >= 0 && parseInt(rstUserDtl2[i].O_BDT) > 20200107)
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
				// 반복문 마지막
				
				//gird 데이터 추가, 체크 표시 검사 결과 테이블 생성
				resultTable(rstUserDtl2);
				
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
				
				//console.log("### 222 mast_class6 :: "+mast_class6);
				//alert("### 222 mast_class6 :: "+mast_class6);
				
				
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
				
				//console.log("### 333 rlt_class :: "+rlt_class);
				//alert("### 333 rlt_class :: "+rlt_class);
				
				// 20190319 김윤영 과장님 요청사항 : 내용이 없는 상세내역 hide 처리 
				if(rlt_class == "" || rlt_class == "<br>" || rlt_class == "<br><br>&nbsp;&nbsp;"){
					mast_str="N";
				}
				
				$('#MastResult').html(rlt_class);
				//console.log(mast_str+" = "+rlt_class);
				
				//alert(LmbYN_str+ " = " + pathologyType_str+ " = " +pathologyType2_str);  
				
				 
				/*
				console.log("pdf_html = " + pdf_html);
				console.log("pdf_htmlx = " + pdf_htmlx);
				pdf_htmlx += pdf_html;
				
				$('#hasimg').html(img_html);
				$('#Popphoto').html(pdf_htmlx);
				*/
				//callResize();
				
				//기형아 검사
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
						
						/*
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk by Maternal age</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+param_rstUserMw109rms.O_RSK+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Down syndrome</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+param_rstUserMw109rms.O_DWN+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Edward syndrome</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+param_rstUserMw109rms.O_EDW+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line9_l' height='25' colspan='2'></td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						riaResult += "			<br/>";
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Open NTD</td>";
						riaResult += "					<td class='line9' height='25' width='250'></td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line9_l' height='25' colspan='2'></td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						*/
						
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
				
				
				//기형아 검사 끝
				
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
					if(rstUserMw107rms3.length > 0){
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
//			setTimeout("mastResult()",1000);
			//console.log("확인1");
					
		}
		//검사결과 끝 예상
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
				//var img_dpt2 = resultList[r].URL.substring(resultList[r].URL.lastIndexOf("\\")+1);
				//var img_dpt2 = resultList[r].URL.substring(resultList[r].URL.indexOf("\\mCLIS\\NML\\image\\"),resultList[r].URL.indexOf("\\mCLIS\\NML\\image\\")+4);
				var img_dpt2 = resultList[r].URL.substring(17, 21);
				//console.log("img_dpt2 = " +img_dpt2);
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
						//img_html += 		"<img src='"+trmsSystemUrl+"/rotateImage.do?url=http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath&sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg&radians=270' width=100%>"; 
						img_html += 		"<img src='/rotateImage.do?url=http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath&sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg&radians=270' width=100%>";
						img_html +=		"</td>";
						img_html += "</tr>";
						
						//console.log(img_html);
						//setTimeout("",100);
						
					} else if(resultList[r].URL.indexOf("25000.jpg") > -1) {
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						img_html += 		"<img src='/images/remarkImg/25000.jpg' width=100%>"; 
						img_html +=		"</td>";
						img_html += "</tr>";	
						//console.log(img_html);
					} else {
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						img_html += 		"<img src='http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg' width=100%>"; 
						img_html +=		"</td>";
						img_html += "</tr>";	
						//console.log(img_html);
					}
				}
				
			}
			
			pdf_htmlx += pdf_html;
			
			$('#hasimg').html(img_html);
			$('#Popphoto').html(pdf_htmlx);
			calLCnt--;
			setTimeout("mastResult()",1000);
		}
		//검사결과 안에서 다른 데이터 가져오기
	}
	
	var rsUserList = "";
	function dataResult2(SDAT, SJNO, i){
		rsUserList = dataList[i];
		//console.log("---dataList----");
		//console.log(rsUserList);
		//상세 초기화
		init();
		
		//RD DATA
		rd_dat = SDAT;
	    rd_jno = SJNO;
	    
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
	    
	    //rd_gcd = data.GCD;
	    //rd_acd = data.ACD;
	    //console.log("rd_rgn1 = " + rd_rgn1);
		var formData = $("#searchForm").serialize();
		img_cnt = 0;
		img_sdb = "";
		img_rotate = "";
		
		
		var rstUserParam = "";
		rstUserParam = "&I_DAT="+SDAT;
		rstUserParam += "&I_JNO="+SJNO;
		
		$('#I_DTLDAT').val(SDAT);
		$('#I_DTLJNO').val(SJNO);
		
		formData += rstUserParam;
		var url = "/rstUserDtl.do";
		calLCnt = 1;
		ajaxCall(url, formData, false);
	}
	var calLCnt = 0;
	
	//조회
	function search(){
		//상세 초기화
		init();
		callLoading(null, "off");
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
				$('#I_FNM').val('');
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
		// cancel
		// clear table 2개
		if(!searchValidation()) {return;}
		
		/*
		 if($("#I_TDT").val()==""||$("#I_FDT").val()==""){
	         CallMessageEng("273","alert");      //조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
	         return ;
	      }
		 */

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
		
		dataResult();
	}
	
	function searchValidation(){
		
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		
		//I_PINQ = Y ==> 수진자명，챠트번호　조건만 들어가는 기본 조건
		if(
				(
						$('#I_DGN').val() == "P" 
					|| $('#I_PETC').val() != "" 
					|| $('#I_STS').val() != ""  
					|| $('#I_PJKN').val() != "" 
					|| $('#I_HAK').val() != "" 
					|| $('#I_RGN1').is(":checked") == true 
					|| $('#I_RGN2').is(":checked") == true  
					|| $('#I_RGN3').is(":checked") == true
				)
				||(
						$("#I_FDT").val() == "" 
						&& $("#I_TDT").val() == "" 
						&& $('#CHN').html("") != ""
						&& $('#I_BIRTH').val() == ""// 생년월일
				)
				
		){
			$("#I_PINQ").val("N");
		} else {
			$("#I_PINQ").val("Y");
		}
		
		// 유효성검사 - 최대 검색 기간 체크 !!
		var now = new Date();
		var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
			CallMessageEng("301","alert",["10"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
			return ;
		}
		
		// 변경전 : 검색조건에 병원코드가 입력되지 않으면 검색이 불가능함.
		// 변경후 : 검색조건에 병원코드가 입력되지 않으면서 사원인 경우만 검색이 불가능 하도록 변경. 파견사원의 경우, 병원코드가 비어있어도 검색이 되도록 하기 위해서.
		if($('#I_PHOS').val() == "" && $('#I_PECF').val() =="E" && s_paguen_hos_yn == ""){
			CallMessageEng("245", "alert", "",dataClean);
			return false;
		}
		
		if(sDate > eDate){
			CallMessageEng("229","alert","", dataClean); // 조회기간을 확인하여 주십시오.
			return false;
		}		
		
		if(strByteLength($("#I_PCHN").val()) > 15){
			CallMessageEng("292", "alert", "", dataClean);
			return false;
		}
		
		if(strByteLength($("#I_PETC").val()) > 50){
			CallMessageEng("295", "alert", "", dataClean);
			return false;
		}
		//page_count 초기화
		page_count = 0;
		
		/*최초 조회 시 I_IGBN = C / I_IDAT = 0 / I_IJNO = 0*/
		$("#I_IGBN").val("C");
		$("#I_IDAT").val("0");
		$("#I_IJNO").val("0");
		
		//console.log(strByteLength("a한글b") + " Bytes");
		//var sDate = new Date($("#I_FDT").val());
		//var eDate = new Date($("#I_TDT").val());
		
		
		
		
		/*
		if($('#I_NAM').val() == ""){
			CallMessageEng("245", "alert", ["수진자 명을"], dataClean);
			return false;
		}

		if($('#I_CHN').val() == ""){
			CallMessageEng("245", "alert", ["수진자 명을 검색하여 차트 번호를"], dataClean);
			return false;
		}		
		
		var interval = eDate - sDate;
		var day = 1000*60*60*24;
		
		if (interval/day > 365){
   			CallMessageEng("274","alert","", dataClean);		//최대 1년 내에서 조회해주세요.
			return false;
		}
		*/
		if($("#I_FDT").val() != "" && $("#I_TDT").val() != ""){
			if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
				CallMessageEng("273", "alert","", dataClean);
			   return false;
			}	
		}
		return true;
		
	}
	
	function dataClean(){
		// 검사 결과 상세 초기화 
		init();
		// 수진자 목록 table 초기화
		$('#rstUserBody').empty();
		// 검사 결과table 초기화
		$('#rstResultBody').empty();
		
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
	}
	
	function mastResult(){
		//console.log("mastResult    ====  "+calLCnt);
		
		/*
		console.log("mast_str =" +mast_str);
		console.log("ria_str =" +ria_str);
		console.log("hematoma_str =" +hematoma_str);
		console.log("obgy_str =" +obgy_str);
		console.log("img_str =" +img_str);
		console.log("photo_str =" +photo_str);
		console.log("microbio_str =" +microbio_str);
		console.log("LmbYN_str =" +LmbYN_str);
		console.log("pathologyType_str =" +pathologyType_str);
		console.log("pathologyType2_str =" +pathologyType2_str);
		console.log("CytologyType_str =" +CytologyType_str);
		console.log("CytologyType2_str =" +CytologyType2_str);
		console.log("CytologyType3_str =" +CytologyType3_str);
		console.log("remark_str =" +remark_str);
		*/
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
			//20190311 PDF가 있을경우 PDF를 제외한 상세보기를 모두 표시하지 않음(최재원 대리)
			//운영은 현재 표시되고 있음
			
			mast_str = "N";
			ria_str = "N";
			hematoma_str = "N";
			obgy_str = "N";
			img_str = "N";
			microbio_str = "N";
			LmbYN_str = "N";
			pathologyType_str = "N";
			pathologyType2_str = "N";
			CytologyType_str = "N";
			CytologyType2_str = "N";
			CytologyType3_str = "N";
			Cytogene_str = "N";
			Cytogene_prc = "N";
			remark_str = "N";
			corona_ct_str = "N";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
		}
		
		if(mast_str == "Y"){
			$('#DivMastResult').show();
			$('#DivMastResult_Reference').show();
			
		} else {
			$('#DivMastResult').hide();
			$('#DivMastResult_Reference').hide();
		}
		
		//기형아 검사
		if(ria_str == "Y"){
			if($("#I_DTLDAT").val() >= 20170609){
				//$("#REALAGE").text("분만 시 나이");
				$("#REALAGE").text("Childbirth age");
			} else {
				//$("#REALAGE").text("나이");
				$("#REALAGE").text("age");
			}
			$('#DivRia').show();
			//$('#Ria').html(str);		
		} else {
			//$("#REALAGE").text("나이");
			$("#REALAGE").text("age");
			$('#DivRia').hide();
			//$('#MastResult').html(str);
		}
		
		//remark(혈종)
		if(hematoma_str == "Y"){
			$('#DivHematoma').show();
					
		} else {
			$('#DivHematoma').hide();
			
		}
		
		//remark(산전)
		if(obgy_str == "Y"){
			$('#DivObgy').show();
					
		} else {
			$('#DivObgy').hide();
			
		}
		
		//이미지
		if(img_str == "Y"){
			$('#DivHasimg').show();
					
		} else {
			$('#DivHasimg').hide();
			
		}
		
		//pdf
		if(photo_str == "Y"){
			$('#DivPopphoto').show();
					
		} else {
			$('#DivPopphoto').hide();
			
		}
		
		//microbio
		if(microbio_str == "Y"){
			$('#DivMicrobio').show();
					
		} else {
			$('#DivMicrobio').hide();
			
		}
		
		
		//LmbYN
		if(LmbYN_str == "Y"){
			$('#DivLmbYN').show();
					
		} else {
			$('#DivLmbYN').hide();
			
		}
		
		//pathologyType
		if(pathologyType_str == "Y"){
			$('#DivPathologyType').show();
					
		} else {
			$('#DivPathologyType').hide();
			
		}
		
		//pathologyType2
		if(pathologyType2_str == "Y"){
			$('#DivPathologyType2').show();
					
		} else {
			$('#DivPathologyType2').hide();
			
		}
		
		//CytologyType
		if(CytologyType_str == "Y"){
			$('#DivCytologyType').show();
					
		} else {
			$('#DivCytologyType').hide();
			
		}
		
		//CytologyType2
		if(CytologyType2_str == "Y"){
			$('#DivCytologyType2').show();
					
		} else {
			$('#DivCytologyType2').hide();
			
		}
		
		//CytologyType
		if(CytologyType3_str == "Y"){
			$('#DivCytologyType3').show();
					
		} else {
			$('#DivCytologyType3').hide();
			
		}
		
		//Cytogene
		if(Cytogene_str == "Y" && Cytogene_prc == "Y"){
			$('#DivCytogene').show();
					
		} else {
			$('#DivCytogene').hide();
			
		}
		
		
		//remark 세포병리로 삽입로직 복구 
		//if(remark_str == "Y" && CytologyType_str == "N" && CytologyType2_str == "N" && CytologyType3_str == "N" ){
		if(remark_str == "Y"){
			$('#DivRemarktext').show();
			//$('#Remarktext').html(str);		
		} else {
			$('#DivRemarktext').hide();
			//$('#Remarktext').html(str);
		}

		// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
		if(corona_ct_str == "Y"){
			$('#DivCoronaCT').show();
		} else {
			$('#DivCoronaCT').hide();
		}
		
 		//console.log(calLCnt);
		//console.log("tab2");
		TabResize();
		//$('#topFunc').click();
		//moveFocus();
		if(calLCnt ==0){
			setTimeout(function() {
				loding = true;
				//$('#I_DGN').focus();
				//$('#addTestButton').focus();
				//$('#pointButton').focus();
				//$('#rstDisplayPrintButton').focus();
				
				//20190326 요청사항 수정(화면 줄였을때 포커싱 이동)
				$('#addTestButton')[0].scrollIntoView(true); 
				//$('#dtlDiv').focus();
				callLoading(null, "off");
			}, 300);
		}
		
		 
// 		setTimeout(function() {
// 		}, 2000);
	}
	/*
	function moveFocus(){
		//$('#dtlDiv').focus();
		$('#I_DGN').focus();
		
	}
	*/
	function openPopup2(gcd, fkn){

        //var itemIndex = index.itemIndex;
		//var gridValus = gridView2.getValues(itemIndex);
		//var gdcn = gridView2.getValue(itemIndex, "O_GCDN");
		
		var gridValus = {};
		gridValus.F010GCD = gcd;
		
		fnOpenPopup("/testItem01.do",fkn +" 검사 항목 상세",gridValus,callFunPopup2);
	}
	function  callFunPopup2(url,iframe,gridValus){
		iframe.gridViewRead(gridValus);
	}
	
	//추가검사
	function openPopup(str){
		var formData = $("#searchForm").serialize();
		if(str == "1"){
			if($('#NAM').html() == ""){
				//messagePopup("","","추가 검사는 검사결과 상세 데이터가 필수입니다." );
				messagePopup("","","For additional test, detailed data of the test results is required." );
				return;
			}
			fnOpenPopup("/testReq01Popup.do","추가의뢰",formData,callFunPopup);	
		} else if(str == "2"){
			if($('#NAM').html() == ""){
				//messagePopup("","","오더 의뢰지는 검사결과 상세 데이터가 필수입니다." );
				messagePopup("","","Request form requires detailed data of test results." );
				return;
			}
			fnOpenPopup("/orderReqPopup.do","오더의뢰지",formData,callFunPopup);
		} else if(str == "3"){
			fnOpenPopup("/hospSearchSEng.do","Hospital Search",formData,callFunPopup);
		} else if(str == "4"){
			//formData
			/* SMS 보내기
			var dataRows = gridView1.getCheckedRows();
			if(dataRows.length == 0){
				messagePopup("","","수진자 목록 최소 1건 이상 선택 필수입니다." );
				return;
			}
			var data = [];
			for(var i in dataRows){
				//data.push(dataProvider1.getValues(dataRows[i], ""));
				data.push(dataProvider1.getJsonRow(dataRows[i]));
				
				//jsonRow.push(dataProvider.getJsonRow(e));
			}
			//console.log(data);
			if (dataRows.length > 0){
				fnOpenPopup("/smsReqPopup.do","SMS",data,callFunPopup);	
			}
			 */
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
		    	
			//rd_rgn1 = $('#I_RGN1').val();
		    //rd_rgn2 = $('#I_RGN2').val();
		    //rd_rgn3 = $('#I_RGN3').val();
		    
			fnOpenPopup("/addReq01Popup.do","추가의뢰 등록",formData,callFunPopup);
		} else if(str == "9"){
			//getCurrent
			var checkBox = $('input:checkbox[name=rstCheck]:checked');
			var checkBox2 = $('input:checkbox[name=rstCheck]');
			
			if(checkBox.length == 0){
				//messagePopup("","","검사 결과 최소 1건 이상 선택 필수입니다." );
				messagePopup("","","It is necessary to select at least one test result." );
				return;
			}
			
			if(checkBox.length > 0){
				var data = [];
				
				//체크된 값 가져오기
/* 				checkBox.each(function(i){
					console.log(checkBox);
					var tr = checkBox.parent().parent().eq(i);
					var td = tr.children();
					
					console.log(i);
					
				}); */
				
				// 수진자 정보 data에 담기
				var gridData = rsUserList;
				gridData["RSTUSER"] = "RSTUSER";
				gridData["JNO"] = $("#I_DTLJNO").val();
				gridData["DAT"] = $("#I_DTLDAT").val();
				gridData["I_HOS"] = $("#I_PHOS").val();
				//console.log(gridData);
				
				data.push(gridData);
				
				// check된 데이터 data에 담기
				checkBox2.each(function(i){
					//console.log(checkBox2[i].checked);
					if(checkBox2[i].checked == true){
						data.push(rsTableList[i]);
					}
				});
				
				//console.log(data);
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
		} else if(url == "/hospSearchSEng.do"){
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
			//messagePopup("","","화면 출력은 검사결과 상세 데이터가 필수입니다." );
			messagePopup("","","The screen print requires the detailed data of test result." );
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
	            $('#I_FNM').val("");
	         }
		}
	}
	
	function infoClick(){
 		if($('#infoOpen').hasClass('open')){
			$('#infoOpen').removeClass('open');
			$('#infoOpen').html('[정보열기]');
			$('#infoTbody').find('.infoTr').fadeOut(function(){
				TabResize();
			});
		}else{
			$('#infoOpen').addClass('open');
			$('#infoOpen').html('[정보닫기]');
			$('#infoTbody').find('.infoTr').fadeIn(function(){
				TabResize();
			});
		} 
		
	}
	
	function miniOpen(){
		//window.open("rstUserTableMini01.do", "mini", "width=700px, height=400px");
		window.open("rstUserTableMini01.do", "mini", "width=375px, height=590px, scrollbars=yes, resizable=no");
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


		<!-- 
		<input id="topFunc" type="button" onclick="javascript:moveFocus();" style="display:none;">
		 -->
		 
		<div class="content_inner" style="overflow: auto;">
                    <div class="container-fluid" id="containerDiv" ><!-- style="min-width: 1024px;" -->
						<!-- 검색영역 -->
                            <div class="container-fluid container-fluid-inner">
                                <div class="con_wrap_t col-md-12 srch_wrap">
                                    <div class="con_section row" >
				<!-- 						<p class="srch_txt text-center" style="display: none">조회하려면 검색영역을 열어주세요.</p> -->
										<div class="main_srch_box">
											<div class="col-md-11-t col-md-10-t m-t-10">
												<div class="fleft label_margin fleft_next fmgbot" style="width : 40px;">
													<label for="" class="label_basic_t" >Date</label>
												</div>
												<div class="fleft fmgbot" style="width : 330px;">
													<div class="select_area_t" id="I_DGN_DIV">
														<select id="I_DGN" name="I_DGN" class="form-control"></select>
													</div>
													<div class="btn_group" style="font-size: 14px" id="I_S_TERM"></div>
												</div>
												<div class="fleft fmgbot" id="searchDT" style="width : 245px;">
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
												<div class="fleft fmgtop" style="width : 280px;">
													<label for="I_NAM" class="label_basic_t">Subject Name</label>
													<input type="text" id="I_NAM" name="I_NAM" maxlength="14" class="srch_put"  onkeydown="javasscript:enterSearch(0)" style="-webkit-ime-mode:active;-moz-ime-mode:active;-ms-ime-mode:active;ime-mode:active;" >
												</div>
												<div class="fleft fmgtop" style="width : 180px;">
													<label for="I_PCHN" class="label_basic_t">Chart No.</label>
													<input type="text" id="I_PCHN" name="I_PCHN" title="차트번호" maxlength="15" class="srch_put"  onkeydown="javasscript:enterSearch(0)" >
												</div>
												<div class="fleft fmgtop" id="DivHosSearch" style="display: none; ">
                                                    <label for="I_FNM" class="label_basic_t">Hospital</label>
                                                    <%-- 병원코드를 input box 로 받을 경우, 파견사원 병원계정으로 로그인 한 사용자가 타병원 결과까지 검색이 가능하기 때문에 popup 창이 뜨도록 변경함 --%>
                                                    <%-- <input id="I_PHOS" name="I_PHOS" type="text"  value="<%=ss_hos%>" maxlength="5" onkeydown="javasscript:enterSearch(0)" class="srch_put numberOnly srch_hos" style="width:100px;"/> --%>
                                                    <input id="I_PHOS" name="I_PHOS" type="text"  value="<%=ss_hos%>" readonly="readonly"  placeholder="" onClick="javascript:openPopup(3)" class="srch_put numberOnly srch_hos" style="width:100px;"/>
                                                    <input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly"  placeholder="" onClick="javascript:openPopup(3)" style="width:150px;">
												</div>
												<div class="fleft fmgtop"  style="width : 135px;">
													<button type="button" class="btn_srch_t" name = "btn_srch" onClick="javascript:search()">Search</button>
													<button type="button" class="btn btn-gray btn-sm" title="초기화" onClick="javascript:init2()">Reset</button>
												</div>
											</div>
											<div class="col-md-1-t col-md-2-t m-t-9">
											    <div class="fleft title_area_t open" style="float : right">
													<a href="#" class="btn_open_t">close</a>
												</div>
											</div>
										</div>
										<div class="srch_line" style="margin-top: 7px;"></div>
										<div class="srch_box floatLeft">
                                             <div class="srch_box_inner" >
                                             	<div class="floatLeft2">                                                
	                                                 <div class="srch_item_v1">  
	                                                    <label for="I_PETC" class="label_basic">Sample number</label>
	                                                    <input type="text" class="srch_put" id="I_PETC" name="I_PETC" placeholder="" maxlength="50" style="width: 30%;" onkeydown="javascript:enterSearch(0)">
	                                                    <label for="I_BIRTH" class="label_basic">생년월일</label>
	                                                    <input type="text" class="srch_put" id="I_BIRTH" name="I_BIRTH" placeholder="" maxlength="50" style="width: 30%;" onkeydown="javascript:enterSearch(0)">
	                                                </div>
	                                                <div class="srch_item_v2">    
	                                                    <label for="I_STS" class="label_basic">Inspection progress</label>
	                                                    <div class="select_area">
	                                                        <select id="I_STS" name="I_STS">
	                                                        </select>
	                                                    </div>
	                                                </div>             
	                                                <div class="srch_item_v2">
	                                                	<label for="I_HAK" class="label_basic">Inspection field</label>
	                                                   <div class="select_area">
	                                                       <select id="I_HAK" name="I_HAK">
	                                                       </select>
	                                                   </div>
	                                                </div>
                                                </div>
                                                <div class="floatLeft2">
	                                               <div class="srch_item_v1">
	                                               	<div class="select_area">
	                                                      <select id="I_PJKNF" name="I_PJKNF">
				                                       </select>
	                                                   </div>
	                                                   <input type="text" class="srch_put" id="I_PJKN" name="I_PJKN" placeholder="" maxlength="9" onkeydown="javascript:enterSearch(0)" style="width:51%;">
	                                               </div>
	                                               <div class="srch_item_v2">
	                                               	<ul class="chk_lst chk_lst_v2" id="RGN"></ul>
	                                               </div>
                                                </div>
                                            </div>
                                       </div>
 										<!-- <div class="srch_box floatLeft">
                                             <div class="srch_box_inner m-t-10" >                                                
                                                 <div class="srch_item_v5">  
                                                    <label for="I_PETC" class="label_basic">검체번호</label>
                                                    <input type="text" class="srch_put" id="I_PETC" name="I_PETC" placeholder="" maxlength="50" style="width: 75%;" onkeydown="javascript:enterSearch(0)">
                                                </div>
                                                <div class="srch_item_v4">    
                                                    <label for="I_STS" class="label_basic">검사진행</label>
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
                                                 	<label for="I_HAK" class="label_basic">검사분야</label>
                                                    <div class="select_area">
                                                        <select id="I_HAK" name="I_HAK">
                                                        </select>
                                                    </div>
                                                 </div>
                                            </div> 
                                            <div class="srch_box_inner m-t-10">
                                                <div class="srch_item">
                                                	<ul class="chk_lst chk_lst_v2" id="RGN"></ul>
                                                	
                                                	<ul class="chk_lst chk_lst_v2">                                          
                                                        <li><input type="checkbox" value="Y" id="I_RGN1" name="I_RGN1"> <label for="I_RGN1">일반</label></li>  
                                                        <li><input type="checkbox" value="Y" id="I_RGN2" name="I_RGN2"> <label for="I_RGN2">조직</label></li>
                                                        <li><input type="checkbox" value="Y" id="I_RGN3" name="I_RGN3"> <label for="I_RGN3">세포</label></li>
                                                    </ul>
                                                    
                                                </div>
                                            </div>
                                       </div>  -->
                                    </div>
                                </div>
                            </div>
                            <!-- 검색영역 end -->
                        <div class="con_wrap_t col-md-5-t" id="rstListDiv"><!--  style="min-width: 400px;" -->
                            <div class="title_area">
                                <h3 class="tit_h3">Subject List</h3>
                                <div class="tit_btn_area_t">
                                <!-- 
									<button type="button" id="stsPop" class="btn btn-default btn-sm" data-toggle="modal" data-target="#sts-Modal" style="display:none;">STS</button>
									 -->
									 <!--                                 
									 <button type="button" id="stsPop" class="btn btn-default btn-sm" data-toggle="modal" data-target="#sts-Modal">STS</button>
									  -->
                                	<button type="button" class="btn btn-default btn-sm_t" onClick="javascript:openPopup(4)" id="smsDisplay" style="display:none">SMS</button>
                                	<button type="button" class="btn btn-default btn-sm_t" id="I_PNN_P" onClick="javascript:paging('P')" >Prev</button>
                                	<button type="button" class="btn btn-default btn-sm_t" id="I_PNN_N" onclick="javascript:paging('N')" >Next</button>
                                	<!-- 수진자 목록 Mini 버전  네오티커 대체 홈페이지 -->
                                	<button type="button" class="btn btn-default btn-sm_t" id="miniWin" onclick="javascript:miniOpen()" style="display: none;" >window팝업</button>                            
                                </div>
                            </div>
                            <div class="con_section overflow-scr" style="top:-10px;">
                                <div class="tbl_type" style="width:100%; overflow:auto; margin-top:5px;" > <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                                	<div class="realgridH" style="overflow-y: auto; overflow-x:hidden; cursor:default;">
                                	<table class="table table-bordered table-condensed tbl_c-type" width="100%" id="rstUserTable">
                                    	<colgroup>
                                        	<%-- <col width="100px">
                                            <col width="100px">
                                            <col width="80px">
                                            <col width="60px">
                                            <col width="60px">
                                            <col width="80px"> --%>
                                            <col width="20%">
                                            <col width="20%">
                                            <col width="17%">
                                            <col width="8%">
                                            <col width="8%">
                                            <col width="27%">
                                        </colgroup>
                                        <thead >
                                        	<tr style="font-weight: bold; background-color: #fafafa">
                                        		<td>Registration Date</td>
                                        		<td>Chart No.</td>
                                        		<td>Subject Name</td>
                                        		<td>Sex</td>
                                        		<td>Age</td>
                                        		<td>Status of Test</td>
                                        	</tr>
                                        </thead>
                                        <tbody id="rstUserBody" name="rstUserBody" >
                                        </tbody>
                                	</table>
                                	</div>
                         		</div>
                            </div>
                        </div>
                        
                        <div class="con_wrap_t col-md-7-t" style="overflow:auto; " id="dtlDiv"><!-- min-width: 500px; -->
                            <div class="title_area">
                                <h3 class="tit_h3" id="titTest">Result</h3>
                                <div class="tit_btn_area_t">
                                	<!-- <button type="button" class="btn btn-default btn-sm_t" data-toggle="modal" data-target="#result-Modal" onClick="javascript:openPopup(9)">결과지 출력</button>
                                    <button type="button" class="btn btn-default btn-sm_t" onClick="javascript:rstDisplayPrint()" id="rstDisplayPrintButton">화면 출력</button> -->
                                    <!-- 
                                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#plus-srch-Modal" onClick="javascript:rstPopup(1)">추가 검사</button>
                                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#order-Modal" onClick="javascript:rstPopup(2)">오더 의뢰지</button>
                                     -->	
                                    <!-- <button type="button" class="btn btn-default btn-sm_t" onClick="javascript:openPopup(1)" id="addTestButton">추가 검사</button>
                                    <button type="button" class="btn btn-default btn-sm_t" onClick="javascript:openPopup(2)">오더 의뢰지</button> -->
                                </div>																
                            </div>
	                        <div class="con_section overflow-scr" style="overflow:auto; top:-10px;">
		                        <div class="con_wrap_t col-md-12 col-sm-12" style="overflow-y: auto; overflow-x:hidden;" id="rstDiv">
	                            	<div class="" style="cursor:default;" id="infoDiv"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                            		<!-- <button type="button" class="btn-info open" onClick="javascript:infoClick()" id="infoOpen">수진자 정보 닫기</button> -->
	                                	<table class="table table-bordered table-condensed tbl_l-type tbl_type" width="100%" >
	                                    	<colgroup>
	                                        	<%-- <col width="118px">
	                                            <col width="118px">
	                                            <col width="118px">
	                                            <col width="118px">
	                                            <col width="118px">
	                                            <col width="118px"> --%>
	                                            <col width="16.66%">
	                                            <col width="16.66%">
	                                            <col width="16.66%">
	                                            <col width="16.66%">
	                                            <col width="16.66%">
	                                            <col width="16.66%">
	                                        </colgroup>
	                                        <tbody id="infoTbody">
	                                        	<tr>
	                                            	<th scope="row">
	                                            	Subject Name
	                                            	<span class="open" onClick="javascript:infoClick()" id="infoOpen" style="color:#505050; cursor:pointer;">[Close]</span>
	                                            	</th>
	                                                <td><label class="label_basic" id="NAM"></label></td>
	                                                <th scope="row">Chart No.</th>
	                                                <td><label class="label_basic" id="CHN"></label></td>
	                                                <th scope="row">Registration Date</th>
	                                                <td><label class="label_basic" id="DAT"></label></td>
	                                            </tr>
	                                            <tr class="infoTr">
	                                            	<th scope="row">National ID</th>
	                                                <td><label class="label_basic" id="JN"></label></td>
	                                                <th scope="row">Doctor</th>
	                                                <td><label class="label_basic" id="DPTN"></label></td>
	                                                <th scope="row">Sex</th>
	                                                <td><label class="label_basic" id="SEX"></label></td>
	                                            </tr>
	                                            <tr class="infoTr">
	                                            	<th scope="row" id="REALAGE">Age</th>
	                                                <td><label class="label_basic" id="AGE"></label></td>
	                                                <th scope="row">Specimen Type</th>
	                                                <td><label class="label_basic" id="ETCINF"></label></td>
	                                                <th scope="row">Specimen No.</th>
	                                                <td><label class="label_basic" id="ETC"></label></td>
	                                            </tr>
	                                            <tr class="infoTr">
	                                            	<th scope="row">Institution Name</th>
	                                                <td><label class="label_basic" id="HOSN"></label></td>
	                                                <th scope="row">Hospital Code</th>
	                                                <td><label class="label_basic" id="HOS"></label></td>
	                                                <th scope="row">Registration No.</th>
	                                                <td><label class="label_basic" id="JNO"></label></td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                <!-- class="tbl_type" -->
	                                <div  style="width:100%;" > <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
	                                	<!-- 
	                                	<div id="realgrid2"  class="realgridH"></div>
	                                	 -->	
	                                	 <div class="realgridH" id="rstTableDiv" style="cursor:default;">
	                                	 <table class="table table-bordered table-condensed tbl_c-type tbl_type" width="100%" id="rstResultTable">
	                                	 	<colgroup>
	                                	 		<%-- <col width="20px" />
	                                	 		<col width="90px"/>
	                                	 		<col width="130px"/>
	                                	 		<col width="140px"/>
	                                	 		<col width="140px"/>
	                                	 		<col width="40px"/>
	                                	 		<col width="90px">
	                                	 		<col width="60px"/> --%>
	                                	 		<col width="5%" />
	                                	 		<col width="12%"/>
	                                	 		<col width="19%"/>
	                                	 		<col width="18%"/>
	                                	 		<col width="24%"/>
	                                	 		<col width="7%"/>
	                                	 		<col width="15%">
	                                	 		<%-- <col width="9%"/> --%>
	                                	 	</colgroup>
	                                	 	<thead>
	                                	 		<tr style="font-weight: bold; background-color: #fafafa">
	                                	 			<td><input type="checkbox" id="resCheck"></td>
	                                	 			<td>Insurance code</td>
	                                	 			<td>Test Name</td>
	                                	 			<td>Result</td>
	                                	 			<td>Reference Value</td>
	                                	 			<td>H/L</td>
	                                	 			<td>Reporting Date</td>
	                                	 			<!-- <td>시계열</td> -->
	                                	 		</tr>
	                                	 	</thead>
	                                	 	<tbody id="rstResultBody" name="rstResultBody">
	                                	 	</tbody>
	                                	 </table>
										 </div>
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




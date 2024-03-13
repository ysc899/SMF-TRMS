<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1">
<title>::씨젠의료재단 모바일::</title>
	<%@ include file="/inc/mobile-common.inc"%>
	
	
	<%
		String Param_HOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_HOS"), "");
		String Param_FDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FDT"), "");
		String Param_TDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_TDT"), "");
	%>
	
	<script type="text/javascript" src="/plugins/mobile/underscore-master/underscore.js"></script>
	
</head>
<body>



	<div id="app_wrap">
		<%@ include file="../include/header_login.jsp"%>
		
		<!--container-->
		<div id="container" style="margin-top:50px;height:600px;">
			<div class="covid_area">
				<h2>결과보고</h2>
				<form id="searchForm" name="searchForm">
					<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
					<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->
					<input id="I_ID" 		name="I_ID" 	type="hidden" />	<!-- 로그인한 사용자 ID -->
					<input id="I_ECF" 		name="I_ECF" 	type="hidden" />	<!-- 사원/파견사원 구분 -->
					
					<table class="covid_table01">
					  <thead></thead>
					  <tbody>
						<tr>
						  <td><b>접수일자</b></td>
						  <td style="width:75%;">
			
							<input type="text" class="fr_date startDt calendar_put input_type1" id="I_FDT" name="I_FDT" ></input>
							<!-- <img src="./images/mobile/date_ico.jpg" alt="달력아이콘"> -->
							<i>~</i>
							<input type="text" class="fr_date calendar_put input_type1" id="I_TDT" name="I_TDT" ></input>
							<!-- <img src="./images/mobile/date_ico.jpg" alt="달력아이콘"> -->
			
						  </td>
<!-- 						  <td class="td_bt" id="btnSearch" ><span calss="search">검색</span></td> -->
						</tr>
			
						<tr>
						  <td><b>병원</b></td>
						  <td rowspan"2">
			  				<input id="I_HOS" name="I_HOS" type="hidden"  class="srch_put numberOnly srch_hos"  value="<%=ss_hos%>" readonly="readonly" maxlength="5" class="numberOnly"/>
						  	<input type="text" class="srch_put hos_pop_srch_ico input_type2 _btnSelectHospital" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly"  placeholder="터치하여 병원을 선택 하세요" >
					  	  </td>
<!-- 						  <td class="td_bt _btnSelectHospital"><span class="choice">선택</span></td> -->
						</tr>
						<tr style="text-align: center;">
							<td colspan="2"  style="padding-top:12px;">
								<span class="search" id="btnSearch" style="padding-left:20px;padding-right:20px;" >검&nbsp;&nbsp;색</span>
							</td>
						</tr>
					  </tbody>
					</table>
				</form>
		
				<div class="covid_table02_box">
				  <table class="covid_table02">
					<colgroup>
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					</colgroup>
					<thead>
					  <tr class="table_sticky">
						<th rowspan="2" class="sticky-th" style="border-left:none;">&nbsp;&nbsp;</th>
						<th rowspan="2" class="sticky-th">병원코드</th>
						<th rowspan="2" class="sticky-th">보건소명</th>
						<th colspan="2" class="sticky-th">건수</th>
						<th colspan="4" class="sticky-th">최종보고 결과 건수</th>
						<th rowspan="2" class="sticky-th">검사중</th>
						<th rowspan="2" class="sticky-th" style="border-right:none;">재검중</th>
					  </tr>
		
					  <tr class="table_sticky">
						<th class="sticky-two two_null1">접수건수</th>
						<th class="sticky-two">검사건수</th>
						<th class="sticky-two">양성</th>
						<th class="sticky-two">미결정</th>
						<th class="sticky-two">음성</th>
						<th class="sticky-two two_null2">계</th>
					  </tr>
					</thead>
		
					<tbody id="tbodyCorona1">
		
					</tbody>
		
					<tfoot id="tfootCorona1" style="border-bottom:none;">
					</tfoot>
				  </table>
				</div>
		
				<div class="covid_table03_box" style="min-height:140px;">
				  <table class="covid_table03">
					<colgroup>
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					</colgroup>
					<thead>
					  <tr class="table_sticky">
						<th rowspan="2" class="sticky-th" style="border-left:none;">&nbsp;&nbsp;</th>
						<th rowspan="2" class="sticky-th">병원코드</th>
						<th rowspan="2" class="sticky-th">보건소명</th>
						<th rowspan="2" class="sticky-th">양성분류</th>
						<!-- <th rowspan="2" class="sticky-th">환자목록</th> -->
						<th rowspan="2" class="sticky-th">환자명</th>
						<th rowspan="2" class="sticky-th">의뢰일</th>
						<th rowspan="2" class="sticky-th">생년월일</th>
						<th rowspan="2" class="sticky-th">기타기록</th>
						<th rowspan="2" class="sticky-th">핸드폰번호</th>
						<th colspan="3" class="sticky-th" style="border-right:none;">CT Value</th>
					  </tr>
		
					  <tr class="table_sticky">
						<th class="sticky-two two_null1">E gene</th>
						<th class="sticky-two">RdRP/S gene</th>
						<th class="sticky-two two_null2">N gene</th>
					  </tr>
					</thead>
		
					<tbody id="tbodyCorona2">
		
					</tbody>
		
					<tfoot style="border-bottom:none;">
<!-- 					  <tr> -->
<!-- 						<td colspan="10" style="font-size:12px;">검색결과가 없습니다.</td> -->
<!-- 					  </tr> -->
					</tfoot>
				  </table>
				</div>
		
		
			  </div>
			
		</div>
		<!--//container End-->
		
		<%@ include file="../include/navigation.jsp"%>
	</div>
	
	<div class="modal_popup" style="display:none;">
	  <div class="black_bg _close">
	  </div>

	  <div class="search_hospital_popup">
		<div class="title">
		  <span>병원조회</span>
		  <img src="/images/mobile/x.png" alt="닫기" class="x _close">
		</div>

		<div class="inner_search">

			<form id="popupSearchForm" name="searchForm">
				<input id="POPUP_I_LOGMNU" name="I_LOGMNU" type="hidden" />
				<input id="POPUP_I_LOGMNM" name="I_LOGMNM" type="hidden" />
				<table>		
					<colgroup>
					  <col width="50%">
					  <col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>병원코드</th>
							<td><input type="text" style="max-width:200px;" class="engNumOnly" id="POPUP_I_HOS" name="I_HOS" maxlength="5"></td>
						</tr>
		
						<tr>
							<th>병원명</th>
							<td><input type="text" style="max-width:200px;" id="POPUP_I_FNM" name="I_FNM" maxlength="30"></td>
						</tr>
					</tbody>
				</table>
			</form>

			<span class="modal_search_bt" id="popupSearch">조회</span>
		</div>

		<div class="innertable">
		  <table>
			<colgroup>
			  <col width="10%">
			  <col width="20%">
			  <col width="50%">
			  <col width="30%">
			</colgroup>
			<thead>
			  <tr>
				<th class="stick-th"></th>
				<th class="stick-th">병원코드</th>
				<th class="stick-th">병원명</th>
				<th class="stick-th">담당자</th>
			  </tr>
			</thead>

			<tbody id="tbodyPopupSearch">
			</tbody>
		  </table>

		</div>

		<span class="close_bt _close">닫기</span>
	  </div>
	</div>
	
</body>

<script type="text/javascript">

	var I_LOGMNU = "M_RECVREPORTCORONA";
	var I_LOGMNM = "모바일 코로나 결과보고";
	var Param_HOS = "<%=Param_HOS%>";
	var Param_FDT = "<%=Param_FDT%>";
	var Param_TDT = "<%=Param_TDT%>";
	var s_uid = "<%= ss_uid %>";
	var s_ecf = "<%= ss_ecf %>";
	var s_hos = "<%= ss_hos %>";
	var s_nam = "<%= ss_nam %>";
	var s_paguen_hos_yn = "<%= ss_paguen_hos_yn %>";	/* 파견사원 병원유무 Flag */
	
	
	
	function search(){
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
 			
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
 		
			// 2020.04.21 (수)
 			// 검색초기화 후, 조회를 할 경우
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
 		if(!searchValidation()) {return false;}
 		
 		dataResult1();
	}
	
	function searchValidation(){
		
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		var interval = eDate - sDate;
		var day = 1000*60*60*24;
		
		//if (interval/day > 365){
		if (interval/day > 30){
   			CallMessage("298","alert");		//최대 30일 내에서 조회해주세요.
			return false;
		}
		
		// 변경전 : 검색조건에 병원코드가 입력되지 않으면 검색이 불가능함.
		// 변경후 : 검색조건에 병원코드가 입력되지 않으면서 사원인 경우만 검색이 불가능 하도록 변경. 파견사원의 경우, 병원코드가 비어있어도 검색이 되도록 하기 위해서.
		if($('#I_HOS').val() == "" && $('#I_ECF').val() =="E" && s_paguen_hos_yn == ""){
			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
			return false;
		}
		
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			CallMessage("273", "alert");
		   return false;
		}
		
		if(sDate > eDate){
			CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
			return false;
		}
		  
		return true;
		
	}
		
	// 데이터 가져오기
	function dataResult1(){
		callLoading(null,"on");
		$(".blockPage").css('margin-left', '-125px');
		
		var formData = $("#searchForm").serializeArray();
		
		if(isIOS()){
			// 2022-02-23 수정
			//ajaxCall("/mobileRecvReportCoronaList1.do", formData,false);
			ajaxCall("/recvReportCoronaList.do", formData,false);
		}else{
			ajaxCall("/recvReportCoronaList.do", formData,false);
		}
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		
		console.log('1111');
		console.log(strUrl);
		
		if(strUrl == "/recvReportCoronaList.do"){
				
			callLoading(null,"off");
			
			if(!isNull( response.recvReportCoronaList_1) || !isNull( response.recvReportCoronaList_2)){
				
				var result1Html = '';
				
				
				for(var idx in response.recvReportCoronaList_1){
					
					var item = response.recvReportCoronaList_1[idx];
					
					if(Number(idx) < (response.recvReportCoronaList_1.length -1) ){
					
						result1Html += '<tr>';
					}else{
						result1Html += '<tr style="border-bottom:none;">';
					}
						
					result1Html += '	<td>' + (Number(idx)+1) +'</td>'
					result1Html += '	<td>' + item.HOSCD +'</td>'
					result1Html += '	<td>' + item.HOSNM +'</td>'
					result1Html += '	<td>' + (item.JSCNT ? item.JSCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.GSCNT ? item.GSCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.NECNT ? item.NECNT : '0') +'</td>'
					result1Html += '	<td>' + (item.INCNT ? item.INCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.POCNT ? item.POCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.RSCNT ? item.RSCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.INGCNT ? item.INGCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.RECNT ? item.RECNT : '0') +'</td>'
					result1Html += '</tr>';
					
				}
				
				
				$("#tbodyCorona1").html(result1Html);
				
				var rowNum = 1;
				var result2Html = '';
				
				//그룹핑 후 처리
				var hoscdGroup = _.groupBy(response.recvReportCoronaList_2, function(it){
					return it.HOSCD;
				});
				
				for(var idx in hoscdGroup){
					var item = hoscdGroup[idx];
					var parentFirstFlag = true;
					
					
					var subGroup = _.groupBy(item, function(it){
						return it.GUBUN;
					});
					
					
					for(var idx2 in subGroup){
						var item2 = subGroup[idx2];
						var secondFirstFlag = true;
						
						for(var idx3 in item2){
							var item3 = item2[idx3];
							
							result2Html += '<tr>';
							result2Html += '	<td>' + rowNum +'</td>';
							rowNum++;
							if(parentFirstFlag){
								result2Html += '	<td rowspan="'+ item.length +'">' + item[0].HOSCD +'</td>';
								result2Html += '	<td rowspan="'+ item.length +'">' + item[0].HOSNM +'</td>';
								parentFirstFlag = false;
							}
							
							if(secondFirstFlag){
								result2Html += '	<td rowspan="'+ item2.length +'">' + (item2[0].GUBUN ? item2[0].GUBUN : '') +'</td>';
								secondFirstFlag = false;
							}
							/* result2Html += '	<td>' + item3.PATIENTNM +'</td>'; */
							result2Html += '	<td>' + item3.NAM +'</td>';
							result2Html += '	<td>' + item3.DAT +'</td>';
							result2Html += '	<td>' + item3.JN12 +'</td>';
							result2Html += '	<td>' + item3.ETC +'</td>';
							result2Html += '	<td>' + item3.PNO +'</td>';
							result2Html += '	<td>' + item3.EGN +'</td>';
							result2Html += '	<td>' + item3.RGN +'</td>';
							result2Html += '	<td>' + item3.NGN +'</td>';
							result2Html += '</tr>';
						}
					}
				}
				
				$("#tbodyCorona2").html(result2Html);
				
				
			}else{
				var messge = "접수일자 ";// 보고일자 , 접수일자
				messge += $("#I_FDT").val() + " ~ " + $("#I_TDT").val() +" ";
				
				CallMessage("291","alert",[messge]);//결과가 존재하지 않습니다.
			}
		}else if(strUrl == "/mobileRecvReportCoronaList1.do"){
				
			callLoading(null,"off");
			
			if(!isNull( response.recvReportCoronaList_1) || !isNull( response.recvReportCoronaList_2)){
				
				var result1Html = '';
				
				
				for(var idx in response.recvReportCoronaList_1){
					
					var item = response.recvReportCoronaList_1[idx];
					
					if(Number(idx) < (response.recvReportCoronaList_1.length -1) ){
					
						result1Html += '<tr>';
					}else{
						result1Html += '<tr style="border-bottom:none;">';
					}
						
					result1Html += '	<td>' + (Number(idx)+1) +'</td>'
					result1Html += '	<td>' + item.HOSCD +'</td>'
					result1Html += '	<td>' + item.HOSNM +'</td>'
					result1Html += '	<td>' + (item.JSCNT ? item.JSCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.GSCNT ? item.GSCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.NECNT ? item.NECNT : '0') +'</td>'
					result1Html += '	<td>' + (item.INCNT ? item.INCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.POCNT ? item.POCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.RSCNT ? item.RSCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.INGCNT ? item.INGCNT : '0') +'</td>'
					result1Html += '	<td>' + (item.RECNT ? item.RECNT : '0') +'</td>'
					result1Html += '</tr>';
					
				}
				
				$("#tbodyCorona1").html(result1Html);
				
				callLoading(null,"on");
				$(".blockPage").css('margin-left', '-125px');
				
				var formData = $("#searchForm").serializeArray();
				
				ajaxCall("/mobileRecvReportCoronaList2.do", formData,false);
				
				
			}else{
				var messge = "접수일자 ";// 보고일자 , 접수일자
				messge += $("#I_FDT").val() + " ~ " + $("#I_TDT").val() +" ";
				
				CallMessage("291","alert",[messge]);//결과가 존재하지 않습니다.
			}
		}else if(strUrl == "/mobileRecvReportCoronaList2.do"){
			callLoading(null,"off");
			
			if(!isNull( response.recvReportCoronaList_2)){
				
				var rowNum = 1;
				var result2Html = '';
				
				//그룹핑 후 처리
				var hoscdGroup = _.groupBy(response.recvReportCoronaList_2, function(it){
					return it.HOSCD;
				});
				
				for(var idx in hoscdGroup){
					var item = hoscdGroup[idx];
					var parentFirstFlag = true;
					
					
					var subGroup = _.groupBy(item, function(it){
						return it.GUBUN;
					});
					
					
					for(var idx2 in subGroup){
						var item2 = subGroup[idx2];
						var secondFirstFlag = true;
						
						for(var idx3 in item2){
							var item3 = item2[idx3];
							
							result2Html += '<tr>';
							result2Html += '	<td>' + rowNum +'</td>';
							rowNum++;
							if(parentFirstFlag){
								result2Html += '	<td rowspan="'+ item.length +'">' + item[0].HOSCD +'</td>';
								result2Html += '	<td rowspan="'+ item.length +'">' + item[0].HOSNM +'</td>';
								parentFirstFlag = false;
							}
							
							if(secondFirstFlag){
								result2Html += '	<td rowspan="'+ item2.length +'">' + (item2[0].GUBUN ? item2[0].GUBUN : '') +'</td>';
								secondFirstFlag = false;
							}
							/* result2Html += '	<td>' + item3.PATIENTNM +'</td>'; */
							result2Html += '	<td>' + item3.NAM +'</td>';
							result2Html += '	<td>' + item3.DAT +'</td>';
							result2Html += '	<td>' + item3.JN12 +'</td>';
							result2Html += '	<td>' + item3.ETC +'</td>';
							result2Html += '	<td>' + item3.PNO +'</td>';
							result2Html += '	<td>' + item3.EGN +'</td>';
							result2Html += '	<td>' + item3.RGN +'</td>';
							result2Html += '	<td>' + item3.NGN +'</td>';
							result2Html += '</tr>';
						}
					}
				}
				
				$("#tbodyCorona2").html(result2Html);
				
				
			}else{
				var messge = "접수일자 ";// 보고일자 , 접수일자
				messge += $("#I_FDT").val() + " ~ " + $("#I_TDT").val() +" ";
				
				CallMessage("291","alert",[messge]);//결과가 존재하지 않습니다.
			}
		}else if(strUrl == "/hospSearchSList.do"){
			//병원 검색
			var popupResultHtml = '';
			for(var idx in response.resultList){
				var item = response.resultList[idx];
				
				popupResultHtml += '<tr class="_popupRow" data-pcd="'+ item.F120PCD +'" data-fnm="' + item.F120FNM + '">';
				popupResultHtml += ' <td>'+ (Number(idx) + 1) +'</td>';
				popupResultHtml += ' <td>'+ item.F120PCD +'</td>';
				popupResultHtml += ' <td>'+ item.F120FNM +'</td>';
				popupResultHtml += ' <td>'+ item.F910NAM +'</td>';
				popupResultHtml += '</tr>';
			}
			
			$("#tbodyPopupSearch").html(popupResultHtml);
			
			if(response.resultList.length == 0){
				alert('검색 결과가 없습니다.');
			}
			
		}
	}
	
	
	function showHospitalSearchPopup(){
		$(".modal_popup").fadeIn();
		$(".black_bg").height($(window).height());
	}
	
	function searchHospitalSearch(){
		
		strUrl = "/hospSearchSList.do";
		var formData = $("#popupSearchForm").serialize();
		ajaxCall(strUrl, formData);
	}

	$(document).on('click', '._popupRow', function(){

		$("#I_HOS").val($(this).data('pcd'));
		$("#I_FNM").val($(this).data('fnm'));
		
		$("._close").trigger('click');
		
	});
	


	$(document).ready(function(){
		
		
		var wh = $(window).height() > $(window).width() ? $(window).height() : $(window).width();
		var h =  window.innerHeight > window.innerWidth ? window.innerHeight : window.innerWidth;
		
		
		var table1height = h * 0.23;
		
		$(".covid_table02_box").height(table1height);
		
		var table2height = wh - (table1height + $(".covid_table02_box").offset().top + $("#navigation").height()) - 20;
		
		
		
		
		
// 		$(".covid_table03_box").height(window.innerHeight * 0.28);
//		$(".covid_table03_box").height(table2height);
		$(".covid_table03_box").height(table2height-100);	// 2021.12.17 - A90 모델에서 화면 아래 부분이 잘려보인다는 지점 문의로 수정함 
		
		$("#container").css('margin-bottom', $("#navigation").height());
		
		$("#I_ID").val(s_uid);
		$("#I_ECF").val(s_ecf);
		$('#I_HOS').val(s_hos);
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		//모바일 팝업 검색 메뉴명 고정
		$("#POPUP_I_LOGMNU").val("MOBILEWEB");
		$("#POPUP_I_LOGMNM").val(I_LOGMNM);
		
		$(".area_name").html(s_nam+'님');
		
		
		// 병원 사용자 && 파견사원이 아닌 경우
		if(s_ecf != "E" && s_paguen_hos_yn == ""){
			$('#I_FNM').val(s_nam);
		}
		
		
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
		yearAgo.setDate(yearAgo.getDate() - 1);
		
		$("#I_FDT").datepicker('setDate',yearAgo);
		$("#I_TDT").datepicker('setDate',new Date);
		
	 	// 처음 수진자별 결과조회 화면이 열릴 때, 파견사원 병원인 경우는 병원코드 입력값을 빈값으로 셋팅한다.
		// (이유는 파견사원 병원은 1개 계정으로 N개 병원 검사결과 데이터를 조회해야하므로,
		// 병원코드 값이 존재하지 않아야 N개 병원 검사결과 데이터가 모두 조회 가능하다.)
		if(s_paguen_hos_yn != ""){
			$("#I_HOS").val('');			
		}
		
		/** 메인에서 들어올경우 조회 조건 셋팅 **/
	   	if(!isNull(Param_HOS)){		// 사원/파견사용자인 경우
	   		
			$("#I_HOS").val(Param_HOS);	 // 병원코드
			
			Param_FDT = Param_FDT.substring(0,4)+"-"+Param_FDT.substring(4,6)+"-"+Param_FDT.substring(6,8); 
			Param_TDT = Param_TDT.substring(0,4)+"-"+Param_TDT.substring(4,6)+"-"+Param_TDT.substring(6,8);
			
			$("#I_FDT").datepicker('setDate', Param_FDT);		// 접수일자 - 시작
			$("#I_TDT").datepicker('setDate', Param_TDT);	 	// 접수일자 - 종료
				
		}
		
		
		
		$("#btnSearch").click(function(){
			search();
		});
		
		$("._btnSelectHospital").click(function(){
			showHospitalSearchPopup();
		});
		
		$("#popupSearch").click(function(){
			searchHospitalSearch();
		});
		
		$("._close").click(function(){
			$('.modal_popup').fadeOut();
		});
		
		
		
	});
</script>

</html>
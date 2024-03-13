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
	
	<script type="text/javascript" src="/plugins/mobile/underscore-master/underscore.js"></script>
	
</head>
<body>



	<div id="app_wrap">
		<%@ include file="../include/header_login.jsp"%>
		
		<!--container-->
		<div id="container"  style="margin-top:50px;">
			<div class="result_area">
				<h2>검사결과 조회</h2>
			
				<form id="searchForm" name="searchForm" onsubmit="return false" target="_self">
					<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
					<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
					<input id="I_ICNT" name="I_ICNT" type="hidden"> 				  	   <!-- 그리드에 보여줄 건수 -->
					<input id="I_CNT" name="I_CNT" value="40" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
					<input id="I_ROW" name="I_ROW" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
					<input id="I_IROW" name="I_IROW" value="0" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
					
					<input id="I_PECF" name="I_PECF" type="hidden"> 			   
					<input id="I_PNN" name="I_PNN" type="hidden">
					
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
					
					
					<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  시작-->
					<!-- C:최초 조회, N:다음, P:이전 -->
					<input id="I_IGBN" name="I_IGBN" type="hidden">
					<!-- 이전/다음 시작 접수일자 : 최초 조회일 경우 0 -->
					<input id="I_IDAT" name="I_IDAT" type="hidden">
					<!-- 이전/다음 시작 접수번호 : 최초 조회일 경우 0 -->
					<input id="I_IJNO" name="I_IJNO" type="hidden">
					<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  끝-->
					
					
					<input id="I_DGN" name="I_DGN" type="hidden">
					
					<!-- 2022.01.03 - 로그인한 유저 권한 -->
					<input id="loginUser_authority" name="loginUser_authority" type="hidden" value="<%=ss_agr%>">
					
					<table class="result_table01">
					  <tbody>
						<tr>
						  <td><b>접수일자</b></td>
						  <td style="width:75%;">
							<input type="text" class="fr_date startDt calendar_put input_type1" id="I_FDT" name="I_FDT"  ></input>
							<!-- <img src="./images/mobile/date_ico.jpg" alt="달력아이콘"> -->
							<i>~</i>
							<input type="text" class="fr_date calendar_put input_type1" id="I_TDT" name="I_TDT" ></input>
							<!-- <img src="./images/mobile/date_ico.jpg" alt="달력아이콘"> -->
						  </td>
<!-- 						  <td class="td_bt" id="btnSearch"><span calss="search">검색</span></td> -->
						</tr>
			
						<tr>
						  <td><b>병원</b></td>
						  <td>
						  	<input id="I_PHOS" name="I_PHOS" type="hidden"  value="<%=ss_hos%>" readonly="readonly"  placeholder="" class="srch_put numberOnly srch_hos"/>
							<input type="text" class="input_type2 srch_put hos_pop_srch_ico _btnSelectHospital" id="I_FNM" name="I_FNM" readonly="readonly"  placeholder="터치하여 병원을 선택 하세요" >
						  </td>
			
<!-- 						  <td class="td_bt"><span class="choice _btnSelectHospital">선택</span></td> -->
						</tr>
						<tr>
						  <td>
						  	<select id="I_DPN" name="I_DPN" class="search_option" onchange="javascript:selectChange()">
								<option value="I_NAM" selected="selected">수진자명</option>
								<option value="I_PCHN">차트번호</option>
							</select>
						  </td>
						  <td>
							<div class="_searchOption _iNam">
								<input type="text" id="I_NAM" name="I_NAM" maxlength="14" placeholder="수진자명을 입력하세요" class="srch_put input_type2" >
							</div>
							<div class="_searchOption _iPchn" style="display:none;">
								<input type="text" id="I_PCHN" name="I_PCHN" title="차트번호" placeholder="차트번호를 입력하세요" maxlength="15" class="srch_put input_type2"  >
							</div>
						  </td>
<!-- 						  <td></td> -->
						</tr>
			
						<tr>
						  <td><b>생년월일</b></td>
						  <td>
						  	<input type="number" class="input_type3 srch_put" id="I_BIRTH" name="I_BIRTH" placeholder="881231 형식으로 입력하세요" maxlength="6" pattern="[0-9]+"> <!-- 생년월일 -->
						  </td>
<!-- 						  <td></td> -->
						</tr>
			
						<tr>
						  <td><b style="font-size:12px;">양성 여부</b></td>
						  <td>
						  	<select id="I_CORONA" name="I_CORONA" class="covid_result">
							</select>
						  </td>
<!-- 						  <td></td> -->
						</tr>
						<tr style="text-align: center;">
							<td colspan="2"  style="padding-top:12px;">
								<span class="search" id="btnSearch" style="padding-left:20px;padding-right:20px;" >검&nbsp;&nbsp;색</span>
							</td>
						</tr>
						
					  </tbody>
					</table>
				</form>
				
				<div class="result_table02_box">
				  <table class="result_table02">
					<colgroup>
					  <col width="">
					  <col width="">
					  <col width="">
					  <col width="">
					</colgroup>
					<thead>
					  <tr class="table_sticky">
						<th class="sticky-th _sort" data-sort="DAT" style="border-left:none;">접수일자  <i class="glyphicon glyphicon-arrow-up _DAT_up" style="display:none;"></i> <i class="glyphicon glyphicon-arrow-down _DAT_down" style="display:none;"></i> </th>
						<th class="sticky-th _sort" data-sort="CHN">차트번호 <i class="glyphicon glyphicon-arrow-up _CHN_up" style="display:none;"></i> <i class="glyphicon glyphicon-arrow-down _CHN_down" style="display:none;"></i></th>
						<th class="sticky-th _sort" data-sort="NAM">환자명 <i class="glyphicon glyphicon-arrow-up _NAM_up" style="display:none;"></i> <i class="glyphicon glyphicon-arrow-down _NAM_down" style="display:none;"></i></th>
						<th class="sticky-th _sort" data-sort="STSD" style="border-right:none;">상태 <i class="glyphicon glyphicon-arrow-up _STSD_up" style="display:none;"></i> <i class="glyphicon glyphicon-arrow-down _STSD_down" style="display:none;"></i></th>
					  </tr>
					</thead>
		
					<tbody id="tbodyResult">
		
		
					</tbody>
		
					<tfoot style="border-bottom:none;">
					  <tr>
						<td colspan="10" style="font-size:12px;"></td>
					  </tr>
					</tfoot>
				  </table>

				<span class="more_view" style="display:none;">+ 더보기</span>
			
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
							<th>병원코드 </th>
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

	var I_LOGMNU = "M_RSTUSERTABLE";
	var I_LOGMNM = "모바일 수진자별 결과관리(Table)";
	
	var s_uid = "<%= ss_uid %>";
   	var s_ip =  "<%= ss_ip %>";
   	var s_cor = "<%= ss_cor %>";
   	
   	var s_ecf = "<%= ss_ecf %>";
   	var s_hos = "<%= ss_hos %>";
   	var s_nam = "<%= ss_nam %>";
   	
   	var s_syn = "<%= ss_syn %>";
   	
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
   	
   	
   	//페이징 카운트
   var page_count = 0;		// 페이지 확인 0이면 첫 페이지
   var endData = {};
   	
   var dataList = [];
   var sortMode = "";
   var sortType = false;
   
   	
	function selectChange(){
		
		var data = $('#I_DPN option:selected').val()
		
		$("._searchOption").hide();

		$("#I_PCHN").val('');
		$("#I_NAM").val('');

		
		if(data == 'I_PCHN'){
			$("._iPchn").show();
		}else{
			$("._iNam").show();
		}
		
		
// 		var maxlength = 8;
		
// 		if(data == "I_PCHN"){
// 			maxlength = 15;
// 		}else if(data == "I_NAM"){
// 			maxlength = 14;
// 		}else if(data == "I_DGN"){
// 			$('.I_DPN').addClass('numberOnly');
// 		}
			
// 		$('.I_DPN').attr({
// 			'id' : data,
// 			'name' : data,
// 			'maxlength' : maxlength
// 		});
	}
	
	function searchValidation(){
		
		var I_DPN = $('#I_DPN option:selected').val()
		//날짜를 시작일 종료일 선택으로 변경
		//console.log('vali');
// 		if(I_DPN == "I_DGN"){
// 			//console.log(1);
// 			// 데이터가 날짜 형식 아닐 경우
// 			var I_DT = $('#I_DGN').val();
// 			if((I_DT.length < 8 && I_DT.length > 0) && !isNull(I_DT)){
// 				CallMessage("229", "alert","", dataClean);
// 				return false;
// 			}
			
// 		}else 
		if(I_DPN == "I_PCHN"){
			//console.log(2);
			if(strByteLength($("#I_PCHN").val()) > 15){
				CallMessage("292", "alert", ["차트번호는 15 Byte"], dataClean);
				return false;
			}
		}
		
		
		//I_PINQ = Y ==> 수진자명，챠트번호　조건만 들어가는 기본 조건
		if(
				(
						$('#I_DGN').val() == "P" 	// 기간구분(접수일자:D/보고일자:P)
					|| $('#I_PETC').val() != ""		// 검체번호 	
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
						&& $('#CHN').html("") != ""
						&& $('#I_BIRTH').val() == ""// 생년월일
				)
				
		){
			$("#I_PINQ").val("N");
			/* if (interval/day > 365 || isNaN(interval)){
				if($('#I_DGN').val() == "P"){
					CallMessage("294","alert",["보고일자로"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.
				}else{
					CallMessage("274","alert","", dataClean);		//최대 1년 내에서 조회해주세요.
				}
				
				return false;
			} */
			
		} else {
			$("#I_PINQ").val("Y");
		}
		
		
		return true;
	}
	
	
	//조회
	function search(){
		//상세 초기화
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

	var loding = false;
	function dataResult(data){
		//상세 초기화
// 		$('#rstUserBody').empty();
		
		
		var formData = $("#searchForm").serialize();
		
		if(isNull(data)){
			data = "&I_DAT="+"";
			data += "&I_JNO="+"";
		}
		var I_TDT = "";
		var I_FDT = "";
		if(!isNull($('#I_DGN').val())){
			I_TDT = $('#I_DGN').val();
			I_TDT = I_TDT.substring(0,4)+"-"+I_TDT.substring(4,6)+"-"+I_TDT.substring(6,8);
			var I_DT = new Date(I_TDT);
			I_DT.setDate(I_DT.getDate() - 90);
			I_FDT = parseDate(I_DT.yyyymmdd());
		}
		data += "&I_FDT=" + I_FDT;
		data += "&I_TDT=" + I_TDT;
		formData += data;
		
		
		callLoading(null, "on");
		$(".blockPage").css('margin-left', '-125px');
		//console.log(formData);
// 		ajaxCall("/rstUserListMini.do", formData, false);
		ajaxCall("/rstUserList.do", formData, false);
	}
	
	function onResult(strUrl,response){
		
// 		tbodyResult
		
		//console.log('onResult1');
// 		if(strUrl == "/rstUserListMini.do"){
		if(strUrl == "/rstUserList.do"){
	
			callLoading(null,"off");
			
			$(".glyphicon").hide();
			mode = '';
			sortType = false;
			
			
			//상세 초기화
			loding = false;
			page_count++;
			
			var resultList =  response.resultList;
			
			var resultHtml = '';
			
			var d = new Date();
			
			var firstFlag = true;
 			for (var i in resultList){
 				if(i < 40){
 				
					if(resultList[i].PRF == "N"){
						resultList[i].PRF = "";
					}
					
					if(resultList[i].DAT != 0){
						resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
						
						/* // 2029년 데이터의 년도를 2020년 or 2021년으로 치환한다.
						if(resultList[i].DAT.substring(0,4) == "2029" && resultList[i].DAT.substring(4,6) == "12")
							resultList[i].DAT2 = resultList[i].DAT.substring(0,4).replace("2029","2020")+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
						else if(resultList[i].DAT.substring(0,4) == "2029" && resultList[i].DAT.substring(4,6) != "12"){
							resultList[i].DAT2 = resultList[i].DAT.substring(0,4).replace("2029","2021")+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
						}else{
							resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8)
						} */
						/* IDR 유승현 날짜형식변경 제거 2022.08.11
						resultList[i].DAT2 = resultList[i].DAT.substring(0,4).replace("2000","2020").replace("2027","2021").replace("2028","2021").replace("2029","2021").replace("2030","2021").replace("2023","2022").replace("2024","2022").replace("2025","2022").replace("2026","2022").replace("2037","2022").replace("2038","2022").replace("2039","2022")
											+"-"+resultList[i].DAT.substring(4,6)
											+"-"+resultList[i].DAT.substring(6,8);
						*/
						
					}
					
					var item = resultList[i];
					
					if(firstFlag){
						firstFlag = false;
						resultHtml += '	<tr class="_row _pagging_'+ page_count +'" data-dat="'+ item.DAT +'" data-jno="'+ item.JNO +'" data-idx="'+ i +'">';
					}else{
						resultHtml += '	<tr class="_row" data-dat="'+ item.DAT +'" data-jno="'+ item.JNO +'" data-idx="'+ i +'">';
					}
					resultHtml += '       <td>'+ item.DAT2  + '</td>';
					resultHtml += '       <td>' + item.CHN + '</td>';
					resultHtml += '       <td style="text-align:left; padding-left:5px;">' + item.NAM + '</td>';
					resultHtml += '       <td style="text-align:center;">' + item.STSD + '</td>';
					resultHtml += '	</tr>';
					
					
					dataList.push(item);
 				}
				
				if(i == (resultList.length -1) ){
					endData.date = item.DAT;
					endData.jno = item.JNO;
				}
				
			}
 			
 			
 			$("#tbodyResult").append(resultHtml);
 			
 			if(resultList.length > 0){
	 			setTimeout(function(){
	 				var offset = $("._pagging_"+page_count).offset();  
	 				$('html, body').animate({scrollTop : (offset.top -30) }, 400);
				}, 250);
	 			
	 			if(resultList.length < $('#I_ICNT').val()){					// 보여줄 건수보다 가져온 건수가 적으면 다음 페이지 버튼 숨김
	 				$(".more_view").hide();
	 			}else{
	 				$(".more_view").show();
	 			}
 			}else{
 				alert('검색 결과가 없습니다.');
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

		$("#I_PHOS").val($(this).data('pcd'));
		$("#I_HOS").val($(this).data('pcd'));
		$("#I_FNM").val($(this).data('fnm'));
		
// 		$("#I_DGN").val('D');
		
		$("._close").trigger('click');
		
	});

	
	$(document).on('click', '._row', function(){

		$('#I_DTLDAT').val($(this).data('dat'));
		$('#I_DTLJNO').val($(this).data('jno'));
		
		rstformData = document.searchForm.searchForm;
		
		window.open("/mobileRstUserTableMini02.do", "rstPopup", "width=100%, height=100%, scrollbars=no, resizable=no");

		rstformData.target = "rstPopup";
// 		rstformData.target = "_self";
		rstformData.action = "/mobileRstUserTableMini02.do";
		rstformData.method = "post";
		rstformData.submit();
		
	});

	$(document).ready(function(){
		
		$(".area_name").html(s_nam+'님');
		
		$('#I_PECF').val(s_ecf);
		$('#I_PHOS').val(s_hos);
		if(s_ecf != "E"){
			$('#I_FNM').val(s_nam);
		} 
		
  		//사원일경우 병원정보 초기화 or 파견사원 병원인 경우 초기화
  		if($('#I_PECF').val() == "E" || s_paguen_hos_yn != ""){
  			$('#I_PHOS').val("");
  	  		$('#I_FNM').val("");
		}
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		//모바일 팝업 검색 메뉴명 고정
		$("#POPUP_I_LOGMNU").val("MOBILEWEB");
		$("#POPUP_I_LOGMNM").val(I_LOGMNM);
		
		//달력 셋팅
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
		yearAgo.setDate(yearAgo.getDate() - 3);
		
		$("#I_FDT").datepicker('setDate',yearAgo);
		$("#I_TDT").datepicker('setDate',new Date);
		
		
		
		//코로나 결과(양성여부)
		var resultCode4 = "";
		resultCode4 = getCode(I_LOGMNU, I_LOGMNM, "CORONA_RESULT", 'Y');
		$('#I_CORONA').html(resultCode4);
		
		
		$("#btnSearch").click(function(){
			
			dataList = [];
			page_count = 0;
			$(".more_view").hide();
			$("#tbodyResult").empty();
			
			
			search();
		});
		
		$(".more_view").click(function(){
			
			var page = Number($('#I_CNT').val());
			var row = Number($('#I_ROW').val());
			
			row = row + 40;
			$('#I_ROW').val(row);
			
			$('#I_IDAT').val(endData.date);
			//다음 페이지 시작 접수번호
			$('#I_IJNO').val(endData.jno);
			
			dataResult();
			
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
		
		
		$("._sort").click(function(){
			
			$(".glyphicon").hide();
			
			if(dataList.length == 0){
				return false;
			}
			
			var mode = $(this).data('sort');
			
			
			if(_.keys(_.groupBy(dataList, mode)).length <= 1){
				return false;
			}
			
			
			//기존이랑 동일 하면 반대껄로
			if(mode == sortMode){
				sortType = !sortType;
			}else{
				sortType = false;
			}
			
			sortMode = mode;
			var sortData = _.sortBy(dataList, mode);
			
			if(sortType){
				sortData = sortData.reverse();
			}
			
			$("#tbodyResult").empty();
			var resultHtml = '';
			
			sortData.forEach(function(item, idx){
				resultHtml += '	<tr class="_row" data-dat="'+ item.DAT +'" data-jno="'+ item.JNO +'" data-idx="'+ idx +'">';
				resultHtml += '       <td>'+ item.DAT2  + '</td>';
				resultHtml += '       <td>' + item.CHN + '</td>';
				resultHtml += '       <td style="text-align:left; padding-left:5px;">' + item.NAM + '</td>';
				resultHtml += '       <td style="text-align:center;">' + item.STSD + '</td>';
				resultHtml += '	</tr>';
			});
			
			$("#tbodyResult").append(resultHtml);
			
			$("._"+sortMode +"_" + (sortType ? 'up': 'down')).show();
			
			
		});
		
	});
</script>

</html>
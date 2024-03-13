<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1">
<title>::씨젠의료재단 모바일::</title>
	<%@ include file="/inc/mobile-common.inc"%>
	
	
</head>
<body>



	<div id="app_wrap">
		<%@ include file="../include/header_fix.jsp"%>
		
		<!--container-->
		<div id="container" style="margin-top:20px; margin-bottom: 30%;">
			<div class="item_area">
				<h2>검사항목 조회</h2>
				
				<form id="searchForm" name="searchForm">
					<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
					<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->
					<input id="I_ALHNM" name="I_ALHNM" type="hidden"/>
					
					
					<table class="item_table01">
						<tbody>
						<tr>
							<td><b>검사항목</b></td>
							<td style="width:75%;">
							<select class="info_select" id="I_SERFNM"	name="I_SERFNM">
							</select>
							<input type="text" class="input_type1" id="I_SERNM" name="I_SERNM" maxlength="49" onkeydown="return enterSearch(event)"></input>
							</td>
						</tr>
			
						<tr>
							<td><b>학부분류</b></td>
							<td>
							<select class="info_kind" id="I_HAK"	name="I_HAK" >
							</select>
							</td>
						</tr>
						<tr style="text-align: center;">
							<td colspan="2"	style="padding-top:12px;">
								<span class="search" id="btnSearch" style="padding-left:20px;padding-right:20px;" >검&nbsp;&nbsp;색</span>
							</td>
						</tr>
						</tbody>
					</table>
					
				</form>
				
				<div class="item_table02_box">
					<table class="item_table02">
						<colgroup>
							<col width="50px;">
							<col width="*">
							<col width="25%">
							<col width="25%">
						</colgroup>
						<thead>

							<tr class="table_sticky">
								<th rowspan="2" class="sticky-th" style="border-left:none;">검사코드</th>
								<th rowspan="2" class="sticky-th">검사항목</th>
								<th class="sticky-one">검체</th>
								<th class="sticky-one" style="border-right:none;">검사법</th>
							</tr>

							<tr class="table_sticky">
								<th class="sticky-two">검사일정</th>
								<th class="sticky-two">소요일</th>
							</tr>

						</thead>

						<tbody id="tbodyOcsItems">
							<tr>
								<td colspan="4"><b>검색을 해주세요.</b></td>
							</tr>
						</tbody>

						<tfoot style="border-bottom:none;">
							<tr>
								<td colspan="10" style="font-size:12px;"></td>
							</tr>
						</tfoot>
					</table>

<!-- 					<span class="more_view">+ 더보기</span> -->
				</div>
		
			</div>
			
		</div>
		<!--//container End-->
		
		<form id="popupForm" name="popupForm">
			<input type="hidden" id="popupGcd" name="gcd"/>
		</form>
		
		<%@ include file="../include/navigation.jsp"%>
	</div>
	
</body>

<script type="text/javascript">

	var I_LOGMNU = "M_TESTITEM";
	var I_LOGMNM = "모바일 검사항목 조회";
	
	
	function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/mobileTestItemList.do", formData);
		$(".blockPage").css('margin-left', '-125px');
	}
	
	function onResult(strUrl,response){
		
		if(strUrl == "/mobileTestItemList.do"){
			var resultList = response.resultList;
			
			$("#tbodyOcsItems").empty();
			var tbodyHtml = '';
			
			resultList.forEach(it =>{
				
				tbodyHtml += '<tr class="_row" data-gcd="'+ it.F010GCD + '">';
				tbodyHtml += '	<td rowspan="2">'+ it.F010GCD + '</td>' ;
				tbodyHtml += '	<td rowspan="2">'+ it.F010FKN + '</td>' ;
				tbodyHtml += '	<td>'+ it.F010TCD + '</td>' ;
				tbodyHtml += '	<td>'+ it.F010MSNM + '</td>' ;
				tbodyHtml += '</tr>';
				tbodyHtml += '<tr class="_row" data-gcd="'+ it.F010GCD + '">';
				tbodyHtml += '	<td>'+ it.T001DAY + '</td>' ;
				tbodyHtml += '	<td>'+ it.F010EED + '</td>' ;
				tbodyHtml += '</tr>';
			});
			
			$("#tbodyOcsItems").html(tbodyHtml);
			
			
		}
	}
	
	$(document).on('click', '._row', function(){

		$('#popupGcd').val($(this).data('gcd'));
		
		
		var formData = document.popupForm;
		
		window.open("/mobileTestItemView.do", "viewPopup", "width=100%, height=100%, scrollbars=no, resizable=no");

		formData.target = "viewPopup";
// 		formData.target = "_self";
		formData.action = "/mobileTestItemView.do";
		formData.method = "post";
		formData.submit();
		
	});
	
	function enterSearch(e){	
		if (e.keyCode == 13) {
			// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우	
			if(e.target.nodeName == "INPUT" ){
				dataResult();
			}
		}
	}
	


	$(document).ready(function(){
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		//검사정보
		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "TEST_INFO",'Y');
// 		var resultCode = '<option value="">전체</option><option value="01">검사명</option><option value="02">검사 약어</option><option value="03">검사코드</option><option value="04">보험코드</option>';
// 		console.log(resultCode);
		$('#I_SERFNM').html(resultCode);
		
		//학부분류 MEDI_CLS
		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "MEDI_CLS",'Y');
// 		var resultCode = '<option value="">전체</option><option value="5210">임상화학팀</option><option value="5211">특수화학팀</option><option value="5212">요검경학팀</option><option value="5220">진단혈액학팀</option><option value="5230">특수면역학팀</option><option value="5231">면역학팀</option><option value="5240">미생물학팀</option><option value="5250">핵의학팀</option><option value="5260">조직병리학팀</option><option value="5270">세포병리팀</option><option value="5281">세포유전학팀</option><option value="5295">국내수탁</option><option value="5501">분자유전팀</option><option value="7001">대사체분석연구팀</option><option value="7006">연구용역사업</option><option value="5290">검체관리팀</option><option value="5502">자원제조부</option><option value="5280">분자미생물</option>';
// 		console.log(resultCode);
		$('#I_HAK').html(resultCode);
		
		
		//검색 결과 리스트 사이즈 조정
		var resultTableHeight = $(window).height() - ($("#searchForm").height() + $("#searchForm").offset().top + $("#navigation").height()) - 50;
		$(".item_table02_box").height(resultTableHeight);
		
		
		$("#btnSearch").click(function(){
			dataResult();
		});
		
// 		$("#btnSearch").trigger('click');
		
		
	});
</script>

</html>
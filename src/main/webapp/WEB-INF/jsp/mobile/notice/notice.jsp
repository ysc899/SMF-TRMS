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
			<div class="notice_area">
				<h2>공문 / 새소식</h2>
				<form id="searchForm" name="searchForm">
					<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
					<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->
					<input id="I_SEQ" name="I_SEQ" type="hidden"/>
					<input id="I_TIT" name="I_TIT" type="hidden"/>
					<input id="I_CON" name="I_CON" type="hidden"/>
					
					<input id="I_DCL" name="I_DCL" type="hidden"/> <!-- 문서종류 하나로 통합 -->
					<input type="hidden" class="fr_date calendar_put" id="I_FWDT" name="I_FWDT"> <!-- 시작일 최근 1년으로 그냥 고정 -->
					<input type="hidden" class="fr_date calendar_put" id="I_TWDT" name="I_TWDT"><!-- 종료일 최근 1년으로 그냥 고정 -->
					<input type="hidden" class="srch_put" id="I_CNM" name="I_CNM"><!-- 작성자 -->
					
					<table class="notice_table01">
						<colgroup>
							<col width="30%;">
							<col width="*">
							<col width="20%;">
						</colgroup>
					  <thead></thead>
					  <tbody>
						<tr>
						  <td>
							<select name="info_select" class="info_select" id="I_TIT1" name="I_TIT1">
							</select></td>
						  <td>
							<input type="text" class="input_type1" id="searchValue" name="searchValue" onKeydown="if(event.keyCode==13) dataResult()" maxlength = "40"></input>
						  </td>
						  <td class="td_bt">
							<span calss="search" id="btnSearch" style="width:100%;">검&nbsp;&nbsp;색</span>
						</td>
						</tr>
			
					  </tbody>
					</table>
					
				</form>
				
				
				<div class="notice_table02_box">
				  <table class="notice_table02">
					<thead></thead>
					<tbody id="tbodyNotices">
					</tbody>
				  </table>
				  
<!-- 				  <span class="more_view">+ 더보기</span> -->
				</div>
		
		
			  </div>
			
		</div>
		<!--//container End-->
		
		<form id="popupForm" name="popupForm">
			<input type="hidden" id="popupSeq" name="seq"/>
		</form>
		
		<%@ include file="../include/navigation.jsp"%>
	</div>
	
</body>

<script type="text/javascript">

	var I_LOGMNU = "M_NOTICE";
	var I_LOGMNM = "공문/새소식";
	
	
	function dataResult(){
		
		var sDate = new Date($("#I_FWDT").val());
		var eDate = new Date($("#I_TWDT").val());
		var interval = eDate - sDate;
		var day = 1000*60*60*24;
		
		if(!checkdate($("#I_FWDT"))||!checkdate($("#I_TWDT"))){
			CallMessage("273");	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
// 			dataClean();
			return;
		}
		
		if (interval/day > 365){
   			CallMessage("274","alert");		//최대 1년 내에서 조회해주세요.
//				dataClean();
			return false;
		}
		
		if(sDate > eDate){
			CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
// 			dataClean();
			return bool;
		}
		
		if($('#I_TIT1').val() == "TITLE"){
			$('#I_TIT').val($('#searchValue').val());
			$('#I_CON').val("");
		} else {
			$('#I_CON').val($('#searchValue').val());
			$('#I_TIT').val("");
		}
		
		
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/mobileNoticeList.do", formData);
		
		$(".blockPage").css('margin-left', '-125px');
	}
	
	function onResult(strUrl,response){
		
		
		if(strUrl == "/mobileNoticeList.do"){
			
			var resultList =  response.resultList;
			
			$("#tbodyNotices").empty();
			
			var resultHtml = '';
			
			resultList.forEach(it =>{
				
				resultHtml += '<tr class="_row" data-seq="'+ it.S014SEQ +'"><td><ul>';
				
				resultHtml += '<li>';
				resultHtml += '	<b class="' + ( it.S014DCL == 'NOTICE' ? 'new_ico' : 'notice_ico' ) +'">' + ( it.S014DCL == 'NOTICE' ? '새소식' : '공문' ) +'</b> ';
				resultHtml += it.S014TIT;
				resultHtml += ' </li>';
				
				resultHtml += '<li>';
				resultHtml += '<span>' + it.S014DNO + '</span>';
				resultHtml += '<img src="./images/mobile/bar.png" alt="회색바">';
				resultHtml += '<i>' + it.S014WDT + '</i>';
// 				if(it.S014DCL == 'NOTICE'){
// 					//새소식
					
// 				}else{
// 					//공문
					
// 				}
				resultHtml += ' </li>';
				
				resultHtml += '</ul></td></tr>';
			});
			
			$("#tbodyNotices").html(resultHtml);
			

		}
	}
	
	$(document).on('click', '._row', function(){

		$('#popupSeq').val($(this).data('seq'));
		
		
		var formData = document.popupForm;
		
		window.open("/mobileNoticeView.do", "viewPopup", "width=100%, height=100%, scrollbars=no, resizable=no");

		formData.target = "viewPopup";
// 		formData.target = "_self";
		formData.action = "/mobileNoticeView.do";
		formData.method = "post";
		formData.submit();
		
	});
	


	$(document).ready(function(){
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		//검색 옵션
		searchWord = getCode(I_LOGMNU, I_LOGMNM, "SEARCH_WORD","N");
		
// 		searchWord = '<option value="TITLE">제목</option><option value="CONTS">내용</option>';
		$('#I_TIT1').html(searchWord);
		
		
		//달력 from~to
		$("#I_FWDT").datepicker({
			dateFormat: 'yy-mm-dd',
			maxDate: $('#I_TWDT').val(),
			onSelect: function(selectDate){
				$('#I_TWDT').datepicker('option', {
					minDate: selectDate,
				});
			}
		});	
		$("#I_TWDT").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: $('#I_FWDT').val(),
			onSelect : function(selectDate){
				$('#I_FWDT').datepicker('option', {
					maxDate: selectDate,
				});
			}		
		});
		$(".fr_date").datepicker("setDate", new Date());
		var yearAgo = new Date();
		yearAgo.setDate(yearAgo.getDate() - 365);
		$("#I_FWDT").datepicker('setDate',yearAgo);
		
		
		
		
		$("#btnSearch").click(function(){
			dataResult();
		});
		
		$("#btnSearch").trigger('click');
		
		
	});
</script>

</html>
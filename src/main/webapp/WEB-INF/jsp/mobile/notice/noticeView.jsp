<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=yes, minimum-scale=0.5, maximum-scale=2, initial-scale=1">
<title>::씨젠의료재단 모바일::</title>
	<%@ include file="/inc/mobile-common.inc"%>
	
	
</head>
<body>



	<div id="app_wrap">
		<%@ include file="../include/header_popup.jsp"%>
		
		<!--container-->
		<div id="container" style="margin-top:20px; ">
			<div class="notice_area">
				<h2>공문 / 새소식</h2>
				<form id="noticeForm" name="searchForm">
					<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
					<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->
					<input id="I_SEQ" name="I_SEQ" type="hidden" value="${seq }"/>
					<input id="I_RUR" name="I_RUR" type="hidden" value="<%=ss_uid %>"/>
					<input id="I_FNM" name="I_FNM" type="hidden" value="<%=menu_nm%>"/>
					<input id="I_FPT" name="I_FPT" type="hidden"/>
				</form>
				<div class="noticeD_table01">
				  <table>
					<thead></thead>
					<tbody>
					  <tr>
						<th id="_dnoTitle">공문 번호</th>
						<td id="_dno"></td>
					  </tr>
		
					  <tr>
						<th>제목</th>
						<td id="_tit"></td>
					  </tr>
		
					  <tr>
						<th>작성일</th>
						<td id="_wdt"></td>
					  </tr>
		
					  <tr id="trAttachFile">
						<th>첨부파일</th>
						<td>
							<ul id="attachFiles">
<!-- 								<li> -->
<!-- 									<b> -->
<!-- 										[씨젠서울 제2021-015호] 신규 검사코드 및 검사정보 변경 -->
<!-- 										안내 (MTB real-time PCR(Fluid), CMV real-time PCR ) -->
<!-- 										.pdf -->
<!-- 									  </b> -->
<!-- 								</li> -->
							</ul>
						</td>
					  </tr>
		
					</tbody>
				  </table>
				  </div>
					
				
				<div class="noticeD_table02" style="height:100%; margin-bottom: 0px; border-bottom: 0px;">
					<p>내용</p>
				</div>
				  <div class="noticeD_table02" style="margin-top:0px;">
					<table>
					  <tbody>
						<tr>
						  <td id="noticeContent">
						</tr>
					  </tbody>
					</table>
				  </div>
			  </div>
		</div>
		<!--//container End-->
		<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>		
		
<%-- 		<%@ include file="../include/navigation.jsp"%> --%>
	</div>
	
</body>

<script type="text/javascript">

	var I_LOGMNU = "M_NOTICE";
	var I_LOGMNM = "공문/새소식";
	
	
	function dataResult(){
		
		var formData = $("#noticeForm").serializeArray();
		ajaxCall("/mobileNoticeDtl.do",formData);
		$(".blockPage").css('margin-left', '-125px');
		
		noticeAttachList();
	}
	
// 	function noticeRead(){
// 		var formData = $("#noticeForm").serializeArray();
// 		ajaxCall("/noticeRead.do", formData,false);
// 	}	
	
	function noticeAttachList()
	{
		var formData = new FormData($("#noticeForm")[0]);
		ajaxCall2("/mobileNoticeAttachList.do",formData,false);
	}
	
	function onResult(strUrl,response){
		if(strUrl == "/mobileNoticeDtl.do"){
			var result1 = response.result1;
			
			$("#_dnoTitle").html((result1[0]["S014DCL"] == "NOTICE" ? '문서 번호' : '공문 번호'));
			
			$("#_dno").html(result1[0]["S014DNO"]);
			$("#_tit").html(result1[0]["S014TIT"]);
			
			var S014WDT = result1[0]["S014WDT"].toString();
			
			var wdt = S014WDT.substring(0,4)+"-"+S014WDT.substring(4,6)+"-"+S014WDT.substring(6,8);
			
// 			$("#_wdt").html(result1[0]["S014WDT"]);
			$("#_wdt").html(wdt);
			$("#noticeContent").html(result1[0]["S014CON"]);
			
			
			var ryn  = result1[0]["S014RYN"];
			if(ryn == "미열람"){
// 				noticeRead();
			}

			setTimeout(function(){
				if(result1[0]["S014DCL"] != "NOTICE" ){
					$("#noticeContent").find("img").width($(window).width()-50)
				}
			}, 100);
			
			
		}
		if(strUrl == "/mobileNoticeAttachList.do"){
			var resultList = response.resultList;
			
			$("#attachFiles").html('');
// 			console.log("mobileNoticeAttachList.do");
// 			console.log(resultList);
			
			var html_str = "";
			if(resultList[0]){
				$('#I_FPT').html(resultList[0]["S014FNM"]);
				$('#I_FPT').val(resultList[0]["S014FPT"]);
				$('#I_FNM').val(resultList[0]["S014FNM"]);
			}
			
			for (var key in resultList){
				html_str +="<li>";
				html_str +="<a href='#' style='cursor:pointer; font-weight:bold; padding:5px;' onclick='downNotice(\""+resultList[key].S014FPT+"\""+","+"\""+resultList[key].S014FNM+"\")'>"+resultList[key].S014FNM+"</a>";
				html_str +="</li>";
				
			}
			
			
			$("#attachFiles").html(html_str);
			
			if(resultList.length == 0){
				$("#trAttachFile").hide();
			}
			
			
// 			attachFiles
			
// 			var html_str = "";	// 신규 홍보 홈페이지 관리자에서 업로드 했을 때, 첨부파일 가져오기위한 변수.
			
// 			if(resultList != ""){
				
// 				// ******************* 첨부파일(신규 결과관리 관리자) *******************
// 				if(resultList[0]){
// 					$('#I_FPT').html(resultList[0]["S014FNM"]);
// 					$('#I_FPT').val(resultList[0]["S014FPT"]);
// 					$('#I_FNM').val(resultList[0]["S014FNM"]);
// 				}
				
// 				// ******************* 첨부파일(신규 홍보홈페이지 관리자) *******************
// 				for (var key in resultList){
// 					html_str +="<a href='#' style='cursor:pointer; font-weight:bold; padding:5px;' onclick='downNotice(\""+resultList[key].S014FPT+"\""+","+"\""+resultList[key].S014FNM+"\")'>"+resultList[key].S014FNM+"</a>";
// 					html_str +="<br>";
// 				}
// 				$("#down_file_tbody").empty();
// 				$("#down_file_tbody").append(html_str);
// 			}
		}
	}

	// 공문 첨부파일 다운로드
	function downNotice(s014fpt_val,s014fnm_val){
	
		var path = "";
		var fileNm = "";
		
		// 구)홍보홈페이지에서 업로드 했던 공문글의 첨부파일 다운받을 때 사용
		if(s014fpt_val.indexOf("shared_files") > 0){
			path = $("#I_FPT").val();
			fileNm = $("#I_FNM").val();

			console.log(path);
			console.log(fileNm);
			
			$.when(function () {
		        var def = jQuery.Deferred();
		        window.setTimeout(function () {
		        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+path+"&file_name="+encodeURIComponent(fileNm);
		            def.resolve();
		        }, 100);
		        return def.promise();
		    }())
		    .done(function () {
		    });	
		}else{  // 신)홍보홈페이지에서 업로드 했던 공문글의 첨부파일 다운받을 때 사용
			
			path = s014fpt_val;
			fileNm = s014fnm_val;
			 
			$.when(function () {
		        var def = jQuery.Deferred();
		        window.setTimeout(function () {
		        	window.parent.location.href="https://pr.seegenemedical.com"+path
		            def.resolve();
		        }, 100);
		        return def.promise();
		    }())
		    .done(function () {
		    });
		}
	}

	$(document).ready(function(){
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
// 		alert('${seq}');
		
		
		$(".area_name").hide();
		
		dataResult();
		
// 		//검색 옵션
// 		searchWord = getCode(I_LOGMNU, I_LOGMNM, "SEARCH_WORD","N");
// 		$('#I_TIT1').html(searchWord);
		
		
// 		//달력 from~to
// 		$("#I_FWDT").datepicker({
// 			dateFormat: 'yy-mm-dd',
// 			maxDate: $('#I_TWDT').val(),
// 			onSelect: function(selectDate){
// 				$('#I_TWDT').datepicker('option', {
// 					minDate: selectDate,
// 				});
// 			}
// 		});	
// 		$("#I_TWDT").datepicker({
// 			dateFormat: 'yy-mm-dd',
// 			minDate: $('#I_FWDT').val(),
// 			onSelect : function(selectDate){
// 				$('#I_FWDT').datepicker('option', {
// 					maxDate: selectDate,
// 				});
// 			}		
// 		});
// 		$(".fr_date").datepicker("setDate", new Date());
// 		var yearAgo = new Date();
// 		yearAgo.setDate(yearAgo.getDate() - 365);
// 		$("#I_FWDT").datepicker('setDate',yearAgo);
		
		
		
		
// 		$("#btnSearch").click(function(){
// 			dataResult();
// 		});
		
// 		$("#btnSearch").trigger('click');
		
		
	});
</script>

</html>
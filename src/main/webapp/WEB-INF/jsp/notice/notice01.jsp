<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script>
	
		var I_SEQ = 0;
		$(document).ready( function(){
			/* callResize(); */
		})
		
		function gridViewRead(getValue){// 데이터를 그려주는 부분
			I_SEQ = getValue["index"];
			var MainYN = getValue["MainYN"];
			$("#I_SEQ").val(I_SEQ);
			
			if(MainYN =="Y"){
				$(".mainDis").addClass("display_off");
			}
			dataResult();
		}
		
		function dataResult(){
			
			var formData = $("#noticeForm").serializeArray();
			ajaxCall("/noticeDtl.do",formData);
			
			noticeAttachList();
		}
		
		function noticeRead(){
			var formData = $("#noticeForm").serializeArray();
			ajaxCall("/noticeRead.do", formData,false);
		}
		
		function onResult(strUrl,response){
			if(strUrl == "/noticeDtl.do"){
				var result1 = response.result1;
				$('#I_DCL').html(result1[0]["S002DCL"]);
				$('#I_CNM').html(result1[0]["S014CNM"]);
				$('#I_WDT').html(result1[0]["S014WDT"]);
				$('#I_SDV').html(result1[0]["S002SDV"]);
				$('#I_PYN').html(result1[0]["S002PYN"]);
				$('#I_DNO').html(result1[0]["S014DNO"]);
				$('#I_PFR').html(result1[0]["S014PFR"]);
				$('#I_PTO').html(result1[0]["S014PTO"]);
				$('#I_LCO').html(result1[0]["S014LCO"]);
				$('#I_TCO').html(result1[0]["S014TCO"]);
				$('#I_TIT').html(result1[0]["S014TIT"]);
				$('#I_RYN').html(result1[0]["S014RYN"]);
				
				var I_WDT = result1[0]["S014WDT"];
				$('#I_WDT').html(parseDate(I_WDT));
				
				var text = result1[0]["S014CON"];
				
				$('#I_CON').html(text);
				
				var ryn  = $('#I_RYN').html();
				if(ryn == "미열람"){
					noticeRead();
				}
			}
			if(strUrl == "/noticeAttachList.do"){
				var resultList = response.resultList;
				var html_str = "";	// 신규 홍보 홈페이지 관리자에서 업로드 했을 때, 첨부파일 가져오기위한 변수.
				
				if(resultList != ""){
					
					// ******************* 첨부파일(신규 결과관리 관리자) *******************
					if(resultList[0]){
						$('#I_FPT').html(resultList[0]["S014FNM"]);
						$('#I_FPT').val(resultList[0]["S014FPT"]);
						$('#I_FNM').val(resultList[0]["S014FNM"]);
					}
					
					// ******************* 첨부파일(신규 홍보홈페이지 관리자) *******************
					for (var key in resultList){
						html_str +="<a href='#' style='cursor:pointer; font-weight:bold; padding:5px;' onclick='downNotice(\""+resultList[key].S014FPT+"\""+","+"\""+resultList[key].S014FNM+"\")'>"+resultList[key].S014FNM+"</a>";
						html_str +="<br>";
					}
					$("#down_file_tbody").empty();
			    	$("#down_file_tbody").append(html_str);
				}
			}
	        $("#pageiframe", parent.document).width(900);  
			callResize();
		}
		
		function noticeAttachList()
		{
			var formData = new FormData($("#noticeForm")[0]);
			formData.append("I_SEQ",$('#I_SEQ').val());
			
			ajaxCall2("/noticeAttachList.do",formData,false);
		}
		
		function noticeUpdate(){
			var seq = I_SEQ;
			closeModal("udtModal",seq);
		}
		
		// 공문 첨부파일 다운로드
		function downNotice(s014fpt_val,s014fnm_val){
		
			var path = "";
			var fileNm = "";
			
			// 구)홍보홈페이지에서 업로드 했던 공문글의 첨부파일 다운받을 때 사용
			if(s014fpt_val.indexOf("shared_files") > 0){
				path = $("#I_FPT").val();
				fileNm = $("#I_FNM").val();
	
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
		
	</script>
	
	
</head>
<body  style="width : 900px;">
	<div class="tbl_type">
		<form id="noticeForm" name="noticeForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_SEQ" name="I_SEQ" type="hidden" value=""/>
			<input id="I_RUR" name="I_RUR" type="hidden" value="<%=ss_uid %>"/>
			<input id="I_FNM" name="I_FNM" type="hidden" value="<%=menu_nm%>"/>
			<table class="table table-bordered table-condensed tbl_l-type" >
				<colgroup>
	            	<col width="15%">
	            	<col width="35%">
	            	<col width="15%">
	            	<col width="35%">
	            </colgroup>
	            <tbody>
				    <tr class="mainDis">
				        <th scope="row">문서종류</th>
				        <td><span id="I_DCL"></span></td>
				        <th scope="row">문서번호</th>
				        <td><span id="I_DNO"></span></td>
				    </tr>
				    <tr class="mainDis">
				        <th scope="row">작성자</th>
				        <td><span id="I_CNM"></span></td>
				        <th scope="row">작성일자</th>
				        <td><span id="I_WDT"></span></td>
				    </tr>
				    <tr class="mainDis">
				        <th scope="row">시스템구분</th>
				        <td><span id="I_SDV"></span></td>
				        <th scope="row">열람여부</th>
				        <td><span id="I_RYN"></span></td>
				    </tr>
				    <tr align="center">
				        <th scope="row">제목</th>
				        <td colspan="3"><span id="I_TIT"></span></td>
				    </tr>
				    <tr>
				        <th scope="row">첨부파일</th>
				        <td colspan="3"><table><tr><td><a><tbody id="down_file_tbody"></tbody></td></tr></table></td>
				    </tr>
				    <tr>
				        <th scope="col" colspan="4" style="text-align : center;">내용</th>
				    </tr>
				    <tr>
				    	<td scope="col" colspan="4">
					  		<div id="I_CON" name="I_CON" style=" width : 100%; height: 400px; overflow : auto;padding:5px">
							</div>
						</td>  	
				    </tr>
				</tbody>
			</table>
			
		</form>
	</div>
	<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
	<div class="modal-footer">
		<div align="center">
			<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		</div>
	</div>
</body>
</html>
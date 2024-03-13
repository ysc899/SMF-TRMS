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
		
		function gridViewRead(index){// 데이터를 그려주는 부분
			var gridView = index;
			$("#I_SEQ").val(gridView);
			I_SEQ = $("#I_SEQ").val();
			
			dataResult();
		}
		
		function dataResult(){
			var formData = $("#noticeForm").serializeArray();
			ajaxCall("/noticeDtl.do",formData);
			
			noticeAttachList();
		}
		
		function onResult(strUrl,response){
			if(strUrl == "/noticeDtl.do"){
				var result1 = response.result1;
				$('#I_DCL').html(result1[0]["S002DCL"]);
				$('#I_CNM').html(result1[0]["S014CNM"]);
				$('#I_SDV').html(result1[0]["S002SDV"]);
				$('#I_PYN').html(result1[0]["S002PYN"]);
				$('#I_DNO').html(result1[0]["S014DNO"]);
				$('#I_LCO').html(result1[0]["S014LCO"]);
				$('#I_TCO').html(result1[0]["S014TCO"]);
				$('#I_TIT').html(result1[0]["S014TIT"]);
				$('#I_CUR').val(result1[0]["S014CUR"]);
				
				var I_WDT = result1[0]["S014WDT"];
				var I_PFR = result1[0]["S014PFR"];
				var I_PTO = result1[0]["S014PTO"];
				
				if(I_PFR != 0 || I_PTO != 0 ){
					$('#I_PFR').html(parseDate(I_PFR) + " ~ ");
					$('#I_PTO').html(parseDate(I_PTO));
				}
				
				$('#I_WDT').html(parseDate(I_WDT));
				
				var text = result1[0]["S014CON"];
				$('#I_CON').html(text);
			}
			if(strUrl == "/noticeAttachList.do"){
				var resultList = response.resultList;
				if(resultList != ""){
					if(resultList[0]){
						$('#I_FPT').html(resultList[0]["S014FNM"]);
						$('#I_FPT').val(resultList[0]["S014FPT"]);
						$('#I_FNM').val(resultList[0]["S014FNM"]);
					}
				}
			}
		}
		
		function noticeAttachList()
		{
			var formData = new FormData($("#noticeForm")[0]);
			formData.append("I_SEQ",$('#I_SEQ').val());
			
			ajaxCall2("/noticeAttachList.do",formData,false);
		}
		
		function noticeUpdate(){
			var seq = I_SEQ;
			if($('#I_CUR').val() != "<%=session.getAttribute("UID") %>"){
				CallMessage("227","alert",["수정"]) //본인이 작성한 글외에는 수정할 수 없습니다.
				return;	
			}
			
			closeModal("udtModal",seq);
		}
		
		function downNotice(){
			var path = $("#I_FPT").val();
			var fileNm = $("#I_FNM").val();
			
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
		}
		
	</script>
	
	
</head>
<body >
	<div style="min-width: 800px;">
	<div class="tbl_type">
		<form id="noticeForm" name="noticeForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_SEQ" name="I_SEQ" type="hidden" value=""/>
			<input id="I_CUR" name="I_CUR" type="hidden" value=""/>
			<input id="I_FNM" name="I_FNM" type="hidden"/>
			<table class="table table-bordered table-condensed tbl_l-type">
				<colgroup>
	            	<col width="20%">
	            	<col width="30%">
	            	<col width="20%">
	            	<col width="30%">
	            </colgroup>
	            <tbody>
				    <tr>
				        <th scope="row">문서종류</th>
				        <td><span id="I_DCL"></span></td>
				        <th scope="row">문서번호</th>
				        <td><span id="I_DNO"></span></td>
				    </tr>
				    <tr>
				        <th scope="row">작성자</th>
				        <td><span id="I_CNM"></span></td>
				        <th scope="row">작성일자</th>
				        <td><span id="I_WDT"></span></td>
				    </tr>
				    <tr>
				        <th scope="row">시스템구분</th>
				        <td><span id="I_SDV"></span></td>
				        <th scope="row">메인 팝업 유무 생성</th>
				        <td><span id="I_PYN"></span></td>
				    </tr>
				    <tr>
				        <th scope="row">메인 팝업 생성 기간</th>
				        <td colspan="3"><span id="I_PFR"></span><span id="I_PTO"></span></td>
				    </tr>
				    <tr>
				        <th scope="row">좌측 시작 좌표</th>
				        <td><span id="I_LCO"></span></td>
				        <th scope="row">상단 시작 좌표</th>
				        <td><span id="I_TCO"></span></td>
				    </tr>
				    <tr align="center">
				        <th scope="row">제목</th>
				        <td colspan="3"><span id="I_TIT"></span></td>
				    </tr>
				    <tr>
				        <th scope="row">첨부파일</th>
				        <td colspan="3">
				        <a onclick="downNotice();"><span id="I_FPT" style="cursor:pointer; font-weight:bold"></span></a></td>
				        
				    </tr>
				    <tr>
				        <th scope="col" colspan="4" style="text-align : center;">내용</th>
				    </tr>
				    <tr>
				    	<td scope="col" colspan="4">
					  		<div id="I_CON" style=" width : 100%; height: 350px; overflow : auto;padding:5px">
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
			<button type="button" class="btn btn-primary" onclick="javascript:noticeUpdate();">수정</button>
			<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		</div>
	</div>
	</div>

</body>
</html>
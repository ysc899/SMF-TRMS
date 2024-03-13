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
			
		})
		
		function gridViewRead(index){// 데이터를 그려주는 부분
			gridView = index;
			$("#I_SEQ").val(gridView);
			I_SEQ = $("#I_SEQ").val();
			//아이디
			dataResult();
		}
		
		function qnaRdCntUdt()
		{
			var formData = $("#qna02Form").serializeArray();
			ajaxCall("/qnaRdCntUdt.do",formData,false);
		}
		
		function popupClose(){
			closeModal("fnCloseModal");
		}
		
		function onResult(strUrl,response){
			if(strUrl == "/qnaDtl.do"){
				var resultList = response.resultList;
				$('#I_PDNO').html(resultList[0]["S015PDNO"]);
				$('#I_DCL').html(resultList[0]["S015DCL"]);
				$('#S002CNM').html(resultList[0]["S002CNM"]);
				$('#I_CNM').html(resultList[0]["S015CNM"]);
				$('#I_TIT').html(resultList[0]["S015TIT"]);
				$('#I_DNCHK').val(resultList[0]["DNCHK"]);
				$('#I_CUR').val(resultList[0]["S015CUR"]);
				
				var text = resultList[0]["S015CON"];
				$('#I_CON').html(text);
			}
			if(strUrl == "/qnaDelete.do"){
				CallMessage("223","alert","",popupClose);	//삭제를 완료하였습니다.
			}
			
			if(strUrl == "/qnaAttachList.do"){
				var resultList = response.resultList;
				if(resultList != ""){
					if(resultList[0]["S015FSQ"] > 1){
							$('#I_FPT2').html(resultList[0]["S015FNM"]);
							$('#I_FPT2').val(resultList[0]["S015FPT"]);
							$('#I_FNM2').val(resultList[0]["S015FNM"]);
					}else{
							$('#I_FPT1').html(resultList[0]["S015FNM"]);
							$('#I_FPT1').val(resultList[0]["S015FPT"]);
							$('#I_FNM1').val(resultList[0]["S015FNM"]);
							
							$('#I_FPT2').html(resultList[1]["S015FNM"]);
							$('#I_FPT2').val(resultList[1]["S015FPT"]);
							$('#I_FNM2').val(resultList[1]["S015FNM"]);
					}
				}
			}
		}
		
		function dataResult()
		{
			var formData = $("#qna02Form").serializeArray();
			ajaxCall("/qnaDtl.do",formData);
			
			qnaRdCntUdt();
			qnaAttachList();
		}
		
		function qnaAttachList()
		{
			var formData = new FormData($("#qna02Form")[0]);
			
			formData.append("I_SEQ",$('#I_SEQ').val());
			
			ajaxCall2("/qnaAttachList.do",formData,false);
			
		}
		
		function downNotice(index){
			var fileNum = index;
			var path = $("#I_FPT"+fileNum).val();
			var fileNm = $("#I_FNM" + fileNum).val();	
			
			$.when(function () {
		        var def = jQuery.Deferred();
		        window.setTimeout(function () {
		        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+path+"&file_name="+encodeURIComponent(fileNm);
		            def.resolve();
		        }, 100);
		        return def.promise();
		    }())
		    .done(function() {
		    });
		}

		function qnaRpy(){
			var seq = I_SEQ; 
			closeModal("rpyModal",seq);
		}
		
		function qnaUdt(){
			var I_DNCHK = $('#I_DNCHK').val();
			if(I_DNCHK == 'Y'){
				CallMessage("230","alert");	//답변이 완료된 문의사항은 수정할 수 없습니다.
				return;
			}
			
			if($('#I_CUR').val() != "<%=session.getAttribute("UID") %>"){
				CallMessage("227","alert",["수정"]) //본인이 작성한 글외에는 수정할 수 없습니다.
				return;	
			}
			
			var seq = I_SEQ; 
			closeModal("udtModal",seq);
		}
		
		function qnaDelete(){
			var T_QNA = "문의사항"
			CallMessage("228","confirm",[T_QNA],fnDel);	//문의사항을 삭제하시겠습니까?
		}
		
		function fnDel(){
			
			if($('#I_DNCHK').val() == 'Y'){
				CallMessage("226","alert");	//답변이 존재하여 삭제할 수 없습니다.
				return;	
			}
			var aa = "<%=session.getAttribute("UID") %>";
			
			if($('#I_CUR').val() != "<%=session.getAttribute("UID") %>"){
				CallMessage("227","alert",["삭제"]) //본인이 작성한 글외에는 삭제할 수 없습니다.
				return;	
			}
			
			var formData = $("#qna02Form").serializeArray();
			ajaxCall("/qnaDelete.do",formData);
		}
	
	</script>
	
	
</head>
<body>
	<div style="min-width: 1000px;">
<div class="tbl_type">
	<form id="qna02Form" name="qna02Form">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_PDNO" 	 name="I_PDNO" 	 type="hidden" value=""/>
		<input id="I_DCL" 	 name="I_DCL" 	 type="hidden" value=""/>
		<input id="I_SEQ" 	 name="I_SEQ" 	 type="hidden" value=""/>
		<input id="I_DNCHK"  name="I_DNCHK"  type="hidden" value=""/>
		<input id="I_CUR" 	 name="I_CUR" 	 type="hidden" value=""/>
		<input id="I_FNM1"   name="I_FNM1"   type="hidden"/>
		<input id="I_FNM2"   name="I_FNM2"   type="hidden"/>
			<table class="table table-bordered table-condensed tbl_l-type">
			<colgroup>
            	<col width="20%">
                <col >
            	<col width="20%">
                <col >
            </colgroup>
            <tbody>
				<tr>
					<th scope="row">문의종류</th>
					<td><span id="S002CNM"></span></td>
					<th scope="row">작성자</th>
					<td><span id="I_CNM"></span></td>
				</tr>
				<tr>
					<th scope="row">제 목</th>
					<td colspan="3"><span id="I_TIT"></span></td>
					<!-- <th colspan="3"><span id="I_TIT"></span></th> -->
				</tr>
				<tr>
					<th scope="row">첨부파일 1 </th>
					<td colspan="3">
					<a onclick="downNotice(1);"><span id="I_FPT1" style="cursor:pointer; font-weight:bold"></span></a></td>
					<!--<a type="" href="javascript:fileDown(1);" id="I_FPT1" name="I_FPT1"></a> -->
				</tr>
				<tr>
					<th scope="row">첨부파일 2 </th>
					<td colspan="3">
					<a onclick="downNotice(2);"><span id="I_FPT2" style="cursor:pointer;font-weight:bold"></span></a></td>
					<!-- <a type="" href="javascript:fileDown(2);" id="I_FPT2" name="I_FPT2"></a> -->
				</tr>
				<tr>
					<th scope="col" colspan="4" style="text-align : center;">내용</th>
				</tr>
				<tr>
					<td scope="col" colspan="4">
						<div id="I_CON" style=" width : 100%; height: 450px; overflow : auto; padding:5px">
						</div>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
		</div>
		<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
		<div class="modal-footer">
			<div class="min_btn_area">	
				<button type="button" class="btn btn-primary" onclick="javascript:qnaUdt();">수정</button>
				<button type="button" class="btn btn-warning" onclick="javascript:qnaRpy();">답변</button>
				<button type="button" class="btn btn-default" onclick="javascript:qnaDelete()">삭제</button>
				<button type="button" class="btn btn-info" onclick="closeModal()">취소</button>
			</div>
		</div>
		</div>
</body>
</html>
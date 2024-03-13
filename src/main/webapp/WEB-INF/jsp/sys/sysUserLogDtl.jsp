<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>사용자 로그 상세조회</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script>
	function gridView(gridValus){
		$("#S004UID").html(gridValus["S004UID"]);			//아이디		
		$("#S004NAM").html(gridValus["S004NAM"]);			 //사용자 명	
		var dt =  gridValus["S004EDT"];
		var tm =  gridValus["S004ETM"];
		$("#S004EDT").html(parseDate(dt) +" "+ parseTime(tm));			 //접속 일시	
		$("#S004MNM").html(gridValus["S004MNM"]);			 //메뉴 명		
		$("#S004EFG").html(gridValus["S004EFG"]);			 //이벤트		
		$("#S004URL").html(gridValus["S004URL"]);			 //호출 명		
		$("#S004PAR").html(gridValus["S004PAR"]);			 //파라미터		
		$("#S004SFL").html(gridValus["S004SFL"]);			 //성공여부
		$("#S004MSG").html(gridValus["S004MSG"]);			 //로그
		callResize();
	}
	
	</script>
	
</head>
<body>
	<div style="min-width: 900px;">
		<div class="tbl_type">
			<table  class="table table-bordered table-condensed tbl_l-type">
				<colgroup>
					<col width="15%">
			  		<col >
					<col width="15%">
			  		<col >
				</colgroup>
				<tr>
					<th  scope="row">아이디</th> 
					<td id="S004UID"> </td>
					<th  scope="row">사용자</th> 
					<td id="S004NAM"> </td>
				</tr>
				<tr>
					<th  scope="row">접속일시 </th> 
					<td id="S004EDT"> </td>
					<th  scope="row">메뉴명 </th> 
					<td id="S004MNM"> </td>
				</tr>
				<tr>
					<th  scope="row">이벤트 </th> 
					<td id="S004EFG"> </td>
					<th  scope="row">호출명 </th> 
					<td id="S004URL"> </td>
				</tr>
				<tr>
					<th>파라미터 </th> 
					<td colspan="3">
					
						<div id="S004PAR"  class="edit_area" style="word-wrap: break-word; white-space:inherit ;"></div> 
					 </td>
				</tr>
				<tr>
					<th>로그</th> 
					<td colspan="3">
						<div id="S004MSG"  class="edit_area"  style="word-wrap: break-word; white-space:inherit ;"></div> 
					</td>
				</tr>
			</table>
		</div>
		<div class="modal-footer">
			<div class="min_btn_area">
			   <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
			</div>
		</div>
	</div>
</body>
</html>


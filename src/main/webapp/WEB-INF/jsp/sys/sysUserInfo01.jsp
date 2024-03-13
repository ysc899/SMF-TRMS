<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원(회원) 조회</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<script>

    var pscd = "";
	$(document).ready( function(){		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		var resultCode = '<option value=""></option>';
		
		//I_EML
		var formData = $("#productForm").serializeArray();
	})
	
	/*팝업 호출 Input 내역 설정*/
	function gridViewRead(gridValus){

		$("#IDC").attr("hidden",true);
		$("#HOSC").attr("hidden",true);
		
		$("#I_LOGLID").attr("readOnly",true);

		$("#I_SAVEFLG").val("U");
		
		$("#I_LOGLID").val(gridValus["LOGLID"]);			//아이디		
		$("#I_LOGHOS").val(gridValus["LOGHOS"]);       //병원코드 
		$("#I_GIC").val(gridValus["F120GIC"]);       //요양기관코드 
		$("#I_FNM").val(gridValus["F120FNM"]);       //병원명 
		$("#I_SNO").val(gridValus["LOGSNO"]);       //사업자번호
		$("#I_WNO").val(gridValus["LOGWNO"]);       //대표자명 
		$("#I_TEL").val(gridValus["LOGTEL"]);       //전화번호 
		$("#I_LOGWNO").val(gridValus["LOGWNO"]);		//대표자명
		$("#I_LOGGNO").val(gridValus["LOGGNO"]);		//면허번호
		$("#I_I_EKD").val(gridValus["I_EKD"]);		//메일코드
		$("#I_DRS").val(gridValus["S005DRS"]);		//원장출신학교
		$("#I_HPH").val(gridValus["S005HPH"]);		//병원홈페이지
		$("#I_ITM").val(gridValus["S005ITM"]);		//전문진료과목
		$("#I_POS").val(gridValus["S005POS"]);		//우편번호
		$("#I_JAD1").val(gridValus["S005JAD1"]);		//지번주소1
		$("#I_DAD1").val(gridValus["S005DAD1"]);		//도로명주소1
		$("#I_DAD2").val(gridValus["S005DAD2"]);		//도로명주소2
		$("#I_MMO").val(gridValus["S005MMO"]);		//메모
		$("#I_MRV").val(gridValus["S005MRV"]);		//메일수신여부
		$("#I_AUTH").val(gridValus["S005ANM"]);       //권한정보 
		$("#I_SYN").val(gridValus["S005SNM"]);       //SMS 사용여부 
 	    $("#mail_chk").prop('checked', gridValus["S005MRV"]=="Y"?true:false) ;
		
		setMail(gridValus["LOGEML"]);//메일 설정
	}
	
	//정보 상세보기시 메일 설정
	function setMail(strMail){
		if(strMail.indexOf("@")>-1){
			var splitMail = strMail.split('@');
			$("#I_MAILID").val(splitMail[0]);
			$("#I_MAILCD").val(splitMail[1]);
		}
		
	}
	function clsoe(){
		closeModal();
	}
	</script>
	
	<style type="text/css">
		body{
/* 			width:400px !important ; */
		}
		.form-group{
			padding-top: 5px;
		}
		.#HOSC{
			padding-left: 5px;
		}
		.col-md-9,.col-md-3,.col-md-1,.col-md-2{
			padding-right: 5px;
		}
	</style>
</head>
<body>
	<form id="Mng01Form" name="Mng01Form">
		<input id="I_CHKTYPE" name="I_CHKTYPE" type="hidden"/>
		<input id="I_CHKCD" name="I_CHKCD" type="hidden"/>
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_MRV" 	name="I_MRV" type="hidden"/>
		<input id="I_LOGCOR" name="I_LOGCOR" type="hidden" value="NML"/>
		<input id="I_SAVEFLG" name="I_SAVEFLG" type="hidden" value="F"/>
		<input id="I_LOGINCHK" name="I_LOGINCHK" type="hidden" value="F"/>
		<input id="I_PO1" name="I_PO1" type="hidden" value=""/>
		<input id="I_PO2" name="I_PO2" type="hidden" value=""/>
		<input id="I_FL4" name="I_FL4" type="hidden" value=""/>
		
		<div class="form-group">
			<label for="I_LOGLID">아이디</label> 
			<input type="text" class="form-control engNum" maxlength="12" id="I_LOGLID" name="I_LOGLID" required >  
		</div>

		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_LOGHOS">병원코드</label> 
					<input type="text" class="form-control numberOnly" id="I_LOGHOS" name="I_LOGHOS" maxlength="5" required readonly >  
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_FNM" >병원(사용자)명</label> 
					<div>
						<input type="text" class="form-control" id="I_FNM" name="I_FNM" readonly>
					</div>
				</div>
			</div>
		</div>

		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label  for="I_GIC" >요양기관코드</label> 
					<div>
						<input type="text" class="form-control" id="I_GIC" name="I_GIC" readonly>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_TEL">병원연락처</label> 
					<div>
						<input type="text" class="form-control telNm" id="I_TEL" name="I_TEL" maxlength="14" placeholder="031-1234-5678" required  readonly="readonly">
					</div>
				</div>
			</div>
		</div>

		
		<div class="form-group">
			<label for="I_MAILID">대표이메일</label> 
			<div class="row">
				<div class="col-md-4">
					<input type="text" class="form-control"  id="I_MAILID" name="I_MAILID" required  readonly="readonly">
				</div>
				<div class="col-md-1" style="width: 40px;">@</div>
				<div class="col-md-4">
					<input type="text" class="form-control"  id="I_MAILCD" name="I_MAILCD" required  readonly="readonly"> 
				</div>
				<div class="col-md-2 modal_mail_chk">            										
					<input id="mail_chk" name="mail_chk" type="checkbox" value="Y" disabled="disabled">
					<label for="mail_chk">메일수신</label> 
				</div>
			</div>
		</div>
				
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_AUTH">권한그룹</label> 
					<input type="text" class="form-control" id="I_AUTH" name="I_AUTH" readonly="readonly" >									
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label for="I_SYN">SMS사용권한</label>	
					<input type="text" class="form-control" id="I_SYN" name="I_SYN" readonly="readonly" >		
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>대표자성명</label>
					<div>
						<input type="text" class="form-control" id="I_WNO" name="I_WNO" readonly="readonly" >
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label>대표자면허번호</label>
					<div>
						<input type="text" class="form-control Num" id="I_LOGGNO" name="I_LOGGNO"  readonly="readonly" >
					</div>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>사업자번호</label>
					<div>
						<input type="text" class="form-control Num" id="I_SNO" name="I_SNO"  readonly="readonly" >
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label>전문진료과목</label>
					<div>
						<input type="text" class="form-control" id="I_ITM" name="I_ITM"  readonly="readonly">
					</div>
				</div>
			</div>
		</div>
		<div class = "row">
			<div class="col-md-6" >
				<div class="form-group">
					<label>병원홈페이지</label>
					<div>
							<input type="text" class="form-control" id="I_HPH" name="I_HPH"  readonly="readonly">
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group">
					<label>원장출신학교</label>
					<div>
							<input type="text" class="form-control" id="I_DRS" name="I_DRS"  readonly="readonly">
					</div>
				</div>
			</div>
		</div>
		<!-- 우편번호 -->
		<div id="div_post_cd" class="form-group">
			<label>우편번호</label>
			<input type="text" class="form-control" id="I_POS" name="I_POS"  placeholder="우편번호" maxlength="6" readonly="readonly" >  
		</div>
		<!-- 도로명 주소 -->
		<div id="div_road_addr" class="form-group">
			<label for="I_DAD1">도로명주소</label> 
			<input type="text" class="form-control  m-t-10" id="I_DAD1"  name="I_DAD1" placeholder="도로명주소" readonly="readonly">
		</div>
		<!-- 지번 주소 -->
		<div id="div_gibun_addr" class="form-group">
			<label for="I_JAD1">지번주소</label> 
			<input type="text" class="form-control  m-t-10" id="I_JAD1"  name="I_JAD1" placeholder="지번주소" readonly="readonly">
		</div>
		<!-- 나머지 주소 -->
		<div id="div_rest_addr" class="form-group">
			<label for="I_DAD2">나머지주소</label> 
			<input type="text" class="form-control  m-t-10" id="I_DAD2" name="I_DAD2" placeholder="나머지주소" maxlength="150" readonly="readonly">
		</div>
		<!-- 메모 -->
		<div class="form-group">
			<label>메모</label>
			<div>
				<input type="text" class="form-control" id="I_MMO" name="I_MMO" maxlength="4000" readonly="readonly">
			</div>
		</div>
	</form>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		 </div>
	</div>
</body>
</html>


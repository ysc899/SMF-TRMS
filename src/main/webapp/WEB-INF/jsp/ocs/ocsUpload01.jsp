<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>Main Quick Setup</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	
	var resultCode = "";
	var searchSetup, count;
	var I_VCD;
    var maxSize = 104857600;
    //16222780
	var maxSizeDtl = 100;
	var fileIdx = 1;
	var AttIdx = 1;
	var dataProvider1, gridView1, dataProvider2, gridView2;
	

	$(document).ready( function(){

		$("button[name='delBtn']").on("click",function(){
			var selected_inx=$("button[name='delBtn']").index(this);
			$("tr").eq(selected_inx).remove();
			fileIdx -- ;
			if(fileIdx == 0){
				AttIdx = 1;
				addTable();
			}
			//FileRow
		});
		$("button[name='addBtn']").on("click",function(){
			var selected_inx=$("button[name='addBtn']").index(this);
			var FileSeq=$("input[name='FileSeq']").val();
			
			$("input[name='attachments_"+FileSeq+"']").click();
		});
	});
	
	function onPopup(getValue){// 데이터를 그려주는 부분
		$("#I_HOS").val(getValue["I_HOS"]);
		$("#I_S009HOS").val(getValue["I_HOS"]);
		$("#I_FNM").val(getValue["I_FNM"]);
		if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }else{
	    	$('#hospHide').hide();
	    }
	    
	}
	function addTable(){
		AttIdx++;
		var strTable = "";
		strTable += "		<tr name='FileRow'>";
		strTable += "			<th scope=\"row\">접수파일</th>";
		strTable += "			<td>";
		strTable += "				<input type=\"hidden\" name=\"FileSeq\" value=\""+AttIdx+"\">";
		strTable += "				<input type=\"text\" name=\"attFileNm\" id=\"attFileNm_"+AttIdx+"\" readOnly>";
		strTable += "				<button type=\"button\" class=\"btn btn-success btn-sm\" name=\"addBtn\">첨부</button>";
		strTable += "				<button type=\"button\" class=\"btn btn-light btn-sm\" name=\"delBtn\">삭제</button>";
		strTable += "				<input type=\"file\" name=\"attachments_"+AttIdx+"\" style=\"display: none;\"   onchange=\"change(event,"+AttIdx+")\" >";
		strTable += "				<label id=\"SaveFlg_"+AttIdx+"\" name=\"SaveFlg\"></label>";
		strTable += "			</td>";
		strTable += "		</tr>";
		$('#tbody').append(strTable);
		
		window.setTimeout(function () {
			callResize();
		}, 100);

		$.each($("tr"),function(i,e){
			
			$("button[name='delBtn']").eq(i).off().on("click",function(){
				var selected_inx=$("button[name='delBtn']").index(this);
				$("tr").eq(selected_inx).remove();
				fileIdx -- ;
				if(fileIdx == 0){
					AttIdx = 1;
					addTable();
				}
			});
			$("button[name='addBtn']").eq(i).off().on("click",function(){
				var selected_inx=$("button[name='addBtn']").index(this);
				var FileSeq=$("input[name='FileSeq']").eq(selected_inx).val();
				$("input[name='attachments_"+FileSeq+"']").click();
			});
		});
		fileIdx++;
	}

	function change(e,labelIdx){
		var file=e.target.files[0];
		addFile(file,labelIdx);
	}
	
	function addFile(files,FileSeq){
		
		var fileName=files.name;
       	if(!isNull(fileName)){
       		var fileSize1 = files.size;
       		
       		if(fileSize1 > maxSize){
       			CallMessage("231","alert",[maxSizeDtl]); //첨부파일 사이즈는 {0} 이내로 가능합니다.
       			return;
       		}
       		
       		$("#SaveFlg_"+FileSeq).html("");
       		$("#attFileNm_"+FileSeq).val(fileName);
       	}
	}
	
	function saveCheck(){
		var check = true;
		$.each($("tr"),function(i,e){
			var attFileNm = 	$("input[name='attFileNm']").eq(i).val();
			var FileSeq = $("input[name='FileSeq']").eq(i).val();
			
			if(isNull(attFileNm)){
				CallMessage("201","alert",["접수파일은"]);	//필수 입력입니다.
				check = false;
				return false;
			}
			
			// 업로드파일 확장자 확인 / 2022.11-23 취약점 조치			
			//attFileNm = attFileNm.slice(attFileNm.indexOf(".") + 1).toLowerCase();			
			attFileNm = attFileNm.split('.').pop().toLowerCase();			
           	if(attFileNm != "xls" && attFileNm != "xlsx" && attFileNm != "csv"){
           		CallMessage("302","alert","");
           		check = false;
           		return false;
           	}
			
			if(!isNull($("#SaveFlg_"+FileSeq).html())){
				CallMessage("266","alert");	//업로드 파일을 변경해 주세요.
				check = false;
				return false;
			}
			
			// 파일명에 ',' 문자 포함되어 있는지 체크
			if(attFileNm.indexOf(',') >= 0){
				CallMessage("299","alert",["쉼표(,)"]);	
				check = false;
				return false;
			}
		});
		if(check){
			CallMessage("203","confirm","",save);	//저장하시겠습니까?
		}
	}

	function save(){
    	var formData = new FormData($("#searchForm")[0]);
		ajaxCall2("/ocsSave.do", formData);
	}

	var saveFlg = false;
	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/testReqList.do"){
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
		}
		else if(strUrl == "/testReqDtl.do"){
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
		}
		else if(strUrl == "/ocsSave.do"){
			saveFlg = true;
			var saveLabel = "";
			$.each($("input[name='FileSeq']"),function(i,e){
				//SaveFlg_1
				$("#SaveFlg_"+$(e).val()).removeClass("fontRed").removeClass("fontBlue");
				if(response["attachments_"+$(e).val()]   == "F"){
// 					saveFlg  = false;
// 					$("#SaveFlg_"+$(e).val()).html("업로드 실패");
// 					$("#SaveFlg_"+$(e).val()).html("업로드 실패"+response["attachments_"+$(e).val()+"ERRNM"]);
// 					$("#SaveFlg_"+$(e).val()).addClass("fontRed");
				}else{
					$("#SaveFlg_"+$(e).val()).html("업로드 성공");
					$("#SaveFlg_"+$(e).val()).addClass("fontBlue");
				}
				$("#SaveFlg_"+$(e).val()).html("업로드 성공");
				$("#SaveFlg_"+$(e).val()).addClass("fontBlue");
			});
			
			if(saveFlg){
				CallMessage("221","alert","",saveClose);	//저장을 완료하였습니다.
			}else{
				CallMessage("265","alert","");	//업로드 실패한 내역이 있습니다.
			}
		}
	}
	
	function saveClose(){
    	var data = [];
		if(saveFlg){
	    	data.push("/ocsUpload01.do");
		}
    	closeModal("fnCloseModal", data);
	}
	
	
	</script>
	
</head>
<body >
	<form id="searchForm" name="searchForm"  enctype="multipart/form-data" method="post">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
		<%--	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드) --%>
		<input id="I_LOGEFG" name="I_LOGEFG" type="hidden" value="FUP" />
		
		<div class="srch_box modal_tit" >
			<div class="srch_box_inner"  ><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
				<div class="srch_item hidden_srch"  id="hospHide" >
					<label for="" class="label_basic">병원</label>
                    <input type="text" style="width:203px" class="srch_put" id="I_FNM" name="I_FNM" readonly="readonly"  />
					<input id="I_HOS" name="I_HOS" type="hidden"  />
					<input id="I_S009HOS" name="I_S009HOS" type="hidden"  />
				</div>
			</div>
            <div class="btn_srch_box" style="width: 15%;text-align: right;">
			<button type="button" class="btn btn-darkBlue btn-sm" onclick="addTable()">파일추가</button>
            </div> 
        </div>
		<div class="tbl_type">
			<table class="table table-bordered table-condensed tbl_l-type">
				<colgroup>
					<col width="25%">
					<col >
				</colgroup>
	            <tbody id="tbody">
					<tr>
						<th scope="row">접수파일</th>
						<td>
							<input  type="text" name="attFileNm" id="attFileNm_1" readOnly >
							<button type="button" class="btn btn-success btn-sm" name="addBtn">첨부</button>
							<button type="button" class="btn btn-light btn-sm" name="delBtn">삭제</button>
							<input  type="file" name="attachments_1" onchange="change(event,1)"  style="display: none;" >
							<input  type="hidden" name="FileSeq" value="1" >
							<label id="SaveFlg_1"></label>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div class="modal-footer">
		<div class="min_btn_area">
			  <button type="button" class="btn btn-primary" onclick="saveCheck()">저장</button>
			  <button type="button" class="btn btn-info" data-dismiss="modal" onclick="saveClose('')">닫기</button>
		 </div>
	</div>
</body>
</html>


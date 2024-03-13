<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>검사항목조회</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>

	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>

	<%@ include file="/inc/reportPop.inc"%>
	<!-- rdUrl을 자바에서 받아서 사용하는 곳에서만 사용 -->
	<script type="text/javascript" src="<%=rdUrl%>/ReportingServer/html5/js/crownix-viewer.min.js"></script>
	<script type="text/javascript" src="<%=rdUrl%>/ReportingServer/html5/js/invoker/crownix-invoker.min.js"></script>
	<script type="text/javascript" src="/js/reportPop.js"></script>
	<script>

		var pscd = "";

		/*팝업 호출 Input 내역 설정*/
		function gridViewRead(gridValus){
			$("#I_GCD").val(gridValus["F010GCD"]);
			var formData = $("#popupForm").serializeArray();
			ajaxCall("/testItemDtl.do", formData);
		}

		// ajax 호출 result function
		function onResult(strUrl,response){
			if(strUrl == "/testItemDtl.do"){
				var msgCd = response.O_MSGCOD;
				//I_LOGURL
				if(msgCd == "200"){
					var resultList =  response.resultList;

					// url 있으면 qrcode 로 표시
					if (resultList[0]["T001URL"] != ""){
						let qrImg = document.createElement('img');
						qrImg.src = 'data:image/jpg;base64,'+ resultList[0]["T001URLQR"];
						qrImg.width = 64;
						qrImg.height = 64;
						qrImg.style = "vertical-align: middle;";
						let qrLink = document.createElement('a');
						qrLink.href = resultList[0]["T001URL"];
						qrLink.target = "_blank";
						qrLink.innerText = " E-Book 바로가기"
						document.getElementById("qrcodeUrl").appendChild(qrImg);
						document.getElementById("qrcodeUrl").appendChild(qrLink);

						//$('#qrcodeUrlJq').html({correctLevel	: QRErrorCorrectLevel.M, width: 64,height: 64,text: resultList[0]["T001URL"]});
						$("._tb_qr_url").css("text-align", "center");
					}else{
						// qrcode 가 없으면 _tb_qr_url 클래스를 지우고 해당 개체의 앞 td 에 colspan 을 2로 처리함.
						$("._tb_qr_url").prev().attr("colspan", "2");
						$("._tb_qr_url").remove();
					}

					$("#F010GCD").html(resultList[0]["F010GCD"]);
					$("#F010FKN").html(resultList[0]["F010FKN"]);
					$("#F010SNM").html(resultList[0]["F010SNM"]);
					$("#F010TCD").html(resultList[0]["F010TCD"]);
					$("#F010TNM").html(resultList[0]["F010TNM"]);
					$("#F010GBX").html(resultList[0]["F010GBX"]);
					$("#F010GBNM").html(resultList[0]["F010GBNM"]);
					$("#F010MSC").html(resultList[0]["F010MSC"]);
					$("#F010MSNM").html(resultList[0]["F010MSNM"]);
					$("#F010ACH").html(resultList[0]["F010ACH"]);
					$("#T001SAV").html(resultList[0]["T001SAV"]);
					$("#T001DAY").html(resultList[0]["T001DAY"]);
					$("#F010EED").html(resultList[0]["F010EED"]);
					$("#T001CONT").html(resultList[0]["T001CONT"]);
					$("#T001ETC").html(resultList[0]["T001ETC"]);
					//$("#T001URL").html(resultList[0]["T001URL"]);
					$("#T001REF").html(resultList[0]["T001REF"]);
					$("#T001FLG").html(resultList[0]["T001FLG"]);
					$("#T001LFLG").html(resultList[0]["T001LFLG"]);
					$("#F010STS").html(resultList[0]["F010STS"]);
					$("#F010ISC").html(resultList[0]["F010ISC"]);
					$("#F010HAK").html(resultList[0]["F010HAK"]);
					$("#SOCD").html(resultList[0]["SOCD"]);
					$("#BOCD").html(resultList[0]["BOCD"]);
					$("#SSSU").html(resultList[0]["SSSU"].format());
					$("#BSSU").html(resultList[0]["BSSU"].format());
					$("#SBSU").html(resultList[0]["SBSU"].format());
					$("#BBSU").html(resultList[0]["BBSU"].format());
					$("#TSSU").html(resultList[0]["TSSU"]);
					$("#DOCD").html(resultList[0]["DOCD"]);

					// 임시사용 (적용일:20200811) - 보험코드만 !!
					$("#DOCD_TMP").html(resultList[0]["DOCD_TMP"]);

					// 임시사용 (적용일:20200811) - 보험코드만 !!
					$("#OMK").html(resultList[0]["OMK"]);

					// LMB 코드 9999 데이터를 가져와야하는걸로 확인 (학술홍보팀:최지은 과장)
					/* var somk = resultList[0]["SOMK"];
                    var bomk = resultList[0]["BOMK"];
                    var omk = !isNull(somk)? somk :"";
                    if(somk!=bomk){
                        omk += !isNull(bomk)? ",":"";
                        omk += bomk;
                    } */

					// 2020.02.11 (검사마스터에 분류번호 데이터가 없는 경우, undefined 문자가 포함되버림.)
					//$("#OMK").html(omk); //변경전
					// LMB 코드 9999 데이터를 가져와야하는걸로 확인 (학술홍보팀:최지은 과장)
					//$("#OMK").html(omk.replace("undefined","")); //변경후

					$("#VESSIMG").attr("src",resultList[0]["VESSIMG"]);

					callResize()  ;
				}
			}
		}

		function printView(){

			var i_gcd = $("#I_GCD").val();
			//alert(i_gcd+", <%=systemUrl%>");

			//승인 안되면 출력 못함
			var w = window.screen.width ;
			var h = window.screen.height ;

			//reportPop.inc의 셋팅값 받기
			var viewerUrl = "<%=viewerUrl%>";

			// mrd_path 기존_backup
			<%-- var mrd_path = "<%=systemUrl%>/mrd/testItem.mrd"; --%>
			var mrd_path = "testItem_new.mrd";
			//var mrd_path = "<%=systemUrl%>/mrd/testItem_noneimg.mrd";
			var rdServerSaveDir = "<%=rdSaveDir%>";
			var rd_param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR['<%=ss_cor%>']UID['<%=ss_uid%>']GCD['"+i_gcd+"'] /rimageopt render [1] /rsaveopt [4288] /rusecxlib /rpdfexportapitype [0] /rimagexdpi [110]  /rimageydpi [110] /p /rusesystemfont";

			var systemDownDir = "<%=rdSysSaveDir%>/testItem/";
			<%-- 		var systemDownDir = "<%=rdSysSaveDir%>/testItem/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>"; --%>
			//alert(systemDownDir);

			//파일 명만 셋팅 , 확장자는 따로 셋팅
			//alert($("#F010FKN").html());
			var downFileName = $("#F010FKN").html();
			downFileName = downFileName.replace(/\,/,' ');


			var param="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path+"&rdServerSaveDir="+rdServerSaveDir+"&param="+rd_param+"&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);

			var f = document.reportForm;
			f.viewerUrl.value			=  viewerUrl;
			f.mrd_path.value			=  mrd_path;
			f.rdServerSaveDir.value		=  rdServerSaveDir;
			f.param.value				=  rd_param;
			f.systemDownDir.value		=  systemDownDir;
			f.downFileName.value		=  (downFileName);

			showReportViewDialog("/crownixViewer.do" , w, h, printcallback);
		}

		function printcallback(){

		}
	</script>

</head>
<body>
<form method="post" action="" name="reportForm">
	<input type="hidden" name="viewerUrl" />
	<input type="hidden" name="mrd_path" />
	<input type="hidden" name="rdServerSaveDir" />
	<input type="hidden" name="param" />
	<input type="hidden" name="systemDownDir" />
	<input type="hidden" name="downFileName" />
	<input type="hidden" name="imgObj" />
</form>
<div class="tbl_type" style="width:920px; ">
	<form id="popupForm" name="popupForm">
		<input id="I_GCD" name="I_GCD" type="hidden"/>
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<table  class="table table-bordered table-condensed tbl_l-type">
			<colgroup>
				<col width="80px">
				<col width="150px">
				<col width="170px">
				<col width="100px">
				<col width="200px">
				<col width="200px">
			</colgroup>
			<tr>
				<th scope="row">검사명</th>
				<td id="F010FKN" colspan="2"> </td>
				<th scope="row">검사코드</th>
				<td id="F010GCD"> </td>
				<th scope="row" class="_tb_qr_url">학술제작물</th>
			</tr>
			<tr>
				<th scope="row">검사일</th>
				<td id="T001DAY" colspan="2"> </td>
				<th scope="row">검체(ml)</th>
				<td id="F010TNM"> </td>
				<td rowspan="2" class="_tb_qr_url"><div id="qrcodeUrl"></div></td>
			</tr>
			<tr>
				<!-- <th scope="row">검사일정<br>(소요일)</th>
                <td  colspan="2"> <label id="T001DAY"></label> (<label id="F010EED"></label>일)</td>
                 -->

				<th scope="row">소요일</th>
				<td colspan="2"><label id="F010EED"></label>일</td>
				<th scope="row">보존방법</th>
				<td id="T001SAV"> </td>
				<td style="display: none;" class="_tb_qr_url"></td>
			</tr>
			<tr>
				<th scope="row">검사방법</th>
				<td id="F010MSNM" colspan="2"> </td>
				<th scope="row">용기명</th>
				<td id="F010GBNM" colspan="2"> </td>
			</tr>
			<tr>
				<th scope="row">보험코드</th>
				<!-- <td id="DOCD" colspan="2"> </td> -->
				<td id="DOCD_TMP" colspan="2"> </td>
				<th scope="row" rowspan="4">검체용기</th>
				<td rowspan="4" colspan="2"><img id="VESSIMG" width="395px" height="295px"/> </td>
			</tr>
			<tr>
				<th scope="row">검사수가</th>
				<td id="TSSU" colspan="2"> </td>
			</tr>
			<tr>
				<th scope="row">분류번호</th>
				<td id="OMK" colspan="2"> </td>
			</tr>
			<tr>
				<th scope="row">참고치</th>
				<td id="T001REF" colspan="2"> </td>
			</tr>
			<tr>
				<th scope="row">임상정보</th>
				<td id="T001CONT" colspan="5" style="word-break: normal;"> </td>
			</tr>
			<tr>
				<th scope="row">주의사항</th>
				<td id="T001ETC" colspan="5" > </td>
			</tr>
		</table>
	</form>
	<div class="modal-footer">
		<div class="min_btn_area">
			<button type="button" class="btn btn-white" onclick="printView()">출력</button>
			<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
		</div>
	</div>
</div>
</body>
</html>


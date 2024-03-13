<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
	<%@ include file="../include/header_popup.jsp"%>

	<!--container-->
	<div id="container" style="margin-top:20px; ">
		<div class="itemD_area">
			<form id="testItemForm" name="searchForm">
				<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
				<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->
				<input id="I_GCD" name="I_GCD" type="hidden" value="${gcd }"/>
				<input id="LOGIN_YN" type="hidden" value="${loginYn}" />
				<input id="VIEW_YN" type="hidden"/>
				<input id="DOCD_YN" type="hidden"/>

			</form>
			<div id="newAreaTestItem">

				<div class="itemD_table01">
					<table>
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="20%">
							<col width="30%">
						</colgroup>
						<thead>

						</thead>
						<tbody>
						<tr>
							<td colspan="3" rowspan="2"><h2 style="color: black;">검사항목 상세</h2></td>
							<th >학술 제작물</th>
						</tr>
						<tr>
							<td colspan="1" rowspan="2" align="center"><div id="qrcodeUrl"></div>
							</td>
						</tr>
						<tr>
							<th style="width: 10%">검사명</th>
							<td colspan="2" data-value="#F010FKN"></td>
						</tr>

						<tr>
							<th style="width: 10%">검사코드</th>
							<td data-value="#F010GCD"></td>
							<th style="width: 10%">검사방법</th>
							<td data-value="#F010MSNM"></td>
						</tr>

						<tr>
							<th style="width: 10%">검사일</th>
							<td data-value="#T001DAY"></td>
							<th style="width: 10%">검체 종류</th>
							<td data-value="#F010TNM"></td>
						</tr>

						<tr>
							<th style="width: 10%">소요일</th>
							<td><label data-value="#F010EED"></label>일</td>
							<th style="width: 10%">검체 채취량(ml)</th>
							<td data-value="#F010GTQ"></td>
						</tr>

						<tr>
							<th style="width: 10%">보험코드</th>
							<td data-value="#DOCD_TMP"></td>
							<th style="width: 10%">검체 소요량(ml)</th>
							<td data-value="#F010STQ"></td>
						</tr>

						<tr>
							<th style="width: 10%">검사수가</br><font color="blue" size="1px;">*질가산료를 포함하지 않습니다.</font></th>
							<td data-value="#TSSU"></td>
							<th style="width: 10%">보존방법</th>
							<td data-value="#T001SAV"></td>
						</tr>

						<tr>
							<th style="width: 10%">분류번호</th>
							<td data-value="#OMK"></td>
							<th style="width: 10%">용기명</th>
							<td data-value="#F010GBNM"></td>
						</tr>

						<tr>
							<th style="width: 10%">참고치</th>
							<td colspan="3" data-value="#T001REF"></td>
						</tr>

						<tr>
							<th style="width: 10%">임상정보</th>
							<td colspan="3" data-value="#T001CONT"></td>
						</tr>

						<tr>
							<th style="width: 10%">주의사항</th>
							<td colspan="3" data-value="#T001ETC"></td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div id="oldAreaTestItem">
				<h2>검사항목 상세</h2>

				<div class="itemD_table01">
					<table>
						<thead>

						</thead>
						<tbody>
						<tr>
							<th style="width: 10%">검사명</th>
							<td colspan="3" data-value="#F010FKN"></td>
						</tr>

						<tr>
							<th style="width: 10%">검사코드</th>
							<td data-value="#F010GCD"></td>
							<th style="width: 10%">검사방법</th>
							<td data-value="#F010MSNM"></td>
						</tr>

						<tr>
							<th style="width: 10%">검사일</th>
							<td data-value="#T001DAY"></td>
							<th style="width: 10%">검체 종류</th>
							<td data-value="#F010TNM"></td>
						</tr>

						<tr>
							<th style="width: 10%">소요일</th>
							<td><label data-value="#F010EED"></label>일</td>
							<th style="width: 10%">검체 채취량(ml)</th>
							<td data-value="#F010GTQ"></td>
						</tr>

						<tr>
							<th style="width: 10%">보험코드</th>
							<td data-value="#DOCD_TMP"></td>
							<th style="width: 10%">검체 소요량(ml)</th>
							<td data-value="#F010STQ"></td>
						</tr>

						<tr>
							<th style="width: 10%">검사수가</br><font color="blue" size="1px;">*질가산료를 포함하지 않습니다.</font></th>
							<td data-value="#TSSU"></td>
							<th style="width: 10%">보존방법</th>
							<td data-value="#T001SAV"></td>
						</tr>

						<tr>
							<th style="width: 10%">분류번호</th>
							<td data-value="#OMK"></td>
							<th style="width: 10%">용기명</th>
							<td data-value="#F010GBNM"></td>
						</tr>

						<tr>
							<th style="width: 10%">참고치</th>
							<td colspan="3" data-value="#T001REF"></td>
						</tr>

						<tr>
							<th style="width: 10%">임상정보</th>
							<td colspan="3" data-value="#T001CONT"></td>
						</tr>

						<tr>
							<th style="width: 10%">주의사항</th>
							<td colspan="3" data-value="#T001ETC"></td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="itemD_table02">
				<p>검체용기</p>

				<table>
					<thead></thead>
					<tbody>
					<tr>
						<td>
							<img src="/shared_files/vessel/NOIMAGE.gif" alt="검체용기이미지01" id="VESSIMG">
						</td>
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

	var I_LOGMNU = "M_TESTITEM";
	var I_LOGMNM = "모바일 검사항목 조회";


	function dataResult(){

		var formData = $("#testItemForm").serializeArray();
		ajaxCall("/mobileTestItemDtl.do",formData);
		$(".blockPage").css('margin-left', '-125px');

	}


	function onResult(strUrl,response){
		if(strUrl == "/mobileTestItemDtl.do"){

			var msgCd = response.O_MSGCOD;

			if(msgCd == "200"){
				var resultList =  response.resultList;
				// url 있으면 qrcode 로 표시
				if (resultList[0]["T001URL"] != ""){
					$("div#oldAreaTestItem").remove();
					/*let qrImg = document.createElement('img');
					qrImg.src = 'data:image/jpg;base64,'+ resultList[0]["T001URLQR"];
					qrImg.width = 64;
					qrImg.height = 64;
					document.getElementById("qrcodeUrl").appendChild(qrImg);*/
					let qrLink = document.createElement('a');
					qrLink.href = resultList[0]["T001URL"];
					qrLink.target = "_blank";
					qrLink.innerText = " E-Book 바로가기"
					document.getElementById("qrcodeUrl").appendChild(qrLink);
				}else{
					// url 없으면
					$("div#newAreaTestItem").remove();
				}

				$("td[data-value='#F010GCD']").html(resultList[0]["F010GCD"]);
				$("td[data-value='#F010FKN").html(resultList[0]["F010FKN"]);
				$("td[data-value='#F010SNM").html(resultList[0]["F010SNM"]);
				$("td[data-value='#F010TCD").html(resultList[0]["F010TCD"]);
				$("td[data-value='#F010TNM").html(resultList[0]["F010TNM"]);
				$("td[data-value='#F010GBX").html(resultList[0]["F010GBX"]);
				$("td[data-value='#F010GBNM").html(resultList[0]["F010GBNM"]);
				$("td[data-value='#F010MSC").html(resultList[0]["F010MSC"]);
				$("td[data-value='#F010MSNM").html(resultList[0]["F010MSNM"]);
				$("td[data-value='#F010ACH").html(resultList[0]["F010ACH"]);
				$("td[data-value='#T001SAV").html(resultList[0]["T001SAV"]);
				$("td[data-value='#T001DAY").html(resultList[0]["T001DAY"]);
				$("td[data-value='#F010EED").html(resultList[0]["F010EED"]);
				$("td[data-value='#T001CONT").html(resultList[0]["T001CONT"]);
				$("td[data-value='#T001ETC").html(resultList[0]["T001ETC"]);
				$("td[data-value='#T001REF").html(resultList[0]["T001REF"]);
				$("td[data-value='#T001FLG").html(resultList[0]["T001FLG"]);
				$("td[data-value='#T001LFLG").html(resultList[0]["T001LFLG"]);
				$("td[data-value='#F010STS").html(resultList[0]["F010STS"]);
				$("td[data-value='#F010ISC").html(resultList[0]["F010ISC"]);
				$("td[data-value='#F010HAK").html(resultList[0]["F010HAK"]);
				$("td[data-value='#F010STQ").html(resultList[0]["F010STQ"]);
				$("td[data-value='#F010GTQ").html(resultList[0]["F010GTQ"]);
				$("td[data-value='#SOCD").html(resultList[0]["SOCD"]);
				$("td[data-value='#BOCD").html(resultList[0]["BOCD"]);
				$("td[data-value='#SSSU").html(resultList[0]["SSSU"].format());
				$("td[data-value='#BSSU").html(resultList[0]["BSSU"].format());
				$("td[data-value='#SBSU").html(resultList[0]["SBSU"].format());
				$("td[data-value='#BBSU").html(resultList[0]["BBSU"].format());
				$("td[data-value='#DOCD").html(resultList[0]["DOCD"]);

				// 임시사용 (적용일:20200811) - 보험코드만 !!
				$("td[data-value='#DOCD_TMP").html(resultList[0]["DOCD_TMP"]);

				/* 검사수가 노출 로그인 비로그인 여부 체크 추가 */
				$("input#DOCD_YN").val(resultList[0]["DOCD_TMP"]);
				$("input#VIEW_YN").val(resultList[0]["VIEW_YN"]);

				if($("input#DOCD_YN").val() == ""){         //보험코드가 null 일때 무조건 로그인 해야만 볼수있다
					if($("input#LOGIN_YN").val() == "true"){
						$("td[data-value='#TSSU").html(resultList[0]["TSSU"]);
					}else if($("#LOGIN_YN").val() == "false"){
						$("td[data-value='#TSSU").html(" ");
					}
				}else if($("input#DOCD_YN").val() != ""){  								//보험코드가 null 이 아닐때
					if($("input#VIEW_YN").val() == "Y"){ 								// 분류코드가  노로 시작할경우
						if($("input#LOGIN_YN").val() == "true"){							// 로그인 해야만 볼수있다
							$("td[data-value='#TSSU").html(resultList[0]["TSSU"]);
						}else if($("input#LOGIN_YN").val() == "false"){					// 로그인 안하면 못봄
							$("td[data-value='#TSSU").html(" ");
						}
					}else if($("input#VIEW_YN").val() == "N"){						//노 가 아닌경우 노출
						$("td[data-value='#TSSU").html(resultList[0]["TSSU"]);
					}
				}

				// 임시사용 (적용일:20200811) - 보험코드만 !!
				$("td[data-value='#OMK").html(resultList[0]["OMK"]);

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

			}


		}
	}


	$(document).ready(function(){

		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);


// 		alert('${seq}');
		dataResult();

		$(".area_name").hide();



	});
</script>

</html>
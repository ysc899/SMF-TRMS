<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=yes,initial-scale=1">
<title>::씨젠의료재단 모바일::</title>
	<%@ include file="/inc/mobile-common.inc"%>
	
	
</head>
<body>

	<form id="searchForm" name="searchForm">
		<!-- cjw -->
		<input id="I_COR" name="I_COR" type="hidden" value="NML"/>
		<input id="DAT" name="DAT" type="hidden" value="${I_DTLDAT}"/>
		<input id="JNO" name="JNO" type="hidden" value="${I_DTLJNO}"/>
		
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value='${I_LOGMNU}'/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="${I_LOGMNM}"/>
		<input id="I_ICNT" name="I_ICNT" type="hidden"> 				  	<!-- 그리드에 보여줄 건수 -->
		<input id="I_CNT" name="I_CNT" value="40" type="hidden"> 			<!-- 그리드에 보여줄 건수 -->
		<input id="I_ROW" name="I_ROW" type="hidden"> 				  	<!-- 그리드에 보여줄 시작위치 -->
		<input id="I_IROW" name="I_IROW" value="0" type="hidden"> 				  	<!-- 그리드에 보여줄 시작위치 -->
		
		<input id="I_PECF" name="I_PECF" type="hidden" value="${I_PECF}"> 			
		<input id="I_PNN" name="I_PNN" type="hidden" value="${I_PNN}">
		<!-- <input id="I_PHOS" name="I_PHOS" type="hidden"> -->
		
		<!-- Y = 수진자명，챠트번호　조건만 들어가는 기본 조건 -->
		<input id="I_PINQ" name="I_PINQ" type="hidden" value="${I_PINQ}">
		
		<input id="I_DTLDAT" name="I_DTLDAT" type="hidden" value="${I_DTLDAT}">
		<input id="I_DTLJNO" name="I_DTLJNO" type="hidden" value="${I_DTLJNO}">
		
		<input id="I_GCD" name="I_GCD" type="hidden" value="${I_GCD}">
		<input id="I_ACD" name="I_ACD" type="hidden" value="${I_ACD}">
		<input id="I_CHN" name="I_CHN" type="hidden" value="${I_CHN}">
				
		<input id="I_GAC" name="I_GAC" type="hidden" value="${I_GAC}">
		<input id="I_GAC1" name="I_GAC1" type="hidden" value="${I_GAC2}">
		<input id="I_GAC2" name="I_GAC2" type="hidden" value="${I_GAC3}">
		<input id="I_DT" name="I_DT" type="hidden" value="${I_DT}">
		<input id="I_BDT1" name="I_BDT1" type="hidden" value="${I_BDT1}">
		<input id="I_BDT2" name="I_BDT2" type="hidden" value="${I_BDT2}">
		
		<input id="I_RBN" name="I_RBN" type="hidden" value="${I_RBN}">
		<!-- 병원코드 -->
		<input id="I_PHOS" name="I_PHOS" type="hidden" value="${I_PHOS}">
		<!-- 추가 검색 조건 공백 입력 -->
		<input id="I_HAK" name="I_HAK" type="hidden" value="${I_HAK}">
		<input id="I_STS" name="I_STS" type="hidden" value="${I_STS}">
		<input id="I_PETC" name="I_PETC" type="hidden" value="${I_PETC}">
		<input id="I_PJKNF" name="I_PJKNF" type="hidden" value="${I_PJKNF}">
		<input id="I_PJKN" name="I_PJKN" type="hidden" value="${I_PJKN}">
		
		<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  시작-->
		<!-- C:최초 조회, N:다음, P:이전 -->
		<input id="I_IGBN" name="I_IGBN" type="hidden" value="${I_IGBN}">
		<!-- 이전/다음 시작 접수일자 : 최초 조회일 경우 0 -->
		<input id="I_IDAT" name="I_IDAT" type="hidden" value="${I_IDAT}">
		<!-- 이전/다음 시작 접수번호 : 최초 조회일 경우 0 -->
		<input id="I_IJNO" name="I_IJNO" type="hidden" value="${I_IJNO}">
		<!-- 기본 쿼리 외 일 경우 이전/다음처리를 위한 변수  끝-->
	</form>




	<div id="app_wrap">
		<%@ include file="../include/header_popup.jsp"%>
		
		<!--container-->
		<div id="container" style="margin-top:50px;margin-bottom:120px;">
			<div class="detail_area">
				<h2>검사결과 조회</h2>
				
				<!--detail_box-->
				<div class="detail_box">
					<table class="detail_table01" id="infoDiv">
						<tbody>
							<tr>
								<th>수신자</th>
								<td id="NAM"></td>
								<th>차트번호</th>
								<td id="CHN"></td>
							</tr>
	
							<tr>
								<th>접수일자</th>
								<td id="DAT_HTML"></td>
								<th>주민등록번호</th>
								<td id="JN"></td>
							</tr>
	
							<tr>
								<th style="font-size:12px;">담당의/과/병동</th>
								<td id="DPTN"></td>
								<th>성별</th>
								<td id="SEX"></td>
							</tr>
	
							<tr>
								<th>나이</th>
								<td id="AGE"></td>
								<th>검체종류</th>
								<td id="ETCINF"></td>
							</tr>
	
							<tr>
								<th>검체번호</th>
								<td id="ETC"></td>
								<th>의료기관</th>
								<td id="HOSN"></td>
							</tr>
	
							<tr>
								<th>병원코드</th>
								<td id="HOS"></td>
								<th>접수번호</th>
								<td id="JNO_HTML"></td>
							</tr>
						</tbody>
					</table>
					
					<!--detail_txt01-->
					<div class="detail_txt01">
					<table class="detail_table02">
						<colgroup>
							<col width="">
							<col width="">
							<col width="150px;">
							<col width="">
							<col width="">
							<col width="">
							<col width="">
						</colgroup>
						<thead>
							<tr>
								<th style="width:12.4%;" class="sticky-th">보험코드</th>
								<th class="sticky-th">검사항목</th>
								<th class="sticky-th">결과</th>
								<th class="sticky-th">참고치</th>
								<th class="sticky-th">H/L</th>
								<th class="sticky-th">보고일자</th>
								<th class="sticky-th">시계열</th>
							</tr>
						</thead>

						<tbody id="rstResultBody" name="rstResultBody">
						</tbody>
					</table>
					</div>
					<!--//detail_txt01 End-->
					
					
					<!-- mastResult start -->
					<div class="detail_txt022 _DivMastResult">
						Result
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivMastResult">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td>
										<div id="MastResult"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- mastResult end -->
					
					<!-- mastResult_Reference start -->
					<div class="detail_txt022 _DivMastResult_Reference">
						Specific IgE Reference Range
					</div>
					
					<div class="detail_txt02 _DivMastResult_Reference">
						<table class="detail_table03">
							<tbody id="mastrefer">
								<tr>
									<td style="border-right: solid 1px; border-bottom: solid 1px;" align="center">IU/mL</td>
									<td style="border-right: solid 1px; border-bottom: solid 1px;" align="center">Class</td>
									<td style="border-bottom: solid 1px;"align="center">Description</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left"><=0.34</td>
									<td style="border-right: solid 1px;" align="center">0</td>
									<td align="left">Not found</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left">0.35~0.69</td>
									<td style="border-right: solid 1px;" align="center">1</td>
									<td align="left">Weak</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left">0.70~3.49</td>
									<td style="border-right: solid 1px;" align="center">2</td>
									<td align="left">Description</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left">3.35~17.49</td>
									<td style="border-right: solid 1px;" align="center">3</td>
									<td align="left">Moderate</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left">17.50~49.99</td>
									<td style="border-right: solid 1px;" align="center">4</td>
									<td align="left">Strong</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left">50.00~99.99</td>
									<td style="border-right: solid 1px;" align="center">5</td>
									<td align="left">Very strong</td>
								</tr>
								<tr>
									<td style="border-right: solid 1px;" align="left">>=100</td>
									<td style="border-right: solid 1px;" align="center">6</td>
									<td align="left">Extremely strong</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- mastResult_Reference end -->
						
					
					<!-- ria start -->
					<div class="detail_txt022 _DivRia">
						Prenatal Screening Test
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivRia">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td>
										<div id="Ria"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- ria end -->
					
					
						
					
					<!-- Hematoma start -->
					<div class="detail_txt022 _DivHematoma">
						혈종검사소견
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivHematoma">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="Hematoma"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- Hematoma end -->
						
					
					<!-- Obgy start -->
					<div class="detail_txt022 _DivObgy">
						산전검사소견
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivObgy">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="Obgy"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- Obgy end -->
					
					
					<!-- Hasimg start -->
					<div class="detail_txt022 _DivHasimg">
						Image results
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivHasimg">
						<table class="detail_table03">
							<tbody id="hasimg">
							</tbody>
						</table>
					</div>
					<!-- Hasimg end -->

					
					<!-- Popphoto start -->
					<div class="detail_txt022 _DivPopphoto">
						Image results
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivPopphoto">
						<table class="detail_table03">
							<tbody id="Popphoto">
							</tbody>
						</table>
					</div>
					<!-- Popphoto end -->

					
					<!-- Microbio start -->
					<div class="detail_txt022 _DivMicrobio">
						Microbio Result
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivMicrobio">
						<table class="detail_table03">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody id="Microbio">
							
							</tbody>
						</table>
					</div>
					<!-- Microbio end -->

					
					<!-- LmbYN start -->
					<div class="detail_txt022 _DivLmbYN" id="DivLmbYNTitle">
						면역병리 진단보고
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivLmbYN">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="LmbYN"  style="font-family:굴림체;"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- LmbYN end -->

					
					<!-- PathologyType start -->
					<div class="detail_txt022 _DivPathologyType" id="DivPathologyTypeTitle">
						면역병리 진단보고
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivPathologyType">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="PathologyType"  style="font-family:굴림체;"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- PathologyType end -->

					
					<!-- PathologyType2 start -->
					<div class="detail_txt022 _DivPathologyType2" id="DivPathologyTypeTitle">
						면역병리 진단보고
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivPathologyType2">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="PathologyType2"  style="font-family:굴림체;"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- PathologyType2 end -->
					

					
					<!-- CytologyType start -->
					<div class="detail_txt022 _DivCytologyType">
						세포병리 진단보고
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivCytologyType">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="CytologyType"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- CytologyType end -->

					
					<!-- CytologyType2 start -->
					<div class="detail_txt022 _DivCytologyType2">
						세포병리 진단보고
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivCytologyType2">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="CytologyType2"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- CytologyType2 end -->

					
					<!-- CytologyType3 start -->
					<div class="detail_txt022 _DivCytologyType3">
						세포병리 진단보고
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivCytologyType3">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="CytologyType3"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- CytologyType3 end -->
					
					<!-- Cytogene start -->
					<div class="detail_txt022 _DivCytogene">
						Cytogenetics Result
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivCytogene">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="Cytogene"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- Cytogene end -->
					
					
					<!-- Remarktext start -->
					<div class="detail_txt022 _DivRemarktext">
						Remark
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivRemarktext">
						<table class="detail_table03">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="Remarktext" style="font-family:굴림체;"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- Remarktext end -->
					
					<!-- Remarktext start -->
					<div class="detail_txt022 _DivCoronaCT">
						Corona CT
					</div>

					<!--detail_txt-->
					<div class="detail_txt02 _DivCoronaCT" style="height:100%;">
						<table class="detail_table03" style="width:100%;">
							<tbody>
								<tr>
									<td style="text-align:left;">
										<div id="DivCoronaCT_val" style="font-family:굴림체;"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- Remarktext end -->

						
					<!--//detail_txt02 End-->
					
				
				</div>
			
		</div>
		<!--//container End-->
		
<%-- 		<%@ include file="../include/navigation.jsp"%> --%>
	</div>



<!-- 	<h1>메인 페이지</h1> -->
<!-- 	<a class="btn btn-default" href="/mobileLogin.do">로그인</a> -->
<!-- 	<button class="btn btn-info" id="logout">로그 아웃</button> -->
</body>

<script type="text/javascript">

	var I_LOGMNU = "M_RSTUSERTABLE";
	var I_LOGMNM = "모바일 수진자별 결과관리(Table)";
	
	var s_uid = "<%= ss_uid %>";
	var s_ip =  "<%= ss_ip %>";
	var s_cor = "<%= ss_cor %>";
	
	var s_ecf = "<%= ss_ecf %>";
	var s_hos = "<%= ss_hos %>";
	var s_nam = "<%= ss_nam %>";
	
	var s_syn = "<%= ss_syn %>";
	
	
	
	
	
	
	var mast_class1="<b>class1</b> : ";
	var mast_class2="<b>class2</b> : ";
	var mast_class3="<b>class3</b> : ";
	var mast_class4="<b>class4</b> : ";
	var mast_class5="<b>class5</b> : ";
	var mast_class6="<b>class6</b> : ";
	var mast_str="N"; // MSAS검사 class 검사결과 존재여부 flag
	var rlt_class = "";
	
	var ria_str="N"; // Ria
	var riaData="N";	
	var riaResult="";
	var remarkPositiveGcdn=""; // positive Gcdn내용
	var remarkDataRd = "";
	var isTripleNew = "N";
	
	var hematoma_str="N";
	var hematomaStr = "";
		
	var obgyStr = "";
	var obgy_str="N";
		
	var img_str="N";
	
	var photo_str="N";
	
	//img_data
	var img_dpt = "";
	var url_str = "";
	var cnt_str = 0;
	var img_html = "";
	var pdf_html = "";
	var pdf_htmlx = "";
	
	
	var img_cnt = 0;
	var img_sdb = "";
	var img_rotate = "";
	
	//Microbio
	var microbio_str = "N";
	var microbio_html = "";
	
	var microbio119yn = "N";
	var microbio118yn = "N";
	var gac = "";
	var gac1 = "";
	var gac2 = "";
	var dt = "";
	var bdt1 = "";
	var bdt2 = "";
	
	//LmbYN
	var LmbYN_str = "N";
	var LmbYN_html = "";
	var Mw106rm3 = "N"; //이전
	var Mw106rms6 = "N"; //이후
	
	//pathologyType
	var pathologyType_str = "N";
	var pathologyType_html = "";
	//pathologyType2
	var pathologyType2_str = "N";
	var pathologyType2_html = "";
	
	var pathTitleYn = "N";
	
	var CytologyType_str = "N";
	var CytologyType_html = "";
	var CytologyType2_str = "N";
	var CytologyType2_html = "";
	var CytologyType3_str = "N";
	var CytologyType3_html = "";
	
	var Cytogene_str = "N";
	var Cytogene_prc = "N";
	var Cytogene_html = "";
	
	
	var remark_str="N"; // remark 검사결과 존재여부 flag
	var remark_html = "";
	var remarkPositiveYn ="N";
	var remarkData=""; // remark 내용 검사결과 존재여부 flag
	var page_count = 0;		// 페이지 확인 0이면 첫 페이지
	
	//RD DATA
	var rd_dat = 0;
	var rd_jno = 0;
	var rd_rgn1 = "";
	var rd_rgn2 = "";
	var rd_rgn3 = "";
	var rd_hak = "";
	var rd_pnn = "";
	var rd_gcd = "";
	var rd_acd = "";
	
	
	function init(){
// 		$('#DivMastResult').hide();
		$('._DivMastResult').hide();
		$('._DivMastResult_Reference').hide();
		$('._DivRia').hide();
		$('._DivHematoma').hide();
		$('._DivObgy').hide();
		$('._DivHasimg').hide();
		$('._DivPopphoto').hide();
		$('._DivMicrobio').hide();
		$('._DivLmbYN').hide();
		$('._DivPathologyType').hide();
		$('._DivPathologyType2').hide();
		$('._DivCytologyType').hide();
		$('._DivCytologyType2').hide();
		$('._DivCytologyType3').hide();
		$('._DivCytogene').hide();
		$('._DivRemarktext').hide();
		$('._DivCoronaCT').hide();
	}
	
	function dataResult(){
		//상세 초기화
		init();

		//rd_gcd = data.GCD;
		//rd_acd = data.ACD;
		//console.log("rd_rgn1 = " + rd_rgn1);
		var formData = $("#searchForm").serialize();
		img_cnt = 0;
		img_sdb = "";
		img_rotate = "";
		
		
		var rstUserParam = "";
		rstUserParam = "&I_DAT="+$('#I_DTLDAT').val();
		rstUserParam += "&I_JNO="+$('#I_DTLJNO').val();
		
		formData += rstUserParam;
		var url = "/rstUserDtl.do";
		
		callLoading(null,"on");
		$(".blockPage").css('margin-left', '-125px');
		
		calLCnt = 1;
		ajaxCall(url, formData, false);
	}
	
	
	function onResult(strUrl,response){
		if(strUrl == "/rstUserDtl.do"){
			
			callLoading(null,"off");
			
			//gridView1.setCurrent(Param_POT);
			//환자정보
			var rstUserDtl =  response.rstUserDtl;
			//console.log(rstUserDtl);
			$('#NAM').html(rstUserDtl[0]["NAM"]);
			$('#CHN').html(rstUserDtl[0]["CHN"]);
			//console.log(rstUserDtl[0].DAT);
			var datData = rstUserDtl[0].DAT.toString(); 
			var datCustom = datData.substring(0,4)+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			//var datCustom = datData.substring(0,4).replace("2029","2020")+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			
			/* IDR 유승현 날짜형식변경 제거 2022.08.11			
			datCustom = datData.substring(0,4).replace("2000","2020").replace("2027","2021").replace("2028","2021").replace("2029","2021").replace("2030","2021").replace("2023","2022").replace("2024","2022").replace("2025","2022").replace("2026","2022").replace("2037","2022").replace("2038","2022").replace("2039","2022")
						+"-"+datData.substring(4,6)
						+"-"+datData.substring(6,8);
			*/
			
			/* if(datData.substring(0,4) == "2027" && datData.substring(4,6) == "12"){
				datCustom = datData.substring(0,4).replace("2027","2020")+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			}else if(datData.substring(0,4) == "2029" && datData.substring(4,6) != "12"){
				datCustom = datData.substring(0,4).replace("2029","2021")+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			}else{
				datCustom = datData.substring(0,4)+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
			} */
			
			//var datCustom = datData.substring(0,4);
			if(rstUserDtl[0].DAT != 0){
				$('#DAT_HTML').html(datCustom);	
			} else {
				$('#DAT_HTML').html(datData);
			}
			var dptn = rstUserDtl[0]["DPTN"];
			if (dptn == "//"){
				$('#DPTN').html();
			} else {
				$('#DPTN').html(rstUserDtl[0]["DPTN"]);
			}
			
			$('#JN').html(rstUserDtl[0]["JN"]);
			//$('#DPTN').html(rstUserDtl[0]["DPTN"]);
			$('#SEX').html(rstUserDtl[0]["SEX"]);
			$('#AGE').html(rstUserDtl[0]["AGE"]);
			$('#ETCINF').html(rstUserDtl[0]["ETCINF"]);
			$('#ETC').html(rstUserDtl[0]["ETC"]);
			$('#HOSN').html(rstUserDtl[0]["HOSN"]);
			$('#HOS').html(rstUserDtl[0]["HOS"]);
			$('#JNO_HTML').html(rstUserDtl[0]["JNO"]);
			
			$('#DAT').html(rstUserDtl[0]["DAT"]);
			$('#JNO').html(rstUserDtl[0]["JNO"]);

			// MAST결과
			mast_class1="<b>class1</b> : ";
			mast_class2="<b>class2</b> : ";
			mast_class3="<b>class3</b> : ";
			mast_class4="<b>class4</b> : ";
			mast_class5="<b>class5</b> : ";
			mast_class6="<b>class6</b> : ";
			
			//console.log("### 111 mast_class6 :: "+mast_class6);
			//alert("### 111 mast_class6 :: "+mast_class6);
			
			rlt_class = "";
			
			//수진자 목록 상세
			var rstUserDtl2 =  response.rstUserDtl2;
			
			// 코로나 CT 결과
			var coronaCtVal = response.coronaCtVal;

			// 세포유전 결과 (2022.08.29)
			var cellVal = response.cellVal;
			
			// 검사결과 height 조절을 위한 rsTableList 데이터 초기화/추가
			rsTableList = "";
			rsTableList = rstUserDtl2;
			if(rstUserDtl2.length > 0){
				
				for (var i in rstUserDtl2){
					if(rstUserDtl2[i].O_CKI == "Y" && img_cnt == 0){
						calLCnt ++;
// 						console.log("(rstUserDtl2[i].O_CKI	====  "+calLCnt);
					}
					if (rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("Eugene") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("에이스") >= 0){
						LmbYN_str = "Y";
						//console.log("LmbYN_str = " + LmbYN_str);
					}
					
					if(rstUserDtl2[i].O_PRF == "N"){
						rstUserDtl2[i].O_PRF = "";
					}
					
					var datData1 = "";
					
					//datData1 = rstUserDtl2[i].O_REF.replace(/n/gi,"\n").trim();
					datData1 = rstUserDtl2[i].O_REF;
					//datData1 = "<br>";
					rstUserDtl2[i].O_REF = datData1;
					//rstUserDtl2[i].O_REF = "<br>"";
					
					if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "* 아래참조  [phi = (P2PSA/freePSA)*√PSA]"; 	
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF1 = datData1+"(pg/mL)";
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF1 = datData1+"(ng/mL)";
					} else if(rstUserDtl2[i].O_GCD == 71300){
						rstUserDtl2[i].O_REF1 = "Negative";
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21639 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21640 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "\nLow risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF1 = datData1.replace("F", "\n여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF1 = datData1.replace("F", "\n여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 25000 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF1 = "●저위험군 : <38.00\n" +
												"●고위험군 : 38.00~84.99 (20-33주6일)/38.00~109.99 (34주 이상)\n" +
												"●전자간증 : ≥85.00(20-33주6일 )/≥110.00 (34주 이상)\n" +
												"●쌍태아 : 저위험군 <53.00, 전자간증의심 ≥53.00";		
					} else {
						rstUserDtl2[i].O_REF1 = rstUserDtl2[i].O_REF1.replace(/@@/gi,"\n").trim();	
					}
					
					
					//datData1 = rstUserDtl2[i].O_REF;
					//console.log(datData1);
					//rstUserDtl2[i].O_REF = rstUserDtl2[i].O_REF.replace(/$/gi,"\n").trim();
					//rstUserDtl2[i].O_REF = rstUserDtl2[i].O_REF;
					
					
					/*
					if(datData1.length > 25){
						rstUserDtl2[i].O_REF2 = datData1.substring(0,25)+"...";	
					}else {
						//rstUserDtl2[i].O_BDT2 = rstUserDtl2[i].O_BDT; 
						rstUserDtl2[i].O_REF2 = datData1;
					}
					*/
					
					//rstUserDtl2[i].O_REF2 = datData1;
					
					//20190313 참고치 하드코딩 추가(테스트 오류 수정사항)
					if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "* 아래참조  [phi = (P2PSA/freePSA)*√PSA]"; 	
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF2 = datData1+"(pg/mL)";
					} else if(rstUserDtl2[i].O_GCD == 25002 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF2 = datData1+"(ng/mL)";
					} else if(rstUserDtl2[i].O_GCD == 71300){
						rstUserDtl2[i].O_REF2 = "Negative";
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21638 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21639 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 21640 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = "Epithelical ovarian cancer\n" + datData1.replace("저위험 ROMA value", "Low risk:").replace("고위험 ROMA value", "High risk:");
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 01){
						rstUserDtl2[i].O_REF2 = datData1.replace("F", "여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 05484 && rstUserDtl2[i].O_ACD == 02){
						rstUserDtl2[i].O_REF2 = datData1.replace("F", "여").replace("M", "남")+" cP";
					} else if(rstUserDtl2[i].O_GCD == 25000 && rstUserDtl2[i].O_ACD == 03){
						rstUserDtl2[i].O_REF2 = "●저위험군 : <38.00\n" +
												"●고위험군 : 38.00~84.99 (20-33주6일)/38.00~109.99 (34주 이상)\n" +
												"●전자간증 : ≥85.00(20-33주6일 )/≥110.00 (34주 이상)\n" +
												"●쌍태아 : 저위험군 <53.00, 전자간증의심 ≥53.00";		
					} else {
						rstUserDtl2[i].O_REF2 = datData1.replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").replace(/\s\s/gi," ").trim();	
					}
					
					
					
					var datData2 = "";
					
					datData2 = rstUserDtl2[i].O_BDT.toString();
					
					
					if(rstUserDtl2[i].O_BDT != 0){
						rstUserDtl2[i].O_BDT2 = datData2.substring(0,4)+"-"+datData2.substring(4,6)+"-"+datData2.substring(6,8);	
					}else {
						//rstUserDtl2[i].O_BDT2 = rstUserDtl2[i].O_BDT; 
						rstUserDtl2[i].O_BDT2 = "";
					}
					rstUserDtl2[i].F010GCD = rstUserDtl2[i].O_GCD;
					rstUserDtl2[i].O_C07 = rstUserDtl2[i].O_C07.substring(0,1);
					//rstUserDtl2[i].O_C07 = rstUserDtl2[i].O_C07.substring(0,1);
					rstUserDtl2[i].O_GCDNIMG = "../images/common/ico_viewer.png";
					
					if(rstUserDtl2[i].O_ACD != "" && rstUserDtl2[i].O_ACD != "00"){
						rstUserDtl2[i].O_OCD = "";
					}
					
					//20160502 MAST 검사 결과 겹치는 부분 수정 작업
					/*
					if ((tempItemCode.equals("0068916")) || (tempItemCode.equals("0068917"))
							|| (tempItemCode.equals("0068939")) || (tempItemCode.equals("0068940"))
							|| (tempItemCode.equals("0068941")) || (tempItemCode.equals("0068945"))
							|| (tempItemCode.equals("0068946")) || (tempItemCode.equals("0068950"))
							|| (tempItemCode.equals("0068952")) || (tempItemCode.equals("0068959"))
							|| (tempItemCode.equals("0068962")) || (tempItemCode.equals("0068963"))
							|| (tempItemCode.equals("0068817")) || (tempItemCode.equals("0068834"))
							|| (tempItemCode.equals("0068835")) || (tempItemCode.equals("0068836"))
					) {
						continue;
					}
					
					*/

					//mast_str
					if(rstUserDtl2[i].O_GCD == "00690" || rstUserDtl2[i].O_GCD == "00691"
						|| rstUserDtl2[i].O_GCD == "00701" || rstUserDtl2[i].O_GCD == "00702"
						|| rstUserDtl2[i].O_GCD == "00687"
					){
						
						var startChar = -1;
						var item_str = rstUserDtl2[i].O_GCDN;
						//console.log("### item_str["+i+"] : "+item_str);
						try{
							startChar = item_str.indexOf("]");
							
							while(item_str.indexOf("]") > 0){
								
								//console.log("## item_str(전) : "+item_str);
								//console.log("## item_str.indexOf : "+item_str.indexOf("]"));
								//console.log("## startChar : "+startChar);
								item_str = item_str.substring(startChar+1);
								//console.log("## item_str(후) : "+item_str);
								
								// item_str 값을 substring 한 후에 
								// "]" 값이 맨앞에 있을경우, item_str 값이 잘못된 모양으로 출력되어 아래와 같이 변경함.
								// ex) 접수일자 : 20190509 ,접수번호 : 22028 
								if(item_str.indexOf("]") == 0){
									item_str = item_str.replace("]","");
								}
								
							}
							
							// 배열값인 i 값이 page2 판넬 제목 데이터에도 포함이 되기때문에
							// 2번째 page 에 no 값들이 1씩 많게 출력되는 문제가 있어서 아래와 같이 변경함.
							// ex) 접수일자 : 20190509 ,접수번호 : 22028 
							if(parseInt(i)<38){
								item_str = "("+i+")" + item_str;
							}else{
								i --;
								item_str = "("+i+")" + item_str;
								i ++;
							}
							
							//console.log("replaceAll(item_str) : "+replaceAll(item_str));
							
						} catch(e){}
						
						//console.log("### rstUserDtl2["+i+"].O_CHR : "+rstUserDtl2[i].O_CHR);
						//console.log(" ");
						if(rstUserDtl2[i].O_CHR.indexOf("1 Class ")>=0){
							mast_class1 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("2 Class ")>=0){
							mast_class2 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("3 Class ")>=0){
							mast_class3 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("4 Class ")>=0){
							mast_class4 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("5 Class ")>=0){
							mast_class5 += item_str + " , ";
							mast_str="Y";
						} else if (rstUserDtl2[i].O_CHR.indexOf("6 Class ")>=0){
							mast_class6 += item_str + " , ";
							mast_str="Y";
						}
						
						// 문자결과를 강제로 치환해줘야 하는 조건 추가
						// ex) 접수일자 : 20190509 ,접수번호 : 22028 
						mast_class1 = mast_class1.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class2 = mast_class2.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class3 = mast_class3.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class4 = mast_class4.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						mast_class5 = mast_class5.replace("(44)생쥐/쥐(mix) , (45)생쥐/쥐(mix)","(45)(44)생쥐/쥐(mix)");
						
					}
					
					var redTxtList = response.redTxtList;
					// 20170302 Remark 자동추가 입력(STI검사항목)
					var redTxtChk = "N";
					for(var c in redTxtList){
						if(redTxtList[c].R003GCD == rstUserDtl2[i].O_GCD){
							redTxtChk = "Y"; 
						}
					}
					
					if (redTxtChk == "Y" && (rstUserDtl2[i].O_CHR.indexOf("Positive") >=0 ) 
					) {
						if (remarkPositiveGcdn == "") {
							remarkPositiveGcdn += rstUserDtl2[i].O_GCDN;
						} else {
							remarkPositiveGcdn += ", "+rstUserDtl2[i].O_GCDN;
						}
						if(!isNull(remarkPositiveGcdn) ){
							remarkPositiveYn = "Y";
						} else {
							remarkPositiveYn = "N";
						}
						
						//$('#remarkPositive').html("의뢰하신 검체에서 " + remarkPositiveGcdn + " 양성 반응이 검출되었습니다.<br/>");
						
						
					}
					
					//remark_str
					if(rstUserDtl2[i].O_REMK == "Y"){
						remark_str = "Y";
					} 
					
					//코로나
					if(coronaCtVal.length > 0){
						if(coronaCtVal[0].CT1.trim() != "" || coronaCtVal[0].CT2.trim() != "" || coronaCtVal[0].CT3.trim() != ""){
							corona_ct_str = "Y";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
						}
					}
					
					if(rstUserDtl2[i].O_CKI == "Y" && img_cnt == 0){
						img_cnt++;
						//img_sdb = rstUserDtl2[i].O_SDB;
						//$("#I_PMCD").val(pmcd);
						//$("#I_DAT").val(data.DAT);
						//$("#I_JNO").val(data.JNO);
						var rstUserParam = "";
						rstUserParam = "&I_DAT="+$('#I_DTLDAT').val();
						rstUserParam += "&I_JNO="+$('#I_DTLJNO').val();
						//rstUserParam += "&I_GCD="+rstUserDtl2[i].O_GCD;
						//rstUserParam += "&I_ACD="+rstUserDtl2[i].O_ACD;
						var gcdMerge1 = "";
						var gcdMerge2 = "";
						
						for (var c in rstUserDtl2){
							if(rstUserDtl2[c].O_CKI == "Y" && gcdMerge1 == "" ){
								//console.log("rstUserDtl2[c].O_GCD1 = "+ rstUserDtl2[c].O_GCD);
								gcdMerge1 = rstUserDtl2[c].O_GCD;
								gcdMerge2 = gcdMerge1;
							} else if(rstUserDtl2[c].O_CKI == "Y" && gcdMerge1 != "" ){
								//console.log("rstUserDtl2[c].O_GCD2 = "+ rstUserDtl2[c].O_GCD);
								if(gcdMerge1 != rstUserDtl2[c].O_GCD){
									gcdMerge2 += rstUserDtl2[c].O_GCD; 
									gcdMerge1 = rstUserDtl2[c].O_GCD;
								}
							}
						}
						//console.log(gcdMerge2);
						
						$('#I_GCD').val(gcdMerge2);
						
						//$('#I_GCD').val(rstUserDtl2[i].O_GCD);
						//$('#I_ACD').val(rstUserDtl2[i].O_ACD);
						
						rd_gcd = $('#I_GCD').val();
						rd_acd = $('#I_ACD').val();
						
						img_dpt = "";
						if(rstUserDtl2[i].O_DPT == "5230"){
							img_dpt = "5295"	
						} else {
							img_dpt = rstUserDtl2[i].O_DPT;
						}
						//console.log("img_dpt = " + img_dpt);
						var formData = $("#searchForm").serialize();
						//alert(JSON.stringify(data));
						//alert(data.DAT);
						formData += rstUserParam;
						ajaxCall("/rstUserMwr03rms5.do", formData, false);
					}
					
					//화면 flag 설정
					if(rstUserDtl2[i].O_CKP == "5250@"){
						
						//기형아 검사
						ria_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "HEMAT"){
						//혈종소견
						hematoma_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "OBGY"){
						//산전
						obgy_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5240S" || rstUserDtl2[i].O_CKP == "5240C"){
						microbio_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5260@" && LmbYN_str == "N"){
						pathologyType_str = "Y";
					if (rstUserDtl2[i].O_LMBN.indexOf("한미") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("포유") >= 0 
						 || rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("화신") >= 0 
						 || rstUserDtl2[i].O_LMBN.indexOf("운경") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("명진") >= 0
						){
							pathologyType_str = "N";
							pathologyType2_str = "Y";
						}
						//console.log("pathologyType_str = pathologyType2_str : " +pathologyType_str + " = " + pathologyType2_str)
					} else if(rstUserDtl2[i].O_CKP == "5295@"){
						CytologyType2_str = "Y";
					} else if(rstUserDtl2[i].O_CKP == "5270@"){
						CytologyType_str = "Y";
						
						if (rstUserDtl2[i].O_LMBN.indexOf("한미") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("포유") >= 0 
							 || rstUserDtl2[i].O_LMBN.indexOf("CNY") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("화신") >= 0 
							 || rstUserDtl2[i].O_LMBN.indexOf("운경") >= 0 || rstUserDtl2[i].O_LMBN.indexOf("명진") >= 0
						){
								CytologyType3_str = "Y";
								CytologyType_str = "N";
						}
					} else if(rstUserDtl2[i].O_CKP == "5281@"){
						Cytogene_str = "Y";
					}
					//화면 flag 설정 끝
						
					//면역병리 진단보고 타이틀 확인
					if(rstUserDtl2[i].O_GCD == "51234" || rstUserDtl2[i].O_GCD == "51244"
					|| rstUserDtl2[i].O_GCD == "51247" || rstUserDtl2[i].O_GCD == "51253"){
						pathTitleYn = "Y";
					}
				}
				
				
				//gird 데이터 추가, 체크 표시 검사 결과 테이블 생성
				resultTable(rstUserDtl2);
				
				if(mast_class1.length >3 )
				mast_class1 = mast_class1.substring(0, mast_class1.length-3) + "<br>";
						
				if(mast_class2.length >3 )
				mast_class2 = mast_class2.substring(0, mast_class2.length-3) + "<br>";
						
				if(mast_class3.length >3 )
				mast_class3 = mast_class3.substring(0, mast_class3.length-3) + "<br>";
				
				if(mast_class4.length >3 )
				mast_class4 = mast_class4.substring(0, mast_class4.length-3) + "<br>";
				
				if(mast_class5.length >3 )
				mast_class5 = mast_class5.substring(0, mast_class5.length-3) + "<br>";
				
				if(mast_class6.length >3 )
				mast_class6 = mast_class6.substring(0, mast_class6.length-3) + "<br>";
				
				//console.log("### 222 mast_class6 :: "+mast_class6);
				//alert("### 222 mast_class6 :: "+mast_class6);
				
				
				if("<b>class6</b><br>" != mast_class6.trim() && (mast_class6 != null || "" != mast_class6.trim())){	// class6 결과가 없는 경우,
					rlt_class += mast_class6 + "<br>";
				}
				if("<b>class5</b><br>" != mast_class5.trim() && (mast_class5 != null || "" != mast_class5.trim())){	// class5 결과가 없는 경우,
					rlt_class += mast_class5 + "<br>";
				}
				if("<b>class4</b><br>" != mast_class4.trim() && (mast_class4 != null || "" != mast_class4.trim())){	// class4 결과가 없는 경우,
					rlt_class += mast_class4 + "<br>";
				}
				if("<b>class3</b><br>" != mast_class3.trim() && (mast_class3 != null || "" != mast_class3.trim())){	// class3 결과가 없는 경우,
					rlt_class += mast_class3 + "<br>";
				}
				if("<b>class2</b><br>" != mast_class2.trim() && (mast_class2 != null || "" != mast_class2.trim())){	// class2 결과가 없는 경우,
					rlt_class += mast_class2 + "<br>";
				}
				if("<b>class1</b><br>" != mast_class1.trim() && (mast_class1 != null || "" != mast_class1.trim())){	// class1 결과가 없는 경우,
					rlt_class += mast_class1 + "<br>";
				}
				
				//console.log("### 333 rlt_class :: "+rlt_class);
				//alert("### 333 rlt_class :: "+rlt_class);
				
				// 20190319 김윤영 과장님 요청사항 : 내용이 없는 상세내역 hide 처리 
				if(rlt_class == "" || rlt_class == "<br>" || rlt_class == "<br><br>&nbsp;&nbsp;"){
					mast_str="N";
				}
				
				$('#MastResult').html(rlt_class);
				//console.log(mast_str+" = "+rlt_class);
				
				//alert(LmbYN_str+ " = " + pathologyType_str+ " = " +pathologyType2_str);  
				
				 
				/*
				console.log("pdf_html = " + pdf_html);
				console.log("pdf_htmlx = " + pdf_htmlx);
				pdf_htmlx += pdf_html;
				
				$('#hasimg').html(img_html);
				$('#Popphoto').html(pdf_htmlx);
				*/
				//callResize();
				
				//기형아 검사
				var rstUserMw109rms = response.rstUserMw109rms
				
				isTripleNew = "N";
				if($('#I_DTLDAT').val() > 20140430){
					isTripleNew = "Y";
				}
				
				riaResult ="";
				
				if (rstUserMw109rms.length > 0){
					if(rstUserMw109rms[0].RRC != "E"){
						riaData = "Y"; 	
						riaResult += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9'>";
						riaResult += "<tr>";
						riaResult += "			  <td class='line10' height='25' width='120'>Birth date</td>";
						riaResult += "					<td class='line9' width='150'>"+parseDate(rstUserMw109rms[0].RBDT)+"</td>";
						riaResult += "					<td class='line10' width='120'>LMP</td>";
						riaResult += "					<td class='line9' width='150'>"+parseDate(rstUserMw109rms[0].RLPD)+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>LMP age</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RGW1+"</td>";
						riaResult += "					<td class='line10' >Ultrasound age</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RGW2+"</td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						riaResult += "			<br />";
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='180'>AFP</td>";
						riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RAFP+"</td>";
						riaResult += "					<td class='line10' width='80'>ng/mL</td>";
						riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RAFPM+"</td>";
						riaResult += "					<td class='line10' width='70'>MOM</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>hCG</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RHCG+"</td>";
						riaResult += "					<td class='line10' >IU/mL</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RHCGM+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>uE3</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RUE3+"</td>";
						riaResult += "					<td class='line10' >nmol/L</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RUE3M+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height=25>Inhibin A</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RINH+"</td>";
						riaResult += "					<td class='line10' >pg/mL</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RINHM+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						//접수일자 기준으로 보여지는 항목이 다름
						if (isTripleNew == "Y") { //접수날짜가 20140501 이상일떄
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>hCG</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBG+"</td>";
						riaResult += "					<td class='line10' >IU/mL</td>"; 
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBGM+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>PAPP-A</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPA+"</td>";
						riaResult += "					<td class='line10' >ng/mL</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPAM+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						} else { //접수날짜가 2014.05.01 이전일때
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' >FreeB-hCG</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBG+"</td>";
						riaResult += "					<td class='line10' >mlu/mL</td> ";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RFBGM+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>PAPP-A</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPA+"</td>";
						riaResult += "					<td class='line10' >mg/L</td>";
						riaResult += "					<td class='line9' >"+rstUserMw109rms[0].RPPAM+"</td>";
						riaResult += "					<td class='line10' >MOM</td>";
						riaResult += "				</tr>";
						}
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25'>NT</td>";
						riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RNT+"</td>";
						riaResult += "					<td class='line10' width='80'>mm</td>";
						riaResult += "					<td class='line9' width='150'>"+rstUserMw109rms[0].RNTM+"</td>";
						riaResult += "					<td class='line10' width='70'>MOM</td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						riaResult += "			<br />";
						
						/*
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk by Maternal age</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+param_rstUserMw109rms.O_RSK+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Down syndrome</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+param_rstUserMw109rms.O_DWN+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Edward syndrome</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+param_rstUserMw109rms.O_EDW+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line9_l' height='25' colspan='2'></td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						riaResult += "			<br/>";
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Open NTD</td>";
						riaResult += "					<td class='line9' height='25' width='250'></td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line9_l' height='25' colspan='2'></td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						*/
						
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk by Maternal age</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].RRSK+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Down syndrome</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].RDWN+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Edward syndrome</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].REDW+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line9_l' height='25' colspan='2'>"+rstUserMw109rms[0].RRMK1+"</td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
						riaResult += "			<br/>";
						riaResult += "			<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9' >";
						riaResult += "				<tr>";
						riaResult += "					<td class='line10' height='25' width='280'>Risk of Open NTD</td>";
						riaResult += "					<td class='line9' height='25' width='250'>"+rstUserMw109rms[0].RNTD+"</td>";
						riaResult += "				</tr>";
						riaResult += "				<tr>";
						riaResult += "					<td class='line9_l' height='25' colspan='2'>"+rstUserMw109rms[0].RRMK2+"</td>";
						riaResult += "				</tr>";
						riaResult += "			</table>";
							
						// 20190319 김윤영 과장님 요청사항 : 내용이 없는 상세내역 hide 처리 
						if(riaResult == "" || riaResult == "<br>" || riaResult == "<br><br>&nbsp;&nbsp;"){
							ria_str = "N";
						}
						
						$('#Ria').html(riaResult);
					
					} else {
						riaData = "N";
						ria_str = "N";
					}
				}
				
				
				//기형아 검사 끝
				
				//********************************************
				// 혈종검사소견 D폼 / N폼에 따라 해당하는 데이터 가져오기
				//********************************************
					var rstUserMw108rmA = response.rstUserMw108rmA;
					
					if(rstUserMw108rmA.length > 0){
						if(typeof rstUserMw108rmA[0].RREMK == "undefined"){
							hematomaStr = rstUserMw108rmA[0].F680TXT;
							
							if(typeof rstUserMw108rmA[0].F680TXT == "undefined"){
								hematoma_str = "N";
							}else{
								hematoma_str = "Y";
							}
							
						}else{
							hematomaStr = rstUserMw108rmA[0].RREMK;
						}	
					}
					
					$('#Hematoma').html(hematomaStr);
				
				//********************************************
				// 산전검사소견 BOB폼 / P폼에 따라 해당하는 데이터 가져오기
				//********************************************
					var rstUserMw108rmB = response.rstUserMw108rmB
					
					if(rstUserMw108rmB.length > 0){
						if(typeof rstUserMw108rmB[0].F680TXT != "undefined"){
							obgyStr = rstUserMw108rmB[0].F680TXT;
							obgy_str = "Y";
						}
					}
					
					$('#Obgy').html(obgyStr);
				
				//********************************************
				// microbio
				//********************************************
					var rstUserMw118rms1 = response.rstUserMw118rms1;
					
					if(rstUserMw118rms1.length>0)
					{
						microbio_html = "";
						
						//microbio_html += "<table border='0' cellpadding='0' cellspacing='0' class='line9'>";
						microbio_html += "<tr>";
						microbio_html += 	"<td class='line10' width='20%' height='25'>Code</td>";
						microbio_html += 	"<td class='line10' width='30%'>Test Name</td>";
						microbio_html += 	"<td class='line10' width='20%'>Code</td>";
						microbio_html += 	"<td class='line10' width='30%'>Test Name</td>";
						microbio_html += "</tr>";
						
						for (var i in rstUserMw118rms1){
							microbio_html += "<tr>";
							microbio_html += "	<td class='line9' height='25'>"+rstUserMw118rms1[i].CGCD+"</td>";
							microbio_html += "	<td class='line9_l'>"+rstUserMw118rms1[i].CFEN+"</td>";
							microbio_html += "	<td class='line9'>"+rstUserMw118rms1[i].SGCD+"</td>";
							microbio_html += "	<td class='line9_l'>"+rstUserMw118rms1[i].SFEN+"</td>";
							microbio_html += "</tr>";
							
							if(rstUserMw118rms1[i].SGCD == "31022" || rstUserMw118rms1[i].SGCD == "31026"){
								microbio_html += "<tr>";
								microbio_html += 	"<td class='line9_l' colspan='4'><pre>"+rstUserMw118rms1[i].SRST+"</pre></td>";
								microbio_html += "</tr>";
							} else {
								microbio_html += "<tr>";
								microbio_html += 	"<td class='line9_l' colspan='4'><pre>"+rstUserMw118rms1[i].CRST+"</pre></td>";
								microbio_html += "</tr>";
							}
							
						}
						
						if(microbio_html == "" || microbio_html == "<br>" || microbio_html == "<br><br>&nbsp;" || microbio_html == "<br><br>&nbsp;&nbsp;"){
							microbio_str = "N";
						}
						
						
					} else {
						microbio_str = "N";
					}
					
					$('#Microbio').html(microbio_html);
				
				//********************************************
				// LmbYN 20140502 이전 / 20140502 이후
				//********************************************
					var rstUserMw106rm3 = response.rstUserMw106rm3;
					if(LmbYN_str == "Y"){
						if($('#I_DTLDAT').val()<= 20140502){
							if(rstUserMw106rm3.length > 0){
								var strs1 = rstUserMw106rm3[0].RTXT;
								//LmbYN_html = strs1.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
								LmbYN_html = strs1.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
								//LmbYN_html = strs1.replace(/\s/gi,"&nbsp;").replace(/<The/gi,"<&nbsp;The").replace(/System>/gi,"System&nbsp;>").replace(/<IMMUNOHISTOCHEMICAL/gi,"<&nbsp;IMMUNOHISTOCHEMICAL").replace(/STAINS>/gi,"STAINS&nbsp;>").replace(/<Additional/gi,"<&nbsp;Additional").replace(/information>/gi,"information&nbsp;>").replace(/<MICRO/gi,"<&nbsp;MICRO").replace(/[PAP)>]/gi,"PAP)&nbsp;>");
								Mw106rm3 = "Y";
								if(LmbYN_html == "" || LmbYN_html == "<br>" || LmbYN_html == "<br><br>&nbsp;" || LmbYN_html == "<br><br>&nbsp;&nbsp;"){
									Mw106rm3 = "N";
									LmbYN_str = "N";
								}
							}
							$('#LmbYN').html(LmbYN_html);
						}
					} else {
						if(rstUserMw106rm3.length > 0){
							//pathologyType_html = rstUserMw106rm3[0].RTXT;
							var strs2 = rstUserMw106rm3[0].RTXT;
							//pathologyType_html = strs2.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
							pathologyType_html = strs2.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
							//pathologyType_html = strs2.replace(/\s/gi,"&nbsp;").replace(/<The/gi,"<&nbsp;The").replace(/System>/gi,"System&nbsp;>").replace(/<IMMUNOHISTOCHEMICAL/gi,"<&nbsp;IMMUNOHISTOCHEMICAL").replace(/STAINS>/gi,"STAINS&nbsp;>").replace(/<Additional/gi,"<&nbsp;Additional").replace(/information>/gi,"information&nbsp;>").replace(/<MICRO/gi,"<&nbsp;MICRO").replace(/[PAP)>]/gi,"PAP)&nbsp;>");
							
							if(pathologyType_html == "" || pathologyType_html == "<br>" || pathologyType_html == "<br><br>&nbsp;" || pathologyType_html == "<br><br>&nbsp;&nbsp;"){
								pathologyType_str = "N";
							}
						}
						
						$('#PathologyType').html(pathologyType_html);
					}
					//lmbyn 20140502 이전 끝
				
				
					var rstUserMw106rms6 = response.rstUserMw106rms6;
					
					if(LmbYN_str == "Y"){
						if($('#I_DTLDAT').val()> 20140502){
							if(rstUserMw106rms6.length > 0){
								var strs3 = rstUserMw106rms6[0].RTXT;
								//LmbYN_html = strs3.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
								LmbYN_html = strs3.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
								//LmbYN_html = strs3.replace(/\s/gi,"&nbsp;").replace(/<The/gi,"<&nbsp;The").replace(/System>/gi,"System&nbsp;>").replace(/<IMMUNOHISTOCHEMICAL/gi,"<&nbsp;IMMUNOHISTOCHEMICAL").replace(/STAINS>/gi,"STAINS&nbsp;>").replace(/<Additional/gi,"<&nbsp;Additional").replace(/information>/gi,"information&nbsp;>").replace(/<MICRO/gi,"<&nbsp;MICRO").replace(/[PAP)>]/gi,"PAP)&nbsp;>");
								Mw106rms6 = "Y";
								
								if(LmbYN_html == "" || LmbYN_html == "<br>" || LmbYN_html == "<br><br>&nbsp;" || LmbYN_html == "<br><br>&nbsp;&nbsp;"){
									Mw106rms6 = "N";
									LmbYN_str = "N";
								}
							}	
							$('#LmbYN').html(LmbYN_html);
						}
					} else {
						if(rstUserMw106rms6.length > 0){
							var strs4 = rstUserMw106rms6[0].RTXT;
							//pathologyType2_html = strs4.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
							pathologyType2_html = strs4.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
							//pathologyType2_html = strs4.replace(/\s/gi,"&nbsp;").replace(/<The/gi,"<&nbsp;The").replace(/System>/gi,"System&nbsp;>").replace(/<IMMUNOHISTOCHEMICAL/gi,"<&nbsp;IMMUNOHISTOCHEMICAL").replace(/STAINS>/gi,"STAINS&nbsp;>").replace(/<Additional/gi,"<&nbsp;Additional").replace(/information>/gi,"information&nbsp;>").replace(/<MICRO/gi,"<&nbsp;MICRO").replace(/[PAP)>]/gi,"PAP)&nbsp;>");
							
							if(pathologyType2_html == "" || pathologyType2_html == "<br>" || pathologyType2_html == "<br><br>&nbsp;" || pathologyType2_html == "<br><br>&nbsp;&nbsp;"){
								pathologyType2_str = "N";
							}
							
						}
						
						$('#PathologyType2').html(pathologyType2_html);
					}
					//lmbyn 20140502 이후 끝
				
				//********************************************
				// CytologyType
				//********************************************
					var rstUserMw105rms1 = response.rstUserMw105rms1;
					if(rstUserMw105rms1.length > 0){
						CytologyType_html = rstUserMw105rms1[0].RTXT;
					}
				
				//********************************************
				// CytologyType2
				//********************************************
					var rstUserMw106rms1 = response.rstUserMw106rms1;
					if(rstUserMw106rms1.length > 0){
						CytologyType2_html = rstUserMw106rms1[0].RTXT;
					}
				
				//********************************************
				// CytologyType3
				//********************************************
					var rstUserMw106rms5 = response.rstUserMw106rms5;
					if(rstUserMw106rms5.length > 0){
						CytologyType3_html = rstUserMw106rms5[0].RTXT;
					}
				
				//********************************************
				// Cytogene
				//********************************************
					var rstUserMw117rm = response.rstUserMw117rm;
					var param_rstUserMw117rm = response.param_rstUserMw117rm;
					if(param_rstUserMw117rm.O_PRC == "E"){
						Cytogene_prc = "N";
					} else {
						Cytogene_prc = "Y";
						if(rstUserMw117rm.length > 0){
							Cytogene_html = "";
							Cytogene_html += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='line9'>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line10' height='25' width='150' style='text-align:center'>Band resolution</td>";
							Cytogene_html += 		"<td class='line9' width='120' style='text-align:center'>"+rstUserMw117rm[0].RBRS+"</td>";
							Cytogene_html += 		"<td class='line10' width='150' style='text-align:center'>Counted cells</td>";
							Cytogene_html += 		"<td class='line9' width='120' style='text-align:center'>"+rstUserMw117rm[0].RCCN+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line10' height='25' style='text-align:center'>Analyzed cells</td>";
							Cytogene_html += 		"<td class='line9' style='text-align:center'>"+rstUserMw117rm[0].RCAN+"</td>";
							Cytogene_html += 		"<td class='line10' style='text-align:center'>Karyotyped cells</td>";
							Cytogene_html += 		"<td class='line9' style='text-align:center'>"+rstUserMw117rm[0].RKAP+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line10' style='text-align:center'>KARYOTYPE</td>";
							Cytogene_html += 		"<td class='line9_l' colspan='3'>"+rstUserMw117rm[0].RJPG+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += 	"<tr>";
							Cytogene_html += 		"<td class='line9_l' colspan='4'>"+rstUserMw117rm[0].RRMK+"</td>";
							Cytogene_html += 	"</tr>";
							Cytogene_html += "</table>";
						}
					}
					
					if(Cytogene_html == "" || Cytogene_html == "<br>" || Cytogene_html == "<br><br>&nbsp;" || Cytogene_html == "<br><br>&nbsp;&nbsp;"){
						Cytogene_str = "N";
					}
					
					$('#Cytogene').html(Cytogene_html);
				
				//********************************************
				// Remarktext
				//********************************************
				
					remarkData = "";
					remarkDataRd = "";
					if(remarkPositiveYn == "Y"){
						remarkData = "<br>의뢰하신 검체에서 " + remarkPositiveGcdn + " 양성 반응이 검출되었습니다.";
						remarkDataRd = "<br>의뢰하신 검체에서 " + remarkPositiveGcdn + " 양성 반응이 검출되었습니다.";
						
					}
					
					//remarkData += param_rstUserMw107rms3.O_TXT;
					
					var rstUserMw107rms3 = response.rstUserMw107rms3;
					if(rstUserMw107rms3.length > 0){
						remarkData += rstUserMw107rms3[0].RREMK;
					}
					
					if(remarkData.length>0){
						//remarkData = remarkData.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
						remarkData = remarkData.replace(/\s/gi,"&nbsp;").replace(/</gi,"<&nbsp;").replace(/<&nbsp;&nbsp;/gi,"<&nbsp;").replace(/>&nbsp;/gi,"&nbsp;>").replace(/>의/gi,"&nbsp;>의").replace(/></gi,"&nbsp;><").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/&nbsp;&nbsp;>/gi,"&nbsp;>").replace(/<&nbsp;br&nbsp;>/gi,"<br>");
						//remarkData = remarkData.replace(/\s/gi,"&nbsp;").replace(/<The/gi,"<&nbsp;The").replace(/System>/gi,"System&nbsp;>").replace(/<IMMUNOHISTOCHEMICAL/gi,"<&nbsp;IMMUNOHISTOCHEMICAL").replace(/STAINS>/gi,"STAINS&nbsp;>").replace(/<Additional/gi,"<&nbsp;Additional").replace(/information>/gi,"information&nbsp;>").replace(/<MICRO/gi,"<&nbsp;MICRO");//.replace(/PAP)>/gi,"PAP)&nbsp;>");
					}
				
					// remark 내용에 <요경검> 이라고 출력되는 타이틀을 <핵의학>으로 변경 요청
					// 요청자 : 전준호 부장
					// 접수일자 : 20190528
					// 접수번호 : 9717
					if(remarkData.indexOf("요경검") > -1){
						remarkData = remarkData.replace('요경검','핵의학');
					}
								
					if(CytologyType_html == "" || CytologyType_html == "<br>" || CytologyType_html == "<br><br>&nbsp;" || CytologyType_html == "<br><br>&nbsp;&nbsp;"){
						CytologyType_str = "N";
					}
					
					if(CytologyType2_html == "" || CytologyType2_html == "<br>" || Cytogene_html == "<br><br>&nbsp;" || Cytogene_html == "<br><br>&nbsp;&nbsp;"){
						CytologyType2_str = "N";
					}
					
					if(CytologyType3_html == "" || CytologyType3_html == "<br>" || CytologyType3_html == "<br><br>&nbsp;" || CytologyType3_html == "<br><br>&nbsp;&nbsp;"){
						CytologyType3_str = "N";
					}
					
					$('#CytologyType').html(CytologyType_html);
					$('#CytologyType2').html(CytologyType2_html);
					$('#CytologyType3').html(CytologyType3_html);
					
					if(remarkData == "" || remarkData == "<br>" || remarkData == "<br><br>&nbsp;" || remarkData == "<br><br>&nbsp;&nbsp;"){
						remark_str = "N";
					}
					
					$('#Remarktext').html(remarkData);
				
				//********************************************
				// Corona CT
				//********************************************
				
					coronaCtData = "";	// 코로나 CT 결과 존재여부 flag
					cellData = "";		// 세포유전 결과 존재여부 flag
					
					// 코로나 CT 값 (2020.03.05)
					//var coronaCtVal = response.coronaCtVal;
					if(coronaCtVal.length > 0){
						coronaCtData += coronaCtVal[0].CT1;
						coronaCtData += "<br>";
						coronaCtData += coronaCtVal[0].CT2;
						coronaCtData += "<br>";
						coronaCtData += coronaCtVal[0].CT3;
					}
					
					// 세포유전 값 (2022.08.29)
					if(cellVal.length > 0){
						cellData += cellVal[0].FFIRTXT
					}
					
					if(coronaCtData == "" || coronaCtData == "<br>" || coronaCtData == "<br><br>&nbsp;" || coronaCtData == "<br><br>&nbsp;&nbsp;"){
						corona_ct_str = "N";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
					}
					$('#DivCoronaCT_val').html(coronaCtData);	// Corona CT 값 화면에 출력할 변수
					
					// 세포유전 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2022.08.29)
					if(cellData == ""){
						cell_str = "N";
					}
					$('#DivCell_val').html(cellData);	// 세포유전 값 화면에 출력할 변수
				
			}
			calLCnt--;
			mastResult();
//			setTimeout("mastResult()",1000);
			//console.log("확인1");
					
		}
		//검사결과 끝 예상
		if(strUrl == "/rstUserMwr03rms5.do"){
			var resultList = response.resultList;
			url_str = "";
			
			pdf_htmlx += "<tr>";
			pdf_htmlx += "	<td class='line3_l' align='left'>";
			pdf_htmlx += "		<a href='http://img.neodin.com:8080/servlet/com.neodin.files.Download?sourceFilePathName=AdbeRdr70_kor_full.exe&destFileName=AdbeRdr70_kor_full.exe' style='text-decoration:underline;'>Adobe Reader은 여기서 내려 받을수 있습니다.</a>";
			pdf_htmlx += "	</td>";
			pdf_htmlx += "</tr>";
			
			for(var r in resultList){
				
				if(r == resultList[r].URL.length-1){
					return;
				}
				url_str = resultList[r].URL.substring(resultList[r].URL.lastIndexOf("\\")+1);
				//var img_dpt2 = resultList[r].URL.substring(resultList[r].URL.lastIndexOf("\\")+1);
				//var img_dpt2 = resultList[r].URL.substring(resultList[r].URL.indexOf("\\mCLIS\\NML\\image\\"),resultList[r].URL.indexOf("\\mCLIS\\NML\\image\\")+4);
				var img_dpt2 = resultList[r].URL.substring(17, 21);
				//console.log("img_dpt2 = " +img_dpt2);
				if(url_str.indexOf("pdf") > 0 || url_str.indexOf("PDF") > 0){
					photo_str = "Y";
						
						pdf_html += "<tr>";
						pdf_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						pdf_html += 		"<a href='http://img.neodin.com:8080/servlet/com.neodin.files.PdfResultPath?sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".pdf' width='100%' style='text-decoration:underline;'>";
						pdf_html +=		 "<font color='#0000ff' >"+$('#I_DTLDAT').val()+"("+$('#NAM').html()+") This service is in preparation</font></a>";
						pdf_html +=		"</td>";
						pdf_html += "</tr>";

				} else {
					img_str = "Y";
					if(resultList[r].SDB.trim() == "LSD" || resultList[r].SDB.trim() == "NSR"){
						img_rotate = "Y";
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						//img_html += 		"<img src='"+trmsSystemUrl+"/rotateImage.do?url=http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath&sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg&radians=270' width=100%>"; 
						img_html += 		"<img src='/rotateImage.do?url=http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath&sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg&radians=270' width=100%>";
						img_html +=		"</td>";
						img_html += "</tr>";
						
						//console.log(img_html);
						//setTimeout("",100);
						
					} else {
						img_html += "<tr>";
						img_html += 	"<td class='line3' style='padding:15px 20px 15px 20px;'>";
						img_html += 		"<img src='http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName="+ url_str.trim()+ "&sourceDptPathName="+img_dpt2+"&destFileName="+$('#I_DTLDAT').val()+"-"+$('#I_DTLJNO').val()+".jpg' width=100%>"; 
						img_html +=		"</td>";
						img_html += "</tr>";	
						//console.log(img_html);
					}
				}
				
			}
			
			pdf_htmlx += pdf_html;
			
			$('#hasimg').html(img_html);
			$('#Popphoto').html(pdf_htmlx);
			calLCnt--;
			setTimeout("mastResult()",1000);
		}
		//검사결과 안에서 다른 데이터 가져오기
	}
	
	
	function resultTable(rstUserDtl2){		// 검사 결과 Table Body 생성
		//console.log(rstUserDtl2);
		var rstReusltT;
		rsTableList = "";
		rsTableList = rstUserDtl2;
		
		$('#rstResultBody').empty();		// 검사결과 Table Body 초기화
		// white-space: nowrap; text-overflow: clip; overflow: hidden; 데이터가 테이블 가로 사이즈 안넘기고 숨기기
		for(i=0; i<rstUserDtl2.length; i++ ){
			rstReusltT = "";
			rstReusltT += '<tr>';
			rstReusltT += ' 	 <td class="tool" style="max-width: 100px; text-align:left;">' + rstUserDtl2[i].O_OCD + '</td>';
			rstReusltT += '  	 <td class="tool" style="max-width: 150px;" onclick="javascript:goOCD(\''+rstUserDtl2[i].O_GCD+'\', \''+rstUserDtl2[i].O_GCDN+'\',\''+rstUserDtl2[i].O_ACD+'\')">' + rstUserDtl2[i].O_GCDN + '</td>';
			// 검사 결과 색상 변경
			if(rstUserDtl2[i].O_C07 == "H" || rstUserDtl2[i].O_FLG == "R" || rstUserDtl2[i].O_CHR.indexOf("Positive") >= 0){
				rstReusltT += '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #ff0000;">' + rstUserDtl2[i].O_CHR + '</td>';
			}else if(rstUserDtl2[i].O_C07 == "L"){
				rstReusltT += '  <td class="tool" style="max-width: 150px; font-weight: bold; color: #0000ff;">' + rstUserDtl2[i].O_CHR + '</td>';
			}else{
				rstReusltT += '  <td class="tool" style="max-width: 150px; font-weight: bold;">' + rstUserDtl2[i].O_CHR + '</td>';
			}
			rstReusltT += '      <td class="tool" style="max-width: 130px; text-align:left;">' + rstUserDtl2[i].O_REF + '</td>';
			// H/L 색상 변경
			if(rstUserDtl2[i].O_C07 == "H"){
				rstReusltT += '  <td class="tool" style="color: #ff0000;">' + rstUserDtl2[i].O_C07 + '</td>';
			}else if(rstUserDtl2[i].O_C07 == "L"){
				rstReusltT += '  <td class="tool" style="color: #0000ff;">' + rstUserDtl2[i].O_C07 + '</td>';
			}else{
				rstReusltT += '  <td class="tool">' + rstUserDtl2[i].O_C07 + '</td>';
			}
			rstReusltT += '      <td class="tool" style="white-space: nowrap; text-overflow: clip;  max-width: 150px;">' + rstUserDtl2[i].O_BDT2 + '</td>';
			// 시계열 아이콘 표시 여부
			if( rstUserDtl2[i].O_PRF == "Y" ){
				rstReusltT += '  <td><img src="../images/common/btn_srch.png" width="20px" height="20px;" ></td>';
			}else{
				rstReusltT += '  <td></td>';
			}
			rstReusltT += '</tr>';
			
			$('#rstResultBody').append(rstReusltT);
		}
		
		// tooltip 사용
		$('.tool').hover(function(){
			$(this).attr('title', $(this).html());	
		})
		
	}
	
	function mastResult(){
		//console.log("mastResult    ====  "+calLCnt);
		$('._DivMastResult').hide();
		$('._DivMastResult_Reference').hide();
		$('._DivRia').hide();
		$('._DivHematoma').hide();
		$('._DivObgy').hide();
		$('._DivHasimg').hide();
		$('._DivPopphoto').hide();
		$('._DivMicrobio').hide();
		$('._DivLmbYN').hide();
		$('._DivPathologyType').hide();
		$('._DivPathologyType2').hide();
		$('._DivCytologyType').hide();
		$('._DivCytologyType2').hide();
		$('._DivCytologyType3').hide();
		$('._DivCytogene').hide();
		$('._DivRemarktext').hide();
		$('._DivCoronaCT').hide();
		
		if(pathTitleYn == "Y"){
			$('._DivLmbYNTitle').html("면역병리 진단보고");
			$('._DivPathologyTypeTitle').html("면역병리 진단보고");
			$('._DivPathologyType2Title').html("면역병리 진단보고");
		} else {
			$('._DivLmbYNTitle').html("조직병리 진단보고");
			$('._DivPathologyTypeTitle').html("조직병리 진단보고");
			$('._DivPathologyType2Title').html("조직병리 진단보고");
		}
		
		if(photo_str == "Y"){
			//20190311 PDF가 있을경우 PDF를 제외한 상세보기를 모두 표시하지 않음(최재원 대리)
			//운영은 현재 표시되고 있음
			
			mast_str = "N";
			ria_str = "N";
			hematoma_str = "N";
			obgy_str = "N";
			img_str = "N";
			microbio_str = "N";
			LmbYN_str = "N";
			pathologyType_str = "N";
			pathologyType2_str = "N";
			CytologyType_str = "N";
			CytologyType2_str = "N";
			CytologyType3_str = "N";
			Cytogene_str = "N";
			Cytogene_prc = "N";
			remark_str = "N";
			corona_ct_str = "N";	// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)

		}
		
		if(mast_str == "Y"){
			$('._DivMastResult').show();
			$('._DivMastResult_Reference').show();
			
		} else {
			$('._DivMastResult').hide();
			$('._DivMastResult_Reference').hide();
		}
		
		//기형아 검사
		if(ria_str == "Y"){
			if($("#I_DTLDAT").val() >= 20170609){
				$("#REALAGE").text("분만 시 나이");
			} else {
				$("#REALAGE").text("나이");
			}
			$('._DivRia').show();
			//$('#Ria').html(str);		
		} else {
			$("#REALAGE").text("나이");
			$('._DivRia').hide();
			//$('#MastResult').html(str);
		}
		
		//remark(혈종)
		if(hematoma_str == "Y"){
			$('._DivHematoma').show();
					
		} else {
			$('._DivHematoma').hide();
			
		}
		
		//remark(산전)
		if(obgy_str == "Y"){
			$('._DivObgy').show();
					
		} else {
			$('._DivObgy').hide();
			
		}
		
		//이미지
		if(img_str == "Y"){
			$('._DivHasimg').show();
					
		} else {
			$('._DivHasimg').hide();
			
		}
		
		//pdf
		if(photo_str == "Y"){
			$('._DivPopphoto').show();
					
		} else {
			$('._DivPopphoto').hide();
			
		}
		
		//microbio
		if(microbio_str == "Y"){
			$('._DivMicrobio').show();
					
		} else {
			$('._DivMicrobio').hide();
			
		}
		
		
		//LmbYN
		if(LmbYN_str == "Y"){
			$('._DivLmbYN').show();
					
		} else {
			$('._DivLmbYN').hide();
			
		}
		
		//pathologyType
		if(pathologyType_str == "Y"){
			$('._DivPathologyType').show();
					
		} else {
			$('._DivPathologyType').hide();
			
		}
		
		//pathologyType2
		if(pathologyType2_str == "Y"){
			$('._DivPathologyType2').show();
					
		} else {
			$('._DivPathologyType2').hide();
			
		}
		
		//CytologyType
		if(CytologyType_str == "Y"){
			$('._DivCytologyType').show();
					
		} else {
			$('._DivCytologyType').hide();
			
		}
		
		//CytologyType2
		if(CytologyType2_str == "Y"){
			$('._DivCytologyType2').show();
					
		} else {
			$('._DivCytologyType2').hide();
			
		}
		
		//CytologyType
		if(CytologyType3_str == "Y"){
			$('._DivCytologyType3').show();
					
		} else {
			$('._DivCytologyType3').hide();
			
		}
		
		//Cytogene
		if(Cytogene_str == "Y" && Cytogene_prc == "Y"){
			$('._DivCytogene').show();
					
		} else {
			$('._DivCytogene').hide();
			
		}
		
		
		//remark 세포병리로 삽입로직 복구 
		//if(remark_str == "Y" && CytologyType_str == "N" && CytologyType2_str == "N" && CytologyType3_str == "N" ){
		if(remark_str == "Y"){
			$('._DivRemarktext').show();
			//$('#Remarktext').html(str);		
		} else {
			$('._DivRemarktext').hide();
			//$('#Remarktext').html(str);
		}
		
		// 코로나 CT 결과 존재시, 결과 상세 화면에 'Corona CT' 문구를 출력할지 여부 flag(2020.03.05)
		if(corona_ct_str == "Y"){
			$('._DivCoronaCT').show();
		} else {
			$('._DivCoronaCT').hide();
		}

		TabResize();
	
	}
	

	$(document).ready(function(){
		
		
		$(".area_name").html(s_nam+'님');
		
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		
		
		dataResult();
		
	});
</script>

</html>
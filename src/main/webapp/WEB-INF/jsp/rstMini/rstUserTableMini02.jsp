<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>

	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/reportPop.inc"%>
	
	<script type="text/javascript" src="../js/menu.js?var=190327001"></script>		
	<link rel="stylesheet" href="/css/rstUserTable.css?var=190327001"/>
	
<title>결과지 출력</title>
	
	<script type="text/javascript">
		var data = "";
		var rsTableList = "";
		var dataProvider1,dataProvider2;
	    var gridView1,gridView2;
			
		var pmcd = "";
		var I_LOGMNU = "RSTUSERTABLE";
		var I_LOGMNM = "수진자별 결과관리(Table)";
		var mousePointer ;
	    
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
	    //var rd_gcd = "";
	    //var rd_acd = "";
	    var s_uid = "<%= ss_uid %>";
	   	var s_ip =  "<%= ss_ip %>";
	   	var s_cor = "<%= ss_cor %>";
	   	
	   	var s_ecf = "<%= ss_ecf %>";
	   	var s_hos = "<%= ss_hos %>";
	   	var s_nam = "<%= ss_nam %>";
	   	
	   	var s_syn = "<%= ss_syn %>";
	   	
	   	var mainIn = "Y";
	   	var open_click = true;
		$(document).ready(function(){
			//console.log($('#rstFormData').val());
			//console.log(window.parent.rstformData2);
		
			$("#I_LOGMNU").val(I_LOGMNU);
			$("#I_LOGMNM").val(I_LOGMNM);
			
	    	$('#DivMastResult').hide();
			$('#DivMastResult_Reference').hide();
			$('#DivRia').hide();
			$('#DivHematoma').hide();
			$('#DivObgy').hide();
			$('#DivHasimg').hide();
			$('#DivPopphoto').hide();
			$('#DivMicrobio').hide();
			$('#DivLmbYN').hide();
			$('#DivPathologyType').hide();
			$('#DivPathologyType2').hide();
			$('#DivCytologyType').hide();
			$('#DivCytologyType2').hide();
			$('#DivCytologyType3').hide();
			$('#DivCytogene').hide();
			$('#DivRemarktext').hide();
			
			resultClick();
		});
		
		function init(){
			$('#DivMastResult').hide();
			$('#DivMastResult_Reference').hide();
			$('#DivRia').hide();
			$('#DivHematoma').hide();
			$('#DivObgy').hide();
			$('#DivHasimg').hide();
			$('#DivPopphoto').hide();
			$('#DivMicrobio').hide();
			$('#DivLmbYN').hide();
			$('#DivPathologyType').hide();
			$('#DivPathologyType2').hide();
			$('#DivCytologyType').hide();
			$('#DivCytologyType2').hide();
			$('#DivCytologyType3').hide();
			$('#DivCytogene').hide();
			$('#DivRemarktext').hide();
		}
		
		function mastResult(){
			//console.log("mastResult    ====  "+calLCnt);
			$('#DivMastResult').hide();
			$('#DivMastResult_Reference').hide();
			$('#DivRia').hide();
			$('#DivHematoma').hide();
			$('#DivObgy').hide();
			$('#DivHasimg').hide();
			$('#DivPopphoto').hide();
			$('#DivMicrobio').hide();
			$('#DivLmbYN').hide();
			$('#DivPathologyType').hide();
			$('#DivPathologyType2').hide();
			$('#DivCytologyType').hide();
			$('#DivCytologyType2').hide();
			$('#DivCytologyType3').hide();
			$('#DivCytogene').hide();
			$('#DivRemarktext').hide();
			
			if(pathTitleYn == "Y"){
				$('#DivLmbYNTitle').html("면역병리 진단보고");
				$('#DivPathologyTypeTitle').html("면역병리 진단보고");
				$('#DivPathologyType2Title').html("면역병리 진단보고");
			} else {
				$('#DivLmbYNTitle').html("조직병리 진단보고");
				$('#DivPathologyTypeTitle').html("조직병리 진단보고");
				$('#DivPathologyType2Title').html("조직병리 진단보고");
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
			}
			
			if(mast_str == "Y"){
				$('#DivMastResult').show();
				$('#DivMastResult_Reference').show();
				
			} else {
				$('#DivMastResult').hide();
				$('#DivMastResult_Reference').hide();
			}
			
			//기형아 검사
			if(ria_str == "Y"){
				if($("#I_DTLDAT").val() >= 20170609){
					$("#REALAGE").text("분만 시 나이");
				} else {
					$("#REALAGE").text("나이");
				}
				$('#DivRia').show();
				//$('#Ria').html(str);		
			} else {
				$("#REALAGE").text("나이");
				$('#DivRia').hide();
				//$('#MastResult').html(str);
			}
			
			//remark(혈종)
			if(hematoma_str == "Y"){
				$('#DivHematoma').show();
						
			} else {
				$('#DivHematoma').hide();
				
			}
			
			//remark(산전)
			if(obgy_str == "Y"){
				$('#DivObgy').show();
						
			} else {
				$('#DivObgy').hide();
				
			}
			
			//이미지
			if(img_str == "Y"){
				$('#DivHasimg').show();
						
			} else {
				$('#DivHasimg').hide();
				
			}
			
			//pdf
			if(photo_str == "Y"){
				$('#DivPopphoto').show();
						
			} else {
				$('#DivPopphoto').hide();
				
			}
			
			//microbio
			if(microbio_str == "Y"){
				$('#DivMicrobio').show();
						
			} else {
				$('#DivMicrobio').hide();
				
			}
			
			
			//LmbYN
			if(LmbYN_str == "Y"){
				$('#DivLmbYN').show();
						
			} else {
				$('#DivLmbYN').hide();
				
			}
			
			//pathologyType
			if(pathologyType_str == "Y"){
				$('#DivPathologyType').show();
						
			} else {
				$('#DivPathologyType').hide();
				
			}
			
			//pathologyType2
			if(pathologyType2_str == "Y"){
				$('#DivPathologyType2').show();
						
			} else {
				$('#DivPathologyType2').hide();
				
			}
			
			//CytologyType
			if(CytologyType_str == "Y"){
				$('#DivCytologyType').show();
						
			} else {
				$('#DivCytologyType').hide();
				
			}
			
			//CytologyType2
			if(CytologyType2_str == "Y"){
				$('#DivCytologyType2').show();
						
			} else {
				$('#DivCytologyType2').hide();
				
			}
			
			//CytologyType
			if(CytologyType3_str == "Y"){
				$('#DivCytologyType3').show();
						
			} else {
				$('#DivCytologyType3').hide();
				
			}
			
			//Cytogene
			if(Cytogene_str == "Y" && Cytogene_prc == "Y"){
				$('#DivCytogene').show();
						
			} else {
				$('#DivCytogene').hide();
				
			}
			
			
			//remark 세포병리로 삽입로직 복구 
			//if(remark_str == "Y" && CytologyType_str == "N" && CytologyType2_str == "N" && CytologyType3_str == "N" ){
			if(remark_str == "Y"){
				$('#DivRemarktext').show();
				//$('#Remarktext').html(str);		
			} else {
				$('#DivRemarktext').hide();
				//$('#Remarktext').html(str);
			}

			TabResize();
		
		}
			
		function resultClick(){		//수진자 목록 >>> 검사결과 보기
			
			//TabResize();
			setTimeout(function() {
	 			dataResult();	
			}, 200);
		
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
			var url = "/rstUserDtlMini.do";
			calLCnt = 1;
			ajaxCall(url, formData, false);
		}
		
		function tableFocus(obj){
			
			var table = document.getElementById("rstResultTable");
			var tr = table.getElementsByTagName("tr");
			$('#rstResultTable tr').css('background-color', '');
			// 검사결과 선택된 행 배경색 변경
			obj.style.backgroundColor = "rgba(255, 161, 32, 0.196)";
			
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
				rstReusltT += '<tr onclick="javascript:tableFocus(this)">';
				rstReusltT += '      <td><input type="checkbox" checked="checked" id="rstCheck" name="rstCheck"></td>';
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
					rstReusltT += '  <td onclick="javascript:goStatsTime(\''+rstUserDtl2[i].O_GCDN+'\', \''+rstUserDtl2[i].O_GCD+'\', \''+rstUserDtl2[i].O_ACD+'\')"><img src="../images/common/btn_srch.png" width="20px" height="20px;" ></td>';
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
		
		function onResult(strUrl,response){
			if(strUrl == "/rstUserDtlMini.do"){
				
				//gridView1.setCurrent(Param_POT);
				//환자정보
				var rstUserDtl =  response.rstUserDtl;
				//console.log(rstUserDtl);
				$('#NAM').html(rstUserDtl[0]["NAM"]);
				$('#CHN').html(rstUserDtl[0]["CHN"]);
				//console.log(rstUserDtl[0].DAT);
				var datData = rstUserDtl[0].DAT.toString(); 
				var datCustom = datData.substring(0,4)+"-"+datData.substring(4,6)+"-"+datData.substring(6,8);
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
				// 검사결과 height 조절을 위한 rsTableList 데이터 초기화/추가
				rsTableList = "";
				rsTableList = rstUserDtl2;
				if(rstUserDtl2.length > 0){
					
					for (var i in rstUserDtl2){
						if(rstUserDtl2[i].O_CKI == "Y" && img_cnt == 0){
							calLCnt ++;
//	 						console.log("(rstUserDtl2[i].O_CKI    ====  "+calLCnt);
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
					
					//Remark(혈종)
					var rstUserMw108rmA = response.rstUserMw108rmA;
					//var hematomaSplit = param_rstUserMw108rmA.O_REMK.split("|");
					//console.log("rstUserMw108rmA.length = " + rstUserMw108rmA.length);
					if(rstUserMw108rmA.length > 0){
						hematomaStr = rstUserMw108rmA[0].RREMK;
					}
					
					/*
					for(var i in hematomaSplit){
						hematomaStr += "&nbsp;&nbsp;"+hematomaSplit[i]+"<br>"
					}
					*/
					
					if(hematomaStr == "" || hematomaStr == "<br>" || hematomaStr == "<br><br>&nbsp;&nbsp;"){
						hematoma_str = "N";
					}
					
					$('#Hematoma').html(hematomaStr);
					
					//Remark(혈종) 끝
					
					//Remark(산전)
					var rstUserMw108rmB = response.rstUserMw108rmB
					var obgyStr = "";
					/*
					var obgySplit = param_rstUserMw108rmB.O_REMK.split("|");
					var obgyStr = "";
					for(var i in obgySplit){
						obgyStr += "  "+obgySplit[i]+"<br>" 
					}
					*/
					if(rstUserMw108rmB.length > 0){
						obgyStr = rstUserMw108rmB[0].RREMK
					}
					
					if(obgyStr == "" || obgyStr == "<br>" || obgyStr == "<br><br>&nbsp;&nbsp;"){
						obgy_str = "N";
					}
					
					$('#Obgy').html(obgyStr);
					
					//Remark(산전) 끝
					
					
					//microbio
					var rstUserMw118rms1 = response.rstUserMw118rms1;
					//var resultList =  response.resultList;
					//console.log(rstUserMw118rms1.length);
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
					//microbio 끝
					
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
					
					//lmbyn 20140502 이후
					/*
					var param_rstUserMw106rms6 = response.param_rstUserMw106rms6
					if(LmbYN_str == "Y"){
						if($('#I_DTLDAT').val()> 20140502){
							var result = "";
							var pathlogyTapeHtml = "";
							var pathlogyTapeSplit = param_rstUserMw106rms6.O_TXT.split("|");
							//console.log("pathlogyTapeSplit = "+pathlogyTapeSplit);
							
							var pathlogyTapeLenSplit = param_rstUserMw106rms6.O_LEN.split("|");
							var i = param_rstUserMw106rms6.O_CNT;
							for (var j = 0; j < i; j++){
								if(pathlogyTapeSplit[j].trim().startsWith("<")){
									pathlogyTapeHtml += "<br>";	
								}
								
								if(pathlogyTapeLenSplit[j] == "99"){
									result += pathlogyTapeSplit[j].replace(/\s+$/, "");
								} else {
									result += pathlogyTapeSplit[j].replace(/\s+$/, "");
									pathlogyTapeHtml += "  "+result+"<br>";
									result =  "";
								}
							}
							LmbYN_html = pathlogyTapeHtml;
							$('#LmbYN').html(LmbYN_html);
						}
					} else {
						var result = "";
						var pathlogyTape2Html = "";
						var pathlogyTapeSplit = param_rstUserMw106rms6.O_TXT.split("|");
						
						
						var pathlogyTapeLenSplit = param_rstUserMw106rms6.O_LEN.split("|");
						var i = param_rstUserMw106rms6.O_CNT;
						for (var j = 0; j < i; j++){
							if(pathlogyTapeSplit[j].trim().startsWith("<")){
								pathlogyTape2Html += "<br>";	
							}
							
							if(pathlogyTapeLenSplit[j] == "99"){
								result += pathlogyTapeSplit[j].replace(/\s+$/, "");
							} else {
								result += pathlogyTapeSplit[j].replace(/\s+$/, "");
								pathlogyTape2Html += "  "+result+"<br>";
								result =  "";
							}
						}
						pathologyType2_html = pathlogyTape2Html;
						$('#PathologyType2').html(pathologyType2_html);
					}
					*/
					var rstUserMw106rms6 = response.rstUserMw106rms6;
					//var param_rstUserMw106rms6 = response.param_rstUserMw106rms6
					//console.log("param_rstUserMw106rms6.O_TXT = " + param_rstUserMw106rms6.O_TXT).replace(/\s+$/, "");
					//console.log("param_rstUserMw106rms6.O_TXT = " + param_rstUserMw106rms6.O_TXT).replace(/\s/, "");
					//console.log("param_rstUserMw106rms6.O_LEN = " + param_rstUserMw106rms6.O_LEN);
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
					
					//CytologyType
					/*
					var param_rstUserMw105rms1 = response.param_rstUserMw105rms1
					if(param_rstUserMw105rms1.O_ERR == "E"){
						return;
					}
					var result = "";
					var CytologyTypeHtml = "";
					CytologyType_html = "";
					var CytologyTypeSplit = param_rstUserMw105rms1.O_TXT.split("|");
					//console.log("CytologyTypeSplit = "+CytologyTypeSplit);
					
					var CytologyTypeLenSplit = param_rstUserMw105rms1.O_LEN.split("|");
					var i = param_rstUserMw105rms1.O_CNT;
					for (var j = 0; j < i; j++){
						if(CytologyTypeSplit[j].trim().startsWith("<")){
							CytologyTypeHtml += "<br>";	
						}
						
						if(CytologyTypeLenSplit[j] == "99"){
							result += CytologyTypeSplit[j].replace(/\s+$/, "");
						} else {
							
							if(CytologyTypeSplit[j].indexOf("nuclear") > 0){
								result += " ";
							}
							
							result += CytologyTypeSplit[j].replace(/\s+$/, "");
							CytologyTypeHtml += "  "+result+"<br>";
							result =  ""
						}
					}
					*/
					var rstUserMw105rms1 = response.rstUserMw105rms1;
					if(rstUserMw105rms1.length > 0){
						CytologyType_html = rstUserMw105rms1[0].RTXT;
					}
					
					//$('#CytologyType').html(CytologyType_html);
					
					//CytologyType 끝 
					
					
					//CytologyType2
					/*
					var param_rstUserMw106rms1 = response.param_rstUserMw106rms1
					if(param_rstUserMw106rms1.O_ERR == "E"){
						return;
					}
					var result = "";
					var CytologyTypeHtml2 = "";
					CytologyType2_html = "";
					var CytologyTypeSplit2 = param_rstUserMw106rms1.O_TXT.split("|");
					//console.log("CytologyTypeSplit = "+CytologyTypeSplit);
					
					for (var j = 0; j < i; j++){
						CytologyTypeHtml2 += "<br>";
						CytologyTypeHtml2 += "  "+CytologyTypeSplit2[j]+"<br>";
					}
					CytologyTypeHtml2 += "  ";
					
					*/
					var rstUserMw106rms1 = response.rstUserMw106rms1;
					if(rstUserMw106rms1.length > 0){
						CytologyType2_html = rstUserMw106rms1[0].RTXT;
					}
					
					//$('#CytologyType2').html(CytologyType2_html);
					
					//CytologyType2 끝
					
					//CytologyType3
					
					/*
					var param_rstUserMw106rms5 = response.param_rstUserMw106rms5
					if(param_rstUserMw106rms5.O_ERR == "E"){
						return;
					}
					var result = "";
					var CytologyTypeHtml3 = "";
					CytologyType3_html = "";
					var CytologyTypeSplit3 = param_rstUserMw106rms5.O_TXT.split("|");
					//console.log("CytologyTypeSplit = "+CytologyTypeSplit);
					
					var CytologyTypeLenSplit3 = param_rstUserMw106rms5.O_LEN.split("|");
					var i = param_rstUserMw106rms5.O_CNT;
					for (var j = 0; j < i; j++){
						if(CytologyTypeSplit3[j].trim().startsWith("<")){
							CytologyTypeHtml3 += "<br>";	
						}
						
						if(CytologyTypeLenSplit3[j] == "99"){
							result += CytologyTypeSplit3[j].replace(/\s+$/, "");
						} else {
							
							result += CytologyTypeSplit3[j].replace(/\s+$/, "");
							CytologyTypeHtml3 += "  "+result+"<br>";
							result =  "";
						}
					}
					*/
					var rstUserMw106rms5 = response.rstUserMw106rms5;
					if(rstUserMw106rms5.length > 0){
						CytologyType3_html = rstUserMw106rms5[0].RTXT;
					}
					
					//$('#CytologyType3').html(CytologyType3_html);
					
					//CytologyType3 끝
					
					//Cytogene
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
					
					//Cytogene 끝
					
					//Remarktext
					/*
					var param_rstUserMw107rms3 = response.param_rstUserMw107rms3
					
					
					$('#Remarktext').html(remarkData);
					*/
					
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
					
					//CytologyType_html += "<br>"+remarkData;
					//CytologyType2_html += "<br>"+remarkData;
					//CytologyType3_html += "<br>"+remarkData;
					
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
					
					//Remarktext 끝
					
					
				}
				calLCnt--;
				mastResult();
//				setTimeout("mastResult()",1000);
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
							pdf_html +=         "<font color='#0000ff' >"+$('#I_DTLDAT').val()+"("+$('#NAM').html()+") This service is in preparation</font></a>";
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
		
		function infoClick(){
	 		if($('#infoOpen').hasClass('open')){
				$('#infoOpen').removeClass('open');
				$('#infoOpen').html('[정보열기]');
				$('#infoTbody').find('.infoTr').fadeOut(function(){
					TabResize();
				});
			}else{
				$('#infoOpen').addClass('open');
				$('#infoOpen').html('[정보닫기]');
				$('#infoTbody').find('.infoTr').fadeIn(function(){
					TabResize();
				});
			} 
			
		}
		
		/** 결과지출 버튼 클릭 **/
		function openPopup(str){
			var formData = $("#searchForm").serialize();
			
			if(str == "9"){
				//getCurrent
				var checkBox = $('input:checkbox[name=rstCheck]:checked');
				var checkBox2 = $('input:checkbox[name=rstCheck]');
				
				if(checkBox.length == 0){
					messagePopup("","","검사 결과 최소 1건 이상 선택 필수입니다." );
					return;
				}
				
				if(checkBox.length > 0){
					var data = [];
					
					// 수진자 정보 data에 담기
					var gridData = rsTableList;
					gridData["RSTUSERTABLE"] = "RSTUSERTABLE";
					gridData["JNO"] = $("#I_DTLJNO").val();
					gridData["DAT"] = $("#I_DTLDAT").val();
					gridData["I_HOS"] = $("#I_PHOS").val();
					//console.log(gridData);
					
					data.push(gridData);
					
					// check된 데이터 data에 담기
					checkBox2.each(function(i){
						//console.log(checkBox2[i].checked);
						if(checkBox2[i].checked == true){
							data.push(rsTableList[i]);
						}
					});
					
					//console.log(data);
					formData = data;
					//fnOpenPopup_mini("/rstReport.do","결과지 출력",data,callFunPopup);
					fnOpenPopup_mini("/rstReport_mini.do","결과지 출력",data,callFunPopup);
				}
			} 
		}
		
		function fnOpenPopup_mini(url,labelNm,gridValus,callback){
			$('#pageModalView_mini', parent.document).modal('show');
			var openSrc = url;
			if(url.indexOf("?")>-1){
				openSrc += "&menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM);
			}else{
				openSrc += "?menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM);
			}

//			$('#pageiframe_mini', parent.document).width(575);
//		    $('#popCont', parent.document).width(600);
		    
//			if($('#pageiframe_mini', parent.document).width() < 520){
//				var dynWidth = 840;
////					$('#pageiframe_mini').height(parseInt(dynheight) + 10);
//				$('#pageiframe_mini').width(parseInt(dynWidth) + 10);
////					$('#popCont').height(parseInt(dynheight) + 90);
//				$('#popCont').width(parseInt(dynWidth) + 35);
//			}
			
			$('#pageiframe_mini', parent.document).attr("src" , openSrc);
			
			$('#pageModalLabel_mini', parent.document).html(labelNm);
			$('#pageModalView_mini', parent.document).off().on('shown.bs.modal', function (e) {
				try{
					$('#pageiframe_mini', parent.document).width(605);
				    $('#popCont_mini', parent.document).width(600);
					$('#pageiframe_mini', parent.document)[0].contentWindow.callResize()  ;
				}catch (e) {  }
			});
			
			$('#pageiframe_mini', parent.document).on("load", function(e){
				$('body', parent.document).addClass("modal-open");
//					window.parent.closeModal(fnCall,param,closeType,pageId)
				if(!isNull($(this).attr("src" ))){
					if(typeof callback=="function"){
						var iframe = $(e.target)[0].contentWindow;
						//console.log("iframe : "+iframe);
						callback(url,iframe,gridValus);
					}
				}
			});
		}
		
		/** 팝업 실행 **/
		function  callFunPopup(url,iframe,formData){
			
			/* console.log(">> url >> "+url);
			console.log(">> iframe >> "+iframe);
			console.log(">> formData >> "+formData);
			console.log(""); */
		
			if(url == "/rstReport_mini.do"){
				//console.log(">> formData >> "+formData);
				iframe.getReport(formData);
				//iframe.getReport_mini(formData);
				
			}
		}
		
		/**팝업 크기 설정**/
		function resizeTopIframe(dynheight,dynWidth) {  
			//width
			$('#pageiframe_mini').height(500);
			$('#pageiframe_mini').width(580);
			$('#popCont_mini').height(550);
			$('#popCont_mini').width(610);
		}
		
		/** 메세지 팝업 **/
		function parentMag(type,yesFn,strMessage){

			if(type=="confirm"){
		        $.message.confirm({
					title: 'Confirm',
		            contents : strMessage,
		            onConfirm: function () {
		        		setTimeout(function() {
		        			yesFn();
		        		}, 400);
		            },
		            onCancel: function () {
						rtnBool = false;
		            }
		        });
			}else{
				$.message.alert({
		            title : 'Alert',
		            contents : strMessage,
		            onConfirm: function () {
		        		if(typeof yesFn == "function"){
			        		setTimeout(function() {
			        			yesFn();
			        		}, 400);
		            	}
		            }
		        });
			} 
		}
		
		window.closeModal2 = function(fnCall,param,closeType,pageId){

			try{
				//console.log("ttt_aaaaaaaaaaaaaa");
				if(isNull(typeof curr_menu_cd ))
				{
					//console.log("ttt_bbbbbbbbbbbbbbb");
					parent.closeModal2(fnCall,param,closeType,pageId);
				}else{
					//console.log("ttt_ccccccccccccccc");

					$('#pageModalView_mini').modal('hide');
					$('#pageModalView_mini').removeClass('in')
					  .attr('aria-hidden', true)
					  .off('click.dismiss.bs.modal')
					$('#pageModalView_mini').css("display","");
					$('#pageModalView_mini').find(".modal-backdrop").remove();
					$('#pageiframe_mini').off("load");
					$('#pageiframe_mini').unbind("load");
					$('#pageiframe_mini').attr("src" , "");
					var func,contDocument;
					if(!isNull(fnCall)){
						//console.log("ttt_ddddddddddddddddddddd");
						//Popup1
						contDocument= $('#pageiframe_mini')[0].contentWindow;
						func = contDocument[fnCall];
						func(param);
					}
				}
			}catch (e) {
				// TODO: handle exception
			}
		};
	</script>
</head>
<body onresize="parent.resizeTo(680,715)">
	<%-- <input type="hidden" id="rstFormData" value="${param}" > --%>
	<form method="post" action="" name="reportForm">
		<input type="hidden" name="viewerUrl" />
		<input type="hidden" name="mrd_path" />
		<input type="hidden" name="rdServerSaveDir" />
		<input type="hidden" name="param" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="downFileName" />
		<input type="hidden" name="imgObj" />
	</form>
	<form id="searchForm" name="searchForm">
		<!-- cjw -->
		<input id="I_COR" name="I_COR" type="hidden" value="NML"/>
		<input id="DAT" name="DAT" type="hidden" value="${I_DTLDAT}"/>
		<input id="JNO" name="JNO" type="hidden" value="${I_DTLJNO}"/>
		
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value='${I_LOGMNU}'/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="${I_LOGMNM}"/>
		<input id="I_ICNT" name="I_ICNT" type="hidden"> 				  	   <!-- 그리드에 보여줄 건수 -->
		<input id="I_CNT" name="I_CNT" value="40" type="hidden"> 			   <!-- 그리드에 보여줄 건수 -->
		<input id="I_ROW" name="I_ROW" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		<input id="I_IROW" name="I_IROW" value="0" type="hidden"> 				  	   <!-- 그리드에 보여줄 시작위치 -->
		
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
		<div class="content_inner" style="overflow: auto; background-color: #cdcdcd; width: 657px;">
             <div class="container-fluid" >
                 <div class="con_wrap_t col-md-7-t" style="overflow:auto; width: 655px;"><!-- min-width: 500px; -->
                     <div class="title_area">
                         <h3 class="tit_h3" id="titTest">검사 결과</h3>
                         <div class="tit_btn_area_t">
                         	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#result-Modal" onClick="javascript:openPopup(9)">결과지 출력</button>
                     	</div>
                     </div>
                  <div class="con_section" style="overflow:auto; top:-10px;height: 600px;">
                   <div class="con_wrap_t col-md-12 col-sm-12" style="overflow-y: auto; overflow-x:hidden;" id="rstDiv">
                      	<div class="" style="cursor:default;" id="infoDiv"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                      		<!-- <button type="button" class="btn-info open" onClick="javascript:infoClick()" id="infoOpen">수진자 정보 닫기</button> -->
                          	<table class="table table-bordered table-condensed tbl_l-type tbl_type" width="100%" >
                              	<colgroup>
                                      <col width="16.66%">
                                      <col width="16.66%">
                                      <col width="16.66%">
                                      <col width="16.66%">
                                      <col width="16.66%">
                                      <col width="16.66%">
                                  </colgroup>
                                  <tbody id="infoTbody">
                                  	<tr>
                                      	<th scope="row">
                                      	수진자
                                      	<span class="open" onClick="javascript:infoClick()" id="infoOpen" style="color:#505050; cursor:pointer;">[정보닫기]</span>
                                      	</th>
                                          <td><label class="label_basic" id="NAM"></label></td>
                                          <th scope="row">차트번호</th>
                                          <td><label class="label_basic" id="CHN"></label></td>
                                          <th scope="row">접수일자</th>
                                          <td><label class="label_basic" id="DAT_HTML"></label></td>
                                      </tr>
                                      <tr class="infoTr">
                                      	<th scope="row">주민등록번호</th>
                                          <td><label class="label_basic" id="JN"></label></td>
                                          <th scope="row">담당의/과/병동</th>
                                          <td><label class="label_basic" id="DPTN"></label></td>
                                          <th scope="row">성별</th>
                                          <td><label class="label_basic" id="SEX"></label></td>
                                      </tr>
                                      <tr class="infoTr">
                                      	<th scope="row" id="REALAGE">나이</th>
                                          <td><label class="label_basic" id="AGE"></label></td>
                                          <th scope="row">검체종류</th>
                                          <td><label class="label_basic" id="ETCINF"></label></td>
                                          <th scope="row">검체번호</th>
                                          <td><label class="label_basic" id="ETC"></label></td>
                                      </tr>
                                      <tr class="infoTr">
                                      	<th scope="row">의료기관</th>
                                          <td><label class="label_basic" id="HOSN"></label></td>
                                          <th scope="row">병원코드</th>
                                          <td><label class="label_basic" id="HOS"></label></td>
                                          <th scope="row">접수번호</th>
                                          <td><label class="label_basic" id="JNO_HTML"></label></td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          <!-- class="tbl_type" -->
                          <div  style="width:100%;" > <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<!-- 
                          	<div id="realgrid2"  class="realgridH"></div>
                          	 -->	
                          	 <div class="" id="rstTableDiv" style="cursor:default; min-height: 50px;">
                          	 <table class="table table-bordered table-condensed tbl_c-type tbl_type" width="100%" id="rstResultTable">
                          	 	<colgroup>
                          	 		<%-- <col width="20px" />
                          	 		<col width="90px"/>
                          	 		<col width="130px"/>
                          	 		<col width="140px"/>
                          	 		<col width="140px"/>
                          	 		<col width="40px"/>
                          	 		<col width="90px">
                          	 		<col width="60px"/> --%>
                          	 		<col width="5%" />
                          	 		<col width="12%"/>
                          	 		<col width="19%"/>
                          	 		<col width="18%"/>
                          	 		<col width="18%"/>
                          	 		<col width="7%"/>
                          	 		<col width="12%">
                          	 		<col width="9%"/>
                          	 	</colgroup>
                          	 	<thead>
                          	 		<tr style="font-weight: bold;">
                          	 			<td><input type="checkbox" id="resCheck"></td>
                          	 			<td>보험코드</td>
                          	 			<td>검사항목</td>
                          	 			<td>결과</td>
                          	 			<td>참고치</td>
                          	 			<td>H/L</td>
                          	 			<td>보고일자</td>
                          	 			<td>시계열</td>
                          	 		</tr>
                          	 	</thead>
                          	 	<tbody id="rstResultBody" name="rstResultBody">
                          	 	</tbody>
                          	 </table>
			 </div>
                    </div>
                    
                          <div style="height:20px"></div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivMastResult"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                          <th scope="col">Result</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="MastResult"></div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivMastResult_Reference"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                          		<colgroup>
                                      <col width="100%">
                                  </colgroup>
                          		<thead>
                                  	<tr>
                                          <th scope="col">Specific IgE Reference Range</th>
                                      </tr>
                                  </thead>
                                  <tbody id="mastrefer">
                                 		<tr>
                                 			<td>
                                 				<table>
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
							</table>
                                 			</td>
                                 		</tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          
                          <div class="tbl_type" style="overflow:auto" id="DivRia"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                          <th scope="col">Prenatal Screening Test</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td>
                                          	<div id="Ria"></div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div> 
                          
                          <div class="tbl_type" style="overflow:auto" id="DivHematoma"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  		<!--
                                  			<th scope="col">Remark(혈종)</th> 
                                  		 -->
                                          <th scope="col">혈종검사소견</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="Hematoma"></div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivObgy"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  		<!-- 
                                  			<th scope="col">Remark(산전)</th>
                                  		 -->
                                          <th scope="col">산전검사소견</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="Obgy"></div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivHasimg"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  		<!-- 
                                  			<th scope="col">Image results (img)</th>
                                  		 -->
                                          <th scope="col">Image results</th>
                                      </tr>
                                  </thead>
                                  <tbody id="hasimg">
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivPopphoto"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  		<!--
                                  			<th scope="col">Image results (photo)</th> 
                                  		 -->
                                          <th scope="col">Image results</th>
                                      </tr>
                                  </thead>
                                  <tbody id="Popphoto">
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivMicrobio"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed">
                              	<colgroup>
                                      <col width="20%">
                                      <col width="30%">
                                      <col width="20%">
                                      <col width="30%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                          <th scope="col" colspan="4">Microbio Result</th>
                                      </tr>
                                  </thead>
                                  <tbody id="Microbio">
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto;" id="DivLmbYN"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                          <!--
                                          	<th scope="col">면역병리 진단보고 (LmbYN)</th>
                                          	<th scope="col">면역병리 진단보고</th> 
                                           -->
                                           <th scope="col" id="DivLmbYNTitle">면역병리 진단보고</th>
                                          
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="LmbYN"  style="font-family:굴림체;">
                                          	</div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto;" id="DivPathologyType"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  		<!--
                                  			<th scope="col">면역병리 진단보고 (PathologyType)</th>
                                  			<th scope="col">면역병리 진단보고</th> 
                                  		 -->
                                  		 <th scope="col" id="DivPathologyTypeTitle">면역병리 진단보고</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="PathologyType" style="font-family:굴림체;">
                                          	</div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto;" id="DivPathologyType2"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  		<th scope="col" id="DivPathologyType2Title">면역병리 진단보고</th> 
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="PathologyType2" style="font-family:굴림체;">
                                          	</div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivCytologyType"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  	<!--
                                  		<th scope="col">세포병리 진단보고 (CytologyType)</th>
                                  		<th scope="col">세포병리 진단보고</th> 
                                  	 -->
                                  	 <th scope="col">세포병리 진단보고</th>
                                  	 
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="CytologyType">
                                          	</div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivCytologyType2"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  	<!--
                                  		<th scope="col">세포병리 진단보고 (CytologyType2)</th>
                                  		<th scope="col">세포병리 진단보고</th> 
                                  	 -->
                                  	 	<th scope="col">세포병리 진단보고</th>
                                          
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="CytologyType2">
                                          	</div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          
                          <div class="tbl_type" style="overflow:auto" id="DivCytologyType3"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                  	<!--
                                  		<th scope="col">세포병리 진단보고 (CytologyType3)</th>
                                  		<th scope="col">세포병리 진단보고</th> 
                                  	 -->
                                  	 	<th scope="col">세포병리 진단보고</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="CytologyType3">
                                          	</div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                          <!-- 사용하지 않음 데이터도 없음 (최재원 대리님 요청확인) 운영에서 데이터 확은으로인해 노출되도록 변경 20190225 (최재원 대리님 요청)--> 
                          <div class="tbl_type" style="overflow:auto" id="DivCytogene"> 
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                          <th scope="col">Cytogenetics Result</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="Cytogene"></div>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                           
                          <div class="tbl_type" style="overflow:auto" id="DivRemarktext"> <!-- [D] style="overflow:auto" 스크롤 생기게 인라인 스타일 -->
                          	<table class="table table-bordered table-condensed tbl_c-type">
                              	<colgroup>
                                      <col width="100%">
                                  </colgroup>
                                  <thead>
                                  	<tr>
                                          <!-- 
                                          <th scope="col">Remark isRemarktext</th>
                                           -->
                                          <th scope="col">Remark</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	<tr>
                                          <td style="text-align:left;">
                                          	<div id="Remarktext" style="font-family:굴림체;">
                                          	</div>
                                          	<!-- 
                                          	<label class="label_basic" id="RemarkPositive"></label>
                                          	<label id="Remarktext"></label>
                                          	 -->
                                            	
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                              
                        </div>
                        
                       </div>
                   </div>
			</div>
		</div>
	<%-- 	<%-- 팝업 --%> 
	<div class="modal fade" id="pageModalView_mini" tabindex="-1" role="dialog" aria-labelledby="pageModalLabel_mini" data-backdrop="static" aria-hidden="true">
	    <div class="modal-dialog"  id="popCont_mini">
	        <div class="modal-content">
	            <div class="modal-header">
	                <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnclose()"> -->
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal2()">
	                <!-- <span aria-hidden="true">&times;</span> --></button>
	                <h4 class="modal-title" id="pageMpopCont_mini"></h4>
	            </div>
	            <div class="modal-body" id="popCont_mini">
		        	<iframe id="pageiframe_mini" src="" style="width:60%; overflow:hidden;"  frameborder="0" scrolling="yes"></iframe>
		      	</div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>감염병 신고</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%> 
	
    <%
		String I_FNM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FNM"), "");
		String I_HOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_HOS"), "");
		
    %>
    
    <%
	    String Param_PFNM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FNM"), "");
		String Param_PHOS = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_HOS"), "");
	
    	String Param_TNM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_TNM"), "");
    	String Param_NAM = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_NAM"), "");
		String Param_FDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_FDT"), "");
		String Param_TDT = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("I_TDT"), "");

		
    %>
	<script>
	
	var resultCode = "";
	var gridView,dataProvider;
	
	$(document).ready( function(){
		var con = document.getElementById("hosSearch");
		
	    if($('#I_ECF').val() == "E"){
	    	con.style.display = 'block';
	    }
        var I_FNM = "<%=I_FNM%>";
        var I_HOS = "<%=I_HOS%>";
        
        //console.log("I_FNM = " + I_FNM);
        //console.log("I_HOS = " + I_HOS);
        
        if(!isNull(I_FNM)){
			$("#I_FNM").val(I_FNM);      
			$("#I_HOS").val(I_HOS);     
        }
        
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid1");
	    
	    setStyles(gridView);
	    gridView.setDataSource(dataProvider);
	    gridView.setStyles(basicGrayBlueSkin);
	    gridView.setOptions({
	    	panel : {
				visible : false		
			},footer: {
                visible: false
            }
	    })
	    
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })

	    var fields = [
	    	{"fieldName": "R_GUN"},
	        {"fieldName": "R_TNM"},
	        {"fieldName": "R_JDT"},
	        {"fieldName": "R_JDT2"},
	        {"fieldName": "R_BDT"},
	        {"fieldName": "R_BDT2"},
	        {"fieldName": "R_SDT"},
	        {"fieldName": "R_SDT2"},
	        {"fieldName": "R_DAT"},
	        {"fieldName": "R_DAT2"},
	        {"fieldName": "R_TCD"},
	        {"fieldName": "R_JNO"},
	        {"fieldName": "R_NAM"},
	        {"fieldName": "R_CHN"},
	        {"fieldName": "R_BIR"},
	        {"fieldName": "R_BIR2"},
	        {"fieldName": "R_SEX"},
	        {"fieldName": "R_CBC"},
	        {"fieldName": "I_LOGMNU"},
	        {"fieldName": "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	    
	    var columns = [
	    	
	        /* {"name": "R_GUN", "fieldName": "R_GUN", "width": "50",  "styles": {"textAlignment": "center"}, "header": {"text": "질병군"}} */
	        {"name": "R_GUN", "fieldName": "R_GUN", "width": "80",  "styles": {"textAlignment": "center"}, "header": {"text": "감염병분류"}}	        
	      ,	{"name": "R_TNM", "fieldName": "R_TNM", "width": "150", "styles": {"textAlignment": "near"},   "header": {"text": "병원체"}}
	      ,	{"name": "R_JDT", "fieldName": "R_JDT2", "width": "80", "styles": {"textAlignment": "center"}, "header": {"text": "검체의뢰일"}}
	      ,	{"name": "R_BDT", "fieldName": "R_BDT2", "width": "80", "styles": {"textAlignment": "cenger"}, "header": {"text": "진단일"}}
	      ,	{"name": "R_SDT", "fieldName": "R_SDT2", "width": "80", "styles": {"textAlignment": "cenger"}, "header": {"text": "신고일"}}
	      ,	{"name": "R_DAT", "fieldName": "R_DAT2", "width": "80", "styles": {"textAlignment": "cenger"}, "header": {"text": "접수일자"}}
	      ,	{"name": "R_TCD", "fieldName": "R_TCD",  "width": "80", "styles": {"textAlignment": "cenger"}, "header": {"text": "검체명"}}
	      ,	{"name": "R_JNO", "fieldName": "R_JNO", "width": "60",  "styles": {"textAlignment": "cenger"}, "header": {"text": "접수번호"}}
	      ,	{"name": "R_NAM", "fieldName": "R_NAM", "width": "80",  "styles": {"textAlignment": "center"},   "header": {"text": "성명"}}
	      ,	{"name": "R_CHN", "fieldName": "R_CHN", "width": "100", "styles": {"textAlignment": "center"},   "header": {"text": "등록번호"}}
	      ,	{"name": "R_BIR", "fieldName": "R_BIR2", "width": "80", "styles": {"textAlignment": "center"}, "header": {"text": "생년월일"}}
	      ,	{"name": "R_SEX", "fieldName": "R_SEX", "width": "50",  "styles": {"textAlignment": "center"}, "header": {"text": "성별"}}
	      ,	{"name": "R_CBC", "fieldName": "R_CBC", "width": "150", "styles": {"textAlignment": "center"},   "header": {"text": "과명/병동"}}
	    ];
	    gridView.setColumns(columns);
	  	
	    //체크바 제거
	    var checkBar = gridView.getCheckBar();
	    checkBar.visible = false;
	    gridView.setCheckBar(checkBar);
	    
	    //상태바 제거
	    var StateBar = gridView.getStateBar();
	    StateBar.visible = false;
	    gridView.setStateBar(StateBar);
	    
	    gridView.setDisplayOptions({
  			fitStyle: "even"
		});
	    
	    
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "DISE_CLS", "Y");
		$('#I_GUN').html(resultCode);
		jcf.replace($("#I_GUN"));
		
		resultCode = getCode(I_LOGMNU, I_LOGMNM, "INFECT_TERM", "N");
		$('#I_GBN').html(resultCode);
		jcf.replace($("#I_GBN"));
		
		//달력 from~to
		$("#I_FDT").datepicker({
			dateFormat: 'yy-mm-dd',
			maxDate: $('#I_TDT').val(),
			onSelect: function(selectDate){
				$('#I_TDT').datepicker('option', {
					minDate: selectDate,
				});
			}
		});	
		$("#I_TDT").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: $('#I_FDT').val(),
			onSelect : function(selectDate){
				$('#I_FDT').datepicker('option', {
					maxDate: selectDate,
				});
			}		
		});
		var monthAgo = new Date();
		monthAgo.setDate(monthAgo.getDate() - 30);
		$("#I_FDT").datepicker('setDate',monthAgo);
        $("#I_TDT").datepicker('setDate',new Date);
        //$("#I_FDT").attr( 'readOnly' , 'true' );
        //$("#I_TDT").attr( 'readOnly' , 'true' );
        
     	// 기간 검색 구분 - 1일,3일,1개월,3개월
		var buttonList = getTerm(I_LOGMNU, I_LOGMNM, "S_TERM");
		$('#I_S_TERM').append(buttonList);
		
		var Param_HOS = "<%=Param_PHOS%>";
		var Param_FNM = "<%=Param_PFNM%>";
		var Param_TNM = "<%=Param_TNM%>";
		var Param_NAM = "<%=Param_NAM%>";
        var Param_FDT = "<%=Param_FDT%>";
        var Param_TDT = "<%=Param_TDT%>";
        
        if(!isNull(Param_FDT)){
        	/** 메인에서 들어올경우 조회 조건 셋팅 **/
        	// 사원, 병원사용자
			
        	$("#I_HOS").val(Param_HOS);     // 병원코드
        	$("#I_FNM").val(Param_FNM);     // 병원명
        	$("#I_TNM").val(Param_TNM);     // 병원체
			$("#I_NAM").val(Param_NAM);     // 수진자명
			
			$("#I_FDT").datepicker('setDate', Param_FDT);		// 시작
			$("#I_TDT").datepicker('setDate', Param_TDT);	 	// 종료
        } 
        else 
        {
        <%
  		Map<String, Object> QuickParam = new HashMap<String, Object>();
  		if(paramList.size()>0){
  			for(int idx=0;idx<paramList.size();idx++)
  			{
  				QuickParam = paramList.get(idx);
  				String RCV = QuickParam.get("S007RCV").toString();
  				String RCD = QuickParam.get("S007RCD").toString();
  				String VCD = QuickParam.get("S008VCD").toString();
  				if(!"".equals(QuickParam.get("S008VCD"))){
		%>
				if("<%=RCV%>" == "S_TERM"){
					changeDT("<%=VCD%>");
				}else{
					if("<%=RCV%>" == "TERM_DIV"){
						if(isNull("<%=VCD%>")){
							$("#<%=RCD%>").val("D");
						}else{
							$("#<%=RCD%>").val("<%=VCD%>");
						}
					}else if("<%=RCV%>" == "RESULT_DIV"){
						$("input[id=I_RGN<%=VCD%>]:input[value=<%=VCD%>]").prop('checked',true);
					}else{
						$("#<%=RCD%>").val("<%=VCD%>");
					}
			  		jcf.replace($("#<%=RCD%>"));
				}
	<%
  				}
  			}
  		}
  	%>
        }
        dataResult1();
	});
	
	function dataResult1(){
		
		//var hosCode = $('#I_HOS').val();  
		//$('#I_HOS').val(hosCode);
		
		var formData = $("#searchForm").serializeArray();
		
// 		console.log(formData);
		ajaxCall("/infectList.do",formData);
		
	}
	
	function onResult(strUrl,response){
		if(strUrl == "/infectList.do"){
			var resultList = response.resultList;
			for (var i in resultList){
				if(resultList[i].R_JDT != 0){
					resultList[i].R_JDT2 = resultList[i].R_JDT.substring(0,4)+"-"+resultList[i].R_JDT.substring(4,6)+"-"+resultList[i].R_JDT.substring(6,8);
				}
				
				if(resultList[i].R_BDT != 0){
					resultList[i].R_BDT2 = resultList[i].R_BDT.substring(0,4)+"-"+resultList[i].R_BDT.substring(4,6)+"-"+resultList[i].R_BDT.substring(6,8);
				}
				
				if(resultList[i].R_SDT != 0){
					resultList[i].R_SDT2 = resultList[i].R_SDT.substring(0,4)+"-"+resultList[i].R_SDT.substring(4,6)+"-"+resultList[i].R_SDT.substring(6,8);
				}
				
				if(resultList[i].R_DAT != 0){
					resultList[i].R_DAT2 = resultList[i].R_DAT.substring(0,4)+"-"+resultList[i].R_DAT.substring(4,6)+"-"+resultList[i].R_DAT.substring(6,8);
				}
				
				if(resultList[i].R_BIR != 0){
					resultList[i].R_BIR2 = resultList[i].R_BIR.substring(0,4)+"-"+resultList[i].R_BIR.substring(4,6)+"-"+resultList[i].R_BIR.substring(6,8);
				}
				
				if(resultList[i].R_NAM != ""){
					resultList[i].R_NAM = resultList[i].R_NAM.trim();
				}
				
				if(resultList[i].R_CHN != ""){
					resultList[i].R_CHN = resultList[i].R_CHN.trim();
				}
				
				if(resultList[i].R_TCD != ""){
					resultList[i].R_TCD = resultList[i].R_TCD.trim();
				}
				
			}
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	
	
	//조회
	function search(){
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		if(!searchValidation()) {return;}
		dataResult1();
	}
	
	function searchValidation(){
		
		if($('#I_HOS').val() == ""){
			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"],dataClean);
			return false;
		}
		/*
		if($('#I_NAM').val() == ""){
			CallMessage("245", "alert", ["수진자 명을"], dataClean);
			return false;
		}
		*/
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		var interval = eDate - sDate;
		var day = 1000*60*60*24;

		// 유효성검사 - 최대 검색 기간 체크 !!
		var now = new Date();
	    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
			CallMessage("301","alert",["10"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
			return ;
	   	}
		
		if (interval/day > 365){
   			CallMessage("274","alert","", dataClean);		//최대 1년 내에서 조회해주세요.
			return false;
		}
		
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			CallMessage("273", "alert","", dataClean);
		   return false;
		}
		
		if(sDate > eDate){
			CallMessage("229","alert","", dataClean); // 조회기간을 확인하여 주십시오.
			return false;
		}
	      
		return true;
		
	}
	
	function dataClean(){
		gridView.cancel();
		dataProvider.clearRows();
		
	}
	
	function openPopup(index)
	{
		var labelNm,strUrl,gridValus;
		
		if(index =="2"){
			strUrl = "/infect01.do";
			labelNm = "기준항목 조회";
				
		}else{
			strUrl = "/hospSearchS.do";
			labelNm = "병원 조회";
		}
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,gridValus){
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
		}
			iframe.gridViewRead(gridValus);
	}
	
	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(data[0] == "/hospSearchSList.do"){
			$('#I_HOS').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
	    }
	}
	
	function exportGrid(){
		var totalCnt = dataProvider.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		CallMessage("257","confirm", "", gridExport);
	}
	
	function gridExport(){
		gridView.exportGrid({
		    type: "excel",
		    target: "local",
		    fileName: "감염병신고내역.xlsx",
		    showProgress: true,
		    applyDynamicStyles: false,
		    progressMessage: "엑셀 Export중입니다.",
		    indicator: "default",
		    header: "visible",
		    footer: "default",
		    compatibility: "2010",
		    done: function () {  //내보내기 완료 후 실행되는 함수
		    }
		});
	}
	
	
	function enterSearch(enter){
		if(event.keyCode == 13){
			if(enter == "1"){
				search();
			}else{
				openPopup(enter);
			}
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
		}
	}
	
	</script>
</head>
<body>
	<div class="content_inner">
		<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
		<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/>
		<%-- <input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>"/> --%>
		
		<!-- 검색영역 -->
		<div class="container-fluid">
			<div class="con_wrap col-md-12 srch_wrap">
				<div class="title_area open">
                    <h3 class="tit_h3">검색영역</h3>
                    <a href="#" class="btn_open">검색영역 접기</a>
                </div>
                <div class="con_section row">
                	<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>
                	<div class="srch_box">
                		<div class="srch_box_inner">
                			<div class="srch_item srch_item_v5">
<!-- 	                			<label for="I_GBN" class="label_basic">등록일</label> -->
                				<label for="I_S_TERM" class="label_basic">기간</label>
	                			<div class="select_area">
                            		<!-- <select id="searchWordS4" name="searchWordS4" class="form-control"></select> -->
                            		<select id="I_GBN" name="I_GBN"></select>
	                            </div>
                				<div class="btn_group" style="font-size: 14px" id="I_S_TERM"></div>
                				
                				<span class="period_area">
                                    <label for="I_FDT" class="blind">날짜 입력</label>
                                    <input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT">
                                </span>
                                ~
                                <span class="period_area">
                                    <label for="I_TDT" class="blind">날짜 입력</label>
                                    <input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT">
                                </span>
                			</div>
	                        <div class="srch_item srch_item_v4">
	                            <!-- <label for="I_GUN" class="label_basic">질병군</label> -->
	                            <label for="I_GUN" class="label_basic">감염병분류</label>
	                            <div class="select_area">
	                            	<select id="I_GUN" name="I_GUN"></select>
	                            </div>
	                        </div>
                		</div>
                		<div class="srch_box_inner m-t-10">
	                        <div class="srch_item srch_item_v5">
	                            <label for="I_TNM" class="label_basic">병원체</label>
	                            <input type="text" id="I_TNM" name="I_TNM" class="srch_put"  maxlength="20" onkeydown="javascript:enterSearch(1)">
	                        </div>
	                        <div class="srch_item srch_item_v4">
	                            <label for="I_NAM" class="label_basic">성명</label>
	                            <input type="text" id="I_NAM" name="I_NAM" class="srch_put"  maxlength="10" onkeydown="javascript:enterSearch(1)">
	                        </div>
                      	</div>
                      	<div class="btn_srch_box">
                        	<button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
                        </div>
                        <div class="srch_box_inner m-t-10">
	                      	<div class="srch_item hidden_srch" id="hosSearch" style="display: none;">
	                          	<label for="I_FNM" class="label_basic">병원</label>
	                          	<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup(3)" maxlength="5" onkeydown="javasscript:enterSearch(1)" class="numberOnly"/>
	                              <input type="text" style="width:203px" class="srch_put" id="I_FNM" name="I_FNM" readonly="readonly" onclick="javascript:openPopup(3)"/>	                              
	                              <button type="button" class="btn btn-red btn-sm" title="검색" onClick="javascript:openPopup(3)">
	                              <span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
	                       	</div>
                        </div>
                	</div>
                </div>
			</div>
		</div>
		</form>
		<!-- 검색영역 end -->
		
		<div class="container-fluid">
			<div class="con_wrap col-md-12 col-sm-12">
				<div class="title_area">
                    <h3 class="tit_h3">감염병 신고</h3>
                    <div class="tit_btn_area">
                        <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup(2)">기준항목 조회</button>
                        <button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button>
                    </div>
                </div>
                <div class="con_section overflow-scr">
                	<div class="tbl_type">
						<div id="realgrid1" class="realgridH"></div>                
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
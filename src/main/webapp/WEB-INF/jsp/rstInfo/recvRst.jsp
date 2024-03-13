<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>OCS 결과받기(A)</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
    <%@ include file="/inc/reportPop.inc"%>
	<style type="text/css">
	
	.li_table {position:inherit; text-align:left; padding:25px 0px 0px 35px;} 
	.li_table ul {list-style-type:none; line-height:23px} 
		
	</style>
	<script>

	$(document).ready( function(){
		
	    $('#I_FNM').change(function(){
// 	    	$('#I_HOS').val('');
	    });
		if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    	$('#hospHide').width(700);
	    	$('#I_HOS').val('');
	    }else{
	    	$('#I_FNM').val("<%=ss_nam %>");
	    }

		/* REF 참조 */
// 	    resultCode = getRefCode(I_LOGMNU, I_LOGMNM, "RECV_YN", "Y");
// 		$('#I_RECV').html(resultCode);
// 		jcf.replace($("#I_RECV"));
		/* REF 참조 */
		
		var formData = $("#searchForm").serialize();
		formData +="&I_CD1=OCST&I_CD2=PART";
		ajaxAsyncCall( "/getMC999DCommCode.do", formData);
		
		

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
		$("#I_FDT").datepicker('setDate',new Date);
        $("#I_TDT").datepicker('setDate',new Date);
// 		$("#I_FDT").datepicker('setDate',"2019-01-03");
// 		$("#I_TDT").datepicker('setDate',"2019-01-04");
  		<%
	  		Map<String, Object> QuickParam = new HashMap<String, Object>();
	  		if(paramList.size()>0){
	  			for(int idx=0;idx<paramList.size();idx++)
	  			{
	  				QuickParam = paramList.get(idx);
	  				String RCV = QuickParam.get("S007RCV").toString();
	  				String RCD = QuickParam.get("S007RCD").toString();
	  				String VCD = QuickParam.get("S008VCD").toString();
	  				String RF1 = QuickParam.get("S002RF1").toString();
	  				
		  				if(!"".equals(RF1)){
				%>
							$("#<%=RCD%>").val("<%=RF1%>");
					  		jcf.replace($("#<%=RCD%>"));
				<%
		  				}else
		  				{
			  				if(!"".equals(VCD)){
				%>
								$("#<%=RCD%>").val("<%=VCD%>");
						  		jcf.replace($("#<%=RCD%>"));
				<%
			  				}
		  				}
	  			}
	  		}
	  	%>
	});
	

	function onResult(strUrl,response){
		var resultList;
		var optionList="";
	 	var msgCd = response.O_MSGCOD;
		
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		if(strUrl == "/getMC999DCommCode.do"){
			optionList += '<option value="">전체</option>';
			$.each(resultList, function(i, e) {
				optionList += '<option value="'+e.F999NM1+'">'+e.F999NM4+ '</option>';
			});
			$('#I_HAK').html(optionList);
			jcf.replace($("#I_HAK"));
			
			if($("#I_HOS").val()=="29804"){
		    	$('#hakDIv').show();
		    	$('#hakDIv').width(400);
		    }
		}
		if(strUrl == "/recvRstDown.do"){
			var downFg = true;
			
			if(!isNull(response.file_name)){
				if(response.file_name != "nodata"){
					document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+encodeURIComponent(response.file_path)+"&file_name="+encodeURIComponent(response.file_name);
				}else{
					downFg = false;
				}
			}else{
				downFg = false;
			}
			if(!downFg){

	 			CallMessage("244", "alert", ["결과 파일 다운로드를 "]);
			}
			
		}
		//I_HAK
	}
	
	/* 팝업 호출 및 종료 */
	
	function openPopup(index)
	{
		var labelNm,strUrl,gridValus;
		strUrl = "/hospSearchS.do";
		labelNm = "병원 조회";
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,gridValus){
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
			iframe.gridViewRead(gridValus);
		}
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
			if($("#I_HOS").val()=="29804"){
		    	$('#hakDIv').show();
		    	$('#hakDIv').width(300);
		    }
	    }
	}
	
	/* 팝업 호출 및 종료 */
	
	function searchValidation(){
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		var interval = eDate - sDate;
		var day = 1000*60*60*24;

		// 유효성검사 - 최대 검색 기간 체크 !!
		var now = new Date();
		var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
			CallMessage("301","alert",["10"]);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
			return ;
		}
		
 		if($('#I_HOS').val() == ""){
 			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
 			return false;
 		}
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			CallMessage("273","alert","");	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
			return;
		}
 	 	//if (interval/day > 7){
 	 	if (interval/day > 30){
			//267
   			//CallMessage("293","alert");		//결과 다운로드 기간은 최대 7일 입니다.
   			CallMessage("287","alert");		//결과 다운로드 기간은 최대 30일 입니다.
			return false;
		}
		if(sDate > eDate){
			CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
			return;
		}
		
 		return true;
		
	}
	/*다운 1: 전체다운 , 2:수신 받지 않은 결과 다운*/
	function ResultFileDown(flag)
	{
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		if(!searchValidation()) { return;}
		
		/*다운 1: 전체다운 , 2:수신 받지 않은 결과 다운*/
		if(flag=="1"){
			$('#ISREWRITE').val("A");
		}else{
			$('#ISREWRITE').val("X");
		}
		/**
		//구분값 A-전체  O-다운받은자료  X-미다운자료
		String strDownLoadFlag = "A";
		if(!(Boolean) parameters[3]){//isAllDownload
			strDownLoadFlag ="X";
		}
		**/
		
		var url = "/recvRstDown.do";
		var formData = $("#searchForm").serializeObject();
		ajaxCall(url,formData); // 수정
	}
	/*다운*/
	/* enter시 조회 */
	function enterSearch(){
		if(event.keyCode == 13){
			//병원코드 입력 후 조회 할 경우
	 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
				if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
					var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
					if($("#I_HOS").val()=="29804"){
				    	$('#hakDIv').show();
				    	$('#hakDIv').width(300);
				    }
					$('#I_FNM').val(I_FNM);
				}
			}
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
		}
	}

	</script>
	<style type="text/css">
	.srch_box_inner{width: 100% !important;}
	</style>
</head>
<body >
<div class="content_inner">
	<div class="container-fluid">
		<div class="con_wrap col-md-12 srch_wrap">
			<div class="title_area open">
				<h3 class="tit_h3">검색영역</h3>
				<a href="#" class="btn_open">검색영역 접기</a>
			</div>
			<!-- con_section 검색영역 -->
			<div class="con_section row">
				<form id="searchForm" name="searchForm">
					<input id="ISREWRITE" name="ISREWRITE" type="hidden"/>
					<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
					<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
					<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
					<div class="srch_box">
						<div class="srch_box_inner"  >
							<div class="srch_item" >
								<label for="fr_date" class="label_basic">보고일자</label>
								<span class="period_area">
									 <label for="startDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT">
								</span>
								~
								<span class="period_area">
									 <label for="endDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT">
								</span>
							</div>
							<div class="srch_item" id="hakDIv" style="display: none;">
								<label for="" class="label_basic">학부선택</label>
								<div class="select_area">
									<select id="I_HAK" name="I_HAK" class="form-control"></select>
								</div>
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10" id="hospHide"  style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item hidden_srch">
								<label for="" class="label_basic">병원</label>
								<%-- <input id="I_HOS" name="I_HOS" class="srch_put"  type="hidden" value="<%=ss_hos%>" /> --%>
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly" onClick="javascript:openPopup(1)" maxlength="5" onkeydown="javasscript:enterSearch()" class="srch_put numberOnly"/>
	                             <input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup(1)"/>
								<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
							</div>
						</div>
					    
						<div class="srch_box_inner  m-t-10">
							<div class="srch_item_v3" style="text-align: center;" >
								<button type="button" class="btn_srch" onclick="ResultFileDown(1)">전체받기</button>
								<button type="button"  class="btn_srch" onclick="ResultFileDown(2)">수신 받지 않은 결과 받기</button>
							</div> 
						</div> 
					</div>
				</form>
			</div>
		</div>
	</div> 
	<!-- 검색영역 end -->         
	
	<div class="container-fluid">
      <div class="li_table">
	        <ul>
	          <li class="t14_b_r" style="color:red;"><b>[사용 전 반드시 읽어주시기 바랍니다]</b></li>
	          <li>ㆍ<span class="t13_b_r">[전체받기]</span>는 지정된 기간에 보고된 모든 환자 결과가 집계됩니다.</li>
	          <li>ㆍ<span class="t13_b_r">[수신 받지 않은 결과 받기]</span>는 지정된 기간에 보고된 환자중 결과를 내려받지 않은 환자 결과가 집계됩니다.</li>
	          <li>ㆍ생성된 엑셀파일 형식은 씨젠의료재단 검사결과를 병원차트 프로그램에 업로드 하기 위한 파일포멧입니다.</li>
	          <li>ㆍ보고일자는 최대 30일까지 가능합니다.</li>
	          <li>ㆍ30일 초과되는 결과 집계가 필요한 경우, 병원 담당자 통해 요청해주시기 바랍니다.</li>
	          <li>ㆍ기타문의 : <span class="t13_b_r">1566-6500</span></li>
	        </ul>
		</div>
	</div>
</div>
     <%-- 첨부파일 다운로드 --%> 
	<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
	<%-- 첨부파일 다운로드 --%> 
	<!-- content_inner -->       	
</body>
</html>


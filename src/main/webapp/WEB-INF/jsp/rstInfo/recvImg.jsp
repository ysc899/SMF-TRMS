<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>이미지 결과 받기</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
    <%@ include file="/inc/reportPop.inc"%>
	<script>
	var setTime = 200;// 이미지 생성할때 시간차를 두게 설정 
    var pscd = "";
	var I_EKD = "INIT_PW";	//EMAIL_CLS	이메일 종류 	INIT_PW	비밀번호 초기화
	var gridView;
	var dataProvider;
    var gridValues = [];
    var gridLabels = [];
    var broswer;
    
 	// 파견사원 병원유무 Flag
   	var s_paguen_hos_yn = "<%= ss_paguen_hos_yn %>";
	
	$(document).ready( function(){
		broswer = checkBroswer();
		if(broswer.indexOf("ie")>-1){
			$("#Acti_ZipDiv").removeClass("display_off");
		}
		
	    $('#I_FNM').change(function(){
// 	    	$('#I_HOS').val('');
	    });
	    
	    // 2020.10.14 - 파견사원(mclis소분류:CLIC,SAA2) 인 경우, 병원 조회 가능한 inputBox 활성화 되도록 한다.
	    // 병원(28489, 용산구보건소) 요청사항.
		if($('#I_ECF').val() == "E" || s_paguen_hos_yn != ""){
	    	$('#hospHide').show();
	    }else{
	    	$('#I_FNM').val("<%=ss_nam %>");
	    }
		
		/* REF 참조 */
	    resultCode = getRefCode(I_LOGMNU, I_LOGMNM, "RECV_YN", "Y");
		$('#I_RECV').html(resultCode);
		jcf.replace($("#I_RECV"));
		/* REF 참조 */
		
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "ACIV_ZIP");
		$("#ACIV_ZIP").html(resultCode);
		jcf.replace($("#ACIV_ZIP"));
		
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "RPT_DW_EXT");
		$('#I_RPT_DW').html(resultCode);
		jcf.replace($("#I_RPT_DW"));
		
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "TERM_DIV");
		$("#I_TERM_DIV").html(resultCode);
		$("#I_TERM_DIV").val("P");
		jcf.replace($("#I_TERM_DIV"));
		

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
// 		$("#I_FDT").datepicker('setDate',"2018-01-01");
// 		$("#I_TDT").datepicker('setDate',"2018-01-03");
// 	    	$('#I_HOS').val('14054');
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
        
		setGrid();
		$(".btn_srch").click(function(){
			dataResult();
		});
		
	});
	function dataResult(){
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		dataProvider.setRows([]);		
		gridView.setDataSource(dataProvider);
		if(!searchValidation()) { return;}
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/recvImgList.do", formData);
	}
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
 	 	if (interval/day > 7){
			//267
   			CallMessage("267","alert");		//{0}에 실패하였습니다.
			return false;
		}
		if(sDate > eDate){
			CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
			return;
		}
		
 		return true;
		
	}
	
	
	var errorImg = "";
	var errorCnt = 0;
	var TotalCnt = 0;
	var listCnt = 0;
    var d1 = $.Deferred();
    var d2 = $.Deferred();
	var FileNm = "";
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
		//시스템 저장 dir
		var systemDownDir = "<%=rdSysSaveDir%>/"+$("#I_HOS").val();
<%-- 		var systemDownDir = "<%=rdSysSaveDir%>/"+$("#I_HOS").val()+"/<%=yyyy%>/<%=mm%>/<%=dd%>"; --%>
		var zipDownDir = "<%=rdSysSaveDir%>/"+$("#I_HOS").val()+"/zip";
		var zipName = "<%=yyyy%><%=mm%><%=dd%>_"+$("#I_FNM").val()+".zip";
		var file_ext =$("#I_RPT_DW").val();

		//이미지 파일이 PDF 일경우 /PDF 이하 경로로 등록되게 변경
		if(file_ext == "PDF"){
			systemDownDir += "/PDF";
		}
		
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		//공통코드 - 화면에서 명 관련 수정 
		if(strUrl == "/getCommCode.do"){
            $.each(resultList,function(k,v){
                gridValues.push(v.VALUE);
                gridLabels.push(v.LABEL);
            });
   		 	setGrid();
		}
		// 화면 리스트 조회 
		if(strUrl == "/recvImgList.do"){
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
		
		//이미지 로그 저장 
		if(strUrl == "/ImgLogSave.do"){
			// 로그 저장 
        	TotalCnt = TotalCnt-1;
        	if(listCnt==1){
	    		callLoading(null,"off");
	    		if(errorImg !=""){
	    			errorImg += "</br>";
		    		CallMessage("270","alert",[errorImg]);
	    		}
			}
    		if(TotalCnt == 0){
    			d1.resolve();
    		}
		}
		// 엑셀 다운로드후 진행 수를 확인하여 수량 체크 후 zip, ActiveX 다운 가능한 내역 호출
		if(strUrl == "/recvImgReportFileDown.do")
		{
        	TotalCnt = TotalCnt-1;
    		if(TotalCnt == 0){
    			// 엑셀 다운로드후 진행 수를 확인하여 수량 체크 후 zip, ActiveX 다운 가능한 내역 호출
    			d1.resolve(response);
    		}
		}
		if(strUrl == "/recvImgReportZipFile.do")
		{
			if(listCnt  > errorCnt){
				if (response.fileCntInZip > 1){
					//일자_병원명.zip
	        		document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+zipDownDir+"/"+zipName+"&file_name="+encodeURIComponent(zipName);
				}else{
					document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+response.systemDownDir+"/"+response.outJpgNm+"&file_name="+encodeURIComponent(response.outJpgNm);
				}
			}
	        window.setTimeout(function () {

	    		callLoading(null,"off");
	    		
	    		if(errorImg !=""){
	    			errorImg += "</br>";
		    		CallMessage("270","alert",[errorImg]);
	    		}
	        }, setTime);
		}
		if(strUrl == "/getGroup2.do"||strUrl == "/getImgGroup.do")
		{
					
			var downName ;
			 d1 = $.Deferred();
			FileNm = "";
			errorImg = "";
			errorCnt = 0;
			if(!isNull( response.resultList)){
				TotalCnt = resultList.length;
				listCnt = resultList.length;
				// mrd_path 기존_backup
	   			<%-- var mrd_path = "<%=systemUrl%>/mrd/"; --%>
	   			var mrd_path = "";
	   			var timeOutT = 0;
	   			var timidx = 0;
	            $.each(resultList,function(idx,v){
	            	timeOutT = setTime*timidx;
	            	if(timidx%200 == 0){
	            		timidx = 0;
	            	}else{
	            		timidx++;
	            	}
	            	
			        window.setTimeout(function () {
		    			//reportPop.inc의 셋팅값 받기
		    			var param = "/rf [<%=rdAgentUrl%>]";
		    			param += " /rpprnform [A4]";
		    			param += " /rv COR[NML]";
	// 	    			param += " /rv COR["+response.I_COR+"]";
		    			param += "UID[<%=ss_uid%>]UIP[<%=ss_ip%>]";
		    			param += "JDAT["+v.F600DAT+"]JNO["+v.F600JNO+"]";
		    			param += "GCD["+v.P_GCD+"]RFN["+v.F010RNO+"]WTR[A]";
		    			param += "SYSURL["+trmsSystemUrl+"]PG_TIT["+v.PG_TIT+"]";
//		    			param += "PGCD["+v.PGCD+"]TMP["+v.S018RFN+"] /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [110] /rimageydpi [110] /p /rusesystemfont";
		    			
//		    			console.log("### cjw ### : resultList["+idx+"].DPI300_HOS : " + resultList[idx].DPI300_HOS);
		    			
		    			if(resultList[0].DPI300_HOS == 714 || resultList[0].DPI300_HOS == 300){		    				
		    				param += "PGCD["+v.PGCD+"]TMP["+v.S018RFN+"] /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [300] /rimageydpi [300] /p /rusesystemfont";
		    			}else{
//		    				console.log("DPI(110)");
		    				param += "PGCD["+v.PGCD+"]TMP["+v.S018RFN+"] /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [110] /rimageydpi [110] /p /rusesystemfont";
		    			}
		    			
/* 		    			if(v.S018RFN == "91-STI-LSD" || 
		    			   v.S018RFN == "95-STI-NSR" || 
		    			   v.S018RFN == "95-STI_PSTS_C"){
		    				param += " /rusecxlib /rpdfexportapitype [1] /p";
		    			}else{
		    				param += " /rusecxlib /rpdfexportapitype [0] /p";	
		    			} */
		    			
		    			var mrd_file = "GENERAL-01.mrd";
			       		mrd_file = v.S018RFN+".mrd";
		    			//파일 명만 셋팅 , 확장자는 따로 셋팅
		    			
		    			downName = v.FILE_NM;
	
		    			resultList[idx].I_RFN = v.S018RFN;
		    			resultList[idx].I_DAT = v.F600DAT;
		    			resultList[idx].I_JNO = v.F600JNO;
		    			resultList[idx].I_HOS = $("#I_HOS").val();
		    			resultList[idx].I_ACD = "";
// 		    			resultList[idx].I_BDT = "0";
		    			resultList[idx].I_BDT = v.BDT;
		    			resultList[idx].P_GCD = v.P_GCD;
		    			//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		    			resultList[idx].I_LOGEFG = "FD";	//이벤트 종류: (FD 파일 다운로드)
		    			resultList[idx].I_LOGMNU = I_LOGMNU;
		    			resultList[idx].I_LOGMNM = I_LOGMNM;
		    			resultList[idx].rdServerSaveDir = rdServerSaveDir;
			        	resultList[idx].systemDownDir = (systemDownDir);
// 			        	resultList[idx].systemDownDir = (systemDownDir+"/"+file_ext.toUpperCase());
			        	resultList[idx].file_ext = file_ext;
			        	
			        	
			        	


			    		/**Report 전송 데이터 설정*/
			    		var f = document.reportForm;
			    		f.viewerUrl.value			=  viewerUrl;
			    		f.mrd_path.value			=  mrd_path+mrd_file;
			    		f.rdServerSaveDir.value		=  rdServerSaveDir;
			    		f.param.value				=  param;
			    		f.systemDownDir.value		=  systemDownDir;
			    		f.downFileName.value		=  downName;
			    		f.file_ext.value			= file_ext;
			    		/**Report 전송 데이터 설정*/
			    		
			    		console.log("### cjw ### recvImg.jsp : resultList[0].DPI300_HOS : 00 : "+resultList[0].DPI300_HOS);
			    		
		    			//reportPop.js 호출
						//alert(resultList[0].IMG_EACH_HOS);
						downloadZipReport(viewerUrl, mrd_path+mrd_file, param, rdServerSaveDir, systemDownDir, downName, file_ext,resultList[idx],resultList[0].DPI300_HOS, resultList[0].IMG_EACH_HOS);
	
			        },timeOutT);
	            });

	            
	    		//  zip, ActiveX 다운 가능한 내역 호출
				$.when( d1 )
			    .done(function (v1) {
		    		if(listCnt > 1){
	
		    			if(listCnt  > errorCnt){
							AcivValus = {};
		    				var ACIV_ZIP = $("#ACIV_ZIP").val();

		    				if(broswer.indexOf("ie") == -1){
						    	ACIV_ZIP = "ZIP";
		    				}
		    				
	// 	    				if(file_ext == "PDF"){
	// 	    					ACIV_ZIP = "ZIP";
	// 	    				}
		    				if(ACIV_ZIP == "ACTIVEX"){
								AcivValus["FileNm"] =  FileNm;
								AcivValus["systemDownDir"] = "//"+systemDownDir+"/";
// 								AcivValus["systemDownDir"] = "//"+systemDownDir+"/"+ file_ext.toUpperCase() +"/";
								AcivValus["file_ext"] = file_ext;
			    	    		callLoading(null,"off");
			    				//일자_병원명.zip
		    				}else{
								var param = "systemDownDir="+encodeURIComponent(systemDownDir);
// 								var param = "systemDownDir="+encodeURIComponent(systemDownDir+"/"+ file_ext.toUpperCase() );
								 param += "&file_name="+encodeURIComponent(FileNm);
								 param += "&zipDownDir="+zipDownDir+"&zipName="+zipName;
								 param += "&imgEachHos="+v1.imgEachHos;
						        window.setTimeout(function () {
									ajaxCall("/recvImgReportZipFile.do",param,false);
						        },1000);
								
		    				}
		    			}
		    	        window.setTimeout(function () {
		    	    		if(errorImg !=""){
		    	    			errorImg += "</br>";
		    		    		CallMessage("270","alert",[errorImg],downCall);
		    	    		}else{
		    		    		if(listCnt > 1){
		    	    				downCall();
		    		    		}
		    	    		}
		    	        }, setTime);
						//document.getElementById('file_down').src = "/comm_reportFileDownTestTotal.do?systemDownDir="+encodeURIComponent(systemDownDir+"/"+ file_ext.toUpperCase() )+"&file_name="+FileNm;
		    		}else{
				        window.setTimeout(function () {
				    		callLoading(null,"off");
				        }, setTime);
		    		}
			    });

			}else{
	    		callLoading(null,"off");
				var messge = $("#I_TERM_DIV option:selected").text()+" ";// 보고일자 , 접수일자
				messge += $("#I_FDT").val() + " ~ " + $("#I_TDT").val() +" ";
				if(donwflg == "1"){
					messge = "선택 ";
				}else if(donwflg == "3"){
					messge += "<br/>수신받지 않은";
				}else{
					messge += "전체 ";
				}
		    	CallMessage("291","alert",[messge]);//결과가 존재하지 않습니다.
			}
		}
	}
	var AcivValus = {};
	function downCall(){
		var file_ext =$("#I_RPT_DW").val();
		var ACIV_ZIP = $("#ACIV_ZIP").val();

		if(broswer.indexOf("ie") == -1){
	    	ACIV_ZIP = "ZIP";
		}
		
// 		if(file_ext == "PDF"){
// 			ACIV_ZIP = "ZIP";
// 		}
		if(ACIV_ZIP == "ACTIVEX"){
			var labelNm,strUrl;
			strUrl = "/recvImgDown.do";
			labelNm = "일괄 다운로드";
			if(listCnt  > errorCnt){
				var f = document.dataForm;
				var win = window.open('', 'allDown', 'width=601, height=475, scrollbars=no, location=no'); 
				f.action = strUrl;
				f.target = "allDown";
				f.fileList.value = FileNm;
				f.systemDownDir.value = AcivValus["systemDownDir"];
				f.file_ext.value = file_ext;
				f.submit();
				win.focus();
			}
		}
	}
	
	function setGrid(){
		 
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    setGridBar(gridView, false,"state");
	    var fields=[
		    	{fieldName:"F008COR"}        // 회사코드
		    	, {fieldName:"F008HOS"}        // 병원코드
		    	, {fieldName:"JDT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	
		    	, {fieldName:"BDT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	
		    	, {fieldName:"F008DAT"}        // 접수일자
		    	, {fieldName:"F008JNO", dataType: "number"}	        // 접수번호
		    	, {fieldName:"F008GCD"}        // 검사코드
		    	, {fieldName:"F008SEQ"}        // 일련번호
		    	, {fieldName:"F008HID"}        // 검체번호
		    	, {fieldName:"F008CHT"}        // 챠트번호
		    	, {fieldName:"F008NAM"}        // 환자명
		    	, {fieldName:"F008AGE"}        // 나이
		    	, {fieldName:"F008SEX"}        // 성별
		    	, {fieldName:"F008FKN"}        // 검사명　
		    	, {fieldName:"F008RT1"}        // 결과　　
		    	, {fieldName:"F008LEN"}        // 결과길이
		    	, {fieldName:"F008PAN"}        // 결과판정
		    	, {fieldName:"F008RNO"}        // REMARK코드
		    	, {fieldName:"F008BDT"}        // 보고일자
		    	, {fieldName:"F008TIM"}        // 보고시간
		    	, {fieldName:"F008FHI"}       // 참고치이력
		    	, {fieldName:"F008LNG"}        // 참고치언어
		    	, {fieldName:"F008BCD"}        // 보험코드
		    	, {fieldName:"F008FL1"}       // 결과형태
		    	, {fieldName:"F008FL2"}       // 다운여부
		    	, {fieldName:"F008FL3"}       // 병원검체코드
		    	, {fieldName:"F008FL4"}       // 타병원검사코드
		    	, {fieldName:"F008FL5"}       // PROFILE
		    	, {fieldName:"F008IMG"}        // 이미지여부
		    	, {fieldName:"F008JN1"}        // 주민번호１
		    	, {fieldName:"F008JN2"}        // 주민번호２
		    	, {fieldName:"F008CDT"}        // 처방일자　
		    	, {fieldName:"F008INC"}        // 진료구분
		    	, {fieldName:"F008LOV"}        // 참고치하한
		    	, {fieldName:"F008HIV"}        // 참고치상한
		    	, {fieldName:"F008UNT"}        // 단위
		    	, {fieldName:"F008CNO"}        // 처방번호
		    	, {fieldName:"F008FL6"}       // 암여부
		    	, {fieldName:"F008FL7"}       // FL8
		    	, {fieldName:"F008FL8"}       // FL9
		    	, {fieldName:"F008FL9"}       // 기타
		];
	dataProvider.setFields(fields);
	
	var columns=[
		,{name:"BDT",		fieldName:"BDT",	header:{text:"보고일자"},		width:80,  styles:{datetimeFormat:"yyyy-MM-dd", textAlignment:"center"}}
		,{name:"JDT",		fieldName:"JDT",	header:{text:"접수일자"},		width:80,  styles:{datetimeFormat:"yyyy-MM-dd", textAlignment:"center"}}
		,{name:"F008JNO",	fieldName:"F008JNO",	header:{text:"접수번호"},		width:80,  styles: {textAlignment: "center"} }
		,{name:"F008NA",	fieldName:"F008NAM",	header:{text:"수진자명"},		width:80}
		,{name:"F008CHT",	fieldName:"F008CHT",	header:{text:"차트번호"},		width:120}
		,{name:"F008AGE",	fieldName:"F008AGE",	header:{text:"나이"},			width:80,	styles:{textAlignment:"far"}}
		,{name:"F008SEX",	fieldName:"F008SEX",	header:{text:"성별"},			width:80,	styles:{textAlignment:"center" }}
		,{name:"F008FKN",	fieldName:"F008FKN",	header:{text:"검사명"},		width:240}
		,{name:"F008FL4",	fieldName:"F008FL4",	header:{text:"검사코드"},		width:80}
		//,{name:"F008GCD",	fieldName:"F008GCD",	header:{text:"검사코드"},		width:100}
		,{name:"F008HID",	fieldName:"F008HID",	header:{text:"검체번호"},		width:120}
		,{name:"F008FL2",	fieldName:"F008FL2",	header:{text:"수신 여부"},		width:70,	styles:{textAlignment:"center"}}
	];

	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);

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
		if(url == "/recvImgDown.do"){

			iframe.recvImgDown(gridValus);
		}
	}
	
	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(data[0] == "/hospSearchSList.do"){
			$('#I_FNM').val(data[1].F120FNM);
			$('#I_HOS').val(data[1].F120PCD);
	    }
	}
	
	/* 팝업 호출 및 종료 */


var donwflg = "";
	/*다운*/
	function downRow(flag)
	{
		donwflg = flag;
		var jsonRow = [];
		var url;
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		if(flag == "1"){
	        var dataRows = gridView.getCheckedRows();
			url  = "/getGroup2.do";
			if(dataRows != ""){ 
				var MaxCnt = 200;
				if(dataRows.length < MaxCnt ){
					/**
					 * 파라미터
					 * I_PARAM: 파라미터
					 * 구분자: | /  내용: 회사구분(NML)3자+접수일자8자+접수번호5자(모자란 자리수는 왼쪽으로 00채움 즉, 521 => 00521) + 검사코드 5자
					 * 예: I_PARAM=|NML201705160555641377|NML201705160555641392| 
					 */
					 var strParam = "";
					$.each(dataRows, function(i, e) {
						var gridObj =  dataProvider.getJsonRow(e);
						var gcd =  gridObj["F008GCD"];
						if(gcd.length>5){
							gcd = gcd.substring(0,5);
						}
						strParam += "|" + gridObj["F008COR"] + gridObj["F008DAT"] + lpad( gridObj["F008JNO"] ,5,0) + lpad( gcd ,5,0)
					});
					callLoading(null,"on");
					strParam += "|";
	// 	 			var param = "I_LOGMNU=메뉴코드&I_LOGMNM=메뉴명&I_PARAM=|NML201812314854872245|NML201812314854872245||NML201812314854872245|&I_LOGEFG=FD&I_IMGSAVE=S";
	// 	 			var param = "I_LOGMNU=RECVIMG&I_LOGMNM=이미지결과수신&I_PARAM="+strParam+"&I_LOGEFG=FD&I_IMGSAVE=S";
					
					var param = $("#searchForm").serializeArray();
	    			//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
					param.push({ name: "I_LOGEFG",value : "FD"});	//이벤트 종류: (FD 파일 다운로드)
					param.push({ name: "I_PARAM",value :strParam});
					param.push({ name: "I_IMGSAVE",value : "S"});
					ajaxCall(url,param,false); // 수정
				}else{
					CallMessage("206","alert",[MaxCnt+""]);	//{0}개 이상 선택할수 없습니다.
				}
			}
		}else{
			if(!searchValidation()) { return;}
			var messge = $("#I_TERM_DIV option:selected").text()+" ";// 보고일자 , 접수일자
			messge += $("#I_FDT").val() + " ~ " + $("#I_TDT").val() +"<br>";
			
			$("#I_RECV_YN").val("");
			if(flag == "3"){
				$("#I_RECV_YN").val("X");
				messge += "수신받지 않은 결과";
			}else{
				messge += "전체 ";
			}
			CallMessage("285","confirm",[messge],fnFileDown);	//{0} 다운 하시겠습니까
		}
	}
	function fnFileDown()
	{
		callLoading(null,"on");
		var formData = $("#searchForm").serializeArray();
		//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		formData.push({ name: "I_LOGEFG",value : "FD"});	//이벤트 종류: (FD 파일 다운로드)
		formData.push({ name: "I_IMGSAVE",value : "S"});
		url  = "/getImgGroup.do";
		ajaxCall(url,formData,false); // 수정
			
	}
	
	/*다운*/
	/* enter시 조회 */
	function enterSearch(){
		if(event.keyCode == 13){
			dataResult();
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
		}
	}
	
	</script>
	
</head>
<body >
<div class="content_inner">
	<form method="post" action="" name="reportForm">
		<input type="hidden" name="viewerUrl" />
		<input type="hidden" name="mrd_path" />
		<input type="hidden" name="rdServerSaveDir" />
		<input type="hidden" name="param" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="downFileName" />
		<input type="hidden" name="recvObj" />
		<input type="hidden" name="rdFilePathName" />
		<input type="hidden" name="downfile_name" />
		<input type="hidden" name="file_name" />
		<input type="hidden" name="file_ext" />
	</form>
	<form method="post" action="" name="dataForm">
		<input type="hidden" name="fileList" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="file_ext" />
	</form>
	<div class="container-fluid">
		<div class="con_wrap col-md-12 srch_wrap">
			<div class="title_area open">
				<h3 class="tit_h3">검색영역</h3>
				<a href="#" class="btn_open">검색영역 접기</a>
			</div>
			<!-- con_section 검색영역 -->
			<div class="con_section row">
				<form id="searchForm" name="searchForm">
					<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
					<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
					<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
					<div class="srch_box">
						<div class="srch_box_inner">
							<div class="srch_item" style="min-width: 470px;">
								<label for="fr_date" class="label_basic">기간</label>
								<div class="select_area">
									<select id="I_TERM_DIV" name="I_TERM_DIV" class="form-control"></select>
								</div>
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
							<div class="srch_item srch_item_v4">
								<label for="" class="label_basic">수신여부</label>
								<div class="select_area">
									<select id="I_RECV" name="I_RECV" class="form-control"></select>
									<input type="hidden" id="I_RECV_YN" name="I_RECV_YN">
								</div>
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item srch_item_v5 hidden_srch">
								<label for="" class="label_basic">병원</label>
								<%-- <input id="I_HOS" name="I_HOS" class="srch_put"  type="hidden" value="<%=ss_hos%>" /> --%>
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>"  readonly="readonly" onClick="javascript:openPopup(1)" maxlength="5" onkeydown="javasscript:enterSearch()" class="srch_put numberOnly"/>
	                             <input type="text"  class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup(1)"/>
								<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
							</div>
						</div>
					    
						<div class="btn_srch_box">
							<button type="button" id="SearchBtn" class="btn_srch">조회</button>
						</div> 
					</div>
				</form>
			</div>
		</div>
	</div> 
	<!-- 검색영역 end -->         
	
	<div class="container-fluid">
		<div class="con_wrap col-md-12 col-sm-12">
			<div class="title_area">
				<h3 class="tit_h3">이미지 결과 수신</h3>
				<div class="tit_btn_area">
					<div class="select_area">
						<select id="I_RPT_DW" name="I_RPT_DW" class="form-control"></select>
					</div>
					<div class="select_area display_off" id="Acti_ZipDiv">
						<select id="ACIV_ZIP" name="ACIV_ZIP" class="form-control"></select>
<!-- 						<input type="text"  readonly="readonly"> -->
					</div>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:downRow(1)">선택받기</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:downRow(2)">전체받기</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:downRow(3)">수신 받지 않은 결과 받기</button>
				</div>
			</div>
			<div class="con_section overflow-scr">
				<div class="tbl_type">
					<div id="realgrid" class="realgridH"></div>
				</div>
			</div>
		</div>
	</div>
</div>
     <%-- 첨부파일 다운로드 --%> 
	<iframe id="file_down" name="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
	<%-- 첨부파일 다운로드 --%> 
	<!-- content_inner -->       	
</body>
</html>


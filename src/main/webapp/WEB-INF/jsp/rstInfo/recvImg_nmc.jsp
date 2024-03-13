<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.net.URLDecoder"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>이미지 결과 수신</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
    <%@ include file="/inc/reportPop.inc"%>
    <%
    	session.setAttribute("UID",	"NMC");	//로그인아이디
 		session.setAttribute("IP",		"");		//로그인 IP
 		session.setAttribute("COR",		"NML");		//COR코드

		//String parm_fdt = request.getParameter("parm_FDT"); // 보고일자(시작일)
		//String parm_tdt = request.getParameter("parm_TDT"); // 보고일자(종료일)
		//String parm_fdt = URLDecoder.decode(request.getParameter("parm_fdt")); // 보고일자(시작일)
		//String parm_tdt = URLDecoder.decode(request.getParameter("parm_tdt")); // 보고일자(종료일)
		
		 
		String parm_fdt = request.getParameter("parm_fdt"); // 보고일자(시작일)
		String parm_tdt = request.getParameter("parm_tdt"); // 보고일자(종료일)
		String parm_hos = request.getParameter("parm_hos"); // 보고일자(종료일)
		parm_fdt = parm_fdt.substring(0, 4)+"-"+parm_fdt.substring(4, 6)+"-"+parm_fdt.substring(6, 8);
		parm_tdt = parm_tdt.substring(0, 4)+"-"+parm_tdt.substring(4, 6)+"-"+parm_tdt.substring(6, 8);
		
		//https://trms.seegenemedical.com/recvImg_nmc.do?parm_fdt=20180602&parm_tdt=20180602&parm_hos=16360
		//http://localhost:8090/recvImg_nmc.do?parm_fdt=20190101&parm_tdt=20190103&parm_hos=22015
		//http://localhost:8090/recvImg_nmc.do?parm_fdt=20190624&parm_tdt=20190701&parm_hos=16360
		/* 
		String parm_fdt = "2019-06-24";
		String parm_tdt = "2019-07-01";
		String parm_hos = "16360";
		 */
		//System.out.println("## parm_fdt : "+parm_fdt);
		//System.out.println("## parm_tdt : "+parm_tdt);
    %>
	<script>

    var pscd = "";
	var I_EKD = "INIT_PW";	//EMAIL_CLS	이메일 종류 	INIT_PW	비밀번호 초기화
	var gridView;
	var dataProvider;
    var gridValues = [];
    var gridLabels = [];
    var broswer;
	
	$(document).ready( function(){
		
		
		
		var nmc_parm_fdt = "<%=parm_fdt%>";
		var nmc_parm_tdt = "<%=parm_tdt%>";
		
		
	
		broswer = checkBroswer();
		if(broswer.indexOf("ie")>-1){
			$("#Acti_ZipDiv").removeClass("display_off");
		}
		
	    $('#I_FNM').change(function(){
// 	    	$('#I_HOS').val('');
	    });
		if($('#I_ECF').val() == "E"){
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
			//maxDate: $('#I_TDT').val(), //cjw
			maxDate: nmc_parm_fdt, //cjw			
			onSelect: function(selectDate){
				$('#I_TDT').datepicker('option', {
					minDate: selectDate,
				});
			}
		});	
		$("#I_TDT").datepicker({
			dateFormat: 'yy-mm-dd',
			//minDate: $('#I_FDT').val(), //cjw
			minDate: nmc_parm_tdt,
			onSelect : function(selectDate){
				$('#I_FDT').datepicker('option', {
					maxDate: selectDate,
				});
			}		
		});
		$("#I_FDT").datepicker('setDate',new Date);
        $("#I_TDT").datepicker('setDate',nmc_parm_tdt);
//		$("#I_FDT").datepicker('setDate',"2018-12-07");
//		$("#I_TDT").datepicker('setDate',"2018-12-07");
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
	
		dataResult(); // 조회
		fnFileDown(2); // 전체다운
		
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

 		if($('#I_HOS').val() == ""){
 			//CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
 			return false;
 		}
		if(!checkdate($("#I_FDT"))||!checkdate($("#I_TDT"))){
			//CallMessage("273","alert","");	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
			return;
		}
 	 	if (interval/day > 7){
			//267
   			//CallMessage("267","alert");		//{0}에 실패하였습니다.
			return false;
		}
		if(sDate > eDate){
			//CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
			return;
		}
		
 		return true;
		
	}
	
	function changeDT(DayId){
		var today= new Date();
		
		if(DayId == "1"){
			today.setDate(today.getDate() - 1);
		}
		if(DayId == "2"){
			today.setDate(today.getDate() - 3);
		}
		if(DayId == "3"){
			today.setDate(today.getDate() - 30);
		}
		if(DayId == "4"){
			today.setDate(today.getDate() - 90);
		}
		$("#I_FDT").datepicker('setDate', today);
		$("#I_TDT").datepicker('setDate', new Date());
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
		
		console.log("systemDownDir : "+systemDownDir);
		console.log("zipDownDir : "+zipDownDir);
		
		var zipName = "<%=yyyy%><%=mm%><%=dd%>_"+$("#I_FNM").val()+".zip";
		var file_ext =$("#I_RPT_DW").val();
		
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/getCommCode.do"){
            $.each(resultList,function(k,v){
                gridValues.push(v.VALUE);
                gridLabels.push(v.LABEL);
            });
   		 	setGrid();
		}
		if(strUrl == "/recvImgList.do"){
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
		if(strUrl == "/ImgLogSave.do"){
			// 로그 저장 
        	TotalCnt = TotalCnt-1;
        	if(listCnt==1){
        	
	    		callLoading(null,"off");
	    		if(errorImg !=""){
	    			errorImg += "</br>";
		    		//CallMessage("270","alert",[errorImg]);
	    		}
			}
    		if(TotalCnt == 0){
    			d1.resolve();
    		}
		}
		if(strUrl == "/recvImgReportFileDown.do")
		{
        	TotalCnt = TotalCnt-1;
//         	def.resolve();
    		if(TotalCnt == 0){
    			d1.resolve();
    		}
		}
		if(strUrl == "/recvImgReportZipFile.do")
		{
			if(listCnt  > errorCnt){
				//일자_병원명.zip
	        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+zipDownDir+"/"+zipName+"&file_name="+encodeURIComponent(zipName);
			}
	        window.setTimeout(function () {
	    		callLoading(null,"off");
	    		
	    		if(errorImg !=""){
	    			errorImg += "</br>";
		    		//CallMessage("270","alert",[errorImg]);
	    		}
	        }, 200);
		}
		if(strUrl == "/getGroup.do"||strUrl == "/getImgGroup.do")
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
	            $.each(resultList,function(idx,v){
	            	
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
//		    			param += "PGCD["+v.PGCD+"]TMP["+v.S018RFN+"] /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [110]  /rimageydpi [110] /p /rusesystemfont";
		    			
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
		    			resultList[idx].I_BDT = "0";
		    			resultList[idx].P_GCD = v.P_GCD;
		    			resultList[idx].I_LOGEFG = "FD";	//이벤트 종류: (FD 파일 다운로드)
		    			resultList[idx].I_LOGMNU = I_LOGMNU;
		    			resultList[idx].I_LOGMNM = I_LOGMNM;
		    			resultList[idx].rdServerSaveDir = rdServerSaveDir;
			        	resultList[idx].systemDownDir = (systemDownDir+"/"+file_ext.toUpperCase());
			        	resultList[idx].file_ext = file_ext;
		    			
	
	// 	    			FileNm += downName+"."+file_ext+";";
		    			//reportPop.js 호출
						//downloadZipReport(viewerUrl, mrd_path+mrd_file, param, rdServerSaveDir, systemDownDir, downName, file_ext,resultList[idx]);
			        	downloadZipReport(viewerUrl, mrd_path+mrd_file, param, rdServerSaveDir, systemDownDir, downName, file_ext,resultList[idx],resultList[0].DPI300_HOS, resultList[0].IMG_EACH_HOS);
	
			        },100*idx);
	            });
	            
				$.when( d1 )
			    .done(function (v1) {
		    		/* if(listCnt > 1){
	
		    			if(listCnt  > errorCnt){
							AcivValus ={};
		    				var ACIV_ZIP = $("#ACIV_ZIP").val();
		    				
	// 	    				if(file_ext == "PDF"){
	// 	    					ACIV_ZIP = "ZIP";
	// 	    				}
		    				if(ACIV_ZIP == "ACTIVEX"){
								AcivValus["FileNm"] =  FileNm;
								AcivValus["systemDownDir"] = ("//"+systemDownDir+"/"+ file_ext.toUpperCase() +"/");
								AcivValus["file_ext"] = file_ext;
			    				//일자_병원명.zip
		    				}else{
								var param = "systemDownDir="+encodeURIComponent(systemDownDir+"/"+ file_ext.toUpperCase() );
								 param += "&file_name="+encodeURIComponent(FileNm);
								 param += "&zipDownDir="+zipDownDir+"&zipName="+zipName;
								
								ajaxCall("/recvImgReportZipFile.do",param,false);
		    				}
		    			}
		    	        window.setTimeout(function () {
		    	    		callLoading(null,"off");
		    	    		if(errorImg !=""){
		    	    			errorImg += "</br>";
		    		    		CallMessage("270","alert",[errorImg],downCall);
		    	    		}else{
		    		    		if(listCnt > 1){
		    	    				downCall();
		    		    		}
		    	    		}
		    	        }, 200);
						//document.getElementById('file_down').src = "/comm_reportFileDownTestTotal.do?systemDownDir="+encodeURIComponent(systemDownDir+"/"+ file_ext.toUpperCase() )+"&file_name="+FileNm;
		    		}else{
				        window.setTimeout(function () {
				    		callLoading(null,"off");
				        }, 200);
		    		} */
		    		
			    	//window.open("about:blank","_self").close();
			    });

				
				
			}/* else{
	    		callLoading(null,"off");
		    	CallMessage("286","alert");//결과가 존재하지 않습니다.
			} */
		}
	}
	/* var AcivValus =  new Map();
	 var AcivValus =  {};
	function downCall(){
		var file_ext =$("#I_RPT_DW").val();
		var ACIV_ZIP = $("#ACIV_ZIP").val();
		
// 		if(file_ext == "PDF"){
// 			ACIV_ZIP = "ZIP";
// 		}
		if(ACIV_ZIP == "ACTIVEX"){
			var labelNm,strUrl;
			strUrl = "/recvImgDown.do";
// 			strUrl = "/recvImgDown.do?fileList="+encodeURIComponent(FileNm)+"&systemDownDir="+encodeURIComponent(AcivValus["systemDownDir"])+"&file_ext="+file_ext;
			labelNm = "일괄 다운로드";
			if(listCnt  > errorCnt){

				var f = document.dataForm;
// 				var realCnt = f.realCnt.value;
				//검색안했음 검색먼저 하라는 알림추가
// 				if(realCnt !=0) {
					var win = window.open('', 'allDown', 'width=601, height=475, scrollbars=no, location=no'); 
					f.action = strUrl;
					f.target = "allDown";
					f.fileList.value = FileNm;
					f.systemDownDir.value = AcivValus["systemDownDir"];
					f.file_ext.value = file_ext;
					f.submit();
					win.focus();
					
// 				$('#pageiframe', parent.document).height(490);
// 				$('#pageiframe', parent.document).width(610);
// 				fnOpenPopup(strUrl,labelNm,AcivValus,callFunPopup);
			}
		}
	} */
	
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
		,{name:"F008JNO",	fieldName:"F008JNO",	header:{text:"접수번호"},		width:80,  styles: {textAlignment: "far"} }
		,{name:"F008NA",	fieldName:"F008NAM",	header:{text:"수진자명"},		width:80}
		,{name:"F008CHT",	fieldName:"F008CHT",	header:{text:"차트번호"},		width:100}
		,{name:"F008AGE",	fieldName:"F008AGE",	header:{text:"나이"},			width:100,	styles:{textAlignment:"far"}}
		,{name:"F008SEX",	fieldName:"F008SEX",	header:{text:"성별"},			width:100,	styles:{textAlignment:"center" }}
		,{name:"F008FKN",	fieldName:"F008FKN",	header:{text:"검사명"},		width:200}
		,{name:"F008FL4",	fieldName:"F008FL4",	header:{text:"검사코드"},		width:100}
		//,{name:"F008GCD",	fieldName:"F008GCD",	header:{text:"검사코드"},		width:100}
		,{name:"F008HID",	fieldName:"F008HID",	header:{text:"검체번호"},		width:100}
		,{name:"F008FL2",	fieldName:"F008FL2",	header:{text:"수신 여부"},		width:80,	styles:{textAlignment:"center"}}
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



	/*다운*/
	/* function downRow(flag)
	{
		fnFileDown(2);
	} */
	function fnFileDown()
	{
		callLoading(null,"on");
		var formData = $("#searchForm").serializeArray();
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
							<div class="srch_item srch_item_v3">
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
								
								<label for="" class="label_basic">수신여부</label>
								<div class="select_area">
									<select id="I_RECV" name="I_RECV" class="form-control"></select>
									<input type="hidden" id="I_RECV_YN" name="I_RECV_YN" value="X">
								</div>
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item hidden_srch">
								<label for="" class="label_basic">병원</label>
								<%-- <input id="I_HOS" name="I_HOS" class="srch_put"  type="hidden" value="<%=ss_hos%>" /> --%>
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=parm_hos %>" style="width: 150px;" maxlength="5" onkeydown="javasscript:enterSearch()" class="srch_put numberOnly"/>
	                             <input type="text" style="width:203px" class="srch_put" id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup(1)"/>
								<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
	                             
	                             <button type="button" class="btn btn-red btn-sm" title="검색" onClick="javascript:openPopup(1)">
	                             	<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
	                             </button>
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
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:downRow(1)">선택다운</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:downRow(2)">전체다운</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:downRow(3)">수신받지 않은 결과 다운</button>
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


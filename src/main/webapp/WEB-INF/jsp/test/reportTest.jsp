<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>권한 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<!--
	<link rel="stylesheet" href="/css/commonCss.css"> 
    <script type="text/javascript" src="/plugins/jquery/js/jquery.js"></script>
    <script type="text/javascript" src="/js/commonJs.js"></script>
	-->
    <%@ include file="/inc/common.inc"%>

    <%@ include file="/inc/reportPop.inc"%>
    <!-- rdUrl을 자바에서 받아서 사용하는 곳에서만 사용 -->
    <script type="text/javascript" src="<%=rdUrl%>/ReportingServer/html5/js/crownix-viewer.min.js"></script>
	<script type="text/javascript" src="<%=rdUrl%>/ReportingServer/html5/js/invoker/crownix-invoker.min.js"></script>
    <script type="text/javascript" src="/js/reportPop.js"></script>
    
    
	<script>
	
		function viewPrint(){
			//승인 안되면 출력 못함
			var w = window.screen.width ;
			var h = window.screen.height ;
			
			//reportPop.inc의 셋팅값 받기
			var viewerUrl = "<%=viewerUrl%>";
			<%-- var mrd_path = "<%=systemUrl%>/mrd/GENERAL-01.mrd"; --%>
			var mrd_path = "<%=systemUrl%>/mrd/77-BMA8-TMP-02.mrd";
			<%-- var mrd_path = "<%=systemUrl%>/mrd/95-STI-PSTR.mrd"; --%>
			var rdServerSaveDir = "<%=rdSaveDir%>";
			var rd_param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR[NML]UID[KNNSSS]JDAT[20180301]JNO[27604]GCD[ ]RFN[BMA8ARA ]UIP[ ]WTR[A] /rusecxlib /rpdfexportapitype [0] /p";
			var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
			//alert(mrd_path);
			
			//파일 명만 셋팅 , 확장자는 따로 셋팅
			var downFileName = "테스트 Crownix 일반 리포트";
			var param="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path+"&rdServerSaveDir="+rdServerSaveDir+"&param="+rd_param+"&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);
			
			showReportViewDialog("/crownixViewer.do?"+param, w, h, printcallback);
			
			
		}
		
		function viewPrint2(){
			//승인 안되면 출력 못함
			var w = window.screen.width ;
			var h = window.screen.height ;
			
			//reportPop.inc의 셋팅값 받기
			var viewerUrl = "<%=viewerUrl%>";
			var mrd_path = "<%=systemUrl%>/mrd/test01.mrd";
			var rdServerSaveDir = "<%=rdSaveDir%>";
			var rd_param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR['NML']UID['L01118'] /p";
			var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
			//alert(systemDownDir);
			
			//파일 명만 셋팅 , 확장자는 따로 셋팅
			var downFileName = "테스트 Crownix 리포트 test";
			var param="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path+"&rdServerSaveDir="+rdServerSaveDir+"&param="+rd_param+"&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);
			
			showReportViewDialog("/crownixViewer.do?"+param, w, h, printcallback);
			
			
		}
		
		function printcallback(){
			alert("print callback!");
		}
		
		function downReport(file_ext){
			//reportPop.inc의 셋팅값 받기
			var viewerUrl = "<%=viewerUrl%>";
			<%-- var mrd_path = "<%=systemUrl%>/mrd/general_test.mrd"; --%>
			var mrd_path = "<%=systemUrl%>/mrd/77-BMA5-TMP-01.mrd";
			var rdServerSaveDir = "<%=rdSaveDir%>";
			
			<%-- var param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR['NML']DAT['20170109']JNO['91001']GCD['|05520|11001|11002|11004|11005|11006|11007|11008|11009|11011|11012|11013|11014|11015|11052|11310|'] /rusecxlib /rpdfexportapitype [0]  /p"; --%>
			var param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR['NML']UID['KNNSSS']JDAT['20180301']JNO['27604']GCD['|05520|11001|11002|11004|11005|11006|11007|11008|11009|11011|11012|11013|11014|11015|11052|11310|']RFN['BMA8ARA '] /rusecxlib /rpdfexportapitype [0]  /p";
			//시스템 저장 dir
			var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
			
			//파일 명만 셋팅 , 확장자는 따로 셋팅
			var downFileName = "테스트 Crownix 일반 리포트";
			
			//alert(systemDownDir);
			
			downloadReport(viewerUrl, mrd_path, param, rdServerSaveDir, systemDownDir, downFileName, file_ext);
			
		}
		
		function downReport2(file_ext){
			//reportPop.inc의 셋팅값 받기
			var viewerUrl = "<%=viewerUrl%>";
			
			var mrd_path = "<%=systemUrl%>/mrd/test01.mrd";
			var rdServerSaveDir = "<%=rdSaveDir%>";
			var param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR['NML']UID['L01118'] /rpdfexportapitype [0] /p";
			
			//시스템 저장 dir
			var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
			
			//파일 명만 셋팅 , 확장자는 따로 셋팅
			var downFileName = "테스트 Crownix 리포트 test";
			
			//alert(systemDownDir);
			
			downloadReport(viewerUrl, mrd_path, param, rdServerSaveDir, systemDownDir, downFileName, file_ext);
			
		}
		
		
		function downReport3(file_ext){
			//reportPop.inc의 셋팅값 받기
			var viewerUrl = "<%=viewerUrl%>";
			
			var mrd_path = "<%=systemUrl%>/mrd/general_test.mrd";
			var rdServerSaveDir = "<%=rdSaveDir%>";
			var param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rv COR['NML']DAT['20170109']JNO['91001']GCD['|05520|11001|11002|11004|11005|11006|11007|11008|11009|11011|11012|11013|11014|11015|11052|11310|'] /rusecxlib /rpdfexportapitype [0] /p";
			
			//시스템 저장 dir
			var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
			
			//파일 명만 셋팅 , 확장자는 따로 셋팅
			var downFileName = "테스트 Crownix 일반 리포트";
			
			//alert(systemDownDir);
			//pdf 다운 및 jpg 다운은 pdf에서 jpg로 변환
			downloadReportPdfJpg(viewerUrl, mrd_path, param, rdServerSaveDir, systemDownDir, downFileName, file_ext);
			
		}
	
	</script>
</head>
<body>
	<form id="searchForm" name="searchForm">
	
	<div class="content_inner">
         <div class="container-fluid">
             <div class="con_wrap col-md-6 col-sm-12">
                 <div class="title_area">
                     <h3 class="tit_h3">리포트 테스트</h3>
                     <div class="tit_btn_area">
                         <button type="button" class="btn btn-default btn-sm" onClick="javascript:downReport('jpg')">이미지 다운로드</button>
                         <button type="button" class="btn btn-default btn-sm" onClick="javascript:downReport('pdf')">PDF 다운로드</button>
                         <button type="button" class="btn btn-default btn-sm" onClick="javascript:viewPrint()">출력</button>
                     </div>
                 </div>
                 <div class="con_section">
                     <div class="tbl_type">
                     	<div id="realgrid2" class="realgridH"></div>
                     </div>
                 </div>
             </div>
         </div>
                            
     </div>    
     <%-- 첨부파일 다운로드 --%> 
	<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
	<%-- 첨부파일 다운로드 --%> 
     <!-- content_inner -->
     </form>  
</body>
</html>



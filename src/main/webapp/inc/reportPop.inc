<%@page import="java.util.HashMap"%>
<%@ page import="kr.co.softtrain.common.web.util.CommonController"%>
<%@ page import="java.util.Calendar"%>
<%
	Calendar cal = Calendar.getInstance();
	
	String yyyy = cal.get(Calendar.YEAR) +"";
	String mm = (cal.get(Calendar.MONTH) + 1) +"";
	String dd = cal.get(Calendar.DATE) +"";
	
	if(mm.length()==1){
		mm ="0"+mm;
	}
	
	if(dd.length()==1){
		dd ="0"+dd;
	}
	
	HashMap<String, String> cronixInfo = new HashMap<String, String>();
	CommonController cct = new CommonController();
	cronixInfo = cct.getCrownixInfo();
	
	String serverName = request.getServerName();
	
	if("localhost".equals(serverName)){
		serverName = cct.getLocalServerIp();
	}
	
	String systemUrl = request.getScheme() + "://" + serverName + ":" + request.getServerPort();
	
	String rdUrl = cronixInfo.get("rdUrl");
	String viewerUrl = cronixInfo.get("viewerUrl");
	String rdAgentUrl = cronixInfo.get("rdAgentUrl");
	String rdUser = cronixInfo.get("rdUser");
	String rdUserPw = cronixInfo.get("rdUserPw");
	String rdSaveDir = cronixInfo.get("rdSaveDir");
	String rdSysSaveDir = cronixInfo.get("rdSysSaveDir");
	String trmsSystemUrl = cronixInfo.get("trmsSystemUrl");
%>
   
		<!--[if !IE]><!-->
		<script src="<%=rdUrl%>/ReportingServer/html5/js/crownix-viewer.min.js"></script>
		<!--<![endif]-->	
		<!--[if gt IE 8]>
		<script src="<%=rdUrl%>/ReportingServer/html5/js/crownix-viewer.min.js"></script>
		<!--<![endif]-->
		<!--[if lte IE 8]>
		<script src="<%=rdUrl%>/ReportingServer/html5/js/crownix-viewer-ie8.min.js"></script>
		<!--<![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=rdUrl%>/ReportingServer/html5/css/crownix-viewer.min.css">
	<script type="text/javascript" src="<%=rdUrl%>/ReportingServer/html5/js/invoker/crownix-invoker.min.js"></script>		
    <script type="text/javascript" src="/js/reportPop.js?var=2019040114"></script>
	
	<script>
		var viewerUrl = "<%=viewerUrl%>";
		var rdServerSaveDir = "<%=rdSaveDir%>";
		var trmsSystemUrl = "<%=trmsSystemUrl%>";
	</script>
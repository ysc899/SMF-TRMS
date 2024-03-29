<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.softtrain.custom.util.Utils"%>
<%	

	/* //전체 쿠키 삭제하기
	Cookie[] cookies = request.getCookies() ;
	 
	if(cookies != null){
	    for(int i=0; i < cookies.length; i++){
	         
	        // 쿠키의 유효시간을 0으로 설정하여 만료시킨다
	        cookies[i].setMaxAge(0) ;
	         
	        // 응답 헤더에 추가한다
	        response.addCookie(cookies[i]) ;
	    }
	} */
	
	// 특정 쿠키만 삭제하기
    Cookie kc = new Cookie("memberNo", null) ;
    kc.setMaxAge(0) ;
    response.addCookie(kc) ;

	//parameter 받기
	String rdViewerUrl = request.getParameter("viewerUrl");
	String mrd_path = request.getParameter("mrd_path");
	String rdServerSaveDir = request.getParameter("rdServerSaveDir");
	String reportParam = request.getParameter("param");
	String systemDownDir = request.getParameter("systemDownDir");
	String downFileName = request.getParameter("downFileName");
	String dpi300_hos_gubun = request.getParameter("dpi300_hos_gubun");
	String imgEachHos = request.getParameter("imgEachHos");
	String crownixVeiwer_print_YN = request.getParameter("crownixVeiwer_print_YN");
	
	List<Map<String, Object>>  PrintParamList = new ArrayList();
  	Map<String, Object> param = new HashMap<String, Object>();
  	
	String imgObj = request.getParameter("imgObj");
// 	System.out.println("imgObj   == "+imgObj);
  	if(null != imgObj){
		if(!"".equals(imgObj)){
			PrintParamList =  Utils.jsonList(request.getParameter("imgObj").toString());	// 데이터 Parameter 상태로 변경
			param  = PrintParamList.get(0);
// 	System.out.println("param   == "+param);
		}
  	}
%>

<!DOCTYPE html>
<html style="margin:0;height:100%">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta charset="utf-8">
		<title>Crownix HTML5 Viewer</title>
		
		<%@ include file="/inc/common.inc"%>
		<%@ include file="/inc/reportPop.inc"%>
	</head>
	
	<body style="margin:0;height:100%">
		<div id="crownix-viewer" style="position:absolute;width:100%;height:100%"></div>
		<script>
		//현재 open mdi 
		var curr_menu_cd = "";

			window.onload = function(){

				var mrd_path = "<%=mrd_path%>";
				var param = "<%=reportParam%>";
				var viewerUrl = "<%=rdViewerUrl%>";
				var rdServerSaveDir = "<%=rdServerSaveDir%>";
				var systemDownDir = "<%=systemDownDir%>";
				var downFileName = "<%=downFileName%>";
				var dpi300_hos_gubun = "<%=dpi300_hos_gubun%>";
				var imgEachHos = "<%=imgEachHos%>";
				var crownixVeiwer_print_YN = "<%=crownixVeiwer_print_YN%>";
				
				var downFileName = "테스트 Crownix 리포트";
				if("<%=downFileName%>" != ""){
					downFileName = "<%=downFileName%>";
				}
				var imgObj = {};
				<%
				//System.out.println("PrintParamList.size()   == "+PrintParamList.size());				
				%>
				if("<%=PrintParamList.size()%>" != "0"){
					imgObj["I_LOGMNM"] ="<%=param.get("I_LOGMNM")%>";
					imgObj["I_LOGMNU"] ="<%=param.get("I_LOGMNU")%>";
					imgObj["I_HOS"]    ="<%=param.get("I_HOS")%>";
					imgObj["I_DAT"]    ="<%=param.get("F600DAT")%>";
					imgObj["I_JNO"]    ="<%=param.get("F600JNO")%>";
					imgObj["P_GCD"]    ="<%=param.get("P_GCD")%>";
					imgObj["S018RFN"]  ="<%=param.get("S018RFN")%>";
					imgObj["FILE_NM"]  ="<%=param.get("FILE_NM")%>";
					imgObj["I_ACD"]    ="";
					imgObj["I_BDT"]    ="<%=param.get("F600BDT")%>";
					imgObj["I_LOGEFG"]    ="<%=param.get("I_LOGEFG")%>";	//이벤트 종류E VT_FLAG : (RP출력)
					imgObj["I_MCD"] = opener.parent.curr_menu_cd; 		// 결과지 뷰어를 실행한 메뉴코드(RSTUSER : 수진자별 결과 조회(A), 	RSTUSERTABLE : 수진자별 결과 조회(B),  RSTITEM : 항목별 결과조회)
				}

				setReportViewer(mrd_path, param, viewerUrl, rdServerSaveDir, systemDownDir, downFileName, imgObj, dpi300_hos_gubun, imgEachHos, crownixVeiwer_print_YN);
				
			};
			
			function onResult(){
				
			}
			
		</script>
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
			<input type="hidden" name="dpi300_hos_gubun" />
			<input type="hidden" name="imgEachHos" />
		</form>
		<%-- 첨부파일 다운로드 --%> 
		<iframe id="file_down" name="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
		<%-- 첨부파일 다운로드 --%>
	</body>
</html>


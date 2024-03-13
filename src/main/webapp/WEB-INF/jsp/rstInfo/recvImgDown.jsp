<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="kr.co.softtrain.common.web.util.CommonController"%>
<%@page language="java" import="java.io.*, java.util.*, java.sql.*,java.text.*,javax.servlet.http.*" %>
	<%
	
		CommonController cct = new CommonController();
		String serverName = request.getServerName();
		if("localhost".equals(serverName)){
			serverName = cct.getLocalServerIp();
		}
		
		String systemUrl = request.getScheme() + "://" + serverName + ":" + request.getServerPort();
		String root = request.getSession().getServletContext().getRealPath("/");

		request.setCharacterEncoding("utf-8"); 
		
		String FileNm = request.getParameter("fileList");
		String systemDownDir = request.getParameter("systemDownDir");
		String[] fileList = FileNm.split(";");
		
		
		//System.out.println("FileNm  "+FileNm);
		//System.out.println("systemDownDir  "+systemDownDir);
		//System.out.println("fileList  "+fileList[0]);
		//System.out.println(fileList.length);
		String hoscod = request.getParameter("I_HOS");

		String[] fileSize = new String[fileList.length];
		File file = null;
		for (int i = 0; i < fileList.length; i++)
		{
			//file = new File("E:\\home\\site1\\ROOT\\resultImg\\"+hoscod+"\\"+fileList[i]); 
	//		file = new File("\\\\219.252.39.104\\home\\site1\\shared_files\\resultImg\\"+hoscod+"\\"+fileList[i]);
			//System.out.println("file.length()   == "+root + systemDownDir +fileList[i]);
			file = new File(root + systemDownDir +fileList[i]);
			///file = new File("\\shared_files\\resultImg\\"+hoscod+"\\2019\\01\\22\\JPG\\"+fileList[i]);
			
			//file = new File("E:\\home\\site1\\ROOT\\resultImg\\"+fileList[i]); 
			fileSize[i] = file.length()+"";
// 			System.out.println(root + systemDownDir +fileList[i]);
// 			System.out.println(systemDownDir+fileList[i]);
			//System.out.println("file.length()   == "+file.length());
		}
	%>
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
    <meta http-equiv="content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="x-ua-compatible" content="IE=10">
    <title>일괄 다운로드</title>
    <!-- DEXTUploadX 버튼 상태를 위한 변수 -->

    <script type="text/javascript">
            // 전송중 상태를 위한 변수
            var g_bTransfer = false; 
    </script>

	<script type="text/javascript">
    	    function OnErrorDownloadMonitor(nCode, sMsg, sDetailMsg)
    	    {
    		    //alert(nCode);
    		    //alert(sMsg);
    		    //alert(sDetailMsg);
    	    }
    </script>

    <!-- DEXTUploadX 에러처리 -->

   <script type="text/javascript" for="FileDownloadMonitor" event="OnError(nCode, sMsg, sDetailMsg)">
			OnErrorDownloadMonitor(nCode, sMsg, sDetailMsg);
   </script>
   <script type="text/javascript" for="FileDownloadMonitor" event="OnCreationComplete()">
	    OnLoading();
	</script>
	 <!-- DEXTUploadX ActiveX 로딩, 아이템 및 프로퍼티 등 설정 -->

    <script type="text/javascript">
			function OnLoading()
			{ 
<%-- 				var hoscod = "<%=hoscod%>"; --%>
// 				// 파라메터로 받을때
// 				 var fileList = [
<%-- 				 <% for (int i = 0; i < fileList.length; i++) { %> --%>
<%-- 				 "<%= fileList[i] %>"<%= i < fileList.length - 1 ? "," : "" %> --%>
<%-- 				 <% } %>]; --%>
				
// 				 var fileSize = [
<%-- 				 <% for (int i = 0; i < fileSize.length; i++) { %> --%>
<%-- 				 "<%= fileSize[i] %>"<%= i < fileSize.length - 1 ? "," : "" %> --%>
<%-- 				 <% } %>]; --%>
				
// 				 for (var i = 0; i < fileList.length; i++) {
// 					if(fileSize[i] > 0)
// 					 {
// 						document.getElementById("FileDownloadMonitor").AddFile("https://www.seegenemedical.com/resultImg/"+hoscod+"/"+fileList[i], fileSize[i]);
// 					 }
// 					 else
// 					 {
// 						document.getElementById("FileDownloadMonitorNon").AddFile("https://www.seegenemedical.com/resultImg/"+hoscod+"/"+fileList[i], fileSize[i]);
// 					 }
// 				 }
					// 파라메터로 받을때
					var fileList = [];
					var fileSize = [];
					 <% 
// 	 					System.out.println(systemUrl);
// 	 					System.out.println(systemDownDir);
// 	 					System.out.println(fileSize);
					 	for (int i = 0; i < fileList.length; i++) {
					 %>
					 	fileList.push("<%= fileList[i] %>");	 
					 <%
					 	};
					 %>
					 <% 
					 	for (int i = 0; i < fileSize.length; i++) {
					 %>
						 fileSize.push("<%= fileSize[i] %>");	 
					 <%
					 	};
					 %>
					 for (var i = 0; i < fileList.length; i++) {
						if(fileSize[i] > 0)
						 {
							document.getElementById("FileDownloadMonitor").AddFile("<%=systemUrl + systemDownDir%>"+fileList[i], fileSize[i]);
//							document.getElementById("FileDownloadMonitor").AddFile("https://www.seegenemedical.com/resultImg/"+hoscod+"/"+fileList[i], fileSize[i]);
						 }
						 else
						 {
							document.getElementById("FileDownloadMonitorNon").AddFile("<%=systemUrl + systemDownDir%>"+fileList[i], fileSize[i]);
//							document.getElementById("FileDownloadMonitorNon").AddFile("https://www.seegenemedical.com/resultImg/"+hoscod+"/"+fileList[i], fileSize[i]);
						 }
					 }
       	        // DEXTUploadX 전송 정보 초기화
       	        Init();
    	    }
    </script>

   

    <!-- DEXTUploadX 전송 정보 초기화 -->

    <script type="text/javascript">
            function Init()
            {
				document.getElementById("FileDownloadMonitor").ListViewFileNameColumnWidth =370;

                // 파일개수 
                document.getElementById('_CurrentCount').innerHTML = 0; 
                document.getElementById('_TotalCount').innerHTML = document.getElementById("FileDownloadMonitor").TotalCount; 
                
                // 전체파일 전송량
                document.getElementById('_TransferedSizeOfTotalFile').innerHTML = "0%";
                    
                // 남은시간
                document.getElementById('_RemainingTime').innerHTML = "00:00:00"; 
                
                // 전체 파일의 사이즈에 대한 프로그래스바
                document.getElementById('_PercentOfTotalFile').style.width = 0; 
                
                // 파일이름
                document.getElementById('_CurrentFileName').innerHTML = ""; 
                
                // 현재파일 전송량
                document.getElementById('_TransferedSizeOfCurrentFile').innerHTML = "0%"; 
                
                // 전송속도
                document.getElementById('_BytesPerSec').innerHTML = "0 MB";  	
                
                // 현재 파일의 사이즈에 대한 프로그래스바
                document.getElementById('_PercentOfCurrentFile').style.width = 0; 
                
                // 저장경로
    	        var folderPath = document.getElementById("FileDownloadMonitor").DownloadFolder;
    	        var Path_Len = folderPath.length;
    	        var cut_Path = "";
    	        if(Path_Len <= 0)
				{
					 document.getElementById('_DownloadPath').innerHTML = "D:\NDImgRs";
				}
    	        if(28 < Path_Len)
    	        {
    	            cut_Path = folderPath.substr(0,28) + "...";
    	            document.getElementById('_DownloadPath').innerHTML = cut_Path;
    	        }
    	        else
    	        {
    	            document.getElementById('_DownloadPath').innerHTML = folderPath;
    	        }  	
				
            }
    </script>

    <!-- DEXTUploadX 전송 정보 리프레쉬 -->

    <script type="text/javascript">
    	    function Refresh()
    	    {
        	    // 파일개수
    	        document.getElementById('_CurrentCount').innerHTML = document.getElementById("FileDownloadMonitor").CurrentCount;  
    	        document.getElementById('_TotalCount').innerHTML = document.getElementById("FileDownloadMonitor").TotalCount; 
        	    
    	        // 전체파일 전송량
    	        document.getElementById('_TransferedSizeOfTotalFile').innerHTML = document.getElementById("FileDownloadMonitor").PercentOfTotalFile + "%";
        	    
    	        // 남은시간 
    	        document.getElementById('_RemainingTime').innerHTML = document.getElementById("FileDownloadMonitor").RemainingTime;  
        	    
    	        // 전체 파일의 사이즈에 대한 프로그래스바 
    	        // ((테이블 Width / 100) * _PercentOfTotalFile)
    	        document.getElementById('_PercentOfTotalFile').style.width = (3.13 * document.getElementById("FileDownloadMonitor").PercentOfTotalFile); 
        	    
    	        // 파일이름(전체경로에서 파일명 분리, 파일명의 길이가 12자를 넘을 경우 "..." 처리)
    	        var fullpath = document.getElementById("FileDownloadMonitor").CurrentFileName;
    	        var c_FileName = fullpath.substring(fullpath.lastIndexOf("\\") + 1);
    	        var name_Len = c_FileName.length;
    	        var cut_Name = "";
    	        
    	        if(12 < name_Len)
    	        {
    	            cut_Name = c_FileName.substr(0,12) + "...";
    	            document.getElementById('_CurrentFileName').innerHTML = cut_Name;
    	        }
    	        else
    	        {
    	            document.getElementById('_CurrentFileName').innerHTML = c_FileName;
    	        }
    	        
    	        
    	        // 현재파일 전송량
    	        document.getElementById('_TransferedSizeOfCurrentFile').innerHTML = document.getElementById("FileDownloadMonitor").PercentOfCurrentFile + "%";
    	               	    
    	        // 전송속도
                var CurrentSpeed = document.getElementById("FileDownloadMonitor").BytesPerSec; 
				
    	        if(1 < CurrentSpeed/1000.0/1000.0/1000.0) 
    	        {
    	           CurrentSpeed = CurrentSpeed / (1024*1024*1024);
    	           document.getElementById('_BytesPerSec').innerHTML = Math.round(CurrentSpeed * 100) / 100 + " GB";
    	        }
    	        else if(1 < CurrentSpeed/1000.0/1000.0) 
    	        {
    	           CurrentSpeed = CurrentSpeed / (1024*1024); 
    	           document.getElementById('_BytesPerSec').innerHTML = Math.round(CurrentSpeed * 100) / 100 + " MB";  
    	        }
    	        else if(1 < CurrentSpeed/1000.0) 
    	        {  
    	           CurrentSpeed = CurrentSpeed / 1024;
    	           document.getElementById('_BytesPerSec').innerHTML = Math.round(CurrentSpeed * 100) / 100 + " KB";
    	        }
    	        else 
    	        {
    	           document.getElementById('_BytesPerSec').innerHTML = Math.round(CurrentSpeed * 100) / 100 + " Bytes";
    	        }
        	    
    	        // 현재 파일의 사이즈에 대한 프로그래스바   
    	        // ((테이블 Width / 100) * _PercentOfCurrentFile)
    	        document.getElementById('_PercentOfCurrentFile').style.width = (3.13 * document.getElementById("FileDownloadMonitor").PercentOfCurrentFile); 
    	        
    	        // 저장경로
    	        var folderPath = document.getElementById("FileDownloadMonitor").DownloadFolder;
    	        var Path_Len = folderPath.length;
    	        var cut_Path = "";
    	        
    	        if(28 < Path_Len)
    	        {
    	            cut_Path = folderPath.substr(0,28) + "...";
    	            document.getElementById('_DownloadPath').innerHTML = cut_Path;
    	        }
    	        else
    	        {
    	            document.getElementById('_DownloadPath').innerHTML = folderPath;
    	        }
    	    }
    </script>

    <!-- DEXTUploadX 리프레쉬 타임 설정 -->

    <script type="text/javascript">
			
            var termination = 0;  
            var time; 
           
    	    function Repeat(bRefresh) 
    	    {    
    	        termination = document.getElementById("FileDownloadMonitor").PercentOfTotalFile;
        	    
    	        // 전송 상태 리프레쉬
    	        Refresh(); 
        	               
    	        if(bRefresh == false) 
                { 
				   // 다운로드 취소
    	           clearTimeout(time); 
    	           Init();
    	        }
    	        else 
                {  
                    if (termination >= 100) 
                    {
                       // 다운로드 완료
                       document.images["Cancel"].src="/images/downX/btn_close_nor.gif"; 
                       clearTimeout(time); 
                       if(true != document.getElementById("FileDownloadMonitor").CheckAutoCloseWindow) 
                            alert("전송이 완료되었습니다.");
                    }
                    else 
                       time = setTimeout("Repeat(true)",100); 
                }
            } 
    </script>

   

    <!-- DEXTUploadX 이벤트 코드 -->

    <script type="text/javascript" for="FileDownloadMonitor" event="OnTransferComplete()">  
        alert("전송이 완료되었습니다.");       
    </script>

    <!-- <script type="text/javascript" for="FileDownloadMonitor" event="OnTransferCancel()"> 
             Repeat(false);
             alert("전송이 취소되었습니다.");
    </script> -->

    <!-- DEXTUploadX 버튼 동작 코드 -->

    <script type="text/javascript">

            // 항목 삭제 버튼
		    function btnDeleteItem_Onclick()
		    {
		        // 전송중이 아닐때만 파일을 추가한다. 
			    if(false == g_bTransfer) { 
		            document.getElementById("FileDownloadMonitor").DeleteSelectedFile();
		            Refresh();
		        }
		    }
            
            // 파일 전송 및 프로그래스바를 비롯한 전송 정보 출력
			function btnTransfer_Onclick()
			{
			    // 전송중이 아닐때만 Transfer() 메소드를 호출한다. 
			    if(false == g_bTransfer && 0 != document.getElementById("FileDownloadMonitor").TotalCount) { 
			        document.getElementById("FileDownloadMonitor").Transfer(); 
		            Repeat(true); 
		            
		            // 전송상태를 전송중으로 설정하고, 이미지 변경
		            g_bTransfer = true; 
		            document.images["Transfer"].src="/images/downX/btn_ssend_dis.gif"; 
		            document.images["Cancel"].src="/images/downX/btn_cancel_nor.gif"; 
		            document.images["AddFile"].src="/images/downX/btn_fileadd_dis.gif";//20170120 양태용 주석 처리
		            document.images["DeleteItem"].src="/images/downX/btn_listdel_dis.gif";
		            document.images["SelectPath"].src="/images/downX/btn_rootchoice_dis.gif";
		            document.images["MoveUp"].src="/images/downX/btn_goup_dis.GIF";
		            document.images["MoveDown"].src="/images/downX/btn_godown_dis.GIF";
		        }
		        
			}
			
			// 취소 버튼
			function btnCancel_Onclick()
			{   
			    document.getElementById("FileDownloadMonitor").Cancel();   
			    Repeat(false);   
			    
			    if(true == g_bTransfer) {
			        // 전송상태를 중지로 선택하고, 이미지 변경
			        g_bTransfer = false; 
			        document.images["Transfer"].src="/images/downX/btn_ssend_nor.gif"; 
			        document.images["Cancel"].src="/images/downX/btn_close_nor.gif";
			        document.images["AddFile"].src="/images/downX/btn_fileadd_nor.gif"; //20170120 양태용 주석처리
			        document.images["DeleteItem"].src="/images/downX/btn_listdel_nor.gif";
		            document.images["SelectPath"].src="/images/downX/btn_rootchoice_nor.gif";
		            document.images["MoveUp"].src="/images/downX/btn_goup_nor.gif";
		            document.images["MoveDown"].src="/images/downX/btn_godown_nor.gif";
			    }
			}
			
			//파일 위치 이동
			function btnMoveFileUp()
			{ 
			    // 전송중이 아닐때만 파일을 추가한다. 
			    if(false == g_bTransfer) { 
			        document.getElementById("FileDownloadMonitor").MoveFileUp(); 
			    }
			}   
					
			function btnMoveFileDown()
			{ 
			    // 전송중이 아닐때만 파일을 추가한다. 
			    if(false == g_bTransfer) { 
			        document.getElementById("FileDownloadMonitor").MoveFileDown(); 
			    }
			}   
			
			// 체크박스 코드
			function btnCheckbox_Click()
			{
			    if(true == document.getElementById("FileDownloadMonitor").CheckAutoCloseWindow) 
			    {
			        document.getElementById("FileDownloadMonitor").CheckAutoCloseWindow = false; 
			        document.images["Chk_Box"].src="/images/downX/btn_checkno.GIF"; 
			    } 
			    else
			    {
			        document.getElementById("FileDownloadMonitor").CheckAutoCloseWindow = true; 
			        document.images["Chk_Box"].src="/images/downX/btn_checkyes.GIF"; 
			    }  
			}
			
			//DEXTUploadX 다운로드 폴더 지정
			function btnFolder_Onclick()
			{
			    // 전송중이 아닐때만 파일을 추가한다. 
			    if (false == g_bTransfer) { 
				    document.getElementById("FileDownloadMonitor").ChooseDownloadFolder();
				    Refresh();
				}
			}
    </script>

<style type="text/css">
<!--
body, td{
	font-family : 돋움;
	font-size : 11px;
}
.style3 {color: #787878;
	font-size : 12px;
}
.style4 {color: #FFFFFF;
	font-size : 12px;
}
-->
</style>

<script type="text/javascript">
//<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" onLoad="MM_preloadImages('/images/downX/btn_fileadd_over.gif','/images/downX/btn_listdel_over.gif','/images/downX/btn_goup_over.GIF','/images/downX/btn_godown_over.GIF','/images/downX/btn_close_over.gif','/images/downX/btn_ssend_over.gif')">
    <table width="600" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/images/downX/fum_box01.gif" width="2" height="2"></td>
        <td background="/images/downX/fum_box02.gif" width="463" height="2"></td>
        <td><img src="/images/downX/fum_box03.gif" width="2" height="2"></td>
      </tr>
      <tr>
        <td background="/images/downX/fum_box04.gif" width="2" height="200"></td>
        <td align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" background="/images/downX/p_head.gif" width="486" height="64" style="padding:4px 15px 0px 17px"><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td style="padding: 0px 0px 2px 0px;"><img src="/images/downX/icon_02.GIF" width="4" height="4"></td>
                <td style="padding: 0px 0px 2px 0px;"><img src="/images/downX/text_filename.GIF" width="42" height="10" align="absmiddle"></td>
                <td>
                    <!-- 파일이름 -->
                    <span id="_CurrentFileName" class="style4"></span>
                </td>
                <td style="padding: 0px 0px 2px 0px;"><img src="/images/downX/icon_02.GIF" width="4" height="4"></td>
                <td style="padding: 0px 0px 2px 0px;"><img src="/images/downX/text_saveroot.GIF" width="42" height="10"></td>
                <td colspan="4">
                    <!-- 다운로드 경로 -->
                    <span id="_DownloadPath" class="style4"></span>
                </td>
              </tr>
              <tr>
                <td colspan="4" height="8"></td>
              </tr>
              <tr>
                <td width="2%" style="padding: 0px 0px 2px 0px;"><img src="/images/downX/icon_02.GIF" width="4" height="4"></td>
                <td width="12%" style="padding: 0px 0px 2px 0px;"><img src="/images/downX/text_timeleft.GIF" width="43" height="10"></td>
                <td width="24%">
                    <!-- 남은시간 -->
                    <span id="_RemainingTime" class="style4"></span>
                </td>
                <td width="2%" style="padding: 0px 0px 2px 0px;"><img src="/images/downX/icon_02.GIF" width="4" height="4"></td>
                <td width="12%" style="padding: 0px 0px 2px 0px;"><img src="/images/downX/text_filenum.GIF" width="42" height="10"></td>
                <td width="16%">
                    <!-- 파일개수 -->
                    <span id="_CurrentCount" class="style4"></span><span class="style4">/</span><span id="_TotalCount" class="style4"></span>
                </td>
				
                <td width="2%" style="padding: 0px 0px 2px 0px;"><img src="/images/downX/icon_02.GIF" width="4" height="4"></td>
                <td width="12%" style="padding: 0px 0px 2px 0px;"><img src="/images/downX/text_speed.GIF" width="42" height="10"></td>
                <td width="18%">
                    <!-- 전송속도 -->
                    <span id="_BytesPerSec" class="style4"></span>
                </td>
              </tr>
            </table>          <span class="style4"></span></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" bgcolor="#eaecec" style="padding: 10px 10px 10px 10px;"><table width="100%" height="70" border="0" cellpadding="0" cellspacing="1" bgcolor="#c0c5cc">
              <tr>
                <td align="center" bgcolor="#FFFFFF" style="padding: 1px 1px 1px 1px;"><table width="100%" height="70" border="0" cellpadding="0" cellspacing="1" bgcolor="#e9e9e9">
                  <tr>
                    <td align="center" bgcolor="#FFFFFF" style="padding: 0px 14px 0px 14px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="2%"><img src="/images/downX/icon_03.GIF" width="4" height="4"></td>
                        <td width="14%" valign="top"><img src="/images/downX/text_nowfile.GIF" width="55" height="11"></td>
                        <td width="11%" valign="bottom">
                            <!-- 현재파일 전송량 -->
                            <span id="_TransferedSizeOfCurrentFile" class="style3"></span>
                        </td>
                        <td width="73%"><table width="100%" height="11" border="0" cellpadding="0" cellspacing="1" bgcolor="#7b8996">
                          <tr>
                            <td bgcolor="#f6f4e0">
                                <!-- 현재 파일의 사이즈에 대한 프로그래스바 -->
                                <!-- 좌우의 <td> 사이즈를 빼고 계산한다. ((390 - 2) / 100 * _PercentOfCurrentFile) -->
                                <div align="left">
                                    <img id="_PercentOfCurrentFile" src="/images/downX/bar.gif" height="9" /></div>
                            </td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td colspan="4" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="/images/downX/icon_03.GIF" width="4" height="4"></td>
                        <td valign="top"><img src="/images/downX/text_allfile.GIF" width="55" height="11"></td>
                        <td valign="bottom" class="style3">
                            <!-- 전체파일 전송량 -->
                            <span id="_TransferedSizeOfTotalFile" class="style3"></span>
                        </td>
                        <td><table width="100%" height="11" border="0" cellpadding="0" cellspacing="1" bgcolor="#7b8996">
                          <tr>
                            <td bgcolor="#f6f4e0">
                                <!-- 전체 파일 사이즈에 대한 프로그래스바 -->
                                <!-- 좌우의 <td> 사이즈를 빼고 계산한다. ((390 - 2) / 100 * _PercentOfTotalFile) -->
                                <div align="left">
                                    <img id="_PercentOfTotalFile" src="/images/downX/bar.gif" height="9" /></div>
                            </td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" bgcolor="#eaecec" style="padding: 0px 10px 0px 10px;"><table width="100%" height="110" border="0" cellpadding="0" cellspacing="1" bgcolor="#c0c5cc">
              <tr>
                <td align="center" bgcolor="#FFFFFF">
                    <object id="FileDownloadMonitor" height="108" width="574" classid="CLSID:471678BB-F992-4BE6-9761-7767883E8619"
					    codeBase="https://www.seegenemedical.com/resultDown/cab/DEXTUploadX.cab#version=3,3,0,6" viewastext>
						<param name="DialogBoxMode" value="DLG_LISTVIEW" />
						<!-- 다운로드 폴더 지정 -->
				        <param name="VisibleDownloadCheckbox" value="true">
					</object>
                </td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="32" align="center" bgcolor="#eaecec" style="padding: 4px 10px 0px 10px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" valign="top" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <!-- 항목 삭제 버튼 -->
                <img src="/images/downX/btn_listdel_nor.gif" name="DeleteItem" width="80" height="23" border="0" onclick="btnDeleteItem_Onclick()">
                <!-- 경로 선택 버튼 -->
                <img src="/images/downX/btn_rootchoice_nor.gif" name="SelectPath" width="80" height="23" border="0" onclick="btnFolder_Onclick()">
            </td>
            <td align="right" valign="top">
                <!-- 파일 한칸 위로 이동 -->
                <img src="/images/downX/btn_goup_nor.GIF" name="MoveUp" width="21" height="20" border="0" onclick="btnMoveFileUp()">
                <!--파일 한칸 아래로 이동-->
                <img src="/images/downX/btn_godown_nor.GIF" name="MoveDown" width="21" height="20" border="0" onclick="btnMoveFileDown()">
            </td>
              </tr>
            </table></td>
          </tr>

          <tr>
            <td height="1" align="center" bgcolor="#a7b2ba"></td>
          </tr>
          <tr>
            <td height="32" align="center" bgcolor="#969fa2" style="padding: 5px 10px 0px 10px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="4%">
                    <!-- 체크박스 -->
                    <img src="/images/downX/btn_checkno.GIF" name="Chk_Box" width="14" height="14" onclick="btnCheckbox_Click()">
                </td>
                <td width="28%"><img src="/images/downX/text_closeaftersend.gif" width="90" height="10"></td>
                <td width="68%" align="right">
                    <!-- 전송버튼 -->
                    <img src="/images/downX/btn_ssend_nor.gif" name="Transfer" width="80" height="23" border="0" onclick="btnTransfer_Onclick()">
                    <!-- 닫기 버튼 -->
                    <img src="/images/downX/btn_close_nor.gif" name="Cancel" width="80" height="23" border="0" onclick="btnCancel_Onclick()">
                </td>
              </tr>
            </table></td>
          </tr>
		  <tr><td height="32" align="center" bgcolor="#A9A9A9" style="padding: 5px 10px 0px 10px;"> 미생성 파일리스트<td></tr>
		    <tr>
            <td align="center" bgcolor="#eaecec" style="padding: 0px 10px 0px 10px;">
			<table width="100%" height="110" border="0" cellpadding="0" cellspacing="1" bgcolor="#c0c5cc">
              <tr>
                <td align="center" bgcolor="#FFFFFF">
                    <object id="FileDownloadMonitorNon" height="108" width="574" classid="CLSID:471678BB-F992-4BE6-9761-7767883E8619"
					    codeBase="https://www.seegenemedical.com/resultDown/cab/DEXTUploadX.cab#version=3,2,1,0" viewastext>
						<param name="DialogBoxMode" value="DLG_LISTVIEW" />
						<!-- 다운로드 폴더 지정 -->
				        <param name="VisibleDownloadCheckbox" value="false">
					</object>
                </td>
              </tr>
            </table></td>
          </tr>
        </table></td>
        <td background="/images/downX/fum_box05.gif" width="2" height="200"></td>
      </tr>

      <tr>
        <td><img src="/images/downX/fum_box10.gif" width="2" height="2"></td>
        <td background="/images/downX/fum_box09.gif" height="2"></td>
        <td><img src="/images/downX/fum_box11.gif" width="2" height="2"></td>
      </tr>
    </table>
</body>
</html>

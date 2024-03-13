<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>오더의뢰지</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	<%@ include file="/inc/reportPop.inc"%>
	
	<script type="text/javascript" src="../plugins/zip/FileSaver.js"></script>
	<script type="text/javascript" src="../plugins/zip/jszip-utils.js"></script>
	<script type="text/javascript" src="../plugins/zip/jszip.js"></script>
	
	<script type="text/javascript">
	var I_LOGMNU = "reqUser02";
	var I_LOGMNM = "오더의뢰지";
	var dataList = [];
	var dataProvider,gridView;
	$(document).ready( function(){
		
		dataProvider = new RealGridJS.LocalDataProvider();
		gridView = new RealGridJS.GridView("realgrid");
	    setStyles(gridView);
	    gridView.setHeader({
	    	height:40
	    })
	    gridView.setDisplayOptions({
		  heightMeasurer: "fixed",
		  rowResizable: true,
		  rowHeight: 150
		});

	    
	    gridView.setDataSource(dataProvider);
	    gridView.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields = [
	    	{fieldName: "COMPANY"},
	    	{fieldName: "DATE"},
	    	{fieldName: "HOSPITAL_CODE"},
	    	{fieldName: "SEQUENCE"},
	    	{fieldName: "SEND_USER_ID"},
	    	{fieldName: "UUID"},
	    	{fieldName: "I_IMG"},
	    	{fieldName: "FILENM"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider.setFields(fields);
	  //이미지 버튼 생성
		var columns = [
	        {name: "IMAGE", fieldName: "I_IMG", width: "150", styles: {contentFit: "auto"},    renderer:{showTooltip: true, type:"image", sommthing:false}, header: {text: "오더의뢰지"}}
	      , {type : "group" , name : "controll", width: "160", columns : [
	    	  /* {name: "DOWN", fieldName: "", width: "80", styles: {iconLocation: "right", borderRight:"#000000,0"},    renderer:{showTooltip: false, type:"multiIcon", renderCallback:function(grid, index){
		    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
		          var prf2 = [];
		          //console.log(JSON.stringify(index));
		          prf2.push('../images/common/btn_download.png');
		          
		          return prf2
		      }}, header: {text: "다운로드"}}
	    	  , */ {name: "PRINT", fieldName: "", width: "80", styles: {iconLocation: "center"},    renderer:{type:"image", sommthing:true, type:"multiIcon", renderCallback:function(grid, index){
		    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
		          var prf = [];
		          //console.log(JSON.stringify(index));
		          prf.push('../images/common/btn_fview.png');
		          
		          return prf
		      }}, header: {text: "미리보기" ,visible : false}}
		      //, {name: "PRF2", fieldName: "PRF", width: "100", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "시계열"} , "dynamicStyles":[{"criteria":["value = 'Y'"],"styles":["renderer=buttons1"]}]}
		      
	      //], header: {text: "다운로드/출력"}, hideChildHeaders : "hide"}
	      ], header: {text: "미리보기"}, hideChildHeaders : "hide"}
	      
	      ];
	    
		gridView.setColumnProperty("PRINT", "buttonVisibility", "always");
	    //gridView.setColumnProperty("DOWN", "buttonVisibility", "always");
	    
		gridView.setColumns(columns);
		
		gridView.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    };
		
		gridView.onDataCellClicked = function (grid, index) {
			//dataResult4(grid.getValues(index.itemIndex));
			/* if(index.column == "DOWN"){
				//console.log(index.itemIndex);
				
				//console.log(grid.getValue(index.itemIndex, "I_IMG"));
				
				
				downOrderFile(grid.getValue(index.itemIndex, "I_IMG"), grid.getValue(index.itemIndex, "FILENM"));
	 			//parent.goPage('RSTPRE', '이전결과', 'rstPre.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+grid.getValue(index.itemIndex, "CHN")+"&I_NAM="+grid.getValue(index.itemIndex, "NAM")+"&I_DAT="+grid.getValue(index.itemIndex, "DAT").substring(0,4)+"-"+grid.getValue(index.itemIndex, "DAT").substring(4,6)+"-"+grid.getValue(index.itemIndex, "DAT").substring(6,8) );
	 			//parent.goPage('STATSTIME', '시계열', 'statsTime.do', '&menutest=확인&adf=');
	 		} */
			if(index.column == "PRINT"){
				//console.log(index.itemIndex);
				viewPrintS(index.itemIndex);
	 			//parent.goPage('RSTPRE', '이전결과', 'rstPre.do', "&I_HOS="+$('#I_PHOS').val()+"&I_FNM="+$('#I_FNM').val()+"&I_CHN="+grid.getValue(index.itemIndex, "CHN")+"&I_NAM="+grid.getValue(index.itemIndex, "NAM")+"&I_DAT="+grid.getValue(index.itemIndex, "DAT").substring(0,4)+"-"+grid.getValue(index.itemIndex, "DAT").substring(4,6)+"-"+grid.getValue(index.itemIndex, "DAT").substring(6,8) );
	 			//parent.goPage('STATSTIME', '시계열', 'statsTime.do', '&menutest=확인&adf=');
	 		}
			
		};
		
		var checkBar = gridView.getCheckBar();
		checkBar.visible = false;
	    gridView.setCheckBar(checkBar);
	    
	    var stateBar = gridView.getStateBar();
	    stateBar.visible = false;
	    gridView.setStateBar(stateBar);
	});
	
	function orderReq01Call(formData){
		//console.log("formData = " + JSON.stringify(formData));
		ajaxCall("/orderReqList.do", formData);		// 20201222 오더의뢰지 2029년도 같이 나와서 프로시저에 병원코드 조건을 추가함
	}
	
	function orderReq01Call_2(formData){
		//console.log("formData = " + JSON.stringify(formData));
		ajaxCall("/orderReqList2.do", formData);	
	}
	
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/orderReqList.do" || strUrl == "/orderReqList2.do"){ // 20201222 오더의뢰지 2029년도 같이 나와서 프로시저에 병원코드 조건을 추가함
		
		var resultList =  response.resultList;
		
		for (var i in resultList){
			
			//resultList[i].I_IMG = "D:/eGovFrameDev-3.7.0-64bit/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/SMF-TRMS/shared_files/order/1f53504b-d223-437e-9e24-103a300e6bf6/"+resultList[i].FILENM;
			
//			console.log(resultList[i].FILENM);
			/* if(resultList[i].FLAG == "m"){
				resultList[i].I_IMG = "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID="+resultList[i].FILEPAH;
			} else if(resultList[i].FLAG == "s") {
				resultList[i].I_IMG = "http://219.252.39.172:8080/spring_basic/fileDown_xerox?UUID="+resultList[i].FILEPAH;
			} */
			
			resultList[i].I_IMG = "http://219.252.39.22:8080/spring_basic/fileDown?dat="+resultList[i].DATE+"&seq="+resultList[i].SEQUENCE+"&hos="+resultList[i].HOSPITAL_CODE;
			
			console.log(resultList[i].I_IMG);
		
			
			
		}
		/*
		resultList[0].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_1.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		resultList[1].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_2.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		resultList[2].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_3.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		resultList[3].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_4.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		resultList[4].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_1.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		resultList[5].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_2.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		resultList[6].I_IMG = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201811301743921653_3.jpg&sourceDptPathName=5231&destFileName=20181130-17439.jpg";
		*/
		//http://219.252.39.172:8080/spring_basic/fileDown_xerox?UUID="+resultList[i].UUID;
		

		//console.log(strUrl,resultList);

		
		dataList = resultList;
		
		/*
         $.each(resultList,function(i,v){
 			console.log(i,(i*200));
 	        window.setTimeout(function () {
 				if(resultList[i].FLAG == "m"){
 					resultList[i].I_IMG = "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID="+resultList[i].FILEPAH;
 				} else if(resultList[i].FLAG == "s") {
 					resultList[i].I_IMG = "http://219.252.39.172:8080/spring_basic/fileDown_xerox?UUID="+resultList[i].FILEPAH;
 				}
 				console.log(i,resultList[i].I_IMG);
 	        }, 1000*i);
 		});
		*/
			dataProvider.setRows(resultList);
			gridView.setDataSource(dataProvider);
			/*
			var setValue = {
					I_LOGMNU: I_LOGMNU
					, I_LOGMNM:I_LOGMNM
				};
		
		$.each(resultList, function(i, e) {
	        window.setTimeout(function () {
				if(resultList[i].FLAG == "m"){
// 					resultList[i].I_IMG = "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID="+resultList[i].UUID;
					setValue = {
							  I_IMG:"http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID="+resultList[i].UUID
						};
				} else if(resultList[i].FLAG == "s") {
					setValue = {
							  I_IMG:"http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID="+resultList[i].UUID
						};
// 					resultList[i].I_IMG = "http://219.252.39.172:8080/spring_basic/fileDown_xerox?UUID="+resultList[i].UUID;
				}
				dataProvider.updateStrictRow(i, setValue);
 	        }, 1000*i);
		});
		*/	
		
// 		gridView.setDataSource(dataProvider);
//         window.setTimeout(function () {
// 			dataList = resultList;
			
// 			gridView.closeProgress();
// 			//데이터 초기화
// 			dataProvider.clearRows();
	
			
// 			reSize();
//         }, 1000*(resultList.length+1));
		//setTimeout("setRow()", 100);
		
		/*
		if (dataProvider3.getRowCount() > 0){
			dataResult4(gridView3.getValues(0));	
		} else {
			dataProvider4.clearRows();
			gridView4.setDataSource(dataProvider4);

			}
		}
		*/
		}
	
		if(strUrl == "/imgUrlDown.do"){
			var resultList =  response.resultList;
			downOrder(response.downPath, response.fileNm);
		}
	}
	
	function reSize(){
		setTimeout("callResize()", 100);
		//http://219.252.39.22:8080/spring_basic/fileDown?UUID=	
	}
	
	function orderDownZip() {
		if(dataProvider.getRowCount() == 0){
			CallMessage("205","alert",["다운로드"]);	//선택 내역이 없습니다.
			return;
		}
		/*
		for (var i in dataList){
			var url = "http://img.neodin.com:8080/servlet/com.neodin.files.ImageResultPath?sourceFilePathName=201809018161981636_1.jpg&sourceDptPathName=5295&destFileName=20180901-81619.jpg";
	        var filename = dataList[i].UUID+".jpg";
	        var rstUserParam = "";
			//rstUserParam = "&url="+url;
			//rstUserParam += "&filename="+filename;
			
		} 
		*/
		//var param = {};
		//param.userYn = '1';
		
		var jsonData = JSON.stringify(dataList);
		var dataForm = {"jsonData": jsonData};
		//dataForm.jsonData = jsonData; 
		
		ajaxCall("/imgUrlDown.do", dataForm);
		
	}
	
	
	function downOrderFile(downPath, fileNm){
		$(location).attr("href",downPath);
		//var path = $("#I_FPT").val();
		//var fileNm = $("#I_FNM").val();
		//$('#file_down_img').attr("href",downPath);
		//$('#file_down_img').trigger('click');
		//console.log("확인");
		/*
		$.when(function () {
	        var def = jQuery.Deferred();
	        window.setTimeout(function () {
	        	document.getElementById('file_down_img').href = downPath;
	        	//console.log()
	        	document.getElementById('file_down_img').href = downPath;
	        	
	            def.resolve();
	        }, 100);
	        return def.promise();
	    }())
	    .done(function () {
	    });
		*/
	}
	
	function downOrder(downPath, fileNm){
		//var path = $("#I_FPT").val();
		//var fileNm = $("#I_FNM").val();
		
		$.when(function () {
	        var def = jQuery.Deferred();
	        window.setTimeout(function () {
	        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+downPath+"/"+fileNm+"&file_name="+encodeURIComponent(fileNm);
	            def.resolve();
	        }, 100);
	        return def.promise();
	    }())
	    .done(function () {
	    });
	}
	
	function viewPrintS(index){
		//승인 안되면 출력 못함
        var w = window.screen.width ;
        var h = window.screen.height ;
        //var dataset = ;
        //reportPop.inc의 셋팅값 받기
        //var datadd = "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=b330d8ba-f922-4214-bdcb-590bd60a701f";
        var viewerUrl = "<%=viewerUrl%>";
        
        var dataSet = dataList[index].I_IMG;
        
        //alert("## dataSetSSS :: "+dataSet);
        
        <%-- var mrd_path = "<%=systemUrl%>/mrd/GENERAL-01.mrd"; --%>
        <%-- var mrd_path = "<%=systemUrl%>/mrd/77-ECA4-TMP-02.mrd"; --%>
        // mrd_path 기존_backup
        <%-- var mrd_path = "<%=systemUrl%>/mrd/orderImageTest.mrd"; --%>
        var mrd_path = "orderImageTest.mrd";
        var rdServerSaveDir = "<%=rdSaveDir%>";
        var rd_param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rnl [|] /rdata ["+dataSet+"] /rimageopt render [1] /rsaveopt [4288] /rusecxlib /rpdfexportapitype [0] /rimagexdpi [110]  /rimageydpi [110] /p /rusesystemfont";
        //console.log("rd_param = " +rd_param);
        var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
        //alert(mrd_path);
        //rdata [{imgUrl : "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=b330d8ba-f922-4214-bdcb-590bd60a701f"} , {imgUrl : "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=a3668881-bed8-48e9-b8cf-9f708f3dbe62"}]
        
        //파일 명만 셋팅 , 확장자는 따로 셋팅
        var downFileName = dataList[index].FILENM;
        var param="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path+"&rdServerSaveDir="+rdServerSaveDir+"&param="+rd_param+"&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);

        //alert("rd_param :: "+rd_param);
        //alert("param :: "+param);
        
		var f = document.reportForm;
		f.viewerUrl.value			=  viewerUrl;
		f.mrd_path.value			=  mrd_path;
		f.rdServerSaveDir.value		=  rdServerSaveDir;
		f.param.value				=  param;
		f.systemDownDir.value		=  systemDownDir;
		f.downFileName.value		=  (downFileName);
		
        showReportViewDialog("/crownixViewer.do", w, h, printcallback);
     }
	
	function viewPrintA(){
		if(dataProvider.getRowCount() == 0){
			CallMessage("205","alert",["미리보기"]);	//선택 내역이 없습니다.
			return;
			//console.log("확인");
		}
	    //승인 안되면 출력 못함
        var w = window.screen.width ;
        var h = window.screen.height ;
        //var dataset = ;
        //reportPop.inc의 셋팅값 받기
        //var datadd = "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=b330d8ba-f922-4214-bdcb-590bd60a701f";
        var viewerUrl = "<%=viewerUrl%>";
        var dataSet = "";
        for (var i in dataList){
        	//dataSet += "|"+dataList[i].I_IMG;
        	//console.log(i);
        	
        	if(i == 0){
        		dataSet = dataList[i].I_IMG;
        	}else {
        		dataSet += "|"+dataList[i].I_IMG;
        	}
        	
        	
        }
        //console.log(dataSet);
        
        <%-- var mrd_path = "<%=systemUrl%>/mrd/GENERAL-01.mrd"; --%>
        <%-- var mrd_path = "<%=systemUrl%>/mrd/77-ECA4-TMP-02.mrd"; --%>
        // mrd_path 기존_backup
        <%-- var mrd_path = "<%=systemUrl%>/mrd/orderImageTest.mrd"; --%>
        var mrd_path = "orderImageTest.mrd";
        
        var rdServerSaveDir = "<%=rdSaveDir%>";
        var rd_param = "/rf [<%=rdAgentUrl%>] /rpprnform [A4] /rnl [|] /rdata ["+dataSet+"] /rimageopt render [1] /rsaveopt [4288] /rusecxlib /rpdfexportapitype [0] /rimagexdpi [110]  /rimageydpi [110] /p /rusesystemfont";
        var systemDownDir = "<%=rdSysSaveDir%>/<%=ss_hos%>/<%=yyyy%>/<%=mm%>/<%=dd%>";
//        alert("rd_param :: "+rd_param);
        //rdata [{imgUrl : "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=b330d8ba-f922-4214-bdcb-590bd60a701f"} , {imgUrl : "http://219.252.39.22:8080/spring_basic/fileDown_mobile?UUID=a3668881-bed8-48e9-b8cf-9f708f3dbe62"}]
        //console.log(rd_param);
        //파일 명만 셋팅 , 확장자는 따로 셋팅
        var downFileName = dataList[0].DATE+"-"+dataList[0].HOSPITAL_CODE+"-"+dataList[0].USER_ID;
        
        var param="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path+"&rdServerSaveDir="+rdServerSaveDir+"&param="+rd_param+"&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);

//        alert("param :: "+param);
        
		var f = document.reportForm;
		f.viewerUrl.value			=  viewerUrl;
		f.mrd_path.value			=  mrd_path;
		f.rdServerSaveDir.value		=  rdServerSaveDir;
		f.param.value				=  param;
		f.systemDownDir.value		=  systemDownDir;
		f.downFileName.value		=  (downFileName);
		
        showReportViewDialog("/crownixViewer.do", w, h, printcallback);
//         showReportViewDialog("/crownixViewer.do?"+param, w, h, printcallback);
     }
	function printcallback(){
		//alert("print callback!");
	}
    </script>
    
</head>
<body style="width : 500px;">
	<form method="post" action="" name="reportForm">
		<input type="hidden" name="viewerUrl" />
		<input type="hidden" name="mrd_path" />
		<input type="hidden" name="rdServerSaveDir" />
		<input type="hidden" name="param" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="downFileName" />
		<input type="hidden" name="imgObj" />
	</form>
	<form id="popupForm" name="popupForm">
	<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
	<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		
	<input id="I_DTLDAT" name="I_DTLDAT" type="hidden">
	<input id="I_DTLJNO" name="I_DTLJNO" type="hidden">
		<div class="container-fluid">                
	        <div class="con_wrap col-md-12 col-sm-12">
            	<div class="tit_btn_area modal_btn_area">
                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:orderDownZip()">모두 다운로드</button>
                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:viewPrintA()">모두 출력</button>
                </div>
            	<div class="tbl_type" style="overflow:auto">
					<div id="realgrid"  class="realgridH" style="height:400px;"></div>
			    </div>         
	        </div>                    
	    </div>
	 </form>
	 <iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
</body>
</html>

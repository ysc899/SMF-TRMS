<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>결과 리스트</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
    <%@ include file="/inc/reportPop.inc"%>
	
	<%
		String report_language = (String) request.getAttribute("report_language");	// 2020.06.01 - 결과지출력 버튼 국문/영문 구분자
	%>
	
	<script type="text/javascript">
	var I_LOGMNU = "RSTREPORT";
	var I_LOGMNM = "결과지 출력";
	var dataProvider,gridView;
	var rstUserInfo;
	var rstUserList = [];
	var dataList = [];
	
	var dpi300_hos_gubun = "";
	var imgEachHos = "";
	
	$(document).ready( function(){
		setGrid();
	});
	

	function setGrid(){
		 
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    setGridBar(gridView, false);
	    var fields=[ 
	    	{fieldName:"ADD_CD_NM"}
		    , {fieldName:"F010FKNADD"}
		    , {fieldName:"CU_SE_GCD"}
		    , {fieldName:"F010FKN"}
		    , {fieldName:"F010RNO"}
		    , {fieldName:"F100NAM"}
		    , {fieldName:"F600DAT"}
		    , {fieldName:"F600GCDADD"}
		    , {fieldName:"F600GCD"}
		    , {fieldName:"F600HAK"}
		    , {fieldName:"F600JNO"}
		    , {fieldName:"FILE_NM"}
		    , {fieldName:"HOS_GCD"}
		    , {fieldName:"PGCD"}
		    , {fieldName:"PGNM"}
		    , {fieldName:"P_F010FKN"}
		    , {fieldName:"P_GCD"}
		    , {fieldName:"PG_TIT"}
		    , {fieldName:"P_HOS_GCD"}
		    , {fieldName:"S018RFN"}
		    , {fieldName:"S018SYN"}
		    , {fieldName:"F600BDT"}
		    , {fieldName:"I_HOS"}
		    , {fieldName:"BDT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	
		    , {fieldName:"F600DT", dataType: "datetime", datetimeFormat: "yyyyMMdd"}	
		    , {fieldName:"DPI300_HOS"}
			, {fieldName:"IMG_EACH_HOS"}
		];
		dataProvider.setFields(fields);
		
		var columns=[
			{name:"F600DT",	fieldName:"F600DT",	header:{text:"접수일자"},		width:80,  styles:{datetimeFormat:"yyyy-MM-dd", textAlignment:"center"}}
			,{name:"F600JNO",	fieldName:"F600JNO",	header:{text:"접수번호"},		width:80 ,  styles:{textAlignment:"far"}}
			,{name:"F100NAM",	fieldName:"F100NAM",	header:{text:"수진자명"},		width:80 }
			,{name:"F600GCDADD",	fieldName:"F600GCDADD",	header:{text:"검사코드"},		width:80 }
			,{name:"F010FKNADD",	fieldName:"F010FKNADD",	header:{text:"검사명"},		width:230 }
			,{name:"BDT",		fieldName:"BDT",	header:{text:"보고일자"},		width:80,  styles:{datetimeFormat:"yyyy-MM-dd", textAlignment:"center"}}
	        , {name: "STSD",  fieldName: "STSD",  width: "60", cursor: "pointer", styles: {iconLocation : "center", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: true, type:"multiIcon", renderCallback:function(grid, index){
	    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
	          var ret = [];
	          ret.push('../images/common/ico_viewer.png');
	          return ret
	        }}, header: {text: "미리보기"}}
	        , {name: "STSD_PRINT",  fieldName: "STSD",  width: "50", cursor: "pointer", styles: {iconLocation : "center", iconAlignment: "center", textAlignment: "center"},    renderer:{showTooltip: true, type:"multiIcon", renderCallback:function(grid, index){
		    	  //var value = grid.getValue(index.itemIndex, index.fieldName);
		          var ret = [];
		          ret.push('../images/common/ico_viewer.png');
		          return ret
		        }}, header: {text: "인쇄"}}
		];
		
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    if(rstUserList.length>1){
		    getList();
	    }
	 	gridView.onDataCellClicked = function (grid, index) {
	 		
	 		// 미리보기/인쇄 버튼 클릭했을 경우,
			if(index.column == "STSD" || index.column == "STSD_PRINT")
			{
				viewPrint(index.itemIndex,index.column);
			}
		};
	}

	
	function viewPrint(itemIndex,columnIndex){
		var grid = gridView.getValues(itemIndex);
		
		grid["I_LOGMNU"] = I_LOGMNU;
		grid["I_LOGMNM"] = I_LOGMNM;
		grid["I_HOS"] = $("#I_HOS").val();
		//출력
		grid["I_LOGEFG"] = "RP";	//이벤트 종류E VT_FLAG : (RP출력)
		
		//승인 안되면 출력 못함
		var w = window.screen.width ;
		var h = window.screen.height ;
		
		var systemDownDir = "<%=rdSysSaveDir%>/"+$("#I_HOS").val();
<%-- 		var systemDownDir = "<%=rdSysSaveDir%>/"+$("#I_HOS").val()+"/<%=yyyy%>/<%=mm%>/<%=dd%>"; --%>

		//파일 명만 셋팅 , 확장자는 따로 셋팅
		var downFileName = grid["FILE_NM"];
		
		//console.log("#### downFileName :: "+downFileName);
		
		// mrd_path 기존_backup
		<%-- var mrd_path = "<%=systemUrl%>/mrd/"; --%>
		var mrd_path = grid["S018RFN"]+".mrd";

		var param = "/rf [<%=rdAgentUrl%>]";
		param += " /rpprnform [A4]";
		param += " /rv COR[NML]";
		param += "UID[<%=ss_uid%>]UIP[<%=ss_ip%>]";
		param += "JDAT["+grid["F600DAT"]+"]JNO["+grid["F600JNO"]+"]";
		param += "GCD["+grid["P_GCD"]+"]RFN["+grid["F010RNO"]+"]WTR[A]";
		param += "SYSURL["+trmsSystemUrl+"]PG_TIT["+grid["PG_TIT"]+"]";
		
		//console.log("cjw ### dpi300_hos_gubun : "+dpi300_hos_gubun);
		
		if(dpi300_hos_gubun == 714 || dpi300_hos_gubun == 300){
			param += "PGCD["+grid["PGCD"]+"]TMP["+grid["S018RFN"]+"] /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [300] /rimageydpi [300] /p /rusesystemfont";
		}else{
			param += "PGCD["+grid["PGCD"]+"]TMP["+grid["S018RFN"]+"] /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [110] /rimageydpi [110] /p /rusesystemfont";
		}
		
/* 		if(grid["S018RFN"] == "91-STI-LSD" || 
		   grid["S018RFN"] == "95-STI-NSR" || 
		   grid["S018RFN"] == "95-STI_PSTS_C"){
			param += " /rusecxlib /rpdfexportapitype [1] /p";
		}else{
			param += " /rusecxlib /rsaveopt [192] /p";	
		} */
		
		//alert("mrd_path :: "+mrd_path);
		
		var serverParam ="viewerUrl="+viewerUrl+"&mrd_path="+mrd_path;
		serverParam +="&rdServerSaveDir="+rdServerSaveDir+"&param="+param;
		serverParam +="&systemDownDir="+systemDownDir+"&downFileName="+encodeURIComponent(downFileName);
		serverParam += "&imgObj="+encodeURIComponent(JSON.stringify(grid));


		/**Report 전송 데이터 설정*/
		var f = document.reportForm;
		f.viewerUrl.value			=  viewerUrl;
		f.mrd_path.value			=  mrd_path;
		f.rdServerSaveDir.value		=  rdServerSaveDir;
		f.param.value				=  param;
		f.systemDownDir.value		=  systemDownDir;
		f.downFileName.value		=  (downFileName);
		f.imgObj.value				=  (JSON.stringify(grid));
		f.dpi300_hos_gubun.value	=  dpi300_hos_gubun;
		f.imgEachHos.value   =  imgEachHos;
		f.crownixVeiwer_print_YN.value	=  columnIndex;
		/**Report 전송 데이터 설정*/
		
		var StrUrl = "/crownixViewer.do";
		//StrUrl += "?mrd_pathTest="+mrd_path;
		//StrUrl += "&paramTest="+param;
		
		showReportViewDialog(StrUrl, w, h, printcallback);
// 		showReportViewDialog("/crownixViewer.do?"+serverParam, w, h, printcallback);
		
	}

	function printcallback(){
		//console.log("print callback!");
	}
	/*삭제*/
	function getReport(getValue)
	{
		rstUserList = getValue;
		var menu_cd = "<%=menu_cd%>";
		rstUserInfo = getValue[0];
		
    	$("#I_HOS").val(rstUserInfo["I_HOS"]);
	    
    	getList();
	}
	
	function getList()
	{
		var jsonRow = [];
		var url  = "/getGroup.do";
		/**
		 * 파라미터
		 * I_PARAM: 파라미터
		 * 구분자: | /  내용: 회사구분(NML)3자+접수일자8자+접수번호5자(모자란 자리수는 왼쪽으로 00채움 즉, 521 => 00521) + 검사코드 5자
		 * 예: I_PARAM=|NML201705160555641377|NML201705160555641392|
		 * IDR 김석인 접수번호5자->7자 수정
		 */
		 var strParam = "";
		 var menu_cd = "<%=menu_cd%>";
		 
		$.each(rstUserList, function(i, gridObj) {
			var gcd = "";
			if(menu_cd=="RSTUSER")
			{
				if(!isNull(gridObj["O_GCD"])){
					gcd =  gridObj["O_GCD"];
					if(gcd.length>5){
						gcd = gcd.substring(0,5);
					}
					strParam += "|NML" + rstUserInfo["DAT"] + lpad( rstUserInfo["JNO"] ,7,0) + lpad( gcd ,5,0) // IDR 김석인 파라미터 길이변경 (5->7)
				}
			}
// 			 console.log(gridObj["GCD"]);
// 			 console.log((!isNull(gridObj["GCD"])));
			if(menu_cd=="RSTITEM")
			{
				if(!isNull(gridObj["GCD"])){
					gcd =  gridObj["GCD"];
					if(gcd.length>5){
						gcd = gcd.substring(0,5);
					}
					strParam += "|NML" + gridObj["DAT"] + lpad( gridObj["JNO"] ,7,0) + lpad(gcd ,5,0); // IDR 김석인 파라미터 길이변경 (5->7)
				}
			}
			if(menu_cd=="RSTUSERTABLE")
			{
				if(!isNull(gridObj["O_GCD"])){
					gcd =  gridObj["O_GCD"];
					if(gcd.length>5){
						gcd = gcd.substring(0,5);
					}
					strParam += "|NML" + rstUserInfo["DAT"] + lpad( rstUserInfo["JNO"] ,7,0) + lpad( gcd ,5,0) // IDR 김석인 파라미터 길이변경 (5->7)
				}
			}
		});
		strParam += "|";
		
		//var param = "I_LOGMNU="+I_LOGMNU+"&I_LOGMNM="+I_LOGMNM+"&I_PARAM="+strParam+"&I_LOGEFG=FD&I_IMGSAVE=S
		var param = "I_LOGMNU="+I_LOGMNU+"&I_LOGMNM="+I_LOGMNM+"&I_PARAM="+strParam+"&I_LOGEFG=FD&I_IMGSAVE=S&report_language="+"<%=report_language%>";
		ajaxCall(url,param); // 수정
	}

	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
		//시스템 저장 dir
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		//console.log(resultList);
		
        $.each(resultList,function(k,v){
        	resultList[k]["F600BDT"] = resultList[k]["BDT"] +"";
        	resultList[k]["I_HOS"] = $("#I_HOS").val() +"";
        	/**** 2021.12.24 => 2022년 연도 작업 ****/
        	//resultList[k]["F600DT"] = resultList[k]["F600DAT"].replace("2023","2020").replace("2027","2021").replace("2028","2021").replace("2029","2021").replace("2030","2021") +"";
        	//resultList[k]["F600DT"] = resultList[k]["F600DAT"].substr(0,4).replace("2000","2020").replace("2027","2021").replace("2028","2021").replace("2029","2021").replace("2030","2021").replace("2023","2022").replace("2024","2022").replace("2025","2022").replace("2026","2022").replace("2037","2022").replace("2038","2022").replace("2039","2022")+resultList[k]["F600DAT"].substr(4,2)+resultList[k]["F600DAT"].substr(6,2)+"";
        	/* IDR 유승현 미래년도 제거 2022.08.11  */
        	resultList[k]["F600DT"] = resultList[k]["F600DAT"];
        	resultList[k]["F010FKNADD"] = resultList[k]["F010FKN"]+" " + resultList[k]["ADD_CD_NM"] ;
        	resultList[k]["F600GCDADD"] = resultList[k]["F600GCD"]+" " + resultList[k]["ADD_CD_NM"] ;
//        	console.log("cjw ## 01 : "+resultList[k]["F600BDT"]);        	
//        	console.log("cjw ## 02 : "+resultList[k]["I_HOS"]);        	
//        	console.log("cjw ## 03 : "+resultList[k]["F600DT"]);        	
//        	console.log("cjw ## 04 : "+resultList[k]["F010FKNADD"]);        	
//        	console.log("cjw ## 05 : "+resultList[k]["F600GCDADD"]);        	
//        	console.log("cjw ## 06 : "+resultList[k]["DPI300_HOS"]);      
        	dpi300_hos_gubun = resultList[k]["DPI300_HOS"];
			imgEachHos = resultList[k]["IMG_EACH_HOS"];
        });
        
		if(strUrl == "/getGroup.do"){
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	
    </script>
    
</head>
<body style="width : 800px;">
	<form method="post" action="" name="reportForm">
		<input type="hidden" name="viewerUrl" />
		<input type="hidden" name="mrd_path" />
		<input type="hidden" name="rdServerSaveDir" />
		<input type="hidden" name="param" />
		<input type="hidden" name="systemDownDir" />
		<input type="hidden" name="downFileName" />
		<input type="hidden" name="imgObj" />
		<input type="hidden" name="dpi300_hos_gubun" />
		<input type="hidden" name="imgEachHos" />
		<input type="hidden" name="crownixVeiwer_print_YN" />
		<input type="hidden" name="report_language" value="<%=report_language%>"/>
	</form>
	<div>
		<form id="popupForm" name="popupForm">
			<input id="I_HOS" name="I_HOS" type="hidden"/>
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
	        <div class="tbl_type" style="overflow:auto">
				<div id="realgrid"  class="realgridH"></div>
		    </div>         
		</form>
		<div class="modal-footer">
			<div class="min_btn_area">
				<button type="button" class="btn btn-info" data-dismiss="modal" onclick="closeModal()">닫기</button>
			</div>
		</div>
	 	<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
	</div>
</body>
</html>

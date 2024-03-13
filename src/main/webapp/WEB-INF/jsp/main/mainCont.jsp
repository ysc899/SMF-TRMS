<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="author" content="Hwang In Dong">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>씨젠의료재단</title>
	<%@ include file="/inc/common.inc"%>

	
	<script>
		
	var pmcd = "";
	var I_LOGMNU = "MAIN";
	var I_LOGMNM = "메인";
	var I_ListDate = 60;
	
	var gridView1,gridView2,dataProvider1,dataProvider2;
	
	var count = 0;		// 페이지 확인 0이면 첫 페이지
	//session
	$(document).ready( function(){
		var s_ecf = "<%= ss_ecf %>";
		var s_hos = "<%= ss_hos %>";
		if(s_ecf=="C"){
			//hos_div
			$(".hidden_srch").addClass("display_off");
		}else{
			$(".hidden_srch").removeClass("display_off");
		}
		$("input[name='I_LOGMNU']").val(I_LOGMNU);
		$("input[name='I_LOGMNM']").val(I_LOGMNM);
		$('#I_PECF').val(s_ecf);
		$('#I_PHOS').val(s_hos);
		$('#I_HOS').val(s_hos);
		
		var rightNow = new Date();
		var yearAgo = new Date();
		
		/*수진자 목록 조회 일자 */
		var res = rightNow.toISOString().slice(0,10).replace(/-/g,"");
		yearAgo.setDate(yearAgo.getDate() -I_ListDate);
		var res2 = yearAgo.toISOString().slice(0,10).replace(/-/g,"");
		$("#I_FDT").val(res2);
		$("#I_TDT").val(res);
		/*수진자 목록 조회 일자 */

		yearAgo.setDate(yearAgo.getDate() -(I_ListDate + 150));
		var res3 = yearAgo.toISOString().slice(0,10).replace(/-/g,"");
		/*감염병신고 조회 일자  */
		$("#I_GFDT").val(res3);
		$("#I_GTDT").val(res);
		/*감염병신고 조회 일자  */
// 		callLoading(null,"on");
		
		setGrid1();
		setGrid2();
		getNotice("OFF_DOCU");	//공문
		getNotice("NOTICE");	//새소식
	});
	
	function setGrid1(){		
		dataProvider1 = new RealGridJS.LocalDataProvider();
		gridView1 = new RealGridJS.GridView("realgrid1");
		setStyles(gridView1);
		setGridBar(gridView1, false);
		gridView1.setIndicator({
		  visible: false  
		});
		gridView1.setDataSource(dataProvider1);
		gridView1.setEditOptions({
			insertable: false,
			deletable: false,
			//readOnly: true
			editable:false
		})
		var fields1 = [
			{fieldName: "DNO"},
			{fieldName: "DAT"},
			{fieldName: "DATS", dataType: "datetime", datetimeFormat: "yyyyMMdd"},
	    	{fieldName: "JNO"},
	    	{fieldName: "NAM"},
	    	{fieldName: "CHN"},
	    	{fieldName: "SEX"},
	    	{fieldName: "AGE"},
	    	{fieldName: "STSD"},
	    	{fieldName: "ETC"},
	    	{fieldName: "PRF"}, //이전결과/ 시계열
	    	{fieldName: "PNF"},  // 양성 Y
	    	{fieldName: "PNFIMG"},  // 양성 Y
	    	{fieldName: "C07H"}, // 하이 Y
	    	{fieldName: "C07HIMG"}, // 하이 Y
	    	{fieldName: "C07L"}, // 로우 Y
	    	{fieldName: "C07LIMG"}, // 로우 Y
	    	{fieldName: "ETCINF"},
	    	{fieldName: "GADINF"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
		];
		dataProvider1.setFields(fields1);
	  //이미지 버튼 생성
		var imageButtons1 = [{
		  "name": "보기",
		  "up": "../images/common/btn_view.png",
		  "hover": "../images/common/btn_view.png",
		  "down": "../images/common/btn_view.png",
		  "width":50
		}];
		
		//이미지 버튼 추가
		gridView1.addCellRenderers([{
		  "id":"buttons1",
		  "type":"imageButtons",
		  "images": imageButtons1,
		  "margin":10
		}]);
		
		var columns1 = [
			{name: "DATS", fieldName: "DATS", width: "70", renderer:{showTooltip: true}, header: {text: "접수일자"},  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
		  ,	{name: "CHN", fieldName: "CHN", width: "80", renderer:{showTooltip: true}, header: {text: "차트번호"}}
		  , {name: "NAM", fieldName: "NAM", width: "80", styles: {textAlignment: "near"},	renderer:{showTooltip: true}, header: {text: "수진자"}}
		  , {name: "SEX", fieldName: "SEX", width: "30", styles: {textAlignment: "center"},	renderer:{showTooltip: true}, header: {text: "성별"}}
		  , {name: "AGE", fieldName: "AGE", width: "30", styles: {textAlignment: "center"},	renderer:{showTooltip: true}, header: {text: "나이"}}
	      
	      , { type : "group", name : "PNFGROUP", width : "30", hideChildHeaders : "false", columns : [
	    	  {name: "PNF11",  fieldName: "PNFIMG",  width: "15", styles: {iconLocation : "center", iconAlignment: "center", contentFit : "auto", borderRight:"#000000, 0", paddingRight  : "0"},    renderer:{type:"image", smoothing: false}, header: {text: "1"}}
	    	  , { type : "group", name : "PNFSUBGROUP", width : "15", orientation : "vertical", columns : [
	    		  {name: "PNF22",  fieldName: "C07HIMG",  width: "15", styles: {iconLocation : "bottom", iconAlignment: "center", contentFit : "auto", borderBottom:"#000000, 0", paddingLeft  : "0", paddingBottom  : "0"},    renderer:{type:"image", smoothing: false}, header: {text: "2"}}
	    		, {name: "PNF33",  fieldName: "C07LIMG",  width: "15", styles: {iconLocation : "top", iconAlignment: "center", contentFit : "auto", paddingLeft  : "0" , paddingTop  : "0"},    renderer:{type:"image", smoothing: false}, header: {text: "3"}}
	    	  ]}
	      ], header: {text: "상태"}}
	      
		  
		  , {name: "STSD", fieldName: "STSD", width: "100", styles: {textAlignment: "center"},	renderer:{showTooltip: true}, header: {text: "검사진행상황"}}
		];
		gridView1.setColumns(columns1);

		gridView1.setDisplayOptions({
			fitStyle: "even"
		});
		gridView1.setColumnProperty("PNF", "buttonVisibility", "always");
		
		gridView1.onShowTooltip = function (grid, index, value) {
			var column = index.column;
			var itemIndex = index.itemIndex;
			 
			var tooltip = value;
			if (column == "ETC") {
				tooltip = "검체번호 : "+value +
						"\n검체명	: "+ grid.getValue(itemIndex, "ETCINF");
			}
			
			if (column == "STSD") {
				var gadInfHtml = "";
	 			var gadData = "";
	 			gadData = "["+grid.getValue(index.itemIndex, "GADINF")+"]";
	 			var gadInf = JSON.parse(gadData); 
	 			for (var i in gadInf){
	 				gadInfHtml += 		"<tr>";
					gadInfHtml += 			"<td>"+gadInf[i].OCD+"</td>";
					gadInfHtml += 			"<td>"+gadInf[i].GCD+"</td>";
					gadInfHtml += 			"<td>"+gadInf[i].SNM+"</td>";
					gadInfHtml += 			"<td>"+gadInf[i].BDT+"</td>";
					gadInfHtml += 		"</tr>";
	 			}
	 			
	 			var body_width = $('#bodyWidth').width();
	 			var p_list_width = 800;
	 			if (body_width < 1475){
	 				p_list_width = 100;
	 			}
	 			
	 			$('#stsData').html(gadInfHtml);
	 			$('#sts-Modal').attr('style', 'display:block;position:absolute;left:'+p_list_width+'px;');
	 			
	 			$('#sts-Modal').modal('show');
	 			
	 			
			}
			return tooltip;
		}
		gridView1.onDataCellClicked = function (grid, index) {
			var param =  '&I_PFNM='+$("#I_PFNM").val()+'&I_PHOS='+$("#I_PHOS").val();
			param 	 +=  "&I_CHN="+grid.getValue(index.itemIndex, "CHN");
			param 	 +=  "&I_NAM="+grid.getValue(index.itemIndex, "NAM");
			param 	 +=  "&I_DAT="+parseDate(grid.getValue(index.itemIndex, "DAT"));
			param 	 +=  "&I_FDT="+$('#I_FDT').val();
			param 	 +=  "&I_TDT="+$('#I_TDT').val();
			param 	 +=  "&I_POT="+index.itemIndex;
			param 	 +=  "&I_MAINFLAG=Y";
			
			parent.goPage('RSTUSER', '수진자별 결과관리', 'rstUser.do',param);
		};
		
		if($('#I_PHOS').val() != ""){
			dataResult1();
		}
	}	
	function setGrid2(){		

		dataProvider2 = new RealGridJS.LocalDataProvider();
		gridView2 = new RealGridJS.GridView("realgrid2");
		
		setStyles(gridView2);
		setGridBar(gridView2, false);
		gridView2.setIndicator({ visible: false});
		gridView2.setDataSource(dataProvider2);

		var fields = [{fieldName: "R_GUN"}
		,{fieldName: "R_TNM"}
		,{fieldName: "R_JDT"}
		,{fieldName: "R_JDTO", dataType: "datetime", datetimeFormat: "yyyyMMdd"}
		,{fieldName: "R_BDT"}
		,{fieldName: "R_SDT"}
		,{fieldName: "R_SDTO", dataType: "datetime", datetimeFormat: "yyyyMMdd"}
		,{fieldName: "R_DAT"}
		,{fieldName: "R_JNO"}
		,{fieldName: "R_NAM"}
		,{fieldName: "R_CHN"}
		,{fieldName: "R_BIR"}
		,{fieldName: "R_SEX"}
		,{fieldName: "R_CBC"}
		,{fieldName: "I_LOGMNU"}
		,{fieldName: "I_LOGMNM"}];
		dataProvider2.setFields(fields);
		
		var columns = [
			  {name:"R_GUN",	fieldName:"R_GUN",	width:"50",		styles:{textAlignment:"center"},	header:{text:"질병군"}}
			, {name:"R_TNM",	fieldName:"R_TNM",	width:"150",	styles:{textAlignment:"near"},		header:{text:"병원체"}}
			, {name:"R_JDTO",	fieldName:"R_JDTO",	width:"80",		styles:{datetimeFormat: "yyyy-MM-dd",textAlignment:"center"},	header:{text:"검체의뢰일"}}
			, {name:"R_SDTO",	fieldName:"R_SDTO",	width:"80",		styles:{datetimeFormat: "yyyy-MM-dd",textAlignment:"cenger"},	header:{text:"신고일"}}
			, {name:"R_NAM",	fieldName:"R_NAM",	width:"80",		styles:{textAlignment:"center"},	header:{text:"환자명"}}
			, {name:"R_SEX",	fieldName:"R_SEX",	width:"50",		styles:{textAlignment:"center"},	header:{text:"성별"}}
		];
		gridView2.setColumns(columns);
		gridView2.setDisplayOptions({
  			fitStyle: "even"
		});
		
		gridView2.onDataCellClicked = function (grid, index) {
			var param =  '&I_FNM='+$("#I_FNM").val()+'&I_HOS='+$("#I_HOS").val();
			param 	 +=  "&I_TNM="+grid.getValue(index.itemIndex, "R_TNM");
			param 	 +=  "&I_NAM="+grid.getValue(index.itemIndex, "R_NAM");
// 			param 	 +=  "&I_FDT="+parseDate(grid.getValue(index.itemIndex, "R_JDT"));
			param 	 +=  "&I_FDT="+parseDate(grid.getValue(index.itemIndex, "R_DAT"));
			param 	 +=  "&I_TDT="+parseDate(grid.getValue(index.itemIndex, "R_DAT"));
			
			parent.goPage('INFECT', '감염병 신고', 'infect.do',param);
		};
		
		if($('#I_HOS').val() != ""){
			dataResult2();
// 			$('#I_PHOS').val(s_hos);
// 			$('#I_HOS').val(s_hos);
		}
	}	
	
	var serachCnt = 0;
	function dataResult1(){
		serachCnt++;
		var formData = $("#rstUserForm").serialize();
		ajaxCall("/rstUserList.do", formData,false);
	}

	function dataResult2(){
		serachCnt++;
		var formData = $("#infectForm").serializeArray();
		ajaxCall("/infectList.do",formData,false);
		
	}
	function getNotice(dclFlag){
		serachCnt++;
		var formData = $("#noticeForm").serializeArray();
		formData.push({ name: "I_DCL",value : dclFlag});
		ajaxAsyncCall("/mainContNotice.do",formData,false);
		
	}
	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList =  response.resultList;
		if(strUrl == "/rstUserList.do"){
			for (var i in resultList){
				if(resultList[i].PRF == "N"){
					resultList[i].PRF = "";
				}
				if(resultList[i].DAT != 0){
					resultList[i].DAT2 = resultList[i].DAT.substring(0,4)+"-"+resultList[i].DAT.substring(4,6)+"-"+resultList[i].DAT.substring(6,8);
				}
				
				if(resultList[i].PNF != ""){
					if(resultList[i].PNF == "Y"){
						resultList[i].PNFIMG = "../images/common/ico_warning.png";
					} else if(resultList[i].PNF == "Y"){
						resultList[i].PNFIMG = "../images/common/ico_dis_warning.png";
					} else {
						resultList[i].PNFIMG = "../images/common/ico_dis_warning.png";
					}
				}
				
				if(resultList[i].C07H != ""){
					if(resultList[i].C07H == "Y"){
						resultList[i].C07HIMG = "../images/common/ico_high.png";
					} else if(resultList[i].C07H == "Y"){
						resultList[i].C07HIMG = "../images/common/ico_dis_high.png";
					} else {
						resultList[i].C07HIMG = "../images/common/ico_dis_high.png";
					}
				}
				
				if(resultList[i].C07L != ""){
					if(resultList[i].C07L == "Y"){
						resultList[i].C07LIMG = "../images/common/ico_low.png";
					} else if(resultList[i].C07L == "Y"){
						resultList[i].C07LIMG = "../images/common/ico_dis_low.png";
					} else {
						resultList[i].C07LIMG = "../images/common/ico_dis_low.png";
					}
				}
			}
	        $.each(resultList,function(k,v){
	    		resultList[k].DATS = (v.DAT+"").trim();
	        });
			dataProvider1.setRows(resultList);
			gridView1.setDataSource(dataProvider1);			
		}
		if(strUrl == "/infectList.do"){
			$.each(resultList,function(k,v){
	    		resultList[k].R_JDTO = (v.R_JDT+"").trim();
	    		resultList[k].R_SDTO = (v.R_SDT+"").trim();
	        });
			dataProvider2.setRows(resultList);
			gridView2.setDataSource(dataProvider2);			
		}
		if(strUrl == "/mainContNotice.do"){
			var lstLi = "";
			var wdt = "";
			var S014DCL = "";
		    for (var i in resultList)
		    {
		    	wdt = resultList[i].S014WDT;
		    	S014DCL = resultList[i].S014DCL;
		    	lstLi +="\n			<li>";
		    	lstLi +="\n				<a href=\"#\" class=\"ellipsis\" onClick=\"javascript:openPopup('notice','"+resultList[i].S014SEQ+"')\">"+resultList[i].S014TIT+"</a>";
		    	lstLi +="\n			</li>";
		    }
		    if(S014DCL == "OFF_DOCU"){
		   		$("#official_lst").html(lstLi);	//official_lst          notice_lst
		    }
		    if(S014DCL == "NOTICE"){
		   		$("#notice_lst").html(lstLi);	//official_lst          notice_lst
		    }
		    TabResize();
		}
		serachCnt--;
		if(serachCnt==0){
			callLoading(strUrl,"off");
		}
	}
	

	var popFlag = "";
	var sSeq = "";
	/* 팝업 호출 및 종료 */
	function openPopup(flag,s014Seq){
		var setValus,labelNm,strUrl;
		popFlag = flag;
		if(!isNull(s014Seq)){
			strUrl = "/notice01.do";
			labelNm = "공문/새소식 조회";
			setValus = sSeq;
			setValus = {};
			setValus["index"] =  s014Seq;
			setValus["MainYN"] =  "Y";
		}else{
			strUrl = "/hospSearchS.do";
			labelNm = "병원 조회";
		}
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup(strUrl,labelNm,setValus,callFunPopup);
	}
	
	
	/*callback 호출 */
	function callFunPopup(url,iframe,setValus){
		//병원조회
		if(url == "/hospSearchS.do"){
			if(popFlag == "R"){
				/* setValus = $('#I_PFNM').val();
				if(isNull($('#I_PFNM').val())){
					setValus = '';
				} */
				gridValus = {};
				gridValus["I_HOS"] =  $('#I_PHOS').val();
				gridValus["I_FNM"] =  $('#I_PFNM').val();
			}else if(popFlag == "I"){
/* 				setValus = $('#I_FNM').val();
				if(isNull($('#I_FNM').val())){
					setValus = '';
				} */
				gridValus = {};
				gridValus["I_HOS"] =  $('#I_HOS').val();
				gridValus["I_FNM"] =  $('#I_FNM').val();
			}
		}
		iframe.gridViewRead(setValus);
	}

	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(popFlag == "R"){
			$('input[name=I_PHOS]').val(data[1].F120PCD);
			$('#I_PFNM').val(data[1].F120FNM);
			$('#I_PFNM').attr("title",data[1].F120FNM);
			callLoading(null,"on");
			dataResult1();
		}else if(popFlag == "I"){
			$('input[name=I_HOS]').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
			$('#I_FNM').attr("title",data[1].F120FNM);
			callLoading(null,"on");
			dataResult2();
		}
	}
	function morePage(flag){
		if(flag == 1){//수진자별 결과관리
			//parent.goPage('RSTUSER', '수진자별 결과관리', 'rstUser.do','&I_PFNM='+$("#I_PFNM").val()+'&I_PHOS='+$("#I_PHOS").val());
			parent.goPage('RSTUSER', '수진자별 결과관리', 'rstUser.do','');
		}else if(flag == 2){//감염병 신고
			parent.goPage('INFECT', '감염병 신고', 'infect.do','&I_HOS='+$("#I_HOS_KEY").val()+'&I_FNM='+$("#I_FNM").val());
			//parent.goPage('INFECT', '감염병 신고', 'infect.do','&I_FNM='+$("#I_FNM").val()+'&I_HOS='+$("#I_HOS_KEY").val());
		}else if(flag == 3){//공문/새소식(공문)
			parent.goPage('NOTICE', '공문/새소식', 'notice.do','&DCL_CD=OFF_DOCU');
		}else if(flag == 4){//공문/새소식(새소식)
			parent.goPage('NOTICE', '공문/새소식', 'notice.do','&DCL_CD=NOTICE');
		}
	}
	/* enter시 조회 */
	function enterSearch(flag){
		var I_FNM = "";
		
		if(event.keyCode == 13){
			//병원코드 입력 후 조회 할 경우
			if(flag == "R"){
		 		if(isNull($('#I_PFNM').val())){ 		//병원 이름이 없을경우
					if(!isNull($('#I_PHOS_KEY').val())){		//병원코드가 있을 경우
						I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_PHOS_KEY').val());
						$('input[name=I_PHOS]').val($('#I_PHOS_KEY').val());
						$('#I_PFNM').val(I_FNM);
						$('#I_PFNM').attr("title",I_FNM);
						callLoading(null,"on");
						dataResult1();
					}
				}
			}else if(flag == "I"){
				I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS_KEY').val());
		 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
					if(!isNull($('#I_HOS_KEY').val())){		//병원코드가 있을 경우
						$('input[name=I_HOS]').val($('#I_HOS_KEY').val());
						$('#I_FNM').val(I_FNM);
						$('#I_FNM').attr("title",I_FNM);
						callLoading(null,"on");
						dataResult2();
					}
				}
			}
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_PHOS_KEY"){
				$('#I_PHOS').val('');
				$('#I_PFNM').val('');
			}
			if(target.id == "I_HOS_KEY"){
				$('#I_HOS').val('');
				$('#I_FNM').val('');
			}
		}
	}
	
	</script>
</head>
<body>
	<form id="rstUserForm" name="rstUserForm">
		<%-- 수진자목록 조회 조건 --%>
		<input name="I_LOGMNU" type="hidden"/>
		<input name="I_LOGMNM" type="hidden"/>
		<input id="I_DGN"	name="I_DGN"	type="hidden" value="D" />	<%--접수일자구분--%>
		<input id="I_FDT"	name="I_FDT"	type="hidden" value="" />	<%--접수일자FROM--%>
		<input id="I_TDT"	name="I_TDT"	type="hidden" value="" />	<%--접수일자TO--%>
		<input id="I_NAM"	name="I_NAM"	type="hidden" />	<%--수진자명--%>
		<input id="I_RGN1"	name="I_RGN1"	type="hidden" />	<%--일반구분(N)--%>
		<input id="I_RGN2"	name="I_RGN2"	type="hidden" />	<%--조직구분(C)--%>
		<input id="I_RGN3"	name="I_RGN3"	type="hidden" />	<%--세포여부(C)--%>
		<input id="I_HAK"	name="I_HAK"	type="hidden" />	<%--학부구분 --%>
		<input id="I_STS"	name="I_STS"	type="hidden" />	<%--상태구분--%>
		<input id="I_PCHN"	name="I_PCHN"	type="hidden" />	<%--챠트번호--%>
		<input id="I_PETC"	name="I_PETC"	type="hidden" />	<%--기타기록（검체번호） --%>
		<input id="I_PJKNF"	name="I_PJKNF"	type="hidden" />	<%--진료과(1)/진료의사(2)flag --%>
		<input id="I_PJKN"	name="I_PJKN"	type="hidden" />	<%--진료과/진료의사--%>
		<input id="I_PECF"	name="I_PECF"	type="hidden" />	<%--씨젠사용자FLAG:'E'--%>
		<input id="I_PHOS"	name="I_PHOS"	type="hidden" />	<%--병원코드_씨젠사용자인경우 --%>
		<input id="I_ICNT"	name="I_ICNT" 	type="hidden" value="40" />	<%-- 읽을갯수--%>
		<input id="I_PNN"	name="I_PNN"	type="hidden" value="C" />	<%--이전다음--%>
		<input id="I_ROW"	name="I_ROW"	type="hidden" value="0" />	<%--수량--%>
		
		<input id="I_PINQ" name="I_PINQ" type="hidden" value="N"> <%-- 기간전체조회 Flag Y:전체, N:1년이내 --%>
		<%-- 수진자목록 조회 조건 --%>
	 </form>
	<form id="infectForm" name="infectForm">
		<%-- 감염병 신고 조건 --%>
		<input name="I_LOGMNU" type="hidden"/>
		<input name="I_LOGMNM" type="hidden"/>
		<input id="I_GFDT"	name="I_FDT"	type="hidden" value="" />	<%-- 접수일자FROM --%>
		<input id="I_GTDT"	name="I_TDT"	type="hidden" value="" />	<%-- 접수일자TO --%>
		<input id="I_HOS"	name="I_HOS"	type="hidden" />			<%-- 병원코드  --%>
		<input id="I_GBN"	name="I_GBN"	type="hidden" value="4" />	<%-- INFECT_TERM  구분 4:접수일자 , 2:진단일, 3:신고일   --%>
		<input id="I_GUN"	name="I_GUN"	type="hidden" />			<%-- 질병군 --%>
		<input id="I_TNM"	name="I_TNM"	type="hidden" />			<%-- 병원체명 --%>
		<input id="I_NAM"	name="I_NAM"	type="hidden" />			<%-- 환자명  --%>
		<%-- 감염병 신고 조건 --%>
	 </form>
	<form id="noticeForm" name="noticeForm">
		<%-- 공문 새소식 조건 --%>
		<input name="I_LOGMNU" type="hidden"/>
		<input name="I_LOGMNM" type="hidden"/>
		<input id="I_SDV" name="I_SDV" 	value="TEST_RESULT" type="hidden"/><%--공지 조회 조건 검사결과관리 --%>
		<%-- 공문 새소식 조건 --%>
	 </form>
	<!-- content_inner -->
	<div class="content_inner" style="padding:5px 0px 0px 0px;">
		<div class="container-fluid">
			<div class="con_wrap col-md-5 col-sm-12">
				<div class="title_area main_bg_red" id="rstTitle">
					<h3 class="tit_h3">수진자 목록</h3>
					<div class="tit_btn_area">
					  	<div class="srch_item hidden_srch"  style="padding-right: 3px;">
						  	<label for="" class="label_basic">병원</label>
						  	<input name="I_PHOS" id="I_PHOS_KEY" type="text"  value="<%=ss_hos%>" style="width: 70px; min-width: 70px;" maxlength="5" onkeydown="javasscript:enterSearch('R')" class="srch_put numberOnly"/>
							<input type="text" class="srch_put hos_pop_srch_ico" id="I_PFNM" name="I_PFNM" readonly="readonly"  style="width: 150px;min-width: 140px;"  onClick="javascript:openPopup('R')"/>								  
					   	</div>
						<a href="#" class="btn_more"  onClick="morePage(1)">More</a>
					</div>
				</div>
				<div class="con_section">
					<div class="tbl_type">
						<div id="realgrid1"  class="realgridH" style="height: 700px;"></div>
					</div>
				</div>
			</div>
			
			<div class="con_wrap col-md-7 col-sm-12">
				<div class="notice_wrap" style="padding-top: 0px; padding-bottom: 5px;">
					<div class="con_wrap notice_box col-md-6 col-sm-12">
						<div class="title_area main_bg_gray">
							<h3 class="tit_h3">공문</h3>
							<a href="#" class="btn_more"  onClick="morePage(3)">More</a>
						</div>
						<div class="con_section">
							<ul class="notice_lst official_lst" id="official_lst">
							</ul>
						</div>
					</div>
					
					<div class="con_wrap notice_box col-md-6 col-sm-12"  >
						<div class="title_area main_bg_gray">
							<h3 class="tit_h3">새소식</h3>
							<a href="#" class="btn_more"  onClick="morePage(4)">More</a>
						</div>
						<div class="con_section">
							<ul class="notice_lst" id="notice_lst">
							</ul>
						</div>
					</div>
				</div>
				<div class="title_area  main_bg_red" style="padding:0px 0px 19px 0px;">
					<h3 class="tit_h3">감염병 신고</h3>
					<div class="tit_btn_area">
					  	<div class="srch_item hidden_srch" style="padding-right: 3px;">
						  	<label for="" class="label_basic">병원</label>
						  	<!-- 
						  	<input name="I_HOS" id="I_HOS_KEY"  type="text"  value="<%=ss_hos%>" style="width: 70px; min-width: 70px;" maxlength="5" onkeydown="javasscript:enterSearch('I')" class="srch_put numberOnly"/>
						  	 -->
						  	 <input name="I_HOS_KEY" id="I_HOS_KEY"  type="text"  value="<%=ss_hos%>" style="width: 70px; min-width: 70px;" maxlength="5" onkeydown="javasscript:enterSearch('I')" class="srch_put numberOnly"/>
						    <input type="text" class="srch_put hos_pop_srch_ico" id="I_FNM" name="I_FNM" readonly="readonly"  style="width: 150px; min-width: 140px;" onClick="javascript:openPopup('I')"/>									 
					   	</div>
					   	 <a href="#" class="btn_more"  onClick="morePage(2)">More</a>
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
	<!-- content_inner end -->   
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원(회원) 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<style type="text/css">
		.label_basic{width: 100px;}
	</style>
	
	<script>

    var pscd = "";
	var I_EKD = "INIT_PW";	//EMAIL_CLS	이메일 종류 	INIT_PW	비밀번호 초기화
	var gridView;
	var dataProvider;
    var gridValues = [];
    var gridLabels = [];
    var ynValues = [];
    var ynLabels = [];

    function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		search();
	        }
	    }
    }
	
	$(document).ready( function(){
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);

		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "USER_DIV",'Y');
		$('#I_SERDIV').html(resultCode);
		jcf.replace($("#I_SERDIV"));
  		<%
  		Map<String, Object> QuickParam = new HashMap<String, Object>();
 		if(paramList.size()>0){
 			for(int idx=0;idx<paramList.size();idx++)
 			{
 				QuickParam = paramList.get(idx);
 				if(!"".equals(QuickParam.get("S008VCD"))){
		%>
			$("#<%=QuickParam.get("S007RCD")%>").val("<%=QuickParam.get("S008VCD")%>");
	  		jcf.replace($("#<%=QuickParam.get("S007RCD")%>"));
		<%
	  			}
  			}
  		}
  	%>
	    getGridCode("AUTH");
	    getGridCode("YES_NO");
	    
		$("#SearchBtn").click(function(){
			search();
		});

	});

	function getGridCode(pscd){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD="+pscd;
		ajaxAsyncCall("/getCommCode.do", formData);
	}
	function search(){
		dataResult();
	}
	function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysUserMngList.do", formData);
	}
	
	var commonCnt = 0;
	// ajax 호출 result function
	function onResult(strUrl,response){
// 		callLoading(strUrl,"off");
		if(strUrl == "/getCommCode.do"){
			var resultList =  response.resultList;
			var I_PSCD =  response.I_PSCD;
			
            $.each(resultList,function(k,v){
            	if(I_PSCD=="AUTH"){
	            	gridValues.push(v.VALUE);
	                gridLabels.push(v.LABEL);
            	}else{
	            	ynValues.push(v.VALUE);
	                ynLabels.push(v.LABEL);
            	}
            });
            if(gridValues.length>0 && ynValues.length>0){
	   		 	setGrid();
            }
		}
		if(strUrl == "/sysUserMngList.do"){
	    	gridView.cancel();
			var resultList =  response.resultList;
	 		gridView.setAllCheck(false);
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	
	function setGrid(){
		 
	    
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    gridView.setEditOptions({
	    	editable: true
	    });
	    var fields=[
		    	  {fieldName:"USERDIV"}		//USDNM
		    	, {fieldName:"LOGCOR"}		//USDNM
		    	, {fieldName:"USDNM"}		//USDNM
		    	, {fieldName:"LOGLID"}		//LOGINID
		    	, {fieldName:"S005ANM"}		//LOGINID
		    	, {fieldName:"S005AGR"}		//LOGINID
				, {fieldName:"LOGHOS"}		//병원코드
				, {fieldName:"F120FNM"}		//병원명
				, {fieldName:"F120FL1"}		//대표상호명
				, {fieldName:"F120GIC"}		//요양기관코드
				, {fieldName:"LOGTEL"}		//전화번호
				, {fieldName:"LOGEML"}		//멜주소
				, {fieldName:"LOGWNO"}		//대표자명
				, {fieldName:"LOGGNO"}		//면허번호
				, {fieldName:"LOGSNO"}		//사업자번호
				, {fieldName:"F120WNO"}		//대표자명
				, {fieldName:"I_LOGMNU"}	//메뉴코드
				, {fieldName:"I_LOGMNM"}	//메뉴명
				, {fieldName:"I_EKD"}		//메일 코드
				, {fieldName:"S005DRS"}		//원장출신학교
				, {fieldName:"S005HPH"}		//병원홈페이지
				, {fieldName:"S005ITM"}		//전문진료과목
				, {fieldName:"S005POS"}		//우편번호
				, {fieldName:"S005JAD1"}	//지번주소1
				, {fieldName:"S005DAD1"}	//도로명주소1
				, {fieldName:"S005DAD2"}	//도로명주소2
				, {fieldName:"S005MMO"}		//메모
				, {fieldName:"S005MRV"}		//메일수신여부
				, {fieldName:"S005SYN"}		//SMS사용권한
	    	];
	    dataProvider.setFields(fields);
	    
	    var columnStyles_USERDIV = [{
	        criteria: "values['USERDIV'] = 'C'",
	        styles: "fontBold=true"
	    }];
	    var columns=[
	    	  {name:"USDNM",		fieldName:"USDNM",		header:{text:"병원/사원"},				editable: false,	width:100}
		    	, {name:"LOGLID",		fieldName:"LOGLID",		header:{text:"아이디"},			editable: false,	width:120, renderer:{type: "link", url:"#",cursor:"pointer",   requiredFields: "LOGLID", showUrl: false}, dynamicStyles: columnStyles_USERDIV}
		    	, {name:"LOGHOS",		fieldName:"LOGHOS",		header:{text:"병원코드"},			editable: false,	width:80 ,  styles: {textAlignment: "center"}}
		    	, {name:"S005AGR",		fieldName:"S005AGR",	header:{text:"사용자권한"},		editable: true,		width:120, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "domainOnly": true}}//,dropDownWhenClick :true
		    	, {name:"S005SYN",		fieldName:"S005SYN",	header:{text:"SMS사용권한"},		editable: true,		width:100, lookupDisplay : true, "values": ynValues,"labels": ynLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "domainOnly": true}}//,dropDownWhenClick :true
		    	, {name:"F120FNM",		fieldName:"F120FNM",	header:{text:"병원명(사용자)"},		editable: false,	width:150 , renderer:{ showTooltip: true }}
		    	, {name:"F120GIC",		fieldName:"F120GIC",	header:{text:"요양기관코드"},		editable: false,	width:100,  styles: {textAlignment: "center"} }
		    	, {name:"LOGTEL",		fieldName:"LOGTEL",		header:{text:"연락처"},			editable: false,	width:120 }
		    	, {name:"LOGEML",		fieldName:"LOGEML",		header:{text:"대표 이메일"},		editable: false,	width:150 }
		    	, {name:"LOGWNO",		fieldName:"LOGWNO",		header:{text:"대표자명"},			editable: false,	width:120, renderer:{ showTooltip: true } }
		    	, {name:"LOGGNO",		fieldName:"LOGGNO",		header:{text:"대표자 면허 번호"},	editable: false,	width:140 ,  styles: {textAlignment: "center"}}
		    	, {name:"LOGSNO",		fieldName:"LOGSNO",		header:{text:"사업자번호"},		editable: false,	width:110 ,  styles: {textAlignment: "center"}}
		    	, {name:"S005DRS",		fieldName:"S005DRS",	header:{text:"원장출신학교"},		editable: false,	width:130 }
		    	, {name:"S005HPH",		fieldName:"S005HPH",	header:{text:"병원홈페이지"},		editable: false,	width:150 }
		    	, {name:"S005ITM",		fieldName:"S005ITM",	header:{text:"전문진료과목"},		editable: false,	width:120 }
		    	, {name:"S005POS",		fieldName:"S005POS",	header:{text:"우편번호"},			editable: false,	width:80 }
		    	, {name:"S005JAD1",		fieldName:"S005JAD1",	header:{text:"지번주소"},			editable: false,	width:120, renderer:{ showTooltip: true } }
		    	, {name:"S005DAD1",		fieldName:"S005DAD1",	header:{text:"도로명주소"},		editable: false,	width:130, renderer:{ showTooltip: true } }
		    	, {name:"S005DAD2",		fieldName:"S005DAD2",	header:{text:"나머지주소"},		editable: false,	width:150, renderer:{ showTooltip: true } }
	    ];
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);

	    gridView.setColumnProperty("S005AGR", "editButtonVisibility", "always");
	    gridView.setColumnProperty("S005SYN", "editButtonVisibility", "always");
	    gridView.setCheckableExpression("value['USERDIV'] = 'C'", true);
	    gridView.onDataCellClicked  = function (grid, index) {
	        var itemIndex = index.itemIndex;
			var gridValus = gridView.getValues(itemIndex);
			//column
			if( index.column=="LOGLID"){
				if( gridValus["USERDIV"]=="C"){
			    	openPopup(index);
				}else{
					CallMessage("284");	// 씨젠 사원의 상세정보는 확인할 수 없습니다.
				}
			}
	    };
	}
	function exportGrid(){

		var totalCnt = dataProvider.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		gridView.exportGrid({
		    type: "excel",
		    target: "local",
		    lookupDisplay : true,
		    fileName: I_LOGMNM+".xlsx",
		    showProgress: true,
		    applyDynamicStyles: false,
		    progressMessage: "엑셀 Export중입니다.",
		    indicator: "default",
		    header: "visible",
		    footer: "default",
		    compatibility: "2010",
		    done: function () {  //내보내기 완료 후 실행되는 함수
		    }
		});
	}
	/** 수정 **/
	function rowUpdate()
	{
    	gridView.commit();
        var  dataRows = dataProvider.getStateRows('updated');
		if(dataRows.length == 0){
			CallMessage("248");
			return;
		}
    	CallMessage("203","confirm","",updateS005AGR);	// 저장하시겠습니까?
	}
	
	function updateS005AGR()
	{
        var dataRows = dataProvider.getStateRows('updated');
   		var strUrl = "/sysUserMngAuthSave.do";
   		var Provider = dataProvider;
   		
   		ajaxListCall(strUrl,dataProvider,dataRows,updateEnd, false); // 삭제
	}
	function updateEnd(rtnCall){
		if(!rtnCall){
			CallMessage("244","alert",["저장"]);		//{0}에 실패하였습니다.
		}else{
			search();
		}
	}
	/** 수정 **/

	/*삭제*/
	function deleteRow()
	{
        var dataRows = gridView.getCheckedRows();
		if(dataRows.length == 0){
	    	CallMessage("243");	//선택내역이 없습니다.
			return;
		}
 		for (var u in dataRows){
			var gridValus = gridView.getValues(u);
			if(dataProvider.getValue(dataRows[u], "USERDIV") == "E"){
				CallMessage("255");	//씨젠의료재단 사원의 정보는 사용자 권한만 </br>변경이 가능합니다.
				return;
	        }
 		}
    	CallMessage("242","confirm","",fnDel);	//정말 삭제하시겠습니까?
	}
	function fnDel()
	{
        var dataRows = gridView.getCheckedRows();
 		for (var u in dataRows){
			var gridValus = gridView.getValues(u);
			if(dataProvider.getValue(dataRows[u], "USERDIV") == "E"){
				CallMessage("255");	//씨젠의료재단 사원의 정보는 사용자 권한만 </br>변경이 가능합니다.
				return;
	        }
 		}
   		var strUrl = "/sysUserMngDel.do";
   		var Provider = dataProvider;
   		
   		ajaxListCall(strUrl,dataProvider,dataRows,fnDelEnd); // 삭제
	}
	function fnDelEnd(rtnCall)
	{
   		if(!rtnCall){
   			CallMessage("244","alert",["삭제"]);		//{0}에 실패하였습니다.
   		}else{
   			search();
   		}
		
	}
	/*삭제*/
	
	/*비밀번호 초기화 /mailSender.do*/
	function mailCheck()
	{
        var dataRows = gridView.getCheckedRows();
		if(dataRows.length == 0){
			CallMessage("243");	// 선택내역이없습니다.
			return;
		}
   	    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

 		for (var u in dataRows){
			var gridValus = gridView.getValues(u);
	        dataProvider.setValue(dataRows[u], "I_EKD", I_EKD);// I_EKD    EMAIL_CLS	이메일 종류 	INIT_PW	비밀번호 초기화

			if(dataProvider.getValue(dataRows[u], "USERDIV") == "E"){
			    dataProvider.setRowStates(dataRows, "none", false, false);
				CallMessage("255");	// 사원 정보는 사용자 권한만 변경 확인 가능합니다.
				return;
	        }
			if(isNull(dataProvider.getValue(dataRows[u], "LOGEML").match(regExp))){
			    dataProvider.setRowStates(dataRows, "none", false, false);
				CallMessage("256");	// 256=대표 이메일이 없습니다.<br>대표 이메일을 확인해주세요.
				return;
	        }
 		}
		CallMessage("253","confirm","",   mailSender);	// 비밀번호를 초기화 하시겠습니까?
	}

	/*비밀번호 초기화 /mailSender.do*/
	function mailSender(){
        var dataRows = gridView.getCheckedRows();
   		var strUrl = "/mailSender.do";
   		var Provider = dataProvider;

   		ajaxListCall(strUrl,dataProvider,dataRows,mailSenderEnd); // 삭제
	}
	function mailSenderEnd(rtnCall)
	{
   		if(!rtnCall){
   			CallMessage("244","alert",["비밀번호 초기화 메일 전송"]);		//{0}에 실패하였습니다.
   		}else{
			CallMessage("276","alert","",search);	//저장을 완료하였습니다.
//    			search();
   		}
		
	}
	
	/* 팝업 호출 및 종료 */
	function openPopup(index){
		var gridValus,labelNm;
		if(isNull(index)){
			labelNm = "사용자 정보 등록";
		}else{
	        var itemIndex = index.itemIndex;
			gridValus = gridView.getValues(itemIndex);
			labelNm = "사용자 정보 수정";
		}
		//fnOpenPopup(url,labelNm,gridValus,callback)
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup("/sysUserMng01.do",labelNm,gridValus,callFunPopup);
	}
	/*callback 호출 */
	function callFunPopup(url,iframe,gridValus){
		if(url="/sysUserMng01.do"){
			if(!isNull(gridValus)){
				iframe.gridViewRead(gridValus);
			}
		}
	}

	/*close 호출*/
	function fnCloseModal(obj){
		search();
	}
	/* 팝업 호출 및 종료 */
	</script>
	
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
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
				<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
				 <div class="srch_box">
					<div class="srch_box_inner">
						<div class="srch_item srch_item_v2">
							<label for="" class="label_basic">아이디</label>
							<input type="text" class="srch_put engNum" maxlength="12"  id="I_SERID" name="I_SERID"  onkeydown="return enterSearch(event)" >
						</div>
						<div class="srch_item srch_item_v2">
							<label for="" class="label_basic">병원(사용자)명</label>
							<input type="text" class="srch_put" id="I_SERNM" name="I_SERNM"  onkeydown="return enterSearch(event)" >
						</div>               
					</div>
					
					<div class="srch_box_inner m-t-10">
						<div class="srch_item srch_item_v2">
							<label for="" class="label_basic">병원코드</label>
							<input type="text" class="srch_put numberOnly"  maxlength="5"  id="I_SERHOS" name="I_SERHOS"   onkeydown="return enterSearch(event)" >
						</div>
						<div class="srch_item srch_item_v2">
							<label for="" class="label_basic">요양기관코드</label>
							<input type="text" class="srch_put numberOnly" maxlength="10"  id="I_SERGIC" name="I_SERGIC"  onkeydown="return enterSearch(event)" >
						</div>
						<div class="srch_item srch_item_v2">
							<label for="" class="label_basic">사용자 구분</label>
							<div class="select_area">
								<select class="form-control" id="I_SERDIV"  name="I_SERDIV">
								</select>
							</div>
						</div>
					</div>
					
					<div class="btn_srch_box">
						<button type="button" id="SearchBtn" class="btn_srch" >조회</button>
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
				<h3 class="tit_h3">병원(회원) 관리</h3>
				<div class="tit_btn_area">
					<button type="button" class="btn btn-default btn-sm"  onClick="javascript:openPopup()">등록</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:rowUpdate()">권한수정</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow()">삭제</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:mailCheck()">비밀번호 초기화</button>
					<!-- <button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button> -->
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
	<!-- content_inner -->       	
</body>
</html>


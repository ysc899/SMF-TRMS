<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>수진자 정보 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	
	<script type="text/javascript">
	
	var pscd = "";
	var I_EKD = "INIT_PW";
	var gridView;
	var dataProvider;
	var gridValues = [];
	var gridLabels = [];
	
	$(document).ready( function(){
/* 	    $('#I_FNM').change(function(){
	    	$('#I_HOS').val('');
	    }); */
	    
	    if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }
	    if($('#I_ECF').val() == "C"){
	    	$('#hospRes').show();
	    	$('#hospAdd').show();
	    	$('#hospDel').show();
	    }
/* 		$("#I_HOS").keyup(function(event){
			if(event.keyCode!=13){
				$('#I_FNM').val('');
			}
		}); */
	    
		var resultCode = getCode(I_LOGMNU, I_LOGMNM, "GENDER",'Y');
		$('#I_SEX').html(resultCode);
		jcf.replace($("#I_SEX"));
		
  		<%
  		Map<String, Object> QuickParam = new HashMap<String, Object>();
  		if(paramList.size()>0){
  			for(int idx=0;idx<paramList.size();idx++)
  			{
  				QuickParam = paramList.get(idx);
		%>
				if("<%=QuickParam.get("S007RCV")%>" == "S_TERM"){
					changeDT("<%=QuickParam.get("S008VCD")%>");
				}else{
					$("#<%=QuickParam.get("S007RCD")%>").val("<%=QuickParam.get("S008VCD")%>");
			  		jcf.replace($("#<%=QuickParam.get("S007RCD")%>"));
				}
	<%
  			}
  		}
  	%>		
	    
	    getGridCode();
	});
	
    function getGridCode(){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD=GENDER";
		ajaxCall("/getCommCode.do", formData);
	}
    
	function setGrid(){
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
		
	    gridView.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	        editable: true
	    })
    
	    var fields=[
	    		  {fieldName:"C001HOS"}		//병원 코드
				, {fieldName:"C001CHN"}		//차트번호
				, {fieldName:"C001NAM"}		//수진자 명
				, {fieldName:"C001HPN"}		//핸드폰 번호
				, {fieldName:"C001BDT"}		//생년월일
				, {fieldName:"C001SEX"}		//성별
				, {fieldName:"C001AGE", dataType: "number"}		//나이
				, {fieldName:"C001CUR"}		//등록자 ID
				, {fieldName:"C001CDT"}		//등록 일자
				, {fieldName:"C001CTM"}		//등록 시간
				, {fieldName:"C001CIP"}		//등록자 IP
				, {fieldName:"C001UUR"}		//수정자 ID
				, {fieldName:"C001UDT"}		//수정 일자
				, {fieldName:"C001UTM"}		//수정 시간
				, {fieldName:"C001UIP"}		//수정자 IP
				, {fieldName:"I_LOGMNU"}	//메뉴코드
				, {fieldName:"I_LOGMNM"}	//메뉴명
	    	];
	    
	    dataProvider.setFields(fields);
	    var columnStyles_Bold = [{
	        criteria: "values['C001CHN'] != ''"
	    }];
	    
	    var columns=[
	    	  {name:"C001CHN", fieldName:"C001CHN", editable: false,	width:80 ,renderer:{requiredFields: "C001CHN", showUrl: false}, header: {text: "차트 번호", subTextLocation : "left", subTextGap : 2, subStyles : {foreground : "#ffff0000"}}
	    	  , editor : {textCase: "upper", maxLength : 20}, validations : [{ criteria : "value not match '\[^0-9|^A-Z\]'", message : "대문자,숫자만 입력하세요.", level : "error", mode : "always" }], dynamicStyles: columnStyles_Bold}
	    	, {name:"C001NAM",	fieldName:"C001NAM", header:{text:"수진자 명"}, editable: true, width:120, editor : { maxLength : 40 } }//,dropDownWhenClick :true
	    	, {name:"C001HPN",	fieldName:"C001HPN", header:{text:"핸드폰 번호"}, editable: true, width:150, editor : { maxLength : 11 }, "displayRegExp":/^([0-9]{3})([0-9]{3,4})([0-9]{4})$/, "displayReplace": "$1-$2-$3", validations : [{ criteria : "value not match '\[^0-9\]'", message : "숫자만 입력하세요.", level : "error", mode : "always" }]}
	    	, {name:"C001BDT",	fieldName:"C001BDT", header:{text:"생년월일"}, styles: {textAlignment: "center" },	 editable: true, width:100, editor : { minLength : 8, maxLength : 8 }, "displayRegExp":/^([0-9]{4})([0-9]{2})([0-9]{2})$/, "displayReplace": "$1-$2-$3" , validations : [{ criteria : "value not match '\[^0-9\]'", message : "숫자만 입력하세요.", level : "error", mode : "always" }] }
	    	, {name:"C001SEX",	fieldName:"C001SEX", header:{text:"성별"}, styles: {textAlignment: "center" }, editable: true, width:120, lookupDisplay : true, "values": gridValues,"labels": gridLabels, updatable:true, editor : {"type" : "dropDown" , "dropDownPosition": "button", "textReadOnly":true}}//,dropDownWhenClick :true
	    	, {name:"C001AGE",	fieldName:"C001AGE", header:{text:"나이"}, styles: {textAlignment: "far" }, editable: true, width:120, editor : { maxLength : 3}, validations : [{ criteria : "value not match '\[^0-9\]'", message : "숫자만 입력하세요.", level : "error", mode : "always" }] }
	    ];
		
	    gridView.addCellStyle("style", {
		    "editable": true
		});
	    
	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
		//그리드 너비 보다 컬럼 너비가 작으면 width비율로 자동 증가
	    gridView.setDisplayOptions({
			fitStyle: "even"
	    });
		
	}
	
	function dataResult(){
		var formData = $("#searchForm").serialize();
		callLoading(null, "on");
		ajaxCall("/hospPatList.do", formData, false);
	}
	
	function onResult(strUrl,response){
		callLoading(null, "off");
		var resultList;
		if(!isNull(response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/getCommCode.do"){
            $.each(resultList,function(k,v){
                gridValues.push(v.VALUE);
                gridLabels.push(v.LABEL);
            });
            setGrid();
		}
		
		if(strUrl == "/hospPatList.do"){
			gridView.cancel();
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
			gridView.setAllCheck(false);
		}
		
	}
	
 	function insertRow(){
	    var values = [
	    	];
		gridView.commit();
		gridView.setDataSource(dataProvider)
		dataProvider.insertRow(0, values);
		gridView.setCellStyles([0], ["C001CHN"], "style");
		gridView.showEditor();
		gridView.setFocus();
		
	}
 	
 	function saveRow(){
 		var createRows,updateRows;
        gridView.commit();
        
		createRows = dataProvider.getStateRows('created');
		updateRows = dataProvider.getStateRows('updated');
	
		if(createRows != ""){
			CallMessage("203","confirm", "", creatStart);
		}else{
	 		if(updateRows != ""){
				CallMessage("225","confirm", "", updateStart);
			}
		}
 	}
 	
 	function creatStart(){
        var rtnCall = true;
		var createRows = dataProvider.getStateRows('created');
		var createData = dataProvider.getJsonRows(0, (createRows.length-1));
		
		if(createRows != ""){
			for(var i=0;i<createRows.length;i++){		//추가된 데이터 끼리 중복 확인
				for(var j=0; j<i;j++){
					if(createData[i].C001CHN == createData[j].C001CHN){
						CallMessage("280", "alert", ["중복된 차트번호"]);
						return false;
					}
				}
			}
			if(!saveValidation(createRows, 'created')) { return;}
			callLoading(null, "on");
			rtnCall = ajaxListCall("/hospPatSave.do",dataProvider,createRows, createEnd, false); // 등록
		}
 	}
 	
 	function createEnd(rtnCall){
 		
		callLoading(null, "off");
		var	updateRows = dataProvider.getStateRows('updated');
		if(!rtnCall){
			CallMessage("244","alert",["수진자 정보저장"]);		//{0}에 실패하였습니다.
		}else{
			if(updateRows != ""){ 
				dataProvider.setRowState(0, "create", false);
				if(!saveValidation(updateRows, 'updated')) { return;}
				rtnCall = ajaxListCall("/hospPatUdt.do",dataProvider,updateRows, creAupdEnd, false); // 수정
			}else{
				CallMessage("221","alert","",dataResult);
			}
		}
 	}
 	
 	function creAupdEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["수진자 정보수정"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("282","alert","",dataResult);
		}
 	}
 	
 	function updateStart(){
        var rtnCall = true;
		var updateRows = dataProvider.getStateRows('updated');

		if(updateRows != ""){
 			if(rtnCall){
				if(!saveValidation(updateRows, 'updated')) { return;}
 				callLoading(null, "on");
				rtnCall = ajaxListCall("/hospPatUdt.do",dataProvider,updateRows, updateEnd, false); // 수정
			} 
		}
 	}
 	
	function updateEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244","alert",["수진자 정보수정"]);		//{0}에 실패하였습니다.
		}else{
			CallMessage("222","alert","",dataResult);
		}
	}

	function deleteRow() {
		var dataRows;
		dataRows = gridView.getCheckedRows();
		if(dataRows == ""){ 
			CallMessage("243");
			return;
		}
		
		CallMessage("242","confirm", "", deleteStart);
 	}
	
	function deleteStart(){
		var rtnCall =  true;
		var dataRows,strUrl;
		dataRows = gridView.getCheckedRows();
		strUrl = "/hospPatDel.do";
		callLoading(null, "on");
		rtnCall = ajaxListCall(strUrl,dataProvider,dataRows, deleteEnd, false); // 삭제	
	}
	
	function deleteEnd(rtnCall){
		callLoading(null, "off");
		if(!rtnCall){
			CallMessage("244", "alert", ["삭제"]);
		}else{
			CallMessage("223","alert","",dataResult);
		}
	}
	function saveValidation(dataRows, CorU){
		// ID 체크
		var data = [];
		var bool;

		var i = 0;
		do{
			data.push(dataProvider.getJsonRow(dataRows[i]));
			//console.log(dataRows[i]+1);
			bool = validation(data[i], (dataRows[i]+1), CorU);
			if(!bool){
				return bool;
			}else{
				i++;
			}
			
		}while(i < dataRows.length);
		
		return true;
		
	}
	
	function validation(data, num, CorU){
		var validDay = /^\d{4}\-\d{2}\-\d{2}$/;
		var validPhone = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})?([0-9]{3,4})?([0-9]{4})$/;
		
		//CHN
		if(isNull(data.C001CHN)){
			CallMessage("245", "alert", [num+"번째 차트번호를"]);
			return false;
		}
		
		if(CorU == "created"){		// 추가 될 경우
			var chnBool = false;
	    	$.ajax({				// DB데이터와 비교
				 url : "/hospPatChkSave.do"
				, type : "post"
				, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM}
				, dataType: 'json'
				, async: false
				, success : function(response){
					var resultList =  response.resultList;
		            for(var i=0;i<resultList.length;i++){
		            	if(resultList[i].C001CHN == data.C001CHN){//data=추가된 데이터, resultList=기존 데이터
		            		chnBool = true;
		            		break;
		            	}
		            } 
				}
			});
	    	
	    	if(chnBool){
	    		CallMessage("280", "alert", ["중복된 차트번호" + data.C001CHN + "가"]);
	    		return false;
	    	}
	    	
	    /*	var data1 = dataProvider.getJsonRows(0, -1);	// 현재 그리드에 있는 데이터
		    for(var i=0; i<=data1.length-1; i++){
				if(i != num){
					if(data1[i].C001CHN == data.C001CHN){
						CallMessage("245", "alert", [num+"번째 차트번호는 사용중입니다."]);
						return false;
					}
				}
			}  */
	    	
		} 
		if(isNull(data.C001NAM)){
			CallMessage("245", "alert", [num+"번째 수진자 명을"]);
			return false;
		}
		if(isNull(data.C001HPN)){
			CallMessage("245", "alert", [num+"번째 핸드폰 번호를"]);
			return false;
		}
		
		if (!validPhone.test(data.C001HPN)) {
			CallMessage("279", "alert", [num+"번째 핸드폰 번호"]);
			return false;
		}
		
		if(isNull(data.C001BDT)){
			CallMessage("245", "alert", [num+"번째 생년월일을"]);
			return false;
		}
		
 		var dateCur = data.C001BDT.substr(0,4) + "-" + data.C001BDT.substr(4,2) + "-" + data.C001BDT.substr(6,2);
		
		if(!dateCheck(dateCur)){
			CallMessage("279", "alert", [num+"번째 생년월일"]);
		   return false;
		}

		if(isNull(data.C001SEX)){
			CallMessage("245", "alert", [num+"번째 성별을"]);
			return false;
		}
		if(isNull(data.C001AGE)){
			CallMessage("245", "alert", [num+"번째 나이를"]);
			return false;
		}
		return true;
	}
 	
	function dateCheck(input) {
		var validformat = /^\d{4}\-\d{2}\-\d{2}$/; //Basic check for format validity 
		var returnval = false;
		var yearfield,monthfield,dayfield, dayobj;
		
		if (!validformat.test(input)) {
			return returnval;
		} else { //Detailed check for valid date ranges 
			yearfield = input.split("-")[0];
			monthfield = input.split("-")[1];
			dayfield = input.split("-")[2];
			dayobj = new Date(yearfield, monthfield - 1, dayfield);
		}
		try{
			
			if ((dayobj.getMonth() + 1 != monthfield)
				|| (dayobj.getDate() != dayfield)
				|| (dayobj.getFullYear() != yearfield)) {
				if(dayfield=="00"){
					input.val(yearfield+"-"+monthfield+"-0");
				}
				return returnval;
			} else {
				returnval = true;
			}
		}catch (e){
		}
		return returnval;
	}
	
	function exportGrid(){
		var totalCnt = dataProvider.getRowCount();
		if(totalCnt==0){
	    	CallMessage("268","alert");	// 저장하시겠습니까?
			return;
		}
		CallMessage("257","confirm", "", gridExport);
	}
	
	function gridExport(){
		gridView.exportGrid({
		    type: "excel",
		    target: "local",
		    fileName: I_LOGMNM+".xlsx",
		    showProgress: true,
		    applyDynamicStyles: false,
		    progressMessage: "엑셀 Export중입니다.",
		    indicator: "default",
		    header: "visible",
		    lookupDisplay : true,
		    footer: "default",
		    compatibility: "2010",
		    done: function () {  //내보내기 완료 후 실행되는 함수
		    }
		});
	}

	// 조회
	function search(){
 		if(isNull($('#I_FNM').val())){ 		//사원을 로그인시 병원코드 입력후 조회버튼 누를경우
			if(!isNull($('#I_HOS').val())){
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		} 
		if(!searchValidation()){return; }
		dataResult();
	}
	
	function searchValidation(){
		
		if(strByteLength($("#I_CHN").val()) > 15){
			CallMessage("292", "alert", ["차트번호는 15 Byte"], dataClean);
			return false;
		}
		
		if($('#I_HOS').val() == ""){
			CallMessage("245", "alert", ["병원 코드를"],dataClean);
			return false;
		}
/* 		if($('#I_FNM').val() == ""){
			CallMessage("245", "alert", ["병원 이름를"],dataClean);
			return false;
		}  */
		return true;
	}
	
	function openPopup(){
		var gridValus,labelNm,strUrl;
		strUrl = "/hospSearchS.do";
		labelNm = "병원 조회";
		
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,gridValus){
		if(url == "/hospSearchS.do"){
			gridValus = {};
			gridValus["I_HOS"] =  $('#I_HOS').val();
			gridValus["I_FNM"] =  $('#I_FNM').val();
			//console.log(gridValus);
		}
		iframe.gridViewRead(gridValus);
		
	}
	
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		$('#I_HOS').val(data[1].F120PCD);
		$('#I_FNM').val(data[1].F120FNM);
	}
	
	function enterSearch(enter){
		
		if(event.keyCode == 13){
			search();
		}else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
		}
	}
	function dataClean(){
		var data1 = dataProvider.getJsonRows(0, -1);
		dataProvider.removeRows(data1);
	}
	</script>
</head>
<body>
	<div class="content_inner">
		<!-- 검색영역 -->
		<form id="searchForm" name="searchForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>"/>
			<input id="I_AGR" name="I_AGR" type="hidden" value="<%=ss_agr%>"/>
			<div class="container-fluid">
				<div class="con_wrap col-md-12 srch_wrap">
			        <div class="title_area open">
			            <h3 class="tit_h3">검색영역</h3>
			            <a href="#" class="btn_open">검색영역 접기</a>
			        </div>
			        <div class="con_section row">
			            <p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
			             <div class="srch_box">
			                <div class="srch_box_inner">
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">차트번호</label>
									<input type="text" class="srch_put" id="I_CHN" name="I_CHN" maxlength="15" onkeydown="javascript:enterSearch()">
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">수진자 명</label>
									<input type="text" class="srch_put" id="I_NAM" name="I_NAM" maxlength="40" onkeydown="javascript:enterSearch()">
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">성별</label>
									<div class="select_area">
										<select id="I_SEX"  name="I_SEX" class="form-control"></select>
									</div>
								</div>              
			                </div>
							<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
								<div class="srch_item hidden_srch">
									<label for="" class="label_basic">병원</label>
									<input id="I_HOS" name="I_HOS" type="text" class="srch_put srch_hos numberOnly"  value="<%=ss_hos%>" readonly="readonly" onclick="javascript:openPopup()" maxlength="5" onkeydown="javasscript:enterSearch()"/>
									<input type="text" class="hos_pop_srch_ico srch_put" id="I_FNM" name="I_FNM" maxlength="40" readonly="readonly" onclick="javascript:openPopup()">
									<!-- <button type="button" class="btn btn-red btn-sm" title="검색" onClick="javascript:openPopup()"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button> -->
								</div>
							</div> 
			                
			                <div class="btn_srch_box">
			                    <button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
			                </div> 
			            </div>
			        </div>
			    </div>
			</div>
		</form>
        <!-- 검색영역 end -->
               
		<div class="container-fluid">
			<div class="con_wrap col-md-12  col-sm-12">
				<div class="title_area">
					<h3 class="tit_h3">수진자 정보 관리</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:insertRow()"style="display:none" id="hospRes">추가</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()"style="display:none" id="hospAdd">저장</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow()"style="display:none" id="hospDel">삭제</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button>
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
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>병원 조회</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	
	<style type="text/css">
		.label_basic{width: 100px;}
	</style>
	
	<script>
	var gridView;
	var dataProvider;
    var gridValues = [];
    var gridLabels = [];
    
    var agr_cd = '<%=ss_agr%>'; 
    
    function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		search();
	        }
	    }
	}
	
	$(document).ready( function(){

		var resultCode = "";
		
		if(agr_cd == "BRC_MNG" || agr_cd == "HOSP_NMG" || agr_cd == "TEST"){
			resultCode = getCode(I_LOGMNU, I_LOGMNM, "USER_DIV_HOS",'Y');
		}else{
			resultCode = getCode(I_LOGMNU, I_LOGMNM, "USER_DIV",'Y');
		}
		
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
		 	setGrid();
	});

	function search(){
		dataResult();
	}
	function getGridCode(){
		var formData = $("#searchForm").serializeArray();
		formData +="&I_PSCD=AUTH";
		ajaxCall("/getCommCode.do", formData);
	}
	function dataResult(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/sysUserMngList.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/sysUserMngList.do"){
			var resultList =  response.resultList;
			dataProvider.setRows(resultList);		
			gridView.setDataSource(dataProvider);
		}
	}
	function setGrid(){
	    dataProvider = new RealGridJS.LocalDataProvider();
	    gridView = new RealGridJS.GridView("realgrid");
	    gridView.setDataSource(dataProvider);
	    setStyles(gridView);
	    setGridBar(gridView,false);
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
				, {fieldName:"S005SNM"}		//SMS사용권한
	    	];
	    dataProvider.setFields(fields);
	    
	    var columnStyles_USERDIV = [{
	        criteria: "values['USERDIV'] = 'C'",
	        styles: "fontBold=true"
	    }];
	    var columns=[
	    	  {name:"USDNM",		fieldName:"USDNM",		header:{text:"병원/사원"},		width:100 }
		    	, {name:"LOGLID",		fieldName:"LOGLID",		header:{text:"아이디"},		width:120, cursor:"pointer", renderer:{type: "link", url:"#",   requiredFields: "LOGLID", showUrl: false}, dynamicStyles: columnStyles_USERDIV}
		    	, {name:"LOGHOS",		fieldName:"LOGHOS",		header:{text:"병원코드"},		width:80}
		    	, {name:"S005ANM",		fieldName:"S005ANM",	header:{text:"사용자권한"},	width:120}
		    	, {name:"S005SNM",		fieldName:"S005SNM",	header:{text:"SMS사용권한"},	width:100}
		    	, {name:"F120FNM",		fieldName:"F120FNM",	header:{text:"병원명(사용자)"},	width:150 }
		    	, {name:"F120GIC",		fieldName:"F120GIC",	header:{text:"요양기관코드"},	width:100,  styles: {textAlignment: "center"} }
		    	, {name:"LOGTEL",		fieldName:"LOGTEL",		header:{text:"연락처"},		width:120 }
		    	, {name:"LOGEML",		fieldName:"LOGEML",		header:{text:"대표 이메일"},	width:150 }
		    	, {name:"LOGWNO",		fieldName:"LOGWNO",		header:{text:"대표자명"},		width:120 }
		    	, {name:"LOGGNO",		fieldName:"LOGGNO",		header:{text:"대표자 면허 번호"},width:140 ,  styles: {textAlignment: "center"}}
		    	, {name:"LOGSNO",		fieldName:"LOGSNO",		header:{text:"사업자번호"},	width:110 ,  styles: {textAlignment: "center"}}
		    	, {name:"S005DRS",		fieldName:"S005DRS",	header:{text:"원장출신학교"},	width:130 }
		    	, {name:"S005HPH",		fieldName:"S005HPH",	header:{text:"병원홈페이지"},	width:150 }
		    	, {name:"S005ITM",		fieldName:"S005ITM",	header:{text:"전문진료과목"},	width:120 }
		    	, {name:"S005POS",		fieldName:"S005POS",	header:{text:"우편번호"},		width:80 }
		    	, {name:"S005JAD1",		fieldName:"S005JAD1",	header:{text:"지번주소"},		width:120 }
		    	, {name:"S005DAD1",		fieldName:"S005DAD1",	header:{text:"도로명주소"},	width:130 }
		    	, {name:"S005DAD2",		fieldName:"S005DAD2",	header:{text:"나머지주소"},	width:150 }

	    ];


	    //컬럼을 GridView에 입력 합니다.
	    gridView.setColumns(columns);
	    
	    gridView.onDataCellClicked = function (grid, index) {
	        var itemIndex = index.itemIndex;
			var gridValus = gridView.getValues(itemIndex);

			if( index.column=="LOGLID"){
				if( gridValus["USERDIV"]=="C"){
			    	openPopup(index);
				}else{
					CallMessage("284");	// 씨젠 사원의 상세정보는 확인할 수 없습니다.
				}
			}
	    };
		$("#SearchBtn").click(function(){
			search();
		});
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
	function openPopup(index){
		
		var gridValus,labelNm;

        var itemIndex = index.itemIndex;
		gridValus = gridView.getValues(itemIndex);
		labelNm = "사용자 정보 조회";
		//fnOpenPopup(url,labelNm,gridValus,callback)
		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup("/sysUserInfo01.do",labelNm,gridValus,callFunPopup);
	}
	/*callback 호출 */
	function callFunPopup(url,iframe,gridValus){
		if(url="/sysUserInfo01.do"){
			if(!isNull(gridValus)){
				iframe.gridViewRead(gridValus);
			}
		}
	}

	

	
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
							<input type="text" class="srch_put engNum" maxlength="12"  id="I_SERID" name="I_SERID" onkeydown="return enterSearch(event)" >
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
				<h3 class="tit_h3">병원(회원) 조회</h3>
				<!-- 2020.05.07 - 병원 조회 메뉴에서는 엑셀 다운 기능 제외 -->
				<!-- <div class="tit_btn_area">
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:exportGrid()">엑셀다운</button>
				</div> -->
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


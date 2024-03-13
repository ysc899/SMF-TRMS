<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>의뢰내역 접수 (OCS)</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	 
	<%@ include file="/inc/common.inc"%>
	
	<script>
	var gridView1,dataProvider1,gridView2,dataProvider2;
    var RCP_YNValues = [];
    var RCP_YNLabels = [];


	function enterSearch(e){	
	    if (e.keyCode == 13) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName == "INPUT" ){
	    		search();
	        }
	    }else{
			var target = event.target || event.srcElement;
			if(target.id == "I_HOS"){
				$('#I_FNM').val('');
			}
	    }
	}
	$(document).ready( function(){
	    $('#I_FNM').change(function(){
	    	$('#I_HOS').val('');
	    });
		if($('#I_ECF').val() == "E"){
	    	$('#hospHide').show();
	    }
		
		getGridCode("RCP_YN");
	    resultCode = getCode(I_LOGMNU, I_LOGMNM, "RCP_YN", "Y");
		$('#I_RCP').html(resultCode);
		jcf.replace($("#I_RCP"));

		//달력 from~to
		$("#I_FDT").datepicker({
			dateFormat: 'yy-mm-dd',
			maxDate: $('#I_TDT').val(),
			onSelect: function(selectDate){
				$('#I_TDT').datepicker('option', {
					minDate: selectDate,
				});
			}
		});	
		$("#I_TDT").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: $('#I_FDT').val(),
			onSelect : function(selectDate){
				$('#I_FDT').datepicker('option', {
					maxDate: selectDate,
				});
			}		
		});
		$(".fr_date").datepicker('setDate',new Date());

  		<%
	  		Map<String, Object> QuickParam = new HashMap<String, Object>();
	  		if(paramList.size()>0){
	  			for(int idx=0;idx<paramList.size();idx++)
	  			{
	  				QuickParam = paramList.get(idx);
	  				if(!"".equals(QuickParam.get("S008VCD"))){
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
	  		}
	  	%>
		setGrid2();
		$(".btn_srch").click(function(){
			search();
		});
	});
	function getGridCode(PSCD){
		var formData = $("#searchForm").serialize();
		formData +="&I_PSCD="+PSCD;
		ajaxAsyncCall("/getCommCode.do", formData);
	}
	
	function setGrid1(){
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    setStyles(gridView1);
	    setGridBar(gridView1, false,"state");

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
		
	    var fields1=[
		    	{fieldName:"R002COR"}	//회사코드
		    	, {fieldName:"R002HOS"}	//병원코드
		    	, {fieldName:"R002HNM"}	//병원명
		    	, {fieldName:"GRIDDAT", dataType: "datetime", datetimeFormat: "yyyyMMddHHmmss"}	   	//업로드 일자
		    	, {fieldName:"R002UPDT"}	   	//업로드 일자
		    	, {fieldName:"R002FSQ", dataType: "number"}	//파일순번
		    	, {fieldName:"R002FPT"}	//파일 경로
		    	, {fieldName:"R002FNM"}	//파일 명
		    	, {fieldName:"R002FSNM"} //파일 저장 명
		    	, {fieldName:"RCP_YN"} //접수여부
		    	, {fieldName:"DOWN"} //파일 저장 명
		    	, {fieldName:"UPSTS"} //파일 저장 명
				, {fieldName:"I_LOGMNU"}
				, {fieldName:"I_LOGMNM"}
				, {fieldName:"I_HOS"}
				, {fieldName:"I_UPDT"}
				, {fieldName:"I_FSQ"}
				, {fieldName:"I_STS"}
		    	];
				
	    
	    dataProvider1.setFields(fields1);

	    var columns1=[
				    {name:"RCP_YN",	fieldName:"RCP_YN",	header:{text:"접수여부"},		width:70,lookupDisplay:true, values:RCP_YNValues, labels:RCP_YNLabels,   styles: {textAlignment:"center"}}
				   , {name:"GRIDDAT",	fieldName:"GRIDDAT", 	renderer:{showTooltip: true}, header:{text:"업로드 일자"},	width:130,   styles: {"datetimeFormat": "yyyy-MM-dd HH:mm:ss",textAlignment:"center"}}
				   , {name:"R002FNM",	fieldName:"R002FNM",	header:{text:"파일 명"},		width:170}
				   , {name:"UPSTS",	fieldName:"UPSTS",	header:{text:"업로드여부"},		width:80, styles: {textAlignment:"center"}}
			       , {name:"DOWN", fieldName: "DOWN", width:80, styles: {contentFit: "auto", iconLocation:"center"}, cursor: "pointer",   
			    	   	renderer:{showTooltip: false, type:"multiIcon", renderCallback:function(grid, index){
					          var prf = [];
				              prf.push('../images/common/btn_download2.png');
					          return prf
					      }}, header: {text: "다운로드"}}
	    ];
	    gridView1.setColumns(columns1);
	 	gridView1.onDataCellClicked = function (grid, index) {
			$("#I_UPDT").val(grid.getValue(index.itemIndex, "R002UPDT"));
			$("#I_FSQ").val(grid.getValue(index.itemIndex, "R002FSQ"));
			
			var path = grid.getValue(index.itemIndex, "R002FPT");
			var fileNm = grid.getValue(index.itemIndex, "R002FNM");
			
			if( index.column=="DOWN"){
	        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+path+"&file_name="+encodeURIComponent(fileNm);
			}else{
		 		dataResult2();
			}
	 		
		};
// 	    dataResult1();
	}
	
	function setGrid2(){
	    dataProvider2 = new RealGridJS.LocalDataProvider();
	    gridView2 = new RealGridJS.GridView("realgrid2");
	    gridView2.setDataSource(dataProvider2);
	    
	    setStyles(gridView2);
	    setGridBar(gridView2, false);

	    var fields2=[
			    	{fieldName:"R002COR"}	//회사코드
			    	, {fieldName:"R002DAT",dataType: "datetime", datetimeFormat: "yyyyMMdd"}	   	//씨젠 접수일자
			    	, {fieldName:"R002JNO", dataType: "number"}	//씨젠 접수번호
			    	, {fieldName:"R002GCD"}	//씨젠 검사코드
			    	, {fieldName:"R002HOS"}	//병원코드
			    	, {fieldName:"R002UPDT"} //업로드 일자
			    	, {fieldName:"R002FSQ"}	//파일순번
			    	, {fieldName:"R002RNO"}	//Row 번호
			    	, {fieldName:"R002001"}	//내원번호
			    	, {fieldName:"R002002"}	//외래구분
			    	, {fieldName:"R002003"}	//의뢰일자
			    	, {fieldName:"R002004"}	//검체번호
			    	, {fieldName:"R002005"}	//처방번호
			    	, {fieldName:"R002006"}	//처방코드
			    	, {fieldName:"R002007"}	//처방명
			    	, {fieldName:"R002008"}	//검체명
			    	, {fieldName:"R002009"}	//일련번호
			    	, {fieldName:"R002010"}	//검체코드
			    	, {fieldName:"R002011"}	//여유필드
			    	, {fieldName:"R002012"}	//차트번호
			    	, {fieldName:"R002013"}	//수신자명
			    	, {fieldName:"R002014"}	//주민번호
			    	, {fieldName:"R002015"}	//나이
			    	, {fieldName:"R002016"}	//성별
			    	, {fieldName:"R002017"}	//과명
			    	, {fieldName:"R002018"}	//병동
			    	, {fieldName:"R002019"}	//참고사항
			    	, {fieldName:"R002020"}	//검사파트
			    	, {fieldName:"R002021"}	//의뢰기관
			    	, {fieldName:"R002022"}	//처방일자
			    	, {fieldName:"R002023"}	//혈액형
			    	, {fieldName:"R002024"}	//진료의사
			    	, {fieldName:"R002044"}	//생년월일
			    	, {fieldName:"R002045"}	//프로파일
					,{fieldName:"I_LOGMNU"}
					,{fieldName:"I_LOGMNM"}
		    	];
				
	    
	    dataProvider2.setFields(fields2);

	    var columns2=[
		    	, {name:"R002DAT",	fieldName:"R002DAT",	header:{text:"접수일자"},	width:80,  styles: {"datetimeFormat": "yyyy-MM-dd",textAlignment:"center"}}
				, {name:"R002JNO",	fieldName:"R002JNO",	header:{text:"접수번호"},	width:80,  styles: {textAlignment: "far"} }
			    , {name:"R002012",	fieldName:"R002012",	header:{text:"차트번호"},	width:80}
			    , {name:"R002013",	fieldName:"R002013",	header:{text:"수진자명"},	width:80}
			    , {name:"R002016",	fieldName:"R002016",	header:{text:"성별"},		width:70,  styles: {textAlignment: "center"}}
		    	, {name:"R002006",	fieldName:"R002006",	header:{text:"처방코드"},	width:140}
		    	, {name:"R002007",	fieldName:"R002007",	header:{text:"검사명"},renderer:{showTooltip: true},	width:140}
		    	, {name:"R002004",	fieldName:"R002004",	header:{text:"검체번호"},	width:80}
		    	, {name:"R002021",	fieldName:"R002021",	header:{text:"의뢰기관"},	width:150}

	    ];
	    
	    
	    gridView2.setColumns(columns2);
	    // 툴팁 기능
	    gridView2.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        if (column == "F018OCD") {
	            tooltip = value;
	        }
	        return tooltip;
	    }	    
	}
	//조회
	function search(){
		
		var sDate = new Date($("#I_FDT").val());
		var eDate = new Date($("#I_TDT").val());
		
		// 유효성검사 - 최대 검색 기간 체크 !!
		var now = new Date();
	    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
			CallMessage("301","alert",["10"]);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
			return ;
	   	}
		
		//병원코드 입력 후 조회 할 경우
 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
			if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
				var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
				$('#I_FNM').val(I_FNM);
			}
		}
		$("#I_UPDT").val("0");
		$("#I_FSQ").val("0");
		dataResult1();
	}
	
	function dataResult1(){
 		if($('#I_HOS').val() == ""){
 			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
 			return false;
 		}
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/ocsUploadList.do", formData);
	}

	// ajax 호출 result function
	function onResult(strUrl,response){
		var resultList;
	 	var msgCd = response.O_MSGCOD;
		if(!isNull( response.resultList)){
			resultList = response.resultList;
		}
		
		if(strUrl == "/getCommCode.do"){
			var I_PSCD = "";
            $.each(resultList,function(k,v){
            	I_PSCD = response.I_PSCD;
            	if(response.I_PSCD =="RCP_YN"){
            		RCP_YNValues.push(v.VALUE);
	                RCP_YNLabels.push(v.LABEL);
            	}
            });
            setGrid1();
		}
		
		if(strUrl == "/ocsUploadList.do"){
	 		gridView1.setAllCheck(false);
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
			if(!isNull(resultList))
			{
				$("#I_UPDT").val(gridView1.getValue(0, "R002UPDT"));
				$("#I_FSQ").val(gridView1.getValue(0, "R002FSQ"));
				
			}
			dataResult2();
		}
		else if(strUrl == "/ocsUploadDtl.do"){
			dataProvider2.setRows(resultList);		
			gridView2.setDataSource(dataProvider2);
			TitView();
		}
		else if(strUrl == "/ocsUploadDel.do"){
			CallMessage(msgCd,"alert","",search);	//삭제를 완료하였습니다.
		}
	}
	function dataResult2(){
		var formData = $("#searchForm").serializeArray();
		ajaxCall("/ocsUploadDtl.do", formData);
	}
	

	function openPopup(index)
	{
		var labelNm,strUrl,gridValus;
		if(index == 1){
			strUrl = "/hospSearchS.do";
			labelNm = "병원 조회";
		}else{
			//병원코드 입력 후 조회 할 경우
	 		if(isNull($('#I_FNM').val())){ 		//병원 이름이 없을경우
				if(!isNull($('#I_HOS').val())){		//병원코드가 있을 경우
					var I_FNM = getHosFNM(I_LOGMNU, I_LOGMNM, $('#I_HOS').val());
					$('#I_FNM').val(I_FNM);
				}
			}
	 		if($('#I_HOS').val() == ""){
	 			CallMessage("245", "alert", ["병원 이름을 검색하여 병원 코드를"]);
	 			return false;
	 		}
			strUrl = "/ocsUpload01.do";
			labelNm = "의뢰 접수";
		}
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	function callFunPopup(url,iframe,setValus){
		if(url == "/hospSearchS.do"){
			setValus = {};
			setValus["I_HOS"] =  $('#I_HOS').val();
			setValus["I_FNM"] =  $('#I_FNM').val();
			iframe.gridViewRead(setValus);
		}
		if(url == "/ocsUpload01.do"){
			setValus = {};
			setValus["I_HOS"] =  $('#I_HOS').val();
			setValus["I_FNM"] =  $('#I_FNM').val();
			iframe.onPopup(setValus);
		}
	}
	
	/*close 호출*/
	function fnCloseModal(obj){
		var data = [];
		for(var x in obj){
			data.push(obj[x]);
		}
		if(data[0] == "/hospSearchSList.do"){
			$('#I_HOS').val(data[1].F120PCD);
			$('#I_FNM').val(data[1].F120FNM);
	    }
		if(data[0] == "/ocsUpload01.do"){

			search();
		}
	}
	


	/* 의뢰목록관리 삭제 */
	function rowDelete(flag){
		
		var dataRows = gridView1.getCheckedItems();
		if(dataRows == ""){ 
			CallMessage("243");			//선택내역이 없습니다.
			return;
		}
		CallMessage("239","confirm","",fnDelete);	//정말 삭제하시겠습니까?
	}
	/*삭제 call */
	function fnDelete(){
		var jsonRow = [];
		var	dataRows = null;
		var url;
		dataRows = gridView1.getCheckedItems();
		url = "/ocsUploadDel.do";
	    
		if(dataRows != ""){ 
			var setValue = {
					I_LOGMNU: I_LOGMNU
					, I_LOGMNM:I_LOGMNM
				};
			$.each(dataRows, function(i, e) {
				
				
				setValue = {
						I_LOGMNU: I_LOGMNU
						, I_LOGMNM:I_LOGMNM
						, I_HOS:gridView1.getValue(e, "R002HOS")
						, I_UPDT:gridView1.getValue(e, "R002UPDT")
						, I_FSQ:gridView1.getValue(e, "R002FSQ")// 승인
						, I_STS:"Y"// 승인
					};
				dataProvider1.updateStrictRow(e, setValue);
			});
			
 			$.each(dataRows, function(i, e) {
				jsonRow.push(dataProvider1.getJsonRow(e));
 			});
 			var param = "I_LOGMNU="+I_LOGMNU+"&I_LOGMNM="+I_LOGMNM+"&JSONROW="+encodeURIComponent(JSON.stringify(jsonRow));
			ajaxCall3(url,param); // 수정
		}
	}
	
	function TitView(){
		var I_TITVIEW = $("#I_TITVIEW").prop("checked");
		
		if(I_TITVIEW){
			dataProvider2.hideRows([0]);						// 보여줄 데이터 마지막 값 숨김
		}else{
		    dataProvider2.showHiddenRows([0]);
		}
	}
	</script>
</head>
<body>
	
<div class="content_inner">
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"value="<%=menu_cd%>"  />
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>" />
		<input id="I_SERDAT" name="I_SERDAT" type="hidden" value="0"/>
		<input id="I_SERJNO" name="I_SERJNO" type="hidden" value="0"/>
		<input id="I_UPDT" name="I_UPDT" type="hidden"/>
		<input id="I_FSQ" name="I_FSQ" type="hidden"/>
		<!-- 검색영역 -->
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
							<div class="srch_item srch_item_v5">
								<label for="fr_date" class="label_basic">접수일</label>
								<div class="btn_group">
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('1D')">1일</button>
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('3D')">3일</button>
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('1M')">1개월</button>
									<button type="button" class="btn btn-white" onClick="javascript:changeDT('3M')">3개월</button>
								</div>
								<span class="period_area">
									 <label for="startDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date startDt calendar_put" id="I_FDT" name="I_FDT"  >
								</span>
								~
								<span class="period_area">
									 <label for="endDt" class="blind">날짜 입력</label>
									 <input type="text" class="fr_date calendar_put" id="I_TDT" name="I_TDT"  >
								</span>
							</div>
								
							<div class="srch_item srch_item_v4">
								<label for="" class="label_basic">접수여부</label>
								<div class="select_area">
									<select id="I_RCP" name="I_RCP" class="form-control"></select>
								</div>
							</div>
						</div>
						
						<div class="srch_box_inner m-t-10" id="hospHide" style="display:none"><!-- [D] 사용자가 로그인하면 활성화 고객사용자는 비활성화 -->
							<div class="srch_item srch_item_v5 hidden_srch">
								<label for="" class="label_basic">병원</label>
								<input id="I_HOS" name="I_HOS" type="text"  value="<%=ss_hos%>" readonly="readonly" onClick="javascript:openPopup('1')" maxlength="5" onkeydown="return enterSearch(event)" class="numberOnly"/>
	                             <input type="text" class="srch_put hos_pop_srch_ico " id="I_FNM" name="I_FNM" readonly="readonly" onClick="javascript:openPopup('1')"/>
								<%-- <input id="I_HOS" name="I_HOS" type="hidden" value="<%=ss_hos%>" /> --%>
								<input id="I_ECF" name="I_ECF" type="hidden" value="<%=ss_ecf%>" />
							</div>
						</div>
					    
					    <div class="btn_srch_box">
							<button type="button" class="btn_srch">조회</button>
					    </div> 
					</div>
			    </div>
			</div>
	    </div>
		</form>
		<!-- 검색영역 end -->
	    
		<div class="container-fluid">
		    <div class="con_wrap col-md-5 col-sm-12">
			<div class="title_area">
			    <h3 class="tit_h3">의뢰 접수</h3>
				<div class="tit_btn_area">
					<button type="button" class="btn btn-default btn-sm"  onClick="javascript:openPopup('2')">업로드</button>
					<button type="button" class="btn btn-default btn-sm" onClick="javascript:rowDelete()">삭제</button>
				</div>
			</div>
			<div class="con_section overflow-scr">
			    <div class="tbl_type">
					<div id="realgrid1"  class="realgridH"></div>
			    </div>
			</div>
		    </div>
		    
		    <div class="con_wrap col-md-7 col-sm-12">
			<div class="title_area">
			    <h3 class="tit_h3">접수 검사 항목</h3>
				<div class="tit_btn_area">
				    <input type="checkbox" id="I_TITVIEW" name="I_TITVIEW"  onClick="TitView();" />
				  	<label class="btnLabel" for="I_TITVIEW" onClick="TitView();" >타이틀 숨기기</label>
				</div>
			</div>
			<div class="con_section overflow-scr">
			    <div class="tbl_type">
					<div id="realgrid2"  class="realgridH"></div>
			    </div>
			</div>
		    </div>
		</div>
			       
	</div>    
	<!-- content_inner -->    
	<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>   	
</body>
</html>



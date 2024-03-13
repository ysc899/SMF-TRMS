<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>항목별 결과 양식 관리  </title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	<script>
		var pmcd = "";
		var resultCode = "";
	    var yesNoValues = [];
	    var yesNoLabels = [];
	    function enterSearch(e){	
		    if (e.keyCode == 13) {
		    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
		    	if(e.target.nodeName == "INPUT" ){
		    		dataResult();
		        }
		    }
		}
		var gridView,dataProvider;
		$(document).ready( function(){
			var resultCode = getCode(I_LOGMNU, I_LOGMNM, "RPT_FORM",'Y');	//폼
			$('#I_RFM').html(resultCode);
	  		jcf.replace($("#I_RFM"));
			var resultCode = getCode(I_LOGMNU, I_LOGMNM, "RPT_CLS",'Y');	//양식지
			$('#I_RCL').html(resultCode);
	  		jcf.replace($("#I_RCL"));
			var resultCode = getCode(I_LOGMNU, I_LOGMNM, "RPT_TYPE",'Y');	//결과지
			$('#I_RTP').html(resultCode);
	  		jcf.replace($("#I_RTP"));
			var resultCode = getCode(I_LOGMNU, I_LOGMNM, "YES_NO",'Y');		//사용여부
			$('#I_STS').html(resultCode);
			$('#I_STS').val("Y");
	  		jcf.replace($("#I_STS"));

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
			getGridCode("YES_NO");	//단일출력여부

			//RPT_FORM
			$(".btn_srch").click(function(){
				dataResult();
			});
		});
		function getGridCode(PSCD){
			var formData = $("#searchForm").serialize();
			formData +="&I_PSCD="+PSCD;
			ajaxCall("/getCommCode.do", formData);
		}
		
		function dataResult(){
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysTestRptList.do", formData);
		}
	
		// ajax 호출 result function
		function onResult(strUrl,response){
			var resultList;
			if(!isNull( response.resultList)){
				resultList = response.resultList;
			}
			if(strUrl == "/getCommCode.do"){
				var I_PSCD = "";
	            $.each(resultList,function(k,v){
	            	I_PSCD = response.I_PSCD;
	            	if(response.I_PSCD =="YES_NO"){
	            		yesNoValues.push(v.VALUE);
		                yesNoLabels.push(v.LABEL);
	            	}
	            });
				setGrid();
			}
			
			
			if(strUrl == "/sysTestRptList.do"){
		    	gridView.cancel();
				dataProvider.setRows(resultList);		
				gridView.setDataSource(dataProvider);
// 				callLoading("/sysTestRptList.do","off");
			}
			if(strUrl == "/sysTestRptSave.do"){
				var msgcod =response.O_MSGCOD;
				if(msgcod != "200"){
					CallMessage("244","alert",["항목별 결과 양식 저장"]);		//{0}에 실패하였습니다.
				}else{
					CallMessage("221","alert","",dataResult);	//저장을 완료하였습니다.
				}
			}
		}
		/*항목별 결과 양식 그리드 설정*/
		function setGrid(){
		    dataProvider = new RealGridJS.LocalDataProvider();
		    gridView = new RealGridJS.GridView("realgrid");
		    gridView.setDataSource(dataProvider);
		    setStyles(gridView);
		    setGridBar(gridView, false,"check");
		    
		    gridView.setEditOptions({
		    	insertable:false,
		    	deletable:false,
		    	editable:true,
		    	readOnly:false
		    });
	
			var fields = [  
				 {fieldName:"RCLNM"}
				,{fieldName:"RTPNM"}
				,{fieldName:"RFMNM"}
				,{fieldName:"F010GCD"}
				,{fieldName:"F010FKN"}
				,{fieldName:"S018TIT"}
				,{fieldName:"S018RGN"}
				,{fieldName:"S018RFN"}
				,{fieldName:"S018SYN"}
				,{fieldName:"F010COR"}
				,{fieldName:"I_GCD"}
				,{fieldName:"I_TIT"}
				,{fieldName:"I_RGN"}
				,{fieldName:"I_SYN"}
				,{fieldName:"I_RFN"}
				,{fieldName:"F010STS"}
				,{fieldName:"I_LOGMNU"}
				,{fieldName:"I_LOGMNM"}
			];
		    dataProvider.setFields(fields);
		    var columnEditor =  {type :"dropDown" , dropDownPosition:"button", "domainOnly": true}
		    
	
		    var columns = [  
		    	  {name:"RCLNM",  	 fieldName:"RCLNM",  	 header:{text:"양식지"},		width:80,  	editable:false}
			    , {name:"RTPNM",  	 fieldName:"RTPNM", 	 header:{text:"결과지 타입"},	width:150,  editable:false}
			    , {name:"RFMNM",  	 fieldName:"RFMNM",  	 header:{text:"폼"},			width:80,  	editable:false}
			    , {name:"F010GCD",   fieldName:"F010GCD",    header:{text:"검사코드"},		width:80,  	editable:false, styles: {textAlignment:"center"}}
			    , {name:"F010FKN",   fieldName:"F010FKN",    header:{text:"검사항목"},		width:200,  editable:false}
			    , {name:"F010STS",   fieldName:"F010STS",    header:{text:"사용여부"},		width:80,  editable:false,lookupDisplay:true,	 values:yesNoValues,labels:yesNoLabels, styles: {textAlignment:"center"}}
			    , {name:"I_SYN",   fieldName:"I_SYN",    header:{text:"단일 출력 여부", subText :"*", subTextLocation :"left", subTextGap : 2, subStyles : {foreground :"#ffff0000"}},		width:100,  editable:true,	lookupDisplay:true, values:yesNoValues,labels:yesNoLabels, editor:columnEditor, styles: {textAlignment:"center"}}
			    , {name:"I_RFN",   fieldName:"I_RFN",    header:{text:"리포트 파일", 	subText :"*", subTextLocation :"left", subTextGap : 2, subStyles : {foreground :"#ffff0000"}},		width:200,  editable:true}
			    , {name:"I_TIT",   fieldName:"I_TIT",    header:{text:"타이틀"},		width:200,  editable:true}
			    , {name:"I_RGN",   fieldName:"I_RGN",    header:{text:"검사명"},		width:200,  editable:true}
		    ];
		    gridView.setColumns(columns);
		    gridView.setPasteOptions({ fillColumnDefaults:true });
		    gridView.setColumnProperty("I_SYN", "editButtonVisibility", "always");

			gridView.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
			    var v = grid.getValue(itemIndex, field);
				 grid.setValue(itemIndex, "I_LOGMNU",I_LOGMNU);
				 grid.setValue(itemIndex, "I_LOGMNM",I_LOGMNM);
			};
// 			dataResult();
		}
		function EditChange(grid, index, value){
			if (value) {
				var onEditChange = grid.onEditChange;
				grid.onEditChange = null; // 무한 루프로 인한 이벤트 제거
				grid.setEditValue(value.replace(/[/^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,""))
				grid.onEditChange = onEditChange; // 이벤트 재설정
			}
		}
		/** 그리드 호출 **/
		
		function saveValidation(dataRows)
		{
			var rtnValid = false;
			var focusCell = gridView.getCurrent();
			if(dataRows != ""){
				$.each(dataRows, function(i, e) {
		 			if(isNull(dataProvider.getValue(e, "I_SYN"))){
		 				CallMessage("201","alert",["단일 출력 여부"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "I_SYN";
		 				focusCell.fieldName = "I_SYN";
		 				gridView.setCurrent(focusCell);
		 				gridView.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
		 			if(isNull(dataProvider.getValue(e, "I_RFN"))){
		 				CallMessage("201","alert",["리포트 파일"]);				//{0} 필수 입력입니다.
		 				focusCell.itemIndex = e;
		 				focusCell.column = "I_RFN";
		 				focusCell.fieldName = "I_RFN";
		 				gridView.setCurrent(focusCell);
		 				gridView.showEditor();
		 				rtnValid = true;
		 				return false;
		 			}
				});
			}
			return rtnValid;
		}
		
		/* 항목별 결과 양식 저장 */
		function saveRow(){
		    //항목별 결과 양식 추가, 수정
			gridView.commit();	
			var	updateRows = dataProvider.getStateRows('updated');
			var valid =  saveValidation(updateRows);
			if(!valid){
				CallMessage("203","confirm","",fnSave);	//저장 하시겠습니까?
			}
		}
		function fnSave(rtnCall){

			var jsonRow = [];
			var	updateRows = dataProvider.getStateRows('updated');
			if(updateRows != ""){ 
	 			$.each(updateRows, function(i, e) {
					jsonRow.push(dataProvider.getJsonRow(e));
	 			});
	 			
	 			var param = "I_LOGMNU="+I_LOGMNU+"&I_LOGMNM="+I_LOGMNM+"&JSONROW="+encodeURIComponent(JSON.stringify(jsonRow));
				ajaxCall3("/sysTestRptSave.do",param); // 수정
			}
		}

		function ListSaveEnd(rtnCall){
			if(!rtnCall){
				CallMessage("244","alert",["항목별 결과 양식 저장"]);		//{0}에 실패하였습니다.
			}else{
				dataResult();
			}
		}
		/* 항목별 결과 양식 저장 */
		
		
		function setS018RFN(){
			gridView.commit();	
			var updateArr = [];
			var totalCnt = dataProvider.getRowCount();
			var updateCol = {
					S018RFN	:$("#REPORT_NM").val()
					, I_RFN	:$("#REPORT_NM").val()
					, I_LOGMNU:I_LOGMNU
					, I_LOGMNM:I_LOGMNM
				};
			for( var i = 0 ;i<totalCnt ; i++){
				updateArr.push(updateCol);
			}
		    dataProvider.updateStrictRows(0, updateArr, 0, totalCnt, false);
		}
	</script>
</head>
<body>
	<div class="content_inner">
		<!-- 검색영역 -->
		<div class="container-fluid">
			<div class="con_wrap col-md-12 srch_wrap">
				<div class="title_area open">
					<h3 class="tit_h3">검색영역</h3>
					<a href="#" class="btn_open">검색영역 접기</a>
				</div>
				<div class="con_section row">
					<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
					<form id="searchForm" name="searchForm">
						<input id="I_LOGMNU" 	name="I_LOGMNU" 	type="hidden"/>
						<input id="I_LOGMNM" 	name="I_LOGMNM" 	type="hidden"/>
						<div class="srch_box">
							<div class="srch_box_inner">
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">양식지</label>
									<div class="select_area">
									<select class="form-control"  id="I_RCL"  name="I_RCL"></select> 
									</div>
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">결과지</label>
									<div class="select_area">
									<select class="form-control"  id="I_RTP"  name="I_RTP"></select> 
									</div>
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">폼</label>
									<div class="select_area">
									<select class="form-control"  id="I_RFM"  name="I_RFM"></select> 
									</div>
								</div>
							</div>  
					
							<div class="srch_box_inner m-t-10">
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">검사코드</label>
									<input  type="text"  class="srch_put"  id="I_GCD" name="I_GCD" onkeydown="return enterSearch(event)" >
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">검사항목</label>
									<input  type="text"  class="srch_put"  id="I_FKN" name="I_FKN" onkeydown="return enterSearch(event)" >
								</div>
								<div class="srch_item srch_item_v2">
									<label for="" class="label_basic">사용여부</label>
									<div class="select_area">
										<select class="form-control" id="I_STS"  name="I_STS"></select> 
									</div>
								</div>
							
							</div>
							<div class="btn_srch_box">
								<button type="button" class="btn_srch">조회</button>
							</div> 
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 검색영역 end -->
		<div class="container-fluid">
            <div class="con_wrap">
				<div class="title_area">
					<h3 class="tit_h3">항목별 결과 양식</h3>
					<div class="tit_btn_area">
						<label for="" class="label_basic">리포트 파일 명</label>
						<input type="text" class="srch_put" id="REPORT_NM" placeholder="">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:setS018RFN()">모두적용</button>
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()">저장</button>
					</div>
				</div>
	            <div class="con_section overflow-scr">
                    <div class="tbl_type">
                    	<div id="realgrid"  class="realgridH" ></div>
                    </div>
                </div>
			</div>
		</div>
	</div>    
	<!-- content_inner -->       	
	
</body>
</html>
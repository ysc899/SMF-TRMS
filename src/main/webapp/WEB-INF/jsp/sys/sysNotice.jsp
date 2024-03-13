<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>공문/새소식 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>

    <script type="text/javascript">
		var gridView;
		var dataProvider;
		var searchItem = "";
		var searchWord = "";
    
		$(document).ready( function(){
			
			searchItem = getCode(I_LOGMNU, I_LOGMNM, "NOTICE_KIND","N");
	        $('#I_DCL').html(searchItem);
	        jcf.replace($("#I_DCL"));
	        
	        searchItem = getCode(I_LOGMNU, I_LOGMNM, "MAIN_POP_YN","Y");
	        $('#I_PYN').html(searchItem);
	        jcf.replace($("#I_PYN"));
	        
	        searchWord = getCode(I_LOGMNU, I_LOGMNM, "SEARCH_WORD","N");
	        $('#I_TIT1').html(searchWord);
	        jcf.replace($("#I_TIT1"));
	        
	      	//달력 from~to
			$("#I_FWDT").datepicker({
				dateFormat: 'yy-mm-dd',
				maxDate: $('#I_TWDT').val(),
				onSelect: function(selectDate){
					$('#I_TWDT').datepicker('option', {
						minDate: selectDate,
					});
				}
			});	
			$("#I_TWDT").datepicker({
				dateFormat: 'yy-mm-dd',
				minDate: $('#I_FWDT').val(),
				onSelect : function(selectDate){
					$('#I_FWDT').datepicker('option', {
						maxDate: selectDate,
					});
				}		
			});
			
			//달력 from~to
			$("#I_PFR").datepicker({
				dateFormat: 'yy-mm-dd',
				maxDate: $('#I_PTO').val(),
				onSelect: function(selectDate){
					$('#I_PTO').datepicker('option', {
						minDate: selectDate,
					});
				}
			});	
			$("#I_PTO").datepicker({
				dateFormat: 'yy-mm-dd',
				minDate: $('#I_PFR').val(),
				onSelect : function(selectDate){
					$('#I_PFR').datepicker('option', {
						maxDate: selectDate,
					});
				}		
			});
			
	        var yearAgo = new Date();
	        yearAgo.setDate(yearAgo.getDate() - 365);
	        $("#I_FWDT").datepicker('setDate',yearAgo);
	        $("#I_TWDT").datepicker('setDate',new Date);
	        
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
			//dataResult();
		});		
		
		function setGrid(){
			dataProvider = new RealGridJS.LocalDataProvider();
			gridView = new RealGridJS.GridView("realgrid");
			
			setStyles(gridView);
			
			gridView.setStateBar({
				  visible: false  
			});
			
			gridView.setDataSource(dataProvider);
			
			gridView.setStyles(basicGrayBlueSkin);
			
			gridView.setOptions({
                insertable: false,
                appendable: false,
                deletable : false,
                readOnly  : true,
                panel     : {visible : false},
    			footer	  : {visible: false},
               });
			
			var fields=[
		    	 {fieldName:"S014COR"}
		    	,{fieldName:"S014SEQ"}
		    	,{fieldName:"S014WDT", dataType: "datetime",datetimeFormat: "yyyyMMdd"}
		    	,{fieldName:"S014DNO"}
		    	,{fieldName:"S014DCL"}
		    	,{fieldName:"S014SDV"}
		    	,{fieldName:"S014PYN"}
		    	,{fieldName:"S014PFR", dataType: "datetime",datetimeFormat: "yyyyMMdd"}
		    	,{fieldName:"S014PTO", dataType: "datetime",datetimeFormat: "yyyyMMdd"}
		    	,{fieldName:"S014LCO"}
		    	,{fieldName:"S014TCO"}
		    	,{fieldName:"S014WID"}
		    	,{fieldName:"S014HIT"}
		    	,{fieldName:"S014TIT"}
		    	,{fieldName:"S014FYN"}
		    	,{fieldName:"S014CNM"}
		    	,{fieldName:"S014CUR"}
		    	,{fieldName:"S002CNM"}
				,{fieldName:"I_LOGMNU"}	//메뉴코드
				,{fieldName:"I_LOGMNM"}	//메뉴명
		    	];
			
			dataProvider.setFields(fields);
			
			var columns=[
			     {name:"S014DNO",	fieldName:"S014DNO", renderer:{showTooltip: true}, header:{text:"문서번호"},			width:120, styles: {textAlignment: "near"} }
			    ,{name:"S002CNM",	fieldName:"S002CNM", renderer:{showTooltip: true}, header:{text:"문서종류"},			width:60, styles: {textAlignment: "center"}}
			    ,{name:"S014TIT",	fieldName:"S014TIT", renderer:{showTooltip: true}, header:{text:"제 목"},				width:500, styles: {textAlignment: "near"  }}
			    ,{name:"S014PYN",	fieldName:"S014PYN", renderer:{showTooltip: true}, header:{text:"메인 팝업생성 여부"},	width:90, styles: {textAlignment: "center"}}
			    ,{name:"S014PFR",	fieldName:"S014PFR", renderer:{showTooltip: true}, header:{text:"메인 팝업생성 시작"},	width:90, styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
			    ,{name:"S014PTO",	fieldName:"S014PTO", renderer:{showTooltip: true}, header:{text:"메인 팝업생성 종료"},	width:90, styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
			    ,{name:"S014FYN",	fieldName:"S014FYN", renderer:{showTooltip: true}, header:{text:"첨부파일"},			width:60,  styles: {textAlignment: "center"}}
			    ,{name:"S014CNM",	fieldName:"S014CNM", renderer:{showTooltip: true}, header:{text:"작성자"},			width:120, styles: {textAlignment: "near"}}
			    ,{name:"S014WDT",	fieldName:"S014WDT", renderer:{showTooltip: true}, header:{text:"작성일"},			width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
			    
	    	];
			
			var options = { visible: false}
		    gridView.setCheckBar(options);
			
			
			//컬럼을 GridView에 입력 합니다.
		    gridView.setColumns(columns);
			
		    gridView.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
			
		    gridView.onDataCellDblClicked = function (grid, index) {
		    	var itemIndex = index.itemIndex;
		    	var gridValue = gridView.getValue(itemIndex,"S014SEQ");
		    	openPopup(gridValue);
        	};
	
		    var checkBar = gridView.getCheckBar();
            checkBar.visible = true;
            gridView.setCheckBar(checkBar);
            
            gridView.setEditOptions({
		    	  editable: false,
		    	  updatable: false
		    	});
            
            $("#searchBtn").click(function(){
            	
    			dataResult();
    		});
            
            dataResult();
		}
		
		function openPopup(index){
			var gridValus,labelNm;
			if(isNull(index)){
				gridValus = index;
				labelNm = "공문/새소식 등록";
				/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
				fnOpenPopup("/sysNotice01.do",labelNm,gridValus,callFunPopup);
			}else{
				gridValus = index;
				labelNm = "공문/새소식 상세보기";
				/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
				fnOpenPopup("/sysNotice02.do",labelNm,gridValus,callFunPopup);
			}
		}
		
		function callFunPopup(url,iframe,gridValus){
		    iframe.gridViewRead(gridValus);
		}
		
		/*close 호출*/
		function fnCloseModal(obj){
		    dataResult();
		}
		/* 팝업 호출 및 종료 */
		
		function dataClean(){
			var data1 = dataProvider.getJsonRows(0, -1);
			dataProvider.removeRows(data1);
		}
		
		function dataResult(){
			
			gridView.setAllCheck(false);
			
        	var sDate = new Date($("#I_FWDT").val());
    		var eDate = new Date($("#I_TWDT").val());
    		var sPDate = new Date($("#I_PFR").val());
    		var ePDate = new Date($("#I_PTO").val());
    		
    		var interval = eDate - sDate;
    		var day = 1000*60*60*24;
    		
    		if(!checkdate($("#I_FWDT"))||!checkdate($("#I_TWDT"))){
    			CallMessage("273","alert","",dataClean);	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
    			return;
    		}
    		if (interval/day > 365){
       			CallMessage("274","alert","",dataClean);		//최대 1년 내에서 조회해주세요.
    			return false;
    		}
    		
    		if(sDate > eDate){
    			CallMessage("229","alert","",dataClean); // 조회기간을 확인하여 주십시오.
				return;
			}
    		
    		if(sPDate > ePDate){
    			CallMessage("232","alert","",dataClean); // 조회기간을 확인하여 주십시오.
				return;
			}
    		
    		
			
			if($('#I_TIT1').val() == "TITLE"){
				$('#I_TIT').val($('#searchValue').val());
				$("#I_CON").val("");
			} else {
				$('#I_CON').val($('#searchValue').val());
				$("#I_TIT").val("");
			}
			
			if($('#I_PFR').val() == ""){
				if($('#I_PTO').val() != ""){
					CallMessage("232","alert","",dataClean);	//시작일자와 종료일자를 확인하여 주십시오.
					dataClean();
					return;
				}
			}
				
			if($('#I_PTO').val() == ""){
				if($('#I_PFR').val() != ""){
					CallMessage("232","alert","",dataClean);	//시작일자와 종료일자를 확인하여 주십시오.
					dataClean();
					return;	
				}
			};
			
			
			
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/sysNoticeList.do", formData);
		}
		
		function onResult(strUrl,response){
			if(strUrl == "/sysNoticeList.do"){
				var resultList =  response.resultList;
				dataProvider.setRows(resultList);
				gridView.setDataSource(dataProvider);
			}
		}
		
		function noticeDelete(){
			var dataRows = gridView.getCheckedRows();
			if(dataRows.length == 0){
				CallMessage("243","alert");	//선택 내역이 없습니다.
				return;
			}
			
			CallMessage("242","confirm","",fnDel);	//정말 삭제하시겠습니까?
		}
		
		function fnDel(){
			var dataRows = gridView.getCheckedRows();
			for (var i in dataRows){
				
				$('#I_SEQ').val(dataProvider.getValue(dataRows[i], "S014SEQ"));
				if(dataProvider.getValue(dataRows[i], "S014CUR") != "<%=session.getAttribute("UID") %>"){
					CallMessage("227","alert",["삭제"]) //본인이 작성한 글외에는 삭제할 수 없습니다.
					return;
				}
				
				var formData = $("#searchForm").serializeArray();
			}
			var strUrl = "/noticeDelete.do";
			
			rtnCall = ajaxListCall(strUrl,dataProvider, dataRows,ListCallEnd);
			
		}
		
		function ListCallEnd(rtnCall){
			if(!rtnCall){
	   			CallMessage("244","alert",["삭제"]);		//{0}에 실패하였습니다.
	   		}else{
	   			CallMessage("223","alert","",dataResult);	//삭제를 완료하였습니다.
//	   			dataResult();
	   		} 
		}

		function udtModal(index){
			 var gridValus,labelNm;
			 gridValus = index;
			 labelNm = "공문/새소식 수정";
			 /** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			 fnOpenPopup("/sysNotice01.do",labelNm,gridValus,callFunPopup);
		}
		
		function init(){
				$('#I_PFR').val('');
				$('#I_PTO').val('');
		}
    </script>
    
</head>
<body>
	<!-- content_inner -->
	<div class="content_inner">
		<form id="searchForm" name="searchForm">
			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
			<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
			<input id="I_SEQ" name="I_SEQ" type="hidden"/>
			<input id="I_TIT" name="I_TIT" type="hidden"/>
			<input id="I_CON" name="I_CON" type="hidden"/>
			
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
	                    			<label for="user_code" class="label_basic">문서 종류</label>
	                    			<div class="select_area">
	                    				<select class="form-control" id="I_DCL" name="I_DCL">
	                                    </select>
	                    			</div>
	                    			<div class="select_area">
                                       <select class="form-control" id="I_TIT1" name="I_TIT1">
                                       </select>
                                   	</div>
	                            	<input type="text" style="width:268px;" class="srch_put" id="searchValue" name="searchValue" onKeydown="if(event.keyCode==13) dataResult()" maxlength = "40">
								</div>
								<div class="srch_item srch_item_v4">
	                            	<label for="user_code" class="label_basic">메인 팝업 생성 유무</label>
	                                	<div class="select_area">
	                                       <select class="form-control" id="I_PYN" name="I_PYN">
	                                       </select>
	                                </div>
								</div>
							</div>
							
							
							<div class="srch_box_inner m-t-10">
								<!-- 달력 -->
								<div class="srch_item srch_item_v5">
									<label for="fr_date" class="label_basic">등록 일자</label>
									<span class="period_area">
									    <label for="startDt" class="blind">날짜 입력</label>
									    <input type="text" class="fr_date calendar_put" id="I_FWDT" name="I_FWDT">
									</span>
									~
									<span class="period_area">
									    <label for="endDt" class="blind">날짜 입력</label>
									    <input type="text" class="fr_date calendar_put" id="I_TWDT" name="I_TWDT">
	                				</span>
	                            </div>
	                            <!-- 달력 end -->
	                            <!-- 달력 -->
	                            <div class="srch_item srch_item_v4 m-t-10"">
									<label for="fr_date" class="label_basic">메인 팝업 생성 기간</label>
									<span class="period_area">
									    <label for="startDt2" class="blind">날짜 입력</label>
									    <input type="text" class="fr_date calendar_put" id="I_PFR" name="I_PFR">
									</span>
									~
									<span class="period_area">
									    <label for="endDt2" class="blind">날짜 입력</label>
									    <input type="text" class="fr_date calendar_put" id="I_PTO" name="I_PTO">
									</span>
									<button type="button" class="btn btn-gray btn-sm" onclick="javascript:init()" title="초기화"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button>
									
	                            </div>
							</div>
							<div class="btn_srch_box">
	                        	<button type="button" class="btn_srch" id="searchBtn">조회</button>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- 검색영역 end -->
		
		<!-- realGrid -->
		<div class="container-fluid">
	        <div class="con_wrap col-md-12 col-sm-12">
	            <!-- title section -->
	            <div class="title_area">
	                <h3 class="tit_h3">공문/새소식</h3>
	                <div class="tit_btn_area">
	                	<!-- <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#notice-Modal" onClick="javascript:openPopup();">등록</button> -->
	                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup();">등록</button>
	                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:noticeDelete();">삭제</button>
	                </div>
	            </div>
	            <!-- realGrid section -->
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>

	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>공문/새소식</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
    <%
		String DCL_CD = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("DCL_CD"), "");
    %>
    <script type="text/javascript">
		
		var gridView;
		var dataProvider;
		var searchItem = "";
		var searchWord = "";
		
		$(document).ready( function(){
			
			searchItem = getCode(I_LOGMNU, I_LOGMNM, "NOTICE_KIND","N");
	        $('#I_DCL').html(searchItem);
	        var dclCd = "<%=DCL_CD%>";
	        if(!isNull(dclCd)){
				$("#I_DCL").val(dclCd);  
	        }
	        jcf.replace($("#I_DCL"));
	        
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
			$(".fr_date").datepicker("setDate", new Date());
			var yearAgo = new Date();
	        yearAgo.setDate(yearAgo.getDate() - 365);
	        $("#I_FWDT").datepicker('setDate',yearAgo);
	        
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
		
		function setGrid()
		{
			dataProvider = new RealGridJS.LocalDataProvider();
			gridView = new RealGridJS.GridView("realgrid");
			
			setStyles(gridView);
			
			gridView.setDataSource(dataProvider);
			
			gridView.setStyles(basicGrayBlueSkin);
			
			gridView.setStateBar({
				  visible: false  
				});
			
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
		    	,{fieldName:"S014PFR"}
		    	,{fieldName:"S014PTO"}
		    	,{fieldName:"S014LCO"}
		    	,{fieldName:"S014TCO"}
		    	,{fieldName:"S014WID"}
		    	,{fieldName:"S014HIT"}
		    	,{fieldName:"S014TIT"}
		    	,{fieldName:"S014FYN"}
		    	,{fieldName:"S014CNM"}
		    	,{fieldName:"S014CUR"}
		    	,{fieldName:"S002CNM"}
		    	,{fieldName:"S014RYN"}
				,{fieldName:"I_LOGMNU"}	//메뉴코드
				,{fieldName:"I_LOGMNM"}	//메뉴명
		    	];
			
			dataProvider.setFields(fields);
			
			var columns=[
			     {name:"S014DNO",	fieldName:"S014DNO", renderer:{showTooltip: true} ,header:{text:"문서번호"},	width:100, styles: {textAlignment: "near"}  }
			    ,{name:"S002CNM",	fieldName:"S002CNM" ,renderer:{showTooltip: true} ,header:{text:"문서종류"},	width:60, styles: {textAlignment: "center"} }
			    ,{name:"S014TIT",	fieldName:"S014TIT" ,renderer:{showTooltip: true} ,header:{text:"제 목"},		width:500, styles: {textAlignment: "near"} }
			    ,{name:"S014RYN",	fieldName:"S014RYN" ,renderer:{showTooltip: true} ,header:{text:"열람여부"},	width:60, styles: {textAlignment: "center"}}
			    ,{name:"S014FYN",	fieldName:"S014FYN" ,renderer:{showTooltip: true} ,header:{text:"첨부파일"},	width:60, styles: {textAlignment: "center"} }
			    ,{name:"S014CNM",	fieldName:"S014CNM" ,renderer:{showTooltip: true} ,header:{text:"작성자"},	width:120 }
			    ,{name:"S014WDT",	fieldName:"S014WDT" ,renderer:{showTooltip: true} ,header:{text:"작성일"},	width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
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
			var setValus, labelNm;

			setValus = {};
			setValus["index"] = index;
			setValus["MainYN"] =   "N";
// 			gridValus = index;
			labelNm = "공문/새소식 조회";
			/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			fnOpenPopup("/notice01.do",labelNm,setValus,callFunPopup);
		}
		
		function callFunPopup(url,iframe,setValus){
		    iframe.gridViewRead(setValus);
		}
		
		function dataClean(){
			var data1 = dataProvider.getJsonRows(0, -1);
			dataProvider.removeRows(data1);
		}
		
		function dataResult(){
			
			var sDate = new Date($("#I_FWDT").val());
    		var eDate = new Date($("#I_TWDT").val());
    		var interval = eDate - sDate;
    		var day = 1000*60*60*24;
    		
    		if(!checkdate($("#I_FWDT"))||!checkdate($("#I_TWDT"))){
    			CallMessage("273");	//조회기간은 필수조회 조건입니다.</br>조회기간을 넣고 조회해주세요.
    			dataClean();
    			return;
    		}
    		
    		if (interval/day > 365){
       			CallMessage("274","alert");		//최대 1년 내에서 조회해주세요.
       			dataClean();
    			return false;
    		}
    		
    		if(sDate > eDate){
    			CallMessage("229","alert"); // 조회기간을 확인하여 주십시오.
    			dataClean();
				return bool;
			}
			
			if($('#I_TIT1').val() == "TITLE"){
				$('#I_TIT').val($('#searchValue').val());
				$('#I_CON').val("");
			} else {
				$('#I_CON').val($('#searchValue').val());
				$('#I_TIT').val("");
			}
			
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/noticeList.do", formData);
		}
		
		function onResult(strUrl,response){
			if(strUrl == "/noticeList.do"){
				var resultList =  response.resultList;
				dataProvider.setRows(resultList);
				gridView.setDataSource(dataProvider);
			}
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
	                   			<div class="srch_item srch_item_v2">
	                    			<label for="document_code" class="label_basic">문서종류</label>
	                    			<div class="select_area">
	                    				<select class="form-control" id="I_DCL" name="I_DCL">
	                                       </select>
	                    			</div>
								</div>
	                            <!-- 달력 -->
								<div class="srch_item">
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
							</div>
							<div class="srch_box_inner m-t-10">
								<div class="srch_item srch_item_v2">
	                                <label for="" class="label_basic">작성자</label>
	                                <input type="text" class="srch_put" style="width: 150px;" id="I_CNM" name="I_CNM" onKeydown="if(event.keyCode==13) dataResult()" maxlength = "40">
	                            </div>
	                            <div class="srch_item">
	                            	<div class="select_area">
                                       <select class="form-control" id="I_TIT1" name="I_TIT1" > <!-- 제목 -->
                                       </select>
                                   	</div>
	                            	<input type="text" class="srch_put" style="width: 328px;" id="searchValue" name="searchValue" onKeydown="if(event.keyCode==13) dataResult()" maxlength = "40">
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
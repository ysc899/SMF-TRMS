<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<!-- 검사 실시간 현황 css (하이차트 css 포함) -->
	<link rel="stylesheet" href="/css/realtime.css?v=3"> 
	
	<%@ include file="/inc/common.inc"%>		
	
	<title>뱅킹통계</title>
	
	<script>
		
		var I_LOGMNU = "BANKINGSTAT";
		var I_LOGMNM = "뱅킹통계";
		
		/*************** 뱅킹통계 - grid ***************/
		var gridView;
		var dataProvider5;
		var getBankingStatisticList;
			    
	    $(document).ready( function(){
	    	
	    	/********************************
	    	* 종목분류
	    	*********************************/
			var resultSearchCondition_1 = "";
			resultSearchCondition_1 = getBankingCmCodeSelect("/getBankingCmCodeSelect.do","NML","BANKING","GCDGRP");
			$('#P_GCDGRP').html(resultSearchCondition_1);
						
			//console.log("### resultSearchCondition_1 :: "+resultSearchCondition_1);
			
			/********************************
	    	* 검체명
	    	*********************************/
			var resultSearchCondition_2 = "";
			resultSearchCondition_2 = getBankingCmCodeSelect("/getBankingCmCodeSelect.do","NML","BANKING","SPC");
			$('#P_SPECIMEN_ID').html(resultSearchCondition_2);
			
			//console.log("### resultSearchCondition_2 :: "+resultSearchCondition_2);			
			
			/********************************
	    	* 조회결과
	    	*********************************/
	    	
			dataProvider5 = new RealGridJS.LocalDataProvider();
			gridView = new RealGridJS.GridView("realgrid5");
			setStyles(gridView);
			gridView.setHeader({	// 헤더라인 높이 조절
		    	//height:50
		    	heightFill : "default"
		    })
		    gridView.setDataSource(dataProvider5);
			gridView.setDisplayOptions({
				heightMeasurer: "fixed",
				rowResizable: true,
				rowHeight: -1,
				emptyMessage: "조회된 데이터가 없습니다."
				//emptyMessage: ""
			});
			
			var fields5=[
			    	{fieldName: "GNMGRP"},
			    	{fieldName: "GNM"},
			    	{fieldName: "ANM"},
			    	{fieldName: "SPCNM"},
			    	{fieldName: "POSI"},
			    	{fieldName: "NEGA"},
			    	{fieldName: "POSI_NUCL"},
			    	{fieldName: "NEGA_NUCL"}
		    	];
			
			dataProvider5.setFields(fields5);
			
			var columns5=[
					{name: "GNMGRP",	fieldName: "GNMGRP",	width: "150"	, styles: {textAlignment: "center",},    renderer:{showTooltip: true}, header: {text: "종목분류"}},
					{name: "GNM",		fieldName: "GNM",		width: "150"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "검사명"}},
					{name: "ANM",		fieldName: "ANM",		width: "150"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "부속명"}},
					{name: "SPCNM", 	fieldName: "SPCNM",		width: "150"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "검체명"}},
					{name: "POSI", 		fieldName: "POSI",		width: "100"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "양성검체"}},
					{name: "NEGA", 		fieldName: "NEGA",		width: "100"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "음성검체"}},
					{name: "POSI_NUCL", fieldName: "POSI_NUCL",	width: "100"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "양성핵산"}},
					{name: "NEGA_NUCL", fieldName: "NEGA_NUCL",	width: "100"	, styles: {textAlignment: "center"}	,    renderer:{showTooltip: true}, header: {text: "음성핵산"}}
					
	    	];
			
		    gridView.setColumns(columns5);	//컬럼을 GridView에 입력 합니다.
			
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
				keepNullFocus: true
	           });
			
			var options = { visible: false}
		    gridView.setCheckBar(options);			
			
		    gridView.onShowTooltip = function (grid, index, value) {
		        var column = index.column;
		        var itemIndex = index.itemIndex;
		         
		        var tooltip = value;
		        
		        return tooltip;
		    }
	        
	        gridView.setEditOptions({
				editable: false,
	    	  	updatable: false
	    	});
	        
	        //**************************************
	        //** 특정 열의 값이 특정 값일 경우, 행에 숫자배경 넣기
	        //**************************************
	        gridView.setStyles({
	            body:{
	                dynamicStyles: function(grid, index) {
	                    var jobValue = grid.getValue(index.itemIndex, "GNM");

	                    if (jobValue == '전체') {
	                        return {
	                            //background:"#FF073763",
	                            //foreground:"#FFFFFFFF"
	                            background:"#d9d9d9",
	                            foreground:"#000000"
	                        }
	                    }
	                }
	            }
	        }); 
	     	// =============================================================================================================================== 
	     	// =============================================================================================================================== 
	       		  	
		});
		
	 	/********************************
    	* 데이터 가져오기
    	*********************************/
		function dataResult(){
			callLoading(null,"on");
			
			var formData = $("#searchForm").serializeArray();
			ajaxCall("/getBankingStatisticList.do", formData,false);
		}
		
		/********************************
    	* ajax 호출 result function
    	*********************************/
		function onResult(strUrl,response){
			
			if(strUrl == "/getBankingStatisticList.do"){
					
				callLoading(null,"off");
				
				var resultList;;
				
				if(!isNull(response.resultList)){
					
					resultList = response.resultList;
					
					// 뱅킹검체 정보
					resultList =  response.resultList;
					dataProvider5.setRows(resultList);
					gridView.setDataSource(dataProvider5);
					
				}
			}
		}
		
		/********************************
    	* 조회
    	*********************************/
		function btn_search(){
			console.log("search() 호출");
			dataResult();
		}
		
		/********************************
    	* excel 파일로 export 할 때 내용 그리기
    	*********************************/
		function exportGrid(){
			var totalCnt_1 = dataProvider5.getRowCount();
			
			if(totalCnt_1 == 0){
		    	CallMessage("268","alert");	// 저장하시겠습니까?
				return;
			}
			CallMessage("257","confirm", "", gridExport);
		}
		
		/********************************
    	* excel 파일로 export
    	*********************************/
		function gridExport(){
			RealGridJS.exportGrid({
			    type: "excel",
			    target: "local",
			    lookupDisplay : true,
			    fileName: "뱅킹검체_"+getToday()+".xlsx",
			    showProgress: true,
			    applyDynamicStyles: true,
			    progressMessage: "엑셀 Export중입니다.",
			    indicator: "default",
			    header: "visible",
			    footer: "default",
			    compatibility: "2010",
			    done: function () {},  //내보내기 완료 후 실행되는 함수			    
			    exportGrids:[
			        { 
			            grid:gridView, //그리드 변수명
			            sheetName:"뱅킹검체" // 다른 그리드와 중복되어서는 안된다.
			        }
		      	]
			});
		}
		
		/********************************
    	* excel 파일로 export 할 때 파일명에 날짜값 계산
    	*********************************/
		function getToday(){
		    var date = new Date();
		    var year = date.getFullYear();
		    var month = pad(("0" + (1 + date.getMonth())).slice(-2),2);
		    var day = pad(("0" + date.getDate()).slice(-2),2);
		    var hh = pad(date.getHours(),2);
		    var mm = pad(date.getMinutes(),2);
		    var ss = pad(date.getSeconds(),2);
		    
		    ss.len
		    
		    console.log("## hh :: "+hh);
		    console.log("## mm :: "+mm);
		    console.log("## ss :: "+ss);
		    

		    return year + month + day + hh + mm + ss;
		}
		
		function pad(number, length){
			var str = number.toString();
			while(str.length < length){
				str = '0' + str;
			}
			return str;
		}
		
		/********************************
    	* 검색조건 가져오기 (종목분류, 검사명, 검체명)
    	*********************************/
		function getBankingCmCodeSelect(strUrl,P_COR, P_CM_CODE1, P_CM_CODE2){
		
			var optionList = "";
				$.ajax({
					 url : strUrl
					, type : "post"
					, data : {"P_COR" : P_COR, "P_CM_CODE1" : P_CM_CODE1, "P_CM_CODE2" : P_CM_CODE2}
					, dataType: 'json'
					, async:false
					, success : function(response){
						var resultList =  response.resultList;
						
						optionList += '<option value="">전체</option>';
						$.each(resultList, function(i, e) {
							optionList += '<option value="'+e.TABLE_ID_3+'">'+e.NAME+ '</option>'; 
						});
						
					},error:function(x,e){
						callLoading(strUrl,"off");
						if(x.status==401){
							goReload();
						}
					}
				});	
			
			return optionList;
		}
		
		/********************************
    	* 검색조건 가져오기 (부속명리스트)
    	*********************************/
		function getBankingSimpleList(strUrl,P_COR, P_GCD){
		
			var optionList = "";
				$.ajax({
					 url : strUrl
					, type : "post"
					, data : {"P_COR" : P_COR, "P_GCD" : P_GCD}
					, dataType: 'json'
					, async:false
					, success : function(response){
						var resultList =  response.resultList;
						
						optionList += '<option value="">전체</option>';
						$.each(resultList, function(i, e) {
							optionList += '<option value="'+e.ACD+'">'+e.ACDSNM+ '</option>'; 
						});
						
					},error:function(x,e){
						if(x.status==401){
							goReload();
						}
					}
				});
			
			
			
			return optionList;
		}
		
		/********************************
    	* selectBox 선택시 change 이벤트 (종목분류)
    	*********************************/
		function chn_GCDGRP(param_GCDGRP) {			
			
			console.log("## param_GCDGRP :: "+param_GCDGRP);
						
			var getBankingCmCodeSelect_var = "";
			getBankingCmCodeSelect_var = getBankingCmCodeSelect("/getBankingCmCodeSelect.do","NML","BANKING",param_GCDGRP);
			$('#P_GCD').html(getBankingCmCodeSelect_var);
			
			
			// 종목분류 선택시 검사명 selectBox 전체 값으로 초기화
			$("#P_GCD").trigger("click");
			
			// 종목분류 선택시 부속명 selectBox 전체 값으로 초기화
			$("#P_ACD").html("<option value='*'>전체</option>");
			$("#P_ACD").trigger("click");
		}
		
		/********************************
    	* selectBox 선택시 change 이벤트 (검사명)
    	*********************************/
		function chn_GCD(param_GCD) {
			
			console.log("## param_GCD :: "+param_GCD);
			
			var getBankingSimpleList_var = "";
			getBankingSimpleList_var = getBankingSimpleList("/getBankingSimpleList.do","NML",param_GCD);
			$('#P_ACD').html(getBankingSimpleList_var);
			
			// 종목분류 선택시 부속명 selectBox 전체 값으로 초기화
			$("#P_ACD").trigger("click");
		}	
	</script>
	
</head>
<body>
	<form id="searchForm" name="searchForm">
		<input id="I_LOGMNU" 	name="I_LOGMNU"	type="hidden" />	<!-- 메뉴코드 -->
		<input id="I_LOGMNM" 	name="I_LOGMNM"	type="hidden" />	<!-- 메뉴명 -->

		<input id="P_COR" 		name="P_COR" 		type="hidden" />
		<input id="P_CM_CODE1" 	name="P_CM_CODE1" 	type="hidden" />
		<input id="P_CM_CODE2" 	name="P_CM_CODE2" 	type="hidden" />		 
		
		<div class="content_inner" >
           	<!-- 검색영역 -->
               <div class="container-fluid">
					<div class="con_wrap col-md-12 srch_wrap">
						<div class="title_area open">
							<h3 class="tit_h3">검색영역</h3>
						</div>
						<div class="con_section row">
							<div class="srch_box floatLeft">
								<div class="srch_box_inner m-t-10" >
									<div class="srch_item_v3">
										
										<label for="P_GCDGRP" class="label_basic">종목분류</label>
			                            <div class="select_area">
			                                <select onchange="chn_GCDGRP(this.value)" id="P_GCDGRP" name="P_GCDGRP" class="form-control"><option value="*">전체</option></select>			                                
			                                <!-- <select onchange="alert(this.value)" id="P_GCDGRP" name="P_GCDGRP"class="form-control"><option value="">전체</option></select> -->			                                
			                            </div>
			                           
			                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                            <label for="P_GCD" class="label_basic">검사명</label>
			                            <div class="select_area">
			                                <select onchange="chn_GCD(this.value)" id="P_GCD" name="P_GCD"class="form-control"><option value="*">전체</option></select>			                                			                                
			                            </div>
			                            
			                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                            <label for="P_ACD" class="label_basic">부속명</label>
			                            <div class="select_area">
			                                <select onchange="console.log(this.value)" id="P_ACD" name="P_ACD"class="form-control"><option value="*">전체</option></select>			                                
			                            </div>
			                           
			                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                            <label for="P_SPECIMEN_ID" class="label_basic">검체명</label>
			                            <div class="select_area">
			                                <select onchange="console.log(this.value)" id="P_SPECIMEN_ID" name="P_SPECIMEN_ID"class="form-control"><option value="*">전체</option></select>			                                
			                            </div>
			                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                            <ul class="chk_lst chk_lst_v2">                                          
                                            <li><input type="checkbox" value="dataOnly" id="dataOnly" name="dataOnly"> <label for="dataOnly">Data Only</label></li>
                                        </ul>
			                            
			                            <div class="btn_srch_box">
					                       <button type="button" class="btn btn-default btn-red" name = "btn_srch" onclick ="javascript:btn_search()">조회</button>
					                       <button type="button" class="btn btn-default btn-red" onclick="javascript:exportGrid()">엑셀 다운</button>
					                   </div>
		                            </div>
	                            </div>
							</div>
						</div>
					</div>
				</div>
				
            	<!-- 검색영역 end -->     
                
                <div class="container-fluid">
                    <div class="con_wrap col-md-12 ">
                        <div id="realgrid_realtime_view_1" class="container-fluid" border:2px;">
							<div class="con_wrap_realtime col-md-12 col-sm-12" style="margin-left:-30px;">
					        	
					           	<div style="float:left;width:80%;">
					           		<div class="title_area_realtime_4">
						            	<h3 class="tit_h3">■ 뱅킹검체 정보</h3>
						          	</div>
				 		          	
						          	<div class="con_section overflow-scr">
						          		<div id="realgrid5" style="height:600px;"></div>
						          	</div>	
								</div>		
							</div>
						</div>
                    </div>
                </div>                 
            </div>    
            <!-- content_inner -->	
     </form>
     
</body>
</html>




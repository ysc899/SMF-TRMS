<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>

	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>문의사항 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	<%@ include file="/inc/common.inc"%>
	
    
    <script type="text/javascript">
		var gridView;
		var dataProvider;
		var searchItem = "";
		var searchWord = "";
		var replyStat = "";
		
		$(document).ready( function(){
			searchItem = getCode(I_LOGMNU, I_LOGMNM, "SEARCH_ITEM","Y");
	        $('#I_DCL').html(searchItem);
	        jcf.replace($("#I_DCL"));
	        
	        searchWord = getCode(I_LOGMNU, I_LOGMNM, "SEARCH_WORD","N");
	        $('#I_TIT1').html(searchWord);
	        jcf.replace($("#I_TIT1"));
	        
	        replyStat = getCode(I_LOGMNU, I_LOGMNM, "REPLY_STAT","Y");
	        $('#I_PDNOC').html(replyStat);
	        jcf.replace($("#I_PDNOC"));
	        
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
		});
		
		function setGrid()
		{
			dataProvider = new RealGridJS.LocalTreeDataProvider();
			gridView = new RealGridJS.TreeView("realgrid");
			
			setStyles(gridView);
			gridView.setDataSource(dataProvider);
			
			gridView.setStyles(basicGrayBlueSkin);
			
			gridView.setOptions({
                insertable: false,
                appendable: false,
                deletable:  false,
                readOnly:   true,
                panel : {visible : false},
    			footer: {visible: false},
               });
			
			 var fields=[
                 {fieldName:"TREE"}			//tree 
               , {fieldName:"S015PDNOC"}	//답변상태(0 : 대기중, 그 외 답변 완료)
               , {fieldName:"S015COR"}		//
               , {fieldName:"S015SEQ"}		//순번
               , {fieldName:"S015DCL"}		//문의종류
               , {fieldName:"S015PDNO"}		//문서상위번호
               , {fieldName:"S015TIT"}		//문의제목
               , {fieldName:"S015CON"}		//문의내용
               , {fieldName:"S015CNM"}		//작성자ID
               , {fieldName:"S015CMA"}		//이메일
               , {fieldName:"S015IYN"}		//개인정보동의여부
               , {fieldName:"S015RCT"}		//조회수
               , {fieldName:"S015DYN"}		//삭제여부
               , {fieldName:"S015CUR"}		//등록자ID
               , {fieldName:"S015CDT", dataType: "datetime",datetimeFormat: "yyyyMMdd"}		//등록일자
               , {fieldName:"S015CTM"}		//등록시간
               , {fieldName:"S015CIP"}		//둥록자ID
               , {fieldName:"S015UUR"}		//수정자ID
               , {fieldName:"S015UDT"}		//수정일자
               , {fieldName:"S015UTM"}		//수정시간
               , {fieldName:"S015UIP"}		//수정자IP
               , {fieldName:"S015DUR"}		//삭제자ID
               , {fieldName:"S015DDT"}		//삭제일자
               , {fieldName:"S015DTM"}		//삭제시간 
               , {fieldName:"S015DIP"}		//삭제자IP
               , {fieldName:"DNCHK"}		//답변유무
               , {fieldName: "I_LOGMNU"}
               , {fieldName: "I_LOGMNM"}
               
         ];
			 
			dataProvider.setFields(fields);
			
			var columns=[
                 {name:"S015SEQ",   fieldName:"S015SEQ",   renderer:{showTooltip: true}, header:{text:"번호"},   width:100, styles: {textAlignment: "far"}}
                ,{name:"S015TIT",   fieldName:"S015TIT",   renderer:{showTooltip: true}, header:{text:"제목"},   width:500}
                ,{name:"S015PDNOC", fieldName:"S015PDNOC", renderer:{showTooltip: true}, header:{text:"답변상태"},	width:100, styles: {textAlignment: "center"}}
                ,{name:"S015CNM",   fieldName:"S015CNM",   renderer:{showTooltip: true}, header:{text:"작성자"},	width:100, styles: {textAlignment: "near"}}
                ,{name:"S015CDT",   fieldName:"S015CDT",   renderer:{showTooltip: true}, header:{text:"작성일"}, 	width:80,  styles: {datetimeFormat: "yyyy-MM-dd",textAlignment:"center"}}
                ,{name:"S015RCT",   fieldName:"S015RCT",   renderer:{showTooltip: true}, header:{text:"조회"},	width:100, styles: {textAlignment: "far"}}
            ];
			
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
		    	var gridValue = gridView.getValue(itemIndex,"S015SEQ");
		    	openPopup(gridValue);
        	};
			
		    var checkBar = gridView.getCheckBar();
            checkBar.visible = true;
            gridView.setCheckBar(checkBar);
            
            gridView.setEditOptions({
		    	  editable: false,
		    	  updatable: false
		    	});
            
            gridView.setStateBar({
				  visible: false  
			});
            
            gridView.orderBy(["S015SEQ"],["DESCENDING"]);
            
            
            $("#searchBtn").click(function(){
    			dataResult();
    		});
            
            dataResult();
            
		}
		
		function qnaDelete(){
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
				var chk = "";
				chk = dataProvider.getValue(dataRows[i], "DNCHK");
			 	if(chk == "Y"){
			 		CallMessage("226","alert");	//답변이 존재하여 삭제할 수 없습니다.
					return;	
				}
			 	
			 	$('#I_SEQ').val(dataProvider.getValue(dataRows[i], "S015SEQ"));
				if(dataProvider.getValue(dataRows[i], "S015CUR") != "<%=session.getAttribute("UID") %>"){
					CallMessage("227","alert",["삭제"]) //본인이 작성한 글외에는 삭제할 수 없습니다.
					return;
				}
				
				var formData = $("#searchForm").serializeArray();
				
			}
			var strUrl = "/qnaDelete.do";
			
			rtnCall = ajaxListCall(strUrl,dataProvider, dataRows,ListCallEnd);//삭제call
		}
		
		function ListCallEnd(rtnCall){
			if(!rtnCall){
	   			CallMessage("244","alert",["삭제"]);		//{0}에 실패하였습니다.
	   		}else{
	   			CallMessage("223","alert","",dataResult);	//삭제를 완료하였습니다.
	   		} 
		}
		
		function openPopup(index){
			var gridValus,labelNm;
			if(isNull(index)){
				gridValus = index;
				labelNm = "문의사항 등록";
				/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
				fnOpenPopup("/qna01.do",labelNm,gridValus,callFunPopup);
			}else{
				gridValus = index;
				labelNm = "문의사항 상세보기";
				/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
				fnOpenPopup("/qna02.do",labelNm,gridValus,callFunPopup);
			}
		}
		
		function callFunPopup(url,iframe,gridValus){
		    iframe.gridViewRead(gridValus);
		}
		
		function dataClean(){
			dataProvider.clearRows();
		}
		
		function dataResult(){
			
			gridView.setAllCheck(false);
			
			var sDate = new Date($("#I_FWDT").val());
    		var eDate = new Date($("#I_TWDT").val());
    		var interval = eDate - sDate;
    		var day = 1000*60*60*24;
			
    		// 유효성검사 - 최대 검색 기간 체크 !!
    		var now = new Date();
    	    var tenYearAgo = new Date(now.setFullYear(now.getFullYear() - 10));
    		if(sDate<tenYearAgo || eDate<tenYearAgo){	// 조회 기간이 최근 10년 이전 기간이 포함되었는지 확인.검사운영팀 요청으로 홈페이지 결과 조회는 최근 10년까지만 가능하도록 함.
    			CallMessage("301","alert",["10"], dataClean);		//{0} 조회할 경우,</br>최대 1년 내에서 조회해주세요.      
    			return ;
    	   	}
    		
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
    		
			if($('#I_TIT1').val() == "TITLE"){
				$('#I_TIT').val($('#searchValue').val());
				$("#I_CON").val("");
			} else {
				$('#I_CON').val($('#searchValue').val());
				$("#I_TIT").val("");
			}
			
			var formData = $("#searchForm").serializeArray();
			
			ajaxCall("/qnaList.do", formData);
		}
		
		function onResult(strUrl,response){
 			if(strUrl == "/qnaList.do"){
 				var resultList = response.resultList;
				dataProvider.setRows(resultList, "TREE", true);
				gridView.setDataSource(dataProvider);
				gridView.expandAll();
 			}
		}
		
		function rpyModal(index){
			var gridValus,labelNm;
			gridValus = index;
			labelNm = "문의사항 답변";
			/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			fnOpenPopup("/qnaRe.do",labelNm,gridValus,callFunPopup);
		}
		
		function udtModal(index){
			var gridValus,labelNm;
			gridValus = index;
			labelNm = "문의사항 수정";
			/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
			fnOpenPopup("/qna01.do",labelNm,gridValus,callFunPopup);
		}
		
		/*close 호출*/
		function fnCloseModal(obj){
		    dataResult();
		}
		/* 팝업 호출 및 종료 */
		
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
	                	<div class="col-md-12 srch_box">
	                		<div class="srch_box_inner m-t-10">
	                			<div class="srch_item">
	                				<label for="" class="label_basic">작성일</label>
	                				<span class="period_area">
									    <label for="startDt" class="blind">날짜 입력</label>
									    <input type="text" class="fr_date calendar_put" id="I_FWDT" name="I_FWDT">
									</span>
	                				&nbsp;~&nbsp;
									<span class="period_area">
									    <label for="endDt" class="blind">날짜 입력</label>
									    <input type="text" class="fr_date calendar_put" id="I_TWDT" name="I_TWDT">
	                				</span>
	                			</div>
	                			<div class="srch_item">
	                			<label for="" class="label_basic">답변상태</label>
	                				<div class="select_area">
	                                    <select id="I_PDNOC" name="I_PDNOC" class="form-control">
	                                    </select>
                                    </div>
	                			</div>
	                		</div>
	                		<div class="srch_box_inner m-t-10">
	                			<div class="srch_item">
		                			<div class="select_area">
		                				<select id="I_TIT1" name="I_TIT1" class="form-control"></select>
		                			</div>
		                			<input type="text" id="searchValue" name="searchValue" style="width: 336px;" onKeydown="if(event.keyCode==13) dataResult()" maxlength = "40" >
		                			
	                			</div>
	                			<div class="srch_item">
	                				<label for="" class="label_basic">검색항목</label>
		                			<div class="select_area">
		                				<select id="I_DCL" name="I_DCL" class="form-control"></select>
		                			</div>
	                			</div>
	                		</div>
	                		<div class="btn_srch_box">
	                			<button id="searchBtn" type="button" class="btn_srch">조회</button>
	                		</div>
	                	</div>
	                </div>
           		</div>
            </div>
		</form>
		<!-- realGrid -->
		<div class="container-fluid">
			<div class="con_wrap col-md-12 col-sm-12">
				<!-- title section -->
				<div class="title_area">
					<h3 class="tit_h3">문의사항 조회</h3>
					<div class="tit_btn_area">
						<button type="button" class="btn btn-default btn-sm" onClick="javascript:openPopup();">등록</button>
	                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:qnaDelete();">삭제</button>
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
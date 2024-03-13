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
	<script type="text/javascript" src="../js/menu.js?var=190327001"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/sha256.js"></script>	
	<script src="https://cdn.jsdelivr.net/npm/commonjs-require@1.4.6/commonjs-require.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/indexeddb-export-import@2.1.2/index.min.js"></script>
	<script src="https://kit.fontawesome.com/e73ebbec4d.js" crossorigin="anonymous"></script>
	<%-- <%@ include file="/inc/did.inc"%> --%>
	<% 
		
		if(null != request.getParameter("I_HLK") && !"".equals(request.getParameter("I_HLK"))){
	       response.sendRedirect("/main.do");  
		}
	
		if("Y".equals(ss_iyn)){
	        response.sendRedirect("/session_out.jsp");  
		}
		
		// ===================== 최종 접속일 : 임시 (Start) =====================
		// 접속당일 날짜가 보여지도록 처리함.
		
		Calendar cal = Calendar.getInstance();
		 
		//현재 년도, 월, 일
		int year = cal.get ( cal.YEAR );
		int month = cal.get ( cal.MONTH ) + 1 ;
		int date = cal.get ( cal.DATE ) ;
		
		String month_val = Integer.toString(month);
		String date_val = Integer.toString(date);
		
		if(month_val.length()<2){
			month_val = "0"+month_val;
		}
		if(date_val.length()<2){
			date_val = "0"+date_val;
		}
		String jubsok_dat = year + "-" + month_val + "-" + date_val;
		
		// =================================== 현재날짜와 기준날짜 비교하기 : start ===================================
				
		Date date_today = new Date(year, month, date);
		Date date_lastday = new Date(2022, 10, 19);
		String customerPopupLink_print_yn = "N";
		
		int date_compare = date_today.compareTo(date_lastday);
		
		if(date_compare <= 0){
			customerPopupLink_print_yn = "Y";
		}
		// =================================== 현재날짜와 기준날짜 비교하기 : end ===================================
		
		String I_DID = (String) session.getAttribute("I_DID");
		
		String publicKeyModulus = (String) request.getAttribute("publicKeyModulus");
		String publicKeyExponent = (String) request.getAttribute("publicKeyExponent");
		if("".equals(Utils.isNull(publicKeyModulus))){
			publicKeyModulus = (String) session.getAttribute("publicKeyModulus");
			publicKeyExponent = (String) session.getAttribute("publicKeyExponent");
		}
		
		// ===================== 최종 접속일 : 임시 (End) =====================
	%>
	<script>	

	function parentMag(type,yesFn,strMessage){
		
		if(type=="confirm"){
	        $.message.confirm({
				title: 'Confirm',
	            contents : strMessage,
	            onConfirm: function () {
	        		setTimeout(function() {
	        			yesFn();
	        		}, 400);
	            },
	            onCancel: function () {
					rtnBool = false;
	            }
	        });
		}else{
			$.message.alert({
	            title : 'Alert',
	            contents : strMessage,
	            onConfirm: function () {
	        		if(typeof yesFn == "function"){
		        		setTimeout(function() {
		        			yesFn();
		        		}, 400);
	            	}
	            }
	        });
		} 
		
	}
	//페이지 삭제 여부
	var is_page_del = false;
	//이전 mdi
	var pre_menu_cd = null;
	//열린 MDI 메뉴 페이지 url들
	var open_pages_url = "";
	var FavoritesList;
	var treeMenu;

	$(document).ready(function(){
	    I_LOGMNU = "MAIN";
		I_LOGMNM = "메인";
		$("#I_LOGMNU").val(I_LOGMNU);
		$("#I_LOGMNM").val(I_LOGMNM);
		//로그아웃
		$("#logout").click(function(){
		 	location.assign("/logout.do");
		});
		callLoading(null,"on");
 		var formData = $("#MainForm").serializeArray();
 		ajaxAsyncCall("/menuList.do", formData,false);

 	    $('#book_mark').popoverButton({
 	        placement: 'top top-left', 
 	        target: '#bookmark-list'
 	    });
   		getNotice();

/*****************************************************   		
   		// DID 프로젝트 초기화
   		initDid();
*****************************************************************/   		
   		$("#ids-chat").on("click keyup",function(){
			//console.log("click");
			var options = 'top=0, left=0, width=434, height=900, status=no, menubar=no, toolbar=no, resizable=no, scrollbars=no';
			var userid = $("#I_IDSCHATID").val(); 
			$("#idschat_formIO").remove();
			$.ajax({
				 url : "chatbotLoginKey.do"
				, type : "post"
				, data : {
					"I_UID" : userid
				}
				, dataType: "json"
				, success : function(response){
					if(response.O_MSGCOD == "200"){
						$.ajax({
							 url : "loginCheck.do"
							, type : "post"
							, data : {
								"I_UID" : userid,
								"I_HCK" : response.I_HCK,
							}
							, dataType: "json"
							, success : function(response2){
							console.log(response2);
								if(response2.data.O_MSGCOD == "200"){
									window.open("", "IDS 챗봇", options);
									//개발
									//var frmObj = $('<form>', {'id': 'idschat_formIO', 'action': 'http://219.252.39.18:9090/ids-cs-chatbot/serviceChat', 'method':'POST', 'target':'IDS 챗봇'});
									//운영
									var frmObj = $('<form>', {'id': 'idschat_formIO', 'action': 'https://chatbot.seegenemedical.com/ids-cs-chatbot/serviceChat', 'method':'POST', 'target':'IDS 챗봇'});
									var inpObj = $('<input>', {'name':'userId', 'value':userid, 'style':"display:none"});
	 									frmObj.append(inpObj);
	 									inpObj = $('<input>', {'name':'hspNm', 'value':response2.data.O_HOS_NM, 'style':"display:none"});
										frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'hosCode', 'value':response2.data.O_HOS_HCD, 'style':"display:none"});
	 									frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'hspGic', 'value':response2.data.O_HOS_GIC, 'style':"display:none"});
										frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'hspDrn', 'value':response2.data.O_HOS_DRN, 'style':"display:none"});
										frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'hspTel', 'value':response2.data.O_HOS_TEL, 'style':"display:none"});
										frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'hosDpt', 'value':response2.data.O_HOS_DPT, 'style':"display:none"});
										frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'hosDptNm', 'value':response2.data.O_HOS_DPT_NM, 'style':"display:none"});
										frmObj.append(inpObj);
										inpObj = $('<input>', {'name':'lmbCode', 'value':response2.data.O_HOS_CVL, 'style':"display:none"});
										frmObj.append(inpObj);
										$(document.body).append(frmObj);
										$('#idschat_formIO').submit();
								}else{
									$.message.alert({
							            title : 'Alert',
							            contents : "병원정보를 읽어오는데 실패하였습니다.",
							            onConfirm: function () {
							        		if(typeof yesFn == "function"){
								        		setTimeout(function() {
								        			yesFn();
								        		}, 400);
							            	}
							            }
							        });
						        }
							},error:function(x2,e2){
							 	return x2.responseJSON;
							}
						});
					}else{
						$.message.alert({
				            title : 'Alert',
				            contents : "챗봇로그인에 실패하였습니다.",
				            onConfirm: function () {
				        		if(typeof yesFn == "function"){
					        		setTimeout(function() {
					        			yesFn();
					        		}, 400);
				            	}
				            }
				        });
					}
					//if(response.reslut == true){
					//	window.open("/ids-cs-chatbot/serviceChat?userId=" + userid, "IDS 챗봇", options);			
					//} 
					return false;
				},error:function(x,e){
				 	return x.responseJSON;
				}
			});
		});

   		
	})
	
	//창크기에 따라 이미지로 된 버튼 메뉴 크기 변경 처리  ========== Start ==========
	var callback = function (){
	//$(window).resize(function() {
		if($(window).width() > 1220){
  			$("#background_img").attr("src", "/images/common/ico_bg_item_lst14.jpg");
  			$('#background_div').css('height', 40);
  			$('#background_div').css('width', 200);
  			$('#background_img').css('padding-top', 0);
  			$('#background_img').css('padding-left', 0);
  		}else{
  			$("#background_img").attr("src", "/images/common/ico_bg_item_lst_check.png");
  			$('#background_div').css('height', 50);
  			$('#background_div').css('width', 50);
  			$('#background_img').css('padding-top', 20);
  			$('#background_img').css('padding-left', 22);
  		}	
 	//});
	}
	
	$(document).ready(callback);
	$(window).resize(callback);
	
	//창크기에 따라 이미지로 된 버튼 메뉴 크기 변경 처리  ========== End ==========
	
	/* 공지/공문 팝업 */
	function getNotice(){
		var formData = $("#MainForm").serializeArray();
		ajaxAsyncCall("/mainNotice.do", formData,false);
	}

	/* 즐겨찾기 조회 */
	function getFavorites(){
		var formData = $("#MainForm").serializeArray();
		ajaxAsyncCall("/Favorites.do", formData,false);
	}
	
	
	// ajax 호출 result function
	function onResult(strUrl,response){

// 		loadingOff();
		if(strUrl == "/menuList.do"){
			 // Left Menu
			treeMenu = response.resultList;
		 	var main_area_div = $("#main_area");
			var menu_area_div = $("#menu_area");
		 	 
		 	//footer
		 	main_area_div.height($( document ).height() - $( ".footer" ).outerHeight());
		 	menu_area_div.height($( document ).height() - $( ".footer" ).outerHeight()-15);
		 	//메인 내용 불러오기
		 	//main_area_div.load("/main_area.do");
		 	main_area_div.removeClass("display_off");
		 	main_area_div.addClass("display_on");
			//document.getElementById("main_conts").src = "/sysUserLog.do";
 		 	//document.getElementById("main_conts").src = "/mainCont.do";
 		 	//document.getElementById("main_conts").src = "/rstUser.do"; //수진자별결과조회
 		 	//document.getElementById("main_conts").src = "/ocsUpload.do"; // 의뢰내역 접수(OCS) 		 	
 		 	
 		 	
 		 	//================================================================================================================================================
 		 	// 메인페이지를 수진자결과조회 화면으로 이동 - 설정 : start
 		 	//================================================================================================================================================
 		 	if("<%=ss_agr%>".indexOf("OLD") > -1){
 		 		goPage('RSTUSERTABLE','수진자별 결과조회(B)','rstUserTable.do');
 		 	}else{
 		 		goPage('RSTUSER','수진자별 결과조회(A)','rstUser.do');
 		 	}
 		 	
 		 	callLoading(null, "off");
 		 	//================================================================================================================================================
 		 	// 메인페이지를 수진자결과조회 화면으로 이동 - 설정 : end
 		 	//================================================================================================================================================

 		 	//================================================================================================================================================
 		 	// 네오티커 홈페이지 대신 로그인시 팝업창 바로 띄우기 : start
 		 	// 병원 사용자 'C' 일 경우 팝업창 띄우기
 		 	// 병원별 조건에 추가된 병원만 티커 팝업이 띄어지도록 한다. ex)15710
 		 	//================================================================================================================================================
 			
 		 	// 제외요청
 		 	// 20191017 - 병원 (21207)
 		 		<%-- console.log("ss_ecf : "+"<%=ss_ecf%>"); --%>
 		 		<%-- console.log("ss_ticker_yn : "+"<%=ss_ticker_yn%>"); --%>
 		 	if("<%=ss_ecf%>"=="C"
 				&& "<%=ss_ticker_yn%>"=="Y"
 				<%-- ("<%=ss_hos%>"=="15710" 
 					|| "<%=ss_hos%>"=="14620" || "<%=ss_hos%>"=="16160" || "<%=ss_hos%>"=="16360" || "<%=ss_hos%>"=="17086" || "<%=ss_hos%>"=="17939"
 					|| "<%=ss_hos%>"=="18046" || "<%=ss_hos%>"=="20955" || "<%=ss_hos%>"=="22189" || "<%=ss_hos%>"=="22357"
 					|| "<%=ss_hos%>"=="22390" || "<%=ss_hos%>"=="23535" || "<%=ss_hos%>"=="24207" || "<%=ss_hos%>"=="24604" || "<%=ss_hos%>"=="24916"
 					|| "<%=ss_hos%>"=="25054" || "<%=ss_hos%>"=="26475" || "<%=ss_hos%>"=="26537" || "<%=ss_hos%>"=="29491" || "<%=ss_hos%>"=="30039"
 					|| "<%=ss_hos%>"=="30253" || "<%=ss_hos%>"=="30879" || "<%=ss_hos%>"=="30919" || "<%=ss_hos%>"=="31003" || "<%=ss_hos%>"=="31067"
 					|| "<%=ss_hos%>"=="31288" || "<%=ss_hos%>"=="31330" || "<%=ss_hos%>"=="31440" || "<%=ss_hos%>"=="31557" || "<%=ss_hos%>"=="31757"
 					//20190801 - 추가
 					|| "<%=ss_hos%>"=="18116" || "<%=ss_hos%>"=="28080"
 					//20190809 - 추가
 	 				|| "<%=ss_hos%>"=="13936" || "<%=ss_hos%>"=="26348" || "<%=ss_hos%>"=="29832"
 	 				//20190813 - 추가
 	 	 			|| "<%=ss_hos%>"=="11811" || "<%=ss_hos%>"=="25723" || "<%=ss_hos%>"=="19316" || "<%=ss_hos%>"=="16842"
 	 	 			//20190819 - 추가
 	 				|| "<%=ss_hos%>"=="22134"
 	 				//20190821 - 추가
 	 	 			|| "<%=ss_hos%>"=="26648"
 	 	 			//20190823 - 추가
 	 	 	 		|| "<%=ss_hos%>"=="24999" || "<%=ss_hos%>"=="15493"
 	 	 			//20190904 - 추가
 	 	 	 		|| "<%=ss_hos%>"=="13101"
 	 	 	 		//20190927 - 추가
 	 	 	 	 	|| "<%=ss_hos%>"=="14380"
 	 	 	 	 	//20191104 - 추가
 	 	 	 	 	|| "<%=ss_hos%>"=="33156"
 	 	 	 	 	//20191105 - 추가
 	 	 	 	 	|| "<%=ss_hos%>"=="26420"
 	 	 	 	 	//20200109 - 추가
 	 	 	 	 	 	|| "<%=ss_hos%>"=="33334"
 				   ) --%>
 			){ 
					// 특정 병원 코드 만 네오티커 대행 팝업 호출 				
	 				window.open("rstUserTableMini01.do", "mini", "width=375px, height=590px, scrollbars=yes, resizable=no");
 				 
					// 티커 팝업이 뜨면 부모창은 홍보site로 페이지가 열리도록 한다.
	 				/* window.open('http://www.seegenemedical.com','_self').close();
	 				window.close(); */
 			}
 			
 		 	//================================================================================================================================================
 		 	// 네오티커 홈페이지 대신 로그인시 팝업창 바로 띄우기 : end	
 		 	//================================================================================================================================================
 		 	
 		 	// ****************************
 		 	// 챗봇 미사용 
 			// ****************************
 		 	if("<%=ss_chatbot_yn%>"=="Y"){
 		 		$("#ids-chat").hide();
 		 	}	
 		 		
 			getFavorites();
		    displayMenu(response.resultList); // 메뉴 호출
		    
		    	
		}
		else if(strUrl == "/Favorites.do"){
			/* 즐겨찾기 */
			FavoritesList = response.resultList;
			var html = "";
		    for (var i in FavoritesList)
		    {
		        html += "<li><a href=\"#\" onClick=\"javascript:goPage('" + FavoritesList[i].S006MCD + "','"+FavoritesList[i].S001MNM +"','" +FavoritesList[i].S001PTH + "')\" >"+FavoritesList[i].S001MNM+"</a></li>";
		    }
		    $(".bookmark_lst").html(html);
			
		}
		else if(strUrl == "/mainNotice.do"){
			var NoticeDiv = "";
			var NoticeList = response.resultList;
			var NoticeId,NTop,Nleft;
		    for (var i in NoticeList)
		    {
		    	NoticeId =  NoticeList[i].S014WDT+""+NoticeList[i].S014SEQ ;
		    	NTop =  NoticeList[i].S014TCO ;
		    	Nleft =  NoticeList[i].S014LCO ;
		    	var cookieVal = getCookie(NoticeId);
		    	if(cookieVal!="T"){
					NoticeDiv +="\n	<div id=\""+NoticeId+"\" class=\"ly_pop\" style=\"position:absolute;top:"+NTop+"px;left:"+Nleft+"px;\">";
					NoticeDiv +="\n		<div class=\"pop_wrap\">";
					NoticeDiv +="\n	        <div class=\"pop_header\"  onClick=\"NoticeIdx('"+NoticeId+"')\"\>";
					NoticeDiv +="\n	            <h4>"+NoticeList[i].S002DCL+"</h4>";
					NoticeDiv +="\n	        </div>";
					NoticeDiv +="\n	        <div class=\"pop_content\">";
					NoticeDiv +="\n	        <div class='pop_line'></div>";
					NoticeDiv +="\n	        	<div>";
					NoticeDiv +="\n	        		<h5 class=\"tit_h5\">"+NoticeList[i].S014TIT+"</h5>";
					NoticeDiv +="\n	        		<input id=\"SEQ_"+NoticeId+"\" value=\""+NoticeList[i].S014SEQ+"\" type=\"hidden\">";
					NoticeDiv +="\n	        	</div>";
					NoticeDiv +=  NoticeList[i].S014CON;
					if(!isNull(NoticeList[i].S014FNM)){
						NoticeDiv +="\n	        	<div style=\"padding-bottom:20px;\">";
						NoticeDiv +="\n	        		<h5 class=\"tit_h5\">첨부파일</h5>";
						NoticeDiv +="\n	        		<ul> <li><a href=\"#\" onClick=\"downNotice('"+NoticeId+"')\" >"+NoticeList[i].S014FNM+"</a></li>";
						NoticeDiv +="\n	        		<input id=\"P_"+NoticeId+"\" value=\""+NoticeList[i].S014FPT+"\" type=\"hidden\">";
						NoticeDiv +="\n	        		<input id=\"NM_"+NoticeId+"\" value=\""+NoticeList[i].S014FNM+"\" type=\"hidden\">";
						NoticeDiv +="\n	        		</ul>";
						NoticeDiv +="\n	        	</div>";
					}
					NoticeDiv +="\n	        <div class='pop_line'></div>";
					NoticeDiv +="\n	        </div>";
					NoticeDiv +="\n	        <div class=\"pop_foot\"> ";
					NoticeDiv +="\n	        	<div class=\"pop_foot_close\">";
					NoticeDiv +="\n	                <div class=\"fl\"><input type=\"checkbox\" id=\"T_"+NoticeId+"\"  > <label for=\"T_"+NoticeId+"\">오늘 그만보기</label></div>";
					NoticeDiv +="\n	                <div class=\"fr\"><a href=\"#\" onClick=\"closeNotice('"+NoticeId+"','"+NoticeList[i].S014RYN+"','Y')\"  class=\"pop_clse\">닫기</a></div>";
					NoticeDiv +="\n	            </div>";
					NoticeDiv +="\n	        </div>";
					NoticeDiv +="\n	        <a href=\"#\" onClick=\"closeNotice('"+NoticeId+"','"+NoticeList[i].S014RYN+"','N')\" class=\"pop_clse\">닫기</a>";
					NoticeDiv +="\n	    </div>";
					NoticeDiv +="\n	</div>";
		    	}
		    }
		    
		    $("#NoticeDiv").html(NoticeDiv);
	        // 레이어 팝업을 띄웁니다.
	        $('.ly_pop').show();
		}else if(strUrl == "/FavoritesSave.do" || strUrl == "/FavoritesDel.do"){
		    $(".bookmark_lst").html("");
			 getFavorites();
		}
	}
	function NoticeIdx(NoticeId){
		///ly_pop
	    $(".ly_pop").css("z-index","1000");
	    $("#"+NoticeId).css("z-index","2000");
		
		
		
	}
	function downNotice(NoticeId){
		var path = $("#P_"+NoticeId).val();
		var fileNm = $("#NM_"+NoticeId).val();
		FileDown(path,fileNm);
	}
	function closeNotice(NoticeId,S014RYN,clseYN){
		$("#"+NoticeId).hide();
		if(clseYN=="Y"){
			if($("#T_"+NoticeId).prop("checked")){
				setCookie(NoticeId, "T", 1);
			}
			if(S014RYN=="0"){
				noticeRead(NoticeId);
			}
		}
	}
	
	function noticeRead(NoticeId){
		var formData = $("#MainForm").serializeArray();
		formData.push({ name: "I_SEQ",value : $("#SEQ_"+NoticeId).val()});
		formData.push({ name: "I_RUR",value : "<%=ss_uid %>"});
		ajaxCall("/noticeRead.do", formData,false);
	}
	function downHelp(){
		//var formData = "";
		callPagePop = "main";
		var formData = $("#MainForm").serialize();
		fnOpenPopup("/sysHelpView.do","도움말",formData,callFunPopup);
		/*
		if(!isNull( $("#S013FNM").val())){
			var path = $("#S013FPT").val();
			var fileNm = $("#S013FNM").val();
			FileDown(path,fileNm);
		}
		*/
	}
	
	function FileDown(path,fileNm){
		$.when(function () {
	        var def = jQuery.Deferred();
	        window.setTimeout(function () {
	        	document.getElementById('file_down').src = "/comm_fileDown.do?file_path="+path+"&file_name="+encodeURIComponent(fileNm);
	            def.resolve();
	        }, 100);
	        return def.promise();
	    }())
	    .done(function () {
	    });
	}

	/**사용자 정보수정**/
	function UserModify(){
		
		if("<%=ss_ecf%>"=="E"){
			CallMessage("284");	// 씨젠 사원의 상세정보는 확인할 수 없습니다.
			return;
		}
		
		callPagePop = "main";
		$('#pageiframe').unbind("load");
		$('#pageModalView').modal('show');
		$('#pageiframe').attr("src" , "/sysUserMng02.do?menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM));
		$('#pageModalLabel').html("사용자 정보 수정");
	    $('#pageiframe').off().load(function(e){
	    	var pageSrc = $('#pageiframe').attr("src" );
			if(pageSrc.indexOf("/sysUserMng02.do")>-1){
				var iframe = $(e.target)[0].contentWindow;
				iframe.gridViewRead();
			}
		});	
	    $('#pageModalView').off().on('shown.bs.modal', function (e) {
	    	try{
	    		$('#pageiframe', parent.document)[0].contentWindow.callResize()  ;
	    	}catch (e) {  }
	    	
	    });
	}
	
	/* 팝업 호출 및 종료 */
	function openPopup(){
		callPagePop = "main";
		var gridValus,labelNm,strUrl;
		strUrl = "/mainQcSet.do";
		labelNm = "메인 Quick Setup";

		/** 팝업 호출 fnOpenPopup("팝업호출 주소,"상단라벨명","전달데이터","callback 호출")  **/
		fnOpenPopup(strUrl,labelNm,gridValus,callFunPopup);
	}
	
	/*callback 호출 */
	function callFunPopup(url,iframe,gridValus){
		if(isNull(gridValus)){
			gridValus = '';
		}
		if(url == "/sysHelpView.do"){
			iframe.dataResult();	
		}
	}
	
	/* 고객만족도조사참여 - 2019.11.08 */
	/* 고객만족도조사참여  - 2022.10.11 */
	function customerPopupLink(){
		
		var now = new Date();
	    var date1 = new Date('2022-10-12 00:00:00');
	    var date2 = new Date('2020-10-19 24:00:00');
	    
	    if(now <= date1){
	    	alert("설문조사 기간이 아닙니다.");
	    }else{
	    	if("<%=ss_ecf%>"=="C"){
				var url = 'https://isurvey.panel.co.kr/?Alias=7064269278&panel_id='+"<%=ss_uid %>";
			    window.open(url,'popupView2','top=100px, left=100px, height=800px, width=800px'); 
			}else{
				alert("병원 사용자만 이용 가능합니다.");
			}
	    }
	    
	}
	
	
<%------------------------------------------- 
	/* DID 연동 프로젝트 - 2021 */
	async function initDid(){
		I_DID = "<%=I_DID %>";
		$("#I_DID").val(I_DID);
		if($("#I_DID").val() == "Y"){
   	   		var userid = "<%=ss_uid %>";
   	   		//console.log("IndexedDB 세팅");
   		 	//console.log("[4.1.2 : Holder DID 조회]")
   		 	await NdsApi.initIndexedDB();
   		 	try{
  	  		 	// 성공시
	   		 	let response = await NdsApi.getDidHolder(userid);
	   		 	//console.log(response);
	   		 	await NdsApi.getIndexedDB().createHolderDid(userid, response);
  		 	}catch(x){
  	  		 	// 서버오류 실패시 이쪽으로...
				//console.log(x);
				if(x.responseJSON.success == false){
					//console.log(x.responseJSON.message);
					callPagePop = "main";
					var formData = $("#MainForm").serialize();
					fnOpenPopup("/didIntro.do","DID 소개",formData,callFunPopup);
				}
 			}
   	   	}
	} 
-----------------------------------------------------%>
	</script>
	
</head>
<body>
	<form id="pageFrom" name="pageFrom" method="get">
		<input id="param" name="param" type="hidden"/>
		<input id="menu_cd" name="menu_cd" type="hidden"/>
		<input id="menu_nm" name="menu_nm" type="hidden"/>
		<input id="Quick" name="Quick" type="hidden"/>
	</form>
	<form id="MainForm" name="MainForm">
		<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
		<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
		<input id="I_DID" name="I_DID" type="hidden"/>
		<input id="S013FPT" name="S013FPT" type="hidden"/>
		<input id="S013FNM" name="S013FNM" type="hidden"/>
		<input id="I_SDV" name="I_SDV" 	value="TEST_RESULT" type="hidden"/><%--공지 조회 조건 검사결과관리 --%>
	</form>
	<!-- wrap -->
	<div id="wrap">
		<!-- wrap_inner -->
	    <div id="wrapper" class="wrap_inner">
			<div class="m-top">
				<h1 class="m-top_logo"><a href="/main.do" class=""><img src="../images/common/mobile-logo.png" alt="재단법인 씨젠의료재단"></a></h1>
				<div class="btn_menu_area">
					<a class="btn_menu" href="#">
						<span></span>
						<span></span>
						<span></span>
					</a>
				</div>
			</div>
	        <!-- side_wrap -->
	    	<div class="side_wrap left side-menu">
	        	<!-- top_area -->
	        	<div class="top_area">
	        		<h1 class="top_logo"><a href="/main.do" class="top_logo_link"><img src="../images/common/logo.png" alt="재단법인 씨젠의료재단"></a></h1>
	                <div class="user_area">                	
	                	<p class="user_name"><span style="color: #FFE400; font-weight: bold;"><%=ss_nam %></span>님 환영합니다.</p>
	                    <%-- <p>최종 접속일 : <span><%=ss_cdt %></span></p> --%>
	                    <p>최종 접속일 : <span><%=jubsok_dat%></span></p>
	                </div>
	                <ul class="top_util">
	                	<li>
	                    	<a href="#" class="" onclick="UserModify()">정보수정</a>
	                    </li>
	                    <li>
	                    	<a href="#" id="logout">Logout</a>
	                    </li>
	                </ul>                
	            </div>
	            <!-- top_area end -->
	            <button type="button" class="button_close"><span class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span></button>
	            <button type="button" class="button_menu"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></button>
	            
	<%
		// 2022-10-13 :: 특정 병원에서 고객만족도조사참여 버튼 보이지 않도록 처리 요청하여 반영함
   		if( "Y".equals(customerPopupLink_print_yn) && !"18958".equals(ss_hos)	// 강동연세내과, 해당 병원만 고객만족도조사참여 버튼 제외 요청.
   		){	
   	%>    
	            <!-- 설문조사 링크 및 팝업 (고객만족도조사참여) start -2019.11.08- -->
	            <!-- 설문조사 링크 및 팝업 (고객만족도조사참여) start -2022.10.11- -->
	             <div id = "background_div" class="side_menu left">
		            <li style="width: auto;height:57px;" align="center;">
		            	<a href="#" onclick="customerPopupLink()" id="customerPopupLink" class="customerPopupLink" >
		            		<img id="background_img" src="/images/common/ico_bg_item_lst14.jpg" alt="고객만족도조사참여" />
		            	</a>
					</li>
				</div> 
				<!-- 설문조사 링크 및 팝업 (고객만족도조사참여) end -2019.11.08- -->
				<!-- 설문조사 링크 및 팝업 (고객만족도조사참여) end -2022.10.11- -->
	<%
   		}	
   	%>			
				<!-- side-menu -->
	            <div class="side_menu left">
	            	<!-- slim_scroll_area -->
	            	<div class="slim_scroll_area">
	                	<h2 class="blind">대메뉴</h2>
	                	<ul class="left-menu-item">
	                        
	                    </ul>
	                    
	                    <div class="side_quick_menu">
	                        <ul class="quick_lst">
	                            <li>
	                                <a href="#" id="book_mark" class="book_mark">즐겨찾기</a>
	                            </li>
	                            <li>
	                                <a href="#" class="set" data-toggle="tooltip" data-placement="top" title="Quick Setup" onClick="openPopup()">Quick Setup</a>
	                            </li>
	                            <li>
	                                <a href="#" class="help" data-toggle="tooltip" data-placement="top" title="도움말" onclick="downHelp()">도움말</a>
	                            </li>
	                        </ul>
	                        <div id="bookmark-list" class="popover popover-default bookmark_popover" >
	                            <div class="arrow"></div>
	                            <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>즐겨찾기목록</h3>
	                            <div class="popover-content bookmark_popover-content" data-trigger="focus" >
	                                <ul class="bookmark_lst"><%-- 즐겨찾기 목록 --%>
	                                </ul>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <!-- slim_scroll_area end -->
	            </div>
	            <!-- side-menu end -->
	        </div>
	        <!-- side_wrap end -->
	        
	        <!-- container_wrap -->
	        <div id="container_wrap" >
	        	
				<!-- 메인 내용 내용 시작 -->
				<div class="display_off" id="main_area" style="overflow: hidden;">
		        	<div class="content_wrap" style="height: 100%">
						<iframe id="main_conts" style="height:100%;width:100%;border:0;" scrolling="no"></iframe>
					</div>
				</div>
				<!-- 메인 내용 내용 끝 -->
				<div class="display_off" id="menu_area" >
				
		            <div class="tab_area">
		            	<div class="bookmark_tab">
		                    <!-- Nav tabs -->
		                    <ul class="nav nav-tabs" role="tablist" id="tablist">
		                    </ul>
		                    <!-- Nav tabs end -->
		                </div>
		            </div>
		        	
		            <!-- content_wrap -->
		        	<div class="content_wrap tab-content" id="mdi_tab_menu_con" style="height: 100%;">
						<!-- tab menu 내용 시작 -->
						<!-- tab menu 내용 끝 -->
		            </div>      
		            <!-- content_wrap end -->
	            </div>      
	            <div style="height: 50px;" id="copyFoot"></div>
				<!-- footer -->
				<footer class="footer">
				    Copyright &copy; Seegene Medical Foundation. All right reserved.
				</footer>
				<!-- footer end -->  
	                
		    </div>
		    <!-- wrap_inner end -->
		</div>
		<!-- wrap end -->    
	</div>
	<!-- container_wrap end -->   
	<%-- 첨부파일 다운로드 --%> 
	<iframe id="file_down" src="" width="0" height="0" frameborder="0" scrolling="no" style="display: none;"></iframe>
	<%-- 첨부파일 다운로드 --%> 
	

	<%-- 팝업 --%> 
	<div class="modal fade" id="pageModalView" tabindex="-1" role="dialog" aria-labelledby="pageModalLabel" data-backdrop="static" aria-hidden="true">
	    <div class="modal-dialog"  id="popCont">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnclose()">
	                <span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title" id="pageModalLabel"></h4>
	            </div>
	            <div class="modal-body" id="popBody">
		        	<iframe id="pageiframe" src="" style="width:100%; overflow:hidden;"  frameborder="0" scrolling="no"></iframe>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<%-- 팝업 --%> 

	<%-- 팝업2 --%> 
	<div class="modal fade" id="pageModalView2" tabindex="-1" role="dialog" aria-labelledby="pageModalLabel2" data-backdrop="static" aria-hidden="true">
	    <div class="modal-dialog"  id="popCont2">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnclose2()">
	                <span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title" id="pageModalLabel2"></h4>
	            </div>
	            <div class="modal-body" id="popBody2">
		        	<iframe id="pageiframe2" src="" style="width:100%; overflow:hidden;"  frameborder="0" scrolling="no"></iframe>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<%-- 팝업2 --%> 
	<div id="NoticeDiv">
	</div>
	
	<%-- <%
   		if("ADMIN".equals(ss_agr) 				// 관리자
   			|| "IT".equals(ss_agr)				// 정보지원본부
   			|| "SUPPORT".equals(ss_agr)			// 고객지원
   			|| "SUPPORT_REALTIME".equals(ss_agr)// 고객지원(실시간현황)
   			|| "BRC_MNG".equals(ss_agr)			// 지점장
   			|| "HOSP_NMG".equals(ss_agr)		// 병원담당자
   			|| "LAB".equals(ss_agr)				// 검사실
   			|| "LAB_REALTIME".equals(ss_agr)	// 검사실(실시간현황)
   		){	
   	%> --%>                        	
		<%-- IDS 챗봇 버튼 --%>
		<div id="ids-chat">
		  <a class="ids-chat-open" data-tooltip="Contact Us" data-placement="left">
			<i class="far fa-comment-dots"> 
				<input id="I_IDSCHATID"  name="I_IDSCHATID"  type="hidden" value="<%=ss_uid%>"/>
			</i>
			</a>
		</div>
	<%-- <%
   		}	
   	%> --%>
	
</body>
</html>


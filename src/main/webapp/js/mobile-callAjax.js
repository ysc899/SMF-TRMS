
var NotLoadingSrc = "|/getCommCode.do|/Favorites.do|/helpFile.do|/menuList.do|/loginUser.do|/noticeRead.do|/mainNotice.do|/MenuQcSetList.do";

function goReload(){
	try{
		parent.location.reload("/");
	}catch (e) {
		location.reload("/");
		// TODO: handle exception
	}
//	window.location.reload();
}
function callLoading(strUrl,flag){
//	console.log(strUrl,flag);
	
	if(NotLoadingSrc.indexOf(strUrl) == -1){
		
		if(flag == "on"){
			try{
				window.parent.loadingOn();
			}catch (e) {
				loadingOn();
			}
		}else{
		 	try{
		 		window.parent.loadingOff();
		 	}catch (e) {
		 		loadingOff();
		 	}
		}
	}
}

	// 공통코드 가져오기 (국문)
	function getCode(I_LOGMNU, I_LOGMNM, I_PSCD, ALLYN){
	
	var optionList = "";
		$.ajax({
			 url : "/getCommCode.do"
			, type : "post"
			//, data : {"I_COR" : "NML", "I_UID" : "twbae","I_IP" : "100.100.40.183","I_MCD" : $("#searchWordCode").val(), "I_MNM" : $("#searchWordName").val()}
			, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : I_PSCD}
			, dataType: 'json'
			, async:false
			, success : function(response){
				var resultList =  response.resultList;
				if(ALLYN == 'Y'){
					optionList += '<option value="">전체</option>';
					$.each(resultList, function(i, e) {
						optionList += '<option value="'+e.$R_SCD+'">'+e.$R_CNM+ '</option>';
					});
				} else {
					$.each(resultList, function(i, e) {
						optionList += '<option value="'+e.$R_SCD+'">'+e.$R_CNM+ '</option>';
					});
				}
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
		return optionList;
	}

	// 공통코드 가져오기 (영문)
	function getCodeEng(I_LOGMNU, I_LOGMNM, I_PSCD, ALLYN){
		var optionList = "";
		
		$.ajax({
			 url : "/getCommCode.do"
			, type : "post"
			//, data : {"I_COR" : "NML", "I_UID" : "twbae","I_IP" : "100.100.40.183","I_MCD" : $("#searchWordCode").val(), "I_MNM" : $("#searchWordName").val()}
			, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : I_PSCD}
			, dataType: 'json'
			, async:false
			, success : function(response){
				var resultList =  response.resultList;
				if(ALLYN == 'Y'){
					optionList += '<option value="">All</option>';
					$.each(resultList, function(i, e) {
						optionList += '<option value="'+e.$R_SCD+'">'+e.$R_CNM+ '</option>';
					});
				} else {
					$.each(resultList, function(i, e) {
						optionList += '<option value="'+e.$R_SCD+'">'+e.$R_CNM+ '</option>';
					});
				}
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
		return optionList;
	}

	/*메일 관련 공통코드 추가*/
	function getRefCode(I_LOGMNU, I_LOGMNM, I_PSCD, ALLYN){
		var optionList = "";
		$.ajax({
			 url : "/getCommCode.do"
			, type : "post"
			//, data : {"I_COR" : "NML", "I_UID" : "twbae","I_IP" : "100.100.40.183","I_MCD" : $("#searchWordCode").val(), "I_MNM" : $("#searchWordName").val()}
			, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : I_PSCD}
			, dataType: 'json'
			, async:false
			, success : function(response){
				var resultList =  response.resultList;
				if(ALLYN == 'Y'){
					optionList += '<option value="">전체</option>';
					$.each(resultList, function(i, e) {
						optionList += '<option value="'+e.$R_RF1+'">'+e.$R_CNM+ '</option>';
					});
				} else {
					$.each(resultList, function(i, e) {
						optionList += '<option value="'+e.$R_RF1+'">'+e.$R_CNM+ '</option>';
					});
				}
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
		return optionList;
	}

	//공통 단일 ajaxCall 설정
	function ajaxAsyncCall(strUrl,formData, lodingFlag){
		if(!isNull(lodingFlag)){
			lodingFlag = lodingFlag?"":lodingFlag;
		}
		if(isNull(lodingFlag)){
			callLoading(strUrl,"on");
		}
		setTimeout(function() {
			realAjaxAsyncCall(strUrl,formData, lodingFlag);
		}, 200);
	}
	//공통 단일 ajaxCall 설정
	function realAjaxAsyncCall(strUrl,formData, lodingFlag){
		
		$.ajax({
			 url : strUrl
			, dataType: 'json'
			, type : "post"
			, data : formData
			, beforeSend : function(xmlHttpRequest){
				xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
			}
			, async: true
			, success : function(response){
			 	var msgCd = response.O_MSGCOD;
				if(msgCd != "200"){
					if(!isNull(response.strMessage)){
						 messagePopup("","",response.strMessage);
					}else{
//						 messagePopup("","","Controller의 return이 List<Map<String, Object>> 입니다.\n param으로 return해주세요. \n 메세지코드가 없습니다.");
//						 callLoading(strUrl,"off");
//						 return;
					}
				}
				if(isNull(lodingFlag)){
					callLoading(strUrl,"off");
				}
//				callLoading(strUrl,"off");
				onResult(strUrl,response);
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
	}
	
	
	//공통 단일 ajaxCall 설정
	function ajaxCall(strUrl,formData, lodingFlag){
		if(!isNull(lodingFlag)){
			lodingFlag = lodingFlag?"":lodingFlag;
		}
		if(isNull(lodingFlag)){
			callLoading(strUrl,"on");
		}
		setTimeout(function() {
			realAjaxCall(strUrl,formData,lodingFlag);
		}, 200);
	}
	
	function realAjaxCall(strUrl,formData,lodingFlag){
		$.ajax({
			 url : strUrl
			, dataType: 'json'
			, type : "post"
			, data : formData
			, beforeSend : function(xmlHttpRequest){
			   xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
		   }
			, async: false
			, success : function(response){
			 	var msgCd = response.O_MSGCOD;
				if(msgCd != "200"){
					if(!isNull(response.strMessage)){
						 messagePopup("","",response.strMessage);
					}else{
//						 messagePopup("","","Controller의 return이 List<Map<String, Object>> 입니다.\n param으로 return해주세요. \n 메세지코드가 없습니다.");
//					 	 callLoading(strUrl,"off");
//						 return;
					}
				}
				if(isNull(lodingFlag)){
					callLoading(strUrl,"off");
				}
				if("/MenuQcSetList.do" == strUrl){
					onMainResult(strUrl,response);
				}else{
					onResult(strUrl,response);
				}
			},error:function(x,e){
				console.log(x,e);
				
				callLoading(strUrl,"off");
				
				if(strUrl == '/mobileRecvReportCoronaList1.do' || strUrl == '/mobileRecvReportCoronaList2.do' || strUrl == '/recvReportCoronaList.do' ){
					alert('조회건수가 많아 조회가 지연되고 있습니다.\n조회기간을 줄이거나, PC 화면에서 확인해 주시기 바랍니다.');
				}
				
				if(x.status==401){
					goReload();
				}
			}
		});
	}

	//공통 단일 ajaxCall 설정
	function ajaxCall2(strUrl,formData, lodingFlag){
		if(!isNull(lodingFlag)){
			lodingFlag = lodingFlag?"":lodingFlag;
		}
		if(isNull(lodingFlag)){
			callLoading(strUrl,"on");
		}
		setTimeout(function() {
			realAjaxCall2(strUrl,formData, lodingFlag);
		}, 200);
	}
	
	//공통 단일 ajaxCall2 async 적용
	function realAjaxCall2(strUrl,formData, lodingFlag){
		
		$.ajax({
			 url : strUrl
			, dataType: 'json'
			, type : "post"
			, data : formData
			, beforeSend : function(xmlHttpRequest){
				xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
			}
			, async: false
			, processData : false
			, contentType : false
			, success : function(response){
				
			 	var msgCd = response.O_MSGCOD;
				if(msgCd != "200"){
					if(!isNull(response.strMessage)){
//						alert(response.strMessage);
						 messagePopup("","",response.strMessage);
					}else{
//						 messagePopup("","","Controller의 return이 List<Map<String, Object>> 입니다.\n param으로 return해주세요. \n 메세지코드가 없습니다.");
//						 callLoading(strUrl,"off");
//						 return;
					}
				}
				if(isNull(lodingFlag)){
					callLoading(strUrl,"off");
				}
//				callLoading(strUrl,"off");
				onResult(strUrl,response);
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
	}
	//공통 단일 ajaxCall 설정
	function ajaxCall3(strUrl,formData, lodingFlag){
		if(!isNull(lodingFlag)){
			lodingFlag = lodingFlag?"":lodingFlag;
		}
		if(isNull(lodingFlag)){
			callLoading(strUrl,"on");
		}
		setTimeout(function() {
			realAjaxCall3(strUrl,formData, lodingFlag);
		}, 200);
	}
	
	function realAjaxCall3(strUrl,formData, lodingFlag){
		
		$.ajax({
			 url : strUrl
			, dataType: 'json'
			, type : "post"
			, data : formData
//			, traditional: true
//			, processData : true
//			, contentType: "application/json; charset=utf-8"
//			, contentType : false
//			, beforeSend : function(xmlHttpRequest){
//			   xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
//		   }
			, async: false
			, success : function(response){
			 	var msgCd = response.O_MSGCOD;
				if(msgCd != "200"){
					if(!isNull(response.strMessage)){
						 messagePopup("","",response.strMessage);
					}else{
//						 messagePopup("","","Controller의 return이 List<Map<String, Object>> 입니다.\n param으로 return해주세요. \n 메세지코드가 없습니다.");
//					 	 callLoading(strUrl,"off");
//						 return;
					}
				}
				if(isNull(lodingFlag)){
					callLoading(strUrl,"off");
				}
//				callLoading(strUrl,"off");
				onResult(strUrl,response);
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
	}
	var listMsg;
	//공통 단일 ajaxCall 설정
	function ajaxListCall(createUrl,dataProvider,dataRows,callback,lodingFlag){
		var rtnCall=true;
		if(!isNull(lodingFlag)){
			lodingFlag = lodingFlag?"":lodingFlag;
		}
		if(isNull(lodingFlag)){
			callLoading(createUrl,"on");
		}
		$.when(function () {
			var def = jQuery.Deferred();
			window.setTimeout(function () {
				rtnCall = realAjaxListCall(createUrl,dataProvider,dataRows,lodingFlag);
				def.resolve();
			}, 200);
			return def.promise();
		}())
		.done(function () {
			if(isNull(lodingFlag)){
				callLoading(createUrl,"off");
			}
//			callLoading(createUrl,"off");
			window.setTimeout(function () {
				callback(rtnCall,listMsg);
			}, 100);
		});
	}
	
//	//공통 단일 ajaxCall 설정
	function realAjaxListCall(createUrl,dataProvider,dataRows,lodingFlag){
		var rtnCall = true;
		if(dataRows != ""){
			for (var i in dataRows){
				listMsg = "";
			
				var MenuValue = {
					I_LOGMNU: I_LOGMNU
					,I_LOGMNM:I_LOGMNM
				};
				dataProvider.updateStrictRow(dataRows[i], MenuValue);
					
				$.ajax({
					url : createUrl
					, type : "post"
					, data : dataProvider.getJsonRow(dataRows[i])
					, dataType: 'json'
					, beforeSend : function(xmlHttpRequest){
						xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
					}
					, async: false
					, success : function(response){
					 	var msgCd = response.O_MSGCOD;
						if(msgCd != "200"){
							if( msgCd == "221" || msgCd == "222" || msgCd == "223"){
								if(!isNull(response.strMessage)){
									listMsg = response.strMessage;
									// messagePopup("","",response.strMessage);
//									rtnCall=true;
								}
							}else{
								if(!isNull(response.strMessage)){
									listMsg = response.strMessage;
//									rtnCall=true;
//									 messagePopup("","",response.strMessage);
								}else{
//									 messagePopup("","","Controller의 return이 List<Map<String, Object>> 입니다.\n param으로 return해주세요. \n 메세지코드가 없습니다.");
//									 return;
								}
								rtnCall=false;
							}
						}else{
							
						}
					}
					,error:function(x,e){
						console.log(x,e);
						rtnCall=false;
						callLoading(createUrl,"off");
						if(x.status==401){
							goReload();
						}
					}
				});
				if(!rtnCall)break;
			}	
		}
		return rtnCall;
	}

	function messagePopup(type,yesFn,strMessage){
		try{
			window.parent.parentMag(type,yesFn,strMessage);
		}catch(e1){
			parentMag(type,yesFn,strMessage);
		}
	}
	
	
	//공통  CallMessage 설정
	function CallMessage(msgCd,type,arguments,yesFn){
		var rtnBool = true;
		$.ajax({
			url : "/CallMessage.do"
			, type : "post"
			, data : {MSGCOD:msgCd,arguments:arguments}
			, dataType: 'json'
			, beforeSend : function(xmlHttpRequest){
				xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
			 }
			, async: false
			, success : function(response){
				try{
					window.parent.parentMag(type,yesFn,response.strMessage);
				}catch(e1){
					parentMag(type,yesFn,response.strMessage);
				}
			},error:function(x,e){
				console.log(x,e);
				if(x.status==401){
					goReload();
				}
			}
		});
		return rtnBool;
	}
	
	//공통  CallMessage 설정
	function CallMessageEng(msgCd,type,arguments,yesFn){
		var rtnBool = true;
		$.ajax({
			url : "/CallMessageEng.do"
			, type : "post"
			, data : {MSGCOD:msgCd,arguments:arguments}
			, dataType: 'json'
			, beforeSend : function(xmlHttpRequest){
				xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
			 }
			, async: false
			, success : function(response){
				try{
					window.parent.parentMag(type,yesFn,response.strMessage);
				}catch(e1){
					parentMag(type,yesFn,response.strMessage);
				}
			},error:function(x,e){
				console.log(x,e);
				if(x.status==401){
					goReload();
				}
			}
		});
		return rtnBool;
	}

	function getCheckBox(I_LOGMNU, I_LOGMNM, I_PSCD){
		var chkDiv = "";
		$.ajax({
			 url : "/getCommCode.do"
			, type : "post"
			, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : I_PSCD}
			, dataType: 'json'
			, async:false
			, success : function(response){
				var resultList =  response.resultList;
				$.each(resultList, function(i, e) {
					chkDiv += '<li><input type="checkbox" value="'+e.$R_SCD+'" id="I_RGN'+(i+1)+'" name="I_RGN"> <label for="I_RGN'+(i+1)+'">'+e.$R_CNM+'</label></li>';
					//chkDiv += '<span class="div-float div30"><input type="checkbox" id="I_RGN'+(i+1)+'" name="I_RGN'+(i+1)+'" value="Y" /><label for="I_RGN'+(i+1)+'">'+e.$R_CNM+'</label></span>';
				});
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
		return chkDiv;
	}
	
	function getTerm(I_LOGMNU, I_LOGMNM, I_PSCD){
		var buttonList = "";
		$.ajax({
			 url : "/getCommCode.do"
			, type : "post"
			, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_PSCD" : I_PSCD}
			, dataType: 'json'
			, async:false
			, success : function(response){
				var resultList =  response.resultList;
				$.each(resultList, function(i, e) {
					buttonList += '<button type="button" class="btn btn-white" onClick="javascript:changeDT(\''+e.$R_SCD+'\')">'+e.$R_CNM+'</button>'
				});
			},error:function(x,e){
				callLoading(strUrl,"off");
				if(x.status==401){
					goReload();
				}
			}
		});
		return buttonList;
	}
	
	
	function getHosFNM(I_LOGMNU, I_LOGMNM, I_HOS){
		var F120FNM = "";
		$.ajax({
			 url : "/hospSearchA.do"
			, type : "post"
			, data : {"I_LOGMNU" : I_LOGMNU, "I_LOGMNM" : I_LOGMNM, "I_HOS" : I_HOS}
			, dataType: 'json'
			, async:false
			, success : function(response){
				var resultList =  response.resultList;
				//console.log(resultList[0].F120FNM);
				//$('#I_FNM').val(resultList[0].F120FNM);
				if(!isNull(resultList)){
					//console.log(resultList[0].F120FNM);
					F120FNM = resultList[0].F120FNM;
				}
			}
		});
		
		return F120FNM;
	}
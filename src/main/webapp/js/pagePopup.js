//************ 팝업 호출 및 종료 설정 ****************/
	var callPagePop ="";
	/*팝업 설정*/
	function fnOpenPopup(url,labelNm,gridValus,callback){
		$('#pageModalView', parent.document).modal('show');
		var openSrc = url;
		if(url.indexOf("?")>-1){
			openSrc += "&menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM);
		}else{
			openSrc += "?menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM);
		}

//		$('#pageiframe', parent.document).width(575);
//	    $('#popCont', parent.document).width(600);
	    
//		if($('#pageiframe', parent.document).width() < 520){
//			var dynWidth = 840;
////				$('#pageiframe').height(parseInt(dynheight) + 10);
//			$('#pageiframe').width(parseInt(dynWidth) + 10);
////				$('#popCont').height(parseInt(dynheight) + 90);
//			$('#popCont').width(parseInt(dynWidth) + 35);
//		}
		
		$('#pageiframe', parent.document).attr("src" , openSrc);
		
		$('#pageModalLabel', parent.document).html(labelNm);
		$('#pageModalView', parent.document).off().on('shown.bs.modal', function (e) {
			try{
				$('#pageiframe', parent.document).width(605);
			    $('#popCont', parent.document).width(600);
				$('#pageiframe', parent.document)[0].contentWindow.callResize()  ;
			}catch (e) {  }
		});
		
		$('#pageiframe', parent.document).on("load", function(e){
			$('body', parent.document).addClass("modal-open");
//				window.parent.closeModal(fnCall,param,closeType,pageId)
			if(!isNull($(this).attr("src" ))){
				if(typeof callback=="function"){
					var iframe = $(e.target)[0].contentWindow;
					callback(url,iframe,gridValus);
				}
			}
		});
	}
	
	/*팝업 설정*/
	function fnOpenPopup2(url,labelNm,gridValus,callback){

		var openSrc = url;
		$('#pageModalView2', parent.document).modal('hide');
		$('#pageiframe2', parent.document).off("load");
		$('#pageiframe2', parent.document).attr("src" , "");
		
		var $this   = $('#pageModalView2', parent.document);
		  var data	= $this.data('bs.modal');
		  if(!isNull(data)){
			  data.isShown = false;
		  }

		$('#pageiframe2', parent.document).unbind("load");
		$('#pageModalView2', parent.document).modal('show');
		
		if(url.indexOf("?")>-1){
			openSrc += "&menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM);
		}else{
			openSrc += "?menu_cd="+I_LOGMNU+"&menu_nm="+encodeURIComponent(I_LOGMNM);
		}
		$('#pageiframe2', parent.document).attr("src" , openSrc);
		$('#pageModalLabel2', parent.document).html(labelNm);
		$('#pageModalView2', parent.document).off().on('shown.bs.modal', function (e) {
			try{
				$('#pageiframe2', parent.document).width(605);
			    $('#popCont2', parent.document).width(600);
				$('#pageiframe2', parent.document)[0].contentWindow.callResize2()  ;
			}catch (e) { }
			
		});
		$('#pageiframe2', parent.document).load(function(e){
//				$('body', parent.document).addClass("modal-open");
//				window.parent.closeModal(fnCall,param,closeType,pageId)
			
			if(!isNull($(this).off().attr("src" ))){
				if(typeof callback=="function"){
					var iframe = $(e.target)[0].contentWindow;
					callback(url,iframe,gridValus);
				}
			}
		});
	}
	
	function fnclose2(){
		closeModal2();
	}

	function fnclose(){
		if(isNull(callPagePop)){
			closeModal();
		}else{
			closeModal("","","main");
		}
	}

	window.closeModal = function(fnCall,param,closeType,pageId){

		var frameInfo = $("#pageiframe2", parent.document);
		var frameUrl = frameInfo.attr("src");
		var iframeCheck = false;
		
		if(!isNull(typeof frameUrl)){
			if(frameUrl.indexOf("do?")>-1){
				iframeCheck = true;
			}
		}
		if(!iframeCheck){
			closePageModal(fnCall,param,closeType,pageId);
		}else{
			closeModal2(fnCall,param,closeType,pageId);
		}	
		
	}
	window.closePageModal = function(fnCall,param,closeType,pageId){

		callPagePop = "";
		try{
			if(isNull(closeType )){
				if(isNull(pageId ))
				{
					if(isNull(typeof curr_menu_cd )){
						window.parent.closeModal(fnCall,param,closeType);
					}else{
						if(isNull(curr_menu_cd )){
							$('#main_conts')[0].contentWindow.closePageModal(fnCall,param,closeType,"main_conts");
						}else{
							$('#'+curr_menu_cd)[0].contentWindow.closePageModal(fnCall,param,closeType,curr_menu_cd);
						}
					}
				}else{
						$('#pageModalView', parent.document).modal('hide');
						
						$("#pageModalView", parent.document).on("hidden.bs.modal", function () {

							var dynWidth = 840;
							$('#pageiframe', parent.document).height(400);
//								$('#pageiframe').height(parseInt(dynheight) + 10);
							$('#pageiframe').width(parseInt(dynWidth) + 10);
//								$('#popCont').height(parseInt(dynheight) + 90);
							$('#popCont').width(parseInt(dynWidth) + 35);
							
							$('#pageiframe', parent.document).off("load");
							$('#pageiframe', parent.document).unbind("load");
							$('body', parent.document).removeClass("modal-open");
							$('#pageiframe', parent.document).attr("src" , "");
							
							var func,contDocument;
							if(!isNull(fnCall)){
								try{
									contDocument = window;
								}catch (e) {
									// TODO: handle exception
								}
								if(isNull(contDocument)){
									try{
										contDocument= $('#'+pageId).contentWindow;
									}catch (e) {
										// TODO: handle exception
									}
								}
								if(isNull(contDocument)){
									try{
										contDocument = $('#'+pageId).contentDocument;
									}catch (e) {
										// TODO: handle exception
									}
								}
								
								func = contDocument[fnCall];
								func(param);
								
							}
							// put your default event here
						});
				}
			}else{
				if(isNull(typeof curr_menu_cd )){
					window.parent.closeModal(fnCall,param,closeType);
				}else{
					$('#pageModalView').modal('hide');
					$('#pageiframe').off("load");
					$('#pageiframe').unbind("load");
					$('#pageiframe').attr("src" , "");
				}
			}
		}catch (e) {
			// TODO: handle exception
		}
	};	

	window.closeModal2 = function(fnCall,param,closeType,pageId){

		try{

			if(isNull(typeof curr_menu_cd ))
			{
				parent.closeModal2(fnCall,param,closeType,pageId);
			}else{

				$('#pageModalView2').modal('hide');
				$('#pageModalView2').removeClass('in')
				  .attr('aria-hidden', true)
				  .off('click.dismiss.bs.modal')
				$('#pageModalView2').css("display","");
				$('#pageModalView2').find(".modal-backdrop").remove();
				$('#pageiframe2').off("load");
				$('#pageiframe2').unbind("load");
				$('#pageiframe2').attr("src" , "");
				var func,contDocument;
				if(!isNull(fnCall)){
					//Popup1
					contDocument= $('#pageiframe')[0].contentWindow;
					func = contDocument[fnCall];
					func(param);
				}
			}
		}catch (e) {
			// TODO: handle exception
		}
	};	
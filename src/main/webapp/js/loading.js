$(function () {
	$.blockUI.defaults = {
		message: '<p>Loading...</p>',
		title: null,
		draggable: true,
		theme: false,
		css: {position: 'absolute', textAlign: 'center', margintop: '-50px',marginLeft: '-164px', paddingLeft: '15px', paddingRight: '15px', maxWidth:'327px', height:'100px', top: '50%', left:'50%'},
		overlayCSS: {backgroundColor: '#fff',opacity:'0.8', cursor: 'wait'},
		cursorReset: 'default',
		iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank',
		forceIframe: false,
		baseZ: 30000,
		centerX: true,
		centerY: true,
		allowBodyStretch: true,
		bindEvents: true,
		constrainTabKey: true,
		fadeIn: 200,
		fadeOut: 400,
		timeout: 0,
		showOverlay: true,
		focusInput: true,
		onBlock: null,
		onUnblock: null,
		quirksmodeOffsetHack: 4,
		blockMsgClass: 'blockMsg',
		ignoreIfBlocked: false
	};
});

function loadingOn(target){
	var html ="";
	html += '<div class="loding-gif">';
	html += '<img src ="../images/common/loading.gif">';
	html += '</div>';
	html += '<p>';
	html += '로딩중입니다. 잠시만 기다려주세요';
	html += '</p>';

	if(isNull(typeof curr_menu_cd)){

		if (target == undefined) {
			$.blockUI({message : html,overlayCSS: {backgroundColor: '#fff',opacity:'0.8', cursor: 'wait'}});
		} else {
			$(target).block({message : html});	
		}
	}else{
		if(curr_menu_cd == "RSTUSER" || curr_menu_cd == "RSTUSERTABLE" || curr_menu_cd == "RSTUSERTABLEENG"){	
//		if(curr_menu_cd == "RSTUSER"){
//		if(curr_menu_cd == "RSTUSER"||curr_menu_cd == "RSTITEM"){
			
			if (target == undefined) {
				$.blockUI({message : "",overlayCSS: {backgroundColor: '#fff',opacity:'0', cursor: 'wait'}});
			} else {
				$(target).block({message : html});	
			}
			
		}else{
			
			if (target == undefined) {
				$.blockUI({message : html,overlayCSS: {backgroundColor: '#fff',opacity:'0.8', cursor: 'wait'}});
			} else {
				$(target).block({message : html});	
			}
		}
		
	}
}

function loadingPercent(percent){
	$(".blockMsg .loading-percent").html(percent + "%");
}

function loadingOff(target){
	if (target == undefined) {
		$.unblockUI();
	} else {
		$(target).unblock();	
	}
}

function progressOn(target){
	var html ="";
	html += '<div class="loding-logo_area">';
	html += '<img src ="../images/common/loading-logo.png">';
	html += '</div>';
	html += '<div class="progress">';
  	html += '<div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 0%">';
    html += '<span class="progress-percent">0%</span>';
  	html += '</div>';
	html += '</div>';
	html += '<p>';
	html += '잠시만 기다려주세요.';
	html += '</p>';
	if (target == undefined) {
		$.blockUI({message : html});
	} else {
		$(target).block({message : html});	
	}
}

function progressPercent(percent){
	$(".blockMsg .progress-bar").css("width", percent + "%");
	$(".blockMsg .progress-percent").html(percent + "%");
}

function progressOff(target){
	if (target == undefined) {
		$.unblockUI();
	} else {
		$(target).unblock();	
	}
}
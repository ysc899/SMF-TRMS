function Modal(obj){
	return $(obj);
}

$.message = {
		
	modal : null,
	title : null,
	contents : null,
	btnText : null,
	confirmButton: null,
	cancelButton: null,

	alert : function(p){
		$.message.confirmButton.removeClass('hidden');
		$.message.cancelButton.addClass('hidden');

		var param = {
			title : 'Alert'
			, contents : 'Alert Message'
		};
		
		param = $.extend(param, p);
		
		$.message.modal.modal('show');
		$.message.title.html(param.title);
		$.message.contents.html(param.contents);
		
		$.message.confirmButton.off().on('click', function(){
			if(p.onConfirm != null){
				$.message.modal.modal('hide');
				p.onConfirm();
			}
		});
        $.message.cancelButton.off().on('click', function(){
			if(p.onCancel != null){
				$.message.modal.modal('hide');
				p.onCancel();
			}
		});
		$.message.modal.off().on('hidden.bs.modal', function (e) {
			if(!isNull($('#pageiframe', parent.document).attr("src" )))
	    	{
				$('body', parent.document).addClass("modal-open");
	    	}
		});
		
		
	},
	
	confirm : function(p){
		$.message.confirmButton.removeClass('hidden');
        $.message.cancelButton.removeClass('hidden');
        
        var param = {
			title : 'Confirm'
			, contents : 'Confirm Message'
		};
		
		param = $.extend(param, p);
		
		$.message.modal.modal('show');
		$.message.title.html(param.title);
		$.message.contents.html(param.contents);
		
		$.message.confirmButton.off().on('click', function(){
			if(p.onConfirm != null){
				$.message.modal.modal('hide');
				p.onConfirm();
			}
        });
        
        $.message.cancelButton.off().on('click', function(){
			if(p.onCancel != null){
				$.message.modal.modal('hide');
				p.onCancel();
			}
		});
		$.message.modal.off().on('hidden.bs.modal', function (e) {
			
			if(!isNull($('pageiframe', parent.document).attr("src" )))
	    	{
				$('body', parent.document).addClass("modal-open");
	    	}
		});
	},
};


function closeMessage(){
	$.message.modal.modal('hide');
}

$(document).ready(function(){
	var popup = $('<div class="modal fade bs-example-modal-sm message" data-backdrop="static" />');
	var dialog = $('<div class="modal-dialog modal-sm" />');
	var content = $('<div class="modal-content" />');
	
	var header = $('<div class="modal-header" />');
	var header_btn = $('<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick=\"closeMessage()\"><span aria-hidden="true">&times;</span></button>');
	var body = $('<div class="modal-body"/>');
	var footer = $('<div class="modal-footer"/>');

	$.message.title = $('<h4 class="modal-title" />');
	$.message.contents = $('<p class="message-content_c" />');

	$.message.confirmButton = $('<button type="button" class="btn btn-default btn-confirm">확인</button>');
    $.message.cancelButton = $('<button type="button" class="btn btn-default btn-cancel" data-dismiss="modal">취소</button>');

	popup.append(dialog);
	dialog.append(content);
	content.append(header);
	header.append(header_btn);
	header.append($.message.title);
	content.append(body);
	content.append(footer);
	footer.append($.message.confirmButton);
	footer.append($.message.cancelButton);
	
	body.append($.message.contents);
	
	$('body').append(popup)
	
	$.message.modal = new Modal(popup);

	$.message.confirmButton.on('click', function(){
		$.message.modal.modal('hide');
	});

	$.message.cancelButton.on('click', function(){
		$.message.modal.modal('hide'); 
	});
});
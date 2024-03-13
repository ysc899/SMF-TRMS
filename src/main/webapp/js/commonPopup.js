   
	window.onload = callResize;  
	function callResize()  
    {  
        var height = $("body").height();  
//        var width23 = $("body").width();  
//        var height = document.body.scrollHeight;  
        var width = document.body.scrollWidth;  
        
		var frameInfo = $("#pageiframe2", parent.document);
		var frameUrl = frameInfo.attr("src");
		
		if(!isNull(typeof frameUrl)){
			if(frameUrl.indexOf("do?")>-1){
				callResize2();
				return;
			}
		}
		if(width==0){
			width = 900;
		}
		if(height==0){
			height = 300;
		}
		
//		
//        var height2 = $("body", parent.document).height();  
//        var width2 = $("body", parent.document).width();  
//        var width22 = $("#pageiframe", parent.document).width();  
//
//        console.log("height2     ",   height2
//        	   , "width2   ", width2
//               , "height    ",   height
//               , "width   ", width
//               , "width23   ", width23
//               , "width22   ", width22
//        		
//        );
//		page2Width = 0;
        parent.resizeTopIframe(height,width);  
        //resizeTopIframe2
	}  
	function callResize2()  
	{  
        var height = $("body").height();  
		var width = document.body.scrollWidth; 
//		if(browserEdge != "edge"){ 
//			if($("body").width()<width){
//				$("body").width(width);
//			}
//		}
		
//		if(page2Width==0 ||page2Width+20 < width)
//		{
//			page2Width = width;
//		}
		parent.resizeTopIframe2(height,width);  
		
		//resizeTopIframe2
	}  
	

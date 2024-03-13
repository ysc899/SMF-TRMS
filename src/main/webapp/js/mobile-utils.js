	$(document).ready( function(){ 


	   //숫자만 입력
	    $(".numberOnly").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[^0-9]/gi,''));
	        }
	    });
	    //영문자만 입력
	    $(".engOnly").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[0-9]|[^\!-z\s]/gi,""));
	        }
	    });
	    //숫자, 영문만 입력
	    $(".engNumOnly").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[^0-9a-zA-Z]/gi,""));
	        }
	    });
	    //숫자, 영문 특수문자만 입력
	    $(".engNum").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[^0-9a-zA-Z.!@#$*_\- ]/gi,""));
	        }
	    });
	    //숫자, 특수문자만 입력
	    $(".fr_date").focusout(function(event){

    		var inputVal = $(this).val();
    		var textlength = inputVal.length;
    		if (textlength == 9) {
				var yearfield = inputVal.split("-")[0];
				var monthfield = inputVal.split("-")[1];
				var dayfield = inputVal.split("-")[2];
				inputVal = yearfield+"-"+monthfield+"-0"+dayfield
	    		$(this).val(inputVal);
    		}
	    });
	    //숫자, 특수문자만 입력
	    $(".fr_date").keyup(function(event){
	    	if ((event.keyCode != 8 && event.keyCode != 46)) {
	    	    if (event.keyCode == 13) {
	    	    	var thisId = $(this).attr("id");
	    	    	if(thisId.indexOf("I_FR")>-1){
		    	    	$("#"+$(this).attr("id").replace('I_FR','I_TO')).focus();
	    	    	}else if(thisId.indexOf("I_F")>-1){
		    	    	$("#"+$(this).attr("id").replace('F','T')).focus();
	    	    	}
	    	    	if(thisId.indexOf("I_PFR")>-1){
	    	    		$("#I_PTO").focus();
	    	    	}
	    	    }
		    	if (!(event.keyCode >=37 && event.keyCode<=40)) {
	
		    		var inputVal = $(this).val();
		    			inputVal = inputVal.replace(/[^0-9(.)~_\-]/gi,"");
			    		inputVal = inputVal.replace(/\./gi,'');
			    		inputVal = inputVal.replace(/-/gi,'');
		    		var textlength = inputVal.length;
		    		
		    		if(textlength>8){
		    			inputVal = inputVal.substr(0,8);
		    			textlength = inputVal.length;
		    		}
		    		if (textlength == 4) {
		    			inputVal = inputVal + "-";
		    		}else if (textlength == 5||textlength == 6) {
		    			inputVal = inputVal.substr(0,4) + "-"+inputVal.substr(4,2) ;
		    		} else if (textlength == 7||textlength == 8) {
		    			inputVal = inputVal.substr(0,4) + "-"+inputVal.substr(4,2)  + "-"+inputVal.substr(6,2) ;
		    		}
		    		$(this).val(inputVal);
	    		}
    		}
	    });



	    //숫자, 특수문자만 입력
	    $(".Num").keyup(function(event){
	    	if (!(event.keyCode >=37 && event.keyCode<=40)) {
	    		var inputVal = $(this).val();
	    		$(this).val(inputVal.replace(/[^0-9(.)~_\-]/gi,""));
	    	}
	    });
	    // 한글만 입력
	    $(".onlyHangul").keyup(function(event){
	        if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            $(this).val(inputVal.replace(/[a-z0-9]/gi,''));
	        }
	    });
	    // 전화번호 입력
	    $(".telNm").keyup(function(event){
	    	if ((event.keyCode != 8 && event.keyCode != 46 )) {
		        if (!(event.keyCode >=37 && event.keyCode<=40)) {
		            var inputVal = $(this).val();
		            var textlength = inputVal.length;
		            if(textlength>1){
		            	if(inputVal.substr(0,2)=="02"){
		    	            if (textlength == 2) {
		    	            	inputVal = inputVal + "-";
		    	            }else if(textlength == 6){
		    	            	inputVal = inputVal + "-";
		    	            }else if(textlength >= 12){
		    	            	inputVal = inputVal.replace(/-/gi,'');
		    	            	inputVal = inputVal.substr(0,2)+"-"+inputVal.substr(2, 4)+"-"+inputVal.substr(6, 4);
		    	            }
		            	}else{
		    	            if (textlength == 3) {
		    	            	inputVal = inputVal + "-";
		    	            }else if(textlength == 7){
		    	            	inputVal = inputVal + "-";
		    	            }else if(textlength >= 13){
		    	            	inputVal = inputVal.replace(/-/gi,'');
		    	            	inputVal = inputVal.substr(0,3)+"-"+inputVal.substr(3, 4)+"-"+inputVal.substr(7, 4);
		    	            }
		            	}
		            }
	
	            	$(this).val(inputVal.replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3"));
		        }
	    	}
	    });
	})
	
	function checkdate(input) {
		var validformat = /^\d{4}\-\d{2}\-\d{2}$/; //Basic check for format validity 
		var returnval = false;
		var yearfield,monthfield,dayfield, dayobj;
		
		if (!validformat.test(input.val())) {
			return returnval;
		} else { //Detailed check for valid date ranges 
			yearfield = input.val().split("-")[0];
			monthfield = input.val().split("-")[1];
			dayfield = input.val().split("-")[2];
			dayobj = new Date(yearfield, monthfield - 1, dayfield);
		}
		try{
			
			if ((dayobj.getMonth() + 1 != monthfield)
				|| (dayobj.getDate() != dayfield)
				|| (dayobj.getFullYear() != yearfield)) {
				if(dayfield=="00"){
					input.val(yearfield+"-"+monthfield+"-0");
				}
				return returnval;
			} else {
				returnval = true;
			}
		}catch (e){
		}
		return returnval;
	}


	function changeDT(DayId){
		
		var today = new Date();

		
		if(checkdate($("#I_TDT"))){
			var to_date = $("#I_TDT").val();
			today =  new Date(to_date);
		}
		//20190404 수진자목록 전용 전체 조회(백기성 이사님)
		
		
		if(DayId == "1D"){
			today.setDate(today.getDate() - 1);
		}
		if(DayId == "3D"){
			today.setDate(today.getDate() - 3);
		}
		
		if(DayId == "1M"){
			today.setDate(today.getDate() - 30);
		}
		if(DayId == "3M"){
			today.setDate(today.getDate() - 90);
		}
		
//		$("#I_FDT").datepicker('setDate', today);
		if(DayId == "A"){
			$("#I_FDT").val("");
			$("#I_TDT").val("");
		} else {
			$("#I_FDT").val(parseDate(today.yyyymmdd()));
			if(!checkdate($("#I_TDT"))){
				$("#I_TDT").datepicker('setDate',  new Date());
			}
		}
		
	}

	
	$(document).keydown(function(e){
		var backspace = 8;
	    if (e.keyCode == backspace) {
	    	// INPUT, TEXTAREA, SELECT 가 아닌곳에서 backspace 키 누른경우  
	    	if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA" && e.target.nodeName != "SELECT"){
	    		return false;
	    	}
	    	
	    	// SELECT 에서 backspack 키 누른경우 
	        if (e.target.tagName == "SELECT") {
	        	return false;
	        }

	     	// 속성이 readonly이며 INPUT에서 backspack 키 누른경우
			if (e.target.tagName == "INPUT" && e.target.getAttribute("readonly") == "readonly") {
	            return false;
	        }
	    }
	});

	var version_IE = "0"; 
	var browser = ""; 
	var browserEdge = ""; 
	
	function checkBroswer(){

	    var agent = navigator.userAgent.toLowerCase(),
	        name = navigator.appName,
	        browser = '';
	    	browserEdge = '';
			// MS 계열 브라우저를 구분
			if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1 || agent.indexOf('edge/') > -1) {
//			    browser = 'ie';
			    if(name === 'Microsoft Internet Explorer') { // IE old version (IE 10 or Lower)
			    	version_IE = agent.toString().replace( "MSIE " , "" );  
//			        agent = /msie ([0-9]{1,}[\.0-9]{0,})/.exec(agent);
//			        browser += parseInt(agent[1]);
			    } else { // IE 11+
			        if(agent.indexOf('trident') > -1) { // IE 11
			            browser += 11;
			        } else if(agent.indexOf('edge/') > -1) { // Edge
			            browser = 'edge';
			            browserEdge = 'edge';
			        }
			    }
			    browser = 'ie';
			} else if(agent.indexOf('safari') > -1) { // Chrome or Safari
			    if(agent.indexOf('opr') > -1) { // Opera
			        browser = 'opera';
			    } else if(agent.indexOf('chrome') > -1) { // Chrome
			        browser = 'chrome';
			    } else { // Safari
			        browser = 'safari';
			    }
			} else if(agent.indexOf('firefox') > -1) { // Firefox
			    browser = 'firefox';
			}
		return browser;
	}
	

	function get_position_of_mousePointer ( event ) { 
		 event = event || window.event; 

		 var x = 0; // 마우스 포인터의 좌측 위치 
		 var y = 0; // 마우스 포인터의 위쪽 위치 

		 if ( 0 < version_IE && version_IE < 7 ) { // 인터넷 익스플로러 (ie) 6 이하 버전일 경우 적용될 내용 
		        x = event.offsetX; 
		        y = event.offsetY; 
		 } 
		 else if ( event.pageX ) { // pageX & pageY를 사용할 수 있는 브라우저일 경우 
		        x = event.pageX; 
		        y = event.pageY; 
		 } 
		 else { // 그외 브라우저용 
	        x = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft; 
	        y = event.clientY + document.body.scrollTop + document.documentElement.scrollTop; 
		 }
		 
		if(screen.height-450 < event.screenY ){
			y = y - (event.screenY-(screen.height-420));
//			y = y - (event.screenY-(screen.height-450));
		}
		
		return { positionX : x, positionY : y }; 
	} 
/** * 전화번호 형식 체크 * * @param 데이터 */ 
	function isPhone(phoneNum) { 
		//var regExp =/(02|0[3-9]{1}[0-9]{1})[1-9]{1}[0-9]{2,3}[0-9]{4}$/; 
		var regExp =/(02)-([0-9]{3,4})-([0-9]{4})$/; 
		var myArray; 
		if(regExp.test(phoneNum)){ 
			myArray = regExp.exec(phoneNum); 
			return true; 
		} else { 
			regExp =/(0[1-9]{1}[0-9]{1})-([0-9]{3,4})-([0-9]{4})$/; 
			if(regExp.test(phoneNum)){ 
				myArray = regExp.exec(phoneNum); 
				return true; 
			} else { 
				return false; 
			} 
		} 
	}

	function isNull(strVal)
	{
		strVal = strVal+"";
		if(strVal=="undefined"){
			return true;
		}
		if(strVal==undefined){
			return true;
		}
		if(strVal==""){
			return true;
		}
		if(strVal==null){
			return true;
		}
		if(strVal=="null"){
			return true;
		}
		
		return false;
	}
	
	// 숫자 타입에서 쓸 수 있도록 format() 함수 추가
	Number.prototype.format = function(){
	    if(this==0) return 0;
	 
	    var reg = /(^[+-]?\d+)(\d{3})/;
	    var n = (this + '');
	 
	    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
	 
	    return n;
	};

	// 문자열 타입에서 쓸 수 있도록 format() 함수 추가
	String.prototype.format = function(){
	    var num = parseFloat(this);
	    if( isNaN(num) ) return "0";
	 
	    return num.format();
	};
	
	/**
	 * 리포트 미리보기 팝업
	 * IE : showModalDialog 
	 * IE 외 : window.open
	 * url: 미리보기 url
	 * width: 팝업 너비
	 * height: 팝업 높이
	 * closeCallback: 팝업 닫을 때 callback 함수
	 */
	function showReportViewDialog(url, width, height, closeCallback) {
	    var modalDiv,
	        dialogPrefix = window.showModalDialog ? 'dialog' : '',
	        unit = 'px',
	        maximized = width === true || height === true,
	        w = width || 600,
	        h = height || 500,
	        border = 5,
	        taskbar = 40, // windows taskbar
	        header = 20,
	        x,
	        y;
	    
	    maximized = false;
	   
	    if (maximized) {
	        x = 0;
	        y = 0;
	        w = screen.width - 100;
	        h = screen.height - 100;
	        
	    } else {
	    	w = 1000;
	    	h = screen.height - 50;
	    	x = 0;
//	    	y = window.screenY + (screen.height / 2) - (h / 2) - taskbar - border;
	        x = window.screenX + (screen.width / 2) - (w / 2) - (border * 2);
	    	
	    }

	    var features = [
	            'toolbar=no',
	            'location=no',
	            'directories=no',
	            'status=no',
	            'menubar=no',
	            'scrollbars=no',
	            'resizable=no',
	            'copyhistory=no',
	            'center=yes',
	            'width=' + w + unit,
	            'height=' + h + unit,
	            'top=' +  y + unit,
	            'left=' + x+ unit,
	            dialogPrefix + 'width=' + w + unit,
	            dialogPrefix + 'height=' + h + unit,
	            dialogPrefix + 'top=' + y + unit,
	            dialogPrefix + 'left=' + x + unit
	        ],
	        showModal = function (context) {
	            if (context) {
	                modalDiv = context.document.createElement('div');
	                modalDiv.style.cssText = 'top:0;right:0;bottom:0;left:0;position:absolute;z-index:50000;';
	                modalDiv.onclick = function () {
	                    if (context.focus) {
	                        context.focus();
	                    }
	                    return false;
	                }
	                window.top.document.body.appendChild(modalDiv);
	            }
	        },
	        removeModal = function () {
	            if (modalDiv) {
	                modalDiv.onclick = null;
	                modalDiv.parentNode.removeChild(modalDiv);
	                modalDiv = null;
	            }
	        };

			var f = document.reportForm;
	        var win = window.open('', 'allDown', features.join(',')+"fullscreen");
			f.action = url;
			f.target = "allDown";
			f.submit();
			win.focus();
	        
//	        if (maximized) {
//	            win.moveTo(0, 0);
//	        }

	        // When charging the window.
	        var onLoadFn = function () {
	                showModal(this);
	            },
	            // When you close the window.
	            unLoadFn = function () {
	                window.clearInterval(interval);
	                if (closeCallback) {
	                    closeCallback();
	                }
	                removeModal();
	            },
	            // When you refresh the context that caught the window.
	            beforeUnloadAndCloseFn = function () {
	                try {
	                    unLoadFn();
	                }
	                finally {
	                    win.close();
	                }
	            };

	        if (win) {
	            // Create a task to check if the window was closed.
	            var interval = window.setInterval(function () {
	                try {
	                    if (win == null || win.closed) {
	                        unLoadFn();
	                    }
	                } catch (e) { }
	            }, 500);

	            if (win.addEventListener) {
	                win.addEventListener('load', onLoadFn, false);
	            } else {
	                win.attachEvent('load', onLoadFn);
	            }

	            window.addEventListener('beforeunload', beforeUnloadAndCloseFn, false);
	        }
//	    }
	}
	/*그리드 row 삭제 */
	function InsertGridRowDel(dataRows,grid,dataProvid){
		var insertRowChk = false;
		dataRows.reverse();
		grid.commit();	
		$.each(dataRows, function(i, e) {
			if(dataProvid.getRowState(e)=="created"){
				dataProvid.removeRow(e);
				insertRowChk = true;
			}
		});
		grid.commit();	
		
		return insertRowChk;
	}
	
	/*숫자형 날짜 데이터 문자열 변환 후 하이픈 추가*/
	function parse(str) {
		str = str.toString();
	    var y = str.substr(0, 4);
	    var m = str.substr(4, 2);
	    var d = str.substr(6, 2);
	    str = y + "-" + m + "-" + d;
	    return str;
	}
	function parseDate(str) {
		str = str.toString();
		var y = str.substr(0, 4);
		var m = str.substr(4, 2);
		var d = str.substr(6, 2);
		str = y + "-" + m + "-" + d;
		return str;
	}
	function parseTime(str) {
		str = str.toString();
		if(str.length==5){
			str = "0"+str;
		}
		
		var h = str.substr(0, 2);
		var m = str.substr(2, 2);
		var s = str.substr(4, 2);
		str = h + ":" + m + ":" + s;
		return str;
	}
	function lpad(s, padLength, padString){
		var rtnStr = s+"";
	    while(rtnStr.length < padLength)
    	{
	    	rtnStr = padString+""+ rtnStr;
    	}
	    return rtnStr;
	}
	 
	function rpad(s, padLength, padString){
		var rtnStr = s+"";
	    while(rtnStr.length < padLength)
    	{
	    	rtnStr = rtnStr+""+padString;
    	}
	    return rtnStr;
	}

	function setCookie(cookie_name, cookie_value, days) {
		  // 설정 일수만큼 현재시간에 만료값으로 지정
        var date = new Date();
        date.setTime(date.getTime() + days * 60 * 60 * 24 * 1000);
        document.cookie = cookie_name + '=' + cookie_value + ';expires=' + date.toUTCString() + ';path=/';
	}
	
	function getCookie(cookie_name) {
		  var x, y;
		  var val = document.cookie.split(';');
		  for (var i = 0; i < val.length; i++) {
		    x = val[i].substr(0, val[i].indexOf('='));
		    y = val[i].substr(val[i].indexOf('=') + 1);
		    x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
		    if (x == cookie_name) {
		      return unescape(y); // unescape로 디코딩 후 값 리턴
	    }
	  }
	}
	Date.prototype.yyyymmdd = function() {
	   var yyyy = this.getFullYear();
	   var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth() is zero-based
	   var dd  = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
	   return "".concat(yyyy).concat(mm).concat(dd);
	 };
	 
	//한글 조합 길이 확인
	var strByteLength = function(s,b,i,c){
	  	for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
	  	return b
	}
	
	// 공통코드 가져오기 (국문)
	function getCode(I_LOGMNU, I_LOGMNM, I_PSCD, ALLYN){
	
	var optionList = "";
		$.ajax({
			 url : "/mobileGetCommCode.do"
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
	
    function isIOS() {
        return (
          [
            "iPad Simulator",
            "iPhone Simulator",
            "iPod Simulator",
            "iPad",
            "iPhone",
            "iPod",
          ].includes(navigator.platform) ||
          (navigator.userAgent.includes("Mac") && "ontouchend" in document)
        );
      }

// JavaScript Document
/* KJ S&M Digital Insight Group Hwnag In Dong */

$(window).on("click",function(e){
	try{
		var bookMark = $('#bookmark-list',parent.document);
		var bookMarkActive="";
		if(bookMark.length >0){
			var bookMarkActive =$('#bookmark-list',parent.document).hasClass("kv-popover-active");
			if(bookMarkActive){
				window.parent.bookMarkHidden(e);
			}
		}
	}catch (e2) {
		bookMarkHidden(e);
	}
});

function bookMarkHidden(e){
	var bookMarkActive =$('#bookmark-list').hasClass("kv-popover-active");
	if(bookMarkActive){
		if($(e.target).parents(".popover").length == 0){
			if(!$(e.target).hasClass("book_mark")) { 
				$('#bookmark-list').popoverX('hide');
			}
		}
	}
}
// window 가로사이즈 최대 1024px
//var mql = window.matchMedia("screen and (max-width: 1024px)");

$(function () {
	// LEFT 메뉴 열고,닫기
	(function () {
		var SCROLLTOP = 0;
		$sideWrap = $('.side_wrap');
		// left 메뉴
		$(document).off('click.menuClose').on('click.menuClose', '.button_close', function (e) {
			e.preventDefault();
			$(document).trigger('menu.close');
		});
		$(document).off('click.menuOpen ').on('click.menuOpen', '.button_menu', function (e) {
			e.preventDefault();
			$(document).trigger('menu.open');
		});
		
		// top 메뉴
		$(document).off('click.btn_menu').on('click.menuClose', '.btn_menu', function (e) {
			e.preventDefault();
			if($(this).hasClass('active')){
				$(".side_wrap").addClass("open");
			} else {
				$(".side_wrap").removeClass("open");
			}
		});
		
		$(document).off('menu.open menu.close').on('menu.close', function () {
			$sideWrap.addClass('on');
			$('.left-menu-item').find('li').removeClass('on');
			$('.dep_lst').hide();
		});
		$(document).off('menu.open').on('menu.open', function () {
			$sideWrap.removeClass('on');
		});
		
/*		var winSize = 1024;
		
		if($sideWrap.hasClass('on')){
			winSize += 70;
		}else{
			winSize += 200;
		}*/
		var mql = window.matchMedia("screen and (max-width: 1224px)");
		var curMcode = window.parent.curr_menu_cd;
		
		// 화면 가로 사이즈가 1200이하일경우 최소화
		if(mql.matches){
			$sideWrap.addClass('on');
			$sideWrap.addClass('onKeep');
			$('.left-menu-item').find('li').removeClass('on');
			$('.dep_lst').hide();
		}
		// 화면 가로 사이즈 조절시
		
		mql.addListener(function(e){
			if(e.matches){		// 화면 가로 사이즈가 1224이하일경우 최소화
				$sideWrap.addClass('on');
				$sideWrap.addClass('onKeep');
				$('.left-menu-item').find('li').removeClass('on');
				$('.dep_lst').hide();
			}else{				// 화면 가로 사이즈가 1224이상일 경우 복구
				$sideWrap.removeClass('on');
				$sideWrap.removeClass('onKeep');
			}
		});
		
	}());
	
	/* LEFT 메뉴 아코디언*/	
	$(function(){
		// Local Navigation Bar
		$(".left-menu-item>li>a").on("click keyup",function(){
			var current=$(this).parent(),
				currentList=current.find('.dep_lst'),
				old=$(this).parent().siblings('.on'),
				oldList=old.find('.dep_lst');
	
			if(currentList.html()!=undefined && !$(".side_wrap").hasClass("on")){
				currentList.css('display',currentList.css('display'));
				current.toggleClass('on').find('.dep_lst').slideToggle(250);
				old.removeClass('on').find('.dep_lst').show();
				oldList.slideUp(250,function(){old.find('.dep_lst').hide().find('li').removeClass('on')});
	
				return false; //
			};
		});
	});
	
	
	// 모바일 메뉴버튼
	var burger = $('.btn_menu');
		burger.each(function(index){
			var $this = $(this);
			$this.on('click', function(e){
				e.preventDefault();
				$(this).toggleClass('active');
			})
		});
	
	sideMenuScrollResize();
//
//	$(window).resize(function(){
//		sideMenuScrollResize();
//		TabResize();
//	});
	
//	sideMenuScrollResize();
//	TabResize();
	// side menu end

	// side menu end
	
	// 즐겨찾기 리스트
	$('.bookmark_popover-content').slimscroll({
	  height: '150px',
	  width: '180px'
	});
	// 즐겨찾기 리스트 end
	
	$('[data-toggle="tooltip"]').tooltip()
	
	//tab_area
	$('.tab_lst>li>a').on('click focus', function(){
		$(this).parent().addClass('on');
		$(this).parent().siblings().removeClass('on');
	});
	
	var open_click = true;

	// 검색영역
	$('.btn_open').on('click', function(){
		
		if(open_click)
		{
			
			var srch_wrap = $(this).closest('.srch_wrap');
			var title_area = $(this).closest('.title_area');
			
			//btn_open
			open_click = false;
//			if( $(this).html() == '검색영역 열기' ) 
			if ($(this).hasClass('on')){
				$(this).removeClass('on');
				$(this).html('검색영역 닫기');
//				$(this).html('검색영역 접기');
			}
			else {
				$(this).addClass('on');
				if(srch_wrap.find('.srch_txt').length > 0){
					$(this).html('검색영역 열기');
				}else{
					$(this).html('검색영역 더보기');
				}
			}
		
			$(this).removeAttr('href');

			if (title_area.hasClass('open')) {
				title_area.removeClass('open');
				$('.srch_line').hide();
				if(srch_wrap.find('.srch_txt').length > 0){
					srch_wrap.find('.srch_box').fadeOut(function(){
						srch_wrap.find('.srch_txt').fadeIn(function(){
							 open_click = true;
						});
						TabResize();
					});
				}else{
					srch_wrap.find('.srch_box').fadeOut(function(){
						open_click = true;
						TabResize();
					});
				}
			}
			else {
				title_area.addClass('open');
				 $('.srch_line').show();
				if(srch_wrap.find('.srch_txt').length > 0){
					srch_wrap.find('.srch_txt').fadeOut(function(){
						srch_wrap.find('.srch_box').fadeIn(function(){
							 open_click = true;
						});
						TabResize();
					});
				}else{
					srch_wrap.find('.srch_box').fadeIn(function(){
						 open_click = true;
					});
					TabResize();
				}
			}
		}
	});

	// 검색영역 (영문)
	$('.btn_open_eng').on('click', function(){
		
		if(open_click)
		{
			
			var srch_wrap = $(this).closest('.srch_wrap');
			var title_area = $(this).closest('.title_area');
			
			//btn_open_eng
			open_click = false;
//			if( $(this).html() == '검색영역 열기' ) 
			if ($(this).hasClass('on')){
				$(this).removeClass('on');
				$(this).html('Search area close');
//				$(this).html('검색영역 접기');
			}
			else {
				$(this).addClass('on');
				if(srch_wrap.find('.srch_txt').length > 0){
					$(this).html('Search area open');
				}else{
					$(this).html('Search area more');
				}
			}
		
			$(this).removeAttr('href');

			if (title_area.hasClass('open')) {
				title_area.removeClass('open');
				$('.srch_line').hide();
				if(srch_wrap.find('.srch_txt').length > 0){
					srch_wrap.find('.srch_box').fadeOut(function(){
						srch_wrap.find('.srch_txt').fadeIn(function(){
							 open_click = true;
						});
						TabResize();
					});
				}else{
					srch_wrap.find('.srch_box').fadeOut(function(){
						open_click = true;
						TabResize();
					});
				}
			}
			else {
				title_area.addClass('open');
				 $('.srch_line').show();
				if(srch_wrap.find('.srch_txt').length > 0){
					srch_wrap.find('.srch_txt').fadeOut(function(){
						srch_wrap.find('.srch_box').fadeIn(function(){
							 open_click = true;
						});
						TabResize();
					});
				}else{
					srch_wrap.find('.srch_box').fadeIn(function(){
						 open_click = true;
					});
					TabResize();
				}
			}
		}
	});

    $('.nav-tabs').scrollingTabs({
      enableSwiping: true,
      scrollToTabEdge: true,
      disableScrollArrowsOnFullyScrolled: true,
      leftArrowContent: [
        '  <div class="scrtabs-tab-scroll-arrow scrtabs-js-tab-scroll-arrow-left"><span class="glyphicon glyphicon-chevron-left"></span></div>',
      ].join(''),
      rightArrowContent: [
        '  <div class="scrtabs-tab-scroll-arrow scrtabs-js-tab-scroll-arrow-right"><span class="glyphicon glyphicon-chevron-right"></span></div>',
      ].join('')
    })
    .on('ready.scrtabs', function() {
      $('.tab-content').show();
    });
    
    
	//레이어 팝업  
    $(document).ready(function(){
		// 레이어 팝업을 띄웁니다.
        $('.ly_pop').show();
 
        // 닫기(close)를 눌렀을 때 작동합니다.
//        $('.ly_pop .pop_clse').click(function (e) {
//            e.preventDefault();
//            $('.ly_pop').hide();
//        });
    });
	//레이어 팝업 end
});

	// side menu
	function sideMenuScrollResize(){
		var menu_height = $(window).height();
		menu_height = menu_height - 254;
		$('.slim_scroll_area').slimscroll({
			height: menu_height,
			width: '200px',
			animate : true,
		});

		if(screen.width<780){	// 모바일 가로 길이 780
			if($(".side_wrap").hasClass("on")){		
				// m-top >> 모바일만 사용(device-width)
				$(".button_menu").trigger("click");
			}
		}
	}
	

//셀박,인풋,라디오, 체크박스
	$(function(){
		jcf.replaceAll();
	});

	var cont = 0;
	var tmRsize = 100;
	var lastWindowWidth = $(window).width();
	var lastWindowHeight = $(window).height();

	$(window).resize(function(){
		
//		sideMenuScrollResize();
		TabResize();
//	    if($(window).width() != lastWindowWidth || $(window).height() != lastWindowHeight)
//	    {
//	        clearTimeout(this.id);
//	        this.tiempo = tmRsize;
////	        this.id = setTimeout(TabResize, this.tiempo);
//	    }
	});
	/*****redimensionamiento**********/
	function TabResize()
	{
		lastWindowWidth = $(window).width();
	    lastWindowHeight = $(window).height();
		sideMenuScrollResize();
		
		var gridchk = 0;
		var frameInfo = $("#pageiframe", parent.document);
		var frameUrl = frameInfo.attr("src");

		if(!isNull(typeof frameUrl)){
			if(frameUrl.indexOf("do?")>-1){
				return;
			}
		}
		
		frameInfo = $("#pageiframe2", parent.document);
		frameUrl = frameInfo.attr("src");
		if(!isNull(typeof frameUrl)){
			if(frameUrl.indexOf("do?")>-1){
				return;
			}
		}
		
		if(isNull(typeof curr_menu_cd)){
			if(!isNull(typeof gridView)){
				if(typeof gridView == "object"){
					gridchk++;
				}
			}
			if(!isNull(typeof gridView1)){
				gridchk++;
			}
			if(!isNull(typeof gridView2)){
				gridchk++;
			}
			if(!isNull(typeof gridView3)){
				gridchk++;
			}
			if(!isNull(typeof treeView1)){
				gridchk++;
			}
			if(!isNull(typeof treeView2)){
				gridchk++;
			}
			if(!isNull(typeof rsTableList)){
				gridchk++;
			}
			
			if(gridchk>0)
			{
				
				var curr = window.parent.curr_menu_cd;
				var gridDivision = 10;
				var gridH = 0;
				var bottomH = 70;	// Copyright 높이 + 하단 높이
				var notGridH = 0;	//chart , 표 등이 나올때 높이
				var realgridh =$(".realgridH").height();

				var pagination_area = isNull($(".pagination_area").outerHeight())? 0 :$(".pagination_area").outerHeight();// 페이징 높이
				var finalH = 0;//추가 높이
				var finalAreaH = 0;//최종 area 높이
				var pagination_area = 0;
				var gridNonH = 0;
				var srch_h = $(".srch_wrap").outerHeight();

				//RSTUSER 수진자별 결과 관리 일때 그리드 크기 변경
				$.each($(".overflow-scr"), function(i, e) {
					// 페이징이 있을경우 높이 추가
					if(!isNull($(e).find(".pagination_area").height())){
						pagination_area = 54;
					}
				});
				//RSTUSER 수진자별 결과 관리제외 크기 변경
				if(curr != "RSTUSER" && curr != "RSTUSERTABLE" && curr != "RSTUSERTABLEENG"){
					
					if(gridH == 0){
//						bottom : 70 , top :50, pagination_area = 54,title : 80, padding: 30 
						var tmp = 150;
						if(browser == "ie"){
							tmp = 170;
						}
						if(isNull(curr )){
							tmp = 0;
						}
						if($(".container-fluid").eq(0).find(".srch_box").length>0){
							gridH = parent.innerHeight - $(".container-fluid").eq(0).height() - pagination_area - bottomH - tmp;
						}else{
							gridH = parent.innerHeight - pagination_area - bottomH - tmp;
						}
					}

					if(gridH<400){
						gridH = 400;
					}
					
					if(parent.innerWidth<1024){
						gridH = 400;	// 그리드 최대 높이
					}
					if(parent.innerHeight<768){
						gridH = 400;	// 그리드 최대 높이
					}
					if(isNull(curr )){
						if(parent.innerWidth<1024 || window.innerWidth<=991){
							$("#realgrid1").height(400);
							$("#realgrid2").height(350);
						}else{
							gridNonH = (parent.innerHeight - $('.content_inner').height());
							var rstTitleH = $("#rstTitle").height();
							if(gridNonH<50){
								$("#realgrid2").height($("#realgrid2").height() - Math.abs(gridNonH) -50);
								if($("#realgrid2").height()<350){
									$("#realgrid2").height(350);
								}
								gridH  = $("#realgrid2").height() +  $(".notice_wrap").height();
								
								//bottomH
							}else{
								if( $(".notice_wrap").height()>200){
									if(gridNonH>100){
										$("#realgrid2").height($("#realgrid2").height()+30);
									}
									if($("#realgrid2").height()<300){
										$("#realgrid2").height(350);
									}
								}
								gridH  = $("#realgrid2").height() +  $(".notice_wrap").height();
								if(gridH<400){
									gridH = 500;
								}
							}
							if(rstTitleH>80){
								gridH = gridH-40;
							}
							
							$("#realgrid1").height(gridH);
						}
						finalAreaH =  ($('.content_inner').height() + bottomH - 40);
						if(browserEdge != "edge"){ 
							$('#main_area', parent.document).height(document.body.scrollHeight+50);
						}else{
							$('#main_area', parent.document).height(document.body.scrollHeight+20);
						}
					}else{
						//chartjs-render-monitor
						if(parent.innerWidth<1024 || window.innerWidth<=991){
							$(".realgridH").height(400);
						}else{
							if(!isNull($(".chartjs-render-monitor").height())){
								if($(".chartjs-render-monitor").css("display") != "none"){
									if($(".chartjs-render-monitor").height()>400){
										chart.resize();
										if(gridH<$(".chartjs-render-monitor").height()){
											gridH = $(".chartjs-render-monitor").height();
										}
									}
								}
							}
							
							if(gridH>0){
								gridH = Math.floor(gridH/gridDivision)*gridDivision;
								$(".realgridH").height(gridH -30);
							}
						}
					}
					
					//*/
					if(!isNull(typeof gridView)){
						gridView.resetSize();
					}
					if(!isNull(typeof gridView1)){
						gridView1.resetSize();
					}
					if(!isNull(typeof gridView2)){
						gridView2.resetSize();
					}
					if(!isNull(typeof treeView1)){
						treeView1.resetSize();
					}
					if(!isNull(typeof treeView2)){
						treeView2.resetSize();
					}
					if(finalAreaH < 600){
						finalAreaH = 600;
					}
						if(browserEdge != "edge"){ 
							$('#menu_area', parent.document).height(document.body.scrollHeight + 70);
//							$('#main_area', parent.document).height(document.body.scrollHeight+50);
						}else{
							$('#menu_area', parent.document).height(document.body.scrollHeight+20);
//							$('#main_area', parent.document).height(document.body.scrollHeight);
						}
//					$('#menu_area', parent.document).height(Math.ceil(finalAreaH/gridDivision)*gridDivision);
						//chartjs-render-monitor
						
				}else if(curr == "RSTUSERTABLE" || curr == "RSTUSERTABLEENG"){
					//table로 변경
					//RSTUSERTABLE 수진자별 결과 관리 일때 그리드 크기 변경
					var realgrid2Height = 0;
					var gridHedear = 60;
					var tmp = 85;
					var btm = 0;
					var scrollFlg = true; // 스크롤바 생성 여부(각각 스크롤바 생성 true, 하나만 생성되게 설정 false)
					
					//수진자별 결과관리 수진자 목록, 검사결과 각각 스크롤바 생성되게 변경
					if(scrollFlg){
						btm = 20;
						if(browser == "ie"){
							tmp = 95;
						}
						if(isNull(curr )){
							tmp = 0;
						}
						//부모 높이 - 검색영역 - 페이징 높이 - 하단높이 - (예외 높이)
						gridH = parent.innerHeight - $(".srch_wrap").eq(0).height() - pagination_area - bottomH - tmp;
						//부모 높이 - 페이징 높이 - 하단높이 - (예외 높이)
						//gridH = parent.innerHeight - pagination_area - bottomH - tmp;
						
						// 300 보다 작아질경우 300으로 고정
						if(gridH<300){
							gridH=300;
						}
						
						//넓이가 1024보다 작아질경우 높이 고정
						/*						
						if(window.innerWidth<1024){
							gridH -= 20;	// 그리드 최대 높이
							btm += 5;
							if(window.innerWidth<780){
								btm  = btm- 20;
							}
						}
						*/
						//높이가 768보다 작아질경우 높이 고정
/*						if(parent.innerHeight<768){
							gridH = 300;	// 그리드 최대 높이
						}*/
						
						$(".realgridH").height(gridH);
						
						if(!isNull(typeof rsTableList)){
							//if(rsTableList.length > 0){
								// 그리드 내역이 있을경우 높이 설정 
								var totalCnt = rsTableList.length;
								if(totalCnt>0){
									realgrid2Height = gridHedear + ( totalCnt * gridRowHeight);
									//console.log(rstResultTable.offsetHeight);
									// table 높이 지정
									realgrid2Height = rstResultTable.offsetHeight;
								}else{
									// 검사 결과가 없을경우 수진자 목록 화면의 높이와 같게 설정
									realgrid2Height = $("#rstListDiv").height() - $("#infoDiv").height() - 140;
								}
								$("#rstTableDiv").height(realgrid2Height);	// 기본 그리드 높이 설정
								//$("#rstResultTable").height(realgrid2Height);	// 기본 그리드 높이 설정
							//}
						}
			    		$("#rstDiv").height( $("#rstListDiv").height()- 59 );	// 기본 그리드 높이 설정
						
					}else{// scrollFlg == false
						btm = 90;
						if(!isNull(typeof rsTableList)){
							//if(rsTableList.length > 0){
						    	// 그리드 내역이 있을경우 높이 설정 
					    		var totalCnt = rsTableList.length;
					    		if(totalCnt>0){
					    			realgrid2Height = gridHedear + ( totalCnt * gridRowHeight);
									// table 높이 지정
									realgrid2Height = rstResultTable.offsetHeight;
					    		}else{
					    			realgrid2Height = 400;
					    		}
					    		$("#rstResultTable").height(realgrid2Height);	// 기본 그리드 높이 설정
					    		//$("#rstTableDiv").height(realgrid2Height);	// 기본 그리드 높이 설정
							//}
						}

						var totalCnt1 = rsTableList.length;					
			    		if(totalCnt1 > 0){
							totalHeight = gridHedear + ( totalCnt1 * gridRowHeight);
						}else{
							totalHeight = 400;
						}
						$(".realgridH").height(totalHeight);
						//gridView1.resetSize();

					}
//		    		if(totalCnt1>0){
//						$(".realgridH").height(gridHedear + ( totalCnt1 * gridRowHeight));
//		    		}
					
/*					if(!isNull(typeof gridView1)){
						gridView1.resetSize();
					}
					if(!isNull(typeof gridView2)){
						gridView2.resetSize();
					}*/

					if(browserEdge != "edge"){ 
//						$('#main_area', parent.document).height(document.body.scrollHeight+50);
						$('#menu_area', parent.document).height(document.body.scrollHeight+btm);
					}else{
						if(btm>30){
							btm = btm - 30;
						}
						$('#menu_area', parent.document).height(document.body.scrollHeight+btm);
//						$('#main_area', parent.document).height(document.body.scrollHeight);
					}
//					$('#menu_area', parent.document).height(Math.ceil(finalAreaH/gridDivision)*gridDivision);
				}else{	// RSTUSERT 시작

					//RSTUSER 수진자별 결과 관리 일때 그리드 크기 변경
					var realgrid2Height = 0;
					var gridHedear = 60;
					var tmp = 150;
					var btm = 0;
					var scrollFlg = true; // 스크롤바 생성 여부(각각 스크롤바 생성 true, 하나만 생성되게 설정 false)
					
					//수진자별 결과관리 수진자 목록, 검사결과 각각 스크롤바 생성되게 변경
					if(scrollFlg){
						btm = 50;
						if(browser == "ie"){
							tmp = 160;
						}
						if(isNull(curr )){
							tmp = 0;
						}
						//부모 높이 - 검색영역 - 페이징 높이 - 하단높이 - (예외 높이)
						gridH = parent.innerHeight - $(".srch_wrap").eq(0).height() - pagination_area - bottomH - tmp;
						
						// 300 보다 작아질경우 300으로 고정
						if(gridH<300){
							gridH=300;
						}
						
						//넓이가 1024보다 작아질경우 높이 고정
						if(window.innerWidth<1024){
							gridH = 300;	// 그리드 최대 높이
							btm += 30;
							if(window.innerWidth<780){
								btm  = btm- 20;
							}
						}
						//높이가 768보다 작아질경우 높이 고정
						if(parent.innerHeight<768){
							gridH = 300;	// 그리드 최대 높이
						}
						
						$(".realgridH").height(gridH);

						if(!isNull(typeof dataProvider2)){
					    	// 그리드 내역이 있을경우 높이 설정 
				    		var totalCnt = dataProvider2.getRowCount();
				    		if(totalCnt>0){
				    			realgrid2Height = gridHedear + ( totalCnt * gridRowHeight);
				    		}else{
				    			// 검사 결과가 없을경우 수진자 목록 화면의 높이와 같게 설정
								realgrid2Height = $("#rstListDiv").height() - $("#infoDiv").height()-110;
				    		}
				    		$("#realgrid2").height(realgrid2Height);	// 기본 그리드 높이 설정
						}
			    		$("#rstDiv").height( $("#rstListDiv").height()-80 );	// 기본 그리드 높이 설정
						
					}
					else{
						btm = 90;
						if(!isNull(typeof dataProvider2)){
					    	// 그리드 내역이 있을경우 높이 설정 
				    		var totalCnt = dataProvider2.getRowCount();
				    		if(totalCnt>0){
				    			realgrid2Height = gridHedear + ( totalCnt * gridRowHeight);
				    		}else{
				    			realgrid2Height = 400;
				    		}
				    		$("#realgrid2").height(realgrid2Height);	// 기본 그리드 높이 설정
						}
	
			    		totalCnt1 = dataProvider1.getRowCount();					
			    		if(totalCnt1 > 0){
							totalHeight = gridHedear + ( totalCnt1 * gridRowHeight);
						}else{
							totalHeight = 400;
						}
						$(".realgridH").height(totalHeight);
						gridView1.resetSize();

					}
//		    		if(totalCnt1>0){
//						$(".realgridH").height(gridHedear + ( totalCnt1 * gridRowHeight));
//		    		}
					
					if(!isNull(typeof gridView1)){
						gridView1.resetSize();
					}
					if(!isNull(typeof gridView2)){
						gridView2.resetSize();
					}

					if(browserEdge != "edge"){ 
//						$('#main_area', parent.document).height(document.body.scrollHeight+50);
						 $('#menu_area', parent.document).height(document.body.scrollHeight+btm);
					}else{
						if(btm>30){
							btm = btm - 30;
						}
						$('#menu_area', parent.document).height(document.body.scrollHeight+btm);
//						$('#main_area', parent.document).height(document.body.scrollHeight);
					}
//					$('#menu_area', parent.document).height(Math.ceil(finalAreaH/gridDivision)*gridDivision);
					
				}
			}

		}else{
			var curr = curr_menu_cd;
			var parentbody = $(document).height();
			
			
			if(isNull(curr )){
				$('#main_area', parent.document).height(parentbody - 70);
			}else{
				$('#menu_area', parent.document).height(parentbody- 80);
			}
		}
//			 parent.resizeTopIframe(height,width);
	}

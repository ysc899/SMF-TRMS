var displayMenu = function (leftMenu) {
    var menu = treeModel(leftMenu, "ROOT");
    var html = "";
    for (var i in menu) {
        html += "<li class=\"" + menu[i].S001ICN + "\">";
        
        if(menu[i].S001PTH === "" ){
        	html += "<a href=\"#\" " ;
        }else{
        	html += "<a href=\" javascript:void(0);\" ";
        	html += "onclick=\"goPage('" + menu[i].S001MCD + "','"+menu[i].S001MNM +"','" + menu[i].S001PTH + "')\" ";
        }
//        html += " data-toggle=\"tooltip\" data-placement=\"right\"  title=\"" + menu[i].S001MNM + "\"> " + menu[i].S001MNM;
        html += " style='font-weight: bold;' data-toggle=\"tooltip\" data-placement=\"right\"  title=\"" + menu[i].S001MNM + "\"> " + menu[i].S001MNM;
        
        if (menu[i].children != undefined) {
            html += "<span class=\"glyphicon glyphicon-menu-down pull-right\" aria-hidden=\"true\"></span>";
        }
        html += "</a>";

        if (menu[i].children != undefined) {
            html += "<ul class=\"dep_lst\">";
            for (var j in menu[i].children) {
                html += "<li><a href=\" javascript:void(0);\" ";
                html += "onclick=\"goPage('" + menu[i].children[j].S001MCD + "','"+menu[i].children[j].S001MNM +"','" + menu[i].children[j].S001PTH + "')\">";
                html +=  menu[i].children[j].S001MNM + "</a></li>";
            }
            html += "</ul>";
        }
        html += "</li>";
    }

    $(".left-menu-item").html(html);
	$("[data-toggle='tooltip']").tooltip();
    menuli();
	$('.arrowArea').hide();
};

function treeModel(arrayList, rootId) {
	var rootNodes = [];
	var traverse = function (nodes, item, index) {
		if (nodes instanceof Array) {
			return nodes.some(function (node) {
				if (node.S001MCD === item.S001PMCD) {
					node.children = node.children || [];
					return node.children.push(arrayList.splice(index, 1)[0]);
				}
				return traverse(node.children, item, index);
			});
		}
	};

	while (arrayList.length > 0) {
		arrayList.some(function (item, index) {
			if (item.S001PMCD === rootId) {
				return rootNodes.push(arrayList.splice(index, 1)[0]);
			}
			return traverse(rootNodes, item, index);
		});
	}
	return rootNodes;
};



function FavoritesBtn(menu_cd){
	var bookmarkOn ="";
	for(var i in FavoritesList){
		if(menu_cd == FavoritesList[i].S006MCD ){
			bookmarkOn = "on";
			break;
		}
	}
	
	var formData = $("#MainForm").serializeArray();
	formData.push({ name: "I_MCD",value : menu_cd});
	if(bookmarkOn==""){
		$("#b_"+menu_cd).addClass("on");
		ajaxCall("/FavoritesSave.do", formData);
	}else{
		$("#b_"+menu_cd).removeClass("on");
		ajaxCall("/FavoritesDel.do", formData);
	}
}

var pageMenu_cd = "";
var pageMenu_nm = "";
var pageUrl = "";
var pageParam = "";

//해당 페이지 생성 및 오픈
//menu_cd: 'mdi_' + menu id
//menu_nm: 'mdi_' + menu id + '_con'
function goPage(menu_cd, menu_nm, url,param){
	
	if(url.indexOf("http")>-1)
	{
		//location.href(url);
		window.location.replace(url);
		return;
	}
	
	//메인 내용 지우기
	delMainConts();
	
//	console.log(url);
	param = isNull(param)?"":param;
	if(open_pages_url.indexOf(url) > -1){
		if(!isNull(param)){
			if(param.indexOf("&") > -1){
				close_tab(menu_cd, menu_nm, url);
			}
		}
	}
	if($('.btn_menu').hasClass('active')){	
		$(".btn_menu").trigger("click");
	}
	// menu 왼쪽 최소화 유지
	if($('.side_wrap').hasClass('onKeep')){
		$('.button_close').trigger("click");
	}
	//console.log(url);
	//console.log(param);
	
//		//해당 페이지 리로딩이 아닌 현재 편집된 그대로 다시 보여주기 위함
	if(open_pages_url.indexOf(url) < 0){
		pageMenu_cd = menu_cd;
		pageMenu_nm = menu_nm;
		pageParam = param;
		pageUrl = url;
//		var formData = $("#MainForm").serializeArray();
//		formData.push({ name: "I_MCD",value : menu_cd});
//		
//		ajaxCall("/MenuQcSetList.do", formData);
		createPage(menu_cd, menu_nm, url, param);
	}else{
		openPage(menu_cd);
	}
}

//메인 내용을 지운다.
function delMainConts(){
	var main_area_div = $("#main_area");
	var menu_area_div = $("#menu_area");
	
	main_area_div.removeClass("display_on");
	main_area_div.addClass("display_off");
	menu_area_div.removeClass("display_off");
	menu_area_div.addClass("display_on");
	
// 	$("#mdi_tab_menu_con").height($( "#menu_area" ).height() - 70);
 	
 	
 	
	//메인 내용 지움
	//main_area_div.empty() ;
}

function onMainResult(strUrl,response)
{
	var f = document.pageFrom;
	var resultList =  response.resultList;
	var param = "";
	if(!isNull(pageParam)){
		param = pageParam;
	}
	if(resultList.length>0){
		f.Quick.value				=  JSON.stringify(response.resultList);
		param += "&Quick="+encodeURIComponent(JSON.stringify(response.resultList));
	}
	
//	f.param.value				=  param;
	//console.log(param);
	
	
	createPage(pageMenu_cd, pageMenu_nm, pageUrl, param);
}

//해당 페이지 생성 및 오픈
//menu_cd: 'mdi_' + menu id
//menu_nm: 'mdi_' + menu id + '_con'
function createPage(menu_cd, menu_nm, url,param){
	$("#"+curr_menu_cd).removeClass("display_on");
	$("#"+curr_menu_cd).addClass("display_off");

	var bookmarkOn = "";
	for(var i in FavoritesList){
		if(menu_cd == FavoritesList[i].S006MCD ){
			bookmarkOn = "on";
			break;
		}
	}

	var main_swiper_tabs_str = "";
	main_swiper_tabs_str +="   <li role=\"presentation\"  id='li_"+menu_cd+"'  class=\"active\">";
	main_swiper_tabs_str +="   	  <a href=\"#\" role=\"tab\" data-toggle=\"tab\">       ";
	main_swiper_tabs_str +="       <button class=\"tab_bookmark "+bookmarkOn+"\"  id=\"b_"+menu_cd+"\" onClick=\"javascript:FavoritesBtn('"+menu_cd+"')\" ><span class=\"book_mark_ico\"></span></button>";
	main_swiper_tabs_str +="           <strong class=\"ellipsis menu_name\"  onClick=\"javascript:openPage('"+menu_cd+"')\">";
	main_swiper_tabs_str += 			  menu_nm+ " </strong>";
	main_swiper_tabs_str +="           <button class=\"close pull-right\" data-dismiss=\"popover-x\"   onClick=\"javascript:close_tab('"+menu_cd+"','"+menu_nm+"','"+url+"')\" >&times;</button>       ";
	main_swiper_tabs_str +="       </a>       ";
	main_swiper_tabs_str +="   </li>       ";
	
  	open_pages_url = open_pages_url + url;
  	
  	$('<iframe id="'+menu_cd+'" name="'+menu_cd+'" style="height:100%;width:100%;border:0; " scrolling="no"></iframe>').appendTo('#mdi_tab_menu_con');

  	$.each($("#tablist > li"), function(i, e) {
  		$(this).removeClass('active');
  	});
	
	
	$("#tablist").append(main_swiper_tabs_str);

    $('.nav-tabs').scrollingTabs('refresh', {
      forceActiveTab: true // make our new tab active
    });
    param += "&QuickYN=Y";
    param = isNull(param) ? "" : param;

    
    //console.log( url +"   "+param);
    
//	var f = document.pageFrom;
//
//	f.menu_cd.value				=  menu_cd;
//	f.menu_nm.value				=  menu_nm;
//	f.action = url +"?var=1"+param;
//	f.action = url +"?var=1"+param;
//	f.target = menu_cd;       //target을 iframe으로 잡는다.
//	f.submit();                // submit 호출
	
  	document.getElementById(menu_cd).src = url +"?menu_cd="+menu_cd+"&menu_nm="+encodeURIComponent(menu_nm)+param;
  	tabCnt++;
  	pre_menu_cd = curr_menu_cd;
  	curr_menu_cd = menu_cd;
	if(menu_cd == 'RSTUSERTABLE' || menu_cd == 'RSTUSERTABLEENG'){
		$('.content_wrap').css('padding-top', '0px');
		$('#copyFoot').css('background-color', '#cdcdcd');
	}
	
}
//현재 open mdi 
var curr_menu_cd = "";

var tabCnt = 0;

/** 페이지 종료 **/
function close_tab(menu_cd, menu_nm, url){
	is_page_del = true; 
	$("#"+menu_cd).remove();
	$("#li_"+menu_cd).remove();
	
	tabCnt--;
	
	//해당 페이지 지우기
	//삭제했다가 다시 리로딩 하기 위해 지움
	open_pages_url = open_pages_url.replace(url, ""); 

	if(open_pages_url != null && open_pages_url != ""){
		//현재 메뉴를 닫았을 경우 마지막 메뉴를 활성화 한다.
		if(!document.getElementById(curr_menu_cd)) {
			var TabMenu_cd = $("#tablist li:last").attr("id");
			TabMenu_cd = TabMenu_cd.replace("li_","");
			openPage(TabMenu_cd);
		}
	}
    $('.nav-tabs').scrollingTabs('refresh', {
        forceActiveTab: true // make our new tab active
      });

	if(open_pages_url == null || open_pages_url == ""){
		//메뉴가 없을 경우 메인 내용 보여주기
		pre_menu_cd = null;
		pre_menu_cd = null;
		curr_menu_cd = null;
		curr_menu_cd = null;
		var main_area_div = $("#main_area");
		var menu_area_div = $("#menu_area");
		
		main_area_div.removeClass("display_off");
		main_area_div.addClass("display_on");
		menu_area_div.removeClass("display_on");
		menu_area_div.addClass("display_off");
	}
}
/** 페이지 오픈 **/
function openPage(menu_cd){
	if(curr_menu_cd != null || curr_menu_cd != ""){
		$("#"+curr_menu_cd).removeClass("display_on");
		$("#"+curr_menu_cd).addClass("display_off");
		$("#li_"+curr_menu_cd).removeClass("active");
		
		//기존 화면
		pre_menu_cd = curr_menu_cd;
	}
  	$.each($("#tablist > li"), function(i, e) {
  		$(this).removeClass('active');
  	});
	var slideIdx = 0;
	
	$.each($(".swiper-slide"), function(i, e) {
		if($(e)[0].id == "li_"+menu_cd){//curr_menu_cd
			slideIdx = i;
			return;
		}
	});
//	scrollToActiveTab();
	$("#li_"+menu_cd).addClass("active");
	$("#"+menu_cd).addClass("display_on");
	$("#"+menu_cd).removeClass("display_off");
    $('.nav-tabs'). scrollingTabs ( 'scrollToActiveTab' );
	curr_menu_cd = menu_cd;
	
	if(menu_cd == 'RSTUSERTABLE'){
		$('.content_wrap').css('padding-top', '0px');
		$('#copyFoot').css('background-color', '#cdcdcd');
	}else{
		$('.content_wrap').css('padding-top', '5px');
		$('#copyFoot').css('background-color', '');
	}
}


function menuli()
{
	// Local Navigation Bar
	$(".left-menu-item>li>a").on("click keyup",function(){
		var current=$(this).parent(),
			currentList=current.find('.dep_lst'),
			old=$(this).parent().siblings('.on'),
			oldList=old.find('.dep_lst');
		if($(".side_wrap").hasClass("on")){		
			$(".button_menu").trigger("click");
		}
		if(currentList.html()!=undefined && !$(".side_wrap").hasClass("on")){
			currentList.css('display',currentList.css('display'));
			current.toggleClass('on').find('.dep_lst').slideToggle(250);
			old.removeClass('on').find('.dep_lst').show();
			oldList.slideUp(250,function(){old.find('.dep_lst').hide().find('li').removeClass('on')});

			return false; //
		};
	});
}
/**팝업 크기 설정**/
function resizeTopIframe(dynheight,dynWidth) {  
	if(dynheight< 100){
		dynheight = 500;
		dynWidth = 700;
	}
	//width
	$('#pageiframe').height(parseInt(dynheight) + 10);
	$('#popCont').height(parseInt(dynheight) + 90);
	
	if(browserEdge == "edge"){
		$('#pageiframe').width(parseInt(dynWidth));
		$('#popCont').width(parseInt(dynWidth) + 35);
	}else{
		$('#popCont').width(parseInt(dynWidth) + 35);
		$('#pageiframe').width(parseInt(dynWidth) + 10);
	}
	
	if(dynheight+100 >= window.innerHeight){
		$('.modal-backdrop').height(parseInt(dynheight) +90+ 90);
	}else{
		$('.modal-backdrop').height(window.innerHeight);
	}
}
function resizeTopIframe2(dynheight,dynWidth) {  
	if(dynheight< 100){
		dynheight = 500;
		dynWidth = 700;
	}
	//width
	$('#pageiframe2').height(parseInt(dynheight) + 10);
//	$('#pageiframe2').width(parseInt(dynWidth) + 10);
	$('#popCont2').height(parseInt(dynheight) + 90);
//	$('#popCont2').width(parseInt(dynWidth) + 35);
	if(browserEdge == "edge"){
		$('#pageiframe2').width(parseInt(dynWidth));
		$('#popCont2').width(parseInt(dynWidth) + 35);
	}else{
		$('#pageiframe2').width(parseInt(dynWidth) + 10);
		$('#popCont2').width(parseInt(dynWidth) + 35);
	}
	
	if(dynheight>800){
		$('.modal-backdrop').height(parseInt(dynheight) + 120+ 70);
	}
}

/*********** 자동 높이 조절 시작(부모의 ifram 높이를 조정)***********/


//자식 화면의 크기대로 iframe 크기 변경
function chg_main_areaH(bodyH){
	//_debug( "chg_contentsH bodyH = "+ bodyH);
	document.getElementById("main_conts").style.height=  bodyH   + "px";
	document.getElementById("mdi_tab_menu_con").style.height=  bodyH   + "px";
}

/*********** 자동 높이 조절 끝(부모의 ifram 높이를 조정)***********/


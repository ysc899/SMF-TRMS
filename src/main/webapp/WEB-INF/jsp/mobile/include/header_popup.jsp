<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <!--header-->
    <header style="position:relative;">
      <!-- <div id="wrapper">
        <div id="line-wrapper">
          <div id="line-top" class="line init top-reverse"></div>
          <div id="line-mid" class="line init mid-reverse"></div>
          <div id="line-bot" class="line init bot-reverse"></div>
        </div>
      </div> -->
		<img src="./images/mobile/undo.png" alt="뒤로가기화살표" class="undo _popup-close">
      <p><img src="/images/mobile/logo.png" alt="씨젠의료재단로고" class="logo"></p>
      <p>
<!--       <p><img src="/images/mobile/login_ico.png" alt="로그인아이콘" class="login_ico _goNavi" data-url="/mobileMyPage.do"></p> -->
      <p class="area_name"></p>
    </header>
    <!--//header End-->
    
    
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("._popup-close").click(function(){
	    		if (window.opener !== null) {	  			 
	  				window.close();
	  			} else {	  			
	  			window.location.href="https://trms.seegenemedical.com/mobileTestItem.do"
	  			}
	    	});
	    	
	    });
    
    </script>
    

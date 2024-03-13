<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!--navigation-->
    <div class="navigation">
      <table>
        <thead>
          <tr>
            <td style="padding-left:5px;" class="_goNavi" data-url="/mobileMain.do"><img src="./images/mobile/ico_bt01.png" alt="하단고정버튼01" style="width:45%;">
              <span>메인으로</span>
            </td>

            <td style="padding-left:5px;" class="_goNavi" data-url="/mobileTestItem.do"><img src="./images/mobile/ico_bt02.png" alt="하단고정버튼02" style="width:40%;">
              <span>항목조회</span>
            </td>

            <td class="_goNavi" data-url="/mobileRstUserTableMini01.do"><img src="./images/mobile/ico_bt03.png" alt="하단고정버튼03" style="width:45%;padding-bottom:5px;">
              <span>결과조회</span>
            </td>

            <td style="padding:0;" class="_goNavi"  data-url="/mobileRecvRstCorona.do"><img src="./images/mobile/ico_bt04.png" alt="하단고정버튼04" style="width:40%;">
              <span>결과보고</span>
            </td>

            <td class="_goNavi" data-url="/mobileNotice.do"><img src="./images/mobile/ico_bt05.png" alt="하단고정버튼05" style="width:50%;padding-bottom:3px;">
              <span>새소식</span>
            </td>

          </tr>
        </thead>
      </table>
    </div>
  <!--//navigation End-->
  
  <script type="text/javascript">
  	
  
  var login_user_id = "<%= ss_uid %>";
  
	$("document").ready(function(){
		
		$("._goNavi").click(function(){
			
			location.href = ($(this).data('url'));
		});
		
		$("._goAlert").click(function(){
			alert('추후 오픈 예정 입니다.');
			return;
		});
		
	});
	
	
	function parentMag(type,yesFn,strMessage){
		var regex = /<[\/]br\s*[\/]?>/gi;
		alert(strMessage.replace(regex, '\n'));
	}
  
  </script>
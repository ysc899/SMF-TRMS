<%@page  pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="kr.co.softtrain.common.web.main.MainController"%>
<%@page import="kr.co.softtrain.custom.util.Utils"%>
	<script type="text/javascript" src="../plugins/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="../plugins/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../plugins/jquery/jquery.serializeObject.min.js"></script>
	<script type="text/javascript" src="../plugins/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="../plugins/bootstrap/bootstrap-popover-x.js"></script>
	<script type="text/javascript" src="../plugins/jquery/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="../plugins/jquery/jquery.blockUI.js"></script>
	<script type="text/javascript" src="../plugins/jquery/jquery.scrolling-tabs.js"></script>
	
	<!-- <script type="text/javascript" src="../plugins/jquery/jcf.js"></script> -->
	<!-- <script type="text/javascript" src="../plugins/jquery/jcf.select.js"></script> -->
	
	<script type="text/javascript" src="../plugins/chartjs/Chart.js"></script>
	<!-- rsa -->
	<script type="text/javascript" src="/plugins/rsa/jsbn.js"></script>
	<script type="text/javascript" src="/plugins/rsa/rsa.js"></script>
	<script type="text/javascript" src="/plugins/rsa/prng4.js"></script>
	<script type="text/javascript" src="/plugins/rsa/rng.js"></script>
	<!-- rsa -->
	<script type="text/javascript" src="../js/graph.js?var=19040101"></script>
	<script type="text/javascript" src="../js/mobile-common.js?var=19040102"></script>
	<script type="text/javascript" src="../js/message.js"></script>
	<script type="text/javascript" src="../js/loading.js?var=19040101"></script>
	<script type="text/javascript" src="../js/pagePopup.js?var=19040101"></script>
	<script type="text/javascript" src="../js/mobile-callAjax.js?var=210602-3"></script>
	<script type="text/javascript" src="../js/mobile-utils.js?var=19040524"></script>
	<%
		String menu_cd = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("menu_cd"), "");
		String menu_nm = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("menu_nm"), "");
		String QuickYN = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("QuickYN"), "");
// 		String Quick = org.apache.commons.lang3.StringUtils.defaultIfEmpty((String)request.getParameter("Quick"), "");

		List<Map<String, Object>>  paramList = new ArrayList<Map<String, Object>>();
	  	Map<String, Object> param2 = new HashMap<String, Object>();
	  	MainController main = new MainController();
// 		I_MCD
		if("Y".equals(QuickYN)){
			param2.put("I_LOGMNU", menu_cd );
			param2.put("I_LOGMNM", menu_nm );
			param2.put("I_MCD", menu_cd );
			paramList =  main.getMenuQcSetList(param2);
		}
		
// 		if(!"".equals(Quick)){
// 			paramList =  Utils.jsonList(Quick);	// 데이터 Parameter 상태로 변경
// // 			System.out.println(paramList.size());
// 		}
	
	%>
	<script>
		var I_LOGMNU = "<%=menu_cd%>";
		var I_LOGMNM = "<%=menu_nm%>";
		$(document).ready( function(){
			$("#I_LOGMNU").val(I_LOGMNU);
			$("#I_LOGMNM").val(I_LOGMNM);
			
			if($('.btn_open').length>0){
				$('.btn_open').html('검색영역 닫기');
				
			}
			
			if($('.btn_open_eng').length>0){
				$('.btn_open_eng').html('Search area close');
			}
		});
	</script>
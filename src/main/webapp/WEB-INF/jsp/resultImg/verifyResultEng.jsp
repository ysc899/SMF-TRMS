<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.tuple.Pair"%>
<%
	String dat = Objects.toString(request.getParameter("dat"), "");
	String jno = Objects.toString(request.getParameter("jno"), "");
	String reportLang = Objects.toString(request.getParameter("reportLang"), "");

	String pttName = Objects.toString(request.getAttribute("R_100NAM"), "");      // 수진자명
	String pttAge = Objects.toString(request.getAttribute("R_100AGE"), "");       // 나이
	String pttSex = Objects.toString(request.getAttribute("R_100SEX"), "");       // 성별
	String pttBirth = Objects.toString(request.getAttribute("R_100JNR"), "");     // 생년월일
	String mdcInstName = Objects.toString(request.getAttribute("R_120FNM"), "");  // 의료기관명
	String mdcInstNo = Objects.toString(request.getAttribute("R_120GIC"), "");    // 기관번호
	String mngNames = Objects.toString(request.getAttribute("R_HNAM"), "");       // 담당검사자
	String mngDrs = Objects.toString(request.getAttribute("R_HLIC"), "");         // 담당전문의
	String cnfmDrs = Objects.toString(request.getAttribute("R_LNAM"), "");        // 확인전문의
	String repMdcDr = Objects.toString(request.getAttribute("R_LLIC"), "");       // 대표의료원장
	String sampleRcptDt = Objects.toString(request.getAttribute("R_PJDT"), "");   // 검체접수일자
	String sampleRcptTm = Objects.toString(request.getAttribute("R_PJTM"), "");   // 검체접수시간
	String insptDt = Objects.toString(request.getAttribute("R_PSDT"), "");        // 검사시행일자
	String resultRptDt = Objects.toString(request.getAttribute("R_PBDT"), "");    // 결과보고일자
	String resultRptTm = Objects.toString(request.getAttribute("R_PBTM"), "");    // 결과보고시간

	List<String> testAddrs = (List<String>)request.getAttribute("R_ADDR_LIST");      // 검사실시기관
	
	List<Pair<String, String>> covidResults = (List<Pair<String, String>>)request.getAttribute("COVID_RESULT_LIST");		// 검사결과 조회
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta name="p:domain_verify">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Seegene</title>
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
    <meta name="format-detection" content="telephone=no">
	 
	<link rel="stylesheet" type="text/css" href="../css/resultImg/sg_ui.css">
</head>
<body>
    <div class="ui-wrap check-wrap en">
        <div class="ui-container">
            <!--  title-box s -->
            <div class="title-box">
                <h1 class="logo">
                    <img src="../images/resultImg/logo_img_en.png" alt="Seegene Medical Foundation">
                </h1>
                <p class="txt">
                    We confirm that the test was conducted<br>
                    <span>by the Seegene Medical Foundation</span> as below.
                </p>
            </div>
            <!--// title-box e -->

            <!-- content s -->
            <div class="content">
                <!-- 수검자/검사기관 정보 s -->
                <div class="section">
                    <h4 class="cation">
                        <span class="icon"><img src="../images/resultImg/icons/building.png" alt="icon"></span>
                        <p class="txt">Examinee / Institution</p>
                    </h4>
                    <div class="table-wrap">
                            <table>
                                <colgroup>
                                    <col width="50%">
                                    <col width="50%">
                                </colgroup>
                                <tbody>
                                    <tr class="border-tn">
                                        <td colspan="2">
                                            <span class="sub-title">Institution</span>
                                            <%=mdcInstName %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="sub-title">Examinee Name</span>
                                            <%=pttName %>
                                        </td>
                                        <td>
                                            <span class="sub-title">Date of birth</span>
                                            <%=pttBirth %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="sub-title">Age/Gender</span>
                                            <%=pttAge %>/<%=pttSex %>
                                        </td>
                                        <td>
                                            <span class="sub-title">Received Date</span>
                                            <%=sampleRcptDt %>&nbsp;<%=sampleRcptTm %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="sub-title">Testing Date</span>
                                            <%=insptDt %>
                                        </td>
                                        <td>
                                            <span class="sub-title">Reporting Date</span>
                                            <%-- <span class="data"><%=resultRptDt %>&nbsp;<%=resultRptTm %></span> --%>
                                            <%=resultRptDt %>&nbsp;<%=resultRptTm %>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                </div>
                <!--// 수검자/검사기관 정보 e -->

                <!-- 검사결과 s -->
                <div class="section">
                    <h4 class="cation">
                        <span class="icon bottle"><img src="../images/resultImg/icons/reagent_bottle.png" alt="icon"></span>
                        <p class="txt">Test Result</p>
                    </h4>
                    <div class="table-wrap result-box">
                        <table>
                            <colgroup>
                                <col width="68%">
                                <col width="auto">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">Test Item</th>
                                    <th scope="col">Result</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<% for (Pair<String, String> resultRow : covidResults) { %>
                                <tr>
                                    <td><%=resultRow.getLeft() %></td>
                                    <td><%=resultRow.getRight() %></td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!--// 검사결과 e -->

                <!-- 검사자 정보 s -->
                <div class="section">
                    <div class="table-wrap">
                        <table>
                            <colgroup>
                                <col width="50%">
                                <col width="50%">
                            </colgroup>
                                <tbody>
                                <tr class="border-tn">
                                    <td>
                                        <span class="sub-title">Tested by</span>
                                        <%=mngNames %>
                                    </td>
                                    <td>
                                        <span class="sub-title">Section Director</span>
                                        <%=mngDrs %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="sub-title">Head of Center</span>
                                        <%=cnfmDrs %>
                                    </td>
                                    <td>
                                        <span class="sub-title">Chief Medical Director</span>
                                        <%=repMdcDr %>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="border-n">
                                        <span class="sub-title">Testing Laboratory</span>
                                        <ul class="lab-info">
                                        	<% for (String addrItem : testAddrs) { %>
												<li><%=addrItem %></li>
											<% } %>
                                        </ul>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!--// 검사자 정보 e -->
            </div>
            <!--// content e -->
        </div>
    </div>
</body>
</html>
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
    <div class="ui-wrap check-wrap">
        <div class="ui-container">
            <!--  title-box s -->
            <div class="title-box">
                <h1 class="logo">
                    <img src="../images/resultImg/logo_img.png" alt="씨젠 의료 제단">
                </h1>
                <p class="txt">
                    아래와 같이<br><span>(재)씨젠의료재단에서 검사하였음을 확인</span>합니다.
                </p>
            </div>
            <!--// title-box e -->

            <!-- content s -->
            <div class="content">
                <!-- 수검자/검사기관 정보 s -->
                <div class="section">
                    <h4 class="cation">
                        <span class="icon"><img src="../images/resultImg/icons/building.png" alt="icon"></span>
                        <p class="txt">수검자/검사기관 정보</p>
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
                                            <span class="sub-title">의료기관명</span>
                                            <%=mdcInstName %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="sub-title">수진자명</span>
                                            <%=pttName %>
                                        </td>
                                        <td>
                                            <span class="sub-title">생년월일</span>
                                            <%=pttBirth %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="sub-title">나이/성별</span>
                                            <%=pttAge %>/<%=pttSex %>
                                        </td>
                                        <td>
                                            <span class="sub-title">검체접수일</span>
                                            <%=sampleRcptDt %>&nbsp;<%=sampleRcptTm %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="sub-title">검사시행일</span>
                                            <%=insptDt %>
                                        </td>
                                        <td>
                                            <span class="sub-title">결과보고일</span>
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
                        <p class="txt">검사결과</p>
                    </h4>
                    <div class="table-wrap result-box">
                        <table>
                            <colgroup>
                                <col width="68%">
                                <col width="auto%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">검사항목</th>
                                    <th scope="col">검사결과</th>
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
                    <h4 class="cation">
                        <span class="icon report"><img src="../images/resultImg/icons/report.png" alt="icon"></span>
                        <p class="txt">검사자 정보</p>
                    </h4>
                    <div class="table-wrap">
                        <table>
                            <colgroup>
                                <col width="50%">
                                <col width="50%">
                            </colgroup>
                                <tbody>
                                <tr class="border-tn">
                                    <td>
                                        <span class="sub-title">담당검사자</span>
                                        <%=mngNames %>
                                    </td>
                                    <td>
                                        <span class="sub-title">담당전문의</span>
                                        <%=mngDrs %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="sub-title">확인전문의</span>
                                        <%=cnfmDrs %>
                                    </td>
                                    <td>
                                        <span class="sub-title">대표의료원장</span>
                                        <%=repMdcDr %>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="border-n">
                                        <span class="sub-title">검사실시기관</span>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

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
    <div class="ui-wrap">
        <div class="ui-container">
            <div class="title">
                <h1>COVID-19 검사<br><span>결과 조회</span></h1>
                <span class="txt">해당 검사 결과 조회를 위해<br>이름과 전화번호, 생년월일을 입력해 주세요. </span>
            </div>

            <form  class="form" method="post" action="/resultInqResult.do" name="reportForm" id="report_form">
                <div class="section-form">
                    <!-- input : 이름 -->
                    <div class="m-input">
                        <label class="a-label" for="cust_name">이름</label>
                        <input class="a-input" type="text" id="cust_name" name="custName" placeholder="이름을 입력해 주세요" required />
                    </div>
                    <!-- // input : 이름 -->

                    <!-- input : 전화번호 -->
                    <div class="m-input">
                        <label class="a-label" for="cust_mobile_no">전화번호</label>
                        <input class="a-input" type="tel" id="cust_mobile_no_fmt" placeholder="‘-’없이  입력해 주세요" required />
                        <input type="hidden" id="cust_mobile_no" name="custMobileNo" />
                    </div>
                    <!-- // input : 전화번호 -->

                    <!-- input : 생년월일 -->
                    <div class="m-input">
                        <label class="a-label" for="cust_birthday">생년월일</label>
                        <input class="a-input" type="text" id="cust_birthday_fmt" placeholder="‘-’없이 8자리 입력해 주세요" required />
                        <input type="hidden" id="cust_birthday" name="custBirthday" />
                    </div>
                    <!-- // input : 생년월일 -->
                </div>
                <button type="submit" class="btn">확인</button>
            </form>
        </div>
    </div>
	<script type="text/javascript">
		(function initPage() {
			const form = document.getElementById('report_form');
			form.addEventListener('submit', function(e) {
				e.preventDefault();
				
				if (isIOSChrome()) {
					alert('아이폰에서는 크롬 브라우저를 지원하지 않습니다.\n아이폰 기본 브라우저인 사파리를 이용하시기 바랍니다.');
					return;
				}
				
				const custName = document.querySelector('#cust_name');
				const custMobileNo = document.querySelector('#cust_mobile_no_fmt');
				const custBirthday = document.querySelector('#cust_birthday_fmt');
				
				if (!custName.value) {
					alert('이름을 입력하세요.');
					custName.focus();
					return;
				}
				
				if (!custMobileNo.value) {
					alert('휴대폰 번호를 입력하세요.');
					custMobileNo.focus();
					return;
				}
				
				if (!mobileNoCheck(custMobileNo.value)) {
					alert('전화번호가 유효하지 않습니다. 확인 후 다시 입력하세요.');
					custMobileNo.focus();
					return;
				}
				
				document.querySelector("#cust_mobile_no").value = custMobileNo.value.replace(/[^0-9]/g, '');
				
				if (!custBirthday.value) {
					alert('생년월일을 입력하세요.');
					custBirthday.focus();
					return;
				}
				
				if (!dateCheck(custBirthday.value)) {
					alert('생년월일이 유효하지 않습니다. 확인 후 다시 입력하세요.');
					custBirthday.focus();
					return;
				}
				
				document.querySelector("#cust_birthday").value = custBirthday.value.replace(/[^0-9]/g, '');
				
				form.submit();
			});
			
			document.querySelector('#cust_mobile_no_fmt').addEventListener("keyup", function(evt) {
				evt.target.value = maskMobileNo(evt.target.value);
			});
			
			document.querySelector('#cust_birthday_fmt').addEventListener("keyup", function(evt) {
				evt.target.value = maskDate(evt.target.value);
			});
			
			if (isIOSChrome()) {
				alert('아이폰에서는 크롬 브라우저를 지원하지 않습니다.\n아이폰 기본 브라우저인 사파리를 이용하시기 바랍니다.');
				return;
			}
		})();
		
		function maskMobileNo(val) {
			const digitOnly = val.replace(/[^0-9]/g, '');
		    const mobileNo = digitOnly.length > 11 ? digitOnly.substring(0, 11) : digitOnly;

		    const regExp2 = /^(01[016789])(\d{1,4})$/;
		    const regExp3 = /^(01[016789])(\d{3,4})(\d{1,4})$/;
		    const regExpFn = /^(01[016789])(\d{3,4})(\d{4})$/;

		    if (regExpFn.test(mobileNo)) {
		    	// const [ , tel1, tel2, tel3] = regExpFn.exec(mobileNo);
		    	// return tel1 + '-' + tel2 + '-' + tel3;
		    	const result = regExpFn.exec(mobileNo);
		    	return result[1] + '-' + result[2] + '-' + result[3];
		    }
		    else if (regExp2.test(mobileNo)) {
		    	// const [ , tel1, tel2] = regExp2.exec(mobileNo);
		        // return tel1 + '-' + tel2;
		    	const result = regExp2.exec(mobileNo);
		    	return result[1] + '-' + result[2];
		    }
		    else if (regExp3.test(mobileNo)) {
		    	// const [ , tel1, tel2, tel3] = regExp3.exec(mobileNo);
		        // return tel1 + '-' + tel2 + '-' + tel3;
		        const result = regExp3.exec(mobileNo);
		        return result[1] + '-' + result[2] + '-' + result[3];
		    }
		    else {
		        return mobileNo.length < 4 ? mobileNo : mobileNo.substring(0, mobileNo.length - 1);
		    }
		}

		function mobileNoCheck(val) {
			var regPhone = /^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$/;
			return regPhone.test(val);
		}
		
		function maskDate(val) {
			const digitOnly = val.replace(/[^0-9]/g, '');
		    const dateVal = digitOnly.length > 8 ? digitOnly.substring(0, 8) : digitOnly;

		    const regExp2 = /^((?:19|20)\d{2})(0[1-9]?|1[012]?)$/;
		    const regExp3 = /^((?:19|20)\d{2})(0[1-9]|1[012])(0[1-9]?|[12][0-9]?|3[01]?)$/;
		    const regExpFn = /^((?:19|20)\d{2})(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/;

		    if (regExpFn.test(dateVal)) {
		    	// const [ , yyyy, mm, dd] = regExpFn.exec(dateVal);
		    	// return yyyy + '-' + mm + '-' + dd;
		    	const result = regExpFn.exec(dateVal);
		    	return result[1] + '-' + result[2] + '-' + result[3];
		    }
		    else if (regExp2.test(dateVal)) {
		    	// const [ , yyyy, mm] = regExp2.exec(dateVal);
		        // return yyyy + '-' + mm;
		        const result = regExp2.exec(dateVal);
		        return result[1] + '-' + result[2];
		    }
		    else if (regExp3.test(dateVal)) {
		    	// const [ , tel1, tel2, tel3] = regExp3.exec(dateVal);
		        // return yyyy + '-' + mm + '-' + dd;
		    	const result = regExp3.exec(dateVal);
		    	return result[1] + '-' + result[2] + '-' + result[3];
		    }
		    else {
		        return dateVal.length < 5 ? dateVal : dateVal.substring(0, dateVal.length - 1);
		    }
		}

		function dateCheck(datVal) {
			var regDate = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
			return regDate.test(datVal);
		}
		
		function isIOSChrome() {
			const uaLower = navigator.userAgent.toLowerCase();
			return (uaLower.indexOf('iphone') >= 0 && uaLower.indexOf('crios') >= 0);
		}
	</script>
</body>
</html>
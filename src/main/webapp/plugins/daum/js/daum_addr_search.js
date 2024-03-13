document.write("<script  language=javascript src='https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js'></script>");
//document.write("<script  language=javascript src='https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js'></script>");
//http://dmaps.daum.net/map_js_init/postcode.v2.js
/***********************************************************************
반환되는 data 형태 예) data.address 
	postcode: "300-834"
	postcode1: "300"
	postcode2: "834"
	postcodeSeq: "001"
	zonecode: "34672"
	address: "대전 동구 판교1길 14"
	addressEnglish: "14, Pangyo 1-gil, Dong-gu, Daejeon, Korea"
	addressType: "R"
	bcode: "3011010700"
	bname: "판암동"
	bname1: ""
	bname2: "판암동"
	sido: "대전"
	sigungu: "동구"
	sigunguCode: "30110"
	userLanguageType: "K"
	query: "대전광역시 동구 판교1길 "
	buildingName: ""
	buildingCode: "3011010700105080002030763"
	apartment: "N"
	jibunAddress: "대전 동구 판암동 508-2"
	jibunAddressEnglish: "508-2, Panam-dong, Dong-gu, Daejeon, Korea"
	roadAddress: "대전 동구 판교1길 14"
	roadAddressEnglish: "14, Pangyo 1-gil, Dong-gu, Daejeon, Korea"
	autoRoadAddress: ""
	autoRoadAddressEnglish: ""
	autoJibunAddress: ""
	autoJibunAddressEnglish: ""
	userSelectedType: "J"
	hname: ""
	roadnameCode: "4292553"
	roadname: "판교1길"
***********************************************************************/

// 우편번호 찾기 찾기 화면을 넣을 element
var element_wrap;

function foldDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    element_wrap.style.display = 'none';
    callResize()  ;
}
//div_id:검색창 div id, zip_cd_id: 우편번호 입력 할 id
//road_addr_id: 도로명 입력할 id, gibun_addr_id: 지번 주소 입력할 id
function execDaumPostcode(div_id, zip_cd_id, road_addr_id, gibun_addr_id) {
    element_wrap = document.getElementById(div_id);
    // 현재 scroll 위치를 저장해놓는다.
    var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
    new daum.Postcode({
        oncomplete: function(data) {
            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = data.address; //data.roadAddress
            var roadAddr = data.roadAddress
            var jibunAddr = data.jibunAddress
            var extraAddr = ''; // 조합형 주소 변수
            var buildingName = data.buildingName;
            
            if(buildingName != ""){
            	buildingName = "(" +buildingName +")";
            }

            // 기본 주소가 도로명 타입일때 조합한다.
            if(data.addressType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }
			//zip_cd_id, road_addr_id, gibun_addr_id
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById(zip_cd_id).value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById(road_addr_id).value = roadAddr + buildingName;
            document.getElementById(gibun_addr_id).value = jibunAddr;

            // iframe을 넣은 element를 안보이게 한다.
            // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
            element_wrap.style.display = 'none';
            callResize()  ;

            // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
            document.body.scrollTop = currentScroll;
        },
        // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
        onresize : function(size) {
        	//다음 이미지 가리기 위한 높이 조정
            //element_wrap.style.height = (Number(size.height) - 50 )+'px';
//        	console.log(size.height);
//            element_wrap.style.height = size.height+'px';
            element_wrap.style.height = size.height +6+'px';
//            parent.resizeTopIframe(height+size.height,width);  
     	   callResize()  ;
        },
        width : '100%',
        height : '100%'
    }).embed(element_wrap);

    // iframe을 넣은 element를 보이게 한다.
    element_wrap.style.display = 'block';
//    element_wrap.style.marginLeft = '25px';
}

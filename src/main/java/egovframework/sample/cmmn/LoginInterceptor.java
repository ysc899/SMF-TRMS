
package egovframework.sample.cmmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
   // Logger logger = LogManager.getLogger();
    @Override // 이부분은 컨트롤러 타기전에
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션 객체 생성
        HttpSession session = request.getSession();  
        // 세션에 id가 null이면
    	boolean result = false;
        try {

        	System.out.println(session.getAttribute("UID")+ "  [  "+request.getRequestURI()+"  ] ");
//        	logger.debug(session.getAttribute("UID")+ "  [  "+request.getRequestURL()+"  ] ");
        	
        	if(request.getRequestURL().indexOf("loginUser.do")>-1
        			||request.getRequestURL().indexOf("trmslogin.do")>-1
        			||request.getRequestURL().indexOf("logout.do")>-1
        			||request.getRequestURL().indexOf("SMFTRMS.do")>-1			// 홍보site 에서 (신)결과조회 화면으로 이동시 login 체크
        			||request.getRequestURL().indexOf("recvImg_mclis.do")>-1 	// mCLIS에서 검사결과전송 했을때, 이미지생성 페이지를 호출하여 이미지결과를 생성
        			||request.getRequestURL().indexOf("recvImg_nmc.do")>-1		// 의료원프로그램에서 이미지생성 페이지를 호출하여 이미지결과를 생성(1)
        			||request.getRequestURL().indexOf("recvImgReportFileDown.do")>-1		// 의료원프로그램에서 이미지생성 페이지를 호출하여 이미지결과를 생성(2)
        			||request.getRequestURL().indexOf("recvImgReportOneFileDown.do")>-1		// 의료원프로그램에서 이미지생성 페이지를 호출하여 이미지결과를 생성(3)
        			//||request.getRequestURL().indexOf("rstUserList.do")>-1 	// 부하테스트 2019-03-10 , cjw
        			//||request.getRequestURL().indexOf("rstUser.do")>-1 		// 부하테스트 2019-03-10 , cjw
        			//||request.getRequestURL().indexOf("main.do")>-1			// 부하테스트 2019-03-10 , cjw
        			||request.getRequestURL().indexOf("recvImg.do")>-1
        			//모바일 어플 
        			||request.getRequestURL().indexOf("mobileMain.do")>-1  				//모바일 메인
        			||request.getRequestURL().indexOf("mobileLogin.do")>-1				//모바일 로그인 페이지
        			||request.getRequestURL().indexOf("mobileNotice.do")>-1				//모바일 공문 새소식 리스트 페이지
        			||request.getRequestURL().indexOf("mobileNoticeList.do")>-1			//모바일 공문 새소식 리스트 json
        			||request.getRequestURL().indexOf("mobileNoticeView.do")>-1			//모바일 공문 새소식 상세 페이지
        			||request.getRequestURL().indexOf("mobileNoticeDtl.do")>-1			//모바일 공문 새소식 상세 json
        			||request.getRequestURL().indexOf("mobileNoticeAttachList.do")>-1	//모바일 공문 새소식 첨부파일 json
        			||request.getRequestURL().indexOf("mobileTestItem.do")>-1			//모바일 검사항목 리스트 페이지
        			||request.getRequestURL().indexOf("mobileTestItemList.do")>-1		//모바일 검사항목 리스트 json
        			||request.getRequestURL().indexOf("mobileTestItemView.do")>-1		//모바일 검사항목 상세 페이지 
        			||request.getRequestURL().indexOf("mobileTestItemDtl.do")>-1		//모바일 검사항목 상세 json
        			||request.getRequestURL().indexOf("mobileGetCommCode.do")>-1		//모바일 검사항목 리스트 json
					//DID&블록체인(2021.09)
        			||request.getRequestURL().indexOf("requestTestResultForUserInfo.do")>-1
        			//코로나검사결과조회(2021.10)
        			// TODO : 홍재훈
        			||request.getRequestURL().indexOf("resultInqForm.do")>-1
        			||request.getRequestURL().indexOf("resultInqResult.do")>-1
        			||request.getRequestURL().indexOf("sms_reportFileDown.do")>-1
        			||request.getRequestURL().indexOf("verifyReport.do")>-1
        			// 보건소 앱에서 코로나 이미지결과조회 (만안구,강남구) - 2021.12
        			||request.getRequestURL().indexOf("bogunResultInqForm.do")>-1
        			){
        		
        		 result = true;
        	}else{
	            //세션값이 널일경우
	            if(session.getAttribute("UID") == null ){
	                //Ajax 콜인지 아닌지 판단
	                if(isAjaxRequest(request)){
	                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED); 
	                }else{ // Ajax 콜이 아닐경우 login페이지로 이동.
	                	
	                	
	                	if(request.getRequestURL().indexOf("mobile") > -1){
	                		//어플용 페이지의 경우 로그인 페이지로 이동
	                		response.sendRedirect("/mobileLogin.do");
	                	}else{
	                	
	                		response.sendRedirect("/session_out.jsp");
	                	}
	                }
	                result =  false;
	                
	            }else{
	                result =  true;
	            }
        	}
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }

       //result =  true;
        //널이 아니면 정상적으로 컨트롤러 호출
        return result;
    	
    }     
    
	private boolean isAjaxRequest(HttpServletRequest req) {
		String ajaxHeader = "AJAX";
		return req.getHeader(ajaxHeader) != null && req.getHeader(ajaxHeader).equals(Boolean.TRUE.toString());
	}
}
    

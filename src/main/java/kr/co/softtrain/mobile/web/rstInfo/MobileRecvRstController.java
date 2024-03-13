package kr.co.softtrain.mobile.web.rstInfo;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

@Controller
public class MobileRecvRstController {

	private final String PATH_PREFIX = "mobile/rstInfo/";

	/**
	 * @Method Name : goRecvRstCorona
	 * @see
	 * 
	 *      <pre>
	 * Method 설명 : OCS 결과 받기(B) 페이지 호출
	 * return_type : String
	 *      </pre>
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/mobileRecvRstCorona.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String goRecvRstCorona(HttpServletRequest request, HttpSession session) throws Exception {
		String path = PATH_PREFIX + "recvRstCorona";

		/*
		 * if(session.getAttribute("UID") != null){ //로그인 세션이 있는경우
		 * if(CommonController.AuthPage(request,session)){
		 * 
		 * //권한이 없는 경우 //위 메소드가 세션을 제거 함으로 다시 자동 로그인 프로세스를 실행 path =
		 * "redirect:/mobileLogin.do?pmsg="+ URLEncoder.encode("조회 권한이 없습니다.",
		 * "UTF-8"); }
		 * 
		 * }else{ //세션이 없는 경우 path = "redirect:/mobileLogin.do?pmsg="+
		 * URLEncoder.encode("로그인이 필요 합니다.", "UTF-8");
		 * 
		 * }
		 */

		if (session.getAttribute("UID") == null) {
			// 세션이 없는 경우
			path = "redirect:/mobileLogin.do?pmsg=" + URLEncoder.encode("로그인이 필요 합니다.", "UTF-8");
		}

		return path;
	}

}

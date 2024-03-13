package kr.co.softtrain.mobile.web.main;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;
import kr.co.softtrain.mobile.service.MobileService;

@Controller
public class MobileMainController {
	
	
	private final String PATH_PREFIX = "mobile/main/";
	
	@Resource(name="MobileService")
	private MobileService service;
	
	
	@RequestMapping("/mobileMain.do")
	public String mobileMain(Model model, @RequestParam(name="pmsg", defaultValue="", required=false) String printMsg){
		
		model.addAttribute("pmsg", printMsg);
		
		return PATH_PREFIX + "main";
	}
	
	@RequestMapping("/mobileLogin.do")
	public String mobileLogin(HttpServletRequest request, HttpSession session,
			Model model, @RequestParam(name="pmsg", defaultValue="", required=false) String printMsg
			) throws Exception{
		
		String path = PATH_PREFIX + "login";
		
		model.addAttribute("pmsg", printMsg);
		
		if(session.getAttribute("UID") != null){
			path = "redirect:/mobileMain.do";
		}else{
			if("".equals(Utils.isNull(session.getAttribute("__rsaPrivateKey__"))))
			{
				service.rsaKey(request,session);
			}
		}
		return path;
	}
	
	@RequestMapping(value = "/mobileMyPage.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String mobileMyPage(HttpServletRequest request, HttpSession session) throws Exception{
		String path = PATH_PREFIX+"mypage";
		
		if(session.getAttribute("UID") == null){
			//세션이 없는 경우
			path = "redirect:/mobileLogin.do";
			
		}
		
		return path;
	}
	
	/**
	 * @Method Name	: 모바일용 로그아웃
	 * @see
	 * <pre>
	 * Method 설명 :   로그아웃 
	 * 			   - 사용자 세션 삭제
	 * return_type : 모바일 메인 페이지로 리다이렉트
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/mobileLogout.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goLogout(HttpServletRequest request, HttpSession session){

		if(session != null){
			if(session.getAttribute("UID") != null){
				session.invalidate();
				session = request.getSession(true);
			}
    	}

		return "redirect:/mobileMain.do";
	}
	
}

package kr.co.softtrain.mobile.web.rstMini;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MobileRstUserMiniController {
	
	private final String PATH_PREFIX = "mobile/rstMini/";
	
	@RequestMapping(value = "/mobileRstUserTableMini01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstUserTableMini01(HttpServletRequest request, HttpSession session) throws Exception{
		
		String path = PATH_PREFIX + "rstUserTableMini01";
		
		if(session.getAttribute("UID") == null){
			//세션이 없는 경우
			path = "redirect:/mobileLogin.do?pmsg=로그인이 필요 합니다.";
		}
		
		return path;
	}
	
	@RequestMapping(value = "/mobileRstUserTableMini02.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstUserTableMini02(HttpServletRequest request, HttpSession session, Model model) throws Exception{
		
		String path = PATH_PREFIX + "rstUserTableMini02";
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		getParameterModel(request, model);	// 데이터 Parameter 상태로 변경
		return path;
	}
	
	public static Model getParameterModel(HttpServletRequest request, Model model) {
		//Model model = new ModelAndView();
		//ModelAndView model = new ModelAndView();
		// 파라미터 이름
		Enumeration<String> paramNames = request.getParameterNames();
		// 저장할 맵
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 맵에 저장
		while(paramNames.hasMoreElements()) {
			String name	= paramNames.nextElement().toString();
			Object value	= request.getParameter(name);
			paramMap.put(name, value.toString().trim());
			//model.addAttribute(name, value.toString().trim());
			model.addAttribute(name, value.toString().trim());
		}
		// 결과반환
		return model;
	}

}


package kr.co.softtrain.mobile.web.ocs;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.softtrain.resultImg.web.QrCodeUtils;

@Controller
public class MobileTestItemController {
	
	private final String PATH_PREFIX = "mobile/ocs/";
	
    @Resource(name = "commonService")
    private commonService commonService;

	@RequestMapping("/mobileTestItem.do")
	public String mobileNotice(){
		
		return PATH_PREFIX + "testItem";
	}
	
	/**
	 * @Method Name	: getTestItemList
	 * @see
	 * <pre>
	 * Method 설명 : 상세 리스트 호출
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/mobileTestItemList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getTestItemList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	Object[] args = new Object[0];
	  	
	  	if(param.containsKey("I_ISC")){
	  		args =  request.getParameterValues("I_ISC");
	  	}
	  	
	  	String tt = "";
	  	if(args.length>0){
	  		for (Object object : args) {
  				tt += "|"+object+"";
	  		}
	  		tt += "|";
	  	}
	  	param.put("I_ISC", tt);
	  	
	  	param.put("I_COR", "NML");
	  	param.put("I_UID", "mobile");
	  	
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("testItemList", param);
	  	
//	  	for (int j = 0; j <= resultList.size(); j++) {
//	  		System.out.println("################"+ resultList.get(j));	
//		}
	  	
	  	param.put("resultList", resultList);
	  
	  	return param;
	}
	
	
	@RequestMapping("/mobileTestItemView.do")
	public String mobileNoticeView(@RequestParam( name ="gcd", required=true) int gcd, Model model,HttpSession session){
		
		model.addAttribute("gcd", gcd);
		
		if(session.getAttribute("UID") == null || session.getAttribute("UID") == ""){
			model.addAttribute("loginYn", false);
			System.out.println("비로그인 유저"+ session.getAttribute("UID"));
		}else{
			model.addAttribute("loginYn", true);
			System.out.println("로그인 유저"+ session.getAttribute("UID"));	
		}
		
		return PATH_PREFIX + "testItemView";
	}
	

	/**
	 * @Method Name	: testItemDtl
	 * @see
	 * <pre>
	 * Method 설명 : 검사항목 조회 상세
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/mobileTestItemDtl.do", method = {RequestMethod.GET, RequestMethod.POST})
	//	  , produces="application/json;charset=UTF-8")
	public Object testItemDtl(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	param.put("I_COR", "NML");
	  	param.put("I_UID", "mobile");
	  	
	  	// 검사코드 ex) 00659 가 659 로 넘어가 앞에 빈자릿수 만큼 0 을 채워준다
	  	param.put("I_GCD",String.format("%05d", Integer.parseInt((String) param.get("I_GCD"))));

	  	List<Map<String, Object>> resultList = commonService.commonList("testItemDtl", param);
		//url 을 qr코드로 변환
		if(!"".equals(String.valueOf(resultList.get(0).get("T001URL")))){
			resultList.get(0).put("T001URLQR", QrCodeUtils.generateQRCodeImage(String.valueOf(resultList.get(0).get("T001URL")), 350, 350));
		}

	  	param.put("resultList", resultList);
	  	
	  	return param;
	}
}

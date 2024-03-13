package kr.co.softtrain.mobile.web.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;

@Controller
public class MobileNoticeController {
	
	private final String PATH_PREFIX = "mobile/notice/";
	
    @Resource(name = "commonService")
    private commonService commonService;

	@RequestMapping("/mobileNotice.do")
	public String mobileNotice(){
		
		return PATH_PREFIX + "notice";
	}
	
	/**
     * @Method Name : noticeList
     * @see
     * <pre>
     * Method 설명 : 
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/mobileNoticeList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_FWDT", Utils.Null2Zero(request.getParameter("I_FWDT").replaceAll("-", "")));
        param.put("I_TWDT", Utils.Null2Zero(request.getParameter("I_TWDT").replaceAll("-", "")));
        
        List<Map<String, Object>> noticeList = commonService.commonList("noticeList",param);
      
        param.put("resultList", noticeList);
        
        return param;
    }
	
	@RequestMapping("/mobileNoticeView.do")
	public String mobileNoticeView(@RequestParam( name ="seq", required=true) int seq, Model model){
		
		model.addAttribute("seq", seq);
		
		return PATH_PREFIX + "noticeView";
	}
	
	@ResponseBody
    @RequestMapping(value="/mobileNoticeDtl.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeDtl(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_SEQ",request.getParameter("I_SEQ"));
        
//        logger.debug(param);
        List<Map<String, Object>> noticeDtl = commonService.commonList("noticeDtl",param);
        param.put("result1",noticeDtl);
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value="/mobileNoticeAttachList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeAttachList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> noticeAttachList = commonService.commonList("noticeAttachList",param);
        
        param.put("resultList",noticeAttachList);
        
        return param;
    }
	
	
}

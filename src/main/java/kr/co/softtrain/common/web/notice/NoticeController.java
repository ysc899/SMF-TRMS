package kr.co.softtrain.common.web.notice;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;


/**
 * <pre>
 * kr.co.softtrain.common.web.notice
 * NoticeController.java
 * </pre>
 * @title :  
 * @author : jsyoo
 * @since : 2018. 11. 30.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 30.		jsyoo 			최초생성
 * 
 * </pre>
 */
@Controller
public class NoticeController {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    @RequestMapping(value = "/notice.do")
    public String goNotice(HttpServletRequest request,HttpSession session)throws Exception{
    	String str = "notice/notice";
    	if(CommonController.AuthPage(request,session)){
    		str = "index";
    	}
    	return str;
    }
    
    @RequestMapping(value = "/notice01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goNoticeOpen01(HttpServletRequest request, HttpSession session){
		String str = "notice/notice01";
		return str;
	}
    
    @Autowired  
    private MessageSource messageSource;
    
   
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
    @RequestMapping(value="/noticeList.do"
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
    
    @ResponseBody
    @RequestMapping(value="/noticeRead.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeReadSave(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        
        commonService.commonInsert("noticeReadSave", param);
        
        return param;
    }
    
    
 
}


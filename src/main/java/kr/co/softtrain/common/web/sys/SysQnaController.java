package kr.co.softtrain.common.web.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.sys
 * SysQnaController.java
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
public class SysQnaController {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    @RequestMapping(value = "/sysQna.do")
    public String menuTree(HttpServletRequest request,HttpSession session) throws Exception{
    	String str = "sys/sysQna";
    	if(CommonController.AuthPage(request,session)){
    		str = "index";
    	}
    	return str;
    }
    
    @Autowired  
    private MessageSource messageSource;
    
   
    /**
     * @Method Name : qnaList
     * @see
     * <pre>
     * Method 설명 : 문의사항 관리자 조회 
     * return_type : List<Map<String,Object>>
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/sysQnaList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object sysQnaList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_FWDT", Utils.Null2Zero(request.getParameter("I_FWDT").replaceAll("-", "")));
        param.put("I_TWDT", Utils.Null2Zero(request.getParameter("I_TWDT").replaceAll("-", "")));
        
        List<Map<String, Object>> sysQnaList = commonService.commonList("sysQnaList",param);
        
        param.put("resultList",sysQnaList);
        
        return param;
    }
}


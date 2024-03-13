package kr.co.softtrain.mobile.web.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;
import kr.co.softtrain.mobile.service.MobileService;

@Controller
public class MobileSysCodeController {

    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    @Resource(name= "MobileService")
    private MobileService mobileService;
    
    /**
     * @Method Name : getCommCode
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
	@RequestMapping(value = "/mobileGetCommCode.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object getCommCode(HttpServletRequest request, HttpSession session) throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
	  	param.put("I_COR", "NML");
	  	param.put("I_UID", "mobile");
	  	param.put("I_IP", mobileService.getIP(request));
    	
    	List<Map<String, Object>> resultList = commonService.commonList("getCommCode", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    	
    }
}

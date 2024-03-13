package kr.co.softtrain.common.web.rst;

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
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.rst
 * RstBeforeController.java
 * </pre>
 * @title : 결과관리 > 이전결과
 * @author : OJS
 * @since : 2018. 12. 4.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 3.		OJS  				최초생성
 * 
 * </pre>
 */
@Controller
public class RstBeforeController {

	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : gorstPre
	 * @see
	 * <pre>
	 * Method 설명 : hospPat.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/rstPre.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gorstPre(HttpServletRequest request, HttpSession session) throws Exception{
	   String str = "rst/rstPre";
	   if(CommonController.AuthPage(request,session)){
		      str = "index";
	   }
		return str;
	}
	
	
	/**
	 * @Method Name : rstPreList
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
	@RequestMapping(value="/rstPreList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object rstPreList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	Object[] args = new Object[0];
	  	if(param.containsKey("I_RGN")){
	  		args =  request.getParameterValues("I_RGN");
	  	}

	  	param.put("I_RGN1", "");
	  	param.put("I_RGN2", "");
	  	param.put("I_RGN3", "");
	  	if(args.length>0){
	  		for (Object object : args) {
	  			param.put("I_RGN"+object, "Y");
	  		}
	  	}
		param.put("I_DGN", Utils.isNull(param.get("I_DGN")));
		param.put("I_HOS", Utils.isNull(param.get("I_HOS")));		
		param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
		param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
		param.put("I_BDT", Utils.isNull(param.get("I_BDT")));
		param.put("I_CHN", Utils.isNull(param.get("I_CHN")));
		param.put("I_NAM", Utils.isNull(param.get("I_NAM")));
	  	//param.put("I_RGN1", Utils.isNull(param.get("I_RGN1")));
	  	//param.put("I_RGN2", Utils.isNull(param.get("I_RGN2")));
	  	//param.put("I_RGN3", Utils.isNull(param.get("I_RGN3")));
		param.put("I_HAK", Utils.isNull(param.get("I_HAK")));
		param.put("I_STS", Utils.isNull(param.get("I_STS")));
		param.put("I_ETC", Utils.isNull(param.get("I_ETC")));
		param.put("I_GAD", Utils.isNull(param.get("I_GAD")));
		
		//logger.debug("--test--" + param);
	  	List<Map<String, Object>> resultList = commonService.commonList("rstPreList", param);
	  	
	  	//logger.debug("--test2--" + param.get("O_DAT"));
	  	param.put("resultList", resultList);
	  	
		return param;
	}
}


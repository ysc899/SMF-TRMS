package kr.co.softtrain.common.web.test;

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


/**
 * <pre>
 * kr.co.softtrain.common.web.test
 * TestController.java
 * </pre>
 * @title :  리포트 테스트
 * @author : OJS
 * @since : 2018. 11. 27.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 27.		수정자명			최초생성
 * 
 * </pre>
 */
@Controller
public class TestController {

	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;
	
	/**
	 * @Method Name : goReportTest
	 * @see
	 * <pre>
	 * Method 설명 : reportTest.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/reportTest.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goReportTest(HttpServletRequest request, HttpSession session){
		String str = "test/reportTest";
		
		return str;
	}
	
	
	/**
	 * @Method Name : goTest01
	 * @see
	 * <pre>
	 * Method 설명 : test01.jsp 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/test01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goTest01(HttpServletRequest request, HttpSession session){
		String str = "report/test01";
		
		return str;
	}
}

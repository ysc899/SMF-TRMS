package kr.co.softtrain.common.web.realtime;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public class RealtimeController {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    
    /**
	 * @Method Name	: goRealtime
	 * @see
	 * <pre>
	 * Method 설명 : realtimeDash 화면 호출
	 * return_type : String
	 * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/realtimeDash.do")
    public String goRealtime(HttpServletRequest request,HttpSession session)throws Exception{
    	String str = "realtime/realtimeDash";
    	if(CommonController.AuthPage(request,session)){
    		str = "index";
    	}
    	return str;
    }
    
    /**
	 * @Method Name	: goRealtime
	 * @see
	 * <pre>
	 * Method 설명 : realtimeA 화면 호출
	 * return_type : String
	 * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/realtimeA.do")
    public String goRealtimeA(HttpServletRequest request,HttpSession session)throws Exception{
    	String str = "realtime/realtimeA";
    	if(CommonController.AuthPage(request,session)){
    		str = "index";
    	}
    	return str;
    }
    
    /**
	 * @Method Name	: goRealtime
	 * @see
	 * <pre>
	 * Method 설명 : realtimeB 화면 호출
	 * return_type : String
	 * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/realtimeB.do")
    public String goRealtimeB(HttpServletRequest request,HttpSession session)throws Exception{
    	String str = "realtime/realtimeB";
    	if(CommonController.AuthPage(request,session)){
    		str = "index";
    	}
    	return str;
    }
    
    /**
	 * @Method Name	: goRealtime
	 * @see
	 * <pre>
	 * Method 설명 : realtimeC 화면 호출
	 * return_type : String
	 * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/realtimeC.do")
    public String goRealtimeC(HttpServletRequest request,HttpSession session)throws Exception{
    	String str = "realtime/realtimeC";
    	if(CommonController.AuthPage(request,session)){
    		str = "index";
    	}
    	return str;
    }
	
    
    /**
	 * @Method Name	: goRealtimePopup
	 * @see
	 * <pre>
	 * Method 설명 : realtimePopup 팝업 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/realtimePopup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRealtimePopup(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "realtime/realtimePopup";
		return str;
	}
	
	@Autowired  
    private MessageSource messageSource;
	
    /**
     * @Method Name : 실시간 현황(A)
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
    @RequestMapping(value="/realtimeAList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object realtimeAList(HttpServletRequest request, HttpSession session) throws Exception{
        
        Map<String, Object> param_all = new HashMap<String, Object>();
        
        // ============ 당일
        Map<String, Object> param_common = new HashMap<String, Object>();
        param_common = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String toDay = sdf.format(c1.getTime());
        
        // ============ 전일
        Map<String, Object> param_common_2 = new HashMap<String, Object>();
        param_common_2 = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        Date dDate = new Date();
        dDate = new Date(dDate.getTime()+(1000*60*60*24*-1));
        /*SimpleDateFormat dSdf = new SimpleDateFormat("yyyy/MM/dd HH", Locale.KOREA);*/
        SimpleDateFormat dSdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String toDay_2 = dSdf.format(dDate);
        
        param_common.put("I_COR", "NML");
        param_common.put("I_FDT", toDay);
        param_common.put("I_TDT", toDay);
        //param_common.put("I_FDT", "20210308");
        //param_common.put("I_TDT", "20210308");
        
        param_common_2.put("I_COR", "NML");
        param_common_2.put("I_FDT", toDay_2);
        param_common_2.put("I_TDT", toDay_2);
        
        /*************** 01.접수현황에 따른 검체별 개수 (조회일 기준) ***************/
        List<Map<String, Object>> realtimeList1 = commonService.commonList("realtimeList1",param_common);		// 당일
        List<Map<String, Object>> realtimeList1_1 = commonService.commonList("realtimeList1",param_common_2);	// 전일
        
        /*************** 03.본원 TS SYSTEM 가동률 (실시간현황) ***************/
        List<Map<String, Object>> realtimeList3 = commonService.commonList("realtimeList3",param_common);		// 당일

        /*************** 05.분야별 검사 실시 현황 (전일접수) ***************/
        List<Map<String, Object>> realtimeList5 = commonService.commonList("realtimeList5",param_common_2);		// 전일
        
        /*************** 06.근무 현황 (조회일 기준) ***************/
        List<Map<String, Object>> realtimeList6 = commonService.commonList("realtimeList6",param_common);		// 당일
        
        param_all.put("resultList1"  , realtimeList1);
        param_all.put("resultList1_1", realtimeList1_1);
        param_all.put("resultList3"  , realtimeList3);
        param_all.put("resultList5"  , realtimeList5);
        param_all.put("resultList6"  , realtimeList6);
        
        return param_all;
    }
    
    /**
     * @Method Name : 실시간 현황(B)
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
    @RequestMapping(value="/realtimeBList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object realtimeBList(HttpServletRequest request, HttpSession session) throws Exception{
        
        Map<String, Object> param_all = new HashMap<String, Object>();
        
        // ============ 당일
        Map<String, Object> param_common = new HashMap<String, Object>();
        param_common = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String toDay = sdf.format(c1.getTime());
        
        // ============ 전일
        Map<String, Object> param_common_2 = new HashMap<String, Object>();
        param_common_2 = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        Date dDate = new Date();
        dDate = new Date(dDate.getTime()+(1000*60*60*24*-1));
        /*SimpleDateFormat dSdf = new SimpleDateFormat("yyyy/MM/dd HH", Locale.KOREA);*/
        SimpleDateFormat dSdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String toDay_2 = dSdf.format(dDate);
        
        param_common.put("I_COR", "NML");
        param_common.put("I_FDT", toDay);
        param_common.put("I_TDT", toDay);
        //param_common.put("I_FDT", "20210308");
        //param_common.put("I_TDT", "20210308");
        
        param_common_2.put("I_COR", "NML");
        param_common_2.put("I_FDT", toDay_2);
        param_common_2.put("I_TDT", toDay_2);
        
        /*************** 08.야간검사 결과보고율 (전일 접수) ***************/
        List<Map<String, Object>> realtimeList8 = commonService.commonList("realtimeList8",param_common_2);	// 전일
        
        /*************** 02.검체오류 현황 (전일 접수) ***************/
        List<Map<String, Object>> realtimeList2 = commonService.commonList("realtimeList2",param_common);	// 당일(전일자 데이터를 당일에 입력하기 때문)
        
        /*************** 07.양성률 (전일 보고) ***************/
        List<Map<String, Object>> realtimeList7 = commonService.commonList("realtimeList7",param_common_2);	// 전일
        
        /*************** 04.주단위 건수 추이 (최근 일주일) ***************/
        List<Map<String, Object>> realtimeList4 = commonService.commonList("realtimeList4",param_common);	// 당일
        
        param_all.put("resultList8", realtimeList8);
        param_all.put("resultList2", realtimeList2);
        param_all.put("resultList7", realtimeList7);
        param_all.put("resultList4", realtimeList4);
        
        return param_all;
    }
    
    
    /**
     * @Method Name : 실시간 현황(C)
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
    @RequestMapping(value="/realtimeCList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object realtimeCList(HttpServletRequest request, HttpSession session) throws Exception{
        
        Map<String, Object> param_all = new HashMap<String, Object>();
        
        // ============ 당일
        Map<String, Object> param_common = new HashMap<String, Object>();
        param_common = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String toDay = sdf.format(c1.getTime());
        
        // ============ 전일
        Map<String, Object> param_common_2 = new HashMap<String, Object>();
        param_common_2 = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        Date dDate = new Date();
        dDate = new Date(dDate.getTime()+(1000*60*60*24*-1));
        /*SimpleDateFormat dSdf = new SimpleDateFormat("yyyy/MM/dd HH", Locale.KOREA);*/
        SimpleDateFormat dSdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String toDay_2 = dSdf.format(dDate);
        
        param_common.put("I_COR", "NML");
        param_common.put("I_FDT", toDay);
        param_common.put("I_TDT", toDay);
        //param_common.put("I_FDT", "20210308");
        //param_common.put("I_TDT", "20210308");
        
        param_common_2.put("I_COR", "NML");
        param_common_2.put("I_FDT", toDay_2);
        param_common_2.put("I_TDT", toDay_2);
        
        /*************** 09.코로나 검사 실시 현황 (조회일 기준) ***************/
        List<Map<String, Object>> realtimeList9 = commonService.commonList("realtimeList9",param_common);		// 당일
        List<Map<String, Object>> realtimeList9_1 = commonService.commonList("realtimeList9",param_common_2);	// 전일
        
        /*************** 10.코로나 주단위 건수 추이 ***************/
        List<Map<String, Object>> realtimeList10 = commonService.commonList("realtimeList10",param_common);		// 당일
        
        /*************** 11.코로나 양성률 (전일 보고) ***************/
        List<Map<String, Object>> realtimeList11 = commonService.commonList("realtimeList11",param_common_2);	// 전일
        
        param_all.put("resultList9"   , realtimeList9);
        param_all.put("resultList9_1" , realtimeList9_1);
        param_all.put("resultList10"  , realtimeList10);
        param_all.put("resultList11"  , realtimeList11);
        
        return param_all;
    }
}



package kr.co.softtrain.common.web.banking.controller;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.did.util.DidConstants;
import kr.co.softtrain.common.web.did.util.DidUtility;
import kr.co.softtrain.common.web.did.vo.PatientData;
import kr.co.softtrain.common.web.did.vo.TestResultData;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.common.web.did.vo.RequestUserData;
import kr.co.softtrain.custom.util.Utils;

@Controller
public class BankingController {
	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;

	@RequestMapping(value = "/bankingStat.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String bankingStat(HttpServletRequest request, HttpSession session) throws Exception {
		String path = "banking/bankingStat";
		if(CommonController.AuthPage(request,session)){
			path = "index";
		}

		return path;
	}
	
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
	@RequestMapping(value = "/getBankingCmCodeSelect.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
	  //, produces="application/json;charset=UTF-8")
	public Object getBankingCmCodeSelect(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
		
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("getBankingCmCodeSelect", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    	
    }
    
    
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
    @RequestMapping(value = "/getBankingSimpleList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    //, produces="application/json;charset=UTF-8")
    public Object getBankingSimpleList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("getBankingSimpleList", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    	
    }
    
    
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
    @RequestMapping(value = "/getBankingStatisticList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    //, produces="application/json;charset=UTF-8")
    public Object getBankingStatisticList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("P_COMPANY", "NML");
    	
    	if("".equals(param.get("P_GCDGRP"))){ param.put("P_GCDGRP", "*"); }
    	if("".equals(param.get("P_GCD"))){ param.put("P_GCD", "*"); }
    	if("".equals(param.get("P_ACD"))){ param.put("P_ACD", "*"); }
    	if("".equals(param.get("P_SPECIMEN_ID"))){ param.put("P_SPECIMEN_ID", "*"); }
    	
    	System.out.println("param.get(dataOnly) >> "+param.get("dataOnly"));
    	
    	//뱅킹통계 (합계)
    	List<Map<String, Object>> resultListSum = commonService.commonList("getBankingStatisticListSum", param);

    	//뱅킹통계
    	List<Map<String, Object>> resultList = commonService.commonList("getBankingStatisticList", param);
    	
    	List<Map<String, Object>> resultList_final = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
    	
		resultListSum.addAll(resultList);
		
    	if(param.get("dataOnly") == null){	//dataOnly 선택하지 않은 경우
    		param.put("resultList", resultListSum);
    	}else{	//dataOnly 선택한 경우
        	 
        	for(int i=0;resultListSum.size()>i;i++){
        		map = new HashMap<String, Object>();
        		
        		if(!"".equals(resultListSum.get(i).get("POSI").toString().trim()) &&
        			!"".equals(resultListSum.get(i).get("NEGA").toString().trim()) &&
        			!"".equals(resultListSum.get(i).get("POSI_NUCL").toString().trim()) &&
        			!"".equals(resultListSum.get(i).get("NEGA_NUCL").toString().trim()) &&
        			(!"0".equals(resultListSum.get(i).get("POSI").toString().trim()) ||
        			!"0".equals(resultListSum.get(i).get("NEGA").toString().trim()) ||
        			!"0".equals(resultListSum.get(i).get("POSI_NUCL").toString().trim()) ||
        			!"0".equals(resultListSum.get(i).get("NEGA_NUCL").toString().trim()))
        		){      		
            		
            		map.put("GNMGRP",resultListSum.get(i).get("GNMGRP").toString().trim());
            		map.put("GNM",resultListSum.get(i).get("GNM").toString().trim());
            		map.put("ANM",resultListSum.get(i).get("ANM").toString().trim());
            		map.put("SPCNM",resultListSum.get(i).get("SPCNM").toString().trim());
            		
            		map.put("POSI",resultListSum.get(i).get("POSI").toString().trim());
            		map.put("NEGA",resultListSum.get(i).get("NEGA").toString().trim());
            		map.put("POSI_NUCL",resultListSum.get(i).get("POSI_NUCL").toString().trim());
            		map.put("NEGA_NUCL",resultListSum.get(i).get("NEGA_NUCL").toString().trim());
            		
            		resultList_final.add(map);
        		}
        	}
    		
    		param.put("resultList", resultList_final);
    	}
    	
    	return param;
    	
    }
}
/*
 * Copyright 2014 MOSPA(Ministry of Security and Public Administration).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.co.softtrain.common.web.rst;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import com.fasterxml.jackson.core.JsonParser;

import antlr.debug.NewLineListener;
//import antlr.Utils;
import kr.co.softtrain.custom.util.*;
import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;


/**
 * <pre>
 * kr.co.softtrain.common.web.rst
 * SysMenuController.java
 * </pre>
 * @title :  
 * @author : twbae
 * @since : 2018. 12. 12.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 12.		twbae	 			최초생성
 * 
 * </pre>
 */
@Controller
public class RstUserController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	@RequestMapping(value = "/rstUser.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String rstUser(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rst/rstUser";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	@Autowired  
    private MessageSource messageSource;

	
	@RequestMapping(value = "/testReq01Popup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String rstUserPopup1(HttpServletRequest request, HttpSession session){
		String str = "rst/testReq01Popup";
		return str;
	}
	
	@RequestMapping(value = "/addReq01Popup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String addReq01Popup(HttpServletRequest request, HttpSession session){
		String str = "rst/addReq01Popup";
		return str;
	}
	
	@RequestMapping(value = "/orderReqPopup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String orderReqPopup(HttpServletRequest request, HttpSession session){
		String str = "rst/rstUser02";
		return str;
	}
	
	@RequestMapping(value = "/smsReqPopup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String smsReqPopup(HttpServletRequest request, HttpSession session){
		String str = "rst/rstUser03";
		return str;
	}
	
	@RequestMapping(value = "/smsMsgPopup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String smsMsgPopup(HttpServletRequest request, HttpSession session){
		String str = "rst/rstUser03_message";
		return str;
	}
	
	@RequestMapping(value = "/rstUserTable.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstUserTable(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rst/rstUserTable";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * <pre>
	 * Method 설명 : 수진자별 결과조회 - 영문
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rstUserTableEng.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gorstUserEng(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rst/rstUserTableEng";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name : rstUserList
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
	@RequestMapping(value = "/rstUserList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
    	// ************ 5년전 날짜 - start
    	Calendar beforeDateTime = Calendar.getInstance();
    	beforeDateTime.add(Calendar.YEAR , -11);
    	String beforeDate = new java.text.SimpleDateFormat("yyyyMMdd").format(beforeDateTime.getTime());
    	
    	if(param.get("I_FDT") == "0"){
    		//param.put("I_FDT", "00000000"); 
    		param.put("I_FDT", beforeDate);
    	}
    	// ************ 5년전 날짜 - end
    	
    	// ************ 현재날짜 - start
    	Calendar nowDateTime = Calendar.getInstance();
    	nowDateTime.add(Calendar.YEAR , 9);
    	nowDateTime.add(Calendar.MONTH , 0);
    	nowDateTime.add(Calendar.DATE , 0);
    	/*nowDateTime.clear();
    	nowDateTime.set(2029,Calendar.MONTH,Calendar.DATE);*/
    	
    	String nowDate = new java.text.SimpleDateFormat("yyyyMMdd").format(nowDateTime.getTime());
    	
    	if(param.get("I_TDT") == "0"){
    		//param.put("I_TDT", "99999999"); 
    		param.put("I_TDT", nowDate); 
    	}
    	// ************ 현재날짜 - end
    	
    	//param.put("I_PECF", Utils.Null2Blank(request.getParameter("I_PECF")));
        
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
	  	
    	/*
        param.put("I_RGN1", Utils.Null2Blank(request.getParameter("I_RGN1")));
    	param.put("I_RGN2", Utils.Null2Blank(request.getParameter("I_RGN2")));
    	param.put("I_RGN3", Utils.Null2Blank(request.getParameter("I_RGN3")));
    	*/
    	param.put("I_PNN", Utils.isNull(param.get("I_PNN")));
    	param.put("I_ICNT", Utils.isNull(request.getParameter("I_ICNT")));
		
		//param.put("I_ICNT", "40");            //읽을갯수
    	//param.put("I_PNN", "C");			  //이전다음
    	//param.put("I_DAT", "0");            
    	//param.put("I_JNO", "0");
    	
    	// 2021.02.10 추가 (검색조건추가)
    	param.put("I_CORONA", Utils.isNull(request.getParameter("I_CORONA")));
    	
    	List<Map<String, Object>> resultList;
    	
    	// IDR 유승현 날짜변경 처리부분 제거
    	resultList = commonService.commonList("rstUserList", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/rstUserDtl.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserDtl(HttpServletRequest request, HttpSession session) throws Exception {
		
		Map<String, Object> param_all = new HashMap<String, Object>();
		
		//환자정보
		Map<String, Object> param_rstUserDtl = new HashMap<String, Object>();
    	param_rstUserDtl = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	 Object[] args = new Object[0];
 	  	if(param_rstUserDtl.containsKey("I_RGN")){
 	  		args =  request.getParameterValues("I_RGN");
 	  	}

 	  	param_rstUserDtl.put("I_RGN1", "");
 	  	param_rstUserDtl.put("I_RGN2", "");
 	  	param_rstUserDtl.put("I_RGN3", "");
 	  	if(args.length>0){
 	  		for (Object object : args) {
 	  			param_rstUserDtl.put("I_RGN"+object, "Y");
 	  		}
 	  	}
 	  	
    	/*
    	param_rstUserDtl.put("I_RGN1", Utils.Null2Blank(request.getParameter("I_RGN1")));
    	param_rstUserDtl.put("I_RGN2", Utils.Null2Blank(request.getParameter("I_RGN2")));
    	param_rstUserDtl.put("I_RGN3", Utils.Null2Blank(request.getParameter("I_RGN3")));
    	*/
    	param_rstUserDtl.put("I_HAK",  Utils.Null2Blank(request.getParameter("I_HAK")));
    	param_rstUserDtl.put("I_SAB",  Utils.Null2Blank(request.getParameter("I_SAB")));
    	List<Map<String, Object>> rstUserDtl = commonService.commonList("rstUserDtl", param_rstUserDtl);
    	
    	param_all.put("rstUserDtl", rstUserDtl);
    	param_all.put("param_rstUserDtl", param_rstUserDtl);
    	
    	//수진자 항목 리스트
    	Map<String, Object> param_rstUserDtl2 = new HashMap<String, Object>();
    	
    	param_rstUserDtl2 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserDtl2.put("I_RGN1", "");
    	param_rstUserDtl2.put("I_RGN2", "");
    	param_rstUserDtl2.put("I_RGN3", "");
  	  	if(args.length>0){
  	  		for (Object object : args) {
  	  			param_rstUserDtl2.put("I_RGN"+object, "Y");
  	  		}
  	  	}
    	
  	  	/*
    	param_rstUserDtl2.put("I_RGN1", Utils.Null2Blank(request.getParameter("I_RGN1")));
    	param_rstUserDtl2.put("I_RGN2", Utils.Null2Blank(request.getParameter("I_RGN2")));
    	param_rstUserDtl2.put("I_RGN3", Utils.Null2Blank(request.getParameter("I_RGN3")));
    	*/
    	param_rstUserDtl2.put("I_HAK",  Utils.Null2Blank(request.getParameter("I_HAK")));
    	param_rstUserDtl2.put("I_SAB",  Utils.Null2Blank(request.getParameter("I_SAB")));
    	
    	param_rstUserDtl2.put("I_GCD", "");
    	param_rstUserDtl2.put("I_ACD", "");
    	
    	param_rstUserDtl2.put("I_ICNT", "1000");            //읽을갯수
    	param_rstUserDtl2.put("I_PNN", "C");			  //이전다음
    	
    	List<Map<String, Object>> rstUserDtl2 = commonService.commonList("rstUserDtl2", param_rstUserDtl2);
    	
    	param_all.put("rstUserDtl2", rstUserDtl2);
    	param_all.put("param_rstUserDtl2", param_rstUserDtl2);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 1) 기형아 검사(Prenatal Screening Test)
		//****************************************************************************************
    	//****************************************************************************************

    	Map<String, Object> param_rstUserMw109rms = new HashMap<String, Object>();
    	param_rstUserMw109rms = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw109rms = commonService.commonList("rstUserMw109rms", param_rstUserMw109rms);
    	
    	param_all.put("rstUserMw109rms", rstUserMw109rms);
    	param_all.put("param_rstUserMw109rms", param_rstUserMw109rms);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 2) 혈종검사소견 D폼 / N폼에 따라 해당하는 데이터 가져오기 (구폼 = D폼 / 신폼 = N폼)
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw108rmA = new HashMap<String, Object>();
    	param_rstUserMw108rmA = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	param_rstUserMw108rmA.put("I_RBN", "A");
    	
    	List<Map<String, Object>> rstUserMw108rmA = commonService.commonList("SEL06_RESULTMGMTAI_SG680D_SP", param_rstUserMw108rmA);
    	param_all.put("rstUserMw108rmA", rstUserMw108rmA);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 3) 혈종검사소견 BOB폼 / P폼에 따라 해당하는 데이터 가져오기
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw108rmB = new HashMap<String, Object>();
    	param_rstUserMw108rmB = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	param_rstUserMw108rmB.put("I_RBN", "B");
    	
    	List<Map<String, Object>> rstUserMw108rmB = commonService.commonList("SEL06_RESULTMGMTAI_SG680D_SP", param_rstUserMw108rmB);
    	param_all.put("rstUserMw108rmB", rstUserMw108rmB);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 4) microbio
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw118rms1 = new HashMap<String, Object>();
    	param_rstUserMw118rms1 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw118rms1 = commonService.commonList("rstUserMw118rms1", param_rstUserMw118rms1);

		// 2020.11.24 - RPG 에서 return 되는 값중에 '' 문자가 포하묀 경우로 인해 해당 문자를 replace 되도록 처리함. (병원,21273 요청)
    	if (rstUserMw118rms1 != null && !rstUserMw118rms1.isEmpty()){
        	if(rstUserMw118rms1.get(0).get("CRST").toString().indexOf("")>0){
        		rstUserMw118rms1.get(0).put("CRST", rstUserMw118rms1.get(0).get("CRST").toString().replace("", ""));
        	}
    	}
    	
    	param_all.put("rstUserMw118rms1", rstUserMw118rms1);
    	param_all.put("param_rstUserMw118rms1", param_rstUserMw118rms1);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 5) LmbYN 20140502 이전 / 20140502 이후
		//****************************************************************************************
    	//****************************************************************************************
    	
    	//LmbYN 20140502 이전
    	Map<String, Object> param_rstUserMw106rm3 = new HashMap<String, Object>();
    	param_rstUserMw106rm3.put("I_HAK", "5260");
		param_rstUserMw106rm3 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw106rm3 = commonService.commonList("rstUserMw106rm3", param_rstUserMw106rm3);
    	
    	param_all.put("rstUserMw106rm3", rstUserMw106rm3);
    	param_all.put("param_rstUserMw106rm3", param_rstUserMw106rm3);
    	
    	//LmbYN 20140502 이후
    	Map<String, Object> param_rstUserMw106rms6 = new HashMap<String, Object>();
    	param_rstUserMw106rms6 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw106rms6 = commonService.commonList("rstUserMw106rms6", param_rstUserMw106rms6);
    	
    	param_all.put("rstUserMw106rms6", rstUserMw106rms6);
    	param_all.put("param_rstUserMw106rms6", param_rstUserMw106rms6);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 6) CytologyType
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw105rms1 = new HashMap<String, Object>();
    	param_rstUserMw105rms1 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	param_rstUserMw105rms1.put("I_HAK", "5270");
		
    	List<Map<String, Object>> rstUserMw105rms1 = commonService.commonList("rstUserMw105rms1", param_rstUserMw105rms1);
    	
    	param_all.put("rstUserMw105rms1", rstUserMw105rms1);
    	param_all.put("param_rstUserMw105rms1", param_rstUserMw105rms1);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 7) CytologyType2
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw106rms1 = new HashMap<String, Object>();
    	param_rstUserMw106rms1 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw106rms1 = commonService.commonList("rstUserMw106rms1", param_rstUserMw106rms1);
    	
    	param_all.put("rstUserMw106rms1", rstUserMw106rms1);
    	param_all.put("param_rstUserMw106rms1", param_rstUserMw106rms1);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 8) CytologyType3
		//****************************************************************************************
    	//****************************************************************************************
    	Map<String, Object> param_rstUserMw106rms5 = new HashMap<String, Object>();
		
    	param_rstUserMw106rms5 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw106rms5 = commonService.commonList("rstUserMw106rms5", param_rstUserMw106rms5);
    	
    	param_all.put("rstUserMw106rms5", rstUserMw106rms5);
    	param_all.put("param_rstUserMw106rms5", param_rstUserMw106rms5);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 9) Cytogene
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw117rm = new HashMap<String, Object>();
		
    	param_rstUserMw117rm = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw117rm = commonService.commonList("rstUserMw117rm", param_rstUserMw117rm);
    	
    	param_all.put("rstUserMw117rm", rstUserMw117rm);
    	param_all.put("param_rstUserMw117rm", param_rstUserMw117rm);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 10) isRemarktext
		//****************************************************************************************
    	//****************************************************************************************
    	
    	Map<String, Object> param_rstUserMw107rms3 = new HashMap<String, Object>();
    	param_rstUserMw107rms3 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> rstUserMw107rms3 = commonService.commonList("rstUserMw107rms3", param_rstUserMw107rms3);
    	    	
    	param_all.put("rstUserMw107rms3", rstUserMw107rms3);
    	param_all.put("param_rstUserMw107rms3", param_rstUserMw107rms3);
    	
    	//****************************************************************************************
    	//****************************************************************************************
		//********************** 11) Corona CT
		//****************************************************************************************
    	//****************************************************************************************
    	
    	/* ======================= Corona CT 조회 : Start ======================= */
    	// 1. 마라미터  map 선언
    	Map<String, Object> param_coronaCtVal = new HashMap<String, Object>();
    	
    	// 2. 선언한 마라미터 map 에 데이터 입력
    	param_coronaCtVal = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	// 3. 쿼리 실행 / 실행 결과값 list 입력
    	List<Map<String, Object>> coronaCtVal = commonService.commonList("coronaCtVal", param_coronaCtVal);
    	
    	// 4. 쿼리 결과 list를 Out 파라미터에 입력
    	param_all.put("coronaCtVal", coronaCtVal);
    	/* ======================= Corona CT 조회 : End ======================= */
    	
    	/* ======================= 세포유전 조회 : Start ======================= */
    	// 1. 마라미터  map 선언
    	Map<String, Object> param_cellVal = new HashMap<String, Object>();
    	
    	// 2. 선언한 마라미터 map 에 데이터 입력
    	param_cellVal = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	// 3. 쿼리 실행 / 실행 결과값 list 입력
    	List<Map<String, Object>> cellVal = commonService.commonList("cellVal", param_cellVal);
    	
    	// 4. 쿼리 결과 list를 Out 파라미터에 입력
    	param_all.put("cellVal", cellVal);
    	/* ======================= 세포유전 조회 : End ======================= */
    	
    	List<Map<String, Object>> redTxtList = commonService.commonList("redTxtList", param_rstUserMw107rms3);
    	param_all.put("redTxtList", redTxtList);
    	
    	param_all.put("O_MSGCOD", 200);
    	return param_all;
    }
	
	@ResponseBody
	@RequestMapping(value = "/rstUserMwr03rms5.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserMwr03rms5(HttpServletRequest request, HttpSession session) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> resultList = commonService.commonList("rstUserMwr03rms5", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/rstUserMw119rm.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserMw119rm(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
		
    	List<Map<String, Object>> resultList = commonService.commonList("rstUserMw119rm", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/rstUserMw118rm.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserMw118rm(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_GCT1",  "1");
		param.put("I_GCT2",  "1");
		param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
		
    	List<Map<String, Object>> resultList = commonService.commonList("rstUserMw118rm", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/testReq01List.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object testReq01List(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
    	param.put("I_NAM",  Utils.Null2Blank(request.getParameter("I_NAM")));
		param.put("I_CHN",  Utils.Null2Blank(request.getParameter("I_PCHN")));
    	
		param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_PHOS")));
		  
    	param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> testReq01List = commonService.commonList("testReq01List", param);
    	
    	param.put("testReq01List", testReq01List);
    	
    	//List<Map<String, Object>> rstUserDtl = commonService.commonList("rstUserDtl", param);
    	
    	//param.put("rstUserDtl", rstUserDtl);
    	
    	return param;
    }
	
	@ResponseBody			  
	@RequestMapping(value = "/testReq01List2.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object testReq01List2(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
    	param.put("I_NAM",  Utils.Null2Blank(request.getParameter("I_NAM")));
		param.put("I_CHN",  Utils.Null2Blank(request.getParameter("I_PCHN")));
    	
		param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_HOS")));
		  
    	param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> testReq01List = commonService.commonList("testReq01List", param);
    	
    	param.put("testReq01List", testReq01List);
    	
    	return param;
    }
	
	
	
	@ResponseBody
	@RequestMapping(value = "/testReq02List.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object testReq02List(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> resultList = commonService.commonList("testReq02List", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	//추가의뢰 고정코드
	@ResponseBody
	@RequestMapping(value = "/addReq00List.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object addReq00List(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
    	param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
		param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_PHOS")));
		param.put("I_NAM",  Utils.Null2Blank(request.getParameter("I_NAM")));
		param.put("I_CHN",  Utils.Null2Blank(request.getParameter("I_PCHN")));
		
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
	  	
		/*
		param.put("I_RGN1", Utils.Null2Blank(request.getParameter("I_RGN1")));
		param.put("I_RGN2", Utils.Null2Blank(request.getParameter("I_RGN2")));
		param.put("I_RGN3", Utils.Null2Blank(request.getParameter("I_RGN3")));
		*/
		
		param.put("I_HAK",  Utils.Null2Blank(request.getParameter("I_HAK")));
		param.put("I_SAB",  Utils.Null2Blank(request.getParameter("I_SAB")));
    	
    	List<Map<String, Object>> rstUserDtl = commonService.commonList("rstUserDtl", param);
    	List<Map<String, Object>> testReq01List = commonService.commonList("testReq01List", param);
    	List<Map<String, Object>> addReq01List = commonService.commonList("addReq01List", param);
    	List<Map<String, Object>> addReq02List = commonService.commonList("addReq02List", param);
    	List<Map<String, Object>> addReq01grid = commonService.commonList("addReq01grid", param);
    	
    	param.put("rstUserDtl", rstUserDtl);
    	param.put("testReq01List", testReq01List);
    	param.put("addReq01List", addReq01List);
    	param.put("addReq02List", addReq02List);
    	param.put("addReq01grid", addReq01grid);
    	
    	return param;
    }
	
	//추가의뢰 고정코드
		@ResponseBody
		@RequestMapping(value = "/addReq00List_item.do"
		  , method = {RequestMethod.GET, RequestMethod.POST})
//		  , produces="application/json;charset=UTF-8")
		public Object addReq00List_item(HttpServletRequest request, HttpSession session) throws Exception {

			Map<String, Object> param = new HashMap<String, Object>();
	    	
	    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	    	
	    	param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
	    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
	    	
	    	param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
			param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
			param.put("I_HOS",  Utils.Null2Blank(request.getParameter("I_HOS")));
			param.put("I_NAM",  Utils.Null2Blank(request.getParameter("I_NAM")));
			param.put("I_CHN",  Utils.Null2Blank(request.getParameter("I_PCHN")));
			
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
		  	
			/*
			param.put("I_RGN1", Utils.Null2Blank(request.getParameter("I_RGN1")));
			param.put("I_RGN2", Utils.Null2Blank(request.getParameter("I_RGN2")));
			param.put("I_RGN3", Utils.Null2Blank(request.getParameter("I_RGN3")));
			*/
			
			param.put("I_HAK",  Utils.Null2Blank(request.getParameter("I_HAK")));
			param.put("I_SAB",  Utils.Null2Blank(request.getParameter("I_SAB")));
	    	
	    	List<Map<String, Object>> rstUserDtl = commonService.commonList("rstUserDtl", param);
	    	List<Map<String, Object>> testReq01List = commonService.commonList("testReq01List", param);
	    	List<Map<String, Object>> addReq01List = commonService.commonList("addReq01List", param);
	    	List<Map<String, Object>> addReq02List = commonService.commonList("addReq02List", param);
	    	List<Map<String, Object>> addReq01grid = commonService.commonList("addReq01grid", param);
	    	
	    	param.put("rstUserDtl", rstUserDtl);
	    	param.put("testReq01List", testReq01List);
	    	param.put("addReq01List", addReq01List);
	    	param.put("addReq02List", addReq02List);
	    	param.put("addReq01grid", addReq01grid);
	    	
	    	return param;
	    }
	
	@ResponseBody
	@RequestMapping(value = "/addReq01grid.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object addReq01grid(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	//param.put("I_HOS",  Utils.Null2Zero(request.getParameter("I_DTLHOS")));
    	
    	List<Map<String, Object>> resultList = commonService.commonList("addReq01grid", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/addReq01gridSave.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object addReq01gridSave(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	commonService.commonInsert("addReq01gridSave", param);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/addReq01gridUpdate.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object addReq01gridUpdate(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		
		//saveData3 += "&R001TRM="+ popupFormData[i].R001TRM;
				//saveData3 += "&R001WRD="+ R001WRD;
				//saveData3 += "&R001CLT="+ R001CLT;
				//saveData3 += "&R001GDT="+ R001GDT;
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		param.put("I_R001PGY",  Utils.Null2Blank(request.getParameter("I_R001PGY")));
		param.put("I_R001TRM",  Utils.Null2Blank(request.getParameter("I_R001TRM")));
		param.put("I_R001WRD",  Utils.Null2Blank(request.getParameter("I_R001WRD")));
		param.put("I_R001CLT",  Utils.Null2Zero(request.getParameter("I_R001CLT").replaceAll("-", "")));
		param.put("I_R001GDT",  Utils.Null2Zero(request.getParameter("I_R001GDT").replaceAll("-", "")));
		param.put("I_R001GKD",  Utils.Null2Blank(request.getParameter("I_R001GKD")));
		param.put("I_R001OPN",  Utils.Null2Blank(request.getParameter("I_R001OPN")));
		param.put("I_R001STS",  Utils.Null2Blank(request.getParameter("I_R001STS")));
		
		
    	
    	commonService.commonUpdate("addReq01gridUpdate", param);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/addReq01gridInsert.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object addReq01gridInsert(HttpServletRequest request, HttpSession session) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		//saveData3 += "&R001TRM="+ popupFormData[i].R001TRM;
				//saveData3 += "&R001WRD="+ R001WRD;
				//saveData3 += "&R001CLT="+ R001CLT;
				//saveData3 += "&R001GDT="+ R001GDT;
		param.put("I_R001HOS",  Utils.Null2Blank(request.getParameter("I_R001HOS")));
		param.put("I_R001HNM",  Utils.Null2Blank(request.getParameter("I_R001HNM")));
		param.put("I_R001CHN",  Utils.Null2Blank(request.getParameter("I_R001CHN")));
		param.put("I_R001NAM",  Utils.Null2Blank(request.getParameter("I_R001NAM")));
		param.put("I_R001JMN",  Utils.Null2Blank(request.getParameter("I_R001JMN")));
		param.put("I_R001AGE",  Utils.Null2Zero(request.getParameter("I_R001AGE")));
		param.put("I_R001SEX",  Utils.Null2Blank(request.getParameter("I_R001SEX")));
		param.put("I_R001PGY",  Utils.Null2Blank(request.getParameter("I_R001PGY")));
		param.put("I_R001DTC",  Utils.Null2Blank(request.getParameter("I_R001DTC")));
		param.put("I_R001TRM",  Utils.Null2Blank(request.getParameter("I_R001TRM")));
		param.put("I_R001WRD",  Utils.Null2Blank(request.getParameter("I_R001WRD")));
		param.put("I_R001CLT",  Utils.Null2Zero(request.getParameter("I_R001CLT").replaceAll("-", "")));
		param.put("I_R001GDT",  Utils.Null2Zero(request.getParameter("I_R001GDT").replaceAll("-", "")));
		param.put("I_R001GKD",  Utils.Null2Blank(request.getParameter("I_R001GKD")));
		param.put("I_R001OPN",  Utils.Null2Blank(request.getParameter("I_R001OPN")));
		param.put("I_R001STS",  Utils.Null2Blank(request.getParameter("I_R001STS")));
		
    	commonService.commonInsert("addReq01gridInsert", param);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/orderReqList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object orderReqList(HttpServletRequest request, HttpSession session) throws Exception {
		
		
		List<Map<String, Object>> resultListA = new ArrayList<>(); 

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param_order = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		param_order = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param_order.put("I_DAT", Utils.isNull(param_order.get("I_DTLDAT")));
		param_order.put("I_JNO", Utils.isNull(param_order.get("I_DTLJNO")));
		
		//param_order.put("I_HOS", Utils.isNull(param_order.get("I_PHOS"))); // 2020.12.23 - 조회 조건에 병원추가 
		// 2021.12.16 - 오더의뢰지 버튼 누르면 현재 선택된 접수데이터의 병원코드가 넘어갈 수 있도록 변수가 필여함.
		param_order.put("I_HOS", Utils.isNull(param_order.get("order_img_hos"))); 
		 		
		// 해당 병원의 오더의뢰지만 조회 할 수 있도록 조회조건에 병원코드 추가
		List<Map<String, Object>> resultList = commonService.commonList("orderReqList", param_order);
		
		for(int i = 0; i < resultList.size(); i++){
			Map<String, Object> UUIDParam = new HashMap<String, Object>();
			StringBuffer sbMobileFileName;
			StringBuffer sbFileName;
			if(resultList.get(i).get("FLAG").equals("m")){
				sbMobileFileName = new StringBuffer();
				sbMobileFileName.append( resultList.get(i).get("DATE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_00").append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				sbFileName = new StringBuffer();
				sbFileName.append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_00").append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				param.put("I_HOS", Utils.isNull(param.get("I_PHOS")));
				param.put("I_DAT", Utils.isNull(param.get("I_DTLDAT")));
				param.put("I_JNO", resultList.get(i).get("SEQUENCE"));
				param.put("I_PAH", sbMobileFileName.toString());
				param.put("I_CNT", UUID.randomUUID().toString());
				param.put("I_ETC1", sbFileName.toString());
				param.put("I_ETC2", "m");
				
			} else if(resultList.get(i).get("FLAG").equals("s")){
				sbMobileFileName = new StringBuffer();
				sbMobileFileName.append( resultList.get(i).get("DATE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				sbFileName = new StringBuffer();
				sbFileName.append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				param.put("I_HOS", Utils.isNull(param.get("I_PHOS")));
				param.put("I_DAT", Utils.isNull(param.get("I_DTLDAT")));
				param.put("I_JNO", resultList.get(i).get("SEQUENCE"));
				param.put("I_PAH", sbMobileFileName.toString());
				param.put("I_CNT", UUID.randomUUID().toString());
				param.put("I_ETC1", sbFileName.toString());
				param.put("I_ETC2", "s");
			}
			
			
			commonService.commonInsert("orderReqSave", param);
			resultList.get(i).put("UUID", param.get("I_CNT"));
			resultList.get(i).put("FILENM", param.get("I_ETC1"));
			resultList.get(i).put("FILEPAH", param.get("I_PAH"));
			
			//resultList.get(i).put("I_IMG", "http://219.252.39.172:8080/spring_basic/fileDown?UUID=2e0bfd64-4a5b-41e0-8e6e-6ae07623ecab");
			//resultList.get(i).put("I_IMG", "http://219.252.39.131/shared_files/qna/abc.png");
			
		}
		
		param.put("resultList", resultList);
		
    	return param;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/orderReqList2.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object orderReqList2(HttpServletRequest request, HttpSession session) throws Exception {
		
		
		List<Map<String, Object>> resultListA = new ArrayList<>(); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param_order = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		param_order = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		param_order.put("I_DAT", Utils.isNull(param_order.get("I_DTLDAT")));
		param_order.put("I_JNO", Utils.isNull(param_order.get("I_DTLJNO")));
		
		List<Map<String, Object>> resultList = commonService.commonList("orderReqList", param_order);
		
		for(int i = 0; i < resultList.size(); i++){
			Map<String, Object> UUIDParam = new HashMap<String, Object>();
			StringBuffer sbMobileFileName;
			StringBuffer sbFileName;
			if(resultList.get(i).get("FLAG").equals("m")){
				sbMobileFileName = new StringBuffer();
				sbMobileFileName.append( resultList.get(i).get("DATE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_00").append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				sbFileName = new StringBuffer();
				sbFileName.append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_00").append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
				param.put("I_DAT", Utils.isNull(param.get("I_DTLDAT")));
				param.put("I_JNO", resultList.get(i).get("SEQUENCE"));
				param.put("I_PAH", sbMobileFileName.toString());
				param.put("I_CNT", UUID.randomUUID().toString());
				param.put("I_ETC1", sbFileName.toString());
				param.put("I_ETC2", "m");
				
			} else if(resultList.get(i).get("FLAG").equals("s")){
				sbMobileFileName = new StringBuffer();
				sbMobileFileName.append( resultList.get(i).get("DATE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")+"//").append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				sbFileName = new StringBuffer();
				sbFileName.append( resultList.get(i).get("HOSPITAL_CODE")).append("_").append(""+resultList.get(i).get("DATE")).append("_").append(StringUtils.leftPad(""+resultList.get(i).get("SEQUENCE"), 5, "0")).append("_").append(StringUtils.trimToEmpty((String)resultList.get(i).get("USER_ID"))).append(".jpg");
				
				param.put("I_HOS", Utils.isNull(param.get("I_HOS")));
				param.put("I_DAT", Utils.isNull(param.get("I_DTLDAT")));
				param.put("I_JNO", resultList.get(i).get("SEQUENCE"));
				param.put("I_PAH", sbMobileFileName.toString());
				param.put("I_CNT", UUID.randomUUID().toString());
				param.put("I_ETC1", sbFileName.toString());
				param.put("I_ETC2", "s");
			}
			
			
			commonService.commonInsert("orderReqSave", param);
			resultList.get(i).put("UUID", param.get("I_CNT"));
			resultList.get(i).put("FILENM", param.get("I_ETC1"));
			resultList.get(i).put("FILEPAH", param.get("I_PAH"));

			
			//resultList.get(i).put("I_IMG", "http://219.252.39.172:8080/spring_basic/fileDown?UUID=2e0bfd64-4a5b-41e0-8e6e-6ae07623ecab");
			//resultList.get(i).put("I_IMG", "http://219.252.39.131/shared_files/qna/abc.png");
			
		}
		
		param.put("resultList", resultList);
		
    	return param;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/rstUser03List.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUser03List(HttpServletRequest request, HttpSession session) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		String[] value = request.getParameterValues("CHN");
		
		String value2 = "";
		
		if (value != null){
			for(int i = 0; i < value.length; i++){
				value2 += value[i];
				if(i != value.length-1){
					value2 += "','";
				}
			}
		}
    	
    	List<Map<String, Object>> resultList = commonService.commonList("rstUser03List", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	
	@ResponseBody
    @RequestMapping(value = "/imgUrlDown.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object imgUrlDown(HttpServletRequest request,HttpSession response, @RequestParam(value="jsonData", required=false, defaultValue="") String jsonData)  throws Exception{
        
		String savePath =  "";//request.getParameter("file_path") ;
	      
	    String donwPath = "";
	             
	    OutputStream outStream = null;
	    URLConnection uCon = null;
	    InputStream is = null;
	      
	    FileInputStream fis = null;
	    ZipArchiveOutputStream zos = null;
	    BufferedInputStream bis = null;
	        
	    int size = 10240;
	    int sizez = 102400;
	    int byteWritten = 0;
	    byte[] bufz = new byte[sizez];
	    //String root = request.getSession().getServletContext().getRealPath("/");
	    //String location =   
	     
	    String targetPath = "/shared_files/order/";
	    
        String path = "";
        //String fileNm = "test.zip"; 
        String uuid = "";
        String downPath = "";
        String zipName = "";
		Map<String, Object> param = new HashMap<String, Object>();
		
		JSONArray array = new JSONArray(jsonData);
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		//JSONArray array = new JSONArray(String 타입으로 저장된 JSONArray데이터 넣기 );
		for (int i = 0; i < array.length(); i++) {
			JSONObject insideObject = array.getJSONObject(i);
			Map<String,Object> map = new HashMap<>();
			map.put("DATE",insideObject.get("DATE"));
			map.put("SEQUENCE",insideObject.get("SEQUENCE"));
			map.put("HOSPITAL_CODE",insideObject.get("HOSPITAL_CODE"));
			map.put("USER_ID",insideObject.get("USER_ID"));
			map.put("UUID",insideObject.get("UUID"));
			map.put("FLAG",insideObject.get("FLAG"));
			map.put("I_IMG",insideObject.get("I_IMG"));
			map.put("FILENM",insideObject.get("FILENM"));
			map.put("COMPANY",insideObject.get("COMPANY"));
		    dataList.add(map);
		}
		//BufferedInputStream bis = null;
		String pathUuid = "";
		for(int c = 0; c < dataList.size(); c++){

			if(c == 0){
				//FileInputStream fis = new FileInputStream(dataList.get(c).get("I_IMG").toString());
				//bis = fis;
				path = request.getSession().getServletContext().getRealPath(targetPath+dataList.get(c).get("UUID").toString());
				uuid = dataList.get(c).get("UUID").toString();
				zipName = dataList.get(c).get("DATE").toString() + "_" + dataList.get(c).get("HOSPITAL_CODE").toString() + "_" + dataList.get(c).get("USER_ID").toString().trim() + ".zip";
				downPath = targetPath+uuid;
				File f = new File(path);
		        if (! f.exists()) {
		            f.mkdirs();
		        }
				
			}
			
			try {
				savePath = dataList.get(c).get("I_IMG").toString();
		         URL Url;
		         byte[] buf;
		         int byteRead;
		         //url로 이미지 받기
		         Url = new URL(savePath);
		         
		         //outStream = new BufferedOutputStream(new FileOutputStream( path +"/"+ dataList.get(c).get("UUID").toString()+".jpg"));
		         outStream = new BufferedOutputStream(new FileOutputStream( path +"/"+ dataList.get(c).get("FILENM").toString()));
		         
		         uCon = Url.openConnection();
		         is = uCon.getInputStream();
		         buf = new byte[size];
		         while ((byteRead = is.read(buf)) != -1) {
		            outStream.write(buf, 0, byteRead);
		            byteWritten += byteRead;
		         }
		         
		         //파일 업로드된 경로
		         //donwPath = "D:/test/" + "otest.jpg" ;
		         

		      } catch (Exception e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            is.close();
		            outStream.flush();
		            outStream.close();
		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		      }
		}
		
		try {
//			
            // Zip 파일생성
            zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(path+"/"+zipName))); 
            for(int z = 0; z < dataList.size(); z++){
                //해당 폴더안에 다른 폴더가 있다면 지나간다.
            	/*
                if( new File(path).isDirectory()){
                	System.out.println("확인1");
                	continue;
                }
                */
                //encoding 설정
                zos.setEncoding("UTF-8");
                 
                //buffer에 해당파일의 stream을 입력한다.
                fis = new FileInputStream(path +"/"+ dataList.get(z).get("FILENM").toString());
                bis = new BufferedInputStream(fis,sizez);
                 
                //zip에 넣을 다음 entry 를 가져온다.
                zos.putArchiveEntry(new ZipArchiveEntry(dataList.get(z).get("FILENM").toString()));
                 
                //준비된 버퍼에서 집출력스트림으로 write 한다.
                int len;
                while((len = bis.read(bufz,0,sizez)) != -1){
                    zos.write(bufz,0,len);
                }
                
                bis.close();
                fis.close();
                zos.closeArchiveEntry();
            }
            zos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
			logger.debug(e);
        }finally{        
            if( zos != null ){
                zos.close();
            }
            if( fis != null ){
                fis.close();
            }
            if( bis != null ){
                bis.close();
            }
        }

		param.put("outZipNm", path+"test.zip");
		param.put("downPath", downPath);
		param.put("fileNm", zipName);
		param.put("O_MSGCOD", "200");
		
		return param;
    }
    
	/**
	 * @Method Name : rstUserList
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
	@RequestMapping(value = "/rstUserSMSSend.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserSMSSend(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		param.put("I_LOGMNU",  "reqUser03");
		param.put("I_LOGMNM",  "SMS");
		param.put("C001HPN",  Utils.Null2Blank(request.getParameter("C001HPN")));
    	
		commonService.commonInsert("rstUserSMSSend", param);
    	
    	return param;
    }
	
    private String uploadFile(String originalName, byte[] fileData) throws Exception{
            UUID uuid = UUID.randomUUID();
            
            String savedName = uuid.toString() + "_" + originalName;
            
            return savedName;
    }
    
	/**
	 * @Method Name	: comm_dofileDown
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	/*
	@RequestMapping(value = "/order_fileDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void order_fileDown(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException{
		
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

				
		//파일 업로드된 경로
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = request.getParameter("file_path") ;
		 
		// 실제 내보낼 파일명
		File file = new File(savePath);
		String CHARSET = "UTF-8";
		String mimetype = request.getSession().getServletContext().getMimeType(file.getName());		
		String mime = mimetype;
		int BUFFER_SIZE = 10240; 			// 10kb
		if (mimetype == null || mimetype.length() == 0) {
			mime = "application/octet-stream;";
		}

		byte[] buffer = new byte[BUFFER_SIZE];

		response.setContentType(mime + "; charset=" + CHARSET);

		String userAgent = request.getHeader("User-Agent");

		
		//fileNm = fileNm.replaceAll("[ ]+", "_");
		String fileNm = request.getParameter("file_name");
		//String fileNm = URLEncoder.encode(request.getParameter("file_name"), CHARSET);
		//fileNm = fileNm.replaceAll("\\+", "%20"); 
		
		String encodedFilename = "";
		*/
		/*******************브라우저에 따른 한글 깨짐 처리 시작*************************/
    	/*
		if (userAgent.indexOf("MSIE") > -1) {
			encodedFilename = URLEncoder.encode(fileNm, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgent.indexOf("Trident")  > -1) {       // IE11 문자열 깨짐 방지
			encodedFilename = URLEncoder.encode(fileNm, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgent.indexOf("Firefox") > -1) {
			encodedFilename = "\"" + new String(fileNm.getBytes("UTF-8"), "8859_1") + "\"";
			encodedFilename = URLDecoder.decode(encodedFilename);
		} else if (userAgent.indexOf("Opera") > -1) {
			encodedFilename = "\"" + new String(fileNm.getBytes("UTF-8"), "8859_1") + "\"";
		}else if(userAgent.indexOf("Chrome") > -1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileNm.length(); i++) {
				   char c = fileNm.charAt(i);
				   if (c > '~') {
						 sb.append(URLEncoder.encode("" + c, "UTF-8"));
				   }else{
						 sb.append(c);
				   }
			}
			encodedFilename = sb.toString().replaceAll("%20", " ");
		}else if(userAgent.indexOf("Safari") > -1){
			encodedFilename = "\"" + new String(fileNm.getBytes("UTF-8"), "8859_1")+ "\"";
			encodedFilename = URLDecoder.decode(encodedFilename);
		}else{
			encodedFilename = URLEncoder.encode(fileNm, "UTF-8").replaceAll("\\+", "%20");
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);

		if (userAgent.indexOf("Opera") > -1 ){

		    response.setContentType("application/octet-stream;charset=UTF-8");

		}


		long filesize = file.length();

		if (filesize > 0) {
			response.setHeader("Content-Length", "" + filesize);
		}


		InputStream is = null;
		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			is = new FileInputStream(file);
			fin = new BufferedInputStream(is);
			outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;

			while ((read = fin.read(buffer)) != -1) {
				outs.write(buffer, 0, read);
			}
		} catch(Exception e) {
			if (! (e instanceof java.net.SocketException) && ! "Broken pipe".equals(e.getMessage())){
				e.printStackTrace();
			}
		} finally {
			try {
				if(outs != null) {
					outs.flush();
					outs.close();
				}
			} catch (Exception ex1) {
				if (! (ex1 instanceof java.net.SocketException) && ! "Broken pipe".equals(ex1.getMessage())){
					ex1.printStackTrace();
				}
			}

			try {
				if(fin != null) {
					fin.close();
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
			
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} // end of try/catch

		
	}
	*/
    
    
}





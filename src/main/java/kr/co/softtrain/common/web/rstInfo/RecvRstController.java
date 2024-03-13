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
package kr.co.softtrain.common.web.rstInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.core.ParameterList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neodin.files.MCR03RM;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.rstInfo
 * RecvRstController.java
 * </pre>
 * @title :  엑셀 결과 관리
 * @author : seegene3
 * @since : 2019. 1. 23.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2019. 1. 23.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class RecvRstController  {

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;

	
	/**
	 * @Method Name	: goRecvRst
	 * @see
	 * <pre>
	 * Method 설명 : OCS 결과 받기 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvRst.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRecvRst(HttpServletRequest request, HttpSession session) throws Exception {
		String str = "rstInfo/recvRst";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name	: getrecvRstDown
	 * @see
	 * <pre>
	 * Method 설명 :  OCS 결과 받기 
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/recvRstDown.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getRecvRstDown(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		//시작일자
		param.put("I_FDT", Utils.isNull(param.get("I_FDT")).replaceAll("-", "")); 
		//종료일자
		param.put("I_TDT", Utils.isNull(param.get("I_TDT")).replaceAll("-", "")); 
    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		param.put("I_LOGEFG", "FD");
        param.put("O_MSGCOD", "200");	//상태값
        param.put("I_LOGSFL", "S");
        
   		session.setAttribute("I_LOGMNU", param.get("I_LOGMNU"));		//메뉴 코드
   		session.setAttribute("I_LOGMNM", param.get("I_LOGMNM"));		//메뉴 명

		if(param.containsKey("I_LOGPAR")){
			param.remove("I_LOGPAR");
		}
        param.put("I_LOGPAR", param.toString());
		// 결과받기 엑셀 다운전 로그 입력	
		commonService.commonLog(param);
		
		boolean DownFg = false;
		String filename = "nodata";
		String excelDownId = session.getAttribute("UID").toString();
	     
		// 씨젠 직원일 경우( 병원 코드의 첫 병원 아이디 조회하여 엑셀 다운 ID 설정)
		if("E".equals(session.getAttribute("ECF"))){

			// 씨젠 직원일 경우( 병원 코드가 없을경우 조회 되지 않게 설정)
			if(!"".equals(param.get("I_HOS").toString())){
				param.put("I_SERID",""); 
				param.put("I_SERNM",""); 
				param.put("I_SERHOS", param.get("I_HOS").toString()); 
				param.put("I_SERGIC",""); 
				param.put("I_SERDIV",""); 
			  	List<Map<String, Object>> resultList = commonService.commonList("sysUserMngList", param);
			  	if(resultList.size() > 0){
				  	excelDownId = resultList.get(0).get("LOGLID").toString();
				  	DownFg = true;
			  	}else{
			  		excelDownId = "미가입";
				  	DownFg = true;
			  	}
			}		  	
		}else{
			DownFg = true;
		}
		String Hakcd =  Utils.isNull(param.get("I_HAK").toString(),"");
		String isRewrite =  Utils.isNull(param.get("ISREWRITE").toString(),"X");
        String targetPath =  "/shared_files/outfiles/";
        // 엑셀 다운이 가능한 아이디 일경우 다운
        //(조회 병원의 아이디가 있을경우, 로그인한 사람이 병원일경우)
        if(DownFg){
			/**
			//구분값 A-전체  O-다운받은자료  X-미다운자료
			String strDownLoadFlag = "A";
			if(!(Boolean) parameters[3]){//isAllDownload
				strDownLoadFlag ="X";
			}
			**/
			MCR03RM  mcr03rm = new MCR03RM();
			/*A: 전체다운 , X:수신 받지 않은 결과 다운*/
			if("X".equals(isRewrite)){
				if("".equals(Hakcd)){
					filename = mcr03rm.createExcel(excelDownId,param.get("I_TDT").toString(),true);
				}else{
					filename = mcr03rm.createExcel(excelDownId,param.get("I_TDT").toString(),true,Hakcd);
				}
			}else if("A".equals(isRewrite)){
				if("".equals(Hakcd)){
					filename = mcr03rm.createExcel(excelDownId,param.get("I_FDT").toString(),param.get("I_TDT").toString(),true);
				}else{
					filename = mcr03rm.createExcel(excelDownId,param.get("I_FDT").toString(),param.get("I_TDT").toString(),true,Hakcd);
				}
			}
			
			param.put("file_name", filename);
			param.put("file_path", targetPath+File.separator+filename);
	        param.remove("I_UID");
	        param.remove("I_IP");
	        param.put("O_MSGCOD", "200");	//상태값
	        param.put("I_LOGSFL", "S");
        }
		if("nodata".equals(filename)){
	        param.put("O_MSGCOD", "300");// 파일 없을경우 저장 실패
	        param.put("I_LOGSFL", "F");
		}
		if(param.containsKey("I_LOGPAR")){
			param.remove("I_LOGPAR");
		}
        param.put("I_LOGPAR", param.toString());
        
   		session.setAttribute("I_LOGMNU", "");		//메뉴 코드
   		session.setAttribute("I_LOGMNM", "");		//메뉴 명

		// 결과받기 엑셀 다운로직 진행 후 로그 입력(성공,실패)	
		commonService.commonLog(param);
		
	  	return param;
	}
	
	/**
	 * @Method Name	: goRecvRst
	 * @see
	 * <pre>
	 * Method 설명 : OCS 결과 받기 페이지 호출 (영문)
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvRstEng.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRecvRstEng(HttpServletRequest request, HttpSession session) throws Exception {
		String str = "rstInfo/recvRstEng";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}

	/**
	 * @Method Name	: goRecvRstCorona
	 * @see
	 * <pre>
	 * Method 설명 : OCS 결과 받기(B) 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvRstCorona.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRecvRstCorona(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rstInfo/recvRstCorona";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name	: goRecvReportCorona
	 * @see
	 * <pre>
	 * Method 설명 : 코로나 결과보고 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvReportCorona.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRecvReportCorona(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rstInfo/recvReportCorona";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	
	/**
	 * @Method Name : recvRstCoronaList
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
	@RequestMapping(value = "/recvRstCoronaList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object recvRstCoronaList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
	  	param.put("I_DOWN_YN", request.getParameter("I_DOWN_YN"));
	  	param.put("I_CORONA_ASSEMBLE", request.getParameter("I_CORONA_ASSEMBLE"));	// 취함검사 검색조건 추가 (2021.08.05)
	  	param.put("I_CORONA_RESULT", request.getParameter("I_CORONA_RESULT"));	// 코로나결과 검색조건 추가 (2022.05.16)
	  			  
    	List<Map<String, Object>> recvRstCoronaList_ocs = commonService.commonList("recvRstCorona", param);
    	
    	String dat_yyyymmdd = "0";
    	String bdt_yyyymmdd = "0";
    	
    	for(int i=0;i<recvRstCoronaList_ocs.size();i++){
        	
    		if("0".equals(recvRstCoronaList_ocs.get(i).get("DAT").toString())){
    			dat_yyyymmdd = "0";
    		}else{
    			dat_yyyymmdd = recvRstCoronaList_ocs.get(i).get("DAT").toString().substring(0, 4) + "-" + recvRstCoronaList_ocs.get(i).get("DAT").toString().substring(4, 6) + "-" + recvRstCoronaList_ocs.get(i).get("DAT").toString().substring(6, 8);
    		}
    		
    		if("0".equals(recvRstCoronaList_ocs.get(i).get("BDT").toString())){
    			bdt_yyyymmdd = "0";
    		}else{
    			bdt_yyyymmdd = recvRstCoronaList_ocs.get(i).get("BDT").toString().substring(0, 4) + "-" + recvRstCoronaList_ocs.get(i).get("BDT").toString().substring(4, 6) + "-" + recvRstCoronaList_ocs.get(i).get("BDT").toString().substring(6, 8);
    		}
    		
    		
    		recvRstCoronaList_ocs.get(i).put("DAT", dat_yyyymmdd);
    		recvRstCoronaList_ocs.get(i).put("BDT", bdt_yyyymmdd);
    	}
    	
    	param.put("recvRstCoronaList_ocs", recvRstCoronaList_ocs);
    	
    	return param;
    }
	
	/**
	 * @Method Name : recvRstCoronaList
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
    @RequestMapping(value = "/recvRstCoronaListSave.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object recvRstCoronaListSave(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>>  paramList = new ArrayList();
		String JSONROW = request.getParameter("JSONROW")==null ? "" : request.getParameter("JSONROW");
	  	if(!"".equals(JSONROW)){
			paramList =  Utils.jsonList(request.getParameter("JSONROW"));	// 데이터 Parameter 상태로 변경
			
			logger.debug(paramList.size());
			logger.debug(paramList.get(0));
			
			commonService.commonListUpdate("recvRstCorona_save", param, paramList,"");
	  	}
	  	
		logger.debug("==================recvRstCoronaListSave===================================="+param);
	  	return param;
    }
	
	/**
	 * @Method Name : recvReportCoronaList
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
	@RequestMapping(value = "/recvReportCoronaList.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object recvReportCoronaList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_COR", "NML");
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	param.put("I_ID", Utils.Null2Blank(request.getParameter("I_ID")));
    	param.put("I_HOS", Utils.Null2Blank(request.getParameter("I_HOS")));
    	
    	List<Map<String, Object>> recvReportCoronaList_1 = commonService.commonList("recvReportCoronaHosCnt", param);
    	List<Map<String, Object>> recvReportCoronaList_2 = commonService.commonList("recvReportCoronaHosResult", param);    	
    	
    	// 환자정보에 '|'를 줄바꿈 문자로 치환한다
        /*for (Map<String, Object> row : recvReportCoronaList_2) {
            String patientnm = Utils.isNull(row.get("PATIENTNM")).replace("|", "\n");
            patientnm = patientnm.substring(0, patientnm.length()-1);
 
	        if (!patientnm.equals("")) {
	            row.put("PATIENTNM", patientnm);
	        }
        }*/
    	
    	param.put("recvReportCoronaList_1", recvReportCoronaList_1);
    	param.put("recvReportCoronaList_2", recvReportCoronaList_2);
    	
    	return param;
    }
	
	/**
	 * @Method Name : recvReportCoronaList
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
	@RequestMapping(value = "/mobileRecvReportCoronaList1.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object mobileRecvReportCoronaList1(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
		param.put("I_COR", "NML");
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	param.put("I_ID", Utils.Null2Blank(request.getParameter("I_ID")));
    	param.put("I_HOS", Utils.Null2Blank(request.getParameter("I_HOS")));
    	
    	List<Map<String, Object>> recvReportCoronaList_1 = commonService.commonList("recvReportCoronaHosCnt", param);
    	
    	param.put("recvReportCoronaList_1", recvReportCoronaList_1);
    	
    	return param;
    }
	
	/**
	 * @Method Name : recvReportCoronaList
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
	@RequestMapping(value = "/mobileRecvReportCoronaList2.do"
	, method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object mobileRecvReportCoronaList2(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		param.put("I_COR", "NML");
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
		param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
		param.put("I_ID", Utils.Null2Blank(request.getParameter("I_ID")));
		param.put("I_HOS", Utils.Null2Blank(request.getParameter("I_HOS")));
		
		List<Map<String, Object>> recvReportCoronaList_2 = commonService.commonList("recvReportCoronaHosResult", param);    	
		
		// 환자정보에 '|'를 줄바꿈 문자로 치환한다
		/*for (Map<String, Object> row : recvReportCoronaList_2) {
			String patientnm = Utils.isNull(row.get("PATIENTNM")).replace("|", "\n");
			patientnm = patientnm.substring(0, patientnm.length()-1);
			
			if (!patientnm.equals("")) {
				row.put("PATIENTNM", patientnm);
			}
		}*/
		
		param.put("recvReportCoronaList_2", recvReportCoronaList_2);
		
		return param;
	}
}

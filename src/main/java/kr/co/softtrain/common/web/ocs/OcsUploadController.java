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
package kr.co.softtrain.common.web.ocs;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.service.commonServiceChaDb;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.ExcelRead;
import kr.co.softtrain.custom.util.ExcelReadOption;
import kr.co.softtrain.custom.util.Utils;
import kr.co.softtrain.custom.util.jxlExcelRead;

/**
 * <pre>
 * kr.co.softtrain.common.web.ocs
 * OcsUploadController.java
 * </pre>
 * @title :  의뢰 접수(OCS)
 * @author : seegene3
 * @since : 2019. 1. 10.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2019. 1. 10.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class OcsUploadController  {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;

	@Resource(name = "commonServiceChaDb")
	private commonServiceChaDb commonServiceChaDb;

	/**
	 * @Method Name	: ocsUpload
	 * @see
	 * <pre>
	 * Method 설명 :의뢰 접수 조회 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/ocsUpload.do")
	public String ocsUpload(HttpServletRequest request,HttpSession session) throws Exception{
		String str = "ocs/ocsUpload";
		if(CommonController.AuthPage(request,session)){
			str = "index";
		}
		return str;
	}
	/**
	 * @Method Name	: ocsUpload
	 * @see
	 * <pre>
	 * Method 설명 :의뢰 접수 조회 페이지 호출
	 * return_type : String
	 * </pre>
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/ocsUpload01.do")
	public String ocsUpload01(HttpSession session){
		return "ocs/ocsUpload01";
	}
	

	/**
	 * @Method Name	: ocsUploadList
	 * @see
	 * <pre>
	 * Method 설명 : 추가검사 수진자 접수 목록 조회 
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/ocsUploadList.do" , method = {RequestMethod.GET, RequestMethod.POST})
	public Object ocsUploadList(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		param.put("I_FDT", request.getParameter("I_FDT").replaceAll("-", ""));
		param.put("I_TDT", request.getParameter("I_TDT").replaceAll("-", ""));
    	
    	List<Map<String, Object>> resultList = commonService.commonList("ocsUploadList", param);
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
	
	/**
	 * @Method Name	: ocsUploadDtl
	 * @see
	 * <pre>
	 * Method 설명 :의뢰 접수 항목 리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/ocsUploadDtl.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public Object ocsUploadDtl(HttpServletRequest request, HttpSession session) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	List<Map<String, Object>> resultList = commonService.commonList("ocsUploadDtl", param);
    	
	  	param.put("resultList", resultList);
	  	
	  	return param;
    }
	
	/**
	 * @Method Name	: ocsSave
	 * @see
	 * <pre>
	 * Method 설명 :의뢰 접수 항목 리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/ocsSave.do", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
	public Object ocsSave(MultipartHttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		boolean excelBool =  true;
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		Date d1 = new Date();
		SimpleDateFormat df = new SimpleDateFormat("YYYY/MM");
		String formattedDate = df.format(d1);
		
		//엑셀 포멧 조회  WEBSRCLIB.MWS009R2
    	List<Map<String, Object>> sysRegExFormatList = commonService.commonList("sysRegExFormatDtl", param);
    	
		Iterator<String> iter = request.getFileNames();

		// 엑셀 파일 받기
		while(iter.hasNext()) {
			String uploadFileName = iter.next();
			
			MultipartFile mFile = request.getFile(uploadFileName);
			String originalFileName = mFile.getOriginalFilename();
			if(!"".equals(originalFileName)){
				//엑셀 파일 업로드 경로
		        String targetPath =  "/shared_files/upload/excel/" +param.get("I_HOS");
//		        String targetPath =  "/shared_files/upload/excel/" +param.get("I_HOS")+"/"+ formattedDate;
		        String path = request.getSession().getServletContext().getRealPath(targetPath);

				File f = new File(path);
				if (! f.exists()) {
					f.mkdirs();
				}
	            param.put("I_FNM", originalFileName);
	            String savedName1f = uploadFile(originalFileName, mFile.getBytes());
	            File attachmentsf1 = new File(path,savedName1f);
	            FileCopyUtils.copy(mFile.getBytes(), attachmentsf1);
	            param.put("I_FPT", targetPath + "/" + attachmentsf1.getName());
	            param.put("I_FSNM", attachmentsf1.getName());

            	param.put(uploadFileName, "S");

				// 차세대db 에 넘길 파라미터
				HashMap<String, Object> paramChaDb = new HashMap<String, Object>();
				paramChaDb.putAll(param);

				commonService.commonInsert("ocsUploadSaveM", param);	// WEBSRCLIB.MWR002C1 등록

				param.put("I_UPDT",  param.get("O_UPDT").toString());	//  업로드 일자   
				param.put("I_FSQ", 	 param.get("O_FSQ"));	//  업로드 일자
				// 차세대db에 실패해도 프로세스 무관하게 Exception 처리.
				try{
					commonServiceChaDb.commonInsert("ocsUploadSaveM", paramChaDb);	// 차세대 WEBOBJLIB.MWR002C1 -> WEBDBLIB.MWR002M@
					paramChaDb.put("I_UPDT",  paramChaDb.get("O_UPDT").toString());	//  업로드 일자
					paramChaDb.put("I_FSQ", 	 paramChaDb.get("O_FSQ"));	//  업로드 일자
				}catch(Exception e){
					e.printStackTrace();
					logger.error("===========commonServiceChaDb.ocsUploadSaveM errors================\n"+ e.toString()+"================================================");
				}
				if(sysRegExFormatList.size()==0){
					excelBool = false;
				} 
				if(excelBool){
					excelBool = readExcelCheck(path + "/" + attachmentsf1.getName(), sysRegExFormatList,param);
				}
				//엑셀 업로드 가능 확인후 등록
				if(excelBool){
		            List<Map<String, Object>> paramList = readExcelToList(path + "/" + attachmentsf1.getName(), sysRegExFormatList, param);

		            if(paramList.size()>0){
						commonService.commonListInsert("ocsUploadSaveD", param, paramList); // WEBSRCLIB.MWR002C2 등록
						// 차세대db에 실패해도 프로세스 무관하게 Exception 처리.
						try{
							List<Map<String, Object>> paramListChaDb = readExcelToList(path + "/" + attachmentsf1.getName(), sysRegExFormatList, paramChaDb);
							commonServiceChaDb.commonListInsert("ocsUploadSaveD", paramChaDb, paramListChaDb); // 차세대 WEBOBJLIB.MWR002C2 -> WEBDBLIB.MWR002D@
						}catch(Exception e){
							e.printStackTrace();
							logger.error("===========commonServiceChaDb.ocsUploadSaveD errors================\n"+ e.toString()+"================================================");
						}
			    		if(Utils.isNull(param.get("O_MSGCOD"), 0) > 200){
			            	param.put(uploadFileName+"ERRNM",  "데이터 등록 실패");
			    	        param.put("I_LOGMSG","데이터 등록 실패");
			    		}
		            }
		            
		    		if(Utils.isNull(param.get("O_MSGCOD"), 0) > 200){
		    			// WEBSRCLIB.MWR002C2 데이터  등록시 오류날경우 등록내역 삭제
		            	param.put(uploadFileName, "F");
						param.put("I_STS", 	 "N");	// 오류날경우 상태값
		    	  		commonService.commonDelete("ocsUploadDel", param);
						// 차세대db에 실패해도 프로세스 무관하게 Exception 처리.
						try{
							paramChaDb.put(uploadFileName, "F");
							paramChaDb.put("I_STS", 	 "N");	// 오류날경우 상태값
							commonServiceChaDb.commonDelete("ocsUploadDel", paramChaDb);
						}catch(Exception e){
							e.printStackTrace();
							logger.error("===========commonServiceChaDb.ocsUploadDel errors by ocsUploadSaveD ================\n"+ e.toString()+"================================================");
						}
	    	  		    if(paramList.size() == 0){
	    	            	param.put(uploadFileName+"ERRNM",  "엑셀 형식 오류");
	    	    	  		param.put("I_LOGMSG", "엑셀 형식 오류");
	    	  		    }
		    	        param.put("O_MSGCOD", "999");	//상태값
		    	        param.put("I_LOGSFL", "F");
		    	        
		    	  		commonService.commonLog(param);
		            }
				}else{
						
	    			// WEBSRCLIB.MWR002C2 데이터  등록시 오류날경우 등록내역 삭제
	            	param.put(uploadFileName, "F");
	            	param.put(uploadFileName+"ERRNM",  "엑셀 형식 오류");
					param.put("I_STS", 	 "N");	// 오류날경우 상태값

	    	  		commonService.commonDelete("ocsUploadDel", param);
					// 차세대db에 실패해도 프로세스 무관하게 Exception 처리.
					try{
						paramChaDb.put(uploadFileName, "F");
						paramChaDb.put(uploadFileName+"ERRNM",  "엑셀 형식 오류");
						paramChaDb.put("I_STS", 	 "N");	// 오류날경우 상태값
						commonServiceChaDb.commonDelete("ocsUploadDel", paramChaDb);
					}catch(Exception e){
						e.printStackTrace();
						logger.error("===========commonServiceChaDb.ocsUploadDel errors by ocsUploadSaveM ================\n"+ e.toString()+"================================================");
					}
	    	  		param.put("I_LOGMSG", "엑셀 형식 오류");
	    	  		if(sysRegExFormatList.size() == 0)
	    	  		{
	    	  			param.put("I_LOGMSG", "엑셀 포맷 없음");
	    	  			param.put(uploadFileName+"ERRNM",   "엑셀 포맷 없음");
	    	  		}
	    	  		
	    	        param.put("O_MSGCOD", "999");	//상태값
	    	        param.put("I_LOGSFL", "F");
	    	  		commonService.commonLog(param);
				}
			}
		}
		

		return param;
	}
	    
	private String uploadFile(String originalName, byte[] fileData) throws Exception{
	        UUID uuid = UUID.randomUUID();
	        String savedName = uuid.toString() + "_" + originalName;
	        return savedName;
	}

	/**
	 * @Method Name	: readExcelCheck
	 * @see
	 * <pre>
	 * Method 설명 : 엑셀 다운전 다운 가능 체크 
	 * return_type : boolean
	 * </pre>
	 * @param url
	 * @param formatList
	 * @param param
	 * @return 
	 */
	public boolean  readExcelCheck(String url, List<Map<String, Object>> formatList,Map<String, Object> param){
		boolean rtnBool = true;
		ExcelRead  exRead = new ExcelRead();		
		jxlExcelRead  jxlexRead = new jxlExcelRead();
		ExcelReadOption ero = new ExcelReadOption();
		Map<String, Object> ListDtl = formatList.get(0);
		try {
			
			ero.setFilePath(url);
			ero.setStartRow(0);
			ArrayList<String> outputColumns = new ArrayList<String>();

	        for( String key : ListDtl.keySet() ){
	        	String KeyNm = 	key.substring(key.length()-3, key.length());
	        	try {
	        	    Double.parseDouble(KeyNm);
	  	        	if(!"".equals(Utils.isNull( ListDtl.get(key)))){
	  	        		outputColumns.add(ListDtl.get(key).toString());
	  	        	}
	        	} catch(NumberFormatException e) {
	        	}
	        }
			ero.setOutputColumns(outputColumns);
		} catch(RuntimeException e) {
			rtnBool = false;
			logger.error(" ExcelReadOption RuntimeException    == "+e.toString());
		}
		
		if(rtnBool){
			try{
				rtnBool =  exRead.readCheck(ero);
				
				if(!rtnBool){
					rtnBool =  jxlexRead.readCheck(ero);
				}
			} catch(Exception ex) {
				try{		
					rtnBool =  jxlexRead.readCheck(ero);
				} catch(RuntimeException e) {
					rtnBool = false;
					logger.error(" exRead  RuntimeException    == "+e.toString());
				} catch (Exception e) {
					rtnBool = false;
					logger.error(" exRead  Exception    == "+e.toString());
				}
			}		
		}
		
		return rtnBool;
	}
	/**
	 * @Method Name	: readExcelToList
	 * @see
	 * <pre>
	 * Method 설명 : 엑셀내역 List 생성 
	 * return_type : List<Map<String,Object>>
	 * </pre>
	 * @param url
	 * @param formatList
	 * @param param
	 * @return 
	 */
	public List<Map<String, Object>>  readExcelToList(String url, List<Map<String, Object>> formatList,Map<String, Object> param){

		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		ExcelRead  exRead = new ExcelRead();		
		jxlExcelRead  jxlexRead = new jxlExcelRead();	
		ExcelReadOption ero = new ExcelReadOption();
		
		boolean excelread = true;
		ero.setFilePath(url);
		ero.setStartRow(0);
		
		int  rowNum = 1;
		
		Map<String, Object> ListDtl = formatList.get(0);
		ArrayList<String> outputColumns = new ArrayList<String>();
		List<Map<String, Object>> exList =  new ArrayList<Map<String, Object>>();
		/** 001~045 컬럼 명이 숫자인 내역만 출력하여 진행 */
        for( String key : ListDtl.keySet() ){
        	String KeyNm = 	key.substring(key.length()-3, key.length());
        	try {
        	    Double.parseDouble(KeyNm);
  	        	if(!"".equals(Utils.isNull( ListDtl.get(key)))){
  	        		outputColumns.add(ListDtl.get(key).toString());
  	        	}
        	} catch(NumberFormatException e) {
        	}
        }

		try {
			ero.setOutputColumns(outputColumns);
			
			try{
				exList =  exRead.read(ero);
				if(exList.size()==0){
    				exList =  jxlexRead.read(ero);
    				if(exList.size()==0){
                    	excelread = false;
            			param.put("O_MSGCOD", "300");  
    				}
				}
            } catch (Exception ex) {
            	try{
    				exList =  jxlexRead.read(ero);
    				if(exList.size()==0){
                    	excelread = false;
            			param.put("O_MSGCOD", "300");  
    				}
                } catch (Exception e) {
                	excelread = false;
        			param.put("O_MSGCOD", "300");  
                	
    			}
			}
			
			if(excelread)
			{
			
				HashMap<String, Object> valMap = new HashMap<String, Object>(); 
				int row_cnt = exList.size();
				
				StringBuffer sb = new StringBuffer();
				
				int getKey_val_cnt = 0;			// 엑셀접수포맷 열(세로) 개수를 count
				int getKey_val_empty_cnt = 0;	// 엑셀접수포맷 열(세로) 데이터가 빈값인치 체크해서 개수를 count
				int empty_row_cnt = 0;			// 엑셀접수포맷 row(가로) 데이터에 열(세로) 데이터들이 모두 빈값인지 체크해서 개수를 count
				
				for(Map<String, Object> colMap : exList) {
					valMap = new HashMap<String, Object>(); 
					
					if(Integer.parseInt(Utils.isNull(ListDtl.get("S009STR"),"1")) <= rowNum){
						
						getKey_val_cnt = 0;
						getKey_val_empty_cnt = 0;
						
				        for( String key : ListDtl.keySet() ){
				        	String KeyNm = 	key.substring(key.length()-3, key.length());
				        	String getKey = ListDtl.get(key).toString();	//cell Key (A,B,C....)
				        	
				        	// 2022.12.05
				        	// 각 데이터 row(가로)마다 엑셀 접수포맷에 등록된 엑셀 열(세로)에 데이터가 빈값인지 체크한다.
				        	// row(가로) 데이터에 입력된 데이터가 모두 빈값인지를 체크하기 위함.
				        	if(colMap.containsKey(getKey) && !"".equals(getKey)){
				        		//System.out.println("### getKey : "+getKey);
				        		getKey_val_cnt++;
				        		if("".equals(colMap.get(getKey).toString().trim())){
				        			//System.out.println("### colMap.get(getKey).toString().trim()) : "+colMap.get(getKey).toString().trim());
				        			getKey_val_empty_cnt++;
				        		}
				        	}
				        	
				        	if(colMap.containsKey(getKey)){
				        		String strCell = colMap.get(getKey).toString();
				        		if("003".equals(KeyNm)||"022".equals(KeyNm)){
				        			strCell = isValidDate(colMap.get(getKey).toString());
				        		}
				        		// 숫자형 데이터의 경우 최대 길이를 변경
				        		if("015".equals(KeyNm)||"016".equals(KeyNm)){
				        			if(strCell.getBytes().length > 5){
				        				strCell = "";
				        			}
				        		}
			        			if("003".equals(KeyNm)||"022".equals(KeyNm)||"023".equals(KeyNm)||"044".equals(KeyNm)||"045".equals(KeyNm)){
				        			if(strCell.getBytes().length > 30){
				        				strCell = "";
				        			}
				        		}
				        		valMap.put("I_"+KeyNm, strCell);	// 엑셀 포멧에 맞는 데이터를 입력
			        		}else{
				        		if("HOS".equals(KeyNm)||"COR".equals(KeyNm)){
				        			valMap.put("I_"+KeyNm, getKey);
				        		}else{
				        			valMap.put("I_"+KeyNm, "");
				        		}
				        	}
				        }
				        
				        valMap.put("I_RNO",		String.valueOf(rowNum));	// row 순번
				        valMap.put("I_UPDT",   	param.get("O_UPDT").toString());	//  업로드 일자   
				        valMap.put("I_FSQ",   	param.get("O_FSQ"));	//  파일순번        
				        valMap.put("R002UPDT",  param.get("O_UPDT").toString());	//  업로드 일자   
				        valMap.put("R002FSQ",   param.get("O_FSQ"));	//  파일순번        
				        valMap.put("R002HOS",   param.get("I_HOS"));	//  병원코드        
				        valMap.put("I_HOS",   param.get("I_HOS"));	//  병원코드        
				        valMap.put("I_UPDT",  param.get("O_UPDT").toString());	//  업로드 일자   
				        valMap.put("I_FSQ",   param.get("O_FSQ"));	//  파일순번        
				        valMap.put("I_LOGMNM",	param.get("I_LOGMNM"));	//  메뉴명   
				        valMap.put("I_LOGMNU",  param.get("I_LOGMNU"));	//  메뉴코드          
				        rtnList.add(valMap);
					}
			        rowNum++;
			        
			        /*System.out.println("## getKey_val_cnt :: "+getKey_val_cnt);
					System.out.println("## getKey_val_empty_cnt :: "+getKey_val_empty_cnt);
					System.out.println("");*/
					
			        // 2022.12.05
			        // row(가로) 데이터에 입력된 데이터가 모두 빈값인 경우 count 한다.
					if(getKey_val_cnt==getKey_val_empty_cnt){
						empty_row_cnt++;
					}
					
					// 2022.12.05
					// row(가로) 데이터에 입력된 데이터가 모두 빈값인 데이터가 100개 이상인 경우는 엑셀접수 상세 테이블에 데이터를 입력하지 못하게 break 한다.
					// 이유는 비어있는 row를 불필요하게 엑셀접수 상세 테이블에 데이터가 입력되면서,
					// 서버에 부하가 발생하기 때문이다.
					if(empty_row_cnt>100){
						break;
					}
			    }				
			}
		} catch(RuntimeException e) {
			logger.error("readExcelToList RuntimeException"+ e.toString());
			param.put("O_MSGCOD", "300");  
		}		
		return rtnList;
	}
	private  String isValidDate(String input) {
		input	=	input.trim();
		String rtnStr = input;
		Date d1 = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		try	{
			d1	=	format.parse(input);
			rtnStr	=	df.format(d1);
			return	rtnStr;
		}
		catch(ParseException	e){
			try	{
				format	=	new	SimpleDateFormat("yyyy/MM/dd");
				d1	=	format.parse(input);
				rtnStr =	df.format(d1);
				return	rtnStr;
			}
			catch(ParseException	e2){
				try	{
					format	=	new	SimpleDateFormat("yyyy년MM월dd일");
					d1	=	format.parse(input);
					rtnStr	=	df.format(d1);
					return	rtnStr;
				}
				catch(ParseException	e1){
					return	rtnStr;
				}
			}
		}
	}
	
    /**
     * @Method Name	: ocsUploadDel
     * @see
     * <pre>
     * Method 설명 :  (추가의뢰정보) 승인
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    
    @ResponseBody
    @RequestMapping(value = "/ocsUploadDel.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
    public Object ocsUploadDel(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>>  paramList = new ArrayList();
		String JSONROW = request.getParameter("JSONROW")==null ? "" : request.getParameter("JSONROW");
		
	  	if(!"".equals(JSONROW)){
			paramList =  Utils.jsonList(request.getParameter("JSONROW").toString());	// 데이터 Parameter 상태로 변경
	  	}
	  	
	  	Object obj = commonService.commonListOne("ocsUploadCheck", param, paramList);
	  	
	  	if("200".equals(param.get("O_MSGCOD").toString())){
	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
        	param.put("I_LOGEFG", "DEL"); //수정 
	  		commonService.commonListDelete("ocsUploadDel", param, paramList,"");
	  	}
	  	return param;
    }
    
    
    
}




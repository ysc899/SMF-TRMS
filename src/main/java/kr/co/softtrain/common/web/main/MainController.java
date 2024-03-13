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
package kr.co.softtrain.common.web.main;

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
 * kr.co.softtrain.common.web.main
 * MainController.java
 * </pre>
 * @title :  
 * @author : seegene3
 * @since : 2018. 12. 4.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 12. 4.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class MainController  {
	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;

	private static commonService commonService2;
	 
	@Resource
	public void setCommonService2(commonService commonService) {
		MainController.commonService2 = commonService;
	}
	/**
	 * @Method Name	: goMain
	 * @see
	 * <pre>
	 * Method 설명 : 메인 화면 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/main.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goMain(HttpServletRequest request, HttpSession session){
		String str = "main/main";
		return str;
	}
	
	/**
	 * @Method Name	: goMainCont
	 * @see
	 * <pre>
	 * Method 설명 :  메인 컨텐츠 호출
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/mainCont.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goMainCont(HttpServletRequest request, HttpSession session){
		String str = "main/mainCont";
		return str;
	}
	

	/**
	 * @Method Name	: getMenuList
	 * @see
	 * <pre>
	 * Method 설명 : 로그인 사용자 권한의 LEFT메뉴 리스트 조회		
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/menuList.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getMenuList(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	List<Map<String, Object>> resultList = commonService.commonList("menuList", param);

	  	param.put("resultList", resultList);
	  	
	  	return param;
	}

	/**
	 * @Method Name	: getFavorites
	 * @see
	 * <pre>
	 * Method 설명 : 즐겨찾기 리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/Favorites.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object getFavorites(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		List<Map<String, Object>> resultList = commonService.commonList("Favorites", param);
		
		param.put("resultList", resultList);
		
		return param;
	}

    
    /**
     * @Method Name	: mainNotice
     * @see
     * <pre>
     * Method 설명 : 메인 팝업 생성 공문/공지사항 리스트 조회
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/mainNotice.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object mainNotice(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> noticeDtl = commonService.commonList("mainNotice",param);
        param.put("resultList",noticeDtl);
        
        return param;
    }
    
    /**
     * @Method Name	: mainContNotice
     * @see
     * <pre>
     * Method 설명 : 메인 컨텐츠 공문/공지사항 화면 리스트
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/mainContNotice.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object mainContNotice(HttpServletRequest request, HttpSession session) throws Exception{
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	
    	List<Map<String, Object>> noticeDtl = commonService.commonList("mainContNotice",param);
    	param.put("resultList",noticeDtl);
    	
    	return param;
    }
    
	
	/**
	 * @Method Name	: setFavoritesSave
	 * @see
	 * <pre>
	 * Method 설명 : 즐겨찾기에 메뉴 저장
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/FavoritesSave.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object setFavoritesSave(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	commonService.commonInsert("FavoritesSave", param);
	  	return param;
	}
	


	/**
	 * @Method Name	: setFavoritesDel
	 * @see
	 * <pre>
	 * Method 설명 : 즐겨찾기에 저장된 메뉴 삭제		
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/FavoritesDel.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object setFavoritesDel(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	commonService.commonDelete("FavoritesDel", param);
	  	return param;
	}
    
    /**
     * @Method Name : MenuQcSetList
     * @see
     * <pre>
     * Method 설명 : 사용자, 메뉴별 퀵셋업 저장된 내역 조회
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value = "/MenuQcSetList.do" , method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public Object MenuQcSetList(HttpServletRequest request,HttpSession session) throws Exception {
		Map<String, Object> getParam = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();

		getParam = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

	  	List<Map<String, Object>> resultList = commonService.commonList("mainQcSetList", getParam);
		for(Map<String, Object> Obj : resultList){
			Obj.put("S007RNM", "");
	  	}
	  	
	  	getParam.put("resultList", resultList);
	  	param.putAll(getParam);
	  	return param;
    }

    /**
     * @Method Name : getMenuQcSetList
     * @see
     * <pre>
     * Method 설명 : 사용자, 메뉴별 퀵셋업 저장된 내역 조회
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
	public  List<Map<String, Object>> getMenuQcSetList(Map<String, Object> param) throws Exception{
	  	List<Map<String, Object>> resultList = commonService2.commonList("mainQcSetList", param);
		for(Map<String, Object> Obj : resultList){
			Obj.put("S007RNM", "");
	  	}
	  	return resultList;
	}	
	
}




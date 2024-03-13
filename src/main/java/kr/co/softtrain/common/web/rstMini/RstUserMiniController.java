package kr.co.softtrain.common.web.rstMini;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

@Controller
public class RstUserMiniController {

Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	@RequestMapping(value = "/rstUserTableMini01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstUserTableMini01(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rstMini/rstUserTableMini01";
		/*if(CommonController.AuthPage(request,session)){
			str = "index";
		}*/
		return str;
	}
	
	@RequestMapping(value = "/rstUserTableMini02.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstUserTableMini02(HttpServletRequest request, HttpSession session, Model model) throws Exception{
		String str = "rstMini/rstUserTableMini02";
		Map<String, Object> param = new HashMap<String, Object>();
		logger.info(param.toString());
		getParameterModel(request, model);	// 데이터 Parameter 상태로 변경
		logger.info(model);
		//logger.info(param.toString());
		//logger.info(param.get("I_DTLDAT"));
		//model.addAttribute("param", param);
		
		/*if(CommonController.AuthPage(request,session)){
			str = "index";
		}*/
		return str;
	}
	
	public static Model getParameterModel(HttpServletRequest request, Model model) {
		//Model model = new ModelAndView();
		//ModelAndView model = new ModelAndView();
		// 파라미터 이름
		Enumeration<String> paramNames = request.getParameterNames();
		// 저장할 맵
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 맵에 저장
		while(paramNames.hasMoreElements()) {
			String name	= paramNames.nextElement().toString();
			Object value	= request.getParameter(name);
			paramMap.put(name, value.toString().trim());
			//model.addAttribute(name, value.toString().trim());
			model.addAttribute(name, value.toString().trim());
		}
		// 결과반환
		return model;
	}
	
	/**
	 * @Method Name : rstUserListMini
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
	@RequestMapping(value = "/rstUserListMini.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		//logger.debug(((String) param.get("I_FDT")).replaceAll("-", ""));
		//logger.debug(((String) param.get("I_TDT")).replaceAll("-", ""));
		// 기본 검색 조건 전체 기간으로 검색
		//param.put("I_FDT", "");
		//param.put("I_TDT", "");
		//logger.debug(param.toString());
		
		param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
    	param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
    	
		if(param.get("I_DPN").toString().equals("I_DGN")){
			//param.get("I_DGN").toString()
/*			if(!param.get("I_DGN").toString().equals("")){
				logger.debug("적용");
				param.put("I_FDT", ((String) param.get("I_FDT")).replaceAll("-", ""));
				param.put("I_TDT", ((String) param.get("I_TDT")).replaceAll("-", ""));
				logger.info(param.get("I_TDT"));
			}
			logger.info(param.get("I_TDT"));*/
			param.put("I_PCHN", "");
			param.put("I_NAM", "");
		}else if(param.get("I_DPN").toString().equals("I_NAM")){
			param.put("I_PCHN", "");
			param.put("I_NAM", param.get("I_NAM"));
		}else if(param.get("I_DPN").toString().equals("I_PCHN")){
			param.put("I_PCHN", param.get("I_PCHN"));
			param.put("I_NAM", "");
		}
		param.put("I_DGN", "D");
		
    	if(param.get("I_FDT") == "0"){
    		param.put("I_FDT", "00000000"); 
    	}
    	
    	if(param.get("I_TDT") == "0"){
    		param.put("I_TDT", "99999999"); 
    	}
		
		//param.put("I_PECF", Utils.Null2Blank(request.getParameter("I_PECF")));
        
		// 일반,조직,세포 공백
	  	param.put("I_RGN1", "");
	  	param.put("I_RGN2", "");
	  	param.put("I_RGN3", "");
	  	// 수진자명 또는 차트번호만 검색
	  	param.put("I_PINQ", "Y");
	  	
	  	//기타 검색 조건 공백 입력
	  	param.put("I_HAK", "");
	  	param.put("I_STS", "");
	  	param.put("I_PETC", "");
	  	param.put("I_PJKNF", "");
	  	param.put("I_PJKN", "");
	  	
	  	
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
    	
    	List<Map<String, Object>> resultList = commonService.commonList("rstUserList", param);
    	
    	param.put("resultList", resultList);
    	
    	return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/rstUserDtlMini.do"
	  , method = {RequestMethod.GET, RequestMethod.POST})
//	  , produces="application/json;charset=UTF-8")
	public Object rstUserDtl(HttpServletRequest request, HttpSession session) throws Exception {
//		System.out.println(session.getAttributeNames());
		
		Map<String, Object> param_all = new HashMap<String, Object>();
		
		//param.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
		//param.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
		
		//환자정보
		Map<String, Object> param_rstUserDtl = new HashMap<String, Object>();
    	param_rstUserDtl = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	//logger.info("miniDtl");
    	//logger.debug(param_rstUserDtl.toString());

 	  	param_rstUserDtl.put("I_RGN1", "");
 	  	param_rstUserDtl.put("I_RGN2", "");
 	  	param_rstUserDtl.put("I_RGN3", "");
 	  	
 	  	//logger.debug(param_rstUserDtl.get("I_DTLDAT"));
 	  	
    	param_rstUserDtl.put("I_HAK",  Utils.Null2Blank(request.getParameter("I_HAK")));
    	param_rstUserDtl.put("I_SAB",  Utils.Null2Blank(request.getParameter("I_HAK")));
    	
    	param_rstUserDtl.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserDtl.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
		
    	List<Map<String, Object>> rstUserDtl = commonService.commonList("rstUserDtl", param_rstUserDtl);
    	
    	param_all.put("rstUserDtl", rstUserDtl);
    	param_all.put("param_rstUserDtl", param_rstUserDtl);
    	
    	//수진자 항목 리스트
    	Map<String, Object> param_rstUserDtl2 = new HashMap<String, Object>();
    	
    	param_rstUserDtl2 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserDtl2.put("I_RGN1", "");
    	param_rstUserDtl2.put("I_RGN2", "");
    	param_rstUserDtl2.put("I_RGN3", "");

    	param_rstUserDtl2.put("I_HAK",  Utils.Null2Blank(request.getParameter("I_HAK")));
    	param_rstUserDtl2.put("I_SAB",  Utils.Null2Blank(request.getParameter("I_SAB")));
    	
    	param_rstUserDtl2.put("I_GCD", "");
    	param_rstUserDtl2.put("I_ACD", "");
    	
    	param_rstUserDtl2.put("I_ICNT", "1000");            //읽을갯수
    	param_rstUserDtl2.put("I_PNN", "C");			  //이전다음
    	
    	param_rstUserDtl2.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserDtl2.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserDtl2 = commonService.commonList("rstUserDtl2", param_rstUserDtl2);
    	
    	param_all.put("rstUserDtl2", rstUserDtl2);
    	param_all.put("param_rstUserDtl2", param_rstUserDtl2);
    	
    	//기형아 검사(Prenatal Screening Test)
    	Map<String, Object> param_rstUserMw109rms = new HashMap<String, Object>();
    	
    	param_rstUserMw109rms = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	//param.put("I_FDT", Utils.Null2Zero(request.getParameter("I_FDT").replaceAll("-", "")));
        //param.put("I_TDT", Utils.Null2Zero(request.getParameter("I_TDT").replaceAll("-", "")));
        
    	//param.put("I_PECF", "");
    	
    	//param.put("I_ICNT", "40");            //읽을갯수
    	//param.put("I_PNN", "C");			  //이전다음
    	//param.put("O_BDT","");
    	param_rstUserMw109rms.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw109rms.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw109rms = commonService.commonList("rstUserMw109rms", param_rstUserMw109rms);
    	
    	param_rstUserMw109rms.put("O_BDT", param_rstUserMw109rms.get("O_BDT"));
    	param_rstUserMw109rms.put("O_LPD", param_rstUserMw109rms.get("O_LPD"));
    	param_rstUserMw109rms.put("O_GW1", param_rstUserMw109rms.get("O_GW1"));
    	param_rstUserMw109rms.put("O_GW2", param_rstUserMw109rms.get("O_GW2"));
    	param_rstUserMw109rms.put("O_RSK", param_rstUserMw109rms.get("O_RSK"));
    	param_rstUserMw109rms.put("O_DWN", param_rstUserMw109rms.get("O_DWN"));
    	param_rstUserMw109rms.put("O_EDW", param_rstUserMw109rms.get("O_EDW"));
    	param_rstUserMw109rms.put("O_NTD", param_rstUserMw109rms.get("O_NTD"));
    	param_rstUserMw109rms.put("O_RMK1", param_rstUserMw109rms.get("O_RMK1"));
    	param_rstUserMw109rms.put("O_RMK2", param_rstUserMw109rms.get("O_RMK2"));
    	param_rstUserMw109rms.put("O_AFP", param_rstUserMw109rms.get("O_AFP"));
    	param_rstUserMw109rms.put("O_AFPM", param_rstUserMw109rms.get("O_AFPM"));
    	param_rstUserMw109rms.put("O_HCG", param_rstUserMw109rms.get("O_HCG"));
    	param_rstUserMw109rms.put("O_HCGM", param_rstUserMw109rms.get("O_HCGM"));
    	param_rstUserMw109rms.put("O_UE3", param_rstUserMw109rms.get("O_UE3"));
    	param_rstUserMw109rms.put("O_UE3M", param_rstUserMw109rms.get("O_UE3M"));
    	param_rstUserMw109rms.put("O_INH", param_rstUserMw109rms.get("O_INH"));
    	param_rstUserMw109rms.put("O_INHM", param_rstUserMw109rms.get("O_INHM"));
    	param_rstUserMw109rms.put("O_FBG", param_rstUserMw109rms.get("O_FBG"));
    	param_rstUserMw109rms.put("O_FBGM", param_rstUserMw109rms.get("O_FBGM"));
    	param_rstUserMw109rms.put("O_PPA", param_rstUserMw109rms.get("O_PPA"));
    	param_rstUserMw109rms.put("O_PPAM", param_rstUserMw109rms.get("O_PPAM"));
    	param_rstUserMw109rms.put("O_NT", param_rstUserMw109rms.get("O_NT"));
    	param_rstUserMw109rms.put("O_NTM", param_rstUserMw109rms.get("O_NTM"));
    	param_rstUserMw109rms.put("O_RC", param_rstUserMw109rms.get("O_RC"));
    	
    	
    	param_all.put("rstUserMw109rms", rstUserMw109rms);
    	param_all.put("param_rstUserMw109rms", param_rstUserMw109rms);
    	
    	//Remark(혈종)
    	Map<String, Object> param_rstUserMw108rmA = new HashMap<String, Object>();
    	
    	param_rstUserMw108rmA = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw108rmA.put("I_RBN", "A");
    	
    	param_rstUserMw108rmA.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw108rmA.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw108rmA = commonService.commonList("rstUserMw108rm", param_rstUserMw108rmA);
    	
    	param_rstUserMw108rmA.put("O_REMK", param_rstUserMw108rmA.get("O_REMK"));
    	param_rstUserMw108rmA.put("O_CNT", param_rstUserMw108rmA.get("O_CNT"));
    	param_rstUserMw108rmA.put("O_RC", param_rstUserMw108rmA.get("O_RC"));
    	
    	
    	param_all.put("rstUserMw108rmA", rstUserMw108rmA);
    	param_all.put("param_rstUserMw108rmA", param_rstUserMw108rmA);
    	
    	//Remark(산전)
    	Map<String, Object> param_rstUserMw108rmB = new HashMap<String, Object>();
    	
    	param_rstUserMw108rmB = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw108rmB.put("I_RBN", "B");
    	
    	param_rstUserMw108rmB.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw108rmB.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw108rmB = commonService.commonList("rstUserMw108rm", param_rstUserMw108rmB);
    	
    	param_rstUserMw108rmB.put("O_REMK", param_rstUserMw108rmB.get("O_REMK"));
    	param_rstUserMw108rmB.put("O_CNT", param_rstUserMw108rmB.get("O_CNT"));
    	param_rstUserMw108rmB.put("O_RC", param_rstUserMw108rmB.get("O_RC"));
    	
    	
    	param_all.put("rstUserMw108rmB", rstUserMw108rmB);
    	param_all.put("param_rstUserMw108rmB", param_rstUserMw108rmB);
    	
    	//microbio
    	Map<String, Object> param_rstUserMw118rms1 = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
		
    	param_rstUserMw118rms1 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw118rms1.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw118rms1.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw118rms1 = commonService.commonList("rstUserMw118rms1", param_rstUserMw118rms1);
    	
    	param_all.put("rstUserMw118rms1", rstUserMw118rms1);
    	param_all.put("param_rstUserMw118rms1", param_rstUserMw118rms1);
    	
    	//LmbYN 20140502 이전
    	Map<String, Object> param_rstUserMw106rm3 = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
    	param_rstUserMw106rm3.put("I_HAK", "5260");
		
    	param_rstUserMw106rm3 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw106rm3.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw106rm3.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw106rm3 = commonService.commonList("rstUserMw106rm3", param_rstUserMw106rm3);
    	
    	param_all.put("rstUserMw106rm3", rstUserMw106rm3);
    	param_all.put("param_rstUserMw106rm3", param_rstUserMw106rm3);
    	
    	//LmbYN 20140502 이후
    	Map<String, Object> param_rstUserMw106rms6 = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
		
    	param_rstUserMw106rms6 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw106rms6.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw106rms6.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw106rms6 = commonService.commonList("rstUserMw106rms6", param_rstUserMw106rms6);
    	
    	param_all.put("rstUserMw106rms6", rstUserMw106rms6);
    	param_all.put("param_rstUserMw106rms6", param_rstUserMw106rms6);
    	
    	//CytologyType
    	Map<String, Object> param_rstUserMw105rms1 = new HashMap<String, Object>();
    	
    	param_rstUserMw105rms1 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw105rms1.put("I_HAK", "5270");
		
    	param_rstUserMw105rms1.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw105rms1.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw105rms1 = commonService.commonList("rstUserMw105rms1", param_rstUserMw105rms1);
    	
    	param_all.put("rstUserMw105rms1", rstUserMw105rms1);
    	param_all.put("param_rstUserMw105rms1", param_rstUserMw105rms1);
    	
    	//CytologyType2
    	Map<String, Object> param_rstUserMw106rms1 = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
		
    	param_rstUserMw106rms1 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw106rms1.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw106rms1.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw106rms1 = commonService.commonList("rstUserMw106rms1", param_rstUserMw106rms1);
    	
    	param_all.put("rstUserMw106rms1", rstUserMw106rms1);
    	param_all.put("param_rstUserMw106rms1", param_rstUserMw106rms1);
    	
    	//CytologyType3
    	Map<String, Object> param_rstUserMw106rms5 = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
		
    	param_rstUserMw106rms5 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw106rms5.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw106rms5.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw106rms5 = commonService.commonList("rstUserMw106rms5", param_rstUserMw106rms5);
    	
    	param_all.put("rstUserMw106rms5", rstUserMw106rms5);
    	param_all.put("param_rstUserMw106rms5", param_rstUserMw106rms5);
    	
    	//Cytogene
    	Map<String, Object> param_rstUserMw117rm = new HashMap<String, Object>();
    	
		//param.put("I_GCD", "");
		//param.put("I_ACD", "");
		
    	param_rstUserMw117rm = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw117rm.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw117rm.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw117rm = commonService.commonList("rstUserMw117rm", param_rstUserMw117rm);
    	
    	param_all.put("rstUserMw117rm", rstUserMw117rm);
    	param_all.put("param_rstUserMw117rm", param_rstUserMw117rm);
    	
    	//isRemarktext
    	Map<String, Object> param_rstUserMw107rms3 = new HashMap<String, Object>();
    	
    	param_rstUserMw107rms3 = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
    	
    	param_rstUserMw107rms3.put("I_DAT",  Utils.Null2Zero(request.getParameter("I_DTLDAT")));
    	param_rstUserMw107rms3.put("I_JNO",  Utils.Null2Zero(request.getParameter("I_DTLJNO")));
    	
    	List<Map<String, Object>> rstUserMw107rms3 = commonService.commonList("rstUserMw107rms3", param_rstUserMw107rms3);
    	
    	//System.out.println(param_rstUserMw107rms3.get("O_ERR"));
    	
    	param_all.put("rstUserMw107rms3", rstUserMw107rms3);
    	param_all.put("param_rstUserMw107rms3", param_rstUserMw107rms3);
    	
    	List<Map<String, Object>> redTxtList = commonService.commonList("redTxtList", param_rstUserMw107rms3);
    	
    	param_all.put("redTxtList", redTxtList);
    	
    	param_all.put("O_MSGCOD", 200);
    	return param_all;
    }
}

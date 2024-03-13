package kr.co.softtrain.common.web.chatbot.controller;

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
public class ChatBotController {
	Logger logger = LogManager.getLogger();

	@Resource(name = "commonService")
	private commonService commonService;

	@RequestMapping(value = "/loginCheck.do", method = {RequestMethod.POST})
	public ResponseEntity<Object> requestTestResultForUserInfo(HttpServletRequest request, 
			                                                   HttpSession session) throws Exception
	{
		String I_UID = request.getParameter("I_UID");
		String I_HCK = request.getParameter("I_HCK");
		Map<String, Object> responseResult = new HashMap<String, Object>();
		
		if(I_UID == null || I_HCK == null) {
			// TODO: handle exception
			responseResult.put("result", false);
			return ResponseEntity.badRequest().body(responseResult);
		}
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("I_UID", I_UID);
			params.put("I_HCK", I_HCK);
			List<Map<String, Object>> loginCheck = commonService.commonList("loginCheck", params); //kh
			responseResult.put("result", true);
			responseResult.put("data", params);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			responseResult.put("result", false);
			logger.error("requestTestResultForUserInfo failed", e);
		}
		return ResponseEntity.ok(responseResult);
	}
	
	@ResponseBody
	@RequestMapping(value = "/chatbotLoginKey.do", method = {RequestMethod.POST})
	public Object getChatbotLoginKey(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param = Utils.getParameterMap(request);
		
		String pw_sha_check_val = "";
		
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		
		pw_sha_check_val = CommonController.sha256(generatedString).substring(0,50);
		param.put("I_HCK", pw_sha_check_val);
		commonService.commonUpdate("chatbotLoginKey", param);
		
		return param;
	}
}
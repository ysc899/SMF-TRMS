package kr.co.softtrain.resultImg.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.rst.RstGroupReport;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;
//IDR확인일자 2022-07-11 유승현
@Controller
public class ResultImgController {

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
	
	@Autowired
	private RstGroupReport reportController;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private ResultImgProp resultImgProp;
	
	// 한글명 -> 영문명 변환 맵핑 데이터
	private Map<String, String> korEngNameMap = new HashMap<>();
	
	private final AES256 aes256 = new AES256();
	
	// "MMM d, yyyy"
	private static final DateTimeFormatter SRC_BIRTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter RESULT_BIRTH_FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

	private static final DateTimeFormatter SRC_DT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter RESULT_DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	private static final DateTimeFormatter SRC_TM_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
	private static final DateTimeFormatter RESULT_TM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	
	public ResultImgController() {
		// 담당검사자
		korEngNameMap.put("박주형", "Ju Hyeong Park");
		korEngNameMap.put("김광현", "Kim Kwang Hyun");
		korEngNameMap.put("최광후", "Choi Kwang Hoo");
		korEngNameMap.put("박준완", "Park Jun Wan");
		
		// 담당전문의
		korEngNameMap.put("정선경", "Sunkyung Jung");
		korEngNameMap.put("최재림", "Jae-Lim Choi");
		korEngNameMap.put("이현철", "Hyunchul Lee");
		korEngNameMap.put("허민석", "Min Seok Heo");

		// 확인전문의
		korEngNameMap.put("이선화","Sun Hwa Lee");
		korEngNameMap.put("윤태준","Tae Jun Yoon");
		korEngNameMap.put("김태엽","Tae Yeob Kim");
		korEngNameMap.put("장민중","Min Joong Jang");

		// 대표의료원장
		korEngNameMap.put("한규섭","kyou-Sup Han");
		korEngNameMap.put("김정만","Jeong-Man Kim");
		korEngNameMap.put("서헌석","Hun Suk Suh");		
	}
	
	@RequestMapping(value="/resultInqForm.do", method=RequestMethod.GET)
	public String goCovidResultInqForm(HttpServletRequest request, HttpSession session) {
		Map<String, Object> param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		if (param.containsKey("sign")) {
			// 인증절차 진행
			if (this.isValidParam(param)) {
				session.setAttribute("JSIGN", param.get("sign"));
				session.setAttribute("JDAT", param.get("dat"));
				session.setAttribute("JNO", param.get("jno"));
				session.setAttribute("HOS", Objects.toString(param.get("hos"), ""));	// 병원코드. 없을 수 있음.
				
				if (session.getAttribute("UID") == null || StringUtils.isEmpty(Objects.toString(session.getAttribute("UID"), ""))) {
					session.setAttribute("UID", "SMS_rslt_IMG");
					try {
						session.setAttribute("IP", StringUtils.defaultString(this.getRequestIp(request)));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						session.setAttribute("IP", "");
					}
				}
			} else {
				throw new ResultImgException("INVALID_ENCKEY", "요청 URL이 유효하지 않습니다.");
			}
		} else {
			throw new ResultImgException("요청 URL이 유효하지 않습니다.");
		}
		
		return "resultImg/resultInqForm";
	}
	
	@RequestMapping(value="/resultInqResult.do", method=RequestMethod.POST)
	public String goCrownixViewer(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> reqParam = Utils.getParameterMap(request);	// request에 전달된 parameter 데이터를 Map 형태로 변경
		logger.debug("request param : {}", reqParam);
		
		String sign = Objects.toString(session.getAttribute("JSIGN"), "");
		String dat = Objects.toString(session.getAttribute("JDAT"), "");
		String jno = StringUtils.isNotEmpty(Objects.toString(session.getAttribute("JNO"), "")) ? String.format("%5s", Objects.toString(session.getAttribute("JNO"), "")).replaceAll(" ", "0") : "";
		String hos = Objects.toString(session.getAttribute("HOS"));
		logger.debug("sign:{}, dat:{}, jno:{}", sign, dat, jno);
		
		if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(dat) || StringUtils.isEmpty(jno)) {
			throw new ResultImgException("시간이 경과하여 세션이 종료되었습니다.\nSMS 메시지로 제공한 link를 통해 다시 접속하시기 바랍니다.");
		}
		
		String reqCustName = Objects.toString(reqParam.get("custName"), "");
		String reqMobileNo = Objects.toString(reqParam.get("custMobileNo"), "");
		String reqBirthday = Objects.toString(reqParam.get("custBirthday"), "");

		// 요청 key 값으로 접수일자, 접수번호, 검사항목코드, 병원코드, 환자 개인정보 조회
		Map<String, Object> procParam = new HashMap<>();
		procParam.put("I_HOS", hos);
		procParam.put("I_DAT", dat);
		procParam.put("I_JNO", jno);
		procParam.put("I_SIGN", sign);
		List<Map<String, Object>> resultList = Collections.<Map<String, Object>>emptyList();
		try {
			// 2021.12.02 - 기존 프로시저 호출 시간 딜레이로 인한 프로시저 수정
			resultList = commonService.commonList("rstGroupCoronaResult", procParam);
		} catch(Exception ex) {
			throw new ResultImgException("조회 결과가 없습니다.", ex);
		}

		if (resultList.size() <= 0) {
			throw new ResultImgException("조회 결과가 없습니다.");
		}

		// 조회 결과에서 개인정보 입력폼에서 입력한 개인정보와 일치하는 정보 찾기
		List<Map<String, Object>> resultRows = resultList.stream()
			.filter(row ->
						dat.equals(Objects.toString(row.get("S100DAT"), "").trim()) &&
						jno.equals(String.format("%5s", Objects.toString(row.get("S100JNO"), "").trim()).replaceAll(" ", "0"))  &&
						reqCustName.equals(Objects.toString(row.get("F100NAM"), "").trim()) &&
						reqMobileNo.equals(Objects.toString(row.get("S100PNO"), "").trim()) &&
						reqBirthday.equals(Objects.toString(row.get("F100BDT"), "").trim()) &&
						sign.equals(Objects.toString(row.get("S100TMP8"), "").trim())  )
			.collect(Collectors.toList());
		if (resultRows.size() <= 0) {
			throw new ResultImgException("요청하신 데이터를 찾을 수 없습니다.");
		}
		logger.debug("CORONA_RESULT search result : {}", resultRows.size());
		
		// rstGroupReportList (MWGRPRPT 프로시저) 호출 및 report Grouping 처리 
		// 예: I_PARAM=|NML201705160555641377|NML201705160555641392|
		//String combineParam = resultRows.stream().map(row -> "|NML" + dat + jno + Objects.toString(row.get("F600GCD"), "")).collect(Collectors.joining()) + "|";
		//logger.debug("I_PARAM: {}", combineParam);

		final String LOGMNM = "결과지 출력";
		final String LOGMNU = "RSTREPORT";

		// 질병청 요청으로 국문만 출력하도록 변경됨에 따라 언어 param 값을 "kor"로 고정 - 20211108 최재원 대리 변경 요청
		Map<String, Object> resultGroupMap = this.getReportGroup(dat, jno, Objects.toString(session.getAttribute("UID"), ""), Objects.toString(session.getAttribute("IP"), ""), "kor", resultRows);
		
		// TODO 홍재훈 - QRCode 생성 로직 시작 -------------------------------------
		logger.debug("F010RNO: {}", Objects.toString(resultGroupMap.get("F010RNO"), ""));
		if ("COV".equals(Objects.toString(resultGroupMap.get("F010RNO"), "").toUpperCase().trim())) {
			try {
				// 질병청 요청으로 국문만 출력하도록 변경됨에 따라 언어 param 값을 "kor"로 고정 - 20211108 최재원 대리 변경 요청
				final String appRoot = request.getSession().getServletContext().getRealPath("/");
				QrCodeUtils.makeQrCode(dat, jno, hos, "kor", sign, resultImgProp, appRoot);
			} catch(Exception ex) {
				StringWriter error = new StringWriter();
	            ex.printStackTrace(new PrintWriter(error));
	            if (logger.isErrorEnabled()) {
	                logger.error("QRCode 생성 실패 : {}", error.toString());
	            }
	            // QRCode 생성이 실패하더라도 결과지는 정상적으로 출력이 되어야 하므로 log만 남기고 나머지 로직 정상적으로 실행
			}
		}
		// TODO 홍재훈 - QRCode 생성 로직 종료 -------------------------------------
		
		// Crownix properties 정보 조회
		Map<String, String> crownixInfo = commonController.getCrownixInfo();
		
		// crownixViewer 호출위한 parameter 설정
		request.setAttribute("SMS_LINK_REQ", "Y");
		request.setAttribute("viewerUrl", crownixInfo.get("viewerUrl"));
		request.setAttribute("mrd_path", resultGroupMap.get("S018RFN") + ".mrd");
		request.setAttribute("rdServerSaveDir", crownixInfo.get("rdSaveDir"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("/rf [").append(crownixInfo.get("rdAgentUrl")).append("]")
			.append(" /rpprnform [A4]")
			.append(" /rv COR[NML]")
			.append("UID[")
				.append(session.getAttribute("UID"))
			.append("]UIP[")
				.append(session.getAttribute("IP"))
			.append("]")
			.append("JDAT[")
				.append(Objects.toString(resultGroupMap.get("F600DAT"), ""))
			.append("]JNO[")
				.append(Objects.toString(resultGroupMap.get("F600JNO"), ""))
			.append("]")
			.append("GCD[")
				.append(Objects.toString(resultGroupMap.get("P_GCD"), ""))
			.append("]RFN[")
				.append(Objects.toString(resultGroupMap.get("F010RNO"), ""))
			.append("]WTR[A]")
			.append("SYSURL[")
				.append(crownixInfo.get("trmsSystemUrl"))
			.append("]PG_TIT[")
				.append(Objects.toString(resultGroupMap.get("PG_TIT"), ""))
			.append("]")
			.append("PGCD[")
				.append(Objects.toString(resultGroupMap.get("PGCD"), ""))
			.append("]TMP[")
				.append(Objects.toString(resultGroupMap.get("S018RFN"), ""))
			.append("]");
		
		String dpi300HosGubun = Objects.toString(resultGroupMap.get("DPI300_HOS"), "");
		if ("714".equals(dpi300HosGubun) || "300".equals(dpi300HosGubun)) {
			sb.append(" /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [300] /rimageydpi [300] /p /rusesystemfont");
		} else {
			sb.append(" /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [110] /rimageydpi [110] /p /rusesystemfont");
		}
		request.setAttribute("param", sb.toString());
		
		request.setAttribute("systemDownDir", crownixInfo.get("rdSysSaveDir") + "/" + Objects.toString(resultRows.get(0).get("S100HOS"), ""));
		request.setAttribute("downFileName", Objects.toString(resultGroupMap.get("FILE_NM"), ""));
		
		Map<String, String> imgObj = new HashMap<>();
		imgObj.put("I_LOGMNM", 	LOGMNM);			// 고정값
		imgObj.put("I_LOGMNU", 	LOGMNU);		// 고정값
		imgObj.put("I_HOS", 	Objects.toString(resultRows.get(0).get("S100HOS"), ""));		// 병원코드
		imgObj.put("F600DAT", 	Objects.toString(resultGroupMap.get("F600DAT"), ""));	// 접수일자
		imgObj.put("F600JNO", 	Objects.toString(resultGroupMap.get("F600JNO"), ""));	// 접수번호
		imgObj.put("P_GCD", 	Objects.toString(resultGroupMap.get("PGCD"), ""));		// 검사코드
		imgObj.put("S018RFN", 	Objects.toString(resultGroupMap.get("S018RFN"), ""));	// 검사 결과지 폼
		imgObj.put("FILE_NM", 	Objects.toString(resultGroupMap.get("FILE_NM"), ""));	// 파일명
		imgObj.put("F600BDT", 	Objects.toString(resultGroupMap.get("BDT"), ""));		// 보고일자
		imgObj.put("I_LOGEFG", 	"RP");		//이벤트 종류E VT_FLAG : (RP출력)
		imgObj.put("I_MCD", 	"RESULTINQFORM");		// 결과지 뷰어를 실행한 메뉴코드(RESULTINQFORM : SMS URL 코로나 결과지)

		//logger.debug("imgObj : {}", JSONValue.toJSONString(imgObj));

		request.setAttribute("imgObj", JSONValue.toJSONString(imgObj));
		
		request.setAttribute("dpi300_hos_gubun", dpi300HosGubun);
		request.setAttribute("crownixVeiwer_print_YN", "STSD");		// STSD : 미리보기, STSD_PRINT : 인쇄
		// 질병청 요청으로 국문만 출력하도록 변경됨에 따라 언어 param 값을 "kor"로 고정 - 20211108 최재원 대리 변경 요청
		request.setAttribute("report_language", "kor");	// 국문 - kor, 영문 - eng
		
		return "report/mobCrownixViewer";
	}
	
	/**
	 * @Method Name	: sms_reportFileDown
	 * @see
	 * <pre>
	 * Method 설명 : 결과지출력 - PDF다운
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws InterruptedException 
	 */
	/* CommonController 에 comm_reportFileDown 과 합침.
	@RequestMapping(value = "/sms_reportFileDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void doSmsReportfileDown(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException, InterruptedException{
		String sign = Objects.toString(session.getAttribute("JSIGN"), "");
		String dat = Objects.toString(session.getAttribute("JDAT"), "");
		String jno = StringUtils.isNotEmpty(Objects.toString(session.getAttribute("JNO"), "")) ? String.format("%5s", Objects.toString(session.getAttribute("JNO"), "")).replaceAll(" ", "0") : "";
		logger.debug("sign:{}, dat:{}, jno:{}", sign, dat, jno);
		
		if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(dat) || StringUtils.isEmpty(jno)) {
			throw new ResultImgException("시간이 경과하여 세션이 종료되었습니다.\nSMS 메시지로 제공한 link를 통해 다시 접속하시기 바랍니다.");
		}

		Utils utils = new Utils();
		
		
		//리포트 서버의 파일 업로드된 경로 
		String rdServerSaveDir = request.getParameter("rdServerSaveDir") ;
		String rdFilePathName = request.getParameter("rdFilePathName") ;

		String systemDownDir = request.getParameter("systemDownDir") ;

		//logger.debug("systemDownDir:: "+systemDownDir);

		String file_name = request.getParameter("file_name");
		String dpi300_hos_gubun = request.getParameter("dpi300_hos_gubun");
		String imgEachHos = request.getParameter("imgEachHos");

		//logger.debug("file_name:: "+file_name);

		//file_name = new String(file_name.getBytes("8859_1"), "UTF-8");

		//logger.debug("file_name22:: "+file_name);

		//file_name = file_name.replaceAll("\\+", "%20");

		String file_ext = request.getParameter("file_ext") ;

		String CHARSET = "UTF-8";

		//logger.debug("file_name="+file_name);

		String root = request.getSession().getServletContext().getRealPath("/");

		String downFilePath = root+systemDownDir;

		//디렉토리 생성 

		File desti = new File(downFilePath);

		//해당 디렉토리의 존재여부를 확인
		if(!desti.exists()){
		//없다면 생성
			desti.mkdirs(); 
		}


		/***************************Crownix Sever의 파일 System으로 이동 시작************************************//*
		//파일 업로드된 경로
		String savePath = "";

		if("PDF".equals( file_ext.toUpperCase() )){//PDF 파일 Crownix 서버에서 System 서버로 이동
			String rdServerSavePath = rdServerSaveDir + "/"+rdFilePathName ;
			savePath = utils.downCrownixPDFFile(rdServerSavePath, downFilePath, file_name);
		}else if("JPG".equals( file_ext.toUpperCase() )){//JPG 파일들을 Crownix 서버에서 System 서버로 하나의 jpg로 합치고 이동
			
			if(!"".equals(dpi300_hos_gubun) && "714".equals(dpi300_hos_gubun)){
				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name, dpi300_hos_gubun, imgEachHos);
			}else{
				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name, imgEachHos);
			}
			
		}

		/***************************리포트 파일 다운로드 시작************************************//*

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");


		//실제 내보낼 파일명


		File file = new File(savePath);
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
		//String fileNm = URLEncoder.encode(request.getParameter("file_name"), CHARSET);
		//fileNm = fileNm.replaceAll("\\+", "%20"); 
		//file_name = URLEncoder.encode(request.getParameter("file_name"), CHARSET);
		//response.setHeader("Content-Disposition", "attachment; filename=\""	+ fileNm + "\";");


		String encodedFilename = "";

		//logger.debug("userAgent="+userAgent);


		/*******************브라우저에 따른 한글 깨짐 처리 시작*************************//*
		if (userAgent.indexOf("MSIE") > -1) {
			encodedFilename = URLEncoder.encode(file_name, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgent.indexOf("Trident")  > -1) {       // IE11 문자열 깨짐 방지
			encodedFilename = URLEncoder.encode(file_name, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgent.indexOf("Firefox") > -1) {
			encodedFilename = "\"" + new String(file_name.getBytes("UTF-8"), "8859_1") + "\"";
			encodedFilename = URLDecoder.decode(encodedFilename);
		} else if (userAgent.indexOf("Opera") > -1) {
			encodedFilename = "\"" + new String(file_name.getBytes("UTF-8"), "8859_1") + "\"";
		}else if(userAgent.indexOf("Chrome") > -1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < file_name.length(); i++) {
				   char c = file_name.charAt(i);
				   if (c > '~') {
						 sb.append(URLEncoder.encode("" + c, "UTF-8"));
				   }else{
						 sb.append(c);
				   }
			}
			encodedFilename = sb.toString().replaceAll("%20", " ");
		}else if(userAgent.indexOf("Safari") > -1){
			encodedFilename = "\"" + new String(file_name.getBytes("UTF-8"), "8859_1")+ "\"";
			encodedFilename = URLDecoder.decode(encodedFilename);
		}else{
			encodedFilename = URLEncoder.encode(file_name, "UTF-8").replaceAll("\\+", "%20");
		}

		// 파일 속성이 zip 일땐는 마지막 확장자를 지우고 zip 으로 바꾼다.
		if ("application/zip".equals(mime)){
			String repFileName = encodedFilename;
			String regex = ".*\\.(\\w+)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(encodedFilename);
			// 파일 확장자만 zip 으로 바꿈.
			if (matcher.matches()) {
				repFileName = repFileName.replace(matcher.group(1),"zip");
			}
			encodedFilename = repFileName;
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);

		if (userAgent.indexOf("Opera") > -1 ){

		    response.setContentType("application/octet-stream;charset=UTF-8");

		}
		/*******************브라우저에 따른 한글 깨짐 처리 끝*************************//*

		long filesize = file.length();

		if (filesize > 0) {
			response.setHeader("Content-Length", "" + filesize);
		}


		InputStream is = null;
		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
	
			//out.clear();
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
		/***************************리포트 파일 다운로드 끝************************************//*
	}
	*/

	@RequestMapping(value="/verifyReport.do", method=RequestMethod.GET)
	public String verifyReport(HttpServletRequest request, HttpSession session) {
		String encData = request.getParameter("q");
		Map<String, Object> param  = Collections.emptyMap();
		try {
			List<Map<String, Object>> paramList =  Utils.jsonList(aes256.decrypt(encData));	// 데이터 Parameter 상태로 변경
			param = paramList.get(0);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error(ex);
			throw new ResultImgException("요청 정보가 유효하지 않습니다.", ex);
		}
		
		if (param.containsKey("sign")) {
			// 인증절차 진행
			if (!this.isValidParam(param)) {
				throw new ResultImgException("INVALID_ENCKEY", "요청 정보가 유효하지 않습니다.");
			}
		} else {
			throw new ResultImgException("요청 정보가 유효하지 않습니다.");
		}

		logger.debug("############################## 요청 key 값으로 접수일자, 접수번호, 검사항목코드, 병원코드, 환자 개인정보 조회 ##################################### [" + param.toString() + "]");
		// 요청 key 값으로 접수일자, 접수번호, 검사항목코드, 병원코드, 환자 개인정보 조회
		String dat = Objects.toString(param.get("dat"), "");
		String jno = StringUtils.isNotEmpty(Objects.toString(param.get("jno"), "")) ? String.format("%7s", Objects.toString(param.get("jno"), "")).replaceAll(" ", "0") : ""; // IDR 유승현 접수번호 길이변경(5->7)
		String reportLang = Objects.toString(param.get("reportLang"), "kor");
		String uid = StringUtils.isNotEmpty(Objects.toString(session.getAttribute("UID"), "")) ? Objects.toString(session.getAttribute("UID"), "") : "SMS_rslt_IMG";
		String connIp;
		try {
			connIp = StringUtils.isNotEmpty(Objects.toString(session.getAttribute("IP"), "")) ? Objects.toString(session.getAttribute("IP"), "") : StringUtils.defaultString(this.getRequestIp(request));
		} catch (UnknownHostException e) {
			connIp = "";
		}

		Map<String, Object> procParam = new HashMap<>();
		procParam.put("I_HOS", Objects.toString(param.get("hos"), ""));
		procParam.put("I_DAT", dat);
		procParam.put("I_JNO", jno);
		procParam.put("I_SIGN", param.get("sign"));
		List<Map<String, Object>> resultList = Collections.<Map<String, Object>>emptyList();
		try {
			// 2021.12.02 - 기존 프로시저 호출 시간 딜레이로 인한 프로시저 수정
			resultList = commonService.commonList("rstGroupCoronaResult", procParam);
		} catch(Exception ex) {
			throw new ResultImgException("조회 결과가 없습니다.", ex);
		}

		if (resultList.size() <= 0) {
			throw new ResultImgException("조회 결과가 없습니다.");
		}
		
		// 조회 결과에서 개인정보 입력폼에서 입력한 개인정보와 일치하는 정보 찾기
		List<Map<String, Object>> resultRows = resultList.stream()
			.filter(row ->
						dat.equals(Objects.toString(row.get("S100DAT"), "").trim()) &&
						jno.equals(String.format("%7s", Objects.toString(row.get("S100JNO"), "").trim()).replaceAll(" ", "0")) ) // IDR 유승현 접수번호 길이변경(5->7)
			.collect(Collectors.toList());
		if (resultRows.size() <= 0) {
			throw new ResultImgException("요청하신 데이터를 찾을 수 없습니다.");
		}
		logger.debug("CORONA_RESULT search result : {}", resultRows.size());

		// rstGroupReportList (MWGRPRPT 프로시저) 호출 및 report Grouping 처리 
		Map<String, Object> resultGroupMap = this.getReportGroup(dat, jno, uid, connIp, reportLang, resultRows);
		
		// 검사코드 연결
		String gcds = resultRows.stream().map(row -> Objects.toString(row.get("F600GCD"), "")).collect(Collectors.joining());

		// 환자정보/검사결과 조회 procedure 호출 parameter 설정
		procParam.clear();
		procParam.put("I_COR", "NML");
		procParam.put("I_UID", uid);
		procParam.put("I_IP", connIp);
		procParam.put("I_DAT", dat);
		procParam.put("I_JNO", jno);
		procParam.put("I_GCD", gcds);
		procParam.put("I_FOM", "COV");
		procParam.put("I_TMP", resultGroupMap.get("S018RFN"));
		procParam.put("I_WTR", "A");
		
		logger.debug("############################## 환자정보 조회 verifyReport ##################################### [" + procParam.toString() + "]");
		
		// 환자정보 조회
		List<Map<String, Object>> patientInfos = Collections.emptyList();
		try {
			if ("eng".equals(reportLang)) {
				// 영문 정보
				patientInfos = commonService.commonList("getPatientInfoEng", procParam);
			} else {
				// 국문 정보
				patientInfos = commonService.commonList("getPatientInfoKor", procParam);
			}
			
		} catch(Exception ex) {
			throw new ResultImgException("조회 결과가 없습니다.", ex);
		}

		if (patientInfos.size() <= 0) throw new ResultImgException("조회 결과가 없습니다.");
		Map<String, Object> patientInfoRow = patientInfos.get(0);
		
		// 담당검사자
		String mngNames = patientInfoRow.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith("R_HNAM") && !entry.getKey().startsWith("R_HNAMNO") && !entry.getKey().equals("R_HNAM0") && StringUtils.isNotEmpty(Objects.toString(entry.getValue(),"")))
			.map(entry -> {
				String name = "eng".equals(reportLang) ? korEngNameMap.get(Objects.toString(entry.getValue(),"")) : Objects.toString(entry.getValue(),"");
				return name + " " + patientInfoRow.get(entry.getKey().replace("R_HNAM", "R_HNAMNO"));
			})
			.collect(Collectors.joining(", "));

		// 담당전문의
		String mngDrs = patientInfoRow.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith("R_HLIC") && !entry.getKey().startsWith("R_HLICNO") && StringUtils.isNotEmpty(Objects.toString(entry.getValue(),"")))
				.map(entry -> {
					String name = "eng".equals(reportLang) ? korEngNameMap.get(Objects.toString(entry.getValue(),"")) : Objects.toString(entry.getValue(),"");
					return name + " " + patientInfoRow.get(entry.getKey().replace("R_HLIC", "R_HLICNO"));
				})
				.collect(Collectors.joining(", "));
		
		// 확인전문의
		String cnfmDrs = patientInfoRow.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith("R_LNAM") && !entry.getKey().startsWith("R_LNAMNO") && StringUtils.isNotEmpty(Objects.toString(entry.getValue(),"")))
				.map(entry -> "eng".equals(reportLang) ? korEngNameMap.get(Objects.toString(entry.getValue(),"")) : Objects.toString(entry.getValue(),""))
				.collect(Collectors.joining(", "));
		
		// 대표의료원장
		String repMdcDr = "eng".equals(reportLang) ?
								korEngNameMap.get(Objects.toString(patientInfoRow.get("R_LLIC"), "")) :
									Objects.toString(patientInfoRow.get("R_LLIC"), "");
		
		// 검사실시기관
		List<String> testingCenterAddr = splitTestingCenterAddr(Objects.toString(patientInfoRow.get("R_ADDR"),""), reportLang);
		
		// 검체접수일자 변경
//		String pjdt = Objects.toString(patientInfoRow.get("R_PJDT"),"").replaceFirst("(^2027)([0-9]{4})$", "2020$2").replaceFirst("^(2028|2029|2030)([0-9]{4})$", "2021$2");
		// IDR 유승현 날짜형식변경 제거 2022.08.11
		String pjdt = Objects.toString(patientInfoRow.get("R_PJDT"),"");
		
		// 검체접수시간
		String pjtm = Objects.toString(patientInfoRow.get("R_PJTM"),"");
		if (StringUtils.isNotEmpty(pjtm) || !"0".equals(pjtm)) {
			pjtm = String.format("%6s", pjtm).replaceAll(" ", "0");
		}
		
		// 결과보고시간
		String pbtm = Objects.toString(patientInfoRow.get("R_PBTM"),"");
		if (StringUtils.isNotEmpty(pbtm) || !"0".equals(pbtm)) {
			pbtm = String.format("%6s", pbtm).replaceAll(" ", "0");
		}
		
		request.setAttribute("R_100NAM", patientInfoRow.get("R_100NAM"));		// 수진자명
		request.setAttribute("R_100AGE", patientInfoRow.get("R_100AGE"));		// 나이
		request.setAttribute("R_100SEX", patientInfoRow.get("R_100SEX"));		// 성별
		request.setAttribute("R_100JNR", convertJnrToBirth(Objects.toString(patientInfoRow.get("R_100JNR"),""), reportLang));		// 생년월일
		request.setAttribute("R_120FNM", patientInfoRow.get("R_120FNM"));		// 의료기관명
		request.setAttribute("R_HNAM", mngNames);								// 담당검사자
		request.setAttribute("R_HNAMNO1", patientInfoRow.get("R_HNAMNO1"));		// 담당검사자 코드
		request.setAttribute("R_HLIC", mngDrs);									// 담당전문의
		request.setAttribute("R_LNAM", cnfmDrs);								// 확인전문의
		request.setAttribute("R_LLIC", repMdcDr);								// 대표의료원장
		request.setAttribute("R_ADDR_LIST", testingCenterAddr);					// 검사실시기관
		request.setAttribute("R_PJDT", strToFormatDate(pjdt));					// 검체접수일자
		request.setAttribute("R_PJTM", strToFormatTime(pjtm));					// 검체접수시간
		request.setAttribute("R_PSDT", strToFormatDate(Objects.toString(patientInfoRow.get("R_PSDT"),"")));			// 검사시행일자
		request.setAttribute("R_PBDT", strToFormatDate(Objects.toString(patientInfoRow.get("R_PBDT"),"")));			// 결과보고일자
		request.setAttribute("R_PBTM", strToFormatTime(pbtm));					// 결과보고시간
		
		// 검사결과 조회
		List<Map<String, Object>> covidResults = Collections.emptyList();
		procParam.clear();
		procParam.put("I_COR", "NML");
		procParam.put("I_UID", uid);
		procParam.put("I_IP", connIp);
		procParam.put("I_DAT", dat);
		procParam.put("I_JNO", jno);
		procParam.put("I_GCD", gcds);
		procParam.put("I_FOM", "COV");
		procParam.put("I_WTR", "A");
		try {
			covidResults = commonService.commonList("getCovid19Result", procParam);
		} catch(Exception ex) {
			throw new ResultImgException("조회 결과가 없습니다.", ex);
		}
		
		if (covidResults.size() <= 0) throw new ResultImgException("조회 결과가 없습니다.");
		
		request.setAttribute("COVID_RESULT_LIST",
				covidResults.stream()
				.map(result ->
					Pair.<String, String>of(this.matchInsptNameToLang(Objects.toString(result.get("R_FKN"),""), Objects.toString(result.get("R_GCD"),""), reportLang), Objects.toString(result.get("R_RST"),"")))
				.collect(Collectors.toList())
				);

		return "eng".equals(reportLang) ? "resultImg/verifyResultEng" : "resultImg/verifyResult";
	}

	private boolean isValidParam(Map<String, Object> param) {
		// sign 값의 유효성 check 및 접수일자, 접수번호 유효성 체크
		String sign = Utils.setSHA256(param.get("dat") + ":" + param.get("jno") + ":" + resultImgProp.get(ResultImgProp.ENCKEY));
		return sign.equals(param.get("sign"));
	}
	
	private String convertJnrToBirth(String jnr, String lang) {
		if ("eng".equals(lang)) {
			String[] arrJnr = jnr.split("-");
			if (arrJnr != null && arrJnr.length == 2) {
				if (arrJnr[1].startsWith("3") || arrJnr[1].startsWith("4")) {
					// 2000년대 출생자
					return LocalDate.parse("20" + arrJnr[0], SRC_BIRTH_FORMATTER).format(RESULT_BIRTH_FORMATTER);
				} else {
					// 1900년대 출생자
					return LocalDate.parse("19" + arrJnr[0], SRC_BIRTH_FORMATTER).format(RESULT_BIRTH_FORMATTER);
				}
				
			}
		} else {
			if (jnr.indexOf("*") >= 0) {
				return jnr.substring(0, jnr.indexOf("*"));
			}
		}
		
		return jnr;
	}
	
	private String strToFormatDate(String srcDate) {
		try {
			if (srcDate != null && srcDate.length() == 8) {
				return LocalDate.parse(srcDate, SRC_DT_FORMATTER).format(RESULT_DT_FORMATTER);
			} else {
				return "";
			}
		} catch(Exception ex) {
			logger.debug("Date: {}", srcDate);
			logger.debug(ex.getMessage());
			return "";
		}
	}
	
	private String strToFormatTime(String srcTime) {
		try {
			if (srcTime != null && srcTime.length() == 6) {
				return LocalTime.parse(srcTime, SRC_TM_FORMATTER).format(RESULT_TM_FORMATTER);
			} else {
				return "";
			}
		} catch(Exception ex) {
			logger.debug("Time: {}", srcTime);
			logger.debug(ex.getMessage());
			return "";
		}
	}
	
	private String getRequestIp(HttpServletRequest request) throws UnknownHostException {
    	String ip = request.getHeader("X-FORWARDED-FOR");
    	/** 사용자 IP 설정**/
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
        if("0:0:0:0:0:0:0:1".equals(ip)){
        	  ip = Inet4Address.getLocalHost().getHostAddress();
        }
        /** 사용자 IP 설정**/
        
        return ip;
	}
	
	private Map<String, Object> getReportGroup(String rcptDat, String rcptNo, String uid, String ip, String reportLang, List<Map<String, Object>> patientLists) {
		// 예: I_PARAM=|NML201705160555641377|NML201705160555641392|
		String combineParam = patientLists.stream().map(row -> "|NML" + rcptDat + rcptNo + Objects.toString(row.get("F600GCD"), "")).collect(Collectors.joining()) + "|";
		logger.debug("I_PARAM: {}", combineParam);

		final String LOGMNM = "결과지 출력";
		final String LOGMNU = "RSTREPORT";

		// MWGRPRPT 프로시저 조회 - /getGroup.do 호출 결과와 동일한 결과가 나오도록 구현
		// rstReport.jsp에서 /getGroup.do 호출하여 MWGRPRPT 프로시저 호출하는 로직 구현. /getGroup.do 호출 시 전달하는 parameter와 동일하게 설정
		Map<String, Object> rstGroupReportParam = new HashMap<>();
		rstGroupReportParam.put("I_PARAM", combineParam);
		rstGroupReportParam.put("I_REPORT_LANGUAGE", reportLang);		// 고정값
		rstGroupReportParam.put("I_UID", uid);		// 고정값
		rstGroupReportParam.put("I_IP", ip);	// Session에 기록된 접속 IP 주소
		
		rstGroupReportParam.put("I_LOGMNU", LOGMNU); 	// 고정값
		rstGroupReportParam.put("I_LOGMNM", LOGMNM);	// 고정값
		rstGroupReportParam.put("I_LOGEFG", "FD");		// 고정값
		rstGroupReportParam.put("I_IMGSAVE", "S");		// 고정값
		rstGroupReportParam.put("report_language", reportLang);		// 국문 - kor, 영문 - eng
		
		//생성할 검사코드의 정보 가져오기
		List<Map<String, Object>> resultGroupList = Collections.<Map<String, Object>>emptyList();
		try {
			resultGroupList = commonService.commonList("rstGroupReportList", rstGroupReportParam);
		} catch(Exception ex) {
			throw new ResultImgException("조회 결과가 없습니다.", ex);
		}
		
		Map<String, Object> resultGroupReport = (Map<String, Object>) reportController.setGroupReoport(resultGroupList,  rstGroupReportParam, false, "1");
		logger.debug("resultGroupReport : {}", resultGroupReport);
		
		if (!resultGroupReport.containsKey("resultList") || ((List<Map<String, Object>>)resultGroupReport.get("resultList")).size() <= 0) {
			throw new ResultImgException("조회 결과가 없습니다.");
		}
		
		return ((List<Map<String, Object>>)resultGroupReport.get("resultList")).stream()
			.filter(resultGroup -> "COV".equals(Objects.toString(resultGroup.get("F010RNO"), "").toUpperCase().trim()))
			.findFirst()
			.orElseThrow(() -> new ResultImgException("조회 결과가 없습니다."));

//		return ((List<Map<String, Object>>)resultGroupReport.get("resultList")).get(0);
	}

//	private String matchAgncAddrToLang(String agncAddr, String reportLang) {
//		if ("eng".equals(reportLang)) {
//			if (agncAddr.contains("11370319"))
//				return "Seegene Medical Foundation | No.11370319 | 320, Cheonho-daero, Seongdong-gu, Seoul | TEL: +82-1566-6500 | FAX: +82-2-3394-6503";
//			else if (agncAddr.contains("21349428"))
//				return "Seegene Medical Foundation | Busan,Gyeongnam Testing Center No.21349428 | 297, Jungang-daero, Dong-gu, Busan | TEL: +82-1566-6500";
//			else if (agncAddr.contains("37362101"))
//				return "Seegene Medical Foundation | Daegu,Gyeongbuk Testing Center No.37362101 | 2619, Dalgubeol-daero, Suseong-gu, Daegu | TEL: +82-1566-6500";
//			else if (agncAddr.contains("36325953"))
//				return "Seegene Medical Foundation | Gwangju,Honam Testing Center No.36325953 | 200, Hyou-ro, Nam-gu, Gwangju, Korea | TEL: +82-1566-6500";
//			else return agncAddr;
//		} else {
//			return agncAddr;
//		}
//	}
	
	private List<String> splitTestingCenterAddr(String addr, String reportLang) {
		
		if ("eng".equals(reportLang)) {
			if (addr.contains("11370319"))
				return Arrays.asList(
						"Seegene Medical Foundation",
						"No.11370319",
						"320, Cheonho-daero, Seongdong-gu, Seoul",
						"TEL: +82-1566-6500 | FAX: +82-2-3394-6503"
						);
			else if (addr.contains("21349428"))
				return Arrays.asList(
						"Seegene Medical Foundation",
						"Busan,Gyeongnam Testing Center No.21349428",
						"297, Jungang-daero, Dong-gu, Busan",
						"TEL: +82-1566-6500"
						);
			else if (addr.contains("37362101"))
				return Arrays.asList(
						"Seegene Medical Foundation",
						"Daegu,Gyeongbuk Testing Center No.37362101",
						"2619, Dalgubeol-daero, Suseong-gu, Daegu",
						"TEL: +82-1566-6500"
						);
			else if (addr.contains("36325953"))
				return Arrays.asList(
						"Seegene Medical Foundation",
						"Gwangju,Honam Testing Center No.36325953",
						"200, Hyou-ro, Nam-gu, Gwangju, Korea",
						"TEL: +82-1566-6500"
						);
			else return Arrays.asList(addr);
		} else {
			String contactStr = "";
			List<String> elements = new ArrayList<>();
			for (String el : addr.split("\\|")) {
				// TEL, FAX 정보는 한줄로 합쳐준다.
				if (el.toUpperCase().contains("TEL")) {
					contactStr = el.trim() + (contactStr.startsWith(" | ") ? "" : " | ") + contactStr;
				} else if (el.toUpperCase().contains("FAX")) {
					contactStr = contactStr + (contactStr.endsWith(" | ") ? "" : " | ") + el.trim();
				} else {
					elements.add(el.trim());
				}
			}
			
			if (StringUtils.isNotEmpty(contactStr)) elements.add(contactStr);
			return elements;
		}
	}
	
	private String matchInsptNameToLang(String insptName, String gcd, String reportLang) {
		if ("eng".equals(reportLang)) {
			if ("71330".equals(gcd) ||
					"71332".equals(gcd) ||
					"71334".equals(gcd) ||
					"71335".equals(gcd) ||
					"71336".equals(gcd) ||
					"71337".equals(gcd) ||
					"71338".equals(gcd) ||
					"71339".equals(gcd) ||
					"71340".equals(gcd) ||
					"71346".equals(gcd) ||
					"71349".equals(gcd) ||
					"71350".equals(gcd) )
			{
				return "SARS-CoV-2 (Upper Respiratory)";
			} else if ("71331".equals(gcd) ||  "71351".equals(gcd) ) {
				return "SARS-CoV-2 (Lower airway)";
			} else if ("71341".equals(gcd)) {
				return "Respiratory virus tests";
			} else {
				return insptName;
			}
		} else {
			return insptName;
		}
	}

	@ExceptionHandler(ResultImgException.class)
	public String exHandler(ResultImgException ex, HttpServletRequest request) {
		request.setAttribute("ERR_CODE", ex.getErrCode());
		request.setAttribute("ERR_MSG", ex.getMessage());
		return "resultImg/error";
	}
	
	
	
	
	/*
	 * 2021-12-28
	 * 만안구보건소, 강남구보건소에서 App을 통해 result Image 보기/다운 기능 제공을 위함
	 * 
	 */
	@RequestMapping(value="/bogunResultInqForm.do", method=RequestMethod.GET)
	public String gobogunResultInqForm(HttpServletRequest request, HttpSession session) {
		
		/*****************************************
		 ******** URL 에 포함된 데이터 정합성 확인 
		 *****************************************/
		
		Map<String, Object> param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
				
		if (param.containsKey("sign")) {
			// 인증절차 진행
			if (this.isValidParam(param)) {
				session.setAttribute("JSIGN", param.get("sign"));
				session.setAttribute("JDAT", param.get("dat"));
				session.setAttribute("JNO", param.get("jno"));
				session.setAttribute("HOS", Objects.toString(param.get("hos"), ""));	// 병원코드. 없을 수 있음.
				
				if (session.getAttribute("UID") == null || StringUtils.isEmpty(Objects.toString(session.getAttribute("UID"), ""))) {
					session.setAttribute("UID", "APP_bogunIMG");
					try {
						session.setAttribute("IP", StringUtils.defaultString(this.getRequestIp(request)));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						session.setAttribute("IP", "");
					}
				}
			} else {
				throw new ResultImgException("INVALID_ENCKEY", "요청 URL이 유효하지 않습니다.");
			}
		} else {
			throw new ResultImgException("요청 URL이 유효하지 않습니다.");
		}	
			
		
		
		/*****************************************
		 ******** param 값을 통해 환자 검사정보를 찾아
		 ******** 이미지결과로 view 되도록 호출 
		 *****************************************/
		
		Map<String, Object> reqParam = Utils.getParameterMap(request);	// request에 전달된 parameter 데이터를 Map 형태로 변경
		logger.debug("request param : {}", reqParam);
		
		String sign = Objects.toString(session.getAttribute("JSIGN"), "");
		String dat = Objects.toString(session.getAttribute("JDAT"), "");
		String jno = StringUtils.isNotEmpty(Objects.toString(session.getAttribute("JNO"), "")) ? String.format("%5s", Objects.toString(session.getAttribute("JNO"), "")).replaceAll(" ", "0") : "";
		String hos = Objects.toString(session.getAttribute("HOS"));
		logger.debug("sign:{}, dat:{}, jno:{}", sign, dat, jno);
		
		if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(dat) || StringUtils.isEmpty(jno)) {
			throw new ResultImgException("시간이 경과하여 세션이 종료되었습니다.\nSMS 메시지로 제공한 link를 통해 다시 접속하시기 바랍니다.");
		}
		
		String reqCustName = Objects.toString(reqParam.get("custName"), "");
		String reqMobileNo = Objects.toString(reqParam.get("custMobileNo"), "");
		String reqBirthday = Objects.toString(reqParam.get("custBirthday"), "");

		// 요청 key 값으로 접수일자, 접수번호, 검사항목코드, 병원코드, 환자 개인정보 조회
		Map<String, Object> procParam = new HashMap<>();
		procParam.put("I_HOS", hos);
		procParam.put("I_DAT", dat);
		procParam.put("I_JNO", jno);
		procParam.put("I_SIGN", sign);
		List<Map<String, Object>> resultList = Collections.<Map<String, Object>>emptyList();
		try {
			// 2021.12.02 - 기존 프로시저 호출 시간 딜레이로 인한 프로시저 수정
			resultList = commonService.commonList("rstGroupCoronaResult", procParam);
		} catch(Exception ex) {
			throw new ResultImgException("조회 결과가 없습니다.", ex);
		}

		if (resultList.size() <= 0) {
			throw new ResultImgException("조회 결과가 없습니다.");
		}

		// 조회 결과에서 개인정보 입력폼에서 입력한 개인정보와 일치하는 정보 찾기
		/*List<Map<String, Object>> resultRows = resultList.stream()
			.filter(row ->
						dat.equals(Objects.toString(row.get("S100DAT"), "").trim()) &&
						jno.equals(String.format("%5s", Objects.toString(row.get("S100JNO"), "").trim()).replaceAll(" ", "0"))  &&
						reqCustName.equals(Objects.toString(row.get("F100NAM"), "").trim()) &&
						reqMobileNo.equals(Objects.toString(row.get("S100PNO"), "").trim()) &&
						reqBirthday.equals(Objects.toString(row.get("F100BDT"), "").trim()) &&
						sign.equals(Objects.toString(row.get("S100TMP8"), "").trim())  )
			.collect(Collectors.toList());*/
		List<Map<String, Object>> resultRows = resultList;
		if (resultRows.size() <= 0) {
			throw new ResultImgException("요청하신 데이터를 찾을 수 없습니다.");
		}
		logger.debug("CORONA_RESULT search result : {}", resultRows.size());
		
		// rstGroupReportList (MWGRPRPT 프로시저) 호출 및 report Grouping 처리 
		// 예: I_PARAM=|NML201705160555641377|NML201705160555641392|
		//String combineParam = resultRows.stream().map(row -> "|NML" + dat + jno + Objects.toString(row.get("F600GCD"), "")).collect(Collectors.joining()) + "|";
		//logger.debug("I_PARAM: {}", combineParam);

		final String LOGMNM = "결과지 출력";
		final String LOGMNU = "RSTREPORT";

		// 질병청 요청으로 국문만 출력하도록 변경됨에 따라 언어 param 값을 "kor"로 고정 - 20211108 최재원 대리 변경 요청
		Map<String, Object> resultGroupMap = this.getReportGroup(dat, jno, Objects.toString(session.getAttribute("UID"), ""), Objects.toString(session.getAttribute("IP"), ""), "kor", resultRows);
		
		// TODO 홍재훈 - QRCode 생성 로직 시작 -------------------------------------
		logger.debug("F010RNO: {}", Objects.toString(resultGroupMap.get("F010RNO"), ""));
		if ("COV".equals(Objects.toString(resultGroupMap.get("F010RNO"), "").toUpperCase().trim())) {
			try {
				// 질병청 요청으로 국문만 출력하도록 변경됨에 따라 언어 param 값을 "kor"로 고정 - 20211108 최재원 대리 변경 요청
				final String appRoot = request.getSession().getServletContext().getRealPath("/");
				QrCodeUtils.makeQrCode(dat, jno, hos, "kor", sign, resultImgProp, appRoot);
			} catch(Exception ex) {
				StringWriter error = new StringWriter();
	            ex.printStackTrace(new PrintWriter(error));
	            if (logger.isErrorEnabled()) {
	                logger.error("QRCode 생성 실패 : {}", error.toString());
	            }
	            // QRCode 생성이 실패하더라도 결과지는 정상적으로 출력이 되어야 하므로 log만 남기고 나머지 로직 정상적으로 실행
			}
		}
		// TODO 홍재훈 - QRCode 생성 로직 종료 -------------------------------------
		
		// Crownix properties 정보 조회
		Map<String, String> crownixInfo = commonController.getCrownixInfo();
		
		// crownixViewer 호출위한 parameter 설정
		request.setAttribute("SMS_LINK_REQ", "Y");
		request.setAttribute("viewerUrl", crownixInfo.get("viewerUrl"));
		request.setAttribute("mrd_path", resultGroupMap.get("S018RFN") + ".mrd");
		request.setAttribute("rdServerSaveDir", crownixInfo.get("rdSaveDir"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("/rf [").append(crownixInfo.get("rdAgentUrl")).append("]")
			.append(" /rpprnform [A4]")
			.append(" /rv COR[NML]")
			.append("UID[")
				.append(session.getAttribute("UID"))
			.append("]UIP[")
				.append(session.getAttribute("IP"))
			.append("]")
			.append("JDAT[")
				.append(Objects.toString(resultGroupMap.get("F600DAT"), ""))
			.append("]JNO[")
				.append(Objects.toString(resultGroupMap.get("F600JNO"), ""))
			.append("]")
			.append("GCD[")
				.append(Objects.toString(resultGroupMap.get("P_GCD"), ""))
			.append("]RFN[")
				.append(Objects.toString(resultGroupMap.get("F010RNO"), ""))
			.append("]WTR[A]")
			.append("SYSURL[")
				.append(crownixInfo.get("trmsSystemUrl"))
			.append("]PG_TIT[")
				.append(Objects.toString(resultGroupMap.get("PG_TIT"), ""))
			.append("]")
			.append("PGCD[")
				.append(Objects.toString(resultGroupMap.get("PGCD"), ""))
			.append("]TMP[")
				.append(Objects.toString(resultGroupMap.get("S018RFN"), ""))
			.append("]");
		
		String dpi300HosGubun = Objects.toString(resultGroupMap.get("DPI300_HOS"), "");
		if ("714".equals(dpi300HosGubun) || "300".equals(dpi300HosGubun)) {
			sb.append(" /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [300] /rimageydpi [300] /p /rusesystemfont");
		} else {
			sb.append(" /rimageopt render [1] /rsaveopt [4288] /rimagexdpi [110] /rimageydpi [110] /p /rusesystemfont");
		}
		request.setAttribute("param", sb.toString());
		
		request.setAttribute("systemDownDir", crownixInfo.get("rdSysSaveDir") + "/" + Objects.toString(resultRows.get(0).get("S100HOS"), ""));
		request.setAttribute("downFileName", Objects.toString(resultGroupMap.get("FILE_NM"), ""));
		
		Map<String, String> imgObj = new HashMap<>();
		imgObj.put("I_LOGMNM", 	LOGMNM);			// 고정값
		imgObj.put("I_LOGMNU", 	LOGMNU);		// 고정값
		imgObj.put("I_HOS", 	Objects.toString(resultRows.get(0).get("S100HOS"), ""));		// 병원코드
		imgObj.put("F600DAT", 	Objects.toString(resultGroupMap.get("F600DAT"), ""));	// 접수일자
		imgObj.put("F600JNO", 	Objects.toString(resultGroupMap.get("F600JNO"), ""));	// 접수번호
		imgObj.put("P_GCD", 	Objects.toString(resultGroupMap.get("PGCD"), ""));		// 검사코드
		imgObj.put("S018RFN", 	Objects.toString(resultGroupMap.get("S018RFN"), ""));	// 검사 결과지 폼
		imgObj.put("FILE_NM", 	Objects.toString(resultGroupMap.get("FILE_NM"), ""));	// 파일명
		imgObj.put("F600BDT", 	Objects.toString(resultGroupMap.get("BDT"), ""));		// 보고일자
		imgObj.put("I_LOGEFG", 	"RP");		//이벤트 종류E VT_FLAG : (RP출력)
		
		logger.debug("imgObj : {}", JSONValue.toJSONString(imgObj));
		request.setAttribute("imgObj", JSONValue.toJSONString(imgObj));
		
		request.setAttribute("dpi300_hos_gubun", dpi300HosGubun);
		request.setAttribute("crownixVeiwer_print_YN", "STSD");		// STSD : 미리보기, STSD_PRINT : 인쇄
		// 질병청 요청으로 국문만 출력하도록 변경됨에 따라 언어 param 값을 "kor"로 고정 - 20211108 최재원 대리 변경 요청
		request.setAttribute("report_language", "kor");	// 국문 - kor, 영문 - eng
		
		return "report/mobCrownixViewer";
	}
}

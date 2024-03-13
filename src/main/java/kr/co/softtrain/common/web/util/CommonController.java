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
package kr.co.softtrain.common.web.util;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.softtrain.resultImg.web.ResultImgException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.mail.smtp.SMTPTransport;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;
import kr.co.softtrain.resultImg.web.AES256;
import kr.co.softtrain.resultImg.web.QrCodeUtils;
import kr.co.softtrain.resultImg.web.ResultImgProp;
import org.springframework.web.servlet.HandlerMapping;


/**
 * 
 * <pre>
 * kr.co.softtrain.common.web.util
 * CommonController.java
 * </pre>
 * @title :  
 * @author : ejlee
 * @since : 2018. 11. 21.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 21.		ejlee  			  최초생성
 * 
 * </pre>
 */
@Controller
public class CommonController  {

	Logger logger = LogManager.getLogger();
    
	@Resource(name = "commonService")
	private commonService commonService;
	
	private static commonService commonService2;
	
	private final AES256 aes256 = new AES256();
	 
	@Resource
	public void setCommonService2(commonService commonService) {
		CommonController.commonService2 = commonService;
	}

    @Autowired  
    private MessageSource messageSource;
    
    @Autowired  
    private MessageSource messageSourceEng;

    // TODO 홍재훈 - QRCode 생성 로직 추가
	@Autowired
	private ResultImgProp resultImgProp;

	/**
	 * @Method Name	: index
	 * @author	: ejlee
	 * @version : 1.0
	 * @since	: 2018. 10. 31.
	 * @param request
	 * @param session
	 * @return 
	 * @ReturnType : String
	 */
	@RequestMapping(value = "/index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goLogin(HttpServletRequest request, HttpSession session){
		String str = "";		
		str = "index";
		return str;
	}
	@RequestMapping(value = "/rstReport.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstReportn(HttpServletRequest request, HttpSession session) throws Exception{
		
		// ================== 결과지출력 버튼 국문/영문 구분자 param setting : start ==================
		
		// 2020.06.01 - 결과지출력 버튼 국문/영문 구분자
		String param_languege = ""; 
		
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()){
		    String name = (String)params.nextElement();
		    //System.out.println(name + " : " +request.getParameter(name));
		    
		    if(request.getParameter(name).indexOf("eng") > -1){
		    	param_languege = "eng";
		    }
		    else if(request.getParameter(name).indexOf("kor") > -1){
		    	param_languege = "kor";
		    }else{
		    	param_languege = "";
		    }
		}
		
		request.setAttribute("report_language", param_languege);
		// ================== 결과지출력 버튼 국문/영문 구분자 param setting : end ==================
		
		String str = "rst/rstReport";
		return str;
	}
	@RequestMapping(value = "/rstReport_mini.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String goRstReportn_mini(HttpServletRequest request, HttpSession session) throws Exception{
		String str = "rstMini/rstReport_mini";
		return str;
	}
    
	/**
	 * @Method Name	: comm_fileDown_back20181218
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/comm_fileDown_back20181218.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String comm_fileDown_back20181218(HttpServletRequest request, HttpSession session){
		String str = "common/fileDown";
		return str;
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
	@RequestMapping(value = "/comm_fileDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void dofileDown(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException{
		
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

				
		//파일 업로드된 경로
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + request.getParameter("file_path") ;
		 
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
		
		String encodedFilename = Utils.downloadEncFileForBrowser(userAgent, fileNm);

		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);

		if (userAgent.indexOf("Opera") > -1 ){

		    response.setContentType("application/octet-stream;charset=UTF-8");

		}

//		System.out.println("savePath   === "+savePath);
//		System.out.println("fileNm   === "+fileNm);
//		System.out.println("encodedFilename   === "+encodedFilename);
//		System.out.println("file   === "+file);
		

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
	@RequestMapping(value = "/comm_fileDown2.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void dofileDown2(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException{

		String CHARSET = "EUC-KR";
		
		request.setCharacterEncoding(CHARSET);
		response.setContentType("text/html;charset="+CHARSET);
		
		
		//파일 업로드된 경로
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + request.getParameter("file_path") ;
		
		// 실제 내보낼 파일명
		
		
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
		String fileNm = request.getParameter("file_name");
		//String fileNm = URLEncoder.encode(request.getParameter("file_name"), CHARSET);
		//fileNm = fileNm.replaceAll("\\+", "%20"); 
		
		String encodedFilename = Utils.downloadEncFileForBrowser(userAgent, fileNm);

		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);
		
		if (userAgent.indexOf("Opera") > -1 ){
			
			response.setContentType("application/octet-stream;charset="+CHARSET);
			
		}
		
//		System.out.println("savePath   === "+savePath);
//		System.out.println("fileNm   === "+fileNm);
//		System.out.println("encodedFilename   === "+encodedFilename);
//		System.out.println("file   === "+file);
		
		
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
	
	/**
	 * @Method Name	: doReportfileDown_back20181218
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/comm_reportFileDown_back20181218.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String doReportfileDown_back20181218(HttpServletRequest request, HttpSession session){
		String str = "common/reportFileDown";
		return str;
	}
	
	
	/**
	 * @Method Name	: comm_reportFileDown
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/comm_reportPDFJPGFileDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void comm_reportPDFJPGFileDown(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException{

		Utils utils = new Utils();
		
		//리포트 서버의 파일 업로드된 경로 
		String rdServerSaveDir = request.getParameter("rdServerSaveDir") ;
		String rdFilePathName = request.getParameter("rdFilePathName") ;

		String systemDownDir = request.getParameter("systemDownDir") ;

		//logger.debug("systemDownDir:: "+systemDownDir);
		
		String file_ext = request.getParameter("file_ext") ;

		String file_name = request.getParameter("file_name");

		//logger.debug("file_name:: "+file_name);

		//file_name = new String(file_name.getBytes("8859_1"), "UTF-8");

		//logger.debug("file_name22:: "+file_name);

		//file_name = file_name.replaceAll("\\+", "%20");


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


		/***************************Crownix Sever의 파일 System으로 이동 시작************************************/
		//파일 업로드된 경로
		String savePath = "";
		String rdServerSavePath = rdServerSaveDir;
		if("PDF".equals( file_ext.toUpperCase() )){//PDF 파일 Crownix 서버에서 System 서버로 이동
			rdServerSavePath = rdServerSaveDir + "/"+rdFilePathName ;
			savePath = utils.downCrownixPDFFile(rdServerSavePath, downFilePath, file_name+".pdf");
		}else if("JPG".equals( file_ext.toUpperCase() )){//JPG 파일들을 Crownix 서버에서 System 서버로 하나의 jpg로 합치고 이동
			savePath = utils.downCrownixPDFtoJPGFile(rdServerSavePath, downFilePath, file_name);
			
			
			//savePath = Utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name);
		}

		int size = 10240;
		int byteWritten = 0;



		/***************************리포트 파일 다운로드 시작************************************/

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

		file_name = request.getParameter("file_name")+"."+file_ext;
		
		String encodedFilename = Utils.downloadEncFileForBrowser(userAgent, file_name);

		//logger.debug("userAgent="+userAgent);

		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);

		if (userAgent.indexOf("Opera") > -1 ){

		    response.setContentType("application/octet-stream;charset=UTF-8");

		}
		/*******************브라우저에 따른 한글 깨짐 처리 끝*************************/

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
		/***************************리포트 파일 다운로드 끝************************************/
		
		
	}
	
	
	/**
	 * @Method Name	: comm_reportFileDown
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
	@RequestMapping(value = {"/comm_reportFileDown.do", "sms_reportFileDown.do"}, method = {RequestMethod.GET, RequestMethod.POST})
	public void doReportfileDown(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException, InterruptedException{
		// comm_reportFileDown.do, sms_reportFileDown.do 합침.
		// 기존 sms_reportFileDown 에서는 앞에 세션 체크 하는 부분 존재. saveMCWRKIMG 처리부분 없었으나 로그 저장하기 위해 이제 있어야 함.
		String reqUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		// sms_reportFileDown.do 에서 가져옴.
		if(reqUrl.equals("/sms_reportFileDown.do")) {
			String sign = Objects.toString(session.getAttribute("JSIGN"), "");
			String dat = Objects.toString(session.getAttribute("JDAT"), "");
			String jno = StringUtils.isNotEmpty(Objects.toString(session.getAttribute("JNO"), "")) ? String.format("%5s", Objects.toString(session.getAttribute("JNO"), "")).replaceAll(" ", "0") : "";
			logger.debug("sign:{}, dat:{}, jno:{}", sign, dat, jno);

			if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(dat) || StringUtils.isEmpty(jno)) {
				throw new ResultImgException("시간이 경과하여 세션이 종료되었습니다.\nSMS 메시지로 제공한 link를 통해 다시 접속하시기 바랍니다.");
			}
		}

		// request recvObj 를 param 으로 세팅 I_HOS 등 saveMCWRKIMG 에 쓸 값이 있음.
		Map<String, Object> param = new HashMap<String, Object>();
		String recvObj = request.getParameter("recvObj");

		if (StringUtils.isNotEmpty(recvObj)){
			List<Map<String, Object>>  printParamList =  Utils.jsonList(recvObj);	// 데이터 Parameter 상태로 변경

			if (printParamList.size() >= 0) {
				param = printParamList.get(0);
			}
		}

		Utils utils = new Utils();

		String CHARSET = "UTF-8";

		//리포트 서버의 파일 업로드된 경로 
		String rdServerSaveDir = request.getParameter("rdServerSaveDir") ;
		String rdFilePathName = request.getParameter("rdFilePathName") ;

		String systemDownDir = URLDecoder.decode(request.getParameter("systemDownDir"), CHARSET) ;
		//logger.debug("systemDownDir:: "+systemDownDir);

		String file_name = URLDecoder.decode(request.getParameter("file_name"), CHARSET) ;
		String dpi300_hos_gubun = request.getParameter("dpi300_hos_gubun");
		String imgEachHos = request.getParameter("imgEachHos");

		//logger.debug("file_name:: "+file_name);

		//file_name = new String(file_name.getBytes("8859_1"), "UTF-8");

		//logger.debug("file_name22:: "+file_name);

		//file_name = file_name.replaceAll("\\+", "%20");

		String file_ext = request.getParameter("file_ext") ;

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
		// saveMCWRKIMG 에서 체크하기 위해 param에 저장.
		param.put("downFilePath", downFilePath);


		/***************************Crownix Sever의 파일 System으로 이동 시작************************************/
		//파일 업로드된 경로
		String savePath = "";

		if("PDF".equals( file_ext.toUpperCase() )){//PDF 파일 Crownix 서버에서 System 서버로 이동
			String rdServerSavePath = rdServerSaveDir + "/"+rdFilePathName ;
			savePath = utils.downCrownixPDFFile(rdServerSavePath, downFilePath, file_name);
		}else if("JPG".equals( file_ext.toUpperCase() )){//JPG 파일들을 Crownix 서버에서 System 서버로 하나의 jpg로 합치고 이동
			
			if(!"".equals(dpi300_hos_gubun) && "714".equals(dpi300_hos_gubun)){
				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name, dpi300_hos_gubun, imgEachHos);
			}else{
				//savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name);
				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name, imgEachHos);
			}
		}

		// 압축파일(zip)일 경우에는 파일안에 있는 zip 파일 리스트를 가져와서 db에 저장한다.
		if (savePath.endsWith(".zip")) {
			param.put("file_name",getFileNameInZip(savePath));
		}else{
			param.put("file_name",file_name);
		}

		// pdf 일떄는 저장하지 않음. -> PDF제외 로직은 WEBOBJLIB.MCWRKIMGC 프로시저에서 처리
		//if (!savePath.endsWith(".pdf")) {
			try {
				saveMCWRKIMG(param);
			} catch (Exception e) {
				logger.error("saveMCWRKIMG 저장 실패 : {}", e.toString());
			}
		//}
		int size = 10240;
		//int byteWritten = 0;

		/***************************리포트 파일 다운로드 시작************************************/

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
		int BUFFER_SIZE = size; 			// 10kb
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


		String encodedFilename = Utils.downloadEncFileForBrowser(userAgent, file_name);

		//logger.debug("userAgent="+userAgent);


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
		/*******************브라우저에 따른 한글 깨짐 처리 끝*************************/

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
		/***************************리포트 파일 다운로드 끝************************************/
	}
	
	/**
	 * @Method Name	: doCrownixViewer
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/crownixViewer.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String doCrownixViewer(HttpServletRequest request, HttpSession session) throws Exception{
		// TODO 홍재훈 - QRCode 생성 로직 시작
		// Covid19 결과지일때만 QRCode 생성하는 로직 추가
		String imgObj = request.getParameter("imgObj");
	  	if (StringUtils.isNotEmpty(imgObj)){
	  		List<Map<String, Object>>  printParamList =  Utils.jsonList(imgObj);	// 데이터 Parameter 상태로 변경
	  	
		  	if (printParamList.size() >= 0) {
		  		Map<String, Object> param = printParamList.get(0);
		  		logger.debug("F010RNO: {}, reportLang: {}", Objects.toString(param.get("F010RNO"), ""), Objects.toString(request.getParameter("report_language"), ""));
		  		if ("COV".equals(Objects.toString(param.get("F010RNO"), "").toUpperCase().trim())) {
					String dat = Objects.toString(param.get("F600DAT"), "");
					String jno = Objects.toString(param.get("F600JNO"), "");
					String sign = Utils.setSHA256(dat + ":" + jno + ":" + resultImgProp.get(ResultImgProp.ENCKEY));
					
					try {
						final String appRoot = request.getSession().getServletContext().getRealPath("/");
						QrCodeUtils.makeQrCode(dat, jno, Objects.toString(param.get("I_HOS"), ""), StringUtils.defaultIfBlank(request.getParameter("report_language"), "kor"), sign, resultImgProp, appRoot);
					} catch (Exception ex) {
						StringWriter error = new StringWriter();
						ex.printStackTrace(new PrintWriter(error));
						if (logger.isErrorEnabled()) {
							logger.error("QRCode 생성 실패 : {}", error.toString());
						}
						// QRCode 생성이 실패하더라도 결과지는 정상적으로 출력이 되어야 하므로 log만 남기고 나머지 로직 정상적으로 실행
					}
		  		}
		  	}
	  	}
		// TODO 홍재훈 - QRCode 생성 로직 종료
	  	
		String str = "report/crownixViewer";
		return str;
	}
	
	
	/**
	 * @Method Name	: CallMessage - 국문
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
	@RequestMapping(value = "/CallMessage.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
	public Object CallMessage(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	
	  	
	  	Object[] args = new Object[1];
	  	
	  	if(param.containsKey("arguments[]")){
	  		args =  request.getParameterValues("arguments[]");
	  	}
	  	
	  	logger.debug("MSGCOD   ==== "+Utils.isNull(param.get("MSGCOD"),"999"));
	  	
    	param.put("strMessage", messageSource.getMessage(param.get("MSGCOD").toString(), args, "해당하는 메세지가 없습니다.\n["+param.get("MSGCOD")+"]",null));
		
    	return param;
	}
	
	/**
	 * @Method Name	: CallMessage - 영문
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
	@RequestMapping(value = "/CallMessageEng.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
	public Object CallMessageEng(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	param = Utils.getParameterMap(request);	
	  	
	  	Object[] args = new Object[1];
	  	
	  	if(param.containsKey("arguments[]")){
	  		args =  request.getParameterValues("arguments[]");
	  	}
	  	
	  	logger.debug("MSGCOD   ==== "+Utils.isNull(param.get("MSGCOD"),"999"));
	  	
    	param.put("strMessage", messageSourceEng.getMessage(param.get("MSGCOD").toString(), args, "해당하는 메세지가 없습니다.\n["+param.get("MSGCOD")+"]",null));
		
    	return param;
	}
	
	
	
	/**
	 * @Method Name	: mailSender
	 * @see
	 * <pre>
	 * Method 설명 : 비밀번호초기화메일전송
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/mailSender.do" , method = {RequestMethod.GET, RequestMethod.POST} , produces="application/json;charset=UTF-8")
	public Object mailSender(HttpServletRequest request, HttpSession session) throws AddressException, MessagingException,Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		String newPass = NewPassword();
		
		param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		param.put("I_LOGCOR", request.getParameter("LOGCOR"));
		param.put("I_LOGLID", request.getParameter("LOGLID"));
		param.put("I_PASASW", sha256(newPass));	//SHA256 암호화된 패스워드
		
		param.put("I_EKD", request.getParameter("I_EKD"));
		param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
		param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
		
//    	if("".equals(param.get("I_EKD"))){
//    		NPWD
//    	}
//    	
//    	//LOGEML
		
		commonService.commonUpdate("sysUserMngpwd", param);
        param.remove("I_PASASW");
//
		Object getMail = commonService.commonOne("getMail", param);
		
		param.put("I_TMA", param.get("LOGEML"));   //--수신자 이메일 주소      
		param.put("I_TNM", param.get("F120FNM"));   //--수신자 명                      
		param.put("I_CMA","");   //--참조 이메일 주소          
		param.put("I_CNM","");   //--참조 명
		
		//O_HED
		logger.debug(param);
		param.put("I_FMA", param.get("O_FNM"));   //--송신 이메일 주소          
		param.put("I_FNM", param.get("O_FSNM"));   //--송신자 명                      
		
		// 메일 내용 입력해주세요. === start ===
		String strPsd = "아이디 : " + request.getParameter("LOGLID")+"<br>";
		strPsd += "비밀번호 : "+newPass+"<br>"; 
		// 메일 내용 입력해주세요. === end ===
		
		StringBuffer strMail = new StringBuffer();
		
		strMail.append(param.get("O_HED").toString());
		strMail.append("\n<div>" +strPsd+"<div>\n");
		strMail.append(param.get("O_FOT").toString());
		
		logger.debug("   strMail                    "+strMail.toString());
		
		
		param.put("I_TIT", param.get("O_TIT"));  //--내용               
		param.put("I_CONT",strMail.toString());  //--내용                  
		
		logger.debug(param);
		
		sendMail(param);
		
		param.put("I_CONT","");  //--내용                  
    	return param;
	}


	public Object sendMail(Map<String, Object> param) throws  Exception
	{
		String charset = "utf-8";
		/** 메일 정보 설정 **/
		Properties prop = new Properties();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL url = cl.getResource("META-INF/property/mail.properties");
			
		try {
			prop.load(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String mailType =  prop.getProperty("mail.type");
		String realHost =  prop.getProperty("mail.real.Host");
		String realUserMail =  prop.getProperty("mail.real.UserMail");
		String realpassword =  prop.getProperty("mail.real.password");
		String realport =  prop.getProperty("mail.real.port");
		
		String  testHost    =    prop.getProperty("mail.test.Host");
		String  testUserMail    =    prop.getProperty("mail.test.UserMail");
		String  testpassword    =    prop.getProperty("mail.test.password");
		String  testToMail    =    prop.getProperty("mail.test.toMail");
		String  testport    =    prop.getProperty("mail.test.port");
		/** 메일 정보 설정 **/
		
		// 네이버일 경우 smtp.naver.com 을 입력합니다.
		// Google일 경우 smtp.gmail.com 을 입력합니다.
		
		String host = "";
		int port=465; //포트번호
		//메일 host / port 설정 
		if("REAL".equals(mailType)){
			host = realHost;
			port = Integer.parseInt(realport);
		}else{
			host = testHost;
			port = Integer.parseInt(testport);
		}
		
		Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성
		
		// SMTP 서버 정보 설정
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");	// 2022-04-07 , 이메일서버 변경으로 인한 ssl인증 변경
		props.put("mail.smtp.ssl.enable", "true");			// 2022-04-07 , 이메일서버 변경으로 인한 ssl인증 변경
		
		//Session 생성
		Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String un="";
			String pw="";
			
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				// 메일 로그인 아이디 비밀번호 설정
				if("REAL".equals(mailType)){
					un=realUserMail;
					pw=realpassword;
				}else{
					un=testUserMail;
					pw=testpassword;
				}
//				System.out.println("un"+un);
//				System.out.println("pw"+pw);
				return new javax.mail.PasswordAuthentication(un, pw);
			}
		});

		System.out.println(mailType);
		if("REAL".equals(mailType)){
			mailSession.setDebug(false); //for debug
		}else{
			mailSession.setDebug(true); //for debug
//			mailSession.setDebug(false); //for debug
		}
		try{
			
			MimeMessage mimeMessage = new MimeMessage(mailSession); //MimeMessage 생성
			mimeMessage.setFrom(new InternetAddress(param.get("I_FMA").toString(), param.get("I_FNM").toString())); //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
			/********** 메일 다수로 보낼때 설정 
			 * InternetAddress[] toAddr = new InternetAddress[메일 전송 수량]; 
			 * toAddr[0] = new InternetAddress("메일 주소","받는사람 명"); 
			 * toAddr[1] = new InternetAddress("메일 주소","받는사람 명"); 
			 *  **************/ 
			InternetAddress[] toAddr = new InternetAddress[1]; 
	
			if("REAL".equals(mailType)){
				toAddr[0] = new InternetAddress(param.get("I_TMA").toString(), param.get("I_TNM").toString()); 
				mimeMessage.setRecipients(Message.RecipientType.TO, toAddr); //수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
			}else{
				toAddr[0] = new InternetAddress (testToMail, param.get("I_TNM").toString(), charset); 
				mimeMessage.setRecipients(Message.RecipientType.TO, toAddr); //수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
			}
			mimeMessage.setSubject(MimeUtility.encodeText(param.get("I_TIT").toString(), charset, "B"));  //제목셋팅
			
			mimeMessage.setContent(param.get("I_CONT").toString(), "text/html; charset=\"" + charset + "\"");
			mimeMessage.setSentDate(new Date());
	
		    try {
				mimeMessage.setHeader("Content-Transfer-Encoding", "7bit");
				
				SMTPTransport transport = (SMTPTransport)mailSession.getTransport("smtp");
	            transport.connect(host, testUserMail, testpassword);
	            
	            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
				transport.close();
		        param.put("I_SYN","Y");  
		    	param.put("I_FMS",""); 
		    } catch(Exception e){
		    	param.put("I_SYN","N");  
		    	param.put("I_FMS",e.toString());  
		    }
	    
			commonService.commonInsert("saveMailLog", param);
						
			if("N".equals(param.get("I_SYN"))){
				param.put("O_MSGCOD","244");  
			}
		} catch(Exception e){
//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	    }
    	return param;
	}



	public  String NewPassword(){
		String  dummyPW = "";

	    List<String> dummylist=new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		StringBuffer sc = new StringBuffer("!@#$^*");  // 특수문자 모음, {}[] 같은 비호감문자는 뺌
		
//		// 대문자 1개를 임의 발생 
		char dummyChar;
		for( int i = 0; i<1; i++) {
			dummyChar = (char)((Math.random() * 26)+65);
			sb.append(dummyChar);  // 아스키번호 65(A) 부터 26글자 중에서 택일
			dummylist.add(String.valueOf(dummyChar));
		} 
		
		// 소문자 1개를 임의발생
		for( int i = 0; i<1; i++) {
			dummyChar = (char)((Math.random() * 26)+97); // 아스키번호 97(a) 부터 26글자 중에서 택일
			sb.append(dummyChar);  						
			dummylist.add(String.valueOf(dummyChar));
		}  

		// 숫자 2개를 임의 발생
		for( int i = 0; i<3; i++) {
			dummyChar = (char)((Math.random() * 10)+48);//아스키번호 48(1) 부터 10글자 중에서 택일
			sb.append(dummyChar);  // 아스키번호 65(A) 부터 26글자 중에서 택일
			dummylist.add(String.valueOf(dummyChar));
		}

		dummyChar = sc.charAt((int)(Math.random()*sc.length()-1)); // 특수문자 랜덤 생성
		sb.append(dummyChar); 
		dummylist.add(String.valueOf(dummyChar));
		
		// 특수문자를 두개  발생시켜 랜덤하게 중간에 끼워 넣는다 
//		sb.setCharAt(((int)(Math.random()*2)+1), sc.charAt((int)(Math.random()*sc.length()-1))); //대문자3개중 하나   
//		sb.setCharAt(((int)(Math.random()*4)+4), sc.charAt((int)(Math.random()*sc.length()-1))); //소문자4개중 하나
		
	    Collections.shuffle(dummylist); 
		
	    for (String string : dummylist) { 
	    	dummyPW+=string;
	    }
	    
		return dummyPW;
	}
	
	/**
	 * @Method Name	: getCrownixInfo
	 * @author	: hong
	 * @version : 1.0
	 * @since	: 2018. 12. 14.
	 * @see : 
	 * @return
	 * @throws IOException 
	 * @return String:crownixUrl
	 */
	public HashMap<String, String> getCrownixInfo(){
		
		String crownixUrl = "";
		Properties prop = new Properties();
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		URL url = cl.getResource("META-INF/property/crownix.properties");
			
		try {
			prop.load(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String rdUrl =  prop.getProperty("crownix.url");
		String viewerUrl =  prop.getProperty("crownix.viewer.url");
		String rdAgentUrl =  prop.getProperty("crownix.agent");
		String rdUser =  prop.getProperty("crownix.user");
		String rdUserPw =  prop.getProperty("crownix.pw");
		String rdSaveDir =  prop.getProperty("crownix.save.dir");
		String rdSysSaveDir =  prop.getProperty("crownix.system.save.dir");
		String trmsSystemUrl =  prop.getProperty("crownix.trms.system.url");
		
		HashMap<String, String> hm = new HashMap<String, String>();
		
		hm.put("rdUrl", rdUrl);
		hm.put("viewerUrl", viewerUrl);
		hm.put("rdAgentUrl", rdAgentUrl);
		hm.put("rdUser", rdUser);
		hm.put("rdUserPw", rdUserPw);
		hm.put("rdSaveDir", rdSaveDir);
		hm.put("rdSysSaveDir", rdSysSaveDir);
		hm.put("trmsSystemUrl", trmsSystemUrl);

		return hm;
	}
	
	/**
	 * @Method Name	: getLocalServerIp
	 * @author	: hong
	 * @version : 1.0
	 * @since	: 2018. 12. 14.
	 * @see : 
	 * @return
	 * @throws IOException 
	 * @return String:IPv4기반의 IP주소
	 */
	public String getLocalServerIp() {
	    InetAddress localAddress = getLocalAddress();
	    if (localAddress == null) {
	        try {
	            return Inet4Address.getLocalHost().getHostAddress();
	        } catch (UnknownHostException e) {
	            
	        }
	    } else {
	        return localAddress.getHostAddress();
	    }
	 

	    return "";
	}
	
	/**
	 * @Method Name	: getLocalAddress
	 * @author	: hong
	 * @version : 1.0
	 * @since	: 2018. 12. 14.
	 * @see : 
	 * @return
	 * @throws IOException 
	 * @return InetAddress:address
	 */

	public  InetAddress getLocalAddress() {
	    try {
	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	        while (networkInterfaces.hasMoreElements()) {
	            List<InterfaceAddress> interfaceAddresses = networkInterfaces.nextElement().getInterfaceAddresses();
	            for (InterfaceAddress interfaceAddress : interfaceAddresses) {
	                InetAddress address =interfaceAddress.getAddress();
	                if (address.isSiteLocalAddress()) {
	                    return address;
	                }
	            }
	        }
	    } catch (Exception e) {
	        ;
	    }
	 

	    return null;
	}
	
	/**
	 * @Method Name	: comm_reportFileDown
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/recvImgReportFileDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object recvImgReportFileDown(HttpServletRequest request, HttpSession session) throws Exception{

	  	Map<String, Object> param = new HashMap<String, Object>();
		Utils utils = new Utils();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경


		logger.debug("I_STS   "+param.get("I_STS"));
		//파일 업로드된 경로
		String savePath = "";
		if("Y".equals(param.get("I_STS"))){
			
			//리포트 서버의 파일 업로드된 경로 
			String rdServerSaveDir = param.get("rdServerSaveDir").toString() ;
			String rdFilePathName = param.get("rdFilePathName").toString() ;
			
			String systemDownDir = param.get("systemDownDir").toString() ;	//RD JPG/PDF 올려두는 파일
			
			String file_name = param.get("file_name").toString();
			
			String file_ext = param.get("file_ext").toString() ;
			
			String dpi300_hos_gubun = param.get("dpi300_hos_gubun").toString() ;

			String imgEachHos = param.get("imgEachHos").toString() ;
			
			String root = request.getSession().getServletContext().getRealPath("/");
			String downFilePath = root+systemDownDir;
			
			//디렉토리 생성 
			
			logger.debug("file_name   "+file_name);
			logger.debug("downFilePath  "+downFilePath);
			logger.debug("rdServerSaveDir  "+rdServerSaveDir);
			logger.debug("file_name  "+file_name);
//			logger.debug("### cjw ### CommonController.java ### dpi300_hos_gubun : "+dpi300_hos_gubun);
			
			
			
			File desti = new File(downFilePath);
			
			//해당 디렉토리의 존재여부를 확인
			if(!desti.exists()){
				//없다면 생성
				desti.mkdirs(); 
			}
			// saveMCWRKIMG 에서 체크하기 위해 param에 저장.
			param.put("downFilePath", downFilePath);
			/***************************Crownix Sever의 파일 System으로 이동 시작************************************/

			
			if("PDF".equals( file_ext.toUpperCase() )){//PDF 파일 Crownix 서버에서 System 서버로 이동
				String rdServerSavePath = rdServerSaveDir + "/"+rdFilePathName ;
//				savePath = utils.downCrownixPDFFile(rdServerSavePath, downFilePath, file_name);
				savePath = utils.ThreadDownCrownixPDFFile(rdServerSavePath, downFilePath, file_name);
			}else if("JPG".equals( file_ext.toUpperCase() )){//JPG 파일들을 Crownix 서버에서 System 서버로 하나의 jpg로 합치고 이동
				//savePath = utils.ThreadDownCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name);
//				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name);
				
				savePath = utils.ThreadDownCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name, dpi300_hos_gubun, imgEachHos);
			}
			
		}

		// * 구분자 값이 있으면, 파일명 update
		if (savePath.indexOf("*") > 0){
			// saveMCWRKIMG 에서 file_name 을 I_FILE_NM 값으로 사용함.
			param.put("file_name",savePath);
		}

		// pdf 일떄는 저장하지 않음. -> PDF제외 로직은 WEBOBJLIB.MCWRKIMGC 프로시저에서 처리
		//if (!savePath.endsWith(".pdf")) {
			try {
				saveMCWRKIMG(param);
			} catch (Exception e) {
				logger.error("saveMCWRKIMG 저장 실패 : {}", e.toString());
			}
		//}
		
		param.put("O_MSGCOD", "200");
	  	return param;
	}
	
	/**
	 * @Method Name	: recvImgReportOneFileDown
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/recvImgReportOneFileDown.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void recvImgReportOneFileDown(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{

		// url.getPath() 는 이런 값을 가진다. "/recvImg.do", "recvImg_mclis.do", "recvimg_nmc.do"
		// 블랙리스트로 관리한다. 해당 url일때는 클라이언트에 다운로드 안함(clientDownOk=false)
		String[] clientDownBlackUrl = {"/recvImg_mclis.do", "/recvimg_nmc.do"};
		URL url = new URL(request.getHeader("referer"));
		String referrPath = url.getPath();
		Boolean clientDownOk = (Arrays.stream(clientDownBlackUrl).anyMatch(s->s.equals(referrPath))) ? false : true;

		// clientDownOk 적용여부는 아직 2023-01-12 현재 미정. true 로 고정.
		//clientDownOk = true;
	  	Map<String, Object> param = new HashMap<String, Object>();
		Utils utils = new Utils();
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
		
		List<Map<String, Object>>  PrintParamList = new ArrayList();
		String recvObj = request.getParameter("recvObj");
	  	if(null != recvObj){
			if(!"".equals(recvObj)){
				PrintParamList =  Utils.jsonList(param.get("recvObj").toString());	// 데이터 Parameter 상태로 변경
				param.putAll(PrintParamList.get(0));
			}
	  	}

		//리포트 서버의 파일 업로드된 경로 
		String rdServerSaveDir = param.get("rdServerSaveDir").toString() ;
		String rdFilePathName = param.get("rdFilePathName").toString() ;
		String systemDownDir = param.get("systemDownDir").toString() ;	//RD JPG/PDF 올려두는 파일
		String file_name = param.get("file_name").toString();
		String downfile_name = param.get("downfile_name").toString();
		String file_ext = param.get("file_ext").toString() ;
		String dpi300_hos_gubun = param.get("DPI300_HOS").toString() ; //DPI300 or DPI110 gubun
		String imgEachHos = param.get("IMG_EACH_HOS").toString() ;
		
		String CHARSET = "UTF-8";
		String root = request.getSession().getServletContext().getRealPath("/");
		String downFilePath = root+systemDownDir;
		
		//디렉토리 생성 
		
		File desti = new File(downFilePath);
		
		//해당 디렉토리의 존재여부를 확인
		if(!desti.exists()){
			//없다면 생성
			desti.mkdirs(); 
		}
		// saveMCWRKIMG 에서 체크하기 위해 param에 저장.
		param.put("downFilePath", downFilePath);
		
		/***************************Crownix Sever의 파일 System으로 이동 시작************************************/
		//파일 업로드된 경로
		String savePath = "";
		

//		
	logger.debug("downfile_name   == "+downfile_name);
	logger.debug("file_name   === "+file_name);
	logger.debug("downFilePath   === "+downFilePath);
	logger.debug("rdFilePathName   === "+rdFilePathName);
	logger.debug("rdServerSaveDir   === "+rdServerSaveDir);
	logger.debug("dpi300_hos_gubun   === "+dpi300_hos_gubun);
		if("PDF".equals( file_ext.toUpperCase() )){//PDF 파일 Crownix 서버에서 System 서버로 이동
			String rdServerSavePath = rdServerSaveDir + "/"+rdFilePathName ;
			savePath = utils.downCrownixPDFFile(rdServerSavePath, downFilePath, file_name);
		}else if("JPG".equals( file_ext.toUpperCase() )){//JPG 파일들을 Crownix 서버에서 System 서버로 하나의 jpg로 합치고 이동
			
			if("714".equals(dpi300_hos_gubun)){
				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name,dpi300_hos_gubun, imgEachHos);
			}else{
				savePath = utils.downCrownixJPGFile(rdServerSaveDir, rdFilePathName, downFilePath, file_name, imgEachHos);
			}
			
		}

		// 압축파일(zip)일 경우에는 파일안에 있는 zip 파일 리스트를 가져와서 db에 저장(saveMCWRKIMG 에서 file_name 을 저장한다.)
		if (savePath.endsWith(".zip")) {
			param.put("file_name",getFileNameInZip(savePath));
		}

		// 다운로드 전에 db저장하던것을 다운로드하고 db저장하는 순서로 변경.
		// pdf 일떄는 저장하지 않음. -> PDF제외 로직은 WEBOBJLIB.MCWRKIMGC 프로시저에서 처리
		//if (!savePath.endsWith(".pdf")) {
			try {
				saveMCWRKIMG(param);
			} catch (Exception e) {
				logger.error("saveMCWRKIMG 저장 실패 : {}", e.toString());
			}
		//}
		//실제 내보낼 파일명

//		logger.debug("savePath"+savePath);
		if (clientDownOk){



//		logger.debug("I_STS   "+param.get("I_STS"));
			int size = 10240;
			int byteWritten = 0;


			/***************************리포트 파일 다운로드 시작************************************/

			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			response.setContentType("text/html;charset=UTF-8");

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
			String encodedFilename = Utils.downloadEncFileForBrowser(userAgent, file_name);


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
			/*******************브라우저에 따른 한글 깨짐 처리 끝*************************/

			long filesize = file.length();

			if (filesize > 0) {
				response.setHeader("Content-Length", "" + filesize);
			}


			logger.debug("encodedFilename   === "+encodedFilename);
			logger.debug("file   === "+file);

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
			/***************************리포트 파일 다운로드 끝************************************/
		}

	}

	@ResponseBody
	@RequestMapping(value = "/recvImgReportZipFile.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object recvImgReportZipFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
	  	Map<String, Object> param = new HashMap<String, Object>();
		//리포트 서버의 파일 업로드된 경로 

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String systemDownDir = request.getParameter("systemDownDir") ;
		String zipDownDir = request.getParameter("zipDownDir") ;
		String file_name = request.getParameter("file_name") ;
		String Zip_file_name = request.getParameter("zipName") ;
		String userAgent = request.getHeader("User-Agent");
		String imgEachHos = request.getParameter("imgEachHos");
		String CHARSET = "UTF-8";
		
		String root = request.getSession().getServletContext().getRealPath("/");
		String downFilePath = root+systemDownDir;
		String zipDwon = root+zipDownDir;
		//디렉토리 생성 

        String files[] =  file_name.split(";");
        
        String outZipNm = File.separator + Zip_file_name;

        File outZipfile = new File(zipDwon);
        logger.debug(systemDownDir);
        logger.debug(zipDwon);
        logger.debug(file_name);
		logger.debug(Zip_file_name);



		// zip 압축 파일에 담긴 파일수
		int fileCntInZip = 0;
        //buffer size
        int size = 102400;
        int byteWritten = 0;
        byte[] buf = new byte[size];
		/***************************리포트 파일 다운로드 시작************************************/
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//해당 디렉토리의 존재여부를 확인
		if(!outZipfile.exists()){
			//없다면 생성
			outZipfile.mkdirs(); 
		}
		
	    if( outZipfile.exists() ){
	    	File[] deleteFolderList = outZipfile.listFiles();
	    	
	    	Calendar cal = Calendar.getInstance() ;
	    	long todayMil = cal.getTimeInMillis() ;     // 현재 시간(밀리 세컨드)
	    	long oneDayMil = 24*60*60*1000 ;            // 일 단위
	    	Calendar fileCal = Calendar.getInstance() ;

	    	Date fileDate = null ;
	    	 
	    	for(int j=0 ; j < deleteFolderList.length; j++){
	    	                     
	    	    // 파일의 마지막 수정시간 가져오기
	    	    fileDate = new Date(deleteFolderList[j].lastModified()) ;
	    	     
	    	    // 현재시간과 파일 수정시간 시간차 계산(단위 : 밀리 세컨드)
	    	    fileCal.setTime(fileDate);
	    	    long diffMil = todayMil - fileCal.getTimeInMillis() ;
	    	     
	    	    //날짜로 계산
	    	    int diffDay = (int)(diffMil/oneDayMil) ;
	    	    // 3일이 지난 파일 삭제
	    	    if(diffDay > 3 && deleteFolderList[j].exists()){
	    	    	deleteFolderList[j].delete() ;
//	    	        System.out.println(deleteFolderList[j].getName() + " 파일을 삭제했습니다.");
	    	    }
	    	     
	    	}

	    }else{
//	        System.out.println("파일이 존재하지 않습니다.");
	    }
	    
        FileInputStream fis = null;
        ZipArchiveOutputStream zos = null;
        BufferedInputStream bis = null;

		try {
//			
            // Zip 파일생성
            zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(zipDwon+outZipNm))); 
            for( int i=0; i < files.length; i++ ){
                //해당 폴더안에 다른 폴더가 있다면 지나간다.
                if( new File(downFilePath+File.separator+files[i]).isDirectory() ){
                    continue;
                }
                //encoding 설정
                zos.setEncoding("UTF-8");
                File f =  new File(downFilePath + File.separator+ files[i]);

				/*
				   imgEachHos == 1 일때는 분할된 파일이 있는지 먼저 처리한다.
				 */

				// imgEachHos == 1 이고 분할된 파일이 있는지 확인한다.
				if ("1".equals(imgEachHos)){
					if( !findSplitFile(downFilePath, files[i]).isEmpty() ){

						List<String> splitFiles = findSplitFile(downFilePath, files[i]);
						List<String> splitFileNames = new ArrayList<String>();
						for(String splitFile : splitFiles) {
							splitFileNames.add(FilenameUtils.getName(splitFile));
							fis = new FileInputStream(splitFile);
							bis = new BufferedInputStream(fis,size);

							//zip에 넣을 다음 entry 를 가져온다.
							zos.putArchiveEntry(new ZipArchiveEntry(FilenameUtils.getName(splitFile)));

							//준비된 버퍼에서 집출력스트림으로 write 한다.
							int len;
							while((len = bis.read(buf,0,size)) != -1){
								zos.write(buf,0,len);
							}
							fileCntInZip += 1;

							bis.close();
							fis.close();
							zos.closeArchiveEntry();

						}
					}else if (f.isFile()) {
						//buffer에 해당파일의 stream을 입력한다.
						fis = new FileInputStream(downFilePath + File.separator+ files[i]);
						bis = new BufferedInputStream(fis,size);

						//zip에 넣을 다음 entry 를 가져온다.
						zos.putArchiveEntry(new ZipArchiveEntry(files[i]));

						//준비된 버퍼에서 집출력스트림으로 write 한다.
						int len;
						while((len = bis.read(buf,0,size)) != -1){
							zos.write(buf,0,len);
						}
						fileCntInZip += 1;

						bis.close();
						fis.close();
						zos.closeArchiveEntry();

					}else{
//        	        System.out.println("파일이 존재하지 않습니다.");
						logger.error("파일이 존재하지 않습니다.         "+files[i]);

					}

				}else{
					if (f.isFile()) {
						//buffer에 해당파일의 stream을 입력한다.
						fis = new FileInputStream(downFilePath + File.separator+ files[i]);
						bis = new BufferedInputStream(fis,size);

						//zip에 넣을 다음 entry 를 가져온다.
						zos.putArchiveEntry(new ZipArchiveEntry(files[i]));

						//준비된 버퍼에서 집출력스트림으로 write 한다.
						int len;
						while((len = bis.read(buf,0,size)) != -1){
							zos.write(buf,0,len);
						}
						fileCntInZip += 1;

						bis.close();
						fis.close();
						zos.closeArchiveEntry();

						// imgEachHos == 1 이고, 파일 이름이 유사한 파일이 있으면 분할된 파일이므로 zip파일로 묶는다.
					}else{
//        	        System.out.println("파일이 존재하지 않습니다.");
						logger.error("파일이 존재하지 않습니다.         "+files[i]);

					}
				}
            }
            zos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
			logger.error(e);
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

		param.put("outZipNm", zipDwon+outZipNm);
		//param.put("outZipNm", root + systemDownDir + File.separator + file_name);

		//프론트 response 에 이미 jpg 경로와 zip 경로가 결정된 데이타를 가지고 있음.
		// zip파일에 담긴 파일 갯수만 카운트해서 zipCount > 1 이면 zip 다운로드, 아니면 jpg 다운로드 하면 될듯...
		// outJpgNm : 프론트에서 파일 다운로드 시킬 파일명. file_name 을 써도 되지만 ;이 붙어 있어서 하나 더 만듦.
		// fileCntInZip = 1 이면 files 는 어차피 1개일꺼니까 files[0] 로 처리
		param.put("fileCntInZip", fileCntInZip);
		param.put("outJpgNm", files[0]);
		param.put("O_MSGCOD", "200");
		
		return param;
    }
	public  Object saveMCWRKIMG(Map<String, Object> param) throws Exception{

		//실제로 파일이 존재하는지 확인하기
		String downFilePath = param.get("downFilePath").toString();
		String fileNames = param.get("file_name").toString();

		if (!boolChkFileExist(downFilePath, fileNames, "\\*")) {
			logger.error("MCWRKIMG errorr 확인 downFilePath fileNames : "+ downFilePath + File.separator + fileNames);
			logger.error("MCWRKIMG errorr 확인 hos : "+ param.get("I_HOS").toString());
			logger.error("MCWRKIMG errorr 확인 jno : "+ param.get("I_JNO").toString());
			throw new Exception("파일을 찾을 수 없습니다.");
		}
		//생성할 검사코드의 정보 가져오기
	  	List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//	  	logger.debug(param);
	  	
		HashMap<String, Object> resultObj = new HashMap<String, Object>();
//		  		resultObj =  (HashMap<String, Object>) resultList.get(i);

		String P_GCD = param.get("P_GCD").toString();
		String I_MCD = Utils.isNullTrim(param.get("I_MCD"));

		// I_MCD 에 값이 있으면 개별 다운로드 처리이므로 MCWRKIMGN 에 인서트, 여러 row가 insert 될 필요가 없으므로 한줄만 입력되게끔 P_GCD 를 하나만 입력함.
		if (StringUtils.isNotEmpty(I_MCD) && StringUtils.isNotEmpty(P_GCD)){
			P_GCD = P_GCD.substring(0,5);
		}
		// 2022.06.02 - 파일이름 이미지결과 새성 로그에 추가.
		// String FILE_NM = param.get("file_name").toString();
		param.put("I_FILE_NM", fileNames);

		int pgcd_lenght = 0;
		if(P_GCD.length()>5){

			for(int pgcdIdx=0; pgcdIdx<P_GCD.length();pgcdIdx = pgcdIdx+5){
				resultObj  = new HashMap<String, Object>();
				param.put("I_GCD", P_GCD.substring(pgcdIdx,pgcdIdx+5));
				resultObj.putAll(param);
				resultList.add(resultObj);
			}
		}else{
			param.put("I_GCD", P_GCD);
			resultList.add(param);
		}
		commonService.commonListInsert("ImgLogSave", param, resultList);
	    return param;
	}

	public static  boolean AuthPage(HttpServletRequest request, HttpSession session) throws Exception{
		boolean rtnBool = false;
		Map<String,Object> param = new HashMap<String, Object>();
		
		String I_PTH = request.getRequestURI().toString();
		param.put("I_PTH", I_PTH.replaceAll("/",""));
    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		param.put("I_LOGEFG", "GOPAGE");	//이벤트 종류: (GOPAGE 페이지 이동)

	  	List<Map<String, Object>> resultList = commonService2.commonList("AuthPage", param);
	  	if(resultList.size()>0){
		  	HashMap<String, Object>  ListDtl = (HashMap<String, Object>) resultList.get(0);

		  	if("AUTHFALSE".equals(ListDtl.get("S003ACD").toString())){
		  		rtnBool = true;
				if(session != null){
					if(session.getAttribute("UID") != null){
						session.invalidate();
						session = request.getSession(true);
					}
		    	}
		  	}
	  	}else{
	  		rtnBool = true;
			if(session != null){
				if(session.getAttribute("UID") != null){
					session.invalidate();
					session = request.getSession(true);
				}
	    	}
	  	}

	    return rtnBool;
	}	
	
	/**
	 * @Method Name	: ImgLogSave
	 * @see
	 * <pre>
	 * Method 설명 : 병원(회원) 리스트 조회
	 * return_type : Object
	 * </pre>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/ImgLogSave.do" , method = {RequestMethod.GET, RequestMethod.POST})// , produces="application/json;charset=UTF-8")
	public Object ImgLogSave(HttpServletRequest request, HttpSession session) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> resultObj = new HashMap<String, Object>();
	  	Map<String, Object> param = new HashMap<String, Object>();
	  	
	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	String P_GCD = param.get("P_GCD").toString();
	  	
	  	// 2022.06.02 - 파일이름 이미지결과 새성 로그에 추가.
	  	String FILE_NM = param.get("FILE_NM").toString();
	  	param.put("I_FILE_NM", FILE_NM);
	  	
	  	if(P_GCD.length()>5){
	  		for(int pgcdIdx=0; pgcdIdx<P_GCD.length();pgcdIdx = pgcdIdx+5){
	  			resultObj  = new HashMap<String, Object>();
	  			param.put("I_GCD", P_GCD.substring(pgcdIdx,pgcdIdx+5));
	  			resultObj.putAll(param);
	  			resultList.add(resultObj);
	  		}
	  	}else{
	  		param.put("I_GCD", P_GCD);
	  		resultList.add(param);
	  	}
	  	logger.debug(resultList);
	  	logger.debug(param);
  		
	  	commonService.commonListInsert("ImgLogSave", param, resultList);
	  	
	  	return param;
	}
	
	
	/**
	 * @Method Name	: rotateImage
	 * @see
	 * <pre>
	 * Method 설명 : 이미지 회전
	 * return_type : String
	 * </pre>
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/rotateImage.do", method = {RequestMethod.GET, RequestMethod.POST})
	public void rotateImage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException{
		String url = request.getParameter("url");
		String sourceFilePathName = request.getParameter("sourceFilePathName");
		String sourceDptPathName = request.getParameter("sourceDptPathName");
		String destFileName = request.getParameter("destFileName");
		String str_radians = request.getParameter("radians");
		String file_name = destFileName;
		
		String img_path = url+"?sourceFilePathName="+sourceFilePathName+"&sourceDptPathName="+sourceDptPathName+"&destFileName="+destFileName;
		
		int radians = Integer.parseInt(str_radians)   ;
		
//		System.out.println("img_path = "+img_path);
		
		String savePath = null;
		
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;

		int size = 10240;
		int byteWritten = 0;
		BufferedImage orgImage = null;
		BufferedImage newImage = null;
		
		String root = request.getSession().getServletContext().getRealPath("/");
		
		
		String file_folder = "shared_files/report/rotateImg";
		
		Date date= new Date();
	    
	    long time = date.getTime();
//	    System.out.println("Time in Milliseconds: " + time);
		
	    savePath = root+file_folder+"/"+time+destFileName;
	    
	    //변환할 파일 생성
	    File file = new File(savePath);
	    
	    //해당 디렉토리의 존재여부를 확인
		if(!file.exists()){
			//없다면 생성
			file.mkdirs(); 
		}
	    
//		System.out.println(savePath);
		try {
			URL Url;
			byte[] buf;
			int byteRead;
			//url로 이미지 받기
			Url = new URL(img_path);
			
			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			
			orgImage = ImageIO.read(is);
			
			//이미지 가로 세로 크기 설정
			if(radians == 180){
				newImage = new BufferedImage(orgImage.getWidth(),orgImage.getHeight(),orgImage.getType());
			}else{//90, 270
				newImage = new BufferedImage(orgImage.getHeight(),orgImage.getWidth(),orgImage.getType());
			}
			
			Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		    graphics.rotate(Math. toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
		    graphics.translate((newImage.getWidth() - orgImage.getWidth()) / 2, (newImage.getHeight() - orgImage.getHeight()) / 2);
		    graphics.drawImage(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), null );
		    
		    //이미지 파일로 생성
			ImageIO.write(newImage, "jpg", file);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 파일 다운로드
		 */
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
		String encodedFilename = Utils.downloadEncFileForBrowser(userAgent, file_name);
		

		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);
		
		if (userAgent.indexOf("Opera") > -1 ){
			
			response.setContentType("application/octet-stream;charset=UTF-8");
			
		}
		/*******************브라우저에 따른 한글 깨짐 처리 끝*************************/
		
		long filesize = file.length();
		
		if (filesize > 0) {
			response.setHeader("Content-Length", "" + filesize);
		}
		
		
		is = null;
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
		
		if( file.exists() ){
            if(file.delete()){
            	logger.debug("파일삭제 성공");
            }else{
            	logger.debug("파일삭제 실패");
            }
        }else{
        	logger.debug("파일이 존재하지 않습니다.");
        }
		
	 }
	
	public static  Object callRecvRstPrc(String pgm,Map<String, Object> param) throws Exception{
	  	List<Map<String, Object>> resultList = commonService2.commonList(pgm, param);
	  	if(resultList.size()>0){
	  		param.put("resultList", resultList);
	  	}
	  	return param;
	}
	
	/**
     * SHA-256으로 해싱하는 메소드
     * 
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String sha256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        return bytesToHex(md.digest());
    }
 
 
    /**
     * 바이트를 헥스값으로 변환한다
     * 
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
          builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


	/**
	 * Path에 있는 파일중 FileName과 유사한 파일 이름 찾기
	 *
	 * @param String
	 * @return List<String>
	 */
	private static List<String> findSplitFile(String Path, String FileName){

		File pathDir = new File(Path);
		File[] fileNameList = pathDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// 파일이름에서 확장자 제거 하고 시작글자가 FileName 으로 시작하는 파일들
				// 예) fileName 이 file_01.jpg 이면
				// file_01_01.jpg , file_01_02.jpg , file_01_03.jpg 들 가져옴.
				return FilenameUtils.removeExtension(name).startsWith(FilenameUtils.removeExtension(FileName));
			}
		});
		List<String> filterFiles = new ArrayList<String>();
		for(File curFile : Objects.requireNonNull(fileNameList)) {
			if(!curFile.getName().equals(FileName)) {
				filterFiles.add(curFile.getPath());
			}
		}
		return filterFiles;
	}

	/**
	 * zip 파일 안에 file 목록 가져오기
	 *
	 * @param String
	 * @return String
	 */
	private static String getFileNameInZip(String zipFilePathName){
		String strFileName = "";

		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePathName))) {
			ZipEntry ze = zis.getNextEntry();
			List<String> listFileName = new ArrayList<String>();
			while (ze != null) {
				listFileName.add(ze.getName());
				ze = zis.getNextEntry();
			}
			//오름차순으로 정렬후 첫번째꺼가 기존에 들어가 이미지 파일명이다.
			Collections.sort(listFileName);
			String fisrtFileNameExt = listFileName.get(0);
			String fisrtFileName = "";
			String fisrtFileExt = "";
			// ex) fisrtFileNameExt : 2220870691_L74124_05562_01_01.jpg
			// 마지막 _01 을 제거한 파일명을 가져와야한다.

			String regex = ".*\\.(\\w+)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(fisrtFileNameExt);

			// 파일 확장자만 jpg
			if (matcher.matches()) {
				fisrtFileExt = matcher.group(1);
			}

			// 파일명에서 마지막 _ 이후는 제거
			// ex) 2220870691_L74124_05562_01
			String regex2 = "(_[^_]+)$";  // matches the "_" character followed by one or more characters at the end of the string
			String[] parts = Pattern.compile(regex2).split(fisrtFileNameExt);
			fisrtFileName = parts[0];

			// fisrtFileNameExt 을 재조합한다. _01을 제외한 파일명이 되어야함.
			// ex) 2220870691_L74124_05562_01.jpg
			fisrtFileNameExt = fisrtFileName +"."+ fisrtFileExt;

			String strJoinSplitFileNames = String.join("*", listFileName);
			// 분할된 파일일 경우 이미저장된 로그기록을 업데이트 한다. 로그의 파일 이름 부분을 구분자 "*" 으로 여러개 파일을 기록한다.

			// saveMCWRKIMG 에서 file_name 을 I_FILE_NM 값으로 사용함.
			strFileName = strJoinSplitFileNames;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strFileName;
	}

	private Boolean boolChkFileExist(String strPath, String strFiles, String strSplit) {
		try {
			// 정확한 경로값이 없을때는 체크가 불가능하므로 false 처리함.
			if (strPath == null || strPath.trim().isEmpty()) {
				return false;
			} else {
				// 경로가 존재하는지 확인
				Path dirPath = Paths.get(strPath);
				if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
					// 파일이 존재하는지 확인
					for (String file : strFiles.split(strSplit)) {
						if (!boolChkFileExist(strPath, file))
							return false;
					}
				} else {
					return false;
				}

			}
			// 체크로직 다 패스했으면 true 처리
			return true;
		} catch (Exception e) {
			logger.error("boolChkFileExist errorr 확인 : "+ e.toString());
			return false;
		}
	}

	private Boolean boolChkFileExist(String strPath, String strFile) {
		try {
			// 정확한 경로값이 없을때는 체크가 불가능하므로 false 처리함.
			if (strPath == null || strPath.trim().isEmpty()) {
				return false;
			}else{
				// 파일 존재하는지 확인
				Path filePath = Paths.get( strPath + File.separator + strFile);
				if (Files.exists(filePath)) {
					return true;
				} else {
					return false;
				}

			}
			// 체크로직 다 패스했으면 true 처리
			// return true;
		} catch (Exception e) {
			logger.error("boolChkFileExist errorr 확인 : "+ e.toString());
			return false;
		}
	}

}

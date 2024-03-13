package kr.co.softtrain.common.web.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
//import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class SysNoticeController {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    @RequestMapping(value = "/sysNotice.do")
    public String sysNotice(HttpServletRequest request,HttpSession session) throws Exception{
    	String str = "sys/sysNotice";
    	
    	if(CommonController.AuthPage(request,session)){
			str = "index";
		}
    	
        return str;
    }
    
    @Autowired  
    private MessageSource messageSource;
    
    /**
     * @Method Name : noticeList
     * @see
     * <pre>
     * Method 설명 : 공문/공지사항 조회
     * return_type : List<Map<String,Object>>
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/sysNoticeList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object sysNoticeList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_FWDT", Utils.Null2Zero(request.getParameter("I_FWDT").replaceAll("-", "")));
        param.put("I_TWDT", Utils.Null2Zero(request.getParameter("I_TWDT").replaceAll("-", "")));
        param.put("I_PFR", Utils.Null2Zero(request.getParameter("I_PFR").replaceAll("-", "")));
    	param.put("I_PTO", Utils.Null2Zero(request.getParameter("I_PTO").replaceAll("-", "")));
        
        List<Map<String, Object>> sysNoticeList = commonService.commonList("sysNoticeList",param);
        
        param.put("resultList", sysNoticeList);
        
        return param;
    }
    
    @RequestMapping(value = "/sysNotice01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gosysNoticeOpen01(HttpServletRequest request, HttpSession session){
		String str = "sys/sysNotice01";
		return str;
	}
    
    @RequestMapping(value = "/sysNotice02.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String gosysNoticeOpen02(HttpServletRequest request, HttpSession session){
		String str = "sys/sysNotice02";
		return str;
	}
    
    @ResponseBody
    @RequestMapping(value = "/sysNoticeSave.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object sysNoticeSave(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_PFR",  Utils.Null2Zero(request.getParameter("I_PFR").replaceAll("-", "")));
        param.put("I_PTO",  Utils.Null2Zero(request.getParameter("I_PTO").replaceAll("-", "")));
        param.put("I_LCO",  Utils.Null2Zero(request.getParameter("I_LCO")));
        param.put("I_TCO",  Utils.Null2Zero(request.getParameter("I_TCO")));
        
        logger.debug(param);
        
        commonService.commonInsert("sysNoticeSave", param);
    
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value = "/sysNoticeNextVal.do", method = {RequestMethod.GET, RequestMethod.POST})
    //    , produces="application/json;charset=UTF-8")
    public Object sysNoticeNextVal(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_LOGMNU", "NOTICENextVal");// 메뉴코드isEmpty
        param.put("I_LOGMNM", "공지사항 순번");// 메뉴코드isEmpty
        
        List<Map<String, Object>> sysNoticeNextVal = commonService.commonList("sysNoticeNextVal", param);
        
        param.put("resultList", sysNoticeNextVal);
        
        return param;
    }
    
    private String uploadFile(String originalName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        
        String savedName = uuid.toString() + "_" + originalName;
        
        return savedName;
    }
    
    @ResponseBody
    @RequestMapping(value = "/noticeAttchSave.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeAttchSave(MultipartHttpServletRequest request)  throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        if(!request.getParameter("I_FNM").equals("")){
            param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("I_SEQ")));
            param.put("I_FSQ",  1);
            param.put("I_FPT",  Utils.Null2Blank(request.getParameter("I_FPT")));
            param.put("I_FNM",  Utils.Null2Blank(request.getParameter("I_FNM")));
            param.put("I_FSNM", Utils.Null2Blank(request.getParameter("I_FSNM")));
            
            logger.debug(param);
            
            commonService.commonInsert("noticeAttchSave", param);
        }
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value="/noticeDtl.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeDtl(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_SEQ",request.getParameter("I_SEQ"));
        
        logger.debug(param);
        List<Map<String, Object>> noticeDtl = commonService.commonList("noticeDtl",param);
        param.put("result1",noticeDtl);
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value="/noticeAttachList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeAttachList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> noticeAttachList = commonService.commonList("noticeAttachList",param);
        
        param.put("resultList",noticeAttachList);
        
        return param;
    }
    
    @ResponseBody
	@RequestMapping(value = "/noticeDelete.do", method = {RequestMethod.GET, RequestMethod.POST})
	//	  , produces="application/json;charset=UTF-8")
	public Object noticeDelete(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경

        param.put("I_SEQ", request.getParameter("I_SEQ"));
        
	  	commonService.commonDelete("noticeDelete", param);

	  	return param;
	}
    
    @ResponseBody
    @RequestMapping(value = "/sysNoticeUpdate.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object sysNoticeUpdate(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        logger.debug(param);
        param.put("I_PFR", Utils.Null2Zero(request.getParameter("I_PFR").replaceAll("-", "")));
        param.put("I_PTO", Utils.Null2Zero(request.getParameter("I_PTO").replaceAll("-", "")));
        param.put("I_LCO", Utils.Null2Zero(request.getParameter("I_LCO")));
        param.put("I_TCO", Utils.Null2Zero(request.getParameter("I_TCO")));
        
        
        commonService.commonUpdate("sysNoticeUpdate", param);
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value = "/noticeAttchUpt.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeAttchUpt(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        commonService.commonUpdate("noticeAttchUpt", param);
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value = "/noticeAttachments.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object noticeAttachments(MultipartHttpServletRequest request,HttpServletResponse response)  throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> dayDt = commonService.commonList("getDate",param);
          
        String targetPath = "/shared_files/notice/" + dayDt.get(0).get("C"); 
          
        String path = request.getSession().getServletContext().getRealPath(targetPath);
        
          File f = new File(path);
          if (! f.exists()) {
              f.mkdirs();
          }
        
        if(request.getFile("attachments").getOriginalFilename() != ""){
            MultipartFile attachments = request.getFile("attachments");
            String savedName = attachments.getOriginalFilename();
            param.put("S014FNM", savedName);
            String savedNamef = uploadFile(savedName, attachments.getBytes());
            File attachmentsf = new File(path,savedNamef);
            FileCopyUtils.copy(attachments.getBytes(), attachmentsf);
            param.put("S014FPT", targetPath + "/" + attachmentsf.getName());
            param.put("S014FSNM", attachmentsf.getName());
        }
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value = "/ckeditorImageUploadNotice.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp, 
                 MultipartHttpServletRequest multiFile) throws Exception {
		String targetPath = "/shared_files/notice/images/";
        
        String path = req.getSession().getServletContext().getRealPath(targetPath);
        
        File f = new File(path);
        if (! f.exists()) {
            f.mkdirs();
        }
        
		JSONObject json = new JSONObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = multiFile.getFile("upload");
		if(file != null){
			if(file.getSize() > 0 && StringUtils.isNotBlank(file.getName())){
				if(file.getContentType().toLowerCase().startsWith("image/")){
					try{
						//String fileName = file.getName();
						String fileName = file.getOriginalFilename();
						
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath(targetPath);
						File uploadFile = new File(uploadPath);
						if(!uploadFile.exists()){
							uploadFile.mkdirs();
						}
						fileName = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName;
						out = new FileOutputStream(new File(uploadPath));
                        out.write(bytes);
                        
                        printWriter = resp.getWriter();
                        resp.setContentType("text/html");
                        String fileUrl = req.getContextPath() + targetPath + fileName;

                        json.put("uploaded", 1);
                        json.put("fileName", fileName);
                        json.put("url", fileUrl);
//                        System.out.println("확인" + json);
                        printWriter.println(json);
                        
                        
                    }catch(IOException e){
                        e.printStackTrace();
                    }finally{
                        if(out != null){
                            out.close();
                        }
                        if(printWriter != null){
                            printWriter.close();
                        }		
					}
				}
			}
		}
		return null;
	}	
	



}


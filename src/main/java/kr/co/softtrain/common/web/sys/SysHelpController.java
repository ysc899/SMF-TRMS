package kr.co.softtrain.common.web.sys;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;

/**
 * <pre>
 * kr.co.softtrain.common.web.sys
 * SysHelpController.java
 * </pre>
 * @title :  
 * @author : jsyoo
 * @since : 2018. 11. 14.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 14.		twbae 			최초생성
 * 
 * </pre>
 */

@Controller
public class SysHelpController {
	
	Logger logger = LogManager.getLogger();
	
	@Resource(name = "commonService")
	private commonService commonService;
	
	
	@RequestMapping(value = "/sysHelp.do")
    public String sysHelp(HttpServletRequest request,HttpSession session) throws Exception{
    	String str = "sys/sysHelp";
    	
    	if(CommonController.AuthPage(request,session)){
			str = "index";
		}
    	
        return str;
    }
	
	@RequestMapping(value = "/sysHelp01.do")
	public String sysHelp01(HttpSession session){
		return "sys/sysHelp01";
	}
	
	@RequestMapping(value = "/sysHelp01_1.do")
	public String sysHelp01_1(HttpSession session){
		return "sys/sysHelp01_1";
	}
	
	@RequestMapping(value = "/sysHelp02.do")
	public String sysHelp02(HttpSession session){
		return "sys/sysHelp02";
	}
	
	@RequestMapping(value = "/sysHelpView.do")
	public String sysHelpView(HttpSession session){
		return "sys/sysHelpView";
	}
	
	
	@Autowired  
    private MessageSource messageSource;
	
	@ResponseBody
	@RequestMapping(value="/sysHelpList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysHelpList(HttpServletRequest request, HttpSession session) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
		
		List<Map<String, Object>> sysHelpList = commonService.commonList("sysHelpList",param);
		
		param.put("resultList", sysHelpList);
		
		return param;
	}
	
	@ResponseBody
    @RequestMapping(value = "/sysHelpSave.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object sysHelpSave(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_S013DYN", "N");
        
        List<Map<String, Object>> sysHelpSaveCnt = commonService.commonList("sysHelpSaveCnt",param);
        if((int)sysHelpSaveCnt.get(0).get("CNT") > 0){
        	commonService.commonInsert("sysHelpUpdate", param);
        } else {
        	commonService.commonInsert("sysHelpSave", param);
        }
        
    
        return param;
    }
	
	@ResponseBody
	@RequestMapping(value = "/sysHelpDelete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysHelpDelete(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	  	param.put("I_MCD", Utils.Null2Blank(request.getParameter("S013MCD")));
	  	
	  	commonService.commonDelete("sysHelpDelete", param);

	  	return param;
	}
	
	@ResponseBody
	@RequestMapping(value="/sysHelpViewData.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sysHelpViewData(HttpServletRequest request, HttpSession session) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		
		param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
		
		List<Map<String, Object>> resultList = commonService.commonList("sysHelpViewData",param);
		
		param.put("resultList", resultList);
		
		return param;
	}
	
	
	
	/*
	@ResponseBody
    @RequestMapping(value = "/sysHelpFileSave.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object sysHelpFileSave(MultipartHttpServletRequest request,HttpServletResponse response)  throws Exception{
		
		//int sizeLimit = 10 * 1024 * 1024;
		int sizeLimit = 1024;
		
		Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("I_LOGMNU",request.getParameter("I_LOGMNU"));   //시스템 메뉴의 S001MCD
    	param.put("I_LOGMNM",request.getParameter("I_LOGMNM"));   //시스템 메뉴의 S001MNM
        
        param.put("O_MSGCOD", "");
        param.put("O_ERRCOD", "");
        
        List<Map<String, Object>> dayDt = commonService.commonList("getDate",param);
          
        String targetPath = "/shared_files/help/" + dayDt.get(0).get("C"); 
          
        String path = request.getSession().getServletContext().getRealPath(targetPath);
          
          File f = new File(path);
          if (! f.exists()) {
              f.mkdirs();
          }
         


        if(request.getFile("attachments").getOriginalFilename() != ""){
            MultipartFile attachments1 = request.getFile("attachments");
            String savedName1 = attachments1.getOriginalFilename();
            param.put("I_S013FNM", savedName1);
            String savedName1f = uploadFile(savedName1, attachments1.getBytes());
            File attachmentsf1 = new File(path,savedName1f);
            
            if(attachmentsf1.length() > sizeLimit){
            	param.put("O_MSGCOD", "204");
            	return param;
            }
            
            FileCopyUtils.copy(attachments1.getBytes(), attachmentsf1);
            param.put("I_S013FPT", targetPath + "/" + attachmentsf1.getName());
            param.put("I_S013FSNM", attachmentsf1.getName());
        }
        
        //logger.debug(savedName1 + " = " + savedName2 + " = " + savedName1f + " = " + savedName2f);
        
        return param;
    }
    */
	
    private String uploadFile(String originalName, byte[] fileData) throws Exception{
            UUID uuid = UUID.randomUUID();
            
            String savedName = uuid.toString() + "_" + originalName;
            
            return savedName;
    }
    
    @ResponseBody
    @RequestMapping(value = "/ckeditorImageUploadHelp.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String ckeditorImageUploadHelp(HttpServletRequest req, HttpServletResponse resp, 
                 MultipartHttpServletRequest multiFile) throws Exception {
		String targetPath = "/shared_files/help/images/";
        
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


package kr.co.softtrain.common.web.qna;

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
 * SysQnaController.java
 * </pre>
 * @title :  
 * @author : jsyoo
 * @since : 2018. 11. 14.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일            수정자             수정내용
 *  ---------------------------------------------------------------------------------
 *   2018. 11. 14.      jsyoo           최초생성
 * 
 * </pre>
 */

@Controller
public class QnaController {
    
    Logger logger = LogManager.getLogger();
    
    @Resource(name = "commonService")
    private commonService commonService;
    
    @RequestMapping(value = "/qna.do")
    public String menuTree(HttpServletRequest request,HttpSession session) throws Exception{
	   String str = "qna/qna";
	   if(CommonController.AuthPage(request,session)){
	      str = "index";
	   }
	   return str;
    }
    
    @Autowired  
    private MessageSource messageSource;
    
    /**
     * @Method Name : qnaList
     * @see
     * <pre>
     * Method 설명 : 문의사항 조회
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/qnaList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_FWDT", Utils.Null2Zero(request.getParameter("I_FWDT").replaceAll("-", "")));
        param.put("I_TWDT", Utils.Null2Zero(request.getParameter("I_TWDT").replaceAll("-", "")));
        
        List<Map<String, Object>> qnaList = commonService.commonList("qnaList",param);
        
        param.put("resultList",qnaList);
        
        return param;
    }
    
    /**
     * @Method Name : qnaAttchList
     * @see
     * <pre>
     * Method 설명 : 문의사항 첨부파일 조회
     * return_type : Object
     * </pre>
     * @param request
     * @param session
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping(value="/qnaAttachList.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaAttachList(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> qnaAttachList = commonService.commonList("qnaAttachList",param);
        
        param.put("resultList",qnaAttachList);
        
        return param;
    }
    
    
    @RequestMapping(value = "/qna01.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String qna01(HttpServletRequest request, HttpSession session){
        String str = "qna/qna01";
        return str;
    }
    
    @RequestMapping(value = "/qna02.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String qna02(HttpServletRequest request, HttpSession session){
        String str = "qna/qna02";
        return str;
    }
    
    @RequestMapping(value = "/qnaRe.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String qnaRe(HttpServletRequest request, HttpSession session){
        String str = "qna/qnaRe";
        return str;
    }
    
    @ResponseBody
    @RequestMapping(value = "/qnaReReg.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaReReg(HttpServletRequest request, HttpSession session) throws Exception{
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
    	
        param.put("I_CMA",  Utils.Null2Blank(request.getParameter("I_CMA1")+"@"+request.getParameter("I_CMA2")));
    	
        commonService.commonInsert("qnaSave", param);
    	
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value="/qnaDtl.do"
    , method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaDtl(HttpServletRequest request, HttpSession session) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> qnaDtl = commonService.commonList("qnaDtl",param);
        
        param.put("resultList",qnaDtl);
        
        return param;
    }
    
    /**
     * 조회수 증가 
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/qnaRdCntUdt.do"
      , method = {RequestMethod.GET, RequestMethod.POST}
      , produces="application/json;charset=UTF-8")
    public void qnaRdCnt(HttpServletRequest request,HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_SEQ", Utils.Null2Zero(request.getParameter("I_SEQ")));
        
        List<Map<String, Object>> qnaRdCntUdt = commonService.commonList("qnaRdCntUdt", param);
    }
    
    @ResponseBody
    @RequestMapping(value = "/qnaUdt.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaUdt(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_CMA",  Utils.Null2Blank(request.getParameter("I_CMA1")+"@"+request.getParameter("I_CMA2")));
        
        commonService.commonUpdate("qnaUdt", param);
        
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value = "/qnaAttachSave.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaAttachSave(MultipartHttpServletRequest request)  throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        if(!request.getParameter("I_FNM1").equals("")){
        	
        	param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("I_SEQ")));
            param.put("I_FSQ",  1);
            param.put("I_FPT",  Utils.Null2Blank(request.getParameter("I_FPT1")));
            param.put("I_FNM",  Utils.Null2Blank(request.getParameter("I_FNM1")));
            param.put("I_FSNM", Utils.Null2Blank(request.getParameter("I_FSNM1")));
        	
            if(!request.getParameter("I_OLD1").equals("")){
            	commonService.commonUpdate("qnaAttachUpt", param);
        	}else{
        		commonService.commonInsert("qnaAttachSave", param);
        	}
        }
        
        if(!request.getParameter("I_FNM2").equals("")){
        	
            param.put("I_SEQ",  Utils.Null2Zero(request.getParameter("I_SEQ")));
            param.put("I_FSQ",  2);
            param.put("I_FPT",  Utils.Null2Blank(request.getParameter("I_FPT2")));
            param.put("I_FNM",  Utils.Null2Blank(request.getParameter("I_FNM2")));
            param.put("I_FSNM", Utils.Null2Blank(request.getParameter("I_FSNM2")));
            
            if(!request.getParameter("I_OLD2").equals("")){
            	commonService.commonUpdate("qnaAttachUpt", param);
        	}else{
        		commonService.commonInsert("qnaAttachSave", param);
        	}
        }
        
        return param;
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/qnaSave.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaSave(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        param.put("I_CMA",  Utils.Null2Blank(request.getParameter("I_CMA1")+"@"+request.getParameter("I_CMA2")));
        param.put("I_RCT", 0);
        
        commonService.commonInsert("qnaSave", param);
        return param;
    }
    
    @ResponseBody
    @RequestMapping(value = "/qnaAttachments.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object qnaAttachments(MultipartHttpServletRequest request,HttpServletResponse response)  throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        
        param = Utils.getParameterMap(request); // 데이터 Parameter 상태로 변경
        
        List<Map<String, Object>> dayDt = commonService.commonList("getDate",param);
          
        String targetPath = "/shared_files/qna/" + dayDt.get(0).get("C"); 
          
        String path = request.getSession().getServletContext().getRealPath(targetPath);
          
          File f = new File(path);
          if (! f.exists()) {
              f.mkdirs();
          }
          
        if(request.getFile("attachments1").getOriginalFilename() != ""){
            MultipartFile attachments1 = request.getFile("attachments1");
            String savedName1 = attachments1.getOriginalFilename();
            param.put("S015FNM1", savedName1);
            String savedName1f = uploadFile(savedName1, attachments1.getBytes());
            File attachmentsf1 = new File(path,savedName1f);
            FileCopyUtils.copy(attachments1.getBytes(), attachmentsf1);
            param.put("S015FPT1", targetPath + "/" + attachmentsf1.getName());
            param.put("S015FSNM1", attachmentsf1.getName());
        }
        
        if(request.getFile("attachments2").getOriginalFilename() != ""){
            MultipartFile attachments2 = request.getFile("attachments2");
            String savedName2 = attachments2.getOriginalFilename();
            param.put("S015FNM2", savedName2);
            String savedName2f = uploadFile(savedName2, attachments2.getBytes());
            File attachmentsf2 = new File(path,savedName2f);
            FileCopyUtils.copy(attachments2.getBytes(), attachmentsf2);
            param.put("S015FPT2", targetPath + "/" + attachmentsf2.getName());
            param.put("S015FSNM2", attachmentsf2.getName());
        }
        
        return param;
    }
        
    private String uploadFile(String originalName, byte[] fileData) throws Exception{
            UUID uuid = UUID.randomUUID();
            
            String savedName = uuid.toString() + "_" + originalName;
            
            return savedName;
    }
    
    @ResponseBody
    @RequestMapping(value = "/qnaNextVal.do", method = {RequestMethod.GET, RequestMethod.POST})
    //    , produces="application/json;charset=UTF-8")
    public Object qnaNextVal(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("I_LOGMNU", "QNANextVal");// 메뉴코드isEmpty
        param.put("I_LOGMNM", "문의사항 순번");// 메뉴코드isEmpty
        
        List<Map<String, Object>> qnaNextVal = commonService.commonList("qnaNextVal", param);
        
        param.put("resultList",qnaNextVal);
        
        return param;
    }
    
    @ResponseBody
	@RequestMapping(value = "/qnaDelete.do", method = {RequestMethod.GET, RequestMethod.POST})
	//	  , produces="application/json;charset=UTF-8")
	public Object qnaDelete(HttpServletRequest request, HttpSession session) throws Exception {
	  	Map<String, Object> param = new HashMap<String, Object>();

	  	param = Utils.getParameterMap(request);	// 데이터 Parameter 상태로 변경
	  	
	    if(request.getParameter("S015SEQ") != null ){
	    	param.put("I_SEQ", request.getParameter("S015SEQ"));
	  	}
        commonService.commonDelete("qnaDelete", param);
//	
	  	return param;
	}
    
    @ResponseBody
    @RequestMapping(value = "/ckeditorImageUploadQna.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp, 
                 MultipartHttpServletRequest multiFile) throws Exception {
		String targetPath = "/shared_files/qna/images/";
        
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


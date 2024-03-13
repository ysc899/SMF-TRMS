package kr.co.softtrain.common.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.custom.util.Utils;

/**
 * 공통 CRUD 비즈니스 인터페이스
 * 
 * @author David Park
 * @since 2018. 05. 21
 * @version
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018.05.21		David Park 			최초생성
 * 
 * </pre>
 */

@Service("commonService")
public class commonServiceImpl extends EgovAbstractServiceImpl implements commonService {

	Logger logger = LogManager.getLogger();


//	@Resource(name = "commonMapper")
//	private commonMapper commonMapper;

    @Autowired  
    private MessageSource messageSource;
	/*
	 **********************기본적인 선언 시작*************************
	 */
	@Autowired
	@Resource(name="sqlSession")
	private SqlSessionFactory sqlSessionFactory;

	/*Transaction 관리를 위한 변수*/
	@Autowired
	@Resource(name="transactionManager")
	private PlatformTransactionManager transactionManager;

	/**
	 * @Method    : getSqlSessionFactory
	 * @Author      : ky.hong
	 * @Date        : 2015. 6. 2.
	 * @Version     :
	 * @Description :
	 * @return
	 */
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	/**
	 * @Method    : setSqlSessionFactory
	 * @Author      : ky.hong
	 * @Date        : 2015. 6. 2.
	 * @Version     :
	 * @Description :xx-context.xml에 정의된 sqlSessionFactory를 set 한다.
	 * @param sqlSessionFactory
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//		logger.error("*********setSqlSessionFactory***************");
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * @Method    : setTransactionManager
	 * @Author      : ky.hong
	 * @Date        : 2015. 6. 2.
	 * @Version     :
	 * @Description :xxx-context.xml에 정의된 PlatformTransactionManager를 set 한다.
	 * @param transactionManager
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * @Method Name	: commonLog
	 * @author	: ejlee
	 * @version : 1.0
	 * @see : 
	 * @param param
	 * @return
	 * @throws Exception 
	 * @return type :Object
	 */
	public Object commonLog( Map<String,Object> param) throws Exception{

		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		Object rtnObj = new Object();
        param.put("I_LOGURL",	reg.getRequestURI());
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("sysUserLogSave");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
	        logger.debug("===================================================log save===================================================================");
	        if(param.containsKey("O_MSGCOD")){
				if(param.containsKey("O_ERRCOD")){
					logger.debug("O_ERRCOD  == ["+(param.get("O_ERRCOD"))+"]");
				}
				if(param.containsKey("I_LOGURL")){
					
					if("/ImgLogSave.do".equals(param.get("I_LOGURL")))
					{
						if("N".equals(param.get("I_STS")))
						{
							param.put("I_LOGSFL","F");
						}
					}
				}
				logger.debug("O_MSGCOD  == ["+(param.get("O_MSGCOD"))+"]");
	
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
				if("LI".equals(param.get("I_LOGEFG").toString())){
					if(!"F".equals(param.get("I_LOGSFL").toString())){
						param.put("I_UID", Utils.isNull(param.get("O_UID")));
						param.put("I_COR",   Utils.isNull(param.get("O_COR"), "NML"));
					}else{
						param.put("I_UID", "");
						param.put("I_COR", "NML");
					}					
					param.put("I_UPWD", "");
					param.put("I_LOGNAM",   Utils.isNullTrim(param.get("O_NAM")));
				}else{
					param.put("I_UID", Utils.isNull(session.getAttribute("UID")));
					param.put("I_LOGNAM", Utils.isNull(session.getAttribute("NAM")));
					param.put("I_COR",  Utils.isNull(session.getAttribute("COR")));
				  	param.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
				}
				
				if(!param.containsKey("I_LOGPAR"))
				{
					param.put("I_LOGPAR",  param.toString());
				}
				
				if(param.get("I_LOGPAR").toString().length()>2000)
				{
					param.put("I_LOGPAR",  param.get("I_LOGPAR").toString().substring(0, 2000));
				}
		        
		//			if(param.get("I_LOGMSG").toString().isEmpty()){
				if(!param.containsKey("I_LOGMSG")){
					if(Utils.isNull(param.get("O_MSGCOD"), 0)>=300){
						if("999".equals(Utils.isNull(param.get("O_MSGCOD")))){
							param.put("I_LOGMSG",   Utils.isNullTrim(param.get("O_ERRCOD")));
						}else{
					    	param.put("I_LOGMSG", Utils.isNullTrim(param.get("O_MSGCOD"))+" / "+messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, "해당하는 메세지가 없습니다.\n["+param.get("O_MSGCOD")+"]",null));
//							param.put("I_LOGMSG",   Utils.isNullTrim(param.get("O_MSGCOD")));
						}
					}else{
						if(Utils.isNull(param.get("O_MSGCOD"), 0)==200){
							param.put("I_LOGMSG",   "" );
						}else{
							param.put("I_LOGMSG",   Utils.isNullTrim(param.get("O_ERRCOD")));
						}
					}
				}
		
		    	param.put("strMessage", messageSource.getMessage(Utils.isNull(param.get("O_MSGCOD"),"999"), null, "해당하는 메세지가 없습니다.\n["+param.get("O_MSGCOD")+"]",null));
		
				if(!param.containsKey("I_LOGMNU")){
					param.put("I_LOGMNU",	"");
					param.put("I_LOGMNM",	"");
				}
		    	if(!"/getCommCode.do".equals(param.get("I_LOGURL").toString())){
			        logger.debug("===========commonLog ================\n"+param.toString()+"================================================");
			         rtnObj =   sqlSession.insert("sysUserLogSave", param);  
			        logger.debug("===================================================log save end===================================================================");
		    	}
	        }
		}
		catch (Exception e) {
	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========Exception errors================\n"+errors.toString()+"================================================");
	      ///  param.put("I_LOGSFL", "F");
	      //   param.put("O_MSGCOD", "999");
		  // TODO: handle exception
		}finally {

			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
				
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
		}

//        param.remove("I_COR");
        param.remove("I_UID");
        param.remove("I_IP");
        param.remove("I_LOGPAR");
        param.remove("I_LOGMSG");
		return rtnObj;
	}
	/**
	 * 다건조회
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> commonList(String queryId, Map<String,Object> param) throws Exception{

		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
        boolean logFlg = false;

		try{
			if(!param.containsKey("I_UID")){
				param.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
				param.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
			  	param.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
			}
			if(param.containsKey("I_LOGPAR")){
				param.remove("I_LOGPAR");
			}
			param.put("MAPPER_ID", queryId);
	        param.put("I_LOGPAR", param.toString());
			param.put("strMessage","");
	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
	        if(!param.containsKey("I_LOGEFG")){
	        	param.put("I_LOGEFG", "RET"); //조회 
	        }
	        

	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
//			if("LI".equals(param.get("I_LOGEFG").toString())){
	        
	        logger.debug("	commonList 		["+queryId+"]");
//	        logger.debug("===========commonList ================\n"+param.toString()+"================================================");
	        
	        rtnList = sqlSession.selectList(queryId, param);

//			transactionManager.commit(status);
			
			if(!param.containsKey("O_MSGCOD")){
				param.put("O_MSGCOD","200");
				param.put("O_ERRCOD","");
			}

			if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
		        param.put("I_LOGSFL", "S");
				if("AuthPage".equals(queryId)){
					Map<String, Object>  ListDtl;
					if(rtnList.size()>0){
						ListDtl = (Map<String, Object>) rtnList.get(0);
						param.put("I_LOGMNU",	ListDtl.get("S001MCD"));
						param.put("I_LOGMNM",	ListDtl.get("S001MNM"));
					  	if("AUTHFALSE".equals(ListDtl.get("S003ACD").toString())){
							param.put("I_LOGSFL", "F");
							param.put("I_LOGMSG", "접속 권한 없음");
					  	}
			        }else{
						param.put("I_LOGSFL", "F");
						param.put("I_LOGMSG", "접속 권한 없음");
			        }
				}
			}else{
		        param.put("I_LOGSFL", "F");
			}
	        if(!param.containsKey("I_LOGMNU")){
	        	param.put("I_LOGMNU",	Utils.isNull(session.getAttribute("I_LOGMNU"),""));
	        	param.put("I_LOGMNM",	Utils.isNull(session.getAttribute("I_LOGMNM"),""));
	        }

	        logger.debug("===============List length===============   [   "+rtnList.size()+"   ]   =================================");
	        if(rtnList.size()>0){
	        	for(int kk=0;kk<rtnList.size();kk++){
	        		logger.debug("===============ListDtl["+kk+"]============   \n[   "+rtnList.get(kk)+"   ]\n   =================================");	        		
	        	}
	        	
	        	//logger.debug("===============ListDtl============   \n[   "+ListDtl.toString()+"   ]\n   =================================");
	        }
		}
		catch (Exception e) {
//			transactionManager.rollback(status);
	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonList errors================\n"+errors.toString()+"================================================");
	        param.put("I_LOGSFL", "F");
	        param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}
			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
	        param.put("I_LOGURL",	reg.getRequestURI());
			if(!"/recvRstDown.do".equals(param.get("I_LOGURL")))
			{
				logFlg = true;
			}
			else
			{
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
				param.put("I_LOGEFG", "FD");
				if(param.containsKey("O_MSGCOD")){
					if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))>300){
						logFlg = true;
					}
				}
			}
			
			if(logFlg){
				commonLog(param);
			}
//		    commonLog(param);
		}


		return rtnList;
	}

	/**
	 * 단건조회
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public Object commonOne(String queryId, Map<String,Object> param) throws Exception {

		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		Object rtnObj = new Object();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
        boolean logFlg = false;
		try{
			if(!param.containsKey("I_UID")){
				param.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
				param.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
			  	param.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
			}
			if(param.containsKey("I_LOGPAR")){
				param.remove("I_LOGPAR");
			}
			param.put("MAPPER_ID", queryId);
	        param.put("I_LOGPAR", param.toString());
			param.put("strMessage","");
	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
	        if(!param.containsKey("I_LOGEFG")){
	        	param.put("I_LOGEFG", "RET"); //조회 
	        }
	        logger.debug("	commonOne 		["+queryId+"]");
	        
			rtnObj = sqlSession.selectOne(queryId, param);
//			transactionManager.commit(status);
			
			if(!param.containsKey("O_MSGCOD")){
				param.put("O_MSGCOD","200");
				param.put("O_ERRCOD","");
			}
			if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
		        param.put("I_LOGSFL", "S");
			}else{
		        param.put("I_LOGSFL", "F");
			}
		}
		catch (Exception e) {
	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonOne errors================\n"+errors.toString()+"================================================");
	        param.put("I_LOGSFL", "F");
	        param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}
			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
	        param.put("I_LOGURL",	reg.getRequestURI());
			if(!"/recvRstDown.do".equals(param.get("I_LOGURL")))
			{
				logFlg = true;
			}
			else
			{
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
				param.put("I_LOGEFG", "FD");
				if(param.containsKey("O_MSGCOD")){
					if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))>300){
						logFlg = true;
					}
				}
			}
			
			if(logFlg){
				commonLog(param);
			}
		}
	   // commonLog(param);

		return rtnObj;
	}

	/**
	 * 수정처리
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public void commonUpdate(String queryId, Map<String,Object> param) throws Exception {

		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		Object rtnObj = new Object();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
        
		try{
			if(!param.containsKey("I_UID")){
				param.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
				param.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
			  	param.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
			}
			if(param.containsKey("I_LOGPAR")){
				param.remove("I_LOGPAR");
			}
			param.put("MAPPER_ID", queryId);
	        param.put("I_LOGPAR", param.toString());
	        param.put("strMessage","");

	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
	        if(!param.containsKey("I_LOGEFG")){
	        	param.put("I_LOGEFG", "UP"); //수정 
	        }else{
				if("RET".equals(param.get("I_LOGEFG").toString())){
					param.put("I_LOGEFG", "UP"); //수정 
				}
	        }

	        logger.debug("	commonUpdate 		["+queryId+"]");
//	        logger.debug("===========commonUpdate ================\n"+param.toString()+"================================================");
	        rtnObj = sqlSession.update(queryId, param);
//			transactionManager.commit(status);
			
			if(!param.containsKey("O_MSGCOD")){
				param.put("O_MSGCOD","200");
				param.put("O_ERRCOD","");
			}
			if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
		        param.put("I_LOGSFL", "S");
			}else{
		        param.put("I_LOGSFL", "F");
			}
		}
		catch (Exception e) {

	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonUpdate errors================\n"+errors.toString()+"================================================");
	        param.put("I_LOGSFL", "F");
	        param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}
			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
		    commonLog(param);
		}

	}

	/**
	 * 등록처리
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public void commonInsert(String queryId, Map<String,Object> param) throws Exception {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		Object rtnObj = new Object();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
			if(!param.containsKey("I_UID")){
				param.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
				param.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
			  	param.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
			}
			if(param.containsKey("I_LOGPAR")){
				param.remove("I_LOGPAR");
			}
			param.put("MAPPER_ID", queryId);
	        param.put("I_LOGPAR", param.toString());
			param.put("strMessage","");
	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
	        if(!param.containsKey("I_LOGEFG")){
	        	param.put("I_LOGEFG", "SAVE"); //저장 
	        }else{
				if("RET".equals(param.get("I_LOGEFG").toString())){
					param.put("I_LOGEFG", "SAVE"); //수정 
				}
	        }


	        logger.debug("	commonInsert 		["+queryId+"]");
//	        logger.debug("===========commonInsert ================\n"+param.toString()+"================================================");
	          
			rtnObj = sqlSession.insert(queryId, param);
//			transactionManager.commit(status);
			
			if(!param.containsKey("O_MSGCOD")){
				param.put("O_MSGCOD","200");
				param.put("O_ERRCOD","");
			}
			if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
		        param.put("I_LOGSFL", "S");
			}else{
		        param.put("I_LOGSFL", "F");
			}
		}
		catch (Exception e) {

	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonInsert errors================\n"+errors.toString()+"================================================");
	        param.put("I_LOGSFL", "F");
	        param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}
			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
		    commonLog(param);
		}
	}

	/**
	 * 삭제 처리
	 * @param tarId
	 * @param inputParam
	 * @throws Exception
	 */
	public void commonDelete(String queryId, Map<String,Object> param) throws Exception {

		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		Object rtnObj = new Object();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
        
		try{
			if(!param.containsKey("I_UID")){
				param.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
				param.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
			  	param.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
			}
			if(param.containsKey("I_LOGPAR")){
				param.remove("I_LOGPAR");
			}
			param.put("MAPPER_ID", queryId);
	        param.put("I_LOGPAR", param.toString());
	        param.put("strMessage","");
	    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
	        if(!param.containsKey("I_LOGEFG")){
	        	param.put("I_LOGEFG", "DEL"); //삭제 
	        }else{
				if("RET".equals(param.get("I_LOGEFG").toString())){
					param.put("I_LOGEFG", "DEL"); //수정 
				}
	        }
	        logger.debug("	commonDelete 		["+queryId+"]");
//	        logger.debug("===========commonDelete ================\n"+param.toString()+"================================================");
	          
			rtnObj = sqlSession.delete(queryId, param);
//			transactionManager.commit(status);
			
			if(!param.containsKey("O_MSGCOD")){
				param.put("O_MSGCOD","200");
				param.put("O_ERRCOD","");
			}
			if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
		        param.put("I_LOGSFL", "S");
			}else{
		        param.put("I_LOGSFL", "F");
			}
		}
		catch (Exception e) {

	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonDelete errors================\n"+errors.toString()+"================================================");
	        param.put("I_LOGSFL", "F");
	        param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}
			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
		    commonLog(param);
		}

	}
	/**
	 * 단건조회
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public Object commonListOne(String queryId, Map<String,Object> param,  List<Map<String, Object>> paramList) throws Exception {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		Object rtnObj = new Object();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
			logger.debug("	commonListOne 		["+queryId+"]");
//			logger.debug("===========commonListOne ================\n"+paramList.size()+"================================================");

			int i =0;
			for(Map<String, Object> queryParam : paramList)
			{
				i++;
				if(!queryParam.containsKey("I_UID")){
					queryParam.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
					queryParam.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
					queryParam.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
				}
				if(queryParam.containsKey("I_LOGPAR")){
					queryParam.remove("I_LOGPAR");
				}
				if(param.containsKey("I_LOGPAR")){
					param.remove("I_LOGPAR");
				}
				param.put("MAPPER_ID", queryId);
				queryParam.put("I_LOGPAR", queryParam.toString());
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
				if(!param.containsKey("I_LOGEFG")){
					param.put("I_LOGEFG", "RET"); //조회
				}
				param.putAll(queryParam);
				param.put("strMessage","");
				rtnObj = sqlSession.selectOne(queryId, param);
//				transactionManager.commit(status);
				
				if(!param.containsKey("O_MSGCOD")){
					param.put("O_MSGCOD","200");
					param.put("O_ERRCOD","");
				}
				if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<201){
					param.put("I_LOGSFL", "S");
				}else{
					param.put("I_LOGSFL", "F");
					break;
				}

			}
//			logger.debug("======END ====commonListOne============================================");
//
		}
		catch (Exception e) {
//			logger.debug("===========commonListOne ================\n"+param.toString()+"================================================");
//
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonListOne errors================\n"+errors.toString()+"================================================");
			param.put("I_LOGSFL", "F");
			param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}

			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
			commonLog(param);
		}
		return rtnObj;
	}
	/**
	 * 등록처리
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public void commonListInsert(String queryId, Map<String,Object> param,  List<Map<String, Object>> paramList) throws Exception {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
			logger.debug("	commonInsert 		["+queryId+"]");
//			logger.debug("===========commonListInsert ================\n"+paramList.size()+"================================================");
			int i =0;
			for(Map<String, Object> queryParam : paramList)
			{
				i++;
				if(!queryParam.containsKey("I_UID")){
					queryParam.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
					queryParam.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
					queryParam.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
				}
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
				if(!param.containsKey("I_LOGEFG")){
					param.put("I_LOGEFG", "SAVE"); //저장 
				}else{
					if("RET".equals(param.get("I_LOGEFG").toString())){
						param.put("I_LOGEFG", "SAVE"); //수정 
					}
		        }
				param.putAll(queryParam);
				if(param.containsKey("I_LOGPAR")){
					param.remove("I_LOGPAR");
				}
				param.put("MAPPER_ID", queryId);
				param.put("I_LOGPAR", param.toString());
				param.put("strMessage","");
				sqlSession.insert(queryId, param);
//				transactionManager.commit(status);
				
				if(!param.containsKey("O_MSGCOD")){
					param.put("O_MSGCOD","200");
					param.put("O_ERRCOD","");
				}
				if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
					param.put("I_LOGSFL", "S");
				}else{
					param.put("I_LOGSFL", "F");
				}
			}
//			logger.debug("======END ====commonListInsert============================================");
//
		}
		catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonListInsert errors================\n"+errors.toString()+"================================================");
			param.put("I_LOGSFL", "F");
			param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}

			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
			commonLog(param);
		}
	}


	/**
	 * 수정처리
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public void commonListUpdate(String queryId, Map<String,Object> param,  List<Map<String, Object>> paramList, String RQueryId) throws Exception {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
			logger.debug("	commonInsert 		["+queryId+"]");
//			logger.debug("===========commonListUpdate ================\n"+paramList.size()+"================================================");
			  int i =0;
			 for(Map<String, Object> queryParam : paramList)
			 {
				 i++;
					if(!queryParam.containsKey("I_UID")){
					queryParam.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
					queryParam.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
					queryParam.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
				}
				if(param.containsKey("I_LOGPAR")){
					param.remove("I_LOGPAR");
				}
				if(queryParam.containsKey("I_LOGPAR")){
					queryParam.remove("I_LOGPAR");
				}
				param.put("MAPPER_ID", queryId);
				queryParam.put("I_LOGPAR", queryParam.toString());
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		        if(!param.containsKey("I_LOGEFG")){
		        	param.put("I_LOGEFG", "UP"); //수정 
		        }else{
					if("RET".equals(param.get("I_LOGEFG").toString())){
						param.put("I_LOGEFG", "UP"); //수정 
					}
		        }
		        
		        if(!"".equals( RQueryId)){
		        	sqlSession.selectOne(RQueryId, queryParam);
//					transactionManager.commit(status);
//					logger.debug("======END ====commonListInsert============================================");
			        if(queryParam.containsKey("O_SSU")){
			        	queryParam.put("I_SSU", queryParam.get("O_SSU"));
			        	queryParam.put("I_BSU", queryParam.get("O_BSU"));
			        	queryParam.put("I_JSU", queryParam.get("O_JSU"));
						queryParam.put("I_ASU", queryParam.get("O_ASU"));
						queryParam.put("I_DCR", queryParam.get("O_DCR"));
						queryParam.put("I_BCC", queryParam.get("O_BCC"));
						queryParam.put("I_CSU", queryParam.get("O_CSU"));
						queryParam.put("I_CCR", queryParam.get("O_CCR"));
			        }
		        }
				logger.debug("======END ====commonListInsert============================================");
//				 logger.debug(queryParam);
		        
				param.putAll(queryParam);
				param.put("strMessage","");
				sqlSession.update(queryId, param);
//				transactionManager.commit(status);
				
				if(!param.containsKey("O_MSGCOD")){
					param.put("O_MSGCOD","200");
					param.put("O_ERRCOD","");
				}
				if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
					param.put("I_LOGSFL", "S");
				}else{
					param.put("I_LOGSFL", "F");
				}
			 }
//			 logger.debug("======END ====commonListUpdate============================================"+param.toString());
//
		}
		catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonListUpdate errors================\n"+errors.toString()+"================================================");
			param.put("I_LOGSFL", "F");
			param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}

			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
			commonLog(param);
		}
	}
	/**
	 * 수정처리
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */
	public void commonListUpdate2(String queryId, Map<String,Object> param,  List<Map<String, Object>> paramList,String subListQueryId, String RQueryId) throws Exception {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
			logger.debug("	commonListUpdate2 		["+queryId+"]");
//			logger.debug("===========commonListUpdate2 ================\n"+paramList.size()+"================================================");
			  int i =0;
			 for(Map<String, Object> queryParam : paramList)
			 {
				i++;
				if(!queryParam.containsKey("I_UID")){
					queryParam.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
					queryParam.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
					queryParam.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
				}
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		        if(!param.containsKey("I_LOGEFG")){
		        	param.put("I_LOGEFG", "UP"); //수정 
		        }else{
					if("RET".equals(param.get("I_LOGEFG").toString())){
						param.put("I_LOGEFG", "UP"); //수정 
					}
		        }
//				logger.debug("===========commonListUpdate2 ================\n"+queryParam.size()+"================================================");
		        param.putAll(queryParam);

		        rtnList = sqlSession.selectList(subListQueryId, queryParam);
//				transactionManager.commit(status);

		        logger.debug(rtnList);

				for(Map<String, Object> subParam : rtnList)
				{
					param.putAll(subParam);
			        if(param.containsKey("I_SSU")){
						param.remove("I_SSU");
						param.remove("I_BSU");
						param.remove("I_JSU");
						param.remove("I_ASU");
						param.remove("I_DCR");
						param.remove("I_BCC");
						param.remove("I_CSU");
						param.remove("I_CCR");
			        }
					if(param.containsKey("I_LOGPAR")){
						param.remove("I_LOGPAR");
					}
					if(param.containsKey("R001GCD")){
						param.put("I_GCD", param.get("R001GCD"));
					}
					param.put("MAPPER_ID", queryId);
					param.put("I_LOGPAR", param.toString());
					param.put("strMessage","");
			        if(!"".equals( RQueryId)){
			        	sqlSession.selectOne(RQueryId, param);
//						transactionManager.commit(status);
			        	if(param.containsKey("O_SSU")){
							param.put("I_SSU", param.get("O_SSU"));
							param.put("I_BSU", param.get("O_BSU"));
							param.put("I_JSU", param.get("O_JSU"));
							param.put("I_ASU", param.get("O_ASU"));
							param.put("I_DCR", param.get("O_DCR"));
							param.put("I_BCC", param.get("O_BCC"));
							param.put("I_CSU", param.get("O_CSU"));
							param.put("I_CCR", param.get("O_CCR"));
				        }
			        }
					sqlSession.update(queryId, param);
//					transactionManager.commit(status);
					if(!param.containsKey("O_MSGCOD")){
						param.put("O_MSGCOD","200");
						param.put("O_ERRCOD","");
					}
	
					if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
						param.put("I_LOGSFL", "S");
					}else{
						param.put("I_LOGSFL", "F");
					}
				}
			 }
			if(param.containsKey("I_LOGPAR")){
				param.remove("I_LOGPAR");
			}
			param.put("MAPPER_ID", queryId);
			 param.put("I_LOGPAR", param.toString());
			 param.put("strMessage","");
//			 logger.debug("======END ====commonListUpdate2============================================"+param.toString());
		}
		catch (Exception e) {
//
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonListUpdate2 errors================\n"+errors.toString()+"================================================");
			param.put("I_LOGSFL", "F");
			param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}

			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
			commonLog(param);
		}
	}
	
	/**
	 * 삭제처리
	 * 
	 * @param empVO
	 * @return List
	 * @throws Exception
	 */

	public void commonListDelete(String queryId, Map<String,Object> param,  List<Map<String, Object>> paramList, String RQueryId) throws Exception {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(queryId);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try{
			logger.debug("	commonListDelete 		["+queryId+"]");
//				logger.debug("===========commonListUpdate ================\n"+paramList.size()+"================================================");
			  int i =0;
			 for(Map<String, Object> queryParam : paramList)
			 {
				 i++;
					if(!queryParam.containsKey("I_UID")){
					queryParam.put("I_COR",	Utils.isNull(session.getAttribute("COR"),""));
					queryParam.put("I_UID",	Utils.isNull(session.getAttribute("UID"),""));
					queryParam.put("I_IP",	Utils.isNull(session.getAttribute("IP"),""));
				}
				if(queryParam.containsKey("I_LOGPAR")){
					queryParam.remove("I_LOGPAR");
				}
				if(param.containsKey("I_LOGPAR")){
					param.remove("I_LOGPAR");
				}
				queryParam.put("MAPPER_ID", queryId);
				queryParam.put("I_LOGPAR", queryParam.toString());
		    	//이벤트 종류 I_LOGEFG  =  EVT_FLAG (RET : 조회, CRT : 생성, SAVE : 저장, UP : 수정, DEL : 삭제, RP : 출력, FD : 파일다운, LI : 로그인, LO : 로그아웃,업로드)
		        if(!param.containsKey("I_LOGEFG")){
		        	param.put("I_LOGEFG", "DEL"); //수정 
		        }else{
					if("RET".equals(param.get("I_LOGEFG").toString())){
						param.put("I_LOGEFG", "DEL"); //수정 
					}
		        }
		        
		        if(!"".equals( RQueryId)){
		        	sqlSession.selectOne(RQueryId, queryParam);
//					transactionManager.commit(status);
		        }
//					 logger.debug(queryParam);
		        
				param.putAll(queryParam);
				param.put("strMessage","");
				sqlSession.delete(queryId, param);
//				transactionManager.commit(status);
				
				if(!param.containsKey("O_MSGCOD")){
					param.put("O_MSGCOD","200");
					param.put("O_ERRCOD","");
				}
				if(Integer.parseInt(Utils.isNull(param.get("O_MSGCOD"),"999"))<300){
					param.put("I_LOGSFL", "S");
				}else{
					param.put("I_LOGSFL", "F");
				}
			 }
		}
		catch (Exception e) {
//
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
	        logger.error("===========commonListDelete errors================\n"+errors.toString()+"================================================");
			param.put("I_LOGSFL", "F");
			param.put("O_MSGCOD", "999");
			if(errors.toString().length()>3500){
				param.put("I_LOGMSG", errors.toString().substring(0, 3500));
			}else{
				param.put("I_LOGMSG", errors.toString());
			}

			// TODO: handle exception
		}finally {
			try {
				status.flush();
				sqlSession.getConnection().close();
				sqlSession.close();
			} catch (SQLException e) {
				//log.error(e!=null?e:"transaction close error");
			}
			commonLog(param);
		}
	}

}

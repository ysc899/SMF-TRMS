package com.neodin.comm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.softtrain.common.service.commonService;
import kr.co.softtrain.common.web.util.CommonController;
import kr.co.softtrain.custom.util.Utils;
import mbi.jmts.cs.CSDPC;
import mbi.jmts.dpc.DPCParameter;

/**
 * 유형 설명을 삽입하십시오. 작성 날짜: (2005-01-13 오후 5:07:04) 작 성 자: 백기성
 */
public abstract class AbstractDpc {
	protected DPCParameter parm = null;
	protected List<Map<String, Object>> parmList = null;
	@Resource(name = "commonService")
	private commonService commonService;

	Logger logger = LogManager.getLogger();
    

	protected CSDPC dpc = null;

	protected String lib = null;

	protected String pgm = null;

	private int SUCCESS_CHK_FIELD = -1;

//	private i550System i550Conn = null;

	// protected int length = 0 ;
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-17 오전 9:27:57)
	 * 
	 * @return boolean
	 * @param parm
	 *            mbi.jmts.dpc.DPCParameter
	 */
	private final boolean callPgm() {
		HttpServletRequest reg =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = reg.getSession();
		try {			
			Map<String, Object> param = new HashMap<String, Object>();
			/* IN , OUT 파라메터 설정 되어 있는 프로시저 정보 */
			String iN_OUT_PGM = "";//"||MWR003RTR||MWR003RTR7||MCDWNCLS||MCALLSAA2||MC999RM||MCR003T11||MCR003T12||MCR03RMT75||MCR003RTR8||MCR03RMS6||MCR03RMS61||MC308RMS4||MC105RMS1||MCR03RM2W||MC118RM||MC118RMS1||MCR003TM1||MCR003T13||MCR003RMT8||MCR003RMR||MCR003RMR8||MCR003RMS8||MCR03RMT76||";
		
//			logger.error("       "+"  call Start PGM [  "+lib+"."+pgm+"  ]  ");
			double[] ParmLens = parm.getParmLens();
			short[] ParmTypes = parm.getParmTypes();
			int paramLen = 0;
			String paramStr = "";
			String inParamStr = "";
			for(int idx=0;idx<ParmLens.length;idx++){
			    double fp;
			    paramLen =  (int)ParmLens[idx];
			    fp = ParmLens[idx] - paramLen;
				paramStr =  Utils.isNull( parm.getStringParm(idx)," ");
				inParamStr =  Utils.isNull( parm.getStringParm(idx)," ");
				
				if(paramLen < paramStr.length()){
					if(fp == 0){
						inParamStr = paramStr.substring(0,paramLen);
					}
				}
				
				if(iN_OUT_PGM.indexOf(pgm+"||") > -1){
					param.put("I_PARM"+idx, inParamStr);
					if("MC999RM".equals(pgm)){
						if(idx==3){
							param.put("IO_PARM"+idx, inParamStr);
						}
					}
				}else{
					param.put("IO_PARM"+idx, inParamStr);
				}
			}
			
			param.put("I_LOGMNU",session.getAttribute("I_LOGMNU"));//메뉴 코드
			param.put("I_LOGMNM",session.getAttribute("I_LOGMNM"));//메뉴 명
			
			Object param2 = CommonController.callRecvRstPrc(lib+"."+pgm, param);
			
	        if(param.containsKey("resultList")){
				parmList = (List<Map<String, Object>>) param.get("resultList");
//				logger.error(parmList);
			}
	        
			if(!"MCR003T11".equals(pgm)){
		        for( String key : param.keySet() ){
					String paramI = "";
		        	if(key.indexOf("IO_PARM")>-1){
						paramI = key.replace("IO_PARM","").toString();
//						logger.error(paramI+"   ///  "+pgm +"   ///   "+param.get(key).toString());
						parm.setParm(Integer.parseInt(paramI), param.get(key).toString());
		        	}
//		        	logger.error("   "+key+" =    "+param.get(key).toString()+"  ]  ");
//		        	logger.error("  ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+"]    param.get(key).toString() [  "+param.get(key).toString()+"  ]  ");
		        	if("".equals(paramI)){
			        	if(key.indexOf("O_PARM")>-1){
							paramI = key.replace("O_PARM","").toString();
//							logger.error(paramI+"   ///  "+pgm +"   ///   "+param.get(key).toString());
							parm.setParm(Integer.parseInt(paramI), param.get(key).toString());
							
			        	}
		        	}
		        }
			}
//			logger.error("  ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+"]     "+"  call END PGM [  "+lib+"."+pgm+"  ]  ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//				i550Conn.disconnected(0);    	
			}
		return isSucceed();
	}

	public final int getCheckField() {
		return SUCCESS_CHK_FIELD;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-17 오전 9:29:47)
	 * 
	 * @return mbi.jmts.dpc.DPCParameter
	 */
	public final DPCParameter getParm() {
		return parm;
	}
	
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-17 오전 9:29:47)
	 * 
	 * @return List<Map<String, Object>>
	 */
	public final List<Map<String, Object>> getParmList() {
		return parmList;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-15 오후 3:08:58)
	 */
//	private final CSDPC getPgm(String lib, String pgm) {
////		i550Conn = new i550System();
////		logger.error("getPgm          ===   "+pgm);
////		return i550Conn.getDPC(lib, pgm);
//	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-17 오전 11:31:00)
	 * 
	 * @return boolean
	 */
	private final boolean isSucceed() {

		if (getCheckField() == -1)
			return true;

		try {
			short[] parmTypes = parm.getParmTypes();
			switch (parmTypes[getCheckField()]) {
			case DPCParameter.Text:
				return !getParm().getStringParm(getCheckField()).equalsIgnoreCase("E");
			case DPCParameter.Pack:
				return (getParm().getIntParm(getCheckField()) > 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public final boolean processDpc() {
		setParameters();
		return callPgm();
	}

	public final boolean processDpc(Object[] parameters) {
		if (parameters == null)
			return false;
		setParameters(parameters);
		return callPgm();
	}

	public final boolean processDpc(Vector parameters) {
		if (parameters == null)
			return false;
		setParameters(parameters);
		return callPgm();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-03-16 오전 10:45:27)
	 * 
	 * @param fieldNumber
	 *            int
	 */
	public final void setCheckField(int fieldNumber) {
		SUCCESS_CHK_FIELD = fieldNumber;
	}

	public abstract void setParameters();

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-14 오전 8:58:01)
	 * 
	 * @param parm
	 *            mbi.jmts.dpc.DPCParameter
	 */
	public abstract void setParameters(Object[] parameters);

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-01-14 오전 8:58:01)
	 * 
	 * @param parm
	 *            mbi.jmts.dpc.DPCParameter
	 */
	public abstract void setParameters(Vector parameters);
}

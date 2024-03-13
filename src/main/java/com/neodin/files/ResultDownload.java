package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 71   Fields: 44

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;
import com.neodin.comm.Trim;
import com.neodin.result.PatientInformation;
import com.neodin.rpg.DpcOfMCPATBAS2;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import kr.co.softtrain.common.web.util.CommonController;
import mbi.jmts.dpc.DPCParameter;

// Referenced classes of package com.neodin.files:
//            DpcMCALLSAA2, DpcMCR03RM2, DpcMCR03RMS6, DpcMCR03RMS71, 
//            DpcMC999RM, DpcMCR003RMR, DpcMCR003RMR5, DpcMCR003RMS9

public abstract class ResultDownload {

	// !
	public boolean is90027 = false; // First double maker
	public boolean is99934 = false; // Integrated test 1차
	public boolean is99935 = false; // Sequential test 1차
	public boolean is99936 = false; // Sequential test 2차
	public boolean is90100 = false; // Quad test
	public boolean is90028 = false; // Triple marker
	public boolean is90029 = false; // Double marker

	//
	private DecimalFormat df;
	private PatientInformation mPatientData;
	protected Workbook wb;
	protected WritableWorkbook workbook;
	protected WritableSheet wbresult;
	protected WritableSheet wbremark;
	protected WritableSheet wbresultC;
	protected WritableSheet wbresultT;
	protected Label label;
	private String parameters[];
	private String hospitalCode[];
	private String dpt_code[];
//!   

	private String divide;
	private int result_start_row;
	private Hashtable dpc;
	private Hashtable downloadData;
	// private Hashtable hasher;
	private Hashtable reference;
	private Hashtable hsExcelField;
	private Hashtable hsExcelFieldName;
	private String ExcelField[];
	private String ExcelFieldName[];
	protected int row;
	protected int row2;
	protected String kumdata[] = { "", "", "" };
	private String id;
	private String fdat; 
	private String tdat; 
	private String mid; 
	private String hakCd=""; 
	protected boolean isRewrite;	// 20150605 최대열 다운로드 대상(전체, 다운로드 받지 않은 결과)
	
	
	// private boolean debug;
	
	protected StringBuffer text;

	protected FileOutputStream f_outStream;

	protected OutputStreamWriter o_writer;

	protected BufferedWriter b_writer;

	protected File file;

	// private String encoding;

	protected String savedir;

	protected File cpyFilePath;

	protected String m_Filename;

	public static final int DWN_LIMIT = 400;

	protected String gubun1;

	protected String gubun2;

	protected GregorianCalendar cal;

	public ResultDownload() {
		HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();		
		
		divide = "";
		result_start_row = 3;
		gubun1 = "\n============================================================\n";
		gubun2 = "\n------------------------------------------------------------\n";
		df = new DecimalFormat("#######0.0");
		gubun1 = "\r\n============================================================\r\n";
		gubun2 = "\r\n------------------------------------------------------------\r\n";
		workbook = null;
		wb = null;
		wbresult = null;
		wbremark = null;
		wbresultC = null;
		wbresultT = null;
		label = null;
		dpc = null;
		parameters = null;
		hospitalCode = null;
		downloadData = null;
		// hasher = null;
		dpt_code = null;
		row = 0;
		row2 = 0;
		id = "";
		fdat = "";
		tdat = "";
		mid = "";
		isRewrite = true;
		// debug = false;
		text = new StringBuffer();
		f_outStream = null;
		o_writer = null;
		b_writer = null;
		file = null;
//		 encoding = "UTF8";
//        savedir = "E:\\home\\site1\\ROOT\\outfiles\\";
//        savedir = "D:\\excel\\";
		  String targetPath =  "/shared_files/outfiles/";
		  String path = request.getSession().getServletContext().getRealPath(targetPath);
		  savedir  = path;
		  

//       cpyFilePath = new File("E:\\home\\site1\\ROOT\\outfiles\\");
//        cpyFilePath = new File("D:\\excel\\");			
        cpyFilePath = new File(path);
		  
		m_Filename = "";
	}

	public String addBlanks(String s, int i) {
		String s1 = s.substring(0);
		try {
			int j = s.getBytes().length;
			if (j < i) {
				for (int k = 0; k < i - j; k++)
					s1 = s1 + " ";
			} else {
				s1 = s.substring(0, i);
			}
			return s1;
		} catch (Exception _ex) {
			s1 = s;
		}
		return s1;
	}

	
	public boolean isMastDuplPrint(String str)
	{
	   boolean isMastPrint=false;
	   if((str.trim().equals("0068916"))||(str.trim().equals("0068917"))||(str.trim().equals("0068939"))||(str.trim().equals("0068940"))||(str.trim().equals("0068941"))
	         ||(str.trim().equals("0068945"))||(str.trim().equals("0068946"))||(str.trim().equals("0068950"))||(str.trim().equals("0068952"))
	         ||(str.trim().equals("0068959"))||(str.trim().equals("0068962"))||(str.trim().equals("0068963"))
	         ||(str.trim().equals("0068817"))||(str.trim().equals("0068834"))||(str.trim().equals("0068835"))||(str.trim().equals("0068836"))
	         ||(str.trim().equals("0069014"))||(str.trim().equals("0069015"))||(str.trim().equals("0069016"))
	         ||(str.trim().equals("0069109"))||(str.trim().equals("0069128"))
	    	 ||(str.trim().equals("0069129"))||(str.trim().equals("0069138"))||(str.trim().equals("0069139"))||(str.trim().equals("0069140"))
	    	 ||(str.trim().equals("0069143"))||(str.trim().equals("0069144"))||(str.trim().equals("0069147"))
	    	 ||(str.trim().equals("0069149"))||(str.trim().equals("0069155"))||(str.trim().equals("0069158"))||(str.trim().equals("0069159")))
	      isMastPrint=true;

	   return isMastPrint;
	}
	
	
	
	public boolean isMastPrint(String str)
	{
	   boolean isMastPrint=true;
	   if((str.trim().equals("0068916"))||(str.trim().equals("0068917"))||(str.trim().equals("0068939"))||(str.trim().equals("0068940"))||(str.trim().equals("0068941"))
	         ||(str.trim().equals("0068945"))||(str.trim().equals("0068946"))||(str.trim().equals("0068950"))||(str.trim().equals("0068952"))
	         ||(str.trim().equals("0068959"))||(str.trim().equals("0068962"))||(str.trim().equals("0068963"))
	         ||(str.trim().equals("0068817"))||(str.trim().equals("0068834"))||(str.trim().equals("0068835"))||(str.trim().equals("0068836"))
	         ||(str.trim().equals("0069014"))||(str.trim().equals("0069015"))||(str.trim().equals("0069016"))
	    	 ||(str.trim().equals("0069109"))||(str.trim().equals("0069128"))
	    	 ||(str.trim().equals("0069129"))||(str.trim().equals("0069138"))||(str.trim().equals("0069139"))||(str.trim().equals("0069140"))
	    	 ||(str.trim().equals("0069143"))||(str.trim().equals("0069144"))||(str.trim().equals("0069147"))
	    	 ||(str.trim().equals("0069149"))||(str.trim().equals("0069155"))||(str.trim().equals("0069158"))||(str.trim().equals("0069159")))
	      isMastPrint=false;

	   return isMastPrint;
	}

	
	private boolean addLine(String s) {
		s = s.toLowerCase();
		StringTokenizer stringtokenizer = new StringTokenizer(s, " ");
		// String s1 = "";
		while (stringtokenizer.hasMoreTokens()) {
			String s2 = stringtokenizer.nextToken().trim();
			if (s2.equals("total") || s2.equals("ld1/ld2"))
				return true;
		}
		return s.equals("cholesterol") || s.equals("t.cholesterol");
	}

	public String appendBlanks(String s, int i) {
		return OracleCommon.appendBlanks(s, i);
	}

	public String getRPad(String str, int size) {
        for(int i = (str.trim().getBytes()).length; i <size; i++) {
            str += " ";
        }
        return str;
    }
	
	private boolean callResultSelectionDpc() throws Exception {
		boolean flag = false;
		DPCParameter dpcparameter2;
		List<Map<String, Object>> parmList = null;
		
		for (int i = 0; i < dpt_code.length; i++){
			//20150605 최대열 from to 데이터 수정
			//if (((AbstractDpc) getDpc().get("결과집계CL")).processDpc(new Object[] { gettDat().trim(), dpt_code[i].trim(), gettDat().trim() }))
			if (((AbstractDpc) getDpc().get("결과집계CL")).processDpc(new Object[] { getfDat().trim(), dpt_code[i].trim(), gettDat().trim() ,isRewrite}))
			{
				
				dpcparameter2 = ((AbstractDpc) getDpc().get("결과집계CL")).getParm();				
				
				setmid( dpcparameter2.getStringParm(7));
				
				flag = true;
				callResultDownDpc(i, dpcparameter2.getStringParm(7));// 추가 20150611 엑셀 생성부분 추가
			}
		}
		return flag;
	}

	private boolean callResultSelectionDpcHak() throws Exception {
		boolean flag = false;
		DPCParameter dpcparameter2;
		
		for (int i = 0; i < dpt_code.length; i++){
			//20150605 최대열 from to 데이터 수정
			if (((AbstractDpc) getDpc().get("결과집계HakCL")).processDpc(new Object[] { getfDat().trim(), dpt_code[i].trim(), gettDat().trim() ,isRewrite,gethakCd().trim()}))
			{
				dpcparameter2 = ((AbstractDpc) getDpc().get("결과집계HakCL")).getParm();
				flag = true;
				setmid( dpcparameter2.getStringParm(8));
				callResultDownDpc(i, dpcparameter2.getStringParm(8));// 추가 20150611 엑셀 생성부분 추가
			}
		}
		return flag;
	}
	
	/**
	private void callResultDownDpc(int dptIndex) {
		try {
			setParameters(new String[] { dpt_code[dptIndex].trim(), "0", "0", "", "0" });
			DPCParameter dpcparameter;
			for (; ((AbstractDpc) getDpc().get("결과CL")).processDpc(getParameters()); processingData(dpcparameter.getIntParm(33))) {
				dpcparameter = ((AbstractDpc) getDpc().get("결과CL")).getParm();
				parsingDownloadData(dpcparameter);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
*/
	private void callResultDownDpc(int dptIndex,String MID) {
		try {
			setParameters(new String[] { dpt_code[dptIndex].trim(), "0", "0", "", "0", MID });
			DPCParameter dpcparameter;
			List<Map<String, Object>> parmList = null;
			
			if(((AbstractDpc) getDpc().get("결과CL")).processDpc(getParameters()))
			{
				dpcparameter = ((AbstractDpc) getDpc().get("결과CL")).getParm();
				parmList = ((AbstractDpc) getDpc().get("결과CL")).getParmList();
				
				parsingDownloadData2(parmList);
				
				processingData(dpcparameter.getIntParm(6));
//				parsingDownloadData(dpcparameter);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	protected void parsingDownloadData2(List<Map<String, Object>> parmList) {
		if (!getDownloadData().isEmpty())getDownloadData().clear();
		Common common = new Common();
		try {
			int r_len = parmList.size();
			
			getDownloadData().put("병원코드", common.getArrayTypeData( parmList , "O_HOS" ));												
			getDownloadData().put("접수일자", common.getArrayTypeData( parmList , "O_DAT" ));												
			getDownloadData().put("접수번호", common.getArrayTypeData( parmList , "O_JNO" ));												
			getDownloadData().put("검체번호", common.getArrayTypeData( parmList , "O_HID" ));		
			getDownloadData().put("차트번호", common.getArrayTypeData( parmList , "O_CHT" ));		
			getDownloadData().put("수신자명", common.getArrayTypeData( parmList , "O_NAM" ));		
			getDownloadData().put("나이", common.getArrayTypeData( parmList , "O_AGE" ));													
			getDownloadData().put("성별", common.getArrayTypeData( parmList , "O_SEX" ));													
			getDownloadData().put("검사코드", common.getArrayTypeData( parmList , "O_GCD" ));												
			getDownloadData().put("검사명", common.getArrayTypeData( parmList , "O_FKN" ));		
			getDownloadData().put("일련번호", common.getArrayTypeData( parmList , "O_SEQ" ));												
			getDownloadData().put("결과",  common.getArrayTypeData( parmList , "O_RT1" ));		
			getDownloadData().put("결과상태", common.getArrayTypeData( parmList , "O_PAN" ));												
			getDownloadData().put("리마크코드", common.getArrayTypeData( parmList , "O_RNO" ));												
			getDownloadData().put("검사완료일", common.getArrayTypeData( parmList , "O_BDT" ));												
			getDownloadData().put("처방일자", common.getArrayTypeData( parmList , "O_TIM" ));												
			getDownloadData().put("이력", common.getArrayTypeData( parmList , "O_FHI" ));													
			getDownloadData().put("언어", common.getArrayTypeData( parmList , "O_LNG" ));													
			getDownloadData().put("보험코드", common.getArrayTypeData( parmList , "O_BCD" ));
			getDownloadData().put("결과타입", common.getArrayTypeData( parmList , "O_FL1" ));
			getDownloadData().put("외래구분", common.getArrayTypeData( parmList , "O_FL2" ));												
			getDownloadData().put("병원검체코드", common.getArrayTypeData( parmList , "O_FL3" ));											
			getDownloadData().put("병원검사코드", common.getArrayTypeData( parmList , "O_FL4" ));											
			getDownloadData().put("요양기관번호", common.getArrayTypeData( parmList , "O_FL5" ));	
			getDownloadData().put("이미지여부", common.getArrayTypeData( parmList , "O_IMG" ));												
			getDownloadData().put("주민번호", common.getArrayTypeData( parmList , "O_JUNO" ));		
			getDownloadData().put("참고치단위등",  common.getArrayTypeData( parmList , "O_LHU" ));		
			getDownloadData().put("처방번호", common.getArrayTypeData( parmList , "O_CNO" ));		

		} catch (Exception _ex) {

//			System.out.println("_ex:"+_ex.getMessage());
		}
	}

	// private String change_filepath(String s, String s1) {
	// s = s.replace('\\', '/');
	// s = s.substring(s.indexOf(s1)).trim();
	// return s;
	// }10279

	public abstract void closeDownloadFile();

	public String[] cutHrcvDateNumber(String s) {
		String as[] = { "", "" };
		if (s == null || as.equals(""))
			return as;
		as = Common.getDataCut(s, "^");
		if (as == null || as.length == 0)
			return (new String[] { "", "" });
		else
			return as;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2010-04-22 오후 3:45:04)
	 */
	public String dat(String _date) {
		GregorianCalendar calDate = getGregorianCalendar(_date + "");
		// cal = Calendar.getInstance();
		// cal= getGregorianCalendar(_date);
		// 날짜 설정하는 방법

		// Calendar.DATE 에서 날짜 계산
		// 어제 구할때 [내일 1][지난주 -7][다음주 7]
		calDate.add(Calendar.DATE, -1);
		// cal.add(Calendar.DATE, -1);
		// Calendar.MONTH에서 달 계산
		// 지난달 구할때 같은 방법으로 구할수 있음
		// cal.add(Calendar.MONTH, -1);

		// 설정한 날짜의 일, 월, 년을 구함
		String days = String.valueOf(calDate.get(Calendar.DATE)); // 일
		String month = String.valueOf(calDate.get(Calendar.MONTH) + 1); // 월
		String year = String.valueOf(calDate.get(Calendar.YEAR)); // 년

		if (month.length() == 1) {
			month = "0" + month;
		}
		return year + month + days;
	}

	protected synchronized boolean downloading() {
		try {
			if (!setHospitalList()){//병원 리스트를 받아와서 결과 폼을 생성
				System.out.println("### 01 downloading() >> 결과포맷에 등록되어 있지 않은 병원");
				return false;
			}
			
			makeDownloadFile();//각 병원별 폼 생성

			// !
			//cl 프로그램 호출 할때는 주석 해야함
//			if (isRewrite()) {
//				prepareRedownload(); //결과 다운로드 해지
//			}
			
			try {
				callmcr003tDpc();//temp table 생성	
			} catch (Exception e) {	
				System.out.println("### 02 downloading() >> "+e.getMessage());
				System.out.println("### 03 downloading() >> "+e.getStackTrace());
			}
			//cl 프로그램 호출 초기화
			
			
			try {
				//사용자가 학부선택을 하면 callResultSelectionDpcHak 호출 
				if(!hakCd.equals("")){
					callResultSelectionDpcHak();
				}else{
					callResultSelectionDpc(); //MCR003T12 현:DpcMCR003RMR()
				}
			} catch (Exception e) {
				System.out.println("### 04 downloading() >> "+e.getMessage());
				System.out.println("### 05 downloading() >> "+e.getStackTrace());
			}
			
			
			//callResultDownDpc(); //MCR003RTR8 현:DpcMCR003RMR8()			//20150611 모든병원 집계 후 조회 되던 것을 각 병원 집계 후 조회로 변경
		} catch (Exception _ex) {
			
			System.out.println("### 06 downloading() >> "+_ex.getMessage());
			System.out.println("### 07 downloading() >> "+_ex.getStackTrace());
			
			return false;
		} finally {
			closeDownloadFile();
			try {
				callmcr003tDpc();//temp table 생성
			} catch (Exception e) {	
			
				System.out.println("### 08 downloading() >> "+e.getMessage());
				System.out.println("### 09 downloading() >> "+e.getStackTrace());
				
			}
		}
		
		System.out.println("### 10 downloading() >> return true 실행");
		
		return true;
	}

	private boolean callmcr003tDpc() {
		boolean flag = false;
		for (int i = 0; i < dpt_code.length; i++)
			if (((AbstractDpc) getDpc().get("MCR003T11")).processDpc(new Object[] { gettDat().trim(), dpt_code[i].trim(), gettDat().trim() }))
			{
				flag = true;
			}
		return flag;
	}

	// private String fileName(String s, String s1) {
	// String s2 = "";
	// for (StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
	// stringtokenizer
	// .hasMoreTokens();)
	// s2 = stringtokenizer.nextToken();
	//
	// return s2;
	// }

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLast_NoTrim(String data, int res_cnt) {

		String result[] = new String[res_cnt];

		if (res_cnt == 0)
			return result;

		for (int i = 0; i < res_cnt; i++)
			result[i] = "";

		int i = 0;

		if (data.substring(0, 1).equals("|")) {
			data = data.substring(1);
			result[i++] = "";

			if (res_cnt == 1) {
				return result;
			}
		}

		java.util.StringTokenizer str = new java.util.StringTokenizer(data, "|");

		while (str.hasMoreTokens()) {
			result[i] = str.nextToken("|");
			i++;
			if (i == res_cnt)
				break;
		}

		return result;
	}

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLastNoTrim(String data) {
		if (data.equals(""))
			return null;
		// System.out.println(data);
		Vector result = new Vector();
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, "|");
		while (str.hasMoreTokens()) {
			result.addElement(str.nextToken("|"));
		}
		String[] src = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			src[i] = result.elementAt(i).toString();
		}
		return src;
	}

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLastNoTrim(String data, String div) {
		if (data.equals(""))
			return null;
		// System.out.println(data);
		Vector result = new Vector();
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, div);
		while (str.hasMoreTokens()) {
			result.addElement(str.nextToken(div));
		}
		String[] src = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			src[i] = result.elementAt(i).toString();
		}
		return src;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2012-05-30 오후 3:06:27)
	 * 
	 * @return java.lang.String
	 */
	public String getCrLF(String str) {
		String src[] = Common.getStringSpectialChar(str);
		String result = "";
		for (int i = 0; i < src.length; i++) {
			result += src[i].replace('\r', ' ').replace('\n', ' ').trim() + "\r\n";
		}
		return result;
	}

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2006-08-31 오후 3:58:11) 작 성 자: 조남식
	 * 
	 * 병리 = 조직  + 세포
	 * 미생물 -> 컬처 결과
	 * 
	 */
	protected String getCultureSensi(String date, String jno) {
		// String s4 = null;
		// try {
		// if (!((AbstractDpc) getDpc().get("리마크")).processDpc(new Object[] {s,
		// s1, s2, s3}))
		// return "";
		// String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc)
		// getDpc().get("리마크")).getParm().getStringParm(5));
		// s4 = getRemarkTxt(as);
		// if (s4 == null)
		// s4 = "";
		// } catch (Exception exception) {
		// exception.printStackTrace();
		// return "";
		// }
		// return s4;

		String rtnMk = "";
		Object[] data = new Object[7];
		try {
			if (!((AbstractDpc) getDpc().get("MC118RMS1")).processDpc(new Object[] { "NML", date, jno }))
				return "";

			// dpc = new DpcOfMC118RMS1();
			// if (dpc.processDpc(new String[] {"NML", date, jno})) {
			// parmC = dpc.getParm();

			// !
			int cnt = ((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getIntParm(9);

			// !

			String[] CultureCode = Common.getArrayTypeData(((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getStringParm(3), 5, cnt);
			String[] CultureName = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getStringParm(4), cnt);
			String[] CultureDate = Common.getArrayTypeData(((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getStringParm(5), 8, cnt);
			String[] SensiCode = Common.getArrayTypeData(((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getStringParm(6), 5, cnt);
			String[] SensiName = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getStringParm(7), cnt);
			String[] SensiDate = Common.getArrayTypeData(((AbstractDpc) getDpc().get("MC118RMS1")).getParm().getStringParm(8), 8, cnt);

			// !
			for (int i = 0; i < cnt; i++) {
				data[0] = i + 1 + "";
				data[1] = CultureCode[i];
				data[2] = CultureName[i];
				data[3] = CultureDate[i];
				data[4] = SensiCode[i];
				data[5] = SensiName[i];
				data[6] = SensiDate[i];
				// }
				// } else {
				// return "";
			}
		} catch (Exception e) {
			return "";
		}

		// !
		try {
			if (!((AbstractDpc) getDpc().get("MC118RM")).processDpc(new Object[] { "NML", date, jno, "1", "1", data[1].toString(),
					data[4].toString(), data[3].toString(), data[6].toString() }))
				return "";

			// dpc = new nml.mclis.lmb.patient.dpc.DpcOfMC118RM();
			// if (dpc.processDpc(new String[] {jAB._CORID, date, jno, "1", "1",
			// data[1].toString(), data[4].toString(), data[3].toString(),
			// data[6].toString()})) {
			// parmC = dpc.getParm();

			// !
			int cnt = ((AbstractDpc) getDpc().get("MC118RM")).getParm().getIntParm(10);
			// int resultLine = 0;
			String[] CultureResult = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("MC118RM")).getParm().getStringParm(11), cnt); // 학부명

			rtnMk = getRemarkTxt2(CultureResult);
			// }
		} catch (Exception e) {
			return rtnMk;
		}
		return rtnMk;
	}

	private String getfDat() {
		return fdat;
	}

	private String gettDat() {
		return tdat;
	}
	private String getmid() {
		return mid;
	}

	private String gethakCd() {
		return hakCd;
	}

	private String[] getDataCut_(String s, String s1) {
		if (s.equals("") || s1.equals(""))
			return null;
		Vector vector = new Vector();
		int i = s.length();
		// String s2 = "";
		String s5 = "";
		for (int j = 0; j < i; j++)
			if (j + 1 < i) {
				String s3 = s.substring(j, j + 1);
				if (!s3.equals(s1)) {
					s5 = s5 + s3;
				} else {
					vector.addElement(s5);
					s5 = "";
				}
			} else {
				String s4 = s.substring(j, j + 1);
				vector.addElement(s5 + s4);
			}

		i = vector.size();
		String as[] = new String[i];
		for (int k = 0; k < i; k++)
			as[k] = vector.elementAt(k).toString().trim();

		return as;
	}

	// private String getDate() {
	// SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
	// Date date = new Date();
	// return simpledateformat.format(date);
	// }

	protected String getDateCut(String s, String s1) {
		if (s.length() == 0)
			return "";
		StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
		String s2;
		for (s2 = ""; stringtokenizer.hasMoreTokens(); s2 = s2 + stringtokenizer.nextToken() + "  ")
			;
		return s2;
	}

	private synchronized boolean getDateFormat(String s, String s1, int i, String s2) {
		return s1.length() <= 10 || !s.equals(s1.substring(0, s1.length() - 6)) || !s1.substring(s1.length() - 3, s1.length()).equalsIgnoreCase(s2)
				|| Integer.parseInt(s1.substring(s1.length() - 6, s1.length() - 4)) != i;
	}

	/**
	 * <p>
	 * 현재의 요일을 구한다.
	 * 
	 * @param
	 * @return 요일
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 * - 사용 예
	 * int day = DateUtil.getDayOfWeek()
	 * SUNDAY = 1
	 * MONDAY = 2
	 * TUESDAY = 3
	 * WEDNESDAY = 4
	 * THURSDAY = 5
	 * FRIDAY = 6
	 * </pre>
	 */
	public static int getDayOfWeek() {
		Calendar rightNow = Calendar.getInstance();
		int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
		return day_of_week;
	}

	protected String getDivide() {
		return divide;
	}

	private double getDouble(String s) {
		return Double.valueOf(s).doubleValue();
	}

	protected Hashtable getDownloadData() {
		if (downloadData == null)
			downloadData = new Hashtable();
		return downloadData;
	}

	protected Hashtable getDpc() {
		if (dpc == null)
			dpc = new Hashtable();
		return dpc;
	}

	private String getExcelField(String s) {
		try {
			String s1 = (String) hsExcelField.get(s);
			if (s1 == null)
				s1 = "";
			return s1;
		} catch (Exception _ex) {
			return "";
		}
	}

	protected String[] getExcelFieldArray() {
		return ExcelField;
	}

	private String getExcelFieldName(String s) {
		if (s.equals("20"))
			return "";
		try {
			String s1 = (String) hsExcelFieldName.get(s);
			if (s1 == null)
				s1 = "";
			return s1;
		} catch (Exception _ex) {
			return "";
		}
	}

	protected String[] getExcelFieldNameArray() {
		return ExcelFieldName;
	}

	private synchronized String getExcelFormat(String s, String s1, String s2) {
		String s3 = null;
		try {
			if (!((AbstractDpc) getDpc().get("결과포멧")).processDpc(new Object[] { s, s1, s2 }))
				return "";
			Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("결과포멧")).getParm().getStringParm(4));
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("결과포멧")).getParm().getStringParm(5));
			String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("결과포멧")).getParm().getStringParm(6));
			String as2[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("결과포멧")).getParm().getStringParm(7));
			s3 = as2[0] + as1[0] + as[0];
			if (s3 == null)
				s3 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s3;
	}

	public String getFileName() {
		return m_Filename;
	}

	/**
	 * <p>
	 * GregorianCalendar 객체를 반환함.
	 * 
	 * @param yyyymmdd
	 *            날짜 인수
	 * @return GregorianCalendar
	 * @see java.util.Calendar
	 * @see java.util.GregorianCalendar <p>
	 * 
	 *      <pre>
	 * - 사용 예
	 * Calendar cal = DateUtil.getGregorianCalendar(DateUtil.getCurrentYyyymmdd())
	 * </pre>
	 */
	public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
		int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int dd = Integer.parseInt(yyyymmdd.substring(6));
		GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);
		return calendar;
	}

	// private synchronized String getHashData(String s) {
	// return (String) getReferHash().get(s);
	// }

	protected String[] getHospitalCode() {
		return hospitalCode;
	}

	private String getId() {
		return id;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2012-05-30 오후 3:06:27)
	 * 
	 * @return java.lang.String
	 */
	public String getLF(String str) {
		String src[] = Common.getStringSpectialChar(str);
		String result = "";
		for (int i = 0; i < src.length; i++) {
			result += src[i].replace('\r', ' ').replace('\n', ' ').trim() + "\n";
		}
		return result;
	}

	protected String getMastRemark() {
		String s = getDivide() + "\r\n** 참고치 **" + getDivide() + "\r\n";
		s = s + "===========================================" + getDivide() + "\r\n";
		s = s + "    * Total IgE Reference Range" + getDivide() + "\r\n";
		s = s + "-------------------------------------------" + getDivide() + "\r\n";
		s = s + "    <=100 IU/mL      Negative" + getDivide() + "\r\n";
		s = s + "     >100 IU/mL      Positive" + getDivide() + "\r\n";
		s = s + "===========================================" + getDivide() + "\r\n";
		s = s + "    * Specific IgE Reference Range" + getDivide() + "\r\n";
		s = s + "-------------------------------------------" + getDivide() + "\r\n";
		s = s + "   IU/mL      Class  Content" + getDivide() + "\r\n";
		s = s + "  0.00~0.34     0    None or hardly any found" + getDivide() + "\r\n";
		s = s + "  0.35~0.69     1    Low" + getDivide() + "\r\n";
		s = s + "  0.70~3.49     2    Increased" + getDivide() + "\r\n";
		s = s + "  3.50~17.49    3    Significantly increased" + getDivide() + "\r\n";
		s = s + "  17.50~49.99   4    High" + getDivide() + "\r\n";
		s = s + "  50.00~99.99   5    Very high" + getDivide() + "\r\n";
		s = s + "  >= 100        6    Extremely high" + getDivide() + "\r\n";
		s = s + "-------------------------------------------" + getDivide() + "\r\n";
		return s;
	}

	protected String[] getParameters() {
		return parameters;
	}

	protected synchronized String getReamrkValue(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("리마크")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크")).getParm().getStringParm(5));
			s4 = getRemarkTxt(as);
			
			if (s4 == null)
				s4 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s4;
	}

	protected synchronized String getReamrkValue2(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("리마크")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크")).getParm().getStringParm(5));
			s4 = getRemarkTxt4(as);
			if (s4 == null)
				s4 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s4;
	}

	protected synchronized String getReamrkValue99(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("리마크2")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크2")).getParm().getStringParm(5));
			String ln[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크2")).getParm().getStringParm(7));
			s4 = getRemarkTxt3(as, ln);
			if (s4 == null)
				s4 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s4;
	}
	
	protected synchronized String getReamrkValue99NCC(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("리마크2")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크2")).getParm().getStringParm(5));
			String ln[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크2")).getParm().getStringParm(7));
			s4 = getRemarkTxt3NCC(as, ln);
			if (s4 == null)
				s4 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s4;
	}

	protected synchronized String getReamrkValueTrim(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("리마크")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크")).getParm().getStringParm(5));
			s4 = getRemarkTxt4Trim(as);
			if (s4 == null)
				s4 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s4;
	}



	protected synchronized String getReamrkValueTrim2(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("리마크")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("리마크")).getParm().getStringParm(5));
			s4 = getRemarkTxt4Trim(as);
			if (s4 == null)
				s4 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s4;
	}

	protected String getReferenceValueAge(String as[]) {
		
		int i = as.length;
		String s = as[0].trim() + as[1].trim() + as[2].trim() + as[3].trim();
		try {
			if (i == 5) {
				as[4].trim();
				s = as[0].trim() + as[1].trim() + as[2].trim() + as[3].trim() + as[4].trim();
			}
		} catch (Exception ee) {
			as[4] = 0 + "";
			s = as[0].trim() + as[1].trim() + as[2].trim() + as[3].trim() + as[4].trim();
		}
		String s1 = null;
		if ((s1 = (String) getReferHash().get(s)) != null)
			return s1;
		try {
			if (!((AbstractDpc) getDpc().get("참고치2")).processDpc(as))
				return "";
			String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("참고치2")).getParm().getStringParm(7));
			// if (i < 5)
			s1 = getRemarkTxt(as1, "0");
			// else
			// s1 = getRemarkTxt(as1, as[4].trim());
			getReferHash().put(s, s1);
			if (s1 == null)
				s1 = "";
		} catch (Exception exception) {
			return "";
		}
		return s1;
	}

	protected String getReferenceValueAge2(String as[]) {
		int i = as.length;
		String s = as[0].trim() + as[1].trim() + as[2].trim() + as[3].trim();
		try {
			if (i == 5) {
				as[4].trim();
			}
		} catch (Exception ee) {
			as[4] = "";
		}
		String s1 = null;
		//if ((s1 = (String) getReferHash().get(s)) != null)
		//	return s1;
		try {
			if (!((AbstractDpc) getDpc().get("참고치2")).processDpc(as))
				return "";
			String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("참고치2")).getParm().getStringParm(7));
			if (i < 5)
				s1 = getRemarkTxt(as1, "0");
			else
				s1 = getRemarkTxt(as1, as[4].trim());
			getReferHash().put(s, s1);
			if (s1 == null)
				s1 = "";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
		return s1;
	}
	
	protected String getReferenceValue(String as[]) {
		String tempParam[] = new String[5]; 

		try {
			tempParam[0] =as[0];
			tempParam[1] =as[1];
			tempParam[2] =as[2];
			tempParam[3] =as[3];
			tempParam[4] =as[4];
		} catch (Exception e) {
			tempParam[0] =as[0];
			tempParam[1] =as[1];
			tempParam[2] =as[2];
			tempParam[3] =as[3];
			tempParam[4] ="0";
		}
		
		String s = tempParam[0].trim() + tempParam[1].trim() + tempParam[2].trim() + tempParam[3].trim() + tempParam[4].trim();;
		String s1 = null;
		
		try {
				if (!((AbstractDpc) getDpc().get("참고치2" )).processDpc(tempParam))
                      return "" ;
               String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("참고치2" )).getParm().getStringParm(7));
               s1 = getRemarkTxt(as1, tempParam[4]);
               getReferHash().put(s, s1);
               if (s1 == null)s1 = "";
               
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}finally{
			tempParam = null;
		}
		
		return s1;
	}
	

	
	protected String getReferenceValueNotBlank(String as[]) {
		String tempParam[] = new String[5]; 

		try {
			tempParam[0] =as[0];
			tempParam[1] =as[1];
			tempParam[2] =as[2];
			tempParam[3] =as[3];
			tempParam[4] =as[4];
		} catch (Exception e) {
			tempParam[0] =as[0];
			tempParam[1] =as[1];
			tempParam[2] =as[2];
			tempParam[3] =as[3];
			tempParam[4] ="0";
		}
		
		String s = tempParam[0].trim() + tempParam[1].trim() + tempParam[2].trim() + tempParam[3].trim() + tempParam[4].trim();;
		String s1 = null;
		
		try {
				if (!((AbstractDpc) getDpc().get("참고치2" )).processDpc(tempParam))
                      return "" ;
               String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("참고치2" )).getParm().getStringParm(7));
               s1 = getRemarkTxtNotBlank(as1, tempParam[4]);
               getReferHash().put(s, s1);
               if (s1 == null)s1 = "";
               
		} catch (Exception exception) {
			return "";
		}finally{
			tempParam = null;
		}
		
		return s1;
	}
	
//	protected String getReferenceValueNotBlank(String as[]) {
//		int i = as.length;
//		String s = as[0].trim() + as[1].trim() + as[2].trim() + as[3].trim();
//		try {
//			if (i == 5) {
//				as[4].trim();
//			}
//		} catch (Exception ee) {
//			as[4] = "";
//		}
//		String s1 = null;
//		if ((s1 = (String) getReferHash().get(s)) != null)
//			return s1;
//		try {
//			if (!((AbstractDpc) getDpc().get("참고치")).processDpc(as))
//				return "";
//			String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("참고치")).getParm().getStringParm(6));
//			if (i < 5)
//				s1 = getRemarkTxtNotBlank(as1, "0");
//			else
//				s1 = getRemarkTxtNotBlank(as1, as[4].trim());
//			getReferHash().put(s, s1);
//			if (s1 == null)
//				s1 = "";
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return "";
//		}
//		return s1;
//	}
	
	protected String[] getReferenceValue2(String as[]) {
		
		int i = as.length;
		String s = as[0].trim() + as[1].trim() + as[2].trim() + as[3].trim();
		try {
			if (i == 5) {
				as[4].trim();
			}
		} catch (Exception ee) {
			as[4] = "";
		}
		String as1[] = null;
		if (((String) getReferHash().get(s)) != null)
			return null;
		try {
			if (!((AbstractDpc) getDpc().get("참고치")).processDpc(as))
				return null;
			as1 = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get("참고치")).getParm().getStringParm(6));
			String s2;
			if (i < 5)
				s2 = getRemarkTxt(as1, "0");
			else
				s2 = getRemarkTxt(as1, as[4].trim());
			getReferHash().put(s, s2);
			if (s2 == null)
				s2 = "";
		} catch (Exception _ex) {
			return as1;
		}
		return as1;
	}

	protected String getReferenceValueTrim(String as[]) {
		 String tempParam[] = new String[5];

         try {
              tempParam[0] =as[0];
              tempParam[1] =as[1];
              tempParam[2] =as[2];
              tempParam[3] =as[3];
              tempParam[4] =as[4];
        } catch (Exception e) {
              tempParam[0] =as[0];
              tempParam[1] =as[1];
              tempParam[2] =as[2];
              tempParam[3] =as[3];
              tempParam[4] = "0";
        }
        
        String s = tempParam[0].trim() + tempParam[1].trim() + tempParam[2].trim() + tempParam[3].trim() + tempParam[4].trim();;
        String s1 = null;
        
         try {
                     if (!((AbstractDpc) getDpc().get("참고치2" )).processDpc(tempParam))
                  return "" ;
           String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get( "참고치2" )).getParm().getStringParm(7));
           s1 = getRemarkTxtTrim(as1, tempParam[4]);
           getReferHash().put(s, s1);
           if (s1 == null)s1 = "";
          
        } catch (Exception exception) {
              exception.printStackTrace();
               return "" ;
        } finally{
              tempParam = null;
        }
        
         return s1;
	}

	protected String getReferenceValueTrim2(String as[]) {
		 String tempParam[] = new String[5];

         try {
              tempParam[0] =as[0];
              tempParam[1] =as[1];
              tempParam[2] =as[2];
              tempParam[3] =as[3];
              tempParam[4] =as[4];
        } catch (Exception e) {
              tempParam[0] =as[0];
              tempParam[1] =as[1];
              tempParam[2] =as[2];
              tempParam[3] =as[3];
              tempParam[4] = "0";
        }
        
        String s = tempParam[0].trim() + tempParam[1].trim() + tempParam[2].trim() + tempParam[3].trim() + tempParam[4].trim();;
        String s1 = null;
        
         try {
                     if (!((AbstractDpc) getDpc().get("참고치2" )).processDpc(tempParam))
                  return "" ;
           String as1[] = Common.getArrayTypeData_CheckLast(((AbstractDpc) getDpc().get( "참고치2" )).getParm().getStringParm(7));
           s1 = getRemarkTxtTrim(as1, tempParam[4]);
           getReferHash().put(s, s1);
           if (s1 == null)s1 = "";
          
        } catch (Exception exception) {
              exception.printStackTrace();
               return "" ;
        } finally{
              tempParam = null;
        }
        
         return s1;
	}

	public Hashtable getReferHash() {
		if (reference == null)
			reference = new Hashtable();
		return reference;
	}

	protected String getRemarkTxt(String as[]) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "\r\n");
		}
		return stringbuffer.toString().trim();
	}

	protected String getRemarkTxt(String as[], String len[]) {
		String result_ = "";
		// !
		if (as.length == 0)
			return null;

		// !
		for (int i = 0; i < as.length; i++) {

			// !
			if (len[i].toString().equals("990") || len[i].toString().equals("999") || len[i].toString().equals("99")|| len[i].toString().equals("099")) {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = "\r\n<General Categorization>";
				} else if (as[i].trim().indexOf("<Result") > -1) {
					as[i] = "\r\n<Result>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = "\r\n<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = "\r\n<Recommendation>";
				} else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>\r\n";
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = "\r\n<Gross Description>";
				}else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = "\r\n<Non-Gyn Cytology>";
				//	as[i] = as[i].trim() + "\r\n"; 20201013 김영상 차장님이 예외상황 요청 
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = "\r\n<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = "\r\n<Note>";
				}
				result_ += getDivide() + Common.trimRight(as[i].toString());
			} else {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = "\r\n<General Categorization>";
				} else if (as[i].trim().indexOf("<Result") > -1) {
					as[i] = "\r\n<Result>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = "\r\n<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = "\r\n<Recommendation>";
				} else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>\r\n";
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = "\r\n<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
				// 	as[i] = as[i].trim(); 20201013 김영상 차장 요청으로 수정함
					as[i] = "\r\n<Non-Gyn Cytology>";
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = "\r\n<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = "\r\n" + "\r\n<Note>";
				} else if (as[i].trim().indexOf("Cell block") > -1) {
					as[i] = "" + as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("N-GY") > -1) {
					as[i] = "" + as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("FNA") > -1) {
					as[i] = "" + as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("네오딘") > -1) {
					as[i] = "\r\n" + as[i].trim();
				} else if (as[i].trim().indexOf("Class") > -1) {
					as[i] = "\r\n" + as[i].trim();
				}

				// !
				result_ += Common.trimRight(as[i].toString()) + getDivide() + "\r\n";
			}
		}
		return result_;

		/*
		 * if (hosCode[i].trim().equals("23429")) { if
		 * (result[i].trim().indexOf("<Specimen") > -1) { result[i] = "<Specimen
		 * Adequacy>\r\n"; } if (result[i].trim().indexOf("<General") > -1) {
		 * result[i] = "<General Categorization>\r\n"; } if
		 * (result[i].trim().indexOf("<Interpretation") > -1) { result[i] =
		 * "<Interpretation
		 * Result>\r\n"; } if (result[i].trim().indexOf("<Recommendation") > -1)
		 * { result[i] = "<Recommendation>\r\n"; } }
		 */

		//
	}

	protected String getRemarkTxtNotBlank(String as[], String s) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			//if (i > 0 && !s.equals("0"))
				//stringbuffer.append(insertBlanks("", 38) + insertBlanks("", 38));
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "\r\n");
		}
		return stringbuffer.toString().trim();
	}
	
	protected String getRemarkTxt(String as[], String s) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			if (i > 0 && !s.equals("0"))
				stringbuffer.append(insertBlanks("", 30) + insertBlanks("", 30));
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "\r\n");
		}
		return stringbuffer.toString().trim();
	}
	

	
	protected String getRemarkTxt(Vector vector) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (vector.size() == 0)
			return null;
		for (int i = 0; i < vector.size(); i++) {
			stringbuffer.append(vector.elementAt(i).toString().trim());
			if (vector.size() - 1 != i)
				stringbuffer.append(getDivide() + "\r\n");
		}
		return stringbuffer.toString().trim();
	}

	protected String getRemarkTxt_NGY(String as[], String len[]) {
		String result_ = "";

		// !
		if (as.length == 0)
			return null;

		// !
		for (int i = 0; i < as.length; i++) {
			if (as[i].toString().trim().indexOf("N-GY") > -1)
				continue;

			// !
			if (len[i].toString().equals("990") || len[i].toString().equals("999") || len[i].toString().equals("99")|| len[i].toString().equals("099")) {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = "\r\n<General Categorization>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = "\r\n<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = "\r\n<Recommendation>";
				}
				
				// !
				else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>\r\n";
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = "\r\n<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = "\r\n<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = "\r\n<Note>";
				}
				result_ += getDivide() + Common.trimRight(as[i].toString());
			} else {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = "\r\n<General Categorization>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = "\r\n<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = "\r\n<Recommendation>";
				} else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>\r\n";
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = "\r\n<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = as[i].trim();
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = "\r\n<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = "\r\n" + "\r\n<Note>";
				} else if (as[i].trim().indexOf("Cell block") > -1) {
					as[i] = "" + as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("N-GY") > -1) {
					as[i] = "" + as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("FNA") > -1) {
					as[i] = "" + as[i].trim() + "\r\n";
				} else if (as[i].trim().indexOf("네오딘") > -1) {
					as[i] = "\r\n" + as[i].trim();
				} else if (as[i].trim().indexOf("Class") > -1) {
					as[i] = "\r\n" + as[i].trim();
				}

				// !
				result_ += Common.trimRight(as[i].toString()) + getDivide() + "\r\n";
			}
		}
		return result_;

		/*
		 * if (hosCode[i].trim().equals("23429")) { if
		 * (result[i].trim().indexOf("<Specimen") > -1) { result[i] = "<Specimen
		 * Adequacy>\r\n"; } if (result[i].trim().indexOf("<General") > -1) {
		 * result[i] = "<General Categorization>\r\n"; } if
		 * (result[i].trim().indexOf("<Interpretation") > -1) { result[i] =
		 * "<Interpretation
		 * Result>\r\n"; } if (result[i].trim().indexOf("<Recommendation") > -1)
		 * { result[i] = "<Recommendation>\r\n"; } }
		 */

		//
	}

	//

	protected String getRemarkTxt2(String as[]) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			if (as[i].trim().equals(""))
				continue;
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append("\r\n");
		}
		return stringbuffer.toString().trim();
	}

	protected String getRemarkTxt3(String as[], String len[]) {
		// StringBuffer stringbuffer = new StringBuffer("");
		String result_ = "";

		// !
		if (as.length == 0)
			return null;

		// !
		for (int i = 0; i < as.length; i++) {

			// !
			if (len[i].toString().equals("990") || len[i].toString().equals("999") || len[i].toString().equals("99")|| len[i].toString().equals("099")) {
				result_ += Common.trimRight(as[i].toString());
			} else {
				result_ += Common.trimRight(getDivide() + as[i].toString()) + "\r\n";
				// stringbuffer.append(as[i]);
				// if (as.length - 1 != i)
				// stringbuffer.append(getDivide() + "\r\n");
			}
		}
		// System.out.println(result_);
		return result_;

		// !
		// if (RemarkLen[i].toString().equals("99")) {
		// result_ += Common.trimRight(RemarkResult[i].toString());
		// } else {
		// result_ += Common.trimRight(RemarkResult[i].toString()) + "\r\n";

		//
	}
	
	protected String getRemarkTxt3NCC(String as[], String len[]) {
		// StringBuffer stringbuffer = new StringBuffer("");
		String result_ = "";

		// !
		if (as.length == 0)
			return null;

		// !
		for (int i = 0; i < as.length; i++) {

			// !
			if (len[i].toString().equals("990") || len[i].toString().equals("999") || len[i].toString().equals("99")|| len[i].toString().equals("099")) {
				result_ += Common.trimRight(as[i].toString());
			} else {
				result_ += Common.trimRight(getDivide() + as[i].toString()) + "\n";
				// stringbuffer.append(as[i]);
				// if (as.length - 1 != i)
				// stringbuffer.append(getDivide() + "\r\n");
			}
		}
		// System.out.println(result_);
		return result_;

		// !
		// if (RemarkLen[i].toString().equals("99")) {
		// result_ += Common.trimRight(RemarkResult[i].toString());
		// } else {
		// result_ += Common.trimRight(RemarkResult[i].toString()) + "\r\n";

		//
	}


	protected String getRemarkTxt4(String as[]) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "");
		}
		return stringbuffer.toString().trim();
	}

	protected String getRemarkTxt4Trim(String as[]) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "");
		}
		return stringbuffer.toString().trim();
	}



	protected String getRemarkTxtTrim(String as[], String len[]) {
		String result_ = "";

		// !
		if (as.length == 0)
			return null;

		// !
		for (int i = 0; i < as.length; i++) {

			// !
			if (len[i].toString().equals("990") || len[i].toString().equals("999") || len[i].toString().equals("99")|| len[i].toString().equals("099")) {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = "\n<General Categorization>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = "\n<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = "\n<Recommendation>";
					
				}
				// !
				else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>\n";
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = "\n<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = as[i].trim() + "\n";
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = "\n<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = "\n<Note>";
				}
				result_ += getDivide() + Common.trimRight(as[i].toString());
			} else {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = "\n<General Categorization>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = "\n<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = "\n<Recommendation>";
				} else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>\n";
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = "\n<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = as[i].trim();
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = "\n<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = "\n" + "\n<Note>";
				} else if (as[i].trim().indexOf("Cell block") > -1) {
					as[i] = "" + as[i].trim() + "\n";
				} else if (as[i].trim().indexOf("N-GY") > -1) {
					as[i] = "" + as[i].trim() + "\n";
				} else if (as[i].trim().indexOf("FNA") > -1) {
					as[i] = "" + as[i].trim() + "\n";
				} else if (as[i].trim().indexOf("네오딘") > -1) {
					as[i] = "\n" + as[i].trim();
				}   else if (as[i].trim().indexOf("Class") > -1) {
					as[i] = "\n" + as[i].trim();
				}

				// !
				result_ += Common.trimRight(as[i].toString()) + getDivide() + "\n";
			}
		}
		return result_;

		//
	}

	protected String getRemarkTxtTrim(String as[], String s) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			if (i > 0 && !s.equals("0"))
				stringbuffer.append(insertBlanks("        ", 30) + insertBlanks("        ", 30));
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "\n");
		}
		return stringbuffer.toString().trim();
	}

	protected String getRemarkTxtTrim2(String as[], String s) {
		StringBuffer stringbuffer = new StringBuffer("");
		if (as.length == 0)
			return null;
		for (int i = 0; i < as.length; i++) {
			if (i > 0 && !s.equals("0"))
				stringbuffer.append(insertBlanks("        ", 30) + insertBlanks("        ", 30));
			stringbuffer.append(as[i]);
			if (as.length - 1 != i)
				stringbuffer.append(getDivide() + "\n");
		}
		return stringbuffer.toString().trim();
	}

	protected Vector getResult_BMA(String as[]) {
		Vector vector = new Vector();
		vector.addElement("검 사 종 목");
		vector.addElement("Class");
		vector.addElement("검 사 종 목");
		vector.addElement("Class");
		for (int i = 1; i < as.length; i++) {
			String as1[] = Common.getArrayTypeData_CheckLastNoTrim(as[i].toString());
			for (int j = 0; j < as1.length; j++)
				vector.addElement(Common.trimRight(as1[j]));

		}

		return vector;
	}

	protected String getResultEEP2(String as[]) {

		String s = "";
		String s1 = "";
		// String s2 = "";
		// String s4 = "";
		// String s6 = "";
		// String s8 = "";
		String s10 = "";
		String as1[] = null;
		try {
			mPatientData = new PatientInformation();
		} catch (Exception exception) {
			return "PatientInformation :" + exception.getMessage();
		}
		try {
			as1 = mPatientData.getPatientResultEP(as[0], as[1], as[2]);
		} catch (Exception exception1) {
			return "getPatientResultEP :" + exception1.getMessage();
		}
		try {
			mPatientData.getResultRemark(as[0], as[1], as1[5], as1[4]);
		} catch (Exception exception2) {
			return "getResultRemark :" + exception2.getMessage();
		}
		Vector vector = new Vector();
		int i = as1.length;
		for (int j = 13; j < i; j++)
			vector.addElement(as1[j].toString().trim());
		s = getResultEEP2_Title(as[2].toString());
		s = s + "\r\n";
		int k = vector.size() / 6;
		int l = 0;
		for (int i1 = 0; i1 < k; i1++) {
			vector.elementAt(l++);
			String s3;
			String s5;
			String s7;
			String s9;
			if (as[2].toString().equals("00309")) {
				if (Integer.parseInt(as[0].toString()) < 0x132697c) {
					s1 = appendBlanks(vector.elementAt(l++).toString(), 20);
					s3 = appendBlanks(df.format(getDouble(vector.elementAt(l++).toString())), 5);
					s7 = appendBlanks(df.format(getDouble(vector.elementAt(l++).toString())), 10);
					s5 = appendBlanks(vector.elementAt(l++).toString(), 5);
					s9 = appendBlanks(vector.elementAt(l++).toString(), 30) + getDivide() + "\r\n";
				} else {
					s1 = appendBlanks(vector.elementAt(l++).toString(), 20);
					s5 = appendBlanks(df.format(getDouble(vector.elementAt(l++).toString())), 5);
					s3 = appendBlanks(df.format(getDouble(vector.elementAt(l++).toString())), 10);
					s7 = appendBlanks(vector.elementAt(l++).toString(), 5);
					s9 = appendBlanks(vector.elementAt(l++).toString(), 30) + getDivide() + "\r\n";
				}
			} else {
				s1 = appendBlanks(vector.elementAt(l++).toString(), 20);
				s5 = appendBlanks(df.format(getDouble(vector.elementAt(l++).toString())), 5);
				s3 = appendBlanks(df.format(getDouble(vector.elementAt(l++).toString())), 10);
				s7 = appendBlanks(vector.elementAt(l++).toString(), 5);
				s9 = appendBlanks(vector.elementAt(l++).toString(), 30) + getDivide() + "\r\n";
			}
			
			if(as[2].toString().equals("00334")) s9="\r\n";

			if (addLine(s1)) {
				if (removeLine(s1))
					s = s + "\r\n";
				else
					s = s + gubun2;
				s5 = "";
				//s7 = "";
				//s9 = "";
			}
			s = s + s10 + s1 + s3 + s5 + s7 + s9;
		}

		if (addLine(s1))
			if (as[2].toString().equals("00309"))
				s = s + "g/dL" + gubun1;
			else if (as[2].toString().equals("00323"))
				s = s + "mg/dL" + gubun1;
			else
				s = s + gubun1;

		return s;
	}

	protected String getResultEEP2_Title(String s) {
		String s1 = "";
		s1 = "";
		if (s.equals("00305"))
			return "수탁 종목임====> 이건 다시 확인해봐야 합니다";
		if (s.equals("00301") || s.equals("00304") || s.equals("00305") || s.equals("00307") || s.equals("00312") || s.equals("00313")) {
			if (s.equals("00301"))
				s1 = "<< ALP isoenzyme >>" + getDivide() + "\r\n\n";
			else if (s.equals("00312"))
				s1 = "<< LDH isoenzyme >>" + getDivide() + "\r\n\n";
			else
				s1 = s1 + gubun1;
			s1 = s1 + "* 검사방법 : Electrophoresis " + getDivide() + "\r\n";
			s1 = s1 + gubun1;
			s1 = s1 + appendBlanks("Fraction", 20) + appendBlanks("IU/L", 12) + appendBlanks("%", 7) + appendBlanks("% Reference Range", 30);
			s1 = s1 + gubun2;
			return s1;
		}
		else if (s.equals("00323") || s.equals("00324") || s.equals("00314")|| s.equals("00334")) {
			if (s.equals("00323"))
				s1 = "<< Protein E.P (Urine) >>" + getDivide() + "\r\n\n";
			if (s.equals("00334"))
				s1 = "<< Protein E.P (24U) >>" + getDivide() + "\r\n\n";
			s1 = s1 + "* 검사방법 : Electrophoresis " + getDivide() + "\r\n";
			s1 = s1 + gubun1;
			s1 = s1 + appendBlanks("Fraction", 20) + appendBlanks("mg/dL", 12) + appendBlanks("%", 7) + appendBlanks("% Reference Range", 30);
			s1 = s1 + gubun2;
			return s1;
		}
		else if (s.equals("00309") || s.equals("00322")) {
			if (s.equals("00309"))
				s1 = "<< 혈청단백 전기영동(Protein electrophoresis) >>" + getDivide() + "\r\n\n";
			s1 = s1 + "* 검사방법 : Electrophoresis " + getDivide() + "\r\n";
			s1 = s1 + gubun1;
			s1 = s1 + appendBlanks("Fraction", 20) + appendBlanks("g/dL", 12) + appendBlanks("%", 7) + appendBlanks("g/dL (%) Reference Range", 30);
			s1 = s1 + gubun2;
			return s1;
		}
		else if (s.equals("00310") || s.equals("00316")) {
			s1 = s1 + "* 검사방법 : Electrophoresis " + getDivide() + "\r\n";
			s1 = s1 + appendBlanks("Fraction", 20) + appendBlanks("%", 7) + appendBlanks("% Reference Range", 20);
			return s1;
		} 
		else {
			return "전기영동 검사가 아님니다";
		}
	}

	protected String getResultHBV(String s) {
		if (s.equals("7124502")) {
			return "Lamivudine";
		}
		if (s.equals("7124503")) {
			return "Lamivudine, Famciclovir"; // 1
		}
		if (s.equals("7124504")) {
			return "Lamivudine, Famciclovir, Emtricitabine(FTC)"; // 2
		}
		if (s.equals("7124505")) {
			return "Lamivudine, Famciclovir, Emtricitabine(FTC)";
		}
		if (s.equals("7124506")) {
			return "Lamivudine, Telbivudine(Ldt) Emtricitabine(FTC)"; // 4
		}
		if (s.equals("7124507")) {
			return "Lamivudine, Emtricitabine(FTC)"; // 5
		}
		if (s.equals("7124508")) {
			return "Lamivudine";
		}
		if (s.equals("7124509")) {
			return "Lamivudine, Famciclovir";
		}
		if (s.equals("7124510")) {
			// return "Lamivudine, Famciclovir, Adefovir";
			return "Adefovir";
		}
		if (s.equals("7124511")) {
			return "Adefovir,Lamivudine, Famciclovir";
		}
		if (s.equals("7124512")) {
			return "Adefovir";
		}
		if (s.equals("7124513")) {
			return "Adefovir";
		}
		if (s.equals("7124514")) {
			return "Adefovir";
		}
		if (s.equals("7124515")) {
			return "Adefovir";
		}
		if (s.equals("7124516")) {
			return "Adefovir";
		}
		if (s.equals("7124517")) {
			return "Adefovir";
		}
		if (s.equals("7124518")) {
			return "Entecavir";
		}
		if (s.equals("7124519")) {
			return "Entecavir";
		}
		if (s.equals("7124520")) {
			return "Entecavir";
		}
		if (s.equals("7124521")) {
			return "Entecavir";
		}
		if (s.equals("7124522")) {
			return "Tenofovir";
		}

		// !
		return "";
	}

	// private String getResultMAST(String as[]) {
	// String s = "";
	// mPatientData = new PatientInformation();
	// String as1[] = mPatientData.getPatientResultEP(as[0], as[1], as[2]);
	// mPatientData.getResultRemark(as[0], as[1], as1[5], as1[4]);
	// Vector vector = new Vector();
	// int i = as1.length;
	// for (int j = 14; j < i; j++)
	// vector.addElement(as1[j].toString().trim());
	//
	// s = s + "\r\n";
	// s = s + gubun1;
	// s = s + appendBlanks("검사종목", 17) + appendBlanks("CLASS", 5)
	// + appendBlanks("검사종목", 17) + appendBlanks("CLASS", 5)
	// + (s = s + gubun2);
	// int k = vector.size() / 6;
	// int l = 0;
	// for (int i1 = 0; i1 < k; i1++)
	// vector.elementAt(l++);
	//
	// return s;
	// }

	protected String getRemarkTxt_Txt(String as[], String len[]) {
		String result_ = "";
		divide = "@^";
		// !
		if (as.length == 0)
			return null;

		// !
		for (int i = 0; i < as.length; i++) {

			// !
			if (len[i].toString().equals("990") || len[i].toString().equals("999") || len[i].toString().equals("99")|| len[i].toString().equals("099")) {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = getDivide() + "<General Categorization>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = getDivide() + "<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = getDivide() + "<Recommendation>";
				}
				
				// !
				else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>" + getDivide();
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = getDivide() + "<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = as[i].trim() + getDivide() + "";
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = getDivide() + "<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = getDivide() + "<Note>";
				}
				result_ += getDivide() + Common.trimRight(as[i].toString());
			} else {
				if (as[i].trim().indexOf("<Specimen") > -1) {
					as[i] = "<Specimen Adequacy>";
				} else if (as[i].trim().indexOf("<General") > -1) {
					as[i] = getDivide() + "<General Categorization>";
				} else if (as[i].trim().indexOf("<Interpretation") > -1) {
					as[i] = getDivide() + "<Interpretation Result>";
				} else if (as[i].trim().indexOf("<Recommendation") > -1) {
					as[i] = getDivide() + "<Recommendation>";
				} else if (as[i].trim().indexOf("<Pathological") > -1) {
					as[i] = "<Pathological Diagnosis>" + getDivide();
				} else if (as[i].trim().indexOf("<Gross") > -1) {
					as[i] = getDivide() + "<Gross Description>";
				} else if (as[i].trim().indexOf("<Non-Gyn") > -1) {
					as[i] = as[i].trim();
				} else if (as[i].trim().indexOf("<Microscopic") > -1) {
					as[i] = getDivide() + "<Microscopic Findings>";
				} else if (as[i].trim().indexOf("<Note") > -1) {
					as[i] = getDivide() + getDivide() + "<Note>";
				} else if (as[i].trim().indexOf("Cell block") > -1) {
					as[i] = "" + as[i].trim() + getDivide();
				} else if (as[i].trim().indexOf("N-GY") > -1) {
					as[i] = "" + as[i].trim() + getDivide();
				} else if (as[i].trim().indexOf("FNA") > -1) {
					as[i] = getDivide() + as[i].trim() + getDivide();
				} else if (as[i].trim().indexOf("네오딘") > -1) {
					as[i] = getDivide() + as[i].trim();
				}  else if (as[i].trim().indexOf("Class") > -1) {
					as[i] = getDivide() + as[i].trim();
				}

				// !
				result_ += Common.trimRight(as[i].toString()) + getDivide();
			}
		}
		return result_;

	}

	protected synchronized String getTextResultValueTxt(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);
			
			s4 = getRemarkTxt_Txt(as, len);
			if (s4 == null)
				s4 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s4;
	}

	protected String getResultMAST(String s, Vector vector) {
		String s1 = "";
		String as[] = new String[43];
		int i = as.length;
		as[0] = vector.elementAt(0).toString();
		as[1] = vector.elementAt(1).toString();
		as[2] = vector.elementAt(2).toString();
		as[3] = vector.elementAt(3).toString();
		as[4] = vector.elementAt(4).toString();
		as[5] = vector.elementAt(5).toString();
		as[6] = vector.elementAt(6).toString();
		try {
			as[7] = vector.elementAt(41).toString();
		} catch (Exception _ex) {
			as[7] = "";
		}
		as[8] = vector.elementAt(38).toString();
		as[9] = vector.elementAt(9).toString();
		as[10] = vector.elementAt(10).toString();
		as[11] = vector.elementAt(7).toString();
		as[12] = vector.elementAt(8).toString();
		as[13] = vector.elementAt(11).toString();
		as[14] = vector.elementAt(12).toString();
		as[15] = vector.elementAt(13).toString();
		as[16] = vector.elementAt(14).toString();
		as[17] = vector.elementAt(15).toString();
		as[18] = vector.elementAt(39).toString();
		as[19] = vector.elementAt(20).toString();
		as[20] = vector.elementAt(16).toString();
		as[21] = vector.elementAt(17).toString();
		as[22] = vector.elementAt(18).toString();
		as[23] = vector.elementAt(19).toString();
		as[24] = vector.elementAt(21).toString();
		as[25] = vector.elementAt(22).toString();
		as[26] = vector.elementAt(23).toString();
		as[27] = vector.elementAt(24).toString();
		as[28] = vector.elementAt(25).toString();
		as[29] = vector.elementAt(36).toString();
		as[30] = vector.elementAt(37).toString();
		as[31] = vector.elementAt(40).toString();
		as[32] = vector.elementAt(26).toString();
		as[33] = vector.elementAt(27).toString();
		as[34] = vector.elementAt(28).toString();
		as[35] = vector.elementAt(29).toString();
		as[36] = vector.elementAt(30).toString();
		as[37] = vector.elementAt(31).toString();
		as[38] = vector.elementAt(32).toString();
		as[39] = vector.elementAt(33).toString();
		as[40] = vector.elementAt(34).toString();
		as[41] = vector.elementAt(35).toString();
		try {
			as[42] = vector.elementAt(42).toString();
		} catch (Exception w) {
		}
		for (int j = 0; j < i; j++) {
			try {
				s1 = s1 + getDivide() + "\r\n" + as[j++] + as[j];
			} catch (Exception xx) {
				s1 = s1 + getDivide() + "\r\n" + as[--j];
			}
			if (j == 7 || j == 17 || j == 31 || j == 35)
				s1 = s1 + getDivide() + "\r\n";
		}
		return s + s1;
	}

	protected String getResultMAST_Two(String s, Vector vector) {
		String s1 = "";
		String as[] = new String[vector.size()];
		int i = as.length;		
		try {
		     for (int j = 0; j < as.length; j++) {
		    	 as[j] = vector.elementAt(j).toString();	
			}
			for (int j = 0; j < i; j++) {     
				if(as[j].toString().trim().indexOf("MAST")>-1){
					continue;
				}
				try {
					s1 = s1 + getDivide() + "\r\n" + as[j++] + as[j];
				} catch (Exception xx) {
					s1 = s1 + getDivide() + "\r\n" + as[--j];
				}
//				if (j == 7 || j == 17 || j == 31 || j == 35)
//					s1 = s1 + getDivide() + "\r\n";
			}
			return s + s1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return"";
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-10-08 오전 3:28:30)
	 * 
	 * @return java.lang.String
	 */
	private String getResultRemark(String param) {
		String str = "";
		Object[] remark1 = Common.getArrayTypeData_CheckLast(param);
		if (remark1 != null) {

			// !
			int len = remark1.length;
			for (int i = 0; i < len; i++) {
				str += remark1[i].toString() + "\n";
			}
		}
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-10-08 오전 12:46:47)
	 */
	protected String getResultTriple(java.lang.String[] id) {
		// !
		AbstractDpc dpc_;
		DPCParameter parm_;
		// !
		boolean is90027 = false; // First double maker
		boolean is99934 = false; // Integrated test 1차
		boolean is99935 = false; // Sequential test 1차
		boolean is99936 = false; // Sequential test 2차
		boolean is90100 = false; // Quad test
		// boolean is90028 = false; // Triple marker
		// boolean is90029 = false; // Double marker

		// ! 검사결과
		// 가져오기***************************************************************
		String[] tempArray = new String[24];
		String temp_title = "";
		// rtnResult += "* 검사방법 : 선별검사 \n";
		// rtnResult += "* 선별검사결과 : \n";
		String rtnResult = "";
		try {
			// !
			dpc_ = new com.neodin.result.DpcOfMC308RMS4();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자, 접수번호

			DPCParameter parm = dpc_.getParm();

			// ! 임신중기검사
			tempArray[0] = parm.getStringParm(14).trim(); // AFP
			tempArray[1] = parm.getStringParm(15).trim(); // MOM
			// !
			tempArray[2] = parm.getStringParm(20).trim(); // FREE
			tempArray[3] = parm.getStringParm(21).trim(); // MOM
			tempArray[4] = parm.getStringParm(18).trim(); // HCG
			tempArray[5] = parm.getStringParm(19).trim(); // MOM
			tempArray[6] = parm.getStringParm(16).trim(); // PAPP
			tempArray[7] = parm.getStringParm(17).trim(); // MOM
			tempArray[8] = parm.getStringParm(22).trim(); // UE3
			tempArray[9] = parm.getStringParm(23).trim(); // MOM
			tempArray[10] = parm.getStringParm(24).trim(); // NT
			tempArray[11] = parm.getStringParm(25).trim(); // MOM
			tempArray[12] = parm.getStringParm(34).substring(1, 16).trim(); // IuhibinA
			tempArray[13] = parm.getStringParm(34).substring(16, 31).trim(); // MOM

			// ! 결과및 판정
			tempArray[14] = parm.getStringParm(26).trim(); // 나이
			tempArray[15] = parm.getStringParm(27).trim(); // 결과
			tempArray[16] = parm.getStringParm(28).trim(); // 다운
			tempArray[17] = parm.getStringParm(29).trim(); // 결과
			tempArray[18] = parm.getStringParm(30).trim(); // 에드워드
			tempArray[19] = parm.getStringParm(31).trim(); // 결과
			tempArray[20] = parm.getStringParm(32).trim(); // 신경관결손
			tempArray[21] = parm.getStringParm(33).trim(); // 결과
			
			// 문장결과(판정) 불러오기
			// }
			rtnResult += gubun1;
			rtnResult += appendBlanks("검사항목", 10) + appendBlanks("결      과", 15) + appendBlanks("MOM", 10);
			rtnResult += gubun2;
			// !
			if (!tempArray[0].equals(""))
				rtnResult += appendBlanks("AFP", 10) + appendBlanks(tempArray[0] + " ng/mL", 15) + appendBlanks(tempArray[1] + " mom", 10) + "\n";
			if (!tempArray[2].equals(""))
				if (Integer.parseInt(id[0].toString().trim()) > 20140430) {
					rtnResult += appendBlanks("hCG", 10) + appendBlanks(tempArray[2] + " IU/mL", 15) + appendBlanks(tempArray[3] + " mom", 10) + "@";
				} else {
					rtnResult += appendBlanks("Freeb-HCG", 10) + appendBlanks(tempArray[2] + " ng/mL", 15) + appendBlanks(tempArray[3] + " mom", 10)
							+ "@";

				}
			if (!tempArray[4].equals(""))
				rtnResult += appendBlanks("HCG", 10) + appendBlanks(tempArray[4] + " ug/mL", 15) + appendBlanks(tempArray[5] + " mom", 10) + "\n";
			if (!tempArray[6].equals(""))
				if (Integer.parseInt(id[0].toString().trim()) > 20140430) {
					rtnResult += appendBlanks("PAPP-A", 10) + appendBlanks(tempArray[6] + " ng/mL", 15) + appendBlanks(tempArray[7] + " mom", 10)
							+ "@";
				} else {
					rtnResult += appendBlanks("PAPP-A", 10) + appendBlanks(tempArray[6] + " IU/mL", 15) + appendBlanks(tempArray[7] + " mom", 10)
							+ "@";
				}
			if (!tempArray[8].equals(""))
				rtnResult += appendBlanks("uE3", 10) + appendBlanks(tempArray[8] + " nmol/L", 15) + appendBlanks(tempArray[9] + " mom", 10) + "\n";
			if (!tempArray[10].equals(""))
				rtnResult += appendBlanks("NT", 10) + appendBlanks(tempArray[10] + " mm", 15) + appendBlanks(tempArray[11] + " mom", 10) + "\n";
			if (!tempArray[12].equals(""))
				rtnResult += appendBlanks(" Inhibin A ", 12) + appendBlanks(tempArray[12] + " pg / mL ", 13)
						+ appendBlanks(tempArray[13] + " mom ", 10) + "\n";
			rtnResult += gubun2;

			// !
			dpc_ = new com.neodin.result.DpcOfMC308RMS3();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자,
																	// 접수번호
			parm_ = dpc_.getParm();
			// !
			tempArray[22] = parm_.getStringParm(3).trim(); // 다운/에드워드 판정
			tempArray[23] = parm_.getStringParm(4).trim(); // 신경관 결손
			

			// !
			dpc_ = new com.neodin.result.DpcOfMC308RM3();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자,
																	// 접수번호
			parm_ = dpc_.getParm();
			// !

			try {
				if (parm_.getStringParm(5) != null) {
					String pcode = parm_.getStringParm(5);
					if (pcode.equals("90027")) {
						is90027 = true;
					} else if (pcode.equals("90029")) {
						is90029 = true;
					} else if (pcode.equals("90028")) {
						is90028 = true;
					} else if (pcode.equals("90100")) {
						is90100 = true;
					} else if (pcode.equals("99934")) {
						is99934 = true;
					} else if (pcode.equals("99935")) {
						is99935 = true;
					} else if (pcode.equals("99936")) {
						is99936 = true;
					}
				}
			} catch (Exception ee) {
			}
			// !
			// !
			// boolean is90027 = false; //First double maker
			// boolean is99934 = false; //Integrated test 1차
			// boolean is99935 = false; //Sequential test 1차
			// boolean is99936 = false; //Sequential test 2차
			// boolean is90100 = false; //Quad test
			// boolean is90028 = false; //Triple marker
			// boolean is90029 = false; //Double marker
			// //}
		} catch (Exception e) {
		}
		if (is99934) {
			temp_title = "<<Integrated test 1차 결과보고>>\n";
			rtnResult = temp_title + "\n\n" + rtnResult + "\n";
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22) + appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13) + appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22) + appendBlanks(" 2.5 MOM ", 12) + appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\n" + appendBlanks(" 에드워드증후군 ", 22) + appendBlanks(" 1 : 200 ", 12) + appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\n" + appendBlanks(" 다운증후군 ", 22) + appendBlanks(" 1 : 495 ", 12) + appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);



			
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
			
		} else if (is99935) {
			temp_title = "<<Sequential test 1차 결과보고>>\n";
			rtnResult = temp_title + "\n\n" + rtnResult + "\n";
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22) + appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13) + appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22) + appendBlanks(" 2.5 MOM ", 12) + appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\n" + appendBlanks(" 에드워드증후군 ", 22) + appendBlanks(" 1 : 300 ", 12) + appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\n" + appendBlanks(" 다운증후군 ", 22) + appendBlanks(" 1 : 41 ", 12) + appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		} else if (is99936) {
			temp_title = "<<Sequential test 2차 결과보고>>\n";
			rtnResult = temp_title + "\n\n" + rtnResult + "\n";

			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22) + appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13) + appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22) + appendBlanks(" 2.5 MOM ", 12) + appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\n" + appendBlanks(" 에드워드증후군 ", 22) + appendBlanks(" 1 : 200 ", 12) + appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\n" + appendBlanks(" 다운증후군 ", 22) + appendBlanks(" 1 : 450 ", 12) + appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		} else if (is90027) {
			temp_title = "<<First double maker 결과보고>>\n";
			rtnResult = temp_title + "\n\n" + rtnResult + "\n";
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22) + appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13) + appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22) + appendBlanks(" 2.5 MOM ", 12) + appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\n" + appendBlanks(" 에드워드증후군 ", 22) + appendBlanks(" 1 : 300 ", 12) + appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\n" + appendBlanks(" 다운증후군 ", 22) + appendBlanks(" 1 : 250 ", 12) + appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		} else if (is90100) {
			temp_title = "<<Quad test 결과보고>>\n";
			rtnResult = temp_title + "\n\n" + rtnResult + "\n";
			// !
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22) + appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13) + appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22) + appendBlanks(" 2.5 MOM ", 12) + appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\n" + appendBlanks(" 에드워드증후군 ", 22) + appendBlanks(" 1 : 300 ", 12) + appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\n" + appendBlanks(" 다운증후군 ", 22) + appendBlanks(" 1 : 250 ", 12) + appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		} else {
			// !
			temp_title = "<< 기형아  검사결과보고 >>\n";
			rtnResult = temp_title + "\n\n" + rtnResult + "\n";
			rtnResult += gubun1;
			rtnResult += appendBlanks(" 위 험 도 ", 22) + appendBlanks(" 기준치 ", 12) + appendBlanks(" 결 과 ", 13) + appendBlanks(" 선별검사항목 ", 10);
			rtnResult += gubun2;
			rtnResult += appendBlanks(" 신경관 결손증 ", 22) + appendBlanks(" 2.5 MOM ", 12) + appendBlanks(Common.cutZero(tempArray[21]), 13)
					+ appendBlanks(tempArray[20], 10);
			rtnResult += "\n" + appendBlanks(" 에드워드증후군 ", 22) + appendBlanks(" 1 : 200 ", 12) + appendBlanks(Common.cutZero(tempArray[19]), 13)
					+ appendBlanks(tempArray[18], 10);
			rtnResult += "\n" + appendBlanks(" 다운증후군 ", 22) + appendBlanks(" 1 : 270 ", 12) + appendBlanks(Common.cutZero(tempArray[17]), 13)
					+ appendBlanks(tempArray[16], 10);
			if(Integer.parseInt(id[0]) <= 20170608){
				
			rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
					+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
					+ appendBlanks(tempArray[14], 10);
			rtnResult += gubun1;
			rtnResult += "@@* Comment : @@";
			} else {
				
				rtnResult += "@" + appendBlanks(" Down SD Risk by Age ", 30)
						+ appendBlanks(" 만 35세 ", 12)
	//				+ appendBlanks(" 1 : 270 ", 12)
	//				+ appendBlanks(Common.cutZero(tempArray[15]), 13) 
						+ appendBlanks(tempArray[14], 10);
				rtnResult += gubun1;
				rtnResult += "@@* Comment : @@";
				} 
		
		}

		// if (id[2].toString().equals(" 41381 ")) {
		if (!tempArray[23].equals(""))
			rtnResult += getResultRemark(tempArray[23] + "\n\n");
		// } else {
		if (!tempArray[22].equals(""))
			rtnResult += getResultRemark(tempArray[22] + "");
		// }

		return rtnResult;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-10-08 오전 12:46:47)
	 */
	protected String getResultRSTS(java.lang.String[] id) {
		// !
		AbstractDpc dpc_;
		DPCParameter parm_;
		// !
		boolean is90100 = false; // Quad test
		// boolean is90028 = false; // Triple marker
		// boolean is90029 = false; // Double marker

		// ! 검사결과
		// 가져오기***************************************************************
		String[] tempArray = new String[10];
		String temp_title = "<<임신중독증위험도 분석 결과>>\n";
		String rtnResult = "";
		try {
			// !
			dpc_ = new com.neodin.files.DpcOfMC308RMS7();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자, 접수번호

			DPCParameter parm = dpc_.getParm();

	        //! 임신중기검사
	        tempArray[0]= parm.getStringParm(14).trim(); //PIGF 결과
	        tempArray[1]= parm.getStringParm(15).trim(); //PIGF MOM
	        tempArray[2]= parm.getStringParm(16).trim(); //PAPP-A 결과
	        tempArray[3]= parm.getStringParm(17).trim(); //PAPP-A MOM
	        tempArray[4]= parm.getStringParm(24).trim(); //Prior Risk Factors Only 조기임신중독증 위험도
	        tempArray[5]= parm.getStringParm(26).trim(); //Prior Risk Factors Only 후기임신중독증 위험도
	        tempArray[6]= parm.getStringParm(25).trim(); //After Blood Pressure, Biochemistry & Doppler  조기임신중독증 위험도 
	        tempArray[7]= parm.getStringParm(27).trim(); //After Blood Pressure, Biochemistry & Doppler  후기임신중독증 위험도
	        tempArray[8]= parm.getStringParm(28).trim(); //조기임신중독증 위험도 최종결과 
	        tempArray[9]= parm.getStringParm(29).trim(); //후기임신중독증 위험도 최종결과 
	        

	        rtnResult = rtnResult+"구분                              조기임신중독증 위험도       후기임신중독증 위험도\n";
	        rtnResult = rtnResult+"Prior Risk Factors Only*             1:"+tempArray[4]+"                        1:"+tempArray[5]+"\n";
	        rtnResult = rtnResult+"After Blood Pressure,                1:"+tempArray[6]+"                        1:"+tempArray[7]+"\n";
	        rtnResult = rtnResult+"Biochemistry & Doppler**                                                                                 \n";
	        rtnResult = rtnResult+"Cut-off Risk                            1:20                          1:20\n";
	        rtnResult = rtnResult+"최종결과                                "+tempArray[8]+"                  "+tempArray[9]+"\n\n";
	        rtnResult = rtnResult+"<<바이오마커 결과>>\n";
	        rtnResult = rtnResult+"검사명                              결과                          MoM\n";
	        rtnResult = rtnResult+"PIGF                               "+tempArray[0]+"     pg/mL               "+ tempArray[1]+"\n";
	        rtnResult = rtnResult+"PAPP-A                          "+tempArray[2]+"  mU/L                 "+ tempArray[3]+"\n";
	        rtnResult = temp_title+rtnResult;
		} catch (Exception ee) {
		}

		return rtnResult;
	}

	public int getStsrtResultRow() {
		return result_start_row;
	}

	protected synchronized String getTextResultValue(String s, String s1, String s2, String s3, int cnt) {
		String s4 = null;
		try {
			
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))	{
//				System.out.println("===========================문장결과CL[1] 결과 없는 현상 e=========================");
				
				try {
//					LogChaing log = new LogChaing(99, "홈페이지 장문결과 로그");
//					log.setEtc1(s).setEtc2(s1).setEtc3(s2).setEtc4(s3).setEtc5("누락").addLog();
				} catch (Exception e) {e.printStackTrace();	}
				
				((AbstractDpc) getDpc().get("문장결과재집계CL")).processDpc(new Object[] { s, s1, s2, s3 });
				
				mid = ((AbstractDpc) getDpc().get("문장결과재집계CL")).getParm().getStringParm(7);
				
				if(((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3 , getmid()})){

					String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
					String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);

					s4 = getRemarkTxt(as, len);
					System.out.println(s4);
//					LogChaing log = new LogChaing(99, "홈페이지 장문결과 로그 재생성 성공");
//					log.setEtc1(s).setEtc2(s1).setEtc3(s2).setEtc4(s3).setEtc5("성공").addLog();
					
					return s4;
				}else{
//					LogChaing log = new LogChaing(99, "홈페이지 장문결과 로그 재생성 실패");
//					log.setEtc1(s).setEtc2(s1).setEtc3(s2).setEtc4(s3).setEtc5("실패").addLog();
					return "";					
				}

				
			}

			
			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);

			s4 = getRemarkTxt(as, len);
			if (s4 == null){
//				System.out.println("===========================문장결과CL[2] 결과 없는 현상 s=========================");
				for (String string : as) {
//					System.out.println("====[as]=========");
//					System.out.println(string);
				}
				for (String string : len) {
//					System.out.println("====[len]=========");
//					System.out.println(string);
				}
//				System.out.println("===========================문장결과CL[2] 결과 없는 현상 e=========================");
				
				s4 = "";
			}
		} catch (Exception _ex) {
			System.out.println("===========================문장결과CL[3] 결과 없는 현상 s=========================");
			System.out.println(_ex.getMessage());
			System.out.println("===========================문장결과CL[3] 결과 없는 현상 e=========================");
			return "";
		}
		return s4;
	}
	
	protected String getResultMSAFP(java.lang.String[] id) {
		// !
		AbstractDpc dpc_;
		// ! 검사결과
		// 가져오기***************************************************************
		String[] tempArray = new String[2];
		String rtnResult = "";
		try {
			// !
			dpc_ = new com.neodin.result.DpcOfMC308RMS4();
			dpc_.processDpc(new String[] { "NML", id[0], id[1] }); // 접수일자, 접수번호

			DPCParameter parm = dpc_.getParm();

			// ! 임신중기검사
			tempArray[0] = parm.getStringParm(14).trim(); // AFP
			tempArray[1] = parm.getStringParm(15).trim(); // MOM
			rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
			//검사코드까지 넘어오면 해당 검사코드 결과리턴
			if(id.length>=3)
			{
				if(id[2].equals("41338"))
				{
					tempArray[0] = parm.getStringParm(34).substring(1, 16).trim(); // IuhibinA
					tempArray[1] = parm.getStringParm(34).substring(16, 31).trim(); // MOM
					rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
				}
				else 	if(id[2].equals("41381"))
				{
					tempArray[0] = parm.getStringParm(14).trim(); // AFP
					tempArray[1] = parm.getStringParm(15).trim(); // MOM
					rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
				}
				else 	if(id[2].equals("41382"))
				{
					tempArray[0] = parm.getStringParm(18).trim(); // HCG
					tempArray[1] = parm.getStringParm(19).trim(); // MOM
					rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
				}
				else 	if(id[2].equals("41383"))
				{
					tempArray[0] = parm.getStringParm(22).trim(); // UE3
					tempArray[1] = parm.getStringParm(23).trim(); // MOM
					rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
				}
				else 	if(id[2].equals("41121"))
				{
					tempArray[0] = parm.getStringParm(16).trim(); // PAPP
					tempArray[1] = parm.getStringParm(17).trim(); // MOM
					rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
				}
				else 	if(id[2].equals("41431"))
				{
					tempArray[0] = parm.getStringParm(24).trim(); // NT
					tempArray[1] = parm.getStringParm(25).trim(); // MOM
					rtnResult ="result:"+tempArray[0]+" ,mom:"+tempArray[1]+" ng/mL ";
				}else if(id[2].equals("주수"))
				{
					rtnResult= Trim.cutWeek(parm.getStringParm(6).trim()); // LMP 주수
				}
			}
		}catch(Exception e){
			return rtnResult;
		}
			return rtnResult;
	}
	
	protected synchronized String getTextResultValue(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))	{
//				System.out.println("===========================문장결과CL[1] 결과 없는 현상 e=========================");
				try {
					//LogChaing log = new LogChaing(99, "홈페이지 장문결과 로그");
					//log.setEtc1(s).setEtc2(s1).setEtc3(s2).setEtc4(s3).setEtc5("누락").addLog();
				} catch (Exception e) {e.printStackTrace();	}
				
				((AbstractDpc) getDpc().get("문장결과재집계CL")).processDpc(new Object[] { s, s1, s2, s3});

				mid = ((AbstractDpc) getDpc().get("문장결과재집계CL")).getParm().getStringParm(7);
				
				if(((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3, getmid() })){
					
					String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
					String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);

					s4 = getRemarkTxt(as, len);
//					System.out.println(s4);
					//LogChaing log = new LogChaing(99, "홈페이지 장문결과 로그 재생성 성공");
					//log.setEtc1(s).setEtc2(s1).setEtc3(s2).setEtc4(s3).setEtc5("성공").addLog();

					
					return s4;
				}else{
					//LogChaing log = new LogChaing(99, "홈페이지 장문결과 로그 재생성 실패");
					//log.setEtc1(s).setEtc2(s1).setEtc3(s2).setEtc4(s3).setEtc5("실패").addLog();
					return "";					
				}
			}
			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);

			s4 = getRemarkTxt(as, len);
			if (s4 == null){
//				System.out.println("===========================문장결과CL[2] 결과 없는 현상 s=========================");
				for (String string : as) {
//					System.out.println("====[as]=========");
//					System.out.println(string);
				}
				for (String string : len) {
//					System.out.println("====[len]=========");
//					System.out.println(string);
				}
//				System.out.println("===========================문장결과CL[2] 결과 없는 현상 e=========================");
				
				s4 = "";
			}
		} catch (Exception _ex) {
//			System.out.println("===========================문장결과CL[3] 결과 없는 현상 s=========================");
//			System.out.println(_ex.getMessage());
//			System.out.println("===========================문장결과CL[3] 결과 없는 현상 e=========================");
			return "";
		}
		return s4;
	}

	protected synchronized String getTextResultValue_NGY(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";

			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);
			
			s4 = getRemarkTxt_NGY(as, len);
			if (s4 == null)
				s4 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s4;
	}

	protected synchronized String getTextResultValue2(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3 , getmid()}))
				return "";

			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			// String len[] = Common.getArrayTypeData(((AbstractDpc)
			// getDpc().get(
			// "문장결과CL")).getParm().getStringParm(6), 3, as.length);
			s4 = getRemarkTxt(as);
			if (s4 == null)
				s4 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s4;
	}

	protected synchronized String getTextResultValue3(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3, getmid() }))
				return "";
			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);
			s4 = getRemarkTxt3(as, len);
			if (s4 == null)
				s4 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s4;
	}

	protected synchronized String getTextResultValueTrim(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3 , getmid()}))
				return "";
			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);
			s4 = getRemarkTxtTrim(as, len);
			if (s4 == null)
				s4 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s4;
	}

	protected synchronized String getTextResultValueTrim2(String s, String s1, String s2, String s3) {
		String s4 = null;
		try {
			if (!((AbstractDpc) getDpc().get("문장결과CL")).processDpc(new Object[] { s, s1, s2, s3 , getmid()}))
				return "";
			String as[] = getArrayTypeData_CheckLastNoTrim(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(5));
			String len[] = Common.getArrayTypeData(((AbstractDpc) getDpc().get("문장결과CL")).getParm().getStringParm(6), 3, as.length);
			s4 = getRemarkTxtTrim(as, len);
			if (s4 == null)
				s4 = "";
		} catch (Exception _ex) {
			return "";
		}
		return s4;
	}

	protected void initialize() {
		
		getDpc().put("병원리스트", new DpcMCALLSAA2());
		getDpc().put("결과다운해제", new DpcMCR03RM2W());
		getDpc().put("참고치", new DpcMCR03RMS6());
		getDpc().put("참고치2", new DpcMCR03RMS61());
		getDpc().put("리마크", new DpcMWR03RMT75());
//		getDpc().put("리마크", new DpcMCR03RMS75());
		getDpc().put("리마크2", new DpcMWR03RMT76());
//		getDpc().put("리마크2", new DpcMCR03RMS76());
		getDpc().put("결과포멧", new DpcMC999RM());
		getDpc().put("결과집계", new DpcMCR003RMR());
		getDpc().put("결과", new DpcMCR003RMR8());
		getDpc().put("문장결과", new DpcMCR003RMS8());
		getDpc().put("결과다운해제2", new DpcMCR03RM2W());
		getDpc().put("MC118RM", new DpcOfMC118RM());
		getDpc().put("MC118RMS1", new DpcOfMC118RMS1());
		getDpc().put("MCR003T11", new DpcMWR003T11());
//		getDpc().put("MCR003T11", new DpcMCR003T11());
		getDpc().put("결과집계CL", new DpcMWR003RTR());
//		getDpc().put("결과집계CL", new DpcMCR003T12());
		getDpc().put("결과CL", new DpcMWR003RTR8());
//		getDpc().put("결과CL", new DpcMCR003RTR8());
		getDpc().put("문장결과CL", new DpcMWR003RMT8());
//		getDpc().put("문장결과CL", new DpcMCR003RMT8());
		getDpc().put("문장결과재집계CL", new DpcMWR003TM1());
//		getDpc().put("문장결과재집계CL", new DpcMCR003TM1());
		getDpc().put("결과집계HakCL", new DpcMWR003RTR1());
//		getDpc().put("결과집계HakCL", new DpcMCR003T13());
				
		System.out.println("### initialize() >> 집계완료");
		System.out.println("### initialize() >> downloading() 시작");
		System.out.println("### initialize() >> 병원아이디 :: "+id);
		System.out.println("### initialize() >> 시작일 :: "+fdat);
		System.out.println("### initialize() >> 종료일 :: "+tdat);
		
		downloading();
	}

	public final String insertBlanks(String s, int i) {
		String s1 = "";
		for (int j = 0; j < i; j++)
			s1 = s1 + " ";

		return s1 + s;
	}

	protected boolean isAFBsensi(String s) {
		return s.equals("31022");
	}

	protected boolean isChromosome(String s) {
		if (s.equals("71001"))
			return true;
		if (s.equals("71002"))
			return true;
		if (s.equals("71003"))
			return true;
		if (s.equals("71004"))
			return true;
		if (s.equals("71005"))
			return true;
		else
			return s.equals("71006");
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-11-20 오전 10:13:54)
	 * 
	 * @return boolean
	 */
	protected boolean isCultureSensi(String gcd) {
		if (gcd.equals("31010") || gcd.equals("31011") || gcd.equals("31012") || gcd.equals("31014"))
			return true;
		return false;
	}

	protected boolean isEP(String s) {
		if (s.equals("00301"))
			return true;
		if (s.equals("00304"))
			return true;
		if (s.equals("00305"))
			return true;
		if (s.equals("00307"))
			return true;
		if (s.equals("00312"))
			return true;
		if (s.equals("00313"))
			return true;
		if (s.equals("00323"))
			return true;
		if (s.equals("00324"))
			return true;
		if (s.equals("00314"))
			return true;
		if (s.equals("00309"))
			return true;
		if (s.equals("00322"))
			return true;
		if (s.equals("00310"))
			return true;
		if (s.equals("00334"))
			return true;
		else
			return s.equals("00316");
	}

	protected boolean isHBV(String s) {
		if (s.equals("71245"))
			return true;
		return false;
		// return s.equals("00674");
	}

	protected boolean isIFEP(String s) {
		if (s.equals("00317"))
			return true;
		if (s.equals("00318"))
			return true;
		else
			return s.equals("00319");
	}

	protected boolean isLeukemia(String s) {
		return s.equals("11028");
	}

	protected boolean isMAST(String s) {
		if (s.equals("00673")||//  
			s.equals("00674"))
			return true;
		return false;
	}

	protected boolean isMAST_Two(String s) {
		if (s.equals("00683")||//
			s.equals("00684")||
			s.equals("00687")||
			s.equals("00688")||
			s.equals("00689")||
			s.equals("00690")||
			s.equals("00691")
			)
			return true;
		return false;
	}

	protected boolean isParasites(String s) {
		if (s.equals("21280"))
			return true;
		if (s.equals("21281"))
			return true;
		if (s.equals("21282"))
			return true;
		if (s.equals("21283"))
			return true;
		if (s.equals("21284"))
			return true;
		if (s.equals("21285"))
			return true;
		if (s.equals("21286"))
			return true;
		if (s.equals("21287"))
			return true;
		if (s.equals("21288"))
			return true;
		if (s.equals("21289"))
			return true;
		else
			return s.equals("21290");
	}

	protected boolean isRemarkValue(String s) {
		return s.equals("71069");
	}

	public boolean isRewrite() {
		return isRewrite;
	}
	

	
	protected boolean isTriple(String s) {
		if (s.equals("41381"))
			return true;
		else if (s.equals("41382"))
			return true;
		else if (s.equals("41383"))
			return true;
		else if (s.equals("41121"))
			return true;
		else if (s.equals("41359"))
			return true;
		else if (s.equals("41431"))
			return true;
		else if (s.equals("42000"))
			return true;
		else if (s.equals("90027"))
			return true;
		else if (s.equals("99934"))
			return true;
		else if (s.equals("99935"))
			return true;
		else if (s.equals("99936"))
			return true;
		else if (s.equals("90100"))
			return true;
		else if (s.equals("90028"))
			return true;
		else if (s.equals("90029"))
			return true;
		else
			return s.equals("41338");

		/*
		 * boolean is90027 = false; //First double maker boolean is99934 =
		 * false; //Integrated test 1차 boolean is99935 = false; //Sequential
		 * test 1차 boolean is99936 = false; //Sequential test 2차 boolean is90100
		 * = false; //Quad test boolean is90028 = false; //Triple marker boolean
		 * is90029 = false; //Double marker
		 */
	}
	protected boolean isPSTS(String s) {
		if (s.equals("42021"))
			return true;
		else
			return false;
	}
	
	public abstract void makeDownloadFile();

	protected final String makeOutFile() {
		String s = getId().trim() + gettDat().trim();
		int i = 1;
		if (!cpyFilePath.exists()) {
			cpyFilePath.mkdir();
		} else {
			String as[] = cpyFilePath.list();
			for (int j = 0; j < as.length; j++)
				if (!getDateFormat(s, as[j].toString(), i, "xls"))
					i++;

		}
		if (i < 10) {
			m_Filename = s + "0" + i + ".xls";
			return s + "0" + i + ".xls";
		} else {
			m_Filename = s + i + ".xls";
			return s + i + ".xls";
		}
	}

	protected final String mkOutDatFile() {
		String s = getId().trim() + gettDat().trim();
		int i = 1;
		if (!cpyFilePath.exists()) {
			cpyFilePath.mkdir();
		} else {
			String as[] = cpyFilePath.list();
			for (int j = 0; j < as.length; j++)
				if (!getDateFormat(s, as[j].toString(), i, "dat"))
					i++;

		}
		if (i < 10) {
			m_Filename = s + "0" + i + ".dat";
			return s + "0" + i + ".dat";
		} else {
			m_Filename = s + i + ".dat";
			return s + i + ".dat";
		}
	}

	protected final String mkOutTextFile() {
		String s = getId().trim() + gettDat().trim();
		int i = 1;
		if (!cpyFilePath.exists()) {
			cpyFilePath.mkdir();
		} else {
			String as[] = cpyFilePath.list();
			for (int j = 0; j < as.length; j++)
				if (!getDateFormat(s, as[j].toString(), i, "txt"))
					i++;
		}
		if (i < 10) {
			m_Filename = s + "0" + i + ".txt";
			return s + "0" + i + ".txt";
		} else {
			m_Filename = s + i + ".txt";
			return s + i + ".txt";
		}
	}

	protected final String mkOutCSVFile() {
		String s = getId().trim() + gettDat().trim();
		int i = 1;
		if (!cpyFilePath.exists()) {
			cpyFilePath.mkdir();
		} else {
			String as[] = cpyFilePath.list();
			for (int j = 0; j < as.length; j++)
				if (!getDateFormat(s, as[j].toString(), i, "csv"))
					i++;
		}
		if (i < 10) {
			m_Filename = s + "0" + i + ".csv";
			return s + "0" + i + ".csv";
		} else {
			m_Filename = s + i + ".csv";
			return s + i + ".csv";
		}
	}
	
	protected final String mkOutTextFileNew() {
		String s = getId().trim() + gettDat().trim();
		int i = 1;
		if (!cpyFilePath.exists()) {
			cpyFilePath.mkdir();
		} else {
			String as[] = cpyFilePath.list();
			for (int j = 0; j < as.length; j++)
				if (!getDateFormat(s, as[j].toString(), i, "xls"))
					i++;
		}
		if (i < 10) {
			m_Filename = s + "0" + i + ".xls";
			return s + "0" + i + ".xls";
		} else {
			m_Filename = s + i + ".xls";
			return s + i + ".xls";
		}
	}

	protected void parsingDownloadData(DPCParameter dpcparameter) {
		if (!getDownloadData().isEmpty())getDownloadData().clear();
		
		try {
			int i = dpcparameter.getIntParm(33);
			getDownloadData().put("병원코드", Common.getArrayTypeData(dpcparameter.getStringParm(6), 5, i));
			getDownloadData().put("접수일자", Common.getArrayTypeData(dpcparameter.getStringParm(7), 8, i));
			getDownloadData().put("접수번호", Common.getArrayTypeData(dpcparameter.getStringParm(8), 5, i));
			getDownloadData().put("검체번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(9), i));
			getDownloadData().put("차트번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(10), i));
			getDownloadData().put("수신자명", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(11), i));
			getDownloadData().put("나이", Common.getArrayTypeData(dpcparameter.getStringParm(12), 6, i));
			getDownloadData().put("성별", Common.getArrayTypeData(dpcparameter.getStringParm(13), 1, i));
			getDownloadData().put("검사코드", Common.getArrayTypeData(dpcparameter.getStringParm(14), 7, i));
			getDownloadData().put("검사명", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(15), i));
			getDownloadData().put("일련번호", Common.getArrayTypeData(dpcparameter.getStringParm(16), 2, i));
			getDownloadData().put("결과", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(17), i));
			getDownloadData().put("결과상태", Common.getArrayTypeData(dpcparameter.getStringParm(18), 1, i));
			getDownloadData().put("리마크코드", Common.getArrayTypeData(dpcparameter.getStringParm(19), 7, i));
			getDownloadData().put("검사완료일", Common.getArrayTypeData(dpcparameter.getStringParm(20), 8, i));
			getDownloadData().put("처방일자", Common.getArrayTypeData(dpcparameter.getStringParm(21), 8, i));
			getDownloadData().put("이력", Common.getArrayTypeData(dpcparameter.getStringParm(22), 3, i));
			getDownloadData().put("언어", Common.getArrayTypeData(dpcparameter.getStringParm(23), 1, i));
			getDownloadData().put("보험코드", Common.getArrayTypeData(dpcparameter.getStringParm(24), 10, i));
			getDownloadData().put("결과타입", Common.getArrayTypeData(dpcparameter.getStringParm(25), 1, i));
			getDownloadData().put("외래구분", Common.getArrayTypeData(dpcparameter.getStringParm(26), 1, i));
			getDownloadData().put("병원검체코드", Common.getArrayTypeData(dpcparameter.getStringParm(27), 5, i));
			getDownloadData().put("병원검사코드", Common.getArrayTypeData(dpcparameter.getStringParm(28), 20, i));
			getDownloadData().put("요양기관번호", Common.getArrayTypeData(dpcparameter.getStringParm(29), 20, i));
			getDownloadData().put("이미지여부", Common.getArrayTypeData(dpcparameter.getStringParm(30), 8, i)); // 내원번호
			getDownloadData().put("주민번호", Common.getArrayTypeData(dpcparameter.getStringParm(31), 14, i));
			getDownloadData().put("참고치단위등", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(32), i));
			getDownloadData().put("처방번호", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(34), i));
		} catch (Exception _ex) {

//			System.out.println("_ex:"+_ex.getMessage());
		}
	}

	private void prepareRedownload() {
		for (int i = 0; i < dpt_code.length; i++) {
			((AbstractDpc) getDpc().get("결과다운해제")).processDpc(new Object[] { getfDat().trim(), dpt_code[i].toString(), gettDat().trim() });
		}
	}

	public abstract void processingData(int i);

	private boolean removeLine(String s) {
		s = s.toLowerCase();
		StringTokenizer stringtokenizer = new StringTokenizer(s, " ");
		// String s1 = "";
		while (stringtokenizer.hasMoreTokens()) {
			String s2 = stringtokenizer.nextToken().trim();
			if (s2.equals("ld1/ld2"))
				return true;
		}
		return false;
	}

	protected void setfDat(String s) {
		fdat = s;
	}

	protected void settDat(String s) {
		tdat = s;
	}
	protected void setmid(String s) {
		mid = s;
	}


	protected void setHakcd(String s) {
		hakCd = s;
	}
	private void setExcelForm(String s) {
		String as[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
				"Z" };
		divide = "";
		hsExcelField = new Hashtable();
		hsExcelFieldName = new Hashtable();
		String as1[] = getDataCut_(s, "$");
		int i = as1.length;
		// String as2[] = { "", "" };
		for (int j = 0; j < i; j++)
			if (!as1[j].toString().trim().equals("")) {
				String as3[] = null;
				as3 = Common.getDataCut(as1[j].trim(), ":");
				if (as3 != null && as3.length != 1)
					if (as3[0].trim().equals("26")) {
						divide = as3[1].trim().replace('$', ' ').toString().trim();
					} else if (as3[0].trim().equals("20"))
						try {
							result_start_row = Integer.parseInt(as3[1].trim().replace('$', ' ').toString().trim());
						} catch (Exception _ex) {
						}
					else
						hsExcelField.put(as3[1].trim().replace('$', ' ').toString().trim(), as3[0].trim());
			}
		hsExcelFieldName.put("1", "내원번호");
		hsExcelFieldName.put("2", "외래구분");
		hsExcelFieldName.put("3", "의뢰일자");
		hsExcelFieldName.put("4", "검체번호");
		hsExcelFieldName.put("5", "처방번호");
		hsExcelFieldName.put("6", "처방코드");
		hsExcelFieldName.put("7", "처방명");
		hsExcelFieldName.put("8", "검체");

		// !
		hsExcelFieldName.put("9", "일련번호");
		hsExcelFieldName.put("10", "검체코드");
		hsExcelFieldName.put("11", "여유필드");

		// !
		hsExcelFieldName.put("12", "차트번호");
		hsExcelFieldName.put("13", "수진자명");
		hsExcelFieldName.put("14", "주민번호");
		hsExcelFieldName.put("15", "나이");
		hsExcelFieldName.put("16", "성별");
		hsExcelFieldName.put("17", "과명");
		hsExcelFieldName.put("18", "병동");
		hsExcelFieldName.put("19", "참고사항");
		hsExcelFieldName.put("21", "보고일자");
		hsExcelFieldName.put("22", "처방일자");
		hsExcelFieldName.put("25", "단위");
		hsExcelFieldName.put("26", "줄바꿈구분");
		hsExcelFieldName.put("42", "최고치");
		hsExcelFieldName.put("43", "최저치");
		hsExcelFieldName.put("30", "기관구분");
		hsExcelFieldName.put("31", "접수일자");
		hsExcelFieldName.put("32", "접수번호");
		hsExcelFieldName.put("33", "검사코드");
		hsExcelFieldName.put("34", "단문결과");
		hsExcelFieldName.put("35", "장문결과");
		hsExcelFieldName.put("36", "단문+장문");
		hsExcelFieldName.put("37", "판정");
		hsExcelFieldName.put("38", "리마크");
		hsExcelFieldName.put("39", "참고치");
		int k = hsExcelField.size();
		int l = as.length;
		ExcelField = new String[k];
		ExcelFieldName = new String[k];
		int i1 = 0;
		for (int j1 = 0; j1 < l; j1++)
			if (!getExcelField(as[j1]).trim().equals("")) {
				ExcelField[i1] = getExcelField(as[j1]).trim();
				i1++;
			}
		i1 = 0;
		l = ExcelField.length;
		for (int k1 = 0; k1 < l; k1++)
			if (!getExcelFieldName(ExcelField[k1].trim()).trim().equals("")) {
				ExcelFieldName[i1] = getExcelFieldName(ExcelField[k1].trim()).trim();
				i1++;
			}
		gubun1 = getDivide() + "\r\n============================================================\r\n" + getDivide();
		gubun2 = getDivide() + "\r\n------------------------------------------------------------\r\n" + getDivide();
	}

	private boolean setHospitalList() {
		// Object obj = null;
		try {
			// boolean flag = false;
			if (((AbstractDpc) getDpc().get("병원리스트")).processDpc(new Object[] { getId().trim() })) {
				DPCParameter dpcparameter = ((AbstractDpc) getDpc().get("병원리스트")).getParm();
				int i = dpcparameter.getIntParm(3);
				dpt_code = Common.getArrayTypeData(dpcparameter.getStringParm(2).trim(), 5, i); 
				try {
					setExcelForm(getExcelFormat("OCST", "RNQY", dpt_code[0].trim()));
				} catch (Exception _ex) {
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	protected void setId(String s) {
		id = s;
	}

	protected void setIsRewrite(boolean flag) {
		isRewrite = flag;
	}

	protected void setParameters(String as[]) {
		parameters = as;
	}
	protected synchronized  String getTextCytologyType(String s, String s1) {
//		i550System i550Conn = new i550System();
//		CSDPC dpc8307 = null;
		DPCParameter parm8307 = null;
		StringBuffer strbuff =null;
		try {
//			 CSDPC csdpc = i550Conn.getDPC("MCLISOLIB", "MC105RMS1");
//			dpc8307 = i550Conn.getDPC("MCLISOLIB", "MC105RMS1");
			parm8307 = new DPCParameter(8);
			parm8307.setParm(0, "NML", (short) 1, 3D);
			parm8307.setParm(1, new BigDecimal(s), (short) 2, 8D);
			parm8307.setParm(2, new BigDecimal(s1), (short) 2, 5D);
			parm8307.setParm(3, "5270", (short) 1, 4D);
			parm8307.setParm(4, "", (short) 1, 8100D);
			parm8307.setParm(5, "", (short) 1, 300D);
			parm8307.setParm(6, "0", (short) 2, 3D);
			parm8307.setParm(7, "", (short) 1, 1.0D);
			int i = 0;

			Map<String, Object> param = new HashMap<String, Object>();

			double[] ParmLens = parm8307.getParmLens();
			short[] ParmTypes = parm8307.getParmTypes();

			for(int idx=0;idx<ParmLens.length;idx++){
				if(ParmTypes[idx]==1){
					param.put("I_PARM"+idx,parm8307.getStringParm(idx));
				}else{
					param.put("I_PARM"+idx,new BigDecimal(parm8307.getStringParm(idx).toString()));
				}
			}
			
			Object param2 = CommonController.callRecvRstPrc("MCLISOLIB.MC105RMS1", param);
			
	        for( String key : param.keySet() ){

				String paramI = "";
	        	if(key.indexOf("IO_PARM")>-1){
					paramI = key.replace("IO_PARM","").toString();
					parm8307.setParm(Integer.parseInt(paramI), param.get(key).toString());
	        	}
	        	if("".equals(paramI)){
		        	if(key.indexOf("O_PARM")>-1){
						paramI = key.replace("O_PARM","").toString();
						parm8307.setParm(Integer.parseInt(paramI), param.get(key).toString());
						
		        	}
	        	}
	        
	        }
			
//			parm8307 = dpc8307.call(parm8307);
//			i550Conn.disconnected(0);
			// i550Conn.disconnected(0);
			if (parm8307.getStringParm(7).trim().equals("E"))
				return "";
			i = parm8307.getIntParm(6);
			if (i != 0) {
				String as[] = Common.getArrayTypeData_CheckLast(parm8307.getStringParm(4), i);
				String len[] = Common.getArrayTypeData_CheckLast(parm8307.getStringParm(5), i);
				strbuff = new StringBuffer();
				String result = "";
				// !
				for (int j = 0; j < i; j++) {
					if (as[j].toString().trim().startsWith("<")) {
						strbuff.append("\r\n");
						// strbuff.append("<br>");
					}
					if (len[j].toString().equals("99")) {
						result += Common.trimRight(as[j]);
					} else {
						result += as[j];
						strbuff.append("  " + result + "\r\n");
						result = "";
					}
				}
				strbuff.append("  ");
			} else {
				return "";
			}
		} catch (Exception _ex) {
			return _ex.getMessage() + "";
		} finally {
//			i550Conn.disconnected(0);
			// i550SystemSingleton_.getInstance().disconnected(0);
		}
		return strbuff.toString();
	}
	public String getPatientInformationNew(String Date, String No) {
//		i550System i550Conn = new i550System();
		AbstractDpc dpc = null;
		DPCParameter parm8307 = null;
		StringBuffer strbuff =null;
		String returnst="";
		DPCParameter parm;
		try {
			dpc = new DpcOfMCPATBAS2();
			if (dpc.processDpc(new String[] { "NML", Date, No })) {
				parm = dpc.getParm();
				returnst = parm.getStringParm(8).trim(); // 병동명
			}
		}catch (Exception _ex) {
			return "";
		}
		return returnst;
	}
}

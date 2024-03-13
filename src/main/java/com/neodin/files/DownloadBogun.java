package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 6   Fields: 1
import java.io.File;
import java.util.Vector;

import jxl.Workbook;
import jxl.write.Boolean;
import mbi.jmts.dpc.DPCParameter;

import com.neodin.comm.AbstractDpc;
import com.neodin.comm.Common;
import com.neodin.rpg.DpcOfMCPATBAS2;



//!
public class DownloadBogun extends ResultDownload {

	boolean isDebug = false;

	boolean isData = false;
	
	private String parameters[];
	private String hospitalCode[];
	private String dpt_code[];

	protected int row;
	
	private String id;
	private String fdat; 
	private String tdat; 
	private String hakCd=""; 
	protected boolean isRewrite;
	
	
	
	protected String[] getHospitalCode() {
		return hospitalCode;
	}

	private String getId() {
		return id;
	}
	
	public boolean isRewrite() {
		return isRewrite;
	}
	

	public DownloadBogun(String id, String fdat, String tdat, java.lang.Boolean isRewrite) {
		isDebug = false;
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		//initialize();
	}

	public void closeDownloadFile() {
		if (!isDebug && isData) {
			try {
				workbook.write();
			} catch (Exception exception) {
				exception.printStackTrace();
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception exception2) {
					exception2.printStackTrace();
				}
			}
		}
	}
	

	public void makeDownloadFile() {
		row = 1;
		if (!isDebug) 
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("Result", 0);
				String as[] = null;
				
				as = (new String[] { "잠복결핵검사방법", "소속", "신분", "기타내용", "성명",
									"주민번호", "나이", "연락처", "연락처2", "결핵치료력", "LTBI치료력", "TST검사일",
									"TST(mm)", "TST부작용", "IGRA대상", "IGRA검사일", "IGRA검사결과(Nil)", "IGRA검사결과(TBAg)",
									"IGRA검사결과(Mitogen)", "IGRA판독결과(TBAg-Nil)", "TST 2차검사일", "TST 2차(mm)",
									"TST 2차 부작용", "흉부X선 실시여부", "흉부X선 검사일", "흉부X선 결과"});
				for (int i = 0; i < as.length; i++) {
					label = new jxl.write.Label(i, 0, as[i]);
					wbresult.addCell(label);
				}
				
				
			} catch (Exception exception) {
				//System.out.println("OCS 파일쓰기 스레드 오류" + exception.getMessage());
			}
		}
	
	
	
	
public void processingData(int cnt) {
	
		
		makeDownloadFile();//파일 초기 생성
		
		
		SG403RM1List sg403rm1 = parsingDownloadData();
			
		try {
			//in  
			String as[] = (String[]) sg403rm1.getAs();	//회사코드
			String as1[] = (String[]) sg403rm1.getAs1();	//보고일자 from
			String as2[] = (String[]) sg403rm1.getAs2();	//보고일자 to
			String as3[] = (String[]) sg403rm1.getAs3();	//병원코드
			
			//out 
			String as4[] = (String[]) sg403rm1.getAs4();	//검사방법
			String as5[] = (String[]) sg403rm1.getAs5();	//소속
			String as6[] = (String[]) sg403rm1.getAs6();	//신분
			String as7[] = (String[]) sg403rm1.getAs7();	//기타기록
			String as8[] = (String[]) sg403rm1.getAs8();	//성명
			String as9[] = (String[]) sg403rm1.getAs9();	//주민번호
			String as10[] = (String[]) sg403rm1.getAs10();	//나이
			String as11[] = (String[]) sg403rm1.getAs11();	//연락처
			String as12[] = (String[]) sg403rm1.getAs12();	//연락처2
			String as13[] = (String[]) sg403rm1.getAs13();	//결핵치료력
			String as14[] = (String[]) sg403rm1.getAs14();	//LTBU치료력
			String as15[] = (String[]) sg403rm1.getAs15();	//TST검사일
			String as16[] = (String[]) sg403rm1.getAs16();	//TST(mm)
			String as17[] = (String[]) sg403rm1.getAs17();	//TST(부작용)
			String as18[] = (String[]) sg403rm1.getAs18();	//igra대상
			String as19[] = (String[]) sg403rm1.getAs19();	//IGRA검사일
			String as20[] = (String[]) sg403rm1.getAs20();	//검사결과(Nil)
			String as21[] = (String[]) sg403rm1.getAs21();	//TB Ag
			String as22[] = (String[]) sg403rm1.getAs22();	//mitogen
			String as23[] = (String[]) sg403rm1.getAs23();	//tb ag-nil
			String as24[] = (String[]) sg403rm1.getAs24();	//판정
			String as25[] = (String[]) sg403rm1.getAs25();	//TST 2차 검사일
			String as26[] = (String[]) sg403rm1.getAs26();	//TST 2차(mm)
			String as28[] = (String[]) sg403rm1.getAs27();	//TST 2차 부작용
			String as27[] = (String[]) sg403rm1.getAs28();	//흉부 x선 실시여부
			String as29[] = (String[]) sg403rm1.getAs29();	//흉부 x선 검사일
			String as30[] = (String[]) sg403rm1.getAs30();	//흉부 x선 결과
			
			String as31[] = (String[]) sg403rm1.getAs31();	//COUNT
			String as32[] = (String[]) sg403rm1.getAs32();	//ERROR
			
			boolean isNext = false;
			
			String lastData = "";
			int lastindex = 0;
			
			for (int i = 0; i < cnt; i++) {
				
				isData = true;
				String data[] = new String[27];
				
				data[0] = as4[i];
				data[1] = as5[i];
				data[2] = as6[i].trim();
				data[3] = as7[i].trim();
				data[4] = as8[i].trim();
				data[6] = as9[i].trim();
				data[7] = as10[i].trim();
				data[8] = as11[i].trim();
				data[9] = as12[i].trim();
				data[10] = as13[i].trim();
				data[11] = as14[i].trim();
				data[12] = as15[i].trim();
				data[13] = as16[i].trim();
				data[14] = as17[i].trim();
				data[15] = as18[i].trim();
				data[16] = as19[i].trim();
				data[17] = as20[i].trim();
				data[18] = as21[i].trim();
				data[19] = as22[i].trim();
				data[20] = as23[i].trim();
				data[21] = as24[i].trim();
				data[22] = as25[i].trim();
				data[23] = as26[i].trim();
				data[24] = as27[i].trim();
				data[25] = as28[i].trim();
				data[26] = as29[i].trim();
				
				for (int k = 0; k < data.length; k++) {
					label = new jxl.write.Label(k, i, data[k]);
					wbresult.addCell(label);
				}
				
				
			}
//			if (!isDebug) {
//				for (int k = 0; k < as32.length; k++) {
//					label = new jxl.write.Label(k, row, as32[k]);
//					wbresult.addCell(label);
//				}
//			}
			row++;
		
			if (cnt == 400)
				setParameters(new String[] { as[cnt - 1], as1[cnt - 1], as2[cnt - 1], as3[cnt - 1] });
			else
				setParameters(null);
			} catch (Exception exception) {
			setParameters(null);
			exception.printStackTrace();
		}finally{
			closeDownloadFile();
		}
	}
	
	
	
	
	
	
	
	public DownloadBogun() {
		initialize();
	
	}
	
	protected void initialize() {
		getDpc().put("병원리스트", new DpcMCALLSAA2());

		//2019.02.18  
		//서대문보건소 사용상태가 없음. 
		//RPG 기준으로 데이터를 프로시저로 변경하려 할때 크기가 큰 상태로 변경이 힘듬.
		//RPG 를  프로시저로 호출 할수 있게 수정해야 사용가능
		
		//getDpc().put("서대문보건소", new DpcSG403RM1());
		
		
		downloading();
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
	
	private boolean setHospitalList() {
		// Object obj = null;
		try {
			// boolean flag = false;
			if (((AbstractDpc) getDpc().get("병원리스트")).processDpc(new Object[] { getId().trim() })) {
				DPCParameter dpcparameter = ((AbstractDpc) getDpc().get("병원리스트")).getParm();
				int i = dpcparameter.getIntParm(3);
				dpt_code = Common.getArrayTypeData(dpcparameter.getStringParm(2).trim(), 5, i); 
				try {
					//setExcelForm(getExcelFormat("OCST", "RNQY", dpt_code[0].trim()));  //dpt_code ='병원코드'
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
	
	
	


	public String appendBlanks(String s, int i) {
		String s1 = s.trim().substring(0);
		if (s.trim().length() < i) {
			for (int j = 0; j < i - s.length(); j++)
				s1 = s1 + " ";
		} else {
			s1 = s.substring(0, i);
		}
		return s1;
	}

	

	


	public void parsingDownloadData(DPCParameter dpcparameter) {
		
		try {
			int i = dpcparameter.getIntParm(33);
			getDownloadData().put("as", Common.getArrayTypeData(dpcparameter.getStringParm(1), 3, i));
			getDownloadData().put("as1", Common.getArrayTypeData(dpcparameter.getStringParm(7), 8, i));
			getDownloadData().put("as2", Common.getArrayTypeData(dpcparameter.getStringParm(8), 5, i));
			getDownloadData().put("as3", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(9), i));
			getDownloadData().put("as4", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(10), i));
			getDownloadData().put("as5", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(11), i));
			getDownloadData().put("as6", Common.getArrayTypeData(dpcparameter.getStringParm(12), 6, i));
			getDownloadData().put("as7", Common.getArrayTypeData(dpcparameter.getStringParm(13), 1, i));
			getDownloadData().put("as8", Common.getArrayTypeData(dpcparameter.getStringParm(14), 7, i));
			getDownloadData().put("as9", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(15), i));
			getDownloadData().put("as10", Common.getArrayTypeData(dpcparameter.getStringParm(16), 2, i));
			getDownloadData().put("as11", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(17), i));
			getDownloadData().put("as12", Common.getArrayTypeData(dpcparameter.getStringParm(18), 1, i));
			getDownloadData().put("as13", Common.getArrayTypeData(dpcparameter.getStringParm(19), 7, i));
			getDownloadData().put("as14", Common.getArrayTypeData(dpcparameter.getStringParm(20), 8, i));
			getDownloadData().put("as15", Common.getArrayTypeData(dpcparameter.getStringParm(21), 8, i));
			getDownloadData().put("as16", Common.getArrayTypeData(dpcparameter.getStringParm(22), 3, i));
			getDownloadData().put("as17", Common.getArrayTypeData(dpcparameter.getStringParm(23), 1, i));
			getDownloadData().put("as18", Common.getArrayTypeData(dpcparameter.getStringParm(24), 10, i));
			getDownloadData().put("as19", Common.getArrayTypeData(dpcparameter.getStringParm(25), 1, i));
			getDownloadData().put("as20", Common.getArrayTypeData(dpcparameter.getStringParm(26), 1, i));
			getDownloadData().put("as21", Common.getArrayTypeData(dpcparameter.getStringParm(27), 5, i));
			getDownloadData().put("as22", Common.getArrayTypeData(dpcparameter.getStringParm(28), 20, i));
			getDownloadData().put("as23", Common.getArrayTypeData(dpcparameter.getStringParm(29), 20, i));
			getDownloadData().put("as24", Common.getArrayTypeData(dpcparameter.getStringParm(30), 8, i)); // 내원번호
			getDownloadData().put("as25", Common.getArrayTypeData(dpcparameter.getStringParm(31), 14, i));
			getDownloadData().put("as26", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(32), i));
			getDownloadData().put("as27", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(34), i));
			getDownloadData().put("as28", Common.getArrayTypeData(dpcparameter.getStringParm(29), 20, i));
			getDownloadData().put("as29", Common.getArrayTypeData(dpcparameter.getStringParm(30), 8, i)); // 내원번호
			getDownloadData().put("as30", Common.getArrayTypeData(dpcparameter.getStringParm(31), 14, i));
			getDownloadData().put("as31", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(32), i));
			getDownloadData().put("as32", Common.getArrayTypeData_CheckLast(dpcparameter.getStringParm(34), i));
		} catch (Exception _ex) {

			//System.out.println("_ex:"+_ex.getMessage());
		}
	}
	

	
	public synchronized boolean downloading() {
		try {
			if (!setHospitalList())//병원 리스트를 받아와서 결과 폼을 생성
				return false;
			makeDownloadFile();
			
		
			//callResultDownDpc(); //MCR003RTR8 현:DpcMCR003RMR8()			//20150611 모든병원 집계 후 조회 되던 것을 각 병원 집계 후 조회로 변경
		} catch (Exception _ex) {
			return false;
		} finally {
			closeDownloadFile();
		}
		return true;
	}
	
	
	public String getPatientInformationNew(String Date, String No) {
		//i550System i550Conn = new i550System();
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
	

	

	private SG403RM1List parsingDownloadData() {
		// TODO 자동 생성된 메소드 스텁
		return null;
	}
}



	


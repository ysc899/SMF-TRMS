package kr.co.softtrain.common.web.test;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;

import ch.qos.logback.core.net.SyslogOutputStream;
import kr.co.softtrain.custom.util.*;

public class RegOCSRegData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RegOCSRegData rord = new RegOCSRegData();
		rord.readExcelToInsert();
	}
	
	public void readExcelToInsert(){
		ExcelRead  exRead = new ExcelRead();
		
		ExcelReadOption ero = new ExcelReadOption();
		
		ero.setFilePath("D:/test/OCS reg.xlsx");
		ero.setStartRow(0);
		
		ArrayList<String> outputColumns = new ArrayList<String>();
		outputColumns.add("A");
		outputColumns.add("B");
		outputColumns.add("C");
		outputColumns.add("D");
				
		ero.setOutputColumns(outputColumns);
		
		List<Map<String, Object>> exList =  exRead.read(ero);
		
		HashMap<String, String> valMap = new HashMap<String, String>(); 
		
		int row_cnt = exList.size();
		
		//System.out.println("row_cnt::"+row_cnt);
		
		StringBuffer sb = new StringBuffer();
		
		
		
		for(Map<String, Object> colMap : exList) {
			valMap = new HashMap<String, String>(); 
			sb = new StringBuffer();
			
			sb.append("INSERT INTO WEBDBLIB.MWS009M@ ( ");
			
			sb.append(" S009COR");
			sb.append(", S009HOS");
			sb.append(", S009EXT");
			sb.append(", S009COL");
			sb.append(", S009STR");
			sb.append(", S009001");
			sb.append(", S009002");
			sb.append(", S009003");
			sb.append(", S009004");
			sb.append(", S009005");
			sb.append(", S009006");
			sb.append(", S009007");
			sb.append(", S009008");
			sb.append(", S009009");
			sb.append(", S009010");
			sb.append(", S009011");
			sb.append(", S009012");
			sb.append(", S009013");
			sb.append(", S009014");
			sb.append(", S009015");
			sb.append(", S009016");
			sb.append(", S009017");
			sb.append(", S009018");
			sb.append(", S009019");
			sb.append(", S009020");
			sb.append(", S009021");
			sb.append(", S009022");
			sb.append(", S009023");
			sb.append(", S009024");
			sb.append(", S009044");
			sb.append(", S009045");
			sb.append(", S009CUR");
			sb.append(", S009CDT");
			sb.append(", S009CTM");
			sb.append(", S009CIP");
			sb.append(", S009UUR");
			sb.append(", S009UDT");
			sb.append(", S009UTM");
			sb.append(", S009UIP ) VALUES( ");
			
			sb.append(" 'NML' "); //S009COR
			
			String hop = (String) colMap.get("A");
			sb.append(", '"+ hop + "' "); //S009HOS
			sb.append(", 'XLSX' "); //S009EXT
			sb.append(", '' "); //S009COL
			sb.append(", '1' "); //S009STR
			
			//System.out.println(colMap.toString());
			
			String val01 = (String) colMap.get("B");
			//System.out.println("val01::::"+val01);
			if(val01 != null && !"".equals(val01)){
				val01 = val01.replaceAll(" ", "");
			}
			
			String val02 = (String) colMap.get("C");
			//System.out.println("val02::::"+val02);
			if(val02 != null && !"".equals(val02)){
				val02 = val02.replaceAll(" ", "");
				//System.out.println("val0222::::"+val02);
			}
			String val03 = (String) colMap.get("D");
			//System.out.println("val03::::"+val03);
			if(val03 != null && !"".equals(val03)){
				val03 = val03.replaceAll(" ", "");
			}
			
			String[] arr_val01 = null;
			String[] arr_val02 = null;
			String[] arr_val03 = null;
			
			String[] arr_str_val01 = null;
			String[] arr_str_val02 = null;
			String[] arr_str_val03 = null;
			
			if(val01 != null && !"".equals(val01)){
				//System.out.println("val01::"+val01);
				arr_val01 = val01.split("[$]");
				for(String str : arr_val01){
					//System.out.println("str::"+str);
					if(str != null && !"".equals(str)){
						arr_str_val01 = str.split(":");
						valMap.put(  ( Integer.parseInt(arr_str_val01[0]) < 10)? "0"+arr_str_val01[0] : arr_str_val01[0]   , arr_str_val01[1]);
					}
					
				}
			}
			
			if(val02 != null && !"".equals(val02)){
				//System.out.println("val02::"+val02);
				arr_val02 = val02.split("[$]");
				for(String str : arr_val02){
					//System.out.println("str::"+str);
					if(str != null && !"".equals(str)){
						arr_str_val02 = str.split(":");
						valMap.put(( Integer.parseInt(arr_str_val02[0]) < 10)? "0"+arr_str_val02[0] : arr_str_val02[0], arr_str_val02[1]);
					}
					
				}
			}
			
			if(val03 != null && !"".equals(val03)){
				//System.out.println("val03::"+val03);
				arr_val03 = val03.split("[$]");
				for(String str : arr_val03){
					//System.out.println("str::"+str);
					if(str != null && !"".equals(str)){
						arr_str_val03 = str.split(":");
						valMap.put(( Integer.parseInt(arr_str_val03[0]) < 10)? "0"+arr_str_val03[0] : arr_str_val03[0], arr_str_val03[1]);
					}
					
				}
			}
			
			//System.out.println("valMap\n"+valMap.toString());
			
			String idx = "";
			String col_nm = "";
			for(int i = 1; i < 25 ; i++){
				idx = (i < 10)? "0"+i : i+"";
				col_nm = "S0090" + idx;
				if(  valMap.get(idx) == null || "".equals(valMap.get(idx)) ){
					sb.append(", '' ");
				}else{
					sb.append(", '"+valMap.get(idx)+"' ");
				}
				
				//System.out.println(idx);
			}
			
			for(int i = 44; i < 46 ; i++){
				idx = (i < 10)? "0"+i : i+"";
				col_nm = "S0090" + idx;
				if(  valMap.get(idx) == null || "".equals(valMap.get(idx)) ){
					sb.append(", '' ");
				}else{
					sb.append(", '"+valMap.get(idx)+"' ");
				}
				
				//System.out.println(idx);
			}
			// 기본 값
			sb.append(", '' ");
			sb.append(", 0 ");
			sb.append(", 0 ");
			sb.append(", '' ");
			sb.append(", '' ");
			sb.append(", 0 ");
			sb.append(", 0 ");
			sb.append(", '' ); \n\n ");
			
			//System.out.println("====================================");
			
			System.out.println(sb.toString());
			
	    }
		
		
		//System.out.println(exList.toString());
		
		
	}

}

package kr.co.softtrain.custom.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException; 
 
 
public class jxlExcelRead {
	public static List<Map<String, Object>> read(ExcelReadOption excelReadOption) {
	
		/**
		 * 각 row마다의 값을 저장할 맵 객체
		 * 저장되는 형식은 다음과 같다.
		 * put("A", "이름");
		 * put("B", "게임명");
		 */
		Map<String, Object> map = null;
		/*
		 * 각 Row를 리스트에 담는다.
		 * 하나의 Row를 하나의 Map으로 표현되며
		 * List에는 모든 Row가 포함될 것이다.
		 */
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
			String[] colNames = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
		System.out.println(excelReadOption.getFilePath());
		// 파일 객체 생성 - 엑셀파일 경로
		File file = new File(excelReadOption.getFilePath());
		try {
		 
			//엑셀 파일 자체
			//엑셀파일을 읽어 들인다.
			//FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
			WorkbookSettings ws = new WorkbookSettings();
			ws.setEncoding("EUC-KR");
			 
			// 엑셀파일 워크북 객체 생성
			Workbook workbook = Workbook.getWorkbook(file,  ws) ;
			/**
			 * 엑셀 파일에서 첫번째 시트를 가지고 온다.
			 */
			Sheet sheet = workbook.getSheet(0);
			/**
			 * sheet에서 유효한(데이터가 있는) 행의 개수를 가져온다.
			 */
			int endIdx = sheet.getColumn(1).length;
			int numOfCells = 0;
			Cell cell = null;
			String cellName ="";
			Cell[]	row  = null;	
			for(int i=0; i <= endIdx; i++){
				
            	try{
            		row = sheet.getRow(i);
            	}catch (Exception e) {
            		row = null;
				}
				
				if(row != null) {
					 /*
						* 가져온 Row의 Cell의 개수를 구한다.
						*/
					 numOfCells = row.length;
					 /*
						* 데이터를 담을 맵 객체 초기화
						*/
					 map = new HashMap<String, Object>();
					 /*
						* cell의 수 만큼 반복한다.
						*/
					 for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
						 	/*
							* Row에서 CellIndex에 해당하는 Cell을 가져온다.
							*/
							cell = row[cellIndex];
							/*
							* 현재 Cell의 이름을 가져온다
							* 이름의 예 : A,B,C,D,......
							*/
							cellName = colNames[cellIndex];
							/*
							 * map객체의 Cell의 이름을 키(Key)로 데이터를 담는다.
							 */
							map.put(cellName ,	cell.getContents());
					 }
					 /*
						* 만들어진 Map객체를 List로 넣는다.
						*/
					result.add(map);
					 
				}
			}
		} catch (BiffException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean readCheck(ExcelReadOption excelReadOption) {
		boolean rtnBool = true;
 	 
 		// 파일 객체 생성 - 엑셀파일 경로
		File file = new File(excelReadOption.getFilePath());
		//엑셀 파일 자체
		//엑셀파일을 읽어 들인다.
		//FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		try {
			 Workbook wb = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			 rtnBool = false;
//			 e.printStackTrace();
		} catch (IOException e) {
			 rtnBool = false;
//			 e.printStackTrace();
		}
		return rtnBool;
	}
		
}

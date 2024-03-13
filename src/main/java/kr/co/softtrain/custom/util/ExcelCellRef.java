package kr.co.softtrain.custom.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
 
public class ExcelCellRef {
    /**
     * Cell에 해당하는 Column Name을 가젼온다(A,B,C..)
     * 만약 Cell이 Null이라면 int cellIndex의 값으로
     * Column Name을 가져온다.
     * @param cell
     * @param cellIndex
     * @return
     */
    public static String getName(Cell cell, int cellIndex) {
        int cellNum = 0;
        if(cell != null) {
            cellNum = cell.getColumnIndex();
        }
        else {
            cellNum = cellIndex;
        }
        
        return CellReference.convertNumToColString(cellNum);
    }
    
    public static String getValue(Cell cell) {
        String value = "";
        
        if(cell == null) {
            value = "";
        }
        else{
	        switch(cell.getCellType()) {
	            case Cell.CELL_TYPE_FORMULA :
	                value = cell.getCellFormula();
	                break;
	            
	            case Cell.CELL_TYPE_NUMERIC :
	            	
	    			if( DateUtil.isCellDateFormatted(cell)) {
	    				Date date = cell.getDateCellValue();
	    		        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
	    		        SimpleDateFormat formatYearOnly = new SimpleDateFormat("yyyy");
	    		        String dateStamp = formatYearOnly.format(date);
	    		        if (dateStamp.equals("1899")){
			            	value = formatTime.format(date);
	    		        } else {
	    		            //here you may have a date-only or date-time value

	    		            //get time as String HH:mm:ss 
	    		            String timeStamp =formatTime.format(date);
	    		            if (timeStamp.equals("00:00:00")){
	    		                //if time is 00:00:00 you can assume it is a date only value (but it could be midnight)
	    		                //In this case I'm fine with the default Cell.toString method (returning dd-MMM-yyyy in case of a date value)
//	    		                 cell.toString();
	    	    				value = new SimpleDateFormat("yyyyMMdd").format(date);
	    		            } else {
	    	    				value = new SimpleDateFormat("yyyy-MM-dd").format(date)+timeStamp;
	    		                //return date-time value as "dd-MMM-yyyy HH:mm:ss"
//	    		                 cell.toString()+" "+timeStamp;
//	    		    	        	System.out.println("timeStamp        "+timeStamp);
	    		            }
	    		        }
	    		        
	    			}else{
		        		cell.setCellType( HSSFCell.CELL_TYPE_STRING );
		        		value = cell.getStringCellValue();
	    			}
//	                value = (int)cell.getNumericCellValue() + "";
	                break;
	                
	            case Cell.CELL_TYPE_STRING :
	                value = cell.getStringCellValue() + "";
	                break;
	            
	            case Cell.CELL_TYPE_BOOLEAN :
	                value = cell.getBooleanCellValue() + "";
	                break;
	           
	            case Cell.CELL_TYPE_BLANK :
	                value = "";
	                break;
	            
	            case Cell.CELL_TYPE_ERROR :
	                value = cell.getErrorCellValue() + "";
	                break;
	            default:
	                value = cell.getStringCellValue();
	        }
        }
        return value;
    }
 
}

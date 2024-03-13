package kr.co.softtrain.common.web.test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.lang3.RandomStringUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		//System.out.println(cal.get(Calendar.YEAR)+"년");
//	    System.out.println(cal.get(Calendar.MONTH)+1+"월");
//	    System.out.println(cal.get(Calendar.DATE)+"일");
//	    System.out.println(String.format("%s-%d-pdf.jpg", "123123" , 1));
	    
	    Date date= new Date();
	    
	    long time = date.getTime();
//        System.out.println("Time in Milliseconds: " + time);
	    
	    Timestamp ts = new Timestamp(time);
//	    System.out.println("Current Time Stamp: " + ts);
	    
//	    System.out.println( String.format("%s-%d-pdf.jpg", time+"_"+RandomStringUtils.randomAlphanumeric(6) ,1));
	    
	    
	    long m_time = System.currentTimeMillis(); 
	    SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
	    String strDT = dayTime.format(new Date(m_time)); 
//	    System.out.println(strDT); 
	    
	    
	    String temp ="asdf,.\\<><>dsf:ddf:\"s???*d*f";
	    
//	    System.out.println("replaceall:::" +temp.replaceAll( "[\\\\]", "").replaceAll( "/", "").replaceAll(":","").replaceAll("[*]","").replaceAll("[?]","").replaceAll("\"","").replaceAll("<","").replaceAll(">","").replaceAll("|",""));
	    
	    
	    String aaa = "123456789";
	    
//	    getLocalServerIp("0, 5 = "+aaa.substring(0, 5));
//	    System.out.println("5 = "+aaa.substring(5));
	    //TestMain tm = new TestMain();
	   // tm.readJXLExcel();
	    TestMain tm = new TestMain();
	    String ip = tm.getLocalServerIp();
	    System.out.println("ip="+ip);
	}
	
	
	/** 
	 * 현재 서버의 IP 주소를 가져옵니다. 
	 *  
	 * @return IP 주소 
	 */ 
	public String getLocalServerIp() 
	{ 
	        try 
	        { 
	            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) 
	            { 
	                NetworkInterface intf = en.nextElement(); 
	                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) 
	                { 
	                    InetAddress inetAddress = enumIpAddr.nextElement(); 
	                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) 
	                    { 
	                        //return inetAddress.getHostAddress().toString(); 
	                        return inetAddress.toString();                                                 
	                    } 
	                } 
	            } 
	        } 
	        catch (SocketException ex) {} 
	        return null; 
	}
	
	public void readJXLExcel(){
		
		// 파일 객체 생성 - 엑셀파일 경로
        File file = new File("D:/프로젝트/검사결과관리 시스템/산출물/02. 개발/06. 단위테스트/OCS 파일/201903051601_출력(한국병원).xls");
         
        try {
             
            // 엑셀파일 워크북 객체 생성
            Workbook workbook = Workbook.getWorkbook(file) ;
            // 시트 지정
            Sheet sheet = workbook.getSheet(0);
            // 행 길이
            int endIdx = sheet.getColumn(1).length-1;
            
            
            for(int i=0; i <= endIdx; i++){
                // 첫번째 열(A)
                String a_cell = sheet.getCell(0, i).getContents() ;
                // 두번째 열(B)
                String b_cell = sheet.getCell(1, i).getContents() ;
                System.out.println(a_cell + " : " + b_cell) ;
            }
             
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}


}

package kr.co.softtrain.custom.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class Utils {
	Logger logger = LogManager.getLogger();
    
    // java 날짜 형식
    public final static String FMT_YEAR         = "yyyy";
    public final static String FMT_MON          = "MM";
    public final static String FMT_DAY          = "dd";
    public final static String FMT_TIME         = "HH:mm:ss";
    public final static String FMT_YEAR_MON     = FMT_YEAR + "-" + FMT_MON;
    public final static String FMT_DATE         = FMT_YEAR + "-" + FMT_MON + "-" + FMT_DAY;
    public final static String FMT_DATE2        = FMT_YEAR + FMT_MON + FMT_DAY; //yyyyMMdd (공백없이)
    public final static String FMT_DATE3        = FMT_YEAR + "." + FMT_MON + "." + FMT_DAY;
    public final static String FMT_DATE_TIME    = FMT_DATE + " " + FMT_TIME;

    // java 숫자 형식
    public final static String FMT_NUMBER       = "#,###.#####";    // 소수 5자리까지 (그 이상은 반올림)
    public final static String FMT_NUMBER_0     = "#,###";          // 정수형
    public final static String FMT_NUMBER_1     = "#,##0.0";        // 소수 1자리까지
    public final static String FMT_NUMBER_2     = "#,##0.00";       // 소수 2자리까지
    public final static String FMT_NUMBER_3     = "#,##0.000";      // 소수 3자리까지
    public final static String FMT_NUMBER_4     = "#,##0.0000";     // 소수 4자리까지
    public final static String FMT_NUMBER_5     = "#,##0.00000";    // 소수 5자리까지

    // 날짜형 연산시 증감대상 필드
    public final static int FIELD_YEAR          = Calendar.YEAR;
    public final static int FIELD_MON           = Calendar.MONTH;
    public final static int FIELD_DAY           = Calendar.DAY_OF_YEAR;
    
    
/*==========================================================
*  날짜 관련 유틸
===========================================================*/   
    /**
     * 입력  날짜에 전후 날짜 구해오기 
     * @param dateStr
     * @param val
     * @return String
     * @throws Exception
     */
    public static String cal(String dateStr, int val) throws Exception {    
        
        String _date = "";
        String _yearSize = "";
        String _year = dateStr.substring(0,4);
        String _month = dateStr.substring(4,6);
        String _day = dateStr.substring(6,8);
                
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(_year),Integer.parseInt(_month) - 1,Integer.parseInt(_day));
        cal.add(Calendar.MONTH, val);
        _date += cal.get(Calendar.YEAR);
        _yearSize += (cal.get(Calendar.MONTH)+1);
        if( _yearSize.length() == 1 ){
            _date += "0" + _yearSize;
        }else _date += _yearSize;
        
        _date += _day;
        
        return _date;
    }
    
    /**
     * 현재 시각 가져오기(Calendar)
     * @return Calendar
     */
    public static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT+09:00"); // korean time
        calendar.setTimeZone(tz);
        
        return calendar;
    }
    
    /**
     * 현재 시각 가져오기(Date)
     * @return date
     */
    public static Date getTime() {
        return getCalendar().getTime();
    }
    
    /**
     * 현재 시각 가져오기(원하는폼,String)
     * yyyyMMddHHmmss 값을 받아서 받은값의 포맷으로 현재날짜,시간값을 반환한다.<br>
     * getDateStr("yyyyMMdd") ->  20100525
     * @param toFormat
     * @return String
     */
    public static String getDateStr(String toFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat (toFormat);

        return formatter.format(getTime());
    }
    
    /**
     * 날짜형 String을 원하는 포멧으로 변경하기
     * ex) setDateFormat("20090801","####-##-##") ==> 2009-08-01 
     * @param  argument : string, format ; 예 ###-##-######
     * @return String
     */
    public static String setDateFormat(String date, String toFormat) {

        String res = "";
        try {
            
            date = date.replaceAll("[.-]","");
            DateFormat informat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat outformat = new SimpleDateFormat(toFormat);
        
            Date dt = informat.parse(date);         
            res = outformat.format(dt); 
            
        } catch (ParseException e) {
//            System.out.println("formatStr : "+e);
            res = date;
        }
        
        return res;
    }
    
    /**
     * 날짜형 문자열 연산
     * @param dateStr : 입력 날짜 "YYYYMMdd"형태로 입력 하여야한다. 구분자 '-', '.'를 사용 하여도 된다.
     * @param year : 증가시킬 년
     * @param month : 증가시킬 월
     * @param day : 증가시킬 일
     * @return String "yyyyMMdd" 형태로 출력된다.
     * @throws Exception
     */
    public static String addDay (String dateStr, int year, int month, int day) throws Exception {
        
        dateStr = dateStr.replaceAll("[.-]","");
        
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String yyyy = "",MM = "",dd = "";
        try {
            Date date = format.parse(dateStr);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            cal.add(Calendar.YEAR, year);
            cal.add(Calendar.MONTH, month);
            cal.add(Calendar.DATE, day);
            
            yyyy = cal.get(Calendar.YEAR)+"";
            MM = (cal.get(Calendar.MONTH)+1)+"";
            dd = cal.get(Calendar.DATE)+"";
            
            if(MM.length() < 2) MM = "0"+MM;
            if(dd.length() < 2) dd = "0"+dd;        
            
        } catch (ParseException e) {
            e.printStackTrace();
        }       
        return yyyy+MM+dd;
    }
    
    /**
     * 날짜형 String 입력시 각년도와 해당년 주 차수로 리턴하기.(201329)
     * @param : date (yyyyMMdd 형식으로 입력)
     * @return
     */
    public static String weekOf(String date){
        date = date.replaceAll("[.-]","");
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        
        String res = ""; 
        try {
            Date dt = format.parse(date);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            
            String weekOf = cal.get(Calendar.WEEK_OF_YEAR )+"";
            
            if(weekOf.length() < 2) weekOf = "0"+weekOf;
            res = cal.get(Calendar.YEAR )+weekOf;
            
        } catch (ParseException e) {
            res = "0";
        }   
        
        return  res;
    }
    
    /**
     * 시작날짜와 종료 일짜를 입력 하면 각주차 별로 "년도,월,주차,"을 List 형식으로 리턴하기
     * @param : sDate(yyyyMMdd 형식으로 입력)
     * @param : eDate(yyyyMMdd 형식으로 입력)
     * @return : List{YEAR,MONTH,weekOf} 
     */
    public static Map getWeekOf(String sDate , String eDate ){
        
        sDate = sDate.replaceAll("[.-]","");        
        eDate = eDate.replaceAll("[.-]","");
        
        Map reMap = new HashMap();
        List YEARArr = new ArrayList(); 
        List MONTHArr = new ArrayList();
        List weekArr =  new ArrayList();    
        
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date sd = format.parse(sDate);
            Date ed = format.parse(eDate);
            
            Calendar sCal = Calendar.getInstance();
            sCal.setTime(sd);
            
            Calendar eCal = Calendar.getInstance();
            eCal.setTime(ed);
            
            int CHKYear = sCal.get(Calendar.YEAR);
            int CHKMonth = sCal.get(Calendar.MONTH);
            int YearCNT =0 ,MonthCNT = 0;
                        
            do{  
                //각년 처리
                Map YEARArrRow = new HashMap();             
                if(sCal.get(Calendar.YEAR) == CHKYear) YearCNT++;
                else{
                    YearCNT = 1;
                    CHKYear = sCal.get(Calendar.YEAR);
                }
                
                //각월 처리
                Map MONTHArrRow = new HashMap();                
                if(sCal.get(Calendar.MONTH) == CHKMonth) MonthCNT++;
                else{                                   
                    MonthCNT = 1;
                    CHKMonth = sCal.get(Calendar.MONTH);
                }       
                
                //각주차 처리
                Map weekArrRow = new HashMap();
                weekArrRow.put("year", sCal.get(Calendar.YEAR)+"");
                weekArrRow.put("month", (sCal.get(Calendar.MONTH)+1)+""); //1달 가중치 적용
                weekArrRow.put("weekOf", sCal.get(Calendar.WEEK_OF_YEAR )+"");              
                weekArr.add(weekArrRow);        
                
                //1주일 증가 처리
                sCal.add(Calendar.DATE, 7);             
                
                //년 내용입력
                if( sCal.get(Calendar.YEAR) != CHKYear || sCal.after(eCal) || ( sCal.equals(eCal) ) ){
                    YEARArrRow.put("year", CHKYear+"");
                    YEARArrRow.put("yearCnt", YearCNT+"");                  
                    YEARArr.add(YEARArrRow); 
                }
                //월 내용 입력
                if(sCal.get(Calendar.MONTH) != CHKMonth || sCal.after(eCal) || ( sCal.equals(eCal) ) ){
                    MONTHArrRow.put("month", (CHKMonth+1)+"");
                    MONTHArrRow.put("monthCnt", MonthCNT+"");
                    MONTHArr.add(MONTHArrRow);
                }
                            
            }while(sCal.before(eCal));
            
            reMap.put("yearArr", YEARArr);
            reMap.put("monthArr", MONTHArr);
            reMap.put("weekArr", weekArr);      
            
        } catch (ParseException e) {
            e.printStackTrace();
        }   
        
            
        return reMap;
    }
    
    public static Map getWeekOf(String sDate ) throws Exception{    
        sDate = sDate.replaceAll("[.-]","");
        return getWeekOf(sDate, addDay(sDate, 0, 6, 0));
    }
        
    
/*==========================================================
*  null체크 유틸
===========================================================*/   
    /**
     * Object형이 null일경우 String형으로 반환 - 빈값반환
     * 빈값 또는 null일경우 ""을 반환한다.
     * @param str
     * @return String
     */
    public static String isNull( Object str ){
        return isNull(str,"");
    }
    /**
     * Object형이 null일경우 String형으로 반환 - 반환 직접입력
     * 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * @param str
     * @param cvf
     * @return String
     */
    public static String isNull( Object str , String cvf){
        String s = "";
        s = String.valueOf(str).trim();
        
        if(StringUtils.isBlank(s)){
            s = cvf;
        }else if( s == null ){
            s = cvf;
        }else if( s.equals("") ){
	        s = cvf;
	    }else if( s.equals("null")){
            s = cvf;
        }else if( s.equals(null)){
            s = cvf;
        }else{
        	s = String.valueOf(str);
        }
        
        return s;
    }
    
    /**
     * Object형이 null일경우 String형으로 반환 - 반환 직접입력 , 뒤쪽에 내용입력 
     * 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * null이 아닐경우 뒤쪽에 내용을 입력 하여 완성형을 만들수 있다.(예 : 10000 → 10000원)
     * @param str
     * @param cvf
     * @param after
     * @return String
     */
    public static String isNull( Object str , String cvf , String after){
        String s = "";
        s = String.valueOf(str).trim();
        if(StringUtils.isBlank(s)){
            s = cvf;
        }else if( s == null ){
            s = cvf;
        }else if( s.equals("") ){
	        s = cvf;
	    }else if( s.equals("null")){
            s = cvf;
        }else if( s.equals(null)){
            s = cvf;
        }else{
            s = str.toString();     
            s = s+after;
        }
        return s;
    }
        
    /**
     * String형이 null일경우 String형으로 반환 - 빈값반환
     * 빈값 또는 null일경우 ""을 반환한다.
     * @param str
     * @return String
     */
    public static String isNull( String str ){
        return isNull(str,"");
    }
    
    /**
     * String형이 null일경우 String형으로 반환 - 반환 직접 입력 
     * 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * @param str
     * @param cvf
     * @return String
     */
    public static String isNull( String str , String cvf ){
        String s = "";
        s = String.valueOf(str).trim();
    	
        if(StringUtils.isBlank(s)){
            s = cvf;
        }else if( s == null ){
            s = cvf;
        }else if( s.equals("") ){
	        s = cvf;
	    }else if( s.equals("null")){
            s = cvf;
        }else if( s.equals(null)){
            s = cvf;
        }else{
        	s = str;
        }
        
        return s;
    }
            
    /**
     * Object형이 null일경우 int형으로 반환
     * 숫자형이 아니거나 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * @param str
     * @param cvf
     * @return int
     */
    public static int isNull( Object str , int cvf ){
        String s = String.valueOf(str).trim();
        if(StringUtils.isBlank(s)){
        	 return cvf;
        }else if( s == null )
    	{
        	return cvf;
    	}
        try{
            cvf = Integer.parseInt(s);
        }catch(Exception e){
            return cvf;
        }       
        return cvf;
    }
    
    /**
     * String형이 null일경우 int형으로 반환
     * 숫자형이 아니거나 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * @param str
     * @param cvf
     * @return int
     */
    public static int isNull( String str , int cvf ){
        String s = String.valueOf(str).trim();
        if(StringUtils.isBlank(s)){
        	 return cvf;
        }else if( s == null ){ 
        	return cvf;
        }
        try{
            cvf = Integer.parseInt(str);
        }catch(Exception e){
            
        }       
        return cvf;
    }       

    /**
     * Object형이 null일경우 String형으로 반환 - 빈값반환
     * 빈값 또는 null일경우 ""을 반환한다.
     * @param str
     * @return String
     */
    public static String isNullTrim( Object str ){
        return isNullTrim(str,"");
    }
    /**
     * String형이 null일경우 String형으로 반환 - 빈값반환
     * 빈값 또는 null일경우 ""을 반환한다.
     * @param str
     * @return String
     */
    public static String isNullTrim( String str ){
        return isNullTrim(str,"");
    }

    /**
     * Object형이 null일경우 String형으로 반환 - 반환 직접입력
     * 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * @param str
     * @param cvf
     * @return String
     */
    public static String isNullTrim( Object str , String cvf){
        String s = "";
        s = String.valueOf(str).trim();
        s = isNullTrim(s,cvf);
        
        return s;
    }
    /**
     * Object형이 null일경우 String형으로 반환 - 반환 직접입력
     * 빈값 또는 null일경우 입력값 cvf를 반환한다.
     * @param str
     * @param cvf
     * @return String
     */
    public static String isNullTrim( String str , String cvf){
        String s = str;
        
        if(StringUtils.isBlank(s)){
            s = cvf;
        }else if( s == null ){
            s = cvf;
        }else if( s.equals("") ){
	        s = cvf;
	    }else if( s.equals("null")){
            s = cvf;
        }else if( s.equals(null)){
            s = cvf;
        }
        
        return s;
    }
    
    /**
     * String형이 null여부에 따라 boolean 형으로 반환
     * @param str
     * @return boolean
     */
    public static boolean isNullBool( String str ){
        boolean bool = false;
        if( !isNull(str,"").equals("") )
            bool = true;
        
        return bool;
    }
    
    /**
     * String형이 null일경우 int형 0으로 반환
     * 숫자형이 아니거나 빈값 또는 null일경우 0을 반환한다.
     * @param str
     * @return int
     */
    public static int isNullInt( String str ){
        return isNull(str,0);
    }
    
    

    
    /**
     * 숫자형 문자열에서 기호문자 없애기
     * ex) trimNumberStr("1,000,000") => "1000000"
     * ex) trimNumberStr("+1,000,000.12") => "1000000.12"
     * ex) trimNumberStr("-1,000,000.12") => "-1000000.12"
     * @param val
     * @return String
     */
    public static String trimNumberStr(String src) throws Exception {
        if (isNullBool(src) )
            return "";

        char[] chars = new char[src.length()];
        src.getChars(0, src.length(), chars, 0);

        char[] digits = { '-', '.', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

        StringBuffer buf = new StringBuffer();
        boolean ok;
        char c;

        for (int i = 0; i < chars.length; i++) {
            c = chars[i];
            ok = false;

            for (int j = 0; j < digits.length; j++) {
                if (c == digits[j]) {
                    ok = true;
                    break;
                }
            }
            if (ok) buf.append(c);
        }

        return buf.toString();
    }
    
    /**
     * 숫자형 문자열을 원하는 형식으로 변환
     * FMT_NUMBER = "#,###.#####";      // 소수 5자리까지 (그 이상은 반올림)
     * FMT_NUMBER_0 = "#,###";          // 정수형
     * FMT_NUMBER_1 = "#,##0.0";        // 소수 1자리까지
     * FMT_NUMBER_2 = "#,##0.00";       // 소수 2자리까지
     * FMT_NUMBER_3 = "#,##0.000";      // 소수 3자리까지
     * FMT_NUMBER_4 = "#,##0.0000";     // 소수 4자리까지
     * FMT_NUMBER_5 = "#,##0.00000";    // 소수 5자리까지
     * @param numberStr
     * @param toFormat
     * @return String
     * @throws Exception
     */
    public static String formatNumberStr(String numberStr, String toFormat)
    throws Exception {
        if (isNullBool(numberStr))
            return "";
        
        DecimalFormat formatter = new DecimalFormat();
        Number number = formatter.parse(numberStr);
        formatter = new DecimalFormat(toFormat);
        return formatter.format(number);
    }   
    
    /**
     * 문자 숫자형 문자열을 기본 형식으로 변환 
     * @param numberStr
     * @return String
     * @throws Exception
     */
    public static String formatNumberStr(String numberStr) throws Exception {
        numberStr = trimNumberStr(numberStr);

        return formatNumberStr(numberStr, FMT_NUMBER_0);
    }
    
    /**
     * 숫자형 문자열을 기본 형식으로 변환 
     * @param numberStr
     * @return String
     * @throws Exception
     */
    public static String formatNumberStr(int numberInt) throws Exception {
        String numberStr = Integer.toString(numberInt);
        numberStr = trimNumberStr(numberStr);

        return formatNumberStr(numberStr, FMT_NUMBER_0);
    }
    
    
    
    
/*==========================================================
*  형변환 유틸
===========================================================*/   
    /**
     * 롱형을 기본형으로
     * (-2606367688371937948L ==> -2,606,367,688,371,937,948)
     * @param number
     * @return String
     * @throws Exception
     */
    public static String formatNumber(long number) throws Exception {
        DecimalFormat formatter = new DecimalFormat(FMT_NUMBER_0);
        return formatter.format(number);
    }
    
    /**
     * 숫자형을 기본형으로 
     * (123123 => 123,123)
     * @param number
     * @return String
     * @throws Exception
     */
    public static String formatNumber(int number) throws Exception {
        DecimalFormat formatter = new DecimalFormat(FMT_NUMBER_0);
        return formatter.format(number);
    }
    
    /**
     * 문자형을 기본형으로 
     * (123123 => 123,123)
     * @param number
     * @return String
     * @throws Exception
     */
    public static String formatNumber(String number) throws Exception {
        int num = isNull(number,0);
        DecimalFormat formatter = new DecimalFormat(FMT_NUMBER_0);
        return formatter.format(num);
    }
    
    /**
     * Object형을 기본형으로 
     * (123123 => 123,123)
     * @param number
     * @return String
     * @throws Exception
     */
    public static String formatNumber(Object number) throws Exception {
        int num = isNull(number,0);
        DecimalFormat formatter = new DecimalFormat(FMT_NUMBER_0);
        return formatter.format(num);
    }
    
  
    
    /**
     * javascript escape
     * @param str : 문자
     * @return String - 이스케이프문자
     */
    public static String escape(String str){
        int i ;
        char j;
        StringBuffer tmp = new StringBuffer();

        tmp.ensureCapacity(str.length() * 6);
        for( i = 0; i < str.length() ; i++ ){
            j = str.charAt(i);

            if( Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j) )
                tmp.append(j);
            else if( j < 256 ){
                tmp.append("%");
                if( j < 16 )
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            }else{
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }

        return tmp.toString();
    }

    /**
     * javascript unescape
     * @param str : 이스케이프 문자
     * @return String - 정상적인 문자
     */
    public static String unescape(String str){
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(str.length());
        int lastPos = 0;
        int pos = 0;
        char ch;
        while( lastPos < str.length() ){
            pos = str.indexOf("%", lastPos);
            if( pos == lastPos){
                if( str.charAt( pos+1 ) == 'u' ){
                    ch = (char)Integer.parseInt(str.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                }else{
                    ch = (char)Integer.parseInt(str.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            }else{
                if( pos == -1 ){
                    tmp.append(str.substring(lastPos));
                    lastPos = str.length();
                }else{
                    tmp.append(str.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }

        return tmp.toString();
    }
    
    
    /**
     * MD5로 암호화
     * @param str
     * @return
     */
    public static String setMD5(String str){

     String MD5 = ""; 

     try{

         MessageDigest md = MessageDigest.getInstance("MD5"); 
         md.update(str.getBytes()); 
         byte byteData[] = md.digest();
         StringBuffer sb = new StringBuffer(); 
         for(int i = 0 ; i < byteData.length ; i++){
             sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
         }
         MD5 = sb.toString();       

     }catch(NoSuchAlgorithmException e){
         e.printStackTrace(); 
         MD5 = null; 
     }
     return MD5;
   }
    
    
    /**
     * SHA256암호화
     * @param str
     * @return
     */
    public static String setSHA256(String str){

     String SHA = ""; 

     try{

         MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
         sh.update(str.getBytes()); 
         byte byteData[] = sh.digest();
         StringBuffer sb = new StringBuffer(); 
         for(int i = 0 ; i < byteData.length ; i++){
             sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
         }
         SHA = sb.toString();       

     }catch(NoSuchAlgorithmException e){
         e.printStackTrace(); 
         SHA = null; 
     }
     return SHA;
   }    
    
    
    
    /**
     * num자리 임시비밀번호 만들기 
     * @return String
     */
    public static String tempPwd(int num){
        String pwd = "";
        
        String[] abc = {"a","b","c","d","e","f","g","h","i","j"};
        String[] sum = {"1","2","3","4","5","6","7","8","9","0"};
        
        Random r = new Random();
        
        for(int i = 0 ; i < num ; i++ ){
            int ran = r.nextInt(2);
            int mm = r.nextInt(10);
            if( ran == 1 ){
                pwd = pwd + abc[mm];
            }else{
                pwd = pwd + sum[mm];
            }
        }
        
        return pwd;
    }   
    
    
    /**
     * replace로 안되는 구분자를 replace 시키기
     * @param str
     * @param ch
     * @param eh
     * @return String
     */
    public static String replace(String str, String ch, String eh){ 
        if(str == null) return null; 
        if(ch == null) return null; 
        if(eh == null) return null; 

        String sTemp=""; 
        String eTemp=""; 

        int chLen = ch.length(); 
        int ehLen = eh.length(); 

        int m=0; 
        int s=0; 

        while(true){ 
            s = str.indexOf(ch, m);
            if (s == -1)
                break;
            sTemp = str.substring(0, s);
            eTemp = str.substring(s + chLen);
            str = sTemp + eh + eTemp;
            m = s + ehLen;
        } 

        return str; 
    } 
    
    
    /**
     * 전화면호 국번 selete box html을 리턴하기(onchange 함수포함)
     * s = all, hp, tel <br>
     * sel = 값<br>
     * sname = 이름<br>
     * onfunction = javascript<br>
     * 서울 02 경기 031 인천 032 강원 033 충남 041 대전 042 충북 043 부산 051 울산 052 대구 053 경북 054 경남 055 전남 061 광주 062 전북 063 제주 064<br> 
     * return String
     */
    public static String getAreaCode(String s, String sel , String sname, String onfunction){
        StringBuffer sb = new StringBuffer();
        sb.append("<select name = \""+sname+"\" onchange=\""+onfunction+"\">");
        sb.append("<option value = \"\" " + sel.equals("")!=null?"selected":"" + ">선택</option>");
        if( s.equals("all") || s.equals("hp") ){
            sb.append("<option value = \"010\" " + sel.equals("010")!=null?"selected":"" + ">010</option>")
              .append("<option value = \"011\" " + sel.equals("011")!=null?"selected":"" + ">011</option>")
              .append("<option value = \"016\" " + sel.equals("016")!=null?"selected":"" + ">016</option>")
              .append("<option value = \"017\" " + sel.equals("017")!=null?"selected":"" + ">017</option>")
              .append("<option value = \"018\" " + sel.equals("018")!=null?"selected":"" + ">018</option>")
              .append("<option value = \"019\" " + sel.equals("019")!=null?"selected":"" + ">019</option>");
        }
        if( s.equals("all") || s.equals("tel") ){
            sb.append("<option value = \"02\" " + sel.equals("02")!=null?"selected":"" + ">02</option>")
              .append("<option value = \"031\" " + sel.equals("031")!=null?"selected":"" + ">031</option>")
              .append("<option value = \"032\" " + sel.equals("032")!=null?"selected":"" + ">032</option>")
              .append("<option value = \"033\" " + sel.equals("033")!=null?"selected":"" + ">033</option>")
              .append("<option value = \"041\" " + sel.equals("041")!=null?"selected":"" + ">041</option>")
              .append("<option value = \"042\" " + sel.equals("042")!=null?"selected":"" + ">042</option>")
              .append("<option value = \"043\" " + sel.equals("043")!=null?"selected":"" + ">043</option>")
              .append("<option value = \"051\" " + sel.equals("051")!=null?"selected":"" + ">051</option>")
              .append("<option value = \"052\" " + sel.equals("052")!=null?"selected":"" + ">052</option>")
              .append("<option value = \"053\" " + sel.equals("053")!=null?"selected":"" + ">053</option>")
              .append("<option value = \"054\" " + sel.equals("054")!=null?"selected":"" + ">054</option>")
              .append("<option value = \"055\" " + sel.equals("055")!=null?"selected":"" + ">055</option>")
              .append("<option value = \"061\" " + sel.equals("061")!=null?"selected":"" + ">061</option>")
              .append("<option value = \"062\" " + sel.equals("062")!=null?"selected":"" + ">062</option>")
              .append("<option value = \"063\" " + sel.equals("063")!=null?"selected":"" + ">063</option>")
              .append("<option value = \"064\" " + sel.equals("064")!=null?"selected":"" + ">064</option>");
        }
        sb.append("</select>");
        
        return sb.toString();
    }
    
    /**
     * 전화면호 국번 selete box html을 리턴하기
     * s = all, hp, tel<br>
     * sel = 값<br>
     * sname = 이름<br>
     * 서울 02 경기 031 인천 032 강원 033 충남 041 대전 042 충북 043 부산 051 울산 052 대구 053 경북 054 경남 055 전남 061 광주 062 전북 063 제주 064 <br>
     * return String
     */
    public static String getAreaCode(String s, String sel , String sname){      
        if( sel == null )
            sel = "";
        
        StringBuffer sb = new StringBuffer();
        sb.append("<select name = \""+sname+"\">");
        sb.append("<option value = \"\" "); if( sel.equals("") ){ sb.append("selected"); }sb.append(">선택</option>");
        if( s.equals("all") || s.equals("hp") ){
            sb.append("<option value = \"010\" ");  if( sel.equals("010") ){ sb.append("selected"); }sb.append(">010</option>")
              .append("<option value = \"011\" ");  if( sel.equals("011") ){ sb.append("selected"); }sb.append(">011</option>")
              .append("<option value = \"016\" ");  if( sel.equals("016") ){ sb.append("selected"); }sb.append(">016</option>")
              .append("<option value = \"017\" ");  if( sel.equals("017") ){ sb.append("selected"); }sb.append(">017</option>")
              .append("<option value = \"018\" ");  if( sel.equals("018") ){ sb.append("selected"); }sb.append(">018</option>")
              .append("<option value = \"019\" ");  if( sel.equals("019") ){ sb.append("selected"); }sb.append(">019</option>");
        }
        if( s.equals("all") || s.equals("tel") ){
            sb.append("<option value = \"02\" ");   if( sel.equals("02") ){ sb.append("selected"); }sb.append(">02</option>")
              .append("<option value = \"031\" ");  if( sel.equals("031") ){ sb.append("selected"); }sb.append(">031</option>")
              .append("<option value = \"032\" ");  if( sel.equals("032") ){ sb.append("selected"); }sb.append(">032</option>")
              .append("<option value = \"033\" ");  if( sel.equals("033") ){ sb.append("selected"); }sb.append(">033</option>")
              .append("<option value = \"041\" ");  if( sel.equals("041") ){ sb.append("selected"); }sb.append(">041</option>")
              .append("<option value = \"042\" ");  if( sel.equals("042") ){ sb.append("selected"); }sb.append(">042</option>")
              .append("<option value = \"043\" ");  if( sel.equals("043") ){ sb.append("selected"); }sb.append(">043</option>")
              .append("<option value = \"051\" ");  if( sel.equals("051") ){ sb.append("selected"); }sb.append(">051</option>")
              .append("<option value = \"052\" ");  if( sel.equals("052") ){ sb.append("selected"); }sb.append(">052</option>")
              .append("<option value = \"053\" ");  if( sel.equals("053") ){ sb.append("selected"); }sb.append(">053</option>")
              .append("<option value = \"054\" ");  if( sel.equals("054") ){ sb.append("selected"); }sb.append(">054</option>")
              .append("<option value = \"055\" ");  if( sel.equals("055") ){ sb.append("selected"); }sb.append(">055</option>")
              .append("<option value = \"061\" ");  if( sel.equals("061") ){ sb.append("selected"); }sb.append(">061</option>")
              .append("<option value = \"062\" ");  if( sel.equals("062") ){ sb.append("selected"); }sb.append(">062</option>")
              .append("<option value = \"063\" ");  if( sel.equals("063") ){ sb.append("selected"); }sb.append(">063</option>")
              .append("<option value = \"064\" ");  if( sel.equals("064") ){ sb.append("selected"); }sb.append(">064</option>");
        }
        sb.append("</select>");
        
        return sb.toString();
    }
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 파일 이름 변경유틸 
     * @param str
     * @return
     */
    public static String getFileName(String str) {
        int p = str.indexOf("_")+1;
        String name = str.substring(p);

        return name;
    }
    
    /** 
     * 숫자 대소 비교
     * @param num1, num2
     * @return String - num1 < num2 : lt , num1 = num2 : eq , num1 > num2 : gt
     */
    
    public static String numCompare(String num1,String num2) {
        
        String reslut = null;
        int com_num1 = Integer.parseInt(num1);
        
        int com_num2 = Integer.parseInt(num2);
        
        if (com_num1 < com_num2) {
            reslut = "lt";
        } else if (com_num1 > com_num2) {
            reslut = "gt";
        } else {
            reslut = "eq";
        }       
        return reslut;
    }


    /* ---------------------------------------------------------------------------------*/
    
    /**
     * 기준값과 증가값 받아서 str을 subString으로 잘라서 return
     * @param str
     * @param b
     * @param c
     * @return result
     * @throws Exception
     */
    public static String strSubstring(String str, int b, int c) throws Exception {
        String result = "";
        
        int a = 0;
        
        // a : 초기값
        // b : 기준값
        // c : 증가값
        
        a = b * c;
        
        result = str.substring(a - b, a);
        
        return result;
    }
    
    /**
     * str 값이 null일경우 "" return
     * @param str
     * @return tmp
     * @throws Exception
     */
    public static String Null2Blank(String str) {
		String tmp = null;
		
		if(str == null || str.equals("null") || str.equals("undefined")) tmp="";
		else tmp = str;
		
		return tmp;
	}
    
    /**
     * str 값이 null일경우 0 return
     * @param str
     * @return tmp
     * @throws Exception
     */
    
    public static String Null2Zero(String str){
		String tmp = null;
		
		if(str == null || str.equals("")) tmp="0";
		else tmp=str;
		
		return tmp;
	}

	/**
     * parameter 데이터를 한번에 설정
	 * @param request
	 * @return Map<string,string>
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		// 파라미터 이름
		Enumeration<String> paramNames = request.getParameterNames();
		// 저장할 맵
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 맵에 저장
		while(paramNames.hasMoreElements()) {
			String name	= paramNames.nextElement().toString();
			Object value	= request.getParameter(name);
			paramMap.put(name, value.toString().trim());
		}
		// 결과반환
		return paramMap;
	}
	
	public String downCrownixPDFFile(String rdServerSavePath, String downFilePath,String file_name){
		String savePath = null;
		
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;

		int size = 10240;
		int byteWritten = 0;
		
		try {
			URL Url;
			byte[] buf;
			int byteRead;
			//url로 이미지 받기
			Url = new URL(rdServerSavePath);
			
//			System.out.println("rdServerSavePath::"+rdServerSavePath);
			outStream = new BufferedOutputStream(new FileOutputStream( downFilePath + File.separator + file_name));
			
			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			
			//파일 업로드된 경로
			savePath = downFilePath + File.separator + file_name ;
			
//			System.out.println("Download Successfully.");
//			System.out.println("File path : " + downFilePath + "\\" + file_name);
//			System.out.println("File name : " + file_name);
//			System.out.println("of bytes  : " + byteWritten);
//			System.out.println("-------Download End--------");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.flush();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return savePath;
	}
	
	
	public String downCrownixPDFtoJPGFile(String rdServerSavePath, String downFilePath,String file_name){
		//출력이미지 확장자
		Utils utils = new Utils();
		String imageFormat = "jpg";
		String savePath = null;

		String pdfSavePath = utils.downCrownixPDFFile(rdServerSavePath, downFilePath,file_name+".pdf");
		
//		System.out.println("pdfSavePath::"+pdfSavePath);

        File pdfFile = new File(pdfSavePath);

		int pdfPageCn = 0;
		PDDocument pdfDoc = null;
		
		//여러 이미지 list에 담기
		String creatJpgFilePath[] = null;
		try {
		//PDF파일 정보 취득
			pdfDoc = PDDocument.load(pdfFile);
			//PDF파일 총페이지 수 취득
			pdfPageCn = pdfDoc.getNumberOfPages();
//			System.out.println("PDF파일 총페이지 수 : " + pdfPageCn);
			
			PDFRenderer renderer = new PDFRenderer(pdfDoc);
			
			Date date= new Date();
			
			long time = date.getTime();
	        
//			System.out.println("Time in Milliseconds: " + time);
			
			creatJpgFilePath = new String[pdfDoc.getNumberOfPages()];
			
			for(int i = 0 ; i < pdfDoc.getNumberOfPages() ; i++){			
				BufferedImage image = renderer.renderImageWithDPI(i, 300);  // 해상도 조절
				creatJpgFilePath[i] = downFilePath+"/"+String.format("%s-%d-pdf.jpg", time+"_"+RandomStringUtils.randomAlphanumeric(6) , (i+1));
				ImageIO.write(image, "JPEG", new File(creatJpgFilePath[i]));
//				System.out.println(i+"th:::"+creatJpgFilePath[i]);
			}
			
			
		

		} catch (IOException ioe) {
			System.out.println("PDF 정보취득 실패 : " + ioe.getMessage());
		}finally {
			try {
				pdfDoc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		/*********************파일 합치기******************************/
		ArrayList<BufferedImage> arrBuffImgFile = new ArrayList<BufferedImage>();
		
		for(String fn : creatJpgFilePath){
//			System.out.println(fn);
			try {
				//url로 이미지 받기
				BufferedImage image = ImageIO.read(new File(fn));
				
				//여러 이미지 list에 담기
				arrBuffImgFile.add(image);
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		int bi_len = arrBuffImgFile.size();
		int width = 0;
		int height = 0;
		
		try {
			BufferedImage mergedImage = null;
			Graphics2D graphics = null;
			//너비, 높이 구하기
			for(int i=0; i< bi_len; i++){
				if(width < arrBuffImgFile.get(i).getWidth()){
					width = arrBuffImgFile.get(i).getWidth();
				}
				height = height + arrBuffImgFile.get(i).getHeight();
			}
			//너비 높이 설정
			mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			graphics = (Graphics2D) mergedImage.getGraphics();
			graphics.setBackground(Color.WHITE);
			
			int drawHeight = 0;
			
			//이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
			for(int i=0; i< bi_len; i++){
				
				if(i==0){
					graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
				}else{
					drawHeight = drawHeight + arrBuffImgFile.get(i-1).getHeight();
					graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
				}
			
			}
			//이미지 만들기
			ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name+".jpg"));
			savePath = downFilePath + "/" + file_name+".jpg";
			
//			System.out.println("savePath=="+savePath);
			//페이지별 jpg 파일 삭제
			for(String fn : creatJpgFilePath){
//				System.out.println(fn);
				File fn_file = new File(fn);
				if( fn_file.exists() ){
		            if(fn_file.delete()){
//		                System.out.println("파일삭제 성공");
		            }else{
		                System.out.println(fn+"  파일삭제 실패");
		            }
		        }
			}
			//pdf 파일 삭제
			File file = new File(pdfSavePath);
			if( file.exists() ){
	            if(file.delete()){
//	                System.out.println("파일삭제 성공");
	            }else{
	                System.out.println(pdfSavePath+" 파일삭제 실패");
	            }
	        }
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		
		return savePath;
	}

	//여러이미지 파일별로 나누기
	public  String downCrownixJPGFile(String rdSeverSaveDir, String rdFilePathName, String downFilePath, String file_name, String imgEachHos){
		String savePath = null;
		String rdServerSavePath = null;
		
//		System.out.println("rdSeverSaveDir=="+rdSeverSaveDir);
//		System.out.println("rdFilePathName=="+rdFilePathName);
//		System.out.println("downFilePath=="+downFilePath);

		//여러이미지 파일별로 나누기
		String[] arrRdFilePathName = rdFilePathName.split(";");

		/*  20190402 이미지 사이즈 변경 설정 
		 * width : 714 
		 * height : 1010 
		 * */
		//여러 이미지 list에 담기
		/*ArrayList<Image> arrBuffImgFile = new ArrayList<Image>();

        int w = 900;
        int h = 1273;
		for(String fn : arrRdFilePathName){
//			System.out.println(downFilePath + "/" + file_name + "    =       "+fn);
			try {
				rdServerSavePath = rdSeverSaveDir + "/" + fn;
				URL Url;
//				System.out.println("rdServerSavePath= "+rdServerSavePath);
				Url = new URL(rdServerSavePath);
				//url로 이미지 받기
				Image image = ImageIO.read(Url);
				
//				System.out.println("             ");
				
				

	            // 이미지 리사이즈
	            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
	            // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
	            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
	            // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
	            // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
	            Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	 
				//여러 이미지 list에 담기
				arrBuffImgFile.add(resizeImage);
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		int bi_len = arrBuffImgFile.size();

		int width = 900;
		int height = 1273;
		try {
			BufferedImage mergedImage = null;
			Graphics2D graphics = null;
			//너비, 높이 구하기
			height = height*bi_len;
			//너비 높이 설정
			mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			graphics = (Graphics2D) mergedImage.getGraphics();
			graphics.setBackground(Color.WHITE);
			
			int drawHeight = 0;
			
			//이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
			for(int i=0; i< bi_len; i++){
				
				if(i==0){
					graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
				}else{
					drawHeight = drawHeight + h;
//					drawHeight = drawHeight + arrBuffImgFile.get(i-1).getHeight();
					graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
				}
			
			}
			//이미지 만들기
			ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name));
			savePath = downFilePath + "/" + file_name;*/
//			System.out.println("       savePath           =       "+savePath);



        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 이부분에서 zip 파일로 합치기
        //encoding 설정

        // imgEachHos 1 보다 크면 파일 합치기 싫어요.
        boolean mergeFile = ("null".equals(imgEachHos) || "0".equals(imgEachHos) || "undefined".equals(imgEachHos)) ? true : false;

        // 파일 합치기 싫고 파일이 2개이상이고
        if (!mergeFile){
            ArrayList<String> savedJpgFiles =  Utils.saveFilebyRdServerUrl(arrRdFilePathName, rdSeverSaveDir, downFilePath, file_name, false);

            String returnStr = "";
            // 저장한 파일이 2개 이상이면 첫번째 파일명에 .zip을 붙여서 압축한다.
            if (savedJpgFiles.size() > 1){

                //해당 디렉토리의 존재여부를 확인
                String outZipFoler = downFilePath + File.separator + "zip";

                // .jpg .gif .xxx 등 확장자만 .zip 으로 파일명을 바꾼다.
                String repFileName = savedJpgFiles.get(0);
                String regex = ".*\\.(\\w+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(savedJpgFiles.get(0));
                // 파일 확장자만 zip 으로 바꿈.
                if (matcher.matches()) {
                    repFileName = repFileName.replace(matcher.group(1),"zip");
                }
                String outZipfileName =  File.separator + repFileName;

                File fileZipFoder = new File(outZipFoler);
                if(!fileZipFoder.exists()){
                    //없다면 생성
                    fileZipFoder.mkdirs();
                }	
                ZipArchiveOutputStream zos = null;
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                int bufSize = 102400;
                int byteWritten = 0;
                byte[] buf = new byte[bufSize];
                try {
                    zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(outZipFoler + outZipfileName)));
                    zos.setEncoding("UTF-8");
                    for(String jpgFile : savedJpgFiles) {
                        File f =  new File(downFilePath + File.separator+ jpgFile);

                        if (f.isFile()) {
                            //buffer에 해당파일의 stream을 입력한다.
                            fis = new FileInputStream(downFilePath + File.separator+ jpgFile);
                            bis = new BufferedInputStream(fis, bufSize);

                            // zip 파일에 들어갈 파일명 urlDecode UTF-8 처리
                            // jpgFile = URLDecoder.decode(jpgFile, "UTF-8");
                            //zip에 넣을 다음 entry 를 가져온다.
                            zos.putArchiveEntry(new ZipArchiveEntry(FilenameUtils.getName(jpgFile)));

                            //준비된 버퍼에서 집출력스트림으로 write 한다.
                            int len;
                            while ((len = bis.read(buf, 0, bufSize)) != -1) {
                                zos.write(buf, 0, len);
                            }

                            bis.close();
                            fis.close();
                            zos.closeArchiveEntry();
                        }

                    }
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
                returnStr = outZipFoler + outZipfileName;
            }else if (savedJpgFiles.size() == 1){
                returnStr = downFilePath + File.separator + savedJpgFiles.get(0);
            }
            savePath = returnStr;
        }else {
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //  20190402 이미지 사이즈 변경 설정
            //  올린 파일 높이 기본 설정
            ArrayList<BufferedImage> arrBuffImgFile = new ArrayList<BufferedImage>();

            for (String fn : arrRdFilePathName) {
                //			System.out.println(fn);
                try {
                    rdServerSavePath = rdSeverSaveDir + "/" + fn;
                    URL Url;
                    //				System.out.println("rdServerSavePath= "+rdServerSavePath);
                    Url = new URL(rdServerSavePath);
                    //url로 이미지 받기
                    BufferedImage image = ImageIO.read(Url);

                    //여러 이미지 list에 담기
                    arrBuffImgFile.add(image);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            int bi_len = arrBuffImgFile.size();
            int width = 0;
            int height = 0;

            try {
                BufferedImage mergedImage = null;
                Graphics2D graphics = null;
                //너비, 높이 구하기
                for (int i = 0; i < bi_len; i++) {
                    if (width < arrBuffImgFile.get(i).getWidth()) {
                        width = arrBuffImgFile.get(i).getWidth();
                    }
                    height = height + arrBuffImgFile.get(i).getHeight();
                }
                //너비 높이 설정
                mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                graphics = (Graphics2D) mergedImage.getGraphics();
                graphics.setBackground(Color.WHITE);

                int drawHeight = 0;

                //이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
                for (int i = 0; i < bi_len; i++) {

                    if (i == 0) {
                        graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
                    } else {
                        drawHeight = drawHeight + arrBuffImgFile.get(i - 1).getHeight();
                        graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
                    }

                }
                //이미지 만들기
                ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name));
                savePath = downFilePath + "/" + file_name;


            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
		//System.out.println("savePath== "+savePath);
		return savePath;
	}
	///////////////////////////////////////////////////////////////////////////////////
	// 300 DPI + resize
	///////////////////////////////////////////////////////////////////////////////////
    public  String downCrownixJPGFile(String rdSeverSaveDir, String rdFilePathName, String downFilePath,String file_name, String dpi300_hos_gubun, String imgEachHos){
		String savePath = null;
		String rdServerSavePath = null;
		
//		System.out.println("rdSeverSaveDir=="+rdSeverSaveDir);
//		System.out.println("rdFilePathName=="+rdFilePathName);
//		System.out.println("downFilePath=="+downFilePath);

		//여러이미지 파일별로 나누기
		String[] arrRdFilePathName = rdFilePathName.split(";");

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 이부분에서 zip 파일로 합치기
        //encoding 설정

        // imgEachHos 1 보다 크면 파일 합치기 싫어요.
        boolean mergeFile = ("null".equals(imgEachHos) || "0".equals(imgEachHos)) ? true : false;

        // 파일 합치기 싫고 파일이 2개이상이고
        if (!mergeFile){
            ArrayList<String> savedJpgFiles =  Utils.saveFilebyRdServerUrl(arrRdFilePathName, rdSeverSaveDir, downFilePath, file_name, true);

            String returnStr = "";
            // 저장한 파일이 2개 이상이면 첫번째 파일명에 .zip을 붙여서 압축한다.
            if (savedJpgFiles.size() > 1){

                //해당 디렉토리의 존재여부를 확인
                String outZipFoler = downFilePath + File.separator + "zip";

                // .jpg .gif .xxx 등 확장자만 .zip 으로 파일명을 바꾼다.
                String repFileName = savedJpgFiles.get(0);
                String regex = ".*\\.(\\w+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(savedJpgFiles.get(0));
                // 파일 확장자만 zip 으로 바꿈.
                if (matcher.matches()) {
                    repFileName = repFileName.replace(matcher.group(1),"zip");
                }
                String outZipfileName =  File.separator + repFileName;

                File fileZipFoder = new File(outZipFoler);
                if(!fileZipFoder.exists()){
                    //없다면 생성
                    fileZipFoder.mkdirs();
                }
                ZipArchiveOutputStream zos = null;
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                int bufSize = 102400;
                int byteWritten = 0;
                byte[] buf = new byte[bufSize];
                try {
                    zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(outZipFoler + outZipfileName)));
                    zos.setEncoding("UTF-8");
                    for(String jpgFile : savedJpgFiles) {
                        File f =  new File(downFilePath + File.separator+ jpgFile);

                        if (f.isFile()) {
                            //buffer에 해당파일의 stream을 입력한다.
                            fis = new FileInputStream(downFilePath + File.separator+ jpgFile);
                            bis = new BufferedInputStream(fis, bufSize);

                            // zip 파일에 들어갈 파일명 urlDecode UTF-8 처리
                            // jpgFile = URLDecoder.decode(jpgFile, "UTF-8");
                            //zip에 넣을 다음 entry 를 가져온다.
                            zos.putArchiveEntry(new ZipArchiveEntry(FilenameUtils.getName(jpgFile)));

                            //준비된 버퍼에서 집출력스트림으로 write 한다.
                            int len;
                            while ((len = bis.read(buf, 0, bufSize)) != -1) {
                                zos.write(buf, 0, len);
                            }

                            bis.close();
                            fis.close();
                            zos.closeArchiveEntry();
                        }

                    }
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
                returnStr = outZipFoler + outZipfileName;
            }else if (savedJpgFiles.size() == 1){
                returnStr = downFilePath + File.separator + savedJpgFiles.get(0);
            }
            savePath = returnStr;
        }else {
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            /*  20190402 이미지 사이즈 변경 설정
             * width : 714
             * height : 1010
             * */
            //여러 이미지 list에 담기
            ArrayList<Image> arrBuffImgFile = new ArrayList<Image>();

            int w = 714;
            int h = 1010;
            for (String fn : arrRdFilePathName) {
                //			System.out.println(downFilePath + "/" + file_name + "    =       "+fn);
                try {
                    rdServerSavePath = rdSeverSaveDir + "/" + fn;
                    URL Url;
                    //				System.out.println("rdServerSavePath= "+rdServerSavePath);
                    Url = new URL(rdServerSavePath);
                    //url로 이미지 받기
                    Image image = ImageIO.read(Url);

                    //				System.out.println("             ");

                    /** 리사이즈할 때, 원본 가로/세로 높이 기준으로 29%로 줄인다 **/
                    BufferedImage srcImg = ImageIO.read(Url);
                    int z1 = srcImg.getWidth();
                    int z2 = srcImg.getHeight();
                    w = (int) (z1 * 0.29);
                    h = (int) (z2 * 0.29);
                    /** 리사이즈할 때, 원본 가로/세로 높이 기준으로 29%로 줄인다 **/

                    // 이미지 리사이즈
                    // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
                    // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
                    // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
                    // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
                    // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
                    Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

                    //여러 이미지 list에 담기
                    arrBuffImgFile.add(resizeImage);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            int bi_len = arrBuffImgFile.size();

            int width = w;
            int height = h;
            //int width = 714;
            //int height = 1010;
            try {
                BufferedImage mergedImage = null;
                Graphics2D graphics = null;
                //너비, 높이 구하기
                height = height * bi_len;
                //너비 높이 설정
                mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                graphics = (Graphics2D) mergedImage.getGraphics();
                graphics.setBackground(Color.WHITE);

                int drawHeight = 0;

                //이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
                for (int i = 0; i < bi_len; i++) {

                    if (i == 0) {
                        graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
                    } else {
                        drawHeight = drawHeight + h;
                        //					drawHeight = drawHeight + arrBuffImgFile.get(i-1).getHeight();
                        graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
                    }

                }
                //이미지 만들기
                ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name));
                savePath = downFilePath + "/" + file_name;
                //			System.out.println("       savePath           =       "+savePath);

                //  20190402 이미지 사이즈 변경 설정
                //  올린 파일 높이 기본 설정
            /*ArrayList<BufferedImage> arrBuffImgFile = new ArrayList<BufferedImage>();

            for(String fn : arrRdFilePathName){
    //			System.out.println(fn);
                try {
                    rdServerSavePath = rdSeverSaveDir + "/" + fn;
                    URL Url;
    //				System.out.println("rdServerSavePath= "+rdServerSavePath);
                    Url = new URL(rdServerSavePath);
                    //url로 이미지 받기
                    BufferedImage image = ImageIO.read(Url);

                    //여러 이미지 list에 담기
                    arrBuffImgFile.add(image);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            int bi_len = arrBuffImgFile.size();
            int width = 0;
            int height = 0;

            try {
                BufferedImage mergedImage = null;
                Graphics2D graphics = null;
                //너비, 높이 구하기
                for(int i=0; i< bi_len; i++){
                    if(width < arrBuffImgFile.get(i).getWidth()){
                        width = arrBuffImgFile.get(i).getWidth();
                    }
                    height = height + arrBuffImgFile.get(i).getHeight();
                }
                //너비 높이 설정
                mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                graphics = (Graphics2D) mergedImage.getGraphics();
                graphics.setBackground(Color.WHITE);

                int drawHeight = 0;

                //이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
                for(int i=0; i< bi_len; i++){

                    if(i==0){
                        graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
                    }else{
                        drawHeight = drawHeight + arrBuffImgFile.get(i-1).getHeight();
                        graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
                    }

                }
                //이미지 만들기
                ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name));
                savePath = downFilePath + "/" + file_name;*/


            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
		//System.out.println("savePath== "+savePath);
		return savePath;
	}

	public static List<Map<String, Object>>  jsonList(String JSONROW ){
		List<Map<String, Object>>  paramList = new ArrayList();
		Object obj = JSONValue.parse(JSONROW);
		try{
			JSONArray jsonA = new JSONArray();
			jsonA = (JSONArray) obj;
			int data_len = jsonA.size();
			HashMap<String, Object> hm = new HashMap<String, Object>();
			for(int i=0; i<data_len; i++){
				hm = (HashMap<String, Object>) ((HashMap)jsonA.get(i));
				paramList.add(hm);
			}
		}catch (Exception e) {
			paramList.add((HashMap<String, Object>) ((HashMap)obj));
			// TODO: handle exception
		}
		return paramList;
	}
	
	
	public static  Map<String, Object>  getRtnObject(Map<String, Object> param ){
		
		
		// 저장할 맵
		Map<String, Object> paramMap = new HashMap<String, Object>();
		 for (Object key : param.keySet()) {			 
			 if(!"I_LOGLID".equals(key.toString())){
				 paramMap.put(key.toString(), param.get(key));
			 }
		 }
		// 결과반환
		return paramMap;
	}

	
	/**
	 * @Method Name	: decryptRsa
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : String
	 * </pre>
	 * @param privateKey
	 * @param securedValue
	 * @return
	 * @throws Exception 
	 */
	public static String decryptRsa(PrivateKey privateKey, Object Value) throws Exception {
		
		String securedValue = Value.toString();
		String decryptedValue = "";
	
//		System.out.println("securedValue   = ["+securedValue+"]");
		
		if(isNullBool(securedValue)){
			Cipher cipher = Cipher.getInstance("RSA");
			byte[] encryptedBytes = hexToByteArray(securedValue);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			decryptedValue = new String(decryptedBytes, "UTF-8");
		}
		return decryptedValue;
	}
	/**
	 * @Method Name	: hexToByteArray
	 * @see
	 * <pre>
	 * Method 설명 : 
	 * return_type : byte[]
	 * </pre>
	 * @param hex
	 * @return 
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;
	}

	public String ThreadDownCrownixJPGFile(String rdSeverSaveDir, String rdFilePathName, String downFilePath, String file_name, String dpi300_hos_gubun_175, String imgEachHos) throws InterruptedException {
        String savePath = null;
//		logger.error("Main thread starts here..."+rdSeverSaveDir+" "+rdFilePathName+" "+downFilePath+" "+file_name);

//		logger.debug("### cjw ### Utils.java ### dpi300_hos_gubun_175 : "+dpi300_hos_gubun_175);

        ThreadJpg thread = new ThreadJpg(rdSeverSaveDir, rdFilePathName, downFilePath, file_name, dpi300_hos_gubun_175, imgEachHos);

//		logger.error("쓰레드 갯수 : " + Thread.activeCount());
        thread.start();

        try{
            thread.join();
        }catch(Exception e){
            logger.error("*** thread.join() failed");
            e.printStackTrace();
        }
        String ResultThreadString = thread.getSaveFile();
//		logger.error(file_name+" join    --------------"+thread.isAlive());
        //savePath = downFilePath + "/" + file_name;
        return ResultThreadString;
	}
	public String ThreadDownCrownixPDFFile(String rdServerSavePath, String downFilePath,String file_name) throws InterruptedException {
		String savePath = null;
//		logger.error("Main thread starts here..."+rdSeverSaveDir+" "+rdFilePathName+" "+downFilePath+" "+file_name);
		ThreadPdf thread = new ThreadPdf(rdServerSavePath, downFilePath, file_name);
		
		thread.start();
//		logger.error("쓰레드 갯수 : " + Thread.activeCount());
//		logger.error(file_name+" join    --------------"+thread.isAlive());  
		savePath = downFilePath + "/" + file_name;
		return savePath;
	}
    public static ArrayList<String> saveFilebyRdServerUrl(String[] arrRdFilePathName, String rdSeverSaveDir, String saveFilePath, String saveFileName, Boolean tfResize){

        // 원래 저장할 파일명에서 숫자를 붙여가면서 파일을 저장한다. _01 ~ _99
        // 넘버링 숫자 변수 fileNum

        // tfResize 은 dpi300_hos_gubun 을 쓰면 true
        int resizeW = 714;
        int resizeH = 1010;
        ArrayList<String> retArrStr = new ArrayList<String>();
        int fileNumbering = 0;
        for(String fn : arrRdFilePathName) {
            fileNumbering += 1;
            // 파일명에 붙일 2자리 문자
            String strFileNum = String.format("%02d", fileNumbering);
            String splitFileName = "";
            if(arrRdFilePathName.length > 1){
                splitFileName = saveFileName.replace(".jpg", "_"+ strFileNum +".jpg");
            }else{
                splitFileName = saveFileName;
            }

            try {
                //String rdServerSavePath = rdSeverSaveDir  + File.separator +  fn;
                String rdServerSavePath = rdSeverSaveDir  + "/" +  fn;
                //url로 이미지 받기
                URL Url = new URL(rdServerSavePath);
                if (tfResize){
                    //url로 이미지 받기
                    Image image = ImageIO.read(Url);

                    /** 리사이즈할 때, 원본 가로/세로 높이 기준으로 29%로 줄인다 **/
                    BufferedImage srcImg = ImageIO.read(Url);
                    int z1 = srcImg.getWidth();
                    int z2 = srcImg.getHeight();
                    resizeW = (int) (z1 * 0.29);
                    resizeH = (int) (z2 * 0.29);
                    /** 리사이즈할 때, 원본 가로/세로 높이 기준으로 29%로 줄인다 **/

                    // 이미지 리사이즈
                    // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
                    // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
                    // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
                    // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
                    // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
                    Image resizeImage = image.getScaledInstance(resizeW, resizeH, Image.SCALE_SMOOTH);

                    BufferedImage reImage = new BufferedImage(resizeW, resizeH, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = (Graphics2D) reImage.getGraphics();
                    graphics.setBackground(Color.WHITE);

                    int drawHeight = 0;
                    graphics.drawImage(resizeImage, 0, 0, null);
                    //이미지 만들기
                    ImageIO.write(reImage, "jpg",  new File(saveFilePath  + File.separator +  splitFileName));
                }else{
                    BufferedImage image = ImageIO.read(Url);
                    ImageIO.write(image, "jpg",  new File(saveFilePath  + File.separator +  splitFileName));
                }
                retArrStr.add(splitFileName);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                retArrStr = null;
            }
        }
        return retArrStr;
    }


    /**
     * parameter 브라우저 타입과 파일명
     * @param userAgent
     * @param fileNm
     * @return String
     */
    public static String downloadEncFileForBrowser(String userAgent, String fileNm) throws UnsupportedEncodingException {
        String strEncFile = "";
        /*******************브라우저에 따른 한글 깨짐 처리 시작*************************/
        if(userAgent.indexOf("Safari") > -1 || userAgent.indexOf("Firefox") > -1 ){
            strEncFile = "\"" + new String(fileNm.getBytes("UTF-8"), "8859_1")+ "\"";
            strEncFile = URLDecoder.decode(strEncFile);
        } else if (userAgent.indexOf("Opera") > -1) {
            strEncFile = "\"" + new String(fileNm.getBytes("UTF-8"), "8859_1") + "\"";
        /*}else if(userAgent.indexOf("Chrome") > -1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < fileNm.length(); i++) {
                char c = fileNm.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                }else{
                    sb.append(c);
                }
            }
            strEncFile = sb.toString().replaceAll("%20", " ");
        */
        }else{  // MSIE, Trident, Chrome
            strEncFile = URLEncoder.encode(fileNm, "UTF-8").replaceAll("\\+", "%20");
        }

        return strEncFile;
    }
}

class ThreadJpg extends  Thread  {
	
	String rdSeverSaveDir ="";
	String rdFilePathName ="";
	String downFilePath ="";
	String file_name ="";
	String dpi300_hos_gubun ="";
    String imgEachHos = "";

    String resultSaveFile = "";
	
	private static int count = 0;
	private int id;
	@Override
	public void run(){
//		
//		try {
////			TimeUnit.MICROSECONDS.sleep((long)Math.random()*1000);
//			sleep(10);
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
			//**
			String savePath = null;
			String rdServerSavePath = null;
			
//			System.out.println("rdSeverSaveDir=="+rdSeverSaveDir);
//			System.out.println("rdFilePathName=="+rdFilePathName);
//			System.out.println("downFilePath=="+downFilePath);
	
			//여러이미지 파일별로 나누기
			String[] arrRdFilePathName = rdFilePathName.split(";");
	
			if("714".equals(dpi300_hos_gubun)){
				/*  20190402 이미지 사이즈 변경 설정 
				 * width : 714 
				 * height : 1010 
				 * */


                // imgEachHos 1 보다 크면 파일 합치기 싫어요.
                boolean mergeFile = ("null".equals(imgEachHos) || "0".equals(imgEachHos)) ? true : false;

                // 파일 합치기 싫고 파일이 2개이상이고
                if (!mergeFile && arrRdFilePathName.length > 1){
                    // jpg파일 저장만 하고 끝냄. zip다운로드는 recvImgReportZipFile.do 에서 처리됨.
                    ArrayList<String> savedJpgFiles =  Utils.saveFilebyRdServerUrl(arrRdFilePathName, rdSeverSaveDir, downFilePath, file_name, false);
                    return;
                }else {
                    //여러 이미지 list에 담기
                    ArrayList<Image> arrBuffImgFile = new ArrayList<Image>();

                    //int w = 900;
                    //int h = 1273;
                    int w = 714;
                    int h = 1010;
                    for (String fn : arrRdFilePathName) {
                        try {
                            rdServerSavePath = rdSeverSaveDir + "/" + fn;
                            URL Url;
                            Url = new URL(rdServerSavePath);
                            //url로 이미지 받기
                            Image image = ImageIO.read(Url);

                            // 이미지 리사이즈
                            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
                            // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
                            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
                            // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
                            // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
                            Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

                            //여러 이미지 list에 담기
                            arrBuffImgFile.add(resizeImage);

                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }

                    int bi_len = arrBuffImgFile.size();

                    //int width = 900;
                    //int height = 1273;
                    int width = 714;
                    int height = 1010;
                    try {
                        BufferedImage mergedImage = null;
                        Graphics2D graphics = null;
                        //너비, 높이 구하기
                        height = height * bi_len;
                        //너비 높이 설정
                        mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        graphics = (Graphics2D) mergedImage.getGraphics();
                        graphics.setBackground(Color.WHITE);

                        int drawHeight = 0;

                        //이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
                        for (int i = 0; i < bi_len; i++) {

                            if (i == 0) {
                                graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
                            } else {
                                drawHeight = drawHeight + h;
                                graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
                            }

                        }
                        //이미지 만들기
                        ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name));
                        savePath = downFilePath + "/" + file_name;


                        //					Utils.ThreadResult(savePath);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } finally {
                        Thread.currentThread().interrupt();
                        //					System.out.println("END 쓰레드 갯수 : Thread.currentThread().interrupt() " + Thread.activeCount() +"   /// "+Thread.currentThread().isInterrupted());
                    }
                }
			}else{

                // imgEachHos 1 보다 크면 파일 합치기 싫어요.
                boolean mergeFile = ("null".equals(imgEachHos) || "0".equals(imgEachHos)) ? true : false;

                // 파일 합치기 싫고 파일이 2개이상이고
                if (!mergeFile && arrRdFilePathName.length > 1){
                    // jpg파일 저장만 하고 끝냄. zip다운로드는 recvImgReportZipFile.do 에서 처리됨.
                    ArrayList<String> savedJpgFiles =  Utils.saveFilebyRdServerUrl(arrRdFilePathName, rdSeverSaveDir, downFilePath, file_name, false);
                    //return;
                    savePath = String.join("*", savedJpgFiles);
                }else {
                    //  20190402 이미지 사이즈 변경 설정
                    //  올린 파일 높이 기본 설정
                    ArrayList<BufferedImage> arrBuffImgFile = new ArrayList<BufferedImage>();

                    for (String fn : arrRdFilePathName) {
                        //			System.out.println(fn);
                        try {
                            rdServerSavePath = rdSeverSaveDir + "/" + fn;
                            URL Url;
                            //				System.out.println("rdServerSavePath= "+rdServerSavePath);
                            Url = new URL(rdServerSavePath);
                            //url로 이미지 받기
                            BufferedImage image = ImageIO.read(Url);

                            //여러 이미지 list에 담기
                            arrBuffImgFile.add(image);

                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }

                    int bi_len = arrBuffImgFile.size();
                    int width = 0;
                    int height = 0;

                    try {
                        BufferedImage mergedImage = null;
                        Graphics2D graphics = null;
                        //너비, 높이 구하기
                        for (int i = 0; i < bi_len; i++) {
                            if (width < arrBuffImgFile.get(i).getWidth()) {
                                width = arrBuffImgFile.get(i).getWidth();
                            }
                            height = height + arrBuffImgFile.get(i).getHeight();
                        }
                        //너비 높이 설정
                        mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        graphics = (Graphics2D) mergedImage.getGraphics();
                        graphics.setBackground(Color.WHITE);

                        int drawHeight = 0;

                        //이미지 별로 x좌표 , y좌표를 구해서 이어 붙이기
                        for (int i = 0; i < bi_len; i++) {

                            if (i == 0) {
                                graphics.drawImage(arrBuffImgFile.get(i), 0, 0, null);
                            } else {
                                drawHeight = drawHeight + arrBuffImgFile.get(i - 1).getHeight();
                                graphics.drawImage(arrBuffImgFile.get(i), 0, drawHeight, null);
                            }

                        }
                        //이미지 만들기
                        ImageIO.write(mergedImage, "jpg", new File(downFilePath + "/" + file_name));
                        // savePath = downFilePath + "/" + file_name;
                        // savePath 는 기존 로직상에서 의미 없었음. 경로를 제거하고 파일명만 리턴함.
                        savePath = file_name;

//					Utils.ThreadResult(savePath);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } finally {
                        Thread.currentThread().interrupt();
//					System.out.println("END 쓰레드 갯수 : Thread.currentThread().interrupt() " + Thread.activeCount() +"   /// "+Thread.currentThread().isInterrupted());
                    }
                }
			}

        // getSaveFile 에서 쓰임.
        resultSaveFile = savePath;

		//System.out.println("savePath== "+savePath);
//		return savePath;

	}

	public ThreadJpg(String rdSeverSaveDir2, String rdFilePathName2, String downFilePath2, String file_name2, String dpi300_hos_gubun2, String imgEachHos2) {
	
	    this.rdSeverSaveDir = rdSeverSaveDir2;
	    this.rdFilePathName = rdFilePathName2;
	    this.downFilePath = downFilePath2;
	    this.file_name = file_name2;
        this.dpi300_hos_gubun = dpi300_hos_gubun2;
        this.imgEachHos = imgEachHos2;
//		this.id = ++count;
		//new Thread(this).start();		//제거
	}

    public String getSaveFile(){
        return this.resultSaveFile;
    }
}
	
class ThreadPdf extends  Thread  {	
	
	String rdServerSavePath ="";
	String downFilePath ="";
	String file_name ="";
	
	private static int count = 0;
	private int id;
	@Override
	public void run(){
		
//		try {
////			TimeUnit.MICROSECONDS.sleep((long)Math.random()*1000);
//			sleep(10);
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		String savePath = null;
		
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;

		int size = 10240;
		int byteWritten = 0;
		
		try {
			URL Url;
			byte[] buf;
			int byteRead;
			//url로 이미지 받기
			Url = new URL(rdServerSavePath);
			
//			System.out.println("rdServerSavePath::"+rdServerSavePath);
			outStream = new BufferedOutputStream(new FileOutputStream( downFilePath + File.separator + file_name));
			
			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			
			//파일 업로드된 경로
			savePath = downFilePath + File.separator + file_name ;
			
//			System.out.println("Download Successfully.");
//			System.out.println("File path : " + downFilePath + "\\" + file_name);
//			System.out.println("File name : " + file_name);
//			System.out.println("of bytes  : " + byteWritten);
//			System.out.println("-------Download End--------");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.flush();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread.currentThread().interrupt();
//			System.out.println("END 쓰레드 갯수 : " + Thread.activeCount());
		}
	}
	
	public ThreadPdf(String rdServerSavePath2,String downFilePath2,String file_name2) {
		
		this.rdServerSavePath = rdServerSavePath2;
		this.downFilePath = downFilePath2;
		this.file_name = file_name2;
//		this.id = ++count;
		//new Thread(this).start();		//제거
	}
}

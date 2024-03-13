package com.neodin.comm;

import java.text.DateFormat;
/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2009-12-21 오후 2:36:58)
 * @author: Administrator
 */
//public class 날자변환기 {
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <b>기능</b> :
 * <p>
 * 날짜 및 시간을 시스템으로부터 연산하는 클래스입니다.
 * 
 * @author Administrator
 * @since 1.0
 * @see java.util.Date
 */
public class DateUtil {
	public static final int YEAR = 1;

	public static final int MONTH = 2;

	public static final int DATE = 3;

	public static final int MONTHFIRST = 4;

	public static final int MONTHEND = 5;

	/**
	 * <p>
	 * 입력된 일자를 '9999년 99월 99일' 형태로 변환하여 반환한다.
	 * 
	 * @param yyyymmdd
	 * @return String
	 *         <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.changeDateFormat(&quot;20080101&quot;)
	 * </pre>
	 */
	public static String changeDateFormat(String yyyymmdd) {   
		String rtnDate = null;
		String yyyy = yyyymmdd.substring(0, 4);
		String mm = yyyymmdd.substring(4, 6);
		String dd = yyyymmdd.substring(6, 8);
		rtnDate = yyyy + " 년 " + mm + " 월 " + dd + " 일";
		return rtnDate;
	}
	/**
	 * <p>
	 * 입력된 일자를 format 형태로 변환하여 반환한다.
	 * 
	 * @param 입력일자
	 * @param 변환할 format
	 * @return String
	 * <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.changeDateFormat("20170101","yyyy-mm-dd")
	 * </pre>
	 */
	public static String changeDateFormat(String yyyymmdd, String format) {
		Date rtnDate = null;
		SimpleDateFormat formatter = null;
		
		try {
			if (yyyymmdd == null || "".equals(yyyymmdd)) {
				rtnDate = new Date();
				
			} else {
				rtnDate = new SimpleDateFormat("yyyymmdd").parse(yyyymmdd);
			}

			
			if (format == null || "".equals(format)) {
				format = "yyyy-mm-dd";
			}
			formatter = new SimpleDateFormat(format);
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return formatter.format(rtnDate);
	}

	/**
	 * <p>
	 * 해당 p_date날짜에 Calendar 객체를 반환함.
	 * 
	 * @param p_date
	 * @return Calendar
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * Calendar cal = DateUtil.getCalendarInstance(DateUtil.getCurrentYyyymmdd())
	 * </pre>
	 */
	public static Calendar getCalendarInstance(String p_date) {
		// Locale LOCALE_COUNTRY = Locale.KOREA;
		Locale LOCALE_COUNTRY = Locale.FRANCE;
		Calendar retCal = Calendar.getInstance(LOCALE_COUNTRY);
		if (p_date != null && p_date.length() == 8) {
			int year = Integer.parseInt(p_date.substring(0, 4));
			int month = Integer.parseInt(p_date.substring(4, 6)) - 1;
			int date = Integer.parseInt(p_date.substring(6));
			retCal.set(year, month, date);
		}
		return retCal;
	}

	/**
	 * <p>
	 * 현재 날짜와 시각을 yyyyMMddhhmmss 형태로 변환 후 return.
	 * 
	 * @param null
	 * @return yyyyMMddhhmmss
	 * @see java.util.Date
	 * @see java.util.Locale
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getCurrentDateTime()
	 * </pre>
	 */
	public static String getCurrentDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}

	/**
	 * <p>
	 * 현재 시각을 hhmmss 형태로 변환 후 return.
	 * 
	 * @param null
	 * @return hhmmss
	 * @see java.util.Date
	 * @see java.util.Locale
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getCurrentDateTime()
	 * </pre>
	 */
	public static String getCurrentTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "HHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}

	/**
	 * <p>
	 * 현재 날짜를 yyyyMMdd 형태로 변환 후 return.
	 * 
	 * @param null
	 * @return yyyyMMdd *
	 *         <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getCurrentYyyymmdd()
	 * </pre>
	 */
	public static String getCurrentYyyymmdd() {
		return getCurrentDateTime().substring(0, 8);
	}

	/**
	 * <p>
	 * 현재의 요일을 구한다.
	 * 
	 * @param
	 * @return 요일
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
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

	/**
	 * <p>
	 * 두 날짜간의 날짜수를 반환(윤년을 감안함)
	 * 
	 * @param startDate
	 *            시작 날짜
	 * @param endDate
	 *            끝 날짜
	 * @return 날수
	 * @see java.util.GregorianCalendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * long date = DateUtil.getDifferDays(&quot;20080101&quot;,&quot;20080202&quot;)
	 * </pre>
	 */
	public static long getDifferDays(String startDate, String endDate) {
		GregorianCalendar StartDate = getGregorianCalendar(startDate);
		GregorianCalendar EndDate = getGregorianCalendar(endDate);
		long difer = (EndDate.getTime().getTime() - StartDate.getTime()
				.getTime()) / 86400000;
		return difer;
	}

	/**
	 * <p>
	 * GregorianCalendar 객체를 반환함.
	 * 
	 * @param yyyymmdd
	 *            날짜 인수
	 * @return GregorianCalendar
	 * @see java.util.Calendar
	 * @see java.util.GregorianCalendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * Calendar cal = DateUtil.getGregorianCalendar(DateUtil.getCurrentYyyymmdd())
	 * </pre>
	 */
	public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
		int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int dd = Integer.parseInt(yyyymmdd.substring(6));
		GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0,
				0, 0);
		return calendar;
	}

	/**
	 * <p>
	 * 입력된 년월의 마지막 일수를 return 한다.
	 * 
	 * @param year
	 * @param month
	 * @return 마지막 일수
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * int date = DateUtil.getLastDayOfMon(2008 , 1)
	 * </pre>
	 */
	public static int getLastDayOfMon(int year, int month) {

		// VisualAge for java 3.02 에서는 에러나서 코멘트 처리
		// Calendar cal = Calendar.getInstance();
		// cal.set(year, month, 1);
		// return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return 0;
	} // :

	/**
	 * <p>
	 * 입력된 년월의 마지막 일수를 return한다
	 * 
	 * @param year
	 * @param month
	 * @return 마지막 일수
	 *         <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * int date = DateUtil.getLastDayOfMon(&quot;2008&quot;)
	 * </pre>
	 */
	public static int getLastDayOfMon(String yyyymm) {

		// VisualAge for java 3.02 에서는 에러나서 코멘트 처리
		// Calendar cal= Calendar.getInstance();
		// int yyyy= Integer.parseInt(yyyymm.substring(0, 4));
		// int mm= Integer.parseInt(yyyymm.substring(4)) - 1;
		// cal.set(yyyy, mm, 1);
		// return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return 0;
	}

	/**
	 * <p>
	 * 지정된 플래그에 따라 연도 , 월 , 일자를 연산한다.
	 * 
	 * @param field
	 *            연산 필드
	 * @param amount
	 *            더할 수
	 * @param date
	 *            연산 대상 날짜
	 * @return 연산된 날짜
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getOpDate(java.util.Calendar.DATE , 1, &quot;20080101&quot;)
	 * </pre>
	 */
	public static String getOpDate(int field, int amount, String date) {
		GregorianCalendar calDate = getGregorianCalendar(date);
		if (field == Calendar.YEAR) {
			calDate.add(Calendar.YEAR, amount);
		} else if (field == Calendar.MONTH) {
			calDate.add(Calendar.MONTH, amount);
		} else {
			calDate.add(Calendar.DATE, amount);
		}
		return getYyyymmdd(calDate);
	}

	/**
	 * <p>
	 * 현재 일자를 입력된 type의 날짜로 반환합니다.
	 * 
	 * @param type
	 * @return String
	 * @see java.text.DateFormat
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getThisDay(&quot;yyyymmddhhmmss&quot;)
	 * </pre>
	 */
	public static String getThisDay(String type) {
		Date date = new Date();
		SimpleDateFormat sdf = null;
		try {
			if (type.toLowerCase().equals("yyyymmdd")) {
				sdf = new SimpleDateFormat("yyyyMMdd");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhh")) {
				sdf = new SimpleDateFormat("yyyyMMddHH");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmm")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmm");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmmss")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmmssms")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				return sdf.format(date);
			} else {
				sdf = new SimpleDateFormat(type);
				return sdf.format(date);
			}
		} catch (Exception e) {
			return "[ ERROR ]: parameter must be 'YYYYMMDD', 'YYYYMMDDHH', 'YYYYMMDDHHSS'or 'YYYYMMDDHHSSMS '";
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2010-10-19 오후 9:05:15) 시간차
	 */
	public static String[] getTimeDiff(String time1, String time2) {

		// //!
		Calendar ttime = Calendar.getInstance();
		Calendar ftime = Calendar.getInstance();
		String[] str = new String[] { "", "", "", "" };

		// //!

		ftime.set(Integer.parseInt(time1.substring(0, 4)) //
				, Integer.parseInt(time1.substring(4, 6)) //
				, Integer.parseInt(time1.substring(6, 8)) //
				, Integer.parseInt(time1.substring(8, 10)) //
				, Integer.parseInt(time1.substring(10, 12)) //
				, Integer.parseInt(time1.substring(12, 14)));

		// !
		ttime.set(Integer.parseInt(time2.substring(0, 4)) //
				, Integer.parseInt(time2.substring(4, 6)) //
				, Integer.parseInt(time2.substring(6, 8)) //
				, Integer.parseInt(time2.substring(8, 10)) //
				, Integer.parseInt(time2.substring(10, 12)) //
				, Integer.parseInt(time2.substring(12, 14)));

		// //!
		long millisDiff = ttime.getTime().getTime() - ftime.getTime().getTime();
		long remainder = millisDiff / (1000);
		long hour = remainder / (60 * 60);
		// !
		long day = hour / 24;
		if (day > 0) {
			hour = (remainder / (60 * 60)) % 24;
		}
		long min = (remainder % (60 * 60)) / 60;
		long sec = (remainder % (60 * 60)) % 60;

		// !
		str[0] = day + "";
		str[1] = hour + "";
		str[2] = min + "";
		str[3] = sec + "";
		return str;
	}

	/**
	 * <p>
	 * 입력된 일자를 더한 주를 구하여 return한다
	 * 
	 * @param yyyymmdd
	 *            년도별
	 * @param addDay
	 *            추가일
	 * @return 연산된 주
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * int date = DateUtil.getWeek(DateUtil.getCurrentYyyymmdd() , 0)
	 * </pre>
	 */
	public static int getWeek(String yyyymmdd, int addDay) {
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));
		cal.set(new_yy, new_mm - 1, new_dd);
		cal.add(Calendar.DATE, addDay);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * <p>
	 * 현재주가 현재월에 몇째주에 해당되는지 계산한다.
	 * 
	 * @param
	 * @return 요일
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * int day = DateUtil.getWeekOfMonth()
	 * </pre>
	 */
	public static int getWeekOfMonth() {
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
		return week_of_month;
	}

	/**
	 * <p>
	 * 현재주가 올해 전체의 몇째주에 해당되는지 계산한다.
	 * 
	 * @param
	 * @return 요일
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * int day = DateUtil.getWeekOfYear()
	 * </pre>
	 */
	public static int getWeekOfYear() {
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}

	/**
	 * <p>
	 * 주로 일자를 구하는 메소드.
	 * 
	 * @param yyyymm
	 *            년월
	 * @param week
	 *            몇번째 주
	 * @param pattern
	 *            리턴되는 날짜패턴 (ex:yyyyMMdd)
	 * @return 연산된 날짜
	 * @see java.util.Calendar
	 *      <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getWeekToDay(&quot;200801&quot; , 1, &quot;yyyyMMdd&quot;)
	 * </pre>
	 */
	public static String getWeekToDay(String yyyymm, int week, String pattern) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		int new_yy = Integer.parseInt(yyyymm.substring(0, 4));
		int new_mm = Integer.parseInt(yyyymm.substring(4, 6));
		int new_dd = 1;
		cal.set(new_yy, new_mm - 1, new_dd);
		// 임시 코드
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			week = week - 1;
		}
		cal.add(Calendar.DATE, (week - 1) * 7
				+ (cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK)));
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				Locale.FRANCE);
		return formatter.format(cal.getTime());
	}

	public static Date getYesterday(Date today) {
		if (today == null)
			throw new IllegalStateException("today is null");
		Date yesterday = new Date();
		yesterday.setTime(today.getTime() - ((long) 1000 * 60 * 60 * 24));
		return yesterday;
	}

	/**
	 * <p>
	 * 현재 날짜와 시각을 yyyyMMdd 형태로 변환 후 return.
	 * 
	 * @param null
	 * @return yyyyMMdd
	 * 
	 * <pre>
	 * - 사용 예
	 * String date = DateUtil.getYyyymmdd()
	 * </pre>
	 */
	public static String getYyyymmdd(Calendar cal) {
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(cal.getTime());
	}

	/**
	 * <p>
	 * 입력된 날자가 올바른 날자인지 확인합니다.
	 * 
	 * @param yyyy
	 * @param mm
	 * @param dd
	 * @return boolean
	 *         <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * boolean b = DateUtil.isCorrect(2008,1,1)
	 * </pre>
	 */
	public static boolean isCorrect(int yyyy, int mm, int dd) {
		if (yyyy < 0 || mm < 0 || dd < 0)
			return false;
		if (mm > 12 || dd > 31)
			return false;
		String year = "" + yyyy;
		String month = "00" + mm;
		String year_str = year + month.substring(month.length() - 2);
		int endday = DateUtil.getLastDayOfMon(year_str);
		if (dd > endday)
			return false;
		return true;
	} // :

	/**
	 * <p>
	 * 입력된 날자가 올바른지 확인합니다.
	 * 
	 * @param yyyymmdd
	 * @return boolean
	 *         <p>
	 * 
	 * <pre>
	 * - 사용 예
	 * boolean b = DateUtil.isCorrect(&quot;20080101&quot;)
	 * </pre>
	 */
	public static boolean isCorrect(String yyyymmdd) {
		boolean flag = false;
		if (yyyymmdd.length() < 8)
			return false;
		try {
			int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
			int dd = Integer.parseInt(yyyymmdd.substring(6));
			flag = DateUtil.isCorrect(yyyy, mm, dd);
		} catch (Exception ex) {
			return false;
		}
		return flag;
	} // :

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2011-01-13 오후 11:07:03)
	 */
	public void newMethod() {
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2010-10-19 오후 9:29:51)
	 */
	public void readme2() {
		/*
		 * 날짜 연산법 ]
		 * 
		 * 
		 * 가. 이해 및 유틸 - 시스템 시간에 대한 이해 - 날짜 계산 종합 유틸리티
		 * 
		 * 나. 응용팁
		 * 
		 * 시스템의 밀리초 구하기.(국제표준시각(UTC, GMT) 1970/1/1/0/0/0 으로부터 경과한 시각)
		 * ------------------------------------------------------------------ //
		 * 밀리초 단위(*1000은 1초), 음수이면 이전 시각 long time = System.currentTimeMillis ( );
		 * System.out.println ( time.toString ( ) );
		 * ------------------------------------------------------------------
		 * 
		 * 현재 시각을 가져오기.
		 * ------------------------------------------------------------------
		 * Date today = new Date (); System.out.println ( today );
		 * 
		 * 결과 : Sat Jul 12 16:03:00 GMT+01:00 2000
		 * ------------------------------------------------------------------
		 * 
		 * 경과시간(초) 구하기
		 * ------------------------------------------------------------------
		 * long time1 = System.currentTimeMillis (); long time2 =
		 * System.currentTimeMillis (); system.out.println ( ( time2 - time1 ) /
		 * 1000.0 );
		 * ------------------------------------------------------------------
		 * 
		 * Date를 Calendar로 맵핑시키기
		 * ------------------------------------------------------------------
		 * Date d = new Date ( ); Calendar c = Calendar.getInstance ( );
		 * c.setTime ( d );
		 * ------------------------------------------------------------------
		 * 
		 * 날짜(년/월/일/시/분/초) 구하기
		 * ------------------------------------------------------------------
		 * import java.util.*; import java.text.*;
		 * 
		 * SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd
		 * HH:mm:ss", Locale.KOREA ); Date currentTime = new Date ( ); String
		 * dTime = formatter.format ( currentTime ); System.out.println ( dTime );
		 * ------------------------------------------------------------------
		 * 
		 * 날짜(년/월/일/시/분/초) 구하기2
		 * ------------------------------------------------------------------
		 * GregorianCalendar today = new GregorianCalendar ( );
		 * 
		 * int year = today.get ( today.YEAR ); int month = today.get (
		 * today.MONTH ) + 1; int yoil = today.get ( today.DAY_OF_MONTH );
		 * 
		 * GregorianCalendar gc = new GregorianCalendar ( );
		 * 
		 * System.out.println ( gc.get ( Calendar.YEAR ) ); System.out.println (
		 * String.valueOf ( gc.get ( Calendar.MONTH ) + 1 ) );
		 * System.out.println ( gc.get ( Calendar.DATE ) ); System.out.println (
		 * gc.get ( DAY_OF_MONTH ) );
		 * ------------------------------------------------------------------
		 * 
		 * 날짜(년/월/일/시/분/초) 구하기3
		 * ------------------------------------------------------------------
		 * DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
		 * Locale.KOREA); Calendar cal = Calendar.getInstance(Locale.KOREA); nal =
		 * df.format(cal.getTime());
		 * ------------------------------------------------------------------ -
		 * 표준시간대를 지정하고 날짜를 가져오기.
		 * ------------------------------------------------------------------
		 * TimeZone jst = TimeZone.getTimeZone ("JST"); Calendar cal =
		 * Calendar.getInstance ( jst ); // 주어진 시간대에 맞게 현재 시각으로 초기화된
		 * GregorianCalender 객체를 반환.// 또는 Calendar now =
		 * Calendar.getInstance(Locale.KOREA); System.out.println ( cal.get (
		 * Calendar.YEAR ) + "년 " + ( cal.get ( Calendar.MONTH ) + 1 ) + "월 " +
		 * cal.get ( Calendar.DATE ) + "일 " + cal.get ( Calendar.HOUR_OF_DAY ) +
		 * "시 " +cal.get ( Calendar.MINUTE ) + "분 " + cal.get ( Calendar.SECOND ) +
		 * "초 " );
		 * 
		 * 결과 : 2000년 8월 5일 16시 16분 47초
		 * ------------------------------------------------------------------
		 * 
		 * 영어로된 날짜를 숫자로 바꾸기
		 * ------------------------------------------------------------------
		 * Date myDate = new Date ( "Sun,5 Dec 1999 00:07:21" );
		 * System.out.println ( myDate.getYear ( ) + "-" + myDate.getMonth ( ) +
		 * "-" + myDate.getDay ( ) );
		 * ------------------------------------------------------------------
		 * 
		 * "Sun, 5 Dec 1999 00:07:21"를 "1999-12-05"로 바꾸기
		 * ------------------------------------------------------------------
		 * SimpleDateFormat formatter_one = new SimpleDateFormat ( "EEE, dd MMM
		 * yyyy hh:mm:ss",Locale.ENGLISH ); SimpleDateFormat formatter_two = new
		 * SimpleDateFormat ( "yyyy-MM-dd" );
		 * 
		 * String inString = "Sun, 5 Dec 1999 00:07:21";
		 * 
		 * ParsePosition pos = new ParsePosition ( 0 ); Date frmTime =
		 * formatter_one.parse ( inString, pos ); String outString =
		 * formatter_two.format ( frmTime );
		 * 
		 * System.out.println ( outString );
		 * ------------------------------------------------------------------
		 * 
		 * 숫자 12자리를, 다시 날짜로 변환하기
		 * ------------------------------------------------------------------
		 * Date conFromDate = new Date(); long ttl = conFromDate.parse ( "Dec
		 * 25, 1997 10:10:10" ); System.out.println ( ttl ); //예 938291839221
		 * 
		 * Date today = new Date ( ttl ); DateFormat format =
		 * DateFormat.getDateInstance ( DateFormat.FULL,Locale.US ); String
		 * formatted = format.format ( today ); System.out.println ( formatted );
		 * ------------------------------------------------------------------
		 * 
		 * 특정일로부터 n일 만큼 이동한 날짜 구하기
		 * ------------------------------------------------------------------
		 * 특정일의 시간을 long형으로 읽어온다음.. 날짜*24*60*60*1000 을 계산하여. long형에 더해줍니다. 그리고
		 * 나서 Date클래스와 Calender클래스를 이용해서 날짜와 시간을 구하면 됩니다
		 * ------------------------------------------------------------------
		 * 
		 * 특정일에서 일정 기간후의 날짜 구하기2
		 * ------------------------------------------------------------------
		 * //iDay 에 입력하신 만큼 빼거나 더한 날짜를 반환 합니다. import java.util.*;
		 * 
		 * public String getDate ( int iDay ) { Calendar
		 * temp=Calendar.getInstance ( ); StringBuffer sbDate=new StringBuffer ( );
		 * 
		 * temp.add ( Calendar.DAY_OF_MONTH, iDay );
		 * 
		 * int nYear = temp.get ( Calendar.YEAR ); int nMonth = temp.get (
		 * Calendar.MONTH ) + 1; int nDay = temp.get ( Calendar.DAY_OF_MONTH );
		 * 
		 * sbDate.append ( nYear ); if ( nMonth < 10 ) sbDate.append ( "0" );
		 * sbDate.append ( nMonth ); if ( nDay < 10 ) sbDate.append ( "0" );
		 * sbDate.append ( nDay );
		 * 
		 * return sbDate.toString ( ); }
		 * ------------------------------------------------------------------
		 * 
		 * 현재날짜에서 2달전의 날짜를 구하기
		 * ------------------------------------------------------------------
		 * Calendar cal = Calendar.getInstance ( );//오늘 날짜를 기준으루.. cal.add (
		 * cal.MONTH, -2 ); //2개월 전.... System.out.println ( cal.get ( cal.YEAR ) );
		 * System.out.println ( cal.get ( cal.MONTH ) + 1 ); System.out.println (
		 * cal.get ( cal.DATE ) );
		 * ------------------------------------------------------------------
		 * 
		 * 달에 마지막 날짜 구하기
		 * ------------------------------------------------------------------
		 * for ( int month = 1; month <= 12; month++ ) { GregorianCalendar cld =
		 * new GregorianCalendar ( 2001, month - 1, 1 ); System.out.println (
		 * month + "/" + cld.getActualMaximum ( Calendar.DAY_OF_MONTH ) ); }
		 * ------------------------------------------------------------------
		 * 
		 * 해당하는 달의 마지막 일 구하기
		 * ------------------------------------------------------------------
		 * GregorianCalendar today = new GregorianCalendar ( ); int maxday =
		 * today.getActualMaximum ( ( today.DAY_OF_MONTH ) ); System.out.println (
		 * maxday );
		 * ------------------------------------------------------------------
		 * 
		 * 특정일을 입력받아 해당 월의 마지막 날짜를 구하는 간단한 예제.(달은 -1 해준다.)...윤달 30일 31일 알아오기.
		 * ------------------------------------------------------------------
		 * Calendar cal = Calendar.getInstance ( ); cal.set ( Integer.parseInt (
		 * args[0] ), Integer.parseInt ( args [1] ) - 1, Integer.parseInt ( args
		 * [2] ) ); SimpleDateFormat dFormat = new SimpleDateFormat (
		 * "yyyy-MM-dd" ); System.out.println ( "입력 날짜 " + dFormat.format (
		 * cal.getTime ( ) ) ); System.out.println ( "해당 월의 마지막 일자 : " +
		 * cal.getActualMaximum ( Calendar.DATE ) );
		 * ------------------------------------------------------------------
		 * 
		 * 해당월의 실제 날짜수 구하기 ( 1999년 1월달의 실제 날짜수를 구하기 )
		 * ------------------------------------------------------------------
		 * Calendar calendar = Calendar.getInstance ( ); calendar.set ( 1999, 0,
		 * 1 ); int maxDays = calendar.getActualMaximum ( Calendar.DAY_OF_MONTH );
		 * ------------------------------------------------------------------
		 * 
		 * 어제 날짜 구하기
		 * ------------------------------------------------------------------
		 * 오늘날짜를 초단위로 구해서 하루분을 빼주고 다시 셋팅해주면 쉽게 구할수 있죠.. setTime((기준일부터 오늘까지의 초를
		 * 구함) - 24*60*60)해주면 되겠죠..
		 * ------------------------------------------------------------------
		 * 
		 * 어제 날짜 구하기2
		 * ------------------------------------------------------------------
		 * import java.util.*;
		 * 
		 * public static Date getYesterday ( Date today ) { if ( today == null )
		 * throw new IllegalStateException ( "today is null" ); Date yesterday =
		 * new Date ( ); yesterday.setTime ( today.getTime ( ) - ( (long) 1000 *
		 * 60 * 60 * 24 ) );
		 * 
		 * return yesterday; }
		 * ------------------------------------------------------------------
		 * 
		 * 내일 날짜 구하기
		 * ------------------------------------------------------------------
		 * Date today = new Date ( ); Date tomorrow = new Date ( today.getTime ( ) +
		 * (long) ( 1000 * 60 * 60 * 24 ) );
		 * ------------------------------------------------------------------
		 * 
		 * 내일 날짜 구하기2
		 * ------------------------------------------------------------------
		 * Calendar today = Calendar.getInstance ( ); today.add ( Calendar.DATE,
		 * 1 ); Date tomorrow = today.getTime ( );
		 * ------------------------------------------------------------------
		 * 
		 * 오늘날짜에서 5일 이후 날짜를 구하기
		 * ------------------------------------------------------------------
		 * Calendar cCal = Calendar.getInstance(); c.add(Calendar.DATE, 5);
		 * ------------------------------------------------------------------
		 * 
		 * 날짜에 해당하는 요일 구하기
		 * ------------------------------------------------------------------
		 * //DAY_OF_WEEK리턴값이 일요일(1), 월요일(2), 화요일(3) ~~ 토요일(7)을 반환합니다. //아래 소스는
		 * JSP일부입니다. import java.util.*;
		 * 
		 * Calendar cal= Calendar.getInstance ( ); int day_of_week = cal.get (
		 * Calendar.DAY_OF_WEEK ); if ( day_of_week == 1 ) m_week="일요일"; else if (
		 * day_of_week == 2 ) m_week="월요일"; else if ( day_of_week == 3 )
		 * m_week="화요일"; else if ( day_of_week == 4 ) m_week="수요일"; else if (
		 * day_of_week == 5 ) m_week="목요일"; else if ( day_of_week == 6 )
		 * m_week="금요일"; else if ( day_of_week == 7 ) m_week="토요일";
		 * 
		 * 오늘은 : 입니다.
		 * ------------------------------------------------------------------
		 * 
		 * 콤보박스로 선택된 날짜(예:20001023)를 통해 요일을 영문으로 가져오기
		 * ------------------------------------------------------------------
		 * //gc.get(gc.DAY_OF_WEEK); 하면 일요일=1, 월요일=2, ..., 토요일=7이 나오니까, //요일을
		 * 배열로 만들어서 뽑아내면 되겠죠. GregorianCalendar gc=new GregorianCalendar ( 2000,
		 * 10 - 1 , 23 ); String [] dayOfWeek = { "", "Sun", "Mon", .... , "Sat" };
		 * String yo_il = dayOfWeek ( gc.get ( gc.DAY_OF_WEEK ) );
		 * ------------------------------------------------------------------
		 * 
		 * 두 날짜의 차이를 일수로 구하기
		 * ------------------------------------------------------------------
		 * 각각의 날짜를 Date형으로 만들어서 getTime()하면 long으로 값이 나오거든요(1970년 1월 1일 이후-맞던가?-
		 * 1/1000 초 단위로..) 그러면 이값의 차를 구해서요. (1000*60*60*24)로 나누어 보면 되겠죠.
		 * ------------------------------------------------------------------
		 * 
		 * 두 날짜의 차이를 일수로 구하기2
		 * ------------------------------------------------------------------
		 * import java.io.*; import java.util.*;
		 * 
		 * Date today = new Date ( ); Calendar cal = Calendar.getInstance ( );
		 * cal.setTime ( today );// 오늘로 설정.
		 * 
		 * Calendar cal2 = Calendar.getInstance ( ); cal2.set ( 2000, 3, 12 ); //
		 * 기준일로 설정. month의 경우 해당월수-1을 해줍니다.
		 * 
		 * int count = 0; while ( !cal2.after ( cal ) ) { count++; cal2.add (
		 * Calendar.DATE, 1 ); // 다음날로 바뀜
		 * 
		 * System.out.println ( cal2.get ( Calendar.YEAR ) + "년 " + ( cal2.get (
		 * Calendar.MONTH ) + 1 ) + "월 " + cal2.get ( Calendar.DATE ) + "일" ); }
		 * 
		 * System.out.println ( "기준일로부터 " + count + "일이 지났습니다." );
		 * ------------------------------------------------------------------
		 * 
		 * 두 날짜의 차이를 일수로 구하기3
		 * ------------------------------------------------------------------
		 * import java.io.*; import java.util.*;
		 * 
		 * public class DateDiff { public static int GetDifferenceOfDate ( int
		 * nYear1, int nMonth1, int nDate1, int nYear2, int nMonth2, int nDate2 ) {
		 * Calendar cal = Calendar.getInstance ( ); int nTotalDate1 = 0,
		 * nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;
		 * 
		 * if ( nYear1 > nYear2 ) { for ( int i = nYear2; i < nYear1; i++ ) {
		 * cal.set ( i, 12, 0 ); nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR ); }
		 * nTotalDate1 += nDiffOfYear; } else if ( nYear1 < nYear2 ) { for ( int
		 * i = nYear1; i < nYear2; i++ ) { cal.set ( i, 12, 0 ); nDiffOfYear +=
		 * cal.get ( Calendar.DAY_OF_YEAR ); } nTotalDate2 += nDiffOfYear; }
		 * 
		 * cal.set ( nYear1, nMonth1-1, nDate1 ); nDiffOfDay = cal.get (
		 * Calendar.DAY_OF_YEAR ); nTotalDate1 += nDiffOfDay;
		 * 
		 * cal.set ( nYear2, nMonth2-1, nDate2 ); nDiffOfDay = cal.get (
		 * Calendar.DAY_OF_YEAR ); nTotalDate2 += nDiffOfDay;
		 * 
		 * return nTotalDate1-nTotalDate2; }
		 * 
		 * public static void main ( String args[] ) { System.out.println ( "" +
		 * GetDifferenceOfDate (2000, 6, 15, 1999, 8, 23 ) ); } }
		 * ------------------------------------------------------------------
		 * 
		 * 파일에서 날짜정보를 가져오기
		 * ------------------------------------------------------------------
		 * File f = new File ( directory, file );
		 * 
		 * Date date = new Date ( f.lastModified ( ) ); Calendar cal =
		 * Calendar.getInstance ( ); cal.setTime ( date );
		 * 
		 * System.out.println("Year : " + cal.get(Calendar.YEAR));
		 * System.out.println("Month : " + (cal.get(Calendar.MONTH) + 1));
		 * System.out.println("Day : " + cal.get(Calendar.DAY_OF_MONTH));
		 * System.out.println("Hours : " + cal.get(Calendar.HOUR_OF_DAY));
		 * System.out.println("Minutes : " + cal.get(Calendar.MINUTE));
		 * System.out.println("Second : " + cal.get(Calendar.SECOND));
		 * ------------------------------------------------------------------
		 * 
		 * 날짜형식으로 2000-01-03으로 처음에 인식을 시킨후 7일씩 증가해서 1년정도의 날짜를 출력해 주고 싶은데요.
		 * ------------------------------------------------------------------
		 * SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-mm-dd" );
		 * Calendar c = Calendar.getInstance ( );
		 * 
		 * for ( int i = 0; i < 48; i++ ) { c.clear ( ); c.set ( 2000, 1, 3 - (
		 * i * 7 ) ); java.util.Date d = c.getTime ( ); String thedate =
		 * sdf.format ( d ); System.out.println ( thedate ); }
		 * ------------------------------------------------------------------
		 * 
		 * 쓰레드에서 날짜 바꾸면 죽는 문제
		 * ------------------------------------------------------------------
		 * Main화면에 날짜와시간이Display되는 JPanel이 있습니다. date로 날짜와 시간을 변경하면 Main화면의 날짜와
		 * 시간이 Display되는 Panel에 변경된 날짜가 Display되지 않고 Main화면이 종료되어 버립니다.
		 * 
		 * 문제소스: public void run ( ) { while ( true ) { try{ timer.sleep ( 60000 ); }
		 * catch ( InterruptedException ex ) { }
		 * 
		 * lblTimeDate.setText ( fGetDateTime ( ) ); repaint ( ); } }
		 * 
		 * public String fGetDateTime ( ) { final int millisPerHour = 60 * 60 *
		 * 1000; String DATE_FORMAT = "yyyy / MM / dd HH:mm"; SimpleDateFormat
		 * sdf = new SimpleDateFormat ( DATE_FORMAT ); SimpleTimeZone timeZone =
		 * new SimpleTimeZone ( 9 * millisPerHour, "KST" ); sdf.setTimeZone (
		 * timeZone );
		 * 
		 * long time = System.currentTimeMillis ( ); Date date = new Date ( time );
		 * return sdf.format ( date ); }
		 * 
		 * 해답: // 날짜와 요일 구한다. timezone 으로 날짜를 다시 셋팅하시면 됨니다. public String
		 * getDate ( ) { Date now = new Date ( ); SimpleDateFormat sdf4 = new
		 * SimpleDateFormat ( "yyyy/MM/dd HH:mm EE" ); sdf4.setTimeZone (
		 * TimeZone.getTimeZone ( "Asia/Seoul" ) );
		 * 
		 * return sdf4.format ( now ); }
		 * ------------------------------------------------------------------
		 * 
		 * 날짜와 시간이 유효한지 검사하려면...?
		 * ------------------------------------------------------------------
		 * import java.util.*; import java.text.*;
		 * 
		 * public class DateCheck { boolean dateValidity = true;
		 * 
		 * DateCheck ( String dt ) { try { DateFormat df =
		 * DateFormat.getDateInstance ( DateFormat.SHORT ); df.setLenient (
		 * false ); Date dt2 = df.parse ( dt ); } catch ( ParseException e ) {
		 * this.dateValidity = false; } catch ( IllegalArgumentException e ) {
		 * this.dateValidity = false; } }
		 * 
		 * public boolean datevalid ( ) { return dateValidity; }
		 * 
		 * public static void main ( String args [] ) { DateCheck dc = new
		 * DateCheck ( "2001-02-28" ); System.out.println ( " 유효한 날짜 : " +
		 * dc.datevalid ( ) ); } }
		 * ------------------------------------------------------------------
		 * 
		 * 두 날짜 비교하기(아래보다 정확)
		 * ------------------------------------------------------------------ 그냥
		 * 날짜 두개를 long(밀리 세컨드)형으로 비교하시면 됩니다...
		 * 
		 * 이전의 데이타가 date형으로 되어 있다면, 이걸 long형으로 변환하고. 현재 날짜(시간)은
		 * System.currentTimeMillis()메소드로 읽어들이고, 두수(long형)를 연산하여 그 결과 값으로 비교를
		 * 하시면 됩니다.
		 * 
		 * 만약 그 결과값이 몇시간 혹은 며칠차이가 있는지를 계산할려면, 결과값을 Calender의
		 * setTimeInMillis(long millis) 메소드를 이용해 설정한다음 각각의 날짜나 시간을 읽어오시면 됩니다
		 * ------------------------------------------------------------------
		 * 
		 * 두 날짜 비교하기2
		 * ------------------------------------------------------------------
		 * //Calendar를 쓸 경우 데이타의 원본을 고치기 때문에 clone()을 사용하여 //복사한 후에 그 복사본을 가지고
		 * 비교한다 import java.util.*; import java.util.Calendar.*; import
		 * java.text.SimpleDateFormat;
		 * 
		 * public class DayComparisonTest { public static void main(String
		 * args[]) { Calendar cal = Calendar.getInstance(); SimpleDateFormat
		 * dateForm = new SimpleDateFormat("yyyy-MM-dd");
		 * 
		 * Calendar aDate = Calendar.getInstance(); // 비교하고자 하는 임의의 날짜
		 * aDate.set(2001, 0, 1);
		 * 
		 * Calendar bDate = Calendar.getInstance(); // 이것이 시스템의 날짜 // 여기에 시,분,초를
		 * 0으로 세팅해야 before, after를 제대로 비교함 aDate.set( Calendar.HOUR_OF_DAY, 0 );
		 * aDate.set( Calendar.MINUTE, 0 ); aDate.set( Calendar.SECOND, 0 );
		 * aDate.set( Calendar.MILLISECOND, 0 );
		 * 
		 * bDate.set( Calendar.HOUR_OF_DAY, 0 ); bDate.set( Calendar.MINUTE, 0 );
		 * bDate.set( Calendar.SECOND, 0 ); bDate.set( Calendar.MILLISECOND, 0 );
		 * 
		 * 
		 * if (aDate.after(bDate)) // aDate가 bDate보다 클 경우 출력
		 * System.out.println("시스템 날짜보다 뒤일 경우 aDate = " +
		 * dateForm.format(aDate.getTime())); else if (aDate.before(bDate)) //
		 * aDate가 bDate보다 작을 경우 출력 System.out.println("시스템 날짜보다 앞일 경우 aDate = " +
		 * dateForm.format(aDate.getTime())); else // aDate = bDate인 경우
		 * System.out.println("같은 날이구만"); } }
		 * 
		 * 
		 */}
}

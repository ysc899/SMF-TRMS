package kr.co.softtrain.common.web.did.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class DidUtility 
{
	public static boolean checkMandatory(Map<String, Object> param, String key)
	{
		return (param.containsKey(key) && 
				param.get(key) != null &&
				param.get(key).toString().isEmpty() == false);
	}
	
	public static String getValue(Map<String, Object> map, String key)
	{
		return (map.get(key) == null ? "" : map.get(key).toString());
	}
	
	public static String convertDateFormat(String yyyymmdd)
	{
		if (yyyymmdd.length() < 8)
			return yyyymmdd;
		
		StringBuffer sb = new StringBuffer(yyyymmdd);
		sb.insert(6, "-");
		sb.insert(4, "-");
		sb.append(" 00:00");
		
		return sb.toString();
	}
	
	public static boolean isMatched(String a, String b)
	{
		return a != null && a.isEmpty() == false && 
			   b != null && b.isEmpty() == false &&
			   a.trim().equals(b.trim());
	}
	
	/** YYMMDD -> YYYYMMDD */
	public static String getBirthDayFromPersonalNo(String personalNo)
	{
		String birthDay = "";
		if (personalNo.length() == 6)
		{
			String year = personalNo.substring(0, 2);
			String currentYear 
				= String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
			if (Integer.valueOf(year) > Integer.valueOf(currentYear))
			{
				birthDay = "19" + year + personalNo.substring(2);
			}
			else
			{
				birthDay = "20" + year + personalNo.substring(2);
			}
		}
		
		return birthDay;
	}
	
	/** "yyyy-MM-dd" */
	public static java.util.Date getDateFromDashFormat(String str)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			java.util.Date tDate = sdf.parse(str);
			return tDate;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	
	
	
	
}

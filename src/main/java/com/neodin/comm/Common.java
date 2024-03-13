package com.neodin.comm;

/**
 * 이 유형은 VisualAge에서 작성되었습니다.
 */

import java.awt.Color;
import java.awt.Component;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public final class Common {
	public final static Color background = new Color(204, 204, 204);
	public static final int YEAR = 1;
	public static final int MONTH = 2;
	public static final int DATE = 3;
	public static final int MONTHFIRST = 4;
	public static final int MONTHEND = 5;

	public static Component _oldGlassPane = null;

	// public static com.sun.java.swing.JInternalFrame _frame = null;
	public static Properties msg = null;

	public static Properties mImageProperty = null;

	// 2002-02-02 날짜를 바꿔줌
	public static String[] getDateDivideCut(String date) {
		if (date.length() == 0)
			return null;
		int index = 0;
		StringTokenizer tok = new StringTokenizer(date);
		String[] data = new String[tok.countTokens()];
		while (tok.hasMoreTokens()) {
			data[index++] = tok.nextToken();
		}
		return data;
	}

	// 2002-02-02 날짜를 바꿔줌
	public static String[] getDateDivideCut(String date, String str) {
		if (date.length() == 0)
			return null;
		int index = 0;
		StringTokenizer tok = new StringTokenizer(date, str);
		String[] data = new String[tok.countTokens()];
		while (tok.hasMoreTokens()) {
			data[index++] = tok.nextToken();
		}
		return data;
	}

	public static String appendBlanks(String src, int length) {
		String dest = src.trim().substring(0);
		if (src.trim().length() < length) {
			for (int i = 0; i < length - src.length(); i++)
				dest = dest + " ";

		} else {   
			dest = src.substring(0, length);   
		}
		return dest;
	}

	// !
	public final static String IMAGE_PREVIEW = "preview",
			IMAGE_PREVIEW_SMALL = "preview1", IMAGE_NEXT = "next",
			IMAGE_NEXT_SMALL = "next1", IMAGE_FIRST = "first",
			IMAGE_FIRST_SMALL = "first1", IMAGE_LAST = "last",
			IMAGE_LAST_SMALL = "last1", IMAGE_ADD = "add",
			IMAGE_ADD_SMALL = "add1", IMAGE_DELETE = "delete",
			IMAGE_DELETE_SMALL = "delete1", IMAGE_SEARCH = "search",
			IMAGE_SEARCH_SMALL = "search1", IMAGE_CANCEL = "cancel",
			IMAGE_CANCEL_SMAL = "cancel1", IMAGE_OK = "ok",
			IMAGE_OK_SMALL = "ok1", IMAGE_SAVE = "save",
			IMAGE_SAVE_SMALL = "save1";

	public static Hashtable mKey_Image = null;

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
	 * 이 메소드는 VisualAge에서 작성되었습니다.
	 * 
	 * @return java.lang.String
	 * @param day
	 *            java.lang.String
	 */
	public static String change_day1(String day) {

		String return_val = null;

		try {
			return_val = day.substring(0, 4) + "-";
			return_val += (day.substring(4, 6) + "-" + day.substring(6, 8));
		} catch (Exception e) {
		}

		return return_val;

	}

	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다.
	 * 
	 * @return java.lang.String
	 * @param day
	 *            java.lang.String
	 */
	public static String change_day3(String day) {
		String return_val = null;
		try {
			return_val = day.substring(0, 4) + "년 ";
			return_val += (day.substring(4, 6) + "월 " + day.substring(6, 8) + "일 ");
		} catch (Exception e) {
		}
		return return_val;
	}

	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다.
	 * 
	 * @return java.lang.String
	 * @param time
	 *            java.lang.String
	 */
	public static String change_time1(String time) {
		String return_val = null;
		int hh;
		String mm, ss;
		ss = "";
		try {
			if (time.length() == 3) {
				hh = Integer.parseInt(time.substring(0, 1));
				mm = time.substring(1, 3);
			} else if (time.length() == 4) {
				hh = Integer.parseInt(time.substring(0, 2));
				mm = time.substring(2, 4);
			} else if (time.length() == 5) {
				hh = Integer.parseInt(time.substring(0, 1));
				mm = time.substring(1, 3);
				ss = " : " + time.substring(3, 5);
			} else if (time.length() == 6) {
				hh = Integer.parseInt(time.substring(0, 2));
				mm = time.substring(2, 4);
				ss = " : " + time.substring(4, 6);
			} else
				return null;
			if (hh < 13) {
				return_val = hh + " : ";
				return_val += mm + ss + "  AM";
			} else {
				hh -= 12;
				return_val = hh + " : ";
				return_val += mm + ss + "  PM";
			}
		} catch (Exception e) {
		}
		return return_val;
	}

	private static boolean checkDigit(String jumin) {
		// 주민등록번호 체크 로직
		// 1. 주민등록번호의 앞 6자리의 수에 처음부터 차례대로 2,3,4,5,6,7 을 곱한다. 그 다음, 뒤 7자리의 수에 마지막
		// 자리만 제외하고 차례대로 8,9,2,3,4,5 를 곱한다.
		// 2. 이렇게 곱한 각 자리의 수들을 모두 더한다.
		// 3. 모두 더한 수를 11로 나눈 나머지를 구한다.
		// 4. 이 나머지를 11에서 뺀다.
		// 5. 이렇게 해서 나온 최종 값을 주민등록번호의 마지막 자리 수와 비교해서 같으면 유효한 번호이고 다르면 잘못된 값이다.

		String jumin1 = jumin.substring(0, 6);
		String jumin2 = jumin.substring(6, 13);
		int digit1 = Integer.parseInt(jumin1.substring(0, 1)) * 2;
		int digit2 = Integer.parseInt(jumin1.substring(1, 2)) * 3;
		int digit3 = Integer.parseInt(jumin1.substring(2, 3)) * 4;
		int digit4 = Integer.parseInt(jumin1.substring(3, 4)) * 5;
		int digit5 = Integer.parseInt(jumin1.substring(4, 5)) * 6;
		int digit6 = Integer.parseInt(jumin1.substring(5, 6)) * 7;
		int digit7 = Integer.parseInt(jumin2.substring(0, 1)) * 8;
		int digit8 = Integer.parseInt(jumin2.substring(1, 2)) * 9;
		int digit9 = Integer.parseInt(jumin2.substring(2, 3)) * 2;
		int digit10 = Integer.parseInt(jumin2.substring(3, 4)) * 3;
		int digit11 = Integer.parseInt(jumin2.substring(4, 5)) * 4;
		int digit12 = Integer.parseInt(jumin2.substring(5, 6)) * 5;
		int last_digit = Integer.parseInt(jumin2.substring(6, 7));
		int error_verify = (digit1 + digit2 + digit3 + digit4 + digit5 + digit6
				+ digit7 + digit8 + digit9 + digit10 + digit11 + digit12) % 11;
		int sum_digit = 0;
		if (error_verify == 0) {
			sum_digit = 1;
		} else if (error_verify == 1) {
			sum_digit = 0;
		} else {
			sum_digit = 11 - error_verify;
		}
		if (last_digit == sum_digit)
			return true;
		return false;
	}

	/**
	 * <p>
	 * 외국인 주민번호 체크 로직
	 * </p>
	 * 
	 * @param jmno
	 *            주민번호 13자리
	 * @return
	 */
	private static boolean checkForeigner(String jmno) {
		int sum = 0;
		int odd = 0;
		String[] buf = new String[13];
		for (int i = 0; i < 13; i++) {
			buf[i] = String.valueOf(jmno.charAt(i));
		}
		odd = Integer.parseInt(buf[7]) * 10 + Integer.parseInt(buf[8]);
		if (odd % 2 != 0) {
			return false;
		}
		if ((Integer.parseInt(buf[11]) != 6)
				&& (Integer.parseInt(buf[11]) != 7)
				&& (Integer.parseInt(buf[11]) != 8)
				&& (Integer.parseInt(buf[11]) != 9)) {
			return false;
		}
		int[] multipliers = { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 };
		for (int i = 0; i < 12; i++) {
			sum += (Integer.parseInt(buf[i]) * multipliers[i]);
		}
		sum = 11 - (sum % 11);
		if (sum >= 10) {
			sum -= 10;
		}
		sum += 2;
		if (sum >= 10) {
			sum -= 10;
		}
		if (sum != Integer.parseInt(buf[12])) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 국내 주민 번호 체크 로직
	 * </p>
	 * 
	 * @param birthday
	 * @param num
	 */
	private static boolean checkHome(String birthday, String num) {
		int hap = 0;
		for (int i = 0; i < 6; i++) {
			int temp = Integer.parseInt(birthday.substring(i, i + 1)) * (i + 2);
			hap += temp;
		}
		int n1 = Integer.parseInt(num.substring(0, 1));
		int n2 = Integer.parseInt(num.substring(1, 2));
		int n3 = Integer.parseInt(num.substring(2, 3));
		int n4 = Integer.parseInt(num.substring(3, 4));
		int n5 = Integer.parseInt(num.substring(4, 5));
		int n6 = Integer.parseInt(num.substring(5, 6));
		int n7 = Integer.parseInt(num.substring(6, 7));
		hap += n1 * 8 + n2 * 9 + n3 * 2 + n4 * 3 + n5 * 4 + n6 * 5;
		hap %= 11;
		hap = 11 - hap;
		hap %= 10;
		if (hap != n7) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2000-02-17 오후 8:16:00)
	 * 
	 * @return java.lang.String
	 */
	public static String constructResult(String parm) {
		String temp = parm;
		String temp2 = "";
		if (parm.indexOf(".") == -1) {
			if (parm.length() > 3) {
				int pos = parm.length() % 3;
				if (pos != 0) {
					temp2 += parm.substring(0, pos);
					temp2 += ",";
				}
				while (pos < temp.length()) {
					temp2 += parm.substring(pos, pos + 3);
					pos += 3;
					if (pos < temp.length())
						temp2 += ",";
				}
			} else
				temp2 = temp;
		} else {
			temp2 = temp;
		}
		return temp2;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2000-02-18 오전 11:15:44)
	 * 
	 * @return java.lang.String
	 * @param parm
	 *            java.lang.String
	 */
	public static String constructResult2(String parm) {
		String temp = "";
		if (parm.length() == 4) {
			temp = parm.substring(0, 3);
			temp = String.valueOf(Integer.parseInt(temp));
			temp += ".";
			temp += parm.substring(3, 4);
			return temp;
		}
		return parm;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2000-02-18 오전 11:15:44)
	 * 
	 * @return java.lang.String
	 * @param parm
	 *            java.lang.String
	 */
	public static String constructResult3(String parm) {
		String temp = "";
		int i, j;
		i = parm.length();
		j = i - 1;
		if (j < 0)
			j = 0;
		temp = parm.substring(0, j);
		if (j != 0)
			temp = String.valueOf(Integer.parseInt(temp));
		if (temp.trim().equals(""))
			temp = "0";
		temp += ".";
		temp += parm.substring(j, i);
		return temp;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2007-05-04 오후 3:14:40)
	 */
	public static String cutMinus(String str) {
		try {
			String temp = str.substring(str.length() - 1);
			try {
				Integer.parseInt(temp);
				return str;
			} catch (Exception e) {
				return "-" + str.replace(temp.toCharArray()[0], '0');
			}
			// if (str.endsWith("}"))
			// return "-" + str.replace('}', '0');
			// if (str.endsWith("{"))
			// return "-" + str.replace('{', '0');
			// if (str.endsWith("A"))
			// return "-" + str.replace('A', '0');
			// if (str.endsWith("B"))
			// return "-" + str.replace('B', '0');
			// if (str.endsWith("C"))
			// return "-" + str.replace('C', '0');
			// if (str.endsWith("D"))
			// return "-" + str.replace('D', '0');
			// if (str.endsWith("E"))
			// return "-" + str.replace('E', '0');
			// if (str.endsWith("F"))
			// return "-" + str.replace('F', '0');
			// if (str.endsWith("G"))
			// return "-" + str.replace('G', '0');
			// if (str.endsWith("H"))
			// return "-" + str.replace('H', '0');
			// if (str.endsWith("I"))
			// return "-" + str.replace('I', '0');
			// if (str.endsWith("J"))
			// return "-" + str.replace('J', '0');
			// if (str.endsWith("K"))
			// return "-" + str.replace('K', '0');
			// if (str.endsWith("L"))
			// return "-" + str.replace('L', '0');
			// if (str.endsWith("M"))
			// return "-" + str.replace('M', '0');
			// return str;
		} catch (Exception e) {
			return str;
		}
	}

	public final static String cutZeroComa(String parm) {
		String temp = parm;
		String temp2 = "";
		if (parm.indexOf(".") == -1) {
			if (parm.length() > 3) {
				int pos = parm.length() % 3;
				if (pos != 0) {
					temp2 += parm.substring(0, pos);
					temp2 += ",";
				}
				while (pos < temp.length()) {
					temp2 += parm.substring(pos, pos + 3);
					pos += 3;
					if (pos < temp.length())
						temp2 += ",";
				}
			} else
				temp2 = temp;
		} else {
			temp2 = temp;
		}
		return temp2;
	}

	public final static String cutZeroComaZero(String parm) {
		String temp = cutZero(parm);
		if (temp.equals(""))
			return temp;
		String temp2 = "";
		if (temp.indexOf(".") == -1) {
			if (temp.length() > 3) {
				int pos = temp.length() % 3;
				if (pos != 0) {
					temp2 += temp.substring(0, pos);
					temp2 += ",";
				}
				while (pos < temp.length()) {
					temp2 += temp.substring(pos, pos + 3);
					pos += 3;
					if (pos < temp.length())
						temp2 += ",";
				}
			} else
				temp2 = temp;
		} else {
			temp2 = Double.valueOf(temp) + "";
		}
		return temp2;
	}

	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다.
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public static String cvtDBCStoSBCS(String str) {
		StringBuffer sb = new StringBuffer();
		char[] c = str.toCharArray();

		for (int i = 0; i < str.length(); i++) {
			char convert = c[i];

			if (convert == '　')
				convert = ' ';
			sb.append(convert);
		}

		return sb.toString();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2000-03-08 오전 11:36:06)
	 * 
	 * @param isup
	 *            boolean
	 */
	public static String dayUpDown(String current, boolean isup) {
		String year = current.substring(0, 4);
		String month = current.substring(4, 6);
		String day = current.substring(6);
		String _year = "";
		String _month = "";
		String _day = "";
		java.util.GregorianCalendar gc = new java.util.GregorianCalendar(
				Integer.parseInt(year), Integer.parseInt(month) - 1, Integer
						.parseInt(day));
		gc.roll(java.util.Calendar.DAY_OF_YEAR, isup);
		gc.getGregorianChange();
		_year = Integer.toString(gc.get(java.util.Calendar.YEAR));
		if (gc.get(java.util.Calendar.MONTH) + 1 > 9)
			_month = Integer.toString(gc.get(java.util.Calendar.MONTH) + 1);
		else
			_month = "0"
					+ Integer.toString(gc.get(java.util.Calendar.MONTH) + 1);
		if (gc.get(java.util.Calendar.DAY_OF_MONTH) > 9)
			_day = Integer.toString(gc.get(java.util.Calendar.DAY_OF_MONTH));
		else
			_day = "0"
					+ Integer.toString(gc.get(java.util.Calendar.DAY_OF_MONTH));

		// !

		if ((_day.equals("01") && _month.equals("01") && isup)
				|| (day.equals("01") && month.equals("01") && !isup)) {
			gc.roll(java.util.Calendar.YEAR, isup);
			gc.getGregorianChange();
			_year = Integer.toString(gc.get(java.util.Calendar.YEAR));
		}
		return _year + _month + _day;
	}

	/**
	 * 이 메소드는 자바에서 AS400 형태의 oe of를 포함한 적용 가능한 사이즈의 문자열을 나눠주는 메소드 입니다. ( String
	 * 처리이전의 문자열, int 원하는 크기) made by oklee
	 */
	public static Vector divideCheckMethodString(String before_divide_string,
			int want_length) {
		Vector divided_string_vector_type = new Vector(); // return 될 vector
		// 선언
		Vector special_char_removed_string_vector_type = new Vector(); // 작업해야
		// 할
		// 특수문자가
		// 제거된
		// 스트링
		// vector

		// 특수문자가 제거된 스트링의 vector를 구한다.
		special_char_removed_string_vector_type = getStringRemoved_SpectialChar(before_divide_string);
		String current_divided_string = ""; // current divided string
		int data_full_length = 0; // 입력받은 문자열의 길이
		int checked_JAVA_length = 0; // current의 want_length 내에서의 증가량
		boolean length_zero_checker = false;
		for (int string_cnt = 0; string_cnt < special_char_removed_string_vector_type
				.size(); string_cnt++) {
			before_divide_string = special_char_removed_string_vector_type
					.elementAt(string_cnt).toString();
			current_divided_string = ""; // current divided string
			data_full_length = before_divide_string.length(); // 특수문자 제거 처리된 한
			// 문자열의 길이 확보
			checked_JAVA_length = 0; // current의 want_length 내에서의 증가량

			int cutcnt = 0;
			int totalcnt = 0;
			while (true) {
				if (data_full_length - checked_JAVA_length == 0
						&& length_zero_checker) {
					break;
				}

				// 나눠야할 마지막 string
				if ((data_full_length - checked_JAVA_length) < want_length) {
					try {
						if (cutcnt > 0
								&& totalcnt == 0
								&& (before_divide_string.substring(cutcnt - 1)
										.startsWith(" "))) {
							current_divided_string = before_divide_string
									.substring(--checked_JAVA_length);
						} else {
							current_divided_string = before_divide_string
									.substring(checked_JAVA_length);
						}
					} catch (Exception eee) {
						current_divided_string = before_divide_string
								.substring(checked_JAVA_length);
					}
					if (dividedCharCheckForAS400(current_divided_string) <= want_length) {
						if (totalcnt > 0
								&& (before_divide_string
										.substring(totalcnt - 1)
										.startsWith(" "))) {
							divided_string_vector_type.addElement(" "
									+ current_divided_string);
						} else {
							divided_string_vector_type
									.addElement(current_divided_string);
						}
						break;
					} else {
						int bytes_length = 0;
						while (true) {
							bytes_length = current_divided_string.getBytes().length;
							if (bytes_length <= want_length) {
								break;
							}
							bytes_length = ((bytes_length % 2) == 1) ? ++bytes_length
									: bytes_length;
							current_divided_string = current_divided_string
									.substring(
											0,
											(current_divided_string.length() - ((bytes_length - want_length) / 2)));
						} // while

						int as400_length = 0;
						while (true) {
							as400_length = dividedCharCheckForAS400(current_divided_string);
							if (as400_length <= want_length) {
								break;
							}
							if ((as400_length % 2) == 1) {
								++as400_length;
							}
							cutcnt = current_divided_string.length()
									- ((as400_length - want_length) / 2);
							current_divided_string = current_divided_string
									.substring(0, cutcnt);
						} // while
						totalcnt += cutcnt;
						divided_string_vector_type
								.addElement(current_divided_string);
						checked_JAVA_length += current_divided_string.length();
					} // else

				} // if

				// size 크기 만큼의 길이가 가능한 string
				else {
					length_zero_checker = true; // 한번 자료를 넣었음을 체크
					current_divided_string = before_divide_string.substring(
							checked_JAVA_length,
							(checked_JAVA_length + want_length));
					if (dividedCharCheckForAS400(current_divided_string) > want_length) {
						int bytes_length = 0;
						while (true) {
							bytes_length = current_divided_string.getBytes().length;
							if (bytes_length <= want_length) {
								break;
							}
							bytes_length = ((bytes_length % 2) == 1) ? ++bytes_length
									: bytes_length;
							current_divided_string = current_divided_string
									.substring(
											0,
											current_divided_string.length()
													- ((bytes_length - want_length) / 2));
						} // while

						int as400_length = 0;
						while (true) {
							as400_length = dividedCharCheckForAS400(current_divided_string);
							if (as400_length <= want_length) {
								break;
							}
							if ((as400_length % 2) == 1) {
								++as400_length;
							}
							cutcnt = current_divided_string.length()
									- ((as400_length - want_length) / 2);
							current_divided_string = current_divided_string
									.substring(0, cutcnt);
						} // while
						totalcnt += cutcnt;
					} // if

					divided_string_vector_type
							.addElement(current_divided_string);
					checked_JAVA_length += current_divided_string.length();
				} // else

			} // while all...

		} // for ...
		return divided_string_vector_type;
	}

	/**
	 * 이 메소드는 divideCheckMethodString에서 호출하는 메소드입니다. made by oklee
	 */
	public static int dividedCharCheckForAS400(String Java_type_string) {
		int checked_JAVA_length = Java_type_string.length();
		int checked_BYTE_length = Java_type_string.getBytes().length;
		int checked_AS400_length = checked_BYTE_length;
		if (checked_JAVA_length > 2) {
			// 첫글이 2 byte 문자이면 OE / OF를 계산한다.
			if (Java_type_string.substring(0, 1).getBytes().length != 1) {
				checked_AS400_length++;
			}
			for (int i = 0; i < (Java_type_string.length() - 2); i++) {
				if ((Java_type_string.substring(i, i + 1).getBytes().length) != (Java_type_string
						.substring(i + 1, i + 2).getBytes().length)) {
					checked_AS400_length++;
				}
			}
			if ((Java_type_string.substring((Java_type_string.length() - 2),
					(Java_type_string.length() - 1)).getBytes().length) != (Java_type_string
					.substring(Java_type_string.length() - 1).getBytes().length)) {
				checked_AS400_length++;
			}

			// 마지막 글이 2 byte 문자이면 OE / OF를 계산한다.
			if ((Java_type_string.substring(Java_type_string.length() - 1)
					.getBytes().length) != 1) {
				checked_AS400_length++;
			}
			return checked_AS400_length;
		} else if (checked_BYTE_length > checked_JAVA_length) {
			return checked_AS400_length + 2; // 2 <- OE/OF size
		} else {
			return checked_AS400_length;
		}
	}

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2002-06-05 오후 1:28:03) 작 성 자 : 조 남 식
	 * 
	 * @param string
	 *            java.lang.String
	 */

	/**
	 * 이메소드는 AS400에서 문장형결과(remark결과,문장형결과)를 가져올때 영문+한글(abce는) 과 반대의 경우에서
	 * ODBC=>MDB 올리는 과정에서의 SQLException 문제를 해결하기 위함 관련메소드 divStrig
	 */
	public static String divideString(String str) {
		if (str.length() == 0)
			return "";
		java.util.StringTokenizer token = new java.util.StringTokenizer(str);
		StringBuffer b = new StringBuffer("");
		while (token.hasMoreTokens()) {
			b.append(divString(token.nextToken()));
		}
		return b.toString();
	}

	/**
	 * 이 메소드는 자바에서 AS400 형태의 oe of를 포함한 적용 가능한 사이즈의 문자열을 나눠주는 메소드 입니다. 파라메터 src -
	 * 작업할 source string max - 분리될 각 문자열의 허용하는 maximum length
	 */
	public static Vector divideString(String src, int max) {
		Vector dst = new Vector();
		try {
			int idx = 0;
			int tot = src.length();
			boolean eos = false;
			do {
				boolean dbcs = false;
				String work = "";
				int length = 0;
				while (!eos && length < max) {
					char chr = src.charAt(idx);
					int len = String.valueOf(chr).getBytes().length;
					length += len;
					if (len > 1) {
						if (!dbcs) {
							dbcs = true;
							length++;
						}
					} else if (dbcs) {
						dbcs = false;
						length++;
					}
					if (length >= max)
						break;
					work += chr;
					eos = ++idx >= tot;
				}
				if (dbcs && !eos) {
					idx--;
					work = work.substring(0, work.length() - 1);
				}
				if (work.length() > 0)
					dst.addElement(work);
			} while (!eos);
		} catch (Exception e) {
		}
		return dst;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2003-02-18 오후 2:02:55) 작 성 자 : 조 남 식 설 명 : 이 메소드는
	 * Java => AS400 으로 DPC 를 사용해서 저장 하기 위한 메소드이다. Text(60) + "|"(1) => 총 61 자를
	 * 만들어 준다. Return 값은 전체 문장
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 * @param len
	 *            int
	 */
	public static String[] divideStringFromJavaToAS400(String str, int len) {
		if (str.equals("") || len == 0)
			return null;
		String resultdata[] = new String[2];
		Vector result = divideCheckMethodString(str, len);
		int max = result.size();
		StringBuffer returnstr = new StringBuffer();
		for (int i = 0; i < max; i++) {
			String resulttext = result.elementAt(i).toString();
			int lens = Common.dividedCharCheckForAS400(resulttext);
			if (lens != len) {
				String blank = "";
				for (int a = 0; a < len - lens; a++) {
					blank += " ";
				}
				returnstr.append(resulttext + blank + "|");
			} else {
				returnstr.append(resulttext + "|");
			}
		}
		resultdata[0] = max + ""; // SEQ
		resultdata[1] = returnstr.toString(); // TEXT
		return resultdata;
	}

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2002-06-05 오후 1:28:03) 작 성 자 : 조 남 식
	 * 
	 * @param string
	 *            java.lang.String
	 */

	/**
	 * 이메소드는 AS400에서 문장형결과(remark결과,문장형결과)를 가져올때 영문+한글(abce는) 과 반대의 경우에서
	 * ODBC=>MDB 올리는 과정에서의 SQLException 문제를 해결하기 위함 관련메소드 divideString(String)
	 */
	public static String divString(String str) {
		if (str.length() == 0)
			return "";
		StringBuffer cString, pString;
		String endChar = ".";
		StringBuffer returnStr = new StringBuffer("");
		for (int i = 0; i < str.trim().length(); i++) {

			/* 현재문자 */
			cString = new StringBuffer("");
			if (str.charAt(i) == '\'')
				cString.append("\"");
			else
				cString.append(str.charAt(i));
			String CcharAt = cString.toString();
			if (i > 0) { // 앞 문자(영문 or 한글) 을 저장

				pString = new StringBuffer("");
				pString.append(str.charAt(i - 1));
				String pCharAt = pString.toString();
				if (pCharAt.getBytes().length == CcharAt.getBytes().length) { // 이전문자
					// 길이와
					// 현재
					// 길이를
					// 비교

					returnStr.append(CcharAt);
				} else if (!CcharAt.equals(endChar)) {
					returnStr.append(" " + CcharAt);
				}
			} else
				returnStr.append(CcharAt);
		}
		return returnStr.toString().trim() + " ";
	}
    
	/**
	 * 이 메소드는 dpc 프로그램의 결과의 배열화 작업이다. made by oklee
	 */
	public  String[] getArrayTypeData(List<Map<String, Object>> parmList, String getName) {
		Map<String, Object> rowMap = null;
		int res_cnt = parmList.size();
		String result[] = new String[res_cnt];

		if (res_cnt == 0)
			return result;

		int all_size = 0;
		try {
			for (int i = 0; i < res_cnt; i++) {
				rowMap =  parmList.get(i);
				
				String ResultStr = rowMap.get(getName).toString();
				ResultStr =  ResultStr.replace("|", "");
				result[i] = ResultStr.trim();
			}
		} catch (Exception e) {
		}

		return result;
	}


	/**
	 * 이 메소드는 dpc 프로그램의 결과의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData(String data, int size, int res_cnt) {

		String result[] = new String[res_cnt];

		if (res_cnt == 0)
			return result;

		int all_size = 0;
		try {
			for (int i = 0; i < res_cnt; i++) {
				if (all_size + size < data.length())
					result[i] = data.substring(all_size, all_size + size)
							.trim();
				else
					result[i] = data.substring(all_size).trim();
				all_size += size;
			}
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLast(String data) {
		if (data.equals(""))
			return null;
		Vector result = new Vector();
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, "|");
		while (str.hasMoreTokens()) {
			result.addElement(str.nextToken("|").trim());
		}
		String[] src = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			src[i] = result.elementAt(i).toString().trim();
		}
		return src;
	}

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLast(String data, int res_cnt) {
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
			result[i] = str.nextToken("|").trim();
			i++;
			if (i == res_cnt)
				break;
		}
		return result;
	}

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLast_NoTrim(String data,
			int res_cnt) {
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
	public static String[] getArrayTypeData_CheckLast1(String data, int res_cnt) {
		String result[] = new String[res_cnt];
		if (res_cnt == 0)
			return result;
		for (int i = 0; i < res_cnt; i++)
			result[i] = "";
		int i = 0;
		if (data.substring(0, 1).equals(",")) {
			data = data.substring(1);
			result[i++] = "";
			if (res_cnt == 1) {
				return result;
			}
		}
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, ",");
		while (str.hasMoreTokens()) {
			result[i] = str.nextToken(",").trim();
			i++;
			if (i == res_cnt)
				break;
		}
		return result;
	}

	/**
	 * 이 메소드는 DPC 프로그램 호출시 필드의 특성상 분리자 | 가있는 스트링의 배열화 작업이다. made by oklee
	 */
	public static String[] getArrayTypeData_CheckLastTrim(String data) {
		if (data.equals(""))
			return null;
		Vector result = new Vector();
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, "|");
		while (str.hasMoreTokens()) {
			if (str.nextToken("|").trim().length() == 0) {
				result.addElement(str.nextToken("|").trim());
			}
		}
		String[] src = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			src[i] = result.elementAt(i).toString().trim();
		}
		return src;
	}

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2001-04-03 오후 6:55:33)
	 * 
	 * @작성자: Administrator
	 */
	public static String[] getArrayTypeDataNoTrim(String data, int size,
			int res_cnt) {
		String result[] = new String[res_cnt];
		if (res_cnt == 0)
			return result;
		int all_size = 0;
		try {
			for (int i = 0; i < res_cnt; i++) {
				if (all_size + size < data.length())
					result[i] = data.substring(all_size, all_size + size);
				else
					result[i] = data.substring(all_size);
				all_size += size;
			}
		} catch (Exception e) {
		}
		return result;
	}

  /**
   * 배열 reverse
   * 
   * @작성자: Administrator
   */
  public static String[] reverseArrayString(String[] array) {
    // 배열을 리스트로 변환
    List<String> list = Arrays.asList(array);
    // 리스트 뒤집어 주기
    Collections.reverse(list);
    // 리스트를 배열로 다시 변환
    array = list.toArray(new String[list.size()]);
    return array;
  }
	
	public static int getAS400Length(String src) {
		int idx = 0;
		int tot = src.length();
		int length = 0;
		boolean dbcs = false;

		try {
			while (idx < tot) {
				char chr = src.charAt(idx++);
				int chrLen = String.valueOf(chr).getBytes().length;

				length += chrLen;

				if (chrLen > 1) {
					if (!dbcs) {
						dbcs = true;
						length++;
					}
				} else if (dbcs) {
					dbcs = false;
					length++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return length;
	}

	public static String[] getCustom_Branch(String cus_brch) {
		final StringTokenizer token = new StringTokenizer(cus_brch, "-");
		final String[] value = new String[2];
		int index = -1;
		while (token.hasMoreTokens()) {
			value[++index] = token.nextToken().trim();
		}
		return value;
	}

	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다. 문자에 해당문자 찾아서 제거
	 */

	// 2002-02-02 날짜를 바꿔줌
	public static String[] getDataCut(String date, String str) {
		// if (date.length() == 0)
		// return new String[] {""};
		// StringTokenizer tok = new StringTokenizer(date, str);
		// String[] data = new String[tok.countTokens()];
		// int i = 0;
		// while (tok.hasMoreTokens()) {
		// data[i++] = tok.nextToken();
		// }
		// return data;
		// }
		// !
		if (date.length() == 0)
			return new String[] { "" };

		// !
		String d = "";
//		int index = 1;
		boolean flag = false;
		// !
		StringTokenizer tok = new StringTokenizer(date, str, true);
		// String[] data = new String[tok.countTokens()];
		Vector vet = new Vector();
		// String[] rtn = null;
		// int i = 0;
		while (tok.hasMoreTokens()) {
			d = tok.nextToken();
			if (str.equals(d)) {
				if ((flag) && (tok.hasMoreTokens())) {
					d = tok.nextToken();
				}
			} else {
				flag = true;
			}
			if (str.equals(d)) {
				flag = false;
				d = "";
			}
			vet.addElement(d);
		}
		int size = vet.size();
		String[] data = new String[size];
		for (int i = 0; i < size; i++) {
			data[i] = vet.elementAt(i).toString().trim();
		}
		return data;
	}

	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다. 문자에 해당문자 찾아서 제거
	 */

	// 2002-02-02 날짜를 바꿔줌
	public static String getDateCut(String date, String str) {

		if (date.length() == 0)
			return "";

		StringTokenizer tok = new StringTokenizer(date, str);
		String data = "";

		while (tok.hasMoreTokens()) {
			data += tok.nextToken();
		}

		return data;
	}

	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다.
	 * 
	 * @return java.lang.String
	 * @param date
	 *            java.lang.String
	 */

	// 2002-02-02 날짜를 바꿔줌
	public static String getDateFormat(String date) {
		String dateFormat = "";

		if (date.length() == 8)
			dateFormat = date.substring(0, 4) + "-" + date.substring(4, 6)
					+ "-" + date.substring(6, 8);

		return dateFormat.trim();
	}

	public static String getDaySeperate(String day) {
		if (day.length() == 0)
			return "";
		return day.substring(0, 4) + "-" + day.substring(4, 6) + "-"
				+ day.substring(6, 8);
	}

	public static String getDaySeperate_mmddyy(String day) {
		if (day.length() != 8)
			return day;
		if (Integer.parseInt(day) == 0)
			return "";
		return day.substring(4, 6) + "/" + day.substring(6, 8) + "/"
				+ day.substring(2, 4);
	}

	public static String getDaySeperate_mmddyy(String day, String div) {
		if (day.length() != 8)
			return day;
		if (Integer.parseInt(day) == 0)
			return "";
		return day.substring(4, 6) + div + day.substring(6, 8) + div
				+ day.substring(2, 4);
	}

	public static String getDaySeperate_mmddyyyy(String day) {
		if (day.length() != 8)
			return day;
		if (Integer.parseInt(day) == 0)
			return "";
		return day.substring(4, 6) + "/" + day.substring(6, 8) + "/"
				+ day.substring(0, 4);
	}

	public static String getDaySeperate_mmddyyyy(String day, String div) {
		if (day.length() != 8)
			return day;
		if (Integer.parseInt(day) == 0)
			return "";
		return day.substring(4, 6) + div + day.substring(6, 8) + div
				+ day.substring(0, 4);
	}

	public static String getDaySeperate2(String day) {
		if (day.length() != 8)
			return day;
		if (Integer.parseInt(day) == 0)
			return "";
		return day.substring(2, 4) + "/" + day.substring(4, 6) + "/"
				+ day.substring(6, 8);
	}

	public static String[] getDeID(Object id) {
		if (id == null)
			return new String[] { "", "" };
		String rId = id.toString().trim();
		if (rId.length() == 0)
			return new String[] { "", "" };
		if (rId.startsWith("-"))
			return new String[] { "", rId.substring(rId.indexOf("-")).trim() };
		final StringTokenizer token = new StringTokenizer(rId, "-");
		final int tokenCnt = token.countTokens();
		return tokenCnt == 2 ? new String[] { token.nextToken().trim(),
				token.nextToken().trim() } : new String[] { token.nextToken(),
				"" };
	}

	public final static String getFormattedDate(String src) {
		return getFormattedDate(src, '/');
	}

	public final static String getFormattedDate(String src, char sep) {
		if (src == null)
			return "";

		int len = src.length();
		if (len < 6)
			return "";

		len -= 4;
		return src.substring(0, len) + sep + src.substring(len++, ++len) + sep
				+ src.substring(len);
	}

	public final static String getFormattedTime(String src) {
		return getFormattedTime(src, ':');
	}

	public final static String getFormattedTime(String src, char sep) {
		if (src == null)
			return "";

		int len = src.length();
		if (len < 5)
			return "";

		len -= 4;

		int hour = 0;
		try {
			hour = Integer.parseInt(src.substring(0, len));
		} catch (Exception e) {
			return "";
		}

		boolean isAM = hour < 12;
		if (!isAM) {
			if (hour != 12)
				hour -= 12;
		}

		return "" + hour + sep + src.substring(len++, ++len) + sep
				+ src.substring(len) + " " + (isAM ? "A" : "P") + "M.";
	}

	/**
	 * 해당연도에 해당하는 달의 마지막 날을 리턴...
	 */

	public static int getLastDayOfMonth(String year, String month) {
		if (year.length() != 4 || month.length() >= 3)
			return 0;

		final boolean check = (Integer.parseInt(year) % 4 == 0);
		final String[] days = { "31", check ? "29" : "28", "31", "30", "31",
				"30", "31", "31", "30", "31", "30", "31" };

		for (int i = 0, index = days.length; i < index; i++) {
			if (month.charAt(0) == '0')
				month = month.substring(1);

			if (month.equals("" + (i + 1))) {
				return Integer.parseInt(days[i]);
			}
		}
		return 0;
	}

	/**
	 * 메소드 설명을 삽입하십시오.
	 * 
	 * 작성 날짜: (2001-03-20 오후 4:24:04)
	 * 
	 * 메세지 파일 을 읽어 메세지를 넘겨준다.
	 * 
	 * @작성자 : 김영은
	 */
	public static String getMessage(String key) {
		if (msg == null) {
			java.io.FileInputStream fis = null;

			try {
				fis = new java.io.FileInputStream(System
						.getProperty("user.dir")
						+ "/message/메세지");
				msg = new Properties();
				msg.load(fis);
			} catch (Exception e) {
			} finally {
				if (fis != null)
					try {
						fis.close();
					} catch (Exception e) {
					}
			}
		}

		return msg != null ? msg.get(key).toString() : "";
	}

	public static String getNumber(String day) {
		if (day.length() == 0)
			return "";
		StringTokenizer token = new StringTokenizer(day, ",");
		StringBuffer b = new StringBuffer("");
		while (token.hasMoreTokens()) {
			b.append(token.nextToken());
		}
		return b.toString();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-09-03 오전 10:15:02)
	 */
	public static String getPercent(int x, int y) {

		// ////////////////////////////////////////////////////////
		// 전체값에서 일부값은 몇 퍼센트? 계산
		// 공식은 "일부값 나누기 전체값 곱하기 100"
		// ////////////////////////////////////////////////////////

		// 10은 100에서 몇 퍼센트?
		// System.out.format("%.2f%%%n", 10.0 / 100.0 * 100.0);
		// 출력 결과: 10.00%

		// 33은 100에서 몇 퍼센트?
		// int x = 33;
		// int y = 100;
		// System.out.println((double) x / (double) y * 100.0 + "%");

		return new java.text.DecimalFormat("###.#").format((100 - (double) x
				/ (double) y * 100.0))
				+ "%";
		// 출력 결과: 33.0%
		// 정수의 경우에는 (double) 로 실수화시키지 않으면
		// 0.0 이라는 엉뚱한 값이 나옴
		// 맨 끝의 + "%" 이 부분은 공식의 일부가 아니라 퍼센트 기호 출력

		// // 105는 300의 몇퍼센트?
		// double x2 = 105.0;
		// double y2 = 300.0;
		// System.out.format("%.2f%%%n", x2 / y2 * 100.0);
		// // 출력 결과: 35.00%
		// // 실수형 변수에 값을 넣어두는 것이 좋음

		// // 한달 봉급 156만원인 사람이, 음식 값으로 21만원을 쓰면,
		// // 그 음식값은 한 달 봉급의 몇 퍼센트?
		// System.out.format("%.2f%%%n", 210000.0 / 1560000.0 * 100.0);
		// // 출력 결과: 13.46%

		// // 만약 봉급 156만원으로 모두 먹는 데 사용했다면
		// // 100% 가 나와야겠지요.
		// System.out.format("%.2f%%%n", 1560000.0 / 1560000.0 * 100.0);
		// // 출력 결과: 100.00%

		// // 만약 아무것도 먹지 않았면 0% 가 나와야합니다.
		// System.out.format("%.2f%%%n", 0.0 / 1560000.0 * 100.0);
		// // 출력 결과: 0.00%

		// //////////////////////////////////////////////////////////
		// // 전체값의 몇 퍼센트는 얼마? 계산
		// // 공식은, "전체값 곱하기 퍼센트 나누기 100"
		// //////////////////////////////////////////////////////////

		// // 100의 10퍼센트는 얼마?
		// System.out.format("%.2f%n", 100.0 * 10.0 / 100.0);
		// // 출력 결과: 10.00

		// // 100의 33퍼센트는 얼마?
		// System.out.format("%.2f%n", 100.0 * 33.0 / 100.0);
		// // 출력 결과: 33.00

		// // 300의 35퍼센트는 얼마?
		// System.out.format("%.2f%n", 300.0 * 35.0 / 100.0);
		// // 출력 결과: 105.00

		// // 156만원의 13.46퍼센트는 얼마?
		// System.out.format("%.2f%n", 1560000.0 * 13.46 / 100.0);
		// // 출력 결과 (21만원에 가까운 값): 209976.00

		// // 156만원의 100퍼센트는 얼마?
		// System.out.format("%.2f%n", 1560000.0 * 100.0 / 100.0);
		// // 출력 결과: 1560000.00

		// // 156만원의 0퍼센트는 얼마?
		// System.out.format("%.2f%n", 1560000.0 * 0.0 / 100.0);
		// // 출력 결과: 0.00

	}

	public static String getRealDate(String date) {
		String dateFormat = "";
		if (date.length() == 10)
			dateFormat = date.substring(0, 4) + date.substring(5, 7)
					+ date.substring(8, 10);
		else
			return date.trim();
		return dateFormat.trim();
	}

	// !
	/**
	 * 해당연도, 달에 남아 있는 일수를 구한다.
	 * 
	 */

	public static int getRemainDay(String year, String month, String day) {
		if (year.length() != 4 || month.length() >= 3 || day.length() >= 3)
			return 0;

		final boolean check = (Integer.parseInt(year) % 4 == 0);
		final String[] days = { "31", check ? "29" : "28", "31", "30", "31",
				"30", "31", "31", "30", "31", "30", "31" };

		for (int i = 0, index = days.length; i < index; i++) {
			if (month.charAt(0) == '0')
				month = month.substring(1);

			if (month.equals("" + (i + 1))) {
				return (Integer.parseInt(days[i]) - Integer.parseInt(day));
			}
		}
		return 0;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2007-03-23 오후 5:59:37)
	 */
	public static String getSex(String idNum) {
		try {
			String tmp = "";
			String Sex = ""; // 만 나이

			if (idNum.length() == 13)
				tmp = String.valueOf(idNum.charAt(6));
			else if (idNum.length() == 14)
				tmp = String.valueOf(idNum.charAt(7));
			else
				return "";
			if (tmp.equals("1") || tmp.equals("3") || tmp.equals("5")
					|| tmp.equals("7") || tmp.equals("9")) {
				Sex = "M";
			} else if (tmp.equals("2") || tmp.equals("4") || tmp.equals("6")
					|| tmp.equals("8") || tmp.equals("0")) {
				Sex = "F";
			} else {
				Sex = "";
			}
			return Sex;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 이 메소드는 입력 받은 스트링의 \n, \t, \f, \r 를 제외한 스트링을 \n 를 기준으로 잘라 리턴하는 메소드입니다.
	 * 
	 * by oklee
	 */

	public static Vector getStringRemoved_SpectialChar(
			String before_divide_string) {

		Vector newline_removed_string_vector_type = new Vector(); // return 될
		// vector 선언

		int string_size = before_divide_string.length();

		String one_vector_string = "";
		String check_one_size_string = "";

		for (int i = 0; i < string_size; i++) {
			if (i == (string_size - 1)) {
				check_one_size_string = before_divide_string.substring(i);

				if (check_one_size_string.equals("\n")) {
					newline_removed_string_vector_type
							.addElement(one_vector_string);
					one_vector_string = "";
				} else if (check_one_size_string.equals("\t")) {
					newline_removed_string_vector_type
							.addElement(one_vector_string + "        ");
					one_vector_string = "";
				} else if ((check_one_size_string.equals("\r") == false)
						&& (check_one_size_string.equals("\f") == false)) {
					newline_removed_string_vector_type
							.addElement(one_vector_string
									+ check_one_size_string);
					one_vector_string = "";
				}
			} else {
				check_one_size_string = before_divide_string.substring(i,
						(i + 1));
			}

			if (check_one_size_string.equals("\n")) {
				newline_removed_string_vector_type
						.addElement(one_vector_string);
				one_vector_string = "";
			} else if (check_one_size_string.equals("\t")) {
				one_vector_string += "        ";
			} else if ((check_one_size_string.equals("\r") == false)
					&& (check_one_size_string.equals("\f") == false)) {
				one_vector_string += check_one_size_string;
			}
		}

		return newline_removed_string_vector_type;
	}

	/**
	 * 이 메소드는 입력 받은 스트링의 \n, \t, \f, \r 를 제외한 스트링을 \n 를 기준으로 잘라 리턴하는 메소드입니다.
	 * 
	 * by oklee
	 */

	public static String[] getStringSpectialChar(String before_divide_string) {

		// !
		Vector vt = getStringRemoved_SpectialChar(before_divide_string);
		String[] str = new String[vt.size()];
		int cnt = str.length;

		// !
		for (int i = 0; i < cnt; i++) {
			str[i] = vt.elementAt(i).toString();
		}
		return str;
	}

	public static boolean isValidSSNWithString(String str_SSN) {
		if (str_SSN.trim().length() < 13)
			return false;

		// !
		str_SSN = str_SSN.replace('-', ' ').trim();
		str_SSN = str_SSN.replace(' ', ' ').trim();

		// !
		String birthday = str_SSN.substring(0, 6);
		String num = str_SSN.substring(6, 13);
		String num_ = str_SSN.substring(7, 13);
		if (birthday.length() != 6) {
			return false;
		}
		if (num.length() != 7) {
			return false;
		}
		if (num_.equals("000000")) {
			return false;
		}

		// !
		String firstStr = num.substring(0, 1);
		if (("1".equals(firstStr)) || ("2".equals(firstStr))
				|| ("3".equals(firstStr)) || ("4".equals(firstStr))) {
			return checkHome(birthday, num);
		} else if (("5".equals(firstStr)) || ("6".equals(firstStr))
				|| ("7".equals(firstStr)) || ("8".equals(firstStr))) {
			return checkForeigner(birthday + num);
		}
		return false;
	}

	public static boolean validJunmin(String jumin) {
		// 입력 받은 주민번호 숫자 여부 체크===============================
		try {
			// Double.parseDouble(jumin);
			// Double.
		} catch (Exception e) {
			return false;
		}

		// 입력받은 주민등록 번호 자리수 체크==============================
		int len = jumin.length();
		if (len != 13)
			return false;

		// 입력받은 주민번호 앞자리 유효성 검증============================
		String jumin1 = jumin.substring(0, 6);
		// String jumin2 = jumin.substring(6, 13);
		int yy = Integer.parseInt(jumin1.substring(0, 2));
		int mm = Integer.parseInt(jumin1.substring(2, 4));
		int dd = Integer.parseInt(jumin1.substring(4, 6));
		if (yy < 1 || yy > 99 || mm > 12 || mm < 1 || dd < 1 || dd > 31) {
			return false;
		}

		// 주민등록번호 유효성 검증
		if (!checkDigit(jumin))
			return false;
		return true;
	}

	public static String change_day2(String day) {
		String return_val = "";
		try {
			return_val += (day.substring(4, 6) + "-" + day.substring(6, 8));
		} catch (Exception e) {
		}
		return return_val;
	}

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2002-07-12 오전 9:13:24) 작 성 자 : 조 남 식
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */

	/**
	 * 이메소드는 텍스트를 읽어 오는중에 #,$,&,*"" 마지막에 붙어 있으면 자름 사용예) Dynex 장비 인터페이스중 결과 읽어오기 /
	 * 자료 백업하기
	 */

	public static String cut_Special_Character(String str) {
		String result = "";
		if (str.length() == 0)
			return null;
		else
			for (int i = 1; i <= str.length(); i++)
				if (str.substring(i - 1, i).equals("")
						|| str.substring(i - 1, i).equals("*")
						|| str.substring(i - 1, i).equals("#")) {
					result = str.substring(0, i - 1);
					break;
				}
		return result.length() == 0 ? str : result;
	}

	/**
	 * 유형 설명을 삽입하십시오. 작성 날짜: (2002-07-12 오전 9:13:24) 작 성 자 : 조 남 식
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */

	/**
	 * 이메소드는 텍스트를 읽어 오는중에 #,$,&,*"" 마지막에 붙어 있으면 자름 사용예) Dynex 장비 인터페이스중 결과 읽어오기 /
	 * 자료 백업하기
	 */

	public static String cut_Special_Character(String[] str) {
		final String src = str[0].toString();
		String result = "";
		if (src.length() == 0)
			return null;
		else
			for (int i = 1; i <= src.length(); i++)
				if (src.substring(i - 1, i).equals("")
						|| src.substring(i - 1, i).equals("*")
						|| src.substring(i - 1, i).equals("#")) {
					result = src.substring(0, i - 1);
					break;
				}
		return result.length() == 0 ? src : result;
	}

	// 접수번호같은 경우는 앞의 0들을 모두 잘라서 비교하도록...
	public static String cutZero(String source) {
		final int len = source.length();

		String s = source;

		for (int i = 0; i < len; i++) {
			if (s.charAt(0) == '0') {
				s = s.substring(1);
			} else
				break;
		}

		return s;
	}

	// 접수번호같은 경우는 앞의 0들을 모두 잘라서 비교하도록...
	public static String cutJun(String source) {
		final int len = source.length();
		String s = source;
		if (len < 2) {
			return s;
		}

		s = s.substring(0, 1);
		for (int i = 1; i < 7; i++) {

			s += "*";

		}

		return s;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2005-02-23 오후 8:18:16)
	 * 
	 * @return java.math.BigDecimal
	 */
	public static DecimalFormat df() {
		return new java.text.DecimalFormat("########.####");
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-02-10 오후 6:59:52)
	 * 
	 * @return java.lang.String
	 * @param en
	 *            java.lang.String
	 */
	public static String En(String en) {
		try {
			return new String(en.getBytes("KSC5601"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-02-14 오후 3:16:12)
	 * 
	 * @return java.lang.String[]
	 * @param str
	 *            java.lang.String
	 * @param cnt
	 *            int
	 */
	public static String[] getArrayData_CheckLast(String str, int cnt) {
		if (str == null || cnt < 0) {
			return null;
		}
		int count = 0;
		int len = 0;
		String[] src = Common.getArrayTypeData_CheckLast(str, cnt);
		String[] titel = null;
		String[] result = null;
		java.util.StringTokenizer tok = null;
		for (int i = 0; i < cnt; i++) {
			if (src[i].toString().trim().startsWith("<")) {
				count++;
				if (count == 1) {
					result[count] = src[i].trim().substring(1,
							src[i].length() - 1);
				} else {
					result[count] = src[i].trim();
				}
			} else {
				if (src[i].toString().trim().startsWith("6") && count == 1) {
					tok = new java.util.StringTokenizer(src[i], "\t\n\r");
					while (tok.hasMoreTokens()) {
						titel[len] = tok.nextToken().trim();
						len++;
					}
				} else {

				}
				count++;
			}
		}
		return src;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-02-09 오전 10:18:59)
	 * 
	 * @return java.lang.String
	 */
	public static String getDateCut(String day) {
		if (day == null || day.startsWith("0")) {
			return "";
		}
		String return_val = "";
		try {
			return_val = day.substring(2, 4) + ".";
			return_val += (day.substring(4, 6) + "." + day.substring(6, 8));
		} catch (Exception e) {
			return "";
		}
		return return_val;
	}

	public static String getNameCut(String name) {
		if (name == null) {
			return "";
		}
		String return_val = null;
		if (name.length() > 5) {
			return_val = name.substring(0, 5);
		} else {
			return_val = name;
		}
		return return_val;
	}

	public static String getNameCutEng(String name) {
		if (name == null) {
			return "";
		}
		String return_val = null;
		if (name.length() > 13) {
			return_val = name.substring(0, 12) + "~";
		} else {
			return_val = name;
		}
		return return_val;
	}

	public static String getReferCut(String str) {
		if (str == null) {
			return "";
		}
		StringTokenizer token = new StringTokenizer(str, " ");
		StringBuffer b = new StringBuffer("");
		int cnt = 0;
		while (token.hasMoreTokens()) {
			if (cnt < 3) {
				b.append(" " + token.nextToken());
				cnt++;
			} else
				break;
		}
		return b.toString().trim();
	}

	public static String Ko(String ko) {
		if (ko == null)
			return "";
		try {
			return new String(ko.getBytes("ISO-8859-1"), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			return "";
			/*
			 * new
			 * String(request.getParameter("chart").getBytes("8859_1"),"KSC5601")
			 */
		}
	}

	public static String EUC_KR(String ko) {
		if (ko == null)
			return "";
		try {
			return new String(ko.getBytes("KSC5601"), "EUC_KR");
		} catch (UnsupportedEncodingException e) {
			return "";
			/*
			 * new
			 * String(request.getParameter("chart").getBytes("8859_1"),"KSC5601")
			 */
		}
	}

	public final static String trimRight(String src) {
		int index = src.length() - 1;
		for (; index > -1; index--)
			if (src.charAt(index) != ' ')
				break;
		if (index < 0)
			return "";
		return src.substring(0, index + 1);

		/*
		 * public final static String trimRight(String src) { int index =
		 * src.length() - 1; for (; index > -1; index--) if (src.charAt(index) != ' ')
		 * break; if (index < 0) return ""; return src.substring(0, index + 1); }
		 * 
		 */
	}

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
		int endday = getLastDayOfMon(year_str);
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
			flag = isCorrect(yyyy, mm, dd);
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

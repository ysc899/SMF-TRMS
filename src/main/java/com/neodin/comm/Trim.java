package com.neodin.comm;

import java.util.Vector;

/**
 * 유형 설명을 삽입하십시오. 작성 날짜: (2003-10-02 오후 3:30:47)
 * 
 * @author: 조남식
 */
public final class Trim {
	final static String blank = " ";

	final static String line = "▒";

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2003-10-10 오후 5:50:58)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public final static String chgLine(String data) {
		if (data.equals(""))
			return null;
		Vector result = new Vector();
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, "|");
		while (str.hasMoreTokens()) {

			result.addElement(str.nextToken("|").trim());
		}

		StringBuffer src = new StringBuffer();
		for (int i = 0; i < result.size(); i++) {

			if (i != 0 && i % 2 == 0)
				src.append("\n" + result.elementAt(i).toString().trim());
			else
				src.append(result.elementAt(i).toString().trim()); // 0 ,1
		}
		return src.toString();

	}

	// 접수번호같은 경우는 앞의 0들을 모두 잘라서 비교하도록...
	public static String cutDateZero(String source) {
		int len = source.length();

		String s = source;

		for (int i = 0; i < len; i++) {
			if (s.charAt(0) == '0') {
				s = s.substring(1);
			} else
				break;
		}
		if (s.length() == 1)
			s = blank + s;
		return s;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2003-10-17 오후 2:30:45)
	 * 
	 * @return java.lang.String
	 * @param param
	 *            java.lang.String
	 */
	public static String cutJumin(String param) {
		if (param.length() < 8)
			return param;
		String str = param.substring(0, 6) + "-" + param.substring(7, 8)
				+ "******";

		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 Ver 1.22 작성 날짜:
	 * (2003-10-20 오후 3:59:15)
	 * 
	 * @return java.lang.String
	 */
	public static String cutLen(int data) {
		String src = "";
		if (data == 0)
			return "";
		java.util.StringTokenizer str = new java.util.StringTokenizer(
				data + "", ".");
		while (str.hasMoreTokens()) {

			src = str.nextToken();
			break;
		}
		if (src.equals(""))
			src = data + "";
		return src;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 Ver 1.22 작성 날짜:
	 * (2003-10-20 오후 3:59:15)
	 * 
	 * @return java.lang.String
	 */
	public static String cutLen(String data) {
		String src = "";
		if (data.length() == 0)
			return "";
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, ".");
		while (str.hasMoreTokens()) {

			src = str.nextToken();
			break;
		}

		return src;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2003-10-17 오후 2:16:27)
	 * 
	 * @return java.lang.String
	 * @param data
	 *            java.lang.String
	 */
	public static String cutWeek(String data) {
		String str = "";
		if (data.length() == 0)
			return "";
		if (data.length() == 3)
			str = data.substring(0, 2) + " 주" + data.substring(2, 3) + " 일";
		else if (data.length() == 2)
			return data + " 주";
		else {
			if (data.endsWith("0"))
				return data + " 주";
			else {
				try {
					str = data.substring(0, 1) + " 주" + data.substring(1, 2)
							+ " 일";
				} catch (Exception e) {
					str = data.substring(0, 1) + " 주";
				}

			}
		}
		return str;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2003-10-17 오후 1:32:23)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public static String cutWeight(String data) {
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, ".");
		StringBuffer src = new StringBuffer();
		int cnt = 0;
		String zero = "";
		while (str.hasMoreTokens()) {
			if (cnt == 0) {
				src.append(str.nextToken() + '.');
			} else {

				zero = str.nextToken();

				if (zero.length() == 2) {
					zero = zero.endsWith("0") ? zero.substring(0, 1) : zero;
				}
				if (zero.length() == 1) {
					zero = zero.endsWith("0") ? "" : zero;
				}
				if (zero.length() == 0) {
					return src.deleteCharAt(src.length() - 1).toString();
				}
			}
			src.append(zero);
			cnt++;

		}

		return src.toString();
	}

	// 접수번호같은 경우는 앞의 0들을 모두 잘라서 비교하도록...
	public static String cutZero(String source) {
		int len = source.length();

		String s = source;

		for (int i = 0; i < len; i++) {
			if (s.charAt(0) == '0') {
				s = s.substring(1);
			} else
				break;
		}

		len = s.trim().length();

		if (len < 5) {

			int i = 5 - len;

			for (int k = 0; k < i; k++) {

				s = blank + s;

			}
		}
		return s;
	}

	// 2002-02-02 날짜를 바꿔줌
	public static String getDateFormat(String date) {
		String dateFormat = "";

		if (date.length() == 8)
			dateFormat = date.substring(0, 4) + " 년 "
					+ cutDateZero(date.substring(4, 6)) + " 월 "
					+ cutDateZero(date.substring(6, 8)) + " 일";

		return dateFormat.trim();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 Ver 1.22 작성 날짜:
	 * (2003-10-20 오후 3:35:24)
	 * 
	 * @return java.lang.String
	 * @param row
	 *            java.lang.String
	 * @param result
	 *            java.lang.String
	 */
	public static String getLineSize(int row, int result) {
		StringBuffer str = new StringBuffer();
		int count = 0;
		int len = 0;

		switch (row) {
		case 0:
		case 1:

		{
			if (result == 99999) {
				count = 1;
				break;
			} else if (result > 9999) {
				len = Integer.parseInt(cutLen(result / 10000));
				count = 9 - len;
				break;
			} else if (result > 999) {
				len = Integer.parseInt(cutLen(result / 1000));
				count = (9 - len) + 9;
				break;
			} else if (result >= 270) {
				if (result > 927) {
					count = 18;
				} else if (result > 845) {
					count = 19;
				} else if (result > 781) {
					count = 20;
				} else if (result > 708) {
					count = 21;
				} else if (result > 635) {
					count = 22;
				} else if (result > 562) {
					count = 23;
				} else if (result > 489) {
					count = 24;
				} else if (result > 416) {
					count = 25;
				} else if (result > 271) {
					count = 26;
				} else if (result == 270) {
					count = 27;
				}

			} else if ((100 <= result) && (result < 270)) {
				if (result >= 253) {
					count = 27;
				} else if (result >= 236) {
					count = 28;
				} else if (result >= 219) {
					count = 29;
				} else if (result >= 202) {
					count = 30;
				} else if (result >= 185) {
					count = 31;
				} else if (result >= 151) {
					count = 32;
				} else if (result >= 134) {
					count = 33;
				} else if (result >= 117) {
					count = 34;
				} else if (result > 100) {
					count = 35;
				} else if (result == 100) {
					count = 36;
				}

				break;
			} else if ((10 <= result) && (result < 100)) {
				len = Integer.parseInt(cutLen(result / 9));
				count = (8 - len) + 37;
				break;
			} else if (result < 10) {
				count = 45;
				break;
			}
			break;
		}

		case 2: {

			// oklee //if (result < 100) {
			count = Integer.parseInt(cutLen(result / 9));
			break;
			// } else
			// if (result < 200) {
			// len = Integer.parseInt(cutLen(result / 20));
			// count = 10 + len;
			// break;
			// } else
			// if (result == 250) {
			// count = 25;
			// break;
			// } else
			// if (result < 300) {
			// len = Integer.parseInt(cutLen(result / 30));
			// count = 30 + len;
			// break;
			// } else
			// if (result < 400) {
			// len = Integer.parseInt(cutLen(result / 40));
			// count = 40 + len;
			// break;
			// } else
			// if (result >= 499) {
			// count = 50;
			// break;
			// }

		}
		}
		if (count == 0)
			count = 1;
		for (int i = 0; i < count; i++) {
			str.append(line);
		}

		return str.toString();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 Ver 1.22 작성 날짜:
	 * (2003-10-20 오후 3:35:24)
	 * 
	 * @return java.lang.String
	 * @param row
	 *            java.lang.String
	 * @param result
	 *            java.lang.String
	 */
	public static String getLineSize(int row, int result, String src) {
		StringBuffer str = new StringBuffer();
		int count = 0;
		switch (row) {
		case 0:
		case 1:
		case 2:
			count = Integer.parseInt(cutLen(result / 9));
		}

		if (count == 0)
			count = 1;
		for (int i = 0; i < count; i++) {
			str.append(line);
		}
		str.append(" [ " + src.trim() + " ]");
		return str.toString();
	}

	public static final String trimLeft(String src) {
		final char[] array = src.toCharArray();

		for (int i = 0; i < array.length; i++) {
			if (array[i] == ' ')
				continue;
			return new String(array, i, array.length - i);
		}

		return "";
	}

	public static final String trimRight(String src) {
		final char[] array = src.toCharArray();

		for (int i = array.length - 1; i > -1; i--) {
			if (array[i] == ' ')
				continue;
			return new String(array, 0, i + 1);
		}

		return "";
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2003-10-10 오후 5:50:58)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public final static String chgLine(String data, String len) {
		if (data.equals(""))
			return null;
		Vector result = new Vector();
		java.util.StringTokenizer str = new java.util.StringTokenizer(data, "|");
		while (str.hasMoreTokens()) {

			result.addElement(str.nextToken("|").trim());
		}

		StringBuffer src = new StringBuffer();
		for (int i = 0; i < result.size(); i++) {

			if (i != 0 && i % 2 == 0)
				src.append("\n" + result.elementAt(i).toString().trim());
			else
				src.append(result.elementAt(i).toString().trim()); // 0 ,1
		}
		return src.toString();

	}

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 Ver 1.22 작성 날짜:
	 * (2003-10-20 오후 3:35:24)
	 * 
	 * @return java.lang.String
	 * @param row
	 *            java.lang.String
	 * @param result
	 *            java.lang.String
	 */
	public static int getLineSizeNew(int row, int result) {

		double len = 0;
		if (result == 0)
			return 0;

		switch (row) {
		case 0: // 다운증후군 위험도
		{
			if (result == 99999) {
				len = 1;
				break;
			} else if (result == 10000) {
				len = 82;
				break;
			} else if (result == 1000) {
				len = 164;
				break;
			} else if (result == 270) {
				len = 246;
				break;
			} else if (result == 100) {
				len = 323;
				break;
			} else if (result <= 10) {
				len = 407;
				break;
			} else if (result > 10000) {
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) {
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 270) {
				len = 82 - ((result - 270) / ((double) 730 / 82)) + 164;
				break;
			} else if (result > 100) {
				len = 77 - ((result - 100) / ((double) 170 / 77)) + 246;
				break;
			} else if (result > 10) {
				len = 84 - ((result - 10) / ((double) 90 / 84)) + 323;
				break;
			}
			break;
		}
		case 1: // 에드워드 증후군 위험도
		{
			if (result == 99999) { // 0
				len = 1;
				break;
			} else if (result == 10000) { // 1
				len = 82;
				break;
			} else if (result == 1000) { // 2
				len = 164;
				break;
			} else if (result == 200) { // 3
				len = 267;
				break;
			} else if (result == 100) { // 4
				len = 323;
				break;
			} else if (result <= 10) { // 5
				len = 407;
				break;
			} else if (result > 10000) { // 1
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) { // 2
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 200) { // 3
				len = 103 - ((result - 200) / ((double) 800 / 103)) + 164;
				break;
			} else if (result > 100) { // 4
				len = 56 - ((result - 100) / ((double) 100 / 56)) + 267;
				// len= 56 - (double) ((result - 100) / ((double) 100 / 56)) +
				// 246;
				break;
			} else if (result > 10) { // 5
				len = 84 - ((result - 10) / ((double) 90 / 84)) + 323;
				break;
			}
			break;
		}
		case 2: // 신경관결손 위험도
		{

			if (result == 0) {
				len = 1;
				break;
			} else if (result == 100) {
				len = 82;
				break;
			} else if (result == 200) {
				len = 164;
				break;
			} else if (result == 300) {
				len = 246;
				break;
			} else if (result == 400) {
				len = 323;
				break;
			} else if (result >= 500) {
				len = 407;
				break;
			} else if (result > 400) {
				len = ((result - 400) * (double) 84 / 100) + 323;
				break;
			} else if (result > 300) {
				len = ((result - 300) * (double) 77 / 100) + 246;
				break;
			} else if (result > 200) {
				len = ((result - 200) * (double) 82 / 100) + 164;
				break;
			} else if (result > 100) {
				len = ((result - 100) * (double) 82 / 100) + 82;
				break;
			} else if (result > 1) {
				len = (result * (double) 82 / 100);
				break;
			}
			break;
		}
		}

		if ((int) len == 0)
			len = 1;
		return (int) len;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 자바 버전: Visual Age for Java 4.00 Ver 1.22 작성 날짜:
	 * (2003-10-20 오후 3:35:24)
	 * 
	 * @return java.lang.String
	 * @param row
	 *            java.lang.String
	 * @param result
	 *            java.lang.String
	 */
	public static int getLineSizeNewTwo(int row, int result) {

		double len = 0;
		if (result == 0)
			return 0;

		switch (row) {
		case 0: // ! First double marker 90027 다운증후군위험도
		{
			if (result == 99999) {
				len = 1;
				break;
			} else if (result == 10000) {
				len = 82;
				break;
			} else if (result == 1000) {
				len = 164;
				break;
			} else if (result == 250) {
				len = 246;
				break;
			} else if (result == 100) {
				len = 323;
				break;
			} else if (result <= 10) {
				len = 407;
				break;
			} else if (result > 10000) {
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) {
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 250) {
				len = 82 - ((result - 250) / ((double) 750 / 82)) + 164;
				break;
			} else if (result > 100) {
				len = 77 - ((result - 100) / ((double) 170 / 77)) + 246;
				break;
			} else if (result > 10) {
				len = 84 - ((result - 10) / ((double) 90 / 84)) + 323;
				break;
			}
			break;
		}
		case 1: {
			if (result == 99999) { // Sequential test 1차 99935 에드워드 //! First
									// double marker90027 에드워드
				len = 1;
				break;
			} else if (result == 10000) {
				len = 82;
				break;
			} else if (result == 1000) {
				len = 164;
				break;
			} else if (result == 300) {
				len = 267;
				break;
			} else if (result == 100) {
				len = 323;
				break;
			} else if (result <= 10) {
				len = 407;
				break;
			} else if (result > 10000) {
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) {
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 300) {
				len = 103 - ((result - 300) / ((double) 800 / 103)) + 164;
				break;
			} else if (result > 100) {
				len = 56 - ((result - 100) / ((double) 100 / 56)) + 267;
				break;
			} else if (result > 10) {
				len = 84 - ((result - 10) / ((double) 90 / 84)) + 323;
				break;
			}
			break;
		}
		case 2: // Integrated test 2차 99934 다운증후군
		{
			if (result == 99999) {
				len = 1;
				break;
			} else if (result == 10000) {
				len = 82;
				break;
			} else if (result == 1000) {
				len = 164;
				break;
			} else if (result == 450) {
				len = 246;
				break;
			} else if (result == 100) {
				len = 323;
				break;
			} else if (result <= 10) {
				len = 407;
				break;
			} else if (result > 10000) {
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) {
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 495) {
				len = 82 - ((result - 495) / ((double) 505 / 82)) + 164;
				break;
			} else if (result > 100) {
				len = 77 - ((result - 100) / ((double) 395 / 77)) + 246;
				break;
			} else if (result > 10) {
				len = 84 - ((result - 10) / ((double) 90 / 84)) + 323;
				break;
			}
			break;

		}
		case 3: // Sequential test 2차 다운 99936
		{
			if (result == 99999) {
				len = 1;
				break;
			} else if (result == 10000) {
				len = 82;
				break;
			} else if (result == 1000) {
				len = 164;
				break;
			} else if (result == 450) {
				len = 246;
				break;
			} else if (result == 100) {
				len = 323;
				break;
			} else if (result <= 10) {
				len = 407;
				break;
			} else if (result > 10000) {
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) {
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 450) {
				len = 82 - ((result - 450) / ((double) 730 / 82)) + 164;
				break;
			} else if (result > 100) {
				len = 77 - ((result - 100) / ((double) 350 / 77)) + 246;
				break;
			} else if (result > 10) {
				len = 84 - ((result - 10) / ((double) 90 / 84)) + 323;
				break;
			}
			break;
		}
		case 4: {
			if (result == 99999) { // Sequential test 1차 99935 다운증후군

				len = 1;
				break;
			} else if (result == 10000) {
				len = 82;
				break;
			} else if (result == 1000) {
				len = 164;
				break;
			} else if (result == 100) {
				len = 246;
				break;
			} else if (result < 60 && result > 41) {
				len = 226;
				break;
			} else if (result == 41) {
				len = 323;
				break;
			} else if (result <= 10) {
				len = 407;
				break;
			} else if (result > 10000) {
				len = 82 - ((result - 10000) / ((double) 90000 / 82));
				break;
			} else if (result > 1000) {
				len = 82 - ((result - 1000) / ((double) 9000 / 82)) + 82;
				break;
			} else if (result > 100) {
				len = 82 - ((result - 100) / ((double) 900 / 82)) + 164;
				break;
			} else if (result > 41) {
				len = 77 - ((result - 41) / ((double) 49 / 77)) + 190;
				break;
			} else if (result > 10) {
				len = 84 - ((result - 10) / ((double) 30 / 84)) + 323;
				break;
			}
			break;

		}
		}

		if ((int) len == 0)
			len = 1;
		return (int) len;
	}
}

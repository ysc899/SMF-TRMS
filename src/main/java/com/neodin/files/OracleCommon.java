package com.neodin.files;

import com.neodin.comm.Common;

/**
 * 유형 설명을 삽입하십시오. 작성 날짜: (2008-09-09 오후 5:17:55)
 * 
 * @작성자: Administrator
 */
public class OracleCommon {
	public static String appendBlanks(String src, int length) {
		String dest = src.trim().substring(0);
		try {
			int index = src.trim().getBytes().length;
			if (index < length) {
				for (int i = 0; i < length - index; i++) {
					dest += " ";
				}
			} else
				dest = src.substring(0, length);
			return dest;
		} catch (Exception e) {
			dest = src;
		}
		return dest;
	}

	public static String appendBlanks2(String src, int length) {
		String dest = src.substring(0);
		try {
			int index = src.getBytes().length;
			if (index < length) {
				for (int i = 0; i < length - index; i++) {
					dest += " ";
				}
			} else
				dest = src.substring(0, length);
			return dest;
		} catch (Exception e) {
			dest = src;
		}
		return dest;
	}

	// static FontMetrics fm12 = FontMetrics(new Font("Dialog", 0, 12));
	// static Font f12 = new Font("Dialog", 0, 12);
	/**
	 * 이 메소드는 VisualAge에서 작성되었습니다.
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public static int cntDBCSplusSBCS(String str) {
		int cnt = 0;
//		StringBuffer sb = new StringBuffer();
		char[] c = str.toCharArray();
//		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			char convert = c[i];
			if (new String(convert + "").getBytes().length == 2) {
				// cnt =+2;
				cnt++;
				cnt++;
			}
		}
		return cnt;
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
	public static java.util.Vector divString(String src) {
		java.util.Vector vt = new java.util.Vector();
//		String result = "";
		String dest = toDB(src.substring(0));
		int index = dest.getBytes().length;
		int length = 190;
		int total = index / length + (index % length > 0 ? 1 : 0);
		int sindex = 0;
		int eindex = 0;
		try {
			for (int i = 0; i < total; i++) {
				sindex = length * i;
				eindex = length * (i + 1);
				// System.out.println(sindex + " : " + eindex);
				vt.addElement(dest.substring(sindex, eindex));
			}
		} catch (Exception e) {
			try {
				vt.addElement(dest.substring(sindex));
			} catch (Exception exx) {
			}
		}
		return vt;
	}

	// DB에 저장하기전에 한글인코딩을 바꿔주는 함수//

	public static String fromDB(String str) {
		String temp = "";
		try {
			temp = new String(str.getBytes("8859_1"), "EUC-KR");
		} catch (Exception e) {
		}
		return temp;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-09-25 오후 5:25:24)
	 * 
	 * @return int
	 * @param str
	 *            java.lang.String
	 */
	public static int getTextSize(String str) {
		try {
			return str.getBytes().length;
		} catch (Exception e) {
			return 250;
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2008-09-25 오후 5:25:24)
	 * 
	 * @return int
	 * @param str
	 *            java.lang.String
	 */
	public static int getTextSize(java.util.Vector str) {
		int cnt = 0;
		try {
			for (int i = 0; i < str.size(); i++) {
				cnt = +str.elementAt(i).toString().getBytes().length;
			}
		} catch (Exception ee) {
		}
		return cnt;
	}

	public static String toDB(String str) {
		String temp = "";
		try {
			temp = new String(str.getBytes("EUC-KR"), "8859_1");
		} catch (Exception e) {
		}
		return temp;
	}

	// !
	public static String writeText(String src, int length) {
		String dest = Common.trimRight(src.trim().substring(0));
		try {
			int index = src.trim().getBytes().length;
			if (index < length) {
				for (int i = 0; i < length - src.length(); i++) {
					dest += " ";
				}
			} else
				dest = src.substring(0, length);
			return dest;
		} catch (Exception rr) {
			return dest;
		}
	}
}

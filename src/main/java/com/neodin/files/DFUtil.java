package com.neodin.files;

import com.neodin.comm.Common;

public class DFUtil {

	// substring (0,5)
	public static String COMPAREDATA19_1 = 
			"00095|31010|61029|61026|61028|31014|11052|72185|21638|21653|81399|21274|41121|41338|31060|21123|71311|31016|80014|41381|71005|72020";
	public static String COMPAREDATA19_2=
			"7224501|7218201|7218901|7224201|0040501|7129700|71139|52001|52002|52003|"
			+ "52004|52005|52006|52007|52008|52009|68021|68014|68015|68016|68017|68018|31100|31101|31102|31103|"
			+ "31104|31105|31106|31107|31108|31109|31110|31111|31112|31113|31114|31115|31116|31117|"
			+ "31118|31119|31120|31121|31122|31123|31124|31125|31126|31127|31128|72178|61069|52036|52031|52032|52036|52034|"
			+ "3100100|0548300|0556700|2500000";
	
	public static boolean isContinue(final String hosCode,final String inspectCode, final String result) {
		switch(hosCode.trim()) {
		case "17788" : // 
			return  "추후|보고예정|검체".contains(result.trim()) ? true : false;
		case "29171" : // 
			return  "추후송부".contains(result.trim() ) 
					|| (inspectCode.trim().length() == 7 && !inspectCode.trim().substring(0, 5).equals("21068") && inspectCode.trim().substring(5, 7).equals("00")) ? true : false;
		}
		return false;
	}
	
	public static boolean isContinue(final String hosCode, final String inspectCode) {
		try {
			switch(hosCode.trim()) {
			case "22721" :
				return  "7125200|7125203|7125900|7224100|7224101".contains(inspectCode.trim()) ? true : false;
		
			case "13952" :
				return  "4102600|4102602|4102603".contains(inspectCode.trim()) ? true : false;
//			case "29112" :
			case "26260" : //고성강병원
				return  "0009500|8137102".contains(inspectCode.trim()) ? true : false;
			case "10650" ://안종훈내과,예스병원(안산)
				return  "0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;
			case "29034" :
			case "28312" :
				return  "2106801|2106802|2106803|2106804".contains(inspectCode.trim()) ? true : false;
			case "27993" : //참조은요양병원
				return  "1100202".contains(inspectCode.trim()) ? true : false;
			case "23535" : // 삼성조은내과 건너뛰기
				return  "7125200|7125201|7125203|7125900|7125902".contains(inspectCode.trim()) ? true : false;
			case "15298" : // 서울하내과
				return  "0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;
			case "27021" : //일산21세기병원
				return  "1130100".contains(inspectCode.trim()) ? true : false;
			case "29005" : // 520**(조직)  빼기
				return  "520".contains(inspectCode.trim().substring(0, 3)) ? true : false;
			case "27282" :
			case "27693" : // 
				return  "7125100|0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;
			case "20231" :
				return inspectCode.trim().length() == 7 
					&& !"0548150|0548356".contains(inspectCode.trim()) 
					&&  ("1110100|1110102|1110103".contains(inspectCode.trim()) 
								|| "05481|05483|05567".contains(inspectCode.trim().substring(0, 5)) ) ? true : false;
			case "26719" : // 
				return  "7125200|7125201|7125203|0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;		
			case "25815" : // 
				return  "7125200|7125201|7125202|71197|7129700|7129701|7129702|7129703|7129704|7129705".contains(inspectCode.trim()) ? true : false;	
			case "29154" : // 
				return  "31019|31077|0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;	
			case "26577" : // 
				return  "1100200|0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;	
			case "28833" : // 
			case "29504" :
				return  "0009500|0009502|0009503".contains(inspectCode.trim()) ? true : false;	
			case "25493" : // 
			
				return  "05481|05483|21254|21257|05567|41377|41392|21259|21273|21274|05562|05564|05565".contains(inspectCode.trim().substring(0, 5)) ||
						"0009500|0009502|0009503|7218500|7218502|2163801|2163802".contains(inspectCode.trim()) ? true : false;	
			case "28280" : // 
			case "29463" : return inspectCode.trim().substring(0, 5).equals("00095")&& !inspectCode.equals("0009501") ? true : false;
			case "25560" : return inspectCode.trim().substring(0, 5).equals("71252")&& !inspectCode.equals("7125202") ? true : false;
			case "20796" : return inspectCode.trim().substring(0, 5).equals("00405")&& !  inspectCode.equals("0040501") ? true : false;
			case "16702" : return inspectCode.trim().substring(0, 5).equals("05567")&& !  inspectCode.equals("0556700") ? true : false;
			}
			
			
		
		} catch(Exception e) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String inspectCode = null; 
//		try {
//			System.out.println("7125200|7125203|7125900|7224100|7224101".contains(inspectCode.trim()) );
//		} catch (Exception e) {
//			
//		}
//		DFUtil util = new DFUtil();
//		String hosCode ="20231";
//		inspectCode = "05567";
////		System.out.println(util.continueHos20231(hosCode, inspectCode));
//		System.out.println(DFUtil.isContinue(hosCode, inspectCode));
		
		String data[] = new String[] {"acddedf\r\nddddd\r\ndkdljdkjfkdjlkfdl\r\n"};
 		
		data[0] = data[0].split("\r\n")[0]; // 참고치 첫번째줄만
	}

	public static boolean isSpecialTest(final String inspectCode, final int type) {
		switch(type) {
		case 0 : return inspectCode .trim().substring(0, 5).equals("05028")&&!inspectCode.trim().equals("0502802")
			|| (inspectCode.trim().substring(0, 5).equals("05029")&&!inspectCode.trim().equals("0502902"))
			|| (inspectCode.trim().substring(0, 5).equals("05011")&&!inspectCode.trim().equals("0501102"))
			|| (inspectCode.trim().substring(0, 5).equals("05025")&&!inspectCode.trim().equals("0502502"))
			|| (inspectCode.trim().substring(0, 5).equals("05008")&&!inspectCode.trim().equals("0500802"))
			|| (inspectCode.trim().substring(0, 5).equals("05010")&&!inspectCode.trim().equals("0501002"))
			|| (inspectCode.trim().substring(0, 5).equals("05021")&&!inspectCode.trim().equals("0502102"))
			|| (inspectCode.trim().substring(0, 5).equals("05022")&&!inspectCode.trim().equals("0502202"))
			|| (inspectCode.trim().substring(0, 5).equals("05030")&&!inspectCode.trim().equals("0503002"))
			|| (inspectCode.trim().substring(0, 5).equals("05031")&&!inspectCode.trim().equals("0503102")) ? true : false;
		case 1 : return (inspectCode.trim().substring(0, 5).equals("05028")&&!inspectCode.trim().equals("0502802"))
				|| (inspectCode.trim().substring(0, 5).equals("05029")&&!inspectCode.trim().equals("0502903")
						&&!inspectCode.trim().equals("0502902"))
				|| (inspectCode.trim().substring(0, 5).equals("05011")&&!inspectCode.trim().equals("0501102"))
				|| (inspectCode.trim().substring(0, 5).equals("05025")&&!inspectCode.trim().equals("0502502"))
				|| (inspectCode.trim().substring(0, 5).equals("05008")&&!inspectCode.trim().equals("0500802"))
				|| (inspectCode.trim().substring(0, 5).equals("05010")&&!inspectCode.trim().equals("0501002"))
				|| (inspectCode.trim().substring(0, 5).equals("05021")&&!inspectCode.trim().equals("0502102"))
				|| (inspectCode.trim().substring(0, 5).equals("05022")&&!inspectCode.trim().equals("0502202"))
				|| (inspectCode.trim().substring(0, 5).equals("05030")&&!inspectCode.trim().equals("0503002"))
				|| (inspectCode.trim().substring(0, 5).equals("05031")&&!inspectCode.trim().equals("0503102")); 
		}
		
		return false;
	}

	public static boolean isEnableData_19(final String hosCode, final String inspectCode) {
		
		 return ("31393|31395".contains(hosCode.trim()) &&(
				 COMPAREDATA19_1.contains(inspectCode.trim().substring(0,5)) || COMPAREDATA19_2.contains(inspectCode.trim()))  ? true : false);  
				  
		 
		 
	}
	
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-08 오후 3:07:55)
	 * 
	 * @return java.lang.String
	 */
	//Ace병원등 일부 배열 3개 받는 병원
	//201709011 배열값 3개 받을수 있도록 수정 
	public static String[] cutHrcvDateNumberAce(final String str) {

		// !
		// String src_[] = new String[] { "", "", ""}; 20170911이전
		// !
				String src_[] = new String[] { "", "", ""};//20170911 이후
		// !
		if (str == null || "".equals(str))
			return src_;
		

		// !
		src_ = Common.getDataCut(str, "^");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };
		// !
		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
			
		} catch (Exception e) {
			src_[0] = "";
		}
		try {
			src_[1] = src_[1].replace('D', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		//20170911 이후 추가
		try {
			src_[2] = src_[2].replace('^', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[2] = "";
		}
		return src_;
	}

	
	public static String[] cutHrcvDateNumber(final String str) {

		// !
		 String src_[] = new String[] { "", ""};
		// !
		
		if (str == null || "".equals(str))
			return src_;
		

		// !
		src_ = Common.getDataCut(str, "^");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };
		// !
		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
			
		} catch (Exception e) {
			src_[0] = "";
		}
		try {
			src_[1] = src_[1].replace('D', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		
		return src_;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-04-08 오후 3:07:55)
	 * 
	 * @return java.lang.String
	 */
	public static String[] cutHrcvDateNumber2(final String str) {
		String src_[] = new String[] { "", "" };
		if (str == null || src_.equals(""))
			return src_;

		src_ = Common.getDataCut(str, "D");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

		try {
			src_[0] = src_[0].replace('N', ' ').replace(':', ' ').trim();
			src_[0] = src_[0].substring(0, src_[0].length() - 1);
		} catch (Exception e) {
			src_[0] = "";
		}

		try {
			src_[1] = src_[1].replace(':', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		return src_;
	}
	
	/**
	 * N = > 구문을 강제로 ' ' 에서 replaceAll "N:" 을 " " 작성 날짜: (2016-08-18 오후 3:07:55)
	 * 
	 * @return java.lang.String
	 */
	public static String[] cutHrcvDateNumber3(final String str) {

		// !
		String src_[] = new String[] { "", "" };

		// !
		if (str == null || src_.equals(""))
			return src_;

		// !
		src_ = Common.getDataCut(str, "^");
		if (src_ == null || src_.length == 0)
			return new String[] { "", "" };

		// !
		try {
			src_[0] = src_[0].replaceAll("N:"," ").trim();
		} catch (Exception e) {
			src_[0] = "";
		}
		try {
			src_[1] = src_[1].replace('D', ' ').replace(':', ' ').trim();
		} catch (Exception e) {
			src_[1] = "";
		}
		return src_;
	}
	
	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2009-10-25 오후 6:54:21)
	 * 
	 * @return java.lang.String
	 */
	public static String[] getUintCut(final String unt___) {
		String str[] = new String[] { "", "", "" };
		str = Common.getDataCut(unt___, ",");
		if (str.length == 2) {
			str = new String[] { str[0], str[1].replace(',', ' ').trim(), "" };
		}
		return str;
	}

				
}
	
//	private boolean continueHos20796(String[] hosCode, String[] inspectCode, int i) {
//		return hosCode[i].trim().equals("20796") && inspectCode[i].trim().substring(0, 5).equals("00405")&& !  inspectCode[i].equals("0040501");
//	}
	
//	private boolean continueHos27693(String[] hosCode, String[] inspectCode,
//			int i) {
//		return (hosCode[i].trim().equals("27282")||hosCode[i].trim().equals("27693"))
//				&& (inspectCode[i].trim().equals("7125100")||inspectCode[i].trim().equals("0009500")||inspectCode[i].trim().equals("0009502")||inspectCode[i].trim().equals("0009503"));
//	}

//	private boolean continueHos20231(String hosCode, String inspectCode
//			) {
//		return inspectCode.trim().length() == 7 && ((hosCode.trim().equals("20231")) && !inspectCode.trim().equals("0548150") && !inspectCode.trim().equals("0548356") 
//				&& (inspectCode.trim().equals("1110100") || inspectCode.trim().equals("1110102") || inspectCode.trim().equals("1110103")
//				||(inspectCode.trim().substring(0, 5).equals("05481")
//						||(inspectCode.trim().substring(0, 5).equals("05483") || inspectCode.trim().substring(0, 5).equals("05567")))));
//	}

//	private boolean continueHos26719(String[] hosCode, String[] inspectCode,
//			int i) {
//		return hosCode[i].trim().equals("26719")
//				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")||
//						inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals("7125203"));
//	}

//	private boolean continueHos25815(String[] hosCode, String[] inspectCode,
//			int i) {
//		return hosCode[i].trim().equals("25815")
//				&& (inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals(
//						"7125202") || inspectCode[i].trim().equals("71197")|| inspectCode[i].trim().equals("7129700")|| inspectCode[i].trim().equals("7129701")
//						|| inspectCode[i].trim().equals("7129702")|| inspectCode[i].trim().equals("7129703")|| inspectCode[i].trim().equals("7129704")|| inspectCode[i].trim().equals("7129705"));
//	}

//	private boolean continueHos29154_1(String[] hosCode, String[] inspectCode,
//			int i) {
//		return (hosCode[i].trim().equals("29154")) && (inspectCode[i].trim().equals("31019")||inspectCode[i].trim().equals("31077"));
//	}
//
//	private boolean continueHos29154(String[] hosCode, String[] inspectCode,
//			int i) {
//		return (hosCode[i].trim().equals("29154") //|| hosCode[i].trim().equals("17210") 20190109  사용 안하는거래처 양태용  
//				)
//				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals(
//						"0009503"));
//	}



//	private boolean continueHos26577(String[] hosCode, String[] inspectCode,
//			int i) {
//		return (hosCode[i].trim().equals("26577"))
//				&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")
//						|| inspectCode[i].trim().equals("1100200"));
//	}


//	private boolean continueHos28833(String[] hosCode, String[] inspectCode,
//			int i) {
//		return hosCode[i].trim().equals("28833")
//				&& (inspectCode[i].trim().equals("0009500")
//						|| inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"));
//	}



//	private boolean continueHos29504(String[] hosCode, String[] inspectCode,
//			int i) {
//		return (hosCode[i].trim().equals("29504"))
//				&& (inspectCode[i].trim().equals("0009500")
//						|| inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"));
//	}

//	private boolean continueHos25493_2(String[] hosCode, String[] inspectCode,
//			int i) {
//		return hosCode[i].trim().equals("25493")
//				&& (inspectCode[i].trim().substring(0, 5).equals("05481") || inspectCode[i].trim().substring(0, 5).equals("05483") 
//						|| inspectCode[i].trim().substring(0, 5).equals("21254")
//						|| inspectCode[i].trim().substring(0, 5).equals("21257") || inspectCode[i].trim().substring(0, 5).equals("05567")
//						|| inspectCode[i].trim().substring(0, 5).equals("41377") || inspectCode[i].trim().substring(0, 5).equals("41392")
//						|| inspectCode[i].trim().substring(0, 5).equals("21259") || inspectCode[i].trim().substring(0, 5).equals("21273")
//						|| inspectCode[i].trim().substring(0, 5).equals("21274") || inspectCode[i].trim().substring(0, 5).equals("41377")
//						|| inspectCode[i].trim().substring(0, 5).equals("05562") || inspectCode[i].trim().substring(0, 5).equals("05564")
//						|| inspectCode[i].trim().substring(0, 5).equals("05565")
//						|| inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") 
//						|| inspectCode[i].trim().equals("0009503")|| inspectCode[i].trim().equals("7218500")|| inspectCode[i].trim().equals("7218502"));
//	}

//	private boolean continueHos25493(String[] hosCode, String[] inspectCode,
//			int i) {
//		return inspectCode[i].trim().length() == 7
//				&& hosCode[i].trim().equals("25493") &&( inspectCode[i].trim().equals("2163801") || inspectCode[i].trim().equals("2163802"));
//	}
//	

//	private boolean continueHos17788 (String[] hosCode, String[] result, int i) {
//		return (hosCode[i].trim().equals("17788")) //추후송부, 추후보고, 보고예정, 검체부족, 검체부적합등의 문구는 
//				&& (result[i].trim().indexOf("추후") > 0  || result[i].trim().indexOf("보고예정") > 0 || result[i].trim().indexOf("검체") > 0);
//	}
//	private boolean continueHos29171(String[] hosCode, String[] inspectCode,
//			String[] result, int i) {
//		return hosCode[i].trim().equals("29171")
//				&& (result[i].trim().indexOf("추후송부") > 0 
//						|| (inspectCode[i].trim().length() == 7 
//						&& !inspectCode[i].trim().substring(0, 5).equals("21068") && inspectCode[i].trim().substring(5, 7).equals("00")));
//	}

//	private boolean continueHos28280(String[] hosCode, String[] inspectCode,
//			int i) {
//		return (hosCode[i].trim().equals("28280")||hosCode[i].trim().equals("29463")//||hosCode[i].trim().equals("29516") 20190109  사용 안하는거래처 양태용  
//
//				) && 
//				 (inspectCode[i].trim().substring(0, 5).equals("00095")&& !inspectCode[i].equals("0009501"));
//	}




//	private boolean continueHos25560(String[] hosCode, String[] inspectCode,
//			int i) {
//		return hosCode[i].trim().equals("25560") && 
//			 //(inspectCode[i].trim().substring(0, 5).equals("71252")&& !inspectCode[i].equals("7125202"))||
//			 (inspectCode[i].trim().substring(0, 5).equals("71252")&& !inspectCode[i].equals("7125202"));
//	}


//	private boolean continueHos12657(String[] hosCode, String[] inspectCode,
//			int i) {
//		return inspectCode[i].trim().length() == 7	&& (hosCode[i].trim().equals("12657") && 
//				(inspectCode[i].trim().equals("7224101") || inspectCode[i].trim().equals("7125902")));
//		        
//	}
//	public boolean continueHos22721_1(String[] hosCode, String[] inspectCode,
//	int i) {
//return hosCode[i].trim().equals("22721")&&(inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125203")
//		||inspectCode[i].trim().equals("7125900") || inspectCode[i].trim().equals("7224100")	||inspectCode[i].trim().equals("7224101"));
//}
//
//public boolean continueHos13952(String[] hosCode, String[] inspectCode,
//	int i) {
//return hosCode[i].trim().equals("13952")&&(inspectCode[i].trim().equals("4102600") || inspectCode[i].trim().equals("4102602")||inspectCode[i].trim().equals("4102603"));
//}
//
//public boolean continueHos29112(String[] hosCode, String[] inspectCode,
//	int i) {
//return (//hosCode[i].trim().equals("29112")||  20190109  사용 안하는거래처 양태용  
//		hosCode[i].trim().equals("26260"))&&(inspectCode[i].trim().equals("0009500")||inspectCode[i].trim().equals("8137102"));
//}

//public boolean continueHos10650(String[] hosCode, String[] inspectCode,
//	int i) {
//return (hosCode[i].trim().equals("10650")//||hosCode[i].trim().equals("15428") 20190109  사용 안하는거래처 양태용  
//
//		)
//		&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim()
//				.equals("0009503"));
//}

//public boolean continueHos21505(String[] hosCode, String[] inspectCode,
//	int i) {
//return inspectCode[i].trim().length() == 7
//		&& ((//hosCode[i].trim().equals("21505")||  20190109  사용 안하는거래처 양태용  
//
//				hosCode[i].trim().equals("29034") //|| hosCode[i].trim().equals("")20190109  사용 안하는거래처 양태용  
//				|| hosCode[i].trim().equals("28312"))
//				&& (inspectCode[i].trim().equals("2106801") || inspectCode[i].trim().equals("2106802")
//				|| inspectCode[i].trim().equals("2106803")|| inspectCode[i].trim().equals("2106804")));
//}
//
//public boolean continueHos27993(String[] hosCode, String[] inspectCode,
//	int i) {
//return hosCode[i].trim().equals("27993")
//		&& (inspectCode[i].trim().equals("1100202"));
//}



//
//public boolean continueHos23535(String[] hosCode, String[] inspectCode,
//	int i) {
//return inspectCode[i].trim().length() == 7 && (hosCode[i].trim().equals("23535")
//		&& (inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals("7125203") 
//				|| inspectCode[i].trim().equals("7125900") || inspectCode[i].trim().equals("7125902")));
//}

//public boolean continueHos15298(String[] hosCode, String[] inspectCode,
//	int i) {
//return inspectCode[i].trim().length() == 7
//		&& (hosCode[i].trim().equals("15298") && (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i]
//				.trim().equals("0009503")));
//}
//if (hosCode[i].trim().equals("27021")&&(inspectCode[i].trim().equals("1130100"))) { // 단독
//continue;
//}
////29005 520**(조직)  빼기
//if (hosCode[i].trim().equals("29005")&&(inspectCode[i].trim().substring(0, 3).equals("520"))) { // 단독
//continue;
//}


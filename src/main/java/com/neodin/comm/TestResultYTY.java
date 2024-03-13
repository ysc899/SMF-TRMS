package com.neodin.comm;
/**
 * 기본 함수 및 문법 확인
 * @author cdy
 *
 */
public class TestResultYTY {
	boolean isNext = false;
	public static String hosCode[] = {"12345","12345","12345","12345","12345"};
	public static String rcvDate[] = {"20160701","20160701","20160701","20160701","20160701"};
	public static String rcvNo[] = {"15316","15316","15316","15316","15316"};
	public static String patName[] = {"테스터1","테스터2","테스터3","테스터4","테스터5"};
	public static String inspectCode[] = {"00067","0006800","0006801","0006802","0006803"};
	public static String inspectName[] =  {"배탈검사","치킨검사","막걸리검사","양대리검사","식중독검사"};
	public static String result[] =  {"정상^A","정상^B","정상^C","비정상^D","삐꾸^F"};
	public static String[] data = new String[100];
	
	public static String  curDate = "";
	public static String  curNo = "";
	
	public static int cnt = 5;
	
	
	public static void main(String[] args) {
		
		for (int i = 0; i < rcvDate.length; i++) {
			data[35]= "";
			
			//1. 병원 12345 에 00068검사 코드를 묶어서     [검  사  명]  [결    과]  [결과] 형식으로 노출
			if ( hosCode[i].trim().equals("12345")
					&& (inspectCode[i].trim().substring(0, 5).equals("00068"))) {

				data[35] = appendBlanks("검  사  명 ", 30) + "\t" + appendBlanks("결    과", 21) + " \t";
				data[35] += getDivide() + "\r\n" + appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t";
				data[34] = ""; // 문자결과
				data[39] = ""; // 참고치
				curDate = rcvDate[i];//접수일자
				curNo = rcvNo[i];//접수번호
				
				//접수일자 접수번호 검사코드 앞 5자리가 같은지 체크하여 문장 형태로 묶는다.
				for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode
						.equals(inspectCode[i].trim().substring(0, 5)) && curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
					data[35] += getDivide() + "\r\n" + 
						appendBlanks(inspectName[i], 30) + "\t" + appendBlanks(result[i], 21) + "\t";
					if (++i == cnt || i > cnt)
						break;
				}
				i--;
				
				data[36] = data[35].trim();
			}
			//System.out.println(data[35]);

			//2. 검사명 중 양대리라는 이름의 검사명이 포함된 것만의 환자명 출력  
			if(inspectName[i].indexOf("양대리")>-1){
//				System.out.println(patName[i]);
			}
			//3. 결과 값 중 ^구분자로 나눠서 노출
			String[] temp = result[i].split("\\^");
			/*for (int j = 0; j < temp.length; j++) {
				System.out.println(temp[j]);
			}*/
			
		}
		
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
	public static String getDivide() {
		return "";
	}
}

package com.neodin.comm;
/**
 * 기본 함수 및 문법 확인
 * @author cdy
 *
 */
public class Test {
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
			
			//System.out.println(data[35]);

			//2. 검사명 중 양대리라는 이름의 검사명이 포함된 것만의 환자명 출력  
			
			//3. 결과 값 중 ^구분자로 나눠서 노출
				
			
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

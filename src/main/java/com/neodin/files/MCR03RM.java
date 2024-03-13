package com.neodin.files;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

/**
 * 유형 설명을 삽입하십시오. 작성 날짜: (2004-02-04 오전 9:49:22) 작성 버전: VisualAge for Java 3.02
 * 작 성 자: 조 남 식 검사결과 내려 받기 Excel
 * 
 */
public class MCR03RM {
	private String id = ""; // 접속자 ID

	private String fdat = ""; // 작업일자

	private String tdat = ""; // 작업일자

	private String Hakcd = ""; // 작업일자

	/**
	 * MC178RM 생성자 주석.
	 */
	public MCR03RM() {
		initialize();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-04-19 오후 4:02:42) 작 성 자: 조 남 식
	 */
	public synchronized String createExcel(String id, String fromDate,
			String toDate, boolean isRewrite) {
		System.out.println("시작종료일자 id :" + id + " fromDate:" + fromDate
				+ " toDate:" + toDate);
		isRewrite = true;
		setId(id);
		setfDat(fromDate);
		settDat(toDate);
		setIsRewrite(isRewrite);
		try {
			Class runner = DownloadClassFactory.createDownloadClass(getId());
			System.out.println("class명:" + runner.getName());
			if (runner == null)
				runner = DownloadGeneral.class;
			Constructor constructor = runner.getConstructor(new Class[] {
					String.class, String.class, String.class, Boolean.class });
			return ((ResultDownload) constructor.newInstance(new Object[] {
					getId(), getfDat(), gettDat(), new Boolean(isRewrite) }))
					.getFileName();
		} catch (Exception e) {
			e.printStackTrace();
			return "nodata";

		}

	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-04-19 오후 4:02:42) 작 성 자: 조 남 식
	 */
	public synchronized String createExcel(String id, String fromDate,
			String toDate, boolean isRewrite, String Hakcd) {
		System.out.println("시작종료일자 id :" + id + " fromDate:" + fromDate
				+ " toDate:" + toDate + " Hak:" + Hakcd);
		isRewrite = true;
		setId(id);
		setfDat(fromDate);
		settDat(toDate);
		setIsRewrite(isRewrite);
		setHakcd(Hakcd);
		try {
			Class runner = DownloadClassFactory.createDownloadClass(getId());
			System.out.println("class명:" + runner.getName());
			if (runner == null)
				runner = DownloadGeneral.class;
			Constructor constructor = runner.getConstructor(new Class[] {
					String.class, String.class, String.class, Boolean.class,
					String.class });
			return ((ResultDownload) constructor.newInstance(new Object[] {
					getId(), getfDat(), gettDat(), new Boolean(isRewrite),
					getHakcd() })).getFileName();
		} catch (Exception e) {
			e.printStackTrace();
			return "nodata";

		}

	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-04-19 오후 4:02:42) 작 성 자: 조 남 식
	 */
	public synchronized String createExcel(String id, String fromDate,
			boolean isRewrite) {
		System.out.println("시작일자만 id :" + id + " fromDate:" + fromDate);
		isRewrite = false;
		setId(id);
		setfDat(fromDate);
		settDat(fromDate);
		setIsRewrite(isRewrite);
		try {
			Class runner = DownloadClassFactory.createDownloadClass(getId());
			// System.out.println("class명:" + runner.getName());
			if (runner == null)
				runner = DownloadGeneral.class;
			Constructor constructor = runner.getConstructor(new Class[] {
					String.class, String.class, String.class, Boolean.class });
			return ((ResultDownload) constructor.newInstance(new Object[] {
					getId(), getfDat(), gettDat(), new Boolean(isRewrite) }))
					.getFileName();

		} catch (Exception e) {
			e.printStackTrace();
			return "nodata";

		}

	}

	public synchronized String createExcel(String id, String fromDate,
			boolean isRewrite, String Hakcd) {
		System.out.println("시작일자만 id :" + id + " fromDate:" + fromDate
				+ " Hak:" + Hakcd);
		isRewrite = false;
		setId(id);
		setfDat(fromDate);
		settDat(fromDate);
		setIsRewrite(isRewrite);
		setHakcd(Hakcd);
		try {
			Class runner = DownloadClassFactory.createDownloadClass(getId());
			// System.out.println("class명:" + runner.getName());
			if (runner == null)
				runner = DownloadGeneral.class;
			Constructor constructor = runner.getConstructor(new Class[] {
					String.class, String.class, String.class, Boolean.class,
					String.class });
			return ((ResultDownload) constructor.newInstance(new Object[] {
					getId(), getfDat(), gettDat(), new Boolean(isRewrite),
					getHakcd() })).getFileName();

		} catch (Exception e) {
			e.printStackTrace();
			return "nodata";

		}

	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-03 오후 2:33:25)
	 * 
	 * @return java.lang.String
	 */
	private java.lang.String getfDat() {
		return fdat;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-03 오후 2:33:25)
	 * 
	 * @return java.lang.String
	 */
	private java.lang.String gettDat() {
		return tdat;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-03 오후 2:33:25)
	 * 
	 * @return java.lang.String
	 */
	private java.lang.String getId() {
		return id;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-04 오후 3:19:23)
	 */
	public void initialize() {
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2004-04-19 오후 4:04:25) 작 성 자: 조 남 식
	 * 
	 * @param args
	 *            ` java.lang.String[]
	 */
	public static void main(String[] args) {
		MCR03RM mcr03rm = new MCR03RM();

		System.out.println("파일명 :"
		
				+ mcr03rm.createExcel("31393" , "20190304", "20190304", true));
		//		+ mcr03rm.createExcel("7157878" , "20181017", "20181017", true));
				
		//				+ mcr03rm.createExcel("15892", "20170914", "20170914", true));
	}

	/**
	 * @param newDat
	 *            java.lang.String
	 */
	private void setfDat(java.lang.String newDat) {
		fdat = newDat;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-03 오후 2:33:25)
	 * 
	 * @param newDat
	 *            java.lang.String
	 */
	private void settDat(java.lang.String newDat) {
		tdat = newDat;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-03 오후 2:33:25)
	 * 
	 * @param newId
	 *            java.lang.String
	 */
	private void setId(java.lang.String newId) {
		id = newId;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2006-04-03 오후 2:35:45)
	 * 
	 * @param newIsRewrite
	 *            boolean
	 */
	private void setIsRewrite(boolean newIsRewrite) {

	}

	private java.lang.String getHakcd() {
		return Hakcd;
	}

	private void setHakcd(String hakcd) {
		Hakcd = hakcd;
	}
}
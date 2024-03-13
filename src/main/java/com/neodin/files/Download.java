package com.neodin.files;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2004-04-21 오후 9:02:42)
 * 작  성  자: 조 남 식
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Download extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/smnet");
		String sourceFilePathName = request.getParameter("sourceFilePathName") == null ? ""
				: request.getParameter("sourceFilePathName").trim();
		String destFileName = request.getParameter("destFileName") == null ? ""
				: request.getParameter("destFileName").trim();

		/* 파일 다운로드 시작 */

//		String filepath = "E:\\home\\site1\\ROOT\\outfiles"; // 파일의 절대경로
		String filepath = ""; // 파일의 절대경로
		
		String targetPath =  "/shared_files/outfiles/";
		String path = request.getSession().getServletContext().getRealPath(targetPath);
		filepath  = path;
		
		String ut = filepath + sourceFilePathName;
		File files = new java.io.File(ut);
		long filesize = files.length(); // 파일의 크기
		byte b[] = new byte[(int) filesize];
		String strClient = request.getHeader("User-Agent");
		if (strClient.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename="
					+ new String(destFileName.getBytes(), "ISO8859_1") + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(destFileName.getBytes(), "ISO8859_1") + ";");
		}
		response.setHeader("Content-Length", "" + filesize);
		if (filesize > 0 && files.isFile()) {
			BufferedInputStream fin = new BufferedInputStream(
					new FileInputStream(files));
			BufferedOutputStream outs = new BufferedOutputStream(response
					.getOutputStream());
			int read = 0;
			try {
				while ((read = fin.read(b)) != -1) {
					outs.write(b, 0, read);
				}
			} catch (Exception e) {
			} finally {
				outs.close();
				fin.close();
			}
		}
	}
}

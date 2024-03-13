package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 2   Fields: 0

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageResultPath extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageResultPath() {
	}

	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("이미지 쓰기");
		response.setContentType("application/smnet");
		final String sourceFilePathName = request.getParameter("sourceFilePathName") != null ? request
				.getParameter("sourceFilePathName").trim()
				: "";
		String sourceDptPathName = request.getParameter("sourceDptPathName") != null ? request
				.getParameter("sourceDptPathName").trim()
				: "5295";
		final String destFileName = request.getParameter("destFileName") != null ? request
				.getParameter("destFileName").trim()
				: "";
		if (sourceDptPathName.trim().equals("5211")
				|| sourceDptPathName.trim().equals("5230")
				|| sourceDptPathName.trim().equals("5231"))
			sourceDptPathName = "5295";
		final String temppaht = "\\";
		final String filepath = "E:\\mCLIS\\NML\\image\\" + sourceDptPathName
				+ temppaht;
		final String ut = filepath + sourceFilePathName.substring(0, 6) + temppaht
				+ sourceFilePathName;
		
		final File files = new File(ut);
		final long filesize = files.length();
		final byte b[] = new byte[(int) filesize];
//		final String strClient = request.getHeader("User-Agent");
		// if (strClient.indexOf("MSIE 5.5") > -1)
		// response.setHeader("Content-Disposition", "filename=" + new
		// String(destFileName.getBytes(), "ISO8859_1") + ";");
		// else
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(destFileName.getBytes(), "EUC-KR") + ";");
		response.setHeader("Content-Length", "" + filesize);
		if (filesize > 0L && files.isFile()) {
			final BufferedInputStream fin = new BufferedInputStream(
					new FileInputStream(files));
			final BufferedOutputStream outs = new BufferedOutputStream(response
					.getOutputStream());
			int read = 0;
			try {
				while ((read = fin.read(b)) != -1)
					outs.write(b, 0, read);
			} catch (final Exception e) {
				System.out.println(e.getMessage());
			} finally {
				outs.close();
				fin.close();
			}
		}
	}
}

package com.neodin.files;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neodin.comm.Common;

public class PdfResultPath extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PdfResultPath() {
	}

	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/smnet");
		final String sourceFilePathName = request.getParameter("sourceFilePathName") != null ? request
				.getParameter("sourceFilePathName").trim()
				: "";
		final String sourceDptPathName = request.getParameter("sourceDptPathName") != null ? request
				.getParameter("sourceDptPathName").trim()
				: "국내수탁";
		final String destFileName = request.getParameter("destFileName") != null ? request
				.getParameter("destFileName").trim()
				: "";
		final String temppaht = "\\";
		final String filepath = "E:\\mCLIS\\NML\\image\\"
				+ Common.Ko(sourceDptPathName) + temppaht;
		final String ut = filepath + sourceFilePathName.substring(0, 6) + temppaht
				+ sourceFilePathName;
		final File files = new File(ut);
		final long filesize = files.length();
		final byte b[] = new byte[(int) filesize];
		final String strClient = request.getHeader("User-Agent");
		if (strClient.indexOf("MSIE 5.5") > -1)
			response.setHeader("Content-Disposition", "filename="
					+ new String(destFileName.getBytes(), "ISO8859_1") + ";");
		else
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(destFileName.getBytes(), "ISO8859_1") + ";");
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
//				System.out.println(e.getMessage());
			} finally {
				outs.close();
				fin.close();
			}
		}
	}
}

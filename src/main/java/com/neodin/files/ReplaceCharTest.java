// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 3   Fields: 0

package com.neodin.files;

public class ReplaceCharTest {

	public ReplaceCharTest() {
		try {
			System.out.println(replaceCrLf("test\r\nreplace\r\nchar"));
			System.out.println(replaceCrLf(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new ReplaceCharTest();
	}

	public String replaceCrLf(String src) {
		return src.replace('\r', '@').replace('\n', '^');
	}
}

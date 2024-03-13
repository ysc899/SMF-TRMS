package com.neodin.files;

// Decompiled by Decafe PRO - Java Decompiler
// Classes: 1   Methods: 3   Fields: 1

import com.neodin.comm.AbstractDpc;

// Referenced classes of package com.neodin.files:
//            DpcGetDownloadClass

public class DownloadClassFactory {

	private static AbstractDpc dpc = new DpcGetDownloadClass();

	private DownloadClassFactory() {
	}

	public static Class createDownloadClass(String id) throws Exception {
		if (dpc.processDpc(new Object[] { id })) {
			String className = dpc.getParm().getStringParm(2);
//			System.out.println(className);
//			if(className.indexOf("DownloadGeneral")>-1){
//				className = "com.neodin.files.DownloadGeneral";
//			}
				
//			if("28858".equals(id)){
//				System.out.println(className);
//				className = "com.neodin.files.DownloadYOUNCT";	
//			}
			
			
			
			return className == null ? Class.forName("DownloadGeneral.class")
					: Class.forName(className.trim());
		} else {
			return Class.forName("DownloadGeneral.class");
		}
	}
}


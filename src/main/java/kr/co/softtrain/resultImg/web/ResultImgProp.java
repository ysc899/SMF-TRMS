package kr.co.softtrain.resultImg.web;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class ResultImgProp {
	private HashMap<String, String> propMap = new HashMap<String, String>();
	
	public static final String ENCKEY = "enc.key";
	public static final String QR_VERIFY_URL = "qrcode.verifyurl";
	public static final String QR_SAVE_DIR = "qrcode.savedir";
	public static final String QR_WIDTH = "qrcode.width";
	public static final String QR_HEIGHT = "qrcode.height";
	
	/**
	 * @Method Name	: init
	 * @author	: hong
	 * @version : 1.0
	 * @since	: 2021. 10. 14.
	 * @see : 
	 * @return
	 * @throws 
	 * @return 
	 */
	@PostConstruct
	protected void init() {
		Properties prop = new Properties();
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		URL url = cl.getResource("META-INF/property/result-img.properties");
			
		try {
			prop.load(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		String enckey 		= prop.getProperty("resultimg." + ENCKEY);
		String qrVerifyurl 	= prop.getProperty("resultimg." + QR_VERIFY_URL);
		String qrSavedir 	= prop.getProperty("resultimg." + QR_SAVE_DIR);
		String qrWidth 		= prop.getProperty("resultimg." + QR_WIDTH);
		String qrHeight 	= prop.getProperty("resultimg." + QR_HEIGHT);

		this.propMap.put(ENCKEY, enckey);
		this.propMap.put(QR_VERIFY_URL, qrVerifyurl);
		this.propMap.put(QR_SAVE_DIR, qrSavedir);
		this.propMap.put(QR_WIDTH, qrWidth);
		this.propMap.put(QR_HEIGHT, qrHeight);
	}
	
	public String get(String key) {
		if (this.propMap.containsKey(key))
			return this.propMap.get(key);
		else
			return "";
	}
	
	public String get(String key, String defaultVal) {
		if (this.propMap.containsKey(key))
			return this.propMap.get(key);
		else
			return "";
	}
}

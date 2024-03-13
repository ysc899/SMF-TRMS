package kr.co.softtrain.resultImg.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeUtils {
	private static Logger logger = LogManager.getLogger(QrCodeUtils.class);
	private static final AES256 aes256 = new AES256();
	
	public static void makeQrCode(String dat, String jno, String hos, String lang, String sign, ResultImgProp resultImgProp, String appRoot) {
		
        Map<String, String> qrMap = new HashMap<>();
        qrMap.put("dat", dat);
        qrMap.put("jno", jno);
        qrMap.put("hos", hos);
        qrMap.put("reportLang", lang);
        qrMap.put("sign", sign);			
        logger.debug("======= QR Code data - dat: {}, jno: {}, hos: {}, lang: {}, sign: {}", dat, jno, hos, lang, sign);

        try {
            String qrParam = aes256.encrypt(JSONValue.toJSONString(qrMap));
            logger.debug("======= QR Code data 암호화 결과: {}", qrParam);
            
            String qrData = resultImgProp.get(ResultImgProp.QR_VERIFY_URL) + "?q=" + qrParam;
            String qrSavePath = appRoot + resultImgProp.get(ResultImgProp.QR_SAVE_DIR) + "/" + dat + "_" + jno + "_" + lang + ".png";
            int qrWidth = NumberUtils.toInt(resultImgProp.get(ResultImgProp.QR_WIDTH), 200);
            int qrHeight = NumberUtils.toInt(resultImgProp.get(ResultImgProp.QR_HEIGHT), 200);
            logger.debug("======= QR Code 저장 경로 : {}", qrSavePath);

            writeQrCodeImage(qrData, qrWidth, qrHeight, qrSavePath);
        } catch (Exception ex) {
            throw new ResultImgException("QR Code 생성 실패", ex);
        }
    }

	
	public static void writeQrCodeImage(String text, int width, int height, String qrSavePath) throws Exception {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(new String(text.getBytes("UTF-8"), "ISO-8859-1"), BarcodeFormat.QR_CODE, width, height);
	        
	        MatrixToImageConfig config = new MatrixToImageConfig(0xff020202, 0xffffffff);
	        
	        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
	        ImageIO.write(qrImage, "png", new File(qrSavePath));
		} catch (Exception e) {
			throw e;
		}
	}


    // QR코드 생성
    public static String generateQRCodeImage(String text, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        String base64Encoded = Base64.getEncoder().encodeToString(pngData);
        return base64Encoded;
    }
}

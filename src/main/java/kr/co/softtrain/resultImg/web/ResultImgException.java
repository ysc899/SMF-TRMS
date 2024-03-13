package kr.co.softtrain.resultImg.web;

public class ResultImgException extends RuntimeException {
	private String errCode;

	public ResultImgException() {
		super();
	}

	public ResultImgException(String msg) {
		super(msg);
	}
	
	public ResultImgException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ResultImgException(String errCode, String msg) {
		super(msg);
		this.errCode = errCode;
	}

	public ResultImgException(String errCode, String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return this.errCode;
	}
}

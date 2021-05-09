package com.server.fmb.exception;

public class DevException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int errorCode;
	
	public DevException() {
		super();
	}
	public DevException(String detailMessage) {
		super(detailMessage);
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}

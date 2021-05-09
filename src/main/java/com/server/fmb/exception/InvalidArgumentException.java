package com.server.fmb.exception;

public class InvalidArgumentException extends DevException {

	private static final long serialVersionUID = 1L;
	public InvalidArgumentException() {
		super("Invalid Argument Exception");
		this.setErrorCode(DevErrorCode.INVALID_ARGUMENT.getValue());
	}
	public InvalidArgumentException(String detailMessage) {
		super("Invalid Argument Exception : " + detailMessage);
		this.setErrorCode(DevErrorCode.INVALID_ARGUMENT.getValue());
	}
	
}

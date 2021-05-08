/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    InvalidArgumentException.java
 *  Description:  	유효하지 않은 아큐먼트를 위한 Exception
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
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

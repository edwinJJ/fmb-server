/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    ErrorCode.java
 *  Description:  	Airov Development Exception의 Error Code들 정의
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.exception;
	
public enum DevErrorCode {
		
	NOT_AUTHENTICATED(101),		
	DUPLICATED_NAME(101),		
	DUPLICATED_USERID(102),		
	INVALID_ARGUMENT(103);
	
    private final int value;
    
    DevErrorCode(int value){
        this.value = value;
            
    }
    
    public int getValue(){
        return value;
    }
}

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

/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    ResultSet.java
 *  Description:  	Data result 관련 서비스
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.server.fmb.constant.Constant;

public class ResultSet {
	
	public Map<String, Object> getResultSet(Object result, boolean status, String dataName, String errorMessage) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(dataName, result);
		resultMap.put(Constant.STATUS, status);
		resultMap.put(Constant.ERRORMESSAGE, errorMessage);
		return resultMap;
	}
}

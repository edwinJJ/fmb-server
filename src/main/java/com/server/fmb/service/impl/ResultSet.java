package com.server.fmb.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.server.fmb.constant.Constant;

public class ResultSet {
	
	public Map<String, Object> getResultSet(Object result, boolean status, String dataName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(dataName, result);
		resultMap.put(Constant.STATUS, status);
		return resultMap;
	}
}

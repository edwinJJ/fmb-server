/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    FormatUtil.java
 *  Description:    값들의 표현형식에 대한 다양한 기능들을 제공하는 클래스
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.util;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FormatUtil {

	/**
	 * str 문자열이 size보다 작으면 front 혹은 rear에 size만큼 filler를 채워서 리턴한다.  
	 * 
	 * @param str
	 * @param front
	 * @param size
	 * @param filler
	 * @return
	 */
	public static String fixSize(String str, boolean front, int size, String filler) {
		if (str.length() >= size) {
			return str;
		} else {
			int fillCount = size - str.length();
			for(int i = 0 ; i < fillCount ; i++) {
				if(front) {
					str = filler + str;
				} else {
					str = str + filler;
				}
			}
			return str;
		}
	}
	
	/**
	 * Convert Object To Pretty JSON String
	 * 
	 * @param item
	 * @return
	 */
	public static String toJsonString(Object item) {
		return toJsonString(item, true);
	}
	
	/**
	 * Convert Object To JSON String
	 * 
	 * @param item
	 * @param pretty
	 * @return
	 */
	public static String toJsonString(Object item, boolean pretty) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (pretty) {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
			} else {
				return mapper.writeValueAsString(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	

	/**
	 * Convert JSON String To Object
	 * 
	 * @param jsonStr
	 * @param inputType
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr, Class<T> inputType) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, inputType);
	}
	
	/**
	 * Convert Object To JSON String
	 * 
	 * @param item
	 * @return
	 */
	public static String toUnderScoreJsonString(Object item) {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		return gson.toJson(item);
	}

	/**
	 * Convert JSON String To Object
	 * 
	 * @param jsonStr
	 * @param inputType
	 * @return
	 */
	public static <T> T underScoreJsonToObject(String jsonStr, Class<T> inputType) {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		return gson.fromJson(jsonStr, inputType);
	}
	
	/**
	 * json Content를 JSONArray로 파싱 
	 * 
	 * @param content
	 * @return
	 */
	public static JSONArray parseJsonArray(String content) {
		JSONParser jsonParser = new JSONParser();		
		try {
			return (JSONArray) jsonParser.parse(content);
		} catch (org.json.simple.parser.ParseException e) {
			throw new IllegalArgumentException("Failed to parse : " + e.getMessage(), e);
		}
	}
	
	/**
	 * json Content를 JSONArray로 파싱 
	 * 
	 * @param content
	 * @return
	 */
	public static JSONObject parseJsonObject(String content) {
		JSONParser jsonParser = new JSONParser();		
		try {
			return (JSONObject) jsonParser.parse(content);
		} catch (org.json.simple.parser.ParseException e) {
			throw new IllegalArgumentException("Failed to parse : " + e.getMessage(), e);
		}
	}
		
	/**
	 * convert from map to object
	 * entity 클래스의 변수명으로 할당시키는것이 아니라 mapper<mapData 키명, class 변수명> 를 이용하여 
	 * 객채화 시킨다.
	 * 
	 * @param mapData
	 * @param entityClass
	 * @return
	 */
	public static Object mapToObject(Map<String, Object> mapData, Map<String, String> mapper, Class<?> entityClass) {
		if (ValueUtil.isEmpty(mapData) || ValueUtil.isEmpty(mapper)) {
			return null;
		}
		Object instance = ClassUtil.newInstance(entityClass);
		Iterator<String> keyIter = mapData.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			Object value = mapData.get(key);
			String mappingField = mapper.get(key);
			if (ValueUtil.isEmpty(mappingField)) {
				continue;
			}
			if (ClassUtil.hasField(entityClass, mappingField)) {
				ClassUtil.setFieldValue(instance, mappingField, value);
			}
		}
		return instance;
	}
	
	/**
	 * str substring
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substr(String str, int start, int end) {
		if (str != null && str.length() > (start + end)) {
			str = str.substring(start, end);
		}
		return str;
	}
	
	/**
	 * number formatting
	 * 
	 * @param format
	 * @param value
	 * @return
	 */
	public static String numberFormat(String format, Number value) {
		if (ValueUtil.isEmpty(format)) {
			format = "#,###";
		}
		if (value == null) {
			value = 0;
		}
	    DecimalFormat df = new DecimalFormat(format);
	    return df.format(value);
	}
	
	/**
	 * camel case 형태의 문자를 under score 형태의 문자로 변환한다.
	 * 
	 * @param camelCaseStr
	 * @return
	 */
	public static String toUnderScore(String camelCaseStr) {
		return camelCaseStr.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
	}
	
	/**
	 * under score 형식의 underScoreStr 문자열을 camel case 형식의 문자열로 변환  
	 * 
	 * @param underScoreStr
	 * @return
	 */
	public static String toCamelCase(String underScoreStr) {
		return ValueUtil.toCamelCase(underScoreStr, '_');
	}
	
	/**
	 * bytes를 KB, MB, GB등으로 변환한다.
	 * 
	 * @param bytes
	 * @param si
	 * @return
	 */
	public static String humanReadableByte(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);		
	}
	
	/**
	 * 파라메터에 값으로 치환하여 메세지를 생성한다.
	 * 
	 * @param message
	 * @param params
	 * @return
	 */
	public static String parseMessge(String message, List<String> params) {
		return (params == null || params.isEmpty()) ? message : MessageFormat.format(message, params.toArray());
	}

	/**
	 * java object to map.
	 * 
	 * @param object
	 * @return
	 */
    public static Map<String, Object> getMapFromJavaObject(Object object) {

        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(object, Map.class);
        return map;
    }
    
    public static String xmlStringToJsonString(String xmlString) {
    	try {
    		return XML.toJSONObject(xmlString).toString(0);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    public static String jsonStringToXmlString(String jsonString) {
    	try {
    		org.json.JSONObject json = new org.json.JSONObject(jsonString);
    		return XML.toString(json);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static Map<String,Object> convertJSONstringToMap(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        
        map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        
        return map;
    }


}

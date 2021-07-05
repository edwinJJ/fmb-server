package com.server.fmb.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.server.fmb.websocket.UIWebsocketManager;

public class EngineUtil {

	public static Object access(String accessor, Object base) {
		if (accessor == null || accessor.equals("")) {
			return null;
		}
		String[] accessors = accessor.trim().replaceAll("\\[(\\w+)\\]", ".$1").replaceAll("^\\.", "").split(".");
		if (accessors.length == 0) {
			return null;
		}
		
		Object result = base;
		for (String key : accessors) {
			if (key.trim() == null || key.trim().equals("")) {
				continue;
			}
			if (result instanceof Map) {
				result = ((Map)result).get(key.trim());
			} else {
				return null;
			}
		}
		return result;
	}
	
	private static Map parseSubText(String text) {
		Map prop = new HashMap();
		int defaultIndex = text.indexOf("||");
		String originText = "";
		String defaultValue = "";
		if (defaultIndex != -1) {
			originText = text;
			defaultValue = text.substring(defaultIndex + 2, text.length() - 1).trim();
			text = text.replace(text.substring(defaultIndex, text.length() - 1), "").trim();
		}
		String[] splited = text.substring(2, text.length() - 3).trim().replaceAll("\\[(\\w+)\\]", ".$1").replace("^\\.", "").split(".");
		List<String> accessors = new ArrayList<String>();
		for (String item : splited) {
			if (item.trim()==null || item.trim().equals("")) {
				continue;
			}
			accessors.add(item.trim());
		}
		if (accessors.size()>0) {
						
		}
		prop.put("defaultValue", defaultValue);
		prop.put("match", text);
		prop.put("originText", originText == null || originText.equals("") ? text : originText);
		prop.put("target", accessors.remove(0));
		prop.put("accessors", accessors);
		return prop;
	}
	public static Object substitue(String value, Object data) {
		String text = value;
		Matcher matcher = Pattern.compile("#{[^}]*}", Pattern.CASE_INSENSITIVE).matcher(text);
		matcher.find();
		String substituteVal = matcher.group(0);
  	  	Map prop = substituteVal != null ? EngineUtil.parseSubText(substituteVal) : null;
		if (prop != null && data instanceof Map && ((Map)data).get(prop.get("target")) != null) {
			List<String> accessors = (List)prop.get("accessors");
			if (accessors.size() > 0) {
				Object result = ((Map)data).get(prop.get("target"));;
				for (String key : accessors) {
					if (key.trim() == null || key.trim().equals("")) {
						continue;
					}
					if (result instanceof Map) {
						result = ((Map)result).get(key.trim());
					} else {
						return null;
					}
				}
				return result;
			} else {
				return ((Map)data).get(prop.get("target"));
			}
		}
		return value;
	}
	
	public static void publish(String key, Object data) {
		try {
	        UIWebsocketManager ws = new UIWebsocketManager();
	        Map<String, Object> message = new HashMap<String, Object>();
	        message.put("key", key);
	        message.put("data", data);
	        ws.sendAll(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

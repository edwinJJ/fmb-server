package com.server.fmb.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TaskRegistry {
	
	static Map<String, Function> handlers = new HashMap<String, Function>();
	
	static Function getTaskHandler(String type) {
		return TaskRegistry.handlers.get(type);
	}
	
	static void registerTaskHandler(String type, Function handler) {
		TaskRegistry.handlers.put(type, handler);
	}

	static void unregisterTaskHandler(String type) {
		TaskRegistry.handlers.remove(type);
	}
	static Map<String, Function> getTaskHandlers() {
		return TaskRegistry.handlers;
	}
}

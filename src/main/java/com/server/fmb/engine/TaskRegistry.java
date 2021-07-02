package com.server.fmb.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TaskRegistry {
	
	static Map<String, ITaskHandler> handlers = new HashMap<String, ITaskHandler>();
	
	static ITaskHandler getTaskHandler(String type) {
		return TaskRegistry.handlers.get(type);
	}
	
	static void registerTaskHandler(String type, ITaskHandler handler) {
		TaskRegistry.handlers.put(type, handler);
	}

	static void unregisterTaskHandler(String type) {
		TaskRegistry.handlers.remove(type);
	}
	static Map<String, ITaskHandler> getTaskHandlers() {
		return TaskRegistry.handlers;
	}
	
}

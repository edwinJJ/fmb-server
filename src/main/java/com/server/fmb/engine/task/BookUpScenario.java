package com.server.fmb.engine.task;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.PendingObject;
import com.server.fmb.engine.PendingQueue;
import com.server.fmb.engine.ScenarioEngine;
import com.server.fmb.entity.Steps;

public class BookUpScenario implements ITaskHandler {

	@Autowired
	ScenarioEngine scenarioEngine;
	
	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return this.runAwait(step, context);
	}
	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		HandlerResult hResult = new HandlerResult();
		
		JSONObject paramsJson = (JSONObject)(new JSONParser()).parse(step.getParams());
		String params_scenario = (String)paramsJson.get("scenario");
		String params_delay = (String)paramsJson.get("delay");
		String params_priority = (String)paramsJson.get("priority");
		String params_variables = (String)paramsJson.get("variables");
		String params_tag = (String)paramsJson.get("tag");

		PendingQueue pendingQueue = scenarioEngine.getPendingQueue(context.domainId);
		
		int delay = (int)EngineUtil.substitue(params_delay, context.data);
		
		PendingObject pendingObject = new PendingObject();
		Map<String, Object> stuffMap = new HashMap<String, Object>();
		stuffMap.put("scenario", params_scenario);
		stuffMap.put("variables", ((Map<String, Object>)EngineUtil.access(params_variables, context.data)).entrySet().stream()
			    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
		stuffMap.put("delay", delay);
		stuffMap.put("priority", Integer.parseInt(params_priority));
		stuffMap.put("tag", params_tag);
		pendingObject.stuff = stuffMap;
		pendingQueue.put(pendingObject);
		
		hResult.data = pendingQueue.getStatus();
		return hResult;
	}

}

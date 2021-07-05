package com.server.fmb.engine.task;

import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.entity.Steps;

public class SwitchRangeScenario implements ITaskHandler {

	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return runAwait(step, context);
	}

	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		HandlerResult hResult = new HandlerResult();
		
		JSONObject paramsJson = (JSONObject)(new JSONParser()).parse(step.getParams());
		String params_accessor = (String)paramsJson.get("accessor");
		JSONObject params_cases = (JSONObject)paramsJson.get("cases");
		String params_variables = (String)paramsJson.get("variables");

		int value = (int)EngineUtil.access(params_accessor, context.data);
		
		String range = null;
		for (Object key : params_cases.keySet()) {
			if ("default".equals(key)) {
				continue;
			}
			String[] tokens = ((String)key).split("~");
			if (tokens.length != 2) {
				continue;
			}
			int from = Integer.parseInt(tokens[0]);
			int to = Integer.parseInt(tokens[1]);
			if (from <= value && to > value) {
				range = (String)key;
				break;
			}
		}
		String scenarioName = (String)params_cases.get(range);
		
		// TODO id가 scenarioName와 같은 Scenario로 가져오는 기능 추가 필
		Scenarios subscenario = null; //scenarioService.getScenarioById(scenario); id = scenarioName, join steps, domain
		
		Context contextCloned = context.clone();
		contextCloned.data = null;
		if (params_variables != null) {
			contextCloned.variables = ((Map<String, Object>)EngineUtil.access(params_variables, context.data)).entrySet().stream()
				    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));			
		}
		Context subContext = context.instance.loadSubscenarioAwait(step, subscenario, contextCloned);
				
		hResult.data = subContext.data;

		return hResult;
	}

}

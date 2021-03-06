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

public class SubScenario implements ITaskHandler {

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
		String params_scenario = (String)paramsJson.get("scenario");
		String params_variables = (String)paramsJson.get("variables");

		// TODO id가 scenario와 같은 Scenario로 가져오는 기능 추가 필
		Scenarios subscenario = null; //scenarioService.getScenarioById(scenario); id = scenario, join steps, domain
		
		Context contextCloned = context.clone();
		contextCloned.data = null;
		if (params_variables != null) {
			contextCloned.variables = ((Map<String, Object>)EngineUtil.access(params_variables, context.data)).entrySet().stream()
				    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));			
		}
		Context subContext = context.instance.loadSubscenarioAwait(step, subscenario, contextCloned);
				
		hResult.data = subContext.data;

		return null;
	}

}

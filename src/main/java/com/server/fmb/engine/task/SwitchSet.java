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

public class SwitchSet implements ITaskHandler {

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

		String value = (String)EngineUtil.access(params_accessor, context.data);
		
		Object data = params_cases.get(value);
		if (data == null) {
			data = params_cases.get("default");
		}
		hResult.data = data;

		return hResult;
	}

}

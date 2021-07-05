package com.server.fmb.engine.task;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.entity.Steps;

public class SwitchGoto implements ITaskHandler {

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

		Object value = EngineUtil.access(params_accessor, context.data);
		String next = (String)params_cases.get((String)value);
		if (next == null) {
			next = (String)params_cases.get("default");
		}
		
		hResult.next = next;
		return hResult;
	}

}

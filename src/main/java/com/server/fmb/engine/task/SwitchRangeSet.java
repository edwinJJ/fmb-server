package com.server.fmb.engine.task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.entity.Steps;

public class SwitchRangeSet implements ITaskHandler {

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
		hResult.data = params_cases.get(range);
		
		return hResult;
	}

}

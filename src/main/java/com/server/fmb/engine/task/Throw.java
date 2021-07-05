package com.server.fmb.engine.task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.entity.Steps;

public class Throw implements ITaskHandler {

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
		String params_message = (String)paramsJson.get("message");

		throw new Exception(params_message + " is thrown by step " + step.getName());
		
	}

}

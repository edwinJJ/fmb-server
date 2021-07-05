package com.server.fmb.engine.task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.entity.Steps;

public class Goto implements ITaskHandler {

	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return runAwait(step, context);
	}

	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		HandlerResult hResult = new HandlerResult();
		JSONObject paramsJson = (JSONObject)(new JSONParser()).parse(step.getParams());
		String params_goto = (String)paramsJson.get("goto");
		hResult.next = params_goto;
		return hResult;
	}

}

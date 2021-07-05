package com.server.fmb.engine.task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.entity.Steps;

public class Publish implements ITaskHandler {

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
		String params_tag = (String)paramsJson.get("tag");
		String params_accessor = (String)paramsJson.get("accessor");

		if (params_tag == null || params_accessor == null) {
			throw new Exception("tag and accessor should be defined");
		}
		
		Object value = EngineUtil.access(params_accessor, context.data);
		
		context.instance.publishData(params_tag, value);
		
		hResult.data = value;
		return hResult;
	}

}

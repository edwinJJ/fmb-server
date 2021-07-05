package com.server.fmb.engine.task;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.entity.Steps;

public class Script implements ITaskHandler {

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
		String params_script = (String)paramsJson.get("script");
		Object data = context.data;
		Object variables = context.variables;
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		ScriptContext engineContext = engine.getContext();
		engineContext.setAttribute("data", data, ScriptContext.ENGINE_SCOPE);
		engineContext.setAttribute("variables", variables, ScriptContext.ENGINE_SCOPE);

		hResult.data = engine.eval(params_script);
		return hResult;
	}

}

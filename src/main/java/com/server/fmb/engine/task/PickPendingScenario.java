package com.server.fmb.engine.task;

import java.util.Date;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.PendingObject;
import com.server.fmb.engine.PendingQueue;
import com.server.fmb.engine.ScenarioEngine;
import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.entity.Steps;
import com.server.fmb.service.IScenarioService;

public class PickPendingScenario implements ITaskHandler {

	@Autowired
	ScenarioEngine scenarioEngine;
	@Autowired
	IScenarioService scenarioService;
	
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
		String params_waitFor = (String)paramsJson.get("waitFor");

		String tag = params_tag;
		int waitFor = params_waitFor == null ? -1 : Integer.parseInt(params_waitFor);
		long till = new Date().getTime() + waitFor;
		
		PendingQueue pendingQueue = scenarioEngine.getPendingQueue(context.domainId);
		
		Object stuff = null;
		while(context.checkState()) {
			PendingObject pendingObject = pendingQueue.pick(tag);
			stuff = pendingObject.stuff;
			if (stuff != null) {
				break;
			}
			
			long toTill = waitFor == -1 ? 1000 : till - new Date().getTime();
			if (toTill <= 0) {
				return null;
			}
			
			Thread.sleep(Math.min(1000, toTill));
		}
		
		if (stuff == null) {
			return null;
		}
		
		String scenario = null;
		Object variables = null;
		if (stuff instanceof Map) {
			scenario = (String)((Map)stuff).get("scenario");
			variables = ((Map)stuff).get("variables");
		} 
		
		// TODO id가 scenario와 같은 Scenario로 가져오는 기능 추가 필
		Scenarios subscenario = null; //scenarioService.getScenarioById(scenario); id = scenario, join steps, domain
		
		Context contextCloned = context.clone();
		contextCloned.data = null;
		contextCloned.variables = variables;
		Context subContext = context.instance.loadSubscenarioAwait(step, subscenario, contextCloned);
				
		hResult.data = subContext.data;
		return hResult;
	}

}

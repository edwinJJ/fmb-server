package com.server.fmb.engine.task;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.PendingQueue;
import com.server.fmb.engine.PendingQueue.PendingObject;
import com.server.fmb.engine.ScenarioEngine;
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
		
		String tag = ""; // step.params.tag
		int waitFor = -1;//step.params.waitFor;
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
		
		Scenarios subscenario = null; //scenarioService.getScenarioById(scenario); id = scenario, join steps, domain
		
		Context subContext = context.instance.loadSubscenarioAwait(step, subscenario, context);
		subContext.data = null;
		subContext.variables = variables;
		
		
		HandlerResult result = new HandlerResult();
		result.data = subContext.data;
		return result;
	}

}

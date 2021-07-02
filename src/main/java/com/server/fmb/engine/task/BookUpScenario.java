package com.server.fmb.engine.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.PendingQueue;
import com.server.fmb.engine.ScenarioEngine;
import com.server.fmb.entity.Steps;

public class BookUpScenario implements ITaskHandler {

	@Autowired
	ScenarioEngine scenarioEngine;
	
	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return this.runAwait(step, context);
	}
	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		
		PendingQueue pendingQueue = scenarioEngine.getPendingQueue(context.domainId);
		
		// TODO
//		  delay = Number(substitute(delay, data))
//
//				  pendingQueue.put({
//				    stuff: {
//				      scenario,
//				      variables: deepClone(access(variables, data))
//				    },
//				    delay,
//				    priority: Number(priority) || 0,
//				    tag: tag || ''
//				  })
		
		
		
		HandlerResult result = new HandlerResult();
		result.data = pendingQueue.getStatus();
		return result;
	}

}

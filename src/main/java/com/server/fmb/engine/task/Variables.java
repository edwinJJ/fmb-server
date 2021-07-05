package com.server.fmb.engine.task;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.entity.Steps;

public class Variables implements ITaskHandler {

	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return runAwait(step, context);
	}

	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		HandlerResult hResult = new HandlerResult();
		hResult.data = context.variables;
		if (context.variables instanceof ArrayList) {
			if (((ArrayList)context.variables).size() > 0) {
				Object[] listData = new Object[((ArrayList)context.variables).size()];
				for (int i = 0; i < ((ArrayList)context.variables).size(); i++) {
					listData[i] = ((ArrayList)context.variables).get(i);
				}
				hResult.data = listData;
			}
//			hResult.data = context.variables instanceof List ? [...variables] : typeof variables == 'object' ? { ...variables } : variables			
		} else if (context.variables instanceof Map) {
//			hResult.data = context.variables instanceof List ? [...variables] : typeof variables == 'object' ? { ...variables } : variables			
		}
		return hResult;
	}

}

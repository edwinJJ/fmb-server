package com.server.fmb.engine.task;

import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.engine.Types.SCENARIO_STATE;
import com.server.fmb.entity.Steps;

public class End implements ITaskHandler {

	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return runAwait(step, context);
	}

	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		HandlerResult result = new HandlerResult();
		result.state = SCENARIO_STATE.STOPPED;
		return result;
	}

}

package com.server.fmb.engine;

import com.server.fmb.engine.Types.SCENARIO_STATE;
import com.server.fmb.entity.Steps;

public interface ITaskHandler {
	
	public class handlerResult {
		String next;
		SCENARIO_STATE state;
		Object data;
	}	
	public handlerResult run(Steps step, Context context); 
	
}

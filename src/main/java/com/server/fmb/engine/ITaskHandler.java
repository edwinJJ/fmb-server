package com.server.fmb.engine;

import com.server.fmb.engine.Types.SCENARIO_STATE;
import com.server.fmb.entity.Steps;

public interface ITaskHandler {
	
	public class HandlerResult {
		public String next;
		public SCENARIO_STATE state;
		public Object data;
	}
	public HandlerResult run(Steps step, Context context) throws Exception;
	public HandlerResult runAwait(Steps step, Context context) throws Exception;
	
	
}

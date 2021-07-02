package com.server.fmb.engine.task;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.ConnectionManager;
import com.server.fmb.engine.Context;
import com.server.fmb.engine.IConnectionInstance;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.entity.Steps;

public class DatabaseQuery implements ITaskHandler {

	@Autowired
	ConnectionManager connectionManager;
	
	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return runAwait(step, context);
	}

	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		// TODO
		String connectionName = null;//step.getConnection();
		String query = step.getParams();
		
		IConnectionInstance dbConnection = connectionManager.getConnectionInstancByName(context.domainId, connectionName);
		
		HandlerResult result = new HandlerResult();
		result.data = dbConnection.queryAwait(query, null); 
		return result;
	}
}

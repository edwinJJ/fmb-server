package com.server.fmb.engine.task;

import java.sql.Connection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.server.fmb.engine.ConnectionManager;
import com.server.fmb.engine.Context;
import com.server.fmb.engine.IConnectionInstance;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.entity.Steps;
import com.server.fmb.util.ValueUtil;

@Service
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

		HandlerResult hResult = new HandlerResult();

		String connectionName = step.getConnection();
		
		JSONObject paramsJson = (JSONObject)(new JSONParser()).parse(step.getParams());
		String query = (String)paramsJson.get("query");
		
		IConnectionInstance dbconnection = connectionManager.getConnectionInstancByName(context.domainId, connectionName);
		
//		  const vm = new VM({
//			    sandbox: {
//			      data,
//			      variables
//			    }
//			  })
//
//			  query = vm.run('`' + query + '`')

		if (ValueUtil.isNotEmpty(dbconnection)) {
			hResult.data = dbconnection.queryAwait(query, null);
		}
		return hResult;
	}
}

package com.server.fmb.engine.connector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.ConnectionManager;
import com.server.fmb.engine.IConnectionInstance;
import com.server.fmb.engine.Types.Connector;
import com.server.fmb.entity.Connections;

public class OracleConnector implements Connector {
	
	@Autowired
	ConnectionManager connectionManager;
	
	private class ConnectionInstance implements IConnectionInstance {

		Object dbConnection;
		
		public ConnectionInstance(Object dbConnection) {
			this.dbConnection = dbConnection;
		}
		
		@Override
		public Object query(String queryString, String params) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	@Async
	public void ready(List<Connections> connections) throws Exception {
		for (Connections connection : connections) {
			this.connect(connection);
		}
	}

	@Override
	@Async
	public void connect(Connections connection) throws Exception {
		// get oracel connection
		Object dbConnection = null;
		ConnectionInstance connectionInstance = new ConnectionInstance(dbConnection);
		connectionManager.addConnectionInstance(connection, connectionInstance);
	}

	@Override
	@Async
	public void disconnect(Connections connection) throws Exception {
		IConnectionInstance connectionInstance = connectionManager.getConnectionInstance(connection);
		if (connectionInstance != null) {
			connectionInstance.close();			
		}
		connectionManager.removeConnectionInstance(connection);
	}

}

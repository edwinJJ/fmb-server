package com.server.fmb.engine.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.server.fmb.engine.ConnectionManager;
import com.server.fmb.engine.IConnectionInstance;
import com.server.fmb.engine.Types.Connector;
import com.server.fmb.entity.Connections;

@Service
public class OracleConnector implements Connector {
	
	@Autowired
	ConnectionManager connectionManager;
	
	static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String ORACLE_URL = "jdbc:oracle:thin:@";
	
//	Connection dbConnection;
//    PreparedStatement pstmt;
//    ResultSet rs;
	
	private class ConnectionInstance implements IConnectionInstance {

		Connection dbConnection;
		
		public ConnectionInstance(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}
		
		@Override
		public Object query(String queryString, String params) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() throws Exception {
			this.dbConnection.close();
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
		JSONObject jsonObj = (JSONObject) new JSONParser().parse(connection.getParams());
	    String url = connection.getEndpoint();
	    String user = (String) jsonObj.get("user");
	    String password = (String) jsonObj.get("password");
	    
    	Class.forName(ORACLE_DRIVER);
    	Connection dbConnection = DriverManager.getConnection(ORACLE_URL + url, user, password);
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

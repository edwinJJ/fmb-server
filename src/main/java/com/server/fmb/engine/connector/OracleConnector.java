package com.server.fmb.engine.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private class ConnectionInstance implements IConnectionInstance {

		Connection dbConnection;
		String connectionName;
		
		public ConnectionInstance(Connection dbConnection, String connectionName) {
			this.dbConnection = dbConnection;
			this.connectionName = connectionName;
		}
		
		@Override
		public Object query(String queryString, String params) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object queryAwait(String queryString, String params) throws Exception {
			Statement stmt = this.dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);
			ResultSetMetaData rsmd = rs.getMetaData();
			List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				for (int i=0; i<rsmd.getColumnCount(); i++) {
					if (rsmd.getColumnTypeName(i+1).equals("CLOB") ||
						rsmd.getColumnTypeName(i+1).equals("NCLOB") ||
						rsmd.getColumnTypeName(i+1).equals("BLOB") ||
						rsmd.getColumnTypeName(i+1).equals("BFILE")) {
						dataMap.put(rsmd.getColumnName(i+1), rs.getString(i+1));
					} else {
						dataMap.put(rsmd.getColumnName(i+1), rs.getObject(i+1));
					}
					
				}
				objList.add(dataMap);
            }
			stmt.close();
			rs.close();
			return objList;
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
	    
	    try {
	    	Class.forName(ORACLE_DRIVER);
	    	Connection dbConnection = DriverManager.getConnection(ORACLE_URL + url, user, password);
	    	ConnectionInstance connectionInstance = new ConnectionInstance(dbConnection, connection.getName());
			connectionManager.addConnectionInstance(connection, connectionInstance);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
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

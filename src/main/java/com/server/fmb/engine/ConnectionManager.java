package com.server.fmb.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.server.fmb.constant.Constant;
import com.server.fmb.engine.Types.CONNECTION_STATE;
import com.server.fmb.engine.Types.Connector;
import com.server.fmb.engine.connector.OracleConnector;
import com.server.fmb.entity.Connections;
import com.server.fmb.service.IConnectionService;

@Service
public class ConnectionManager implements CommandLineRunner {
	
	@Autowired
	private IConnectionService connectionService;
	
	@Autowired
	private OracleConnector oracleConnectorService;
	
	public final static String[] ConnectionStatus = new String[] {Constant.CONNECTED, Constant.DISCONNECTED};


	private static Map<String, Connector> connectors = new HashMap<String, Connector>();
	private static Map<String, Map<String, IConnectionInstance>> connections = new HashMap<String, Map<String, IConnectionInstance>>();
	
	@Override
	public void run(String... args) throws Exception {
		this.ready();
	}
	
	public ConnectionManager() {
		this.registerConnector("oracle-connector", new OracleConnector());
	}
	
	@Async
	public void ready() throws Exception {
		List<Connections> activeConnections = connectionService.getConnectionsByActive(Constant.CONNECTION_ACTIVE_TRUE); // where active = true, join domain, creator, updator
		if (activeConnections == null) {
			return;
		}
//		for (String type : connectors.keySet()) {
//			List<Connections> typeConnections = new ArrayList<Connections>();
//			for (Connections connection : activeConnections) {
//				if (type.equals(connection.getType())) {
//					typeConnections.add(connection);					
//				}
//			}
//			connectors.get(type).ready(typeConnections);
//		}
		
		oracleConnectorService.ready(activeConnections);
	}
	
	public void registerConnector(String type, Connector connector) {
		connectors.put(type, connector);
	}

	public Connector getConnector(String type) {
		return connectors.get(type);
	}
	
	public Map<String, Connector> getConnectors() {
		return connectors;
	}
	
	public void unregisterConnector(String type) {
		connectors.remove(type);
	}
	
	public IConnectionInstance getConnectionInstance(Connections connection) {
		Map<String, IConnectionInstance> domainConnections = connections.get(connection.getDomainId());
		if (domainConnections != null) {
			return domainConnections.get(connection.getName());
		} 
		return null;
	}
	
	public IConnectionInstance getConnectionInstancByName(String domainId, String name) {
		Map<String, IConnectionInstance> domainConnections = connections.get(domainId);
		if (domainConnections != null) {
			return domainConnections.get(name);
		} 
		return null;
	}
	public Map<String, IConnectionInstance> getConnectionInstancs(String domainId) {
		return connections.get(domainId);
	}
	
	public void addConnectionInstance(Connections connection, IConnectionInstance instance) {
		Map<String, IConnectionInstance> domainConnections = connections.get(connection.getDomainId());
		if (domainConnections == null) {
			domainConnections = new HashMap<String, IConnectionInstance>();
		}
		domainConnections.put(connection.getName(), instance);
		connections.put(connection.getDomainId(), domainConnections);
		publishState(connection, CONNECTION_STATE.CONNECTED);
	}
	
	public IConnectionInstance removeConnectionInstance(Connections connection) throws Exception {
		Map<String, IConnectionInstance> domainConnections = connections.get(connection.getDomainId());
		if (domainConnections == null) {
			return null;
		}
		IConnectionInstance instance = domainConnections.remove(connection.getName());
		connections.put(connection.getDomainId(), domainConnections);
		publishState(connection, CONNECTION_STATE.DISCONNECTED);
		return instance;
	}
	
	public void publishState(Connections connection, CONNECTION_STATE state) {
		
	}

}

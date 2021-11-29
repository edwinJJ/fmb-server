package com.server.fmb.engine;

import java.util.List;

import com.server.fmb.entity.Connections;


public class Types {

	public interface PropertySpec {
		public String type = null;
		public String label = null;
		public String name = null;
		public String placeholder = null;
		public Object property =null;
	}

	public interface Connector {
		public void ready(List<Connections> connections) throws Exception;
		public boolean connect(Connections connection) throws Exception;
		public void disconnect(Connections connection) throws Exception;
		public PropertySpec[] parameterSpec = null;
		public String[] taskPrefixes = null;
		public String description = null;
		public String help= null;
	}
	
	public enum CONNECTION_STATE {
		CONNECTED,
		DISCONNECTED
	}

	public enum SCENARIO_STATE {
		READY,
		STARTED,
		STOPPED,
		HALTED,
		UNLOADED
	}

}

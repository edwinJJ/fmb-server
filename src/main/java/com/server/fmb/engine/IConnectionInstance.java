package com.server.fmb.engine;

public interface IConnectionInstance {
	
	public Object query(String queryString, String params) throws Exception;
	public void close() throws Exception;
	
}

package com.server.fmb.engine;

import org.springframework.scheduling.annotation.Async;

public interface IConnectionInstance {
	
	@Async
	public Object query(String queryString, String params) throws Exception;
	public Object queryAwait(String queryString, String params) throws Exception;
	public void close() throws Exception;
	
}

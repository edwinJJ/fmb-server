package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Connections;

public interface IConnectionService {
	public List<Connections> getConnections() throws Exception;
}

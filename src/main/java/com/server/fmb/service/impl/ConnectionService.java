package com.server.fmb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IConnectionQueryManager;
import com.server.fmb.entity.Connections;
import com.server.fmb.service.IConnectionService;

@Service
public class ConnectionService implements IConnectionService {

	@Autowired
	IConnectionQueryManager connectionQueryManager;
	
	@Override
	public List<Connections> getConnections() throws Exception {
		return connectionQueryManager.findAll();
	}
	@Override
	public void deleteConnectionByName(String name) throws Exception{
		connectionQueryManager.deleteConnectionByName(name);
	}
	
}

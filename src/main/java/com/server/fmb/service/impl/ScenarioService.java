package com.server.fmb.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IScenarioQueryManager;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IScenarioService;



@Service
public class ScenarioService implements IScenarioService {


	@Autowired
	IScenarioQueryManager scenarioQueryManager;
	
	@Override
	public List<Scenarios> getScenarios() throws Exception {
		return scenarioQueryManager.findAll();
	}

	@Override
	public void deleteScenarioById(String id) throws Exception{
		scenarioQueryManager.deleteById(UUID.fromString(id));
	}
}

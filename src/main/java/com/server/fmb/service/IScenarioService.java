package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Scenarios;

public interface IScenarioService {
	public List<Scenarios> getScenarios() throws Exception;
	
	public void deleteScenarioById(String id) throws Exception;
}

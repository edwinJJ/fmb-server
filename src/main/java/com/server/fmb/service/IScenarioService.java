package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Scenarios;

public interface IScenarioService {
	public List<Scenarios> getScenarios() throws Exception;
	
	public List<Scenarios> getScenariosByActive(Integer active) throws Exception;
	
	public Scenarios getScenarioByName(String scenarioName) throws Exception;
	
	public void updateScenarios(List<Scenarios> scenarioList) throws Exception;
	
	public void deleteScenarioById(List<String> id) throws Exception;
}

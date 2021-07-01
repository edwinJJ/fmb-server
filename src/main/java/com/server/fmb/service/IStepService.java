package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Steps;

public interface IStepService {
	public List<Steps> getStepsByScenarioId(String scenarioId) throws Exception;
	
	public void updateSteps(List<Steps> stepList) throws Exception;
	
	public void deleteStepById(List<String> ids) throws Exception;
}

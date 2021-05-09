package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Steps;

public interface IStepService {
	public List<Steps> getStepsByScenarioId(String id) throws Exception;
}

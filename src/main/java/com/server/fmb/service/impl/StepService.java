package com.server.fmb.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IStepQueryManager;
import com.server.fmb.entity.Steps;
import com.server.fmb.service.IStepService;

@Service
public class StepService implements IStepService{
	
	@Autowired
	IStepQueryManager stepQueryManager;
	
	@Override
	public List<Steps> getStepsByScenarioId(String id) throws Exception{
		return stepQueryManager.getStepsByScenarioId(UUID.fromString(id));
	}
	

}

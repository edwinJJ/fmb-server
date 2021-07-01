package com.server.fmb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IStepQueryManager;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.entity.Steps;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IStepService;
import com.server.fmb.service.IUserService;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;

@Service
public class StepService implements IStepService{
	
	@Autowired
	IStepQueryManager stepQueryManager;
	
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public List<Steps> getStepsByScenarioId(String scenarioId) throws Exception{
		return stepQueryManager.getStepsByScenarioId(scenarioId);
//		return stepQueryManager.getStepsByScenarioId(UUID.fromString(id));
	}

	@Override
	public void updateSteps(List<Steps> stepList) throws Exception {
		int index = 0;
		for (Steps stepUpdate : stepList) {
			if (ValueUtil.isNotEmpty(stepUpdate.getId())) {
				Steps step = stepQueryManager.findById(stepUpdate.getId()).get();
				if (ValueUtil.isEmpty(stepUpdate.getName())) {
					stepUpdate.setName(step.getName());
				}
				if (ValueUtil.isEmpty(stepUpdate.getDescription())) {
					stepUpdate.setDescription(step.getDescription());
				}
				if (ValueUtil.isEmpty(stepUpdate.getTask())) {
					stepUpdate.setTask(step.getTask());
				}
				if (ValueUtil.isEmpty(stepUpdate.getSkip())) {
					stepUpdate.setSkip(step.getSkip());
				}
				if (ValueUtil.isEmpty(stepUpdate.getLog())) {
					stepUpdate.setLog(step.getLog());
				}
				if (ValueUtil.isEmpty(stepUpdate.getConnection())) {
					stepUpdate.setConnection(step.getConnection());
				}
				if (ValueUtil.isEmpty(stepUpdate.getParams())) {
					stepUpdate.setParams(step.getParams());
				}
				if (ValueUtil.isEmpty(stepUpdate.getDomainId())) {
					stepUpdate.setDomainId(step.getDomainId());
				}
				if (ValueUtil.isEmpty(stepUpdate.getScenarioId())) {
					stepUpdate.setScenarioId(step.getScenarioId());
				}
				if (ValueUtil.isEmpty(stepUpdate.getUpdaterId())) {
					stepUpdate.setUpdaterId(step.getUpdaterId());
				}
				stepUpdate.setSequence(index);
				stepUpdate.setUpdatedAt(new Date());
			} else {
				stepUpdate.setId(IdUtil.getUUIDString());
				stepUpdate.setSequence(index);
				stepUpdate.setDomainId(domainService.getDomain().getId());
				stepUpdate.setCreatorId(userService.getAdminUser().getId());
				stepUpdate.setUpdaterId(userService.getAdminUser().getId());
				stepUpdate.setCreatedAt(new Date());
				stepUpdate.setUpdatedAt(new Date());
			}
			index++;
		}
		stepQueryManager.saveAll(stepList);
	}

	@Override
	public void deleteStepById(List<String> ids) throws Exception {
		stepQueryManager.deleteStepById(ids);
	}
	
}

package com.server.fmb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IScenarioQueryManager;
import com.server.fmb.entity.Connections;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IScenarioService;
import com.server.fmb.service.IUserService;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;



@Service
public class ScenarioService implements IScenarioService {

	@Autowired
	IScenarioQueryManager scenarioQueryManager;
	
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public List<Scenarios> getScenarios() throws Exception {
		return scenarioQueryManager.findAll();
	}
	
	@Override
	public void updateScenarios(List<Scenarios> scenarioList) throws Exception {
		for (Scenarios scenarioUpdate : scenarioList) {
			if (ValueUtil.isNotEmpty(scenarioUpdate.getId())) {
				Scenarios scenario = scenarioQueryManager.findById(scenarioUpdate.getId()).get();
				if (ValueUtil.isEmpty(scenarioUpdate.getName())) {
					scenarioUpdate.setName(scenario.getName());
				}
				if (ValueUtil.isEmpty(scenarioUpdate.getDescription())) {
					scenarioUpdate.setDescription(scenario.getDescription());
				}
				if (ValueUtil.isEmpty(scenarioUpdate.getActive())) {
					scenarioUpdate.setActive(scenario.getActive());
				}
				if (ValueUtil.isEmpty(scenarioUpdate.getSchedule())) {
					scenarioUpdate.setSchedule(scenario.getSchedule());
				}
				if (ValueUtil.isEmpty(scenarioUpdate.getTimezone())) {
					scenarioUpdate.setTimezone(scenario.getTimezone());
				}
				if (ValueUtil.isEmpty(scenarioUpdate.getDomainId())) {
					scenarioUpdate.setDomainId(scenario.getDomainId());
				}
				if (ValueUtil.isEmpty(scenarioUpdate.getUpdaterId())) {
					scenarioUpdate.setUpdaterId(scenario.getUpdaterId());
				}
				scenarioUpdate.setUpdatedAt(new Date());
			} else {
				scenarioUpdate.setId(IdUtil.getUUIDString());
				scenarioUpdate.setDomainId(domainService.getDomain().getId());
				scenarioUpdate.setCreatorId(userService.getAdminUser().getId());
				scenarioUpdate.setUpdaterId(userService.getAdminUser().getId());
				if (ValueUtil.isEmpty(scenarioUpdate.getActive())) scenarioUpdate.setActive(0);
				scenarioUpdate.setCreatedAt(new Date());
				scenarioUpdate.setUpdatedAt(new Date());
			}
		}
		scenarioQueryManager.saveAll(scenarioList);
	}

	@Override
	public void deleteScenarioById(List<String> ids) throws Exception{
		scenarioQueryManager.deleteScenarioById(ids);
//		scenarioQueryManager.deleteById(UUID.fromString(id));
	}

}

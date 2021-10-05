package com.server.fmb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IScenarioQueryManager;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IScenarioService;
import com.server.fmb.service.IStepService;
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
	
	@Autowired
	IStepService stepService;
	
	@Override
	public List<Scenarios> getScenarios() throws Exception {
		return scenarioQueryManager.getAllScenarios();
	}
	
	public List<Scenarios> getScenariosByActive(Integer active) throws Exception {
		return scenarioQueryManager.getScenariosByActive(active);
	}
	
	@Override
	public Scenarios getScenarioByName(String scenarioName) throws Exception {
		return scenarioQueryManager.getScenariosByName(scenarioName);
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
				scenarioUpdate.setCreatorId(scenario.getUpdaterId());
				scenarioUpdate.setCreatedAt(scenario.getCreatedAt());
				scenarioUpdate.setUpdaterId(scenario.getUpdaterId());
				scenarioUpdate.setUpdatedAt(new Date());
			} else {
				scenarioUpdate.setId(IdUtil.getUUIDString());
				scenarioUpdate.setDomainId(domainService.getDomain().getId());
				scenarioUpdate.setCreatorId(scenarioUpdate.getUpdaterId());
				scenarioUpdate.setUpdaterId(scenarioUpdate.getUpdaterId());
//				if (ValueUtil.isEmpty(scenarioUpdate.getActive())) scenarioUpdate.setActive(0);
				if (ValueUtil.isEmpty(scenarioUpdate.getActive())) scenarioUpdate.setActive(false);
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

	@Override
	public void setConfigScenarioStep(List<Map<String, Object>> configScenarioStepList) throws Exception {
		setConfigScenario(configScenarioStepList);
		stepService.setConfigStep(configScenarioStepList);
	}
	
	public void setConfigScenario(List<Map<String, Object>> configScenarioStepList) throws Exception {
		for (int i=0; i<configScenarioStepList.size(); i++) {
			Scenarios scenario =  scenarioQueryManager.getScenarioStepById((String) configScenarioStepList.get(i).get("id"));
			if (ValueUtil.isEmpty(scenario)) {
				scenario = new Scenarios();
				scenario.setId((String) configScenarioStepList.get(i).get("id"));
				scenario.setCreatedAt(new Date());
			}
			scenario.setName((String) configScenarioStepList.get(i).get("name"));
			scenario.setDescription((String) configScenarioStepList.get(i).get("description"));
			boolean active = false;
			if ((Integer) configScenarioStepList.get(i).get("active") == 1) {
				active = true;
			}
			scenario.setActive(active);
			scenario.setSchedule((String) configScenarioStepList.get(i).get("schedule"));
			scenario.setDomainId(domainService.getDomain().getId());
			scenario.setUpdatedAt(new Date());
			scenarioQueryManager.save(scenario);
		}
	}

}

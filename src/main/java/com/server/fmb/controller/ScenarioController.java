package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.fmb.constant.Constant;
import com.server.fmb.engine.ScenarioEngine;
import com.server.fmb.engine.ScenarioInstance;
import com.server.fmb.entity.Connections;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IScenarioService;
import com.server.fmb.service.impl.ResultSet;
import com.server.fmb.util.ValueUtil;


@RestController
public class ScenarioController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IScenarioService scenarioService;
	
	@Autowired
	ScenarioEngine scenarioEngine;
	
	// get Scenario list
	@RequestMapping(value="/fetchScenarios", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchScenarios(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Scenarios> scenarioList = new ArrayList<>();
		List<Map<String, Object>> scenarioListMap = new ArrayList<Map<String, Object>>();
		try {
//			ArrayList sorters = (ArrayList)requestBody.get(Constant.SORTERS);
//			if (sorters.size() > 0) {
//				Map<String, String> sortersMap = (Map<String, String>)sorters.get(0);
//				if (sortersMap.get(Constant.NAME).equals(Constant.NAME)) {
//					
//				}
//			} else {
//				int page = (Integer)requestBody.get(Constant.PAGE);
//				int limit = (Integer)requestBody.get(Constant.LIMIT);
//				
//			}
			scenarioList = scenarioService.getScenarios();
			
			for (Scenarios scenario: scenarioList) {
				ObjectMapper objectMapper = new ObjectMapper();
				Map scenarioMap = objectMapper.convertValue(scenario, Map.class);
				if (ValueUtil.isNotEmpty(scenarioEngine.getScenarioInstance((String) scenarioMap.get("domainId"), (String) scenarioMap.get("name")))) {
					scenarioMap.put(Constant.STATE, Constant.READY);
				} else {
					scenarioMap.put(Constant.STATE, Constant.UNLOADED);
				}
				scenarioListMap.add(scenarioMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(scenarioList, false, "scenario", e.toString());
		}
		Map<String, Object> ScenarioResult = new HashMap<String, Object>();
		ScenarioResult.put(Constant.ITEMS, scenarioListMap);
		ScenarioResult.put(Constant.TOTAL, scenarioListMap.size());
		return new ResultSet().getResultSet(ScenarioResult, true, "scenario", null);
	}
	
	// update scenario list
	@RequestMapping(value="/updateScenarios", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateConnections(@RequestBody List<Scenarios> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			scenarioService.updateScenarios(requestBody);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "updateScenarios", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "updateScenarios", null);
	}
	
	// delete Scenario 
	@RequestMapping(value="/deleteScenarioById", method = RequestMethod.POST)
	public @ResponseBody Object deleteScenarioById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<String> ids = (List<String>)requestBody.get("ids");
		try {
			scenarioService.deleteScenarioById(ids);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(null, false, "deleteScenario", e.toString());
		}
		return new ResultSet().getResultSet(null, true, "deleteScenario", null);
	}
	
	// start scenario list
	@RequestMapping(value="/startScenario", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> startScenario(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		String scenarioName = requestBody.get("scenarioName");
//		String instanceName = requestBody.get("instanceName");
		try {
			Scenarios scenario = scenarioService.getScenarioByName(scenarioName);
			scenarioEngine.load(scenarioName, scenario, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(null, false, "startScenario", e.toString());
		}
		Map<String, Object> ScenarioResult = new HashMap<String, Object>();
		ScenarioResult.put(Constant.STATE, "READY");
		return new ResultSet().getResultSet(ScenarioResult, true, "startScenario", null);
	}
	
	// stop scenario list
	@RequestMapping(value="/stopScenario", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> stopScenario(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		String instanceName = requestBody.get("instanceName");
		try {
			Scenarios scenario = scenarioService.getScenarioByName(instanceName);
			scenarioEngine.unload(scenario.getDomainId(), instanceName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(null, false, "stopScenario", e.toString());
		}
		Map<String, Object> ScenarioResult = new HashMap<String, Object>();
		ScenarioResult.put(Constant.STATE, "UNLOADED");
		return new ResultSet().getResultSet(ScenarioResult, true, "stopScenario", null);
	}
}

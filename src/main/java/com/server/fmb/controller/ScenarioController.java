package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.constant.Constant;
import com.server.fmb.entity.Connections;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IScenarioService;
import com.server.fmb.service.impl.ResultSet;


@RestController
public class ScenarioController {

	
	@Autowired
	IScenarioService scenarioService;
	
	// get Scenario list
	@RequestMapping(value="/fetchScenarios", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchScenarios(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Scenarios> scenarioList = new ArrayList<>();
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
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(scenarioList, false, "scenario", e.toString());
		}
		Map<String, Object> ScenarioResult = new HashMap<String, Object>();
		ScenarioResult.put(Constant.ITEMS, scenarioList);
		ScenarioResult.put(Constant.TOTAL, scenarioList.size());
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
			e.printStackTrace();
			return new ResultSet().getResultSet(success, false, "updateConnections", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "updateConnections", null);
	}
	
	// delete Scenario 
	@RequestMapping(value="/deleteScenarioById", method = RequestMethod.POST)
	public @ResponseBody Object deleteScenarioById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<String> ids = (List<String>)requestBody.get("ids");
		try {
			scenarioService.deleteScenarioById(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(null, false, "Scenario", e.toString());
		}
		return new ResultSet().getResultSet(null, true, "Scenario", null);
	}
}

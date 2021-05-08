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
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IScenarioService;


@RestController
public class ScenarioController {

	
	@Autowired
	IScenarioService scenarioService;
	
	// get Scenario list
	@RequestMapping(value="/getScenarios", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getScenarios(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
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
		}
		Map<String, Object> ScenarioResult = new HashMap<String, Object>();
		ScenarioResult.put(Constant.ITEMS, scenarioList);
		ScenarioResult.put(Constant.TOTAL, scenarioList.size());
		return ScenarioResult;
	}
}

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
import com.server.fmb.entity.Steps;
import com.server.fmb.service.IStepService;
import com.server.fmb.service.impl.ResultSet;


@RestController
public class StepController {

	
	@Autowired
	IStepService stepService;
	
	// get Step list
	@RequestMapping(value="/fetchStepByScenarioId", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchStepByScenarioId(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Steps> stepList = new ArrayList<>();
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
			ArrayList filters = (ArrayList)requestBody.get(Constant.FILTERS);
			
			if (filters.size() > 0) {
				Map<String, String> filtersMap = (Map<String, String>)filters.get(0);
				if (filtersMap.get(Constant.NAME).equals(Constant.SCENARIO)) {
					stepList = stepService.getStepsByScenarioId(filtersMap.get(Constant.VALUE));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(stepList, false, "steps", e.toString());
		}
		Map<String, Object> StepResult = new HashMap<String, Object>();
		StepResult.put(Constant.ITEMS, stepList);
		StepResult.put(Constant.TOTAL, stepList.size());
		return new ResultSet().getResultSet(StepResult, true, "steps", null);
	}
	
	// update steps list
	@RequestMapping(value="/updateSteps", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateConnections(@RequestBody List<Steps> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			stepService.updateSteps(requestBody);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(success, false, "updateSteps", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "updateSteps", null);
	}
	
	// delete steps 
	@RequestMapping(value="/deleteStepById", method = RequestMethod.POST)
	public @ResponseBody Object deleteScenarioById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<String> ids = (List<String>)requestBody.get("ids");
		try {
			stepService.deleteStepById(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(null, false, "deleteSteps", e.toString());
		}
		return new ResultSet().getResultSet(null, true, "deleteSteps", null);
	}
}

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
			else {
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> StepResult = new HashMap<String, Object>();
		StepResult.put(Constant.ITEMS, stepList);
		StepResult.put(Constant.TOTAL, stepList.size());
		return new ResultSet().getResultSet(StepResult, true, "steps");
	}
}

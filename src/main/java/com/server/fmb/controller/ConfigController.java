package com.server.fmb.controller;

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

import com.server.fmb.engine.ConnectionManager;
import com.server.fmb.service.IBoardService;
import com.server.fmb.service.IConnectionService;
import com.server.fmb.service.IScenarioService;
import com.server.fmb.service.IStepService;
import com.server.fmb.service.impl.ResultSet;

@RestController
public class ConfigController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IConnectionService connectionService;
	
	@Autowired
	IScenarioService scenarioService;
	
	@Autowired
	IStepService stepService;
	
	@Autowired
	IBoardService boardService;
	
	// mcs config 파일 내용 받아서 업데이트
	@RequestMapping(value="/setAutoMcsConfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> setConfigConnScenarioStep(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			connectionService.setConfigConnection((List<Map<String, Object>>) requestBody.get("connections"));
			scenarioService.setConfigScenarioStep((List<Map<String, Object>>) requestBody.get("scenarios"));
			boardService.setConfigBoardModel((List<Map<String, String>>) requestBody.get("boards"));
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "updateConnections", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "updateConnections", null);
	}

}

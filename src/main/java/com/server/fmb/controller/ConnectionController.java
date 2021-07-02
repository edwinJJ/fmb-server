/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    ConnectionController.java
 *  Description:  	Connection Controller 파일
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
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
import com.server.fmb.engine.ConnectionManager;
import com.server.fmb.engine.IConnectionInstance;
import com.server.fmb.engine.connector.OracleConnector;
import com.server.fmb.entity.Connections;
import com.server.fmb.entity.Domains;
import com.server.fmb.service.IConnectionService;
import com.server.fmb.service.impl.ResultSet;

@RestController
public class ConnectionController {
	
	@Autowired
	IConnectionService connectionService;
	
	@Autowired
	ConnectionManager connectionManager;
	
	@Autowired
	OracleConnector oracleConnectorService;
	
	// get connection list
	@RequestMapping(value="/getConnections", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getConnections(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Connections> connectionList = new ArrayList<>();
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
			connectionList = connectionService.getConnections();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(connectionList, false, "connectionList", e.toString());
		}
		Map<String, Object> connectionResult = new HashMap<String, Object>();
		connectionResult.put(Constant.ITEMS, connectionList);
		connectionResult.put(Constant.TOTAL, connectionList.size());
		return new ResultSet().getResultSet(connectionResult, true, "connectionList", null);
	}
	
	// update connection list
	@RequestMapping(value="/updateConnections", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateConnections(@RequestBody List<Connections> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			connectionService.updateConnections(requestBody);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(success, false, "updateConnections", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "updateConnections", null);
	}

	// delete connection list
	@RequestMapping(value="/deleteConnetcionByName", method = RequestMethod.POST)
	public @ResponseBody Object deleteScenarioById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		List<String> names = (List<String>)requestBody.get("names");
		try {
			connectionService.deleteConnectionByName(names);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(success, false, "deleteConnections", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "deleteConnections", null);
	}
	
	// database connect
	@RequestMapping(value="/connect", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> connect(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		String name = requestBody.get("name");
		String state = Constant.DISCONNECTED;
		try {
			Connections connection = connectionService.getConnectionByName(name);
			oracleConnectorService.connect(connection);
			state = Constant.CONNECTED;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(state, false, "connect", e.toString());
		}
		Map<String, Object> connectionResult = new HashMap<String, Object>();
		connectionResult.put("state", state);
		return new ResultSet().getResultSet(connectionResult, true, "connect", null);
	}
	
	// database disconnect
	@RequestMapping(value="/disconnect", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> disconnect(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		String name = requestBody.get("name");
		String state = Constant.DISCONNECTED;
		try {
			Connections connection = connectionService.getConnectionByName(name);
			oracleConnectorService.disconnect(connection);
			state = Constant.DISCONNECTED;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultSet().getResultSet(state, false, "disconnect", e.toString());
		}
		Map<String, Object> connectionResult = new HashMap<String, Object>();
		connectionResult.put("state", state);
		return new ResultSet().getResultSet(connectionResult, true, "disconnect", null);
	}
}

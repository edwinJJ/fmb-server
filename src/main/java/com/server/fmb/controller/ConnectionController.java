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
import com.server.fmb.entity.Connections;
import com.server.fmb.service.IConnectionService;

@RestController
public class ConnectionController {
	
	@Autowired
	IConnectionService connectionService;
	
	// get connection list
	@RequestMapping(value="/getConnections", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getConnections(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Connections> ConnectionList = new ArrayList<>();
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
			ConnectionList = connectionService.getConnections();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> connectionResult = new HashMap<String, Object>();
		connectionResult.put(Constant.ITEMS, ConnectionList);
		connectionResult.put(Constant.TOTAL, ConnectionList.size());
		return connectionResult;
	}
	
}

/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    BoardController.java
 *  Description:  	Board Controller 파일
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.service.IEqpService;
import com.server.fmb.service.impl.ResultSet;

@RestController
public class EqpController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IEqpService eqpService;
	
	// get board 
	@RequestMapping(value = "/getEqpNamesByIdType/{id}/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getMultiLanguagesByIdType(@PathVariable("id") String id, @PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) {
		String eqpNames = "";
		try {
			eqpNames = eqpService.getEqpNamesByIdType(id, type);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(eqpNames, false, "eqpName", e.toString());
		}
		return new ResultSet().getResultSet(eqpNames, true, "eqpName", null);
	}
	
	@RequestMapping(value = "/getStockerPopupData/{id}/{empty}/{unknownRule}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getStockerPopupData(@PathVariable("id") String id,
			@PathVariable("empty") String empty,
			@PathVariable("unknownRule") String unknownRule,HttpServletRequest request, HttpServletResponse response) {
		Object stockerPopupData = null;
		try {
			stockerPopupData = eqpService.getStockerPopupData(id, empty, unknownRule);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(stockerPopupData, false, "stockerPopupData", e.toString());
		}
		return new ResultSet().getResultSet(stockerPopupData, true, "stockerPopupData", null);
	}
	
	@RequestMapping(value = "/getStockerEqpPortPopupData/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getStockerEqpPortPopupData(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
		Object portPopupData = null;
		try {
			portPopupData = eqpService.getStockerEqpPortPopupData(name);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(portPopupData, false, "portPopupData", e.toString());
		}
		return new ResultSet().getResultSet(portPopupData, true, "portPopupData", null);
	}
}

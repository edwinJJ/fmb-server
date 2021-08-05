/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    GroupController.java
 *  Description:  	Group Controller 파일
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Groups;
import com.server.fmb.service.IGroupService;
import com.server.fmb.service.impl.ResultSet;

@RestController
public class GroupController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IGroupService groupService;
	
	// create group 
	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
	public @ResponseBody Object createGroup(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Groups group = new Groups();
		try {
			group = groupService.setGroup(requestBody);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(group, false, "group", e.toString());
		}
		return new ResultSet().getResultSet(group, true, "group", null);
	}
	
	// update group
	@RequestMapping(value="/updateGroup", method = RequestMethod.PUT)
	public @ResponseBody Object updateGroup(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			groupService.setGroup(requestBody);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "success", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "success", null);
	}
	
	
	// delete group 
	@RequestMapping(value="/deleteGroup/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object deleteGroup(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			groupService.deleteGroup(id);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "delete", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "delete", null);
	}
	
	// get group list 
	@RequestMapping(value = "/fetchGroupList", method = RequestMethod.GET)
	public @ResponseBody Object fetchGroupList(HttpServletRequest request) {
		List<Groups> groups = new ArrayList<Groups>();
		try {
			groups = groupService.fetchGroupList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(groups, false, "groups", e.toString());
		}
		return new ResultSet().getResultSet(groups, true, "groups", null);
	}
	
	// get play-group list 
	@RequestMapping(value = "/fetchPlayGroupList", method = RequestMethod.GET)
	public @ResponseBody Object fetchPlayGroupList(HttpServletRequest request) {
		List<Groups> groups = new ArrayList<Groups>();
		try {
			groups = groupService.fetchPlayGroupList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(groups, false, "groups", e.toString());
		}
		return new ResultSet().getResultSet(groups, true, "groups", null);
	}
	
	// get group 
	@RequestMapping(value = "/fetchGroupById/{groupId}", method = RequestMethod.GET)
	public @ResponseBody Object fetchBoardById(@PathVariable("groupId") String groupId, HttpServletRequest request) {
		Groups group = new Groups();
		try {
			group = groupService.getGroupById(groupId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(group, false, "group", e.toString());
		}
		return new ResultSet().getResultSet(group, true, "group", null);
	}
}

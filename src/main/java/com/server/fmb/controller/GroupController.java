package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.entity.Groups;
import com.server.fmb.service.IGroupService;

@RestController
public class GroupController {

	@Autowired
	IGroupService groupService;
	
	// create group 
	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
	public @ResponseBody Groups createGroup(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Groups group = new Groups();
		try {
			group = groupService.setGroup(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}
	
	// update group
	@RequestMapping(value="/updateGroup", method = RequestMethod.PUT)
	public @ResponseBody boolean updateGroup(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			groupService.setGroup(requestBody);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	// delete group 
	@RequestMapping(value="/deleteGroup/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean deleteGroup(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			groupService.deleteGroup(id);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	// get group list 
	@RequestMapping(value = "/fetchGroupList", method = RequestMethod.GET)
	public @ResponseBody List<Groups> fetchGroupList(HttpServletRequest request) {
		List<Groups> Groups = new ArrayList<Groups>();
		try {
			Groups = groupService.fetchGroupList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Groups;
	}
	
	// get play-group list 
	@RequestMapping(value = "/fetchPlayGroupList", method = RequestMethod.GET)
	public @ResponseBody List<Groups> fetchPlayGroupList(HttpServletRequest request) {
		List<Groups> Groups = new ArrayList<Groups>();
		try {
			Groups = groupService.fetchPlayGroupList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Groups;
	}
}

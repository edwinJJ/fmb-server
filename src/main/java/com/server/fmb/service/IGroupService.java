package com.server.fmb.service;

import java.util.List;
import java.util.Map;

import com.server.fmb.entity.Groups;

public interface IGroupService {
	public Groups getGroupById(String groupId) throws Exception;
	public Groups setGroup(Map<String, String> groupMap) throws Exception;
	public void deleteGroup(String id) throws Exception;
	public List<Groups> fetchGroupList() throws Exception;
	public List<Groups> fetchPlayGroupList() throws Exception;
}

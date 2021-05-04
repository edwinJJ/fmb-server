package com.server.fmb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IGroupQueryManager;
import com.server.fmb.entity.Groups;
import com.server.fmb.service.IGroupService;

@Service
public class GroupService implements IGroupService {
	
	@Autowired
	IGroupQueryManager groupQueryManager;

	@Override
	public List<Groups> fetchGroupList() throws Exception {
		return groupQueryManager.findAll();
	}

	@Override
	public List<Groups> fetchPlayGroupList() throws Exception {
		return groupQueryManager.fetchPlayGroupList();
	}
	
	
	
}

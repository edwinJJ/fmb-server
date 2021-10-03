/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    GroupService.java
 *  Description:  	Group 관련 서비스 Interface Impl
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IGroupQueryManager;
import com.server.fmb.entity.Groups;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IGroupService;
import com.server.fmb.service.IUserService;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;

@Service
public class GroupService implements IGroupService {
	
	@Autowired
	IGroupQueryManager groupQueryManager;
	
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public Groups getGroupById(String groupId) throws Exception {
		return groupQueryManager.findById(groupId).get();
//		return groupQueryManager.findById(UUID.fromString(groupId)).get();
	}
	
	@Override
	public Groups setGroup(Map<String, String> groupMap) throws Exception {
		if (!ValueUtil.isEmpty(groupMap.get("id"))) {
			Groups group = getGroupById(groupMap.get("id"));
			group.setName(groupMap.get("name"));
			group.setDescription(groupMap.get("description"));
			group.setUpdatedAt(new Date());
			group.setUpdaterId(groupMap.get("userName"));
//			group.setUpdaterId(UUID.fromString(groupMap.get("updaterId")));
			return groupQueryManager.save(group);
		} else {
			Groups group = new Groups();
			group.setId(IdUtil.getUUIDString());
//			group.setId(IdUtil.getUUID());
			group.setName(groupMap.get("name"));
			group.setDescription(groupMap.get("description"));
			group.setCreatedAt(new Date());
			group.setUpdatedAt(new Date());
			group.setDomainId(domainService.getDomain().getId());
			group.setCreatorId(groupMap.get("userName"));
			group.setUpdaterId(groupMap.get("userName"));
			return groupQueryManager.save(group);
		}
	}

	@Override
	public void deleteGroup(String id) throws Exception {
		groupQueryManager.deleteById(id);
//		groupQueryManager.deleteById(UUID.fromString(id));
	}
	
	@Override
	public List<Groups> fetchGroupList() throws Exception {
		return groupQueryManager.fetchGroupList();
	}

	@Override
	public List<Groups> fetchPlayGroupList() throws Exception {
		return groupQueryManager.fetchPlayGroupList();
	}

}

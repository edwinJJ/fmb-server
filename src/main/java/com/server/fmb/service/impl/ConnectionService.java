/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    ConnectionService.java
 *  Description:  	Attachment 관련 서비스 Interface Impl
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IConnectionQueryManager;
import com.server.fmb.entity.Connections;
import com.server.fmb.service.IConnectionService;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IUserService;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;

@Service
public class ConnectionService implements IConnectionService {

	@Autowired
	IConnectionQueryManager connectionQueryManager;
	
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public List<Connections> getConnections() throws Exception {
		return connectionQueryManager.getConnections();
	}
	
	@Override
	public void updateConnections(List<Connections> connectionList) throws Exception {
		for (Connections connectionUpdate : connectionList) {
			if (ValueUtil.isNotEmpty(connectionUpdate.getId())) {
				Connections connection = connectionQueryManager.findById(connectionUpdate.getId()).get();
				if (ValueUtil.isEmpty(connectionUpdate.getName())) {
					connectionUpdate.setName(connection.getName());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getDescription())) {
					connectionUpdate.setDescription(connection.getDescription());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getType())) {
					connectionUpdate.setType(connection.getType());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getEndpoint())) {
					connectionUpdate.setEndpoint(connection.getEndpoint());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getActive())) {
					connectionUpdate.setActive(connection.getActive());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getParams())) {
					connectionUpdate.setParams(connection.getParams());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getDomainId())) {
					connectionUpdate.setDomainId(connection.getDomainId());
				}
				if (ValueUtil.isEmpty(connectionUpdate.getUpdaterId())) {
					connectionUpdate.setUpdaterId(connection.getUpdaterId());
				}
				connectionUpdate.setUpdatedAt(new Date());
			} else {
				connectionUpdate.setId(IdUtil.getUUIDString());
				connectionUpdate.setDomainId(domainService.getDomain().getId());
				connectionUpdate.setCreatorId(userService.getAdminUser().getId());
				connectionUpdate.setUpdaterId(userService.getAdminUser().getId());
				connectionUpdate.setCreatedAt(new Date());
				connectionUpdate.setUpdatedAt(new Date());
			}
		}
		connectionQueryManager.saveAll(connectionList);
	}
	
	@Override
	public void deleteConnectionByName(List<String> names) throws Exception{
		connectionQueryManager.deleteConnectionByName(names);
	}

}

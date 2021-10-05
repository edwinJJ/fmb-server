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
import java.util.Map;
import java.util.Optional;

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
	public List<Connections> getConnectionsByActive(Integer active) throws Exception {
		return connectionQueryManager.getConnectionsByActive(active);
	}
	
	@Override
	public Connections getConnectionByName(String name) throws Exception {
		return connectionQueryManager.getConnectionByName(name);
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
				connectionUpdate.setCreatorId(connection.getUpdaterId());
				connectionUpdate.setCreatedAt(connection.getCreatedAt());
				connectionUpdate.setUpdaterId(connection.getUpdaterId());
				connectionUpdate.setUpdatedAt(new Date());
			} else {
				connectionUpdate.setId(IdUtil.getUUIDString());
				connectionUpdate.setDomainId(domainService.getDomain().getId());
				connectionUpdate.setCreatorId(connectionUpdate.getUpdaterId());
				connectionUpdate.setUpdaterId(connectionUpdate.getUpdaterId());
//				if (ValueUtil.isEmpty(connectionUpdate.getActive())) connectionUpdate.setActive(0); 
				if (ValueUtil.isEmpty(connectionUpdate.getActive())) connectionUpdate.setActive(false); 
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

	@Override
	public void setConfigConnection(List<Map<String, Object>> configConnectionList) throws Exception {
		for (int i=0; i<configConnectionList.size(); i++) {
			Connections connection =  connectionQueryManager.getConnectionById((String) configConnectionList.get(i).get("id"));
			if (ValueUtil.isEmpty(connection)) {
				connection = new Connections();
				connection.setId((String) configConnectionList.get(i).get("id"));
				connection.setCreatedAt(new Date());
			}
			connection.setName((String) configConnectionList.get(i).get("name"));
			connection.setDescription((String) configConnectionList.get(i).get("description"));
			connection.setType((String) configConnectionList.get(i).get("type"));
			connection.setEndpoint((String) configConnectionList.get(i).get("endpoint"));
			boolean active = false;
			if ((Integer) configConnectionList.get(i).get("active") == 1) {
				active = true;
			}
			connection.setActive(active);
			connection.setParams((String) configConnectionList.get(i).get("params"));
			connection.setDomainId(domainService.getDomain().getId());
			connection.setUpdatedAt(new Date());
			connectionQueryManager.save(connection);
		}
	}

}

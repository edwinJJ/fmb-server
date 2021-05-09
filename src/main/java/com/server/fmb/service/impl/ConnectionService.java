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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IConnectionQueryManager;
import com.server.fmb.entity.Connections;
import com.server.fmb.service.IConnectionService;

@Service
public class ConnectionService implements IConnectionService {

	@Autowired
	IConnectionQueryManager connectionQueryManager;
	
	@Override
	public List<Connections> getConnections() throws Exception {
		return connectionQueryManager.findAll();
	}
	
}

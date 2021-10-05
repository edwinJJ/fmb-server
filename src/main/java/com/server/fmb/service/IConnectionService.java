/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    IConnectionService.java
 *  Description:  	Connection 관련 서비스 Interface
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service;

import java.util.List;
import java.util.Map;

import com.server.fmb.entity.Connections;

public interface IConnectionService {
	public List<Connections> getConnections() throws Exception;
	public List<Connections> getConnectionsByActive(Integer active) throws Exception;
	public Connections getConnectionByName(String name) throws Exception;
	public void updateConnections(List<Connections> connectionList) throws Exception;
	public void deleteConnectionByName(List<String> names) throws Exception;
	public void setConfigConnection(List<Map<String, Object>> configConnectionList) throws Exception;
}

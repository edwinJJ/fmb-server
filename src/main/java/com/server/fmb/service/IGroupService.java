/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    IGroupService.java
 *  Description:  	Group 관련 서비스 Interface
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
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

/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    UserService.java
 *  Description:  	User 관련 서비스 Interface
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IUserQueryManager;
import com.server.fmb.entity.Users;
import com.server.fmb.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserQueryManager iUserQueryManager;
	
	@Override
	public Users getAdminUser() throws Exception {
		return iUserQueryManager.getAllUsers().get(0);
	}
	
}

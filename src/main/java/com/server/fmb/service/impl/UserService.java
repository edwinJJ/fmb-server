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
		return iUserQueryManager.findAll().get(0);
	}
	
}

package com.server.fmb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IDomainQueryManager;
import com.server.fmb.entity.Domains;
import com.server.fmb.service.IDomainService;

@Service
public class DomainService implements IDomainService {
	
	@Autowired
	IDomainQueryManager iDomainQueryManager;

	@Override
	public Domains getDomain() throws Exception {
		return iDomainQueryManager.findAll().get(0);
	}
	
	
}

/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    DomainService.java
 *  Description:  	Domain 관련 서비스 Interface Impl
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
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
		return iDomainQueryManager.getAllDomains().get(0);
	}
	
	
}

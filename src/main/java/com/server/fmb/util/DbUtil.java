/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    DBUtil.java
 *  Description:  	Database에 관련된 기능들을 제공하는 클래스 
 *  Authors:        J.I Cho
 *  Update History:
 *                  2021.05.17 : Created by J.I. Cho
 *
 */
package com.server.fmb.util;

import org.springframework.beans.factory.annotation.Autowired;

public class DbUtil {
	
	@Autowired
	EnvUtil envUtil;

	public String getMyDatabaseType() {
		String dbType = null;
		String dbUrl = envUtil.getProperty("spring.datasource.url");
		if (ValueUtil.isEmpty(dbUrl)) {
			return dbType;
		}
		try {
			dbType = dbUrl.split("\\:\\/\\/")[0].split("\\:")[1].toLowerCase();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbType;
		
	}
		
}

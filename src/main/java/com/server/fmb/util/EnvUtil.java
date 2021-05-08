/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    EnvUtil.java
 *  Description:  	Environment Property와 관련된 다양한 기능들을 제공하는 클래스 
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

//import com.miflow.util.BeanUtil;

@Configuration
//@PropertySource("classpath:application.properties")
//@RunWith(SpringRunner.class)
public class EnvUtil {

	@Autowired
	private Environment env;
	
	private Environment getEnvironment() {
//		if (ValueUtil.isEmpty(env)) {
//			env = BeanUtil.get(Environment.class);
//		}
		return env;
	}

	public String getProperty(String key) {
		return getEnvironment().getProperty(key).trim();
	}

	public String getProperty(String key, String defaultValue) {
		if (ValueUtil.isEmpty(getEnvironment().getProperty(key))) {
			return defaultValue;
		} else {
			return getEnvironment().getProperty(key).trim();
		}
	}
	
	public boolean containsProperty(String key) {
		return getEnvironment().containsProperty(key);
	}
}

/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    WebConfig.java
 *  Description:  	Web MVC Service 설정 파일
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.server.fmb.constant.Constant;
import com.server.fmb.util.EnvUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private String uploadImagesPath;
	
	private String uploadImagesUrlPath;
	
	@Autowired
	EnvUtil envUtil;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://192.168.0.11:3000", "http://192.168.0.29:3000")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowCredentials(true);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		uploadImagesPath = 
				envUtil.getProperty(Constant.FILE_REPOSITORY) + 
				envUtil.getProperty(Constant.ROOT_DIVISION) + 
				"/Temps";
		uploadImagesUrlPath = "/images";
		registry
			.addResourceHandler(uploadImagesUrlPath + "/**")
			.addResourceLocations("file:" + uploadImagesPath + "/");
	}
}

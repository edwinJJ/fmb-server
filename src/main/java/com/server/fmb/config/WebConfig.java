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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.server.fmb.constant.Constant;
import com.server.fmb.interceptor.BizContextHandlerInterceptor;
import com.server.fmb.util.EnvUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private String uploadImagesPath;
	
	private String uploadImagesUrlPath;
	
	@Autowired
	private EnvUtil envUtil;
	
	@Autowired
	private BizContextHandlerInterceptor bizContextHandlerInterceptor; 
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedOrigins("*");
		
//		String allowedOrigins = envUtil.getProperty(Constant.WEBCONFIG_ALLOWED_ORIGINS);
//		String[] allowedOriginsArray = allowedOrigins.split(",");
//		registry.addMapping("/**")
//			.allowedOrigins("http://localhost:16060)
//			.allowedMethods("GET", "POST", "PUT", "DELETE")
//			.allowCredentials(true);
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
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(bizContextHandlerInterceptor)
		.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/images/**");
	}
}

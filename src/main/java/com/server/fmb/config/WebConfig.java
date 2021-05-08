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
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowCredentials(false);
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

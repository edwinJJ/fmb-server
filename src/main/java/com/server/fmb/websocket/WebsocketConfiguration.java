package com.server.fmb.websocket;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebsocketConfiguration {
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
	  return new ServerEndpointExporter();
	}

	@Bean
	public UIWebsocketManager uiWebsocketServerEndpoint() {
	  return new UIWebsocketManager();
	}
	
	@Bean
	public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
	    return new ServletContextAware() {

	        @Override
	        public void setServletContext(ServletContext servletContext) {
	            ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
	            serverEndpointExporter.setApplicationContext(applicationContext);
	            try {
	                serverEndpointExporter.afterPropertiesSet();
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }               
	        }           
	    };
	}
}

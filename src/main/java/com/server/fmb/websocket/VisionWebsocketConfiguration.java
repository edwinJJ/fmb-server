/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    VisionWebSocketConfiguration.java
 *  Description:  	Vision System WebSocket 구성을 정의하는 클래스 
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 
 * @author kmyu
 *	SpringBoot 에서 Websocket 서버(@serverEndPoint)를 사용할 수 있게끔 하기 위한 클래스
 */

//@Configuration
public class VisionWebsocketConfiguration {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
	  return new ServerEndpointExporter();
	}

	@Bean
	public VisionWebsocketManager visionWebsocketServerEndpoint() {
	  return new VisionWebsocketManager();
	}
}

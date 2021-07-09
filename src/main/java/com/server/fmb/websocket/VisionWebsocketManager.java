/*
 *  Copyright (c) 2020 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    VisionWebSocketManager.java
 *  Description:  	Vision System에게 WebSocket 서비스를 제공하는 클래스 
 *  Authors:        Y.S. Jung
 *  Update History:
 *                  2020.03.14 : Created by Y.S. Jung
 *
 */
package com.server.fmb.websocket;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.server.fmb.util.ValueUtil;

@ServerEndpoint("/ws/fmbServer/{clientKey}")
public class VisionWebsocketManager {
	
	public static int ERROR_CODE_INVALID_VISION_ID = 201;
	/**
	 * Logger
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 비전시스템을 관리하기 위한 컬렉션
     */
    private static Map<String, Map<String, Object>> clientsMap = new HashMap<String, Map<String, Object>>();
    
    @OnOpen
    public void onOpen(@PathParam("clientKey") String clientKey, Session session) {
        try {
        	this.logger.info("New session opened: " + clientKey);
//        	replyInvalidVisionIdMessage(session);
	        this.registerSession(clientKey, session);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);        	
        }        
    }
    
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
        	String clientKey = this.getSessionClientKey(session.getId());
            this.logger.info("Received message [" + message + "] from session [" + clientKey + "]!");
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
        }
    }

    @OnError
    public void error(Session session, Throwable th) {
        this.logger.error("Error on session : [" + this.getSessionClientKey(session.getId()) + "]");
    }

    @OnClose
    public void closedConnection(Session session) {
    	String clientKey = this.getSessionClientKey(session.getId());
    	this.unregisterSession(session.getId());
        this.logger.info("Closed session : [" + clientKey + "]");
    }

    private void registerSession(String clientKey, Session session) {
    	Map<String, Object> sessionMap = new HashMap<String, Object>();
    	sessionMap.put("clientKey", clientKey);
    	sessionMap.put("session", session);
    	clientsMap.put(session.getId(), sessionMap);
    }
    
    private void unregisterSession(String sessionId) {
		if(!clientsMap.isEmpty()) {
			clientsMap.remove(sessionId);
		}
    }
    private String getSessionClientKey(String sessionId) {
		Map<String, Object> sessionMap = clientsMap.get(sessionId);
		if(!sessionMap.isEmpty()) {
			return (String)sessionMap.get("clientKey");
		}
		return null;
    }
    private void replyInvalidClientKeyMessage(Session session) {
    	try {
    		JSONObject messageObject = new JSONObject();
    		messageObject.put("errorCode",ERROR_CODE_INVALID_VISION_ID);
        	session.getBasicRemote().sendText(messageObject.toJSONString());    		
    	} catch (Exception e) {
            this.logger.error(e.getMessage(), e);    		
    	}
    }
    
    public static Session getSessionBySystemId(String systemId) {
    	for (Map.Entry<String, Map<String, Object>> elem : clientsMap.entrySet()) {
			Map<String, Object> sessionMap = (Map<String, Object>)elem.getValue();
			if (systemId.contentEquals((String)sessionMap.get("clientKey"))) {
				return (Session)sessionMap.get("session");
			}    				    		
    	}
    	return null;
    }
    
    public static boolean isSystemConnected(String systemId) {
    	boolean exists = false;
    	for (Map.Entry<String, Map<String, Object>> elem : clientsMap.entrySet()) {
			Map<String, Object> sessionMap = (Map<String, Object>)elem.getValue();
			if (systemId.contentEquals((String)sessionMap.get("clientKey"))) {
				exists = true;
				break;
			}    				    		
    	}
    	return exists;
    }
    public static void sendMessageToSystem(String systemId, String message) throws Exception {
    	Session session = getSessionBySystemId(systemId);
    	if (ValueUtil.isEmpty(session)) {
    		throw new Exception("Session closed : [" + systemId + "]");
    	}
    	session.getBasicRemote().sendText(message);    	
    }

}

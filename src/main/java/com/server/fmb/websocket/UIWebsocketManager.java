/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    UIWebSocketManager.java
 *  Description:  	UI Client들에게 WebSocket 서비스를 제공하는 클래스 
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2020.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.server.fmb.util.ValueUtil;

/**
 * 웹 소켓 매니저 
 * 
 *  ** Sample Client **
  	var baseUrl = 'ws://localhost:8080/ws/websocket/kmyu@smartworks.net';
	var websocket = new WebSocket(baseUrl);
	websocket.onopen = function(ev) {
        console.log('connect!');
    };
    websocket.onclose = function(ev) {
        console.log('connect closed ev=', ev);
    };
    websocket.onmessage = function(ev) {
        if (ev.data) {
            console.log(data);
        }
    };
 */
@ServerEndpoint("/ws/websocket/{clientKey}")
public class UIWebsocketManager {
	
	/**
	 * Logger
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 클라이언트를 관리하기 위한 컬렉션
     * @param map<clientKey, Session>
     */
    private static Map<String, Session> clientsSessionMap = new HashMap<String, Session>();
    
//    private static IOrganizationManager orgManager;
//    /**
//     * 클라이언트가 접속을 하게되면 호출되는 메소드
//     * 
//     * @param session
//     */
//    @OnOpen
//    public void onOpen(@PathParam("clientKey") String clientKey, Session session) {
//        clientsSessionMap.put(clientKey, session);
//        this.logger.info("New session opened: " + clientKey);
//        try {
//        	String ipAddress = null;
//        	String macAddress = null;
//            if (orgManager == null) {
//            	orgManager = BeanUtil.get(IOrganizationManager.class);
//            }
//            orgManager.logLoginHistory(clientKey, true, ipAddress, macAddress);        	
//        } catch(Exception e) {
//        	e.printStackTrace();
//        }
//    }
//    
//    /**
//     * 클라이언트가 메세지를 보내면 호출되는 메소드
//     * 
//     * @param session
//     * @param message
//     */
//    @OnMessage
//    public void onMessage(Session session, String message) {
//        try {
//            this.logger.info("Received message [" + message + "] from session [" + session.getId() + "]!");
//            if (message != null) {
//            	WebsocketInEvent inEvent = WebsocketEventFactory.createWebsocketInEvent(message);
//        		this.registerSession(session, inEvent);
//        		//eventHandler(inEvent);
//            }
//        } catch (Exception ex) {
//            this.logger.error(ex.getMessage(), ex);
//        }
//    }
//
//    /**
//     * 통신 중 에러 발생 시 호출되는 메소드
//     * 
//     * @param session
//     * @param th
//     */
//    @OnError
//    public void error(Session session, Throwable th) {
//        this.logger.error("Error on session : [" + session.getId() + "]");
//    }
//
//    /**
//     * 클라이언트와 연결이 끊길때 호출되는 메소드
//     * 
//     * @param session
//     */
//    @OnClose
//    public void closedConnection(Session session) {
//    	String sessionId = session.getId();
//        String clientKey = session.getPathParameters().get("clientKey");
//    	this.unregisterSession(sessionId);
//        this.logger.info("Closed session : [" + clientKey + "]");
//        if (ValueUtil.isNotEmpty(clientKey)) {
//            try {
//                orgManager.logLoginHistory(clientKey, false, null, null);
//            } catch (Exception e) {
//            }        	
//        }
//    }
//
//    public boolean checkIfClientIsOpen(String clientKey) {
//		if (ValueUtil.isEmpty(clientKey) || ValueUtil.isEmpty(clientsSessionMap)) {
//            return false;
//        }
//		Session session = clientsSessionMap.get(clientKey);
//		return session != null && session.isOpen();
//    }
    
    /**
     * 메시지 전송 
     * 
     * @param message
     * @throws Exception
     */
    public void sendMessage(String clientKey, String message) {
    	if (ValueUtil.isEmpty(clientKey) || ValueUtil.isEmpty(message) || ValueUtil.isEmpty(clientsSessionMap)) {
            return;
        }
    	Session session = clientsSessionMap.get(clientKey);
        if (session != null) {
            if (!session.isOpen()) {
                this.logger.error("Closed session: [" + session.getId() + "]");
            } else {
                try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					this.logger.error("Failed to send message by websocket", e);
				}
            }
        }
    }
    
//    /**
//     * 모든 클라이언트에게 메세지 전송
//     * 
//     * @param message
//     */
//    public void sendAll(Object data) {
//    	if(data != null) {
//    		String message = FormatUtil.toJsonString(data);
//    		this.sendAll(message);
//    	}
//    }
//
//    /**
//     * 모든 클라이언트에게 메세지 전송
//     * 
//     * @param message
//     */
//    public void sendAll(String message) {
//    	if(clientsSessionMap.isEmpty()) {
//    		return;
//    	}
//        Iterator<String> keyIter = clientsSessionMap.keySet().iterator();
//        int count = 0;
//        while (keyIter.hasNext()) {
//        	String key = keyIter.next();
//        	Session session = clientsSessionMap.get(key);
//            if (!session.isOpen()) {
//                this.logger.error("Closed session : [" + session.getId() + "]");
//            } else {
//                try {
//					session.getBasicRemote().sendText(message);
//				} catch (IOException e) {
//					this.logger.error("Failed to send message", e);
//				}
//            }
//        }
//        this.logger.info("Sending " + message + " to [" + clientsSessionMap.size() + "] clients");
//    }
//    
//    /**
//     * 지정한 유저만 제외하고 모든 클라이언트에게 메세지 전송
//     * 
//     * @param message
//     */
//    public void sendAllExceptUserId(String userId, Object data) {
//    	if(data != null) {
//    		String message = FormatUtil.toJsonString(data);
//    		this.sendAllExceptUserId(userId, message);
//    	}
//    }
//
//    /**
//     * 지정한 유저만 제외하고 모든 클라이언트에게 메세지 전송
//     * 
//     * @param message
//     */
//    public void sendAllExceptUserId(String userId, String message) {
//    	if(clientsSessionMap.isEmpty()) {
//    			return;
//    	}
//        Iterator<String> keyIter = clientsSessionMap.keySet().iterator();
//        int count = 0;
//        while (keyIter.hasNext()) {
//        	String key = keyIter.next();
//        	if (key.equals(userId)) {
//        		continue;
//        	}
//        	Session session = clientsSessionMap.get(key);
//            if (!session.isOpen()) {
//                this.logger.error("Closed session : [" + session.getId() + "]");
//            } else {
//                try {
//					session.getBasicRemote().sendText(message);
//					count++;
//				} catch (IOException e) {
//					this.logger.error("Failed to send message", e);
//				}
//            }
//        }
//        if (count > 0)
//        this.logger.info("Sending " + message + " to [" + count + "] clients");
//    }
//    
//    /**
//     * 세션 등록 
//     * 
//     * @param session
//     */
//    private void registerSession(Session session, WebsocketInEvent inEvent) {
//    	if (ValueUtil.isNotEmpty(inEvent) && ValueUtil.isNotEmpty(inEvent.getClientKey())) {
//    		clientsSessionMap.put(inEvent.getClientKey(), session);
//    	}
//    }
//    
//    /**
//     * 세션 제거 
//     * 
//     * @param sessionId
//     */
//    private void unregisterSession(String sessionId) {
//		Session targetSession = null;
//		String targetClientKey = null;
//		if(!clientsSessionMap.isEmpty()) {
//			Iterator clientKeyItr = clientsSessionMap.keySet().iterator();
//	        while (clientKeyItr.hasNext()) {
//        		String clientKey = (String)clientKeyItr.next();
//        		Session session = clientsSessionMap.get(clientKey);
//        		if (ValueUtil.isEqual(session.getId(), sessionId)) {
//        			targetSession = session;
//        			targetClientKey = clientKey;
//        			break;
//        		}
//	        }
//		}
//		if (ValueUtil.isNotEmpty(targetSession)) {
//			try {
//			targetSession.close();
//		} catch (Exception e) {
//		}
//		}
//		if (ValueUtil.isNotEmpty(targetClientKey)) {
//			clientsSessionMap.remove(targetClientKey);
//		}
//    }
}

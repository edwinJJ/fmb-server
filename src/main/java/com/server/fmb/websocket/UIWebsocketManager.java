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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.fmb.engine.ScenarioInstance;
import com.server.fmb.util.FormatUtil;
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
//@ServerEndpoint("/ws/fmbServer/{clientKey}")
@ServerEndpoint(value="/ws/fmbServer/{clientKey}", encoders = MessageEncoder.class, decoders = MessageDecoder.class) 
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
    private static Map<String, String> clientsTagMap = new HashMap<String, String>();
    
    /**
     * 클라이언트가 접속을 하게되면 호출되는 메소드
     * 
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("clientKey") String clientKey, Session session) {
    	try {
	    	System.out.println("client is now connected........................");
	    	System.out.println("clientKey : " + clientKey);
	    	System.out.println("session : " + clientsSessionMap.get(clientKey));
	    	if (clientsSessionMap.get(clientKey) != null) {
	    		System.out.println("already exist! sessionId : " + clientsSessionMap.get(clientKey).getId());
	    	}
	    	if (ValueUtil.isEmpty(clientsSessionMap.get(clientKey))) {
	    		clientsSessionMap.put(clientKey, session);
	    		System.out.println("new sessionId : " + clientsSessionMap.get(clientKey).getId());
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 클라이언트가 메세지를 보내면 호출되는 메소드
     * 
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            this.logger.info("Received message [" + message + "] from session [" + session.getId() + "]!");
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(message);
            if(((String)jsonObj.get("type")).equals("start")) {
	            JSONObject payload = (JSONObject) jsonObj.get("payload");
	            String queryString = (String) payload.get("query");
	            String tagname = queryString.substring(1, queryString.length()-1);
	            String keyName = (String)jsonObj.get("key");
	            clientsTagMap.put(keyName, tagname);
            }
            if (message != null) {
//            	WebsocketInEvent inEvent = WebsocketEventFactory.createWebsocketInEvent(message);
//        		this.registerSession(session, inEvent);
        		//eventHandler(inEvent);

//            	List<Map<String, String>> messageList = new ArrayList<Map<String, String>>();
//            	Map<String, String> messageMap = new HashMap<String, String>();
//            	messageMap.put("first", "hello fmb!!!");
//            	messageMap.put("second", "bye fmb!!!");
//            	Map<String, String> messageMap2 = new HashMap<String, String>();
//            	messageMap2.put("third", "again hi fmb!!!");
//            	messageMap2.put("fourth", "again bye fmb!!!");
//            	messageList.add(messageMap);
//            	messageList.add(messageMap2);
//            	
//            	sendMessage(session, null, null, messageList);
//            	sendMessage(session, null, messageMap.toString(), null);
            }
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
        }
    }

    /**
     * 통신 중 에러 발생 시 호출되는 메소드
     * 
     * @param session
     * @param th
     */
    @OnError
    public void error(Session session, Throwable th) {
        this.logger.error("Error on session : [" + session.getId() + "]");
        th.printStackTrace();
    }

    /**
     * 클라이언트와 연결이 끊길때 호출되는 메소드
     * 
     * @param session
     */
    @OnClose
    public void closedConnection(Session session) {
    	try {
	    	String sessionId = session.getId();
	        String clientKey = session.getPathParameters().get("clientKey");
	    	this.unregisterSession(sessionId);
	        this.logger.info("Closed session : [" + clientKey + "]");
	        if (ValueUtil.isNotEmpty(clientKey)) {
	        	clientsSessionMap.remove(clientKey);
	        	clientsTagMap.remove(clientKey);
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public boolean checkIfClientIsOpen(String clientKey) {
    	try {
			if (ValueUtil.isEmpty(clientKey) || ValueUtil.isEmpty(clientsSessionMap)) {
	            return false;
	        }
			Session session = clientsSessionMap.get(clientKey);
			return session != null && session.isOpen();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    /**
     * 메시지 전송 
     * 
     * @param message
     * @throws Exception
     */
    public void sendMessage(Session session, String clientKey, String message, Object messageObject) {
    	try {
//    		this.logger.info("Send Message session [\" + session.getId() + \"]");
	    	if ((ValueUtil.isEmpty(session) && ValueUtil.isEmpty(clientKey)) || 
	    		(ValueUtil.isEmpty(message) && ValueUtil.isEmpty(messageObject)) || 
	    		ValueUtil.isEmpty(clientsSessionMap)) {
	            return;
	        }
//	    	Session session = clientsSessionMap.get(clientKey);
	    	if (ValueUtil.isEmpty(session)) session = clientsSessionMap.get(clientKey);
	        if (session != null) {
	            if (!session.isOpen()) {
	                this.logger.error("Closed session: [" + session.getId() + "]");
	            } else {
	                try {
	                	if (ValueUtil.isNotEmpty(message)) {
	                		session.getBasicRemote().sendText(message);
	                	} else {
	                		session.getBasicRemote().sendObject(messageObject);
	                	}
					} catch (IOException e) {
						this.logger.error("Failed to send message by websocket", e);
					}
	            }
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 모든 클라이언트에게 메세지 전송
     * 
     * @param message
     */
    public void sendAll(Object data) {
    	if(data != null) {
    		String message = null;
    		Object messageObject = null;
    		ObjectMapper mapper = new ObjectMapper();
    		try {
    			String key = (String) ((Map<String, Object>) data).get("key");
//    			this.logger.info("Ready to Send Message key [" + key + "]");
    			if (key.equals("scenario-instance-state")) {
        			Map<String, Object> dataObjectMap = (Map<String, Object>) ((Map<String, Object>) data).get("data");
        			ScenarioInstance ScenarioInstance = (ScenarioInstance) dataObjectMap.get("scenarioQueueState");

        			Map<String, Object> scenarioInstanceMap = new HashMap<String, Object>(); 
        			scenarioInstanceMap.put("domainId", ScenarioInstance.getDomainId());
        			scenarioInstanceMap.put("instanceName", ScenarioInstance.getInstanceName());
//        			scenarioInstanceMap.put("message", ScenarioInstance.getMessage());
        			scenarioInstanceMap.put("scenarioName", ScenarioInstance.getScenarioName());
//        			scenarioInstanceMap.put("schedule", ScenarioInstance.getSchedule());
//        			scenarioInstanceMap.put("timezone", ScenarioInstance.getTimezone());
//        			scenarioInstanceMap.put("context", ScenarioInstance.getContext());
//        			scenarioInstanceMap.put("cronjob", ScenarioInstance.getCronjob());
//        			scenarioInstanceMap.put("disposer", ScenarioInstance.getDisposer());
//        			scenarioInstanceMap.put("lastStep", ScenarioInstance.getLastStep());
//        			scenarioInstanceMap.put("nextStep", ScenarioInstance.getNextStep());
//        			scenarioInstanceMap.put("rounds", ScenarioInstance.getRounds());
//        			scenarioInstanceMap.put("steps", ScenarioInstance.getSteps());
//        			scenarioInstanceMap.put("subScenarioInstances", ScenarioInstance.getSubScenarioInstance());
        			messageObject = scenarioInstanceMap;
    			} else if (key.equals("data")) {
        			Map<String, Object> dataObjectMap = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) data).get("data")).get("data");
//        			String domainId = (String) dataObjectMap.get("domainId");
//        			String tag = (String) dataObjectMap.get("tag");
//        			Object dataObject = (Object) dataObjectMap.get("data");
//        			message = domainId + ", " + tag + "," + dataObject + "," + key;
        			messageObject = dataObjectMap;
    			}
//    			message = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		this.sendAll(null, messageObject);
    	}
    }

    /**
     * 모든 클라이언트에게 메세지 전송
     * 
     * @param message
     */
    public void sendAll(String message, Object messageObject) {
    	try {
	    	if(clientsSessionMap.isEmpty()) {
//	    		this.logger.info("Client Session Not Exist");
	    		return;
	    	}
	        Iterator<String> keyIter = clientsSessionMap.keySet().iterator();
	        int count = 0;
	        while (keyIter.hasNext()) {
	        	String key = keyIter.next();
	        	Session session = clientsSessionMap.get(key);
	        	
	        	String tag = ((Map<String, String>)messageObject).get("tag");
	        	
	        	if (tag.equals(clientsTagMap.get(key))) {
		            if (!session.isOpen()) {
		                this.logger.error("Closed session : [" + session.getId() + "]");
		            } else {
		                try {
		                	this.logger.info("Send Message session [" + session.getId() + "]" + " , tag : " + tag);
	//						session.getBasicRemote().sendText(message);
							if (ValueUtil.isNotEmpty(message)) {
		                		session.getBasicRemote().sendText(message);
		                	} else {
		                		session.getBasicRemote().sendObject(messageObject);
		                	}
							
						} catch (IOException e) {
							this.logger.error("Failed to send message", e);
						}
		            }
	        	}
	        }
//	        this.logger.info("Sending " + message + " to [" + clientsSessionMap.size() + "] clients");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 지정한 유저만 제외하고 모든 클라이언트에게 메세지 전송
     * 
     * @param message
     */
    public void sendAllExceptUserId(String userId, Object data) {
    	try {
	    	if(data != null) {
	    		String message = FormatUtil.toJsonString(data);
	    		this.sendAllExceptUserId(userId, message);
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * 지정한 유저만 제외하고 모든 클라이언트에게 메세지 전송
     * 
     * @param message
     */
    public void sendAllExceptUserId(String userId, String message) {
    	try {
	    	if(clientsSessionMap.isEmpty()) {
	    			return;
	    	}
	        Iterator<String> keyIter = clientsSessionMap.keySet().iterator();
	        int count = 0;
	        while (keyIter.hasNext()) {
	        	String key = keyIter.next();
	        	if (key.equals(userId)) {
	        		continue;
	        	}
	        	Session session = clientsSessionMap.get(key);
	            if (!session.isOpen()) {
	                this.logger.error("Closed session : [" + session.getId() + "]");
	            } else {
	                try {
						session.getBasicRemote().sendText(message);
						count++;
					} catch (IOException e) {
						this.logger.error("Failed to send message", e);
					}
	            }
	        }
	        if (count > 0)
	        this.logger.info("Sending " + message + " to [" + count + "] clients");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 세션 등록 
     * 
     * @param session
     */
//    private void registerSession(Session session, WebsocketInEvent inEvent) {
//    	if (ValueUtil.isNotEmpty(inEvent) && ValueUtil.isNotEmpty(inEvent.getClientKey())) {
//    		clientsSessionMap.put(inEvent.getClientKey(), session);
//    	}
//    }
    
    /**
     * 세션 제거 
     * 
     * @param sessionId
     */
    private void unregisterSession(String sessionId) {
    	try {
			Session targetSession = null;
			String targetClientKey = null;
			if(!clientsSessionMap.isEmpty()) {
				Iterator clientKeyItr = clientsSessionMap.keySet().iterator();
		        while (clientKeyItr.hasNext()) {
	        		String clientKey = (String)clientKeyItr.next();
	        		Session session = clientsSessionMap.get(clientKey);
	        		if (ValueUtil.isEqual(session.getId(), sessionId)) {
	        			targetSession = session;
	        			targetClientKey = clientKey;
	        			break;
	        		}
		        }
			}
			if (ValueUtil.isNotEmpty(targetSession)) {
				try {
					targetSession.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (ValueUtil.isNotEmpty(targetClientKey)) {
				clientsSessionMap.remove(targetClientKey);
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}

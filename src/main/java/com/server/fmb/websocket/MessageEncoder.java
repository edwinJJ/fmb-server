package com.server.fmb.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<Object> {
	
	private static Gson gson = new Gson();

	@Override
	public String encode(Object message) throws EncodeException {
		return gson.toJson(message);
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
		// custom initialization logic
	}

	@Override
	public void destroy() {
		// close resources
	}
}
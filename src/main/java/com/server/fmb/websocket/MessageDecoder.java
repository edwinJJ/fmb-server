package com.server.fmb.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<Object> {

	private static Gson gson = new Gson();

	@Override
	public Object decode(String s) throws DecodeException {
		return gson.fromJson(s, Object.class);
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
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
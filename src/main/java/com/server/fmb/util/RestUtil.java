package com.server.fmb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
	/**
	 * 토큰 접근 프로퍼티
	 */
	private static final String ENV_DW_TOKEN_PROPERTY = "server.token";
	/**
	 * 토큰 접근 프로퍼티
	 */
	private static final String HEADER_KEY_AUTH_TYPE = "Authorization-Type";
	/**
	 * 토큰 접근 프로퍼티
	 */
	private static final String HEADER_VAL_TOKEN = "token";
	/**
	 * 토큰 접근 프로퍼티
	 */
	private static final String HEADER_KEY_AUTH_KEY = "Authorization-Key";
	
	/**
	 * token
	 */
	private static String token;

	/**
	 * RestTemplate을 생성 
	 * 
	 * @return
	 */
	public static RestTemplate newRestTemplate() {
		RestTemplate rest = new RestTemplate(new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				super.prepareConnection(connection, httpMethod);
				//connection.setRequestProperty(HEADER_KEY_AUTH_TYPE, HEADER_VAL_TOKEN);
				//connection.setRequestProperty(HEADER_KEY_AUTH_KEY, getToken());
			}
		});
		
		return rest;
	}
	
	/**
	 * 서버용 토큰 
	 * 
	 * @return
	 */
	public static String getToken() {
		return token;
	}
	
	public static JSONObject readRequestBody(HttpServletRequest request) throws IOException, Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String buffer;
		while ((buffer = input.readLine()) != null) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			builder.append(buffer);
		}
		String stringValue = builder.toString();
		return FormatUtil.parseJsonObject(stringValue);
    }
	
	public static String getServiceUrlFromRequest(HttpServletRequest request) throws Exception {
		if (ValueUtil.isEmpty(request)) {
			return null;
		}
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int portNumber = request.getServerPort();
        return scheme + "://" + serverName + ":" + portNumber;
	}
	
	public static Object getRequest(String urlString) throws Exception {
		if (ValueUtil.isEmpty(urlString)) {
			return null;
		}
		StringBuffer output = new StringBuffer();
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				output.append(inputLine);
			}
			br.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	public static Object postRequest(String urlString, Map<String, Object> requestBody) throws Exception {
		if (ValueUtil.isEmpty(urlString)) {
			return null;
		}
		StringBuffer output = new StringBuffer();
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
		    OutputStream os = conn.getOutputStream();
		    os.write(FormatUtil.toJsonString(requestBody).getBytes());
		    os.flush();
		    os.close();
	
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				output.append(inputLine);
			}
			br.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}

}

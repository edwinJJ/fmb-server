package com.server.fmb.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpCookieUtil {
	
	public static Cookie getCookie(HttpServletRequest request, String name) throws Exception {
		try {
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				for(int i = 0; i< cookies.length ; ++i) {
					Cookie cookie = cookies[i]; 
					if (name.equals(cookie.getName())) {
						return cookie;
					}
				} 
			} 
		} catch (Exception e) {
			throw e;
		}
		return null; 
	}
	
	public static Cookie[] getAllCookie(HttpServletRequest request) throws Exception { 
		return request.getCookies();
	}
	
	public static String getCookieName(Cookie cookie) throws Exception {
		return cookie.getName();
	}
	
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain, String path) { 
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		if ( domain != null && domain.length() > 0 ) cookie.setDomain(domain); 
		if ( path != null && path.length() > 0 ) cookie.setPath(path); 
		response.addCookie(cookie);
	}
	
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) { 
		setCookie(response, name, value, maxAge, null, null);
	}
	
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain, String path) { 
		// remove cookie         
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for(int i = 0; i< cookies.length ; ++i) { 
				Cookie cookie = cookies[i]; 
				if ( name.equals(cookie.getName()) ) { 
					Cookie newCookie = new Cookie(cookies[i].getName(), ""); 
					newCookie.setMaxAge(0);
					// if ( !StringHelper.isNullOrEmpty(domain) )                     
					// newCookie.setDomain(domain);                     
					// if ( !StringHelper.isNullOrEmpty(path) )                     
					// newCookie.setPath(path);                     
					response.addCookie(newCookie); 
					break;
				}
			} 
		} 
	} 
	
	public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response, String domain, String path) { 
		// remove all cookie         
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for(int i = 0; i< cookies.length ; ++i) { 
				Cookie cookie = cookies[i]; 
				cookie.setMaxAge(0); 
				// if ( !StringHelper.isNullOrEmpty(domain) )                 
				// cookie.setDomain(domain);                 
				// if ( !StringHelper.isNullOrEmpty(path) )                 
				// cookie.setPath(path);                 
				response.addCookie(cookie);
			}
		} 
	}
}

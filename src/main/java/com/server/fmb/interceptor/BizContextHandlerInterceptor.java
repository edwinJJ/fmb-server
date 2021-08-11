package com.server.fmb.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.server.fmb.util.HttpCookieUtil;

@Component
public class BizContextHandlerInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			logger.info("Request URI ===> " + request.getRequestURI());
//			BizContext.setUserId(HttpCookieUtil.getCookieName(HttpCookieUtil.getCookie(request, "userId")));
//			logger.info("Request userId ===> " + BizContext.getUserId());
		} catch (NullPointerException e) {
//			logger.error("Cookie or Cookie.getName() is Null : " + e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	@Override 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// 작업을 끝내고 마지막에 실행된다. todo code;
	}
	
}

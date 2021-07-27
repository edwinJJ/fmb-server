package com.server.fmb.interceptor;

import java.util.HashMap;

/** 
* Biz의 Context 정보를 저장한다.
* 현재 Thread 에서만 값이 유효하다. 
* ThreadPool 사용시 이전 값이 남아있으므로, BizContext.remove() 를 반드시 호출해야 한다. 
* */
public class BizContext extends HashMap<String, Object> {
	
	private final static InheritableThreadLocal<BizContext> local = new InheritableThreadLocal(); 

	private static final String KEY_NAME_USERID = "userId";
	
	private BizContext() {
	}

	public static String getUserId() { 
		if ( local.get() == null ) local.set(new BizContext()); 
		return (String) local.get().get(KEY_NAME_USERID); 
	} 
	
	public static void setUserId(String userId) { 
		if ( local.get() == null ) local.set(new BizContext());
		local.get().put(KEY_NAME_USERID, userId);
	}
	
	/** 
	* ThreadLocal 의 값을 지운다. 
	* Thread Pool 을 사용하는 경우에는 값이 남아 있으므로, 반드시 Thread 사용이 끝난 후 삭제해 주어야 한다. 
	*/
	
	public static void remove() { 
		local.remove();
	}
	
	public <T> T get(String key, Class<T> klass) { 
		return klass.cast(get(key));
	}
	
	public <T> T get(Class<T> klass) {
		return klass.cast(get(klass.getName())); 
	}
}
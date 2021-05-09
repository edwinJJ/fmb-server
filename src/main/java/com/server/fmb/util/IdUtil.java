/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    IdUtil.java
 *  Description:  	ID값과 관련된 다양한 기능들을 제공하는 클래스 
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.util;

import java.net.InetAddress;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IdUtil {
	
	public static UUID getUUID() {
		return UUID.randomUUID();
	}
	
	public static String getUUIDString() {
		return UUID.randomUUID().toString();
	}
	
	private static IdUtil instance;

	private static final int IP;
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}
	private static short counter = (short) 0;
	private static final int JVM = (int) ( System.currentTimeMillis() >>> 8 );
	
	public static IdUtil getInstance() {
		if (instance == null)
			instance = new IdUtil();
		return instance;
	}

	public static int toInt( byte[] bytes ) {
		int result = 0;
		for (int i=0; i<4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4-formatted.length(), 4, formatted );
		return buf.toString();
	}

	protected short getHiTime() {
		return (short)(System.currentTimeMillis() >>> 32 );
	}

	protected int getLoTime() {
		return (int)System.currentTimeMillis();
	}

	protected short getCount() {
		synchronized(IdUtil.class) {
			if (counter<0) counter=0;
			return counter++;
		}
	}

	public String generate() {
		return new StringBuffer(32)
		.append(format(IP))
		.append(format(JVM))
		.append(format(getHiTime()))
		.append(format(getLoTime()))
		.append(format(getCount()))
		.toString();
	}
	
//	public static String createId(String prefix) {
//		return ValueUtil.toNotNull(prefix) + (prefix == null || prefix.equals("") ? "" : "_") + createId();
//	}
	
//	public static String createId() {
//		return com.miflow.workflow.util.id.UUID.randomUUID().toStringWithoutDash();
//	}
	
	public static String createShortId(String prefix) {
		return ValueUtil.toNotNull(prefix) + (prefix == null || prefix.equals("") ? "" : "_") + Long.toHexString(new Date().getTime()) + Long.toHexString(getRandomNumberInRange(0, 15));
	}
	
	public static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}

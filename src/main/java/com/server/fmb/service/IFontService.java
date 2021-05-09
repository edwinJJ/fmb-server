/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    IFontService.java
 *  Description:  	Font 관련 서비스 Interface
 *  Authors:        J.H. Lee
 *  Update History:
 *                  2021.05.07 : Created by J.H. Lee
 *
 */
package com.server.fmb.service;

import java.util.List;
import java.util.Map;

import com.server.fmb.entity.Fonts;

public interface IFontService {

	
	
	public List<Fonts> getFonts(int start, int end) throws Exception;
	public List<Fonts> getFontsByCategory(String category,int start, int end) throws Exception;
	
	public Map<String, Object> setFonts(Map<String, String> fontMap) throws Exception;
	public void deleteFonts(String fontId) throws Exception;
	public Fonts fetchFontsById(String id) throws Exception;
}

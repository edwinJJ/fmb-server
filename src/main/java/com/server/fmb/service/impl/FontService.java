/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    FontService.java
 *  Description:  	Font 관련 서비스 Interface Impl
 *  Authors:        J.H. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.H. Lee
 *
 */
package com.server.fmb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IFontQueryManager;
import com.server.fmb.entity.Fonts;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IFontService;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;


@Service
public class FontService implements IFontService{

	@Autowired
	IFontQueryManager fontQueryManager;
	
	@Autowired
	IDomainService domainService;
	
	public List<Fonts> getFonts(int start, int end) throws Exception{
		if(start == 0  && end ==0) {
			return fontQueryManager.findAll();
		}
		return fontQueryManager.getFonts(start, end);
	}
	public List<Fonts> getFontsByCategory(String category,int start, int end) throws Exception{

		return fontQueryManager.getFontsByCategory(category, start, end);
	}
	
	public Map<String, Object> setFonts(Map<String, String> fontMap) throws Exception{
		Fonts font = new Fonts();
		Map<String, Object> fontResult = new HashMap<String, Object>();
		
		if(ValueUtil.isNotEmpty(fontMap.get("id"))) {
			font = fetchFontsById((String)fontMap.get("id"));
//			font.setName((String)fontMap.get("name"));
//			font.setProvider((String)fontMap.get("provider"));
//			font.setUri((String)fontMap.get("uri"));
//			font.setPath((String)fontMap.get("path"));
			font.setActive(Boolean.valueOf(fontMap.get("active")));
			font.setUpdatedAt(new Date());
			font.setDomainId(domainService.getDomain().getId());
//			font.setUpdaterId(domainService.getDomain().getId());
			
			fontResult.put("updateFont", fontQueryManager.save(font));
			
			return fontResult;
		}
		else {
			font.setId(IdUtil.getUUIDString());
//			font.setId(IdUtil.getUUID());
			font.setName((String)fontMap.get("name"));
			font.setProvider("provider");
			font.setUri((String)fontMap.get("uri"));
			font.setPath((String)fontMap.get("path"));
			font.setActive(Boolean.valueOf(fontMap.get("active")));
			font.setUpdatedAt(new Date());
			font.setCreatedAt(new Date());
			font.setDomainId(domainService.getDomain().getId());
//			font.setUpdaterId(domainService.getDomain().getId());
//			font.setCreatorId(domainService.getDomain().getId());
			
			fontResult.put("createFont", fontQueryManager.save(font));
			
			return fontResult;
		}
		
		
	}
	public void deleteFonts(String fontId) throws Exception{
		fontQueryManager.deleteById(fontId);
//		fontQueryManager.deleteById(UUID.fromString(fontId));
	}
	public Fonts fetchFontsById(String id) throws Exception{
		return (fontQueryManager.findById(id)).get();
//		return (fontQueryManager.findById(UUID.fromString(id))).get();
	}
	
	
}

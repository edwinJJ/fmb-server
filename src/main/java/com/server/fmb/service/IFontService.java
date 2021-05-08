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

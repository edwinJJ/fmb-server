package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.constant.Constant;
import com.server.fmb.entity.Fonts;
import com.server.fmb.service.IFontService;
import com.server.fmb.util.ValueUtil;

@RestController
public class FontController {

	
	
	@Autowired
	IFontService fontService;
	
	
	@RequestMapping(value="/fetchFontList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchFontList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response){
		List<Fonts> fonts = new ArrayList<>();
		try {
			
			ArrayList filters = (ArrayList)requestBody.get(Constant.FILTERS);
			Map<String, Integer> pagination = (Map<String, Integer>) requestBody.get(Constant.PAGINATION);
			int start = 0;
			int end = 0;
			
			
			
			if(ValueUtil.isNotEmpty(pagination)) {
				start = (Integer) pagination.get(Constant.PAGE);
				end = (Integer)pagination.get(Constant.LIMIT);
				if (start == 1) start = 0;
				
			}
			
			fonts = fontService.getFonts(start, end);
			
//			if (filters.size() > 0) {
//				Map<String, String> filtersMap = (Map<String, String>)filters.get(0);
//				if (filtersMap.get(Constant.NAME).equals(Constant.CATEGORY)) {
//					fonts = fontService.getFontsByCategory(filtersMap.get(Constant.VALUE),start, (Integer)pagination.get(Constant.LIMIT));
//				}
//			}
//			else {
//				fonts = fontService.getFonts(start, end);
//			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> fontResult = new HashMap<String, Object>();
		
		fontResult.put("items", fonts);
		fontResult.put("total", fonts.size());
		
		return fontResult;
	}
	
	// create fonts 
	@RequestMapping(value="/setFont", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> setFont(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> fontResult = new HashMap<String, Object>();
		try {
			fontResult = fontService.setFonts(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fontResult;
	}
	
	
	// delete font 
	@RequestMapping(value="/deleteFont/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean deleteFont₩(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			fontService.deleteFonts(id);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
}

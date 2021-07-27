/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    AttachmentController.java
 *  Description:  	Attachment Controller 파일
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.server.fmb.constant.Constant;
import com.server.fmb.entity.Attachments;
import com.server.fmb.service.IAttachmentService;
import com.server.fmb.service.impl.ResultSet;

@RestController
public class AttachmentController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IAttachmentService attachmentService;
	
	
	@RequestMapping(value="/fetchAttachmentList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchAttachmentList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response){
		List<Attachments> attachments = new ArrayList<>();
		try {
			
			ArrayList filters = (ArrayList)requestBody.get(Constant.FILTERS);
			Map<String, Integer> pagination = (Map<String, Integer>) requestBody.get(Constant.PAGINATION);
			int start = (Integer) pagination.get(Constant.PAGE);
			if (start == 1) start = 0;
			
			if (filters.size() > 0) {
				Map<String, String> filtersMap = (Map<String, String>)filters.get(0);
				if (filtersMap.get(Constant.NAME).equals(Constant.CATEGORY)) {
					attachments = attachmentService.getAttachmentsByCategory(filtersMap.get(Constant.VALUE),start, (Integer)pagination.get(Constant.LIMIT));
				}
			}
			else {
				attachments = attachmentService.getAttachments(start, (Integer)pagination.get(Constant.LIMIT));
			}

		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(attachments, false, "attachments", e.toString());
		}
		
		Map<String, Object> attachmentResult = new HashMap<String, Object>();
		
		attachmentResult.put("items", attachments);
		attachmentResult.put("total", attachments.size());
		return new ResultSet().getResultSet(attachmentResult, true, "attachments", null);
	}
	
	// create Attachments 
	@RequestMapping(value="/createAttachment", method = RequestMethod.POST)
	public @ResponseBody Object createAttachments(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Attachments attachment = new Attachments();
		try {
			attachment = attachmentService.setAttachments(requestBody);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(attachment, false, "attachment", e.toString());
		}
		return new ResultSet().getResultSet(attachment, true, "attachment", null);
	}
	
	
	// delete attachment 
	@RequestMapping(value="/deleteAttachment/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object deleteAttachments(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		Attachments attachment = new Attachments();
		try {
			attachment = attachmentService.deleteAttachments(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(attachment, false, "attachment", e.toString());
		}
		return new ResultSet().getResultSet(attachment, true, "attachment", null);
	}
	
	@RequestMapping(value = "/uploadTempFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void uploadTempFile(HttpServletRequest request, @RequestParam("file") MultipartFile uploader, HttpServletResponse response) throws Exception {
		try {
			attachmentService.uploadTempFile(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
    
    @RequestMapping(value = "/deleteFile/{path}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Object deleteFile(@PathVariable("path") String path, HttpServletRequest request) throws Exception {
    	boolean result = false;
    	try {
    		result = attachmentService.deleteFile(path);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return new ResultSet().getResultSet(result, false, "deleteFile", e.toString());
    	}
    	return new ResultSet().getResultSet(result, true, "deleteFile", null);
    }
    
    @RequestMapping(value = "/downloadFile/{path}/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public void downloadFile(@PathVariable("path") String path, @PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		attachmentService.downloadFile(request, response, path, fileName);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    	}
    }
    
    // delete attachment 
	@RequestMapping(value="/deleteAttachmentByRef/{ref}", method = RequestMethod.DELETE)
	public @ResponseBody Object deleteAttachmentByRef(@PathVariable("ref") String ref, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			attachmentService.deleteAttachmentByRef(ref);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "deleteAttachmentByRef", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "deleteAttachmentByRef", null);
	}
	
}

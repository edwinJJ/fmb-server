/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    IAttachmentService.java
 *  Description:  	Attachment 관련 서비스 Interface
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.server.fmb.entity.Attachments;

public interface IAttachmentService {

	public List<Attachments> getAttachments(int start, int end) throws Exception;
	public List<Attachments> getAttachmentsByCategory(String category,int start, int end) throws Exception;
	
	public Attachments setAttachments(Map<String, String> boardMap) throws Exception;
	public Attachments deleteAttachments(String boardId) throws Exception;
	public Attachments fetchAttachmentsById(String id) throws Exception;

	public void uploadTempFile(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public boolean deleteFile(String path) throws Exception;
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String path, String fileName) throws Exception;
	
	public void deleteAttachmentByRef(String ref) throws Exception;
}

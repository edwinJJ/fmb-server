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
	
	public void deleteAttachmentByRef(String ref) throws Exception;
}

package com.server.fmb.service;

import java.util.List;
import java.util.Map;

import com.server.fmb.entity.Attachments;

public interface IAttachmentService {

	public List<Attachments> getAttachments(int start, int end) throws Exception;
	public List<Attachments> getAttachmentsByCategory(String category,int start, int end) throws Exception;
	
	public Attachments setAttachments(Map<String, String> boardMap) throws Exception;
	public void deleteAttachments(String boardId) throws Exception;
	public Attachments fetchAttachmentsById(String id) throws Exception;
	public void deleteAttachmentByRef(String ref) throws Exception;
	
}

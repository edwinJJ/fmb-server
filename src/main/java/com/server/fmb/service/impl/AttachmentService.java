package com.server.fmb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IAttachmentQueryManager;
import com.server.fmb.entity.Attachment;
import com.server.fmb.service.IAttachmentService;

@Service
public class AttachmentService implements IAttachmentService {

	
	@Autowired
	IAttachmentQueryManager attachmentQueryManager;
	
	
	public List<Attachment> getAttachments(int start, int end) throws Exception{
		return attachmentQueryManager.getAttachments(start, end);
	}
}

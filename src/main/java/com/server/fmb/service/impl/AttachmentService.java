package com.server.fmb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IAttachmentQueryManager;
import com.server.fmb.entity.Attachments;
import com.server.fmb.service.IAttachmentService;
import com.server.fmb.util.CommonUtil;
import com.server.fmb.util.ValueUtil;

@Service
public class AttachmentService implements IAttachmentService {

	
	@Autowired
	IAttachmentQueryManager attachmentQueryManager;
	
	
	@Override
	public List<Attachments> getAttachments(int start, int end) throws Exception{
		return attachmentQueryManager.getAttachments(start, end);
	}
	@Override
	public List<Attachments> getAttachmentsByCategory(String category,int start, int end) throws Exception{
		return attachmentQueryManager.getAttachmentsByCategory(category,start, end);
	}
	
	
	@Override
	public Attachments setAttachments(Map<String, String> attachmentMap) throws Exception{
		Attachments attachment = new Attachments();
		
		if(ValueUtil.isNotEmpty(attachmentMap.get("id"))){
			attachment = fetchAttachmentsById(attachmentMap.get("id"));
			attachment.setCategory(attachmentMap.get("category"));
			attachment.setName(attachmentMap.get("name"));
			attachment.setDescription(attachmentMap.get("description"));
			attachment.setMimetype(attachmentMap.get("mimetype"));
			attachment.setEncoding(attachmentMap.get("encoding"));
			attachment.setRefBy(attachmentMap.get("refBy"));
			attachment.setPath(attachmentMap.get("path"));
			attachment.setSize(attachmentMap.get("size"));
			attachment.setUpdatedAt(new Date());
			attachment.setDomainId(UUID.fromString(attachmentMap.get("dominId")));
			attachment.setUpdaterId(UUID.fromString(attachmentMap.get("updatedId")));
			
			
			return attachmentQueryManager.save(attachment);
		}
		else {
			attachment.setId(CommonUtil.getUUID());
			attachment = fetchAttachmentsById(attachmentMap.get("id"));
			attachment.setCategory(attachmentMap.get("category"));
			attachment.setName(attachmentMap.get("name"));
			attachment.setDescription(attachmentMap.get("description"));
			attachment.setMimetype(attachmentMap.get("mimetype"));
			attachment.setEncoding(attachmentMap.get("encoding"));
			attachment.setRefBy(attachmentMap.get("refBy"));
			attachment.setPath(attachmentMap.get("path"));
			attachment.setSize(attachmentMap.get("size"));
			attachment.setUpdatedAt(new Date());
			attachment.setCreatedAt(new Date());
			attachment.setDomainId(UUID.fromString(attachmentMap.get("dominId")));
			attachment.setUpdaterId(UUID.fromString(attachmentMap.get("updatedId")));
			attachment.setCreatorId(UUID.fromString(attachmentMap.get("createdAt")));
			
			return attachmentQueryManager.save(attachment);
		}
		
	}
	
	@Override
	public void deleteAttachments(String boardId) throws Exception{
		attachmentQueryManager.deleteById(UUID.fromString(boardId));
	}
	
	@Override
	public Attachments fetchAttachmentsById(String id) throws Exception{
		return (attachmentQueryManager.findById(UUID.fromString(id))).get();
	}
}

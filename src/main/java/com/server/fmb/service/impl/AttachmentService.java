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
		if(start == 0  && end ==0) {
			return attachmentQueryManager.findAll();
		}
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
			attachment.setCategory((String)attachmentMap.get("category"));
			attachment.setName((String)attachmentMap.get("name"));
			attachment.setDescription((String)attachmentMap.get("description"));
			attachment.setMimetype((String)attachmentMap.get("mimetype"));
			attachment.setEncoding((String)attachmentMap.get("encoding"));
			attachment.setRefBy((String)attachmentMap.get("refBy"));
			attachment.setPath((String)attachmentMap.get("path"));
			attachment.setSize((String)attachmentMap.get("size"));
			attachment.setUpdatedAt(new Date());
			attachment.setDomainId(UUID.fromString(attachmentMap.get("dominId")));
			attachment.setUpdaterId(UUID.fromString(attachmentMap.get("updatedId")));
			
			
			return attachmentQueryManager.save(attachment);
		}
		else {
			attachment.setId(CommonUtil.getUUID());
			attachment.setCategory((String)attachmentMap.get("category"));
			attachment.setName((String)attachmentMap.get("name"));
			attachment.setDescription((String)attachmentMap.get("description"));
			attachment.setMimetype((String)attachmentMap.get("mimetype"));
			attachment.setEncoding((String)attachmentMap.get("encoding"));
			attachment.setRefBy((String)attachmentMap.get("refBy"));
			attachment.setPath((String)attachmentMap.get("path"));
			attachment.setSize((String)attachmentMap.get("size"));
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
	public void deleteAttachmentByRef(String ref) throws Exception{
		attachmentQueryManager.deleteAttachmentByRef(ref);
	}
	@Override
	public Attachments fetchAttachmentsById(String id) throws Exception{
		return (attachmentQueryManager.findById(UUID.fromString(id))).get();
	}
}

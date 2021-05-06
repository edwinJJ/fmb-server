package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Attachment;

public interface IAttachmentService {

	public List<Attachment> getAttachments(int start, int end) throws Exception;
}

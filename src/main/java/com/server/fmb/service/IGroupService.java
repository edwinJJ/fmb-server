package com.server.fmb.service;

import java.util.List;

import com.server.fmb.entity.Groups;

public interface IGroupService {
	public List<Groups> fetchGroupList() throws Exception;
	public List<Groups> fetchPlayGroupList() throws Exception;
}

package com.server.fmb.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.server.fmb.entity.Boards;

public interface IBoardService {
	public List<Boards> getBoards(int start, int end) throws Exception;
	public List<Boards> getBoardsByRoutingIds(List<UUID> routingIds) throws Exception;
	public List<Boards> getFavorites() throws Exception;
	public List<Boards> getBoardsByGroupId(String groupId) throws Exception;
	public Boards setBoard(Map<String, String> boardMap) throws Exception;
	public void deleteBoard(String boardId) throws Exception;
	public Boards fetchBoardById(String id) throws Exception;
}

package com.server.fmb.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Favorites;

public interface IBoardService {
	public List<Boards> getBoards(int start, int end) throws Exception;
	public List<Boards> getBoardsByRoutingIds(List<UUID> routingIds) throws Exception;
	public List<Boards> getFavorites() throws Exception;
	public List<Favorites> getOnlyFavorites() throws Exception;
	public List<Boards> getBoardsByGroupId(String groupId) throws Exception;
	public Boards setBoard(Map<String, String> boardMap) throws Exception;
	public void deleteBoard(String boardId) throws Exception;
	public Boards fetchBoardById(String id) throws Exception;
	public Favorites addFavorite(Map<String, String> boardMap) throws Exception;
	public void removeFavorite(String boardId) throws Exception;
}

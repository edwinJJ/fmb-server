package com.server.fmb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IBoardQueryManager;
import com.server.fmb.db.IFavoritesQueryManager;
import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Favorites;
import com.server.fmb.service.IBoardService;

@Service
public class BoardService implements IBoardService {

	@Autowired
	IBoardQueryManager boardQueryManager;
	
	@Autowired
	IFavoritesQueryManager favoritesManager;
	
	@Override
	public List<Boards> getBoards(int start, int end) throws Exception {
		return boardQueryManager.getBoards(start, end);
	}
	
	@Override
	public List<Boards> getBoardsByRoutingIds(List<UUID> routingIds) throws Exception {
//		일부 데이터만 가져오는 로직 (UUID 이슈로 보류)
//		List<Map<String, Object>> boardsListMap = boardQueryManager.getBoardsByRoutingIds(routingIds);
//		
//		List<Boards> boards = new ArrayList<Boards>();
//		for (Map<String, Object> boardMap : boardsListMap) {
//			Boards board = new Boards();
//			board.setId((UUID.fromString((String)boardMap.get("id"))));
//			board.setName((String)boardMap.get("name"));
//			board.setDescription((String)boardMap.get("description"));
//			board.setThumbnail((String)boardMap.get("thumbnail"));
//			board.setCreatedAt(null);
//			board.setUpdatedAt(null);
//			boards.add(board);
//		}
//		return boards;
		
		List<Boards> boardsListMap = boardQueryManager.getBoardsByRoutingIds(routingIds);
		return boardsListMap;
	}
	
	@Override
	public List<Boards> getFavorites() throws Exception {
		List<Favorites> favoritesList = favoritesManager.findAll();
		
		List<UUID> routingIds = new ArrayList<UUID>();
		for (Favorites favorites: favoritesList) {
			routingIds.add(UUID.fromString(favorites.getRouting()));
		}
		return getBoardsByRoutingIds(routingIds);
	}
	
	@Override
	public List<Boards> getBoardsByGroupId(String groupId) throws Exception {
		return boardQueryManager.getBoardsByGroupId(UUID.fromString(groupId));
	}
	

}

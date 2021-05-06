package com.server.fmb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.fmb.db.IBoardQueryManager;
import com.server.fmb.db.IDomainQueryManager;
import com.server.fmb.db.IFavoritesQueryManager;
import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Favorites;
import com.server.fmb.service.IBoardService;
import com.server.fmb.service.IDomainService;
import com.server.fmb.util.CommonUtil;
import com.server.fmb.util.ValueUtil;

@Service
public class BoardService implements IBoardService {

	@Autowired
	IBoardQueryManager boardQueryManager;
	
	@Autowired
	IFavoritesQueryManager favoritesManager;
	
	@Autowired
	IDomainService domainService;
	
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

	@Override
	public Boards setBoard(Map<String, String> boardMap) throws Exception {
		Boards board = new Boards();
		if (!ValueUtil.isEmpty(boardMap.get("id"))) {
			board = fetchBoardById(boardMap.get("id"));
//			board.setId(UUID.fromString(boardMap.get("id")));
			board.setName(boardMap.get("name"));
			board.setDescription(boardMap.get("description"));
//			board.setModel(boardMap.get("model"));
			board.setGroupId(UUID.fromString(boardMap.get("groupId")));
//			board.setThumbnail(boardMap.get("thumbnail"));
			board.setUpdatedAt(new Date());
			board.setUpdaterId(UUID.fromString(boardMap.get("updaterId")));
			board.setDomainId(UUID.fromString(boardMap.get("domainId")));
//			return boardQueryManager.updateBoard(board);
			return boardQueryManager.save(board);
		} else {
			board.setId(CommonUtil.getUUID());
			board.setName(boardMap.get("name"));
			board.setModel(boardMap.get("model"));
			board.setGroupId(UUID.fromString(boardMap.get("groupId")));
			board.setThumbnail("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZAAAAEsCAYAAADtt+XCAAAAAXNSR0IArs4c6QAABDtJREFUeJzt1cENwCAQwLDS/Xc+ZiAfhGRPkF/WzMwHAIf+2wEAvMlAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAxEAASAwEgMRAAEgMBIDEQABIDASAZAOEcwZU5JordwAAAABJRU5ErkJggg==");
			board.setCreatedAt(new Date());
			board.setUpdatedAt(new Date());
			board.setDomainId(domainService.getDomain().getId());
			return boardQueryManager.save(board);
		}
	}

	@Override
	public void deleteBoard(String boardId) throws Exception {
		boardQueryManager.deleteById(UUID.fromString(boardId));
	}

	@Override
	public Boards fetchBoardById(String id) throws Exception {
		return (boardQueryManager.findById(UUID.fromString(id))).get();
	}

}

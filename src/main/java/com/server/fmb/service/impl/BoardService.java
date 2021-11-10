/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    BoardService.java
 *  Description:  	BoardService 관련 서비스 Interface Impl
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.fmb.constant.Constant;
import com.server.fmb.db.IBoardQueryManager;
import com.server.fmb.db.IFavoriteQueryManager;
import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Favorites;
import com.server.fmb.service.IBoardService;
import com.server.fmb.service.IDomainService;
import com.server.fmb.service.IUserService;
import com.server.fmb.util.IdUtil;
import com.server.fmb.util.ValueUtil;

@Service
public class BoardService implements IBoardService {

	@Autowired
	IBoardQueryManager boardQueryManager;
	
	@Autowired
	IFavoriteQueryManager favoritesManager;
	
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public Boards getBoardByUseMcsAutoConfigAndId(String id) throws Exception {
		return boardQueryManager.getBoardByUseMcsAutoConfigAndId(id, true);
	}
	
	@Override
	public List<Boards> getBoards(int start, int end) throws Exception {
//		List<Boards> boardList = boardQueryManager.getBoards(start, end);
		List<Map<String, Object>> boardMapList = boardQueryManager.getBoards(start, end);
		return setMapToBoardsModel(boardMapList);
	}
	
	@Override
	public List<Boards> getBoardsByRoutingIds(List<String> routingIds) throws Exception {
//	public List<Boards> getBoardsByRoutingIds(List<UUID> routingIds) throws Exception {
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
		
		List<Map<String, Object>> boardMapList = boardQueryManager.getBoardsByRoutingIds(routingIds);
		return setMapToBoardsModel(boardMapList);
	}
	
	@Override
	public List<Boards> getFavorites() throws Exception {
		List<Favorites> favoritesList = favoritesManager.getAllFavorites();
		
		List<String> routingIds = new ArrayList<String>();
		for (Favorites favorites: favoritesList) {
			routingIds.add(favorites.getRouting());
		}
//		List<UUID> routingIds = new ArrayList<UUID>();
//		for (Favorites favorites: favoritesList) {
//			routingIds.add(UUID.fromString(favorites.getRouting()));
//		}
		return getBoardsByRoutingIds(routingIds);
	}
	
	@Override
	public List<Favorites> getOnlyFavorites() throws Exception {
		return favoritesManager.getAllFavorites();
	}
	
	@Override
	public List<Boards> getBoardsByGroupId(String groupId) throws Exception {
		List<Map<String, Object>> boardMapList = boardQueryManager.getBoardsByGroupId(groupId);
		return setMapToBoardsModel(boardMapList);
	}
	
	@Override
	public void setConfigBoardModel(Map<String, String> boardMap) throws Exception {
		setBoard(boardMap);
	}

	@Override
	public Boards setBoard(Map<String, String> boardMap) throws Exception {
		Boards board = new Boards();
		if (!ValueUtil.isEmpty(boardMap.get("id"))) {
			board = fetchBoardById(boardMap.get("id"));
			if (!ValueUtil.isEmpty(boardMap.get("name"))) board.setName(boardMap.get("name"));
			if (!ValueUtil.isEmpty(boardMap.get("description"))) board.setDescription(boardMap.get("description"));
			if (!ValueUtil.isEmpty(boardMap.get("model"))) board.setModel(boardMap.get("model"));
			if (!ValueUtil.isEmpty(boardMap.get("header"))) board.setHeader(boardMap.get("header"));
			if (!ValueUtil.isEmpty(boardMap.get("groupId"))) board.setGroupId(boardMap.get("groupId"));
//			if (!ValueUtil.isEmpty(boardMap.get("groupId"))) board.setGroupId(UUID.fromString(boardMap.get("groupId")));
			board.setUpdatedAt(new Date());
			board.setUpdaterId(boardMap.get("userName"));
			if (!ValueUtil.isEmpty(boardMap.get("domainId"))) {
				board.setDomainId(boardMap.get("domainId"));
//				board.setDomainId(UUID.fromString(boardMap.get("domainId")));
			} else {
				board.setDomainId(domainService.getDomain().getId());
			}
			if (ValueUtil.isNotEmpty(boardMap.get("thumbnail"))) board.setThumbnail(boardMap.get("thumbnail"));
			if (ValueUtil.isNotEmpty(boardMap.get("useMcsAutoConfig"))) board.setUseMcsAutoConfig(Boolean.parseBoolean(boardMap.get("useMcsAutoConfig")));
			return boardQueryManager.save(board);
		} else {
			board.setId(IdUtil.getUUIDString());
//			board.setId(IdUtil.getUUID());
			board.setName(boardMap.get("name"));
			board.setDescription(boardMap.get("description"));
			board.setModel(boardMap.get("model"));
			board.setHeader(boardMap.get("header"));
			board.setGroupId(boardMap.get("groupId"));
//			board.setGroupId(UUID.fromString(boardMap.get("groupId")));
			board.setThumbnail(Constant.NEW_THUMBNAIL);
			board.setUseMcsAutoConfig(Boolean.parseBoolean(boardMap.get("useMcsAutoConfig")));
			board.setCreatedAt(new Date());
			board.setUpdatedAt(new Date());
			board.setDomainId(domainService.getDomain().getId());
			board.setCreatorId(boardMap.get("userName"));
			board.setUpdaterId(boardMap.get("userName"));
			return boardQueryManager.save(board);
		}
	}

	@Override
	public void deleteBoard(String boardId) throws Exception {
		boardQueryManager.deleteById(boardId);
//		boardQueryManager.deleteById(UUID.fromString(boardId));
	}

	@Override
	public Boards fetchBoardById(String id) throws Exception {
		return (boardQueryManager.findById(id)).get();
//		return (boardQueryManager.findById(UUID.fromString(id))).get();
	}

	@Override
	public Favorites addFavorite(Map<String, String> boardMap) throws Exception {
		Favorites favorite = new Favorites();
		favorite.setId(IdUtil.getUUIDString());
//		favorite.setId(IdUtil.getUUID());
		favorite.setRouting(boardMap.get("boardId"));
		favorite.setCreatedAt(new Date());
		favorite.setUpdatedAt(new Date());
		favorite.setDomainId(domainService.getDomain().getId());
		favorite.setUserId(userService.getAdminUser().getId());
		return favoritesManager.save(favorite);
	}

	@Override
	@Transactional
	public void removeFavorite(String boardId) throws Exception {
		favoritesManager.deleteByRoutingId(boardId);
	}
	
	private List<Boards> setMapToBoardsModel(List<Map<String, Object>> boardMapList) throws Exception {
		List<Boards> boardList = new ArrayList<Boards>();
		for (Map<String, Object> boardMap : boardMapList) {
			Boards board = new Boards();
			board.setId((String)boardMap.get("id"));
			board.setName((String)boardMap.get("name"));
			board.setDescription((String)boardMap.get("description"));
			board.setThumbnail(clobToStr((Clob)boardMap.get("thumbnail")));
			board.setCreatedAt((Date)boardMap.get("created_at"));
			board.setUpdatedAt((Date)boardMap.get("updated_at"));
			board.setDomainId((String)boardMap.get("domain_id"));
			board.setGroupId((String)boardMap.get("group_id"));
			board.setCreatorId((String)boardMap.get("creator_id"));
			board.setUpdaterId((String)boardMap.get("updater_id"));
			boardList.add(board);
		}
		return boardList;
	}
	
	private String clobToStr(Clob clob) throws Exception {
		if (clob == null) {
			return "";
		} else {
			BufferedReader contentReader = new BufferedReader(clob.getCharacterStream());
			StringBuffer out = new StringBuffer();
			String aux;
			while ((aux = contentReader.readLine()) != null) {
				out.append(aux);
			}
			return out.toString();
			
		}
	}
	
}

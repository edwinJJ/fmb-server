/*
 *  Copyright (c) 2021 Smartworks.net, Inc. & AIROV Tech. All Rights Reserved
 *
 *  Use of this software is controlled by the terms and conditions found in the
 *  license agreement under which this software has been supplied.
 *------------------------------------------------------------------------------
 *
 *  Source Name:    BoardController.java
 *  Description:  	Board Controller 파일
 *  Authors:        J.I. Cho
 *  Update History:
 *                  2021.05.07 : Created by J.I. Cho
 *
 */
package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.constant.Constant;
import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Favorites;
import com.server.fmb.service.IBoardService;
import com.server.fmb.service.impl.ResultSet;
import com.server.fmb.util.ValueUtil;

@RestController
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IBoardService boardService;
	
	// get board list (ALL / GROUP)
	@RequestMapping(value="/fetchBoardList", method = RequestMethod.POST)
	public @ResponseBody Object fetchBoardList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Boards> boards = new ArrayList<>();
		try {
			ArrayList filters = (ArrayList)requestBody.get(Constant.FILTERS);
			if (ValueUtil.isNotEmpty(filters) && filters.size() > 0) {
				Map<String, String> filtersMap = (Map<String, String>)filters.get(0);
				if (filtersMap.get(Constant.NAME).equals(Constant.GROUP_ID)) {
					boards = boardService.getBoardsByGroupId(filtersMap.get(Constant.VALUE));
				}
			} else {
				Map<String, Integer> pagination = (Map<String, Integer>) requestBody.get(Constant.PAGINATION);
				if (ValueUtil.isEmpty(pagination)) {
					boards = boardService.getBoards(0, 0);
				} else {
					int start = (Integer) pagination.get(Constant.PAGE);
					if (start == 1) start = 0;
					boards = boardService.getBoards(start, (Integer)pagination.get(Constant.LIMIT));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(boards, false, "boards", e.toString());
		}
		Map<String, Object> boardsResult = new HashMap<String, Object>();
		boardsResult.put(Constant.ITEMS, boards);
		boardsResult.put(Constant.TOTAL, boards.size());
		return new ResultSet().getResultSet(boards, true, "boards", null);
	}
	
	// get board list (FAVOR)
	@RequestMapping(value="/fetchFavoriteBoardList", method = RequestMethod.POST)
	public @ResponseBody Object fetchFavoriteBoardList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Boards> boards = new ArrayList<>();
		try {
			boards = boardService.getFavorites();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(boards, false, "boards", e.toString());
		}
		return new ResultSet().getResultSet(boards, true, "boards", null);
	}
	
	// create board 
	@RequestMapping(value="/createBoard", method = RequestMethod.POST)
	public @ResponseBody Object createBoard(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Boards board = new Boards();
		try {
			board = boardService.setBoard(requestBody);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(board, false, "board", e.toString());
		}
		return new ResultSet().getResultSet(board, true, "board", null);
	}
	
	// delete board 
	@RequestMapping(value="/deleteBoard/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object deleteBoard(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			boardService.deleteBoard(id);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "success", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "success", null);
	}
	
	// get board 
	@RequestMapping(value = "/fetchBoardById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object fetchBoardById(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		Boards board = new Boards();
		try {
			board = boardService.fetchBoardById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(board, false, "board", e.toString());
		}
		return new ResultSet().getResultSet(board, true, "board", null);
	}
	
	// update board 
	@RequestMapping(value="/updateBoard", method = RequestMethod.PUT)
	public @ResponseBody Object updateBoard(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Boards board = new Boards();
		try {
			board = boardService.setBoard(requestBody);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(board, false, "board", e.toString());
		}
		return new ResultSet().getResultSet(board, true, "board", null);
	}
	
	// add Favorite 
	@RequestMapping(value="/addFavorite", method = RequestMethod.POST)
	public @ResponseBody Object addFavorite(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		Favorites favorite = new Favorites();
		try {
			favorite = boardService.addFavorite(requestBody);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(favorite, false, "favorite", e.toString());
		}
		return new ResultSet().getResultSet(favorite, true, "favorite", null);
	}
	
	// remote Favorite
	@RequestMapping(value="/removeFavorite/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object removeFavorite(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		try {
			boardService.removeFavorite(id);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(success, false, "success", e.toString());
		}
		return new ResultSet().getResultSet(success, true, "success", null);
	}
	
	// get favorites
	@RequestMapping(value = "/refreshFavorites", method = RequestMethod.GET)
	public @ResponseBody Object refreshFavorites(HttpServletRequest request) {
		List<Favorites> favoriteList = new ArrayList<Favorites>();
		try {
			favoriteList = boardService.getOnlyFavorites();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResultSet().getResultSet(favoriteList, false, "favoriteList", e.toString());
		}
		return new ResultSet().getResultSet(favoriteList, true, "favoriteList", null);
	}
	
}

package com.server.fmb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.fmb.constant.Constant;
import com.server.fmb.entity.Boards;
import com.server.fmb.entity.Favorites;
import com.server.fmb.service.IBoardService;

@RestController
public class BoardController {
	
	@Autowired
	IBoardService boardService;
	
	// get board list (ALL / GROUP)
	@RequestMapping(value="/fetchBoardList", method =RequestMethod.POST)
	public @ResponseBody List<Boards> fetchBoardList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) {
		List<Boards> boards = new ArrayList<>();
		try {
			ArrayList filters = (ArrayList)requestBody.get(Constant.FILTERS);
			if (filters.size() > 0) {
				Map<String, String> filtersMap = (Map<String, String>)filters.get(0);
				if (filtersMap.get(Constant.NAME).equals(Constant.GROUP_ID)) {
					boards = boardService.getBoardsByGroupId(filtersMap.get(Constant.VALUE));
				}
			} else {
				Map<String, Integer> pagination = (Map<String, Integer>) requestBody.get(Constant.PAGINATION);
				
				int start = (Integer) pagination.get(Constant.PAGE);
				if (start == 1) start = 0;
				boards = boardService.getBoards(start, (Integer)pagination.get(Constant.LIMIT));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}
	
	// get board list (FAVOR)
	@RequestMapping(value="/fetchFavoriteBoardList", method =RequestMethod.POST)
	public @ResponseBody List<Boards> fetchFavoriteBoardList(HttpServletRequest request, HttpServletResponse response) {
		List<Boards> boards = new ArrayList<>();
		try {
			boards = boardService.getFavorites();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}
	
	// create board 
	@RequestMapping(value="/createBoard", method =RequestMethod.POST)
	public @ResponseBody List<Boards> createBoard(HttpServletRequest request, HttpServletResponse response) {
		List<Boards> boards = new ArrayList<>();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}
	
}

package com.deu.football_love.service;

import com.deu.football_love.dto.BoardDto;

public interface BoardService {
	boolean add(BoardDto boardDto);

	void delete(Long boardId);
	
	BoardDto findById(Long boardId);
}

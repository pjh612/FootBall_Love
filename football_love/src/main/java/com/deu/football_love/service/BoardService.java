package com.deu.football_love.service;

import com.deu.football_love.dto.BoardRequest;

public interface BoardService {
	boolean add(BoardRequest boardRequest);

	void delete(Long boardId);
}

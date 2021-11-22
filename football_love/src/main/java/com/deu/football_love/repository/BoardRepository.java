package com.deu.football_love.repository;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;

public interface BoardRepository {

	Board insertBoard(Board board);

	void deleteBoard(Long boardId);

	int countBoardByType(String teamName, BoardType boardType);
	
	Board selectBoardById(Long boardId);
}

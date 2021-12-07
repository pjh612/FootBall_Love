package com.deu.football_love.repository;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;

public interface BoardRepository {

	Board insertBoard(Board board);

	void deleteBoard(Board board);

	int countBoardByType(Long teamId, BoardType boardType);
	
	Board selectBoardById(Long boardId);

	Board selectBoardByTeamIdAndBoardName(String boardName, Long teamId);
}

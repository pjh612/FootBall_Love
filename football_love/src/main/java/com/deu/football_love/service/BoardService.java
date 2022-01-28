package com.deu.football_love.service;

import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.dto.board.DeleteBoardResponse;

public interface BoardService {
    AddBoardResponse add(AddBoardRequest request);

    DeleteBoardResponse delete(Long boardId);

    BoardDto findById(Long boardId);

    BoardDto findByTeamIdAndBoardName(Long teamId, String boardName);
}

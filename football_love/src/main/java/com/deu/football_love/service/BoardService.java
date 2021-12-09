package com.deu.football_love.service;

import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.BoardDto;

public interface BoardService {
    AddBoardResponse add(AddBoardRequest request);

    void delete(Long boardId);

    BoardDto findById(Long boardId);
}

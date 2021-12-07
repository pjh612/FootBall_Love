package com.deu.football_love.dto.board;

import com.deu.football_love.domain.Board;
import lombok.Getter;

@Getter
public class AddBoardResponse {
    private Long boardId;

    public AddBoardResponse(Long boardId) {
        this.boardId = boardId;
    }

    public static AddBoardResponse from(Board board)
    {
        return new AddBoardResponse(board.getId());
    }

}

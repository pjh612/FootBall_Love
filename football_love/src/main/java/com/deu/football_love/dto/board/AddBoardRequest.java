package com.deu.football_love.dto.board;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;
import lombok.Getter;

@Getter
public class AddBoardRequest {
    private String boardName;
    private BoardType boardType;
    private Long teamId;

    public AddBoardRequest(String boardName, BoardType boardType, Long teamId) {
        this.boardName = boardName;
        this.boardType = boardType;
        this.teamId = teamId;
    }
}

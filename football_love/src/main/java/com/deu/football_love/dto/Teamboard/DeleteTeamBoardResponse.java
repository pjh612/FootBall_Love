package com.deu.football_love.dto.Teamboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteTeamBoardResponse {
    private Long boardId;
    private Integer status;
    private String message;

    public DeleteTeamBoardResponse(Long boardId, Integer status, String message) {
        this.boardId = boardId;
        this.status = status;
        this.message = message;
    }
}

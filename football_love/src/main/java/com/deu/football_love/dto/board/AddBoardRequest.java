package com.deu.football_love.dto.board;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.deu.football_love.domain.type.BoardType;

import lombok.Getter;

@Getter
public class AddBoardRequest {
	@NotNull
	@Size(min=1,max=30)
    private String boardName;
	
	@NotNull
    private BoardType boardType;
    
    @NotNull
    @Positive
    private Long teamId;

    public AddBoardRequest(String boardName, BoardType boardType, Long teamId) {
        this.boardName = boardName;
        this.boardType = boardType;
        this.teamId = teamId;
    }
}

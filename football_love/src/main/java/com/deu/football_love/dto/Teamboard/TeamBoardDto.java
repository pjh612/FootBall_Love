package com.deu.football_love.dto.Teamboard;

import com.deu.football_love.domain.TeamBoard;
import com.deu.football_love.domain.type.BoardType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamBoardDto {
	private Long boardId;
	private String boardName;
	private BoardType boardType;
	private Long teamId;
	
	public TeamBoardDto(TeamBoard board) {
		this.boardId = board.getId();
		this.boardName = board.getBoardName();
		this.boardType = board.getBoardType();
		this.teamId = board.getTeam().getId();
	}
}

package com.deu.football_love.dto;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDto {
	private String boardName;
	private BoardType boardType;
	private String teamName;
	
	public BoardDto(Board board) {
		this.boardName = board.getBoardName();
		this.boardType = board.getBoardType();
		this.teamName = board.getTeam().getName();
	}
}

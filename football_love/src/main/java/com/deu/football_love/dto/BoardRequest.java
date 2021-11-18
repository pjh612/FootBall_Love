package com.deu.football_love.dto;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;

import lombok.Getter;

@Getter
public class BoardRequest {
	private String boardName;
	private BoardType boardType;
	private String teamName;
	
	public BoardRequest(Board board) {
		this.boardName = board.getBoardName();
		this.boardType = board.getBoardType();
		this.teamName = board.getTeam().getName();
	}
}

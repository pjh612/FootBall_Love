package com.deu.football_love.dto.board;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryBoardDto {
  private Long boardId;
  private String boardName;
  private BoardType boardType;

  public QueryBoardDto(Board board) {
    this.boardId = board.getId();
    this.boardName = board.getBoardName();
    this.boardType = board.getBoardType();
  }
}

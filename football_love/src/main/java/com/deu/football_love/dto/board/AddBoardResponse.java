package com.deu.football_love.dto.board;

import com.deu.football_love.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddBoardResponse {
  private Long boardId;

  public static AddBoardResponse from(Board board) {
    return new AddBoardResponse(board.getId());
  }
}

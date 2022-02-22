package com.deu.football_love.dto.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteBoardResponse {
  private Long boardId;
  private Integer status;
  private String message;

  public DeleteBoardResponse(Long boardId, Integer status, String message) {
    this.boardId = boardId;
    this.status = status;
    this.message = message;
  }
}

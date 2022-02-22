package com.deu.football_love.dto.Teamboard;

import com.deu.football_love.domain.TeamBoard;
import lombok.Getter;

@Getter
public class AddTeamBoardResponse {
  private Long boardId;

  public AddTeamBoardResponse(Long boardId) {
    this.boardId = boardId;
  }

  public static AddTeamBoardResponse from(TeamBoard board) {

    return new AddTeamBoardResponse(board.getId());
  }

}

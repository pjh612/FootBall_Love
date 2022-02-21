package com.deu.football_love.dto.match;

import com.deu.football_love.domain.type.MatchApplicationState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryMatchApplicationDto {

  private Long id;

  private Long teamId;

  private Long matchId;

  private MatchApplicationState state;

  private String refuseMessage;

  public QueryMatchApplicationDto(Long id, Long teamId, Long matchId,
      MatchApplicationState state, String refuseMessage) {
    this.id = id;
    this.teamId = teamId;
    this.matchId = matchId;
    this.state = state;
    this.refuseMessage = refuseMessage;
  }
}

package com.deu.football_love.dto.match;

import java.time.LocalDateTime;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.type.MatchState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddMatchResponse {

  private Long matchId;
  private Long stadiumId;
  private LocalDateTime reservationTime;
  private MatchState state;

  public static AddMatchResponse from(Matches match) {
    return new AddMatchResponse(match.getId(), match.getStadium().getId(), match.getReservationTime(), match.getState());

  }
}

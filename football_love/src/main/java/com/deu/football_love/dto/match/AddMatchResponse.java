package com.deu.football_love.dto.match;

import java.time.LocalDateTime;
import com.deu.football_love.domain.Matches;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddMatchResponse {
  private Long matchId;
  private String teamName;
  private Long stadiumId;
  private Boolean approval;
  private LocalDateTime reservation_time;

  public static AddMatchResponse from(Matches match) {
    return new AddMatchResponse(match.getId(), match.getTeam().getName(),
        match.getStadium().getId(), match.getApproval(), match.getReservationTime());
  }
}

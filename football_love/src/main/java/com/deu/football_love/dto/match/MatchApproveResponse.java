package com.deu.football_love.dto.match;

import java.time.LocalDateTime;
import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchApproveResponse {
  private Long matchId;
  private Long matchApplicationId;
  private Long stadiumId;
  private Boolean approval;
  private LocalDateTime reservation_time;

  public static MatchApproveResponse from(Matches match, MatchApplication matchApplication) {
    return new MatchApproveResponse(match.getId(), matchApplication.getId(),
        match.getStadium().getId(), match.getApproval(), match.getReservationTime());
  }
}

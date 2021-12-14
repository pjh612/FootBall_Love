package com.deu.football_love.dto.match;

import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {
	private Long matchId;
	private String teamName;
	private Long stadiumId;
	private LocalDateTime reservation_time;

	public static MatchResponse from(Matches match) {
		return new MatchResponse(match.getId(), match.getTeam().getName(), match.getStadium().getId(),
				match.getReservationTime());
	}

	public static MatchResponse from(MatchApplication matchApplication) {
		return MatchResponse.from(matchApplication.getMatches());
	}
}

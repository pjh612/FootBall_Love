package com.deu.football_love.dto.match;

import com.deu.football_love.dto.team.QueryTeamDto;
import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchApproveResponse {
	private Long matchId;
	private Long stadiumId;
	private LocalDateTime reservationTime;
	private Long homeTeamId;
	private Long awayTeamId;

	public MatchApproveResponse(Long matchId, Long stadiumId, LocalDateTime reservationTime, Long homeTeamId, Long awayTeamId)
	{
		this.matchId = matchId;
		this.stadiumId= stadiumId;
		this.reservationTime = reservationTime;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
	}

}

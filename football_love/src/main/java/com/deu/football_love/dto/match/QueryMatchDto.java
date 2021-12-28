package com.deu.football_love.dto.match;

import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QueryMatchDto {
	private Long matchId;
	private String teamName;
	private Long stadiumId;
	private Boolean approval;
	private LocalDateTime reservation_time;

	public static QueryMatchDto from(Matches match) {
		return new QueryMatchDto(match.getId(), match.getTeam().getName(), match.getStadium().getId(),match.getApproval(),
				match.getReservationTime());
	}
}

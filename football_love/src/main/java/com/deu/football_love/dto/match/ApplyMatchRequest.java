package com.deu.football_love.dto.match;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMatchRequest {
	@Positive
	@NotNull
	Long teamId;
	
	@Positive
	@NotNull
	Long matchId;

	public ApplyMatchRequest(Long teamId, Long matchId) {
		this.teamId = teamId;
		this.matchId = matchId;
	}
}

package com.deu.football_love.dto.match;

import com.deu.football_love.domain.MatchApplication;

import com.deu.football_love.domain.type.MatchApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchApplicationResponse {
	private Long matchApplicationId;
	private Long matchId;
	private MatchApplicationState state;
	
	public static MatchApplicationResponse from(MatchApplication matchApplication) {
		return new MatchApplicationResponse(matchApplication.getId(), matchApplication.getMatch().getId(),matchApplication.getState());
	}
}

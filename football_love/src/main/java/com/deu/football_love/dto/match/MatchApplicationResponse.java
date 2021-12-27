package com.deu.football_love.dto.match;

import com.deu.football_love.domain.MatchApplication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchApplicationResponse {
	private Long matchApplicationId;
	private Long matchId;
	private Boolean approval;
	
	public static MatchApplicationResponse from(MatchApplication matchApplication) {
		return new MatchApplicationResponse(matchApplication.getId(), matchApplication.getMatches().getId(),matchApplication.getApproval());
	}
}

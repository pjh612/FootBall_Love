package com.deu.football_love.dto.match;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class RemoveRequest {
	@Positive
	@NotNull
	Long teamId;
	
	@Positive
	@NotNull
	Long matchId;
}

package com.deu.football_love.dto.match;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class ApproveMatchRequest {
	@Positive
	@NotNull
	private Long teamId;
	
	@Positive
	@NotNull
    private Long matchApplicationId;
}

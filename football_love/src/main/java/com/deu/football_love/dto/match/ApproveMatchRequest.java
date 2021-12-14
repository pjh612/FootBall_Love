package com.deu.football_love.dto.match;

import lombok.Getter;

@Getter
public class ApproveMatchRequest {
	private Long teamId;
    private Long memberNumber;
    private Long matchApplicationId;
}

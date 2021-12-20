package com.deu.football_love.dto.match;

import lombok.Getter;

@Getter
public class RemoveRequest {
	Long teamId;
	Long memberNumber;
	Long matchId;
}

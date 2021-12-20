package com.deu.football_love.dto.match;

import java.time.LocalDateTime;

import com.deu.football_love.domain.Stadium;

import lombok.Getter;

@Getter
public class ModifyMatchRequest {
	Long memberNumber;
	Long matchId;
	Long teamId;
	Long stadiumId;
	LocalDateTime reservationTime;
}

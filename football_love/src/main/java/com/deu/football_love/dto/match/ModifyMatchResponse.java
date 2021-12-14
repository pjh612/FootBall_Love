package com.deu.football_love.dto.match;

import java.time.LocalDateTime;

import com.deu.football_love.domain.Matches;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyMatchResponse {
	Long matchId;
	String stadiumType;
	LocalDateTime reservationTime;

	public static ModifyMatchResponse from(Matches match) {
		return new ModifyMatchResponse(match.getId(), match.getStadium().getType(), match.getReservationTime());
	}
}

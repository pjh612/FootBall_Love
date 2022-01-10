package com.deu.football_love.dto.stadium;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class DeleteStadiumRequest {
	@Positive
	@NotNull
	private Long stadiumId;
}

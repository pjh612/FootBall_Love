package com.deu.football_love.dto.match;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class ModifyMatchRequest {
	@Positive
	@NotNull
	Long matchId;
	
	@Positive
	@NotNull
	Long teamId;
	
	@Positive
	@NotNull
	Long stadiumId;

	@Future
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime reservationTime;
}

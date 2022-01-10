package com.deu.football_love.dto.match;

import lombok.Getter;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
public class CreateMatchRequest {
	@Positive
	@NotNull
    private Long teamId;
	
	@Positive
	@NotNull
    private Long stadiumId;
	
	@Future
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDateTime reservation_time;
}

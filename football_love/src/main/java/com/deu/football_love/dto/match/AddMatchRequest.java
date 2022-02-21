package com.deu.football_love.dto.match;

import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddMatchRequest {
	@Positive
	@NotNull
    private Long stadiumId;
	
	//@Future
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservationTime;

	public AddMatchRequest(Long stadiumId, LocalDateTime reservationTime) {
		this.stadiumId = stadiumId;
		this.reservationTime = reservationTime;
	}
}

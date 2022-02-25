package com.deu.football_love.dto.stadium;

import com.deu.football_love.domain.type.StadiumFieldType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class AddStadiumRequest {

	@NotNull
	private StadiumFieldType type;

	@NotNull
	@Size(min = 1, max = 15)
	private String size;

	@Positive
	@NotNull
	private Long cost;

	public AddStadiumRequest(StadiumFieldType type, String size, Long cost) {
		this.type = type;
		this.size = size;
		this.cost = cost;
	}
}

package com.deu.football_love.dto.stadium;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class AddStadiumRequest {
	@Positive
	@NotNull
	private Long companyId;

	@NotNull
	private String type; // type StadiumType Enum으로 변경해야함

	@NotNull
	@Size(min = 1, max = 15)
	private String size;

	@Positive
	@NotNull
	private Long cost;

	public AddStadiumRequest(Long companyId, String type, String size, Long cost) {
		this.companyId = companyId;
		this.type = type;
		this.size = size;
		this.cost = cost;
	}
}

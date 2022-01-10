package com.deu.football_love.dto.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class JoinApplyRequest {
	@NotNull
	@Size(min = 1, max = 200)
	private String Message;
}

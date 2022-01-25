package com.deu.football_love.dto.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTeamRequest {
	@NotNull
	@Size(min = 1, max = 20)
	private String teamName;

	@NotNull
	@Size(min = 1, max =400)
	private String teamIntroduce;
}

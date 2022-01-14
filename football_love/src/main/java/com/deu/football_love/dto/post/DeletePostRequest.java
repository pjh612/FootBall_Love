package com.deu.football_love.dto.post;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class DeletePostRequest {
	@Positive
	@NotNull
	private Long boardId;

	@Positive
	@NotNull
	private Long postId;
}

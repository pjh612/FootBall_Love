package com.deu.football_love.dto.post;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class WritePostRequest {
	@Positive
	@NotNull
	private Long authorNumber;

	@Positive
	@NotNull
	private Long boardId;

	@NotNull
	@Size(min = 1, max = 30)
	private String title;

	@NotNull
	@Size(min = 1, max = 30)
	private String content;

	public WritePostRequest(Long authorNumber, Long boardId, String title, String content) {
		this.authorNumber = authorNumber;
		this.boardId = boardId;
		this.title = title;
		this.content = content;
	}
}

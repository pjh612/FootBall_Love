package com.deu.football_love.dto.post;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class UpdatePostRequest {
	@NotNull
	@Size(min = 1, max = 20)
	private String title;

	@NotNull
	@Size(min = 1, max = 100)
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modifyDate;
}

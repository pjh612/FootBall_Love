package com.deu.football_love.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostRequest {
    private LocalDateTime modifyDate;
    private String title;
    private String content;
}

package com.deu.football_love.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WritePostRequest {
    private String Author;
    private Long boardId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String title;
    private String content;
}

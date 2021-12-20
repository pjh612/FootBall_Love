package com.deu.football_love.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WritePostRequest {
    private Long authorNumber;
    private Long boardId;
    private String title;
    private String content;

    public WritePostRequest(Long authorNumber, Long boardId, String title, String content) {
        this.authorNumber = authorNumber;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}

package com.deu.football_love.dto.post;

import lombok.Getter;

@Getter
public class DeletePostRequest {
    private Long boardId;
    private Long postId;
}

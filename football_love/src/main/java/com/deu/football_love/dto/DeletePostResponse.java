package com.deu.football_love.dto;

import lombok.Getter;

@Getter
public class DeletePostResponse {
    private Long postId;

    public DeletePostResponse(Long postId) {
        this.postId = postId;
    }
}

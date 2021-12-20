package com.deu.football_love.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeletePostResponse {
    private Long postId;

    public DeletePostResponse(Long postId) {
        this.postId = postId;
    }
}
